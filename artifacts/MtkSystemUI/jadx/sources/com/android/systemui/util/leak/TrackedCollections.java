package com.android.systemui.util.leak;

import android.os.SystemClock;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class TrackedCollections {
    private final WeakIdentityHashMap<Collection<?>, CollectionState> mCollections = new WeakIdentityHashMap<>();

    public synchronized void track(Collection<?> collection, String tag) {
        CollectionState collectionState = this.mCollections.get(collection);
        if (collectionState == null) {
            collectionState = new CollectionState();
            collectionState.tag = tag;
            collectionState.startUptime = SystemClock.uptimeMillis();
            this.mCollections.put(collection, collectionState);
        }
        if (collectionState.halfwayCount == -1 && SystemClock.uptimeMillis() - collectionState.startUptime > 1800000) {
            collectionState.halfwayCount = collectionState.lastCount;
        }
        collectionState.lastCount = collection.size();
        collectionState.lastUptime = SystemClock.uptimeMillis();
    }

    private static class CollectionState {
        int halfwayCount;
        int lastCount;
        long lastUptime;
        long startUptime;
        String tag;

        private CollectionState() {
            this.halfwayCount = -1;
            this.lastCount = -1;
        }

        void dump(PrintWriter pw) {
            long now = SystemClock.uptimeMillis();
            pw.format("%s: %.2f (start-30min) / %.2f (30min-now) / %.2f (start-now) (growth rate in #/hour); %d (current size)", this.tag, Float.valueOf(ratePerHour(this.startUptime, 0, this.startUptime + 1800000, this.halfwayCount)), Float.valueOf(ratePerHour(this.startUptime + 1800000, this.halfwayCount, now, this.lastCount)), Float.valueOf(ratePerHour(this.startUptime, 0, now, this.lastCount)), Integer.valueOf(this.lastCount));
        }

        private float ratePerHour(long uptime1, int count1, long uptime2, int count2) {
            if (uptime1 >= uptime2 || count1 < 0 || count2 < 0) {
                return Float.NaN;
            }
            return ((count2 - count1) / (uptime2 - uptime1)) * 60.0f * 60000.0f;
        }
    }

    public synchronized void dump(PrintWriter pw, Predicate<Collection<?>> filter) {
        for (Map.Entry<WeakReference<Collection<?>>, CollectionState> entry : this.mCollections.entrySet()) {
            Collection<?> key = entry.getKey().get();
            if (filter == null || (key != null && filter.test(key))) {
                entry.getValue().dump(pw);
                pw.println();
            }
        }
    }
}
