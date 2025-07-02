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
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Class that represents an OutPort.
 */
public class OutPort {
    private IOutPort outPortBinder = null;

    /**
     * Constructor.
     *
     * @param outPortBinder OutPort binder instance.
     * @hide
     */
    public OutPort(@NonNull IOutPort outPortBinder) {
        this.outPortBinder = requireNonNull(outPortBinder);
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
     * Send message through this OutPort.
     *
     * @throws MessageBrokerConnectionException if the port is already deleted.
     */
    public void send(final @NonNull Message message) throws MessageBrokerConnectionException {
        Objects.requireNonNull(message);
        ensurePortIsNotDeleted();
        try {
            handleResult(outPortBinder.send(message));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete the OutPort.
     *
     * @throws MessageBrokerConnectionException if the port is already deleted.
     */
    public void delete() throws MessageBrokerConnectionException {
        delete(false);
    }

    /**
     * Delete the OutPort along with the persistence information.
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
            handleResult(outPortBinder.delete(deletePermanently));
            // Killing connection to the service
            outPortBinder = null;
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
            return outPortBinder.getPortConfiguration();
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
            return outPortBinder.getUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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
            return outPortBinder.getName();
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
        try {
            Objects.requireNonNull(portName);
            ensurePortIsNotDeleted();
            handleResult(outPortBinder.setName(portName));
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
            return outPortBinder.getApplicationId();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private IPortConnectionListener.Stub createConnectionListenerProxy(
            OutPortConnectionListener connectionListener, Handler handler) {
        if (connectionListener == null) {
            return null;
        }
        IPortConnectionListener.Stub connectionListenerProxy =
                new IPortConnectionListener.Stub() {

                    @Override
                    public void onNewConnection(PortConnectionResponse connectionResponse)
                            throws RemoteException {
                        OutPortConnection connection = connectionResponse.getOutPortConnection();
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
     * <p>Also see {@link OutPort#setConnectionListener(OutPortConnectionListener, Handler)}.</p>
     *
     * @param connectionListener Listener for listening to connection status updates of the port.
     *                           If {@code null}, the previously set connection listener
     *                           will be removed.
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public void setConnectionListener(
            @Nullable OutPortConnectionListener connectionListener)
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
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public void setConnectionListener(
            @Nullable OutPortConnectionListener connectionListener,
            @Nullable Handler handler)
            throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        IPortConnectionListener.Stub portConnectionListener =
                createConnectionListenerProxy(connectionListener, handler);
        try {
            handleResult(outPortBinder.setConnectionListener(portConnectionListener));
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
     * @return OutPortConnection object if the connection with the uid exists, null otherwise.
     * @throws MessageBrokerConnectionException if the port is already deleted.
     */
    public OutPortConnection getConnectionWithUid(@NonNull String connectionUid)
            throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        Objects.requireNonNull(connectionUid);
        try {
            PortConnectionResponse portConnectionResponse
                    = outPortBinder.getConnectionWithUid(connectionUid);
            if (portConnectionResponse == null) {
                return null;
            } else {
                return portConnectionResponse.getOutPortConnection();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Interface to receive {@link OutPortConnection} object when a new connection is made
     * between this {@link OutPort} and another {@link InPort}.
     */
    public interface OutPortConnectionListener {

        /**
         * Called when a new connection is made between this {@link OutPort} and another
         * {@link InPort}.
         *
         * @param connection {@link OutPortConnection} object.
         */
        void onNewConnection(OutPortConnection connection);
    }

    /**
     * Get all connections in the port.
     *
     * @return {@link Set} of OutPortConnection objects of connections present in the port. If no
     * connections are present returns an empty set.
     * @throws MessageBrokerConnectionException if the port is deleted.
     */
    public Set<OutPortConnection> getAllConnections()
            throws MessageBrokerConnectionException {
        ensurePortIsNotDeleted();
        try {
            return outPortBinder.getAllConnections()
                    .stream()
                    .map(PortConnectionResponse::getOutPortConnection)
                    .collect(Collectors.toSet());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensurePortIsNotDeleted() throws MessageBrokerConnectionException {
        try {
            if (outPortBinder == null) {
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.PORT_DELETED);
            }

            if (outPortBinder.isDeleted()) {
                outPortBinder = null;
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.PORT_DELETED);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
