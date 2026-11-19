package com.android.systemui.statusbar;

import android.app.AppOpsManager;
import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.ForegroundServiceController;

/* JADX INFO: loaded from: classes.dex */
public class AppOpsListener implements AppOpsManager.OnOpActiveChangedListener {
    protected static final int[] OPS = {26, 24, 27};
    protected final AppOpsManager mAppOps;
    private final Context mContext;
    protected NotificationEntryManager mEntryManager;
    private final ForegroundServiceController mFsc = (ForegroundServiceController) Dependency.get(ForegroundServiceController.class);
    protected NotificationPresenter mPresenter;

    public AppOpsListener(Context context) {
        this.mContext = context;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
        this.mAppOps.startWatchingActive(OPS, this);
    }

    public void onOpActiveChanged(final int code, final int uid, final String packageName, final boolean active) {
        this.mFsc.onAppOpChanged(code, uid, packageName, active);
        this.mPresenter.getHandler().post(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$AppOpsListener$q1rvytNuPQxuSlMkTPgexkQ-Hb4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mEntryManager.updateNotificationsForAppOp(code, uid, packageName, active);
            }
        });
    }
}
