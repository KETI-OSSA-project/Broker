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

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.securityandsafetythings.messagebroker.MessageBrokerConnectionException;
import com.securityandsafetythings.messagebroker.MessageBrokerResult;
import com.securityandsafetythings.messagebroker.PortResponse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * PortGroup class.
 *
 * @hide
 */
@SystemApi
public class PortGroup {
    private IPortGroup portGroupBinder = null;

    /**
     * Constructor.
     *
     * @param portGroupBinder binder for PortGroup.
     *
     * @hide
     */
    public PortGroup(@NonNull IPortGroup portGroupBinder) {
        this.portGroupBinder = requireNonNull(portGroupBinder);
    }

    private static void handleResult(MessageBrokerResult result)
            throws MessageBrokerConnectionException {
        switch (result) {
            case OK:
                return;
            case INVALID_PORT_CONFIGURATION:
                throw new MessageBrokerConnectionException(
                        MessageBrokerConnectionException.Type.INVALID_PORT_CONFIGURATION);
            case NOT_OK:
            default:
                throw new MessageBrokerConnectionException(
                        MessageBrokerConnectionException.Type.UNKNOWN_ERROR);
        }
    }

    /**
     * Deletes the port Group and all the ports that are part of it.
     *
     * @param deletePermanently If {@code true}, the decisions made for the connections to the ports
     *                         by integrator will be removed, so that when the port is created
     *                         again integrator need to again repeat the process.
     *                         If {@code false}, the decisions made for the connections to the ports
     *                         by integrator will be persisted, so that when the port is created
     *                         again integrator does not need to again repeat the process.
     * @throws MessageBrokerConnectionException if there is an error in deleting the configuration.
     */
    public void delete(boolean deletePermanently) throws MessageBrokerConnectionException {
        try {
            ensurePortGroupIsNotDeleted();
            handleResult(portGroupBinder.delete(deletePermanently));
            // Killing connection to the service
            portGroupBinder = null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the unique identifier for this port group.
     *
     * @return Unique identifier for this port group.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public String getUid() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            return portGroupBinder.getUid();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enable the PortGroup.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public void enable() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            handleResult(portGroupBinder.enable());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disable the PortGroup.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public void disable() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            handleResult(portGroupBinder.disable());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set name for the portGroup.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public void setName(@NonNull String name) throws MessageBrokerConnectionException {
        requireNonNull(name);
        ensurePortGroupIsNotDeleted();
        try {
            handleResult(portGroupBinder.setName(name));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get name of the portGroup.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public String getName() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            return portGroupBinder.getName();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an inPort belonging to the group.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public InPort createInPort(@NonNull PortConfiguration portConfiguration)
            throws MessageBrokerConnectionException {
        requireNonNull(portConfiguration);
        ensurePortGroupIsNotDeleted();
        try {
            PortResponse portResponse = portGroupBinder.createInPort(portConfiguration);
            if (MessageBrokerResult.OK.equals(portResponse.getResult())) {
                return portResponse.getInPort();
            } else {
                handleResult(portResponse.getResult());
                return null;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an OutPort belonging to the group.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public OutPort createOutPort(@NonNull PortConfiguration portConfiguration)
            throws MessageBrokerConnectionException {
        requireNonNull(portConfiguration);
        ensurePortGroupIsNotDeleted();
        try {
            PortResponse portResponse = portGroupBinder.createOutPort(portConfiguration);
            if (MessageBrokerResult.OK.equals(portResponse.getResult())) {
                return portResponse.getOutPort();
            } else {
                handleResult(portResponse.getResult());
                return null;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete an inPort belonging to the group.
     *
     * @param inPort InPort to be deleted.
     * @param deletePermanently If {@code true}, the decisions made for the connections to the port
     *                         by integrator will be removed, so that when the port is created
     *                         again integrator need to again repeat the process.
     *                         If {@code false}, the decisions made for the connections to the port
     *                         by integrator will be persisted, so that when the port is created
     *                         again integrator does not need to again repeat the process.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public void deleteInPort(@NonNull InPort inPort,
                             boolean deletePermanently) throws MessageBrokerConnectionException {
        requireNonNull(inPort);
        ensurePortGroupIsNotDeleted();
        try {
            handleResult(portGroupBinder.deleteInPort(inPort.getUid(), deletePermanently));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete an outPort belonging to the group.
     *
     * @param outPort OutPort to be deleted.
     * @param deletePermanently If {@code true}, the decisions made for the connections to the port
     *                         by integrator will be removed, so that when the port is created
     *                         again integrator need to again repeat the process.
     *                         If {@code false}, the decisions made for the connections to the port
     *                         by integrator will be persisted, so that when the port is created
     *                         again integrator does not need to again repeat the process.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public void deleteOutPort(@NonNull OutPort outPort,
                              boolean deletePermanently) throws MessageBrokerConnectionException {
        requireNonNull(outPort);
        ensurePortGroupIsNotDeleted();
        try {
            handleResult(portGroupBinder.deleteOutPort(outPort.getUid(), deletePermanently));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the set of inPorts belonging to this group.
     *
     * @return Set of inPorts part of this group. Empty set if there are no inPorts belonging
     * to this group.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public Set<InPort> getInPorts() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        HashSet<InPort> inPorts = new HashSet<>();
        try {
            List<PortResponse> responses = portGroupBinder.getInPorts();

            for (PortResponse response : responses) {
                inPorts.add(response.getInPort());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return inPorts;
    }

    /**
     * Get the set of outPorts belonging to this group.
     *
     * @return Set of outPorts part of this group. Empty set if there are no outPorts belonging
     * to this group.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public Set<OutPort> getOutPorts() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        HashSet<OutPort> outPorts = new HashSet<>();
        try {
            List<PortResponse> responses = portGroupBinder.getOutPorts();

            for (PortResponse response : responses) {
                outPorts.add(response.getOutPort());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return outPorts;
    }

    /**
     * Get the Uids of the InPorts belonging to this group.
     *
     * @return List of InPort uuids.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public Set<String> getInPortUids() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            return new HashSet<>(portGroupBinder.getInPortUids());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the Uids of the OutPorts belonging to this group.
     *
     * @return Set of OutPort uuids.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public Set<String> getOutPortUids() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            return new HashSet<>(portGroupBinder.getOutPortUids());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add an attribute to this group.
     *
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public void addAttribute(@NonNull String key, @NonNull String value)
            throws MessageBrokerConnectionException {
        requireNonNull(key);
        requireNonNull(value);
        ensurePortGroupIsNotDeleted();
        try {
            portGroupBinder.addAttribute(key, value);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove an attribute of this group.
     *
     * @return True if the attribute is successfully removed. False if no such attribute exists.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public boolean removeAttribute(@NonNull String key) throws MessageBrokerConnectionException {
        requireNonNull(key);
        ensurePortGroupIsNotDeleted();
        try {
            return portGroupBinder.removeAttribute(key);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the ApplicationId of the application that created this port group.
     *
     * @return ApplicationId of the application that created this port group.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public String getApplicationId() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            return portGroupBinder.getApplicationId();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the attributes of this port group.
     *
     * @return Package name of the application that created this port group.
     * @throws MessageBrokerConnectionException if the port group is already deleted.
     */
    public HashMap<String, String> getAttributes() throws MessageBrokerConnectionException {
        ensurePortGroupIsNotDeleted();
        try {
            return (HashMap<String, String>) portGroupBinder.getAttributes();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensurePortGroupIsNotDeleted() throws MessageBrokerConnectionException {
        try {
            if (portGroupBinder == null) {
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.PORT_GROUP_DELETED);
            }

            if (portGroupBinder.isDeleted()) {
                portGroupBinder = null;
                throw new MessageBrokerConnectionException(MessageBrokerConnectionException
                        .Type.PORT_GROUP_DELETED);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
