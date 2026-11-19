package com.hcn.autocan.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.hcn.autocan.hiworld.gm.air.AIR;

/* JADX INFO: loaded from: classes.dex */
public interface IAirCallBack extends IInterface {
    void CallBackEvent(AIR air) throws RemoteException;

    public static abstract class Stub extends Binder implements IAirCallBack {
        public Stub() {
            attachInterface(this, "com.hcn.autocan.aidl.IAirCallBack");
        }

        public static IAirCallBack asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.hcn.autocan.aidl.IAirCallBack");
            if (iin != null && (iin instanceof IAirCallBack)) {
                return (IAirCallBack) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            AIR _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString("com.hcn.autocan.aidl.IAirCallBack");
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface("com.hcn.autocan.aidl.IAirCallBack");
            if (data.readInt() != 0) {
                _arg0 = AIR.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            CallBackEvent(_arg0);
            reply.writeNoException();
            return true;
        }

        private static class Proxy implements IAirCallBack {
            public static IAirCallBack sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.hcn.autocan.aidl.IAirCallBack
            public void CallBackEvent(AIR airBean) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hcn.autocan.aidl.IAirCallBack");
                    if (airBean != null) {
                        _data.writeInt(1);
                        airBean.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().CallBackEvent(airBean);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IAirCallBack getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
