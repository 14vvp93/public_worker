package com.hcn.bluetooth.service;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.protocol.HeadsetClientProfile;
import com.hcn.bluetooth.protocol.LocalBluetoothProfileManager;
import com.hcn.bluetooth.protocol.PbapClientProfile;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothPbapClientHelper {
    public static final String ACTION_BLUETOOTH_PB_DOWNLOAD_FINISH = "com.autochips.bluetooth.BtUtils.action.ACTION_BLUE_PB_DOWNLOAD_FINISH";
    public static final String ACTION_HCN_BT_DATA_DW = "ACTION_HCN_BT_DATA_DW";
    public static final String ACTION_HCN_BT_ERROR = "ACTION_HCN_BT_ERROR";
    public static final String ACTION_SYNC_FINISH = "ACTION_MIOCH_SYNC_FINISH";
    public static final int CALL_PBAP_CONNECT_STATE_CHANGE = 1;
    public static final int CALL_PBAP_DOWNLOAD_STATE_CHANGE = 0;
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final int ERROR_CODE_DW_MIOCH = 1;
    public static final int ERROR_CODE_DW_PB = 0;
    public static final int ERROR_CODE_OBEX_FAILED = 2;
    public static final String EXTRA_DW_TYPE = "EXTRA_DW_TYPE";
    private static final int MSG_START_DOWNLOAD = 1;
    private static final String TAG = "BluetoothPbapClientHelper";
    private Context mContext;
    private LocalBluetoothProfileManager mProfileManager;
    private Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.hcn.bluetooth.service.BluetoothPbapClientHelper.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1) {
                return;
            }
            Log.d(BluetoothPbapClientHelper.TAG, "handleMessage: MSG_START_DOWNLOAD type=" + message.arg1);
            if (BluetoothPbapClientHelper.this.isPbapConnected()) {
                Intent intent = new Intent(BluetoothPbapClientHelper.ACTION_HCN_BT_DATA_DW);
                intent.putExtra(BluetoothPbapClientHelper.EXTRA_DW_TYPE, message.arg1);
                BluetoothPbapClientHelper.this.mContext.sendBroadcast(intent);
            } else {
                Log.e(BluetoothPbapClientHelper.TAG, "handleMessage: download failed pbap not connect!!!");
                BluetoothPbapClientHelper.this.mDownloadState = 2;
                BluetoothPbapClientHelper.this.callPbapListener(0);
                BluetoothPbapClientHelper.this.mDownloadType = 0;
            }
        }
    };
    private int mPbapConnectState = 0;
    private int mDownloadType = 0;
    private int mDownloadState = 0;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.hcn.bluetooth.service.BluetoothPbapClientHelper.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothPbapClientHelper.ACTION_SYNC_FINISH)) {
                BluetoothPbapClientHelper.this.mDownloadState = 0;
                BluetoothPbapClientHelper.this.mDownloadType = intent.getIntExtra(BluetoothPbapClientHelper.EXTRA_DW_TYPE, 6);
                Log.d(BluetoothPbapClientHelper.TAG, "ACTION_SYNC_FINISH dwType : " + BluetoothPbapClientHelper.this.mDownloadType);
                BluetoothPbapClientHelper.this.callPbapListener(0);
                if (BluetoothPbapClientHelper.this.mDownloadType == 2) {
                    BluetoothPbapClientHelper.this.mContext.sendBroadcast(new Intent(BluetoothPbapClientHelper.ACTION_BLUETOOTH_PB_DOWNLOAD_FINISH));
                }
                BluetoothPbapClientHelper.this.mDownloadType = 0;
                return;
            }
            if (action.equals("android.bluetooth.pbapclient.profile.action.CONNECTION_STATE_CHANGED")) {
                BluetoothPbapClientHelper.this.handlePbapConnectStateChange((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"), intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
            } else if (action.equals(BluetoothPbapClientHelper.ACTION_HCN_BT_ERROR)) {
                int intExtra = intent.getIntExtra(BluetoothPbapClientHelper.ERROR_CODE, 0);
                if (intExtra == 0) {
                    BluetoothPbapClientHelper.this.mDownloadType = 2;
                    BluetoothPbapClientHelper.this.mDownloadState = 3;
                } else if (intExtra == 1) {
                    BluetoothPbapClientHelper.this.mDownloadType = 6;
                    BluetoothPbapClientHelper.this.mDownloadState = 3;
                } else if (intExtra == 2) {
                    BluetoothPbapClientHelper.this.mDownloadState = 3;
                }
                BluetoothPbapClientHelper.this.callPbapListener(0);
                BluetoothPbapClientHelper.this.mDownloadType = 0;
            }
        }
    };
    private RemoteCallbackList<IPbapCallback> mCallBackList = new RemoteCallbackList<>();

    public BluetoothPbapClientHelper(Context context, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mProfileManager = localBluetoothProfileManager;
    }

    public int getPbapConnectState() {
        return this.mPbapConnectState;
    }

    public int getDownloadType() {
        return this.mDownloadType;
    }

    public void setDownloadType(int i) {
        this.mDownloadType = i;
    }

    public int getDownloadState() {
        return this.mDownloadState;
    }

    public void setDownloadState(int i) {
        this.mDownloadState = i;
    }

    public synchronized boolean pbapStartDownLoad(int i) {
        Log.d(TAG, "pbapStartDownLoad dwType : " + i);
        int i2 = 0;
        if (this.mDownloadState == 1) {
            return false;
        }
        this.mDownloadType = i;
        this.mDownloadState = 1;
        callPbapListener(0);
        if (!isPbapConnected()) {
            connectPbap();
            i2 = 1500;
        }
        Utils.updateReverseName();
        this.mHandler.removeMessages(1);
        Message messageObtainMessage = this.mHandler.obtainMessage(1);
        messageObtainMessage.arg1 = i;
        this.mHandler.sendMessageDelayed(messageObtainMessage, i2);
        return true;
    }

    public synchronized boolean getPbapDownLoadState(int i) {
        return this.mDownloadType == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePbapConnectStateChange(BluetoothDevice bluetoothDevice, int i) {
        this.mPbapConnectState = i;
        if (i == 0) {
            Log.d(TAG, "handlePbapConnectStateChange pbap disconnect!");
            if (this.mDownloadState == 1) {
                Log.e(TAG, "download failed pbap disconnect!!!");
                this.mDownloadState = 0;
                callPbapListener(0);
                this.mDownloadType = 0;
            }
        } else if (i != 1 && i == 2) {
            Log.d(TAG, "handlePbapConnectStateChange pbap connect!");
        }
        callPbapListener(1);
    }

    public void handleAdapterStateChange(int i) {
        if (i == 10 && this.mDownloadState == 1) {
            Log.e(TAG, "download finish caused by adapter off!!!");
            this.mDownloadState = 0;
            callPbapListener(0);
            this.mDownloadType = 0;
        }
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SYNC_FINISH);
        intentFilter.addAction(ACTION_HCN_BT_ERROR);
        intentFilter.addAction("android.bluetooth.pbapclient.profile.action.CONNECTION_STATE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    public void unRegisterReceiver() {
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (Exception unused) {
        }
    }

    public synchronized boolean isPbapConnected() {
        PbapClientProfile pbapClientProfile = this.mProfileManager.getPbapClientProfile();
        HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
        boolean z = false;
        if (headsetClientProfile != null && pbapClientProfile != null) {
            List<BluetoothDevice> connectedDevices = headsetClientProfile.getConnectedDevices();
            if (!connectedDevices.isEmpty() && pbapClientProfile.getConnectionStatus(connectedDevices.get(0)) == 2) {
                z = true;
            }
            return z;
        }
        Log.e(TAG, "isPbapConnected failed,profile is null!");
        return false;
    }

    public synchronized void connectPbap() {
        PbapClientProfile pbapClientProfile = this.mProfileManager.getPbapClientProfile();
        HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
        if (headsetClientProfile != null && pbapClientProfile != null) {
            List<BluetoothDevice> connectedDevices = headsetClientProfile.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                pbapClientProfile.connect(connectedDevices.get(0));
            }
            return;
        }
        Log.e(TAG, "connectPbap failed,profile null");
    }

    public synchronized void disConnectPbap() {
        PbapClientProfile pbapClientProfile = this.mProfileManager.getPbapClientProfile();
        HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
        if (headsetClientProfile != null && pbapClientProfile != null) {
            List<BluetoothDevice> connectedDevices = headsetClientProfile.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                pbapClientProfile.disconnect(connectedDevices.get(0));
            }
            return;
        }
        Log.e(TAG, "disConnectPbap failed,profile null");
    }

    public synchronized void registerPbapCallback(IPbapCallback iPbapCallback) {
        if (iPbapCallback == null) {
            return;
        }
        this.mCallBackList.unregister(iPbapCallback);
        this.mCallBackList.register(iPbapCallback);
    }

    public synchronized void unregisterPbapCallback(IPbapCallback iPbapCallback) {
        if (iPbapCallback == null) {
            return;
        }
        this.mCallBackList.unregister(iPbapCallback);
    }

    public synchronized void callPbapListener(int i) {
        RemoteCallbackList<IPbapCallback> remoteCallbackList;
        int iBeginBroadcast = this.mCallBackList.beginBroadcast();
        Log.d(TAG, "callPbapListener: count=" + iBeginBroadcast);
        for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
            try {
                IPbapCallback iPbapCallback = (IPbapCallback) this.mCallBackList.getBroadcastItem(i2);
                if (i == 0) {
                    iPbapCallback.onPbapDownloadStateChanged(getDownloadState(), getDownloadType());
                } else if (i == 1) {
                    iPbapCallback.onPbapConnectStateChanged(this.mPbapConnectState);
                }
            } catch (RemoteException unused) {
                remoteCallbackList = this.mCallBackList;
            } catch (Throwable th) {
                this.mCallBackList.finishBroadcast();
                throw th;
            }
        }
        remoteCallbackList = this.mCallBackList;
        remoteCallbackList.finishBroadcast();
    }
}
