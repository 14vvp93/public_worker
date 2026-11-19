package com.android.systemui.classifier;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public abstract class Classifier {
    protected ClassifierData mClassifierData;

    public abstract String getTag();

    public void onTouchEvent(MotionEvent event) {
    }

    public void onSensorChanged(SensorEvent event) {
    }
}
