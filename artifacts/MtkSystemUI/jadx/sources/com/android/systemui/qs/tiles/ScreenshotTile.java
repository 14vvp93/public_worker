package com.android.systemui.qs.tiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import com.android.systemui.plugins.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* JADX INFO: loaded from: classes.dex */
public class ScreenshotTile extends QSTileImpl<QSTile.BooleanState> {

    @SuppressLint({"HandlerLeak"})
    Handler mHandler;

    public ScreenshotTile(QSHost host) {
        super(host);
        this.mHandler = new Handler() { // from class: com.android.systemui.qs.tiles.ScreenshotTile.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    long now = SystemClock.uptimeMillis();
                    KeyEvent down = new KeyEvent(now, now, 0, R.styleable.AppCompatTheme_windowNoTitle, 0);
                    InputManager.getInstance().injectInputEvent(down, 0);
                    KeyEvent up = new KeyEvent(now, now, 1, R.styleable.AppCompatTheme_windowNoTitle, 0);
                    InputManager.getInstance().injectInputEvent(up, 0);
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
        this.mHandler.sendMessageDelayed(msg, 1000L);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(com.android.systemui.R.string.quick_settings_screenshot_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        state.value = true;
        state.icon = QSTileImpl.ResourceIcon.get(com.android.systemui.R.drawable.ic_screenshot);
        state.label = this.mContext.getString(com.android.systemui.R.string.quick_settings_screenshot_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }
}
