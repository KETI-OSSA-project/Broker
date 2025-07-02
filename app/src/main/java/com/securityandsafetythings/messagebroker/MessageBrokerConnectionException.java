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
/* 포트 연결 작업 관련 오류 상황 처리 메서드 */
package com.securityandsafetythings.messagebroker;

import android.annotation.SystemApi;

/**
 * Provides information on various errors when trying to create or operate on Message Ports.
 *
 */
public class MessageBrokerConnectionException extends MessageBrokerException {
    private final Type type;

    public MessageBrokerConnectionException(Type type) {
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
     * Error type of a {@link MessageBrokerConnectionException}.
     */

    /* 발생할 수 있는 구체적인 오류 -> enum type 정의 */
    public enum Type {
        PORT_DELETED("The port is already deleted. " +
                "Cannot process the request"),
        CONNECTION_DELETED("The connection is already deleted. " +
                                      "Cannot process the request"),
        /**
         * Type for acknowledging port group deletion.
         * @hide
         */
        @SystemApi
        PORT_GROUP_DELETED("The port group is already deleted. " +
                "Cannot process the request"),
        INVALID_PORT_CONFIGURATION("The provided PortConfiguration is invalid."),
        DUPLICATE_PORT_CONFIGURATION("The PortConfiguration is already used in a Port."),
        UNKNOWN_ERROR("Failed to process the request due to unknown remote error.");
        private final String message;

        Type(String message) {
            this.message = message;
        }
    }
}
