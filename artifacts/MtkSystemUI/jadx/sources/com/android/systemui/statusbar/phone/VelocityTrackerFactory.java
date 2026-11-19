package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class VelocityTrackerFactory {
    /* JADX WARN: Code duplicated, block: B:13:0x002e  */
    public static VelocityTrackerInterface obtain(Context ctx) {
        String tracker = ctx.getResources().getString(R.string.velocity_tracker_impl);
        switch (tracker) {
            case "noisy":
                return NoisyVelocityTracker.obtain();
            case "platform":
                return PlatformVelocityTracker.obtain();
            default:
                throw new IllegalStateException("Invalid tracker: " + tracker);
        }
    }
}
