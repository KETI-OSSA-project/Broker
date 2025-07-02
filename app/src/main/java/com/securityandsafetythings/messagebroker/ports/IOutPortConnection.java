/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.ports;
/**
* Interface that represents an OutPortConnection.
*
* @hide
*/
public interface IOutPortConnection extends android.os.IInterface
{
  /** Default implementation for IOutPortConnection. */
  public static class Default implements com.securityandsafetythings.messagebroker.ports.IOutPortConnection
  {
    @Override public java.lang.String getUid() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getApplicationId() throws android.os.RemoteException
    {
      return null;
    }
    @Override public boolean isDeleted() throws android.os.RemoteException
    {
      return false;
    }
    @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getConnectedPortConfiguration() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult send(com.securityandsafetythings.messagebroker.ports.Message message) throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ports.PortConnectionState getConnectionState() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setConnectionStateListener(com.securityandsafetythings.messagebroker.ports.IConnectionStateListener connectionStateListener) throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.ports.IOutPortConnection
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.ports.IOutPortConnection";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.ports.IOutPortConnection interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.ports.IOutPortConnection asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.ports.IOutPortConnection))) {
        return ((com.securityandsafetythings.messagebroker.ports.IOutPortConnection)iin);
      }
      return new com.securityandsafetythings.messagebroker.ports.IOutPortConnection.Stub.Proxy(obj);
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
        case TRANSACTION_isDeleted:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isDeleted();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getConnectedPortConfiguration:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConfiguration _result = this.getConnectedPortConfiguration();
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
        case TRANSACTION_getConnectionState:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConnectionState _result = this.getConnectionState();
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
        case TRANSACTION_setConnectionStateListener:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.IConnectionStateListener _arg0;
          _arg0 = com.securityandsafetythings.messagebroker.ports.IConnectionStateListener.Stub.asInterface(data.readStrongBinder());
          com.securityandsafetythings.messagebroker.MessageBrokerResult _result = this.setConnectionStateListener(_arg0);
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
    private static class Proxy implements com.securityandsafetythings.messagebroker.ports.IOutPortConnection
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
      @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getConnectedPortConfiguration() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ports.PortConfiguration _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getConnectedPortConfiguration, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getConnectedPortConfiguration();
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
      @Override public com.securityandsafetythings.messagebroker.ports.PortConnectionState getConnectionState() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ports.PortConnectionState _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getConnectionState, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getConnectionState();
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.ports.PortConnectionState.CREATOR.createFromParcel(_reply);
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
      @Override public com.securityandsafetythings.messagebroker.MessageBrokerResult setConnectionStateListener(com.securityandsafetythings.messagebroker.ports.IConnectionStateListener connectionStateListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.MessageBrokerResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((connectionStateListener!=null))?(connectionStateListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setConnectionStateListener, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setConnectionStateListener(connectionStateListener);
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
      public static com.securityandsafetythings.messagebroker.ports.IOutPortConnection sDefaultImpl;
    }
    static final int TRANSACTION_getUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getApplicationId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_isDeleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_getConnectedPortConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_send = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_getConnectionState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_setConnectionStateListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.ports.IOutPortConnection impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.ports.IOutPortConnection getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public java.lang.String getUid() throws android.os.RemoteException;
  public java.lang.String getApplicationId() throws android.os.RemoteException;
  public boolean isDeleted() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ports.PortConfiguration getConnectedPortConfiguration() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult send(com.securityandsafetythings.messagebroker.ports.Message message) throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ports.PortConnectionState getConnectionState() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.MessageBrokerResult setConnectionStateListener(com.securityandsafetythings.messagebroker.ports.IConnectionStateListener connectionStateListener) throws android.os.RemoteException;
}
