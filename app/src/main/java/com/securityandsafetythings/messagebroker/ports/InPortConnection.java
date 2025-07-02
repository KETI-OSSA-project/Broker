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
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * InPortConnection class.
 */
public class InPortConnection {
    private IInPortConnection inPortConnectionBinder;

    //TODO: unify the session id with the UID to avoid inconsistencies in listener registration.
    private final UUID sessionId = UUID.randomUUID();

    /**
     * Constructor.
     *
     * @param inPortConnection InPortConnection binder instance.
     * @hide
     */
    InPortConnection(@NonNull IInPortConnection inPortConnection) {
        this.inPortConnectionBinder = requireNonNull(inPortConnection);
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
     * Get the unique identifier for this connection.
     *
     * @return Unique identifier for this connection.
     * @throws MessageBrokerConnectionException if the connection is deleted.
     */
    public String getUid() throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            return inPortConnectionBinder.getUid();
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
            return inPortConnectionBinder.getApplicationId();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a listener for receiving messages.
     *
     * <p>Also see {@link InPortConnection#addMessageListener(InPort.MessageListener, Handler)}.</p>
     *
     * @param client  MessageListener message listener.
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void addMessageListener(@NonNull InPort.MessageListener client)
            throws MessageBrokerConnectionException {
        addMessageListener(client, null);
    }

    /**
     * Add a listener for receiving messages.
     *
     * @param client  MessageListener message listener.
     * @param handler The {@link Handler} the listener callback should be executed by. If handler is
     *                null, the callback will be executed on arbitrary binder thread.
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void addMessageListener(@NonNull InPort.MessageListener client,
                                   @Nullable Handler handler)
            throws MessageBrokerConnectionException {
        requireNonNull(client);
        ensureConnectionIsNotDeleted();
        try {
            IMessageListener.Stub messageListener = createMessageListenerProxy(client, handler);
            handleResult(inPortConnectionBinder.addMessageListener(messageListener,
                    Objects.hash(client, sessionId)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private IMessageListener.Stub createMessageListenerProxy(InPort.MessageListener messageListener,
                                                             Handler handler) {
        IMessageListener.Stub messageListenerProxy =
                new IMessageListener.Stub() {

                    @Override
                    public void onMessage(Message message) throws RemoteException {
                        if (handler != null) {
                            handler.post(() -> messageListener.onMessage(message));
                        } else {
                            messageListener.onMessage(message);
                        }
                    }
                };
        return messageListenerProxy;
    }

    /**
     * Remove previously added message listener {@link InPort.MessageListener}.
     *
     * @param client MessageListener on which the remove is being carried out.
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void removeMessageListener(@NonNull InPort.MessageListener client)
            throws MessageBrokerConnectionException {
        requireNonNull(client);
        ensureConnectionIsNotDeleted();
        try {
            handleResult(inPortConnectionBinder
                    .removeMessageListener(Objects.hash(client, sessionId)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the configuration of the {@link OutPort} connected through this connection.
     *
     * @return {@link PortConfiguration} of the connected {@link OutPort}.
     * @throws MessageBrokerConnectionException if the connection is deleted.
     */
    public PortConfiguration getConnectedPortConfiguration()
            throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            return inPortConnectionBinder.getConnectedPortConfiguration();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the connection state of the port.
     *
     * @return connection state as defined in {@link PortConnectionState}.
     * @throws MessageBrokerConnectionException if the connection handle is deleted.
     */
    public PortConnectionState getConnectionState() throws MessageBrokerConnectionException {
        ensureConnectionIsNotDeleted();
        try {
            return inPortConnectionBinder.getConnectionState();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set a listener for receiving state changes of the connection.
     *
     * <p>Also see
     * {@link InPortConnection#setConnectionStateListener(ConnectionStateListener, Handler)}.</p>
     *
     * @param client  Listener for listening to connection status of the InPortConnection.
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
     * @param client  Listener for listening to connection status of the InPortConnection.
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
            handleResult(inPortConnectionBinder
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
            if (inPortConnectionBinder == null) {
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.CONNECTION_DELETED);
            }

            if (inPortConnectionBinder.isDeleted()) {
                inPortConnectionBinder = null;
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.CONNECTION_DELETED);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
