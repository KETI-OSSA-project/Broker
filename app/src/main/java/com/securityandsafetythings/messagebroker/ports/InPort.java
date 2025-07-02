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
import com.securityandsafetythings.messagebroker.MessageListenerException;

import java.util.Objects;
import java.util.UUID;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Class that represents an InPort.
 */
public class InPort {
    private IInPort inPortBinder;
    //TODO: unify the session id with the UID to avoid inconsistencies in listener registration.
    private final UUID sessionId = UUID.randomUUID();

    /**
     * Constructor.
     *
     * @param inPortBinder InPort binder instance.
     * @hide
     */
    public InPort(final @NonNull IInPort inPortBinder) {
        this.inPortBinder = requireNonNull(inPortBinder);
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
     * Get name of the port.
     *
     * @return Name of the port.
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public String getName() throws MessageBrokerConnectionException {
        try {
            ensurePortIsNotDeleted();
            return inPortBinder.getName();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set name of the port.
     *
     * @param portName Name of the port.
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public void setName(final @NonNull String portName) throws MessageBrokerConnectionException {
        Objects.requireNonNull(portName);
        try {
            ensurePortIsNotDeleted();
            handleResult(inPortBinder.setName(portName));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get identifier of the application that owns the port.
     *
     * @return Identifier of the application.
     * @throws MessageBrokerConnectionException if the port is deleted.
     * @hide
     */
    public String getApplicationId() throws MessageBrokerConnectionException {
        try {
            ensurePortIsNotDeleted();
            return inPortBinder.getApplicationId();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete the InPort.
     *
     * @throws MessageBrokerConnectionException if the port is already deleted.
     */
    public void delete() throws MessageBrokerConnectionException {
        delete(false);
    }

    /**
     * Delete the InPort along with the persistence information.
     *
     * @param deletePermanently If {@code true}, the decisions made for the connections to the ports
     *                          by integrator will be removed, so that when the port is created
     *                          again integrator need to again repeat the process.
     *                          If {@code false}, the decisions made for the connections to the
     *                          ports by integrator will be persisted, so that when the port is
     *                          created again integrator does not need to again repeat the process.
     * @throws MessageBrokerConnectionException if the port is already deleted.
     *
     * @hide
     */
    public void delete(boolean deletePermanently) throws MessageBrokerConnectionException {
        try {
            ensurePortIsNotDeleted();
            handleResult(inPortBinder.delete(deletePermanently));
            // Killing connection to the service
            inPortBinder = null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the unique identifier for this port.
     *
     * @return Unique identifier for this port.
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public String getUid() throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        try {
            return inPortBinder.getUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the port configuration of the port.
     *
     * @return Port configuration of the port.
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public PortConfiguration getPortConfiguration() throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        try {
            return inPortBinder.getPortConfiguration();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a message listener.
     *
     * @param client  MessageListener
     * @param handler The {@link Handler} the listener callback should be executed by. If handler is
     *                null, the callback will be executed on arbitrary binder thread.
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void addMessageListener(final @NonNull MessageListener client,
                                   final @Nullable Handler handler)
            throws MessageBrokerConnectionException {
        Objects.requireNonNull(client);
        ensurePortIsNotDeleted();
        try {
            IMessageListener.Stub messageListener = createMessageListenerProxy(client, handler);
            handleResult(inPortBinder.addMessageListener(messageListener,
                    Objects.hash(client, sessionId)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a message listener.
     *
     * <p>Also see {@link InPort#addMessageListener(MessageListener, Handler)}.</p>
     *
     * @param client  MessageListener
     * @throws MessageBrokerConnectionException For the types
     *                                          {@link MessageBrokerConnectionException}
     */
    public void addMessageListener(final @NonNull MessageListener client)
            throws MessageBrokerConnectionException {
        addMessageListener(client, null);
    }

    private IMessageListener.Stub createMessageListenerProxy(MessageListener messageListener,
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
     * Remove a previously added message listener {@link MessageListener}.
     *
     * @param client MessageListener on which the remove is being carried out.
     * @throws MessageBrokerConnectionException A {@link MessageListenerException} is thrown if the
     *                                          client was not added before.
     */
    public void removeMessageListener(final @NonNull MessageListener client)
            throws MessageBrokerConnectionException {

        Objects.requireNonNull(client);
        ensurePortIsNotDeleted();
        try {
            handleResult(inPortBinder.removeMessageListener(Objects.hash(client, sessionId)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private IPortConnectionListener.Stub createConnectionListenerProxy(
            InPortConnectionListener connectionListener, Handler handler) {
        if (connectionListener == null) {
            return null;
        }
        IPortConnectionListener.Stub connectionListenerProxy =
                new IPortConnectionListener.Stub() {
                    @Override
                    public void onNewConnection(PortConnectionResponse response)
                            throws RemoteException {
                        InPortConnection connection = response.getInPortConnection();
                        if (handler != null) {
                            handler.post(() -> connectionListener.onNewConnection(connection));
                        } else {
                            connectionListener.onNewConnection(connection);
                        }
                    }

                };
        return connectionListenerProxy;
    }

    /**
     * Set a connection listener to listen to the updates on the connection status of the port.
     *
     * <p>Also see {@link InPort#setConnectionListener(InPortConnectionListener, Handler)}.</p>
     *
     * @param connectionListener Listener for listening to connection status updates of the port.
     *                           If {@code null}, the previously set connection listener
     *                           will be removed.
     * @throws MessageBrokerConnectionException if there is an error in setting up the connection
     *                                          listener.
     */
    public void setConnectionListener(
            final @Nullable InPortConnectionListener connectionListener)
            throws MessageBrokerConnectionException {
        setConnectionListener(connectionListener, null);
    }

    /**
     * Set a connection listener to listen to the updates on the connection status of the port.
     *
     * @param connectionListener Listener for listening to connection status updates of the port.
     *                           If {@code null}, the previously set connection listener
     *                           will be removed.
     * @param handler            The {@link Handler} on whose associated thread the
     *                           listener callbacks are executed. If the handler is null,
     *                           the callbacks are executed on an arbitrary binder thread.
     * @throws MessageBrokerConnectionException if there is an error in setting up the connection
     *                                          listener.
     */
    public void setConnectionListener(
            final @Nullable InPortConnectionListener connectionListener,
            final @Nullable Handler handler)
            throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        IPortConnectionListener.Stub portConnectionListener =
                createConnectionListenerProxy(connectionListener, handler);
        try {
            handleResult(inPortBinder.setConnectionListener(portConnectionListener));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //TODO: Logic for creating a proxy listener, sending it to the service and call the
        // callbacks appropriately when needed (Medium Priority).
    }

    /**
     * Get the connection object with it's uid.
     *
     * @param connectionUid uid of the connection.
     * @return InPortConnection object if the connection with the uid exists, null otherwise.
     * @throws MessageBrokerConnectionException if the port is already deleted.
     */
    public InPortConnection getConnectionWithUid(@NonNull String connectionUid)
            throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        Objects.requireNonNull(connectionUid);
        try {
            PortConnectionResponse portConnectionResponse
                    = inPortBinder.getConnectionWithUid(connectionUid);
            if (portConnectionResponse == null) {
                return null;
            } else {
                return portConnectionResponse.getInPortConnection();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all connections to the port.
     *
     * @return {@link Set} of InPortConnection objects of connections present to the port. If no
     * connections are present returns an empty set.
     * @throws MessageBrokerConnectionException if the port is already deleted.
     */
    public Set<InPortConnection> getAllConnections()
            throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        try {
            return inPortBinder.getAllConnections()
                    .stream()
                    .map(PortConnectionResponse::getInPortConnection)
                    .collect(Collectors.toSet());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensurePortIsNotDeleted() throws MessageBrokerConnectionException {
        try {
            if (inPortBinder == null) {
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.PORT_DELETED);
            }

            if (inPortBinder.isDeleted()) {
                inPortBinder = null;
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.PORT_DELETED);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Interface to receive {@link InPortConnection} object when a new connection is made
     * between this {@link InPort} and another {@link OutPort}.
     */
    public interface InPortConnectionListener {

        /**
         * Called when a new connection is made between this {@link InPort} and another
         * {@link OutPort}.
         *
         * @param connection {@link InPortConnection} object.
         */
        void onNewConnection(InPortConnection connection);
    }

    /**
     * Interface for listening to messages reaching this InPort. The client object implementing the
     * interface can receive the {@link com.securityandsafetythings.messagebroker.ports.Message}
     * sent by other {@link OutPort}s.
     * The callback {@link #onMessage} is triggered when a
     * {@link com.securityandsafetythings.messagebroker.ports.Message} reaches this InPort from
     * another {@link OutPort}.
     */
    public interface MessageListener {
        /**
         * Called when a {@link com.securityandsafetythings.messagebroker.ports.Message} is received
         * in this InPort.
         *
         * @param message contains the new
         *                {@link com.securityandsafetythings.messagebroker.ports.Message}
         */
        void onMessage(Message message);
    }
}
