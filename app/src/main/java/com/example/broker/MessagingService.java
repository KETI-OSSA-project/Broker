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

    private static final String TAG = "MQTTBroker";
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
        Log.i(TAG, "MessagingService bound");
        return messagingServiceBinder;
    }

    private final IMessagingService.Stub messagingServiceBinder = new IMessagingService.Stub() {

        @Override
        public void publish(Message message) {
            if (message == null || message.getTopic() == null) {
                Log.w(TAG, "Invalid message for publishing");
                return;
            }

            String topic = message.getTopic();
            Log.i(TAG, "📤 Publishing message to topic: " + topic);

            addToTopicHistory(topic, message);
            deliverMessageToSubscribers(topic, message);
        }

        @Override
        public boolean subscribe(IMessageListener client, Filter filter) {
            if (client == null || filter == null) {
                Log.w(TAG, "Invalid subscribe parameters");
                return false;
            }

            String topic = filter.getTopic();
            String packageName = getPackageManager().getNameForUid(Binder.getCallingUid());
            Log.i(TAG, "📥 Subscribe request - Topic: " + topic + ", Package: " + packageName);

            topicSubscriptions.computeIfAbsent(topic, k -> new CopyOnWriteArraySet<>())
                             .add(new ClientSubscription(client, packageName));

            Log.i(TAG, "✅ Subscribe successful - Topic: " + topic);
            return true;
        }

        @Override
        public boolean unsubscribe(IMessageListener client, Filter filter) {
            if (client == null || filter == null) {
                return false;
            }

            String topic = filter.getTopic();
            Log.i(TAG, "📤 Unsubscribe request - Topic: " + topic);

            Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
            if (subscribers != null) {
                subscribers.removeIf(sub -> sub.listener.asBinder().equals(client.asBinder()));
                if (subscribers.isEmpty()) {
                    topicSubscriptions.remove(topic);
                }
            }

            Log.i(TAG, "✅ Unsubscribe successful - Topic: " + topic);
            return true;
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
