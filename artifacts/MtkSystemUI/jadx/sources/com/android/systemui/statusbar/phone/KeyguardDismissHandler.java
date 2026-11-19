package com.android.systemui.statusbar.phone;

import com.android.keyguard.KeyguardHostView;

/* JADX INFO: loaded from: classes.dex */
public interface KeyguardDismissHandler {
    void executeWhenUnlocked(KeyguardHostView.OnDismissAction onDismissAction);
}
