package androidx.car.moderator;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
class SystemClockTimeProvider implements ContentRateLimiter.ElapsedTimeProvider {
    SystemClockTimeProvider() {
    }

    @Override // androidx.car.moderator.ContentRateLimiter.ElapsedTimeProvider
    public long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }
}
