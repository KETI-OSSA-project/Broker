/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker.ports;
/**
* A connection listener interface, the callbacks of which are used to notify the status of change
in the network connection status with the remote Mqtt broker.
*
* @hide
*/
public interface IPortConnectionListener extends android.os.IInterface
{
  /** Default implementation for IPortConnectionListener. */
  public static class Default implements com.securityandsafetythings.messagebroker.ports.IPortConnectionListener
  {
    @Override public void onNewConnection(com.securityandsafetythings.messagebroker.ports.PortConnectionResponse connectionResponse) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.ports.IPortConnectionListener
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.ports.IPortConnectionListener";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.ports.IPortConnectionListener interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.ports.IPortConnectionListener asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.ports.IPortConnectionListener))) {
        return ((com.securityandsafetythings.messagebroker.ports.IPortConnectionListener)iin);
      }
      return new com.securityandsafetythings.messagebroker.ports.IPortConnectionListener.Stub.Proxy(obj);
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
        case TRANSACTION_onNewConnection:
        {
          data.enforceInterface(descriptor);
          com.securityandsafetythings.messagebroker.ports.PortConnectionResponse _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.ports.PortConnectionResponse.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          this.onNewConnection(_arg0);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.ports.IPortConnectionListener
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
      @Override public void onNewConnection(com.securityandsafetythings.messagebroker.ports.PortConnectionResponse connectionResponse) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((connectionResponse!=null)) {
            _data.writeInt(1);
            connectionResponse.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_onNewConnection, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onNewConnection(connectionResponse);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static com.securityandsafetythings.messagebroker.ports.IPortConnectionListener sDefaultImpl;
    }
    static final int TRANSACTION_onNewConnection = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.ports.IPortConnectionListener impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.ports.IPortConnectionListener getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void onNewConnection(com.securityandsafetythings.messagebroker.ports.PortConnectionResponse connectionResponse) throws android.os.RemoteException;
}
