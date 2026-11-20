package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;
import com.hcn.bluetoothservice.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class BluetoothEventManager {
    private static final String TAG = "BluetoothEventManager";
    private final IntentFilter mAdapterIntentFilter;
    private final BroadcastReceiver mBroadcastReceiver;
    private final Collection<BluetoothCallback> mCallbacks = new ArrayList();
    private Context mContext;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private final Map<String, Handler> mHandlerMap;
    private final LocalBluetoothAdapter mLocalAdapter;
    private final IntentFilter mProfileIntentFilter;
    private LocalBluetoothProfileManager mProfileManager;
    private android.os.Handler mReceiverHandler;

    interface Handler {
        void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    private void addHandler(String str, Handler handler) {
        this.mHandlerMap.put(str, handler);
        this.mAdapterIntentFilter.addAction(str);
    }

    void addProfileHandler(String str, Handler handler) {
        this.mHandlerMap.put(str, handler);
        this.mProfileIntentFilter.addAction(str);
    }

    void setProfileManager(LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mProfileManager = localBluetoothProfileManager;
    }

    BluetoothEventManager(LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, Context context) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.hcn.bluetooth.protocol.BluetoothEventManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Log.v(BluetoothEventManager.TAG, "Received " + intent.getAction());
                String action = intent.getAction();
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                Handler handler = (Handler) BluetoothEventManager.this.mHandlerMap.get(action);
                if (handler != null) {
                    handler.onReceive(context2, intent, bluetoothDevice);
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        IntentFilter intentFilter = new IntentFilter();
        this.mAdapterIntentFilter = intentFilter;
        this.mProfileIntentFilter = new IntentFilter();
        this.mHandlerMap = new HashMap();
        this.mContext = context;
        addHandler("android.bluetooth.adapter.action.STATE_CHANGED", new AdapterStateChangedHandler());
        addHandler("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED", new ConnectionStateChangedHandler());
        addHandler("android.bluetooth.adapter.action.DISCOVERY_STARTED", new ScanningStateChangedHandler(true));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_FINISHED", new ScanningStateChangedHandler(false));
        addHandler("android.bluetooth.device.action.FOUND", new DeviceFoundHandler());
        addHandler("android.bluetooth.device.action.DISAPPEARED", new DeviceDisappearedHandler());
        addHandler("android.bluetooth.device.action.NAME_CHANGED", new NameChangedHandler());
        addHandler("android.bluetooth.device.action.ALIAS_CHANGED", new NameChangedHandler());
        addHandler("android.bluetooth.device.action.BOND_STATE_CHANGED", new BondStateChangedHandler());
        addHandler("android.bluetooth.device.action.CLASS_CHANGED", new ClassChangedHandler());
        addHandler("android.bluetooth.device.action.UUID", new UuidChangedHandler());
        addHandler("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED", new BatteryLevelChangedHandler());
        addHandler("android.intent.action.DOCK_EVENT", new DockEventHandler());
        this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mReceiverHandler);
    }

    void registerProfileIntentReceiver() {
        this.mContext.registerReceiver(this.mBroadcastReceiver, this.mProfileIntentFilter, null, this.mReceiverHandler);
    }

    public void setReceiverHandler(android.os.Handler handler) {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mReceiverHandler = handler;
        this.mContext.registerReceiver(this.mBroadcastReceiver, this.mAdapterIntentFilter, null, handler);
        registerProfileIntentReceiver();
    }

    public void registerCallback(BluetoothCallback bluetoothCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.add(bluetoothCallback);
        }
    }

    public void unregisterCallback(BluetoothCallback bluetoothCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(bluetoothCallback);
        }
    }

    private class AdapterStateChangedHandler implements Handler {
        private AdapterStateChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            BluetoothEventManager.this.mLocalAdapter.setBluetoothStateInt(intExtra);
            synchronized (BluetoothEventManager.this.mCallbacks) {
                Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((BluetoothCallback) it.next()).onBluetoothStateChanged(intExtra);
                }
            }
            BluetoothEventManager.this.mDeviceManager.onBluetoothStateChanged(intExtra);
        }
    }

    private class ScanningStateChangedHandler implements Handler {
        private final boolean mStarted;

        ScanningStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            synchronized (BluetoothEventManager.this.mCallbacks) {
                Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((BluetoothCallback) it.next()).onScanningStateChanged(this.mStarted);
                }
            }
            Log.d(BluetoothEventManager.TAG, "scanning state change to " + this.mStarted);
            BluetoothEventManager.this.mDeviceManager.onScanningStateChanged(this.mStarted);
        }
    }

    private class DeviceFoundHandler implements Handler {
        private DeviceFoundHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", ShortCompanionObject.MIN_VALUE);
            BluetoothClass bluetoothClass = (BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS");
            String stringExtra = intent.getStringExtra("android.bluetooth.device.extra.NAME");
            Log.d(BluetoothEventManager.TAG, "Device " + stringExtra + " ,Class: " + (bluetoothClass != null ? Integer.valueOf(bluetoothClass.getMajorDeviceClass()) : null));
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (cachedBluetoothDeviceFindDevice == null) {
                cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.addDevice(BluetoothEventManager.this.mLocalAdapter, BluetoothEventManager.this.mProfileManager, bluetoothDevice);
                Log.d(BluetoothEventManager.TAG, "DeviceFoundHandler created new CachedBluetoothDevice: " + cachedBluetoothDeviceFindDevice);
            }
            cachedBluetoothDeviceFindDevice.setRssi(shortExtra);
            cachedBluetoothDeviceFindDevice.setBtClass(bluetoothClass);
            cachedBluetoothDeviceFindDevice.setNewName(stringExtra);
            cachedBluetoothDeviceFindDevice.setVisible(true);
        }
    }

    private class ConnectionStateChangedHandler implements Handler {
        private ConnectionStateChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.dispatchConnectionStateChanged(BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice), intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", Integer.MIN_VALUE));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        synchronized (this.mCallbacks) {
            Iterator<BluetoothCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onConnectionStateChanged(cachedBluetoothDevice, i);
            }
        }
    }

    void dispatchDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        synchronized (this.mCallbacks) {
            Iterator<BluetoothCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onDeviceAdded(cachedBluetoothDevice);
            }
        }
    }

    private class DeviceDisappearedHandler implements Handler {
        private DeviceDisappearedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (cachedBluetoothDeviceFindDevice == null) {
                Log.w(BluetoothEventManager.TAG, "received ACTION_DISAPPEARED for an unknown device: " + bluetoothDevice);
                return;
            }
            if (CachedBluetoothDeviceManager.onDeviceDisappeared(cachedBluetoothDeviceFindDevice)) {
                synchronized (BluetoothEventManager.this.mCallbacks) {
                    Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((BluetoothCallback) it.next()).onDeviceDeleted(cachedBluetoothDeviceFindDevice);
                    }
                }
            }
        }
    }

    private class NameChangedHandler implements Handler {
        private NameChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.mDeviceManager.onDeviceNameUpdated(bluetoothDevice);
        }
    }

    private class BondStateChangedHandler implements Handler {
        private BondStateChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (bluetoothDevice == null) {
                Log.e(BluetoothEventManager.TAG, "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (cachedBluetoothDeviceFindDevice == null) {
                Log.w(BluetoothEventManager.TAG, "CachedBluetoothDevice for device " + bluetoothDevice + " not found, calling readPairedDevices().");
                if (BluetoothEventManager.this.readPairedDevices()) {
                    Log.e(BluetoothEventManager.TAG, "Got bonding state changed for " + bluetoothDevice + ", and we have record of that device.");
                    cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
                }
                if (cachedBluetoothDeviceFindDevice == null) {
                    Log.w(BluetoothEventManager.TAG, "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                    cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.addDevice(BluetoothEventManager.this.mLocalAdapter, BluetoothEventManager.this.mProfileManager, bluetoothDevice);
                    BluetoothEventManager.this.dispatchDeviceAdded(cachedBluetoothDeviceFindDevice);
                }
            }
            synchronized (BluetoothEventManager.this.mCallbacks) {
                Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((BluetoothCallback) it.next()).onDeviceBondStateChanged(cachedBluetoothDeviceFindDevice, intExtra);
                }
            }
            cachedBluetoothDeviceFindDevice.onBondingStateChanged(intExtra);
            if (intExtra == 10) {
                Log.d(BluetoothEventManager.TAG, cachedBluetoothDeviceFindDevice.getName() + " show unbond message for " + intent.getIntExtra("android.bluetooth.device.extra.REASON", Integer.MIN_VALUE));
            }
        }

        private void showUnbondMessage(Context context, String str, int i) {
            int i2;
            switch (i) {
                case 1:
                    i2 = R.string.bluetooth_pairing_pin_error_message;
                    break;
                case 2:
                    i2 = R.string.bluetooth_pairing_rejected_error_message;
                    break;
                case 3:
                default:
                    Log.w(BluetoothEventManager.TAG, "showUnbondMessage: Not displaying any message for reason: " + i);
                    return;
                case 4:
                    i2 = R.string.bluetooth_pairing_device_down_error_message;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    i2 = R.string.bluetooth_pairing_error_message;
                    break;
            }
            showError(context, str, i2);
        }

        public void showError(Context context, String str, int i) {
            Toast.makeText(context, context.getString(i, str), 0).show();
        }
    }

    private class ClassChangedHandler implements Handler {
        private ClassChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.mDeviceManager.onBtClassChanged(bluetoothDevice);
        }
    }

    private class UuidChangedHandler implements Handler {
        private UuidChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.mDeviceManager.onUuidChanged(bluetoothDevice);
        }
    }

    private class DockEventHandler implements Handler {
        private DockEventHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice;
            if (intent.getIntExtra("android.intent.extra.DOCK_STATE", 1) != 0 || bluetoothDevice == null || bluetoothDevice.getBondState() != 10 || (cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice)) == null) {
                return;
            }
            cachedBluetoothDeviceFindDevice.setVisible(false);
        }
    }

    private class BatteryLevelChangedHandler implements Handler {
        private BatteryLevelChangedHandler() {
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (cachedBluetoothDeviceFindDevice != null) {
                cachedBluetoothDeviceFindDevice.refresh();
            }
        }
    }

    boolean readPairedDevices() {
        Set<BluetoothDevice> bondedDevices = this.mLocalAdapter.getBondedDevices();
        boolean z = false;
        if (bondedDevices == null) {
            return false;
        }
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (this.mDeviceManager.findDevice(bluetoothDevice) == null) {
                dispatchDeviceAdded(this.mDeviceManager.addDevice(this.mLocalAdapter, this.mProfileManager, bluetoothDevice));
                z = true;
            }
        }
        return z;
    }
}
