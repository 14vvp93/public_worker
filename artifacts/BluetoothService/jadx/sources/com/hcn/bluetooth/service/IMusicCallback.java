package com.hcn.bluetooth.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.hcn.bluetooth.api.MusicPlayState;

/* JADX INFO: loaded from: classes.dex */
public interface IMusicCallback extends IInterface {

    public static class Default implements IMusicCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onA2dpConnectStateChanged(int i) throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onAvrcpConnectStateChanged(int i) throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onMetadataChanged(String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onPlayStatusChanged(MusicPlayState musicPlayState) throws RemoteException {
        }
    }

    void onA2dpConnectStateChanged(int i) throws RemoteException;

    void onAvrcpConnectStateChanged(int i) throws RemoteException;

    void onMetadataChanged(String str, String str2, String str3) throws RemoteException;

    void onPlayStatusChanged(MusicPlayState musicPlayState) throws RemoteException;

    public static abstract class Stub extends Binder implements IMusicCallback {
        private static final String DESCRIPTOR = "com.hcn.bluetooth.service.IMusicCallback";
        static final int TRANSACTION_onA2dpConnectStateChanged = 1;
        static final int TRANSACTION_onAvrcpConnectStateChanged = 2;
        static final int TRANSACTION_onMetadataChanged = 3;
        static final int TRANSACTION_onPlayStatusChanged = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMusicCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMusicCallback)) {
                return (IMusicCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onA2dpConnectStateChanged(parcel.readInt());
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onAvrcpConnectStateChanged(parcel.readInt());
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                onMetadataChanged(parcel.readString(), parcel.readString(), parcel.readString());
                return true;
            }
            if (i != 4) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onPlayStatusChanged(parcel.readInt() != 0 ? MusicPlayState.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IMusicCallback {
            public static IMusicCallback sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.hcn.bluetooth.service.IMusicCallback
            public void onA2dpConnectStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onA2dpConnectStateChanged(i);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IMusicCallback
            public void onAvrcpConnectStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcpConnectStateChanged(i);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IMusicCallback
            public void onMetadataChanged(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(3, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onMetadataChanged(str, str2, str3);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IMusicCallback
            public void onPlayStatusChanged(MusicPlayState musicPlayState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (musicPlayState != null) {
                        parcelObtain.writeInt(1);
                        musicPlayState.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayStatusChanged(musicPlayState);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMusicCallback iMusicCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iMusicCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iMusicCallback;
            return true;
        }

        public static IMusicCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
