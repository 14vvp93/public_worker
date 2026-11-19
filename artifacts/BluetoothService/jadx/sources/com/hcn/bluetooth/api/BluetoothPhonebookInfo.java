package com.hcn.bluetooth.api;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothPhonebookInfo implements Parcelable, Cloneable {

    /* JADX INFO: renamed from: id */
    public long f72id;
    public String mTelephone;
    public int mType;
    public String mUserName;
    private static final BluetoothPhonebookInfo sProtoInfo = new BluetoothPhonebookInfo();
    public static final Parcelable.Creator<BluetoothPhonebookInfo> CREATOR = new Parcelable.Creator<BluetoothPhonebookInfo>() { // from class: com.hcn.bluetooth.api.BluetoothPhonebookInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothPhonebookInfo createFromParcel(Parcel parcel) {
            return new BluetoothPhonebookInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothPhonebookInfo[] newArray(int i) {
            return new BluetoothPhonebookInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static synchronized BluetoothPhonebookInfo getNewObject() {
        return (BluetoothPhonebookInfo) sProtoInfo.clone();
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder().append("id:").append(this.f72id).append("type: ").append(this.mType).append("telephone: ");
        String str = this.mTelephone;
        if (str == null) {
            str = "null";
        }
        StringBuilder sbAppend2 = sbAppend.append(str).append("name: ");
        String str2 = this.mUserName;
        return sbAppend2.append(str2 != null ? str2 : "null").toString();
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public String getTelephone() {
        return this.mTelephone;
    }

    public void setTelephone(String str) {
        this.mTelephone = str;
    }

    public String getUserName() {
        return this.mUserName;
    }

    public void setUserName(String str) {
        this.mUserName = str;
    }

    public long getId() {
        return this.f72id;
    }

    public void setId(long j) {
        this.f72id = j;
    }

    public BluetoothPhonebookInfo reset() {
        this.mType = -1;
        this.mTelephone = null;
        this.mUserName = null;
        this.f72id = 0L;
        return this;
    }

    public Object clone() {
        BluetoothPhonebookInfo bluetoothPhonebookInfo;
        try {
            bluetoothPhonebookInfo = (BluetoothPhonebookInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            bluetoothPhonebookInfo = null;
        }
        bluetoothPhonebookInfo.reset();
        return bluetoothPhonebookInfo;
    }

    public BluetoothPhonebookInfo() {
        this.mTelephone = null;
        this.mUserName = null;
        this.mType = 0;
        this.mTelephone = new String();
        this.mUserName = new String();
        this.f72id = 0L;
    }

    private BluetoothPhonebookInfo(Parcel parcel) {
        this.mTelephone = null;
        this.mUserName = null;
        this.mType = parcel.readInt();
        this.mTelephone = parcel.readString();
        this.mUserName = parcel.readString();
        this.f72id = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeString(this.mTelephone);
        parcel.writeString(this.mUserName);
        parcel.writeLong(this.f72id);
    }
}
