package com.android.systemui.statusbar.phone;

import android.content.res.Resources;
import android.util.MathUtils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.NotificationUtils;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardClockPositionAlgorithm {
    private static float CLOCK_HEIGHT_WEIGHT = 0.7f;
    private int mBouncerTop;
    private int mBurnInPreventionOffsetX;
    private int mBurnInPreventionOffsetY;
    private int mClockNotificationsMargin;
    private int mContainerTopPadding;
    private boolean mCurrentlySecure;
    private float mDarkAmount;
    private int mHeight;
    private int mKeyguardStatusHeight;
    private int mMaxShadeBottom;
    private int mMinTopMargin;
    private int mNotificationStackHeight;
    private float mPanelExpansion;
    private boolean mPulsing;
    private int mPulsingPadding;

    public static class Result {
        public float clockAlpha;
        public int clockX;
        public int clockY;
        public int stackScrollerPadding;
    }

    public void loadDimens(Resources res) {
        this.mClockNotificationsMargin = res.getDimensionPixelSize(R.dimen.keyguard_clock_notifications_margin);
        this.mContainerTopPadding = res.getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
        this.mBurnInPreventionOffsetX = res.getDimensionPixelSize(R.dimen.burn_in_prevention_offset_x);
        this.mBurnInPreventionOffsetY = res.getDimensionPixelSize(R.dimen.burn_in_prevention_offset_y);
        this.mPulsingPadding = res.getDimensionPixelSize(R.dimen.widget_pulsing_bottom_padding);
    }

    public void setup(int minTopMargin, int maxShadeBottom, int notificationStackHeight, float panelExpansion, int parentHeight, int keyguardStatusHeight, float dark, boolean secure, boolean pulsing, int bouncerTop) {
        this.mMinTopMargin = this.mContainerTopPadding + minTopMargin;
        this.mMaxShadeBottom = maxShadeBottom;
        this.mNotificationStackHeight = notificationStackHeight;
        this.mPanelExpansion = panelExpansion;
        this.mHeight = parentHeight;
        this.mKeyguardStatusHeight = keyguardStatusHeight;
        this.mDarkAmount = dark;
        this.mCurrentlySecure = secure;
        this.mPulsing = pulsing;
        this.mBouncerTop = bouncerTop;
    }

    public void run(Result result) {
        int y = getClockY();
        result.clockY = y;
        result.clockAlpha = getClockAlpha(y);
        result.stackScrollerPadding = (this.mPulsing ? 0 : this.mKeyguardStatusHeight) + y;
        result.clockX = (int) NotificationUtils.interpolate(0.0f, burnInPreventionOffsetX(), this.mDarkAmount);
    }

    public float getMinStackScrollerPadding() {
        return this.mMinTopMargin + this.mKeyguardStatusHeight + this.mClockNotificationsMargin;
    }

    private int getMaxClockY() {
        return ((this.mHeight / 2) - this.mKeyguardStatusHeight) - this.mClockNotificationsMargin;
    }

    public int getExpandedClockPosition() {
        int availableHeight = this.mMaxShadeBottom - this.mMinTopMargin;
        int containerCenter = this.mMinTopMargin + (availableHeight / 2);
        float y = ((containerCenter - (this.mKeyguardStatusHeight * CLOCK_HEIGHT_WEIGHT)) - this.mClockNotificationsMargin) - (this.mNotificationStackHeight / 2);
        if (y < this.mMinTopMargin) {
            y = this.mMinTopMargin;
        }
        float maxClockY = getMaxClockY();
        if (y > maxClockY) {
            y = maxClockY;
        }
        return (int) y;
    }

    private int getClockY() {
        float clockYDark = getMaxClockY() + burnInPreventionOffsetY();
        if (this.mPulsing) {
            clockYDark -= this.mPulsingPadding;
        }
        float clockYRegular = getExpandedClockPosition();
        boolean hasEnoughSpace = this.mMinTopMargin + this.mKeyguardStatusHeight < this.mBouncerTop;
        float clockYTarget = (this.mCurrentlySecure && hasEnoughSpace) ? this.mMinTopMargin : -this.mKeyguardStatusHeight;
        float shadeExpansion = Interpolators.FAST_OUT_LINEAR_IN.getInterpolation(this.mPanelExpansion);
        float clockY = MathUtils.lerp(clockYTarget, clockYRegular, shadeExpansion);
        return (int) MathUtils.lerp(clockY, clockYDark, this.mDarkAmount);
    }

    private float getClockAlpha(int y) {
        float alphaKeyguard;
        if (this.mCurrentlySecure) {
            alphaKeyguard = 1.0f;
        } else {
            float alphaKeyguard2 = Math.max(0.0f, y / Math.max(1.0f, getExpandedClockPosition()));
            alphaKeyguard = Interpolators.ACCELERATE.getInterpolation(alphaKeyguard2);
        }
        return MathUtils.lerp(alphaKeyguard, 1.0f, this.mDarkAmount);
    }

    private float burnInPreventionOffsetY() {
        return zigzag(System.currentTimeMillis() / 60000, this.mBurnInPreventionOffsetY * 2, 521.0f) - this.mBurnInPreventionOffsetY;
    }

    private float burnInPreventionOffsetX() {
        return zigzag(System.currentTimeMillis() / 60000, this.mBurnInPreventionOffsetX * 2, 83.0f) - this.mBurnInPreventionOffsetX;
    }

    private float zigzag(float x, float amplitude, float period) {
        float xprime = (x % period) / (period / 2.0f);
        float interpolationAmount = xprime <= 1.0f ? xprime : 2.0f - xprime;
        return NotificationUtils.interpolate(0.0f, amplitude, interpolationAmount);
    }
}
