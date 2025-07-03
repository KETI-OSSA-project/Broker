package com.example.broker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

/**
 * AppPreferences is a utility class for managing application preferences using SharedPreferences.
 * It provides methods to get and set various preferences related to MQTT broker configuration.
 */
public class AppPreferences {
    private static final String NAME = "MQTT Broker";
    private static final int MODE = Context.MODE_PRIVATE;   // Use private mode for security
    private static SharedPreferences preferences = null;

    // Default values for MQTT configuration
    public static String mqttHost = "기본값";
    public static String mqttPort = "1883";
    public static boolean allowLocalRun = false;
    public static String mqttWebSocketPort = "8080";

    // Preference keys and default values
    private static final Pair<String, Boolean> IS_FIRST_RUN_PREF = new Pair<>("is_first_time", true);
    private static final Pair<String, String> MQTT_PORT = new Pair<>("mqtt_port1", "1883");
    private static final Pair<String, Boolean> MQTT_AUTH_STATUS = new Pair<>("mqtt_auth_status", false);
    private static final Pair<String, Boolean> MQTT_BROKER_STATUS = new Pair<>("mqtt_broker_status", false);
    private static final Pair<String, String> MQTT_USER_NAME = new Pair<>("mqtt_username", "admin");
    private static final Pair<String, String> MQTT_PASSWORD = new Pair<>("mqtt_password", null);
    private static final Pair<String, String> MQTT_HOST = new Pair<>("mqtt_host", null);
    private static final Pair<String, Boolean> MQTT_ALLOW_LOCAL_RUN = new Pair<>("mqtt_allow_local_run", false);

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
        return requirePrefs().getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second);
    }

    public static void setFirstRun(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(IS_FIRST_RUN_PREF.first, value);
        editor.apply();
    }

    // MQTT Port
    public static String getMqttPort() {
        return requirePrefs().getString(MQTT_PORT.first, MQTT_PORT.second);
    }

    public static void setMqttPort(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_PORT.first, value);
        editor.apply();
    }

    // MQTT Username
    public static String getMqttUserName() {
        return requirePrefs().getString(MQTT_USER_NAME.first, MQTT_USER_NAME.second);
    }

    public static void setMqttUserName(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_USER_NAME.first, value);
        editor.apply();
    }

    // MQTT Password
    public static String getMqttPassword() {
        return requirePrefs().getString(MQTT_PASSWORD.first, MQTT_PASSWORD.second);
    }

    public static void setMqttPassword(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_PASSWORD.first, value);
        editor.apply();
    }

    // MQTT Auth Status
    public static boolean getMqttAuthStatus() {
        return requirePrefs().getBoolean(MQTT_AUTH_STATUS.first, MQTT_AUTH_STATUS.second);
    }

    public static void setMqttAuthStatus(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(MQTT_AUTH_STATUS.first, value);
        editor.apply();
    }

    // MQTT Broker Status
    public static boolean getMqttBrokerStatus() {
        return requirePrefs().getBoolean(MQTT_BROKER_STATUS.first, MQTT_BROKER_STATUS.second);
    }

    public static void setMqttBrokerStatus(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(MQTT_BROKER_STATUS.first, value);
        editor.apply();
    }

    // MQTT Host
    public static String getMqttHost() {
        return requirePrefs().getString(MQTT_HOST.first, MQTT_HOST.second);
    }

    public static void setMqttHost(String value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putString(MQTT_HOST.first, value);
        editor.apply();
    }

    // Allow Local Run
    public static boolean getAllowLocalRun() {
        return requirePrefs().getBoolean(MQTT_ALLOW_LOCAL_RUN.first, MQTT_ALLOW_LOCAL_RUN.second);
    }

    public static void setAllowLocalRun(boolean value) {
        SharedPreferences.Editor editor = requirePrefs().edit();
        editor.putBoolean(MQTT_ALLOW_LOCAL_RUN.first, value);
        editor.apply();
    }
}
