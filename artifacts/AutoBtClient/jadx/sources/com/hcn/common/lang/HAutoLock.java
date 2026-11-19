package com.hcn.common.lang;

import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class HAutoLock implements AutoCloseable {
    private final ReentrantLock mReentrantLock = new ReentrantLock();

    public HAutoLock lock() {
        this.mReentrantLock.lock();
        return this;
    }

    public static HAutoLock getAutoLock() {
        return new HAutoLock().lock();
    }

    private HAutoLock unLock() {
        ReentrantLock reentrantLock = this.mReentrantLock;
        if (reentrantLock != null && reentrantLock.isLocked()) {
            this.mReentrantLock.unlock();
        }
        return this;
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        unLock();
    }
}
