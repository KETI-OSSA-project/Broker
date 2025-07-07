package com.example.broker.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * AppPreferences is a utility class for managing application preferences using SharedPreferences.
 * It provides methods to get and set various preferences related to MQTT broker configuration.
 */
public class AppPreferences {
    private static final String NAME = "MQTT Broker";
    private static final int MODE = Context.MODE_PRIVATE;
    private static SharedPreferences preferences = null;

    // MQTT configuration variables
    public static String mqttHost = "기본값";
    public static String mqttPort = "1883";
    public static boolean allowLocalRun = false;

    // Preference keys
    private static final String IS_FIRST_RUN_KEY = "is_first_time";
    private static final String MQTT_PORT_KEY = "mqtt_port1";
    private static final String MQTT_AUTH_STATUS_KEY = "mqtt_auth_status";
    private static final String MQTT_BROKER_STATUS_KEY = "mqtt_broker_status";
    private static final String MQTT_USER_NAME_KEY = "mqtt_username";
    private static final String MQTT_PASSWORD_KEY = "mqtt_password";
    private static final String MQTT_HOST_KEY = "mqtt_host";
    private static final String MQTT_ALLOW_LOCAL_RUN_KEY = "mqtt_allow_local_run";

    // Default values
    private static final boolean IS_FIRST_RUN_DEFAULT = true;
    private static final String MQTT_PORT_DEFAULT = "1883";
    private static final boolean MQTT_AUTH_STATUS_DEFAULT = false;
    private static final boolean MQTT_BROKER_STATUS_DEFAULT = false;
    private static final String MQTT_USER_NAME_DEFAULT = "admin";
    private static final String MQTT_PASSWORD_DEFAULT = null;
    private static final String MQTT_HOST_DEFAULT = null;
    private static final boolean MQTT_ALLOW_LOCAL_RUN_DEFAULT = false;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(NAME, MODE);
    }

    private static SharedPreferences requirePrefs() {
        if (preferences == null) {
            throw new IllegalStateException("AppPreferences not initialized");
        }
        return preferences;
    }

    // First Run
    public static boolean getFirstRun() {
        return requirePrefs().getBoolean(IS_FIRST_RUN_KEY, IS_FIRST_RUN_DEFAULT);
    }

    public static void setFirstRun(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(IS_FIRST_RUN_KEY, value);
        editor.commit(); // Use commit instead of apply for better compatibility
    }

    // MQTT Port
    public static String getMqttPort() {
        return requirePrefs().getString(MQTT_PORT_KEY, MQTT_PORT_DEFAULT);
    }

    public static void setMqttPort(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_PORT_KEY, value);
        editor.commit();
    }

    // MQTT Username
    public static String getMqttUserName() {
        return requirePrefs().getString(MQTT_USER_NAME_KEY, MQTT_USER_NAME_DEFAULT);
    }

    public static void setMqttUserName(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_USER_NAME_KEY, value);
        editor.commit();
    }

    // MQTT Password
    public static String getMqttPassword() {
        return requirePrefs().getString(MQTT_PASSWORD_KEY, MQTT_PASSWORD_DEFAULT);
    }

    public static void setMqttPassword(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_PASSWORD_KEY, value);
        editor.commit();
    }

    // MQTT Auth Status
    public static boolean getMqttAuthStatus() {
        return requirePrefs().getBoolean(MQTT_AUTH_STATUS_KEY, MQTT_AUTH_STATUS_DEFAULT);
    }

    public static void setMqttAuthStatus(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(MQTT_AUTH_STATUS_KEY, value);
        editor.commit();
    }

    // MQTT Broker Status
    public static boolean getMqttBrokerStatus() {
        return requirePrefs().getBoolean(MQTT_BROKER_STATUS_KEY, MQTT_BROKER_STATUS_DEFAULT);
    }

    public static void setMqttBrokerStatus(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(MQTT_BROKER_STATUS_KEY, value);
        editor.commit();
    }

    // MQTT Host
    public static String getMqttHost() {
        return requirePrefs().getString(MQTT_HOST_KEY, MQTT_HOST_DEFAULT);
    }

    public static void setMqttHost(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_HOST_KEY, value);
        editor.commit();
    }

    // Allow Local Run
    public static boolean getAllowLocalRun() {
        return requirePrefs().getBoolean(MQTT_ALLOW_LOCAL_RUN_KEY, MQTT_ALLOW_LOCAL_RUN_DEFAULT);
    }

    public static void setAllowLocalRun(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(MQTT_ALLOW_LOCAL_RUN_KEY, value);
        editor.commit();
    }
}