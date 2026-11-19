package com.autochips.bluetooth.viewpager.transforms;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class StackTransformer extends ABaseTransformer {
    @Override // com.autochips.bluetooth.viewpager.transforms.ABaseTransformer
    protected void onTransform(View view, float f) {
        view.setTranslationX(f >= 0.0f ? (-view.getWidth()) * f : 0.0f);
    }
}
