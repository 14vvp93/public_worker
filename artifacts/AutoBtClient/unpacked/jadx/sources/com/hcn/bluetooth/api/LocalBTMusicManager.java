package com.hcn.bluetooth.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.hcn.bluetooth.service.IBluetoothMusicService;
import com.hcn.bluetooth.service.IMusicCallback;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class LocalBTMusicManager {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_TRACK_EVENT = "android.bluetooth.avrcp-controller.profile.action.TRACK_EVENT";
    public static final int CMD_AVRCP_NEXT = 4;
    public static final int CMD_AVRCP_PAUSE = 1;
    public static final int CMD_AVRCP_PLAY = 0;
    public static final int CMD_AVRCP_PLAY_PAUSE = 2;
    public static final int CMD_AVRCP_PREV = 5;
    public static final int CMD_AVRCP_STOP = 3;
    public static final String EXTRA_METADATA = "android.bluetooth.avrcp-controller.profile.extra.METADATA";
    public static final String EXTRA_PLAYBACK = "android.bluetooth.avrcp-controller.profile.extra.PLAYBACK";
    public static final String TAG = "LocalBluetoothMusicManager";
    private static LocalBTMusicManager sInstance;
    private Context mContext;
    private boolean isBound = false;
    private IBluetoothMusicService mService = null;
    private List<ConnectionListener> mListeners = new ArrayList();
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.hcn.bluetooth.api.LocalBTMusicManager.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(LocalBTMusicManager.TAG, "onServiceDisconnected");
            LocalBTMusicManager.this.isBound = false;
            LocalBTMusicManager.this.mService = null;
            LocalBTMusicManager.this.callConnectListener(false);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(LocalBTMusicManager.TAG, "onServiceConnected");
            LocalBTMusicManager.this.mService = IBluetoothMusicService.Stub.asInterface(iBinder);
            LocalBTMusicManager.this.isBound = true;
            LocalBTMusicManager.this.callConnectListener(true);
        }
    };

    public static LocalBTMusicManager getInstance() {
        if (sInstance == null) {
            sInstance = new LocalBTMusicManager();
        }
        return sInstance;
    }

    private LocalBTMusicManager() {
        Log.i(TAG, "LocalBluetoothMusicManager Create");
    }

    public LocalBTMusicManager init(Context context) {
        Log.i(TAG, "init");
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
        }
        if (!this.isBound) {
            Intent intent = new Intent("com.hcn.bluetooth.service.BluetoothMusicService");
            intent.setClassName("com.hcn.bluetoothservice", "com.hcn.bluetooth.service.BluetoothMusicService");
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

    public boolean isA2dpConnected() {
        Log.i(TAG, "isA2dpConnected");
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isA2dpConnected();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAvrcpConnected() {
        Log.i(TAG, "isAvrcpConnected");
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isAvrcpConnected();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isA2dpPlaying() {
        Log.i(TAG, "isA2dpPlaying");
        if (!this.isBound) {
            return false;
        }
        try {
            return this.mService.isA2dpPlaying();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BluetoothDeviceInfo getConnectDevice() {
        if (!this.isBound) {
            return null;
        }
        try {
            return this.mService.getConnectDevice();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void send_Avrcp_Cmd(int i) {
        Log.i(TAG, "send_Avrcp_Cmd cmd=" + i);
        if (this.isBound) {
            try {
                this.mService.send_Avrcp_Cmd(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestA2dp() {
        Log.i(TAG, "requestA2dp");
        if (this.isBound) {
            try {
                this.mService.requestA2dp();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseA2dp() {
        Log.i(TAG, "releaseA2dp");
        if (this.isBound) {
            try {
                this.mService.releaseA2dp();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void regMusicClientBinder(IBinder iBinder) {
        IBluetoothMusicService iBluetoothMusicService;
        if (!this.isBound || (iBluetoothMusicService = this.mService) == null) {
            return;
        }
        try {
            iBluetoothMusicService.regMusicClientBinder(iBinder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unRegMusicClientBinder() throws RemoteException {
        IBluetoothMusicService iBluetoothMusicService;
        if (!this.isBound || (iBluetoothMusicService = this.mService) == null) {
            return;
        }
        try {
            iBluetoothMusicService.unRegMusicClientBinder();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerBTMusicCallback(IMusicCallback iMusicCallback) {
        IBluetoothMusicService iBluetoothMusicService;
        if (!this.isBound || (iBluetoothMusicService = this.mService) == null) {
            return;
        }
        try {
            iBluetoothMusicService.registerBTMusicCallback(iMusicCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unRegisterBTMusicCallback(IMusicCallback iMusicCallback) {
        IBluetoothMusicService iBluetoothMusicService;
        if (!this.isBound || (iBluetoothMusicService = this.mService) == null) {
            return;
        }
        try {
            iBluetoothMusicService.unregisterBTMusicCallback(iMusicCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String[] getID3Info() {
        IBluetoothMusicService iBluetoothMusicService;
        if (!this.isBound || (iBluetoothMusicService = this.mService) == null) {
            return null;
        }
        try {
            return iBluetoothMusicService.getID3Info();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
