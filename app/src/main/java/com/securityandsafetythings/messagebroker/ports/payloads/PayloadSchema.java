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

package com.securityandsafetythings.messagebroker.ports.payloads;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.securityandsafetythings.ParcelUtils;

import java.util.Objects;

/**
 * PayloadSchema class.
 *
 */
public class PayloadSchema implements Parcelable {
    private final String schema;
    private boolean required = false;
    private String description;

    private PayloadSchema(@NonNull String schema) {
        this.schema = schema;
        this.description = null;
    }

    /**
     * Get description about the payload.
     *
     * @return description for the payload.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get schema of the payload.
     *
     * @return schema of the payload.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Check if the payload schema is required in the other
     * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
     * a match.
     *
     * @return {@code true} if the payload schema is required in the other
     * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
     * a match, {@code false} otherwise. The default value of required is {@code false}.
     */
    public boolean isRequired() {
        return required;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayloadSchema> CREATOR =
            new Creator<PayloadSchema>() {
                @Override
                public PayloadSchema createFromParcel(Parcel in) {
                    return new PayloadSchema(in);
                }

                @Override
                public PayloadSchema[] newArray(int size) {
                    return new PayloadSchema[size];
                }
            };

    protected PayloadSchema(Parcel in) {
        this.schema = in.readString();
        this.required = ParcelUtils.readBoolean(in);
        this.description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schema);
        ParcelUtils.writeBoolean(dest, this.required);
        dest.writeString(description);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof PayloadSchema)) {
            return false;
        }
        PayloadSchema otherPayloadSchema = (PayloadSchema) object;
        return ((this.required == otherPayloadSchema.required)
                && this.schema.equals(otherPayloadSchema.schema));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.required, this.schema);
    }

    /**
     * Builder for {@link PayloadSchema}.
     *
     * @hide
     */
    public static class Builder {
        private final PayloadSchema schema;

        /**
         * Constructor for Builder.
         *
         * @param schema Schema of the payload.
         */
        public Builder(@NonNull final String schema) {
            Objects.requireNonNull(schema);
            this.schema = new PayloadSchema(schema);
        }

        /**
         * Set the payload schema is required in the other
         * {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration} while doing
         * a match.
         *
         * @param required Use true if an hierarchical attribute with same key is required in the
         * other {@link com.securityandsafetythings.messagebroker.ports.PortConfiguration}
         * while doing a match, {@code false} otherwise. The default value of required is
         * {@code false}.
         * @return Builder instance.
         */
        public Builder setRequired(final boolean required) {
            schema.required = required;
            return this;
        }

        /**
         * Set description for the payload schema.
         *
         * @param description description for the payload schema.
         * @return Builder instance.
         */
        public Builder setDescription(final String description) {
            schema.description = description;
            return this;
        }

        /**
         * Get the {@link PayloadSchema} object from the builder instance.
         *
         * @return Built {@link PayloadSchema}.
         */
        public PayloadSchema build() {
            return this.schema;
        }
    }
}
