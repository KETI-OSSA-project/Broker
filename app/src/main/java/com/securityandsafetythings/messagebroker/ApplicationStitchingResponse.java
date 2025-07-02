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

import android.support.annotation.NonNull;
import com.securityandsafetythings.messagebroker.applicationstitching.ApplicationBlock;
import com.securityandsafetythings.messagebroker.applicationstitching.Edge;
import com.securityandsafetythings.messagebroker.applicationstitching.IEdge;
import com.securityandsafetythings.messagebroker.ports.IInPort;
import com.securityandsafetythings.messagebroker.ports.IOutPort;
import com.securityandsafetythings.messagebroker.ports.InPort;
import com.securityandsafetythings.messagebroker.ports.OutPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Holds the response when trying to stitch applications.
 *
 * @hide
 */
public class ApplicationStitchingResponse implements Parcelable {
    public static final Creator<ApplicationStitchingResponse> CREATOR =
            new Creator<ApplicationStitchingResponse>() {
                @Override
                public ApplicationStitchingResponse createFromParcel(Parcel in) {
                    return new ApplicationStitchingResponse(in);
                }

                @Override
                public ApplicationStitchingResponse[] newArray(int size) {
                    return new ApplicationStitchingResponse[size];
                }
            };
    private ApplicationStitchingResult result;
    private int nInPorts = 0;
    private int nOutPorts = 0;
    private int nEdges = 0;
    private List<IEdge> edges = new ArrayList<>();
    private List<IInPort> inPorts = new ArrayList<>();
    private List<IOutPort> outPorts = new ArrayList<>();
    private HashMap<String, String> attributeConfiguration;
    private String applicationId;

    protected ApplicationStitchingResponse(Parcel in) {
        result = ApplicationStitchingResult.CREATOR.createFromParcel(in);
        if (result == ApplicationStitchingResult.OK) {
            nInPorts = in.readInt();
            nOutPorts = in.readInt();
            nEdges = in.readInt();
            for (int i = 0; i < nInPorts; i++) {
                inPorts.add(IInPort.Stub.asInterface(in.readStrongBinder()));
            }

            for (int i = 0; i < nOutPorts; i++) {
                outPorts.add(IOutPort.Stub.asInterface(in.readStrongBinder()));
            }

            for (int i = 0; i < nEdges; i++) {
                edges.add(IEdge.Stub.asInterface(in.readStrongBinder()));
            }
            attributeConfiguration = in.readHashMap(HashMap.class.getClassLoader());
            applicationId = in.readString();
        }
    }

    /**
     * Constructor for creating an instance of ApplicationStitchingResponse using a result object.
     * Used by the service for communicating error codes.
     *
     * @param result Result component of the response.
     */
    public ApplicationStitchingResponse(@NonNull ApplicationStitchingResult result) {
        this.result = Objects.requireNonNull(result);
        inPorts = null;
        outPorts = null;
        edges = null;
        attributeConfiguration = null;
        applicationId = null;
    }

    /**
     * Constructor for creating an instance of ApplicationStitchingResponse.
     * Used by the service for transferring the binders.
     *
     * @hide
     */
    public ApplicationStitchingResponse(List<IInPort> inPorts,
                                        List<IOutPort> outPorts,
                                        List<IEdge> edges) {
        this.result = ApplicationStitchingResult.OK;
        this.inPorts = inPorts;
        this.outPorts = outPorts;
        this.edges = edges;
        if (inPorts != null) {
            nInPorts = inPorts.size();
        }
        if (outPorts != null) {
            nOutPorts = outPorts.size();
        }
        if (edges != null) {
            nEdges = edges.size();
        }
    }

    /**
     * Constructor for creating an instance of ApplicationStitchingResponse.
     * Used by the service for transferring the binders.
     */
    public ApplicationStitchingResponse(String applicationId,
                                 HashMap<String, String> attributeConfiguration,
                                 List<IInPort> inPorts,
                                 List<IOutPort> outPorts) {
        this.result = ApplicationStitchingResult.OK;
        this.inPorts = inPorts;
        this.outPorts = outPorts;
        this.edges = null;
        if (inPorts != null) {
            nInPorts = inPorts.size();
        }
        if (outPorts != null) {
            nOutPorts = outPorts.size();
        }
        this.applicationId = applicationId;
        this.attributeConfiguration = attributeConfiguration;
    }

    /**
     * Getter to fetch the ApplicationStitchingResult.
     *
     * @return a ApplicationStitchingResult describing if a new connection is successfully
     * created or error code indicating why the connection cannot be created.
     */
    public ApplicationStitchingResult getResult() {
        return result;
    }

    /**
     * Getter to fetch inPort handles.
     * Returns the inPort handles if {@link #getResult} returns
     * MessageBrokerResult.OK, returns {@code null} otherwise.
     *
     * @return the inPorts handle if available, null otherwise.
     */
    public List<InPort> getInPorts() {
        if (result == ApplicationStitchingResult.OK && inPorts != null) {
            return inPorts.stream().map(InPort::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * Getter to fetch attributes of an application.
     * Empty map if there are no attributes declared.
     *
     * @return HashMap containing the attribute configuration.
     */
    public HashMap<String, String> getAttributeConfiguration() {
        if (attributeConfiguration == null) {
            return new HashMap<>();
        }
        return new HashMap<String, String>(attributeConfiguration);
    }

    /**
     * Getter to fetch application block parsing the response.
     *
     * @return {@link ApplicationBlock} object, if it can be constructed from response,
     * {@code null} otherwise.
     */
    public ApplicationBlock getApplicationBlock() {
        if (applicationId == null || attributeConfiguration == null) {
            return null;
        }
        List<InPort> inPorts = getInPorts();
        List<OutPort> outPorts = getOutPorts();
        if (inPorts == null || outPorts == null) {
            return null;
        }
        return new ApplicationBlock(applicationId, attributeConfiguration,
                new HashSet<>(inPorts),
                new HashSet<>(outPorts));
    }

    /**
     * Getter to fetch applicationId of an application.
     *
     * @return applicationId if available, {@code null} otherwise.
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Getter to fetch outPort handles.
     * Returns the outPort handles if {@link #getResult} returns
     * MessageBrokerResult.OK, returns {@code null} otherwise.
     *
     * @return the outPort handle if available, {@code null} otherwise.
     */
    public List<OutPort> getOutPorts() {
        if (result == ApplicationStitchingResult.OK && outPorts != null) {
            return outPorts.stream().map(OutPort::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * Getter to fetch edge handle.
     * Returns the edge handle if {@link #getResult} returns
     * MessageBrokerResult.OK, returns {@code null} otherwise.
     *
     * @return the edge handle if available, {@code null} otherwise.
     */
    public List<Edge> getEdges() {
        if (result == ApplicationStitchingResult.OK && outPorts != null) {
            return edges.stream().map(Edge::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        result.writeToParcel(dest, flags);
        dest.writeInt(nInPorts);
        dest.writeInt(nOutPorts);
        dest.writeInt(nEdges);
        if (result == ApplicationStitchingResult.OK) {
            if (inPorts != null) {
                for (int i = 0; i < nInPorts; i++) {
                    dest.writeStrongBinder(inPorts.get(i).asBinder());
                }
            }
            if (outPorts != null) {
                for (int i = 0; i < nOutPorts; i++) {
                    dest.writeStrongBinder(outPorts.get(i).asBinder());
                }
            }
            if (edges != null) {
                for (int i = 0; i < nEdges; i++) {
                    dest.writeStrongBinder(edges.get(i).asBinder());
                }
            }
            dest.writeMap(attributeConfiguration);
            dest.writeString(applicationId);
        }
    }
}
