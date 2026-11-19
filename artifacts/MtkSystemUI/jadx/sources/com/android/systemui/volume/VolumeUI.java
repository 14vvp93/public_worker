package com.android.systemui.volume;

import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.SystemUI;
import com.android.systemui.qs.tiles.DndTile;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class VolumeUI extends SystemUI {
    private static boolean LOGD = Log.isLoggable("VolumeUI", 3);
    private boolean mEnabled;
    private final Handler mHandler = new Handler();
    private VolumeDialogComponent mVolumeComponent;

    @Override // com.android.systemui.SystemUI
    public void start() {
        final boolean enableVolumeUi = this.mContext.getResources().getBoolean(R.bool.enable_volume_ui);
        final boolean enableSafetyWarning = this.mContext.getResources().getBoolean(R.bool.enable_safety_warning);
        this.mEnabled = enableVolumeUi || enableSafetyWarning;
        if (this.mEnabled) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$VolumeUI$u1bFWlb00zJgP--lDnXjw1Y-rw4
                @Override // java.lang.Runnable
                public final void run() {
                    VolumeUI.lambda$start$0(this.f$0, enableVolumeUi, enableSafetyWarning);
                }
            }, 4000L);
        }
    }

    public static /* synthetic */ void lambda$start$0(VolumeUI volumeUI, boolean enableVolumeUi, boolean enableSafetyWarning) {
        volumeUI.mVolumeComponent = new VolumeDialogComponent(volumeUI, volumeUI.mContext, null);
        volumeUI.mVolumeComponent.setEnableDialogs(enableVolumeUi, enableSafetyWarning);
        volumeUI.putComponent(VolumeComponent.class, volumeUI.getVolumeComponent());
        volumeUI.setDefaultVolumeController();
    }

    private VolumeComponent getVolumeComponent() {
        return this.mVolumeComponent;
    }

    @Override // com.android.systemui.SystemUI
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!this.mEnabled || getVolumeComponent() == null) {
            return;
        }
        getVolumeComponent().onConfigurationChanged(newConfig);
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("mEnabled=");
        pw.println(this.mEnabled);
        if (this.mEnabled) {
            getVolumeComponent().dump(fd, pw, args);
        }
    }

    private void setDefaultVolumeController() {
        DndTile.setVisible(this.mContext, true);
        if (LOGD) {
            Log.d("VolumeUI", "Registering default volume controller");
        }
        getVolumeComponent().register();
    }
}
