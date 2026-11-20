package com.hcn.bluetooth.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Log;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BleDeviceHelper {
    private static final int BLE_SCAN_INTERVAL = 15000;
    private static final int BLE_SCAN_TIMEOUT = 2000;
    private static final int MSG_SCAN_TIMEOUT = 3;
    private static final int MSG_START_SCAN = 1;
    private static final int MSG_STOP_SCAN = 2;
    private static final String TAG = "BleDeviceHelper";
    public static String supportDevice = SystemProperties.get("persist.sys.ble.device", "");
    private final BluetoothAdapter mAdapter;
    private BluetoothLeScanner mBleScanner;
    private final BleSwcDevice mBleSwcDevice;
    private BluetoothGatt mBluetoothGatt;
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.hcn.bluetooth.le.BleDeviceHelper.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                BleDeviceHelper.this.startBleScanWithTimeout();
                return;
            }
            if (i == 2) {
                removeMessages(1);
                removeMessages(3);
                BleDeviceHelper.this.stopBleScan();
            } else {
                if (i != 3) {
                    return;
                }
                BleDeviceHelper.this.stopBleScan();
                sendEmptyMessageDelayed(1, 15000L);
            }
        }
    };
    private final ScanCallback scanCallback = new ScanCallback() { // from class: com.hcn.bluetooth.le.BleDeviceHelper.2
        @Override // android.bluetooth.le.ScanCallback
        public void onScanResult(int i, ScanResult scanResult) {
            super.onScanResult(i, scanResult);
            BluetoothDevice device = scanResult.getDevice();
            ScanRecord scanRecord = scanResult.getScanRecord();
            if (device == null || scanRecord == null || BleDeviceHelper.this.mBluetoothGatt != null || !BleSwcDevice.isMatchDevice(device, scanRecord)) {
                return;
            }
            BleDeviceHelper.this.mHandler.sendEmptyMessage(2);
            BleDeviceHelper.this.connectGatt(device);
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanFailed(int i) {
            super.onScanFailed(i);
            Log.e(BleDeviceHelper.TAG, "onScanFailed errorCode=" + i);
        }
    };
    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() { // from class: com.hcn.bluetooth.le.BleDeviceHelper.3
        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            super.onConnectionStateChange(bluetoothGatt, i, i2);
            Log.d(BleDeviceHelper.TAG, "onConnectionStateChange newState=" + i2 + " status=" + i);
            if (i2 == 2 && i == 0) {
                bluetoothGatt.discoverServices();
            } else if (i2 == 0) {
                bluetoothGatt.close();
                BleDeviceHelper.this.mBluetoothGatt = null;
                BleDeviceHelper.this.mHandler.sendEmptyMessage(1);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            super.onServicesDiscovered(bluetoothGatt, i);
            BleDeviceHelper.this.mBleSwcDevice.onServicesDiscovered(bluetoothGatt, i);
            if (i != 0) {
                BleDeviceHelper.this.disconnectGatt();
                BleDeviceHelper.this.mHandler.sendEmptyMessage(1);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
            BleDeviceHelper.this.mBleSwcDevice.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
            BleDeviceHelper.this.mBleSwcDevice.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
            BleDeviceHelper.this.mBleSwcDevice.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            BleDeviceHelper.this.mBleSwcDevice.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
        }
    };

    public BleDeviceHelper(Context context) {
        this.mContext = context;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mAdapter = defaultAdapter;
        this.mBleScanner = defaultAdapter.getBluetoothLeScanner();
        this.mBleSwcDevice = new BleSwcDevice();
    }

    public void handleAdapterStateChange(int i) {
        if (i == 10) {
            this.mHandler.removeCallbacksAndMessages(null);
        } else {
            if (i != 12) {
                return;
            }
            this.mHandler.sendEmptyMessage(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBleScanWithTimeout() {
        if (this.mBleScanner == null) {
            this.mBleScanner = this.mAdapter.getBluetoothLeScanner();
        }
        this.mBleScanner.startScan((List<ScanFilter>) null, new ScanSettings.Builder().setScanMode(1).build(), this.scanCallback);
        this.mHandler.sendEmptyMessageDelayed(3, 2000L);
        Log.d(TAG, "startBleScanWithTimeout...");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopBleScan() {
        BluetoothLeScanner bluetoothLeScanner = this.mBleScanner;
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.scanCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectGatt(BluetoothDevice bluetoothDevice) {
        this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, false, this.gattCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectGatt() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mBleSwcDevice.release();
    }
}
