package com.android.systemui.classifier;

/* JADX INFO: loaded from: classes.dex */
public class EndPointRatioEvaluator {
    public static float evaluate(float value) {
        float evaluation = ((double) value) < 0.85d ? 0.0f + 1.0f : 0.0f;
        if (value < 0.75d) {
            evaluation += 1.0f;
        }
        if (value < 0.65d) {
            evaluation += 1.0f;
        }
        if (value < 0.55d) {
            evaluation += 1.0f;
        }
        if (value < 0.45d) {
            evaluation += 1.0f;
        }
        return ((double) value) < 0.35d ? evaluation + 1.0f : evaluation;
    }
}
