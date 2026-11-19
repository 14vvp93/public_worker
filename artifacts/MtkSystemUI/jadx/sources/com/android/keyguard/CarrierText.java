package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.internal.telephony.IccCardConstants;
import com.android.settingslib.WirelessUtils;
import com.mediatek.keyguard.ext.ICarrierTextExt;
import com.mediatek.keyguard.ext.IOperatorSIMString;
import com.mediatek.keyguard.ext.OpKeyguardCustomizationFactoryBase;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class CarrierText extends TextView {
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private static CharSequence mSeparator;
    private KeyguardUpdateMonitorCallback mCallback;
    private String[] mCarrier;
    private boolean[] mCarrierNeedToShow;
    private ICarrierTextExt mCarrierTextExt;
    private Context mContext;
    private int mFlags;
    private IOperatorSIMString mIOperatorSIMString;
    private final boolean mIsEmergencyCallCapable;
    private KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private int mNumOfPhone;
    private boolean[] mSimErrorState;
    private StatusMode[] mStatusMode;
    private WifiManager mWifiManager;

    private enum StatusMode {
        Normal,
        NetworkLocked,
        SimMissing,
        SimMissingLocked,
        SimPukLocked,
        SimLocked,
        SimPermDisabled,
        SimNotReady,
        SimIoError,
        SimUnknown,
        NetworkSearching
    }

    public void setDisplayFlags(int flags) {
        this.mFlags = flags;
    }

    private void initMembers() {
        this.mNumOfPhone = KeyguardUtils.getNumOfPhone();
        this.mCarrier = new String[this.mNumOfPhone];
        this.mCarrierNeedToShow = new boolean[this.mNumOfPhone];
        this.mStatusMode = new StatusMode[this.mNumOfPhone];
        for (int i = 0; i < this.mNumOfPhone; i++) {
            this.mStatusMode[i] = StatusMode.Normal;
        }
    }

    public CarrierText(Context context) {
        this(context, null);
    }

    public CarrierText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSimErrorState = new boolean[TelephonyManager.getDefault().getPhoneCount()];
        this.mCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.CarrierText.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onRefreshCarrierInfo() {
                CarrierText.this.updateCarrierText();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onFinishedGoingToSleep(int why) {
                CarrierText.this.setSelected(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onStartedWakingUp() {
                CarrierText.this.setSelected(true);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
                CarrierText.this.updateCarrierText();
            }
        };
        this.mContext = context;
        this.mIsEmergencyCallCapable = context.getResources().getBoolean(android.R.^attr-private.popupPromptView);
        this.mKeyguardUpdateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        initMembers();
        this.mIOperatorSIMString = OpKeyguardCustomizationFactoryBase.getOpFactory(this.mContext).makeOperatorSIMString();
        this.mCarrierTextExt = OpKeyguardCustomizationFactoryBase.getOpFactory(this.mContext).makeCarrierText();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CarrierText, 0, 0);
        try {
            boolean useAllCaps = a.getBoolean(0, false);
            a.recycle();
            setTransformationMethod(new CarrierTextTransformationMethod(this.mContext, useAllCaps));
            this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    protected void updateCarrierText() {
        boolean anySimReadyAndInService;
        ServiceState ss;
        if (isWifiOnlyDevice()) {
            Log.d("CarrierText", "updateCarrierText() - WifiOnly deivce, not show carrier text.");
            setText("");
            return;
        }
        boolean allSimsMissing = showOrHideCarrier();
        int i = 0;
        boolean anySimReadyAndInService2 = false;
        int phoneId = 0;
        while (true) {
            int phoneId2 = phoneId;
            int phoneId3 = this.mNumOfPhone;
            if (phoneId2 >= phoneId3) {
                break;
            }
            int subId = KeyguardUtils.getSubIdUsingPhoneId(phoneId2);
            IccCardConstants.State simState = this.mKeyguardUpdateMonitor.getSimStateOfPhoneId(phoneId2);
            SubscriptionInfo subInfo = this.mKeyguardUpdateMonitor.getSubscriptionInfoForSubId(subId);
            CharSequence carrierName = subInfo == null ? null : subInfo.getCarrierName();
            if (simState != IccCardConstants.State.READY || (ss = this.mKeyguardUpdateMonitor.mServiceStates.get(Integer.valueOf(subId))) == null || ss.getDataRegState() != 0 || (ss.getRilDataRadioTechnology() == 18 && (!this.mWifiManager.isWifiEnabled() || this.mWifiManager.getConnectionInfo() == null || this.mWifiManager.getConnectionInfo().getBSSID() == null))) {
                anySimReadyAndInService = anySimReadyAndInService2;
            } else {
                if (DEBUG) {
                    Log.d("CarrierText", "SIM ready and in service: subId=" + subId + ", ss=" + ss);
                }
                anySimReadyAndInService = true;
            }
            CharSequence carrierTextForSimState = getCarrierTextForSimState(phoneId2, simState, carrierName, null, null);
            if (carrierTextForSimState != null) {
                CharSequence carrierTextForSimState2 = this.mIOperatorSIMString.getOperatorSIMString(carrierTextForSimState.toString(), phoneId2, IOperatorSIMString.SIMChangedTag.DELSIM, this.mContext);
                if (carrierTextForSimState2 != null) {
                    carrierTextForSimState = this.mCarrierTextExt.customizeCarrierTextCapital(carrierTextForSimState2.toString()).toString();
                } else {
                    carrierTextForSimState = null;
                }
            }
            if (carrierTextForSimState == null) {
                this.mCarrier[phoneId2] = null;
            } else {
                this.mCarrier[phoneId2] = carrierTextForSimState.toString();
            }
            phoneId = phoneId2 + 1;
            anySimReadyAndInService2 = anySimReadyAndInService;
        }
        String carrierFinalContent = null;
        String divider = this.mCarrierTextExt.customizeCarrierTextDivider(mSeparator.toString());
        while (true) {
            int i2 = i;
            if (i2 >= this.mNumOfPhone) {
                break;
            }
            if (this.mCarrierNeedToShow[i2] && this.mCarrier[i2] != null) {
                if (carrierFinalContent == null) {
                    carrierFinalContent = this.mCarrier[i2];
                } else {
                    carrierFinalContent = carrierFinalContent + divider + this.mCarrier[i2];
                }
            }
            i = i2 + 1;
        }
        if (!anySimReadyAndInService2 && WirelessUtils.isAirplaneModeOn(this.mContext)) {
            carrierFinalContent = getContext().getString(com.android.systemui.R.string.airplane_mode);
        }
        Log.d("CarrierText", "updateCarrierText() - after combination, carrierFinalContent = " + carrierFinalContent + ", allSimsMissing = " + allSimsMissing);
        setText(carrierFinalContent);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSeparator = getResources().getString(android.R.string.config_wearMediaSessionsPackage);
        boolean shouldMarquee = KeyguardUpdateMonitor.getInstance(this.mContext).isDeviceInteractive();
        setSelected(shouldMarquee);
        setLayerType(2, null);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (ConnectivityManager.from(this.mContext).isNetworkSupported(0)) {
            this.mKeyguardUpdateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
            this.mKeyguardUpdateMonitor.registerCallback(this.mCallback);
        } else {
            this.mKeyguardUpdateMonitor = null;
            setText("");
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mKeyguardUpdateMonitor != null) {
            this.mKeyguardUpdateMonitor.removeCallback(this.mCallback);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0) {
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    private CharSequence getCarrierTextForSimState(int phoneId, IccCardConstants.State simState, CharSequence text, CharSequence hnbName, CharSequence csgId) {
        CharSequence carrierText;
        StatusMode status = getStatusForIccState(simState);
        switch (status) {
            case Normal:
                carrierText = text;
                break;
            case SimNotReady:
                carrierText = null;
                break;
            case NetworkLocked:
                carrierText = makeCarrierStringOnEmergencyCapable(this.mContext.getText(com.android.systemui.R.string.keyguard_network_locked_message), text, hnbName, csgId);
                break;
            case SimMissing:
                CharSequence simMessage = getContext().getText(com.android.systemui.R.string.keyguard_missing_sim_message_short);
                CharSequence carrierText2 = makeCarrierStringOnEmergencyCapable(simMessage, text, hnbName, csgId);
                carrierText = this.mCarrierTextExt.customizeCarrierTextWhenSimMissing(this.mCarrierTextExt.customizeCarrierText(carrierText2, simMessage, phoneId));
                break;
            case SimPermDisabled:
                carrierText = makeCarrierStringOnEmergencyCapable(getContext().getText(com.android.systemui.R.string.keyguard_permanent_disabled_sim_message_short), text, hnbName, csgId);
                break;
            case SimMissingLocked:
                carrierText = null;
                break;
            case SimLocked:
                carrierText = makeCarrierStringOnEmergencyCapable(getContext().getText(com.android.systemui.R.string.keyguard_sim_locked_message), text, hnbName, csgId);
                break;
            case SimPukLocked:
                carrierText = makeCarrierStringOnEmergencyCapable(getContext().getText(com.android.systemui.R.string.keyguard_sim_puk_locked_message), text, hnbName, csgId);
                break;
            case SimIoError:
                carrierText = makeCarrierStringOnEmergencyCapable(getContext().getText(com.android.systemui.R.string.keyguard_sim_error_message_short), text, hnbName, csgId);
                break;
            default:
                carrierText = text;
                break;
        }
        if (carrierText != null) {
            carrierText = this.mCarrierTextExt.customizeCarrierTextWhenCardTypeLocked(carrierText, this.mContext, phoneId, false).toString();
        }
        if (DEBUG) {
            Log.d("CarrierText", "getCarrierTextForSimState simState=" + simState + " carrierName(from Sub)=" + ((Object) text) + " HNB=" + ((Object) hnbName) + " CSG=" + ((Object) csgId) + " carrierText=" + ((Object) carrierText));
        }
        return carrierText;
    }

    private CharSequence makeCarrierStringOnEmergencyCapable(CharSequence simMessage, CharSequence emergencyCallMessage, CharSequence hnbName, CharSequence csgId) {
        CharSequence emergencyCallMessageExtend = emergencyCallMessage;
        if (!TextUtils.isEmpty(emergencyCallMessage)) {
            emergencyCallMessageExtend = appendCsgInfo(emergencyCallMessage, hnbName, csgId);
        }
        if (this.mIsEmergencyCallCapable) {
            return concatenate(simMessage, emergencyCallMessageExtend);
        }
        return simMessage;
    }

    private StatusMode getStatusForIccState(IccCardConstants.State simState) {
        if (simState == null) {
            return StatusMode.SimUnknown;
        }
        boolean missingAndNotProvisioned = !KeyguardUpdateMonitor.getInstance(this.mContext).isDeviceProvisioned() && (simState == IccCardConstants.State.ABSENT || simState == IccCardConstants.State.PERM_DISABLED);
        if (missingAndNotProvisioned) {
            return StatusMode.SimMissingLocked;
        }
        switch (AnonymousClass2.$SwitchMap$com$android$internal$telephony$IccCardConstants$State[simState.ordinal()]) {
            case 1:
                return StatusMode.SimMissing;
            case 2:
                return StatusMode.NetworkLocked;
            case 3:
                return StatusMode.SimNotReady;
            case 4:
                return StatusMode.SimLocked;
            case 5:
                return StatusMode.SimPukLocked;
            case 6:
                return StatusMode.Normal;
            case 7:
                return StatusMode.SimPermDisabled;
            case 8:
                return StatusMode.SimUnknown;
            case 9:
                return StatusMode.SimIoError;
            default:
                return StatusMode.SimMissing;
        }
    }

    /* JADX INFO: renamed from: com.android.keyguard.CarrierText$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$IccCardConstants$State = new int[IccCardConstants.State.values().length];

        static {
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.ABSENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.NETWORK_LOCKED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.NOT_READY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PIN_REQUIRED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PUK_REQUIRED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.READY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PERM_DISABLED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.UNKNOWN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.CARD_IO_ERROR.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            $SwitchMap$com$android$keyguard$CarrierText$StatusMode = new int[StatusMode.values().length];
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimNotReady.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.NetworkLocked.ordinal()] = 3;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimMissing.ordinal()] = 4;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimPermDisabled.ordinal()] = 5;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimMissingLocked.ordinal()] = 6;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimLocked.ordinal()] = 7;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimPukLocked.ordinal()] = 8;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierText$StatusMode[StatusMode.SimIoError.ordinal()] = 9;
            } catch (NoSuchFieldError e18) {
            }
        }
    }

    private static CharSequence concatenate(CharSequence plmn, CharSequence spn) {
        boolean plmnValid = !TextUtils.isEmpty(plmn);
        boolean spnValid = !TextUtils.isEmpty(spn);
        if (plmnValid && spnValid) {
            StringBuilder sb = new StringBuilder();
            sb.append(plmn);
            sb.append(mSeparator);
            sb.append(spn);
            return sb.toString();
        }
        if (plmnValid) {
            return plmn;
        }
        if (spnValid) {
            return spn;
        }
        return "";
    }

    private class CarrierTextTransformationMethod extends SingleLineTransformationMethod {
        private final boolean mAllCaps;
        private final Locale mLocale;

        public CarrierTextTransformationMethod(Context context, boolean allCaps) {
            this.mLocale = context.getResources().getConfiguration().locale;
            this.mAllCaps = allCaps;
        }

        @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
        public CharSequence getTransformation(CharSequence source, View view) {
            CharSequence source2 = super.getTransformation(source, view);
            if (this.mAllCaps && source2 != null) {
                return source2.toString().toUpperCase(this.mLocale);
            }
            return source2;
        }
    }

    private boolean isWifiOnlyDevice() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService("connectivity");
        return !cm.isNetworkSupported(0);
    }

    private boolean showOrHideCarrier() {
        int mNumOfSIM = 0;
        for (int mNumOfSIM2 = 0; mNumOfSIM2 < this.mNumOfPhone; mNumOfSIM2++) {
            IccCardConstants.State simState = this.mKeyguardUpdateMonitor.getSimStateOfPhoneId(mNumOfSIM2);
            StatusMode statusMode = getStatusForIccState(simState);
            boolean simMissing = statusMode == StatusMode.SimMissing || statusMode == StatusMode.SimMissingLocked || statusMode == StatusMode.SimUnknown;
            if (this.mCarrierTextExt.showCarrierTextWhenSimMissing(simMissing, mNumOfSIM2)) {
                this.mCarrierNeedToShow[mNumOfSIM2] = false;
            } else {
                this.mCarrierNeedToShow[mNumOfSIM2] = true;
                mNumOfSIM++;
            }
        }
        List<SubscriptionInfo> subs = this.mKeyguardUpdateMonitor.getSubscriptionInfo(false);
        if (mNumOfSIM == 0) {
            String defaultPlmn = this.mKeyguardUpdateMonitor.getDefaultPlmn().toString();
            int index = 0;
            for (int i = 0; i < subs.size(); i++) {
                SubscriptionInfo info = subs.get(i);
                info.getSubscriptionId();
                int phoneId = info.getSimSlotIndex();
                CharSequence carrierName = info.getCarrierName();
                if (carrierName != null && !defaultPlmn.contentEquals(carrierName)) {
                    index = phoneId;
                    break;
                }
            }
            this.mCarrierNeedToShow[index] = true;
        }
        return mNumOfSIM == 0;
    }

    private CharSequence appendCsgInfo(CharSequence srcText, CharSequence hnbName, CharSequence csgId) {
        if (!TextUtils.isEmpty(hnbName)) {
            CharSequence outText = concatenate(srcText, hnbName);
            return outText;
        }
        if (TextUtils.isEmpty(csgId)) {
            return srcText;
        }
        CharSequence outText2 = concatenate(srcText, csgId);
        return outText2;
    }
}
