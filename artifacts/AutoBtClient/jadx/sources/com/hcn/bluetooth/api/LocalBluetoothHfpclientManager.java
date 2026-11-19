package com.hcn.bluetooth.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.bluetooth.service.IBluetoothHfpclientService;

/* JADX INFO: loaded from: classes.dex */
public final class LocalBluetoothHfpclientManager {
    public static final String TAG = "LocalBluetoothHfpclientManager";
    private static LocalBluetoothHfpclientManager sInstance;
    private Context mContext = null;
    private boolean isBound = false;
    private IBluetoothHfpclientService mService = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.hcn.bluetooth.api.LocalBluetoothHfpclientManager.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(LocalBluetoothHfpclientManager.TAG, "onServiceDisconnected");
            LocalBluetoothHfpclientManager.this.isBound = false;
            LocalBluetoothHfpclientManager.this.mService = null;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(LocalBluetoothHfpclientManager.TAG, "onServiceConnected");
            LocalBluetoothHfpclientManager.this.mService = IBluetoothHfpclientService.Stub.asInterface(iBinder);
            LocalBluetoothHfpclientManager.this.isBound = true;
        }
    };

    public static synchronized LocalBluetoothHfpclientManager getInstance() {
        if (sInstance == null) {
            sInstance = new LocalBluetoothHfpclientManager();
        }
        return sInstance;
    }

    private LocalBluetoothHfpclientManager() {
        Log.i(TAG, "LocalBluetoothHfpclientManager Create");
    }

    public void init(Context context) {
        Log.i(TAG, "init");
        if (context == null) {
            throw new IllegalArgumentException("LocalBluetoothHfpclientManagerinit(Context context): conext is null");
        }
        if (this.mContext != null) {
            Log.e(TAG, "mContext is not null " + this.mContext.toString());
        }
        this.mContext = context.getApplicationContext();
        Intent intent = new Intent("com.hcn.bluetooth.service.BluetoothHfpclientService");
        intent.setClassName("com.hcn.bluetoothservice", "com.hcn.bluetooth.service.BluetoothHfpclientService");
        this.mContext.bindService(intent, this.mConnection, 1);
    }

    public boolean isReady() {
        return this.isBound;
    }

    public void deinit() {
        Log.i(TAG, "deinit");
        if (this.isBound) {
            this.mContext.unbindService(this.mConnection);
        }
        this.isBound = false;
        this.mContext = null;
        this.mService = null;
        sInstance = null;
    }

    public synchronized boolean isAudioConnected() {
        try {
            Log.i(TAG, "isAudioConnected");
            if (this.isBound) {
                try {
                    return this.mService.isAudioConnected();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void dial(String str) {
        Log.i(TAG, "dial:" + str + " isBound:" + this.isBound);
        IBluetoothHfpclientService iBluetoothHfpclientService = this.mService;
        if (iBluetoothHfpclientService != null) {
            try {
                iBluetoothHfpclientService.dial(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "mService == null");
        }
    }

    public synchronized void sendDTMF(byte b) {
        Log.i(TAG, "sendDTMF:" + ((int) b));
        if (this.isBound) {
            try {
                this.mService.sendDTMF(b);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void acceptCall(int i) {
        Log.i(TAG, "pickup");
        if (this.isBound) {
            try {
                this.mService.acceptCall(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void hangup() {
        Log.i(TAG, "hangup");
        if (this.isBound) {
            try {
                this.mService.hangup();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void switchAudio() {
        Log.i(TAG, "switchAudio");
        if (this.isBound) {
            try {
                this.mService.switchAudio();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized String getLastCall() {
        try {
            Log.i(TAG, "getLastCall");
            if (this.isBound) {
                try {
                    return this.mService.getLastCall();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return SkinID.SKIN_NONE;
        } catch (Throwable th) {
            throw th;
        }
    }
}
