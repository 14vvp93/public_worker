package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ShadowKeyDrawable;

/* JADX INFO: loaded from: classes.dex */
public class KeyButtonDrawable extends LayerDrawable {
    private final boolean mHasDarkDrawable;

    public static KeyButtonDrawable create(Context lightContext, Drawable lightDrawable, Drawable darkDrawable, boolean hasShadow) {
        if (darkDrawable != null) {
            ShadowKeyDrawable light = new ShadowKeyDrawable(lightDrawable.mutate());
            ShadowKeyDrawable dark = new ShadowKeyDrawable(darkDrawable.mutate());
            if (hasShadow) {
                Resources res = lightContext.getResources();
                int offsetX = res.getDimensionPixelSize(R.dimen.nav_key_button_shadow_offset_x);
                int offsetY = res.getDimensionPixelSize(R.dimen.nav_key_button_shadow_offset_y);
                int radius = res.getDimensionPixelSize(R.dimen.nav_key_button_shadow_radius);
                int color = lightContext.getColor(R.color.nav_key_button_shadow_color);
                light.setShadowProperties(offsetX, offsetY, radius, color);
            }
            return new KeyButtonDrawable(new Drawable[]{light, dark});
        }
        return new KeyButtonDrawable(new Drawable[]{new ShadowKeyDrawable(lightDrawable.mutate())});
    }

    protected KeyButtonDrawable(Drawable[] drawables) {
        super(drawables);
        for (int i = 0; i < drawables.length; i++) {
            setLayerGravity(i, 17);
        }
        mutate();
        this.mHasDarkDrawable = drawables.length > 1;
        setDarkIntensity(0.0f);
    }

    public void setDarkIntensity(float intensity) {
        if (!this.mHasDarkDrawable) {
            return;
        }
        getDrawable(0).setAlpha((int) ((1.0f - intensity) * 255.0f));
        getDrawable(1).setAlpha((int) (255.0f * intensity));
        invalidateSelf();
    }

    public void setRotation(float degrees) {
        if (getDrawable(0) instanceof ShadowKeyDrawable) {
            ((ShadowKeyDrawable) getDrawable(0)).setRotation(degrees);
        }
        if (this.mHasDarkDrawable && (getDrawable(1) instanceof ShadowKeyDrawable)) {
            ((ShadowKeyDrawable) getDrawable(1)).setRotation(degrees);
        }
    }
}
