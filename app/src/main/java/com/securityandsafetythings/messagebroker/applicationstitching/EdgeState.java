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
import android.os.Parcel;
import android.os.Parcelable;

/**
 * List of status code for edge.
 *
 * <p>Used for getting the edge status.
 *
 * @hide
 */
@SystemApi
public enum EdgeState implements Parcelable {
    /**
     * Edge is enabled and messages are flowing.
     */
    ENABLED,
    /**
     * Edge is disabled and messages are not flowing.
     */
    DISABLED,
    /**
     * The edge is deleted.
     */
    DELETED;

    public static final Creator<EdgeState> CREATOR =
            new Creator<EdgeState>() {
                @Override
                public EdgeState createFromParcel(Parcel in) {
                    return EdgeState.values()[in.readInt()];
                }

                @Override
                public EdgeState[] newArray(int size) {
                    return new EdgeState[size];
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
