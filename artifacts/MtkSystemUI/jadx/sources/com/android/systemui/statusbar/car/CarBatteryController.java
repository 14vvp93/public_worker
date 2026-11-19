package com.android.systemui.statusbar.car;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.statusbar.policy.BatteryController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CarBatteryController extends BroadcastReceiver implements BatteryController {
    private BatteryViewHandler mBatteryViewHandler;
    private BluetoothHeadsetClient mBluetoothHeadsetClient;
    private final Context mContext;
    private int mLevel;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final ArrayList<BatteryController.BatteryStateChangeCallback> mChangeCallbacks = new ArrayList<>();
    private final BluetoothProfile.ServiceListener mHfpServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.systemui.statusbar.car.CarBatteryController.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if (profile == 16) {
                CarBatteryController.this.mBluetoothHeadsetClient = (BluetoothHeadsetClient) proxy;
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int profile) {
            if (profile == 16) {
                CarBatteryController.this.mBluetoothHeadsetClient = null;
            }
        }
    };

    public interface BatteryViewHandler {
        void hideBatteryView();

        void showBatteryView();
    }

    public CarBatteryController(Context context) {
        this.mContext = context;
        if (this.mAdapter == null) {
            return;
        }
        this.mAdapter.getProfileProxy(context.getApplicationContext(), this.mHfpServiceListener, 16);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("CarBatteryController state:");
        pw.print("    mLevel=");
        pw.println(this.mLevel);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(BatteryController.BatteryStateChangeCallback cb) {
        this.mChangeCallbacks.add(cb);
        cb.onBatteryLevelChanged(this.mLevel, false, false);
        cb.onPowerSaveChanged(false);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(BatteryController.BatteryStateChangeCallback cb) {
        this.mChangeCallbacks.remove(cb);
    }

    public void addBatteryViewHandler(BatteryViewHandler batteryViewHandler) {
        this.mBatteryViewHandler = batteryViewHandler;
    }

    public void startListening() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_EVENT");
        this.mContext.registerReceiver(this, filter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Log.isLoggable("CarBatteryController", 3)) {
            Log.d("CarBatteryController", "onReceive(). action: " + action);
        }
        if ("android.bluetooth.headsetclient.profile.action.AG_EVENT".equals(action)) {
            if (Log.isLoggable("CarBatteryController", 3)) {
                Log.d("CarBatteryController", "Received ACTION_AG_EVENT");
            }
            int batteryLevel = intent.getIntExtra("android.bluetooth.headsetclient.extra.BATTERY_LEVEL", -1);
            updateBatteryLevel(batteryLevel);
            if (batteryLevel != -1 && this.mBatteryViewHandler != null) {
                this.mBatteryViewHandler.showBatteryView();
                return;
            }
            return;
        }
        if ("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
            int newState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            if (Log.isLoggable("CarBatteryController", 3)) {
                int oldState = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -1);
                Log.d("CarBatteryController", "ACTION_CONNECTION_STATE_CHANGED event: " + oldState + " -> " + newState);
            }
            BluetoothDevice device = (BluetoothDevice) intent.getExtra("android.bluetooth.device.extra.DEVICE");
            updateBatteryIcon(device, newState);
        }
    }

    private void updateBatteryLevel(int batteryLevel) {
        if (batteryLevel == -1) {
            if (Log.isLoggable("CarBatteryController", 3)) {
                Log.d("CarBatteryController", "Battery level invalid. Ignoring.");
                return;
            }
            return;
        }
        switch (batteryLevel) {
            case 1:
                this.mLevel = 12;
                break;
            case 2:
                this.mLevel = 28;
                break;
            case 3:
                this.mLevel = 63;
                break;
            case 4:
                this.mLevel = 87;
                break;
            case 5:
                this.mLevel = 100;
                break;
            default:
                this.mLevel = 0;
                break;
        }
        if (Log.isLoggable("CarBatteryController", 3)) {
            Log.d("CarBatteryController", "Battery level: " + batteryLevel + "; setting mLevel as: " + this.mLevel);
        }
        notifyBatteryLevelChanged();
    }

    private void updateBatteryIcon(BluetoothDevice device, int newState) {
        Bundle featuresBundle;
        if (newState == 2) {
            if (Log.isLoggable("CarBatteryController", 3)) {
                Log.d("CarBatteryController", "Device connected");
            }
            if (this.mBatteryViewHandler != null) {
                this.mBatteryViewHandler.showBatteryView();
            }
            if (this.mBluetoothHeadsetClient == null || device == null || (featuresBundle = this.mBluetoothHeadsetClient.getCurrentAgEvents(device)) == null) {
                return;
            }
            int batteryLevel = featuresBundle.getInt("android.bluetooth.headsetclient.extra.BATTERY_LEVEL", -1);
            updateBatteryLevel(batteryLevel);
            return;
        }
        if (newState == 0) {
            if (Log.isLoggable("CarBatteryController", 3)) {
                Log.d("CarBatteryController", "Device disconnected");
            }
            if (this.mBatteryViewHandler != null) {
                this.mBatteryViewHandler.hideBatteryView();
            }
        }
    }

    @Override // com.android.systemui.DemoMode
    public void dispatchDemoCommand(String command, Bundle args) {
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController
    public boolean isPowerSave() {
        return false;
    }

    private void notifyBatteryLevelChanged() {
        int size = this.mChangeCallbacks.size();
        for (int i = 0; i < size; i++) {
            this.mChangeCallbacks.get(i).onBatteryLevelChanged(this.mLevel, false, false);
        }
    }
}
