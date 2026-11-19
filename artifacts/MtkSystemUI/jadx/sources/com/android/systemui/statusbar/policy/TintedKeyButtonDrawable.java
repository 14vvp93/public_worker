package com.android.systemui.statusbar.policy;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.android.internal.graphics.ColorUtils;

/* JADX INFO: loaded from: classes.dex */
public class TintedKeyButtonDrawable extends KeyButtonDrawable {
    private final int mDarkColor;
    private float mDarkIntensity;
    private final int mLightColor;

    public static TintedKeyButtonDrawable create(Drawable drawable, int lightColor, int darkColor) {
        return new TintedKeyButtonDrawable(new Drawable[]{drawable}, lightColor, darkColor);
    }

    private TintedKeyButtonDrawable(Drawable[] drawables, int lightColor, int darkColor) {
        super(drawables);
        this.mDarkIntensity = -1.0f;
        this.mLightColor = lightColor;
        this.mDarkColor = darkColor;
        setDarkIntensity(0.0f);
    }

    @Override // com.android.systemui.statusbar.policy.KeyButtonDrawable
    public void setDarkIntensity(float intensity) {
        this.mDarkIntensity = intensity;
        int intermediateColor = ColorUtils.compositeColors(blendAlpha(this.mDarkColor, intensity), blendAlpha(this.mLightColor, 1.0f - intensity));
        getDrawable(0).setTint(intermediateColor);
        invalidateSelf();
    }

    private int blendAlpha(int color, float alpha) {
        float newAlpha = 0.0f;
        if (alpha >= 0.0f) {
            newAlpha = alpha > 1.0f ? 1.0f : alpha;
        }
        float colorAlpha = Color.alpha(color) / 255.0f;
        int alphaInt = (int) (255.0f * newAlpha * colorAlpha);
        return ColorUtils.setAlphaComponent(color, alphaInt);
    }

    public boolean isDarkIntensitySet() {
        return this.mDarkIntensity != -1.0f;
    }

    public float getDarkIntensity() {
        return this.mDarkIntensity;
    }
}
