package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintWidget {
    public static float DEFAULT_BIAS = 0.5f;
    boolean mBottomHasCentered;
    private Object mCompanionWidget;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    boolean mHorizontalChainFixedPosition;
    boolean mHorizontalWrapVisited;
    boolean mIsHeightWrapContent;
    boolean mIsWidthWrapContent;
    boolean mLeftHasCentered;
    protected int mMinHeight;
    protected int mMinWidth;
    boolean mRightHasCentered;
    boolean mTopHasCentered;
    boolean mVerticalChainFixedPosition;
    boolean mVerticalWrapVisited;
    private int mWrapHeight;
    private int mWrapWidth;
    public int mHorizontalResolution = -1;
    public int mVerticalResolution = -1;
    int mMatchConstraintDefaultWidth = 0;
    int mMatchConstraintDefaultHeight = 0;
    int mMatchConstraintMinWidth = 0;
    int mMatchConstraintMaxWidth = 0;
    float mMatchConstraintPercentWidth = 1.0f;
    int mMatchConstraintMinHeight = 0;
    int mMatchConstraintMaxHeight = 0;
    float mMatchConstraintPercentHeight = 1.0f;
    ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
    ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
    ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
    ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
    ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
    ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
    ConstraintAnchor mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    protected ConstraintAnchor[] mListAnchors = {this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline};
    protected ArrayList<ConstraintAnchor> mAnchors = new ArrayList<>();
    ConstraintWidget mParent = null;
    int mWidth = 0;
    int mHeight = 0;
    protected float mDimensionRatio = 0.0f;
    protected int mDimensionRatioSide = -1;
    protected int mSolverLeft = 0;
    protected int mSolverTop = 0;
    protected int mSolverRight = 0;
    protected int mSolverBottom = 0;
    protected int mX = 0;
    protected int mY = 0;
    private int mDrawX = 0;
    private int mDrawY = 0;
    private int mDrawWidth = 0;
    private int mDrawHeight = 0;
    protected int mOffsetX = 0;
    protected int mOffsetY = 0;
    int mBaselineDistance = 0;
    float mHorizontalBiasPercent = DEFAULT_BIAS;
    float mVerticalBiasPercent = DEFAULT_BIAS;
    DimensionBehaviour mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
    DimensionBehaviour mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
    private int mContainerItemSkip = 0;
    private int mVisibility = 0;
    private String mDebugName = null;
    private String mType = null;
    int mHorizontalChainStyle = 0;
    int mVerticalChainStyle = 0;
    float mHorizontalWeight = 0.0f;
    float mVerticalWeight = 0.0f;
    ConstraintWidget mHorizontalNextWidget = null;
    ConstraintWidget mVerticalNextWidget = null;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mWrapWidth = 0;
        this.mWrapHeight = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
    }

    public ConstraintWidget() {
        addAnchors();
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public void setWidthWrapContent(boolean widthWrapContent) {
        this.mIsWidthWrapContent = widthWrapContent;
    }

    public void setHeightWrapContent(boolean heightWrapContent) {
        this.mIsHeightWrapContent = heightWrapContent;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        if (this.mType != null) {
            str = "type: " + this.mType + " ";
        } else {
            str = "";
        }
        sb.append(str);
        if (this.mDebugName != null) {
            str2 = "id: " + this.mDebugName + " ";
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.mX);
        sb.append(", ");
        sb.append(this.mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        sb.append(" wrap: (");
        sb.append(this.mWrapWidth);
        sb.append(" x ");
        sb.append(this.mWrapHeight);
        sb.append(")");
        return sb.toString();
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (this.mMatchConstraintDefaultWidth == 1) {
                w = Math.max(this.mMatchConstraintMinWidth, w2);
            } else if (this.mMatchConstraintMinWidth > 0) {
                w = this.mMatchConstraintMinWidth;
                this.mWidth = w;
            } else {
                w = 0;
            }
            if (this.mMatchConstraintMaxWidth > 0 && this.mMatchConstraintMaxWidth < w) {
                return this.mMatchConstraintMaxWidth;
            }
            return w;
        }
        return w2;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (this.mMatchConstraintDefaultHeight == 1) {
                h = Math.max(this.mMatchConstraintMinHeight, h2);
            } else if (this.mMatchConstraintMinHeight > 0) {
                h = this.mMatchConstraintMinHeight;
                this.mHeight = h;
            } else {
                h = 0;
            }
            if (this.mMatchConstraintMaxHeight > 0 && this.mMatchConstraintMaxHeight < h) {
                return this.mMatchConstraintMaxHeight;
            }
            return h;
        }
        return h2;
    }

    public int getWrapWidth() {
        return this.mWrapWidth;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getWrapHeight() {
        return this.mWrapHeight;
    }

    public int getDrawX() {
        return this.mDrawX + this.mOffsetX;
    }

    public int getDrawY() {
        return this.mDrawY + this.mOffsetY;
    }

    public int getDrawBottom() {
        return getDrawY() + this.mDrawHeight;
    }

    public int getDrawRight() {
        return getDrawX() + this.mDrawWidth;
    }

    protected int getRootX() {
        return this.mX + this.mOffsetX;
    }

    protected int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public boolean hasBaseline() {
        return this.mBaselineDistance > 0;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public void setOrigin(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void updateDrawPosition() {
        int left = this.mX;
        int top = this.mY;
        int right = this.mX + this.mWidth;
        int bottom = this.mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void setWidth(int w) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max;
        this.mMatchConstraintPercentWidth = percent;
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max;
        this.mMatchConstraintPercentHeight = percent;
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 > 0 && commaIndex2 < len - 1) {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        } else {
            commaIndex = 0;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex >= 0 && colonIndex < len - 1) {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setWrapWidth(int w) {
        this.mWrapWidth = w;
    }

    public void setWrapHeight(int h) {
        this.mWrapHeight = h;
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.mX = left;
        this.mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.mX = left;
        this.mWidth = right - left;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.mY = top;
        this.mHeight = bottom - top;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mHorizontalWeight = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mVerticalWeight = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        ConstraintAnchor startAnchor = getAnchor(startType);
        ConstraintAnchor endAnchor = target.getAnchor(endType);
        startAnchor.connect(endAnchor, margin, goneMargin, ConstraintAnchor.Strength.STRONG, 0, true);
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent != null && (parent instanceof ConstraintWidgetContainer)) {
            ConstraintWidgetContainer parentContainer = (ConstraintWidgetContainer) getParent();
            if (parentContainer.handlesInternalConstraints()) {
                return;
            }
        }
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            ConstraintAnchor anchor = this.mAnchors.get(i);
            anchor.reset();
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (anchorType) {
            case LEFT:
                return this.mLeft;
            case TOP:
                return this.mTop;
            case RIGHT:
                return this.mRight;
            case BOTTOM:
                return this.mBottom;
            case BASELINE:
                return this.mBaseline;
            case CENTER:
                return this.mCenter;
            case CENTER_X:
                return this.mCenterX;
            case CENTER_Y:
                return this.mCenterY;
            case NONE:
                return null;
            default:
                throw new AssertionError(anchorType.name());
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mHorizontalDimensionBehaviour;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mVerticalDimensionBehaviour;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mHorizontalDimensionBehaviour = behaviour;
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setWidth(this.mWrapWidth);
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mVerticalDimensionBehaviour = behaviour;
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setHeight(this.mWrapHeight);
        }
    }

    public void addToSolver(LinearSystem system, int group) {
        int width;
        int height;
        boolean horizontalDimensionFixed;
        boolean verticalDimensionFixed;
        boolean useRatio;
        int dimensionRatioSide;
        float dimensionRatio;
        boolean useVerticalRatio;
        int dimensionRatioSide2;
        int i;
        LinearSystem linearSystem;
        ConstraintWidget constraintWidget;
        int i2;
        SolverVariable bottom;
        SolverVariable top;
        SolverVariable left;
        SolverVariable right;
        int i3;
        SolverVariable bottom2;
        SolverVariable top2;
        SolverVariable baseline;
        SolverVariable top3;
        LinearSystem linearSystem2;
        SolverVariable bottom3;
        SolverVariable left2 = (group == Integer.MAX_VALUE || this.mLeft.mGroup == group) ? system.createObjectVariable(this.mLeft) : null;
        SolverVariable right2 = (group == Integer.MAX_VALUE || this.mRight.mGroup == group) ? system.createObjectVariable(this.mRight) : null;
        SolverVariable top4 = (group == Integer.MAX_VALUE || this.mTop.mGroup == group) ? system.createObjectVariable(this.mTop) : null;
        SolverVariable bottom4 = (group == Integer.MAX_VALUE || this.mBottom.mGroup == group) ? system.createObjectVariable(this.mBottom) : null;
        SolverVariable baseline2 = (group == Integer.MAX_VALUE || this.mBaseline.mGroup == group) ? system.createObjectVariable(this.mBaseline) : null;
        boolean inHorizontalChain = false;
        boolean inVerticalChain = false;
        if (this.mParent != null) {
            if ((this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) || (this.mRight.mTarget != null && this.mRight.mTarget.mTarget == this.mRight)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 0);
                inHorizontalChain = true;
            }
            if ((this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) || (this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == this.mBottom)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 1);
                inVerticalChain = true;
            }
            if (this.mParent.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !inHorizontalChain) {
                if (this.mLeft.mTarget == null || this.mLeft.mTarget.mOwner != this.mParent) {
                    SolverVariable parentLeft = system.createObjectVariable(this.mParent.mLeft);
                    ArrayRow row = system.createRow();
                    row.createRowGreaterThan(left2, parentLeft, system.createSlackVariable(), 0);
                    system.addConstraint(row);
                } else if (this.mLeft.mTarget != null && this.mLeft.mTarget.mOwner == this.mParent) {
                    this.mLeft.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
                if (this.mRight.mTarget == null || this.mRight.mTarget.mOwner != this.mParent) {
                    SolverVariable parentRight = system.createObjectVariable(this.mParent.mRight);
                    ArrayRow row2 = system.createRow();
                    row2.createRowGreaterThan(parentRight, right2, system.createSlackVariable(), 0);
                    system.addConstraint(row2);
                } else if (this.mRight.mTarget != null && this.mRight.mTarget.mOwner == this.mParent) {
                    this.mRight.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
            }
            if (this.mParent.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !inVerticalChain) {
                if (this.mTop.mTarget == null || this.mTop.mTarget.mOwner != this.mParent) {
                    SolverVariable parentTop = system.createObjectVariable(this.mParent.mTop);
                    ArrayRow row3 = system.createRow();
                    row3.createRowGreaterThan(top4, parentTop, system.createSlackVariable(), 0);
                    system.addConstraint(row3);
                } else if (this.mTop.mTarget != null && this.mTop.mTarget.mOwner == this.mParent) {
                    this.mTop.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
                if (this.mBottom.mTarget == null || this.mBottom.mTarget.mOwner != this.mParent) {
                    SolverVariable parentBottom = system.createObjectVariable(this.mParent.mBottom);
                    ArrayRow row4 = system.createRow();
                    row4.createRowGreaterThan(parentBottom, bottom4, system.createSlackVariable(), 0);
                    system.addConstraint(row4);
                } else if (this.mBottom.mTarget != null && this.mBottom.mTarget.mOwner == this.mParent) {
                    this.mBottom.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
            }
        }
        boolean inHorizontalChain2 = inHorizontalChain;
        boolean inVerticalChain2 = inVerticalChain;
        int width2 = this.mWidth;
        if (width2 < this.mMinWidth) {
            width2 = this.mMinWidth;
        }
        int height2 = this.mHeight;
        if (height2 < this.mMinHeight) {
            height2 = this.mMinHeight;
        }
        boolean horizontalDimensionFixed2 = this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT;
        boolean verticalDimensionFixed2 = this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT;
        if (!horizontalDimensionFixed2 && this.mLeft != null && this.mRight != null && (this.mLeft.mTarget == null || this.mRight.mTarget == null)) {
            horizontalDimensionFixed2 = true;
        }
        if (!verticalDimensionFixed2 && this.mTop != null && this.mBottom != null && ((this.mTop.mTarget == null || this.mBottom.mTarget == null) && (this.mBaselineDistance == 0 || (this.mBaseline != null && (this.mTop.mTarget == null || this.mBaseline.mTarget == null))))) {
            verticalDimensionFixed2 = true;
        }
        boolean useRatio2 = false;
        int dimensionRatioSide3 = this.mDimensionRatioSide;
        float dimensionRatio2 = this.mDimensionRatio;
        SolverVariable top5 = top4;
        if (this.mDimensionRatio <= 0.0f || this.mVisibility == 8) {
            width = width2;
            height = height2;
            horizontalDimensionFixed = horizontalDimensionFixed2;
            verticalDimensionFixed = verticalDimensionFixed2;
            useRatio = useRatio2;
            dimensionRatioSide = dimensionRatioSide3;
            dimensionRatio = dimensionRatio2;
        } else {
            if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                useRatio2 = true;
                if (horizontalDimensionFixed2 && !verticalDimensionFixed2) {
                    dimensionRatioSide3 = 0;
                } else if (!horizontalDimensionFixed2 && verticalDimensionFixed2) {
                    dimensionRatioSide3 = 1;
                    if (this.mDimensionRatioSide == -1) {
                        width = width2;
                        height = height2;
                        horizontalDimensionFixed = horizontalDimensionFixed2;
                        verticalDimensionFixed = verticalDimensionFixed2;
                        useRatio = true;
                        dimensionRatioSide = 1;
                        dimensionRatio = 1.0f / dimensionRatio2;
                    }
                }
            } else if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                dimensionRatioSide3 = 0;
                width2 = (int) (this.mHeight * dimensionRatio2);
                horizontalDimensionFixed2 = true;
            } else if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                dimensionRatioSide3 = 1;
                if (this.mDimensionRatioSide == -1) {
                    dimensionRatio2 = 1.0f / dimensionRatio2;
                }
                height2 = (int) (this.mWidth * dimensionRatio2);
                verticalDimensionFixed2 = true;
            }
            width = width2;
            height = height2;
            horizontalDimensionFixed = horizontalDimensionFixed2;
            verticalDimensionFixed = verticalDimensionFixed2;
            useRatio = useRatio2;
            dimensionRatioSide = dimensionRatioSide3;
            dimensionRatio = dimensionRatio2;
        }
        boolean useHorizontalRatio = useRatio && (dimensionRatioSide == 0 || dimensionRatioSide == -1);
        boolean wrapContent = this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && (this instanceof ConstraintWidgetContainer);
        if (this.mHorizontalResolution == 2) {
            baseline2 = baseline2;
            bottom4 = bottom4;
            top5 = top5;
            useVerticalRatio = false;
        } else if (group != Integer.MAX_VALUE && (this.mLeft.mGroup != group || this.mRight.mGroup != group)) {
            baseline2 = baseline2;
            bottom4 = bottom4;
            top5 = top5;
            useVerticalRatio = false;
        } else if (!useHorizontalRatio || this.mLeft.mTarget == null || this.mRight.mTarget == null) {
            baseline2 = baseline2;
            bottom4 = bottom4;
            top5 = top5;
            useVerticalRatio = false;
            applyConstraints(system, wrapContent, horizontalDimensionFixed, this.mLeft, this.mRight, this.mX, this.mX + width, width, this.mMinWidth, this.mHorizontalBiasPercent, useHorizontalRatio, inHorizontalChain2, this.mMatchConstraintDefaultWidth, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth);
        } else {
            SolverVariable begin = system.createObjectVariable(this.mLeft);
            SolverVariable end = system.createObjectVariable(this.mRight);
            SolverVariable beginTarget = system.createObjectVariable(this.mLeft.getTarget());
            SolverVariable endTarget = system.createObjectVariable(this.mRight.getTarget());
            system.addGreaterThan(begin, beginTarget, this.mLeft.getMargin(), 3);
            system.addLowerThan(end, endTarget, this.mRight.getMargin() * (-1), 3);
            if (inHorizontalChain2) {
                useVerticalRatio = false;
            } else {
                useVerticalRatio = false;
                system.addCentering(begin, beginTarget, this.mLeft.getMargin(), this.mHorizontalBiasPercent, endTarget, end, this.mRight.getMargin(), 4);
            }
        }
        if (this.mVerticalResolution == 2) {
            return;
        }
        boolean wrapContent2 = (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && (this instanceof ConstraintWidgetContainer)) ? true : useVerticalRatio;
        if (useRatio) {
            dimensionRatioSide2 = dimensionRatioSide;
            if (dimensionRatioSide2 != 1) {
                i = -1;
                if (dimensionRatioSide2 == -1) {
                }
            } else {
                i = -1;
            }
            useVerticalRatio = true;
        } else {
            dimensionRatioSide2 = dimensionRatioSide;
            i = -1;
        }
        if (this.mBaselineDistance > 0) {
            ConstraintAnchor endAnchor = this.mBottom;
            if (group == Integer.MAX_VALUE || (this.mBottom.mGroup == group && this.mBaseline.mGroup == group)) {
                baseline = baseline2;
                top3 = top5;
                linearSystem2 = system;
                linearSystem2.addEquality(baseline, top3, getBaselineDistance(), 5);
            } else {
                baseline = baseline2;
                top3 = top5;
                linearSystem2 = system;
            }
            int originalHeight = height;
            if (this.mBaseline.mTarget != null) {
                int height3 = this.mBaselineDistance;
                endAnchor = this.mBaseline;
                height = height3;
            }
            if (group == Integer.MAX_VALUE || (this.mTop.mGroup == group && endAnchor.mGroup == group)) {
                if (!useVerticalRatio || this.mTop.mTarget == null || this.mBottom.mTarget == null) {
                    ConstraintAnchor endAnchor2 = endAnchor;
                    linearSystem = system;
                    applyConstraints(linearSystem, wrapContent2, verticalDimensionFixed, this.mTop, endAnchor2, this.mY, this.mY + height, height, this.mMinHeight, this.mVerticalBiasPercent, useVerticalRatio, inVerticalChain2, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight, this.mMatchConstraintPercentHeight);
                    bottom3 = bottom4;
                    top3 = top3;
                    linearSystem.addEquality(bottom3, top3, originalHeight, 5);
                } else {
                    SolverVariable begin2 = linearSystem2.createObjectVariable(this.mTop);
                    SolverVariable end2 = linearSystem2.createObjectVariable(this.mBottom);
                    SolverVariable beginTarget2 = linearSystem2.createObjectVariable(this.mTop.getTarget());
                    SolverVariable endTarget2 = linearSystem2.createObjectVariable(this.mBottom.getTarget());
                    linearSystem2.addGreaterThan(begin2, beginTarget2, this.mTop.getMargin(), 3);
                    linearSystem2.addLowerThan(end2, endTarget2, i * this.mBottom.getMargin(), 3);
                    if (!inVerticalChain2) {
                        linearSystem2.addCentering(begin2, beginTarget2, this.mTop.getMargin(), this.mVerticalBiasPercent, endTarget2, end2, this.mBottom.getMargin(), 4);
                    }
                    linearSystem = system;
                }
                bottom = bottom3;
                top = top3;
                constraintWidget = this;
                i2 = group;
            } else {
                linearSystem = linearSystem2;
            }
            bottom3 = bottom4;
            bottom = bottom3;
            top = top3;
            constraintWidget = this;
            i2 = group;
        } else {
            dimensionRatioSide2 = dimensionRatioSide2;
            SolverVariable bottom5 = bottom4;
            SolverVariable top6 = top5;
            linearSystem = system;
            if (group != Integer.MAX_VALUE) {
                constraintWidget = this;
                if (constraintWidget.mTop.mGroup != group || constraintWidget.mBottom.mGroup != group) {
                    constraintWidget = constraintWidget;
                }
                i2 = group;
                bottom = bottom5;
                top = top6;
            } else {
                constraintWidget = this;
            }
            if (!useVerticalRatio || constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null) {
                constraintWidget = constraintWidget;
                i2 = group;
                bottom = bottom5;
                top = top6;
                constraintWidget.applyConstraints(linearSystem, wrapContent2, verticalDimensionFixed, constraintWidget.mTop, constraintWidget.mBottom, constraintWidget.mY, constraintWidget.mY + height, height, constraintWidget.mMinHeight, constraintWidget.mVerticalBiasPercent, useVerticalRatio, inVerticalChain2, constraintWidget.mMatchConstraintDefaultHeight, constraintWidget.mMatchConstraintMinHeight, constraintWidget.mMatchConstraintMaxHeight, constraintWidget.mMatchConstraintPercentHeight);
            } else {
                SolverVariable begin3 = linearSystem.createObjectVariable(constraintWidget.mTop);
                SolverVariable end3 = linearSystem.createObjectVariable(constraintWidget.mBottom);
                SolverVariable beginTarget3 = linearSystem.createObjectVariable(constraintWidget.mTop.getTarget());
                SolverVariable endTarget3 = linearSystem.createObjectVariable(constraintWidget.mBottom.getTarget());
                linearSystem.addGreaterThan(begin3, beginTarget3, constraintWidget.mTop.getMargin(), 3);
                linearSystem.addLowerThan(end3, endTarget3, i * constraintWidget.mBottom.getMargin(), 3);
                if (!inVerticalChain2) {
                    linearSystem.addCentering(begin3, beginTarget3, constraintWidget.mTop.getMargin(), constraintWidget.mVerticalBiasPercent, endTarget3, end3, constraintWidget.mBottom.getMargin(), 4);
                }
                i2 = group;
                bottom = bottom5;
                top = top6;
            }
        }
        if (useRatio) {
            ArrayRow row5 = system.createRow();
            if (i2 == Integer.MAX_VALUE || (constraintWidget.mLeft.mGroup == i2 && constraintWidget.mRight.mGroup == i2)) {
                int dimensionRatioSide4 = dimensionRatioSide2;
                if (dimensionRatioSide4 == 0) {
                    linearSystem.addConstraint(row5.createRowDimensionRatio(right2, left2, bottom, top, dimensionRatio));
                } else {
                    if (dimensionRatioSide4 != 1) {
                        if (constraintWidget.mMatchConstraintMinWidth > 0) {
                            left = left2;
                            right = right2;
                            i3 = 3;
                            linearSystem.addGreaterThan(right, left, constraintWidget.mMatchConstraintMinWidth, 3);
                        } else {
                            left = left2;
                            right = right2;
                            i3 = 3;
                        }
                        if (constraintWidget.mMatchConstraintMinHeight > 0) {
                            bottom2 = bottom;
                            top2 = top;
                            linearSystem.addGreaterThan(bottom2, top2, constraintWidget.mMatchConstraintMinHeight, i3);
                        } else {
                            bottom2 = bottom;
                            top2 = top;
                        }
                        row5.createRowDimensionRatio(right, left, bottom2, top2, dimensionRatio);
                        SolverVariable error1 = system.createErrorVariable();
                        SolverVariable error2 = system.createErrorVariable();
                        error1.strength = 4;
                        error2.strength = 4;
                        row5.addError(error1, error2);
                        linearSystem.addConstraint(row5);
                        return;
                    }
                    linearSystem.addConstraint(row5.createRowDimensionRatio(bottom, top, right2, left2, dimensionRatio));
                }
            }
        }
    }

    private void applyConstraints(LinearSystem system, boolean wrapContent, boolean dimensionFixed, ConstraintAnchor beginAnchor, ConstraintAnchor endAnchor, int beginPosition, int endPosition, int dimension, int minDimension, float bias, boolean useRatio, boolean inChain, int matchConstraintDefault, int matchMinDimension, int matchMaxDimension, float matchPercentDimension) {
        boolean dimensionFixed2;
        int dimension2;
        int beginAnchorMargin;
        int dimension3;
        SolverVariable percentEnd;
        SolverVariable percentBegin;
        int matchMaxDimension2;
        int i;
        int beginAnchorMargin2;
        SolverVariable end;
        SolverVariable percentEnd2;
        SolverVariable percentBegin2;
        SolverVariable end2;
        SolverVariable percentBegin3;
        SolverVariable percentEnd3;
        int dimension4;
        SolverVariable endTarget;
        SolverVariable beginTarget;
        SolverVariable end3;
        int matchMinDimension2;
        int matchMaxDimension3;
        int endAnchorMargin;
        int matchMaxDimension4;
        SolverVariable percentEnd4;
        SolverVariable percentBegin4;
        SolverVariable begin = system.createObjectVariable(beginAnchor);
        SolverVariable end4 = system.createObjectVariable(endAnchor);
        SolverVariable beginTarget2 = system.createObjectVariable(beginAnchor.getTarget());
        SolverVariable endTarget2 = system.createObjectVariable(endAnchor.getTarget());
        int beginAnchorMargin3 = beginAnchor.getMargin();
        int endAnchorMargin2 = endAnchor.getMargin();
        if (this.mVisibility == 8) {
            dimension2 = 0;
            dimensionFixed2 = true;
        } else {
            dimensionFixed2 = dimensionFixed;
            dimension2 = dimension;
        }
        int matchMinDimension3 = matchMinDimension;
        if (matchMinDimension3 == -2) {
            matchMinDimension3 = dimension2;
        }
        int matchMaxDimension5 = matchMaxDimension;
        if (matchMaxDimension5 == -2) {
            int matchMaxDimension6 = dimension2;
            matchMaxDimension5 = matchMaxDimension6;
        }
        int dimension5 = Math.max(dimension2, matchMinDimension3);
        if (beginTarget2 == null && endTarget2 == null) {
            system.addConstraint(system.createRow().createRowEquals(begin, beginPosition));
            if (matchConstraintDefault == 2) {
                if (beginAnchor.getType() == ConstraintAnchor.Type.TOP || beginAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                    SolverVariable percentBegin5 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                    SolverVariable percentEnd5 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                    percentEnd4 = percentEnd5;
                    percentBegin4 = percentBegin5;
                } else {
                    SolverVariable percentBegin6 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                    percentBegin4 = percentBegin6;
                    SolverVariable percentEnd6 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                    percentEnd4 = percentEnd6;
                }
                endAnchorMargin = endAnchorMargin2;
                matchMaxDimension3 = matchMaxDimension5;
                matchMaxDimension4 = beginAnchorMargin3;
                dimension4 = dimension5;
                endTarget = endTarget2;
                beginTarget = beginTarget2;
                end3 = end4;
                matchMinDimension2 = matchMinDimension3;
                system.addConstraint(system.createRow().createRowDimensionRatio(end4, begin, percentEnd4, percentBegin4, matchPercentDimension));
            } else {
                dimension4 = dimension5;
                endTarget = endTarget2;
                beginTarget = beginTarget2;
                end3 = end4;
                matchMinDimension2 = matchMinDimension3;
                matchMaxDimension3 = matchMaxDimension5;
                endAnchorMargin = endAnchorMargin2;
                matchMaxDimension4 = beginAnchorMargin3;
                if (!useRatio) {
                    if (wrapContent) {
                        system.addConstraint(LinearSystem.createRowEquals(system, end3, begin, minDimension, true));
                    } else if (dimensionFixed2) {
                        system.addConstraint(LinearSystem.createRowEquals(system, end3, begin, dimension4, false));
                    } else {
                        system.addConstraint(system.createRow().createRowEquals(end3, endPosition));
                    }
                }
            }
            return;
        }
        SolverVariable endTarget3 = endTarget2;
        int dimension6 = matchMinDimension3;
        int matchMaxDimension7 = matchMaxDimension5;
        if (beginTarget2 == null || endTarget3 != null) {
            beginAnchorMargin = beginAnchorMargin3;
            SolverVariable end5 = end4;
            if (beginTarget2 != null || endTarget3 == null) {
                if (dimensionFixed2) {
                    if (wrapContent) {
                        system.addConstraint(LinearSystem.createRowEquals(system, end5, begin, minDimension, true));
                    } else {
                        system.addConstraint(system.createRow().createRowEquals(end5, begin, dimension5));
                    }
                    if (beginAnchor.getStrength() != endAnchor.getStrength()) {
                        if (beginAnchor.getStrength() == ConstraintAnchor.Strength.STRONG) {
                            beginAnchorMargin2 = beginAnchorMargin;
                            system.addConstraint(system.createRow().createRowEquals(begin, beginTarget2, beginAnchorMargin2));
                            SolverVariable slack = system.createSlackVariable();
                            ArrayRow row = system.createRow();
                            row.createRowLowerThan(end5, endTarget3, slack, (-1) * endAnchorMargin2);
                            system.addConstraint(row);
                        } else {
                            beginAnchorMargin2 = beginAnchorMargin;
                            SolverVariable slack2 = system.createSlackVariable();
                            ArrayRow row2 = system.createRow();
                            row2.createRowGreaterThan(begin, beginTarget2, slack2, beginAnchorMargin2);
                            system.addConstraint(row2);
                            system.addConstraint(system.createRow().createRowEquals(end5, endTarget3, (-1) * endAnchorMargin2));
                        }
                    } else {
                        if (beginTarget2 == endTarget3) {
                            system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget2, 0, 0.5f, endTarget3, end5, 0, true));
                            return;
                        }
                        if (inChain) {
                            return;
                        }
                        boolean useBidirectionalError = beginAnchor.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                        system.addConstraint(LinearSystem.createRowGreaterThan(system, begin, beginTarget2, beginAnchorMargin, useBidirectionalError));
                        boolean useBidirectionalError2 = endAnchor.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                        system.addConstraint(LinearSystem.createRowLowerThan(system, end5, endTarget3, (-1) * endAnchorMargin2, useBidirectionalError2));
                        dimension3 = dimension5;
                        system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget2, beginAnchorMargin, bias, endTarget3, end5, endAnchorMargin2, false));
                    }
                } else {
                    SolverVariable begin2 = begin;
                    dimension3 = dimension5;
                    if (useRatio) {
                        system.addGreaterThan(begin2, beginTarget2, beginAnchorMargin, 3);
                        system.addLowerThan(end5, endTarget3, (-1) * endAnchorMargin2, 3);
                        system.addConstraint(LinearSystem.createRowCentering(system, begin2, beginTarget2, beginAnchorMargin, bias, endTarget3, end5, endAnchorMargin2, true));
                        return;
                    }
                    if (inChain) {
                        return;
                    }
                    if (matchConstraintDefault == 1 || matchConstraintDefault == 2) {
                        if (matchConstraintDefault == 2) {
                            if (beginAnchor.getType() == ConstraintAnchor.Type.TOP || beginAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                                percentBegin = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                                percentEnd = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                            } else {
                                begin2 = begin2;
                                percentBegin = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                                percentEnd = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                            }
                            system.addConstraint(system.createRow().createRowDimensionRatio(end5, begin2, percentEnd, percentBegin, matchPercentDimension));
                        } else {
                            begin2 = begin2;
                        }
                        int dimension7 = dimension3;
                        if (dimension6 > dimension7) {
                            dimension7 = dimension6;
                        }
                        if (matchMaxDimension7 > 0) {
                            if (matchMaxDimension7 < dimension7) {
                                dimension7 = matchMaxDimension7;
                            } else {
                                system.addLowerThan(end5, begin2, matchMaxDimension7, 3);
                            }
                        }
                        system.addEquality(end5, begin2, dimension7, 3);
                        system.addGreaterThan(begin2, beginTarget2, beginAnchorMargin, 2);
                        system.addLowerThan(end5, endTarget3, -endAnchorMargin2, 2);
                        system.addCentering(begin2, beginTarget2, beginAnchorMargin, bias, endTarget3, end5, endAnchorMargin2, 4);
                        return;
                    }
                    if (dimension6 != 0 || matchMaxDimension7 != 0) {
                        if (matchMaxDimension7 > 0) {
                            matchMaxDimension2 = matchMaxDimension7;
                            system.addLowerThan(end5, begin2, matchMaxDimension2, 3);
                        } else {
                            matchMaxDimension2 = matchMaxDimension7;
                        }
                        if (dimension6 > 0) {
                            i = 2;
                            system.addGreaterThan(end5, begin2, dimension6, 2);
                        } else {
                            i = 2;
                        }
                        system.addGreaterThan(begin2, beginTarget2, beginAnchorMargin, i);
                        system.addLowerThan(end5, endTarget3, -endAnchorMargin2, i);
                        system.addCentering(begin2, beginTarget2, beginAnchorMargin, bias, endTarget3, end5, endAnchorMargin2, 4);
                        return;
                    }
                    system.addConstraint(system.createRow().createRowEquals(begin2, beginTarget2, beginAnchorMargin));
                    system.addConstraint(system.createRow().createRowEquals(end5, endTarget3, (-1) * endAnchorMargin2));
                }
                return;
            }
            system.addConstraint(system.createRow().createRowEquals(end5, endTarget3, (-1) * endAnchorMargin2));
            if (matchConstraintDefault == 2) {
                if (beginAnchor.getType() == ConstraintAnchor.Type.TOP || beginAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                    percentBegin2 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                    percentEnd2 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                } else {
                    end5 = end5;
                    percentBegin2 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                    percentEnd2 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                }
                end = end5;
                system.addConstraint(system.createRow().createRowDimensionRatio(end5, begin, percentEnd2, percentBegin2, matchPercentDimension));
            } else {
                end = end5;
                if (wrapContent) {
                    system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
                } else {
                    if (useRatio) {
                        return;
                    }
                    if (dimensionFixed2) {
                        system.addConstraint(system.createRow().createRowEquals(end, begin, dimension5));
                    } else {
                        system.addConstraint(system.createRow().createRowEquals(begin, beginPosition));
                    }
                }
            }
        }
        system.addConstraint(system.createRow().createRowEquals(begin, beginTarget2, beginAnchorMargin3));
        if (matchConstraintDefault == 2) {
            if (beginAnchor.getType() == ConstraintAnchor.Type.TOP || beginAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                SolverVariable percentBegin7 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                SolverVariable percentEnd7 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                percentEnd3 = percentEnd7;
                percentBegin3 = percentBegin7;
            } else {
                endTarget3 = endTarget3;
                SolverVariable percentBegin8 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                percentBegin3 = percentBegin8;
                SolverVariable percentEnd8 = system.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                percentEnd3 = percentEnd8;
            }
            beginAnchorMargin = beginAnchorMargin3;
            end2 = end4;
            system.addConstraint(system.createRow().createRowDimensionRatio(end4, begin, percentEnd3, percentBegin3, matchPercentDimension));
        } else {
            beginAnchorMargin = beginAnchorMargin3;
            end2 = end4;
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end2, begin, minDimension, true));
            } else if (!useRatio) {
                if (dimensionFixed2) {
                    system.addConstraint(system.createRow().createRowEquals(end2, begin, dimension5));
                } else {
                    system.addConstraint(system.createRow().createRowEquals(end2, endPosition));
                }
            }
        }
    }

    public void updateFromSolver(LinearSystem system, int group) {
        if (group == Integer.MAX_VALUE) {
            int left = system.getObjectVariableValue(this.mLeft);
            int top = system.getObjectVariableValue(this.mTop);
            int right = system.getObjectVariableValue(this.mRight);
            int bottom = system.getObjectVariableValue(this.mBottom);
            setFrame(left, top, right, bottom);
            return;
        }
        if (group == -2) {
            setFrame(this.mSolverLeft, this.mSolverTop, this.mSolverRight, this.mSolverBottom);
            return;
        }
        if (this.mLeft.mGroup == group) {
            this.mSolverLeft = system.getObjectVariableValue(this.mLeft);
        }
        if (this.mTop.mGroup == group) {
            this.mSolverTop = system.getObjectVariableValue(this.mTop);
        }
        if (this.mRight.mGroup == group) {
            this.mSolverRight = system.getObjectVariableValue(this.mRight);
        }
        if (this.mBottom.mGroup == group) {
            this.mSolverBottom = system.getObjectVariableValue(this.mBottom);
        }
    }
}
