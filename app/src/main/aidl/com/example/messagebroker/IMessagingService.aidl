// IMessagingService.aidl
package com.example.messagebroker;

import com.example.messagebroker.Message;
import com.example.messagebroker.IMessageListener;

/**
 * MQTT Broker Service Interface
 * 토픽 기반 메시지 브로커 기능을 제공합니다.
 */
interface IMessagingService {
    /**
     * 메시지를 특정 토픽에 발행합니다.
     * @param message 발행할 메시지 (토픽 정보 포함)
     */
    void publish(in Message message);

    /**
     * 특정 토픽을 구독합니다.
     * @param listener 메시지를 받을 리스너
     * @param clientHash 클라이언트 고유 해시
     * @param topic 구독할 토픽 (와일드카드 지원: +, #)
     * @param applicationPackageName 클라이언트 패키지명
     * @return 구독 성공 여부
     */
    boolean subscribeToTopic(in IMessageListener listener, int clientHash, String topic, String applicationPackageName);

    /**
     * 모든 토픽을 구독합니다 (호환성을 위한 기존 메소드)
     * @param listener 메시지를 받을 리스너
     * @param clientHash 클라이언트 고유 해시
     * @param applicationPackageName 클라이언트 패키지명
     * @return 구독 성공 여부
     */
    boolean subscribe(in IMessageListener listener, int clientHash, String applicationPackageName);

    /**
     * 특정 토픽 구독을 해제합니다.
     * @param clientHash 클라이언트 고유 해시
     * @param topic 구독 해제할 토픽
     * @return 구독 해제 성공 여부
     */
    boolean unsubscribeFromTopic(int clientHash, String topic);

    /**
     * 클라이언트의 모든 구독을 해제합니다.
     * @param clientHash 클라이언트 고유 해시
     * @return 구독 해제 성공 여부
     */
    boolean unsubscribe(int clientHash);

    /**
     * 클라이언트의 모든 구독을 해제합니다 (unsubscribe와 동일)
     * @param clientHash 클라이언트 고유 해시
     * @return 구독 해제 성공 여부
     */
    boolean unsubscribeAll(int clientHash);

    /**
     * 브로커 상태 정보를 반환합니다.
     * @return 브로커 상태 문자열 (JSON 형태)
     */
    String getBrokerStatus();

    /**
     * 특정 토픽의 구독자 수를 반환합니다.
     * @param topic 토픽명
     * @return 구독자 수
     */
    int getSubscriberCount(String topic);

    /**
     * AIDL 기본 타입 테스트용 메소드
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);
}