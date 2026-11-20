package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.ArraySet;
import android.util.Log;
import com.hcn.bluetooth.api.LocalBTMusicManager;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LocalBluetoothProfileManager {
    public static final String ACTION_PROFILE_UPDATE = "com.mtk.bluetooth.common.profiles_updater";
    private static final String BLUETOOTH_PERM = "android.permission.BLUETOOTH";
    private static final boolean DEBUG = true;
    private static Object INSTANCE_LOCK = new Object();
    private static final String TAG = "Car_LocalBluetoothProfileManager";
    private A2dpProfile mA2dpProfile;
    private A2dpSinkProfile mA2dpSinkProfile;
    private final Context mContext;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private final BluetoothEventManager mEventManager;
    private HeadsetClientProfile mHeadsetClientProfile;
    private HeadsetProfile mHeadsetProfile;
    private final LocalBluetoothAdapter mLocalAdapter;
    private PanProfile mPanProfile;
    private PbapClientProfile mPbapClientProfile;
    private PbapServerProfile mPbapProfile;
    private final Map<String, LocalBluetoothProfile> mProfileNameMap = new HashMap();
    private final Collection<ServiceListener> mServiceListeners = new ArrayList();
    private final boolean mUsePbapPce = DEBUG;

    public interface ServiceListener {
        void onServiceConnected();

        void onServiceDisconnected();
    }

    LocalBluetoothProfileManager(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, BluetoothEventManager bluetoothEventManager) {
        this.mContext = context;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mEventManager = bluetoothEventManager;
        localBluetoothAdapter.setProfileManager(this);
        bluetoothEventManager.setProfileManager(this);
        ParcelUuid[] uuids = localBluetoothAdapter.getUuids();
        if (uuids != null) {
            updateLocalProfiles(uuids);
        }
        PanProfile panProfile = new PanProfile(context);
        this.mPanProfile = panProfile;
        addPanProfile(panProfile, PanProfile.NAME, "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        this.mPbapProfile = new PbapServerProfile(context);
        Log.d(TAG, "LocalBluetoothProfileManager construction complete");
    }

    void updateLocalProfiles(ParcelUuid[] parcelUuidArr) {
        if (BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.AudioSource)) {
            if (this.mA2dpProfile == null) {
                Log.d(TAG, "Adding local A2DP SRC profile");
                A2dpProfile a2dpProfile = new A2dpProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                this.mA2dpProfile = a2dpProfile;
                addProfile(a2dpProfile, "A2DP", "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mA2dpProfile != null) {
            Log.w(TAG, "Warning: A2DP profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.AudioSink)) {
            if (this.mA2dpSinkProfile == null) {
                Log.d(TAG, "Adding local A2DP Sink profile");
                A2dpSinkProfile a2dpSinkProfile = new A2dpSinkProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                this.mA2dpSinkProfile = a2dpSinkProfile;
                addProfile(a2dpSinkProfile, "A2DPSink", LocalBTMusicManager.ACTION_CONNECTION_STATE_CHANGED);
            }
        } else if (this.mA2dpSinkProfile != null) {
            Log.w(TAG, "Warning: A2DP Sink profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Handsfree_AG) || BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.HSP_AG)) {
            if (this.mHeadsetProfile == null) {
                Log.d(TAG, "Adding local HEADSET profile");
                HeadsetProfile headsetProfile = new HeadsetProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                this.mHeadsetProfile = headsetProfile;
                addProfile(headsetProfile, "HEADSET", "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mHeadsetProfile != null) {
            Log.w(TAG, "Warning: HEADSET profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Handsfree)) {
            if (this.mHeadsetClientProfile == null) {
                Log.d(TAG, "Adding local HEADSET CLIENT profile");
                HeadsetClientProfile headsetClientProfile = new HeadsetClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                this.mHeadsetClientProfile = headsetClientProfile;
                addProfile(headsetClientProfile, HeadsetClientProfile.NAME, LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
            }
        } else if (this.mHeadsetClientProfile != null) {
            Log.w(TAG, "Warning: HEADSET CLIENT profile was previously added but the UUID is now missing.");
        } else {
            Log.d(TAG, "Handsfree Uuid not found.");
        }
        if (this.mUsePbapPce) {
            if (this.mPbapClientProfile == null) {
                Log.d(TAG, "Adding local PBAP Client profile");
                PbapClientProfile pbapClientProfile = new PbapClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                this.mPbapClientProfile = pbapClientProfile;
                addProfile(pbapClientProfile, PbapClientProfile.NAME, "android.bluetooth.pbapclient.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mPbapClientProfile != null) {
            Log.w(TAG, "Warning: PBAP Client profile was previously added but the UUID is now missing.");
        }
        this.mEventManager.registerProfileIntentReceiver();
    }

    private void addProfile(LocalBluetoothProfile localBluetoothProfile, String str, String str2) {
        this.mEventManager.addProfileHandler(str2, new StateChangedHandler(localBluetoothProfile));
        this.mProfileNameMap.put(str, localBluetoothProfile);
    }

    private void addPanProfile(LocalBluetoothProfile localBluetoothProfile, String str, String str2) {
        this.mEventManager.addProfileHandler(str2, new PanStateChangedHandler(localBluetoothProfile));
        this.mProfileNameMap.put(str, localBluetoothProfile);
    }

    public LocalBluetoothProfile getProfileByName(String str) {
        return this.mProfileNameMap.get(str);
    }

    void setBluetoothStateOn() {
        ParcelUuid[] uuids = this.mLocalAdapter.getUuids();
        if (uuids != null) {
            updateLocalProfiles(uuids);
        }
        this.mEventManager.readPairedDevices();
    }

    private class StateChangedHandler implements BluetoothEventManager.Handler {
        final LocalBluetoothProfile mProfile;

        StateChangedHandler(LocalBluetoothProfile localBluetoothProfile) {
            this.mProfile = localBluetoothProfile;
        }

        @Override // com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (bluetoothDevice == null) {
                return;
            }
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = LocalBluetoothProfileManager.this.mDeviceManager.findDevice(bluetoothDevice);
            Log.d(LocalBluetoothProfileManager.TAG, "StateChangedHandler cachedDevice ==" + cachedBluetoothDeviceFindDevice);
            if (cachedBluetoothDeviceFindDevice == null) {
                Log.w(LocalBluetoothProfileManager.TAG, "StateChangedHandler found new device: " + bluetoothDevice);
                cachedBluetoothDeviceFindDevice = LocalBluetoothProfileManager.this.mDeviceManager.addDevice(LocalBluetoothProfileManager.this.mLocalAdapter, LocalBluetoothProfileManager.this, bluetoothDevice);
            }
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
            if (intExtra == 0 && intExtra2 == 1) {
                Log.i(LocalBluetoothProfileManager.TAG, "Failed to connect " + this.mProfile + " device");
            }
            cachedBluetoothDeviceFindDevice.onProfileStateChanged(this.mProfile, intExtra);
            cachedBluetoothDeviceFindDevice.refresh();
        }
    }

    private class PanStateChangedHandler extends StateChangedHandler {
        PanStateChangedHandler(LocalBluetoothProfile localBluetoothProfile) {
            super(localBluetoothProfile);
        }

        @Override // com.hcn.bluetooth.protocol.LocalBluetoothProfileManager.StateChangedHandler, com.hcn.bluetooth.protocol.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            PanProfile panProfile = (PanProfile) this.mProfile;
            int intExtra = intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0);
            panProfile.setLocalRole(bluetoothDevice, intExtra);
            Log.d(LocalBluetoothProfileManager.TAG, "pan profile state change, role is " + intExtra);
            super.onReceive(context, intent, bluetoothDevice);
        }
    }

    public void addServiceListener(ServiceListener serviceListener) {
        this.mServiceListeners.add(serviceListener);
    }

    public void removeServiceListener(ServiceListener serviceListener) {
        this.mServiceListeners.remove(serviceListener);
    }

    void callServiceConnectedListeners() {
        Iterator<ServiceListener> it = this.mServiceListeners.iterator();
        while (it.hasNext()) {
            it.next().onServiceConnected();
        }
    }

    void callServiceDisconnectedListeners() {
        Iterator<ServiceListener> it = this.mServiceListeners.iterator();
        while (it.hasNext()) {
            it.next().onServiceDisconnected();
        }
    }

    public synchronized boolean isManagerReady() {
        HeadsetProfile headsetProfile = this.mHeadsetProfile;
        if (headsetProfile != null) {
            return headsetProfile.isProfileReady();
        }
        A2dpProfile a2dpProfile = this.mA2dpProfile;
        if (a2dpProfile != null) {
            return a2dpProfile.isProfileReady();
        }
        A2dpSinkProfile a2dpSinkProfile = this.mA2dpSinkProfile;
        if (a2dpSinkProfile == null) {
            return false;
        }
        return a2dpSinkProfile.isProfileReady();
    }

    public A2dpProfile getA2dpProfile() {
        Log.d(TAG, "getA2dpProfile, mA2dpProfile = " + this.mA2dpProfile);
        return this.mA2dpProfile;
    }

    public PanProfile getPanProfile() {
        Log.d(TAG, "getPanProfile, mPanProfile = " + this.mPanProfile);
        return this.mPanProfile;
    }

    public A2dpSinkProfile getA2dpSinkProfile() {
        Log.d(TAG, "getA2dpSinkProfile, mA2dpSinkProfile = " + this.mA2dpSinkProfile);
        A2dpSinkProfile a2dpSinkProfile = this.mA2dpSinkProfile;
        if (a2dpSinkProfile == null || !a2dpSinkProfile.isProfileReady()) {
            return null;
        }
        return this.mA2dpSinkProfile;
    }

    public HeadsetProfile getHeadsetProfile() {
        Log.d(TAG, "getHeadsetProfile, mHeadsetProfile = " + this.mHeadsetProfile);
        return this.mHeadsetProfile;
    }

    public HeadsetClientProfile getHeadsetClientProfile() {
        HeadsetClientProfile headsetClientProfile = this.mHeadsetClientProfile;
        if (headsetClientProfile == null || !headsetClientProfile.isProfileReady()) {
            return null;
        }
        return this.mHeadsetClientProfile;
    }

    public PbapClientProfile getPbapClientProfile() {
        return this.mPbapClientProfile;
    }

    public PbapServerProfile getPbapProfile() {
        Log.d(TAG, "getPbapProfile, mPbapProfile = " + this.mPbapProfile);
        return this.mPbapProfile;
    }

    synchronized void updateProfiles(ParcelUuid[] parcelUuidArr, ParcelUuid[] parcelUuidArr2, Collection<LocalBluetoothProfile> collection, Collection<LocalBluetoothProfile> collection2, boolean z, BluetoothDevice bluetoothDevice) {
        collection2.clear();
        collection2.addAll(collection);
        Log.d(TAG, "Current Profiles" + collection.toString());
        collection.clear();
        if (parcelUuidArr == null) {
            Log.d(TAG, "remote device uuid is null");
            return;
        }
        if (this.mHeadsetProfile != null && ((BluetoothUuid.isUuidPresent(parcelUuidArr2, BluetoothUuid.HSP_AG) && BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.HSP)) || (BluetoothUuid.isUuidPresent(parcelUuidArr2, BluetoothUuid.Handsfree_AG) && BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Handsfree)))) {
            Log.d(TAG, "Add HeadsetProfile to connectable profile list");
            collection.add(this.mHeadsetProfile);
            collection2.remove(this.mHeadsetProfile);
        }
        if (this.mHeadsetClientProfile != null && BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.Handsfree_AG) && BluetoothUuid.isUuidPresent(parcelUuidArr2, BluetoothUuid.Handsfree)) {
            collection.add(this.mHeadsetClientProfile);
            collection2.remove(this.mHeadsetClientProfile);
        }
        if (BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpProfile.SINK_UUIDS) && this.mA2dpProfile != null) {
            Log.d(TAG, "Add A2dpProfile to connectable profile list");
            collection.add(this.mA2dpProfile);
            collection2.remove(this.mA2dpProfile);
        }
        if (BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpSinkProfile.SRC_UUIDS) && this.mA2dpSinkProfile != null) {
            Log.d(TAG, "[updateProfiles] remote device support source, add the a2dp sink profile");
            Log.d(TAG, "[updateProfiles] contains the SINK UUID");
            collection.add(this.mA2dpSinkProfile);
            collection2.remove(this.mA2dpSinkProfile);
        }
        if (z) {
            Log.d(TAG, "Valid PAN-NAP connection exists.");
        }
        if ((BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.NAP) && this.mPanProfile != null) || z) {
            collection.add(this.mPanProfile);
            collection2.remove(this.mPanProfile);
        }
        if (BluetoothUuid.isUuidPresent(parcelUuidArr, BluetoothUuid.PBAP_PSE) && this.mPbapProfile != null) {
            Log.d(TAG, "Add PbapProfile to connectable profile list");
            collection.add(this.mPbapProfile);
            collection2.remove(this.mPbapProfile);
            if (this.mUsePbapPce) {
                collection.add(this.mPbapClientProfile);
                collection2.remove(this.mPbapClientProfile);
                collection.remove(this.mPbapProfile);
                collection2.add(this.mPbapProfile);
            }
        }
        Log.d(TAG, "updateProfiles end profiles" + collection);
    }

    private void log(String str) {
        Log.v(TAG, "[BT][profile manager]" + str);
    }

    public boolean isBusy() {
        int[] iArr = {1, 3};
        HeadsetClientProfile headsetClientProfile = this.mHeadsetClientProfile;
        if (headsetClientProfile != null && !headsetClientProfile.getDevicesMatchingConnectionStates(iArr).isEmpty()) {
            return DEBUG;
        }
        A2dpSinkProfile a2dpSinkProfile = this.mA2dpSinkProfile;
        if (a2dpSinkProfile != null && !a2dpSinkProfile.getDevicesMatchingConnectionStates(iArr).isEmpty()) {
            return DEBUG;
        }
        PbapClientProfile pbapClientProfile = this.mPbapClientProfile;
        if (pbapClientProfile == null || pbapClientProfile.getDevicesMatchingConnectionStates(iArr).isEmpty()) {
            return false;
        }
        return DEBUG;
    }

    public ArraySet<BluetoothDevice> getConnectedDevices() {
        ArraySet<BluetoothDevice> arraySet = new ArraySet<>();
        int[] iArr = {2};
        HeadsetClientProfile headsetClientProfile = this.mHeadsetClientProfile;
        if (headsetClientProfile == null) {
            return arraySet;
        }
        arraySet.addAll(headsetClientProfile.getDevicesMatchingConnectionStates(iArr));
        arraySet.addAll(this.mA2dpSinkProfile.getDevicesMatchingConnectionStates(iArr));
        arraySet.addAll(this.mPbapClientProfile.getDevicesMatchingConnectionStates(iArr));
        return arraySet;
    }
}
