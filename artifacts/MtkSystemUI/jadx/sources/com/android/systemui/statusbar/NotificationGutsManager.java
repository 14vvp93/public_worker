package com.android.systemui.statusbar;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.phone.StatusBar;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationGutsManager implements Dumpable {
    private final AccessibilityManager mAccessibilityManager;
    private NotificationInfo.CheckSaveListener mCheckSaveListener;
    private final Context mContext;
    protected NotificationEntryManager mEntryManager;
    private NotificationMenuRowPlugin.MenuItem mGutsMenuItem;
    private String mKeyToRemoveOnGutsClosed;
    private NotificationListContainer mListContainer;
    private NotificationGuts mNotificationGutsExposed;
    private OnSettingsClickListener mOnSettingsClickListener;
    protected NotificationPresenter mPresenter;
    private final MetricsLogger mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
    private final NotificationLockscreenUserManager mLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class);

    public interface OnSettingsClickListener {
        void onClick(String str);
    }

    public NotificationGutsManager(Context context) {
        this.mContext = context;
        context.getResources();
        this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager, NotificationListContainer listContainer, NotificationInfo.CheckSaveListener checkSaveListener, OnSettingsClickListener onSettingsClickListener) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
        this.mListContainer = listContainer;
        this.mCheckSaveListener = checkSaveListener;
        this.mOnSettingsClickListener = onSettingsClickListener;
    }

    public String getKeyToRemoveOnGutsClosed() {
        return this.mKeyToRemoveOnGutsClosed;
    }

    public void setKeyToRemoveOnGutsClosed(String keyToRemoveOnGutsClosed) {
        this.mKeyToRemoveOnGutsClosed = keyToRemoveOnGutsClosed;
    }

    public void onDensityOrFontScaleChanged(ExpandableNotificationRow row) {
        setExposedGuts(row.getGuts());
        bindGuts(row);
    }

    private void startAppNotificationSettingsActivity(String packageName, int appUid, NotificationChannel channel, ExpandableNotificationRow row) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", packageName, null));
        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
        intent.putExtra("app_uid", appUid);
        if (channel != null) {
            intent.putExtra(":settings:fragment_args_key", channel.getId());
        }
        this.mPresenter.startNotificationGutsIntent(intent, appUid, row);
    }

    protected void startAppOpsSettingsActivity(String pkg, int uid, ArraySet<Integer> ops, ExpandableNotificationRow row) {
        if (ops.contains(24)) {
            if (ops.contains(26) || ops.contains(27)) {
                startAppNotificationSettingsActivity(pkg, uid, null, row);
                return;
            }
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.fromParts("package", pkg, null));
            this.mPresenter.startNotificationGutsIntent(intent, uid, row);
            return;
        }
        if (ops.contains(26) || ops.contains(27)) {
            Intent intent2 = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
            intent2.putExtra("android.intent.extra.PACKAGE_NAME", pkg);
            this.mPresenter.startNotificationGutsIntent(intent2, uid, row);
        }
    }

    public void bindGuts(ExpandableNotificationRow row) {
        bindGuts(row, this.mGutsMenuItem);
    }

    private void bindGuts(final ExpandableNotificationRow row, NotificationMenuRowPlugin.MenuItem item) {
        final StatusBarNotification sbn = row.getStatusBarNotification();
        row.inflateGuts();
        row.setGutsView(item);
        row.setTag(sbn.getPackageName());
        row.getGuts().setClosedListener(new NotificationGuts.OnGutsClosedListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationGutsManager$5BbQHmFW6nuVeiAwlAjRPvwSbrU
            @Override // com.android.systemui.statusbar.NotificationGuts.OnGutsClosedListener
            public final void onGutsClosed(NotificationGuts notificationGuts) {
                NotificationGutsManager.lambda$bindGuts$0(this.f$0, row, sbn, notificationGuts);
            }
        });
        View gutsView = item.getGutsView();
        if (gutsView instanceof NotificationSnooze) {
            initializeSnoozeView(row, (NotificationSnooze) gutsView);
        } else if (gutsView instanceof AppOpsInfo) {
            initializeAppOpsInfo(row, (AppOpsInfo) gutsView);
        } else if (gutsView instanceof NotificationInfo) {
            initializeNotificationInfo(row, (NotificationInfo) gutsView);
        }
    }

    public static /* synthetic */ void lambda$bindGuts$0(NotificationGutsManager notificationGutsManager, ExpandableNotificationRow row, StatusBarNotification sbn, NotificationGuts g) {
        row.onGutsClosed();
        if (!g.willBeRemoved() && !row.isRemoved()) {
            notificationGutsManager.mListContainer.onHeightChanged(row, !notificationGutsManager.mPresenter.isPresenterFullyCollapsed());
        }
        if (notificationGutsManager.mNotificationGutsExposed == g) {
            notificationGutsManager.mNotificationGutsExposed = null;
            notificationGutsManager.mGutsMenuItem = null;
        }
        String key = sbn.getKey();
        if (key.equals(notificationGutsManager.mKeyToRemoveOnGutsClosed)) {
            notificationGutsManager.mKeyToRemoveOnGutsClosed = null;
            notificationGutsManager.mEntryManager.removeNotification(key, notificationGutsManager.mEntryManager.getLatestRankingMap());
        }
    }

    private void initializeSnoozeView(final ExpandableNotificationRow row, NotificationSnooze notificationSnoozeView) {
        NotificationGuts guts = row.getGuts();
        StatusBarNotification sbn = row.getStatusBarNotification();
        notificationSnoozeView.setSnoozeListener(this.mListContainer.getSwipeActionHelper());
        notificationSnoozeView.setStatusBarNotification(sbn);
        notificationSnoozeView.setSnoozeOptions(row.getEntry().snoozeCriteria);
        guts.setHeightChangedListener(new NotificationGuts.OnHeightChangedListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationGutsManager$cVmDFdPRr0bMQ_wKyHQ8UdoctmE
            @Override // com.android.systemui.statusbar.NotificationGuts.OnHeightChangedListener
            public final void onHeightChanged(NotificationGuts notificationGuts) {
                NotificationGutsManager notificationGutsManager = this.f$0;
                ExpandableNotificationRow expandableNotificationRow = row;
                notificationGutsManager.mListContainer.onHeightChanged(expandableNotificationRow, expandableNotificationRow.isShown());
            }
        });
    }

    private void initializeAppOpsInfo(final ExpandableNotificationRow row, AppOpsInfo appOpsInfoView) {
        final NotificationGuts guts = row.getGuts();
        StatusBarNotification sbn = row.getStatusBarNotification();
        UserHandle userHandle = sbn.getUser();
        PackageManager pmUser = StatusBar.getPackageManagerForUser(this.mContext, userHandle.getIdentifier());
        AppOpsInfo.OnSettingsClickListener onSettingsClick = new AppOpsInfo.OnSettingsClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationGutsManager$sf1-N5qoeH1wYt_-Wu4HHDalZDE
            @Override // com.android.systemui.statusbar.AppOpsInfo.OnSettingsClickListener
            public final void onClick(View view, String str, int i, ArraySet arraySet) {
                NotificationGutsManager.lambda$initializeAppOpsInfo$2(this.f$0, guts, row, view, str, i, arraySet);
            }
        };
        if (!row.getEntry().mActiveAppOps.isEmpty()) {
            appOpsInfoView.bindGuts(pmUser, onSettingsClick, sbn, row.getEntry().mActiveAppOps);
        }
    }

    public static /* synthetic */ void lambda$initializeAppOpsInfo$2(NotificationGutsManager notificationGutsManager, NotificationGuts guts, ExpandableNotificationRow row, View v, String pkg, int uid, ArraySet ops) {
        notificationGutsManager.mMetricsLogger.action(1346);
        guts.resetFalsingCheck();
        notificationGutsManager.startAppOpsSettingsActivity(pkg, uid, ops, row);
    }

    void initializeNotificationInfo(final ExpandableNotificationRow row, NotificationInfo notificationInfoView) {
        final NotificationGuts guts = row.getGuts();
        final StatusBarNotification sbn = row.getStatusBarNotification();
        final String packageName = sbn.getPackageName();
        UserHandle userHandle = sbn.getUser();
        PackageManager pmUser = StatusBar.getPackageManagerForUser(this.mContext, userHandle.getIdentifier());
        INotificationManager iNotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        NotificationInfo.OnAppSettingsClickListener onAppSettingsClick = new NotificationInfo.OnAppSettingsClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationGutsManager$5ccSwmHai9tNnko8L2PlieI593k
            @Override // com.android.systemui.statusbar.NotificationInfo.OnAppSettingsClickListener
            public final void onClick(View view, Intent intent) {
                NotificationGutsManager.lambda$initializeNotificationInfo$3(this.f$0, guts, sbn, row, view, intent);
            }
        };
        boolean isForBlockingHelper = row.isBlockingHelperShowing();
        NotificationInfo.OnSettingsClickListener onSettingsClick = (!userHandle.equals(UserHandle.ALL) || this.mLockscreenUserManager.getCurrentUserId() == 0) ? new NotificationInfo.OnSettingsClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationGutsManager$CuJJD4QSv95ak-chMNjvs4UJcoM
            @Override // com.android.systemui.statusbar.NotificationInfo.OnSettingsClickListener
            public final void onClick(View view, NotificationChannel notificationChannel, int i) {
                NotificationGutsManager.lambda$initializeNotificationInfo$4(this.f$0, guts, sbn, packageName, row, view, notificationChannel, i);
            }
        } : null;
        try {
            try {
                notificationInfoView.bindNotification(pmUser, iNotificationManager, packageName, row.getEntry().channel, row.getNumUniqueChannels(), sbn, this.mCheckSaveListener, onSettingsClick, onAppSettingsClick, row.getIsNonblockable(), isForBlockingHelper, row.getEntry().userSentiment == -1);
            } catch (RemoteException e) {
                e = e;
                Log.e("NotificationGutsManager", e.toString());
            }
        } catch (RemoteException e2) {
            e = e2;
        }
    }

    public static /* synthetic */ void lambda$initializeNotificationInfo$3(NotificationGutsManager notificationGutsManager, NotificationGuts guts, StatusBarNotification sbn, ExpandableNotificationRow row, View v, Intent intent) {
        notificationGutsManager.mMetricsLogger.action(206);
        guts.resetFalsingCheck();
        notificationGutsManager.mPresenter.startNotificationGutsIntent(intent, sbn.getUid(), row);
    }

    public static /* synthetic */ void lambda$initializeNotificationInfo$4(NotificationGutsManager notificationGutsManager, NotificationGuts guts, StatusBarNotification sbn, String packageName, ExpandableNotificationRow row, View v, NotificationChannel channel, int appUid) {
        notificationGutsManager.mMetricsLogger.action(205);
        guts.resetFalsingCheck();
        notificationGutsManager.mOnSettingsClickListener.onClick(sbn.getKey());
        notificationGutsManager.startAppNotificationSettingsActivity(packageName, appUid, channel, row);
    }

    public void closeAndSaveGuts(boolean removeLeavebehinds, boolean force, boolean removeControls, int x, int y, boolean resetMenu) {
        if (this.mNotificationGutsExposed != null) {
            this.mNotificationGutsExposed.closeControls(removeLeavebehinds, removeControls, x, y, force);
        }
        if (resetMenu) {
            this.mListContainer.resetExposedMenuView(false, true);
        }
    }

    public NotificationGuts getExposedGuts() {
        return this.mNotificationGutsExposed;
    }

    public void setExposedGuts(NotificationGuts guts) {
        this.mNotificationGutsExposed = guts;
    }

    boolean openGuts(View view, final int x, final int y, final NotificationMenuRowPlugin.MenuItem menuItem) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        if (view.getWindowToken() == null) {
            Log.e("NotificationGutsManager", "Trying to show notification guts, but not attached to window");
            return false;
        }
        final ExpandableNotificationRow row = (ExpandableNotificationRow) view;
        if (row.isDark()) {
            return false;
        }
        view.performHapticFeedback(0);
        if (row.areGutsExposed()) {
            closeAndSaveGuts(false, false, true, -1, -1, true);
            return false;
        }
        bindGuts(row, menuItem);
        final NotificationGuts guts = row.getGuts();
        if (guts == null) {
            return false;
        }
        this.mMetricsLogger.action(204);
        guts.setVisibility(4);
        guts.post(new Runnable() { // from class: com.android.systemui.statusbar.NotificationGutsManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (row.getWindowToken() == null) {
                    Log.e("NotificationGutsManager", "Trying to show notification guts in post(), but not attached to window");
                    return;
                }
                NotificationGutsManager.this.closeAndSaveGuts(true, true, true, -1, -1, false);
                guts.setVisibility(0);
                boolean needsFalsingProtection = NotificationGutsManager.this.mPresenter.isPresenterLocked() && !NotificationGutsManager.this.mAccessibilityManager.isTouchExplorationEnabled();
                NotificationGuts notificationGuts = guts;
                boolean z = !row.isBlockingHelperShowing();
                int i = x;
                int i2 = y;
                final ExpandableNotificationRow expandableNotificationRow = row;
                Objects.requireNonNull(expandableNotificationRow);
                notificationGuts.openControls(z, i, i2, needsFalsingProtection, new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$A0nRQ1BmXNDkvl3KYSKAx5O94nc
                    @Override // java.lang.Runnable
                    public final void run() {
                        expandableNotificationRow.onGutsOpened();
                    }
                });
                row.closeRemoteInput();
                NotificationGutsManager.this.mListContainer.onHeightChanged(row, true);
                NotificationGutsManager.this.mNotificationGutsExposed = guts;
                NotificationGutsManager.this.mGutsMenuItem = menuItem;
            }
        });
        return true;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("NotificationGutsManager state:");
        pw.print("  mKeyToRemoveOnGutsClosed: ");
        pw.println(this.mKeyToRemoveOnGutsClosed);
    }
}
