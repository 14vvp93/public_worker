package com.android.systemui.statusbar.policy;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Handler;
import android.view.accessibility.AccessibilityManager;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AccessibilityManagerWrapper implements CallbackController<AccessibilityManager.AccessibilityServicesStateChangeListener> {
    private final AccessibilityManager mAccessibilityManager;

    public AccessibilityManagerWrapper(Context context) {
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(AccessibilityManager.AccessibilityServicesStateChangeListener listener) {
        this.mAccessibilityManager.addAccessibilityServicesStateChangeListener(listener, (Handler) null);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(AccessibilityManager.AccessibilityServicesStateChangeListener listener) {
        this.mAccessibilityManager.removeAccessibilityServicesStateChangeListener(listener);
    }

    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackTypeFlags) {
        return this.mAccessibilityManager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }
}
