package com.mediatek.keyguard.PowerOffAlarm.multiwaveview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class GlowPadView extends View {
    private int mActiveTarget;
    private boolean mAllowScaling;
    private boolean mAlwaysTrackFinger;
    private boolean mAnimatingTargets;
    private Tweener mBackgroundAnimator;
    private ArrayList<String> mDirectionDescriptions;
    private int mDirectionDescriptionsResourceId;
    private boolean mDragging;
    private int mFeedbackCount;
    private AnimationBundle mGlowAnimations;
    private float mGlowRadius;
    private int mGrabbedState;
    private int mGravity;
    private TargetDrawable mHandleDrawable;
    private int mHorizontalInset;
    private boolean mInitialLayout;
    private float mInnerRadius;
    private int mMaxTargetHeight;
    private int mMaxTargetWidth;
    private int mNewTargetResources;
    private OnTriggerListener mOnTriggerListener;
    private float mOuterRadius;
    private TargetDrawable mOuterRing;
    private PointCloud mPointCloud;
    private int mPointerId;
    private Animator.AnimatorListener mResetListener;
    private Animator.AnimatorListener mResetListenerWithPing;
    private float mRingScaleFactor;
    private float mSnapMargin;
    private AnimationBundle mTargetAnimations;
    private ArrayList<String> mTargetDescriptions;
    private int mTargetDescriptionsResourceId;
    private ArrayList<TargetDrawable> mTargetDrawables;
    private int mTargetResourceId;
    private Animator.AnimatorListener mTargetUpdateListener;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private int mVerticalInset;
    private int mVibrationDuration;
    private Vibrator mVibrator;
    private AnimationBundle mWaveAnimations;
    private float mWaveCenterX;
    private float mWaveCenterY;

    public interface OnTriggerListener {
        void onFinishFinalAnimation();

        void onGrabbed(View view, int i);

        void onGrabbedStateChange(View view, int i);

        void onReleased(View view, int i);

        void onTrigger(View view, int i);
    }

    private class AnimationBundle extends ArrayList<Tweener> {
        private static final long serialVersionUID = -6319262269245852568L;
        private boolean mSuspended;

        private AnimationBundle() {
        }

        public void start() {
            if (this.mSuspended) {
                return;
            }
            int count = size();
            for (int i = 0; i < count; i++) {
                Tweener anim = get(i);
                anim.animator.start();
            }
        }

        public void cancel() {
            int count = size();
            for (int i = 0; i < count; i++) {
                Tweener anim = get(i);
                anim.animator.cancel();
            }
            clear();
        }

        public void stop() {
            int count = size();
            for (int i = 0; i < count; i++) {
                Tweener anim = get(i);
                anim.animator.end();
            }
            clear();
        }
    }

    public GlowPadView(Context context) {
        this(context, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public GlowPadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTargetDrawables = new ArrayList<>();
        this.mWaveAnimations = new AnimationBundle();
        this.mTargetAnimations = new AnimationBundle();
        this.mGlowAnimations = new AnimationBundle();
        this.mFeedbackCount = 3;
        this.mVibrationDuration = 0;
        this.mActiveTarget = -1;
        this.mRingScaleFactor = 1.0f;
        this.mOuterRadius = 0.0f;
        this.mSnapMargin = 0.0f;
        this.mResetListener = new AnimatorListenerAdapter() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.GlowPadView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GlowPadView.this.switchToState(0, GlowPadView.this.mWaveCenterX, GlowPadView.this.mWaveCenterY);
                GlowPadView.this.dispatchOnFinishFinalAnimation();
            }
        };
        this.mResetListenerWithPing = new AnimatorListenerAdapter() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.GlowPadView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GlowPadView.this.ping();
                GlowPadView.this.switchToState(0, GlowPadView.this.mWaveCenterX, GlowPadView.this.mWaveCenterY);
                GlowPadView.this.dispatchOnFinishFinalAnimation();
            }
        };
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.GlowPadView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                GlowPadView.this.invalidate();
            }
        };
        this.mTargetUpdateListener = new AnimatorListenerAdapter() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.GlowPadView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (GlowPadView.this.mNewTargetResources != 0) {
                    GlowPadView.this.internalSetTargetResources(GlowPadView.this.mNewTargetResources);
                    GlowPadView.this.mNewTargetResources = 0;
                    GlowPadView.this.hideTargets(false, false);
                }
                GlowPadView.this.mAnimatingTargets = false;
            }
        };
        this.mGravity = 48;
        this.mInitialLayout = true;
        Resources resources = context.getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GlowPadView);
        this.mInnerRadius = typedArrayObtainStyledAttributes.getDimension(7, this.mInnerRadius);
        this.mOuterRadius = typedArrayObtainStyledAttributes.getDimension(8, this.mOuterRadius);
        this.mSnapMargin = typedArrayObtainStyledAttributes.getDimension(11, this.mSnapMargin);
        this.mVibrationDuration = typedArrayObtainStyledAttributes.getInt(14, this.mVibrationDuration);
        this.mFeedbackCount = typedArrayObtainStyledAttributes.getInt(4, this.mFeedbackCount);
        this.mAllowScaling = typedArrayObtainStyledAttributes.getBoolean(1, false);
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(6);
        this.mHandleDrawable = new TargetDrawable(resources, typedValuePeekValue != null ? typedValuePeekValue.resourceId : 0, 1);
        this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
        this.mOuterRing = new TargetDrawable(resources, getResourceId(typedArrayObtainStyledAttributes, 9), 1);
        this.mAlwaysTrackFinger = typedArrayObtainStyledAttributes.getBoolean(2, false);
        int resourceId = getResourceId(typedArrayObtainStyledAttributes, 10);
        Drawable drawable = resourceId != 0 ? resources.getDrawable(resourceId) : null;
        this.mGlowRadius = typedArrayObtainStyledAttributes.getDimension(5, 0.0f);
        TypedValue typedValue = new TypedValue();
        if (typedArrayObtainStyledAttributes.getValue(13, typedValue)) {
            internalSetTargetResources(typedValue.resourceId);
        }
        if (this.mTargetDrawables == null || this.mTargetDrawables.size() == 0) {
            throw new IllegalStateException("Must specify at least one target drawable");
        }
        if (typedArrayObtainStyledAttributes.getValue(12, typedValue)) {
            int i = typedValue.resourceId;
            if (i == 0) {
                throw new IllegalStateException("Must specify target descriptions");
            }
            setTargetDescriptionsResourceId(i);
        }
        if (typedArrayObtainStyledAttributes.getValue(3, typedValue)) {
            int i2 = typedValue.resourceId;
            if (i2 == 0) {
                throw new IllegalStateException("Must specify direction descriptions");
            }
            setDirectionDescriptionsResourceId(i2);
        }
        this.mGravity = typedArrayObtainStyledAttributes.getInt(0, 48);
        typedArrayObtainStyledAttributes.recycle();
        setVibrateEnabled(this.mVibrationDuration > 0);
        assignDefaultsIfNeeded();
        this.mPointCloud = new PointCloud(drawable);
        this.mPointCloud.makePointCloud(this.mInnerRadius, this.mOuterRadius);
        this.mPointCloud.glowManager.setRadius(this.mGlowRadius);
    }

    private int getResourceId(TypedArray a, int id) {
        TypedValue tv = a.peekValue(id);
        if (tv == null) {
            return 0;
        }
        return tv.resourceId;
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        return (int) (Math.max(this.mOuterRing.getWidth(), 2.0f * this.mOuterRadius) + this.mMaxTargetWidth);
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        return (int) (Math.max(this.mOuterRing.getHeight(), 2.0f * this.mOuterRadius) + this.mMaxTargetHeight);
    }

    protected int getScaledSuggestedMinimumWidth() {
        return (int) ((this.mRingScaleFactor * Math.max(this.mOuterRing.getWidth(), 2.0f * this.mOuterRadius)) + this.mMaxTargetWidth);
    }

    protected int getScaledSuggestedMinimumHeight() {
        return (int) ((this.mRingScaleFactor * Math.max(this.mOuterRing.getHeight(), 2.0f * this.mOuterRadius)) + this.mMaxTargetHeight);
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int mode = View.MeasureSpec.getMode(measureSpec);
        if (mode == Integer.MIN_VALUE) {
            int result = Math.min(specSize, desired);
            return result;
        }
        if (mode == 0) {
            return desired;
        }
        return specSize;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int computedWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
        int computedHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
        this.mRingScaleFactor = computeScaleFactor(minimumWidth, minimumHeight, computedWidth, computedHeight);
        int scaledWidth = getScaledSuggestedMinimumWidth();
        int scaledHeight = getScaledSuggestedMinimumHeight();
        computeInsets(computedWidth - scaledWidth, computedHeight - scaledHeight);
        setMeasuredDimension(computedWidth, computedHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToState(int state, float x, float y) {
        switch (state) {
            case 0:
                deactivateTargets();
                hideTargets(false, false);
                hideGlow(0, 0, 0.0f, null);
                startBackgroundAnimation(0, 0.0f);
                this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
                this.mHandleDrawable.setAlpha(1.0f);
                break;
            case 1:
                startBackgroundAnimation(0, 0.0f);
                break;
            case 2:
                this.mHandleDrawable.setAlpha(0.0f);
                deactivateTargets();
                showTargets(true);
                startBackgroundAnimation(200, 1.0f);
                setGrabbedState(1);
                AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
                if (accessibilityManager.isEnabled()) {
                    announceTargets();
                }
                break;
            case 3:
                this.mHandleDrawable.setAlpha(0.0f);
                break;
            case 4:
                this.mHandleDrawable.setAlpha(0.0f);
                showGlow(0, 0, 0.0f, null);
                break;
            case 5:
                doFinish();
                break;
        }
    }

    private void showGlow(int duration, int delay, float finalAlpha, Animator.AnimatorListener finishListener) {
        this.mGlowAnimations.cancel();
        this.mGlowAnimations.add(Tweener.to(this.mPointCloud.glowManager, duration, "ease", Ease.Cubic.easeIn, "delay", Integer.valueOf(delay), "alpha", Float.valueOf(finalAlpha), "onUpdate", this.mUpdateListener, "onComplete", finishListener));
        this.mGlowAnimations.start();
    }

    private void hideGlow(int duration, int delay, float finalAlpha, Animator.AnimatorListener finishListener) {
        this.mGlowAnimations.cancel();
        this.mGlowAnimations.add(Tweener.to(this.mPointCloud.glowManager, duration, "ease", Ease.Quart.easeOut, "delay", Integer.valueOf(delay), "alpha", Float.valueOf(finalAlpha), "x", Float.valueOf(0.0f), "y", Float.valueOf(0.0f), "onUpdate", this.mUpdateListener, "onComplete", finishListener));
        this.mGlowAnimations.start();
    }

    private void deactivateTargets() {
        int count = this.mTargetDrawables.size();
        for (int i = 0; i < count; i++) {
            TargetDrawable target = this.mTargetDrawables.get(i);
            target.setState(TargetDrawable.STATE_INACTIVE);
        }
        this.mActiveTarget = -1;
    }

    private void dispatchTriggerEvent(int whichTarget) {
        vibrate();
        if (this.mOnTriggerListener != null) {
            this.mOnTriggerListener.onTrigger(this, whichTarget);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnFinishFinalAnimation() {
        if (this.mOnTriggerListener != null) {
            this.mOnTriggerListener.onFinishFinalAnimation();
        }
    }

    private void doFinish() {
        int activeTarget = this.mActiveTarget;
        boolean targetHit = activeTarget != -1;
        if (!targetHit) {
            hideGlow(200, 0, 0.0f, this.mResetListenerWithPing);
            hideTargets(true, false);
        } else {
            highlightSelected(activeTarget);
            hideGlow(200, 1200, 0.0f, this.mResetListener);
            dispatchTriggerEvent(activeTarget);
            if (!this.mAlwaysTrackFinger) {
                this.mTargetAnimations.stop();
            }
        }
        setGrabbedState(0);
    }

    private void highlightSelected(int activeTarget) {
        this.mTargetDrawables.get(activeTarget).setState(TargetDrawable.STATE_ACTIVE);
        hideUnselected(activeTarget);
    }

    private void hideUnselected(int active) {
        for (int i = 0; i < this.mTargetDrawables.size(); i++) {
            if (i != active) {
                this.mTargetDrawables.get(i).setAlpha(0.0f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideTargets(boolean animate, boolean expanded) {
        this.mTargetAnimations.cancel();
        this.mAnimatingTargets = animate;
        char c = 0;
        int duration = animate ? 200 : 0;
        int delay = animate ? 200 : 0;
        float targetScale = expanded ? 1.0f : 0.8f;
        int length = this.mTargetDrawables.size();
        TimeInterpolator interpolator = Ease.Cubic.easeOut;
        int i = 0;
        while (i < length) {
            TargetDrawable target = this.mTargetDrawables.get(i);
            target.setState(TargetDrawable.STATE_INACTIVE);
            AnimationBundle animationBundle = this.mTargetAnimations;
            Object[] objArr = new Object[12];
            objArr[c] = "ease";
            objArr[1] = interpolator;
            objArr[2] = "alpha";
            objArr[3] = Float.valueOf(0.0f);
            objArr[4] = "scaleX";
            objArr[5] = Float.valueOf(targetScale);
            objArr[6] = "scaleY";
            objArr[7] = Float.valueOf(targetScale);
            objArr[8] = "delay";
            objArr[9] = Integer.valueOf(delay);
            objArr[10] = "onUpdate";
            objArr[11] = this.mUpdateListener;
            animationBundle.add(Tweener.to(target, duration, objArr));
            i++;
            c = 0;
        }
        float ringScaleTarget = (expanded ? 1.0f : 0.5f) * this.mRingScaleFactor;
        this.mTargetAnimations.add(Tweener.to(this.mOuterRing, duration, "ease", interpolator, "alpha", Float.valueOf(0.0f), "scaleX", Float.valueOf(ringScaleTarget), "scaleY", Float.valueOf(ringScaleTarget), "delay", Integer.valueOf(delay), "onUpdate", this.mUpdateListener, "onComplete", this.mTargetUpdateListener));
        this.mTargetAnimations.start();
    }

    private void showTargets(boolean animate) {
        this.mTargetAnimations.stop();
        this.mAnimatingTargets = animate;
        int delay = animate ? 50 : 0;
        int duration = animate ? 200 : 0;
        int length = this.mTargetDrawables.size();
        for (int i = 0; i < length; i++) {
            TargetDrawable target = this.mTargetDrawables.get(i);
            target.setState(TargetDrawable.STATE_INACTIVE);
            this.mTargetAnimations.add(Tweener.to(target, duration, "ease", Ease.Cubic.easeOut, "alpha", Float.valueOf(1.0f), "scaleX", Float.valueOf(1.0f), "scaleY", Float.valueOf(1.0f), "delay", Integer.valueOf(delay), "onUpdate", this.mUpdateListener));
        }
        float ringScale = this.mRingScaleFactor * 1.0f;
        this.mTargetAnimations.add(Tweener.to(this.mOuterRing, duration, "ease", Ease.Cubic.easeOut, "alpha", Float.valueOf(1.0f), "scaleX", Float.valueOf(ringScale), "scaleY", Float.valueOf(ringScale), "delay", Integer.valueOf(delay), "onUpdate", this.mUpdateListener, "onComplete", this.mTargetUpdateListener));
        this.mTargetAnimations.start();
    }

    private void vibrate() {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate(this.mVibrationDuration);
        }
    }

    private ArrayList<TargetDrawable> loadDrawableArray(int resourceId) {
        Resources res = getContext().getResources();
        TypedArray array = res.obtainTypedArray(resourceId);
        int count = array.length();
        ArrayList<TargetDrawable> drawables = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            TypedValue value = array.peekValue(i);
            TargetDrawable target = new TargetDrawable(res, value != null ? value.resourceId : 0, 3);
            drawables.add(target);
        }
        array.recycle();
        return drawables;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalSetTargetResources(int resourceId) {
        ArrayList<TargetDrawable> targets = loadDrawableArray(resourceId);
        this.mTargetDrawables = targets;
        this.mTargetResourceId = resourceId;
        int maxWidth = this.mHandleDrawable.getWidth();
        int maxHeight = this.mHandleDrawable.getHeight();
        int count = targets.size();
        for (int i = 0; i < count; i++) {
            TargetDrawable target = targets.get(i);
            maxWidth = Math.max(maxWidth, target.getWidth());
            maxHeight = Math.max(maxHeight, target.getHeight());
        }
        int i2 = this.mMaxTargetWidth;
        if (i2 != maxWidth || this.mMaxTargetHeight != maxHeight) {
            this.mMaxTargetWidth = maxWidth;
            this.mMaxTargetHeight = maxHeight;
            requestLayout();
        } else {
            updateTargetPositions(this.mWaveCenterX, this.mWaveCenterY);
            updatePointCloudPosition(this.mWaveCenterX, this.mWaveCenterY);
        }
    }

    public void setTargetDescriptionsResourceId(int resourceId) {
        this.mTargetDescriptionsResourceId = resourceId;
        if (this.mTargetDescriptions != null) {
            this.mTargetDescriptions.clear();
        }
    }

    public void setDirectionDescriptionsResourceId(int resourceId) {
        this.mDirectionDescriptionsResourceId = resourceId;
        if (this.mDirectionDescriptions != null) {
            this.mDirectionDescriptions.clear();
        }
    }

    public void setVibrateEnabled(boolean enabled) {
        if (enabled && this.mVibrator == null) {
            this.mVibrator = (Vibrator) getContext().getSystemService("vibrator");
        } else {
            this.mVibrator = null;
        }
    }

    public void ping() {
        if (this.mFeedbackCount > 0) {
            boolean doWaveAnimation = true;
            AnimationBundle waveAnimations = this.mWaveAnimations;
            if (waveAnimations.size() > 0 && waveAnimations.get(0).animator.isRunning()) {
                long t = waveAnimations.get(0).animator.getCurrentPlayTime();
                if (t < 675) {
                    doWaveAnimation = false;
                }
            }
            if (doWaveAnimation) {
                startWaveAnimation();
            }
        }
    }

    private void stopAndHideWaveAnimation() {
        this.mWaveAnimations.cancel();
        this.mPointCloud.waveManager.setAlpha(0.0f);
    }

    private void startWaveAnimation() {
        this.mWaveAnimations.cancel();
        this.mPointCloud.waveManager.setAlpha(1.0f);
        this.mPointCloud.waveManager.setRadius(this.mHandleDrawable.getWidth() / 2.0f);
        this.mWaveAnimations.add(Tweener.to(this.mPointCloud.waveManager, 1350L, "ease", Ease.Quad.easeOut, "delay", 0, "radius", Float.valueOf(2.0f * this.mOuterRadius), "onUpdate", this.mUpdateListener, "onComplete", new AnimatorListenerAdapter() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.GlowPadView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GlowPadView.this.mPointCloud.waveManager.setRadius(0.0f);
                GlowPadView.this.mPointCloud.waveManager.setAlpha(0.0f);
            }
        }));
        this.mWaveAnimations.start();
    }

    private void startBackgroundAnimation(int duration, float alpha) {
        Drawable background = getBackground();
        if (this.mAlwaysTrackFinger && background != null) {
            if (this.mBackgroundAnimator != null) {
                this.mBackgroundAnimator.animator.cancel();
            }
            this.mBackgroundAnimator = Tweener.to(background, duration, "ease", Ease.Cubic.easeIn, "alpha", Integer.valueOf((int) (255.0f * alpha)), "delay", 50);
            this.mBackgroundAnimator.animator.start();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        boolean handled = false;
        switch (action) {
            case 0:
            case 5:
                handleDown(event);
                handleMove(event);
                handled = true;
                break;
            case 1:
            case 6:
                handleMove(event);
                handleUp(event);
                handled = true;
                break;
            case 2:
                handleMove(event);
                handled = true;
                break;
            case 3:
                handleMove(event);
                handleCancel(event);
                handled = true;
                break;
        }
        invalidate();
        if (handled) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void updateGlowPosition(float x, float y) {
        float dx = x - this.mOuterRing.getX();
        float dy = y - this.mOuterRing.getY();
        float dx2 = dx * (1.0f / this.mRingScaleFactor);
        float dy2 = dy * (1.0f / this.mRingScaleFactor);
        this.mPointCloud.glowManager.setX(this.mOuterRing.getX() + dx2);
        this.mPointCloud.glowManager.setY(this.mOuterRing.getY() + dy2);
    }

    private void handleDown(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        float eventX = event.getX(actionIndex);
        float eventY = event.getY(actionIndex);
        switchToState(1, eventX, eventY);
        if (!trySwitchToFirstTouchState(eventX, eventY)) {
            this.mDragging = false;
        } else {
            this.mPointerId = event.getPointerId(actionIndex);
            updateGlowPosition(eventX, eventY);
        }
    }

    private void handleUp(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        if (event.getPointerId(actionIndex) == this.mPointerId) {
            switchToState(5, event.getX(actionIndex), event.getY(actionIndex));
        }
    }

    private void handleCancel(MotionEvent event) {
        int actionIndex = event.findPointerIndex(this.mPointerId);
        int actionIndex2 = actionIndex == -1 ? 0 : actionIndex;
        switchToState(5, event.getX(actionIndex2), event.getY(actionIndex2));
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00e4  */
    private void handleMove(MotionEvent event) {
        int historySize;
        float limitX;
        MotionEvent motionEvent = event;
        int historySize2 = event.getHistorySize();
        ArrayList<TargetDrawable> targets = this.mTargetDrawables;
        int ntargets = targets.size();
        int actionIndex = motionEvent.findPointerIndex(this.mPointerId);
        if (actionIndex == -1) {
            return;
        }
        float y = 0.0f;
        float y2 = 0.0f;
        int activeTarget = -1;
        int activeTarget2 = 0;
        while (activeTarget2 < historySize2 + 1) {
            float eventX = activeTarget2 < historySize2 ? motionEvent.getHistoricalX(actionIndex, activeTarget2) : motionEvent.getX(actionIndex);
            float eventY = activeTarget2 < historySize2 ? motionEvent.getHistoricalY(actionIndex, activeTarget2) : motionEvent.getY(actionIndex);
            float tx = eventX - this.mWaveCenterX;
            float ty = eventY - this.mWaveCenterY;
            int actionIndex2 = actionIndex;
            float touchRadius = (float) Math.hypot(tx, ty);
            float scale = touchRadius > this.mOuterRadius ? this.mOuterRadius / touchRadius : 1.0f;
            float limitX2 = tx * scale;
            y = ty * scale;
            int k = activeTarget2;
            double angleRad = Math.atan2(-ty, tx);
            if (!this.mDragging) {
                trySwitchToFirstTouchState(eventX, eventY);
            }
            if (this.mDragging) {
                float snapRadius = (this.mRingScaleFactor * this.mOuterRadius) - this.mSnapMargin;
                float snapDistance2 = snapRadius * snapRadius;
                int activeTarget3 = activeTarget;
                int activeTarget4 = 0;
                while (activeTarget4 < ntargets) {
                    int historySize3 = historySize2;
                    TargetDrawable target = targets.get(activeTarget4);
                    float eventX2 = eventX;
                    float eventY2 = eventY;
                    int activeTarget5 = activeTarget3;
                    float limitX3 = limitX2;
                    double targetMinRad = (((((double) activeTarget4) - 0.5d) * 2.0d) * 3.141592653589793d) / ((double) ntargets);
                    int i = activeTarget4;
                    float snapRadius2 = snapRadius;
                    double targetMaxRad = (((((double) activeTarget4) + 0.5d) * 2.0d) * 3.141592653589793d) / ((double) ntargets);
                    if (target.isEnabled()) {
                        boolean angleMatches = (angleRad > targetMinRad && angleRad <= targetMaxRad) || (angleRad + 6.283185307179586d > targetMinRad && 6.283185307179586d + angleRad <= targetMaxRad);
                        if (!angleMatches || dist2(tx, ty) <= snapDistance2) {
                            activeTarget3 = activeTarget5;
                        } else {
                            activeTarget3 = i;
                        }
                    } else {
                        activeTarget3 = activeTarget5;
                    }
                    activeTarget4 = i + 1;
                    historySize2 = historySize3;
                    eventX = eventX2;
                    eventY = eventY2;
                    limitX2 = limitX3;
                    snapRadius = snapRadius2;
                }
                historySize = historySize2;
                limitX = limitX2;
                activeTarget = activeTarget3;
            } else {
                historySize = historySize2;
                limitX = limitX2;
            }
            y2 = limitX;
            activeTarget2 = k + 1;
            actionIndex = actionIndex2;
            historySize2 = historySize;
            motionEvent = event;
        }
        float x = y2;
        float y3 = y;
        if (!this.mDragging) {
            return;
        }
        if (activeTarget != -1) {
            switchToState(4, x, y3);
            updateGlowPosition(x, y3);
        } else {
            switchToState(3, x, y3);
            updateGlowPosition(x, y3);
        }
        if (this.mActiveTarget != activeTarget) {
            if (this.mActiveTarget != -1) {
                TargetDrawable target2 = targets.get(this.mActiveTarget);
                target2.setState(TargetDrawable.STATE_INACTIVE);
            }
            if (activeTarget != -1) {
                TargetDrawable target3 = targets.get(activeTarget);
                target3.setState(TargetDrawable.STATE_FOCUSED);
                AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
                if (accessibilityManager.isEnabled()) {
                    String targetContentDescription = getTargetDescription(activeTarget);
                    announceForAccessibility(targetContentDescription);
                }
            }
        }
        this.mActiveTarget = activeTarget;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent event) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        if (accessibilityManager.isTouchExplorationEnabled()) {
            int action = event.getAction();
            if (action != 7) {
                switch (action) {
                    case 9:
                        event.setAction(0);
                        break;
                    case 10:
                        event.setAction(1);
                        break;
                }
            } else {
                event.setAction(2);
            }
            onTouchEvent(event);
            event.setAction(action);
        }
        super.onHoverEvent(event);
        return true;
    }

    private void setGrabbedState(int newState) {
        if (newState != this.mGrabbedState) {
            if (newState != 0) {
                vibrate();
            }
            this.mGrabbedState = newState;
            if (this.mOnTriggerListener != null) {
                if (newState == 0) {
                    this.mOnTriggerListener.onReleased(this, 1);
                } else {
                    this.mOnTriggerListener.onGrabbed(this, 1);
                }
                this.mOnTriggerListener.onGrabbedStateChange(this, newState);
            }
        }
    }

    private boolean trySwitchToFirstTouchState(float x, float y) {
        float tx = x - this.mWaveCenterX;
        float ty = y - this.mWaveCenterY;
        if (this.mAlwaysTrackFinger || dist2(tx, ty) <= getScaledGlowRadiusSquared()) {
            switchToState(2, x, y);
            updateGlowPosition(tx, ty);
            this.mDragging = true;
            return true;
        }
        return false;
    }

    private void assignDefaultsIfNeeded() {
        if (this.mOuterRadius == 0.0f) {
            this.mOuterRadius = Math.max(this.mOuterRing.getWidth(), this.mOuterRing.getHeight()) / 2.0f;
        }
        if (this.mSnapMargin == 0.0f) {
            this.mSnapMargin = TypedValue.applyDimension(1, 20.0f, getContext().getResources().getDisplayMetrics());
        }
        if (this.mInnerRadius == 0.0f) {
            this.mInnerRadius = this.mHandleDrawable.getWidth() / 10.0f;
        }
    }

    private void computeInsets(int dx, int dy) {
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, layoutDirection);
        int i = absoluteGravity & 7;
        if (i == 3) {
            this.mHorizontalInset = 0;
        } else if (i == 5) {
            this.mHorizontalInset = dx;
        } else {
            this.mHorizontalInset = dx / 2;
        }
        int i2 = absoluteGravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
        if (i2 == 48) {
            this.mVerticalInset = 0;
        } else if (i2 == 80) {
            this.mVerticalInset = dy;
        } else {
            this.mVerticalInset = dy / 2;
        }
    }

    private float computeScaleFactor(int desiredWidth, int desiredHeight, int actualWidth, int actualHeight) {
        if (!this.mAllowScaling) {
            return 1.0f;
        }
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, layoutDirection);
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        int i = absoluteGravity & 7;
        if (i != 3 && i != 5 && desiredWidth > actualWidth) {
            scaleX = ((actualWidth * 1.0f) - this.mMaxTargetWidth) / (desiredWidth - this.mMaxTargetWidth);
        }
        int i2 = absoluteGravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
        if (i2 != 48 && i2 != 80 && desiredHeight > actualHeight) {
            scaleY = ((1.0f * actualHeight) - this.mMaxTargetHeight) / (desiredHeight - this.mMaxTargetHeight);
        }
        return Math.min(scaleX, scaleY);
    }

    private float getRingWidth() {
        return this.mRingScaleFactor * Math.max(this.mOuterRing.getWidth(), 2.0f * this.mOuterRadius);
    }

    private float getRingHeight() {
        return this.mRingScaleFactor * Math.max(this.mOuterRing.getHeight(), 2.0f * this.mOuterRadius);
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int i = right - left;
        int i2 = bottom - top;
        float placementWidth = getRingWidth();
        float placementHeight = getRingHeight();
        float newWaveCenterX = this.mHorizontalInset + ((this.mMaxTargetWidth + placementWidth) / 2.0f);
        float newWaveCenterY = this.mVerticalInset + ((this.mMaxTargetHeight + placementHeight) / 2.0f);
        if (this.mInitialLayout) {
            stopAndHideWaveAnimation();
            hideTargets(false, false);
            this.mInitialLayout = false;
        }
        this.mOuterRing.setPositionX(newWaveCenterX);
        this.mOuterRing.setPositionY(newWaveCenterY);
        this.mPointCloud.setScale(this.mRingScaleFactor);
        this.mHandleDrawable.setPositionX(newWaveCenterX);
        this.mHandleDrawable.setPositionY(newWaveCenterY);
        updateTargetPositions(newWaveCenterX, newWaveCenterY);
        updatePointCloudPosition(newWaveCenterX, newWaveCenterY);
        updateGlowPosition(newWaveCenterX, newWaveCenterY);
        this.mWaveCenterX = newWaveCenterX;
        this.mWaveCenterY = newWaveCenterY;
    }

    private void updateTargetPositions(float centerX, float centerY) {
        ArrayList<TargetDrawable> targets = this.mTargetDrawables;
        int size = targets.size();
        float alpha = (float) ((-6.283185307179586d) / ((double) size));
        for (int i = 0; i < size; i++) {
            TargetDrawable targetIcon = targets.get(i);
            float angle = i * alpha;
            targetIcon.setPositionX(centerX);
            targetIcon.setPositionY(centerY);
            targetIcon.setX((getRingWidth() / 2.0f) * ((float) Math.cos(angle)));
            targetIcon.setY((getRingHeight() / 2.0f) * ((float) Math.sin(angle)));
        }
    }

    private void updatePointCloudPosition(float centerX, float centerY) {
        this.mPointCloud.setCenter(centerX, centerY);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mPointCloud.draw(canvas);
        this.mOuterRing.draw(canvas);
        int ntargets = this.mTargetDrawables.size();
        for (int i = 0; i < ntargets; i++) {
            TargetDrawable target = this.mTargetDrawables.get(i);
            if (target != null) {
                target.draw(canvas);
            }
        }
        this.mHandleDrawable.draw(canvas);
    }

    public void setOnTriggerListener(OnTriggerListener listener) {
        this.mOnTriggerListener = listener;
    }

    private float square(float d) {
        return d * d;
    }

    private float dist2(float dx, float dy) {
        return (dx * dx) + (dy * dy);
    }

    private float getScaledGlowRadiusSquared() {
        float scaledTapRadius;
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            scaledTapRadius = 1.3f * this.mGlowRadius;
        } else {
            scaledTapRadius = this.mGlowRadius;
        }
        return square(scaledTapRadius);
    }

    private void announceTargets() {
        StringBuilder utterance = new StringBuilder();
        int targetCount = this.mTargetDrawables.size();
        for (int i = 0; i < targetCount; i++) {
            String targetDescription = getTargetDescription(i);
            String directionDescription = getDirectionDescription(i);
            if (!TextUtils.isEmpty(targetDescription) && !TextUtils.isEmpty(directionDescription)) {
                String text = String.format(directionDescription, targetDescription);
                utterance.append(text);
            }
        }
        if (utterance.length() > 0) {
            announceForAccessibility(utterance.toString());
        }
    }

    private String getTargetDescription(int index) {
        if (this.mTargetDescriptions == null || this.mTargetDescriptions.isEmpty()) {
            this.mTargetDescriptions = loadDescriptions(this.mTargetDescriptionsResourceId);
            if (this.mTargetDrawables.size() != this.mTargetDescriptions.size()) {
                Log.w("GlowPadView", "The number of target drawables must be equal to the number of target descriptions.");
                return null;
            }
        }
        return this.mTargetDescriptions.get(index);
    }

    private String getDirectionDescription(int index) {
        if (this.mDirectionDescriptions == null || this.mDirectionDescriptions.isEmpty()) {
            this.mDirectionDescriptions = loadDescriptions(this.mDirectionDescriptionsResourceId);
            if (this.mTargetDrawables.size() != this.mDirectionDescriptions.size()) {
                Log.w("GlowPadView", "The number of target drawables must be equal to the number of direction descriptions.");
                return null;
            }
        }
        return this.mDirectionDescriptions.get(index);
    }

    private ArrayList<String> loadDescriptions(int resourceId) {
        TypedArray array = getContext().getResources().obtainTypedArray(resourceId);
        int count = array.length();
        ArrayList<String> targetContentDescriptions = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String contentDescription = array.getString(i);
            targetContentDescriptions.add(contentDescription);
        }
        array.recycle();
        return targetContentDescriptions;
    }

    public int getResourceIdForTarget(int index) {
        TargetDrawable drawable = this.mTargetDrawables.get(index);
        if (drawable == null) {
            return 0;
        }
        return drawable.getResourceId();
    }
}
