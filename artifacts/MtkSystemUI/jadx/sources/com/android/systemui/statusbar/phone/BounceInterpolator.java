package com.android.systemui.statusbar.phone;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
public class BounceInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        float t2 = t * 1.1f;
        if (t2 < 0.36363637f) {
            return 7.5625f * t2 * t2;
        }
        if (t2 < 0.72727275f) {
            float t3 = t2 - 0.54545456f;
            return (7.5625f * t3 * t3) + 0.75f;
        }
        if (t2 < 0.90909094f) {
            float t4 = t2 - 0.8181818f;
            return (7.5625f * t4 * t4) + 0.9375f;
        }
        return 1.0f;
    }
}
