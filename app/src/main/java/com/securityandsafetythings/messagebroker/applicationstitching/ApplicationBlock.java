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
import com.securityandsafetythings.messagebroker.ports.InPort;
import com.securityandsafetythings.messagebroker.ports.OutPort;

import java.util.HashMap;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;

/**
 * Application Block.
 * <p> Represents an Application Block with Ports and attribute configurations.</p>
 *
 * @hide
 */
@SystemApi
public class ApplicationBlock {
    private final String applicationId;
    private final HashMap<String, String> attributeConfiguration;
    private final HashSet<InPort> inPorts;
    private final HashSet<OutPort> outPorts;

    public ApplicationBlock(@NonNull String applicationId,
                            @NonNull HashMap<String, String> attributeConfiguration,
                            @NonNull HashSet<InPort> inPorts,
                            @NonNull HashSet<OutPort> outPorts) {
        this.attributeConfiguration = requireNonNull(attributeConfiguration);
        this.applicationId = requireNonNull(applicationId);
        this.inPorts = requireNonNull(inPorts);
        this.outPorts = requireNonNull(outPorts);
    }

    public String getApplicationId() {
        return applicationId;
    }

    public HashMap<String, String> getAttributeConfiguration() {
        return attributeConfiguration;
    }

    public String getAttribute(String key) {
        return attributeConfiguration.get(key);
    }

    public HashSet<InPort> getInPorts() {
        return inPorts;
    }

    public HashSet<OutPort> getOutPorts() {
        return outPorts;
    }
}
