/*
 * Copyright 2020 by Security and Safety Things GmbH
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

package com.securityandsafetythings.messagebroker.accesscontrol;

import android.annotation.SystemApi;

/**
 * Gives information about errors.
 * Possible exception types can be found in {@link Type}.
 *
 * @hide
 */
@SystemApi
public class MessagingAccessControlException extends Exception {
    private final MessagingAccessControlException.Type type;

    MessagingAccessControlException(final MessagingAccessControlException.Type type) {
        super(type.message);
        this.type = type;
    }

    /**
     * Get the error type.
     *
     * @return indicating the type of failure.
     */
    public MessagingAccessControlException.Type getType() {
        return type;
    }

    /**
     * Error type of a {@link MessagingAccessControlException}.
     */
    public enum Type {
        PRINCIPAL_ALREADY_EXISTS("The principal already exists in messaging access control."),
        PRINCIPAL_DOES_NOT_EXIST("The principal is absent in messaging access control."),
        RULE_ALREADY_EXISTS("The rule already exists in the Principal."),
        RULE_DOES_NOT_EXIST("The rule is absent in the Principal."),
        CANNOT_REMOVE_UNIVERSAL_GROUP("A universal group principal cannot be" +
                " removed, it can only be updated."),
        CONFLICTING_RULE("This rule is conflicting with another rule in the principal."),
        CANNOT_RESOLVE_RULES_CONFLICT("There are conflicting rules in the messaging access" +
                " control applicable to this access."),
        ACCESS_TO_SOME_TOPICS_ARE_INDETERMINABLE("Access to some of the topics" +
                " in this access cannot be determined."),
        CANNOT_CONFIGURE_APPLICATION("No Messaging Access Control configurations" +
                " are allowed for this application.");

        private final String message;

        public String getMessage() {
            return this.message;
        }

        Type(String message) {
            this.message = message;
        }
    }
}
