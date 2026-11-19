package com.android.systemui.statusbar.phone;

import android.Configures.HConfig;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.IApplicationThread;
import android.app.IWallpaperManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.app.StatusBarManager;
import android.app.TaskStackBuilder;
import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.carsource.McuManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.RemoteAnimationAdapter;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.DateTimeView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.ActivityStarterDelegate;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.DemoMode;
import com.android.systemui.Dependency;
import com.android.systemui.EventLogTags;
import com.android.systemui.Interpolators;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.RecentsComponent;
import com.android.systemui.SystemUI;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.classifier.FalsingLog;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.fragments.ExtensionFragmentListener;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.QuickStatusBarHeader;
import com.android.systemui.qs.car.CarQSFragment;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.AppTransitionFinishedEvent;
import com.android.systemui.recents.events.activity.UndockingTaskEvent;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.shared.system.WindowManagerWrapper;
import com.android.systemui.stackdivider.Divider;
import com.android.systemui.stackdivider.WindowManagerProxy;
import com.android.systemui.statusbar.ActivatableNotificationView;
import com.android.systemui.statusbar.AppOpsListener;
import com.android.systemui.statusbar.BackDropView;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.DZ23CanStatusBarController;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.FooterView;
import com.android.systemui.statusbar.GestureRecorder;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.NotificationContentView;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationEntryManager;
import com.android.systemui.statusbar.NotificationGutsManager;
import com.android.systemui.statusbar.NotificationInfo;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLogger;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.ScrimView;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.ActivityLaunchAnimator;
import com.android.systemui.statusbar.notification.VisualStabilityManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpUtil;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import com.android.systemui.statusbar.policy.KeyguardMonitorImpl;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcher;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.PreviewInflater;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.TopWindow;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import com.android.systemui.util.Utils;
import com.android.systemui.volume.VolumeComponent;
import com.hcn.autocan.aidl.AirCtrlManager;
import com.hcn.autocan.aidl.IAirCallBack;
import com.hcn.autocan.hiworld.gm.air.AIR;
import com.mediatek.systemui.ext.IStatusBarPlmnPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import com.mediatek.systemui.statusbar.util.SIMHelper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class StatusBar extends SystemUI implements ColorExtractor.OnColorsChangedListener, DemoMode, ActivityStarter, CommandQueue.Callbacks, DragDownHelper.DragDownCallback, NotificationPresenter, UnlockMethodCache.OnUnlockMethodChangedListener, ConfigurationController.ConfigurationListener, OnHeadsUpChangedListener, ZenModeController.Callback {
    private static final boolean ONLY_CORE_APPS;
    IAirCallBack callBack;
    private AboveShelfObserver mAboveShelfObserver;
    protected AccessibilityManager mAccessibilityManager;
    protected ActivityLaunchAnimator mActivityLaunchAnimator;
    private View mAmbientIndicationContainer;
    protected AppOpsListener mAppOpsListener;
    protected AssistManager mAssistManager;
    private boolean mAutohideSuspended;
    protected BackDropView mBackdrop;
    protected ImageView mBackdropBack;
    protected ImageView mBackdropFront;
    protected IStatusBarService mBarService;
    private BatteryController mBatteryController;
    private TopWindow mBatteryVoltageView;
    protected boolean mBouncerShowing;
    private boolean mBouncerWasShowingWhenHidden;
    private BrightnessMirrorController mBrightnessMirrorController;
    private boolean mBrightnessMirrorVisible;
    private long[] mCameraLaunchGestureVibePattern;
    protected boolean mClearAllEnabled;
    private SysuiColorExtractor mColorExtractor;
    protected CommandQueue mCommandQueue;
    private DZ23CanStatusBarController mDZ23Controller;
    private boolean mDemoMode;
    private boolean mDemoModeAllowed;
    protected boolean mDeviceInteractive;
    protected DevicePolicyManager mDevicePolicyManager;
    protected Display mDisplay;
    protected DozeScrimController mDozeScrimController;
    protected boolean mDozing;
    private boolean mDozingRequested;
    private ExpandableNotificationRow mDraggedDownRow;
    protected EmptyShadeView mEmptyShadeView;
    protected NotificationEntryManager mEntryManager;
    private boolean mExpandedVisible;
    protected FalsingManager mFalsingManager;
    protected FingerprintUnlockController mFingerprintUnlockController;
    protected FooterView mFooterView;
    private PowerManager.WakeLock mGestureWakeLock;
    protected NotificationGroupManager mGroupManager;
    private NotificationGutsManager mGutsManager;
    private HeadsUpAppearanceController mHeadsUpAppearanceController;
    protected HeadsUpManagerPhone mHeadsUpManager;
    private boolean mHideIconsForBouncer;
    protected StatusBarIconController mIconController;
    private PhoneStatusBarPolicy mIconPolicy;
    private int mInteractingWindows;
    private boolean mIsKeyguard;
    private boolean mIsOccluded;
    protected boolean mKeyguardFadingAway;
    protected long mKeyguardFadingAwayDelay;
    protected long mKeyguardFadingAwayDuration;
    KeyguardIndicationController mKeyguardIndicationController;
    protected KeyguardManager mKeyguardManager;
    private boolean mKeyguardRequested;
    private KeyguardStatusBarView mKeyguardStatusBar;
    private KeyguardUserSwitcher mKeyguardUserSwitcher;
    protected KeyguardViewMediator mKeyguardViewMediator;
    private ViewMediatorCallback mKeyguardViewMediatorCallback;
    private int mLastCameraLaunchSource;
    private int mLastLoggedStateFingerprint;
    private boolean mLaunchCameraOnFinishedGoingToSleep;
    private boolean mLaunchCameraOnScreenTurningOn;
    private Runnable mLaunchTransitionEndRunnable;
    protected boolean mLaunchTransitionFadingAway;
    private boolean mLeaveOpenOnKeyguardHide;
    private LightBarController mLightBarController;
    private LockPatternUtils mLockPatternUtils;
    protected NotificationLockscreenUserManager mLockscreenUserManager;
    protected LockscreenWallpaper mLockscreenWallpaper;
    private int mMaxAllowedKeyguardNotifications;
    private int mMaxKeyguardNotifications;
    private NotificationMediaManager mMediaManager;
    private NavigationBarFragment mNavigationBar;
    private View mNavigationBarView;
    private NetworkController mNetworkController;
    private boolean mNoAnimationOnNextBarModeChange;
    protected NotificationIconAreaController mNotificationIconAreaController;
    protected NotificationListener mNotificationListener;
    protected NotificationLogger mNotificationLogger;
    protected NotificationPanelView mNotificationPanel;
    protected NotificationShelf mNotificationShelf;
    TopWindow mOutTempView;
    private IOverlayManager mOverlayManager;
    protected boolean mPanelExpanded;
    private View mPendingRemoteInputView;
    private View mPendingWorkRemoteInputView;
    protected PowerManager mPowerManager;
    private QSPanel mQSPanel;
    protected RecentsComponent mRecents;
    private boolean mReinflateNotificationsOnUserSwitched;
    protected NotificationRemoteInputManager mRemoteInputManager;
    private View mReportRejectedTouch;
    private ScreenLifecycle mScreenLifecycle;
    private ScreenPinningNotify mScreenPinningNotify;
    private ScreenPinningRequest mScreenPinningRequest;
    protected ScrimController mScrimController;
    protected boolean mScrimSrcModeEnabled;
    private StatusBarSignalPolicy mSignalPolicy;
    protected NotificationStackScrollLayout mStackScroller;
    protected int mState;
    protected StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    private int mStatusBarMode;
    private LogMaker mStatusBarStateLog;
    protected PhoneStatusBarView mStatusBarView;
    protected StatusBarWindowView mStatusBarWindow;
    private boolean mStatusBarWindowHidden;
    protected StatusBarWindowManager mStatusBarWindowManager;
    private boolean mTopHidesStatusBar;
    protected UnlockMethodCache mUnlockMethodCache;
    protected UserSwitcherController mUserSwitcherController;
    private boolean mVibrateOnOpening;
    private Vibrator mVibrator;
    private VibratorHelper mVibratorHelper;
    protected NotificationViewHierarchyManager mViewHierarchyManager;
    protected boolean mVisible;
    private boolean mVisibleToUser;
    protected VisualStabilityManager mVisualStabilityManager;
    private VolumeComponent mVolumeComponent;
    protected boolean mVrMode;
    private boolean mWakeUpComingFromTouch;
    private PointF mWakeUpTouchLocation;

    @VisibleForTesting
    WakefulnessLifecycle mWakefulnessLifecycle;
    private boolean mWereIconsJustHidden;
    protected WindowManager mWindowManager;
    protected IWindowManager mWindowManagerService;
    private ZenModeController mZenController;
    public static final boolean ENABLE_CHILD_NOTIFICATIONS = SystemProperties.getBoolean("debug.child_notifs", true);
    private static int VOICE_ASSISTANT = SystemProperties.getInt("persist.sys.voice_assis", 0);
    public static final boolean DEBUG = FeatureOptions.LOG_ENABLE;
    public static final boolean CHATTY = DEBUG;
    private static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    private int mChangedSysBar = 0;
    private boolean mAirInfoShowEnable = true;
    private OpSystemUICustomizationFactoryBase mSystemUIFactoryBase = null;
    private int mNaturalBarHeight = -1;
    private final Point mCurrentDisplaySize = new Point();
    private int mStatusBarWindowState = 0;
    private DozeServiceHost mDozeServiceHost = new DozeServiceHost();
    private final Object mQueueLock = new Object();
    private RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler = (RemoteInputQuickSettingsDisabler) Dependency.get(RemoteInputQuickSettingsDisabler.class);
    private final int[] mAbsPos = new int[2];
    private final ArrayList<Runnable> mPostCollapseRunnables = new ArrayList<>();
    private int mDisabled1 = 0;
    private int mDisabled2 = 0;
    private int mSystemUiVisibility = 0;
    private final Rect mLastFullscreenStackBounds = new Rect();
    private final Rect mLastDockedStackBounds = new Rect();
    private final Rect mTmpRect = new Rect();
    private int mLastDispatchedSystemUiVisibility = -1;
    private final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    private final GestureRecorder mGestureRec = null;
    private final MetricsLogger mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);

    @VisibleForTesting
    protected boolean mUserSetup = false;
    private final DeviceProvisionedController.DeviceProvisionedListener mUserSetupObserver = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.phone.StatusBar.1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSetupChanged() {
            boolean userSetup = StatusBar.this.mDeviceProvisionedController.isUserSetup(StatusBar.this.mDeviceProvisionedController.getCurrentUser());
            if (userSetup != StatusBar.this.mUserSetup) {
                StatusBar.this.mUserSetup = userSetup;
                if (!StatusBar.this.mUserSetup && StatusBar.this.mStatusBarView != null) {
                    StatusBar.this.animateCollapseQuickSettings();
                }
                if (StatusBar.this.mNotificationPanel != null) {
                    StatusBar.this.mNotificationPanel.setUserSetupComplete(StatusBar.this.mUserSetup);
                }
                StatusBar.this.updateQsExpansionEnabled();
            }
        }
    };
    protected final H mHandler = createHandler();
    private final UiOffloadThread mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
    private final Runnable mAutohide = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$Xuhkv64bZHfI7FOWJTQgAefazRk
        @Override // java.lang.Runnable
        public final void run() {
            StatusBar.lambda$new$0(this.f$0);
        }
    };
    protected final PorterDuffXfermode mSrcXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    protected final PorterDuffXfermode mSrcOverXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    private BroadcastReceiver mWallpaperChangedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.StatusBar.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
            if (wallpaperManager == null) {
                Log.w("StatusBar", "WallpaperManager not available");
                return;
            }
            WallpaperInfo info = wallpaperManager.getWallpaperInfo();
            boolean supportsAmbientMode = info != null && info.getSupportsAmbientMode();
            StatusBar.this.mStatusBarWindowManager.setWallpaperSupportsAmbientMode(supportsAmbientMode);
            StatusBar.this.mScrimController.setWallpaperSupportsAmbientMode(supportsAmbientMode);
        }
    };
    private final int[] mTmpInt2 = new int[2];
    private final ScrimController.Callback mUnlockScrimCallback = new ScrimController.Callback() { // from class: com.android.systemui.statusbar.phone.StatusBar.3
        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public void onFinished() {
            if (StatusBar.this.mStatusBarKeyguardViewManager == null) {
                Log.w("StatusBar", "Tried to notify keyguard visibility when mStatusBarKeyguardViewManager was null");
            } else if (StatusBar.this.mKeyguardFadingAway) {
                StatusBar.this.mStatusBarKeyguardViewManager.onKeyguardFadedAway();
            }
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public void onCancelled() {
            onFinished();
        }
    };
    private KeyguardMonitorImpl mKeyguardMonitor = (KeyguardMonitorImpl) Dependency.get(KeyguardMonitor.class);
    private final LockscreenGestureLogger mLockscreenGestureLogger = new LockscreenGestureLogger();
    private final View.OnClickListener mGoToLockedShadeListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$nk2TlfrOg2jlsjvFTJOkIoJCn4k
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            StatusBar.lambda$new$1(this.f$0, view);
        }
    };
    private final KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.StatusBar.4
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onDreamingStateChanged(boolean dreaming) {
            if (dreaming) {
                StatusBar.this.maybeEscalateHeadsUp();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStrongAuthStateChanged(int userId) {
            super.onStrongAuthStateChanged(userId);
            StatusBar.this.mEntryManager.updateNotifications();
        }
    };
    private boolean isBtCalling = false;
    private final View.OnClickListener mSplitClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.StatusBar.7
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Log.i("StatusBar", "SplitButton onClick");
            StatusBar.this.toggleSplitScreenMode(271, 286);
        }
    };
    private final View.OnClickListener mRotationClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.StatusBar.8
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Configuration currentConfig = StatusBar.this.mContext.getResources().getConfiguration();
            boolean portraitScreen = currentConfig.orientation == 1;
            Log.d("StatusBar", "RotationButton/onClick, Portrait Screen: " + portraitScreen);
            McuManager.getsInstance().injectKeyEventTimeout(334, 50);
        }
    };
    protected final Runnable mHideBackdropFront = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBar.10
        @Override // java.lang.Runnable
        public void run() {
            StatusBar.this.mBackdropFront.setVisibility(4);
            StatusBar.this.mBackdropFront.animate().cancel();
            StatusBar.this.mBackdropFront.setImageDrawable(null);
        }
    };
    private final Runnable mAnimateCollapsePanels = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$hcoUGmHpwgtk12ln4V8HNBe6RFA
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.animateCollapsePanels();
        }
    };
    private final Runnable mCheckBarModes = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KBnY14rlKZ6x8gvk_goBuFrr5eE
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.checkBarModes();
        }
    };
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.StatusBar.13
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (StatusBar.DEBUG) {
                Log.v("StatusBar", "onReceive: " + intent);
            }
            String action = intent.getAction();
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action)) {
                KeyboardShortcuts.dismiss();
                if (StatusBar.this.mRemoteInputManager.getController() != null) {
                    StatusBar.this.mRemoteInputManager.getController().closeRemoteInputs();
                }
                if (StatusBar.this.mLockscreenUserManager.isCurrentProfile(getSendingUserId())) {
                    int flags = 0;
                    String reason = intent.getStringExtra("reason");
                    if (reason != null && reason.equals("recentapps")) {
                        flags = 0 | 2;
                    }
                    StatusBar.this.animateCollapsePanels(flags);
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                StatusBar.this.finishBarAnimations();
                StatusBar.this.resetUserExpandedStates();
                return;
            }
            if ("android.app.action.SHOW_DEVICE_MONITORING_DIALOG".equals(action)) {
                if (StatusBar.this.mQSPanel != null) {
                    StatusBar.this.mQSPanel.showDeviceMonitoringDialog();
                    return;
                }
                Log.w("StatusBar", "mBroadcastReceiver fail not qspanel, action: " + action);
                return;
            }
            if ("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED".equals(action)) {
                BluetoothHeadsetClientCall call = intent.getParcelableExtra("android.bluetooth.headsetclient.extra.CALL");
                Log.i("StatusBar", "Received action_call_changed, call state: " + call.getState());
                switch (call.getState()) {
                    case 0:
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                        StatusBar.this.isBtCalling = true;
                        break;
                    case 3:
                    default:
                        StatusBar.this.isBtCalling = false;
                        break;
                }
            }
            if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED") || action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                StatusBar.this.isBtCalling = false;
            }
        }
    };
    private final BroadcastReceiver mDemoReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.StatusBar.14
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (StatusBar.DEBUG) {
                Log.v("StatusBar", "onReceive: " + intent);
            }
            String action = intent.getAction();
            if ("com.android.systemui.demo".equals(action)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    String command = bundle.getString("command", "").trim().toLowerCase();
                    if (command.length() > 0) {
                        try {
                            StatusBar.this.dispatchDemoCommand(command, bundle);
                            return;
                        } catch (Throwable t) {
                            Log.w("StatusBar", "Error running demo command, intent=" + intent, t);
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            "fake_artwork".equals(action);
        }
    };
    private final ContentObserver mOutTempSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.statusbar.phone.StatusBar.15
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            if (StatusBar.this.mOutTempView != null) {
                boolean mShow = Settings.System.getInt(StatusBar.this.mContext.getContentResolver(), "outdoor_temp_show", 1) > 0;
                StatusBar.this.mOutTempView.setVisibility(mShow ? 0 : 8);
            }
        }
    };
    private final ContentObserver mBatteryVoltageSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.statusbar.phone.StatusBar.16
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            if (StatusBar.this.mBatteryVoltageView != null) {
                boolean mShow = Settings.System.getInt(StatusBar.this.mContext.getContentResolver(), "voltage_value_display", 0) > 0;
                StatusBar.this.mBatteryVoltageView.setVisibility(mShow ? 0 : 8);
            }
        }
    };
    private final ContentObserver mChangeSysBarSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.statusbar.phone.StatusBar.17
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            int curVal = Settings.System.getInt(StatusBar.this.mContext.getContentResolver(), "key_change_sys_bar", 0);
            if (curVal != StatusBar.this.mChangedSysBar) {
                FragmentHostManager.get(StatusBar.this.mStatusBarWindow).getFragmentManager().beginTransaction().replace(R.id.status_bar_container, new CollapsedStatusBarFragment(), "CollapsedStatusBarFragment").commit();
                if ((curVal == 1 || StatusBar.this.mChangedSysBar == 1) && StatusBar.this.mNotificationPanel.getQS() != null && StatusBar.this.mNotificationPanel.getQS().getHeader() != null) {
                    try {
                        ((QuickStatusBarHeader) StatusBar.this.mNotificationPanel.getQS().getHeader()).resetHeaderBar();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("StatusBar", "Change Sys Bar :resetHeaderBar error");
                    }
                }
                StatusBar.this.mChangedSysBar = curVal;
            }
        }
    };
    private final ContentObserver mAddButtonsSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.statusbar.phone.StatusBar.18
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            int curVal = Settings.System.getInt(StatusBar.this.mContext.getContentResolver(), "key_add_buttons", 0);
            StatusBar.this.setAddButtons(curVal);
        }
    };
    private final ContentObserver mAirInfoSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.statusbar.phone.StatusBar.19
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            if (StatusBar.this.mNavigationBar != null) {
                StatusBar.this.mAirInfoShowEnable = Settings.System.getInt(StatusBar.this.mContext.getContentResolver(), "air_navigationbar_data", 1) > 0;
                StatusBar.this.mNavigationBar.showAirData(StatusBar.this.mAirInfoShowEnable);
            }
        }
    };
    final Runnable mStartTracing = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBar.20
        @Override // java.lang.Runnable
        public void run() {
            StatusBar.this.vibrate();
            SystemClock.sleep(250L);
            Log.d("StatusBar", "startTracing");
            Debug.startMethodTracing("/data/statusbar-traces/trace");
            StatusBar.this.mHandler.postDelayed(StatusBar.this.mStopTracing, 10000L);
        }
    };
    final Runnable mStopTracing = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$MsdyLUwOwzVqr4M_rJXfH7Y5uf4
        @Override // java.lang.Runnable
        public final void run() {
            StatusBar.lambda$new$30(this.f$0);
        }
    };
    final WakefulnessLifecycle.Observer mWakefulnessObserver = new AnonymousClass22();
    final ScreenLifecycle.Observer mScreenObserver = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.StatusBar.23
        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public void onScreenTurningOn() {
            StatusBar.this.mFalsingManager.onScreenTurningOn();
            StatusBar.this.mNotificationPanel.onScreenTurningOn();
            if (StatusBar.this.mLaunchCameraOnScreenTurningOn) {
                StatusBar.this.mNotificationPanel.launchCamera(false, StatusBar.this.mLastCameraLaunchSource);
                StatusBar.this.mLaunchCameraOnScreenTurningOn = false;
            }
            StatusBar.this.updateScrimController();
        }

        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public void onScreenTurnedOn() {
            StatusBar.this.mScrimController.onScreenTurnedOn();
        }

        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public void onScreenTurnedOff() {
            StatusBar.this.mFalsingManager.onScreenOff();
            StatusBar.this.mScrimController.onScreenTurnedOff();
            if (!StatusBar.this.isPulsing()) {
                StatusBar.this.updateIsKeyguard();
            }
        }
    };
    private DeviceProvisionedController mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    private final IVrStateCallbacks mVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.statusbar.phone.StatusBar.24
        public void onVrStateChanged(boolean enabled) {
            StatusBar.this.mVrMode = enabled;
        }
    };
    private final BroadcastReceiver mBannerActionBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.StatusBar.25
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.statusbar.banner_action_cancel".equals(action) || "com.android.systemui.statusbar.banner_action_setup".equals(action)) {
                NotificationManager noMan = (NotificationManager) StatusBar.this.mContext.getSystemService("notification");
                noMan.cancel(5);
                Settings.Secure.putInt(StatusBar.this.mContext.getContentResolver(), "show_note_about_notification_hiding", 0);
                if ("com.android.systemui.statusbar.banner_action_setup".equals(action)) {
                    StatusBar.this.animateCollapsePanels(2, true);
                    StatusBar.this.mContext.startActivity(new Intent("android.settings.ACTION_APP_NOTIFICATION_REDACTION").addFlags(268435456));
                    return;
                }
                return;
            }
            if ("com.hcn.voicesystem.action.StartVoice".equals(action)) {
                if (StatusBar.VOICE_ASSISTANT == 2) {
                    StatusBar.this.startGoogleVoice();
                    return;
                }
                return;
            }
            if ("ACTION_AIR_NAVIGATIONBAR_DATA".equals(action)) {
                Log.d("StatusBar", "ACTION_AIR_NAVIGATIONBAR_DATA");
                Boolean show = Boolean.valueOf(intent.getBooleanExtra("SHOW", false));
                if (StatusBar.this.mNavigationBar != null) {
                    StatusBar.this.mNavigationBar.showAirData(show.booleanValue());
                    return;
                }
                return;
            }
            if ("hcn.air.outdoor_temp".equals(action)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    String outdoorTemp = bundle.getString("hcn.air.extra.outdoor_temp");
                    TopWindow outdoorTemptv = (TopWindow) StatusBar.this.mStatusBarView.findViewById(R.id.outdoortemp);
                    if (outdoorTemptv != null) {
                        outdoorTemptv.setText(outdoorTemp);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("ACTION_OUTDOOR_TEMP_DATA".equals(action)) {
                Boolean showOutTemp = Boolean.valueOf(intent.getBooleanExtra("SHOW_OUTDOOR_TEMP", false));
                TopWindow outdoorTemptv2 = (TopWindow) StatusBar.this.mStatusBarView.findViewById(R.id.outdoortemp);
                if (outdoorTemptv2 != null) {
                    if (showOutTemp.booleanValue()) {
                        outdoorTemptv2.setVisibility(0);
                    } else {
                        outdoorTemptv2.setVisibility(8);
                    }
                }
            }
        }
    };
    private final Runnable mAutoDim = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$Vx_dWXqtM3JgO60ZHzHJ0IXI9ek
        @Override // java.lang.Runnable
        public final void run() {
            StatusBar.lambda$new$51(this.f$0);
        }
    };
    private final NotificationInfo.CheckSaveListener mCheckSaveListener = new NotificationInfo.CheckSaveListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$tQM7LKA7LzmLIRLiqNo0MHNSmpc
        @Override // com.android.systemui.statusbar.NotificationInfo.CheckSaveListener
        public final void checkSave(Runnable runnable, StatusBarNotification statusBarNotification) {
            StatusBar.lambda$new$53(this.f$0, runnable, statusBarNotification);
        }
    };
    private IStatusBarPlmnPlugin mStatusBarPlmnPlugin = null;
    private View mCustomizeCarrierLabel = null;
    private View.OnClickListener mRecentsClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.StatusBar.26
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            StatusBar.this.awakenDreams();
            StatusBar.this.mCommandQueue.toggleRecentApps();
        }
    };

    static {
        boolean onlyCoreApps = false;
        try {
            IPackageManager packageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            onlyCoreApps = packageManager.isOnlyCoreApps();
        } catch (RemoteException e) {
        }
        ONLY_CORE_APPS = onlyCoreApps;
    }

    public static /* synthetic */ void lambda$new$0(StatusBar statusBar) {
        int requested = statusBar.mSystemUiVisibility & (-201326593);
        if (statusBar.mSystemUiVisibility != requested) {
            statusBar.notifyUiVisibilityChanged(requested);
        }
    }

    public static /* synthetic */ void lambda$new$1(StatusBar statusBar, View v) {
        if (statusBar.mState == 1) {
            statusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), v);
            statusBar.goToLockedShade(null);
        }
    }

    @Override // com.android.systemui.SystemUI
    public void start() {
        this.mGroupManager = (NotificationGroupManager) Dependency.get(NotificationGroupManager.class);
        this.mVisualStabilityManager = (VisualStabilityManager) Dependency.get(VisualStabilityManager.class);
        this.mNotificationLogger = (NotificationLogger) Dependency.get(NotificationLogger.class);
        this.mRemoteInputManager = (NotificationRemoteInputManager) Dependency.get(NotificationRemoteInputManager.class);
        this.mNotificationListener = (NotificationListener) Dependency.get(NotificationListener.class);
        this.mGroupManager = (NotificationGroupManager) Dependency.get(NotificationGroupManager.class);
        this.mNetworkController = (NetworkController) Dependency.get(NetworkController.class);
        this.mUserSwitcherController = (UserSwitcherController) Dependency.get(UserSwitcherController.class);
        this.mScreenLifecycle = (ScreenLifecycle) Dependency.get(ScreenLifecycle.class);
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        this.mWakefulnessLifecycle = (WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class);
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mBatteryController = (BatteryController) Dependency.get(BatteryController.class);
        this.mAssistManager = (AssistManager) Dependency.get(AssistManager.class);
        this.mLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class);
        this.mGutsManager = (NotificationGutsManager) Dependency.get(NotificationGutsManager.class);
        this.mMediaManager = (NotificationMediaManager) Dependency.get(NotificationMediaManager.class);
        this.mEntryManager = (NotificationEntryManager) Dependency.get(NotificationEntryManager.class);
        this.mViewHierarchyManager = (NotificationViewHierarchyManager) Dependency.get(NotificationViewHierarchyManager.class);
        this.mAppOpsListener = (AppOpsListener) Dependency.get(AppOpsListener.class);
        this.mAppOpsListener.setUpWithPresenter(this, this.mEntryManager);
        this.mZenController = (ZenModeController) Dependency.get(ZenModeController.class);
        this.mKeyguardViewMediator = (KeyguardViewMediator) getComponent(KeyguardViewMediator.class);
        this.mColorExtractor = (SysuiColorExtractor) Dependency.get(SysuiColorExtractor.class);
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        this.mDisplay = this.mWindowManager.getDefaultDisplay();
        updateDisplaySize();
        Resources res = this.mContext.getResources();
        this.mVibrateOnOpening = this.mContext.getResources().getBoolean(R.bool.config_vibrateOnIconAnimation);
        this.mVibratorHelper = (VibratorHelper) Dependency.get(VibratorHelper.class);
        this.mScrimSrcModeEnabled = res.getBoolean(R.bool.config_status_bar_scrim_behind_use_src);
        this.mClearAllEnabled = res.getBoolean(R.bool.config_enableNotificationsClearAll);
        DateTimeView.setReceiverHandler((Handler) Dependency.get(Dependency.TIME_TICK_HANDLER));
        putComponent(StatusBar.class, this);
        this.mWindowManagerService = WindowManagerGlobal.getWindowManagerService();
        this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("outdoor_temp_show"), true, this.mOutTempSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("voltage_value_display"), true, this.mBatteryVoltageSettingsObserver, -1);
        this.mChangedSysBar = Settings.System.getInt(this.mContext.getContentResolver(), "key_change_sys_bar", 0);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("key_change_sys_bar"), true, this.mChangeSysBarSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("key_add_buttons"), true, this.mAddButtonsSettingsObserver, -1);
        this.mAirInfoShowEnable = Settings.System.getInt(this.mContext.getContentResolver(), "air_navigationbar_data", 1) > 0;
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("air_navigationbar_data"), true, this.mAirInfoSettingsObserver, -1);
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mRecents = (RecentsComponent) getComponent(Recents.class);
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mMediaManager.setUpWithPresenter(this, this.mEntryManager);
        this.mCommandQueue = (CommandQueue) getComponent(CommandQueue.class);
        this.mCommandQueue.addCallbacks(this);
        int[] switches = new int[9];
        ArrayList<IBinder> binders = new ArrayList<>();
        ArrayList<String> iconSlots = new ArrayList<>();
        ArrayList<StatusBarIcon> icons = new ArrayList<>();
        Rect fullscreenStackBounds = new Rect();
        Rect dockedStackBounds = new Rect();
        try {
            this.mBarService.registerStatusBar(this.mCommandQueue, iconSlots, icons, switches, binders, fullscreenStackBounds, dockedStackBounds);
        } catch (RemoteException e) {
        }
        createAndAddWindows();
        IntentFilter wallpaperChangedFilter = new IntentFilter("android.intent.action.WALLPAPER_CHANGED");
        this.mContext.registerReceiver(this.mWallpaperChangedReceiver, wallpaperChangedFilter);
        this.mWallpaperChangedReceiver.onReceive(this.mContext, null);
        this.mLockscreenUserManager.setUpWithPresenter(this, this.mEntryManager);
        this.mCommandQueue.disable(switches[0], switches[6], false);
        setSystemUiVisibility(switches[1], switches[7], switches[8], -1, fullscreenStackBounds, dockedStackBounds);
        topAppWindowChanged(switches[2] != 0);
        setImeWindowStatus(binders.get(0), switches[3], switches[4], switches[5] != 0);
        int N = iconSlots.size();
        for (int i = 0; i < N; i++) {
            this.mCommandQueue.setIcon(iconSlots.get(i), icons.get(i));
        }
        this.mNotificationListener.setUpWithPresenter(this, this.mEntryManager);
        if (DEBUG) {
            Log.d("StatusBar", String.format("init: icons=%d disabled=0x%08x lights=0x%08x menu=0x%08x imeButton=0x%08x", Integer.valueOf(icons.size()), Integer.valueOf(switches[0]), Integer.valueOf(switches[1]), Integer.valueOf(switches[2]), Integer.valueOf(switches[3])));
        }
        setHeadsUpUser(this.mLockscreenUserManager.getCurrentUserId());
        IntentFilter internalFilter = new IntentFilter();
        internalFilter.addAction("com.android.systemui.statusbar.banner_action_cancel");
        internalFilter.addAction("com.android.systemui.statusbar.banner_action_setup");
        this.mContext.registerReceiver(this.mBannerActionBroadcastReceiver, internalFilter, "com.android.systemui.permission.SELF", null);
        IntentFilter airFilter = new IntentFilter();
        airFilter.addAction("ACTION_AIR_INFO");
        this.mContext.registerReceiver(this.mBannerActionBroadcastReceiver, airFilter);
        IntentFilter outTempFilter = new IntentFilter();
        outTempFilter.addAction("hcn.air.outdoor_temp");
        this.mContext.registerReceiver(this.mBannerActionBroadcastReceiver, outTempFilter);
        IntentFilter autoFilter = new IntentFilter();
        autoFilter.addAction("com.hcn.voicesystem.action.StartVoice");
        this.mContext.registerReceiver(this.mBannerActionBroadcastReceiver, autoFilter);
        IVrManager vrManager = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        try {
            vrManager.registerListener(this.mVrStateCallbacks);
        } catch (RemoteException e2) {
            Slog.e("StatusBar", "Failed to register VR mode state listener: " + e2);
        }
        IWallpaperManager wallpaperManager = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        try {
            wallpaperManager.setInAmbientMode(false, false);
        } catch (RemoteException e3) {
        }
        this.mIconPolicy = new PhoneStatusBarPolicy(this.mContext, this.mIconController);
        this.mSignalPolicy = new StatusBarSignalPolicy(this.mContext, this.mIconController);
        this.mUnlockMethodCache = UnlockMethodCache.getInstance(this.mContext);
        this.mUnlockMethodCache.addListener(this);
        startKeyguard();
        KeyguardUpdateMonitor.getInstance(this.mContext).registerCallback(this.mUpdateCallback);
        putComponent(DozeHost.class, this.mDozeServiceHost);
        this.mScreenPinningRequest = new ScreenPinningRequest(this.mContext);
        this.mFalsingManager = FalsingManager.getInstance(this.mContext);
        ((ActivityStarterDelegate) Dependency.get(ActivityStarterDelegate.class)).setActivityStarterImpl(this);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        this.callBack = new IAirCallBack.Stub() { // from class: com.android.systemui.statusbar.phone.StatusBar.5
            @Override // com.hcn.autocan.aidl.IAirCallBack
            public void CallBackEvent(AIR airBean) {
                if (!StatusBar.this.mAirInfoShowEnable) {
                    return;
                }
                Message message = new Message();
                message.what = 1005;
                message.obj = airBean;
                StatusBar.this.mHandler.removeMessages(message.what);
                StatusBar.this.mHandler.sendMessage(message);
            }
        };
        this.mHandler.sendEmptyMessageDelayed(1006, 20000L);
    }

    protected void makeStatusBarView() {
        Context context = this.mContext;
        updateDisplaySize();
        updateResources();
        updateTheme();
        inflateStatusBarWindow(context);
        this.mStatusBarWindow.setService(this);
        this.mStatusBarWindow.setOnTouchListener(getStatusBarWindowTouchListener());
        this.mNotificationPanel = (NotificationPanelView) this.mStatusBarWindow.findViewById(R.id.notification_panel);
        this.mStackScroller = (NotificationStackScrollLayout) this.mStatusBarWindow.findViewById(R.id.notification_stack_scroller);
        this.mZenController.addCallback(this);
        this.mActivityLaunchAnimator = new ActivityLaunchAnimator(this.mStatusBarWindow, this, this.mNotificationPanel, this.mStackScroller);
        this.mGutsManager.setUpWithPresenter(this, this.mEntryManager, this.mStackScroller, this.mCheckSaveListener, new NotificationGutsManager.OnSettingsClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$EGLDayN0ppRMZI1envjIVfb7R_0
            @Override // com.android.systemui.statusbar.NotificationGutsManager.OnSettingsClickListener
            public final void onClick(String str) {
                this.f$0.mBarService.onNotificationSettingsViewed(str);
            }
        });
        this.mNotificationLogger.setUpWithEntryManager(this.mEntryManager, this.mStackScroller);
        this.mNotificationPanel.setStatusBar(this);
        this.mNotificationPanel.setGroupManager(this.mGroupManager);
        this.mAboveShelfObserver = new AboveShelfObserver(this.mStackScroller);
        this.mAboveShelfObserver.setListener((AboveShelfObserver.HasViewAboveShelfChangedListener) this.mStatusBarWindow.findViewById(R.id.notification_container_parent));
        this.mKeyguardStatusBar = (KeyguardStatusBarView) this.mStatusBarWindow.findViewById(R.id.keyguard_header);
        this.mNotificationIconAreaController = SystemUIFactory.getInstance().createNotificationIconAreaController(context, this);
        inflateShelf();
        this.mNotificationIconAreaController.setupShelf(this.mNotificationShelf);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mNotificationIconAreaController);
        FragmentHostManager.get(this.mStatusBarWindow).addTagListener("CollapsedStatusBarFragment", new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$TPJyILujZ88K3rKFmgzHGHpbtLo
            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewCreated(String str, Fragment fragment) {
                StatusBar.lambda$makeStatusBarView$3(this.f$0, str, fragment);
            }
        }).getFragmentManager().beginTransaction().replace(R.id.status_bar_container, new CollapsedStatusBarFragment(), "CollapsedStatusBarFragment").commit();
        this.mIconController = (StatusBarIconController) Dependency.get(StatusBarIconController.class);
        this.mHeadsUpManager = new HeadsUpManagerPhone(context, this.mStatusBarWindow, this.mGroupManager, this, this.mVisualStabilityManager);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this.mHeadsUpManager);
        this.mHeadsUpManager.addListener(this);
        this.mHeadsUpManager.addListener(this.mNotificationPanel);
        this.mHeadsUpManager.addListener(this.mGroupManager);
        this.mHeadsUpManager.addListener(this.mVisualStabilityManager);
        this.mNotificationPanel.setHeadsUpManager(this.mHeadsUpManager);
        this.mGroupManager.setHeadsUpManager(this.mHeadsUpManager);
        putComponent(HeadsUpManager.class, this.mHeadsUpManager);
        this.mEntryManager.setUpWithPresenter(this, this.mStackScroller, this, this.mHeadsUpManager);
        this.mViewHierarchyManager.setUpWithPresenter(this, this.mEntryManager, this.mStackScroller);
        try {
            boolean showNav = this.mWindowManagerService.hasNavigationBar();
            if (DEBUG) {
                Log.v("StatusBar", "hasNavigationBar=" + showNav);
            }
            if (showNav) {
                createNavigationBar();
            }
        } catch (RemoteException e) {
        }
        this.mScreenPinningNotify = new ScreenPinningNotify(this.mContext);
        this.mStackScroller.setLongPressListener(this.mEntryManager.getNotificationLongClicker());
        this.mStackScroller.setStatusBar(this);
        this.mStackScroller.setGroupManager(this.mGroupManager);
        this.mStackScroller.setHeadsUpManager(this.mHeadsUpManager);
        this.mGroupManager.setOnGroupChangeListener(this.mStackScroller);
        this.mVisualStabilityManager.setVisibilityLocationProvider(this.mStackScroller);
        inflateEmptyShadeView();
        inflateFooterView();
        this.mBackdrop = (BackDropView) this.mStatusBarWindow.findViewById(R.id.backdrop);
        this.mBackdropFront = (ImageView) this.mBackdrop.findViewById(R.id.backdrop_front);
        this.mBackdropBack = (ImageView) this.mBackdrop.findViewById(R.id.backdrop_back);
        this.mLockscreenWallpaper = new LockscreenWallpaper(this.mContext, this, this.mHandler);
        this.mKeyguardIndicationController = SystemUIFactory.getInstance().createKeyguardIndicationController(this.mContext, (ViewGroup) this.mStatusBarWindow.findViewById(R.id.keyguard_indication_area), this.mNotificationPanel.getLockIcon());
        this.mNotificationPanel.setKeyguardIndicationController(this.mKeyguardIndicationController);
        this.mAmbientIndicationContainer = this.mStatusBarWindow.findViewById(R.id.ambient_indication_container);
        setAreThereNotifications();
        this.mBatteryController.addCallback(new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.StatusBar.6
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public void onPowerSaveChanged(boolean isPowerSave) {
                StatusBar.this.mHandler.post(StatusBar.this.mCheckBarModes);
                if (StatusBar.this.mDozeServiceHost != null) {
                    StatusBar.this.mDozeServiceHost.firePowerSaveChanged(isPowerSave);
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
            }
        });
        this.mLightBarController = (LightBarController) Dependency.get(LightBarController.class);
        if (this.mNavigationBar != null) {
            this.mNavigationBar.setLightBarController(this.mLightBarController);
        }
        ScrimView scrimBehind = (ScrimView) this.mStatusBarWindow.findViewById(R.id.scrim_behind);
        ScrimView scrimInFront = (ScrimView) this.mStatusBarWindow.findViewById(R.id.scrim_in_front);
        this.mScrimController = SystemUIFactory.getInstance().createScrimController(scrimBehind, scrimInFront, this.mLockscreenWallpaper, new TriConsumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$CPTthYwWNrh8z0d-UzTtbuHfZd4
            public final void accept(Object obj, Object obj2, Object obj3) {
                this.f$0.mLightBarController.setScrimState((ScrimState) obj, ((Float) obj2).floatValue(), (ColorExtractor.GradientColors) obj3);
            }
        }, new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$aLenH7zcwVaIHX6ie2fIXARtA4g
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StatusBar.lambda$makeStatusBarView$5(this.f$0, (Integer) obj);
            }
        }, DozeParameters.getInstance(this.mContext), (AlarmManager) this.mContext.getSystemService(AlarmManager.class));
        if (this.mScrimSrcModeEnabled) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$EdGxnh5D2TWPQa657dfBiOwfR50
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$makeStatusBarView$6(this.f$0);
                }
            };
            this.mBackdrop.setOnVisibilityChangedRunnable(runnable);
            runnable.run();
        }
        this.mStackScroller.setScrimController(this.mScrimController);
        this.mDozeScrimController = new DozeScrimController(this.mScrimController, context, DozeParameters.getInstance(context));
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$EhQT7WQ0e8bJB68m5r-fEaTlNV0
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar statusBar = this.f$0;
                statusBar.mVolumeComponent = (VolumeComponent) statusBar.getComponent(VolumeComponent.class);
            }
        }, 4000L);
        SIMHelper.setContext(context);
        this.mStatusBarPlmnPlugin = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeStatusBarPlmn(context);
        if (supportCustomizeCarrierLabel()) {
            this.mCustomizeCarrierLabel = this.mStatusBarPlmnPlugin.customizeCarrierLabel(this.mNotificationPanel, null);
        }
        this.mNotificationPanel.setUserSetupComplete(this.mUserSetup);
        if (UserManager.get(this.mContext).isUserSwitcherEnabled()) {
            createUserSwitcher();
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$YbhfPYm0d8l98UyzSK9a_LHH8j4
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$makeStatusBarView$10(this.f$0);
            }
        }, 3000L);
        this.mReportRejectedTouch = this.mStatusBarWindow.findViewById(R.id.report_rejected_touch);
        if (this.mReportRejectedTouch != null) {
            updateReportRejectedTouchVisibility();
            this.mReportRejectedTouch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$A2Opy_HZVZpu93nAF-LI2cIq66w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StatusBar.lambda$makeStatusBarView$11(this.f$0, view);
                }
            });
        }
        PowerManager pm = (PowerManager) this.mContext.getSystemService("power");
        if (!pm.isScreenOn()) {
            this.mBroadcastReceiver.onReceive(this.mContext, new Intent("android.intent.action.SCREEN_OFF"));
        }
        this.mGestureWakeLock = pm.newWakeLock(10, "GestureWakeLock");
        this.mVibrator = (Vibrator) this.mContext.getSystemService(Vibrator.class);
        int[] pattern = this.mContext.getResources().getIntArray(R.array.config_cameraLaunchGestureVibePattern);
        this.mCameraLaunchGestureVibePattern = new long[pattern.length];
        for (int i = 0; i < pattern.length; i++) {
            this.mCameraLaunchGestureVibePattern[i] = pattern[i];
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED");
        filter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_EVENT");
        filter.addAction("android.app.action.SHOW_DEVICE_MONITORING_DIALOG");
        context.registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, filter, null, null);
        IntentFilter demoFilter = new IntentFilter();
        demoFilter.addAction("com.android.systemui.demo");
        context.registerReceiverAsUser(this.mDemoReceiver, UserHandle.ALL, demoFilter, "android.permission.DUMP", null);
        this.mDeviceProvisionedController.addCallback(this.mUserSetupObserver);
        this.mUserSetupObserver.onUserSetupChanged();
        ThreadedRenderer.overrideProperty("disableProfileBars", "true");
        ThreadedRenderer.overrideProperty("ambientRatio", String.valueOf(1.5f));
    }

    public static /* synthetic */ void lambda$makeStatusBarView$3(StatusBar statusBar, String tag, Fragment fragment) {
        CollapsedStatusBarFragment statusBarFragment = (CollapsedStatusBarFragment) fragment;
        statusBarFragment.initNotificationIconArea(statusBar.mNotificationIconAreaController);
        statusBar.mStatusBarView = (PhoneStatusBarView) fragment.getView();
        statusBar.mStatusBarView.setBar(statusBar);
        statusBar.mStatusBarView.setPanel(statusBar.mNotificationPanel);
        statusBar.mStatusBarView.setScrimController(statusBar.mScrimController);
        statusBar.mStatusBarView.setBouncerShowing(statusBar.mBouncerShowing);
        ImageView btn_recent = (ImageView) statusBar.getStatusBarViewRecentsButton();
        Log.e("StatusBar", "Utils.is8321():" + Utils.is8321());
        if (btn_recent != null) {
            if (Utils.is8321()) {
                btn_recent.setImageResource(R.drawable.ic_sysbar_recent_8321);
            }
            if (Utils.getSystemProperty("ro.fota.device", "").contains("AJ1501-OB")) {
                btn_recent.setVisibility(8);
            }
        }
        ImageView btn_back = (ImageView) statusBar.mStatusBarView.findViewById(R.id.back);
        if (btn_back != null) {
            if (Utils.is8321()) {
                btn_back.setImageResource(R.drawable.ic_sysbar_back_8321);
            }
            if (Utils.getSystemProperty("ro.fota.device", "").contains("AJ1501-OB")) {
                btn_back.setVisibility(8);
            }
        }
        ImageView btn_home = (ImageView) statusBar.mStatusBarView.findViewById(R.id.home);
        if (btn_home != null) {
            if (Utils.is8321()) {
                btn_home.setImageResource(R.drawable.ic_sysbar_home_8321);
            }
            if (Utils.getSystemProperty("ro.fota.device", "").contains("AJ1501-OB")) {
                btn_home.setVisibility(8);
            }
        }
        if (statusBar.mHeadsUpAppearanceController != null) {
            statusBar.mHeadsUpAppearanceController.destroy();
        }
        statusBar.mHeadsUpAppearanceController = new HeadsUpAppearanceController(statusBar.mNotificationIconAreaController, statusBar.mHeadsUpManager, statusBar.mStatusBarWindow);
        statusBar.setAreThereNotifications();
        statusBar.checkBarModes();
        statusBar.attachPlmnPlugin();
        statusBar.setRecentsButton();
        statusBar.setSplitButton();
        statusBar.setRotationButton();
        statusBar.tryHideStatusBarContents();
        statusBar.mOutTempView = (TopWindow) statusBar.mStatusBarView.findViewById(R.id.outdoortemp);
        boolean mTempShow = Settings.System.getInt(statusBar.mContext.getContentResolver(), "outdoor_temp_show", 1) > 0;
        if (statusBar.mOutTempView != null) {
            statusBar.mOutTempView.setVisibility(mTempShow ? 0 : 8);
        }
        statusBar.mBatteryVoltageView = (TopWindow) statusBar.mStatusBarView.findViewById(R.id.batteryvoltage);
        boolean mBatteryVoltageShow = Settings.System.getInt(statusBar.mContext.getContentResolver(), "voltage_value_display", 0) > 0;
        if (statusBar.mBatteryVoltageView != null) {
            statusBar.mBatteryVoltageView.setVisibility(mBatteryVoltageShow ? 0 : 8);
            statusBar.mHandler.sendEmptyMessage(1007);
        }
    }

    public static /* synthetic */ void lambda$makeStatusBarView$5(StatusBar statusBar, Integer scrimsVisible) {
        if (statusBar.mStatusBarWindowManager != null) {
            statusBar.mStatusBarWindowManager.setScrimsVisibility(scrimsVisible.intValue());
        }
    }

    public static /* synthetic */ void lambda$makeStatusBarView$6(StatusBar statusBar) {
        boolean asSrc = statusBar.mBackdrop.getVisibility() != 0;
        statusBar.mScrimController.setDrawBehindAsSrc(asSrc);
        statusBar.mStackScroller.setDrawBackgroundAsSrc(asSrc);
    }

    public static /* synthetic */ void lambda$makeStatusBarView$10(final StatusBar statusBar) {
        View container = statusBar.mStatusBarWindow.findViewById(R.id.qs_frame);
        if (container != null) {
            FragmentHostManager fragmentHostManager = FragmentHostManager.get(container);
            ExtensionFragmentListener.attachExtensonToFragment(container, QS.TAG, R.id.qs_frame, ((ExtensionController) Dependency.get(ExtensionController.class)).newExtension(QS.class).withPlugin(QS.class).withFeature("android.hardware.type.automotive", new Supplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$8CIXyGMqrW3leEv17MCPM15_8RU
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new CarQSFragment();
                }
            }).withDefault(new Supplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$5DhEg0l8l_X2iMYdu6k_V3Zid54
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new QSFragment();
                }
            }).build());
            final QSTileHost qsh = SystemUIFactory.getInstance().createQSTileHost(statusBar.mContext, statusBar, statusBar.mIconController);
            statusBar.mBrightnessMirrorController = new BrightnessMirrorController(statusBar.mStatusBarWindow, new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$wrzjn7ZULXGvCyKUjwxJtAbeFg0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StatusBar.lambda$makeStatusBarView$8(this.f$0, (Boolean) obj);
                }
            });
            fragmentHostManager.addTagListener(QS.TAG, new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$0k4u7R1a85SA07P2Ef6B3i3RtnU
                @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
                public final void onFragmentViewCreated(String str, Fragment fragment) {
                    StatusBar.lambda$makeStatusBarView$9(this.f$0, qsh, str, fragment);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$makeStatusBarView$8(StatusBar statusBar, Boolean visible) {
        statusBar.mBrightnessMirrorVisible = visible.booleanValue();
        statusBar.updateScrimController();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$makeStatusBarView$9(StatusBar statusBar, QSTileHost qsh, String tag, Fragment fragment) {
        QS qs = (QS) fragment;
        if (qs instanceof QSFragment) {
            ((QSFragment) qs).setHost(qsh);
            statusBar.mQSPanel = ((QSFragment) qs).getQsPanel();
            statusBar.mQSPanel.setBrightnessMirror(statusBar.mBrightnessMirrorController);
            statusBar.mKeyguardStatusBar.setQSPanel(statusBar.mQSPanel);
        }
    }

    public static /* synthetic */ void lambda$makeStatusBarView$11(StatusBar statusBar, View v) {
        Uri session = statusBar.mFalsingManager.reportRejectedTouch();
        if (session == null) {
            return;
        }
        StringWriter message = new StringWriter();
        message.write("Build info: ");
        message.write(SystemProperties.get("ro.build.description"));
        message.write("\nSerial number: ");
        message.write(SystemProperties.get("ro.serialno"));
        message.write("\n");
        PrintWriter falsingPw = new PrintWriter(message);
        FalsingLog.dump(falsingPw);
        falsingPw.flush();
        statusBar.startActivityDismissingKeyguard(Intent.createChooser(new Intent("android.intent.action.SEND").setType("*/*").putExtra("android.intent.extra.SUBJECT", "Rejected touch report").putExtra("android.intent.extra.STREAM", session).putExtra("android.intent.extra.TEXT", message.toString()), "Share rejected touch report").addFlags(268435456), true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startGoogleVoice() {
        if (this.mContext != null) {
            Log.d("StatusBar", "startGoogleVoice");
            try {
                Intent i = new Intent("android.intent.action.VOICE_ASSIST");
                i.setFlags(268435456);
                this.mContext.startActivity(i);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this.mContext, "Install google voice please!", 0).show();
            }
        }
    }

    protected void createNavigationBar() {
        this.mNavigationBarView = NavigationBarFragment.create(this.mContext, new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$bxGJK73UKGaF3EySkW_2rfCBuHg
            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewCreated(String str, Fragment fragment) {
                StatusBar.lambda$createNavigationBar$12(this.f$0, str, fragment);
            }
        });
        if (SystemUIApplication.getNaviBarPosition() > 0) {
            this.mDZ23Controller = (DZ23CanStatusBarController) Dependency.get(DZ23CanStatusBarController.class);
            if (this.mDZ23Controller != null) {
                this.mDZ23Controller.setNavigationBarFragment(this.mNavigationBarView, this.mHandler);
            }
        }
    }

    public static /* synthetic */ void lambda$createNavigationBar$12(StatusBar statusBar, String tag, Fragment fragment) {
        statusBar.mNavigationBar = (NavigationBarFragment) fragment;
        if (statusBar.mLightBarController != null) {
            statusBar.mNavigationBar.setLightBarController(statusBar.mLightBarController);
        }
        statusBar.mNavigationBar.setCurrentSysuiVisibility(statusBar.mSystemUiVisibility);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    protected View.OnTouchListener getStatusBarWindowTouchListener() {
        return new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$5DuNCjGkVbLv6UzPuHT7P5YRKFw
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return StatusBar.lambda$getStatusBarWindowTouchListener$13(this.f$0, view, motionEvent);
            }
        };
    }

    public static /* synthetic */ boolean lambda$getStatusBarWindowTouchListener$13(StatusBar statusBar, View v, MotionEvent event) {
        statusBar.checkUserAutohide(event);
        statusBar.mRemoteInputManager.checkRemoteInputOutside(event);
        if (event.getAction() == 0 && statusBar.mExpandedVisible) {
            statusBar.animateCollapsePanels();
        }
        return statusBar.mStatusBarWindow.onTouchEvent(event);
    }

    private void inflateShelf() {
        this.mNotificationShelf = (NotificationShelf) LayoutInflater.from(this.mContext).inflate(R.layout.status_bar_notification_shelf, (ViewGroup) this.mStackScroller, false);
        this.mNotificationShelf.setOnActivatedListener(this);
        this.mStackScroller.setShelf(this.mNotificationShelf);
        this.mNotificationShelf.setOnClickListener(this.mGoToLockedShadeListener);
        this.mNotificationShelf.setStatusBarState(this.mState);
    }

    public void onDensityOrFontScaleChanged() {
        MessagingMessage.dropCache();
        MessagingGroup.dropCache();
        if (!KeyguardUpdateMonitor.getInstance(this.mContext).isSwitchingUser()) {
            this.mEntryManager.updateNotificationsOnDensityOrFontScaleChanged();
        } else {
            this.mReinflateNotificationsOnUserSwitched = true;
        }
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.onDensityOrFontScaleChanged();
        }
        this.mStatusBarKeyguardViewManager.onDensityOrFontScaleChanged();
        ((UserInfoControllerImpl) Dependency.get(UserInfoController.class)).onDensityOrFontScaleChanged();
        ((UserSwitcherController) Dependency.get(UserSwitcherController.class)).onDensityOrFontScaleChanged();
        if (this.mKeyguardUserSwitcher != null) {
            this.mKeyguardUserSwitcher.onDensityOrFontScaleChanged();
        }
        this.mNotificationIconAreaController.onDensityOrFontScaleChanged(this.mContext);
        this.mHeadsUpManager.onDensityOrFontScaleChanged();
        reevaluateStyles();
    }

    private void setSplitButton() {
        View split;
        if (SystemUIApplication.isPortraitWindowMode(this.mContext) && (split = this.mStatusBarView.findViewById(R.id.split)) != null) {
            split.setOnClickListener(this.mSplitClickListener);
        }
    }

    private void setRotationButton() {
        View rotation;
        if (HConfig.needRotationToggleButton(this.mContext) && (rotation = this.mStatusBarView.findViewById(R.id.rotation)) != null) {
            rotation.setVisibility(0);
            rotation.setOnClickListener(this.mRotationClickListener);
        }
    }

    private void onThemeChanged() {
        reevaluateStyles();
        this.mNotificationPanel.onThemeChanged();
        if (this.mKeyguardStatusBar != null) {
            this.mKeyguardStatusBar.onThemeChanged();
        }
        this.mKeyguardIndicationController = SystemUIFactory.getInstance().createKeyguardIndicationController(this.mContext, (ViewGroup) this.mStatusBarWindow.findViewById(R.id.keyguard_indication_area), this.mNotificationPanel.getLockIcon());
        this.mNotificationPanel.setKeyguardIndicationController(this.mKeyguardIndicationController);
        this.mKeyguardIndicationController.setStatusBarKeyguardViewManager(this.mStatusBarKeyguardViewManager);
        this.mKeyguardIndicationController.setVisible(this.mState == 1);
        this.mKeyguardIndicationController.setDozing(this.mDozing);
        if (this.mStatusBarKeyguardViewManager != null) {
            this.mStatusBarKeyguardViewManager.onThemeChanged();
        }
        if (this.mAmbientIndicationContainer instanceof AutoReinflateContainer) {
            ((AutoReinflateContainer) this.mAmbientIndicationContainer).inflateLayout();
        }
    }

    protected void reevaluateStyles() {
        inflateFooterView();
        updateFooter();
        inflateEmptyShadeView();
        updateEmptyShadeView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBatteryVoltage() {
        float battery = McuManager.getsInstance().getBattery();
        String batteryStr = String.format(Locale.ENGLISH, "%.1fV", Float.valueOf(battery));
        Log.v("StatusBar", "get battery voltage: " + batteryStr);
        if (this.mBatteryVoltageView != null) {
            this.mBatteryVoltageView.setText(batteryStr);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onOverlayChanged() {
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.onOverlayChanged();
        }
    }

    private void inflateEmptyShadeView() {
        if (this.mStackScroller == null) {
            return;
        }
        this.mEmptyShadeView = (EmptyShadeView) LayoutInflater.from(this.mContext).inflate(R.layout.status_bar_no_notifications, (ViewGroup) this.mStackScroller, false);
        this.mEmptyShadeView.setText(R.string.empty_shade_text);
        this.mStackScroller.setEmptyShadeView(this.mEmptyShadeView);
    }

    private void inflateFooterView() {
        if (this.mStackScroller == null) {
            return;
        }
        this.mFooterView = (FooterView) LayoutInflater.from(this.mContext).inflate(R.layout.status_bar_notification_footer, (ViewGroup) this.mStackScroller, false);
        this.mFooterView.setDismissButtonClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$O-Wv011qCNwX6EduWeTTLl3vM68
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StatusBar.lambda$inflateFooterView$14(this.f$0, view);
            }
        });
        this.mFooterView.setManageButtonClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$5Ugw-UMuV_0z3aTZrKo1KJq53CM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.manageNotifications();
            }
        });
        this.mStackScroller.setFooterView(this.mFooterView);
    }

    public static /* synthetic */ void lambda$inflateFooterView$14(StatusBar statusBar, View v) {
        statusBar.mMetricsLogger.action(148);
        statusBar.clearAllNotifications();
    }

    protected void createUserSwitcher() {
        this.mKeyguardUserSwitcher = new KeyguardUserSwitcher(this.mContext, (ViewStub) this.mStatusBarWindow.findViewById(R.id.keyguard_user_switcher), this.mKeyguardStatusBar, this.mNotificationPanel);
    }

    protected void inflateStatusBarWindow(Context context) {
        this.mStatusBarWindow = (StatusBarWindowView) View.inflate(context, R.layout.super_status_bar, null);
    }

    public void manageNotifications() {
        Intent intent = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS");
        startActivity(intent, true, true, 536870912);
    }

    public void clearAllNotifications() {
        int numChildren = this.mStackScroller.getChildCount();
        ArrayList<View> viewsToHide = new ArrayList<>(numChildren);
        final ArrayList<ExpandableNotificationRow> viewsToRemove = new ArrayList<>(numChildren);
        for (int i = 0; i < numChildren; i++) {
            View child = this.mStackScroller.getChildAt(i);
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                boolean parentVisible = false;
                boolean hasClipBounds = child.getClipBounds(this.mTmpRect);
                if (this.mStackScroller.canChildBeDismissed(child)) {
                    viewsToRemove.add(row);
                    if (child.getVisibility() == 0 && (!hasClipBounds || this.mTmpRect.height() > 0)) {
                        viewsToHide.add(child);
                        parentVisible = true;
                    }
                } else if (child.getVisibility() == 0 && (!hasClipBounds || this.mTmpRect.height() > 0)) {
                    parentVisible = true;
                }
                List<ExpandableNotificationRow> children = row.getNotificationChildren();
                if (children != null) {
                    for (ExpandableNotificationRow childRow : children) {
                        viewsToRemove.add(childRow);
                        if (parentVisible && row.areChildrenExpanded() && this.mStackScroller.canChildBeDismissed(childRow)) {
                            boolean hasClipBounds2 = childRow.getClipBounds(this.mTmpRect);
                            if (childRow.getVisibility() == 0 && (!hasClipBounds2 || this.mTmpRect.height() > 0)) {
                                viewsToHide.add(childRow);
                            }
                        }
                    }
                }
            }
        }
        if (viewsToRemove.isEmpty()) {
            animateCollapsePanels(0);
        } else {
            addPostCollapseAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$YwJl8Z1_Wk-8Ikgqypg4wCfothI
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$clearAllNotifications$16(this.f$0, viewsToRemove);
                }
            });
            performDismissAllAnimations(viewsToHide);
        }
    }

    public static /* synthetic */ void lambda$clearAllNotifications$16(StatusBar statusBar, ArrayList viewsToRemove) {
        statusBar.mStackScroller.setDismissAllInProgress(false);
        Iterator it = viewsToRemove.iterator();
        while (it.hasNext()) {
            ExpandableNotificationRow rowToRemove = (ExpandableNotificationRow) it.next();
            if (statusBar.mStackScroller.canChildBeDismissed(rowToRemove)) {
                statusBar.mEntryManager.removeNotification(rowToRemove.getEntry().key, null);
            } else {
                rowToRemove.resetTranslation();
            }
        }
        try {
            statusBar.mBarService.onClearAllNotifications(statusBar.mLockscreenUserManager.getCurrentUserId());
        } catch (Exception e) {
        }
    }

    private void performDismissAllAnimations(ArrayList<View> hideAnimatedList) {
        Runnable animationFinishAction = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$s75-xLrcktgke9omL2ZU7-G0E-c
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.animateCollapsePanels(0);
            }
        };
        if (hideAnimatedList.isEmpty()) {
            animationFinishAction.run();
            return;
        }
        this.mStackScroller.setDismissAllInProgress(true);
        int currentDelay = 140;
        int totalDelay = 180;
        int numItems = hideAnimatedList.size();
        for (int i = numItems - 1; i >= 0; i--) {
            View view = hideAnimatedList.get(i);
            Runnable endRunnable = null;
            if (i == 0) {
                endRunnable = animationFinishAction;
            }
            this.mStackScroller.dismissViewAnimated(view, endRunnable, totalDelay, 260L);
            currentDelay = Math.max(50, currentDelay - 10);
            totalDelay += currentDelay;
        }
    }

    protected void startKeyguard() {
        Trace.beginSection("StatusBar#startKeyguard");
        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) getComponent(KeyguardViewMediator.class);
        this.mFingerprintUnlockController = new FingerprintUnlockController(this.mContext, this.mDozeScrimController, keyguardViewMediator, this.mScrimController, this, UnlockMethodCache.getInstance(this.mContext));
        this.mStatusBarKeyguardViewManager = keyguardViewMediator.registerStatusBar(this, getBouncerContainer(), this.mNotificationPanel, this.mFingerprintUnlockController);
        this.mKeyguardIndicationController.setStatusBarKeyguardViewManager(this.mStatusBarKeyguardViewManager);
        this.mFingerprintUnlockController.setStatusBarKeyguardViewManager(this.mStatusBarKeyguardViewManager);
        this.mRemoteInputManager.getController().addCallback(this.mStatusBarKeyguardViewManager);
        this.mKeyguardViewMediatorCallback = keyguardViewMediator.getViewMediatorCallback();
        this.mLightBarController.setFingerprintUnlockController(this.mFingerprintUnlockController);
        ((KeyguardDismissUtil) Dependency.get(KeyguardDismissUtil.class)).setDismissHandler(new KeyguardDismissHandler() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$9MqmIExNr864CrvDQmmxl0ZneDg
            @Override // com.android.systemui.statusbar.phone.KeyguardDismissHandler
            public final void executeWhenUnlocked(KeyguardHostView.OnDismissAction onDismissAction) {
                this.f$0.executeWhenUnlocked(onDismissAction);
            }
        });
        Trace.endSection();
    }

    protected View getStatusBarView() {
        return this.mStatusBarView;
    }

    public StatusBarWindowView getStatusBarWindow() {
        return this.mStatusBarWindow;
    }

    protected ViewGroup getBouncerContainer() {
        return this.mStatusBarWindow;
    }

    public int getStatusBarHeight() {
        if (this.mNaturalBarHeight < 0) {
            Resources res = this.mContext.getResources();
            this.mNaturalBarHeight = res.getDimensionPixelSize(android.R.dimen.handwriting_bounds_offset_right);
        }
        return this.mNaturalBarHeight;
    }

    protected boolean toggleSplitScreenMode(int metricsDockAction, int metricsUndockAction) {
        if (this.mRecents == null) {
            return false;
        }
        int dockSide = WindowManagerProxy.getInstance().getDockSide();
        if (dockSide == -1) {
            int navbarPos = WindowManagerWrapper.getInstance().getNavBarPosition();
            if (navbarPos == -1) {
                return false;
            }
            int createMode = navbarPos == 1 ? 1 : 0;
            return this.mRecents.splitPrimaryTask(-1, createMode, null, metricsDockAction);
        }
        Divider divider = (Divider) getComponent(Divider.class);
        if (divider != null && divider.isMinimized() && !divider.isHomeStackResizable()) {
            return false;
        }
        EventBus.getDefault().send(new UndockingTaskEvent());
        if (metricsUndockAction != -1) {
            this.mMetricsLogger.action(metricsUndockAction);
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public void onPerformRemoveNotification(StatusBarNotification n) {
        if (this.mStackScroller.hasPulsingNotifications() && !this.mHeadsUpManager.hasHeadsUpNotifications()) {
            this.mDozeScrimController.pulseOutNow();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void updateNotificationViews() {
        if (this.mStackScroller == null || this.mScrimController == null) {
            return;
        }
        if (isCollapsing()) {
            addPostCollapseAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$GA5FozNehiplNux-Psv9796exeM
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.updateNotificationViews();
                }
            });
            return;
        }
        this.mViewHierarchyManager.updateNotificationViews();
        updateSpeedBumpIndex();
        updateFooter();
        updateEmptyShadeView();
        updateQsExpansionEnabled();
        this.mNotificationIconAreaController.updateNotificationIcons();
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public void onNotificationAdded(NotificationData.Entry shadeEntry) {
        setAreThereNotifications();
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public void onNotificationUpdated(StatusBarNotification notification) {
        setAreThereNotifications();
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public void onNotificationRemoved(String key, StatusBarNotification old) {
        if (old != null && !hasActiveNotifications() && !this.mNotificationPanel.isTracking() && !this.mNotificationPanel.isQsExpanded()) {
            if (this.mState == 0) {
                animateCollapsePanels();
            } else if (this.mState == 2 && !isCollapsing()) {
                goToKeyguard();
            }
        }
        setAreThereNotifications();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateQsExpansionEnabled() {
        this.mNotificationPanel.setQsExpansionEnabled(isDeviceProvisioned() && (this.mUserSetup || this.mUserSwitcherController == null || !this.mUserSwitcherController.isSimpleUserSwitcher()) && (this.mDisabled2 & 4) == 0 && (this.mDisabled2 & 1) == 0 && !this.mDozing && !ONLY_CORE_APPS);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void addQsTile(ComponentName tile) {
        if (this.mQSPanel != null && this.mQSPanel.getHost() != null) {
            this.mQSPanel.getHost().addTile(tile);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void remQsTile(ComponentName tile) {
        if (this.mQSPanel != null && this.mQSPanel.getHost() != null) {
            this.mQSPanel.getHost().removeTile(tile);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void clickTile(ComponentName tile) {
        if (this.mQSPanel == null) {
            Log.w("StatusBar", "clickTile fail not qspanel, componentname: " + tile);
            return;
        }
        this.mQSPanel.clickTile(tile);
    }

    @VisibleForTesting
    protected void updateFooter() {
        boolean showFooterView = false;
        boolean showDismissView = this.mClearAllEnabled && hasActiveClearableNotifications();
        if ((showDismissView || this.mEntryManager.getNotificationData().getActiveNotifications().size() != 0) && this.mState != 1 && !this.mRemoteInputManager.getController().isRemoteInputActive()) {
            showFooterView = true;
        }
        this.mStackScroller.updateFooterView(showFooterView, showDismissView);
    }

    private boolean hasActiveClearableNotifications() {
        int childCount = this.mStackScroller.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.mStackScroller.getChildAt(i);
            if ((child instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) child).canViewBeDismissed()) {
                return true;
            }
        }
        return false;
    }

    private void updateEmptyShadeView() {
        boolean showEmptyShadeView = this.mState != 1 && this.mEntryManager.getNotificationData().getActiveNotifications().size() == 0;
        this.mNotificationPanel.showEmptyShadeView(showEmptyShadeView);
    }

    private void updateSpeedBumpIndex() {
        int N = this.mStackScroller.getChildCount();
        int currentIndex = 0;
        int speedBumpIndex = 0;
        for (int speedBumpIndex2 = 0; speedBumpIndex2 < N; speedBumpIndex2++) {
            View view = this.mStackScroller.getChildAt(speedBumpIndex2);
            if (view.getVisibility() != 8 && (view instanceof ExpandableNotificationRow)) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) view;
                currentIndex++;
                if (!this.mEntryManager.getNotificationData().isAmbient(row.getStatusBarNotification().getKey())) {
                    speedBumpIndex = currentIndex;
                }
            }
        }
        boolean noAmbient = speedBumpIndex == N;
        this.mStackScroller.updateSpeedBumpIndex(speedBumpIndex, noAmbient);
    }

    public static boolean isTopLevelChild(NotificationData.Entry entry) {
        return entry.row.getParent() instanceof NotificationStackScrollLayout;
    }

    public boolean areNotificationsHidden() {
        return this.mZenController.areNotificationsHiddenInShade();
    }

    public void requestNotificationUpdate() {
        this.mEntryManager.updateNotifications();
    }

    protected void setAreThereNotifications() {
        if (this.mStatusBarView != null) {
            final View nlo = this.mStatusBarView.findViewById(R.id.notification_lights_out);
            boolean showDot = hasActiveNotifications() && !areLightsOn();
            if (showDot != (nlo.getAlpha() == 1.0f)) {
                if (showDot) {
                    nlo.setAlpha(0.0f);
                    nlo.setVisibility(0);
                }
                nlo.animate().alpha(showDot ? 1.0f : 0.0f).setDuration(showDot ? 750L : 250L).setInterpolator(new AccelerateInterpolator(2.0f)).setListener(showDot ? null : new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.StatusBar.9
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator _a) {
                        nlo.setVisibility(8);
                    }
                }).start();
            }
        }
        this.mMediaManager.findAndUpdateMediaNotifications();
        updateCarrierLabelVisibility(false);
    }

    public void updateMediaMetaData(boolean metaDataChanged, boolean allowEnterAnimation) {
        Bitmap lockWallpaper;
        Trace.beginSection("StatusBar#updateMediaMetaData");
        if (this.mBackdrop == null) {
            Trace.endSection();
            return;
        }
        boolean wakeAndUnlock = this.mFingerprintUnlockController != null && this.mFingerprintUnlockController.isWakeAndUnlock();
        if (this.mLaunchTransitionFadingAway || wakeAndUnlock) {
            this.mBackdrop.setVisibility(4);
            Trace.endSection();
            return;
        }
        MediaMetadata mediaMetadata = this.mMediaManager.getMediaMetadata();
        Drawable artworkDrawable = null;
        if (mediaMetadata != null) {
            Bitmap artworkBitmap = mediaMetadata.getBitmap("android.media.metadata.ART");
            if (artworkBitmap == null) {
                artworkBitmap = mediaMetadata.getBitmap("android.media.metadata.ALBUM_ART");
            }
            if (artworkBitmap != null) {
                artworkDrawable = new BitmapDrawable(this.mBackdropBack.getResources(), artworkBitmap);
            }
        }
        boolean allowWhenShade = false;
        if (artworkDrawable == null && (lockWallpaper = this.mLockscreenWallpaper.getBitmap()) != null) {
            artworkDrawable = new LockscreenWallpaper.WallpaperDrawable(this.mBackdropBack.getResources(), lockWallpaper);
            allowWhenShade = this.mStatusBarKeyguardViewManager != null && this.mStatusBarKeyguardViewManager.isShowing();
        }
        boolean hideBecauseOccluded = this.mStatusBarKeyguardViewManager != null && this.mStatusBarKeyguardViewManager.isOccluded();
        boolean hasArtwork = artworkDrawable != null;
        if (hasArtwork && !this.mDozing && ((this.mState != 0 || allowWhenShade) && this.mFingerprintUnlockController.getMode() != 2 && !hideBecauseOccluded)) {
            if (this.mBackdrop.getVisibility() != 0) {
                this.mBackdrop.setVisibility(0);
                if (allowEnterAnimation) {
                    this.mBackdrop.setAlpha(0.002f);
                    this.mBackdrop.animate().alpha(1.0f);
                } else {
                    this.mBackdrop.animate().cancel();
                    this.mBackdrop.setAlpha(1.0f);
                }
                this.mStatusBarWindowManager.setBackdropShowing(true);
                this.mColorExtractor.setMediaBackdropVisible(true);
                metaDataChanged = true;
            }
            if (metaDataChanged) {
                if (this.mBackdropBack.getDrawable() != null) {
                    Drawable drawable = this.mBackdropBack.getDrawable().getConstantState().newDrawable(this.mBackdropFront.getResources()).mutate();
                    this.mBackdropFront.setImageDrawable(drawable);
                    if (this.mScrimSrcModeEnabled) {
                        this.mBackdropFront.getDrawable().mutate().setXfermode(this.mSrcOverXferMode);
                    }
                    this.mBackdropFront.setAlpha(1.0f);
                    this.mBackdropFront.setVisibility(0);
                } else {
                    this.mBackdropFront.setVisibility(4);
                }
                this.mBackdropBack.setImageDrawable(artworkDrawable);
                if (this.mScrimSrcModeEnabled) {
                    this.mBackdropBack.getDrawable().mutate().setXfermode(this.mSrcXferMode);
                }
                if (this.mBackdropFront.getVisibility() == 0) {
                    this.mBackdropFront.animate().setDuration(250L).alpha(0.0f).withEndAction(this.mHideBackdropFront);
                }
            }
        } else if (this.mBackdrop.getVisibility() != 8) {
            this.mColorExtractor.setMediaBackdropVisible(false);
            boolean cannotAnimateDoze = this.mDozing && !ScrimState.AOD.getAnimateChange();
            if (this.mFingerprintUnlockController.getMode() == 2 || hideBecauseOccluded || cannotAnimateDoze) {
                this.mBackdrop.setVisibility(8);
                this.mBackdropBack.setImageDrawable(null);
                this.mStatusBarWindowManager.setBackdropShowing(false);
            } else {
                this.mStatusBarWindowManager.setBackdropShowing(false);
                this.mBackdrop.animate().alpha(0.002f).setInterpolator(Interpolators.ACCELERATE_DECELERATE).setDuration(300L).setStartDelay(0L).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$OKgwmoJbKLKHaG8HMZkjE4ZpBw0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBar.lambda$updateMediaMetaData$18(this.f$0);
                    }
                });
                if (this.mKeyguardFadingAway) {
                    this.mBackdrop.animate().setDuration(this.mKeyguardFadingAwayDuration / 2).setStartDelay(this.mKeyguardFadingAwayDelay).setInterpolator(Interpolators.LINEAR).start();
                }
            }
        }
        Trace.endSection();
    }

    public static /* synthetic */ void lambda$updateMediaMetaData$18(StatusBar statusBar) {
        statusBar.mBackdrop.setVisibility(8);
        statusBar.mBackdropFront.animate().cancel();
        statusBar.mBackdropBack.setImageDrawable(null);
        statusBar.mHandler.post(statusBar.mHideBackdropFront);
    }

    private void updateReportRejectedTouchVisibility() {
        if (this.mReportRejectedTouch == null) {
            return;
        }
        this.mReportRejectedTouch.setVisibility((this.mState == 1 && !this.mDozing && this.mFalsingManager.isReportingEnabled()) ? 0 : 4);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void disable(int state1, int state2, boolean animate) {
        int state3 = this.mRemoteInputQuickSettingsDisabler.adjustDisableFlags(state2);
        boolean z = animate & (this.mStatusBarWindowState != 2);
        int old1 = this.mDisabled1;
        int diff1 = state1 ^ old1;
        this.mDisabled1 = state1;
        int old2 = this.mDisabled2;
        int diff2 = state3 ^ old2;
        this.mDisabled2 = state3;
        if (DEBUG) {
            Log.d("StatusBar", String.format("disable1: 0x%08x -> 0x%08x (diff1: 0x%08x)", Integer.valueOf(old1), Integer.valueOf(state1), Integer.valueOf(diff1)));
            Log.d("StatusBar", String.format("disable2: 0x%08x -> 0x%08x (diff2: 0x%08x)", Integer.valueOf(old2), Integer.valueOf(state3), Integer.valueOf(diff2)));
        }
        StringBuilder flagdbg = new StringBuilder();
        flagdbg.append("disable<");
        flagdbg.append((state1 & 65536) != 0 ? 'E' : 'e');
        flagdbg.append((diff1 & 65536) != 0 ? '!' : ' ');
        flagdbg.append((state1 & 131072) != 0 ? 'I' : 'i');
        flagdbg.append((131072 & diff1) != 0 ? '!' : ' ');
        flagdbg.append((state1 & 262144) != 0 ? 'A' : 'a');
        flagdbg.append((diff1 & 262144) != 0 ? '!' : ' ');
        flagdbg.append((state1 & 1048576) != 0 ? 'S' : 's');
        flagdbg.append((diff1 & 1048576) != 0 ? '!' : ' ');
        flagdbg.append((4194304 & state1) != 0 ? 'B' : 'b');
        flagdbg.append((4194304 & diff1) != 0 ? '!' : ' ');
        flagdbg.append((2097152 & state1) != 0 ? 'H' : 'h');
        flagdbg.append((2097152 & diff1) != 0 ? '!' : ' ');
        flagdbg.append((state1 & 16777216) != 0 ? 'R' : 'r');
        flagdbg.append((diff1 & 16777216) != 0 ? '!' : ' ');
        flagdbg.append((8388608 & state1) != 0 ? 'C' : 'c');
        flagdbg.append((8388608 & diff1) != 0 ? '!' : ' ');
        flagdbg.append((33554432 & state1) == 0 ? 's' : 'S');
        flagdbg.append((33554432 & diff1) != 0 ? '!' : ' ');
        flagdbg.append("> disable2<");
        flagdbg.append((state3 & 1) != 0 ? 'Q' : 'q');
        flagdbg.append((diff2 & 1) != 0 ? '!' : ' ');
        flagdbg.append((state3 & 2) != 0 ? 'I' : 'i');
        flagdbg.append((diff2 & 2) != 0 ? '!' : ' ');
        flagdbg.append((state3 & 4) != 0 ? 'N' : 'n');
        flagdbg.append((diff2 & 4) != 0 ? '!' : ' ');
        flagdbg.append('>');
        Log.d("StatusBar", flagdbg.toString());
        if ((diff1 & 65536) != 0 && (65536 & state1) != 0) {
            animateCollapsePanels();
        }
        if ((diff1 & 16777216) != 0) {
            if ((state1 & 16777216) != 0) {
                this.mHandler.removeMessages(1020);
                this.mHandler.sendEmptyMessage(1020);
                if (this.mStatusBarPlmnPlugin != null) {
                    this.mStatusBarPlmnPlugin.setPlmnVisibility(8);
                }
            } else if (this.mStatusBarPlmnPlugin != null) {
                this.mStatusBarPlmnPlugin.setPlmnVisibility(0);
            }
        }
        if (this.mStatusBarView != null) {
            boolean visible = (16777216 & state1) == 0;
            ImageView btn_recent = (ImageView) getStatusBarViewRecentsButton();
            if (btn_recent != null) {
                btn_recent.setEnabled(visible);
            }
            ImageView btn_back = (ImageView) this.mStatusBarView.findViewById(R.id.back);
            if (btn_back != null) {
                btn_back.setEnabled(visible);
            }
            ImageView btn_home = (ImageView) this.mStatusBarView.findViewById(R.id.home);
            if (btn_home != null) {
                btn_home.setEnabled(visible);
            }
        }
        if ((diff1 & 262144) != 0) {
            this.mEntryManager.setDisableNotificationAlerts((state1 & 262144) != 0);
        }
        if ((diff2 & 1) != 0) {
            updateQsExpansionEnabled();
        }
        if ((diff2 & 4) != 0) {
            updateQsExpansionEnabled();
            if ((state1 & 4) != 0) {
                animateCollapsePanels();
            }
        }
    }

    public void recomputeDisableFlags(boolean animate) {
        this.mCommandQueue.recomputeDisableFlags(animate);
    }

    protected H createHandler() {
        return new H();
    }

    private void startActivity(Intent intent, boolean onlyProvisioned, boolean dismissShade, int flags) {
        startActivityDismissingKeyguard(intent, onlyProvisioned, dismissShade, flags);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean dismissShade) {
        startActivityDismissingKeyguard(intent, false, dismissShade);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean onlyProvisioned, boolean dismissShade) {
        startActivityDismissingKeyguard(intent, onlyProvisioned, dismissShade);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean dismissShade, ActivityStarter.Callback callback) {
        startActivityDismissingKeyguard(intent, false, dismissShade, false, callback, 0);
    }

    public void setQsExpanded(boolean expanded) {
        int i;
        this.mStatusBarWindowManager.setQsExpanded(expanded);
        NotificationPanelView notificationPanelView = this.mNotificationPanel;
        if (expanded) {
            i = 4;
        } else {
            i = 0;
        }
        notificationPanelView.setStatusAccessibilityImportance(i);
    }

    public boolean isGoingToNotificationShade() {
        return this.mLeaveOpenOnKeyguardHide;
    }

    public boolean isWakeUpComingFromTouch() {
        return this.mWakeUpComingFromTouch;
    }

    public boolean isFalsingThresholdNeeded() {
        return getBarState() == 1;
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public boolean isDozing() {
        return this.mDozing && this.mStackScroller.isFullyDark();
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public boolean shouldPeek(NotificationData.Entry entry, StatusBarNotification sbn) {
        if (this.mIsOccluded && !isDozing()) {
            boolean devicePublic = this.mLockscreenUserManager.isLockscreenPublicMode(this.mLockscreenUserManager.getCurrentUserId());
            boolean userPublic = devicePublic || this.mLockscreenUserManager.isLockscreenPublicMode(sbn.getUserId());
            boolean needsRedaction = this.mLockscreenUserManager.needsRedaction(entry);
            if (userPublic && needsRedaction) {
                return false;
            }
        }
        boolean devicePublic2 = panelsEnabled();
        if (!devicePublic2) {
            if (DEBUG) {
                Log.d("StatusBar", "No peeking: disabled panel : " + sbn.getKey());
            }
            return false;
        }
        if (sbn.getNotification().fullScreenIntent == null) {
            return true;
        }
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            if (DEBUG) {
                Log.d("StatusBar", "No peeking: accessible fullscreen: " + sbn.getKey());
            }
            return false;
        }
        if (isDozing()) {
            return false;
        }
        return !this.mStatusBarKeyguardViewManager.isShowing() || this.mStatusBarKeyguardViewManager.isOccluded();
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public String getCurrentMediaNotificationKey() {
        return this.mMediaManager.getMediaNotificationKey();
    }

    public boolean isScrimSrcModeEnabled() {
        return this.mScrimSrcModeEnabled;
    }

    public void onKeyguardViewManagerStatesUpdated() {
        logStateToEventlog();
    }

    @Override // com.android.systemui.statusbar.phone.UnlockMethodCache.OnUnlockMethodChangedListener
    public void onUnlockMethodStateChanged() {
        logStateToEventlog();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinnedModeChanged(boolean inPinnedMode) {
        if (inPinnedMode) {
            this.mStatusBarWindowManager.setHeadsUpShowing(true);
            this.mStatusBarWindowManager.setForceStatusBarVisible(true);
            if (this.mNotificationPanel.isFullyCollapsed()) {
                this.mNotificationPanel.requestLayout();
                this.mStatusBarWindowManager.setForceWindowCollapsed(true);
                this.mNotificationPanel.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$OL2P4RHv1KVB2pnvGsyWpK0DLPY
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mStatusBarWindowManager.setForceWindowCollapsed(false);
                    }
                });
                return;
            }
            return;
        }
        if (this.mNotificationPanel.isFullyCollapsed() && !this.mNotificationPanel.isTracking()) {
            this.mHeadsUpManager.setHeadsUpGoingAway(true);
            this.mStackScroller.runAfterAnimationFinished(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$EoN5W1U7BTw7oyJlqfjdN9hVEvY
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$onHeadsUpPinnedModeChanged$20(this.f$0);
                }
            });
        } else {
            this.mStatusBarWindowManager.setHeadsUpShowing(false);
        }
    }

    public static /* synthetic */ void lambda$onHeadsUpPinnedModeChanged$20(StatusBar statusBar) {
        if (!statusBar.mHeadsUpManager.hasPinnedHeadsUp()) {
            statusBar.mStatusBarWindowManager.setHeadsUpShowing(false);
            statusBar.mHeadsUpManager.setHeadsUpGoingAway(false);
        }
        statusBar.mRemoteInputManager.removeRemoteInputEntriesKeptUntilCollapsed();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinned(ExpandableNotificationRow headsUp) {
        dismissVolumeDialog();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpUnPinned(ExpandableNotificationRow headsUp) {
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpStateChanged(NotificationData.Entry entry, boolean isHeadsUp) {
        this.mEntryManager.onHeadsUpStateChanged(entry, isHeadsUp);
        if (isHeadsUp) {
            this.mDozeServiceHost.fireNotificationHeadsUp();
        }
    }

    protected void setHeadsUpUser(int newUserId) {
        if (this.mHeadsUpManager != null) {
            this.mHeadsUpManager.setUser(newUserId);
        }
    }

    public boolean isKeyguardCurrentlySecure() {
        return !this.mUnlockMethodCache.canSkipBouncer();
    }

    public void setPanelExpanded(boolean isExpanded) {
        this.mPanelExpanded = isExpanded;
        updateHideIconsForBouncer(false);
        this.mStatusBarWindowManager.setPanelExpanded(isExpanded);
        this.mVisualStabilityManager.setPanelExpanded(isExpanded);
        if (isExpanded && getBarState() != 1) {
            if (DEBUG) {
                Log.v("StatusBar", "clearing notification effects from setExpandedHeight");
            }
            clearNotificationEffects();
        }
        if (!isExpanded) {
            this.mRemoteInputManager.removeRemoteInputEntriesKeptUntilCollapsed();
        }
    }

    public NotificationStackScrollLayout getNotificationScrollLayout() {
        return this.mStackScroller;
    }

    public boolean isPulsing() {
        return this.mDozeScrimController != null && this.mDozeScrimController.isPulsing();
    }

    public boolean isLaunchTransitionFadingAway() {
        return this.mLaunchTransitionFadingAway;
    }

    public boolean hideStatusBarIconsWhenExpanded() {
        return this.mNotificationPanel.hideStatusBarIconsWhenExpanded();
    }

    public void onColorsChanged(ColorExtractor extractor, int which) {
        updateTheme();
    }

    public boolean isUsingDarkTheme() {
        if (this.mOverlayManager == null) {
            return false;
        }
        OverlayInfo themeInfo = null;
        try {
            themeInfo = this.mOverlayManager.getOverlayInfo("com.android.systemui.theme.dark", this.mLockscreenUserManager.getCurrentUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    public View getAmbientIndicationContainer() {
        return this.mAmbientIndicationContainer;
    }

    public void setOccluded(boolean occluded) {
        this.mIsOccluded = occluded;
        this.mScrimController.setKeyguardOccluded(occluded);
        updateHideIconsForBouncer(false);
    }

    public boolean hideStatusBarIconsForBouncer() {
        return this.mHideIconsForBouncer || this.mWereIconsJustHidden;
    }

    private void updateHideIconsForBouncer(boolean animate) {
        boolean hideBecauseApp = this.mTopHidesStatusBar && this.mIsOccluded && (this.mStatusBarWindowHidden || this.mBouncerShowing);
        boolean hideBecauseKeyguard = (this.mPanelExpanded || this.mIsOccluded || !this.mBouncerShowing) ? false : true;
        boolean shouldHideIconsForBouncer = hideBecauseApp || hideBecauseKeyguard;
        if (this.mHideIconsForBouncer != shouldHideIconsForBouncer) {
            this.mHideIconsForBouncer = shouldHideIconsForBouncer;
            if (!shouldHideIconsForBouncer && this.mBouncerWasShowingWhenHidden) {
                this.mWereIconsJustHidden = true;
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$3pZkTnn8OxbCCul4oyKKALPCspk
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBar.lambda$updateHideIconsForBouncer$21(this.f$0);
                    }
                }, 500L);
            } else {
                recomputeDisableFlags(animate);
            }
        }
        if (shouldHideIconsForBouncer) {
            this.mBouncerWasShowingWhenHidden = this.mBouncerShowing;
        }
    }

    public static /* synthetic */ void lambda$updateHideIconsForBouncer$21(StatusBar statusBar) {
        statusBar.mWereIconsJustHidden = false;
        statusBar.recomputeDisableFlags(true);
    }

    public void onLaunchAnimationCancelled() {
        if (!isCollapsing()) {
            onClosingFinished();
        }
    }

    protected class H extends Handler {
        protected H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message m) {
            int i = m.what;
            switch (i) {
                case 1000:
                    StatusBar.this.animateExpandNotificationsPanel();
                    break;
                case 1001:
                    StatusBar.this.animateCollapsePanels();
                    break;
                case 1002:
                    StatusBar.this.animateExpandSettingsPanel((String) m.obj);
                    break;
                case 1003:
                    StatusBar.this.onLaunchTransitionTimeout();
                    break;
                default:
                    switch (i) {
                        case 1005:
                            AIR air_bean = (AIR) m.obj;
                            if (StatusBar.this.mNavigationBar != null) {
                                StatusBar.this.mNavigationBar.updateAir(air_bean);
                            }
                            break;
                        case 1006:
                            AirCtrlManager.getInstance().init(StatusBar.this.mContext);
                            AirCtrlManager.getInstance().registerCallBack(StatusBar.this.callBack);
                            break;
                        case 1007:
                            StatusBar.this.setBatteryVoltage();
                            removeMessages(m.what);
                            sendEmptyMessageDelayed(m.what, 3000L);
                            break;
                        default:
                            switch (i) {
                                case 1026:
                                    StatusBar.this.toggleKeyboardShortcuts(m.arg1);
                                    break;
                                case 1027:
                                    StatusBar.this.dismissKeyboardShortcuts();
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
    }

    public void maybeEscalateHeadsUp() {
        this.mHeadsUpManager.getAllEntries().forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StatusBar.lambda$maybeEscalateHeadsUp$22((NotificationData.Entry) obj);
            }
        });
        this.mHeadsUpManager.releaseAllImmediately();
    }

    static /* synthetic */ void lambda$maybeEscalateHeadsUp$22(NotificationData.Entry entry) {
        StatusBarNotification sbn = entry.notification;
        Notification notification = sbn.getNotification();
        if (notification.fullScreenIntent != null) {
            if (DEBUG) {
                Log.d("StatusBar", "converting a heads up to fullScreen");
            }
            try {
                EventLog.writeEvent(36003, sbn.getKey());
                notification.fullScreenIntent.send();
                entry.notifyFullScreenIntentLaunched();
            } catch (PendingIntent.CanceledException e) {
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void handleSystemKey(int key) {
        if (panelsEnabled() && this.mKeyguardMonitor.isDeviceInteractive()) {
            if ((!this.mKeyguardMonitor.isShowing() || this.mKeyguardMonitor.isOccluded()) && this.mUserSetup) {
                if (280 == key) {
                    this.mMetricsLogger.action(493);
                    this.mNotificationPanel.collapse(false, 1.0f);
                    return;
                }
                if (281 == key) {
                    this.mMetricsLogger.action(494);
                    if (this.mNotificationPanel.isFullyCollapsed()) {
                        if (this.mVibrateOnOpening) {
                            this.mVibratorHelper.vibrate(2);
                        }
                        this.mNotificationPanel.expand(true);
                        this.mMetricsLogger.count("panel_open", 1);
                        return;
                    }
                    if (!this.mNotificationPanel.isInSettings() && !this.mNotificationPanel.isExpanding()) {
                        this.mNotificationPanel.flingSettings(0.0f, true);
                        this.mMetricsLogger.count("panel_open_qs", 1);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showPinningEnterExitToast(boolean entering) {
        if (entering) {
            this.mScreenPinningNotify.showPinningStartToast();
        } else {
            this.mScreenPinningNotify.showPinningExitToast();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showPinningEscapeToast() {
        this.mScreenPinningNotify.showEscapeToast(getNavigationBarView() == null || getNavigationBarView().isRecentsButtonVisible());
    }

    boolean panelsEnabled() {
        if (ActivityManager.isUserAMonkey()) {
            Log.d("StatusBar", "monkey testing, disable Panels");
            return false;
        }
        if (com.auto.common.HConfig.isDisableDropdownPanelBar() || com.auto.common.HConfig.isChangedAppBlockingDropdownPanelBar()) {
            return false;
        }
        boolean hasPinnedHeadUp = this.mHeadsUpManager != null && this.mHeadsUpManager.hasPinnedHeadsUp();
        return (hasPinnedHeadUp || ((this.mDisabled1 & 65536) == 0 && (this.mDisabled2 & 4) == 0)) && !ONLY_CORE_APPS;
    }

    void makeExpandedVisible(boolean force) {
        if (!force && (this.mExpandedVisible || !panelsEnabled())) {
            return;
        }
        this.mExpandedVisible = true;
        updateCarrierLabelVisibility(true);
        this.mStatusBarWindowManager.setPanelVisible(true);
        visibilityChanged(true);
        recomputeDisableFlags(!force);
        setInteracting(1, true);
    }

    public void animateCollapsePanels() {
        animateCollapsePanels(0);
    }

    public void postAnimateCollapsePanels() {
        this.mHandler.post(this.mAnimateCollapsePanels);
    }

    public void postAnimateForceCollapsePanels() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$naFEUrjBDlFuXm1tPClp8YQ7NIY
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.animateCollapsePanels(0, true);
            }
        });
    }

    public void postAnimateOpenPanels() {
        this.mHandler.sendEmptyMessage(1002);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void togglePanel() {
        if (this.mPanelExpanded) {
            animateCollapsePanels();
        } else {
            animateExpandNotificationsPanel();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void animateCollapsePanels(int flags) {
        animateCollapsePanels(flags, false, false, 1.0f);
    }

    public void animateCollapsePanels(int flags, boolean force) {
        animateCollapsePanels(flags, force, false, 1.0f);
    }

    public void animateCollapsePanels(int flags, boolean force, boolean delayed) {
        animateCollapsePanels(flags, force, delayed, 1.0f);
    }

    public void animateCollapsePanels(int flags, boolean force, boolean delayed, float speedUpFactor) {
        if (!force && this.mState != 0) {
            runPostCollapseRunnables();
            return;
        }
        if ((flags & 2) == 0 && !this.mHandler.hasMessages(1020)) {
            this.mHandler.removeMessages(1020);
            this.mHandler.sendEmptyMessage(1020);
        }
        Log.v("StatusBar", "mStatusBarWindow: " + this.mStatusBarWindow + " canPanelBeCollapsed(): " + this.mNotificationPanel.canPanelBeCollapsed());
        if (this.mStatusBarWindow != null && this.mNotificationPanel.canPanelBeCollapsed()) {
            this.mStatusBarWindowManager.setStatusBarFocusable(false);
            this.mStatusBarWindow.cancelExpandHelper();
            this.mStatusBarView.collapsePanel(true, delayed, speedUpFactor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runPostCollapseRunnables() {
        ArrayList<Runnable> clonedList = new ArrayList<>(this.mPostCollapseRunnables);
        this.mPostCollapseRunnables.clear();
        int size = clonedList.size();
        for (int i = 0; i < size; i++) {
            clonedList.get(i).run();
        }
        this.mStatusBarKeyguardViewManager.readyForKeyguardDone();
    }

    public void animateExpandNotificationsPanel() {
        if (!panelsEnabled()) {
            return;
        }
        this.mNotificationPanel.expandWithoutQs();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void animateExpandSettingsPanel(String subPanel) {
        if (panelsEnabled() && this.mUserSetup) {
            if (subPanel != null) {
                if (this.mQSPanel == null) {
                    Log.w("StatusBar", "animateExpandSettingsPanel fail not qspanel, subPanel: " + subPanel);
                    return;
                }
                this.mQSPanel.openDetails(subPanel);
            }
            this.mNotificationPanel.expandWithQs();
        }
    }

    public void animateCollapseQuickSettings() {
        if (this.mState == 0) {
            this.mStatusBarView.collapsePanel(true, false, 1.0f);
        }
    }

    void makeExpandedInvisible() {
        if (!this.mExpandedVisible || this.mStatusBarWindow == null) {
            return;
        }
        this.mStatusBarView.collapsePanel(false, false, 1.0f);
        this.mNotificationPanel.closeQs();
        this.mExpandedVisible = false;
        visibilityChanged(false);
        this.mStatusBarWindowManager.setPanelVisible(false);
        this.mStatusBarWindowManager.setForceStatusBarVisible(false);
        this.mGutsManager.closeAndSaveGuts(true, true, true, -1, -1, true);
        runPostCollapseRunnables();
        setInteracting(1, false);
        showBouncerIfKeyguard();
        recomputeDisableFlags(this.mNotificationPanel.hideStatusBarIconsWhenExpanded());
        if (!this.mStatusBarKeyguardViewManager.isShowing()) {
            WindowManagerGlobal.getInstance().trimMemory(20);
        }
    }

    public boolean interceptTouchEvent(MotionEvent event) {
        if (CHATTY && event.getAction() != 2) {
            Log.d("StatusBar", String.format("panel: %s at (%f, %f) mDisabled1=0x%08x mDisabled2=0x%08x", MotionEvent.actionToString(event.getAction()), Float.valueOf(event.getRawX()), Float.valueOf(event.getRawY()), Integer.valueOf(this.mDisabled1), Integer.valueOf(this.mDisabled2)));
        }
        if (this.mStatusBarWindowState == 0) {
            boolean upOrCancel = event.getAction() == 1 || event.getAction() == 3;
            if (upOrCancel && !this.mExpandedVisible) {
                setInteracting(1, false);
            } else {
                setInteracting(1, true);
            }
        }
        return false;
    }

    public GestureRecorder getGestureRecorder() {
        return this.mGestureRec;
    }

    public FingerprintUnlockController getFingerprintUnlockController() {
        return this.mFingerprintUnlockController;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setWindowState(int window, int state) {
        boolean showing = state == 0;
        if (this.mStatusBarWindow != null && window == 1 && this.mStatusBarWindowState != state) {
            this.mStatusBarWindowState = state;
            if (!showing && this.mState == 0) {
                this.mStatusBarView.collapsePanel(false, false, 1.0f);
            }
            if (this.mStatusBarView != null) {
                this.mStatusBarWindowHidden = state == 2;
                updateHideIconsForBouncer(false);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setSystemUiVisibility(int vis, int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenStackBounds, Rect dockedStackBounds) {
        int oldVal = this.mSystemUiVisibility;
        int newVal = ((~mask) & oldVal) | (vis & mask);
        int diff = newVal ^ oldVal;
        if (DEBUG) {
            Log.d("StatusBar", String.format("setSystemUiVisibility vis=%s mask=%s oldVal=%s newVal=%s diff=%s", Integer.toHexString(vis), Integer.toHexString(mask), Integer.toHexString(oldVal), Integer.toHexString(newVal), Integer.toHexString(diff)));
        }
        boolean sbModeChanged = false;
        if (diff != 0) {
            this.mSystemUiVisibility = newVal;
            if ((diff & 1) != 0) {
                setAreThereNotifications();
            }
            if ((268435456 & vis) != 0) {
                this.mSystemUiVisibility &= -268435457;
                this.mNoAnimationOnNextBarModeChange = true;
            }
            int sbMode = computeStatusBarMode(oldVal, newVal);
            sbModeChanged = sbMode != -1;
            if (sbModeChanged && sbMode != this.mStatusBarMode) {
                this.mStatusBarMode = sbMode;
                checkBarModes();
                touchAutoHide();
            }
            if ((536870912 & vis) != 0) {
                this.mSystemUiVisibility &= -536870913;
            }
            notifyUiVisibilityChanged(this.mSystemUiVisibility);
        }
        this.mLightBarController.onSystemUiVisibilityChanged(fullscreenStackVis, dockedStackVis, mask, fullscreenStackBounds, dockedStackBounds, sbModeChanged, this.mStatusBarMode);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showWirelessChargingAnimation(int batteryLevel) {
        if (this.mDozing || this.mKeyguardManager.isKeyguardLocked()) {
            WirelessChargingAnimation.makeWirelessChargingAnimation(this.mContext, null, batteryLevel, new WirelessChargingAnimation.Callback() { // from class: com.android.systemui.statusbar.phone.StatusBar.11
                @Override // com.android.systemui.charging.WirelessChargingAnimation.Callback
                public void onAnimationStarting() {
                    CrossFadeHelper.fadeOut(StatusBar.this.mNotificationPanel, 1.0f);
                }

                @Override // com.android.systemui.charging.WirelessChargingAnimation.Callback
                public void onAnimationEnded() {
                    CrossFadeHelper.fadeIn(StatusBar.this.mNotificationPanel);
                }
            }, this.mDozing).show();
        } else {
            WirelessChargingAnimation.makeWirelessChargingAnimation(this.mContext, null, batteryLevel, null, false).show();
        }
    }

    void touchAutoHide() {
        if (this.mStatusBarMode == 1 || (this.mNavigationBar != null && this.mNavigationBar.isSemiTransparent())) {
            scheduleAutohide();
        } else {
            cancelAutohide();
        }
    }

    protected int computeStatusBarMode(int oldVal, int newVal) {
        return computeBarMode(oldVal, newVal, 67108864, 1073741824, 8);
    }

    protected BarTransitions getStatusBarTransitions() {
        return this.mStatusBarView.getBarTransitions();
    }

    protected int computeBarMode(int oldVis, int newVis, int transientFlag, int translucentFlag, int transparentFlag) {
        int oldMode = barMode(oldVis, transientFlag, translucentFlag, transparentFlag);
        int newMode = barMode(newVis, transientFlag, translucentFlag, transparentFlag);
        if (oldMode == newMode) {
            return -1;
        }
        return newMode;
    }

    private int barMode(int vis, int transientFlag, int translucentFlag, int transparentFlag) {
        int lightsOutTransparent = 1 | transparentFlag;
        if ((vis & transientFlag) != 0) {
            return 1;
        }
        if ((vis & translucentFlag) != 0) {
            return 2;
        }
        if ((vis & lightsOutTransparent) == lightsOutTransparent) {
            return 6;
        }
        if ((vis & transparentFlag) != 0) {
            return 4;
        }
        return (vis & 1) != 0 ? 3 : 0;
    }

    void checkBarModes() {
        if (this.mDemoMode) {
            return;
        }
        if (this.mStatusBarView != null) {
            checkBarMode(this.mStatusBarMode, this.mStatusBarWindowState, getStatusBarTransitions());
        }
        if (this.mNavigationBar != null) {
            this.mNavigationBar.checkNavBarModes();
        }
        this.mNoAnimationOnNextBarModeChange = false;
    }

    void attachPlmnPlugin() {
        try {
            if (this.mStatusBarPlmnPlugin == null) {
                this.mSystemUIFactoryBase = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext);
                this.mStatusBarPlmnPlugin = this.mSystemUIFactoryBase.makeStatusBarPlmn(this.mContext);
                Log.d("StatusBar", "create plugin mStatusBarPlmnPlugin = " + this.mStatusBarPlmnPlugin);
            }
        } catch (Exception e) {
            Log.e("StatusBar", "mStatusBarPlmnPlugin init failed = " + e);
        }
        try {
            this.mStatusBarPlmnPlugin.addPlmn((LinearLayout) this.mStatusBarView.findViewById(R.id.status_bar_contents), this.mContext);
            this.mStatusBarPlmnPlugin.setPlmnVisibility(8);
        } catch (Exception e2) {
            Log.e("StatusBar", "exception = " + e2);
        }
    }

    void tryHideStatusBarContents() {
        View contents;
        boolean needHidden = false;
        ContentResolver cr = this.mContext.getContentResolver();
        String pin = Settings.System.getString(cr, "lockscreen_unlock_pin");
        if (pin != null && !pin.isEmpty() && !this.mLockPatternUtils.isLockScreenDisabled(0)) {
            needHidden = true;
        }
        if (this.mStatusBarView != null && (contents = this.mStatusBarView.findViewById(R.id.status_bar_contents)) != null && (com.auto.common.HConfig.isDisableStatusBarContents() || needHidden)) {
            contents.setVisibility(4);
        }
        if (needHidden && this.mNavigationBarView != null) {
            this.mWindowManager.removeViewImmediate(this.mNavigationBarView);
            this.mNavigationBarView = null;
        }
    }

    void setQsScrimEnabled(boolean scrimEnabled) {
        this.mNotificationPanel.setQsScrimEnabled(scrimEnabled);
    }

    void checkBarMode(int mode, int windowState, BarTransitions transitions) {
        boolean anim = (this.mNoAnimationOnNextBarModeChange || !this.mDeviceInteractive || windowState == 2) ? false : true;
        transitions.transitionTo(mode, anim);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishBarAnimations() {
        if (this.mStatusBarView != null) {
            this.mStatusBarView.getBarTransitions().finishAnimations();
        }
        if (this.mNavigationBar != null) {
            this.mNavigationBar.finishBarAnimations();
        }
    }

    public void setInteracting(int barWindow, boolean interacting) {
        int i;
        boolean changing = ((this.mInteractingWindows & barWindow) != 0) != interacting;
        if (interacting) {
            i = this.mInteractingWindows | barWindow;
        } else {
            i = this.mInteractingWindows & (~barWindow);
        }
        this.mInteractingWindows = i;
        if (this.mInteractingWindows != 0) {
            suspendAutohide();
        } else {
            resumeSuspendedAutohide();
        }
        if (changing && interacting && barWindow == 2) {
            touchAutoDim();
            dismissVolumeDialog();
        }
        checkBarModes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissVolumeDialog() {
        if (this.mVolumeComponent != null) {
            this.mVolumeComponent.dismissNow();
        }
    }

    private void resumeSuspendedAutohide() {
        if (this.mAutohideSuspended) {
            scheduleAutohide();
            this.mHandler.postDelayed(this.mCheckBarModes, 500L);
        }
    }

    private void suspendAutohide() {
        this.mHandler.removeCallbacks(this.mAutohide);
        this.mHandler.removeCallbacks(this.mCheckBarModes);
        this.mAutohideSuspended = (this.mSystemUiVisibility & 201326592) != 0;
    }

    private void cancelAutohide() {
        this.mAutohideSuspended = false;
        this.mHandler.removeCallbacks(this.mAutohide);
    }

    private void scheduleAutohide() {
        cancelAutohide();
        this.mHandler.postDelayed(this.mAutohide, 2250L);
    }

    public void touchAutoDim() {
        if (this.mNavigationBar != null) {
            this.mNavigationBar.getBarTransitions().setAutoDim(false);
        }
        this.mHandler.removeCallbacks(this.mAutoDim);
        if (this.mState != 1 && this.mState != 2) {
            this.mHandler.postDelayed(this.mAutoDim, 2250L);
        }
    }

    void checkUserAutohide(MotionEvent event) {
        if ((this.mSystemUiVisibility & 201326592) != 0 && event.getAction() == 4 && event.getX() == 0.0f && event.getY() == 0.0f && !this.mRemoteInputManager.getController().isRemoteInputActive()) {
            userAutohide();
        }
    }

    private void userAutohide() {
        cancelAutohide();
        this.mHandler.postDelayed(this.mAutohide, 350L);
    }

    private boolean areLightsOn() {
        return (this.mSystemUiVisibility & 1) == 0;
    }

    public void setLightsOn(boolean on) {
        Log.v("StatusBar", "setLightsOn(" + on + ")");
        if (on) {
            setSystemUiVisibility(0, 0, 0, 1, this.mLastFullscreenStackBounds, this.mLastDockedStackBounds);
        } else {
            setSystemUiVisibility(1, 0, 0, 1, this.mLastFullscreenStackBounds, this.mLastDockedStackBounds);
        }
    }

    private void notifyUiVisibilityChanged(int vis) {
        try {
            if (this.mLastDispatchedSystemUiVisibility != vis) {
                this.mWindowManagerService.statusBarVisibilityChanged(vis);
                this.mLastDispatchedSystemUiVisibility = vis;
            }
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void topAppWindowChanged(boolean showMenu) {
        if (showMenu) {
            setLightsOn(true);
        }
    }

    public static String viewInfo(View v) {
        return "[(" + v.getLeft() + "," + v.getTop() + ")(" + v.getRight() + "," + v.getBottom() + ") " + v.getWidth() + "x" + v.getHeight() + "]";
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        synchronized (this.mQueueLock) {
            pw.println("Current Status Bar state:");
            pw.println("  mExpandedVisible=" + this.mExpandedVisible);
            pw.println("  mDisplayMetrics=" + this.mDisplayMetrics);
            pw.println("  mStackScroller: " + viewInfo(this.mStackScroller));
            pw.println("  mStackScroller: " + viewInfo(this.mStackScroller) + " scroll " + this.mStackScroller.getScrollX() + "," + this.mStackScroller.getScrollY());
        }
        pw.print("  mInteractingWindows=");
        pw.println(this.mInteractingWindows);
        pw.print("  mStatusBarWindowState=");
        pw.println(StatusBarManager.windowStateToString(this.mStatusBarWindowState));
        pw.print("  mStatusBarMode=");
        pw.println(BarTransitions.modeToString(this.mStatusBarMode));
        pw.print("  mDozing=");
        pw.println(this.mDozing);
        pw.print("  mZenMode=");
        pw.println(Settings.Global.zenModeToString(Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0)));
        if (this.mStatusBarView != null) {
            dumpBarTransitions(pw, "mStatusBarView", this.mStatusBarView.getBarTransitions());
        }
        pw.println("  StatusBarWindowView: ");
        if (this.mStatusBarWindow != null) {
            this.mStatusBarWindow.dump(fd, pw, args);
        }
        pw.println("  mMediaManager: ");
        if (this.mMediaManager != null) {
            this.mMediaManager.dump(fd, pw, args);
        }
        pw.println("  Panels: ");
        if (this.mNotificationPanel != null) {
            pw.println("    mNotificationPanel=" + this.mNotificationPanel + " params=" + this.mNotificationPanel.getLayoutParams().debug(""));
            pw.print("      ");
            this.mNotificationPanel.dump(fd, pw, args);
        }
        pw.println("  mStackScroller: ");
        if (this.mStackScroller != null) {
            pw.print("      ");
            this.mStackScroller.dump(fd, pw, args);
        }
        pw.println("  Theme:");
        if (this.mOverlayManager == null) {
            pw.println("    overlay manager not initialized!");
        } else {
            pw.println("    dark overlay on: " + isUsingDarkTheme());
        }
        boolean lightWpTheme = this.mContext.getThemeResId() == 2131886648;
        pw.println("    light wallpaper theme: " + lightWpTheme);
        DozeLog.dump(pw);
        if (this.mFingerprintUnlockController != null) {
            this.mFingerprintUnlockController.dump(pw);
        }
        if (this.mKeyguardIndicationController != null) {
            this.mKeyguardIndicationController.dump(fd, pw, args);
        }
        if (this.mScrimController != null) {
            this.mScrimController.dump(fd, pw, args);
        }
        if (this.mStatusBarKeyguardViewManager != null) {
            this.mStatusBarKeyguardViewManager.dump(pw);
        }
        synchronized (this.mEntryManager.getNotificationData()) {
            this.mEntryManager.getNotificationData().dump(pw, "  ");
        }
        if (this.mHeadsUpManager != null) {
            this.mHeadsUpManager.dump(fd, pw, args);
        } else {
            pw.println("  mHeadsUpManager: null");
        }
        if (this.mGroupManager != null) {
            this.mGroupManager.dump(fd, pw, args);
        } else {
            pw.println("  mGroupManager: null");
        }
        if (this.mLightBarController != null) {
            this.mLightBarController.dump(fd, pw, args);
        }
        if (KeyguardUpdateMonitor.getInstance(this.mContext) != null) {
            KeyguardUpdateMonitor.getInstance(this.mContext).dump(fd, pw, args);
        }
        FalsingManager.getInstance(this.mContext).dump(pw);
        FalsingLog.dump(pw);
        pw.println("SharedPreferences:");
        for (Map.Entry<String, ?> entry : Prefs.getAll(this.mContext).entrySet()) {
            pw.print("  ");
            pw.print(entry.getKey());
            pw.print("=");
            pw.println(entry.getValue());
        }
    }

    static void dumpBarTransitions(PrintWriter pw, String var, BarTransitions transitions) {
        pw.print("  ");
        pw.print(var);
        pw.print(".BarTransitions.mMode=");
        pw.println(BarTransitions.modeToString(transitions.getMode()));
    }

    public void createAndAddWindows() {
        addStatusBarWindow();
    }

    private void addStatusBarWindow() {
        makeStatusBarView();
        this.mStatusBarWindowManager = (StatusBarWindowManager) Dependency.get(StatusBarWindowManager.class);
        this.mRemoteInputManager.setUpWithPresenter(this, this.mEntryManager, this, new RemoteInputController.Delegate() { // from class: com.android.systemui.statusbar.phone.StatusBar.12
            @Override // com.android.systemui.statusbar.RemoteInputController.Delegate
            public void setRemoteInputActive(NotificationData.Entry entry, boolean remoteInputActive) {
                StatusBar.this.mHeadsUpManager.setRemoteInputActive(entry, remoteInputActive);
                entry.row.notifyHeightChanged(true);
                StatusBar.this.updateFooter();
            }

            @Override // com.android.systemui.statusbar.RemoteInputController.Delegate
            public void lockScrollTo(NotificationData.Entry entry) {
                StatusBar.this.mStackScroller.lockScrollTo(entry.row);
            }

            @Override // com.android.systemui.statusbar.RemoteInputController.Delegate
            public void requestDisallowLongPressAndDismiss() {
                StatusBar.this.mStackScroller.requestDisallowLongPress();
                StatusBar.this.mStackScroller.requestDisallowDismiss();
            }
        });
        this.mRemoteInputManager.getController().addCallback(this.mStatusBarWindowManager);
        this.mStatusBarWindowManager.add(this.mStatusBarWindow, getStatusBarHeight());
    }

    void updateDisplaySize() {
        this.mDisplay.getMetrics(this.mDisplayMetrics);
        this.mDisplay.getSize(this.mCurrentDisplaySize);
    }

    float getDisplayDensity() {
        return this.mDisplayMetrics.density;
    }

    float getDisplayWidth() {
        return this.mDisplayMetrics.widthPixels;
    }

    float getDisplayHeight() {
        return this.mDisplayMetrics.heightPixels;
    }

    int getRotation() {
        return this.mDisplay.getRotation();
    }

    public void startActivityDismissingKeyguard(Intent intent, boolean onlyProvisioned, boolean dismissShade, int flags) {
        startActivityDismissingKeyguard(intent, onlyProvisioned, dismissShade, false, null, flags);
    }

    public void startActivityDismissingKeyguard(Intent intent, boolean onlyProvisioned, boolean dismissShade) {
        startActivityDismissingKeyguard(intent, onlyProvisioned, dismissShade, 0);
    }

    public void startActivityDismissingKeyguard(final Intent intent, boolean onlyProvisioned, boolean dismissShade, final boolean disallowEnterPictureInPictureWhileLaunching, final ActivityStarter.Callback callback, final int flags) {
        if (!onlyProvisioned || isDeviceProvisioned()) {
            boolean afterKeyguardGone = PreviewInflater.wouldLaunchResolverActivity(this.mContext, intent, this.mLockscreenUserManager.getCurrentUserId());
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$0pgfSomqGHz5-5DtRaRMHR9KcUE
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$startActivityDismissingKeyguard$25(this.f$0, intent, flags, disallowEnterPictureInPictureWhileLaunching, callback);
                }
            };
            Runnable cancelRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$sdNCAJTrwZSnVZYaI6pWamsZF7I
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$startActivityDismissingKeyguard$26(callback);
                }
            };
            executeRunnableDismissingKeyguard(runnable, cancelRunnable, dismissShade, afterKeyguardGone, true);
        }
    }

    public static /* synthetic */ void lambda$startActivityDismissingKeyguard$25(StatusBar statusBar, Intent intent, int flags, boolean disallowEnterPictureInPictureWhileLaunching, ActivityStarter.Callback callback) {
        int result;
        statusBar.mAssistManager.hideAssist();
        intent.setFlags(335544320);
        intent.addFlags(flags);
        ActivityOptions options = new ActivityOptions(statusBar.getActivityOptions(null));
        options.setDisallowEnterPictureInPictureWhileLaunching(disallowEnterPictureInPictureWhileLaunching);
        if (intent == KeyguardBottomAreaView.INSECURE_CAMERA_INTENT) {
            options.setRotationAnimationHint(3);
        }
        try {
            try {
                result = ActivityManager.getService().startActivityAsUser((IApplicationThread) null, statusBar.mContext.getBasePackageName(), intent, intent.resolveTypeIfNeeded(statusBar.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, options.toBundle(), UserHandle.CURRENT.getIdentifier());
            } catch (RemoteException e) {
                e = e;
                Log.w("StatusBar", "Unable to start activity", e);
                result = -96;
            }
        } catch (RemoteException e2) {
            e = e2;
        }
        if (callback != null) {
            callback.onActivityStarted(result);
        }
    }

    static /* synthetic */ void lambda$startActivityDismissingKeyguard$26(ActivityStarter.Callback callback) {
        if (callback != null) {
            callback.onActivityStarted(-96);
        }
    }

    public void readyForKeyguardDone() {
        this.mStatusBarKeyguardViewManager.readyForKeyguardDone();
    }

    public void executeRunnableDismissingKeyguard(final Runnable runnable, Runnable cancelAction, final boolean dismissShade, boolean afterKeyguardGone, final boolean deferred) {
        dismissKeyguardThenExecute(new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$bj9kjwz0A60KYJPPWTgllxyLLbE
            @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
            public final boolean onDismiss() {
                return StatusBar.lambda$executeRunnableDismissingKeyguard$27(this.f$0, runnable, dismissShade, deferred);
            }
        }, cancelAction, afterKeyguardGone);
    }

    public static /* synthetic */ boolean lambda$executeRunnableDismissingKeyguard$27(final StatusBar statusBar, Runnable runnable, boolean dismissShade, boolean deferred) {
        if (runnable != null) {
            if (statusBar.mStatusBarKeyguardViewManager.isShowing() && statusBar.mStatusBarKeyguardViewManager.isOccluded()) {
                statusBar.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
            } else {
                AsyncTask.execute(runnable);
            }
        }
        if (dismissShade) {
            if (statusBar.mExpandedVisible) {
                statusBar.animateCollapsePanels(2, true, true);
            } else {
                statusBar.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$h1YVkfulr3o8W-Bsc2YTikmPmYI
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.runPostCollapseRunnables();
                    }
                });
            }
        } else if (statusBar.isInLaunchTransition() && statusBar.mNotificationPanel.isLaunchTransitionFinished()) {
            H h = statusBar.mHandler;
            final StatusBarKeyguardViewManager statusBarKeyguardViewManager = statusBar.mStatusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            h.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$JQMd1r5WuAA5n3kv4yv5u3MFjI8
                @Override // java.lang.Runnable
                public final void run() {
                    statusBarKeyguardViewManager.readyForKeyguardDone();
                }
            });
        }
        return deferred;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAddButtons(int addButtons) {
        int vis = addButtons == 1 ? 0 : 8;
        View mFyScreen = this.mStatusBarView.findViewById(R.id.off_screen);
        if (mFyScreen != null) {
            mFyScreen.setVisibility(vis);
        }
        View mVolumeUp = this.mStatusBarView.findViewById(R.id.volume_up);
        if (mVolumeUp != null) {
            mVolumeUp.setVisibility(vis);
        }
        View mVolumeDown = this.mStatusBarView.findViewById(R.id.volume_down);
        if (mVolumeDown != null) {
            mVolumeDown.setVisibility(vis);
        }
        View mVolumeMute = this.mStatusBarView.findViewById(R.id.volume_mute);
        if (mVolumeMute != null) {
            mVolumeMute.setVisibility(vis);
        }
    }

    public void resetUserExpandedStates() {
        ArrayList<NotificationData.Entry> activeNotifications = this.mEntryManager.getNotificationData().getActiveNotifications();
        int notificationCount = activeNotifications.size();
        for (int i = 0; i < notificationCount; i++) {
            NotificationData.Entry entry = activeNotifications.get(i);
            if (entry.row != null) {
                entry.row.resetUserExpansion();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeWhenUnlocked(KeyguardHostView.OnDismissAction action) {
        if (this.mStatusBarKeyguardViewManager.isShowing()) {
            this.mLeaveOpenOnKeyguardHide = true;
        }
        dismissKeyguardThenExecute(action, null, false);
    }

    protected void dismissKeyguardThenExecute(KeyguardHostView.OnDismissAction action, boolean afterKeyguardGone) {
        dismissKeyguardThenExecute(action, null, afterKeyguardGone);
    }

    private void dismissKeyguardThenExecute(KeyguardHostView.OnDismissAction action, Runnable cancelAction, boolean afterKeyguardGone) {
        if (this.mWakefulnessLifecycle.getWakefulness() == 0 && this.mUnlockMethodCache.canSkipBouncer() && !this.mLeaveOpenOnKeyguardHide && isPulsing()) {
            this.mFingerprintUnlockController.startWakeAndUnlock(2);
        }
        if (this.mStatusBarKeyguardViewManager.isShowing()) {
            this.mStatusBarKeyguardViewManager.dismissWithAction(action, cancelAction, afterKeyguardGone);
        } else {
            action.onDismiss();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration newConfig) {
        updateResources();
        updateDisplaySize();
        if (DEBUG) {
            Log.v("StatusBar", "configuration changed: " + this.mContext.getResources().getConfiguration());
        }
        this.mViewHierarchyManager.updateRowStates();
        this.mScreenPinningRequest.onConfigurationChanged();
    }

    public void onUserSwitched(int newUserId) {
        setHeadsUpUser(newUserId);
        animateCollapsePanels();
        updatePublicMode();
        this.mEntryManager.getNotificationData().filterAndSort();
        if (this.mReinflateNotificationsOnUserSwitched) {
            this.mEntryManager.updateNotificationsOnDensityOrFontScaleChanged();
            this.mReinflateNotificationsOnUserSwitched = false;
        }
        updateNotificationViews();
        this.mMediaManager.clearCurrentMediaNotification();
        setLockscreenUser(newUserId);
    }

    public NotificationLockscreenUserManager getNotificationLockscreenUserManager() {
        return this.mLockscreenUserManager;
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public void onBindRow(NotificationData.Entry entry, PackageManager pmUser, StatusBarNotification sbn, ExpandableNotificationRow row) {
        row.setAboveShelfChangedListener(this.mAboveShelfObserver);
        row.setSecureStateProvider(new BooleanSupplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$Er5d2KQJ-uxw_IoSsj98m9LUD4U
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.isKeyguardCurrentlySecure();
            }
        });
    }

    protected void setLockscreenUser(int newUserId) {
        this.mLockscreenWallpaper.setCurrentUser(newUserId);
        this.mScrimController.setCurrentUser(newUserId);
        updateMediaMetaData(true, false);
    }

    void updateResources() {
        if (this.mQSPanel != null) {
            this.mQSPanel.updateResources();
        }
        loadDimens();
        if (this.mStatusBarView != null) {
            this.mStatusBarView.updateResources();
        }
        if (this.mNotificationPanel != null) {
            this.mNotificationPanel.updateResources();
        }
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.updateResources();
        }
    }

    protected void loadDimens() {
        Resources res = this.mContext.getResources();
        int oldBarHeight = this.mNaturalBarHeight;
        this.mNaturalBarHeight = res.getDimensionPixelSize(android.R.dimen.handwriting_bounds_offset_right);
        if (this.mStatusBarWindowManager != null && this.mNaturalBarHeight != oldBarHeight) {
            this.mStatusBarWindowManager.setBarHeight(this.mNaturalBarHeight);
        }
        this.mMaxAllowedKeyguardNotifications = res.getInteger(R.integer.keyguard_max_notification_count);
        if (DEBUG) {
            Log.v("StatusBar", "defineSlots");
        }
    }

    protected void handleVisibleToUserChanged(boolean visibleToUser) {
        if (visibleToUser) {
            handleVisibleToUserChangedImpl(visibleToUser);
            this.mNotificationLogger.startNotificationLogging();
        } else {
            this.mNotificationLogger.stopNotificationLogging();
            handleVisibleToUserChangedImpl(visibleToUser);
        }
    }

    void handlePeekToExpandTransistion() {
        try {
            int notificationLoad = this.mEntryManager.getNotificationData().getActiveNotifications().size();
            this.mBarService.onPanelRevealed(false, notificationLoad);
        } catch (RemoteException e) {
        }
    }

    private void handleVisibleToUserChangedImpl(boolean visibleToUser) {
        if (visibleToUser) {
            boolean pinnedHeadsUp = this.mHeadsUpManager.hasPinnedHeadsUp();
            final boolean clearNotificationEffects = !isPresenterFullyCollapsed() && (this.mState == 0 || this.mState == 2);
            int notificationLoad = this.mEntryManager.getNotificationData().getActiveNotifications().size();
            if (pinnedHeadsUp && isPresenterFullyCollapsed()) {
                notificationLoad = 1;
            }
            final int finalNotificationLoad = notificationLoad;
            this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$dgx7BY4XCnsqGVWEzjeBwekF2kM
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mBarService.onPanelRevealed(clearNotificationEffects, finalNotificationLoad);
                }
            });
            return;
        }
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$IIjsVBGF5-aJdoMSbYeOByWWpwo
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mBarService.onPanelHidden();
            }
        });
    }

    private void logStateToEventlog() {
        boolean zIsShowing = this.mStatusBarKeyguardViewManager.isShowing();
        boolean zIsOccluded = this.mStatusBarKeyguardViewManager.isOccluded();
        boolean zIsBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        boolean zIsMethodSecure = this.mUnlockMethodCache.isMethodSecure();
        boolean zCanSkipBouncer = this.mUnlockMethodCache.canSkipBouncer();
        int loggingFingerprint = getLoggingFingerprint(this.mState, zIsShowing, zIsOccluded, zIsBouncerShowing, zIsMethodSecure, zCanSkipBouncer);
        if (loggingFingerprint != this.mLastLoggedStateFingerprint) {
            if (this.mStatusBarStateLog == null) {
                this.mStatusBarStateLog = new LogMaker(0);
            }
            this.mMetricsLogger.write(this.mStatusBarStateLog.setCategory(zIsBouncerShowing ? 197 : 196).setType(zIsShowing ? 1 : 2).setSubtype(zIsMethodSecure ? 1 : 0));
            EventLogTags.writeSysuiStatusBarState(this.mState, zIsShowing ? 1 : 0, zIsOccluded ? 1 : 0, zIsBouncerShowing ? 1 : 0, zIsMethodSecure ? 1 : 0, zCanSkipBouncer ? 1 : 0);
            this.mLastLoggedStateFingerprint = loggingFingerprint;
        }
    }

    private static int getLoggingFingerprint(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return (i & 255) | ((z ? 1 : 0) << 8) | ((z2 ? 1 : 0) << 9) | ((z3 ? 1 : 0) << 10) | ((z4 ? 1 : 0) << 11) | ((z5 ? 1 : 0) << 12);
    }

    void vibrate() {
        Vibrator vib = (Vibrator) this.mContext.getSystemService("vibrator");
        vib.vibrate(250L, VIBRATION_ATTRIBUTES);
    }

    public static /* synthetic */ void lambda$new$30(StatusBar statusBar) {
        Debug.stopMethodTracing();
        Log.d("StatusBar", "stopTracing");
        statusBar.vibrate();
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postQSRunnableDismissingKeyguard(final Runnable runnable) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$DbHzbcPYKOzhU4VGsvwDaIv817Q
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$postQSRunnableDismissingKeyguard$32(this.f$0, runnable);
            }
        });
    }

    public static /* synthetic */ void lambda$postQSRunnableDismissingKeyguard$32(final StatusBar statusBar, final Runnable runnable) {
        statusBar.mLeaveOpenOnKeyguardHide = true;
        statusBar.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$MfM6AfVtaUSJMxEfEIIc36W-_j4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mHandler.post(runnable);
            }
        }, null, false, false, false);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(final PendingIntent intent) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$h5ieT1G7V0QiR77bU1CEB-FF690
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startPendingIntentDismissingKeyguard(intent);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(final Intent intent, int delay) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$6t8PmSxKZlI3N8QA7P7vojasPC4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleStartActivityDismissingKeyguard(intent, true);
            }
        }, delay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartActivityDismissingKeyguard(Intent intent, boolean onlyProvisioned) {
        startActivityDismissingKeyguard(intent, onlyProvisioned, true);
    }

    @Override // com.android.systemui.DemoMode
    public void dispatchDemoCommand(String command, Bundle args) {
        int barMode = 1;
        if (!this.mDemoModeAllowed) {
            this.mDemoModeAllowed = Settings.Global.getInt(this.mContext.getContentResolver(), "sysui_demo_allowed", 0) != 0;
        }
        if (this.mDemoModeAllowed) {
            if (command.equals("enter")) {
                this.mDemoMode = true;
            } else if (command.equals("exit")) {
                this.mDemoMode = false;
                checkBarModes();
            } else if (!this.mDemoMode) {
                dispatchDemoCommand("enter", new Bundle());
            }
            boolean modeChange = command.equals("enter") || command.equals("exit");
            if ((modeChange || command.equals("volume")) && this.mVolumeComponent != null) {
                this.mVolumeComponent.dispatchDemoCommand(command, args);
            }
            if (modeChange || command.equals("clock")) {
                dispatchDemoCommandToView(command, args, R.id.clock);
            }
            if (modeChange || command.equals("battery")) {
                this.mBatteryController.dispatchDemoCommand(command, args);
            }
            if (modeChange || command.equals("status")) {
                ((StatusBarIconControllerImpl) this.mIconController).dispatchDemoCommand(command, args);
            }
            if (this.mNetworkController != null && (modeChange || command.equals("network"))) {
                this.mNetworkController.dispatchDemoCommand(command, args);
            }
            if (modeChange || command.equals("notifications")) {
                View notifications = this.mStatusBarView == null ? null : this.mStatusBarView.findViewById(R.id.notification_icon_area);
                if (notifications != null) {
                    String visible = args.getString("visible");
                    int vis = (this.mDemoMode && "false".equals(visible)) ? 4 : 0;
                    notifications.setVisibility(vis);
                }
            }
            if (command.equals("bars")) {
                String mode = args.getString("mode");
                if (!"opaque".equals(mode)) {
                    if ("translucent".equals(mode)) {
                        barMode = 2;
                    } else if (!"semi-transparent".equals(mode)) {
                        if ("transparent".equals(mode)) {
                            barMode = 4;
                        } else {
                            barMode = "warning".equals(mode) ? 5 : -1;
                        }
                    }
                } else {
                    barMode = 0;
                }
                if (barMode != -1) {
                    if (this.mStatusBarView != null) {
                        this.mStatusBarView.getBarTransitions().transitionTo(barMode, true);
                    }
                    if (this.mNavigationBar != null) {
                        this.mNavigationBar.getBarTransitions().transitionTo(barMode, true);
                    }
                }
            }
            if (modeChange || command.equals("operator")) {
                dispatchDemoCommandToView(command, args, R.id.operator_name);
            }
        }
    }

    private void dispatchDemoCommandToView(String command, Bundle args, int id) {
        if (this.mStatusBarView == null) {
            return;
        }
        KeyEvent.Callback callbackFindViewById = this.mStatusBarView.findViewById(id);
        if (callbackFindViewById instanceof DemoMode) {
            ((DemoMode) callbackFindViewById).dispatchDemoCommand(command, args);
        }
    }

    public int getBarState() {
        return this.mState;
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isPresenterFullyCollapsed() {
        return this.mNotificationPanel.isFullyCollapsed();
    }

    public void showKeyguard() {
        this.mKeyguardRequested = true;
        this.mLeaveOpenOnKeyguardHide = false;
        this.mPendingRemoteInputView = null;
        updateIsKeyguard();
        this.mAssistManager.onLockscreenShown();
    }

    public boolean hideKeyguard() {
        ContentResolver cr = this.mContext.getContentResolver();
        String pin = Settings.System.getString(cr, "lockscreen_unlock_pin");
        if (pin != null && !pin.isEmpty()) {
            View contents = this.mStatusBarView.findViewById(R.id.status_bar_contents);
            if (contents != null) {
                contents.setVisibility(0);
            }
            if (this.mStatusBarView != null) {
                this.mStatusBarView.setVisibility(0);
            }
            try {
                boolean showNav = this.mWindowManagerService.hasNavigationBar();
                if (DEBUG) {
                    Log.v("StatusBar", "hasNavigationBar=" + showNav);
                }
                if (showNav) {
                    createNavigationBar();
                }
            } catch (RemoteException e) {
            }
        }
        this.mKeyguardRequested = false;
        return updateIsKeyguard();
    }

    public boolean isFullScreenUserSwitcherState() {
        return this.mState == 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateIsKeyguard() {
        boolean wakeAndUnlocking = this.mFingerprintUnlockController.getMode() == 1;
        boolean keyguardForDozing = this.mDozingRequested && (!this.mDeviceInteractive || (isGoingToSleep() && (isScreenFullyOff() || this.mIsKeyguard)));
        boolean shouldBeKeyguard = (this.mKeyguardRequested || keyguardForDozing) && !wakeAndUnlocking;
        if (keyguardForDozing) {
            updatePanelExpansionForKeyguard();
        }
        if (shouldBeKeyguard) {
            if (!isGoingToSleep() || this.mScreenLifecycle.getScreenState() != 3) {
                showKeyguardImpl();
            }
            return false;
        }
        return hideKeyguardImpl();
    }

    public void showKeyguardImpl() {
        this.mIsKeyguard = true;
        if (this.mLaunchTransitionFadingAway) {
            this.mNotificationPanel.animate().cancel();
            onLaunchTransitionFadingEnded();
        }
        this.mHandler.removeMessages(1003);
        if (this.mUserSwitcherController != null && this.mUserSwitcherController.useFullscreenUserSwitcher()) {
            setBarState(3);
        } else {
            setBarState(1);
        }
        updateKeyguardState(false, false);
        updatePanelExpansionForKeyguard();
        if (this.mDraggedDownRow != null) {
            this.mDraggedDownRow.setUserLocked(false);
            this.mDraggedDownRow.notifyHeightChanged(false);
            this.mDraggedDownRow = null;
        }
        if (this.mStatusBarView != null) {
            this.mStatusBarView.setVisibility(4);
        }
        if (this.mNavigationBarView != null) {
            this.mWindowManager.removeViewImmediate(this.mNavigationBarView);
            this.mNavigationBarView = null;
        }
        instantCollapseNotificationPanel();
    }

    private void updatePanelExpansionForKeyguard() {
        if (this.mState == 1 && this.mFingerprintUnlockController.getMode() != 1) {
            instantExpandNotificationsPanel();
        } else if (this.mState == 3) {
            instantCollapseNotificationPanel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLaunchTransitionFadingEnded() {
        this.mNotificationPanel.setAlpha(1.0f);
        this.mNotificationPanel.onAffordanceLaunchEnded();
        releaseGestureWakeLock();
        runLaunchTransitionEndRunnable();
        this.mLaunchTransitionFadingAway = false;
        updateMediaMetaData(true, true);
    }

    public boolean isCollapsing() {
        return this.mNotificationPanel.isCollapsing() || this.mActivityLaunchAnimator.isAnimationPending();
    }

    public void addPostCollapseAction(Runnable r) {
        this.mPostCollapseRunnables.add(r);
    }

    public boolean isInLaunchTransition() {
        return this.mNotificationPanel.isLaunchTransitionRunning() || this.mNotificationPanel.isLaunchTransitionFinished();
    }

    public void fadeKeyguardAfterLaunchTransition(final Runnable beforeFading, Runnable endRunnable) {
        this.mHandler.removeMessages(1003);
        this.mLaunchTransitionEndRunnable = endRunnable;
        Runnable hideRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$tMqEv0GW0xlxzgaW8Oin80LEqC8
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$fadeKeyguardAfterLaunchTransition$35(this.f$0, beforeFading);
            }
        };
        if (this.mNotificationPanel.isLaunchTransitionRunning()) {
            this.mNotificationPanel.setLaunchTransitionEndRunnable(hideRunnable);
        } else {
            hideRunnable.run();
        }
    }

    public static /* synthetic */ void lambda$fadeKeyguardAfterLaunchTransition$35(final StatusBar statusBar, Runnable beforeFading) {
        statusBar.mLaunchTransitionFadingAway = true;
        if (beforeFading != null) {
            beforeFading.run();
        }
        statusBar.updateScrimController();
        statusBar.updateMediaMetaData(false, true);
        statusBar.mNotificationPanel.setAlpha(1.0f);
        statusBar.mStackScroller.setParentNotFullyVisible(true);
        statusBar.mNotificationPanel.animate().alpha(0.0f).setStartDelay(100L).setDuration(300L).withLayer().withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$GDSEpzokV1v2-uNGuP8V5K9Jrjw
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onLaunchTransitionFadingEnded();
            }
        });
        statusBar.mCommandQueue.appTransitionStarting(SystemClock.uptimeMillis(), 120L, true);
    }

    public void fadeKeyguardWhilePulsing() {
        this.mNotificationPanel.notifyStartFading();
        this.mNotificationPanel.animate().alpha(0.0f).setStartDelay(0L).setDuration(96L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$cC8dYfurInJrREmSng5-MS15e3s
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$fadeKeyguardWhilePulsing$36(this.f$0);
            }
        }).start();
    }

    public static /* synthetic */ void lambda$fadeKeyguardWhilePulsing$36(StatusBar statusBar) {
        statusBar.hideKeyguard();
        statusBar.mStatusBarKeyguardViewManager.onKeyguardFadedAway();
    }

    public void animateKeyguardUnoccluding() {
        this.mNotificationPanel.setExpandedFraction(0.0f);
        animateExpandNotificationsPanel();
    }

    public void startLaunchTransitionTimeout() {
        this.mHandler.sendEmptyMessageDelayed(1003, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLaunchTransitionTimeout() {
        Log.w("StatusBar", "Launch transition: Timeout!");
        this.mNotificationPanel.onAffordanceLaunchEnded();
        releaseGestureWakeLock();
        this.mNotificationPanel.resetViews();
        this.mStatusBarKeyguardViewManager.reset(true);
    }

    private void runLaunchTransitionEndRunnable() {
        if (this.mLaunchTransitionEndRunnable != null) {
            Runnable r = this.mLaunchTransitionEndRunnable;
            this.mLaunchTransitionEndRunnable = null;
            r.run();
        }
    }

    public boolean hideKeyguardImpl() {
        this.mIsKeyguard = false;
        Trace.beginSection("StatusBar#hideKeyguard");
        boolean staying = this.mLeaveOpenOnKeyguardHide;
        setBarState(0);
        View viewToClick = null;
        if (this.mLeaveOpenOnKeyguardHide) {
            if (!this.mKeyguardRequested) {
                this.mLeaveOpenOnKeyguardHide = false;
            }
            long delay = calculateGoingToFullShadeDelay();
            this.mNotificationPanel.animateToFullShade(delay);
            if (this.mDraggedDownRow != null) {
                this.mDraggedDownRow.setUserLocked(false);
                this.mDraggedDownRow = null;
            }
            if (!this.mKeyguardRequested) {
                viewToClick = this.mPendingRemoteInputView;
                this.mPendingRemoteInputView = null;
            }
            if (this.mNavigationBar != null) {
                this.mNavigationBar.disableAnimationsDuringHide(delay);
            }
        } else if (!this.mNotificationPanel.isCollapsing()) {
            instantCollapseNotificationPanel();
        }
        updateKeyguardState(staying, false);
        if (viewToClick != null && viewToClick.isAttachedToWindow()) {
            viewToClick.callOnClick();
        }
        if (this.mQSPanel != null) {
            this.mQSPanel.refreshAllTiles();
        }
        this.mHandler.removeMessages(1003);
        releaseGestureWakeLock();
        this.mNotificationPanel.onAffordanceLaunchEnded();
        this.mNotificationPanel.animate().cancel();
        this.mNotificationPanel.setAlpha(1.0f);
        Trace.endSection();
        return staying;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseGestureWakeLock() {
        if (this.mGestureWakeLock.isHeld()) {
            this.mGestureWakeLock.release();
        }
    }

    public long calculateGoingToFullShadeDelay() {
        return this.mKeyguardFadingAwayDelay + this.mKeyguardFadingAwayDuration;
    }

    public void keyguardGoingAway() {
        this.mKeyguardMonitor.notifyKeyguardGoingAway(true);
        this.mCommandQueue.appTransitionPending(true);
    }

    public void setKeyguardFadingAway(long startTime, long delay, long fadeoutDuration) {
        this.mKeyguardFadingAway = true;
        this.mKeyguardFadingAwayDelay = delay;
        this.mKeyguardFadingAwayDuration = fadeoutDuration;
        this.mCommandQueue.appTransitionStarting((startTime + fadeoutDuration) - 120, 120L, true);
        recomputeDisableFlags(fadeoutDuration > 0);
        this.mCommandQueue.appTransitionStarting(startTime - 120, 120L, true);
        this.mKeyguardMonitor.notifyKeyguardFadingAway(delay, fadeoutDuration);
    }

    public boolean isKeyguardFadingAway() {
        return this.mKeyguardFadingAway;
    }

    public void finishKeyguardFadingAway() {
        this.mKeyguardFadingAway = false;
        this.mKeyguardMonitor.notifyKeyguardDoneFading();
        this.mScrimController.setExpansionAffectsAlpha(true);
    }

    private void updatePublicMode() {
        boolean showingKeyguard = this.mStatusBarKeyguardViewManager.isShowing();
        boolean devicePublic = showingKeyguard && this.mStatusBarKeyguardViewManager.isSecure(this.mLockscreenUserManager.getCurrentUserId());
        SparseArray<UserInfo> currentProfiles = this.mLockscreenUserManager.getCurrentProfiles();
        int userId = currentProfiles.size() - 1;
        while (true) {
            int i = userId;
            if (i >= 0) {
                int userId2 = currentProfiles.valueAt(i).id;
                boolean isProfilePublic = devicePublic;
                if (!devicePublic && userId2 != this.mLockscreenUserManager.getCurrentUserId() && this.mLockPatternUtils.isSeparateProfileChallengeEnabled(userId2) && this.mStatusBarKeyguardViewManager.isSecure(userId2)) {
                    isProfilePublic = this.mKeyguardManager.isDeviceLocked(userId2);
                }
                this.mLockscreenUserManager.setLockscreenPublicMode(isProfilePublic, userId2);
                userId = i - 1;
            } else {
                return;
            }
        }
    }

    protected void updateKeyguardState(boolean goingToFullShade, boolean fromShadeLocked) {
        Trace.beginSection("StatusBar#updateKeyguardState");
        if (this.mState == 1) {
            this.mKeyguardIndicationController.setVisible(true);
            this.mNotificationPanel.resetViews();
            if (this.mKeyguardUserSwitcher != null) {
                this.mKeyguardUserSwitcher.setKeyguard(true, fromShadeLocked);
            }
            if (this.mStatusBarView != null) {
                this.mStatusBarView.removePendingHideExpandedRunnables();
            }
            if (this.mAmbientIndicationContainer != null) {
                this.mAmbientIndicationContainer.setVisibility(0);
            }
        } else {
            this.mKeyguardIndicationController.setVisible(false);
            if (this.mKeyguardUserSwitcher != null) {
                this.mKeyguardUserSwitcher.setKeyguard(false, goingToFullShade || this.mState == 2 || fromShadeLocked);
            }
            if (this.mAmbientIndicationContainer != null) {
                this.mAmbientIndicationContainer.setVisibility(4);
            }
        }
        this.mNotificationPanel.setBarState(this.mState, this.mKeyguardFadingAway, goingToFullShade);
        updateTheme();
        updateDozingState();
        updatePublicMode();
        updateStackScrollerState(goingToFullShade, fromShadeLocked);
        this.mEntryManager.updateNotifications();
        checkBarModes();
        updateCarrierLabelVisibility(false);
        updateScrimController();
        updateMediaMetaData(false, this.mState != 1);
        this.mKeyguardMonitor.notifyKeyguardState(this.mStatusBarKeyguardViewManager.isShowing(), this.mUnlockMethodCache.isMethodSecure(), this.mStatusBarKeyguardViewManager.isOccluded());
        Trace.endSection();
    }

    protected void updateTheme() {
        int which;
        boolean useDarkTheme = false;
        boolean inflated = this.mStackScroller != null;
        if (this.mOverlayManager != null) {
            WallpaperColors systemColors = this.mColorExtractor.getWallpaperColors(1);
            if (systemColors != null && (systemColors.getColorHints() & 2) != 0) {
                useDarkTheme = true;
            }
            if (isUsingDarkTheme() != useDarkTheme) {
                this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$HtfG3KRaqdAvtYhfxZgqXV4vBDg
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBar.lambda$updateTheme$37(this.f$0);
                    }
                });
            }
        }
        boolean lockDarkText = this.mColorExtractor.getColors(2, true).supportsDarkText();
        int themeResId = lockDarkText ? 2131886648 : 2131886644;
        if (this.mContext.getThemeResId() != themeResId) {
            this.mContext.setTheme(themeResId);
            if (inflated) {
                onThemeChanged();
            }
        }
        if (inflated) {
            if (this.mState == 1 || this.mState == 2) {
                which = 2;
            } else {
                which = 1;
            }
            boolean useDarkText = this.mColorExtractor.getColors(which, true).supportsDarkText();
            this.mStackScroller.updateDecorViews(useDarkText);
            this.mStatusBarWindowManager.setKeyguardDark(useDarkText);
        }
    }

    public static /* synthetic */ void lambda$updateTheme$37(StatusBar statusBar) {
        try {
            statusBar.mOverlayManager.setEnabled("com.android.systemui.theme.dark", false, statusBar.mLockscreenUserManager.getCurrentUserId());
        } catch (RemoteException e) {
            Log.w("StatusBar", "Can't change theme", e);
        }
    }

    private void updateDozingState() {
        Trace.traceCounter(4096L, "dozing", this.mDozing ? 1 : 0);
        Trace.beginSection("StatusBar#updateDozingState");
        boolean z = (!this.mDozing && this.mDozeServiceHost.shouldAnimateWakeup()) || (this.mDozing && this.mDozeServiceHost.shouldAnimateScreenOff() && this.mStatusBarKeyguardViewManager.isGoingToSleepVisibleNotOccluded());
        this.mStackScroller.setDark(this.mDozing, z, this.mWakeUpTouchLocation);
        this.mDozeScrimController.setDozing(this.mDozing);
        this.mKeyguardIndicationController.setDozing(this.mDozing);
        this.mNotificationPanel.setDozing(this.mDozing, z);
        updateQsExpansionEnabled();
        Trace.endSection();
    }

    public void updateStackScrollerState(boolean goingToFullShade, boolean fromShadeLocked) {
        if (this.mStackScroller == null) {
            return;
        }
        boolean onKeyguard = this.mState == 1;
        boolean publicMode = this.mLockscreenUserManager.isAnyProfilePublicMode();
        if (this.mHeadsUpAppearanceController != null) {
            this.mHeadsUpAppearanceController.setPublicMode(publicMode);
        }
        this.mStackScroller.setHideSensitive(publicMode, goingToFullShade);
        this.mStackScroller.setDimmed(onKeyguard, fromShadeLocked);
        this.mStackScroller.setExpandingEnabled(onKeyguard ? false : true);
        ActivatableNotificationView activatedChild = this.mStackScroller.getActivatedChild();
        this.mStackScroller.setActivatedChild(null);
        if (activatedChild != null) {
            activatedChild.makeInactive(false);
        }
    }

    public void userActivity() {
        if (this.mState == 1) {
            this.mKeyguardViewMediatorCallback.userActivity();
        }
    }

    public boolean interceptMediaKey(KeyEvent event) {
        return this.mState == 1 && this.mStatusBarKeyguardViewManager.interceptMediaKey(event);
    }

    protected boolean shouldUnlockOnMenuPressed() {
        return this.mDeviceInteractive && this.mState != 0 && this.mStatusBarKeyguardViewManager.shouldDismissOnMenuPressed();
    }

    public boolean onMenuPressed() {
        if (shouldUnlockOnMenuPressed()) {
            animateCollapsePanels(2, true);
            return true;
        }
        return false;
    }

    public void endAffordanceLaunch() {
        releaseGestureWakeLock();
        this.mNotificationPanel.onAffordanceLaunchEnded();
    }

    public boolean onBackPressed() {
        boolean isScrimmedBouncer = this.mScrimController.getState() == ScrimState.BOUNCER_SCRIMMED;
        if (this.mStatusBarKeyguardViewManager.onBackPressed(isScrimmedBouncer)) {
            if (!isScrimmedBouncer) {
                this.mNotificationPanel.expandWithoutQs();
            }
            return true;
        }
        if (this.mNotificationPanel.isQsExpanded()) {
            if (this.mNotificationPanel.isQsDetailShowing()) {
                this.mNotificationPanel.closeQsDetail();
            } else {
                this.mNotificationPanel.animateCloseQs();
            }
            return true;
        }
        if (this.mState == 1 || this.mState == 2) {
            return this.mKeyguardUserSwitcher != null && this.mKeyguardUserSwitcher.hideIfNotSimple(true);
        }
        animateCollapsePanels();
        return true;
    }

    public boolean onSpacePressed() {
        if (this.mDeviceInteractive && this.mState != 0) {
            animateCollapsePanels(2, true);
            return true;
        }
        return false;
    }

    private void showBouncerIfKeyguard() {
        if ((this.mState == 1 || this.mState == 2) && !this.mKeyguardViewMediator.isHiding()) {
            showBouncer(true);
        }
    }

    protected void showBouncer(boolean scrimmed) {
        this.mStatusBarKeyguardViewManager.showBouncer(scrimmed);
    }

    private void instantExpandNotificationsPanel() {
        makeExpandedVisible(true);
        this.mNotificationPanel.expand(false);
        recomputeDisableFlags(false);
    }

    private void instantCollapseNotificationPanel() {
        this.mNotificationPanel.instantCollapse();
        runPostCollapseRunnables();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView.OnActivatedListener
    public void onActivated(ActivatableNotificationView view) {
        onActivated((View) view);
        this.mStackScroller.setActivatedChild(view);
    }

    public void onActivated(View view) {
        this.mLockscreenGestureLogger.write(192, 0, 0);
        this.mKeyguardIndicationController.showTransientIndication(R.string.notification_tap_again);
        ActivatableNotificationView previousView = this.mStackScroller.getActivatedChild();
        if (previousView != null) {
            previousView.makeInactive(true);
        }
    }

    public void setBarState(int state) {
        if (state != this.mState && this.mVisible && (state == 2 || (state == 0 && isGoingToNotificationShade()))) {
            clearNotificationEffects();
        }
        if (state == 1) {
            this.mRemoteInputManager.removeRemoteInputEntriesKeptUntilCollapsed();
            maybeEscalateHeadsUp();
        }
        this.mState = state;
        this.mGroupManager.setStatusBarState(state);
        this.mHeadsUpManager.setStatusBarState(state);
        this.mFalsingManager.setStatusBarState(state);
        this.mStatusBarWindowManager.setStatusBarState(state);
        this.mStackScroller.setStatusBarState(state);
        updateReportRejectedTouchVisibility();
        updateDozing();
        updateTheme();
        touchAutoDim();
        this.mNotificationShelf.setStatusBarState(state);
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView.OnActivatedListener
    public void onActivationReset(ActivatableNotificationView view) {
        if (view == this.mStackScroller.getActivatedChild()) {
            this.mStackScroller.setActivatedChild(null);
            onActivationReset((View) view);
        }
    }

    public void onActivationReset(View view) {
        this.mKeyguardIndicationController.hideTransientIndication();
    }

    public void onTrackingStarted() {
        runPostCollapseRunnables();
    }

    public void onClosingFinished() {
        runPostCollapseRunnables();
        if (!isPresenterFullyCollapsed()) {
            this.mStatusBarWindowManager.setStatusBarFocusable(true);
        }
    }

    public void onUnlockHintStarted() {
        this.mFalsingManager.onUnlockHintStarted();
        this.mKeyguardIndicationController.showTransientIndication(R.string.keyguard_unlock);
    }

    public void onHintFinished() {
        this.mKeyguardIndicationController.hideTransientIndicationDelayed(1200L);
    }

    public void onCameraHintStarted() {
        this.mFalsingManager.onCameraHintStarted();
        this.mKeyguardIndicationController.showTransientIndication(R.string.camera_hint);
    }

    public void onVoiceAssistHintStarted() {
        this.mFalsingManager.onLeftAffordanceHintStarted();
        this.mKeyguardIndicationController.showTransientIndication(R.string.voice_hint);
    }

    public void onPhoneHintStarted() {
        this.mFalsingManager.onLeftAffordanceHintStarted();
        this.mKeyguardIndicationController.showTransientIndication(R.string.phone_hint);
    }

    public void onTrackingStopped(boolean expand) {
        if ((this.mState == 1 || this.mState == 2) && !expand && !this.mUnlockMethodCache.canSkipBouncer()) {
            showBouncer(false);
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public int getMaxNotificationsWhileLocked(boolean recompute) {
        if (recompute) {
            this.mMaxKeyguardNotifications = Math.max(1, this.mNotificationPanel.computeMaxKeyguardNotifications(this.mMaxAllowedKeyguardNotifications));
            return this.mMaxKeyguardNotifications;
        }
        return this.mMaxKeyguardNotifications;
    }

    public NavigationBarView getNavigationBarView() {
        if (this.mNavigationBar != null) {
            return (NavigationBarView) this.mNavigationBar.getView();
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.DragDownHelper.DragDownCallback
    public boolean onDraggedDown(View startingChild, int dragLengthY) {
        if (this.mState != 1 || !hasActiveNotifications() || (isDozing() && !isPulsing())) {
            return false;
        }
        this.mLockscreenGestureLogger.write(187, (int) (dragLengthY / this.mDisplayMetrics.density), 0);
        goToLockedShade(startingChild);
        if (startingChild instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) startingChild;
            row.onExpandedByGesture(true);
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.DragDownHelper.DragDownCallback
    public void onDragDownReset() {
        this.mStackScroller.setDimmed(true, true);
        this.mStackScroller.resetScrollPosition();
        this.mStackScroller.resetCheckSnoozeLeavebehind();
    }

    @Override // com.android.systemui.statusbar.DragDownHelper.DragDownCallback
    public void onCrossedThreshold(boolean above) {
        this.mStackScroller.setDimmed(!above, true);
    }

    @Override // com.android.systemui.statusbar.DragDownHelper.DragDownCallback
    public void onTouchSlopExceeded() {
        this.mStackScroller.cancelLongPress();
        this.mStackScroller.checkSnoozeLeavebehind();
    }

    @Override // com.android.systemui.statusbar.DragDownHelper.DragDownCallback
    public void setEmptyDragAmount(float amount) {
        this.mNotificationPanel.setEmptyDragAmount(amount);
    }

    @Override // com.android.systemui.statusbar.DragDownHelper.DragDownCallback
    public boolean isFalsingCheckNeeded() {
        return this.mState == 1;
    }

    public void goToLockedShade(View expandView) {
        if ((this.mDisabled2 & 4) != 0) {
            return;
        }
        int userId = this.mLockscreenUserManager.getCurrentUserId();
        ExpandableNotificationRow row = null;
        if (expandView instanceof ExpandableNotificationRow) {
            row = (ExpandableNotificationRow) expandView;
            row.setUserExpanded(true, true);
            row.setGroupExpansionChanging(true);
            if (row.getStatusBarNotification() != null) {
                userId = row.getStatusBarNotification().getUserId();
            }
        }
        boolean fullShadeNeedsBouncer = (this.mLockscreenUserManager.userAllowsPrivateNotificationsInPublic(this.mLockscreenUserManager.getCurrentUserId()) && this.mLockscreenUserManager.shouldShowLockscreenNotifications() && !this.mFalsingManager.shouldEnforceBouncer()) ? false : true;
        if (this.mLockscreenUserManager.isLockscreenPublicMode(userId) && fullShadeNeedsBouncer) {
            this.mLeaveOpenOnKeyguardHide = true;
            showBouncerIfKeyguard();
            this.mDraggedDownRow = row;
            this.mPendingRemoteInputView = null;
            return;
        }
        this.mNotificationPanel.animateToFullShade(0L);
        setBarState(2);
        updateKeyguardState(false, false);
    }

    public void onLockedNotificationImportanceChange(KeyguardHostView.OnDismissAction dismissAction) {
        this.mLeaveOpenOnKeyguardHide = true;
        dismissKeyguardThenExecute(dismissAction, true);
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public void onLockedRemoteInput(ExpandableNotificationRow row, View clicked) {
        this.mLeaveOpenOnKeyguardHide = true;
        showBouncer(true);
        this.mPendingRemoteInputView = clicked;
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public void onMakeExpandedVisibleForRemoteInput(ExpandableNotificationRow row, final View clickedView) {
        if (isKeyguardShowing()) {
            onLockedRemoteInput(row, clickedView);
            return;
        }
        row.setUserExpanded(true);
        NotificationContentView privateLayout = row.getPrivateLayout();
        Objects.requireNonNull(clickedView);
        privateLayout.setOnExpandedVisibleListener(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$MVkYf3B-uVxXy7rxrXvHR4SUXEU
            @Override // java.lang.Runnable
            public final void run() {
                clickedView.performClick();
            }
        });
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public boolean shouldHandleRemoteInput(View view, PendingIntent pendingIntent) {
        return (this.mDisabled2 & 4) != 0;
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public boolean handleRemoteViewClick(View view, PendingIntent pendingIntent, Intent fillInIntent, final NotificationRemoteInputManager.ClickHandler defaultHandler) {
        boolean isActivity = pendingIntent.isActivity();
        if (isActivity) {
            boolean afterKeyguardGone = PreviewInflater.wouldLaunchResolverActivity(this.mContext, pendingIntent.getIntent(), this.mLockscreenUserManager.getCurrentUserId());
            dismissKeyguardThenExecute(new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$JPBy4IfK0tFqqFzxhrEUhntTwTI
                @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
                public final boolean onDismiss() {
                    return StatusBar.lambda$handleRemoteViewClick$38(this.f$0, defaultHandler);
                }
            }, afterKeyguardGone);
            return true;
        }
        boolean afterKeyguardGone2 = defaultHandler.handleClick();
        return afterKeyguardGone2;
    }

    public static /* synthetic */ boolean lambda$handleRemoteViewClick$38(StatusBar statusBar, NotificationRemoteInputManager.ClickHandler defaultHandler) {
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException e) {
        }
        boolean handled = defaultHandler.handleClick();
        if (!handled || statusBar.mNotificationPanel.isFullyCollapsed()) {
            return false;
        }
        statusBar.animateCollapsePanels(2, true);
        statusBar.visibilityChanged(false);
        statusBar.mAssistManager.hideAssist();
        return true;
    }

    protected boolean startWorkChallengeIfNecessary(int userId, IntentSender intendSender, String notificationKey) {
        this.mPendingWorkRemoteInputView = null;
        Intent newIntent = this.mKeyguardManager.createConfirmDeviceCredentialIntent(null, null, userId);
        if (newIntent == null) {
            return false;
        }
        Intent callBackIntent = new Intent("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        callBackIntent.putExtra("android.intent.extra.INTENT", intendSender);
        callBackIntent.putExtra("android.intent.extra.INDEX", notificationKey);
        callBackIntent.setPackage(this.mContext.getPackageName());
        PendingIntent callBackPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, callBackIntent, 1409286144);
        newIntent.putExtra("android.intent.extra.INTENT", callBackPendingIntent.getIntentSender());
        try {
            ActivityManager.getService().startConfirmDeviceCredentialIntent(newIntent, (Bundle) null);
            return true;
        } catch (RemoteException e) {
            return true;
        }
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public void onLockedWorkRemoteInput(int userId, ExpandableNotificationRow row, View clicked) {
        animateCollapsePanels();
        startWorkChallengeIfNecessary(userId, null, null);
        this.mPendingWorkRemoteInputView = clicked;
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void onWorkChallengeChanged() {
        updatePublicMode();
        this.mEntryManager.updateNotifications();
        if (this.mPendingWorkRemoteInputView != null && !this.mLockscreenUserManager.isAnyProfilePublicMode()) {
            final Runnable clickPendingViewRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$JNDJSSt19YCqUkF3G0i6kFmkI2c
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$onWorkChallengeChanged$41(this.f$0);
                }
            };
            this.mNotificationPanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.statusbar.phone.StatusBar.21
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (StatusBar.this.mNotificationPanel.mStatusBar.getStatusBarWindow().getHeight() != StatusBar.this.mNotificationPanel.mStatusBar.getStatusBarHeight()) {
                        StatusBar.this.mNotificationPanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        StatusBar.this.mNotificationPanel.post(clickPendingViewRunnable);
                    }
                }
            });
            instantExpandNotificationsPanel();
        }
    }

    public static /* synthetic */ void lambda$onWorkChallengeChanged$41(final StatusBar statusBar) {
        View pendingWorkRemoteInputView = statusBar.mPendingWorkRemoteInputView;
        if (pendingWorkRemoteInputView == null) {
            return;
        }
        ViewParent p = pendingWorkRemoteInputView.getParent();
        while (!(p instanceof ExpandableNotificationRow)) {
            if (p == null) {
                return;
            } else {
                p = p.getParent();
            }
        }
        final ExpandableNotificationRow row = (ExpandableNotificationRow) p;
        ViewParent viewParent = row.getParent();
        if (viewParent instanceof NotificationStackScrollLayout) {
            final NotificationStackScrollLayout scrollLayout = (NotificationStackScrollLayout) viewParent;
            row.makeActionsVisibile();
            row.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$MNz2fPdL8lBCD7Ieal34T_hUT7A
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBar.lambda$onWorkChallengeChanged$40(this.f$0, scrollLayout, row);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$onWorkChallengeChanged$40(final StatusBar statusBar, final NotificationStackScrollLayout scrollLayout, ExpandableNotificationRow row) {
        Runnable finishScrollingCallback = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$RZVvPju9DtgQO-iKIWj758BC1qM
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$onWorkChallengeChanged$39(this.f$0, scrollLayout);
            }
        };
        if (scrollLayout.scrollTo(row)) {
            scrollLayout.setFinishScrollingCallback(finishScrollingCallback);
        } else {
            finishScrollingCallback.run();
        }
    }

    public static /* synthetic */ void lambda$onWorkChallengeChanged$39(StatusBar statusBar, NotificationStackScrollLayout scrollLayout) {
        statusBar.mPendingWorkRemoteInputView.callOnClick();
        statusBar.mPendingWorkRemoteInputView = null;
        scrollLayout.setFinishScrollingCallback(null);
    }

    @Override // com.android.systemui.statusbar.ExpandableNotificationRow.OnExpandClickListener
    public void onExpandClicked(NotificationData.Entry clickedEntry, boolean nowExpanded) {
        this.mHeadsUpManager.setExpanded(clickedEntry, nowExpanded);
        if (this.mState == 1 && nowExpanded) {
            goToLockedShade(clickedEntry.row);
        }
    }

    public void goToKeyguard() {
        if (this.mState == 2) {
            this.mStackScroller.onGoToKeyguard();
            setBarState(1);
            updateKeyguardState(false, true);
        }
    }

    public long getKeyguardFadingAwayDelay() {
        return this.mKeyguardFadingAwayDelay;
    }

    public long getKeyguardFadingAwayDuration() {
        return this.mKeyguardFadingAwayDuration;
    }

    public void setBouncerShowing(boolean bouncerShowing) {
        this.mBouncerShowing = bouncerShowing;
        if (this.mStatusBarView != null) {
            this.mStatusBarView.setBouncerShowing(bouncerShowing);
        }
        updateHideIconsForBouncer(true);
        recomputeDisableFlags(true);
        updateScrimController();
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.StatusBar$22, reason: invalid class name */
    class AnonymousClass22 implements WakefulnessLifecycle.Observer {
        AnonymousClass22() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onFinishedGoingToSleep() {
            StatusBar.this.mNotificationPanel.onAffordanceLaunchEnded();
            StatusBar.this.releaseGestureWakeLock();
            StatusBar.this.mLaunchCameraOnScreenTurningOn = false;
            StatusBar.this.mDeviceInteractive = false;
            StatusBar.this.mWakeUpComingFromTouch = false;
            StatusBar.this.mWakeUpTouchLocation = null;
            StatusBar.this.mStackScroller.setAnimationsEnabled(false);
            StatusBar.this.mVisualStabilityManager.setScreenOn(false);
            StatusBar.this.updateVisibleToUser();
            StatusBar.this.mNotificationPanel.setTouchDisabled(true);
            StatusBar.this.mStatusBarWindow.cancelCurrentTouch();
            if (StatusBar.this.mLaunchCameraOnFinishedGoingToSleep) {
                StatusBar.this.mLaunchCameraOnFinishedGoingToSleep = false;
                StatusBar.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$22$wYaLkBq_B8CfTwxXPn1P0M9-WvQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBar.AnonymousClass22 anonymousClass22 = this.f$0;
                        StatusBar.this.onCameraLaunchGestureDetected(StatusBar.this.mLastCameraLaunchSource);
                    }
                });
            }
            StatusBar.this.updateIsKeyguard();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onStartedGoingToSleep() {
            StatusBar.this.notifyHeadsUpGoingToSleep();
            StatusBar.this.dismissVolumeDialog();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onStartedWakingUp() {
            StatusBar.this.mDeviceInteractive = true;
            StatusBar.this.mStackScroller.setAnimationsEnabled(true);
            StatusBar.this.mVisualStabilityManager.setScreenOn(true);
            StatusBar.this.mNotificationPanel.setTouchDisabled(false);
            StatusBar.this.mDozeServiceHost.stopDozing();
            StatusBar.this.updateVisibleToUser();
            StatusBar.this.updateIsKeyguard();
            StatusBar.this.updateScrimController();
        }
    }

    public int getWakefulnessState() {
        return this.mWakefulnessLifecycle.getWakefulness();
    }

    private void vibrateForCameraGesture() {
        this.mVibrator.vibrate(this.mCameraLaunchGestureVibePattern, -1);
    }

    public boolean isScreenFullyOff() {
        return this.mScreenLifecycle.getScreenState() == 0;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showScreenPinningRequest(int taskId) {
        if (this.mKeyguardMonitor.isShowing()) {
            return;
        }
        showScreenPinningRequest(taskId, true);
    }

    public void showScreenPinningRequest(int taskId, boolean allowCancel) {
        this.mScreenPinningRequest.showPrompt(taskId, allowCancel);
    }

    public boolean hasActiveNotifications() {
        return !this.mEntryManager.getNotificationData().getActiveNotifications().isEmpty();
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void wakeUpIfDozing(long time, View where) {
        if (this.mDozing) {
            PowerManager pm = (PowerManager) this.mContext.getSystemService("power");
            pm.wakeUp(time, "com.android.systemui:NODOZE");
            this.mWakeUpComingFromTouch = true;
            where.getLocationInWindow(this.mTmpInt2);
            this.mWakeUpTouchLocation = new PointF(this.mTmpInt2[0] + (where.getWidth() / 2), this.mTmpInt2[1] + (where.getHeight() / 2));
            this.mStatusBarKeyguardViewManager.notifyDeviceWakeUpRequested();
            this.mFalsingManager.onScreenOnFromTouch();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isDeviceLocked(int userId) {
        return this.mKeyguardManager.isDeviceLocked(userId);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionCancelled() {
        EventBus.getDefault().send(new AppTransitionFinishedEvent());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionFinished() {
        EventBus.getDefault().send(new AppTransitionFinishedEvent());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void onCameraLaunchGestureDetected(int source) {
        boolean z;
        this.mLastCameraLaunchSource = source;
        if (isGoingToSleep()) {
            this.mLaunchCameraOnFinishedGoingToSleep = true;
            return;
        }
        NotificationPanelView notificationPanelView = this.mNotificationPanel;
        if (this.mStatusBarKeyguardViewManager.isShowing() && this.mExpandedVisible) {
            z = true;
        } else {
            z = false;
        }
        if (!notificationPanelView.canCameraGestureBeLaunched(z)) {
            return;
        }
        if (!this.mDeviceInteractive) {
            PowerManager pm = (PowerManager) this.mContext.getSystemService(PowerManager.class);
            pm.wakeUp(SystemClock.uptimeMillis(), "com.android.systemui:CAMERA_GESTURE");
            this.mStatusBarKeyguardViewManager.notifyDeviceWakeUpRequested();
        }
        vibrateForCameraGesture();
        if (!this.mStatusBarKeyguardViewManager.isShowing()) {
            startActivityDismissingKeyguard(KeyguardBottomAreaView.INSECURE_CAMERA_INTENT, false, true, true, null, 0);
            return;
        }
        if (!this.mDeviceInteractive) {
            this.mGestureWakeLock.acquire(6000L);
        }
        if (isScreenTurningOnOrOn()) {
            if (this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                this.mStatusBarKeyguardViewManager.reset(true);
            }
            if (!this.mStatusBarKeyguardViewManager.isSecure() && this.mStatusBarKeyguardViewManager.isOccluded()) {
                Slog.d("StatusBar", "Non-Secure unlock, no need to launch camera");
                return;
            } else {
                this.mNotificationPanel.launchCamera(this.mDeviceInteractive, source);
                updateScrimController();
                return;
            }
        }
        this.mLaunchCameraOnScreenTurningOn = true;
    }

    boolean isCameraAllowedByAdmin() {
        if (this.mDevicePolicyManager.getCameraDisabled(null, this.mLockscreenUserManager.getCurrentUserId())) {
            return false;
        }
        return !(this.mStatusBarKeyguardViewManager == null || (isKeyguardShowing() && isKeyguardSecure())) || (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, this.mLockscreenUserManager.getCurrentUserId()) & 2) == 0;
    }

    private boolean isGoingToSleep() {
        return this.mWakefulnessLifecycle.getWakefulness() == 3;
    }

    private boolean isScreenTurningOnOrOn() {
        return this.mScreenLifecycle.getScreenState() == 1 || this.mScreenLifecycle.getScreenState() == 2;
    }

    public void notifyFpAuthModeChanged() {
        updateDozing();
        updateScrimController();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDozing() {
        Trace.beginSection("StatusBar#updateDozing");
        boolean z = false;
        boolean dozing = (this.mDozingRequested && this.mState == 1) || this.mFingerprintUnlockController.getMode() == 2;
        boolean alwaysOn = DozeParameters.getInstance(this.mContext).getAlwaysOn();
        if (this.mFingerprintUnlockController.getMode() == 1) {
            dozing = false;
        }
        if (this.mDozing != dozing) {
            this.mDozing = dozing;
            KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
            if (this.mDozing && alwaysOn) {
                z = true;
            }
            keyguardViewMediator.setAodShowing(z);
            this.mStatusBarWindowManager.setDozing(this.mDozing);
            this.mStatusBarKeyguardViewManager.setDozing(this.mDozing);
            if (this.mAmbientIndicationContainer instanceof DozeReceiver) {
                ((DozeReceiver) this.mAmbientIndicationContainer).setDozing(this.mDozing);
            }
            this.mEntryManager.updateNotifications();
            updateDozingState();
            updateReportRejectedTouchVisibility();
        }
        Trace.endSection();
    }

    @VisibleForTesting
    void updateScrimController() {
        Trace.beginSection("StatusBar#updateScrimController");
        boolean wakeAndUnlocking = this.mFingerprintUnlockController.isWakeAndUnlock();
        this.mScrimController.setExpansionAffectsAlpha(!this.mFingerprintUnlockController.isFingerprintUnlock());
        if (this.mBouncerShowing) {
            ScrimState state = (this.mIsOccluded || this.mStatusBarKeyguardViewManager.bouncerNeedsScrimming() || this.mStatusBarKeyguardViewManager.willDismissWithAction() || this.mStatusBarKeyguardViewManager.isFullscreenBouncer()) ? ScrimState.BOUNCER_SCRIMMED : ScrimState.BOUNCER;
            this.mScrimController.transitionTo(state);
        } else if (this.mLaunchCameraOnScreenTurningOn || isInLaunchTransition()) {
            this.mScrimController.transitionTo(ScrimState.UNLOCKED, this.mUnlockScrimCallback);
        } else if (this.mBrightnessMirrorVisible) {
            this.mScrimController.transitionTo(ScrimState.BRIGHTNESS_MIRROR);
        } else if (!isPulsing()) {
            if (this.mDozing) {
                this.mScrimController.transitionTo(ScrimState.AOD);
            } else if (this.mIsKeyguard && !wakeAndUnlocking) {
                this.mScrimController.transitionTo(ScrimState.KEYGUARD);
            } else {
                this.mScrimController.transitionTo(ScrimState.UNLOCKED, this.mUnlockScrimCallback);
            }
        }
        Trace.endSection();
    }

    public boolean isKeyguardShowing() {
        if (this.mStatusBarKeyguardViewManager == null) {
            Slog.i("StatusBar", "isKeyguardShowing() called before startKeyguard(), returning true");
            return true;
        }
        return this.mStatusBarKeyguardViewManager.isShowing();
    }

    private final class DozeServiceHost implements DozeHost {
        private boolean mAnimateScreenOff;
        private boolean mAnimateWakeup;
        private final ArrayList<DozeHost.Callback> mCallbacks;
        private boolean mIgnoreTouchWhilePulsing;

        private DozeServiceHost() {
            this.mCallbacks = new ArrayList<>();
        }

        public String toString() {
            return "PSB.DozeServiceHost[mCallbacks=" + this.mCallbacks.size() + "]";
        }

        public void firePowerSaveChanged(boolean active) {
            for (DozeHost.Callback callback : this.mCallbacks) {
                callback.onPowerSaveChanged(active);
            }
        }

        public void fireNotificationHeadsUp() {
            for (DozeHost.Callback callback : this.mCallbacks) {
                callback.onNotificationHeadsUp();
            }
        }

        @Override // com.android.systemui.doze.DozeHost
        public void addCallback(DozeHost.Callback callback) {
            this.mCallbacks.add(callback);
        }

        @Override // com.android.systemui.doze.DozeHost
        public void removeCallback(DozeHost.Callback callback) {
            this.mCallbacks.remove(callback);
        }

        @Override // com.android.systemui.doze.DozeHost
        public void startDozing() {
            if (!StatusBar.this.mDozingRequested) {
                StatusBar.this.mDozingRequested = true;
                DozeLog.traceDozing(StatusBar.this.mContext, StatusBar.this.mDozing);
                StatusBar.this.updateDozing();
                StatusBar.this.updateIsKeyguard();
            }
        }

        @Override // com.android.systemui.doze.DozeHost
        public void pulseWhileDozing(final DozeHost.PulseCallback callback, int reason) {
            if (reason == 5) {
                StatusBar.this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), "com.android.systemui:NODOZE");
                StatusBar.this.startAssist(new Bundle());
            } else {
                StatusBar.this.mDozeScrimController.pulse(new DozeHost.PulseCallback() { // from class: com.android.systemui.statusbar.phone.StatusBar.DozeServiceHost.1
                    @Override // com.android.systemui.doze.DozeHost.PulseCallback
                    public void onPulseStarted() {
                        callback.onPulseStarted();
                        if (StatusBar.this.mHeadsUpManager.hasHeadsUpNotifications()) {
                            setPulsing(true);
                        }
                    }

                    @Override // com.android.systemui.doze.DozeHost.PulseCallback
                    public void onPulseFinished() {
                        callback.onPulseFinished();
                        setPulsing(false);
                    }

                    private void setPulsing(boolean pulsing) {
                        StatusBar.this.mNotificationPanel.setPulsing(pulsing);
                        StatusBar.this.mVisualStabilityManager.setPulsing(pulsing);
                        DozeServiceHost.this.mIgnoreTouchWhilePulsing = false;
                    }
                }, reason);
            }
        }

        @Override // com.android.systemui.doze.DozeHost
        public void stopDozing() {
            if (StatusBar.this.mDozingRequested) {
                StatusBar.this.mDozingRequested = false;
                DozeLog.traceDozing(StatusBar.this.mContext, StatusBar.this.mDozing);
                StatusBar.this.mWakefulnessLifecycle.dispatchStartedWakingUp();
                StatusBar.this.updateDozing();
            }
        }

        @Override // com.android.systemui.doze.DozeHost
        public void onIgnoreTouchWhilePulsing(boolean ignore) {
            if (ignore != this.mIgnoreTouchWhilePulsing) {
                DozeLog.tracePulseTouchDisabledByProx(StatusBar.this.mContext, ignore);
            }
            this.mIgnoreTouchWhilePulsing = ignore;
            if (StatusBar.this.isDozing() && ignore) {
                StatusBar.this.mStatusBarWindow.cancelCurrentTouch();
            }
        }

        @Override // com.android.systemui.doze.DozeHost
        public void dozeTimeTick() {
            StatusBar.this.mNotificationPanel.dozeTimeTick();
        }

        @Override // com.android.systemui.doze.DozeHost
        public boolean isPowerSaveActive() {
            return StatusBar.this.mBatteryController.isAodPowerSave();
        }

        @Override // com.android.systemui.doze.DozeHost
        public boolean isPulsingBlocked() {
            return StatusBar.this.mFingerprintUnlockController.getMode() == 1;
        }

        @Override // com.android.systemui.doze.DozeHost
        public boolean isProvisioned() {
            return StatusBar.this.mDeviceProvisionedController.isDeviceProvisioned() && StatusBar.this.mDeviceProvisionedController.isCurrentUserSetup();
        }

        @Override // com.android.systemui.doze.DozeHost
        public boolean isBlockingDoze() {
            if (StatusBar.this.mFingerprintUnlockController.hasPendingAuthentication()) {
                Log.i("StatusBar", "Blocking AOD because fingerprint has authenticated");
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.doze.DozeHost
        public void extendPulse() {
            StatusBar.this.mDozeScrimController.extendPulse();
        }

        @Override // com.android.systemui.doze.DozeHost
        public void setAnimateWakeup(boolean animateWakeup) {
            if (StatusBar.this.mWakefulnessLifecycle.getWakefulness() == 2 || StatusBar.this.mWakefulnessLifecycle.getWakefulness() == 1) {
                return;
            }
            this.mAnimateWakeup = animateWakeup;
        }

        @Override // com.android.systemui.doze.DozeHost
        public void setAnimateScreenOff(boolean animateScreenOff) {
            this.mAnimateScreenOff = animateScreenOff;
        }

        @Override // com.android.systemui.doze.DozeHost
        public void onDoubleTap(float screenX, float screenY) {
            if (screenX > 0.0f && screenY > 0.0f && StatusBar.this.mAmbientIndicationContainer != null && StatusBar.this.mAmbientIndicationContainer.getVisibility() == 0) {
                StatusBar.this.mAmbientIndicationContainer.getLocationOnScreen(StatusBar.this.mTmpInt2);
                float viewX = screenX - StatusBar.this.mTmpInt2[0];
                float viewY = screenY - StatusBar.this.mTmpInt2[1];
                if (0.0f <= viewX && viewX <= StatusBar.this.mAmbientIndicationContainer.getWidth() && 0.0f <= viewY && viewY <= StatusBar.this.mAmbientIndicationContainer.getHeight()) {
                    dispatchDoubleTap(viewX, viewY);
                }
            }
        }

        @Override // com.android.systemui.doze.DozeHost
        public void setDozeScreenBrightness(int value) {
            StatusBar.this.mStatusBarWindowManager.setDozeScreenBrightness(value);
        }

        @Override // com.android.systemui.doze.DozeHost
        public void setAodDimmingScrim(float scrimOpacity) {
            StatusBar.this.mScrimController.setAodFrontScrimAlpha(scrimOpacity);
        }

        public void dispatchDoubleTap(float viewX, float viewY) {
            dispatchTap(StatusBar.this.mAmbientIndicationContainer, viewX, viewY);
            dispatchTap(StatusBar.this.mAmbientIndicationContainer, viewX, viewY);
        }

        private void dispatchTap(View view, float x, float y) {
            long now = SystemClock.elapsedRealtime();
            dispatchTouchEvent(view, x, y, now, 0);
            dispatchTouchEvent(view, x, y, now, 1);
        }

        private void dispatchTouchEvent(View view, float x, float y, long now, int action) {
            MotionEvent ev = MotionEvent.obtain(now, now, action, x, y, 0);
            view.dispatchTouchEvent(ev);
            ev.recycle();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldAnimateWakeup() {
            return this.mAnimateWakeup;
        }

        public boolean shouldAnimateScreenOff() {
            return this.mAnimateScreenOff;
        }
    }

    public boolean shouldIgnoreTouch() {
        return isDozing() && this.mDozeServiceHost.mIgnoreTouchWhilePulsing;
    }

    public boolean isDeviceInteractive() {
        return this.mDeviceInteractive;
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public boolean isDeviceProvisioned() {
        return this.mDeviceProvisionedController.isDeviceProvisioned();
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isDeviceInVrMode() {
        return this.mVrMode;
    }

    @Override // com.android.systemui.statusbar.NotificationEntryManager.Callback
    public void onNotificationClicked(final StatusBarNotification sbn, final ExpandableNotificationRow row) {
        PendingIntent pendingIntent;
        final RemoteInputController controller = this.mRemoteInputManager.getController();
        if (controller.isRemoteInputActive(row.getEntry()) && !TextUtils.isEmpty(row.getActiveRemoteInputText())) {
            controller.closeRemoteInputs();
            return;
        }
        Notification notification = sbn.getNotification();
        if (notification.contentIntent != null) {
            pendingIntent = notification.contentIntent;
        } else {
            pendingIntent = notification.fullScreenIntent;
        }
        final PendingIntent intent = pendingIntent;
        final String notificationKey = sbn.getKey();
        boolean afterKeyguardGone = intent.isActivity() && PreviewInflater.wouldLaunchResolverActivity(this.mContext, intent.getIntent(), this.mLockscreenUserManager.getCurrentUserId());
        final boolean wasOccluded = this.mIsOccluded;
        dismissKeyguardThenExecute(new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$utLCe04OlISyy9KEurpzPN7px5s
            @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
            public final boolean onDismiss() {
                return StatusBar.lambda$onNotificationClicked$43(this.f$0, notificationKey, row, sbn, intent, controller, wasOccluded);
            }
        }, afterKeyguardGone);
    }

    public static /* synthetic */ boolean lambda$onNotificationClicked$43(final StatusBar statusBar, final String notificationKey, ExpandableNotificationRow row, final StatusBarNotification sbn, final PendingIntent intent, final RemoteInputController controller, final boolean wasOccluded) {
        ExpandableNotificationRow expandableNotificationRow;
        if (statusBar.mHeadsUpManager != null && statusBar.mHeadsUpManager.isHeadsUp(notificationKey)) {
            if (statusBar.isPresenterFullyCollapsed()) {
                expandableNotificationRow = row;
                HeadsUpUtil.setIsClickedHeadsUpNotification(expandableNotificationRow, true);
            } else {
                expandableNotificationRow = row;
            }
            statusBar.mHeadsUpManager.releaseImmediately(notificationKey);
        } else {
            expandableNotificationRow = row;
        }
        StatusBarNotification parentToCancel = null;
        if (statusBar.shouldAutoCancel(sbn) && statusBar.mGroupManager.isOnlyChildInGroup(sbn)) {
            StatusBarNotification summarySbn = statusBar.mGroupManager.getLogicalGroupSummary(sbn).getStatusBarNotification();
            if (statusBar.shouldAutoCancel(summarySbn)) {
                parentToCancel = summarySbn;
            }
        }
        final StatusBarNotification parentToCancel2 = parentToCancel;
        final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$imbbcfAlXdgPMUkZRmiSaTQh4kg
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$onNotificationClicked$42(this.f$0, intent, notificationKey, expandableNotificationRow2, controller, wasOccluded, parentToCancel2, sbn);
            }
        };
        if (statusBar.mStatusBarKeyguardViewManager.isShowing() && statusBar.mStatusBarKeyguardViewManager.isOccluded()) {
            statusBar.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
            statusBar.collapsePanel(true);
        } else {
            new Thread(runnable).start();
        }
        return !statusBar.mNotificationPanel.isFullyCollapsed();
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:47:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:53:0x0111  */
    public static /* synthetic */ void lambda$onNotificationClicked$42(StatusBar statusBar, PendingIntent intent, String notificationKey, ExpandableNotificationRow row, RemoteInputController controller, boolean wasOccluded, StatusBarNotification parentToCancelFinal, StatusBarNotification sbn) {
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException e) {
        }
        if (intent != null) {
            if (intent.isActivity()) {
                int userId = intent.getCreatorUserHandle().getIdentifier();
                if (statusBar.mLockPatternUtils.isSeparateProfileChallengeEnabled(userId) && statusBar.mKeyguardManager.isDeviceLocked(userId) && statusBar.startWorkChallengeIfNecessary(userId, intent.getIntentSender(), notificationKey)) {
                    statusBar.collapseOnMainThread();
                    return;
                }
            }
            Intent fillInIntent = null;
            NotificationData.Entry entry = row.getEntry();
            CharSequence remoteInputText = null;
            if (!TextUtils.isEmpty(entry.remoteInputText)) {
                remoteInputText = entry.remoteInputText;
            }
            CharSequence remoteInputText2 = remoteInputText;
            if (!TextUtils.isEmpty(remoteInputText2) && !controller.isSpinning(entry.key)) {
                fillInIntent = new Intent().putExtra("android.remoteInputDraft", remoteInputText2.toString());
            }
            Intent fillInIntent2 = fillInIntent;
            RemoteAnimationAdapter adapter = statusBar.mActivityLaunchAnimator.getLaunchAnimation(row, wasOccluded);
            if (adapter != null) {
                try {
                    ActivityManager.getService().registerRemoteAnimationForNextActivityStart(intent.getCreatorPackage(), adapter);
                    try {
                        try {
                            int launchResult = intent.sendAndReturnResult(statusBar.mContext, 0, fillInIntent2, null, null, null, statusBar.getActivityOptions(adapter));
                            statusBar.mActivityLaunchAnimator.setLaunchResult(launchResult);
                        } catch (PendingIntent.CanceledException | RemoteException e2) {
                            e = e2;
                            Log.w("StatusBar", "Sending contentIntent failed: " + e);
                        }
                    } catch (PendingIntent.CanceledException | RemoteException e3) {
                        e = e3;
                    }
                } catch (PendingIntent.CanceledException | RemoteException e4) {
                    e = e4;
                    Log.w("StatusBar", "Sending contentIntent failed: " + e);
                    if (intent.isActivity()) {
                        statusBar.mAssistManager.hideAssist();
                    }
                    if (statusBar.shouldCollapse()) {
                        statusBar.collapseOnMainThread();
                    }
                    int count = statusBar.mEntryManager.getNotificationData().getActiveNotifications().size();
                    int rank = statusBar.mEntryManager.getNotificationData().getRank(notificationKey);
                    NotificationVisibility nv = NotificationVisibility.obtain(notificationKey, rank, count, true);
                    statusBar.mBarService.onNotificationClick(notificationKey, nv);
                    if (parentToCancelFinal != null) {
                        statusBar.removeNotification(parentToCancelFinal);
                    }
                    if (!statusBar.shouldAutoCancel(sbn)) {
                    }
                    statusBar.removeNotification(sbn);
                }
                if (intent.isActivity()) {
                    statusBar.mAssistManager.hideAssist();
                }
            } else {
                int launchResult2 = intent.sendAndReturnResult(statusBar.mContext, 0, fillInIntent2, null, null, null, statusBar.getActivityOptions(adapter));
                statusBar.mActivityLaunchAnimator.setLaunchResult(launchResult2);
                if (intent.isActivity()) {
                    statusBar.mAssistManager.hideAssist();
                }
            }
        }
        if (statusBar.shouldCollapse()) {
            statusBar.collapseOnMainThread();
        }
        int count2 = statusBar.mEntryManager.getNotificationData().getActiveNotifications().size();
        int rank2 = statusBar.mEntryManager.getNotificationData().getRank(notificationKey);
        NotificationVisibility nv2 = NotificationVisibility.obtain(notificationKey, rank2, count2, true);
        try {
            statusBar.mBarService.onNotificationClick(notificationKey, nv2);
        } catch (RemoteException e5) {
        }
        if (parentToCancelFinal != null) {
            statusBar.removeNotification(parentToCancelFinal);
        }
        if (!statusBar.shouldAutoCancel(sbn) || statusBar.mEntryManager.isNotificationKeptForRemoteInput(notificationKey)) {
            statusBar.removeNotification(sbn);
        }
    }

    private void collapseOnMainThread() {
        if (Looper.getMainLooper().isCurrentThread()) {
            collapsePanel();
        } else {
            this.mStackScroller.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$gz0x-DF8XfLLI3DV9TaqObWTwvs
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.collapsePanel();
                }
            });
        }
    }

    private boolean shouldCollapse() {
        return (this.mState == 0 && this.mActivityLaunchAnimator.isAnimationPending()) ? false : true;
    }

    public void collapsePanel(boolean animate) {
        if (animate) {
            collapsePanel();
        } else if (!isPresenterFullyCollapsed()) {
            instantCollapseNotificationPanel();
            visibilityChanged(false);
        } else {
            runPostCollapseRunnables();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean collapsePanel() {
        if (this.mNotificationPanel.isFullyCollapsed()) {
            return false;
        }
        animateCollapsePanels(2, true, true);
        visibilityChanged(false);
        return true;
    }

    private void removeNotification(final StatusBarNotification notification) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$2XL5M8cYG3eNky2IEXNm9CS9MKc
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$removeNotification$45(this.f$0, notification);
            }
        });
    }

    public static /* synthetic */ void lambda$removeNotification$45(final StatusBar statusBar, final StatusBarNotification notification) {
        Runnable removeRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$b7yfdxiiTNEOkOIgVSymNOOzsyY
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mEntryManager.performRemoveNotification(notification);
            }
        };
        if (statusBar.isCollapsing()) {
            statusBar.addPostCollapseAction(removeRunnable);
        } else {
            removeRunnable.run();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public boolean isNotificationForCurrentProfiles(StatusBarNotification n) {
        int notificationUserId = n.getUserId();
        boolean z = DEBUG;
        return this.mLockscreenUserManager.isCurrentProfile(notificationUserId);
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public NotificationGroupManager getGroupManager() {
        return this.mGroupManager;
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void startNotificationGutsIntent(final Intent intent, final int appUid, final ExpandableNotificationRow row) {
        dismissKeyguardThenExecute(new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$sYvzQeF5O-fmLOs--81NBwAc7Nw
            @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
            public final boolean onDismiss() {
                return StatusBar.lambda$startNotificationGutsIntent$48(this.f$0, intent, row, appUid);
            }
        }, false);
    }

    public static /* synthetic */ boolean lambda$startNotificationGutsIntent$48(final StatusBar statusBar, final Intent intent, final ExpandableNotificationRow row, final int appUid) {
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$_P6Kd8TtC9rD1RzoKPaEWTKjObY
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$startNotificationGutsIntent$47(this.f$0, intent, row, appUid);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$startNotificationGutsIntent$47(final StatusBar statusBar, Intent intent, ExpandableNotificationRow row, int appUid) {
        int launchResult = TaskStackBuilder.create(statusBar.mContext).addNextIntentWithParentStack(intent).startActivities(statusBar.getActivityOptions(statusBar.mActivityLaunchAnimator.getLaunchAnimation(row, statusBar.mIsOccluded)), new UserHandle(UserHandle.getUserId(appUid)));
        statusBar.mActivityLaunchAnimator.setLaunchResult(launchResult);
        if (statusBar.shouldCollapse()) {
            statusBar.mStatusBarWindow.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$I8aUTx8Ws3MMq1JlwW7DvidTjXA
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.animateCollapsePanels(2, true);
                }
            });
        }
    }

    public void setNotificationSnoozed(StatusBarNotification sbn, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        if (snoozeOption.getSnoozeCriterion() != null) {
            this.mNotificationListener.snoozeNotification(sbn.getKey(), snoozeOption.getSnoozeCriterion().getId());
        } else {
            this.mNotificationListener.snoozeNotification(sbn.getKey(), snoozeOption.getMinutesToSnoozeFor() * 60 * 1000);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void toggleSplitScreen() {
        toggleSplitScreenMode(-1, -1);
    }

    void awakenDreams() {
        SystemServicesProxy.getInstance(this.mContext).awakenDreamsAsync();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void preloadRecentApps() {
        this.mHandler.removeMessages(1022);
        this.mHandler.sendEmptyMessage(1022);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void cancelPreloadRecentApps() {
        this.mHandler.removeMessages(1023);
        this.mHandler.sendEmptyMessage(1023);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void dismissKeyboardShortcutsMenu() {
        this.mHandler.removeMessages(1027);
        this.mHandler.sendEmptyMessage(1027);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void toggleKeyboardShortcutsMenu(int deviceId) {
        this.mHandler.removeMessages(1026);
        this.mHandler.obtainMessage(1026, deviceId, 0).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setTopAppHidesStatusBar(boolean topAppHidesStatusBar) {
        this.mTopHidesStatusBar = topAppHidesStatusBar;
        if (!topAppHidesStatusBar && this.mWereIconsJustHidden) {
            this.mWereIconsJustHidden = false;
            recomputeDisableFlags(true);
        }
        updateHideIconsForBouncer(true);
    }

    protected void toggleKeyboardShortcuts(int deviceId) {
        KeyboardShortcuts.toggle(this.mContext, deviceId);
    }

    protected void dismissKeyboardShortcuts() {
        KeyboardShortcuts.dismiss();
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public boolean shouldHideNotifications(int userId) {
        return this.mLockscreenUserManager.shouldHideNotifications(userId);
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public boolean shouldHideNotifications(String key) {
        return this.mLockscreenUserManager.shouldHideNotifications(key);
    }

    @Override // com.android.systemui.statusbar.NotificationData.Environment
    public boolean isSecurelyLocked(int userId) {
        return this.mLockscreenUserManager.isLockscreenPublicMode(userId);
    }

    public void onPanelLaidOut() {
        updateKeyguardMaxNotifications();
    }

    public void updateKeyguardMaxNotifications() {
        if (this.mState == 1) {
            int maxBefore = getMaxNotificationsWhileLocked(false);
            int maxNotifications = getMaxNotificationsWhileLocked(true);
            if (maxBefore != maxNotifications) {
                this.mViewHierarchyManager.updateRowStates();
            }
        }
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(final PendingIntent intent) {
        if (isDeviceProvisioned()) {
            boolean afterKeyguardGone = intent.isActivity() && PreviewInflater.wouldLaunchResolverActivity(this.mContext, intent.getIntent(), this.mLockscreenUserManager.getCurrentUserId());
            dismissKeyguardThenExecute(new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$iByXFcwi_Pj6P_6Arn3ScNDKAzg
                @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
                public final boolean onDismiss() {
                    return StatusBar.lambda$startPendingIntentDismissingKeyguard$50(this.f$0, intent);
                }
            }, afterKeyguardGone);
        }
    }

    public static /* synthetic */ boolean lambda$startPendingIntentDismissingKeyguard$50(final StatusBar statusBar, final PendingIntent intent) {
        new Thread(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$u_v8XO621Mhw3VQqkQcGi9ippaM
            @Override // java.lang.Runnable
            public final void run() {
                StatusBar.lambda$startPendingIntentDismissingKeyguard$49(this.f$0, intent);
            }
        }).start();
        return statusBar.collapsePanel();
    }

    public static /* synthetic */ void lambda$startPendingIntentDismissingKeyguard$49(StatusBar statusBar, PendingIntent intent) {
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException e) {
        }
        try {
            intent.send(null, 0, null, null, null, null, statusBar.getActivityOptions(null));
        } catch (PendingIntent.CanceledException e2) {
            Log.w("StatusBar", "Sending intent failed: " + e2);
        }
        if (intent.isActivity()) {
            statusBar.mAssistManager.hideAssist();
        }
    }

    private boolean shouldAutoCancel(StatusBarNotification sbn) {
        int flags = sbn.getNotification().flags;
        return (flags & 16) == 16 && (flags & 64) == 0;
    }

    protected Bundle getActivityOptions(RemoteAnimationAdapter animationAdapter) {
        ActivityOptions options;
        if (animationAdapter != null) {
            options = ActivityOptions.makeRemoteAnimation(animationAdapter);
        } else {
            options = ActivityOptions.makeBasic();
        }
        options.setLaunchWindowingMode(4);
        return options.toBundle();
    }

    protected void visibilityChanged(boolean visible) {
        if (this.mVisible != visible) {
            this.mVisible = visible;
            if (!visible) {
                this.mGutsManager.closeAndSaveGuts(true, true, true, -1, -1, true);
            }
        }
        updateVisibleToUser();
    }

    protected void updateVisibleToUser() {
        boolean oldVisibleToUser = this.mVisibleToUser;
        this.mVisibleToUser = this.mVisible && this.mDeviceInteractive;
        if (oldVisibleToUser != this.mVisibleToUser) {
            handleVisibleToUserChanged(this.mVisibleToUser);
        }
    }

    public void clearNotificationEffects() {
        try {
            this.mBarService.clearNotificationEffects();
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void onUpdateRowStates() {
        int offsetFromEnd = 1;
        if (this.mFooterView != null) {
            int offsetFromEnd2 = 1 + 1;
            this.mStackScroller.changeViewPosition(this.mFooterView, this.mStackScroller.getChildCount() - 1);
            offsetFromEnd = offsetFromEnd2;
        }
        this.mStackScroller.changeViewPosition(this.mEmptyShadeView, this.mStackScroller.getChildCount() - offsetFromEnd);
        this.mStackScroller.changeViewPosition(this.mNotificationShelf, this.mStackScroller.getChildCount() - (offsetFromEnd + 1));
        this.mScrimController.setNotificationCount(this.mStackScroller.getNotGoneChildCount());
    }

    protected void notifyHeadsUpGoingToSleep() {
        maybeEscalateHeadsUp();
    }

    public boolean isBouncerShowing() {
        return this.mBouncerShowing;
    }

    public static PackageManager getPackageManagerForUser(Context context, int userId) {
        Context contextForUser = context;
        if (userId >= 0) {
            try {
                contextForUser = context.createPackageContextAsUser(context.getPackageName(), 4, new UserHandle(userId));
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return contextForUser.getPackageManager();
    }

    public boolean isKeyguardSecure() {
        if (this.mStatusBarKeyguardViewManager == null) {
            Slog.w("StatusBar", "isKeyguardSecure() called before startKeyguard(), returning false", new Throwable());
            return false;
        }
        return this.mStatusBarKeyguardViewManager.isSecure();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onZenChanged(int zen) {
        updateEmptyShadeView();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showAssistDisclosure() {
        if (this.mAssistManager != null) {
            this.mAssistManager.showDisclosure();
        }
    }

    public NotificationPanelView getPanel() {
        return this.mNotificationPanel;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void startAssist(Bundle args) {
        if (this.mAssistManager != null) {
            this.mAssistManager.startAssist(args);
        }
    }

    public static /* synthetic */ void lambda$new$51(StatusBar statusBar) {
        if (statusBar.mNavigationBar != null) {
            statusBar.mNavigationBar.getBarTransitions().setAutoDim(true);
        }
    }

    public NotificationGutsManager getGutsManager() {
        return this.mGutsManager;
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isPresenterLocked() {
        return this.mState == 1;
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public Handler getHandler() {
        return this.mHandler;
    }

    public static /* synthetic */ void lambda$new$53(StatusBar statusBar, final Runnable saveImportance, StatusBarNotification sbn) {
        if (statusBar.mLockscreenUserManager.isLockscreenPublicMode(sbn.getUser().getIdentifier()) && (statusBar.mState == 1 || statusBar.mState == 2)) {
            statusBar.onLockedNotificationImportanceChange(new KeyguardHostView.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBar$CtNPTV-RmWu86CDbHNPMMwbJMBg
                @Override // com.android.keyguard.KeyguardHostView.OnDismissAction
                public final boolean onDismiss() {
                    return StatusBar.lambda$new$52(saveImportance);
                }
            });
        } else {
            saveImportance.run();
        }
    }

    static /* synthetic */ boolean lambda$new$52(Runnable saveImportance) {
        saveImportance.run();
        return true;
    }

    private final boolean supportCustomizeCarrierLabel() {
        return this.mStatusBarPlmnPlugin != null && this.mStatusBarPlmnPlugin.supportCustomizeCarrierLabel() && this.mNetworkController != null && this.mNetworkController.hasMobileDataFeature();
    }

    private final void updateCustomizeCarrierLabelVisibility(boolean force) {
        if (DEBUG) {
            Log.d("StatusBar", "updateCustomizeCarrierLabelVisibility(), force = " + force + ", mState = " + this.mState);
        }
        boolean makeVisible = this.mStackScroller.getVisibility() == 0 && this.mState != 1;
        this.mStatusBarPlmnPlugin.updateCarrierLabelVisibility(force, makeVisible);
    }

    protected void updateCarrierLabelVisibility(boolean force) {
        if (supportCustomizeCarrierLabel()) {
            if (this.mState == 1 || this.mNotificationPanel.isPanelVisibleBecauseOfHeadsUp()) {
                if (this.mCustomizeCarrierLabel != null) {
                    this.mCustomizeCarrierLabel.setVisibility(8);
                    return;
                }
                return;
            }
            updateCustomizeCarrierLabelVisibility(force);
        }
    }

    private View getStatusBarViewRecentsButton() {
        return this.mStatusBarView.findViewById(R.id.recent_apps007);
    }

    private void setRecentsButton() {
        if (this.mStatusBarView != null && getStatusBarViewRecentsButton() != null) {
            getStatusBarViewRecentsButton().setOnClickListener(this.mRecentsClickListener);
            getStatusBarViewRecentsButton().setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.StatusBar.27
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction() & 255;
                    if (action == 0) {
                        StatusBar.this.mCommandQueue.preloadRecentApps();
                        return false;
                    }
                    if (action == 3) {
                        StatusBar.this.mCommandQueue.cancelPreloadRecentApps();
                        return false;
                    }
                    if (action == 1 && !v.isPressed()) {
                        StatusBar.this.mCommandQueue.cancelPreloadRecentApps();
                        return false;
                    }
                    return false;
                }
            });
        }
    }
}
