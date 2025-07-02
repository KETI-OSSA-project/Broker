/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.ports;
/**
* Interface that represents a PortGroup.
*
* @hide
*/
public interface IPortGroup extends android.os.IInterface
{
  /** Default implementation for IPortGroup. */
  public static class Default implements com.securityandsafetythings.messagebroker.ports.IPortGroup
  {
    @Override public java.lang.String getUid() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult delete(boolean deletePermanently) throws android.os.RemoteException
    {
      return null;
    }
    @Override public boolean isDeleted() throws android.os.RemoteException
    {
      return false;
    }
    @Override public boolean isEnabled() throws android.os.RemoteException
    {
      return false;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult enable() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult disable() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getName() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setName(java.lang.String name) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.PortResponse createInPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.PortResponse createOutPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult deleteInPort(java.lang.String inPortUid, boolean deletePermanently) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult deleteOutPort(java.lang.String outPortUid, boolean deletePermanently) throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getInPorts() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getOutPorts() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<java.lang.String> getInPortUids() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<java.lang.String> getOutPortUids() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void addAttribute(java.lang.String key, java.lang.String value) throws android.os.RemoteException
    {
    }
    @Override public boolean removeAttribute(java.lang.String key) throws android.os.RemoteException
    {
      return false;
    }
    @Override public java.util.Map getAttributes() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getApplicationId() throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.ports.IPortGroup
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.ports.IPortGroup";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.ports.IPortGroup interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.ports.IPortGroup asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.ports.IPortGroup))) {
        return ((com.securityandsafetythings.messagebroker.ports.IPortGroup)iin);
      }
      return new com.securityandsafetythings.messagebroker.ports.IPortGroup.Stub.Proxy(obj);
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
        case TRANSACTION_getUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getUid();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_delete:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.delete(_arg0);
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
        case TRANSACTION_isDeleted:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isDeleted();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_isEnabled:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isEnabled();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_enable:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.enable();
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
        case TRANSACTION_disable:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.disable();
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
        case TRANSACTION_getName:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getName();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_setName:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.setName(_arg0);
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
          com.securityandsafetythings.messagebroker.PortResponse _result = this.createInPort(_arg0);
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
          com.securityandsafetythings.messagebroker.PortResponse _result = this.createOutPort(_arg0);
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
        case TRANSACTION_deleteInPort:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _arg1;
          _arg1 = (0!=data.readInt());
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.deleteInPort(_arg0, _arg1);
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
        case TRANSACTION_deleteOutPort:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _arg1;
          _arg1 = (0!=data.readInt());
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.deleteOutPort(_arg0, _arg1);
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
        case TRANSACTION_getInPorts:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result = this.getInPorts();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getOutPorts:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result = this.getOutPorts();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getInPortUids:
        {
          data.enforceInterface(descriptor);
          java.util.List<java.lang.String> _result = this.getInPortUids();
          reply.writeNoException();
          reply.writeStringList(_result);
          return true;
        }
        case TRANSACTION_getOutPortUids:
        {
          data.enforceInterface(descriptor);
          java.util.List<java.lang.String> _result = this.getOutPortUids();
          reply.writeNoException();
          reply.writeStringList(_result);
          return true;
        }
        case TRANSACTION_addAttribute:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          this.addAttribute(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_removeAttribute:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.removeAttribute(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getAttributes:
        {
          data.enforceInterface(descriptor);
          java.util.Map _result = this.getAttributes();
          reply.writeNoException();
          reply.writeMap(_result);
          return true;
        }
        case TRANSACTION_getApplicationId:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getApplicationId();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.ports.IPortGroup
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
      @Override public java.lang.String getUid() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getUid();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult delete(boolean deletePermanently) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((deletePermanently)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_delete, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().delete(deletePermanently);
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
      @Override public boolean isDeleted() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isDeleted, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isDeleted();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public boolean isEnabled() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isEnabled, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isEnabled();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult enable() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_enable, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().enable();
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult disable() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_disable, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().disable();
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
      @Override public java.lang.String getName() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getName();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setName(java.lang.String name) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(name);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setName, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setName(name);
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
      @Override public com.securityandsafetythings.messagebroker.PortResponse createInPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration) throws android.os.RemoteException
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
          boolean _status = mRemote.transact(Stub.TRANSACTION_createInPort, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createInPort(portConfiguration);
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
      @Override public com.securityandsafetythings.messagebroker.PortResponse createOutPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration) throws android.os.RemoteException
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
          boolean _status = mRemote.transact(Stub.TRANSACTION_createOutPort, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createOutPort(portConfiguration);
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult deleteInPort(java.lang.String inPortUid, boolean deletePermanently) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(inPortUid);
          _data.writeInt(((deletePermanently)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_deleteInPort, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().deleteInPort(inPortUid, deletePermanently);
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult deleteOutPort(java.lang.String outPortUid, boolean deletePermanently) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(outPortUid);
          _data.writeInt(((deletePermanently)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_deleteOutPort, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().deleteOutPort(outPortUid, deletePermanently);
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
      @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getInPorts() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getInPorts, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getInPorts();
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
      @Override public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getOutPorts() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.PortResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOutPorts, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOutPorts();
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
      @Override public java.util.List<java.lang.String> getInPortUids() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<java.lang.String> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getInPortUids, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getInPortUids();
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
      @Override public java.util.List<java.lang.String> getOutPortUids() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<java.lang.String> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOutPortUids, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOutPortUids();
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
      @Override public void addAttribute(java.lang.String key, java.lang.String value) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(key);
          _data.writeString(value);
          boolean _status = mRemote.transact(Stub.TRANSACTION_addAttribute, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().addAttribute(key, value);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public boolean removeAttribute(java.lang.String key) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(key);
          boolean _status = mRemote.transact(Stub.TRANSACTION_removeAttribute, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().removeAttribute(key);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.util.Map getAttributes() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.Map _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAttributes, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAttributes();
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
      @Override public java.lang.String getApplicationId() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getApplicationId, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getApplicationId();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.securityandsafetythings.messagebroker.ports.IPortGroup sDefaultImpl;
    }
    static final int TRANSACTION_getUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_delete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_isDeleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_isEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_enable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_disable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_setName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_createInPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_createOutPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_deleteInPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_deleteOutPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_getInPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_getOutPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_getInPortUids = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
    static final int TRANSACTION_getOutPortUids = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
    static final int TRANSACTION_addAttribute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
    static final int TRANSACTION_removeAttribute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
    static final int TRANSACTION_getAttributes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
    static final int TRANSACTION_getApplicationId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.ports.IPortGroup impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.ports.IPortGroup getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public java.lang.String getUid() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult delete(boolean deletePermanently) throws android.os.RemoteException;
  public boolean isDeleted() throws android.os.RemoteException;
  public boolean isEnabled() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult enable() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult disable() throws android.os.RemoteException;
  public java.lang.String getName() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult setName(java.lang.String name) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.PortResponse createInPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.PortResponse createOutPort(com.securityandsafetythings.messagebroker.ports.PortConfiguration portConfiguration) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult deleteInPort(java.lang.String inPortUid, boolean deletePermanently) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult deleteOutPort(java.lang.String outPortUid, boolean deletePermanently) throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getInPorts() throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.PortResponse> getOutPorts() throws android.os.RemoteException;
  public java.util.List<java.lang.String> getInPortUids() throws android.os.RemoteException;
  public java.util.List<java.lang.String> getOutPortUids() throws android.os.RemoteException;
  public void addAttribute(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
  public boolean removeAttribute(java.lang.String key) throws android.os.RemoteException;
  public java.util.Map getAttributes() throws android.os.RemoteException;
  public java.lang.String getApplicationId() throws android.os.RemoteException;
}
