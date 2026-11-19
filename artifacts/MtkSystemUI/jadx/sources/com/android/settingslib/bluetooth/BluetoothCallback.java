package com.android.settingslib.bluetooth;

/* JADX INFO: loaded from: classes.dex */
public interface BluetoothCallback {
    void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i);

    void onAudioModeChanged();

    void onBluetoothStateChanged(int i);

    void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i);

    void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice);

    void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i);

    void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice);

    void onScanningStateChanged(boolean z);

    default void onProfileConnectionStateChanged(CachedBluetoothDevice cachedDevice, int state, int bluetoothProfile) {
    }
}
