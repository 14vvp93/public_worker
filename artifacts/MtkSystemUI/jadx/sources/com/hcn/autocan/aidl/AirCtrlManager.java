package com.hcn.autocan.aidl;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.hcn.autocan.hiworld.gm.air.AIR;
import com.hcn.util.HCNConstants;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AirCtrlManager {
    private static AirCtrlManager instance = new AirCtrlManager();
    private IAirCtrlServices airCtrlServices;
    private Context mContext;
    private String TAG = "AirCtrlManager_";
    private InitBroadcastReceiver mReceiver = null;
    private ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.hcn.autocan.aidl.AirCtrlManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(AirCtrlManager.this.TAG, " onServiceConnected ");
            AirCtrlManager.this.airCtrlServices = IAirCtrlServices.Stub.asInterface(service);
            AirCtrlManager.this.registerCallback();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Log.i(AirCtrlManager.this.TAG, "onServiceDisconnected");
            AirCtrlManager.this.unRegisterCallBack();
            AirCtrlManager.this.airCtrlServices = null;
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName name) {
            AirCtrlManager.this.unRegisterCallBack();
            AirCtrlManager.this.airCtrlServices = null;
        }
    };
    private ArrayList<IAirCallBack> list_call_back = new ArrayList<>();
    IAirCallBack callBack = new IAirCallBack.Stub() { // from class: com.hcn.autocan.aidl.AirCtrlManager.2
        @Override // com.hcn.autocan.aidl.IAirCallBack
        public void CallBackEvent(AIR airBean) throws RemoteException {
            for (IAirCallBack callBack : AirCtrlManager.this.list_call_back) {
                try {
                    callBack.CallBackEvent(airBean);
                } catch (Exception e) {
                    Log.i(AirCtrlManager.this.TAG, "err " + e.getMessage());
                }
            }
        }
    };

    private AirCtrlManager() {
    }

    public static AirCtrlManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        bindServices();
        this.mReceiver = new InitBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(HCNConstants.ACTION_INIT_CAN);
        filter.addAction(HCNConstants.ACTION_UNBIND_CAN);
        this.mContext.registerReceiver(this.mReceiver, filter);
    }

    public void airCtrl(int ctrl_id, int action) {
        if (this.airCtrlServices == null) {
            Log.i(this.TAG, "airCtrlServices is null");
            if (this.mContext != null) {
                bindServices();
                return;
            }
            return;
        }
        try {
            this.airCtrlServices.airCtrl(ctrl_id, action);
        } catch (RemoteException e) {
            bindServices();
            e.printStackTrace();
        }
    }

    public void registerCallBack(IAirCallBack callBack) {
        Log.i(this.TAG, "registerCallBack " + callBack);
        if (callBack != null && !this.list_call_back.contains(callBack)) {
            this.list_call_back.add(callBack);
        }
    }

    public void registerCallback() {
        try {
            Log.i(this.TAG, "register callBack " + this.callBack);
            if (this.callBack != null && this.airCtrlServices != null) {
                this.airCtrlServices.registerCallBack(this.callBack);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unRegisterCallBack() {
        if (this.callBack == null) {
            Log.i(this.TAG, "unRegisterCallBack is null can not be remove");
            return;
        }
        try {
            Log.i(this.TAG, "unregister callBack " + this.callBack);
            if (this.airCtrlServices != null) {
                this.airCtrlServices.unRegisterCallBack(this.callBack);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindServices() {
        if (this.mContext == null) {
            Log.i(this.TAG, "context is null");
            return;
        }
        Intent intent = new Intent();
        ComponentName mAudioComponent = new ComponentName("com.hcn.autocan", "com.hcn.autocan.service.CanService");
        intent.setComponent(mAudioComponent);
        this.mContext.bindService(intent, this.serviceConnection, 1);
    }

    public class InitBroadcastReceiver extends BroadcastReceiver {
        public InitBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (HCNConstants.ACTION_INIT_CAN.equals(action)) {
                Log.i(AirCtrlManager.this.TAG, "init can");
                AirCtrlManager.this.unRegisterCallBack();
                AirCtrlManager.this.bindServices();
            } else if (HCNConstants.ACTION_UNBIND_CAN.equals(action) && AirCtrlManager.this.mContext != null && AirCtrlManager.this.serviceConnection != null && AirCtrlManager.this.airCtrlServices != null) {
                Log.i(AirCtrlManager.this.TAG, "unbindService ");
                try {
                    AirCtrlManager.this.mContext.unbindService(AirCtrlManager.this.serviceConnection);
                } catch (Exception e) {
                    Log.i(AirCtrlManager.this.TAG, "e " + e.getMessage());
                }
            }
        }
    }
}
