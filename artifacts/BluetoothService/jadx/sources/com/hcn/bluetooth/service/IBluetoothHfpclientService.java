package com.hcn.bluetooth.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IBluetoothHfpclientService extends IInterface {

    public static class Default implements IBluetoothHfpclientService {
        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void acceptCall(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void dial(String str) throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public String getLastCall() throws RemoteException {
            return null;
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void hangup() throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public boolean isAudioConnected() throws RemoteException {
            return false;
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void sendDTMF(byte b) throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void switchAudio() throws RemoteException {
        }
    }

    void acceptCall(int i) throws RemoteException;

    void dial(String str) throws RemoteException;

    String getLastCall() throws RemoteException;

    void hangup() throws RemoteException;

    boolean isAudioConnected() throws RemoteException;

    void sendDTMF(byte b) throws RemoteException;

    void switchAudio() throws RemoteException;

    public static abstract class Stub extends Binder implements IBluetoothHfpclientService {
        private static final String DESCRIPTOR = "com.hcn.bluetooth.service.IBluetoothHfpclientService";
        static final int TRANSACTION_acceptCall = 4;
        static final int TRANSACTION_dial = 2;
        static final int TRANSACTION_getLastCall = 7;
        static final int TRANSACTION_hangup = 5;
        static final int TRANSACTION_isAudioConnected = 1;
        static final int TRANSACTION_sendDTMF = 3;
        static final int TRANSACTION_switchAudio = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothHfpclientService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBluetoothHfpclientService)) {
                return (IBluetoothHfpclientService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsAudioConnected = isAudioConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsAudioConnected ? 1 : 0);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    dial(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    sendDTMF(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    acceptCall(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    hangup();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    switchAudio();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    String lastCall = getLastCall();
                    parcel2.writeNoException();
                    parcel2.writeString(lastCall);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBluetoothHfpclientService {
            public static IBluetoothHfpclientService sDefaultImpl;
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

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public boolean isAudioConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAudioConnected();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public void dial(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dial(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public void sendDTMF(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendDTMF(b);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public void acceptCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acceptCall(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public void hangup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hangup();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public void switchAudio() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().switchAudio();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
            public String getLastCall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastCall();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothHfpclientService iBluetoothHfpclientService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iBluetoothHfpclientService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iBluetoothHfpclientService;
            return true;
        }

        public static IBluetoothHfpclientService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
