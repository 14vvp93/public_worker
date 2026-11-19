package com.android.systemui.statusbar.policy;

import android.R;
import android.content.Context;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.util.ArraySet;
import com.android.internal.logging.MetricsLogger;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class IconLoggerImpl implements IconLogger {
    protected static long MIN_LOG_INTERVAL = 1000;
    private final Context mContext;
    private final Handler mHandler;
    private final List<String> mIconIndex;
    private final ArraySet<String> mIcons = new ArraySet<>();
    private long mLastLog = System.currentTimeMillis();
    private final Runnable mLog = new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$IconLoggerImpl$R-UnLK2IlCvdUfqG-_mI_-TEpe4
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.doLog();
        }
    };
    private final MetricsLogger mLogger;

    public IconLoggerImpl(Context context, Looper bgLooper, MetricsLogger logger) {
        this.mContext = context;
        this.mHandler = new Handler(bgLooper);
        this.mLogger = logger;
        String[] icons = this.mContext.getResources().getStringArray(R.array.config_deviceStatesAvailableForAppRequests);
        this.mIconIndex = Arrays.asList(icons);
        doLog();
    }

    @Override // com.android.systemui.statusbar.policy.IconLogger
    public void onIconShown(String tag) {
        synchronized (this.mIcons) {
            if (this.mIcons.contains(tag)) {
                return;
            }
            this.mIcons.add(tag);
            if (!this.mHandler.hasCallbacks(this.mLog)) {
                this.mHandler.postDelayed(this.mLog, MIN_LOG_INTERVAL);
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.IconLogger
    public void onIconHidden(String tag) {
        synchronized (this.mIcons) {
            if (this.mIcons.contains(tag)) {
                this.mIcons.remove(tag);
                if (!this.mHandler.hasCallbacks(this.mLog)) {
                    this.mHandler.postDelayed(this.mLog, MIN_LOG_INTERVAL);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLog() {
        ArraySet<String> icons;
        long time = System.currentTimeMillis();
        long timeSinceLastLog = time - this.mLastLog;
        this.mLastLog = time;
        synchronized (this.mIcons) {
            icons = new ArraySet<>(this.mIcons);
        }
        this.mLogger.write(new LogMaker(1093).setType(4).setLatency(timeSinceLastLog).addTaggedData(1095, Integer.valueOf(icons.size())).addTaggedData(1094, Integer.valueOf(getBitField(icons))));
    }

    private int getBitField(ArraySet<String> icons) {
        int iconsVisible = 0;
        for (String icon : icons) {
            int index = this.mIconIndex.indexOf(icon);
            if (index >= 0) {
                iconsVisible |= 1 << index;
            }
        }
        return iconsVisible;
    }
}
