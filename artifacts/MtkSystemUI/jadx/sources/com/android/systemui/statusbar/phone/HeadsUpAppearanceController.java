package com.android.systemui.statusbar.phone;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpAppearanceController implements DarkIconDispatcher.DarkReceiver, OnHeadsUpChangedListener {
    private final View mClockView;
    private final DarkIconDispatcher mDarkIconDispatcher;
    private float mExpandFraction;
    private float mExpandedHeight;
    private final HeadsUpManagerPhone mHeadsUpManager;
    private final HeadsUpStatusBarView mHeadsUpStatusBarView;
    private boolean mIsExpanded;
    private final NotificationIconAreaController mNotificationIconAreaController;
    private final NotificationPanelView mPanelView;
    Point mPoint;
    private final BiConsumer<Float, Float> mSetExpandedHeight;
    private final Consumer<ExpandableNotificationRow> mSetTrackingHeadsUp;
    private boolean mShown;
    private final View.OnLayoutChangeListener mStackScrollLayoutChangeListener;
    private final NotificationStackScrollLayout mStackScroller;
    private ExpandableNotificationRow mTrackedChild;
    private final Runnable mUpdatePanelTranslation;

    public HeadsUpAppearanceController(NotificationIconAreaController notificationIconAreaController, HeadsUpManagerPhone headsUpManager, View statusbarView) {
        this(notificationIconAreaController, headsUpManager, (HeadsUpStatusBarView) statusbarView.findViewById(R.id.heads_up_status_bar_view), (NotificationStackScrollLayout) statusbarView.findViewById(R.id.notification_stack_scroller), (NotificationPanelView) statusbarView.findViewById(R.id.notification_panel), statusbarView.findViewById(R.id.clock));
    }

    @VisibleForTesting
    public HeadsUpAppearanceController(NotificationIconAreaController notificationIconAreaController, HeadsUpManagerPhone headsUpManager, HeadsUpStatusBarView headsUpStatusBarView, NotificationStackScrollLayout stackScroller, NotificationPanelView panelView, View clockView) {
        this.mSetTrackingHeadsUp = new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$Ndod8fRabzshTVmNSVHx_M0XNU4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.setTrackingHeadsUp((ExpandableNotificationRow) obj);
            }
        };
        this.mUpdatePanelTranslation = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$22QZFjoGlQJQoKOrFe-bHbZltB4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updatePanelTranslation();
            }
        };
        this.mSetExpandedHeight = new BiConsumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$DialNTWPBOn27MISeLu6p9klZxI
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.setExpandedHeight(((Float) obj).floatValue(), ((Float) obj2).floatValue());
            }
        };
        this.mStackScrollLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$HeadsUpAppearanceController$hwNOwOgXItDjQM7QwL00pigpnrk
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.updatePanelTranslation();
            }
        };
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpManager.addListener(this);
        this.mHeadsUpStatusBarView = headsUpStatusBarView;
        headsUpStatusBarView.setOnDrawingRectChangedListener(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$HeadsUpAppearanceController$1d3l5klDiH8maZOdHwrJBKgigPE
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateIsolatedIconLocationAndClock(true);
            }
        });
        this.mStackScroller = stackScroller;
        this.mPanelView = panelView;
        panelView.addTrackingHeadsUpListener(this.mSetTrackingHeadsUp);
        panelView.addVerticalTranslationListener(this.mUpdatePanelTranslation);
        panelView.setHeadsUpAppearanceController(this);
        this.mStackScroller.addOnExpandedHeightListener(this.mSetExpandedHeight);
        this.mStackScroller.addOnLayoutChangeListener(this.mStackScrollLayoutChangeListener);
        this.mStackScroller.setHeadsUpAppearanceController(this);
        this.mClockView = clockView;
        this.mDarkIconDispatcher = (DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class);
        this.mDarkIconDispatcher.addDarkReceiver(this);
    }

    public void destroy() {
        this.mHeadsUpManager.removeListener(this);
        this.mHeadsUpStatusBarView.setOnDrawingRectChangedListener(null);
        this.mPanelView.removeTrackingHeadsUpListener(this.mSetTrackingHeadsUp);
        this.mPanelView.removeVerticalTranslationListener(this.mUpdatePanelTranslation);
        this.mPanelView.setHeadsUpAppearanceController(null);
        this.mStackScroller.removeOnExpandedHeightListener(this.mSetExpandedHeight);
        this.mStackScroller.removeOnLayoutChangeListener(this.mStackScrollLayoutChangeListener);
        this.mDarkIconDispatcher.removeDarkReceiver(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIsolatedIconLocationAndClock(boolean requireStateUpdate) {
        this.mNotificationIconAreaController.setIsolatedIconLocation(this.mHeadsUpStatusBarView.getIconDrawingRect(), requireStateUpdate);
        Log.d("HeadsUpAppearanceController", "ATC: updateIsolatedIconLocationAndClock, isShown = " + isShown());
        if (this.mClockView != null && isShown()) {
            this.mClockView.setVisibility(4);
        }
    }

    private void updateIsolatedIconLocation(boolean requireStateUpdate) {
        this.mNotificationIconAreaController.setIsolatedIconLocation(this.mHeadsUpStatusBarView.getIconDrawingRect(), requireStateUpdate);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinned(ExpandableNotificationRow headsUp) {
        updateTopEntry();
        updateHeader(headsUp.getEntry());
    }

    private int getRtlTranslation() {
        if (this.mPoint == null) {
            this.mPoint = new Point();
        }
        int realDisplaySize = 0;
        if (this.mStackScroller.getDisplay() != null) {
            this.mStackScroller.getDisplay().getRealSize(this.mPoint);
            realDisplaySize = this.mPoint.x;
        }
        WindowInsets windowInset = this.mStackScroller.getRootWindowInsets();
        return ((windowInset.getSystemWindowInsetLeft() + this.mStackScroller.getRight()) + windowInset.getSystemWindowInsetRight()) - realDisplaySize;
    }

    public void updatePanelTranslation() {
        float newTranslation;
        if (this.mStackScroller.isLayoutRtl()) {
            newTranslation = getRtlTranslation();
        } else {
            newTranslation = this.mStackScroller.getLeft();
        }
        this.mHeadsUpStatusBarView.setPanelTranslation(newTranslation + this.mStackScroller.getTranslationX());
    }

    private void updateTopEntry() {
        NotificationData.Entry newEntry = null;
        if (!this.mIsExpanded && this.mHeadsUpManager.hasPinnedHeadsUp()) {
            newEntry = this.mHeadsUpManager.getTopEntry();
        }
        NotificationData.Entry previousEntry = this.mHeadsUpStatusBarView.getShowingEntry();
        this.mHeadsUpStatusBarView.setEntry(newEntry);
        if (newEntry != previousEntry) {
            boolean animateIsolation = false;
            if (newEntry == null) {
                setShown(false);
                animateIsolation = true ^ this.mIsExpanded;
            } else if (previousEntry == null) {
                setShown(true);
                animateIsolation = true ^ this.mIsExpanded;
            }
            updateIsolatedIconLocation(false);
            this.mNotificationIconAreaController.showIconIsolated(newEntry == null ? null : newEntry.icon, animateIsolation);
        }
    }

    private void setShown(boolean isShown) {
        if (this.mShown != isShown) {
            this.mShown = isShown;
            if (!isShown) {
                CrossFadeHelper.fadeIn(this.mClockView, 110L, 100);
                CrossFadeHelper.fadeOut(this.mHeadsUpStatusBarView, 110L, 0, new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$HeadsUpAppearanceController$5yJ6zXPrjSk_KB2fbcTxEu2j2TI
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mHeadsUpStatusBarView.setVisibility(8);
                    }
                });
            } else {
                this.mHeadsUpStatusBarView.setVisibility(0);
                CrossFadeHelper.fadeIn(this.mHeadsUpStatusBarView, 110L, 100);
                CrossFadeHelper.fadeOut(this.mClockView, 110L, 0, new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$HeadsUpAppearanceController$iMPD_c-MpkAUOLIdQAujzNCdyYQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mClockView.setVisibility(4);
                    }
                });
            }
        }
    }

    @VisibleForTesting
    public boolean isShown() {
        return this.mShown;
    }

    public boolean shouldBeVisible() {
        return !this.mIsExpanded && this.mHeadsUpManager.hasPinnedHeadsUp();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpUnPinned(ExpandableNotificationRow headsUp) {
        updateTopEntry();
        updateHeader(headsUp.getEntry());
    }

    public void setExpandedHeight(float expandedHeight, float appearFraction) {
        boolean changedHeight = expandedHeight != this.mExpandedHeight;
        this.mExpandedHeight = expandedHeight;
        this.mExpandFraction = appearFraction;
        boolean isExpanded = expandedHeight > 0.0f;
        if (changedHeight) {
            updateHeadsUpHeaders();
        }
        if (isExpanded != this.mIsExpanded) {
            this.mIsExpanded = isExpanded;
            updateTopEntry();
        }
    }

    public void setTrackingHeadsUp(ExpandableNotificationRow trackedChild) {
        ExpandableNotificationRow previousTracked = this.mTrackedChild;
        this.mTrackedChild = trackedChild;
        if (previousTracked != null) {
            updateHeader(previousTracked.getEntry());
        }
    }

    private void updateHeadsUpHeaders() {
        this.mHeadsUpManager.getAllEntries().forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$HeadsUpAppearanceController$1KnuQ6evxsUDWWnSk6M2Hz6dh50
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.updateHeader((NotificationData.Entry) obj);
            }
        });
    }

    public void updateHeader(NotificationData.Entry entry) {
        ExpandableNotificationRow row = entry.row;
        float headerVisibleAmount = 1.0f;
        if (row.isPinned() || row.isHeadsUpAnimatingAway() || row == this.mTrackedChild) {
            headerVisibleAmount = this.mExpandFraction;
        }
        row.setHeaderVisibleAmount(headerVisibleAmount);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        this.mHeadsUpStatusBarView.onDarkChanged(area, darkIntensity, tint);
    }

    public void setPublicMode(boolean publicMode) {
        this.mHeadsUpStatusBarView.setPublicMode(publicMode);
        updateTopEntry();
    }
}
