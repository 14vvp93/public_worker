package com.android.systemui.statusbar.phone;

import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationGroupManager implements OnHeadsUpChangedListener {
    private HeadsUpManager mHeadsUpManager;
    private boolean mIsUpdatingUnchangedGroup;
    private OnGroupChangeListener mListener;
    private HashMap<String, NotificationData.Entry> mPendingNotifications;
    private final HashMap<String, NotificationGroup> mGroupMap = new HashMap<>();
    private int mBarState = -1;
    private HashMap<String, StatusBarNotification> mIsolatedEntries = new HashMap<>();

    public interface OnGroupChangeListener {
        void onGroupCreatedFromChildren(NotificationGroup notificationGroup);

        void onGroupExpansionChanged(ExpandableNotificationRow expandableNotificationRow, boolean z);

        void onGroupsChanged();
    }

    public void setOnGroupChangeListener(OnGroupChangeListener listener) {
        this.mListener = listener;
    }

    public boolean isGroupExpanded(StatusBarNotification sbn) {
        NotificationGroup group = this.mGroupMap.get(getGroupKey(sbn));
        if (group == null) {
            return false;
        }
        return group.expanded;
    }

    public void setGroupExpanded(StatusBarNotification sbn, boolean expanded) {
        NotificationGroup group = this.mGroupMap.get(getGroupKey(sbn));
        if (group == null) {
            return;
        }
        setGroupExpanded(group, expanded);
    }

    private void setGroupExpanded(NotificationGroup group, boolean expanded) {
        group.expanded = expanded;
        if (group.summary != null) {
            this.mListener.onGroupExpansionChanged(group.summary.row, expanded);
        }
    }

    public void onEntryRemoved(NotificationData.Entry removed) {
        onEntryRemovedInternal(removed, removed.notification);
        this.mIsolatedEntries.remove(removed.key);
    }

    private void onEntryRemovedInternal(NotificationData.Entry removed, StatusBarNotification sbn) {
        String groupKey = getGroupKey(sbn);
        NotificationGroup group = this.mGroupMap.get(groupKey);
        if (group == null) {
            return;
        }
        if (isGroupChild(sbn)) {
            group.children.remove(removed.key);
        } else {
            group.summary = null;
        }
        updateSuppression(group);
        if (group.children.isEmpty() && group.summary == null) {
            this.mGroupMap.remove(groupKey);
        }
    }

    public void onEntryAdded(NotificationData.Entry added) {
        if (added.row.isRemoved()) {
            added.setDebugThrowable(new Throwable());
        }
        StatusBarNotification sbn = added.notification;
        boolean isGroupChild = isGroupChild(sbn);
        String groupKey = getGroupKey(sbn);
        NotificationGroup group = this.mGroupMap.get(groupKey);
        if (group == null) {
            group = new NotificationGroup();
            this.mGroupMap.put(groupKey, group);
        }
        if (isGroupChild) {
            NotificationData.Entry existing = group.children.get(added.key);
            if (existing != null && existing != added) {
                Throwable existingThrowable = existing.getDebugThrowable();
                StringBuilder sb = new StringBuilder();
                sb.append("Inconsistent entries found with the same key ");
                sb.append(added.key);
                sb.append("existing removed: ");
                sb.append(existing.row.isRemoved());
                sb.append(existingThrowable != null ? Log.getStackTraceString(existingThrowable) + "\n" : "");
                sb.append(" added removed");
                sb.append(added.row.isRemoved());
                Log.wtf("NotificationGroupManager", sb.toString(), new Throwable());
            }
            group.children.put(added.key, added);
            updateSuppression(group);
        } else {
            group.summary = added;
            group.expanded = added.row.areChildrenExpanded();
            updateSuppression(group);
            if (!group.children.isEmpty()) {
                ArrayList<NotificationData.Entry> childrenCopy = new ArrayList<>(group.children.values());
                for (NotificationData.Entry child : childrenCopy) {
                    onEntryBecomingChild(child);
                }
                this.mListener.onGroupCreatedFromChildren(group);
            }
        }
        cleanUpHeadsUpStatesOnAdd(group, false);
    }

    public void onPendingEntryAdded(NotificationData.Entry shadeEntry) {
        String groupKey = getGroupKey(shadeEntry.notification);
        NotificationGroup group = this.mGroupMap.get(groupKey);
        if (group != null) {
            cleanUpHeadsUpStatesOnAdd(group, true);
        }
    }

    private void cleanUpHeadsUpStatesOnAdd(NotificationGroup group, boolean addIsPending) {
        boolean notifyImmediately = false;
        if (!addIsPending && group.hunSummaryOnNextAddition) {
            if (!this.mHeadsUpManager.isHeadsUp(group.summary.key)) {
                this.mHeadsUpManager.showNotification(group.summary);
            }
            group.hunSummaryOnNextAddition = false;
        }
        if (SystemClock.elapsedRealtime() - group.lastHeadsUpTransfer >= 300 || !onlySummaryAlerts(group.summary)) {
            return;
        }
        int numChildren = group.children.size();
        NotificationData.Entry isolatedChild = getIsolatedChild(getGroupKey(group.summary.notification));
        int numPendingChildren = getPendingChildrenNotAlerting(group);
        int numChildren2 = numChildren + numPendingChildren;
        if (isolatedChild != null) {
            numChildren2++;
        }
        if (numChildren2 <= 1) {
            return;
        }
        ArrayList<NotificationData.Entry> children = new ArrayList<>(group.children.values());
        int size = children.size();
        boolean releasedChild = false;
        for (int i = 0; i < size; i++) {
            NotificationData.Entry entry = children.get(i);
            if (onlySummaryAlerts(entry) && entry.row.isHeadsUp()) {
                releasedChild = true;
                this.mHeadsUpManager.releaseImmediately(entry.key);
            }
        }
        if (isolatedChild != null && onlySummaryAlerts(isolatedChild) && isolatedChild.row.isHeadsUp()) {
            releasedChild = true;
            this.mHeadsUpManager.releaseImmediately(isolatedChild.key);
        }
        if (releasedChild && !this.mHeadsUpManager.isHeadsUp(group.summary.key)) {
            if (numChildren2 - numPendingChildren > 1) {
                notifyImmediately = true;
            }
            if (notifyImmediately) {
                this.mHeadsUpManager.showNotification(group.summary);
            } else {
                group.hunSummaryOnNextAddition = true;
            }
            group.lastHeadsUpTransfer = 0L;
        }
    }

    private int getPendingChildrenNotAlerting(NotificationGroup group) {
        if (this.mPendingNotifications == null) {
            return 0;
        }
        int number = 0;
        String groupKey = getGroupKey(group.summary.notification);
        Collection<NotificationData.Entry> values = this.mPendingNotifications.values();
        for (NotificationData.Entry entry : values) {
            if (isGroupChild(entry.notification) && Objects.equals(getGroupKey(entry.notification), groupKey) && !group.children.containsKey(entry.key) && onlySummaryAlerts(entry)) {
                number++;
            }
        }
        return number;
    }

    private void onEntryBecomingChild(NotificationData.Entry entry) {
        if (entry.row.isHeadsUp()) {
            onHeadsUpStateChanged(entry, true);
        }
    }

    private void updateSuppression(NotificationGroup group) {
        if (group == null) {
            return;
        }
        boolean prevSuppressed = group.suppressed;
        boolean z = true;
        if (group.summary == null || group.expanded || (group.children.size() != 1 && (group.children.size() != 0 || !group.summary.notification.getNotification().isGroupSummary() || !hasIsolatedChildren(group)))) {
            z = false;
        }
        group.suppressed = z;
        if (prevSuppressed != group.suppressed) {
            if (group.suppressed) {
                handleSuppressedSummaryHeadsUpped(group.summary);
            }
            if (!this.mIsUpdatingUnchangedGroup && this.mListener != null) {
                this.mListener.onGroupsChanged();
            }
        }
    }

    private boolean hasIsolatedChildren(NotificationGroup group) {
        return getNumberOfIsolatedChildren(group.summary.notification.getGroupKey()) != 0;
    }

    private int getNumberOfIsolatedChildren(String groupKey) {
        int count = 0;
        for (StatusBarNotification sbn : this.mIsolatedEntries.values()) {
            if (sbn.getGroupKey().equals(groupKey) && isIsolated(sbn)) {
                count++;
            }
        }
        return count;
    }

    private NotificationData.Entry getIsolatedChild(String groupKey) {
        for (StatusBarNotification sbn : this.mIsolatedEntries.values()) {
            if (sbn.getGroupKey().equals(groupKey) && isIsolated(sbn)) {
                return this.mGroupMap.get(sbn.getKey()).summary;
            }
        }
        return null;
    }

    public void onEntryUpdated(NotificationData.Entry entry, StatusBarNotification oldNotification) {
        String oldKey = oldNotification.getGroupKey();
        String newKey = entry.notification.getGroupKey();
        boolean groupKeysChanged = !oldKey.equals(newKey);
        boolean wasGroupChild = isGroupChild(oldNotification);
        boolean isGroupChild = isGroupChild(entry.notification);
        this.mIsUpdatingUnchangedGroup = !groupKeysChanged && wasGroupChild == isGroupChild;
        if (this.mGroupMap.get(getGroupKey(oldNotification)) != null) {
            onEntryRemovedInternal(entry, oldNotification);
        }
        onEntryAdded(entry);
        this.mIsUpdatingUnchangedGroup = false;
        if (isIsolated(entry.notification)) {
            this.mIsolatedEntries.put(entry.key, entry.notification);
            if (groupKeysChanged) {
                updateSuppression(this.mGroupMap.get(oldKey));
                updateSuppression(this.mGroupMap.get(newKey));
                return;
            }
            return;
        }
        if (!wasGroupChild && isGroupChild) {
            onEntryBecomingChild(entry);
        }
    }

    public boolean isSummaryOfSuppressedGroup(StatusBarNotification sbn) {
        return isGroupSuppressed(getGroupKey(sbn)) && sbn.getNotification().isGroupSummary();
    }

    private boolean isOnlyChild(StatusBarNotification sbn) {
        return !sbn.getNotification().isGroupSummary() && getTotalNumberOfChildren(sbn) == 1;
    }

    public boolean isOnlyChildInGroup(StatusBarNotification sbn) {
        ExpandableNotificationRow logicalGroupSummary;
        return (!isOnlyChild(sbn) || (logicalGroupSummary = getLogicalGroupSummary(sbn)) == null || logicalGroupSummary.getStatusBarNotification().equals(sbn)) ? false : true;
    }

    private int getTotalNumberOfChildren(StatusBarNotification sbn) {
        int isolatedChildren = getNumberOfIsolatedChildren(sbn.getGroupKey());
        NotificationGroup group = this.mGroupMap.get(sbn.getGroupKey());
        int realChildren = group != null ? group.children.size() : 0;
        return isolatedChildren + realChildren;
    }

    private boolean isGroupSuppressed(String groupKey) {
        NotificationGroup group = this.mGroupMap.get(groupKey);
        return group != null && group.suppressed;
    }

    public void setStatusBarState(int newState) {
        if (this.mBarState == newState) {
            return;
        }
        this.mBarState = newState;
        if (this.mBarState == 1) {
            collapseAllGroups();
        }
    }

    public void collapseAllGroups() {
        ArrayList<NotificationGroup> groupCopy = new ArrayList<>(this.mGroupMap.values());
        int size = groupCopy.size();
        for (int i = 0; i < size; i++) {
            NotificationGroup group = groupCopy.get(i);
            if (group.expanded) {
                setGroupExpanded(group, false);
            }
            updateSuppression(group);
        }
    }

    public boolean isChildInGroupWithSummary(StatusBarNotification sbn) {
        NotificationGroup group;
        return (!isGroupChild(sbn) || (group = this.mGroupMap.get(getGroupKey(sbn))) == null || group.summary == null || group.suppressed || group.children.isEmpty()) ? false : true;
    }

    public boolean isSummaryOfGroup(StatusBarNotification sbn) {
        NotificationGroup group;
        if (isGroupSummary(sbn) && (group = this.mGroupMap.get(getGroupKey(sbn))) != null) {
            return !group.children.isEmpty();
        }
        return false;
    }

    public ExpandableNotificationRow getGroupSummary(StatusBarNotification sbn) {
        return getGroupSummary(getGroupKey(sbn));
    }

    public ExpandableNotificationRow getLogicalGroupSummary(StatusBarNotification sbn) {
        return getGroupSummary(sbn.getGroupKey());
    }

    private ExpandableNotificationRow getGroupSummary(String groupKey) {
        NotificationGroup group = this.mGroupMap.get(groupKey);
        if (group == null || group.summary == null) {
            return null;
        }
        return group.summary.row;
    }

    public boolean toggleGroupExpansion(StatusBarNotification sbn) {
        NotificationGroup group = this.mGroupMap.get(getGroupKey(sbn));
        if (group == null) {
            return false;
        }
        setGroupExpanded(group, !group.expanded);
        return group.expanded;
    }

    private boolean isIsolated(StatusBarNotification sbn) {
        return this.mIsolatedEntries.containsKey(sbn.getKey());
    }

    private boolean isGroupSummary(StatusBarNotification sbn) {
        if (isIsolated(sbn)) {
            return true;
        }
        return sbn.getNotification().isGroupSummary();
    }

    private boolean isGroupChild(StatusBarNotification sbn) {
        return (isIsolated(sbn) || !sbn.isGroup() || sbn.getNotification().isGroupSummary()) ? false : true;
    }

    private String getGroupKey(StatusBarNotification sbn) {
        if (isIsolated(sbn)) {
            return sbn.getKey();
        }
        return sbn.getGroupKey();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinnedModeChanged(boolean inPinnedMode) {
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinned(ExpandableNotificationRow headsUp) {
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpUnPinned(ExpandableNotificationRow headsUp) {
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpStateChanged(NotificationData.Entry entry, boolean isHeadsUp) {
        StatusBarNotification sbn = entry.notification;
        if (entry.row.isHeadsUp()) {
            if (shouldIsolate(sbn)) {
                onEntryRemovedInternal(entry, entry.notification);
                this.mIsolatedEntries.put(sbn.getKey(), sbn);
                onEntryAdded(entry);
                updateSuppression(this.mGroupMap.get(entry.notification.getGroupKey()));
                this.mListener.onGroupsChanged();
                return;
            }
            handleSuppressedSummaryHeadsUpped(entry);
            return;
        }
        if (this.mIsolatedEntries.containsKey(sbn.getKey())) {
            onEntryRemovedInternal(entry, entry.notification);
            this.mIsolatedEntries.remove(sbn.getKey());
            onEntryAdded(entry);
            this.mListener.onGroupsChanged();
        }
    }

    private void handleSuppressedSummaryHeadsUpped(NotificationData.Entry entry) {
        StatusBarNotification sbn = entry.notification;
        if (!isGroupSuppressed(sbn.getGroupKey()) || !sbn.getNotification().isGroupSummary() || !entry.row.isHeadsUp()) {
            return;
        }
        NotificationGroup notificationGroup = this.mGroupMap.get(sbn.getGroupKey());
        if (pendingInflationsWillAddChildren(notificationGroup)) {
            return;
        }
        if (notificationGroup != null) {
            Iterator<NotificationData.Entry> iterator = notificationGroup.children.values().iterator();
            NotificationData.Entry child = iterator.hasNext() ? iterator.next() : null;
            if (child == null) {
                child = getIsolatedChild(sbn.getGroupKey());
            }
            if (child != null) {
                if (child.row.keepInParent() || child.row.isRemoved() || child.row.isDismissed()) {
                    return;
                }
                if (this.mHeadsUpManager.isHeadsUp(child.key)) {
                    this.mHeadsUpManager.updateNotification(child, true);
                } else {
                    if (onlySummaryAlerts(entry)) {
                        notificationGroup.lastHeadsUpTransfer = SystemClock.elapsedRealtime();
                    }
                    this.mHeadsUpManager.showNotification(child);
                }
            }
        }
        this.mHeadsUpManager.releaseImmediately(entry.key);
    }

    private boolean onlySummaryAlerts(NotificationData.Entry entry) {
        return entry.notification.getNotification().getGroupAlertBehavior() == 1;
    }

    private boolean pendingInflationsWillAddChildren(NotificationGroup group) {
        if (this.mPendingNotifications == null) {
            return false;
        }
        Collection<NotificationData.Entry> values = this.mPendingNotifications.values();
        String groupKey = getGroupKey(group.summary.notification);
        for (NotificationData.Entry entry : values) {
            if (isGroupChild(entry.notification) && Objects.equals(getGroupKey(entry.notification), groupKey) && !group.children.containsKey(entry.key)) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldIsolate(StatusBarNotification sbn) {
        NotificationGroup notificationGroup = this.mGroupMap.get(sbn.getGroupKey());
        return sbn.isGroup() && !sbn.getNotification().isGroupSummary() && (sbn.getNotification().fullScreenIntent != null || notificationGroup == null || !notificationGroup.expanded || isGroupNotFullyVisible(notificationGroup));
    }

    private boolean isGroupNotFullyVisible(NotificationGroup notificationGroup) {
        return notificationGroup.summary == null || notificationGroup.summary.row.getClipTopAmount() > 0 || notificationGroup.summary.row.getTranslationY() < 0.0f;
    }

    public void setHeadsUpManager(HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("GroupManager state:");
        pw.println("  number of groups: " + this.mGroupMap.size());
        for (Map.Entry<String, NotificationGroup> entry : this.mGroupMap.entrySet()) {
            pw.println("\n    key: " + entry.getKey());
            pw.println(entry.getValue());
        }
        pw.println("\n    isolated entries: " + this.mIsolatedEntries.size());
        for (Map.Entry<String, StatusBarNotification> entry2 : this.mIsolatedEntries.entrySet()) {
            pw.print("      ");
            pw.print(entry2.getKey());
            pw.print(", ");
            pw.println(entry2.getValue());
        }
    }

    public void setPendingEntries(HashMap<String, NotificationData.Entry> pendingNotifications) {
        this.mPendingNotifications = pendingNotifications;
    }

    public static class NotificationGroup {
        public final HashMap<String, NotificationData.Entry> children = new HashMap<>();
        public boolean expanded;
        public boolean hunSummaryOnNextAddition;
        public long lastHeadsUpTransfer;
        public NotificationData.Entry summary;
        public boolean suppressed;

        public String toString() {
            String stackTraceString;
            String stackTraceString2;
            StringBuilder sb = new StringBuilder();
            sb.append("    summary:\n      ");
            sb.append(this.summary != null ? this.summary.notification : "null");
            if (this.summary != null && this.summary.getDebugThrowable() != null) {
                stackTraceString = Log.getStackTraceString(this.summary.getDebugThrowable());
            } else {
                stackTraceString = "";
            }
            sb.append(stackTraceString);
            String result = sb.toString();
            String result2 = result + "\n    children size: " + this.children.size();
            for (NotificationData.Entry child : this.children.values()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(result2);
                sb2.append("\n      ");
                sb2.append(child.notification);
                if (child.getDebugThrowable() != null) {
                    stackTraceString2 = Log.getStackTraceString(child.getDebugThrowable());
                } else {
                    stackTraceString2 = "";
                }
                sb2.append(stackTraceString2);
                result2 = sb2.toString();
            }
            return result2;
        }
    }
}
