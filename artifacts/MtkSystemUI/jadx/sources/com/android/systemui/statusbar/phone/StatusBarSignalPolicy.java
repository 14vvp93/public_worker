package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.tuner.TunerService;
import com.mediatek.systemui.ext.ISystemUIStatusBarExt;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarSignalPolicy implements NetworkController.SignalCallback, SecurityController.SecurityControllerCallback, TunerService.Tunable, ISystemUIStatusBarExt.StatusBarCallback {
    private static final boolean DEBUG;
    private boolean mActivityEnabled;
    private boolean mBlockAirplane;
    private boolean mBlockEthernet;
    private boolean mBlockMobile;
    private boolean mBlockWifi;
    private final Context mContext;
    private boolean mForceBlockWifi;
    private final StatusBarIconController mIconController;
    private final NetworkController mNetworkController;
    private final SecurityController mSecurityController;
    private final String mSlotAirplane;
    private final String mSlotEthernet;
    private final String mSlotMobile;
    private final String mSlotVpn;
    private final String mSlotWifi;
    private ISystemUIStatusBarExt mStatusBarExt;
    private final Handler mHandler = Handler.getMain();
    private boolean mIsAirplaneMode = false;
    private boolean mWifiVisible = false;
    private ArrayList<MobileIconState> mMobileStates = new ArrayList<>();
    private WifiIconState mWifiIconState = new WifiIconState();
    private boolean mNoSimsVisible = false;

    static {
        DEBUG = Log.isLoggable("StatusBarSignalPolicy", 3) || FeatureOptions.LOG_ENABLE;
    }

    public StatusBarSignalPolicy(Context context, StatusBarIconController iconController) {
        this.mContext = context;
        this.mSlotAirplane = this.mContext.getString(R.string.mediasize_iso_b3);
        this.mSlotMobile = this.mContext.getString(R.string.mediasize_iso_c8);
        this.mSlotWifi = this.mContext.getString(R.string.mediasize_japanese_jis_b6);
        this.mSlotEthernet = this.mContext.getString(R.string.mediasize_iso_c2);
        this.mSlotVpn = this.mContext.getString(R.string.mediasize_japanese_jis_b5);
        this.mActivityEnabled = this.mContext.getResources().getBoolean(com.android.systemui.R.bool.config_showActivity);
        this.mStatusBarExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeSystemUIStatusBar(context);
        this.mStatusBarExt.addCallback(this);
        this.mIconController = iconController;
        this.mNetworkController = (NetworkController) Dependency.get(NetworkController.class);
        this.mSecurityController = (SecurityController) Dependency.get(SecurityController.class);
        this.mNetworkController.addCallback((NetworkController.SignalCallback) this);
        this.mSecurityController.addCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVpn() {
        boolean vpnVisible = this.mSecurityController.isVpnEnabled();
        int vpnIconId = currentVpnIconId(this.mSecurityController.isVpnBranded());
        this.mIconController.setIcon(this.mSlotVpn, vpnIconId, null);
        this.mIconController.setIconVisibility(this.mSlotVpn, vpnVisible);
    }

    private int currentVpnIconId(boolean isBranded) {
        return isBranded ? com.android.systemui.R.drawable.stat_sys_branded_vpn : com.android.systemui.R.drawable.stat_sys_vpn_ic;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public void onStateChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarSignalPolicy$UsBELiDs0GJjQ8hYeagcWJmxhFc
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateVpn();
            }
        });
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if (!"icon_blacklist".equals(key)) {
            return;
        }
        ArraySet<String> blockList = StatusBarIconController.getIconBlacklist(newValue);
        boolean blockAirplane = blockList.contains(this.mSlotAirplane);
        boolean blockMobile = blockList.contains(this.mSlotMobile);
        boolean blockWifi = blockList.contains(this.mSlotWifi);
        boolean blockEthernet = blockList.contains(this.mSlotEthernet);
        if (blockAirplane != this.mBlockAirplane || blockMobile != this.mBlockMobile || blockEthernet != this.mBlockEthernet || blockWifi != this.mBlockWifi) {
            this.mBlockAirplane = blockAirplane;
            this.mBlockMobile = blockMobile;
            this.mBlockEthernet = blockEthernet;
            this.mBlockWifi = blockWifi || this.mForceBlockWifi;
            this.mNetworkController.removeCallback((NetworkController.SignalCallback) this);
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setWifiIndicators(boolean enabled, NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, boolean activityIn, boolean activityOut, String description, boolean isTransient, String statusLabel) {
        boolean z = false;
        boolean visible = statusIcon.visible && !this.mBlockWifi;
        boolean in = activityIn && this.mActivityEnabled && visible;
        boolean out = activityOut && this.mActivityEnabled && visible;
        WifiIconState newState = this.mWifiIconState.copy();
        newState.visible = visible;
        newState.resId = statusIcon.icon;
        newState.activityIn = in;
        newState.activityOut = out;
        newState.slot = this.mSlotWifi;
        newState.airplaneSpacerVisible = this.mIsAirplaneMode;
        newState.contentDescription = statusIcon.contentDescription;
        MobileIconState first = getFirstMobileState();
        if (first != null && first.typeId != 0) {
            z = true;
        }
        newState.signalSpacerVisible = z;
        updateWifiIconWithState(newState);
        this.mWifiIconState = newState;
    }

    private void updateShowWifiSignalSpacer(WifiIconState state) {
        MobileIconState first = getFirstMobileState();
        state.signalSpacerVisible = (first == null || first.typeId == 0) ? false : true;
    }

    private void updateWifiIconWithState(WifiIconState state) {
        if (state.visible && state.resId > 0) {
            this.mIconController.setSignalIcon(this.mSlotWifi, state);
            this.mIconController.setIconVisibility(this.mSlotWifi, true);
        } else {
            this.mIconController.setIconVisibility(this.mSlotWifi, false);
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataIndicators(NetworkController.IconState statusIcon, NetworkController.IconState qsIcon, int statusType, int networkType, int volteIcon, int qsType, boolean activityIn, boolean activityOut, String typeContentDescription, String description, boolean isWide, int subId, boolean roaming, boolean isDefaultData) {
        MobileIconState state = getState(subId);
        if (state == null) {
            return;
        }
        boolean z = false;
        boolean typeChanged = statusType != state.typeId && (statusType == 0 || state.typeId == 0);
        state.visible = statusIcon.visible && !this.mBlockMobile;
        state.strengthId = statusIcon.icon;
        state.typeId = statusType;
        state.contentDescription = statusIcon.contentDescription;
        state.typeContentDescription = typeContentDescription;
        state.roaming = roaming;
        state.activityIn = activityIn && this.mActivityEnabled;
        if (activityOut && this.mActivityEnabled) {
            z = true;
        }
        state.activityOut = z;
        state.networkIcon = networkType;
        state.volteIcon = volteIcon;
        state.mDataActivityIn = activityIn;
        state.mDataActivityOut = activityOut;
        state.mDefaultData = isDefaultData;
        this.mIconController.setMobileIcons(this.mSlotMobile, MobileIconState.copyStates(this.mMobileStates));
        if (typeChanged) {
            WifiIconState wifiCopy = this.mWifiIconState.copy();
            updateShowWifiSignalSpacer(wifiCopy);
            if (!Objects.equals(wifiCopy, this.mWifiIconState)) {
                updateWifiIconWithState(wifiCopy);
                this.mWifiIconState = wifiCopy;
            }
        }
    }

    private MobileIconState getState(int subId) {
        for (MobileIconState state : this.mMobileStates) {
            if (state.subId == subId) {
                return state;
            }
        }
        Log.e("StatusBarSignalPolicy", "Unexpected subscription " + subId);
        return null;
    }

    private MobileIconState getFirstMobileState() {
        if (this.mMobileStates.size() > 0) {
            return this.mMobileStates.get(0);
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setSubs(List<SubscriptionInfo> subs) {
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setSubs: size = " + subs.size() + ", subs = " + subs);
        }
        if (hasCorrectSubs(subs)) {
            if (DEBUG) {
                Log.d("StatusBarSignalPolicy", "setSubs: hasCorrectSubs and return");
                return;
            }
            return;
        }
        this.mIconController.removeAllIconsForSlot(this.mSlotMobile);
        this.mMobileStates.clear();
        int n = subs.size();
        for (int i = 0; i < n; i++) {
            this.mMobileStates.add(new MobileIconState(subs.get(i).getSubscriptionId(), subs.get(i).getSimSlotIndex()));
        }
    }

    private boolean hasCorrectSubs(List<SubscriptionInfo> subs) {
        int N = subs.size();
        if (N != this.mMobileStates.size()) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            if (this.mMobileStates.get(i).subId != subs.get(i).getSubscriptionId()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setNoSims(boolean show, boolean simDetected) {
        this.mNoSimsVisible = (!show || this.mBlockMobile || this.mIsAirplaneMode) ? false : true;
        this.mStatusBarExt.setCustomizedNoSimView(this.mNoSimsVisible);
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setEthernetIndicators(NetworkController.IconState state) {
        if (!state.visible || !this.mBlockEthernet) {
        }
        int resId = state.icon;
        String description = state.contentDescription;
        if (resId > 0) {
            this.mIconController.setIcon(this.mSlotEthernet, resId, description);
            this.mIconController.setIconVisibility(this.mSlotEthernet, true);
        } else {
            this.mIconController.setIconVisibility(this.mSlotEthernet, false);
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setIsAirplaneMode(NetworkController.IconState icon) {
        this.mIsAirplaneMode = icon.visible && !this.mBlockAirplane;
        int resId = icon.icon;
        String description = icon.contentDescription;
        if (this.mIsAirplaneMode && resId > 0) {
            this.mIconController.setIcon(this.mSlotAirplane, resId, description);
            this.mIconController.setIconVisibility(this.mSlotAirplane, true);
        } else {
            this.mIconController.setIconVisibility(this.mSlotAirplane, false);
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
    public void setMobileDataEnabled(boolean enabled) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static abstract class SignalIconState {
        public boolean activityIn;
        public boolean activityOut;
        public String contentDescription;
        public String slot;
        public boolean visible;

        private SignalIconState() {
        }

        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SignalIconState that = (SignalIconState) o;
            return this.visible == that.visible && this.activityOut == that.activityOut && this.activityIn == that.activityIn && Objects.equals(this.contentDescription, that.contentDescription) && Objects.equals(this.slot, that.slot);
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.visible), Boolean.valueOf(this.activityOut), this.slot);
        }

        protected void copyTo(SignalIconState other) {
            other.visible = this.visible;
            other.activityIn = this.activityIn;
            other.activityOut = this.activityOut;
            other.slot = this.slot;
            other.contentDescription = this.contentDescription;
        }
    }

    public static class WifiIconState extends SignalIconState {
        public boolean airplaneSpacerVisible;
        public int resId;
        public boolean signalSpacerVisible;

        public WifiIconState() {
            super();
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass() || !super.equals(o)) {
                return false;
            }
            WifiIconState that = (WifiIconState) o;
            return this.resId == that.resId && this.airplaneSpacerVisible == that.airplaneSpacerVisible && this.signalSpacerVisible == that.signalSpacerVisible;
        }

        public void copyTo(WifiIconState other) {
            super.copyTo((SignalIconState) other);
            other.resId = this.resId;
            other.airplaneSpacerVisible = this.airplaneSpacerVisible;
            other.signalSpacerVisible = this.signalSpacerVisible;
        }

        public WifiIconState copy() {
            WifiIconState newState = new WifiIconState();
            copyTo(newState);
            return newState;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public int hashCode() {
            return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.resId), Boolean.valueOf(this.airplaneSpacerVisible), Boolean.valueOf(this.signalSpacerVisible));
        }

        public String toString() {
            return "WifiIconState(resId=" + this.resId + ", visible=" + this.visible + ")";
        }
    }

    public static class MobileIconState extends SignalIconState {
        public boolean mDataActivityIn;
        public boolean mDataActivityOut;
        public boolean mDefaultData;
        public boolean needsLeadingPadding;
        public int networkIcon;
        public int phoneId;
        public boolean roaming;
        public int strengthId;
        public int subId;
        public String typeContentDescription;
        public int typeId;
        public int volteIcon;

        private MobileIconState(int subId) {
            super();
            this.subId = subId;
        }

        private MobileIconState(int subId, int phoneId) {
            this(subId);
            this.phoneId = phoneId;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass() || !super.equals(o)) {
                return false;
            }
            MobileIconState that = (MobileIconState) o;
            return this.subId == that.subId && this.strengthId == that.strengthId && this.typeId == that.typeId && this.roaming == that.roaming && this.needsLeadingPadding == that.needsLeadingPadding && Objects.equals(this.typeContentDescription, that.typeContentDescription) && this.networkIcon == that.networkIcon && this.mDataActivityIn == that.mDataActivityIn && this.mDataActivityOut == that.mDataActivityOut && this.mDefaultData == that.mDefaultData && this.volteIcon == that.volteIcon && this.phoneId == this.phoneId;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public int hashCode() {
            return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.subId), Integer.valueOf(this.strengthId), Integer.valueOf(this.typeId), Boolean.valueOf(this.roaming), Boolean.valueOf(this.needsLeadingPadding), this.typeContentDescription, Integer.valueOf(this.networkIcon), Boolean.valueOf(this.mDataActivityIn), Boolean.valueOf(this.mDataActivityOut), Boolean.valueOf(this.mDefaultData), Integer.valueOf(this.volteIcon), Integer.valueOf(this.phoneId));
        }

        public MobileIconState copy() {
            MobileIconState copy = new MobileIconState(this.subId);
            copyTo(copy);
            return copy;
        }

        public void copyTo(MobileIconState other) {
            super.copyTo((SignalIconState) other);
            other.subId = this.subId;
            other.strengthId = this.strengthId;
            other.typeId = this.typeId;
            other.roaming = this.roaming;
            other.needsLeadingPadding = this.needsLeadingPadding;
            other.typeContentDescription = this.typeContentDescription;
            other.networkIcon = this.networkIcon;
            other.mDataActivityIn = this.mDataActivityIn;
            other.mDataActivityOut = this.mDataActivityOut;
            other.mDefaultData = this.mDefaultData;
            other.volteIcon = this.volteIcon;
            other.phoneId = this.phoneId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static List<MobileIconState> copyStates(List<MobileIconState> inStates) {
            ArrayList<MobileIconState> outStates = new ArrayList<>();
            for (MobileIconState state : inStates) {
                MobileIconState copy = new MobileIconState(state.subId);
                state.copyTo(copy);
                outStates.add(copy);
            }
            return outStates;
        }

        public String toString() {
            return "MobileIconState(subId=" + this.subId + ", strengthId=" + this.strengthId + ", roaming=" + this.roaming + ", typeId=" + this.typeId + ", networkIcon = " + this.networkIcon + ", mDataActivityIn: " + this.mDataActivityIn + ", mDataActivityOut: " + this.mDataActivityOut + ", mDefaultData: " + this.mDefaultData + ", volteIcon = " + this.volteIcon + ", visible=" + this.visible + ", phoneId = " + this.phoneId + ")";
        }
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt.StatusBarCallback
    public void setSystemIcon(String slot, int resourceId, CharSequence contentDescription, boolean isVisible) {
        if (slot.equals("hdvoice")) {
            resourceId = com.android.systemui.R.drawable.stat_sys_hd_voice_call;
        } else if (slot.equals("nosim")) {
            int count = TelephonyManager.getDefault().getSimCount();
            if (count == 1) {
                resourceId = com.android.systemui.R.drawable.stat_sys_signal_null_one_sim;
            } else if (count == 2) {
                resourceId = com.android.systemui.R.drawable.stat_sys_signal_null_two_sims;
            }
        }
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setSystemIcon, slot = " + slot + ", resourceId = " + resourceId + ", contentDescription = " + ((Object) contentDescription) + ", isVisible = " + isVisible);
        }
        this.mIconController.setIcon(slot, resourceId, contentDescription);
        this.mIconController.setIconVisibility(slot, isVisible);
    }
}
