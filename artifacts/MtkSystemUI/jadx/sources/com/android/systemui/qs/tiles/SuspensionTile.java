package com.android.systemui.qs.tiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* JADX INFO: loaded from: classes.dex */
public class SuspensionTile extends QSTileImpl<QSTile.BooleanState> {

    @SuppressLint({"HandlerLeak"})
    Handler mHandler;

    public SuspensionTile(QSHost host) {
        super(host);
        this.mHandler = new Handler() { // from class: com.android.systemui.qs.tiles.SuspensionTile.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    SuspensionTile.this.launchSuspensionTask();
                }
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        this.mHost.collapsePanels();
        Message msg = this.mHandler.obtainMessage(1);
        this.mHandler.sendMessageDelayed(msg, 1500L);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_suspension_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        int currentSettiingValue = Settings.System.getInt(this.mContext.getContentResolver(), "shortcut_setting", 0);
        state.value = currentSettiingValue == 1;
        state.icon = QSTileImpl.ResourceIcon.get(R.drawable.icon_suspension);
        if (state.value) {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.icon_suspension_p);
            state.label = this.mContext.getString(R.string.quick_settings_suspension_label) + ": " + this.mContext.getString(R.string.quick_settings_suspension_open);
            return;
        }
        state.icon = QSTileImpl.ResourceIcon.get(R.drawable.icon_suspension_n);
        state.label = this.mContext.getString(R.string.quick_settings_suspension_label) + ": " + this.mContext.getString(R.string.quick_settings_suspension_close);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchSuspensionTask() {
        try {
            int currentSettiingValue = Settings.System.getInt(this.mContext.getContentResolver(), "shortcut_setting", 0);
            int newSettiingValue = currentSettiingValue == 0 ? 1 : 0;
            Intent mSCutIntent = new Intent(this.mContext.getString(R.string.quick_settings_suspension_action_shortcut_status));
            mSCutIntent.setPackage(this.mContext.getString(R.string.quick_settings_suspension_pkg));
            this.mContext.sendBroadcast(mSCutIntent);
            Settings.System.putInt(this.mContext.getContentResolver(), "shortcut_setting", newSettiingValue);
            refreshState();
        } catch (Exception e) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.quick_settings_suspension_message), 1).show();
        }
    }
}
