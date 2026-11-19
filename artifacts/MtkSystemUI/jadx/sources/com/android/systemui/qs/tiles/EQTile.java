package com.android.systemui.qs.tiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* JADX INFO: loaded from: classes.dex */
public class EQTile extends QSTileImpl<QSTile.BooleanState> {

    @SuppressLint({"HandlerLeak"})
    Handler mHandler;

    public EQTile(QSHost host) {
        super(host);
        this.mHandler = new Handler() { // from class: com.android.systemui.qs.tiles.EQTile.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    EQTile.this.launchEQTask();
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
        return this.mContext.getString(R.string.quick_settings_eq_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        state.value = true;
        state.icon = QSTileImpl.ResourceIcon.get(R.drawable.icon_eq);
        state.label = this.mContext.getString(R.string.quick_settings_eq_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchEQTask() {
        try {
            String mPackageName = this.mContext.getString(R.string.quick_settings_eq_pkg);
            Intent launchIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(mPackageName);
            if (launchIntent != null) {
                launchIntent.addFlags(268435456);
                this.mContext.startActivity(launchIntent);
            }
        } catch (Exception e) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.quick_settings_eq_message), 1).show();
        }
    }
}
