package com.hcn.bluetooth.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.hcn.bluetooth.service.IAdapterCallback;
import com.hcn.bluetooth.service.IBluetoothAdapterManagerService;
import com.hcn.bluetooth.service.IPbapCallback;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class LocalBluetoothAdapterManager {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED";
    public static final int PARAM_DOWNLOAD_ALL = 1;
    public static final int PARAM_DOWNLOAD_MCH = 3;
    public static final int PARAM_DOWNLOAD_MIOCH = 6;
    public static final int PARAM_DOWNLOAD_OCH = 5;
    public static final int PARAM_DOWNLOAD_PB = 2;
    public static final int STATE_DOWNLOADED = 0;
    public static final int STATE_DOWNLOADING = 1;
    public static final int STATE_FAILED_NO_CONNECT = 2;
    public static final int STATE_FAILED_UNKNOWN = 3;
    public static final String TAG = "LocalBluetoothAdapterManager";
    public static final int THIRD_PART_NORMAL_MODE = 0;
    public static final int THIRD_PART_ZJ_CARPLAY_MODE = 1;
    private static LocalBluetoothAdapterManager sInstance;
    private Context mContext;
    private boolean isBound = false;
    private IBluetoothAdapterManagerService mService = null;
    private List<ConnectionListener> mListeners = new ArrayList();
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.hcn.bluetooth.api.LocalBluetoothAdapterManager.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(LocalBluetoothAdapterManager.TAG, "onServiceDisconnected");
            LocalBluetoothAdapterManager.this.isBound = false;
            LocalBluetoothAdapterManager.this.mService = null;
            LocalBluetoothAdapterManager.this.callConnectListener(false);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(LocalBluetoothAdapterManager.TAG, "onServiceConnected");
            LocalBluetoothAdapterManager.this.mService = IBluetoothAdapterManagerService.Stub.asInterface(iBinder);
            LocalBluetoothAdapterManager.this.isBound = true;
            LocalBluetoothAdapterManager.this.callConnectListener(true);
        }
    };

    public static synchronized LocalBluetoothAdapterManager getInstance() {
        if (sInstance == null) {
            sInstance = new LocalBluetoothAdapterManager();
        }
        return sInstance;
    }

    private LocalBluetoothAdapterManager() {
        Log.i(TAG, "LocalBluetoothAdapterManager Create");
    }

    public LocalBluetoothAdapterManager init(Context context) {
        Log.i(TAG, "init");
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
        }
        if (!this.isBound) {
            Intent intent = new Intent("com.hcn.bluetooth.service.BluetoothAdapterManagerService");
            intent.setClassName("com.hcn.bluetoothservice", "com.hcn.bluetooth.service.BluetoothAdapterManagerService");
            this.mContext.bindService(intent, this.mConnection, 1);
        }
        return sInstance;
    }

    public boolean isReady() {
        return this.isBound;
    }

    public void addConnectListener(ConnectionListener connectionListener) {
        if (connectionListener != null) {
            this.mListeners.remove(connectionListener);
            this.mListeners.add(connectionListener);
        }
    }

    public void removeConnectListener(ConnectionListener connectionListener) {
        if (connectionListener != null) {
            this.mListeners.remove(connectionListener);
        }
    }

    public void callConnectListener(boolean z) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            if (z) {
                this.mListeners.get(i).onServiceConnected();
            } else {
                this.mListeners.get(i).onServiceDisconnected();
            }
        }
    }

    public synchronized List<BluetoothDeviceInfo> getDeviceList() {
        try {
            Log.i(TAG, "getDeviceList");
            if (this.isBound) {
                try {
                    return this.mService.getDeviceList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized List<BluetoothDeviceInfo> getBondedDevices() {
        if (this.isBound) {
            try {
                return this.mService.getBondedDevices();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public synchronized BluetoothDeviceInfo getConnectDevice() {
        try {
            Log.i(TAG, "getConnectDevice");
            if (this.isBound) {
                try {
                    return this.mService.getConnectDevice();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void pairDevice(String str) {
        Log.i(TAG, "pairDevice:" + str);
        if (this.isBound) {
            try {
                this.mService.pairDevice(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void connectDevice(String str) {
        Log.i(TAG, "connectDevice:" + str);
        if (this.isBound) {
            try {
                this.mService.connectDevice(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void disconnectDevice(String str) {
        Log.i(TAG, "disconnectDevice:" + str);
        if (this.isBound) {
            try {
                this.mService.disconnectDevice(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void unpairDevice(String str) {
        Log.i(TAG, "unpairDevice:" + str);
        if (this.isBound) {
            try {
                this.mService.unpairDevice(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean isBluetoothEnable() {
        try {
            Log.i(TAG, "isBluetoothEnable");
            if (this.isBound) {
                try {
                    return this.mService.isBluetoothEnable();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized boolean isBluetoothConnected() {
        try {
            Log.i(TAG, "isBluetoothConnected");
            if (this.isBound) {
                try {
                    return this.mService.isBluetoothConnected();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setBluetoothEnable(boolean z) {
        Log.i(TAG, "setBluetoothEnable:" + z);
        if (this.isBound) {
            try {
                this.mService.setBluetoothEnable(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized String getBTName() {
        try {
            Log.i(TAG, "getBTName");
            if (this.isBound) {
                try {
                    return this.mService.getBTName();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setBTName(String str) {
        Log.i(TAG, "setBTName");
        if (this.isBound) {
            try {
                this.mService.setBTName(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized String getBTPincode() {
        try {
            Log.i(TAG, "getBTName");
            if (this.isBound) {
                try {
                    return this.mService.getBTPincode();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setBTPincode(String str) {
        if (this.isBound) {
            try {
                this.mService.setBTPincode(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBluetoothAutoAnswer() {
        Log.i(TAG, "isBluetoothAutoAnswer");
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isBluetoothAutoAnswer();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setBluetoothAutoAnswer(boolean z) {
        Log.i(TAG, "setBluetoothAutoAnswer:" + z);
        if (this.isBound) {
            try {
                this.mService.setBluetoothAutoAnswer(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBluetoothAutoConnect() {
        Log.i(TAG, "isBluetoothAutoConnect");
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isBluetoothAutoConnect();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setBluetoothAutoConnect(boolean z) {
        Log.i(TAG, "setBluetoothAutoConnect:" + z);
        if (this.isBound) {
            try {
                this.mService.setBluetoothAutoConnect(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetBT() {
        if (this.isBound) {
            try {
                this.mService.resetBT();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void startDiscovery() {
        if (this.isBound) {
            try {
                this.mService.startDiscovery();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopDiscovery() {
        if (this.isBound) {
            try {
                this.mService.stopDiscovery();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDiscovering() {
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isDiscovering();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setAutoConnectedNetwork(boolean z) {
        if (this.isBound) {
            try {
                this.mService.setAutoConnectedNetwork(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAutoConnectedNetwork() {
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isAutoConnectedNetwork();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void connectA2dp(String str) {
        if (this.isBound) {
            try {
                this.mService.connectA2dp(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnectA2dp(String str) {
        if (this.isBound) {
            try {
                this.mService.disconnectA2dp(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isA2dpConnected(String str) {
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isA2dpConnected(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPbapConnected() {
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isPbapConnected();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void connectPbap() {
        if (this.isBound) {
            try {
                this.mService.connectPbap();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void disConnectPbap() {
        if (this.isBound) {
            try {
                this.mService.disConnectPbap();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean pbapStartDownLoad(int i) {
        if (!this.isBound) {
            return false;
        }
        try {
            Utils.updateReverseName();
            return this.mService.pbapStartDownLoad(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getPbapDownLoadState(int i) {
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.getPbapDownLoadState(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registerPbapCallback(IPbapCallback iPbapCallback) {
        if (this.isBound) {
            try {
                this.mService.registerPbapCallback(iPbapCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterPbapCallback(IPbapCallback iPbapCallback) {
        if (this.isBound) {
            try {
                this.mService.unregisterPbapCallback(iPbapCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerCallback(IAdapterCallback iAdapterCallback) {
        if (this.isBound) {
            try {
                this.mService.registerCallback(iAdapterCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterCallback(IAdapterCallback iAdapterCallback) {
        if (this.isBound) {
            try {
                this.mService.unregisterCallback(iAdapterCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getThirdPartAPPMode() {
        if (!this.isBound) {
            return 0;
        }
        try {
            return this.mService.getThirdPartAPPMode();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
