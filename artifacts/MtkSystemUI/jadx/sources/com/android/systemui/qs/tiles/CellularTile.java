package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.CellTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SignalTileView;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import com.android.systemui.statusbar.policy.NetworkController;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;

/* JADX INFO: loaded from: classes.dex */
public class CellularTile extends QSTileImpl<QSTile.SignalState> {
    private final ActivityStarter mActivityStarter;
    private final NetworkController mController;
    private final DataUsageController mDataController;
    private final CellularDetailAdapter mDetailAdapter;
    private boolean mDisplayDataUsage;
    private QSTile.Icon mIcon;
    private final KeyguardMonitor mKeyguardMonitor;
    private IQuickSettingsPlugin mQuickSettingsPlugin;
    private final CellSignalCallback mSignalCallback;

    public CellularTile(QSHost host) {
        super(host);
        this.mSignalCallback = new CellSignalCallback();
        this.mController = (NetworkController) Dependency.get(NetworkController.class);
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        this.mKeyguardMonitor = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);
        this.mDataController = this.mController.getMobileDataController();
        this.mDetailAdapter = new CellularDetailAdapter();
        this.mQuickSettingsPlugin = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext).makeQuickSettings(this.mContext);
        this.mDisplayDataUsage = this.mQuickSettingsPlugin.customizeDisplayDataUsage(false);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_data_usage);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        return new QSTile.SignalState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
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
    public QSIconView createTileView(Context context) {
        if (this.mDisplayDataUsage) {
            return new SignalTileView(context);
        }
        return new CellTileView(context);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return getCellularSettingIntent();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        if (getState().state == 0) {
            return;
        }
        if (this.mDataController.isMobileDataEnabled()) {
            if (this.mKeyguardMonitor.isSecure() && !this.mKeyguardMonitor.canSkipBouncer()) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CellularTile$e5lJLa1KQT80zJILmE-4gKT4_rA
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.maybeShowDisableDialog();
                    }
                });
                return;
            } else {
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CellularTile$e5lJLa1KQT80zJILmE-4gKT4_rA
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.maybeShowDisableDialog();
                    }
                });
                return;
            }
        }
        this.mDataController.setMobileDataEnabled(true);
        this.mQuickSettingsPlugin.disableDataForOtherSubscriptions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeShowDisableDialog() {
        if (Prefs.getBoolean(this.mContext, "QsHasTurnedOffMobileData", false)) {
            this.mDataController.setMobileDataEnabled(false);
            return;
        }
        String carrierName = this.mController.getMobileDataNetworkName();
        if (TextUtils.isEmpty(carrierName)) {
            carrierName = this.mContext.getString(R.string.mobile_data_disable_message_default_carrier);
        }
        AlertDialog dialog = new AlertDialog.Builder(this.mContext).setTitle(R.string.mobile_data_disable_title).setMessage(this.mContext.getString(R.string.mobile_data_disable_message, carrierName)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(android.R.string.PERSOSUBSTATE_RUIM_HRPD_ERROR, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CellularTile$oLJGrvqAwKFs9wNM4MvnfZ_a1QQ
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CellularTile.lambda$maybeShowDisableDialog$0(this.f$0, dialogInterface, i);
            }
        }).create();
        dialog.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(dialog, true);
        SystemUIDialog.registerDismissListener(dialog);
        SystemUIDialog.setWindowOnTop(dialog);
        dialog.show();
    }

    public static /* synthetic */ void lambda$maybeShowDisableDialog$0(CellularTile cellularTile, DialogInterface d, int w) {
        cellularTile.mDataController.setMobileDataEnabled(false);
        Prefs.putBoolean(cellularTile.mContext, "QsHasTurnedOffMobileData", true);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleSecondaryClick() {
        if (this.mDataController.isMobileDataSupported()) {
            showDetail(true);
        } else {
            this.mActivityStarter.postStartActivityDismissingKeyguard(getCellularSettingIntent(), 0);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        if (this.mDisplayDataUsage) {
            return this.mContext.getString(R.string.data_usage);
        }
        return this.mContext.getString(R.string.quick_settings_cellular_detail_title);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState state, Object arg) {
        CharSequence contentDescriptionSuffix;
        if (this.mDisplayDataUsage) {
            state.icon = this.mIcon;
            state.label = this.mContext.getString(R.string.data_usage);
            state.contentDescription = this.mContext.getString(R.string.data_usage);
            return;
        }
        CallbackInfo cb = (CallbackInfo) arg;
        if (cb == null) {
            cb = this.mSignalCallback.mInfo;
        }
        Resources r = this.mContext.getResources();
        state.activityIn = cb.enabled && cb.activityIn;
        state.activityOut = cb.enabled && cb.activityOut;
        state.label = r.getString(R.string.mobile_data);
        boolean mobileDataEnabled = this.mDataController.isMobileDataSupported() && this.mDataController.isMobileDataEnabled();
        state.value = mobileDataEnabled;
        state.expandedAccessibilityClassName = Switch.class.getName();
        if (cb.noSim) {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_no_sim);
        } else {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_swap_vert);
        }
        if (cb.noSim) {
            state.state = 0;
            state.secondaryLabel = r.getString(R.string.keyguard_missing_sim_message_short);
        } else if (cb.airplaneModeEnabled) {
            state.state = 0;
            state.secondaryLabel = r.getString(R.string.status_bar_airplane);
        } else if (mobileDataEnabled) {
            state.state = 2;
            state.secondaryLabel = getMobileDataDescription(cb);
        } else {
            state.state = 1;
            state.secondaryLabel = r.getString(R.string.cell_data_off);
        }
        if (state.state == 1) {
            contentDescriptionSuffix = r.getString(R.string.cell_data_off_content_description);
        } else {
            contentDescriptionSuffix = state.secondaryLabel;
        }
        state.contentDescription = ((Object) state.label) + ", " + ((Object) contentDescriptionSuffix);
    }

    private CharSequence getMobileDataDescription(CallbackInfo cb) {
        if (cb.roaming && !TextUtils.isEmpty(cb.dataContentDescription)) {
            String roaming = this.mContext.getString(R.string.data_connection_roaming);
            String dataDescription = cb.dataContentDescription;
            return this.mContext.getString(R.string.mobile_data_text_format, roaming, dataDescription);
        }
        if (cb.roaming) {
            return this.mContext.getString(R.string.data_connection_roaming);
        }
        return cb.dataContentDescription;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return com.android.systemui.plugins.R.styleable.AppCompatTheme_windowFixedHeightMinor;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mController.hasMobileDataFeature();
    }

    private static final class CallbackInfo {
        boolean activityIn;
        boolean activityOut;
        boolean airplaneModeEnabled;
        String dataContentDescription;
        boolean enabled;
        boolean noSim;
        boolean roaming;

        private CallbackInfo() {
        }
    }

    private final class CellSignalCallback implements NetworkController.SignalCallback {
        private final CallbackInfo mInfo;

        private CellSignalCallback() {
            this.mInfo = new CallbackInfo();
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setMobileDataIndicators(NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, int statusType, int networkIcon, int volteIcon, int qsType, boolean activityIn, boolean activityOut, String typeContentDescription, String description, boolean isWide, int subId, boolean roaming, boolean isDefaultData) {
            if (qsIcon == null) {
                return;
            }
            this.mInfo.enabled = qsIcon.visible;
            this.mInfo.dataContentDescription = typeContentDescription;
            this.mInfo.activityIn = activityIn;
            this.mInfo.activityOut = activityOut;
            this.mInfo.roaming = roaming;
            CellularTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setNoSims(boolean show, boolean simDetected) {
            this.mInfo.noSim = show;
            CellularTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setIsAirplaneMode(NetworkController.IconState icon) {
            this.mInfo.airplaneModeEnabled = icon.visible;
            CellularTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setMobileDataEnabled(boolean enabled) {
            CellularTile.this.mDetailAdapter.setMobileDataEnabled(enabled);
        }
    }

    static Intent getCellularSettingIntent() {
        return new Intent("android.settings.DATA_USAGE_SETTINGS");
    }

    private final class CellularDetailAdapter implements DetailAdapter {
        private CellularDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public CharSequence getTitle() {
            return CellularTile.this.mContext.getString(R.string.quick_settings_cellular_detail_title);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Boolean getToggleState() {
            if (CellularTile.this.mDataController.isMobileDataSupported()) {
                return Boolean.valueOf(CellularTile.this.mDataController.isMobileDataEnabled());
            }
            return null;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Intent getSettingsIntent() {
            return CellularTile.getCellularSettingIntent();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public void setToggleState(boolean state) {
            MetricsLogger.action(CellularTile.this.mContext, 155, state);
            CellularTile.this.mDataController.setMobileDataEnabled(state);
            if (state) {
                CellularTile.this.mQuickSettingsPlugin.disableDataForOtherSubscriptions();
            }
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getMetricsCategory() {
            return com.android.systemui.plugins.R.styleable.AppCompatTheme_windowFixedWidthMinor;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public View createDetailView(Context context, View convertView, ViewGroup parent) {
            View viewInflate;
            if (convertView == null) {
                viewInflate = LayoutInflater.from(CellularTile.this.mContext).inflate(R.layout.data_usage, parent, false);
            } else {
                viewInflate = convertView;
            }
            DataUsageDetailView v = (DataUsageDetailView) viewInflate;
            DataUsageController.DataUsageInfo info = CellularTile.this.mDataController.getDataUsageInfo();
            if (info == null) {
                return v;
            }
            v.bind(info);
            v.findViewById(R.id.roaming_text).setVisibility(CellularTile.this.mSignalCallback.mInfo.roaming ? 0 : 4);
            return v;
        }

        public void setMobileDataEnabled(boolean enabled) {
            CellularTile.this.fireToggleStateChanged(enabled);
        }
    }
}
