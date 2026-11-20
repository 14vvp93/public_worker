package com.hcn.skin.support.resources;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.util.Pools;
import com.hcn.common.misc.LogUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class AsyncLayoutInflaterEx {
    private static final String TAG = "AsyncLayoutInflaterEx";
    private final LayoutInflater mInflater;
    private final Pools.SynchronizedPool<InflateRequest> mRequestPool = new Pools.SynchronizedPool<>(10);
    private Handler.Callback mHandlerCallback = new Handler.Callback() { // from class: com.hcn.skin.support.resources.AsyncLayoutInflaterEx.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            InflateRequest inflateRequest = (InflateRequest) message.obj;
            if (inflateRequest.view == null) {
                inflateRequest.view = AsyncLayoutInflaterEx.this.mInflater.inflate(inflateRequest.resId, inflateRequest.parent, false);
            }
            inflateRequest.callback.onInflateFinished(inflateRequest.view, inflateRequest.resId, inflateRequest.parent);
            return true;
        }
    };
    private final Handler mHandler = new Handler(this.mHandlerCallback);
    private final Dispatcher mDispatcher = new Dispatcher();

    public interface OnInflateFinishedListener {
        void onInflateFinished(View view, int i, ViewGroup viewGroup);
    }

    public AsyncLayoutInflaterEx(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void inflate(int i, ViewGroup viewGroup, OnInflateFinishedListener onInflateFinishedListener) {
        if (onInflateFinishedListener == null) {
            throw new NullPointerException("callback may not be null!");
        }
        InflateRequest inflateRequestObtainRequest = obtainRequest();
        inflateRequestObtainRequest.inflater = this;
        inflateRequestObtainRequest.resId = i;
        inflateRequestObtainRequest.parent = viewGroup;
        inflateRequestObtainRequest.callback = onInflateFinishedListener;
        this.mDispatcher.enqueue(inflateRequestObtainRequest);
    }

    private static class Dispatcher {
        private static final int CORE_POOL_SIZE;
        private static final int CPU_COUNT;
        private static final int KEEP_ALIVE_SECONDS = 30;
        private static final int MAXIMUM_POOL_SIZE;
        public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
        private static final BlockingQueue<Runnable> sPoolWorkQueue;
        private static final ThreadFactory sThreadFactory;

        private Dispatcher() {
        }

        static {
            int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
            CPU_COUNT = iAvailableProcessors;
            int iMax = Math.max(2, Math.min(iAvailableProcessors - 1, 4));
            CORE_POOL_SIZE = iMax;
            int i = (iAvailableProcessors * 2) + 1;
            MAXIMUM_POOL_SIZE = i;
            ThreadFactory threadFactory = new ThreadFactory() { // from class: com.hcn.skin.support.resources.AsyncLayoutInflaterEx.Dispatcher.1
                private final AtomicInteger mCount = new AtomicInteger(1);

                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "AsyncLayoutInflateEx #" + this.mCount.getAndIncrement());
                }
            };
            sThreadFactory = threadFactory;
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            sPoolWorkQueue = linkedBlockingQueue;
            LogUtils.iTag(AsyncLayoutInflaterEx.TAG, "ThreadPool init: CPU=" + iAvailableProcessors + " CORE=" + iMax + " MAX=" + i);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(iMax, i, 30L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
            THREAD_POOL_EXECUTOR = threadPoolExecutor;
            threadPoolExecutor.allowCoreThreadTimeOut(true);
        }

        public void enqueue(InflateRequest inflateRequest) {
            THREAD_POOL_EXECUTOR.execute(new InflateRunnable(inflateRequest));
        }
    }

    private static class InflateRunnable implements Runnable {
        private boolean isRunning;
        private final InflateRequest request;

        InflateRunnable(InflateRequest inflateRequest) {
            this.request = inflateRequest;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.isRunning = true;
            try {
                InflateRequest inflateRequest = this.request;
                inflateRequest.view = inflateRequest.inflater.mInflater.inflate(this.request.resId, this.request.parent, false);
            } catch (RuntimeException e) {
                LogUtils.wTag(AsyncLayoutInflaterEx.TAG, "Failed in background! Retrying on UI thread", e);
            }
            Message.obtain(this.request.inflater.mHandler, 0, this.request).sendToTarget();
        }

        public boolean isRunning() {
            return this.isRunning;
        }
    }

    private InflateRequest obtainRequest() {
        InflateRequest inflateRequestAcquire = this.mRequestPool.acquire();
        return inflateRequestAcquire != null ? inflateRequestAcquire : new InflateRequest();
    }

    private void releaseRequest(InflateRequest inflateRequest) {
        inflateRequest.callback = null;
        inflateRequest.inflater = null;
        inflateRequest.parent = null;
        inflateRequest.resId = 0;
        inflateRequest.view = null;
        this.mRequestPool.release(inflateRequest);
    }

    public void cancel() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandlerCallback = null;
    }

    private static class InflateRequest {
        OnInflateFinishedListener callback;
        AsyncLayoutInflaterEx inflater;
        ViewGroup parent;
        int resId;
        View view;

        private InflateRequest() {
        }
    }
}
