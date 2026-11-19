package com.hcn.bluetooth.api;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.autochips.bluetooth.skin.SkinID;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothDeviceInfo implements Parcelable {
    public static final Parcelable.Creator<BluetoothDeviceInfo> CREATOR = new Parcelable.Creator<BluetoothDeviceInfo>() { // from class: com.hcn.bluetooth.api.BluetoothDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothDeviceInfo createFromParcel(Parcel parcel) {
            return new BluetoothDeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothDeviceInfo[] newArray(int i) {
            return new BluetoothDeviceInfo[i];
        }
    };
    public String mDeviceAddr;
    public int mDeviceIndex;
    public String mDeviceName;
    public int mDeviceStatus;

    public static final class DeviceStatus {
        public static final int DEVICE_STATUS_CONNECTED = 1;
        public static final int DEVICE_STATUS_CONNECTEDMEDIA = 3;
        public static final int DEVICE_STATUS_CONNECTEDPHONE = 2;
        public static final int DEVICE_STATUS_CONNECTING = 4;
        public static final int DEVICE_STATUS_DISCONNECTING = 5;
        public static final int DEVICE_STATUS_PAIRED = 0;
        public static final int DEVICE_STATUS_UNKOWN = -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BluetoothDeviceInfo() {
        this.mDeviceName = null;
        this.mDeviceAddr = null;
        this.mDeviceIndex = -1;
        this.mDeviceStatus = -1;
        this.mDeviceName = SkinID.SKIN_NONE;
        this.mDeviceAddr = SkinID.SKIN_NONE;
    }

    public BluetoothDeviceInfo(BluetoothDevice bluetoothDevice) {
        this.mDeviceName = null;
        this.mDeviceAddr = null;
        if (bluetoothDevice == null) {
            this.mDeviceIndex = -1;
            this.mDeviceStatus = -1;
            this.mDeviceName = SkinID.SKIN_NONE;
            this.mDeviceAddr = SkinID.SKIN_NONE;
            return;
        }
        if (!TextUtils.isEmpty(bluetoothDevice.getAliasName())) {
            this.mDeviceName = bluetoothDevice.getAliasName();
        } else if (!TextUtils.isEmpty(bluetoothDevice.getName())) {
            this.mDeviceName = bluetoothDevice.getName();
        } else {
            this.mDeviceName = bluetoothDevice.getAddress();
        }
        this.mDeviceAddr = bluetoothDevice.getAddress();
        this.mDeviceStatus = bluetoothDevice.getBondState();
    }

    private BluetoothDeviceInfo(Parcel parcel) {
        this.mDeviceName = null;
        this.mDeviceAddr = null;
        this.mDeviceIndex = parcel.readInt();
        this.mDeviceStatus = parcel.readInt();
        this.mDeviceName = parcel.readString();
        this.mDeviceAddr = parcel.readString();
    }

    public int getDeviceIndex() {
        return this.mDeviceIndex;
    }

    public void setDeviceIndex(int i) {
        this.mDeviceIndex = i;
    }

    public int getDeviceStatus() {
        return this.mDeviceStatus;
    }

    public void setDeviceStatus(int i) {
        this.mDeviceStatus = i;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public void setDeviceName(String str) {
        this.mDeviceName = str;
    }

    public String getDeviceAddr() {
        return this.mDeviceAddr;
    }

    public void setDeviceAddr(String str) {
        this.mDeviceAddr = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceIndex);
        parcel.writeInt(this.mDeviceStatus);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mDeviceAddr);
    }
}
