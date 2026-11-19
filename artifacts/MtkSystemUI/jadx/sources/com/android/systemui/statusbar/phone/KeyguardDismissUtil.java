package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.keyguard.KeyguardHostView;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardDismissUtil implements KeyguardDismissHandler {
    private volatile KeyguardDismissHandler mDismissHandler;

    public void setDismissHandler(KeyguardDismissHandler dismissHandler) {
        this.mDismissHandler = dismissHandler;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardDismissHandler
    public void executeWhenUnlocked(KeyguardHostView.OnDismissAction action) {
        KeyguardDismissHandler dismissHandler = this.mDismissHandler;
        if (dismissHandler == null) {
            Log.wtf("KeyguardDismissUtil", "KeyguardDismissHandler not set.");
            action.onDismiss();
        } else {
            dismissHandler.executeWhenUnlocked(action);
        }
    }
}
