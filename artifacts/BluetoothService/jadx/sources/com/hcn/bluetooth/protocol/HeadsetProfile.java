package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HeadsetProfile implements LocalBluetoothProfile {
    static final String NAME = "HEADSET";
    private static final int ORDINAL = 0;
    private static final String TAG = "HeadsetProfile";
    static final ParcelUuid[] UUIDS = {BluetoothUuid.HSP, BluetoothUuid.Handsfree};
    private static boolean V = true;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private boolean mIsProfileReady;
    private final LocalBluetoothAdapter mLocalAdapter;
    private final LocalBluetoothProfileManager mProfileManager;
    private BluetoothHeadset mService;

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getOrdinal() {
        return 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getProfileId() {
        return 1;
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

    private final class HeadsetServiceListener implements BluetoothProfile.ServiceListener {
        private HeadsetServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (HeadsetProfile.V) {
                Log.d(HeadsetProfile.TAG, "Bluetooth service connected");
            }
            HeadsetProfile.this.mService = (BluetoothHeadset) bluetoothProfile;
            List<BluetoothDevice> connectedDevices = HeadsetProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDeviceRemove = connectedDevices.remove(0);
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = HeadsetProfile.this.mDeviceManager.findDevice(bluetoothDeviceRemove);
                if (cachedBluetoothDeviceFindDevice == null) {
                    Log.w(HeadsetProfile.TAG, "HeadsetProfile found new device: " + bluetoothDeviceRemove);
                    cachedBluetoothDeviceFindDevice = HeadsetProfile.this.mDeviceManager.addDevice(HeadsetProfile.this.mLocalAdapter, HeadsetProfile.this.mProfileManager, bluetoothDeviceRemove);
                }
                cachedBluetoothDeviceFindDevice.onProfileStateChanged(HeadsetProfile.this, 2);
                cachedBluetoothDeviceFindDevice.refresh();
            }
            HeadsetProfile.this.mProfileManager.callServiceConnectedListeners();
            HeadsetProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (HeadsetProfile.V) {
                Log.d(HeadsetProfile.TAG, "Bluetooth service disconnected");
            }
            HeadsetProfile.this.mProfileManager.callServiceDisconnectedListeners();
            HeadsetProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    HeadsetProfile(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        localBluetoothAdapter.getProfileProxy(context, new HeadsetServiceListener(), 1);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "Headset connect device = " + bluetoothDevice);
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return false;
        }
        List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
        if (connectedDevices != null) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                Log.d(TAG, "Not disconnecting device = " + it.next());
            }
        }
        return this.mService.connect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return false;
        }
        List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
        if (!connectedDevices.isEmpty()) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().equals(bluetoothDevice)) {
                    if (V) {
                        Log.d(TAG, "Downgrade priority as useris disconnecting the headset");
                    }
                    if (this.mService.getPriority(bluetoothDevice) > 100) {
                        this.mService.setPriority(bluetoothDevice, 100);
                    }
                    return this.mService.disconnect(bluetoothDevice);
                }
            }
        }
        return false;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return 0;
        }
        List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
        if (!connectedDevices.isEmpty()) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().equals(bluetoothDevice)) {
                    return this.mService.getConnectionState(bluetoothDevice);
                }
            }
        }
        return 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        return bluetoothHeadset != null && bluetoothHeadset.getPriority(bluetoothDevice) > 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return 0;
        }
        return bluetoothHeadset.getPriority(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return;
        }
        if (z) {
            if (bluetoothHeadset.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothHeadset.setPriority(bluetoothDevice, 0);
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothHeadset bluetoothHeadset = this.mService;
        return bluetoothHeadset == null ? new ArrayList(0) : bluetoothHeadset.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(1, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up HID proxy", th);
            }
        }
    }
}
