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
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.securityandsafetythings.BoundServiceHelper;
import com.securityandsafetythings.messagebroker.Constants;
import com.securityandsafetythings.messagebroker.IMessagingService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.securityandsafetythings.messagebroker.Constants.MESSAGE_BROKER_PACKAGE;
import static com.securityandsafetythings.messagebroker.Constants.MESSAGING_ACCESS_CONTROL_MANAGE;
import static com.securityandsafetythings.messagebroker.Constants.MESSAGING_ACCESS_CONTROL_QUERY;
import static com.securityandsafetythings.messagebroker.Constants.MESSAGING_SERVICE;

/**
 * Application interface for managing Messaging Access Control.
 *
 * <p>All the requests to the Messaging Access Control are handled by the manager object.
 * The {@link #getInstance(Context)} provides an instance of the manager object.
 * <h3>Usage:</h3>
 * <pre><code>
 *     messagingAccessControlManager =
 *          MessagingAccessControlManager.getInstance(context);
 *
 *     ...
 *
 *     //Cleanup resources
 *     messagingAccessControlManager.disconnect();
 * </code></pre>
 *
 * <p>The APIs in the Messaging Access Control are centred around the
 * {@link Principal} object. This object represents the entity whose access is
 * being controlled. See {@link Principal}.
 * <h3>Usage:</h3>
 * <h4>Adding a new Principal for an application in Messaging Access Control:</h4>
 * <pre><code>
 *     //Package name of application
 *     String packageName = "com.example.app";
 *     //Create a principal object
 *     Principal principal = new Principal(packageName,PrincipalType.APPLICATION);
 *
 *     //adding principal to Messaging Access Control
 *     messagingAccessControlManager.add(principal);
 * </code></pre>
 *
 * <h4>Getting a Principal already existing in Messaging Access Control:</h4>
 * <pre><code>
 *     //Package name of application
 *     String packageName = "com.example.app";
 *     //Create a principal object
 *     Principal principal =
 *              messagingAccessControlManager.getPrincipal(packageName, PrincipalType.APPLICATION);
 * </code></pre>
 *
 * <h4>Updating a Principal already existing in Messaging Access Control:</h4>
 * <pre><code>
 *     //Updating a principal in Messaging Access Control
 *     messagingAccessControlManager.update(principal);
 * </code></pre>
 *
 * <h4>Removing a Principal already existing in Messaging Access Control:</h4>
 * <pre><code>
 *     //remove a principal in Messaging Access Control
 *     messagingAccessControlManager.remove(principal);
 * </code></pre>
 *
 * <h4>Getting all Principals existing in Messaging Access Control:</h4>
 * <pre><code>
 *     List&lt;Principal&gt; principals =
 *              messagingAccessControlManager.getAllPrincipals();
 * </code></pre>
 *
 * <h4>Check publish access of an application as per the Messaging Access Control:</h4>
 * <pre><code>
 *     String packageName = "com.example.app";
 *     int accessCheckResult = messagingAccessControlManager.checkAccess(packageName,
 *                                                                       PrincipalType.APPLICATION,
 *                                                                       "topic1/#",
 *                                                                       AccessType.PUBLISH);
 * </code></pre>
 *
 * @hide
 */
@SystemApi
public class MessagingAccessControlManager implements AutoCloseable {

    private static final String INTERNAL_ERROR_TAG =
            "Internal Error: incorrect result returned for this request: ";

    private static final ComponentName SERVICE =
            ComponentName.createRelative(MESSAGE_BROKER_PACKAGE,
                    MESSAGING_SERVICE);
    /* Codes for check access result */
    /**
     * Result of {@link MessagingAccessControlManager#checkAccess(String, PrincipalType,
     * String, AccessType)} if access is denied.
     */
    public static final int ACCESS_DENIED = 0;

    /**
     * Result of {@link MessagingAccessControlManager#checkAccess(String, PrincipalType,
     * String, AccessType)} if access is allowed.
     */
    public static final int ACCESS_ALLOWED = 1;

    private final BoundServiceHelper<IMessagingService> helper;

    // 생성자 및 인스턴스 생성, 바인딩 시작 -> 서비스 & 클라이언트 연결
    private MessagingAccessControlManager(final Context context) {
        this.helper = BoundServiceHelper.newInstance(context, SERVICE,
                IMessagingService.Stub::asInterface);
    }

    /**
     * Get the singleton instance of the Messaging Access Control Manager.
     *
     * @param context The application context of your app.
     * @return The Messaging Access Control Manager instance.
     * @throws RuntimeException on connection error (e.g. service does not exist).
     */
    @NonNull
    public static MessagingAccessControlManager getInstance(@NonNull final Context context) {
        return new MessagingAccessControlManager(context);
    }

    /**
     * Add a principal to Messaging Access Control. -> principal: 접근 제어 대상, 객체 주체
     *
     * <p>Requires permission
     * com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_MANAGE.
     *
     * @param principal Principal to be added to the Messaging Access Control.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of type
     *                        {@link MessagingAccessControlException.Type#PRINCIPAL_ALREADY_EXISTS}
     *                        is thrown if it is already existing.
     * @throws SecurityException               if caller has no relevant permission.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_MANAGE)
    public void add(@NonNull final Principal principal) throws MessagingAccessControlException {
        Objects.requireNonNull(principal);
        try {
            MessagingAccessControlResult result = helper.get().add(principal);
            switch (result) {
                case SUCCESS:
                    break;
                case PRINCIPAL_ALREADY_EXISTS:
                    throw new MessagingAccessControlException(
                            MessagingAccessControlException.Type.PRINCIPAL_ALREADY_EXISTS);
                case CANNOT_CONFIGURE_APPLICATION:
                    throw new MessagingAccessControlException(MessagingAccessControlException.Type
                            .CANNOT_CONFIGURE_APPLICATION);
                default:
                    throw new RuntimeException(INTERNAL_ERROR_TAG + result.name());
            }
            /* RemoteException: 반드시 처리해야 하는 예외 -> RuntimeException: 처리 안해도 되는 예외 */
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove the principal from the Messaging Access Control.
     *
     * <p>Requires permission
     * com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_MANAGE.
     *
     * @param principal Principal to be removed from the Messaging Access Control.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of types:
     *                    {@link MessagingAccessControlException.Type#PRINCIPAL_DOES_NOT_EXIST}
     *                    is thrown if the entry is absent.
     *                    {@link MessagingAccessControlException.Type#CANNOT_REMOVE_UNIVERSAL_GROUP}
     *                    is thrown if attempt is to remove universal group instance.
     * @throws SecurityException               if caller has no relevant permission.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_MANAGE)
    public void remove(@NonNull final Principal principal) throws MessagingAccessControlException {
        Objects.requireNonNull(principal);
        try {
            MessagingAccessControlResult result = helper.get().remove(principal);
            switch (result) {
                case SUCCESS:
                    break;
                case PRINCIPAL_DOES_NOT_EXIST:
                    throw new MessagingAccessControlException(
                            MessagingAccessControlException.Type.PRINCIPAL_DOES_NOT_EXIST);
                case CANNOT_REMOVE_UNIVERSAL_GROUP:
                    throw new MessagingAccessControlException(
                            MessagingAccessControlException.Type
                                    .CANNOT_REMOVE_UNIVERSAL_GROUP);
                default:
                    throw new RuntimeException(INTERNAL_ERROR_TAG + result.name());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update a principal in the Messaging Access Control.
     *
     * <p>Requires permission
     * com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_MANAGE.
     *
     * @param principal The principal to be updated.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of type
     *                         {@link MessagingAccessControlException.Type#PRINCIPAL_DOES_NOT_EXIST}
     *                         is thrown if the entry is absent.
     * @throws SecurityException               if caller has no relevant permission.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_MANAGE)
    public void update(@NonNull final Principal principal) throws MessagingAccessControlException {
        Objects.requireNonNull(principal);
        try {
            MessagingAccessControlResult result = helper.get().update(principal);
            switch (result) {
                case SUCCESS:
                    break;
                case PRINCIPAL_DOES_NOT_EXIST:
                    throw new MessagingAccessControlException(
                            MessagingAccessControlException.Type.PRINCIPAL_DOES_NOT_EXIST);
                default:
                    throw new RuntimeException(INTERNAL_ERROR_TAG + result.name());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all principals in the Messaging Access Control.
     *
     * <p>Requires permission com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_QUERY.
     *
     * @return A {@link List} of all principals in the Messaging Access Control. If there are no
     * principals an empty list is returned.
     * @throws SecurityException if caller has no relevant permission.
     */
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_QUERY)
    public List<Principal> getAllPrincipals() {
        try {
            return helper.get().getAllPrincipals();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a Principal using its name and type.
     *
     * <p>Requires permission com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_QUERY.
     *
     * @param principalName The name of the principal.
     * @param principalType The {@link PrincipalType} of the principal.
     * @return The matching principal if any, {@code null} otherwise.
     * @throws SecurityException if caller has no relevant permission.
     */
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_QUERY)
    public Principal getPrincipal(@NonNull final String principalName,
                                  @NonNull final PrincipalType principalType) {
        Objects.requireNonNull(principalName);
        Objects.requireNonNull(principalType);
        try {
            return helper.get().getPrincipal(principalName, principalType);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get a Principal in messaging access control using it's UUID.
     *
     * <p>Requires permission com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_QUERY.
     *
     * @param uuid The UUID of the principal.
     * @return The matching principal if any, {@code null} otherwise.
     * @throws SecurityException if caller has no relevant permission.
     */
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_QUERY)
    public Principal getPrincipalByUuid(@NonNull final String uuid) {
        Objects.requireNonNull(uuid);
        try {
            return helper.get().getPrincipalByUuid(uuid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the principal representing the universal group. The universal group is
     * unique and contains all other principals. Any rule added to universal group principal
     * is applicable to all principals, but has low priority than any principal's own
     * rules for making access control decisions.
     *
     * <p>Requires permission com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_QUERY.
     *
     * @return The principal for universal group.
     * @throws SecurityException if caller has no relevant permission.
     */
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_QUERY)
    public Principal getUniversalGroupPrincipal() {
            return getPrincipal(Constants.UNIVERSAL_GROUP_NAME, PrincipalType.UNIVERSAL_GROUP);
    }

    /**
     * Get the set of package names of apps which are whitelisted in Messaging Access Control.
     *
     * <p>Requires permission com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_QUERY.
     *
     * @return {@link Set} of package names of whitelisted applications.
     * @throws SecurityException if caller has no relevant permission.
     */
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_QUERY)
    public Set<String> getWhitelistedPackageNames() {
        try {
            return new HashSet<>(helper.get().getWhitelistedPackageNames());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check access of a principal. The {@code topicExpression} can be an
     * MQTT topic expression or an MQTT topic filter expression.
     *
     * <p>Requires permission com.securityandsafetythings.permission.MESSAGING_ACCESS_CONTROL_QUERY.
     *
     * @param principalName   Name of the principal for which access is checked.
     * @param principalType   Type of the principal for which access is checked.
     * @param topicExpression An MQTT Topic expression or MQTT Topic filter expression
     *                        under check.
     * @param accessType      {@link AccessType} under check.
     * @return Returns {@link MessagingAccessControlManager#ACCESS_ALLOWED} if access is allowed,
     *         or {@link MessagingAccessControlManager#ACCESS_DENIED} if access is denied.
     * @throws SecurityException if caller has no relevant permission.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of types:
     *         {@link MessagingAccessControlException.Type#CANNOT_RESOLVE_RULES_CONFLICT} is
     *         thrown if there are clashing rules in messaging access control which cannot
     *         be resolved.
     *        {@link MessagingAccessControlException.Type#ACCESS_TO_SOME_TOPICS_ARE_INDETERMINABLE}
     *        if access to some of the topics in the current check cannot be determined.
     *        Consumer application need to settle for a more specific set of topics.
     */
    // Workaround for checkstyle bug: checkstyle complains about missing SecurityException
    // in the method signature. See: https://github.com/checkstyle/checkstyle/issues/5088
    @SuppressWarnings("checkstyle:javadocmethod")
    @RequiresPermission(MESSAGING_ACCESS_CONTROL_QUERY)
    public int checkAccess(@NonNull final String principalName,
                           @NonNull final PrincipalType principalType,
                           @NonNull final String topicExpression,
                           @NonNull final AccessType accessType)
            throws MessagingAccessControlException {
        Objects.requireNonNull(principalName);
        Objects.requireNonNull(principalType);
        Objects.requireNonNull(topicExpression);
        Objects.requireNonNull(accessType);
        try {
            MessagingAccessControlResult result = helper.get().checkAccess(
                    principalName,
                    principalType,
                    topicExpression,
                    accessType);

            return handleCheckAccessResult(result);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Same as {@link #close()}.
     */
    public void disconnect() {
        close();
    }

    /**
     * Unbind from the service and cleanup all resources.
     */
    @Override
    public void close() {
        helper.close();
    }

    /**
     * 바로 위의 MessagingAccessControlManager 메서드 호출로부터 얻어진 결과를 처리하는 메서드
     * Method to handle the result of {@link MessagingAccessControlResult} from a
     * {@link MessagingAccessControlManager#checkAccess(String, PrincipalType, String, AccessType)}
     * call.
     *
     * @param checkAccessResult {@link MessagingAccessControlResult} obtained from
     *                          check access result call.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of types:
     *        {@link MessagingAccessControlException.Type#CANNOT_RESOLVE_RULES_CONFLICT} if
     *        there are conflicting rules which cannot be resolved,
     *        {@link MessagingAccessControlException.Type#ACCESS_TO_SOME_TOPICS_ARE_INDETERMINABLE}
     *        if access to some of the topics in the current check cannot be determined.
     *        Consumer application need to settle for a more specific set of topics.
     * @hide
     */
    public static int handleCheckAccessResult(MessagingAccessControlResult checkAccessResult)
            throws MessagingAccessControlException {
        switch (checkAccessResult) {
            case ACCESS_ALLOWED:
                return ACCESS_ALLOWED;
            case ACCESS_DENIED:
                return ACCESS_DENIED;
            case ACCESS_TO_SOME_TOPICS_ARE_INDETERMINABLE:
                throw new MessagingAccessControlException(
                        MessagingAccessControlException.Type
                                .ACCESS_TO_SOME_TOPICS_ARE_INDETERMINABLE);
            case CANNOT_RESOLVE_RULES_CONFLICT:
                throw new MessagingAccessControlException(
                        MessagingAccessControlException.Type
                                .CANNOT_RESOLVE_RULES_CONFLICT);
            default:
                throw new RuntimeException(INTERNAL_ERROR_TAG + checkAccessResult.name());
        }
    }
}
