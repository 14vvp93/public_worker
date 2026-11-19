package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class FlipVerticalTransformer extends ABaseTransformer {
    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        float f2 = f * (-180.0f);
        view.setAlpha((f2 > 90.0f || f2 < -90.0f) ? 0.0f : 1.0f);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationX(f2);
    }
}
