package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothA2dpSink;
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
public class A2dpSinkProfile implements LocalBluetoothProfile {
    static final String NAME = "A2DPSink";
    private static final int ORDINAL = 5;
    static final ParcelUuid[] SRC_UUIDS = {BluetoothUuid.AudioSource, BluetoothUuid.AdvAudioDist};
    private static final String TAG = "A2dpSinkProfile";
    private static boolean V = true;
    private Context mContext;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private boolean mIsProfileReady;
    private final LocalBluetoothAdapter mLocalAdapter;
    private final LocalBluetoothProfileManager mProfileManager;
    private BluetoothA2dpSink mService;

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getOrdinal() {
        return 5;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getProfileId() {
        return 11;
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

    private final class A2dpSinkServiceListener implements BluetoothProfile.ServiceListener {
        private A2dpSinkServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (A2dpSinkProfile.V) {
                Log.d(A2dpSinkProfile.TAG, "Bluetooth service connected");
            }
            A2dpSinkProfile.this.mService = (BluetoothA2dpSink) bluetoothProfile;
            List connectedDevices = A2dpSinkProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = A2dpSinkProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (cachedBluetoothDeviceFindDevice == null) {
                    Log.w(A2dpSinkProfile.TAG, "A2dpSinkProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDeviceFindDevice = A2dpSinkProfile.this.mDeviceManager.addDevice(A2dpSinkProfile.this.mLocalAdapter, A2dpSinkProfile.this.mProfileManager, bluetoothDevice);
                }
                cachedBluetoothDeviceFindDevice.onProfileStateChanged(A2dpSinkProfile.this, 2);
                cachedBluetoothDeviceFindDevice.refresh();
            }
            A2dpSinkProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (A2dpSinkProfile.V) {
                Log.d(A2dpSinkProfile.TAG, "Bluetooth service disconnected");
            }
            A2dpSinkProfile.this.mIsProfileReady = false;
        }
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    A2dpSinkProfile(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        localBluetoothAdapter.getProfileProxy(context, new A2dpSinkServiceListener(), 11);
    }

    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        return bluetoothA2dpSink == null ? new ArrayList(0) : bluetoothA2dpSink.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
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
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        if (bluetoothA2dpSink.getPriority(bluetoothDevice) > 100) {
            this.mService.setPriority(bluetoothDevice, 100);
        }
        return this.mService.disconnect(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return 0;
        }
        return bluetoothA2dpSink.getConnectionState(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public boolean isPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        return bluetoothA2dpSink != null && bluetoothA2dpSink.getPriority(bluetoothDevice) > 0;
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public int getPreferred(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return 0;
        }
        return bluetoothA2dpSink.getPriority(bluetoothDevice);
    }

    @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfile
    public void setPreferred(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return;
        }
        if (z) {
            if (bluetoothA2dpSink.getPriority(bluetoothDevice) < 100) {
                this.mService.setPriority(bluetoothDevice, 100);
                return;
            }
            return;
        }
        bluetoothA2dpSink.setPriority(bluetoothDevice, 0);
    }

    boolean isA2dpPlaying() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        List connectedDevices = bluetoothA2dpSink.getConnectedDevices();
        return !connectedDevices.isEmpty() && this.mService.isA2dpPlaying((BluetoothDevice) connectedDevices.get(0));
    }

    protected void finalize() {
        if (V) {
            Log.d(TAG, "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(11, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w(TAG, "Error cleaning up A2DP proxy", th);
            }
        }
    }

    public void connectConfirm(BluetoothDevice bluetoothDevice, int i) {
        Log.d(TAG, "[connectConfirm] acceptOrReject : " + i);
        Intent intent = new Intent();
        intent.setAction("android.bluetooth.a2dp_sink.connect_confirm");
        intent.putExtra("confirmed", i);
        intent.putExtra("address", bluetoothDevice.getAddress());
        this.mContext.sendBroadcast(intent);
    }

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        return bluetoothA2dpSink == null ? new ArrayList(0) : bluetoothA2dpSink.getDevicesMatchingConnectionStates(iArr);
    }
}
