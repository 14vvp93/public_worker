package com.android.systemui.statusbar;

import android.R;
import android.content.Context;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class NotificationBlockingHelperManager {
    private ExpandableNotificationRow mBlockingHelperRow;
    private final Context mContext;
    private boolean mIsShadeExpanded;
    private Set<String> mNonBlockablePkgs = new HashSet();

    public NotificationBlockingHelperManager(Context context) {
        this.mContext = context;
        Collections.addAll(this.mNonBlockablePkgs, this.mContext.getResources().getStringArray(R.array.config_concurrentDisplayDeviceStates));
    }

    boolean perhapsShowBlockingHelper(ExpandableNotificationRow row, NotificationMenuRowPlugin menuRow) {
        if (row.getEntry().userSentiment != -1 || !this.mIsShadeExpanded || row.getIsNonblockable() || (row.isChildInGroup() && !row.isOnlyChildInGroup())) {
            return false;
        }
        dismissCurrentBlockingHelper();
        NotificationGutsManager manager = (NotificationGutsManager) Dependency.get(NotificationGutsManager.class);
        this.mBlockingHelperRow = row;
        this.mBlockingHelperRow.setBlockingHelperShowing(true);
        manager.openGuts(this.mBlockingHelperRow, 0, 0, menuRow.getLongpressMenuItem(this.mContext));
        ((MetricsLogger) Dependency.get(MetricsLogger.class)).count("blocking_helper_shown", 1);
        return true;
    }

    boolean dismissCurrentBlockingHelper() {
        if (isBlockingHelperRowNull()) {
            return false;
        }
        if (!this.mBlockingHelperRow.isBlockingHelperShowing()) {
            Log.e("BlockingHelper", "Manager.dismissCurrentBlockingHelper: Non-null row is not showing a blocking helper");
        }
        this.mBlockingHelperRow.setBlockingHelperShowing(false);
        if (this.mBlockingHelperRow.isAttachedToWindow()) {
            ((NotificationEntryManager) Dependency.get(NotificationEntryManager.class)).updateNotifications();
        }
        this.mBlockingHelperRow = null;
        return true;
    }

    public void setNotificationShadeExpanded(float expandedHeight) {
        this.mIsShadeExpanded = expandedHeight > 0.0f;
    }

    public boolean isNonblockablePackage(String packageName) {
        return this.mNonBlockablePkgs.contains(packageName);
    }

    boolean isBlockingHelperRowNull() {
        return this.mBlockingHelperRow == null;
    }

    void setBlockingHelperRowForTest(ExpandableNotificationRow blockingHelperRowForTest) {
        this.mBlockingHelperRow = blockingHelperRowForTest;
    }
}
