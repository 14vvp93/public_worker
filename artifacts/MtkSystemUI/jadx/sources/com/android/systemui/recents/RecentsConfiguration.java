package com.android.systemui.recents;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.os.SystemProperties;
import com.android.systemui.R;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.recents.views.DockState;

/* JADX INFO: loaded from: classes.dex */
public class RecentsConfiguration {
    public boolean dragToSplitEnabled;
    public boolean fakeShadows;
    public boolean isGridEnabled;
    public final boolean isLargeScreen;
    public boolean isLowRamDevice;
    public final boolean isXLargeScreen;
    private final Context mAppContext;
    public RecentsActivityLaunchState mLaunchState = new RecentsActivityLaunchState();
    public final int smallestWidth;
    public int svelteLevel;

    public RecentsConfiguration(Context context) {
        SystemServicesProxy ssp = Recents.getSystemServices();
        this.mAppContext = context.getApplicationContext();
        Resources res = this.mAppContext.getResources();
        this.fakeShadows = res.getBoolean(R.bool.config_recents_fake_shadows);
        this.svelteLevel = res.getInteger(R.integer.recents_svelte_level);
        this.isGridEnabled = SystemProperties.getBoolean("ro.recents.grid", false);
        this.isLowRamDevice = ActivityManager.isLowRamDeviceStatic();
        this.dragToSplitEnabled = !this.isLowRamDevice;
        long totalMemory = Process.getTotalMemory() / 1048576;
        this.isLowRamDevice = totalMemory <= 1024;
        float screenDensity = context.getResources().getDisplayMetrics().density;
        this.smallestWidth = ssp.getDeviceSmallestWidth();
        this.isLargeScreen = this.smallestWidth >= ((int) (600.0f * screenDensity));
        this.isXLargeScreen = this.smallestWidth >= ((int) (720.0f * screenDensity));
    }

    public RecentsActivityLaunchState getLaunchState() {
        return this.mLaunchState;
    }

    public DockState[] getDockStatesForCurrentOrientation() {
        boolean isLandscape = this.mAppContext.getResources().getConfiguration().orientation == 2;
        RecentsConfiguration config = Recents.getConfiguration();
        if (config.isLargeScreen) {
            return isLandscape ? DockRegion.TABLET_LANDSCAPE : DockRegion.TABLET_PORTRAIT;
        }
        return isLandscape ? DockRegion.PHONE_LANDSCAPE : DockRegion.PHONE_PORTRAIT;
    }
}
