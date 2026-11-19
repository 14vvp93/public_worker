package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class CubeInTransformer extends ABaseTransformer {
    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    public boolean isPagingEnabled() {
        return true;
    }

    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        view.setPivotX(f > 0.0f ? 0.0f : view.getWidth());
        view.setPivotY(0.0f);
        view.setRotationY(f * (-90.0f));
    }
}
