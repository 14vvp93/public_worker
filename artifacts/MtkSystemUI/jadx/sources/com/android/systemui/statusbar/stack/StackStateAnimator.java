package com.android.systemui.statusbar.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.StatusBarIconView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class StackStateAnimator {
    public static final int ANIMATION_DURATION_HEADS_UP_APPEAR_CLOSED = (int) (550.0f * HeadsUpAppearInterpolator.getFractionUntilOvershoot());
    private ValueAnimator mBottomOverScrollAnimator;
    private long mCurrentAdditionalDelay;
    private int mCurrentLastNotAddedIndex;
    private long mCurrentLength;
    private final int mGoToFullShadeAppearingTranslation;
    private int mHeadsUpAppearHeightBottom;
    public NotificationStackScrollLayout mHostLayout;
    private final int mPulsingAppearingTranslation;
    private boolean mShadeExpanded;
    private NotificationShelf mShelf;
    private ValueAnimator mTopOverScrollAnimator;
    private final ExpandableViewState mTmpState = new ExpandableViewState();
    private ArrayList<NotificationStackScrollLayout.AnimationEvent> mNewEvents = new ArrayList<>();
    private ArrayList<View> mNewAddChildren = new ArrayList<>();
    private HashSet<View> mHeadsUpAppearChildren = new HashSet<>();
    private HashSet<View> mHeadsUpDisappearChildren = new HashSet<>();
    private HashSet<Animator> mAnimatorSet = new HashSet<>();
    private Stack<AnimatorListenerAdapter> mAnimationListenerPool = new Stack<>();
    private AnimationFilter mAnimationFilter = new AnimationFilter();
    private ArrayList<ExpandableView> mTransientViewsToRemove = new ArrayList<>();
    private int[] mTmpLocation = new int[2];
    private final AnimationProperties mAnimationProperties = new AnimationProperties() { // from class: com.android.systemui.statusbar.stack.StackStateAnimator.1
        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return StackStateAnimator.this.mAnimationFilter;
        }

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimatorListenerAdapter getAnimationFinishListener() {
            return StackStateAnimator.this.getGlobalAnimationFinishedListener();
        }

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public boolean wasAdded(View view) {
            return StackStateAnimator.this.mNewAddChildren.contains(view);
        }

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public Interpolator getCustomInterpolator(View child, Property property) {
            if (StackStateAnimator.this.mHeadsUpAppearChildren.contains(child) && View.TRANSLATION_Y.equals(property)) {
                return Interpolators.HEADS_UP_APPEAR;
            }
            return null;
        }
    };

    public StackStateAnimator(NotificationStackScrollLayout hostLayout) {
        this.mHostLayout = hostLayout;
        this.mGoToFullShadeAppearingTranslation = hostLayout.getContext().getResources().getDimensionPixelSize(R.dimen.go_to_full_shade_appearing_translation);
        this.mPulsingAppearingTranslation = hostLayout.getContext().getResources().getDimensionPixelSize(R.dimen.pulsing_notification_appear_translation);
    }

    public boolean isRunning() {
        return !this.mAnimatorSet.isEmpty();
    }

    public void startAnimationForEvents(ArrayList<NotificationStackScrollLayout.AnimationEvent> mAnimationEvents, StackScrollState finalState, long additionalDelay) {
        processAnimationEvents(mAnimationEvents, finalState);
        int childCount = this.mHostLayout.getChildCount();
        this.mAnimationFilter.applyCombination(this.mNewEvents);
        this.mCurrentAdditionalDelay = additionalDelay;
        this.mCurrentLength = NotificationStackScrollLayout.AnimationEvent.combineLength(this.mNewEvents);
        this.mCurrentLastNotAddedIndex = findLastNotAddedIndex(finalState);
        for (int i = 0; i < childCount; i++) {
            ExpandableView child = (ExpandableView) this.mHostLayout.getChildAt(i);
            ExpandableViewState viewState = finalState.getViewStateForView(child);
            if (viewState != null && child.getVisibility() != 8 && !applyWithoutAnimation(child, viewState, finalState)) {
                initAnimationProperties(finalState, child, viewState);
                viewState.animateTo(child, this.mAnimationProperties);
            }
        }
        if (!isRunning()) {
            onAnimationFinished();
        }
        this.mHeadsUpAppearChildren.clear();
        this.mHeadsUpDisappearChildren.clear();
        this.mNewEvents.clear();
        this.mNewAddChildren.clear();
    }

    private void initAnimationProperties(StackScrollState finalState, ExpandableView child, ExpandableViewState viewState) {
        boolean wasAdded = this.mAnimationProperties.wasAdded(child);
        this.mAnimationProperties.duration = this.mCurrentLength;
        adaptDurationWhenGoingToFullShade(child, viewState, wasAdded);
        this.mAnimationProperties.delay = 0L;
        if (!wasAdded) {
            if (this.mAnimationFilter.hasDelays) {
                if (viewState.yTranslation == child.getTranslationY() && viewState.zTranslation == child.getTranslationZ() && viewState.alpha == child.getAlpha() && viewState.height == child.getActualHeight() && viewState.clipTopAmount == child.getClipTopAmount() && viewState.dark == child.isDark() && viewState.shadowAlpha == child.getShadowAlpha()) {
                    return;
                }
            } else {
                return;
            }
        }
        this.mAnimationProperties.delay = this.mCurrentAdditionalDelay + calculateChildAnimationDelay(viewState, finalState);
    }

    private void adaptDurationWhenGoingToFullShade(ExpandableView child, ExpandableViewState viewState, boolean wasAdded) {
        if (wasAdded && this.mAnimationFilter.hasGoToFullShadeEvent) {
            child.setTranslationY(child.getTranslationY() + this.mGoToFullShadeAppearingTranslation);
            float longerDurationFactor = viewState.notGoneIndex - this.mCurrentLastNotAddedIndex;
            this.mAnimationProperties.duration = 514 + ((long) (100.0f * ((float) Math.pow(longerDurationFactor, 0.699999988079071d))));
        }
    }

    private boolean applyWithoutAnimation(ExpandableView child, ExpandableViewState viewState, StackScrollState finalState) {
        if (this.mShadeExpanded || ViewState.isAnimatingY(child) || this.mHeadsUpDisappearChildren.contains(child) || this.mHeadsUpAppearChildren.contains(child) || NotificationStackScrollLayout.isPinnedHeadsUp(child)) {
            return false;
        }
        viewState.applyToView(child);
        return true;
    }

    private int findLastNotAddedIndex(StackScrollState finalState) {
        int childCount = this.mHostLayout.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            ExpandableView child = (ExpandableView) this.mHostLayout.getChildAt(i);
            ExpandableViewState viewState = finalState.getViewStateForView(child);
            if (viewState != null && child.getVisibility() != 8 && !this.mNewAddChildren.contains(child)) {
                return viewState.notGoneIndex;
            }
        }
        return -1;
    }

    private long calculateChildAnimationDelay(ExpandableViewState viewState, StackScrollState finalState) {
        View viewAfterChangingView;
        StackStateAnimator stackStateAnimator = this;
        if (stackStateAnimator.mAnimationFilter.hasGoToFullShadeEvent) {
            return calculateDelayGoToFullShade(viewState);
        }
        if (stackStateAnimator.mAnimationFilter.customDelay != -1) {
            return stackStateAnimator.mAnimationFilter.customDelay;
        }
        long minDelay = 0;
        for (NotificationStackScrollLayout.AnimationEvent event : stackStateAnimator.mNewEvents) {
            long delayPerElement = 80;
            switch (event.animationType) {
                case 0:
                    int difference = viewState.notGoneIndex;
                    int changingIndex = finalState.getViewStateForView(event.changingView).notGoneIndex;
                    long delay = ((long) (2 - Math.max(0, Math.min(2, Math.abs(difference - changingIndex) - 1)))) * 80;
                    minDelay = Math.max(delay, minDelay);
                    stackStateAnimator = this;
                    break;
                case 2:
                    delayPerElement = 32;
                case 1:
                    int ownIndex = viewState.notGoneIndex;
                    boolean noNextView = event.viewAfterChangingView == null;
                    if (noNextView) {
                        viewAfterChangingView = stackStateAnimator.mHostLayout.getLastChildNotGone();
                    } else {
                        viewAfterChangingView = event.viewAfterChangingView;
                    }
                    if (viewAfterChangingView != null) {
                        int nextIndex = finalState.getViewStateForView(viewAfterChangingView).notGoneIndex;
                        if (ownIndex >= nextIndex) {
                            ownIndex++;
                        }
                        int difference2 = Math.abs(ownIndex - nextIndex);
                        long delay2 = ((long) Math.max(0, Math.min(2, difference2 - 1))) * delayPerElement;
                        minDelay = Math.max(delay2, minDelay);
                        stackStateAnimator = this;
                    }
                    break;
                default:
                    stackStateAnimator = this;
                    break;
            }
        }
        return minDelay;
    }

    private long calculateDelayGoToFullShade(ExpandableViewState viewState) {
        int shelfIndex = this.mShelf.getNotGoneIndex();
        float index = viewState.notGoneIndex;
        long result = 0;
        if (index > shelfIndex) {
            float diff = index - shelfIndex;
            result = 0 + ((long) (((double) (((float) Math.pow(diff, 0.699999988079071d)) * 48.0f)) * 0.25d));
            index = shelfIndex;
        }
        return result + ((long) (48.0f * ((float) Math.pow(index, 0.699999988079071d))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AnimatorListenerAdapter getGlobalAnimationFinishedListener() {
        if (!this.mAnimationListenerPool.empty()) {
            return this.mAnimationListenerPool.pop();
        }
        return new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.StackStateAnimator.2
            private boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                StackStateAnimator.this.mAnimatorSet.remove(animation);
                if (StackStateAnimator.this.mAnimatorSet.isEmpty() && !this.mWasCancelled) {
                    StackStateAnimator.this.onAnimationFinished();
                }
                StackStateAnimator.this.mAnimationListenerPool.push(this);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mWasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                this.mWasCancelled = false;
                StackStateAnimator.this.mAnimatorSet.add(animation);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationFinished() {
        this.mHostLayout.onChildAnimationFinished();
        for (ExpandableView transientViewsToRemove : this.mTransientViewsToRemove) {
            transientViewsToRemove.getTransientContainer().removeTransientView(transientViewsToRemove);
        }
        this.mTransientViewsToRemove.clear();
    }

    /* JADX WARN: Code duplicated, block: B:75:0x01ef A[PHI: r6
      0x01ef: PHI (r6v4 'needsAnimation' boolean) = (r6v3 'needsAnimation' boolean), (r6v6 'needsAnimation' boolean) binds: [B:68:0x01a8, B:73:0x01be] A[DONT_GENERATE, DONT_INLINE]] */
    private void processAnimationEvents(ArrayList<NotificationStackScrollLayout.AnimationEvent> animationEvents, StackScrollState finalState) {
        float targetLocation;
        boolean needsAnimation;
        StackScrollState stackScrollState = finalState;
        for (NotificationStackScrollLayout.AnimationEvent event : animationEvents) {
            final ExpandableView changingView = (ExpandableView) event.changingView;
            if (event.animationType == 0) {
                ExpandableViewState viewState = stackScrollState.getViewStateForView(changingView);
                if (viewState != null && !viewState.gone) {
                    viewState.applyToView(changingView);
                    this.mNewAddChildren.add(changingView);
                    this.mNewEvents.add(event);
                    stackScrollState = finalState;
                }
            } else {
                if (event.animationType == 1) {
                    if (changingView.getVisibility() != 0) {
                        removeTransientView(changingView);
                    } else {
                        ExpandableViewState viewState2 = stackScrollState.getViewStateForView(event.viewAfterChangingView);
                        int actualHeight = changingView.getActualHeight();
                        float fMax = -1.0f;
                        if (viewState2 != null) {
                            float ownPosition = changingView.getTranslationY();
                            if ((changingView instanceof ExpandableNotificationRow) && (event.viewAfterChangingView instanceof ExpandableNotificationRow)) {
                                ExpandableNotificationRow changingRow = (ExpandableNotificationRow) changingView;
                                ExpandableNotificationRow nextRow = (ExpandableNotificationRow) event.viewAfterChangingView;
                                if (changingRow.isRemoved() && changingRow.wasChildInGroupWhenRemoved() && !nextRow.isChildInGroup()) {
                                    ownPosition = changingRow.getTranslationWhenRemoved();
                                }
                            }
                            float translationDirection = ((viewState2.yTranslation - ((actualHeight / 2.0f) + ownPosition)) * 2.0f) / actualHeight;
                            fMax = Math.max(Math.min(translationDirection, 1.0f), -1.0f);
                        }
                        float translationDirection2 = fMax;
                        changingView.performRemoveAnimation(464L, 0L, translationDirection2, false, 0.0f, new Runnable() { // from class: com.android.systemui.statusbar.stack.StackStateAnimator.3
                            @Override // java.lang.Runnable
                            public void run() {
                                StackStateAnimator.removeTransientView(changingView);
                            }
                        }, null);
                    }
                } else if (event.animationType == 2) {
                    if (Math.abs(changingView.getTranslation()) == changingView.getWidth() && changingView.getTransientContainer() != null) {
                        changingView.getTransientContainer().removeTransientView(changingView);
                    }
                } else if (event.animationType == 13) {
                    ((ExpandableNotificationRow) event.changingView).prepareExpansionChanged(stackScrollState);
                } else if (event.animationType == 19) {
                    this.mTmpState.copyFrom(stackScrollState.getViewStateForView(changingView));
                    this.mTmpState.yTranslation += this.mPulsingAppearingTranslation;
                    this.mTmpState.alpha = 0.0f;
                    this.mTmpState.applyToView(changingView);
                } else if (event.animationType == 20) {
                    ExpandableViewState viewState3 = stackScrollState.getViewStateForView(changingView);
                    viewState3.yTranslation += this.mPulsingAppearingTranslation;
                    viewState3.alpha = 0.0f;
                } else if (event.animationType == 14) {
                    this.mTmpState.copyFrom(stackScrollState.getViewStateForView(changingView));
                    if (event.headsUpFromBottom) {
                        this.mTmpState.yTranslation = this.mHeadsUpAppearHeightBottom;
                    } else {
                        this.mTmpState.yTranslation = 0.0f;
                        changingView.performAddAnimation(0L, ANIMATION_DURATION_HEADS_UP_APPEAR_CLOSED, true);
                    }
                    this.mHeadsUpAppearChildren.add(changingView);
                    this.mTmpState.applyToView(changingView);
                } else if (event.animationType == 15 || event.animationType == 16) {
                    this.mHeadsUpDisappearChildren.add(changingView);
                    Runnable endRunnable = null;
                    int extraDelay = event.animationType == 16 ? com.android.systemui.plugins.R.styleable.AppCompatTheme_windowNoTitle : 0;
                    if (changingView.getParent() == null) {
                        this.mHostLayout.addTransientView(changingView, 0);
                        changingView.setTransientContainer(this.mHostLayout);
                        this.mTmpState.initFrom(changingView);
                        this.mTmpState.yTranslation = 0.0f;
                        this.mAnimationFilter.animateY = true;
                        this.mAnimationProperties.delay = extraDelay + com.android.systemui.plugins.R.styleable.AppCompatTheme_windowNoTitle;
                        this.mAnimationProperties.duration = 300L;
                        this.mTmpState.animateTo(changingView, this.mAnimationProperties);
                        endRunnable = new Runnable() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$StackStateAnimator$583ttX1KKMjNzUBxpRTbg9B4uQA
                            @Override // java.lang.Runnable
                            public final void run() {
                                StackStateAnimator.removeTransientView(changingView);
                            }
                        };
                    }
                    Runnable endRunnable2 = endRunnable;
                    boolean needsAnimation2 = true;
                    if (changingView instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow row = (ExpandableNotificationRow) changingView;
                        if (row.isDismissed()) {
                            needsAnimation2 = false;
                        }
                        StatusBarIconView icon = row.getEntry().icon;
                        if (icon.getParent() != null) {
                            icon.getLocationOnScreen(this.mTmpLocation);
                            float iconPosition = (this.mTmpLocation[0] - icon.getTranslationX()) + ViewState.getFinalTranslationX(icon) + (icon.getWidth() * 0.25f);
                            this.mHostLayout.getLocationOnScreen(this.mTmpLocation);
                            float targetLocation2 = iconPosition - this.mTmpLocation[0];
                            needsAnimation = needsAnimation2;
                            targetLocation = targetLocation2;
                        } else {
                            targetLocation = 0.0f;
                            needsAnimation = needsAnimation2;
                        }
                    } else {
                        targetLocation = 0.0f;
                        needsAnimation = needsAnimation2;
                    }
                    if (needsAnimation) {
                        changingView.performRemoveAnimation(420L, extraDelay, 0.0f, true, targetLocation, endRunnable2, getGlobalAnimationFinishedListener());
                    } else if (endRunnable2 != null) {
                        endRunnable2.run();
                    }
                }
                this.mNewEvents.add(event);
                stackScrollState = finalState;
            }
        }
    }

    public static void removeTransientView(ExpandableView viewToRemove) {
        if (viewToRemove.getTransientContainer() != null) {
            viewToRemove.getTransientContainer().removeTransientView(viewToRemove);
        }
    }

    public void animateOverScrollToAmount(float targetAmount, final boolean onTop, final boolean isRubberbanded) {
        float startOverScrollAmount = this.mHostLayout.getCurrentOverScrollAmount(onTop);
        if (targetAmount == startOverScrollAmount) {
            return;
        }
        cancelOverScrollAnimators(onTop);
        ValueAnimator overScrollAnimator = ValueAnimator.ofFloat(startOverScrollAmount, targetAmount);
        overScrollAnimator.setDuration(360L);
        overScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.stack.StackStateAnimator.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentOverScroll = ((Float) animation.getAnimatedValue()).floatValue();
                StackStateAnimator.this.mHostLayout.setOverScrollAmount(currentOverScroll, onTop, false, false, isRubberbanded);
            }
        });
        overScrollAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        overScrollAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.StackStateAnimator.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (onTop) {
                    StackStateAnimator.this.mTopOverScrollAnimator = null;
                } else {
                    StackStateAnimator.this.mBottomOverScrollAnimator = null;
                }
            }
        });
        overScrollAnimator.start();
        if (onTop) {
            this.mTopOverScrollAnimator = overScrollAnimator;
        } else {
            this.mBottomOverScrollAnimator = overScrollAnimator;
        }
    }

    public void cancelOverScrollAnimators(boolean onTop) {
        ValueAnimator currentAnimator = onTop ? this.mTopOverScrollAnimator : this.mBottomOverScrollAnimator;
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }
    }

    public void setHeadsUpAppearHeightBottom(int headsUpAppearHeightBottom) {
        this.mHeadsUpAppearHeightBottom = headsUpAppearHeightBottom;
    }

    public void setShadeExpanded(boolean shadeExpanded) {
        this.mShadeExpanded = shadeExpanded;
    }

    public void setShelf(NotificationShelf shelf) {
        this.mShelf = shelf;
    }
}
