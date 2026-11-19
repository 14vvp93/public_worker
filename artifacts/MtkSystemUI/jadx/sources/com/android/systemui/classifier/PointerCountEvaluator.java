package com.android.systemui.classifier;

/* JADX INFO: loaded from: classes.dex */
public class PointerCountEvaluator {
    public static float evaluate(int value) {
        return (value - 1) * (value - 1);
    }
}
