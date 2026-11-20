package com.hcn.bluetooth.service;

import android.app.ActivityManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.hcn.auto_compat.bluetooth.BluetoothCompat;
import com.hcn.auto_compat.constants.PackageInfo;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.bean.CallInfo;
import com.hcn.bluetooth.view.PhoneCallView;
import com.hcn.bluetooth.view.PhoneCallViewBase;
import com.hcn.bluetooth.view.PhoneCallViewEx;
import com.hcn.bluetooth.view.PhoneCallViewMini;
import com.hcn.bluetooth.view.PhoneCallViewMiniEx;
import com.hcn.bluetoothservice.R;
import com.hcn.skin2.Skin2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothHfpclientService extends Service {
    private static final String ACTION_CONFIGURATION_CHANGED = "android.intent.action.CONFIGURATION_CHANGED";
    public static final String ACTION_ENABLE_BT_CALL_VIEW = "ACTION_ENABLE_BT_CALL_VIEW";
    public static final String ACTION_GLOBAL_BUTTON = "android.intent.action.GLOBAL_BUTTON";
    private static final String ACTION_NOTIFICATION_CALL_ACCEPT_NONE = "com.autochips.bluetooth.BluetoothReceiver.ACTION.ACTION_NOTIFICATION_CALL_ACCEPT_NONE";
    private static final String ACTION_NOTIFICATION_CALL_CALL_NUMBER = "com.autochips.bluetooth.BluetoothReceiver.ACTION.ACTION_NOTIFICATION_CALL_NUMBER";
    private static final String ACTION_NOTIFICATION_CALL_REJECT = "com.autochips.bluetooth.BluetoothReceiver.ACTION.ACTION_NOTIFICATION_CALL_REJECT";
    public static final String ACTION_REVSTATUS = "android.mcu.device.action.REVSTATUS";
    public static final String ACTION_UPDATE_BT_CALL_VIEW = "ACTION_UPDATE_BT_CALL_VIEW";
    public static final int AUTO_ANSWER_DELAY = 5000;
    public static final String BTACTION = "com.cardroid.action.START_cardroid.bt";
    public static final int CALL_VIEW_INVALIDATE = -1;
    public static final int CALL_VIEW_MINI = 1;
    public static final int CALL_VIEW_NORMAL = 0;
    private static final String EXTRA_ENABLE = "EXTRA_ENABLE";
    private static final String EXTRA_NUMBER = "EXTRA_NUMBER";
    private static final String EXTRA_PACKAGE = "EXTRA_PACKAGE";
    public static final String EXTRA_REVSTATUS = "REVSTATUS";
    private static final int HANGUP_INTERVAL = 800;
    private static final int INTERVAL_TIME = 1000;
    public static final int MSG_AUTO_ANSWER = 1;
    public static final int MSG_FILTER_DIAL = 5;
    public static final int MSG_HANGUP_CALL = 4;
    public static final int MSG_SWITCH_AUDIO = 3;
    public static final int MSG_UPDATE_TIME = 2;
    private static final String NEED_MINI_CALL_VIEW_ACTIVITY = "com.auto.hcamera.FCameraVideoInActivity,";
    private static final String NEED_MINI_CALL_VIEW_ACTIVITY1 = "com.zjinnova.android.zlink.features.main.MainActivity";
    public static final String TAG = "HfpClientService";
    private static BluetoothHfpclientService sInstance;
    private AudioManager mAudioManager;
    private Context mContext;
    private PhoneCallViewBase mCurPhoneCallView;
    private WindowManager mWindowManager;
    private BluetoothHeadsetClient mHeadsetClient = null;
    private BluetoothDevice mConnectedDevice = null;
    private boolean isReverseState = false;
    private int mAudioState = 0;
    private final ArrayMap<String, CallInfo> mCallTimeMap = new ArrayMap<>(3);
    private boolean isCallTimeBegin = false;
    private long mAcceptCallTime = 0;
    private String mLastCall = "";
    private IBluetoothHfpclientService.Stub mBinder = null;
    private PhoneCallViewBase mPhoneCallView = null;
    private PhoneCallViewBase mPhoneCallViewMini = null;
    private int mSelectCallViewByManual = -1;
    private int mScreenOrientation = 2;
    private BroadcastReceiver mReceiver = new AnonymousClass1();
    private final BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.hcn.bluetooth.service.BluetoothHfpclientService.2
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d(BluetoothHfpclientService.TAG, "onServiceConnected :" + bluetoothProfile);
            BluetoothHfpclientService.this.mHeadsetClient = (BluetoothHeadsetClient) bluetoothProfile;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            Log.d(BluetoothHfpclientService.TAG, "mHeadsetClient Disconnected");
        }
    };
    private final Handler mHandler = new Handler() { // from class: com.hcn.bluetooth.service.BluetoothHfpclientService.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.e(BluetoothHfpclientService.TAG, "handleMessage:" + message.what);
            int i = message.what;
            if (i == 1) {
                BluetoothHfpclientService.this.acceptCall(0);
            } else if (i == 2) {
                Iterator it = BluetoothHfpclientService.this.mCallTimeMap.entrySet().iterator();
                while (it.hasNext()) {
                    ((CallInfo) ((Map.Entry) it.next()).getValue()).CallTimeAdd();
                }
                if (BluetoothHfpclientService.this.mCurPhoneCallView != null) {
                    BluetoothHfpclientService.this.mCurPhoneCallView.updateCallTime(BluetoothHfpclientService.this.mCallTimeMap);
                }
                sendMessageDelayed(obtainMessage(2), 1000L);
            } else if (i == 4) {
                BluetoothHfpclientService.this.hangup();
            }
            super.handleMessage(message);
        }
    };

    public static synchronized BluetoothHfpclientService getInstance() {
        return sInstance;
    }

    public BluetoothHeadsetClient getHeadsetClient() {
        return this.mHeadsetClient;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "onCreate");
        init();
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        deinit();
        if (this.mBinder != null) {
            this.mBinder = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        Log.d(TAG, "onStart");
        super.onStart(intent, i);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        if (this.mBinder == null) {
            this.mBinder = new HfpBinder(this, null);
        }
        return this.mBinder;
    }

    private void init() {
        Log.i(TAG, "init");
        sInstance = this;
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        this.mScreenOrientation = applicationContext.getResources().getConfiguration().orientation;
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        renewPhoneCallView();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(this.mContext, this.mProfileServiceListener, 16);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED");
        intentFilter.addAction(BluetoothCompat.HeadsetClient.ACTION_AUDIO_STATE_CHANGED);
        intentFilter.addAction(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction("android.bluetooth.headsetclient.profile.action.LAST_VTAG");
        intentFilter.addAction("android.bluetooth.headsetclient.profile.action.AG_EVENT");
        intentFilter.addAction("android.bluetooth.headsetclient.profile.action.RESULT");
        intentFilter.addAction(BTACTION);
        intentFilter.addAction(ACTION_NOTIFICATION_CALL_CALL_NUMBER);
        intentFilter.addAction(ACTION_NOTIFICATION_CALL_ACCEPT_NONE);
        intentFilter.addAction(ACTION_NOTIFICATION_CALL_REJECT);
        intentFilter.addAction(ACTION_REVSTATUS);
        intentFilter.addAction(ACTION_ENABLE_BT_CALL_VIEW);
        intentFilter.addAction(ACTION_UPDATE_BT_CALL_VIEW);
        intentFilter.addAction(ACTION_CONFIGURATION_CHANGED);
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction(ACTION_GLOBAL_BUTTON);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void renewPhoneCallView() {
        Log.d(TAG, "renewPhoneCallView");
        if (Utils.isT5Platform()) {
            this.mPhoneCallView = new PhoneCallViewEx(this.mContext, this.mWindowManager, this.mAudioManager);
        } else {
            this.mPhoneCallView = new PhoneCallView(this.mContext, this.mWindowManager, this.mAudioManager);
        }
        if (Skin2.getInteger(R.integer.mini_call_view_type) != 0) {
            if (Utils.isT5Platform()) {
                this.mPhoneCallViewMini = new PhoneCallViewMiniEx(this.mContext, this.mWindowManager, this.mAudioManager);
            } else {
                this.mPhoneCallViewMini = new PhoneCallViewMini(this.mContext, this.mWindowManager, this.mAudioManager);
            }
        }
        this.mCurPhoneCallView = this.mPhoneCallView;
    }

    private void deinit() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public String callStateToString(int i) {
        switch (i) {
            case 0:
                return "ACTIVE";
            case 1:
                return "HELD";
            case 2:
                return "DIALING";
            case 3:
                return "ALERTING";
            case 4:
                return "INCOMING";
            case 5:
                return "WAITING";
            case 6:
                return "HELD_BY_RESPONSE_AND_HOLD";
            case 7:
                return "TERMINATED";
            default:
                return String.valueOf(i);
        }
    }

    /* JADX INFO: renamed from: com.hcn.bluetooth.service.BluetoothHfpclientService$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(BluetoothHfpclientService.TAG, "onReceive:" + action);
            if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10) == 10) {
                    BluetoothHfpclientService.this.onEndCall();
                    return;
                }
                return;
            }
            if (action.equals("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED")) {
                BluetoothHfpclientService.this.handleCallStateChanged(intent);
                return;
            }
            if (action.equals(BluetoothCompat.HeadsetClient.ACTION_AUDIO_STATE_CHANGED)) {
                BluetoothHfpclientService.this.mAudioState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.d(BluetoothHfpclientService.TAG, "onReceive: ACTION_AUDIO_STATE_CHANGED " + BluetoothHfpclientService.this.mAudioState);
                BluetoothHfpclientService.this.mCurPhoneCallView.updateAudioState(BluetoothHfpclientService.this.mAudioState);
                return;
            }
            if (action.equals(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED)) {
                BluetoothHfpclientService.this.mConnectedDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                if (intExtra == 2) {
                    BluetoothHfpclientService.this.cancelAutoAnswer();
                    BluetoothHfpclientService.this.mLastCall = "";
                    return;
                } else {
                    if (intExtra == 0) {
                        BluetoothHfpclientService.this.onEndCall();
                        return;
                    }
                    return;
                }
            }
            if (action.equals("android.bluetooth.headsetclient.profile.action.AG_EVENT")) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.headsetclient.extra.BATTERY_LEVEL", -1);
                int intExtra3 = intent.getIntExtra("android.bluetooth.headsetclient.extra.NETWORK_SIGNAL_STRENGTH", -1);
                int intExtra4 = intent.getIntExtra("android.bluetooth.headsetclient.extra.NETWORK_STATUS", -1);
                int intExtra5 = intent.getIntExtra("android.bluetooth.headsetclient.extra.NETWORK_ROAMING", -1);
                String stringExtra = intent.getStringExtra("android.bluetooth.headsetclient.extra.OPERATOR_NAME");
                int intExtra6 = intent.getIntExtra("android.bluetooth.headsetclient.extra.VOICE_RECOGNITION", 0);
                StringBuilder sb = new StringBuilder();
                sb.append("voiceState=").append(intExtra6);
                if (-1 != intExtra2) {
                    sb.append(" battery=").append(intExtra2);
                }
                if (-1 != intExtra3) {
                    sb.append(" signalLevel=").append(intExtra3);
                }
                if (-1 != intExtra4) {
                    sb.append(" netStatus=").append(intExtra4);
                }
                if (-1 != intExtra5) {
                    sb.append(" netRoaming=").append(intExtra5);
                }
                if (!TextUtils.isEmpty(stringExtra)) {
                    sb.append(" operatorName=").append(stringExtra);
                }
                Log.d(BluetoothHfpclientService.TAG, "ACTION_AG_EVENT: " + sb.toString());
                return;
            }
            if (action.equals("android.bluetooth.headsetclient.profile.action.RESULT")) {
                int intExtra7 = intent.getIntExtra("android.bluetooth.headsetclient.extra.RESULT_CODE", 1);
                Log.d(BluetoothHfpclientService.TAG, "result =" + intExtra7);
                if (intExtra7 != 0) {
                    Log.e(BluetoothHfpclientService.TAG, "Can't call out,please check input number or callhistory");
                    return;
                }
                return;
            }
            if (action.equals(BluetoothHfpclientService.BTACTION)) {
                KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
                Log.d(BluetoothHfpclientService.TAG, "onReceive BT KEY: " + keyEvent.getKeyCode() + ", action:" + keyEvent.getAction() + ",long press = " + keyEvent.isLongPress());
                if (keyEvent.getKeyCode() == 5 && keyEvent.getAction() == 0) {
                    List<BluetoothHeadsetClientCall> currentCalls = BluetoothHfpclientService.this.getCurrentCalls();
                    if (currentCalls == null || currentCalls.isEmpty()) {
                        if (BluetoothHfpclientService.this.isReverseState) {
                            return;
                        }
                        Utils.startBtClient(BluetoothHfpclientService.this.mContext, "swc_key_bt");
                        return;
                    } else {
                        if (BluetoothHfpclientService.getCall(currentCalls, 4, 5).isEmpty()) {
                            return;
                        }
                        BluetoothHfpclientService.this.acceptCall(0);
                        return;
                    }
                }
                if (keyEvent.getKeyCode() == 6 && keyEvent.getAction() == 0) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - BluetoothHfpclientService.this.mAcceptCallTime;
                    if (jCurrentTimeMillis >= 800) {
                        BluetoothHfpclientService.this.mHandler.removeMessages(4);
                        BluetoothHfpclientService.this.mHandler.sendEmptyMessage(4);
                        return;
                    } else {
                        long j = 800 - jCurrentTimeMillis;
                        Log.d(BluetoothHfpclientService.TAG, "delay " + j + "ms to hangup");
                        BluetoothHfpclientService.this.mHandler.removeMessages(4);
                        BluetoothHfpclientService.this.mHandler.sendEmptyMessageDelayed(4, j);
                        return;
                    }
                }
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_NOTIFICATION_CALL_CALL_NUMBER)) {
                String stringExtra2 = intent.getStringExtra(BluetoothHfpclientService.EXTRA_NUMBER);
                if (TextUtils.isEmpty(stringExtra2)) {
                    return;
                }
                BluetoothHfpclientService.this.dial(stringExtra2);
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_NOTIFICATION_CALL_ACCEPT_NONE)) {
                BluetoothHfpclientService.this.acceptCall(0);
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_NOTIFICATION_CALL_REJECT)) {
                long jCurrentTimeMillis2 = System.currentTimeMillis() - BluetoothHfpclientService.this.mAcceptCallTime;
                if (jCurrentTimeMillis2 >= 800) {
                    BluetoothHfpclientService.this.mHandler.removeMessages(4);
                    BluetoothHfpclientService.this.mHandler.sendEmptyMessage(4);
                    return;
                } else {
                    long j2 = 800 - jCurrentTimeMillis2;
                    Log.d(BluetoothHfpclientService.TAG, "delay " + j2 + "ms to hangup");
                    BluetoothHfpclientService.this.mHandler.removeMessages(4);
                    BluetoothHfpclientService.this.mHandler.sendEmptyMessageDelayed(4, j2);
                    return;
                }
            }
            if (action.equals(BluetoothHfpclientService.ACTION_REVSTATUS)) {
                BluetoothHfpclientService.this.isReverseState = intent.getBooleanExtra(BluetoothHfpclientService.EXTRA_REVSTATUS, false);
                if (BluetoothHfpclientService.this.mCurPhoneCallView.isAddView()) {
                    BluetoothHfpclientService.this.updateCallView();
                    return;
                }
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_UPDATE_BT_CALL_VIEW) || action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                if (BluetoothHfpclientService.this.mCurPhoneCallView.isAddView() && Utils.isT5Platform()) {
                    BluetoothHfpclientService.this.mHandler.postDelayed(new Runnable() { // from class: com.hcn.bluetooth.service.BluetoothHfpclientService$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m40x726ddf05();
                        }
                    }, 300L);
                    return;
                }
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_ENABLE_BT_CALL_VIEW)) {
                String stringExtra3 = intent.getStringExtra(BluetoothHfpclientService.EXTRA_PACKAGE);
                boolean booleanExtra = intent.getBooleanExtra(BluetoothHfpclientService.EXTRA_ENABLE, true);
                Log.d(BluetoothHfpclientService.TAG, "onReceive: ACTION_ENABLE_BT_CALL_VIEW enable=" + booleanExtra + " package=" + stringExtra3);
                BluetoothHfpclientService.this.onSetCallViewEnable(booleanExtra);
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_CONFIGURATION_CHANGED)) {
                Log.d(BluetoothHfpclientService.TAG, "onReceive: ACTION_CONFIGURATION_CHANGED");
                if (BluetoothHfpclientService.this.mScreenOrientation != BluetoothHfpclientService.this.mContext.getResources().getConfiguration().orientation) {
                    BluetoothHfpclientService bluetoothHfpclientService = BluetoothHfpclientService.this;
                    bluetoothHfpclientService.mScreenOrientation = bluetoothHfpclientService.mContext.getResources().getConfiguration().orientation;
                    BluetoothHfpclientService.this.mPhoneCallView = null;
                    BluetoothHfpclientService.this.mPhoneCallViewMini = null;
                    BluetoothHfpclientService.this.renewPhoneCallView();
                    return;
                }
                return;
            }
            if (action.equals(BluetoothHfpclientService.ACTION_GLOBAL_BUTTON)) {
                KeyEvent keyEvent2 = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
                if (keyEvent2 == null) {
                    Log.e(BluetoothHfpclientService.TAG, "onReceive: ACTION_GLOBAL_BUTTON keyEvent is null");
                } else if (keyEvent2.getAction() == 0 && keyEvent2.getKeyCode() == 287) {
                    BluetoothHfpclientService.this.switchAudio();
                }
            }
        }

        /* JADX INFO: renamed from: lambda$onReceive$0$com-hcn-bluetooth-service-BluetoothHfpclientService$1, reason: not valid java name */
        /* synthetic */ void m40x726ddf05() {
            BluetoothHfpclientService.this.updateCallView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallStateChanged(Intent intent) {
        BluetoothHeadsetClientCall parcelableExtra = intent.getParcelableExtra("android.bluetooth.headsetclient.extra.CALL");
        if (parcelableExtra == null) {
            Log.e(TAG, "CallStateChanged get call fall!");
        }
        int state = parcelableExtra.getState();
        String number = parcelableExtra.getNumber();
        Log.i(TAG, "number = " + number + " state = " + callStateToString(state));
        List<BluetoothHeadsetClientCall> currentCalls = getCurrentCalls();
        if (currentCalls == null || currentCalls.isEmpty()) {
            Log.i(TAG, "handleCallStateChanged no calls hideView!!!");
            onEndCall();
            return;
        }
        if (!this.mCurPhoneCallView.isAddView()) {
            if (isShowMiniCallView()) {
                this.mCurPhoneCallView = this.mPhoneCallViewMini;
                Log.d(TAG, "handleCallStateChanged: select CallViewMini.");
            } else {
                this.mCurPhoneCallView = this.mPhoneCallView;
                Log.d(TAG, "handleCallStateChanged: select CallView.");
            }
        }
        this.mCurPhoneCallView.onActionCallStateChanged(currentCalls);
        switch (state) {
            case 0:
                onStartCall();
                if (this.mCallTimeMap.containsKey(number)) {
                    this.mCallTimeMap.get(number).setCallState(state);
                } else {
                    this.mCallTimeMap.put(number, new CallInfo(number, state));
                }
                startCallTime();
                cancelAutoAnswer();
                break;
            case 1:
            case 6:
                if (this.mCallTimeMap.containsKey(number)) {
                    this.mCallTimeMap.get(number).setCallState(state);
                }
                break;
            case 2:
            case 3:
            case 5:
                onStartCall();
                break;
            case 4:
                onStartCall();
                startAutoAnswer();
                break;
            case 7:
                cancelAutoAnswer();
                if (this.mCallTimeMap.containsKey(number)) {
                    this.mCallTimeMap.remove(number);
                }
                break;
        }
    }

    public void startCallTime() {
        if (this.isCallTimeBegin) {
            return;
        }
        Log.d(TAG, "startCallTime: ");
        this.isCallTimeBegin = true;
        PhoneCallViewBase phoneCallViewBase = this.mCurPhoneCallView;
        if (phoneCallViewBase != null) {
            phoneCallViewBase.updateCallTime(this.mCallTimeMap);
        }
        this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 2), 1000L);
    }

    public void stopCallTime() {
        if (this.isCallTimeBegin) {
            Log.d(TAG, "stopCallTime: ");
            this.mCallTimeMap.clear();
            this.mHandler.removeMessages(2);
            this.isCallTimeBegin = false;
        }
    }

    private void onStartCall() {
        Log.d(TAG, "onStartCall: ");
        if (this.mCurPhoneCallView.isAddView()) {
            return;
        }
        Log.d(TAG, "onStartCall: showView");
        this.mCurPhoneCallView.showView();
        BluetoothAdapterManagerService.getInstance().stopDiscovery();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEndCall() {
        Log.d(TAG, "onEndCall: ");
        if (this.mCurPhoneCallView.isAddView()) {
            Log.d(TAG, "onEndCall: hideView");
            this.mCurPhoneCallView.hideView();
            if (this.mAudioManager.isMicrophoneMute()) {
                this.mAudioManager.setMicrophoneMute(false);
            }
        }
        stopCallTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCallView() {
        if (isShowMiniCallView()) {
            PhoneCallViewBase phoneCallViewBase = this.mCurPhoneCallView;
            if ((phoneCallViewBase instanceof PhoneCallViewMini) || (phoneCallViewBase instanceof PhoneCallViewMiniEx)) {
                Log.d(TAG, "updateCallView: already mini.");
                return;
            }
            Log.d(TAG, "updateCallView: turning to mini.");
            this.mPhoneCallViewMini.updateCallTime(this.mCallTimeMap);
            this.mPhoneCallViewMini.updateAudioState(this.mAudioState);
            List<BluetoothHeadsetClientCall> currentCalls = getCurrentCalls();
            this.mPhoneCallViewMini.onActionCallStateChanged(currentCalls);
            this.mCurPhoneCallView.hideView();
            if (currentCalls == null || currentCalls.isEmpty()) {
                return;
            }
            PhoneCallViewBase phoneCallViewBase2 = this.mPhoneCallViewMini;
            this.mCurPhoneCallView = phoneCallViewBase2;
            phoneCallViewBase2.showView();
            return;
        }
        PhoneCallViewBase phoneCallViewBase3 = this.mCurPhoneCallView;
        if ((phoneCallViewBase3 instanceof PhoneCallView) || (phoneCallViewBase3 instanceof PhoneCallViewEx)) {
            Log.d(TAG, "updateCallView: already normal.");
            return;
        }
        Log.d(TAG, "updateCallView: turning to normal.");
        this.mPhoneCallView.updateCallTime(this.mCallTimeMap);
        this.mPhoneCallView.updateAudioState(this.mAudioState);
        List<BluetoothHeadsetClientCall> currentCalls2 = getCurrentCalls();
        this.mPhoneCallView.onActionCallStateChanged(currentCalls2);
        this.mCurPhoneCallView.hideView();
        if (currentCalls2 == null || currentCalls2.isEmpty()) {
            return;
        }
        PhoneCallViewBase phoneCallViewBase4 = this.mPhoneCallView;
        this.mCurPhoneCallView = phoneCallViewBase4;
        phoneCallViewBase4.showView();
    }

    private boolean isShowMiniCallView() {
        if (this.mPhoneCallViewMini == null) {
            return false;
        }
        int i = this.mSelectCallViewByManual;
        if (i == 0) {
            this.mSelectCallViewByManual = -1;
            return false;
        }
        if (i == 1) {
            this.mSelectCallViewByManual = -1;
            return true;
        }
        if (!Utils.isT5Platform()) {
            if (this.isReverseState || Utils.isNaviTopRunning(this.mContext)) {
                return true;
            }
            try {
                ComponentName componentName = ((ActivityManager) getSystemService("activity")).getRunningTasks(1).get(0).topActivity;
                if (NEED_MINI_CALL_VIEW_ACTIVITY.contains(componentName.getClassName())) {
                    return true;
                }
                if (NEED_MINI_CALL_VIEW_ACTIVITY1.contains(componentName.getClassName()) && Utils.getSystemProperty("zj.phonelink.type", "").equals("carlife_wired")) {
                    return true;
                }
            } catch (Exception unused) {
            }
            return false;
        }
        try {
            return PackageInfo.LAUNCHER_PACKAGE_NAME.equals(((ActivityManager) getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getPackageName()) && Utils.getSystemProperty("persist.sys.air.show_state", "0").equals("0");
        } catch (Exception unused2) {
        }
    }

    public void switchCallViewByManual(int i) {
        PhoneCallViewBase phoneCallViewBase = this.mCurPhoneCallView;
        if (phoneCallViewBase == null || !phoneCallViewBase.isAddView()) {
            return;
        }
        this.mSelectCallViewByManual = i;
        updateCallView();
    }

    public synchronized void dial(String str) {
        Log.i(TAG, "dial:" + str);
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mHeadsetClient;
        if (bluetoothHeadsetClient == null) {
            Log.e(TAG, "dial failed! mHeadsetClient null!!");
            return;
        }
        if (bluetoothHeadsetClient.getConnectedDevices().isEmpty()) {
            Log.e(TAG, "dial: getConnectedDevices empty!!");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "dial failed! number is empty!!");
            return;
        }
        List<BluetoothHeadsetClientCall> currentCalls = getCurrentCalls();
        if (currentCalls != null && currentCalls.size() >= 2) {
            Log.e(TAG, "dial failed! too many calls!!");
            return;
        }
        Iterator<BluetoothHeadsetClientCall> it = currentCalls.iterator();
        while (it.hasNext()) {
            if (it.next().getState() == 4) {
                Log.e(TAG, "dial failed! incoming state!!");
                return;
            }
        }
        if (this.mHandler.hasMessages(5)) {
            Log.e(TAG, "filter fast dial operation！！！");
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(5, 600L);
        this.mLastCall = str;
        this.mHeadsetClient.dial(this.mConnectedDevice, str);
        Log.d(TAG, "dial:" + str + " successful!!!");
    }

    public synchronized void sendDTMF(byte b) {
        Log.i(TAG, "sendDTMF:" + ((int) b));
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mHeadsetClient;
        if (bluetoothHeadsetClient != null) {
            bluetoothHeadsetClient.sendDTMF(this.mConnectedDevice, b);
        }
    }

    public synchronized void acceptCall(int i) {
        Log.i(TAG, "acceptCall");
        List<BluetoothHeadsetClientCall> currentCalls = getCurrentCalls();
        if (currentCalls != null && !currentCalls.isEmpty()) {
            if (this.mHeadsetClient != null) {
                Log.i(TAG, "acceptCall flag=" + i);
                this.mHeadsetClient.acceptCall(this.mConnectedDevice, i);
                this.mAcceptCallTime = System.currentTimeMillis();
            }
            return;
        }
        Log.e(TAG, "no call!");
    }

    public synchronized void hangup() {
        List<BluetoothHeadsetClientCall> currentCalls = getCurrentCalls();
        if (currentCalls != null && currentCalls.size() != 0) {
            List<BluetoothHeadsetClientCall> call = getCall(currentCalls, 4, 5);
            if (!call.isEmpty()) {
                Log.d(TAG, "hangup rejectCall: " + call.get(0).getNumber());
                this.mHeadsetClient.rejectCall(this.mConnectedDevice);
            } else {
                List<BluetoothHeadsetClientCall> call2 = getCall(currentCalls, 2, 3, 0);
                if (!call2.isEmpty()) {
                    Log.d(TAG, "hangup terminateCall: " + call2.get(0).getNumber());
                    this.mHeadsetClient.terminateCall(this.mConnectedDevice, call2.get(0));
                } else {
                    Log.d(TAG, "hangup rejectCall");
                    this.mHeadsetClient.rejectCall(this.mConnectedDevice);
                }
            }
            return;
        }
        Log.e(TAG, "hangup no call!");
    }

    public synchronized List<BluetoothHeadsetClientCall> getCurrentCalls() {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mHeadsetClient;
        if (bluetoothHeadsetClient == null) {
            return null;
        }
        List connectedDevices = bluetoothHeadsetClient.getConnectedDevices();
        if (connectedDevices != null && connectedDevices.size() != 0) {
            return this.mHeadsetClient.getCurrentCalls((BluetoothDevice) connectedDevices.get(0));
        }
        Log.e(TAG, "hf client is not connected!");
        return null;
    }

    public static synchronized List<BluetoothHeadsetClientCall> getCall(List<BluetoothHeadsetClientCall> list, int... iArr) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (BluetoothHeadsetClientCall bluetoothHeadsetClientCall : list) {
            for (int i : iArr) {
                if (bluetoothHeadsetClientCall.getState() == i) {
                    arrayList.add(bluetoothHeadsetClientCall);
                }
            }
        }
        return arrayList;
    }

    public synchronized boolean isAudioConnected() {
        Log.d(TAG, "isAudioConnected AudioState=" + this.mAudioState);
        return this.mAudioState == 2;
    }

    public int getAudioState() {
        return this.mAudioState;
    }

    public synchronized void switchAudio() {
        BluetoothHeadsetClient bluetoothHeadsetClient;
        if (this.mHandler.hasMessages(3)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(3, 600L);
        Log.i(TAG, "switchAudio mAudioState=" + this.mAudioState);
        int i = this.mAudioState;
        if (i == 0) {
            BluetoothHeadsetClient bluetoothHeadsetClient2 = this.mHeadsetClient;
            if (bluetoothHeadsetClient2 != null) {
                if (bluetoothHeadsetClient2.connectAudio(this.mConnectedDevice)) {
                    Log.i(TAG, "connectAudio Success!");
                } else {
                    Log.i(TAG, "connectAudio Failed!");
                }
            }
        } else if (i == 2 && (bluetoothHeadsetClient = this.mHeadsetClient) != null) {
            if (bluetoothHeadsetClient.disconnectAudio(this.mConnectedDevice)) {
                Log.i(TAG, "disconnectAudio Success!");
            } else {
                Log.i(TAG, "disconnectAudio Failed!");
            }
        }
    }

    public synchronized String getLastCall() {
        Log.i(TAG, "getLastCall:" + this.mLastCall);
        return this.mLastCall;
    }

    private void startAutoAnswer() {
        if (!BluetoothAdapterManagerService.getInstance().isBluetoothAutoAnswer() || this.mHandler.hasMessages(1)) {
            return;
        }
        Log.i(TAG, "start auto answer,after 5s will auto answer!");
        this.mHandler.sendEmptyMessageDelayed(1, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAutoAnswer() {
        Log.i(TAG, "cancelAutoAnswer!");
        this.mHandler.removeMessages(1);
    }

    private final class HfpBinder extends IBluetoothHfpclientService.Stub {
        private HfpBinder() {
        }

        /* synthetic */ HfpBinder(BluetoothHfpclientService bluetoothHfpclientService, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public boolean isAudioConnected() {
            return BluetoothHfpclientService.this.isAudioConnected();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void dial(String str) {
            BluetoothHfpclientService.this.dial(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void sendDTMF(byte b) {
            BluetoothHfpclientService.this.sendDTMF(b);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void acceptCall(int i) {
            BluetoothHfpclientService.this.acceptCall(i);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void hangup() {
            BluetoothHfpclientService.this.hangup();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public void switchAudio() {
            BluetoothHfpclientService.this.switchAudio();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothHfpclientService
        public String getLastCall() {
            return BluetoothHfpclientService.this.getLastCall();
        }
    }

    public void onSetCallViewEnable(boolean z) {
        PhoneCallViewBase phoneCallViewBase = this.mPhoneCallView;
        if (phoneCallViewBase != null) {
            phoneCallViewBase.setEnable(z);
        }
        PhoneCallViewBase phoneCallViewBase2 = this.mPhoneCallViewMini;
        if (phoneCallViewBase2 != null) {
            phoneCallViewBase2.setEnable(z);
        }
    }
}
