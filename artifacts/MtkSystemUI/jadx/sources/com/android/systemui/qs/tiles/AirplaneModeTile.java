package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.SystemProperties;
import android.util.Log;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.GlobalSetting;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* JADX INFO: loaded from: classes.dex */
public class AirplaneModeTile extends QSTileImpl<QSTile.BooleanState> {
    private final QSTile.Icon mIcon;
    private boolean mListening;
    private final BroadcastReceiver mReceiver;
    private final GlobalSetting mSetting;

    public AirplaneModeTile(QSHost host) {
        super(host);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.ic_signal_airplane);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                    AirplaneModeTile.this.refreshState();
                }
            }
        };
        this.mSetting = new GlobalSetting(this.mContext, this.mHandler, "airplane_mode_on") { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.1
            @Override // com.android.systemui.qs.GlobalSetting
            protected void handleValueChanged(int value) {
                if (AirplaneModeTile.DEBUG) {
                    Log.d(AirplaneModeTile.this.TAG, "Received Gloable changes to " + value);
                }
                AirplaneModeTile.this.handleRefreshState(Integer.valueOf(value));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState airPlaneTile = new QSTile.BooleanState();
        if (this.mSetting != null && this.mSetting.getValue() == 1) {
            airPlaneTile.value = true;
        }
        return airPlaneTile;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick() {
        boolean airplaneModeEnabled = ((QSTile.BooleanState) this.mState).value;
        MetricsLogger.action(this.mContext, getMetricsCategory(), !airplaneModeEnabled);
        if (!airplaneModeEnabled && Boolean.parseBoolean(SystemProperties.get("ril.cdma.inecmmode"))) {
            ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(new Intent("com.android.internal.intent.action.ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS"), 0);
            if (DEBUG) {
                Log.w(this.TAG, "AirplaneModeTile click not work!");
                return;
            }
            return;
        }
        setEnabled(!airplaneModeEnabled);
    }

    private void setEnabled(boolean enabled) {
        ConnectivityManager mgr = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        if (DEBUG) {
            Log.d(this.TAG, "AirplaneModeTile will set enable to " + enabled);
        }
        mgr.setAirplaneMode(enabled);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.airplane_mode);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        checkIfRestrictionEnforcedByAdminOnly(state, "no_airplane_mode");
        int value = arg instanceof Integer ? ((Integer) arg).intValue() : this.mSetting.getValue();
        boolean airplaneMode = value != 0;
        state.value = airplaneMode;
        state.label = this.mContext.getString(R.string.airplane_mode);
        state.icon = this.mIcon;
        if (state.slash == null) {
            state.slash = new QSTile.SlashState();
        }
        state.slash.isSlashed = airplaneMode ? false : true;
        state.state = airplaneMode ? 2 : 1;
        state.contentDescription = state.label;
        state.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected String composeChangeAnnouncement() {
        if (((QSTile.BooleanState) this.mState).value) {
            return this.mContext.getString(R.string.accessibility_quick_settings_airplane_changed_on);
        }
        return this.mContext.getString(R.string.accessibility_quick_settings_airplane_changed_off);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
        if (this.mListening == listening) {
            return;
        }
        this.mListening = listening;
        if (listening) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.AIRPLANE_MODE");
            this.mContext.registerReceiver(this.mReceiver, filter);
        } else {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
        this.mSetting.setListening(listening);
    }
}
