package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/* JADX INFO: loaded from: classes.dex */
public class NeutralGoodDrawable extends LayerDrawable {
    public static NeutralGoodDrawable create(Context light, Context dark, int resId) {
        return new NeutralGoodDrawable(new Drawable[]{light.getDrawable(resId).mutate(), dark.getDrawable(resId).mutate()});
    }

    protected NeutralGoodDrawable(Drawable[] drawables) {
        super(drawables);
        for (int i = 0; i < drawables.length; i++) {
            setLayerGravity(i, 17);
        }
        mutate();
        setDarkIntensity(0.0f);
    }

    public void setDarkIntensity(float intensity) {
        getDrawable(0).setAlpha((int) ((1.0f - intensity) * 255.0f));
        getDrawable(1).setAlpha((int) (255.0f * intensity));
        invalidateSelf();
    }
}
