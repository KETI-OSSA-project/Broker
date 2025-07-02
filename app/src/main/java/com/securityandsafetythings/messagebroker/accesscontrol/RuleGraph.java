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

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Graph structure for ordering rules in a principal.
 *
 * @hide
 */
class RuleGraph {
    private static final Integer PROPER_SUBSET = Integer.valueOf(-1);
    private static final Integer PROPER_SUPERSET = Integer.valueOf(1);
    private static final Integer ARE_EQUALS = Integer.valueOf(0);

    private List<Node> allNodes;
    private List<Node> rootNodes;
    private List<Node> leafNodes;

    RuleGraph() {
        this.allNodes = new ArrayList<>();
        this.rootNodes = new ArrayList<>();
        this.leafNodes = new ArrayList<>();
    }

    /**
     * Construct a rule graph from a given set of rules.
     *
     * @param rules rules for the graph
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} is thrown
     * for the following scenarios:
     * An exception of type {@link MessagingAccessControlException.Type#CONFLICTING_RULE}  if there
     * are multiple rules with same topic and access type but different control type.
     * An exception of type {@link MessagingAccessControlException.Type#RULE_ALREADY_EXISTS}  if
     * there is repetition of rules.
     * @hide
     */
    RuleGraph(@NonNull final Set<Rule> rules) throws MessagingAccessControlException {
        this();
        this.addRules(Objects.requireNonNull(rules));
    }

    private void addRules(Set<Rule> rules) throws MessagingAccessControlException {
        for (Rule rule : rules) {
            this.addRule(rule);
        }
    }

    /**
     * Get the root nodes in the graph.
     *
     * @return root nodes in the graph.
     * @hide
     */
    List<Node> getRootNodes() {
        return new ArrayList<>(rootNodes);
    }

    /**
     * Get the leaf nodes in the graph.
     *
     * @return leaf nodes in the graph.
     * @hide
     */
    List<Node> getLeafNodes() {
        return new ArrayList<>(leafNodes);
    }

    /**
     * Add rule to the graph.
     *
     * @param rule rule to be added to the graph.
     * @throws MessagingAccessControlException A {@link MessagingAccessControlException} is thrown
     * for the following scenarios:
     * An exception of type {@link MessagingAccessControlException.Type#CONFLICTING_RULE}  if there
     * are multiple rules with same topic and access type but different control type.
     * An exception of type {@link MessagingAccessControlException.Type#RULE_ALREADY_EXISTS}  if
     * there is repetition of rules.
     * @hide
     */
    void addRule(@NonNull final Rule rule) throws MessagingAccessControlException {
        Node newNode = new Node(Objects.requireNonNull(rule));
        if (this.allNodes.isEmpty()) {
            this.leafNodes.add(newNode);
            this.rootNodes.add(newNode);
            this.allNodes.add(newNode);
            return;
        }
        IterationResult iterationResult = new IterationResult();
        iterationResult = iterateWithTheNodes(this.rootNodes, newNode, iterationResult);
        this.allNodes.add(newNode);

        //if no parents are added for the new node it is a root node
        if (!iterationResult.hasParentAdded()) {
            this.rootNodes.add(newNode);
        }
        //if no children are added for the new node it is a leaf node
        if (!iterationResult.hasChildAdded()) {
            this.leafNodes.add(newNode);
        }
        /*
         * If there are cases like
         *   [#]---------->[A/#]
         *    |              |
         *    |              |
         *    |              V
         *    \----------->[A/B]
         * keep only [#]---------->[A/#]-------->[A/B], and
         * remove direct link [#]----->[A/B].
         * */
        this.allNodes.forEach(node -> node.preferIndirectRelations());
    }

    private IterationResult iterateWithTheNodes(List<Node> nodes,
                                                Node newNode,
                                                IterationResult iterationResult)
            throws MessagingAccessControlException {
        for (Iterator<Node> iterator = nodes.iterator(); iterator.hasNext(); ) {
            Node node = iterator.next();
            if (!node.isRuleAccessTypeSame(newNode)) {
                continue;
            }
            Integer nodeComparisonResult = node.compareTopicSetTo(newNode);
            if (nodeComparisonResult == null) {
                //new node has no proper-subset or proper-superset relation with current node,
                //but it might be a superset/parent of a node in the sub-graph.
                iterationResult = iterateWithTheNodes(node.children, newNode, iterationResult);

            } else if (nodeComparisonResult.equals(PROPER_SUBSET)) {
                newNode.addChild(node);
                node.addParent(newNode);
                if (isRootNode(node)) {
                    iterator.remove();
                }
                iterationResult.setChildAdded();

            } else if (nodeComparisonResult.equals(PROPER_SUPERSET)) {
                iterationResult.resetAddedInSubGraph();
                iterationResult = iterateWithTheNodes(node.children, newNode, iterationResult);
                if (!iterationResult.wasAddedInSubGraph()) {
                    node.addChild(newNode);
                    newNode.addParent(node);
                    iterationResult.setAddedInSubGraph();
                    if (isLeafNode(node)) {
                        this.leafNodes.remove(node);
                    }
                    iterationResult.setParentAdded();
                }

            } else if (nodeComparisonResult.equals(ARE_EQUALS)) {
                newNode.cutRelationshipTies();
                if (!node.isRuleControlTypeSame(newNode)) {
                    throw new MessagingAccessControlException(
                            MessagingAccessControlException.Type.CONFLICTING_RULE);
                } else {
                    throw new MessagingAccessControlException(
                            MessagingAccessControlException.Type.RULE_ALREADY_EXISTS);
                }
            }
        }
        return iterationResult;
    }

    private boolean isRootNode(Node node) {
        return this.rootNodes.contains(node);
    }

    private boolean isLeafNode(Node node) {
        return this.leafNodes.contains(node);
    }

    /**
     * Get the node in the graph linked to a particular rule if present.
     *
     * @param rule to be searched in the graph.
     * @return The related node if any, {@code null} otherwise.
     * @hide
     */
    Node getNode(@NonNull final Rule rule) {
        Objects.requireNonNull(rule);
        Optional<Node> searchResult = this.allNodes.stream()
                .filter(node -> node.rule.equals(rule))
                .findAny();

        return searchResult.orElseGet(null);
    }

    /**
     * Get the ordered rules in the graph.
     * This call is destructive and removes all the rules from the graph, thus this graph is for
     * one time use with this call.
     *
     * @return list of rules
     * @hide
     */
    List<Rule> getRulesInOrder() {
        List<Rule> publishRules = new ArrayList<>();
        List<Rule> subscribeRules = new ArrayList<>();
        while (!rootNodes.isEmpty()) {
            List<Rule> poppedRules = this.popRootNodeRules();
            for (Rule poppedRule : poppedRules) {
                if (poppedRule.getAccessType().equals(AccessType.PUBLISH)) {
                    publishRules.add(poppedRule);
                } else {
                    subscribeRules.add(poppedRule);
                }
            }
        }
        List<Rule> orderedRules = publishRules;
        orderedRules.addAll(subscribeRules);
        return orderedRules;
    }

    private List<Rule> popRootNodeRules() {
        List<Rule> rules = new ArrayList<>();
        List<Node> newRootNodes = new ArrayList<>();
        for (Iterator<Node> iterator = this.rootNodes.iterator(); iterator.hasNext(); ) {
            Node rootNode = iterator.next();
            rules.add(rootNode.rule);
            List<Node> children = new ArrayList<>(rootNode.children);
            rootNode.cutRelationshipTies();
            for (Node child : children) {
                if (!child.hasParent()) {
                    newRootNodes.add(child);
                }
            }
            this.allNodes.remove(rootNode);
            iterator.remove();
        }
        this.rootNodes.addAll(newRootNodes);
        return rules;
    }

    /**
     * Class to represent a node in the graph and represents a rule.
     *
     * @hide
     */
    static class Node {
        private List<Node> parents = new ArrayList<>();
        private Rule rule;
        private List<Node> children = new ArrayList<>();

        private Node(Rule rule) {
            this.rule = rule;
        }

        private boolean isDescendantOf(Node node) {
            if (this.parents.isEmpty()) {
                return false;
            }
            if (this.parents.contains(node)) {
                return true;
            }
            return this.parents.stream().anyMatch(parentNode -> parentNode.isDescendantOf(node));
        }

        @Override
        public boolean equals(final Object other) {
            if (!(other instanceof Node)) {
                return false;
            }

            Node otherNode = (Node) other;
            return Objects.equals(this.rule, otherNode.rule);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.rule.hashCode());
        }

        /**
         * Get topic expression of the rule.
         *
         * @return Topic expression of the rule.
         * @hide
         */
        String getRuleTopicExpression() {
            return this.rule.getTopicExpression();
        }

        private boolean isRuleAccessTypeSame(Node node) {
            return this.rule.getAccessType().equals(node.rule.getAccessType());
        }

        /**
         * Get the access type of the rule.
         *
         * @return Access type of rule.
         * @hide
         */
        AccessType getAccessType() {
            return this.rule.getAccessType();
        }

        private boolean isRuleControlTypeSame(Node node) {
            return this.rule.getControlType().equals(node.rule.getControlType());
        }

        private Integer compareTopicSetTo(Node node) {
            return this.rule.compareTopicSetTo(node.rule);
        }

        private void cutRelationshipTies() {
            for (Node child : this.children) {
                child.parents.remove(this);
            }
            this.children.clear();
            for (Node parent : this.parents) {
                parent.children.remove(this);
            }
            this.parents.clear();
        }

        private void addParent(Node node) {
            if (!this.parents.contains(node)) {
                this.parents.add(node);
            }
        }

        private void addChild(Node node) {
            if (!this.children.contains(node)) {
                this.children.add(node);
            }
        }

        /**
         * Get the parent nodes of the node.
         *
         * @return Parent nodes.
         * @hide
         */
        List<Node> getParents() {
            return new ArrayList<>(this.parents);
        }

        /**
         * Get the child nodes of the node.
         *
         * @return Child nodes.
         * @hide
         */
        List<Node> getChildren() {
            return new ArrayList<>(this.children);
        }

        /**
         * Check whether the node has child nodes.
         *
         * @return Returns {@code true} if the node has children, {@code false} otherwise.
         * @hide
         */
        boolean hasChildren() {
            return this.children.size() > 0;
        }

        /**
         * Check whether the node has parent nodes.
         *
         * @return {@code true} if the node has children, {@code false} otherwise.
         * @hide
         */
        boolean hasParent() {
            return this.parents.size() > 0;
        }

        private void preferIndirectRelations() {
            Iterator<Node> parentNodesIterator = this.parents.iterator();
            while (parentNodesIterator.hasNext()) {
                Node parentNode = parentNodesIterator.next();
                boolean isIndirectAncestor
                        = this.parents
                        .stream()
                        .anyMatch(otherParent -> otherParent.isDescendantOf(parentNode));
                if (isIndirectAncestor) {
                    parentNode.children.remove(this);
                    parentNodesIterator.remove();
                }
            }
        }
    }

    private class IterationResult {
        private boolean parentAdded = false;
        private boolean childAdded = false;
        private boolean addedInSubGraph = false;

        boolean hasParentAdded() {
            return this.parentAdded;
        }

        void setParentAdded() {
            this.parentAdded = true;
        }

        boolean hasChildAdded() {
            return this.childAdded;
        }

        void setAddedInSubGraph() {
            this.addedInSubGraph = true;
        }

        void resetAddedInSubGraph() {
            this.addedInSubGraph = false;
        }

        boolean wasAddedInSubGraph() {
            return this.addedInSubGraph;
        }

        void setChildAdded() {
            this.childAdded = true;
        }
    }
}
