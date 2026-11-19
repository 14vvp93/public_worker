package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.statusbar.notification.FakeShadowView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.phone.DoubleTapHelper;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivatableNotificationView extends ExpandableOutlineView {
    private final AccessibilityManager mAccessibilityManager;
    private boolean mActivated;
    private float mAnimationTranslationY;
    private float mAppearAnimationFraction;
    private RectF mAppearAnimationRect;
    private float mAppearAnimationTranslation;
    private ValueAnimator mAppearAnimator;
    private ObjectAnimator mBackgroundAnimator;
    private ValueAnimator mBackgroundColorAnimator;
    private NotificationBackgroundView mBackgroundDimmed;
    protected NotificationBackgroundView mBackgroundNormal;
    private ValueAnimator.AnimatorUpdateListener mBackgroundVisibilityUpdater;
    private float mBgAlpha;
    protected int mBgTint;
    private boolean mBlockNextTouch;
    private Interpolator mCurrentAlphaInterpolator;
    private Interpolator mCurrentAppearInterpolator;
    private int mCurrentBackgroundTint;
    private boolean mDark;
    private boolean mDimmed;
    private int mDimmedAlpha;
    private float mDimmedBackgroundFadeInAmount;
    private final DoubleTapHelper mDoubleTapHelper;
    private boolean mDrawingAppearAnimation;
    private AnimatorListenerAdapter mFadeInEndListener;
    private ValueAnimator mFadeInFromDarkAnimator;
    private FakeShadowView mFakeShadow;
    private FalsingManager mFalsingManager;
    private int mHeadsUpAddStartLocation;
    private float mHeadsUpLocation;
    private boolean mIsAppearing;
    private boolean mIsBelowSpeedBump;
    private boolean mIsHeadsUpAnimation;
    private boolean mNeedsDimming;
    private float mNormalBackgroundVisibilityAmount;
    private final int mNormalColor;
    protected final int mNormalRippleColor;
    private OnActivatedListener mOnActivatedListener;
    private float mOverrideAmount;
    private int mOverrideTint;
    private float mShadowAlpha;
    private boolean mShadowHidden;
    private final Interpolator mSlowOutFastInInterpolator;
    private final Interpolator mSlowOutLinearInInterpolator;
    private int mStartTint;
    private final Runnable mTapTimeoutRunnable;
    private int mTargetTint;
    private final int mTintedRippleColor;
    private ValueAnimator.AnimatorUpdateListener mUpdateOutlineListener;
    private static final Interpolator ACTIVATE_INVERSE_INTERPOLATOR = new PathInterpolator(0.6f, 0.0f, 0.5f, 1.0f);
    private static final Interpolator ACTIVATE_INVERSE_ALPHA_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.5f, 1.0f);

    public interface OnActivatedListener {
        void onActivated(ActivatableNotificationView activatableNotificationView);

        void onActivationReset(ActivatableNotificationView activatableNotificationView);
    }

    protected abstract View getContentView();

    public ActivatableNotificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBgTint = 0;
        this.mBgAlpha = 1.0f;
        this.mAppearAnimationRect = new RectF();
        this.mAppearAnimationFraction = -1.0f;
        this.mDimmedBackgroundFadeInAmount = -1.0f;
        this.mBackgroundVisibilityUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ActivatableNotificationView.this.setNormalBackgroundVisibilityAmount(ActivatableNotificationView.this.mBackgroundNormal.getAlpha());
                ActivatableNotificationView.this.mDimmedBackgroundFadeInAmount = ActivatableNotificationView.this.mBackgroundDimmed.getAlpha();
            }
        };
        this.mFadeInEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ActivatableNotificationView.this.mFadeInFromDarkAnimator = null;
                ActivatableNotificationView.this.mDimmedBackgroundFadeInAmount = -1.0f;
                ActivatableNotificationView.this.updateBackground();
            }
        };
        this.mUpdateOutlineListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ActivatableNotificationView.this.updateOutlineAlpha();
            }
        };
        this.mShadowAlpha = 1.0f;
        this.mTapTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.4
            @Override // java.lang.Runnable
            public void run() {
                ActivatableNotificationView.this.makeInactive(true);
            }
        };
        this.mSlowOutFastInInterpolator = new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
        this.mSlowOutLinearInInterpolator = new PathInterpolator(0.8f, 0.0f, 1.0f, 1.0f);
        setClipChildren(false);
        setClipToPadding(false);
        this.mNormalColor = context.getColor(R.color.notification_material_background_color);
        this.mTintedRippleColor = context.getColor(R.color.notification_ripple_tinted_color);
        this.mNormalRippleColor = context.getColor(R.color.notification_ripple_untinted_color);
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mAccessibilityManager = AccessibilityManager.getInstance(this.mContext);
        DoubleTapHelper.ActivationListener activationListener = new DoubleTapHelper.ActivationListener() { // from class: com.android.systemui.statusbar.-$$Lambda$ActivatableNotificationView$UY9VuuIVVnbnmWBGvyVyrd85Q1w
            @Override // com.android.systemui.statusbar.phone.DoubleTapHelper.ActivationListener
            public final void onActiveChanged(boolean z) {
                ActivatableNotificationView.lambda$new$0(this.f$0, z);
            }
        };
        DoubleTapHelper.DoubleTapListener doubleTapListener = new DoubleTapHelper.DoubleTapListener() { // from class: com.android.systemui.statusbar.-$$Lambda$ActivatableNotificationView$s9Lg6gd_pJUKy0ZrSBDjqcS3dhE
            @Override // com.android.systemui.statusbar.phone.DoubleTapHelper.DoubleTapListener
            public final boolean onDoubleTap() {
                return super/*com.android.systemui.statusbar.ExpandableOutlineView*/.performClick();
            }
        };
        DoubleTapHelper.SlideBackListener slideBackListener = new DoubleTapHelper.SlideBackListener() { // from class: com.android.systemui.statusbar.-$$Lambda$S0gLtMwWSvptmQSsp_EwTT5xQHY
            @Override // com.android.systemui.statusbar.phone.DoubleTapHelper.SlideBackListener
            public final boolean onSlideBack() {
                return this.f$0.handleSlideBack();
            }
        };
        final FalsingManager falsingManager = this.mFalsingManager;
        Objects.requireNonNull(falsingManager);
        this.mDoubleTapHelper = new DoubleTapHelper(this, activationListener, doubleTapListener, slideBackListener, new DoubleTapHelper.DoubleTapLogListener() { // from class: com.android.systemui.statusbar.-$$Lambda$kuG3qm72pLcP-jShb_m2hOhoaKY
            @Override // com.android.systemui.statusbar.phone.DoubleTapHelper.DoubleTapLogListener
            public final void onDoubleTapLog(boolean z, float f, float f2) {
                falsingManager.onNotificationDoubleTap(z, f, f2);
            }
        });
        initDimens();
    }

    public static /* synthetic */ void lambda$new$0(ActivatableNotificationView activatableNotificationView, boolean active) {
        if (active) {
            activatableNotificationView.makeActive();
        } else {
            activatableNotificationView.makeInactive(true);
        }
    }

    private void initDimens() {
        this.mHeadsUpAddStartLocation = getResources().getDimensionPixelSize(android.R.dimen.conversation_compact_face_pile_size);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    public void onDensityOrFontScaleChanged() {
        super.onDensityOrFontScaleChanged();
        initDimens();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBackgroundNormal = (NotificationBackgroundView) findViewById(R.id.backgroundNormal);
        this.mFakeShadow = (FakeShadowView) findViewById(R.id.fake_shadow);
        this.mShadowHidden = this.mFakeShadow.getVisibility() != 0;
        this.mBackgroundDimmed = (NotificationBackgroundView) findViewById(R.id.backgroundDimmed);
        this.mDimmedAlpha = Color.alpha(this.mContext.getColor(R.color.notification_material_background_dimmed_color));
        initBackground();
        updateBackground();
        updateBackgroundTint();
        updateOutlineAlpha();
    }

    protected void initBackground() {
        this.mBackgroundNormal.setCustomBackground(R.drawable.notification_material_bg);
        this.mBackgroundDimmed.setCustomBackground(R.drawable.notification_material_bg_dim);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mNeedsDimming && ev.getActionMasked() == 0 && disallowSingleClick(ev) && !isTouchExplorationEnabled()) {
            if (!this.mActivated) {
                return true;
            }
            if (!this.mDoubleTapHelper.isWithinDoubleTapSlop(ev)) {
                this.mBlockNextTouch = true;
                makeInactive(true);
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isTouchExplorationEnabled() {
        return this.mAccessibilityManager.isTouchExplorationEnabled();
    }

    protected boolean disallowSingleClick(MotionEvent ev) {
        return false;
    }

    protected boolean handleSlideBack() {
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        boolean result;
        if (this.mBlockNextTouch) {
            this.mBlockNextTouch = false;
            return false;
        }
        if (this.mNeedsDimming && !isTouchExplorationEnabled() && isInteractive()) {
            boolean wasActivated = this.mActivated;
            result = handleTouchEventDimmed(event);
            if (wasActivated && result && event.getAction() == 1) {
                removeCallbacks(this.mTapTimeoutRunnable);
            }
        } else {
            result = super.onTouchEvent(event);
        }
        return result;
    }

    protected boolean isInteractive() {
        return true;
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float x, float y) {
        if (!this.mDimmed) {
            this.mBackgroundNormal.drawableHotspotChanged(x, y);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mDimmed) {
            this.mBackgroundDimmed.setState(getDrawableState());
        } else {
            this.mBackgroundNormal.setState(getDrawableState());
        }
    }

    public void setRippleAllowed(boolean allowed) {
        this.mBackgroundNormal.setPressedAllowed(allowed);
    }

    private boolean handleTouchEventDimmed(MotionEvent event) {
        if (this.mNeedsDimming && !this.mDimmed) {
            super.onTouchEvent(event);
        }
        return this.mDoubleTapHelper.onTouchEvent(event, getActualHeight());
    }

    @Override // android.view.View
    public boolean performClick() {
        if (!this.mNeedsDimming || isTouchExplorationEnabled()) {
            return super.performClick();
        }
        return false;
    }

    private void makeActive() {
        this.mFalsingManager.onNotificationActive();
        startActivateAnimation(false);
        this.mActivated = true;
        if (this.mOnActivatedListener != null) {
            this.mOnActivatedListener.onActivated(this);
        }
    }

    private void startActivateAnimation(final boolean reverse) {
        Interpolator interpolator;
        Interpolator alphaInterpolator;
        if (!isAttachedToWindow() || !isDimmable()) {
            return;
        }
        int widthHalf = this.mBackgroundNormal.getWidth() / 2;
        int heightHalf = this.mBackgroundNormal.getActualHeight() / 2;
        float radius = (float) Math.sqrt((widthHalf * widthHalf) + (heightHalf * heightHalf));
        float f = 0.0f;
        Animator animator = reverse ? ViewAnimationUtils.createCircularReveal(this.mBackgroundNormal, widthHalf, heightHalf, radius, 0.0f) : ViewAnimationUtils.createCircularReveal(this.mBackgroundNormal, widthHalf, heightHalf, 0.0f, radius);
        this.mBackgroundNormal.setVisibility(0);
        if (!reverse) {
            interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
            alphaInterpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        } else {
            interpolator = ACTIVATE_INVERSE_INTERPOLATOR;
            alphaInterpolator = ACTIVATE_INVERSE_ALPHA_INTERPOLATOR;
        }
        animator.setInterpolator(interpolator);
        animator.setDuration(220L);
        if (reverse) {
            this.mBackgroundNormal.setAlpha(1.0f);
            animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    ActivatableNotificationView.this.updateBackground();
                }
            });
            animator.start();
        } else {
            this.mBackgroundNormal.setAlpha(0.4f);
            animator.start();
        }
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mBackgroundNormal.animate();
        if (!reverse) {
            f = 1.0f;
        }
        viewPropertyAnimatorAnimate.alpha(f).setInterpolator(alphaInterpolator).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                if (reverse) {
                    animatedFraction = 1.0f - animatedFraction;
                }
                ActivatableNotificationView.this.setNormalBackgroundVisibilityAmount(animatedFraction);
            }
        }).setDuration(220L);
    }

    public void makeInactive(boolean animate) {
        if (this.mActivated) {
            this.mActivated = false;
            if (this.mDimmed) {
                if (animate) {
                    startActivateAnimation(true);
                } else {
                    updateBackground();
                }
            }
        }
        if (this.mOnActivatedListener != null) {
            this.mOnActivatedListener.onActivationReset(this);
        }
        removeCallbacks(this.mTapTimeoutRunnable);
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setDimmed(boolean dimmed, boolean fade) {
        this.mNeedsDimming = dimmed;
        boolean dimmed2 = dimmed & isDimmable();
        if (this.mDimmed != dimmed2) {
            this.mDimmed = dimmed2;
            resetBackgroundAlpha();
            if (fade) {
                fadeDimmedBackground();
            } else {
                updateBackground();
            }
        }
    }

    public boolean isDimmable() {
        return true;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setDark(boolean dark, boolean fade, long delay) {
        super.setDark(dark, fade, delay);
        if (this.mDark == dark) {
            return;
        }
        this.mDark = dark;
        updateBackground();
        updateBackgroundTint(false);
        if (!dark && fade && !shouldHideBackground()) {
            fadeInFromDark(delay);
        }
        updateOutlineAlpha();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOutlineAlpha() {
        if (this.mDark) {
            setOutlineAlpha(0.0f);
            return;
        }
        float alpha = (0.7f + ((1.0f - 0.7f) * this.mNormalBackgroundVisibilityAmount)) * this.mShadowAlpha;
        if (this.mFadeInFromDarkAnimator != null) {
            alpha *= this.mFadeInFromDarkAnimator.getAnimatedFraction();
        }
        setOutlineAlpha(alpha);
    }

    public void setNormalBackgroundVisibilityAmount(float normalBackgroundVisibilityAmount) {
        this.mNormalBackgroundVisibilityAmount = normalBackgroundVisibilityAmount;
        updateOutlineAlpha();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setBelowSpeedBump(boolean below) {
        super.setBelowSpeedBump(below);
        if (below != this.mIsBelowSpeedBump) {
            this.mIsBelowSpeedBump = below;
            updateBackgroundTint();
            onBelowSpeedBumpChanged();
        }
    }

    protected void onBelowSpeedBumpChanged() {
    }

    public boolean isBelowSpeedBump() {
        return this.mIsBelowSpeedBump;
    }

    public void setTintColor(int color) {
        setTintColor(color, false);
    }

    public void setTintColor(int color, boolean animated) {
        if (color != this.mBgTint) {
            this.mBgTint = color;
            updateBackgroundTint(animated);
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setDistanceToTopRoundness(float distanceToTopRoundness) {
        super.setDistanceToTopRoundness(distanceToTopRoundness);
        this.mBackgroundNormal.setDistanceToTopRoundness(distanceToTopRoundness);
        this.mBackgroundDimmed.setDistanceToTopRoundness(distanceToTopRoundness);
    }

    public void setOverrideTintColor(int color, float overrideAmount) {
        if (this.mDark) {
            color = 0;
            overrideAmount = 0.0f;
        }
        this.mOverrideTint = color;
        this.mOverrideAmount = overrideAmount;
        int newColor = calculateBgColor();
        setBackgroundTintColor(newColor);
        if (!isDimmable() && this.mNeedsDimming) {
            this.mBackgroundNormal.setDrawableAlpha((int) NotificationUtils.interpolate(255.0f, this.mDimmedAlpha, overrideAmount));
        } else {
            this.mBackgroundNormal.setDrawableAlpha(255);
        }
    }

    protected void updateBackgroundTint() {
        updateBackgroundTint(false);
    }

    private void updateBackgroundTint(boolean animated) {
        if (this.mBackgroundColorAnimator != null) {
            this.mBackgroundColorAnimator.cancel();
        }
        int rippleColor = getRippleColor();
        this.mBackgroundDimmed.setRippleColor(rippleColor);
        this.mBackgroundNormal.setRippleColor(rippleColor);
        int color = calculateBgColor();
        if (!animated) {
            setBackgroundTintColor(color);
            return;
        }
        if (color != this.mCurrentBackgroundTint) {
            this.mStartTint = this.mCurrentBackgroundTint;
            this.mTargetTint = color;
            this.mBackgroundColorAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mBackgroundColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    int newColor = NotificationUtils.interpolateColors(ActivatableNotificationView.this.mStartTint, ActivatableNotificationView.this.mTargetTint, animation.getAnimatedFraction());
                    ActivatableNotificationView.this.setBackgroundTintColor(newColor);
                }
            });
            this.mBackgroundColorAnimator.setDuration(360L);
            this.mBackgroundColorAnimator.setInterpolator(Interpolators.LINEAR);
            this.mBackgroundColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.8
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    ActivatableNotificationView.this.mBackgroundColorAnimator = null;
                }
            });
            this.mBackgroundColorAnimator.start();
        }
    }

    protected void setBackgroundTintColor(int color) {
        if (color != this.mCurrentBackgroundTint) {
            this.mCurrentBackgroundTint = color;
            if (color == this.mNormalColor) {
                color = 0;
            }
            this.mBackgroundDimmed.setTint(color);
            this.mBackgroundNormal.setTint(color);
        }
    }

    private void fadeInFromDark(long delay) {
        final View background = this.mDimmed ? this.mBackgroundDimmed : this.mBackgroundNormal;
        background.setAlpha(0.0f);
        this.mBackgroundVisibilityUpdater.onAnimationUpdate(null);
        background.animate().alpha(1.0f).setDuration(500L).setStartDelay(delay).setInterpolator(Interpolators.ALPHA_IN).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                background.setAlpha(1.0f);
            }
        }).setUpdateListener(this.mBackgroundVisibilityUpdater).start();
        this.mFadeInFromDarkAnimator = TimeAnimator.ofFloat(0.0f, 1.0f);
        this.mFadeInFromDarkAnimator.setDuration(500L);
        this.mFadeInFromDarkAnimator.setStartDelay(delay);
        this.mFadeInFromDarkAnimator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mFadeInFromDarkAnimator.addListener(this.mFadeInEndListener);
        this.mFadeInFromDarkAnimator.addUpdateListener(this.mUpdateOutlineListener);
        this.mFadeInFromDarkAnimator.start();
    }

    private void fadeDimmedBackground() {
        this.mBackgroundDimmed.animate().cancel();
        this.mBackgroundNormal.animate().cancel();
        if (this.mActivated) {
            updateBackground();
            return;
        }
        if (!shouldHideBackground()) {
            if (this.mDimmed) {
                this.mBackgroundDimmed.setVisibility(0);
            } else {
                this.mBackgroundNormal.setVisibility(0);
            }
        }
        float startAlpha = this.mDimmed ? 1.0f : 0.0f;
        float endAlpha = this.mDimmed ? 0.0f : 1.0f;
        int duration = 220;
        if (this.mBackgroundAnimator != null) {
            startAlpha = ((Float) this.mBackgroundAnimator.getAnimatedValue()).floatValue();
            duration = (int) this.mBackgroundAnimator.getCurrentPlayTime();
            this.mBackgroundAnimator.removeAllListeners();
            this.mBackgroundAnimator.cancel();
            if (duration <= 0) {
                updateBackground();
                return;
            }
        }
        this.mBackgroundNormal.setAlpha(startAlpha);
        this.mBackgroundAnimator = ObjectAnimator.ofFloat(this.mBackgroundNormal, (Property<NotificationBackgroundView, Float>) View.ALPHA, startAlpha, endAlpha);
        this.mBackgroundAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mBackgroundAnimator.setDuration(duration);
        this.mBackgroundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ActivatableNotificationView.this.updateBackground();
                ActivatableNotificationView.this.mBackgroundAnimator = null;
                if (ActivatableNotificationView.this.mFadeInFromDarkAnimator == null) {
                    ActivatableNotificationView.this.mDimmedBackgroundFadeInAmount = -1.0f;
                }
            }
        });
        this.mBackgroundAnimator.addUpdateListener(this.mBackgroundVisibilityUpdater);
        this.mBackgroundAnimator.start();
    }

    protected void updateBackgroundAlpha(float transformationAmount) {
        this.mBgAlpha = (isChildInGroup() && this.mDimmed) ? transformationAmount : 1.0f;
        if (this.mDimmedBackgroundFadeInAmount != -1.0f) {
            this.mBgAlpha *= this.mDimmedBackgroundFadeInAmount;
        }
        this.mBackgroundDimmed.setAlpha(this.mBgAlpha);
    }

    protected void resetBackgroundAlpha() {
        updateBackgroundAlpha(0.0f);
    }

    protected void updateBackground() {
        cancelFadeAnimations();
        if (shouldHideBackground()) {
            this.mBackgroundDimmed.setVisibility(4);
            this.mBackgroundNormal.setVisibility(this.mActivated ? 0 : 4);
        } else if (this.mDimmed) {
            boolean dontShowDimmed = isGroupExpansionChanging() && isChildInGroup();
            this.mBackgroundDimmed.setVisibility(dontShowDimmed ? 4 : 0);
            this.mBackgroundNormal.setVisibility((this.mActivated || dontShowDimmed) ? 0 : 4);
        } else {
            this.mBackgroundDimmed.setVisibility(4);
            this.mBackgroundNormal.setVisibility(0);
            this.mBackgroundNormal.setAlpha(1.0f);
            removeCallbacks(this.mTapTimeoutRunnable);
            makeInactive(false);
        }
        setNormalBackgroundVisibilityAmount(this.mBackgroundNormal.getVisibility() != 0 ? 0.0f : 1.0f);
    }

    protected void updateBackgroundClipping() {
        this.mBackgroundNormal.setBottomAmountClips(!isChildInGroup());
        this.mBackgroundDimmed.setBottomAmountClips(!isChildInGroup());
    }

    protected boolean shouldHideBackground() {
        return this.mDark;
    }

    private void cancelFadeAnimations() {
        if (this.mBackgroundAnimator != null) {
            this.mBackgroundAnimator.cancel();
        }
        this.mBackgroundDimmed.animate().cancel();
        this.mBackgroundNormal.animate().cancel();
    }

    @Override // com.android.systemui.statusbar.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setPivotX(getWidth() / 2);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setActualHeight(int actualHeight, boolean notifyListeners) {
        super.setActualHeight(actualHeight, notifyListeners);
        setPivotY(actualHeight / 2);
        this.mBackgroundNormal.setActualHeight(actualHeight);
        this.mBackgroundDimmed.setActualHeight(actualHeight);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setClipTopAmount(int clipTopAmount) {
        super.setClipTopAmount(clipTopAmount);
        this.mBackgroundNormal.setClipTopAmount(clipTopAmount);
        this.mBackgroundDimmed.setClipTopAmount(clipTopAmount);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setClipBottomAmount(int clipBottomAmount) {
        super.setClipBottomAmount(clipBottomAmount);
        this.mBackgroundNormal.setClipBottomAmount(clipBottomAmount);
        this.mBackgroundDimmed.setClipBottomAmount(clipBottomAmount);
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void performRemoveAnimation(long duration, long delay, float translationDirection, boolean isHeadsUpAnimation, float endLocation, Runnable onFinishedRunnable, AnimatorListenerAdapter animationListener) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = isHeadsUpAnimation;
        this.mHeadsUpLocation = endLocation;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(false, translationDirection, delay, duration, onFinishedRunnable, animationListener);
        } else if (onFinishedRunnable != null) {
            onFinishedRunnable.run();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void performAddAnimation(long delay, long duration, boolean isHeadsUpAppear) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = isHeadsUpAppear;
        this.mHeadsUpLocation = this.mHeadsUpAddStartLocation;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(true, isHeadsUpAppear ? 0.0f : -1.0f, delay, duration, null, null);
        }
    }

    private void startAppearAnimation(final boolean isAppearing, float translationDirection, long delay, long duration, final Runnable onFinishedRunnable, AnimatorListenerAdapter animationListener) {
        cancelAppearAnimation();
        this.mAnimationTranslationY = getActualHeight() * translationDirection;
        float targetValue = 0.0f;
        if (this.mAppearAnimationFraction == -1.0f) {
            if (isAppearing) {
                this.mAppearAnimationFraction = 0.0f;
                this.mAppearAnimationTranslation = this.mAnimationTranslationY;
            } else {
                this.mAppearAnimationFraction = 1.0f;
                this.mAppearAnimationTranslation = 0.0f;
            }
        }
        this.mIsAppearing = isAppearing;
        if (isAppearing) {
            this.mCurrentAppearInterpolator = this.mSlowOutFastInInterpolator;
            this.mCurrentAlphaInterpolator = Interpolators.LINEAR_OUT_SLOW_IN;
            targetValue = 1.0f;
        } else {
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            this.mCurrentAlphaInterpolator = this.mSlowOutLinearInInterpolator;
        }
        float targetValue2 = targetValue;
        this.mAppearAnimator = ValueAnimator.ofFloat(this.mAppearAnimationFraction, targetValue2);
        this.mAppearAnimator.setInterpolator(Interpolators.LINEAR);
        this.mAppearAnimator.setDuration((long) (duration * Math.abs(this.mAppearAnimationFraction - targetValue2)));
        this.mAppearAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.11
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ActivatableNotificationView.this.mAppearAnimationFraction = ((Float) animation.getAnimatedValue()).floatValue();
                ActivatableNotificationView.this.updateAppearAnimationAlpha();
                ActivatableNotificationView.this.updateAppearRect();
                ActivatableNotificationView.this.invalidate();
            }
        });
        if (animationListener != null) {
            this.mAppearAnimator.addListener(animationListener);
        }
        if (delay > 0) {
            updateAppearAnimationAlpha();
            updateAppearRect();
            this.mAppearAnimator.setStartDelay(delay);
        }
        this.mAppearAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ActivatableNotificationView.12
            private boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (onFinishedRunnable != null) {
                    onFinishedRunnable.run();
                }
                if (!this.mWasCancelled) {
                    ActivatableNotificationView.this.enableAppearDrawing(false);
                    ActivatableNotificationView.this.onAppearAnimationFinished(isAppearing);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                this.mWasCancelled = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mWasCancelled = true;
            }
        });
        this.mAppearAnimator.start();
    }

    protected void onAppearAnimationFinished(boolean wasAppearing) {
    }

    private void cancelAppearAnimation() {
        if (this.mAppearAnimator != null) {
            this.mAppearAnimator.cancel();
            this.mAppearAnimator = null;
        }
    }

    public void cancelAppearDrawing() {
        cancelAppearAnimation();
        enableAppearDrawing(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppearRect() {
        float left;
        float right;
        float top;
        float bottom;
        float inverseFraction = 1.0f - this.mAppearAnimationFraction;
        float translationFraction = this.mCurrentAppearInterpolator.getInterpolation(inverseFraction);
        float translateYTotalAmount = this.mAnimationTranslationY * translationFraction;
        this.mAppearAnimationTranslation = translateYTotalAmount;
        float widthFraction = this.mCurrentAppearInterpolator.getInterpolation(Math.min(1.0f, Math.max(0.0f, (inverseFraction - 0.0f) / 0.8f)));
        float startWidthFraction = 0.05f;
        if (this.mIsHeadsUpAnimation && !this.mIsAppearing) {
            startWidthFraction = 0.0f;
        }
        float width = MathUtils.lerp(startWidthFraction, 1.0f, 1.0f - widthFraction) * getWidth();
        if (this.mIsHeadsUpAnimation) {
            left = MathUtils.lerp(this.mHeadsUpLocation, 0.0f, 1.0f - widthFraction);
            right = left + width;
        } else {
            left = (getWidth() * 0.5f) - (width / 2.0f);
            right = getWidth() - left;
        }
        float heightFraction = this.mCurrentAppearInterpolator.getInterpolation(Math.max(0.0f, (inverseFraction - 0.0f) / 1.0f));
        int actualHeight = getActualHeight();
        if (this.mAnimationTranslationY > 0.0f) {
            bottom = (actualHeight - ((this.mAnimationTranslationY * heightFraction) * 0.1f)) - translateYTotalAmount;
            top = bottom * heightFraction;
        } else {
            top = (((actualHeight + this.mAnimationTranslationY) * heightFraction) * 0.1f) - translateYTotalAmount;
            bottom = (top * heightFraction) + (actualHeight * (1.0f - heightFraction));
        }
        this.mAppearAnimationRect.set(left, top, right, bottom);
        setOutlineRect(left, this.mAppearAnimationTranslation + top, right, this.mAppearAnimationTranslation + bottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppearAnimationAlpha() {
        float contentAlphaProgress = this.mAppearAnimationFraction;
        setContentAlpha(this.mCurrentAlphaInterpolator.getInterpolation(Math.min(1.0f, contentAlphaProgress / 1.0f)));
    }

    private void setContentAlpha(float contentAlpha) {
        int layerType;
        View contentView = getContentView();
        if (contentView.hasOverlappingRendering()) {
            if (contentAlpha == 0.0f || contentAlpha == 1.0f) {
                layerType = 0;
            } else {
                layerType = 2;
            }
            int currentLayerType = contentView.getLayerType();
            if (currentLayerType != layerType) {
                contentView.setLayerType(layerType, null);
            }
        }
        contentView.setAlpha(contentAlpha);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    protected void applyRoundness() {
        super.applyRoundness();
        applyBackgroundRoundness(getCurrentBackgroundRadiusTop(), getCurrentBackgroundRadiusBottom());
    }

    protected void applyBackgroundRoundness(float topRadius, float bottomRadius) {
        this.mBackgroundDimmed.setRoundness(topRadius, bottomRadius);
        this.mBackgroundNormal.setRoundness(topRadius, bottomRadius);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    protected void setBackgroundTop(int backgroundTop) {
        this.mBackgroundDimmed.setBackgroundTop(backgroundTop);
        this.mBackgroundNormal.setBackgroundTop(backgroundTop);
    }

    public int calculateBgColor() {
        return calculateBgColor(true, true);
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    protected boolean childNeedsClipping(View child) {
        if ((child instanceof NotificationBackgroundView) && isClippingNeeded()) {
            return true;
        }
        return super.childNeedsClipping(child);
    }

    private int calculateBgColor(boolean withTint, boolean withOverRide) {
        if (withTint && this.mDark) {
            return getContext().getColor(R.color.notification_material_background_dark_color);
        }
        if (withOverRide && this.mOverrideTint != 0) {
            int defaultTint = calculateBgColor(withTint, false);
            return NotificationUtils.interpolateColors(defaultTint, this.mOverrideTint, this.mOverrideAmount);
        }
        if (withTint && this.mBgTint != 0) {
            return this.mBgTint;
        }
        return this.mNormalColor;
    }

    protected int getRippleColor() {
        if (this.mBgTint != 0) {
            return this.mTintedRippleColor;
        }
        return this.mNormalRippleColor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAppearDrawing(boolean enable) {
        if (enable != this.mDrawingAppearAnimation) {
            this.mDrawingAppearAnimation = enable;
            if (!enable) {
                setContentAlpha(1.0f);
                this.mAppearAnimationFraction = -1.0f;
                setOutlineRect(null);
            }
            invalidate();
        }
    }

    public boolean isDrawingAppearAnimation() {
        return this.mDrawingAppearAnimation;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawingAppearAnimation) {
            canvas.save();
            canvas.translate(0.0f, this.mAppearAnimationTranslation);
        }
        super.dispatchDraw(canvas);
        if (this.mDrawingAppearAnimation) {
            canvas.restore();
        }
    }

    public void setOnActivatedListener(OnActivatedListener onActivatedListener) {
        this.mOnActivatedListener = onActivatedListener;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public float getShadowAlpha() {
        return this.mShadowAlpha;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setShadowAlpha(float shadowAlpha) {
        if (shadowAlpha != this.mShadowAlpha) {
            this.mShadowAlpha = shadowAlpha;
            updateOutlineAlpha();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setFakeShadowIntensity(float shadowIntensity, float outlineAlpha, int shadowYEnd, int outlineTranslation) {
        boolean hiddenBefore = this.mShadowHidden;
        this.mShadowHidden = shadowIntensity == 0.0f;
        if (!this.mShadowHidden || !hiddenBefore) {
            this.mFakeShadow.setFakeShadowTranslationZ((getTranslationZ() + 0.1f) * shadowIntensity, outlineAlpha, shadowYEnd, outlineTranslation);
        }
    }

    public int getBackgroundColorWithoutTint() {
        return calculateBgColor(false, false);
    }

    public boolean isPinned() {
        return false;
    }

    public boolean isHeadsUpAnimatingAway() {
        return false;
    }
}
