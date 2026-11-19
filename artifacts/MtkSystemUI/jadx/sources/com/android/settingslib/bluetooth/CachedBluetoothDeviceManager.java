package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class CachedBluetoothDeviceManager {
    private final LocalBluetoothManager mBtManager;
    private Context mContext;

    @VisibleForTesting
    final List<CachedBluetoothDevice> mCachedDevices = new ArrayList();

    @VisibleForTesting
    final List<CachedBluetoothDevice> mHearingAidDevicesNotAddedInCache = new ArrayList();

    @VisibleForTesting
    final Map<Long, CachedBluetoothDevice> mCachedDevicesMapForHearingAids = new HashMap();

    CachedBluetoothDeviceManager(Context context, LocalBluetoothManager localBtManager) {
        this.mContext = context;
        this.mBtManager = localBtManager;
    }

    public synchronized Collection<CachedBluetoothDevice> getCachedDevicesCopy() {
        return new ArrayList(this.mCachedDevices);
    }

    public static boolean onDeviceDisappeared(CachedBluetoothDevice cachedDevice) {
        cachedDevice.setJustDiscovered(false);
        return cachedDevice.getBondState() == 10;
    }

    public void onDeviceNameUpdated(BluetoothDevice device) {
        CachedBluetoothDevice cachedDevice = findDevice(device);
        if (cachedDevice != null) {
            cachedDevice.refreshName();
        }
    }

    public synchronized CachedBluetoothDevice findDevice(BluetoothDevice device) {
        for (CachedBluetoothDevice cachedDevice : this.mCachedDevices) {
            if (cachedDevice.getDevice().equals(device)) {
                return cachedDevice;
            }
        }
        for (CachedBluetoothDevice notCachedDevice : this.mHearingAidDevicesNotAddedInCache) {
            if (notCachedDevice.getDevice().equals(device)) {
                return notCachedDevice;
            }
        }
        return null;
    }

    public CachedBluetoothDevice addDevice(LocalBluetoothAdapter adapter, LocalBluetoothProfileManager profileManager, BluetoothDevice device) {
        CachedBluetoothDevice newDevice = new CachedBluetoothDevice(this.mContext, adapter, profileManager, device);
        if (profileManager.getHearingAidProfile() != null && profileManager.getHearingAidProfile().getHiSyncId(newDevice.getDevice()) != 0) {
            newDevice.setHiSyncId(profileManager.getHearingAidProfile().getHiSyncId(newDevice.getDevice()));
        }
        if (isPairAddedInCache(newDevice.getHiSyncId())) {
            synchronized (this) {
                this.mHearingAidDevicesNotAddedInCache.add(newDevice);
            }
        } else {
            synchronized (this) {
                this.mCachedDevices.add(newDevice);
                if (newDevice.getHiSyncId() != 0 && !this.mCachedDevicesMapForHearingAids.containsKey(Long.valueOf(newDevice.getHiSyncId()))) {
                    this.mCachedDevicesMapForHearingAids.put(Long.valueOf(newDevice.getHiSyncId()), newDevice);
                }
                this.mBtManager.getEventManager().dispatchDeviceAdded(newDevice);
            }
        }
        return newDevice;
    }

    private synchronized boolean isPairAddedInCache(long hiSyncId) {
        if (hiSyncId == 0) {
            return false;
        }
        return this.mCachedDevicesMapForHearingAids.containsKey(Long.valueOf(hiSyncId));
    }

    public synchronized void updateHearingAidsDevices(LocalBluetoothProfileManager profileManager) {
        HearingAidProfile profileProxy = profileManager.getHearingAidProfile();
        if (profileProxy == null) {
            log("updateHearingAidsDevices: getHearingAidProfile() is null");
            return;
        }
        Set<Long> syncIdChangedSet = new HashSet<>();
        for (CachedBluetoothDevice cachedDevice : this.mCachedDevices) {
            if (cachedDevice.getHiSyncId() == 0) {
                long newHiSyncId = profileProxy.getHiSyncId(cachedDevice.getDevice());
                if (newHiSyncId != 0) {
                    cachedDevice.setHiSyncId(newHiSyncId);
                    syncIdChangedSet.add(Long.valueOf(newHiSyncId));
                }
            }
        }
        for (Long syncId : syncIdChangedSet) {
            onHiSyncIdChanged(syncId.longValue());
        }
    }

    public synchronized void clearNonBondedDevices() {
        this.mCachedDevicesMapForHearingAids.entrySet().removeIf(new Predicate() { // from class: com.android.settingslib.bluetooth.-$$Lambda$CachedBluetoothDeviceManager$e-LCWrNLhjsTC176L1agksvai7c
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CachedBluetoothDeviceManager.lambda$clearNonBondedDevices$0((Map.Entry) obj);
            }
        });
        this.mCachedDevices.removeIf(new Predicate() { // from class: com.android.settingslib.bluetooth.-$$Lambda$CachedBluetoothDeviceManager$kt27ylP2LAAkpFyw4Jk0DP1n8j4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CachedBluetoothDeviceManager.lambda$clearNonBondedDevices$1((CachedBluetoothDevice) obj);
            }
        });
        this.mHearingAidDevicesNotAddedInCache.removeIf(new Predicate() { // from class: com.android.settingslib.bluetooth.-$$Lambda$CachedBluetoothDeviceManager$RiRiNHhShon-xX_yLpIJ83GKKko
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CachedBluetoothDeviceManager.lambda$clearNonBondedDevices$2((CachedBluetoothDevice) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$clearNonBondedDevices$0(Map.Entry entries) {
        return ((CachedBluetoothDevice) entries.getValue()).getBondState() == 10;
    }

    static /* synthetic */ boolean lambda$clearNonBondedDevices$1(CachedBluetoothDevice cachedDevice) {
        return cachedDevice.getBondState() == 10;
    }

    static /* synthetic */ boolean lambda$clearNonBondedDevices$2(CachedBluetoothDevice hearingAidDevice) {
        return hearingAidDevice.getBondState() == 10;
    }

    public synchronized void onScanningStateChanged(boolean started) {
        if (started) {
            for (int i = this.mCachedDevices.size() - 1; i >= 0; i--) {
                CachedBluetoothDevice cachedDevice = this.mCachedDevices.get(i);
                cachedDevice.setJustDiscovered(false);
            }
            for (int i2 = this.mHearingAidDevicesNotAddedInCache.size() - 1; i2 >= 0; i2--) {
                CachedBluetoothDevice notCachedDevice = this.mHearingAidDevicesNotAddedInCache.get(i2);
                notCachedDevice.setJustDiscovered(false);
            }
        }
    }

    public synchronized void onBtClassChanged(BluetoothDevice device) {
        CachedBluetoothDevice cachedDevice = findDevice(device);
        if (cachedDevice != null) {
            cachedDevice.refreshBtClass();
        }
    }

    public synchronized void onUuidChanged(BluetoothDevice device) {
        CachedBluetoothDevice cachedDevice = findDevice(device);
        if (cachedDevice != null) {
            cachedDevice.onUuidChanged();
        }
    }

    public synchronized void onBluetoothStateChanged(int bluetoothState) {
        if (bluetoothState == 13) {
            for (int i = this.mCachedDevices.size() - 1; i >= 0; i--) {
                CachedBluetoothDevice cachedDevice = this.mCachedDevices.get(i);
                if (cachedDevice.getBondState() != 12) {
                    Log.d("CachedBluetoothDeviceManager", "Remove device for bond state : " + cachedDevice.getBondState() + "     and device name is : " + cachedDevice.getName());
                    cachedDevice.setJustDiscovered(false);
                    this.mCachedDevices.remove(i);
                    if (cachedDevice.getHiSyncId() != 0 && this.mCachedDevicesMapForHearingAids.containsKey(Long.valueOf(cachedDevice.getHiSyncId()))) {
                        this.mCachedDevicesMapForHearingAids.remove(Long.valueOf(cachedDevice.getHiSyncId()));
                    }
                } else {
                    cachedDevice.clearProfileConnectionState();
                }
            }
            for (int i2 = this.mHearingAidDevicesNotAddedInCache.size() - 1; i2 >= 0; i2--) {
                CachedBluetoothDevice notCachedDevice = this.mHearingAidDevicesNotAddedInCache.get(i2);
                if (notCachedDevice.getBondState() != 12) {
                    notCachedDevice.setJustDiscovered(false);
                    this.mHearingAidDevicesNotAddedInCache.remove(i2);
                } else {
                    notCachedDevice.clearProfileConnectionState();
                }
            }
        }
    }

    public synchronized void onActiveDeviceChanged(CachedBluetoothDevice activeDevice, int bluetoothProfile) {
        for (CachedBluetoothDevice cachedDevice : this.mCachedDevices) {
            boolean isActive = Objects.equals(cachedDevice, activeDevice);
            cachedDevice.onActiveDeviceChanged(isActive, bluetoothProfile);
        }
    }

    public synchronized void onHiSyncIdChanged(long hiSyncId) {
        int indexToRemoveFromUi;
        CachedBluetoothDevice deviceToRemoveFromUi;
        int firstMatchedIndex = -1;
        for (int i = this.mCachedDevices.size() - 1; i >= 0; i--) {
            CachedBluetoothDevice cachedDevice = this.mCachedDevices.get(i);
            if (cachedDevice.getHiSyncId() == hiSyncId) {
                if (firstMatchedIndex != -1) {
                    if (cachedDevice.isConnected()) {
                        indexToRemoveFromUi = firstMatchedIndex;
                        deviceToRemoveFromUi = this.mCachedDevices.get(firstMatchedIndex);
                        this.mCachedDevicesMapForHearingAids.put(Long.valueOf(hiSyncId), cachedDevice);
                    } else {
                        indexToRemoveFromUi = i;
                        deviceToRemoveFromUi = cachedDevice;
                        this.mCachedDevicesMapForHearingAids.put(Long.valueOf(hiSyncId), this.mCachedDevices.get(firstMatchedIndex));
                    }
                    this.mCachedDevices.remove(indexToRemoveFromUi);
                    this.mHearingAidDevicesNotAddedInCache.add(deviceToRemoveFromUi);
                    log("onHiSyncIdChanged: removed from UI device=" + deviceToRemoveFromUi + ", with hiSyncId=" + hiSyncId);
                    this.mBtManager.getEventManager().dispatchDeviceRemoved(deviceToRemoveFromUi);
                    break;
                }
                this.mCachedDevicesMapForHearingAids.put(Long.valueOf(hiSyncId), cachedDevice);
                firstMatchedIndex = i;
            }
        }
    }

    private CachedBluetoothDevice getHearingAidOtherDevice(CachedBluetoothDevice thisDevice, long hiSyncId) {
        if (hiSyncId == 0) {
            return null;
        }
        for (CachedBluetoothDevice notCachedDevice : this.mHearingAidDevicesNotAddedInCache) {
            if (hiSyncId == notCachedDevice.getHiSyncId() && !Objects.equals(notCachedDevice, thisDevice)) {
                return notCachedDevice;
            }
        }
        CachedBluetoothDevice cachedDevice = this.mCachedDevicesMapForHearingAids.get(Long.valueOf(hiSyncId));
        if (Objects.equals(cachedDevice, thisDevice)) {
            return null;
        }
        return cachedDevice;
    }

    private void hearingAidSwitchDisplayDevice(CachedBluetoothDevice toDisplayDevice, CachedBluetoothDevice toHideDevice, long hiSyncId) {
        log("hearingAidSwitchDisplayDevice: toDisplayDevice=" + toDisplayDevice + ", toHideDevice=" + toHideDevice);
        this.mHearingAidDevicesNotAddedInCache.add(toHideDevice);
        this.mCachedDevices.remove(toHideDevice);
        this.mBtManager.getEventManager().dispatchDeviceRemoved(toHideDevice);
        this.mHearingAidDevicesNotAddedInCache.remove(toDisplayDevice);
        this.mCachedDevices.add(toDisplayDevice);
        this.mCachedDevicesMapForHearingAids.put(Long.valueOf(hiSyncId), toDisplayDevice);
        this.mBtManager.getEventManager().dispatchDeviceAdded(toDisplayDevice);
    }

    public synchronized void onProfileConnectionStateChanged(CachedBluetoothDevice cachedDevice, int state, int bluetoothProfile) {
        CachedBluetoothDevice mapDevice;
        if (bluetoothProfile == 21) {
            if (cachedDevice.getHiSyncId() != 0 && cachedDevice.getBondState() == 12) {
                long hiSyncId = cachedDevice.getHiSyncId();
                CachedBluetoothDevice otherDevice = getHearingAidOtherDevice(cachedDevice, hiSyncId);
                if (otherDevice == null) {
                    return;
                }
                if (state == 2 && this.mHearingAidDevicesNotAddedInCache.contains(cachedDevice)) {
                    hearingAidSwitchDisplayDevice(cachedDevice, otherDevice, hiSyncId);
                } else if (state == 0 && otherDevice.isConnected() && (mapDevice = this.mCachedDevicesMapForHearingAids.get(Long.valueOf(hiSyncId))) != null && Objects.equals(cachedDevice, mapDevice)) {
                    hearingAidSwitchDisplayDevice(otherDevice, cachedDevice, hiSyncId);
                }
            }
        }
    }

    public synchronized void onDeviceUnpaired(CachedBluetoothDevice device) {
        long hiSyncId = device.getHiSyncId();
        if (hiSyncId == 0) {
            return;
        }
        for (int i = this.mHearingAidDevicesNotAddedInCache.size() - 1; i >= 0; i--) {
            CachedBluetoothDevice cachedDevice = this.mHearingAidDevicesNotAddedInCache.get(i);
            if (cachedDevice.getHiSyncId() == hiSyncId) {
                this.mHearingAidDevicesNotAddedInCache.remove(i);
                if (device != cachedDevice) {
                    log("onDeviceUnpaired: Unpair device=" + cachedDevice);
                    cachedDevice.unpair();
                }
            }
        }
        CachedBluetoothDevice mappedDevice = this.mCachedDevicesMapForHearingAids.get(Long.valueOf(hiSyncId));
        if (mappedDevice != null && !Objects.equals(device, mappedDevice)) {
            log("onDeviceUnpaired: Unpair mapped device=" + mappedDevice);
            mappedDevice.unpair();
        }
    }

    public synchronized void dispatchAudioModeChanged() {
        for (CachedBluetoothDevice cachedDevice : this.mCachedDevices) {
            cachedDevice.onAudioModeChanged();
        }
    }

    private void log(String msg) {
        Log.d("CachedBluetoothDeviceManager", msg);
    }
}
