/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.ports;
/**
* Interface that represents an OutPort.
*
* @hide
*/
public interface IOutPort extends android.os.IInterface
{
  /** Default implementation for IOutPort. */
  public static class Default implements com.securityandsafetythings.messagebroker.ports.IOutPort
  {
    @Override public java.lang.String getUid() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getApplicationId() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getName() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setName(java.lang.String portName) throws android.os.RemoteException
    {
      return null;
    }
    @Override public boolean isDeleted() throws android.os.RemoteException
    {
      return false;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult send(com.securityandsafetythings.messagebroker.ports.Message message) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult delete(boolean deletePermanently) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getPortConfiguration() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setConnectionListener(com.securityandsafetythings.messagebroker.ports.IPortConnectionListener connectionListener) throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConnectionResponse> getAllConnections() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ports.PortConnectionResponse getConnectionWithUid(java.lang.String connectionUid) throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.ports.IOutPort
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.ports.IOutPort";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.ports.IOutPort interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.ports.IOutPort asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.ports.IOutPort))) {
        return ((com.securityandsafetythings.messagebroker.ports.IOutPort)iin);
      }
      return new com.securityandsafetythings.messagebroker.ports.IOutPort.Stub.Proxy(obj);
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
        case TRANSACTION_getApplicationId:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getApplicationId();
          reply.writeNoException();
          reply.writeString(_result);
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
        case TRANSACTION_isDeleted:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isDeleted();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_send:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.Message _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.ports.Message.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.send(_arg0);
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
        case TRANSACTION_getPortConfiguration:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConfiguration _result = this.getPortConfiguration();
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
        case TRANSACTION_setConnectionListener:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.IPortConnectionListener _arg0;
          _arg0 = com.securityandsafetythings.messagebroker.ports.IPortConnectionListener.Stub.asInterface(data.readStrongBinder());
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.setConnectionListener(_arg0);
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
        case TRANSACTION_getAllConnections:
        {
          data.enforceInterface(descriptor);
          java.util.List<com.securityandsafetythings.messagebroker.ports.PortConnectionResponse> _result = this.getAllConnections();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_getConnectionWithUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.securityandsafetythings.messagebroker.ports.PortConnectionResponse _result = this.getConnectionWithUid(_arg0);
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
    private static class Proxy implements com.securityandsafetythings.messagebroker.ports.IOutPort
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setName(java.lang.String portName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(portName);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setName, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setName(portName);
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult send(com.securityandsafetythings.messagebroker.ports.Message message) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((message!=null)) {
            _data.writeInt(1);
            message.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_send, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().send(message);
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
      @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getPortConfiguration() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ports.PortConfiguration _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPortConfiguration, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPortConfiguration();
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ports.PortConfiguration.CREATOR.createFromParcel(_reply);
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setConnectionListener(com.securityandsafetythings.messagebroker.ports.IPortConnectionListener connectionListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((connectionListener!=null))?(connectionListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setConnectionListener, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setConnectionListener(connectionListener);
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
      @Override public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConnectionResponse> getAllConnections() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<com.securityandsafetythings.messagebroker.ports.PortConnectionResponse> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAllConnections, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAllConnections();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(com.securityandsafetythings.messagebroker.ports.PortConnectionResponse.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public com.securityandsafetythings.messagebroker.ports.PortConnectionResponse getConnectionWithUid(java.lang.String connectionUid) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ports.PortConnectionResponse _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(connectionUid);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getConnectionWithUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getConnectionWithUid(connectionUid);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ports.PortConnectionResponse.CREATOR.createFromParcel(_reply);
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
      public static com.securityandsafetythings.messagebroker.ports.IOutPort sDefaultImpl;
    }
    static final int TRANSACTION_getUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getApplicationId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_setName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_isDeleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_send = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_delete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getPortConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_setConnectionListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_getAllConnections = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_getConnectionWithUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.ports.IOutPort impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.ports.IOutPort getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public java.lang.String getUid() throws android.os.RemoteException;
  public java.lang.String getApplicationId() throws android.os.RemoteException;
  public java.lang.String getName() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult setName(java.lang.String portName) throws android.os.RemoteException;
  public boolean isDeleted() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult send(com.securityandsafetythings.messagebroker.ports.Message message) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult delete(boolean deletePermanently) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ports.PortConfiguration getPortConfiguration() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult setConnectionListener(com.securityandsafetythings.messagebroker.ports.IPortConnectionListener connectionListener) throws android.os.RemoteException;
  public java.util.List<com.securityandsafetythings.messagebroker.ports.PortConnectionResponse> getAllConnections() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ports.PortConnectionResponse getConnectionWithUid(java.lang.String connectionUid) throws android.os.RemoteException;
}
