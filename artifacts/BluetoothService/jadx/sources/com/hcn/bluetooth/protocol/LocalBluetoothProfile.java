package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface LocalBluetoothProfile {
    boolean connect(BluetoothDevice bluetoothDevice);

    boolean disconnect(BluetoothDevice bluetoothDevice);

    int getConnectionStatus(BluetoothDevice bluetoothDevice);

    int getOrdinal();

    int getPreferred(BluetoothDevice bluetoothDevice);

    int getProfileId();

    boolean isAutoConnectable();

    boolean isConnectable();

    boolean isPreferred(BluetoothDevice bluetoothDevice);

    boolean isProfileReady();

    void setPreferred(BluetoothDevice bluetoothDevice, boolean z);
}
