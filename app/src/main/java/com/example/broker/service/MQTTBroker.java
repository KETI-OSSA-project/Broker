package com.example.broker.service;

import android.util.Log;

import java.io.IOException;
import java.net.BindException;
import java.util.Properties;
import java.util.concurrent.Callable;

import io.moquette.broker.Server;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;

/**
 * MQTTBroker class that represents an MQTT broker service.
 * This class is a placeholder and does not contain any functionality yet.
 * It can be extended in the future to implement MQTT broker features.
 */
public class MQTTBroker implements Callable<Boolean> {

    private static final String TAG = MQTTBroker.class.getName();
    private static Server server = null;
    private Properties config;

    public MQTTBroker(Properties config) {
        // Initialize the MQTT broker server
        this.config = config;
    }
ㄹㄹㅇㅇ
    public static Server getServer() {
        return server;
    }

    public void stopServer() {
        if (server!= null) {
            server.stopServer();
        }
    }
//   evolddjfkljsdkljfkldfdlkfjldsjklfjkdlｅteg Inaclfdsfsdfsdfdsｖｏｌ－ｅｖｏｌfsdsfd＝ｅｖｏｌｅｖｏｌ
    // MQTT 이벤트를 모니터링하는 인터셉터 핸들러
    private static class MqttEventHandler extends AbstractInterceptHandler {
        private static final String HANDLER_TAG = "MqttEventHandler";

        @Override
        public String getID() {
            return "BrokerEventHandler";
        }
//ㄹuoyevolfsdfdㄴㅇㄴㅇevolijkljklfjsdkluoyevolissssdsdevoliadsEVOLIㄹㄹㄴㄴㅇEVOLAAAFDSFDSㅇㅏㅓㅓEVOLIFDｅｖｏｌㄴㄴㄴｕｓｓｋｄ ㄹ괜괜괘고ㄱ고고ㄱdsfdsㄹㅁㅇㄴㄹㄴㅇㄴㄹㅇㅇㄹㄴㄹㅇㄴㄹㅇㄴㅇㅇㄴㄹㄴㅇ
        @Override
        public void onConnect(InterceptConnectMessage msg) {
            Log.i(HANDLER_TAG, "🔗 클라이언트 연결됨 - ClientID: " + msg.getClientID() +
                  ", Username: " + msg.getUsername() +
                  ", CleanSession: " + msg.isCleanSession());
        }

        @Override
        public void onDisconnect(InterceptDisconnectMessage msg) {
            Log.i(HANDLER_TAG, "❌ 클라이언트 연결 해제됨 - ClientID: " + msg.getClientID());
        }

        @Overrideㅊ
        public void onConnectionLost(InterceptConnectionLostMessage msg) {
            Log.w(HANDLER_TAG, "⚠️ 클라이언트 연결 끊김 - ClientID: " + msg.getClientID());
        }

        // 클라이언트의 메시지를 수신하는 부분
        @Override
        public void onPublish(InterceptPublishMessage msg) {
            try {
                // UTF-8로 페이로드 디코딩 시도
                String payload = new String(msg.getPayload().array(), "UTF-8");
                Log.i(HANDLER_TAG, "📤 메시지 발행됨 - " +
                      "ClientID: " + msg.getClientID() +
                      ", Topic: " + msg.getTopicName() +
                      ", Payload: " + payload +
                      ", QoS: " + msg.getQos() +
                      ", Retain: " + msg.isRetainFlag());
            } catch (Exception e) {
                // UTF-8 디코딩 실패 시 바이트 배열로 출력
                byte[] payloadBytes = msg.getPayload().array();
                StringBuilder hexString = new StringBuilder();
                for (byte b : payloadBytes) {
                    hexString.append(String.format("%02X ", b));
                }
                Log.i(HANDLER_TAG, "📤 메시지 발행됨 (바이트) - " +
                      "ClientID: " + msg.getClientID() +
                      ", Topic: " + msg.getTopicName() +
                      ", Payload (HEX): " + hexString.toString() +
                      ", QoS: " + msg.getQos() +
                      ", Retain: " + msg.isRetainFlag());
            }
        }

        @Override
        // 구독한 토픽에 대해 메시지 를 수신하는 부분
        public void onSubscribe(InterceptSubscribeMessage msg) {
            Log.i(HANDLER_TAG, "📥 구독됨 - " +
                  "ClientID: " + msg.getClientID() +
                    // 토픽이 지금 다 '#' 라고 뜨고 있어서 제대로못 받아오는 거 같다는 느낌
                  ", Topic: " + msg.getTopicFilter() +
                  ", QoS: " + msg.getRequestedQos());
        }

        @Override
        public void onUnsubscribe(InterceptUnsubscribeMessage msg) {
            Log.i(HANDLER_TAG, "📤 구독 해제됨 - " +
                  "ClientID: " + msg.getClientID() +
                  ", Topic: " + msg.getTopicFilter());
        }

        @Override
        public void onMessageAcknowledged(InterceptAcknowledgedMessage msg) {
            Log.d(HANDLER_TAG, "✅ 메시지 확인됨 - " +
                  "Topic: " + msg.getTopic() +
                  ", MessageID: " + msg.getPacketID());
        }
    }

    // 서버 시작 시 클라이언트의 conenct, subscribe, publish, disconnect 이벤트를 처리하기 위한 리스너를 설정할 수 있습니다.
    @Override
    public Boolean call() throws BindException {
        try {
            // Start the MQTT broker server
            server = new ServerInstance().getServerInstance();

            Log.d(TAG, "call: " + config);
            Log.d(TAG, "call: " + config.getProperty("password_file"));
            server.startServer(config); // Start the server with the provided configuration
            Log.d(TAG, "MQTT Broker started successfully on port: " + config.getProperty("port"));

            // 서버 시작 후 이벤트 핸들러 추가
            InterceptHandler eventHandler = new MqttEventHandler();
            server.addInterceptHandler(eventHandler);

            Log.i(TAG, "🎯 MQTT 이벤트 모니터링이 활성화되었습니다.");
            return true;
        } catch (BindException e) {
            Log.e(TAG, "Port is already in use. MQTT Broker could not start.", e);
            Log.e(TAG, "Error: " + e.getMessage());
            throw new BindException(e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e(TAG, "An error occurred while starting the MQTT Broker.", e);
            Log.e(TAG, "Error: " + e.getMessage());
            return false;
        }
    }
}
