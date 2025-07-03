package com.example.broker.utils;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import com.example.broker.MQTTBrokerApp;

public class NetworkCallBack extends ConnectivityManager.NetworkCallback {

    private static final String TAG = "NetworkCallBack";

    // Got network connection
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Intent intent = new Intent(Utils.NETWORK_BROADCAST_ACTION);
        intent.putExtra("status", true);
        MQTTBrokerApp.getInstance().sendBroadcast(intent);
    }

    // Lost network connection
    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Intent intent = new Intent(Utils.NETWORK_BROADCAST_ACTION);
        intent.putExtra("status", false);
        MQTTBrokerApp.getInstance().sendBroadcast(intent);
    }
}
