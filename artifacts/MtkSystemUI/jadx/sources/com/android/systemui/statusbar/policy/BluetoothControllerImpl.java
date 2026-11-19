package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.Dependency;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothControllerImpl implements BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener, BluetoothController {
    private static final boolean DEBUG;
    private final Handler mBgHandler;
    private final int mCurrentUser;
    private boolean mEnabled;
    private int mState;
    private final UserManager mUserManager;
    private final WeakHashMap<CachedBluetoothDevice, ActuallyCachedState> mCachedState = new WeakHashMap<>();
    private final List<CachedBluetoothDevice> mConnectedDevices = new ArrayList();
    private int mConnectionState = 0;
    private final H mHandler = new H(Looper.getMainLooper());
    private final LocalBluetoothManager mLocalBluetoothManager = (LocalBluetoothManager) Dependency.get(LocalBluetoothManager.class);

    static {
        DEBUG = Log.isLoggable("BluetoothController", 3) || FeatureOptions.LOG_ENABLE;
    }

    public BluetoothControllerImpl(Context context, Looper bgLooper) {
        this.mBgHandler = new Handler(bgLooper);
        if (this.mLocalBluetoothManager != null) {
            this.mLocalBluetoothManager.getEventManager().setReceiverHandler(this.mBgHandler);
            this.mLocalBluetoothManager.getEventManager().registerCallback(this);
            this.mLocalBluetoothManager.getProfileManager().addServiceListener(this);
            onBluetoothStateChanged(this.mLocalBluetoothManager.getBluetoothAdapter().getBluetoothState());
        }
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mCurrentUser = ActivityManager.getCurrentUser();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public boolean canConfigBluetooth() {
        return (this.mUserManager.hasUserRestriction("no_config_bluetooth", UserHandle.of(this.mCurrentUser)) || this.mUserManager.hasUserRestriction("no_bluetooth", UserHandle.of(this.mCurrentUser))) ? false : true;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("BluetoothController state:");
        pw.print("  mLocalBluetoothManager=");
        pw.println(this.mLocalBluetoothManager);
        if (this.mLocalBluetoothManager == null) {
            return;
        }
        pw.print("  mEnabled=");
        pw.println(this.mEnabled);
        pw.print("  mConnectionState=");
        pw.println(stateToString(this.mConnectionState));
        pw.print("  mConnectedDevices=");
        pw.println(this.mConnectedDevices);
        pw.print("  mCallbacks.size=");
        pw.println(this.mHandler.mCallbacks.size());
        pw.println("  Bluetooth Devices:");
        for (CachedBluetoothDevice device : getDevices()) {
            pw.println("    " + getDeviceString(device));
        }
    }

    private static String stateToString(int state) {
        switch (state) {
            case 0:
                return "DISCONNECTED";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "DISCONNECTING";
            default:
                return "UNKNOWN(" + state + ")";
        }
    }

    private String getDeviceString(CachedBluetoothDevice device) {
        return device.getName() + " " + device.getBondState() + " " + device.isConnected();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public int getBondState(CachedBluetoothDevice device) {
        return getCachedState(device).mBondState;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public List<CachedBluetoothDevice> getConnectedDevices() {
        return this.mConnectedDevices;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(BluetoothController.Callback cb) {
        this.mHandler.obtainMessage(3, cb).sendToTarget();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(BluetoothController.Callback cb) {
        this.mHandler.obtainMessage(4, cb).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public boolean isBluetoothEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public int getBluetoothState() {
        return this.mState;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public boolean isBluetoothConnected() {
        return this.mConnectionState == 2;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public boolean isBluetoothConnecting() {
        return this.mConnectionState == 1;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public void setBluetoothEnabled(boolean enabled) {
        if (this.mLocalBluetoothManager != null) {
            this.mLocalBluetoothManager.getBluetoothAdapter().setBluetoothEnabled(enabled);
        }
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public boolean isBluetoothSupported() {
        return this.mLocalBluetoothManager != null;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public void connect(CachedBluetoothDevice device) {
        if (this.mLocalBluetoothManager == null || device == null) {
            return;
        }
        device.connect(true);
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public void disconnect(CachedBluetoothDevice device) {
        if (this.mLocalBluetoothManager == null || device == null) {
            return;
        }
        device.disconnect();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public String getConnectedDeviceName() {
        if (this.mConnectedDevices.size() == 1) {
            return this.mConnectedDevices.get(0).getName();
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public Collection<CachedBluetoothDevice> getDevices() {
        if (this.mLocalBluetoothManager != null) {
            return this.mLocalBluetoothManager.getCachedDeviceManager().getCachedDevicesCopy();
        }
        return null;
    }

    private void updateConnected() {
        int state = this.mLocalBluetoothManager.getBluetoothAdapter().getConnectionState();
        this.mConnectedDevices.clear();
        for (CachedBluetoothDevice device : getDevices()) {
            if (device.isConnected()) {
                this.mConnectedDevices.add(device);
            }
        }
        Log.d("BluetoothController", "updateConnected: devices size=" + this.mConnectedDevices.size() + " state=" + state);
        if (this.mConnectedDevices.isEmpty()) {
        }
        Log.d("BluetoothController", "updateConnected: state=" + state + " mConnectionState=" + this.mConnectionState);
        if (state != this.mConnectionState) {
            this.mConnectionState = state;
            this.mHandler.sendEmptyMessage(2);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onBluetoothStateChanged(int bluetoothState) {
        this.mEnabled = bluetoothState == 12 || bluetoothState == 11;
        if (DEBUG) {
            Log.d("BluetoothController", "onBluetoothStateChanged, bluetoothState = " + bluetoothState + ", mEnabled = " + this.mEnabled);
        }
        this.mState = bluetoothState;
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onScanningStateChanged(boolean started) {
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onDeviceAdded(CachedBluetoothDevice cachedDevice) {
        cachedDevice.registerCallback(this);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onDeviceDeleted(CachedBluetoothDevice cachedDevice) {
        this.mCachedState.remove(cachedDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onDeviceBondStateChanged(CachedBluetoothDevice cachedDevice, int bondState) {
        this.mCachedState.remove(cachedDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public void onDeviceAttributesChanged() {
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onConnectionStateChanged(CachedBluetoothDevice cachedDevice, int state) {
        this.mCachedState.remove(cachedDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onActiveDeviceChanged(CachedBluetoothDevice activeDevice, int bluetoothProfile) {
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onAudioModeChanged() {
    }

    private ActuallyCachedState getCachedState(CachedBluetoothDevice device) {
        ActuallyCachedState state = this.mCachedState.get(device);
        if (state == null) {
            ActuallyCachedState state2 = new ActuallyCachedState(device, this.mHandler);
            this.mBgHandler.post(state2);
            this.mCachedState.put(device, state2);
            return state2;
        }
        return state;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceConnected() {
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceDisconnected() {
    }

    private static class ActuallyCachedState implements Runnable {
        private int mBondState;
        private final WeakReference<CachedBluetoothDevice> mDevice;
        private int mMaxConnectionState;
        private final Handler mUiHandler;

        private ActuallyCachedState(CachedBluetoothDevice device, Handler uiHandler) {
            this.mBondState = 10;
            this.mMaxConnectionState = 0;
            this.mDevice = new WeakReference<>(device);
            this.mUiHandler = uiHandler;
        }

        @Override // java.lang.Runnable
        public void run() {
            CachedBluetoothDevice device = this.mDevice.get();
            if (device != null) {
                this.mBondState = device.getBondState();
                this.mMaxConnectionState = device.getMaxConnectionState();
                this.mUiHandler.removeMessages(1);
                this.mUiHandler.sendEmptyMessage(1);
            }
        }
    }

    private final class H extends Handler {
        private final ArrayList<BluetoothController.Callback> mCallbacks;

        public H(Looper looper) {
            super(looper);
            this.mCallbacks = new ArrayList<>();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    firePairedDevicesChanged();
                    break;
                case 2:
                    fireStateChange();
                    break;
                case 3:
                    this.mCallbacks.add((BluetoothController.Callback) msg.obj);
                    break;
                case 4:
                    this.mCallbacks.remove((BluetoothController.Callback) msg.obj);
                    break;
            }
        }

        private void firePairedDevicesChanged() {
            for (BluetoothController.Callback cb : this.mCallbacks) {
                cb.onBluetoothDevicesChanged();
            }
        }

        private void fireStateChange() {
            for (BluetoothController.Callback cb : this.mCallbacks) {
                fireStateChange(cb);
            }
        }

        private void fireStateChange(BluetoothController.Callback cb) {
            cb.onBluetoothStateChange(BluetoothControllerImpl.this.mEnabled);
        }
    }
}
