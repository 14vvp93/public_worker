package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AsyncChannel;
import com.android.settingslib.wifi.WifiStatusTracker;
import com.android.systemui.R;
import java.util.BitSet;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class WifiSignalController extends SignalController<WifiState, SignalController.IconGroup> {
    private final boolean mHasMobileData;
    private final AsyncChannel mWifiChannel;
    private final WifiStatusTracker mWifiTracker;

    public WifiSignalController(Context context, boolean hasMobileData, CallbackHandler callbackHandler, NetworkControllerImpl networkController, WifiManager wifiManager) {
        Messenger wifiMessenger;
        super("WifiSignalController", context, 1, callbackHandler, networkController);
        NetworkScoreManager networkScoreManager = (NetworkScoreManager) context.getSystemService(NetworkScoreManager.class);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mWifiTracker = new WifiStatusTracker(this.mContext, wifiManager, networkScoreManager, connectivityManager, new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$WifiSignalController$AffzGdHvQakHA4bIzi_tW1MVLCY
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleStatusUpdated();
            }
        });
        this.mWifiTracker.setListening(true);
        this.mHasMobileData = hasMobileData;
        Handler handler = new WifiHandler(Looper.getMainLooper());
        this.mWifiChannel = new AsyncChannel();
        if (wifiManager != null && (wifiMessenger = wifiManager.getWifiServiceMessenger()) != null) {
            this.mWifiChannel.connect(context, handler, wifiMessenger);
        }
        WifiState wifiState = (WifiState) this.mCurrentState;
        WifiState wifiState2 = (WifiState) this.mLastState;
        SignalController.IconGroup iconGroup = new SignalController.IconGroup("Wi-Fi Icons", WifiIcons.WIFI_SIGNAL_STRENGTH, WifiIcons.QS_WIFI_SIGNAL_STRENGTH, AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH, R.drawable.stat_sys_wifi_signal_null, R.drawable.ic_qs_wifi_no_network, R.drawable.stat_sys_wifi_signal_null, R.drawable.ic_qs_wifi_no_network, R.string.accessibility_no_wifi);
        wifiState2.iconGroup = iconGroup;
        wifiState.iconGroup = iconGroup;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.policy.SignalController
    public WifiState cleanState() {
        return new WifiState();
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void notifyListeners(NetworkController.SignalCallback callback) {
        boolean wifiVisible = ((WifiState) this.mCurrentState).enabled;
        String wifiDesc = wifiVisible ? ((WifiState) this.mCurrentState).ssid : null;
        boolean ssidPresent = wifiVisible && ((WifiState) this.mCurrentState).ssid != null;
        String contentDescription = getStringIfExists(getContentDescription());
        if (((WifiState) this.mCurrentState).inetCondition == 0) {
            contentDescription = contentDescription + "," + this.mContext.getString(R.string.data_connection_no_internet);
        }
        String contentDescription2 = contentDescription;
        NetworkController.IconState statusIcon = new NetworkController.IconState(wifiVisible, getCurrentIconId(), contentDescription2);
        NetworkController.IconState qsIcon = new NetworkController.IconState(((WifiState) this.mCurrentState).connected, getQsCurrentIconId(), contentDescription2);
        callback.setWifiIndicators(((WifiState) this.mCurrentState).enabled, statusIcon, qsIcon, ssidPresent && ((WifiState) this.mCurrentState).activityIn, ssidPresent && ((WifiState) this.mCurrentState).activityOut, wifiDesc, ((WifiState) this.mCurrentState).isTransient, ((WifiState) this.mCurrentState).statusLabel);
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public int getCurrentIconId() {
        int type;
        int iconId = super.getCurrentIconId();
        if (((WifiState) this.mCurrentState).connected) {
            if ((((WifiState) this.mCurrentState).activityIn || ((WifiState) this.mCurrentState).activityOut) && (type = getActiveType()) < WifiIcons.WIFI_SIGNAL_STRENGTH_INOUT[0].length) {
                return WifiIcons.WIFI_SIGNAL_STRENGTH_INOUT[((WifiState) this.mCurrentState).level][type];
            }
            return iconId;
        }
        return iconId;
    }

    private int getActiveType() {
        if (((WifiState) this.mCurrentState).activityIn && ((WifiState) this.mCurrentState).activityOut) {
            return 3;
        }
        if (((WifiState) this.mCurrentState).activityIn) {
            return 1;
        }
        if (!((WifiState) this.mCurrentState).activityOut) {
            return 0;
        }
        return 2;
    }

    public void handleBroadcast(Intent intent) {
        this.mWifiTracker.handleBroadcast(intent);
        ((WifiState) this.mCurrentState).enabled = this.mWifiTracker.enabled;
        ((WifiState) this.mCurrentState).connected = this.mWifiTracker.connected;
        ((WifiState) this.mCurrentState).ssid = this.mWifiTracker.ssid;
        ((WifiState) this.mCurrentState).rssi = this.mWifiTracker.rssi;
        ((WifiState) this.mCurrentState).level = this.mWifiTracker.level;
        ((WifiState) this.mCurrentState).statusLabel = this.mWifiTracker.statusLabel;
        notifyListenersIfNecessary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStatusUpdated() {
        ((WifiState) this.mCurrentState).statusLabel = this.mWifiTracker.statusLabel;
        notifyListenersIfNecessary();
    }

    @VisibleForTesting
    void setActivity(int wifiActivity) {
        ((WifiState) this.mCurrentState).activityIn = wifiActivity == 3 || wifiActivity == 1;
        ((WifiState) this.mCurrentState).activityOut = wifiActivity == 3 || wifiActivity == 2;
        notifyListenersIfNecessary();
    }

    private class WifiHandler extends Handler {
        WifiHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                WifiSignalController.this.setActivity(msg.arg1);
            } else if (i == 69632) {
                if (msg.arg1 == 0) {
                    WifiSignalController.this.mWifiChannel.sendMessage(Message.obtain(this, 69633));
                } else {
                    Log.e(WifiSignalController.this.mTag, "Failed to connect to wifi");
                }
            }
        }
    }

    static class WifiState extends SignalController.State {
        boolean isTransient;
        String ssid;
        String statusLabel;

        WifiState() {
        }

        @Override // com.android.systemui.statusbar.policy.SignalController.State
        public void copyFrom(SignalController.State s) {
            super.copyFrom(s);
            WifiState state = (WifiState) s;
            this.ssid = state.ssid;
            this.isTransient = state.isTransient;
            this.statusLabel = state.statusLabel;
        }

        @Override // com.android.systemui.statusbar.policy.SignalController.State
        protected void toString(StringBuilder builder) {
            super.toString(builder);
            builder.append(",ssid=");
            builder.append(this.ssid);
            builder.append(",isTransient=");
            builder.append(this.isTransient);
            builder.append(",statusLabel=");
            builder.append(this.statusLabel);
        }

        @Override // com.android.systemui.statusbar.policy.SignalController.State
        public boolean equals(Object o) {
            if (!super.equals(o)) {
                return false;
            }
            WifiState other = (WifiState) o;
            return Objects.equals(other.ssid, this.ssid) && other.isTransient == this.isTransient && TextUtils.equals(other.statusLabel, this.statusLabel);
        }
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void updateConnectivity(BitSet bitSet, BitSet bitSet2) {
        ((WifiState) this.mCurrentState).inetCondition = bitSet2.get(this.mTransportType) ? 1 : 0;
        Log.d("WifiSignalController", "mCurrentState.inetCondition = " + ((WifiState) this.mCurrentState).inetCondition);
        notifyListenersIfNecessary();
    }
}
