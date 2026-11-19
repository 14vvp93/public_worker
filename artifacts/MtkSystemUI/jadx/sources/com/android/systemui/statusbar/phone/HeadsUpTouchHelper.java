package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpTouchHelper {
    private boolean mCollapseSnoozes;
    private HeadsUpManagerPhone mHeadsUpManager;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private NotificationPanelView mPanel;
    private ExpandableNotificationRow mPickedChild;
    private NotificationStackScrollLayout mStackScroller;
    private float mTouchSlop;
    private boolean mTouchingHeadsUpView;
    private boolean mTrackingHeadsUp;
    private int mTrackingPointer;

    public HeadsUpTouchHelper(HeadsUpManagerPhone headsUpManager, NotificationStackScrollLayout stackScroller, NotificationPanelView notificationPanelView) {
        this.mHeadsUpManager = headsUpManager;
        this.mStackScroller = stackScroller;
        this.mPanel = notificationPanelView;
        Context context = stackScroller.getContext();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mTouchSlop = configuration.getScaledTouchSlop();
    }

    public boolean isTrackingHeadsUp() {
        return this.mTrackingHeadsUp;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationData.Entry topEntry;
        if (!this.mTouchingHeadsUpView && motionEvent.getActionMasked() != 0) {
            return false;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
        if (iFindPointerIndex < 0) {
            iFindPointerIndex = 0;
            this.mTrackingPointer = motionEvent.getPointerId(0);
        }
        float x = motionEvent.getX(iFindPointerIndex);
        float y = motionEvent.getY(iFindPointerIndex);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    this.mInitialTouchY = y;
                    this.mInitialTouchX = x;
                    setTrackingHeadsUp(false);
                    ExpandableView childAtRawPosition = this.mStackScroller.getChildAtRawPosition(x, y);
                    this.mTouchingHeadsUpView = false;
                    if (childAtRawPosition instanceof ExpandableNotificationRow) {
                        this.mPickedChild = (ExpandableNotificationRow) childAtRawPosition;
                        this.mTouchingHeadsUpView = !this.mStackScroller.isExpanded() && this.mPickedChild.isHeadsUp() && this.mPickedChild.isPinned();
                    } else if (childAtRawPosition == null && !this.mStackScroller.isExpanded() && (topEntry = this.mHeadsUpManager.getTopEntry()) != null && topEntry.row.isPinned()) {
                        this.mPickedChild = topEntry.row;
                        this.mTouchingHeadsUpView = true;
                    }
                    break;
                case 1:
                case 3:
                    if (this.mPickedChild != null && this.mTouchingHeadsUpView && this.mHeadsUpManager.shouldSwallowClick(this.mPickedChild.getStatusBarNotification().getKey())) {
                        endMotion();
                        return true;
                    }
                    endMotion();
                    break;
                    break;
                case 2:
                    float f = y - this.mInitialTouchY;
                    if (this.mTouchingHeadsUpView && Math.abs(f) > this.mTouchSlop && Math.abs(f) > Math.abs(x - this.mInitialTouchX)) {
                        setTrackingHeadsUp(true);
                        this.mCollapseSnoozes = f < 0.0f;
                        this.mInitialTouchX = x;
                        this.mInitialTouchY = y;
                        int actualHeight = (int) (this.mPickedChild.getActualHeight() + this.mPickedChild.getTranslationY());
                        this.mPanel.setPanelScrimMinFraction(actualHeight / this.mPanel.getMaxPanelHeight());
                        this.mPanel.startExpandMotion(x, y, true, actualHeight);
                        this.mPanel.startExpandingFromPeek();
                        this.mHeadsUpManager.unpinAll();
                        this.mPanel.clearNotificationEffects();
                        endMotion();
                        return true;
                    }
                    break;
            }
        } else {
            int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
            if (this.mTrackingPointer == pointerId) {
                boolean z = motionEvent.getPointerId(0) == pointerId;
                this.mTrackingPointer = motionEvent.getPointerId(z ? 1 : 0);
                this.mInitialTouchX = motionEvent.getX(z ? 1 : 0);
                this.mInitialTouchY = motionEvent.getY(z ? 1 : 0);
            }
        }
        return false;
    }

    private void setTrackingHeadsUp(boolean tracking) {
        this.mTrackingHeadsUp = tracking;
        this.mHeadsUpManager.setTrackingHeadsUp(tracking);
        this.mPanel.setTrackedHeadsUp(tracking ? this.mPickedChild : null);
    }

    public void notifyFling(boolean collapse) {
        if (collapse && this.mCollapseSnoozes) {
            this.mHeadsUpManager.snooze();
        }
        this.mCollapseSnoozes = false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mTrackingHeadsUp) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            endMotion();
            setTrackingHeadsUp(false);
        }
        return true;
    }

    private void endMotion() {
        this.mTrackingPointer = -1;
        this.mPickedChild = null;
        this.mTouchingHeadsUpView = false;
    }
}
