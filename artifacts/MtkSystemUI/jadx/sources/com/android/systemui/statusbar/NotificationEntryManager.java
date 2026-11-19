package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.R;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationInflater;
import com.android.systemui.statusbar.notification.RowInflaterTask;
import com.android.systemui.statusbar.notification.VisualStabilityManager;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.leak.LeakDetector;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationEntryManager implements Dumpable, ExpandableNotificationRow.ExpansionLogger, NotificationInflater.InflationCallback, VisualStabilityManager.Callback {
    protected static final boolean CHATTY = StatusBar.DEBUG;
    protected Callback mCallback;
    protected final Context mContext;
    protected boolean mDisableNotificationAlerts;
    protected HeadsUpManager mHeadsUpManager;
    protected ContentObserver mHeadsUpObserver;
    protected NotificationListenerService.RankingMap mLatestRankingMap;
    protected NotificationListContainer mListContainer;
    protected final NotificationMessagingUtil mMessagingUtil;
    protected NotificationData mNotificationData;
    private ExpandableNotificationRow.OnAppOpsClickListener mOnAppOpsClickListener;
    protected PowerManager mPowerManager;
    protected NotificationPresenter mPresenter;
    protected SystemServicesProxy mSystemServicesProxy;
    protected final HashMap<String, NotificationData.Entry> mPendingNotifications = new HashMap<>();
    protected final NotificationClicker mNotificationClicker = new NotificationClicker();
    protected final ArraySet<NotificationData.Entry> mHeadsUpEntriesToRemoveOnSwitch = new ArraySet<>();
    protected final NotificationLockscreenUserManager mLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class);
    protected final NotificationGroupManager mGroupManager = (NotificationGroupManager) Dependency.get(NotificationGroupManager.class);
    protected final NotificationGutsManager mGutsManager = (NotificationGutsManager) Dependency.get(NotificationGutsManager.class);
    protected final NotificationRemoteInputManager mRemoteInputManager = (NotificationRemoteInputManager) Dependency.get(NotificationRemoteInputManager.class);
    protected final NotificationMediaManager mMediaManager = (NotificationMediaManager) Dependency.get(NotificationMediaManager.class);
    protected final MetricsLogger mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
    protected final DeviceProvisionedController mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    protected final VisualStabilityManager mVisualStabilityManager = (VisualStabilityManager) Dependency.get(VisualStabilityManager.class);
    protected final UiOffloadThread mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
    protected final ForegroundServiceController mForegroundServiceController = (ForegroundServiceController) Dependency.get(ForegroundServiceController.class);
    protected final NotificationListener mNotificationListener = (NotificationListener) Dependency.get(NotificationListener.class);
    private final SmartReplyController mSmartReplyController = (SmartReplyController) Dependency.get(SmartReplyController.class);
    private final NotificationLifetimeExtender mFGSExtender = new ForegroundServiceLifetimeExtender();
    private final Map<NotificationData.Entry, NotificationLifetimeExtender> mRetainedNotifications = new ArrayMap();
    protected boolean mUseHeadsUp = false;
    private final ArraySet<String> mKeysKeptForRemoteInput = new ArraySet<>();
    private final DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.NotificationEntryManager.1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onDeviceProvisionedChanged() {
            NotificationEntryManager.this.updateNotifications();
        }
    };
    protected IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    public interface Callback {
        void onBindRow(NotificationData.Entry entry, PackageManager packageManager, StatusBarNotification statusBarNotification, ExpandableNotificationRow expandableNotificationRow);

        void onNotificationAdded(NotificationData.Entry entry);

        void onNotificationClicked(StatusBarNotification statusBarNotification, ExpandableNotificationRow expandableNotificationRow);

        void onNotificationRemoved(String str, StatusBarNotification statusBarNotification);

        void onNotificationUpdated(StatusBarNotification statusBarNotification);

        void onPerformRemoveNotification(StatusBarNotification statusBarNotification);

        boolean shouldPeek(NotificationData.Entry entry, StatusBarNotification statusBarNotification);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class NotificationClicker implements View.OnClickListener {
        private NotificationClicker() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (!(v instanceof ExpandableNotificationRow)) {
                Log.e("NotificationEntryMgr", "NotificationClicker called on a view that is not a notification row.");
                return;
            }
            NotificationEntryManager.this.mPresenter.wakeUpIfDozing(SystemClock.uptimeMillis(), v);
            final ExpandableNotificationRow row = (ExpandableNotificationRow) v;
            StatusBarNotification sbn = row.getStatusBarNotification();
            if (sbn == null) {
                Log.e("NotificationEntryMgr", "NotificationClicker called on an unclickable notification,");
                return;
            }
            if (row.getProvider() != null && row.getProvider().isMenuVisible()) {
                row.animateTranslateNotification(0.0f);
                return;
            }
            row.setJustClicked(true);
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationEntryManager$NotificationClicker$LBMGOladLjt5equMOv9trf31Q8s
                @Override // java.lang.Runnable
                public final void run() {
                    row.setJustClicked(false);
                }
            });
            NotificationEntryManager.this.mCallback.onNotificationClicked(sbn, row);
        }

        public void register(ExpandableNotificationRow row, StatusBarNotification sbn) {
            Notification notification = sbn.getNotification();
            if (notification.contentIntent != null || notification.fullScreenIntent != null) {
                row.setOnClickListener(this);
            } else {
                row.setOnClickListener(null);
            }
        }
    }

    public NotificationListenerService.RankingMap getLatestRankingMap() {
        return this.mLatestRankingMap;
    }

    public void setLatestRankingMap(NotificationListenerService.RankingMap latestRankingMap) {
        this.mLatestRankingMap = latestRankingMap;
    }

    public void setDisableNotificationAlerts(boolean disableNotificationAlerts) {
        this.mDisableNotificationAlerts = disableNotificationAlerts;
        this.mHeadsUpObserver.onChange(true);
    }

    public void onHeadsUpStateChanged(NotificationData.Entry entry, boolean isHeadsUp) {
        if (!isHeadsUp && this.mHeadsUpEntriesToRemoveOnSwitch.contains(entry)) {
            removeNotification(entry.key, getLatestRankingMap());
            this.mHeadsUpEntriesToRemoveOnSwitch.remove(entry);
            if (this.mHeadsUpEntriesToRemoveOnSwitch.isEmpty()) {
                setLatestRankingMap(null);
                return;
            }
            return;
        }
        updateNotificationRanking(null);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("NotificationEntryManager state:");
        pw.print("  mPendingNotifications=");
        if (this.mPendingNotifications.size() == 0) {
            pw.println("null");
        } else {
            Iterator<NotificationData.Entry> it = this.mPendingNotifications.values().iterator();
            while (it.hasNext()) {
                pw.println(it.next().notification);
            }
        }
        pw.println("  Lifetime-extended notifications:");
        if (this.mRetainedNotifications.isEmpty()) {
            pw.println("    None");
        } else {
            for (Map.Entry<NotificationData.Entry, NotificationLifetimeExtender> entry : this.mRetainedNotifications.entrySet()) {
                pw.println("    " + entry.getKey().notification + " retained by " + entry.getValue().getClass().getName());
            }
        }
        pw.print("  mUseHeadsUp=");
        pw.println(this.mUseHeadsUp);
        pw.print("  mKeysKeptForRemoteInput: ");
        pw.println(this.mKeysKeptForRemoteInput);
    }

    public NotificationEntryManager(Context context) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mMessagingUtil = new NotificationMessagingUtil(context);
        this.mSystemServicesProxy = SystemServicesProxy.getInstance(this.mContext);
        this.mGroupManager.setPendingEntries(this.mPendingNotifications);
        this.mFGSExtender.setCallback(new NotificationLifetimeExtender.NotificationSafeToRemoveCallback() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationEntryManager$iv3ZXz4UNYv0IroYYN1fLGff62s
            @Override // com.android.systemui.statusbar.NotificationLifetimeExtender.NotificationSafeToRemoveCallback
            public final void onSafeToRemove(String str) {
                NotificationEntryManager notificationEntryManager = this.f$0;
                notificationEntryManager.removeNotification(str, notificationEntryManager.mLatestRankingMap);
            }
        });
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationListContainer listContainer, Callback callback, HeadsUpManager headsUpManager) {
        this.mPresenter = presenter;
        this.mCallback = callback;
        this.mNotificationData = new NotificationData(presenter, this.mContext);
        this.mHeadsUpManager = headsUpManager;
        this.mNotificationData.setHeadsUpManager(this.mHeadsUpManager);
        this.mListContainer = listContainer;
        this.mHeadsUpObserver = new ContentObserver(this.mPresenter.getHandler()) { // from class: com.android.systemui.statusbar.NotificationEntryManager.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                boolean wasUsing = NotificationEntryManager.this.mUseHeadsUp;
                NotificationEntryManager notificationEntryManager = NotificationEntryManager.this;
                boolean z = false;
                if (!NotificationEntryManager.this.mDisableNotificationAlerts && Settings.Global.getInt(NotificationEntryManager.this.mContext.getContentResolver(), "heads_up_notifications_enabled", 0) != 0) {
                    z = true;
                }
                notificationEntryManager.mUseHeadsUp = z;
                StringBuilder sb = new StringBuilder();
                sb.append("heads up is ");
                sb.append(NotificationEntryManager.this.mUseHeadsUp ? "enabled" : "disabled");
                Log.d("NotificationEntryMgr", sb.toString());
                if (wasUsing != NotificationEntryManager.this.mUseHeadsUp && !NotificationEntryManager.this.mUseHeadsUp) {
                    Log.d("NotificationEntryMgr", "dismissing any existing heads up notification on disable event");
                    NotificationEntryManager.this.mHeadsUpManager.releaseAllImmediately();
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("heads_up_notifications_enabled"), true, this.mHeadsUpObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("ticker_gets_heads_up"), true, this.mHeadsUpObserver);
        this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
        this.mHeadsUpObserver.onChange(true);
        final NotificationGutsManager notificationGutsManager = this.mGutsManager;
        Objects.requireNonNull(notificationGutsManager);
        this.mOnAppOpsClickListener = new ExpandableNotificationRow.OnAppOpsClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$UXhQQT1EcpQ7iahyyi282W5mE2Q
            @Override // com.android.systemui.statusbar.ExpandableNotificationRow.OnAppOpsClickListener
            public final boolean onClick(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
                return notificationGutsManager.openGuts(view, i, i2, menuItem);
            }
        };
    }

    @VisibleForTesting
    protected Map<NotificationData.Entry, NotificationLifetimeExtender> getRetainedNotificationMap() {
        return this.mRetainedNotifications;
    }

    public NotificationData getNotificationData() {
        return this.mNotificationData;
    }

    public ExpandableNotificationRow.LongPressListener getNotificationLongClicker() {
        final NotificationGutsManager notificationGutsManager = this.mGutsManager;
        Objects.requireNonNull(notificationGutsManager);
        return new ExpandableNotificationRow.LongPressListener() { // from class: com.android.systemui.statusbar.-$$Lambda$c60ntPGLw0RNAFWdGf1n1pJttuQ
            @Override // com.android.systemui.statusbar.ExpandableNotificationRow.LongPressListener
            public final boolean onLongPress(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
                return notificationGutsManager.openGuts(view, i, i2, menuItem);
            }
        };
    }

    @Override // com.android.systemui.statusbar.ExpandableNotificationRow.ExpansionLogger
    public void logNotificationExpansion(final String key, final boolean userAction, final boolean expanded) {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationEntryManager$sDpXPCQ-YxMoOyZYF2g-q9o9PaQ
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mBarService.onNotificationExpansionChanged(key, userAction, expanded);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.VisualStabilityManager.Callback
    public void onReorderingAllowed() {
        updateNotifications();
    }

    private boolean shouldSuppressFullScreenIntent(NotificationData.Entry entry) {
        if (this.mPresenter.isDeviceInVrMode()) {
            return true;
        }
        return this.mNotificationData.shouldSuppressFullScreenIntent(entry);
    }

    private void inflateViews(final NotificationData.Entry entry, ViewGroup parent) {
        final PackageManager pmUser = StatusBar.getPackageManagerForUser(this.mContext, entry.notification.getUser().getIdentifier());
        final StatusBarNotification sbn = entry.notification;
        if (entry.row != null) {
            entry.reset();
            updateNotification(entry, pmUser, sbn, entry.row);
        } else {
            new RowInflaterTask().inflate(this.mContext, parent, entry, new RowInflaterTask.RowInflationFinishedListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationEntryManager$9-WZSsUAqFuZSzfJs1MZiwS4QQY
                @Override // com.android.systemui.statusbar.notification.RowInflaterTask.RowInflationFinishedListener
                public final void onInflationFinished(ExpandableNotificationRow expandableNotificationRow) {
                    NotificationEntryManager.lambda$inflateViews$2(this.f$0, entry, pmUser, sbn, expandableNotificationRow);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$inflateViews$2(NotificationEntryManager notificationEntryManager, NotificationData.Entry entry, PackageManager pmUser, StatusBarNotification sbn, ExpandableNotificationRow row) {
        notificationEntryManager.bindRow(entry, pmUser, sbn, row);
        notificationEntryManager.updateNotification(entry, pmUser, sbn, row);
    }

    private void bindRow(NotificationData.Entry entry, PackageManager pmUser, StatusBarNotification sbn, final ExpandableNotificationRow row) {
        row.setExpansionLogger(this, entry.notification.getKey());
        row.setGroupManager(this.mGroupManager);
        row.setHeadsUpManager(this.mHeadsUpManager);
        row.setOnExpandClickListener(this.mPresenter);
        row.setInflationCallback(this);
        row.setLongPressListener(getNotificationLongClicker());
        this.mListContainer.bindRow(row);
        this.mRemoteInputManager.bindRow(row);
        String pkg = sbn.getPackageName();
        String appname = pkg;
        try {
            ApplicationInfo info = pmUser.getApplicationInfo(pkg, 8704);
            if (info != null) {
                appname = String.valueOf(pmUser.getApplicationLabel(info));
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        row.setAppName(appname);
        row.setOnDismissRunnable(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationEntryManager$FtVLTfjpGJ6N_j30Hzj-eE-u1n4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.performRemoveNotification(row.getStatusBarNotification());
            }
        });
        row.setDescendantFocusability(393216);
        if (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT) {
            row.setDescendantFocusability(131072);
        }
        row.setAppOpsOnClickListener(this.mOnAppOpsClickListener);
        this.mCallback.onBindRow(entry, pmUser, sbn, row);
    }

    public void performRemoveNotification(StatusBarNotification n) {
        int rank = this.mNotificationData.getRank(n.getKey());
        int count = this.mNotificationData.getActiveNotifications().size();
        NotificationVisibility nv = NotificationVisibility.obtain(n.getKey(), rank, count, true);
        NotificationData.Entry entry = this.mNotificationData.get(n.getKey());
        if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY && this.mKeysKeptForRemoteInput.contains(n.getKey())) {
            this.mKeysKeptForRemoteInput.remove(n.getKey());
        }
        this.mRemoteInputManager.onPerformRemoveNotification(n, entry);
        String pkg = n.getPackageName();
        String tag = n.getTag();
        int id = n.getId();
        int userId = n.getUserId();
        int dismissalSurface = 3;
        try {
            if (isHeadsUp(n.getKey())) {
                dismissalSurface = 1;
            } else if (this.mListContainer.hasPulsingNotifications()) {
                dismissalSurface = 2;
            }
            this.mBarService.onNotificationClear(pkg, tag, id, userId, n.getKey(), dismissalSurface, nv);
            removeNotification(n.getKey(), null);
        } catch (RemoteException e) {
        }
        this.mCallback.onPerformRemoveNotification(n);
    }

    void handleNotificationError(StatusBarNotification n, String message) {
        removeNotification(n.getKey(), null);
        try {
            this.mBarService.onNotificationError(n.getPackageName(), n.getTag(), n.getId(), n.getUid(), n.getInitialPid(), message, n.getUserId());
        } catch (RemoteException e) {
        }
    }

    private void abortExistingInflation(String key) {
        if (this.mPendingNotifications.containsKey(key)) {
            NotificationData.Entry entry = this.mPendingNotifications.get(key);
            entry.abortTask();
            this.mPendingNotifications.remove(key);
        }
        NotificationData.Entry addedEntry = this.mNotificationData.get(key);
        if (addedEntry != null) {
            addedEntry.abortTask();
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationInflater.InflationCallback
    public void handleInflationException(StatusBarNotification notification, Exception e) {
        handleNotificationError(notification, e.getMessage());
    }

    private void addEntry(NotificationData.Entry shadeEntry) {
        boolean isHeadsUped = shouldPeek(shadeEntry);
        if (CHATTY) {
            StringBuilder sb = new StringBuilder();
            sb.append("addEntry, isHeadsUped: ");
            sb.append(isHeadsUped);
            sb.append(", key: ");
            sb.append(shadeEntry != null ? shadeEntry.key : null);
            Log.d("NotificationEntryMgr", sb.toString());
        }
        if (isHeadsUped) {
            this.mHeadsUpManager.showNotification(shadeEntry);
            setNotificationShown(shadeEntry.notification);
        }
        addNotificationViews(shadeEntry);
        this.mCallback.onNotificationAdded(shadeEntry);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationInflater.InflationCallback
    public void onAsyncInflationFinished(NotificationData.Entry entry) {
        this.mPendingNotifications.remove(entry.key);
        boolean isNew = this.mNotificationData.get(entry.key) == null;
        if (CHATTY) {
            StringBuilder sb = new StringBuilder();
            sb.append("onAsyncInflationFinished, isNew: ");
            sb.append(isNew);
            sb.append(", removed: ");
            sb.append(entry.row.isRemoved());
            sb.append(", key: ");
            sb.append(entry != null ? entry.key : null);
            Log.d("NotificationEntryMgr", sb.toString());
        }
        if (isNew && !entry.row.isRemoved()) {
            addEntry(entry);
        } else if (!isNew && entry.row.hasLowPriorityStateUpdated()) {
            this.mVisualStabilityManager.onLowPriorityUpdated(entry);
            this.mPresenter.updateNotificationViews();
        }
        entry.row.setLowPriorityStateUpdated(false);
    }

    public void removeNotification(String key, NotificationListenerService.RankingMap ranking) {
        NotificationData.Entry pendingEntry = this.mPendingNotifications.get(key);
        if (pendingEntry != null && this.mFGSExtender.shouldExtendLifetimeForPendingNotification(pendingEntry)) {
            extendLifetime(pendingEntry, this.mFGSExtender);
            return;
        }
        abortExistingInflation(key);
        boolean deferRemoval = false;
        boolean updated = false;
        if (this.mHeadsUpManager.isHeadsUp(key)) {
            boolean ignoreEarliestRemovalTime = (this.mRemoteInputManager.getController().isSpinning(key) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) || !this.mVisualStabilityManager.isReorderingAllowed();
            deferRemoval = true ^ this.mHeadsUpManager.removeNotification(key, ignoreEarliestRemovalTime);
        }
        this.mMediaManager.onNotificationRemoved(key);
        NotificationData.Entry entry = this.mNotificationData.get(key);
        if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY && shouldKeepForRemoteInput(entry) && entry.row != null && !entry.row.isDismissed()) {
            CharSequence remoteInputText = entry.remoteInputText;
            if (TextUtils.isEmpty(remoteInputText)) {
                remoteInputText = entry.remoteInputTextWhenReset;
            }
            StatusBarNotification newSbn = rebuildNotificationWithRemoteInput(entry, remoteInputText, false);
            boolean updated2 = false;
            entry.onRemoteInputInserted();
            try {
                updateNotificationInternal(newSbn, null);
                updated2 = true;
            } catch (InflationException e) {
                deferRemoval = false;
            }
            if (updated2) {
                Log.w("NotificationEntryMgr", "Keeping notification around after sending remote input " + entry.key);
                addKeyKeptForRemoteInput(entry.key);
                return;
            }
        }
        if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY && shouldKeepForSmartReply(entry) && entry.row != null && !entry.row.isDismissed()) {
            StatusBarNotification newSbn2 = rebuildNotificationForCanceledSmartReplies(entry);
            try {
                updateNotificationInternal(newSbn2, null);
                updated = true;
            } catch (InflationException e2) {
            }
            this.mSmartReplyController.stopSending(entry);
            if (updated) {
                Log.w("NotificationEntryMgr", "Keeping notification around after sending smart reply " + entry.key);
                addKeyKeptForRemoteInput(entry.key);
                return;
            }
        }
        if (entry != null && this.mFGSExtender.shouldExtendLifetime(entry)) {
            extendLifetime(entry, this.mFGSExtender);
            return;
        }
        this.mSmartReplyController.stopSending(entry);
        if (deferRemoval) {
            this.mLatestRankingMap = ranking;
            this.mHeadsUpEntriesToRemoveOnSwitch.add(this.mHeadsUpManager.getEntry(key));
            return;
        }
        if (this.mRemoteInputManager.onRemoveNotification(entry)) {
            this.mLatestRankingMap = ranking;
            return;
        }
        if (entry != null && this.mGutsManager.getExposedGuts() != null && this.mGutsManager.getExposedGuts() == entry.row.getGuts() && entry.row.getGuts() != null && !entry.row.getGuts().isLeavebehind()) {
            Log.w("NotificationEntryMgr", "Keeping notification because it's showing guts. " + key);
            this.mLatestRankingMap = ranking;
            this.mGutsManager.setKeyToRemoveOnGutsClosed(key);
            return;
        }
        if (entry != null) {
            this.mForegroundServiceController.removeNotification(entry.notification);
        }
        if (entry != null && entry.row != null) {
            entry.row.setRemoved();
            this.mListContainer.cleanUpViewState(entry.row);
        }
        handleGroupSummaryRemoved(key);
        StatusBarNotification old = removeNotificationViews(key, ranking);
        cancelLifetimeExtension(entry);
        this.mCallback.onNotificationRemoved(key, old);
    }

    private void extendLifetime(NotificationData.Entry entry, NotificationLifetimeExtender extender) {
        NotificationLifetimeExtender activeExtender = this.mRetainedNotifications.get(entry);
        if (activeExtender != null && activeExtender != extender) {
            activeExtender.setShouldManageLifetime(entry, false);
        }
        this.mRetainedNotifications.put(entry, extender);
        extender.setShouldManageLifetime(entry, true);
    }

    private void cancelLifetimeExtension(NotificationData.Entry entry) {
        NotificationLifetimeExtender activeExtender = this.mRetainedNotifications.remove(entry);
        if (activeExtender != null) {
            activeExtender.setShouldManageLifetime(entry, false);
        }
    }

    public StatusBarNotification rebuildNotificationWithRemoteInput(NotificationData.Entry entry, CharSequence remoteInputText, boolean showSpinner) {
        CharSequence[] newHistory;
        StatusBarNotification sbn = entry.notification;
        Notification.Builder b = Notification.Builder.recoverBuilder(this.mContext, sbn.getNotification().clone());
        if (remoteInputText != null) {
            CharSequence[] oldHistory = sbn.getNotification().extras.getCharSequenceArray("android.remoteInputHistory");
            if (oldHistory != null) {
                newHistory = new CharSequence[oldHistory.length + 1];
                System.arraycopy(oldHistory, 0, newHistory, 1, oldHistory.length);
            } else {
                newHistory = new CharSequence[1];
            }
            newHistory[0] = String.valueOf(remoteInputText);
            b.setRemoteInputHistory(newHistory);
        }
        b.setShowRemoteInputSpinner(showSpinner);
        b.setHideSmartReplies(true);
        Notification newNotification = b.build();
        newNotification.contentView = sbn.getNotification().contentView;
        newNotification.bigContentView = sbn.getNotification().bigContentView;
        newNotification.headsUpContentView = sbn.getNotification().headsUpContentView;
        StatusBarNotification newSbn = new StatusBarNotification(sbn.getPackageName(), sbn.getOpPkg(), sbn.getId(), sbn.getTag(), sbn.getUid(), sbn.getInitialPid(), newNotification, sbn.getUser(), sbn.getOverrideGroupKey(), sbn.getPostTime());
        return newSbn;
    }

    @VisibleForTesting
    StatusBarNotification rebuildNotificationForCanceledSmartReplies(NotificationData.Entry entry) {
        return rebuildNotificationWithRemoteInput(entry, null, false);
    }

    private boolean shouldKeepForSmartReply(NotificationData.Entry entry) {
        return entry != null && this.mSmartReplyController.isSendingSmartReply(entry.key);
    }

    private boolean shouldKeepForRemoteInput(NotificationData.Entry entry) {
        if (entry == null) {
            return false;
        }
        return this.mRemoteInputManager.getController().isSpinning(entry.key) || entry.hasJustSentRemoteInput();
    }

    private StatusBarNotification removeNotificationViews(String key, NotificationListenerService.RankingMap ranking) {
        NotificationData.Entry entry = this.mNotificationData.remove(key, ranking);
        if (entry == null) {
            Log.w("NotificationEntryMgr", "removeNotification for unknown key: " + key);
            return null;
        }
        updateNotifications();
        ((LeakDetector) Dependency.get(LeakDetector.class)).trackGarbage(entry);
        return entry.notification;
    }

    private void handleGroupSummaryRemoved(String key) {
        NotificationData.Entry entry = this.mNotificationData.get(key);
        if (entry != null && entry.row != null && entry.row.isSummaryWithChildren()) {
            if (entry.notification.getOverrideGroupKey() != null && !entry.row.isDismissed()) {
                return;
            }
            List<ExpandableNotificationRow> notificationChildren = entry.row.getNotificationChildren();
            for (int i = 0; i < notificationChildren.size(); i++) {
                ExpandableNotificationRow row = notificationChildren.get(i);
                NotificationData.Entry childEntry = row.getEntry();
                boolean isForeground = (row.getStatusBarNotification().getNotification().flags & 64) != 0;
                boolean keepForReply = NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY && (shouldKeepForRemoteInput(childEntry) || shouldKeepForSmartReply(childEntry));
                if (!isForeground && !keepForReply) {
                    row.setKeepInParent(true);
                    row.setRemoved();
                }
            }
        }
    }

    public void updateNotificationsOnDensityOrFontScaleChanged() {
        ArrayList<NotificationData.Entry> userNotifications = this.mNotificationData.getNotificationsForCurrentUser();
        for (int i = 0; i < userNotifications.size(); i++) {
            NotificationData.Entry entry = userNotifications.get(i);
            boolean exposedGuts = this.mGutsManager.getExposedGuts() != null && entry.row.getGuts() == this.mGutsManager.getExposedGuts();
            entry.row.onDensityOrFontScaleChanged();
            if (exposedGuts) {
                this.mGutsManager.onDensityOrFontScaleChanged(entry.row);
            }
        }
    }

    protected void updateNotification(NotificationData.Entry entry, PackageManager pmUser, StatusBarNotification sbn, ExpandableNotificationRow row) {
        row.setNeedsRedaction(this.mLockscreenUserManager.needsRedaction(entry));
        boolean isLowPriority = this.mNotificationData.isAmbient(sbn.getKey());
        boolean isUpdate = this.mNotificationData.get(entry.key) != null;
        boolean wasLowPriority = row.isLowPriority();
        row.setIsLowPriority(isLowPriority);
        row.setLowPriorityStateUpdated(isUpdate && wasLowPriority != isLowPriority);
        this.mNotificationClicker.register(row, sbn);
        try {
            ApplicationInfo info = pmUser.getApplicationInfo(sbn.getPackageName(), 0);
            entry.targetSdk = info.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e("NotificationEntryMgr", "Failed looking up ApplicationInfo for " + sbn.getPackageName(), ex);
        }
        row.setLegacy(entry.targetSdk >= 9 && entry.targetSdk < 21);
        entry.setIconTag(R.id.icon_is_pre_L, Boolean.valueOf(entry.targetSdk < 21));
        entry.autoRedacted = entry.notification.getNotification().publicVersion == null;
        entry.row = row;
        entry.row.setOnActivatedListener(this.mPresenter);
        boolean useIncreasedCollapsedHeight = this.mMessagingUtil.isImportantMessaging(sbn, this.mNotificationData.getImportance(sbn.getKey()));
        boolean useIncreasedHeadsUp = useIncreasedCollapsedHeight && !this.mPresenter.isPresenterFullyCollapsed();
        row.setUseIncreasedCollapsedHeight(useIncreasedCollapsedHeight);
        row.setUseIncreasedHeadsUpHeight(useIncreasedHeadsUp);
        row.updateNotification(entry);
    }

    protected void addNotificationViews(NotificationData.Entry entry) {
        if (entry == null) {
            return;
        }
        this.mNotificationData.add(entry);
        tagForeground(entry.notification);
        updateNotifications();
    }

    protected NotificationData.Entry createNotificationViews(StatusBarNotification sbn) throws InflationException {
        if (CHATTY) {
            Log.d("NotificationEntryMgr", "createNotificationViews(notification=" + sbn);
        }
        NotificationData.Entry entry = new NotificationData.Entry(sbn);
        ((LeakDetector) Dependency.get(LeakDetector.class)).trackInstance(entry);
        entry.createIcons(this.mContext, sbn);
        inflateViews(entry, this.mListContainer.getViewParentForNotification(entry));
        return entry;
    }

    private void addNotificationInternal(StatusBarNotification notification, NotificationListenerService.RankingMap ranking) throws InflationException {
        String key = notification.getKey();
        if (CHATTY) {
            Log.d("NotificationEntryMgr", "addNotification key=" + key);
        }
        this.mNotificationData.updateRanking(ranking);
        NotificationData.Entry shadeEntry = createNotificationViews(notification);
        boolean isHeadsUped = shouldPeek(shadeEntry);
        if (!isHeadsUped && notification.getNotification().fullScreenIntent != null) {
            if (shouldSuppressFullScreenIntent(shadeEntry)) {
                if (CHATTY) {
                    Log.d("NotificationEntryMgr", "No Fullscreen intent: suppressed by DND: " + key);
                }
            } else if (this.mNotificationData.getImportance(key) < 4) {
                if (CHATTY) {
                    Log.d("NotificationEntryMgr", "No Fullscreen intent: not important enough: " + key);
                }
            } else {
                SystemServicesProxy.getInstance(this.mContext).awakenDreamsAsync();
                if (CHATTY) {
                    Log.d("NotificationEntryMgr", "Notification has fullScreenIntent; sending fullScreenIntent");
                }
                try {
                    EventLog.writeEvent(36002, key);
                    notification.getNotification().fullScreenIntent.send();
                    shadeEntry.notifyFullScreenIntentLaunched();
                    this.mMetricsLogger.count("note_fullscreen", 1);
                } catch (PendingIntent.CanceledException e) {
                }
            }
        }
        abortExistingInflation(key);
        this.mForegroundServiceController.addNotification(notification, this.mNotificationData.getImportance(key));
        this.mPendingNotifications.put(key, shadeEntry);
        this.mGroupManager.onPendingEntryAdded(shadeEntry);
    }

    @VisibleForTesting
    protected void tagForeground(StatusBarNotification notification) {
        ArraySet<Integer> activeOps = this.mForegroundServiceController.getAppOps(notification.getUserId(), notification.getPackageName());
        if (activeOps != null) {
            int N = activeOps.size();
            for (int i = 0; i < N; i++) {
                updateNotificationsForAppOp(activeOps.valueAt(i).intValue(), notification.getUid(), notification.getPackageName(), true);
            }
        }
    }

    public void addNotification(StatusBarNotification notification, NotificationListenerService.RankingMap ranking) {
        try {
            addNotificationInternal(notification, ranking);
        } catch (InflationException e) {
            handleInflationException(notification, e);
        }
    }

    public void updateNotificationsForAppOp(int appOp, int uid, String pkg, boolean showIcon) {
        String foregroundKey = this.mForegroundServiceController.getStandardLayoutKey(UserHandle.getUserId(uid), pkg);
        if (foregroundKey != null) {
            this.mNotificationData.updateAppOp(appOp, uid, pkg, foregroundKey, showIcon);
            updateNotifications();
        }
    }

    private boolean alertAgain(NotificationData.Entry oldEntry, Notification newNotification) {
        return oldEntry == null || !oldEntry.hasInterrupted() || (newNotification.flags & 8) == 0;
    }

    private void updateNotificationInternal(StatusBarNotification notification, NotificationListenerService.RankingMap ranking) throws InflationException {
        if (CHATTY) {
            Log.d("NotificationEntryMgr", "updateNotification(" + notification + ")");
        }
        String key = notification.getKey();
        abortExistingInflation(key);
        NotificationData.Entry entry = this.mNotificationData.get(key);
        if (entry == null) {
            if (CHATTY) {
                Log.d("NotificationEntryMgr", "updateNotification, entry == null, (" + notification + ")");
                return;
            }
            return;
        }
        this.mHeadsUpEntriesToRemoveOnSwitch.remove(entry);
        this.mRemoteInputManager.onUpdateNotification(entry);
        this.mSmartReplyController.stopSending(entry);
        if (key.equals(this.mGutsManager.getKeyToRemoveOnGutsClosed())) {
            this.mGutsManager.setKeyToRemoveOnGutsClosed(null);
            Log.w("NotificationEntryMgr", "Notification that was kept for guts was updated. " + key);
        }
        cancelLifetimeExtension(entry);
        Notification n = notification.getNotification();
        this.mNotificationData.updateRanking(ranking);
        StatusBarNotification oldNotification = entry.notification;
        entry.notification = notification;
        this.mGroupManager.onEntryUpdated(entry, oldNotification);
        entry.updateIcons(this.mContext, notification);
        inflateViews(entry, this.mListContainer.getViewParentForNotification(entry));
        this.mForegroundServiceController.updateNotification(notification, this.mNotificationData.getImportance(key));
        boolean shouldPeek = shouldPeek(entry, notification);
        boolean alertAgain = alertAgain(entry, n);
        updateHeadsUp(key, entry, shouldPeek, alertAgain);
        updateNotifications();
        if (!notification.isClearable()) {
            this.mListContainer.snapViewIfNeeded(entry.row);
        }
        this.mCallback.onNotificationUpdated(notification);
    }

    public void updateNotification(StatusBarNotification notification, NotificationListenerService.RankingMap ranking) {
        try {
            updateNotificationInternal(notification, ranking);
        } catch (InflationException e) {
            handleInflationException(notification, e);
        }
    }

    public void updateNotifications() {
        this.mNotificationData.filterAndSort();
        this.mPresenter.updateNotificationViews();
    }

    public void updateNotificationRanking(NotificationListenerService.RankingMap ranking) {
        this.mNotificationData.updateRanking(ranking);
        updateNotifications();
    }

    protected boolean shouldPeek(NotificationData.Entry entry) {
        return shouldPeek(entry, entry.notification);
    }

    public boolean shouldPeek(NotificationData.Entry entry, StatusBarNotification sbn) {
        if (!this.mUseHeadsUp || this.mPresenter.isDeviceInVrMode() || this.mNotificationData.shouldFilterOut(entry)) {
            return false;
        }
        boolean inUse = this.mPowerManager.isScreenOn() && !this.mSystemServicesProxy.isDreaming();
        if (!inUse && !this.mPresenter.isDozing()) {
            return false;
        }
        if (!this.mPresenter.isDozing() && this.mNotificationData.shouldSuppressPeek(entry)) {
            return false;
        }
        if ((this.mPresenter.isDozing() && this.mNotificationData.shouldSuppressAmbient(entry)) || entry.hasJustLaunchedFullScreenIntent() || isSnoozedPackage(sbn)) {
            return false;
        }
        int importanceLevel = this.mPresenter.isDozing() ? 3 : 4;
        if (this.mNotificationData.getImportance(sbn.getKey()) < importanceLevel) {
            return false;
        }
        return !(sbn.isGroup() && sbn.getNotification().suppressAlertingDueToGrouping()) && this.mCallback.shouldPeek(entry, sbn);
    }

    protected void setNotificationShown(StatusBarNotification n) {
        setNotificationsShown(new String[]{n.getKey()});
    }

    protected void setNotificationsShown(String[] keys) {
        try {
            this.mNotificationListener.setNotificationsShown(keys);
        } catch (RuntimeException e) {
            Log.d("NotificationEntryMgr", "failed setNotificationsShown: ", e);
        }
    }

    protected boolean isSnoozedPackage(StatusBarNotification sbn) {
        return this.mHeadsUpManager.isSnoozed(sbn.getPackageName());
    }

    protected void updateHeadsUp(String key, NotificationData.Entry entry, boolean shouldPeek, boolean alertAgain) {
        boolean wasHeadsUp = isHeadsUp(key);
        if (wasHeadsUp) {
            if (!shouldPeek) {
                if (CHATTY) {
                    Log.d("NotificationEntryMgr", "updateHeadsUp, removeNotification: " + key);
                }
                this.mHeadsUpManager.removeNotification(key, false);
                return;
            }
            if (CHATTY) {
                Log.d("NotificationEntryMgr", "updateHeadsUp, updateNotification: " + key);
            }
            this.mHeadsUpManager.updateNotification(entry, alertAgain);
            return;
        }
        if (shouldPeek && alertAgain) {
            if (CHATTY) {
                Log.d("NotificationEntryMgr", "updateHeadsUp, showNotification: " + key);
            }
            this.mHeadsUpManager.showNotification(entry);
        }
    }

    protected boolean isHeadsUp(String key) {
        return this.mHeadsUpManager.isHeadsUp(key);
    }

    public boolean isNotificationKeptForRemoteInput(String key) {
        return this.mKeysKeptForRemoteInput.contains(key);
    }

    public void removeKeyKeptForRemoteInput(String key) {
        this.mKeysKeptForRemoteInput.remove(key);
    }

    public void addKeyKeptForRemoteInput(String key) {
        if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
            this.mKeysKeptForRemoteInput.add(key);
        }
    }
}
