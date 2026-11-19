package com.mediatek.systemui.qs.tiles.ext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.PhoneConstants;
import com.android.systemui.plugins.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.extcb.IconIdWrapper;
import com.mediatek.systemui.statusbar.util.SIMHelper;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SimDataConnectionTile extends QSTileImpl<QSTile.BooleanState> {
    private boolean mListening;
    private IconIdWrapper[] mSimConnectionIconWrapperArray;
    private SimDataSwitchStateMachine mSimDataSwitchStateMachine;
    private CharSequence mTileLabel;

    public enum SIMConnState {
        SIM1_E_D,
        SIM1_E_E,
        SIM1_D_D,
        SIM1_D_E,
        SIM2_E_D,
        SIM2_E_E,
        SIM2_D_D,
        SIM2_D_E,
        NO_SIM,
        SIM1_E_F,
        SIM1_D_F,
        SIM2_E_F,
        SIM2_D_F
    }

    public SimDataConnectionTile(QSHost host) {
        super(host);
        this.mSimConnectionIconWrapperArray = new IconIdWrapper[SIMConnState.values().length];
        init();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        this.mTileLabel = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext).makeQuickSettings(this.mContext).getTileLabel("simdataconnection");
        return this.mTileLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    private void init() {
        for (int i = 0; i < this.mSimConnectionIconWrapperArray.length; i++) {
            this.mSimConnectionIconWrapperArray[i] = new IconIdWrapper();
        }
        this.mSimDataSwitchStateMachine = new SimDataSwitchStateMachine();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleLongClick() {
        handleClick();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        if (this.mSimDataSwitchStateMachine.isClickable()) {
            this.mSimDataSwitchStateMachine.toggleState(this.mContext);
        }
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
        if (this.mListening == listening) {
            return;
        }
        this.mListening = listening;
        if (listening) {
            this.mSimDataSwitchStateMachine.registerReceiver();
        } else {
            this.mSimDataSwitchStateMachine.unregisterReceiver();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return R.styleable.AppCompatTheme_windowActionBar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        int simConnState = this.mSimDataSwitchStateMachine.getCurrentSimConnState().ordinal();
        IQuickSettingsPlugin quickSettingsPlugin = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext).makeQuickSettings(this.mContext);
        quickSettingsPlugin.customizeSimDataConnectionTile(simConnState, this.mSimConnectionIconWrapperArray[simConnState]);
        state.icon = QsIconWrapper.get(this.mSimConnectionIconWrapperArray[simConnState].getIconId(), this.mSimConnectionIconWrapperArray[simConnState]);
        state.label = quickSettingsPlugin.getTileLabel("simdataconnection");
    }

    private class SimDataSwitchStateMachine {
        private boolean mIsAirlineMode;
        protected boolean mIsUserSwitching;
        boolean mMmsOngoing;
        private PhoneStateListener[] mPhoneStateListener;
        boolean mSimConnStateTrackerReady;
        private int mSlotCount;
        TelephonyManager mTelephonyManager;
        private SIMConnState mCurrentSimConnState = SIMConnState.NO_SIM;
        private Handler mDataTimerHandler = new Handler() { // from class: com.mediatek.systemui.qs.tiles.ext.SimDataConnectionTile.SimDataSwitchStateMachine.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                int simFrom = msg.arg1;
                int simTo = msg.arg2;
                if (msg.what == 2000) {
                    Log.d("SimDataConnectionTile", "switching time out..... switch from " + simFrom + " to " + simTo);
                    if (!SimDataConnectionTile.this.isWifiOnlyDevice()) {
                        SimDataSwitchStateMachine.this.refresh();
                    }
                }
            }
        };
        private BroadcastReceiver mSimStateIntentReceiver = new BroadcastReceiver() { // from class: com.mediatek.systemui.qs.tiles.ext.SimDataConnectionTile.SimDataSwitchStateMachine.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("SimDataConnectionTile", "onReceive action is " + action);
                if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                    SimDataSwitchStateMachine.this.updateSimConnTile();
                    return;
                }
                if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                    boolean enabled = intent.getBooleanExtra("state", false);
                    Log.d("SimDataConnectionTile", "airline mode changed: state is " + enabled);
                    if (SimDataSwitchStateMachine.this.mSimConnStateTrackerReady) {
                        SimDataSwitchStateMachine.this.setAirplaneMode(enabled);
                    }
                    SimDataSwitchStateMachine.this.updateSimConnTile();
                    return;
                }
                if (action.equals("android.intent.action.ANY_DATA_STATE")) {
                    PhoneConstants.DataState state = SimDataSwitchStateMachine.this.getMobileDataState(intent);
                    boolean isApnTypeChange = false;
                    String types = intent.getStringExtra("apnType");
                    if (types != null) {
                        String[] typeArray = types.split(",");
                        for (String type : typeArray) {
                            if ("default".equals(type)) {
                                isApnTypeChange = true;
                                break;
                            }
                        }
                    }
                    if (isApnTypeChange) {
                        if ((state == PhoneConstants.DataState.CONNECTED || state == PhoneConstants.DataState.DISCONNECTED) && !SimDataSwitchStateMachine.this.isMmsOngoing()) {
                            SimDataSwitchStateMachine.this.updateSimConnTile();
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE")) {
                    SimDataSwitchStateMachine.this.updateSimConnTile();
                    return;
                }
                if (action.equals("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED")) {
                    SimDataSwitchStateMachine.this.unRegisterPhoneStateListener();
                    SimDataSwitchStateMachine.this.updateSimConnTile();
                    SimDataSwitchStateMachine.this.registerPhoneStateListener();
                } else {
                    if (action.equals("com.android.mms.transaction.START")) {
                        if (!SimDataConnectionTile.this.isWifiOnlyDevice() && SimDataSwitchStateMachine.this.mSimConnStateTrackerReady) {
                            SimDataSwitchStateMachine.this.setIsMmsOnging(true);
                            SimDataSwitchStateMachine.this.updateSimConnTile();
                            return;
                        }
                        return;
                    }
                    if (action.equals("com.android.mms.transaction.STOP") && !SimDataConnectionTile.this.isWifiOnlyDevice() && SimDataSwitchStateMachine.this.mSimConnStateTrackerReady) {
                        SimDataSwitchStateMachine.this.setIsMmsOnging(false);
                        SimDataSwitchStateMachine.this.updateSimConnTile();
                    }
                }
            }
        };

        public SIMConnState getCurrentSimConnState() {
            return this.mCurrentSimConnState;
        }

        public SimDataSwitchStateMachine() {
            this.mSlotCount = 0;
            this.mTelephonyManager = (TelephonyManager) SimDataConnectionTile.this.mContext.getSystemService("phone");
            this.mSlotCount = SIMHelper.getSlotCount();
            this.mPhoneStateListener = new PhoneStateListener[this.mSlotCount];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerReceiver() {
            IntentFilter simIntentFilter = new IntentFilter();
            simIntentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            simIntentFilter.addAction("com.android.mms.transaction.START");
            simIntentFilter.addAction("com.android.mms.transaction.STOP");
            simIntentFilter.addAction("android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE");
            simIntentFilter.addAction("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED");
            simIntentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
            simIntentFilter.addAction("android.intent.action.ANY_DATA_STATE");
            SimDataConnectionTile.this.mContext.registerReceiver(this.mSimStateIntentReceiver, simIntentFilter);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterReceiver() {
            SimDataConnectionTile.this.mContext.unregisterReceiver(this.mSimStateIntentReceiver);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSimConnTile() {
            onActualStateChange(SimDataConnectionTile.this.mContext, null);
            SimDataConnectionTile.this.refreshState();
        }

        public void refresh() {
            onActualStateChange(SimDataConnectionTile.this.mContext, null);
            setUserSwitching(false);
        }

        public void onActualStateChange(Context context, Intent intent) {
            List<SubscriptionInfo> infos = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
            boolean sim1Enable = isSimEnable(infos, 0);
            boolean sim2Enable = isSimEnable(infos, 1);
            boolean sim1Conn = false;
            boolean sim2Conn = false;
            int dataConnectionId = SubscriptionManager.getDefaultDataSubscriptionId();
            if (SubscriptionManager.getSlotIndex(dataConnectionId) != 0) {
                if (SubscriptionManager.getSlotIndex(dataConnectionId) == 1) {
                    sim1Conn = false;
                    sim2Conn = true;
                }
            } else {
                sim1Conn = true;
                sim2Conn = false;
            }
            Log.d("SimDataConnectionTile", "SimConnStateTracker onActualStateChange sim1Enable = " + sim1Enable + ", sim2Enable = " + sim2Enable);
            if (sim1Enable || sim2Enable) {
                boolean dataConnected = isDataConnected();
                Log.d("SimDataConnectionTile", "onActualStateChange, dataConnected = " + dataConnected + ", sim1Enable = " + sim1Enable + ", sim2Enable = " + sim2Enable + ", sim1Conn = " + sim1Conn + ", sim2Conn = " + sim2Conn);
                if (dataConnected) {
                    if (sim1Enable && sim2Enable) {
                        if (sim1Conn) {
                            this.mCurrentSimConnState = SIMConnState.SIM1_E_E;
                        } else {
                            this.mCurrentSimConnState = SIMConnState.SIM2_E_E;
                        }
                    } else if (!sim1Enable && sim2Enable) {
                        if (isSimInsertedWithUnAvaliable(infos, 0) && sim1Conn) {
                            this.mCurrentSimConnState = SIMConnState.SIM1_E_F;
                        } else {
                            this.mCurrentSimConnState = SIMConnState.SIM2_D_E;
                        }
                    } else if (sim1Enable && !sim2Enable) {
                        if (isSimInsertedWithUnAvaliable(infos, 1) && sim2Conn) {
                            this.mCurrentSimConnState = SIMConnState.SIM2_E_F;
                        } else {
                            this.mCurrentSimConnState = SIMConnState.SIM1_D_E;
                        }
                    }
                } else if (sim1Enable && sim2Enable) {
                    if (sim1Conn) {
                        this.mCurrentSimConnState = SIMConnState.SIM1_E_D;
                    } else {
                        this.mCurrentSimConnState = SIMConnState.SIM2_E_D;
                    }
                } else if (!sim1Enable && sim2Enable) {
                    if (isSimInsertedWithUnAvaliable(infos, 0) && sim1Conn) {
                        this.mCurrentSimConnState = SIMConnState.SIM1_E_F;
                    } else {
                        this.mCurrentSimConnState = SIMConnState.SIM2_D_D;
                    }
                } else if (sim1Enable && !sim2Enable) {
                    if (isSimInsertedWithUnAvaliable(infos, 1) && sim2Conn) {
                        this.mCurrentSimConnState = SIMConnState.SIM2_E_F;
                    } else {
                        this.mCurrentSimConnState = SIMConnState.SIM1_D_D;
                    }
                }
            } else if (isSimInsertedWithUnAvaliable(infos, 0) && sim1Conn) {
                this.mCurrentSimConnState = SIMConnState.SIM1_D_F;
            } else if (isSimInsertedWithUnAvaliable(infos, 1) && sim2Conn) {
                this.mCurrentSimConnState = SIMConnState.SIM2_D_F;
            } else {
                this.mCurrentSimConnState = SIMConnState.NO_SIM;
            }
            setUserSwitching(false);
        }

        private boolean isSimEnable(List<SubscriptionInfo> infos, int slotId) {
            return SimDataConnectionTile.this.isSimInsertedBySlot(infos, slotId) && !isAirplaneMode() && isRadioOn(slotId) && !isSimLocked(slotId);
        }

        private boolean isSimInsertedWithUnAvaliable(List<SubscriptionInfo> infos, int slotId) {
            return SimDataConnectionTile.this.isSimInsertedBySlot(infos, slotId) && (!isRadioOn(slotId) || isAirplaneMode() || isSimLocked(slotId));
        }

        private boolean isRadioOn(int slotId) {
            int subId1 = SIMHelper.getFirstSubInSlot(slotId);
            return SIMHelper.isRadioOn(subId1);
        }

        private boolean isSimLocked(int slotId) {
            int simState = TelephonyManager.getDefault().getSimState(slotId);
            boolean bSimLocked = simState == 2 || simState == 3 || simState == 4;
            Log.d("SimDataConnectionTile", "isSimLocked, slotId=" + slotId + " simState=" + simState + " bSimLocked= " + bSimLocked);
            return bSimLocked;
        }

        public void toggleState(Context context) {
            enterNextState(this.mCurrentSimConnState);
        }

        private void enterNextState(SIMConnState state) {
            Log.d("SimDataConnectionTile", "enterNextState state is " + state);
            switch (state) {
                case NO_SIM:
                case SIM1_D_D:
                case SIM1_D_E:
                case SIM2_D_D:
                case SIM2_D_E:
                case SIM1_D_F:
                case SIM2_D_F:
                    Log.d("SimDataConnectionTile", "No Sim or one Sim do nothing!");
                    break;
                case SIM1_E_D:
                    Log.d("SimDataConnectionTile", "Try to switch from Sim1 to Sim2! mSimCurrentCurrentState=" + this.mCurrentSimConnState);
                    this.mCurrentSimConnState = SIMConnState.SIM2_E_D;
                    switchDataDefaultSIM(1);
                    break;
                case SIM1_E_E:
                    Log.d("SimDataConnectionTile", "Try to switch from Sim1 to Sim2! mSimCurrentCurrentState=" + this.mCurrentSimConnState);
                    this.mCurrentSimConnState = SIMConnState.SIM2_E_E;
                    switchDataDefaultSIM(1);
                    break;
                case SIM2_E_D:
                    Log.d("SimDataConnectionTile", "Try to switch from Sim2 to Sim1! mSimCurrentCurrentState=" + this.mCurrentSimConnState);
                    this.mCurrentSimConnState = SIMConnState.SIM1_E_D;
                    switchDataDefaultSIM(0);
                    break;
                case SIM2_E_E:
                    Log.d("SimDataConnectionTile", "Try to switch from Sim2 to Sim1! mSimCurrentCurrentState=" + this.mCurrentSimConnState);
                    this.mCurrentSimConnState = SIMConnState.SIM1_E_E;
                    switchDataDefaultSIM(0);
                    break;
                case SIM1_E_F:
                    Log.d("SimDataConnectionTile", "Try to switch from Sim1 to Sim2! mSimCurrentCurrentState=" + this.mCurrentSimConnState);
                    switchDataDefaultSIM(1);
                    break;
                case SIM2_E_F:
                    Log.d("SimDataConnectionTile", "Try to switch from Sim2 to Sim1! mSimCurrentCurrentState=" + this.mCurrentSimConnState);
                    switchDataDefaultSIM(0);
                    break;
            }
        }

        private void switchDataDefaultSIM(int slotId) {
            if (!SimDataConnectionTile.this.isWifiOnlyDevice()) {
                setUserSwitching(true);
                handleDataConnectionChange(slotId);
            }
        }

        private void handleDataConnectionChange(int newSlot) {
            Log.d("SimDataConnectionTile", "handleDataConnectionChange, newSlot=" + newSlot);
            if (SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultDataSubscriptionId()) != newSlot) {
                this.mDataTimerHandler.sendEmptyMessageDelayed(2000, 30000L);
                List<SubscriptionInfo> si = SubscriptionManager.from(SimDataConnectionTile.this.mContext).getActiveSubscriptionInfoList();
                if (si != null && si.size() > 0) {
                    boolean dataEnabled = this.mTelephonyManager.getDataEnabled();
                    for (int i = 0; i < si.size(); i++) {
                        SubscriptionInfo subInfo = si.get(i);
                        int subId = subInfo.getSubscriptionId();
                        if (newSlot == subInfo.getSimSlotIndex()) {
                            Log.d("SimDataConnectionTile", "handleDataConnectionChange. newSlot = " + newSlot + " subId = " + subId);
                            SubscriptionManager.from(SimDataConnectionTile.this.mContext).setDefaultDataSubId(subId);
                            if (dataEnabled) {
                                this.mTelephonyManager.setDataEnabled(subId, true);
                            }
                        } else if (dataEnabled) {
                            this.mTelephonyManager.setDataEnabled(subId, false);
                        }
                    }
                }
            }
        }

        public boolean isClickable() {
            List<SubscriptionInfo> infos = SubscriptionManager.from(SimDataConnectionTile.this.mContext).getActiveSubscriptionInfoList();
            if (SimDataConnectionTile.this.isSimInsertedBySlot(infos, 0) || SimDataConnectionTile.this.isSimInsertedBySlot(infos, 1)) {
                return ((!isRadioOn(0) && !isRadioOn(1)) || isAirplaneMode() || isMmsOngoing() || isUserSwitching()) ? false : true;
            }
            return false;
        }

        private boolean isDataConnected() {
            return TelephonyManager.getDefault().getDataState() == 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIsMmsOnging(boolean ongoing) {
            this.mMmsOngoing = ongoing;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isMmsOngoing() {
            return this.mMmsOngoing;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAirplaneMode(boolean airplaneMode) {
            this.mIsAirlineMode = airplaneMode;
        }

        private boolean isAirplaneMode() {
            return this.mIsAirlineMode;
        }

        private void setUserSwitching(boolean userSwitching) {
            this.mIsUserSwitching = userSwitching;
        }

        private boolean isUserSwitching() {
            return this.mIsUserSwitching;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public PhoneConstants.DataState getMobileDataState(Intent intent) {
            String str = intent.getStringExtra("state");
            if (str != null) {
                return Enum.valueOf(PhoneConstants.DataState.class, str);
            }
            return PhoneConstants.DataState.DISCONNECTED;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerPhoneStateListener() {
            for (int i = 0; i < this.mSlotCount; i++) {
                int subId = SIMHelper.getFirstSubInSlot(i);
                if (subId >= 0) {
                    this.mPhoneStateListener[i] = getPhoneStateListener(subId, i);
                    this.mTelephonyManager.listen(this.mPhoneStateListener[i], 1);
                } else {
                    this.mPhoneStateListener[i] = null;
                }
            }
        }

        private PhoneStateListener getPhoneStateListener(int subId, final int slotId) {
            return new PhoneStateListener(Integer.valueOf(subId)) { // from class: com.mediatek.systemui.qs.tiles.ext.SimDataConnectionTile.SimDataSwitchStateMachine.3
                @Override // android.telephony.PhoneStateListener
                public void onServiceStateChanged(ServiceState state) {
                    Log.d("SimDataConnectionTile", "PhoneStateListener:onServiceStateChanged, slot " + slotId + " servicestate = " + state);
                    SimDataSwitchStateMachine.this.updateSimConnTile();
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unRegisterPhoneStateListener() {
            for (int i = 0; i < this.mSlotCount; i++) {
                if (this.mPhoneStateListener[i] != null) {
                    this.mTelephonyManager.listen(this.mPhoneStateListener[i], 0);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWifiOnlyDevice() {
        Context context = this.mContext;
        Context context2 = this.mContext;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        return !cm.isNetworkSupported(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSimInsertedBySlot(List<SubscriptionInfo> infos, int slotId) {
        if (slotId >= SIMHelper.getSlotCount()) {
            return false;
        }
        if (infos != null && infos.size() > 0) {
            for (SubscriptionInfo info : infos) {
                if (info.getSimSlotIndex() == slotId) {
                    return true;
                }
            }
            return false;
        }
        Log.d("SimDataConnectionTile", "isSimInsertedBySlot, SubscriptionInfo is null");
        return false;
    }
}
