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

package com.securityandsafetythings.messagebroker.ports.attributes;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.securityandsafetythings.ParcelUtils;
import com.securityandsafetythings.messagebroker.MessageHeaderKeyNames;
import com.securityandsafetythings.messagebroker.MqttTopicUtils;
import com.securityandsafetythings.messagebroker.PortType;

import java.util.Objects;

/**
 * HierarchicalAttribute class.
 */
public class HierarchicalAttribute implements Parcelable {
    private static final String PUBLISHER_ID = MessageHeaderKeyNames.PUBLISHER_ID;
    private final String key;
    private final String value;
    private final boolean required;
    private final String description;

    /**
     * Constructor for attribute.
     *
     * @param key         key for the attribute.
     * @param value       value of the attribute. The restrictions on the acceptable values are as
     *                    given below:
     *                    <ul>
     *                    <li> To use in a
     *                    {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration}
     *                    for {@link com.securityandsafetythings.messagebroker.ports.OutPort}, the
     *                    value should follow the topic name syntax rules described in MQTT v.5.0
     *                    section 4.7.
     *                    </li>
     *                    <li> To use in a
     *                    {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration}
     *                    for {@link com.securityandsafetythings.messagebroker.ports.InPort}, the
     *                    value should follow the  filter syntax rules described in MQTT v.5.0
     *                    section 4.7.
     *                    </li>
     *                    </ul>
     * @param required    use {@code true} if a hierarchical attribute with same key is required in
     *                    the other
     *                    {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration}
     *                    while doing a match, {@code false} otherwise. The default value of
     *                    required is {@code false}.
     * @param description optional description for the attribute.
     * @hide
     */
    public HierarchicalAttribute(@NonNull final String key,
                                 @NonNull final String value,
                                 final boolean required,
                                 @Nullable final String description) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
        this.required = Objects.requireNonNull(required);
        this.description = description;
    }

    /**
     * Overloaded constructor without optional argument to provide description.
     * See {@link HierarchicalAttribute#HierarchicalAttribute(String, String, boolean, String)}.
     *
     * @hide
     */
    public HierarchicalAttribute(@NonNull final String key,
                                 @NonNull final String value,
                                 final boolean required) {
        this(key, value, required, null);
    }

    /**
     * Check the correctness of the value as per MQTT Specification.
     *
     * @param portType Type of port for the attribute is used.
     * @hide
     */
    public void checkAttributeSyntax(@NonNull final PortType portType) {
        Objects.requireNonNull(portType);
        if (portType.equals(PortType.INPORT)) {
            MqttTopicUtils.checkFilterTopicSyntax(value);
        } else {
            try {
                MqttTopicUtils.checkMessageTopicSyntax(value);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new IllegalArgumentException("For OutPort, the value need to follow the" +
                        " syntax for MQTT Topic specification. Error: " +
                        illegalArgumentException.getMessage());
            }
            if (key.equals(PUBLISHER_ID)) {
                throw new IllegalArgumentException("It is not permitted to add " +
                        "attribute with key " + PUBLISHER_ID);
            }
        }
    }

    /**
     * Get optional description for the attribute.
     *
     * @return description for the attribute.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get key of the attribute.
     *
     * @return key of the attribute.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get value of the attribute.
     *
     * @return value of the attribute.
     */
    public String getValue() {
        return value;
    }

    /**
     * Check if a hierarchical attribute with same key is required in the other
     * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
     * a match.
     *
     * @return {@code true} if an hierarchical attribute with same key is required in the other
     * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
     * a match, {@code false} otherwise. The default value of required is {@code false}.
     */
    public boolean isRequired() {
        return required;
    }

    public static final Creator<HierarchicalAttribute> CREATOR =
            new Creator<HierarchicalAttribute>() {
                @Override
                public HierarchicalAttribute createFromParcel(Parcel in) {
                    return new HierarchicalAttribute(in);
                }

                @Override
                public HierarchicalAttribute[] newArray(int size) {
                    return new HierarchicalAttribute[size];
                }
            };

    protected HierarchicalAttribute(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
        this.required = ParcelUtils.readBoolean(in);
        this.description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
        ParcelUtils.writeBoolean(dest, required);
        dest.writeString(description);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof HierarchicalAttribute)) {
            return false;
        }
        HierarchicalAttribute otherHierarchicalAttribute = (HierarchicalAttribute) object;
        return (this.required == otherHierarchicalAttribute.required)
                && this.key.equals(otherHierarchicalAttribute.key)
                && this.value.equals(otherHierarchicalAttribute.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.required, this.key, this.value);
    }
}
