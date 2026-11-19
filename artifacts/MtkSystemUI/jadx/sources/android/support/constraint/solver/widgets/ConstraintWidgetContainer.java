package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintWidgetContainer extends WidgetContainer {
    static boolean ALLOW_ROOT_GROUP = true;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    private Snapshot mSnapshot;
    int mWrapHeight;
    int mWrapWidth;
    protected LinearSystem mSystem = new LinearSystem();
    protected LinearSystem mBackgroundSystem = null;
    private int mHorizontalChainsSize = 0;
    private int mVerticalChainsSize = 0;
    private ConstraintWidget[] mMatchConstraintsChainedWidgets = new ConstraintWidget[4];
    private ConstraintWidget[] mVerticalChainsArray = new ConstraintWidget[4];
    private ConstraintWidget[] mHorizontalChainsArray = new ConstraintWidget[4];
    private int mOptimizationLevel = 2;
    private boolean[] flags = new boolean[3];
    private ConstraintWidget[] mChainEnds = new ConstraintWidget[4];
    private boolean mWidthMeasuredTooSmall = false;
    private boolean mHeightMeasuredTooSmall = false;

    public void setOptimizationLevel(int value) {
        this.mOptimizationLevel = value;
    }

    @Override // android.support.constraint.solver.widgets.WidgetContainer, android.support.constraint.solver.widgets.ConstraintWidget
    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        super.reset();
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public boolean addChildrenToSolver(LinearSystem system, int group) {
        addToSolver(system, group);
        int count = this.mChildren.size();
        boolean setMatchParent = false;
        int i = 0;
        if (this.mOptimizationLevel == 2 || this.mOptimizationLevel == 4) {
            if (optimize(system)) {
                return false;
            }
        } else {
            setMatchParent = true;
        }
        while (true) {
            int i2 = i;
            if (i2 >= count) {
                break;
            }
            ConstraintWidget widget = this.mChildren.get(i2);
            if (widget instanceof ConstraintWidgetContainer) {
                ConstraintWidget.DimensionBehaviour horizontalBehaviour = widget.mHorizontalDimensionBehaviour;
                ConstraintWidget.DimensionBehaviour verticalBehaviour = widget.mVerticalDimensionBehaviour;
                if (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                widget.addToSolver(system, group);
                if (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(horizontalBehaviour);
                }
                if (verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(verticalBehaviour);
                }
            } else {
                if (setMatchParent) {
                    Optimizer.checkMatchParent(this, system, widget);
                }
                widget.addToSolver(system, group);
            }
            i = i2 + 1;
        }
        if (this.mHorizontalChainsSize > 0) {
            applyHorizontalChain(system);
        }
        if (this.mVerticalChainsSize > 0) {
            applyVerticalChain(system);
            return true;
        }
        return true;
    }

    private boolean optimize(LinearSystem system) {
        int count = this.mChildren.size();
        boolean done = false;
        int dv = 0;
        int dv2 = 0;
        int n = 0;
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            widget.mHorizontalResolution = -1;
            widget.mVerticalResolution = -1;
            if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                widget.mHorizontalResolution = 1;
                widget.mVerticalResolution = 1;
            }
            if (widget instanceof Barrier) {
                widget.mHorizontalResolution = 1;
                widget.mVerticalResolution = 1;
            }
        }
        while (!done) {
            int prev = dv;
            int preh = dv2;
            n++;
            int dh = 0;
            int dv3 = 0;
            for (int dv4 = 0; dv4 < count; dv4++) {
                ConstraintWidget widget2 = this.mChildren.get(dv4);
                if (widget2.mHorizontalResolution == -1) {
                    if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        widget2.mHorizontalResolution = 1;
                    } else {
                        Optimizer.checkHorizontalSimpleDependency(this, system, widget2);
                    }
                }
                if (widget2.mVerticalResolution == -1) {
                    if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        widget2.mVerticalResolution = 1;
                    } else {
                        Optimizer.checkVerticalSimpleDependency(this, system, widget2);
                    }
                }
                if (widget2.mVerticalResolution == -1) {
                    dv3++;
                }
                if (widget2.mHorizontalResolution == -1) {
                    dh++;
                }
            }
            if (dv3 == 0 && dh == 0) {
                done = true;
            } else if (prev == dv3 && preh == dh) {
                done = true;
            }
            dv = dv3;
            dv2 = dh;
        }
        int sv = 0;
        int sv2 = 0;
        for (int sh = 0; sh < count; sh++) {
            ConstraintWidget widget3 = this.mChildren.get(sh);
            if (widget3.mHorizontalResolution == 1 || widget3.mHorizontalResolution == -1) {
                sv2++;
            }
            if (widget3.mVerticalResolution == 1 || widget3.mVerticalResolution == -1) {
                sv++;
            }
        }
        return sv2 == 0 && sv == 0;
    }

    /* JADX WARN: Code duplicated, block: B:201:0x0529  */
    /* JADX WARN: Code duplicated, block: B:204:0x0539  */
    /* JADX WARN: Code duplicated, block: B:205:0x053c  */
    private void applyHorizontalChain(LinearSystem system) {
        LinearSystem linearSystem;
        int i;
        char c;
        char c2;
        ConstraintWidget next;
        ConstraintWidget first;
        ConstraintWidget first2;
        ConstraintWidget constraintWidget;
        int margin;
        int i2;
        int i3;
        LinearSystem linearSystem2 = system;
        char c3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i4;
            int i6 = this.mHorizontalChainsSize;
            if (i5 >= i6) {
                return;
            }
            ConstraintWidget first3 = this.mHorizontalChainsArray[i5];
            int numMatchConstraints = countMatchConstraintsChainedWidgets(linearSystem2, this.mChainEnds, this.mHorizontalChainsArray[i5], 0, this.flags);
            ConstraintWidget currentWidget = this.mChainEnds[2];
            if (currentWidget == null) {
                i = i5;
                c = c3;
                linearSystem = linearSystem2;
            } else {
                int i7 = 1;
                if (this.flags[1]) {
                    int x = first3.getDrawX();
                    while (currentWidget != null) {
                        linearSystem2.addEquality(currentWidget.mLeft.mSolverVariable, x);
                        ConstraintWidget next2 = currentWidget.mHorizontalNextWidget;
                        x += currentWidget.mLeft.getMargin() + currentWidget.getWidth() + currentWidget.mRight.getMargin();
                        currentWidget = next2;
                    }
                } else {
                    char c4 = first3.mHorizontalChainStyle == 0 ? (char) 1 : c3;
                    char c5 = first3.mHorizontalChainStyle == 2 ? (char) 1 : c3;
                    ConstraintWidget widget = first3;
                    char c6 = this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? (char) 1 : c3;
                    if ((this.mOptimizationLevel == 2 || this.mOptimizationLevel == 8) && this.flags[c3] && widget.mHorizontalChainFixedPosition && c5 == 0 && c6 == 0 && first3.mHorizontalChainStyle == 0) {
                        Optimizer.applyDirectResolutionHorizontalChain(this, linearSystem2, numMatchConstraints, widget);
                    } else {
                        char c7 = 3;
                        if (numMatchConstraints == 0 || c5 != 0) {
                            boolean isLast = false;
                            ConstraintWidget lastWidget = null;
                            ConstraintWidget previousVisibleWidget = null;
                            ConstraintWidget previousVisibleWidget2 = null;
                            while (currentWidget != null) {
                                ConstraintWidget next3 = currentWidget.mHorizontalNextWidget;
                                if (next3 == null) {
                                    lastWidget = this.mChainEnds[1];
                                    isLast = true;
                                }
                                if (c5 != 0) {
                                    ConstraintAnchor left = currentWidget.mLeft;
                                    int margin2 = left.getMargin();
                                    if (previousVisibleWidget != null) {
                                        int previousMargin = previousVisibleWidget.mRight.getMargin();
                                        margin2 += previousMargin;
                                    }
                                    int strength = currentWidget != currentWidget ? 3 : 1;
                                    linearSystem2.addGreaterThan(left.mSolverVariable, left.mTarget.mSolverVariable, margin2, strength);
                                    if (currentWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                        ConstraintAnchor right = currentWidget.mRight;
                                        if (currentWidget.mMatchConstraintDefaultWidth == 1) {
                                            int dimension = Math.max(currentWidget.mMatchConstraintMinWidth, currentWidget.getWidth());
                                            c2 = 3;
                                            linearSystem2.addEquality(right.mSolverVariable, left.mSolverVariable, dimension, 3);
                                        } else {
                                            c2 = 3;
                                            linearSystem2.addGreaterThan(left.mSolverVariable, left.mTarget.mSolverVariable, left.mMargin, 3);
                                            linearSystem2.addLowerThan(right.mSolverVariable, left.mSolverVariable, currentWidget.mMatchConstraintMinWidth, 3);
                                        }
                                    } else {
                                        c2 = 3;
                                    }
                                } else {
                                    c2 = 3;
                                    if (c4 != 0 || !isLast || previousVisibleWidget == null) {
                                        if (c4 == 0 && !isLast && previousVisibleWidget == null) {
                                            if (currentWidget.mLeft.mTarget == null) {
                                                linearSystem2.addEquality(currentWidget.mLeft.mSolverVariable, currentWidget.getDrawX());
                                            } else {
                                                int margin3 = currentWidget.mLeft.getMargin();
                                                first2 = first3;
                                                linearSystem2.addEquality(currentWidget.mLeft.mSolverVariable, first2.mLeft.mTarget.mSolverVariable, margin3, 5);
                                            }
                                            next = next3;
                                            first = first2;
                                        } else {
                                            ConstraintWidget first4 = first3;
                                            ConstraintAnchor left2 = currentWidget.mLeft;
                                            ConstraintAnchor right2 = currentWidget.mRight;
                                            int leftMargin = left2.getMargin();
                                            int rightMargin = right2.getMargin();
                                            linearSystem2.addGreaterThan(left2.mSolverVariable, left2.mTarget.mSolverVariable, leftMargin, 1);
                                            int leftMargin2 = -rightMargin;
                                            linearSystem2.addLowerThan(right2.mSolverVariable, right2.mTarget.mSolverVariable, leftMargin2, 1);
                                            SolverVariable leftTarget = left2.mTarget != null ? left2.mTarget.mSolverVariable : null;
                                            if (previousVisibleWidget == null) {
                                                leftTarget = first4.mLeft.mTarget != null ? first4.mLeft.mTarget.mSolverVariable : null;
                                            }
                                            if (next3 == null) {
                                                next3 = lastWidget.mRight.mTarget != null ? lastWidget.mRight.mTarget.mOwner : null;
                                            }
                                            if (next3 != null) {
                                                SolverVariable rightTarget = next3.mLeft.mSolverVariable;
                                                if (isLast) {
                                                    rightTarget = lastWidget.mRight.mTarget != null ? lastWidget.mRight.mTarget.mSolverVariable : null;
                                                }
                                                SolverVariable rightTarget2 = rightTarget;
                                                if (leftTarget == null || rightTarget2 == null) {
                                                    next = next3;
                                                    first = first4;
                                                } else {
                                                    first = first4;
                                                    next = next3;
                                                    linearSystem2.addCentering(left2.mSolverVariable, leftTarget, leftMargin, 0.5f, rightTarget2, right2.mSolverVariable, rightMargin, 4);
                                                }
                                            } else {
                                                next = next3;
                                                first = first4;
                                            }
                                        }
                                        previousVisibleWidget = currentWidget;
                                        if (isLast) {
                                            constraintWidget = null;
                                        } else {
                                            constraintWidget = next;
                                        }
                                        currentWidget = constraintWidget;
                                        linearSystem2 = linearSystem2;
                                        i5 = i5;
                                        widget = widget;
                                        first3 = first;
                                        previousVisibleWidget2 = next;
                                        this = this;
                                    } else if (currentWidget.mRight.mTarget == null) {
                                        linearSystem2.addEquality(currentWidget.mRight.mSolverVariable, currentWidget.getDrawRight());
                                    } else {
                                        int margin4 = currentWidget.mRight.getMargin();
                                        linearSystem2.addEquality(currentWidget.mRight.mSolverVariable, lastWidget.mRight.mTarget.mSolverVariable, -margin4, 5);
                                    }
                                }
                                first2 = first3;
                                next = next3;
                                first = first2;
                                previousVisibleWidget = currentWidget;
                                if (isLast) {
                                    constraintWidget = null;
                                } else {
                                    constraintWidget = next;
                                }
                                currentWidget = constraintWidget;
                                linearSystem2 = linearSystem2;
                                i5 = i5;
                                widget = widget;
                                first3 = first;
                                previousVisibleWidget2 = next;
                                this = this;
                            }
                            linearSystem = linearSystem2;
                            i = i5;
                            ConstraintWidget first5 = first3;
                            c = 0;
                            if (c5 != 0) {
                                ConstraintAnchor left3 = currentWidget.mLeft;
                                ConstraintAnchor right3 = lastWidget.mRight;
                                int leftMargin3 = left3.getMargin();
                                int rightMargin2 = right3.getMargin();
                                SolverVariable leftTarget2 = first5.mLeft.mTarget != null ? first5.mLeft.mTarget.mSolverVariable : null;
                                SolverVariable rightTarget3 = lastWidget.mRight.mTarget != null ? lastWidget.mRight.mTarget.mSolverVariable : null;
                                if (leftTarget2 != null && rightTarget3 != null) {
                                    linearSystem.addLowerThan(right3.mSolverVariable, rightTarget3, -rightMargin2, 1);
                                    linearSystem.addCentering(left3.mSolverVariable, leftTarget2, leftMargin3, first5.mHorizontalBiasPercent, rightTarget3, right3.mSolverVariable, rightMargin2, 4);
                                }
                            }
                        } else {
                            ConstraintWidget previous = null;
                            float totalWeights = 0.0f;
                            while (currentWidget != null) {
                                if (currentWidget.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    int margin5 = currentWidget.mLeft.getMargin();
                                    if (previous != null) {
                                        margin5 += previous.mRight.getMargin();
                                    }
                                    int strength2 = currentWidget.mLeft.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 2 : 3;
                                    linearSystem2.addGreaterThan(currentWidget.mLeft.mSolverVariable, currentWidget.mLeft.mTarget.mSolverVariable, margin5, strength2);
                                    int margin6 = currentWidget.mRight.getMargin();
                                    if (currentWidget.mRight.mTarget.mOwner.mLeft.mTarget != null && currentWidget.mRight.mTarget.mOwner.mLeft.mTarget.mOwner == currentWidget) {
                                        margin6 += currentWidget.mRight.mTarget.mOwner.mLeft.getMargin();
                                    }
                                    int strength3 = currentWidget.mRight.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 2 : 3;
                                    SolverVariable rightTarget4 = currentWidget.mRight.mTarget.mSolverVariable;
                                    if (currentWidget == this.mChainEnds[c7]) {
                                        rightTarget4 = this.mChainEnds[1].mRight.mTarget.mSolverVariable;
                                        strength3 = 3;
                                    }
                                    linearSystem2.addLowerThan(currentWidget.mRight.mSolverVariable, rightTarget4, -margin6, strength3);
                                    i3 = 1;
                                } else {
                                    totalWeights += currentWidget.mHorizontalWeight;
                                    int margin7 = 0;
                                    if (currentWidget.mRight.mTarget != null) {
                                        margin7 = currentWidget.mRight.getMargin();
                                        if (currentWidget != this.mChainEnds[3]) {
                                            margin7 += currentWidget.mRight.mTarget.mOwner.mLeft.getMargin();
                                        }
                                    }
                                    i3 = 1;
                                    linearSystem2.addGreaterThan(currentWidget.mRight.mSolverVariable, currentWidget.mLeft.mSolverVariable, 0, 1);
                                    linearSystem2.addLowerThan(currentWidget.mRight.mSolverVariable, currentWidget.mRight.mTarget.mSolverVariable, -margin7, 1);
                                }
                                previous = currentWidget;
                                currentWidget = currentWidget.mHorizontalNextWidget;
                                i7 = i3;
                                c7 = 3;
                            }
                            if (numMatchConstraints == i7) {
                                ConstraintWidget w = this.mMatchConstraintsChainedWidgets[0];
                                int leftMargin4 = w.mLeft.getMargin();
                                if (w.mLeft.mTarget != null) {
                                    leftMargin4 += w.mLeft.mTarget.getMargin();
                                }
                                int rightMargin3 = w.mRight.getMargin();
                                if (w.mRight.mTarget != null) {
                                    rightMargin3 += w.mRight.mTarget.getMargin();
                                }
                                SolverVariable rightTarget5 = widget.mRight.mTarget.mSolverVariable;
                                if (w == this.mChainEnds[3]) {
                                    i2 = 1;
                                    rightTarget5 = this.mChainEnds[1].mRight.mTarget.mSolverVariable;
                                } else {
                                    i2 = 1;
                                }
                                if (w.mMatchConstraintDefaultWidth == i2) {
                                    linearSystem2.addGreaterThan(widget.mLeft.mSolverVariable, widget.mLeft.mTarget.mSolverVariable, leftMargin4, 1);
                                    linearSystem2.addLowerThan(widget.mRight.mSolverVariable, rightTarget5, -rightMargin3, 1);
                                    linearSystem2.addEquality(widget.mRight.mSolverVariable, widget.mLeft.mSolverVariable, widget.getWidth(), 2);
                                } else {
                                    linearSystem2.addEquality(w.mLeft.mSolverVariable, w.mLeft.mTarget.mSolverVariable, leftMargin4, 1);
                                    linearSystem2.addEquality(w.mRight.mSolverVariable, rightTarget5, -rightMargin3, 1);
                                }
                            } else {
                                int j = 0;
                                while (j < numMatchConstraints - 1) {
                                    ConstraintWidget current = this.mMatchConstraintsChainedWidgets[j];
                                    ConstraintWidget nextWidget = this.mMatchConstraintsChainedWidgets[j + 1];
                                    SolverVariable left4 = current.mLeft.mSolverVariable;
                                    SolverVariable right4 = current.mRight.mSolverVariable;
                                    SolverVariable nextLeft = nextWidget.mLeft.mSolverVariable;
                                    SolverVariable nextRight = nextWidget.mRight.mSolverVariable;
                                    if (nextWidget == this.mChainEnds[3]) {
                                        nextRight = this.mChainEnds[1].mRight.mSolverVariable;
                                    }
                                    int margin8 = current.mLeft.getMargin();
                                    ConstraintWidget first6 = first3;
                                    if (current.mLeft.mTarget != null && current.mLeft.mTarget.mOwner.mRight.mTarget != null && current.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == current) {
                                        margin8 += current.mLeft.mTarget.mOwner.mRight.getMargin();
                                    }
                                    char c8 = c4;
                                    linearSystem2.addGreaterThan(left4, current.mLeft.mTarget.mSolverVariable, margin8, 2);
                                    int margin9 = current.mRight.getMargin();
                                    if (current.mRight.mTarget != null && current.mHorizontalNextWidget != null) {
                                        margin9 += current.mHorizontalNextWidget.mLeft.mTarget != null ? current.mHorizontalNextWidget.mLeft.getMargin() : 0;
                                    }
                                    linearSystem2.addLowerThan(right4, current.mRight.mTarget.mSolverVariable, -margin9, 2);
                                    int margin10 = j + 1;
                                    if (margin10 == numMatchConstraints - 1) {
                                        int margin11 = nextWidget.mLeft.getMargin();
                                        if (nextWidget.mLeft.mTarget != null && nextWidget.mLeft.mTarget.mOwner.mRight.mTarget != null && nextWidget.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == nextWidget) {
                                            margin11 += nextWidget.mLeft.mTarget.mOwner.mRight.getMargin();
                                        }
                                        linearSystem2.addGreaterThan(nextLeft, nextWidget.mLeft.mTarget.mSolverVariable, margin11, 2);
                                        ConstraintAnchor anchor = nextWidget.mRight;
                                        if (nextWidget == this.mChainEnds[3]) {
                                            anchor = this.mChainEnds[1].mRight;
                                        }
                                        int margin12 = anchor.getMargin();
                                        if (anchor.mTarget != null && anchor.mTarget.mOwner.mLeft.mTarget != null && anchor.mTarget.mOwner.mLeft.mTarget.mOwner == nextWidget) {
                                            margin12 += anchor.mTarget.mOwner.mLeft.getMargin();
                                        }
                                        int numMatchConstraints2 = -margin12;
                                        margin = 2;
                                        linearSystem2.addLowerThan(nextRight, anchor.mTarget.mSolverVariable, numMatchConstraints2, 2);
                                    } else {
                                        margin = 2;
                                    }
                                    if (widget.mMatchConstraintMaxWidth > 0) {
                                        linearSystem2.addLowerThan(right4, left4, widget.mMatchConstraintMaxWidth, margin);
                                    }
                                    ArrayRow row = system.createRow();
                                    row.createRowEqualDimension(current.mHorizontalWeight, totalWeights, nextWidget.mHorizontalWeight, left4, current.mLeft.getMargin(), right4, current.mRight.getMargin(), nextLeft, nextWidget.mLeft.getMargin(), nextRight, nextWidget.mRight.getMargin());
                                    linearSystem2.addConstraint(row);
                                    j++;
                                    first3 = first6;
                                    c4 = c8;
                                    numMatchConstraints = numMatchConstraints;
                                }
                            }
                            linearSystem = linearSystem2;
                            i = i5;
                            c = 0;
                        }
                    }
                }
                i = i5;
                c = c3;
                linearSystem = linearSystem2;
            }
            linearSystem2 = linearSystem;
            i4 = i + 1;
            c3 = c;
        }
    }

    /* JADX WARN: Code duplicated, block: B:212:0x054f  */
    /* JADX WARN: Code duplicated, block: B:215:0x055f  */
    /* JADX WARN: Code duplicated, block: B:216:0x0562  */
    private void applyVerticalChain(LinearSystem system) {
        LinearSystem linearSystem;
        int i;
        char c;
        char c2;
        ConstraintWidget next;
        ConstraintWidget first;
        ConstraintWidget widget;
        ConstraintWidget first2;
        ConstraintWidget constraintWidget;
        int margin;
        int i2;
        int i3;
        LinearSystem linearSystem2 = system;
        char c3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i4;
            int i6 = this.mVerticalChainsSize;
            if (i5 >= i6) {
                return;
            }
            ConstraintWidget first3 = this.mVerticalChainsArray[i5];
            int numMatchConstraints = countMatchConstraintsChainedWidgets(linearSystem2, this.mChainEnds, this.mVerticalChainsArray[i5], 1, this.flags);
            ConstraintWidget currentWidget = this.mChainEnds[2];
            if (currentWidget == null) {
                i = i5;
                c = c3;
                linearSystem = linearSystem2;
            } else {
                int i7 = 1;
                if (this.flags[1]) {
                    int y = first3.getDrawY();
                    while (currentWidget != null) {
                        linearSystem2.addEquality(currentWidget.mTop.mSolverVariable, y);
                        ConstraintWidget next2 = currentWidget.mVerticalNextWidget;
                        y += currentWidget.mTop.getMargin() + currentWidget.getHeight() + currentWidget.mBottom.getMargin();
                        currentWidget = next2;
                    }
                } else {
                    char c4 = first3.mVerticalChainStyle == 0 ? (char) 1 : c3;
                    char c5 = first3.mVerticalChainStyle == 2 ? (char) 1 : c3;
                    ConstraintWidget widget2 = first3;
                    char c6 = this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? (char) 1 : c3;
                    if ((this.mOptimizationLevel == 2 || this.mOptimizationLevel == 8) && this.flags[c3] && widget2.mVerticalChainFixedPosition && c5 == 0 && c6 == 0 && first3.mVerticalChainStyle == 0) {
                        Optimizer.applyDirectResolutionVerticalChain(this, linearSystem2, numMatchConstraints, widget2);
                    } else {
                        char c7 = 3;
                        if (numMatchConstraints == 0 || c5 != 0) {
                            boolean isLast = false;
                            ConstraintWidget lastWidget = null;
                            ConstraintWidget previousVisibleWidget = null;
                            ConstraintWidget previousVisibleWidget2 = null;
                            while (currentWidget != null) {
                                ConstraintWidget next3 = currentWidget.mVerticalNextWidget;
                                if (next3 == null) {
                                    lastWidget = this.mChainEnds[1];
                                    isLast = true;
                                }
                                if (c5 != 0) {
                                    ConstraintAnchor top = currentWidget.mTop;
                                    int margin2 = top.getMargin();
                                    if (previousVisibleWidget != null) {
                                        int previousMargin = previousVisibleWidget.mBottom.getMargin();
                                        margin2 += previousMargin;
                                    }
                                    int strength = currentWidget != currentWidget ? 3 : 1;
                                    SolverVariable source = null;
                                    SolverVariable target = null;
                                    if (top.mTarget != null) {
                                        source = top.mSolverVariable;
                                        target = top.mTarget.mSolverVariable;
                                    } else if (currentWidget.mBaseline.mTarget != null) {
                                        source = currentWidget.mBaseline.mSolverVariable;
                                        target = currentWidget.mBaseline.mTarget.mSolverVariable;
                                        margin2 -= top.getMargin();
                                    }
                                    if (source != null && target != null) {
                                        linearSystem2.addGreaterThan(source, target, margin2, strength);
                                    }
                                    if (currentWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                        ConstraintAnchor bottom = currentWidget.mBottom;
                                        if (currentWidget.mMatchConstraintDefaultHeight == 1) {
                                            int dimension = Math.max(currentWidget.mMatchConstraintMinHeight, currentWidget.getHeight());
                                            c2 = 3;
                                            linearSystem2.addEquality(bottom.mSolverVariable, top.mSolverVariable, dimension, 3);
                                        } else {
                                            c2 = 3;
                                            linearSystem2.addGreaterThan(top.mSolverVariable, top.mTarget.mSolverVariable, top.mMargin, 3);
                                            linearSystem2.addLowerThan(bottom.mSolverVariable, top.mSolverVariable, currentWidget.mMatchConstraintMinHeight, 3);
                                        }
                                    } else {
                                        c2 = 3;
                                    }
                                } else {
                                    c2 = 3;
                                    if (c4 != 0 || !isLast || previousVisibleWidget == null) {
                                        if (c4 == 0 && !isLast && previousVisibleWidget == null) {
                                            if (currentWidget.mTop.mTarget == null) {
                                                linearSystem2.addEquality(currentWidget.mTop.mSolverVariable, currentWidget.getDrawY());
                                            } else {
                                                int margin3 = currentWidget.mTop.getMargin();
                                                first2 = first3;
                                                linearSystem2.addEquality(currentWidget.mTop.mSolverVariable, first2.mTop.mTarget.mSolverVariable, margin3, 5);
                                            }
                                            next = next3;
                                            widget = widget2;
                                            first = first2;
                                        } else {
                                            ConstraintWidget first4 = first3;
                                            ConstraintAnchor top2 = currentWidget.mTop;
                                            ConstraintAnchor bottom2 = currentWidget.mBottom;
                                            int topMargin = top2.getMargin();
                                            int bottomMargin = bottom2.getMargin();
                                            linearSystem2.addGreaterThan(top2.mSolverVariable, top2.mTarget.mSolverVariable, topMargin, 1);
                                            ConstraintWidget widget3 = widget2;
                                            linearSystem2.addLowerThan(bottom2.mSolverVariable, bottom2.mTarget.mSolverVariable, -bottomMargin, 1);
                                            SolverVariable topTarget = top2.mTarget != null ? top2.mTarget.mSolverVariable : null;
                                            if (previousVisibleWidget == null) {
                                                topTarget = first4.mTop.mTarget != null ? first4.mTop.mTarget.mSolverVariable : null;
                                            }
                                            SolverVariable topTarget2 = topTarget;
                                            if (next3 == null) {
                                                next3 = lastWidget.mBottom.mTarget != null ? lastWidget.mBottom.mTarget.mOwner : null;
                                            }
                                            if (next3 != null) {
                                                SolverVariable bottomTarget = next3.mTop.mSolverVariable;
                                                if (isLast) {
                                                    bottomTarget = lastWidget.mBottom.mTarget != null ? lastWidget.mBottom.mTarget.mSolverVariable : null;
                                                }
                                                SolverVariable bottomTarget2 = bottomTarget;
                                                if (topTarget2 == null || bottomTarget2 == null) {
                                                    next = next3;
                                                    first = first4;
                                                    widget = widget3;
                                                } else {
                                                    widget = widget3;
                                                    first = first4;
                                                    next = next3;
                                                    linearSystem2.addCentering(top2.mSolverVariable, topTarget2, topMargin, 0.5f, bottomTarget2, bottom2.mSolverVariable, bottomMargin, 4);
                                                }
                                            } else {
                                                next = next3;
                                                first = first4;
                                                widget = widget3;
                                            }
                                        }
                                        previousVisibleWidget = currentWidget;
                                        if (isLast) {
                                            constraintWidget = null;
                                        } else {
                                            constraintWidget = next;
                                        }
                                        currentWidget = constraintWidget;
                                        linearSystem2 = linearSystem2;
                                        widget2 = widget;
                                        i5 = i5;
                                        first3 = first;
                                        previousVisibleWidget2 = next;
                                        this = this;
                                    } else if (currentWidget.mBottom.mTarget == null) {
                                        linearSystem2.addEquality(currentWidget.mBottom.mSolverVariable, currentWidget.getDrawBottom());
                                    } else {
                                        int margin4 = currentWidget.mBottom.getMargin();
                                        linearSystem2.addEquality(currentWidget.mBottom.mSolverVariable, lastWidget.mBottom.mTarget.mSolverVariable, -margin4, 5);
                                    }
                                }
                                first2 = first3;
                                next = next3;
                                widget = widget2;
                                first = first2;
                                previousVisibleWidget = currentWidget;
                                if (isLast) {
                                    constraintWidget = null;
                                } else {
                                    constraintWidget = next;
                                }
                                currentWidget = constraintWidget;
                                linearSystem2 = linearSystem2;
                                widget2 = widget;
                                i5 = i5;
                                first3 = first;
                                previousVisibleWidget2 = next;
                                this = this;
                            }
                            linearSystem = linearSystem2;
                            i = i5;
                            ConstraintWidget first5 = first3;
                            c = 0;
                            if (c5 != 0) {
                                ConstraintAnchor top3 = currentWidget.mTop;
                                ConstraintAnchor bottom3 = lastWidget.mBottom;
                                int topMargin2 = top3.getMargin();
                                int bottomMargin2 = bottom3.getMargin();
                                SolverVariable topTarget3 = first5.mTop.mTarget != null ? first5.mTop.mTarget.mSolverVariable : null;
                                SolverVariable bottomTarget3 = lastWidget.mBottom.mTarget != null ? lastWidget.mBottom.mTarget.mSolverVariable : null;
                                if (topTarget3 != null && bottomTarget3 != null) {
                                    linearSystem.addLowerThan(bottom3.mSolverVariable, bottomTarget3, -bottomMargin2, 1);
                                    linearSystem.addCentering(top3.mSolverVariable, topTarget3, topMargin2, first5.mVerticalBiasPercent, bottomTarget3, bottom3.mSolverVariable, bottomMargin2, 4);
                                }
                            }
                        } else {
                            ConstraintWidget previous = null;
                            float totalWeights = 0.0f;
                            while (currentWidget != null) {
                                if (currentWidget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    int margin5 = currentWidget.mTop.getMargin();
                                    if (previous != null) {
                                        margin5 += previous.mBottom.getMargin();
                                    }
                                    int strength2 = currentWidget.mTop.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 2 : 3;
                                    linearSystem2.addGreaterThan(currentWidget.mTop.mSolverVariable, currentWidget.mTop.mTarget.mSolverVariable, margin5, strength2);
                                    int margin6 = currentWidget.mBottom.getMargin();
                                    if (currentWidget.mBottom.mTarget.mOwner.mTop.mTarget != null && currentWidget.mBottom.mTarget.mOwner.mTop.mTarget.mOwner == currentWidget) {
                                        margin6 += currentWidget.mBottom.mTarget.mOwner.mTop.getMargin();
                                    }
                                    int strength3 = currentWidget.mBottom.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 2 : 3;
                                    SolverVariable bottomTarget4 = currentWidget.mBottom.mTarget.mSolverVariable;
                                    if (currentWidget == this.mChainEnds[c7]) {
                                        bottomTarget4 = this.mChainEnds[1].mBottom.mTarget.mSolverVariable;
                                        strength3 = 3;
                                    }
                                    linearSystem2.addLowerThan(currentWidget.mBottom.mSolverVariable, bottomTarget4, -margin6, strength3);
                                    i3 = 1;
                                } else {
                                    totalWeights += currentWidget.mVerticalWeight;
                                    int margin7 = 0;
                                    if (currentWidget.mBottom.mTarget != null) {
                                        margin7 = currentWidget.mBottom.getMargin();
                                        if (currentWidget != this.mChainEnds[3]) {
                                            margin7 += currentWidget.mBottom.mTarget.mOwner.mTop.getMargin();
                                        }
                                    }
                                    i3 = 1;
                                    linearSystem2.addGreaterThan(currentWidget.mBottom.mSolverVariable, currentWidget.mTop.mSolverVariable, 0, 1);
                                    linearSystem2.addLowerThan(currentWidget.mBottom.mSolverVariable, currentWidget.mBottom.mTarget.mSolverVariable, -margin7, 1);
                                }
                                previous = currentWidget;
                                currentWidget = currentWidget.mVerticalNextWidget;
                                i7 = i3;
                                c7 = 3;
                            }
                            if (numMatchConstraints == i7) {
                                ConstraintWidget w = this.mMatchConstraintsChainedWidgets[0];
                                int topMargin3 = w.mTop.getMargin();
                                if (w.mTop.mTarget != null) {
                                    topMargin3 += w.mTop.mTarget.getMargin();
                                }
                                int bottomMargin3 = w.mBottom.getMargin();
                                if (w.mBottom.mTarget != null) {
                                    bottomMargin3 += w.mBottom.mTarget.getMargin();
                                }
                                SolverVariable bottomTarget5 = widget2.mBottom.mTarget.mSolverVariable;
                                if (w == this.mChainEnds[3]) {
                                    i2 = 1;
                                    bottomTarget5 = this.mChainEnds[1].mBottom.mTarget.mSolverVariable;
                                } else {
                                    i2 = 1;
                                }
                                if (w.mMatchConstraintDefaultHeight == i2) {
                                    linearSystem2.addGreaterThan(widget2.mTop.mSolverVariable, widget2.mTop.mTarget.mSolverVariable, topMargin3, 1);
                                    linearSystem2.addLowerThan(widget2.mBottom.mSolverVariable, bottomTarget5, -bottomMargin3, 1);
                                    linearSystem2.addEquality(widget2.mBottom.mSolverVariable, widget2.mTop.mSolverVariable, widget2.getHeight(), 2);
                                } else {
                                    linearSystem2.addEquality(w.mTop.mSolverVariable, w.mTop.mTarget.mSolverVariable, topMargin3, 1);
                                    linearSystem2.addEquality(w.mBottom.mSolverVariable, bottomTarget5, -bottomMargin3, 1);
                                }
                            } else {
                                int j = 0;
                                while (j < numMatchConstraints - 1) {
                                    ConstraintWidget current = this.mMatchConstraintsChainedWidgets[j];
                                    ConstraintWidget nextWidget = this.mMatchConstraintsChainedWidgets[j + 1];
                                    SolverVariable top4 = current.mTop.mSolverVariable;
                                    SolverVariable bottom4 = current.mBottom.mSolverVariable;
                                    SolverVariable nextTop = nextWidget.mTop.mSolverVariable;
                                    SolverVariable nextBottom = nextWidget.mBottom.mSolverVariable;
                                    if (nextWidget == this.mChainEnds[3]) {
                                        nextBottom = this.mChainEnds[1].mBottom.mSolverVariable;
                                    }
                                    int margin8 = current.mTop.getMargin();
                                    ConstraintWidget first6 = first3;
                                    if (current.mTop.mTarget != null && current.mTop.mTarget.mOwner.mBottom.mTarget != null && current.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == current) {
                                        margin8 += current.mTop.mTarget.mOwner.mBottom.getMargin();
                                    }
                                    char c8 = c4;
                                    linearSystem2.addGreaterThan(top4, current.mTop.mTarget.mSolverVariable, margin8, 2);
                                    int margin9 = current.mBottom.getMargin();
                                    if (current.mBottom.mTarget != null && current.mVerticalNextWidget != null) {
                                        margin9 += current.mVerticalNextWidget.mTop.mTarget != null ? current.mVerticalNextWidget.mTop.getMargin() : 0;
                                    }
                                    linearSystem2.addLowerThan(bottom4, current.mBottom.mTarget.mSolverVariable, -margin9, 2);
                                    int margin10 = j + 1;
                                    if (margin10 == numMatchConstraints - 1) {
                                        int margin11 = nextWidget.mTop.getMargin();
                                        if (nextWidget.mTop.mTarget != null && nextWidget.mTop.mTarget.mOwner.mBottom.mTarget != null && nextWidget.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == nextWidget) {
                                            margin11 += nextWidget.mTop.mTarget.mOwner.mBottom.getMargin();
                                        }
                                        linearSystem2.addGreaterThan(nextTop, nextWidget.mTop.mTarget.mSolverVariable, margin11, 2);
                                        ConstraintAnchor anchor = nextWidget.mBottom;
                                        if (nextWidget == this.mChainEnds[3]) {
                                            anchor = this.mChainEnds[1].mBottom;
                                        }
                                        int margin12 = anchor.getMargin();
                                        if (anchor.mTarget != null && anchor.mTarget.mOwner.mTop.mTarget != null && anchor.mTarget.mOwner.mTop.mTarget.mOwner == nextWidget) {
                                            margin12 += anchor.mTarget.mOwner.mTop.getMargin();
                                        }
                                        int numMatchConstraints2 = -margin12;
                                        margin = 2;
                                        linearSystem2.addLowerThan(nextBottom, anchor.mTarget.mSolverVariable, numMatchConstraints2, 2);
                                    } else {
                                        margin = 2;
                                    }
                                    if (widget2.mMatchConstraintMaxHeight > 0) {
                                        linearSystem2.addLowerThan(bottom4, top4, widget2.mMatchConstraintMaxHeight, margin);
                                    }
                                    ArrayRow row = system.createRow();
                                    row.createRowEqualDimension(current.mVerticalWeight, totalWeights, nextWidget.mVerticalWeight, top4, current.mTop.getMargin(), bottom4, current.mBottom.getMargin(), nextTop, nextWidget.mTop.getMargin(), nextBottom, nextWidget.mBottom.getMargin());
                                    linearSystem2.addConstraint(row);
                                    j++;
                                    first3 = first6;
                                    c4 = c8;
                                    numMatchConstraints = numMatchConstraints;
                                }
                            }
                            linearSystem = linearSystem2;
                            i = i5;
                            c = 0;
                        }
                    }
                }
                i = i5;
                c = c3;
                linearSystem = linearSystem2;
            }
            linearSystem2 = linearSystem;
            i4 = i + 1;
            c3 = c;
        }
    }

    public void updateChildrenFromSolver(LinearSystem system, int group, boolean[] flags) {
        flags[2] = false;
        updateFromSolver(system, group);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            widget.updateFromSolver(system, group);
            if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.getWidth() < widget.getWrapWidth()) {
                flags[2] = true;
            }
            if (widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.getHeight() < widget.getWrapHeight()) {
                flags[2] = true;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:106:0x021e A[PHI: r0 r13
      0x021e: PHI (r0v16 'needsSolving' boolean) = 
      (r0v15 'needsSolving' boolean)
      (r0v19 'needsSolving' boolean)
      (r0v19 'needsSolving' boolean)
      (r0v19 'needsSolving' boolean)
     binds: [B:93:0x01e6, B:101:0x0206, B:102:0x0208, B:104:0x020e] A[DONT_GENERATE, DONT_INLINE]
      0x021e: PHI (r13v5 'wrap_override' boolean) = 
      (r13v4 'wrap_override' boolean)
      (r13v7 'wrap_override' boolean)
      (r13v7 'wrap_override' boolean)
      (r13v7 'wrap_override' boolean)
     binds: [B:93:0x01e6, B:101:0x0206, B:102:0x0208, B:104:0x020e] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.support.constraint.solver.widgets.WidgetContainer
    public void layout() {
        int prex = this.mX;
        int prey = this.mY;
        int prew = Math.max(0, getWidth());
        int preh = Math.max(0, getHeight());
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        if (this.mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            setX(this.mPaddingLeft);
            setY(this.mPaddingTop);
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            this.mX = 0;
            this.mY = 0;
        }
        boolean wrap_override = false;
        ConstraintWidget.DimensionBehaviour originalVerticalDimensionBehaviour = this.mVerticalDimensionBehaviour;
        ConstraintWidget.DimensionBehaviour originalHorizontalDimensionBehaviour = this.mHorizontalDimensionBehaviour;
        boolean z = true;
        if (this.mOptimizationLevel == 2 && (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
            findWrapSize(this.mChildren, this.flags);
            wrap_override = this.flags[0];
            if (prew > 0 && preh > 0 && (this.mWrapWidth > prew || this.mWrapHeight > preh)) {
                wrap_override = false;
            }
            if (wrap_override) {
                if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (prew <= 0 || prew >= this.mWrapWidth) {
                        setWidth(Math.max(this.mMinWidth, this.mWrapWidth));
                    } else {
                        this.mWidthMeasuredTooSmall = true;
                        setWidth(prew);
                    }
                }
                if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (preh <= 0 || preh >= this.mWrapHeight) {
                        setHeight(Math.max(this.mMinHeight, this.mWrapHeight));
                    } else {
                        this.mHeightMeasuredTooSmall = true;
                        setHeight(preh);
                    }
                }
            }
        }
        resetChains();
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ((WidgetContainer) widget).layout();
            }
        }
        boolean needsSolving = true;
        boolean wrap_override2 = wrap_override;
        int countSolve = 0;
        while (needsSolving) {
            int countSolve2 = countSolve + 1;
            try {
                this.mSystem.reset();
                needsSolving = addChildrenToSolver(this.mSystem, Integer.MAX_VALUE);
                if (needsSolving) {
                    this.mSystem.minimize();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (needsSolving) {
                updateChildrenFromSolver(this.mSystem, Integer.MAX_VALUE, this.flags);
            } else {
                updateFromSolver(this.mSystem, Integer.MAX_VALUE);
                for (int i2 = 0; i2 < count; i2++) {
                    ConstraintWidget widget2 = this.mChildren.get(i2);
                    if (widget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.getWidth() < widget2.getWrapWidth()) {
                        this.flags[2] = z;
                        break;
                    } else {
                        if (widget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.getHeight() < widget2.getWrapHeight()) {
                            this.flags[2] = z;
                            break;
                        }
                    }
                }
            }
            boolean needsSolving2 = false;
            if (countSolve2 < 8 && this.flags[2]) {
                int maxY = 0;
                int maxX = 0;
                for (int maxX2 = 0; maxX2 < count; maxX2++) {
                    ConstraintWidget widget3 = this.mChildren.get(maxX2);
                    maxX = Math.max(maxX, widget3.mX + widget3.getWidth());
                    maxY = Math.max(maxY, widget3.mY + widget3.getHeight());
                }
                int i3 = this.mMinWidth;
                int maxX3 = Math.max(i3, maxX);
                int maxY2 = Math.max(this.mMinHeight, maxY);
                if (originalHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && getWidth() < maxX3) {
                    setWidth(maxX3);
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    wrap_override2 = true;
                    needsSolving2 = true;
                }
                if (originalVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && getHeight() < maxY2) {
                    setHeight(maxY2);
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    wrap_override2 = true;
                    needsSolving2 = true;
                }
            }
            int maxX4 = this.mMinWidth;
            int width = Math.max(maxX4, getWidth());
            if (width > getWidth()) {
                setWidth(width);
                this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                wrap_override2 = true;
                needsSolving2 = true;
            }
            int height = Math.max(this.mMinHeight, getHeight());
            if (height > getHeight()) {
                setHeight(height);
                this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                wrap_override2 = true;
                needsSolving2 = true;
            }
            if (wrap_override2) {
                z = true;
            } else {
                if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && prew > 0 && getWidth() > prew) {
                    this.mWidthMeasuredTooSmall = true;
                    wrap_override2 = true;
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    setWidth(prew);
                    needsSolving2 = true;
                }
                if (this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || preh <= 0 || getHeight() <= preh) {
                    z = true;
                } else {
                    z = true;
                    this.mHeightMeasuredTooSmall = true;
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    setHeight(preh);
                    needsSolving2 = true;
                    wrap_override2 = true;
                }
            }
            needsSolving = needsSolving2;
            countSolve = countSolve2;
        }
        if (this.mParent != null) {
            int width2 = Math.max(this.mMinWidth, getWidth());
            int height2 = Math.max(this.mMinHeight, getHeight());
            this.mSnapshot.applyTo(this);
            setWidth(this.mPaddingLeft + width2 + this.mPaddingRight);
            setHeight(this.mPaddingTop + height2 + this.mPaddingBottom);
        } else {
            this.mX = prex;
            this.mY = prey;
        }
        if (wrap_override2) {
            this.mHorizontalDimensionBehaviour = originalHorizontalDimensionBehaviour;
            this.mVerticalDimensionBehaviour = originalVerticalDimensionBehaviour;
        }
        resetSolverVariables(this.mSystem.getCache());
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    public void findHorizontalWrapRecursive(ConstraintWidget widget, boolean[] flags) {
        boolean z = false;
        if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mDimensionRatio > 0.0f) {
            flags[0] = false;
            return;
        }
        if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintDefaultWidth == 2) {
            flags[0] = false;
            return;
        }
        int w = widget.getOptimizerWrapWidth();
        if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mDimensionRatio > 0.0f) {
            flags[0] = false;
            return;
        }
        int distToRight = w;
        int distToLeft = w;
        ConstraintWidget leftWidget = null;
        ConstraintWidget rightWidget = null;
        widget.mHorizontalWrapVisited = true;
        if (widget instanceof Guideline) {
            Guideline guideline = (Guideline) widget;
            if (guideline.getOrientation() == 1) {
                distToLeft = 0;
                distToRight = 0;
                if (guideline.getRelativeBegin() != -1) {
                    distToLeft = guideline.getRelativeBegin();
                } else if (guideline.getRelativeEnd() != -1) {
                    distToRight = guideline.getRelativeEnd();
                } else if (guideline.getRelativePercent() != -1.0f) {
                    flags[0] = false;
                    return;
                }
            }
        } else if (!widget.mRight.isConnected() && !widget.mLeft.isConnected()) {
            distToLeft += widget.getX();
        } else {
            if (widget.mRight.mTarget != null && widget.mLeft.mTarget != null && widget.mIsWidthWrapContent && widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                flags[0] = false;
                return;
            }
            if (widget.mRight.mTarget != null && widget.mLeft.mTarget != null && (widget.mRight.mTarget == widget.mLeft.mTarget || (widget.mRight.mTarget.mOwner == widget.mLeft.mTarget.mOwner && widget.mRight.mTarget.mOwner != widget.mParent))) {
                flags[0] = false;
                return;
            }
            if (widget.mRight.mTarget != null) {
                rightWidget = widget.mRight.mTarget.mOwner;
                distToRight += widget.mRight.getMargin();
                if (!rightWidget.isRoot() && !rightWidget.mHorizontalWrapVisited) {
                    findHorizontalWrapRecursive(rightWidget, flags);
                }
            }
            if (widget.mLeft.mTarget != null) {
                leftWidget = widget.mLeft.mTarget.mOwner;
                distToLeft += widget.mLeft.getMargin();
                if (!leftWidget.isRoot() && !leftWidget.mHorizontalWrapVisited) {
                    findHorizontalWrapRecursive(leftWidget, flags);
                }
            }
            if (widget.mRight.mTarget != null && !rightWidget.isRoot()) {
                if (widget.mRight.mTarget.mType == ConstraintAnchor.Type.RIGHT) {
                    distToRight += rightWidget.mDistToRight - rightWidget.getOptimizerWrapWidth();
                } else if (widget.mRight.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                    distToRight += rightWidget.mDistToRight;
                }
                widget.mRightHasCentered = rightWidget.mRightHasCentered || !(rightWidget.mLeft.mTarget == null || rightWidget.mRight.mTarget == null || rightWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                if (widget.mRightHasCentered && (rightWidget.mLeft.mTarget == null || rightWidget.mLeft.mTarget.mOwner != widget)) {
                    distToRight += distToRight - rightWidget.mDistToRight;
                }
            }
            if (widget.mLeft.mTarget != null && !leftWidget.isRoot()) {
                if (widget.mLeft.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                    distToLeft += leftWidget.mDistToLeft - leftWidget.getOptimizerWrapWidth();
                } else if (widget.mLeft.mTarget.getType() == ConstraintAnchor.Type.RIGHT) {
                    distToLeft += leftWidget.mDistToLeft;
                }
                if (leftWidget.mLeftHasCentered || (leftWidget.mLeft.mTarget != null && leftWidget.mRight.mTarget != null && leftWidget.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
                    z = true;
                }
                widget.mLeftHasCentered = z;
                if (widget.mLeftHasCentered && (leftWidget.mRight.mTarget == null || leftWidget.mRight.mTarget.mOwner != widget)) {
                    distToLeft += distToLeft - leftWidget.mDistToLeft;
                }
            }
        }
        if (widget.getVisibility() == 8) {
            distToLeft -= widget.mWidth;
            distToRight -= widget.mWidth;
        }
        widget.mDistToLeft = distToLeft;
        widget.mDistToRight = distToRight;
    }

    public void findVerticalWrapRecursive(ConstraintWidget widget, boolean[] flags) {
        boolean z = false;
        if (widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mDimensionRatio > 0.0f) {
            flags[0] = false;
            return;
        }
        if (widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintDefaultHeight == 2) {
            flags[0] = false;
            return;
        }
        int h = widget.getOptimizerWrapHeight();
        int distToTop = h;
        int distToBottom = h;
        ConstraintWidget topWidget = null;
        ConstraintWidget bottomWidget = null;
        widget.mVerticalWrapVisited = true;
        if (widget instanceof Guideline) {
            Guideline guideline = (Guideline) widget;
            if (guideline.getOrientation() == 0) {
                distToTop = 0;
                distToBottom = 0;
                if (guideline.getRelativeBegin() != -1) {
                    distToTop = guideline.getRelativeBegin();
                } else if (guideline.getRelativeEnd() != -1) {
                    distToBottom = guideline.getRelativeEnd();
                } else if (guideline.getRelativePercent() != -1.0f) {
                    flags[0] = false;
                    return;
                }
            }
        } else if (widget.mBaseline.mTarget == null && widget.mTop.mTarget == null && widget.mBottom.mTarget == null) {
            distToTop += widget.getY();
        } else {
            if (widget.mBottom.mTarget != null && widget.mTop.mTarget != null && widget.mIsHeightWrapContent && widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                flags[0] = false;
                return;
            }
            if (widget.mBottom.mTarget != null && widget.mTop.mTarget != null && (widget.mBottom.mTarget == widget.mTop.mTarget || (widget.mBottom.mTarget.mOwner == widget.mTop.mTarget.mOwner && widget.mBottom.mTarget.mOwner != widget.mParent))) {
                flags[0] = false;
                return;
            }
            if (widget.mBaseline.isConnected()) {
                ConstraintWidget baseLineWidget = widget.mBaseline.mTarget.getOwner();
                if (!baseLineWidget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(baseLineWidget, flags);
                }
                int distToTop2 = Math.max((baseLineWidget.mDistToTop - baseLineWidget.mHeight) + h, h);
                int distToBottom2 = Math.max((baseLineWidget.mDistToBottom - baseLineWidget.mHeight) + h, h);
                if (widget.getVisibility() == 8) {
                    distToTop2 -= widget.mHeight;
                    distToBottom2 -= widget.mHeight;
                }
                widget.mDistToTop = distToTop2;
                widget.mDistToBottom = distToBottom2;
                return;
            }
            if (widget.mTop.isConnected()) {
                topWidget = widget.mTop.mTarget.getOwner();
                distToTop += widget.mTop.getMargin();
                if (!topWidget.isRoot() && !topWidget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(topWidget, flags);
                }
            }
            if (widget.mBottom.isConnected()) {
                bottomWidget = widget.mBottom.mTarget.getOwner();
                distToBottom += widget.mBottom.getMargin();
                if (!bottomWidget.isRoot() && !bottomWidget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(bottomWidget, flags);
                }
            }
            if (widget.mTop.mTarget != null && !topWidget.isRoot()) {
                if (widget.mTop.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                    distToTop += topWidget.mDistToTop - topWidget.getOptimizerWrapHeight();
                } else if (widget.mTop.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                    distToTop += topWidget.mDistToTop;
                }
                widget.mTopHasCentered = topWidget.mTopHasCentered || !(topWidget.mTop.mTarget == null || topWidget.mTop.mTarget.mOwner == widget || topWidget.mBottom.mTarget == null || topWidget.mBottom.mTarget.mOwner == widget || topWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                if (widget.mTopHasCentered && (topWidget.mBottom.mTarget == null || topWidget.mBottom.mTarget.mOwner != widget)) {
                    distToTop += distToTop - topWidget.mDistToTop;
                }
            }
            if (widget.mBottom.mTarget != null && !bottomWidget.isRoot()) {
                if (widget.mBottom.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                    distToBottom += bottomWidget.mDistToBottom - bottomWidget.getOptimizerWrapHeight();
                } else if (widget.mBottom.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                    distToBottom += bottomWidget.mDistToBottom;
                }
                if (bottomWidget.mBottomHasCentered || (bottomWidget.mTop.mTarget != null && bottomWidget.mTop.mTarget.mOwner != widget && bottomWidget.mBottom.mTarget != null && bottomWidget.mBottom.mTarget.mOwner != widget && bottomWidget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
                    z = true;
                }
                widget.mBottomHasCentered = z;
                if (widget.mBottomHasCentered && (bottomWidget.mTop.mTarget == null || bottomWidget.mTop.mTarget.mOwner != widget)) {
                    distToBottom += distToBottom - bottomWidget.mDistToBottom;
                }
            }
        }
        if (widget.getVisibility() == 8) {
            distToTop -= widget.mHeight;
            distToBottom -= widget.mHeight;
        }
        widget.mDistToTop = distToTop;
        widget.mDistToBottom = distToBottom;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r11v29 */
    /* JADX WARN: Type inference failed for: r13v10, types: [int] */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [int] */
    /* JADX WARN: Type inference failed for: r13v9 */
    public void findWrapSize(ArrayList<ConstraintWidget> arrayList, boolean[] zArr) {
        int iMax = 0;
        int iMax2 = 0;
        int iMax3 = 0;
        int size = arrayList.size();
        ?? r11 = 0;
        zArr[0] = true;
        int iMax4 = 0;
        int iMax5 = 0;
        int iMax6 = 0;
        int i = 0;
        while (i < size) {
            try {
                ConstraintWidget constraintWidget = arrayList.get(i);
                if (!constraintWidget.isRoot()) {
                    if (!constraintWidget.mHorizontalWrapVisited) {
                        findHorizontalWrapRecursive(constraintWidget, zArr);
                    }
                    if (!zArr[r11]) {
                        for (?? r13 = r11; r13 < size; r13++) {
                            ConstraintWidget constraintWidget2 = arrayList.get(r13);
                            constraintWidget2.mHorizontalWrapVisited = r11;
                            constraintWidget2.mVerticalWrapVisited = r11;
                            constraintWidget2.mLeftHasCentered = r11;
                            constraintWidget2.mRightHasCentered = r11;
                            constraintWidget2.mTopHasCentered = r11;
                            constraintWidget2.mBottomHasCentered = r11;
                        }
                        return;
                    }
                    if (!constraintWidget.mVerticalWrapVisited) {
                        findVerticalWrapRecursive(constraintWidget, zArr);
                    }
                    if (!zArr[r11]) {
                        for (?? r14 = r11; r14 < size; r14++) {
                            ConstraintWidget constraintWidget3 = arrayList.get(r14);
                            constraintWidget3.mHorizontalWrapVisited = r11;
                            constraintWidget3.mVerticalWrapVisited = r11;
                            constraintWidget3.mLeftHasCentered = r11;
                            constraintWidget3.mRightHasCentered = r11;
                            constraintWidget3.mTopHasCentered = r11;
                            constraintWidget3.mBottomHasCentered = r11;
                        }
                        return;
                    }
                    int width = (constraintWidget.mDistToLeft + constraintWidget.mDistToRight) - constraintWidget.getWidth();
                    int height = (constraintWidget.mDistToTop + constraintWidget.mDistToBottom) - constraintWidget.getHeight();
                    if (constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        width = constraintWidget.getWidth() + constraintWidget.mLeft.mMargin + constraintWidget.mRight.mMargin;
                    }
                    if (constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        height = constraintWidget.getHeight() + constraintWidget.mTop.mMargin + constraintWidget.mBottom.mMargin;
                    }
                    if (constraintWidget.getVisibility() == 8) {
                        width = 0;
                        height = 0;
                    }
                    iMax = Math.max(iMax, constraintWidget.mDistToLeft);
                    iMax2 = Math.max(iMax2, constraintWidget.mDistToRight);
                    iMax5 = Math.max(iMax5, constraintWidget.mDistToBottom);
                    iMax6 = Math.max(iMax6, constraintWidget.mDistToTop);
                    iMax3 = Math.max(iMax3, width);
                    iMax4 = Math.max(iMax4, height);
                }
                i++;
                r11 = 0;
            } catch (Throwable th) {
                for (int i2 = 0; i2 < size; i2++) {
                    ConstraintWidget constraintWidget4 = arrayList.get(i2);
                    constraintWidget4.mHorizontalWrapVisited = false;
                    constraintWidget4.mVerticalWrapVisited = false;
                    constraintWidget4.mLeftHasCentered = false;
                    constraintWidget4.mRightHasCentered = false;
                    constraintWidget4.mTopHasCentered = false;
                    constraintWidget4.mBottomHasCentered = false;
                }
                throw th;
            }
        }
        this.mWrapWidth = Math.max(this.mMinWidth, Math.max(Math.max(iMax, iMax2), iMax3));
        this.mWrapHeight = Math.max(this.mMinHeight, Math.max(Math.max(iMax6, iMax5), iMax4));
        for (int i3 = 0; i3 < size; i3++) {
            ConstraintWidget constraintWidget5 = arrayList.get(i3);
            constraintWidget5.mHorizontalWrapVisited = false;
            constraintWidget5.mVerticalWrapVisited = false;
            constraintWidget5.mLeftHasCentered = false;
            constraintWidget5.mRightHasCentered = false;
            constraintWidget5.mTopHasCentered = false;
            constraintWidget5.mBottomHasCentered = false;
        }
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    void addChain(ConstraintWidget constraintWidget, int type) {
        ConstraintWidget widget = constraintWidget;
        if (type == 0) {
            while (widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget == widget.mLeft && widget.mLeft.mTarget.mOwner != widget) {
                widget = widget.mLeft.mTarget.mOwner;
            }
            addHorizontalChain(widget);
            return;
        }
        if (type == 1) {
            while (widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget == widget.mTop && widget.mTop.mTarget.mOwner != widget) {
                widget = widget.mTop.mTarget.mOwner;
            }
            addVerticalChain(widget);
        }
    }

    private void addHorizontalChain(ConstraintWidget widget) {
        for (int i = 0; i < this.mHorizontalChainsSize; i++) {
            if (this.mHorizontalChainsArray[i] == widget) {
                return;
            }
        }
        int i2 = this.mHorizontalChainsSize;
        if (i2 + 1 >= this.mHorizontalChainsArray.length) {
            this.mHorizontalChainsArray = (ConstraintWidget[]) Arrays.copyOf(this.mHorizontalChainsArray, this.mHorizontalChainsArray.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = widget;
        this.mHorizontalChainsSize++;
    }

    private void addVerticalChain(ConstraintWidget widget) {
        for (int i = 0; i < this.mVerticalChainsSize; i++) {
            if (this.mVerticalChainsArray[i] == widget) {
                return;
            }
        }
        int i2 = this.mVerticalChainsSize;
        if (i2 + 1 >= this.mVerticalChainsArray.length) {
            this.mVerticalChainsArray = (ConstraintWidget[]) Arrays.copyOf(this.mVerticalChainsArray, this.mVerticalChainsArray.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = widget;
        this.mVerticalChainsSize++;
    }

    private int countMatchConstraintsChainedWidgets(LinearSystem system, ConstraintWidget[] chainEnds, ConstraintWidget widget, int direction, boolean[] flags) {
        char c;
        ConstraintWidget last;
        char c2;
        ConstraintWidget widget2 = widget;
        flags[0] = true;
        flags[1] = false;
        ConstraintWidget constraintWidget = null;
        chainEnds[0] = null;
        chainEnds[2] = null;
        chainEnds[1] = null;
        chainEnds[3] = null;
        int i = 5;
        int i2 = 8;
        if (direction == 0) {
            boolean fixedPosition = true;
            if (widget2.mLeft.mTarget != null && widget2.mLeft.mTarget.mOwner != this) {
                fixedPosition = false;
            }
            widget2.mHorizontalNextWidget = null;
            ConstraintWidget firstVisible = null;
            if (widget.getVisibility() != 8) {
                firstVisible = widget2;
            }
            int count = 0;
            ConstraintWidget firstVisible2 = firstVisible;
            ConstraintWidget last2 = null;
            while (widget2.mRight.mTarget != null) {
                widget2.mHorizontalNextWidget = constraintWidget;
                if (widget2.getVisibility() != 8) {
                    if (firstVisible2 == null) {
                        firstVisible2 = widget2;
                    }
                    if (firstVisible != null && firstVisible != widget2) {
                        firstVisible.mHorizontalNextWidget = widget2;
                    }
                    ConstraintWidget lastVisible = widget2;
                    firstVisible = lastVisible;
                } else {
                    system.addEquality(widget2.mLeft.mSolverVariable, widget2.mLeft.mTarget.mSolverVariable, 0, 5);
                    system.addEquality(widget2.mRight.mSolverVariable, widget2.mLeft.mSolverVariable, 0, 5);
                }
                if (widget2.getVisibility() != 8 && widget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (widget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        flags[0] = false;
                    }
                    if (widget2.mDimensionRatio <= 0.0f) {
                        flags[0] = false;
                        if (count + 1 >= this.mMatchConstraintsChainedWidgets.length) {
                            this.mMatchConstraintsChainedWidgets = (ConstraintWidget[]) Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                        }
                        this.mMatchConstraintsChainedWidgets[count] = widget2;
                        count++;
                    }
                }
                if (widget2.mRight.mTarget.mOwner.mLeft.mTarget == null || widget2.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != widget2 || widget2.mRight.mTarget.mOwner == widget2) {
                    break;
                }
                widget2 = widget2.mRight.mTarget.mOwner;
                last2 = widget2;
                constraintWidget = null;
            }
            if (widget2.mRight.mTarget != null && widget2.mRight.mTarget.mOwner != this) {
                fixedPosition = false;
            }
            if (widget2.mLeft.mTarget == null) {
                last = last2;
            } else {
                last = last2;
                if (last.mRight.mTarget != null) {
                    c2 = 1;
                }
                widget2.mHorizontalChainFixedPosition = fixedPosition;
                last.mHorizontalNextWidget = null;
                chainEnds[0] = widget2;
                chainEnds[2] = firstVisible2;
                chainEnds[c2] = last;
                chainEnds[3] = firstVisible;
                return count;
            }
            c2 = 1;
            flags[1] = true;
            widget2.mHorizontalChainFixedPosition = fixedPosition;
            last.mHorizontalNextWidget = null;
            chainEnds[0] = widget2;
            chainEnds[2] = firstVisible2;
            chainEnds[c2] = last;
            chainEnds[3] = firstVisible;
            return count;
        }
        boolean fixedPosition2 = true;
        if (widget2.mTop.mTarget != null && widget2.mTop.mTarget.mOwner != this) {
            fixedPosition2 = false;
        }
        widget2.mVerticalNextWidget = null;
        ConstraintWidget firstVisible3 = null;
        if (widget.getVisibility() != 8) {
            firstVisible3 = widget2;
        }
        ConstraintWidget last3 = null;
        int count2 = 0;
        ConstraintWidget firstVisible4 = firstVisible3;
        while (widget2.mBottom.mTarget != null) {
            widget2.mVerticalNextWidget = null;
            if (widget2.getVisibility() != i2) {
                if (firstVisible4 == null) {
                    firstVisible4 = widget2;
                }
                if (firstVisible3 != null && firstVisible3 != widget2) {
                    firstVisible3.mVerticalNextWidget = widget2;
                }
                firstVisible3 = widget2;
            } else {
                system.addEquality(widget2.mTop.mSolverVariable, widget2.mTop.mTarget.mSolverVariable, 0, i);
                system.addEquality(widget2.mBottom.mSolverVariable, widget2.mTop.mSolverVariable, 0, i);
            }
            i2 = 8;
            if (widget2.getVisibility() != 8 && widget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (widget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    flags[0] = false;
                }
                if (widget2.mDimensionRatio <= 0.0f) {
                    flags[0] = false;
                    if (count2 + 1 >= this.mMatchConstraintsChainedWidgets.length) {
                        this.mMatchConstraintsChainedWidgets = (ConstraintWidget[]) Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                    }
                    this.mMatchConstraintsChainedWidgets[count2] = widget2;
                    count2++;
                }
            }
            if (widget2.mBottom.mTarget.mOwner.mTop.mTarget == null || widget2.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != widget2 || widget2.mBottom.mTarget.mOwner == widget2) {
                break;
            }
            widget2 = widget2.mBottom.mTarget.mOwner;
            last3 = widget2;
            i = 5;
        }
        if (widget2.mBottom.mTarget != null && widget2.mBottom.mTarget.mOwner != this) {
            fixedPosition2 = false;
        }
        if (widget2.mTop.mTarget == null || last3.mBottom.mTarget == null) {
            c = 1;
            flags[1] = true;
        } else {
            c = 1;
        }
        widget2.mVerticalChainFixedPosition = fixedPosition2;
        last3.mVerticalNextWidget = null;
        chainEnds[0] = widget2;
        chainEnds[2] = firstVisible4;
        chainEnds[c] = last3;
        chainEnds[3] = firstVisible3;
        return count2;
    }
}
