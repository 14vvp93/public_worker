package android.sourceservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public interface ISourceCallback extends IInterface {
    void onMusicEvent(MusicInfo musicInfo) throws RemoteException;

    void onSourceChanged(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISourceCallback {
        private static final String DESCRIPTOR = "android.sourceservice.ISourceCallback";
        static final int TRANSACTION_onMusicEvent = 1;
        static final int TRANSACTION_onSourceChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISourceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISourceCallback)) {
                return (ISourceCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            MusicInfo _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = MusicInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onMusicEvent(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg1 = data.readString();
                    onSourceChanged(_arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISourceCallback {
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

            @Override // android.sourceservice.ISourceCallback
            public void onMusicEvent(MusicInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.sourceservice.ISourceCallback
            public void onSourceChanged(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
