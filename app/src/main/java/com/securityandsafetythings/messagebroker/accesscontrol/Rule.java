/*
 * Copyright 2020 by Security and Safety Things GmbH
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

package com.securityandsafetythings.messagebroker.accesscontrol;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.securityandsafetythings.messagebroker.MqttTopicUtils;

import java.util.Objects;

import static com.securityandsafetythings.messagebroker.MqttTopicUtils.checkFilterTopicSyntax;
import static com.securityandsafetythings.messagebroker.MqttTopicUtils.hasWildcards;
import static java.util.Objects.requireNonNull;

/**
 * Access control rule for a {@link Principal}.
 * <p> A Rule composes of three elements:
 * <ul>
 * <li>A set of topics for which the rule is applicable. This set in a rule
 * can be defined using an MQTT topic filter expression following the specification described
 * in MQTT v.5.0 Section 4.7.</li>
 * <li>The type of access for which the rule is applicable.</li>
 * <li>The type of control the rule imparts.</li>
 * </ul>
 * </p>
 *
 * @hide
 */
@SystemApi
public class Rule implements Parcelable, Comparable<Rule> {
    private final TopicSet topicSet;
    private final AccessType accessType;
    private final ControlType controlType;

    /**
     * Constructor.
     *
     * <p>Constructor for creating a Rule.
     *
     * @param topicExpression An MQTT topic filter expression (following the specification
     *                        described in MQTT v.5.0 Section 4.7.) defining the set of
     *                        topics for which the rule is applicable.
     * @param accessType      {@link AccessType} for the type of access.
     * @param controlType     {@link ControlType} for the type of control.
     */
    public Rule(@NonNull final String topicExpression,
                @NonNull final AccessType accessType,
                @NonNull final ControlType controlType) {
        this.topicSet = new TopicSet(topicExpression);
        this.accessType = requireNonNull(accessType);
        this.controlType = requireNonNull(controlType);
    }

    public static final Creator<Rule> CREATOR = new Creator<Rule>() {
        @Override
        public Rule createFromParcel(Parcel in) {
            return new Rule(in);
        }

        @Override
        public Rule[] newArray(int size) {
            return new Rule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Computes hash code for the rule.
     */
    @Override
    public int hashCode() {
        return Objects.hash(topicSet.topicExpression, accessType, controlType);
    }

    /**
     * Compares with another rule.
     */
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == this) {
            return true;
        }

        if (!(otherObject instanceof Rule)) {
            return false;
        }

        Rule otherRule = (Rule) otherObject;
        return Objects.equals(this.accessType, otherRule.accessType)
                && Objects.equals(this.controlType, otherRule.controlType)
                && Objects.equals(this.topicSet.topicExpression,
                otherRule.topicSet.topicExpression);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        this.accessType.writeToParcel(dest, flags);
        this.controlType.writeToParcel(dest, flags);
        dest.writeString(this.topicSet.topicExpression);
    }

    @Override
    public int compareTo(@NonNull Rule other) {
        requireNonNull(other);
        if (other.equals(this)) {
            return 0;
        }
        int difference = this.accessType.ordinal() - other.accessType.ordinal();
        if (difference != 0) {
            return difference;
        }
        difference = this.controlType.ordinal() - other.controlType.ordinal();
        if (difference != 0) {
            return difference;
        }
        difference = this.topicSet.topicExpression.length()
                - other.topicSet.topicExpression.length();
        if (difference != 0) {
            return difference;
        }
        return this.topicSet.topicExpression.compareTo(other.topicSet.topicExpression);
    }

    /**
     * Get the MQTT topic filter expression defining the topic set.
     *
     * @return The expression defining the topic set for the rule.
     */
    public String getTopicExpression() {
        return topicSet.topicExpression;
    }

    /**
     * Get the {@link AccessType}.
     *
     * @return {@link AccessType} in the rule.
     */
    public AccessType getAccessType() {
        return accessType;
    }

    /**
     * Get the {@link ControlType}.
     *
     * @return {@link ControlType} in the rule.
     */
    public ControlType getControlType() {
        return controlType;
    }

    /**
     * Check whether a rule is applicable to a given topic set expression and {@link AccessType}.
     *
     * @param topicExpression An MQTT Topic filter expression which need to be
     *                        checked with the rule.
     * @param accessType      {@link AccessType} to check with the rule.
     * @return Returns {@code true} if {@code topicExpression} has topics
     * covered by the rule and {@code accessType} is same as the {@link AccessType}
     * in the rule, {@code false} otherwise.
     *
     * @hide
     */
    public boolean isApplicableTo(final String topicExpression,
                                  final AccessType accessType) {
        if (!this.accessType.equals(accessType)) {
            return false;
        }

        return topicSet.hasIntersection(topicExpression);
    }

    /**
     * Check whether a rule is covering all the topics in a filter expression.
     *
     * @param topicExpression An MQTT Topic filter expression which need to be
     *                        checked with the rule.
     * @return Returns {@code true} if {@code topicExpression} represents a subset of the
     * topic set in the rule.
     *
     * @hide
     */
    public boolean isTopicSupersetOf(final @NonNull String topicExpression) {
        return topicSet.isSuperset(topicExpression);
    }

    /**
     * Compares the topic sets of two rules.
     *
     * @param otherRule Another rule.
     * @return Returns -1 if topics in {@code this} rule is a proper subset of topics in
     * {@code otherRule}, 1 if topics in {@code this} rule is a proper superset of topics in
     * {@code otherRule}, 0 if both topic sets are equal, and {@code null} if proper
     * subset-superset relation cannot be established.
     *
     * @hide
     */
    public Integer compareTopicSetTo(final @NonNull Rule otherRule) {
        Objects.requireNonNull(otherRule);
        return topicSet.compareTo(otherRule.topicSet);
    }

    private Rule(Parcel in) {
        this.accessType = AccessType.CREATOR.createFromParcel(in);
        this.controlType = ControlType.CREATOR.createFromParcel(in);
        this.topicSet = new TopicSet(in.readString());
    }

    private static class TopicSet {
        final String topicExpression;

        TopicSet(final @NonNull String topicExpression) {
            requireNonNull(topicExpression);
            //The topic set expression follows the MQTT topic filter syntax, check for it.
            checkFilterTopicSyntax(topicExpression);
            this.topicExpression = topicExpression;
        }

        /**
         * Checks whether the topic set has an intersection with the topics covered in the
         * {@code otherTopicExpression}.
         *
         * @param otherTopicExpression A topic filter to be checked.
         * @return Returns {@code true} if the topic set has an intersection with topics in
         * {@code otherTopicExpression}, {@code false} otherwise.
         */
        boolean hasIntersection(@NonNull String otherTopicExpression) {
            if (!hasWildcards(otherTopicExpression)) {
                return MqttTopicUtils.match(this.topicExpression, otherTopicExpression);
            } else {
                return MqttTopicUtils.hasIntersection(this.topicExpression, otherTopicExpression);
            }
        }

        /**
         * Checks whether the topic set is a super set of the topics covered by the topic
         * filter expression.
         *
         * @param otherTopicExpression A topic filter to be checked.
         * @return Returns {@code true} if the topic set is a super of the topic set covered by
         * the topic filter expression, {@code false} otherwise.
         */
        boolean isSuperset(@NonNull String otherTopicExpression) {
            requireNonNull(otherTopicExpression);
            if (!hasWildcards(otherTopicExpression)) {
                return MqttTopicUtils.match(this.topicExpression, otherTopicExpression);
            } else {
                return MqttTopicUtils.isSuperset(this.topicExpression, otherTopicExpression);
            }
        }

        /**
         * Compares two topic sets.
         *
         * @param otherTopicSet Another topic set.
         * @return Returns -1 if topics in {@code this} rule is a proper subset of topics in
         * {@code otherRule}, 1 if topics in {@code this} rule is a proper superset of topics in
         * {@code otherRule}, 0 if both topic sets are equal, and {@code null} if proper
         * subset-superset relation cannot be established.
         */
        private Integer compareTo(@NonNull TopicSet otherTopicSet) {
            if (this.topicExpression.equals(otherTopicSet.topicExpression)) {
                return 0;
            }

            String intersectedExpression = MqttTopicUtils.intersection(this.topicExpression,
                                                                otherTopicSet.topicExpression);
            if (this.topicExpression.equals(intersectedExpression)) {
                return -1;
            }
            if (otherTopicSet.topicExpression.equals(intersectedExpression)) {
                return 1;
            }
            return null;
        }
    }
}
