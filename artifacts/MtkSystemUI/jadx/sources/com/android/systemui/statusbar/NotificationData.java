package com.android.systemui.statusbar;

import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.Person;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.NotificationColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationData {
    private static final boolean DEBUG = Log.isLoggable("NotificationData", 3);
    private List hiddenList;
    private Context mContext;
    private final Environment mEnvironment;
    private NotificationGroupManager mGroupManager;
    private HeadsUpManager mHeadsUpManager;
    private NotificationListenerService.RankingMap mRankingMap;
    final ZenModeController mZen = (ZenModeController) Dependency.get(ZenModeController.class);
    final ForegroundServiceController mFsc = (ForegroundServiceController) Dependency.get(ForegroundServiceController.class);
    private final ArrayMap<String, Entry> mEntries = new ArrayMap<>();
    private final ArrayList<Entry> mSortedAndFiltered = new ArrayList<>();
    private final ArrayList<Entry> mFilteredForUser = new ArrayList<>();
    private final NotificationListenerService.Ranking mTmpRanking = new NotificationListenerService.Ranking();
    private final Comparator<Entry> mRankingComparator = new Comparator<Entry>() { // from class: com.android.systemui.statusbar.NotificationData.1
        private final NotificationListenerService.Ranking mRankingA = new NotificationListenerService.Ranking();
        private final NotificationListenerService.Ranking mRankingB = new NotificationListenerService.Ranking();

        @Override // java.util.Comparator
        public int compare(Entry a, Entry b) {
            StatusBarNotification na = a.notification;
            StatusBarNotification nb = b.notification;
            int aImportance = 3;
            int bImportance = 3;
            int aRank = 0;
            int bRank = 0;
            if (NotificationData.this.mRankingMap != null) {
                NotificationData.this.getRanking(a.key, this.mRankingA);
                NotificationData.this.getRanking(b.key, this.mRankingB);
                aImportance = this.mRankingA.getImportance();
                bImportance = this.mRankingB.getImportance();
                aRank = this.mRankingA.getRank();
                bRank = this.mRankingB.getRank();
            }
            String mediaNotification = NotificationData.this.mEnvironment.getCurrentMediaNotificationKey();
            boolean bSystemMax = false;
            boolean aMedia = a.key.equals(mediaNotification) && aImportance > 1;
            boolean bMedia = b.key.equals(mediaNotification) && bImportance > 1;
            boolean aSystemMax = aImportance >= 4 && NotificationData.isSystemNotification(na);
            if (bImportance >= 4 && NotificationData.isSystemNotification(nb)) {
                bSystemMax = true;
            }
            boolean isHeadsUp = a.row.isHeadsUp();
            if (isHeadsUp != b.row.isHeadsUp()) {
                return isHeadsUp ? -1 : 1;
            }
            if (isHeadsUp) {
                return NotificationData.this.mHeadsUpManager.compare(a, b);
            }
            if (aMedia != bMedia) {
                return aMedia ? -1 : 1;
            }
            if (aSystemMax != bSystemMax) {
                return aSystemMax ? -1 : 1;
            }
            if (aRank != bRank) {
                return aRank - bRank;
            }
            return Long.compare(nb.getNotification().when, na.getNotification().when);
        }
    };

    public interface Environment {
        String getCurrentMediaNotificationKey();

        NotificationGroupManager getGroupManager();

        boolean isDeviceProvisioned();

        boolean isDozing();

        boolean isNotificationForCurrentProfiles(StatusBarNotification statusBarNotification);

        boolean isSecurelyLocked(int i);

        boolean shouldHideNotifications(int i);

        boolean shouldHideNotifications(String str);
    }

    public static final class Entry {
        public boolean autoRedacted;
        public RemoteViews cachedAmbientContentView;
        public RemoteViews cachedBigContentView;
        public RemoteViews cachedContentView;
        public RemoteViews cachedHeadsUpContentView;
        public RemoteViews cachedPublicContentView;
        public NotificationChannel channel;
        public StatusBarIconView expandedIcon;
        private boolean hasSentReply;
        public CharSequence headsUpStatusBarText;
        public CharSequence headsUpStatusBarTextPublic;
        public StatusBarIconView icon;
        private boolean interruption;
        public String key;
        private Throwable mDebugThrowable;
        public Boolean mIsSystemNotification;
        public StatusBarNotification notification;
        public CharSequence remoteInputText;
        public CharSequence remoteInputTextWhenReset;
        public ExpandableNotificationRow row;
        public List<SnoozeCriterion> snoozeCriteria;
        public int targetSdk;
        private long lastFullScreenIntentLaunchTime = -2000;
        public int userSentiment = 0;
        private int mCachedContrastColor = 1;
        private int mCachedContrastColorIsFor = 1;
        private InflationTask mRunningTask = null;
        public long lastRemoteInputSent = -2000;
        public ArraySet<Integer> mActiveAppOps = new ArraySet<>(3);

        public Entry(StatusBarNotification n) {
            this.key = n.getKey();
            this.notification = n;
        }

        public void setInterruption() {
            this.interruption = true;
        }

        public boolean hasInterrupted() {
            return this.interruption;
        }

        public void reset() {
            if (this.row != null) {
                this.row.reset();
            }
        }

        public View getExpandedContentView() {
            return this.row.getPrivateLayout().getExpandedChild();
        }

        public void notifyFullScreenIntentLaunched() {
            setInterruption();
            this.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
        }

        public boolean hasJustLaunchedFullScreenIntent() {
            return SystemClock.elapsedRealtime() < this.lastFullScreenIntentLaunchTime + 2000;
        }

        public boolean hasJustSentRemoteInput() {
            return SystemClock.elapsedRealtime() < this.lastRemoteInputSent + 500;
        }

        public void createIcons(Context context, StatusBarNotification sbn) throws InflationException {
            Notification n = sbn.getNotification();
            Icon smallIcon = n.getSmallIcon();
            if (smallIcon == null) {
                if (NotificationData.DEBUG) {
                    Log.d("NotificationData", "createIcons, smallIcon == null, sbn: " + sbn);
                }
                throw new InflationException("No small icon in notification from " + sbn.getPackageName());
            }
            this.icon = new StatusBarIconView(context, sbn.getPackageName() + "/0x" + Integer.toHexString(sbn.getId()), sbn);
            this.icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.expandedIcon = new StatusBarIconView(context, sbn.getPackageName() + "/0x" + Integer.toHexString(sbn.getId()), sbn);
            this.expandedIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            StatusBarIcon ic = new StatusBarIcon(sbn.getUser(), sbn.getPackageName(), smallIcon, n.iconLevel, n.number, StatusBarIconView.contentDescForNotification(context, n));
            if (!this.icon.set(ic) || !this.expandedIcon.set(ic)) {
                this.icon = null;
                this.expandedIcon = null;
                if (NotificationData.DEBUG) {
                    Log.d("NotificationData", "createIcons, set failed, sbn: " + sbn);
                }
                throw new InflationException("Couldn't create icon: " + ic);
            }
            this.expandedIcon.setVisibility(4);
            this.expandedIcon.setOnVisibilityChangedListener(new StatusBarIconView.OnVisibilityChangedListener() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationData$Entry$htRh1w4D2IBYeXc1CwB54DGLhpM
                @Override // com.android.systemui.statusbar.StatusBarIconView.OnVisibilityChangedListener
                public final void onVisibilityChanged(int i) {
                    NotificationData.Entry.lambda$createIcons$0(this.f$0, i);
                }
            });
        }

        public static /* synthetic */ void lambda$createIcons$0(Entry entry, int newVisibility) {
            if (entry.row != null) {
                entry.row.setIconsVisible(newVisibility != 0);
            }
        }

        public void setIconTag(int key, Object tag) {
            if (this.icon != null) {
                this.icon.setTag(key, tag);
                this.expandedIcon.setTag(key, tag);
            }
        }

        public void updateIcons(Context context, StatusBarNotification sbn) throws InflationException {
            if (this.icon != null) {
                Notification n = sbn.getNotification();
                StatusBarIcon ic = new StatusBarIcon(this.notification.getUser(), this.notification.getPackageName(), n.getSmallIcon(), n.iconLevel, n.number, StatusBarIconView.contentDescForNotification(context, n));
                this.icon.setNotification(sbn);
                this.expandedIcon.setNotification(sbn);
                if (!this.icon.set(ic) || !this.expandedIcon.set(ic)) {
                    if (NotificationData.DEBUG) {
                        Log.d("NotificationData", "updateIcons, set failed, sbn: " + sbn);
                    }
                    throw new InflationException("Couldn't update icon: " + ic);
                }
            }
        }

        public int getContrastedColor(Context context, boolean isLowPriority, int backgroundColor) {
            int rawColor = isLowPriority ? 0 : this.notification.getNotification().color;
            if (this.mCachedContrastColorIsFor == rawColor && this.mCachedContrastColor != 1) {
                return this.mCachedContrastColor;
            }
            int contrasted = NotificationColorUtil.resolveContrastColor(context, rawColor, backgroundColor);
            this.mCachedContrastColorIsFor = rawColor;
            this.mCachedContrastColor = contrasted;
            return this.mCachedContrastColor;
        }

        public void abortTask() {
            if (this.mRunningTask != null) {
                this.mRunningTask.abort();
                this.mRunningTask = null;
            }
        }

        public void setInflationTask(InflationTask abortableTask) {
            InflationTask existing = this.mRunningTask;
            abortTask();
            this.mRunningTask = abortableTask;
            if (existing != null && this.mRunningTask != null) {
                this.mRunningTask.supersedeTask(existing);
            }
        }

        public void onInflationTaskFinished() {
            this.mRunningTask = null;
        }

        @VisibleForTesting
        public InflationTask getRunningTask() {
            return this.mRunningTask;
        }

        public void setDebugThrowable(Throwable debugThrowable) {
            this.mDebugThrowable = debugThrowable;
        }

        public Throwable getDebugThrowable() {
            return this.mDebugThrowable;
        }

        public void onRemoteInputInserted() {
            this.lastRemoteInputSent = -2000L;
            this.remoteInputTextWhenReset = null;
        }

        public void setHasSentReply() {
            this.hasSentReply = true;
        }

        public boolean isLastMessageFromReply() {
            Notification.MessagingStyle.Message lastMessage;
            if (!this.hasSentReply) {
                return false;
            }
            Bundle extras = this.notification.getNotification().extras;
            CharSequence[] replyTexts = extras.getCharSequenceArray("android.remoteInputHistory");
            if (!ArrayUtils.isEmpty(replyTexts)) {
                return true;
            }
            Parcelable[] messages = extras.getParcelableArray("android.messages");
            if (messages != null && messages.length > 0) {
                Parcelable message = messages[messages.length - 1];
                if ((message instanceof Bundle) && (lastMessage = Notification.MessagingStyle.Message.getMessageFromBundle((Bundle) message)) != null) {
                    Person senderPerson = lastMessage.getSenderPerson();
                    if (senderPerson == null) {
                        return true;
                    }
                    Person user = (Person) extras.getParcelable("android.messagingUser");
                    return Objects.equals(user, senderPerson);
                }
            }
            return false;
        }
    }

    public void setHeadsUpManager(HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
    }

    public NotificationData(Environment environment, Context context) {
        this.mEnvironment = environment;
        this.mGroupManager = environment.getGroupManager();
        this.mContext = context;
        this.hiddenList = Arrays.asList(this.mContext.getResources().getStringArray(R.array.notification_hidden_list));
    }

    public ArrayList<Entry> getActiveNotifications() {
        return this.mSortedAndFiltered;
    }

    public ArrayList<Entry> getNotificationsForCurrentUser() {
        this.mFilteredForUser.clear();
        synchronized (this.mEntries) {
            int N = this.mEntries.size();
            for (int i = 0; i < N; i++) {
                Entry entry = this.mEntries.valueAt(i);
                StatusBarNotification sbn = entry.notification;
                if (this.mEnvironment.isNotificationForCurrentProfiles(sbn)) {
                    this.mFilteredForUser.add(entry);
                }
            }
        }
        return this.mFilteredForUser;
    }

    public Entry get(String key) {
        return this.mEntries.get(key);
    }

    public void add(Entry entry) {
        synchronized (this.mEntries) {
            this.mEntries.put(entry.notification.getKey(), entry);
        }
        this.mGroupManager.onEntryAdded(entry);
        updateRankingAndSort(this.mRankingMap);
    }

    public Entry remove(String key, NotificationListenerService.RankingMap ranking) {
        Entry removed;
        synchronized (this.mEntries) {
            removed = this.mEntries.remove(key);
        }
        if (removed == null) {
            return null;
        }
        this.mGroupManager.onEntryRemoved(removed);
        updateRankingAndSort(ranking);
        return removed;
    }

    public void updateRanking(NotificationListenerService.RankingMap ranking) {
        updateRankingAndSort(ranking);
    }

    public void updateAppOp(int appOp, int uid, String pkg, String key, boolean showIcon) {
        synchronized (this.mEntries) {
            int N = this.mEntries.size();
            for (int i = 0; i < N; i++) {
                Entry entry = this.mEntries.valueAt(i);
                if (uid == entry.notification.getUid() && pkg.equals(entry.notification.getPackageName()) && key.equals(entry.key)) {
                    if (showIcon) {
                        entry.mActiveAppOps.add(Integer.valueOf(appOp));
                    } else {
                        entry.mActiveAppOps.remove(Integer.valueOf(appOp));
                    }
                }
            }
        }
    }

    public boolean isAmbient(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.isAmbient();
        }
        return false;
    }

    public int getVisibilityOverride(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.getVisibilityOverride();
        }
        return -1000;
    }

    public boolean shouldSuppressFullScreenIntent(Entry entry) {
        return shouldSuppressVisualEffect(entry, 4);
    }

    public boolean shouldSuppressPeek(Entry entry) {
        return shouldSuppressVisualEffect(entry, 16);
    }

    public boolean shouldSuppressStatusBar(Entry entry) {
        return shouldSuppressVisualEffect(entry, 32);
    }

    public boolean shouldSuppressAmbient(Entry entry) {
        return shouldSuppressVisualEffect(entry, 128);
    }

    public boolean shouldSuppressNotificationList(Entry entry) {
        return shouldSuppressVisualEffect(entry, 256);
    }

    private boolean shouldSuppressVisualEffect(Entry entry, int effect) {
        if (isExemptFromDndVisualSuppression(entry)) {
            return false;
        }
        String key = entry.key;
        if (this.mRankingMap == null) {
            return false;
        }
        getRanking(key, this.mTmpRanking);
        return (this.mTmpRanking.getSuppressedVisualEffects() & effect) != 0;
    }

    protected boolean isExemptFromDndVisualSuppression(Entry entry) {
        if (isNotificationBlockedByPolicy(entry.notification.getNotification())) {
            return false;
        }
        if ((entry.notification.getNotification().flags & 64) == 0 && !entry.notification.getNotification().isMediaNotification()) {
            return entry.mIsSystemNotification != null && entry.mIsSystemNotification.booleanValue();
        }
        return true;
    }

    protected boolean isNotificationBlockedByPolicy(Notification n) {
        if (isCategory("call", n) || isCategory("msg", n) || isCategory("alarm", n) || isCategory("event", n) || isCategory("reminder", n)) {
            return true;
        }
        return false;
    }

    private boolean isCategory(String category, Notification n) {
        return Objects.equals(n.category, category);
    }

    public int getImportance(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.getImportance();
        }
        return -1000;
    }

    public String getOverrideGroupKey(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.getOverrideGroupKey();
        }
        return null;
    }

    public List<SnoozeCriterion> getSnoozeCriteria(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.getSnoozeCriteria();
        }
        return null;
    }

    public NotificationChannel getChannel(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.getChannel();
        }
        return null;
    }

    public int getRank(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.getRank();
        }
        return 0;
    }

    public boolean shouldHide(String key) {
        if (this.mRankingMap != null) {
            getRanking(key, this.mTmpRanking);
            return this.mTmpRanking.isSuspended();
        }
        return false;
    }

    private void updateRankingAndSort(NotificationListenerService.RankingMap ranking) {
        if (ranking != null) {
            this.mRankingMap = ranking;
            synchronized (this.mEntries) {
                int N = this.mEntries.size();
                for (int i = 0; i < N; i++) {
                    Entry entry = this.mEntries.valueAt(i);
                    if (getRanking(entry.key, this.mTmpRanking)) {
                        StatusBarNotification oldSbn = entry.notification.cloneLight();
                        String overrideGroupKey = getOverrideGroupKey(entry.key);
                        if (!Objects.equals(oldSbn.getOverrideGroupKey(), overrideGroupKey)) {
                            entry.notification.setOverrideGroupKey(overrideGroupKey);
                            this.mGroupManager.onEntryUpdated(entry, oldSbn);
                        }
                        entry.channel = getChannel(entry.key);
                        entry.snoozeCriteria = getSnoozeCriteria(entry.key);
                        entry.userSentiment = this.mTmpRanking.getUserSentiment();
                    }
                }
            }
        }
        filterAndSort();
    }

    @VisibleForTesting
    protected boolean getRanking(String key, NotificationListenerService.Ranking outRanking) {
        return this.mRankingMap.getRanking(key, outRanking);
    }

    public void filterAndSort() {
        this.mSortedAndFiltered.clear();
        synchronized (this.mEntries) {
            int N = this.mEntries.size();
            for (int i = 0; i < N; i++) {
                Entry entry = this.mEntries.valueAt(i);
                if (!shouldFilterOut(entry)) {
                    this.mSortedAndFiltered.add(entry);
                }
            }
        }
        Collections.sort(this.mSortedAndFiltered, this.mRankingComparator);
    }

    public boolean shouldFilterOut(Entry entry) {
        String[] apps;
        StatusBarNotification sbn = entry.notification;
        if (DEBUG) {
            Log.d("NotificationData", "[shouldFilterOut]" + sbn.toString());
        }
        Notification notification = sbn.getNotification();
        if (notification != null) {
            String channelId = notification.getChannelId();
            if ("hdmi_notification_channel".equals(channelId)) {
                return true;
            }
        }
        if (this.hiddenList != null && this.hiddenList.contains(sbn.getPackageName())) {
            return true;
        }
        if ("com.williexing.android.apps.xcdvr1".equals(sbn.getPackageName()) && sbn.getId() == 1) {
            return true;
        }
        if ((!this.mEnvironment.isDeviceProvisioned() && !showNotificationEvenIfUnprovisioned(sbn)) || !this.mEnvironment.isNotificationForCurrentProfiles(sbn)) {
            return true;
        }
        if (this.mEnvironment.isSecurelyLocked(sbn.getUserId()) && (sbn.getNotification().visibility == -1 || this.mEnvironment.shouldHideNotifications(sbn.getUserId()) || this.mEnvironment.shouldHideNotifications(sbn.getKey()))) {
            return true;
        }
        if (this.mEnvironment.isDozing() && shouldSuppressAmbient(entry)) {
            return true;
        }
        if ((!this.mEnvironment.isDozing() && shouldSuppressNotificationList(entry)) || shouldHide(sbn.getKey())) {
            return true;
        }
        if (!StatusBar.ENABLE_CHILD_NOTIFICATIONS && this.mGroupManager.isChildInGroupWithSummary(sbn)) {
            return true;
        }
        if (!this.mFsc.isDungeonNotification(sbn) || this.mFsc.isDungeonNeededForUser(sbn.getUserId())) {
            return this.mFsc.isSystemAlertNotification(sbn) && (apps = sbn.getNotification().extras.getStringArray("android.foregroundApps")) != null && apps.length >= 1 && !this.mFsc.isSystemAlertWarningNeeded(sbn.getUserId(), apps[0]);
        }
        return true;
    }

    public static boolean showNotificationEvenIfUnprovisioned(StatusBarNotification sbn) {
        return showNotificationEvenIfUnprovisioned(AppGlobals.getPackageManager(), sbn);
    }

    @VisibleForTesting
    static boolean showNotificationEvenIfUnprovisioned(IPackageManager packageManager, StatusBarNotification sbn) {
        return checkUidPermission(packageManager, "android.permission.NOTIFICATION_DURING_SETUP", sbn.getUid()) == 0 && sbn.getNotification().extras.getBoolean("android.allowDuringSetup");
    }

    private static int checkUidPermission(IPackageManager packageManager, String permission, int uid) {
        try {
            return packageManager.checkUidPermission(permission, uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void dump(PrintWriter pw, String indent) {
        int N = this.mSortedAndFiltered.size();
        pw.print(indent);
        pw.println("active notifications: " + N);
        int active = 0;
        while (active < N) {
            Entry e = this.mSortedAndFiltered.get(active);
            dumpEntry(pw, indent, active, e);
            active++;
        }
        synchronized (this.mEntries) {
            int M = this.mEntries.size();
            pw.print(indent);
            pw.println("inactive notifications: " + (M - active));
            int inactiveCount = 0;
            for (int i = 0; i < M; i++) {
                Entry entry = this.mEntries.valueAt(i);
                if (!this.mSortedAndFiltered.contains(entry)) {
                    dumpEntry(pw, indent, inactiveCount, entry);
                    inactiveCount++;
                }
            }
        }
    }

    private void dumpEntry(PrintWriter pw, String indent, int i, Entry e) {
        getRanking(e.key, this.mTmpRanking);
        pw.print(indent);
        pw.println("  [" + i + "] key=" + e.key + " icon=" + e.icon);
        StatusBarNotification n = e.notification;
        pw.print(indent);
        pw.println("      pkg=" + n.getPackageName() + " id=" + n.getId() + " importance=" + this.mTmpRanking.getImportance());
        pw.print(indent);
        StringBuilder sb = new StringBuilder();
        sb.append("      notification=");
        sb.append(n.getNotification());
        pw.println(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSystemNotification(StatusBarNotification sbn) {
        String sbnPackage = sbn.getPackageName();
        return "android".equals(sbnPackage) || "com.android.systemui".equals(sbnPackage);
    }
}
