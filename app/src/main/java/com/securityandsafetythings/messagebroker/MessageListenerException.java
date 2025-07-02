/*
 * Copyright 2019 by Security and Safety Things GmbH
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


package com.securityandsafetythings.messagebroker;

/**
 * Gives information about errors.
 * Possible exception cases can be found in {@link Type}.
 */
public class MessageListenerException extends MessageBrokerException {
    private final Type type;

    MessageListenerException(Type type) {
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
     * Error type of an {@link MessageListenerException}.
     */
    public enum Type {
        LISTENER_NOT_SUBSCRIBED("Listener not found in the subscription list");

        private final String message;

        Type(String message) {
            this.message = message;
        }
    }
}
