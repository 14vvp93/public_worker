package com.android.systemui.statusbar.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.policy.HeadsUpUtil;

/* JADX INFO: loaded from: classes.dex */
public class ViewState {
    protected static final AnimationProperties NO_NEW_ANIMATIONS = new AnimationProperties() { // from class: com.android.systemui.statusbar.stack.ViewState.1
        AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    private static final AnimatableProperty SCALE_X_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.stack.ViewState.2
        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationStartTag() {
            return R.id.scale_x_animator_start_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationEndTag() {
            return R.id.scale_x_animator_end_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimatorTag() {
            return R.id.scale_x_animator_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public Property getProperty() {
            return View.SCALE_X;
        }
    };
    private static final AnimatableProperty SCALE_Y_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.stack.ViewState.3
        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationStartTag() {
            return R.id.scale_y_animator_start_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationEndTag() {
            return R.id.scale_y_animator_end_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimatorTag() {
            return R.id.scale_y_animator_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public Property getProperty() {
            return View.SCALE_Y;
        }
    };
    public float alpha;
    public boolean gone;
    public boolean hidden;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float xTranslation;
    public float yTranslation;
    public float zTranslation;

    public void copyFrom(ViewState viewState) {
        this.alpha = viewState.alpha;
        this.xTranslation = viewState.xTranslation;
        this.yTranslation = viewState.yTranslation;
        this.zTranslation = viewState.zTranslation;
        this.gone = viewState.gone;
        this.hidden = viewState.hidden;
        this.scaleX = viewState.scaleX;
        this.scaleY = viewState.scaleY;
    }

    public void initFrom(View view) {
        this.alpha = view.getAlpha();
        this.xTranslation = view.getTranslationX();
        this.yTranslation = view.getTranslationY();
        this.zTranslation = view.getTranslationZ();
        this.gone = view.getVisibility() == 8;
        this.hidden = view.getVisibility() == 4;
        this.scaleX = view.getScaleX();
        this.scaleY = view.getScaleY();
    }

    public void applyToView(View view) {
        int newLayerType;
        if (this.gone) {
            return;
        }
        boolean animatingX = isAnimating(view, R.id.translation_x_animator_tag);
        if (animatingX) {
            updateAnimationX(view);
        } else if (view.getTranslationX() != this.xTranslation) {
            view.setTranslationX(this.xTranslation);
        }
        boolean animatingY = isAnimating(view, R.id.translation_y_animator_tag);
        if (animatingY) {
            updateAnimationY(view);
        } else if (view.getTranslationY() != this.yTranslation) {
            view.setTranslationY(this.yTranslation);
        }
        boolean animatingZ = isAnimating(view, R.id.translation_z_animator_tag);
        if (animatingZ) {
            updateAnimationZ(view);
        } else if (view.getTranslationZ() != this.zTranslation) {
            view.setTranslationZ(this.zTranslation);
        }
        boolean animatingScaleX = isAnimating(view, SCALE_X_PROPERTY);
        if (animatingScaleX) {
            updateAnimation(view, SCALE_X_PROPERTY, this.scaleX);
        } else if (view.getScaleX() != this.scaleX) {
            view.setScaleX(this.scaleX);
        }
        boolean animatingScaleY = isAnimating(view, SCALE_Y_PROPERTY);
        if (animatingScaleY) {
            updateAnimation(view, SCALE_Y_PROPERTY, this.scaleY);
        } else if (view.getScaleY() != this.scaleY) {
            view.setScaleY(this.scaleY);
        }
        int oldVisibility = view.getVisibility();
        boolean becomesInvisible = this.alpha == 0.0f || (this.hidden && !(isAnimating(view) && oldVisibility == 0));
        boolean animatingAlpha = isAnimating(view, R.id.alpha_animator_tag);
        if (animatingAlpha) {
            updateAlphaAnimation(view);
        } else if (view.getAlpha() != this.alpha) {
            boolean becomesFullyVisible = this.alpha == 1.0f;
            boolean newLayerTypeIsHardware = (becomesInvisible || becomesFullyVisible || !view.hasOverlappingRendering()) ? false : true;
            int layerType = view.getLayerType();
            if (newLayerTypeIsHardware) {
                newLayerType = 2;
            } else {
                newLayerType = 0;
            }
            if (layerType != newLayerType) {
                view.setLayerType(newLayerType, null);
            }
            view.setAlpha(this.alpha);
        }
        int newVisibility = becomesInvisible ? 4 : 0;
        if (newVisibility != oldVisibility) {
            if (!(view instanceof ExpandableView) || !((ExpandableView) view).willBeGone()) {
                view.setVisibility(newVisibility);
            }
        }
    }

    public boolean isAnimating(View view) {
        return isAnimating(view, R.id.translation_x_animator_tag) || isAnimating(view, R.id.translation_y_animator_tag) || isAnimating(view, R.id.translation_z_animator_tag) || isAnimating(view, R.id.alpha_animator_tag) || isAnimating(view, SCALE_X_PROPERTY) || isAnimating(view, SCALE_Y_PROPERTY);
    }

    private static boolean isAnimating(View view, int tag) {
        return getChildTag(view, tag) != null;
    }

    public static boolean isAnimating(View view, AnimatableProperty property) {
        return getChildTag(view, property.getAnimatorTag()) != null;
    }

    public void animateTo(View child, AnimationProperties animationProperties) {
        boolean wasVisible = child.getVisibility() == 0;
        float alpha = this.alpha;
        if (!wasVisible && ((alpha != 0.0f || child.getAlpha() != 0.0f) && !this.gone && !this.hidden)) {
            child.setVisibility(0);
        }
        float childAlpha = child.getAlpha();
        boolean alphaChanging = this.alpha != childAlpha;
        if (child instanceof ExpandableView) {
            alphaChanging &= true ^ ((ExpandableView) child).willBeGone();
        }
        if (child.getTranslationX() != this.xTranslation) {
            startXTranslationAnimation(child, animationProperties);
        } else {
            abortAnimation(child, R.id.translation_x_animator_tag);
        }
        if (child.getTranslationY() != this.yTranslation) {
            startYTranslationAnimation(child, animationProperties);
        } else {
            abortAnimation(child, R.id.translation_y_animator_tag);
        }
        if (child.getTranslationZ() != this.zTranslation) {
            startZTranslationAnimation(child, animationProperties);
        } else {
            abortAnimation(child, R.id.translation_z_animator_tag);
        }
        if (child.getScaleX() != this.scaleX) {
            PropertyAnimator.startAnimation(child, SCALE_X_PROPERTY, this.scaleX, animationProperties);
        } else {
            abortAnimation(child, SCALE_X_PROPERTY.getAnimatorTag());
        }
        if (child.getScaleY() != this.scaleY) {
            PropertyAnimator.startAnimation(child, SCALE_Y_PROPERTY, this.scaleY, animationProperties);
        } else {
            abortAnimation(child, SCALE_Y_PROPERTY.getAnimatorTag());
        }
        if (alphaChanging) {
            startAlphaAnimation(child, animationProperties);
        } else {
            abortAnimation(child, R.id.alpha_animator_tag);
        }
    }

    private void updateAlphaAnimation(View view) {
        startAlphaAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void startAlphaAnimation(final View child, AnimationProperties properties) {
        Float previousStartValue = (Float) getChildTag(child, R.id.alpha_animator_start_value_tag);
        Float previousEndValue = (Float) getChildTag(child, R.id.alpha_animator_end_value_tag);
        final float newEndValue = this.alpha;
        if (previousEndValue != null && previousEndValue.floatValue() == newEndValue) {
            return;
        }
        ObjectAnimator previousAnimator = (ObjectAnimator) getChildTag(child, R.id.alpha_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.animateAlpha) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                float relativeDiff = newEndValue - previousEndValue.floatValue();
                float newStartValue = previousStartValue.floatValue() + relativeDiff;
                values[0].setFloatValues(newStartValue, newEndValue);
                child.setTag(R.id.alpha_animator_start_value_tag, Float.valueOf(newStartValue));
                child.setTag(R.id.alpha_animator_end_value_tag, Float.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setAlpha(newEndValue);
            if (newEndValue == 0.0f) {
                child.setVisibility(4);
            }
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.ALPHA, child.getAlpha(), newEndValue);
        animator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        child.setLayerType(2, null);
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ViewState.4
            public boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                child.setLayerType(0, null);
                if (newEndValue == 0.0f && !this.mWasCancelled) {
                    child.setVisibility(4);
                }
                child.setTag(R.id.alpha_animator_tag, null);
                child.setTag(R.id.alpha_animator_start_value_tag, null);
                child.setTag(R.id.alpha_animator_end_value_tag, null);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mWasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                this.mWasCancelled = false;
            }
        });
        long newDuration = cancelAnimatorAndGetNewDuration(properties.duration, previousAnimator);
        animator.setDuration(newDuration);
        if (properties.delay > 0 && (previousAnimator == null || previousAnimator.getAnimatedFraction() == 0.0f)) {
            animator.setStartDelay(properties.delay);
        }
        AnimatorListenerAdapter listener = properties.getAnimationFinishListener();
        if (listener != null) {
            animator.addListener(listener);
        }
        startAnimator(animator, listener);
        child.setTag(R.id.alpha_animator_tag, animator);
        child.setTag(R.id.alpha_animator_start_value_tag, Float.valueOf(child.getAlpha()));
        child.setTag(R.id.alpha_animator_end_value_tag, Float.valueOf(newEndValue));
    }

    private void updateAnimationZ(View view) {
        startZTranslationAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void updateAnimation(View view, AnimatableProperty property, float endValue) {
        PropertyAnimator.startAnimation(view, property, endValue, NO_NEW_ANIMATIONS);
    }

    private void startZTranslationAnimation(final View child, AnimationProperties properties) {
        Float previousStartValue = (Float) getChildTag(child, R.id.translation_z_animator_start_value_tag);
        Float previousEndValue = (Float) getChildTag(child, R.id.translation_z_animator_end_value_tag);
        float newEndValue = this.zTranslation;
        if (previousEndValue != null && previousEndValue.floatValue() == newEndValue) {
            return;
        }
        ObjectAnimator previousAnimator = (ObjectAnimator) getChildTag(child, R.id.translation_z_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.animateZ) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                float relativeDiff = newEndValue - previousEndValue.floatValue();
                float newStartValue = previousStartValue.floatValue() + relativeDiff;
                values[0].setFloatValues(newStartValue, newEndValue);
                child.setTag(R.id.translation_z_animator_start_value_tag, Float.valueOf(newStartValue));
                child.setTag(R.id.translation_z_animator_end_value_tag, Float.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setTranslationZ(newEndValue);
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_Z, child.getTranslationZ(), newEndValue);
        animator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        long newDuration = cancelAnimatorAndGetNewDuration(properties.duration, previousAnimator);
        animator.setDuration(newDuration);
        if (properties.delay > 0 && (previousAnimator == null || previousAnimator.getAnimatedFraction() == 0.0f)) {
            animator.setStartDelay(properties.delay);
        }
        AnimatorListenerAdapter listener = properties.getAnimationFinishListener();
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ViewState.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                child.setTag(R.id.translation_z_animator_tag, null);
                child.setTag(R.id.translation_z_animator_start_value_tag, null);
                child.setTag(R.id.translation_z_animator_end_value_tag, null);
            }
        });
        startAnimator(animator, listener);
        child.setTag(R.id.translation_z_animator_tag, animator);
        child.setTag(R.id.translation_z_animator_start_value_tag, Float.valueOf(child.getTranslationZ()));
        child.setTag(R.id.translation_z_animator_end_value_tag, Float.valueOf(newEndValue));
    }

    private void updateAnimationX(View view) {
        startXTranslationAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void startXTranslationAnimation(final View child, AnimationProperties properties) {
        Float previousStartValue = (Float) getChildTag(child, R.id.translation_x_animator_start_value_tag);
        Float previousEndValue = (Float) getChildTag(child, R.id.translation_x_animator_end_value_tag);
        float newEndValue = this.xTranslation;
        if (previousEndValue != null && previousEndValue.floatValue() == newEndValue) {
            return;
        }
        ObjectAnimator previousAnimator = (ObjectAnimator) getChildTag(child, R.id.translation_x_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.animateX) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                float relativeDiff = newEndValue - previousEndValue.floatValue();
                float newStartValue = previousStartValue.floatValue() + relativeDiff;
                values[0].setFloatValues(newStartValue, newEndValue);
                child.setTag(R.id.translation_x_animator_start_value_tag, Float.valueOf(newStartValue));
                child.setTag(R.id.translation_x_animator_end_value_tag, Float.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setTranslationX(newEndValue);
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_X, child.getTranslationX(), newEndValue);
        Interpolator customInterpolator = properties.getCustomInterpolator(child, View.TRANSLATION_X);
        Interpolator interpolator = customInterpolator != null ? customInterpolator : Interpolators.FAST_OUT_SLOW_IN;
        animator.setInterpolator(interpolator);
        long newDuration = cancelAnimatorAndGetNewDuration(properties.duration, previousAnimator);
        animator.setDuration(newDuration);
        if (properties.delay > 0 && (previousAnimator == null || previousAnimator.getAnimatedFraction() == 0.0f)) {
            animator.setStartDelay(properties.delay);
        }
        AnimatorListenerAdapter listener = properties.getAnimationFinishListener();
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ViewState.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                child.setTag(R.id.translation_x_animator_tag, null);
                child.setTag(R.id.translation_x_animator_start_value_tag, null);
                child.setTag(R.id.translation_x_animator_end_value_tag, null);
            }
        });
        startAnimator(animator, listener);
        child.setTag(R.id.translation_x_animator_tag, animator);
        child.setTag(R.id.translation_x_animator_start_value_tag, Float.valueOf(child.getTranslationX()));
        child.setTag(R.id.translation_x_animator_end_value_tag, Float.valueOf(newEndValue));
    }

    private void updateAnimationY(View view) {
        startYTranslationAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void startYTranslationAnimation(final View child, AnimationProperties properties) {
        Float previousStartValue = (Float) getChildTag(child, R.id.translation_y_animator_start_value_tag);
        Float previousEndValue = (Float) getChildTag(child, R.id.translation_y_animator_end_value_tag);
        float newEndValue = this.yTranslation;
        if (previousEndValue != null && previousEndValue.floatValue() == newEndValue) {
            return;
        }
        ObjectAnimator previousAnimator = (ObjectAnimator) getChildTag(child, R.id.translation_y_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.shouldAnimateY(child)) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                float relativeDiff = newEndValue - previousEndValue.floatValue();
                float newStartValue = previousStartValue.floatValue() + relativeDiff;
                values[0].setFloatValues(newStartValue, newEndValue);
                child.setTag(R.id.translation_y_animator_start_value_tag, Float.valueOf(newStartValue));
                child.setTag(R.id.translation_y_animator_end_value_tag, Float.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setTranslationY(newEndValue);
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_Y, child.getTranslationY(), newEndValue);
        Interpolator customInterpolator = properties.getCustomInterpolator(child, View.TRANSLATION_Y);
        Interpolator interpolator = customInterpolator != null ? customInterpolator : Interpolators.FAST_OUT_SLOW_IN;
        animator.setInterpolator(interpolator);
        long newDuration = cancelAnimatorAndGetNewDuration(properties.duration, previousAnimator);
        animator.setDuration(newDuration);
        if (properties.delay > 0 && (previousAnimator == null || previousAnimator.getAnimatedFraction() == 0.0f)) {
            animator.setStartDelay(properties.delay);
        }
        AnimatorListenerAdapter listener = properties.getAnimationFinishListener();
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ViewState.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                HeadsUpUtil.setIsClickedHeadsUpNotification(child, false);
                child.setTag(R.id.translation_y_animator_tag, null);
                child.setTag(R.id.translation_y_animator_start_value_tag, null);
                child.setTag(R.id.translation_y_animator_end_value_tag, null);
                ViewState.this.onYTranslationAnimationFinished(child);
            }
        });
        startAnimator(animator, listener);
        child.setTag(R.id.translation_y_animator_tag, animator);
        child.setTag(R.id.translation_y_animator_start_value_tag, Float.valueOf(child.getTranslationY()));
        child.setTag(R.id.translation_y_animator_end_value_tag, Float.valueOf(newEndValue));
    }

    protected void onYTranslationAnimationFinished(View view) {
        if (this.hidden && !this.gone) {
            view.setVisibility(4);
        }
    }

    public static void startAnimator(Animator animator, AnimatorListenerAdapter listener) {
        if (listener != null) {
            listener.onAnimationStart(animator);
        }
        animator.start();
    }

    public static <T> T getChildTag(View view, int i) {
        return (T) view.getTag(i);
    }

    protected void abortAnimation(View child, int animatorTag) {
        Animator previousAnimator = (Animator) getChildTag(child, animatorTag);
        if (previousAnimator != null) {
            previousAnimator.cancel();
        }
    }

    public static long cancelAnimatorAndGetNewDuration(long duration, ValueAnimator previousAnimator) {
        if (previousAnimator == null) {
            return duration;
        }
        long newDuration = Math.max(previousAnimator.getDuration() - previousAnimator.getCurrentPlayTime(), duration);
        previousAnimator.cancel();
        return newDuration;
    }

    public static float getFinalTranslationX(View view) {
        if (view == null) {
            return 0.0f;
        }
        ValueAnimator xAnimator = (ValueAnimator) getChildTag(view, R.id.translation_x_animator_tag);
        if (xAnimator == null) {
            return view.getTranslationX();
        }
        return ((Float) getChildTag(view, R.id.translation_x_animator_end_value_tag)).floatValue();
    }

    public static float getFinalTranslationY(View view) {
        if (view == null) {
            return 0.0f;
        }
        ValueAnimator yAnimator = (ValueAnimator) getChildTag(view, R.id.translation_y_animator_tag);
        if (yAnimator == null) {
            return view.getTranslationY();
        }
        return ((Float) getChildTag(view, R.id.translation_y_animator_end_value_tag)).floatValue();
    }

    public static float getFinalTranslationZ(View view) {
        if (view == null) {
            return 0.0f;
        }
        ValueAnimator zAnimator = (ValueAnimator) getChildTag(view, R.id.translation_z_animator_tag);
        if (zAnimator == null) {
            return view.getTranslationZ();
        }
        return ((Float) getChildTag(view, R.id.translation_z_animator_end_value_tag)).floatValue();
    }

    public static boolean isAnimatingY(View child) {
        return getChildTag(child, R.id.translation_y_animator_tag) != null;
    }

    public void cancelAnimations(View view) {
        Animator animator = (Animator) getChildTag(view, R.id.translation_x_animator_tag);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) getChildTag(view, R.id.translation_y_animator_tag);
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = (Animator) getChildTag(view, R.id.translation_z_animator_tag);
        if (animator3 != null) {
            animator3.cancel();
        }
        Animator animator4 = (Animator) getChildTag(view, R.id.alpha_animator_tag);
        if (animator4 != null) {
            animator4.cancel();
        }
    }
}
