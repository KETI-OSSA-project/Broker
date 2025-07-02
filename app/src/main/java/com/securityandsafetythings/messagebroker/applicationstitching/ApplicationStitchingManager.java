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
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresPermission;
import com.securityandsafetythings.BoundServiceHelper;
import com.securityandsafetythings.messagebroker.ApplicationStitchingResponse;
import com.securityandsafetythings.messagebroker.ApplicationStitchingResult;
import com.securityandsafetythings.messagebroker.Constants;
import com.securityandsafetythings.messagebroker.IMessagingService;
import com.securityandsafetythings.messagebroker.MessageBrokerResult;
import com.securityandsafetythings.messagebroker.PortGroupResponse;
import com.securityandsafetythings.messagebroker.PortResponse;
import com.securityandsafetythings.messagebroker.ports.InPort;
import com.securityandsafetythings.messagebroker.ports.OutPort;
import com.securityandsafetythings.messagebroker.ports.PortConfiguration;
import com.securityandsafetythings.messagebroker.ports.PortGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.securityandsafetythings.messagebroker.Constants.MESSAGE_BROKER_PACKAGE;
import static com.securityandsafetythings.messagebroker.Constants.MESSAGING_SERVICE;
import static com.securityandsafetythings.messagebroker.Constants.STITCH_MESSAGE_PORTS_PERMISSION;

/**
 * Application interface for stitching applications using the message broker service.
 *
 * @hide
 */
@SystemApi
public class ApplicationStitchingManager implements AutoCloseable {
    private static final ComponentName SERVICE =
            ComponentName.createRelative(MESSAGE_BROKER_PACKAGE,
                    MESSAGING_SERVICE);
    private final BoundServiceHelper<IMessagingService> serviceHelper;

    private ApplicationStitchingManager(@NonNull Context context) {
        Objects.requireNonNull(context);
        serviceHelper = BoundServiceHelper.newInstance(context,
                SERVICE,
                IMessagingService.Stub::asInterface);
    }

    /**
     * Creates an instance.
     *
     * @param context of the caller.
     * @return instance of the manager
     * @throws RuntimeException on connection error (e.g. service does not exist)
     */
    @NonNull
    public static ApplicationStitchingManager getApplicationStitchingManager(
            @NonNull Context context) {
        return new ApplicationStitchingManager(context);
    }

    /**
     * Creates an instance with callbacks.
     *
     * <p>The {@code listener} callbacks are executed on main thread of the app.
     *
     * @param context  of the caller
     * @param listener for service availability changes
     * @return instance of the manager
     * @throws RuntimeException on connection error (e.g. service does not exist)
     */
    @NonNull
    public static ApplicationStitchingManager getConnectedInstance(
            @NonNull Context context, @NonNull MessagingServiceConnectionListener listener) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(listener);
        ApplicationStitchingManager manager = new ApplicationStitchingManager(context);
        manager.setMessagingServiceConnectionListener(listener, null);
        return manager;
    }

    /**
     * Register a callback for service availability state changes.
     *
     * <p>When you register the {@code listener} for the first time, the {@code onConnected}
     * callback is invoked at once, if the manager is already connected to the service.
     *
     * @param listener to receive state changes, or {@code null} to unregister a
     *                 previously registered listener
     * @param handler  on which the callback will be invoked, or {@code null} to use the main thread
     *                 {@link Looper#getMainLooper()}. The value is ignored when {@code listener}
     *                 is {@code null}.
     */
    public void setMessagingServiceConnectionListener(
            @Nullable MessagingServiceConnectionListener listener, @Nullable Handler handler) {
        serviceHelper.setExternalConnectionListener(buildConnectionListener(listener), handler);
    }

    @Nullable
    private BoundServiceHelper.ConnectionListener buildConnectionListener(
            @Nullable MessagingServiceConnectionListener listener) {
        if (listener == null) {
            return null;
        }
        return new BoundServiceHelper.ConnectionListener() {
            @Override
            public void onConnected() {
                listener.onConnected();
            }

            @Override
            public void onDisconnected() {
                listener.onDisconnected();
            }
        };
    }

    /**
     * Same as {@link #close()}.
     */
    public void disconnect() {
        close();
    }

    /**
     * Unbind from the service and cleanup all resources.
     */
    @Override
    public void close() {
        serviceHelper.close();
    }

    /**
     * Get potential matches for a InPort.
     *
     * @param inPortUid Unique identifier of the InPort.
     * @return HashSet of all OutPorts matching to the InPort identified by the unique identifier.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public HashSet<OutPort> getMatchingOutPorts(@NonNull String inPortUid) {
        Objects.requireNonNull(inPortUid);
        HashSet<OutPort> outPorts = new HashSet<>();
        try {
            ApplicationStitchingResponse response
                    = serviceHelper.get().getMatchingOutPorts(inPortUid);
            if (response.getResult() == ApplicationStitchingResult.OK) {
                for (OutPort outPort : response.getOutPorts()) {
                    outPorts.add(outPort);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return outPorts;
    }

    /**
     * Get potential matches for a OutPort.
     *
     * @param outPortUid Unique identifier of the OutPort.
     * @return HashSet of all InPorts matching to the OutPort identified by the outPortUid.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public HashSet<InPort> getMatchingInPorts(@NonNull String outPortUid) {
        Objects.requireNonNull(outPortUid);
        HashSet<InPort> inPorts = new HashSet<>();
        try {
            ApplicationStitchingResponse response
                    = serviceHelper.get().getMatchingInPorts(outPortUid);
            if (response.getResult() == ApplicationStitchingResult.OK) {
                for (InPort inPort : response.getInPorts()) {
                    inPorts.add(inPort);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return inPorts;
    }

    /**
     * Get all InPorts.
     *
     * @return HashSet of all InPorts.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public HashSet<InPort> getAllInPorts() {
        HashSet<InPort> inPorts = new HashSet<>();
        try {
            List<PortResponse> responses = serviceHelper.get().getAllInPorts();

            for (PortResponse response : responses) {
                inPorts.add(response.getInPort());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return inPorts;
    }

    /**
     * Get all OutPorts.
     *
     * @return HashSet of all OutPorts.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public HashSet<OutPort> getAllOutPorts() {
        HashSet<OutPort> outPorts = new HashSet<>();
        try {
            List<PortResponse> responses = serviceHelper.get().getAllOutPorts();

            for (PortResponse response : responses) {
                outPorts.add(response.getOutPort());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return outPorts;
    }

    /**
     * Get all edges.
     *
     * @return HashSet of all edges.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .STITCH_MESSAGE_PORTS</code>.
     */
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public HashSet<Edge> getAllEdges() {
        HashSet<Edge> edges = new HashSet<>();
        try {
            ApplicationStitchingResponse response = serviceHelper.get().getAllEdges();
            if (response.getResult() == ApplicationStitchingResult.OK) {
                for (Edge edge : response.getEdges()) {
                    edges.add(edge);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return edges;
    }

    /**
     * Get handle to the {@link InPort} object filtered by the UID of the port.
     *
     * @param portUid Unique identifier of the port instance being requested.
     * @return InPort identified by the unique identifier.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public InPort getInPortWithUid(@NonNull String portUid) {
        Objects.requireNonNull(portUid);
        try {
            ApplicationStitchingResponse response = serviceHelper.get()
                    .getInPortWithUid(portUid);
            ApplicationStitchingResult result = response.getResult();
            if (result == ApplicationStitchingResult.OK) {
                return response.getInPorts().get(0);
            } else {
                return null;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get handle to the {@link OutPort} object filtered by the UID of the port.
     *
     * @param portUid Unique identifier of the port instance being requested.
     * @return OutPort identified by the unique identifier.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public OutPort getOutPortWithUid(@NonNull String portUid) {
        Objects.requireNonNull(portUid);
        try {
            ApplicationStitchingResponse response = serviceHelper.get()
                    .getOutPortWithUid(portUid);
            ApplicationStitchingResult result = response.getResult();
            if (result == ApplicationStitchingResult.OK) {
                return response.getOutPorts().get(0);
            } else {
                return null;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get handle to the {@link Edge} object filtered by the UID of the edge.
     *
     * @param edgeUid Unique identifier of the edge instance being requested.
     * @return Edge identified by the unique identifier.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .STITCH_MESSAGE_PORTS</code>.
     */
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public Edge getEdgeWithUid(@NonNull String edgeUid) {
        Objects.requireNonNull(edgeUid);
        try {
            ApplicationStitchingResponse response = serviceHelper.get()
                    .getEdgeWithUid(edgeUid);
            ApplicationStitchingResult result = response.getResult();
            if (result == ApplicationStitchingResult.OK) {
                return response.getEdges().get(0);
            } else {
                return null;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an edge between the ports using the default port-matching algorithm.
     *
     * @param inPortUid  uid of the InPort.
     * @param outPortUid uid of the OutPort.
     * @return Edge created between the ports.
     * @throws SecurityException             if the caller doesn't have
     *                                       <code>com.securityandsafetythings.permission
     *                                       .STITCH_MESSAGE_PORTS</code>.
     * @throws ApplicationStitchingException for the following two scenarios: <ul>
     *                                       <li>If an edge between the two ports already exists.
     *                                       </li>
     *                                       <li>If the port configurations of the port do not
     *                                       match.</li>
     *                                       </ul>
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public Edge createEdge(@NonNull String inPortUid, @NonNull String outPortUid)
            throws ApplicationStitchingException {
        Objects.requireNonNull(inPortUid);
        Objects.requireNonNull(outPortUid);
        try {
            ApplicationStitchingResponse response
                    = serviceHelper.get().createEdge(inPortUid, outPortUid);
            switch (response.getResult()) {
                case EDGE_ALREADY_EXISTS:
                    throw new ApplicationStitchingException(
                            ApplicationStitchingException.Type.EDGE_ALREADY_EXISTS);
                case UNMATCHED_PORT_CONFIGURATIONS:
                    throw new ApplicationStitchingException(
                            ApplicationStitchingException.Type.UNMATCHED_PORT_CONFIGURATIONS);
                case SELF_CONNECTION:
                    throw new ApplicationStitchingException(
                            ApplicationStitchingException.Type.SELF_CONNECTION);
                case OK:
                    // Get edges
                    return response.getEdges().get(0);
                case NOT_OK:
                default:
                    throw new ApplicationStitchingException(
                            ApplicationStitchingException.Type.UNKNOWN_ERROR);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get {@link PortConfiguration}s of all {@link InPort}s.
     *
     * @return PortConfigurations of InPorts.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public List<PortConfiguration> getAllInPortConfigurations() {
        try {
            return serviceHelper.get().getAllInPortConfigurations();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get {@link PortConfiguration}s of all {@link OutPort}s.
     *
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORTS_INFO_PERMISSION)
    public List<PortConfiguration> getAllOutPortConfigurations() {
        try {
            return serviceHelper.get().getAllOutPortConfigurations();

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all port groups.
     *
     * @return HashSet of all Port groups.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORT_GROUPS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORT_GROUPS_INFO_PERMISSION)
    public HashSet<PortGroup> getAllPortGroups() {
        HashSet<PortGroup> portGroups = new HashSet<>();
        try {
            List<PortGroupResponse> portGroupResponses = serviceHelper.get().getAllPortGroups();

            for (PortGroupResponse portGroupResponse : portGroupResponses) {
                portGroups.add(portGroupResponse.getPortGroup());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return portGroups;
    }

    /**
     * Get all application blocks.
     *
     * @return HashSet of all {@link ApplicationBlock} objects.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .STITCH_MESSAGE_PORTS</code>.
     */
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public HashSet<ApplicationBlock> getAllApplicationBlocks() {
        HashSet<ApplicationBlock> applicationBlocks = new HashSet<>();
        try {
            List<ApplicationStitchingResponse> applicationStitchingResponses =
                    serviceHelper.get().getAllApplicationBlocks();

            for (ApplicationStitchingResponse applicationStitchingResponse :
                    applicationStitchingResponses) {
                applicationBlocks.add(applicationStitchingResponse.getApplicationBlock());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return applicationBlocks;
    }

    /**
     * Get a port group with its Uid.
     *
     * @param portGroupUid Uid of the port group.
     * @return PortGroup object.
     * @throws SecurityException if the caller doesn't have
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORT_GROUPS_INFO</code>.
     */
    @RequiresPermission(Constants.GET_MESSAGE_PORT_GROUPS_INFO_PERMISSION)
    public PortGroup getPortGroupWithUid(@NonNull final String portGroupUid) {
        Objects.requireNonNull(portGroupUid);
        try {
            PortGroupResponse portGroupResponse
                    = serviceHelper.get().getPortGroupWithUid(portGroupUid);
            return (portGroupResponse == null) ? null : portGroupResponse.getPortGroup();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get application graph.
     *
     * @return ApplicationGraph.
     * @throws SecurityException if the caller doesn't have any of the permissions:
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORTS_INFO</code>,
     *                           <code>com.securityandsafetythings.permission
     *                           .GET_MESSAGE_PORT_GROUPS_INFO</code>,
     *                           <code>com.securityandsafetythings.permission
     *                           .STITCH_MESSAGE_PORTS</code>.
     */
    @RequiresPermission(allOf = {Constants.GET_MESSAGE_PORTS_INFO_PERMISSION,
            Constants.GET_MESSAGE_PORT_GROUPS_INFO_PERMISSION,
            STITCH_MESSAGE_PORTS_PERMISSION})
    public ApplicationGraph getApplicationGraph() {
        HashSet<Edge> edges = new HashSet<>();
        HashSet<InPort> inPorts = new HashSet<>();
        HashSet<OutPort> outPorts = new HashSet<>();
        HashSet<PortGroup> portGroups = new HashSet<>();
        HashMap<String, ApplicationBlock> applicationBlocks = new HashMap<>();
        try {
            ApplicationStitchingResponse response = serviceHelper.get().getCurrentState();
            if (response.getResult() == ApplicationStitchingResult.OK) {
                // Get edges
                edges.addAll(response.getEdges());

                // Get InPorts
                inPorts.addAll(response.getInPorts());

                // Get OutPorts
                outPorts.addAll(response.getOutPorts());

                portGroups.addAll(this.getAllPortGroups());
            }

            List<ApplicationStitchingResponse> responses
                    = serviceHelper.get().getAllApplicationBlocks();
            for (ApplicationStitchingResponse applicationStitchingResponse :
                    responses) {
                if (applicationStitchingResponse.getResult()
                        == ApplicationStitchingResult.OK) {
                    applicationBlocks.put(applicationStitchingResponse.getApplicationId(),
                            applicationStitchingResponse.getApplicationBlock());
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return new ApplicationGraph(inPorts, outPorts, edges, portGroups, applicationBlocks);
    }

    /**
     * Add an attribute to an application.
     *
     * @param applicationId applicationId to which the attribute is being added.
     * @param key           Key of the attribute being added.
     * @param value         Value of the attribute being added.
     * @throws ApplicationStitchingException For the types {@link ApplicationStitchingException}
     * @throws SecurityException             if the caller doesn't have
     *                                       <code>com.securityandsafetythings.permission
     *                                       .STITCH_MESSAGE_PORTS</code>.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public void addAttribute(@NonNull String applicationId, @NonNull String key,
                             @NonNull String value)
            throws ApplicationStitchingException {
        Objects.requireNonNull(applicationId);
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        try {
            ApplicationStitchingResult result = serviceHelper.get()
                    .addAttribute(applicationId, key, value);
            if (result != ApplicationStitchingResult.OK) {
                throw new ApplicationStitchingException(
                        ApplicationStitchingException.Type.APPLICATION_NOT_FOUND);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove an attribute for an application block.
     *
     * @param applicationId applicationId to which the attribute is being added.
     * @param key           Key of the attribute being added.
     * @throws ApplicationStitchingException For the types {@link ApplicationStitchingException}
     * @throws SecurityException             if the caller doesn't have
     *                                       <code>com.securityandsafetythings.permission
     *                                       .STITCH_MESSAGE_PORTS</code>.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public void removeAttribute(@NonNull String applicationId, @NonNull String key)
            throws ApplicationStitchingException {
        Objects.requireNonNull(applicationId);
        Objects.requireNonNull(key);
        try {
            ApplicationStitchingResult result = serviceHelper.get()
                    .removeAttribute(applicationId, key);
            if (result != ApplicationStitchingResult.OK) {
                throw new ApplicationStitchingException(
                        ApplicationStitchingException.Type.APPLICATION_NOT_FOUND);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the attributes of application with given applicationId.
     *
     * @return Attributes of the application that holds the application block. Empty {@link HashMap}
     * is returned if no attributes exist for the given applicationId
     * @throws ApplicationStitchingException if some error occurs when fetching the data.
     * @throws SecurityException             if the caller doesn't have
     *                                       <code>com.securityandsafetythings.permission
     *                                       .STITCH_MESSAGE_PORTS</code>.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public HashMap<String, String> getAttributes(@NonNull String applicationId)
            throws ApplicationStitchingException {
        Objects.requireNonNull(applicationId);
        try {
            return (HashMap<String, String>) serviceHelper.get().getAttributes(applicationId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clean-up the database remnants of ports and edges.
     * <p>If there are database entries for the application with the given applicationId, the
     * entries will be deleted. If {@code null}, all the entries in the database will be
     * deleted.</p>
     *
     * <p>After, using the API, restart of the application or the camera might be needed.</p>
     *
     * @param applicationId ApplicationId of the application. If there are database entries for the
     *                     application with the given applicationId, the entries will be deleted.
     *                     If {@code null}, all the entries in the database will be deleted.
     *
     */
    @RequiresPermission(Constants.STITCH_MESSAGE_PORTS_PERMISSION)
    public void cleanDatabaseRemnants(String applicationId) {
        try {
            MessageBrokerResult result = serviceHelper.get().cleanDatabaseRemnants(applicationId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Listener to handle availability state changes of the service.
     *
     * <p>The callback {@code onConnected} is called, when the service is available and ready
     * to be used. The callback {@code onDisconnected} is called, when the service has died. Any
     * calls may throw a {@link android.os.DeadObjectException} exception. The manager will
     * reconnect automatically.
     **/
    public interface MessagingServiceConnectionListener {
        /**
         * Remote service is available and can be used.
         */
        void onConnected();

        /**
         * Remote service has died. Do not use it anymore.
         */
        void onDisconnected();
    }
}
