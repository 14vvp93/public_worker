package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPbap;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class PbapServerProfile implements LocalBluetoothProfile {
    public static final String NAME = "PBAP Server";
    private static final int ORDINAL = 6;
    static final ParcelUuid[] PBAB_CLIENT_UUIDS = {BluetoothUuid.HSP, BluetoothUuid.Handsfree, BluetoothUuid.PBAP_PCE};
    private static final String TAG = "PbapServerProfile";
    private static boolean V = true;
    private boolean mIsProfileReady;
    private BluetoothPbap mService;

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        return false;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getOrdinal() {
        return 6;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getPreferred(BluetoothDevice bluetoothDevice) {
        return -1;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getProfileId() {
        return 6;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isAutoConnectable() {
        return false;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isConnectable() {
        return true;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        return false;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
    }

    public String toString() {
        return NAME;
    }

    private final class PbapServiceListener implements BluetoothPbap.ServiceListener {
        private PbapServiceListener() {
        }

        public void onServiceConnected(BluetoothPbap bluetoothPbap) {
            if (PbapServerProfile.V) {
                Log.d(PbapServerProfile.TAG, "Bluetooth service connected");
            }
            PbapServerProfile.this.mService = bluetoothPbap;
            PbapServerProfile.this.mIsProfileReady = true;
        }

        public void onServiceDisconnected() {
            if (PbapServerProfile.V) {
                Log.d(PbapServerProfile.TAG, "Bluetooth service disconnected");
            }
            PbapServerProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    PbapServerProfile(Context context) {
        new BluetoothPbap(context, new PbapServiceListener());
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap == null) {
            return false;
        }
        return bluetoothPbap.disconnect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPbap bluetoothPbap = this.mService;
        return (bluetoothPbap != null && bluetoothPbap.isConnected(bluetoothDevice)) ? 2 : 0;
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap != null) {
            try {
                bluetoothPbap.close();
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up PBAP proxy", th);
            }
        }
    }
}
