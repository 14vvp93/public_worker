package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemProperties;
import android.telephony.SubscriptionInfo;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.IconLogger;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import com.mediatek.systemui.ext.ISystemUIStatusBarExt;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SignalClusterView extends LinearLayout implements DarkIconDispatcher.DarkReceiver, NetworkController.SignalCallback, SecurityController.SecurityControllerCallback, TunerService.Tunable {
    static final boolean DEBUG;
    private boolean mActivityEnabled;
    ImageView mAirplane;
    private String mAirplaneContentDescription;
    private int mAirplaneIconId;
    private boolean mBlockAirplane;
    private boolean mBlockEthernet;
    private boolean mBlockMobile;
    private boolean mBlockWifi;
    private float mDarkIntensity;
    private final int mEndPadding;
    private final int mEndPaddingNothingVisible;
    ImageView mEthernet;
    ImageView mEthernetDark;
    private String mEthernetDescription;
    ViewGroup mEthernetGroup;
    private int mEthernetIconId;
    private boolean mEthernetVisible;
    private boolean mForceBlockWifi;
    private final IconLogger mIconLogger;
    private final float mIconScaleFactor;
    private int mIconTint;
    private boolean mIsAirplaneMode;
    boolean mIsWfcEnable;
    private int mLastAirplaneIconId;
    private int mLastEthernetIconId;
    private int mLastVpnIconId;
    private int mLastWifiStrengthId;
    private final int mMobileDataIconStartPadding;
    LinearLayout mMobileSignalGroup;
    private final int mMobileSignalGroupEndPadding;
    private final NetworkController mNetworkController;
    private ArrayList<PhoneState> mPhoneStates;
    private final int mSecondaryTelephonyPadding;
    private final SecurityController mSecurityController;
    private ISystemUIStatusBarExt mStatusBarExt;
    private final Rect mTintArea;
    ImageView mVpn;
    private int mVpnIconId;
    private boolean mVpnVisible;
    ImageView mWifi;
    ImageView mWifiActivityIn;
    ImageView mWifiActivityOut;
    View mWifiAirplaneSpacer;
    ImageView mWifiDark;
    private String mWifiDescription;
    ViewGroup mWifiGroup;
    private boolean mWifiIn;
    private boolean mWifiOut;
    View mWifiSignalSpacer;
    private int mWifiStrengthId;
    private boolean mWifiVisible;

    static {
        DEBUG = Log.isLoggable("SignalClusterView", 3) || FeatureOptions.LOG_ENABLE;
    }

    public SignalClusterView(Context context) {
        this(context, null);
    }

    public SignalClusterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignalClusterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mVpnVisible = false;
        this.mVpnIconId = 0;
        this.mLastVpnIconId = -1;
        this.mEthernetVisible = false;
        this.mEthernetIconId = 0;
        this.mLastEthernetIconId = -1;
        this.mWifiVisible = false;
        this.mWifiStrengthId = 0;
        this.mLastWifiStrengthId = -1;
        this.mIsAirplaneMode = false;
        this.mAirplaneIconId = 0;
        this.mLastAirplaneIconId = -1;
        this.mPhoneStates = new ArrayList<>();
        this.mIconTint = -1;
        this.mTintArea = new Rect();
        this.mIconLogger = (IconLogger) Dependency.get(IconLogger.class);
        Resources res = getResources();
        this.mMobileSignalGroupEndPadding = res.getDimensionPixelSize(R.dimen.mobile_signal_group_end_padding);
        this.mMobileDataIconStartPadding = res.getDimensionPixelSize(R.dimen.mobile_data_icon_start_padding);
        this.mSecondaryTelephonyPadding = res.getDimensionPixelSize(R.dimen.secondary_telephony_padding);
        this.mEndPadding = res.getDimensionPixelSize(R.dimen.signal_cluster_battery_padding);
        this.mEndPaddingNothingVisible = res.getDimensionPixelSize(R.dimen.no_signal_cluster_battery_padding);
        TypedValue typedValue = new TypedValue();
        res.getValue(R.dimen.status_bar_icon_scale_factor, typedValue, true);
        this.mIconScaleFactor = typedValue.getFloat();
        this.mNetworkController = (NetworkController) Dependency.get(NetworkController.class);
        this.mSecurityController = (SecurityController) Dependency.get(SecurityController.class);
        addOnAttachStateChangeListener(new Utils.DisableStateTracker(0, 2));
        updateActivityEnabled();
        this.mStatusBarExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeSystemUIStatusBar(context);
        this.mIsWfcEnable = SystemProperties.get("persist.vendor.mtk_wfc_support").equals("1");
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if (!"icon_blacklist".equals(key)) {
            return;
        }
        ArraySet<String> blockList = StatusBarIconController.getIconBlacklist(newValue);
        boolean blockAirplane = blockList.contains("airplane");
        boolean blockMobile = blockList.contains("mobile");
        boolean blockWifi = blockList.contains("wifi");
        boolean blockEthernet = blockList.contains("ethernet");
        if (blockAirplane != this.mBlockAirplane || blockMobile != this.mBlockMobile || blockEthernet != this.mBlockEthernet || blockWifi != this.mBlockWifi) {
            this.mBlockAirplane = blockAirplane;
            this.mBlockMobile = blockMobile;
            this.mBlockEthernet = blockEthernet;
            this.mBlockWifi = blockWifi || this.mForceBlockWifi;
            this.mNetworkController.removeCallback((NetworkController.SignalCallback) this);
            this.mNetworkController.addCallback((NetworkController.SignalCallback) this);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mVpn = (ImageView) findViewById(R.id.vpn);
        this.mEthernetGroup = (ViewGroup) findViewById(R.id.ethernet_combo);
        this.mEthernet = (ImageView) findViewById(R.id.ethernet);
        this.mEthernetDark = (ImageView) findViewById(R.id.ethernet_dark);
        this.mWifiGroup = (ViewGroup) findViewById(R.id.wifi_combo);
        this.mWifi = (ImageView) findViewById(R.id.wifi_signal);
        this.mWifiDark = (ImageView) findViewById(R.id.wifi_signal_dark);
        this.mWifiActivityIn = (ImageView) findViewById(R.id.wifi_in);
        this.mWifiActivityOut = (ImageView) findViewById(R.id.wifi_out);
        this.mAirplane = (ImageView) findViewById(R.id.airplane);
        this.mWifiAirplaneSpacer = findViewById(R.id.wifi_airplane_spacer);
        this.mWifiSignalSpacer = findViewById(R.id.wifi_signal_spacer);
        this.mMobileSignalGroup = (LinearLayout) findViewById(R.id.mobile_signal_group);
        maybeScaleVpnAndNoSimsIcons();
    }

    private void maybeScaleVpnAndNoSimsIcons() {
        if (this.mIconScaleFactor == 1.0f) {
            return;
        }
        this.mVpn.setImageDrawable(new ScalingDrawableWrapper(this.mVpn.getDrawable(), this.mIconScaleFactor));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mVpnVisible = this.mSecurityController.isVpnEnabled();
        this.mVpnIconId = currentVpnIconId(this.mSecurityController.isVpnBranded());
        if (DEBUG) {
            Log.d("SignalClusterView", "onAttachedToWindow, mPhoneStates = " + this.mPhoneStates);
        }
        for (PhoneState state : this.mPhoneStates) {
            if (state.mMobileGroup.getParent() == null) {
                this.mMobileSignalGroup.addView(state.mMobileGroup);
            }
        }
        int endPadding = this.mMobileSignalGroup.getChildCount() > 0 ? this.mMobileSignalGroupEndPadding : 0;
        this.mMobileSignalGroup.setPaddingRelative(0, 0, endPadding, 0);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist");
        this.mStatusBarExt.addSignalClusterCustomizedView(this.mContext, this, indexOfChild(findViewById(R.id.mobile_signal_group)));
        apply();
        applyIconTint();
        if (DEBUG) {
            Log.d("SignalClusterView", "onAttachedToWindow, addCallback = " + this);
        }
        this.mNetworkController.addCallback((NetworkController.SignalCallback) this);
        this.mSecurityController.addCallback(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.mMobileSignalGroup.removeAllViews();
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        this.mSecurityController.removeCallback(this);
        if (DEBUG) {
            Log.d("SignalClusterView", "onDetachedFromWindow, removeCallback = " + this);
        }
        this.mNetworkController.removeCallback((NetworkController.SignalCallback) this);
        super.onDetachedFromWindow();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        applyIconTint();
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public void onStateChanged() {
        post(new Runnable() { // from class: com.android.systemui.statusbar.SignalClusterView.1
            @Override // java.lang.Runnable
            public void run() {
                SignalClusterView.this.mVpnVisible = SignalClusterView.this.mSecurityController.isVpnEnabled();
                SignalClusterView.this.mVpnIconId = SignalClusterView.this.currentVpnIconId(SignalClusterView.this.mSecurityController.isVpnBranded());
                SignalClusterView.this.apply();
            }
        });
    }

    private void updateActivityEnabled() {
        this.mActivityEnabled = this.mContext.getResources().getBoolean(R.bool.config_showActivity);
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setWifiIndicators(boolean enabled, NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, boolean activityIn, boolean activityOut, String description, boolean isTransient, String secondaryLabel) {
        boolean z = false;
        this.mWifiVisible = statusIcon.visible && !this.mBlockWifi;
        this.mWifiStrengthId = statusIcon.icon;
        this.mWifiDescription = statusIcon.contentDescription;
        this.mWifiIn = activityIn && this.mActivityEnabled && this.mWifiVisible;
        if (activityOut && this.mActivityEnabled && this.mWifiVisible) {
            z = true;
        }
        this.mWifiOut = z;
        apply();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataIndicators(NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, int statusType, int networkType, int volteIcon, int qsType, boolean activityIn, boolean activityOut, String typeContentDescription, String description, boolean isWide, int subId, boolean roaming, boolean isDefaultData) {
        PhoneState state = getState(subId);
        if (state == null) {
            return;
        }
        boolean z = false;
        state.mMobileVisible = statusIcon.visible && !this.mBlockMobile;
        state.mMobileStrengthId = statusIcon.icon;
        state.mMobileTypeId = statusType;
        state.mMobileDescription = statusIcon.contentDescription;
        state.mMobileTypeDescription = typeContentDescription;
        state.mNetworkIcon = networkType;
        state.mVolteIcon = volteIcon;
        state.mRoaming = roaming;
        state.mActivityIn = activityIn && this.mActivityEnabled;
        if (activityOut && this.mActivityEnabled) {
            z = true;
        }
        state.mActivityOut = z;
        state.mDataActivityIn = activityIn;
        state.mDataActivityOut = activityOut;
        apply();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setEthernetIndicators(NetworkController.IconState state) {
        this.mEthernetVisible = state.visible && !this.mBlockEthernet;
        this.mEthernetIconId = state.icon;
        this.mEthernetDescription = state.contentDescription;
        apply();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setNoSims(boolean show, boolean simDetected) {
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setSubs(List<SubscriptionInfo> subs) {
        if (DEBUG) {
            Log.d("SignalClusterView", "setSubs, subs = " + subs);
        }
        if (hasCorrectSubs(subs)) {
            return;
        }
        this.mPhoneStates.clear();
        if (this.mMobileSignalGroup != null) {
            this.mMobileSignalGroup.removeAllViews();
        }
        int n = subs.size();
        for (int i = 0; i < n; i++) {
            inflatePhoneState(subs.get(i).getSubscriptionId());
        }
        if (isAttachedToWindow()) {
            applyIconTint();
        }
    }

    private boolean hasCorrectSubs(List<SubscriptionInfo> subs) {
        int N = subs.size();
        if (N != this.mPhoneStates.size()) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            if (this.mPhoneStates.get(i).mSubId != subs.get(i).getSubscriptionId() || this.mStatusBarExt.checkIfSlotIdChanged(subs.get(i).getSubscriptionId(), subs.get(i).getSimSlotIndex())) {
                return false;
            }
        }
        return true;
    }

    private PhoneState getState(int subId) {
        for (PhoneState state : this.mPhoneStates) {
            if (state.mSubId == subId) {
                return state;
            }
        }
        Log.e("SignalClusterView", "Unexpected subscription " + subId);
        return null;
    }

    private PhoneState inflatePhoneState(int subId) {
        PhoneState state = new PhoneState(subId, this.mContext);
        if (this.mMobileSignalGroup != null) {
            this.mMobileSignalGroup.addView(state.mMobileGroup);
        }
        this.mPhoneStates.add(state);
        return state;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setIsAirplaneMode(NetworkController.IconState icon) {
        this.mIsAirplaneMode = icon.visible && !this.mBlockAirplane;
        this.mAirplaneIconId = icon.icon;
        this.mAirplaneContentDescription = icon.contentDescription;
        apply();
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataEnabled(boolean enabled) {
    }

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        if (this.mEthernetVisible && this.mEthernetGroup != null && this.mEthernetGroup.getContentDescription() != null) {
            event.getText().add(this.mEthernetGroup.getContentDescription());
        }
        if (this.mWifiVisible && this.mWifiGroup != null && this.mWifiGroup.getContentDescription() != null) {
            event.getText().add(this.mWifiGroup.getContentDescription());
        }
        for (PhoneState state : this.mPhoneStates) {
            state.populateAccessibilityEvent(event);
        }
        return super.dispatchPopulateAccessibilityEventInternal(event);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (this.mEthernet != null) {
            this.mEthernet.setImageDrawable(null);
            this.mEthernetDark.setImageDrawable(null);
            this.mLastEthernetIconId = -1;
        }
        if (this.mWifi != null) {
            this.mWifi.setImageDrawable(null);
            this.mWifiDark.setImageDrawable(null);
            this.mLastWifiStrengthId = -1;
        }
        for (PhoneState state : this.mPhoneStates) {
            if (state.mMobileType != null) {
                state.mMobileType.setImageDrawable(null);
                state.mLastMobileTypeId = -1;
            }
        }
        if (this.mAirplane != null) {
            this.mAirplane.setImageDrawable(null);
            this.mLastAirplaneIconId = -1;
        }
        apply();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void apply() {
        if (this.mWifiGroup == null) {
            return;
        }
        if (this.mVpnVisible) {
            if (this.mLastVpnIconId != this.mVpnIconId) {
                setIconForView(this.mVpn, this.mVpnIconId);
                this.mLastVpnIconId = this.mVpnIconId;
            }
            this.mIconLogger.onIconShown("vpn");
            this.mVpn.setVisibility(0);
        } else {
            this.mIconLogger.onIconHidden("vpn");
            this.mVpn.setVisibility(8);
        }
        boolean z = true;
        if (DEBUG) {
            Object[] objArr = new Object[1];
            objArr[0] = this.mVpnVisible ? "VISIBLE" : "GONE";
            Log.d("SignalClusterView", String.format("vpn: %s", objArr));
        }
        if (this.mEthernetVisible) {
            if (this.mLastEthernetIconId != this.mEthernetIconId) {
                setIconForView(this.mEthernet, this.mEthernetIconId);
                setIconForView(this.mEthernetDark, this.mEthernetIconId);
                this.mLastEthernetIconId = this.mEthernetIconId;
            }
            this.mEthernetGroup.setContentDescription(this.mEthernetDescription);
            this.mIconLogger.onIconShown("ethernet");
            this.mEthernetGroup.setVisibility(0);
        } else {
            this.mIconLogger.onIconHidden("ethernet");
            this.mEthernetGroup.setVisibility(8);
        }
        if (DEBUG) {
            Object[] objArr2 = new Object[1];
            objArr2[0] = this.mEthernetVisible ? "VISIBLE" : "GONE";
            Log.d("SignalClusterView", String.format("ethernet: %s", objArr2));
        }
        if (this.mWifiVisible) {
            if (this.mWifiStrengthId != this.mLastWifiStrengthId) {
                setIconForView(this.mWifi, this.mWifiStrengthId);
                setIconForView(this.mWifiDark, this.mWifiStrengthId);
                this.mLastWifiStrengthId = this.mWifiStrengthId;
            }
            this.mIconLogger.onIconShown("wifi");
            this.mWifiGroup.setContentDescription(this.mWifiDescription);
            this.mWifiGroup.setVisibility(0);
        } else {
            this.mIconLogger.onIconHidden("wifi");
            this.mWifiGroup.setVisibility(8);
        }
        if (DEBUG) {
            Object[] objArr3 = new Object[2];
            objArr3[0] = this.mWifiVisible ? "VISIBLE" : "GONE";
            objArr3[1] = Integer.valueOf(this.mWifiStrengthId);
            Log.d("SignalClusterView", String.format("wifi: %s sig=%d", objArr3));
        }
        this.mWifiActivityIn.setVisibility(this.mWifiIn ? 0 : 8);
        this.mWifiActivityOut.setVisibility(this.mWifiOut ? 0 : 8);
        boolean anyMobileVisible = false;
        if (FeatureOptions.MTK_CTA_SET) {
            anyMobileVisible = true;
        }
        int firstMobileTypeId = 0;
        for (PhoneState state : this.mPhoneStates) {
            if (state.apply(anyMobileVisible) && !anyMobileVisible) {
                firstMobileTypeId = state.mMobileTypeId;
                anyMobileVisible = true;
            }
        }
        if (anyMobileVisible) {
            this.mIconLogger.onIconShown("mobile");
        } else {
            this.mIconLogger.onIconHidden("mobile");
        }
        if (this.mIsAirplaneMode) {
            if (this.mLastAirplaneIconId != this.mAirplaneIconId) {
                setIconForView(this.mAirplane, this.mAirplaneIconId);
                this.mLastAirplaneIconId = this.mAirplaneIconId;
            }
            this.mAirplane.setContentDescription(this.mAirplaneContentDescription);
            this.mIconLogger.onIconShown("airplane");
            this.mAirplane.setVisibility(0);
        } else {
            this.mIconLogger.onIconHidden("airplane");
            this.mAirplane.setVisibility(8);
        }
        if (this.mIsAirplaneMode && this.mWifiVisible) {
            this.mWifiAirplaneSpacer.setVisibility(0);
        } else {
            this.mWifiAirplaneSpacer.setVisibility(8);
        }
        if (anyMobileVisible && firstMobileTypeId != 0 && this.mWifiVisible) {
            this.mWifiSignalSpacer.setVisibility(0);
        } else {
            this.mWifiSignalSpacer.setVisibility(8);
        }
        if (!this.mWifiVisible && !this.mIsAirplaneMode && !anyMobileVisible && !this.mVpnVisible && !this.mEthernetVisible) {
            z = false;
        }
        boolean anythingVisible = z;
        setPaddingRelative(0, 0, anythingVisible ? this.mEndPadding : this.mEndPaddingNothingVisible, 0);
    }

    private void setIconForView(ImageView imageView, int iconId) {
        Drawable icon = imageView.getContext().getDrawable(iconId);
        if (this.mIconScaleFactor == 1.0f) {
            imageView.setImageDrawable(icon);
        } else {
            imageView.setImageDrawable(new ScalingDrawableWrapper(icon, this.mIconScaleFactor));
        }
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect tintArea, float darkIntensity, int tint) {
        boolean changed = (tint == this.mIconTint && darkIntensity == this.mDarkIntensity && this.mTintArea.equals(tintArea)) ? false : true;
        this.mIconTint = tint;
        this.mDarkIntensity = darkIntensity;
        this.mTintArea.set(tintArea);
        if (changed && isAttachedToWindow()) {
            applyIconTint();
        }
    }

    private void applyIconTint() {
        setTint(this.mVpn, DarkIconDispatcher.getTint(this.mTintArea, this.mVpn, this.mIconTint));
        setTint(this.mAirplane, DarkIconDispatcher.getTint(this.mTintArea, this.mAirplane, this.mIconTint));
        this.mStatusBarExt.setCustomizedPlmnTextTint(this.mIconTint);
        applyDarkIntensity(DarkIconDispatcher.getDarkIntensity(this.mTintArea, this.mWifi, this.mDarkIntensity), this.mWifi, this.mWifiDark);
        setTint(this.mWifiActivityIn, DarkIconDispatcher.getTint(this.mTintArea, this.mWifiActivityIn, this.mIconTint));
        setTint(this.mWifiActivityOut, DarkIconDispatcher.getTint(this.mTintArea, this.mWifiActivityOut, this.mIconTint));
        applyDarkIntensity(DarkIconDispatcher.getDarkIntensity(this.mTintArea, this.mEthernet, this.mDarkIntensity), this.mEthernet, this.mEthernetDark);
        for (int i = 0; i < this.mPhoneStates.size(); i++) {
            this.mPhoneStates.get(i).setIconTint(this.mIconTint, this.mDarkIntensity, this.mTintArea);
        }
    }

    private void applyDarkIntensity(float darkIntensity, View lightIcon, View darkIcon) {
        lightIcon.setAlpha(1.0f - darkIntensity);
        darkIcon.setAlpha(darkIntensity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTint(ImageView v, int tint) {
        v.setImageTintList(ColorStateList.valueOf(tint));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int currentVpnIconId(boolean isBranded) {
        return isBranded ? R.drawable.stat_sys_branded_vpn : R.drawable.stat_sys_vpn_ic;
    }

    private class PhoneState {
        public boolean mActivityIn;
        public boolean mActivityOut;
        private boolean mDataActivityIn;
        private boolean mDataActivityOut;
        private boolean mIsWfcCase;
        private ImageView mMobile;
        private ImageView mMobileActivityIn;
        private ImageView mMobileActivityOut;
        private String mMobileDescription;
        private ViewGroup mMobileGroup;
        private ImageView mMobileRoaming;
        private View mMobileRoamingSpace;
        private SignalDrawable mMobileSignalDrawable;
        private ImageView mMobileType;
        private String mMobileTypeDescription;
        private ImageView mNetworkType;
        private ISystemUIStatusBarExt mPhoneStateExt;
        public boolean mRoaming;
        private final int mSubId;
        private ImageView mVolteType;
        private boolean mMobileVisible = false;
        private int mMobileStrengthId = 0;
        private int mMobileTypeId = 0;
        private int mNetworkIcon = 0;
        private int mVolteIcon = 0;
        private int mLastMobileStrengthId = -1;
        private int mLastMobileTypeId = -1;

        public PhoneState(int subId, Context context) {
            ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.mobile_signal_group_ext, (ViewGroup) null);
            this.mPhoneStateExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeSystemUIStatusBar(context);
            this.mPhoneStateExt.addCustomizedView(subId, context, root);
            setViews(root);
            this.mSubId = subId;
        }

        public void setViews(ViewGroup root) {
            this.mMobileGroup = root;
            this.mMobile = (ImageView) root.findViewById(R.id.mobile_signal);
            this.mMobileType = (ImageView) root.findViewById(R.id.mobile_type);
            this.mNetworkType = (ImageView) root.findViewById(R.id.network_type);
            this.mVolteType = (ImageView) root.findViewById(R.id.volte_indicator_ext);
            this.mMobileRoaming = (ImageView) root.findViewById(R.id.mobile_roaming);
            this.mMobileRoamingSpace = root.findViewById(R.id.mobile_roaming_space);
            this.mMobileActivityIn = (ImageView) root.findViewById(R.id.mobile_in);
            this.mMobileActivityOut = (ImageView) root.findViewById(R.id.mobile_out);
            this.mMobileSignalDrawable = new SignalDrawable(this.mMobile.getContext());
            this.mMobile.setImageDrawable(this.mMobileSignalDrawable);
        }

        public boolean apply(boolean isSecondaryIcon) {
            if (!this.mMobileVisible || SignalClusterView.this.mIsAirplaneMode) {
                if (SignalClusterView.this.mIsAirplaneMode && SignalClusterView.this.mIsWfcEnable && this.mVolteIcon != 0) {
                    this.mMobileGroup.setVisibility(0);
                    hideViewInWfcCase();
                } else {
                    if (SignalClusterView.DEBUG) {
                        Log.d("SignalClusterView", "setVisibility as GONE, this = " + this + ", mMobileVisible = " + this.mMobileVisible + ", mIsAirplaneMode = " + SignalClusterView.this.mIsAirplaneMode + ", mIsWfcEnable = " + SignalClusterView.this.mIsWfcEnable + ", mVolteIcon = " + this.mVolteIcon);
                    }
                    this.mMobileGroup.setVisibility(8);
                }
            } else {
                if (this.mLastMobileStrengthId != this.mMobileStrengthId) {
                    this.mMobile.getDrawable().setLevel(this.mMobileStrengthId);
                    this.mLastMobileStrengthId = this.mMobileStrengthId;
                }
                if (this.mLastMobileTypeId != this.mMobileTypeId) {
                    if (!this.mPhoneStateExt.disableHostFunction()) {
                        this.mMobileType.setImageResource(this.mMobileTypeId);
                    }
                    this.mLastMobileTypeId = this.mMobileTypeId;
                }
                this.mMobileGroup.setContentDescription(this.mMobileTypeDescription + " " + this.mMobileDescription);
                this.mMobileGroup.setVisibility(0);
                showViewInWfcCase();
            }
            setCustomizeViewProperty();
            this.mMobileGroup.setPaddingRelative(isSecondaryIcon ? SignalClusterView.this.mSecondaryTelephonyPadding : 0, 0, 0, 0);
            this.mMobile.setPaddingRelative(SignalClusterView.this.mMobileDataIconStartPadding, 0, 0, 0);
            if (SignalClusterView.DEBUG) {
                Object[] objArr = new Object[3];
                objArr[0] = this.mMobileVisible ? "VISIBLE" : "GONE";
                objArr[1] = Integer.valueOf(this.mMobileStrengthId);
                objArr[2] = Integer.valueOf(this.mMobileTypeId);
                Log.d("SignalClusterView", String.format("mobile: %s sig=%d typ=%d", objArr));
            }
            if (!this.mIsWfcCase) {
                this.mMobileType.setVisibility(this.mMobileTypeId != 0 ? 0 : 8);
                this.mMobileRoaming.setVisibility(this.mRoaming ? 0 : 8);
                this.mMobileRoamingSpace.setVisibility(this.mRoaming ? 0 : 8);
                this.mMobileActivityIn.setVisibility(this.mActivityIn ? 0 : 8);
                this.mMobileActivityOut.setVisibility(this.mActivityOut ? 0 : 8);
            }
            setCustomizedOpViews();
            return this.mMobileVisible;
        }

        public void populateAccessibilityEvent(AccessibilityEvent event) {
            if (this.mMobileVisible && this.mMobileGroup != null && this.mMobileGroup.getContentDescription() != null) {
                event.getText().add(this.mMobileGroup.getContentDescription());
            }
        }

        public void setIconTint(int tint, float darkIntensity, Rect tintArea) {
            this.mMobileSignalDrawable.setDarkIntensity(darkIntensity);
            SignalClusterView.this.setTint(this.mMobileType, DarkIconDispatcher.getTint(tintArea, this.mMobileType, tint));
            SignalClusterView.this.setTint(this.mMobileRoaming, DarkIconDispatcher.getTint(tintArea, this.mMobileRoaming, tint));
            SignalClusterView.this.setTint(this.mMobileActivityIn, DarkIconDispatcher.getTint(tintArea, this.mMobileActivityIn, tint));
            SignalClusterView.this.setTint(this.mMobileActivityOut, DarkIconDispatcher.getTint(tintArea, this.mMobileActivityOut, tint));
            SignalClusterView.this.setTint(this.mNetworkType, DarkIconDispatcher.getTint(tintArea, this.mNetworkType, tint));
            SignalClusterView.this.setTint(this.mVolteType, DarkIconDispatcher.getTint(tintArea, this.mVolteType, tint));
            this.mPhoneStateExt.setIconTint(DarkIconDispatcher.getTint(tintArea, this.mNetworkType, tint), darkIntensity);
        }

        private void setCustomizeViewProperty() {
            setNetworkIcon();
            setVolteIcon();
        }

        private void setVolteIcon() {
            if (this.mVolteIcon == 0) {
                this.mVolteType.setVisibility(8);
            } else {
                this.mVolteType.setImageResource(this.mVolteIcon);
                this.mVolteType.setVisibility(0);
            }
            SignalClusterView.this.mStatusBarExt.setCustomizedVolteView(this.mVolteIcon, this.mVolteType);
            SignalClusterView.this.mStatusBarExt.setDisVolteView(this.mSubId, this.mVolteIcon, this.mVolteType);
        }

        private void setNetworkIcon() {
            if (!FeatureOptions.MTK_CTA_SET || this.mIsWfcCase) {
                return;
            }
            if (this.mNetworkIcon == 0) {
                this.mNetworkType.setVisibility(8);
                return;
            }
            if (!this.mPhoneStateExt.disableHostFunction()) {
                this.mNetworkType.setImageResource(this.mNetworkIcon);
            }
            this.mNetworkType.setVisibility(0);
        }

        private void setCustomizedOpViews() {
            this.mPhoneStateExt.SetHostViewInvisible(this.mMobileRoaming);
            this.mPhoneStateExt.SetHostViewInvisible(this.mMobileActivityIn);
            this.mPhoneStateExt.SetHostViewInvisible(this.mMobileActivityOut);
            if (this.mMobileVisible && !SignalClusterView.this.mIsAirplaneMode) {
                this.mPhoneStateExt.getServiceStateForCustomizedView(this.mSubId);
                this.mPhoneStateExt.setCustomizedNetworkTypeView(this.mSubId, this.mNetworkIcon, this.mNetworkType);
                this.mPhoneStateExt.setCustomizedDataTypeView(this.mSubId, this.mMobileTypeId, this.mDataActivityIn, this.mDataActivityOut);
                this.mPhoneStateExt.setCustomizedSignalStrengthView(this.mSubId, this.mMobileStrengthId, this.mMobile);
                this.mPhoneStateExt.setCustomizedMobileTypeView(this.mSubId, this.mMobileType);
                this.mPhoneStateExt.setCustomizedView(this.mSubId);
            }
        }

        private void hideViewInWfcCase() {
            Log.d("SignalClusterView", "hideViewInWfcCase, isWfcEnabled = " + SignalClusterView.this.mIsWfcEnable + " mSubId =" + this.mSubId);
            this.mMobile.setVisibility(8);
            this.mMobileType.setVisibility(8);
            this.mNetworkType.setVisibility(8);
            this.mMobileRoaming.setVisibility(8);
            this.mIsWfcCase = true;
        }

        private void showViewInWfcCase() {
            if (this.mIsWfcCase) {
                Log.d("SignalClusterView", "showViewInWfcCase: mSubId = " + this.mSubId);
                this.mMobile.setVisibility(0);
                this.mMobileType.setVisibility(0);
                this.mNetworkType.setVisibility(0);
                this.mMobileRoaming.setVisibility(this.mRoaming ? 0 : 8);
                this.mIsWfcCase = false;
            }
        }
    }
}
