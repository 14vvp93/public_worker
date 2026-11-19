package com.android.systemui.shared.recents.model;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.Process;
import android.os.Trace;
import android.util.Log;
import android.util.LruCache;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RecentsTaskLoader {
    private final LruCache<ComponentName, ActivityInfo> mActivityInfoCache;
    private final TaskKeyLruCache<String> mActivityLabelCache;
    private final TaskKeyLruCache<String> mContentDescriptionCache;
    private int mDefaultTaskBarBackgroundColor;
    private int mDefaultTaskViewBackgroundColor;
    private final HighResThumbnailLoader mHighResThumbnailLoader;
    private final TaskKeyLruCache<Drawable> mIconCache;
    private final IconLoader mIconLoader;
    private final TaskResourceLoadQueue mLoadQueue;
    private final BackgroundTaskLoader mLoader;
    private final int mMaxIconCacheSize;
    private final int mMaxThumbnailCacheSize;
    private int mNumVisibleTasksLoaded;
    private int mSvelteLevel;

    @GuardedBy("this")
    private final TaskKeyStrongCache<ThumbnailData> mThumbnailCache = new TaskKeyStrongCache<>();

    @GuardedBy("this")
    private final TaskKeyStrongCache<ThumbnailData> mTempCache = new TaskKeyStrongCache<>();
    private TaskKeyLruCache.EvictionCallback mClearActivityInfoOnEviction = new TaskKeyLruCache.EvictionCallback() { // from class: com.android.systemui.shared.recents.model.RecentsTaskLoader.1
        @Override // com.android.systemui.shared.recents.model.TaskKeyLruCache.EvictionCallback
        public void onEntryEvicted(Task.TaskKey key) {
            if (key != null) {
                RecentsTaskLoader.this.mActivityInfoCache.remove(key.getComponent());
            }
        }
    };

    public RecentsTaskLoader(Context context, int maxThumbnailCacheSize, int maxIconCacheSize, int svelteLevel) {
        this.mMaxThumbnailCacheSize = maxThumbnailCacheSize;
        this.mMaxIconCacheSize = maxIconCacheSize;
        this.mSvelteLevel = svelteLevel;
        int numRecentTasks = ActivityManager.getMaxRecentTasksStatic();
        boolean isLowRamDevice = ActivityManager.isLowRamDeviceStatic();
        long totalMemory = Process.getTotalMemory() / 1048576;
        if (totalMemory <= 1024) {
            isLowRamDevice = true;
            Log.d("RecentsTaskLoader", "Device Total Memory: " + totalMemory);
        }
        this.mHighResThumbnailLoader = new HighResThumbnailLoader(ActivityManagerWrapper.getInstance(), Looper.getMainLooper(), isLowRamDevice);
        this.mLoadQueue = new TaskResourceLoadQueue();
        this.mIconCache = new TaskKeyLruCache<>(this.mMaxIconCacheSize, this.mClearActivityInfoOnEviction);
        this.mActivityLabelCache = new TaskKeyLruCache<>(numRecentTasks, this.mClearActivityInfoOnEviction);
        this.mContentDescriptionCache = new TaskKeyLruCache<>(numRecentTasks, this.mClearActivityInfoOnEviction);
        this.mActivityInfoCache = new LruCache<>(numRecentTasks);
        this.mIconLoader = createNewIconLoader(context, this.mIconCache, this.mActivityInfoCache);
        TaskResourceLoadQueue taskResourceLoadQueue = this.mLoadQueue;
        IconLoader iconLoader = this.mIconLoader;
        final HighResThumbnailLoader highResThumbnailLoader = this.mHighResThumbnailLoader;
        Objects.requireNonNull(highResThumbnailLoader);
        this.mLoader = new BackgroundTaskLoader(taskResourceLoadQueue, iconLoader, new BackgroundTaskLoader.OnIdleChangedListener() { // from class: com.android.systemui.shared.recents.model.-$$Lambda$vKccogRiJjM5FBa4zs196J3w_Fs
            @Override // com.android.systemui.shared.recents.model.BackgroundTaskLoader.OnIdleChangedListener
            public final void onIdleChanged(boolean z) {
                highResThumbnailLoader.setTaskLoadQueueIdle(z);
            }
        });
    }

    protected IconLoader createNewIconLoader(Context context, TaskKeyLruCache<Drawable> iconCache, LruCache<ComponentName, ActivityInfo> activityInfoCache) {
        return new IconLoader.DefaultIconLoader(context, iconCache, activityInfoCache);
    }

    public void setDefaultColors(int defaultTaskBarBackgroundColor, int defaultTaskViewBackgroundColor) {
        this.mDefaultTaskBarBackgroundColor = defaultTaskBarBackgroundColor;
        this.mDefaultTaskViewBackgroundColor = defaultTaskViewBackgroundColor;
    }

    public HighResThumbnailLoader getHighResThumbnailLoader() {
        return this.mHighResThumbnailLoader;
    }

    public synchronized void preloadTasks(RecentsTaskLoadPlan plan, int runningTaskId) {
        preloadTasks(plan, runningTaskId, ActivityManagerWrapper.getInstance().getCurrentUserId());
    }

    public synchronized void preloadTasks(RecentsTaskLoadPlan plan, int runningTaskId, int currentUserId) {
        try {
            Trace.beginSection("preloadPlan");
            plan.preloadPlan(new RecentsTaskLoadPlan.PreloadOptions(), this, runningTaskId, currentUserId);
            Trace.endSection();
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    public synchronized void loadTasks(RecentsTaskLoadPlan plan, RecentsTaskLoadPlan.Options opts) {
        try {
            if (opts == null) {
                throw new RuntimeException("Requires load options");
            }
            if (opts.onlyLoadForCache && opts.loadThumbnails) {
                this.mTempCache.copyEntries(this.mThumbnailCache);
                this.mThumbnailCache.evictAll();
            }
            plan.executePlan(opts, this);
            this.mTempCache.evictAll();
            if (!opts.onlyLoadForCache) {
                this.mNumVisibleTasksLoaded = opts.numVisibleTasks;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void loadTaskData(Task t) {
        Drawable icon = this.mIconCache.getAndInvalidateIfModified(t.key);
        Drawable icon2 = icon != null ? icon : this.mIconLoader.getDefaultIcon(t.key.userId);
        this.mLoadQueue.addTask(t);
        t.notifyTaskDataLoaded(t.thumbnail, icon2);
    }

    public void unloadTaskData(Task t) {
        this.mLoadQueue.removeTask(t);
        t.notifyTaskDataUnloaded(this.mIconLoader.getDefaultIcon(t.key.userId));
    }

    public void deleteTaskData(Task t, boolean notifyTaskDataUnloaded) {
        this.mLoadQueue.removeTask(t);
        this.mIconCache.remove(t.key);
        this.mActivityLabelCache.remove(t.key);
        this.mContentDescriptionCache.remove(t.key);
        if (notifyTaskDataUnloaded) {
            t.notifyTaskDataUnloaded(this.mIconLoader.getDefaultIcon(t.key.userId));
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004b A[Catch: all -> 0x0087, TryCatch #0 {all -> 0x0087, blocks: (B:18:0x001e, B:19:0x0031, B:20:0x004b, B:21:0x0068), top: B:28:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0068 A[Catch: all -> 0x0087, TRY_LEAVE, TryCatch #0 {all -> 0x0087, blocks: (B:18:0x001e, B:19:0x0031, B:20:0x004b, B:21:0x0068), top: B:28:0x0003 }] */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x001b, code lost:
    
        if (r4 != 80) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void onTrimMemory(int level) {
        try {
            if (level == 5) {
                this.mIconCache.trimToSize(Math.max(1, this.mMaxIconCacheSize / 2));
                this.mActivityInfoCache.trimToSize(Math.max(1, ActivityManager.getMaxRecentTasksStatic() / 2));
            } else if (level == 10) {
                this.mIconCache.trimToSize(Math.max(1, this.mMaxIconCacheSize / 4));
                this.mActivityInfoCache.trimToSize(Math.max(1, ActivityManager.getMaxRecentTasksStatic() / 4));
            } else if (level == 15) {
                this.mIconCache.evictAll();
                this.mActivityInfoCache.evictAll();
                this.mActivityLabelCache.evictAll();
                this.mContentDescriptionCache.evictAll();
                this.mThumbnailCache.evictAll();
            } else if (level == 20) {
                stopLoader();
                this.mIconCache.trimToSize(Math.max(this.mNumVisibleTasksLoaded, this.mMaxIconCacheSize / 2));
            } else if (level == 40) {
                this.mIconCache.trimToSize(Math.max(1, this.mMaxIconCacheSize / 2));
                this.mActivityInfoCache.trimToSize(Math.max(1, ActivityManager.getMaxRecentTasksStatic() / 2));
            } else if (level == 60) {
                this.mIconCache.trimToSize(Math.max(1, this.mMaxIconCacheSize / 4));
                this.mActivityInfoCache.trimToSize(Math.max(1, ActivityManager.getMaxRecentTasksStatic() / 4));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void onPackageChanged(String packageName) {
        Map<ComponentName, ActivityInfo> activityInfoCache = this.mActivityInfoCache.snapshot();
        for (ComponentName cn : activityInfoCache.keySet()) {
            if (cn.getPackageName().equals(packageName)) {
                this.mActivityInfoCache.remove(cn);
            }
        }
    }

    String getAndUpdateActivityTitle(Task.TaskKey taskKey, ActivityManager.TaskDescription td) {
        if (td != null && td.getLabel() != null) {
            return td.getLabel();
        }
        String label = this.mActivityLabelCache.getAndInvalidateIfModified(taskKey);
        if (label != null) {
            return label;
        }
        ActivityInfo activityInfo = getAndUpdateActivityInfo(taskKey);
        if (activityInfo != null) {
            String label2 = ActivityManagerWrapper.getInstance().getBadgedActivityLabel(activityInfo, taskKey.userId);
            this.mActivityLabelCache.put(taskKey, label2);
            return label2;
        }
        return "";
    }

    String getAndUpdateContentDescription(Task.TaskKey taskKey, ActivityManager.TaskDescription td) {
        String label = this.mContentDescriptionCache.getAndInvalidateIfModified(taskKey);
        if (label != null) {
            return label;
        }
        ActivityInfo activityInfo = getAndUpdateActivityInfo(taskKey);
        if (activityInfo != null) {
            String label2 = ActivityManagerWrapper.getInstance().getBadgedContentDescription(activityInfo, taskKey.userId, td);
            if (td == null) {
                this.mContentDescriptionCache.put(taskKey, label2);
            }
            return label2;
        }
        return "";
    }

    Drawable getAndUpdateActivityIcon(Task.TaskKey taskKey, ActivityManager.TaskDescription td, boolean loadIfNotCached) {
        return this.mIconLoader.getAndInvalidateIfModified(taskKey, td, loadIfNotCached);
    }

    synchronized ThumbnailData getAndUpdateThumbnail(Task.TaskKey taskKey, boolean loadIfNotCached, boolean storeInCache) {
        ThumbnailData cached = this.mThumbnailCache.getAndInvalidateIfModified(taskKey);
        if (cached != null) {
            return cached;
        }
        ThumbnailData cached2 = this.mTempCache.getAndInvalidateIfModified(taskKey);
        if (cached2 != null) {
            this.mThumbnailCache.put(taskKey, cached2);
            return cached2;
        }
        if (loadIfNotCached && this.mSvelteLevel < 3) {
            ThumbnailData thumbnailData = ActivityManagerWrapper.getInstance().getTaskThumbnail(taskKey.id, true);
            if (thumbnailData.thumbnail != null) {
                if (storeInCache) {
                    this.mThumbnailCache.put(taskKey, thumbnailData);
                }
                return thumbnailData;
            }
        }
        return null;
    }

    int getActivityPrimaryColor(ActivityManager.TaskDescription td) {
        if (td != null && td.getPrimaryColor() != 0) {
            return td.getPrimaryColor();
        }
        return this.mDefaultTaskBarBackgroundColor;
    }

    int getActivityBackgroundColor(ActivityManager.TaskDescription td) {
        if (td != null && td.getBackgroundColor() != 0) {
            return td.getBackgroundColor();
        }
        return this.mDefaultTaskViewBackgroundColor;
    }

    ActivityInfo getAndUpdateActivityInfo(Task.TaskKey taskKey) {
        return this.mIconLoader.getAndUpdateActivityInfo(taskKey);
    }

    public void startLoader(Context ctx) {
        this.mLoader.start(ctx);
    }

    private void stopLoader() {
        this.mLoader.stop();
        this.mLoadQueue.clearTasks();
    }

    public synchronized void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        writer.print(prefix);
        writer.println("RecentsTaskLoader");
        writer.print(prefix);
        writer.println("Icon Cache");
        this.mIconCache.dump(innerPrefix, writer);
        writer.print(prefix);
        writer.println("Thumbnail Cache");
        this.mThumbnailCache.dump(innerPrefix, writer);
        writer.print(prefix);
        writer.println("Temp Thumbnail Cache");
        this.mTempCache.dump(innerPrefix, writer);
    }
}
