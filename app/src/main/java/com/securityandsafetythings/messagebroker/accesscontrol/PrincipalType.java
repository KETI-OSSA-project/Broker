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

import java.util.Arrays;
import java.util.Comparator;

/**
 * Enum to distinguish the type of Principal.
 *
 * @hide
 */
@SystemApi
public enum PrincipalType implements Parcelable {
    /**
     * Type for application.
     */
    APPLICATION(0),     // 애플리케이션 타입
    /**
     * Type for universal group.
     */
    UNIVERSAL_GROUP(1); // 범용 그룹 타입

    // Parcelable
    public static final Creator<PrincipalType> CREATOR = new Creator<PrincipalType>() {
        @Override
        public PrincipalType createFromParcel(Parcel in) {
            return PrincipalType.values()[in.readInt()];
        }

        @Override
        public PrincipalType[] newArray(int size) {
            return new PrincipalType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    private int precedenceValue;    // 0 or 1

    PrincipalType(final int precedenceValue) {
        this.precedenceValue = precedenceValue;
    }

    /**
     * Types of principals suitably ordered for rule resolution algorithm.
     *
     * @hide
     */
    public static PrincipalType[] orderedTypes = initializeOrderedTypes();

    private static PrincipalType[] initializeOrderedTypes() {
        PrincipalType[] types = PrincipalType.values();
        Arrays.sort(types, new Comparator<PrincipalType>() {
            @Override
            public int compare(PrincipalType type1, PrincipalType type2) {
                int difference = type1.precedenceValue - type2.precedenceValue;
                return difference;
            }
        });
        return types;
    }
}
