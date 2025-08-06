package com.example.broker;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * MessagingService provides MQTT message broker functionality for Android applications.
 */
public class MessagingService extends Service {

    private static final String TAG = "Android Broker";
    private static final int MAX_MESSAGE_HISTORY_PER_TOPIC = 100;

    private final Map<String, Set<ClientSubscription>> topicSubscriptions = new ConcurrentHashMap<>();
    private final Map<String, CopyOnWriteArrayList<Message>> topicMessageHistory = new ConcurrentHashMap<>();

    private static class ClientSubscription {
        final IMessageListener listener;
        final String packageName;

        ClientSubscription(IMessageListener listener, String packageName) {
            this.listener = listener;
            this.packageName = packageName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientSubscription that = (ClientSubscription) o;
            return listener.asBinder().equals(that.listener.asBinder());
        }

        @Override
        public int hashCode() {
            return listener.asBinder().hashCode();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "🔗 클라이언트가 브로커에 연결됨");
        return messagingServiceBinder;
    }

    private boolean checkPermission(String permission) {
        int pid = Binder.getCallingPid();
        int uid = Binder.getCallingUid();
        return checkPermission(permission, pid, uid) == getPackageManager().PERMISSION_GRANTED;
    }

    private final IMessagingService.Stub messagingServiceBinder = new IMessagingService.Stub() {

        @Override
        public void publish(Message message) {
            if (!checkPermission("com.example.permission.MESSAGE_PUBLISH")) {
                Log.w(TAG, "발행 권한 없음");
                return;
            }
            if (message == null || message.getTopic() == null) {
                Log.w(TAG, "Invalid message for publishing");
                return;
            }

            String topic = message.getTopic();
            Log.i(TAG, "📤 Publishing message to topic: " + topic);

            addToTopicHistory(topic, message);
            deliverMessageToSubscribers(topic, message); // sub로 메시지 발행하는 부분
            // gateway로 메시지 전달
            if (gatewayListener != null) {
                try {
                    gatewayListener.onMessage(message);
                    Log.i(TAG, "Message delivered to gateway");
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to deliver message to gateway", e);
                }
            }
        }

        @Override
        public boolean subscribe(IMessageListener client, Filter filter) {
            if (!checkPermission("com.example.permission.MESSAGE_SUBSCRIBE")) {
                Log.w(TAG, "구독 권한 없음");
                return false;
            }
            if (client == null || filter == null) {
                Log.w(TAG, "Invalid subscribe parameters");
                return false;
            }

            String topic = filter.getTopic();
            if (topic == null || topic.isEmpty()) {
                Log.w(TAG, "Subscribe failed: topic is null or empty");
                return false;
            }

            String packageName = getPackageManager().getNameForUid(Binder.getCallingUid());
            Log.i(TAG, "\uD83D\uDCE5 Subscribe request - Package: " + packageName + ", Topic: " + topic + ", client: " + client.asBinder());

            Set<ClientSubscription> subscribers = topicSubscriptions.computeIfAbsent(topic, k -> new CopyOnWriteArraySet<>());
            subscribers.add(new ClientSubscription(client, packageName));
            // 구독자 목록 전체 출력
            for (ClientSubscription sub : subscribers) {
                Log.d(TAG, "[Subscribe] Existing subscriber: " + sub.listener.asBinder());
            }
            Log.d(TAG, "[Subscribe] topicSubscriptions: " + topicSubscriptions.keySet());

            Log.i(TAG, "✅ Subscribe successful");
            return true;
        }

        @Override
        public boolean unsubscribe(IMessageListener client, Filter filter) {
            if (client == null || filter == null) {
                Log.w(TAG, "Unsubscribe failed: client or filter is null");
                return false;
            }

            String topic = filter.getTopic();
            if (topic == null || topic.isEmpty()) {
                Log.w(TAG, "Unsubscribe failed: topic is null or empty");
                return false;
            }

            Log.i(TAG, "\uD83D\uDCE4 Unsubscribe request - Topic: " + topic + ", client: " + client.asBinder());

            Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
            if (subscribers != null) {
                for (ClientSubscription sub : subscribers) {
                    Log.d(TAG, "[Unsubscribe] Existing subscriber: " + sub.listener.asBinder());
                }
                boolean removed = subscribers.removeIf(sub -> sub.listener.asBinder().equals(client.asBinder()));
                if (subscribers.isEmpty()) {
                    topicSubscriptions.remove(topic);
                }
                if (removed) {
                    Log.i(TAG, "✅ Unsubscribe successful");
                    return true;
                }
            } else {
                Log.w(TAG, "Unsubscribe failed: no subscribers for topic " + topic);
            }
            Log.w(TAG, "Unsubscribe failed: client not found for topic");
            return false;
        }

        // 공식 문서에서는 unsubscribe라고 되어있으나, 구분을 위해 변경
        @Override
        public boolean unsubscribeAll(IMessageListener client) {
            if (client == null) {
                return false;
            }

            Log.i(TAG, "📤 Unsubscribe all request");

            for (Set<ClientSubscription> subscribers : topicSubscriptions.values()) {
                subscribers.removeIf(sub -> sub.listener.asBinder().equals(client.asBinder()));
            }

            // 빈 토픽들 정리
            topicSubscriptions.entrySet().removeIf(entry -> entry.getValue().isEmpty());

            Log.i(TAG, "✅ Unsubscribe all successful");
            return true;
        }
    };

    // gateway 리스너 등록 변수
    private IMessageListener gatewayListener;

    // gateway 리스너 등록 메서드
    public void setGatewayListener(IMessageListener listener) {
        this.gatewayListener = listener;
        Log.i(TAG, "Gateway listener registered: " + (listener != null ? listener.asBinder() : "null"));
    }

    private void addToTopicHistory(String topic, Message message) {
        CopyOnWriteArrayList<Message> history = topicMessageHistory.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>());

        if (history.size() >= MAX_MESSAGE_HISTORY_PER_TOPIC) {
            history.remove(0);
        }

        history.add(message);
    }

    private void deliverMessageToSubscribers(String topic, Message message) {
        Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
        if (subscribers == null || subscribers.isEmpty()) {
            Log.w(TAG, "No subscribers for topic: " + topic);
            return;
        }

        Log.i(TAG, "Delivering message to " + subscribers.size() + " subscribers");

        for (ClientSubscription subscription : subscribers) {
            try {
                subscription.listener.onMessage(message);
                Log.d(TAG, "Message delivered to client: " + subscription.packageName);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to deliver message to client: " + subscription.packageName, e);
                // 연결이 끊어진 클라이언트 제거
                subscribers.remove(subscription);
            }
        }
    }
}