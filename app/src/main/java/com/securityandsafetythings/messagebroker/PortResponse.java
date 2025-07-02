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
import com.securityandsafetythings.messagebroker.ports.IInPort;
import com.securityandsafetythings.messagebroker.ports.IOutPort;
import com.securityandsafetythings.messagebroker.ports.InPort;
import com.securityandsafetythings.messagebroker.ports.OutPort;

/**
 * Holds the response when trying to create a new port.
 *
 * @hide
 */
public class PortResponse implements Parcelable {
    public static final Creator<PortResponse> CREATOR =
            new Creator<PortResponse>() {
                @Override
                public PortResponse createFromParcel(Parcel in) {
                    return new PortResponse(in);
                }

                @Override
                public PortResponse[] newArray(int size) {
                    return new PortResponse[size];
                }
            };
    private MessageBrokerResult result;
    private PortType portType;
    private IInPort inPort;
    private IOutPort outPort;

    protected PortResponse(Parcel in) {
        result = MessageBrokerResult.CREATOR.createFromParcel(in);
        if (result == MessageBrokerResult.OK) {
            portType = PortType.CREATOR.createFromParcel(in);
            if (portType.equals(PortType.INPORT)) {
                inPort = IInPort.Stub.asInterface(in.readStrongBinder());
            } else {
                outPort = IOutPort.Stub.asInterface(in.readStrongBinder());
            }
        }
    }

    /**
     * Constructor for creating an instance of PortConnectionResponse using a result object.
     * Used by the service for communicating error codes.
     *
     * @param result Result component of the response.
     */
    public PortResponse(MessageBrokerResult result) {
        this.result = result;
        inPort = null;
        outPort = null;
    }

    /**
     * Constructor for creating an instance of PortConnectionResponse using an inPort binder.
     * binder handle. Used by the service for transferring the binder handle.
     *
     * @param inPort Binder instance of a inPort.
     */
    public PortResponse(IInPort inPort) {
        this.result = MessageBrokerResult.OK;
        this.inPort = inPort;
        portType = PortType.INPORT;
    }

    /**
     * Constructor for creating an instance of PortConnectionResponse using an outPort binder.
     * binder handle. Used by the service for transferring the binder handle.
     *
     * @param outPort Binder instance of a outPort.
     */
    public PortResponse(IOutPort outPort) {
        this.result = MessageBrokerResult.OK;
        this.outPort = outPort;
        portType = PortType.OUTPORT;
    }

    /**
     * Getter to fetch the MessageBrokerResult.
     * Returns the result of creating a new port.
     *
     * @return a MessageBrokerResult describing if a new port is successfully
     * created or error code indicating why the connection cannot be created.
     *
     * @hide
     */
    public MessageBrokerResult getResult() {
        return result;
    }

    /**
     * Getter to fetch inPort object.
     *
     * @return the inPort if an inPort is successfully created.
     */
    public InPort getInPort() {
        return new InPort(inPort);
    }

    /**
     * Getter to fetch iInPort object.
     *
     * @return the inPort if an inPort is successfully created.
     *
     * @hide
     */
    public IInPort getIInPort() {
        return inPort;
    }

    /**
     * Getter to fetch outPort.
     *
     * @return the outPort if an outPort is successfully created,
     */
    public OutPort getOutPort() {
        return new OutPort(outPort);
    }


    /**
     * Getter to fetch iOutPort.
     *
     * @return the outPort if an outPort is successfully created,
     *
     * @hide
     */
    public IOutPort getIOutPort() {
        return outPort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        result.writeToParcel(dest, flags);
        if (result == MessageBrokerResult.OK) {
            portType.writeToParcel(dest, flags);
            if (inPort != null) {
                dest.writeStrongBinder(inPort.asBinder());
            }
            if (outPort != null) {
                dest.writeStrongBinder(outPort.asBinder());
            }
        }
    }
}
