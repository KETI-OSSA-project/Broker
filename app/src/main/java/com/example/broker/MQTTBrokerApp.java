package com.example.broker;

import android.app.Application;
import com.example.broker.utils.AppPreferences;

public class MQTTBrokerApp extends Application {

    private static MQTTBrokerApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppPreferences.init(this);
        instance = this;
    }

    public static MQTTBrokerApp getInstance() {
        return instance;
    }
}
