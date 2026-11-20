package com.mtk.bluetooth.common;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface BluetoothA2dpWrapper {

    public interface Factory {
        BluetoothA2dpWrapper getInstance(BluetoothA2dp bluetoothA2dp);
    }

    BluetoothCodecStatus getCodecStatus();

    int getOptionalCodecsEnabled(BluetoothDevice bluetoothDevice);

    BluetoothA2dp getService();

    void setOptionalCodecsEnabled(BluetoothDevice bluetoothDevice, int i);

    int supportsOptionalCodecs(BluetoothDevice bluetoothDevice);
}
