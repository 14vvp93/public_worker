package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.wifi.AccessPoint;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.NetworkController;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WifiNewTile extends QSTileImpl<QSTile.SignalState> {
    private static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    private final ActivityStarter mActivityStarter;
    protected final NetworkController mController;
    private final WifiDetailAdapter mDetailAdapter;
    private boolean mExpectDisabled;
    protected final WifiSignalCallback mSignalCallback;
    private final QSTile.SignalState mStateBeforeClick;
    private final NetworkController.AccessPointController mWifiController;

    public WifiNewTile(QSHost host) {
        super(host);
        this.mStateBeforeClick = newTileState();
        this.mSignalCallback = new WifiSignalCallback();
        this.mController = (NetworkController) Dependency.get(NetworkController.class);
        this.mWifiController = this.mController.getAccessPointController();
        this.mDetailAdapter = (WifiDetailAdapter) createDetailAdapter();
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        return new QSTile.SignalState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
        if (listening) {
            this.mController.addCallback((NetworkController.SignalCallback) this.mSignalCallback);
        } else {
            this.mController.removeCallback((NetworkController.SignalCallback) this.mSignalCallback);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public void setDetailListening(boolean listening) {
        if (listening) {
            this.mWifiController.addAccessPointCallback(this.mDetailAdapter);
        } else {
            this.mWifiController.removeAccessPointCallback(this.mDetailAdapter);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected DetailAdapter createDetailAdapter() {
        return new WifiDetailAdapter();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        ((QSTile.SignalState) this.mState).copyTo(this.mStateBeforeClick);
        boolean wifiEnabled = ((QSTile.SignalState) this.mState).value;
        refreshState(wifiEnabled ? null : ARG_SHOW_TRANSIENT_ENABLING);
        this.mController.setWifiEnabled(!wifiEnabled);
        this.mExpectDisabled = wifiEnabled;
        if (this.mExpectDisabled) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.-$$Lambda$WifiNewTile$oJv6m0QYqqLN6NYGXmZ1Y71fuNs
                @Override // java.lang.Runnable
                public final void run() {
                    WifiNewTile.lambda$handleClick$0(this.f$0);
                }
            }, 350L);
        }
    }

    public static /* synthetic */ void lambda$handleClick$0(WifiNewTile wifiNewTile) {
        if (wifiNewTile.mExpectDisabled) {
            wifiNewTile.mExpectDisabled = false;
            wifiNewTile.refreshState();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleSecondaryClick() {
        if (!this.mWifiController.canConfigWifi()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
            return;
        }
        showDetail(true);
        if (!((QSTile.SignalState) this.mState).value) {
            this.mController.setWifiEnabled(true);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_wifi_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState state, Object arg) {
        if (DEBUG) {
            Log.d(this.TAG, "handleUpdateState arg=" + arg);
        }
        CallbackInfo cb = this.mSignalCallback.mInfo;
        if (this.mExpectDisabled) {
            if (cb.enabled) {
                return;
            } else {
                this.mExpectDisabled = false;
            }
        }
        boolean transientEnabling = arg == ARG_SHOW_TRANSIENT_ENABLING;
        boolean wifiConnected = cb.enabled && cb.wifiSignalIconId > 0 && cb.ssid != null;
        boolean wifiNotConnected = cb.wifiSignalIconId > 0 && cb.ssid == null;
        boolean enabledChanging = state.value != cb.enabled;
        if (enabledChanging) {
            this.mDetailAdapter.setItemsVisible(cb.enabled);
            fireToggleStateChanged(cb.enabled);
        }
        if (state.slash == null) {
            state.slash = new QSTile.SlashState();
            state.slash.rotation = 6.0f;
        }
        state.slash.isSlashed = false;
        boolean isTransient = transientEnabling || cb.isTransient;
        state.secondaryLabel = getSecondaryLabel(isTransient, cb.statusLabel);
        state.state = 2;
        state.dualTarget = true;
        state.value = transientEnabling || cb.enabled;
        state.activityIn = cb.enabled && cb.activityIn;
        state.activityOut = cb.enabled && cb.activityOut;
        StringBuffer minimalContentDescription = new StringBuffer();
        Resources r = this.mContext.getResources();
        if (isTransient) {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_signal_wifi_transient_animation);
            state.label = r.getString(R.string.quick_settings_wifi_label);
        } else if (!state.value) {
            state.slash.isSlashed = true;
            state.state = 1;
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_wifi_disabled);
            state.label = r.getString(R.string.quick_settings_wifi_label);
        } else if (wifiConnected) {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_signal_wifi_transient_animation);
            state.label = removeDoubleQuotes(cb.ssid);
        } else if (wifiNotConnected) {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_wifi_disconnected);
            state.label = r.getString(R.string.quick_settings_wifi_label);
        } else {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_wifi_no_network);
            state.label = r.getString(R.string.quick_settings_wifi_label);
        }
        minimalContentDescription.append(this.mContext.getString(R.string.quick_settings_wifi_label));
        minimalContentDescription.append(",");
        if (state.value && wifiConnected) {
            minimalContentDescription.append(cb.wifiSignalContentDescription);
            minimalContentDescription.append(",");
            minimalContentDescription.append(removeDoubleQuotes(cb.ssid));
        }
        state.contentDescription = minimalContentDescription.toString();
        state.dualLabelContentDescription = r.getString(R.string.accessibility_quick_settings_open_settings, getTileLabel());
        state.expandedAccessibilityClassName = Switch.class.getName();
    }

    private CharSequence getSecondaryLabel(boolean isTransient, String statusLabel) {
        if (!isTransient) {
            return statusLabel;
        }
        return this.mContext.getString(R.string.quick_settings_wifi_secondary_label_transient);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 126;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected boolean shouldAnnouncementBeDelayed() {
        return this.mStateBeforeClick.value == ((QSTile.SignalState) this.mState).value;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected String composeChangeAnnouncement() {
        if (((QSTile.SignalState) this.mState).value) {
            return this.mContext.getString(R.string.accessibility_quick_settings_wifi_changed_on);
        }
        return this.mContext.getString(R.string.accessibility_quick_settings_wifi_changed_off);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi");
    }

    private static String removeDoubleQuotes(String string) {
        if (string == null) {
            return null;
        }
        int length = string.length();
        if (length > 1 && string.charAt(0) == '\"' && string.charAt(length - 1) == '\"') {
            return string.substring(1, length - 1);
        }
        return string;
    }

    protected static final class CallbackInfo {
        boolean activityIn;
        boolean activityOut;
        boolean connected;
        boolean enabled;
        boolean isTransient;
        String ssid;
        public String statusLabel;
        String wifiSignalContentDescription;
        int wifiSignalIconId;

        protected CallbackInfo() {
        }

        public String toString() {
            return "CallbackInfo[enabled=" + this.enabled + ",connected=" + this.connected + ",wifiSignalIconId=" + this.wifiSignalIconId + ",ssid=" + this.ssid + ",activityIn=" + this.activityIn + ",activityOut=" + this.activityOut + ",wifiSignalContentDescription=" + this.wifiSignalContentDescription + ",isTransient=" + this.isTransient + ']';
        }
    }

    protected final class WifiSignalCallback implements NetworkController.SignalCallback {
        final CallbackInfo mInfo = new CallbackInfo();

        protected WifiSignalCallback() {
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setWifiIndicators(boolean enabled, NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, boolean activityIn, boolean activityOut, String description, boolean isTransient, String statusLabel) {
            if (WifiNewTile.DEBUG) {
                Log.d(WifiNewTile.this.TAG, "onWifiSignalChanged enabled=" + enabled);
            }
            this.mInfo.enabled = enabled;
            this.mInfo.connected = qsIcon.visible;
            this.mInfo.wifiSignalIconId = qsIcon.icon;
            this.mInfo.ssid = description;
            this.mInfo.activityIn = activityIn;
            this.mInfo.activityOut = activityOut;
            this.mInfo.wifiSignalContentDescription = qsIcon.contentDescription;
            this.mInfo.isTransient = isTransient;
            this.mInfo.statusLabel = statusLabel;
            if (WifiNewTile.this.isShowingDetail()) {
                WifiNewTile.this.mDetailAdapter.updateItems();
            }
            WifiNewTile.this.refreshState();
        }
    }

    protected class WifiDetailAdapter implements DetailAdapter, QSDetailItems.Callback, NetworkController.AccessPointController.AccessPointCallback {
        private AccessPoint[] mAccessPoints;
        private QSDetailItems mItems;

        protected WifiDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public CharSequence getTitle() {
            return WifiNewTile.this.mContext.getString(R.string.quick_settings_wifi_label);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Intent getSettingsIntent() {
            return WifiNewTile.WIFI_SETTINGS;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Boolean getToggleState() {
            return Boolean.valueOf(((QSTile.SignalState) WifiNewTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public void setToggleState(boolean state) {
            if (WifiNewTile.DEBUG) {
                Log.d(WifiNewTile.this.TAG, "setToggleState " + state);
            }
            MetricsLogger.action(WifiNewTile.this.mContext, 153, state);
            WifiNewTile.this.mController.setWifiEnabled(state);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getMetricsCategory() {
            return 152;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public View createDetailView(Context context, View convertView, ViewGroup parent) {
            if (WifiNewTile.DEBUG) {
                String str = WifiNewTile.this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("createDetailView convertView=");
                sb.append(convertView != null);
                Log.d(str, sb.toString());
            }
            this.mAccessPoints = null;
            this.mItems = QSDetailItems.convertOrInflate(context, convertView, parent);
            this.mItems.setTagSuffix("Wifi");
            this.mItems.setCallback(this);
            WifiNewTile.this.mWifiController.scanForAccessPoints();
            setItemsVisible(((QSTile.SignalState) WifiNewTile.this.mState).value);
            return this.mItems;
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController.AccessPointCallback
        public void onAccessPointsChanged(List<AccessPoint> accessPoints) {
            this.mAccessPoints = (AccessPoint[]) accessPoints.toArray(new AccessPoint[accessPoints.size()]);
            filterUnreachableAPs();
            updateItems();
        }

        private void filterUnreachableAPs() {
            int numReachable = 0;
            for (AccessPoint accessPoint : this.mAccessPoints) {
                if (accessPoint.isReachable()) {
                    numReachable++;
                }
            }
            if (numReachable != this.mAccessPoints.length) {
                AccessPoint[] unfiltered = this.mAccessPoints;
                this.mAccessPoints = new AccessPoint[numReachable];
                int i = 0;
                for (AccessPoint ap : unfiltered) {
                    if (ap.isReachable()) {
                        this.mAccessPoints[i] = ap;
                        i++;
                    }
                }
            }
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController.AccessPointCallback
        public void onSettingsActivityTriggered(Intent settingsIntent) {
            WifiNewTile.this.mActivityStarter.postStartActivityDismissingKeyguard(settingsIntent, 0);
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemClick(QSDetailItems.Item item) {
            if (item == null || item.tag == null) {
                return;
            }
            AccessPoint ap = (AccessPoint) item.tag;
            if (!ap.isActive() && WifiNewTile.this.mWifiController.connect(ap)) {
                WifiNewTile.this.mHost.collapsePanels();
            }
            WifiNewTile.this.showDetail(false);
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemDisconnect(QSDetailItems.Item item) {
        }

        public void setItemsVisible(boolean visible) {
            if (this.mItems == null) {
                return;
            }
            this.mItems.setItemsVisible(visible);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateItems() {
            int i;
            if (this.mItems == null) {
                return;
            }
            if ((this.mAccessPoints != null && this.mAccessPoints.length > 0) || !WifiNewTile.this.mSignalCallback.mInfo.enabled) {
                WifiNewTile.this.fireScanStateChanged(false);
            } else {
                WifiNewTile.this.fireScanStateChanged(true);
            }
            if (!WifiNewTile.this.mSignalCallback.mInfo.enabled) {
                this.mItems.setEmptyState(R.drawable.ic_qs_wifi_detail_empty, R.string.wifi_is_off);
                this.mItems.setItems(null);
                return;
            }
            this.mItems.setEmptyState(R.drawable.ic_qs_wifi_detail_empty, R.string.quick_settings_wifi_detail_empty_text);
            QSDetailItems.Item[] items = null;
            if (this.mAccessPoints != null) {
                items = new QSDetailItems.Item[this.mAccessPoints.length];
                for (int i2 = 0; i2 < this.mAccessPoints.length; i2++) {
                    AccessPoint ap = this.mAccessPoints[i2];
                    QSDetailItems.Item item = new QSDetailItems.Item();
                    item.tag = ap;
                    item.iconResId = WifiNewTile.this.mWifiController.getIcon(ap);
                    item.line1 = ap.getSsid();
                    item.line2 = ap.isActive() ? ap.getSummary() : null;
                    if (ap.getSecurity() != 0) {
                        i = R.drawable.qs_ic_wifi_lock;
                    } else {
                        i = -1;
                    }
                    item.icon2 = i;
                    items[i2] = item;
                }
            }
            this.mItems.setItems(items);
        }
    }
}
