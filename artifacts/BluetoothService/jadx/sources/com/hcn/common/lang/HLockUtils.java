package com.hcn.common.lang;

import android.text.TextUtils;
import com.autochips.bluetooth.skin.SkinID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public class HLockUtils {
    private static ConcurrentHashMap<String, AutoLock> sLockMap = new ConcurrentHashMap<>();

    public static AutoLock getAutoLock(final String str) {
        AutoLock autoLockLock;
        synchronized (str.intern()) {
            autoLockLock = sLockMap.computeIfAbsent(str, new Function() { // from class: com.hcn.common.lang.HLockUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return HLockUtils.lambda$getAutoLock$0(str, (String) obj);
                }
            }).lock();
        }
        return autoLockLock;
    }

    static /* synthetic */ AutoLock lambda$getAutoLock$0(String str, String str2) {
        return new AutoLock(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void removeAutoLock(String str) {
        sLockMap.remove(str);
    }

    public static class AutoLock implements AutoCloseable {
        private String mLockKey;
        private final ReentrantLock mReentrantLock = new ReentrantLock();
        private int mRefNumber = 0;

        public AutoLock(String str) {
            this.mLockKey = SkinID.SKIN_NONE;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mLockKey = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public AutoLock lock() {
            this.mRefNumber++;
            this.mReentrantLock.lock();
            return this;
        }

        private AutoLock unLock() {
            ReentrantLock reentrantLock = this.mReentrantLock;
            if (reentrantLock != null && reentrantLock.isLocked()) {
                this.mReentrantLock.unlock();
                synchronized (this.mLockKey.intern()) {
                    int i = this.mRefNumber - 1;
                    this.mRefNumber = i;
                    if (i == 0) {
                        HLockUtils.removeAutoLock(this.mLockKey);
                    }
                }
            }
            return this;
        }

        @Override // java.lang.AutoCloseable
        public void close() throws Exception {
            unLock();
        }
    }
}
