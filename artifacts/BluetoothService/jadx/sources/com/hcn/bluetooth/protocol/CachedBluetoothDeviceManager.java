package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CachedBluetoothDeviceManager {
    private static final boolean DEBUG = true;
    private static final String TAG = "CachedBluetoothDeviceManager";
    private final LocalBluetoothManager mBtManager;
    private final List<CachedBluetoothDevice> mCachedDevices = new ArrayList();
    private Context mContext;

    CachedBluetoothDeviceManager(Context context, LocalBluetoothManager localBluetoothManager) {
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
    }

    public synchronized Collection<CachedBluetoothDevice> getCachedDevicesCopy() {
        return new ArrayList(this.mCachedDevices);
    }

    public static boolean onDeviceDisappeared(CachedBluetoothDevice cachedBluetoothDevice) {
        cachedBluetoothDevice.setVisible(false);
        if (cachedBluetoothDevice.getBondState() == 10) {
            return DEBUG;
        }
        return false;
    }

    public void onDeviceNameUpdated(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = findDevice(bluetoothDevice);
        if (cachedBluetoothDeviceFindDevice != null) {
            cachedBluetoothDeviceFindDevice.refreshName();
        }
    }

    public void cancelBondAll() {
        Log.i(TAG, "cancelBondAll");
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (cachedBluetoothDevice != null && cachedBluetoothDevice.getBondState() == 11) {
                cachedBluetoothDevice.getDevice().cancelBondProcess();
                Log.i(TAG, "cancelBondAll cancelBondProcess device name " + cachedBluetoothDevice.getName());
            }
        }
    }

    public CachedBluetoothDevice findDevice(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "findDevice() == " + bluetoothDevice);
        Log.d(TAG, "findDevice() mCachedDevices size == " + this.mCachedDevices.size());
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            Log.d(TAG, "findDevice() mCachedDevices == " + cachedBluetoothDevice);
            if (cachedBluetoothDevice.getDevice().equals(bluetoothDevice)) {
                return cachedBluetoothDevice;
            }
        }
        return null;
    }

    public CachedBluetoothDevice findDeviceByAddr(String str) {
        Log.d(TAG, "findDeviceByAddr() == " + str);
        for (int i = 0; i < this.mCachedDevices.size(); i++) {
            if (this.mCachedDevices.get(i).getDevice().getAddress().equals(str)) {
                return this.mCachedDevices.get(i);
            }
        }
        return null;
    }

    public CachedBluetoothDevice addDevice(LocalBluetoothAdapter localBluetoothAdapter, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "addDevice device ==" + bluetoothDevice);
        CachedBluetoothDevice cachedBluetoothDevice = new CachedBluetoothDevice(this.mContext, localBluetoothAdapter, localBluetoothProfileManager, bluetoothDevice);
        synchronized (this.mCachedDevices) {
            this.mCachedDevices.add(cachedBluetoothDevice);
            this.mBtManager.getEventManager().dispatchDeviceAdded(cachedBluetoothDevice);
        }
        return cachedBluetoothDevice;
    }

    public String getName(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = findDevice(bluetoothDevice);
        if (cachedBluetoothDeviceFindDevice != null && cachedBluetoothDeviceFindDevice.getName() != null) {
            return cachedBluetoothDeviceFindDevice.getName();
        }
        String aliasName = bluetoothDevice.getAliasName();
        return aliasName != null ? aliasName : bluetoothDevice.getAddress();
    }

    public synchronized void clearNonBondedDevices() {
        for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
            if (cachedBluetoothDevice.getBondState() != 12) {
                this.mCachedDevices.remove(size);
                Log.d(TAG, "Clear NonBondedDevices : " + cachedBluetoothDevice.getBondState() + "     and device name is : " + cachedBluetoothDevice.getName());
            }
        }
    }

    public synchronized void onScanningStateChanged(boolean z) {
        if (z) {
            for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
                this.mCachedDevices.get(size).setVisible(false);
            }
        }
    }

    public synchronized void onBtClassChanged(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = findDevice(bluetoothDevice);
        if (cachedBluetoothDeviceFindDevice != null) {
            cachedBluetoothDeviceFindDevice.refreshBtClass();
        }
    }

    public synchronized void onUuidChanged(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = findDevice(bluetoothDevice);
        if (cachedBluetoothDeviceFindDevice != null) {
            cachedBluetoothDeviceFindDevice.onUuidChanged();
        }
    }

    public synchronized void onBluetoothStateChanged(int i) {
        if (i == 13) {
            for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
                CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                if (cachedBluetoothDevice.getBondState() != 12) {
                    Log.d(TAG, "Remove device for bond state : " + cachedBluetoothDevice.getBondState() + "     and device name is : " + cachedBluetoothDevice.getName());
                    cachedBluetoothDevice.setVisible(false);
                    this.mCachedDevices.remove(size);
                } else {
                    cachedBluetoothDevice.clearProfileConnectionState();
                }
            }
        }
    }

    private void log(String str) {
        Log.d(TAG, str);
    }
}
