package com.android.systemui.statusbar.policy;

import com.android.systemui.DemoMode;
import com.android.systemui.Dumpable;

/* JADX INFO: loaded from: classes.dex */
public interface BatteryController extends DemoMode, Dumpable, CallbackController<BatteryStateChangeCallback> {
    boolean isPowerSave();

    default boolean isAodPowerSave() {
        return isPowerSave();
    }

    public interface BatteryStateChangeCallback {
        default void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
        }

        default void onPowerSaveChanged(boolean isPowerSave) {
        }
    }
}
