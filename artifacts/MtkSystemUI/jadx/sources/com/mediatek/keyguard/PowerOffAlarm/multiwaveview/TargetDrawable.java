package com.mediatek.keyguard.PowerOffAlarm.multiwaveview;

import android.R;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/* JADX INFO: loaded from: classes.dex */
public class TargetDrawable {
    private Drawable mDrawable;
    private int mNumDrawables;
    private final int mResourceId;
    public static final int[] STATE_ACTIVE = {R.attr.state_enabled, R.attr.state_active};
    public static final int[] STATE_INACTIVE = {R.attr.state_enabled, -16842914};
    public static final int[] STATE_FOCUSED = {R.attr.state_enabled, -16842914, R.attr.state_focused};
    private float mTranslationX = 0.0f;
    private float mTranslationY = 0.0f;
    private float mPositionX = 0.0f;
    private float mPositionY = 0.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mAlpha = 1.0f;
    private boolean mEnabled = true;

    public TargetDrawable(Resources res, int resId, int count) {
        this.mNumDrawables = 1;
        this.mResourceId = resId;
        setDrawable(res, resId);
        this.mNumDrawables = count;
    }

    public void setDrawable(Resources res, int resId) {
        Drawable drawable = resId == 0 ? null : res.getDrawable(resId);
        this.mDrawable = drawable != null ? drawable.mutate() : null;
        resizeDrawables();
        setState(STATE_INACTIVE);
    }

    public void setState(int[] state) {
        if (this.mDrawable instanceof StateListDrawable) {
            StateListDrawable d = (StateListDrawable) this.mDrawable;
            d.setState(state);
        }
    }

    public boolean isEnabled() {
        return this.mDrawable != null && this.mEnabled;
    }

    private void resizeDrawables() {
        if (!(this.mDrawable instanceof StateListDrawable)) {
            if (this.mDrawable != null) {
                this.mDrawable.setBounds(0, 0, this.mDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
                return;
            }
            return;
        }
        StateListDrawable d = (StateListDrawable) this.mDrawable;
        int maxHeight = 0;
        int maxWidth = 0;
        for (int maxWidth2 = 0; maxWidth2 < this.mNumDrawables; maxWidth2++) {
            d.selectDrawable(maxWidth2);
            Drawable childDrawable = d.getCurrent();
            maxWidth = Math.max(maxWidth, childDrawable.getIntrinsicWidth());
            maxHeight = Math.max(maxHeight, childDrawable.getIntrinsicHeight());
        }
        d.setBounds(0, 0, maxWidth, maxHeight);
        for (int i = 0; i < this.mNumDrawables; i++) {
            d.selectDrawable(i);
            d.getCurrent().setBounds(0, 0, maxWidth, maxHeight);
        }
    }

    public void setX(float x) {
        this.mTranslationX = x;
    }

    public void setY(float y) {
        this.mTranslationY = y;
    }

    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
    }

    public float getX() {
        return this.mTranslationX;
    }

    public float getY() {
        return this.mTranslationY;
    }

    public void setPositionX(float x) {
        this.mPositionX = x;
    }

    public void setPositionY(float y) {
        this.mPositionY = y;
    }

    public int getWidth() {
        if (this.mDrawable != null) {
            return this.mDrawable.getIntrinsicWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (this.mDrawable != null) {
            return this.mDrawable.getIntrinsicHeight();
        }
        return 0;
    }

    public void draw(Canvas canvas) {
        if (this.mDrawable == null || !this.mEnabled) {
            return;
        }
        canvas.save(1);
        canvas.scale(this.mScaleX, this.mScaleY, this.mPositionX, this.mPositionY);
        canvas.translate(this.mTranslationX + this.mPositionX, this.mTranslationY + this.mPositionY);
        canvas.translate(getWidth() * (-0.5f), (-0.5f) * getHeight());
        this.mDrawable.setAlpha(Math.round(this.mAlpha * 255.0f));
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public int getResourceId() {
        return this.mResourceId;
    }
}
