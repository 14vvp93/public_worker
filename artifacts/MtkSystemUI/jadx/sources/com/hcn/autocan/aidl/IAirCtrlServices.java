package com.hcn.autocan.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IAirCtrlServices extends IInterface {
    void airCtrl(int i, int i2) throws RemoteException;

    void registerCallBack(IAirCallBack iAirCallBack) throws RemoteException;

    void unRegisterCallBack(IAirCallBack iAirCallBack) throws RemoteException;

    public static abstract class Stub extends Binder implements IAirCtrlServices {
        public static IAirCtrlServices asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.hcn.autocan.aidl.IAirCtrlServices");
            if (iin != null && (iin instanceof IAirCtrlServices)) {
                return (IAirCtrlServices) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString("com.hcn.autocan.aidl.IAirCtrlServices");
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface("com.hcn.autocan.aidl.IAirCtrlServices");
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    airCtrl(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.hcn.autocan.aidl.IAirCtrlServices");
                    IAirCallBack _arg2 = IAirCallBack.Stub.asInterface(data.readStrongBinder());
                    registerCallBack(_arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.hcn.autocan.aidl.IAirCtrlServices");
                    IAirCallBack _arg3 = IAirCallBack.Stub.asInterface(data.readStrongBinder());
                    unRegisterCallBack(_arg3);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAirCtrlServices {
            public static IAirCtrlServices sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.hcn.autocan.aidl.IAirCtrlServices
            public void airCtrl(int ctrl_id, int action) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hcn.autocan.aidl.IAirCtrlServices");
                    _data.writeInt(ctrl_id);
                    _data.writeInt(action);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().airCtrl(ctrl_id, action);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hcn.autocan.aidl.IAirCtrlServices
            public void registerCallBack(IAirCallBack callBack) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hcn.autocan.aidl.IAirCtrlServices");
                    _data.writeStrongBinder(callBack != null ? callBack.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallBack(callBack);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.hcn.autocan.aidl.IAirCtrlServices
            public void unRegisterCallBack(IAirCallBack callBack) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.hcn.autocan.aidl.IAirCtrlServices");
                    _data.writeStrongBinder(callBack != null ? callBack.asBinder() : null);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unRegisterCallBack(callBack);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IAirCtrlServices getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
