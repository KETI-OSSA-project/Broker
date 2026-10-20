package com.example.broker;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class MessagingService extends Service {

    private static final String TAG = "Android Broker";
    private static final int MAX_MESSAGE_HISTORY_PER_TOPIC = 100;

    private final Map<String, Set<ClientSubscription>> topicSubscriptions = new ConcurrentHashMap<>();
    private final Map<String, CopyOnWriteArrayList<Message>> topicMessageHistory = new ConcurrentHashMap<>();

    private static class ClientSubscription {
        final IMessageListener listener;
        final String packageName;
        final String publisherId;   // 필터의 발행자 조건(OSSA 5.1.7). null이면 모든 발행자

        ClientSubscription(IMessageListener listener, String packageName, String publisherId) {
            this.listener = listener;
            this.packageName = packageName;
            this.publisherId = publisherId;
        }

        boolean accepts(Message message) {
            return publisherId == null || publisherId.equals(message.getPublisherId());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;      // 리스너와 발행자 조건으로 비교
            ClientSubscription that = (ClientSubscription) o;               // 다운캐스팅
            return listener.asBinder().equals(that.listener.asBinder()) && Objects.equals(publisherId, that.publisherId);
        }

        @Override
        public int hashCode() {
            return listener.asBinder().hashCode();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "클라이언트가 브로커에 연결됨");
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
            if (message == null || !TopicFilter.isValidTopic(message.getTopic())) {
                Log.w(TAG, "Invalid message for publishing (토픽 없음 또는 와일드카드 포함)");
                return;
            }

            String topic = message.getTopic();
            // source ID는 앱이 적은 값이 아니라 호출한 앱의 패키지명(OSSA 5.1.6). 남의 이름으로 발행할 수 없다
            message.setPublisherId(getPackageManager().getNameForUid(Binder.getCallingUid()));
            Log.i(TAG, "📤 Publishing message to topic: " + topic + " publisher=" + message.getPublisherId());

            addToTopicHistory(topic, message);
            deliverMessageToSubscribers(topic, message); // sub로 메시지 발행하는 부분
            // gateway로 메시지 전달
            if (gatewayListener != null) {
                try {
                    // Bundle 직렬화 문제를 완전히 우회하기 위해 최소한의 정보만 전달
                    String safeTopic = message.getTopic();
                    byte[] safePayload = message.getPayload();

                    if (safeTopic == null || safeTopic.trim().isEmpty()) {
                        safeTopic = "broker/default";
                        Log.w(TAG, "Topic was null/empty, using default: " + safeTopic);
                    }

                    // 완전히 새로운 Message 객체를 생성하되, Bundle 최소화
                    Message gatewayMessage = new Message(safeTopic, safePayload != null ? safePayload : new byte[0]);

                    gatewayListener.onMessage(gatewayMessage);
                } catch (android.os.DeadObjectException e) {
                    gatewayListener = null;
                } catch (RemoteException e) {
                    // Bundle 관련 오류인 경우 게이트웨이 연결을 끊지 않고 재시도
                    if (e.getMessage() != null && (e.getMessage().contains("Bundle") || e.getMessage().contains("aligned"))) {
                        // gatewayListener를 null로 설정하지 않음 - 게이트웨이는 여전히 연결되어 있음
                    } else {
                        gatewayListener = null;
                    }
                } catch (Exception e) {
                    // Bundle 관련 오류인 경우 연결을 유지
                    if (e.getMessage() != null && (e.getMessage().contains("Bundle") || e.getMessage().contains("aligned"))) {
                    } else {
                        gatewayListener = null;
                    }
                }
            } else {
                Log.w(TAG, "게이트웨이가 아직 켜지지 않음");
            }
        }

        @Override
        public boolean subscribe(IMessageListener client, Filter filter) {
            if (!checkPermission("com.example.permission.MESSAGE_SUBSCRIBE")) {
                Log.w(TAG, "구독 권한 없음");
                return false;
            }
            if (client == null || filter == null) {
                Log.w(TAG, "파라미터가 null");
                return false;
            }

            String topic = filter.getTopic();
            if (topic == null || topic.isEmpty()) {
                Log.w(TAG, "구독 실패: 토픽 비어있음");
                return false;
            }
            if (!TopicFilter.isValidFilter(topic)) {
                Log.w(TAG, "구독 실패: 필터 문법 오류 " + topic);
                return false;
            }

            String packageName = getPackageManager().getNameForUid(Binder.getCallingUid());
            Log.i(TAG, "\uD83D\uDCE5 Subscribe request - Package: " + packageName + ", Topic: " + topic
                    + ", publisher: " + filter.getPublisherId() + ", client: " + client.asBinder());

            Set<ClientSubscription> subscribers = topicSubscriptions.computeIfAbsent(topic, k -> new CopyOnWriteArraySet<>());
            subscribers.add(new ClientSubscription(client, packageName, filter.getPublisherId()));
            for (ClientSubscription sub : subscribers) {
                Log.d(TAG, "[Subscribe] Existing subscriber: " + sub.listener.asBinder());
            }
            Log.d(TAG, "[Subscribe] topicSubscriptions: " + topicSubscriptions.keySet());

            Log.i(TAG, "구독 성공");
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
                boolean removed = subscribers.removeIf(sub -> sub.listener.asBinder().equals(client.asBinder())
                        && Objects.equals(sub.publisherId, filter.getPublisherId()));
                if (subscribers.isEmpty()) {
                    topicSubscriptions.remove(topic);
                }
                if (removed) {
                    Log.i(TAG, "구독 해제 성공");
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

            Log.i(TAG, "모든 구독 해제");
            removeClient(client.asBinder());
            Log.i(TAG, "모든 구독 해제 성공");
            return true;
        }

        // 게이트웨이로 메시지 보내는 함수. 모든 토픽을 받는 자리라 게이트웨이 앱만 등록할 수 있다
        // ponytail: 패키지명만 확인. 같은 이름의 다른 앱이 설치될 수 있는 기기면 서명 비교(checkSignatures)를 더한다
        @Override
        public void GatewayListener(IMessageListener listener) {
            String caller = getPackageManager().getNameForUid(Binder.getCallingUid());
            if (!GATEWAY_PACKAGE.equals(caller)) {
                Log.w(TAG, "GatewayListener 거부: " + caller);
                throw new SecurityException("only " + GATEWAY_PACKAGE + " can register the gateway listener");
            }
            gatewayListener = listener;
            Log.i(TAG, "GatewayListener 등록됨");
        }
    };

    private static final String GATEWAY_PACKAGE = "com.example.gateway";

    // gateway 리스너 등록 변수
    private volatile IMessageListener gatewayListener;

    private void addToTopicHistory(String topic, Message message) {
        CopyOnWriteArrayList<Message> history = topicMessageHistory.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>());

        if (history.size() >= MAX_MESSAGE_HISTORY_PER_TOPIC) {
            history.remove(0);
        }

        history.add(message);
    }

    private void deliverMessageToSubscribers(String topic, Message message) {
        // 리스너별로 맞은 필터를 모은다. 한 리스너가 여러 필터에 맞아도 알림은 한 번 (절차서 19번)
        // 발행자 조건이 있는 필터는 그 발행자의 메시지에만 맞는다 (OSSA 5.1.7)
        Map<ClientSubscription, List<String>> targets = new LinkedHashMap<>();
        for (Map.Entry<String, Set<ClientSubscription>> entry : topicSubscriptions.entrySet()) {
            if (!TopicFilter.matches(entry.getKey(), topic)) continue;
            for (ClientSubscription subscription : entry.getValue()) {
                if (!subscription.accepts(message)) {
                    Log.i(TAG, "Skipped topic=" + topic + " for " + subscription.packageName + " filter=" + entry.getKey()
                            + " wants publisher=" + subscription.publisherId + " got=" + message.getPublisherId());
                    continue;
                }
                // 발행자 조건을 뺀 키로 모아야 한 리스너에 한 번만 간다
                ClientSubscription key = new ClientSubscription(subscription.listener, subscription.packageName, null);
                targets.computeIfAbsent(key, k -> new ArrayList<>()).add(entry.getKey());
            }
        }
        if (targets.isEmpty()) {
            Log.w(TAG, "No subscribers for topic: " + topic);
            return;
        }

        for (Map.Entry<ClientSubscription, List<String>> target : targets.entrySet()) {
            ClientSubscription subscription = target.getKey();
            try {
                subscription.listener.onMessage(message);
                Log.i(TAG, "Delivered topic=" + topic + " to " + subscription.packageName + " filters=" + target.getValue());
            } catch (android.os.DeadObjectException e) {
                Log.w(TAG, "Client disconnected, removed: " + subscription.packageName);
                removeClient(subscription.listener.asBinder());
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to deliver message to client: " + subscription.packageName, e);
                removeClient(subscription.listener.asBinder());
            }
        }
    }

    // 리스너의 구독을 모든 필터에서 지우고 빈 필터를 정리한다
    private void removeClient(IBinder binder) {
        for (Set<ClientSubscription> subscribers : topicSubscriptions.values()) {
            subscribers.removeIf(sub -> sub.listener.asBinder().equals(binder));
        }
        topicSubscriptions.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}

