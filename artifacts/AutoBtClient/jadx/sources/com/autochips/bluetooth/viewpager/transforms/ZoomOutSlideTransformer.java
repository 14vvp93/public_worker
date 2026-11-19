package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ZoomOutSlideTransformer extends ABaseTransformer {
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        if (f >= -1.0f || f <= 1.0f) {
            float height = view.getHeight();
            float fMax = Math.max(MIN_SCALE, 1.0f - Math.abs(f));
            float f2 = 1.0f - fMax;
            float f3 = (height * f2) / 2.0f;
            float width = (view.getWidth() * f2) / 2.0f;
            view.setPivotY(height * MIN_ALPHA);
            if (f < 0.0f) {
                view.setTranslationX(width - (f3 / 2.0f));
            } else {
                view.setTranslationX((-width) + (f3 / 2.0f));
            }
            view.setScaleX(fMax);
            view.setScaleY(fMax);
            view.setAlpha((((fMax - MIN_SCALE) / 0.14999998f) * MIN_ALPHA) + MIN_ALPHA);
        }
    }
}
