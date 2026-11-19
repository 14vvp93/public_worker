package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.widget.FloatingToolbar;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarWindowView extends FrameLayout {
    public static final boolean DEBUG = StatusBar.DEBUG;
    private View mBrightnessMirror;
    private DoubleTapHelper mDoubleTapHelper;
    private DragDownHelper mDragDownHelper;
    private boolean mExpandAnimationPending;
    private boolean mExpandAnimationRunning;
    private Window mFakeWindow;
    private FalsingManager mFalsingManager;
    private ActionMode mFloatingActionMode;
    private View mFloatingActionModeOriginatingView;
    private FloatingToolbar mFloatingToolbar;
    private ViewTreeObserver.OnPreDrawListener mFloatingToolbarPreDrawListener;
    private int mLeftInset;
    private NotificationPanelView mNotificationPanel;
    private int mRightInset;
    private StatusBar mService;
    private NotificationStackScrollLayout mStackScrollLayout;
    private boolean mTouchActive;
    private boolean mTouchCancelled;
    private final Paint mTransparentSrcPaint;

    public StatusBarWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRightInset = 0;
        this.mLeftInset = 0;
        this.mTransparentSrcPaint = new Paint();
        this.mFakeWindow = new Window(this.mContext) { // from class: com.android.systemui.statusbar.phone.StatusBarWindowView.2
            @Override // android.view.Window
            public void takeSurface(SurfaceHolder.Callback2 callback) {
            }

            @Override // android.view.Window
            public void takeInputQueue(InputQueue.Callback callback) {
            }

            @Override // android.view.Window
            public boolean isFloating() {
                return false;
            }

            public void alwaysReadCloseOnTouchAttr() {
            }

            @Override // android.view.Window
            public void setContentView(int layoutResID) {
            }

            @Override // android.view.Window
            public void setContentView(View view) {
            }

            @Override // android.view.Window
            public void setContentView(View view, ViewGroup.LayoutParams params) {
            }

            @Override // android.view.Window
            public void addContentView(View view, ViewGroup.LayoutParams params) {
            }

            public void clearContentView() {
            }

            @Override // android.view.Window
            public View getCurrentFocus() {
                return null;
            }

            @Override // android.view.Window
            public LayoutInflater getLayoutInflater() {
                return null;
            }

            @Override // android.view.Window
            public void setTitle(CharSequence title) {
            }

            @Override // android.view.Window
            public void setTitleColor(int textColor) {
            }

            @Override // android.view.Window
            public void openPanel(int featureId, KeyEvent event) {
            }

            @Override // android.view.Window
            public void closePanel(int featureId) {
            }

            @Override // android.view.Window
            public void togglePanel(int featureId, KeyEvent event) {
            }

            @Override // android.view.Window
            public void invalidatePanelMenu(int featureId) {
            }

            @Override // android.view.Window
            public boolean performPanelShortcut(int featureId, int keyCode, KeyEvent event, int flags) {
                return false;
            }

            @Override // android.view.Window
            public boolean performPanelIdentifierAction(int featureId, int id, int flags) {
                return false;
            }

            @Override // android.view.Window
            public void closeAllPanels() {
            }

            @Override // android.view.Window
            public boolean performContextMenuIdentifierAction(int id, int flags) {
                return false;
            }

            @Override // android.view.Window
            public void onConfigurationChanged(Configuration newConfig) {
            }

            @Override // android.view.Window
            public void setBackgroundDrawable(Drawable drawable) {
            }

            @Override // android.view.Window
            public void setFeatureDrawableResource(int featureId, int resId) {
            }

            @Override // android.view.Window
            public void setFeatureDrawableUri(int featureId, Uri uri) {
            }

            @Override // android.view.Window
            public void setFeatureDrawable(int featureId, Drawable drawable) {
            }

            @Override // android.view.Window
            public void setFeatureDrawableAlpha(int featureId, int alpha) {
            }

            @Override // android.view.Window
            public void setFeatureInt(int featureId, int value) {
            }

            @Override // android.view.Window
            public void takeKeyEvents(boolean get) {
            }

            @Override // android.view.Window
            public boolean superDispatchKeyEvent(KeyEvent event) {
                return false;
            }

            @Override // android.view.Window
            public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
                return false;
            }

            @Override // android.view.Window
            public boolean superDispatchTouchEvent(MotionEvent event) {
                return false;
            }

            @Override // android.view.Window
            public boolean superDispatchTrackballEvent(MotionEvent event) {
                return false;
            }

            @Override // android.view.Window
            public boolean superDispatchGenericMotionEvent(MotionEvent event) {
                return false;
            }

            @Override // android.view.Window
            public View getDecorView() {
                return StatusBarWindowView.this;
            }

            @Override // android.view.Window
            public View peekDecorView() {
                return null;
            }

            @Override // android.view.Window
            public Bundle saveHierarchyState() {
                return null;
            }

            @Override // android.view.Window
            public void restoreHierarchyState(Bundle savedInstanceState) {
            }

            @Override // android.view.Window
            protected void onActive() {
            }

            @Override // android.view.Window
            public void setChildDrawable(int featureId, Drawable drawable) {
            }

            @Override // android.view.Window
            public void setChildInt(int featureId, int value) {
            }

            @Override // android.view.Window
            public boolean isShortcutKey(int keyCode, KeyEvent event) {
                return false;
            }

            @Override // android.view.Window
            public void setVolumeControlStream(int streamType) {
            }

            @Override // android.view.Window
            public int getVolumeControlStream() {
                return 0;
            }

            @Override // android.view.Window
            public int getStatusBarColor() {
                return 0;
            }

            @Override // android.view.Window
            public void setStatusBarColor(int color) {
            }

            @Override // android.view.Window
            public int getNavigationBarColor() {
                return 0;
            }

            @Override // android.view.Window
            public void setNavigationBarColor(int color) {
            }

            @Override // android.view.Window
            public void setDecorCaptionShade(int decorCaptionShade) {
            }

            @Override // android.view.Window
            public void setResizingCaptionDrawable(Drawable drawable) {
            }

            public void onMultiWindowModeChanged() {
            }

            public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
            }

            public void reportActivityRelaunched() {
            }
        };
        setMotionEventSplittingEnabled(false);
        this.mTransparentSrcPaint.setColor(0);
        this.mTransparentSrcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mDoubleTapHelper = new DoubleTapHelper(this, new DoubleTapHelper.ActivationListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarWindowView$54_FzTc3fT8lNrZW41sYOtiGEgM
            @Override // com.android.systemui.statusbar.phone.DoubleTapHelper.ActivationListener
            public final void onActiveChanged(boolean z) {
                StatusBarWindowView.lambda$new$0(z);
            }
        }, new DoubleTapHelper.DoubleTapListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarWindowView$BAuOQoqmlMZAVWY0NirR_GReGxs
            @Override // com.android.systemui.statusbar.phone.DoubleTapHelper.DoubleTapListener
            public final boolean onDoubleTap() {
                return StatusBarWindowView.lambda$new$1(this.f$0);
            }
        }, null, null);
    }

    static /* synthetic */ void lambda$new$0(boolean active) {
    }

    public static /* synthetic */ boolean lambda$new$1(StatusBarWindowView statusBarWindowView) {
        statusBarWindowView.mService.wakeUpIfDozing(SystemClock.uptimeMillis(), statusBarWindowView);
        return true;
    }

    @Override // android.view.View
    protected boolean fitSystemWindows(Rect insets) {
        boolean z = true;
        if (getFitsSystemWindows()) {
            if (insets.top == getPaddingTop() && insets.bottom == getPaddingBottom()) {
                z = false;
            }
            boolean paddingChanged = z;
            if (insets.right != this.mRightInset || insets.left != this.mLeftInset) {
                this.mRightInset = insets.right;
                this.mLeftInset = insets.left;
                applyMargins();
            }
            if (paddingChanged) {
                setPadding(0, 0, 0, 0);
            }
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        } else {
            if (this.mRightInset != 0 || this.mLeftInset != 0) {
                this.mRightInset = 0;
                this.mLeftInset = 0;
                applyMargins();
            }
            if (getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingTop() == 0 && getPaddingBottom() == 0) {
                z = false;
            }
            boolean changed = z;
            if (changed) {
                setPadding(0, 0, 0, 0);
            }
            insets.top = 0;
        }
        return false;
    }

    private void applyMargins() {
        int N = getChildCount();
        for (int i = 0; i < N; i++) {
            View child = getChildAt(i);
            if (child.getLayoutParams() instanceof LayoutParams) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!lp.ignoreRightInset && (lp.rightMargin != this.mRightInset || lp.leftMargin != this.mLeftInset)) {
                    lp.rightMargin = this.mRightInset;
                    lp.leftMargin = this.mLeftInset;
                    child.requestLayout();
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mStackScrollLayout = (NotificationStackScrollLayout) findViewById(R.id.notification_stack_scroller);
        this.mNotificationPanel = (NotificationPanelView) findViewById(R.id.notification_panel);
        this.mBrightnessMirror = findViewById(R.id.brightness_mirror);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child.getId() == R.id.brightness_mirror) {
            this.mBrightnessMirror = child;
        }
    }

    public void setService(StatusBar service) {
        this.mService = service;
        setDragDownHelper(new DragDownHelper(getContext(), this, this.mStackScrollLayout, this.mService));
    }

    @VisibleForTesting
    void setDragDownHelper(DragDownHelper dragDownHelper) {
        this.mDragDownHelper = dragDownHelper;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mService.isScrimSrcModeEnabled()) {
            IBinder windowToken = getWindowToken();
            WindowManager.LayoutParams lp = (WindowManager.LayoutParams) getLayoutParams();
            lp.token = windowToken;
            setLayoutParams(lp);
            WindowManagerGlobal.getInstance().changeCanvasOpacity(windowToken, true);
            setWillNotDraw(false);
            return;
        }
        setWillNotDraw(!DEBUG);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x004d  */
    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (this.mService.interceptMediaKey(event) || super.dispatchKeyEvent(event)) {
            return true;
        }
        boolean down = event.getAction() == 0;
        int keyCode = event.getKeyCode();
        if (keyCode == 4) {
            if (!down) {
                this.mService.onBackPressed();
            }
            return true;
        }
        if (keyCode == 62) {
            if (!down) {
                return this.mService.onSpacePressed();
            }
        } else if (keyCode == 82) {
            if (!down) {
                return this.mService.onMenuPressed();
            }
            if (!down) {
                return this.mService.onSpacePressed();
            }
        } else {
            switch (keyCode) {
                case 24:
                case 25:
                    if (this.mService.isDozing()) {
                        MediaSessionLegacyHelper.getHelper(this.mContext).sendVolumeKeyEvent(event, Integer.MIN_VALUE, true);
                        return true;
                    }
                default:
                    return false;
            }
        }
        return false;
    }

    public void setTouchActive(boolean touchActive) {
        this.mTouchActive = touchActive;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isDown = ev.getActionMasked() == 0;
        boolean isCancel = ev.getActionMasked() == 3;
        if (!isCancel && this.mService.shouldIgnoreTouch()) {
            return false;
        }
        if (isDown && this.mNotificationPanel.isFullyCollapsed()) {
            this.mNotificationPanel.startExpandLatencyTracking();
        }
        if (isDown) {
            setTouchActive(true);
            this.mTouchCancelled = false;
        } else if (ev.getActionMasked() == 1 || ev.getActionMasked() == 3) {
            setTouchActive(false);
        }
        if (this.mTouchCancelled || this.mExpandAnimationRunning || this.mExpandAnimationPending) {
            return false;
        }
        this.mFalsingManager.onTouchEvent(ev, getWidth(), getHeight());
        if (this.mBrightnessMirror != null && this.mBrightnessMirror.getVisibility() == 0 && ev.getActionMasked() == 5) {
            return false;
        }
        if (isDown) {
            this.mStackScrollLayout.closeControlsIfOutsideTouch(ev);
        }
        if (this.mService.isDozing()) {
            this.mService.mDozeScrimController.extendPulse();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mService.isDozing() && !this.mStackScrollLayout.hasPulsingNotifications()) {
            return true;
        }
        boolean intercept = false;
        if (this.mNotificationPanel.isFullyExpanded() && this.mStackScrollLayout.getVisibility() == 0 && this.mService.getBarState() == 1 && !this.mService.isBouncerShowing() && !this.mService.isDozing()) {
            intercept = this.mDragDownHelper.onInterceptTouchEvent(ev);
        }
        if (!intercept) {
            super.onInterceptTouchEvent(ev);
        }
        if (intercept) {
            MotionEvent cancellation = MotionEvent.obtain(ev);
            cancellation.setAction(3);
            this.mStackScrollLayout.onInterceptTouchEvent(cancellation);
            this.mNotificationPanel.onInterceptTouchEvent(cancellation);
            cancellation.recycle();
        }
        return intercept;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        boolean handled = false;
        if (this.mService.isDozing()) {
            this.mDoubleTapHelper.onTouchEvent(ev);
            handled = true;
        }
        if ((this.mService.getBarState() == 1 && !handled) || this.mDragDownHelper.isDraggingDown()) {
            handled = this.mDragDownHelper.onTouchEvent(ev);
        }
        if (!handled) {
            handled = super.onTouchEvent(ev);
        }
        int action = ev.getAction();
        if (!handled && (action == 1 || action == 3)) {
            this.mService.setInteracting(1, false);
        }
        return handled;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mService.isScrimSrcModeEnabled()) {
            int paddedBottom = getHeight() - getPaddingBottom();
            int paddedRight = getWidth() - getPaddingRight();
            if (getPaddingTop() != 0) {
                canvas.drawRect(0.0f, 0.0f, getWidth(), getPaddingTop(), this.mTransparentSrcPaint);
            }
            if (getPaddingBottom() != 0) {
                canvas.drawRect(0.0f, paddedBottom, getWidth(), getHeight(), this.mTransparentSrcPaint);
            }
            if (getPaddingLeft() != 0) {
                canvas.drawRect(0.0f, getPaddingTop(), getPaddingLeft(), paddedBottom, this.mTransparentSrcPaint);
            }
            if (getPaddingRight() != 0) {
                canvas.drawRect(paddedRight, getPaddingTop(), getWidth(), paddedBottom, this.mTransparentSrcPaint);
            }
        }
        if (DEBUG) {
            Paint pt = new Paint();
            pt.setColor(-2130706688);
            pt.setStrokeWidth(12.0f);
            pt.setStyle(Paint.Style.STROKE);
            canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), pt);
        }
    }

    public void cancelExpandHelper() {
        if (this.mStackScrollLayout != null) {
            this.mStackScrollLayout.cancelExpandHelper();
        }
    }

    public void cancelCurrentTouch() {
        if (this.mTouchActive) {
            long now = SystemClock.uptimeMillis();
            MotionEvent event = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
            event.setSource(4098);
            dispatchTouchEvent(event);
            event.recycle();
            this.mTouchCancelled = true;
        }
    }

    public void setExpandAnimationRunning(boolean expandAnimationRunning) {
        this.mExpandAnimationRunning = expandAnimationRunning;
    }

    public void setExpandAnimationPending(boolean pending) {
        this.mExpandAnimationPending = pending;
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("  mExpandAnimationPending=");
        pw.println(this.mExpandAnimationPending);
        pw.print("  mExpandAnimationRunning=");
        pw.println(this.mExpandAnimationRunning);
        pw.print("  mTouchCancelled=");
        pw.println(this.mTouchCancelled);
        pw.print("  mTouchActive=");
        pw.println(this.mTouchActive);
    }

    public class LayoutParams extends FrameLayout.LayoutParams {
        public boolean ignoreRightInset;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.StatusBarWindowView_Layout);
            this.ignoreRightInset = a.getBoolean(0, false);
            a.recycle();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
        if (type == 1) {
            return startActionMode(originalView, callback, type);
        }
        return super.startActionModeForChild(originalView, callback, type);
    }

    private ActionMode createFloatingActionMode(View originatingView, ActionMode.Callback2 callback) {
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mFakeWindow);
        final FloatingActionMode mode = new FloatingActionMode(this.mContext, callback, originatingView, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = originatingView;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.phone.StatusBarWindowView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                mode.updateViewLocationInWindow();
                return true;
            }
        };
        return mode;
    }

    private void setHandledFloatingActionMode(ActionMode mode) {
        this.mFloatingActionMode = mode;
        this.mFloatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupFloatingActionModeViews() {
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        if (this.mFloatingActionModeOriginatingView != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                this.mFloatingActionModeOriginatingView.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    private ActionMode startActionMode(View originatingView, ActionMode.Callback callback, int type) {
        ActionMode.Callback2 wrappedCallback = new ActionModeCallback2Wrapper(callback);
        ActionMode mode = createFloatingActionMode(originatingView, wrappedCallback);
        if (mode != null && wrappedCallback.onCreateActionMode(mode, mode.getMenu())) {
            setHandledFloatingActionMode(mode);
            return mode;
        }
        return null;
    }

    private class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback wrapped) {
            this.mWrapped = wrapped;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onCreateActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            StatusBarWindowView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            this.mWrapped.onDestroyActionMode(mode);
            if (mode == StatusBarWindowView.this.mFloatingActionMode) {
                StatusBarWindowView.this.cleanupFloatingActionModeViews();
                StatusBarWindowView.this.mFloatingActionMode = null;
            }
            StatusBarWindowView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (this.mWrapped instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) this.mWrapped).onGetContentRect(mode, view, outRect);
            } else {
                super.onGetContentRect(mode, view, outRect);
            }
        }
    }
}
