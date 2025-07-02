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

package com.securityandsafetythings.messagebroker.applicationstitching;

import android.annotation.SystemApi;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.securityandsafetythings.messagebroker.ApplicationStitchingResult;
import com.securityandsafetythings.messagebroker.ports.PortConfiguration;

import static java.util.Objects.requireNonNull;

/**
 * Edge class.
 *
 * <p>Represents a connection between an InPort and an OutPort.</p>
 *
 * @hide
 */
@SystemApi
public class Edge {
    private IEdge binder = null;

    /**
     * Constructor.
     *
     * @param binder binder for edge.
     * @hide
     */
    public Edge(@NonNull IEdge binder) {
        this.binder = requireNonNull(binder);
    }

    private static void handleResult(ApplicationStitchingResult result)
            throws ApplicationStitchingException {
        switch (result) {
            case OK:
                return;
            case NOT_OK:
            default:
                throw new ApplicationStitchingException(
                        ApplicationStitchingException.Type.UNKNOWN_ERROR);
        }
    }

    /**
     * Delete the edge.
     *
     * @throws ApplicationStitchingException if there is an error in deleting the edge.
     */
    public void delete() throws ApplicationStitchingException {
        try {
            ensureEdgeIsNotDeleted();
            handleResult(binder.delete());
            // de-referencing references
            binder = null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the unique identifier for this edge.
     *
     * @return Unique identifier for this edge.
     * @throws ApplicationStitchingException if the edge is not active anymore.
     */
    public String getUid() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            return binder.getUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enable the edge.
     *
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public void enable() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            binder.enable();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disable the edge.
     *
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public void disable() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            binder.disable();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the unique identifier of the inPort corresponding to this edge.
     *
     * @return Unique identifier of the inPort corresponding to this edge.
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public String getInPortUid() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            return binder.getInPortUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the unique identifier of the outPort corresponding to this edge.
     *
     * @return Unique identifier of the outPort corresponding to this edge.
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public String getOutPortUid() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            return binder.getOutPortUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the {@link PortConfiguration} of the inPort corresponding to this edge.
     *
     * @return {@link PortConfiguration} of the inPort corresponding to this edge.
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public PortConfiguration getInPortConfiguration() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            return binder.getInPortConfiguration();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the {@link PortConfiguration} of the outPort corresponding to this edge.
     *
     * @return {@link PortConfiguration} of the outPort corresponding to this edge.
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public PortConfiguration getOutPortConfiguration() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            return binder.getOutPortConfiguration();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the state of the edge.
     *
     * @return state of the edge as defined in {@link EdgeState}.
     * @throws ApplicationStitchingException if the edge is already deleted.
     */
    public EdgeState getEdgeState() throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            return binder.getConnectionState();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set a listener for notifications of message traffic on this edge.
     *
     * @param client  {@link MessageFlowListener}
     * @param handler The {@link Handler} the listener callback should be executed by. If handler is
     *                null, the callback will be executed on arbitrary binder thread.
     * @throws ApplicationStitchingException For the types {@link ApplicationStitchingException}
     */
    public void setMessageFlowListener(@Nullable MessageFlowListener client,
                                       @Nullable Handler handler)
            throws ApplicationStitchingException {
        ensureEdgeIsNotDeleted();
        try {
            IMessageFlowListener.Stub messageFlowListenerProxy
                    = createMessageFlowListenerProxy(client, handler);
            handleResult(binder
                    .setMessageFlowListener(messageFlowListenerProxy));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private IMessageFlowListener.Stub createMessageFlowListenerProxy(
            MessageFlowListener stateListener,
            Handler handler) {
        if (stateListener == null) {
            return null;
        }
        IMessageFlowListener.Stub messageFlowListener =
                new IMessageFlowListener.Stub() {

                    @Override
                    public void onMessage() throws RemoteException {
                        if (handler != null) {
                            handler.post(() -> stateListener.onMessage());
                        } else {
                            stateListener.onMessage();
                        }
                    }
                };
        return messageFlowListener;
    }

    private void ensureEdgeIsNotDeleted() throws ApplicationStitchingException {
        try {
            if (binder == null) {
                throw new ApplicationStitchingException(ApplicationStitchingException
                        .Type.EDGE_DELETED);
            }

            if (binder.isDeleted()) {
                binder = null;
                throw new ApplicationStitchingException(ApplicationStitchingException
                        .Type.EDGE_DELETED);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Interface for listening to notifications of message traffic on an edge.
     */
    public interface MessageFlowListener {
        void onMessage();
    }
}
