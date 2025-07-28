// IMessageListener.aidl
package com.example.messagebroker;

import com.example.messagebroker.Message;

/**
 * 메시지 수신을 위한 콜백 인터페이스
 * 클라이언트는 이 인터페이스를 구현하여 구독한 토픽의 메시지를 수신할 수 있습니다.
 */
interface IMessageListener {
    /**
     * 메시지가 수신되었을 때 호출됩니다.
     * @param message 수신된 메시지
     */
    void onMessageReceived(in Message message);

    /**
     * 연결 상태가 변경되었을 때 호출됩니다.
     * @param connected 연결 상태 (true: 연결됨, false: 연결 해제됨)
     */
    void onConnectionStatusChanged(boolean connected);
}
