package com.hcn.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.bluetooth.le.ScanRecord;
import android.carsource.McuManager;
import android.os.Build;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;
import com.hcn.bluetooth.service.BluetoothHfpclientService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes.dex */
public class BleSwcDevice extends BluetoothGattCallback {
    public static final String DEVICE_NAME = "Selfie";
    private static final String TAG = "BleSwcDevice";
    public static final UUID HID_SERVICE_UUID = UUID.fromString("00001812-0000-1000-8000-00805f9b34fb");
    public static final UUID HID_READ_MAP_UUID = UUID.fromString("00002a4b-0000-1000-8000-00805f9b34fb");
    public static final UUID SERVICE_UUID = UUID.fromString("0000ae30-0000-1000-8000-00805f9b34fb");
    public static final UUID CHAR_NOTIFY_UUID = UUID.fromString("0000ae02-0000-1000-8000-00805f9b34fb");
    public static final UUID DESC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private int mKeycode = -1;
    private final BleSwcHandler mKeyHandler = new BleSwcHandler(Looper.getMainLooper(), new KeyActionListenerImp());

    public static boolean isMatchDevice(BluetoothDevice bluetoothDevice, ScanRecord scanRecord) {
        if (!DEVICE_NAME.equals(bluetoothDevice.getName())) {
            return false;
        }
        List<ParcelUuid> serviceUuids = scanRecord.getServiceUuids();
        if (Build.VERSION.SDK_INT < 24 || serviceUuids == null) {
            return false;
        }
        Stream<R> map = serviceUuids.stream().map(new Function() { // from class: com.hcn.bluetooth.le.BleSwcDevice$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ParcelUuid) obj).getUuid();
            }
        });
        final UUID uuid = HID_SERVICE_UUID;
        Objects.requireNonNull(uuid);
        return map.anyMatch(new Predicate() { // from class: com.hcn.bluetooth.le.BleSwcDevice$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return uuid.equals((UUID) obj);
            }
        });
    }

    public void release() {
        this.mKeyHandler.removeCallbacksAndMessages(null);
    }

    private void initReadMapCharacteristic(BluetoothGatt bluetoothGatt, BluetoothGattService bluetoothGattService) {
        UUID uuid = HID_READ_MAP_UUID;
        BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(uuid);
        if (characteristic == null) {
            Log.d(TAG, "initReadMapCharacteristic no uuid:" + uuid);
        } else if ((characteristic.getProperties() & 2) == 0) {
            Log.e(TAG, "initReadMapCharacteristic not support read!");
        } else {
            bluetoothGatt.readCharacteristic(characteristic);
        }
    }

    private void initNotifyCharacteristic(BluetoothGatt bluetoothGatt, BluetoothGattService bluetoothGattService) {
        byte[] bArr;
        UUID uuid = CHAR_NOTIFY_UUID;
        BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(uuid);
        if (characteristic == null) {
            Log.e(TAG, "initNotifyCharacteristic no uuid:" + uuid);
            return;
        }
        int properties = characteristic.getProperties();
        bluetoothGatt.setCharacteristicNotification(characteristic, true);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(DESC_CONFIG_UUID);
        if (descriptor == null) {
            Log.e(TAG, "initNotifyCharacteristic not support notify!");
            return;
        }
        if ((properties & 16) != 0) {
            bArr = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        } else {
            bArr = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        }
        descriptor.setValue(bArr);
        bluetoothGatt.writeDescriptor(descriptor);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
        Log.d(TAG, "onConnectionStateChange newState=" + i2 + " status=" + i);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
        if (i == 0) {
            BluetoothGattService service = bluetoothGatt.getService(HID_SERVICE_UUID);
            if (service == null) {
                Log.e(TAG, "onServicesDiscovered no HID_SERVICE_UUID!!!");
                return;
            } else {
                initReadMapCharacteristic(bluetoothGatt, service);
                return;
            }
        }
        Log.e(TAG, "onServicesDiscovered failed，status=" + i);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        int i;
        byte[] value = bluetoothGattCharacteristic.getValue();
        if (value == null || value.length < 3) {
            return;
        }
        byte b = value[0];
        if (b != 0) {
            if (b == 1) {
                this.mKeycode = 87;
            } else if (b == 2) {
                this.mKeycode = 85;
            } else if (b == 4) {
                this.mKeycode = 25;
            } else if (b == 16) {
                this.mKeycode = 24;
            } else if (b != 32) {
                return;
            } else {
                this.mKeycode = 88;
            }
            i = 0;
        } else {
            i = 1;
        }
        int i2 = this.mKeycode;
        this.mKeyHandler.onDeviceInputEvent(i2, i, i2 == 24 || i2 == 25);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        if (i == 0) {
            Log.d(TAG, "onDescriptorWrite:" + bluetoothGattDescriptor.getUuid());
        } else {
            Log.e(TAG, "onDescriptorWrite failed，status=" + i);
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        if (i == 0) {
            Log.d(TAG, "onDescriptorRead:" + bluetoothGattDescriptor.getUuid());
        } else {
            Log.e(TAG, "onDescriptorRead failed， status=" + i);
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        if (i == 0) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            Log.d(TAG, "onCharacteristicRead data len=" + (value == null ? 0 : value.length));
            BluetoothGattService service = bluetoothGatt.getService(SERVICE_UUID);
            if (service != null) {
                initNotifyCharacteristic(bluetoothGatt, service);
                return;
            } else {
                Log.e(TAG, "onCharacteristicRead no SERVICE_UUID!!!");
                return;
            }
        }
        Log.e(TAG, "onCharacteristicRead failed，status=" + i);
    }

    private static class KeyActionListenerImp implements BleSwcHandler.OnSWCKeyActionListener {
        private KeyActionListenerImp() {
        }

        @Override // com.hcn.bluetooth.le.BleSwcHandler.OnSWCKeyActionListener
        public void onKeyPress(int i) {
            if (i == 24 || i == 25) {
                McuManager.getsInstance().injectKeyEventTimeout(i, 20);
            }
        }

        @Override // com.hcn.bluetooth.le.BleSwcHandler.OnSWCKeyActionListener
        public void onKeyLongPress(int i) {
            if (i == 24 || i == 25) {
                McuManager.getsInstance().injectKeyEventTimeout(i, 20);
            }
        }

        @Override // com.hcn.bluetooth.le.BleSwcHandler.OnSWCKeyActionListener
        public void onKeyRelease(int i, boolean z) {
            if (i == 24 || i == 25 || z) {
                return;
            }
            if (i == 87) {
                List<BluetoothHeadsetClientCall> currentCalls = BluetoothHfpclientService.getInstance().getCurrentCalls();
                if (currentCalls != null && !currentCalls.isEmpty()) {
                    McuManager.getsInstance().injectKeyEventTimeout(307, 20);
                    return;
                } else {
                    McuManager.getsInstance().injectKeyEventTimeout(i, 20);
                    return;
                }
            }
            if (i == 88) {
                List<BluetoothHeadsetClientCall> currentCalls2 = BluetoothHfpclientService.getInstance().getCurrentCalls();
                if (currentCalls2 != null && !currentCalls2.isEmpty()) {
                    McuManager.getsInstance().injectKeyEventTimeout(302, 20);
                    return;
                } else {
                    McuManager.getsInstance().injectKeyEventTimeout(i, 20);
                    return;
                }
            }
            McuManager.getsInstance().injectKeyEventTimeout(i, 20);
        }
    }
}
