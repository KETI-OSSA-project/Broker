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

/**
 * Provides information on various errors while trying to stitch Message Ports.
 *
 * @hide
 */
@SystemApi
public class ApplicationStitchingException extends Exception {
    private final Type type;

    public ApplicationStitchingException(Type type) {
        super(type.message);
        this.type = type;
    }

    /**
     * Get the error type.
     *
     * @return indicating the type of failure
     */
    public Type getType() {
        return type;
    }

    /**
     * Error type of {@link ApplicationStitchingException}.
     */

    public enum Type {
        EDGE_DELETED("The edge is already deleted. " +
                                      "Cannot process the request."),
        EDGE_ALREADY_EXISTS("An edge already exists between the given ports"),
        UNMATCHED_PORT_CONFIGURATIONS("The port configurations of the given ports are" +
                " not matching."),
        SELF_CONNECTION("Cannot create edge between ports belonging to this package."),
        APPLICATION_NOT_FOUND("Application with provided applicationId is not found"),
        UNKNOWN_ERROR("Failed to process the request due to unknown remote error.");
        private final String message;

        Type(String message) {
            this.message = message;
        }
    }
}
