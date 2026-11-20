package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class A2dpRoleSwitcher {
    public static final int CONFIRM_CONNECT_ACCEPT = 1;
    public static final int CONFIRM_CONNECT_REJECT = 0;
    public static final int CONNECT_ACTION = 100;
    public static final int CONNECT_CONFIRM_ACTION = 101;
    private static final String CONNECT_OR_CONFIRM_ACTION = "action";
    private static final String DEVICE_ADDRESS = "device_address_to_connect";
    private static final String DEVICE_ADDRESS_DISCONNECT = "device_address_to_disconnect";
    private static final String REQUEST_ROLE = "request_role";
    private static final String REQUEST_ROLE_SINK = "sink";
    private static final String REQUEST_ROLE_SOURCE = "source";
    private static final String TAG = "A2dpRoleSwitcher";
    private static A2dpRoleSwitcher sInstance;
    private A2dpSinkProfile mA2dpSinkProfile;
    private A2dpProfile mA2dpSourceProfile;
    private int mConnectOrConfirm = -1;
    private Context mContext;
    private CachedBluetoothDeviceManager mDeviceManager;
    private List<BluetoothDevice> mDisconnectDevices;
    private LocalBluetoothProfile mDisconnectProfile;
    private LocalBluetoothProfileManager mProfileManager;
    private List<BluetoothDevice> mReturnDisconnectDevices;
    private BluetoothDevice mWaitToConnectDevice;
    private LocalBluetoothProfile mWaitToConnectProfile;

    private A2dpRoleSwitcher(Context context) {
        this.mContext = context;
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
        this.mProfileManager = localBluetoothManager.getProfileManager();
        this.mDeviceManager = localBluetoothManager.getCachedDeviceManager();
        this.mA2dpSourceProfile = this.mProfileManager.getA2dpProfile();
        this.mA2dpSinkProfile = this.mProfileManager.getA2dpSinkProfile();
    }

    public static A2dpRoleSwitcher getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is NULL");
        }
        if (sInstance == null) {
            sInstance = new A2dpRoleSwitcher(context);
        }
        return sInstance;
    }

    public void processA2dpConnect(LocalBluetoothProfile localBluetoothProfile, BluetoothDevice bluetoothDevice) {
        if (localBluetoothProfile == null || bluetoothDevice == null) {
            Log.d(TAG, "Invalid paramater, profile : " + localBluetoothProfile + " device : " + bluetoothDevice);
            return;
        }
        Log.d(TAG, "processA2dpConnect() profile : " + localBluetoothProfile.toString() + ", device : " + bluetoothDevice.getAddress());
        List<BluetoothDevice> connectedDevices = getConnectedDevices(localBluetoothProfile);
        if (connectedDevices == null || connectedDevices.size() == 0) {
            Log.d(TAG, "profile : " + localBluetoothProfile.toString() + " connected device is 0 , connect directly");
            localBluetoothProfile.connect(bluetoothDevice);
        } else {
            Log.d(TAG, "profile : " + localBluetoothProfile.toString() + " connected device size : " + connectedDevices.size());
            showA2dpRoleChangeConfirm(localBluetoothProfile, getNeedToDisconnectDevices().get(0), bluetoothDevice);
        }
    }

    private void showA2dpRoleChangeConfirm(LocalBluetoothProfile localBluetoothProfile, BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2) {
        new Thread(new Runnable() { // from class: com.hcn.bluetooth.protocol.A2dpRoleSwitcher.1
            @Override // java.lang.Runnable
            public void run() {
            }
        }).start();
    }

    public void confirmConnect(int i, LocalBluetoothProfile localBluetoothProfile, BluetoothDevice bluetoothDevice, int i2) {
        Log.d(TAG, "acceptOrReject : " + i + ", connectOrConfirm : " + i2);
        Log.d(TAG, "profileToConnect : " + localBluetoothProfile.toString() + ", deviceToConnect : " + bluetoothDevice.getAddress());
        if (i != 1) {
            if (i == 0) {
                if (i2 == 100) {
                    Log.d(TAG, "[confirmConnect] connect reject");
                    return;
                }
                if (i2 == 101) {
                    Log.d(TAG, "[confirmConnect] connect reject, send the confirm reject");
                    if (localBluetoothProfile instanceof A2dpProfile) {
                        ((A2dpProfile) localBluetoothProfile).connectConfirm(bluetoothDevice, 0);
                        return;
                    } else {
                        if (localBluetoothProfile instanceof A2dpSinkProfile) {
                            ((A2dpSinkProfile) localBluetoothProfile).connectConfirm(bluetoothDevice, 0);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            return;
        }
        Log.d(TAG, "Connect accept, disconnect the connected device firstly");
        this.mConnectOrConfirm = i2;
        List<BluetoothDevice> connectedDevices = getConnectedDevices(localBluetoothProfile);
        if (connectedDevices == null || connectedDevices.size() == 0) {
            Log.d(TAG, "[confirmConnect] devices is null or empty");
            localBluetoothProfile.connect(bluetoothDevice);
            return;
        }
        this.mWaitToConnectDevice = bluetoothDevice;
        this.mWaitToConnectProfile = localBluetoothProfile;
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = this.mDeviceManager.findDevice(bluetoothDevice);
        if (cachedBluetoothDeviceFindDevice == null) {
            cachedBluetoothDeviceFindDevice = this.mDeviceManager.addDevice(LocalBluetoothAdapter.getInstance(), this.mProfileManager, this.mWaitToConnectDevice);
        }
        cachedBluetoothDeviceFindDevice.onProfileStateChanged(this.mWaitToConnectProfile, 1);
        ArrayList<BluetoothDevice> arrayList = new ArrayList(connectedDevices);
        this.mDisconnectDevices = arrayList;
        for (BluetoothDevice bluetoothDevice2 : arrayList) {
            Log.d(TAG, "[Disconnect] profile : " + this.mDisconnectProfile.toString() + " device : " + bluetoothDevice2);
            this.mDisconnectProfile.disconnect(bluetoothDevice2);
        }
    }

    public List<BluetoothDevice> getConnectedDevices(LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile == null) {
            throw new IllegalArgumentException("profile is null");
        }
        Log.d(TAG, "[getConnectedDevices] profile : " + localBluetoothProfile.toString());
        if (localBluetoothProfile instanceof A2dpSinkProfile) {
            List<BluetoothDevice> connectedDevices = this.mA2dpSourceProfile.getConnectedDevices();
            this.mDisconnectProfile = this.mA2dpSourceProfile;
            this.mReturnDisconnectDevices = new ArrayList(connectedDevices);
            Log.d(TAG, "source connected devices size : " + connectedDevices.size());
            return connectedDevices;
        }
        if (localBluetoothProfile instanceof A2dpProfile) {
            List<BluetoothDevice> connectedDevices2 = this.mA2dpSinkProfile.getConnectedDevices();
            this.mDisconnectProfile = this.mA2dpSinkProfile;
            this.mReturnDisconnectDevices = new ArrayList(connectedDevices2);
            Log.d(TAG, "sink connected devices size : " + connectedDevices2.size());
            return connectedDevices2;
        }
        Log.e(TAG, "[getConnectedDevices] not A2dp Sink & Source Profile");
        return null;
    }

    List<BluetoothDevice> getNeedToDisconnectDevices() {
        return new ArrayList(this.mReturnDisconnectDevices);
    }

    public void changeProfileState(LocalBluetoothProfile localBluetoothProfile, BluetoothDevice bluetoothDevice, int i) {
        List<BluetoothDevice> list;
        Log.d(TAG, "[changeProfileState] profile : " + localBluetoothProfile.toString() + ", device : " + bluetoothDevice.getAddress() + ", state : " + i);
        if (i != 0 || (list = this.mDisconnectDevices) == null) {
            return;
        }
        if (list.contains(bluetoothDevice)) {
            this.mDisconnectDevices.remove(bluetoothDevice);
        }
        if (this.mDisconnectDevices.size() == 0) {
            int i2 = this.mConnectOrConfirm;
            if (i2 == 100) {
                this.mWaitToConnectProfile.connect(this.mWaitToConnectDevice);
            } else if (i2 == 101) {
                LocalBluetoothProfile localBluetoothProfile2 = this.mWaitToConnectProfile;
                if (localBluetoothProfile2 instanceof A2dpProfile) {
                    ((A2dpProfile) localBluetoothProfile2).connectConfirm(this.mWaitToConnectDevice, 1);
                } else if (localBluetoothProfile2 instanceof A2dpSinkProfile) {
                    ((A2dpSinkProfile) localBluetoothProfile2).connectConfirm(this.mWaitToConnectDevice, 1);
                }
            }
            this.mDisconnectDevices.clear();
            this.mDisconnectDevices = null;
            this.mWaitToConnectDevice = null;
            this.mWaitToConnectProfile = null;
        }
    }
}
