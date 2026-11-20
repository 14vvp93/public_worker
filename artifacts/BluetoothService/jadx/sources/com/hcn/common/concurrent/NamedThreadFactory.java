package com.hcn.common.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class NamedThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final String namePrefix;
    private final SecurityManager securityManager;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public NamedThreadFactory(String str) {
        SecurityManager securityManager = System.getSecurityManager();
        this.securityManager = securityManager;
        this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = str;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.group, runnable, this.namePrefix + "-thread-" + this.threadNumber.getAndIncrement(), 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        return thread;
    }
}
