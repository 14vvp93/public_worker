package com.android.systemui.globalactions;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.IStopUserCallback;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.drawable.GradientDrawable;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.Dependency;
import com.android.systemui.HardwareUiLayout;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.volume.SystemUIInterpolators;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class GlobalActionsDialog implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    private MyAdapter mAdapter;
    private ToggleAction mAirplaneModeOn;
    private final AudioManager mAudioManager;
    private final Context mContext;
    private final DevicePolicyManager mDevicePolicyManager;
    private ActionsDialog mDialog;
    private final EmergencyAffordanceManager mEmergencyAffordanceManager;
    private boolean mHasLockdownButton;
    private boolean mHasLogoutButton;
    private boolean mHasTelephony;
    private boolean mHasVibrator;
    private ArrayList<Action> mItems;
    private final KeyguardManager mKeyguardManager;
    private final LockPatternUtils mLockPatternUtils;
    private final ScreenshotHelper mScreenshotHelper;
    private final boolean mShowSilentToggle;
    private Action mSilentModeAction;
    private final GlobalActions.GlobalActionsManager mWindowManagerFuncs;
    private boolean mKeyguardShowing = false;
    private boolean mDeviceProvisioned = false;
    private ToggleAction.State mAirplaneState = ToggleAction.State.Off;
    private boolean mIsWaitingForEcmExit = false;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.8
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) || "android.intent.action.SCREEN_OFF".equals(action)) {
                String reason = intent.getStringExtra("reason");
                if (!"globalactions".equals(reason)) {
                    GlobalActionsDialog.this.mHandler.sendMessage(GlobalActionsDialog.this.mHandler.obtainMessage(0, reason));
                    return;
                }
                return;
            }
            if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) && !intent.getBooleanExtra("PHONE_IN_ECM_STATE", false) && GlobalActionsDialog.this.mIsWaitingForEcmExit) {
                GlobalActionsDialog.this.mIsWaitingForEcmExit = false;
                GlobalActionsDialog.this.changeAirplaneModeSystemSetting(true);
            }
        }
    };
    PhoneStateListener mPhoneStateListener = new PhoneStateListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.9
        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            if (GlobalActionsDialog.this.mHasTelephony) {
                boolean inAirplaneMode = serviceState.getState() == 3;
                GlobalActionsDialog.this.mAirplaneState = inAirplaneMode ? ToggleAction.State.On : ToggleAction.State.Off;
                GlobalActionsDialog.this.mAirplaneModeOn.updateState(GlobalActionsDialog.this.mAirplaneState);
                GlobalActionsDialog.this.mAdapter.notifyDataSetChanged();
            }
        }
    };
    private BroadcastReceiver mRingerModeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.10
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.RINGER_MODE_CHANGED")) {
                GlobalActionsDialog.this.mHandler.sendEmptyMessage(1);
            }
        }
    };
    private ContentObserver mAirplaneModeObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.11
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            GlobalActionsDialog.this.onAirplaneModeChanged();
        }
    };
    private Handler mHandler = new Handler() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.12
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (GlobalActionsDialog.this.mDialog != null) {
                        if ("dream".equals(msg.obj)) {
                            GlobalActionsDialog.this.mDialog.dismissImmediately();
                        } else {
                            GlobalActionsDialog.this.mDialog.dismiss();
                        }
                        GlobalActionsDialog.this.mDialog = null;
                    }
                    break;
                case 1:
                    GlobalActionsDialog.this.refreshSilentMode();
                    GlobalActionsDialog.this.mAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    GlobalActionsDialog.this.handleShow();
                    break;
            }
        }
    };
    private final IDreamManager mDreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));

    /* JADX INFO: Access modifiers changed from: private */
    interface Action {
        View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater);

        boolean isEnabled();

        void onPress();

        boolean showBeforeProvisioning();

        boolean showDuringKeyguard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface LongPressAction extends Action {
        boolean onLongPress();
    }

    public GlobalActionsDialog(Context context, GlobalActions.GlobalActionsManager windowManagerFuncs) {
        boolean z = false;
        this.mContext = new ContextThemeWrapper(context, R.style.qs_theme);
        this.mWindowManagerFuncs = windowManagerFuncs;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        context.registerReceiver(this.mBroadcastReceiver, filter);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        this.mHasTelephony = cm.isNetworkSupported(0);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        telephonyManager.listen(this.mPhoneStateListener, 1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("airplane_mode_on"), true, this.mAirplaneModeObserver);
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService("vibrator");
        if (vibrator != null && vibrator.hasVibrator()) {
            z = true;
        }
        this.mHasVibrator = z;
        this.mShowSilentToggle = !this.mContext.getResources().getBoolean(android.R.^attr-private.pointerIconVectorFillInverse);
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
        this.mScreenshotHelper = new ScreenshotHelper(context);
    }

    public void showDialog(boolean keyguardShowing, boolean isDeviceProvisioned) {
        this.mKeyguardShowing = keyguardShowing;
        this.mDeviceProvisioned = isDeviceProvisioned;
        if (this.mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
            this.mHandler.sendEmptyMessage(2);
            return;
        }
        handleShow();
    }

    public void dismissDialog() {
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessage(0);
    }

    private void awakenIfNecessary() {
        if (this.mDreamManager != null) {
            try {
                if (this.mDreamManager.isDreaming()) {
                    this.mDreamManager.awaken();
                }
            } catch (RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShow() {
        awakenIfNecessary();
        this.mDialog = createDialog();
        prepareDialog();
        if (this.mAdapter.getCount() == 1 && (this.mAdapter.getItem(0) instanceof SinglePressAction) && !(this.mAdapter.getItem(0) instanceof LongPressAction)) {
            ((SinglePressAction) this.mAdapter.getItem(0)).onPress();
            return;
        }
        WindowManager.LayoutParams attrs = this.mDialog.getWindow().getAttributes();
        attrs.setTitle("ActionsDialog");
        attrs.layoutInDisplayCutoutMode = 1;
        this.mDialog.getWindow().setAttributes(attrs);
        this.mDialog.show();
        this.mWindowManagerFuncs.onGlobalActionsShown();
    }

    private ActionsDialog createDialog() {
        if (!this.mHasVibrator) {
            this.mSilentModeAction = new SilentModeToggleAction();
        } else {
            this.mSilentModeAction = new SilentModeTriStateAction(this.mContext, this.mAudioManager, this.mHandler);
        }
        this.mAirplaneModeOn = new ToggleAction(android.R.drawable.ic_ab_back_holo_dark, android.R.drawable.ic_ab_back_holo_light, android.R.string.config_defaultTrustAgent, android.R.string.config_defaultTranslationService, android.R.string.config_defaultTextClassifierPackage) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.1
            @Override // com.android.systemui.globalactions.GlobalActionsDialog.ToggleAction
            void onToggle(boolean on) {
                if (!GlobalActionsDialog.this.mHasTelephony || !Boolean.parseBoolean(SystemProperties.get("ril.cdma.inecmmode"))) {
                    GlobalActionsDialog.this.changeAirplaneModeSystemSetting(on);
                    return;
                }
                GlobalActionsDialog.this.mIsWaitingForEcmExit = true;
                Intent ecmDialogIntent = new Intent("com.android.internal.intent.action.ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
                ecmDialogIntent.addFlags(268435456);
                GlobalActionsDialog.this.mContext.startActivity(ecmDialogIntent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.ToggleAction
            protected void changeStateFromPress(boolean buttonOn) {
                if (GlobalActionsDialog.this.mHasTelephony && !Boolean.parseBoolean(SystemProperties.get("ril.cdma.inecmmode"))) {
                    this.mState = buttonOn ? ToggleAction.State.TurningOn : ToggleAction.State.TurningOff;
                    GlobalActionsDialog.this.mAirplaneState = this.mState;
                }
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showDuringKeyguard() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showBeforeProvisioning() {
                return false;
            }
        };
        onAirplaneModeChanged();
        this.mItems = new ArrayList<>();
        String[] defaultActions = this.mContext.getResources().getStringArray(android.R.array.config_builtInDisplayIsRoundArray);
        ArraySet<String> addedKeys = new ArraySet<>();
        this.mHasLogoutButton = false;
        this.mHasLockdownButton = false;
        int i = 0;
        while (true) {
            if (i >= defaultActions.length) {
                break;
            }
            String actionKey = defaultActions[i];
            if (!addedKeys.contains(actionKey)) {
                if ("power".equals(actionKey)) {
                    this.mItems.add(new PowerAction());
                } else if ("airplane".equals(actionKey)) {
                    this.mItems.add(this.mAirplaneModeOn);
                } else if ("bugreport".equals(actionKey)) {
                    if (Settings.Global.getInt(this.mContext.getContentResolver(), "bugreport_in_power_menu", 0) != 0 && isCurrentUserOwner()) {
                        this.mItems.add(new BugReportAction());
                    }
                } else if ("silent".equals(actionKey)) {
                    if (this.mShowSilentToggle) {
                        this.mItems.add(this.mSilentModeAction);
                    }
                } else if ("users".equals(actionKey)) {
                    if (SystemProperties.getBoolean("fw.power_user_switcher", false)) {
                        addUsersToMenu(this.mItems);
                    }
                } else if ("settings".equals(actionKey)) {
                    this.mItems.add(getSettingsAction());
                } else if ("lockdown".equals(actionKey)) {
                    if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lockdown_in_power_menu", 0, getCurrentUser().id) != 0 && shouldDisplayLockdown()) {
                        this.mItems.add(getLockdownAction());
                        this.mHasLockdownButton = true;
                    }
                } else if ("voiceassist".equals(actionKey)) {
                    this.mItems.add(getVoiceAssistAction());
                } else if ("assist".equals(actionKey)) {
                    this.mItems.add(getAssistAction());
                } else if ("restart".equals(actionKey)) {
                    this.mItems.add(new RestartAction());
                } else if ("screenshot".equals(actionKey)) {
                    this.mItems.add(new ScreenshotAction());
                } else if ("logout".equals(actionKey)) {
                    if (this.mDevicePolicyManager.isLogoutEnabled() && getCurrentUser().id != 0) {
                        this.mItems.add(new LogoutAction());
                        this.mHasLogoutButton = true;
                    }
                } else {
                    Log.e("GlobalActionsDialog", "Invalid global action key " + actionKey);
                }
                addedKeys.add(actionKey);
            }
            i++;
        }
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            this.mItems.add(getEmergencyAction());
        }
        this.mAdapter = new MyAdapter();
        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$KCr2UERgVxA0G1QTICG9cHJxmlk
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i2, long j) {
                return GlobalActionsDialog.lambda$createDialog$0(this.f$0, adapterView, view, i2, j);
            }
        };
        ActionsDialog dialog = new ActionsDialog(this.mContext, this, this.mAdapter, onItemLongClickListener);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setKeyguardShowing(this.mKeyguardShowing);
        dialog.setOnDismissListener(this);
        return dialog;
    }

    public static /* synthetic */ boolean lambda$createDialog$0(GlobalActionsDialog globalActionsDialog, AdapterView parent, View view, int position, long id) {
        Action action = globalActionsDialog.mAdapter.getItem(position);
        if (action instanceof LongPressAction) {
            globalActionsDialog.mDialog.dismiss();
            return ((LongPressAction) action).onLongPress();
        }
        return false;
    }

    private boolean shouldDisplayLockdown() {
        int userId = getCurrentUser().id;
        if (!this.mKeyguardManager.isDeviceSecure(userId)) {
            return false;
        }
        int state = this.mLockPatternUtils.getStrongAuthForUser(userId);
        return state == 0 || state == 4;
    }

    private final class PowerAction extends SinglePressAction implements LongPressAction {
        private PowerAction() {
            super(android.R.drawable.ic_lock_power_off, android.R.string.config_defaultProfcollectReportUploaderApp);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.LongPressAction
        public boolean onLongPress() {
            UserManager um = (UserManager) GlobalActionsDialog.this.mContext.getSystemService("user");
            if (!um.hasUserRestriction("no_safe_boot")) {
                GlobalActionsDialog.this.mWindowManagerFuncs.reboot(true);
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
            GlobalActionsDialog.this.mWindowManagerFuncs.shutdown();
        }
    }

    private final class RestartAction extends SinglePressAction implements LongPressAction {
        private RestartAction() {
            super(android.R.drawable.ic_media_route_connected_light_16_mtrl, android.R.string.config_defaultQrCodeComponent);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.LongPressAction
        public boolean onLongPress() {
            UserManager um = (UserManager) GlobalActionsDialog.this.mContext.getSystemService("user");
            if (!um.hasUserRestriction("no_safe_boot")) {
                GlobalActionsDialog.this.mWindowManagerFuncs.reboot(true);
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
            GlobalActionsDialog.this.mWindowManagerFuncs.reboot(false);
        }
    }

    private class ScreenshotAction extends SinglePressAction {
        public ScreenshotAction() {
            super(android.R.drawable.ic_media_route_connected_light_18_mtrl, android.R.string.config_defaultRotationResolverService);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
            GlobalActionsDialog.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.ScreenshotAction.1
                @Override // java.lang.Runnable
                public void run() {
                    GlobalActionsDialog.this.mScreenshotHelper.takeScreenshot(1, true, true, GlobalActionsDialog.this.mHandler);
                    MetricsLogger.action(GlobalActionsDialog.this.mContext, 1282);
                }
            }, 500L);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return false;
        }
    }

    private class BugReportAction extends SinglePressAction implements LongPressAction {
        public BugReportAction() {
            super(android.R.drawable.ic_ab_back_material, android.R.string.accessibility_autoclick_scroll_up);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
            if (!ActivityManager.isUserAMonkey()) {
                GlobalActionsDialog.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.BugReportAction.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MetricsLogger.action(GlobalActionsDialog.this.mContext, 292);
                            ActivityManager.getService().requestBugReport(1);
                        } catch (RemoteException e) {
                        }
                    }
                }, 500L);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.LongPressAction
        public boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            try {
                MetricsLogger.action(GlobalActionsDialog.this.mContext, 293);
                ActivityManager.getService().requestBugReport(0);
            } catch (RemoteException e) {
            }
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction
        public String getStatus() {
            return GlobalActionsDialog.this.mContext.getString(android.R.string.accessibility_autoclick_scroll_right, Build.VERSION.RELEASE, Build.ID);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LogoutAction extends SinglePressAction {
        private LogoutAction() {
            super(android.R.drawable.ic_bluetooth_transient_animation, android.R.string.config_defaultProfcollectReportUploaderAction);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
            GlobalActionsDialog.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$LogoutAction$3H17sX2I_BqMu2dZ5Dekk1AEv-U
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsDialog.LogoutAction.lambda$onPress$0(this.f$0);
                }
            }, 500L);
        }

        public static /* synthetic */ void lambda$onPress$0(LogoutAction logoutAction) {
            try {
                int currentUserId = GlobalActionsDialog.this.getCurrentUser().id;
                ActivityManager.getService().switchUser(0);
                ActivityManager.getService().stopUser(currentUserId, true, (IStopUserCallback) null);
            } catch (RemoteException re) {
                Log.e("GlobalActionsDialog", "Couldn't logout user " + re);
            }
        }
    }

    private Action getSettingsAction() {
        return new SinglePressAction(android.R.drawable.ic_media_route_connected_light_24_mtrl, android.R.string.config_defaultSearchSelectorPackageName) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.2
            @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
            public void onPress() {
                Intent intent = new Intent("android.settings.SETTINGS");
                intent.addFlags(335544320);
                GlobalActionsDialog.this.mContext.startActivity(intent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showDuringKeyguard() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private Action getEmergencyAction() {
        return new SinglePressAction(android.R.drawable.btn_toggle_off_pressed_holo_light, android.R.string.config_defaultNetworkRecommendationProviderPackage) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.3
            @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
            public void onPress() {
                GlobalActionsDialog.this.mEmergencyAffordanceManager.performEmergencyCall();
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showDuringKeyguard() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private Action getAssistAction() {
        return new SinglePressAction(android.R.drawable.dark_header_dither, android.R.string.config_defaultMusicRecognitionService) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.4
            @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
            public void onPress() {
                Intent intent = new Intent("android.intent.action.ASSIST");
                intent.addFlags(335544320);
                GlobalActionsDialog.this.mContext.startActivity(intent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showDuringKeyguard() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private Action getVoiceAssistAction() {
        return new SinglePressAction(android.R.drawable.ic_media_route_connecting_dark_24_mtrl, android.R.string.config_defaultSupervisionProfileOwnerComponent) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.5
            @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
            public void onPress() {
                Intent intent = new Intent("android.intent.action.VOICE_ASSIST");
                intent.addFlags(335544320);
                GlobalActionsDialog.this.mContext.startActivity(intent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showDuringKeyguard() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    /* JADX INFO: renamed from: com.android.systemui.globalactions.GlobalActionsDialog$6, reason: invalid class name */
    class AnonymousClass6 extends SinglePressAction {
        AnonymousClass6(int iconResId, int messageResId) {
            super(iconResId, messageResId);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
            new LockPatternUtils(GlobalActionsDialog.this.mContext).requireStrongAuth(32, -1);
            try {
                WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                Handler bgHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
                bgHandler.post(new Runnable() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$6$WgIPOZvSRFzb_yD8-G_WZbXNLMU
                    @Override // java.lang.Runnable
                    public final void run() {
                        GlobalActionsDialog.this.lockProfiles();
                    }
                });
            } catch (RemoteException e) {
                Log.e("GlobalActionsDialog", "Error while trying to lock device.", e);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return false;
        }
    }

    private Action getLockdownAction() {
        return new AnonymousClass6(android.R.drawable.ic_ab_back_material_settings, android.R.string.config_defaultOnDeviceSpeechRecognitionService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockProfiles() {
        UserManager um = (UserManager) this.mContext.getSystemService("user");
        TrustManager tm = (TrustManager) this.mContext.getSystemService("trust");
        int currentUserId = getCurrentUser().id;
        int[] profileIds = um.getEnabledProfileIds(currentUserId);
        for (int id : profileIds) {
            if (id != currentUserId) {
                tm.setDeviceLockedForUser(id, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UserInfo getCurrentUser() {
        try {
            return ActivityManager.getService().getCurrentUser();
        } catch (RemoteException e) {
            return null;
        }
    }

    private boolean isCurrentUserOwner() {
        UserInfo currentUser = getCurrentUser();
        return currentUser == null || currentUser.isPrimary();
    }

    private void addUsersToMenu(ArrayList<Action> items) {
        UserManager um = (UserManager) this.mContext.getSystemService("user");
        if (um.isUserSwitcherEnabled()) {
            List<UserInfo> users = um.getUsers();
            UserInfo currentUser = getCurrentUser();
            for (final UserInfo user : users) {
                if (user.supportsSwitchToByUser()) {
                    boolean z = false;
                    if (currentUser != null ? currentUser.id == user.id : user.id == 0) {
                        z = true;
                    }
                    boolean isCurrentUser = z;
                    Drawable icon = user.iconPath != null ? Drawable.createFromPath(user.iconPath) : null;
                    int i = android.R.drawable.ic_lock_airplane_mode_off;
                    StringBuilder sb = new StringBuilder();
                    sb.append(user.name != null ? user.name : "Primary");
                    sb.append(isCurrentUser ? " ✔" : "");
                    SinglePressAction switchToUser = new SinglePressAction(i, icon, sb.toString()) { // from class: com.android.systemui.globalactions.GlobalActionsDialog.7
                        @Override // com.android.systemui.globalactions.GlobalActionsDialog.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialog.Action
                        public void onPress() {
                            try {
                                ActivityManager.getService().switchUser(user.id);
                            } catch (RemoteException re) {
                                Log.e("GlobalActionsDialog", "Couldn't switch user " + re);
                            }
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
                        public boolean showDuringKeyguard() {
                            return true;
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
                        public boolean showBeforeProvisioning() {
                            return false;
                        }
                    };
                    items.add(switchToUser);
                }
            }
        }
    }

    private void prepareDialog() {
        refreshSilentMode();
        this.mAirplaneModeOn.updateState(this.mAirplaneState);
        this.mAdapter.notifyDataSetChanged();
        if (this.mShowSilentToggle) {
            IntentFilter filter = new IntentFilter("android.media.RINGER_MODE_CHANGED");
            this.mContext.registerReceiver(this.mRingerModeReceiver, filter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshSilentMode() {
        if (!this.mHasVibrator) {
            boolean silentModeOn = this.mAudioManager.getRingerMode() != 2;
            ((ToggleAction) this.mSilentModeAction).updateState(silentModeOn ? ToggleAction.State.On : ToggleAction.State.Off);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        this.mWindowManagerFuncs.onGlobalActionsHidden();
        if (this.mShowSilentToggle) {
            try {
                this.mContext.unregisterReceiver(this.mRingerModeReceiver);
            } catch (IllegalArgumentException ie) {
                Log.w("GlobalActionsDialog", ie);
            }
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        Action item = this.mAdapter.getItem(which);
        if (!(item instanceof SilentModeTriStateAction)) {
            dialog.dismiss();
        }
        item.onPress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MyAdapter extends BaseAdapter {
        private MyAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int count = 0;
            for (int i = 0; i < GlobalActionsDialog.this.mItems.size(); i++) {
                Action action = (Action) GlobalActionsDialog.this.mItems.get(i);
                if ((!GlobalActionsDialog.this.mKeyguardShowing || action.showDuringKeyguard()) && (GlobalActionsDialog.this.mDeviceProvisioned || action.showBeforeProvisioning())) {
                    count++;
                }
            }
            return count;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int position) {
            return getItem(position).isEnabled();
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override // android.widget.Adapter
        public Action getItem(int position) {
            int filteredPos = 0;
            for (int i = 0; i < GlobalActionsDialog.this.mItems.size(); i++) {
                Action action = (Action) GlobalActionsDialog.this.mItems.get(i);
                if ((!GlobalActionsDialog.this.mKeyguardShowing || action.showDuringKeyguard()) && (GlobalActionsDialog.this.mDeviceProvisioned || action.showBeforeProvisioning())) {
                    if (filteredPos == position) {
                        return action;
                    }
                    filteredPos++;
                }
            }
            throw new IllegalArgumentException("position " + position + " out of range of showable actions, filtered count=" + getCount() + ", keyguardshowing=" + GlobalActionsDialog.this.mKeyguardShowing + ", provisioned=" + GlobalActionsDialog.this.mDeviceProvisioned);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            Action action = getItem(position);
            View view = action.create(GlobalActionsDialog.this.mContext, convertView, parent, LayoutInflater.from(GlobalActionsDialog.this.mContext));
            if (position == getCount() - 1) {
                HardwareUiLayout.get(parent).setDivisionView(view);
            }
            return view;
        }
    }

    private static abstract class SinglePressAction implements Action {
        private final Drawable mIcon;
        private final int mIconResId;
        private final CharSequence mMessage;
        private final int mMessageResId;

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public abstract void onPress();

        protected SinglePressAction(int iconResId, int messageResId) {
            this.mIconResId = iconResId;
            this.mMessageResId = messageResId;
            this.mMessage = null;
            this.mIcon = null;
        }

        protected SinglePressAction(int iconResId, Drawable icon, CharSequence message) {
            this.mIconResId = iconResId;
            this.mMessageResId = 0;
            this.mMessage = message;
            this.mIcon = icon;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean isEnabled() {
            return true;
        }

        public String getStatus() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public View create(Context context, View convertView, ViewGroup parent, LayoutInflater inflater) {
            View v = inflater.inflate(R.layout.global_actions_item, parent, false);
            ImageView icon = (ImageView) v.findViewById(android.R.id.icon);
            TextView messageView = (TextView) v.findViewById(android.R.id.message);
            TextView statusView = (TextView) v.findViewById(android.R.id.nosensor);
            String status = getStatus();
            if (!TextUtils.isEmpty(status)) {
                statusView.setText(status);
            } else {
                statusView.setVisibility(8);
            }
            if (this.mIcon != null) {
                icon.setImageDrawable(this.mIcon);
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else if (this.mIconResId != 0) {
                icon.setImageDrawable(context.getDrawable(this.mIconResId));
            }
            if (this.mMessage != null) {
                messageView.setText(this.mMessage);
            } else {
                messageView.setText(this.mMessageResId);
            }
            return v;
        }
    }

    private static abstract class ToggleAction implements Action {
        protected int mDisabledIconResid;
        protected int mDisabledStatusMessageResId;
        protected int mEnabledIconResId;
        protected int mEnabledStatusMessageResId;
        protected int mMessageResId;
        protected State mState = State.Off;

        abstract void onToggle(boolean z);

        enum State {
            Off(false),
            TurningOn(true),
            TurningOff(true),
            On(false);

            private final boolean inTransition;

            State(boolean intermediate) {
                this.inTransition = intermediate;
            }

            public boolean inTransition() {
                return this.inTransition;
            }
        }

        public ToggleAction(int enabledIconResId, int disabledIconResid, int message, int enabledStatusMessageResId, int disabledStatusMessageResId) {
            this.mEnabledIconResId = enabledIconResId;
            this.mDisabledIconResid = disabledIconResid;
            this.mMessageResId = message;
            this.mEnabledStatusMessageResId = enabledStatusMessageResId;
            this.mDisabledStatusMessageResId = disabledStatusMessageResId;
        }

        void willCreate() {
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public View create(Context context, View convertView, ViewGroup parent, LayoutInflater inflater) {
            willCreate();
            View v = inflater.inflate(android.R.layout.conversation_face_pile_layout, parent, false);
            ImageView icon = (ImageView) v.findViewById(android.R.id.icon);
            TextView messageView = (TextView) v.findViewById(android.R.id.message);
            TextView statusView = (TextView) v.findViewById(android.R.id.nosensor);
            boolean enabled = isEnabled();
            if (messageView != null) {
                messageView.setText(this.mMessageResId);
                messageView.setEnabled(enabled);
            }
            boolean on = this.mState == State.On || this.mState == State.TurningOn;
            if (icon != null) {
                icon.setImageDrawable(context.getDrawable(on ? this.mEnabledIconResId : this.mDisabledIconResid));
                icon.setEnabled(enabled);
            }
            if (statusView != null) {
                statusView.setText(on ? this.mEnabledStatusMessageResId : this.mDisabledStatusMessageResId);
                statusView.setVisibility(0);
                statusView.setEnabled(enabled);
            }
            v.setEnabled(enabled);
            return v;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public final void onPress() {
            if (this.mState.inTransition()) {
                Log.w("GlobalActionsDialog", "shouldn't be able to toggle when in transition");
                return;
            }
            boolean nowOn = this.mState != State.On;
            onToggle(nowOn);
            changeStateFromPress(nowOn);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean isEnabled() {
            return !this.mState.inTransition();
        }

        protected void changeStateFromPress(boolean buttonOn) {
            this.mState = buttonOn ? State.On : State.Off;
        }

        public void updateState(State state) {
            this.mState = state;
        }
    }

    private class SilentModeToggleAction extends ToggleAction {
        public SilentModeToggleAction() {
            super(android.R.drawable.dialog_ic_close_focused_holo_light, android.R.drawable.dialog_ic_close_focused_holo_dark, android.R.string.config_defaultSmartspaceService, android.R.string.config_defaultSelectToSpeakService, android.R.string.config_defaultSearchUiService);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.ToggleAction
        void onToggle(boolean on) {
            if (on) {
                GlobalActionsDialog.this.mAudioManager.setRingerMode(0);
            } else {
                GlobalActionsDialog.this.mAudioManager.setRingerMode(2);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return false;
        }
    }

    private static class SilentModeTriStateAction implements View.OnClickListener, Action {
        private final int[] ITEM_IDS = {android.R.id.headers, android.R.id.health, android.R.id.help};
        private final AudioManager mAudioManager;
        private final Context mContext;
        private final Handler mHandler;

        SilentModeTriStateAction(Context context, AudioManager audioManager, Handler handler) {
            this.mAudioManager = audioManager;
            this.mHandler = handler;
            this.mContext = context;
        }

        private int ringerModeToIndex(int ringerMode) {
            return ringerMode;
        }

        private int indexToRingerMode(int index) {
            return index;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public View create(Context context, View convertView, ViewGroup parent, LayoutInflater inflater) {
            View v = inflater.inflate(android.R.layout.date_picker_dialog, parent, false);
            int selectedIndex = ringerModeToIndex(this.mAudioManager.getRingerMode());
            int i = 0;
            while (i < 3) {
                View itemView = v.findViewById(this.ITEM_IDS[i]);
                itemView.setSelected(selectedIndex == i);
                itemView.setTag(Integer.valueOf(i));
                itemView.setOnClickListener(this);
                i++;
            }
            return v;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public void onPress() {
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialog.Action
        public boolean isEnabled() {
            return true;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (v.getTag() instanceof Integer) {
                int index = ((Integer) v.getTag()).intValue();
                this.mAudioManager.setRingerMode(indexToRingerMode(index));
                this.mHandler.sendEmptyMessageDelayed(0, 300L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAirplaneModeChanged() {
        if (this.mHasTelephony) {
            return;
        }
        boolean airplaneModeOn = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
        this.mAirplaneState = airplaneModeOn ? ToggleAction.State.On : ToggleAction.State.Off;
        this.mAirplaneModeOn.updateState(this.mAirplaneState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeAirplaneModeSystemSetting(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "airplane_mode_on", z ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.addFlags(536870912);
        intent.putExtra("state", z);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        if (!this.mHasTelephony) {
            this.mAirplaneState = z ? ToggleAction.State.On : ToggleAction.State.Off;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ActionsDialog extends Dialog implements DialogInterface, ColorExtractor.OnColorsChangedListener {
        private final MyAdapter mAdapter;
        private final DialogInterface.OnClickListener mClickListener;
        private final ColorExtractor mColorExtractor;
        private final Context mContext;
        private final GradientDrawable mGradientDrawable;
        private final HardwareUiLayout mHardwareLayout;
        private boolean mKeyguardShowing;
        private final LinearLayout mListView;
        private final AdapterView.OnItemLongClickListener mLongClickListener;

        public ActionsDialog(Context context, DialogInterface.OnClickListener clickListener, MyAdapter adapter, AdapterView.OnItemLongClickListener longClickListener) {
            super(context, 2131886647);
            this.mContext = context;
            this.mAdapter = adapter;
            this.mClickListener = clickListener;
            this.mLongClickListener = longClickListener;
            this.mGradientDrawable = new GradientDrawable(this.mContext);
            this.mColorExtractor = (ColorExtractor) Dependency.get(SysuiColorExtractor.class);
            Window window = getWindow();
            window.requestFeature(1);
            window.getDecorView();
            window.getAttributes().systemUiVisibility |= 1792;
            window.setLayout(-1, -1);
            window.clearFlags(2);
            window.addFlags(17629472);
            window.setBackgroundDrawable(this.mGradientDrawable);
            window.setType(2020);
            setContentView(R.layout.global_actions_wrapped);
            this.mListView = (LinearLayout) findViewById(android.R.id.list);
            this.mHardwareLayout = HardwareUiLayout.get(this.mListView);
            this.mHardwareLayout.setOutsideTouchListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$dQpDVx5ZJSWswwNRJ2NNvfp5RD8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.dismiss();
                }
            });
            setTitle(android.R.string.config_defaultSystemCaptionsManagerService);
            this.mListView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.globalactions.GlobalActionsDialog.ActionsDialog.1
                @Override // android.view.View.AccessibilityDelegate
                public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                    event.getText().add(ActionsDialog.this.mContext.getString(android.R.string.config_defaultSystemCaptionsManagerService));
                    return true;
                }
            });
        }

        private void updateList() {
            this.mListView.removeAllViews();
            for (int i = 0; i < this.mAdapter.getCount(); i++) {
                final View v = this.mAdapter.getView(i, null, this.mListView);
                final int pos = i;
                v.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$wsWXhl2gpbmXCrLlb4WgO3Hp5Tg
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        GlobalActionsDialog.ActionsDialog actionsDialog = this.f$0;
                        actionsDialog.mClickListener.onClick(actionsDialog, pos);
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$9T4uyogvs71HPKwgm3VUPqbZAHc
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return this.f$0.mLongClickListener.onItemLongClick(null, v, pos, 0L);
                    }
                });
                this.mListView.addView(v);
            }
        }

        @Override // android.app.Dialog
        protected void onStart() {
            int i = 1;
            super.setCanceledOnTouchOutside(true);
            super.onStart();
            updateList();
            Point displaySize = new Point();
            this.mContext.getDisplay().getRealSize(displaySize);
            this.mGradientDrawable.setScreenSize(displaySize.x, displaySize.y);
            ColorExtractor colorExtractor = this.mColorExtractor;
            if (this.mKeyguardShowing) {
                i = 2;
            }
            ColorExtractor.GradientColors colors = colorExtractor.getColors(i);
            updateColors(colors, false);
        }

        private void updateColors(ColorExtractor.GradientColors colors, boolean animate) {
            this.mGradientDrawable.setColors(colors, animate);
            View decorView = getWindow().getDecorView();
            if (colors.supportsDarkText()) {
                decorView.setSystemUiVisibility(8208);
            } else {
                decorView.setSystemUiVisibility(0);
            }
        }

        @Override // android.app.Dialog
        protected void onStop() {
            super.onStop();
        }

        @Override // android.app.Dialog
        public void show() {
            super.show();
            this.mGradientDrawable.setAlpha(0);
            this.mHardwareLayout.setTranslationX(getAnimTranslation());
            this.mHardwareLayout.setAlpha(0.0f);
            this.mHardwareLayout.animate().alpha(1.0f).translationX(0.0f).setDuration(300L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$Qe1JHSA7eQR9eTIOptPltFBwKXg
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    GlobalActionsDialog.ActionsDialog.lambda$show$3(this.f$0, valueAnimator);
                }
            }).withEndAction(new Runnable() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$X-E8xNR_KVfIqzXznITVrFd13Ek
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.getWindow().getDecorView().requestAccessibilityFocus();
                }
            }).start();
        }

        public static /* synthetic */ void lambda$show$3(ActionsDialog actionsDialog, ValueAnimator animation) {
            int alpha = (int) (((Float) animation.getAnimatedValue()).floatValue() * 0.45f * 255.0f);
            actionsDialog.mGradientDrawable.setAlpha(alpha);
        }

        @Override // android.app.Dialog, android.content.DialogInterface
        public void dismiss() {
            this.mHardwareLayout.setTranslationX(0.0f);
            this.mHardwareLayout.setAlpha(1.0f);
            this.mHardwareLayout.animate().alpha(0.0f).translationX(getAnimTranslation()).setDuration(300L).withEndAction(new Runnable() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$2QriKiv-fZQGysQ0teAWx7uBxqg
                @Override // java.lang.Runnable
                public final void run() {
                    super/*android.app.Dialog*/.dismiss();
                }
            }).setInterpolator(new SystemUIInterpolators.LogAccelerateInterpolator()).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.globalactions.-$$Lambda$GlobalActionsDialog$ActionsDialog$_0WJKduv0QvmLhPuj3fXKKiMDpo
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    GlobalActionsDialog.ActionsDialog.lambda$dismiss$6(this.f$0, valueAnimator);
                }
            }).start();
        }

        public static /* synthetic */ void lambda$dismiss$6(ActionsDialog actionsDialog, ValueAnimator animation) {
            int alpha = (int) ((1.0f - ((Float) animation.getAnimatedValue()).floatValue()) * 0.45f * 255.0f);
            actionsDialog.mGradientDrawable.setAlpha(alpha);
        }

        void dismissImmediately() {
            super.dismiss();
        }

        private float getAnimTranslation() {
            return getContext().getResources().getDimension(R.dimen.global_actions_panel_width) / 2.0f;
        }

        public void onColorsChanged(ColorExtractor extractor, int which) {
            if (this.mKeyguardShowing) {
                if ((2 & which) != 0) {
                    updateColors(extractor.getColors(2), true);
                }
            } else if ((1 & which) != 0) {
                updateColors(extractor.getColors(1), true);
            }
        }

        public void setKeyguardShowing(boolean keyguardShowing) {
            this.mKeyguardShowing = keyguardShowing;
        }
    }
}
