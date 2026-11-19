package com.android.systemui.statusbar.phone;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.IActivityManager;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.IRotationWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.stackdivider.Divider;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.hcn.auto.systemui.SkinManager;
import com.hcn.auto.systemui.Utils;
import com.hcn.autocan.hiworld.gm.air.AIR;
import com.hcn.skin.support.resources.SkinCompatResources;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarFragment extends Fragment implements CommandQueue.Callbacks {
    RelativeLayout bottomBg;
    Drawable bottomBgPortDrawable;
    String bottomBgPortResName;
    private boolean mAccessibilityFeedbackEnabled;
    private AccessibilityManager mAccessibilityManager;
    protected AssistManager mAssistManager;
    private AudioManager mAudioManager;
    private CommandQueue mCommandQueue;
    private ContentResolver mContentResolver;
    private int mDisabledFlags1;
    private int mDisabledFlags2;
    private Divider mDivider;
    public boolean mHomeBlockedThisTouch;
    private boolean mHoveringRotationSuggestion;
    private long mLastLockToAppLongPress;
    private int mLastRotationSuggestion;
    private int mLayoutDirection;
    private LightBarController mLightBarController;
    private Locale mLocale;
    private MagnificationContentObserver mMagnificationObserver;
    private int mNavigationBarMode;
    private OverviewProxyService mOverviewProxyService;
    protected String mPackageName;
    private boolean mPendingRotationSuggestion;
    private int mPortTheme;
    private Recents mRecents;
    protected Resources mResources;
    private Animator mRotateHideAnimator;
    private RotationLockController mRotationLockController;
    private StatusBar mStatusBar;
    private int mSystemUiVisibility;
    private TaskStackListenerImpl mTaskStackListener;
    private int mUiMode;
    private VolumeDialogController mVCController;
    private WindowManager mWindowManager;
    FrameLayout portBottomBg;
    Utils utils;
    protected NavigationBarView mNavigationBarView = null;
    private int mNavigationBarWindowState = 0;
    private int mNavigationIconHints = 0;
    private final MetricsLogger mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
    private final Runnable mRemoveRotationProposal = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$2a6PQeDykikHzH0rBVD4AZZp14o
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.setRotateSuggestionButtonState(false);
        }
    };
    private final Runnable mCancelPendingRotationProposal = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$Wf_FUQzkbSdMD9hXKJaXOD_rVSY
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.mPendingRotationSuggestion = false;
        }
    };
    private final ViewRippler mViewRippler = new ViewRippler();
    protected MyHandler mHandler = new MyHandler(this);
    private final String CHANGE_BOTTOM_BG = "action.themePicker.ui.changeNaviBg";
    private int mActiveStream = 3;
    private final OverviewProxyService.OverviewProxyListener mOverviewProxyListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarFragment.1
        @Override // com.android.systemui.OverviewProxyService.OverviewProxyListener
        public void onConnectionChanged(boolean isConnected) {
            NavigationBarFragment.this.mNavigationBarView.updateStates();
            NavigationBarFragment.this.updateScreenPinningGestures();
        }

        @Override // com.android.systemui.OverviewProxyService.OverviewProxyListener
        public void onQuickStepStarted() {
            NavigationBarFragment.this.setRotateSuggestionButtonState(false);
        }

        @Override // com.android.systemui.OverviewProxyService.OverviewProxyListener
        public void onInteractionFlagsChanged(int flags) {
            NavigationBarFragment.this.mNavigationBarView.updateStates();
            NavigationBarFragment.this.updateScreenPinningGestures();
        }

        @Override // com.android.systemui.OverviewProxyService.OverviewProxyListener
        public void onBackButtonAlphaChanged(float alpha, boolean animate) {
            ButtonDispatcher backButton = NavigationBarFragment.this.mNavigationBarView.getBackButton();
            backButton.setVisibility(alpha > 0.0f ? 0 : 4);
            backButton.setAlpha(alpha, animate);
        }
    };
    private final AccessibilityManager.AccessibilityServicesStateChangeListener mAccessibilityListener = new AccessibilityManager.AccessibilityServicesStateChangeListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$dxES00kAyC8r2RmY9FwTYgUhoj8
        @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
        public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
            this.f$0.updateAccessibilityServicesState(accessibilityManager);
        }
    };
    private final IRotationWatcher.Stub mRotationWatcher = new AnonymousClass3();
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.NavigationBarFragment.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(action) || "android.intent.action.SCREEN_ON".equals(action)) {
                NavigationBarFragment.this.notifyNavigationBarScreenOn();
            }
            if ("android.intent.action.USER_SWITCHED".equals(action)) {
                NavigationBarFragment.this.updateAccessibilityServicesState(NavigationBarFragment.this.mAccessibilityManager);
            }
            if ("action.themePicker.ui.changeNaviBg".equals(action)) {
                Log.e("NavigationBar", "changeBottomBgByThemePicker---receive----" + NavigationBarFragment.getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"));
                if (SystemUIApplication.hasBottomBar() || SystemUIApplication.isPortraitWindowMode(NavigationBarFragment.this.getContext())) {
                    NavigationBarFragment.this.restart();
                }
            }
        }
    };
    private final VolumeDialogController.Callbacks mVCCallback = new VolumeDialogController.Callbacks() { // from class: com.android.systemui.statusbar.phone.NavigationBarFragment.5
        private int mVol_Stream_Ring = -1;
        private int mVol_Stream_Music = -1;
        private int mVol_Stream_Call = -1;
        private boolean mMute_Stream_Music = false;

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowRequested(int reason) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onDismissRequested(int reason) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onScreenOff() {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowSafetyWarning(int flags) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onAccessibilityModeChanged(Boolean showA11yStream) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onStateChanged(VolumeDialogController.State state) {
            int volume_index;
            boolean change = false;
            for (int i = 0; i < state.states.size(); i++) {
                int stream = state.states.keyAt(i);
                VolumeDialogController.StreamState ss = state.states.valueAt(i);
                if (stream == 3 && (this.mVol_Stream_Music != ss.level || this.mMute_Stream_Music != ss.muted)) {
                    this.mVol_Stream_Music = ss.level;
                    this.mMute_Stream_Music = ss.muted;
                    change = true;
                }
                if (stream == 2 && this.mVol_Stream_Ring != ss.level) {
                    this.mVol_Stream_Ring = ss.level;
                    change = true;
                }
                if (stream == 0 && this.mVol_Stream_Call != ss.level) {
                    this.mVol_Stream_Call = ss.level;
                    change = true;
                }
            }
            if (change) {
                if (NavigationBarFragment.this.mActiveStream != 0) {
                    if (NavigationBarFragment.this.mActiveStream == 2) {
                        volume_index = this.mVol_Stream_Ring;
                    } else {
                        volume_index = this.mVol_Stream_Music;
                    }
                } else {
                    volume_index = this.mVol_Stream_Call;
                }
                NavigationBarFragment.this.myHandler.removeMessages(1);
                Message msg = NavigationBarFragment.this.myHandler.obtainMessage(1, NavigationBarFragment.this.mActiveStream, volume_index);
                NavigationBarFragment.this.myHandler.sendMessage(msg);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onLayoutDirectionChanged(int layoutDirection) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onConfigurationChanged() {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowVibrateHint() {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public void onShowSilentHint() {
        }
    };
    Handler myHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.statusbar.phone.NavigationBarFragment.6
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.d("NavigationBar", "MSG_VOL_CHANGED activeStream=" + msg.arg1 + " vol=" + msg.arg2);
                    if (NavigationBarFragment.this.mNavigationBarView != null) {
                        NavigationBarFragment.this.mNavigationBarView.onVolumeChanged(msg.arg1, msg.arg2, NavigationBarFragment.this.mAudioManager.isStreamMute(msg.arg1));
                    }
                    break;
                case 2:
                    Log.d("NavigationBar", "MSG_CHECK_ACTIVE_STREAM...");
                    boolean isStreamChange = false;
                    if (AudioSystem.isStreamActive(0, 0)) {
                        if (NavigationBarFragment.this.mActiveStream != 0) {
                            NavigationBarFragment.this.mActiveStream = 0;
                            isStreamChange = true;
                        }
                    } else if (AudioSystem.isStreamActive(2, 0)) {
                        if (NavigationBarFragment.this.mActiveStream != 2) {
                            NavigationBarFragment.this.mActiveStream = 2;
                            isStreamChange = true;
                        }
                    } else if (NavigationBarFragment.this.mActiveStream != 3) {
                        NavigationBarFragment.this.mActiveStream = 3;
                        isStreamChange = true;
                    }
                    if (isStreamChange) {
                        removeMessages(1);
                        int volume_index = NavigationBarFragment.this.mAudioManager.getStreamVolume(NavigationBarFragment.this.mActiveStream);
                        sendMessage(obtainMessage(1, NavigationBarFragment.this.mActiveStream, volume_index));
                    }
                    sendEmptyMessageDelayed(2, 1200L);
                    break;
            }
        }
    };

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NavigationBar", "onCreate...");
        this.mCommandQueue = (CommandQueue) SysUiServiceProvider.getComponent(getContext(), CommandQueue.class);
        this.mCommandQueue.addCallbacks(this);
        this.mStatusBar = (StatusBar) SysUiServiceProvider.getComponent(getContext(), StatusBar.class);
        this.mRecents = (Recents) SysUiServiceProvider.getComponent(getContext(), Recents.class);
        this.mDivider = (Divider) SysUiServiceProvider.getComponent(getContext(), Divider.class);
        this.mWindowManager = (WindowManager) getContext().getSystemService(WindowManager.class);
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService(AccessibilityManager.class);
        ((AccessibilityManagerWrapper) Dependency.get(AccessibilityManagerWrapper.class)).addCallback(this.mAccessibilityListener);
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        this.mVCController = (VolumeDialogController) Dependency.get(VolumeDialogController.class);
        this.mContentResolver = getContext().getContentResolver();
        this.mMagnificationObserver = new MagnificationContentObserver(getContext().getMainThreadHandler());
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_display_magnification_navbar_enabled"), false, this.mMagnificationObserver, -1);
        this.mUiMode = getContext().getResources().getConfiguration().uiMode;
        if (SystemUIApplication.hasBottomBar() || SystemUIApplication.isPortraitWindowMode(getContext())) {
            this.utils = new Utils(getContext());
            this.utils.init();
        }
        this.mResources = SkinCompatResources.getInstance().getSkinResources();
        this.mPackageName = SkinCompatResources.getInstance().getSkinPkgName();
        Log.e("NavigationBar", "onCreate..." + this.mPackageName);
        if (savedInstanceState != null) {
            this.mDisabledFlags1 = savedInstanceState.getInt("disabled_state", 0);
            this.mDisabledFlags2 = savedInstanceState.getInt("disabled2_state", 0);
        }
        this.mAssistManager = (AssistManager) Dependency.get(AssistManager.class);
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        try {
            WindowManagerGlobal.getWindowManagerService().watchRotation(this.mRotationWatcher, getContext().getDisplay().getDisplayId());
            this.mRotationLockController = (RotationLockController) Dependency.get(RotationLockController.class);
            if (this.mRotationLockController.isRotationLocked()) {
                int winRotation = this.mWindowManager.getDefaultDisplay().getRotation();
                this.mRotationLockController.setRotationLockedAtAngle(true, winRotation);
            }
            this.mTaskStackListener = new TaskStackListenerImpl();
            ActivityManagerWrapper.getInstance().registerTaskStackListener(this.mTaskStackListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.d("NavigationBar", "onDestroy...");
        this.mCommandQueue.removeCallbacks(this);
        ((AccessibilityManagerWrapper) Dependency.get(AccessibilityManagerWrapper.class)).removeCallback(this.mAccessibilityListener);
        this.mContentResolver.unregisterContentObserver(this.mMagnificationObserver);
        try {
            WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this.mRotationWatcher);
            ActivityManagerWrapper.getInstance().unregisterTaskStackListener(this.mTaskStackListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String skinName;
        Log.d("NavigationBar", "onCreateView...");
        if (SystemUIApplication.hasBottomBar()) {
            View ret = null;
            if ("1".equals(getSystemProperty("independent_systemui_style", "0"))) {
                skinName = getSystemProperty("persist.sys.systemui.skin", "");
            } else {
                skinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
            }
            if (isNewBottomUI(skinName)) {
                String naviBottomRes = com.android.systemui.util.Utils.getSystemProperty("persist.sys.systemUI_NaviBottom", "");
                String naviBottomHeight = com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "");
                if (naviBottomRes.equals("")) {
                    if (SkinCompatResources.getInstance().getLayout("layout_navi_bottom_" + skinName + "_" + naviBottomHeight) != null) {
                        naviBottomRes = "layout_navi_bottom_" + skinName + "_" + naviBottomHeight;
                        Log.e("NavigationBar", "layout_navi_bottom---special height layout---" + naviBottomRes);
                    } else {
                        naviBottomRes = "layout_navi_bottom_" + skinName;
                        Log.e("NavigationBar", "layout_navi_bottom---normal layout---" + naviBottomRes);
                    }
                }
                View inflate = SkinCompatResources.getInstance().getLayout(naviBottomRes);
                if (inflate != null) {
                    FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.navi_bottom_layout_container, container, false);
                    if (frame != null) {
                        frame.addView(inflate);
                        return frame;
                    }
                    Log.e("NavigationBar", "inflate navi_bottom_layout_container error");
                } else {
                    Log.e("NavigationBar", "Skin not contain navi_bottom_layout");
                }
            }
            if ("sa138".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.systemui.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_sa138, container, false);
            } else if ("xt520".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt520, container, false);
            } else if ("xt540_cs".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt540_cs, container, false);
            } else if ("xt540_ac".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt540_ac, container, false);
            } else if ("xt540_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt540_h, container, false);
            } else if ("xt633_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt633_h, container, false);
            } else if ("xt634_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt634_h, container, false);
            } else if ("xt635_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt635_h, container, false);
            } else if ("xt636_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt636_h, container, false);
            } else if ("xt637_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt637_h, container, false);
            } else if ("xt660_h".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt660_h, container, false);
            } else if ("xt634_hs".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt634_hs, container, false);
            } else if ("1".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.haiyangxing", ""))) {
                Log.d("NavigationBar", "onCreateView: 1");
                if ("xt540".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    if ("125".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                        ret = inflater.inflate(R.layout.layout_navi_bottom_xt540_125_hyx, container, false);
                    } else {
                        ret = "140".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt540_140_hyx, container, false) : inflater.inflate(R.layout.layout_navi_bottom_xt540_hyx, container, false);
                    }
                } else if ("xt660".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    Log.d("NavigationBar", "onCreateView: 1,xt660");
                    if ("140".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                        Log.d("NavigationBar", "onCreateView: 1,xt660, 140");
                        ret = inflater.inflate(R.layout.layout_navi_bottom_xt660_140_hyx, container, false);
                    }
                }
            } else if ("xt617".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "")) || "xt618".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt617, container, false);
            } else if ("xt633".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                    ret = inflater.inflate(R.layout.layout_navi_bottom_xt633_190, container, false);
                } else {
                    ret = "210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt633_210, container, false) : inflater.inflate(R.layout.layout_navi_bottom_xt633, container, false);
                }
            } else if ("xt634".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                    ret = inflater.inflate(R.layout.layout_navi_bottom_xt634_190, container, false);
                } else {
                    ret = "210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt634_210, container, false) : inflater.inflate(R.layout.layout_navi_bottom_xt634, container, false);
                }
            } else if ("xt635".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                    ret = inflater.inflate(R.layout.layout_navi_bottom_xt635_190, container, false);
                } else {
                    ret = "210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt635_210, container, false) : inflater.inflate(R.layout.layout_navi_bottom_xt635, container, false);
                }
            } else if ("xt636".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                    ret = inflater.inflate(R.layout.layout_navi_bottom_xt636_190, container, false);
                } else {
                    ret = "210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt636_210, container, false) : inflater.inflate(R.layout.layout_navi_bottom_xt636, container, false);
                }
            } else if ("xt637".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                    ret = inflater.inflate(R.layout.layout_navi_bottom_xt637_190, container, false);
                } else {
                    ret = "210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt637_210, container, false) : inflater.inflate(R.layout.layout_navi_bottom_xt637, container, false);
                }
            } else if ("xt646".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt646, container, false);
            } else if ("xt641".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt641, container, false);
            } else if ("xt651".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt651, container, false);
            } else if ("xt660".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                ret = inflater.inflate(R.layout.layout_navi_bottom_xt660, container, false);
            } else {
                ret = "xt666".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "")) ? inflater.inflate(R.layout.layout_navi_bottom_xt666, container, false) : inflater.inflate(R.layout.layout_navi_bottom, container, false);
            }
            this.bottomBg = (RelativeLayout) ret.findViewById(R.id.navi_bottom);
            Log.d("NavigationBar", "skinName: " + skinName);
            if (skinName != null) {
                String resName = skinName.toLowerCase() + "_bottom_bg";
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(resName);
                if (drawable != null) {
                    this.bottomBg.setBackground(drawable);
                    Log.d("NavigationBar", "onCreateView: resName:" + resName);
                }
            }
            if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                if ("xt633".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt633_bottom_bg_190));
                } else if ("xt634".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt634_bottom_bg_190));
                } else if ("xt635".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt635_bottom_bg_190));
                } else if ("xt636".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt636_bottom_bg_190));
                } else if ("xt637".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt637_bottom_bg_190));
                }
            } else if ("210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                if ("xt633".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt633_bottom_bg_210));
                } else if ("xt634".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt634_bottom_bg_210));
                } else if ("xt635".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt635_bottom_bg_210));
                } else if ("xt636".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt636_bottom_bg_210));
                } else if ("xt637".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt637_bottom_bg_210));
                }
            }
            if ("1".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin_bg_support", ""))) {
                String SysUIName = com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "");
                if ("xt540".equals(SysUIName) || "xt553".equals(SysUIName) || "xt646".equals(SysUIName)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_two));
                } else if ("xt554".equals(SysUIName)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt554_bottom_bg_two));
                } else if ("xt555".equals(SysUIName)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt555_bottom_bg_two));
                }
            }
            String SysUIName2 = com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "");
            String SysUINaviHeight = com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "");
            TextView soundVolume = (TextView) ret.findViewById(R.id.tv_sound_volume);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) soundVolume.getLayoutParams();
            if ("xt540".equals(SysUIName2)) {
                Log.d("NavigationBar", "onCreateView: resName0:" + SysUINaviHeight);
                if ("195".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: resName1:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_195));
                    params.bottomMargin = 60;
                    params.leftMargin = 7;
                    soundVolume.setLayoutParams(params);
                } else if ("220".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: resName2:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_220));
                    params.bottomMargin = 72;
                    params.leftMargin = 21;
                    soundVolume.setLayoutParams(params);
                } else if ("190".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: resName3:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_195));
                    params.bottomMargin = 72;
                    params.leftMargin = 18;
                    soundVolume.setLayoutParams(params);
                } else if ("125".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: resName4:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_125));
                    params.bottomMargin = 2;
                    params.leftMargin = 98;
                    soundVolume.setLayoutParams(params);
                } else if ("140".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: resName5:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_140));
                    params.bottomMargin = 2;
                    params.leftMargin = 98;
                    soundVolume.setLayoutParams(params);
                } else if ("170".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: resName6:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg));
                    if ("1".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.haiyangxing", ""))) {
                        this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_hyx));
                        params.bottomMargin = 6;
                        params.leftMargin = 70;
                    } else {
                        params.bottomMargin = 6;
                        params.leftMargin = 10;
                    }
                    soundVolume.setLayoutParams(params);
                }
            }
            if ("xt540_cs".equals(SysUIName2)) {
                if ("190".equals(SysUINaviHeight)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_cs_bottom_bg_190));
                    params.bottomMargin = 66;
                    params.leftMargin = 7;
                } else if ("210".equals(SysUINaviHeight)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_cs_bottom_bg_210));
                    params.bottomMargin = 77;
                    params.leftMargin = 20;
                }
                soundVolume.setLayoutParams(params);
            } else if ("xt540_ac".equals(SysUIName2)) {
                if ("220".equals(SysUINaviHeight)) {
                    Log.d("NavigationBar", "onCreateView: SysUINaviHeight:" + SysUINaviHeight);
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_ac_bottom_bg_220));
                    params.bottomMargin = 40;
                    params.leftMargin = 0;
                }
                soundVolume.setLayoutParams(params);
            } else if ("xt651".equals(SysUIName2)) {
                if ("190".equals(SysUINaviHeight)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt651_bottom_bg_190));
                    params.bottomMargin = 66;
                    params.leftMargin = 7;
                } else if ("210".equals(SysUINaviHeight)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt651_bottom_bg_210));
                    params.bottomMargin = 77;
                    params.leftMargin = 20;
                }
                soundVolume.setLayoutParams(params);
            } else if ("xt660".equals(SysUIName2)) {
                if ("140".equals(SysUINaviHeight)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt660_bottom_bg_140));
                    params.bottomMargin = 2;
                    params.leftMargin = 98;
                    soundVolume.setLayoutParams(params);
                }
                soundVolume.setLayoutParams(params);
            }
            return ret;
        }
        if (SystemUIApplication.getNaviBarPosition() == 1 && com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "").toLowerCase().contains("dz23")) {
            return inflater.inflate(R.layout.layout_navi_bottom_dz23, container, false);
        }
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            if (isNewLauncher()) {
                Log.e("NavigationBar", "onCreateView...getSystemProperty = " + getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"));
                SkinManager skinManager = SkinManager.getInstance(getContext());
                skinManager.loadSkin();
                String skinName2 = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
                if (!skinName2.equals("p38") && !skinName2.equals("p42") && !skinName2.equals("p45")) {
                    View inflate2 = SkinCompatResources.getInstance().getLayout("bottom_layout");
                    if (inflate2 != null) {
                        FrameLayout frame2 = (FrameLayout) inflater.inflate(R.layout.bottom_layout_container, container, false);
                        this.portBottomBg = (FrameLayout) inflate2;
                        if (frame2 != null) {
                            frame2.addView(inflate2);
                            return frame2;
                        }
                        Log.e("NavigationBar", "inflate bottom_layout_container error");
                    } else {
                        Log.e("NavigationBar", "Skin not contain bottom_layout");
                    }
                }
                if ("p45".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 45;
                    return inflater.inflate(R.layout.bottom_layout_p45, container, false);
                }
                if ("xt166".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.xt166"))) {
                    this.mPortTheme = 166;
                    return inflater.inflate(R.layout.bottom_layout_xt166, container, false);
                }
                if ("xt279".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 279;
                    return inflater.inflate(R.layout.bottom_layout_p10, container, false);
                }
                if ("xt281".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 281;
                    return inflater.inflate(R.layout.bottom_layout_xt167, container, false);
                }
                if ("xt282".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 282;
                    return inflater.inflate(R.layout.bottom_layout_xt167, container, false);
                }
                if ("xt319".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 319;
                    return inflater.inflate(R.layout.bottom_layout_p3, container, false);
                }
                if ("p38".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 38;
                    return inflater.inflate(R.layout.bottom_layout_p38, container, false);
                }
                if ("p42".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 42;
                    return inflater.inflate(R.layout.bottom_layout_p42, container, false);
                }
                if ("p21".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 21;
                    return inflater.inflate(R.layout.bottom_layout_xt167, container, false);
                }
                if ("p22".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 22;
                    return inflater.inflate(R.layout.bottom_layout_xt167, container, false);
                }
                if ("p23".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 23;
                    return inflater.inflate(R.layout.bottom_layout_p11, container, false);
                }
                if ("xt319_qled".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 319;
                    return inflater.inflate(R.layout.bottom_layout_p3, container, false);
                }
                if ("xt320".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 320;
                    return inflater.inflate(R.layout.bottom_layout_p3, container, false);
                }
                if ("xt323".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 323;
                    return inflater.inflate(R.layout.bottom_layout_xt167, container, false);
                }
                if ("sa85".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 85;
                    return inflater.inflate(R.layout.bottom_layout_p4, container, false);
                }
                if ("p8".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p8"))) {
                    this.mPortTheme = 8;
                    return inflater.inflate(R.layout.bottom_layout_p8, container, false);
                }
                if ("p2".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 2;
                    return inflater.inflate(R.layout.bottom_layout_p2, container, false);
                }
                if ("p1".equals(getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 1;
                    return inflater.inflate(R.layout.bottom_layout, container, false);
                }
            } else {
                if ("com.hcn.auto.theme.p20".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 20;
                    return inflater.inflate(R.layout.bottom_layout_p20, container, false);
                }
                if ("com.hcn.auto.theme.p19".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 19;
                    return inflater.inflate(R.layout.bottom_layout_p19, container, false);
                }
                if ("com.hcn.auto.theme.p18".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 18;
                    return inflater.inflate(R.layout.bottom_layout_p18, container, false);
                }
                if ("com.hcn.auto.theme.p17".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 17;
                    return inflater.inflate(R.layout.bottom_layout_p17, container, false);
                }
                if ("com.hcn.auto.theme.xt279".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.xt279"))) {
                    this.mPortTheme = 279;
                    return inflater.inflate(R.layout.bottom_layout_p10, container, false);
                }
                if ("com.hcn.auto.theme.xt195".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.xt195"))) {
                    this.mPortTheme = 195;
                    return inflater.inflate(R.layout.bottom_layout_xt195, container, false);
                }
                if ("com.hcn.auto.theme.xt167".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.xt167"))) {
                    this.mPortTheme = 167;
                    return inflater.inflate(R.layout.bottom_layout_xt167, container, false);
                }
                if ("com.hcn.auto.theme.xt166".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.xt166"))) {
                    this.mPortTheme = 166;
                    return inflater.inflate(R.layout.bottom_layout_xt166, container, false);
                }
                if ("com.hcn.auto.theme.p15".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 15;
                    return inflater.inflate(R.layout.bottom_layout_p15, container, false);
                }
                if ("com.hcn.auto.theme.p14".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 14;
                    return inflater.inflate(R.layout.bottom_layout_p14, container, false);
                }
                if ("com.hcn.auto.theme.p13".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 13;
                    return inflater.inflate(R.layout.bottom_layout_p13, container, false);
                }
                if ("com.hcn.auto.theme.p12".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 12;
                    return inflater.inflate(R.layout.bottom_layout_p12, container, false);
                }
                if ("com.hcn.auto.theme.p11".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 11;
                    return inflater.inflate(R.layout.bottom_layout_p11, container, false);
                }
                if ("com.hcn.auto.theme.p10".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 10;
                    return inflater.inflate(R.layout.bottom_layout_p10, container, false);
                }
                if ("com.hcn.auto.theme.p9".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p9"))) {
                    this.mPortTheme = 9;
                    return inflater.inflate(R.layout.bottom_layout_p9, container, false);
                }
                if ("com.hcn.auto.theme.p8".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p8"))) {
                    this.mPortTheme = 8;
                    return inflater.inflate(R.layout.bottom_layout_p8, container, false);
                }
                if ("com.hcn.auto.theme.p7".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1")) || "com.hcn.auto.theme.xt142".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 7;
                    return inflater.inflate(R.layout.bottom_layout_p7, container, false);
                }
                if ("com.hcn.auto.theme.p6".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 6;
                    return inflater.inflate(R.layout.bottom_layout_p6, container, false);
                }
                if ("com.hcn.auto.theme.p5".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 5;
                    return inflater.inflate(R.layout.bottom_layout_p5, container, false);
                }
                if ("com.hcn.auto.theme.p4".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 4;
                    return inflater.inflate(R.layout.bottom_layout_p4, container, false);
                }
                if ("com.hcn.auto.theme.p3".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 3;
                    return inflater.inflate(R.layout.bottom_layout_p3, container, false);
                }
                if ("com.hcn.auto.theme.p2".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 2;
                    return inflater.inflate(R.layout.bottom_layout_p2, container, false);
                }
                if ("com.hcn.auto.theme.sa51".equals(getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.p1"))) {
                    this.mPortTheme = 51;
                    return inflater.inflate(R.layout.bottom_layout_sa51, container, false);
                }
                this.mPortTheme = 1;
                return inflater.inflate(R.layout.bottom_layout, container, false);
            }
        }
        return inflater.inflate(R.layout.navigation_bar, container, false);
    }

    private boolean isNewLauncher() {
        return "1".equals(getSystemProperty("ro.vendor.sys.skin_support", "0"));
    }

    public boolean isNewBottomUI(String skinName) {
        if (!SystemUIApplication.hasBottomBar()) {
            Log.e("NavigationBar", skinName + "---notNewBottomUI---false");
            return false;
        }
        String apkPath = Utils.getApkPath(getContext(), "com.hcn.systemui.theme." + skinName);
        if (!TextUtils.isEmpty(apkPath)) {
            Log.e("NavigationBar", skinName + "---isNewBottomUI---true" + apkPath);
            return true;
        }
        Log.e("NavigationBar", "notNewBottomUI---false");
        return false;
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("NavigationBar", "onViewCreated...");
        this.mNavigationBarView = (NavigationBarView) view;
        if (SystemUIApplication.hasBottomBar()) {
            this.mNavigationBarView.initBottomBar();
        }
        this.mVCController.addCallback(this.mVCCallback, this.myHandler);
        this.mVCController.getState();
        if (SystemUIApplication.getNaviBarPosition() > 0) {
            this.mNavigationBarView.initLeftSideBar();
        }
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            this.mNavigationBarView.initHcnPort();
            this.mNavigationBarView.updateAirBackground(this.mPortTheme);
            this.mNavigationBarView.handleBluetoothCallback(true);
        }
        this.mNavigationBarView.setDisabledFlags(this.mDisabledFlags1);
        this.mNavigationBarView.setComponents(this.mRecents, this.mDivider, this.mStatusBar.getPanel());
        this.mNavigationBarView.setOnVerticalChangedListener(new NavigationBarView.OnVerticalChangedListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$eFJm5m1txtISSi8Cx3m3pc8Nvjw
            @Override // com.android.systemui.statusbar.phone.NavigationBarView.OnVerticalChangedListener
            public final void onVerticalChanged(boolean z) {
                this.f$0.onVerticalChanged(z);
            }
        });
        this.mNavigationBarView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$X9JO9eLzlFoQkYf8XrZG-l2EMsk
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.onNavigationTouch(view2, motionEvent);
            }
        });
        if (savedInstanceState != null) {
            this.mNavigationBarView.getLightTransitionsController().restoreState(savedInstanceState);
        }
        prepareNavigationBarView();
        checkNavBarModes();
        setDisabled2Flags(this.mDisabledFlags2);
        IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.USER_SWITCHED");
        filter.addAction("action.themePicker.ui.changeNaviBg");
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, filter, null, null);
        notifyNavigationBarScreenOn();
        this.mOverviewProxyService.addCallback(this.mOverviewProxyListener);
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            onUIStyleChanged((this.mUiMode & 48) == 32 ? "night" : "");
        }
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("NavigationBar", "onAttach...");
        this.myHandler.sendEmptyMessage(2);
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        Log.d("NavigationBar", "onDetach...");
        this.myHandler.removeMessages(2);
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("NavigationBar", "onDestroyView...");
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            this.mNavigationBarView.handleBluetoothCallback(false);
        }
        this.mNavigationBarView.getLightTransitionsController().destroy(getContext());
        this.mOverviewProxyService.removeCallback(this.mOverviewProxyListener);
        getContext().unregisterReceiver(this.mBroadcastReceiver);
        this.mVCController.removeCallback(this.mVCCallback);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("NavigationBar", "onSaveInstanceState...");
        outState.putInt("disabled_state", this.mDisabledFlags1);
        outState.putInt("disabled2_state", this.mDisabledFlags2);
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.getLightTransitionsController().saveState(outState);
        }
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("NavigationBar", "onConfigurationChanged...");
        Locale locale = getContext().getResources().getConfiguration().locale;
        int ld = TextUtils.getLayoutDirectionFromLocale(locale);
        if (!locale.equals(this.mLocale) || ld != this.mLayoutDirection) {
            this.mLocale = locale;
            this.mLayoutDirection = ld;
            refreshLayout(ld);
        }
        repositionNavigationBar();
        if (this.mUiMode != newConfig.uiMode) {
            this.mUiMode = newConfig.uiMode;
            onUIStyleChanged((this.mUiMode & 48) == 32 ? "night" : "");
        }
    }

    @Override // android.app.Fragment
    public void dump(String prefix, FileDescriptor fd, PrintWriter pw, String[] args) {
        if (this.mNavigationBarView != null) {
            pw.print("  mNavigationBarWindowState=");
            pw.println(StatusBarManager.windowStateToString(this.mNavigationBarWindowState));
            pw.print("  mNavigationBarMode=");
            pw.println(BarTransitions.modeToString(this.mNavigationBarMode));
            StatusBar.dumpBarTransitions(pw, "mNavigationBarView", this.mNavigationBarView.getBarTransitions());
        }
        pw.print("  mNavigationBarView=");
        if (this.mNavigationBarView == null) {
            pw.println("null");
        } else {
            this.mNavigationBarView.dump(fd, pw, args);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setImeWindowStatus(IBinder token, int vis, int backDisposition, boolean showImeSwitcher) {
        int hints;
        boolean imeShown = (vis & 2) != 0;
        int hints2 = this.mNavigationIconHints;
        switch (backDisposition) {
            case 0:
            case 1:
            case 2:
                if (imeShown) {
                    hints2 |= 1;
                } else {
                    hints2 &= -2;
                }
                break;
            case 3:
                hints2 &= -2;
                break;
        }
        if (showImeSwitcher) {
            hints = hints2 | 2;
        } else {
            hints = hints2 & (-3);
        }
        if (hints == this.mNavigationIconHints) {
            return;
        }
        this.mNavigationIconHints = hints;
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.setNavigationIconHints(hints);
        }
        this.mStatusBar.checkBarModes();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void topAppWindowChanged(boolean showMenu) {
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.setMenuVisibility(showMenu);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setWindowState(int window, int state) {
        if (this.mNavigationBarView != null && window == 2 && this.mNavigationBarWindowState != state) {
            this.mNavigationBarWindowState = state;
            if (state == 0 && this.mPendingRotationSuggestion) {
                showAndLogRotationSuggestion();
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void onRotationProposal(int rotation, boolean isValid) {
        int style;
        int winRotation = this.mWindowManager.getDefaultDisplay().getRotation();
        boolean rotateSuggestionsDisabled = hasDisable2RotateSuggestionFlag(this.mDisabledFlags2);
        StringBuilder sb = new StringBuilder();
        sb.append("onRotationProposal proposedRotation=");
        sb.append(Surface.rotationToString(rotation));
        sb.append(", winRotation=");
        sb.append(Surface.rotationToString(winRotation));
        sb.append(", isValid=");
        sb.append(isValid);
        sb.append(", mNavBarWindowState=");
        sb.append(StatusBarManager.windowStateToString(this.mNavigationBarWindowState));
        sb.append(", rotateSuggestionsDisabled=");
        sb.append(rotateSuggestionsDisabled);
        sb.append(", isRotateButtonVisible=");
        sb.append(this.mNavigationBarView == null ? "null" : Boolean.valueOf(this.mNavigationBarView.isRotateButtonVisible()));
        Log.v("NavigationBar", sb.toString());
        if (rotateSuggestionsDisabled) {
            return;
        }
        if (!isValid) {
            setRotateSuggestionButtonState(false);
            return;
        }
        if (rotation == winRotation) {
            getView().removeCallbacks(this.mRemoveRotationProposal);
            setRotateSuggestionButtonState(false);
            return;
        }
        this.mLastRotationSuggestion = rotation;
        if (this.mNavigationBarView != null) {
            boolean rotationCCW = isRotationAnimationCCW(winRotation, rotation);
            if (winRotation == 0 || winRotation == 2) {
                style = rotationCCW ? R.style.RotateButtonCCWStart90 : R.style.RotateButtonCWStart90;
            } else {
                style = rotationCCW ? R.style.RotateButtonCCWStart0 : R.style.RotateButtonCWStart0;
            }
            this.mNavigationBarView.updateRotateSuggestionButtonStyle(style, true);
        }
        if (this.mNavigationBarWindowState != 0) {
            this.mPendingRotationSuggestion = true;
            getView().removeCallbacks(this.mCancelPendingRotationProposal);
            getView().postDelayed(this.mCancelPendingRotationProposal, 20000L);
            return;
        }
        showAndLogRotationSuggestion();
    }

    private void onRotationSuggestionsDisabled() {
        setRotateSuggestionButtonState(false, true);
        View v = getView();
        if (v != null) {
            v.removeCallbacks(this.mRemoveRotationProposal);
        }
    }

    private void showAndLogRotationSuggestion() {
        setRotateSuggestionButtonState(true);
        rescheduleRotationTimeout(false);
        this.mMetricsLogger.visible(1288);
    }

    private boolean isRotationAnimationCCW(int from, int to) {
        if (from == 0 && to == 1) {
            return false;
        }
        if (from == 0 && to == 2) {
            return true;
        }
        if (from == 0 && to == 3) {
            return true;
        }
        if (from == 1 && to == 0) {
            return true;
        }
        if (from == 1 && to == 2) {
            return false;
        }
        if (from == 1 && to == 3) {
            return true;
        }
        if (from == 2 && to == 0) {
            return true;
        }
        if (from == 2 && to == 1) {
            return true;
        }
        if (from == 2 && to == 3) {
            return false;
        }
        if (from == 3 && to == 0) {
            return false;
        }
        if (from == 3 && to == 1) {
            return true;
        }
        return from == 3 && to == 2;
    }

    public void setRotateSuggestionButtonState(boolean visible) {
        setRotateSuggestionButtonState(visible, false);
    }

    public void setRotateSuggestionButtonState(boolean visible, boolean force) {
        View view;
        KeyButtonDrawable kbd;
        if (this.mNavigationBarView == null) {
            return;
        }
        ButtonDispatcher rotBtn = this.mNavigationBarView.getRotateSuggestionButton();
        boolean currentlyVisible = this.mNavigationBarView.isRotateButtonVisible();
        if ((!visible && !currentlyVisible) || (view = rotBtn.getCurrentView()) == null || (kbd = rotBtn.getImageDrawable()) == null) {
            return;
        }
        AnimatedVectorDrawable animIcon = null;
        if (kbd.getDrawable(0) instanceof AnimatedVectorDrawable) {
            animIcon = (AnimatedVectorDrawable) kbd.getDrawable(0);
        }
        this.mPendingRotationSuggestion = false;
        if (getView() != null) {
            getView().removeCallbacks(this.mCancelPendingRotationProposal);
        }
        if (visible) {
            if (this.mRotateHideAnimator != null && this.mRotateHideAnimator.isRunning()) {
                this.mRotateHideAnimator.cancel();
            }
            this.mRotateHideAnimator = null;
            view.setAlpha(1.0f);
            if (animIcon != null) {
                animIcon.reset();
                animIcon.start();
            }
            if (!isRotateSuggestionIntroduced()) {
                this.mViewRippler.start(view);
            }
            int appliedVisibility = this.mNavigationBarView.setRotateButtonVisibility(true);
            if (appliedVisibility == 0) {
                this.mStatusBar.touchAutoHide();
                return;
            }
            return;
        }
        this.mViewRippler.stop();
        if (force) {
            if (this.mRotateHideAnimator != null && this.mRotateHideAnimator.isRunning()) {
                this.mRotateHideAnimator.pause();
            }
            this.mNavigationBarView.setRotateButtonVisibility(false);
            return;
        }
        if (this.mRotateHideAnimator == null || !this.mRotateHideAnimator.isRunning()) {
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 0.0f);
            fadeOut.setDuration(100L);
            fadeOut.setInterpolator(Interpolators.LINEAR);
            fadeOut.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.NavigationBarFragment.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    NavigationBarFragment.this.mNavigationBarView.setRotateButtonVisibility(false);
                }
            });
            this.mRotateHideAnimator = fadeOut;
            fadeOut.start();
        }
    }

    private void rescheduleRotationTimeout(boolean reasonHover) {
        if (!reasonHover || ((this.mRotateHideAnimator == null || !this.mRotateHideAnimator.isRunning()) && this.mNavigationBarView.isRotateButtonVisible())) {
            getView().removeCallbacks(this.mRemoveRotationProposal);
            getView().postDelayed(this.mRemoveRotationProposal, computeRotationProposalTimeout());
        }
    }

    private int computeRotationProposalTimeout() {
        if (this.mAccessibilityFeedbackEnabled) {
            return 20000;
        }
        return this.mHoveringRotationSuggestion ? 16000 : 10000;
    }

    private boolean isRotateSuggestionIntroduced() {
        ContentResolver cr = getContext().getContentResolver();
        return Settings.Secure.getInt(cr, "num_rotation_suggestions_accepted", 0) >= 3;
    }

    private void incrementNumAcceptedRotationSuggestionsIfNeeded() {
        ContentResolver cr = getContext().getContentResolver();
        int numSuggestions = Settings.Secure.getInt(cr, "num_rotation_suggestions_accepted", 0);
        if (numSuggestions < 3) {
            Settings.Secure.putInt(cr, "num_rotation_suggestions_accepted", numSuggestions + 1);
        }
    }

    public void setCurrentSysuiVisibility(int systemUiVisibility) {
        this.mSystemUiVisibility = systemUiVisibility;
        this.mNavigationBarMode = this.mStatusBar.computeBarMode(0, this.mSystemUiVisibility, 134217728, Integer.MIN_VALUE, 32768);
        checkNavBarModes();
        this.mStatusBar.touchAutoHide();
        this.mLightBarController.onNavigationVisibilityChanged(this.mSystemUiVisibility, 0, true, this.mNavigationBarMode);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setSystemUiVisibility(int vis, int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenStackBounds, Rect dockedStackBounds) {
        int oldVal = this.mSystemUiVisibility;
        int newVal = ((~mask) & oldVal) | (vis & mask);
        int diff = newVal ^ oldVal;
        boolean nbModeChanged = false;
        if (diff != 0) {
            this.mSystemUiVisibility = newVal;
            int nbMode = getView() == null ? -1 : this.mStatusBar.computeBarMode(oldVal, newVal, 134217728, Integer.MIN_VALUE, 32768);
            nbModeChanged = nbMode != -1;
            if (nbModeChanged) {
                if (this.mNavigationBarMode != nbMode) {
                    this.mNavigationBarMode = nbMode;
                    checkNavBarModes();
                }
                this.mStatusBar.touchAutoHide();
            }
        }
        this.mLightBarController.onNavigationVisibilityChanged(vis, mask, nbModeChanged, this.mNavigationBarMode);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void disable(int state1, int state2, boolean animate) {
        int masked = 56623104 & state1;
        if (masked != this.mDisabledFlags1) {
            this.mDisabledFlags1 = masked;
            if (this.mNavigationBarView != null) {
                this.mNavigationBarView.setDisabledFlags(state1);
            }
            updateScreenPinningGestures();
        }
        int masked2 = state2 & 16;
        if (masked2 != this.mDisabledFlags2) {
            this.mDisabledFlags2 = masked2;
            setDisabled2Flags(masked2);
        }
    }

    private void setDisabled2Flags(int state2) {
        boolean rotateSuggestionsDisabled = hasDisable2RotateSuggestionFlag(state2);
        if (rotateSuggestionsDisabled) {
            onRotationSuggestionsDisabled();
        }
    }

    private boolean hasDisable2RotateSuggestionFlag(int disable2Flags) {
        return (disable2Flags & 16) != 0;
    }

    private void refreshLayout(int layoutDirection) {
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.setLayoutDirection(layoutDirection);
        }
    }

    private boolean shouldDisableNavbarGestures() {
        return (this.mStatusBar.isDeviceProvisioned() && (this.mDisabledFlags1 & 33554432) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void repositionNavigationBar() {
        if (this.mNavigationBarView == null || !this.mNavigationBarView.isAttachedToWindow()) {
            return;
        }
        prepareNavigationBarView();
        this.mWindowManager.updateViewLayout((View) this.mNavigationBarView.getParent(), ((View) this.mNavigationBarView.getParent()).getLayoutParams());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScreenPinningGestures() {
        if (this.mNavigationBarView == null) {
            return;
        }
        boolean recentsVisible = this.mNavigationBarView.isRecentsButtonVisible();
        ButtonDispatcher backButton = this.mNavigationBarView.getBackButton();
        if (recentsVisible) {
            backButton.setOnLongClickListener(new $$Lambda$NavigationBarFragment$dtGeJfWz2E4_XAoQgX8peIw4kU8(this));
        } else {
            backButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$oZtQ9jE1OTI8AtitIxsN6ETT4sc
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return this.f$0.onLongPressBackHome(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNavigationBarScreenOn() {
        this.mNavigationBarView.updateNavButtonIcons();
    }

    private void prepareNavigationBarView() {
        this.mNavigationBarView.reorient();
        ButtonDispatcher recentsButton = this.mNavigationBarView.getRecentsButton();
        recentsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$0mmLLxBq7RxotphHQB_RtYb4SpQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onRecentsClick(view);
            }
        });
        recentsButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$VEqqEZFjg0f3lWOW2BJ66Oo_2aE
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.onRecentsTouch(view, motionEvent);
            }
        });
        recentsButton.setLongClickable(true);
        recentsButton.setOnLongClickListener(new $$Lambda$NavigationBarFragment$dtGeJfWz2E4_XAoQgX8peIw4kU8(this));
        ButtonDispatcher backButton = this.mNavigationBarView.getBackButton();
        backButton.setLongClickable(true);
        ButtonDispatcher homeButton = this.mNavigationBarView.getHomeButton();
        homeButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$y_1OHmWTpLl8uCcO3A0Am620g94
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.onHomeTouch(view, motionEvent);
            }
        });
        homeButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$8vcstZEv0YyG7EUTK_UrsNSFXRo
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.onHomeLongClick(view);
            }
        });
        ButtonDispatcher accessibilityButton = this.mNavigationBarView.getAccessibilityButton();
        accessibilityButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$Ylizyb5K7ZQr77j1Ehc8SUjcI6E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onAccessibilityClick(view);
            }
        });
        accessibilityButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$RtBTLxltRKo37YrTKiaCXCxwRDg
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.onAccessibilityLongClick(view);
            }
        });
        updateAccessibilityServicesState(this.mAccessibilityManager);
        ButtonDispatcher rotateSuggestionButton = this.mNavigationBarView.getRotateSuggestionButton();
        rotateSuggestionButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$-5zWkb0xwQ86wYhCk6W_5t9CDxE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onRotateSuggestionClick(view);
            }
        });
        rotateSuggestionButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$rOeIdAxSsC2NXHBJKZPXVBzZPfI
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                return this.f$0.onRotateSuggestionHover(view, motionEvent);
            }
        });
        updateScreenPinningGestures();
        View volAdd = this.mNavigationBarView.getVolAddButton();
        if (volAdd != null) {
            volAdd.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$qK6bO8B8PyRynH_fyvNyzcIwATA
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.onAutoHideTouch(view, motionEvent);
                }
            });
        }
        View volReduce = this.mNavigationBarView.getVolReduceButton();
        if (volReduce != null) {
            volReduce.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$qK6bO8B8PyRynH_fyvNyzcIwATA
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.onAutoHideTouch(view, motionEvent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onAutoHideTouch(View v, MotionEvent event) {
        this.mStatusBar.touchAutoHide();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:19:0x0041  */
    public boolean onHomeTouch(View v, MotionEvent event) {
        if (this.mHomeBlockedThisTouch && event.getActionMasked() != 0) {
            return true;
        }
        int action = event.getAction();
        if (action != 3) {
            switch (action) {
                case 0:
                    this.mHomeBlockedThisTouch = false;
                    TelecomManager telecomManager = (TelecomManager) getContext().getSystemService(TelecomManager.class);
                    if (telecomManager != null && telecomManager.isRinging() && this.mStatusBar.isKeyguardShowing()) {
                        Log.i("NavigationBar", "Ignoring HOME; there's a ringing incoming call. No heads up");
                        this.mHomeBlockedThisTouch = true;
                        return true;
                    }
                    break;
                case 1:
                    this.mStatusBar.awakenDreams();
                    break;
            }
        } else {
            this.mStatusBar.awakenDreams();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVerticalChanged(boolean isVertical) {
        this.mStatusBar.setQsScrimEnabled(!isVertical);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onNavigationTouch(View v, MotionEvent event) {
        this.mStatusBar.checkUserAutohide(event);
        return false;
    }

    boolean onHomeLongClick(View v) {
        if (!this.mNavigationBarView.isRecentsButtonVisible() && ActivityManagerWrapper.getInstance().isScreenPinningActive()) {
            return onLongPressBackHome(v);
        }
        if (shouldDisableNavbarGestures()) {
            return false;
        }
        this.mNavigationBarView.onNavigationButtonLongPress(v);
        this.mMetricsLogger.action(239);
        this.mAssistManager.startAssist(new Bundle());
        this.mStatusBar.awakenDreams();
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.abortCurrentGesture();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onRecentsTouch(View v, MotionEvent event) {
        int action = event.getAction() & 255;
        if (action == 0) {
            this.mCommandQueue.preloadRecentApps();
            return false;
        }
        if (action == 3) {
            this.mCommandQueue.cancelPreloadRecentApps();
            return false;
        }
        if (action == 1 && !v.isPressed()) {
            this.mCommandQueue.cancelPreloadRecentApps();
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRecentsClick(View v) {
        if (LatencyTracker.isEnabled(getContext())) {
            LatencyTracker.getInstance(getContext()).onActionStart(1);
        }
        this.mStatusBar.awakenDreams();
        this.mCommandQueue.toggleRecentApps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onLongPressBackHome(View v) {
        this.mNavigationBarView.onNavigationButtonLongPress(v);
        return onLongPressNavigationButtons(v, R.id.back, R.id.home);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onLongPressBackRecents(View v) {
        this.mNavigationBarView.onNavigationButtonLongPress(v);
        return onLongPressNavigationButtons(v, R.id.back, R.id.recent_apps);
    }

    private boolean onLongPressNavigationButtons(View v, int btnId1, int btnId2) {
        ButtonDispatcher button;
        boolean sendBackLongPress = false;
        try {
            IActivityManager activityManager = ActivityManagerNative.getDefault();
            boolean touchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
            boolean inLockTaskMode = activityManager.isInLockTaskMode();
            if (inLockTaskMode && !touchExplorationEnabled) {
                long time = System.currentTimeMillis();
                if (time - this.mLastLockToAppLongPress < 200) {
                    activityManager.stopSystemLockTaskMode();
                    this.mNavigationBarView.updateNavButtonIcons();
                    return true;
                }
                if (v.getId() == btnId1) {
                    if (btnId2 == R.id.recent_apps) {
                        button = this.mNavigationBarView.getRecentsButton();
                    } else {
                        button = this.mNavigationBarView.getHomeButton();
                    }
                    if (!button.getCurrentView().isPressed()) {
                        sendBackLongPress = true;
                    }
                }
                this.mLastLockToAppLongPress = time;
            } else if (v.getId() == btnId1) {
                sendBackLongPress = true;
            } else if (!touchExplorationEnabled || !inLockTaskMode) {
                if (v.getId() == btnId2) {
                    if (btnId2 == R.id.recent_apps) {
                        return onLongPressRecents();
                    }
                    return onHomeLongClick(this.mNavigationBarView.getHomeButton().getCurrentView());
                }
            } else {
                activityManager.stopSystemLockTaskMode();
                this.mNavigationBarView.updateNavButtonIcons();
                return true;
            }
            if (sendBackLongPress) {
                KeyButtonView keyButtonView = (KeyButtonView) v;
                keyButtonView.sendEvent(0, 128);
                keyButtonView.sendAccessibilityEvent(2);
                return true;
            }
        } catch (RemoteException e) {
            Log.d("NavigationBar", "Unable to reach activity manager", e);
        }
        return false;
    }

    private boolean onLongPressRecents() {
        if (this.mRecents == null || !ActivityManager.supportsMultiWindow(getContext()) || this.mDivider.getView() == null || !this.mDivider.getView().getSnapAlgorithm().isSplitScreenFeasible() || Recents.getConfiguration().isLowRamDevice || this.mOverviewProxyService.getProxy() != null) {
            return false;
        }
        return this.mStatusBar.toggleSplitScreenMode(271, 286);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityClick(View v) {
        this.mAccessibilityManager.notifyAccessibilityButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onAccessibilityLongClick(View v) {
        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
        intent.addFlags(268468224);
        v.getContext().startActivityAsUser(intent, UserHandle.CURRENT);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAccessibilityServicesState(AccessibilityManager accessibilityManager) {
        boolean showAccessibilityButton;
        boolean targetSelection = false;
        int requestingServices = 0;
        try {
            if (Settings.Secure.getIntForUser(this.mContentResolver, "accessibility_display_magnification_navbar_enabled", -2) == 1) {
                requestingServices = 0 + 1;
            }
        } catch (Settings.SettingNotFoundException e) {
        }
        boolean feedbackEnabled = false;
        List<AccessibilityServiceInfo> services = accessibilityManager.getEnabledAccessibilityServiceList(-1);
        for (int i = services.size() - 1; i >= 0; i--) {
            AccessibilityServiceInfo info = services.get(i);
            if ((info.flags & 256) != 0) {
                requestingServices++;
            }
            if (info.feedbackType != 0 && info.feedbackType != 16) {
                feedbackEnabled = true;
            }
        }
        this.mAccessibilityFeedbackEnabled = feedbackEnabled;
        if (requestingServices < 1) {
            showAccessibilityButton = false;
        } else {
            showAccessibilityButton = true;
        }
        if (requestingServices >= 2) {
            targetSelection = true;
        }
        this.mNavigationBarView.setAccessibilityButtonState(showAccessibilityButton, targetSelection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRotateSuggestionClick(View v) {
        this.mMetricsLogger.action(1287);
        incrementNumAcceptedRotationSuggestionsIfNeeded();
        this.mRotationLockController.setRotationLockedAtAngle(true, this.mLastRotationSuggestion);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onRotateSuggestionHover(View v, MotionEvent event) {
        int action = event.getActionMasked();
        this.mHoveringRotationSuggestion = action == 9 || action == 7;
        rescheduleRotationTimeout(true);
        return false;
    }

    public void setLightBarController(LightBarController lightBarController) {
        this.mLightBarController = lightBarController;
        this.mLightBarController.setNavigationBar(this.mNavigationBarView.getLightTransitionsController());
    }

    public boolean isSemiTransparent() {
        return this.mNavigationBarMode == 1;
    }

    public void disableAnimationsDuringHide(long delay) {
        this.mNavigationBarView.setLayoutTransitionsEnabled(false);
        this.mNavigationBarView.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$wHCoB9XA9DosUU7aBE6XFRCVIq8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mNavigationBarView.setLayoutTransitionsEnabled(true);
            }
        }, 448 + delay);
    }

    public BarTransitions getBarTransitions() {
        return this.mNavigationBarView.getBarTransitions();
    }

    public void checkNavBarModes() {
        this.mStatusBar.checkBarMode(this.mNavigationBarMode, this.mNavigationBarWindowState, this.mNavigationBarView.getBarTransitions());
    }

    public void finishBarAnimations() {
        this.mNavigationBarView.getBarTransitions().finishAnimations();
    }

    private class MagnificationContentObserver extends ContentObserver {
        public MagnificationContentObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            NavigationBarFragment.this.updateAccessibilityServicesState(NavigationBarFragment.this.mAccessibilityManager);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.NavigationBarFragment$3, reason: invalid class name */
    class AnonymousClass3 extends IRotationWatcher.Stub {
        AnonymousClass3() {
        }

        public void onRotationChanged(final int rotation) throws RemoteException {
            Handler h = NavigationBarFragment.this.getView().getHandler();
            Message msg = Message.obtain(h, new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$3$kMvCAK_G5IzeGu5joS3NsFaWUXs
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBarFragment.AnonymousClass3.lambda$onRotationChanged$0(this.f$0, rotation);
                }
            });
            msg.setAsynchronous(true);
            h.sendMessageAtFrontOfQueue(msg);
        }

        public static /* synthetic */ void lambda$onRotationChanged$0(AnonymousClass3 anonymousClass3, int rotation) {
            if (NavigationBarFragment.this.mRotationLockController.isRotationLocked()) {
                if (anonymousClass3.shouldOverrideUserLockPrefs(rotation)) {
                    NavigationBarFragment.this.mRotationLockController.setRotationLockedAtAngle(true, rotation);
                }
                NavigationBarFragment.this.setRotateSuggestionButtonState(false, true);
            }
            if (NavigationBarFragment.this.mNavigationBarView != null && NavigationBarFragment.this.mNavigationBarView.needsReorient(rotation)) {
                NavigationBarFragment.this.repositionNavigationBar();
            }
        }

        private boolean shouldOverrideUserLockPrefs(int rotation) {
            return rotation == 0;
        }
    }

    public void changeBottomBgByThemePicker() {
        String skinName;
        if (SystemUIApplication.hasBottomBar() && (skinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1")) != null) {
            String skinName2 = skinName.toLowerCase();
            if (skinName2.equals("xt553")) {
                skinName2 = "xt540";
            }
            String resName = skinName2 + "_bottom_bg";
            Drawable drawable = SkinCompatResources.getInstance().getDrawable(resName);
            if (drawable != null) {
                this.bottomBg.setBackground(drawable);
            } else {
                String SysUINaviHeight = com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", "");
                String resName2 = skinName2 + "_bottom_bg_" + SysUINaviHeight;
                this.bottomBg.setBackground(SkinCompatResources.getInstance().getDrawable(resName2));
            }
            if ("1".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin_bg_support", ""))) {
                String SysUIName = com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "");
                if ("xt540".equals(SysUIName) || "xt553".equals(SysUIName) || "xt646".equals(SysUIName)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt540_bottom_bg_two));
                } else if ("xt554".equals(SysUIName)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt554_bottom_bg_two));
                } else if ("xt555".equals(SysUIName)) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt555_bottom_bg_two));
                }
            }
            if ("190".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                if ("xt633".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt633_bottom_bg_190));
                } else if ("xt634".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt634_bottom_bg_190));
                } else if ("xt635".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt635_bottom_bg_190));
                } else if ("xt636".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt636_bottom_bg_190));
                } else if ("xt637".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt637_bottom_bg_190));
                }
            }
            if ("210".equals(com.android.systemui.util.Utils.getSystemProperty("ro.sys.customer.naviheight", ""))) {
                if ("xt633".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt633_bottom_bg_210));
                    return;
                }
                if ("xt634".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt634_bottom_bg_210));
                    return;
                }
                if ("xt635".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt635_bottom_bg_210));
                } else if ("xt636".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt636_bottom_bg_210));
                } else if ("xt637".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
                    this.bottomBg.setBackground(getContext().getResources().getDrawable(R.drawable.xt637_bottom_bg_210));
                }
            }
        }
    }

    class TaskStackListenerImpl extends SysUiTaskStackChangeListener {
        TaskStackListenerImpl() {
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChanged() {
            NavigationBarFragment.this.setRotateSuggestionButtonState(false);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskRemoved(int taskId) {
            NavigationBarFragment.this.setRotateSuggestionButtonState(false);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskMovedToFront(int taskId) {
            NavigationBarFragment.this.setRotateSuggestionButtonState(false);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityRequestedOrientationChanged(final int taskId, int requestedOrientation) {
            Optional.ofNullable(ActivityManagerWrapper.getInstance()).map(new Function() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$Zm3Yj0EQnVWvu_ZksQ-OsrTwJ3k
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ActivityManagerWrapper) obj).getRunningTask();
                }
            }).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$TaskStackListenerImpl$WOZBpkylBist4A7EGTrKlHaP-0c
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NavigationBarFragment.TaskStackListenerImpl.lambda$onActivityRequestedOrientationChanged$0(this.f$0, taskId, (ActivityManager.RunningTaskInfo) obj);
                }
            });
        }

        public static /* synthetic */ void lambda$onActivityRequestedOrientationChanged$0(TaskStackListenerImpl taskStackListenerImpl, int taskId, ActivityManager.RunningTaskInfo a) {
            if (a.id == taskId) {
                NavigationBarFragment.this.setRotateSuggestionButtonState(false);
            }
        }
    }

    private class ViewRippler {
        private final Runnable mRipple;
        private View mRoot;

        private ViewRippler() {
            this.mRipple = new Runnable() { // from class: com.android.systemui.statusbar.phone.NavigationBarFragment.ViewRippler.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ViewRippler.this.mRoot.isAttachedToWindow()) {
                        ViewRippler.this.mRoot.setPressed(true);
                        ViewRippler.this.mRoot.setPressed(false);
                    }
                }
            };
        }

        public void start(View root) {
            stop();
            this.mRoot = root;
            this.mRoot.postOnAnimationDelayed(this.mRipple, 50L);
            this.mRoot.postOnAnimationDelayed(this.mRipple, 2000L);
            this.mRoot.postOnAnimationDelayed(this.mRipple, 4000L);
            this.mRoot.postOnAnimationDelayed(this.mRipple, 6000L);
            this.mRoot.postOnAnimationDelayed(this.mRipple, 8000L);
        }

        public void stop() {
            if (this.mRoot != null) {
                this.mRoot.removeCallbacks(this.mRipple);
            }
        }
    }

    public static View create(Context context, FragmentHostManager.FragmentListener listener) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1, 2019, 545521768, -3);
        lp.token = new Binder();
        lp.setTitle("NavigationBar");
        lp.accessibilityTitle = context.getString(R.string.nav_bar);
        lp.windowAnimations = 0;
        View navigationBarView = LayoutInflater.from(context).inflate(R.layout.navigation_bar_window, (ViewGroup) null);
        if (navigationBarView == null) {
            return null;
        }
        ((WindowManager) context.getSystemService(WindowManager.class)).addView(navigationBarView, lp);
        FragmentHostManager fragmentHost = FragmentHostManager.get(navigationBarView);
        NavigationBarFragment fragment = new NavigationBarFragment();
        fragmentHost.getFragmentManager().beginTransaction().replace(R.id.navigation_bar_frame, fragment, "NavigationBar").commit();
        fragmentHost.addTagListener("NavigationBar", listener);
        return navigationBarView;
    }

    public void updateAir(AIR air_obj) {
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.updateAir(air_obj);
        }
    }

    public void showAirData(boolean show) {
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.showAirData(show);
        }
    }

    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Method getter = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
            String value = (String) getter.invoke(null, property);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
            Log.d("NavigationBar", "Unable to read system properties");
        }
    }

    private void onUIStyleChanged(String uiStyle) {
        Log.d("NavigationBar", "onUIStyleChanged...");
        Context mContext = getContext();
        if (mContext == null) {
            Log.d("NavigationBar", "mContext = null, return");
            return;
        }
        if (this.mNavigationBarView == null) {
            Log.d("NavigationBar", "mNavigationBarView == null, return");
            return;
        }
        String mPackageName = SkinCompatResources.getInstance().getSkinPkgName();
        String skinName = mPackageName.substring(mPackageName.lastIndexOf(".") + 1);
        String[] resIds = {"btn_back", "hcn_home", "vol_add", "vol_reduce", "btn_bt_bg", "bottom_bg"};
        if (skinName.equals("sa181")) {
            if (!SystemUIApplication.isPortraitWindowMode(getContext())) {
                return;
            }
            for (String resId : resIds) {
                changeDrawable(resId, skinName, uiStyle);
            }
        } else {
            if ("night".equals(uiStyle)) {
                this.bottomBgPortResName = "bottom_bg_" + skinName + "_night";
                Log.e("NavigationBar", "----bottom_bg_" + skinName + "_night");
            } else {
                this.bottomBgPortResName = "bottom_bg_" + skinName;
                Log.e("NavigationBar", "----bottom_bg_" + skinName);
            }
            this.bottomBgPortDrawable = SkinCompatResources.getInstance().getDrawable(this.bottomBgPortResName);
            if (this.bottomBgPortDrawable != null) {
                Log.e("NavigationBar", "----" + this.bottomBgPortResName + "---not null");
                if (this.portBottomBg != null) {
                    Log.e("NavigationBar", "----" + this.bottomBgPortResName + "--- portBottomBg not null");
                    this.portBottomBg.setBackground(this.bottomBgPortDrawable);
                }
            } else {
                changeDrawable("bottom_bg", skinName, uiStyle);
                changeDrawable("air_bg", skinName, uiStyle);
            }
        }
        if (SystemUIApplication.hasBottomBar()) {
            updateBottomBg(uiStyle);
        }
    }

    public void changeDrawable(String resId, String skinName, String uiStyle) {
        String str;
        View view;
        String resName = resId + "_" + skinName;
        if ("".equals(uiStyle)) {
            str = resName;
        } else {
            str = resName + "_" + uiStyle;
        }
        String resName2 = str;
        Log.d("NavigationBar", "update resName = " + resName2);
        final Drawable drawable = SkinCompatResources.getInstance().getDrawable(resName2);
        switch (resId) {
            case "btn_back":
                view = this.mNavigationBarView.getMenuBackButton();
                break;
            case "hcn_home":
                view = this.mNavigationBarView.getMenuHomeButton();
                break;
            case "vol_add":
                view = this.mNavigationBarView.getVolAddButton();
                break;
            case "vol_reduce":
                view = this.mNavigationBarView.getVolReduceButton();
                break;
            case "btn_bt_bg":
                view = this.mNavigationBarView.getBtButton();
                break;
            default:
                view = this.mNavigationBarView.getChildAt(0);
                break;
        }
        if (drawable != null && view != null) {
            if (resId.equals("air_bg")) {
                this.mNavigationBarView.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$N0-MIeUZw_3HtKEJ8zO8FHrjBDk
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mNavigationBarView.updateAirBg(drawable);
                    }
                });
                this.mNavigationBarView.postInvalidate();
            } else {
                final View finalView = view;
                view.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$2nAmgT3lG24AoKreSiih5qpmMi8
                    @Override // java.lang.Runnable
                    public final void run() {
                        finalView.setBackground(drawable);
                    }
                });
                view.postInvalidate();
            }
        }
    }

    public void updateBottomBg(String uiStyle) {
        String resName;
        String skinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
        if (skinName != null) {
            if (isNewBottomUI(skinName)) {
                int configChangeTheme = SkinCompatResources.getInstance().getInteger("config_change_theme");
                if (configChangeTheme == 1) {
                    this.mNavigationBarView.updateBottomBg(uiStyle);
                    return;
                }
                return;
            }
            String skinName2 = skinName.toLowerCase();
            if (skinName2.equals("xt520")) {
                if (uiStyle.equals("night")) {
                    resName = skinName2 + "_bottom_bg_" + uiStyle;
                } else {
                    resName = skinName2 + "_bottom_bg";
                }
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(resName);
                if (drawable != null) {
                    this.bottomBg.setBackground(drawable);
                    this.mNavigationBarView.updateBottomBg(uiStyle);
                }
            }
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<NavigationBarFragment> mWeakReference;

        public MyHandler(NavigationBarFragment navigationBarFragment) {
            this.mWeakReference = new WeakReference<>(navigationBarFragment);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            NavigationBarFragment navigationBarFragment = this.mWeakReference.get();
            if (navigationBarFragment != null && msg.what == 100) {
                Log.e("NavigationBar", "changeBottomBgByThemePicker---handler" + NavigationBarFragment.getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1"));
                this.mWeakReference.get().changeBottomBgByThemePicker();
            }
        }
    }

    public void restart() {
        try {
            FragmentHostManager fragmentHost = FragmentHostManager.get(this.mNavigationBarView);
            FragmentTransaction transaction = fragmentHost.getFragmentManager().beginTransaction();
            NavigationBarFragment navigationBarFragment = new NavigationBarFragment();
            transaction.detach(this).remove(this).replace(R.id.navigation_bar_frame, navigationBarFragment, "NavigationBar").commit();
        } catch (Exception e) {
            Log.e("NavigationBar", e.toString());
        }
    }
}
