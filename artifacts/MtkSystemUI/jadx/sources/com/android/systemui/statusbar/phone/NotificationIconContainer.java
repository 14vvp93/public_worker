package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.AlphaOptimizedFrameLayout;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.stack.AnimationFilter;
import com.android.systemui.statusbar.stack.AnimationProperties;
import com.android.systemui.statusbar.stack.ViewState;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class NotificationIconContainer extends AlphaOptimizedFrameLayout {
    private int[] mAbsolutePosition;
    private int mActualLayoutWidth;
    private float mActualPaddingEnd;
    private float mActualPaddingStart;
    private int mAddAnimationStartIndex;
    private boolean mAnimationsEnabled;
    private int mCannedAnimationStartIndex;
    private boolean mChangingViewPositions;
    private boolean mDark;
    private boolean mDisallowNextAnimation;
    private int mDotPadding;
    private IconState mFirstVisibleIconState;
    private int mIconSize;
    private final HashMap<View, IconState> mIconStates;
    private boolean mIsStaticLayout;
    private StatusBarIconView mIsolatedIcon;
    private View mIsolatedIconForAnimation;
    private Rect mIsolatedIconLocation;
    private IconState mLastVisibleIconState;
    private int mNumDots;
    private float mOpenedAmount;
    private int mOverflowWidth;
    private ArrayMap<String, ArrayList<StatusBarIcon>> mReplacingIcons;
    private int mSpeedBumpIndex;
    private int mStaticDotDiameter;
    private int mStaticDotRadius;
    private float mVisualOverflowStart;
    private static final AnimationProperties DOT_ANIMATION_PROPERTIES = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.1
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateX();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(200);
    private static final AnimationProperties ICON_ANIMATION_PROPERTIES = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.2
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateY().animateAlpha().animateScale();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(100).setCustomInterpolator(View.TRANSLATION_Y, Interpolators.ICON_OVERSHOT);
    private static final AnimationProperties sTempProperties = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.3
        private AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    private static final AnimationProperties ADD_ICON_PROPERTIES = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.4
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateAlpha();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(200).setDelay(50);
    private static final AnimationProperties UNISOLATION_PROPERTY_OTHERS = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.5
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateAlpha();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(110);
    private static final AnimationProperties UNISOLATION_PROPERTY = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.6
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateX();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(110);

    public NotificationIconContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsStaticLayout = true;
        this.mIconStates = new HashMap<>();
        this.mActualLayoutWidth = Integer.MIN_VALUE;
        this.mActualPaddingEnd = -2.14748365E9f;
        this.mActualPaddingStart = -2.14748365E9f;
        this.mAddAnimationStartIndex = -1;
        this.mCannedAnimationStartIndex = -1;
        this.mSpeedBumpIndex = -1;
        this.mOpenedAmount = 0.0f;
        this.mAnimationsEnabled = true;
        this.mAbsolutePosition = new int[2];
        initDimens();
        setWillNotDraw(true);
    }

    private void initDimens() {
        this.mDotPadding = getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        this.mStaticDotRadius = getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius);
        this.mStaticDotDiameter = 2 * this.mStaticDotRadius;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(getActualPaddingStart(), 0.0f, getLayoutEnd(), getHeight(), paint);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initDimens();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float centerY = getHeight() / 2.0f;
        this.mIconSize = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int top = (int) (centerY - (height / 2.0f));
            child.layout(0, top, width, top + height);
            if (i == 0) {
                setIconSize(child.getWidth());
            }
        }
        getLocationOnScreen(this.mAbsolutePosition);
        if (this.mIsStaticLayout) {
            updateState();
        }
    }

    private void setIconSize(int size) {
        this.mIconSize = size;
        this.mOverflowWidth = this.mIconSize + (0 * (this.mStaticDotDiameter + this.mDotPadding));
    }

    private void updateState() {
        resetViewStates();
        calculateIconTranslations();
        applyIconStates();
    }

    public void applyIconStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            ViewState childState = this.mIconStates.get(child);
            if (childState != null) {
                childState.applyToView(child);
            }
        }
        this.mAddAnimationStartIndex = -1;
        this.mCannedAnimationStartIndex = -1;
        this.mDisallowNextAnimation = false;
        this.mIsolatedIconForAnimation = null;
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        boolean isReplacingIcon = isReplacingIcon(child);
        if (!this.mChangingViewPositions) {
            IconState v = new IconState();
            if (isReplacingIcon) {
                v.justAdded = false;
                v.justReplaced = true;
            }
            this.mIconStates.put(child, v);
        }
        int childIndex = indexOfChild(child);
        if (childIndex < getChildCount() - 1 && !isReplacingIcon && this.mIconStates.get(getChildAt(childIndex + 1)).iconAppearAmount > 0.0f) {
            if (this.mAddAnimationStartIndex < 0) {
                this.mAddAnimationStartIndex = childIndex;
            } else {
                this.mAddAnimationStartIndex = Math.min(this.mAddAnimationStartIndex, childIndex);
            }
        }
        if (child instanceof StatusBarIconView) {
            ((StatusBarIconView) child).setDark(this.mDark, false, 0L);
        }
    }

    private boolean isReplacingIcon(View child) {
        if (this.mReplacingIcons == null || !(child instanceof StatusBarIconView)) {
            return false;
        }
        StatusBarIconView iconView = (StatusBarIconView) child;
        Icon sourceIcon = iconView.getSourceIcon();
        String groupKey = iconView.getNotification().getGroupKey();
        ArrayList<StatusBarIcon> statusBarIcons = this.mReplacingIcons.get(groupKey);
        if (statusBarIcons != null) {
            StatusBarIcon replacedIcon = statusBarIcons.get(0);
            if (sourceIcon.sameAs(replacedIcon.icon)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (child instanceof StatusBarIconView) {
            boolean isReplacingIcon = isReplacingIcon(child);
            final StatusBarIconView icon = (StatusBarIconView) child;
            if (icon.getVisibleState() != 2 && child.getVisibility() == 0 && isReplacingIcon) {
                int animationStartIndex = findFirstViewIndexAfter(icon.getTranslationX());
                if (this.mAddAnimationStartIndex < 0) {
                    this.mAddAnimationStartIndex = animationStartIndex;
                } else {
                    this.mAddAnimationStartIndex = Math.min(this.mAddAnimationStartIndex, animationStartIndex);
                }
            }
            if (!this.mChangingViewPositions) {
                this.mIconStates.remove(child);
                if (!isReplacingIcon) {
                    addTransientView(icon, 0);
                    boolean isIsolatedIcon = child == this.mIsolatedIcon;
                    icon.setVisibleState(2, true, new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NotificationIconContainer$sYOppFQ4vSNRi0SYdFbv716CxNY
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.removeTransientView(icon);
                        }
                    }, isIsolatedIcon ? 110L : 0L);
                }
            }
        }
    }

    private int findFirstViewIndexAfter(float translationX) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getTranslationX() > translationX) {
                return i;
            }
        }
        int i2 = getChildCount();
        return i2;
    }

    public void resetViewStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            ViewState iconState = this.mIconStates.get(view);
            iconState.initFrom(view);
            iconState.alpha = (this.mIsolatedIcon == null || view == this.mIsolatedIcon) ? 1.0f : 0.0f;
            iconState.hidden = false;
        }
    }

    public void calculateIconTranslations() {
        int maxVisibleIcons;
        IconState iconState;
        int i;
        float f;
        float translationX = getActualPaddingStart();
        int childCount = getChildCount();
        if (this.mDark) {
            maxVisibleIcons = 5;
        } else {
            maxVisibleIcons = this.mIsStaticLayout ? 4 : childCount;
        }
        float layoutEnd = getLayoutEnd();
        float overflowStart = getMaxOverflowStart();
        float f2 = 0.0f;
        this.mVisualOverflowStart = 0.0f;
        this.mFirstVisibleIconState = null;
        int i2 = -1;
        boolean hasAmbient = this.mSpeedBumpIndex != -1 && this.mSpeedBumpIndex < getChildCount();
        int firstOverflowIndex = -1;
        float translationX2 = translationX;
        int i3 = 0;
        while (i3 < childCount) {
            View view = getChildAt(i3);
            IconState iconState2 = this.mIconStates.get(view);
            iconState2.xTranslation = translationX2;
            if (this.mFirstVisibleIconState == null) {
                this.mFirstVisibleIconState = iconState2;
            }
            boolean forceOverflow = (this.mSpeedBumpIndex != i2 && i3 >= this.mSpeedBumpIndex && iconState2.iconAppearAmount > f2) || i3 >= maxVisibleIcons;
            boolean noOverflowAfter = i3 == childCount + (-1);
            float drawingScale = (this.mDark && (view instanceof StatusBarIconView)) ? ((StatusBarIconView) view).getIconScaleFullyDark() : 1.0f;
            if (this.mOpenedAmount != f2) {
                noOverflowAfter = (!noOverflowAfter || hasAmbient || forceOverflow) ? false : true;
            }
            iconState2.visibleState = 0;
            if (noOverflowAfter) {
                f = layoutEnd - this.mIconSize;
            } else {
                f = overflowStart - this.mIconSize;
            }
            boolean isOverflowing = translationX2 > f;
            if (firstOverflowIndex == -1 && (forceOverflow || isOverflowing)) {
                int firstOverflowIndex2 = (!noOverflowAfter || forceOverflow) ? i3 : i3 - 1;
                int firstOverflowIndex3 = this.mOverflowWidth;
                this.mVisualOverflowStart = layoutEnd - firstOverflowIndex3;
                if (forceOverflow || this.mIsStaticLayout) {
                    this.mVisualOverflowStart = Math.min(translationX2, this.mVisualOverflowStart);
                }
                firstOverflowIndex = firstOverflowIndex2;
            }
            float f3 = iconState2.iconAppearAmount;
            int maxVisibleIcons2 = maxVisibleIcons;
            int maxVisibleIcons3 = view.getWidth();
            translationX2 += f3 * maxVisibleIcons3 * drawingScale;
            i3++;
            maxVisibleIcons = maxVisibleIcons2;
            f2 = 0.0f;
            i2 = -1;
        }
        this.mNumDots = 0;
        if (firstOverflowIndex != -1) {
            float translationX3 = this.mVisualOverflowStart;
            translationX2 = translationX3;
            for (int i4 = firstOverflowIndex; i4 < childCount; i4++) {
                IconState iconState3 = this.mIconStates.get(getChildAt(i4));
                int dotWidth = this.mStaticDotDiameter + this.mDotPadding;
                iconState3.xTranslation = translationX2;
                if (this.mNumDots < 1) {
                    if (this.mNumDots == 0 && iconState3.iconAppearAmount < 0.8f) {
                        iconState3.visibleState = 0;
                        i = 1;
                    } else {
                        i = 1;
                        iconState3.visibleState = 1;
                        this.mNumDots++;
                    }
                    translationX2 += (this.mNumDots == i ? i * dotWidth : dotWidth) * iconState3.iconAppearAmount;
                    this.mLastVisibleIconState = iconState3;
                } else {
                    iconState3.visibleState = 2;
                }
            }
        } else if (childCount > 0) {
            View lastChild = getChildAt(childCount - 1);
            this.mLastVisibleIconState = this.mIconStates.get(lastChild);
            this.mFirstVisibleIconState = this.mIconStates.get(getChildAt(0));
        }
        boolean center = this.mDark;
        if (center && translationX2 < getLayoutEnd()) {
            float initialTranslation = this.mFirstVisibleIconState == null ? 0.0f : this.mFirstVisibleIconState.xTranslation;
            float contentWidth = getFinalTranslationX() - initialTranslation;
            float availableSpace = getLayoutEnd() - getActualPaddingStart();
            float delta = (availableSpace - contentWidth) / 2.0f;
            if (firstOverflowIndex != -1) {
                float deltaIgnoringOverflow = (getLayoutEnd() - this.mVisualOverflowStart) / 2.0f;
                delta = (deltaIgnoringOverflow + delta) / 2.0f;
            }
            for (int i5 = 0; i5 < childCount; i5++) {
                this.mIconStates.get(getChildAt(i5)).xTranslation += delta;
            }
        }
        if (isLayoutRtl()) {
            for (int i6 = 0; i6 < childCount; i6++) {
                View view2 = getChildAt(i6);
                IconState iconState4 = this.mIconStates.get(view2);
                iconState4.xTranslation = (getWidth() - iconState4.xTranslation) - view2.getWidth();
            }
        }
        if (this.mIsolatedIcon != null && (iconState = this.mIconStates.get(this.mIsolatedIcon)) != null) {
            iconState.xTranslation = (this.mIsolatedIconLocation.left - this.mAbsolutePosition[0]) - (((1.0f - this.mIsolatedIcon.getIconScale()) * this.mIsolatedIcon.getWidth()) / 2.0f);
            iconState.visibleState = 0;
        }
    }

    private float getLayoutEnd() {
        return getActualWidth() - getActualPaddingEnd();
    }

    private float getActualPaddingEnd() {
        if (this.mActualPaddingEnd == -2.14748365E9f) {
            return getPaddingEnd();
        }
        return this.mActualPaddingEnd;
    }

    private float getActualPaddingStart() {
        if (this.mActualPaddingStart == -2.14748365E9f) {
            return getPaddingStart();
        }
        return this.mActualPaddingStart;
    }

    public void setIsStaticLayout(boolean isStaticLayout) {
        this.mIsStaticLayout = isStaticLayout;
    }

    public void setActualLayoutWidth(int actualLayoutWidth) {
        this.mActualLayoutWidth = actualLayoutWidth;
    }

    public void setActualPaddingEnd(float paddingEnd) {
        this.mActualPaddingEnd = paddingEnd;
    }

    public void setActualPaddingStart(float paddingStart) {
        this.mActualPaddingStart = paddingStart;
    }

    public int getActualWidth() {
        if (this.mActualLayoutWidth == Integer.MIN_VALUE) {
            return getWidth();
        }
        return this.mActualLayoutWidth;
    }

    public int getFinalTranslationX() {
        if (this.mLastVisibleIconState == null) {
            return 0;
        }
        int translation = (int) (this.mLastVisibleIconState.xTranslation + this.mIconSize);
        return Math.min(getWidth(), translation);
    }

    private float getMaxOverflowStart() {
        return getLayoutEnd() - this.mOverflowWidth;
    }

    public void setChangingViewPositions(boolean changingViewPositions) {
        this.mChangingViewPositions = changingViewPositions;
    }

    public void setDark(boolean dark, boolean fade, long delay) {
        this.mDark = dark;
        this.mDisallowNextAnimation |= !fade;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof StatusBarIconView) {
                ((StatusBarIconView) view).setDark(dark, fade, delay);
            }
        }
    }

    public IconState getIconState(StatusBarIconView icon) {
        return this.mIconStates.get(icon);
    }

    public void setSpeedBumpIndex(int speedBumpIndex) {
        this.mSpeedBumpIndex = speedBumpIndex;
    }

    public void setOpenedAmount(float expandAmount) {
        this.mOpenedAmount = expandAmount;
    }

    public boolean hasOverflow() {
        return this.mNumDots > 0;
    }

    public boolean hasPartialOverflow() {
        return this.mNumDots > 0 && this.mNumDots < 1;
    }

    public int getPartialOverflowExtraPadding() {
        if (!hasPartialOverflow()) {
            return 0;
        }
        int partialOverflowAmount = (1 - this.mNumDots) * (this.mStaticDotDiameter + this.mDotPadding);
        int adjustedWidth = getFinalTranslationX() + partialOverflowAmount;
        if (adjustedWidth > getWidth()) {
            return getWidth() - getFinalTranslationX();
        }
        return partialOverflowAmount;
    }

    public int getNoOverflowExtraPadding() {
        if (this.mNumDots != 0) {
            return 0;
        }
        int collapsedPadding = this.mOverflowWidth;
        if (getFinalTranslationX() + collapsedPadding > getWidth()) {
            return getWidth() - getFinalTranslationX();
        }
        return collapsedPadding;
    }

    public void setAnimationsEnabled(boolean enabled) {
        if (!enabled && this.mAnimationsEnabled) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                ViewState childState = this.mIconStates.get(child);
                if (childState != null) {
                    childState.cancelAnimations(child);
                    childState.applyToView(child);
                }
            }
        }
        this.mAnimationsEnabled = enabled;
    }

    public void setReplacingIcons(ArrayMap<String, ArrayList<StatusBarIcon>> replacingIcons) {
        this.mReplacingIcons = replacingIcons;
    }

    public void showIconIsolated(StatusBarIconView icon, boolean animated) {
        if (animated) {
            this.mIsolatedIconForAnimation = icon != null ? icon : this.mIsolatedIcon;
        }
        this.mIsolatedIcon = icon;
        updateState();
    }

    public void setIsolatedIconLocation(Rect isolatedIconLocation, boolean requireUpdate) {
        this.mIsolatedIconLocation = isolatedIconLocation;
        if (requireUpdate) {
            updateState();
        }
    }

    public class IconState extends ViewState {
        public boolean isLastExpandIcon;
        private boolean justReplaced;
        public boolean needsCannedAnimation;
        public boolean noAnimations;
        public boolean translateContent;
        public boolean useFullTransitionAmount;
        public boolean useLinearTransitionAmount;
        public int visibleState;
        public float iconAppearAmount = 1.0f;
        public float clampedAppearAmount = 1.0f;
        public boolean justAdded = true;
        public int iconColor = 0;
        public int customTransformHeight = Integer.MIN_VALUE;

        public IconState() {
        }

        @Override // com.android.systemui.statusbar.stack.ViewState
        public void applyToView(View view) {
            if (view instanceof StatusBarIconView) {
                StatusBarIconView icon = (StatusBarIconView) view;
                boolean animate = false;
                AnimationProperties animationProperties = null;
                boolean animationsAllowed = (!NotificationIconContainer.this.mAnimationsEnabled || NotificationIconContainer.this.mDisallowNextAnimation || this.noAnimations) ? false : true;
                if (animationsAllowed) {
                    if (this.justAdded || this.justReplaced) {
                        super.applyToView(icon);
                        if (this.justAdded && this.iconAppearAmount != 0.0f) {
                            icon.setAlpha(0.0f);
                            icon.setVisibleState(2, false);
                            animationProperties = NotificationIconContainer.ADD_ICON_PROPERTIES;
                            animate = true;
                        }
                    } else if (this.visibleState != icon.getVisibleState()) {
                        animationProperties = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                        animate = true;
                    }
                    if (!animate && NotificationIconContainer.this.mAddAnimationStartIndex >= 0 && NotificationIconContainer.this.indexOfChild(view) >= NotificationIconContainer.this.mAddAnimationStartIndex && (icon.getVisibleState() != 2 || this.visibleState != 2)) {
                        animationProperties = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                        animate = true;
                    }
                    if (this.needsCannedAnimation) {
                        AnimationFilter animationFilter = NotificationIconContainer.sTempProperties.getAnimationFilter();
                        animationFilter.reset();
                        animationFilter.combineFilter(NotificationIconContainer.ICON_ANIMATION_PROPERTIES.getAnimationFilter());
                        NotificationIconContainer.sTempProperties.resetCustomInterpolators();
                        NotificationIconContainer.sTempProperties.combineCustomInterpolators(NotificationIconContainer.ICON_ANIMATION_PROPERTIES);
                        if (animationProperties != null) {
                            animationFilter.combineFilter(animationProperties.getAnimationFilter());
                            NotificationIconContainer.sTempProperties.combineCustomInterpolators(animationProperties);
                        }
                        animationProperties = NotificationIconContainer.sTempProperties;
                        animationProperties.setDuration(100L);
                        animate = true;
                        NotificationIconContainer.this.mCannedAnimationStartIndex = NotificationIconContainer.this.indexOfChild(view);
                    }
                    if (!animate && NotificationIconContainer.this.mCannedAnimationStartIndex >= 0 && NotificationIconContainer.this.indexOfChild(view) > NotificationIconContainer.this.mCannedAnimationStartIndex && (icon.getVisibleState() != 2 || this.visibleState != 2)) {
                        AnimationFilter animationFilter2 = NotificationIconContainer.sTempProperties.getAnimationFilter();
                        animationFilter2.reset();
                        animationFilter2.animateX();
                        NotificationIconContainer.sTempProperties.resetCustomInterpolators();
                        animationProperties = NotificationIconContainer.sTempProperties;
                        animationProperties.setDuration(100L);
                        animate = true;
                    }
                    if (NotificationIconContainer.this.mIsolatedIconForAnimation != null) {
                        if (view == NotificationIconContainer.this.mIsolatedIconForAnimation) {
                            animationProperties = NotificationIconContainer.UNISOLATION_PROPERTY;
                            animationProperties.setDelay(NotificationIconContainer.this.mIsolatedIcon == null ? 0L : 100L);
                        } else {
                            animationProperties = NotificationIconContainer.UNISOLATION_PROPERTY_OTHERS;
                            animationProperties.setDelay(NotificationIconContainer.this.mIsolatedIcon != null ? 0L : 100L);
                        }
                        animate = true;
                    }
                }
                icon.setVisibleState(this.visibleState, animationsAllowed);
                icon.setIconColor(this.iconColor, this.needsCannedAnimation && animationsAllowed);
                if (animate) {
                    animateTo(icon, animationProperties);
                } else {
                    super.applyToView(view);
                }
                boolean inShelf = this.iconAppearAmount == 1.0f;
                icon.setIsInShelf(inShelf);
            }
            this.justAdded = false;
            this.justReplaced = false;
            this.needsCannedAnimation = false;
        }

        public boolean hasCustomTransformHeight() {
            return this.isLastExpandIcon && this.customTransformHeight != Integer.MIN_VALUE;
        }

        @Override // com.android.systemui.statusbar.stack.ViewState
        public void initFrom(View view) {
            super.initFrom(view);
            if (view instanceof StatusBarIconView) {
                this.iconColor = ((StatusBarIconView) view).getStaticDrawableColor();
            }
        }
    }
}
