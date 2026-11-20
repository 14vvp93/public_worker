package com.hcn.bluetooth.protocol;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.hcn.bluetoothservice.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CachedBluetoothDevice implements Comparable<CachedBluetoothDevice> {
    public static final int ACCESS_ALLOWED = 1;
    public static final int ACCESS_REJECTED = 2;
    public static final int ACCESS_UNKNOWN = 0;
    private static final int BLUETOOTH_NAME_MAX_LENGTH = 59;
    private static final boolean DEBUG = true;
    private static final long MAX_HOGP_DELAY_FOR_AUTO_CONNECT = 30000;
    private static final long MAX_UUID_DELAY_FOR_AUTO_CONNECT = 5000;
    private static final int MESSAGE_REJECTION_COUNT_LIMIT_TO_PERSIST = 2;
    private static final String MESSAGE_REJECTION_COUNT_PREFS_NAME = "bluetooth_message_reject";
    private static final String TAG = "Car_CachedBluetoothDevice";
    private BluetoothClass mBtClass;
    private boolean mConnectAfterPairing;
    private long mConnectAttempted;
    private final Context mContext;
    private BluetoothDevice mDevice;
    private boolean mIsConnectingErrorPossible;
    private final LocalBluetoothAdapter mLocalAdapter;
    private LocalBluetoothManager mLocalManager;
    private boolean mLocalNapRoleConnected;
    private int mMessageRejectionCount;
    private String mName;
    private final LocalBluetoothProfileManager mProfileManager;
    private short mRssi;
    private boolean mUnpairAfterDisconnect;
    private boolean mVisible;
    private final List<LocalBluetoothProfile> mProfiles = new ArrayList();
    private final List<LocalBluetoothProfile> mRemovedProfiles = new ArrayList();
    private final Collection<Callback> mCallbacks = new ArrayList();
    private HashMap<LocalBluetoothProfile, Integer> mProfileConnectionState = new HashMap<>();

    public interface Callback {
        void onDeviceAttributesChanged(CachedBluetoothDevice cachedBluetoothDevice);
    }

    private String describe(LocalBluetoothProfile localBluetoothProfile) {
        StringBuilder sb = new StringBuilder();
        sb.append("Address:").append(this.mDevice);
        if (localBluetoothProfile != null) {
            sb.append(" Profile:").append(localBluetoothProfile);
        }
        return sb.toString();
    }

    void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i) {
        Log.d(TAG, "onProfileStateChanged: profile " + localBluetoothProfile + " newProfileState " + i);
        if (this.mLocalAdapter.getBluetoothState() == 13) {
            Log.d(TAG, " BT Turninig Off...Profile conn state change ignored...");
            return;
        }
        this.mProfileConnectionState.put(localBluetoothProfile, Integer.valueOf(i));
        if (isA2dpSinkSupport(this.mLocalAdapter) && ((localBluetoothProfile instanceof A2dpSinkProfile) || (localBluetoothProfile instanceof A2dpProfile))) {
            A2dpRoleSwitcher.getInstance(this.mContext).changeProfileState(localBluetoothProfile, this.mDevice, i);
        }
        if (i == 2) {
            if (this.mProfiles.contains(localBluetoothProfile)) {
                return;
            }
            this.mRemovedProfiles.remove(localBluetoothProfile);
            this.mProfiles.add(localBluetoothProfile);
            if ((localBluetoothProfile instanceof PanProfile) && ((PanProfile) localBluetoothProfile).isLocalRoleNap(this.mDevice)) {
                this.mLocalNapRoleConnected = DEBUG;
                return;
            }
            return;
        }
        if (i == 0) {
            if (this.mLocalNapRoleConnected && (localBluetoothProfile instanceof PanProfile) && ((PanProfile) localBluetoothProfile).isLocalRoleNap(this.mDevice)) {
                Log.d(TAG, "Removing PanProfile from device after NAP disconnect");
                this.mProfiles.remove(localBluetoothProfile);
                this.mRemovedProfiles.add(localBluetoothProfile);
                this.mLocalNapRoleConnected = false;
            }
            if ((localBluetoothProfile instanceof HeadsetClientProfile) && isUnpairAfterDisconnect()) {
                setUnpairAfterDisconnect(false);
                unpair();
            }
        }
    }

    CachedBluetoothDevice(Context context, LocalBluetoothAdapter localBluetoothAdapter, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        this.mContext = context;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        fillData();
    }

    public void disconnect() {
        Log.d(TAG, "disconnect all connected profiles");
        for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
            Log.d(TAG, "profile == " + localBluetoothProfile + " state: " + localBluetoothProfile.getConnectionStatus(this.mDevice));
            disconnect(localBluetoothProfile);
        }
        PbapServerProfile pbapProfile = this.mProfileManager.getPbapProfile();
        if (pbapProfile.getConnectionStatus(this.mDevice) == 2) {
            pbapProfile.disconnect(this.mDevice);
        }
    }

    public boolean disconnect(LocalBluetoothProfile localBluetoothProfile) {
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice == null || !localBluetoothProfile.disconnect(bluetoothDevice)) {
            return false;
        }
        Log.d(TAG, "Command sent successfully:DISCONNECT " + describe(localBluetoothProfile));
        refresh();
        return DEBUG;
    }

    public void connect() {
        if (ensurePaired()) {
            Log.d(TAG, "CacheBluetoothDevice Connect");
            this.mConnectAttempted = SystemClock.elapsedRealtime();
            connectWithoutResettingTimer(DEBUG);
        }
    }

    public void connect(boolean z) {
        if (ensurePaired()) {
            this.mConnectAttempted = SystemClock.elapsedRealtime();
            connectWithoutResettingTimer(z);
        }
    }

    void onBondingDockConnect() {
        connect(false);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0061 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:0x001a A[SYNTHETIC] */
    private void connectWithoutResettingTimer(boolean z) {
        if (this.mProfiles.isEmpty()) {
            Log.d(TAG, "No profiles. Maybe we will connect later");
            return;
        }
        this.mIsConnectingErrorPossible = DEBUG;
        int i = 0;
        for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
            if (z) {
                if (localBluetoothProfile.isConnectable()) {
                    Log.d(TAG, describe(localBluetoothProfile) + " isPreferred : " + localBluetoothProfile.isPreferred(this.mDevice));
                    if (localBluetoothProfile.isPreferred(this.mDevice)) {
                        i++;
                        connectInt(localBluetoothProfile);
                    }
                }
            } else if (localBluetoothProfile.isAutoConnectable()) {
                Log.d(TAG, describe(localBluetoothProfile) + " isPreferred : " + localBluetoothProfile.isPreferred(this.mDevice));
                if (localBluetoothProfile.isPreferred(this.mDevice)) {
                    i++;
                    connectInt(localBluetoothProfile);
                }
            }
        }
        Log.d(TAG, "Preferred profiles = " + i);
        if (i == 0) {
            connectAutoConnectableProfiles();
        }
    }

    private void connectAutoConnectableProfiles() {
        if (ensurePaired()) {
            this.mIsConnectingErrorPossible = DEBUG;
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.isAutoConnectable()) {
                    localBluetoothProfile.setPreferred(this.mDevice, DEBUG);
                    Log.d(TAG, describe(localBluetoothProfile) + " setPreferred true and connect");
                    connectInt(localBluetoothProfile);
                }
            }
        }
    }

    public void connectProfile(LocalBluetoothProfile localBluetoothProfile) {
        this.mConnectAttempted = SystemClock.elapsedRealtime();
        this.mIsConnectingErrorPossible = DEBUG;
        connectInt(localBluetoothProfile);
        refresh();
    }

    synchronized boolean connectInt(LocalBluetoothProfile localBluetoothProfile) {
        if (!ensurePaired()) {
            return false;
        }
        if (localBluetoothProfile == null) {
            return false;
        }
        if (localBluetoothProfile.getConnectionStatus(this.mDevice) == 2) {
            Log.d(TAG, "connectInt: already connected " + localBluetoothProfile.toString());
            return DEBUG;
        }
        Log.d(TAG, "connectInt, mDevice = " + this.mDevice);
        if (localBluetoothProfile.connect(this.mDevice)) {
            Log.d(TAG, "Command sent successfully:CONNECT " + describe(localBluetoothProfile));
            refresh();
            return DEBUG;
        }
        Log.i(TAG, "Failed to connect " + localBluetoothProfile.toString() + " to " + this.mName);
        return false;
    }

    public void connectProfileName(String str) {
        Resources resources = this.mContext.getResources();
        this.mConnectAttempted = SystemClock.elapsedRealtime();
        this.mIsConnectingErrorPossible = DEBUG;
        Log.d(TAG, "profileName == " + str);
        if (str.equals(resources.getString(R.string.bluetooth_profile_a2dp))) {
            connectProfile(this.mProfileManager.getA2dpProfile());
            return;
        }
        if (str.equals(resources.getString(R.string.bluetooth_profile_a2dp_sink))) {
            A2dpSinkProfile a2dpSinkProfile = this.mProfileManager.getA2dpSinkProfile();
            a2dpSinkProfile.setPreferred(this.mDevice, DEBUG);
            connectProfile(a2dpSinkProfile);
            return;
        }
        if (str.equals(resources.getString(R.string.bluetooth_profile_pbap))) {
            PbapClientProfile pbapClientProfile = this.mProfileManager.getPbapClientProfile();
            pbapClientProfile.setPreferred(this.mDevice, DEBUG);
            connectProfile(pbapClientProfile);
        } else if (str.equals(resources.getString(R.string.bluetooth_profile_headset_client))) {
            HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
            headsetClientProfile.setPreferred(this.mDevice, DEBUG);
            connectProfile(headsetClientProfile);
        } else if (str.equals(resources.getString(R.string.bluetooth_profile_pan_NAP))) {
            PanProfile panProfile = this.mProfileManager.getPanProfile();
            panProfile.setPreferred(this.mDevice, DEBUG);
            connectProfile(panProfile);
        }
    }

    public boolean disconnectProfileName(String str) {
        Resources resources = this.mContext.getResources();
        this.mConnectAttempted = SystemClock.elapsedRealtime();
        this.mIsConnectingErrorPossible = DEBUG;
        Log.d(TAG, "disconnectProfileName : " + str);
        if (str.equals(resources.getString(R.string.bluetooth_profile_a2dp))) {
            return disconnect(this.mProfileManager.getA2dpProfile());
        }
        if (str.equals(resources.getString(R.string.bluetooth_profile_a2dp_sink))) {
            return disconnect(this.mProfileManager.getA2dpSinkProfile());
        }
        if (str.equals(resources.getString(R.string.bluetooth_profile_pbap))) {
            return disconnect(this.mProfileManager.getPbapClientProfile());
        }
        if (str.equals(resources.getString(R.string.bluetooth_profile_headset_client))) {
            return disconnect(this.mProfileManager.getHeadsetClientProfile());
        }
        if (str.equals(resources.getString(R.string.bluetooth_profile_pan_NAP))) {
            return disconnect(this.mProfileManager.getPanProfile());
        }
        return false;
    }

    private boolean ensurePaired() {
        if (getBondState() != 10) {
            return DEBUG;
        }
        startPairing();
        return false;
    }

    public boolean startPairing() {
        if (this.mLocalAdapter.isDiscovering()) {
            this.mLocalAdapter.cancelDiscovery();
        }
        if (this.mDevice.createBond()) {
            return DEBUG;
        }
        return false;
    }

    public boolean isConnectAfterPairing() {
        return this.mConnectAfterPairing;
    }

    public void setConnectAfterPairing(boolean z) {
        this.mConnectAfterPairing = z;
    }

    public boolean isUnpairAfterDisconnect() {
        return this.mUnpairAfterDisconnect;
    }

    public void setUnpairAfterDisconnect(boolean z) {
        this.mUnpairAfterDisconnect = z;
    }

    public void unpair() {
        BluetoothDevice bluetoothDevice;
        int bondState = getBondState();
        if (bondState == 11) {
            this.mDevice.cancelBondProcess();
        }
        if (bondState == 10 || (bluetoothDevice = this.mDevice) == null) {
            return;
        }
        if (bluetoothDevice.removeBond()) {
            Log.d(TAG, "Command sent successfully:REMOVE_BOND " + describe(null));
        } else {
            Log.v(TAG, "Framework rejected command immediately:REMOVE_BOND " + describe(null));
        }
    }

    public int getProfileConnectionState(LocalBluetoothProfile localBluetoothProfile) {
        HashMap<LocalBluetoothProfile, Integer> map = this.mProfileConnectionState;
        if (map == null || map.get(localBluetoothProfile) == null) {
            int connectionStatus = localBluetoothProfile.getConnectionStatus(this.mDevice);
            Log.d(TAG, describe(localBluetoothProfile) + " state : " + connectionStatus);
            this.mProfileConnectionState.put(localBluetoothProfile, Integer.valueOf(connectionStatus));
        }
        return this.mProfileConnectionState.get(localBluetoothProfile).intValue();
    }

    public void clearProfileConnectionState() {
        Log.d(TAG, " Clearing all connection state for dev:" + this.mDevice.getName());
        Iterator<LocalBluetoothProfile> it = getProfiles().iterator();
        while (it.hasNext()) {
            this.mProfileConnectionState.put(it.next(), 0);
        }
    }

    private void fillData() {
        fetchName();
        fetchBtClass();
        updateProfiles();
        migratePhonebookPermissionChoice();
        migrateMessagePermissionChoice();
        fetchMessageRejectionCount();
        dispatchAttributesChanged();
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    public String getAddress() {
        return this.mDevice.getAddress();
    }

    public String getName() {
        return this.mName;
    }

    void setNewName(String str) {
        if (this.mName == null) {
            this.mName = str;
            if (str == null || TextUtils.isEmpty(str)) {
                this.mName = this.mDevice.getAddress();
            }
            dispatchAttributesChanged();
        }
    }

    public void setName(String str) {
        if (str == null || TextUtils.equals(str, this.mName)) {
            return;
        }
        this.mName = str;
        this.mDevice.setAlias(str);
        dispatchAttributesChanged();
    }

    void refreshName() {
        fetchName();
        dispatchAttributesChanged();
    }

    private void fetchName() {
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice == null) {
            return;
        }
        this.mName = bluetoothDevice.getAliasName();
        Log.d(TAG, "fetchName, AlaisName is " + this.mName);
        if (TextUtils.isEmpty(this.mName)) {
            this.mName = this.mDevice.getAddress();
            Log.d(TAG, "Device has no name (yet), use address: " + this.mName);
        }
        Log.d(TAG, "fetchName, Return Name " + this.mName);
    }

    public boolean hasHumanReadableName() {
        return TextUtils.isEmpty(this.mDevice.getAliasName()) ^ DEBUG;
    }

    public int getBatteryLevel() {
        return this.mDevice.getBatteryLevel();
    }

    void refresh() {
        dispatchAttributesChanged();
    }

    boolean isVisible() {
        return this.mVisible;
    }

    void setVisible(boolean z) {
        if (this.mVisible != z) {
            this.mVisible = z;
            dispatchAttributesChanged();
        }
    }

    public int getBondState() {
        return this.mDevice.getBondState();
    }

    void setRssi(short s) {
        if (this.mRssi != s) {
            this.mRssi = s;
            dispatchAttributesChanged();
        }
    }

    public boolean isConnected() {
        Iterator<LocalBluetoothProfile> it = this.mProfiles.iterator();
        while (it.hasNext()) {
            if (getProfileConnectionState(it.next()) == 2) {
                return DEBUG;
            }
        }
        return false;
    }

    List<LocalBluetoothProfile> getConnectedProfiles() {
        ArrayList arrayList = new ArrayList();
        List<LocalBluetoothProfile> list = this.mProfiles;
        if (list == null || list.size() <= 0) {
            Log.v(TAG, "mProfiles is null or it's size 0");
            updateProfiles();
        }
        for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
            Log.v(TAG, "isConnectedProfile(profile) " + isConnectedProfile(localBluetoothProfile));
            if (isConnectedProfile(localBluetoothProfile)) {
                arrayList.add(localBluetoothProfile);
            }
        }
        return arrayList;
    }

    public boolean isConnectedProfile(LocalBluetoothProfile localBluetoothProfile) {
        if (getProfileConnectionState(localBluetoothProfile) == 2) {
            return DEBUG;
        }
        return false;
    }

    public boolean isBusy() {
        int profileConnectionState;
        for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
            if (!(localBluetoothProfile instanceof PanProfile) && ((profileConnectionState = getProfileConnectionState(localBluetoothProfile)) == 1 || profileConnectionState == 3)) {
                return DEBUG;
            }
        }
        Log.d(TAG, this.mName + " bond state is " + getBondState());
        if (getBondState() == 11) {
            return DEBUG;
        }
        return false;
    }

    private void fetchBtClass() {
        BluetoothClass bluetoothClass = this.mDevice.getBluetoothClass();
        this.mBtClass = bluetoothClass;
        if (bluetoothClass == null) {
            Log.d(TAG, "fetchClass, mBtClass is null");
        } else {
            Log.d(TAG, "fetchClass, mBtClass is " + bluetoothClass.getMajorDeviceClass());
        }
    }

    private boolean updateProfiles() {
        ParcelUuid[] uuids = this.mDevice.getUuids();
        if (uuids == null) {
            Log.d(TAG, "Bluetooth device get uuid is null");
            return false;
        }
        ParcelUuid[] uuids2 = this.mLocalAdapter.getUuids();
        if (uuids2 == null) {
            Log.d(TAG, "Bluetooth Adapter get uuid is null");
            return false;
        }
        processPhonebookAccess();
        Log.d(TAG, this.mName + " update profiles");
        this.mProfileManager.updateProfiles(uuids, uuids2, this.mProfiles, this.mRemovedProfiles, this.mLocalNapRoleConnected, this.mDevice);
        Log.e(TAG, "updating profiles for " + this.mDevice.getAliasName());
        BluetoothClass bluetoothClass = this.mDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            Log.v(TAG, "Class: " + bluetoothClass.toString());
        }
        Log.v(TAG, "UUID:");
        for (ParcelUuid parcelUuid : uuids) {
            Log.v(TAG, "  " + parcelUuid);
        }
        return DEBUG;
    }

    void refreshBtClass() {
        fetchBtClass();
        dispatchAttributesChanged();
    }

    void onUuidChanged() {
        updateProfiles();
        long j = BluetoothUuid.isUuidPresent(this.mDevice.getUuids(), BluetoothUuid.Hogp) ? MAX_HOGP_DELAY_FOR_AUTO_CONNECT : MAX_UUID_DELAY_FOR_AUTO_CONNECT;
        Log.d(TAG, "onUuidChanged: Time since last connect" + (SystemClock.elapsedRealtime() - this.mConnectAttempted));
        if (!this.mProfiles.isEmpty() && this.mConnectAttempted + j > SystemClock.elapsedRealtime()) {
            connectWithoutResettingTimer(false);
        }
        dispatchAttributesChanged();
    }

    void onBondingStateChanged(int i) {
        Log.d(TAG, "onBondingStateChanged to " + i);
        if (i == 10) {
            this.mProfiles.clear();
            setConnectAfterPairing(false);
            setPhonebookPermissionChoice(0);
            setMessagePermissionChoice(0);
            setSimPermissionChoice(0);
            this.mMessageRejectionCount = 0;
            saveMessageRejectionCount();
        }
        refresh();
        if (i == 12) {
            Log.d(TAG, "Bond state changed to bonded, mDevice.isBondingInitiatedLocally() is " + this.mDevice.isBondingInitiatedLocally());
            if (this.mDevice.isBluetoothDock()) {
                onBondingDockConnect();
            } else if (this.mDevice.isBondingInitiatedLocally() || this.mConnectAfterPairing) {
                connect(false);
            }
            setConnectAfterPairing(false);
        }
    }

    void setBtClass(BluetoothClass bluetoothClass) {
        if (bluetoothClass == null || this.mBtClass == bluetoothClass) {
            return;
        }
        this.mBtClass = bluetoothClass;
        dispatchAttributesChanged();
    }

    public BluetoothClass getBtClass() {
        return this.mBtClass;
    }

    public List<LocalBluetoothProfile> getProfiles() {
        return Collections.unmodifiableList(this.mProfiles);
    }

    public List<LocalBluetoothProfile> getConnectableProfiles() {
        Log.d(TAG, this.mName + " mprofile size is " + this.mProfiles.size());
        ArrayList arrayList = new ArrayList();
        for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
            if (localBluetoothProfile.isConnectable()) {
                arrayList.add(localBluetoothProfile);
            }
        }
        Log.d(TAG, this.mName + " conectable profile size is " + arrayList.size());
        return arrayList;
    }

    public List<LocalBluetoothProfile> getRemovedProfiles() {
        return this.mRemovedProfiles;
    }

    public void registerCallback(Callback callback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.add(callback);
        }
    }

    public void unregisterCallback(Callback callback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
        }
    }

    private void dispatchAttributesChanged() {
        synchronized (this.mCallbacks) {
            Iterator<Callback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onDeviceAttributesChanged(this);
            }
        }
    }

    public String toString() {
        return this.mDevice.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CachedBluetoothDevice)) {
            return false;
        }
        return this.mDevice.equals(((CachedBluetoothDevice) obj).mDevice);
    }

    public int hashCode() {
        return this.mDevice.getAddress().hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(CachedBluetoothDevice cachedBluetoothDevice) {
        int i = (cachedBluetoothDevice.isConnected() ? 1 : 0) - (isConnected() ? 1 : 0);
        if (i != 0) {
            return i;
        }
        int i2 = (cachedBluetoothDevice.getBondState() == 12 ? 1 : 0) - (getBondState() != 12 ? 0 : 1);
        if (i2 != 0) {
            return i2;
        }
        int i3 = (cachedBluetoothDevice.mVisible ? 1 : 0) - (this.mVisible ? 1 : 0);
        if (i3 != 0) {
            return i3;
        }
        int i4 = cachedBluetoothDevice.mRssi - this.mRssi;
        return i4 != 0 ? i4 : this.mName.compareTo(cachedBluetoothDevice.mName);
    }

    public int getPhonebookPermissionChoice() {
        int phonebookAccessPermission = this.mDevice.getPhonebookAccessPermission();
        if (phonebookAccessPermission == 1) {
            return 1;
        }
        return phonebookAccessPermission == 2 ? 2 : 0;
    }

    public void setPhonebookPermissionChoice(int i) {
        int i2 = 2;
        if (i == 1) {
            i2 = 1;
        } else if (i != 2) {
            i2 = 0;
        }
        this.mDevice.setPhonebookAccessPermission(i2);
    }

    private void migratePhonebookPermissionChoice() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("bluetooth_phonebook_permission", 0);
        if (sharedPreferences.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getPhonebookAccessPermission() == 0) {
                int i = sharedPreferences.getInt(this.mDevice.getAddress(), 0);
                if (i == 1) {
                    this.mDevice.setPhonebookAccessPermission(1);
                } else if (i == 2) {
                    this.mDevice.setPhonebookAccessPermission(2);
                }
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.remove(this.mDevice.getAddress());
            editorEdit.commit();
        }
    }

    public int getMessagePermissionChoice() {
        int messageAccessPermission = this.mDevice.getMessageAccessPermission();
        if (messageAccessPermission == 1) {
            return 1;
        }
        return messageAccessPermission == 2 ? 2 : 0;
    }

    public void setMessagePermissionChoice(int i) {
        int i2 = 2;
        if (i == 1) {
            i2 = 1;
        } else if (i != 2) {
            i2 = 0;
        }
        this.mDevice.setMessageAccessPermission(i2);
    }

    public int getSimPermissionChoice() {
        int simAccessPermission = this.mDevice.getSimAccessPermission();
        if (simAccessPermission == 1) {
            return 1;
        }
        return simAccessPermission == 2 ? 2 : 0;
    }

    void setSimPermissionChoice(int i) {
        int i2 = 2;
        if (i == 1) {
            i2 = 1;
        } else if (i != 2) {
            i2 = 0;
        }
        this.mDevice.setSimAccessPermission(i2);
    }

    private void migrateMessagePermissionChoice() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("bluetooth_message_permission", 0);
        if (sharedPreferences.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getMessageAccessPermission() == 0) {
                int i = sharedPreferences.getInt(this.mDevice.getAddress(), 0);
                if (i == 1) {
                    this.mDevice.setMessageAccessPermission(1);
                } else if (i == 2) {
                    this.mDevice.setMessageAccessPermission(2);
                }
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.remove(this.mDevice.getAddress());
            editorEdit.commit();
        }
    }

    public boolean checkAndIncreaseMessageRejectionCount() {
        int i = this.mMessageRejectionCount;
        if (i < 2) {
            this.mMessageRejectionCount = i + 1;
            saveMessageRejectionCount();
        }
        if (this.mMessageRejectionCount >= 2) {
            return DEBUG;
        }
        return false;
    }

    private void fetchMessageRejectionCount() {
        this.mMessageRejectionCount = this.mContext.getSharedPreferences(MESSAGE_REJECTION_COUNT_PREFS_NAME, 0).getInt(this.mDevice.getAddress(), 0);
    }

    private void saveMessageRejectionCount() {
        SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(MESSAGE_REJECTION_COUNT_PREFS_NAME, 0).edit();
        if (this.mMessageRejectionCount == 0) {
            editorEdit.remove(this.mDevice.getAddress());
        } else {
            editorEdit.putInt(this.mDevice.getAddress(), this.mMessageRejectionCount);
        }
        editorEdit.commit();
    }

    private void processPhonebookAccess() {
        if (this.mDevice.getBondState() == 12 && BluetoothUuid.containsAnyUuid(this.mDevice.getUuids(), PbapServerProfile.PBAB_CLIENT_UUIDS) && getPhonebookPermissionChoice() == 0) {
            if (this.mDevice.getBluetoothClass().getDeviceClass() == 1032 || this.mDevice.getBluetoothClass().getDeviceClass() == 1028) {
                setPhonebookPermissionChoice(1);
            } else {
                setPhonebookPermissionChoice(2);
            }
        }
    }

    public int getMaxConnectionState() {
        Iterator<LocalBluetoothProfile> it = getProfiles().iterator();
        int i = 0;
        while (it.hasNext()) {
            int profileConnectionState = getProfileConnectionState(it.next());
            if (profileConnectionState > i) {
                i = profileConnectionState;
            }
        }
        return i;
    }

    public String getConnectionSummary() {
        Log.d(TAG, this.mName + " getConnectionSummary");
        for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
            int profileConnectionState = getProfileConnectionState(localBluetoothProfile);
            if (localBluetoothProfile != null) {
                Log.d(TAG, "profile name is " + localBluetoothProfile.toString() + ": " + profileConnectionState);
            }
            if (profileConnectionState != 0) {
                if (profileConnectionState != 1) {
                    if (profileConnectionState == 2) {
                        Log.d(TAG, "profileConnected = true");
                    } else if (profileConnectionState != 3) {
                    }
                }
                return "";
            }
            if (localBluetoothProfile.isProfileReady()) {
                if ((localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof A2dpSinkProfile)) {
                    Log.d(TAG, "a2dpNotConnected = true");
                } else if ((localBluetoothProfile instanceof HeadsetProfile) || (localBluetoothProfile instanceof HeadsetClientProfile)) {
                    Log.d(TAG, "hfpNotConnected = true");
                }
            }
        }
        getBatteryLevel();
        return "";
    }

    public static boolean isA2dpSinkSupport(LocalBluetoothAdapter localBluetoothAdapter) {
        ParcelUuid[] uuids = localBluetoothAdapter.getUuids();
        boolean zIsUuidPresent = uuids != null ? BluetoothUuid.isUuidPresent(uuids, BluetoothUuid.AudioSink) : false;
        Log.d(TAG, "localUuids : " + uuids + " isA2dpSinkSupport : " + zIsUuidPresent);
        return zIsUuidPresent;
    }
}
