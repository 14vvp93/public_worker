package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.function.TriConsumer;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.statusbar.ScrimView;
import com.android.systemui.statusbar.stack.ViewState;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ScrimController implements ViewTreeObserver.OnPreDrawListener, ColorExtractor.OnColorsChangedListener, Dumpable {
    private static final boolean DEBUG = Log.isLoggable("ScrimController", 3);
    protected boolean mAnimateChange;
    private long mAnimationDelay;
    private boolean mBlankScreen;
    private Runnable mBlankingTransitionRunnable;
    private Callback mCallback;
    private final Context mContext;
    private int mCurrentBehindTint;
    private int mCurrentInFrontTint;
    private boolean mDarkenWhileDragging;
    private boolean mDeferFinishedListener;
    private final DozeParameters mDozeParameters;
    private boolean mKeyguardOccluded;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private ColorExtractor.GradientColors mLockColors;
    private boolean mNeedsDrawableColorUpdate;
    private float mNotificationDensity;
    private Runnable mOnAnimationFinished;
    private Runnable mPendingFrameCallback;
    private boolean mScreenBlankingCallbackCalled;
    private boolean mScreenOn;
    protected final ScrimView mScrimBehind;
    protected float mScrimBehindAlpha;
    protected float mScrimBehindAlphaResValue;
    protected final ScrimView mScrimInFront;
    private final TriConsumer<ScrimState, Float, ColorExtractor.GradientColors> mScrimStateListener;
    private final Consumer<Integer> mScrimVisibleListener;
    private int mScrimsVisibility;
    private ScrimState mState;
    private ColorExtractor.GradientColors mSystemColors;
    private final AlarmTimeout mTimeTicker;
    private boolean mTracking;
    private final UnlockMethodCache mUnlockMethodCache;
    private boolean mUpdatePending;
    private boolean mWakeLockHeld;
    private boolean mWallpaperSupportsAmbientMode;
    private boolean mWallpaperVisibilityTimedOut;
    protected float mScrimBehindAlphaKeyguard = 0.45f;
    private float mExpansionFraction = 1.0f;
    private boolean mExpansionAffectsAlpha = true;
    protected long mAnimationDuration = -1;
    private final Interpolator mInterpolator = new DecelerateInterpolator();
    private float mCurrentInFrontAlpha = -1.0f;
    private float mCurrentBehindAlpha = -1.0f;
    private final Runnable mScrimAlpha = new Runnable() { // from class: com.android.systemui.statusbar.phone.ScrimController.1
        @Override // java.lang.Runnable
        public void run() {
            if (ScrimController.this.mScrimInFront != null) {
                ScrimController.this.mScrimInFront.setClickable(false);
            }
        }
    };
    private final WakeLock mWakeLock = createWakeLock();
    private final SysuiColorExtractor mColorExtractor = (SysuiColorExtractor) Dependency.get(SysuiColorExtractor.class);

    public ScrimController(ScrimView scrimBehind, ScrimView scrimInFront, TriConsumer<ScrimState, Float, ColorExtractor.GradientColors> scrimStateListener, Consumer<Integer> scrimVisibleListener, DozeParameters dozeParameters, AlarmManager alarmManager) {
        this.mState = ScrimState.UNINITIALIZED;
        this.mScrimBehind = scrimBehind;
        this.mScrimInFront = scrimInFront;
        this.mScrimStateListener = scrimStateListener;
        this.mScrimVisibleListener = scrimVisibleListener;
        this.mContext = scrimBehind.getContext();
        this.mUnlockMethodCache = UnlockMethodCache.getInstance(this.mContext);
        this.mDarkenWhileDragging = !this.mUnlockMethodCache.canSkipBouncer();
        this.mKeyguardUpdateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        this.mScrimBehindAlphaResValue = this.mContext.getResources().getFloat(R.dimen.scrim_behind_alpha);
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ZxOK9HbkOUnaEI0FKoidLb2saOY
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onHideWallpaperTimeout();
            }
        }, "hide_aod_wallpaper", new Handler());
        this.mScrimBehindAlpha = this.mScrimBehindAlphaResValue;
        this.mDozeParameters = dozeParameters;
        this.mColorExtractor.addOnColorsChangedListener(this);
        this.mLockColors = this.mColorExtractor.getColors(2, 1, true);
        this.mSystemColors = this.mColorExtractor.getColors(1, 1, true);
        this.mNeedsDrawableColorUpdate = true;
        ScrimState[] states = ScrimState.values();
        for (int i = 0; i < states.length; i++) {
            states[i].init(this.mScrimInFront, this.mScrimBehind, this.mDozeParameters);
            states[i].setScrimBehindAlphaKeyguard(this.mScrimBehindAlphaKeyguard);
        }
        this.mState = ScrimState.UNINITIALIZED;
        this.mScrimBehind.setDefaultFocusHighlightEnabled(false);
        this.mScrimInFront.setDefaultFocusHighlightEnabled(false);
        updateScrims();
    }

    public void transitionTo(ScrimState state) {
        transitionTo(state, null);
    }

    public void transitionTo(ScrimState state, Callback callback) {
        if (state == this.mState) {
            if (callback != null && this.mCallback != callback) {
                callback.onFinished();
                return;
            }
            return;
        }
        if (DEBUG) {
            Log.d("ScrimController", "State changed to: " + state);
        }
        if (state == ScrimState.UNINITIALIZED) {
            throw new IllegalArgumentException("Cannot change to UNINITIALIZED.");
        }
        ScrimState oldState = this.mState;
        this.mState = state;
        Trace.traceCounter(4096L, "scrim_state", this.mState.getIndex());
        if (this.mCallback != null) {
            this.mCallback.onCancelled();
        }
        this.mCallback = callback;
        state.prepare(oldState);
        this.mScreenBlankingCallbackCalled = false;
        this.mAnimationDelay = 0L;
        this.mBlankScreen = state.getBlanksScreen();
        this.mAnimateChange = state.getAnimateChange();
        this.mAnimationDuration = state.getAnimationDuration();
        this.mCurrentInFrontTint = state.getFrontTint();
        this.mCurrentBehindTint = state.getBehindTint();
        this.mCurrentInFrontAlpha = state.getFrontAlpha();
        this.mCurrentBehindAlpha = state.getBehindAlpha(this.mNotificationDensity);
        applyExpansionToAlpha();
        this.mScrimInFront.setFocusable(!state.isLowPowerState());
        this.mScrimBehind.setFocusable(!state.isLowPowerState());
        if (this.mPendingFrameCallback != null) {
            this.mScrimBehind.removeCallbacks(this.mPendingFrameCallback);
            this.mPendingFrameCallback = null;
        }
        if (getHandler().hasCallbacks(this.mBlankingTransitionRunnable)) {
            getHandler().removeCallbacks(this.mBlankingTransitionRunnable);
            this.mBlankingTransitionRunnable = null;
        }
        this.mNeedsDrawableColorUpdate = state != ScrimState.BRIGHTNESS_MIRROR;
        if (this.mState.isLowPowerState()) {
            holdWakeLock();
        }
        if (this.mWallpaperSupportsAmbientMode && this.mDozeParameters.getAlwaysOn() && this.mState == ScrimState.AOD) {
            if (!this.mWallpaperVisibilityTimedOut) {
                this.mTimeTicker.schedule(this.mDozeParameters.getWallpaperAodDuration(), 1);
            }
        } else if (this.mState != ScrimState.PULSING) {
            this.mTimeTicker.cancel();
            this.mWallpaperVisibilityTimedOut = false;
        }
        if (this.mKeyguardUpdateMonitor.needsSlowUnlockTransition() && this.mState == ScrimState.UNLOCKED) {
            this.mScrimInFront.postOnAnimationDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$5DY8P9cXHTvbVZZOVB_VSCJUZk0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.scheduleUpdate();
                }
            }, 16L);
            this.mAnimationDelay = 100L;
        } else if ((!this.mDozeParameters.getAlwaysOn() && oldState == ScrimState.AOD) || (this.mState == ScrimState.AOD && !this.mDozeParameters.getDisplayNeedsBlanking())) {
            onPreDraw();
        } else {
            scheduleUpdate();
        }
        dispatchScrimState(this.mScrimBehind.getViewAlpha());
    }

    public ScrimState getState() {
        return this.mState;
    }

    public void onTrackingStarted() {
        this.mTracking = true;
        this.mDarkenWhileDragging = true ^ this.mUnlockMethodCache.canSkipBouncer();
    }

    public void onExpandingFinished() {
        this.mTracking = false;
    }

    @VisibleForTesting
    protected void onHideWallpaperTimeout() {
        if (this.mState != ScrimState.AOD) {
            return;
        }
        holdWakeLock();
        this.mWallpaperVisibilityTimedOut = true;
        this.mAnimateChange = true;
        this.mAnimationDuration = this.mDozeParameters.getWallpaperFadeOutDuration();
        scheduleUpdate();
    }

    private void holdWakeLock() {
        if (!this.mWakeLockHeld) {
            if (this.mWakeLock != null) {
                this.mWakeLockHeld = true;
                this.mWakeLock.acquire();
            } else {
                Log.w("ScrimController", "Cannot hold wake lock, it has not been set yet");
            }
        }
    }

    public void setPanelExpansion(float fraction) {
        if (this.mExpansionFraction != fraction) {
            this.mExpansionFraction = fraction;
            boolean keyguardOrUnlocked = this.mState == ScrimState.UNLOCKED || this.mState == ScrimState.KEYGUARD;
            if (!keyguardOrUnlocked || !this.mExpansionAffectsAlpha) {
                return;
            }
            applyExpansionToAlpha();
            if (this.mUpdatePending) {
                return;
            }
            setOrAdaptCurrentAnimation(this.mScrimBehind);
            setOrAdaptCurrentAnimation(this.mScrimInFront);
            dispatchScrimState(this.mScrimBehind.getViewAlpha());
        }
    }

    private void setOrAdaptCurrentAnimation(View scrim) {
        if (!isAnimating(scrim)) {
            updateScrimColor(scrim, getCurrentScrimAlpha(scrim), getCurrentScrimTint(scrim));
            return;
        }
        ValueAnimator previousAnimator = (ValueAnimator) scrim.getTag(R.id.scrim);
        float alpha = getCurrentScrimAlpha(scrim);
        float previousEndValue = ((Float) scrim.getTag(R.id.scrim_alpha_end)).floatValue();
        float previousStartValue = ((Float) scrim.getTag(R.id.scrim_alpha_start)).floatValue();
        float relativeDiff = alpha - previousEndValue;
        float newStartValue = previousStartValue + relativeDiff;
        scrim.setTag(R.id.scrim_alpha_start, Float.valueOf(newStartValue));
        scrim.setTag(R.id.scrim_alpha_end, Float.valueOf(alpha));
        previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
    }

    private void applyExpansionToAlpha() {
        if (!this.mExpansionAffectsAlpha) {
            return;
        }
        if (this.mState == ScrimState.UNLOCKED) {
            float behindFraction = getInterpolatedFraction();
            this.mCurrentBehindAlpha = 0.7f * ((float) Math.pow(behindFraction, 0.800000011920929d));
            this.mCurrentInFrontAlpha = 0.0f;
            return;
        }
        if (this.mState == ScrimState.KEYGUARD) {
            float interpolatedFract = getInterpolatedFraction();
            float alphaBehind = this.mState.getBehindAlpha(this.mNotificationDensity);
            if (this.mDarkenWhileDragging) {
                this.mCurrentBehindAlpha = MathUtils.lerp(0.7f, alphaBehind, interpolatedFract);
                this.mCurrentInFrontAlpha = 0.0f;
            } else {
                this.mCurrentBehindAlpha = MathUtils.lerp(0.0f, alphaBehind, interpolatedFract);
                this.mCurrentInFrontAlpha = 0.0f;
            }
        }
    }

    public void setNotificationCount(int notificationCount) {
        float notificationDensity = Math.min(notificationCount / 3.0f, 1.0f);
        if (this.mNotificationDensity == notificationDensity) {
            return;
        }
        this.mNotificationDensity = notificationDensity;
        if (this.mState == ScrimState.KEYGUARD) {
            applyExpansionToAlpha();
            scheduleUpdate();
        }
    }

    public void setScrimBehindDrawable(Drawable drawable) {
        this.mScrimBehind.setDrawable(drawable);
    }

    public void setAodFrontScrimAlpha(float alpha) {
        if (this.mState == ScrimState.AOD && this.mDozeParameters.getAlwaysOn() && this.mCurrentInFrontAlpha != alpha) {
            this.mCurrentInFrontAlpha = alpha;
            scheduleUpdate();
        }
        ScrimState scrimState = this.mState;
        ScrimState.AOD.setAodFrontScrimAlpha(alpha);
    }

    protected void scheduleUpdate() {
        if (this.mUpdatePending) {
            return;
        }
        this.mScrimBehind.invalidate();
        this.mScrimBehind.getViewTreeObserver().addOnPreDrawListener(this);
        this.mUpdatePending = true;
    }

    protected void updateScrims() {
        ColorExtractor.GradientColors currentScrimColors;
        boolean occludedKeyguard = false;
        if (this.mNeedsDrawableColorUpdate) {
            this.mNeedsDrawableColorUpdate = false;
            if (this.mState == ScrimState.KEYGUARD || this.mState == ScrimState.BOUNCER_SCRIMMED || this.mState == ScrimState.BOUNCER) {
                this.mScrimInFront.setColors(this.mLockColors, true);
                this.mScrimBehind.setColors(this.mLockColors, true);
                currentScrimColors = this.mLockColors;
            } else {
                boolean animateScrimInFront = this.mScrimInFront.getViewAlpha() != 0.0f;
                boolean animateScrimBehind = this.mScrimBehind.getViewAlpha() != 0.0f;
                this.mScrimInFront.setColors(this.mSystemColors, animateScrimInFront);
                this.mScrimBehind.setColors(this.mSystemColors, animateScrimBehind);
                currentScrimColors = this.mSystemColors;
            }
            int textColor = currentScrimColors.supportsDarkText() ? -16777216 : -1;
            int mainColor = currentScrimColors.getMainColor();
            float minOpacity = ColorUtils.calculateMinimumBackgroundAlpha(textColor, mainColor, 4.5f) / 255.0f;
            this.mScrimBehindAlpha = Math.max(this.mScrimBehindAlphaResValue, minOpacity);
            dispatchScrimState(this.mScrimBehind.getViewAlpha());
        }
        boolean aodWallpaperTimeout = this.mState == ScrimState.AOD && this.mWallpaperVisibilityTimedOut;
        if ((this.mState == ScrimState.PULSING || this.mState == ScrimState.AOD) && this.mKeyguardOccluded) {
            occludedKeyguard = true;
        }
        if (aodWallpaperTimeout || occludedKeyguard) {
            this.mCurrentBehindAlpha = 1.0f;
        }
        setScrimInFrontAlpha(this.mCurrentInFrontAlpha);
        setScrimBehindAlpha(this.mCurrentBehindAlpha);
        dispatchScrimsVisible();
    }

    private void dispatchScrimState(float alpha) {
        this.mScrimStateListener.accept(this.mState, Float.valueOf(alpha), this.mScrimInFront.getColors());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchScrimsVisible() {
        int currentScrimVisibility;
        if (this.mScrimInFront.getViewAlpha() == 1.0f || this.mScrimBehind.getViewAlpha() == 1.0f) {
            currentScrimVisibility = 2;
        } else if (this.mScrimInFront.getViewAlpha() == 0.0f && this.mScrimBehind.getViewAlpha() == 0.0f) {
            currentScrimVisibility = 0;
        } else {
            currentScrimVisibility = 1;
        }
        if (this.mScrimsVisibility != currentScrimVisibility) {
            this.mScrimsVisibility = currentScrimVisibility;
            this.mScrimVisibleListener.accept(Integer.valueOf(currentScrimVisibility));
        }
    }

    private float getInterpolatedFraction() {
        float frac = (1.2f * this.mExpansionFraction) - 0.2f;
        if (frac <= 0.0f) {
            return 0.0f;
        }
        return (float) (1.0d - (0.5d * (1.0d - Math.cos(3.141590118408203d * Math.pow(1.0f - frac, 2.0d)))));
    }

    private void setScrimBehindAlpha(float alpha) {
        setScrimAlpha(this.mScrimBehind, alpha);
    }

    private void setScrimInFrontAlpha(float alpha) {
        setScrimAlpha(this.mScrimInFront, alpha);
    }

    private void setScrimAlpha(ScrimView scrim, float alpha) {
        boolean z = false;
        if (alpha == 0.0f) {
            scrim.setClickable(false);
        } else {
            if (this.mState != ScrimState.AOD && this.mState != ScrimState.PULSING) {
                z = true;
            }
            boolean clickable = z;
            scrim.setClickable(clickable);
            if (clickable && scrim == this.mScrimInFront) {
                getHandler().removeCallbacks(this.mScrimAlpha);
                getHandler().postDelayed(this.mScrimAlpha, 1000L);
            }
        }
        updateScrim(scrim, alpha);
    }

    private void updateScrimColor(View scrim, float alpha, int tint) {
        float alpha2 = Math.max(0.0f, Math.min(1.0f, alpha));
        if (scrim instanceof ScrimView) {
            ScrimView scrimView = (ScrimView) scrim;
            Trace.traceCounter(4096L, scrim == this.mScrimInFront ? "front_scrim_alpha" : "back_scrim_alpha", (int) (255.0f * alpha2));
            Trace.traceCounter(4096L, scrim == this.mScrimInFront ? "front_scrim_tint" : "back_scrim_tint", Color.alpha(tint));
            scrimView.setTint(tint);
            scrimView.setViewAlpha(alpha2);
        } else {
            scrim.setAlpha(alpha2);
        }
        dispatchScrimsVisible();
    }

    private void startScrimAnimation(final View scrim, float current) {
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        final int initialScrimTint = scrim instanceof ScrimView ? ((ScrimView) scrim).getTint() : 0;
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ScrimController$RfJJyPt1cPl4hraLjBCUJgqPhOM
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScrimController.lambda$startScrimAnimation$0(this.f$0, scrim, initialScrimTint, valueAnimator);
            }
        });
        anim.setInterpolator(this.mInterpolator);
        anim.setStartDelay(this.mAnimationDelay);
        anim.setDuration(this.mAnimationDuration);
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ScrimController.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ScrimController.this.onFinished();
                scrim.setTag(R.id.scrim, null);
                ScrimController.this.dispatchScrimsVisible();
                if (!ScrimController.this.mDeferFinishedListener && ScrimController.this.mOnAnimationFinished != null) {
                    ScrimController.this.mOnAnimationFinished.run();
                    ScrimController.this.mOnAnimationFinished = null;
                }
            }
        });
        scrim.setTag(R.id.scrim_alpha_start, Float.valueOf(current));
        scrim.setTag(R.id.scrim_alpha_end, Float.valueOf(getCurrentScrimAlpha(scrim)));
        scrim.setTag(R.id.scrim, anim);
        anim.start();
    }

    public static /* synthetic */ void lambda$startScrimAnimation$0(ScrimController scrimController, View scrim, int initialScrimTint, ValueAnimator animation) {
        float startAlpha = ((Float) scrim.getTag(R.id.scrim_alpha_start)).floatValue();
        float animAmount = ((Float) animation.getAnimatedValue()).floatValue();
        int finalScrimTint = scrimController.getCurrentScrimTint(scrim);
        float finalScrimAlpha = scrimController.getCurrentScrimAlpha(scrim);
        float alpha = MathUtils.lerp(startAlpha, finalScrimAlpha, animAmount);
        float alpha2 = MathUtils.constrain(alpha, 0.0f, 1.0f);
        int tint = ColorUtils.blendARGB(initialScrimTint, finalScrimTint, animAmount);
        scrimController.updateScrimColor(scrim, alpha2, tint);
        scrimController.dispatchScrimsVisible();
    }

    private float getCurrentScrimAlpha(View scrim) {
        if (scrim == this.mScrimInFront) {
            return this.mCurrentInFrontAlpha;
        }
        if (scrim == this.mScrimBehind) {
            return this.mCurrentBehindAlpha;
        }
        throw new IllegalArgumentException("Unknown scrim view");
    }

    private int getCurrentScrimTint(View scrim) {
        if (scrim == this.mScrimInFront) {
            return this.mCurrentInFrontTint;
        }
        if (scrim == this.mScrimBehind) {
            return this.mCurrentBehindTint;
        }
        throw new IllegalArgumentException("Unknown scrim view");
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        this.mScrimBehind.getViewTreeObserver().removeOnPreDrawListener(this);
        this.mUpdatePending = false;
        if (this.mCallback != null) {
            this.mCallback.onStart();
        }
        updateScrims();
        if (this.mOnAnimationFinished != null && !isAnimating(this.mScrimInFront) && !isAnimating(this.mScrimBehind)) {
            this.mOnAnimationFinished.run();
            this.mOnAnimationFinished = null;
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinished() {
        if (this.mWakeLockHeld) {
            this.mWakeLock.release();
            this.mWakeLockHeld = false;
        }
        if (this.mCallback != null) {
            this.mCallback.onFinished();
            this.mCallback = null;
        }
        if (this.mState == ScrimState.UNLOCKED) {
            this.mCurrentInFrontTint = 0;
            this.mCurrentBehindTint = 0;
        }
    }

    private boolean isAnimating(View scrim) {
        return scrim.getTag(R.id.scrim) != null;
    }

    public void setDrawBehindAsSrc(boolean asSrc) {
        this.mScrimBehind.setDrawAsSrc(asSrc);
    }

    @VisibleForTesting
    void setOnAnimationFinished(Runnable onAnimationFinished) {
        this.mOnAnimationFinished = onAnimationFinished;
    }

    private void updateScrim(ScrimView scrim, float alpha) {
        float currentAlpha = scrim.getViewAlpha();
        ValueAnimator previousAnimator = (ValueAnimator) ViewState.getChildTag(scrim, R.id.scrim);
        if (previousAnimator != null) {
            if (this.mAnimateChange) {
                this.mDeferFinishedListener = true;
            }
            cancelAnimator(previousAnimator);
            this.mDeferFinishedListener = false;
        }
        if (this.mPendingFrameCallback != null) {
            return;
        }
        if (this.mBlankScreen) {
            blankDisplay();
            return;
        }
        if (!this.mScreenBlankingCallbackCalled && this.mCallback != null) {
            this.mCallback.onDisplayBlanked();
            this.mScreenBlankingCallbackCalled = true;
        }
        if (scrim == this.mScrimBehind) {
            dispatchScrimState(alpha);
        }
        boolean wantsAlphaUpdate = alpha != currentAlpha;
        boolean wantsTintUpdate = scrim.getTint() != getCurrentScrimTint(scrim);
        if (wantsAlphaUpdate || wantsTintUpdate) {
            if (this.mAnimateChange) {
                startScrimAnimation(scrim, currentAlpha);
                return;
            } else {
                updateScrimColor(scrim, alpha, getCurrentScrimTint(scrim));
                onFinished();
                return;
            }
        }
        onFinished();
    }

    @VisibleForTesting
    protected void cancelAnimator(ValueAnimator previousAnimator) {
        if (previousAnimator != null) {
            previousAnimator.cancel();
        }
    }

    private void blankDisplay() {
        updateScrimColor(this.mScrimInFront, 1.0f, -16777216);
        this.mPendingFrameCallback = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ScrimController$kDQtFE2BFSUNax7wJ8UgLaBZyEo
            @Override // java.lang.Runnable
            public final void run() {
                ScrimController.lambda$blankDisplay$2(this.f$0);
            }
        };
        doOnTheNextFrame(this.mPendingFrameCallback);
    }

    public static /* synthetic */ void lambda$blankDisplay$2(final ScrimController scrimController) {
        if (scrimController.mCallback != null) {
            scrimController.mCallback.onDisplayBlanked();
            scrimController.mScreenBlankingCallbackCalled = true;
        }
        scrimController.mBlankingTransitionRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ScrimController$xRo1WgiULtMHpaX-VTe04p0hBHI
            @Override // java.lang.Runnable
            public final void run() {
                ScrimController.lambda$blankDisplay$1(this.f$0);
            }
        };
        int delay = scrimController.mScreenOn ? 32 : 500;
        if (DEBUG) {
            Log.d("ScrimController", "Fading out scrims with delay: " + delay);
        }
        scrimController.getHandler().postDelayed(scrimController.mBlankingTransitionRunnable, delay);
    }

    public static /* synthetic */ void lambda$blankDisplay$1(ScrimController scrimController) {
        scrimController.mBlankingTransitionRunnable = null;
        scrimController.mPendingFrameCallback = null;
        scrimController.mBlankScreen = false;
        scrimController.updateScrims();
    }

    @VisibleForTesting
    protected void doOnTheNextFrame(Runnable callback) {
        this.mScrimBehind.postOnAnimationDelayed(callback, 32L);
    }

    @VisibleForTesting
    protected Handler getHandler() {
        return Handler.getMain();
    }

    public void setExcludedBackgroundArea(Rect area) {
        this.mScrimBehind.setExcludedArea(area);
    }

    public int getBackgroundColor() {
        int color = this.mLockColors.getMainColor();
        return Color.argb((int) (this.mScrimBehind.getViewAlpha() * Color.alpha(color)), Color.red(color), Color.green(color), Color.blue(color));
    }

    public void setScrimBehindChangeRunnable(Runnable changeRunnable) {
        this.mScrimBehind.setChangeRunnable(changeRunnable);
    }

    public void setCurrentUser(int currentUser) {
    }

    public void onColorsChanged(ColorExtractor colorExtractor, int which) {
        if ((which & 2) != 0) {
            this.mLockColors = this.mColorExtractor.getColors(2, 1, true);
            this.mNeedsDrawableColorUpdate = true;
            scheduleUpdate();
        }
        if ((which & 1) != 0) {
            this.mSystemColors = this.mColorExtractor.getColors(1, 1, this.mState != ScrimState.UNLOCKED);
            this.mNeedsDrawableColorUpdate = true;
            scheduleUpdate();
        }
    }

    @VisibleForTesting
    protected WakeLock createWakeLock() {
        return new DelayedWakeLock(getHandler(), WakeLock.createPartial(this.mContext, "Scrims"));
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println(" ScrimController: ");
        pw.print("  state: ");
        pw.println(this.mState);
        pw.print("  frontScrim:");
        pw.print(" viewAlpha=");
        pw.print(this.mScrimInFront.getViewAlpha());
        pw.print(" alpha=");
        pw.print(this.mCurrentInFrontAlpha);
        pw.print(" tint=0x");
        pw.println(Integer.toHexString(this.mScrimInFront.getTint()));
        pw.print("  backScrim:");
        pw.print(" viewAlpha=");
        pw.print(this.mScrimBehind.getViewAlpha());
        pw.print(" alpha=");
        pw.print(this.mCurrentBehindAlpha);
        pw.print(" tint=0x");
        pw.println(Integer.toHexString(this.mScrimBehind.getTint()));
        pw.print("   mTracking=");
        pw.println(this.mTracking);
    }

    public void setWallpaperSupportsAmbientMode(boolean wallpaperSupportsAmbientMode) {
        this.mWallpaperSupportsAmbientMode = wallpaperSupportsAmbientMode;
        ScrimState[] states = ScrimState.values();
        for (ScrimState scrimState : states) {
            scrimState.setWallpaperSupportsAmbientMode(wallpaperSupportsAmbientMode);
        }
    }

    public void onScreenTurnedOn() {
        this.mScreenOn = true;
        Handler handler = getHandler();
        if (handler.hasCallbacks(this.mBlankingTransitionRunnable)) {
            if (DEBUG) {
                Log.d("ScrimController", "Shorter blanking because screen turned on. All good.");
            }
            handler.removeCallbacks(this.mBlankingTransitionRunnable);
            this.mBlankingTransitionRunnable.run();
        }
    }

    public void onScreenTurnedOff() {
        this.mScreenOn = false;
    }

    public void setExpansionAffectsAlpha(boolean expansionAffectsAlpha) {
        this.mExpansionAffectsAlpha = expansionAffectsAlpha;
    }

    public void setKeyguardOccluded(boolean keyguardOccluded) {
        this.mKeyguardOccluded = keyguardOccluded;
        updateScrims();
    }

    public interface Callback {
        default void onStart() {
        }

        default void onDisplayBlanked() {
        }

        default void onFinished() {
        }

        default void onCancelled() {
        }
    }
}
