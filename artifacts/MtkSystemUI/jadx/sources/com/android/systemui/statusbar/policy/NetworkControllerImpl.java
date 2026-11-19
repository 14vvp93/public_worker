package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkSpecifier;
import android.net.StringNetworkSpecifier;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.ConfigurationChangedReceiver;
import com.android.systemui.DemoMode;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.util.Utils;
import com.mediatek.systemui.ext.IStatusBarPlmnPlugin;
import com.mediatek.systemui.ext.ISystemUIStatusBarExt;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import com.mediatek.systemui.statusbar.util.SIMHelper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class NetworkControllerImpl extends BroadcastReceiver implements DataUsageController.NetworkNameProvider, ConfigurationChangedReceiver, DemoMode, Dumpable, NetworkController {
    static final boolean CHATTY;
    static final boolean DEBUG;
    private boolean hasStartedAppStore;
    private final AccessPointControllerImpl mAccessPoints;
    private boolean mAirplaneMode;
    private final CallbackHandler mCallbackHandler;
    private int mCellularSubId;
    private Config mConfig;
    private final BitSet mConnectedTransports;
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private List<SubscriptionInfo> mCurrentSubscriptions;
    private int mCurrentUserId;
    private final DataSaverController mDataSaverController;
    private final DataUsageController mDataUsageController;
    private MobileSignalController mDefaultSignalController;
    private boolean mDemoInetCondition;
    private boolean mDemoMode;
    private WifiSignalController.WifiState mDemoWifiState;
    private boolean[] mEmergencyPhone;
    private int mEmergencySource;

    @VisibleForTesting
    final EthernetSignalController mEthernetSignalController;
    private final boolean mHasMobileDataFeature;
    private boolean mHasNoSubs;
    private boolean mInetCondition;
    private boolean mIsEmergency;

    @VisibleForTesting
    ServiceState mLastServiceState;

    @VisibleForTesting
    boolean mListening;
    private Locale mLocale;

    @VisibleForTesting
    final SparseArray<MobileSignalController> mMobileSignalControllers;
    String[] mNetworkName;
    private final TelephonyManager mPhone;
    private final Handler mReceiverHandler;
    private final Runnable mRegisterListeners;
    private int mSignalCallbackCount;
    private boolean mSimDetected;
    int mSlotCount;
    private IStatusBarPlmnPlugin mStatusBarPlmnPlugin;
    private ISystemUIStatusBarExt mStatusBarSystemUIExt;
    private final SubscriptionDefaults mSubDefaults;
    private SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionListener;
    private final SubscriptionManager mSubscriptionManager;
    private boolean mUserSetup;
    private final CurrentUserTracker mUserTracker;
    private final BitSet mValidatedTransports;
    private final WifiManager mWifiManager;

    @VisibleForTesting
    WifiSignalController mWifiSignalController;

    static {
        boolean z = true;
        DEBUG = Log.isLoggable("NetworkController", 3) || FeatureOptions.LOG_ENABLE;
        if (!Log.isLoggable("NetworkControllerChat", 3) && !FeatureOptions.LOG_ENABLE) {
            z = false;
        }
        CHATTY = z;
    }

    public NetworkControllerImpl(Context context, Looper bgLooper, DeviceProvisionedController deviceProvisionedController) {
        this(context, (ConnectivityManager) context.getSystemService("connectivity"), (TelephonyManager) context.getSystemService("phone"), (WifiManager) context.getSystemService("wifi"), SubscriptionManager.from(context), Config.readConfig(context), bgLooper, new CallbackHandler(), new AccessPointControllerImpl(context), new DataUsageController(context), new SubscriptionDefaults(), deviceProvisionedController);
        this.mReceiverHandler.post(this.mRegisterListeners);
    }

    @VisibleForTesting
    NetworkControllerImpl(Context context, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, WifiManager wifiManager, SubscriptionManager subManager, Config config, Looper bgLooper, CallbackHandler callbackHandler, AccessPointControllerImpl accessPointController, DataUsageController dataUsageController, SubscriptionDefaults defaultsHandler, final DeviceProvisionedController deviceProvisionedController) {
        this.hasStartedAppStore = false;
        this.mWifiSignalController = null;
        this.mMobileSignalControllers = new SparseArray<>();
        this.mConnectedTransports = new BitSet();
        this.mValidatedTransports = new BitSet();
        this.mAirplaneMode = false;
        this.mLocale = null;
        this.mCurrentSubscriptions = new ArrayList();
        this.mCellularSubId = -1;
        this.mSlotCount = 0;
        this.mRegisterListeners = new Runnable() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.8
            @Override // java.lang.Runnable
            public void run() {
                NetworkControllerImpl.this.registerListeners();
            }
        };
        this.mContext = context;
        this.mConfig = config;
        this.mReceiverHandler = new Handler(bgLooper);
        this.mCallbackHandler = callbackHandler;
        this.mDataSaverController = new DataSaverControllerImpl(context);
        this.mSubscriptionManager = subManager;
        this.mSubDefaults = defaultsHandler;
        this.mConnectivityManager = connectivityManager;
        this.mHasMobileDataFeature = this.mConnectivityManager.isNetworkSupported(0);
        this.mSlotCount = SIMHelper.getSlotCount();
        this.mNetworkName = new String[this.mSlotCount];
        this.mEmergencyPhone = new boolean[this.mSlotCount];
        this.mPhone = telephonyManager;
        this.mWifiManager = wifiManager;
        this.mLocale = this.mContext.getResources().getConfiguration().locale;
        this.mAccessPoints = accessPointController;
        this.mDataUsageController = dataUsageController;
        this.mDataUsageController.setNetworkController(this);
        this.mDataUsageController.setCallback(new DataUsageController.Callback() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.1
            @Override // com.android.settingslib.net.DataUsageController.Callback
            public void onMobileDataEnabled(boolean enabled) {
                NetworkControllerImpl.this.mCallbackHandler.setMobileDataEnabled(enabled);
            }
        });
        if (this.mWifiManager != null) {
            this.mWifiSignalController = new WifiSignalController(this.mContext, this.mHasMobileDataFeature, this.mCallbackHandler, this, this.mWifiManager);
        }
        this.mEthernetSignalController = new EthernetSignalController(this.mContext, this.mCallbackHandler, this);
        updateAirplaneMode(true);
        this.mUserTracker = new CurrentUserTracker(this.mContext) { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.2
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int newUserId) {
                NetworkControllerImpl.this.onUserSwitched(newUserId);
            }
        };
        this.mUserTracker.startTracking();
        deviceProvisionedController.addCallback(new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.3
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public void onUserSetupChanged() {
                NetworkControllerImpl.this.setUserSetupComplete(deviceProvisionedController.isUserSetup(deviceProvisionedController.getCurrentUser()));
            }
        });
        ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.4
            private Network mLastNetwork;
            private NetworkCapabilities mLastNetworkCapabilities;

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                boolean lastValidated = this.mLastNetworkCapabilities != null && this.mLastNetworkCapabilities.hasCapability(16);
                boolean validated = networkCapabilities.hasCapability(16);
                if (network.equals(this.mLastNetwork) && networkCapabilities.equalsTransportTypes(this.mLastNetworkCapabilities) && validated == lastValidated) {
                    return;
                }
                this.mLastNetwork = network;
                this.mLastNetworkCapabilities = networkCapabilities;
                NetworkControllerImpl.this.updateConnectivity();
            }
        };
        this.mConnectivityManager.registerDefaultNetworkCallback(callback, this.mReceiverHandler);
        this.mStatusBarSystemUIExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeSystemUIStatusBar(context);
        this.mStatusBarPlmnPlugin = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeStatusBarPlmn(context);
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public DataSaverController getDataSaverController() {
        return this.mDataSaverController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerListeners() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
            mobileSignalController.registerListener();
        }
        if (this.mSubscriptionListener == null) {
            this.mSubscriptionListener = new SubListener();
        }
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.RSSI_CHANGED");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        filter.addAction("android.intent.action.SIM_STATE_CHANGED");
        filter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        filter.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        filter.addAction("android.intent.action.SERVICE_STATE");
        filter.addAction("android.provider.Telephony.SPN_STRINGS_UPDATED");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.conn.INET_CONDITION_ACTION");
        filter.addAction("android.intent.action.AIRPLANE_MODE");
        filter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        addCustomizedAction(filter);
        this.mContext.registerReceiver(this, filter, null, this.mReceiverHandler);
        this.mListening = true;
        updateMobileControllers();
    }

    private void addCustomizedAction(IntentFilter filter) {
        filter.addAction("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED");
        filter.addAction("com.mediatek.server.lwa.LWA_STATE_CHANGE_ACTION");
        filter.addAction("com.mediatek.ims.MTK_IMS_SERVICE_UP");
    }

    private void unregisterListeners() {
        this.mListening = false;
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
            mobileSignalController.unregisterListener();
        }
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this.mSubscriptionListener);
        this.mContext.unregisterReceiver(this);
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public NetworkController.AccessPointController getAccessPointController() {
        return this.mAccessPoints;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public DataUsageController getMobileDataController() {
        return this.mDataUsageController;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public void addEmergencyListener(NetworkController.EmergencyListener listener) {
        this.mCallbackHandler.setListening(listener, true);
        this.mCallbackHandler.setEmergencyCallsOnly(isEmergencyOnly());
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public void removeEmergencyListener(NetworkController.EmergencyListener listener) {
        this.mCallbackHandler.setListening(listener, false);
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public boolean hasMobileDataFeature() {
        return this.mHasMobileDataFeature;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public boolean hasVoiceCallingFeature() {
        return this.mPhone.getPhoneType() != 0;
    }

    private MobileSignalController getDataController() {
        int dataSubId = this.mSubDefaults.getDefaultDataSubId();
        if (!SubscriptionManager.isValidSubscriptionId(dataSubId)) {
            if (DEBUG) {
                Log.e("NetworkController", "No data sim selected");
            }
            return this.mDefaultSignalController;
        }
        if (this.mMobileSignalControllers.indexOfKey(dataSubId) >= 0) {
            return this.mMobileSignalControllers.get(dataSubId);
        }
        if (DEBUG) {
            Log.e("NetworkController", "Cannot find controller for data sub: " + dataSubId);
        }
        return this.mDefaultSignalController;
    }

    @Override // com.android.settingslib.net.DataUsageController.NetworkNameProvider, com.android.systemui.statusbar.policy.NetworkController
    public String getMobileDataNetworkName() {
        MobileSignalController controller = getDataController();
        return controller != null ? controller.getState().networkNameData : "";
    }

    public boolean isEmergencyOnly() {
        if (this.mMobileSignalControllers.size() == 0) {
            Log.d("NetworkController", "isEmergencyOnly No sims ");
            this.mEmergencySource = 0;
            for (int i = 0; i < this.mEmergencyPhone.length; i++) {
                if (this.mEmergencyPhone[i]) {
                    if (DEBUG) {
                        Log.d("NetworkController", "Found emergency in phone " + i);
                    }
                    return true;
                }
            }
            return false;
        }
        int voiceSubId = this.mSubDefaults.getDefaultVoiceSubId();
        if (!SubscriptionManager.isValidSubscriptionId(voiceSubId)) {
            for (int i2 = 0; i2 < this.mMobileSignalControllers.size(); i2++) {
                MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i2);
                if (!mobileSignalController.getState().isEmergency) {
                    this.mEmergencySource = 100 + mobileSignalController.mSubscriptionInfo.getSubscriptionId();
                    if (DEBUG) {
                        Log.d("NetworkController", "Found emergency " + mobileSignalController.mTag);
                    }
                    return false;
                }
            }
        }
        if (this.mMobileSignalControllers.indexOfKey(voiceSubId) >= 0) {
            this.mEmergencySource = 200 + voiceSubId;
            if (DEBUG) {
                Log.d("NetworkController", "Getting emergency from " + voiceSubId);
            }
            return this.mMobileSignalControllers.get(voiceSubId).getState().isEmergency;
        }
        if (this.mMobileSignalControllers.size() == 1) {
            this.mEmergencySource = 400 + this.mMobileSignalControllers.keyAt(0);
            if (DEBUG) {
                Log.d("NetworkController", "Getting assumed emergency from " + this.mMobileSignalControllers.keyAt(0));
            }
            return this.mMobileSignalControllers.valueAt(0).getState().isEmergency;
        }
        if (DEBUG) {
            Log.e("NetworkController", "Cannot find controller for voice sub: " + voiceSubId);
        }
        this.mEmergencySource = 300 + voiceSubId;
        return true;
    }

    void recalculateEmergency() {
        this.mIsEmergency = isEmergencyOnly();
        this.mCallbackHandler.setEmergencyCallsOnly(this.mIsEmergency);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(NetworkController.SignalCallback cb) {
        if (CHATTY) {
            Log.d("NetworkController", "addCallback, cb = " + cb + ", mCurrentSubscriptions = " + this.mCurrentSubscriptions);
        }
        cb.setSubs(this.mCurrentSubscriptions);
        cb.setIsAirplaneMode(new NetworkController.IconState(this.mAirplaneMode, R.drawable.stat_sys_airplane_mode, R.string.accessibility_airplane_mode, this.mContext));
        if (this.mWifiSignalController != null) {
            this.mWifiSignalController.notifyListeners(cb);
        }
        this.mEthernetSignalController.notifyListeners(cb);
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
            mobileSignalController.notifyListeners(cb);
        }
        this.mCallbackHandler.setListening(cb, true);
        cb.setNoSims(this.mHasNoSubs, this.mSimDetected);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(NetworkController.SignalCallback cb) {
        if (CHATTY) {
            Log.d("NetworkController", "removeCallback, cb = " + cb);
        }
        this.mCallbackHandler.setListening(cb, false);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.policy.NetworkControllerImpl$5] */
    @Override // com.android.systemui.statusbar.policy.NetworkController
    public void setWifiEnabled(final boolean enabled) {
        new AsyncTask<Void, Void, Void>() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Void doInBackground(Void... args) {
                NetworkControllerImpl.this.mWifiManager.setWifiEnabled(enabled);
                return null;
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserSwitched(int newUserId) {
        this.mCurrentUserId = newUserId;
        this.mAccessPoints.onUserSwitched(newUserId);
        updateConnectivity();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (CHATTY) {
            Log.d("NetworkController", "onReceive: intent=" + intent);
        }
        String action = intent.getAction();
        int i = 0;
        switch (action) {
            case "android.net.conn.CONNECTIVITY_CHANGE":
            case "android.net.conn.INET_CONDITION_ACTION":
                updateConnectivity();
                return;
            case "android.intent.action.AIRPLANE_MODE":
                refreshLocale();
                updateAirplaneMode(false);
                return;
            case "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED":
                recalculateEmergency();
                return;
            case "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED":
                break;
            case "android.intent.action.SIM_STATE_CHANGED":
                if (!intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                    updateMobileControllers();
                    return;
                }
                return;
            case "android.intent.action.ACTION_SUBINFO_RECORD_UPDATED":
                refreshPlmnCarrierLabel();
                updateMobileControllersEx(intent);
                return;
            case "android.intent.action.SERVICE_STATE":
                this.mLastServiceState = ServiceState.newFromBundle(intent.getExtras());
                if (this.mLastServiceState != null) {
                    int phoneId = intent.getIntExtra("phone", 0);
                    this.mEmergencyPhone[phoneId] = this.mLastServiceState.isEmergencyOnly();
                    if (DEBUG) {
                        Log.d("NetworkController", "Service State changed...phoneId: " + phoneId + " ,isEmergencyOnly: " + this.mEmergencyPhone[phoneId]);
                    }
                    if (this.mMobileSignalControllers.size() == 0) {
                        recalculateEmergency();
                        return;
                    }
                    return;
                }
                return;
            case "android.telephony.action.CARRIER_CONFIG_CHANGED":
                this.mConfig = Config.readConfig(this.mContext);
                this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$ybM43k5QVX_SxWbQACu1XwL3Knk
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.handleConfigurationChanged();
                    }
                });
                return;
            case "com.mediatek.server.lwa.LWA_STATE_CHANGE_ACTION":
                handleLwaAction(intent);
                return;
            case "com.mediatek.ims.MTK_IMS_SERVICE_UP":
                handleIMSServiceUp();
                return;
            default:
                int subId = intent.getIntExtra("subscription", -1);
                if (SubscriptionManager.isValidSubscriptionId(subId)) {
                    if (this.mMobileSignalControllers.indexOfKey(subId) >= 0) {
                        this.mMobileSignalControllers.get(subId).handleBroadcast(intent);
                        return;
                    } else {
                        updateMobileControllers();
                        return;
                    }
                }
                if (this.mWifiSignalController != null) {
                    this.mWifiSignalController.handleBroadcast(intent);
                    return;
                }
                return;
        }
        while (true) {
            int i2 = i;
            if (i2 < this.mMobileSignalControllers.size()) {
                MobileSignalController controller = this.mMobileSignalControllers.valueAt(i2);
                controller.handleBroadcast(intent);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    @Override // com.android.systemui.ConfigurationChangedReceiver
    public void onConfigurationChanged(Configuration newConfig) {
        this.mConfig = Config.readConfig(this.mContext);
        this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.6
            @Override // java.lang.Runnable
            public void run() {
                NetworkControllerImpl.this.handleConfigurationChanged();
            }
        });
    }

    @VisibleForTesting
    void handleConfigurationChanged() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController controller = this.mMobileSignalControllers.valueAt(i);
            controller.setConfiguration(this.mConfig);
        }
        refreshLocale();
    }

    private void updateMobileControllersEx(Intent intent) {
        int detectedType = 4;
        if (intent != null) {
            detectedType = intent.getIntExtra("simDetectStatus", 0);
            Log.d("NetworkController", "updateMobileControllers detectedType: " + detectedType);
        }
        if (detectedType != 3) {
            updateNoSims();
        } else {
            updateMobileControllers();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMobileControllers() {
        SIMHelper.updateSIMInfos(this.mContext);
        if (!this.mListening) {
            if (DEBUG) {
                Log.d("NetworkController", "updateMobileControllers: it's not listening");
                return;
            }
            return;
        }
        doUpdateMobileControllers();
    }

    @VisibleForTesting
    void doUpdateMobileControllers() {
        List<SubscriptionInfo> subscriptions = this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (subscriptions == null) {
            Log.d("NetworkController", "subscriptions is null");
            subscriptions = Collections.emptyList();
        }
        if (CHATTY) {
            Log.d("NetworkController", "doUpdateMobileControllers, subscriptions = " + subscriptions);
        }
        if (hasCorrectMobileControllers(subscriptions)) {
            updateNoSims();
            return;
        }
        setCurrentSubscriptions(subscriptions);
        updateNoSims();
        recalculateEmergency();
    }

    @VisibleForTesting
    protected void updateNoSims() {
        boolean hasNoSubs = this.mHasMobileDataFeature && this.mMobileSignalControllers.size() == 0;
        boolean simDetected = hasAnySim();
        if (hasNoSubs != this.mHasNoSubs || simDetected != this.mSimDetected) {
            this.mHasNoSubs = hasNoSubs;
            this.mSimDetected = simDetected;
            this.mCallbackHandler.setNoSims(this.mHasNoSubs, this.mSimDetected);
        }
    }

    private boolean hasAnySim() {
        int simCount = this.mPhone.getSimCount();
        for (int i = 0; i < simCount; i++) {
            int state = this.mPhone.getSimState(i);
            if (state != 1 && state != 0) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    void setCurrentSubscriptions(List<SubscriptionInfo> subscriptions) {
        Collections.sort(subscriptions, new Comparator<SubscriptionInfo>() { // from class: com.android.systemui.statusbar.policy.NetworkControllerImpl.7
            @Override // java.util.Comparator
            public int compare(SubscriptionInfo lhs, SubscriptionInfo rhs) {
                if (lhs.getSimSlotIndex() == rhs.getSimSlotIndex()) {
                    return lhs.getSubscriptionId() - rhs.getSubscriptionId();
                }
                return lhs.getSimSlotIndex() - rhs.getSimSlotIndex();
            }
        });
        this.mCurrentSubscriptions = subscriptions;
        if (CHATTY) {
            Log.d("NetworkController", "setCurrentSubscriptions, controllers before update = " + this.mMobileSignalControllers);
        }
        SparseArray<MobileSignalController> cachedControllers = new SparseArray<>();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            cachedControllers.put(this.mMobileSignalControllers.keyAt(i), this.mMobileSignalControllers.valueAt(i));
        }
        this.mMobileSignalControllers.clear();
        int num = subscriptions.size();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= num) {
                break;
            }
            int subId = subscriptions.get(i3).getSubscriptionId();
            if (cachedControllers.indexOfKey(subId) >= 0 && cachedControllers.get(subId).mSubscriptionInfo.getSimSlotIndex() == subscriptions.get(i3).getSimSlotIndex()) {
                MobileSignalController msc = cachedControllers.get(subId);
                msc.mSubscriptionInfo = subscriptions.get(i3);
                this.mMobileSignalControllers.put(subId, msc);
                cachedControllers.remove(subId);
            } else {
                MobileSignalController controller = new MobileSignalController(this.mContext, this.mConfig, this.mHasMobileDataFeature, this.mPhone, this.mCallbackHandler, this, subscriptions.get(i3), this.mSubDefaults, this.mReceiverHandler.getLooper());
                controller.setUserSetupComplete(this.mUserSetup);
                this.mMobileSignalControllers.put(subId, controller);
                if (subscriptions.get(i3).getSimSlotIndex() == 0) {
                    this.mDefaultSignalController = controller;
                }
                if (this.mListening) {
                    controller.registerListener();
                }
            }
            i2 = i3 + 1;
        }
        if (CHATTY) {
            Log.d("NetworkController", "setCurrentSubscriptions, removed controllers = " + cachedControllers);
            Log.d("NetworkController", "setCurrentSubscriptions, controllers after update = " + this.mMobileSignalControllers);
        }
        if (this.mListening) {
            int i4 = 0;
            while (true) {
                int i5 = i4;
                if (i5 >= cachedControllers.size()) {
                    break;
                }
                int key = cachedControllers.keyAt(i5);
                if (cachedControllers.get(key) == this.mDefaultSignalController) {
                    this.mDefaultSignalController = null;
                }
                cachedControllers.get(key).unregisterListener();
                i4 = i5 + 1;
            }
        }
        this.mCallbackHandler.setSubs(subscriptions);
        notifyAllListeners();
        pushConnectivityToSignals();
        updateAirplaneMode(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUserSetupComplete(final boolean userSetup) {
        this.mReceiverHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$NetworkControllerImpl$ip_KPuTyKF5u8IR4L3OPJ6WObYU
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleSetUserSetupComplete(userSetup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    public void handleSetUserSetupComplete(boolean userSetup) {
        this.mUserSetup = userSetup;
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController controller = this.mMobileSignalControllers.valueAt(i);
            controller.setUserSetupComplete(this.mUserSetup);
        }
    }

    @VisibleForTesting
    boolean hasCorrectMobileControllers(List<SubscriptionInfo> allSubscriptions) {
        if (allSubscriptions.size() != this.mMobileSignalControllers.size()) {
            Log.d("NetworkController", "size not equals, reset subInfo");
            return false;
        }
        for (SubscriptionInfo info : allSubscriptions) {
            MobileSignalController msc = this.mMobileSignalControllers.get(info.getSubscriptionId());
            if (msc == null || msc.mSubscriptionInfo.getSimSlotIndex() != info.getSimSlotIndex()) {
                Log.d("NetworkController", "info_subId = " + info.getSubscriptionId() + " info_slotId = " + info.getSimSlotIndex());
                return false;
            }
        }
        if (this.mSignalCallbackCount != this.mCallbackHandler.getSignalCallbackCount()) {
            Log.d("NetworkController", "signal callbacks count not equals, reset subInfo, old = " + this.mSignalCallbackCount + ", new = " + this.mCallbackHandler.getSignalCallbackCount());
            this.mSignalCallbackCount = this.mCallbackHandler.getSignalCallbackCount();
            return false;
        }
        return true;
    }

    private void updateAirplaneMode(boolean force) {
        boolean airplaneMode = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
        if (CHATTY) {
            Log.d("NetworkController", "updateAirplaneMode, airplaneMode =" + airplaneMode);
        }
        updateAirplaneMode(airplaneMode, force);
    }

    private void updateAirplaneMode(boolean airplaneMode, boolean force) {
        if (airplaneMode != this.mAirplaneMode || force) {
            this.mAirplaneMode = airplaneMode;
            for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
                MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
                mobileSignalController.setAirplaneMode(this.mAirplaneMode);
            }
            notifyListeners();
        }
    }

    private void refreshLocale() {
        Locale current = this.mContext.getResources().getConfiguration().locale;
        if (!current.equals(this.mLocale)) {
            this.mLocale = current;
            notifyAllListeners();
        }
    }

    private void notifyAllListeners() {
        notifyListeners();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
            mobileSignalController.notifyListeners();
        }
        if (this.mWifiSignalController != null) {
            this.mWifiSignalController.notifyListeners();
        }
        this.mEthernetSignalController.notifyListeners();
    }

    private void notifyListeners() {
        this.mCallbackHandler.setIsAirplaneMode(new NetworkController.IconState(this.mAirplaneMode, R.drawable.stat_sys_airplane_mode, R.string.accessibility_airplane_mode, this.mContext));
        this.mCallbackHandler.setNoSims(this.mHasNoSubs, this.mSimDetected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnectivity() {
        this.mConnectedTransports.clear();
        this.mValidatedTransports.clear();
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        this.mCellularSubId = -1;
        for (NetworkCapabilities nc : this.mConnectivityManager.getDefaultNetworkCapabilitiesForUser(this.mCurrentUserId)) {
            for (int transportType : nc.getTransportTypes()) {
                this.mConnectedTransports.set(transportType);
                if (transportType == 0) {
                    this.mCellularSubId = getSubIdFromNetrequest(nc.getNetworkSpecifier());
                    Log.d("NetworkController", "mCellularSubId = " + this.mCellularSubId);
                }
                if (nc.hasCapability(16)) {
                    this.mValidatedTransports.set(transportType);
                }
            }
        }
        if (CHATTY) {
            Log.d("NetworkController", "updateConnectivity: mConnectedTransports=" + this.mConnectedTransports);
            Log.d("NetworkController", "updateConnectivity: mValidatedTransports=" + this.mValidatedTransports);
        }
        this.mInetCondition = !this.mValidatedTransports.isEmpty();
        if (this.mInetCondition && !this.hasStartedAppStore) {
            startAppStoreService();
        }
        pushConnectivityToSignals();
    }

    boolean isCellularConnected(int subId) {
        return this.mCellularSubId == subId;
    }

    private int getSubIdFromNetrequest(NetworkSpecifier networkspecifier) {
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        if (!(networkspecifier instanceof StringNetworkSpecifier)) {
            return -1;
        }
        try {
            int subId = Integer.parseInt(((StringNetworkSpecifier) networkspecifier).specifier);
            return subId;
        } catch (NumberFormatException e) {
            Log.e("NetworkController", "NumberFormatException on ");
            return -1;
        }
    }

    private void pushConnectivityToSignals() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
            mobileSignalController.updateConnectivity(this.mConnectedTransports, this.mValidatedTransports);
        }
        if (this.mWifiSignalController != null) {
            this.mWifiSignalController.updateConnectivity(this.mConnectedTransports, this.mValidatedTransports);
        }
        this.mEthernetSignalController.updateConnectivity(this.mConnectedTransports, this.mValidatedTransports);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("NetworkController state:");
        pw.println("  - telephony ------");
        pw.print("  hasVoiceCallingFeature()=");
        pw.println(hasVoiceCallingFeature());
        pw.println("  - connectivity ------");
        pw.print("  mConnectedTransports=");
        pw.println(this.mConnectedTransports);
        pw.print("  mValidatedTransports=");
        pw.println(this.mValidatedTransports);
        pw.print("  mInetCondition=");
        pw.println(this.mInetCondition);
        pw.print("  mAirplaneMode=");
        pw.println(this.mAirplaneMode);
        pw.print("  mLocale=");
        pw.println(this.mLocale);
        pw.print("  mLastServiceState=");
        pw.println(this.mLastServiceState);
        pw.print("  mIsEmergency=");
        pw.println(this.mIsEmergency);
        pw.print("  mEmergencySource=");
        pw.println(emergencyToString(this.mEmergencySource));
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalController = this.mMobileSignalControllers.valueAt(i);
            mobileSignalController.dump(pw);
        }
        if (this.mWifiSignalController != null) {
            this.mWifiSignalController.dump(pw);
        }
        this.mEthernetSignalController.dump(pw);
        this.mAccessPoints.dump(pw);
    }

    private static final String emergencyToString(int emergencySource) {
        if (emergencySource > 300) {
            StringBuilder sb = new StringBuilder();
            sb.append("ASSUMED_VOICE_CONTROLLER(");
            sb.append(emergencySource - 200);
            sb.append(")");
            return sb.toString();
        }
        if (emergencySource > 300) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("NO_SUB(");
            sb2.append(emergencySource - 300);
            sb2.append(")");
            return sb2.toString();
        }
        if (emergencySource > 200) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("VOICE_CONTROLLER(");
            sb3.append(emergencySource - 200);
            sb3.append(")");
            return sb3.toString();
        }
        if (emergencySource > 100) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("FIRST_CONTROLLER(");
            sb4.append(emergencySource - 100);
            sb4.append(")");
            return sb4.toString();
        }
        if (emergencySource == 0) {
            return "NO_CONTROLLERS";
        }
        return "UNKNOWN_SOURCE";
    }

    /* JADX WARN: Code duplicated, block: B:205:0x03df  */
    /* JADX WARN: Code duplicated, block: B:81:0x0176  */
    @Override // com.android.systemui.DemoMode
    public void dispatchDemoCommand(String command, Bundle args) {
        int i;
        MobileSignalController.MobileIconGroup mobileIconGroup;
        if (!this.mDemoMode && command.equals("enter")) {
            if (DEBUG) {
                Log.d("NetworkController", "Entering demo mode");
            }
            unregisterListeners();
            this.mDemoMode = true;
            this.mDemoInetCondition = this.mInetCondition;
            if (this.mWifiSignalController != null) {
                this.mDemoWifiState = this.mWifiSignalController.getState();
            }
            this.mDemoWifiState.ssid = "DemoMode";
            return;
        }
        int i2 = 0;
        if (this.mDemoMode && command.equals("exit")) {
            if (DEBUG) {
                Log.d("NetworkController", "Exiting demo mode");
            }
            this.mDemoMode = false;
            updateMobileControllers();
            while (true) {
                int i3 = i2;
                if (i3 >= this.mMobileSignalControllers.size()) {
                    break;
                }
                this.mMobileSignalControllers.valueAt(i3).resetLastState();
                i2 = i3 + 1;
            }
            if (this.mWifiSignalController != null) {
                this.mWifiSignalController.resetLastState();
            }
            this.mReceiverHandler.post(this.mRegisterListeners);
            notifyAllListeners();
            return;
        }
        if (this.mDemoMode && command.equals("network")) {
            String airplane = args.getString("airplane");
            if (airplane != null) {
                boolean show = airplane.equals("show");
                this.mCallbackHandler.setIsAirplaneMode(new NetworkController.IconState(show, R.drawable.stat_sys_airplane_mode, R.string.accessibility_airplane_mode, this.mContext));
            }
            String fully = args.getString("fully");
            if (fully != null) {
                this.mDemoInetCondition = Boolean.parseBoolean(fully);
                BitSet connected = new BitSet();
                if (this.mWifiSignalController != null) {
                    if (this.mDemoInetCondition) {
                        connected.set(this.mWifiSignalController.mTransportType);
                    }
                    this.mWifiSignalController.updateConnectivity(connected, connected);
                }
                for (int i4 = 0; i4 < this.mMobileSignalControllers.size(); i4++) {
                    MobileSignalController controller = this.mMobileSignalControllers.valueAt(i4);
                    if (this.mDemoInetCondition) {
                        connected.set(controller.mTransportType);
                    }
                    controller.updateConnectivity(connected, connected);
                }
            }
            String wifi = args.getString("wifi");
            if (wifi != null && this.mWifiSignalController != null) {
                boolean show2 = wifi.equals("show");
                String level = args.getString("level");
                if (level != null) {
                    this.mDemoWifiState.level = level.equals("null") ? -1 : Math.min(Integer.parseInt(level), WifiIcons.WIFI_LEVEL_COUNT - 1);
                    this.mDemoWifiState.connected = this.mDemoWifiState.level >= 0;
                }
                String activity = args.getString("activity");
                if (activity != null) {
                    switch (activity) {
                        case "inout":
                            this.mWifiSignalController.setActivity(3);
                            break;
                        case "in":
                            this.mWifiSignalController.setActivity(1);
                            break;
                        case "out":
                            this.mWifiSignalController.setActivity(2);
                            break;
                        default:
                            this.mWifiSignalController.setActivity(0);
                            break;
                    }
                } else {
                    this.mWifiSignalController.setActivity(0);
                }
                String ssid = args.getString("ssid");
                if (ssid != null) {
                    this.mDemoWifiState.ssid = ssid;
                }
                this.mDemoWifiState.enabled = show2;
                this.mWifiSignalController.notifyListeners();
            }
            String sims = args.getString("sims");
            if (sims != null) {
                int num = MathUtils.constrain(Integer.parseInt(sims), 1, 8);
                List<SubscriptionInfo> subs = new ArrayList<>();
                if (num != this.mMobileSignalControllers.size()) {
                    this.mMobileSignalControllers.clear();
                    int start = this.mSubscriptionManager.getActiveSubscriptionInfoCountMax();
                    int i5 = start;
                    while (true) {
                        int i6 = i5;
                        if (i6 < start + num) {
                            subs.add(addSignalController(i6, i6));
                            i5 = i6 + 1;
                        } else {
                            this.mCallbackHandler.setSubs(subs);
                            for (int i7 = 0; i7 < this.mMobileSignalControllers.size(); i7++) {
                                int key = this.mMobileSignalControllers.keyAt(i7);
                                this.mMobileSignalControllers.get(key).notifyListeners();
                            }
                        }
                    }
                }
            }
            String nosim = args.getString("nosim");
            if (nosim != null) {
                this.mHasNoSubs = nosim.equals("show");
                this.mCallbackHandler.setNoSims(this.mHasNoSubs, this.mSimDetected);
            }
            String mobile = args.getString("mobile");
            if (mobile != null) {
                boolean show3 = mobile.equals("show");
                String datatype = args.getString("datatype");
                String slotString = args.getString("slot");
                int slot = MathUtils.constrain(TextUtils.isEmpty(slotString) ? 0 : Integer.parseInt(slotString), 0, 8);
                List<SubscriptionInfo> subs2 = new ArrayList<>();
                while (this.mMobileSignalControllers.size() <= slot) {
                    int nextSlot = this.mMobileSignalControllers.size();
                    subs2.add(addSignalController(nextSlot, nextSlot));
                }
                if (!subs2.isEmpty()) {
                    this.mCallbackHandler.setSubs(subs2);
                }
                MobileSignalController controller2 = this.mMobileSignalControllers.valueAt(slot);
                controller2.getState().dataSim = datatype != null;
                controller2.getState().isDefault = datatype != null;
                controller2.getState().dataConnected = datatype != null;
                if (datatype != null) {
                    MobileSignalController.MobileState state = controller2.getState();
                    if (datatype.equals("1x")) {
                        mobileIconGroup = TelephonyIcons.ONE_X;
                    } else if (datatype.equals("3g")) {
                        mobileIconGroup = TelephonyIcons.THREE_G;
                    } else if (datatype.equals("4g")) {
                        mobileIconGroup = TelephonyIcons.FOUR_G;
                    } else if (datatype.equals("4g+")) {
                        mobileIconGroup = TelephonyIcons.FOUR_G_PLUS;
                    } else if (datatype.equals("e")) {
                        mobileIconGroup = TelephonyIcons.E;
                    } else if (datatype.equals("g")) {
                        mobileIconGroup = TelephonyIcons.G;
                    } else if (datatype.equals("h")) {
                        mobileIconGroup = TelephonyIcons.H;
                    } else if (datatype.equals("h+")) {
                        mobileIconGroup = TelephonyIcons.H_PLUS;
                    } else if (datatype.equals("lte")) {
                        mobileIconGroup = TelephonyIcons.LTE;
                    } else if (datatype.equals("lte+")) {
                        mobileIconGroup = TelephonyIcons.LTE_PLUS;
                    } else {
                        mobileIconGroup = datatype.equals("dis") ? TelephonyIcons.DATA_DISABLED : TelephonyIcons.UNKNOWN;
                    }
                    state.iconGroup = mobileIconGroup;
                }
                if (args.containsKey("roam")) {
                    controller2.getState().roaming = "show".equals(args.getString("roam"));
                }
                String level2 = args.getString("level");
                if (level2 != null) {
                    controller2.getState().level = level2.equals("null") ? -1 : Math.min(Integer.parseInt(level2), 5);
                    controller2.getState().connected = controller2.getState().level >= 0;
                }
                String activity2 = args.getString("activity");
                if (activity2 != null) {
                    controller2.getState().dataConnected = true;
                    switch (activity2) {
                        case "inout":
                            controller2.setActivity(3);
                            i = 0;
                            break;
                        case "in":
                            controller2.setActivity(1);
                            i = 0;
                            break;
                        case "out":
                            controller2.setActivity(2);
                            i = 0;
                            break;
                        default:
                            i = 0;
                            controller2.setActivity(0);
                            break;
                    }
                } else {
                    i = 0;
                    controller2.setActivity(0);
                }
                controller2.getState().enabled = show3;
                controller2.notifyListeners();
            } else {
                i = 0;
            }
            String carrierNetworkChange = args.getString("carriernetworkchange");
            if (carrierNetworkChange != null) {
                boolean show4 = carrierNetworkChange.equals("show");
                while (i < this.mMobileSignalControllers.size()) {
                    this.mMobileSignalControllers.valueAt(i).setCarrierNetworkChangeMode(show4);
                    i++;
                }
            }
        }
    }

    private SubscriptionInfo addSignalController(int id, int simSlotIndex) {
        SubscriptionInfo info = new SubscriptionInfo(id, "", simSlotIndex, "", "", 0, 0, "", 0, null, 0, 0, "");
        MobileSignalController controller = new MobileSignalController(this.mContext, this.mConfig, this.mHasMobileDataFeature, this.mPhone, this.mCallbackHandler, this, info, this.mSubDefaults, this.mReceiverHandler.getLooper());
        this.mMobileSignalControllers.put(id, controller);
        controller.getState().userSetup = true;
        return info;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public boolean hasEmergencyCryptKeeperText() {
        return EncryptionHelper.IS_DATA_ENCRYPTED;
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController
    public boolean isRadioOn() {
        return !this.mAirplaneMode;
    }

    private class SubListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        private SubListener() {
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            NetworkControllerImpl.this.updateMobileControllers();
        }
    }

    public static class SubscriptionDefaults {
        public int getDefaultVoiceSubId() {
            return SubscriptionManager.getDefaultVoiceSubscriptionId();
        }

        public int getDefaultDataSubId() {
            return SubscriptionManager.getDefaultDataSubscriptionId();
        }
    }

    @VisibleForTesting
    public static class Config {
        public boolean hspaDataDistinguishable;
        public boolean showAtLeast3G = false;
        public boolean alwaysShowCdmaRssi = false;
        public boolean show4gForLte = false;
        public boolean hideLtePlus = false;
        public boolean inflateSignalStrengths = false;
        public boolean alwaysShowDataRatIcon = false;

        static Config readConfig(Context context) {
            Config config = new Config();
            Resources res = context.getResources();
            config.showAtLeast3G = res.getBoolean(R.bool.config_showMin3G);
            config.alwaysShowCdmaRssi = res.getBoolean(android.R.^attr-private.autofillSaveCustomSubtitleMaxHeight);
            config.show4gForLte = res.getBoolean(R.bool.config_show4GForLTE);
            config.hspaDataDistinguishable = res.getBoolean(R.bool.config_hspa_data_distinguishable);
            config.hideLtePlus = res.getBoolean(R.bool.config_hideLtePlus);
            config.inflateSignalStrengths = res.getBoolean(R.bool.config_inflateSignalStrength);
            CarrierConfigManager configMgr = (CarrierConfigManager) context.getSystemService("carrier_config");
            PersistableBundle b = configMgr.getConfig();
            if (b != null) {
                config.alwaysShowDataRatIcon = b.getBoolean("always_show_data_rat_icon_bool");
            }
            return config;
        }
    }

    private void handleLwaAction(Intent intent) {
        int phoneId = intent.getIntExtra("com.mediatek.server.lwa.EXTRA_PHONE_ID", -1);
        int lwaRegState = intent.getIntExtra("com.mediatek.server.lwa.EXTRA_STATE", -1);
        Log.d("NetworkController", "onRecevie ACTION_LWA:" + phoneId + ",lwa: state" + lwaRegState);
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController controller = this.mMobileSignalControllers.valueAt(i);
            if (controller.getControllerSubInfo().getSimSlotIndex() == phoneId) {
                controller.handleBroadcast(intent);
                return;
            }
        }
    }

    public void refreshPlmnCarrierLabel() {
        for (int i = 0; i < this.mSlotCount; i++) {
            boolean found = false;
            for (int j = 0; j < this.mMobileSignalControllers.size(); j++) {
                MobileSignalController controller = this.mMobileSignalControllers.valueAt(j);
                int slotId = -1;
                if (controller.getControllerSubInfo() != null) {
                    slotId = controller.getControllerSubInfo().getSimSlotIndex();
                }
                if (i == slotId) {
                    this.mNetworkName[slotId] = ((MobileSignalController.MobileState) controller.mCurrentState).networkName;
                    this.mStatusBarPlmnPlugin.updateCarrierLabel(i, true, controller.getControllserHasService(), this.mNetworkName);
                    this.mStatusBarSystemUIExt.setSimInserted(i, true);
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.mNetworkName[i] = this.mContext.getString(android.R.string.contentServiceTooManyDeletesNotificationDesc);
                this.mStatusBarPlmnPlugin.updateCarrierLabel(i, false, false, this.mNetworkName);
                this.mStatusBarSystemUIExt.setSimInserted(i, false);
            }
        }
    }

    private void handleIMSServiceUp() {
        Log.d("NetworkController", "handleIMSServiceUp");
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController controller = this.mMobileSignalControllers.valueAt(i);
            controller.registerImsListener();
        }
    }

    private void startAppStoreService() {
        if (Integer.parseInt(Utils.getSystemProperty("persist.sys.post_location_count", "0")) > 5) {
            this.hasStartedAppStore = true;
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setPackage("com.hcn.appstore");
            intent.setAction("com.hcn.appstore.START_STORE_SERVICE");
            intent.putExtra("from_system_ui", true);
            this.mContext.startForegroundService(intent);
            this.hasStartedAppStore = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
