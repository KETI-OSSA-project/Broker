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

import android.annotation.TestApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.securityandsafetythings.ParcelUtils;
import com.securityandsafetythings.messagebroker.Constants;
import com.securityandsafetythings.messagebroker.MessageHeaderKeyNames;
import com.securityandsafetythings.messagebroker.MessagingManager;
import com.securityandsafetythings.messagebroker.MqttTopicUtils;
import com.securityandsafetythings.messagebroker.PortType;
import com.securityandsafetythings.messagebroker.ports.attributes.ExactAttribute;
import com.securityandsafetythings.messagebroker.ports.attributes.HierarchicalAttribute;
import com.securityandsafetythings.messagebroker.ports.payloads.PayloadSchema;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Holds the set of configuration for creating a port.
 */
public class PortConfiguration implements Parcelable {
    private static final String PUBLISHER_ID = MessageHeaderKeyNames.PUBLISHER_ID;
    private static final String TOPIC = MessageHeaderKeyNames.TOPIC;
    private PayloadSchema payloadSchema = null;
    private final PortType portType;
    private HashMap<String, HierarchicalAttribute> hierarchicalAttributeMap = new HashMap<>();
    private HashMap<String, ExactAttribute> exactAttributeMap = new HashMap<>();
    private static final String TAG = PortConfiguration.class.getSimpleName();
    private boolean autoConnect = true;
    private String portName;
    private static final String TAG_REQUIRED = "required";
    private static final String TAG_KEY = "key";
    private static final String TAG_VALUE = "value";
    private static final String TAG_TRUE = "true";
    private String description;

    public static final Creator<PortConfiguration> CREATOR =
            new Creator<PortConfiguration>() {
                @Override
                public PortConfiguration createFromParcel(Parcel in) {
                    return new PortConfiguration(in);
                }

                @Override
                public PortConfiguration[] newArray(int size) {
                    return new PortConfiguration[size];
                }
            };

    protected PortConfiguration(Parcel in) {
        this.portType = PortType.CREATOR.createFromParcel(in);
        this.exactAttributeMap
                = in.readHashMap(ExactAttribute.class.getClassLoader());
        this.hierarchicalAttributeMap
                = in.readHashMap(HierarchicalAttribute.class.getClassLoader());
        this.payloadSchema
                = in.readParcelable(PayloadSchema.class.getClassLoader());
        this.autoConnect = ParcelUtils.readBoolean(in);
        this.portName = in.readString();
        this.description = in.readString();
    }

    private PortConfiguration(@NonNull final PortType portType) {
        Objects.requireNonNull(portType);
        this.portType = portType;
    }

    /**
     * Create PortConfiguration reading from the configuration portSchema.xml contained in the
     * applications assets folder. The method throws {@link RuntimeException} in case of errors.
     */
    public static PortConfiguration createPortConfigurationFromAssets(@NonNull Context context,
                                                                      @NonNull String portName) {
        return Utils.createPortConfiguration(portName, context);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        this.portType.writeToParcel(dest, flags);
        dest.writeMap(this.exactAttributeMap);
        dest.writeMap(this.hierarchicalAttributeMap);
        dest.writeParcelable(payloadSchema, flags);
        ParcelUtils.writeBoolean(dest, autoConnect);
        dest.writeString(portName);
        dest.writeString(description);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof PortConfiguration)) {
            return false;
        }
        PortConfiguration otherPortConfiguration = (PortConfiguration) object;
        return this.portType.equals(otherPortConfiguration.portType)
                && Objects.equals(this.payloadSchema, otherPortConfiguration.payloadSchema)
                && this.hierarchicalAttributeMap.equals(
                otherPortConfiguration.hierarchicalAttributeMap)
                && this.exactAttributeMap.equals(
                otherPortConfiguration.exactAttributeMap)
                && (this.autoConnect == otherPortConfiguration.autoConnect)
                && (this.portName.equals(otherPortConfiguration.portName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.payloadSchema,
                this.portType.ordinal(),
                getHierarchicalAttributesHashCode(this.hierarchicalAttributeMap),
                getExactAttributesHashCode(this.exactAttributeMap),
                this.autoConnect,
                this.portName
        );
    }

    private int getHierarchicalAttributesHashCode(HashMap<String,
            HierarchicalAttribute> hashMap) {
        // Order of HashMap entries can be random based on the hashing algorithm and size.
        // So getting it sorted in natural order of keys of the entries.
        ArrayList<HierarchicalAttribute> hierarchicalAttributes = new ArrayList<>(hashMap.values());

        Collections.sort(hierarchicalAttributes,
                Comparator.comparing(HierarchicalAttribute::getKey));

        return Arrays.hashCode(hierarchicalAttributes.toArray());
    }

    private int getExactAttributesHashCode(HashMap<String, ExactAttribute> hashMap) {
        // Order of HashMap entries can be random based on the hashing algorithm and size.
        // So getting it sorted in natural order of keys of the entries.
        ArrayList<ExactAttribute> exactAttributes = new ArrayList<>(hashMap.values());

        Collections.sort(exactAttributes, Comparator.comparing(ExactAttribute::getKey));

        return Arrays.hashCode(exactAttributes.toArray());
    }

    /**
     * Get hierarchical attribute map of this port.
     *
     * @return HashMap of hierarchical attributes.
     */
    public HashMap<String, HierarchicalAttribute> getHierarchicalAttributes() {
        return new HashMap<>(this.hierarchicalAttributeMap);
    }

    /**
     * Get exact attribute map of this port.
     *
     * @return HashMap of exact attributes.
     */
    public HashMap<String, ExactAttribute> getExactAttributes() {
        return new HashMap<>(this.exactAttributeMap);
    }

    private void addHierarchicalAttribute(@NonNull final HierarchicalAttribute attribute) {
        Objects.requireNonNull(attribute);
        attribute.checkAttributeSyntax(this.portType);
        this.hierarchicalAttributeMap.put(attribute.getKey(), attribute);
    }

    private void addExactAttribute(@NonNull final ExactAttribute attribute) {
        Objects.requireNonNull(attribute);
        this.exactAttributeMap.put(attribute.getKey(), attribute);
    }

    private void setPayloadSchema(@NonNull final PayloadSchema jsonSchema) {
        Objects.requireNonNull(jsonSchema);
        this.payloadSchema = jsonSchema;
    }

    /**
     * Get the payload schema.
     *
     * @return Payload schema in the port configuration.
     */
    public PayloadSchema getPayloadSchema() {
        return payloadSchema;
    }

    /**
     * Get if the port is auto connectable.
     *
     * @return True, if the port is auto-connectable, false otherwise.
     */
    public boolean isAutoConnectable() {
        return autoConnect;
    }

    /**
     * Sets the publisherId of the message. Used internally.
     *
     * @param publisherId package name derived from the application context passed during the
     *                    initialization of the {@link MessagingManager}.
     * @hide
     */
    public void setPublisherId(@NonNull final String publisherId) {
        Objects.requireNonNull(publisherId);
        HierarchicalAttribute hierarchicalAttribute
                = new HierarchicalAttribute(PUBLISHER_ID, publisherId, false);
        this.hierarchicalAttributeMap.put(hierarchicalAttribute.getKey(), hierarchicalAttribute);
    }

    /**
     * Sets the config file name. Used internally for testing purpose.
     *
     * @param configFileName File name from which the schema for portConfiguration is read.
     * @hide
     */
    @TestApi
    public static void setConfigFileName(@NonNull final String configFileName) {
        Utils.setConfigFileName(configFileName);
    }

    /**
     * Get the type of the port.
     *
     * @return Type of the port.
     */
    public PortType getPortType() {
        return portType;
    }

    /**
     * Get the portName of the message Port.
     *
     * @return portName of the message Port.
     */
    public String getPortName() {
        return portName;
    }

    /**
     * Get the description of the message Port.
     *
     * @return description of the message Port.
     */
    public String getDescription() {
        return description;
    }

    private static boolean matchHierarchical(
            final HashMap<String, HierarchicalAttribute> inPortAttributeMap,
            HashMap<String, HierarchicalAttribute> outPortAttributeMap) {
        //From inPort side
        for (Map.Entry<String, HierarchicalAttribute> inAttributeEntry
                : inPortAttributeMap.entrySet()) {
            String key = inAttributeEntry.getKey();
            HierarchicalAttribute inAttribute = inAttributeEntry.getValue();

            if (outPortAttributeMap.containsKey(key)) {
                //do a hierarchical match
                if (!MqttTopicUtils.match(inAttribute.getValue(),
                        outPortAttributeMap.get(key).getValue())) {
                    return false;
                }
            } else {
                if (inAttribute.isRequired()) {
                    return false;
                }
            }
        }
        //From outPort side
        for (Map.Entry<String, HierarchicalAttribute> outAttributeEntry
                : outPortAttributeMap.entrySet()) {
            String key = outAttributeEntry.getKey();
            HierarchicalAttribute outAttribute = outAttributeEntry.getValue();

            if (inPortAttributeMap.containsKey(key)) {
                //do a hierarchical match
                if (!MqttTopicUtils.match(
                        inPortAttributeMap.get(key).getValue(), outAttribute.getValue())) {
                    return false;
                }
            } else {
                if (outAttribute.isRequired()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean matchExact(HashMap<String, ExactAttribute> inPortAttributeMap,
                                      HashMap<String, ExactAttribute> outPortAttributeMap) {
        //From inPort side
        for (Map.Entry<String, ExactAttribute> entry : inPortAttributeMap.entrySet()) {
            //Check if key exists, if not return false
            String key = entry.getKey();
            if (outPortAttributeMap.containsKey(key)) {
                //Check if value matches to inPort value, if not return false
                if (!outPortAttributeMap.get(key).equals(entry.getValue())) {
                    return false;
                }
            } else {
                if (entry.getValue().isRequired()) {
                    return false;
                }
            }
        }

        //From outPort side
        for (
                Map.Entry<String, ExactAttribute> entry : outPortAttributeMap.entrySet()) {
            //Check if key exists, if not return false
            String key = entry.getKey();
            if (inPortAttributeMap.containsKey(key)) {
                //Check if value matches to inPort value, if not return false
                if (!inPortAttributeMap.get(key).equals(entry.getValue())) {
                    return false;
                }
            } else {
                if (entry.getValue().isRequired()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean matchPayloadSchema(@Nullable PayloadSchema inPortSchema,
                                       @Nullable PayloadSchema outPortSchema) {

        //Solve required flag
        if (inPortSchema == null) {
            return (outPortSchema == null) || !(outPortSchema.isRequired());
        } else {
            if (outPortSchema == null) {
                return !inPortSchema.isRequired();
            }
        }

        String inSchema = inPortSchema.getSchema();
        String outSchema = outPortSchema.getSchema();
        if (inSchema == null || outSchema == null) {
            return false;
        }

        return inSchema.equals(outSchema);
    }

    /**
     * Match a port configuration to another.
     *
     * @param portConfiguration another port configuration.
     * @return returns true if the configurations has a match, false otherwise.
     */
    public boolean matches(PortConfiguration portConfiguration) {
        //if the port is of same type it is not a match for sure.
        if (this.portType == portConfiguration.portType) {
            return false;
        }

        //Figure out in-port and out-port
        PortConfiguration inPortConfiguration = this;
        PortConfiguration outPortConfiguration = portConfiguration;
        if (this.portType.equals(PortType.OUTPORT)) {
            inPortConfiguration = portConfiguration;
            outPortConfiguration = this;
        }

        //do matching
        //TODO: optimize with union of keys while matching
        return matchExact(inPortConfiguration.exactAttributeMap,
                outPortConfiguration.exactAttributeMap)
                && matchHierarchical(inPortConfiguration.hierarchicalAttributeMap,
                outPortConfiguration.hierarchicalAttributeMap)
                && matchPayloadSchema(inPortConfiguration.payloadSchema,
                outPortConfiguration.payloadSchema);
    }

    /**
     * Builder class to create a port configuration.
     *
     * @hide
     */
    public static class Builder {
        private final PortConfiguration portConfiguration;

        /**
         * Constructor to create Builder object for building a PortConfiguration.
         *
         * @param portType type for the port.
         * @param topic    topic for the port.
         */
        public Builder(@NonNull final PortType portType, @NonNull final String topic) {
            Objects.requireNonNull(topic);
            this.portConfiguration = new PortConfiguration(portType);
            HierarchicalAttribute hierarchicalAttribute = new HierarchicalAttribute(TOPIC, topic,
                    false);
            this.portConfiguration.addHierarchicalAttribute(hierarchicalAttribute);
            this.portConfiguration.portName = topic;
        }

        /**
         * Add a hierarchical attribute for the port configuration.
         *
         * @param hierarchicalAttribute a hierarchical attribute to be added.
         * @return Builder object.
         * @throws IllegalArgumentException is thrown on following scenarios:
         *                                  <ul>
         *                                  <li>for
         *                                  OutPort,
         *                                  if the value does not follow the topic syntax rules
         *                                  described in MQTT v.5.0 section 4.7.
         *                                  </li>
         *                                  <li>for
         *                                  InPort, if the
         *                                  value does not follow the filter syntax rules described
         *                                  in MQTT v.5.0 section 4.7.
         *                                  </li>
         *                                  </ul>
         */
        public Builder addHierarchicalAttribute(
                @NonNull final HierarchicalAttribute hierarchicalAttribute) {
            this.portConfiguration.addHierarchicalAttribute(
                    Objects.requireNonNull(hierarchicalAttribute));
            return this;
        }

        /**
         * Add an exact attribute for the port configuration.
         *
         * @param exactAttribute an exact attribute to be added.
         * @return Builder object.
         */
        public Builder addExactAttribute(
                @NonNull final ExactAttribute exactAttribute) {
            this.portConfiguration.addExactAttribute(Objects.requireNonNull(exactAttribute));
            return this;
        }

        /**
         * Set the payload schema for the port.
         *
         * <p>The default value for payload schema is {@code null}.</p>
         *
         * @param payloadSchema payload schema for the port.
         * @return Builder object.
         */
        public Builder setPayloadSchema(
                @NonNull final PayloadSchema payloadSchema) {
            //TODO: Update the following
            this.portConfiguration.setPayloadSchema(Objects.requireNonNull(payloadSchema));
            return this;
        }

        /**
         * Set the portName of the message Port.
         *
         * <p>The default portName is the topic itself.</p>
         *
         * @param portName Name of the port.
         * @return Builder object.
         */
        public Builder setPortName(
                @NonNull final String portName) {
            this.portConfiguration.portName = Objects.requireNonNull(portName);
            return this;
        }

        /**
         * Set the description of the message Port.
         *
         * <p>Description of the port.</p>
         *
         * @param description Description of the port.
         * @return Builder object.
         */
        public Builder setDescription(
                @NonNull final String description) {
            this.portConfiguration.description = Objects.requireNonNull(description);
            return this;
        }

        /**
         * Set if the port should be automatically connected with the ports that are matching.
         *
         * <p>The default value of autoConnect is {@code true}. Setting this false would need
         * device user to manually connect to other matching ports.</p>
         *
         * @param autoConnect {@code true} if the port should be automatically connected with
         *                    other matching ports.
         *                    {@code false} if the device user need to manually connect to other
         *                    matching ports. The default value of autoConnect is
         *                    {@code true}.
         * @return Builder object.
         */
        public Builder setAutoConnect(boolean autoConnect) {
            this.portConfiguration.autoConnect = autoConnect;
            return this;
        }

        /**
         * Build the PortConfiguration.
         *
         * @return PortConfiguration object.
         */
        public PortConfiguration build() {
            //TODO: Check consistency
            return this.portConfiguration;
        }
    }

    private enum AttributeType {
        HIERARCHICAL("HierarchicalAttribute"),
        EXACT("ExactAttribute");
        private final String attributeName;

        AttributeType(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeName() {
            return attributeName;
        }
    }

    private static class Utils {

        private static String CONFIG_FILE_NAME = Constants.MESSAGING_MANIFEST_FILE_NAME;

        private Utils() {

        }

        public static void setConfigFileName(String configFileName) {
            CONFIG_FILE_NAME = configFileName;
        }

        private static String readConfigurationXml(Context context, String fileName) {
            if (fileName == null) {
                fileName = CONFIG_FILE_NAME;
            }
            AssetManager assetManager = context.getAssets();
            try {
                InputStream stream = assetManager.open(fileName);
                BufferedReader r = new BufferedReader(new InputStreamReader(stream));
                StringBuilder total = new StringBuilder();
                String line = r.readLine();
                while (line != null) {
                    total.append(line).append('\n');
                    line = r.readLine();
                }
                return total.toString();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                throw new RuntimeException("Failed to read schema file.");
            }
        }

        private static Node fromStringToNode(String xmlString) {
            if (xmlString == null) {
                return null;
            }
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(xmlString)));
                if (document == null) {
                    return null;
                }
                return document.getFirstChild();
            } catch (SAXException | IOException | ParserConfigurationException e) {
                Log.e(TAG, e.toString());
                throw new RuntimeException("Failed to parse the provided xml.");
            }
        }

        public static PortConfiguration createPortConfiguration(String portName, Context context) {
            Objects.requireNonNull(portName);
            Objects.requireNonNull(context);
            String xmlString = readConfigurationXml(context, null);

            Node rootNode = fromStringToNode(xmlString);
            String schemaVersion = getNodeAttribute(rootNode, "schemaVersion");
            if (schemaVersion == null) {
                throw new RuntimeException("schemaVersion attribute must be declared at the" +
                        " application level");
            }

            if (!schemaVersion.equals("1.0")) {
                throw new RuntimeException("Invalid Schema version found. Schema version"
                        + schemaVersion + "not supported");
            }

            Node matchingNode = getMatchingPort(rootNode, portName);
            if (matchingNode == null) {
                return null;
            }

            PortType portType = getPortType(matchingNode);
            if (portType == null) {
                throw new RuntimeException("PortType not declared");
            }

            Set<HierarchicalAttribute> hierarchicalAttributes =
                    getHierarchicalAttributes(matchingNode);
            Set<ExactAttribute> exactAttributes = getExactAttributes(matchingNode);
            String topic = getNodeAttribute(matchingNode, "topic");
            if (topic == null) {
                throw new RuntimeException("Topic cannot be null");
            }

            final String portDescription = getPortDescription(matchingNode);
            PayloadSchema payloadSchema = constructPayloadSchema(matchingNode, context);

            PortConfiguration.Builder builder = new PortConfiguration.Builder(portType, topic);
            for (HierarchicalAttribute h : hierarchicalAttributes) {
                if (!checkIfAttributeIsTopicAttribute(h)) {
                    builder.addHierarchicalAttribute(h);
                }
            }

            for (ExactAttribute e : exactAttributes) {
                builder.addExactAttribute(e);
            }

            if (payloadSchema != null) {
                builder.setPayloadSchema(payloadSchema);
            }
            if (portDescription != null) {
                builder.setDescription(portDescription);
            }

            builder.setPortName(portName);
            return builder.build();
        }

        private static String getNodeAttribute(Node portNode, String attributeKey) {
            Objects.requireNonNull(portNode);
            Objects.requireNonNull(attributeKey);
            NamedNodeMap nmap = portNode.getAttributes();
            if (nmap != null) {
                for (int j = 0; j < nmap.getLength(); j++) {
                    Node n = nmap.item(j);
                    if (n.getNodeName().equals(attributeKey)) {
                        return n.getNodeValue();
                    }
                }
            }
            return null;
        }

        private static String readPayloadSchemaFromFile(Node payloadNode, Context context) {
            Node schemaFileNode = getChildNode(payloadNode, "SchemaFile");
            if (schemaFileNode != null) {
                String schemaFilePath = getNodeAttribute(schemaFileNode, "path");
                if (schemaFilePath == null) {
                    return null;
                }
                return readConfigurationXml(context, schemaFilePath);
            }
            return null;
        }

        private static String readPayloadFromSchemaBase64(Node payloadNode) {
            Node schemaNodeBase64 = getChildNode(payloadNode, "SchemaBase64");
            if (schemaNodeBase64 == null) {
                return null;
            }
            // Giving preference to Base64 data over file data.
            String schema = schemaNodeBase64.getTextContent();
            if (schema == null) {
                return null;
            }
            // The schema is encoded in base64 according to the xsd specification.
            // Decode it to get the actual payload schema.
            schema = new String(Base64.getDecoder()
                    .decode(removeSpaceAndNewLineCharacters(schema)));
            return schema;
        }

        private static PayloadSchema constructPayloadSchema(Node parentNode, Context context) {

            Node payloadNode = getPayloadSchemaNode(parentNode);
            if (payloadNode == null) {
                return null;
            }
            boolean required = false;
            String description = null;

            String schema = readPayloadSchemaFromFile(payloadNode, context);
            if (schema == null) {
                // try reading from base64
                schema = readPayloadFromSchemaBase64(payloadNode);
            }
            if (schema == null) {
                throw new RuntimeException("Failed to read payload schema. Remove Schema node " +
                        "from the PortConfiguration if you do not intend " +
                        "to provide a payload schema.");
            }
            Node descriptionNode = getDescriptionNode(payloadNode);
            if (descriptionNode != null) {
                description = descriptionNode.getTextContent();
            }

            NamedNodeMap nmap = payloadNode.getAttributes();
            if (nmap != null) {
                for (int j = 0; j < nmap.getLength(); j++) {
                    Node n = nmap.item(j);
                    if (n.getNodeName().equals(TAG_REQUIRED)) {
                        if (n.getNodeValue().equals(TAG_TRUE)) {
                            required = true;
                            break;
                        }
                    }
                }
            }

            PayloadSchema.Builder builder = new PayloadSchema.Builder(schema);
            if (description != null) {
                builder.setDescription(description);
            }
            return builder.setRequired(required)
                    .build();
        }

        private static String removeSpaceAndNewLineCharacters(String schema) {
            return schema.replaceAll("[\\n\\t ]", "");
        }

        private static boolean checkIfAttributeIsTopicAttribute(HierarchicalAttribute h) {
            return h.getKey().equals("topic");
        }

        private static PortType getPortType(Node node) {
            NamedNodeMap nmap = node.getAttributes();
            if (nmap != null) {
                for (int j = 0; j < nmap.getLength(); j++) {
                    Node n = nmap.item(j);
                    if (n.getNodeName().equals("type")) {
                        if (n.getNodeValue().equals("OutPort")) {
                            return PortType.OUTPORT;
                        } else {
                            return PortType.INPORT;
                        }
                    }
                }
            }
            return null;
        }

        private static String getPortDescription(Node portNode) {

            Node description = getDescriptionNode(portNode);
            if (description != null) {
                return description.getTextContent();
            }
            return null;
        }

        private static Set<HierarchicalAttribute> getHierarchicalAttributes(Node portNode) {

            Set<Node> hierarchicalNodes = getNodes(portNode, AttributeType.HIERARCHICAL);
            Set<HierarchicalAttribute> hierarchicalAttributes = new HashSet<>();
            for (Node n : hierarchicalNodes) {
                hierarchicalAttributes.add(constructHierarchicalAttribute(n));
            }
            return hierarchicalAttributes;
        }

        private static Set<ExactAttribute> getExactAttributes(Node portNode) {

            Set<Node> exactNodes = getNodes(portNode, AttributeType.EXACT);
            Set<ExactAttribute> exactAttributes = new HashSet<>();
            for (Node n : exactNodes) {
                exactAttributes.add(constructExactAttribute(n));
            }
            return exactAttributes;
        }

        private static HierarchicalAttribute constructHierarchicalAttribute(Node node) {
            String key = null;
            String value = null;
            boolean required = false;
            String description = null;
            NamedNodeMap nmap = node.getAttributes();
            if (nmap != null) {
                for (int j = 0; j < nmap.getLength(); j++) {
                    Node n = nmap.item(j);
                    if (n.getNodeName().equals(TAG_KEY)) {
                        key = n.getNodeValue();
                    }
                    if (n.getNodeName().equals(TAG_VALUE)) {
                        value = n.getNodeValue();
                    }
                    if (n.getNodeName().equals(TAG_REQUIRED)) {
                        if (n.getNodeValue().equals(TAG_TRUE)) {
                            required = true;
                        }
                    }
                }
            }
            Node descriptionNode = getDescriptionNode(node);
            if (descriptionNode != null) {
                description = descriptionNode.getTextContent();
            }
            // Raise exception if key or value is null (A runtime exception is
            // generated while passing the null
            // values to the HierarchicalAttribute constructor)
            return new HierarchicalAttribute(key, value, required, description);
        }

        private static ExactAttribute constructExactAttribute(Node node) {
            String key = null;
            String value = null;
            boolean required = false;
            String description = null;
            NamedNodeMap nmap = node.getAttributes();
            if (nmap != null) {
                for (int j = 0; j < nmap.getLength(); j++) {
                    Node n = nmap.item(j);
                    if (n.getNodeName().equals(TAG_KEY)) {
                        key = n.getNodeValue();
                    }
                    if (n.getNodeName().equals(TAG_VALUE)) {
                        value = n.getNodeValue();
                    }
                    if (n.getNodeName().equals(TAG_REQUIRED)) {
                        if (n.getNodeValue().equals(TAG_TRUE)) {
                            required = true;
                        }
                    }
                }
            }
            Node descriptionNode = getDescriptionNode(node);
            if (descriptionNode != null) {
                description = descriptionNode.getTextContent();
            }
            // Raise exception if key or value is null (A runtime exception is generated
            // while passing the null
            // values to the ExactAttribute constructor)
            return new ExactAttribute(key, value, required, description);
        }

        private static Node getDescriptionNode(Node parentNode) {
            NodeList nodeList = parentNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeName().equals("Description")) {
                    return currentNode;
                }
            }
            return null;
        }

        private static Node getChildNode(Node parentNode, String childNodeName) {
            NodeList nodeList = parentNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeName().equals(childNodeName)) {
                    return currentNode;
                }
            }
            return null;
        }

        private static Set<Node> getNodes(Node portNode, AttributeType attributeType) {

            Set<Node> hNodes = new HashSet<>();

            NodeList nodeList = portNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                // Check if the node belongs to the provided attributeType class
                if (currentNode.getNodeName().equals(attributeType.getAttributeName())) {
                    hNodes.add(currentNode);
                }
            }
            return hNodes;
        }

        private static Node getPayloadSchemaNode(Node portNode) {

            NodeList nodeList = portNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                // Check if the node is PayloadSchema node
                if (currentNode.getNodeName().equals("PayloadSchema")) {
                    return currentNode;
                }
            }
            return null;
        }

        private static Node getMatchingPort(Node rootNode, String portName) {
            NodeList nodeList = rootNode.getChildNodes();
            boolean portAlreadyFound = false;
            Node matchingPortNode = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                //Check if the node name is PortConfiguration
                if (currentNode.getNodeName().equals("PortConfiguration")) {
                    // Get list of attributes
                    NamedNodeMap namedNodeMap = currentNode.getAttributes();
                    if (namedNodeMap != null) {
                        for (int j = 0; j < namedNodeMap.getLength(); j++) {
                            Node n = namedNodeMap.item(j);
                            //Check if attribute name is portName
                            if (n.getNodeName().equals("name")
                                    && (n.getNodeValue().equals(portName))) {
                                if (portAlreadyFound) {
                                    throw new RuntimeException("More than one port declared with" +
                                            " the given port name");
                                }
                                portAlreadyFound = true;
                                matchingPortNode = currentNode;
                            }

                        }
                    }

                }

            }
            return matchingPortNode;
        }

    }

}
