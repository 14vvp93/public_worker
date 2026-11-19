package com.android.systemui.statusbar.phone;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;

/* JADX INFO: loaded from: classes.dex */
public class NotificationsQuickSettingsContainer extends FrameLayout implements ViewStub.OnInflateListener, FragmentHostManager.FragmentListener, AboveShelfObserver.HasViewAboveShelfChangedListener {
    private int mBottomPadding;
    private boolean mCustomizerAnimating;
    private boolean mHasViewsAboveShelf;
    private boolean mInflated;
    private View mKeyguardStatusBar;
    private boolean mQsExpanded;
    private FrameLayout mQsFrame;
    private NotificationStackScrollLayout mStackScroller;
    private int mStackScrollerMargin;
    private View mUserSwitcher;

    public NotificationsQuickSettingsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mQsFrame = (FrameLayout) findViewById(R.id.qs_frame);
        this.mStackScroller = (NotificationStackScrollLayout) findViewById(R.id.notification_stack_scroller);
        this.mStackScrollerMargin = ((FrameLayout.LayoutParams) this.mStackScroller.getLayoutParams()).bottomMargin;
        this.mKeyguardStatusBar = findViewById(R.id.keyguard_header);
        ViewStub userSwitcher = (ViewStub) findViewById(R.id.keyguard_user_switcher);
        userSwitcher.setOnInflateListener(this);
        this.mUserSwitcher = userSwitcher;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        FragmentHostManager.get(this).addTagListener(QS.TAG, this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        FragmentHostManager.get(this).removeTagListener(QS.TAG, this);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        reloadWidth(this.mQsFrame, R.dimen.qs_panel_width);
        reloadWidth(this.mStackScroller, R.dimen.notification_panel_width);
    }

    private void reloadWidth(View view, int width) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = getResources().getDimensionPixelSize(width);
        view.setLayoutParams(params);
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        this.mBottomPadding = insets.getStableInsetBottom();
        setPadding(0, 0, 0, this.mBottomPadding);
        return insets;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        View view;
        View view2;
        boolean userSwitcherVisible = this.mInflated && this.mUserSwitcher.getVisibility() == 0;
        boolean statusBarVisible = this.mKeyguardStatusBar.getVisibility() == 0;
        boolean qsBottom = this.mHasViewsAboveShelf;
        View stackQsTop = qsBottom ? this.mStackScroller : this.mQsFrame;
        View stackQsBottom = !qsBottom ? this.mStackScroller : this.mQsFrame;
        if (child == this.mQsFrame) {
            if (userSwitcherVisible && statusBarVisible) {
                view2 = this.mUserSwitcher;
            } else if (statusBarVisible) {
                view2 = this.mKeyguardStatusBar;
            } else {
                view2 = userSwitcherVisible ? this.mUserSwitcher : stackQsBottom;
            }
            return super.drawChild(canvas, view2, drawingTime);
        }
        if (child == this.mStackScroller) {
            if (userSwitcherVisible && statusBarVisible) {
                view = this.mKeyguardStatusBar;
            } else {
                view = (statusBarVisible || userSwitcherVisible) ? stackQsBottom : stackQsTop;
            }
            return super.drawChild(canvas, view, drawingTime);
        }
        if (child == this.mUserSwitcher) {
            return super.drawChild(canvas, (userSwitcherVisible && statusBarVisible) ? stackQsBottom : stackQsTop, drawingTime);
        }
        if (child == this.mKeyguardStatusBar) {
            return super.drawChild(canvas, stackQsTop, drawingTime);
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override // android.view.ViewStub.OnInflateListener
    public void onInflate(ViewStub stub, View inflated) {
        if (stub == this.mUserSwitcher) {
            this.mUserSwitcher = inflated;
            this.mInflated = true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
    public void onFragmentViewCreated(String tag, Fragment fragment) {
        QS container = (QS) fragment;
        container.setContainer(this);
    }

    public void setQsExpanded(boolean expanded) {
        if (this.mQsExpanded != expanded) {
            this.mQsExpanded = expanded;
            invalidate();
        }
    }

    public void setCustomizerAnimating(boolean isAnimating) {
        if (this.mCustomizerAnimating != isAnimating) {
            this.mCustomizerAnimating = isAnimating;
            invalidate();
        }
    }

    public void setCustomizerShowing(boolean isShowing) {
        if (!isShowing) {
            setPadding(0, 0, 0, this.mBottomPadding);
            setBottomMargin(this.mStackScroller, this.mStackScrollerMargin);
        } else {
            setPadding(0, 0, 0, 0);
            setBottomMargin(this.mStackScroller, 0);
        }
        this.mStackScroller.setQsCustomizerShowing(isShowing);
    }

    private void setBottomMargin(View v, int bottomMargin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.bottomMargin = bottomMargin;
        v.setLayoutParams(params);
    }

    @Override // com.android.systemui.statusbar.notification.AboveShelfObserver.HasViewAboveShelfChangedListener
    public void onHasViewsAboveShelfChanged(boolean hasViewsAboveShelf) {
        this.mHasViewsAboveShelf = hasViewsAboveShelf;
        invalidate();
    }
}
