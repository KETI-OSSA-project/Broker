/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.applicationstitching;
/**
* Interface that represents a connection handle to an Edge.
*
* @hide
*/
public interface IEdge extends android.os.IInterface
{
  /** Default implementation for IEdge. */
  public static class Default implements com.securityandsafetythings.messagebroker.applicationstitching.IEdge
  {
    @Override public java.lang.String getUid() throws android.os.RemoteException
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
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult delete() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.applicationstitching.EdgeState getConnectionState() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getInPortConfiguration() throws android.os.RemoteException
    {
      return null;
    }
    @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getOutPortConfiguration() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getInPortUid() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getOutPortUid() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void enable() throws android.os.RemoteException
    {
    }
    @Override public void disable() throws android.os.RemoteException
    {
    }
    @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult setMessageFlowListener(com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener messageFlowListener) throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.applicationstitching.IEdge
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.applicationstitching.IEdge";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.applicationstitching.IEdge interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.applicationstitching.IEdge asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.applicationstitching.IEdge))) {
        return ((com.securityandsafetythings.messagebroker.applicationstitching.IEdge)iin);
      }
      return new com.securityandsafetythings.messagebroker.applicationstitching.IEdge.Stub.Proxy(obj);
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
        case TRANSACTION_delete:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result = this.delete();
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
          com.securityandsafetythings.messagebroker.applicationstitching.EdgeState _result = this.getConnectionState();
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
        case TRANSACTION_getInPortConfiguration:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConfiguration _result = this.getInPortConfiguration();
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
        case TRANSACTION_getOutPortConfiguration:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConfiguration _result = this.getOutPortConfiguration();
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
        case TRANSACTION_getInPortUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getInPortUid();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getOutPortUid:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getOutPortUid();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_enable:
        {
          data.enforceInterface(descriptor);
          this.enable();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_disable:
        {
          data.enforceInterface(descriptor);
          this.disable();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setMessageFlowListener:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener _arg0;
          _arg0 = com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener.Stub.asInterface(data.readStrongBinder());
          com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result = this.setMessageFlowListener(_arg0);
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
    private static class Proxy implements com.securityandsafetythings.messagebroker.applicationstitching.IEdge
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
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult delete() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_delete, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().delete();
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
      @Override public com.securityandsafetythings.messagebroker.applicationstitching.EdgeState getConnectionState() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.applicationstitching.EdgeState _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getConnectionState, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getConnectionState();
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = com.securityandsafetythings.messagebroker.applicationstitching.EdgeState.CREATOR.createFromParcel(_reply);
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
      @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getInPortConfiguration() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ports.PortConfiguration _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getInPortConfiguration, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getInPortConfiguration();
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
      @Override public com.securityandsafetythings.messagebroker.ports.PortConfiguration getOutPortConfiguration() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ports.PortConfiguration _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOutPortConfiguration, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOutPortConfiguration();
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
      @Override public java.lang.String getInPortUid() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getInPortUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getInPortUid();
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
      @Override public java.lang.String getOutPortUid() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOutPortUid, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOutPortUid();
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
      @Override public void enable() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_enable, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().enable();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void disable() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_disable, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().disable();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public com.securityandsafetythings.messagebroker.ApplicationStitchingResult setMessageFlowListener(com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener messageFlowListener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        com.securityandsafetythings.messagebroker.ApplicationStitchingResult _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((messageFlowListener!=null))?(messageFlowListener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setMessageFlowListener, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setMessageFlowListener(messageFlowListener);
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
      public static com.securityandsafetythings.messagebroker.applicationstitching.IEdge sDefaultImpl;
    }
    static final int TRANSACTION_getUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_isDeleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_isEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_delete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_getConnectionState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_getInPortConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getOutPortConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getInPortUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_getOutPortUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_enable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_disable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_setMessageFlowListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.applicationstitching.IEdge impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.applicationstitching.IEdge getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public java.lang.String getUid() throws android.os.RemoteException;
  public boolean isDeleted() throws android.os.RemoteException;
  public boolean isEnabled() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResult delete() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.applicationstitching.EdgeState getConnectionState() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ports.PortConfiguration getInPortConfiguration() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ports.PortConfiguration getOutPortConfiguration() throws android.os.RemoteException;
  public java.lang.String getInPortUid() throws android.os.RemoteException;
  public java.lang.String getOutPortUid() throws android.os.RemoteException;
  public void enable() throws android.os.RemoteException;
  public void disable() throws android.os.RemoteException;
  public com.securityandsafetythings.messagebroker.ApplicationStitchingResult setMessageFlowListener(com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener messageFlowListener) throws android.os.RemoteException;
}
