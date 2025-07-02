/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.ports;
/**
* A connection listener interface, the callbacks of which are used to notify the status of change
* in the connection status.
*
* @hide
*/
public interface IConnectionStateListener extends android.os.IInterface
{
  /** Default implementation for IConnectionStateListener. */
  public static class Default implements com.securityandsafetythings.messagebroker.ports.IConnectionStateListener
  {
    @Override public void onEnabled() throws android.os.RemoteException
    {
    }
    @Override public void onDisabled() throws android.os.RemoteException
    {
    }
    @Override public void onDeleted() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.ports.IConnectionStateListener
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.ports.IConnectionStateListener";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.ports.IConnectionStateListener interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.ports.IConnectionStateListener asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.ports.IConnectionStateListener))) {
        return ((com.securityandsafetythings.messagebroker.ports.IConnectionStateListener)iin);
      }
      return new com.securityandsafetythings.messagebroker.ports.IConnectionStateListener.Stub.Proxy(obj);
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
        case TRANSACTION_onEnabled:
        {
          data.enforceInterface(descriptor);
          this.onEnabled();
          return true;
        }
        case TRANSACTION_onDisabled:
        {
          data.enforceInterface(descriptor);
          this.onDisabled();
          return true;
        }
        case TRANSACTION_onDeleted:
        {
          data.enforceInterface(descriptor);
          this.onDeleted();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.ports.IConnectionStateListener
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
      @Override public void onEnabled() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onEnabled, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onEnabled();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onDisabled() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDisabled, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onDisabled();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onDeleted() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDeleted, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onDeleted();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static com.securityandsafetythings.messagebroker.ports.IConnectionStateListener sDefaultImpl;
    }
    static final int TRANSACTION_onEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_onDisabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_onDeleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.ports.IConnectionStateListener impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.ports.IConnectionStateListener getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void onEnabled() throws android.os.RemoteException;
  public void onDisabled() throws android.os.RemoteException;
  public void onDeleted() throws android.os.RemoteException;
}
