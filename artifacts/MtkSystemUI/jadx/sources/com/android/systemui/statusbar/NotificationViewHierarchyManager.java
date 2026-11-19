package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.VisualStabilityManager;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class NotificationViewHierarchyManager {
    private final boolean mAlwaysExpandNonGroupedNotification;
    private NotificationEntryManager mEntryManager;
    private NotificationListContainer mListContainer;
    private NotificationPresenter mPresenter;
    private final HashMap<ExpandableNotificationRow, List<ExpandableNotificationRow>> mTmpChildOrderMap = new HashMap<>();
    protected final NotificationLockscreenUserManager mLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class);
    protected final NotificationGroupManager mGroupManager = (NotificationGroupManager) Dependency.get(NotificationGroupManager.class);
    protected final VisualStabilityManager mVisualStabilityManager = (VisualStabilityManager) Dependency.get(VisualStabilityManager.class);

    public NotificationViewHierarchyManager(Context context) {
        Resources res = context.getResources();
        this.mAlwaysExpandNonGroupedNotification = res.getBoolean(R.bool.config_alwaysExpandNonGroupedNotifications);
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager, NotificationListContainer listContainer) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
        this.mListContainer = listContainer;
    }

    public void updateNotificationViews() {
        ArrayList<NotificationData.Entry> activeNotifications = this.mEntryManager.getNotificationData().getActiveNotifications();
        ArrayList<ExpandableNotificationRow> toShow = new ArrayList<>(activeNotifications.size());
        int N = activeNotifications.size();
        int i = 0;
        while (true) {
            if (i >= N) {
                break;
            }
            NotificationData.Entry ent = activeNotifications.get(i);
            if (!ent.row.isDismissed() && !ent.row.isRemoved()) {
                int userId = ent.notification.getUserId();
                boolean devicePublic = this.mLockscreenUserManager.isLockscreenPublicMode(this.mLockscreenUserManager.getCurrentUserId());
                boolean userPublic = devicePublic || this.mLockscreenUserManager.isLockscreenPublicMode(userId);
                boolean needsRedaction = this.mLockscreenUserManager.needsRedaction(ent);
                boolean sensitive = userPublic && needsRedaction;
                boolean deviceSensitive = devicePublic && !this.mLockscreenUserManager.userAllowsPrivateNotificationsInPublic(this.mLockscreenUserManager.getCurrentUserId());
                ent.row.setSensitive(sensitive, deviceSensitive);
                ent.row.setNeedsRedaction(needsRedaction);
                if (this.mGroupManager.isChildInGroupWithSummary(ent.row.getStatusBarNotification())) {
                    ExpandableNotificationRow summary = this.mGroupManager.getGroupSummary(ent.row.getStatusBarNotification());
                    List<ExpandableNotificationRow> orderedChildren = this.mTmpChildOrderMap.get(summary);
                    if (orderedChildren == null) {
                        orderedChildren = new ArrayList();
                        this.mTmpChildOrderMap.put(summary, orderedChildren);
                    }
                    orderedChildren.add(ent.row);
                } else {
                    toShow.add(ent.row);
                }
            }
            i++;
        }
        ArrayList<ExpandableNotificationRow> viewsToRemove = new ArrayList<>();
        for (int i2 = 0; i2 < this.mListContainer.getContainerChildCount(); i2++) {
            View child = this.mListContainer.getContainerChildAt(i2);
            if (!toShow.contains(child) && (child instanceof ExpandableNotificationRow)) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                if (!row.isBlockingHelperShowing()) {
                    viewsToRemove.add((ExpandableNotificationRow) child);
                }
            }
        }
        for (ExpandableNotificationRow viewToRemove : viewsToRemove) {
            if (this.mGroupManager.isChildInGroupWithSummary(viewToRemove.getStatusBarNotification())) {
                this.mListContainer.setChildTransferInProgress(true);
            }
            if (viewToRemove.isSummaryWithChildren()) {
                viewToRemove.removeAllChildren();
            }
            this.mListContainer.removeContainerView(viewToRemove);
            this.mListContainer.setChildTransferInProgress(false);
        }
        removeNotificationChildren();
        for (int i3 = 0; i3 < toShow.size(); i3++) {
            ExpandableNotificationRow v = toShow.get(i3);
            if (v.getParent() == null) {
                this.mVisualStabilityManager.notifyViewAddition(v);
                this.mListContainer.addContainerView(v);
            }
        }
        addNotificationChildrenAndSort();
        int j = 0;
        for (int i4 = 0; i4 < this.mListContainer.getContainerChildCount(); i4++) {
            View child2 = this.mListContainer.getContainerChildAt(i4);
            if ((child2 instanceof ExpandableNotificationRow) && !((ExpandableNotificationRow) child2).isBlockingHelperShowing()) {
                ExpandableNotificationRow targetChild = toShow.get(j);
                if (child2 != targetChild) {
                    if (this.mVisualStabilityManager.canReorderNotification(targetChild)) {
                        this.mListContainer.changeViewPosition(targetChild, i4);
                    } else {
                        this.mVisualStabilityManager.addReorderingAllowedCallback(this.mEntryManager);
                    }
                }
                j++;
            }
        }
        this.mVisualStabilityManager.onReorderingFinished();
        this.mTmpChildOrderMap.clear();
        updateRowStates();
        this.mListContainer.onNotificationViewUpdateFinished();
    }

    private void addNotificationChildrenAndSort() {
        boolean orderChanged = false;
        for (int i = 0; i < this.mListContainer.getContainerChildCount(); i++) {
            View view = this.mListContainer.getContainerChildAt(i);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow parent = (ExpandableNotificationRow) view;
                List<ExpandableNotificationRow> children = parent.getNotificationChildren();
                List<ExpandableNotificationRow> orderedChildren = this.mTmpChildOrderMap.get(parent);
                for (int childIndex = 0; orderedChildren != null && childIndex < orderedChildren.size(); childIndex++) {
                    ExpandableNotificationRow childView = orderedChildren.get(childIndex);
                    if (children == null || !children.contains(childView)) {
                        if (childView.getParent() != null) {
                            Log.wtf("NotificationViewHierarchyManager", "trying to add a notification child that already has a parent. class:" + childView.getParent().getClass() + "\n child: " + childView);
                            ((ViewGroup) childView.getParent()).removeView(childView);
                        }
                        this.mVisualStabilityManager.notifyViewAddition(childView);
                        parent.addChildNotification(childView, childIndex);
                        this.mListContainer.notifyGroupChildAdded(childView);
                    }
                }
                orderChanged |= parent.applyChildOrder(orderedChildren, this.mVisualStabilityManager, this.mEntryManager);
            }
        }
        if (orderChanged) {
            this.mListContainer.generateChildOrderChangedEvent();
        }
    }

    private void removeNotificationChildren() {
        ArrayList<ExpandableNotificationRow> toRemove = new ArrayList<>();
        for (int i = 0; i < this.mListContainer.getContainerChildCount(); i++) {
            View view = this.mListContainer.getContainerChildAt(i);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow parent = (ExpandableNotificationRow) view;
                List<ExpandableNotificationRow> children = parent.getNotificationChildren();
                List<ExpandableNotificationRow> orderedChildren = this.mTmpChildOrderMap.get(parent);
                if (children != null) {
                    toRemove.clear();
                    for (ExpandableNotificationRow childRow : children) {
                        if (orderedChildren == null || !orderedChildren.contains(childRow)) {
                            if (!childRow.keepInParent()) {
                                toRemove.add(childRow);
                            }
                        }
                    }
                    for (ExpandableNotificationRow remove : toRemove) {
                        parent.removeChildNotification(remove);
                        if (this.mEntryManager.getNotificationData().get(remove.getStatusBarNotification().getKey()) == null) {
                            this.mListContainer.notifyGroupChildRemoved(remove, parent.getChildrenContainer());
                        }
                    }
                }
            }
        }
    }

    public void updateRowStates() {
        Trace.beginSection("NotificationViewHierarchyManager#updateRowStates");
        int N = this.mListContainer.getContainerChildCount();
        int visibleNotifications = 0;
        boolean isLocked = this.mPresenter.isPresenterLocked();
        int maxNotifications = -1;
        boolean z = true;
        if (isLocked) {
            maxNotifications = this.mPresenter.getMaxNotificationsWhileLocked(true);
        }
        this.mListContainer.setMaxDisplayedNotifications(maxNotifications);
        Stack<ExpandableNotificationRow> stack = new Stack<>();
        for (int i = N - 1; i >= 0; i--) {
            View child = this.mListContainer.getContainerChildAt(i);
            if (child instanceof ExpandableNotificationRow) {
                stack.push((ExpandableNotificationRow) child);
            }
        }
        while (!stack.isEmpty()) {
            ExpandableNotificationRow row = stack.pop();
            NotificationData.Entry entry = row.getEntry();
            boolean isChildNotification = this.mGroupManager.isChildInGroupWithSummary(entry.notification);
            row.setOnKeyguard(isLocked);
            if (!isLocked) {
                row.setSystemExpanded((this.mAlwaysExpandNonGroupedNotification || !(visibleNotifications != 0 || isChildNotification || row.isLowPriority())) ? z : false);
            }
            entry.row.setShowAmbient(this.mPresenter.isDozing());
            int userId = entry.notification.getUserId();
            boolean suppressedSummary = (!this.mGroupManager.isSummaryOfSuppressedGroup(entry.notification) || entry.row.isRemoved()) ? false : z;
            boolean showOnKeyguard = this.mLockscreenUserManager.shouldShowOnKeyguard(entry.notification);
            if (suppressedSummary || this.mLockscreenUserManager.shouldHideNotifications(userId) || (isLocked && !showOnKeyguard)) {
                entry.row.setVisibility(8);
            } else {
                boolean wasGone = entry.row.getVisibility() == 8 ? z : false;
                if (wasGone) {
                    entry.row.setVisibility(0);
                }
                if (!isChildNotification && !entry.row.isRemoved()) {
                    if (wasGone) {
                        this.mListContainer.generateAddAnimation(entry.row, !showOnKeyguard);
                    }
                    visibleNotifications++;
                }
            }
            if (row.isSummaryWithChildren()) {
                List<ExpandableNotificationRow> notificationChildren = row.getNotificationChildren();
                int size = notificationChildren.size();
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    stack.push(notificationChildren.get(i2));
                }
            }
            row.showAppOpsIcons(entry.mActiveAppOps);
            z = true;
        }
        Trace.beginSection("NotificationPresenter#onUpdateRowStates");
        this.mPresenter.onUpdateRowStates();
        Trace.endSection();
        Trace.endSection();
    }
}
