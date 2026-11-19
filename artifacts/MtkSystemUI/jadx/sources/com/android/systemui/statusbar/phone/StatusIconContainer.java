package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.stack.AnimationFilter;
import com.android.systemui.statusbar.stack.AnimationProperties;
import com.android.systemui.statusbar.stack.ViewState;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class StatusIconContainer extends AlphaOptimizedLinearLayout {
    private static final AnimationProperties ADD_ICON_PROPERTIES = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.1
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateAlpha();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(200).setDelay(50);
    private static final AnimationProperties DOT_ANIMATION_PROPERTIES = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.2
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateX();

        @Override // com.android.systemui.statusbar.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(200);
    private int mDotPadding;
    private int mIconDotFrameWidth;
    private ArrayList<StatusIconState> mLayoutStates;
    private ArrayList<View> mMeasureViews;
    private boolean mNeedsUnderflow;
    private boolean mShouldRestrictIcons;
    private int mStaticDotDiameter;
    private int mUnderflowStart;
    private int mUnderflowWidth;

    public StatusIconContainer(Context context) {
        this(context, null);
    }

    public StatusIconContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mUnderflowStart = 0;
        this.mShouldRestrictIcons = true;
        this.mLayoutStates = new ArrayList<>();
        this.mMeasureViews = new ArrayList<>();
        initDimens();
        setWillNotDraw(true);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setShouldRestrictIcons(boolean should) {
        this.mShouldRestrictIcons = should;
    }

    public boolean isRestrictingIcons() {
        return this.mShouldRestrictIcons;
    }

    private void initDimens() {
        this.mIconDotFrameWidth = getResources().getDimensionPixelSize(R.dimen.status_bar_icon_size);
        this.mDotPadding = getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        int radius = getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius);
        this.mStaticDotDiameter = 2 * radius;
        this.mUnderflowWidth = this.mIconDotFrameWidth + (0 * (this.mStaticDotDiameter + this.mDotPadding));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float midY = getHeight() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int top = (int) (midY - (height / 2.0f));
            child.layout(0, top, width, top + height);
        }
        resetViewStates();
        calculateIconTranslations();
        applyIconStates();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mMeasureViews.clear();
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) getChildAt(i);
            if (statusIconDisplayable.isIconVisible() && !statusIconDisplayable.isIconBlocked()) {
                this.mMeasureViews.add((View) statusIconDisplayable);
            }
        }
        int visibleCount = this.mMeasureViews.size();
        int maxVisible = visibleCount <= 7 ? 7 : 6;
        int totalWidth = this.mPaddingLeft + this.mPaddingRight;
        boolean trackWidth = true;
        int childWidthSpec = View.MeasureSpec.makeMeasureSpec(width, 0);
        this.mNeedsUnderflow = this.mShouldRestrictIcons && visibleCount > 7;
        for (int i2 = 0; i2 < this.mMeasureViews.size(); i2++) {
            View child = this.mMeasureViews.get((visibleCount - i2) - 1);
            measureChild(child, childWidthSpec, heightMeasureSpec);
            if (this.mShouldRestrictIcons) {
                if (i2 < maxVisible && trackWidth) {
                    totalWidth += getViewTotalMeasuredWidth(child);
                } else if (trackWidth) {
                    totalWidth += this.mUnderflowWidth;
                    trackWidth = false;
                }
            } else {
                totalWidth += getViewTotalMeasuredWidth(child);
            }
        }
        if (mode == 1073741824) {
            if (!this.mNeedsUnderflow && totalWidth > width) {
                this.mNeedsUnderflow = true;
            }
            setMeasuredDimension(width, View.MeasureSpec.getSize(heightMeasureSpec));
            return;
        }
        if (mode == Integer.MIN_VALUE && totalWidth > width) {
            this.mNeedsUnderflow = true;
            totalWidth = width;
        }
        int totalWidth2 = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(totalWidth, totalWidth2);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        StatusIconState vs = new StatusIconState();
        vs.justAdded = true;
        child.setTag(R.id.status_bar_view_state_tag, vs);
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        child.setTag(R.id.status_bar_view_state_tag, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void calculateIconTranslations() {
        this.mLayoutStates.clear();
        float width = getWidth();
        float translationX = width - getPaddingEnd();
        float contentStart = getPaddingStart();
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            StatusIconDisplayable iconView = (StatusIconDisplayable) childAt;
            StatusIconState childState = getViewStateFromChild(childAt);
            if (!iconView.isIconVisible() || iconView.isIconBlocked()) {
                childState.visibleState = 2;
            } else {
                childState.visibleState = 0;
                childState.xTranslation = translationX - getViewTotalWidth(childAt);
                this.mLayoutStates.add(0, childState);
                translationX -= getViewTotalWidth(childAt);
            }
        }
        int totalVisible = this.mLayoutStates.size();
        int maxVisible = totalVisible > 7 ? 6 : 7;
        this.mUnderflowStart = 0;
        int visible = 0;
        int firstUnderflowIndex = -1;
        for (int i2 = totalVisible - 1; i2 >= 0; i2--) {
            StatusIconState state = this.mLayoutStates.get(i2);
            if ((!this.mNeedsUnderflow || state.xTranslation >= this.mUnderflowWidth + contentStart) && (!this.mShouldRestrictIcons || visible < maxVisible)) {
                this.mUnderflowStart = (int) Math.max(contentStart, state.xTranslation - this.mUnderflowWidth);
                visible++;
            } else {
                firstUnderflowIndex = i2;
                break;
            }
        }
        if (firstUnderflowIndex != -1) {
            int dotWidth = this.mStaticDotDiameter + this.mDotPadding;
            int dotOffset = (this.mUnderflowStart + this.mUnderflowWidth) - this.mIconDotFrameWidth;
            int dotOffset2 = dotOffset;
            int totalDots = 0;
            for (int totalDots2 = firstUnderflowIndex; totalDots2 >= 0; totalDots2--) {
                StatusIconState state2 = this.mLayoutStates.get(totalDots2);
                if (totalDots < 1) {
                    state2.xTranslation = dotOffset2;
                    state2.visibleState = 1;
                    dotOffset2 -= dotWidth;
                    totalDots++;
                } else {
                    state2.visibleState = 2;
                }
            }
        }
        if (isLayoutRtl()) {
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 < childCount) {
                    View child = getChildAt(i4);
                    StatusIconState state3 = getViewStateFromChild(child);
                    state3.xTranslation = (width - state3.xTranslation) - child.getWidth();
                    i3 = i4 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private void applyIconStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            StatusIconState vs = getViewStateFromChild(child);
            if (vs != null) {
                vs.applyToView(child);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void resetViewStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            StatusIconState viewStateFromChild = getViewStateFromChild(childAt);
            if (viewStateFromChild != null) {
                viewStateFromChild.initFrom(childAt);
                viewStateFromChild.alpha = 1.0f;
                if (childAt instanceof StatusIconDisplayable) {
                    viewStateFromChild.hidden = !((StatusIconDisplayable) childAt).isIconVisible();
                } else {
                    viewStateFromChild.hidden = false;
                }
            }
        }
    }

    private static StatusIconState getViewStateFromChild(View child) {
        return (StatusIconState) child.getTag(R.id.status_bar_view_state_tag);
    }

    private static int getViewTotalMeasuredWidth(View child) {
        return child.getMeasuredWidth() + child.getPaddingStart() + child.getPaddingEnd();
    }

    private static int getViewTotalWidth(View child) {
        return child.getWidth() + child.getPaddingStart() + child.getPaddingEnd();
    }

    public static class StatusIconState extends ViewState {
        public int visibleState = 0;
        public boolean justAdded = true;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.statusbar.stack.ViewState
        public void applyToView(View view) {
            if (!(view instanceof StatusIconDisplayable)) {
                return;
            }
            StatusIconDisplayable icon = (StatusIconDisplayable) view;
            AnimationProperties animationProperties = null;
            boolean animate = false;
            if (this.justAdded) {
                super.applyToView(view);
                animationProperties = StatusIconContainer.ADD_ICON_PROPERTIES;
                animate = true;
            } else if (icon.getVisibleState() != this.visibleState) {
                animationProperties = StatusIconContainer.DOT_ANIMATION_PROPERTIES;
                animate = true;
            }
            if (animate) {
                animateTo(view, animationProperties);
                icon.setVisibleState(this.visibleState);
            } else {
                icon.setVisibleState(this.visibleState);
                super.applyToView(view);
            }
            this.justAdded = false;
        }
    }
}
