package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SlashDrawable;

/* JADX INFO: loaded from: classes.dex */
public class SlashImageView extends ImageView {
    private boolean mAnimationEnabled;

    @VisibleForTesting
    protected SlashDrawable mSlash;

    public SlashImageView(Context context) {
        super(context);
        this.mAnimationEnabled = true;
    }

    protected SlashDrawable getSlash() {
        return this.mSlash;
    }

    protected void setSlash(SlashDrawable slash) {
        this.mSlash = slash;
    }

    protected void ensureSlashDrawable() {
        if (this.mSlash == null) {
            this.mSlash = new SlashDrawable(getDrawable());
            this.mSlash.setAnimationEnabled(this.mAnimationEnabled);
            super.setImageDrawable(this.mSlash);
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mSlash = null;
            super.setImageDrawable(null);
        } else if (this.mSlash == null) {
            setImageLevel(drawable.getLevel());
            super.setImageDrawable(drawable);
        } else {
            this.mSlash.setAnimationEnabled(this.mAnimationEnabled);
            this.mSlash.setDrawable(drawable);
        }
    }

    protected void setImageViewDrawable(SlashDrawable slash) {
        super.setImageDrawable(slash);
    }

    public void setAnimationEnabled(boolean enabled) {
        this.mAnimationEnabled = enabled;
    }

    public boolean getAnimationEnabled() {
        return this.mAnimationEnabled;
    }

    private void setSlashState(QSTile.SlashState slashState) {
        ensureSlashDrawable();
        this.mSlash.setRotation(slashState.rotation);
        this.mSlash.setSlashed(slashState.isSlashed);
    }

    public void setState(QSTile.SlashState state, Drawable drawable) {
        if (state != null) {
            setImageDrawable(drawable);
            setSlashState(state);
        } else {
            this.mSlash = null;
            setImageDrawable(drawable);
        }
    }
}
