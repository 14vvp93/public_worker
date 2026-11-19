package com.android.systemui.statusbar.policy;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CallbackHandler extends Handler implements NetworkController.EmergencyListener, NetworkController.SignalCallback {
    static final boolean CHATTY;
    static final boolean DEBUG;
    private final ArrayList<NetworkController.EmergencyListener> mEmergencyListeners;
    private final ArrayList<NetworkController.SignalCallback> mSignalCallbacks;

    static {
        boolean z = true;
        DEBUG = Log.isLoggable("CallbackHandler", 3) || FeatureOptions.LOG_ENABLE;
        if (!Log.isLoggable("CallbackHandlerChat", 3) && !FeatureOptions.LOG_ENABLE) {
            z = false;
        }
        CHATTY = z;
    }

    public CallbackHandler() {
        super(Looper.getMainLooper());
        this.mEmergencyListeners = new ArrayList<>();
        this.mSignalCallbacks = new ArrayList<>();
    }

    @VisibleForTesting
    CallbackHandler(Looper looper) {
        super(looper);
        this.mEmergencyListeners = new ArrayList<>();
        this.mSignalCallbacks = new ArrayList<>();
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                for (NetworkController.EmergencyListener listener : this.mEmergencyListeners) {
                    listener.setEmergencyCallsOnly(msg.arg1 != 0);
                }
                break;
            case 1:
                if (CHATTY) {
                    Log.d("CallbackHandler", "handleMessage(MSG_SUBS_CHANGED), mSignalCallbacks = " + this.mSignalCallbacks);
                }
                for (NetworkController.SignalCallback signalCluster : this.mSignalCallbacks) {
                    signalCluster.setSubs((List) msg.obj);
                }
                break;
            case 2:
                for (NetworkController.SignalCallback signalCluster2 : this.mSignalCallbacks) {
                    signalCluster2.setNoSims(msg.arg1 != 0, msg.arg2 != 0);
                }
                break;
            case 3:
                for (NetworkController.SignalCallback signalCluster3 : this.mSignalCallbacks) {
                    signalCluster3.setEthernetIndicators((NetworkController.IconState) msg.obj);
                }
                break;
            case 4:
                for (NetworkController.SignalCallback signalCluster4 : this.mSignalCallbacks) {
                    signalCluster4.setIsAirplaneMode((NetworkController.IconState) msg.obj);
                }
                break;
            case 5:
                for (NetworkController.SignalCallback signalCluster5 : this.mSignalCallbacks) {
                    signalCluster5.setMobileDataEnabled(msg.arg1 != 0);
                }
                break;
            case 6:
                if (msg.arg1 != 0) {
                    this.mEmergencyListeners.add((NetworkController.EmergencyListener) msg.obj);
                } else {
                    this.mEmergencyListeners.remove((NetworkController.EmergencyListener) msg.obj);
                }
                break;
            case 7:
                if (CHATTY) {
                    Log.d("CallbackHandler", "handleMessage(MSG_ADD_REMOVE_SIGNAL), arg1 = " + msg.arg1 + ", obj = " + msg.obj);
                }
                if (msg.arg1 != 0) {
                    this.mSignalCallbacks.add((NetworkController.SignalCallback) msg.obj);
                } else {
                    this.mSignalCallbacks.remove((NetworkController.SignalCallback) msg.obj);
                }
                break;
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setWifiIndicators(final boolean enabled, final NetworkController.IconState statusIcon, final NetworkController.IconState qsIcon, final boolean activityIn, final boolean activityOut, final String description, final boolean isTransient, final String secondaryLabel) {
        post(new Runnable() { // from class: com.android.systemui.statusbar.policy.CallbackHandler.1
            @Override // java.lang.Runnable
            public void run() {
                for (NetworkController.SignalCallback callback : CallbackHandler.this.mSignalCallbacks) {
                    callback.setWifiIndicators(enabled, statusIcon, qsIcon, activityIn, activityOut, description, isTransient, secondaryLabel);
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataIndicators(final NetworkController.IconState statusIcon, final NetworkController.IconState qsIcon, final int statusType, final int networkIcon, final int volteType, final int qsType, final boolean activityIn, final boolean activityOut, final String typeContentDescription, final String description, final boolean isWide, final int subId, final boolean roaming, final boolean isDefaultData) {
        post(new Runnable() { // from class: com.android.systemui.statusbar.policy.CallbackHandler.2
            @Override // java.lang.Runnable
            public void run() {
                for (Iterator it = CallbackHandler.this.mSignalCallbacks.iterator(); it.hasNext(); it = it) {
                    NetworkController.SignalCallback signalCluster = (NetworkController.SignalCallback) it.next();
                    signalCluster.setMobileDataIndicators(statusIcon, qsIcon, statusType, networkIcon, volteType, qsType, activityIn, activityOut, typeContentDescription, description, isWide, subId, roaming, isDefaultData);
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setSubs(List<SubscriptionInfo> subs) {
        if (DEBUG) {
            Log.d("CallbackHandler", "setSubs, subs = " + subs);
        }
        obtainMessage(1, subs).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setNoSims(boolean z, boolean z2) {
        obtainMessage(2, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataEnabled(boolean z) {
        obtainMessage(5, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.EmergencyListener
    public void setEmergencyCallsOnly(boolean z) {
        obtainMessage(0, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setEthernetIndicators(NetworkController.IconState icon) {
        obtainMessage(3, icon).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setIsAirplaneMode(NetworkController.IconState icon) {
        obtainMessage(4, icon).sendToTarget();
    }

    public void setListening(NetworkController.EmergencyListener emergencyListener, boolean z) {
        obtainMessage(6, z ? 1 : 0, 0, emergencyListener).sendToTarget();
    }

    public void setListening(NetworkController.SignalCallback signalCallback, boolean z) {
        if (DEBUG) {
            Log.d("CallbackHandler", "setListening, listener = " + signalCallback + ", listening = " + z);
        }
        obtainMessage(7, z ? 1 : 0, 0, signalCallback).sendToTarget();
    }

    public int getSignalCallbackCount() {
        return this.mSignalCallbacks.size();
    }
}
