package com.hcn.config;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class Feature {
    private static Feature S_INSTANCE;
    private long mFeatureMark = 0;

    public interface BIT {
        public static final int COMPACT_NOTIFICATION = 32;
        public static final int DOUBLE_KNOB_SCREEN = 4;
        public static final int NONE = 0;
        public static final int REMOTE_CONTROL_FOCUS = 1;
        public static final int SUPPORT_NO_FILE_TIMEOUT_EXIT = 16;
        public static final int SUPPORT_SEEKBAR_TOUCH = 8;
        public static final int THEME_SUPPORT_NIGHT_MODE = 2;
    }

    public static Feature instance() {
        Feature feature;
        Feature feature2 = S_INSTANCE;
        if (feature2 != null) {
            return feature2;
        }
        synchronized (Feature.class) {
            if (S_INSTANCE == null) {
                S_INSTANCE = new Feature();
            }
            feature = S_INSTANCE;
        }
        return feature;
    }

    private Feature() {
        if (Customer.FOTA_FEATURE.equals("AJ1501-OB")) {
            addFeature(1);
        }
        if (Customer.NAVIGATION_BAR_HEIGHT > 1 && !Customer.FOTA_FEATURE.contains("ZA00")) {
            addFeature(4);
        }
        if (Build.VERSION.SDK_INT >= 33) {
            addFeature(32);
        }
        addFeature(16);
    }

    public boolean hasFeature(int i) {
        long j = i;
        return (this.mFeatureMark & j) == j;
    }

    public void addFeature(int i) {
        this.mFeatureMark |= (long) i;
    }

    public void removeFeature(int i) {
        this.mFeatureMark &= (long) (~i);
    }
}
