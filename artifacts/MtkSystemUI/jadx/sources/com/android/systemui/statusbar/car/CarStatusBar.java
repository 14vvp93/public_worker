package com.android.systemui.statusbar.car;

import android.app.ActivityManager;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingLog;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.car.hvac.HvacController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CarStatusBar extends StatusBar implements CarBatteryController.BatteryViewHandler {
    public static final boolean ENABLE_HVAC_CONNECTION = !SystemProperties.getBoolean("android.car.hvac.demo", true);
    private ActivityManagerWrapper mActivityManagerWrapper;
    private CarBatteryController mCarBatteryController;
    private CarFacetButtonController mCarFacetButtonController;
    private ConnectedDeviceSignalController mConnectedDeviceSignalController;
    private DeviceProvisionedController mDeviceProvisionedController;
    private FullscreenUserSwitcher mFullscreenUserSwitcher;
    private CarNavigationBarView mLeftNavigationBarView;
    private ViewGroup mLeftNavigationBarWindow;
    private CarNavigationBarView mNavigationBarView;
    private ViewGroup mNavigationBarWindow;
    private Drawable mNotificationPanelBackground;
    private CarNavigationBarView mRightNavigationBarView;
    private ViewGroup mRightNavigationBarWindow;
    private boolean mShowBottom;
    private boolean mShowLeft;
    private boolean mShowRight;
    private TaskStackListenerImpl mTaskStackListener;
    private final Object mQueueLock = new Object();
    private boolean mDeviceIsProvisioned = true;

    @Override // com.android.systemui.statusbar.phone.StatusBar, com.android.systemui.SystemUI
    public void start() {
        super.start();
        this.mTaskStackListener = new TaskStackListenerImpl();
        this.mActivityManagerWrapper = ActivityManagerWrapper.getInstance();
        this.mActivityManagerWrapper.registerTaskStackListener(this.mTaskStackListener);
        this.mStackScroller.setScrollingEnabled(true);
        createBatteryController();
        this.mCarBatteryController.startListening();
        if (ENABLE_HVAC_CONNECTION) {
            Log.d("CarStatusBar", "Connecting to HVAC service");
            ((HvacController) Dependency.get(HvacController.class)).connectToCarService();
        }
        this.mCarFacetButtonController = (CarFacetButtonController) Dependency.get(CarFacetButtonController.class);
        this.mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
        this.mDeviceIsProvisioned = this.mDeviceProvisionedController.isDeviceProvisioned();
        if (!this.mDeviceIsProvisioned) {
            this.mDeviceProvisionedController.addCallback(new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.car.CarStatusBar.1
                @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
                public void onDeviceProvisionedChanged() {
                    CarStatusBar.this.mDeviceIsProvisioned = CarStatusBar.this.mDeviceProvisionedController.isDeviceProvisioned();
                    CarStatusBar.this.restartNavBars();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restartNavBars() {
        this.mCarFacetButtonController.removeAll();
        if (ENABLE_HVAC_CONNECTION) {
            ((HvacController) Dependency.get(HvacController.class)).removeAllComponents();
        }
        if (this.mNavigationBarWindow != null) {
            this.mNavigationBarWindow.removeAllViews();
            this.mNavigationBarView = null;
        }
        if (this.mLeftNavigationBarWindow != null) {
            this.mLeftNavigationBarWindow.removeAllViews();
            this.mLeftNavigationBarView = null;
        }
        if (this.mRightNavigationBarWindow != null) {
            this.mRightNavigationBarWindow.removeAllViews();
            this.mRightNavigationBarView = null;
        }
        buildNavBarContent();
    }

    void setNavBarVisibility(int visibility) {
        if (this.mNavigationBarWindow != null) {
            this.mNavigationBarWindow.setVisibility(visibility);
        }
        if (this.mLeftNavigationBarWindow != null) {
            this.mLeftNavigationBarWindow.setVisibility(visibility);
        }
        if (this.mRightNavigationBarWindow != null) {
            this.mRightNavigationBarWindow.setVisibility(visibility);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar
    public boolean hideKeyguard() {
        boolean result = super.hideKeyguard();
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.hideKeyguardButtons();
        }
        if (this.mLeftNavigationBarView != null) {
            this.mLeftNavigationBarView.hideKeyguardButtons();
        }
        if (this.mRightNavigationBarView != null) {
            this.mRightNavigationBarView.hideKeyguardButtons();
        }
        return result;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar
    public void showKeyguard() {
        super.showKeyguard();
        if (this.mNavigationBarView != null) {
            this.mNavigationBarView.showKeyguardButtons();
        }
        if (this.mLeftNavigationBarView != null) {
            this.mLeftNavigationBarView.showKeyguardButtons();
        }
        if (this.mRightNavigationBarView != null) {
            this.mRightNavigationBarView.showKeyguardButtons();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar
    protected void makeStatusBarView() {
        super.makeStatusBarView();
        this.mNotificationPanelBackground = getDefaultWallpaper();
        this.mScrimController.setScrimBehindDrawable(this.mNotificationPanelBackground);
        FragmentHostManager manager = FragmentHostManager.get(this.mStatusBarWindow);
        manager.addTagListener("CollapsedStatusBarFragment", new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.car.-$$Lambda$CarStatusBar$7jPzIv4lIi3N5zuDHkpus4wOtLo
            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewCreated(String str, Fragment fragment) {
                CarStatusBar.lambda$makeStatusBarView$0(str, fragment);
            }
        });
    }

    static /* synthetic */ void lambda$makeStatusBarView$0(String tag, Fragment fragment) {
    }

    private BatteryController createBatteryController() {
        this.mCarBatteryController = new CarBatteryController(this.mContext);
        this.mCarBatteryController.addBatteryViewHandler(this);
        return this.mCarBatteryController;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar
    protected void createNavigationBar() {
        this.mShowBottom = this.mContext.getResources().getBoolean(R.bool.config_enableBottomNavigationBar);
        this.mShowLeft = this.mContext.getResources().getBoolean(R.bool.config_enableLeftNavigationBar);
        this.mShowRight = this.mContext.getResources().getBoolean(R.bool.config_enableRightNavigationBar);
        buildNavBarWindows();
        buildNavBarContent();
        attachNavBarWindows();
    }

    private void buildNavBarContent() {
        if (this.mShowBottom) {
            buildBottomBar(this.mDeviceIsProvisioned ? R.layout.car_navigation_bar : R.layout.car_navigation_bar_unprovisioned);
        }
        if (this.mShowLeft) {
            buildLeft(this.mDeviceIsProvisioned ? R.layout.car_left_navigation_bar : R.layout.car_left_navigation_bar_unprovisioned);
        }
        if (this.mShowRight) {
            buildRight(this.mDeviceIsProvisioned ? R.layout.car_right_navigation_bar : R.layout.car_right_navigation_bar_unprovisioned);
        }
    }

    private void buildNavBarWindows() {
        if (this.mShowBottom) {
            this.mNavigationBarWindow = (ViewGroup) View.inflate(this.mContext, R.layout.navigation_bar_window, null);
        }
        if (this.mShowLeft) {
            this.mLeftNavigationBarWindow = (ViewGroup) View.inflate(this.mContext, R.layout.navigation_bar_window, null);
        }
        if (this.mShowRight) {
            this.mRightNavigationBarWindow = (ViewGroup) View.inflate(this.mContext, R.layout.navigation_bar_window, null);
        }
    }

    private void attachNavBarWindows() {
        if (this.mShowBottom) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1, 2019, 8650792, -3);
            lp.setTitle("CarNavigationBar");
            lp.windowAnimations = 0;
            this.mWindowManager.addView(this.mNavigationBarWindow, lp);
        }
        if (this.mShowLeft) {
            int width = this.mContext.getResources().getDimensionPixelSize(R.dimen.car_left_navigation_bar_width);
            WindowManager.LayoutParams leftlp = new WindowManager.LayoutParams(width, -1, 2024, 8650792, -3);
            leftlp.setTitle("LeftCarNavigationBar");
            leftlp.windowAnimations = 0;
            leftlp.privateFlags |= 4194304;
            leftlp.gravity = 3;
            this.mWindowManager.addView(this.mLeftNavigationBarWindow, leftlp);
        }
        if (this.mShowRight) {
            int width2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.car_right_navigation_bar_width);
            WindowManager.LayoutParams rightlp = new WindowManager.LayoutParams(width2, -1, 2024, 8650792, -3);
            rightlp.setTitle("RightCarNavigationBar");
            rightlp.windowAnimations = 0;
            rightlp.privateFlags |= 4194304;
            rightlp.gravity = 5;
            this.mWindowManager.addView(this.mRightNavigationBarWindow, rightlp);
        }
    }

    private void buildBottomBar(int layout) {
        View.inflate(this.mContext, layout, this.mNavigationBarWindow);
        this.mNavigationBarView = (CarNavigationBarView) this.mNavigationBarWindow.getChildAt(0);
        if (this.mNavigationBarView == null) {
            Log.e("CarStatusBar", "CarStatusBar failed inflate for R.layout.car_navigation_bar");
            throw new RuntimeException("Unable to build botom nav bar due to missing layout");
        }
        this.mNavigationBarView.setStatusBar(this);
    }

    private void buildLeft(int layout) {
        View.inflate(this.mContext, layout, this.mLeftNavigationBarWindow);
        this.mLeftNavigationBarView = (CarNavigationBarView) this.mLeftNavigationBarWindow.getChildAt(0);
        if (this.mLeftNavigationBarView == null) {
            Log.e("CarStatusBar", "CarStatusBar failed inflate for R.layout.car_navigation_bar");
            throw new RuntimeException("Unable to build left nav bar due to missing layout");
        }
        this.mLeftNavigationBarView.setStatusBar(this);
    }

    private void buildRight(int layout) {
        View.inflate(this.mContext, layout, this.mRightNavigationBarWindow);
        this.mRightNavigationBarView = (CarNavigationBarView) this.mRightNavigationBarWindow.getChildAt(0);
        if (this.mRightNavigationBarView == null) {
            Log.e("CarStatusBar", "CarStatusBar failed inflate for R.layout.car_navigation_bar");
            throw new RuntimeException("Unable to build right nav bar due to missing layout");
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar, com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        synchronized (this.mQueueLock) {
            pw.println("  mStackScroller: " + viewInfo(this.mStackScroller));
            pw.println("  mStackScroller: " + viewInfo(this.mStackScroller) + " scroll " + this.mStackScroller.getScrollX() + "," + this.mStackScroller.getScrollY());
        }
        pw.print("  mTaskStackListener=");
        pw.println(this.mTaskStackListener);
        pw.print("  mCarFacetButtonController=");
        pw.println(this.mCarFacetButtonController);
        pw.print("  mFullscreenUserSwitcher=");
        pw.println(this.mFullscreenUserSwitcher);
        pw.print("  mCarBatteryController=");
        pw.println(this.mCarBatteryController);
        pw.print("  mConnectedDeviceSignalController=");
        pw.println(this.mConnectedDeviceSignalController);
        pw.print("  mNavigationBarView=");
        pw.println(this.mNavigationBarView);
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

    @Override // com.android.systemui.statusbar.phone.StatusBar
    protected View.OnTouchListener getStatusBarWindowTouchListener() {
        return null;
    }

    @Override // com.android.systemui.statusbar.car.CarBatteryController.BatteryViewHandler
    public void showBatteryView() {
        if (Log.isLoggable("CarStatusBar", 3)) {
            Log.d("CarStatusBar", "showBatteryView()");
        }
    }

    @Override // com.android.systemui.statusbar.car.CarBatteryController.BatteryViewHandler
    public void hideBatteryView() {
        if (Log.isLoggable("CarStatusBar", 3)) {
            Log.d("CarStatusBar", "hideBatteryView()");
        }
    }

    private class TaskStackListenerImpl extends SysUiTaskStackChangeListener {
        private TaskStackListenerImpl() {
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChanged() {
            try {
                CarStatusBar.this.mCarFacetButtonController.taskChanged(ActivityManager.getService().getAllStackInfos());
            } catch (Exception e) {
                Log.e("CarStatusBar", "Getting StackInfo from activity manager failed", e);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar
    protected void createUserSwitcher() {
        UserSwitcherController userSwitcherController = (UserSwitcherController) Dependency.get(UserSwitcherController.class);
        if (userSwitcherController.useFullscreenUserSwitcher()) {
            this.mFullscreenUserSwitcher = new FullscreenUserSwitcher(this, (ViewStub) this.mStatusBarWindow.findViewById(R.id.fullscreen_user_switcher_stub), this.mContext);
        } else {
            super.createUserSwitcher();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar, com.android.systemui.statusbar.NotificationPresenter
    public void onUserSwitched(int newUserId) {
        super.onUserSwitched(newUserId);
        if (this.mFullscreenUserSwitcher != null) {
            this.mFullscreenUserSwitcher.onUserSwitched(newUserId);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar
    public void updateKeyguardState(boolean goingToFullShade, boolean fromShadeLocked) {
        super.updateKeyguardState(goingToFullShade, fromShadeLocked);
        if (this.mFullscreenUserSwitcher != null) {
            if (this.mState == 3) {
                this.mFullscreenUserSwitcher.show();
            } else {
                this.mFullscreenUserSwitcher.hide();
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar, com.android.systemui.statusbar.NotificationPresenter
    public void updateMediaMetaData(boolean metaDataChanged, boolean allowEnterAnimation) {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar, com.android.systemui.statusbar.CommandQueue.Callbacks
    public void animateExpandNotificationsPanel() {
        this.mHeadsUpManager.releaseAllImmediately();
        super.animateExpandNotificationsPanel();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBar, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        super.onDensityOrFontScaleChanged();
        this.mNotificationPanelBackground = getDefaultWallpaper();
        this.mScrimController.setScrimBehindDrawable(this.mNotificationPanelBackground);
    }

    private Drawable getDefaultWallpaper() {
        return this.mContext.getDrawable(android.R.drawable.btn_star_label_background);
    }
}
