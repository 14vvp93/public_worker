package com.mediatek.systemui.qs.tiles.ext;

import android.content.Intent;
import android.telephony.SubscriptionManager;
import android.util.Log;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.NetworkController;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.extcb.IconIdWrapper;
import com.mediatek.systemui.statusbar.util.SIMHelper;

/* JADX INFO: loaded from: classes.dex */
public class MobileDataTile extends QSTileImpl<QSTile.SignalState> {
    private final MobileDataSignalCallback mCallback;
    private boolean mConnected;
    private final NetworkController mController;
    private int mDataConnectionState;
    private final DataUsageController mDataController;
    private int mDataStateIconId;
    private final IconIdWrapper mDisableStateIconIdWrapper;
    private final IconIdWrapper mEnableStateIconIdWrapper;
    private boolean mEnabled;
    private IQuickSettingsPlugin mQuickSettingsExt;
    private CharSequence mTileLabel;

    public MobileDataTile(QSHost host) {
        super(host);
        this.mQuickSettingsExt = null;
        this.mDataConnectionState = 0;
        this.mDataStateIconId = R.drawable.ic_qs_mobile_off;
        this.mEnableStateIconIdWrapper = new IconIdWrapper();
        this.mDisableStateIconIdWrapper = new IconIdWrapper();
        this.mCallback = new MobileDataSignalCallback();
        this.mQuickSettingsExt = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext).makeQuickSettings(this.mContext);
        this.mController = (NetworkController) Dependency.get(NetworkController.class);
        this.mDataController = this.mController.getMobileDataController();
        Log.d(this.TAG, "create MobileDataTile");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
        Log.d(this.TAG, "setListening = " + listening);
        if (listening) {
            this.mController.addCallback((NetworkController.SignalCallback) this.mCallback);
        } else {
            this.mController.removeCallback((NetworkController.SignalCallback) this.mCallback);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.mobile);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        return new QSTile.SignalState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBar;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleLongClick() {
        handleClick();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        int subId;
        if (this.mDataController.isMobileDataSupported() && this.mEnabled) {
            if (!this.mConnected && ((subId = SubscriptionManager.getDefaultDataSubscriptionId()) < 0 || !SIMHelper.isRadioOn(subId))) {
                return;
            }
            this.mDataController.setMobileDataEnabled(!this.mConnected);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState state, Object arg) {
        Log.d(this.TAG, "handleUpdateState arg=" + arg);
        CallbackInfo cb = (CallbackInfo) arg;
        if (cb == null) {
            cb = this.mCallback.mInfo;
        }
        boolean enabled = this.mDataController.isMobileDataSupported() && !cb.noSim && !cb.airplaneModeEnabled && isDefaultDataSimRadioOn();
        boolean dataConnected = enabled && this.mDataController.isMobileDataEnabled() && cb.mobileSignalIconId > 0;
        boolean dataNotConnected = cb.mobileSignalIconId > 0 && cb.enabledDesc == null;
        this.mEnabled = enabled;
        this.mConnected = dataConnected;
        state.activityIn = cb.enabled && cb.activityIn;
        state.activityOut = cb.enabled && cb.activityOut;
        if (!enabled) {
            this.mDataConnectionState = 3;
            this.mDataStateIconId = R.drawable.ic_qs_mobile_off;
            this.mDisableStateIconIdWrapper.setResources(this.mContext.getResources());
            this.mDisableStateIconIdWrapper.setIconId(this.mDataStateIconId);
            state.label = this.mQuickSettingsExt.customizeDataConnectionTile(this.mDataConnectionState, this.mDisableStateIconIdWrapper, this.mContext.getString(R.string.mobile));
            state.icon = QsIconWrapper.get(this.mDisableStateIconIdWrapper.getIconId(), this.mDisableStateIconIdWrapper);
        } else if (dataConnected) {
            this.mDataConnectionState = 1;
            this.mDataStateIconId = R.drawable.ic_qs_mobile_white;
            this.mEnableStateIconIdWrapper.setResources(this.mContext.getResources());
            this.mEnableStateIconIdWrapper.setIconId(this.mDataStateIconId);
            state.label = this.mQuickSettingsExt.customizeDataConnectionTile(this.mDataConnectionState, this.mEnableStateIconIdWrapper, this.mContext.getString(R.string.mobile));
            state.icon = QsIconWrapper.get(this.mEnableStateIconIdWrapper.getIconId(), this.mEnableStateIconIdWrapper);
        } else if (dataNotConnected) {
            this.mDataConnectionState = 0;
            this.mDataStateIconId = R.drawable.ic_qs_mobile_off;
            this.mDisableStateIconIdWrapper.setResources(this.mContext.getResources());
            this.mDisableStateIconIdWrapper.setIconId(this.mDataStateIconId);
            state.label = this.mQuickSettingsExt.customizeDataConnectionTile(this.mDataConnectionState, this.mDisableStateIconIdWrapper, this.mContext.getString(R.string.mobile));
            state.icon = QsIconWrapper.get(this.mDisableStateIconIdWrapper.getIconId(), this.mDisableStateIconIdWrapper);
        } else {
            this.mDataConnectionState = 0;
            this.mDataStateIconId = R.drawable.ic_qs_mobile_off;
            this.mDisableStateIconIdWrapper.setResources(this.mContext.getResources());
            this.mDisableStateIconIdWrapper.setIconId(this.mDataStateIconId);
            state.label = this.mQuickSettingsExt.customizeDataConnectionTile(this.mDataConnectionState, this.mDisableStateIconIdWrapper, this.mContext.getString(R.string.mobile));
            state.icon = QsIconWrapper.get(this.mDisableStateIconIdWrapper.getIconId(), this.mDisableStateIconIdWrapper);
        }
        this.mTileLabel = state.label;
        Log.d(this.TAG, "handleUpdateState state=" + state);
    }

    private final boolean isDefaultDataSimRadioOn() {
        int subId = SubscriptionManager.getDefaultDataSubscriptionId();
        boolean isRadioOn = subId >= 0 && SIMHelper.isRadioOn(subId);
        Log.d(this.TAG, "isDefaultDataSimRadioOn subId=" + subId + ", isRadioOn=" + isRadioOn);
        return isRadioOn;
    }

    private static final class CallbackInfo {
        public boolean activityIn;
        public boolean activityOut;
        public boolean airplaneModeEnabled;
        public int dataTypeIconId;
        public boolean enabled;
        public String enabledDesc;
        public int mobileSignalIconId;
        public boolean noSim;
        public boolean wifiConnected;
        public boolean wifiEnabled;

        private CallbackInfo() {
        }

        public String toString() {
            return "CallbackInfo[enabled=" + this.enabled + ",wifiEnabled=" + this.wifiEnabled + ",wifiConnected=" + this.wifiConnected + ",airplaneModeEnabled=" + this.airplaneModeEnabled + ",mobileSignalIconId=" + this.mobileSignalIconId + ",dataTypeIconId=" + this.dataTypeIconId + ",activityIn=" + this.activityIn + ",activityOut=" + this.activityOut + ",enabledDesc=" + this.enabledDesc + ",noSim=" + this.noSim + ']';
        }
    }

    private final class MobileDataSignalCallback implements NetworkController.SignalCallback {
        final CallbackInfo mInfo;

        private MobileDataSignalCallback() {
            this.mInfo = new CallbackInfo();
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setWifiIndicators(boolean enabled, NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, boolean activityIn, boolean activityOut, String description, boolean isTransient, String statusLabel) {
            this.mInfo.wifiEnabled = enabled;
            this.mInfo.wifiConnected = qsIcon.visible;
            MobileDataTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setMobileDataIndicators(NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, int statusType, int networkIcon, int volteIcon, int qsType, boolean activityIn, boolean activityOut, String typeContentDescription, String description, boolean isWide, int subId, boolean roaming, boolean isDefaultData) {
            if (qsIcon == null) {
                return;
            }
            this.mInfo.enabled = qsIcon.visible;
            this.mInfo.mobileSignalIconId = qsIcon.icon;
            this.mInfo.dataTypeIconId = qsType;
            this.mInfo.activityIn = activityIn;
            this.mInfo.activityOut = activityOut;
            this.mInfo.enabledDesc = description;
            Log.d(MobileDataTile.this.TAG, "setMobileDataIndicators mInfo=" + this.mInfo);
            MobileDataTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setNoSims(boolean show, boolean simDetected) {
            this.mInfo.noSim = show;
            if (this.mInfo.noSim) {
                this.mInfo.mobileSignalIconId = 0;
                this.mInfo.dataTypeIconId = 0;
                this.mInfo.enabled = false;
                Log.d(MobileDataTile.this.TAG, "setNoSims noSim=" + show);
            }
            MobileDataTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setIsAirplaneMode(NetworkController.IconState icon) {
            this.mInfo.airplaneModeEnabled = icon.visible;
            if (this.mInfo.airplaneModeEnabled) {
                this.mInfo.mobileSignalIconId = 0;
                this.mInfo.dataTypeIconId = 0;
                this.mInfo.enabled = false;
            }
            MobileDataTile.this.refreshState(this.mInfo);
        }

        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setMobileDataEnabled(boolean enabled) {
            MobileDataTile.this.refreshState(this.mInfo);
        }
    }
}
