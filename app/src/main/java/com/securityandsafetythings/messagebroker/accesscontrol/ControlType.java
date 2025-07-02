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
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum for Control Type.
 *
 * @hide
 */
@SystemApi
public enum ControlType implements Parcelable {
    ALLOW,
    DENY;

    public static final Creator<ControlType> CREATOR = new Creator<ControlType>() {
        @Override
        public ControlType createFromParcel(Parcel in) {
            return ControlType.values()[in.readInt()];
        }

        @Override
        public ControlType[] newArray(int size) {
            return new ControlType[size];
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
