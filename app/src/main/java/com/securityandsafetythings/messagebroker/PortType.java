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

package com.securityandsafetythings.messagebroker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Types of Ports.
 *
 */
public enum PortType implements Parcelable {
    /**
     * Type for {@link com.securityandsafetythings.messagebroker.ports.InPort}.
     */
    INPORT,
    /**
     * Type for {@link com.securityandsafetythings.messagebroker.ports.OutPort}.
     */
    OUTPORT;

    public static final Creator<PortType> CREATOR = new Creator<PortType>() {
        @Override
        public PortType createFromParcel(Parcel in) {
            return parseMessageBrokerResultFrom(in);
        }

        @Override
        public PortType[] newArray(int size) {
            return new PortType[size];
        }
    };

    private static PortType parseMessageBrokerResultFrom(Parcel in) {
        return PortType.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
