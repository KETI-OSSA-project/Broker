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
import com.securityandsafetythings.messagebroker.applicationstitching.ApplicationStitchingManager;

/**
 * Status code list.
 *
 * <p>Used as result of some method calls on {@link ApplicationStitchingManager}.
 *
 * @hide
 */
public enum ApplicationStitchingResult implements Parcelable {
    /**
     * Request processed successfully.
     */
    OK,
    /**
     * Edge already exists between the ports.
     */
    EDGE_ALREADY_EXISTS,
    /**
     * Port Configurations does not match.
     */
    UNMATCHED_PORT_CONFIGURATIONS,
    /**
     * Port Configurations belong to the same package which are blocked for self connection.
     */
    SELF_CONNECTION,
    /**
     * Failed to due unknown error.
     */
    NOT_OK;

    public static final Creator<ApplicationStitchingResult> CREATOR
            = new Creator<ApplicationStitchingResult>() {
        @Override
        public ApplicationStitchingResult createFromParcel(Parcel in) {
            return parseMessageBrokerResultFrom(in);
        }

        @Override
        public ApplicationStitchingResult[] newArray(int size) {
            return new ApplicationStitchingResult[size];
        }
    };

    private static ApplicationStitchingResult parseMessageBrokerResultFrom(Parcel in) {
        return ApplicationStitchingResult.valueOf(in.readString());
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
