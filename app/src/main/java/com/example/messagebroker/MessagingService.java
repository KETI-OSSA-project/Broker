package com.example.messagebroker;

import android.app.Service;
import android.content.Intent;
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
 *
 * This service acts as a local MQTT broker and manages:
 * - Topic-based message publishing and subscription
 * - Client subscription management with topic filtering
 * - Message routing between publishers and subscribers
 * - Message persistence and history management
 */
public class MessagingService extends Service {

    private static final String TAG = "MQTTBroker";

    // 토픽별 구독자 관리 (topic -> Set<ClientSubscription>)
    private final Map<String, Set<ClientSubscription>> topicSubscriptions = new ConcurrentHashMap<>();

    // 클라이언트별 구독 정보 관리 (clientHash -> Set<String>)
    private final Map<Integer, Set<String>> clientTopics = new ConcurrentHashMap<>();

    // 클라이언트 정보 관리 (clientHash -> ClientInfo)
    private final Map<Integer, ClientInfo> registeredClients = new ConcurrentHashMap<>();

    // 메시지 히스토리 관리 (토픽별)
    private final Map<String, CopyOnWriteArrayList<Message>> topicMessageHistory = new ConcurrentHashMap<>();

    // 최대 저장할 메시지 수 (토픽당)
    private static final int MAX_MESSAGE_HISTORY_PER_TOPIC = 100;

    /**
     * 클라이언트 정보를 담는 내부 클래스
     */
    private static class ClientInfo {
        final String packageName;
        final long registerTime;

        ClientInfo(String packageName) {
            this.packageName = packageName;
            this.registerTime = System.currentTimeMillis();
        }
    }

    /**
     * 클라이언트 구독 정보를 담는 내부 클래스
     */
    private static class ClientSubscription {
        final int clientHash;
        final String packageName;
        final long subscribeTime;
        final IMessageListener listener; // 메시지 리스너 추가

        ClientSubscription(int clientHash, String packageName, IMessageListener listener) {
            this.clientHash = clientHash;
            this.packageName = packageName;
            this.listener = listener;
            this.subscribeTime = System.currentTimeMillis();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof ClientSubscription)) return false;
            ClientSubscription other = (ClientSubscription) obj;
            return clientHash == other.clientHash;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(clientHash);
        }
    }

    /**
     * AIDL 인터페이스 구현 - MQTT 브로커 기능
     */
    private final IMessagingService.Stub binder = new IMessagingService.Stub() {

        @Override
        public void publish(Message message) throws RemoteException {
            if (message == null) {
                Log.w(TAG, "Null message received for publishing");
                return;
            }

            String topic = message.getTopic();
            if (topic == null || topic.trim().isEmpty()) {
                Log.w(TAG, "Invalid topic for message publishing");
                return;
            }

            Log.d(TAG, "📤 Publishing message to topic: '" + topic + "'" +
                      ", payload size: " + (message.getPayload() != null ? message.getPayload().length : 0) +
                      ", direct subscribers: " + getSubscriberCountForTopic(topic));

            // 디버깅: 모든 등록된 토픽과 구독자 수 출력
            Log.d(TAG, "🔍 All registered topics and subscribers:");
            for (Map.Entry<String, Set<ClientSubscription>> entry : topicSubscriptions.entrySet()) {
                Log.d(TAG, "   Topic: '" + entry.getKey() + "' -> " + entry.getValue().size() + " subscribers");
            }

            // 디버깅: 클라이언트별 구독 토픽 출력
            Log.d(TAG, "🔍 Client topic subscriptions:");
            for (Map.Entry<Integer, Set<String>> entry : clientTopics.entrySet()) {
                Log.d(TAG, "   Client " + entry.getKey() + " -> topics: " + entry.getValue());
            }

            // 메시지 히스토리에 추가
            addToTopicHistory(topic, message);

            // 해당 토픽을 구독한 클라이언트들에게 메시지 전달
            deliverMessageToTopicSubscribers(topic, message);

            // 와일드카드 토픽 매칭도 처리
            deliverMessageToWildcardSubscribers(topic, message);
        }

        @Override
        public boolean subscribeToTopic(IMessageListener listener, int clientHash, String topic, String applicationPackageName) throws RemoteException {
            if (applicationPackageName == null || topic == null || listener == null) {
                Log.w(TAG, "Invalid parameters for topic subscription");
                return false;
            }

            // 클라이언트 등록
            registeredClients.put(clientHash, new ClientInfo(applicationPackageName));

            // 클라이언트의 토픽 목록에 추가
            clientTopics.computeIfAbsent(clientHash, k -> new CopyOnWriteArraySet<>()).add(topic);

            // 토픽의 구독자 목록에 추가 (리스너 포함)
            topicSubscriptions.computeIfAbsent(topic, k -> new CopyOnWriteArraySet<>())
                             .add(new ClientSubscription(clientHash, applicationPackageName, listener));

            Log.d(TAG, "📥 Client subscribed - Package: " + applicationPackageName +
                      ", Topic: " + topic +
                      ", ClientHash: " + clientHash +
                      ", Total topic subscribers: " + getSubscriberCountForTopic(topic));

            // 연결 상태 알림
            try {
                listener.onConnectionStatusChanged(true);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to notify connection status to client: " + applicationPackageName, e);
            }

            // 기존 메시지 히스토리 전달
            deliverTopicHistoryToNewSubscriber(clientHash, topic);

            return true;
        }

        @Override
        public boolean subscribe(IMessageListener listener, int clientHash, String applicationPackageName) throws RemoteException {
            // 기존 방식과의 호환성을 위해 기본 토픽으로 구독
            return subscribeToTopic(listener, clientHash, "#", applicationPackageName); // 모든 토픽 구독
        }

        @Override
        public boolean unsubscribeFromTopic(int clientHash, String topic) throws RemoteException {
            Set<String> clientTopicSet = clientTopics.get(clientHash);
            if (clientTopicSet == null || !clientTopicSet.remove(topic)) {
                Log.w(TAG, "Client not subscribed to topic: " + topic);
                return false;
            }

            Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
            if (subscribers != null) {
                ClientSubscription toRemove = null;
                for (ClientSubscription sub : subscribers) {
                    if (sub.clientHash == clientHash) {
                        toRemove = sub;
                        break;
                    }
                }

                if (toRemove != null) {
                    subscribers.remove(toRemove);

                    // 연결 해제 알림
                    try {
                        if (toRemove.listener != null) {
                            toRemove.listener.onConnectionStatusChanged(false);
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "Failed to notify disconnection to client", e);
                    }
                }

                if (subscribers.isEmpty()) {
                    topicSubscriptions.remove(topic);
                    Log.d(TAG, "🗑️ Removed empty topic: " + topic);
                }
            }

            ClientInfo clientInfo = registeredClients.get(clientHash);
            String packageName = clientInfo != null ? clientInfo.packageName : "Unknown";

            Log.d(TAG, "📤 Client unsubscribed from topic - Package: " + packageName +
                      ", Topic: " + topic +
                      ", ClientHash: " + clientHash);

            return true;
        }

        @Override
        public boolean unsubscribe(int clientHash) throws RemoteException {
            return unsubscribeFromAllTopics(clientHash);
        }

        @Override
        public boolean unsubscribeAll(int clientHash) throws RemoteException {
            return unsubscribeFromAllTopics(clientHash);
        }

        @Override
        public String getBrokerStatus() throws RemoteException {
            StringBuilder status = new StringBuilder();
            status.append("{\n");
            status.append("  \"totalClients\": ").append(registeredClients.size()).append(",\n");
            status.append("  \"totalTopics\": ").append(topicSubscriptions.size()).append(",\n");
            status.append("  \"topics\": [\n");

            boolean first = true;
            for (Map.Entry<String, Set<ClientSubscription>> entry : topicSubscriptions.entrySet()) {
                if (!first) status.append(",\n");
                status.append("    {\n");
                status.append("      \"topic\": \"").append(entry.getKey()).append("\",\n");
                status.append("      \"subscribers\": ").append(entry.getValue().size()).append("\n");
                status.append("    }");
                first = false;
            }

            status.append("\n  ]\n");
            status.append("}");

            return status.toString();
        }

        @Override
        public int getSubscriberCount(String topic) throws RemoteException {
            return getSubscriberCountForTopic(topic);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                             float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG, "basicTypes called - Debug method");
        }

        /**
         * 모든 토픽에서 구독 해제 (내부 메서드)
         */
        private boolean unsubscribeFromAllTopics(int clientHash) throws RemoteException {
            ClientInfo clientInfo = registeredClients.remove(clientHash);
            Set<String> topics = clientTopics.remove(clientHash);

            if (clientInfo == null || topics == null) {
                Log.w(TAG, "Attempted to unsubscribe non-existent client: " + clientHash);
                return false;
            }

            // 각 토픽에서 클라이언트 제거
            for (String topic : topics) {
                Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
                if (subscribers != null) {
                    ClientSubscription toRemove = null;
                    for (ClientSubscription sub : subscribers) {
                        if (sub.clientHash == clientHash) {
                            toRemove = sub;
                            break;
                        }
                    }

                    if (toRemove != null) {
                        subscribers.remove(toRemove);

                        // 연결 해제 알림
                        try {
                            if (toRemove.listener != null) {
                                toRemove.listener.onConnectionStatusChanged(false);
                            }
                        } catch (RemoteException e) {
                            Log.e(TAG, "Failed to notify disconnection to client", e);
                        }
                    }

                    // 구독자가 없으면 토픽 제거
                    if (subscribers.isEmpty()) {
                        topicSubscriptions.remove(topic);
                        Log.d(TAG, "🗑️ Removed empty topic: " + topic);
                    }
                }
            }

            Log.d(TAG, "📤 Client unsubscribed from all topics - Package: " + clientInfo.packageName +
                      ", ClientHash: " + clientHash +
                      ", Topics: " + topics.size());

            return true;
        }
    };

    /**
     * 특정 토픽의 구독자 수 반환
     */
    private int getSubscriberCountForTopic(String topic) {
        Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
        return subscribers != null ? subscribers.size() : 0;
    }

    /**
     * 토픽 히스토리에 메시지 추가
     */
    private void addToTopicHistory(String topic, Message message) {
        CopyOnWriteArrayList<Message> history = topicMessageHistory.computeIfAbsent(
            topic, k -> new CopyOnWriteArrayList<>());

        history.add(message);

        // 최대 개수 초과시 오래된 메시지 제거
        while (history.size() > MAX_MESSAGE_HISTORY_PER_TOPIC) {
            history.remove(0);
        }
    }

    /**
     * 해당 토픽을 구독한 클라이언트들에게 메시지 전달
     */
    private void deliverMessageToTopicSubscribers(String topic, Message message) {
        Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
        if (subscribers == null || subscribers.isEmpty()) {
            Log.d(TAG, "No subscribers for topic: " + topic);
            return;
        }

        Log.d(TAG, "🚀 Delivering message to " + subscribers.size() + " subscribers for topic: " + topic);

        for (ClientSubscription subscription : subscribers) {
            try {
                if (subscription.listener != null) {
                    subscription.listener.onMessageReceived(message);
                    Log.d(TAG, "📨 Message delivered to client: " + subscription.packageName +
                              " (hash: " + subscription.clientHash + ")");
                } else {
                    Log.w(TAG, "No listener for client: " + subscription.packageName);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to deliver message to client: " + subscription.packageName, e);
                // Dead object - 클라이언트가 죽었을 수 있으므로 구독 해제 고려
                if (e.getCause() instanceof android.os.DeadObjectException) {
                    Log.w(TAG, "Dead client detected, removing subscription: " + subscription.packageName);
                    removeDeadSubscription(subscription);
                }
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error delivering message to client: " + subscription.packageName, e);
            }
        }
    }

    /**
     * 와일드카드 토픽 매칭 처리
     */
    private void deliverMessageToWildcardSubscribers(String publishedTopic, Message message) {
        for (Map.Entry<String, Set<ClientSubscription>> entry : topicSubscriptions.entrySet()) {
            String subscribedTopic = entry.getKey();

            // 정확한 매칭은 이미 처리했으므로 제외
            if (subscribedTopic.equals(publishedTopic)) {
                continue;
            }

            // 와일드카드 매칭 확인
            if (isTopicMatch(publishedTopic, subscribedTopic)) {
                Set<ClientSubscription> subscribers = entry.getValue();

                Log.d(TAG, "🔄 Wildcard match: " + publishedTopic + " matches " + subscribedTopic +
                          " (" + subscribers.size() + " subscribers)");

                for (ClientSubscription subscription : subscribers) {
                    try {
                        if (subscription.listener != null) {
                            subscription.listener.onMessageReceived(message);
                            Log.d(TAG, "📨 Wildcard message delivered to client: " + subscription.packageName);
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "Failed to deliver wildcard message to client: " + subscription.packageName, e);
                        if (e.getCause() instanceof android.os.DeadObjectException) {
                            Log.w(TAG, "Dead client detected in wildcard delivery: " + subscription.packageName);
                            removeDeadSubscription(subscription);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Unexpected error delivering wildcard message to client: " + subscription.packageName, e);
                    }
                }
            }
        }
    }

    /**
     * MQTT 토픽 매칭 로직 (와일드카드 지원)
     */
    private boolean isTopicMatch(String publishedTopic, String subscribedTopic) {
        if (subscribedTopic.equals("#")) {
            return true; // 모든 토픽 매칭
        }

        if (subscribedTopic.equals(publishedTopic)) {
            return true; // 정확한 매칭
        }

        // + 와일드카드 처리 (단일 레벨)
        if (subscribedTopic.contains("+")) {
            String[] pubParts = publishedTopic.split("/");
            String[] subParts = subscribedTopic.split("/");

            if (pubParts.length != subParts.length) {
                return false;
            }

            for (int i = 0; i < pubParts.length; i++) {
                if (!subParts[i].equals("+") && !subParts[i].equals(pubParts[i])) {
                    return false;
                }
            }
            return true;
        }

        // # 와일드카드 처리 (멀티 레벨)
        if (subscribedTopic.endsWith("/#")) {
            String prefix = subscribedTopic.substring(0, subscribedTopic.length() - 2);
            return publishedTopic.startsWith(prefix + "/") || publishedTopic.equals(prefix);
        }

        return false;
    }

    /**
     * 새 구독자에게 토픽 히스토리 전달
     */
    private void deliverTopicHistoryToNewSubscriber(int clientHash, String topic) {
        CopyOnWriteArrayList<Message> history = topicMessageHistory.get(topic);
        if (history == null || history.isEmpty()) {
            return;
        }

        // 클라이언트 구독 정보 찾기
        Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
        if (subscribers == null) {
            return;
        }

        ClientSubscription targetSubscription = null;
        for (ClientSubscription sub : subscribers) {
            if (sub.clientHash == clientHash) {
                targetSubscription = sub;
                break;
            }
        }

        if (targetSubscription == null || targetSubscription.listener == null) {
            return;
        }

        Log.d(TAG, "📚 Delivering " + history.size() + " historical messages to new subscriber: " + targetSubscription.packageName);

        for (Message message : history) {
            try {
                targetSubscription.listener.onMessageReceived(message);
                Log.d(TAG, "📜 Historical message delivered to: " + targetSubscription.packageName);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to deliver historical message to: " + targetSubscription.packageName, e);
                if (e.getCause() instanceof android.os.DeadObjectException) {
                    Log.w(TAG, "Dead client detected during history delivery: " + targetSubscription.packageName);
                    removeDeadSubscription(targetSubscription);
                    break; // 더 이상 전달할 필요 없음
                }
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error delivering historical message to: " + targetSubscription.packageName, e);
            }
        }
    }

    /**
     * 죽은 클라이언트 구독 정보 제거
     */
    private void removeDeadSubscription(ClientSubscription deadSubscription) {
        try {
            // 모든 토픽에서 해당 클라이언트 제거
            Set<String> clientTopicSet = clientTopics.get(deadSubscription.clientHash);
            if (clientTopicSet != null) {
                for (String topic : clientTopicSet) {
                    Set<ClientSubscription> subscribers = topicSubscriptions.get(topic);
                    if (subscribers != null) {
                        subscribers.remove(deadSubscription);
                        if (subscribers.isEmpty()) {
                            topicSubscriptions.remove(topic);
                            Log.d(TAG, "🗑️ Removed empty topic after dead client cleanup: " + topic);
                        }
                    }
                }
            }

            // 클라이언트 정보 제거
            clientTopics.remove(deadSubscription.clientHash);
            registeredClients.remove(deadSubscription.clientHash);

            Log.i(TAG, "🧹 Cleaned up dead client: " + deadSubscription.packageName +
                      " (hash: " + deadSubscription.clientHash + ")");

        } catch (Exception e) {
            Log.e(TAG, "Error during dead client cleanup", e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "🚀 MQTT Broker Service Created");
        Log.i(TAG, "Ready to handle topic-based message routing");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 모든 구독 정보 정리
        int totalClients = registeredClients.size();
        int totalTopics = topicSubscriptions.size();

        topicSubscriptions.clear();
        clientTopics.clear();
        registeredClients.clear();
        topicMessageHistory.clear();

        Log.i(TAG, "🛑 MQTT Broker Service Destroyed");
        Log.i(TAG, "Cleaned up " + totalClients + " clients and " + totalTopics + " topics");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "🔗 Client binding to MQTT Broker Service");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "🔌 Client unbinding from MQTT Broker Service");
        return super.onUnbind(intent);
    }

    /**
     * 브로커 상태 정보 로깅
     */
    private void logBrokerStatus() {
        Log.i(TAG, "📊 Broker Status:");
        Log.i(TAG, "  - Registered Clients: " + registeredClients.size());
        Log.i(TAG, "  - Active Topics: " + topicSubscriptions.size());

        for (Map.Entry<String, Set<ClientSubscription>> entry : topicSubscriptions.entrySet()) {
            Log.i(TAG, "    Topic '" + entry.getKey() + "': " + entry.getValue().size() + " subscribers");
        }
    }
}
