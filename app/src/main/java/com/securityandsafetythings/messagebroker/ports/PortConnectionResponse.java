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
 * Response class for ferrying IInPortConnection or IOutPortConnection objects.
 *
 * @hide
 */
public class PortConnectionResponse implements Parcelable {
    private PortConnectionType portConnectionType;
    private IInPortConnection inPortConnection;
    private IOutPortConnection outPortConnection;

    /**
     * Constructor for IInPortConnection.
     * Sets portConnectionType to PortConnectionType.IN_PORT_CONNECTION.
     *
     * @param inPortConnection IInPortConnection object.
     */
    public PortConnectionResponse(IInPortConnection inPortConnection) {
        this.portConnectionType = PortConnectionType.IN_PORT_CONNECTION;
        this.inPortConnection = inPortConnection;
    }

    /**
     * Constructor for IOutPortConnection.
     * Sets portConnectionType to PortConnectionType.OUT_PORT_CONNECTION.
     *
     * @param outPortConnection IOutPortConnection object.
     */
    public PortConnectionResponse(IOutPortConnection outPortConnection) {
        this.portConnectionType = PortConnectionType.OUT_PORT_CONNECTION;
        this.outPortConnection = outPortConnection;
    }

    /**
     * Get IInPortConnection object from the response.
     *
     * @return IInPortConnection object.
     */
    public InPortConnection getInPortConnection() {
        return new InPortConnection(inPortConnection);
    }

    /**
     * Get IOutPortConnection object from the response.
     *
     * @return IOutPortConnection object.
     */
    public OutPortConnection getOutPortConnection() {
        return new OutPortConnection(outPortConnection);
    }

    protected PortConnectionResponse(Parcel in) {
        this.portConnectionType = PortConnectionType.values()[in.readInt()];
        if (this.portConnectionType.equals(PortConnectionType.IN_PORT_CONNECTION)) {
            this.inPortConnection = IInPortConnection.Stub.asInterface(in.readStrongBinder());
        } else {
            this.outPortConnection = IOutPortConnection.Stub.asInterface(in.readStrongBinder());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.portConnectionType.ordinal());
        if ((this.portConnectionType.equals(PortConnectionType.IN_PORT_CONNECTION))
                && (this.inPortConnection != null)) {
            dest.writeStrongBinder(inPortConnection.asBinder());
        }
        if ((this.portConnectionType.equals(PortConnectionType.OUT_PORT_CONNECTION))
                && (this.outPortConnection != null)) {
            dest.writeStrongBinder(this.outPortConnection.asBinder());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PortConnectionResponse> CREATOR
            = new Creator<PortConnectionResponse>() {
        @Override
        public PortConnectionResponse createFromParcel(Parcel in) {
            return new PortConnectionResponse(in);
        }

        @Override
        public PortConnectionResponse[] newArray(int size) {
            return new PortConnectionResponse[size];
        }
    };

    enum PortConnectionType {
        IN_PORT_CONNECTION,
        OUT_PORT_CONNECTION;
    }
}
