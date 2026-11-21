package android.radio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public interface IRadioPlayer extends IInterface {
    int getFreq() throws RemoteException;

    int[] getPreset(int i) throws RemoteException;

    RadioInfo getRadioInfo() throws RemoteException;

    int getRegion() throws RemoteException;

    int getUiband() throws RemoteException;

    void local(boolean z) throws RemoteException;

    int next() throws RemoteException;

    void presetScan(int i) throws RemoteException;

    int prev() throws RemoteException;

    void removecallback(IRadioCallback iRadioCallback) throws RemoteException;

    void scan(int i, int i2, int i3) throws RemoteException;

    void setAF(boolean z) throws RemoteException;

    void setEON(boolean z) throws RemoteException;

    void setMute(boolean z) throws RemoteException;

    void setPTY(int i) throws RemoteException;

    void setPreset(int i, int[] iArr) throws RemoteException;

    void setREG(boolean z) throws RemoteException;

    void setTA(boolean z) throws RemoteException;

    void setUibandIndexFreq(int i, int i2, int i3) throws RemoteException;

    void setcallback(IRadioCallback iRadioCallback) throws RemoteException;

    int stepDown() throws RemoteException;

    int stepUp() throws RemoteException;

    void stereo(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IRadioPlayer {
        private static final String DESCRIPTOR = "android.radio.IRadioPlayer";
        static final int TRANSACTION_getFreq = 9;
        static final int TRANSACTION_getPreset = 15;
        static final int TRANSACTION_getRadioInfo = 11;
        static final int TRANSACTION_getRegion = 10;
        static final int TRANSACTION_getUiband = 18;
        static final int TRANSACTION_local = 13;
        static final int TRANSACTION_next = 5;
        static final int TRANSACTION_presetScan = 4;
        static final int TRANSACTION_prev = 6;
        static final int TRANSACTION_removecallback = 2;
        static final int TRANSACTION_scan = 3;
        static final int TRANSACTION_setAF = 21;
        static final int TRANSACTION_setEON = 23;
        static final int TRANSACTION_setMute = 14;
        static final int TRANSACTION_setPTY = 19;
        static final int TRANSACTION_setPreset = 16;
        static final int TRANSACTION_setREG = 22;
        static final int TRANSACTION_setTA = 20;
        static final int TRANSACTION_setUibandIndexFreq = 17;
        static final int TRANSACTION_setcallback = 1;
        static final int TRANSACTION_stepDown = 8;
        static final int TRANSACTION_stepUp = 7;
        static final int TRANSACTION_stereo = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioPlayer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRadioPlayer)) {
                return (IRadioPlayer) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    setcallback(IRadioCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    removecallback(IRadioCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    scan(_arg1, _arg2, _arg3);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    presetScan(data.readInt());
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _result = next();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = prev();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _result3 = stepUp();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    int _result4 = stepDown();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    int _result5 = getFreq();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    int _result6 = getRegion();
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    RadioInfo _result7 = getRadioInfo();
                    reply.writeNoException();
                    if (_result7 != null) {
                        reply.writeInt(1);
                        _result7.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    stereo(_arg0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    local(_arg0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    setMute(_arg0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _result8 = getPreset(data.readInt());
                    reply.writeNoException();
                    reply.writeIntArray(_result8);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg4 = data.readInt();
                    int[] _arg5 = data.createIntArray();
                    setPreset(_arg4, _arg5);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg6 = data.readInt();
                    int _arg7 = data.readInt();
                    int _arg8 = data.readInt();
                    setUibandIndexFreq(_arg6, _arg7, _arg8);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    int _result9 = getUiband();
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    setPTY(data.readInt());
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    setTA(_arg0);
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    setAF(_arg0);
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    setREG(_arg0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_setEON /* 23 */:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    setEON(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRadioPlayer {
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

            @Override // android.radio.IRadioPlayer
            public void setcallback(IRadioCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void removecallback(IRadioCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void scan(int type, int band, int count) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(band);
                    _data.writeInt(count);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void presetScan(int seconds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seconds);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int next() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int prev() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int stepUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int stepDown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int getFreq() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int getRegion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public RadioInfo getRadioInfo() throws RemoteException {
                RadioInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RadioInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void stereo(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void local(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setMute(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int[] getPreset(int band) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(band);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setPreset(int band, int[] preset) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(band);
                    _data.writeIntArray(preset);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setUibandIndexFreq(int uiband, int index, int freq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uiband);
                    _data.writeInt(index);
                    _data.writeInt(freq);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public int getUiband() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setPTY(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setTA(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setAF(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setREG(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.radio.IRadioPlayer
            public void setEON(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_setEON, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
