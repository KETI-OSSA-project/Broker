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
import android.util.Log;
import com.securityandsafetythings.ParcelUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Principal class is used to represent the entity whose messaging access is being
 * controlled (Application, Group etc). The enum {@link PrincipalType} signifies the type
 * of the principal.
 *
 * <p>A {@link Principal} object can be obtained using the constructor
 * {@link Principal#Principal(String, PrincipalType)}.
 *
 * <p>As of now, {@link Principal} of types {@link PrincipalType#APPLICATION},
 * {@link PrincipalType#UNIVERSAL_GROUP} are supported. The {@code principalName} for an application
 * is recommended to be the package name of the application whose access is being controlled.
 * <h4>Usage of Principal Constructor for an application:</h4>
 * <pre><code>
 *     //Package name of application
 *     String packageName = "com.example.app";
 *     //Create a principal object
 *     Principal principal = new Principal(packageName,PrincipalType.APPLICATION);
 *     ...
 * </code></pre>
 *
 * <p>Rules can be configured in the principal using APIs {@link #add(Rule)}, {@link #remove(Rule)},
 * {@link #isPresent(Rule)} and {@link #getAllRules()}.
 * <h4>Usage of APIs:</h4>
 * <pre><code>
 *     //A rule to deny publishing for an application on any topic.
 *     Rule ruleDenyAll = new Rule("#", AccessType.PUBLISH, ControlType.DENY);
 *
 *     //Add rule to a principal object
 *     principal.add(ruleDenyAll);
 *
 *     //Use {@link MessagingAccessControlManager#add(Principal)} or
 *     //{@link MessagingAccessControlManager#update(Principal)} accordingly for
 *     //the configuration to reflect in the Messaging Access Control.
 *     ...
 *
 *     //Check whether a rule is present in the principal.
 *     boolean isPresent = principal.isPresent(ruleDenyAll);
 *
 *     //Get all the rules in a principal
 *     Set&lt;Rule&gt; rules = new principal.getAllRules();
 *
 *     //remove a rule from the principal
 *     principal.remove(ruleDenyAll);
 *
 *     //Use {@link MessagingAccessControlManager#add(Principal)} or
 *     //{@link MessagingAccessControlManager#update(Principal)} accordingly for
 *     //the configuration to reflect in the Messaging Access Control.
 *     ...
 * </code></pre>
 *
 * @hide
 */
@SystemApi
public class Principal implements Parcelable {
    private static final String TAG = Principal.class.getCanonicalName();
    private final PrincipalType type;
    private final String principalName;
    private final HashSet<Rule> rules;
    private String principalUuid = null;

    /**
     * Constructor.
     *
     * <p>Constructor for creating a Principal.
     *
     * @param principalName Name of the principal.
     * @param type          {@link PrincipalType} of the principal.
     */
    public Principal(@NonNull final String principalName, @NonNull final PrincipalType type) {
        this(principalName, type, true);
    }

    /**
     * Constructor.
     *
     * <p>Constructor for creating a Principal including principal for universal groups.
     *
     * @param principalName                  Name of the principal.
     * @param type                           {@link PrincipalType} of the principal.
     * @param blockUniversalGroupConstruction Variable to control construction of
     *                                        principals of type universal group.
     *
     * @hide
     */
    public Principal(@NonNull final String principalName,
                     @NonNull final PrincipalType type,
                     @NonNull boolean blockUniversalGroupConstruction) {
        this.principalUuid = UUID.randomUUID().toString();
        this.principalName = Objects.requireNonNull(principalName);
        this.type = Objects.requireNonNull(type);
        Objects.requireNonNull(blockUniversalGroupConstruction);
        this.rules = new HashSet<>();
        if (blockUniversalGroupConstruction && this.type == PrincipalType.UNIVERSAL_GROUP) {
            throw new IllegalArgumentException("A principal of universal group type cannot" +
                    " be constructed as it is unique in messaging access control. Consider" +
                    " getting the object directly from messaging access control using the" +
                    " relevant API.");
        }
    }

    /**
     * Add a {@link Rule} for the principal.
     *
     * @param rule The {@link Rule} to be added in the principal.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of type
     *                        {@link MessagingAccessControlException.Type#RULE_ALREADY_EXISTS} if
     *                        the rule is already present in the principal.
     *                        A {@link MessagingAccessControlException} of type
     *                        {@link MessagingAccessControlException.Type#CONFLICTING_RULE}
     *                        if the rule is conflicting with other rules in in the principal.
     */
    public void add(@NonNull final Rule rule) throws MessagingAccessControlException {
        Objects.requireNonNull(rule);
        if (rules.contains(rule)) {
            throw new MessagingAccessControlException(
                    MessagingAccessControlException.Type.RULE_ALREADY_EXISTS);
        } else if (isConflicting(rule)) {
            throw new MessagingAccessControlException(
                    MessagingAccessControlException.Type.CONFLICTING_RULE);
        } else {
            synchronized (rules) {
                rules.add(rule);
            }
        }
    }

    /**
     * Remove a {@link Rule} from the principal.
     *
     * @param rule The {@link Rule} to be removed from the principal.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} of type
     *                        {@link MessagingAccessControlException.Type#RULE_DOES_NOT_EXIST} if
     *                        the rule is absent in the principal.
     */
    public void remove(@NonNull final Rule rule) throws MessagingAccessControlException {
        Objects.requireNonNull(rule);
        synchronized (rules) {
            if (!rules.remove(rule)) {
                throw new MessagingAccessControlException(
                        MessagingAccessControlException.Type.RULE_DOES_NOT_EXIST);
            }
        }
    }

    /**
     * Check whether a {@link Rule} is present in the principal.
     *
     * @param rule The {@link Rule} for which the presence should be checked.
     * @return {@code true} if the rule is already present, {@code false} if the rule is absent.
     */
    public boolean isPresent(@NonNull final Rule rule) {
        Objects.requireNonNull(rule);
        return rules.contains(rule);
    }

    /**
     * Get all the rules present in a principal.
     *
     * @return {@link Set} of rules present in the principal. If there are no rules in the
     * principal, an empty set is returned.
     */
    public Set<Rule> getAllRules() {
        return new HashSet<>(rules);
    }

    /**
     * Get an ordered list of rules present in a principal.
     *
     * <p>The rules are ordered based on which is more specific.</p>
     *
     * @return {@link List} of rules present in the principal. If there are no rules in the
     * principal, an empty list is returned.
     */
    public List<Rule> getAllRulesInOrder() {
        try {
            RuleGraph ruleGraph = new RuleGraph(rules);
            return ruleGraph.getRulesInOrder();
        } catch (MessagingAccessControlException exception) {
            //not expected to happen as Principal has a set of rules which is already validated
            // against such exceptions
            Log.i(TAG,"Rules in principal is unusually incorrect");
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Get all the rules present in a principal applicable to the given topic and access type.
     *
     * @param topicExpression An MQTT Topic filter expression which need to be
     *                        checked with the rules.
     * @param accessType      {@link AccessType} to check with the rules.
     * @return {@link Set} of rules applicable to the given {@code accessType} and
     * {@code topicExpression}. If there are no rules applicable, an empty List is returned.
     *
     * @hide
     */
    public Set<Rule> getApplicableRules(final String topicExpression,
                                        final AccessType accessType) {
        return rules.stream()
                .filter(rule -> rule.isApplicableTo(topicExpression, accessType))
                .collect(Collectors.toSet());
    }

    /**
     * Computes hash code based for the principal.
     */
    @Override
    public int hashCode() {
        //fetch all the rules as comparable objects
        List<Comparable> objects = new ArrayList<>(this.rules);
        //Sort the collection to have persistent hashCode.
        Collections.sort(objects);

        //add type and principalName also
        objects.add(type);
        objects.add(principalName);

        return Objects.hash(objects.toArray());
    }

    /**
     * Compares with another principal.
     */
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == this) {
            return true;
        }

        if (!(otherObject instanceof Principal)) {
            return false;
        }

        Principal otherPrincipal = (Principal) otherObject;
        return Objects.equals(this.type, otherPrincipal.type)
                && Objects.equals(this.principalName, otherPrincipal.principalName)
                && (this.rules.size() == otherPrincipal.rules.size())
                && (this.rules.containsAll(otherPrincipal.rules));
    }

    public static final Creator<Principal> CREATOR = new Creator<Principal>() {
        @Override
        public Principal createFromParcel(Parcel in) {
            return new Principal(in);
        }

        @Override
        public Principal[] newArray(int size) {
            return new Principal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Get name of the principal.
     *
     * @return name of the principal.
     */
    public String getName() {
        return this.principalName;
    }

    /**
     * Get type of the principal.
     *
     * @return {@link PrincipalType} of the principal.
     */
    public PrincipalType getType() {
        return this.type;
    }

    /**
     * Get UUID of the principal.
     *
     * @return the UUID of the principal.
     */
    public String getUuid() {
        return principalUuid;
    }

    /**
     * Set UUID of the principal.
     *
     * @hide
     */
    public void setUuid(@NonNull final String uuid) {
        this.principalUuid = Objects.requireNonNull(uuid);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(principalUuid);
        this.type.writeToParcel(dest, flags);
        dest.writeString(principalName);
        ParcelUtils.writeParcelableSet(this.rules, dest, flags);
    }

    private Principal(Parcel in) {
        this.principalUuid = in.readString();
        this.type = PrincipalType.CREATOR.createFromParcel(in);
        this.principalName = in.readString();
        rules = ParcelUtils.readParcelableSet(in, Rule.CREATOR);
    }

    /**
     * Method to check whether rules having same topic set and same {@link AccessType}
     * but different {@link ControlType} is already present in the principal.
     * Also it checks whether a rule is intersecting with any other rule in the principal
     * (this behaviour is temporary, and will be removed once intersecting rules can be
     * handled).
     */
    private boolean isConflicting(Rule ruleToCheck) {
        for (ControlType controlType : ControlType.values()) {
            if (!controlType.equals(ruleToCheck.getControlType())) {
                Rule candidateForConflictingRule = new Rule(ruleToCheck.getTopicExpression(),
                        ruleToCheck.getAccessType(),
                        controlType);
                if (rules.contains(candidateForConflictingRule)) {
                    return true;
                }
            }
        }
        return false;
    }
}
