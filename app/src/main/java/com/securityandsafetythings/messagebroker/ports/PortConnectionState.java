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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Connection state code list.
 *
 * <p>Used for getting the connection status.
 */
public enum PortConnectionState implements Parcelable {
    /**
     * Connection is active and messages can flow.
     */
    ENABLED,
    /**
     * Connection is in disabled state, and no messages are flowing.
     */
    DISABLED,
    /**
     * Connection is deleted.
     */
    DELETED;

    public static final Creator<PortConnectionState> CREATOR =
            new Creator<PortConnectionState>() {
                @Override
                public PortConnectionState createFromParcel(Parcel in) {
                    return PortConnectionState.values()[in.readInt()];
                }

                @Override
                public PortConnectionState[] newArray(int size) {
                    return new PortConnectionState[size];
                }
            };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
