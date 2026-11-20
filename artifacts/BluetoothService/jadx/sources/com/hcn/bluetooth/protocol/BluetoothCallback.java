package com.hcn.bluetooth.protocol;

/* JADX INFO: loaded from: classes.dex */
public interface BluetoothCallback {
    void onBluetoothStateChanged(int i);

    void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i);

    void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice);

    void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i);

    void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice);

    void onScanningStateChanged(boolean z);
}
