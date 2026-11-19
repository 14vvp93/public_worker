package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class RotateUpTransformer extends ABaseTransformer {
    private static final float ROT_MOD = -15.0f;

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected boolean isPagingEnabled() {
        return true;
    }

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        float width = view.getWidth();
        float f2 = f * ROT_MOD;
        view.setPivotX(width * 0.5f);
        view.setPivotY(0.0f);
        view.setTranslationX(0.0f);
        view.setRotation(f2);
    }
}
