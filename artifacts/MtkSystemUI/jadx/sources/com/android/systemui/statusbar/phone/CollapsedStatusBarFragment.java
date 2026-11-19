package com.android.systemui.statusbar.phone;

import android.app.Fragment;
import android.carsource.McuManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.EncryptionHelper;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.util.Utils;

/* JADX INFO: loaded from: classes.dex */
public class CollapsedStatusBarFragment extends Fragment implements CommandQueue.Callbacks {
    private View mClockView;
    private StatusBarIconController.DarkIconManager mDarkIconManager;
    private int mDisabled1;
    private View mFyScreen;
    private View mKeyBack;
    private View mKeyHome;
    private View mKeyRecent;
    private KeyguardMonitor mKeyguardMonitor;
    private NetworkController mNetworkController;
    private View mNotificationIconAreaInner;
    private View mOperatorNameFrame;
    private View mScreenOff;
    private PhoneStatusBarView mStatusBar;
    private StatusBar mStatusBarComponent;
    private LinearLayout mSystemIconArea;
    private View mVolumeDown;
    private View mVolumeMute;
    private View mVolumeUp;
    private NetworkController.SignalCallback mSignalCallback = new NetworkController.SignalCallback() { // from class: com.android.systemui.statusbar.phone.CollapsedStatusBarFragment.1
        @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
        public void setIsAirplaneMode(NetworkController.IconState icon) {
            CollapsedStatusBarFragment.this.mStatusBarComponent.recomputeDisableFlags(true);
        }
    };
    private final BroadcastReceiver mBackcarBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CollapsedStatusBarFragment.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("hcn.wireless.backcar.action.REVSTATUS".equals(intent.getAction())) {
                boolean isBackcar = intent.getBooleanExtra("REVSTATUS", false);
                CollapsedStatusBarFragment.this.updateBackcarUI(isBackcar);
            }
        }
    };

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mKeyguardMonitor = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);
        this.mNetworkController = (NetworkController) Dependency.get(NetworkController.class);
        this.mStatusBarComponent = (StatusBar) SysUiServiceProvider.getComponent(getContext(), StatusBar.class);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Settings.System.getInt(getContext().getContentResolver(), "key_change_sys_bar", 0) == 1) {
            return inflater.inflate(R.layout.status_bar_changed, container, false);
        }
        if (Utils.getSystemProperty("persist.sys.skin", "").toLowerCase().contains("dz23")) {
            return inflater.inflate(R.layout.dz23_status_bar, container, false);
        }
        if (Utils.getSystemProperty("persist.sys.skin", "").toLowerCase().contains("p38") || Utils.getSystemProperty("persist.sys.skin", "").toLowerCase().contains("p42")) {
            return inflater.inflate(R.layout.status_bar_top_return, container, false);
        }
        return inflater.inflate(R.layout.status_bar, container, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mStatusBar = (PhoneStatusBarView) view;
        if (savedInstanceState != null && savedInstanceState.containsKey("panel_state")) {
            this.mStatusBar.go(savedInstanceState.getInt("panel_state"));
        }
        this.mDarkIconManager = new StatusBarIconController.DarkIconManager((LinearLayout) view.findViewById(R.id.statusIcons));
        this.mDarkIconManager.setShouldLog(true);
        ((StatusBarIconController) Dependency.get(StatusBarIconController.class)).addIconGroup(this.mDarkIconManager);
        this.mSystemIconArea = (LinearLayout) this.mStatusBar.findViewById(R.id.system_icon_area);
        this.mClockView = this.mStatusBar.findViewById(R.id.clock);
        showSystemIconArea(false);
        showClock(false);
        initEmergencyCryptkeeperText();
        initOperatorName();
        this.mKeyHome = this.mStatusBar.findViewById(R.id.home);
        this.mKeyBack = this.mStatusBar.findViewById(R.id.back);
        this.mKeyRecent = this.mStatusBar.findViewById(R.id.recent_apps007);
        this.mScreenOff = this.mStatusBar.findViewById(R.id.screenoff);
        this.mFyScreen = this.mStatusBar.findViewById(R.id.off_screen);
        this.mVolumeUp = this.mStatusBar.findViewById(R.id.volume_up);
        this.mVolumeDown = this.mStatusBar.findViewById(R.id.volume_down);
        this.mVolumeMute = this.mStatusBar.findViewById(R.id.volume_mute);
        if (this.mFyScreen != null) {
            this.mFyScreen.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$CollapsedStatusBarFragment$s-2n9tkx7Kg480IN8is5yLgdYSQ
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view2) {
                    return CollapsedStatusBarFragment.lambda$onViewCreated$0(view2);
                }
            });
            this.mFyScreen.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.CollapsedStatusBarFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    McuManager.getsInstance().injectKeyEventTimeout(164, 50);
                }
            });
        }
        int vis = Settings.System.getInt(getContext().getContentResolver(), "key_add_buttons", 0) != 1 ? 8 : 0;
        if (this.mFyScreen != null) {
            this.mFyScreen.setVisibility(vis);
        }
        if (this.mVolumeUp != null) {
            this.mVolumeUp.setVisibility(vis);
        }
        if (this.mVolumeDown != null) {
            this.mVolumeDown.setVisibility(vis);
        }
        if (this.mVolumeMute != null) {
            this.mVolumeMute.setVisibility(vis);
        }
        if ("3".equals(SystemProperties.get("ro.vendor.sys.avm.type", "0"))) {
            registerBackcarBroadcast();
        }
    }

    static /* synthetic */ boolean lambda$onViewCreated$0(View v) {
        McuManager.getsInstance().injectKeyEventTimeout(336, 50);
        return true;
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("panel_state", this.mStatusBar.getState());
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        ((CommandQueue) SysUiServiceProvider.getComponent(getContext(), CommandQueue.class)).addCallbacks(this);
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        ((CommandQueue) SysUiServiceProvider.getComponent(getContext(), CommandQueue.class)).removeCallbacks(this);
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        ((StatusBarIconController) Dependency.get(StatusBarIconController.class)).removeIconGroup(this.mDarkIconManager);
        if (this.mNetworkController.hasEmergencyCryptKeeperText()) {
            this.mNetworkController.removeCallback(this.mSignalCallback);
        }
        if ("3".equals(SystemProperties.get("ro.vendor.sys.avm.type", "0"))) {
            unregisterBackcarBroadcast();
        }
    }

    public void initNotificationIconArea(NotificationIconAreaController notificationIconAreaController) {
        ViewGroup notificationIconArea = (ViewGroup) this.mStatusBar.findViewById(R.id.notification_icon_area);
        this.mNotificationIconAreaInner = notificationIconAreaController.getNotificationInnerAreaView();
        if (this.mNotificationIconAreaInner.getParent() != null) {
            ((ViewGroup) this.mNotificationIconAreaInner.getParent()).removeView(this.mNotificationIconAreaInner);
        }
        notificationIconArea.addView(this.mNotificationIconAreaInner);
        showNotificationIconArea(false);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void disable(int state1, int state2, boolean animate) {
        int state3 = adjustDisableFlags(state1);
        int old1 = this.mDisabled1;
        int diff1 = state3 ^ old1;
        this.mDisabled1 = state3;
        if ((diff1 & 1048576) != 0) {
            if ((1048576 & state3) != 0) {
                hideSystemIconArea(animate);
                hideOperatorName(animate);
                hideStatusBarKeys(animate);
            } else {
                showSystemIconArea(animate);
                showOperatorName(animate);
                showStatusBarKeys(animate);
            }
        }
        if ((diff1 & 131072) != 0) {
            if ((131072 & state3) != 0) {
                hideNotificationIconArea(animate);
            } else {
                showNotificationIconArea(animate);
            }
        }
        if ((diff1 & 8388608) != 0 || this.mClockView.getVisibility() != clockHiddenMode()) {
            if ((8388608 & state3) != 0) {
                hideClock(animate);
            } else {
                showClock(animate);
            }
        }
    }

    protected int adjustDisableFlags(int state) {
        if (!this.mStatusBarComponent.isLaunchTransitionFadingAway() && !this.mKeyguardMonitor.isKeyguardFadingAway() && shouldHideNotificationIcons()) {
            state = state | 131072 | 1048576 | 8388608;
        }
        if (this.mNetworkController != null && EncryptionHelper.IS_DATA_ENCRYPTED) {
            if (this.mNetworkController.hasEmergencyCryptKeeperText()) {
                state |= 131072;
            }
            if (!this.mNetworkController.isRadioOn()) {
                return state | 1048576;
            }
            return state;
        }
        return state;
    }

    private boolean shouldHideNotificationIcons() {
        return (!this.mStatusBar.isClosed() && this.mStatusBarComponent.hideStatusBarIconsWhenExpanded()) || this.mStatusBarComponent.hideStatusBarIconsForBouncer();
    }

    public void hideSystemIconArea(boolean animate) {
        animateHide(this.mSystemIconArea, animate);
    }

    public void showSystemIconArea(boolean animate) {
        animateShow(this.mSystemIconArea, animate);
    }

    public void hideStatusBarKeys(boolean animate) {
        if (this.mKeyHome != null) {
            animateHide(this.mKeyHome, animate);
            animateHide(this.mKeyBack, animate);
            animateHide(this.mKeyRecent, animate);
        }
        if (this.mScreenOff != null) {
            animateHide(this.mScreenOff, animate);
        }
    }

    public void showStatusBarKeys(boolean animate) {
        if (this.mKeyHome != null) {
            animateShow(this.mKeyHome, animate);
            animateShow(this.mKeyBack, animate);
            animateShow(this.mKeyRecent, animate);
        }
        if (this.mScreenOff != null) {
            animateShow(this.mScreenOff, animate);
        }
    }

    public void hideClock(boolean animate) {
        animateHiddenState(this.mClockView, clockHiddenMode(), animate);
    }

    public void showClock(boolean animate) {
        animateShow(this.mClockView, animate);
    }

    private int clockHiddenMode() {
        if (!this.mStatusBar.isClosed() && !this.mKeyguardMonitor.isShowing()) {
            return 4;
        }
        return 8;
    }

    public void hideNotificationIconArea(boolean animate) {
        animateHide(this.mNotificationIconAreaInner, animate);
    }

    public void showNotificationIconArea(boolean animate) {
        animateShow(this.mNotificationIconAreaInner, animate);
    }

    public void hideOperatorName(boolean animate) {
        if (this.mOperatorNameFrame != null) {
            animateHide(this.mOperatorNameFrame, animate);
        }
    }

    public void showOperatorName(boolean animate) {
        if (this.mOperatorNameFrame != null) {
            animateShow(this.mOperatorNameFrame, animate);
        }
    }

    private void animateHiddenState(final View v, final int state, boolean animate) {
        v.animate().cancel();
        if (!animate) {
            v.setAlpha(0.0f);
            v.setVisibility(state);
        } else {
            v.animate().alpha(0.0f).setDuration(160L).setStartDelay(0L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$CollapsedStatusBarFragment$g9PULYz-2Fx0vsMvLD8JDBqvrjY
                @Override // java.lang.Runnable
                public final void run() {
                    v.setVisibility(state);
                }
            });
        }
    }

    private void animateHide(View v, boolean animate) {
        animateHiddenState(v, 4, animate);
    }

    private void animateShow(View v, boolean animate) {
        v.animate().cancel();
        v.setVisibility(0);
        if (!animate) {
            v.setAlpha(1.0f);
            return;
        }
        v.animate().alpha(1.0f).setDuration(320L).setInterpolator(Interpolators.ALPHA_IN).setStartDelay(50L).withEndAction(null);
        if (this.mKeyguardMonitor.isKeyguardFadingAway()) {
            v.animate().setDuration(this.mKeyguardMonitor.getKeyguardFadingAwayDuration()).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setStartDelay(this.mKeyguardMonitor.getKeyguardFadingAwayDelay()).start();
        }
    }

    private void initEmergencyCryptkeeperText() {
        View emergencyViewStub = this.mStatusBar.findViewById(R.id.emergency_cryptkeeper_text);
        if (this.mNetworkController.hasEmergencyCryptKeeperText()) {
            if (emergencyViewStub != null) {
                ((ViewStub) emergencyViewStub).inflate();
            }
            this.mNetworkController.addCallback(this.mSignalCallback);
        } else if (emergencyViewStub != null) {
            ViewGroup parent = (ViewGroup) emergencyViewStub.getParent();
            parent.removeView(emergencyViewStub);
        }
    }

    private void initOperatorName() {
        if (getResources().getBoolean(R.bool.config_showOperatorNameInStatusBar)) {
            ViewStub stub = (ViewStub) this.mStatusBar.findViewById(R.id.operator_name);
            this.mOperatorNameFrame = stub.inflate();
        }
    }

    private void registerBackcarBroadcast() {
        Log.d("CollapsedStatusBarFragment", "registerBackcarBroadcast");
        IntentFilter filter = new IntentFilter();
        filter.addAction("hcn.wireless.backcar.action.REVSTATUS");
        getContext().registerReceiver(this.mBackcarBroadcastReceiver, filter);
    }

    private void unregisterBackcarBroadcast() {
        Log.d("CollapsedStatusBarFragment", "unregisterBackcarBroadcast");
        if (this.mBackcarBroadcastReceiver != null) {
            getContext().unregisterReceiver(this.mBackcarBroadcastReceiver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackcarUI(boolean isBackcar) {
        Log.d("CollapsedStatusBarFragment", "updateBackcarUI isBackcar = " + isBackcar);
        if (this.mStatusBar != null) {
            this.mStatusBar.setVisibility(isBackcar ? 8 : 0);
        }
    }
}
