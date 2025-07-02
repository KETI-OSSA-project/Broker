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

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * Message.
 *
 * <p>Container class encapsulating messages for enabling communication between different clients
 * using the message broker service. An outPort can send Message using the method
 * {@link OutPort#send(Message)}. Any {@link InPort} which has an enabled connection to the
 * sending {@link OutPort} will receive the Message.
 */
public class Message implements Parcelable {

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    private UUID messageId;
    private final HashMap<String, String> header;
    private final byte[] payload;
    private String topic;
    private String publisherId;
    private final Instant timeStamp;

    private Message(Builder builder) {
        this.messageId = UUID.randomUUID();
        this.payload = builder.payload;
        this.header = builder.header;
        this.timeStamp = builder.timeStamp;
    }

    private Message(Parcel in) {
        messageId = (UUID) in.readSerializable();
        payload = in.createByteArray();
        header = in.readHashMap(Message.class.getClassLoader());
        topic = in.readString();
        publisherId = in.readString();
        timeStamp = Instant.ofEpochMilli(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(messageId);
        dest.writeByteArray(payload);
        dest.writeMap(header);
        dest.writeString(topic);
        dest.writeString(publisherId);
        dest.writeLong(timeStamp.toEpochMilli());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Get the identifier of the message.
     *
     * @return unique identifier of the message.
     */
    public UUID getMessageId() {
        return messageId;
    }

    /**
     * Set the identifier of the message.
     *
     * @hide
     */
    public void setMessageId(UUID uuid) {
        this.messageId = uuid;
    }

    /**
     * Get the payload encapsulated in the message.
     *
     * @return payload of the message.
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * Get the timeStamp of the message. Note, the timeStamp contains the time when the message is
     * generated. However, the application sending the message can also override the message
     * using the method {@link Builder#setTimeStamp(Instant)} during its creation. This is usually
     * done to capture the time of a certain event to which the metadata of the message belongs.
     *
     * @return timeStamp of the message.
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    /**
     * Get the message header.
     *
     * @return header of the message.
     */
    public HashMap<String, String> getHeader() {
        return header;
    }

    /**
     * Get a specific attribute of message header.
     *
     * @return Attribute value for the given header key. {@code null} if no such mapping exists.
     */
    public String getHeaderAttribute(String key) {
        return header.get(key);
    }

    /**
     * Get topic of the {@link OutPort} sending the Message. The topic is set internally by
     * the MessageBroker when transacting the message, and hence this method is only applicable for
     * messages that are received.
     *
     * @return topic of the {@link OutPort} sending the Message.
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Get publisherId of the {@link OutPort} sending the Message. The publisherId is set
     * internally by MessageBroker when transacting the message, and hence this method is only
     * applicable for messages that are received.
     *
     * @return publisherId of the {@link OutPort} sending the Message.
     */
    public String getPublisherId() {
        return publisherId;
    }

    /**
     * Sets the topic of the message. Set internally by the MessageBroker when transacting the
     * message.
     *
     * @param topic topic of the {@link OutPort} sending the Message.
     * @hide
     */
    public void setTopic(@NonNull String topic) {
        requireNonNull(topic);
        this.topic = topic;
    }

    /**
     * Sets the publisherId of the message. Set internally by the MessageBroker when
     * transacting the message.
     *
     * @param publisherId publisherId of the {@link OutPort} sending the Message.
     * @hide
     */
    public void setPublisherId(@NonNull String publisherId) {
        requireNonNull(publisherId);
        this.publisherId = publisherId;
    }

    /**
     * Builder class for {@link Message}.
     */
    public static class Builder {
        private byte[] payload = null;
        private HashMap<String, String> header = new HashMap<>();
        private Instant timeStamp;

        /**
         * Optional method for setting the payload of the message.
         *
         * @param payload Byte-array containing the payload of the message. It is passed
         *                through unmodified and not interpreted by the message broker
         *                service, except if it is a message targeted to the service itself.
         *                Visit {@link android.os.TransactionTooLargeException} documentation for
         *                the supported maximum payload size.
         * @return Builder object encapsulating the payload of the message
         */
        public Builder setPayload(final @NonNull byte[] payload) {
            requireNonNull(payload);
            this.payload = payload;
            return this;
        }

        /**
         * Optional method to set time stamp of the message. Useful for capturing the time of a
         * certain event to which the metadata of the message belongs to.
         * Populated with the time when the message object is built ({@link #build()}).
         * <h4> Example usage to capture frame timestamp in {@link Message}:</h4>
         * <pre><code>
         * // Get {@link android.media.Image} from video-pipeline by extending
         * // {@link com.securityandsafetythings.app.VideoService} and implementing
         * // {onImageAvailable(ImageReader)} method.
         *
         * // Following this the timeStamp of the image can be obtained using the method
         * {@link Image#getTimestamp()}.
         * long timeStampLong = image.getTimestamp();
         * // The frame timeStamp can be converted into {@link Instant} using the method
         * // {@link com.securityandsafetythings.video.VideoSession#convertTimestampToInstant(long)}
         * Instant timeStamp = videoSession.convertTimestampToInstant(timeStampLong);
         *
         * byte[] payload; // Payload encapsulating the analytics metadata of the frame.
         * // Build {@link Message} object and set timeStamp value.
         * Message message = new Message.Builder()
         *                     .setTimeStamp(timeStamp)
         *                     .setPayload(payload).build();
         * </code></pre>
         *
         * @param timeStamp Instant representing the timeStamp of the message.
         * @return Builder object encapsulating the payload of the message
         */
        public Builder setTimeStamp(final @NonNull Instant timeStamp) {
            requireNonNull(timeStamp);
            this.timeStamp = timeStamp;
            return this;
        }

        /**
         * Optional method for setting the header of the message.
         *
         * @param header HashMap containing the header of the message. It is passed
         *               through unmodified and not interpreted by the message broker
         *               service, except if it is a message targeted to the service itself.
         *               Visit {@link android.os.TransactionTooLargeException} documentation for
         *               the supported maximum payload size.
         * @return Builder object encapsulating the header of the message
         */
        public Builder setHeader(final @NonNull HashMap<String, String> header) {
            requireNonNull(header);
            this.header = header;
            return this;
        }

        /**
         * Optional method for setting the header of the message.
         *
         * @param key   Attribute key of the header
         * @param value Value of the attribute for the provided key
         * @return Builder object encapsulating the header of the message
         */
        public Builder addHeaderAttribute(final @NonNull String key, @NonNull String value) {
            requireNonNull(key);
            requireNonNull(value);
            this.header.put(key, value);
            return this;
        }

        /**
         * Build {@link Message} object from the builder object.
         *
         * @return {@link Message} object
         */
        public Message build() {
            if (timeStamp == null) {
                timeStamp = Instant.now();
            }
            return new Message(this);
        }
    }
}
