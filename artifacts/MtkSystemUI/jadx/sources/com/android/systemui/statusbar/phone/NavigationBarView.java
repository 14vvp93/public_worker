package com.android.systemui.statusbar.phone;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.carsource.McuManager;
import android.carstatus.CarStatus;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.RecentsComponent;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.statusbar.phone.NavGesture;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsOnboarding;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.WindowManagerWrapper;
import com.android.systemui.stackdivider.Divider;
import com.android.systemui.statusbar.DZ23CanStatusBarController;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.DeadZone;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.TintedKeyButtonDrawable;
import com.android.systemui.volume.JellyProgressView;
import com.hcn.auto.systemui.InitViewImpl;
import com.hcn.auto.systemui.SkinApkManager;
import com.hcn.autocan.aidl.AirCtrlManager;
import com.hcn.autocan.hiworld.gm.air.AIR;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.mediatek.systemui.ext.INavigationBarPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarView extends FrameLayout implements View.OnTouchListener, PluginListener<NavGesture> {
    private final int AC_Type_acauto;
    private final int AC_Type_econ;
    private final int AC_Type_max;
    private final int AC_Type_normal;
    private final int AC_Type_null;
    private final String AIR_TEMP_REVERSE_FLAG;
    private final int Auto_Type_big;
    private final int Auto_Type_null;
    private final int Auto_Type_small;
    private boolean curtAirTempMirror;
    private final int cycle_Type_NULL;
    private final int cycle_Type_auto;
    private final int cycle_Type_in;
    private final int cycle_Type_out;
    private JellyProgressView jellyProgressView;
    private LinearLayout layout_air_func;
    private KeyButtonDrawable mAccessibilityIcon;
    private LinearLayout mAirLayout;
    private final ContentObserver mAirTempMirrorCfgObserver;
    protected AudioManager mAudioManager;
    private Handler mAutoDismissHandler;
    private Runnable mAutoDismissRunnable;
    private Button mBTConnectStatus;
    private KeyButtonDrawable mBackAltCarModeIcon;
    private KeyButtonDrawable mBackAltLandCarModeIcon;
    private final Rect mBackButtonBounds;
    private KeyButtonDrawable mBackCarModeIcon;
    private KeyButtonDrawable mBackIcon;
    private KeyButtonDrawable mBackLandCarModeIcon;
    private final NavigationBarTransitions mBarTransitions;
    private final SparseArray<ButtonDispatcher> mButtonDispatchers;
    private final BluetoothController.Callback mCallback;
    private boolean mChangebySelf;
    private Configuration mConfiguration;
    private final BluetoothController mController;
    private int mCurrentRotation;
    View mCurrentView;
    private final DeadZone mDeadZone;
    private boolean mDeadZoneConsuming;
    int mDisabledFlags;
    final Display mDisplay;
    private Divider mDivider;
    private KeyButtonDrawable mDockedIcon;
    private final Consumer<Boolean> mDockedListener;
    private boolean mDockedStackExists;
    private int mDownHitTarget;
    private int mFront_RZC;
    private NavGesture.GestureHelper mGestureHelper;
    private H mHandler;
    private final Runnable mHideMediaBtnRunnable;
    private final Rect mHomeButtonBounds;
    private KeyButtonDrawable mHomeCarModeIcon;
    private KeyButtonDrawable mHomeDefaultIcon;
    private KeyButtonDrawable mImeIcon;
    private final View.OnClickListener mImeSwitcherClickListener;
    private boolean mInCarMode;
    private InitViewImpl mInitView;
    private boolean mLayoutTransitionsEnabled;
    boolean mLongClickableAccessibilityButton;
    private Handler mMediaBtnHandler;
    private KeyButtonDrawable mMenuIcon;
    private INavigationBarPlugin mNavBarPlugin;
    int mNavigationIconHints;
    private NavigationBarInflaterView mNavigationInflaterView;
    private OnVerticalChangedListener mOnVerticalChangedListener;
    private final OverviewProxyService mOverviewProxyService;
    protected String mPackageName;
    private NotificationPanelView mPanelView;
    private final View.AccessibilityDelegate mQuickStepAccessibilityDelegate;
    private int mRearLock;
    private KeyButtonDrawable mRecentIcon;
    private final Rect mRecentsButtonBounds;
    private RecentsComponent mRecentsComponent;
    private RecentsOnboarding mRecentsOnboarding;
    protected Resources mResources;
    private int mRotateBtnStyle;
    private TintedKeyButtonDrawable mRotateSuggestionIcon;
    View[] mRotatedViews;
    private final Rect mRotationButtonBounds;
    private SeekBar mSeekBarVolume;
    boolean mShowAccessibilityButton;
    boolean mShowMenu;
    boolean mShowRotateButton;
    private OpSystemUICustomizationFactoryBase mSystemUICustomizationFactory;
    private final int[] mTmpPosition;
    private final Rect mTmpRect;
    private final NavTransitionListener mTransitionListener;
    private TextView mTvSoundVolume;
    private boolean mUseCarModeUi;
    boolean mVertical;
    private AlertDialog mVolumeDialog;
    private BroadcastReceiver mVolumeReceiver;
    private boolean mWakeAndUnlocking;
    private final BroadcastReceiver myReceiver;
    private ImageButton naviBottomNext;
    private ImageButton naviBottomPre;
    int old_level_max;
    String skinName;

    public interface OnVerticalChangedListener {
        void onVerticalChanged(boolean z);
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.NavigationBarView$2, reason: invalid class name */
    class AnonymousClass2 extends ContentObserver {
        AnonymousClass2(Handler x0) {
            super(x0);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            NavigationBarView.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarView$2$qNlmQkFxA0m02qvDv0pcdA9AnmI
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBarView.this.checkOrSwapAirTemp();
                }
            });
        }
    }

    @Override // android.view.View.OnTouchListener
    @SuppressLint({"NonConstantResourceId"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getAction() == 2) {
            return false;
        }
        try {
            int id = SkinCompatResources.getInstance().getViewId(view);
            if (id == R.id.btn_left_temp_add) {
                if (this.curtAirTempMirror) {
                    AirCtrlManager.getInstance().airCtrl(6, motionEvent.getAction());
                } else {
                    AirCtrlManager.getInstance().airCtrl(4, motionEvent.getAction());
                }
            } else if (id == R.id.btn_left_temp_dec) {
                if (this.curtAirTempMirror) {
                    AirCtrlManager.getInstance().airCtrl(7, motionEvent.getAction());
                } else {
                    AirCtrlManager.getInstance().airCtrl(5, motionEvent.getAction());
                }
            } else if (id == R.id.btn_right_temp_add) {
                if (this.curtAirTempMirror) {
                    AirCtrlManager.getInstance().airCtrl(4, motionEvent.getAction());
                } else {
                    AirCtrlManager.getInstance().airCtrl(6, motionEvent.getAction());
                }
            } else if (id == R.id.btn_right_temp_dec) {
                if (this.curtAirTempMirror) {
                    AirCtrlManager.getInstance().airCtrl(5, motionEvent.getAction());
                } else {
                    AirCtrlManager.getInstance().airCtrl(7, motionEvent.getAction());
                }
            } else if (id == R.id.btn_fan_add) {
                AirCtrlManager.getInstance().airCtrl(2, motionEvent.getAction());
            } else if (id == R.id.btn_fan_dec) {
                AirCtrlManager.getInstance().airCtrl(3, motionEvent.getAction());
            } else if (id == R.id.btn_on_off) {
                AirCtrlManager.getInstance().airCtrl(1, motionEvent.getAction());
            } else if (id == R.id.btn_ac) {
                AirCtrlManager.getInstance().airCtrl(16, motionEvent.getAction());
            }
        } catch (Exception e) {
            Log.i("StatusBar/NavBarView", "err " + e.getMessage());
        }
        return false;
    }

    private class NavTransitionListener implements LayoutTransition.TransitionListener {
        private boolean mBackTransitioning;
        private long mDuration;
        private boolean mHomeAppearing;
        private TimeInterpolator mInterpolator;
        private long mStartDelay;

        private NavTransitionListener() {
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            if (view.getId() == R.id.back) {
                this.mBackTransitioning = true;
                return;
            }
            if (view.getId() == R.id.home && transitionType == 2) {
                this.mHomeAppearing = true;
                this.mStartDelay = transition.getStartDelay(transitionType);
                this.mDuration = transition.getDuration(transitionType);
                this.mInterpolator = transition.getInterpolator(transitionType);
            }
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            if (view.getId() == R.id.back) {
                this.mBackTransitioning = false;
            } else if (view.getId() == R.id.home && transitionType == 2) {
                this.mHomeAppearing = false;
            }
        }

        public void onBackAltCleared() {
            ButtonDispatcher backButton = NavigationBarView.this.getBackButton();
            if (!this.mBackTransitioning && backButton.getVisibility() == 0 && this.mHomeAppearing && NavigationBarView.this.getHomeButton().getAlpha() == 0.0f) {
                NavigationBarView.this.getBackButton().setAlpha(0.0f);
                ValueAnimator a = ObjectAnimator.ofFloat(backButton, "alpha", 0.0f, 1.0f);
                a.setStartDelay(this.mStartDelay);
                a.setDuration(this.mDuration);
                a.setInterpolator(this.mInterpolator);
                a.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class H extends Handler {
        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message m) {
            if (m.what != 8686 || NavigationBarView.this.getCurrentView() == null) {
                return;
            }
            String how = "" + m.obj;
            int w = NavigationBarView.this.getWidth();
            int h = NavigationBarView.this.getHeight();
            int vw = NavigationBarView.this.getCurrentView().getWidth();
            int vh = NavigationBarView.this.getCurrentView().getHeight();
            if (h != vh || w != vw) {
                Log.w("StatusBar/NavBarView", String.format("*** Invalid layout in navigation bar (%s this=%dx%d cur=%dx%d)", how, Integer.valueOf(w), Integer.valueOf(h), Integer.valueOf(vw), Integer.valueOf(vh)));
                NavigationBarView.this.requestLayout();
            }
        }
    }

    public NavigationBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCurrentView = null;
        this.mRotatedViews = new View[4];
        this.mCurrentRotation = -1;
        this.mDisabledFlags = 0;
        this.mNavigationIconHints = 0;
        this.mDownHitTarget = 0;
        this.mHomeButtonBounds = new Rect();
        this.mBackButtonBounds = new Rect();
        this.mRecentsButtonBounds = new Rect();
        this.mRotationButtonBounds = new Rect();
        this.mTmpPosition = new int[2];
        this.mTmpRect = new Rect();
        this.mDeadZoneConsuming = false;
        this.mTransitionListener = new NavTransitionListener();
        this.mLayoutTransitionsEnabled = true;
        this.mUseCarModeUi = false;
        this.mInCarMode = false;
        this.mButtonDispatchers = new SparseArray<>();
        this.mRotateBtnStyle = R.style.RotateButtonCCWStart90;
        this.mSystemUICustomizationFactory = null;
        this.mSeekBarVolume = null;
        this.mChangebySelf = false;
        this.mMediaBtnHandler = new Handler();
        this.mHideMediaBtnRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.1
            @Override // java.lang.Runnable
            public void run() {
                if (NavigationBarView.this.naviBottomPre != null) {
                    NavigationBarView.this.naviBottomPre.setVisibility(8);
                }
                if (NavigationBarView.this.naviBottomNext != null) {
                    NavigationBarView.this.naviBottomNext.setVisibility(8);
                }
            }
        };
        this.mRearLock = 0;
        this.mFront_RZC = 0;
        this.AIR_TEMP_REVERSE_FLAG = "air_temp_reverse_flag";
        this.curtAirTempMirror = false;
        this.mAirTempMirrorCfgObserver = new AnonymousClass2(null);
        this.mImeSwitcherClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((InputMethodManager) NavigationBarView.this.mContext.getSystemService(InputMethodManager.class)).showInputMethodPicker(true);
            }
        };
        this.mQuickStepAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.4
            private AccessibilityNodeInfo.AccessibilityAction mToggleOverviewAction;

            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (this.mToggleOverviewAction == null) {
                    this.mToggleOverviewAction = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_toggle_overview, NavigationBarView.this.getContext().getString(R.string.quick_step_accessibility_toggle_overview));
                }
                info.addAction(this.mToggleOverviewAction);
            }

            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == R.id.action_toggle_overview) {
                    ((Recents) SysUiServiceProvider.getComponent(NavigationBarView.this.getContext(), Recents.class)).toggleRecentApps();
                    return true;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        };
        this.mHandler = new H();
        this.mDockedListener = new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarView$rZTKiGVTHMScp1MOeQG9jGW8tDA
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBarView navigationBarView = this.f$0;
                navigationBarView.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarView$4XDnqGNkJ613pDIbZ7uGeE4B1z4
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavigationBarView.lambda$new$0(this.f$0, bool);
                    }
                });
            }
        };
        this.mAutoDismissHandler = new Handler();
        this.mAutoDismissRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.15
            @Override // java.lang.Runnable
            public void run() {
                if (NavigationBarView.this.mVolumeDialog != null && NavigationBarView.this.mVolumeDialog.isShowing()) {
                    NavigationBarView.this.mVolumeDialog.dismiss();
                }
            }
        };
        this.mVolumeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.16
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.media.VOLUME_CHANGED_ACTION".equals(action) && !NavigationBarView.this.mChangebySelf) {
                    int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                    if (streamType == 3 && NavigationBarView.this.mSeekBarVolume != null) {
                        NavigationBarView.this.updateVolumeProgress();
                    }
                }
            }
        };
        this.Auto_Type_big = 0;
        this.Auto_Type_small = 1;
        this.Auto_Type_null = 3;
        this.old_level_max = 7;
        this.cycle_Type_in = 1;
        this.cycle_Type_out = 2;
        this.cycle_Type_auto = 3;
        this.cycle_Type_NULL = 0;
        this.AC_Type_normal = 1;
        this.AC_Type_max = 2;
        this.AC_Type_null = 0;
        this.AC_Type_acauto = 3;
        this.AC_Type_econ = 4;
        this.mCallback = new BluetoothController.Callback() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.18
            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public void onBluetoothStateChange(boolean enabled) {
                NavigationBarView.this.updateBTStatus();
            }

            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public void onBluetoothDevicesChanged() {
            }
        };
        this.myReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.19
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                NavigationBarView.this.naviBottomPre = (ImageButton) NavigationBarView.this.findViewByIdCompat(R.id.navi_bottom_pre);
                NavigationBarView.this.naviBottomNext = (ImageButton) NavigationBarView.this.findViewByIdCompat(R.id.navi_bottom_next);
                String currentSkinName = NavigationBarView.getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
                if ("com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS".equals(action)) {
                    if (NavigationBarView.this.naviBottomPre != null) {
                        NavigationBarView.this.naviBottomPre.setVisibility(0);
                    }
                    if (NavigationBarView.this.naviBottomNext != null) {
                        NavigationBarView.this.naviBottomNext.setVisibility(8);
                    }
                    if ("p38".equals(currentSkinName)) {
                        NavigationBarView.this.mMediaBtnHandler.removeCallbacks(NavigationBarView.this.mHideMediaBtnRunnable);
                        NavigationBarView.this.mMediaBtnHandler.postDelayed(NavigationBarView.this.mHideMediaBtnRunnable, 3000L);
                    }
                }
                if ("com.mediatek.systemui.KEYCODE_MEDIA_NEXT".equals(action)) {
                    if (NavigationBarView.this.naviBottomNext != null) {
                        NavigationBarView.this.naviBottomNext.setVisibility(0);
                    }
                    if (NavigationBarView.this.naviBottomPre != null) {
                        NavigationBarView.this.naviBottomPre.setVisibility(8);
                    }
                    if ("p38".equals(currentSkinName)) {
                        NavigationBarView.this.mMediaBtnHandler.removeCallbacks(NavigationBarView.this.mHideMediaBtnRunnable);
                        NavigationBarView.this.mMediaBtnHandler.postDelayed(NavigationBarView.this.mHideMediaBtnRunnable, 3000L);
                    }
                }
            }
        };
        this.mDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        this.mVertical = false;
        this.mShowMenu = false;
        this.mShowAccessibilityButton = false;
        this.mLongClickableAccessibilityButton = false;
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        this.mRecentsOnboarding = new RecentsOnboarding(context, this.mOverviewProxyService);
        this.mConfiguration = new Configuration();
        this.mConfiguration.updateFrom(context.getResources().getConfiguration());
        try {
            this.mSystemUICustomizationFactory = OpSystemUICustomizationFactoryBase.getOpFactory(context);
            this.mNavBarPlugin = this.mSystemUICustomizationFactory.makeNavigationBar(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reloadNavIcons();
        this.mBarTransitions = new NavigationBarTransitions(this);
        this.mButtonDispatchers.put(R.id.back, new ButtonDispatcher(R.id.back));
        this.mButtonDispatchers.put(R.id.home, new ButtonDispatcher(R.id.home));
        this.mButtonDispatchers.put(R.id.recent_apps, new ButtonDispatcher(R.id.recent_apps));
        this.mButtonDispatchers.put(R.id.menu, new ButtonDispatcher(R.id.menu));
        this.mButtonDispatchers.put(R.id.ime_switcher, new ButtonDispatcher(R.id.ime_switcher));
        this.mButtonDispatchers.put(R.id.accessibility_button, new ButtonDispatcher(R.id.accessibility_button));
        this.mButtonDispatchers.put(R.id.rotate_suggestion, new ButtonDispatcher(R.id.rotate_suggestion));
        this.mButtonDispatchers.put(R.id.menu_container, new ButtonDispatcher(R.id.menu_container));
        this.mResources = SkinCompatResources.getInstance().getSkinResources();
        this.mPackageName = SkinCompatResources.getInstance().getSkinPkgName();
        this.mDeadZone = new DeadZone(this);
        this.mController = (BluetoothController) Dependency.get(BluetoothController.class);
        if (SystemUIApplication.isPortraitWindowMode(getContext()) && isNewLauncher()) {
            initPortSkinResource();
        }
    }

    public BarTransitions getBarTransitions() {
        return this.mBarTransitions;
    }

    public LightBarTransitionsController getLightTransitionsController() {
        return this.mBarTransitions.getLightTransitionsController();
    }

    public void setComponents(RecentsComponent recentsComponent, Divider divider, NotificationPanelView panel) {
        this.mRecentsComponent = recentsComponent;
        this.mDivider = divider;
        this.mPanelView = panel;
        if (this.mGestureHelper instanceof NavigationBarGestureHelper) {
            ((NavigationBarGestureHelper) this.mGestureHelper).setComponents(recentsComponent, divider, this);
        }
    }

    public void setOnVerticalChangedListener(OnVerticalChangedListener onVerticalChangedListener) {
        this.mOnVerticalChangedListener = onVerticalChangedListener;
        notifyVerticalChangedListener(this.mVertical);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (shouldDeadZoneConsumeTouchEvents(event)) {
            return true;
        }
        if (event.getActionMasked() == 0) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            this.mDownHitTarget = 0;
            if (getBackButton().isVisible() && this.mBackButtonBounds.contains(x, y)) {
                this.mDownHitTarget = 1;
            } else if (getHomeButton().isVisible() && this.mHomeButtonBounds.contains(x, y)) {
                this.mDownHitTarget = 2;
            } else if (getRecentsButton().isVisible() && this.mRecentsButtonBounds.contains(x, y)) {
                this.mDownHitTarget = 3;
            } else if (getRotateSuggestionButton().isVisible() && this.mRotationButtonBounds.contains(x, y)) {
                this.mDownHitTarget = 4;
            }
        }
        return this.mGestureHelper.onInterceptTouchEvent(event);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (shouldDeadZoneConsumeTouchEvents(event) || this.mGestureHelper.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:13:0x0021  */
    private boolean shouldDeadZoneConsumeTouchEvents(MotionEvent event) {
        if (!this.mDeadZone.onTouchEvent(event) && !this.mDeadZoneConsuming) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked != 3) {
            switch (actionMasked) {
                case 0:
                    setSlippery(true);
                    this.mDeadZoneConsuming = true;
                    break;
                case 1:
                    updateSlippery();
                    this.mDeadZoneConsuming = false;
                    break;
            }
        } else {
            updateSlippery();
            this.mDeadZoneConsuming = false;
        }
        return true;
    }

    public int getDownHitTarget() {
        return this.mDownHitTarget;
    }

    public void abortCurrentGesture() {
        getHomeButton().abortCurrentGesture();
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public ButtonDispatcher getRecentsButton() {
        return this.mButtonDispatchers.get(R.id.recent_apps);
    }

    public ButtonDispatcher getMenuButton() {
        return this.mButtonDispatchers.get(R.id.menu);
    }

    public ButtonDispatcher getBackButton() {
        return this.mButtonDispatchers.get(R.id.back);
    }

    public ButtonDispatcher getHomeButton() {
        return this.mButtonDispatchers.get(R.id.home);
    }

    public ButtonDispatcher getImeSwitchButton() {
        return this.mButtonDispatchers.get(R.id.ime_switcher);
    }

    public ButtonDispatcher getAccessibilityButton() {
        return this.mButtonDispatchers.get(R.id.accessibility_button);
    }

    public ButtonDispatcher getRotateSuggestionButton() {
        return this.mButtonDispatchers.get(R.id.rotate_suggestion);
    }

    public ButtonDispatcher getMenuContainer() {
        return this.mButtonDispatchers.get(R.id.menu_container);
    }

    public SparseArray<ButtonDispatcher> getButtonDispatchers() {
        return this.mButtonDispatchers;
    }

    public boolean isRecentsButtonVisible() {
        return getRecentsButton().getVisibility() == 0;
    }

    public boolean isOverviewEnabled() {
        return (this.mDisabledFlags & 16777216) == 0;
    }

    public boolean isQuickStepSwipeUpEnabled() {
        return this.mOverviewProxyService.shouldShowSwipeUpUI() && isOverviewEnabled();
    }

    public boolean isQuickScrubEnabled() {
        return SystemProperties.getBoolean("persist.quickstep.scrub.enabled", true) && this.mOverviewProxyService.isEnabled() && isOverviewEnabled() && (this.mOverviewProxyService.getInteractionFlags() & 2) == 0;
    }

    private void reloadNavIcons() {
        updateIcons(this.mContext, Configuration.EMPTY, this.mConfiguration);
    }

    private void updateIcons(Context ctx, Configuration oldConfig, Configuration newConfig) {
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            Log.d("StatusBar/NavBarView", "updateIcons, is portrait state, return!");
            return;
        }
        int dualToneDarkTheme = Utils.getThemeAttr(ctx, R.attr.darkIconTheme);
        int dualToneLightTheme = Utils.getThemeAttr(ctx, R.attr.lightIconTheme);
        Context lightContext = new ContextThemeWrapper(ctx, dualToneLightTheme);
        Context darkContext = new ContextThemeWrapper(ctx, dualToneDarkTheme);
        if (oldConfig.orientation != newConfig.orientation || oldConfig.densityDpi != newConfig.densityDpi) {
            this.mDockedIcon = getDrawable(lightContext, darkContext, R.drawable.ic_sysbar_docked);
            this.mHomeDefaultIcon = getHomeDrawable(lightContext, darkContext);
        }
        if (oldConfig.densityDpi != newConfig.densityDpi || oldConfig.getLayoutDirection() != newConfig.getLayoutDirection()) {
            this.mBackIcon = getBackDrawable(lightContext, darkContext);
            this.mRecentIcon = getDrawable(lightContext, darkContext, R.drawable.ic_sysbar_recent);
            this.mMenuIcon = getDrawable(lightContext, darkContext, R.drawable.ic_sysbar_menu);
            this.mAccessibilityIcon = getDrawable(lightContext, darkContext, R.drawable.ic_sysbar_accessibility_button, false);
            this.mImeIcon = getDrawable(lightContext, darkContext, R.drawable.ic_ime_switcher_default, false);
            updateRotateSuggestionButtonStyle(this.mRotateBtnStyle, false);
        }
    }

    public KeyButtonDrawable getBackDrawable(Context lightContext, Context darkContext) {
        KeyButtonDrawable drawable = chooseNavigationIconDrawable(lightContext, darkContext, R.drawable.ic_sysbar_back, R.drawable.ic_sysbar_back_quick_step);
        orientBackButton(drawable);
        return drawable;
    }

    public KeyButtonDrawable getHomeDrawable(Context lightContext, Context darkContext) {
        KeyButtonDrawable drawable;
        boolean quickStepEnabled = this.mOverviewProxyService.shouldShowSwipeUpUI();
        if (quickStepEnabled) {
            drawable = getDrawable(lightContext, darkContext, R.drawable.ic_sysbar_home_quick_step);
        } else {
            drawable = getDrawable(lightContext, darkContext, R.drawable.ic_sysbar_home, false);
        }
        orientHomeButton(drawable);
        return drawable;
    }

    private void orientBackButton(KeyButtonDrawable drawable) {
        float f;
        boolean useAltBack = (this.mNavigationIconHints & 1) != 0;
        if (useAltBack) {
            f = -90.0f;
        } else {
            f = getLayoutDirection() == 1 ? 180.0f : 0.0f;
        }
        drawable.setRotation(f);
    }

    private void orientHomeButton(KeyButtonDrawable drawable) {
        drawable.setRotation(this.mVertical ? 90.0f : 0.0f);
    }

    private KeyButtonDrawable chooseNavigationIconDrawable(Context lightContext, Context darkContext, int icon, int quickStepIcon) {
        boolean quickStepEnabled = this.mOverviewProxyService.shouldShowSwipeUpUI();
        if (quickStepEnabled) {
            return getDrawable(lightContext, darkContext, quickStepIcon);
        }
        return getDrawable(lightContext, darkContext, icon);
    }

    private KeyButtonDrawable getDrawable(Context lightContext, Context darkContext, int icon) {
        return getDrawable(lightContext, darkContext, icon, true);
    }

    private KeyButtonDrawable getDrawable(Context lightContext, Context darkContext, int icon, boolean hasShadow) {
        return KeyButtonDrawable.create(lightContext, lightContext.getDrawable(icon), darkContext.getDrawable(icon), hasShadow);
    }

    private TintedKeyButtonDrawable getDrawable(Context ctx, int icon, int lightColor, int darkColor) {
        return TintedKeyButtonDrawable.create(ctx.getDrawable(icon), lightColor, darkColor);
    }

    @Override // android.view.View
    public void setLayoutDirection(int layoutDirection) {
        reloadNavIcons();
        super.setLayoutDirection(layoutDirection);
    }

    private KeyButtonDrawable getBackIconWithAlt(boolean carMode, boolean landscape) {
        if (landscape) {
            return carMode ? this.mBackAltLandCarModeIcon : this.mBackIcon;
        }
        return carMode ? this.mBackAltCarModeIcon : this.mBackIcon;
    }

    private KeyButtonDrawable getBackIcon(boolean carMode, boolean landscape) {
        if (landscape) {
            return carMode ? this.mBackLandCarModeIcon : this.mBackIcon;
        }
        return carMode ? this.mBackCarModeIcon : this.mBackIcon;
    }

    public void setNavigationIconHints(int hints) {
        if (hints == this.mNavigationIconHints) {
            return;
        }
        boolean backAlt = (hints & 1) != 0;
        if ((1 & this.mNavigationIconHints) != 0 && !backAlt) {
            this.mTransitionListener.onBackAltCleared();
        }
        this.mNavigationIconHints = hints;
        updateNavButtonIcons();
    }

    public void setDisabledFlags(int disabledFlags) {
        if (this.mDisabledFlags == disabledFlags) {
            return;
        }
        boolean overviewEnabledBefore = isOverviewEnabled();
        this.mDisabledFlags = disabledFlags;
        if (!overviewEnabledBefore && isOverviewEnabled()) {
            reloadNavIcons();
        }
        updateNavButtonIcons();
        updateSlippery();
        setUpSwipeUpOnboarding(isQuickStepSwipeUpEnabled());
    }

    public void updateNavButtonIcons() {
        KeyButtonDrawable backIcon;
        ViewGroup navButtons;
        LayoutTransition lt;
        boolean useAltBack = (this.mNavigationIconHints & 1) != 0;
        if (useAltBack) {
            backIcon = getBackIconWithAlt(this.mUseCarModeUi, this.mVertical);
        } else {
            backIcon = getBackIcon(this.mUseCarModeUi, this.mVertical);
        }
        KeyButtonDrawable homeIcon = this.mUseCarModeUi ? this.mHomeCarModeIcon : this.mHomeDefaultIcon;
        if (!SystemUIApplication.isPortraitWindowMode(getContext())) {
            if (!this.mUseCarModeUi) {
                orientBackButton(backIcon);
                orientHomeButton(homeIcon);
            }
            getHomeButton().setImageDrawable(homeIcon);
            getBackButton().setImageDrawable(backIcon);
            updateRecentsIcon();
        }
        boolean showImeButton = (this.mShowAccessibilityButton || this.mShowRotateButton || (this.mNavigationIconHints & 2) == 0) ? false : true;
        getImeSwitchButton().setVisibility(showImeButton ? 0 : 4);
        getImeSwitchButton().setImageDrawable(this.mImeIcon);
        setMenuVisibility(this.mShowMenu, true);
        getMenuButton().setImageDrawable(this.mMenuIcon);
        getRotateSuggestionButton().setImageDrawable(this.mRotateSuggestionIcon);
        setAccessibilityButtonState(this.mShowAccessibilityButton, this.mLongClickableAccessibilityButton);
        getAccessibilityButton().setImageDrawable(this.mAccessibilityIcon);
        this.mBarTransitions.reapplyDarkIntensity();
        boolean disableHome = (this.mDisabledFlags & 2097152) != 0;
        boolean disableRecent = this.mUseCarModeUi || !isOverviewEnabled();
        boolean disableBack = ((this.mDisabledFlags & 4194304) == 0 || useAltBack) ? false : true;
        boolean pinningActive = ActivityManagerWrapper.getInstance().isScreenPinningActive();
        if (this.mOverviewProxyService.isEnabled()) {
            int flags = this.mOverviewProxyService.getInteractionFlags();
            disableRecent |= (flags & 4) == 0;
            if (pinningActive) {
                disableBack = false;
                disableHome = false;
            }
        } else if (pinningActive) {
            disableRecent = false;
            disableBack = false;
        }
        if (getCurrentView() != null && (navButtons = (ViewGroup) getCurrentView().findViewById(R.id.nav_buttons)) != null && (lt = navButtons.getLayoutTransition()) != null && !lt.getTransitionListeners().contains(this.mTransitionListener)) {
            lt.addTransitionListener(this.mTransitionListener);
        }
        getBackButton().setVisibility(disableBack ? 4 : 0);
        getHomeButton().setVisibility(disableHome ? 4 : 0);
        getRecentsButton().setVisibility(disableRecent ? 4 : 0);
    }

    public boolean inScreenPinning() {
        return ActivityManagerWrapper.getInstance().isScreenPinningActive();
    }

    public void setLayoutTransitionsEnabled(boolean enabled) {
        this.mLayoutTransitionsEnabled = enabled;
        updateLayoutTransitionsEnabled();
    }

    public void setWakeAndUnlocking(boolean wakeAndUnlocking) {
        setUseFadingAnimations(wakeAndUnlocking);
        this.mWakeAndUnlocking = wakeAndUnlocking;
        updateLayoutTransitionsEnabled();
    }

    private void updateLayoutTransitionsEnabled() {
        LayoutTransition lt;
        if (getCurrentView() == null) {
            return;
        }
        boolean enabled = !this.mWakeAndUnlocking && this.mLayoutTransitionsEnabled;
        ViewGroup navButtons = (ViewGroup) getCurrentView().findViewById(R.id.nav_buttons);
        if (navButtons == null || (lt = navButtons.getLayoutTransition()) == null) {
            return;
        }
        if (enabled) {
            lt.enableTransitionType(2);
            lt.enableTransitionType(3);
            lt.enableTransitionType(0);
            lt.enableTransitionType(1);
            return;
        }
        lt.disableTransitionType(2);
        lt.disableTransitionType(3);
        lt.disableTransitionType(0);
        lt.disableTransitionType(1);
    }

    private void setUseFadingAnimations(boolean useFadingAnimations) {
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) ((ViewGroup) getParent()).getLayoutParams();
        if (lp != null) {
            boolean old = lp.windowAnimations != 0;
            if (!old && useFadingAnimations) {
                lp.windowAnimations = 2131886088;
            } else if (old && !useFadingAnimations) {
                lp.windowAnimations = 0;
            } else {
                return;
            }
            WindowManager wm = (WindowManager) getContext().getSystemService("window");
            wm.updateViewLayout((View) getParent(), lp);
        }
    }

    public void onNavigationButtonLongPress(View v) {
        this.mGestureHelper.onNavigationButtonLongPress(v);
    }

    public void onPanelExpandedChange(boolean expanded) {
        updateSlippery();
    }

    public void updateStates() {
        boolean showSwipeUpUI = this.mOverviewProxyService.shouldShowSwipeUpUI();
        if (this.mNavigationInflaterView != null) {
            this.mNavigationInflaterView.onLikelyDefaultLayoutChange();
        }
        updateSlippery();
        reloadNavIcons();
        updateNavButtonIcons();
        setUpSwipeUpOnboarding(isQuickStepSwipeUpEnabled());
        WindowManagerWrapper.getInstance().setNavBarVirtualKeyHapticFeedbackEnabled(!showSwipeUpUI);
        getHomeButton().setAccessibilityDelegate(showSwipeUpUI ? this.mQuickStepAccessibilityDelegate : null);
    }

    private void updateSlippery() {
        setSlippery(!isQuickStepSwipeUpEnabled() || this.mPanelView.isFullyExpanded());
    }

    private void setSlippery(boolean slippery) {
        boolean changed = false;
        ViewGroup navbarView = (ViewGroup) getParent();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) navbarView.getLayoutParams();
        if (lp == null) {
            return;
        }
        if (slippery && (lp.flags & 536870912) == 0) {
            lp.flags = 536870912 | lp.flags;
            changed = true;
        } else if (!slippery && (536870912 & lp.flags) != 0) {
            lp.flags &= -536870913;
            changed = true;
        }
        if (changed) {
            WindowManager wm = (WindowManager) getContext().getSystemService("window");
            wm.updateViewLayout(navbarView, lp);
        }
    }

    public void setMenuVisibility(boolean show) {
        setMenuVisibility(show, false);
    }

    public void setMenuVisibility(boolean show, boolean force) {
        if (force || this.mShowMenu != show) {
            this.mShowMenu = show;
            boolean shouldShow = this.mShowMenu && !this.mShowAccessibilityButton && !this.mShowRotateButton && (this.mNavigationIconHints & 2) == 0;
            getMenuButton().setVisibility(shouldShow ? 0 : 4);
            getMenuContainer().setVisibility(shouldShow ? 0 : 4);
        }
    }

    public void setAccessibilityButtonState(boolean visible, boolean longClickable) {
        this.mShowAccessibilityButton = visible;
        this.mLongClickableAccessibilityButton = longClickable;
        if (visible) {
            setMenuVisibility(false, true);
            getImeSwitchButton().setVisibility(4);
            setRotateButtonVisibility(false);
        }
        getAccessibilityButton().setVisibility(visible ? 0 : 4);
        getAccessibilityButton().setLongClickable(longClickable);
    }

    public void updateRotateSuggestionButtonStyle(int style, boolean setIcon) {
        this.mRotateBtnStyle = style;
        Context ctx = getContext();
        int dualToneDarkTheme = Utils.getThemeAttr(ctx, R.attr.darkIconTheme);
        int dualToneLightTheme = Utils.getThemeAttr(ctx, R.attr.lightIconTheme);
        Context darkContext = new ContextThemeWrapper(ctx, dualToneDarkTheme);
        Context lightContext = new ContextThemeWrapper(ctx, dualToneLightTheme);
        int lightColor = Utils.getColorAttr(lightContext, R.attr.singleToneColor);
        int darkColor = Utils.getColorAttr(darkContext, R.attr.singleToneColor);
        Context rotateContext = new ContextThemeWrapper(ctx, style);
        TintedKeyButtonDrawable priorIcon = this.mRotateSuggestionIcon;
        this.mRotateSuggestionIcon = getDrawable(rotateContext, R.drawable.ic_sysbar_rotate_button, lightColor, darkColor);
        if (priorIcon != null && priorIcon.isDarkIntensitySet()) {
            this.mRotateSuggestionIcon.setDarkIntensity(priorIcon.getDarkIntensity());
        }
        if (setIcon) {
            getRotateSuggestionButton().setImageDrawable(this.mRotateSuggestionIcon);
        }
    }

    public int setRotateButtonVisibility(boolean visible) {
        boolean adjVisible = visible && !this.mShowAccessibilityButton;
        int vis = adjVisible ? 0 : 4;
        if (vis == getRotateSuggestionButton().getVisibility()) {
            return vis;
        }
        getRotateSuggestionButton().setVisibility(vis);
        this.mShowRotateButton = visible;
        if (!visible) {
            Drawable d = this.mRotateSuggestionIcon.getDrawable(0);
            if (d instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
                avd.clearAnimationCallbacks();
                avd.reset();
            }
        }
        updateNavButtonIcons();
        return vis;
    }

    public boolean isRotateButtonVisible() {
        return this.mShowRotateButton;
    }

    ButtonDispatcher getButtonAtPosition(int x, int y) {
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            ButtonDispatcher button = this.mButtonDispatchers.valueAt(i);
            View buttonView = button.getCurrentView();
            if (buttonView != null) {
                buttonView.getHitRect(this.mTmpRect);
                offsetDescendantRectToMyCoords(buttonView, this.mTmpRect);
                if (this.mTmpRect.contains(x, y)) {
                    return button;
                }
            }
        }
        return null;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        Log.d("StatusBar/NavBarView", "onFinishInflate...");
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            Log.d("StatusBar/NavBarView", "onFinishInflate, is portrait state, return!");
            this.mCurrentView = this;
            initPortView();
        }
    }

    public void onDarkIntensityChange(float intensity) {
        if (this.mGestureHelper != null) {
            this.mGestureHelper.onDarkIntensityChange(intensity);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mGestureHelper.onDraw(canvas);
        this.mDeadZone.onDraw(canvas);
        super.onDraw(canvas);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updateButtonLocationOnScreen(getBackButton(), this.mBackButtonBounds);
        updateButtonLocationOnScreen(getHomeButton(), this.mHomeButtonBounds);
        updateButtonLocationOnScreen(getRecentsButton(), this.mRecentsButtonBounds);
        updateButtonLocationOnScreen(getRotateSuggestionButton(), this.mRotationButtonBounds);
        this.mGestureHelper.onLayout(changed, left, top, right, bottom);
        this.mRecentsOnboarding.setNavBarHeight(getMeasuredHeight());
    }

    private void updateButtonLocationOnScreen(ButtonDispatcher button, Rect buttonBounds) {
        View view = button.getCurrentView();
        if (view == null) {
            buttonBounds.setEmpty();
            return;
        }
        float posX = view.getTranslationX();
        float posY = view.getTranslationY();
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        view.getLocationInWindow(this.mTmpPosition);
        buttonBounds.set(this.mTmpPosition[0], this.mTmpPosition[1], this.mTmpPosition[0] + view.getMeasuredWidth(), this.mTmpPosition[1] + view.getMeasuredHeight());
        view.setTranslationX(posX);
        view.setTranslationY(posY);
    }

    public boolean needsReorient(int rotation) {
        return this.mCurrentRotation != rotation;
    }

    private void updateCurrentView() {
        if (Objects.isNull(this.mNavigationInflaterView)) {
            return;
        }
        int rot = this.mDisplay.getRotation();
        for (int i = 0; i < 4; i++) {
            this.mRotatedViews[i].setVisibility(8);
        }
        this.mCurrentView = this.mRotatedViews[rot];
        this.mCurrentView.setVisibility(0);
        this.mNavigationInflaterView.setAlternativeOrder(rot == 1);
        this.mNavigationInflaterView.updateButtonDispatchersCurrentView();
        updateLayoutTransitionsEnabled();
        this.mCurrentRotation = rot;
    }

    private void updateRecentsIcon() {
        if (this.mDockedIcon != null) {
            this.mDockedIcon.setRotation((this.mDockedStackExists && this.mVertical) ? 90.0f : 0.0f);
            getRecentsButton().setImageDrawable(this.mDockedStackExists ? this.mDockedIcon : this.mRecentIcon);
        }
        this.mBarTransitions.reapplyDarkIntensity();
    }

    public void reorient() {
        Log.d("StatusBar/NavBarView", "reorient...");
        if (Objects.isNull(this.mNavigationInflaterView)) {
            return;
        }
        if (SystemUIApplication.isPortraitWindowMode(getContext())) {
            Log.d("StatusBar/NavBarView", "reorient, is portrait state, return!");
            return;
        }
        updateCurrentView();
        ((NavigationBarFrame) getRootView()).setDeadZone(this.mDeadZone);
        this.mDeadZone.onConfigurationChanged(this.mCurrentRotation);
        this.mBarTransitions.init();
        setMenuVisibility(this.mShowMenu, true);
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
        }
        updateTaskSwitchHelper();
        updateNavButtonIcons();
        getHomeButton().setVertical(this.mVertical);
    }

    private void updateTaskSwitchHelper() {
        if (this.mGestureHelper == null) {
            return;
        }
        boolean isRtl = getLayoutDirection() == 1;
        this.mGestureHelper.setBarState(this.mVertical, isRtl);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        boolean newVertical = w > 0 && h > w;
        if (newVertical != this.mVertical) {
            this.mVertical = newVertical;
            reorient();
            notifyVerticalChangedListener(newVertical);
        }
        postCheckForInvalidLayout("sizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void notifyVerticalChangedListener(boolean newVertical) {
        if (this.mOnVerticalChangedListener != null) {
            this.mOnVerticalChangedListener.onVerticalChanged(newVertical);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean uiCarModeChanged = updateCarMode(newConfig);
        updateTaskSwitchHelper();
        updateIcons(getContext(), this.mConfiguration, newConfig);
        updateRecentsIcon();
        this.mRecentsOnboarding.onConfigurationChanged(newConfig);
        if (uiCarModeChanged || this.mConfiguration.densityDpi != newConfig.densityDpi || this.mConfiguration.getLayoutDirection() != newConfig.getLayoutDirection()) {
            updateNavButtonIcons();
        }
        this.mConfiguration.updateFrom(newConfig);
    }

    private boolean updateCarMode(Configuration newConfig) {
        if (newConfig != null) {
            int uiMode = newConfig.uiMode & 15;
            boolean isCarMode = uiMode == 3;
            if (isCarMode != this.mInCarMode) {
                this.mInCarMode = isCarMode;
                this.mUseCarModeUi = false;
            }
        }
        return false;
    }

    private String getResourceName(int resId) {
        if (resId != 0) {
            Resources res = getContext().getResources();
            try {
                return res.getResourceName(resId);
            } catch (Resources.NotFoundException e) {
                return "(unknown)";
            }
        }
        return "(null)";
    }

    private void postCheckForInvalidLayout(String how) {
        this.mHandler.obtainMessage(8686, 0, 0, how).sendToTarget();
    }

    private static String visibilityToString(int vis) {
        if (vis == 4) {
            return "INVISIBLE";
        }
        if (vis == 8) {
            return "GONE";
        }
        return "VISIBLE";
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestApplyInsets();
        reorient();
        onPluginDisconnected((NavGesture) null);
        ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener((PluginListener) this, NavGesture.class, false);
        setUpSwipeUpOnboarding(isQuickStepSwipeUpEnabled());
        initAirTempMirrorCfg();
        registerMediaButtonReceiver();
        registerVolumeReceiver();
    }

    private void registerMediaButtonReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS");
        filter.addAction("com.mediatek.systemui.KEYCODE_MEDIA_NEXT");
        getContext().registerReceiver(this.myReceiver, filter);
        Log.d("StatusBar/NavBarView", "Media button receiver registered");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
        if (this.mGestureHelper != null) {
            this.mGestureHelper.destroy();
        }
        setUpSwipeUpOnboarding(false);
        if (this.mContext != null && this.mInitView != null) {
            this.mInitView.unregisterTimeReceiver();
        }
        regOrUnregAirListen(false);
        getContext().unregisterReceiver(this.myReceiver);
        this.mMediaBtnHandler.removeCallbacks(this.mHideMediaBtnRunnable);
        unregisterVolumeReceiver();
        cancelAutoDismissTimer();
        if (this.mVolumeDialog != null && this.mVolumeDialog.isShowing()) {
            this.mVolumeDialog.dismiss();
        }
    }

    private void initAirTempMirrorCfg() {
        if (!SystemUIApplication.isPortraitWindowMode(getContext())) {
            return;
        }
        regOrUnregAirListen(true);
        checkOrSwapAirTemp();
    }

    private void regOrUnregAirListen(boolean isReg) {
        if (isReg) {
            getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("air_temp_reverse_flag"), true, this.mAirTempMirrorCfgObserver, -1);
        } else {
            getContext().getContentResolver().unregisterContentObserver(this.mAirTempMirrorCfgObserver);
        }
    }

    private void setUpSwipeUpOnboarding(boolean connectedToOverviewProxy) {
        if (connectedToOverviewProxy) {
            this.mRecentsOnboarding.onConnectedToLauncher();
        } else {
            this.mRecentsOnboarding.onDisconnectedFromLauncher();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(NavGesture plugin, Context context) {
        this.mGestureHelper = plugin.getGestureHelper();
        updateTaskSwitchHelper();
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(NavGesture plugin) {
        NavigationBarGestureHelper defaultHelper = new NavigationBarGestureHelper(getContext());
        defaultHelper.setComponents(this.mRecentsComponent, this.mDivider, this);
        if (this.mGestureHelper != null) {
            this.mGestureHelper.destroy();
        }
        this.mGestureHelper = defaultHelper;
        updateTaskSwitchHelper();
    }

    @SuppressLint({"DefaultLocale"})
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("NavigationBarView {");
        Rect r = new Rect();
        Point size = new Point();
        this.mDisplay.getRealSize(size);
        pw.println(String.format("      this: " + StatusBar.viewInfo(this) + " " + visibilityToString(getVisibility()), new Object[0]));
        getWindowVisibleDisplayFrame(r);
        boolean offscreen = r.right > size.x || r.bottom > size.y;
        StringBuilder sb = new StringBuilder();
        sb.append("      window: ");
        sb.append(r.toShortString());
        sb.append(" ");
        sb.append(visibilityToString(getWindowVisibility()));
        sb.append(offscreen ? " OFFSCREEN!" : "");
        pw.println(sb.toString());
        if (getCurrentView() != null) {
            pw.println(String.format("      mCurrentView: id=%s (%dx%d) %s %f", getResourceName(getCurrentView().getId()), Integer.valueOf(getCurrentView().getWidth()), Integer.valueOf(getCurrentView().getHeight()), visibilityToString(getCurrentView().getVisibility()), Float.valueOf(getCurrentView().getAlpha())));
        } else {
            pw.println("      mCurrentView: null");
        }
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(this.mDisabledFlags);
        objArr[1] = this.mVertical ? "true" : "false";
        objArr[2] = this.mShowMenu ? "true" : "false";
        pw.println(String.format("      disabled=0x%08x vertical=%s menu=%s", objArr));
        dumpButton(pw, "back", getBackButton());
        dumpButton(pw, "home", getHomeButton());
        dumpButton(pw, "rcnt", getRecentsButton());
        dumpButton(pw, "menu", getMenuButton());
        dumpButton(pw, "a11y", getAccessibilityButton());
        this.mRecentsOnboarding.dump(pw);
        pw.println("    }");
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
        return super.onApplyWindowInsets(insets);
    }

    private static void dumpButton(PrintWriter pw, String caption, ButtonDispatcher button) {
        pw.print("      " + caption + ": ");
        if (button == null) {
            pw.print("null");
        } else {
            pw.print(visibilityToString(button.getVisibility()) + " alpha=" + button.getAlpha());
        }
        pw.println();
    }

    public static /* synthetic */ void lambda$new$0(NavigationBarView navigationBarView, Boolean exists) {
        navigationBarView.mDockedStackExists = exists.booleanValue();
        navigationBarView.updateRecentsIcon();
    }

    public void initHcnPort() {
        Log.v("StatusBar/NavBarView", "initHcnPort");
        initPortView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View findViewByIdCompat(int id) {
        return findViewById(SkinCompatResources.getInstance().getId(id));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void initPortView() {
        this.skinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
        ImageButton btnHome = (ImageButton) findViewByIdCompat(R.id.menu_home);
        if (btnHome != null) {
            btnHome.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(3, 50);
                    if ("p45".equals(NavigationBarView.this.skinName)) {
                        Intent intent = new Intent("com.hcn.auto.launcher.HOME_BUTTON_ACTION");
                        intent.putExtra("command", "OPEN_HOME_PANEL");
                        Log.d("StatusBar/NavBarView", "Sending broadcast: " + intent.getAction());
                        Log.d("StatusBar/NavBarView", "Command: " + intent.getStringExtra("command"));
                        Log.d("StatusBar/NavBarView", "Package: " + intent.getPackage());
                        NavigationBarView.this.mContext.sendBroadcast(intent);
                        Log.d("StatusBar/NavBarView", "Broadcast sent successfully");
                    }
                }
            });
        }
        ImageButton btnApps = (ImageButton) findViewByIdCompat(R.id.btn_apps);
        if (btnApps != null) {
            btnApps.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(335, 50);
                }
            });
        }
        ImageButton btnBack = (ImageButton) findViewByIdCompat(R.id.menu_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(4, 50);
                }
            });
        }
        VolButton btnVolAdd = (VolButton) findViewByIdCompat(R.id.add_vol);
        if (btnVolAdd != null) {
            btnVolAdd.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    Log.d("StatusBar/NavBarView", "onClick add volume");
                    if ("p45".equals(NavigationBarView.this.skinName)) {
                        NavigationBarView.this.showVolumeDialog();
                        int currentVolume = NavigationBarView.this.mAudioManager.getStreamVolume(3);
                        int maxVolume = NavigationBarView.this.mAudioManager.getStreamMaxVolume(3);
                        if (currentVolume < maxVolume) {
                            NavigationBarView.this.mAudioManager.setStreamVolume(3, currentVolume + 1, 0);
                            NavigationBarView.this.updateVolumeProgress();
                            return;
                        }
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(24, 50);
                }
            });
            btnVolAdd.setLongClickRepeatListener(new VolButton.LongClickRepeatListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.9
                @Override // com.android.systemui.statusbar.phone.VolButton.LongClickRepeatListener
                public void repeatAction() {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    Log.d("StatusBar/NavBarView", "repeatAction add volume");
                    if ("p45".equals(NavigationBarView.this.skinName)) {
                        NavigationBarView.this.showVolumeDialog();
                        int currentVolume = NavigationBarView.this.mAudioManager.getStreamVolume(3);
                        int maxVolume = NavigationBarView.this.mAudioManager.getStreamMaxVolume(3);
                        if (currentVolume < maxVolume) {
                            NavigationBarView.this.mAudioManager.setStreamVolume(3, currentVolume + 1, 0);
                            NavigationBarView.this.updateVolumeProgress();
                            return;
                        }
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(24, 50);
                }
            });
        }
        VolButton btnVolReduce = (VolButton) findViewByIdCompat(R.id.reduce_vol);
        if (btnVolReduce != null) {
            btnVolReduce.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    Log.d("StatusBar/NavBarView", "onClick reduce volume");
                    if ("p45".equals(NavigationBarView.this.skinName)) {
                        NavigationBarView.this.showVolumeDialog();
                        int currentVolume = NavigationBarView.this.mAudioManager.getStreamVolume(3);
                        if (currentVolume > 0) {
                            NavigationBarView.this.mAudioManager.setStreamVolume(3, currentVolume - 1, 0);
                            NavigationBarView.this.updateVolumeProgress();
                            return;
                        }
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(25, 50);
                }
            });
            btnVolReduce.setLongClickRepeatListener(new VolButton.LongClickRepeatListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.11
                @Override // com.android.systemui.statusbar.phone.VolButton.LongClickRepeatListener
                public void repeatAction() {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    Log.d("StatusBar/NavBarView", "repeatAction reduce volume");
                    if ("p45".equals(NavigationBarView.this.skinName)) {
                        NavigationBarView.this.showVolumeDialog();
                        int currentVolume = NavigationBarView.this.mAudioManager.getStreamVolume(3);
                        if (currentVolume > 0) {
                            NavigationBarView.this.mAudioManager.setStreamVolume(3, currentVolume - 1, 0);
                            NavigationBarView.this.updateVolumeProgress();
                            return;
                        }
                        return;
                    }
                    McuManager.getsInstance().injectKeyEventTimeout(25, 50);
                }
            });
        }
        this.mBTConnectStatus = (Button) findViewByIdCompat(R.id.bt_image);
        if (this.mBTConnectStatus != null) {
            this.mBTConnectStatus.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.12
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    NavigationBarView.this.RunApp("com.autochips.bluetooth", "com.autochips.bluetooth.MainBluetoothActivity");
                }
            });
            updateBTStatus();
        }
        Button btn_left_temp_add = (Button) findViewByIdCompat(R.id.btn_left_temp_add);
        if (btn_left_temp_add != null) {
            btn_left_temp_add.setOnTouchListener(this);
        }
        Button btn_left_temp_dec = (Button) findViewByIdCompat(R.id.btn_left_temp_dec);
        if (btn_left_temp_dec != null) {
            btn_left_temp_dec.setOnTouchListener(this);
        }
        Button btn_right_temp_add = (Button) findViewByIdCompat(R.id.btn_right_temp_add);
        if (btn_right_temp_add != null) {
            btn_right_temp_add.setOnTouchListener(this);
        }
        Button btn_right_temp_dec = (Button) findViewByIdCompat(R.id.btn_right_temp_dec);
        if (btn_right_temp_dec != null) {
            btn_right_temp_dec.setOnTouchListener(this);
        }
        Button btn_fan_add = (Button) findViewByIdCompat(R.id.btn_fan_add);
        if (btn_fan_add != null) {
            btn_fan_add.setOnTouchListener(this);
        }
        Button btn_fan_dec = (Button) findViewByIdCompat(R.id.btn_fan_dec);
        if (btn_fan_dec != null) {
            btn_fan_dec.setOnTouchListener(this);
        }
        Button btn_on_off = (Button) findViewByIdCompat(R.id.btn_on_off);
        if (btn_on_off != null) {
            btn_on_off.setOnTouchListener(this);
        }
        Button btn_ac = (Button) findViewByIdCompat(R.id.btn_ac);
        if (btn_ac != null) {
            btn_ac.setOnTouchListener(this);
        }
        this.mAirLayout = (LinearLayout) findViewByIdCompat(R.id.air_layout);
        this.layout_air_func = (LinearLayout) findViewByIdCompat(R.id.layout_air_func);
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        this.jellyProgressView = (JellyProgressView) findViewByIdCompat(R.id.navi_progress_volume);
        this.naviBottomPre = (ImageButton) findViewByIdCompat(R.id.navi_bottom_pre);
        this.naviBottomNext = (ImageButton) findViewByIdCompat(R.id.navi_bottom_next);
        if (this.naviBottomPre != null) {
            this.naviBottomPre.setVisibility(8);
        }
        if (this.naviBottomNext != null) {
            this.naviBottomNext.setVisibility(8);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS");
        filter.addAction("com.mediatek.systemui.KEYCODE_MEDIA_NEXT");
        getContext().registerReceiver(this.myReceiver, filter);
        if (isNewLauncher()) {
            if (this.skinName.equals("p38") || this.skinName.equals("p42")) {
                this.mTvSoundVolume = (TextView) findViewByIdCompat(R.id.tv_sound_volume);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showVolumeDialog() {
        if (this.mVolumeDialog != null && this.mVolumeDialog.isShowing()) {
            updateVolumeProgress();
            resetAutoDismissTimer();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View customView = inflater.inflate(R.layout.p45_layout_seekbar_volume, (ViewGroup) null);
        this.mSeekBarVolume = (SeekBar) customView.findViewById(R.id.seekbar);
        builder.setView(customView);
        updateVolumeProgress();
        this.mVolumeDialog = builder.create();
        this.mVolumeDialog.getWindow().setType(2038);
        this.mSeekBarVolume.setMax(this.mAudioManager.getStreamMaxVolume(3));
        this.mSeekBarVolume.setProgress(this.mAudioManager.getStreamVolume(3));
        this.mSeekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.13
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    NavigationBarView.this.setStreamVolumeValue(3, progress);
                    NavigationBarView.this.resetAutoDismissTimer();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                NavigationBarView.this.mChangebySelf = true;
                NavigationBarView.this.cancelAutoDismissTimer();
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                NavigationBarView.this.mChangebySelf = false;
                NavigationBarView.this.startAutoDismissTimer();
            }
        });
        this.mVolumeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.14
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                NavigationBarView.this.cancelAutoDismissTimer();
                NavigationBarView.this.mVolumeDialog = null;
                NavigationBarView.this.mSeekBarVolume = null;
            }
        });
        this.mVolumeDialog.show();
        startAutoDismissTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutoDismissTimer() {
        cancelAutoDismissTimer();
        this.mAutoDismissHandler.postDelayed(this.mAutoDismissRunnable, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAutoDismissTimer() {
        startAutoDismissTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAutoDismissTimer() {
        this.mAutoDismissHandler.removeCallbacks(this.mAutoDismissRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolumeProgress() {
        if (this.mSeekBarVolume != null) {
            int currentVolume = this.mAudioManager.getStreamVolume(3);
            this.mSeekBarVolume.setProgress(currentVolume);
        }
    }

    public void setStreamVolumeValue(int streamType, int nValue) {
        this.mAudioManager.setStreamVolume(streamType, nValue, 0);
    }

    private void registerVolumeReceiver() {
        try {
            IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
            getContext().registerReceiver(this.mVolumeReceiver, filter);
        } catch (Exception e) {
            Log.e("StatusBar/NavBarView", "registerVolumeReceiver error: " + e.getMessage());
        }
    }

    private void unregisterVolumeReceiver() {
        try {
            getContext().unregisterReceiver(this.mVolumeReceiver);
        } catch (Exception e) {
            Log.e("StatusBar/NavBarView", "unregisterVolumeReceiver error: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isBackCar() {
        if (new CarStatus().getReversing() > 0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void RunApp(String packageName, String classname) {
        try {
            if (!TextUtils.isEmpty(classname)) {
                Log.d("StatusBar/NavBarView", "RunApp with classname:" + classname);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.addFlags(268566528);
                ComponentName cn = new ComponentName(packageName, classname);
                intent.setComponent(cn);
                getContext().startActivity(intent);
                return;
            }
            Log.d("StatusBar/NavBarView", "RunApp without classname");
            PackageInfo pi = getContext().getPackageManager().getPackageInfo(packageName, 0);
            if (pi != null) {
                Intent resolveIntent = new Intent("android.intent.action.MAIN", (Uri) null);
                resolveIntent.setPackage(pi.packageName);
                PackageManager pManager = getContext().getPackageManager();
                List<ResolveInfo> apps = pManager.queryIntentActivities(resolveIntent, 0);
                ResolveInfo ri = apps.iterator().next();
                if (ri != null) {
                    String packageName2 = ri.activityInfo.packageName;
                    String className = ri.activityInfo.name;
                    Intent intent2 = new Intent("android.intent.action.MAIN");
                    intent2.addCategory("android.intent.category.LAUNCHER");
                    intent2.setFlags(268435456);
                    ComponentName cn2 = new ComponentName(packageName2, className);
                    intent2.setComponent(cn2);
                    getContext().startActivity(intent2);
                } else {
                    Log.e("StatusBar/NavBarView", "RunApp ResolveInfo == null!");
                }
                return;
            }
            Log.e("StatusBar/NavBarView", "RunApp get app error!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAirData(boolean show) {
        Button btImage;
        if (!SystemUIApplication.isPortraitWindowMode(getContext()) || (btImage = (Button) findViewByIdCompat(R.id.bt_image)) == null || this.mAirLayout == null) {
            return;
        }
        if (!show) {
            this.mAirLayout.setVisibility(8);
            btImage.setVisibility(0);
        } else {
            this.mAirLayout.setVisibility(0);
            btImage.setVisibility(8);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:41:0x009d  */
    public void updateAirBackground(int theme) {
        if (!SystemUIApplication.isPortraitWindowMode(getContext())) {
            return;
        }
        int nResID = 0;
        switch (theme) {
            case 1:
                nResID = R.drawable.air_bg_p1;
                break;
            case 2:
                nResID = R.drawable.air_bg_p2;
                break;
            case 3:
                nResID = R.drawable.air_bg_p3;
                break;
            case 4:
                nResID = R.drawable.air_bg_p4;
                break;
            case 5:
                nResID = R.drawable.air_bg_p5;
                break;
            case 6:
                nResID = R.drawable.air_bg_p6;
                break;
            case 7:
                nResID = R.drawable.air_bg_p7;
                break;
            default:
                switch (theme) {
                    case 9:
                        nResID = R.drawable.air_bg_p9;
                        break;
                    case 10:
                        nResID = R.drawable.air_bg_p10;
                        break;
                    case 11:
                        nResID = R.drawable.air_bg_p11;
                        break;
                    case 12:
                        nResID = R.drawable.air_bg_p12;
                        break;
                    case 13:
                        nResID = R.drawable.air_bg_p13;
                        break;
                    case 14:
                        nResID = R.drawable.air_bg_p14;
                        break;
                    case 15:
                        nResID = R.drawable.air_bg_p15;
                        break;
                    default:
                        switch (theme) {
                            case 19:
                                nResID = R.drawable.air_bg_p19;
                                break;
                            case 20:
                                nResID = R.drawable.air_bg_p20;
                                break;
                            case 21:
                                nResID = R.drawable.air_bg_xt167;
                                break;
                            case 22:
                                nResID = R.drawable.air_bg_xt167;
                                break;
                            case 23:
                                nResID = R.drawable.air_bg_p11;
                                break;
                            default:
                                switch (theme) {
                                    case 166:
                                        nResID = R.drawable.air_bg_xt166;
                                        break;
                                    case 167:
                                        nResID = R.drawable.air_bg_xt167;
                                        break;
                                    default:
                                        switch (theme) {
                                            case 281:
                                                nResID = R.drawable.air_bg_xt167;
                                                break;
                                            case 282:
                                                nResID = R.drawable.air_bg_xt167;
                                                break;
                                            default:
                                                switch (theme) {
                                                    case 319:
                                                        nResID = R.drawable.air_bg_p3;
                                                        break;
                                                    case 320:
                                                        this.mAirLayout.setBackgroundResource(R.drawable.air_bg_p3);
                                                        break;
                                                    default:
                                                        switch (theme) {
                                                            case 17:
                                                                nResID = R.drawable.air_bg_p17;
                                                                break;
                                                            case 45:
                                                                nResID = R.drawable.air_bg_p45;
                                                                break;
                                                            case 51:
                                                                nResID = R.drawable.air_bg_sa51;
                                                                break;
                                                            case com.android.systemui.plugins.R.styleable.AppCompatTheme_radioButtonStyle /* 85 */:
                                                                nResID = R.drawable.air_bg_p4;
                                                                break;
                                                            case 195:
                                                                nResID = R.drawable.air_bg_xt195;
                                                                break;
                                                            case 279:
                                                                nResID = R.drawable.air_bg_p10;
                                                                break;
                                                            case 323:
                                                                nResID = R.drawable.air_bg_xt167;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
        }
        if (nResID != 0) {
            this.mAirLayout.setBackground(SkinCompatResources.getInstance().getDrawable(nResID));
        }
    }

    public void updateAir(AIR air_obj) {
        if (!SystemUIApplication.isPortraitWindowMode(getContext())) {
            return;
        }
        this.mAirLayout.setVisibility(0);
        if (this.layout_air_func != null) {
            this.layout_air_func.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarView.17
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (NavigationBarView.this.isBackCar()) {
                        return;
                    }
                    NavigationBarView.this.mContext.sendBroadcast(new Intent("StartAirCondition"));
                }
            });
        }
        Button btImage = (Button) findViewByIdCompat(R.id.bt_image);
        if (btImage != null) {
            btImage.setVisibility(8);
        }
        UpdateManual(air_obj.getManual());
        UpdateAutoDisfog(air_obj.getAutoDisFog());
        int bigAutoFlag = air_obj.getBigAutoFlag();
        int smallAutoFlag = air_obj.getSmallAutoFlag();
        if (bigAutoFlag == 1) {
            UpdateAuto(0);
        } else if (smallAutoFlag == 1) {
            UpdateAuto(1);
        } else {
            UpdateAuto(3);
        }
        UpdateSpeed(air_obj.getWind_level(), air_obj.getWindLevMax());
        Button btn_on_off = (Button) findViewByIdCompat(R.id.btn_on_off);
        if (btn_on_off != null) {
            if (air_obj.getPowerFlag() == 1) {
                if (this.skinName.equals("p38")) {
                    btn_on_off.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_on_p38));
                } else {
                    btn_on_off.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_on));
                }
            } else if (this.skinName.equals("p38")) {
                btn_on_off.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_off_p38));
            } else {
                btn_on_off.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_off_p));
            }
        }
        UpdateDirection(air_obj.getWindMode());
        updateRightDirection(air_obj.getWindModeRight());
        if (this.curtAirTempMirror) {
            UpdateLeftTemp(cutTheTempUnit(air_obj.getRightTempNum()));
            UpdateRightTemp(cutTheTempUnit(air_obj.getLeftTempNum()));
        } else {
            UpdateLeftTemp(cutTheTempUnit(air_obj.getLeftTempNum()));
            UpdateRightTemp(cutTheTempUnit(air_obj.getRightTempNum()));
        }
        UpdateCycle(air_obj.getCycleMode());
        UpdateMaxFront(air_obj.getFrontDisFog());
        UpdateRear(air_obj.getRearDisFog());
        UpdateLeftSeatHeat(air_obj.getLeftChairHeat());
        UpdateRightSeatHeat(air_obj.getRightChairHeat());
        UpdateSteerChairHeat(air_obj.getSteerChairHeat());
        UpdateLeftSeatWind(air_obj.getLeftChairWind());
        UpdateRightSeatWind(air_obj.getRightChairWind());
        UpdateLeftWaist(air_obj.getLeftWaist());
        UpdateRightWaist(air_obj.getRightWaist());
        UpdateLeftMassage(air_obj.getLeftMassage());
        UpdateRightMassage(air_obj.getRightMassage());
        UpdateDual(air_obj.getDual());
        upDataIon(air_obj.getIon());
        upDataZone(air_obj.getZone());
        updateSteerHeat(air_obj.getHeatSteer());
        updateSync(air_obj.getSyncFlag());
        updateSoft(air_obj.getSoft());
        updateNomal(air_obj.getNomal());
        updateFast(air_obj.getFast());
        updateFollower(air_obj.getFollower());
        UpdateRest(air_obj.getRest());
        updateTemp(air_obj.getTemp());
        UpdateRearLock(this.mRearLock);
        if (air_obj.getACMax() == 0) {
            SetViewGone(R.id.ac, true);
        } else {
            UpdateACandACMax(2);
        }
        Button btn_ac = (Button) findViewByIdCompat(R.id.btn_ac);
        if (btn_ac != null) {
            if (air_obj.getACFlag() == 0) {
                if (this.skinName.equals("p38")) {
                    btn_ac.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_ac_p38_p));
                } else {
                    btn_ac.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_ac_p));
                }
            } else if (this.skinName.equals("p38")) {
                btn_ac.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_ac_p38));
            } else {
                btn_ac.setBackgroundResource(SkinCompatResources.getInstance().getId(R.drawable.btn_air_ac));
            }
        }
        UpdateAirOnProfile2(air_obj.getAutoMode());
        UpdateECO(air_obj.getEcon());
        UpdateAQ(air_obj.getAirQuality());
        UpdateForntRZC(air_obj.getMaxFront());
        UpdateRearRZC(air_obj.getRear());
        updateMaxIconFront(air_obj.getMaxFront());
        updateAutoLevel(air_obj.getAutoLevel());
        updateWindIntensity(air_obj.getWindIntensity());
        updateRearLock(air_obj.getLockRear());
        UpdateAirNanoe(air_obj.getNanoe());
        UpdateAirAutoSwing(air_obj.getAutoSwing());
        UpdateAirFilter(air_obj.getFilter());
        UpdateAirPTC(air_obj.getPTC());
        UpdateAirHeat(air_obj.getHeat());
    }

    private String cutTheTempUnit(String str_temp) {
        if (str_temp == null) {
            return "";
        }
        return str_temp.replace("℃", "").replace("℉", "");
    }

    private void UpdateManual(int nValue) {
        SetViewVisible(R.id.air_manual, nValue);
    }

    private void UpdateAutoDisfog(int nValue) {
        SetViewVisible(R.id.air_auto_disfog, nValue);
    }

    private void UpdateAuto(int nWind) {
        int nResID = 0;
        switch (nWind) {
            case 0:
                nResID = R.drawable.raise_vvv_autob;
                break;
            case 1:
                nResID = R.drawable.raise_vvv_autos;
                break;
        }
        if (nResID == 0) {
            SetViewVisible(R.id.wind, 0);
        } else {
            SetViewVisible(R.id.wind, 1);
        }
        SetViewImageByID(R.id.wind, nResID);
    }

    public void UpdateSpeed(int nWind, int windMax) {
        if (this.old_level_max != windMax) {
            Log.i("StatusBar/NavBarView", "level max change hide " + this.old_level_max);
            getAirBar(this.old_level_max).setVisibility(8);
        }
        ProgressBar pro = getAirBar(windMax);
        this.old_level_max = windMax;
        if (pro != null) {
            pro.setMax(windMax);
        }
        if (pro != null) {
            Log.i("StatusBar/NavBarView", "set progress bar " + nWind);
            pro.setProgress(nWind);
        }
    }

    private ProgressBar getAirBar(int max) {
        ProgressBar pro;
        ProgressBar pro2 = (ProgressBar) findViewByIdCompat(R.id.progressBar1);
        pro2.setVisibility(8);
        if (max != 6) {
            if (max == 8) {
                pro = (ProgressBar) findViewByIdCompat(R.id.progressBar8);
            } else {
                pro = (ProgressBar) findViewByIdCompat(R.id.progressBar1);
            }
        } else {
            pro = (ProgressBar) findViewByIdCompat(R.id.progressBar6);
        }
        pro.setVisibility(0);
        pro.setMax(max);
        return pro;
    }

    private void UpdateDirection(int nShow) {
        Log.i("StatusBar/NavBarView", "show wind mode " + nShow);
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nShow) {
                case 0:
                    nResID = R.drawable.raise_wind_null_p38;
                    break;
                case 1:
                    nResID = R.drawable.raise_wind_down_p38;
                    break;
                case 2:
                    nResID = R.drawable.raise_wind_horizontal_p38;
                    break;
                case 3:
                    nResID = R.drawable.raise_wind_horizontal_down_p38;
                    break;
                case 4:
                    nResID = R.drawable.raise_wind_up_p38;
                    break;
                case 5:
                    nResID = R.drawable.raise_wind_up_down_p38;
                    break;
                case 6:
                    nResID = R.drawable.raise_wind_up_horizontal_p38;
                    break;
                case 7:
                    nResID = R.drawable.raise_wind_up_all_p38;
                    break;
                case 8:
                    nResID = R.drawable.raise_wind_auto_p38;
                    break;
                case 9:
                    nResID = R.drawable.raise_wind_front_defog_p38;
                    break;
            }
        } else {
            switch (nShow) {
                case 0:
                    nResID = R.drawable.raise_wind_null;
                    break;
                case 1:
                    nResID = R.drawable.raise_wind_down;
                    break;
                case 2:
                    nResID = R.drawable.raise_wind_horizontal;
                    break;
                case 3:
                    nResID = R.drawable.raise_wind_horizontal_down;
                    break;
                case 4:
                    nResID = R.drawable.raise_wind_up;
                    break;
                case 5:
                    nResID = R.drawable.raise_wind_up_down;
                    break;
                case 6:
                    nResID = R.drawable.raise_wind_up_horizontal;
                    break;
                case 7:
                    nResID = R.drawable.raise_wind_up_all;
                    break;
                case 8:
                    nResID = R.drawable.raise_wind_auto;
                    break;
                case 9:
                    nResID = R.drawable.raise_wind_front_defog;
                    break;
            }
        }
        SetViewVisible(R.id.direction, true);
        SetViewImageByID(R.id.direction, nResID);
    }

    private void updateRightDirection(int nShow) {
        Log.i("StatusBar/NavBarView", "show wind mode " + nShow);
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nShow) {
                case 0:
                    return;
                case 1:
                    nResID = R.drawable.raise_wind_right_down_p38;
                    break;
                case 2:
                    nResID = R.drawable.raise_wind_right_horizontal_p38;
                    break;
                case 3:
                    nResID = R.drawable.raise_wind_horizontal_right_down_p38;
                    break;
                case 4:
                    nResID = R.drawable.raise_wind_right_up_p38;
                    break;
                case 5:
                    nResID = R.drawable.raise_wind_up_right_down_p38;
                    break;
                case 6:
                    nResID = R.drawable.raise_wind_up_right_horizontal_p38;
                    break;
                case 7:
                    nResID = R.drawable.raise_wind_up_right_all_p38;
                    break;
                case 8:
                    nResID = R.drawable.raise_wind_right_auto_p38;
                    break;
                case 9:
                    nResID = R.drawable.raise_wind_up_right_front_defog_p38;
                    break;
            }
        } else {
            switch (nShow) {
                case 0:
                    return;
                case 1:
                    nResID = R.drawable.raise_wind_right_down;
                    break;
                case 2:
                    nResID = R.drawable.raise_wind_right_horizontal;
                    break;
                case 3:
                    nResID = R.drawable.raise_wind_horizontal_right_down;
                    break;
                case 4:
                    nResID = R.drawable.raise_wind_right_up;
                    break;
                case 5:
                    nResID = R.drawable.raise_wind_up_right_down;
                    break;
                case 6:
                    nResID = R.drawable.raise_wind_up_right_horizontal;
                    break;
                case 7:
                    nResID = R.drawable.raise_wind_up_right_all;
                    break;
                case 8:
                    nResID = R.drawable.raise_wind_right_auto;
                    break;
                case 9:
                    nResID = R.drawable.raise_wind_up_right_front_defog;
                    break;
            }
        }
        SetViewVisible(R.id.direction_right, true);
        SetViewImageByID(R.id.direction_right, nResID);
    }

    private void UpdateLeftTemp(String nStrTemp) {
        SetViewVisible(R.id.leftTemp, true);
        SetViewTextByID(R.id.leftTemp, nStrTemp);
    }

    private void UpdateRightTemp(String nStrTemp) {
        SetViewVisible(R.id.rightTemp, true);
        SetViewTextByID(R.id.rightTemp, nStrTemp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOrSwapAirTemp() {
        boolean reqAirTempMirrorCfg = Settings.System.getInt(getContext().getContentResolver(), "air_temp_reverse_flag", 0) > 0;
        Log.v("StatusBar/NavBarView", "reqAirTempMirrorCfg|curtAirTempMirror:" + reqAirTempMirrorCfg + "|" + this.curtAirTempMirror);
        if (reqAirTempMirrorCfg == this.curtAirTempMirror) {
            return;
        }
        TextView leftView = (TextView) findViewByIdCompat(R.id.leftTemp);
        TextView rightView = (TextView) findViewByIdCompat(R.id.rightTemp);
        if (leftView == null || rightView == null) {
            return;
        }
        CharSequence leftTempStr = leftView.getText();
        CharSequence rightTempStr = rightView.getText();
        leftView.setText(rightTempStr);
        rightView.setText(leftTempStr);
        this.curtAirTempMirror = reqAirTempMirrorCfg;
        Log.v("StatusBar/NavBarView", "swap air temp suc!");
    }

    private void UpdateCycle(int nCycle) {
        Log.i("StatusBar/NavBarView", "nCycle " + nCycle);
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nCycle) {
                case 1:
                    nResID = R.drawable.raise_cycle_in_p38;
                    break;
                case 2:
                    nResID = R.drawable.raise_cycle_out_p38;
                    break;
                case 3:
                    nResID = R.drawable.cycle_in_auto_gm_p38;
                    break;
            }
        } else {
            switch (nCycle) {
                case 1:
                    nResID = R.drawable.raise_cycle_in;
                    break;
                case 2:
                    nResID = R.drawable.raise_cycle_out;
                    break;
                case 3:
                    nResID = R.drawable.cycle_in_auto_gm;
                    break;
            }
        }
        if (nCycle == 0) {
            SetViewVisible(R.id.air_cycle, 0);
        } else {
            SetViewVisible(R.id.air_cycle, 1);
        }
        SetViewImageByID(R.id.air_cycle, nResID);
    }

    private void UpdateMaxFront(int nValue) {
        SetViewVisible(R.id.front, nValue);
    }

    private void UpdateRear(int nValue) {
        SetViewVisible(R.id.rear, nValue);
    }

    private void UpdateLeftSeatHeat(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            if (nValue != 15) {
                switch (nValue) {
                    case 1:
                        nResID = R.drawable.raise_vvv_heat_left1_p38;
                        break;
                    case 2:
                        nResID = R.drawable.raise_vvv_heat_left2_p38;
                        break;
                    case 3:
                        nResID = R.drawable.raise_vvv_heat_left3_p38;
                        break;
                    case 4:
                        nResID = R.drawable.raise_vvv_heat_left4_p38;
                        break;
                }
            } else {
                nResID = R.drawable.raise_vvv_heat_left_auto_p38;
            }
        } else if (nValue != 15) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.raise_vvv_heat_left1;
                    break;
                case 2:
                    nResID = R.drawable.raise_vvv_heat_left2;
                    break;
                case 3:
                    nResID = R.drawable.raise_vvv_heat_left3;
                    break;
                case 4:
                    nResID = R.drawable.raise_vvv_heat_left4;
                    break;
            }
        } else {
            nResID = R.drawable.raise_vvv_heat_left_auto;
        }
        if (nResID == 0) {
            SetViewVisible(R.id.leftSeat, 0);
        } else {
            SetViewVisible(R.id.leftSeat, 1);
        }
        SetViewImageByID(R.id.leftSeat, nResID);
    }

    private void UpdateRightSeatHeat(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            if (nValue != 15) {
                switch (nValue) {
                    case 1:
                        nResID = R.drawable.raise_vvv_heat_right1_p38;
                        break;
                    case 2:
                        nResID = R.drawable.raise_vvv_heat_right2_p38;
                        break;
                    case 3:
                        nResID = R.drawable.raise_vvv_heat_right3_p38;
                        break;
                    case 4:
                        nResID = R.drawable.raise_vvv_heat_right4_p38;
                        break;
                }
            } else {
                nResID = R.drawable.raise_vvv_heat_right_auto_p38;
            }
        } else if (nValue != 15) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.raise_vvv_heat_right1;
                    break;
                case 2:
                    nResID = R.drawable.raise_vvv_heat_right2;
                    break;
                case 3:
                    nResID = R.drawable.raise_vvv_heat_right3;
                    break;
                case 4:
                    nResID = R.drawable.raise_vvv_heat_right4;
                    break;
            }
        } else {
            nResID = R.drawable.raise_vvv_heat_right_auto;
        }
        if (nResID == 0) {
            SetViewVisible(R.id.rightSeat, 0);
        } else {
            SetViewVisible(R.id.rightSeat, 1);
        }
        SetViewImageByID(R.id.rightSeat, nResID);
    }

    private void UpdateSteerChairHeat(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.steer_chair_heat1_p38;
                    break;
                case 2:
                    nResID = R.drawable.steer_chair_heat2_p38;
                    break;
                case 3:
                    nResID = R.drawable.steer_chair_heat3_p38;
                    break;
                case 4:
                    nResID = R.drawable.steer_chair_heat0_p38;
                    break;
            }
        } else {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.steer_chair_heat1;
                    break;
                case 2:
                    nResID = R.drawable.steer_chair_heat2;
                    break;
                case 3:
                    nResID = R.drawable.steer_chair_heat3;
                    break;
                case 4:
                    nResID = R.drawable.steer_chair_heat0;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.steer_chair_heat, 0);
        } else {
            SetViewVisible(R.id.steer_chair_heat, 1);
        }
        SetViewImageByID(R.id.steer_chair_heat, nResID);
    }

    private void UpdateLeftSeatWind(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            if (nValue != 15) {
                switch (nValue) {
                    case 1:
                        nResID = R.drawable.aerationleft_1_p38;
                        break;
                    case 2:
                        nResID = R.drawable.aerationleft_2_p38;
                        break;
                    case 3:
                        nResID = R.drawable.aerationleft_3_p38;
                        break;
                    case 4:
                        nResID = R.drawable.aerationleft_4_p38;
                        break;
                }
            } else {
                nResID = R.drawable.aerationleft_auto_p38;
            }
        } else if (nValue != 15) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.aerationleft_1;
                    break;
                case 2:
                    nResID = R.drawable.aerationleft_2;
                    break;
                case 3:
                    nResID = R.drawable.aerationleft_3;
                    break;
                case 4:
                    nResID = R.drawable.aerationleft_4;
                    break;
            }
        } else {
            nResID = R.drawable.aerationleft_auto;
        }
        if (nResID == 0) {
            SetViewVisible(R.id.leftWind, 0);
        } else {
            SetViewVisible(R.id.leftWind, 1);
        }
        SetViewImageByID(R.id.leftWind, nResID);
    }

    private void UpdateRightSeatWind(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            if (nValue != 15) {
                switch (nValue) {
                    case 1:
                        nResID = R.drawable.aerationright_1_p38;
                        break;
                    case 2:
                        nResID = R.drawable.aerationright_2_p38;
                        break;
                    case 3:
                        nResID = R.drawable.aerationright_3_p38;
                        break;
                    case 4:
                        nResID = R.drawable.aerationright_4_p38;
                        break;
                }
            } else {
                nResID = R.drawable.aerationright_auto_p38;
            }
        } else if (nValue != 15) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.aerationright_1;
                    break;
                case 2:
                    nResID = R.drawable.aerationright_2;
                    break;
                case 3:
                    nResID = R.drawable.aerationright_3;
                    break;
                case 4:
                    nResID = R.drawable.aerationright_4;
                    break;
            }
        } else {
            nResID = R.drawable.aerationright_auto;
        }
        if (nResID == 0) {
            SetViewVisible(R.id.rightWind, 0);
        } else {
            SetViewVisible(R.id.rightWind, 1);
        }
        SetViewImageByID(R.id.rightWind, nResID);
    }

    private void UpdateLeftWaist(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_waist_left1_p38;
                    break;
                case 2:
                    nResID = R.drawable.air_waist_left2_p38;
                    break;
                case 3:
                    nResID = R.drawable.air_waist_left3_p38;
                    break;
                case 4:
                    nResID = R.drawable.air_waist_left4_p38;
                    break;
                case 5:
                    nResID = R.drawable.air_waist_left5_p38;
                    break;
            }
        } else {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_waist_left1;
                    break;
                case 2:
                    nResID = R.drawable.air_waist_left2;
                    break;
                case 3:
                    nResID = R.drawable.air_waist_left3;
                    break;
                case 4:
                    nResID = R.drawable.air_waist_left4;
                    break;
                case 5:
                    nResID = R.drawable.air_waist_left5;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.air_left_waist, 0);
        } else {
            SetViewVisible(R.id.air_left_waist, 1);
        }
        SetViewImageByID(R.id.air_left_waist, nResID);
    }

    private void UpdateRightWaist(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_waist_right1_p38;
                    break;
                case 2:
                    nResID = R.drawable.air_waist_right2_p38;
                    break;
                case 3:
                    nResID = R.drawable.air_waist_right3_p38;
                    break;
                case 4:
                    nResID = R.drawable.air_waist_right4_p38;
                    break;
                case 5:
                    nResID = R.drawable.air_waist_right5_p38;
                    break;
            }
        } else {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_waist_right1;
                    break;
                case 2:
                    nResID = R.drawable.air_waist_right2;
                    break;
                case 3:
                    nResID = R.drawable.air_waist_right3;
                    break;
                case 4:
                    nResID = R.drawable.air_waist_right4;
                    break;
                case 5:
                    nResID = R.drawable.air_waist_right5;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.air_right_waist, 0);
        } else {
            SetViewVisible(R.id.air_right_waist, 1);
        }
        SetViewImageByID(R.id.air_right_waist, nResID);
    }

    private void UpdateLeftMassage(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_massage_left1_p38;
                    break;
                case 2:
                    nResID = R.drawable.air_massage_left2_p38;
                    break;
            }
        } else {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_massage_left1;
                    break;
                case 2:
                    nResID = R.drawable.air_massage_left2;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.air_left_massage, 0);
        } else {
            SetViewVisible(R.id.air_left_massage, 1);
        }
        SetViewImageByID(R.id.air_left_massage, nResID);
    }

    private void UpdateRightMassage(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_massage_right1_p38;
                    break;
                case 2:
                    nResID = R.drawable.air_massage_right2_p38;
                    break;
            }
        } else {
            switch (nValue) {
                case 1:
                    nResID = R.drawable.air_massage_right1;
                    break;
                case 2:
                    nResID = R.drawable.air_massage_right2;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.air_right_massage, 0);
        } else {
            SetViewVisible(R.id.air_right_massage, 1);
        }
        SetViewImageByID(R.id.air_right_massage, nResID);
    }

    private void UpdateDual(int nValue) {
        SetViewVisible(R.id.dual, nValue);
    }

    private void upDataIon(int nValue) {
        SetViewVisible(R.id.ion, nValue);
    }

    private void upDataZone(int nValue) {
        SetViewVisible(R.id.zone, nValue);
    }

    private void updateSteerHeat(int steerHeat) {
        SetViewVisible(R.id.steer_heat, steerHeat);
    }

    private void updateSync(int nValue) {
        SetViewVisible(R.id.raise_sync, nValue);
    }

    private void updateSoft(int nValue) {
        SetViewVisible(R.id.soft, nValue);
    }

    private void updateNomal(int nValue) {
        SetViewVisible(R.id.nomal, nValue);
    }

    private void updateFast(int nValue) {
        SetViewVisible(R.id.fast, nValue);
    }

    private void updateFollower(int nValue) {
        SetViewVisible(R.id.follower, nValue);
    }

    private void UpdateRest(int nValue) {
        SetViewVisible(R.id.rest, nValue);
    }

    private void updateTemp(int nValue) {
        SetViewVisible(R.id.temp, nValue);
    }

    private void UpdateRearLock(int nValue) {
        SetViewVisible(R.id.rearlock, nValue);
    }

    private void UpdateACandACMax(int nValue) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (nValue) {
                case 0:
                    nResID = R.drawable.raise_vvv_ac_p38;
                    break;
                case 1:
                    nResID = R.drawable.raise_vvv_ac_n_p38;
                    break;
                case 2:
                    nResID = R.drawable.raise_vvv_acmax_p38;
                    break;
                case 3:
                    nResID = R.drawable.raise_vvv_acauto_p38;
                    break;
                case 4:
                    nResID = R.drawable.raise_vvv_econ_p38;
                    break;
            }
        } else {
            switch (nValue) {
                case 0:
                    nResID = R.drawable.raise_vvv_ac;
                    break;
                case 1:
                    nResID = R.drawable.raise_vvv_ac_n;
                    break;
                case 2:
                    nResID = R.drawable.raise_vvv_acmax;
                    break;
                case 3:
                    nResID = R.drawable.raise_vvv_acauto;
                    break;
                case 4:
                    nResID = R.drawable.raise_vvv_econ;
                    break;
            }
        }
        SetViewVisible(R.id.ac, true);
        SetViewImageByID(R.id.ac, nResID);
    }

    private void UpdateAirOnProfile2(int Profile) {
        ImageView imageView = (ImageView) findViewByIdCompat(R.id.autoModef);
        if (imageView != null) {
            imageView.setImageResource(SkinCompatResources.getInstance().getId(R.drawable.air_auto));
            if (Profile == 1) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    private void UpdateECO(int nValue) {
        SetViewVisible(R.id.eco, nValue);
    }

    private void UpdateAQ(int nValue) {
        SetViewVisible(R.id.air_quality, nValue);
    }

    private void UpdateForntRZC(int nValue) {
        SetViewVisible(R.id.front_RZC, nValue);
    }

    private void UpdateRearRZC(int nValue) {
        SetViewVisible(R.id.rear_RZC, nValue);
    }

    private void updateMaxIconFront(int maxFront) {
        SetViewVisible(R.id.max_front, maxFront);
    }

    private void updateAutoLevel(int autoLevel) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (autoLevel) {
                case 1:
                    nResID = R.drawable.autof_low_p38;
                    break;
                case 2:
                    nResID = R.drawable.autof_m_p38;
                    break;
                case 3:
                    nResID = R.drawable.autof_s_p38;
                    break;
            }
        } else {
            switch (autoLevel) {
                case 1:
                    nResID = R.drawable.autof_low;
                    break;
                case 2:
                    nResID = R.drawable.autof_m;
                    break;
                case 3:
                    nResID = R.drawable.autof_s;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.auto_level, 0);
        } else {
            SetViewVisible(R.id.auto_level, 1);
        }
        SetViewImageByID(R.id.auto_level, nResID);
    }

    private void updateWindIntensity(int windIntensity) {
        int nResID = 0;
        if (this.skinName.equals("p38")) {
            switch (windIntensity) {
                case 1:
                    nResID = R.drawable.autof_low_1_p38;
                    break;
                case 2:
                    nResID = R.drawable.autof_m_1_p38;
                    break;
                case 3:
                    nResID = R.drawable.autof_s_1_p38;
                    break;
            }
        } else {
            switch (windIntensity) {
                case 1:
                    nResID = R.drawable.autof_low_1;
                    break;
                case 2:
                    nResID = R.drawable.autof_m_1;
                    break;
                case 3:
                    nResID = R.drawable.autof_s_1;
                    break;
            }
        }
        if (nResID == 0) {
            SetViewVisible(R.id.wind_intensisy, 0);
        } else {
            SetViewVisible(R.id.wind_intensisy, 1);
        }
        SetViewImageByID(R.id.wind_intensisy, nResID);
    }

    private void updateRearLock(int rearLock) {
        SetViewVisible(R.id.rear_lock, rearLock);
    }

    private void UpdateAirNanoe(int nanoe) {
        ImageView imageView = (ImageView) findViewByIdCompat(R.id.air_nanoe);
        if (imageView != null) {
            imageView.setImageResource(SkinCompatResources.getInstance().getId(R.drawable.air_nanoe));
            if (nanoe == 1) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    private void UpdateAirAutoSwing(int autoSwing) {
        ImageView imageView = (ImageView) findViewByIdCompat(R.id.air_autoswing);
        if (imageView != null) {
            imageView.setImageResource(SkinCompatResources.getInstance().getId(R.drawable.air_autoswing));
            if (autoSwing == 1) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    private void UpdateAirFilter(int para) {
        ImageView imageView = (ImageView) findViewByIdCompat(R.id.air_filter);
        if (imageView != null) {
            imageView.setImageResource(SkinCompatResources.getInstance().getId(R.drawable.clean));
            if (para == 1) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    private void UpdateAirPTC(int para) {
        ImageView imageView = (ImageView) findViewByIdCompat(R.id.air_ptc);
        if (imageView != null) {
            imageView.setImageResource(SkinCompatResources.getInstance().getId(R.drawable.ptc));
            if (para == 1) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    private void UpdateAirHeat(int para) {
        ImageView imageView = (ImageView) findViewByIdCompat(R.id.air_heat);
        if (imageView != null) {
            imageView.setImageResource(SkinCompatResources.getInstance().getId(R.drawable.heat));
            if (para == 1) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    private void SetViewVisible(int id, int visibily) {
        View view = findViewByIdCompat(id);
        if (view != null) {
            if (visibily == 1) {
                view.setVisibility(0);
            } else {
                view.setVisibility(8);
            }
        }
    }

    protected void SetViewVisible(int id, boolean bVisible) {
        View view = findViewByIdCompat(id);
        if (view != null) {
            if (bVisible) {
                view.setVisibility(0);
            } else {
                view.setVisibility(4);
            }
        }
    }

    protected void SetViewGone(int id, boolean gone) {
        View view = findViewByIdCompat(id);
        if (view != null) {
            if (gone) {
                view.setVisibility(8);
            } else {
                view.setVisibility(0);
            }
        }
    }

    protected void SetViewTextByID(int id, String nStr) {
        TextView view = (TextView) findViewByIdCompat(id);
        if (view != null) {
            view.setText(nStr);
        }
    }

    protected void SetViewImageByID(int id, int nResID) {
        ImageView view;
        if (nResID != 0 && (view = (ImageView) findViewByIdCompat(id)) != null) {
            view.setImageResource(SkinCompatResources.getInstance().getId(nResID));
        }
    }

    public void handleBluetoothCallback(boolean set) {
        if (this.mController == null) {
            return;
        }
        if (set) {
            this.mController.addCallback(this.mCallback);
        } else {
            this.mController.removeCallback(this.mCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBTStatus() {
        if (this.mController == null || this.mBTConnectStatus == null || !SystemUIApplication.isPortraitWindowMode(getContext())) {
            return;
        }
        setBTConnectStatusBackgroundAndText(this.mController.isBluetoothConnected());
    }

    private void setBTConnectStatusBackgroundAndText(boolean isConnected) {
        int backgroundResId;
        String skinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
        String uiStyle = (getContext().getResources().getConfiguration().uiMode & 48) == 32 ? "night" : "";
        if ("sa181".equals(skinName)) {
            if (uiStyle.equals("night")) {
                backgroundResId = SkinCompatResources.getInstance().getId(isConnected ? R.drawable.bt_on_sa181_night : R.drawable.bt_off_sa181_night);
            } else {
                backgroundResId = SkinCompatResources.getInstance().getId(isConnected ? R.drawable.bt_bg_n_sa181 : R.drawable.bt_bg_p_sa181);
            }
        } else if ("p45".equals(skinName)) {
            backgroundResId = SkinCompatResources.getInstance().getId(isConnected ? R.drawable.bt_on_p45 : R.drawable.bt_off_p45);
        } else if ("xt819".equals(skinName)) {
            backgroundResId = SkinCompatResources.getInstance().getId(isConnected ? R.drawable.bt_on_xt819 : R.drawable.bt_off_xt819);
        } else {
            backgroundResId = SkinCompatResources.getInstance().getId(isConnected ? R.drawable.bt_on_p5 : R.drawable.bt_off_p5);
        }
        this.mBTConnectStatus.setBackgroundResource(backgroundResId);
        this.mBTConnectStatus.setText(SkinCompatResources.getInstance().getString(isConnected ? R.string.bt_connected : R.string.bt_disconnected));
    }

    public View getVolAddButton() {
        return findViewByIdCompat(R.id.add_vol);
    }

    public View getVolReduceButton() {
        return findViewByIdCompat(R.id.reduce_vol);
    }

    public View getMenuHomeButton() {
        return findViewByIdCompat(R.id.menu_home);
    }

    public View getMenuBackButton() {
        return findViewByIdCompat(R.id.menu_back);
    }

    public View getBtButton() {
        return this.mBTConnectStatus;
    }

    public void updateAirBg(Drawable drawable) {
        this.mAirLayout.setBackground(drawable);
    }

    public void initBottomBar() {
        RelativeLayout layout = (RelativeLayout) findViewByIdCompat(R.id.navi_bottom);
        this.mInitView = new InitViewImpl(layout, this.mContext);
    }

    public void initLeftSideBar() {
        ((DZ23CanStatusBarController) Dependency.get(DZ23CanStatusBarController.class)).setUp();
    }

    public void onVolumeChanged(int stream, int volume, boolean mute) {
        int vol = volume;
        if (stream == 3 && mute) {
            vol = 0;
        }
        String value = String.format(Locale.ENGLISH, "%02d", Integer.valueOf(vol));
        if (this.mTvSoundVolume != null) {
            this.mTvSoundVolume.setText(value);
        }
        if (this.jellyProgressView != null) {
            float progress = (vol * 100.0f) / 40.0f;
            this.jellyProgressView.setProgress(progress);
        }
        if (this.mInitView != null) {
            this.mInitView.onVolumeChanged(stream, volume, mute);
        }
    }

    private boolean isNewLauncher() {
        return "1".equals(getSystemProperty("ro.vendor.sys.skin_support", "0"));
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
            Log.d("StatusBar/NavBarView", "Unable to read system properties");
        }
    }

    public void updateBottomBg(String uiStyle) {
        String uiStyle2;
        if (this.mInitView != null) {
            this.skinName = getSystemProperty("persist.sys.skin", "com.hcn.auto.theme.p1");
            if (this.mInitView.isNewBottomUI(this.skinName)) {
                this.mInitView.updateBottomBgUIStyle(uiStyle);
                return;
            }
            if (this.skinName != null) {
                this.skinName = this.skinName.toLowerCase();
                if (uiStyle.equals("night")) {
                    uiStyle2 = "_night";
                } else {
                    uiStyle2 = "";
                }
                if (this.skinName.equals("xt520")) {
                    if (this.mInitView.getBtnAllApp() != null) {
                        String resAllAppsName = this.skinName + "_icon_all_apps_selector" + uiStyle2;
                        Drawable resAllAppsDraw = SkinCompatResources.getInstance().getDrawable(resAllAppsName);
                        this.mInitView.getBtnAllApp().setBackground(resAllAppsDraw);
                    }
                    if (this.mInitView.getBtnAllApp() != null) {
                        String resNaviName = this.skinName + "_icon_navi_selector" + uiStyle2;
                        Drawable resNaviDraw = SkinCompatResources.getInstance().getDrawable(resNaviName);
                        this.mInitView.getBtnNavi().setBackground(resNaviDraw);
                    }
                    if (this.mInitView.getBtnMusic() != null) {
                        String resMusicName = this.skinName + "_icon_music_selector" + uiStyle2;
                        Drawable resMusicDraw = SkinCompatResources.getInstance().getDrawable(resMusicName);
                        this.mInitView.getBtnMusic().setBackground(resMusicDraw);
                    }
                    if (this.mInitView.getBtnRadio() != null) {
                        String resRadioName = this.skinName + "_icon_radio_selector" + uiStyle2;
                        Drawable resRadioDraw = SkinCompatResources.getInstance().getDrawable(resRadioName);
                        this.mInitView.getBtnRadio().setBackground(resRadioDraw);
                    }
                    if (this.mInitView.getTime() != null) {
                        if (uiStyle2.equals("_night")) {
                            this.mInitView.getTime().setTextColor(getContext().getResources().getColor(R.color.xt520_volume_text_color_night));
                        } else {
                            this.mInitView.getTime().setTextColor(getContext().getResources().getColor(R.color.xt520_volume_text_color));
                        }
                    }
                }
            }
        }
    }

    void initPortSkinResource() {
        String configSkin = com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "");
        String apkPath = null;
        Log.d("StatusBar/NavBarView", "onCreate " + configSkin);
        if (!"".equals(configSkin)) {
            apkPath = getApkPath(getContext(), "com.hcn.systemui.theme." + configSkin);
            Log.v("StatusBar/NavBarView", "skin pkg " + apkPath);
            if (TextUtils.isEmpty(apkPath)) {
                apkPath = SkinApkManager.getInstance(getContext().getApplicationContext()).getApkPathBySkinName(configSkin);
                Log.e("StatusBar/NavBarView", "skin pkg --is empty" + apkPath);
            } else {
                SkinApkManager.getInstance(getContext().getApplicationContext()).setSkinApkPath(apkPath);
                Log.e("StatusBar/NavBarView", "skin pkg --not empty" + apkPath);
            }
        }
        SkinApkManager.getInstance(getContext().getApplicationContext()).loadSkinByAbsolutePath(apkPath);
        this.mResources = SkinCompatResources.getInstance().getSkinResources();
        this.mPackageName = SkinCompatResources.getInstance().getSkinPkgName();
    }

    public static String getApkPath(Context context, String pkg) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(pkg, 0);
            if (info == null) {
                return null;
            }
            String apkPath = info.sourceDir;
            Log.i("StatusBar/NavBarView", "getApkPath apkPath:" + apkPath + " pkg:" + pkg);
            return apkPath;
        } catch (Exception e) {
            Log.e("StatusBar/NavBarView", "Didn't find package " + pkg);
            return null;
        }
    }
}
