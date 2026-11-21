package android.carsource;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public interface IMcuManager extends IInterface {
    void applyVideoPath(int i) throws RemoteException;

    float getBattery() throws RemoteException;

    void getKeySettings(int i, List<KeyMap> list) throws RemoteException;

    void injectKeyEvent(int i, int i2) throws RemoteException;

    void injectKeyEventTimeout(int i, int i2) throws RemoteException;

    void removecallback(IMcuListener iMcuListener, int i) throws RemoteException;

    void requestBeep(int i) throws RemoteException;

    void requestMcuIAP(byte b, byte[] bArr) throws RemoteException;

    void requestSendIR(byte[] bArr) throws RemoteException;

    void requestTouchdown(int i, int i2) throws RemoteException;

    void setKeyMaps(KeyMap keyMap) throws RemoteException;

    int setLogo(String str) throws RemoteException;

    void setTouchIrqType(int i) throws RemoteException;

    void setTouchKeyMaps(KeyMap keyMap) throws RemoteException;

    void setcallback(IMcuListener iMcuListener, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMcuManager {
        private static final String DESCRIPTOR = "android.carsource.IMcuManager";
        static final int TRANSACTION_applyVideoPath = 9;
        static final int TRANSACTION_getBattery = 8;
        static final int TRANSACTION_getKeySettings = 3;
        static final int TRANSACTION_injectKeyEvent = 10;
        static final int TRANSACTION_injectKeyEventTimeout = 11;
        static final int TRANSACTION_removecallback = 2;
        static final int TRANSACTION_requestBeep = 7;
        static final int TRANSACTION_requestMcuIAP = 13;
        static final int TRANSACTION_requestSendIR = 12;
        static final int TRANSACTION_requestTouchdown = 15;
        static final int TRANSACTION_setKeyMaps = 4;
        static final int TRANSACTION_setLogo = 14;
        static final int TRANSACTION_setTouchIrqType = 6;
        static final int TRANSACTION_setTouchKeyMaps = 5;
        static final int TRANSACTION_setcallback = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMcuManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMcuManager)) {
                return (IMcuManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            KeyMap _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    IMcuListener _arg1 = IMcuListener.Stub.asInterface(data.readStrongBinder());
                    int _arg2 = data.readInt();
                    setcallback(_arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IMcuListener _arg3 = IMcuListener.Stub.asInterface(data.readStrongBinder());
                    int _arg4 = data.readInt();
                    removecallback(_arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg5 = data.readInt();
                    ArrayList arrayList = new ArrayList();
                    getKeySettings(_arg5, arrayList);
                    reply.writeNoException();
                    reply.writeTypedList(arrayList);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? KeyMap.CREATOR.createFromParcel(data) : null;
                    setKeyMaps(_arg0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? KeyMap.CREATOR.createFromParcel(data) : null;
                    setTouchKeyMaps(_arg0);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    setTouchIrqType(data.readInt());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    requestBeep(data.readInt());
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    float _result = getBattery();
                    reply.writeNoException();
                    reply.writeFloat(_result);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    applyVideoPath(data.readInt());
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg6 = data.readInt();
                    int _arg7 = data.readInt();
                    injectKeyEvent(_arg6, _arg7);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg8 = data.readInt();
                    int _arg9 = data.readInt();
                    injectKeyEventTimeout(_arg8, _arg9);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    requestSendIR(data.createByteArray());
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg10 = data.readByte();
                    byte[] _arg11 = data.createByteArray();
                    requestMcuIAP(_arg10, _arg11);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = setLogo(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg12 = data.readInt();
                    int _arg13 = data.readInt();
                    requestTouchdown(_arg12, _arg13);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMcuManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.carsource.IMcuManager
            public void setcallback(IMcuListener callback, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(event);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void removecallback(IMcuListener callback, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(event);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void getKeySettings(int type, List<KeyMap> keys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    _reply.readTypedList(keys, KeyMap.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void setKeyMaps(KeyMap key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (key != null) {
                        _data.writeInt(1);
                        key.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void setTouchKeyMaps(KeyMap key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (key != null) {
                        _data.writeInt(1);
                        key.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void setTouchIrqType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void requestBeep(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public float getBattery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void applyVideoPath(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void injectKeyEvent(int keycode, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keycode);
                    _data.writeInt(state);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void injectKeyEventTimeout(int keycode, int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keycode);
                    _data.writeInt(timeout);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void requestSendIR(byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(data);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void requestMcuIAP(byte subcmd, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(subcmd);
                    _data.writeByteArray(data);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public int setLogo(String filename) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(filename);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.carsource.IMcuManager
            public void requestTouchdown(int x, int y) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
