package com.android.systemui.statusbar.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableViewState extends ViewState {
    public boolean belowSpeedBump;
    public int clipTopAmount;
    public boolean dark;
    public boolean dimmed;
    public boolean headsUpIsVisible;
    public int height;
    public boolean hideSensitive;
    public boolean inShelf;
    public int location;
    public int notGoneIndex;
    public float shadowAlpha;

    @Override // com.android.systemui.statusbar.stack.ViewState
    public void copyFrom(ViewState viewState) {
        super.copyFrom(viewState);
        if (viewState instanceof ExpandableViewState) {
            ExpandableViewState svs = (ExpandableViewState) viewState;
            this.height = svs.height;
            this.dimmed = svs.dimmed;
            this.shadowAlpha = svs.shadowAlpha;
            this.dark = svs.dark;
            this.hideSensitive = svs.hideSensitive;
            this.belowSpeedBump = svs.belowSpeedBump;
            this.clipTopAmount = svs.clipTopAmount;
            this.notGoneIndex = svs.notGoneIndex;
            this.location = svs.location;
            this.headsUpIsVisible = svs.headsUpIsVisible;
        }
    }

    @Override // com.android.systemui.statusbar.stack.ViewState
    public void applyToView(View view) {
        super.applyToView(view);
        if (view instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) view;
            int height = expandableView.getActualHeight();
            int newHeight = this.height;
            if (height != newHeight) {
                expandableView.setActualHeight(newHeight, false);
            }
            float shadowAlpha = expandableView.getShadowAlpha();
            float newShadowAlpha = this.shadowAlpha;
            if (shadowAlpha != newShadowAlpha) {
                expandableView.setShadowAlpha(newShadowAlpha);
            }
            expandableView.setDimmed(this.dimmed, false);
            expandableView.setHideSensitive(this.hideSensitive, false, 0L, 0L);
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            expandableView.setDark(this.dark, false, 0L);
            float oldClipTopAmount = expandableView.getClipTopAmount();
            if (oldClipTopAmount != this.clipTopAmount) {
                expandableView.setClipTopAmount(this.clipTopAmount);
            }
            expandableView.setTransformingInShelf(false);
            expandableView.setInShelf(this.inShelf);
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    @Override // com.android.systemui.statusbar.stack.ViewState
    public void animateTo(View child, AnimationProperties properties) {
        super.animateTo(child, properties);
        if (!(child instanceof ExpandableView)) {
            return;
        }
        ExpandableView expandableView = (ExpandableView) child;
        AnimationFilter animationFilter = properties.getAnimationFilter();
        if (this.height != expandableView.getActualHeight()) {
            startHeightAnimation(expandableView, properties);
        } else {
            abortAnimation(child, R.id.height_animator_tag);
        }
        if (this.shadowAlpha != expandableView.getShadowAlpha()) {
            startShadowAlphaAnimation(expandableView, properties);
        } else {
            abortAnimation(child, R.id.shadow_alpha_animator_tag);
        }
        if (this.clipTopAmount != expandableView.getClipTopAmount()) {
            startInsetAnimation(expandableView, properties);
        } else {
            abortAnimation(child, R.id.top_inset_animator_tag);
        }
        expandableView.setDimmed(this.dimmed, animationFilter.animateDimmed);
        expandableView.setBelowSpeedBump(this.belowSpeedBump);
        expandableView.setHideSensitive(this.hideSensitive, animationFilter.animateHideSensitive, properties.delay, properties.duration);
        expandableView.setDark(this.dark, animationFilter.animateDark, properties.delay);
        if (properties.wasAdded(child) && !this.hidden) {
            expandableView.performAddAnimation(properties.delay, properties.duration, false);
        }
        if (!expandableView.isInShelf() && this.inShelf) {
            expandableView.setTransformingInShelf(true);
        }
        expandableView.setInShelf(this.inShelf);
        if (this.headsUpIsVisible) {
            expandableView.setHeadsUpIsVisible();
        }
    }

    private void startHeightAnimation(final ExpandableView child, AnimationProperties properties) {
        Integer previousStartValue = (Integer) getChildTag(child, R.id.height_animator_start_value_tag);
        Integer previousEndValue = (Integer) getChildTag(child, R.id.height_animator_end_value_tag);
        int newEndValue = this.height;
        if (previousEndValue != null && previousEndValue.intValue() == newEndValue) {
            return;
        }
        ValueAnimator previousAnimator = (ValueAnimator) getChildTag(child, R.id.height_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.animateHeight) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                int relativeDiff = newEndValue - previousEndValue.intValue();
                int newStartValue = previousStartValue.intValue() + relativeDiff;
                values[0].setIntValues(newStartValue, newEndValue);
                child.setTag(R.id.height_animator_start_value_tag, Integer.valueOf(newStartValue));
                child.setTag(R.id.height_animator_end_value_tag, Integer.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setActualHeight(newEndValue, false);
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(child.getActualHeight(), newEndValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.stack.ExpandableViewState.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                child.setActualHeight(((Integer) animation.getAnimatedValue()).intValue(), false);
            }
        });
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
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ExpandableViewState.2
            boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                child.setTag(R.id.height_animator_tag, null);
                child.setTag(R.id.height_animator_start_value_tag, null);
                child.setTag(R.id.height_animator_end_value_tag, null);
                child.setActualHeightAnimating(false);
                if (!this.mWasCancelled && (child instanceof ExpandableNotificationRow)) {
                    ((ExpandableNotificationRow) child).setGroupExpansionChanging(false);
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
        startAnimator(animator, listener);
        child.setTag(R.id.height_animator_tag, animator);
        child.setTag(R.id.height_animator_start_value_tag, Integer.valueOf(child.getActualHeight()));
        child.setTag(R.id.height_animator_end_value_tag, Integer.valueOf(newEndValue));
        child.setActualHeightAnimating(true);
    }

    private void startShadowAlphaAnimation(final ExpandableView child, AnimationProperties properties) {
        Float previousStartValue = (Float) getChildTag(child, R.id.shadow_alpha_animator_start_value_tag);
        Float previousEndValue = (Float) getChildTag(child, R.id.shadow_alpha_animator_end_value_tag);
        float newEndValue = this.shadowAlpha;
        if (previousEndValue != null && previousEndValue.floatValue() == newEndValue) {
            return;
        }
        ValueAnimator previousAnimator = (ValueAnimator) getChildTag(child, R.id.shadow_alpha_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.animateShadowAlpha) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                float relativeDiff = newEndValue - previousEndValue.floatValue();
                float newStartValue = previousStartValue.floatValue() + relativeDiff;
                values[0].setFloatValues(newStartValue, newEndValue);
                child.setTag(R.id.shadow_alpha_animator_start_value_tag, Float.valueOf(newStartValue));
                child.setTag(R.id.shadow_alpha_animator_end_value_tag, Float.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setShadowAlpha(newEndValue);
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(child.getShadowAlpha(), newEndValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.stack.ExpandableViewState.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                child.setShadowAlpha(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
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
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ExpandableViewState.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                child.setTag(R.id.shadow_alpha_animator_tag, null);
                child.setTag(R.id.shadow_alpha_animator_start_value_tag, null);
                child.setTag(R.id.shadow_alpha_animator_end_value_tag, null);
            }
        });
        startAnimator(animator, listener);
        child.setTag(R.id.shadow_alpha_animator_tag, animator);
        child.setTag(R.id.shadow_alpha_animator_start_value_tag, Float.valueOf(child.getShadowAlpha()));
        child.setTag(R.id.shadow_alpha_animator_end_value_tag, Float.valueOf(newEndValue));
    }

    private void startInsetAnimation(final ExpandableView child, AnimationProperties properties) {
        Integer previousStartValue = (Integer) getChildTag(child, R.id.top_inset_animator_start_value_tag);
        Integer previousEndValue = (Integer) getChildTag(child, R.id.top_inset_animator_end_value_tag);
        int newEndValue = this.clipTopAmount;
        if (previousEndValue != null && previousEndValue.intValue() == newEndValue) {
            return;
        }
        ValueAnimator previousAnimator = (ValueAnimator) getChildTag(child, R.id.top_inset_animator_tag);
        AnimationFilter filter = properties.getAnimationFilter();
        if (!filter.animateTopInset) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                int relativeDiff = newEndValue - previousEndValue.intValue();
                int newStartValue = previousStartValue.intValue() + relativeDiff;
                values[0].setIntValues(newStartValue, newEndValue);
                child.setTag(R.id.top_inset_animator_start_value_tag, Integer.valueOf(newStartValue));
                child.setTag(R.id.top_inset_animator_end_value_tag, Integer.valueOf(newEndValue));
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            child.setClipTopAmount(newEndValue);
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(child.getClipTopAmount(), newEndValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.stack.ExpandableViewState.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                child.setClipTopAmount(((Integer) animation.getAnimatedValue()).intValue());
            }
        });
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
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.ExpandableViewState.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                child.setTag(R.id.top_inset_animator_tag, null);
                child.setTag(R.id.top_inset_animator_start_value_tag, null);
                child.setTag(R.id.top_inset_animator_end_value_tag, null);
            }
        });
        startAnimator(animator, listener);
        child.setTag(R.id.top_inset_animator_tag, animator);
        child.setTag(R.id.top_inset_animator_start_value_tag, Integer.valueOf(child.getClipTopAmount()));
        child.setTag(R.id.top_inset_animator_end_value_tag, Integer.valueOf(newEndValue));
    }

    public static int getFinalActualHeight(ExpandableView view) {
        if (view == null) {
            return 0;
        }
        ValueAnimator heightAnimator = (ValueAnimator) getChildTag(view, R.id.height_animator_tag);
        if (heightAnimator == null) {
            return view.getActualHeight();
        }
        return ((Integer) getChildTag(view, R.id.height_animator_end_value_tag)).intValue();
    }

    @Override // com.android.systemui.statusbar.stack.ViewState
    public void cancelAnimations(View view) {
        super.cancelAnimations(view);
        Animator animator = (Animator) getChildTag(view, R.id.height_animator_tag);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) getChildTag(view, R.id.shadow_alpha_animator_tag);
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = (Animator) getChildTag(view, R.id.top_inset_animator_tag);
        if (animator3 != null) {
            animator3.cancel();
        }
    }
}
