package com.android.systemui;

import android.app.PendingIntent;
import android.content.Intent;
import com.android.systemui.plugins.ActivityStarter;

/* JADX INFO: loaded from: classes.dex */
public class ActivityStarterDelegate implements ActivityStarter {
    private ActivityStarter mActualStarter;

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent intent) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.startPendingIntentDismissingKeyguard(intent);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean dismissShade) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.startActivity(intent, dismissShade);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean onlyProvisioned, boolean dismissShade) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.startActivity(intent, onlyProvisioned, dismissShade);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean dismissShade, ActivityStarter.Callback callback) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.startActivity(intent, dismissShade, callback);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(Intent intent, int delay) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.postStartActivityDismissingKeyguard(intent, delay);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(PendingIntent intent) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.postStartActivityDismissingKeyguard(intent);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postQSRunnableDismissingKeyguard(Runnable runnable) {
        if (this.mActualStarter == null) {
            return;
        }
        this.mActualStarter.postQSRunnableDismissingKeyguard(runnable);
    }

    public void setActivityStarterImpl(ActivityStarter starter) {
        this.mActualStarter = starter;
    }
}
