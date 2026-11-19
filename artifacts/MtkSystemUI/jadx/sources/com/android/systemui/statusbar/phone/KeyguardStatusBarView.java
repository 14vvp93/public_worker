package com.android.systemui.statusbar.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcher;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardStatusBarView extends RelativeLayout implements BatteryController.BatteryStateChangeCallback, ConfigurationController.ConfigurationListener, UserInfoController.OnUserInfoChangedListener {
    private boolean mBatteryCharging;
    private BatteryController mBatteryController;
    private boolean mBatteryListening;
    private TextView mCarrierLabel;
    private int mCutoutSideNudge;
    private View mCutoutSpace;
    private StatusBarIconController.TintedIconManager mIconManager;
    private KeyguardUserSwitcher mKeyguardUserSwitcher;
    private boolean mKeyguardUserSwitcherShowing;
    private int mLayoutState;
    private ImageView mMultiUserAvatar;
    private MultiUserSwitch mMultiUserSwitch;
    private ViewGroup mStatusIconArea;
    private int mSystemIconsBaseMargin;
    private View mSystemIconsContainer;
    private int mSystemIconsSwitcherHiddenExpandedMargin;
    private UserSwitcherController mUserSwitcherController;

    public KeyguardStatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLayoutState = 0;
        this.mCutoutSideNudge = 0;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mSystemIconsContainer = findViewById(R.id.system_icons_container);
        this.mMultiUserSwitch = (MultiUserSwitch) findViewById(R.id.multi_user_switch);
        this.mMultiUserAvatar = (ImageView) findViewById(R.id.multi_user_avatar);
        this.mCarrierLabel = (TextView) findViewById(R.id.keyguard_carrier_text);
        this.mCutoutSpace = findViewById(R.id.cutout_space_view);
        this.mStatusIconArea = (ViewGroup) findViewById(R.id.status_icon_area);
        loadDimens();
        updateUserSwitcher();
        this.mBatteryController = (BatteryController) Dependency.get(BatteryController.class);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mMultiUserAvatar.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.multi_user_avatar_keyguard_size);
        lp.height = dimensionPixelSize;
        lp.width = dimensionPixelSize;
        this.mMultiUserAvatar.setLayoutParams(lp);
        ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) this.mMultiUserSwitch.getLayoutParams();
        lp2.width = getResources().getDimensionPixelSize(R.dimen.multi_user_switch_width_keyguard);
        lp2.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.multi_user_switch_keyguard_margin));
        this.mMultiUserSwitch.setLayoutParams(lp2);
        ViewGroup.MarginLayoutParams lp3 = (ViewGroup.MarginLayoutParams) this.mSystemIconsContainer.getLayoutParams();
        lp3.setMarginStart(getResources().getDimensionPixelSize(R.dimen.system_icons_super_container_margin_start));
        this.mSystemIconsContainer.setLayoutParams(lp3);
        this.mSystemIconsContainer.setPaddingRelative(this.mSystemIconsContainer.getPaddingStart(), this.mSystemIconsContainer.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.system_icons_keyguard_padding_end), this.mSystemIconsContainer.getPaddingBottom());
        this.mCarrierLabel.setTextSize(0, getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_05));
        ViewGroup.MarginLayoutParams lp4 = (ViewGroup.MarginLayoutParams) this.mCarrierLabel.getLayoutParams();
        lp4.setMarginStart(getResources().getDimensionPixelSize(R.dimen.keyguard_carrier_text_margin));
        this.mCarrierLabel.setLayoutParams(lp4);
        ViewGroup.MarginLayoutParams lp5 = (ViewGroup.MarginLayoutParams) getLayoutParams();
        lp5.height = getResources().getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard);
        setLayoutParams(lp5);
    }

    private void loadDimens() {
        Resources res = getResources();
        this.mSystemIconsSwitcherHiddenExpandedMargin = res.getDimensionPixelSize(R.dimen.system_icons_switcher_hidden_expanded_margin);
        this.mSystemIconsBaseMargin = res.getDimensionPixelSize(R.dimen.system_icons_super_container_avatarless_margin_end);
        this.mCutoutSideNudge = getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
    }

    private void updateVisibilities() {
        if (this.mMultiUserSwitch.getParent() != this.mStatusIconArea && !this.mKeyguardUserSwitcherShowing) {
            if (this.mMultiUserSwitch.getParent() != null) {
                getOverlay().remove(this.mMultiUserSwitch);
            }
            this.mStatusIconArea.addView(this.mMultiUserSwitch, 0);
        } else if (this.mMultiUserSwitch.getParent() == this.mStatusIconArea && this.mKeyguardUserSwitcherShowing) {
            this.mStatusIconArea.removeView(this.mMultiUserSwitch);
        }
        if (this.mKeyguardUserSwitcher == null) {
            if (this.mUserSwitcherController != null && this.mUserSwitcherController.getSwitchableUserCount() > 1) {
                this.mMultiUserSwitch.setVisibility(0);
            } else {
                this.mMultiUserSwitch.setVisibility(8);
            }
        }
        ContentResolver cr = this.mContext.getContentResolver();
        String pin = Settings.System.getString(cr, "lockscreen_unlock_pin");
        if (pin != null && !pin.isEmpty()) {
            this.mStatusIconArea.setVisibility(4);
        }
    }

    private void updateSystemIconsLayoutParams() {
        int baseMarginEnd;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mSystemIconsContainer.getLayoutParams();
        if (this.mMultiUserSwitch.getVisibility() == 8) {
            baseMarginEnd = this.mSystemIconsBaseMargin;
        } else {
            baseMarginEnd = 0;
        }
        int marginEnd = this.mKeyguardUserSwitcherShowing ? this.mSystemIconsSwitcherHiddenExpandedMargin : baseMarginEnd;
        if (marginEnd != lp.getMarginEnd()) {
            lp.setMarginEnd(marginEnd);
            this.mSystemIconsContainer.setLayoutParams(lp);
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        this.mLayoutState = 0;
        if (updateLayoutConsideringCutout()) {
            requestLayout();
        }
        return super.onApplyWindowInsets(insets);
    }

    private boolean updateLayoutConsideringCutout() {
        DisplayCutout dc = getRootWindowInsets().getDisplayCutout();
        Pair<Integer, Integer> cornerCutoutMargins = PhoneStatusBarView.cornerCutoutMargins(dc, getDisplay());
        updateCornerCutoutPadding(cornerCutoutMargins);
        if (dc == null || cornerCutoutMargins != null) {
            return updateLayoutParamsNoCutout();
        }
        return updateLayoutParamsForCutout(dc);
    }

    private void updateCornerCutoutPadding(Pair<Integer, Integer> cornerCutoutMargins) {
        if (cornerCutoutMargins != null) {
            setPadding(((Integer) cornerCutoutMargins.first).intValue(), 0, ((Integer) cornerCutoutMargins.second).intValue(), 0);
        } else {
            setPadding(0, 0, 0, 0);
        }
    }

    private boolean updateLayoutParamsNoCutout() {
        if (this.mLayoutState == 2) {
            return false;
        }
        this.mLayoutState = 2;
        if (this.mCutoutSpace != null) {
            this.mCutoutSpace.setVisibility(8);
        }
        ((RelativeLayout.LayoutParams) this.mCarrierLabel.getLayoutParams()).addRule(16, R.id.status_icon_area);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.mStatusIconArea.getLayoutParams();
        lp.removeRule(1);
        lp.width = -2;
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) this.mSystemIconsContainer.getLayoutParams();
        llp.setMarginStart(getResources().getDimensionPixelSize(R.dimen.system_icons_super_container_margin_start));
        return true;
    }

    private boolean updateLayoutParamsForCutout(DisplayCutout dc) {
        if (this.mLayoutState == 1) {
            return false;
        }
        this.mLayoutState = 1;
        if (this.mCutoutSpace == null) {
            updateLayoutParamsNoCutout();
        }
        Rect bounds = new Rect();
        ScreenDecorations.DisplayCutoutView.boundsFromDirection(dc, 48, bounds);
        this.mCutoutSpace.setVisibility(0);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.mCutoutSpace.getLayoutParams();
        bounds.left += this.mCutoutSideNudge;
        bounds.right -= this.mCutoutSideNudge;
        lp.width = bounds.width();
        lp.height = bounds.height();
        lp.addRule(13);
        ((RelativeLayout.LayoutParams) this.mCarrierLabel.getLayoutParams()).addRule(16, R.id.cutout_space_view);
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) this.mStatusIconArea.getLayoutParams();
        lp2.addRule(1, R.id.cutout_space_view);
        lp2.width = -1;
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) this.mSystemIconsContainer.getLayoutParams();
        llp.setMarginStart(0);
        return true;
    }

    public void setListening(boolean listening) {
        if (listening == this.mBatteryListening) {
            return;
        }
        this.mBatteryListening = listening;
        if (this.mBatteryListening) {
            this.mBatteryController.addCallback(this);
        } else {
            this.mBatteryController.removeCallback(this);
        }
    }

    private void updateUserSwitcher() {
        boolean keyguardSwitcherAvailable = this.mKeyguardUserSwitcher != null;
        this.mMultiUserSwitch.setClickable(keyguardSwitcherAvailable);
        this.mMultiUserSwitch.setFocusable(keyguardSwitcherAvailable);
        this.mMultiUserSwitch.setKeyguardMode(keyguardSwitcherAvailable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        UserInfoController userInfoController = (UserInfoController) Dependency.get(UserInfoController.class);
        userInfoController.addCallback(this);
        this.mUserSwitcherController = (UserSwitcherController) Dependency.get(UserSwitcherController.class);
        this.mMultiUserSwitch.setUserSwitcherController(this.mUserSwitcherController);
        userInfoController.reloadUserInfo();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        this.mIconManager = new StatusBarIconController.TintedIconManager((ViewGroup) findViewById(R.id.statusIcons));
        ((StatusBarIconController) Dependency.get(StatusBarIconController.class)).addIconGroup(this.mIconManager);
        onThemeChanged();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((UserInfoController) Dependency.get(UserInfoController.class)).removeCallback(this);
        ((StatusBarIconController) Dependency.get(StatusBarIconController.class)).removeIconGroup(this.mIconManager);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
    public void onUserInfoChanged(String name, Drawable picture, String userAccount) {
        this.mMultiUserAvatar.setImageDrawable(picture);
    }

    public void setQSPanel(QSPanel qsp) {
        this.mMultiUserSwitch.setQsPanel(qsp);
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
        if (this.mBatteryCharging != charging) {
            this.mBatteryCharging = charging;
            updateVisibilities();
        }
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onPowerSaveChanged(boolean isPowerSave) {
    }

    public void setKeyguardUserSwitcher(KeyguardUserSwitcher keyguardUserSwitcher) {
        this.mKeyguardUserSwitcher = keyguardUserSwitcher;
        this.mMultiUserSwitch.setKeyguardUserSwitcher(keyguardUserSwitcher);
        updateUserSwitcher();
    }

    public void setKeyguardUserSwitcherShowing(boolean showing, boolean animate) {
        this.mKeyguardUserSwitcherShowing = showing;
        if (animate) {
            animateNextLayoutChange();
        }
        updateVisibilities();
        updateLayoutConsideringCutout();
        updateSystemIconsLayoutParams();
    }

    private void animateNextLayoutChange() {
        int systemIconsCurrentX = this.mSystemIconsContainer.getLeft();
        boolean userSwitcherVisible = this.mMultiUserSwitch.getParent() == this.mStatusIconArea;
        getViewTreeObserver().addOnPreDrawListener(new AnonymousClass1(userSwitcherVisible, systemIconsCurrentX));
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.KeyguardStatusBarView$1, reason: invalid class name */
    class AnonymousClass1 implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ int val$systemIconsCurrentX;
        final /* synthetic */ boolean val$userSwitcherVisible;

        AnonymousClass1(boolean z, int i) {
            this.val$userSwitcherVisible = z;
            this.val$systemIconsCurrentX = i;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            KeyguardStatusBarView.this.getViewTreeObserver().removeOnPreDrawListener(this);
            boolean userSwitcherHiding = this.val$userSwitcherVisible && KeyguardStatusBarView.this.mMultiUserSwitch.getParent() != KeyguardStatusBarView.this.mStatusIconArea;
            KeyguardStatusBarView.this.mSystemIconsContainer.setX(this.val$systemIconsCurrentX);
            KeyguardStatusBarView.this.mSystemIconsContainer.animate().translationX(0.0f).setDuration(400L).setStartDelay(userSwitcherHiding ? 300L : 0L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).start();
            if (userSwitcherHiding) {
                KeyguardStatusBarView.this.getOverlay().add(KeyguardStatusBarView.this.mMultiUserSwitch);
                KeyguardStatusBarView.this.mMultiUserSwitch.animate().alpha(0.0f).setDuration(300L).setStartDelay(0L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardStatusBarView$1$DyabYtIeJMptnepd5jqXSnZ7UZ0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardStatusBarView.AnonymousClass1.lambda$onPreDraw$0(this.f$0);
                    }
                }).start();
            } else {
                KeyguardStatusBarView.this.mMultiUserSwitch.setAlpha(0.0f);
                KeyguardStatusBarView.this.mMultiUserSwitch.animate().alpha(1.0f).setDuration(300L).setStartDelay(200L).setInterpolator(Interpolators.ALPHA_IN);
            }
            return true;
        }

        public static /* synthetic */ void lambda$onPreDraw$0(AnonymousClass1 anonymousClass1) {
            KeyguardStatusBarView.this.mMultiUserSwitch.setAlpha(1.0f);
            KeyguardStatusBarView.this.getOverlay().remove(KeyguardStatusBarView.this.mMultiUserSwitch);
        }
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != 0) {
            this.mSystemIconsContainer.animate().cancel();
            this.mSystemIconsContainer.setTranslationX(0.0f);
            this.mMultiUserSwitch.animate().cancel();
            this.mMultiUserSwitch.setAlpha(1.0f);
            return;
        }
        updateVisibilities();
        updateSystemIconsLayoutParams();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void onThemeChanged() {
        int i;
        int textColor = Utils.getColorAttr(this.mContext, R.attr.wallpaperTextColor);
        Context context = this.mContext;
        if (Color.luminance(textColor) < 0.5d) {
            i = R.color.dark_mode_icon_color_single_tone;
        } else {
            i = R.color.light_mode_icon_color_single_tone;
        }
        int iconColor = Utils.getDefaultColor(context, i);
        float intensity = textColor == -1 ? 0.0f : 1.0f;
        this.mCarrierLabel.setTextColor(iconColor);
        this.mIconManager.setTint(iconColor);
        Rect tintArea = new Rect(0, 0, 0, 0);
        applyDarkness(R.id.clock, tintArea, intensity, iconColor);
        ((UserInfoControllerImpl) Dependency.get(UserInfoController.class)).onDensityOrFontScaleChanged();
    }

    private void applyDarkness(int id, Rect tintArea, float intensity, int color) {
        KeyEvent.Callback callbackFindViewById = findViewById(id);
        if (callbackFindViewById instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) callbackFindViewById).onDarkChanged(tintArea, intensity, color);
        }
    }
}
