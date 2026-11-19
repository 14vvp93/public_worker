package com.hcn.bluetooth.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;

/* JADX INFO: loaded from: classes.dex */
public interface IAdapterCallback extends IInterface {
    void onBluetoothStateChanged(int i) throws RemoteException;

    void onConnectionStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) throws RemoteException;

    void onDeviceBondStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) throws RemoteException;

    void onDiscoveryDeviceFound(BluetoothDeviceInfo bluetoothDeviceInfo) throws RemoteException;

    void onDiscoveryDeviceNameChanged(BluetoothDeviceInfo bluetoothDeviceInfo) throws RemoteException;

    void onDiscoveryStateChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAdapterCallback {
        private static final String DESCRIPTOR = "com.hcn.bluetooth.service.IAdapterCallback";
        static final int TRANSACTION_onBluetoothStateChanged = 1;
        static final int TRANSACTION_onConnectionStateChanged = 6;
        static final int TRANSACTION_onDeviceBondStateChanged = 5;
        static final int TRANSACTION_onDiscoveryDeviceFound = 3;
        static final int TRANSACTION_onDiscoveryDeviceNameChanged = 4;
        static final int TRANSACTION_onDiscoveryStateChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAdapterCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAdapterCallback)) {
                return (IAdapterCallback) iInterfaceQueryLocalInterface;
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
                    onBluetoothStateChanged(parcel.readInt());
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    onDiscoveryStateChanged(parcel.readInt());
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    onDiscoveryDeviceFound(parcel.readInt() != 0 ? BluetoothDeviceInfo.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    onDiscoveryDeviceNameChanged(parcel.readInt() != 0 ? BluetoothDeviceInfo.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    onDeviceBondStateChanged(parcel.readInt() != 0 ? BluetoothDeviceInfo.CREATOR.createFromParcel(parcel) : null, parcel.readInt());
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    onConnectionStateChanged(parcel.readInt() != 0 ? BluetoothDeviceInfo.CREATOR.createFromParcel(parcel) : null, parcel.readInt());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAdapterCallback {
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

            @Override // com.hcn.bluetooth.service.IAdapterCallback
            public void onBluetoothStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IAdapterCallback
            public void onDiscoveryStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IAdapterCallback
            public void onDiscoveryDeviceFound(BluetoothDeviceInfo bluetoothDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDeviceInfo != null) {
                        parcelObtain.writeInt(1);
                        bluetoothDeviceInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IAdapterCallback
            public void onDiscoveryDeviceNameChanged(BluetoothDeviceInfo bluetoothDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDeviceInfo != null) {
                        parcelObtain.writeInt(1);
                        bluetoothDeviceInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IAdapterCallback
            public void onDeviceBondStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDeviceInfo != null) {
                        parcelObtain.writeInt(1);
                        bluetoothDeviceInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IAdapterCallback
            public void onConnectionStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDeviceInfo != null) {
                        parcelObtain.writeInt(1);
                        bluetoothDeviceInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
