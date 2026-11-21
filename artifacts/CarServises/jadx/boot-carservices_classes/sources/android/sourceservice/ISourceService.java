package android.sourceservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public interface ISourceService extends IInterface {
    int doExtAudioEffect(int i, int[] iArr) throws RemoteException;

    MusicInfo getMusicInfo() throws RemoteException;

    void onRequestAuxAudio(String str, boolean z, boolean z2) throws RemoteException;

    void onRequestPlayAudio(String str) throws RemoteException;

    void removecallback(ISourceCallback iSourceCallback) throws RemoteException;

    void setInputPathMute(int i, boolean z) throws RemoteException;

    void setcallback(ISourceCallback iSourceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISourceService {
        private static final String DESCRIPTOR = "android.sourceservice.ISourceService";
        static final int TRANSACTION_doExtAudioEffect = 7;
        static final int TRANSACTION_getMusicInfo = 6;
        static final int TRANSACTION_onRequestAuxAudio = 5;
        static final int TRANSACTION_onRequestPlayAudio = 3;
        static final int TRANSACTION_removecallback = 2;
        static final int TRANSACTION_setInputPathMute = 4;
        static final int TRANSACTION_setcallback = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISourceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISourceService)) {
                return (ISourceService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg2;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    ISourceCallback _arg0 = ISourceCallback.Stub.asInterface(data.readStrongBinder());
                    setcallback(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ISourceCallback _arg1 = ISourceCallback.Stub.asInterface(data.readStrongBinder());
                    removecallback(_arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg3 = data.readString();
                    onRequestPlayAudio(_arg3);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg4 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    setInputPathMute(_arg4, _arg2);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg5 = data.readString();
                    boolean _arg6 = data.readInt() != 0;
                    _arg2 = data.readInt() != 0;
                    onRequestAuxAudio(_arg5, _arg6, _arg2);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    MusicInfo _result = getMusicInfo();
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg7 = data.readInt();
                    int[] _arg8 = data.createIntArray();
                    int _result2 = doExtAudioEffect(_arg7, _arg8);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    reply.writeIntArray(_arg8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISourceService {
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

            @Override // android.sourceservice.ISourceService
            public void setcallback(ISourceCallback callback) throws RemoteException {
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

            @Override // android.sourceservice.ISourceService
            public void removecallback(ISourceCallback callback) throws RemoteException {
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

            @Override // android.sourceservice.ISourceService
            public void onRequestPlayAudio(String cls) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cls);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sourceservice.ISourceService
            public void setInputPathMute(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sourceservice.ISourceService
            public void onRequestAuxAudio(String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sourceservice.ISourceService
            public MusicInfo getMusicInfo() throws RemoteException {
                MusicInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = MusicInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sourceservice.ISourceService
            public int doExtAudioEffect(int cmd, int[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeIntArray(data);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readIntArray(data);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
