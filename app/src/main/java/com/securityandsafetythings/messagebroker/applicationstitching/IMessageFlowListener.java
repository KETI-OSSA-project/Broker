/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.applicationstitching;
/**
* Message flow listener interface.
*
* @hide
*/
public interface IMessageFlowListener extends android.os.IInterface
{
  /** Default implementation for IMessageFlowListener. */
  public static class Default implements com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener
  {
    @Override public void onMessage() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener))) {
        return ((com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener)iin);
      }
      return new com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener.Stub.Proxy(obj);
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
        case TRANSACTION_onMessage:
        {
          data.enforceInterface(descriptor);
          this.onMessage();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener
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
      @Override public void onMessage() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onMessage, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onMessage();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener sDefaultImpl;
    }
    static final int TRANSACTION_onMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.applicationstitching.IMessageFlowListener getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void onMessage() throws android.os.RemoteException;
}
