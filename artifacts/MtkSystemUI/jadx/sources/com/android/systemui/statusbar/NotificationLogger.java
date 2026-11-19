package com.android.systemui.statusbar;

import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Dependency;
import com.android.systemui.UiOffloadThread;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class NotificationLogger {
    protected NotificationEntryManager mEntryManager;
    private long mLastVisibilityReportUptimeMs;
    private NotificationListContainer mListContainer;
    private final ArraySet<NotificationVisibility> mCurrentlyVisibleNotifications = new ArraySet<>();
    private final NotificationListenerService mNotificationListener = (NotificationListenerService) Dependency.get(NotificationListener.class);
    private final UiOffloadThread mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
    protected Handler mHandler = new Handler();
    protected final OnChildLocationsChangedListener mNotificationLocationsChangedListener = new OnChildLocationsChangedListener() { // from class: com.android.systemui.statusbar.NotificationLogger.1
        @Override // com.android.systemui.statusbar.NotificationLogger.OnChildLocationsChangedListener
        public void onChildLocationsChanged() {
            if (!NotificationLogger.this.mHandler.hasCallbacks(NotificationLogger.this.mVisibilityReporter)) {
                long nextReportUptimeMs = NotificationLogger.this.mLastVisibilityReportUptimeMs + 500;
                NotificationLogger.this.mHandler.postAtTime(NotificationLogger.this.mVisibilityReporter, nextReportUptimeMs);
            }
        }
    };
    protected final Runnable mVisibilityReporter = new Runnable() { // from class: com.android.systemui.statusbar.NotificationLogger.2
        private final ArraySet<NotificationVisibility> mTmpNewlyVisibleNotifications = new ArraySet<>();
        private final ArraySet<NotificationVisibility> mTmpCurrentlyVisibleNotifications = new ArraySet<>();
        private final ArraySet<NotificationVisibility> mTmpNoLongerVisibleNotifications = new ArraySet<>();

        @Override // java.lang.Runnable
        public void run() {
            NotificationLogger.this.mLastVisibilityReportUptimeMs = SystemClock.uptimeMillis();
            ArrayList<NotificationData.Entry> activeNotifications = NotificationLogger.this.mEntryManager.getNotificationData().getActiveNotifications();
            int N = activeNotifications.size();
            for (int i = 0; i < N; i++) {
                NotificationData.Entry entry = activeNotifications.get(i);
                String key = entry.notification.getKey();
                boolean isVisible = NotificationLogger.this.mListContainer.isInVisibleLocation(entry.row);
                NotificationVisibility visObj = NotificationVisibility.obtain(key, i, N, isVisible);
                boolean previouslyVisible = NotificationLogger.this.mCurrentlyVisibleNotifications.contains(visObj);
                if (isVisible) {
                    this.mTmpCurrentlyVisibleNotifications.add(visObj);
                    if (!previouslyVisible) {
                        this.mTmpNewlyVisibleNotifications.add(visObj);
                    }
                } else {
                    visObj.recycle();
                }
            }
            this.mTmpNoLongerVisibleNotifications.addAll(NotificationLogger.this.mCurrentlyVisibleNotifications);
            this.mTmpNoLongerVisibleNotifications.removeAll((ArraySet<? extends NotificationVisibility>) this.mTmpCurrentlyVisibleNotifications);
            NotificationLogger.this.logNotificationVisibilityChanges(this.mTmpNewlyVisibleNotifications, this.mTmpNoLongerVisibleNotifications);
            NotificationLogger.this.recycleAllVisibilityObjects((ArraySet<NotificationVisibility>) NotificationLogger.this.mCurrentlyVisibleNotifications);
            NotificationLogger.this.mCurrentlyVisibleNotifications.addAll((ArraySet) this.mTmpCurrentlyVisibleNotifications);
            NotificationLogger.this.recycleAllVisibilityObjects(this.mTmpNoLongerVisibleNotifications);
            this.mTmpCurrentlyVisibleNotifications.clear();
            this.mTmpNewlyVisibleNotifications.clear();
            this.mTmpNoLongerVisibleNotifications.clear();
        }
    };
    protected IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    public interface OnChildLocationsChangedListener {
        void onChildLocationsChanged();
    }

    public void setUpWithEntryManager(NotificationEntryManager entryManager, NotificationListContainer listContainer) {
        this.mEntryManager = entryManager;
        this.mListContainer = listContainer;
    }

    public void stopNotificationLogging() {
        if (!this.mCurrentlyVisibleNotifications.isEmpty()) {
            logNotificationVisibilityChanges(Collections.emptyList(), this.mCurrentlyVisibleNotifications);
            recycleAllVisibilityObjects(this.mCurrentlyVisibleNotifications);
        }
        this.mHandler.removeCallbacks(this.mVisibilityReporter);
        this.mListContainer.setChildLocationsChangedListener(null);
    }

    public void startNotificationLogging() {
        this.mListContainer.setChildLocationsChangedListener(this.mNotificationLocationsChangedListener);
        this.mNotificationLocationsChangedListener.onChildLocationsChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logNotificationVisibilityChanges(final Collection<NotificationVisibility> newlyVisible, Collection<NotificationVisibility> noLongerVisible) {
        if (newlyVisible.isEmpty() && noLongerVisible.isEmpty()) {
            return;
        }
        final NotificationVisibility[] newlyVisibleAr = cloneVisibilitiesAsArr(newlyVisible);
        final NotificationVisibility[] noLongerVisibleAr = cloneVisibilitiesAsArr(noLongerVisible);
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationLogger$dDuzCaPCc3FmeArDx2PTcXDC9B8
            @Override // java.lang.Runnable
            public final void run() {
                NotificationLogger.lambda$logNotificationVisibilityChanges$0(this.f$0, newlyVisibleAr, noLongerVisibleAr, newlyVisible);
            }
        });
    }

    public static /* synthetic */ void lambda$logNotificationVisibilityChanges$0(NotificationLogger notificationLogger, NotificationVisibility[] newlyVisibleAr, NotificationVisibility[] noLongerVisibleAr, Collection newlyVisible) {
        try {
            notificationLogger.mBarService.onNotificationVisibilityChanged(newlyVisibleAr, noLongerVisibleAr);
        } catch (RemoteException e) {
        }
        int N = newlyVisible.size();
        if (N > 0) {
            String[] newlyVisibleKeyAr = new String[N];
            for (int i = 0; i < N; i++) {
                newlyVisibleKeyAr[i] = newlyVisibleAr[i].key;
            }
            try {
                notificationLogger.mNotificationListener.setNotificationsShown(newlyVisibleKeyAr);
            } catch (RuntimeException e2) {
                Log.d("NotificationLogger", "failed setNotificationsShown: ", e2);
            }
        }
        notificationLogger.recycleAllVisibilityObjects(newlyVisibleAr);
        notificationLogger.recycleAllVisibilityObjects(noLongerVisibleAr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleAllVisibilityObjects(ArraySet<NotificationVisibility> array) {
        int N = array.size();
        for (int i = 0; i < N; i++) {
            array.valueAt(i).recycle();
        }
        array.clear();
    }

    private void recycleAllVisibilityObjects(NotificationVisibility[] array) {
        int N = array.length;
        for (int i = 0; i < N; i++) {
            if (array[i] != null) {
                array[i].recycle();
            }
        }
    }

    private NotificationVisibility[] cloneVisibilitiesAsArr(Collection<NotificationVisibility> c) {
        NotificationVisibility[] array = new NotificationVisibility[c.size()];
        int i = 0;
        for (NotificationVisibility nv : c) {
            if (nv != null) {
                array[i] = nv.clone();
            }
            i++;
        }
        return array;
    }

    @VisibleForTesting
    public Runnable getVisibilityReporter() {
        return this.mVisibilityReporter;
    }
}
