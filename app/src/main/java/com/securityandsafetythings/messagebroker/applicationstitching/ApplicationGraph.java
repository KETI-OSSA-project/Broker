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
import android.support.annotation.NonNull;
import android.util.Log;
import com.securityandsafetythings.messagebroker.MessageBrokerConnectionException;
import com.securityandsafetythings.messagebroker.ports.InPort;
import com.securityandsafetythings.messagebroker.ports.OutPort;
import com.securityandsafetythings.messagebroker.ports.PortGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Application Graph.
 * <p> Represents an Application graph with Ports (as nodes) and Edges.</p>
 *
 * @hide
 */
@SystemApi
public class ApplicationGraph {
    private static final String TAG = ApplicationGraph.class.getSimpleName();
    private final HashSet<Edge> edges;
    private final HashSet<InPort> inPorts;
    private final HashSet<OutPort> outPorts;
    private final HashSet<PortGroup> portGroups;
    private final HashMap<String, ApplicationBlock> applicationBlocks;

    /**
     * Constructor.
     *
     * @param inPorts           {@link HashSet} of InPorts.
     * @param outPorts          {@link HashSet} of OutPorts.
     * @param edges             {@link HashSet} of Edges.
     * @param portGroups        {@link HashSet} of PortGroups.
     * @param applicationBlocks {@link HashMap} of ApplicationBlocks
     * @hide
     */
    public ApplicationGraph(@NonNull HashSet<InPort> inPorts,
                            @NonNull HashSet<OutPort> outPorts,
                            @NonNull HashSet<Edge> edges,
                            @NonNull HashSet<PortGroup> portGroups,
                            @NonNull HashMap<String, ApplicationBlock> applicationBlocks) {
        this.edges = Objects.requireNonNull(edges);
        this.inPorts = Objects.requireNonNull(inPorts);
        this.outPorts = Objects.requireNonNull(outPorts);
        this.portGroups = Objects.requireNonNull(portGroups);
        this.applicationBlocks = Objects.requireNonNull(applicationBlocks);
    }

    /**
     * Get all application blocks.
     *
     * @return {@link HashMap} of {@link ApplicationBlock} with applicationId as keys.
     */
    public HashMap<String, ApplicationBlock> getApplicationBlocks() {
        return new HashMap<>(applicationBlocks);
    }

    /**
     * Get {@link Edge}s in the Application Graph.
     *
     * @return {@link HashSet} of {@link Edge}s.
     */
    public synchronized HashSet<Edge> getEdges() {
        return new HashSet<>(edges);
    }

    /**
     * Get ApplicationIds of applications having Ports in the Application Graph.
     *
     * @return {@link HashSet} of ApplicationIds.
     */
    public synchronized HashSet<String> getAllApplicationsWithMessagePorts() {
        HashSet<String> applications = new HashSet<>();
        this.inPorts.forEach(inPort -> {
            try {
                applications.add(inPort.getApplicationId());
            } catch (MessageBrokerConnectionException exception) {
                Log.e(TAG, exception.getMessage());
            }
        });
        this.outPorts.forEach(outPort -> {
            try {
                applications.add(outPort.getApplicationId());
            } catch (MessageBrokerConnectionException exception) {
                Log.e(TAG, exception.getMessage());
            }
        });
        return applications;
    }

    /**
     * Get all InPorts of an application.
     *
     * @param applicationId ApplicationId of the application.
     * @return {@link HashSet} of InPorts of the application.
     */
    public synchronized HashSet<InPort> getInPortsByApplication(@NonNull String applicationId) {
        return getInPortsByApplication(applicationId, false);
    }

    /**
     * Get all InPorts of an application.
     *
     * @param applicationId       ApplicationId of the application.
     * @param excludeGroupedPorts {@code true} to exclude ports that belong to a group,
     *                            {@code false} otherwise.
     * @return {@link HashSet} of InPorts of the application.
     */
    public synchronized HashSet<InPort> getInPortsByApplication(@NonNull String applicationId,
                                                                boolean excludeGroupedPorts) {

        Objects.requireNonNull(applicationId);
        HashSet<String> portsToExclude = new HashSet<>();
        if (excludeGroupedPorts) {
            portsToExclude.addAll(getGroupedInPortsOfAnApplication(applicationId));
        }

        return (HashSet<InPort>) this.inPorts.stream().filter(inPort -> {
            try {
                return inPort.getApplicationId().equals(applicationId)
                        && !portsToExclude.contains(inPort.getUid());
            } catch (MessageBrokerConnectionException exception) {
                return false;
            }
        }).collect(Collectors.toSet());
    }

    private HashSet<String> getGroupedInPortsOfAnApplication(String applicationId) {
        HashSet<String> groupedPorts = new HashSet<>();
        for (PortGroup portGroup : portGroups) {
            try {
                if (!applicationId.equals(portGroup.getApplicationId())) {
                    continue;
                }
                groupedPorts.addAll(portGroup.getInPortUids());
            } catch (MessageBrokerConnectionException e) {
                //TODO: improve this
            }
        }
        return groupedPorts;
    }

    /**
     * Get all OutPorts of an application.
     *
     * @param applicationId ApplicationId of the application.
     * @return {@link HashSet} of OutPorts of the application.
     */
    public synchronized HashSet<OutPort> getOutPortsByApplication(@NonNull String applicationId) {
        Objects.requireNonNull(applicationId);
        return getOutPortsByApplication(applicationId, false);
    }

    /**
     * Get all OutPorts of an application.
     *
     * @param applicationId       ApplicationId of the application.
     * @param excludeGroupedPorts {@code true} to exclude ports that belong to a group,
     *                            {@code false} otherwise.
     * @return {@link HashSet} of OutPorts of the application.
     */
    public synchronized HashSet<OutPort> getOutPortsByApplication(@NonNull String applicationId,
                                                                  boolean excludeGroupedPorts) {
        Objects.requireNonNull(applicationId);
        HashSet<String> portsToExclude = new HashSet<>();
        if (excludeGroupedPorts) {
            portsToExclude.addAll(getGroupedOutPorts());
        }

        return (HashSet<OutPort>) this.outPorts.stream().filter(port -> {
            try {
                return port.getApplicationId().equals(applicationId)
                        && !portsToExclude.contains(port.getUid());
            } catch (MessageBrokerConnectionException exception) {
                return false;
            }
        }).collect(Collectors.toSet());
    }

    private HashSet<String> getGroupedOutPorts() {
        HashSet<String> groupedPorts = new HashSet<>();
        for (PortGroup portGroup : portGroups) {
            try {
                groupedPorts.addAll(portGroup.getOutPortUids());
            } catch (MessageBrokerConnectionException e) {
                //TODO: improve this
            }
        }
        return groupedPorts;
    }

    /**
     * Get all InPorts.
     *
     * @return {@link HashSet} of InPorts.
     */
    public synchronized HashSet<InPort> getInPorts() {
        return new HashSet<>(inPorts);
    }

    /**
     * Get all OutPorts.
     *
     * @return {@link HashSet} of OutPorts.
     */
    public synchronized HashSet<OutPort> getOutPorts() {
        return new HashSet<>(outPorts);
    }

    /**
     * Get all PortGroups.
     *
     * @return {@link HashSet} of PortGroups.
     */
    public synchronized HashSet<PortGroup> getPortGroups() {
        return new HashSet<>(portGroups);
    }

    /**
     * Get all PortGroups of an Application.
     *
     * @param applicationId ApplicationId of the application.
     * @return {@link HashSet} of PortGroups.
     */
    public synchronized HashSet<PortGroup> getPortGroupsByApplication(
            @NonNull String applicationId) {
        Objects.requireNonNull(applicationId);
        HashSet<PortGroup> filteredPortGroups = new HashSet<>();

        for (PortGroup portGroup : portGroups) {
            try {
                if (portGroup.getApplicationId().equals(applicationId)) {
                    filteredPortGroups.add(portGroup);
                }
            } catch (MessageBrokerConnectionException e) {
                e.printStackTrace();
            }
        }
        return filteredPortGroups;
    }

    /**
     * Disable all edges.
     */
    public synchronized void disableAllEdges() {
        for (Edge edge : edges) {
            try {
                edge.disable();
            } catch (ApplicationStitchingException e) {
                Log.e(TAG, "Failed to disable the edge.");
            }
        }
    }

    /**
     * Enable all edges.
     */
    public void enableAllEdges() {
        for (Edge edge : edges) {
            try {
                edge.enable();
            } catch (ApplicationStitchingException e) {
                Log.e(TAG, "Failed to enable the edge.");
            }
        }
    }
}
