package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;

/* JADX INFO: loaded from: classes.dex */
public class NotificationGuts extends FrameLayout {
    private int mActualHeight;
    private Drawable mBackground;
    private int mClipBottomAmount;
    private int mClipTopAmount;
    private OnGutsClosedListener mClosedListener;
    private boolean mExposed;
    private Runnable mFalsingCheck;
    private GutsContent mGutsContent;
    private Handler mHandler;
    private OnHeightChangedListener mHeightListener;
    private boolean mNeedsFalsingProtection;

    public interface OnGutsClosedListener {
        void onGutsClosed(NotificationGuts notificationGuts);
    }

    public interface OnHeightChangedListener {
        void onHeightChanged(NotificationGuts notificationGuts);
    }

    public interface GutsContent {
        int getActualHeight();

        View getContentView();

        boolean handleCloseControls(boolean z, boolean z2);

        void setGutsParent(NotificationGuts notificationGuts);

        boolean shouldBeSaved();

        boolean willBeRemoved();

        default boolean isLeavebehind() {
            return false;
        }
    }

    public NotificationGuts(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        this.mHandler = new Handler();
        this.mFalsingCheck = new Runnable() { // from class: com.android.systemui.statusbar.NotificationGuts.1
            @Override // java.lang.Runnable
            public void run() {
                if (NotificationGuts.this.mNeedsFalsingProtection && NotificationGuts.this.mExposed) {
                    NotificationGuts.this.closeControls(-1, -1, false, false);
                }
            }
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Theme, 0, 0);
        ta.recycle();
    }

    public NotificationGuts(Context context) {
        this(context, null);
    }

    public void setGutsContent(GutsContent content) {
        this.mGutsContent = content;
        removeAllViews();
        addView(this.mGutsContent.getContentView());
    }

    public GutsContent getGutsContent() {
        return this.mGutsContent;
    }

    public void resetFalsingCheck() {
        this.mHandler.removeCallbacks(this.mFalsingCheck);
        if (this.mNeedsFalsingProtection && this.mExposed) {
            this.mHandler.postDelayed(this.mFalsingCheck, 8000L);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        draw(canvas, this.mBackground);
    }

    private void draw(Canvas canvas, Drawable drawable) {
        int top = this.mClipTopAmount;
        int bottom = this.mActualHeight - this.mClipBottomAmount;
        if (drawable != null && top < bottom) {
            drawable.setBounds(0, top, getWidth(), bottom);
            drawable.draw(canvas);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBackground = this.mContext.getDrawable(com.android.systemui.R.drawable.notification_guts_bg);
        if (this.mBackground != null) {
            this.mBackground.setCallback(this);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mBackground;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        drawableStateChanged(this.mBackground);
    }

    private void drawableStateChanged(Drawable d) {
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float x, float y) {
        if (this.mBackground != null) {
            this.mBackground.setHotspot(x, y);
        }
    }

    public void openControls(boolean shouldDoCircularReveal, int x, int y, boolean needsFalsingProtection, Runnable onAnimationEnd) {
        animateOpen(shouldDoCircularReveal, x, y, onAnimationEnd);
        setExposed(true, needsFalsingProtection);
    }

    public void closeControls(boolean leavebehinds, boolean controls, int x, int y, boolean force) {
        if (this.mGutsContent != null) {
            if ((this.mGutsContent.isLeavebehind() && leavebehinds) || (!this.mGutsContent.isLeavebehind() && controls)) {
                closeControls(x, y, this.mGutsContent.shouldBeSaved(), force);
            }
        }
    }

    public void closeControls(int x, int y, boolean save, boolean force) {
        boolean wasBlockingHelperDismissed = ((NotificationBlockingHelperManager) Dependency.get(NotificationBlockingHelperManager.class)).dismissCurrentBlockingHelper();
        if (getWindowToken() == null) {
            if (this.mClosedListener != null) {
                this.mClosedListener.onGutsClosed(this);
            }
        } else if (this.mGutsContent == null || !this.mGutsContent.handleCloseControls(save, force) || wasBlockingHelperDismissed) {
            animateClose(x, y, !wasBlockingHelperDismissed);
            setExposed(false, this.mNeedsFalsingProtection);
            if (this.mClosedListener != null) {
                this.mClosedListener.onGutsClosed(this);
            }
        }
    }

    private void animateOpen(boolean shouldDoCircularReveal, int x, int y, Runnable onAnimationEnd) {
        if (isAttachedToWindow()) {
            if (shouldDoCircularReveal) {
                double horz = Math.max(getWidth() - x, x);
                double vert = Math.max(getHeight() - y, y);
                float r = (float) Math.hypot(horz, vert);
                Animator a = ViewAnimationUtils.createCircularReveal(this, x, y, 0.0f, r);
                a.setDuration(360L);
                a.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                a.addListener(new AnimateOpenListener(onAnimationEnd));
                a.start();
                return;
            }
            setAlpha(0.0f);
            animate().alpha(1.0f).setDuration(240L).setInterpolator(Interpolators.ALPHA_IN).setListener(new AnimateOpenListener(onAnimationEnd)).start();
            return;
        }
        Log.w("NotificationGuts", "Failed to animate guts open");
    }

    @VisibleForTesting
    void animateClose(int x, int y, boolean shouldDoCircularReveal) {
        if (isAttachedToWindow()) {
            if (shouldDoCircularReveal) {
                if (x == -1 || y == -1) {
                    x = (getLeft() + getRight()) / 2;
                    y = getTop() + (getHeight() / 2);
                }
                double horz = Math.max(getWidth() - x, x);
                double vert = Math.max(getHeight() - y, y);
                float r = (float) Math.hypot(horz, vert);
                Animator a = ViewAnimationUtils.createCircularReveal(this, x, y, r, 0.0f);
                a.setDuration(360L);
                a.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
                a.addListener(new AnimateCloseListener(this));
                a.start();
                return;
            }
            animate().alpha(0.0f).setDuration(240L).setInterpolator(Interpolators.ALPHA_OUT).setListener(new AnimateCloseListener(this)).start();
            return;
        }
        Log.w("NotificationGuts", "Failed to animate guts close");
    }

    public void setActualHeight(int actualHeight) {
        this.mActualHeight = actualHeight;
        invalidate();
    }

    public int getIntrinsicHeight() {
        return (this.mGutsContent == null || !this.mExposed) ? getHeight() : this.mGutsContent.getActualHeight();
    }

    public void setClipTopAmount(int clipTopAmount) {
        this.mClipTopAmount = clipTopAmount;
        invalidate();
    }

    public void setClipBottomAmount(int clipBottomAmount) {
        this.mClipBottomAmount = clipBottomAmount;
        invalidate();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setClosedListener(OnGutsClosedListener listener) {
        this.mClosedListener = listener;
    }

    public void setHeightChangedListener(OnHeightChangedListener listener) {
        this.mHeightListener = listener;
    }

    protected void onHeightChanged() {
        if (this.mHeightListener != null) {
            this.mHeightListener.onHeightChanged(this);
        }
    }

    @VisibleForTesting
    void setExposed(boolean exposed, boolean needsFalsingProtection) {
        boolean wasExposed = this.mExposed;
        this.mExposed = exposed;
        this.mNeedsFalsingProtection = needsFalsingProtection;
        if (this.mExposed && this.mNeedsFalsingProtection) {
            resetFalsingCheck();
        } else {
            this.mHandler.removeCallbacks(this.mFalsingCheck);
        }
        if (wasExposed != this.mExposed && this.mGutsContent != null) {
            View contentView = this.mGutsContent.getContentView();
            contentView.sendAccessibilityEvent(32);
            if (this.mExposed) {
                contentView.requestAccessibilityFocus();
            }
        }
    }

    public boolean willBeRemoved() {
        if (this.mGutsContent != null) {
            return this.mGutsContent.willBeRemoved();
        }
        return false;
    }

    public boolean isExposed() {
        return this.mExposed;
    }

    public boolean isLeavebehind() {
        return this.mGutsContent != null && this.mGutsContent.isLeavebehind();
    }

    private static class AnimateOpenListener extends AnimatorListenerAdapter {
        final Runnable mOnAnimationEnd;

        private AnimateOpenListener(Runnable onAnimationEnd) {
            this.mOnAnimationEnd = onAnimationEnd;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if (this.mOnAnimationEnd != null) {
                this.mOnAnimationEnd.run();
            }
        }
    }

    private static class AnimateCloseListener extends AnimatorListenerAdapter {
        final View mView;

        private AnimateCloseListener(View view) {
            this.mView = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            this.mView.setVisibility(8);
        }
    }
}
