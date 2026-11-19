package com.android.systemui.recents.views;

/* JADX INFO: compiled from: TaskStackLayoutAlgorithm.java */
/* JADX INFO: loaded from: classes.dex */
class Range {
    float max;
    float min;
    float origin;
    final float relativeMax;
    final float relativeMin;

    public Range(float relMin, float relMax) {
        this.relativeMin = relMin;
        this.min = relMin;
        this.relativeMax = relMax;
        this.max = relMax;
    }

    public void offset(float x) {
        this.origin = x;
        this.min = this.relativeMin + x;
        this.max = this.relativeMax + x;
    }

    public float getNormalizedX(float x) {
        if (x < this.origin) {
            return 0.5f + (((x - this.origin) * 0.5f) / (-this.relativeMin));
        }
        return 0.5f + (((x - this.origin) * 0.5f) / this.relativeMax);
    }

    public float getAbsoluteX(float normX) {
        return normX < 0.5f ? ((normX - 0.5f) / 0.5f) * (-this.relativeMin) : ((normX - 0.5f) / 0.5f) * this.relativeMax;
    }

    public boolean isInRange(float absX) {
        return ((double) absX) >= Math.floor((double) this.min) && ((double) absX) <= Math.ceil((double) this.max);
    }
}
