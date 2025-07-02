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
import com.securityandsafetythings.messagebroker.ports.IPortGroup;
import com.securityandsafetythings.messagebroker.ports.PortGroup;

/**
 * Holds the response when trying to create a new port group.
 *
 * @hide
 */
public class PortGroupResponse implements Parcelable {
    private IPortGroup iPortGroup;

    /**
     * Constructor for PortGroupResponse.
     *
     * @param iPortGroup IPortGroup object.
     *
     * @hide
     */
    public PortGroupResponse(IPortGroup iPortGroup) {
        this.iPortGroup = iPortGroup;
    }

    /**
     * Get PortGroup object from the response.
     *
     */
    public PortGroup getPortGroup() {
        return new PortGroup(iPortGroup);
    }

    protected PortGroupResponse(Parcel in) {
        this.iPortGroup = IPortGroup.Stub.asInterface(in.readStrongBinder());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(iPortGroup.asBinder());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PortGroupResponse> CREATOR
            = new Creator<PortGroupResponse>() {
        @Override
        public PortGroupResponse createFromParcel(Parcel in) {
            return new PortGroupResponse(in);
        }

        @Override
        public PortGroupResponse[] newArray(int size) {
            return new PortGroupResponse[size];
        }
    };
}
