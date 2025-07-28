// IMessagingServiceConnectionListener.aidl
package com.example.messagebroker;

// Declare any non-default types here with import statements

interface IMessagingServiceConnectionListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onConnected();

    void onDisconnected();
}