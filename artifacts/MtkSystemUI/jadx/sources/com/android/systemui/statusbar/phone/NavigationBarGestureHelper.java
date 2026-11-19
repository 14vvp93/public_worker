package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.RecentsComponent;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.plugins.statusbar.phone.NavGesture;
import com.android.systemui.stackdivider.Divider;
import com.android.systemui.stackdivider.DividerView;
import com.android.systemui.tuner.TunerService;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarGestureHelper implements NavGesture.GestureHelper, TunerService.Tunable {
    private Context mContext;
    private Divider mDivider;
    private boolean mDockWindowEnabled;
    private boolean mDockWindowTouchSlopExceeded;
    private boolean mDownOnRecents;
    private int mDragMode;
    private boolean mIsInScreenPinning;
    private boolean mIsVertical;
    private NavigationBarView mNavigationBarView;
    private boolean mNotificationsVisibleOnDown;
    private final QuickStepController mQuickStepController;
    private RecentsComponent mRecentsComponent;
    private final int mScrollTouchSlop;
    private final StatusBar mStatusBar;
    private int mTouchDownX;
    private int mTouchDownY;
    private VelocityTracker mVelocityTracker;

    public NavigationBarGestureHelper(Context context) {
        this.mContext = context;
        this.mStatusBar = (StatusBar) SysUiServiceProvider.getComponent(context, StatusBar.class);
        Resources r = context.getResources();
        this.mScrollTouchSlop = r.getDimensionPixelSize(R.dimen.navigation_bar_min_swipe_distance);
        this.mQuickStepController = new QuickStepController(context);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "overview_nav_bar_gesture");
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void destroy() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
    }

    public void setComponents(RecentsComponent recentsComponent, Divider divider, NavigationBarView navigationBarView) {
        this.mRecentsComponent = recentsComponent;
        this.mDivider = divider;
        this.mNavigationBarView = navigationBarView;
        this.mQuickStepController.setComponents(this.mNavigationBarView);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void setBarState(boolean isVertical, boolean isRTL) {
        this.mIsVertical = isVertical;
        this.mQuickStepController.setBarState(isVertical, isRTL);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            this.mIsInScreenPinning = this.mNavigationBarView.inScreenPinning();
            this.mNotificationsVisibleOnDown = !this.mStatusBar.isPresenterFullyCollapsed();
        }
        if (!canHandleGestures()) {
            return false;
        }
        boolean result = this.mQuickStepController.onInterceptTouchEvent(event);
        if (this.mDockWindowEnabled) {
            return result | interceptDockWindowEvent(event);
        }
        return result;
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public boolean onTouchEvent(MotionEvent event) {
        if (!canHandleGestures()) {
            return false;
        }
        boolean result = this.mQuickStepController.onTouchEvent(event);
        if (this.mDockWindowEnabled) {
            return result | handleDockWindowEvent(event);
        }
        return result;
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onDraw(Canvas canvas) {
        this.mQuickStepController.onDraw(canvas);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mQuickStepController.onLayout(changed, left, top, right, bottom);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onDarkIntensityChange(float intensity) {
        this.mQuickStepController.onDarkIntensityChange(intensity);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onNavigationButtonLongPress(View v) {
        this.mQuickStepController.onNavigationButtonLongPress(v);
    }

    private boolean interceptDockWindowEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                handleDragActionDownEvent(event);
                return false;
            case 1:
            case 3:
                handleDragActionUpEvent(event);
                return false;
            case 2:
                return handleDragActionMoveEvent(event);
            default:
                return false;
        }
    }

    private boolean handleDockWindowEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                handleDragActionDownEvent(event);
                break;
            case 1:
            case 3:
                handleDragActionUpEvent(event);
                break;
            case 2:
                handleDragActionMoveEvent(event);
                break;
        }
        return true;
    }

    private void handleDragActionDownEvent(MotionEvent event) {
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mVelocityTracker.addMovement(event);
        boolean z = false;
        this.mDockWindowTouchSlopExceeded = false;
        this.mTouchDownX = (int) event.getX();
        this.mTouchDownY = (int) event.getY();
        if (this.mNavigationBarView != null) {
            View recentsButton = this.mNavigationBarView.getRecentsButton().getCurrentView();
            if (recentsButton != null) {
                if (this.mTouchDownX >= recentsButton.getLeft() && this.mTouchDownX <= recentsButton.getRight() && this.mTouchDownY >= recentsButton.getTop() && this.mTouchDownY <= recentsButton.getBottom()) {
                    z = true;
                }
                this.mDownOnRecents = z;
                return;
            }
            this.mDownOnRecents = false;
        }
    }

    private boolean handleDragActionMoveEvent(MotionEvent event) {
        int rawY;
        this.mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        int xDiff = Math.abs(x - this.mTouchDownX);
        int yDiff = Math.abs(y - this.mTouchDownY);
        if (this.mDivider == null || this.mRecentsComponent == null) {
            return false;
        }
        if (!this.mDockWindowTouchSlopExceeded) {
            boolean touchSlopExceeded = this.mIsVertical ? !(xDiff <= this.mScrollTouchSlop || xDiff <= yDiff) : !(yDiff <= this.mScrollTouchSlop || yDiff <= xDiff);
            if (this.mDownOnRecents && touchSlopExceeded && this.mDivider.getView() != null && this.mDivider.getView().getWindowManagerProxy().getDockSide() == -1) {
                Rect initialBounds = null;
                int dragMode = calculateDragMode();
                int createMode = 0;
                int i = 2;
                if (dragMode == 1) {
                    initialBounds = new Rect();
                    DividerView view = this.mDivider.getView();
                    if (this.mIsVertical) {
                        rawY = (int) event.getRawX();
                    } else {
                        rawY = (int) event.getRawY();
                    }
                    if (!this.mDivider.getView().isHorizontalDivision()) {
                        i = 1;
                    }
                    view.calculateBoundsForPosition(rawY, i, initialBounds);
                } else if (dragMode == 0 && this.mTouchDownX < this.mContext.getResources().getDisplayMetrics().widthPixels / 2) {
                    createMode = 1;
                }
                boolean docked = this.mRecentsComponent.splitPrimaryTask(dragMode, createMode, initialBounds, 272);
                if (docked) {
                    this.mDragMode = dragMode;
                    if (this.mDragMode == 1) {
                        this.mDivider.getView().startDragging(false, true);
                    }
                    this.mDockWindowTouchSlopExceeded = true;
                    return true;
                }
            }
        } else if (this.mDragMode == 1 && this.mDivider.getView() != null) {
            int position = (int) (!this.mIsVertical ? event.getRawY() : event.getRawX());
            DividerSnapAlgorithm.SnapTarget snapTarget = this.mDivider.getView().getSnapAlgorithm().calculateSnapTarget(position, 0.0f, false);
            this.mDivider.getView().resizeStack(position, snapTarget.position, snapTarget);
        } else if (this.mDragMode == 0) {
            this.mRecentsComponent.onDraggingInRecents(event.getRawY());
        }
        return false;
    }

    private void handleDragActionUpEvent(MotionEvent event) {
        int rawY;
        float yVelocity;
        this.mVelocityTracker.addMovement(event);
        this.mVelocityTracker.computeCurrentVelocity(1000);
        if (this.mDockWindowTouchSlopExceeded && this.mDivider != null && this.mRecentsComponent != null) {
            if (this.mDragMode == 1 && this.mDivider.getView() != null) {
                DividerView view = this.mDivider.getView();
                if (this.mIsVertical) {
                    rawY = (int) event.getRawX();
                } else {
                    rawY = (int) event.getRawY();
                }
                if (this.mIsVertical) {
                    yVelocity = this.mVelocityTracker.getXVelocity();
                } else {
                    yVelocity = this.mVelocityTracker.getYVelocity();
                }
                view.stopDragging(rawY, yVelocity, true, false);
            } else if (this.mDragMode == 0) {
                this.mRecentsComponent.onDraggingInRecentsEnded(this.mVelocityTracker.getYVelocity());
            }
        }
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
    }

    private boolean canHandleGestures() {
        return (this.mIsInScreenPinning || this.mStatusBar.isKeyguardShowing() || this.mNotificationsVisibleOnDown) ? false : true;
    }

    private int calculateDragMode() {
        if (!this.mIsVertical || this.mDivider.getView() == null || this.mDivider.getView().isHorizontalDivision()) {
            return (this.mIsVertical || this.mDivider.getView() == null || !this.mDivider.getView().isHorizontalDivision()) ? 0 : 1;
        }
        return 1;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        boolean z = false;
        if (((key.hashCode() == 572283195 && key.equals("overview_nav_bar_gesture")) ? (byte) 0 : (byte) -1) == 0) {
            if (newValue != null && Integer.parseInt(newValue) != 0) {
                z = true;
            }
            this.mDockWindowEnabled = z;
        }
    }
}
