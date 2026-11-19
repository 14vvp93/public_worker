package com.android.systemui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.RotationUtils;

/* JADX INFO: loaded from: classes.dex */
public class HardwareUiLayout extends FrameLayout implements TunerService.Tunable {
    private Animator mAnimator;
    private HardwareBgDrawable mBackground;
    private View mChild;
    private boolean mCollapse;
    private View mDivision;
    private boolean mEdgeBleed;
    private int mEndPoint;
    private boolean mHasOutsideTouch;
    private final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsListener;
    private int mOldHeight;
    private boolean mRotatedBackground;
    private int mRotation;
    private boolean mRoundedDivider;
    private boolean mSwapOrientation;
    private final int[] mTmp2;

    public HardwareUiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTmp2 = new int[2];
        this.mRotation = 0;
        this.mSwapOrientation = true;
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.-$$Lambda$HardwareUiLayout$Wopid983i_OFN_0DzaqL8EnwZHc
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                HardwareUiLayout.lambda$new$5(this.f$0, internalInsetsInfo);
            }
        };
        updateSettings();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateSettings();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "sysui_hwui_edge_bleed", "sysui_hwui_rounded_divider");
        getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsListener);
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        updateSettings();
    }

    private void updateSettings() {
        this.mEdgeBleed = Settings.Secure.getInt(getContext().getContentResolver(), "sysui_hwui_edge_bleed", 0) != 0;
        this.mRoundedDivider = Settings.Secure.getInt(getContext().getContentResolver(), "sysui_hwui_rounded_divider", 0) != 0;
        updateEdgeMargin(this.mEdgeBleed ? 0 : getEdgePadding());
        this.mBackground = new HardwareBgDrawable(this.mRoundedDivider, true ^ this.mEdgeBleed, getContext());
        if (this.mChild != null) {
            this.mChild.setBackground(this.mBackground);
            requestLayout();
        }
    }

    private void updateEdgeMargin(int edge) {
        if (this.mChild != null) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mChild.getLayoutParams();
            if (this.mRotation == 1) {
                params.topMargin = edge;
            } else if (this.mRotation == 2) {
                params.bottomMargin = edge;
            } else {
                params.rightMargin = edge;
            }
            this.mChild.setLayoutParams(params);
        }
    }

    private int getEdgePadding() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.edge_margin);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mChild == null) {
            if (getChildCount() != 0) {
                this.mChild = getChildAt(0);
                this.mChild.setBackground(this.mBackground);
                updateEdgeMargin(this.mEdgeBleed ? 0 : getEdgePadding());
                this.mOldHeight = this.mChild.getMeasuredHeight();
                this.mChild.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.-$$Lambda$HardwareUiLayout$iXisPfYv_BRDXTMSPDe1D5AA0Wo
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        this.f$0.updatePosition();
                    }
                });
                updateRotation();
            } else {
                return;
            }
        }
        int newHeight = this.mChild.getMeasuredHeight();
        if (newHeight != this.mOldHeight) {
            animateChild(this.mOldHeight, newHeight);
        }
        post(new Runnable() { // from class: com.android.systemui.-$$Lambda$HardwareUiLayout$zQ_qVVlFo_33izIMpEk2X8p8Su8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updatePosition();
            }
        });
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateRotation();
    }

    private void updateRotation() {
        int rotation = RotationUtils.getRotation(getContext());
        if (rotation != this.mRotation) {
            rotate(this.mRotation, rotation);
            this.mRotation = rotation;
        }
    }

    private void rotate(int from, int to) {
        if (from != 0 && to != 0) {
            rotate(from, 0);
            rotate(0, to);
            return;
        }
        if (from == 1 || to == 2) {
            rotateRight();
        } else {
            rotateLeft();
        }
        if (to != 0) {
            if (this.mChild instanceof LinearLayout) {
                this.mRotatedBackground = true;
                this.mBackground.setRotatedBackground(true);
                LinearLayout linearLayout = (LinearLayout) this.mChild;
                if (this.mSwapOrientation) {
                    linearLayout.setOrientation(0);
                }
                swapDimens(this.mChild);
                return;
            }
            return;
        }
        if (this.mChild instanceof LinearLayout) {
            this.mRotatedBackground = false;
            this.mBackground.setRotatedBackground(false);
            LinearLayout linearLayout2 = (LinearLayout) this.mChild;
            if (this.mSwapOrientation) {
                linearLayout2.setOrientation(1);
            }
            swapDimens(this.mChild);
        }
    }

    private void rotateRight() {
        rotateRight(this);
        rotateRight(this.mChild);
        swapDimens(this);
        FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) this.mChild.getLayoutParams();
        p.gravity = rotateGravityRight(p.gravity);
        this.mChild.setLayoutParams(p);
    }

    private void swapDimens(View v) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        int h = params.width;
        params.width = params.height;
        params.height = h;
        v.setLayoutParams(params);
    }

    private int rotateGravityRight(int gravity) {
        int retGravity;
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
        int verticalGravity = gravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
        int i = absoluteGravity & 7;
        if (i == 1) {
            retGravity = 0 | 16;
        } else if (i == 5) {
            retGravity = 0 | 80;
        } else {
            retGravity = 0 | 48;
        }
        if (verticalGravity == 16) {
            return retGravity | 1;
        }
        if (verticalGravity == 80) {
            return retGravity | 3;
        }
        return retGravity | 5;
    }

    private void rotateLeft() {
        rotateLeft(this);
        rotateLeft(this.mChild);
        swapDimens(this);
        FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) this.mChild.getLayoutParams();
        p.gravity = rotateGravityLeft(p.gravity);
        this.mChild.setLayoutParams(p);
    }

    private int rotateGravityLeft(int gravity) {
        int retGravity;
        if (gravity == -1) {
            gravity = 8388659;
        }
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
        int verticalGravity = gravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay;
        int i = absoluteGravity & 7;
        if (i == 1) {
            retGravity = 0 | 16;
        } else if (i == 5) {
            retGravity = 0 | 48;
        } else {
            retGravity = 0 | 80;
        }
        if (verticalGravity == 16) {
            return retGravity | 1;
        }
        if (verticalGravity == 80) {
            return retGravity | 5;
        }
        return retGravity | 3;
    }

    private void rotateLeft(View v) {
        v.setPadding(v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom(), v.getPaddingLeft());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        params.setMargins(params.topMargin, params.rightMargin, params.bottomMargin, params.leftMargin);
        v.setLayoutParams(params);
    }

    private void rotateRight(View v) {
        v.setPadding(v.getPaddingBottom(), v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        params.setMargins(params.bottomMargin, params.leftMargin, params.topMargin, params.rightMargin);
        v.setLayoutParams(params);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        post(new Runnable() { // from class: com.android.systemui.-$$Lambda$HardwareUiLayout$cC2d-RGmOoAkRlNqsTu1n43qy3A
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updatePosition();
            }
        });
    }

    private void animateChild(int oldHeight, int newHeight) {
    }

    public void setDivisionView(View v) {
        this.mDivision = v;
        if (this.mDivision != null) {
            this.mDivision.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.-$$Lambda$HardwareUiLayout$2j9eMBfPQJX3xgwLvM_hUGNd8jc
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    this.f$0.updatePosition();
                }
            });
        }
        updatePosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePosition() {
        if (this.mChild == null) {
            return;
        }
        if (this.mDivision != null && this.mDivision.getVisibility() == 0) {
            int i = !this.mRotatedBackground ? 1 : 0;
            this.mDivision.getLocationOnScreen(this.mTmp2);
            int translationX = (int) (this.mTmp2[i] + (this.mRotatedBackground ? this.mDivision.getTranslationX() : this.mDivision.getTranslationY()));
            this.mChild.getLocationOnScreen(this.mTmp2);
            setCutPoint(translationX - this.mTmp2[i]);
            return;
        }
        setCutPoint(this.mChild.getMeasuredHeight());
    }

    private void setCutPoint(int point) {
        int curPoint = this.mBackground.getCutPoint();
        if (curPoint == point) {
            return;
        }
        if (getAlpha() == 0.0f || curPoint == 0) {
            this.mBackground.setCutPoint(point);
            return;
        }
        if (this.mAnimator != null) {
            if (this.mEndPoint == point) {
                return;
            } else {
                this.mAnimator.cancel();
            }
        }
        this.mEndPoint = point;
        this.mAnimator = ObjectAnimator.ofInt(this.mBackground, "cutPoint", curPoint, point);
        if (this.mCollapse) {
            this.mAnimator.setStartDelay(300L);
            this.mCollapse = false;
        }
        this.mAnimator.start();
    }

    @Override // android.view.View
    public ViewOutlineProvider getOutlineProvider() {
        return super.getOutlineProvider();
    }

    public void setOutsideTouchListener(View.OnClickListener onClickListener) {
        this.mHasOutsideTouch = true;
        requestLayout();
        setOnClickListener(onClickListener);
        setClickable(true);
        setFocusable(true);
    }

    public static HardwareUiLayout get(View v) {
        if (v instanceof HardwareUiLayout) {
            return (HardwareUiLayout) v;
        }
        if (v.getParent() instanceof View) {
            return get((View) v.getParent());
        }
        return null;
    }

    public static /* synthetic */ void lambda$new$5(HardwareUiLayout hardwareUiLayout, ViewTreeObserver.InternalInsetsInfo inoutInfo) {
        if (hardwareUiLayout.mHasOutsideTouch || hardwareUiLayout.mChild == null) {
            inoutInfo.setTouchableInsets(0);
        } else {
            inoutInfo.setTouchableInsets(1);
            inoutInfo.contentInsets.set(hardwareUiLayout.mChild.getLeft(), hardwareUiLayout.mChild.getTop(), 0, hardwareUiLayout.getBottom() - hardwareUiLayout.mChild.getBottom());
        }
    }
}
