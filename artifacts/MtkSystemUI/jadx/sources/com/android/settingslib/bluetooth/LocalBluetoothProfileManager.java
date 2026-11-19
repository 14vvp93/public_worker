package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LocalBluetoothProfileManager {
    private A2dpProfile mA2dpProfile;
    private A2dpSinkProfile mA2dpSinkProfile;
    private final Context mContext;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private final BluetoothEventManager mEventManager;
    private HeadsetProfile mHeadsetProfile;
    private HearingAidProfile mHearingAidProfile;
    private HfpClientProfile mHfpClientProfile;
    private HidDeviceProfile mHidDeviceProfile;
    private HidProfile mHidProfile;
    private final LocalBluetoothAdapter mLocalAdapter;
    private MapClientProfile mMapClientProfile;
    private MapProfile mMapProfile;
    private OppProfile mOppProfile;
    private PanProfile mPanProfile;
    private PbapClientProfile mPbapClientProfile;
    private PbapServerProfile mPbapProfile;
    private final Map<String, LocalBluetoothProfile> mProfileNameMap = new HashMap();
    private final Collection<ServiceListener> mServiceListeners = new ArrayList();
    private final boolean mUseMapClient;
    private final boolean mUsePbapPce;

    public interface ServiceListener {
        void onServiceConnected();

        void onServiceDisconnected();
    }

    LocalBluetoothProfileManager(Context context, LocalBluetoothAdapter adapter, CachedBluetoothDeviceManager deviceManager, BluetoothEventManager eventManager) {
        this.mContext = context;
        this.mLocalAdapter = adapter;
        this.mDeviceManager = deviceManager;
        this.mEventManager = eventManager;
        this.mUsePbapPce = this.mContext.getResources().getBoolean(R.^attr-private.searchDialogTheme);
        this.mUseMapClient = this.mContext.getResources().getBoolean(R.^attr-private.searchDialogTheme);
        this.mLocalAdapter.setProfileManager(this);
        this.mEventManager.setProfileManager(this);
        ParcelUuid[] uuids = adapter.getUuids();
        if (uuids != null) {
            Log.d("LocalBluetoothProfileManager", "bluetooth adapter uuid: ");
            for (ParcelUuid uuid : uuids) {
                Log.d("LocalBluetoothProfileManager", "  " + uuid);
            }
            updateLocalProfiles(uuids);
        }
        if (this.mHidProfile == null) {
            this.mHidProfile = new HidProfile(context, this.mLocalAdapter, this.mDeviceManager, this);
            addProfile(this.mHidProfile, "HID", "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mPanProfile == null) {
            this.mPanProfile = new PanProfile(context, this.mLocalAdapter);
            addPanProfile(this.mPanProfile, "PAN", "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mHidDeviceProfile == null) {
            this.mHidDeviceProfile = new HidDeviceProfile(context, this.mLocalAdapter, this.mDeviceManager, this);
            addProfile(this.mHidDeviceProfile, "HID DEVICE", "android.bluetooth.hiddevice.profile.action.CONNECTION_STATE_CHANGED");
        }
        Log.d("LocalBluetoothProfileManager", "Adding local MAP profile");
        if (this.mUseMapClient) {
            if (this.mMapClientProfile == null) {
                this.mMapClientProfile = new MapClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mMapClientProfile, "MAP Client", "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mMapClientProfile == null) {
            this.mMapProfile = new MapProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
            addProfile(this.mMapProfile, "MAP", "android.bluetooth.map.profile.action.CONNECTION_STATE_CHANGED");
        }
        Log.d("LocalBluetoothProfileManager", "Adding local PBAP profile");
        if (this.mPbapProfile == null) {
            this.mPbapProfile = new PbapServerProfile(context);
            addProfile(this.mPbapProfile, PbapServerProfile.NAME, "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED");
        }
        List<Integer> supportedList = this.mLocalAdapter.getSupportedProfiles();
        if (supportedList.contains(21) && this.mHearingAidProfile == null) {
            this.mHearingAidProfile = new HearingAidProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
            addProfile(this.mHearingAidProfile, "HearingAid", "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
        }
        Log.d("LocalBluetoothProfileManager", "LocalBluetoothProfileManager construction complete");
    }

    void updateLocalProfiles(ParcelUuid[] uuids) {
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.AudioSource)) {
            if (this.mA2dpProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local A2DP SRC profile");
                this.mA2dpProfile = new A2dpProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mA2dpProfile, "A2DP", "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mA2dpProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: A2DP profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.AudioSink)) {
            if (this.mA2dpSinkProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local A2DP Sink profile");
                this.mA2dpSinkProfile = new A2dpSinkProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mA2dpSinkProfile, "A2DPSink", "android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mA2dpSinkProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: A2DP Sink profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Handsfree_AG) || BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.HSP_AG)) {
            if (this.mHeadsetProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local HEADSET profile");
                this.mHeadsetProfile = new HeadsetProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addHeadsetProfile(this.mHeadsetProfile, "HEADSET", "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED", "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED", 10);
            }
        } else if (this.mHeadsetProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: HEADSET profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Handsfree)) {
            if (this.mHfpClientProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local HfpClient profile");
                this.mHfpClientProfile = new HfpClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addHeadsetProfile(this.mHfpClientProfile, "HEADSET_CLIENT", "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED", "android.bluetooth.headsetclient.profile.action.AUDIO_STATE_CHANGED", 0);
            }
        } else if (this.mHfpClientProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: Hfp Client profile was previously added but the UUID is now missing.");
        } else {
            Log.d("LocalBluetoothProfileManager", "Handsfree Uuid not found.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.MNS)) {
            if (this.mMapClientProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local Map Client profile");
                this.mMapClientProfile = new MapClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mMapClientProfile, "MAP Client", "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mMapClientProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: MAP Client profile was previously added but the UUID is now missing.");
        } else {
            Log.d("LocalBluetoothProfileManager", "MAP Client Uuid not found.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.ObexObjectPush)) {
            if (this.mOppProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local OPP profile");
                this.mOppProfile = new OppProfile();
                this.mProfileNameMap.put("OPP", this.mOppProfile);
            }
        } else if (this.mOppProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: OPP profile was previously added but the UUID is now missing.");
        }
        if (this.mUsePbapPce) {
            if (this.mPbapClientProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local PBAP Client profile");
                this.mPbapClientProfile = new PbapClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mPbapClientProfile, "PbapClient", "android.bluetooth.pbapclient.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mPbapClientProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: PBAP Client profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.HearingAid)) {
            if (this.mHearingAidProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local Hearing Aid profile");
                this.mHearingAidProfile = new HearingAidProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mHearingAidProfile, "HearingAid", "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mHearingAidProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: Hearing Aid profile was previously added but the UUID is now missing.");
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Hid) || BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Hogp)) {
            if (this.mHidProfile == null) {
                Log.d("LocalBluetoothProfileManager", "Adding local Hid profile");
                this.mHidProfile = new HidProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mHidProfile, "HID", "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
            }
        } else if (this.mHidProfile != null) {
            Log.w("LocalBluetoothProfileManager", "Warning: Hid profile was previously added but the UUID is now missing.");
        }
        this.mEventManager.registerProfileIntentReceiver();
    }

    private void addHeadsetProfile(LocalBluetoothProfile profile, String profileName, String stateChangedAction, String audioStateChangedAction, int audioDisconnectedState) {
        BluetoothEventManager.Handler handler = new HeadsetStateChangeHandler(profile, audioStateChangedAction, audioDisconnectedState);
        this.mEventManager.addProfileHandler(stateChangedAction, handler);
        this.mEventManager.addProfileHandler(audioStateChangedAction, handler);
        this.mProfileNameMap.put(profileName, profile);
    }

    private void addProfile(LocalBluetoothProfile profile, String profileName, String stateChangedAction) {
        this.mEventManager.addProfileHandler(stateChangedAction, new StateChangedHandler(profile));
        this.mProfileNameMap.put(profileName, profile);
    }

    private void addPanProfile(LocalBluetoothProfile profile, String profileName, String stateChangedAction) {
        this.mEventManager.addProfileHandler(stateChangedAction, new PanStateChangedHandler(profile));
        this.mProfileNameMap.put(profileName, profile);
    }

    void setBluetoothStateOn() {
        if (this.mHidProfile == null) {
            this.mHidProfile = new HidProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
            addProfile(this.mHidProfile, "HID", "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mPanProfile == null) {
            this.mPanProfile = new PanProfile(this.mContext, this.mLocalAdapter);
            addPanProfile(this.mPanProfile, "PAN", "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mHidDeviceProfile == null) {
            this.mHidDeviceProfile = new HidDeviceProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
            addProfile(this.mHidDeviceProfile, "HID DEVICE", "android.bluetooth.hiddevice.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mMapProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local MAP profile");
            if (this.mUseMapClient) {
                this.mMapClientProfile = new MapClientProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mMapClientProfile, "MAP Client", "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED");
            } else {
                this.mMapProfile = new MapProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mMapProfile, "MAP", "android.bluetooth.map.profile.action.CONNECTION_STATE_CHANGED");
            }
        }
        if (this.mPbapProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local PBAP profile");
            this.mPbapProfile = new PbapServerProfile(this.mContext);
            addProfile(this.mPbapProfile, PbapServerProfile.NAME, "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mHearingAidProfile == null) {
            List<Integer> supportedList = this.mLocalAdapter.getSupportedProfiles();
            if (supportedList.contains(21)) {
                this.mHearingAidProfile = new HearingAidProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
                addProfile(this.mHearingAidProfile, "HearingAid", "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
            }
        }
        ParcelUuid[] uuids = this.mLocalAdapter.getUuids();
        if (uuids != null) {
            updateLocalProfiles(uuids);
        }
        this.mEventManager.readPairedDevices();
    }

    private class StateChangedHandler implements BluetoothEventManager.Handler {
        final LocalBluetoothProfile mProfile;

        StateChangedHandler(LocalBluetoothProfile profile) {
            this.mProfile = profile;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice device) {
            CachedBluetoothDevice cachedDevice = LocalBluetoothProfileManager.this.mDeviceManager.findDevice(device);
            if (cachedDevice == null) {
                Log.w("LocalBluetoothProfileManager", "StateChangedHandler found new device: " + device);
                cachedDevice = LocalBluetoothProfileManager.this.mDeviceManager.addDevice(LocalBluetoothProfileManager.this.mLocalAdapter, LocalBluetoothProfileManager.this, device);
            }
            onReceiveInternal(intent, cachedDevice);
        }

        protected void onReceiveInternal(Intent intent, CachedBluetoothDevice cachedDevice) {
            int newState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int oldState = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
            if (newState == 0 && oldState == 1) {
                Log.i("LocalBluetoothProfileManager", "Failed to connect " + this.mProfile + " device");
            }
            if (LocalBluetoothProfileManager.this.getHearingAidProfile() != null && (this.mProfile instanceof HearingAidProfile) && newState == 2 && cachedDevice.getHiSyncId() == 0) {
                long newHiSyncId = LocalBluetoothProfileManager.this.getHearingAidProfile().getHiSyncId(cachedDevice.getDevice());
                if (newHiSyncId != 0) {
                    cachedDevice.setHiSyncId(newHiSyncId);
                    LocalBluetoothProfileManager.this.mDeviceManager.onHiSyncIdChanged(newHiSyncId);
                }
            }
            LocalBluetoothProfileManager.this.mEventManager.dispatchProfileConnectionStateChanged(cachedDevice, newState, this.mProfile.getProfileId());
            cachedDevice.onProfileStateChanged(this.mProfile, newState);
            cachedDevice.refresh();
        }
    }

    private class HeadsetStateChangeHandler extends StateChangedHandler {
        private final String mAudioChangeAction;
        private final int mAudioDisconnectedState;

        HeadsetStateChangeHandler(LocalBluetoothProfile profile, String audioChangeAction, int audioDisconnectedState) {
            super(profile);
            this.mAudioChangeAction = audioChangeAction;
            this.mAudioDisconnectedState = audioDisconnectedState;
        }

        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.StateChangedHandler
        public void onReceiveInternal(Intent intent, CachedBluetoothDevice cachedDevice) {
            if (this.mAudioChangeAction.equals(intent.getAction())) {
                int newState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                if (newState != this.mAudioDisconnectedState) {
                    cachedDevice.onProfileStateChanged(this.mProfile, 2);
                }
                cachedDevice.refresh();
                return;
            }
            super.onReceiveInternal(intent, cachedDevice);
        }
    }

    private class PanStateChangedHandler extends StateChangedHandler {
        PanStateChangedHandler(LocalBluetoothProfile profile) {
            super(profile);
        }

        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.StateChangedHandler, com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice device) {
            PanProfile panProfile = (PanProfile) this.mProfile;
            int role = intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0);
            panProfile.setLocalRole(device, role);
            Log.d("LocalBluetoothProfileManager", "pan profile state change, role is " + role);
            super.onReceive(context, intent, device);
        }
    }

    public void addServiceListener(ServiceListener l) {
        this.mServiceListeners.add(l);
    }

    void callServiceConnectedListeners() {
        for (ServiceListener l : this.mServiceListeners) {
            l.onServiceConnected();
        }
    }

    void callServiceDisconnectedListeners() {
        for (ServiceListener listener : this.mServiceListeners) {
            listener.onServiceDisconnected();
        }
    }

    public A2dpProfile getA2dpProfile() {
        return this.mA2dpProfile;
    }

    public HeadsetProfile getHeadsetProfile() {
        return this.mHeadsetProfile;
    }

    public PbapServerProfile getPbapProfile() {
        return this.mPbapProfile;
    }

    public HearingAidProfile getHearingAidProfile() {
        return this.mHearingAidProfile;
    }

    HidProfile getHidProfile() {
        return this.mHidProfile;
    }

    HidDeviceProfile getHidDeviceProfile() {
        return this.mHidDeviceProfile;
    }

    synchronized void updateProfiles(ParcelUuid[] uuids, ParcelUuid[] localUuids, Collection<LocalBluetoothProfile> profiles, Collection<LocalBluetoothProfile> removedProfiles, boolean isPanNapConnected, BluetoothDevice device) {
        removedProfiles.clear();
        removedProfiles.addAll(profiles);
        Log.d("LocalBluetoothProfileManager", "Current Profiles" + profiles.toString());
        profiles.clear();
        Log.d("LocalBluetoothProfileManager", "update profiles");
        if (uuids == null) {
            Log.d("LocalBluetoothProfileManager", "remote device uuid is null");
            return;
        }
        if (this.mHeadsetProfile != null && ((BluetoothUuid.isUuidPresent(localUuids, BluetoothUuid.HSP_AG) && BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.HSP)) || (BluetoothUuid.isUuidPresent(localUuids, BluetoothUuid.Handsfree_AG) && BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Handsfree)))) {
            Log.d("LocalBluetoothProfileManager", "Add HeadsetProfile to connectable profile list");
            profiles.add(this.mHeadsetProfile);
            removedProfiles.remove(this.mHeadsetProfile);
        }
        if (this.mHfpClientProfile != null && BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Handsfree_AG) && BluetoothUuid.isUuidPresent(localUuids, BluetoothUuid.Handsfree)) {
            profiles.add(this.mHfpClientProfile);
            removedProfiles.remove(this.mHfpClientProfile);
        }
        if (BluetoothUuid.containsAnyUuid(uuids, A2dpProfile.SINK_UUIDS) && this.mA2dpProfile != null) {
            Log.d("LocalBluetoothProfileManager", "Add A2dpProfile to connectable profile list");
            profiles.add(this.mA2dpProfile);
            removedProfiles.remove(this.mA2dpProfile);
        }
        if (BluetoothUuid.containsAnyUuid(uuids, A2dpSinkProfile.SRC_UUIDS) && this.mA2dpSinkProfile != null) {
            profiles.add(this.mA2dpSinkProfile);
            removedProfiles.remove(this.mA2dpSinkProfile);
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.ObexObjectPush) && this.mOppProfile != null) {
            Log.d("LocalBluetoothProfileManager", "Add OppProfile to connectable profile list");
            profiles.add(this.mOppProfile);
            removedProfiles.remove(this.mOppProfile);
        }
        if ((BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Hid) || BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.Hogp)) && this.mHidProfile != null) {
            Log.d("LocalBluetoothProfileManager", "Add HidProfile to connectable profile list");
            profiles.add(this.mHidProfile);
            removedProfiles.remove(this.mHidProfile);
        }
        if (this.mHidDeviceProfile != null && this.mHidDeviceProfile.getConnectionStatus(device) != 0) {
            profiles.add(this.mHidDeviceProfile);
            removedProfiles.remove(this.mHidDeviceProfile);
        }
        if (isPanNapConnected) {
            Log.d("LocalBluetoothProfileManager", "Valid PAN-NAP connection exists.");
        }
        if ((BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.NAP) && this.mPanProfile != null) || isPanNapConnected) {
            Log.d("LocalBluetoothProfileManager", "Add PanProfile to connectable profile list");
            profiles.add(this.mPanProfile);
            removedProfiles.remove(this.mPanProfile);
        }
        if (this.mMapProfile != null && this.mMapProfile.getConnectionStatus(device) == 2) {
            Log.d("LocalBluetoothProfileManager", "Add MapProfile to connectable profile list");
            profiles.add(this.mMapProfile);
            removedProfiles.remove(this.mMapProfile);
            this.mMapProfile.setPreferred(device, true);
        }
        if (this.mPbapProfile != null && this.mPbapProfile.getConnectionStatus(device) == 2) {
            profiles.add(this.mPbapProfile);
            removedProfiles.remove(this.mPbapProfile);
            this.mPbapProfile.setPreferred(device, true);
        }
        if (this.mMapClientProfile != null) {
            profiles.add(this.mMapClientProfile);
            removedProfiles.remove(this.mMapClientProfile);
        }
        if (this.mUsePbapPce) {
            profiles.add(this.mPbapClientProfile);
            removedProfiles.remove(this.mPbapClientProfile);
        }
        if (BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.HearingAid) && this.mHearingAidProfile != null) {
            profiles.add(this.mHearingAidProfile);
            removedProfiles.remove(this.mHearingAidProfile);
        }
        Log.d("LocalBluetoothProfileManager", "New Profiles" + profiles.toString());
    }
}
