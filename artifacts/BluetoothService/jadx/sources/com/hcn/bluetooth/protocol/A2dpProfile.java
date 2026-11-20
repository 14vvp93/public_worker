package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class A2dpProfile implements LocalBluetoothProfile {
    static final String NAME = "A2DP";
    private static final int ORDINAL = 1;
    static final ParcelUuid[] SINK_UUIDS = {BluetoothUuid.AudioSink, BluetoothUuid.AdvAudioDist};
    private static final String TAG = "A2dpProfile";
    private static boolean V = false;
    private Context mContext;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private boolean mIsProfileReady;
    private final LocalBluetoothAdapter mLocalAdapter;
    private final LocalBluetoothProfileManager mProfileManager;
    private BluetoothA2dp mService;

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getOrdinal() {
        return 1;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getProfileId() {
        return 2;
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

    private final class A2dpServiceListener implements BluetoothProfile.ServiceListener {
        private A2dpServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (A2dpProfile.V) {
                Log.d(A2dpProfile.TAG, "Bluetooth service connected");
            }
            A2dpProfile.this.mService = (BluetoothA2dp) bluetoothProfile;
            List<BluetoothDevice> connectedDevices = A2dpProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDeviceRemove = connectedDevices.remove(0);
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = A2dpProfile.this.mDeviceManager.findDevice(bluetoothDeviceRemove);
                if (cachedBluetoothDeviceFindDevice == null) {
                    Log.w(A2dpProfile.TAG, "A2dpProfile found new device: " + bluetoothDeviceRemove);
                    cachedBluetoothDeviceFindDevice = A2dpProfile.this.mDeviceManager.addDevice(A2dpProfile.this.mLocalAdapter, A2dpProfile.this.mProfileManager, bluetoothDeviceRemove);
                }
                cachedBluetoothDeviceFindDevice.onProfileStateChanged(A2dpProfile.this, 2);
                cachedBluetoothDeviceFindDevice.refresh();
            }
            A2dpProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (A2dpProfile.V) {
                Log.d(A2dpProfile.TAG, "Bluetooth service disconnected");
            }
            A2dpProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    A2dpProfile(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        localBluetoothAdapter.getProfileProxy(context, new A2dpServiceListener(), 2);
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothA2dp bluetoothA2dp = this.mService;
        return bluetoothA2dp == null ? new ArrayList(0) : bluetoothA2dp.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean connect(BluetoothDevice bluetoothDevice) {
        List<BluetoothDevice> connectedDevices;
        if (this.mService == null) {
            return false;
        }
        if (this.mLocalAdapter.getMaxConnectedAudioDevices() == 1 && (connectedDevices = getConnectedDevices()) != null) {
            for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                if (bluetoothDevice2.equals(bluetoothDevice)) {
                    Log.w(TAG, "Connecting to device " + bluetoothDevice + " : disconnect skipped");
                } else {
                    this.mService.disconnect(bluetoothDevice2);
                }
            }
        }
        return this.mService.connect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return false;
        }
        if (bluetoothA2dp.getPriority(bluetoothDevice) > 100) {
            this.mService.setPriority(bluetoothDevice, 100);
        }
        return this.mService.disconnect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return 0;
        }
        return bluetoothA2dp.getConnectionState(bluetoothDevice);
    }

    public boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return false;
        }
        return bluetoothA2dp.setActiveDevice(bluetoothDevice);
    }

    public BluetoothDevice getActiveDevice() {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return null;
        }
        return bluetoothA2dp.getActiveDevice();
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        return bluetoothA2dp != null && bluetoothA2dp.getPriority(bluetoothDevice) > 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return 0;
        }
        return bluetoothA2dp.getPriority(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return;
        }
        if (z) {
            if (bluetoothA2dp.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothA2dp.setPriority(bluetoothDevice, 0);
    }

    boolean isA2dpPlaying() {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return false;
        }
        Iterator<BluetoothDevice> it = bluetoothA2dp.getConnectedDevices().iterator();
        while (it.hasNext()) {
            if (this.mService.isA2dpPlaying(it.next())) {
                return true;
            }
        }
        return false;
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(2, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up A2DP proxy", th);
            }
        }
    }

    public void connectConfirm(BluetoothDevice bluetoothDevice, int i) {
        Log.d(TAG, "connectConfirm() acceptOrReject : " + i);
        Intent intent = new Intent();
        intent.setAction("android.bluetooth.a2dp_source.connect_confirm");
        intent.putExtra("confirmed", i);
        intent.putExtra("address", bluetoothDevice.getAddress());
        this.mContext.sendBroadcast(intent);
    }
}
