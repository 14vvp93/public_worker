package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.settingslib.wifi.AccessPoint;
import com.android.settingslib.wifi.WifiTracker;
import com.android.systemui.R;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AccessPointControllerImpl implements WifiTracker.WifiListener, NetworkController.AccessPointController {
    private static final boolean DEBUG = Log.isLoggable("AccessPointController", 3);
    private static final int[] ICONS = {R.drawable.ic_qs_wifi_full_0, R.drawable.ic_qs_wifi_full_1, R.drawable.ic_qs_wifi_full_2, R.drawable.ic_qs_wifi_full_3, R.drawable.ic_qs_wifi_full_4};
    private final ArrayList<NetworkController.AccessPointController.AccessPointCallback> mCallbacks = new ArrayList<>();
    private final WifiManager.ActionListener mConnectListener = new WifiManager.ActionListener() { // from class: com.android.systemui.statusbar.policy.AccessPointControllerImpl.1
        public void onSuccess() {
            if (AccessPointControllerImpl.DEBUG) {
                Log.d("AccessPointController", "connect success");
            }
        }

        public void onFailure(int reason) {
            if (AccessPointControllerImpl.DEBUG) {
                Log.d("AccessPointController", "connect failure reason=" + reason);
            }
        }
    };
    private final Context mContext;
    private int mCurrentUser;
    private final UserManager mUserManager;
    private WifiTracker mWifiTracker;

    public AccessPointControllerImpl(Context context) {
        this.mWifiTracker = null;
        this.mContext = context;
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        if (context.getSystemService("wifi") != null) {
            this.mWifiTracker = new WifiTracker(context, this, false, true);
        }
        this.mCurrentUser = ActivityManager.getCurrentUser();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.mWifiTracker != null) {
            this.mWifiTracker.onDestroy();
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController
    public boolean canConfigWifi() {
        return !this.mUserManager.hasUserRestriction("no_config_wifi", new UserHandle(this.mCurrentUser));
    }

    public void onUserSwitched(int newUserId) {
        this.mCurrentUser = newUserId;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController
    public void addAccessPointCallback(NetworkController.AccessPointController.AccessPointCallback callback) {
        if (callback == null || this.mCallbacks.contains(callback)) {
            return;
        }
        if (DEBUG) {
            Log.d("AccessPointController", "addCallback " + callback);
        }
        this.mCallbacks.add(callback);
        if (this.mCallbacks.size() == 1 && this.mWifiTracker != null) {
            this.mWifiTracker.onStart();
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController
    public void removeAccessPointCallback(NetworkController.AccessPointController.AccessPointCallback callback) {
        if (callback == null) {
            return;
        }
        if (DEBUG) {
            Log.d("AccessPointController", "removeCallback " + callback);
        }
        this.mCallbacks.remove(callback);
        if (this.mCallbacks.isEmpty() && this.mWifiTracker != null) {
            this.mWifiTracker.onStop();
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController
    public void scanForAccessPoints() {
        if (this.mWifiTracker != null) {
            fireAcccessPointsCallback(this.mWifiTracker.getAccessPoints());
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController
    public int getIcon(AccessPoint ap) {
        int level = ap.getLevel();
        return ICONS[level >= 0 ? level : 0];
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController
    public boolean connect(AccessPoint ap) {
        if (ap == null) {
            return false;
        }
        if (DEBUG) {
            Log.d("AccessPointController", "connect networkId=" + ap.getConfig().networkId);
        }
        if (ap.isSaved()) {
            if (this.mWifiTracker != null) {
                this.mWifiTracker.getManager().connect(ap.getConfig().networkId, this.mConnectListener);
            }
        } else {
            if (ap.getSecurity() != 0) {
                Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                intent.putExtra("wifi_start_connect_ssid", ap.getSsidStr());
                intent.addFlags(268435456);
                fireSettingsIntentCallback(intent);
                return true;
            }
            ap.generateOpenNetworkConfig();
            if (this.mWifiTracker != null) {
                this.mWifiTracker.getManager().connect(ap.getConfig(), this.mConnectListener);
            }
        }
        return false;
    }

    private void fireSettingsIntentCallback(Intent intent) {
        for (NetworkController.AccessPointController.AccessPointCallback callback : this.mCallbacks) {
            callback.onSettingsActivityTriggered(intent);
        }
    }

    private void fireAcccessPointsCallback(List<AccessPoint> aps) {
        for (NetworkController.AccessPointController.AccessPointCallback callback : this.mCallbacks) {
            callback.onAccessPointsChanged(aps);
        }
    }

    public void dump(PrintWriter pw) {
        if (this.mWifiTracker != null) {
            this.mWifiTracker.dump(pw);
        }
    }

    @Override // com.android.settingslib.wifi.WifiTracker.WifiListener
    public void onWifiStateChanged(int state) {
    }

    @Override // com.android.settingslib.wifi.WifiTracker.WifiListener
    public void onConnectedChanged() {
        if (this.mWifiTracker != null) {
            fireAcccessPointsCallback(this.mWifiTracker.getAccessPoints());
        }
    }

    @Override // com.android.settingslib.wifi.WifiTracker.WifiListener
    public void onAccessPointsChanged() {
        if (this.mWifiTracker != null) {
            fireAcccessPointsCallback(this.mWifiTracker.getAccessPoints());
        }
    }
}
