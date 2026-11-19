package com.android.systemui.qs;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.CarrierText;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.MultiUserSwitch;
import com.android.systemui.statusbar.phone.SettingsButton;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.tuner.TunerService;
import com.mediatek.systemui.ext.ISystemUIStatusBarExt;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;

/* JADX INFO: loaded from: classes.dex */
public class QSFooterImpl extends FrameLayout implements View.OnClickListener, QSFooter, NetworkController.EmergencyListener, NetworkController.SignalCallback, UserInfoController.OnUserInfoChangedListener {
    private View mActionsContainer;
    private ActivityStarter mActivityStarter;
    private CarrierText mCarrierText;
    private final int mColorForeground;
    private View mDivider;
    private View mDragHandle;
    protected View mEdit;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpanded;
    private float mExpansionAmount;
    protected TouchAnimator mFooterAnimator;
    private final CellSignalState mInfo;
    private boolean mListening;
    private View mMobileGroup;
    private ImageView mMobileRoaming;
    private ImageView mMobileSignal;
    private ImageView mMultiUserAvatar;
    protected MultiUserSwitch mMultiUserSwitch;
    private PageIndicator mPageIndicator;
    private boolean mQsDisabled;
    private QSPanel mQsPanel;
    private SettingsButton mSettingsButton;
    private TouchAnimator mSettingsCogAnimator;
    protected View mSettingsContainer;
    private boolean mShowEmergencyCallsOnly;
    private ISystemUIStatusBarExt mStatusBarExt;
    private UserInfoController mUserInfoController;

    public QSFooterImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInfo = new CellSignalState();
        this.mColorForeground = Utils.getColorAttr(context, R.attr.colorForeground);
        this.mStatusBarExt = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext).makeSystemUIStatusBar(this.mContext);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDivider = findViewById(com.android.systemui.R.id.qs_footer_divider);
        this.mEdit = findViewById(R.id.edit);
        this.mEdit.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$3QBg0cgvu2IRpUDq3RvpL257x8c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$BPGtDaa2eU-tTCTVDpjGrKOXYOs
                    @Override // java.lang.Runnable
                    public final void run() {
                        qSFooterImpl.mQsPanel.showEdit(view);
                    }
                });
            }
        });
        this.mPageIndicator = (PageIndicator) findViewById(com.android.systemui.R.id.footer_page_indicator);
        this.mSettingsButton = (SettingsButton) findViewById(com.android.systemui.R.id.settings_button);
        this.mSettingsContainer = findViewById(com.android.systemui.R.id.settings_button_container);
        this.mSettingsButton.setOnClickListener(this);
        this.mMobileGroup = findViewById(com.android.systemui.R.id.mobile_combo);
        this.mMobileSignal = (ImageView) findViewById(com.android.systemui.R.id.mobile_signal);
        this.mMobileRoaming = (ImageView) findViewById(com.android.systemui.R.id.mobile_roaming);
        this.mCarrierText = (CarrierText) findViewById(com.android.systemui.R.id.qs_carrier_text);
        this.mCarrierText.setDisplayFlags(3);
        this.mMultiUserSwitch = (MultiUserSwitch) findViewById(com.android.systemui.R.id.multi_user_switch);
        this.mMultiUserAvatar = (ImageView) this.mMultiUserSwitch.findViewById(com.android.systemui.R.id.multi_user_avatar);
        this.mDragHandle = findViewById(com.android.systemui.R.id.qs_drag_handle_view);
        this.mActionsContainer = findViewById(com.android.systemui.R.id.qs_footer_actions_container);
        ((RippleDrawable) this.mSettingsButton.getBackground()).setForceSoftware(true);
        updateResources();
        this.mUserInfoController = (UserInfoController) Dependency.get(UserInfoController.class);
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$GSAG9gEF755NpvH4khVvAa75uPs
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.updateAnimator(i3 - i);
            }
        });
        setImportantForAccessibility(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAnimator(int width) {
        int numTiles = QuickQSPanel.getNumQuickTiles(this.mContext);
        int size = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_quick_tile_size) - this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_quick_tile_padding);
        int remaining = (width - (numTiles * size)) / (numTiles - 1);
        int defSpace = this.mContext.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.default_gear_space);
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        View view = this.mSettingsContainer;
        float[] fArr = new float[2];
        fArr[0] = isLayoutRtl() ? remaining - defSpace : -(remaining - defSpace);
        fArr[1] = 0.0f;
        this.mSettingsCogAnimator = builder.addFloat(view, "translationX", fArr).addFloat(this.mSettingsButton, "rotation", -120.0f, 0.0f).build();
        setExpansion(this.mExpansionAmount);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateResources();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        updateResources();
    }

    private void updateResources() {
        updateFooterAnimator();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.mActionsContainer.getLayoutParams();
        params.width = this.mContext.getResources().getInteger(com.android.systemui.R.integer.qs_footer_actions_width);
        params.weight = this.mContext.getResources().getInteger(com.android.systemui.R.integer.qs_footer_actions_weight);
        this.mActionsContainer.setLayoutParams(params);
    }

    private void updateFooterAnimator() {
        this.mFooterAnimator = createFooterAnimator();
    }

    private TouchAnimator createFooterAnimator() {
        return new TouchAnimator.Builder().addFloat(this.mDivider, "alpha", 0.0f, 1.0f).addFloat(this.mCarrierText, "alpha", 0.0f, 0.0f, 1.0f).addFloat(this.mMobileGroup, "alpha", 0.0f, 1.0f).addFloat(this.mActionsContainer, "alpha", 0.0f, 1.0f).addFloat(this.mDragHandle, "alpha", 1.0f, 0.0f, 0.0f).addFloat(this.mPageIndicator, "alpha", 0.0f, 1.0f).setStartDelay(0.15f).build();
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setKeyguardShowing(boolean keyguardShowing) {
        setExpansion(this.mExpansionAmount);
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setExpandClickListener(View.OnClickListener onClickListener) {
        this.mExpandClickListener = onClickListener;
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setExpanded(boolean expanded) {
        if (this.mExpanded == expanded) {
            return;
        }
        this.mExpanded = expanded;
        updateEverything();
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setExpansion(float headerExpansionFraction) {
        this.mExpansionAmount = headerExpansionFraction;
        if (this.mSettingsCogAnimator != null) {
            this.mSettingsCogAnimator.setPosition(headerExpansionFraction);
        }
        if (this.mFooterAnimator != null) {
            this.mFooterAnimator.setPosition(headerExpansionFraction);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        setListening(false);
        super.onDetachedFromWindow();
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setListening(boolean listening) {
        if (listening == this.mListening) {
            return;
        }
        this.mListening = listening;
        updateListeners();
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (action == 262144 && this.mExpandClickListener != null) {
            this.mExpandClickListener.onClick(null);
            return true;
        }
        return super.performAccessibilityAction(action, arguments);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // com.android.systemui.qs.QSFooter
    public void disable(int state1, int state2, boolean animate) {
        boolean disabled = (state2 & 1) != 0;
        if (disabled == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = disabled;
        updateEverything();
    }

    public void updateEverything() {
        post(new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$FK1In3z-Y3ppRrcllMggnruYa_s
            @Override // java.lang.Runnable
            public final void run() {
                QSFooterImpl.lambda$updateEverything$3(this.f$0);
            }
        });
    }

    public static /* synthetic */ void lambda$updateEverything$3(QSFooterImpl qSFooterImpl) {
        qSFooterImpl.updateVisibilities();
        qSFooterImpl.setClickable(false);
    }

    private void updateVisibilities() {
        this.mSettingsContainer.setVisibility(this.mQsDisabled ? 8 : 0);
        this.mSettingsContainer.findViewById(com.android.systemui.R.id.tuner_icon).setVisibility(TunerService.isTunerEnabled(this.mContext) ? 0 : 4);
        boolean isDemo = UserManager.isDeviceInDemoMode(this.mContext);
        this.mMultiUserSwitch.setVisibility(showUserSwitcher(isDemo) ? 0 : 4);
        Object tag = this.mEdit.getTag();
        if (!(tag instanceof String) || !tag.equals("gone")) {
            this.mEdit.setVisibility((isDemo || !this.mExpanded) ? 4 : 0);
        }
        this.mSettingsButton.setVisibility((isDemo || !this.mExpanded) ? 4 : 0);
    }

    private boolean showUserSwitcher(boolean isDemo) {
        if (!this.mExpanded || isDemo || !UserManager.supportsMultipleUsers()) {
            return false;
        }
        UserManager userManager = UserManager.get(this.mContext);
        if (userManager.hasUserRestriction("no_user_switch")) {
            return false;
        }
        int switchableUserCount = 0;
        for (UserInfo user : userManager.getUsers(true)) {
            if (user.supportsSwitchToByUser() && (switchableUserCount = switchableUserCount + 1) > 1) {
                return true;
            }
        }
        return getResources().getBoolean(com.android.systemui.R.bool.qs_show_user_switcher_for_single_user);
    }

    private void updateListeners() {
        if (this.mListening) {
            this.mUserInfoController.addCallback(this);
            if (((NetworkController) Dependency.get(NetworkController.class)).hasVoiceCallingFeature()) {
                ((NetworkController) Dependency.get(NetworkController.class)).addEmergencyListener(this);
                ((NetworkController) Dependency.get(NetworkController.class)).addCallback((NetworkController.SignalCallback) this);
                return;
            }
            return;
        }
        this.mUserInfoController.removeCallback(this);
        ((NetworkController) Dependency.get(NetworkController.class)).removeEmergencyListener(this);
        ((NetworkController) Dependency.get(NetworkController.class)).removeCallback((NetworkController.SignalCallback) this);
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setQSPanel(QSPanel qsPanel) {
        this.mQsPanel = qsPanel;
        if (this.mQsPanel != null) {
            this.mMultiUserSwitch.setQsPanel(qsPanel);
            this.mQsPanel.setFooterPageIndicator(this.mPageIndicator);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (this.mExpanded && v == this.mSettingsButton) {
            if (!((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class)).isCurrentUserSetup()) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$ORlOcuwnOcEc1bdhJcTagEFJfI4
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSFooterImpl.lambda$onClick$4();
                    }
                });
                return;
            }
            MetricsLogger.action(this.mContext, this.mExpanded ? 406 : 490);
            if (this.mSettingsButton.isTunerClick()) {
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$QqFCwKmpQEaqoIsbaA3_odDeJWo
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSFooterImpl.lambda$onClick$6(this.f$0);
                    }
                });
            } else {
                startSettingsActivity();
            }
        }
    }

    static /* synthetic */ void lambda$onClick$4() {
    }

    public static /* synthetic */ void lambda$onClick$6(final QSFooterImpl qSFooterImpl) {
        if (TunerService.isTunerEnabled(qSFooterImpl.mContext)) {
            TunerService.showResetRequest(qSFooterImpl.mContext, new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QSFooterImpl$p6Eelc3uV5Rv_Va6Mn0QpjivHN4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.startSettingsActivity();
                }
            });
        } else {
            Toast.makeText(qSFooterImpl.getContext(), com.android.systemui.R.string.tuner_toast, 1).show();
            TunerService.setTunerEnabled(qSFooterImpl.mContext, true);
        }
        qSFooterImpl.startSettingsActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSettingsActivity() {
        this.mActivityStarter.startActivity(new Intent("android.settings.SETTINGS"), true);
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.EmergencyListener
    public void setEmergencyCallsOnly(boolean show) {
        boolean changed = show != this.mShowEmergencyCallsOnly;
        if (changed) {
            this.mShowEmergencyCallsOnly = show;
            if (this.mExpanded) {
                updateEverything();
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
    public void onUserInfoChanged(String name, Drawable picture, String userAccount) {
        if (picture != null && UserManager.get(this.mContext).isGuestUser(KeyguardUpdateMonitor.getCurrentUser()) && !(picture instanceof UserIconDrawable)) {
            picture = picture.getConstantState().newDrawable(this.mContext.getResources()).mutate();
            picture.setColorFilter(Utils.getColorAttr(this.mContext, R.attr.colorForeground), PorterDuff.Mode.SRC_IN);
        }
        this.mMultiUserAvatar.setImageDrawable(picture);
    }

    private void handleUpdateState() {
        this.mMobileGroup.setVisibility(this.mInfo.visible ? 0 : 8);
        if (this.mInfo.visible) {
            this.mMobileRoaming.setVisibility(this.mInfo.roaming ? 0 : 8);
            this.mMobileRoaming.setImageTintList(ColorStateList.valueOf(this.mColorForeground));
            SignalDrawable d = new SignalDrawable(this.mContext);
            d.setDarkIntensity(QuickStatusBarHeader.getColorIntensity(this.mColorForeground));
            this.mMobileSignal.setImageDrawable(d);
            int signalId = this.mStatusBarExt.getCommonSignalIconId(this.mInfo.mobileSignalIconId);
            this.mMobileSignal.setImageLevel(signalId);
            StringBuilder contentDescription = new StringBuilder();
            if (this.mInfo.contentDescription != null) {
                contentDescription.append(this.mInfo.contentDescription);
                contentDescription.append(", ");
            }
            if (this.mInfo.roaming) {
                contentDescription.append(this.mContext.getString(com.android.systemui.R.string.data_connection_roaming));
                contentDescription.append(", ");
            }
            if (TextUtils.equals(this.mInfo.typeContentDescription, this.mContext.getString(com.android.systemui.R.string.data_connection_no_internet)) || TextUtils.equals(this.mInfo.typeContentDescription, this.mContext.getString(com.android.systemui.R.string.cell_data_off_content_description))) {
                contentDescription.append(this.mInfo.typeContentDescription);
            }
            this.mMobileSignal.setContentDescription(contentDescription);
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataIndicators(NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, int statusType, int networkType, int volteIcon, int qsType, boolean activityIn, boolean activityOut, String typeContentDescription, String description, boolean isWide, int subId, boolean roaming, boolean isDefaultData) {
        this.mInfo.visible = statusIcon.visible;
        this.mInfo.mobileSignalIconId = statusIcon.icon;
        this.mInfo.contentDescription = statusIcon.contentDescription;
        this.mInfo.typeContentDescription = typeContentDescription;
        this.mInfo.roaming = roaming;
        handleUpdateState();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setNoSims(boolean hasNoSims, boolean simDetected) {
        if (hasNoSims) {
            this.mInfo.visible = false;
        }
        handleUpdateState();
    }

    private final class CellSignalState {
        public String contentDescription;
        int mobileSignalIconId;
        boolean roaming;
        String typeContentDescription;
        boolean visible;

        private CellSignalState() {
        }
    }
}
