package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class CubeOutTransformer extends ABaseTransformer {
    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    public boolean isPagingEnabled() {
        return true;
    }

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        view.setPivotX(f < 0.0f ? view.getWidth() : 0.0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(f * 90.0f);
    }
}
