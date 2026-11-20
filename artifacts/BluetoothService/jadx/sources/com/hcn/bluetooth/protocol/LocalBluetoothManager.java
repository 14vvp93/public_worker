package com.hcn.bluetooth.protocol;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class LocalBluetoothManager {
    private static final String TAG = "LocalBluetoothManager";
    private static LocalBluetoothManager sInstance;
    private final CachedBluetoothDeviceManager mCachedDeviceManager;
    private final Context mContext;
    private final BluetoothEventManager mEventManager;
    private final LocalBluetoothAdapter mLocalAdapter;
    private final LocalBluetoothProfileManager mProfileManager;

    public interface BluetoothManagerCallback {
        void onBluetoothManagerInitialized(Context context, LocalBluetoothManager localBluetoothManager);
    }

    public static synchronized LocalBluetoothManager getInstance(Context context, BluetoothManagerCallback bluetoothManagerCallback) {
        if (sInstance == null) {
            LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
            if (localBluetoothAdapter == null) {
                return null;
            }
            Context applicationContext = context.getApplicationContext();
            LocalBluetoothManager localBluetoothManager = new LocalBluetoothManager(localBluetoothAdapter, applicationContext);
            sInstance = localBluetoothManager;
            if (bluetoothManagerCallback != null) {
                bluetoothManagerCallback.onBluetoothManagerInitialized(applicationContext, localBluetoothManager);
            }
        }
        return sInstance;
    }

    private LocalBluetoothManager(LocalBluetoothAdapter localBluetoothAdapter, Context context) {
        this.mContext = context;
        this.mLocalAdapter = localBluetoothAdapter;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = new CachedBluetoothDeviceManager(context, this);
        this.mCachedDeviceManager = cachedBluetoothDeviceManager;
        BluetoothEventManager bluetoothEventManager = new BluetoothEventManager(localBluetoothAdapter, cachedBluetoothDeviceManager, context);
        this.mEventManager = bluetoothEventManager;
        this.mProfileManager = new LocalBluetoothProfileManager(context, localBluetoothAdapter, cachedBluetoothDeviceManager, bluetoothEventManager);
    }

    public LocalBluetoothAdapter getBluetoothAdapter() {
        return this.mLocalAdapter;
    }

    public Context getContext() {
        return this.mContext;
    }

    public CachedBluetoothDeviceManager getCachedDeviceManager() {
        return this.mCachedDeviceManager;
    }

    public BluetoothEventManager getEventManager() {
        return this.mEventManager;
    }

    public LocalBluetoothProfileManager getProfileManager() {
        return this.mProfileManager;
    }
}
