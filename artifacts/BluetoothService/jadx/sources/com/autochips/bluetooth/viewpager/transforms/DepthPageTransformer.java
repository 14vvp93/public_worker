package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class DepthPageTransformer extends ABaseTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected boolean isPagingEnabled() {
        return true;
    }

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        if (f <= 0.0f) {
            view.setVisibility(0);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            return;
        }
        if (f >= 1.0f) {
            if (f >= 1.0f) {
                view.setVisibility(4);
                return;
            }
            return;
        }
        float fAbs = ((1.0f - Math.abs(f)) * 0.25f) + MIN_SCALE;
        view.setVisibility(0);
        view.setAlpha(1.0f - f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setTranslationX(view.getWidth() * (-f));
        view.setScaleX(fAbs);
        view.setScaleY(fAbs);
    }
}
