package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.Dependency;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class HotspotControllerImpl implements WifiManager.SoftApCallback, HotspotController {
    private static final boolean DEBUG = Log.isLoggable("HotspotController", 3);
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private int mHotspotState;
    private int mNumConnectedDevices;
    private final WifiManager mWifiManager;
    private final ArrayList<HotspotController.Callback> mCallbacks = new ArrayList<>();
    private final WifiStateReceiver mWifiStateReceiver = new WifiStateReceiver();

    public HotspotControllerImpl(Context context) {
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("HotspotController state:");
        pw.print("  mHotspotEnabled=");
        pw.println(stateToString(this.mHotspotState));
    }

    private static String stateToString(int hotspotState) {
        switch (hotspotState) {
            case 10:
                return "DISABLING";
            case 11:
                return "DISABLED";
            case 12:
                return "ENABLING";
            case 13:
                return "ENABLED";
            case 14:
                return "FAILED";
            default:
                return null;
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(HotspotController.Callback callback) {
        synchronized (this.mCallbacks) {
            if (callback != null) {
                try {
                    if (!this.mCallbacks.contains(callback)) {
                        if (DEBUG) {
                            Log.d("HotspotController", "addCallback " + callback);
                        }
                        this.mCallbacks.add(callback);
                        if (this.mCallbacks.size() == 1) {
                            updateWifiStateListeners(true);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(HotspotController.Callback callback) {
        if (callback == null) {
            return;
        }
        if (DEBUG) {
            Log.d("HotspotController", "removeCallback " + callback);
        }
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
            if (this.mCallbacks.size() == 0) {
                updateWifiStateListeners(false);
            }
        }
    }

    private void updateWifiStateListeners(boolean shouldListen) {
        if (this.mWifiManager == null) {
            return;
        }
        this.mWifiStateReceiver.setListening(shouldListen);
        if (shouldListen) {
            this.mWifiManager.registerSoftApCallback(this, (Handler) Dependency.get(Dependency.MAIN_HANDLER));
        } else {
            this.mWifiManager.unregisterSoftApCallback(this);
        }
    }

    @Override // com.android.systemui.statusbar.policy.HotspotController
    public boolean isHotspotEnabled() {
        return this.mHotspotState == 13;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireHotspotChangedCallback(boolean enabled) {
        fireHotspotChangedCallback(enabled, this.mNumConnectedDevices);
    }

    private void fireHotspotChangedCallback(boolean enabled, int numConnectedDevices) {
        synchronized (this.mCallbacks) {
            for (HotspotController.Callback callback : this.mCallbacks) {
                callback.onHotspotChanged(enabled, numConnectedDevices);
            }
        }
    }

    public void onStateChanged(int state, int failureReason) {
    }

    public void onNumClientsChanged(int numConnectedDevices) {
        this.mNumConnectedDevices = numConnectedDevices;
        fireHotspotChangedCallback(isHotspotEnabled(), numConnectedDevices);
    }

    private final class WifiStateReceiver extends BroadcastReceiver {
        private boolean mRegistered;

        private WifiStateReceiver() {
        }

        public void setListening(boolean listening) {
            if (listening && !this.mRegistered) {
                if (HotspotControllerImpl.DEBUG) {
                    Log.d("HotspotController", "Registering receiver");
                }
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
                HotspotControllerImpl.this.mContext.registerReceiver(this, filter);
                this.mRegistered = true;
                return;
            }
            if (!listening && this.mRegistered) {
                if (HotspotControllerImpl.DEBUG) {
                    Log.d("HotspotController", "Unregistering receiver");
                }
                HotspotControllerImpl.this.mContext.unregisterReceiver(this);
                this.mRegistered = false;
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra("wifi_state", 14);
            if (HotspotControllerImpl.DEBUG) {
                Log.d("HotspotController", "onReceive " + state);
            }
            HotspotControllerImpl.this.mHotspotState = state;
            if (!HotspotControllerImpl.this.isHotspotEnabled()) {
                HotspotControllerImpl.this.mNumConnectedDevices = 0;
            }
            HotspotControllerImpl.this.fireHotspotChangedCallback(HotspotControllerImpl.this.isHotspotEnabled());
        }
    }
}
