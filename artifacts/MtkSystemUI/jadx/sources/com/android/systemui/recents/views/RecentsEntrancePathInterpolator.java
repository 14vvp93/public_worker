package com.android.systemui.recents.views;

import android.view.animation.PathInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class RecentsEntrancePathInterpolator extends PathInterpolator {
    final float mStartOffsetFraction;

    public RecentsEntrancePathInterpolator(float controlX1, float controlY1, float controlX2, float controlY2, float startOffsetFraction) {
        super(controlX1, controlY1, controlX2, controlY2);
        this.mStartOffsetFraction = startOffsetFraction;
    }

    @Override // android.view.animation.PathInterpolator, android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return super.getInterpolation(this.mStartOffsetFraction + t);
    }
}
