package com.example.broker.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import io.moquette.BrokerConstants;

import androidx.annotation.RequiresApi;

/**
 * MqttService class.
 * This service is intended to handle MQTT connections and operations.
 * Currently, it is a placeholder and does not implement any functionality.
 */

import com.example.broker.MainActivity;
import com.example.broker.R;
import com.example.broker.utils.AppPreferences;
import com.example.broker.utils.Utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MqttService extends Service {

    private static final String TAG = MqttService.class.getSimpleName();
    private static final String CHANNEL_ID = "MqttServiceChannel";
    private MQTTBroker mqttBroker = null;
    private Thread thread = null;

    private void updateIP() {
        AppPreferences.mqttHost = Utils.getIPAddress(true);
    }

    // This method retrieves the MQTT configuration properties from a file or uses default values.
    private Properties getConfig() {
        Properties props = new Properties();
        props.setProperty(BrokerConstants.PORT_PROPERTY_NAME, AppPreferences.mqttPort);
        // props.setProperty(BrokerConstants.NEED_CLIENT_AUTH, "false");

        props.setProperty(
                BrokerConstants.HOST_PROPERTY_NAME,
                AppPreferences.allowLocalRun ? "127.0.0.1" : Utils.getIPAddress(true)
        );
        props.setProperty(
                BrokerConstants.WEB_SOCKET_PORT_PROPERTY_NAME,
                String.valueOf(BrokerConstants.WEBSOCKET_PORT)
        );
        return props;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Handle MQTT connection and operations here
        Log.i(TAG, "MqttService started with intent: " + intent);

        // Initialize AppPreferences -> 버전이 26 이상인 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, getNotification());
        } else {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            int pendingIntentflags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntentflags |= PendingIntent.FLAG_MUTABLE;
            } else {
                pendingIntentflags |= PendingIntent.FLAG_IMMUTABLE;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingIntentflags);

            // API 26 미만용 Notification 생성 코드 작성 필요
            Notification notification = new Notification.Builder(this)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(android.R.drawable.stat_notify_sync)
                    .setContentIntent(pendingIntent) // pendingIntent는 아래에서 정의
                    .build();
            startForeground(1, notification);
        }
        try {
            updateIP();
        } catch (Exception e) {
            Log.e(TAG, "Error updating IP: " + e.getMessage(), e);
            Toast.makeText(this, "Error updating IP: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Properties props = getConfig();
            Log.d(TAG, "Starting MQTT Broker with properties: " + props);
            mqttBroker = new MQTTBroker(props);
            FutureTask<Boolean> futureTask = new FutureTask<>(mqttBroker);

            if (thread == null || !thread.isAlive()) {
                thread = new Thread(futureTask);
                thread.setName("MQTT Server");
                thread.start();

                if (!futureTask.get()) {
                    Toast.makeText(this, "Failed to start MQTT Broker", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "MQTT Broker started successfully", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (ExecutionException e) {
            Log.e(TAG, "ExecutionException while starting MQTT Broker: " + e.getMessage(), e);
            Toast.makeText(this, "ExecutionException: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException while starting MQTT Broker: " + e.getMessage(), e);
            Thread.currentThread().interrupt(); // Restore interrupted status
            Toast.makeText(this, "InterruptedException: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No binding for this service
    }

    @Override
    public void onDestroy() {
        if (thread != null && MQTTBroker.getServer() != null) {
            try {
                Log.d(TAG, "Stopping MQTT Broker");
                mqttBroker.stopServer();
                thread.interrupt();
                Toast.makeText(this, "MQTT Broker stopped", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "Error stopping MQTT Broker: " + e.getMessage(), e);
            }
        } else {
            Log.d(TAG, "MQTT Broker was not running or thread is null");
        }
        stopSelf();
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // Create notification channel for Android O and above
    private Notification getNotification() {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        // Set flags for PendingIntent based on Android version
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_MUTABLE;
        } else {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flags);

        return new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText((R.string.notification_message)))
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "MQTT Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
