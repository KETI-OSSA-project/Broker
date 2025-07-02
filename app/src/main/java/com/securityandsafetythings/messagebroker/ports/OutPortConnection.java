/*
 * Copyright 2021 by Security and Safety Things GmbH
 *
 * All rights reserved,
 * also regarding any disposal, exploitation, reproduction,
 * editing, distribution, as well as in the event of
 * applications for industrial property rights.
 *
 * This software contains proprietary and confidential
 * information of Security and Safety Things GmbH.
 * The disclosure of confidential information without
 * express authorization is prohibited.
 *
 * This software may include Open Source Software.
 * As far as Open Source Software is included,
 * your rights for the respective Open Source Software
 * components are governed by the applicable
 * Open Source Software licenses.
 */

package com.securityandsafetythings.messagebroker.ports;

import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import com.securityandsafetythings.messagebroker.MessageBrokerConnectionException;
import com.securityandsafetythings.messagebroker.MessageBrokerResult;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * OutPortConnection class.
 */
public class OutPortConnection {
    private IOutPortConnection outPortConnectionBinder = null;

    /**
     * Constructor.
     *
     * @param outPortConnection OutPortConnection binder instance.
     * @hide
     */
    OutPortConnection(@NonNull IOutPortConnection outPortConnection) {
        this.outPortConnectionBinder = requireNonNull(outPortConnection);
    }

    private static void handleResult(MessageBrokerResult result)
            throws MessageBrokerConnectionException {
        switch (result) {
            case OK:
                return;
            case NOT_OK:
            default:
                throw new MessageBrokerConnectionException(
                        MessageBrokerConnectionException.Type.UNKNOWN_ERROR);
        }
    }

    /**
     * Send messages.
     *
     * @throws MessageBrokerConnectionException if the connection is deleted.
     */
    public void send(final @NonNull Message message) throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        Objects.requireNonNull(message);
        try {
            handleResult(outPortConnectionBinder.send(message));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the unique identifier for this connection.
     *
     * @return Unique identifier for this connection.
     * @throws MessageBrokerConnectionException if the connection is deleted.
     */
    public String getUid() throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            return outPortConnectionBinder.getUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get identifier of the application that owns the port.
     *
     * @return Identifier of the application.
     * @throws MessageBrokerConnectionException if the connection is deleted.
     */
    public String getApplicationId() throws MessageBrokerConnectionException {
        try {
            ensureConnectionIsNotDeleted();
            return outPortConnectionBinder.getApplicationId();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the configuration of the {@link InPort} connected through this connection.
     *
     * @return {@link PortConfiguration} of the connected {@link InPort}.
     * @throws MessageBrokerConnectionException if the connection is deleted.
     */
    public PortConfiguration getConnectedPortConfiguration()
            throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            return outPortConnectionBinder.getConnectedPortConfiguration();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set a listener for receiving state changes of the connection.
     * <p>Also see
     * {@link OutPortConnection#setConnectionStateListener(ConnectionStateListener, Handler)}.</p>
     *
     * @param client  Listener for listening to connection status of the OutPortConnection.
     *                If {@code null}, the previously set connection state listener
     *                will be removed.
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void setConnectionStateListener(@Nullable ConnectionStateListener client)
            throws MessageBrokerConnectionException {
        setConnectionStateListener(client, null);
    }

    /**
     * Set a listener for receiving state changes of the connection.
     *
     * @param client  Listener for listening to connection status of the OutPortConnection.
     *                If {@code null}, the previously set connection state listener
     *                will be removed.
     * @param handler The {@link Handler} the listener callback should be executed by. If handler is
     *                null, the callback will be executed on arbitrary binder thread.
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void setConnectionStateListener(@Nullable ConnectionStateListener client,
                                           @Nullable Handler handler)
            throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            IConnectionStateListener.Stub connectionStateListener
                    = createConnectionStateProxy(client, handler);
            handleResult(outPortConnectionBinder
                                 .setConnectionStateListener(connectionStateListener));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private IConnectionStateListener.Stub createConnectionStateProxy(
            ConnectionStateListener stateListener,
            Handler handler) {
        IConnectionStateListener.Stub connectionStateListener =
                new IConnectionStateListener.Stub() {

                    @Override
                    public void onEnabled() throws RemoteException {
                        if (handler != null) {
                            handler.post(() -> stateListener.onEnabled());
                        } else {
                            stateListener.onEnabled();
                        }
                    }

                    @Override
                    public void onDisabled() throws RemoteException {
                        if (handler != null) {
                            handler.post(() -> stateListener.onDisabled());
                        } else {
                            stateListener.onDisabled();
                        }
                    }

                    @Override
                    public void onDeleted() throws RemoteException {
                        if (handler != null) {
                            handler.post(() -> stateListener.onDeleted());
                        } else {
                            stateListener.onDeleted();
                        }
                    }
                };
        return connectionStateListener;
    }

    private void ensureConnectionIsNotDeleted() throws MessageBrokerConnectionException {
        try {
            if (outPortConnectionBinder == null) {
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                                                                   .Type.CONNECTION_DELETED);
            }

            if (outPortConnectionBinder.isDeleted()) {
                outPortConnectionBinder = null;
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                                                                   .Type.CONNECTION_DELETED);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the connection state of the port.
     *
     * @return connection state of the connection as defined in {@link PortConnectionState}.
     * @throws MessageBrokerConnectionException if the connection handle is deleted.
     */
    public PortConnectionState getConnectionState() throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            return outPortConnectionBinder.getConnectionState();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
