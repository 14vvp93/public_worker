package com.android.systemui.util.leak;

import android.os.SystemClock;
import android.util.ArrayMap;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TrackedGarbage {
    private final HashSet<LeakReference> mGarbage = new HashSet<>();
    private final ReferenceQueue<Object> mRefQueue = new ReferenceQueue<>();
    private final TrackedCollections mTrackedCollections;

    public TrackedGarbage(TrackedCollections trackedCollections) {
        this.mTrackedCollections = trackedCollections;
    }

    public synchronized void track(Object o) {
        cleanUp();
        this.mGarbage.add(new LeakReference(o, this.mRefQueue));
        this.mTrackedCollections.track(this.mGarbage, "Garbage");
    }

    private void cleanUp() {
        while (true) {
            Reference<?> ref = this.mRefQueue.poll();
            if (ref != null) {
                this.mGarbage.remove(ref);
            } else {
                return;
            }
        }
    }

    private static class LeakReference extends WeakReference<Object> {
        private final Class<?> clazz;
        private final long createdUptimeMillis;

        LeakReference(Object t, ReferenceQueue<Object> queue) {
            super(t, queue);
            this.clazz = t.getClass();
            this.createdUptimeMillis = SystemClock.uptimeMillis();
        }
    }

    public synchronized void dump(PrintWriter pw) {
        cleanUp();
        long now = SystemClock.uptimeMillis();
        ArrayMap<Class<?>, Integer> acc = new ArrayMap<>();
        ArrayMap<Class<?>, Integer> accOld = new ArrayMap<>();
        for (LeakReference ref : this.mGarbage) {
            acc.put(ref.clazz, Integer.valueOf(acc.getOrDefault(ref.clazz, 0).intValue() + 1));
            if (isOld(ref.createdUptimeMillis, now)) {
                accOld.put(ref.clazz, Integer.valueOf(accOld.getOrDefault(ref.clazz, 0).intValue() + 1));
            }
        }
        for (Map.Entry<Class<?>, Integer> entry : acc.entrySet()) {
            pw.print(entry.getKey().getName());
            pw.print(": ");
            pw.print(entry.getValue());
            pw.print(" total, ");
            pw.print(accOld.getOrDefault(entry.getKey(), 0));
            pw.print(" old");
            pw.println();
        }
    }

    public synchronized int countOldGarbage() {
        int result;
        cleanUp();
        long now = SystemClock.uptimeMillis();
        result = 0;
        for (LeakReference ref : this.mGarbage) {
            if (isOld(ref.createdUptimeMillis, now)) {
                result++;
            }
        }
        return result;
    }

    private boolean isOld(long createdUptimeMillis, long now) {
        return 60000 + createdUptimeMillis < now;
    }
}
