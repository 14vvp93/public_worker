package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.util.ArraySet;
import android.util.Pools;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.notification.VisualStabilityManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpManagerPhone extends HeadsUpManager implements ViewTreeObserver.OnComputeInternalInsetsListener, Dumpable, VisualStabilityManager.Callback, ConfigurationController.ConfigurationListener, OnHeadsUpChangedListener {
    private final StatusBar mBar;
    private HashSet<NotificationData.Entry> mEntriesToRemoveAfterExpand;
    private ArraySet<NotificationData.Entry> mEntriesToRemoveWhenReorderingAllowed;
    private final Pools.Pool<HeadsUpEntryPhone> mEntryPool;
    private final NotificationGroupManager mGroupManager;
    private boolean mHeadsUpGoingAway;
    private int mHeadsUpInset;
    private boolean mIsExpanded;
    private boolean mIsObserving;
    private boolean mReleaseOnExpandFinish;
    private int mStatusBarHeight;
    private int mStatusBarState;
    private final View mStatusBarWindowView;
    private HashSet<String> mSwipedOutKeys;
    private int[] mTmpTwoArray;
    private boolean mTrackingHeadsUp;
    private final VisualStabilityManager mVisualStabilityManager;
    private boolean mWaitingOnCollapseWhenGoingAway;

    public HeadsUpManagerPhone(Context context, View statusBarWindowView, NotificationGroupManager groupManager, StatusBar bar, VisualStabilityManager visualStabilityManager) {
        super(context);
        this.mSwipedOutKeys = new HashSet<>();
        this.mEntriesToRemoveAfterExpand = new HashSet<>();
        this.mEntriesToRemoveWhenReorderingAllowed = new ArraySet<>();
        this.mTmpTwoArray = new int[2];
        this.mEntryPool = new Pools.Pool<HeadsUpEntryPhone>() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.1
            private Stack<HeadsUpEntryPhone> mPoolObjects = new Stack<>();

            /* JADX INFO: renamed from: acquire, reason: merged with bridge method [inline-methods] */
            public HeadsUpEntryPhone m19acquire() {
                if (!this.mPoolObjects.isEmpty()) {
                    return this.mPoolObjects.pop();
                }
                return HeadsUpManagerPhone.this.new HeadsUpEntryPhone();
            }

            public boolean release(HeadsUpEntryPhone instance) {
                this.mPoolObjects.push(instance);
                return true;
            }
        };
        this.mStatusBarWindowView = statusBarWindowView;
        this.mGroupManager = groupManager;
        this.mBar = bar;
        this.mVisualStabilityManager = visualStabilityManager;
        initResources();
        addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.2
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public void onHeadsUpPinnedModeChanged(boolean hasPinnedNotification) {
                HeadsUpManagerPhone.this.updateTouchableRegionListener();
            }
        });
    }

    private void initResources() {
        Resources resources = this.mContext.getResources();
        this.mStatusBarHeight = resources.getDimensionPixelSize(R.dimen.handwriting_bounds_offset_right);
        this.mHeadsUpInset = this.mStatusBarHeight + resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_status_bar_padding);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        super.onDensityOrFontScaleChanged();
        initResources();
    }

    public boolean shouldSwallowClick(String key) {
        HeadsUpManager.HeadsUpEntry entry = getHeadsUpEntry(key);
        return entry != null && this.mClock.currentTimeMillis() < entry.postTime;
    }

    public void onExpandingFinished() {
        if (this.mReleaseOnExpandFinish) {
            releaseAllImmediately();
            this.mReleaseOnExpandFinish = false;
        } else {
            for (NotificationData.Entry entry : this.mEntriesToRemoveAfterExpand) {
                if (isHeadsUp(entry.key)) {
                    removeHeadsUpEntry(entry);
                }
            }
        }
        this.mEntriesToRemoveAfterExpand.clear();
    }

    public void setTrackingHeadsUp(boolean trackingHeadsUp) {
        this.mTrackingHeadsUp = trackingHeadsUp;
    }

    public void setIsPanelExpanded(boolean isExpanded) {
        if (isExpanded != this.mIsExpanded) {
            this.mIsExpanded = isExpanded;
            if (isExpanded) {
                this.mWaitingOnCollapseWhenGoingAway = false;
                this.mHeadsUpGoingAway = false;
                updateTouchableRegionListener();
            }
        }
    }

    public void setStatusBarState(int statusBarState) {
        this.mStatusBarState = statusBarState;
    }

    public void setHeadsUpGoingAway(boolean headsUpGoingAway) {
        if (headsUpGoingAway != this.mHeadsUpGoingAway) {
            this.mHeadsUpGoingAway = headsUpGoingAway;
            if (!headsUpGoingAway) {
                waitForStatusBarLayout();
            }
            updateTouchableRegionListener();
        }
    }

    public void setRemoteInputActive(NotificationData.Entry entry, boolean remoteInputActive) {
        HeadsUpEntryPhone headsUpEntry = getHeadsUpEntryPhone(entry.key);
        if (headsUpEntry != null && headsUpEntry.remoteInputActive != remoteInputActive) {
            headsUpEntry.remoteInputActive = remoteInputActive;
            if (remoteInputActive) {
                headsUpEntry.removeAutoRemovalCallbacks();
            } else {
                headsUpEntry.updateEntry(false);
            }
        }
    }

    @VisibleForTesting
    public void removeMinimumDisplayTimeForTesting() {
        this.mMinimumDisplayTime = 0;
        this.mHeadsUpNotificationDecay = 0;
        this.mTouchAcceptanceDelay = 0;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isTrackingHeadsUp() {
        return this.mTrackingHeadsUp;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void snooze() {
        super.snooze();
        this.mReleaseOnExpandFinish = true;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean removeNotification(String key, boolean ignoreEarliestRemovalTime) {
        if (wasShownLongEnough(key) || ignoreEarliestRemovalTime) {
            return super.removeNotification(key, ignoreEarliestRemovalTime);
        }
        HeadsUpEntryPhone entry = getHeadsUpEntryPhone(key);
        entry.removeAsSoonAsPossible();
        return false;
    }

    public void addSwipedOutNotification(String key) {
        this.mSwipedOutKeys.add(key);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager, com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("HeadsUpManagerPhone state:");
        dumpInternal(fd, pw, args);
    }

    public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo info) {
        ExpandableNotificationRow groupSummary;
        if (this.mIsExpanded || this.mBar.isBouncerShowing()) {
            return;
        }
        if (!hasPinnedHeadsUp()) {
            if (this.mHeadsUpGoingAway || this.mWaitingOnCollapseWhenGoingAway) {
                info.setTouchableInsets(3);
                info.touchableRegion.set(0, 0, this.mStatusBarWindowView.getWidth(), this.mStatusBarHeight);
                return;
            }
            return;
        }
        ExpandableNotificationRow topEntry = getTopEntry().row;
        if (topEntry.isChildInGroup() && (groupSummary = this.mGroupManager.getGroupSummary(topEntry.getStatusBarNotification())) != null) {
            topEntry = groupSummary;
        }
        topEntry.getLocationOnScreen(this.mTmpTwoArray);
        int minX = this.mTmpTwoArray[0];
        int maxX = this.mTmpTwoArray[0] + topEntry.getWidth();
        int height = topEntry.getIntrinsicHeight();
        info.setTouchableInsets(3);
        info.touchableRegion.set(minX, 0, maxX, this.mHeadsUpInset + height);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration newConfig) {
        Resources resources = this.mContext.getResources();
        this.mStatusBarHeight = resources.getDimensionPixelSize(R.dimen.handwriting_bounds_offset_right);
    }

    @Override // com.android.systemui.statusbar.notification.VisualStabilityManager.Callback
    public void onReorderingAllowed() {
        this.mBar.getNotificationScrollLayout().setHeadsUpGoingAwayAnimationsAllowed(false);
        for (NotificationData.Entry entry : this.mEntriesToRemoveWhenReorderingAllowed) {
            if (isHeadsUp(entry.key)) {
                removeHeadsUpEntry(entry);
            }
        }
        this.mEntriesToRemoveWhenReorderingAllowed.clear();
        this.mBar.getNotificationScrollLayout().setHeadsUpGoingAwayAnimationsAllowed(true);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    protected HeadsUpManager.HeadsUpEntry createHeadsUpEntry() {
        return (HeadsUpManager.HeadsUpEntry) this.mEntryPool.acquire();
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    protected void releaseHeadsUpEntry(HeadsUpManager.HeadsUpEntry entry) {
        entry.reset();
        this.mEntryPool.release((HeadsUpEntryPhone) entry);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    protected boolean shouldHeadsUpBecomePinned(NotificationData.Entry entry) {
        return !(this.mStatusBarState == 1 || this.mIsExpanded) || super.shouldHeadsUpBecomePinned(entry);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    protected void dumpInternal(FileDescriptor fd, PrintWriter pw, String[] args) {
        super.dumpInternal(fd, pw, args);
        pw.print("  mStatusBarState=");
        pw.println(this.mStatusBarState);
    }

    private HeadsUpEntryPhone getHeadsUpEntryPhone(String key) {
        return (HeadsUpEntryPhone) getHeadsUpEntry(key);
    }

    private HeadsUpEntryPhone getTopHeadsUpEntryPhone() {
        return (HeadsUpEntryPhone) getTopHeadsUpEntry();
    }

    private boolean wasShownLongEnough(String key) {
        if (this.mSwipedOutKeys.contains(key)) {
            this.mSwipedOutKeys.remove(key);
            return true;
        }
        HeadsUpEntryPhone headsUpEntry = getHeadsUpEntryPhone(key);
        HeadsUpEntryPhone topEntry = getTopHeadsUpEntryPhone();
        return headsUpEntry != topEntry || headsUpEntry.wasShownLongEnough();
    }

    private void waitForStatusBarLayout() {
        this.mWaitingOnCollapseWhenGoingAway = true;
        this.mStatusBarWindowView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (HeadsUpManagerPhone.this.mStatusBarWindowView.getHeight() <= HeadsUpManagerPhone.this.mStatusBarHeight) {
                    HeadsUpManagerPhone.this.mStatusBarWindowView.removeOnLayoutChangeListener(this);
                    HeadsUpManagerPhone.this.mWaitingOnCollapseWhenGoingAway = false;
                    HeadsUpManagerPhone.this.updateTouchableRegionListener();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTouchableRegionListener() {
        boolean shouldObserve = hasPinnedHeadsUp() || this.mHeadsUpGoingAway || this.mWaitingOnCollapseWhenGoingAway;
        if (shouldObserve == this.mIsObserving) {
            return;
        }
        if (shouldObserve) {
            this.mStatusBarWindowView.getViewTreeObserver().addOnComputeInternalInsetsListener(this);
            this.mStatusBarWindowView.requestLayout();
        } else {
            this.mStatusBarWindowView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        }
        this.mIsObserving = shouldObserve;
    }

    protected class HeadsUpEntryPhone extends HeadsUpManager.HeadsUpEntry {
        protected HeadsUpEntryPhone() {
            super();
        }

        @Override // com.android.systemui.statusbar.policy.HeadsUpManager.HeadsUpEntry
        public void setEntry(final NotificationData.Entry entry) {
            Runnable removeHeadsUpRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$HeadsUpManagerPhone$HeadsUpEntryPhone$5mHSyZcdvhRZee7SQo_tx2_2-Bg
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpManagerPhone.HeadsUpEntryPhone.lambda$setEntry$0(this.f$0, entry);
                }
            };
            super.setEntry(entry, removeHeadsUpRunnable);
        }

        public static /* synthetic */ void lambda$setEntry$0(HeadsUpEntryPhone headsUpEntryPhone, NotificationData.Entry entry) {
            if (!HeadsUpManagerPhone.this.mVisualStabilityManager.isReorderingAllowed()) {
                HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.add(entry);
                HeadsUpManagerPhone.this.mVisualStabilityManager.addReorderingAllowedCallback(HeadsUpManagerPhone.this);
            } else if (!HeadsUpManagerPhone.this.mTrackingHeadsUp) {
                HeadsUpManagerPhone.this.removeHeadsUpEntry(entry);
            } else {
                HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.add(entry);
            }
        }

        public boolean wasShownLongEnough() {
            return this.earliestRemovaltime < HeadsUpManagerPhone.this.mClock.currentTimeMillis();
        }

        @Override // com.android.systemui.statusbar.policy.HeadsUpManager.HeadsUpEntry
        public void updateEntry(boolean updatePostTime) {
            super.updateEntry(updatePostTime);
            if (HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.contains(this.entry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.remove(this.entry);
            }
            if (HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.contains(this.entry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.remove(this.entry);
            }
        }

        @Override // com.android.systemui.statusbar.policy.HeadsUpManager.HeadsUpEntry
        public void expanded(boolean expanded) {
            if (this.expanded == expanded) {
                return;
            }
            this.expanded = expanded;
            if (expanded) {
                removeAutoRemovalCallbacks();
            } else {
                updateEntry(false);
            }
        }
    }
}
