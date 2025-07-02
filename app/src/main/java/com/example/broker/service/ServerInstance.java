package com.example.broker.service;

import android.util.Log;
import io.moquette.broker.Server;

public class ServerInstance {

    private static final String TAG = ServerInstance.class.getName();
    private Server serverInstance = null;

    public Server getServerInstance() {
        try{
            if (serverInstance == null) {
                // Create and start the server instance
                synchronized (this) {
                    if (serverInstance == null) {
                        serverInstance = new Server();
                        Log.i(TAG, "Server instance created and started successfully.");
                        return serverInstance;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error creating or starting server instance: " + e.getMessage(), e);
        }
        return serverInstance;
    }
}
