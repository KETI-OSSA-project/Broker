/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.securityandsafetythings.messagebroker;
/**
* A client interface the callback of which is called when a message is available.
*
* @hide
*/
public interface IMessageListener extends android.os.IInterface
{
  /** Default implementation for IMessageListener. */
  public static class Default implements com.securityandsafetythings.messagebroker.IMessageListener
  {
    /**
      * onMessage() will be called if a message is available.
      *
      * @param message              {@link Message}
      */
    @Override public void onMessage(com.securityandsafetythings.messagebroker.Message message) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.securityandsafetythings.messagebroker.IMessageListener
  {
    private static final java.lang.String DESCRIPTOR = "com.securityandsafetythings.messagebroker.IMessageListener";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.securityandsafetythings.messagebroker.IMessageListener interface,
     * generating a proxy if needed.
     */
    public static com.securityandsafetythings.messagebroker.IMessageListener asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.securityandsafetythings.messagebroker.IMessageListener))) {
        return ((com.securityandsafetythings.messagebroker.IMessageListener)iin);
      }
      return new com.securityandsafetythings.messagebroker.IMessageListener.Stub.Proxy(obj);
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
          com.securityandsafetythings.messagebroker.Message _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.securityandsafetythings.messagebroker.Message.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          this.onMessage(_arg0);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.securityandsafetythings.messagebroker.IMessageListener
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
        * onMessage() will be called if a message is available.
        *
        * @param message              {@link Message}
        */
      @Override public void onMessage(com.securityandsafetythings.messagebroker.Message message) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((message!=null)) {
            _data.writeInt(1);
            message.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_onMessage, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onMessage(message);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static com.securityandsafetythings.messagebroker.IMessageListener sDefaultImpl;
    }
    static final int TRANSACTION_onMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    public static boolean setDefaultImpl(com.securityandsafetythings.messagebroker.IMessageListener impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.securityandsafetythings.messagebroker.IMessageListener getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
    * onMessage() will be called if a message is available.
    *
    * @param message              {@link Message}
    */
  public void onMessage(com.securityandsafetythings.messagebroker.Message message) throws android.os.RemoteException;
}
