package com.hcn.common.lang;

import android.os.Handler;
import android.text.TextUtils;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public class HTaskRunnable<T> implements Runnable {
    private final Reference<Handler> mHandlerRef;
    private final OnCompletionListener mListener;
    private final Callable<T> mTask;
    private final long mTaskUniqueId;

    public interface OnCompletionListener {
        default void onCompletion(long j, Object obj) {
        }
    }

    private HTaskRunnable() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public HTaskRunnable(Callable<T> callable) {
        this(-1L, callable, null);
    }

    public HTaskRunnable(long j, Callable<T> callable, OnCompletionListener onCompletionListener) {
        this.mTaskUniqueId = j;
        this.mTask = callable;
        this.mHandlerRef = null;
        this.mListener = onCompletionListener;
    }

    public HTaskRunnable(long j, Callable<T> callable, Handler handler, OnCompletionListener onCompletionListener) {
        this.mTaskUniqueId = j;
        this.mTask = callable;
        this.mHandlerRef = new WeakReference(handler);
        this.mListener = onCompletionListener;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0051  */
    /* JADX WARN: Code duplicated, block: B:35:0x0059  */
    /* JADX WARN: Code duplicated, block: B:36:0x0062  */
    @Override // java.lang.Runnable
    public void run() throws Throwable {
        Throwable th;
        boolean z;
        Exception e;
        Reference<Handler> reference;
        Handler handler;
        final String str = null;
        try {
            try {
                final T tCall = this.mTask.call();
                z = true;
                try {
                    OnCompletionListener onCompletionListener = this.mListener;
                    if (onCompletionListener != null) {
                        Reference<Handler> reference2 = this.mHandlerRef;
                        if (reference2 != null) {
                            Handler handler2 = reference2.get();
                            if (handler2 != null) {
                                handler2.post(new Runnable() { // from class: com.hcn.common.lang.HTaskRunnable$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.m87lambda$run$0$comhcncommonlangHTaskRunnable(tCall);
                                    }
                                });
                                return;
                            }
                            return;
                        }
                        onCompletionListener.onCompletion(this.mTaskUniqueId, tCall);
                    }
                } catch (Exception e2) {
                    e = e2;
                    if (!z) {
                        e.getMessage();
                    }
                    throw new RuntimeException(e);
                }
            } catch (Throwable th2) {
                th = th2;
                if (this.mListener != null && 0 == 0 && !TextUtils.isEmpty(null)) {
                    reference = this.mHandlerRef;
                    if (reference != null) {
                        handler = reference.get();
                        if (handler != null) {
                            handler.post(new Runnable() { // from class: com.hcn.common.lang.HTaskRunnable$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m88lambda$run$1$comhcncommonlangHTaskRunnable(str);
                                }
                            });
                        }
                    } else {
                        this.mListener.onCompletion(this.mTaskUniqueId, null);
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            z = false;
            e = e3;
        } catch (Throwable th3) {
            th = th3;
            if (this.mListener != null) {
                reference = this.mHandlerRef;
                if (reference != null) {
                    handler = reference.get();
                    if (handler != null) {
                        handler.post(new Runnable() { // from class: com.hcn.common.lang.HTaskRunnable$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m88lambda$run$1$comhcncommonlangHTaskRunnable(str);
                            }
                        });
                    }
                } else {
                    this.mListener.onCompletion(this.mTaskUniqueId, null);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: renamed from: lambda$run$0$com-hcn-common-lang-HTaskRunnable, reason: not valid java name */
    /* synthetic */ void m87lambda$run$0$comhcncommonlangHTaskRunnable(Object obj) {
        this.mListener.onCompletion(this.mTaskUniqueId, obj);
    }

    /* JADX INFO: renamed from: lambda$run$1$com-hcn-common-lang-HTaskRunnable, reason: not valid java name */
    /* synthetic */ void m88lambda$run$1$comhcncommonlangHTaskRunnable(String str) {
        this.mListener.onCompletion(this.mTaskUniqueId, str);
    }
}
