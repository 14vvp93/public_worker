package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.view.View;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public final class PhoneStatusBarTransitions extends BarTransitions {
    private Animator mCurrentAnimation;
    private final float mIconAlphaWhenOpaque;
    private View mLeftSide;
    private View mStatusIcons;
    private final PhoneStatusBarView mView;

    public PhoneStatusBarTransitions(PhoneStatusBarView view) {
        super(view, R.drawable.status_background);
        this.mView = view;
        Resources res = this.mView.getContext().getResources();
        this.mIconAlphaWhenOpaque = res.getFraction(R.dimen.status_bar_icon_drawing_alpha, 1, 1);
    }

    public void init() {
        this.mLeftSide = this.mView.findViewById(R.id.status_bar_left_side);
        this.mStatusIcons = this.mView.findViewById(R.id.statusIcons);
        applyModeBackground(-1, getMode(), false);
        applyMode(getMode(), false);
    }

    public ObjectAnimator animateTransitionTo(View v, float toAlpha) {
        return ObjectAnimator.ofFloat(v, "alpha", v.getAlpha(), toAlpha);
    }

    private float getNonBatteryClockAlphaFor(int mode) {
        if (isLightsOut(mode)) {
            return 0.0f;
        }
        if (isOpaque(mode)) {
            return this.mIconAlphaWhenOpaque;
        }
        return 1.0f;
    }

    private float getBatteryClockAlpha(int mode) {
        if (isLightsOut(mode)) {
            return 0.5f;
        }
        return getNonBatteryClockAlphaFor(mode);
    }

    private boolean isOpaque(int mode) {
        return (mode == 1 || mode == 2 || mode == 4 || mode == 6) ? false : true;
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    protected void onTransition(int oldMode, int newMode, boolean animate) {
        super.onTransition(oldMode, newMode, animate);
        applyMode(newMode, animate);
    }

    private void applyMode(int mode, boolean animate) {
        if (this.mLeftSide == null) {
            return;
        }
        float newAlpha = getNonBatteryClockAlphaFor(mode);
        getBatteryClockAlpha(mode);
        if (this.mCurrentAnimation != null) {
            this.mCurrentAnimation.cancel();
        }
        if (animate) {
            AnimatorSet anims = new AnimatorSet();
            anims.playTogether(animateTransitionTo(this.mLeftSide, newAlpha), animateTransitionTo(this.mStatusIcons, newAlpha));
            if (isLightsOut(mode)) {
                anims.setDuration(1500L);
            }
            anims.start();
            this.mCurrentAnimation = anims;
            return;
        }
        this.mLeftSide.setAlpha(newAlpha);
        this.mStatusIcons.setAlpha(newAlpha);
    }
}
