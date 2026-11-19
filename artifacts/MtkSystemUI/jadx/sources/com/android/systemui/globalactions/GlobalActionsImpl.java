package com.android.systemui.globalactions;

import android.R;
import android.app.ProgressDialog;
import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardMonitor;

/* JADX INFO: loaded from: classes.dex */
public class GlobalActionsImpl implements GlobalActions, CommandQueue.Callbacks {
    private final Context mContext;
    private boolean mDisabled;
    private GlobalActionsDialog mGlobalActions;
    private final KeyguardMonitor mKeyguardMonitor = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);
    private final DeviceProvisionedController mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);

    public GlobalActionsImpl(Context context) {
        this.mContext = context;
        ((CommandQueue) SysUiServiceProvider.getComponent(context, CommandQueue.class)).addCallbacks(this);
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public void destroy() {
        ((CommandQueue) SysUiServiceProvider.getComponent(this.mContext, CommandQueue.class)).removeCallbacks(this);
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public void showGlobalActions(GlobalActions.GlobalActionsManager manager) {
        if (this.mDisabled) {
            return;
        }
        if (this.mGlobalActions == null) {
            this.mGlobalActions = new GlobalActionsDialog(this.mContext, manager);
        }
        this.mGlobalActions.showDialog(this.mKeyguardMonitor.isShowing(), this.mDeviceProvisionedController.isDeviceProvisioned());
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public void showShutdownUi(boolean isReboot, String reason) {
        ProgressDialog pd = new ProgressDialog(this.mContext);
        pd.setMessage(this.mContext.getText(R.string.keyguard_password_entry_touch_hint));
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.getWindow().setType(2009);
        pd.show();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void disable(int state1, int state2, boolean animate) {
        boolean disabled = (state2 & 8) != 0;
        if (disabled == this.mDisabled) {
            return;
        }
        this.mDisabled = disabled;
        if (disabled && this.mGlobalActions != null) {
            this.mGlobalActions.dismissDialog();
        }
    }
}
