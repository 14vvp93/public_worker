package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.IntentButtonProvider;
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.PreviewInflater;
import com.android.systemui.tuner.LockscreenFragment;
import com.android.systemui.tuner.TunerService;
import com.mediatek.keyguard.ext.IEmergencyButtonExt;
import com.mediatek.keyguard.ext.OpKeyguardCustomizationFactoryBase;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardBottomAreaView extends FrameLayout implements View.OnClickListener, View.OnLongClickListener, UnlockMethodCache.OnUnlockMethodChangedListener, AccessibilityController.AccessibilityStateChangedCallback {
    private AccessibilityController mAccessibilityController;
    private View.AccessibilityDelegate mAccessibilityDelegate;
    private ActivityStarter mActivityStarter;
    private KeyguardAffordanceHelper mAffordanceHelper;
    private AssistManager mAssistManager;
    private int mBurnInXOffset;
    private View mCameraPreview;
    private float mDarkAmount;
    private final BroadcastReceiver mDevicePolicyReceiver;
    private boolean mDozing;
    private EmergencyButton mEmergencyButton;
    private IEmergencyButtonExt mEmergencyButtonExt;
    private TextView mEnterpriseDisclosure;
    private FlashlightController mFlashlightController;
    private ViewGroup mIndicationArea;
    private int mIndicationBottomMargin;
    private int mIndicationBottomMarginAmbient;
    private KeyguardIndicationController mIndicationController;
    private TextView mIndicationText;
    private KeyguardAffordanceView mLeftAffordanceView;
    private Drawable mLeftAssistIcon;
    private IntentButtonProvider.IntentButton mLeftButton;
    private String mLeftButtonStr;
    private ExtensionController.Extension<IntentButtonProvider.IntentButton> mLeftExtension;
    private boolean mLeftIsVoiceAssist;
    private View mLeftPreview;
    private LockIcon mLockIcon;
    private LockPatternUtils mLockPatternUtils;
    private LockscreenGestureLogger mLockscreenGestureLogger;
    private ViewGroup mOverlayContainer;
    private ViewGroup mPreviewContainer;
    private PreviewInflater mPreviewInflater;
    private boolean mPrewarmBound;
    private final ServiceConnection mPrewarmConnection;
    private Messenger mPrewarmMessenger;
    private KeyguardAffordanceView mRightAffordanceView;
    private IntentButtonProvider.IntentButton mRightButton;
    private String mRightButtonStr;
    private ExtensionController.Extension<IntentButtonProvider.IntentButton> mRightExtension;
    private StatusBar mStatusBar;
    private UnlockMethodCache mUnlockMethodCache;
    private final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    private boolean mUserSetupComplete;
    private static final Intent SECURE_CAMERA_INTENT = new Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE").addFlags(8388608);
    public static final Intent INSECURE_CAMERA_INTENT = new Intent("android.media.action.STILL_IMAGE_CAMERA");
    private static final Intent PHONE_INTENT = new Intent("android.intent.action.DIAL");

    public KeyguardBottomAreaView(Context context) {
        this(context, null);
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPrewarmConnection = new ServiceConnection() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                KeyguardBottomAreaView.this.mPrewarmMessenger = new Messenger(service);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                KeyguardBottomAreaView.this.mPrewarmMessenger = null;
            }
        };
        this.mRightButton = new DefaultRightButton();
        this.mLeftButton = new DefaultLeftButton();
        this.mLockscreenGestureLogger = new LockscreenGestureLogger();
        this.mAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.2
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                String label = null;
                if (host != KeyguardBottomAreaView.this.mLockIcon) {
                    if (host != KeyguardBottomAreaView.this.mRightAffordanceView) {
                        if (host == KeyguardBottomAreaView.this.mLeftAffordanceView) {
                            if (KeyguardBottomAreaView.this.mLeftIsVoiceAssist) {
                                label = KeyguardBottomAreaView.this.getResources().getString(R.string.voice_assist_label);
                            } else {
                                label = KeyguardBottomAreaView.this.getResources().getString(R.string.phone_label);
                            }
                        }
                    } else {
                        label = KeyguardBottomAreaView.this.getResources().getString(R.string.camera_label);
                    }
                } else {
                    label = KeyguardBottomAreaView.this.getResources().getString(R.string.unlock_label);
                }
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, label));
            }

            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == 16) {
                    if (host == KeyguardBottomAreaView.this.mLockIcon) {
                        KeyguardBottomAreaView.this.mStatusBar.animateCollapsePanels(2, true);
                        return true;
                    }
                    if (host != KeyguardBottomAreaView.this.mRightAffordanceView) {
                        if (host == KeyguardBottomAreaView.this.mLeftAffordanceView) {
                            KeyguardBottomAreaView.this.launchLeftAffordance();
                            return true;
                        }
                    } else {
                        KeyguardBottomAreaView.this.launchCamera("lockscreen_affordance");
                        return true;
                    }
                }
                return super.performAccessibilityAction(host, action, args);
            }
        };
        this.mDevicePolicyReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                KeyguardBottomAreaView.this.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        KeyguardBottomAreaView.this.updateCameraVisibility();
                    }
                });
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.8
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitchComplete(int userId) {
                KeyguardBottomAreaView.this.updateCameraVisibility();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onStartedWakingUp() {
                KeyguardBottomAreaView.this.mLockIcon.setDeviceInteractive(true);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onFinishedGoingToSleep(int why) {
                KeyguardBottomAreaView.this.mLockIcon.setDeviceInteractive(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onScreenTurnedOn() {
                KeyguardBottomAreaView.this.mLockIcon.setScreenOn(true);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onScreenTurnedOff() {
                KeyguardBottomAreaView.this.mLockIcon.setScreenOn(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardVisibilityChanged(boolean showing) {
                KeyguardBottomAreaView.this.mLockIcon.update();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onFingerprintRunningStateChanged(boolean running) {
                KeyguardBottomAreaView.this.mLockIcon.update();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onStrongAuthStateChanged(int userId) {
                KeyguardBottomAreaView.this.mLockIcon.update();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserUnlocked() {
                KeyguardBottomAreaView.this.inflateCameraPreview();
                KeyguardBottomAreaView.this.updateCameraVisibility();
                KeyguardBottomAreaView.this.updateLeftAffordance();
            }
        };
        this.mEmergencyButtonExt = OpKeyguardCustomizationFactoryBase.getOpFactory(context).makeEmergencyButton();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mPreviewContainer = (ViewGroup) findViewById(R.id.preview_container);
        this.mOverlayContainer = (ViewGroup) findViewById(R.id.overlay_container);
        this.mRightAffordanceView = (KeyguardAffordanceView) findViewById(R.id.camera_button);
        this.mLeftAffordanceView = (KeyguardAffordanceView) findViewById(R.id.left_button);
        this.mLockIcon = (LockIcon) findViewById(R.id.lock_icon);
        this.mIndicationArea = (ViewGroup) findViewById(R.id.keyguard_indication_area);
        this.mEmergencyButton = (EmergencyButton) findViewById(R.id.notification_keyguard_emergency_call_button);
        this.mEnterpriseDisclosure = (TextView) findViewById(R.id.keyguard_indication_enterprise_disclosure);
        this.mIndicationText = (TextView) findViewById(R.id.keyguard_indication_text);
        this.mIndicationBottomMargin = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom);
        this.mIndicationBottomMarginAmbient = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_ambient);
        updateCameraVisibility();
        this.mUnlockMethodCache = UnlockMethodCache.getInstance(getContext());
        this.mUnlockMethodCache.addListener(this);
        KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        this.mLockIcon.setScreenOn(updateMonitor.isScreenOn());
        this.mLockIcon.setDeviceInteractive(updateMonitor.isDeviceInteractive());
        this.mLockIcon.update();
        setClipChildren(false);
        setClipToPadding(false);
        this.mPreviewInflater = new PreviewInflater(this.mContext, new LockPatternUtils(this.mContext));
        inflateCameraPreview();
        this.mLockIcon.setOnClickListener(this);
        this.mLockIcon.setOnLongClickListener(this);
        this.mRightAffordanceView.setOnClickListener(this);
        this.mLeftAffordanceView.setOnClickListener(this);
        initAccessibility();
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        this.mFlashlightController = (FlashlightController) Dependency.get(FlashlightController.class);
        this.mAccessibilityController = (AccessibilityController) Dependency.get(AccessibilityController.class);
        this.mAssistManager = (AssistManager) Dependency.get(AssistManager.class);
        this.mLockIcon.setAccessibilityController(this.mAccessibilityController);
        updateLeftAffordance();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAccessibilityController.addStateChangedCallback(this);
        this.mRightExtension = ((ExtensionController) Dependency.get(ExtensionController.class)).newExtension(IntentButtonProvider.IntentButton.class).withPlugin(IntentButtonProvider.class, "com.android.systemui.action.PLUGIN_LOCKSCREEN_RIGHT_BUTTON", new ExtensionController.PluginConverter() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBottomAreaView$g4KaNPI9kzVsHrOlMY-mA_f9J2Y
            @Override // com.android.systemui.statusbar.policy.ExtensionController.PluginConverter
            public final Object getInterfaceFromPlugin(Object obj) {
                return ((IntentButtonProvider) obj).getIntentButton();
            }
        }).withTunerFactory(new LockscreenFragment.LockButtonFactory(this.mContext, "sysui_keyguard_right")).withDefault(new Supplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBottomAreaView$41MKD52m3LHIf9RRtKFf6LfUif0
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardBottomAreaView.lambda$onAttachedToWindow$1(this.f$0);
            }
        }).withCallback(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBottomAreaView$Z_R5g5wpXUcfPYLHCfZHekG4xK0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.setRightButton((IntentButtonProvider.IntentButton) obj);
            }
        }).build();
        this.mLeftExtension = ((ExtensionController) Dependency.get(ExtensionController.class)).newExtension(IntentButtonProvider.IntentButton.class).withPlugin(IntentButtonProvider.class, "com.android.systemui.action.PLUGIN_LOCKSCREEN_LEFT_BUTTON", new ExtensionController.PluginConverter() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBottomAreaView$Eh9_ou4HbbT4H4ZFilpDDtanY4k
            @Override // com.android.systemui.statusbar.policy.ExtensionController.PluginConverter
            public final Object getInterfaceFromPlugin(Object obj) {
                return ((IntentButtonProvider) obj).getIntentButton();
            }
        }).withTunerFactory(new LockscreenFragment.LockButtonFactory(this.mContext, "sysui_keyguard_left")).withDefault(new Supplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBottomAreaView$W-hTEBW5YZVW2MsKtz0LzBCynHY
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardBottomAreaView.lambda$onAttachedToWindow$4(this.f$0);
            }
        }).withCallback(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBottomAreaView$owXxFBBnubMOAUdfyf5a48bf-Zo
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.setLeftButton((IntentButtonProvider.IntentButton) obj);
            }
        }).build();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        getContext().registerReceiverAsUser(this.mDevicePolicyReceiver, UserHandle.ALL, filter, null, null);
        KeyguardUpdateMonitor.getInstance(this.mContext).registerCallback(this.mUpdateMonitorCallback);
    }

    public static /* synthetic */ IntentButtonProvider.IntentButton lambda$onAttachedToWindow$1(KeyguardBottomAreaView keyguardBottomAreaView) {
        return new DefaultRightButton();
    }

    public static /* synthetic */ IntentButtonProvider.IntentButton lambda$onAttachedToWindow$4(KeyguardBottomAreaView keyguardBottomAreaView) {
        return new DefaultLeftButton();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAccessibilityController.removeStateChangedCallback(this);
        this.mRightExtension.destroy();
        this.mLeftExtension.destroy();
        getContext().unregisterReceiver(this.mDevicePolicyReceiver);
        KeyguardUpdateMonitor.getInstance(this.mContext).removeCallback(this.mUpdateMonitorCallback);
    }

    private void initAccessibility() {
        this.mLockIcon.setAccessibilityDelegate(this.mAccessibilityDelegate);
        this.mLeftAffordanceView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        this.mRightAffordanceView.setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mIndicationBottomMargin = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom);
        this.mIndicationBottomMarginAmbient = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_ambient);
        this.mIndicationArea.getLayoutParams();
        this.mEnterpriseDisclosure.setTextSize(0, getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_05));
        this.mIndicationText.setTextSize(0, getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_05));
        ViewGroup.LayoutParams lp = this.mRightAffordanceView.getLayoutParams();
        lp.width = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_width);
        lp.height = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_height);
        this.mRightAffordanceView.setLayoutParams(lp);
        updateRightAffordanceIcon();
        ViewGroup.LayoutParams lp2 = this.mLockIcon.getLayoutParams();
        lp2.width = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_width);
        lp2.height = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_height);
        this.mLockIcon.setLayoutParams(lp2);
        this.mLockIcon.setContentDescription(getContext().getText(R.string.accessibility_unlock_button));
        this.mLockIcon.update(true);
        ViewGroup.LayoutParams lp3 = this.mLeftAffordanceView.getLayoutParams();
        lp3.width = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_width);
        lp3.height = getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_height);
        this.mLeftAffordanceView.setLayoutParams(lp3);
        updateLeftAffordanceIcon();
    }

    private void updateRightAffordanceIcon() {
        IntentButtonProvider.IntentButton.IconState state = this.mRightButton.getIcon();
        this.mRightAffordanceView.setVisibility((this.mDozing || !state.isVisible) ? 8 : 0);
        this.mRightAffordanceView.setImageDrawable(state.drawable, state.tint);
        this.mRightAffordanceView.setContentDescription(state.contentDescription);
    }

    public void setStatusBar(StatusBar statusBar) {
        this.mStatusBar = statusBar;
        updateCameraVisibility();
    }

    public void setAffordanceHelper(KeyguardAffordanceHelper affordanceHelper) {
        this.mAffordanceHelper = affordanceHelper;
    }

    public void setUserSetupComplete(boolean userSetupComplete) {
        this.mUserSetupComplete = userSetupComplete;
        updateCameraVisibility();
        updateLeftAffordanceIcon();
    }

    private Intent getCameraIntent() {
        return this.mRightButton.getIntent();
    }

    public ResolveInfo resolveCameraIntent() {
        return this.mContext.getPackageManager().resolveActivityAsUser(getCameraIntent(), 65536, KeyguardUpdateMonitor.getCurrentUser());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCameraVisibility() {
        if (this.mRightAffordanceView == null) {
            return;
        }
        this.mRightAffordanceView.setVisibility((this.mDozing || !this.mRightButton.getIcon().isVisible) ? 8 : 0);
    }

    private void updateLeftAffordanceIcon() {
        IntentButtonProvider.IntentButton.IconState state = this.mLeftButton.getIcon();
        this.mLeftAffordanceView.setVisibility((this.mDozing || !state.isVisible) ? 8 : 0);
        this.mLeftAffordanceView.setImageDrawable(state.drawable, state.tint);
        this.mLeftAffordanceView.setContentDescription(state.contentDescription);
    }

    public boolean isLeftVoiceAssist() {
        return this.mLeftIsVoiceAssist;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPhoneVisible() {
        PackageManager pm = this.mContext.getPackageManager();
        return pm.hasSystemFeature("android.hardware.telephony") && pm.resolveActivity(PHONE_INTENT, 0) != null;
    }

    @Override // com.android.systemui.statusbar.policy.AccessibilityController.AccessibilityStateChangedCallback
    public void onStateChanged(boolean accessibilityEnabled, boolean touchExplorationEnabled) {
        this.mRightAffordanceView.setClickable(touchExplorationEnabled);
        this.mLeftAffordanceView.setClickable(touchExplorationEnabled);
        this.mRightAffordanceView.setFocusable(accessibilityEnabled);
        this.mLeftAffordanceView.setFocusable(accessibilityEnabled);
        this.mLockIcon.update();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v == this.mRightAffordanceView) {
            launchCamera("lockscreen_affordance");
        } else if (v == this.mLeftAffordanceView) {
            launchLeftAffordance();
        }
        if (v == this.mLockIcon) {
            if (!this.mAccessibilityController.isAccessibilityEnabled()) {
                handleTrustCircleClick();
            } else {
                this.mStatusBar.animateCollapsePanels(0, true);
            }
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View v) {
        handleTrustCircleClick();
        return true;
    }

    private void handleTrustCircleClick() {
        this.mLockscreenGestureLogger.write(191, 0, 0);
        this.mIndicationController.showTransientIndication(R.string.keyguard_indication_trust_disabled);
        this.mLockPatternUtils.requireCredentialEntry(KeyguardUpdateMonitor.getCurrentUser());
    }

    public void bindCameraPrewarmService() {
        String clazz;
        Intent intent = getCameraIntent();
        ActivityInfo targetInfo = PreviewInflater.getTargetActivityInfo(this.mContext, intent, KeyguardUpdateMonitor.getCurrentUser(), true);
        if (targetInfo != null && targetInfo.metaData != null && (clazz = targetInfo.metaData.getString("android.media.still_image_camera_preview_service")) != null) {
            Intent serviceIntent = new Intent();
            serviceIntent.setClassName(targetInfo.packageName, clazz);
            serviceIntent.setAction("android.service.media.CameraPrewarmService.ACTION_PREWARM");
            try {
                if (getContext().bindServiceAsUser(serviceIntent, this.mPrewarmConnection, 67108865, new UserHandle(-2))) {
                    this.mPrewarmBound = true;
                }
            } catch (SecurityException e) {
                Log.w("StatusBar/KeyguardBottomAreaView", "Unable to bind to prewarm service package=" + targetInfo.packageName + " class=" + clazz, e);
            }
        }
    }

    public void unbindCameraPrewarmService(boolean launched) {
        if (this.mPrewarmBound) {
            if (this.mPrewarmMessenger != null && launched) {
                try {
                    this.mPrewarmMessenger.send(Message.obtain((Handler) null, 1));
                } catch (RemoteException e) {
                    Log.w("StatusBar/KeyguardBottomAreaView", "Error sending camera fired message", e);
                }
            }
            this.mContext.unbindService(this.mPrewarmConnection);
            this.mPrewarmBound = false;
        }
    }

    public void launchCamera(String source) {
        final Intent intent = getCameraIntent();
        intent.putExtra("com.android.systemui.camera_launch_source", source);
        boolean wouldLaunchResolverActivity = PreviewInflater.wouldLaunchResolverActivity(this.mContext, intent, KeyguardUpdateMonitor.getCurrentUser());
        if (intent == SECURE_CAMERA_INTENT && !wouldLaunchResolverActivity) {
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.3
                @Override // java.lang.Runnable
                public void run() {
                    int result = -96;
                    ActivityOptions o = ActivityOptions.makeBasic();
                    o.setDisallowEnterPictureInPictureWhileLaunching(true);
                    o.setRotationAnimationHint(3);
                    try {
                        intent.setFlags(67108864);
                        result = ActivityManager.getService().startActivityAsUser((IApplicationThread) null, KeyguardBottomAreaView.this.getContext().getBasePackageName(), intent, intent.resolveTypeIfNeeded(KeyguardBottomAreaView.this.getContext().getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, o.toBundle(), UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("StatusBar/KeyguardBottomAreaView", "Unable to start camera activity", e);
                    }
                    final boolean launched = KeyguardBottomAreaView.isSuccessfulLaunch(result);
                    KeyguardBottomAreaView.this.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            KeyguardBottomAreaView.this.unbindCameraPrewarmService(launched);
                        }
                    });
                }
            });
        } else {
            this.mActivityStarter.startActivity(intent, false, new ActivityStarter.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.4
                @Override // com.android.systemui.plugins.ActivityStarter.Callback
                public void onActivityStarted(int resultCode) {
                    KeyguardBottomAreaView.this.unbindCameraPrewarmService(KeyguardBottomAreaView.isSuccessfulLaunch(resultCode));
                }
            });
        }
    }

    public void setDarkAmount(float darkAmount) {
        if (darkAmount == this.mDarkAmount) {
            return;
        }
        this.mDarkAmount = darkAmount;
        if (darkAmount == 0.0f) {
            this.mIndicationBottomMarginAmbient = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_ambient) + ((int) (Math.random() * ((double) this.mIndicationText.getTextSize())));
        }
        this.mIndicationArea.setAlpha(MathUtils.lerp(1.0f, 0.7f, darkAmount));
        this.mIndicationArea.setTranslationY(MathUtils.lerp(0.0f, this.mIndicationBottomMargin - this.mIndicationBottomMarginAmbient, darkAmount));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSuccessfulLaunch(int result) {
        return result == 0 || result == 3 || result == 2;
    }

    public void launchLeftAffordance() {
        if (this.mLeftIsVoiceAssist) {
            launchVoiceAssist();
        } else {
            launchPhone();
        }
    }

    private void launchVoiceAssist() {
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.5
            @Override // java.lang.Runnable
            public void run() {
                KeyguardBottomAreaView.this.mAssistManager.launchVoiceAssistFromKeyguard();
            }
        };
        if (this.mStatusBar.isKeyguardCurrentlySecure()) {
            AsyncTask.execute(runnable);
        } else {
            boolean dismissShade = (TextUtils.isEmpty(this.mRightButtonStr) || ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_keyguard_right_unlock", 1) == 0) ? false : true;
            this.mStatusBar.executeRunnableDismissingKeyguard(runnable, null, dismissShade, false, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canLaunchVoiceAssist() {
        return this.mAssistManager.canVoiceAssistBeLaunchedFromKeyguard();
    }

    private void launchPhone() {
        final TelecomManager tm = TelecomManager.from(this.mContext);
        if (tm.isInCall()) {
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.6
                @Override // java.lang.Runnable
                public void run() {
                    tm.showInCallScreen(false);
                }
            });
        } else {
            boolean dismissShade = (TextUtils.isEmpty(this.mLeftButtonStr) || ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_keyguard_left_unlock", 1) == 0) ? false : true;
            this.mActivityStarter.startActivity(this.mLeftButton.getIntent(), dismissShade);
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && visibility == 0) {
            this.mLockIcon.update();
            updateCameraVisibility();
        }
    }

    public KeyguardAffordanceView getLeftView() {
        return this.mLeftAffordanceView;
    }

    public KeyguardAffordanceView getRightView() {
        return this.mRightAffordanceView;
    }

    public View getLeftPreview() {
        return this.mLeftPreview;
    }

    public View getRightPreview() {
        return this.mCameraPreview;
    }

    public LockIcon getLockIcon() {
        return this.mLockIcon;
    }

    public View getIndicationArea() {
        return this.mIndicationArea;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.UnlockMethodCache.OnUnlockMethodChangedListener
    public void onUnlockMethodStateChanged() {
        this.mLockIcon.update();
        updateCameraVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inflateCameraPreview() {
        View previewBefore = this.mCameraPreview;
        boolean visibleBefore = false;
        if (previewBefore != null) {
            this.mPreviewContainer.removeView(previewBefore);
            visibleBefore = previewBefore.getVisibility() == 0;
        }
        this.mCameraPreview = this.mPreviewInflater.inflatePreview(getCameraIntent());
        if (this.mCameraPreview != null) {
            this.mPreviewContainer.addView(this.mCameraPreview);
            this.mCameraPreview.setVisibility(visibleBefore ? 0 : 4);
        }
        if (this.mAffordanceHelper != null) {
            this.mAffordanceHelper.updatePreviews();
        }
    }

    private void updateLeftPreview() {
        View previewBefore = this.mLeftPreview;
        if (previewBefore != null) {
            this.mPreviewContainer.removeView(previewBefore);
        }
        if (this.mLeftIsVoiceAssist) {
            this.mLeftPreview = this.mPreviewInflater.inflatePreviewFromService(this.mAssistManager.getVoiceInteractorComponentName());
        } else {
            this.mLeftPreview = this.mPreviewInflater.inflatePreview(this.mLeftButton.getIntent());
        }
        if (this.mLeftPreview != null) {
            this.mPreviewContainer.addView(this.mLeftPreview);
            this.mLeftPreview.setVisibility(4);
        }
        if (this.mAffordanceHelper != null) {
            this.mAffordanceHelper.updatePreviews();
        }
    }

    public void startFinishDozeAnimation() {
        long delay = 0;
        if (this.mLeftAffordanceView.getVisibility() == 0) {
            startFinishDozeAnimationElement(this.mLeftAffordanceView, 0L);
            delay = 0 + 48;
        }
        startFinishDozeAnimationElement(this.mLockIcon, delay);
        long delay2 = delay + 48;
        if (this.mRightAffordanceView.getVisibility() == 0) {
            startFinishDozeAnimationElement(this.mRightAffordanceView, delay2);
        }
    }

    private void startFinishDozeAnimationElement(View element, long delay) {
        element.setAlpha(0.0f);
        element.setTranslationY(element.getHeight() / 2);
        element.animate().alpha(1.0f).translationY(0.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setStartDelay(delay).setDuration(250L);
    }

    public void setKeyguardIndicationController(KeyguardIndicationController keyguardIndicationController) {
        this.mIndicationController = keyguardIndicationController;
    }

    public void updateLeftAffordance() {
        updateLeftAffordanceIcon();
        updateLeftPreview();
    }

    public void onKeyguardShowingChanged() {
        updateLeftAffordance();
        inflateCameraPreview();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRightButton(IntentButtonProvider.IntentButton button) {
        this.mRightButton = button;
        updateRightAffordanceIcon();
        updateCameraVisibility();
        inflateCameraPreview();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLeftButton(IntentButtonProvider.IntentButton button) {
        this.mLeftButton = button;
        if (!(this.mLeftButton instanceof DefaultLeftButton)) {
            this.mLeftIsVoiceAssist = false;
        }
        updateLeftAffordance();
    }

    public void setDozing(boolean dozing, boolean animate) {
        this.mDozing = dozing;
        updateCameraVisibility();
        updateLeftAffordanceIcon();
        if (dozing) {
            this.mLockIcon.setVisibility(4);
            this.mOverlayContainer.setVisibility(4);
            return;
        }
        this.mLockIcon.setVisibility(0);
        this.mOverlayContainer.setVisibility(0);
        if (animate) {
            startFinishDozeAnimation();
        }
    }

    public void dozeTimeTick() {
        if (this.mDarkAmount == 1.0f) {
            int dozeTranslation = this.mIndicationBottomMargin - this.mIndicationBottomMarginAmbient;
            this.mIndicationArea.setTranslationY(dozeTranslation + (((float) Math.random()) * 5.0f));
        }
    }

    public void setBurnInXOffset(int burnInXOffset) {
        if (this.mBurnInXOffset == burnInXOffset) {
            return;
        }
        this.mBurnInXOffset = burnInXOffset;
        this.mIndicationArea.setTranslationX(burnInXOffset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DefaultLeftButton implements IntentButtonProvider.IntentButton {
        private IntentButtonProvider.IntentButton.IconState mIconState;

        private DefaultLeftButton() {
            this.mIconState = new IntentButtonProvider.IntentButton.IconState();
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public IntentButtonProvider.IntentButton.IconState getIcon() {
            KeyguardBottomAreaView.this.mLeftIsVoiceAssist = KeyguardBottomAreaView.this.canLaunchVoiceAssist();
            boolean showAffordance = KeyguardBottomAreaView.this.getResources().getBoolean(R.bool.config_keyguardShowLeftAffordance);
            boolean z = false;
            if (KeyguardBottomAreaView.this.mLeftIsVoiceAssist) {
                IntentButtonProvider.IntentButton.IconState iconState = this.mIconState;
                if (KeyguardBottomAreaView.this.mUserSetupComplete && showAffordance) {
                    z = true;
                }
                iconState.isVisible = z;
                if (KeyguardBottomAreaView.this.mLeftAssistIcon == null) {
                    this.mIconState.drawable = KeyguardBottomAreaView.this.mContext.getDrawable(R.drawable.ic_mic_26dp);
                } else {
                    this.mIconState.drawable = KeyguardBottomAreaView.this.mLeftAssistIcon;
                }
                this.mIconState.contentDescription = KeyguardBottomAreaView.this.mContext.getString(R.string.accessibility_voice_assist_button);
            } else {
                IntentButtonProvider.IntentButton.IconState iconState2 = this.mIconState;
                if (KeyguardBottomAreaView.this.mUserSetupComplete && showAffordance && KeyguardBottomAreaView.this.isPhoneVisible()) {
                    z = true;
                }
                iconState2.isVisible = z;
                this.mIconState.drawable = KeyguardBottomAreaView.this.mContext.getDrawable(R.drawable.ic_phone_24dp);
                this.mIconState.contentDescription = KeyguardBottomAreaView.this.mContext.getString(R.string.accessibility_phone_button);
            }
            return this.mIconState;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public Intent getIntent() {
            return KeyguardBottomAreaView.PHONE_INTENT;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DefaultRightButton implements IntentButtonProvider.IntentButton {
        private IntentButtonProvider.IntentButton.IconState mIconState;

        private DefaultRightButton() {
            this.mIconState = new IntentButtonProvider.IntentButton.IconState();
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public IntentButtonProvider.IntentButton.IconState getIcon() {
            ResolveInfo resolved = KeyguardBottomAreaView.this.resolveCameraIntent();
            boolean z = false;
            boolean isCameraDisabled = (KeyguardBottomAreaView.this.mStatusBar == null || KeyguardBottomAreaView.this.mStatusBar.isCameraAllowedByAdmin()) ? false : true;
            IntentButtonProvider.IntentButton.IconState iconState = this.mIconState;
            if (!isCameraDisabled && resolved != null && KeyguardBottomAreaView.this.getResources().getBoolean(R.bool.config_keyguardShowCameraAffordance) && KeyguardBottomAreaView.this.mUserSetupComplete) {
                z = true;
            }
            iconState.isVisible = z;
            this.mIconState.drawable = KeyguardBottomAreaView.this.mContext.getDrawable(R.drawable.ic_camera_alt_24dp);
            this.mIconState.contentDescription = KeyguardBottomAreaView.this.mContext.getString(R.string.accessibility_camera_button);
            return this.mIconState;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public Intent getIntent() {
            KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(KeyguardBottomAreaView.this.mContext);
            boolean canSkipBouncer = updateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser());
            boolean secure = KeyguardBottomAreaView.this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser());
            return (!secure || canSkipBouncer) ? KeyguardBottomAreaView.INSECURE_CAMERA_INTENT : KeyguardBottomAreaView.SECURE_CAMERA_INTENT;
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        int bottom = insets.getDisplayCutout() != null ? insets.getDisplayCutout().getSafeInsetBottom() : 0;
        if (isPaddingRelative()) {
            setPaddingRelative(getPaddingStart(), getPaddingTop(), getPaddingEnd(), bottom);
        } else {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), bottom);
        }
        return insets;
    }

    @Override // android.view.View
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        this.mEmergencyButtonExt.setEmergencyButtonVisibility(this.mEmergencyButton, alpha);
    }
}
