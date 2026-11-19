package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class AccordionTransformer extends ABaseTransformer {
    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        view.setPivotX(f >= 0.0f ? view.getWidth() : 0.0f);
        view.setScaleX(f < 0.0f ? f + 1.0f : 1.0f - f);
    }
}
