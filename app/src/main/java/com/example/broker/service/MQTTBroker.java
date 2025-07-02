package com.example.broker.service;

import android.util.Log;

import java.io.IOException;
import java.net.BindException;
import java.util.Properties;
import java.util.concurrent.Callable;

import io.moquette.broker.Server;
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
    public static Server getServer() {
        return server;
    }
    public void stopServer() {
        if (server!= null) {
            server.stopServer();
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
