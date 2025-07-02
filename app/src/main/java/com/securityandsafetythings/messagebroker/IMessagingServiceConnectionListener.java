/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker;
/**
* A listener for listening to messaging service connection.
*
* @hide
*/
public interface IMessagingServiceConnectionListener extends android.os.IInterface
{
  /** Default implementation for IMessagingServiceConnectionListener. */
  public static class Default implements com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener
  {
    @Override public void onConnected() throws android.os.RemoteException
    {
    }
    @Override public void onDisconnected() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener))) {
        return ((com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener)iin);
      }
      return new com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener.Stub.Proxy(obj);
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
        case TRANSACTION_onConnected:
        {
          data.enforceInterface(descriptor);
          this.onConnected();
          return true;
        }
        case TRANSACTION_onDisconnected:
        {
          data.enforceInterface(descriptor);
          this.onDisconnected();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener
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
      @Override public void onConnected() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onConnected, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onConnected();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onDisconnected() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDisconnected, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onDisconnected();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener sDefaultImpl;
    }
    static final int TRANSACTION_onConnected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_onDisconnected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.IMessagingServiceConnectionListener getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void onConnected() throws android.os.RemoteException;
  public void onDisconnected() throws android.os.RemoteException;
}
