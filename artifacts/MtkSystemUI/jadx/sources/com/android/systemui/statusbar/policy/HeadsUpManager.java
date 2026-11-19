package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.ArrayMap;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.NotificationData;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpManager {
    protected final Context mContext;
    protected boolean mHasPinnedNotification;
    protected int mHeadsUpNotificationDecay;
    protected int mMinimumDisplayTime;
    protected int mSnoozeLengthMs;
    private final ArrayMap<String, Long> mSnoozedPackages;
    protected int mTouchAcceptanceDelay;
    protected int mUser;
    protected final Clock mClock = new Clock();
    protected final HashSet<OnHeadsUpChangedListener> mListeners = new HashSet<>();
    protected final Handler mHandler = new Handler(Looper.getMainLooper());
    private final HashMap<String, HeadsUpEntry> mHeadsUpEntries = new HashMap<>();

    public HeadsUpManager(final Context context) {
        this.mContext = context;
        Resources resources = context.getResources();
        this.mMinimumDisplayTime = resources.getInteger(R.integer.heads_up_notification_minimum_time);
        this.mHeadsUpNotificationDecay = resources.getInteger(R.integer.heads_up_notification_decay);
        this.mTouchAcceptanceDelay = resources.getInteger(R.integer.touch_acceptance_delay);
        this.mSnoozedPackages = new ArrayMap<>();
        int defaultSnoozeLengthMs = resources.getInteger(R.integer.heads_up_default_snooze_length_ms);
        this.mSnoozeLengthMs = Settings.Global.getInt(context.getContentResolver(), "heads_up_snooze_length_ms", defaultSnoozeLengthMs);
        ContentObserver settingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.statusbar.policy.HeadsUpManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                int packageSnoozeLengthMs = Settings.Global.getInt(context.getContentResolver(), "heads_up_snooze_length_ms", -1);
                if (packageSnoozeLengthMs > -1 && packageSnoozeLengthMs != HeadsUpManager.this.mSnoozeLengthMs) {
                    HeadsUpManager.this.mSnoozeLengthMs = packageSnoozeLengthMs;
                }
            }
        };
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("heads_up_snooze_length_ms"), false, settingsObserver);
    }

    public void addListener(OnHeadsUpChangedListener listener) {
        this.mListeners.add(listener);
    }

    public void removeListener(OnHeadsUpChangedListener listener) {
        this.mListeners.remove(listener);
    }

    public void showNotification(NotificationData.Entry headsUp) {
        addHeadsUpEntry(headsUp);
        updateNotification(headsUp, true);
        headsUp.setInterruption();
    }

    public void updateNotification(NotificationData.Entry headsUp, boolean alert) {
        HeadsUpEntry headsUpEntry;
        headsUp.row.sendAccessibilityEvent(2048);
        if (!alert || (headsUpEntry = this.mHeadsUpEntries.get(headsUp.key)) == null) {
            return;
        }
        headsUpEntry.updateEntry(true);
        setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(headsUp));
    }

    private void addHeadsUpEntry(NotificationData.Entry entry) {
        HeadsUpEntry headsUpEntry = createHeadsUpEntry();
        headsUpEntry.setEntry(entry);
        this.mHeadsUpEntries.put(entry.key, headsUpEntry);
        entry.row.setHeadsUp(true);
        setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(entry));
        for (OnHeadsUpChangedListener listener : this.mListeners) {
            listener.onHeadsUpStateChanged(entry, true);
        }
        entry.row.sendAccessibilityEvent(2048);
    }

    protected boolean shouldHeadsUpBecomePinned(NotificationData.Entry entry) {
        return hasFullScreenIntent(entry);
    }

    protected boolean hasFullScreenIntent(NotificationData.Entry entry) {
        return entry.notification.getNotification().fullScreenIntent != null;
    }

    protected void setEntryPinned(HeadsUpEntry headsUpEntry, boolean isPinned) {
        ExpandableNotificationRow row = headsUpEntry.entry.row;
        if (row.isPinned() != isPinned) {
            row.setPinned(isPinned);
            updatePinnedMode();
            for (OnHeadsUpChangedListener listener : this.mListeners) {
                if (isPinned) {
                    listener.onHeadsUpPinned(row);
                } else {
                    listener.onHeadsUpUnPinned(row);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void removeHeadsUpEntry(NotificationData.Entry entry) {
        HeadsUpEntry remove = this.mHeadsUpEntries.remove(entry.key);
        onHeadsUpEntryRemoved(remove);
    }

    protected void onHeadsUpEntryRemoved(HeadsUpEntry remove) {
        NotificationData.Entry entry = remove.entry;
        entry.row.sendAccessibilityEvent(2048);
        entry.row.setHeadsUp(false);
        setEntryPinned(remove, false);
        for (OnHeadsUpChangedListener listener : this.mListeners) {
            listener.onHeadsUpStateChanged(entry, false);
        }
        releaseHeadsUpEntry(remove);
    }

    protected void updatePinnedMode() {
        boolean hasPinnedNotification = hasPinnedNotificationInternal();
        if (hasPinnedNotification == this.mHasPinnedNotification) {
            return;
        }
        this.mHasPinnedNotification = hasPinnedNotification;
        if (this.mHasPinnedNotification) {
            MetricsLogger.count(this.mContext, "note_peek", 1);
        }
        for (OnHeadsUpChangedListener listener : this.mListeners) {
            listener.onHeadsUpPinnedModeChanged(hasPinnedNotification);
        }
    }

    public boolean removeNotification(String key, boolean ignoreEarliestRemovalTime) {
        releaseImmediately(key);
        return true;
    }

    public boolean isHeadsUp(String key) {
        return this.mHeadsUpEntries.containsKey(key);
    }

    public void releaseAllImmediately() {
        Iterator<HeadsUpEntry> iterator = this.mHeadsUpEntries.values().iterator();
        while (iterator.hasNext()) {
            HeadsUpEntry entry = iterator.next();
            iterator.remove();
            onHeadsUpEntryRemoved(entry);
        }
    }

    public void releaseImmediately(String key) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(key);
        if (headsUpEntry == null) {
            return;
        }
        NotificationData.Entry shadeEntry = headsUpEntry.entry;
        removeHeadsUpEntry(shadeEntry);
    }

    public boolean isSnoozed(String packageName) {
        String key = snoozeKey(packageName, this.mUser);
        Long snoozedUntil = this.mSnoozedPackages.get(key);
        if (snoozedUntil == null) {
            return false;
        }
        if (snoozedUntil.longValue() > this.mClock.currentTimeMillis()) {
            return true;
        }
        this.mSnoozedPackages.remove(packageName);
        return false;
    }

    public void snooze() {
        for (String key : this.mHeadsUpEntries.keySet()) {
            HeadsUpEntry entry = this.mHeadsUpEntries.get(key);
            String packageName = entry.entry.notification.getPackageName();
            this.mSnoozedPackages.put(snoozeKey(packageName, this.mUser), Long.valueOf(this.mClock.currentTimeMillis() + ((long) this.mSnoozeLengthMs)));
        }
    }

    private static String snoozeKey(String packageName, int user) {
        return user + "," + packageName;
    }

    protected HeadsUpEntry getHeadsUpEntry(String key) {
        return this.mHeadsUpEntries.get(key);
    }

    public NotificationData.Entry getEntry(String key) {
        HeadsUpEntry entry = this.mHeadsUpEntries.get(key);
        if (entry != null) {
            return entry.entry;
        }
        return null;
    }

    public Stream<NotificationData.Entry> getAllEntries() {
        return this.mHeadsUpEntries.values().stream().map(new Function() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$HeadsUpManager$Q03ExeWuSMcpx0F3Hl5vkzUbslA
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((HeadsUpManager.HeadsUpEntry) obj).entry;
            }
        });
    }

    public NotificationData.Entry getTopEntry() {
        HeadsUpEntry topEntry = getTopHeadsUpEntry();
        if (topEntry != null) {
            return topEntry.entry;
        }
        return null;
    }

    public boolean hasHeadsUpNotifications() {
        return !this.mHeadsUpEntries.isEmpty();
    }

    protected HeadsUpEntry getTopHeadsUpEntry() {
        if (this.mHeadsUpEntries.isEmpty()) {
            return null;
        }
        HeadsUpEntry topEntry = null;
        for (HeadsUpEntry entry : this.mHeadsUpEntries.values()) {
            if (topEntry == null || entry.compareTo(topEntry) < 0) {
                topEntry = entry;
            }
        }
        return topEntry;
    }

    public void setUser(int user) {
        this.mUser = user;
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("HeadsUpManager state:");
        dumpInternal(fd, pw, args);
    }

    protected void dumpInternal(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("  mTouchAcceptanceDelay=");
        pw.println(this.mTouchAcceptanceDelay);
        pw.print("  mSnoozeLengthMs=");
        pw.println(this.mSnoozeLengthMs);
        pw.print("  now=");
        pw.println(this.mClock.currentTimeMillis());
        pw.print("  mUser=");
        pw.println(this.mUser);
        for (HeadsUpEntry entry : this.mHeadsUpEntries.values()) {
            pw.print("  HeadsUpEntry=");
            pw.println(entry.entry);
        }
        int N = this.mSnoozedPackages.size();
        pw.println("  snoozed packages: " + N);
        for (int i = 0; i < N; i++) {
            pw.print("    ");
            pw.print(this.mSnoozedPackages.valueAt(i));
            pw.print(", ");
            pw.println(this.mSnoozedPackages.keyAt(i));
        }
    }

    public boolean hasPinnedHeadsUp() {
        return this.mHasPinnedNotification;
    }

    private boolean hasPinnedNotificationInternal() {
        for (String key : this.mHeadsUpEntries.keySet()) {
            HeadsUpEntry entry = this.mHeadsUpEntries.get(key);
            if (entry.entry.row.isPinned()) {
                return true;
            }
        }
        return false;
    }

    public void unpinAll() {
        for (String key : this.mHeadsUpEntries.keySet()) {
            HeadsUpEntry entry = this.mHeadsUpEntries.get(key);
            setEntryPinned(entry, false);
            entry.updateEntry(false);
        }
    }

    public boolean isTrackingHeadsUp() {
        return false;
    }

    public int compare(NotificationData.Entry a, NotificationData.Entry b) {
        HeadsUpEntry aEntry = getHeadsUpEntry(a.key);
        HeadsUpEntry bEntry = getHeadsUpEntry(b.key);
        if (aEntry == null || bEntry == null) {
            return aEntry == null ? 1 : -1;
        }
        return aEntry.compareTo(bEntry);
    }

    public void setExpanded(NotificationData.Entry entry, boolean expanded) {
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntries.get(entry.key);
        if (headsUpEntry != null && entry.row.isPinned()) {
            headsUpEntry.expanded(expanded);
        }
    }

    protected HeadsUpEntry createHeadsUpEntry() {
        return new HeadsUpEntry();
    }

    protected void releaseHeadsUpEntry(HeadsUpEntry entry) {
        entry.reset();
    }

    public void onDensityOrFontScaleChanged() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public class HeadsUpEntry implements Comparable<HeadsUpEntry> {
        public long earliestRemovaltime;
        public NotificationData.Entry entry;
        public boolean expanded;
        private Runnable mRemoveHeadsUpRunnable;
        public long postTime;
        public boolean remoteInputActive;

        protected HeadsUpEntry() {
        }

        public void setEntry(NotificationData.Entry entry) {
            setEntry(entry, null);
        }

        public void setEntry(NotificationData.Entry entry, Runnable removeHeadsUpRunnable) {
            this.entry = entry;
            this.mRemoveHeadsUpRunnable = removeHeadsUpRunnable;
            this.postTime = HeadsUpManager.this.mClock.currentTimeMillis() + ((long) HeadsUpManager.this.mTouchAcceptanceDelay);
            updateEntry(true);
        }

        public void updateEntry(boolean updatePostTime) {
            long currentTime = HeadsUpManager.this.mClock.currentTimeMillis();
            this.earliestRemovaltime = ((long) HeadsUpManager.this.mMinimumDisplayTime) + currentTime;
            if (updatePostTime) {
                this.postTime = Math.max(this.postTime, currentTime);
            }
            removeAutoRemovalCallbacks();
            if (!isSticky()) {
                long finishTime = this.postTime + ((long) HeadsUpManager.this.mHeadsUpNotificationDecay);
                long removeDelay = Math.max(finishTime - currentTime, HeadsUpManager.this.mMinimumDisplayTime);
                HeadsUpManager.this.mHandler.postDelayed(this.mRemoveHeadsUpRunnable, removeDelay);
            }
        }

        private boolean isSticky() {
            return (this.entry.row.isPinned() && this.expanded) || this.remoteInputActive || HeadsUpManager.this.hasFullScreenIntent(this.entry);
        }

        @Override // java.lang.Comparable
        public int compareTo(HeadsUpEntry o) {
            boolean isPinned = this.entry.row.isPinned();
            boolean otherPinned = o.entry.row.isPinned();
            if (isPinned && !otherPinned) {
                return -1;
            }
            if (!isPinned && otherPinned) {
                return 1;
            }
            boolean selfFullscreen = HeadsUpManager.this.hasFullScreenIntent(this.entry);
            boolean otherFullscreen = HeadsUpManager.this.hasFullScreenIntent(o.entry);
            if (selfFullscreen && !otherFullscreen) {
                return -1;
            }
            if (!selfFullscreen && otherFullscreen) {
                return 1;
            }
            if (this.remoteInputActive && !o.remoteInputActive) {
                return -1;
            }
            if ((this.remoteInputActive || !o.remoteInputActive) && this.postTime >= o.postTime) {
                if (this.postTime == o.postTime) {
                    return this.entry.key.compareTo(o.entry.key);
                }
                return -1;
            }
            return 1;
        }

        public void expanded(boolean expanded) {
            this.expanded = expanded;
        }

        public void reset() {
            this.entry = null;
            this.expanded = false;
            this.remoteInputActive = false;
            removeAutoRemovalCallbacks();
            this.mRemoveHeadsUpRunnable = null;
        }

        public void removeAutoRemovalCallbacks() {
            if (this.mRemoveHeadsUpRunnable != null) {
                HeadsUpManager.this.mHandler.removeCallbacks(this.mRemoveHeadsUpRunnable);
            }
        }

        public void removeAsSoonAsPossible() {
            if (this.mRemoveHeadsUpRunnable != null) {
                removeAutoRemovalCallbacks();
                HeadsUpManager.this.mHandler.postDelayed(this.mRemoveHeadsUpRunnable, this.earliestRemovaltime - HeadsUpManager.this.mClock.currentTimeMillis());
            }
        }
    }

    public static class Clock {
        public long currentTimeMillis() {
            return SystemClock.elapsedRealtime();
        }
    }
}
