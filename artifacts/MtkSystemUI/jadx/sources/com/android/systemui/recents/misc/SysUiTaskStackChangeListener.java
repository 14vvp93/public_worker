package com.android.systemui.recents.misc;

import android.content.Context;
import com.android.systemui.shared.system.TaskStackChangeListener;

/* JADX INFO: loaded from: classes.dex */
public abstract class SysUiTaskStackChangeListener extends TaskStackChangeListener {
    protected final boolean checkCurrentUserId(Context context, boolean debug) {
        int currentUserId = SystemServicesProxy.getInstance(context).getCurrentUser();
        return checkCurrentUserId(currentUserId, debug);
    }
}
