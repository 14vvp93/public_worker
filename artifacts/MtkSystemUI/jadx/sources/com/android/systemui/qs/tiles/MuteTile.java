package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;
import com.android.systemui.plugins.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* JADX INFO: loaded from: classes.dex */
public class MuteTile extends QSTileImpl<QSTile.BooleanState> {
    private BroadcastReceiver volumeReceiver;

    public MuteTile(QSHost host) {
        super(host);
        this.volumeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.MuteTile.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.media.STREAM_MUTE_CHANGED_ACTION".equals(action)) {
                    int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                    boolean volumeMuted = intent.getBooleanExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", false);
                    Log.d(MuteTile.this.TAG, "onReceive streamType : " + streamType + ", volumeMuted : " + volumeMuted);
                    if (streamType == 3) {
                        MuteTile.this.refreshState(Boolean.valueOf(volumeMuted));
                    }
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
        if (listening) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            this.mContext.registerReceiver(this.volumeReceiver, filter);
            return;
        }
        this.mContext.unregisterReceiver(this.volumeReceiver);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        audioManager.adjustStreamVolume(3, R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle, 1);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(com.android.systemui.R.string.status_bar_settings_mute_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        boolean muted;
        if (arg == null) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            muted = audioManager.isStreamMute(3);
        } else {
            muted = ((Boolean) arg).booleanValue();
        }
        state.value = muted;
        state.icon = QSTileImpl.ResourceIcon.get(muted ? com.android.systemui.R.drawable.ic_qs_volume_off : com.android.systemui.R.drawable.ic_qs_volume_up);
        state.label = this.mContext.getString(com.android.systemui.R.string.status_bar_settings_mute_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }
}
