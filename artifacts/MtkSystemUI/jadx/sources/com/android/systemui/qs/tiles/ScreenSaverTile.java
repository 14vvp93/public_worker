package com.android.systemui.qs.tiles;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* JADX INFO: loaded from: classes.dex */
public class ScreenSaverTile extends QSTileImpl<QSTile.BooleanState> {

    @SuppressLint({"HandlerLeak"})
    Handler mHandler;

    public ScreenSaverTile(QSHost host) {
        super(host);
        this.mHandler = new Handler() { // from class: com.android.systemui.qs.tiles.ScreenSaverTile.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ScreenSaverTile.this.launchScreenSaverTask();
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
        state.icon = QSTileImpl.ResourceIcon.get(R.drawable.icon_screensaver);
        state.label = this.mContext.getString(R.string.quick_settings_screensaver_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchScreenSaverTask() {
        try {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(this.mContext.getString(R.string.quick_settings_screensaver_pkg), this.mContext.getString(R.string.quick_settings_screensaver_cls));
            intent.setComponent(componentName);
            intent.setFlags(268435456);
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.quick_settings_screensaver_message), 1).show();
        }
    }
}
