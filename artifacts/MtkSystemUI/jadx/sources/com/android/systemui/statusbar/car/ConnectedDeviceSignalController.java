package com.android.systemui.statusbar.car;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.BluetoothController;

/* JADX INFO: loaded from: classes.dex */
public class ConnectedDeviceSignalController extends BroadcastReceiver implements BluetoothController.Callback {
    private static final int[] SIGNAL_STRENGTH_ICONS = {0, 0, 1, 2, 3, 4};
    private BluetoothHeadsetClient mBluetoothHeadsetClient;
    private final ImageView mNetworkSignalView;
    private final SignalDrawable mSignalDrawable;
    private final View mSignalsView;

    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
    public void onBluetoothDevicesChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
    public void onBluetoothStateChange(boolean enabled) {
        if (StatusBar.DEBUG) {
            Log.d("DeviceSignalCtlr", "onBluetoothStateChange(). enabled: " + enabled);
        }
        if (!enabled) {
            this.mNetworkSignalView.setVisibility(8);
            this.mSignalsView.setVisibility(8);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (StatusBar.DEBUG) {
            Log.d("DeviceSignalCtlr", "onReceive(). action: " + action);
        }
        if ("android.bluetooth.headsetclient.profile.action.AG_EVENT".equals(action)) {
            if (StatusBar.DEBUG) {
                Log.d("DeviceSignalCtlr", "Received ACTION_AG_EVENT");
            }
            processActionAgEvent(intent);
        } else if ("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
            int newState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            if (StatusBar.DEBUG) {
                int oldState = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -1);
                Log.d("DeviceSignalCtlr", "ACTION_CONNECTION_STATE_CHANGED event: " + oldState + " -> " + newState);
            }
            BluetoothDevice device = (BluetoothDevice) intent.getExtra("android.bluetooth.device.extra.DEVICE");
            updateViewVisibility(device, newState);
        }
    }

    private void processActionAgEvent(Intent intent) {
        int networkStatus = intent.getIntExtra("android.bluetooth.headsetclient.extra.NETWORK_STATUS", -1);
        if (networkStatus != -1) {
            if (StatusBar.DEBUG) {
                Log.d("DeviceSignalCtlr", "EXTRA_NETWORK_STATUS:  " + networkStatus);
            }
            if (networkStatus == 0) {
                setNetworkSignalIcon(R.drawable.stat_sys_signal_null);
            }
        }
        int signalStrength = intent.getIntExtra("android.bluetooth.headsetclient.extra.NETWORK_SIGNAL_STRENGTH", -1);
        if (signalStrength != -1) {
            if (StatusBar.DEBUG) {
                Log.d("DeviceSignalCtlr", "EXTRA_NETWORK_SIGNAL_STRENGTH: " + signalStrength);
            }
            setNetworkSignalIcon(SIGNAL_STRENGTH_ICONS[signalStrength]);
        }
        int roamingStatus = intent.getIntExtra("android.bluetooth.headsetclient.extra.NETWORK_ROAMING", -1);
        if (roamingStatus != -1 && StatusBar.DEBUG) {
            Log.d("DeviceSignalCtlr", "EXTRA_NETWORK_ROAMING: " + roamingStatus);
        }
    }

    private void setNetworkSignalIcon(int level) {
        this.mSignalsView.setVisibility(0);
        this.mSignalDrawable.setLevel(SignalDrawable.getState(level, 5, false));
        this.mNetworkSignalView.setVisibility(0);
    }

    private void updateViewVisibility(BluetoothDevice device, int newState) {
        Bundle featuresBundle;
        int signalStrength;
        if (newState == 2) {
            if (StatusBar.DEBUG) {
                Log.d("DeviceSignalCtlr", "Device connected");
            }
            if (this.mBluetoothHeadsetClient != null && device != null && (featuresBundle = this.mBluetoothHeadsetClient.getCurrentAgEvents(device)) != null && (signalStrength = featuresBundle.getInt("android.bluetooth.headsetclient.extra.NETWORK_SIGNAL_STRENGTH", -1)) != -1) {
                if (StatusBar.DEBUG) {
                    Log.d("DeviceSignalCtlr", "EXTRA_NETWORK_SIGNAL_STRENGTH: " + signalStrength);
                }
                setNetworkSignalIcon(SIGNAL_STRENGTH_ICONS[signalStrength]);
                return;
            }
            return;
        }
        if (newState == 0) {
            if (StatusBar.DEBUG) {
                Log.d("DeviceSignalCtlr", "Device disconnected");
            }
            this.mNetworkSignalView.setVisibility(8);
            this.mSignalsView.setVisibility(8);
        }
    }
}
