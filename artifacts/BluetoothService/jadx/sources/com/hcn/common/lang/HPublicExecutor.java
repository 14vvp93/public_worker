package com.hcn.common.lang;

import android.os.Handler;
import android.os.Looper;
import com.hcn.common.concurrent.NamedThreadFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class HPublicExecutor {
    private static HPublicExecutor sInstance;
    private final ExecutorService mAsyncTaskExecutor;
    private final Handler mMainHandler;
    private int mThreadId;

    public static HPublicExecutor instance() {
        if (sInstance == null) {
            sInstance = new HPublicExecutor();
        }
        return sInstance;
    }

    private HPublicExecutor() {
        this.mThreadId = 0;
        callerValidityCheck();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        TimeUnit timeUnit = TimeUnit.SECONDS;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        StringBuilder sbAppend = new StringBuilder().append("public-task#");
        int i = this.mThreadId;
        this.mThreadId = i + 1;
        this.mAsyncTaskExecutor = new ThreadPoolExecutor(0, 5, 30L, timeUnit, linkedBlockingQueue, new NamedThreadFactory(sbAppend.append(i).toString()) { // from class: com.hcn.common.lang.HPublicExecutor.1
            @Override // com.hcn.common.concurrent.NamedThreadFactory, java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread threadNewThread = super.newThread(runnable);
                if (threadNewThread.getPriority() != 5) {
                    threadNewThread.setPriority(5);
                }
                return threadNewThread;
            }
        });
    }

    private void callerValidityCheck() {
        if (!HThreadUtils.isMainThread()) {
            throw new RuntimeException("HPublicExecutor#submitTask/Prohibit calls from non main threads!");
        }
    }

    public Future<?> submitTask(long j, Callable<Object> callable, HTaskRunnable.OnCompletionListener onCompletionListener) {
        callerValidityCheck();
        return this.mAsyncTaskExecutor.submit(new HTaskRunnable(j, callable, this.mMainHandler, onCompletionListener));
    }

    public Future<?> submitAsyncResultTask(long j, Callable<Object> callable, HTaskRunnable.OnCompletionListener onCompletionListener) {
        callerValidityCheck();
        return this.mAsyncTaskExecutor.submit(new HTaskRunnable(j, callable, onCompletionListener));
    }

    public Future<?> submitTask(RunnableEx runnableEx) {
        callerValidityCheck();
        return this.mAsyncTaskExecutor.submit(runnableEx);
    }

    public Future<?> submitTask(final long j, final RunnableEx runnableEx, final HTaskRunnable.OnCompletionListener onCompletionListener) {
        callerValidityCheck();
        return this.mAsyncTaskExecutor.submit(new Runnable() { // from class: com.hcn.common.lang.HPublicExecutor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m83lambda$submitTask$1$comhcncommonlangHPublicExecutor(runnableEx, onCompletionListener, j);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$submitTask$1$com-hcn-common-lang-HPublicExecutor, reason: not valid java name */
    /* synthetic */ void m83lambda$submitTask$1$comhcncommonlangHPublicExecutor(RunnableEx runnableEx, final HTaskRunnable.OnCompletionListener onCompletionListener, final long j) {
        runnableEx.run();
        this.mMainHandler.post(new Runnable() { // from class: com.hcn.common.lang.HPublicExecutor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                HPublicExecutor.lambda$submitTask$0(onCompletionListener, j);
            }
        });
    }

    static /* synthetic */ void lambda$submitTask$0(HTaskRunnable.OnCompletionListener onCompletionListener, long j) {
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(j, "ok");
        }
    }
}
