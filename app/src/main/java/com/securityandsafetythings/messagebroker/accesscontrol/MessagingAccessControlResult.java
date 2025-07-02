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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Status code list.
 *
 * <p>Used as result of method calls on {@link MessagingAccessControlManager}.
 *
 * @hide
 */
public enum MessagingAccessControlResult implements Parcelable {
    SUCCESS,
    PRINCIPAL_ALREADY_EXISTS,
    PRINCIPAL_DOES_NOT_EXIST,
    CANNOT_REMOVE_UNIVERSAL_GROUP,
    ACCESS_DENIED,
    ACCESS_ALLOWED,
    CANNOT_RESOLVE_RULES_CONFLICT,
    ACCESS_TO_SOME_TOPICS_ARE_INDETERMINABLE,
    NOT_IMPLEMENTED,
    CANNOT_CONFIGURE_APPLICATION;

    public static final Creator<MessagingAccessControlResult> CREATOR =
            new Creator<MessagingAccessControlResult>() {
        @Override
        public MessagingAccessControlResult createFromParcel(Parcel in) {
            return MessagingAccessControlResult.values()[in.readInt()];
        }

        @Override
        public MessagingAccessControlResult[] newArray(int size) {
            return new MessagingAccessControlResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }
}
