package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* JADX INFO: loaded from: classes.dex */
public class RebootTile extends QSTileImpl<QSTile.BooleanState> {
    private final QSTile.Icon mIcon;

    public RebootTile(QSHost host) {
        super(host);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.ic_baseline_refresh_24);
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
        SystemUIDialog builder = new SystemUIDialog(this.mContext, 5);
        builder.setTitle(R.string.reboot);
        builder.setMessage(R.string.reboot_msg);
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.RebootTile.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (!ActivityManager.isUserAMonkey()) {
                    AudioManager audioManager = (AudioManager) RebootTile.this.mContext.getSystemService("audio");
                    audioManager.adjustStreamVolume(3, -100, 0);
                    PowerManager pManager = (PowerManager) RebootTile.this.mContext.getSystemService("power");
                    pManager.reboot("Reboot");
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.RebootTile.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.reboot);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        state.value = true;
        state.icon = this.mIcon;
        state.label = this.mContext.getString(R.string.reboot);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }
}
