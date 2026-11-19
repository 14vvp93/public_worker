package com.android.settingslib.animation;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
public class AppearAnimationUtils implements AppearAnimationCreator<View> {
    protected boolean mAppearing;
    protected final float mDelayScale;
    private final long mDuration;
    private final Interpolator mInterpolator;
    private final AppearAnimationProperties mProperties;
    protected RowTranslationScaler mRowTranslationScaler;
    private final float mStartTranslation;

    public interface RowTranslationScaler {
        float getRowTranslationScale(int i, int i2);
    }

    public AppearAnimationUtils(Context ctx) {
        this(ctx, 220L, 1.0f, 1.0f, AnimationUtils.loadInterpolator(ctx, R.interpolator.linear_out_slow_in));
    }

    public AppearAnimationUtils(Context ctx, long duration, float translationScaleFactor, float delayScaleFactor, Interpolator interpolator) {
        this.mProperties = new AppearAnimationProperties();
        this.mInterpolator = interpolator;
        this.mStartTranslation = ctx.getResources().getDimensionPixelOffset(com.android.settingslib.R.dimen.appear_y_translation_start) * translationScaleFactor;
        this.mDelayScale = delayScaleFactor;
        this.mDuration = duration;
        this.mAppearing = true;
    }

    public void startAnimation2d(View[][] objects, Runnable finishListener) {
        startAnimation2d(objects, finishListener, this);
    }

    public void startAnimation(View[] objects, Runnable finishListener) {
        startAnimation(objects, finishListener, this);
    }

    public <T> void startAnimation2d(T[][] objects, Runnable finishListener, AppearAnimationCreator<T> creator) {
        AppearAnimationProperties properties = getDelays((Object[][]) objects);
        startAnimations(properties, (Object[][]) objects, finishListener, (AppearAnimationCreator) creator);
    }

    public <T> void startAnimation(T[] objects, Runnable finishListener, AppearAnimationCreator<T> creator) {
        AppearAnimationProperties properties = getDelays(objects);
        startAnimations(properties, objects, finishListener, creator);
    }

    private <T> void startAnimations(AppearAnimationProperties properties, T[] objects, Runnable finishListener, AppearAnimationCreator<T> creator) {
        float rowTranslationScale;
        if (properties.maxDelayRowIndex == -1 || properties.maxDelayColIndex == -1) {
            finishListener.run();
            return;
        }
        for (int row = 0; row < properties.delays.length; row++) {
            long[] columns = properties.delays[row];
            long delay = columns[0];
            Runnable endRunnable = null;
            if (properties.maxDelayRowIndex == row && properties.maxDelayColIndex == 0) {
                endRunnable = finishListener;
            }
            Runnable endRunnable2 = endRunnable;
            if (this.mRowTranslationScaler != null) {
                rowTranslationScale = this.mRowTranslationScaler.getRowTranslationScale(row, properties.delays.length);
            } else {
                rowTranslationScale = 1.0f;
            }
            float translationScale = rowTranslationScale;
            float translation = translationScale * this.mStartTranslation;
            creator.createAnimation(objects[row], delay, this.mDuration, this.mAppearing ? translation : -translation, this.mAppearing, this.mInterpolator, endRunnable2);
        }
    }

    private <T> void startAnimations(AppearAnimationProperties properties, T[][] objects, Runnable finishListener, AppearAnimationCreator<T> creator) {
        float translationScale;
        if (properties.maxDelayRowIndex == -1 || properties.maxDelayColIndex == -1) {
            finishListener.run();
            return;
        }
        for (int row = 0; row < properties.delays.length; row++) {
            long[] columns = properties.delays[row];
            if (this.mRowTranslationScaler != null) {
                translationScale = this.mRowTranslationScaler.getRowTranslationScale(row, properties.delays.length);
            } else {
                translationScale = 1.0f;
            }
            float translation = this.mStartTranslation * translationScale;
            for (int col = 0; col < columns.length; col++) {
                long delay = columns[col];
                Runnable endRunnable = null;
                if (properties.maxDelayRowIndex == row && properties.maxDelayColIndex == col) {
                    endRunnable = finishListener;
                }
                creator.createAnimation(objects[row][col], delay, this.mDuration, this.mAppearing ? translation : -translation, this.mAppearing, this.mInterpolator, endRunnable);
            }
        }
    }

    private <T> AppearAnimationProperties getDelays(T[] items) {
        this.mProperties.maxDelayColIndex = -1;
        this.mProperties.maxDelayRowIndex = -1;
        this.mProperties.delays = new long[items.length][];
        long maxDelay = -1;
        for (int row = 0; row < items.length; row++) {
            this.mProperties.delays[row] = new long[1];
            long delay = calculateDelay(row, 0);
            this.mProperties.delays[row][0] = delay;
            if (items[row] != null && delay > maxDelay) {
                maxDelay = delay;
                this.mProperties.maxDelayColIndex = 0;
                this.mProperties.maxDelayRowIndex = row;
            }
        }
        return this.mProperties;
    }

    private <T> AppearAnimationProperties getDelays(T[][] items) {
        this.mProperties.maxDelayColIndex = -1;
        this.mProperties.maxDelayRowIndex = -1;
        this.mProperties.delays = new long[items.length][];
        long maxDelay = -1;
        int row = 0;
        while (row < items.length) {
            T[] columns = items[row];
            this.mProperties.delays[row] = new long[columns.length];
            long maxDelay2 = maxDelay;
            for (int col = 0; col < columns.length; col++) {
                long delay = calculateDelay(row, col);
                this.mProperties.delays[row][col] = delay;
                if (items[row][col] != null && delay > maxDelay2) {
                    maxDelay2 = delay;
                    this.mProperties.maxDelayColIndex = col;
                    this.mProperties.maxDelayRowIndex = row;
                }
            }
            row++;
            maxDelay = maxDelay2;
        }
        return this.mProperties;
    }

    protected long calculateDelay(int row, int col) {
        return (long) ((((double) (row * 40)) + (((double) col) * (Math.pow(row, 0.4d) + 0.4d) * 20.0d)) * ((double) this.mDelayScale));
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public float getStartTranslation() {
        return this.mStartTranslation;
    }

    @Override // com.android.settingslib.animation.AppearAnimationCreator
    public void createAnimation(final View view, long delay, long duration, float translationY, boolean appearing, Interpolator interpolator, final Runnable endRunnable) {
        RenderNodeAnimator alphaAnimRt;
        if (view != null) {
            view.setAlpha(appearing ? 0.0f : 1.0f);
            view.setTranslationY(appearing ? translationY : 0.0f);
            float targetAlpha = appearing ? 1.0f : 0.0f;
            if (view.isHardwareAccelerated()) {
                alphaAnimRt = new RenderNodeAnimator(11, targetAlpha);
                alphaAnimRt.setTarget(view);
            } else {
                alphaAnimRt = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), targetAlpha);
            }
            RenderNodeAnimator renderNodeAnimator = alphaAnimRt;
            renderNodeAnimator.setInterpolator(interpolator);
            renderNodeAnimator.setDuration(duration);
            renderNodeAnimator.setStartDelay(delay);
            if (view.hasOverlappingRendering()) {
                view.setLayerType(2, null);
                renderNodeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.settingslib.animation.AppearAnimationUtils.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        view.setLayerType(0, null);
                    }
                });
            }
            if (endRunnable != null) {
                renderNodeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.settingslib.animation.AppearAnimationUtils.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        endRunnable.run();
                    }
                });
            }
            renderNodeAnimator.start();
            startTranslationYAnimation(view, delay, duration, appearing ? 0.0f : translationY, interpolator);
        }
    }

    public static void startTranslationYAnimation(View view, long delay, long duration, float endTranslationY, Interpolator interpolator) {
        RenderNodeAnimator translationAnimRt;
        if (view.isHardwareAccelerated()) {
            translationAnimRt = new RenderNodeAnimator(1, endTranslationY);
            translationAnimRt.setTarget(view);
        } else {
            translationAnimRt = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, view.getTranslationY(), endTranslationY);
        }
        translationAnimRt.setInterpolator(interpolator);
        translationAnimRt.setDuration(duration);
        translationAnimRt.setStartDelay(delay);
        translationAnimRt.start();
    }

    public class AppearAnimationProperties {
        public long[][] delays;
        public int maxDelayColIndex;
        public int maxDelayRowIndex;

        public AppearAnimationProperties() {
        }
    }
}
