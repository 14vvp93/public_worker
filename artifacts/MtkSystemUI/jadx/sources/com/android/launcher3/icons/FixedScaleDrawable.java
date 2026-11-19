package com.android.launcher3.icons;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import com.hcn.skin.support.resources.SkinCompatResources;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public class FixedScaleDrawable extends DrawableWrapper {
    private float mScaleX;
    private float mScaleY;
    private float mTranslateX;
    private float mTranslateY;

    public FixedScaleDrawable() {
        super(new ColorDrawable());
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mTranslateX = SkinCompatResources.getInstance().getInteger("icon_translate_x");
        this.mTranslateY = SkinCompatResources.getInstance().getInteger("icon_translate_y");
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int saveCount = canvas.save();
        canvas.translate(this.mTranslateX, this.mTranslateY);
        canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
        super.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) {
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) {
    }

    public void setScale(float scale) {
        float h = getIntrinsicHeight();
        float w = getIntrinsicWidth();
        this.mScaleX = scale * 1.0f;
        this.mScaleY = SkinCompatResources.getInstance().getInteger("icon_size_scale_y");
        if (this.mScaleY != 0.0f) {
            this.mScaleY = this.mScaleY * 0.01f * 0.6667f * 1.0f;
        } else {
            this.mScaleY = this.mScaleX;
        }
        if (h > w && w > 0.0f) {
            this.mScaleX *= w / h;
        } else if (w > h && h > 0.0f) {
            this.mScaleY *= h / w;
        }
    }
}
