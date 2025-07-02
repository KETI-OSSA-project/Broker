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

/**
 * Interface to receive status changes of a connection between an {@link InPort} and an
 * {@link OutPort}.
 */
public interface ConnectionStateListener {

    /**
     * Called when the connection status changes to {@link  PortConnectionState#ENABLED}.
     */
    void onEnabled();

    /**
     * Called when the connection status changes to {@link  PortConnectionState#DISABLED}.
     */
    void onDisabled();

    /**
     * Called when the connection status changes to {@link  PortConnectionState#DELETED}.
     */
    void onDeleted();
}
