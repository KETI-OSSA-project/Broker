/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker;
/**
 * Message broker service API interface which will be implemented by the service.
 *
 * @hide
 */
public interface IMessagingService extends android.os.IInterface
{
  /** Default implementation for IMessagingService. */
  public static class Default implements com.securityandsafetythings.messagebroker.IMessagingService
  {
    /**
         * Publish the message
         *
         * @param Message containing the message
         *
         */
    @Override public void publish(com.securityandsafetythings.messagebroker.Message message) throws android.os.RemoteException
    {
    }
    /**
         * Subscribe to listen the message for a given list of types.
         *
         * @param messageListener       MessageListener Stub
         * @param messageListenerHash   Hash of the MessageListener Stub used for the bookkeeping
         * @param filter                Filter object for filtering the messages
         * @param packageName           Package name of the subscriber application
         * @return                      MessageBrokerResult instance indicating the status code of the
         *                              operation
         */
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult subscribe(com.securityandsafetythings.messagebroker.IMessageListener messageListener, int messageListenerHash, com.securityandsafetythings.messagebroker.Filter filter, java.lang.String packageName) throws android.os.RemoteException
    {
      return null;
    }
    /**
         * Unsubscribe listening for the given listener.
         *
         * @param messageListenerHash  Hash of the MessageListener Stub used for the bookkeeping
         * @param filter               Filter object to be removed from subscription
         * @return                     MessageBrokerResult instance indicating the status code of the
         *                             operation
         *
         */
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult unsubscribe(int messageListenerHash, com.securityandsafetythings.messagebroker.Filter filter) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Unsubscribe listening for the given listener.
        * (Note: Overloading of methods is not supported in AIDL files)
        *
        * @param messageListenerHash  Hash of the MessageListener Stub used for the bookkeeping
        * @return                     MessageBrokerResult instance indicating the status code of the
        *                             operation
        */
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult unsubscribeAll(int messageListenerHash) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Add a principal for messaging access control.
        *
        * @param principal            The principal object to be added.
        * @return                     MessagingAccessControlResult instance indicating the status
        *                             code of the operation
        */
    @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult add(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Remove a principal from the messaging access control.
        *
        * @param principal            The principal object to be removed.
        * @return                     MessagingAccessControlResult instance indicating the status
        *                             code of the operation
        */
    @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult remove(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Update a principal in the messaging access control.
        *
        * @param principal            The principal object to be updated.
        * @return                     MessagingAccessControlResult instance indicating the status
        *                             code of the operation
        */
    @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult update(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get all princials in the messaging access control.
        *
        * @return                     List of all principals in the messaging access control.
        */
    @Override public java.util.List<com.securityandsafetythings.messagebroker.accesscontrol.Principal> getAllPrincipals() throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get a princial in the messaging access control.
        *
        * @param principalName        Name of the principal to be fetched.
        * @return                     The matching principal found.
        */
    @Override public com.securityandsafetythings.messagebroker.accesscontrol.Principal getPrincipal(java.lang.String principalName, com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType principalType) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get a princial in the messaging access control using its UUID.
        *
        * @param uuid                UUID of the principal in messaging access control.
        * @return                    The matching principal found.
        */
    @Override public com.securityandsafetythings.messagebroker.accesscontrol.Principal getPrincipalByUuid(java.lang.String uuid) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Check access for a principal.
        *
        * @param principalName        Name of the principal for which access is checked.
        * @param principalType        Type of the principal for which access is checked.
        * @param topicExpression      An MQTT Topic expression or MQTT Topic filter expression
        *                             under check.
        * @param accessType           AccessType under check.
        * @return                     MessagingAccessControlResult instance signifying the result of
                                      the check.
        */
    @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult checkAccess(java.lang.String principalName, com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType principalType, java.lang.String topicExpression, com.securityandsafetythings.messagebroker.accesscontrol.AccessType accessType) throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get list of package names of apps which are whitelisted in Messaging Access Control.
        *
        * @return                     List of package names of applications whitelisted applications.
        */
    @Override public java.util.List<java.lang.String> getWhitelistedPackageNames() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.PortResponse createInPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration, java.lang.String applicationId, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.PortResponse createOutPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration, java.lang.String applicationId, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getMatchingOutPorts(java.lang.String inPortUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getMatchingInPorts(java.lang.String outPortUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getInPortWithUid(java.lang.String portUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getOutPortWithUid(java.lang.String portUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getEdgeWithUid(java.lang.String edgeUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getCurrentState() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse createEdge(java.lang.String inPortUid, java.lang.String outPortUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getAllInPorts() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getAllOutPorts() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> getAllInPortConfigurations() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> getAllOutPortConfigurations() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getAllEdges() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult addAttribute(java.lang.String applicationId, java.lang.String key, java.lang.String value) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult removeAttribute(java.lang.String applicationId, java.lang.String key) throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.Map getAttributes(java.lang.String applicationId) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.PortGroupResponse createPortGroup(java.lang.String groupName, java.lang.String applicationPackageName, java.lang.String groupUid, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.PortGroupResponse> getAllPortGroups() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.ApplicationStitchingResponse> getAllApplicationBlocks() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.PortGroupResponse getPortGroupWithUid(java.lang.String portGroupUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult cleanDatabaseRemnants(java.lang.String applicationId) throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.IMessagingService
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.IMessagingService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.IMessagingService interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.IMessagingService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.IMessagingService))) {
        return ((com.securityandsafetythings.messagebroker.IMessagingService)iin);
      }
      return new com.securityandsafetythings.messagebroker.IMessagingService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_publish:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.Message _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.Message.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          this.publish(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_subscribe:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.IMessageListener _arg0;
          _arg0 = com.securityandsafetythings.messagebroker.IMessageListener.Stub.asInterface(data.readStrongBinder());
          int _arg1;
          _arg1 = data.readInt();
          com.securityandsafetythings.messagebroker.Filter _arg2;
          if ((0!=data.readInt())) {
            _arg2 = com.securityandsafetythings.messagebroker.Filter.CREATOR.createFromParcel(data);
          }
          else {
            _arg2 = null;
          }
          java.lang.String _arg3;
          _arg3 = data.readString();
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.subscribe(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_unsubscribe:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          com.securityandsafetythings.messagebroker.Filter _arg1;
          if ((0!=data.readInt())) {
            _arg1 = com.securityandsafetythings.messagebroker.Filter.CREATOR.createFromParcel(data);
          }
          else {
            _arg1 = null;
          }
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.unsubscribe(_arg0, _arg1);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_unsubscribeAll:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.unsubscribeAll(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_add:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.accesscontrol.Principal _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.accesscontrol.Principal.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result = this.add(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_remove:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.accesscontrol.Principal _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.accesscontrol.Principal.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result = this.remove(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_update:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.accesscontrol.Principal _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.accesscontrol.Principal.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result = this.update(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getAllPrincipals:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.accesscontrol.Principal> _result = this.getAllPrincipals();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getPrincipal:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType _arg1;
          if ((0!=data.readInt())) {
            _arg1 = com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType.CREATOR.createFromParcel(data);
          }
          else {
            _arg1 = null;
          }
          com.securityandsafetythings.messagebroker.accesscontrol.Principal _result = this.getPrincipal(_arg0, _arg1);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getPrincipalByUuid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.accesscontrol.Principal _result = this.getPrincipalByUuid(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_checkAccess:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType _arg1;
          if ((0!=data.readInt())) {
            _arg1 = com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType.CREATOR.createFromParcel(data);
          }
          else {
            _arg1 = null;
          }
          java.lang.String _arg2;
          _arg2 = data.readString();
          com.securityandsafetythings.messagebroker.accesscontrol.AccessType _arg3;
          if ((0!=data.readInt())) {
            _arg3 = com.securityandsafetythings.messagebroker.accesscontrol.AccessType.CREATOR.createFromParcel(data);
          }
          else {
            _arg3 = null;
          }
          com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result = this.checkAccess(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getWhitelistedPackageNames:
        {
          data.enforceInterface(descriptor);
          java.util.List<java.lang.String> _result = this.getWhitelistedPackageNames();
          reply.writeNoException();
          reply.writeStringList(_result);
          return true;
        }
        case TRANSACTION_createInPort:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConfiguration _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.ports.PortConfiguration.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          java.lang.String _arg1;
          _arg1 = data.readString();
          com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener _arg2;
          _arg2 = com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener.Stub.asInterface(data.readStrongBinder());
          com.securityandsafetythings.messagebroker.PortResponse _result = this.createInPort(_arg0, _arg1, _arg2);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_createOutPort:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConfiguration _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.ports.PortConfiguration.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          java.lang.String _arg1;
          _arg1 = data.readString();
          com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener _arg2;
          _arg2 = com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener.Stub.asInterface(data.readStrongBinder());
          com.securityandsafetythings.messagebroker.PortResponse _result = this.createOutPort(_arg0, _arg1, _arg2);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getMatchingOutPorts:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getMatchingOutPorts(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getMatchingInPorts:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getMatchingInPorts(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getInPortWithUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getInPortWithUid(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getOutPortWithUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getOutPortWithUid(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getEdgeWithUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getEdgeWithUid(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getCurrentState:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getCurrentState();
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_createEdge:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.createEdge(_arg0, _arg1);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getAllInPorts:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result = this.getAllInPorts();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getAllOutPorts:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result = this.getAllOutPorts();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getAllInPortConfigurations:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> _result = this.getAllInPortConfigurations();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getAllOutPortConfigurations:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> _result = this.getAllOutPortConfigurations();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getAllEdges:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result = this.getAllEdges();
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_addAttribute:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          java.lang.String _arg2;
          _arg2 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result = this.addAttribute(_arg0, _arg1, _arg2);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_removeAttribute:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result = this.removeAttribute(_arg0, _arg1);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getAttributes:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.util.Map _result = this.getAttributes(_arg0);
          reply.writeNoException();
          reply.writeMap(_result);
          return true;
        }
        case TRANSACTION_createPortGroup:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          java.lang.String _arg2;
          _arg2 = data.readString();
          com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener _arg3;
          _arg3 = com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener.Stub.asInterface(data.readStrongBinder());
          com.securityandsafetythings.messagebroker.PortGroupResponse _result = this.createPortGroup(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_getAllPortGroups:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.PortGroupResponse> _result = this.getAllPortGroups();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getAllApplicationBlocks:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.ApplicationStitchingResponse> _result = this.getAllApplicationBlocks();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getPortGroupWithUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.PortGroupResponse _result = this.getPortGroupWithUid(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_cleanDatabaseRemnants:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.cleanDatabaseRemnants(_arg0);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.IMessagingService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
           * Publish the message
           *
           * @param Message containing the message
           *
           */
      @Override public void publish(com.securityandsafetythings.messagebroker.Message message) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((message!=null)) {
            _data.writeInt(1);
            message.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_publish, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().publish(message);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
           * Subscribe to listen the message for a given list of types.
           *
           * @param messageListener       MessageListener Stub
           * @param messageListenerHash   Hash of the MessageListener Stub used for the bookkeeping
           * @param filter                Filter object for filtering the messages
           * @param packageName           Package name of the subscriber application
           * @return                      MessageBrokerResult instance indicating the status code of the
           *                              operation
           */
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult subscribe(com.securityandsafetythings.messagebroker.IMessageListener messageListener, int messageListenerHash, com.securityandsafetythings.messagebroker.Filter filter, java.lang.String packageName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((messageListener!=null))?(messageListener.asBinder()):(null)));
          _data.writeInt(messageListenerHash);
          if ((filter!=null)) {
            _data.writeInt(1);
            filter.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeString(packageName);
          boolean _status = mRemote.transact(Stub.TRANSACTION_subscribe, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().subscribe(messageListener, messageListenerHash, filter, packageName);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.MessageBrokerResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * Unsubscribe listening for the given listener.
           *
           * @param messageListenerHash  Hash of the MessageListener Stub used for the bookkeeping
           * @param filter               Filter object to be removed from subscription
           * @return                     MessageBrokerResult instance indicating the status code of the
           *                             operation
           *
           */
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult unsubscribe(int messageListenerHash, com.securityandsafetythings.messagebroker.Filter filter) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(messageListenerHash);
          if ((filter!=null)) {
            _data.writeInt(1);
            filter.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_unsubscribe, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().unsubscribe(messageListenerHash, filter);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.MessageBrokerResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Unsubscribe listening for the given listener.
          * (Note: Overloading of methods is not supported in AIDL files)
          *
          * @param messageListenerHash  Hash of the MessageListener Stub used for the bookkeeping
          * @return                     MessageBrokerResult instance indicating the status code of the
          *                             operation
          */
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult unsubscribeAll(int messageListenerHash) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(messageListenerHash);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unsubscribeAll, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().unsubscribeAll(messageListenerHash);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.MessageBrokerResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Add a principal for messaging access control.
          *
          * @param principal            The principal object to be added.
          * @return                     MessagingAccessControlResult instance indicating the status
          *                             code of the operation
          */
      @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult add(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((principal!=null)) {
            _data.writeInt(1);
            principal.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_add, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().add(principal);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Remove a principal from the messaging access control.
          *
          * @param principal            The principal object to be removed.
          * @return                     MessagingAccessControlResult instance indicating the status
          *                             code of the operation
          */
      @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult remove(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((principal!=null)) {
            _data.writeInt(1);
            principal.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_remove, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().remove(principal);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Update a principal in the messaging access control.
          *
          * @param principal            The principal object to be updated.
          * @return                     MessagingAccessControlResult instance indicating the status
          *                             code of the operation
          */
      @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult update(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((principal!=null)) {
            _data.writeInt(1);
            principal.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_update, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().update(principal);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get all princials in the messaging access control.
          *
          * @return                     List of all principals in the messaging access control.
          */
      @Override public java.util.List<com.securityandsafetythings.messagebroker.accesscontrol.Principal> getAllPrincipals() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.accesscontrol.Principal> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllPrincipals, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllPrincipals();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.accesscontrol.Principal.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get a princial in the messaging access control.
          *
          * @param principalName        Name of the principal to be fetched.
          * @return                     The matching principal found.
          */
      @Override public com.securityandsafetythings.messagebroker.accesscontrol.Principal getPrincipal(java.lang.String principalName, com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType principalType) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.accesscontrol.Principal _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(principalName);
          if ((principalType!=null)) {
            _data.writeInt(1);
            principalType.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrincipal, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPrincipal(principalName, principalType);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.accesscontrol.Principal.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get a princial in the messaging access control using its UUID.
          *
          * @param uuid                UUID of the principal in messaging access control.
          * @return                    The matching principal found.
          */
      @Override public com.securityandsafetythings.messagebroker.accesscontrol.Principal getPrincipalByUuid(java.lang.String uuid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.accesscontrol.Principal _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(uuid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrincipalByUuid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPrincipalByUuid(uuid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.accesscontrol.Principal.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Check access for a principal.
          *
          * @param principalName        Name of the principal for which access is checked.
          * @param principalType        Type of the principal for which access is checked.
          * @param topicExpression      An MQTT Topic expression or MQTT Topic filter expression
          *                             under check.
          * @param accessType           AccessType under check.
          * @return                     MessagingAccessControlResult instance signifying the result of
                                        the check.
          */
      @Override public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult checkAccess(java.lang.String principalName, com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType principalType, java.lang.String topicExpression, com.securityandsafetythings.messagebroker.accesscontrol.AccessType accessType) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(principalName);
          if ((principalType!=null)) {
            _data.writeInt(1);
            principalType.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeString(topicExpression);
          if ((accessType!=null)) {
            _data.writeInt(1);
            accessType.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_checkAccess, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().checkAccess(principalName, principalType, topicExpression, accessType);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get list of package names of apps which are whitelisted in Messaging Access Control.
          *
          * @return                     List of package names of applications whitelisted applications.
          */
      @Override public java.util.List<java.lang.String> getWhitelistedPackageNames() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<java.lang.String> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getWhitelistedPackageNames, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getWhitelistedPackageNames();
          }
          _reply.readException();
          _result = _reply.createStringArrayList();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.PortResponse createInPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration, java.lang.String applicationId, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.PortResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((portConfiguration!=null)) {
            _data.writeInt(1);
            portConfiguration.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeString(applicationId);
          _data.writeStrongBinder((((messagingServiceConnectionListener!=null))?(messagingServiceConnectionListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_createInPort, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createInPort(portConfiguration, applicationId, messagingServiceConnectionListener);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.PortResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.PortResponse createOutPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration, java.lang.String applicationId, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.PortResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((portConfiguration!=null)) {
            _data.writeInt(1);
            portConfiguration.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeString(applicationId);
          _data.writeStrongBinder((((messagingServiceConnectionListener!=null))?(messagingServiceConnectionListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_createOutPort, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createOutPort(portConfiguration, applicationId, messagingServiceConnectionListener);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.PortResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getMatchingOutPorts(java.lang.String inPortUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(inPortUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getMatchingOutPorts, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getMatchingOutPorts(inPortUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getMatchingInPorts(java.lang.String outPortUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(outPortUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getMatchingInPorts, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getMatchingInPorts(outPortUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getInPortWithUid(java.lang.String portUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(portUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getInPortWithUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getInPortWithUid(portUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getOutPortWithUid(java.lang.String portUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(portUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOutPortWithUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOutPortWithUid(portUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getEdgeWithUid(java.lang.String edgeUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(edgeUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getEdgeWithUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getEdgeWithUid(edgeUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getCurrentState() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getCurrentState, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getCurrentState();
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse createEdge(java.lang.String inPortUid, java.lang.String outPortUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(inPortUid);
          _data.writeString(outPortUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_createEdge, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createEdge(inPortUid, outPortUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getAllInPorts() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllInPorts, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllInPorts();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.PortResponse.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getAllOutPorts() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllOutPorts, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllOutPorts();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.PortResponse.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> getAllInPortConfigurations() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllInPortConfigurations, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllInPortConfigurations();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.ports.PortConfiguration.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> getAllOutPortConfigurations() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllOutPortConfigurations, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllOutPortConfigurations();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.ports.PortConfiguration.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getAllEdges() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllEdges, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllEdges();
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult addAttribute(java.lang.String applicationId, java.lang.String key, java.lang.String value) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(applicationId);
          _data.writeString(key);
          _data.writeString(value);
          boolean _status = mRemote.transact(Stub.TRANSACTION_addAttribute, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().addAttribute(applicationId, key, value);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult removeAttribute(java.lang.String applicationId, java.lang.String key) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(applicationId);
          _data.writeString(key);
          boolean _status = mRemote.transact(Stub.TRANSACTION_removeAttribute, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().removeAttribute(applicationId, key);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ApplicationStitchingResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.Map getAttributes(java.lang.String applicationId) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.Map _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(applicationId);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAttributes, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAttributes(applicationId);
          }
          _reply.readException();
          java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
          _result = _reply.readHashMap(cl);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.PortGroupResponse createPortGroup(java.lang.String groupName, java.lang.String applicationPackageName, java.lang.String groupUid, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.PortGroupResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(groupName);
          _data.writeString(applicationPackageName);
          _data.writeString(groupUid);
          _data.writeStrongBinder((((messagingServiceConnectionListener!=null))?(messagingServiceConnectionListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_createPortGroup, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createPortGroup(groupName, applicationPackageName, groupUid, messagingServiceConnectionListener);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.PortGroupResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.List<com.securityandsafetythings.messagebroker.PortGroupResponse> getAllPortGroups() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.PortGroupResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllPortGroups, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllPortGroups();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.PortGroupResponse.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.List<com.securityandsafetythings.messagebroker.ApplicationStitchingResponse> getAllApplicationBlocks() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.ApplicationStitchingResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllApplicationBlocks, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllApplicationBlocks();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.ApplicationStitchingResponse.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.PortGroupResponse getPortGroupWithUid(java.lang.String portGroupUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.PortGroupResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(portGroupUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPortGroupWithUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPortGroupWithUid(portGroupUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.PortGroupResponse.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult cleanDatabaseRemnants(java.lang.String applicationId) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(applicationId);
          boolean _status = mRemote.transact(Stub.TRANSACTION_cleanDatabaseRemnants, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().cleanDatabaseRemnants(applicationId);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.MessageBrokerResult.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.securityandsafetythings.messagebroker.IMessagingService sDefaultImpl;
    }
    static final int TRANSACTION_publish = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_subscribe = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_unsubscribe = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_unsubscribeAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_add = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_remove = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_update = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getAllPrincipals = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_getPrincipal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_getPrincipalByUuid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_checkAccess = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_getWhitelistedPackageNames = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_createInPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_createOutPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_getMatchingOutPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
    static final int TRANSACTION_getMatchingInPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
    static final int TRANSACTION_getInPortWithUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
    static final int TRANSACTION_getOutPortWithUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
    static final int TRANSACTION_getEdgeWithUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
    static final int TRANSACTION_getCurrentState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
    static final int TRANSACTION_createEdge = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
    static final int TRANSACTION_getAllInPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
    static final int TRANSACTION_getAllOutPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
    static final int TRANSACTION_getAllInPortConfigurations = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
    static final int TRANSACTION_getAllOutPortConfigurations = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
    static final int TRANSACTION_getAllEdges = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
    static final int TRANSACTION_addAttribute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
    static final int TRANSACTION_removeAttribute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
    static final int TRANSACTION_getAttributes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
    static final int TRANSACTION_createPortGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
    static final int TRANSACTION_getAllPortGroups = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
    static final int TRANSACTION_getAllApplicationBlocks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
    static final int TRANSACTION_getPortGroupWithUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
    static final int TRANSACTION_cleanDatabaseRemnants = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.IMessagingService impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.IMessagingService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
       * Publish the message
       *
       * @param Message containing the message
       *
       */
  public void publish(com.securityandsafetythings.messagebroker.Message message) throws android.os.RemoteException;
  /**
       * Subscribe to listen the message for a given list of types.
       *
       * @param messageListener       MessageListener Stub
       * @param messageListenerHash   Hash of the MessageListener Stub used for the bookkeeping
       * @param filter                Filter object for filtering the messages
       * @param packageName           Package name of the subscriber application
       * @return                      MessageBrokerResult instance indicating the status code of the
       *                              operation
       */
  public com.securityandsafetythings.messagebroker.MessageBrokerResult subscribe(com.securityandsafetythings.messagebroker.IMessageListener messageListener, int messageListenerHash, com.securityandsafetythings.messagebroker.Filter filter, java.lang.String packageName) throws android.os.RemoteException;
  /**
       * Unsubscribe listening for the given listener.
       *
       * @param messageListenerHash  Hash of the MessageListener Stub used for the bookkeeping
       * @param filter               Filter object to be removed from subscription
       * @return                     MessageBrokerResult instance indicating the status code of the
       *                             operation
       *
       */
  public com.securityandsafetythings.messagebroker.MessageBrokerResult unsubscribe(int messageListenerHash, com.securityandsafetythings.messagebroker.Filter filter) throws android.os.RemoteException;
  /**
      * Unsubscribe listening for the given listener.
      * (Note: Overloading of methods is not supported in AIDL files)
      *
      * @param messageListenerHash  Hash of the MessageListener Stub used for the bookkeeping
      * @return                     MessageBrokerResult instance indicating the status code of the
      *                             operation
      */
  public com.securityandsafetythings.messagebroker.MessageBrokerResult unsubscribeAll(int messageListenerHash) throws android.os.RemoteException;
  /**
      * Add a principal for messaging access control.
      *
      * @param principal            The principal object to be added.
      * @return                     MessagingAccessControlResult instance indicating the status
      *                             code of the operation
      */
  public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult add(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException;
  /**
      * Remove a principal from the messaging access control.
      *
      * @param principal            The principal object to be removed.
      * @return                     MessagingAccessControlResult instance indicating the status
      *                             code of the operation
      */
  public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult remove(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException;
  /**
      * Update a principal in the messaging access control.
      *
      * @param principal            The principal object to be updated.
      * @return                     MessagingAccessControlResult instance indicating the status
      *                             code of the operation
      */
  public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult update(com.securityandsafetythings.messagebroker.accesscontrol.Principal principal) throws android.os.RemoteException;
  /**
      * Get all princials in the messaging access control.
      *
      * @return                     List of all principals in the messaging access control.
      */
  public java.util.List<com.securityandsafetythings.messagebroker.accesscontrol.Principal> getAllPrincipals() throws android.os.RemoteException;
  /**
      * Get a princial in the messaging access control.
      *
      * @param principalName        Name of the principal to be fetched.
      * @return                     The matching principal found.
      */
  public com.securityandsafetythings.messagebroker.accesscontrol.Principal getPrincipal(java.lang.String principalName, com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType principalType) throws android.os.RemoteException;
  /**
      * Get a princial in the messaging access control using its UUID.
      *
      * @param uuid                UUID of the principal in messaging access control.
      * @return                    The matching principal found.
      */
  public com.securityandsafetythings.messagebroker.accesscontrol.Principal getPrincipalByUuid(java.lang.String uuid) throws android.os.RemoteException;
  /**
      * Check access for a principal.
      *
      * @param principalName        Name of the principal for which access is checked.
      * @param principalType        Type of the principal for which access is checked.
      * @param topicExpression      An MQTT Topic expression or MQTT Topic filter expression
      *                             under check.
      * @param accessType           AccessType under check.
      * @return                     MessagingAccessControlResult instance signifying the result of
                                    the check.
      */
  public com.securityandsafetythings.messagebroker.accesscontrol.MessagingAccessControlResult checkAccess(java.lang.String principalName, com.securityandsafetythings.messagebroker.accesscontrol.PrincipalType principalType, java.lang.String topicExpression, com.securityandsafetythings.messagebroker.accesscontrol.AccessType accessType) throws android.os.RemoteException;
  /**
      * Get list of package names of apps which are whitelisted in Messaging Access Control.
      *
      * @return                     List of package names of applications whitelisted applications.
      */
  public java.util.List<java.lang.String> getWhitelistedPackageNames() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.PortResponse createInPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration, java.lang.String applicationId, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.PortResponse createOutPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration, java.lang.String applicationId, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getMatchingOutPorts(java.lang.String inPortUid) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getMatchingInPorts(java.lang.String outPortUid) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getInPortWithUid(java.lang.String portUid) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getOutPortWithUid(java.lang.String portUid) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getEdgeWithUid(java.lang.String edgeUid) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getCurrentState() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse createEdge(java.lang.String inPortUid, java.lang.String outPortUid) throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getAllInPorts() throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getAllOutPorts() throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> getAllInPortConfigurations() throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConfiguration> getAllOutPortConfigurations() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResponse getAllEdges() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResult addAttribute(java.lang.String applicationId, java.lang.String key, java.lang.String value) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResult removeAttribute(java.lang.String applicationId, java.lang.String key) throws android.os.RemoteException;
  public java.util.Map getAttributes(java.lang.String applicationId) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.PortGroupResponse createPortGroup(java.lang.String groupName, java.lang.String applicationPackageName, java.lang.String groupUid, com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener messagingServiceConnectionListener) throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.PortGroupResponse> getAllPortGroups() throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.ApplicationStitchingResponse> getAllApplicationBlocks() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.PortGroupResponse getPortGroupWithUid(java.lang.String portGroupUid) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult cleanDatabaseRemnants(java.lang.String applicationId) throws android.os.RemoteException;
}
