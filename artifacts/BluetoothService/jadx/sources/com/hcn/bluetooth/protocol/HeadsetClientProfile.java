package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsetClientProfile implements LocalBluetoothProfile {
    public static final String NAME = "HEADSET_CLIENT";
    private static final int ORDINAL = 0;
    static final ParcelUuid[] SRC_UUIDS = {BluetoothUuid.HSP_AG, BluetoothUuid.Handsfree_AG};
    private static final String TAG = "HfpClientProfile";
    private static boolean V = false;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private boolean mIsProfileReady;
    private final LocalBluetoothAdapter mLocalAdapter;
    private final LocalBluetoothProfileManager mProfileManager;
    private BluetoothHeadsetClient mService;

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getOrdinal() {
        return 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getProfileId() {
        return 16;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isAutoConnectable() {
        return true;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isConnectable() {
        return true;
    }

    public String toString() {
        return NAME;
    }

    private final class HeadsetClientServiceListener implements BluetoothProfile.ServiceListener {
        private HeadsetClientServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (HeadsetClientProfile.V) {
                Log.d(HeadsetClientProfile.TAG, "Bluetooth service connected");
            }
            HeadsetClientProfile.this.mService = (BluetoothHeadsetClient) bluetoothProfile;
            List connectedDevices = HeadsetClientProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = HeadsetClientProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (cachedBluetoothDeviceFindDevice == null) {
                    Log.w(HeadsetClientProfile.TAG, "HfpClient profile found new device: " + bluetoothDevice);
                    cachedBluetoothDeviceFindDevice = HeadsetClientProfile.this.mDeviceManager.addDevice(HeadsetClientProfile.this.mLocalAdapter, HeadsetClientProfile.this.mProfileManager, bluetoothDevice);
                }
                cachedBluetoothDeviceFindDevice.onProfileStateChanged(HeadsetClientProfile.this, 2);
                cachedBluetoothDeviceFindDevice.refresh();
            }
            HeadsetClientProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (HeadsetClientProfile.V) {
                Log.d(HeadsetClientProfile.TAG, "Bluetooth service disconnected");
            }
            HeadsetClientProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    HeadsetClientProfile(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        localBluetoothAdapter.getProfileProxy(context, new HeadsetClientServiceListener(), 16);
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        return bluetoothHeadsetClient == null ? new ArrayList(0) : bluetoothHeadsetClient.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return false;
        }
        List<BluetoothDevice> connectedDevices = getConnectedDevices();
        if (connectedDevices != null) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().equals(bluetoothDevice)) {
                    Log.d(TAG, "Ignoring Connect");
                    return true;
                }
            }
        }
        return this.mService.connect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return false;
        }
        if (bluetoothHeadsetClient.getPriority(bluetoothDevice) > 100) {
            this.mService.setPriority(bluetoothDevice, 100);
        }
        return this.mService.disconnect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return 0;
        }
        return bluetoothHeadsetClient.getConnectionState(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        return bluetoothHeadsetClient != null && bluetoothHeadsetClient.getPriority(bluetoothDevice) > 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return 0;
        }
        return bluetoothHeadsetClient.getPriority(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return;
        }
        if (z) {
            if (bluetoothHeadsetClient.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothHeadsetClient.setPriority(bluetoothDevice, 0);
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(16, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up HfpClient proxy", th);
            }
        }
    }

    public BluetoothHeadsetClient getHeadsetClientServer() {
        return this.mService;
    }

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        return bluetoothHeadsetClient == null ? new ArrayList(0) : bluetoothHeadsetClient.getDevicesMatchingConnectionStates(iArr);
    }
}
