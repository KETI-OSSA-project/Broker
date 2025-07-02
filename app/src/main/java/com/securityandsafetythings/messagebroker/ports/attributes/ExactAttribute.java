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

import java.util.Objects;

/**
 * ExactAttribute class.
 */
public class ExactAttribute implements Parcelable {
    private final String key;
    private final String value;
    private boolean required = false;
    private final String description;

    /**
     * Constructor for attribute.
     *
     * @param key         key for the attribute.
     * @param value       value of the attribute.
     * @param required    use true if an exact attribute with same key is required in the other
     *                    {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration}
     *                    while doing a match, {@code false} otherwise.
     *                    The default value of required is {@code false}.
     * @param description optional description for the attribute.
     * @hide
     */
    public ExactAttribute(@NonNull final String key,
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
     * See {@link ExactAttribute#ExactAttribute(String, String, boolean, String)}.
     *
     * @hide
     */
    public ExactAttribute(@NonNull final String key,
                          @NonNull final String value,
                          final boolean required) {
        this(key, value, required, null);
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
     * Check if an exact attribute with same key is required in the other
     * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
     * a match. The default value of required is {@code false}.
     *
     * @return {@code true} if an exact attribute with same key is required in the other
     * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
     * a match, {@code false} otherwise.
     */
    public boolean isRequired() {
        return required;
    }

    public static final Creator<ExactAttribute> CREATOR =
            new Creator<ExactAttribute>() {
                @Override
                public ExactAttribute createFromParcel(Parcel in) {
                    return new ExactAttribute(in);
                }

                @Override
                public ExactAttribute[] newArray(int size) {
                    return new ExactAttribute[size];
                }
            };

    protected ExactAttribute(Parcel in) {
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
        if (!(object instanceof ExactAttribute)) {
            return false;
        }
        ExactAttribute otherExactAttribute = (ExactAttribute) object;
        return (this.required == otherExactAttribute.required)
                && this.key.equals(otherExactAttribute.key)
                && this.value.equals(otherExactAttribute.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.required, this.key, this.value);
    }
}
