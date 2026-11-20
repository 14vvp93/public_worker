package com.hcn.bluetooth.service;

import android.app.Service;
import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAvrcpController;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.sourceservice.SourceInfo;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.KeyEvent;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.LocalBTMusicManager;
import com.hcn.bluetooth.api.MusicPlayState;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetoothservice.R;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothMusicService extends Service {
    private static final String A2DP_MBS_TAG = "A2dpMediaBrowserService";
    private static final String A2DP_MBS_TAG_Q = "BluetoothMediaBrowserService";
    private static final int CALL_A2DP_CONNECT_STATE_CHANGE = 3;
    private static final int CALL_AVRCP_CONNECT_STATE_CHANGE = 2;
    private static final int CALL_METADATA_CHANGE = 0;
    private static final int CALL_PLAYSTATE_CHANGE = 1;
    public static final String EASYCONN_REQUEST_A2DP = "net.easyconn.a2dp.acquire";
    private static final int MSG_ABANDON_AUDIO_FOCUS = 11;
    private static final int MSG_FILTER_FORWARD_REWIND = 1;
    private static final int MSG_FILTER_PREV_NEXT_CMD = 0;
    private static final int MSG_REQUEST_AUDIO_FOCUS = 10;
    private static final int MSG_REQUEST_AUTO_PLAY = 2;
    public static final String TAG = "BluetoothMusicService";
    public static final String VOICE_EVENT_EXIT = "com.btmusic.extra.exit";
    private static BluetoothMusicService sInstance;
    private RemoteCallbackList<IMusicCallback> mCallBackList;
    protected ComponentName mComponentName;
    private Context mContext;
    private Handler mMainHandler;
    private MediaMetadata mMediaMetadata;
    protected MediaSessionCompat mMediaSessionCompat;
    private MediaPlayer mPlayer;
    private MediaSessionManager.OnActiveSessionsChangedListener mSessionListener;
    private MediaSessionManager mSessionManager;
    private IBinder mMusicBinder = null;
    private MusicDeathRecipient mMusicDeathRecipient = null;
    private BluetoothAvrcpController mAvrcpService = null;
    private BluetoothA2dpSink mA2dpSinkService = null;
    private SourceInfo mSourceInfo = SourceInfo.getInstance();
    private int mAvrcpConnectState = 0;
    private int mA2dpConnectState = 0;
    private IBluetoothMusicService.Stub mBinder = null;
    private MediaController mMediaController = null;
    private MediaController.Callback mMediaCtrlCallback = null;
    private final MusicPlayState mMusicPlayState = new MusicPlayState();
    private String[] mID3Array = new String[3];
    private AudioManager mAudioManager = null;
    private final BluetoothProfile.ServiceListener mAvrcpServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.hcn.bluetooth.service.BluetoothMusicService.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d(BluetoothMusicService.TAG, "BluetoothAvrcp service connected");
            BluetoothMusicService.this.mAvrcpService = (BluetoothAvrcpController) bluetoothProfile;
            BluetoothMusicService bluetoothMusicService = BluetoothMusicService.this;
            bluetoothMusicService.mAvrcpConnectState = bluetoothMusicService.isAvrcpConnected() ? 2 : 0;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            Log.d(BluetoothMusicService.TAG, "BluetoothAvrcp service disconnected");
            BluetoothMusicService.this.mAvrcpService = null;
        }
    };
    private final BluetoothProfile.ServiceListener mA2dpServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.hcn.bluetooth.service.BluetoothMusicService.2
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d(BluetoothMusicService.TAG, "BluetoothA2dpSink service connected");
            BluetoothMusicService.this.mA2dpSinkService = (BluetoothA2dpSink) bluetoothProfile;
            BluetoothMusicService bluetoothMusicService = BluetoothMusicService.this;
            bluetoothMusicService.mA2dpConnectState = bluetoothMusicService.isA2dpConnected() ? 2 : 0;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            Log.d(BluetoothMusicService.TAG, "BluetoothA2dpSink service disconnected");
            BluetoothMusicService.this.mA2dpSinkService = null;
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.hcn.bluetooth.service.BluetoothMusicService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.bluetooth.a2dp-sink.profile.action.PLAYING_STATE_CHANGED")) {
                intent.getIntExtra("android.bluetooth.profile.extra.STATE", 11);
                return;
            }
            if (action.equals(LocalBTMusicManager.ACTION_CONNECTION_STATE_CHANGED)) {
                BluetoothMusicService.this.handleA2dpStateChanged(intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0), (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                return;
            }
            if (action.equals("android.bluetooth.avrcp-controller.profile.action.CONNECTION_STATE_CHANGED")) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothMusicService.this.handleAvrcpStateChanged(intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0), bluetoothDevice);
                return;
            }
            if (action.equals(LocalBTMusicManager.ACTION_TRACK_EVENT)) {
                return;
            }
            if (action.equals(BluetoothMusicService.VOICE_EVENT_EXIT)) {
                Log.d(BluetoothMusicService.TAG, "onReceive: VOICE_EVENT_EXIT");
                BluetoothMusicService.this.send_Avrcp_Cmd(3);
                return;
            }
            if (action.equals("a2dp_sink_audio_focus_change")) {
                int intExtra = intent.getIntExtra("extra_audio_state", -1);
                if (intExtra == 1) {
                    Log.d(BluetoothMusicService.TAG, "onReceive: AUDIOFOCUS_GAIN");
                    BluetoothMusicService.this.mMainHandler.removeMessages(10);
                    BluetoothMusicService.this.registerMediaButtonEvent();
                    return;
                } else {
                    if (intExtra == -1) {
                        Log.d(BluetoothMusicService.TAG, "onReceive: AUDIOFOCUS_LOSS");
                        BluetoothMusicService.this.mMainHandler.removeMessages(10);
                        BluetoothMusicService.this.unregisterMediaButtonEvent();
                        return;
                    }
                    return;
                }
            }
            if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                if (intExtra2 == 10 || intExtra2 == 13) {
                    if (BluetoothMusicService.this.mA2dpConnectState != 0) {
                        BluetoothMusicService.this.handleA2dpStateChanged(0, null);
                    }
                    if (BluetoothMusicService.this.mAvrcpConnectState != 0) {
                        BluetoothMusicService.this.handleAvrcpStateChanged(0, null);
                        return;
                    }
                    return;
                }
                return;
            }
            if (action.equals(BluetoothMusicService.EASYCONN_REQUEST_A2DP)) {
                Log.d(BluetoothMusicService.TAG, "onReceive: EASYCONN_REQUEST_A2DP requestA2dp");
                BluetoothMusicService.this.requestA2dp();
            }
        }
    };

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 2) {
                if (Utils.isBTMusicAutoPlay()) {
                    String sourcePackage = BluetoothMusicService.this.mSourceInfo.getSourcePackage();
                    Log.v(BluetoothMusicService.TAG, "get connected source package:" + sourcePackage);
                    if ("com.autochips.bluetooth".equals(sourcePackage)) {
                        BluetoothMusicService.this.send_Avrcp_Cmd(0);
                        Log.v(BluetoothMusicService.TAG, "start a2dp success!");
                        return;
                    }
                    return;
                }
                return;
            }
            if (i != 10) {
                if (i != 11) {
                    return;
                }
                Log.d(BluetoothMusicService.TAG, "handleMessage: MSG_ABANDON_AUDIO_FOCUS");
                BluetoothMusicService.this.abandonAudioFocus();
                BluetoothMusicService.this.unregisterMediaButtonEvent();
                return;
            }
            if (!BluetoothMusicService.this.isGainAudioFocus()) {
                Log.d(BluetoothMusicService.TAG, "handleMessage: MSG_REQUEST_AUDIO_FOCUS requestAudioFocus");
                BluetoothMusicService.this.requestAudioFocus();
                BluetoothMusicService.this.mMainHandler.sendEmptyMessageDelayed(10, 1000L);
                return;
            }
            Log.d(BluetoothMusicService.TAG, "handleMessage: MSG_REQUEST_AUDIO_FOCUS Gain!!");
        }
    }

    public static synchronized BluetoothMusicService getInstance() {
        return sInstance;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "onCreate");
        init();
        initSessionManager();
        super.onCreate();
    }

    public void init() {
        Log.i(TAG, "init");
        sInstance = this;
        this.mContext = getApplicationContext();
        this.mCallBackList = new RemoteCallbackList<>();
        this.mAudioManager = (AudioManager) getSystemService("audio");
        this.mMainHandler = new MainHandler(Looper.getMainLooper());
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(this.mContext, this.mA2dpServiceListener, 11);
            defaultAdapter.getProfileProxy(this.mContext, this.mAvrcpServiceListener, 12);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocalBTMusicManager.ACTION_TRACK_EVENT);
        intentFilter.addAction(VOICE_EVENT_EXIT);
        intentFilter.addAction("android.bluetooth.a2dp-sink.profile.action.PLAYING_STATE_CHANGED");
        intentFilter.addAction(LocalBTMusicManager.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction("android.bluetooth.avrcp-controller.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("a2dp_sink_audio_focus_change");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction(EASYCONN_REQUEST_A2DP);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    private void initSessionManager() {
        this.mSessionManager = (MediaSessionManager) getSystemService("media_session");
        this.mMediaCtrlCallback = new MediaControllerCallback();
        SessionChangeListener sessionChangeListener = new SessionChangeListener();
        this.mSessionListener = sessionChangeListener;
        MediaSessionManager mediaSessionManager = this.mSessionManager;
        if (mediaSessionManager != null) {
            mediaSessionManager.addOnActiveSessionsChangedListener(sessionChangeListener, null, this.mMainHandler);
            List<MediaController> activeSessions = this.mSessionManager.getActiveSessions(null);
            Log.d(TAG, " Num Sessions " + activeSessions.size());
            for (int i = 0; i < activeSessions.size(); i++) {
                Log.d(TAG, "Active session : " + i + activeSessions.get(i).getPackageName() + activeSessions.get(i).getTag());
                MediaController mediaController = activeSessions.get(i);
                if (mediaController.getTag().contains(A2DP_MBS_TAG) || mediaController.getTag().contains(A2DP_MBS_TAG_Q)) {
                    Log.d(TAG, "onCreate:contains A2DP_MBS_TAG");
                    setCurrentMediaController(mediaController);
                    PlaybackState playbackState = mediaController.getPlaybackState();
                    if (playbackState != null) {
                        this.mMusicPlayState.setState(playbackState.getState());
                        this.mMusicPlayState.setPlaybackSpeed(playbackState.getPlaybackSpeed());
                        this.mMusicPlayState.setBufferedPosition(playbackState.getBufferedPosition());
                        this.mMusicPlayState.setPosition(playbackState.getPosition());
                        this.mMusicPlayState.setLastPositionUpdateTime(playbackState.getLastPositionUpdateTime());
                    }
                }
            }
        }
    }

    private void unInitSessionManager() {
        MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener;
        MediaController.Callback callback;
        MediaController mediaController = this.mMediaController;
        if (mediaController != null && (callback = this.mMediaCtrlCallback) != null) {
            mediaController.unregisterCallback(callback);
        }
        MediaSessionManager mediaSessionManager = this.mSessionManager;
        if (mediaSessionManager == null || (onActiveSessionsChangedListener = this.mSessionListener) == null) {
            return;
        }
        mediaSessionManager.removeOnActiveSessionsChangedListener(onActiveSessionsChangedListener);
    }

    private void unInit() {
        this.mContext.unregisterReceiver(this.mReceiver);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.closeProfileProxy(11, this.mA2dpSinkService);
            defaultAdapter.closeProfileProxy(12, this.mAvrcpService);
        }
        unInitSessionManager();
        if (this.mBinder != null) {
            this.mBinder = null;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        unInit();
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        if (this.mBinder == null) {
            this.mBinder = new BluetoothMusicBinder();
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    private class MediaControllerCallback extends MediaController.Callback {
        private MediaControllerCallback() {
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            if (playbackState != null) {
                if (BluetoothMusicService.this.mMediaMetadata == null) {
                    BluetoothMusicService bluetoothMusicService = BluetoothMusicService.this;
                    bluetoothMusicService.mMediaMetadata = bluetoothMusicService.mMediaController.getMetadata();
                    if (BluetoothMusicService.this.mMediaMetadata != null) {
                        BluetoothMusicService.this.handleMetadataChange();
                    }
                }
                BluetoothMusicService.this.mMusicPlayState.setState(playbackState.getState());
                BluetoothMusicService.this.mMusicPlayState.setPlaybackSpeed(playbackState.getPlaybackSpeed());
                BluetoothMusicService.this.mMusicPlayState.setBufferedPosition(playbackState.getBufferedPosition());
                BluetoothMusicService.this.mMusicPlayState.setPosition(playbackState.getPosition());
                BluetoothMusicService.this.mMusicPlayState.setLastPositionUpdateTime(playbackState.getLastPositionUpdateTime());
                if (BluetoothMusicService.this.mMusicPlayState.getPosition() <= BluetoothMusicService.this.mMusicPlayState.getDuration()) {
                    BluetoothMusicService.this.callListener(1);
                    return;
                }
                return;
            }
            Log.e(BluetoothMusicService.TAG, " onPlaybackStateChanged...state==null");
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            Log.d(BluetoothMusicService.TAG, " onMetadataChanged ");
            BluetoothMusicService.this.mMediaMetadata = mediaMetadata;
            BluetoothMusicService.this.handleMetadataChange();
        }
    }

    class SessionChangeListener implements MediaSessionManager.OnActiveSessionsChangedListener {
        SessionChangeListener() {
        }

        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(List<MediaController> list) {
            Log.d(BluetoothMusicService.TAG, " onActiveSessionsChanged : " + list.size());
            for (int i = 0; i < list.size(); i++) {
                MediaController mediaController = list.get(i);
                Log.d(BluetoothMusicService.TAG, "onActiveSessionsChanged : " + i + " " + mediaController.getPackageName() + " Tag=" + mediaController.getTag());
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                MediaController mediaController2 = list.get(i2);
                if (mediaController2.getTag().contains(BluetoothMusicService.A2DP_MBS_TAG) || mediaController2.getTag().contains(BluetoothMusicService.A2DP_MBS_TAG_Q)) {
                    Log.d(BluetoothMusicService.TAG, "onActiveSessionsChanged:contains A2DP_MBS_TAG");
                    BluetoothMusicService.this.setCurrentMediaController(mediaController2);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentMediaController(MediaController mediaController) {
        Log.d(TAG, "setCurrentMediaController mMediaController=" + this.mMediaController + " controller=" + mediaController);
        if (mediaController == null) {
            Log.e(TAG, "setCurrentMediaController null!!!!");
        }
        MediaController mediaController2 = this.mMediaController;
        if (mediaController2 == null && mediaController != null) {
            Log.d(TAG, " Set MediaController " + mediaController.getTag());
            this.mMediaController = mediaController;
            mediaController.registerCallback(this.mMediaCtrlCallback);
            return;
        }
        if (mediaController2 == null || mediaController == null) {
            if (mediaController2 == null || mediaController != null) {
                return;
            }
            Log.d(TAG, " Clear MediaController " + this.mMediaController.getTag());
            this.mMediaController.unregisterCallback(this.mMediaCtrlCallback);
            this.mMediaController = mediaController;
            return;
        }
        if (mediaController.getSessionToken().equals(this.mMediaController.getSessionToken())) {
            return;
        }
        Log.d(TAG, " update a new MediaController " + mediaController.getTag());
        this.mMediaController.unregisterCallback(this.mMediaCtrlCallback);
        this.mMediaController = mediaController;
        mediaController.registerCallback(this.mMediaCtrlCallback, this.mMainHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMetadataChange() {
        MediaMetadata mediaMetadata = this.mMediaMetadata;
        if (mediaMetadata != null) {
            try {
                this.mID3Array[0] = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE).trim();
            } catch (Exception unused) {
                this.mID3Array[0] = "";
            }
            try {
                this.mID3Array[1] = this.mMediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST).trim();
            } catch (Exception unused2) {
                this.mID3Array[1] = "";
            }
            try {
                this.mID3Array[2] = this.mMediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM).trim();
            } catch (Exception unused3) {
                this.mID3Array[2] = "";
            }
            if (this.mMediaMetadata.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                this.mMusicPlayState.setDuration(this.mMediaMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
            }
            callListener(0);
        }
    }

    public boolean requestAudioFocus() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mA2dpSinkService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        bluetoothA2dpSink.controlAudioFocus(true);
        return true;
    }

    public boolean abandonAudioFocus() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mA2dpSinkService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        bluetoothA2dpSink.controlAudioFocus(false);
        return true;
    }

    public boolean isGainAudioFocus() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mA2dpSinkService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        return bluetoothA2dpSink.isGainAudioFocus();
    }

    public void onMediaKeyEvent(Intent intent) {
        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        if (keyEvent == null) {
        }
        int keyCode = keyEvent.getKeyCode();
        int action = keyEvent.getAction();
        Log.d(TAG, "onMediaKeyEvent: keycode=" + keyCode + " action=" + action);
        if (action != 1) {
            if (action == 0) {
                if (keyCode == 89) {
                    send_Avrcp_Cmd(7);
                    return;
                } else {
                    if (keyCode != 90) {
                        return;
                    }
                    send_Avrcp_Cmd(6);
                    return;
                }
            }
            return;
        }
        if (keyCode == 126) {
            send_Avrcp_Cmd(0);
            return;
        }
        if (keyCode == 127) {
            send_Avrcp_Cmd(1);
            return;
        }
        switch (keyCode) {
            case 85:
                send_Avrcp_Cmd(2);
                break;
            case R.styleable.AppCompatTheme_panelBackground /* 86 */:
                send_Avrcp_Cmd(3);
                break;
            case 87:
                send_Avrcp_Cmd(4);
                break;
            case 88:
                send_Avrcp_Cmd(5);
                break;
        }
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {
        private Reference<Context> mContextRef;

        public MediaSessionCallback(Context context) {
            this.mContextRef = new WeakReference(context);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public boolean onMediaButtonEvent(Intent intent) {
            boolean zOnMediaButtonEvent = super.onMediaButtonEvent(intent);
            if (!zOnMediaButtonEvent) {
                BluetoothMusicService.this.onMediaKeyEvent(intent);
            }
            return zOnMediaButtonEvent;
        }
    }

    private boolean playZeroMusic() {
        stopZeroMusic();
        Log.d(TAG, "playZeroMusic");
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mPlayer = mediaPlayer;
        try {
            mediaPlayer.setDataSource("system/media/audio/ringtones/zero.mp3");
            this.mPlayer.prepare();
            this.mPlayer.start();
            return true;
        } catch (IOException | IllegalArgumentException | IllegalStateException | SecurityException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean stopZeroMusic() {
        Log.d(TAG, "stopZeroMusic.");
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return true;
        }
        if (mediaPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer.reset();
        this.mPlayer.release();
        this.mPlayer = null;
        return true;
    }

    void registerBTMusicCallback(IMusicCallback iMusicCallback) {
        if (iMusicCallback == null) {
            return;
        }
        this.mCallBackList.register(iMusicCallback);
        Log.d(TAG, "registerBTMusicCallback: size=" + this.mCallBackList.getRegisteredCallbackCount());
        try {
            String[] strArr = this.mID3Array;
            iMusicCallback.onMetadataChanged(strArr[0], strArr[1], strArr[2]);
            iMusicCallback.onPlayStatusChanged(this.mMusicPlayState);
        } catch (RemoteException unused) {
            Log.e(TAG, "registerBTMusicCallback: call back error!!!");
        }
    }

    public void unregisterBTMusicCallback(IMusicCallback iMusicCallback) {
        if (iMusicCallback == null) {
            return;
        }
        this.mCallBackList.unregister(iMusicCallback);
        Log.d(TAG, "unregisterBTMusicCallback: size=" + this.mCallBackList.getRegisteredCallbackCount());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void callListener(int i) {
        RemoteCallbackList<IMusicCallback> remoteCallbackList;
        int iBeginBroadcast = this.mCallBackList.beginBroadcast();
        for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
            try {
                try {
                    IMusicCallback iMusicCallback = (IMusicCallback) this.mCallBackList.getBroadcastItem(i2);
                    if (i == 0) {
                        String[] strArr = this.mID3Array;
                        iMusicCallback.onMetadataChanged(strArr[0], strArr[1], strArr[2]);
                    } else if (i == 1) {
                        iMusicCallback.onPlayStatusChanged(this.mMusicPlayState);
                    } else if (i == 2) {
                        iMusicCallback.onAvrcpConnectStateChanged(this.mAvrcpConnectState);
                    } else if (i == 3) {
                        iMusicCallback.onA2dpConnectStateChanged(this.mA2dpConnectState);
                    }
                } catch (RemoteException unused) {
                    remoteCallbackList = this.mCallBackList;
                }
            } catch (Throwable th) {
                this.mCallBackList.finishBroadcast();
                throw th;
            }
        }
        remoteCallbackList = this.mCallBackList;
        remoteCallbackList.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleA2dpStateChanged(int i, BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "A2dpStateChanged:" + i);
        this.mA2dpConnectState = i;
        callListener(3);
        this.mMainHandler.removeMessages(2);
        if (i == 0) {
            this.mMusicPlayState.reset();
            callListener(1);
            String[] strArr = this.mID3Array;
            strArr[0] = "";
            strArr[1] = "";
            strArr[2] = "";
            callListener(0);
            return;
        }
        if (i == 2 && SystemProperties.get("sys.sourcing.package", "").equals("com.autochips.bluetooth")) {
            Log.d(TAG, "A2dpStateChanged:STATE_CONNECTED requestA2dp");
            requestA2dp();
            this.mMainHandler.sendEmptyMessage(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAvrcpStateChanged(int i, BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "AvrcpStateChanged:" + i);
        this.mAvrcpConnectState = i;
        callListener(2);
        this.mMainHandler.removeMessages(2);
        if (i == 2) {
            this.mMainHandler.sendEmptyMessage(2);
        }
    }

    public synchronized boolean isA2dpConnected() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mA2dpSinkService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        List connectedDevices = bluetoothA2dpSink.getConnectedDevices();
        if (connectedDevices == null) {
            return false;
        }
        return connectedDevices.size() > 0;
    }

    public synchronized boolean isAvrcpConnected() {
        BluetoothAvrcpController bluetoothAvrcpController = this.mAvrcpService;
        if (bluetoothAvrcpController == null) {
            return false;
        }
        List connectedDevices = bluetoothAvrcpController.getConnectedDevices();
        if (connectedDevices == null) {
            return false;
        }
        return connectedDevices.size() > 0;
    }

    public synchronized boolean isA2dpPlaying() {
        return this.mMusicPlayState.getState() == 3;
    }

    public synchronized BluetoothDeviceInfo getConnectDevice() {
        BluetoothA2dpSink bluetoothA2dpSink = this.mA2dpSinkService;
        if (bluetoothA2dpSink == null) {
            return null;
        }
        List connectedDevices = bluetoothA2dpSink.getConnectedDevices();
        if (connectedDevices.isEmpty()) {
            return null;
        }
        return new BluetoothDeviceInfo((BluetoothDevice) connectedDevices.get(0));
    }

    public synchronized void send_Avrcp_Cmd(int i) {
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            Log.e(TAG, "send_Avrcp_Cmd: mMediaController null");
            return;
        }
        if (this.mA2dpConnectState != 2) {
            Log.e(TAG, "send_Avrcp_Cmd: A2dp is not connected");
            return;
        }
        MediaController.TransportControls transportControls = mediaController.getTransportControls();
        if (transportControls == null) {
            Log.e(TAG, "send_Avrcp_Cmd: TransportControls null");
            return;
        }
        Log.d(TAG, "send_Avrcp_Cmd: " + i);
        switch (i) {
            case 0:
                transportControls.play();
                break;
            case 1:
                transportControls.pause();
                break;
            case 2:
                if (this.mMusicPlayState.getState() == 3) {
                    transportControls.pause();
                } else {
                    transportControls.play();
                }
                break;
            case 3:
                transportControls.stop();
                break;
            case 4:
                if (!this.mMainHandler.hasMessages(0)) {
                    this.mMainHandler.sendEmptyMessageDelayed(0, 400L);
                    transportControls.skipToNext();
                }
                break;
            case 5:
                if (!this.mMainHandler.hasMessages(0)) {
                    this.mMainHandler.sendEmptyMessageDelayed(0, 400L);
                    transportControls.skipToPrevious();
                }
                break;
            case 6:
                if (!this.mMainHandler.hasMessages(1)) {
                    this.mMainHandler.sendEmptyMessageDelayed(1, 400L);
                    transportControls.fastForward();
                }
                break;
            case 7:
                if (!this.mMainHandler.hasMessages(1)) {
                    this.mMainHandler.sendEmptyMessageDelayed(1, 400L);
                    transportControls.rewind();
                }
                break;
        }
    }

    public synchronized void requestA2dp() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && defaultAdapter.getState() == 12) {
            Log.d(TAG, "requestA2dp");
            this.mMainHandler.removeMessages(10);
            this.mMainHandler.sendEmptyMessage(10);
            return;
        }
        Log.d(TAG, "requestA2dp return!!");
    }

    public synchronized void releaseA2dp() {
        Log.d(TAG, "releaseA2dp");
        this.mMainHandler.removeMessages(10);
        this.mMainHandler.removeMessages(11);
        this.mMainHandler.sendEmptyMessage(11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerMediaButtonEvent() {
        if (Build.VERSION.SDK_INT >= 28) {
            if (this.mMediaSessionCompat == null) {
                MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this.mContext, TAG);
                this.mMediaSessionCompat = mediaSessionCompat;
                mediaSessionCompat.setMediaButtonReceiver(null);
                this.mMediaSessionCompat.setCallback(new MediaSessionCallback(this.mContext));
            }
            this.mMediaSessionCompat.setActive(true);
        } else {
            if (this.mComponentName == null) {
                this.mComponentName = new ComponentName(getPackageName(), BootReceiver.class.getName());
            }
            this.mAudioManager.registerMediaButtonEventReceiver(this.mComponentName);
        }
        playZeroMusic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterMediaButtonEvent() {
        if (Build.VERSION.SDK_INT >= 28) {
            MediaSessionCompat mediaSessionCompat = this.mMediaSessionCompat;
            if (mediaSessionCompat != null) {
                mediaSessionCompat.release();
                this.mMediaSessionCompat = null;
            }
        } else {
            ComponentName componentName = this.mComponentName;
            if (componentName != null) {
                this.mAudioManager.unregisterMediaButtonEventReceiver(componentName);
                this.mComponentName = null;
            }
        }
        stopZeroMusic();
    }

    public synchronized void regMusicClientBinder(IBinder iBinder) throws RemoteException {
        Log.i(TAG, "regMusicClientBinder");
        this.mMusicBinder = iBinder;
        if (iBinder != null) {
            if (this.mMusicDeathRecipient == null) {
                this.mMusicDeathRecipient = new MusicDeathRecipient();
            }
            this.mMusicBinder.linkToDeath(this.mMusicDeathRecipient, 0);
        }
    }

    public synchronized void unRegMusicClientBinder() {
        MusicDeathRecipient musicDeathRecipient;
        Log.i(TAG, "unRegMusicClientBinder");
        IBinder iBinder = this.mMusicBinder;
        if (iBinder != null && (musicDeathRecipient = this.mMusicDeathRecipient) != null) {
            iBinder.unlinkToDeath(musicDeathRecipient, 0);
        }
        this.mMusicDeathRecipient = null;
        this.mMusicBinder = null;
        if (this.mMusicPlayState.getState() == 3) {
            send_Avrcp_Cmd(3);
        }
        releaseA2dp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getID3Info() {
        return this.mID3Array;
    }

    class MusicDeathRecipient implements IBinder.DeathRecipient {
        MusicDeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            BluetoothMusicService.this.unRegMusicClientBinder();
            Log.i(BluetoothMusicService.TAG, "MusicDeathRecipient binderDied");
        }
    }

    private final class BluetoothMusicBinder extends IBluetoothMusicService.Stub {
        private BluetoothMusicBinder() {
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public boolean isA2dpConnected() {
            return BluetoothMusicService.this.isA2dpConnected();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public boolean isAvrcpConnected() {
            return BluetoothMusicService.this.isAvrcpConnected();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public boolean isA2dpPlaying() {
            return BluetoothMusicService.this.isA2dpPlaying();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public BluetoothDeviceInfo getConnectDevice() {
            return BluetoothMusicService.this.getConnectDevice();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void send_Avrcp_Cmd(int i) {
            BluetoothMusicService.this.send_Avrcp_Cmd(i);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void requestA2dp() {
            BluetoothMusicService.this.mSourceInfo.onRequestPlayAudio("com.autochips.bluetooth/.BtMusicActivity");
            BluetoothMusicService.this.requestA2dp();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void releaseA2dp() {
            BluetoothMusicService.this.releaseA2dp();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void regMusicClientBinder(IBinder iBinder) throws RemoteException {
            BluetoothMusicService.this.regMusicClientBinder(iBinder);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void unRegMusicClientBinder() {
            BluetoothMusicService.this.unRegMusicClientBinder();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void registerBTMusicCallback(IMusicCallback iMusicCallback) {
            BluetoothMusicService.this.registerBTMusicCallback(iMusicCallback);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public void unregisterBTMusicCallback(IMusicCallback iMusicCallback) {
            BluetoothMusicService.this.unregisterBTMusicCallback(iMusicCallback);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothMusicService
        public String[] getID3Info() {
            return BluetoothMusicService.this.getID3Info();
        }
    }
}
