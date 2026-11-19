package com.android.systemui.volume;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.VolumePolicy;
import android.os.Bundle;
import android.os.Handler;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUI;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.tuner.TunerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class VolumeDialogComponent implements TunerService.Tunable, VolumeComponent, VolumeDialogControllerImpl.UserActivityListener {
    private final Context mContext;
    private VolumeDialog mDialog;
    private final SystemUI mSysui;
    private final InterestingConfigChanges mConfigChanges = new InterestingConfigChanges(-1073741820);
    private VolumePolicy mVolumePolicy = new VolumePolicy(false, false, false, 400);
    private final VolumeDialog.Callback mVolumeDialogCallback = new VolumeDialog.Callback() { // from class: com.android.systemui.volume.VolumeDialogComponent.1
        @Override // com.android.systemui.plugins.VolumeDialog.Callback
        public void onZenSettingsClicked() {
            VolumeDialogComponent.this.startSettings(ZenModePanel.ZEN_SETTINGS);
        }

        @Override // com.android.systemui.plugins.VolumeDialog.Callback
        public void onZenPrioritySettingsClicked() {
            VolumeDialogComponent.this.startSettings(ZenModePanel.ZEN_PRIORITY_SETTINGS);
        }
    };
    private final VolumeDialogControllerImpl mController = (VolumeDialogControllerImpl) Dependency.get(VolumeDialogController.class);

    public VolumeDialogComponent(SystemUI sysui, Context context, Handler handler) {
        this.mSysui = sysui;
        this.mContext = context;
        this.mController.setUserActivityListener(this);
        ((PluginDependencyProvider) Dependency.get(PluginDependencyProvider.class)).allowPluginDependency(VolumeDialogController.class);
        ((ExtensionController) Dependency.get(ExtensionController.class)).newExtension(VolumeDialog.class).withPlugin(VolumeDialog.class).withDefault(new Supplier() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogComponent$ZrIXH_vbJQUohqzHD9D7gJaZLEI
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.createDefault();
            }
        }).withFeature("android.hardware.type.automotive", new Supplier() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogComponent$_YDQvDgAZa0Z1NSD02XWqisctiE
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.createCarDefault();
            }
        }).withCallback(new Consumer() { // from class: com.android.systemui.volume.-$$Lambda$VolumeDialogComponent$vZvGMkdhFGTZ9hLE1BnozIW6Wb0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                VolumeDialogComponent.lambda$new$0(this.f$0, (VolumeDialog) obj);
            }
        }).build();
        applyConfiguration();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "sysui_volume_down_silent", "sysui_volume_up_silent", "sysui_do_not_disturb");
    }

    public static /* synthetic */ void lambda$new$0(VolumeDialogComponent volumeDialogComponent, VolumeDialog dialog) {
        if (volumeDialogComponent.mDialog != null) {
            volumeDialogComponent.mDialog.destroy();
        }
        volumeDialogComponent.mDialog = dialog;
        volumeDialogComponent.mDialog.init(2020, volumeDialogComponent.mVolumeDialogCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VolumeDialog createDefault() {
        VolumeDialogImpl impl = new VolumeDialogImpl(this.mContext);
        impl.setStreamImportant(1, false);
        impl.setAutomute(true);
        impl.setSilentMode(false);
        return impl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VolumeDialog createCarDefault() {
        return new CarVolumeDialogImpl(this.mContext);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        boolean z = false;
        if ("sysui_volume_down_silent".equals(key)) {
            if (newValue != null && Integer.parseInt(newValue) != 0) {
                z = true;
            }
            boolean volumeDownToEnterSilent = z;
            setVolumePolicy(volumeDownToEnterSilent, this.mVolumePolicy.volumeUpToExitSilent, this.mVolumePolicy.doNotDisturbWhenSilent, this.mVolumePolicy.vibrateToSilentDebounce);
            return;
        }
        if ("sysui_volume_up_silent".equals(key)) {
            if (newValue != null && Integer.parseInt(newValue) != 0) {
                z = true;
            }
            boolean volumeUpToExitSilent = z;
            setVolumePolicy(this.mVolumePolicy.volumeDownToEnterSilent, volumeUpToExitSilent, this.mVolumePolicy.doNotDisturbWhenSilent, this.mVolumePolicy.vibrateToSilentDebounce);
            return;
        }
        if ("sysui_do_not_disturb".equals(key)) {
            if (newValue != null && Integer.parseInt(newValue) != 0) {
                z = true;
            }
            boolean doNotDisturbWhenSilent = z;
            setVolumePolicy(this.mVolumePolicy.volumeDownToEnterSilent, this.mVolumePolicy.volumeUpToExitSilent, doNotDisturbWhenSilent, this.mVolumePolicy.vibrateToSilentDebounce);
        }
    }

    private void setVolumePolicy(boolean volumeDownToEnterSilent, boolean volumeUpToExitSilent, boolean doNotDisturbWhenSilent, int vibrateToSilentDebounce) {
        this.mVolumePolicy = new VolumePolicy(volumeDownToEnterSilent, volumeUpToExitSilent, doNotDisturbWhenSilent, vibrateToSilentDebounce);
        this.mController.setVolumePolicy(this.mVolumePolicy);
    }

    void setEnableDialogs(boolean volumeUi, boolean safetyWarning) {
        this.mController.setEnableDialogs(volumeUi, safetyWarning);
    }

    @Override // com.android.systemui.volume.VolumeDialogControllerImpl.UserActivityListener
    public void onUserActivity() {
        KeyguardViewMediator kvm = (KeyguardViewMediator) this.mSysui.getComponent(KeyguardViewMediator.class);
        if (kvm != null) {
            kvm.userActivity();
        }
    }

    private void applyConfiguration() {
        this.mController.setVolumePolicy(this.mVolumePolicy);
        this.mController.showDndTile(true);
    }

    @Override // com.android.systemui.volume.VolumeComponent
    public void onConfigurationChanged(Configuration newConfig) {
        if (this.mConfigChanges.applyNewConfig(this.mContext.getResources())) {
            this.mController.mCallbacks.onConfigurationChanged();
        }
    }

    @Override // com.android.systemui.volume.VolumeComponent
    public void dismissNow() {
        this.mController.dismiss();
    }

    @Override // com.android.systemui.DemoMode
    public void dispatchDemoCommand(String command, Bundle args) {
    }

    @Override // com.android.systemui.volume.VolumeComponent
    public void register() {
        this.mController.register();
        DndTile.setCombinedIcon(this.mContext, true);
    }

    @Override // com.android.systemui.volume.VolumeComponent
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSettings(Intent intent) {
        ((ActivityStarter) Dependency.get(ActivityStarter.class)).startActivity(intent, true, true);
    }
}
