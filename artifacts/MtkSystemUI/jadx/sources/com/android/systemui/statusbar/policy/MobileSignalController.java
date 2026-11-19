package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneStateListener;
import android.telephony.PreciseDataConnectionState;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.feature.ImsFeature;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.mediatek.ims.MtkImsConnectionStateListener;
import com.mediatek.systemui.ext.IMobileIconExt;
import com.mediatek.systemui.ext.ISystemUIStatusBarExt;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.networktype.NetworkTypeUtils;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import com.mediatek.systemui.statusbar.util.SIMHelper;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MobileSignalController extends SignalController<MobileState, MobileIconGroup> {
    private CarrierConfigManager mCarrierConfigManager;
    private NetworkControllerImpl.Config mConfig;
    private int mDataNetType;
    private int mDataState;
    private MobileIconGroup mDefaultIcons;
    private final NetworkControllerImpl.SubscriptionDefaults mDefaults;
    private ImsFeature.Capabilities mImsCapConfig;

    @VisibleForTesting
    MtkImsConnectionStateListener mImsConnectionStateListener;
    private ImsManager mImsManager;
    private int mImsRadioTech;
    private IMobileIconExt mMobileIconExt;
    private final String mNetworkNameDefault;
    private final String mNetworkNameSeparator;
    final SparseArray<MobileIconGroup> mNetworkToIconLookup;
    private final ContentObserver mObserver;
    private final TelephonyManager mPhone;

    @VisibleForTesting
    final PhoneStateListener mPhoneStateListener;
    private Handler mReceiverHandler;
    private ServiceState mServiceState;
    private SignalStrength mSignalStrength;
    private ISystemUIStatusBarExt mStatusBarExt;
    SubscriptionInfo mSubscriptionInfo;

    public MobileSignalController(Context context, NetworkControllerImpl.Config config, boolean hasMobileData, TelephonyManager phone, CallbackHandler callbackHandler, NetworkControllerImpl networkController, SubscriptionInfo info, NetworkControllerImpl.SubscriptionDefaults defaults, Looper receiverLooper) {
        super("MobileSignalController(" + info.getSubscriptionId() + ")(" + info.getSimSlotIndex() + ")", context, 0, callbackHandler, networkController);
        this.mDataNetType = 0;
        this.mDataState = 0;
        this.mImsConnectionStateListener = new MtkImsConnectionStateListener() { // from class: com.android.systemui.statusbar.policy.MobileSignalController.2
            public void onImsConnected(final int imsRadioTech) {
                MobileSignalController.this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.MobileSignalController.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SignalController.DEBUG) {
                            Log.d(MobileSignalController.this.mTag, "onImsConnected STATE_IN_SERVICE, imsRadioTech = " + imsRadioTech);
                        }
                        MobileSignalController.this.mImsRadioTech = imsRadioTech;
                        ((MobileState) MobileSignalController.this.mCurrentState).imsRegState = 0;
                        MobileSignalController.this.updateIms();
                    }
                });
            }

            public void onImsDisconnected(ImsReasonInfo info2) {
                MobileSignalController.this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.MobileSignalController.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SignalController.DEBUG) {
                            Log.d(MobileSignalController.this.mTag, "onImsDisconnected STATE_OUT_OF_SERVICE");
                        }
                        ((MobileState) MobileSignalController.this.mCurrentState).imsRegState = 1;
                        ((MobileState) MobileSignalController.this.mCurrentState).imsCap = -1;
                        MobileSignalController.this.updateTelephony();
                    }
                });
            }

            public void onCapabilitiesStatusChanged(final ImsFeature.Capabilities config2) {
                MobileSignalController.this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.MobileSignalController.2.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SignalController.DEBUG) {
                            Log.d(MobileSignalController.this.mTag, "onCapabilitiesStatusChanged:" + config2);
                        }
                        MobileSignalController.this.mImsCapConfig = config2;
                        MobileSignalController.this.updateIms();
                    }
                });
            }

            public void onWifiPdnOOSStateChanged(final int oosState) {
                MobileSignalController.this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.MobileSignalController.2.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SignalController.DEBUG) {
                            Log.d(MobileSignalController.this.mTag, "onWifiPdnOOSStateChanged oosState:" + oosState);
                        }
                        if (oosState == 0 || oosState == 1) {
                            ((MobileState) MobileSignalController.this.mCurrentState).imsRegState = 1;
                        } else if (oosState == 2) {
                            ((MobileState) MobileSignalController.this.mCurrentState).imsRegState = 0;
                        }
                        MobileSignalController.this.updateTelephony();
                    }
                });
            }
        };
        this.mNetworkToIconLookup = new SparseArray<>();
        this.mConfig = config;
        this.mPhone = phone;
        this.mDefaults = defaults;
        this.mSubscriptionInfo = info;
        this.mMobileIconExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeMobileIcon();
        this.mStatusBarExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeSystemUIStatusBar(context);
        this.mPhoneStateListener = new MobilePhoneStateListener(info.getSubscriptionId(), receiverLooper);
        this.mReceiverHandler = new Handler(receiverLooper);
        this.mNetworkNameSeparator = getStringIfExists(R.string.status_bar_network_name_separator);
        this.mNetworkNameDefault = getStringIfExists(android.R.string.contentServiceTooManyDeletesNotificationDesc);
        mapIconSets();
        String networkName = info.getCarrierName() != null ? info.getCarrierName().toString() : this.mNetworkNameDefault;
        MobileState mobileState = (MobileState) this.mLastState;
        ((MobileState) this.mCurrentState).networkName = networkName;
        mobileState.networkName = networkName;
        MobileState mobileState2 = (MobileState) this.mLastState;
        ((MobileState) this.mCurrentState).networkNameData = networkName;
        mobileState2.networkNameData = networkName;
        MobileState mobileState3 = (MobileState) this.mLastState;
        ((MobileState) this.mCurrentState).enabled = hasMobileData;
        mobileState3.enabled = hasMobileData;
        MobileState mobileState4 = (MobileState) this.mLastState;
        MobileState mobileState5 = (MobileState) this.mCurrentState;
        MobileIconGroup mobileIconGroup = this.mDefaultIcons;
        mobileState5.iconGroup = mobileIconGroup;
        mobileState4.iconGroup = mobileIconGroup;
        this.mCarrierConfigManager = (CarrierConfigManager) context.getSystemService("carrier_config");
        updateDataSim();
        this.mObserver = new ContentObserver(new Handler(receiverLooper)) { // from class: com.android.systemui.statusbar.policy.MobileSignalController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                MobileSignalController.this.updateTelephony();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIms() {
        onFeatureCapabilityChangedAdapter(this.mImsRadioTech, this.mImsCapConfig);
        updateTelephony();
    }

    private void onFeatureCapabilityChangedAdapter(int imsRadioTech, ImsFeature.Capabilities c) {
        if (c == null) {
            Log.w(this.mTag, "onFeatureCapabilityChangedAdapter, capabilities is null, return");
            return;
        }
        int[] enabledCapabilities = new int[6];
        Arrays.fill(enabledCapabilities, -1);
        int[] disabledCapabilities = new int[6];
        Arrays.fill(disabledCapabilities, -1);
        switch (imsRadioTech) {
            case 0:
                if (c.isCapable(1)) {
                    enabledCapabilities[0] = 0;
                }
                if (c.isCapable(2)) {
                    enabledCapabilities[1] = 1;
                }
                if (c.isCapable(4)) {
                    enabledCapabilities[4] = 4;
                }
                break;
            case 1:
                if (c.isCapable(1)) {
                    enabledCapabilities[2] = 2;
                }
                if (c.isCapable(2)) {
                    enabledCapabilities[3] = 3;
                }
                if (c.isCapable(4)) {
                    enabledCapabilities[5] = 5;
                }
                break;
        }
        for (int i = 0; i < enabledCapabilities.length; i++) {
            if (enabledCapabilities[i] != i) {
                disabledCapabilities[i] = i;
            }
        }
        ((MobileState) this.mCurrentState).imsCap = getImsEnableCap(enabledCapabilities);
    }

    private int getImsEnableCap(int[] enabledFeatures) {
        if (enabledFeatures == null) {
            return -1;
        }
        if (enabledFeatures[2] == 2) {
            return 2;
        }
        if (enabledFeatures[0] != 0) {
            return -1;
        }
        return 0;
    }

    private int getVolteIcon() {
        int icon = 0;
        if (isImsOverWfc()) {
            boolean needShowWfcSysIcon = this.mStatusBarExt.needShowWfcIcon();
            if (needShowWfcSysIcon && ((MobileState) this.mCurrentState).imsRegState == 0) {
                icon = R.drawable.stat_sys_wfc;
            }
        } else if (isImsOverVoice() && isLteNetWork()) {
            if (((MobileState) this.mCurrentState).imsRegState == 0) {
                icon = R.drawable.stat_sys_volte;
            } else if (FeatureOptions.MTK_CT_MIXED_VOLTE_SUPPORT && SIMHelper.isSecondaryCSIMForMixedVolte(this.mSubscriptionInfo.getSubscriptionId()) && ((MobileState) this.mCurrentState).imsRegState == 1) {
                if (DEBUG) {
                    Log.d(this.mTag, "set dis volte icon");
                }
                icon = R.drawable.stat_sys_volte_dis;
            }
        }
        this.mStatusBarExt.setImsRegInfo(this.mSubscriptionInfo.getSubscriptionId(), ((MobileState) this.mCurrentState).imsRegState, isImsOverWfc(), isImsOverVoice());
        return icon;
    }

    public boolean isImsOverWfc() {
        return ((MobileState) this.mCurrentState).imsCap == 2;
    }

    private boolean isImsOverVoice() {
        return ((MobileState) this.mCurrentState).imsCap == 0;
    }

    public boolean isLteNetWork() {
        return this.mDataNetType == 13 || this.mDataNetType == 19;
    }

    public void setConfiguration(NetworkControllerImpl.Config config) {
        this.mConfig = config;
        mapIconSets();
        updateTelephony();
    }

    public void setAirplaneMode(boolean airplaneMode) {
        ((MobileState) this.mCurrentState).airplaneMode = airplaneMode;
        notifyListenersIfNecessary();
    }

    public void setUserSetupComplete(boolean userSetup) {
        ((MobileState) this.mCurrentState).userSetup = userSetup;
        notifyListenersIfNecessary();
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void updateConnectivity(BitSet connectedTransports, BitSet validatedTransports) {
        boolean isValidated = validatedTransports.get(this.mTransportType);
        ((MobileState) this.mCurrentState).isDefault = connectedTransports.get(this.mTransportType) && this.mNetworkController.isCellularConnected(this.mSubscriptionInfo.getSubscriptionId());
        ((MobileState) this.mCurrentState).inetCondition = (isValidated || !((MobileState) this.mCurrentState).isDefault) ? 1 : 0;
        notifyListenersIfNecessary();
    }

    public void setCarrierNetworkChangeMode(boolean carrierNetworkChangeMode) {
        ((MobileState) this.mCurrentState).carrierNetworkChangeMode = carrierNetworkChangeMode;
        updateTelephony();
    }

    public void registerListener() {
        Log.d(this.mTag, "registerListener");
        registerImsListener();
        this.mPhone.listen(this.mPhoneStateListener, 70113);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data"), true, this.mObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data" + this.mSubscriptionInfo.getSubscriptionId()), true, this.mObserver);
        this.mStatusBarExt.registerOpStateListener();
    }

    public void unregisterListener() {
        Log.d(this.mTag, "unregisterListener");
        this.mPhone.listen(this.mPhoneStateListener, 0);
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
        unregisterImsListener();
    }

    private void mapIconSets() {
        this.mNetworkToIconLookup.clear();
        this.mNetworkToIconLookup.put(5, TelephonyIcons.THREE_G);
        this.mNetworkToIconLookup.put(6, TelephonyIcons.THREE_G);
        this.mNetworkToIconLookup.put(12, TelephonyIcons.THREE_G);
        this.mNetworkToIconLookup.put(14, TelephonyIcons.THREE_G);
        this.mNetworkToIconLookup.put(3, TelephonyIcons.THREE_G);
        this.mNetworkToIconLookup.put(17, TelephonyIcons.THREE_G);
        if (!this.mConfig.showAtLeast3G) {
            this.mNetworkToIconLookup.put(0, TelephonyIcons.UNKNOWN);
            this.mNetworkToIconLookup.put(2, TelephonyIcons.E);
            this.mNetworkToIconLookup.put(4, TelephonyIcons.ONE_X);
            this.mNetworkToIconLookup.put(7, TelephonyIcons.ONE_X);
            this.mDefaultIcons = TelephonyIcons.G;
        } else {
            this.mNetworkToIconLookup.put(0, TelephonyIcons.THREE_G);
            this.mNetworkToIconLookup.put(2, TelephonyIcons.THREE_G);
            this.mNetworkToIconLookup.put(4, TelephonyIcons.THREE_G);
            this.mNetworkToIconLookup.put(7, TelephonyIcons.THREE_G);
            this.mDefaultIcons = TelephonyIcons.THREE_G;
        }
        MobileIconGroup hGroup = TelephonyIcons.THREE_G;
        MobileIconGroup hPlusGroup = TelephonyIcons.THREE_G;
        if (this.mConfig.hspaDataDistinguishable) {
            hGroup = TelephonyIcons.H;
            hPlusGroup = TelephonyIcons.H_PLUS;
        }
        this.mNetworkToIconLookup.put(8, hGroup);
        this.mNetworkToIconLookup.put(9, hGroup);
        this.mNetworkToIconLookup.put(10, hGroup);
        this.mNetworkToIconLookup.put(15, hPlusGroup);
        if (this.mConfig.show4gForLte) {
            this.mNetworkToIconLookup.put(13, TelephonyIcons.FOUR_G);
            if (this.mConfig.hideLtePlus) {
                this.mNetworkToIconLookup.put(19, TelephonyIcons.FOUR_G);
            } else {
                this.mNetworkToIconLookup.put(19, TelephonyIcons.FOUR_G_PLUS);
            }
        } else {
            this.mNetworkToIconLookup.put(13, TelephonyIcons.LTE);
            if (this.mConfig.hideLtePlus) {
                this.mNetworkToIconLookup.put(19, TelephonyIcons.LTE);
            } else {
                this.mNetworkToIconLookup.put(19, TelephonyIcons.LTE_PLUS);
            }
        }
        this.mNetworkToIconLookup.put(18, TelephonyIcons.WFC);
    }

    private int getNumLevels() {
        if (this.mConfig.inflateSignalStrengths) {
            return 6;
        }
        return 5;
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public int getCurrentIconId() {
        if (((MobileState) this.mCurrentState).iconGroup == TelephonyIcons.CARRIER_NETWORK_CHANGE) {
            return SignalDrawable.getCarrierChangeState(getNumLevels());
        }
        if (((MobileState) this.mCurrentState).connected) {
            int level = ((MobileState) this.mCurrentState).level;
            if (this.mConfig.inflateSignalStrengths) {
                level++;
            }
            boolean dataDisabled = ((MobileState) this.mCurrentState).userSetup && ((MobileState) this.mCurrentState).iconGroup == TelephonyIcons.DATA_DISABLED;
            boolean noInternet = ((MobileState) this.mCurrentState).inetCondition == 0;
            boolean cutOut = dataDisabled || noInternet;
            return SignalDrawable.getState(level, getNumLevels(), cutOut);
        }
        if (((MobileState) this.mCurrentState).enabled) {
            return SignalDrawable.getEmptyState(getNumLevels());
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public int getQsCurrentIconId() {
        if (((MobileState) this.mCurrentState).airplaneMode) {
            return SignalDrawable.getAirplaneModeState(getNumLevels());
        }
        return getCurrentIconId();
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void notifyListeners(NetworkController.SignalCallback callback) {
        MobileIconGroup icons = getIcons();
        String contentDescription = getStringIfExists(getContentDescription());
        String dataContentDescription = getStringIfExists(icons.mDataContentDescription);
        if (((MobileState) this.mCurrentState).inetCondition == 0) {
            dataContentDescription = this.mContext.getString(R.string.data_connection_no_internet);
        }
        boolean dataDisabled = ((MobileState) this.mCurrentState).iconGroup == TelephonyIcons.DATA_DISABLED && ((MobileState) this.mCurrentState).userSetup;
        int iconId = getCurrentIconId();
        int iconId2 = this.mStatusBarExt.getCustomizeSignalStrengthIcon(this.mSubscriptionInfo.getSubscriptionId(), iconId, this.mSignalStrength, this.mDataNetType, this.mServiceState);
        boolean showDataIcon = ((MobileState) this.mCurrentState).dataConnected || dataDisabled;
        NetworkController.IconState statusIcon = new NetworkController.IconState(((MobileState) this.mCurrentState).enabled && !((MobileState) this.mCurrentState).airplaneMode, iconId2, contentDescription);
        int qsTypeIcon = 0;
        NetworkController.IconState qsIcon = null;
        String description = null;
        if (((MobileState) this.mCurrentState).dataSim) {
            qsTypeIcon = (showDataIcon || this.mConfig.alwaysShowDataRatIcon) ? icons.mQsDataType : 0;
            qsIcon = new NetworkController.IconState(((MobileState) this.mCurrentState).enabled && !((MobileState) this.mCurrentState).isEmergency, getQsCurrentIconId(), contentDescription);
            description = ((MobileState) this.mCurrentState).isEmergency ? null : ((MobileState) this.mCurrentState).networkName;
        }
        int qsTypeIcon2 = qsTypeIcon;
        NetworkController.IconState qsIcon2 = qsIcon;
        String description2 = description;
        boolean activityIn = ((MobileState) this.mCurrentState).dataConnected && !((MobileState) this.mCurrentState).carrierNetworkChangeMode && ((MobileState) this.mCurrentState).activityIn;
        boolean activityOut = ((MobileState) this.mCurrentState).dataConnected && !((MobileState) this.mCurrentState).carrierNetworkChangeMode && ((MobileState) this.mCurrentState).activityOut;
        boolean showDataIcon2 = showDataIcon & (((MobileState) this.mCurrentState).isDefault || dataDisabled);
        int typeIcon = (showDataIcon2 || this.mConfig.alwaysShowDataRatIcon) ? icons.mDataType : 0;
        int typeIcon2 = (((MobileState) this.mCurrentState).lwaRegState == 1 && showDataIcon2) ? R.drawable.stat_sys_data_fully_connected_4gaw : typeIcon;
        int networkIcon = ((MobileState) this.mCurrentState).networkIcon;
        int volteIcon = (!((MobileState) this.mCurrentState).airplaneMode || isImsOverWfc()) ? ((MobileState) this.mCurrentState).volteIcon : 0;
        this.mStatusBarExt.isDataDisabled(this.mSubscriptionInfo.getSubscriptionId(), dataDisabled);
        callback.setMobileDataIndicators(statusIcon, qsIcon2, this.mStatusBarExt.getDataTypeIcon(this.mSubscriptionInfo.getSubscriptionId(), typeIcon2, this.mDataNetType, ((MobileState) this.mCurrentState).dataConnected ? 2 : 0, this.mServiceState), this.mStatusBarExt.getNetworkTypeIcon(this.mSubscriptionInfo.getSubscriptionId(), networkIcon, this.mDataNetType, this.mServiceState), volteIcon, qsTypeIcon2, activityIn, activityOut, dataContentDescription, description2, icons.mIsWide, this.mSubscriptionInfo.getSubscriptionId(), ((MobileState) this.mCurrentState).roaming, ((MobileState) this.mCurrentState).isDefaultData);
        this.mNetworkController.refreshPlmnCarrierLabel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.policy.SignalController
    public MobileState cleanState() {
        return new MobileState();
    }

    private boolean hasService() {
        if (this.mServiceState == null) {
            return false;
        }
        switch (this.mServiceState.getVoiceRegState()) {
            case 1:
            case 2:
                return this.mServiceState.getDataRegState() == 0;
            case 3:
                return false;
            default:
                return true;
        }
    }

    private boolean isCdma() {
        return (this.mSignalStrength == null || this.mSignalStrength.isGsm()) ? false : true;
    }

    public boolean isEmergencyOnly() {
        return this.mServiceState != null && this.mServiceState.isEmergencyOnly();
    }

    private boolean isRoaming() {
        PersistableBundle carrierConfig;
        boolean z = false;
        if (isCarrierNetworkChangeActive()) {
            return false;
        }
        if (isCdma() && this.mServiceState != null) {
            int iconMode = this.mServiceState.getCdmaEriIconMode();
            if (this.mServiceState.getCdmaEriIconIndex() != 1) {
                return iconMode == 0 || iconMode == 1;
            }
            return false;
        }
        if (this.mServiceState != null && this.mServiceState.getRoaming()) {
            z = true;
        }
        boolean isInRoaming = z;
        if (isInRoaming && (carrierConfig = this.mCarrierConfigManager.getConfigForSubId(this.mSubscriptionInfo.getSubscriptionId())) != null) {
            isInRoaming = carrierConfig.getBoolean("mtk_key_carrier_need_show_roaming_icon");
            if (DEBUG) {
                Log.d(this.mTag, "get roaming state from MtkCarrierConfigManager: " + isInRoaming);
            }
        }
        return isInRoaming;
    }

    private boolean isCarrierNetworkChangeActive() {
        return ((MobileState) this.mCurrentState).carrierNetworkChangeMode;
    }

    public void handleBroadcast(Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.provider.Telephony.SPN_STRINGS_UPDATED")) {
            updateNetworkName(intent.getBooleanExtra("showSpn", false), intent.getStringExtra("spn"), intent.getStringExtra("spnData"), intent.getBooleanExtra("showPlmn", false), intent.getStringExtra("plmn"));
            notifyListenersIfNecessary();
        } else if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
            updateDataSim();
            notifyListenersIfNecessary();
        } else if (action.equals("com.mediatek.server.lwa.LWA_STATE_CHANGE_ACTION")) {
            handleLwaAction(intent);
            notifyListenersIfNecessary();
        }
    }

    private void handleLwaAction(Intent intent) {
        ((MobileState) this.mCurrentState).lwaRegState = intent.getIntExtra("com.mediatek.server.lwa.EXTRA_STATE", -1);
    }

    private void updateDataSim() {
        int defaultDataSub = this.mDefaults.getDefaultDataSubId();
        if (SubscriptionManager.isValidSubscriptionId(defaultDataSub)) {
            ((MobileState) this.mCurrentState).dataSim = defaultDataSub == this.mSubscriptionInfo.getSubscriptionId();
        } else {
            ((MobileState) this.mCurrentState).dataSim = true;
        }
        if (this.mStatusBarExt.disableHostFunction()) {
            ((MobileState) this.mCurrentState).isDefaultData = defaultDataSub == this.mSubscriptionInfo.getSubscriptionId();
        }
    }

    void updateNetworkName(boolean showSpn, String spn, String dataSpn, boolean showPlmn, String plmn) {
        if (CHATTY) {
            Log.d("CarrierLabel", "updateNetworkName showSpn=" + showSpn + " spn=" + spn + " dataSpn=" + dataSpn + " showPlmn=" + showPlmn + " plmn=" + plmn);
        }
        StringBuilder str = new StringBuilder();
        StringBuilder strData = new StringBuilder();
        if (showPlmn && plmn != null) {
            str.append(plmn);
            strData.append(plmn);
        }
        if (showSpn && spn != null) {
            if (str.length() != 0) {
                str.append(this.mNetworkNameSeparator);
            }
            str.append(spn);
        }
        if (str.length() != 0) {
            ((MobileState) this.mCurrentState).networkName = str.toString();
        } else {
            ((MobileState) this.mCurrentState).networkName = this.mNetworkNameDefault;
        }
        if (showSpn && dataSpn != null) {
            if (strData.length() != 0) {
                strData.append(this.mNetworkNameSeparator);
            }
            strData.append(dataSpn);
        }
        if (strData.length() == 0 && showSpn && spn != null) {
            Log.d("CarrierLabel", "show spn instead 'no service' here: " + spn);
            strData.append(spn);
        }
        if (strData.length() != 0) {
            ((MobileState) this.mCurrentState).networkNameData = strData.toString();
        } else {
            ((MobileState) this.mCurrentState).networkNameData = this.mNetworkNameDefault;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTelephony() {
        if (DEBUG && FeatureOptions.LOG_ENABLE) {
            Log.d(this.mTag, "updateTelephonySignalStrength: hasService=" + hasService() + ", ss=" + this.mSignalStrength + ", mConfig.alwaysShowDataRatIcon = " + this.mConfig.alwaysShowDataRatIcon + ", mDataState = " + this.mDataState);
        }
        boolean z = false;
        ((MobileState) this.mCurrentState).connected = hasService() && this.mSignalStrength != null;
        handleIWLANNetwork();
        if (((MobileState) this.mCurrentState).connected) {
            if (!this.mSignalStrength.isGsm() && this.mConfig.alwaysShowCdmaRssi) {
                ((MobileState) this.mCurrentState).level = this.mSignalStrength.getCdmaLevel();
            } else {
                ((MobileState) this.mCurrentState).level = this.mSignalStrength.getLevel();
            }
            ((MobileState) this.mCurrentState).level = this.mStatusBarExt.getCustomizeSignalStrengthLevel(((MobileState) this.mCurrentState).level, this.mSignalStrength, this.mServiceState);
        }
        if (this.mNetworkToIconLookup.indexOfKey(this.mDataNetType) >= 0) {
            ((MobileState) this.mCurrentState).iconGroup = this.mNetworkToIconLookup.get(this.mDataNetType);
        } else {
            ((MobileState) this.mCurrentState).iconGroup = this.mDefaultIcons;
        }
        ((MobileState) this.mCurrentState).dataNetType = this.mDataNetType;
        MobileState mobileState = (MobileState) this.mCurrentState;
        if (((MobileState) this.mCurrentState).connected && this.mDataState == 2) {
            z = true;
        }
        mobileState.dataConnected = z;
        ((MobileState) this.mCurrentState).customizedState = this.mStatusBarExt.getCustomizeCsState(this.mServiceState, ((MobileState) this.mCurrentState).customizedState);
        ((MobileState) this.mCurrentState).isInCsCall = this.mStatusBarExt.isInCsCall();
        ((MobileState) this.mCurrentState).customizedSignalStrengthIcon = this.mStatusBarExt.getCustomizeSignalStrengthIcon(this.mSubscriptionInfo.getSubscriptionId(), ((MobileState) this.mCurrentState).customizedSignalStrengthIcon, this.mSignalStrength, this.mDataNetType, this.mServiceState);
        ((MobileState) this.mCurrentState).roaming = isRoaming();
        if (isCarrierNetworkChangeActive()) {
            ((MobileState) this.mCurrentState).iconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
        } else if (isDataDisabled() && !this.mConfig.alwaysShowDataRatIcon) {
            ((MobileState) this.mCurrentState).iconGroup = TelephonyIcons.DATA_DISABLED;
        }
        if (isEmergencyOnly() != ((MobileState) this.mCurrentState).isEmergency) {
            ((MobileState) this.mCurrentState).isEmergency = isEmergencyOnly();
            this.mNetworkController.recalculateEmergency();
        }
        if (((MobileState) this.mCurrentState).networkName == this.mNetworkNameDefault && this.mServiceState != null && !TextUtils.isEmpty(this.mServiceState.getOperatorAlphaShort())) {
            ((MobileState) this.mCurrentState).networkName = this.mServiceState.getOperatorAlphaShort();
        }
        ((MobileState) this.mCurrentState).networkIcon = NetworkTypeUtils.getNetworkTypeIcon(this.mServiceState, this.mConfig, hasService());
        ((MobileState) this.mCurrentState).volteIcon = getVolteIcon();
        notifyListenersIfNecessary();
    }

    private boolean isDataDisabled() {
        boolean isDataDisabled = !this.mPhone.getDataEnabled(this.mSubscriptionInfo.getSubscriptionId());
        if (DEBUG) {
            Log.d(this.mTag, "isDataDisabled = " + isDataDisabled);
        }
        return isDataDisabled;
    }

    private void handleIWLANNetwork() {
        if (((MobileState) this.mCurrentState).connected && this.mServiceState != null && this.mServiceState.getDataNetworkType() == 18 && this.mServiceState.getCellularRegState() != 0) {
            Log.d(this.mTag, "Current is IWLAN network only, no cellular network available");
            ((MobileState) this.mCurrentState).connected = false;
        }
    }

    @VisibleForTesting
    void setActivity(int activity) {
        ((MobileState) this.mCurrentState).activityIn = activity == 3 || activity == 1;
        ((MobileState) this.mCurrentState).activityOut = activity == 3 || activity == 2;
        notifyListenersIfNecessary();
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void dump(PrintWriter pw) {
        super.dump(pw);
        pw.println("  mSubscription=" + this.mSubscriptionInfo + ",");
        pw.println("  mServiceState=" + this.mServiceState + ",");
        pw.println("  mSignalStrength=" + this.mSignalStrength + ",");
        pw.println("  mDataState=" + this.mDataState + ",");
        pw.println("  mDataNetType=" + this.mDataNetType + ",");
    }

    class MobilePhoneStateListener extends PhoneStateListener {
        public MobilePhoneStateListener(int subId, Looper looper) {
            super(Integer.valueOf(subId), looper);
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            String str;
            if (SignalController.DEBUG) {
                String str2 = MobileSignalController.this.mTag;
                StringBuilder sb = new StringBuilder();
                sb.append("onSignalStrengthsChanged signalStrength=");
                sb.append(signalStrength);
                if (signalStrength == null) {
                    str = "";
                } else {
                    str = " level=" + signalStrength.getLevel();
                }
                sb.append(str);
                Log.d(str2, sb.toString());
            }
            MobileSignalController.this.mSignalStrength = signalStrength;
            MobileSignalController.this.updateTelephony();
        }

        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState state) {
            if (SignalController.DEBUG) {
                Log.d(MobileSignalController.this.mTag, "onServiceStateChanged: " + MobileSignalController.this.dumpServiceState(state));
            }
            MobileSignalController.this.mServiceState = state;
            if (state != null) {
                MobileSignalController.this.mDataNetType = state.getDataNetworkType();
                if (MobileSignalController.this.mDataNetType == 13 && MobileSignalController.this.mServiceState != null && MobileSignalController.this.mServiceState.isUsingCarrierAggregation()) {
                    MobileSignalController.this.mDataNetType = 19;
                }
            }
            MobileSignalController.this.updateTelephony();
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int state, int networkType) {
            if (SignalController.DEBUG) {
                Log.d(MobileSignalController.this.mTag, "onDataConnectionStateChanged: state=" + state + " type=" + networkType);
            }
            MobileSignalController.this.mDataState = state;
            MobileSignalController.this.mDataNetType = networkType;
            if (MobileSignalController.this.mDataNetType == 13 && MobileSignalController.this.mServiceState != null && MobileSignalController.this.mServiceState.isUsingCarrierAggregation()) {
                MobileSignalController.this.mDataNetType = 19;
            }
            MobileSignalController.this.updateTelephony();
        }

        @Override // android.telephony.PhoneStateListener
        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState dataConnectionState) {
            if (dataConnectionState == null) {
                Log.w(MobileSignalController.this.mTag, "onPreciseDataConnectionStateChanged: dataConnectionState is null");
                return;
            }
            String apnType = dataConnectionState.getDataConnectionAPNType();
            if (apnType != null && apnType.equals("preempt") && dataConnectionState.getDataConnectionState() != -1) {
                if (SignalController.DEBUG) {
                    Log.d(MobileSignalController.this.mTag, "onPreciseDataConnectionStateChanged: dataConnectionState=" + dataConnectionState);
                }
                MobileSignalController.this.mDataState = dataConnectionState.getDataConnectionState();
                MobileSignalController.this.mDataNetType = dataConnectionState.getDataConnectionNetworkType();
                if (MobileSignalController.this.mDataNetType == 13 && MobileSignalController.this.mServiceState != null && MobileSignalController.this.mServiceState.isUsingCarrierAggregation()) {
                    MobileSignalController.this.mDataNetType = 19;
                }
                MobileSignalController.this.updateTelephony();
                return;
            }
            if (apnType != null && apnType.equals("default") && MobileSignalController.this.mDataState != dataConnectionState.getDataConnectionState()) {
                if (SignalController.DEBUG) {
                    Log.d(MobileSignalController.this.mTag, "onPreciseDataConnectionStateChanged: APN_TYPE_DEFAULT, dataConnectionState=" + dataConnectionState);
                }
                MobileSignalController.this.mDataState = dataConnectionState.getDataConnectionState();
                MobileSignalController.this.updateTelephony();
            }
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataActivity(int direction) {
            if (SignalController.DEBUG && FeatureOptions.LOG_ENABLE) {
                Log.d(MobileSignalController.this.mTag, "onDataActivity: direction=" + direction);
            }
            MobileSignalController.this.setActivity(direction);
        }

        public void onCarrierNetworkChange(boolean active) {
            if (SignalController.DEBUG && FeatureOptions.LOG_ENABLE) {
                Log.d(MobileSignalController.this.mTag, "onCarrierNetworkChange: active=" + active);
            }
            ((MobileState) MobileSignalController.this.mCurrentState).carrierNetworkChangeMode = active;
            MobileSignalController.this.updateTelephony();
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int state, String incomingNumber) {
            boolean needHandle = MobileSignalController.this.mStatusBarExt.handleCallStateChanged(MobileSignalController.this.mSubscriptionInfo.getSubscriptionId(), state, incomingNumber, MobileSignalController.this.mServiceState);
            if (needHandle) {
                MobileSignalController.this.updateTelephony();
            }
        }
    }

    static class MobileIconGroup extends SignalController.IconGroup {
        final int mDataContentDescription;
        final int mDataType;
        final boolean mIsWide;
        final int mQsDataType;

        public MobileIconGroup(String name, int[][] sbIcons, int[][] qsIcons, int[] contentDesc, int sbNullState, int qsNullState, int sbDiscState, int qsDiscState, int discContentDesc, int dataContentDesc, int dataType, boolean isWide) {
            super(name, sbIcons, qsIcons, contentDesc, sbNullState, qsNullState, sbDiscState, qsDiscState, discContentDesc);
            this.mDataContentDescription = dataContentDesc;
            this.mDataType = dataType;
            this.mIsWide = isWide;
            this.mQsDataType = dataType;
        }
    }

    static class MobileState extends SignalController.State {
        boolean airplaneMode;
        boolean carrierNetworkChangeMode;
        int customizedSignalStrengthIcon;
        int customizedState;
        boolean dataConnected;
        int dataNetType;
        boolean dataSim;
        boolean isDefault;
        boolean isDefaultData;
        boolean isEmergency;
        boolean isInCsCall;
        int networkIcon;
        String networkName;
        String networkNameData;
        boolean roaming;
        boolean userSetup;
        int volteIcon;
        int lwaRegState = -1;
        int imsRegState = 3;
        int imsCap = -1;

        MobileState() {
        }

        @Override // com.android.systemui.statusbar.policy.SignalController.State
        public void copyFrom(SignalController.State s) {
            super.copyFrom(s);
            MobileState state = (MobileState) s;
            this.dataSim = state.dataSim;
            this.networkName = state.networkName;
            this.networkNameData = state.networkNameData;
            this.dataConnected = state.dataConnected;
            this.isDefault = state.isDefault;
            this.isEmergency = state.isEmergency;
            this.airplaneMode = state.airplaneMode;
            this.carrierNetworkChangeMode = state.carrierNetworkChangeMode;
            this.userSetup = state.userSetup;
            this.networkIcon = state.networkIcon;
            this.dataNetType = state.dataNetType;
            this.customizedState = state.customizedState;
            this.isInCsCall = state.isInCsCall;
            this.isDefaultData = state.isDefaultData;
            this.customizedSignalStrengthIcon = state.customizedSignalStrengthIcon;
            this.imsRegState = state.imsRegState;
            this.imsCap = state.imsCap;
            this.volteIcon = state.volteIcon;
            this.roaming = state.roaming;
            this.lwaRegState = state.lwaRegState;
        }

        @Override // com.android.systemui.statusbar.policy.SignalController.State
        protected void toString(StringBuilder builder) {
            super.toString(builder);
            builder.append(',');
            builder.append("dataSim=");
            builder.append(this.dataSim);
            builder.append(',');
            builder.append("networkName=");
            builder.append(this.networkName);
            builder.append(',');
            builder.append("networkNameData=");
            builder.append(this.networkNameData);
            builder.append(',');
            builder.append("dataConnected=");
            builder.append(this.dataConnected);
            builder.append(',');
            builder.append("roaming=");
            builder.append(this.roaming);
            builder.append(',');
            builder.append("isDefault=");
            builder.append(this.isDefault);
            builder.append(',');
            builder.append("isEmergency=");
            builder.append(this.isEmergency);
            builder.append(',');
            builder.append("airplaneMode=");
            builder.append(this.airplaneMode);
            builder.append(',');
            builder.append("lwaRegState=");
            builder.append(this.lwaRegState);
            builder.append(',');
            builder.append("carrierNetworkChangeMode=");
            builder.append(this.carrierNetworkChangeMode);
            builder.append(',');
            builder.append("userSetup=");
            builder.append(this.userSetup);
            builder.append(",");
            builder.append("networkIcon=");
            builder.append(this.networkIcon);
            builder.append(',');
            builder.append("dataNetType=");
            builder.append(this.dataNetType);
            builder.append(',');
            builder.append("customizedState=");
            builder.append(this.customizedState);
            builder.append(',');
            builder.append("isInCsCall=");
            builder.append(this.isInCsCall);
            builder.append(',');
            builder.append("isDefaultData=");
            builder.append(this.isDefaultData);
            builder.append(',');
            builder.append("customizedSignalStrengthIcon=");
            builder.append(this.customizedSignalStrengthIcon);
            builder.append(',');
            builder.append("imsRegState=");
            builder.append(this.imsRegState);
            builder.append(',');
            builder.append("imsCap=");
            builder.append(this.imsCap);
            builder.append(',');
            builder.append("volteIconId=");
            builder.append(this.volteIcon);
            builder.append(',');
            builder.append("carrierNetworkChangeMode=");
            builder.append(this.carrierNetworkChangeMode);
        }

        @Override // com.android.systemui.statusbar.policy.SignalController.State
        public boolean equals(Object o) {
            return super.equals(o) && Objects.equals(((MobileState) o).networkName, this.networkName) && Objects.equals(((MobileState) o).networkNameData, this.networkNameData) && ((MobileState) o).dataSim == this.dataSim && ((MobileState) o).dataConnected == this.dataConnected && ((MobileState) o).isEmergency == this.isEmergency && ((MobileState) o).airplaneMode == this.airplaneMode && ((MobileState) o).carrierNetworkChangeMode == this.carrierNetworkChangeMode && ((MobileState) o).lwaRegState == this.lwaRegState && ((MobileState) o).networkIcon == this.networkIcon && ((MobileState) o).volteIcon == this.volteIcon && ((MobileState) o).dataNetType == this.dataNetType && ((MobileState) o).customizedState == this.customizedState && ((MobileState) o).isInCsCall == this.isInCsCall && ((MobileState) o).isDefaultData == this.isDefaultData && ((MobileState) o).customizedSignalStrengthIcon == this.customizedSignalStrengthIcon && ((MobileState) o).userSetup == this.userSetup && ((MobileState) o).isDefault == this.isDefault && ((MobileState) o).roaming == this.roaming;
        }
    }

    public SubscriptionInfo getControllerSubInfo() {
        return this.mSubscriptionInfo;
    }

    public boolean getControllserHasService() {
        return hasService();
    }

    public void registerImsListener() {
        Log.i(this.mTag, "registerImsListener >>>");
        this.mImsManager = ImsManager.getInstance(this.mContext, this.mSubscriptionInfo.getSimSlotIndex());
        unregisterImsListener();
        try {
            this.mImsManager.addImsConnectionStateListener(this.mImsConnectionStateListener);
            Log.i(this.mTag, "register ims succeed, " + this.mImsConnectionStateListener);
        } catch (ImsException e) {
            Log.w(this.mTag, "register ims fail!");
        }
    }

    public void unregisterImsListener() {
        if (this.mImsManager != null) {
            try {
                this.mImsManager.removeImsConnectionStateListener(this.mImsConnectionStateListener);
                Log.i(this.mTag, "unregister ims succeed, " + this.mImsConnectionStateListener);
            } catch (ImsException e) {
                Log.w(this.mTag, "unregister ims fail!");
            }
        }
    }

    public String dumpServiceState(ServiceState state) {
        if (state == null) {
            return "ServiceState is null";
        }
        return "[ServiceState]dataNetworkType = " + state.getDataNetworkType() + ", voiceNetworkType = " + state.getVoiceNetworkType() + ", dataRegState = " + state.getDataRegState() + ", voiceRegState = " + state.getVoiceRegState() + ", isUsingCarrierAggregation = " + state.isUsingCarrierAggregation() + ", cdmaEriIconMode = " + state.getCdmaEriIconMode() + ", cdmaEriIconIndex = " + state.getCdmaEriIconIndex() + ", roaming = " + state.getRoaming();
    }

    public String toString() {
        return "[MobileSignalController: subid = " + this.mSubscriptionInfo.getSubscriptionId() + ", phoneId = " + this.mSubscriptionInfo.getSimSlotIndex() + "] " + super.toString();
    }
}
