package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        int index = a.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        int index2 = a.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        boolean baselineAligned = a.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider == this.mDivider) {
            return;
        }
        this.mDivider = divider;
        if (divider != null) {
            this.mDividerWidth = divider.getIntrinsicWidth();
            this.mDividerHeight = divider.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int bottom;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int top = (child.getTop() - lp.topMargin) - this.mDividerHeight;
                drawHorizontalDivider(canvas, top);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                bottom = child2.getBottom() + lp2.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = child.getRight() + lp.rightMargin;
                } else {
                    int position3 = child.getLeft();
                    position2 = (position3 - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position2);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    int position4 = getWidth();
                    position = (position4 - getPaddingRight()) - this.mDividerWidth;
                }
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (isLayoutRtl) {
                    int position5 = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                    position = position5;
                } else {
                    int position6 = child2.getRight();
                    position = position6 + lp2.rightMargin;
                }
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    @Override // android.view.View
    public int getBaseline() {
        int majorGravity;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View child = getChildAt(this.mBaselineAlignedChildIndex);
        int childBaseline = child.getBaseline();
        if (childBaseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int childTop = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (majorGravity = this.mGravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay) != 48) {
            if (majorGravity == 16) {
                childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (majorGravity == 80) {
                childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return lp.topMargin + childTop + childBaseline;
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (childIndex == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i = childIndex - 1; i >= 0; i--) {
            if (getChildAt(i).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:159:0x03ce  */
    /* JADX WARN: Code duplicated, block: B:160:0x03d1  */
    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int count;
        int heightMode;
        int alternativeMaxWidth;
        int delta;
        int margin;
        int margin2;
        int i;
        float totalWeight;
        int weightedMaxWidth;
        int i2;
        int i3;
        boolean skippedMeasure;
        int childState;
        int maxWidth;
        int heightMode2;
        View child;
        int count2;
        int weightedMaxWidth2;
        int alternativeMaxWidth2;
        LayoutParams lp;
        int i4;
        int i5;
        int weightedMaxWidth3;
        this.mTotalLength = 0;
        int childState2 = 0;
        float totalWeight2 = 0.0f;
        int count3 = getVirtualChildCount();
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode3 = View.MeasureSpec.getMode(heightMeasureSpec);
        boolean skippedMeasure2 = false;
        int baselineChildIndex = this.mBaselineAlignedChildIndex;
        boolean useLargestChild = this.mUseLargestChild;
        boolean matchWidth = false;
        int weightedMaxWidth4 = 0;
        int alternativeMaxWidth3 = 0;
        int maxWidth2 = 0;
        int margin3 = 0;
        int weightedMaxWidth5 = 0;
        int largestChildHeight = 1;
        while (true) {
            int weightedMaxWidth6 = margin3;
            if (maxWidth2 >= count3) {
                int alternativeMaxWidth4 = weightedMaxWidth4;
                int count4 = count3;
                int heightMode4 = heightMode3;
                boolean skippedMeasure3 = skippedMeasure2;
                int weightedMaxWidth7 = weightedMaxWidth6;
                int childState3 = childState2;
                int maxWidth3 = alternativeMaxWidth3;
                int largestChildHeight2 = weightedMaxWidth5;
                int childState4 = this.mTotalLength;
                if (childState4 > 0) {
                    count = count4;
                    if (hasDividerBeforeChildAt(count)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                } else {
                    count = count4;
                }
                if (useLargestChild) {
                    heightMode = heightMode4;
                    if (heightMode == Integer.MIN_VALUE || heightMode == 0) {
                        this.mTotalLength = 0;
                        int i6 = 0;
                        while (i6 < count) {
                            View child2 = getVirtualChildAt(i6);
                            if (child2 == null) {
                                this.mTotalLength += measureNullChild(i6);
                            } else {
                                if (child2.getVisibility() == 8) {
                                    i6 += getChildrenSkipCount(child2, i6);
                                } else {
                                    LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                                    int totalLength = this.mTotalLength;
                                    i2 = i6;
                                    int i7 = lp2.topMargin;
                                    this.mTotalLength = Math.max(totalLength, totalLength + largestChildHeight2 + i7 + lp2.bottomMargin + getNextLocationOffset(child2));
                                }
                                i6 = i2 + 1;
                            }
                            i2 = i6;
                            i6 = i2 + 1;
                        }
                    }
                } else {
                    heightMode = heightMode4;
                }
                this.mTotalLength += getPaddingTop() + getPaddingBottom();
                int heightSize = this.mTotalLength;
                int heightSizeAndState = View.resolveSizeAndState(Math.max(heightSize, getSuggestedMinimumHeight()), heightMeasureSpec, 0);
                int heightSize2 = heightSizeAndState & 16777215;
                int delta2 = heightSize2 - this.mTotalLength;
                if (skippedMeasure3 || (delta2 != 0 && totalWeight2 > 0.0f)) {
                    float weightSum = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight2;
                    this.mTotalLength = 0;
                    int i8 = 0;
                    int delta3 = delta2;
                    while (i8 < count) {
                        View child3 = getVirtualChildAt(i8);
                        boolean useLargestChild2 = useLargestChild;
                        int largestChildHeight3 = largestChildHeight2;
                        if (child3.getVisibility() == 8) {
                            heightMode = heightMode;
                            baselineChildIndex = baselineChildIndex;
                        } else {
                            LayoutParams lp3 = (LayoutParams) child3.getLayoutParams();
                            float childExtra = lp3.weight;
                            if (childExtra > 0.0f) {
                                int share = (int) ((delta3 * childExtra) / weightSum);
                                float weightSum2 = weightSum - childExtra;
                                delta = delta3 - share;
                                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp3.leftMargin + lp3.rightMargin, lp3.width);
                                if (lp3.height == 0 && heightMode == 1073741824) {
                                    heightMode = heightMode;
                                    child3.measure(childWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(share > 0 ? share : 0, 1073741824));
                                } else {
                                    int heightMode5 = child3.getMeasuredHeight();
                                    int childHeight = heightMode5 + share;
                                    if (childHeight < 0) {
                                        childHeight = 0;
                                    }
                                    child3.measure(childWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(childHeight, 1073741824));
                                }
                                childState3 = View.combineMeasuredStates(childState3, child3.getMeasuredState() & (-256));
                                weightSum = weightSum2;
                            } else {
                                heightMode = heightMode;
                                delta = delta3;
                            }
                            int heightMode6 = lp3.leftMargin;
                            int margin4 = heightMode6 + lp3.rightMargin;
                            int measuredWidth = child3.getMeasuredWidth() + margin4;
                            maxWidth3 = Math.max(maxWidth3, measuredWidth);
                            if (widthMode != 1073741824) {
                                margin = margin4;
                                margin2 = lp3.width == -1 ? 1 : 0;
                                if (margin2 != 0) {
                                    i = margin;
                                } else {
                                    i = measuredWidth;
                                }
                                alternativeMaxWidth4 = Math.max(alternativeMaxWidth4, i);
                                int i9 = (largestChildHeight == 0 && lp3.width == -1) ? 1 : 0;
                                int totalLength2 = this.mTotalLength;
                                this.mTotalLength = Math.max(totalLength2, totalLength2 + child3.getMeasuredHeight() + lp3.topMargin + lp3.bottomMargin + getNextLocationOffset(child3));
                                largestChildHeight = i9;
                                delta3 = delta;
                                weightSum = weightSum;
                            } else {
                                margin = margin4;
                            }
                            if (margin2 != 0) {
                                i = margin;
                            } else {
                                i = measuredWidth;
                            }
                            alternativeMaxWidth4 = Math.max(alternativeMaxWidth4, i);
                            if (largestChildHeight == 0) {
                            }
                            int totalLength3 = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength3, totalLength3 + child3.getMeasuredHeight() + lp3.topMargin + lp3.bottomMargin + getNextLocationOffset(child3));
                            largestChildHeight = i9;
                            delta3 = delta;
                            weightSum = weightSum;
                        }
                        i8++;
                        useLargestChild = useLargestChild2;
                        largestChildHeight2 = largestChildHeight3;
                        baselineChildIndex = baselineChildIndex;
                        heightMode = heightMode;
                    }
                    this.mTotalLength += getPaddingTop() + getPaddingBottom();
                    alternativeMaxWidth = alternativeMaxWidth4;
                } else {
                    int alternativeMaxWidth5 = Math.max(alternativeMaxWidth4, weightedMaxWidth7);
                    if (useLargestChild) {
                        if (heightMode != 1073741824) {
                            int i10 = 0;
                            while (true) {
                                int i11 = i10;
                                if (i11 >= count) {
                                    break;
                                }
                                int delta4 = delta2;
                                View child4 = getVirtualChildAt(i11);
                                if (child4 != null) {
                                    totalWeight = totalWeight2;
                                    weightedMaxWidth = weightedMaxWidth7;
                                    if (child4.getVisibility() != 8 && ((LayoutParams) child4.getLayoutParams()).weight > 0.0f) {
                                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(child4.getMeasuredWidth(), 1073741824);
                                        int alternativeMaxWidth6 = View.MeasureSpec.makeMeasureSpec(largestChildHeight2, 1073741824);
                                        child4.measure(iMakeMeasureSpec, alternativeMaxWidth6);
                                    }
                                } else {
                                    totalWeight = totalWeight2;
                                    weightedMaxWidth = weightedMaxWidth7;
                                }
                                i10 = i11 + 1;
                                delta2 = delta4;
                                totalWeight2 = totalWeight;
                                weightedMaxWidth7 = weightedMaxWidth;
                                alternativeMaxWidth5 = alternativeMaxWidth5;
                            }
                        }
                        alternativeMaxWidth = alternativeMaxWidth5;
                    } else {
                        alternativeMaxWidth = alternativeMaxWidth5;
                    }
                }
                if (largestChildHeight == 0 && widthMode != 1073741824) {
                    maxWidth3 = alternativeMaxWidth;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(maxWidth3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), widthMeasureSpec, childState3), heightSizeAndState);
                if (matchWidth) {
                    forceUniformWidth(count, heightMeasureSpec);
                    return;
                }
                return;
            }
            View child5 = getVirtualChildAt(maxWidth2);
            if (child5 == null) {
                int childState5 = childState2;
                int childState6 = this.mTotalLength;
                this.mTotalLength = childState6 + measureNullChild(maxWidth2);
                count2 = count3;
                heightMode2 = heightMode3;
                margin3 = weightedMaxWidth6;
                childState2 = childState5;
            } else {
                int childState7 = childState2;
                int childState8 = child5.getVisibility();
                int maxWidth4 = alternativeMaxWidth3;
                if (childState8 == 8) {
                    maxWidth2 += getChildrenSkipCount(child5, maxWidth2);
                    count2 = count3;
                    heightMode2 = heightMode3;
                    margin3 = weightedMaxWidth6;
                    childState2 = childState7;
                    alternativeMaxWidth3 = maxWidth4;
                } else {
                    if (hasDividerBeforeChildAt(maxWidth2)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams lp4 = (LayoutParams) child5.getLayoutParams();
                    float totalWeight3 = totalWeight2 + lp4.weight;
                    if (heightMode3 == 1073741824 && lp4.height == 0 && lp4.weight > 0.0f) {
                        int totalLength4 = this.mTotalLength;
                        int i12 = lp4.topMargin + totalLength4;
                        int i13 = maxWidth2;
                        int i14 = lp4.bottomMargin;
                        this.mTotalLength = Math.max(totalLength4, i12 + i14);
                        lp = lp4;
                        child = child5;
                        alternativeMaxWidth2 = weightedMaxWidth4;
                        count2 = count3;
                        heightMode2 = heightMode3;
                        skippedMeasure = true;
                        weightedMaxWidth2 = weightedMaxWidth6;
                        childState = childState7;
                        maxWidth = maxWidth4;
                        i3 = i13;
                    } else {
                        int i15 = maxWidth2;
                        int oldHeight = Integer.MIN_VALUE;
                        if (lp4.height == 0 && lp4.weight > 0.0f) {
                            oldHeight = 0;
                            lp4.height = -2;
                        }
                        int oldHeight2 = oldHeight;
                        i3 = i15;
                        skippedMeasure = skippedMeasure2;
                        childState = childState7;
                        maxWidth = maxWidth4;
                        heightMode2 = heightMode3;
                        int heightMode7 = weightedMaxWidth5;
                        child = child5;
                        count2 = count3;
                        weightedMaxWidth2 = weightedMaxWidth6;
                        alternativeMaxWidth2 = weightedMaxWidth4;
                        int alternativeMaxWidth7 = totalWeight3 == 0.0f ? this.mTotalLength : 0;
                        measureChildBeforeLayout(child5, i3, widthMeasureSpec, 0, heightMeasureSpec, alternativeMaxWidth7);
                        if (oldHeight2 != Integer.MIN_VALUE) {
                            lp = lp4;
                            lp.height = oldHeight2;
                        } else {
                            lp = lp4;
                        }
                        int childHeight2 = child.getMeasuredHeight();
                        int totalLength5 = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength5, totalLength5 + childHeight2 + lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
                        weightedMaxWidth5 = useLargestChild ? Math.max(childHeight2, heightMode7) : heightMode7;
                    }
                    if (baselineChildIndex >= 0) {
                        i4 = i3;
                        if (baselineChildIndex == i4 + 1) {
                            this.mBaselineChildTop = this.mTotalLength;
                        }
                    } else {
                        i4 = i3;
                    }
                    if (i4 < baselineChildIndex && lp.weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    boolean matchWidthLocally = false;
                    if (widthMode != 1073741824) {
                        i5 = -1;
                        if (lp.width == -1) {
                            matchWidth = true;
                            matchWidthLocally = true;
                        }
                    } else {
                        i5 = -1;
                    }
                    int margin5 = lp.leftMargin + lp.rightMargin;
                    int measuredWidth2 = child.getMeasuredWidth() + margin5;
                    int maxWidth5 = Math.max(maxWidth, measuredWidth2);
                    int childState9 = View.combineMeasuredStates(childState, child.getMeasuredState());
                    int i16 = (largestChildHeight == 0 || lp.width != i5) ? 0 : 1;
                    if (lp.weight > 0.0f) {
                        weightedMaxWidth3 = Math.max(weightedMaxWidth2, matchWidthLocally ? margin5 : measuredWidth2);
                    } else {
                        weightedMaxWidth3 = weightedMaxWidth2;
                        alternativeMaxWidth2 = Math.max(alternativeMaxWidth2, matchWidthLocally ? margin5 : measuredWidth2);
                    }
                    maxWidth2 = i4 + getChildrenSkipCount(child, i4);
                    margin3 = weightedMaxWidth3;
                    weightedMaxWidth4 = alternativeMaxWidth2;
                    alternativeMaxWidth3 = maxWidth5;
                    childState2 = childState9;
                    totalWeight2 = totalWeight3;
                    skippedMeasure2 = skippedMeasure;
                    largestChildHeight = i16;
                }
            }
            maxWidth2++;
            heightMode3 = heightMode2;
            count3 = count2;
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight;
        int count;
        int maxHeight2;
        int alternativeMaxHeight;
        int weightedMaxHeight;
        boolean useLargestChild;
        float weightSum;
        int delta;
        boolean allFillParent;
        int alternativeMaxHeight2;
        int maxHeight3;
        int i;
        int i2;
        int weightedMaxHeight2;
        int alternativeMaxHeight3;
        int maxHeight4;
        boolean baselineAligned;
        int i3;
        LayoutParams lp;
        int i4;
        int weightedMaxHeight3;
        int weightedMaxHeight4;
        int childBaseline;
        this.mTotalLength = 0;
        float totalWeight = 0.0f;
        int count2 = getVirtualChildCount();
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        boolean baselineAligned2 = this.mBaselineAligned;
        boolean skippedMeasure = false;
        boolean useLargestChild2 = this.mUseLargestChild;
        boolean isExactly = widthMode == 1073741824;
        int childState = 0;
        int largestChildWidth = 0;
        int largestChildWidth2 = 0;
        boolean matchHeight = true;
        int childState2 = 0;
        int maxHeight5 = 0;
        int weightedMaxHeight5 = 0;
        int alternativeMaxHeight4 = 0;
        while (maxHeight5 < count2) {
            View child = getVirtualChildAt(maxHeight5);
            if (child == null) {
                int largestChildWidth3 = largestChildWidth;
                int largestChildWidth4 = this.mTotalLength;
                this.mTotalLength = largestChildWidth4 + measureNullChild(maxHeight5);
                baselineAligned = baselineAligned2;
                largestChildWidth = largestChildWidth3;
            } else {
                int largestChildWidth5 = largestChildWidth;
                int largestChildWidth6 = child.getVisibility();
                int weightedMaxHeight6 = alternativeMaxHeight4;
                if (largestChildWidth6 == 8) {
                    maxHeight5 += getChildrenSkipCount(child, maxHeight5);
                    baselineAligned = baselineAligned2;
                    largestChildWidth = largestChildWidth5;
                    alternativeMaxHeight4 = weightedMaxHeight6;
                } else {
                    if (hasDividerBeforeChildAt(maxHeight5)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                    float totalWeight2 = totalWeight + lp2.weight;
                    if (widthMode == 1073741824 && lp2.width == 0 && lp2.weight > 0.0f) {
                        if (isExactly) {
                            int i5 = this.mTotalLength;
                            int i6 = lp2.leftMargin;
                            int i7 = lp2.rightMargin;
                            this.mTotalLength = i5 + i6 + i7;
                        } else {
                            int i8 = this.mTotalLength;
                            this.mTotalLength = Math.max(i8, lp2.leftMargin + i8 + lp2.rightMargin);
                        }
                        if (baselineAligned2) {
                            int freeSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            child.measure(freeSpec, freeSpec);
                            lp = lp2;
                            alternativeMaxHeight3 = weightedMaxHeight5;
                            maxHeight4 = childState2;
                            baselineAligned = baselineAligned2;
                            i4 = largestChildWidth5;
                            weightedMaxHeight2 = weightedMaxHeight6;
                            i2 = maxHeight5;
                            i3 = -1;
                            largestChildWidth5 = i4;
                        } else {
                            skippedMeasure = true;
                            lp = lp2;
                            alternativeMaxHeight3 = weightedMaxHeight5;
                            maxHeight4 = childState2;
                            baselineAligned = baselineAligned2;
                            weightedMaxHeight2 = weightedMaxHeight6;
                            i2 = maxHeight5;
                            i3 = -1;
                        }
                    } else {
                        int i9 = maxHeight5;
                        int oldWidth = Integer.MIN_VALUE;
                        if (lp2.width == 0 && lp2.weight > 0.0f) {
                            oldWidth = 0;
                            lp2.width = -2;
                        }
                        int oldWidth2 = oldWidth;
                        i2 = i9;
                        weightedMaxHeight2 = weightedMaxHeight6;
                        alternativeMaxHeight3 = weightedMaxHeight5;
                        maxHeight4 = childState2;
                        int maxHeight6 = totalWeight2 == 0.0f ? this.mTotalLength : 0;
                        baselineAligned = baselineAligned2;
                        i3 = -1;
                        measureChildBeforeLayout(child, i2, widthMeasureSpec, maxHeight6, heightMeasureSpec, 0);
                        if (oldWidth2 != Integer.MIN_VALUE) {
                            lp = lp2;
                            lp.width = oldWidth2;
                        } else {
                            lp = lp2;
                        }
                        int childWidth = child.getMeasuredWidth();
                        if (isExactly) {
                            this.mTotalLength += lp.leftMargin + childWidth + lp.rightMargin + getNextLocationOffset(child);
                        } else {
                            int totalLength = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                        }
                        if (useLargestChild2) {
                            largestChildWidth5 = Math.max(childWidth, largestChildWidth5);
                        } else {
                            i4 = largestChildWidth5;
                            largestChildWidth5 = i4;
                        }
                    }
                    boolean matchHeightLocally = false;
                    if (heightMode != 1073741824 && lp.height == i3) {
                        largestChildWidth2 = 1;
                        matchHeightLocally = true;
                    }
                    int margin = lp.topMargin + lp.bottomMargin;
                    int childHeight = child.getMeasuredHeight() + margin;
                    int childState3 = View.combineMeasuredStates(childState, child.getMeasuredState());
                    if (baselineAligned && (childBaseline = child.getBaseline()) != i3) {
                        int gravity = (lp.gravity < 0 ? this.mGravity : lp.gravity) & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
                        int index = ((gravity >> 4) & (-2)) >> 1;
                        maxAscent[index] = Math.max(maxAscent[index], childBaseline);
                        maxDescent[index] = Math.max(maxDescent[index], childHeight - childBaseline);
                    }
                    int maxHeight7 = Math.max(maxHeight4, childHeight);
                    boolean allFillParent2 = matchHeight && lp.height == -1;
                    if (lp.weight > 0.0f) {
                        weightedMaxHeight3 = Math.max(weightedMaxHeight2, matchHeightLocally ? margin : childHeight);
                        weightedMaxHeight4 = alternativeMaxHeight3;
                    } else {
                        weightedMaxHeight3 = weightedMaxHeight2;
                        weightedMaxHeight4 = Math.max(alternativeMaxHeight3, matchHeightLocally ? margin : childHeight);
                    }
                    int i10 = i2;
                    maxHeight5 = i10 + getChildrenSkipCount(child, i10);
                    childState = childState3;
                    matchHeight = allFillParent2;
                    weightedMaxHeight5 = weightedMaxHeight4;
                    totalWeight = totalWeight2;
                    largestChildWidth = largestChildWidth5;
                    childState2 = maxHeight7;
                    alternativeMaxHeight4 = weightedMaxHeight3;
                }
            }
            maxHeight5++;
            baselineAligned2 = baselineAligned;
        }
        int weightedMaxHeight7 = alternativeMaxHeight4;
        int maxHeight8 = childState2;
        boolean baselineAligned3 = baselineAligned2;
        int childState4 = childState;
        int largestChildWidth7 = largestChildWidth;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count2)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1) {
            maxHeight = maxHeight8;
        } else {
            int ascent = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
            int i11 = maxDescent[3];
            int i12 = maxDescent[0];
            int i13 = maxDescent[1];
            int childState5 = maxDescent[2];
            int descent = Math.max(i11, Math.max(i12, Math.max(i13, childState5)));
            maxHeight = Math.max(maxHeight8, ascent + descent);
        }
        if (useLargestChild2 && (widthMode == Integer.MIN_VALUE || widthMode == 0)) {
            this.mTotalLength = 0;
            int i14 = 0;
            while (i14 < count2) {
                View child2 = getVirtualChildAt(i14);
                if (child2 == null) {
                    this.mTotalLength += measureNullChild(i14);
                } else {
                    if (child2.getVisibility() == 8) {
                        i = i14 + getChildrenSkipCount(child2, i14);
                    } else {
                        LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                        if (isExactly) {
                            int i15 = this.mTotalLength;
                            int i16 = lp3.leftMargin + largestChildWidth7;
                            int i17 = lp3.rightMargin;
                            this.mTotalLength = i15 + i16 + i17 + getNextLocationOffset(child2);
                        } else {
                            int i18 = this.mTotalLength;
                            this.mTotalLength = Math.max(i18, i18 + largestChildWidth7 + lp3.leftMargin + lp3.rightMargin + getNextLocationOffset(child2));
                        }
                    }
                    i14 = i + 1;
                }
                i = i14;
                i14 = i + 1;
            }
        }
        int i19 = this.mTotalLength;
        this.mTotalLength = i19 + getPaddingLeft() + getPaddingRight();
        int widthSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, 0);
        int widthSize = widthSizeAndState & 16777215;
        int delta2 = widthSize - this.mTotalLength;
        if (skippedMeasure || (delta2 != 0 && totalWeight > 0.0f)) {
            float weightSum2 = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
            maxAscent[3] = -1;
            maxAscent[2] = -1;
            maxAscent[1] = -1;
            maxAscent[0] = -1;
            maxDescent[3] = -1;
            maxDescent[2] = -1;
            maxDescent[1] = -1;
            maxDescent[0] = -1;
            maxHeight2 = -1;
            this.mTotalLength = 0;
            alternativeMaxHeight = weightedMaxHeight5;
            int childState6 = childState4;
            float weightSum3 = weightSum2;
            int i20 = 0;
            while (i20 < count2) {
                float totalWeight3 = totalWeight;
                View child3 = getVirtualChildAt(i20);
                if (child3 != null) {
                    weightedMaxHeight = weightedMaxHeight7;
                    int weightedMaxHeight8 = child3.getVisibility();
                    useLargestChild = useLargestChild2;
                    if (weightedMaxHeight8 == 8) {
                        count2 = count2;
                    } else {
                        LayoutParams lp4 = (LayoutParams) child3.getLayoutParams();
                        float childExtra = lp4.weight;
                        if (childExtra > 0.0f) {
                            int share = (int) ((delta2 * childExtra) / weightSum3);
                            weightSum = weightSum3 - childExtra;
                            delta = delta2 - share;
                            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp4.topMargin + lp4.bottomMargin, lp4.height);
                            if (lp4.width == 0 && widthMode == 1073741824) {
                                child3.measure(View.MeasureSpec.makeMeasureSpec(share > 0 ? share : 0, 1073741824), childHeightMeasureSpec);
                            } else {
                                int childWidth2 = child3.getMeasuredWidth() + share;
                                if (childWidth2 < 0) {
                                    childWidth2 = 0;
                                }
                                child3.measure(View.MeasureSpec.makeMeasureSpec(childWidth2, 1073741824), childHeightMeasureSpec);
                            }
                            childState6 = View.combineMeasuredStates(childState6, child3.getMeasuredState() & (-16777216));
                        } else {
                            weightSum = weightSum3;
                            delta = delta2;
                        }
                        if (isExactly) {
                            this.mTotalLength += child3.getMeasuredWidth() + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child3);
                        } else {
                            int totalLength2 = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength2, child3.getMeasuredWidth() + totalLength2 + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child3));
                        }
                        boolean matchHeightLocally2 = heightMode != 1073741824 && lp4.height == -1;
                        int margin2 = lp4.topMargin + lp4.bottomMargin;
                        int childHeight2 = child3.getMeasuredHeight() + margin2;
                        int maxHeight9 = Math.max(maxHeight2, childHeight2);
                        int alternativeMaxHeight5 = Math.max(alternativeMaxHeight, matchHeightLocally2 ? margin2 : childHeight2);
                        boolean matchHeightLocally3 = matchHeight && lp4.height == -1;
                        if (baselineAligned3) {
                            int childBaseline2 = child3.getBaseline();
                            allFillParent = matchHeightLocally3;
                            if (childBaseline2 != -1) {
                                int gravity2 = (lp4.gravity < 0 ? this.mGravity : lp4.gravity) & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
                                int index2 = ((gravity2 >> 4) & (-2)) >> 1;
                                int gravity3 = maxAscent[index2];
                                maxAscent[index2] = Math.max(gravity3, childBaseline2);
                                int alternativeMaxHeight6 = childHeight2 - childBaseline2;
                                maxDescent[index2] = Math.max(maxDescent[index2], alternativeMaxHeight6);
                            }
                        } else {
                            allFillParent = matchHeightLocally3;
                        }
                        maxHeight2 = maxHeight9;
                        weightSum3 = weightSum;
                        delta2 = delta;
                        matchHeight = allFillParent;
                        alternativeMaxHeight = alternativeMaxHeight5;
                    }
                } else {
                    weightedMaxHeight = weightedMaxHeight7;
                    count2 = count2;
                    useLargestChild = useLargestChild2;
                }
                i20++;
                totalWeight = totalWeight3;
                weightedMaxHeight7 = weightedMaxHeight;
                useLargestChild2 = useLargestChild;
                count2 = count2;
            }
            count = count2;
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            if (maxAscent[1] != -1 || maxAscent[0] != -1 || maxAscent[2] != -1 || maxAscent[3] != -1) {
                int ascent2 = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
                int descent2 = Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2])));
                maxHeight2 = Math.max(maxHeight2, ascent2 + descent2);
            }
            childState4 = childState6;
        } else {
            int alternativeMaxHeight7 = Math.max(weightedMaxHeight5, weightedMaxHeight7);
            if (useLargestChild2 && widthMode != 1073741824) {
                int i21 = 0;
                while (true) {
                    int i22 = i21;
                    if (i22 >= count2) {
                        break;
                    }
                    int widthSize2 = widthSize;
                    View child4 = getVirtualChildAt(i22);
                    if (child4 != null) {
                        alternativeMaxHeight2 = alternativeMaxHeight7;
                        int alternativeMaxHeight8 = child4.getVisibility();
                        maxHeight3 = maxHeight;
                        if (alternativeMaxHeight8 != 8 && ((LayoutParams) child4.getLayoutParams()).weight > 0.0f) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(largestChildWidth7, 1073741824);
                            int largestChildWidth8 = child4.getMeasuredHeight();
                            child4.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(largestChildWidth8, 1073741824));
                        }
                    } else {
                        alternativeMaxHeight2 = alternativeMaxHeight7;
                        maxHeight3 = maxHeight;
                    }
                    i21 = i22 + 1;
                    widthSize = widthSize2;
                    alternativeMaxHeight7 = alternativeMaxHeight2;
                    maxHeight = maxHeight3;
                    largestChildWidth7 = largestChildWidth7;
                }
            }
            count = count2;
            alternativeMaxHeight = alternativeMaxHeight7;
            maxHeight2 = maxHeight;
        }
        if (!matchHeight && heightMode != 1073741824) {
            maxHeight2 = alternativeMaxHeight;
        }
        setMeasuredDimension((childState4 & (-16777216)) | widthSizeAndState, View.resolveSizeAndState(Math.max(maxHeight2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), heightMeasureSpec, childState4 << 16));
        if (largestChildWidth2 != 0) {
            forceUniformHeight(count, widthMeasureSpec);
        }
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    int measureNullChild(int childIndex) {
        return 0;
    }

    void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    int getLocationOffset(View child) {
        return 0;
    }

    int getNextLocationOffset(View child) {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int childLeft;
        int paddingLeft = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft) - getPaddingRight();
        int count = getVirtualChildCount();
        int majorGravity = this.mGravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
        int minorGravity = this.mGravity & 8388615;
        if (majorGravity == 16) {
            int childTop2 = getPaddingTop();
            childTop = childTop2 + (((bottom - top) - this.mTotalLength) / 2);
        } else if (majorGravity == 80) {
            childTop = ((getPaddingTop() + bottom) - top) - this.mTotalLength;
        } else {
            childTop = getPaddingTop();
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= count) {
                return;
            }
            View child = getVirtualChildAt(i2);
            if (child == null) {
                childTop += measureNullChild(i2);
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                int layoutDirection = ViewCompat.getLayoutDirection(this);
                int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, layoutDirection);
                int gravity2 = absoluteGravity & 7;
                if (gravity2 == 1) {
                    int childLeft2 = childSpace - childWidth;
                    childLeft = (((childLeft2 / 2) + paddingLeft) + lp.leftMargin) - lp.rightMargin;
                } else if (gravity2 == 5) {
                    childLeft = (childRight - childWidth) - lp.rightMargin;
                } else {
                    childLeft = lp.leftMargin + paddingLeft;
                }
                if (hasDividerBeforeChildAt(i2)) {
                    childTop += this.mDividerHeight;
                }
                int childTop3 = childTop + lp.topMargin;
                int childTop4 = getLocationOffset(child);
                int i3 = childTop3 + childTop4;
                setChildFrame(child, childLeft, i3, childWidth, childHeight);
                int childTop5 = childTop3 + childHeight + lp.bottomMargin + getNextLocationOffset(child);
                i2 += getChildrenSkipCount(child, i2);
                childTop = childTop5;
            }
            i = i2 + 1;
            majorGravity = majorGravity;
            paddingLeft = paddingLeft;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:33:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:35:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:37:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:38:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:40:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:41:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:43:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:44:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:47:0x010e  */
    void layoutHorizontal(int left, int top, int right, int bottom) {
        int childLeft;
        int majorGravity;
        boolean baselineAligned;
        int childBaseline;
        int gravity;
        int i;
        int childTop;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int height = bottom - top;
        int childBottom = height - getPaddingBottom();
        int childSpace = (height - paddingTop) - getPaddingBottom();
        int count = getVirtualChildCount();
        int majorGravity2 = this.mGravity & 8388615;
        int minorGravity = this.mGravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
        boolean baselineAligned2 = this.mBaselineAligned;
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(majorGravity2, layoutDirection);
        if (absoluteGravity == 1) {
            int childLeft2 = getPaddingLeft();
            int layoutDirection2 = this.mTotalLength;
            childLeft = childLeft2 + (((right - left) - layoutDirection2) / 2);
        } else if (absoluteGravity == 5) {
            int childLeft3 = getPaddingLeft();
            childLeft = ((childLeft3 + right) - left) - this.mTotalLength;
        } else {
            childLeft = getPaddingLeft();
        }
        int childLeft4 = childLeft;
        int start = 0;
        int dir = 1;
        if (isLayoutRtl) {
            start = count - 1;
            dir = -1;
        }
        int i2 = 0;
        int childLeft5 = childLeft4;
        while (true) {
            int i3 = i2;
            if (i3 >= count) {
                return;
            }
            int childIndex = start + (dir * i3);
            View child = getVirtualChildAt(childIndex);
            if (child == null) {
                childLeft5 += measureNullChild(childIndex);
                baselineAligned = baselineAligned2;
                majorGravity = majorGravity2;
            } else {
                int i4 = child.getVisibility();
                majorGravity = majorGravity2;
                if (i4 == 8) {
                    baselineAligned = baselineAligned2;
                    i3 = i3;
                } else {
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (!baselineAligned2) {
                        baselineAligned = baselineAligned2;
                    } else {
                        baselineAligned = baselineAligned2;
                        if (lp.height != -1) {
                            childBaseline = child.getBaseline();
                        }
                        gravity = lp.gravity;
                        if (gravity < 0) {
                            gravity = minorGravity;
                        }
                        i = gravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
                        if (i != 16) {
                            int childTop2 = childSpace - childHeight;
                            childTop = (((childTop2 / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                        } else if (i != 48) {
                            int childTop3 = lp.topMargin;
                            childTop = childTop3 + paddingTop;
                            if (childBaseline != -1) {
                                childTop += maxAscent[1] - childBaseline;
                            }
                        } else if (i != 80) {
                            childTop = (childBottom - childHeight) - lp.bottomMargin;
                            if (childBaseline != -1) {
                                int descent = child.getMeasuredHeight() - childBaseline;
                                childTop -= maxDescent[2] - descent;
                            }
                        } else {
                            childTop = paddingTop;
                        }
                        if (hasDividerBeforeChildAt(childIndex)) {
                            childLeft5 += this.mDividerWidth;
                        }
                        int childLeft6 = childLeft5 + lp.leftMargin;
                        setChildFrame(child, childLeft6 + getLocationOffset(child), childTop, childWidth, childHeight);
                        childLeft5 = childLeft6 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                        i3 += getChildrenSkipCount(child, childIndex);
                    }
                    childBaseline = -1;
                    gravity = lp.gravity;
                    if (gravity < 0) {
                        gravity = minorGravity;
                    }
                    i = gravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
                    if (i != 16) {
                        int childTop4 = childSpace - childHeight;
                        childTop = (((childTop4 / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                    } else if (i != 48) {
                        int childTop5 = lp.topMargin;
                        childTop = childTop5 + paddingTop;
                        if (childBaseline != -1) {
                            childTop += maxAscent[1] - childBaseline;
                        }
                    } else if (i != 80) {
                        childTop = (childBottom - childHeight) - lp.bottomMargin;
                        if (childBaseline != -1) {
                            int descent2 = child.getMeasuredHeight() - childBaseline;
                            childTop -= maxDescent[2] - descent2;
                        }
                    } else {
                        childTop = paddingTop;
                    }
                    if (hasDividerBeforeChildAt(childIndex)) {
                        childLeft5 += this.mDividerWidth;
                    }
                    int childLeft7 = childLeft5 + lp.leftMargin;
                    setChildFrame(child, childLeft7 + getLocationOffset(child), childTop, childWidth, childHeight);
                    childLeft5 = childLeft7 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                    i3 += getChildrenSkipCount(child, childIndex);
                }
            }
            i2 = i3 + 1;
            majorGravity2 = majorGravity;
            baselineAligned2 = baselineAligned;
            maxDescent = maxDescent;
            count = count;
            maxAscent = maxAscent;
            isLayoutRtl = isLayoutRtl;
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= 8388611;
            }
            if ((gravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(LinearLayoutCompat.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LinearLayoutCompat.class.getName());
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }
    }
}
