package com.hcn.bluetooth.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IBluetoothAdapterManagerService extends IInterface {
    void connectA2dp(String str) throws RemoteException;

    void connectDevice(String str) throws RemoteException;

    void connectPbap() throws RemoteException;

    void disConnectPbap() throws RemoteException;

    void disconnectA2dp(String str) throws RemoteException;

    void disconnectDevice(String str) throws RemoteException;

    String getBTName() throws RemoteException;

    String getBTPincode() throws RemoteException;

    List<BluetoothDeviceInfo> getBondedDevices() throws RemoteException;

    BluetoothDeviceInfo getConnectDevice() throws RemoteException;

    List<BluetoothDeviceInfo> getDeviceList() throws RemoteException;

    boolean getPbapDownLoadState(int i) throws RemoteException;

    int getThirdPartAPPMode() throws RemoteException;

    boolean isA2dpConnected(String str) throws RemoteException;

    boolean isAutoConnectedNetwork() throws RemoteException;

    boolean isBluetoothAutoAnswer() throws RemoteException;

    boolean isBluetoothAutoConnect() throws RemoteException;

    boolean isBluetoothConnected() throws RemoteException;

    boolean isBluetoothEnable() throws RemoteException;

    boolean isDiscovering() throws RemoteException;

    boolean isPbapConnected() throws RemoteException;

    void pairDevice(String str) throws RemoteException;

    boolean pbapStartDownLoad(int i) throws RemoteException;

    void registerCallback(IAdapterCallback iAdapterCallback) throws RemoteException;

    void registerPbapCallback(IPbapCallback iPbapCallback) throws RemoteException;

    void resetBT() throws RemoteException;

    void setAutoConnectedNetwork(boolean z) throws RemoteException;

    void setBTName(String str) throws RemoteException;

    void setBTPincode(String str) throws RemoteException;

    void setBluetoothAutoAnswer(boolean z) throws RemoteException;

    void setBluetoothAutoConnect(boolean z) throws RemoteException;

    void setBluetoothEnable(boolean z) throws RemoteException;

    void startDiscovery() throws RemoteException;

    void stopDiscovery() throws RemoteException;

    void unpairDevice(String str) throws RemoteException;

    void unregisterCallback(IAdapterCallback iAdapterCallback) throws RemoteException;

    void unregisterPbapCallback(IPbapCallback iPbapCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IBluetoothAdapterManagerService {
        private static final String DESCRIPTOR = "com.hcn.bluetooth.service.IBluetoothAdapterManagerService";
        static final int TRANSACTION_connectA2dp = 16;
        static final int TRANSACTION_connectDevice = 11;
        static final int TRANSACTION_connectPbap = 29;
        static final int TRANSACTION_disConnectPbap = 30;
        static final int TRANSACTION_disconnectA2dp = 17;
        static final int TRANSACTION_disconnectDevice = 12;
        static final int TRANSACTION_getBTName = 4;
        static final int TRANSACTION_getBTPincode = 6;
        static final int TRANSACTION_getBondedDevices = 9;
        static final int TRANSACTION_getConnectDevice = 10;
        static final int TRANSACTION_getDeviceList = 8;
        static final int TRANSACTION_getPbapDownLoadState = 32;
        static final int TRANSACTION_getThirdPartAPPMode = 37;
        static final int TRANSACTION_isA2dpConnected = 18;
        static final int TRANSACTION_isAutoConnectedNetwork = 24;
        static final int TRANSACTION_isBluetoothAutoAnswer = 20;
        static final int TRANSACTION_isBluetoothAutoConnect = 22;
        static final int TRANSACTION_isBluetoothConnected = 13;
        static final int TRANSACTION_isBluetoothEnable = 2;
        static final int TRANSACTION_isDiscovering = 27;
        static final int TRANSACTION_isPbapConnected = 28;
        static final int TRANSACTION_pairDevice = 14;
        static final int TRANSACTION_pbapStartDownLoad = 31;
        static final int TRANSACTION_registerCallback = 35;
        static final int TRANSACTION_registerPbapCallback = 33;
        static final int TRANSACTION_resetBT = 3;
        static final int TRANSACTION_setAutoConnectedNetwork = 23;
        static final int TRANSACTION_setBTName = 5;
        static final int TRANSACTION_setBTPincode = 7;
        static final int TRANSACTION_setBluetoothAutoAnswer = 19;
        static final int TRANSACTION_setBluetoothAutoConnect = 21;
        static final int TRANSACTION_setBluetoothEnable = 1;
        static final int TRANSACTION_startDiscovery = 25;
        static final int TRANSACTION_stopDiscovery = 26;
        static final int TRANSACTION_unpairDevice = 15;
        static final int TRANSACTION_unregisterCallback = 36;
        static final int TRANSACTION_unregisterPbapCallback = 34;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothAdapterManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBluetoothAdapterManagerService)) {
                return (IBluetoothAdapterManagerService) iInterfaceQueryLocalInterface;
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
                    setBluetoothEnable(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsBluetoothEnable = isBluetoothEnable();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsBluetoothEnable ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    resetBT();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    String bTName = getBTName();
                    parcel2.writeNoException();
                    parcel2.writeString(bTName);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    setBTName(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    String bTPincode = getBTPincode();
                    parcel2.writeNoException();
                    parcel2.writeString(bTPincode);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    setBTPincode(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<BluetoothDeviceInfo> deviceList = getDeviceList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(deviceList);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<BluetoothDeviceInfo> bondedDevices = getBondedDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(bondedDevices);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    BluetoothDeviceInfo connectDevice = getConnectDevice();
                    parcel2.writeNoException();
                    if (connectDevice != null) {
                        parcel2.writeInt(1);
                        connectDevice.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    connectDevice(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    disconnectDevice(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsBluetoothConnected = isBluetoothConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsBluetoothConnected ? 1 : 0);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    pairDevice(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    unpairDevice(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    connectA2dp(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    disconnectA2dp(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsA2dpConnected = isA2dpConnected(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsA2dpConnected ? 1 : 0);
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    setBluetoothAutoAnswer(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsBluetoothAutoAnswer = isBluetoothAutoAnswer();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsBluetoothAutoAnswer ? 1 : 0);
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    setBluetoothAutoConnect(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsBluetoothAutoConnect = isBluetoothAutoConnect();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsBluetoothAutoConnect ? 1 : 0);
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    setAutoConnectedNetwork(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsAutoConnectedNetwork = isAutoConnectedNetwork();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsAutoConnectedNetwork ? 1 : 0);
                    return true;
                case 25:
                    parcel.enforceInterface(DESCRIPTOR);
                    startDiscovery();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface(DESCRIPTOR);
                    stopDiscovery();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsDiscovering = isDiscovering();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsDiscovering ? 1 : 0);
                    return true;
                case 28:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsPbapConnected = isPbapConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsPbapConnected ? 1 : 0);
                    return true;
                case 29:
                    parcel.enforceInterface(DESCRIPTOR);
                    connectPbap();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface(DESCRIPTOR);
                    disConnectPbap();
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zPbapStartDownLoad = pbapStartDownLoad(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zPbapStartDownLoad ? 1 : 0);
                    return true;
                case 32:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean pbapDownLoadState = getPbapDownLoadState(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(pbapDownLoadState ? 1 : 0);
                    return true;
                case 33:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerPbapCallback(IPbapCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterPbapCallback(IPbapCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerCallback(IAdapterCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 36:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterCallback(IAdapterCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 37:
                    parcel.enforceInterface(DESCRIPTOR);
                    int thirdPartAPPMode = getThirdPartAPPMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(thirdPartAPPMode);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBluetoothAdapterManagerService {
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

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void setBluetoothEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isBluetoothEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void resetBT() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public String getBTName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void setBTName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public String getBTPincode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void setBTPincode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public List<BluetoothDeviceInfo> getDeviceList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public List<BluetoothDeviceInfo> getBondedDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BluetoothDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public BluetoothDeviceInfo getConnectDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? BluetoothDeviceInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void connectDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void disconnectDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isBluetoothConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void pairDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void unpairDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void connectA2dp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void disconnectA2dp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isA2dpConnected(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void setBluetoothAutoAnswer(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isBluetoothAutoAnswer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void setBluetoothAutoConnect(boolean z) throws RemoteException {
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

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isBluetoothAutoConnect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void setAutoConnectedNetwork(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isAutoConnectedNetwork() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void startDiscovery() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void stopDiscovery() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isDiscovering() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean isPbapConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void connectPbap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void disConnectPbap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean pbapStartDownLoad(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public boolean getPbapDownLoadState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void registerPbapCallback(IPbapCallback iPbapCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iPbapCallback != null ? iPbapCallback.asBinder() : null);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void unregisterPbapCallback(IPbapCallback iPbapCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iPbapCallback != null ? iPbapCallback.asBinder() : null);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void registerCallback(IAdapterCallback iAdapterCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iAdapterCallback != null ? iAdapterCallback.asBinder() : null);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public void unregisterCallback(IAdapterCallback iAdapterCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iAdapterCallback != null ? iAdapterCallback.asBinder() : null);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
            public int getThirdPartAPPMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
