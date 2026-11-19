package com.android.systemui.statusbar.stack;

import android.content.Context;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.ActivatableNotificationView;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.ArrayList;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class AmbientState {
    private ActivatableNotificationView mActivatedChild;
    private boolean mAppearing;
    private int mBaseZHeight;
    private float mCurrentScrollVelocity;
    private boolean mDark;
    private float mDarkAmount;
    private int mDarkTopPadding;
    private boolean mDimmed;
    private boolean mDismissAllInProgress;
    private int mExpandAnimationTopChange;
    private ExpandableNotificationRow mExpandingNotification;
    private float mExpandingVelocity;
    private boolean mExpansionChanging;
    private HeadsUpManager mHeadsUpManager;
    private boolean mHideSensitive;
    private int mIntrinsicPadding;
    private ActivatableNotificationView mLastVisibleBackgroundChild;
    private int mLayoutHeight;
    private int mLayoutMinHeight;
    private float mMaxHeadsUpTranslation;
    private int mMaxLayoutHeight;
    private float mOverScrollBottomAmount;
    private float mOverScrollTopAmount;
    private boolean mPanelFullWidth;
    private boolean mPanelTracking;
    private boolean mPulsing;
    private boolean mQsCustomizerShowing;
    private int mScrollY;
    private boolean mShadeExpanded;
    private NotificationShelf mShelf;
    private float mStackTranslation;
    private int mStatusBarState;
    private int mTopPadding;
    private boolean mUnlockHintRunning;
    private int mZDistanceBetweenElements;
    private ArrayList<View> mDraggedViews = new ArrayList<>();
    private int mSpeedBumpIndex = -1;

    public AmbientState(Context context) {
        reload(context);
    }

    public void reload(Context context) {
        this.mZDistanceBetweenElements = getZDistanceBetweenElements(context);
        this.mBaseZHeight = getBaseHeight(this.mZDistanceBetweenElements);
    }

    private static int getZDistanceBetweenElements(Context context) {
        return Math.max(1, context.getResources().getDimensionPixelSize(R.dimen.z_distance_between_notifications));
    }

    private static int getBaseHeight(int zdistanceBetweenElements) {
        return 4 * zdistanceBetweenElements;
    }

    public static int getNotificationLaunchHeight(Context context) {
        int zDistance = getZDistanceBetweenElements(context);
        return getBaseHeight(zDistance) * 2;
    }

    public int getBaseZHeight() {
        return this.mBaseZHeight;
    }

    public int getZDistanceBetweenElements() {
        return this.mZDistanceBetweenElements;
    }

    public int getScrollY() {
        return this.mScrollY;
    }

    public void setScrollY(int scrollY) {
        this.mScrollY = scrollY;
    }

    public void onBeginDrag(View view) {
        this.mDraggedViews.add(view);
    }

    public void onDragFinished(View view) {
        this.mDraggedViews.remove(view);
    }

    public ArrayList<View> getDraggedViews() {
        return this.mDraggedViews;
    }

    public void setDimmed(boolean dimmed) {
        this.mDimmed = dimmed;
    }

    public void setDark(boolean dark) {
        this.mDark = dark;
    }

    public void setDarkAmount(float darkAmount) {
        this.mDarkAmount = darkAmount;
    }

    public float getDarkAmount() {
        return this.mDarkAmount;
    }

    public void setHideSensitive(boolean hideSensitive) {
        this.mHideSensitive = hideSensitive;
    }

    public void setActivatedChild(ActivatableNotificationView activatedChild) {
        this.mActivatedChild = activatedChild;
    }

    public boolean isDimmed() {
        return this.mDimmed;
    }

    public boolean isDark() {
        return this.mDark;
    }

    public boolean isHideSensitive() {
        return this.mHideSensitive;
    }

    public ActivatableNotificationView getActivatedChild() {
        return this.mActivatedChild;
    }

    public void setOverScrollAmount(float amount, boolean onTop) {
        if (onTop) {
            this.mOverScrollTopAmount = amount;
        } else {
            this.mOverScrollBottomAmount = amount;
        }
    }

    public float getOverScrollAmount(boolean top) {
        return top ? this.mOverScrollTopAmount : this.mOverScrollBottomAmount;
    }

    public int getSpeedBumpIndex() {
        return this.mSpeedBumpIndex;
    }

    public void setSpeedBumpIndex(int shelfIndex) {
        this.mSpeedBumpIndex = shelfIndex;
    }

    public void setHeadsUpManager(HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
    }

    public float getStackTranslation() {
        return this.mStackTranslation;
    }

    public void setStackTranslation(float stackTranslation) {
        this.mStackTranslation = stackTranslation;
    }

    public void setLayoutHeight(int layoutHeight) {
        this.mLayoutHeight = layoutHeight;
    }

    public float getTopPadding() {
        return this.mTopPadding;
    }

    public void setTopPadding(int topPadding) {
        this.mTopPadding = topPadding;
    }

    public int getInnerHeight() {
        return Math.max(Math.min(this.mLayoutHeight, this.mMaxLayoutHeight) - this.mTopPadding, this.mLayoutMinHeight);
    }

    public boolean isShadeExpanded() {
        return this.mShadeExpanded;
    }

    public void setShadeExpanded(boolean shadeExpanded) {
        this.mShadeExpanded = shadeExpanded;
    }

    public void setMaxHeadsUpTranslation(float maxHeadsUpTranslation) {
        this.mMaxHeadsUpTranslation = maxHeadsUpTranslation;
    }

    public float getMaxHeadsUpTranslation() {
        return this.mMaxHeadsUpTranslation;
    }

    public void setDismissAllInProgress(boolean dismissAllInProgress) {
        this.mDismissAllInProgress = dismissAllInProgress;
    }

    public void setLayoutMinHeight(int layoutMinHeight) {
        this.mLayoutMinHeight = layoutMinHeight;
    }

    public void setShelf(NotificationShelf shelf) {
        this.mShelf = shelf;
    }

    public NotificationShelf getShelf() {
        return this.mShelf;
    }

    public void setLayoutMaxHeight(int maxLayoutHeight) {
        this.mMaxLayoutHeight = maxLayoutHeight;
    }

    public void setLastVisibleBackgroundChild(ActivatableNotificationView lastVisibleBackgroundChild) {
        this.mLastVisibleBackgroundChild = lastVisibleBackgroundChild;
    }

    public ActivatableNotificationView getLastVisibleBackgroundChild() {
        return this.mLastVisibleBackgroundChild;
    }

    public void setCurrentScrollVelocity(float currentScrollVelocity) {
        this.mCurrentScrollVelocity = currentScrollVelocity;
    }

    public float getCurrentScrollVelocity() {
        return this.mCurrentScrollVelocity;
    }

    public boolean isOnKeyguard() {
        return this.mStatusBarState == 1;
    }

    public void setStatusBarState(int statusBarState) {
        this.mStatusBarState = statusBarState;
    }

    public void setExpandingVelocity(float expandingVelocity) {
        this.mExpandingVelocity = expandingVelocity;
    }

    public void setExpansionChanging(boolean expansionChanging) {
        this.mExpansionChanging = expansionChanging;
    }

    public boolean isExpansionChanging() {
        return this.mExpansionChanging;
    }

    public float getExpandingVelocity() {
        return this.mExpandingVelocity;
    }

    public void setPanelTracking(boolean panelTracking) {
        this.mPanelTracking = panelTracking;
    }

    public boolean hasPulsingNotifications() {
        return this.mPulsing;
    }

    public void setPulsing(boolean hasPulsing) {
        this.mPulsing = hasPulsing;
    }

    public boolean isPulsing(final NotificationData.Entry entry) {
        if (!this.mPulsing || this.mHeadsUpManager == null) {
            return false;
        }
        return this.mHeadsUpManager.getAllEntries().anyMatch(new Predicate() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$AmbientState$05gi9X6uLduWjWHWb7e4F7kHS2o
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AmbientState.lambda$isPulsing$0(entry, (NotificationData.Entry) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$isPulsing$0(NotificationData.Entry entry, NotificationData.Entry e) {
        return e == entry;
    }

    public boolean isPanelTracking() {
        return this.mPanelTracking;
    }

    public boolean isPanelFullWidth() {
        return this.mPanelFullWidth;
    }

    public void setPanelFullWidth(boolean panelFullWidth) {
        this.mPanelFullWidth = panelFullWidth;
    }

    public void setUnlockHintRunning(boolean unlockHintRunning) {
        this.mUnlockHintRunning = unlockHintRunning;
    }

    public boolean isUnlockHintRunning() {
        return this.mUnlockHintRunning;
    }

    public boolean isQsCustomizerShowing() {
        return this.mQsCustomizerShowing;
    }

    public void setQsCustomizerShowing(boolean qsCustomizerShowing) {
        this.mQsCustomizerShowing = qsCustomizerShowing;
    }

    public void setIntrinsicPadding(int intrinsicPadding) {
        this.mIntrinsicPadding = intrinsicPadding;
    }

    public int getIntrinsicPadding() {
        return this.mIntrinsicPadding;
    }

    public boolean isAboveShelf(ExpandableView expandableView) {
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return expandableView.isAboveShelf();
        }
        ExpandableNotificationRow row = (ExpandableNotificationRow) expandableView;
        return row.isAboveShelf() && !isDozingAndNotPulsing(row);
    }

    public boolean isDozingAndNotPulsing(ExpandableView view) {
        if (view instanceof ExpandableNotificationRow) {
            return isDozingAndNotPulsing((ExpandableNotificationRow) view);
        }
        return false;
    }

    public boolean isDozingAndNotPulsing(ExpandableNotificationRow row) {
        return isDark() && !isPulsing(row.getEntry());
    }

    public void setExpandAnimationTopChange(int expandAnimationTopChange) {
        this.mExpandAnimationTopChange = expandAnimationTopChange;
    }

    public void setExpandingNotification(ExpandableNotificationRow row) {
        this.mExpandingNotification = row;
    }

    public ExpandableNotificationRow getExpandingNotification() {
        return this.mExpandingNotification;
    }

    public int getExpandAnimationTopChange() {
        return this.mExpandAnimationTopChange;
    }

    public boolean isFullyDark() {
        return this.mDarkAmount == 1.0f;
    }

    public void setDarkTopPadding(int darkTopPadding) {
        this.mDarkTopPadding = darkTopPadding;
    }

    public int getDarkTopPadding() {
        return this.mDarkTopPadding;
    }

    public void setAppearing(boolean appearing) {
        this.mAppearing = appearing;
    }

    public boolean isAppearing() {
        return this.mAppearing;
    }
}
