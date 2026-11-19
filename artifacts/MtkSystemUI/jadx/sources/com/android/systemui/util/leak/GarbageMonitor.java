package com.android.systemui.util.leak;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.LongSparseArray;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.SystemUI;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class GarbageMonitor {
    private static final boolean LEAK_REPORTING_ENABLED;
    private final ActivityManager mAm;
    private final Context mContext;
    private DumpTruck mDumpTruck;
    private final Handler mHandler;
    private long mHeapLimit;
    private final LeakReporter mLeakReporter;
    private MemoryTile mQSTile;
    private final TrackedGarbage mTrackedGarbage;
    private final LongSparseArray<ProcessMemInfo> mData = new LongSparseArray<>();
    private final ArrayList<Long> mPids = new ArrayList<>();
    private int[] mPidsArray = new int[1];

    static {
        boolean z = false;
        if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.enable_leak_reporting", false)) {
            z = true;
        }
        LEAK_REPORTING_ENABLED = z;
    }

    public GarbageMonitor(Context context, Looper bgLooper, LeakDetector leakDetector, LeakReporter leakReporter) {
        this.mContext = context.getApplicationContext();
        this.mAm = (ActivityManager) context.getSystemService("activity");
        this.mHandler = new BackgroundHeapCheckHandler(bgLooper);
        this.mTrackedGarbage = leakDetector.getTrackedGarbage();
        this.mLeakReporter = leakReporter;
        this.mDumpTruck = new DumpTruck(this.mContext);
        this.mHeapLimit = this.mContext.getResources().getInteger(R.integer.watch_heap_limit);
    }

    public void startLeakMonitor() {
        if (this.mTrackedGarbage == null) {
            return;
        }
        this.mHandler.sendEmptyMessage(1000);
    }

    public void startHeapTracking() {
        startTrackingProcess(Process.myPid(), this.mContext.getPackageName(), System.currentTimeMillis());
        this.mHandler.sendEmptyMessage(3000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean gcAndCheckGarbage() {
        if (this.mTrackedGarbage.countOldGarbage() > 5) {
            Runtime.getRuntime().gc();
            return true;
        }
        return false;
    }

    void reinspectGarbageAfterGc() {
        int count = this.mTrackedGarbage.countOldGarbage();
        if (count > 5) {
            this.mLeakReporter.dumpLeak(count);
        }
    }

    public ProcessMemInfo getMemInfo(int pid) {
        return this.mData.get(pid);
    }

    public int[] getTrackedProcesses() {
        return this.mPidsArray;
    }

    public void startTrackingProcess(long pid, String name, long start) {
        synchronized (this.mPids) {
            if (this.mPids.contains(Long.valueOf(pid))) {
                return;
            }
            this.mPids.add(Long.valueOf(pid));
            updatePidsArrayL();
            this.mData.put(pid, new ProcessMemInfo(pid, name, start));
        }
    }

    private void updatePidsArrayL() {
        int N = this.mPids.size();
        this.mPidsArray = new int[N];
        StringBuffer sb = new StringBuffer("Now tracking processes: ");
        for (int i = 0; i < N; i++) {
            int p = this.mPids.get(i).intValue();
            this.mPidsArray[i] = p;
            sb.append(p);
            sb.append(" ");
        }
        Log.v("GarbageMonitor", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update() {
        synchronized (this.mPids) {
            Debug.MemoryInfo[] dinfos = this.mAm.getProcessMemoryInfo(this.mPidsArray);
            for (int i = 0; i < dinfos.length; i++) {
                Debug.MemoryInfo dinfo = dinfos[i];
                if (i > this.mPids.size()) {
                    Log.e("GarbageMonitor", "update: unknown process info received: " + dinfo);
                    break;
                }
                long pid = this.mPids.get(i).intValue();
                ProcessMemInfo info = this.mData.get(pid);
                info.head = (info.head + 1) % info.pss.length;
                long[] jArr = info.pss;
                int i2 = info.head;
                long totalPss = dinfo.getTotalPss();
                info.currentPss = totalPss;
                jArr[i2] = totalPss;
                long[] jArr2 = info.uss;
                int i3 = info.head;
                long totalPrivateDirty = dinfo.getTotalPrivateDirty();
                info.currentUss = totalPrivateDirty;
                jArr2[i3] = totalPrivateDirty;
                if (info.currentPss > info.max) {
                    info.max = info.currentPss;
                }
                if (info.currentUss > info.max) {
                    info.max = info.currentUss;
                }
                if (info.currentPss == 0) {
                    Log.v("GarbageMonitor", "update: pid " + pid + " has pss=0, it probably died");
                    this.mData.remove(pid);
                }
            }
            for (int i4 = this.mPids.size() - 1; i4 >= 0; i4--) {
                if (this.mData.get(this.mPids.get(i4).intValue()) == null) {
                    this.mPids.remove(i4);
                    updatePidsArrayL();
                }
            }
        }
        if (this.mQSTile != null) {
            this.mQSTile.update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTile(MemoryTile tile) {
        this.mQSTile = tile;
        if (tile != null) {
            tile.update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatBytes(long b) {
        String[] SUFFIXES = {"B", "K", "M", "G", "T"};
        int i = 0;
        while (i < SUFFIXES.length && b >= 1024) {
            b /= 1024;
            i++;
        }
        return b + SUFFIXES[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpHprofAndShare() {
        Intent share = this.mDumpTruck.captureHeaps(getTrackedProcesses()).createShareIntent();
        this.mContext.startActivity(share);
    }

    private static class MemoryIconDrawable extends Drawable {
        final Drawable baseIcon;
        final float dp;
        long limit;
        final Paint paint = new Paint();
        long pss;

        MemoryIconDrawable(Context context) {
            this.baseIcon = context.getDrawable(R.drawable.ic_memory).mutate();
            this.dp = context.getResources().getDisplayMetrics().density;
            this.paint.setColor(QSTileImpl.getColorForState(context, 2));
        }

        public void setPss(long pss) {
            if (pss != this.pss) {
                this.pss = pss;
                invalidateSelf();
            }
        }

        public void setLimit(long limit) {
            if (limit != this.limit) {
                this.limit = limit;
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.baseIcon.draw(canvas);
            if (this.limit > 0 && this.pss > 0) {
                float frac = Math.min(1.0f, this.pss / this.limit);
                Rect bounds = getBounds();
                canvas.translate(bounds.left + (this.dp * 8.0f), bounds.top + (5.0f * this.dp));
                canvas.drawRect(0.0f, this.dp * 14.0f * (1.0f - frac), (8.0f * this.dp) + 1.0f, (14.0f * this.dp) + 1.0f, this.paint);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setBounds(int left, int top, int right, int bottom) {
            super.setBounds(left, top, right, bottom);
            this.baseIcon.setBounds(left, top, right, bottom);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.baseIcon.getIntrinsicHeight();
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.baseIcon.getIntrinsicWidth();
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.baseIcon.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.baseIcon.setColorFilter(colorFilter);
            this.paint.setColorFilter(colorFilter);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTint(int tint) {
            super.setTint(tint);
            this.baseIcon.setTint(tint);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList tint) {
            super.setTintList(tint);
            this.baseIcon.setTintList(tint);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintMode(PorterDuff.Mode tintMode) {
            super.setTintMode(tintMode);
            this.baseIcon.setTintMode(tintMode);
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }
    }

    private static class MemoryGraphIcon extends QSTile.Icon {
        long limit;
        long pss;

        private MemoryGraphIcon() {
        }

        public void setPss(long pss) {
            this.pss = pss;
        }

        public void setHeapLimit(long limit) {
            this.limit = limit;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            MemoryIconDrawable drawable = new MemoryIconDrawable(context);
            drawable.setPss(this.pss);
            drawable.setLimit(this.limit);
            return drawable;
        }
    }

    public static class MemoryTile extends QSTileImpl<QSTile.State> {
        private final GarbageMonitor gm;
        private ProcessMemInfo pmi;

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl
        public QSTile.State newTileState() {
            return new QSTile.State();
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl
        public Intent getLongClickIntent() {
            return new Intent();
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl
        protected void handleClick() {
            getHost().collapsePanels();
            QSTileImpl<TState>.H h = this.mHandler;
            final GarbageMonitor garbageMonitor = this.gm;
            Objects.requireNonNull(garbageMonitor);
            h.post(new Runnable() { // from class: com.android.systemui.util.leak.-$$Lambda$GarbageMonitor$MemoryTile$RY8VPTPHkg6QWMQHYqV3eM4EOMY
                @Override // java.lang.Runnable
                public final void run() {
                    garbageMonitor.dumpHprofAndShare();
                }
            });
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
        public int getMetricsCategory() {
            return 0;
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl
        public void handleSetListening(boolean listening) {
            if (this.gm != null) {
                this.gm.setTile(listening ? this : null);
            }
            ActivityManager am = (ActivityManager) this.mContext.getSystemService(ActivityManager.class);
            if (listening && this.gm.mHeapLimit > 0) {
                am.setWatchHeapLimit(1024 * this.gm.mHeapLimit);
            } else {
                am.clearWatchHeapLimit();
            }
        }

        @Override // com.android.systemui.plugins.qs.QSTile
        public CharSequence getTileLabel() {
            return getState().label;
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl
        protected void handleUpdateState(QSTile.State state, Object arg) {
            this.pmi = this.gm.getMemInfo(Process.myPid());
            MemoryGraphIcon icon = new MemoryGraphIcon();
            icon.setHeapLimit(this.gm.mHeapLimit);
            if (this.pmi != null) {
                icon.setPss(this.pmi.currentPss);
                state.label = this.mContext.getString(R.string.heap_dump_tile_name);
                state.secondaryLabel = String.format("pss: %s / %s", GarbageMonitor.formatBytes(this.pmi.currentPss * 1024), GarbageMonitor.formatBytes(this.gm.mHeapLimit * 1024));
            } else {
                icon.setPss(0L);
                state.label = "Dump SysUI";
                state.secondaryLabel = null;
            }
            state.icon = icon;
        }

        public void update() {
            refreshState();
        }
    }

    public static class ProcessMemInfo {
        public long currentPss;
        public long currentUss;
        public String name;
        public long pid;
        public long startTime;
        public long[] pss = new long[256];
        public long[] uss = new long[256];
        public long max = 1;
        public int head = 0;

        public ProcessMemInfo(long pid, String name, long start) {
            this.pid = pid;
            this.name = name;
            this.startTime = start;
        }

        public long getUptime() {
            return System.currentTimeMillis() - this.startTime;
        }
    }

    public static class Service extends SystemUI {
        private GarbageMonitor mGarbageMonitor;

        @Override // com.android.systemui.SystemUI
        public void start() {
            boolean forceEnable = Settings.Secure.getInt(this.mContext.getContentResolver(), "sysui_force_enable_leak_reporting", 0) != 0;
            this.mGarbageMonitor = (GarbageMonitor) Dependency.get(GarbageMonitor.class);
            if ((GarbageMonitor.LEAK_REPORTING_ENABLED || forceEnable) && !GarbageMonitor.doesFileExist("disableleakmonitor")) {
                this.mGarbageMonitor.startLeakMonitor();
            }
            if (forceEnable && !GarbageMonitor.doesFileExist("disableheaptracking")) {
                this.mGarbageMonitor.startHeapTracking();
            }
        }
    }

    private class BackgroundHeapCheckHandler extends Handler {
        BackgroundHeapCheckHandler(Looper onLooper) {
            super(onLooper);
            if (Looper.getMainLooper().equals(onLooper)) {
                throw new RuntimeException("BackgroundHeapCheckHandler may not run on the ui thread");
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message m) {
            int i = m.what;
            if (i != 1000) {
                if (i == 3000) {
                    GarbageMonitor.this.update();
                    removeMessages(3000);
                    sendEmptyMessageDelayed(3000, 60000L);
                    return;
                }
                return;
            }
            if (GarbageMonitor.this.gcAndCheckGarbage()) {
                final GarbageMonitor garbageMonitor = GarbageMonitor.this;
                postDelayed(new Runnable() { // from class: com.android.systemui.util.leak.-$$Lambda$XMHjUeThvUDRPlJmBo9djG71pM8
                    @Override // java.lang.Runnable
                    public final void run() {
                        garbageMonitor.reinspectGarbageAfterGc();
                    }
                }, 100L);
            }
            removeMessages(1000);
            sendEmptyMessageDelayed(1000, 900000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean doesFileExist(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }
        return new File(Environment.getExternalStorageDirectory(), name).exists();
    }
}
