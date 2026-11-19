package com.autochips.bluetooth;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.auto.theme.utils.ThemeUtils;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBTMusicManager;
import com.hcn.bluetooth.api.MusicPlayState;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.service.IMusicCallback;
import com.hcn.skin.support.app.SkinCompatActivity;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class BtMusicActivity extends SkinCompatActivity implements View.OnClickListener {
    public static final int MSG_A2DP_CONNECT_STATE_CHANGE = 2;
    public static final int MSG_AVRCP_CONNECT_STATE_CHANGE = 3;
    public static final int MSG_UPDATE_METADATA = 1;
    public static final int MSG_UPDATE_PLAY_STATE = 5;
    public static final int MSG_UPDATE_SEEKBAR = 4;
    private static final String TAG = "BTMusicActivity";
    public static final String VOICE_EVENT_EXIT = "com.btmusic.extra.exit";
    private TextView a2dpsinkStateInfo;
    private TextView avrcpctStateInfo;
    private Button btnPause;
    private Button btnPlay;
    private ImageButton btnStartBt;
    private ImageButton btnVolDown;
    private ImageButton btnVolUp;
    private Button btn_music_eq;
    private ImageView connectState;
    LocalBTMusicManager mBluetoothMusicManager;
    private MusicPlayState mLastMusicPlayState;
    private TextView mediaAlbumInfo;
    private TextView mediaArtistInfo;
    private TextView mediaBtName;
    private TextView mediaLengthInfo;
    private TextView mediaPlayingPositionInfo;
    private TextView mediaTitleInfo;
    private ProgressBar mMusicPlayingProgressBar = null;
    private ImageView playingAnimation = null;
    private ObjectAnimator mRotateAnim = null;
    public int mA2dpsinkstate = 0;
    public int mAvrcpctstate = 0;
    private String musicTitle = SkinID.SKIN_NONE;
    private String musicArtist = SkinID.SKIN_NONE;
    private String musicAlbum = SkinID.SKIN_NONE;
    private int musicDuration = 0;
    private int musicPosition = 0;
    private AudioManager mAudioManager = null;
    private MainHandler mMainHandler = null;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.BtMusicActivity.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BtMusicActivity.VOICE_EVENT_EXIT)) {
                BtMusicActivity.this.exit();
            }
        }
    };
    private final IMusicCallback mBTMusicCallback = new IMusicCallback.Stub() { // from class: com.autochips.bluetooth.BtMusicActivity.3
        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onA2dpConnectStateChanged(int i) throws RemoteException {
            Log.d(BtMusicActivity.TAG, "onA2dpConnectStateChanged:" + i);
            BtMusicActivity.this.mA2dpsinkstate = i;
            BtMusicActivity.this.mMainHandler.sendEmptyMessage(2);
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onAvrcpConnectStateChanged(int i) throws RemoteException {
            Log.d(BtMusicActivity.TAG, "onAvrcpConnectStateChanged:" + i);
            BtMusicActivity.this.mAvrcpctstate = i;
            BtMusicActivity.this.mMainHandler.sendEmptyMessage(3);
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onMetadataChanged(String str, String str2, String str3) throws RemoteException {
            Log.d(BtMusicActivity.TAG, "onMetadataChanged: title=" + str + " artist=" + str2 + " album=" + str3);
            BtMusicActivity.this.musicTitle = str;
            BtMusicActivity.this.musicArtist = str2;
            BtMusicActivity.this.musicAlbum = str3;
            BtMusicActivity.this.mMainHandler.sendEmptyMessage(1);
        }

        @Override // com.hcn.bluetooth.service.IMusicCallback
        public void onPlayStatusChanged(MusicPlayState musicPlayState) throws RemoteException {
            if (musicPlayState == null) {
                Log.e(BtMusicActivity.TAG, "onPlayStatusChanged: null!!");
                return;
            }
            Log.d(BtMusicActivity.TAG, "onPlayStatusChanged:" + musicPlayState.toString());
            int state = musicPlayState.getState();
            if (BtMusicActivity.this.mLastMusicPlayState == null || state != 2) {
                BtMusicActivity.this.mLastMusicPlayState = musicPlayState;
                BtMusicActivity.this.mMainHandler.removeMessages(5);
                BtMusicActivity.this.mMainHandler.sendEmptyMessage(5);
                BtMusicActivity.this.mMainHandler.removeMessages(4);
                BtMusicActivity.this.mMainHandler.sendEmptyMessage(4);
                return;
            }
            BtMusicActivity.this.mLastMusicPlayState = musicPlayState;
            BtMusicActivity.this.mMainHandler.removeMessages(4);
            BtMusicActivity.this.mMainHandler.removeMessages(5);
            BtMusicActivity.this.mMainHandler.sendEmptyMessage(5);
        }
    };
    ConnectionListener mConnectionListener = new ConnectionListener() { // from class: com.autochips.bluetooth.BtMusicActivity.4
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            Log.d(BtMusicActivity.TAG, "BluetoothMusic onServiceConnected");
            BtMusicActivity.this.mBluetoothMusicManager.regMusicClientBinder(MyApplication.getInstance().getBinder());
            BtMusicActivity.this.mBluetoothMusicManager.registerBTMusicCallback(BtMusicActivity.this.mBTMusicCallback);
            BtMusicActivity.this.mBluetoothMusicManager.requestA2dp();
            boolean zIsAvrcpConnected = BtMusicActivity.this.mBluetoothMusicManager.isAvrcpConnected();
            if (BtMusicActivity.this.mBluetoothMusicManager.isA2dpConnected()) {
                BtMusicActivity.this.mA2dpsinkstate = 2;
            } else {
                BtMusicActivity.this.mA2dpsinkstate = 0;
            }
            if (zIsAvrcpConnected) {
                BtMusicActivity.this.mAvrcpctstate = 2;
            } else {
                BtMusicActivity.this.mAvrcpctstate = 0;
            }
            BtMusicActivity btMusicActivity = BtMusicActivity.this;
            btMusicActivity.updateA2dpConnectStatus(btMusicActivity.mA2dpsinkstate);
            BtMusicActivity btMusicActivity2 = BtMusicActivity.this;
            btMusicActivity2.updateAvrcpConnectStatus(btMusicActivity2.mAvrcpctstate);
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
            Log.d(BtMusicActivity.TAG, "BluetoothMusic onServiceDisconnected");
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
            if (i == 1) {
                BtMusicActivity btMusicActivity = BtMusicActivity.this;
                btMusicActivity.updateMetadata(btMusicActivity.musicTitle, BtMusicActivity.this.musicArtist, BtMusicActivity.this.musicAlbum);
                return;
            }
            if (i == 2) {
                BtMusicActivity btMusicActivity2 = BtMusicActivity.this;
                btMusicActivity2.updateA2dpConnectStatus(btMusicActivity2.mA2dpsinkstate);
                return;
            }
            if (i == 3) {
                BtMusicActivity btMusicActivity3 = BtMusicActivity.this;
                btMusicActivity3.updateAvrcpConnectStatus(btMusicActivity3.mAvrcpctstate);
                return;
            }
            if (i != 4) {
                if (i == 5 && BtMusicActivity.this.mLastMusicPlayState != null) {
                    BtMusicActivity btMusicActivity4 = BtMusicActivity.this;
                    btMusicActivity4.updatePlayPauseButton(btMusicActivity4.mLastMusicPlayState.getState());
                    return;
                }
                return;
            }
            BtMusicActivity.this.updateMusicPlayingProgress();
            if (BtMusicActivity.this.mLastMusicPlayState == null || BtMusicActivity.this.mLastMusicPlayState.getState() != 3) {
                return;
            }
            sendEmptyMessageDelayed(4, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMusicPlayingProgress() {
        MusicPlayState musicPlayState = this.mLastMusicPlayState;
        if (musicPlayState != null) {
            if (musicPlayState.getState() == 3) {
                this.musicPosition = (int) (this.mLastMusicPlayState.getPosition() * 0.001f);
                this.musicPosition = (int) (this.musicPosition + (((int) ((SystemClock.elapsedRealtime() - this.mLastMusicPlayState.getLastPositionUpdateTime()) * 0.001f)) * this.mLastMusicPlayState.getPlaybackSpeed()));
                int duration = (int) (this.mLastMusicPlayState.getDuration() * 0.001f);
                this.musicDuration = duration;
                if (this.musicPosition > duration) {
                    this.musicPosition = duration;
                }
            } else {
                this.musicDuration = (int) (this.mLastMusicPlayState.getDuration() * 0.001f);
                this.musicPosition = (int) (this.mLastMusicPlayState.getPosition() * 0.001f);
            }
        }
        String elapsedTime = DateUtils.formatElapsedTime(this.musicPosition);
        String elapsedTime2 = DateUtils.formatElapsedTime(this.musicDuration);
        Log.d(TAG, "updateProgress: pos=" + elapsedTime + " duration=" + elapsedTime2);
        this.mediaLengthInfo.setText(elapsedTime2);
        this.mediaPlayingPositionInfo.setText(elapsedTime);
        this.mMusicPlayingProgressBar.setMax(this.musicDuration);
        this.mMusicPlayingProgressBar.setProgress(this.musicPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMetadata(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str)) {
            this.mediaTitleInfo.setText(str);
        } else {
            this.mediaTitleInfo.setText(SkinUtils.getString(C0414R.string.str_music_title));
        }
        if (!TextUtils.isEmpty(str2)) {
            this.mediaArtistInfo.setText(str2);
        } else {
            this.mediaArtistInfo.setText(SkinUtils.getString(C0414R.string.str_music_artist));
        }
        if (!TextUtils.isEmpty(str3)) {
            this.mediaAlbumInfo.setText(str3);
        } else {
            this.mediaAlbumInfo.setText(SkinUtils.getString(C0414R.string.str_music_album));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayPauseButton(int i) {
        if (i == 3) {
            this.btnPause.setVisibility(0);
            this.btnPlay.setVisibility(8);
            ObjectAnimator objectAnimator = this.mRotateAnim;
            if (objectAnimator != null) {
                objectAnimator.resume();
                return;
            }
            return;
        }
        this.btnPause.setVisibility(8);
        this.btnPlay.setVisibility(0);
        ObjectAnimator objectAnimator2 = this.mRotateAnim;
        if (objectAnimator2 != null) {
            objectAnimator2.pause();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateA2dpConnectStatus(int i) {
        LocalBTMusicManager localBTMusicManager;
        if (i == 2) {
            this.a2dpsinkStateInfo.setText(SkinUtils.getString(C0414R.string.a2dpsink_status_connected_info));
            this.connectState.setBackground(SkinUtils.getDrawable(C0414R.drawable.bt_connect));
            if (this.mediaBtName == null || (localBTMusicManager = this.mBluetoothMusicManager) == null) {
                return;
            }
            String deviceName = localBTMusicManager.getConnectDevice().getDeviceName();
            Log.d(TAG, "updateA2dpConnectStatus: " + deviceName);
            this.mediaBtName.setText(deviceName);
            this.a2dpsinkStateInfo.setText(SkinUtils.getString(C0414R.string.str_a2dp_device_connected));
            return;
        }
        this.a2dpsinkStateInfo.setText(SkinUtils.getString(C0414R.string.a2dpsink_status_notconnected_info));
        this.connectState.setBackground(SkinUtils.getDrawable(C0414R.drawable.bt_unconnect));
        TextView textView = this.mediaBtName;
        if (textView != null) {
            textView.setText(SkinUtils.getString(C0414R.string.a2dpsink_status_notconnected_info));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAvrcpConnectStatus(int i) {
        if (i == 2) {
            this.avrcpctStateInfo.setText(SkinUtils.getString(C0414R.string.avrcpct_status_connected_info));
        } else {
            this.avrcpctStateInfo.setText(SkinUtils.getString(C0414R.string.avrcpct_status_notconnected_info));
        }
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate");
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(67108864);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        }
        super.onCreate(bundle);
        setContentView(C0414R.layout.bt_music);
        this.mAudioManager = (AudioManager) getSystemService("audio");
        this.mMainHandler = new MainHandler(Looper.getMainLooper());
        LocalBTMusicManager localBTMusicManagerInit = LocalBTMusicManager.getInstance().init(this);
        this.mBluetoothMusicManager = localBTMusicManagerInit;
        localBTMusicManagerInit.addConnectListener(this.mConnectionListener);
        this.mBluetoothMusicManager.regMusicClientBinder(MyApplication.getInstance().getBinder());
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(VOICE_EVENT_EXIT);
        registerReceiver(this.mReceiver, intentFilter);
        if (MyApplication.isHeadrest() && MyApplication.getInstance().isA2dpSourceRole()) {
            Utils.showToast(getApplicationContext(), SkinUtils.getString(C0414R.string.headrest_a2dp_source_warning));
            finish();
        }
    }

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(SkinUtils.getId(C0414R.id.ll_main));
        Drawable appWallpaper = ThemeUtils.getInstance().getAppWallpaper();
        if (viewGroup != null && appWallpaper != null) {
            viewGroup.setBackground(appWallpaper);
        }
        this.a2dpsinkStateInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_A2DP_status));
        this.avrcpctStateInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_AVRCP_status));
        this.connectState = (ImageView) findViewById(SkinUtils.getId(C0414R.id.bt_connect_state));
        this.playingAnimation = (ImageView) findViewById(SkinUtils.getId(C0414R.id.ivPlayingAnim));
        this.mediaBtName = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_bt_name));
        ImageButton imageButton = this.btnVolDown;
        if (imageButton != null) {
            imageButton.setOnClickListener(this);
        }
        ImageButton imageButton2 = this.btnVolUp;
        if (imageButton2 != null) {
            imageButton2.setOnClickListener(this);
        }
        ImageButton imageButton3 = this.btnStartBt;
        if (imageButton3 != null) {
            imageButton3.setOnClickListener(this);
        }
        this.btnPause = (Button) findViewById(SkinUtils.getId(C0414R.id.btn_music_pause));
        this.btnPlay = (Button) findViewById(SkinUtils.getId(C0414R.id.btn_music_play));
        this.btn_music_eq = (Button) findViewById(SkinUtils.getId(C0414R.id.btn_music_eq));
        Button button = this.btnPause;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = this.btnPlay;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Button button3 = this.btn_music_eq;
        if (button3 != null) {
            button3.setOnClickListener(this);
        }
        findViewById(SkinUtils.getId(C0414R.id.btn_music_prev)).setOnClickListener(this);
        findViewById(SkinUtils.getId(C0414R.id.btn_music_next)).setOnClickListener(this);
        this.mediaTitleInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_music_title));
        this.mediaArtistInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_music_icon_artist));
        this.mediaAlbumInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_music_icon_album));
        this.mediaPlayingPositionInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_music_play_time));
        this.mediaLengthInfo = (TextView) findViewById(SkinUtils.getId(C0414R.id.tv_music_total_time));
        ProgressBar progressBar = (ProgressBar) findViewById(SkinUtils.getId(C0414R.id.tv_music_playing_progress));
        this.mMusicPlayingProgressBar = progressBar;
        if (!Objects.isNull(progressBar)) {
            this.mMusicPlayingProgressBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.autochips.bluetooth.BtMusicActivity.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
        updateMusicPlayingProgress();
        updateMetadata(this.musicTitle, this.musicArtist, this.musicAlbum);
        updatePlayPauseButton(2);
        initAnimation();
    }

    private void initAnimation() {
        ImageView imageView = this.playingAnimation;
        if (imageView != null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, "rotation", 0.0f, 360.0f);
            this.mRotateAnim = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(5000L);
            this.mRotateAnim.setInterpolator(new LinearInterpolator());
            this.mRotateAnim.setRepeatCount(-1);
            this.mRotateAnim.setRepeatMode(1);
            this.mRotateAnim.start();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        if (this.mBluetoothMusicManager.isReady()) {
            this.mLastMusicPlayState = null;
            this.mBluetoothMusicManager.registerBTMusicCallback(this.mBTMusicCallback);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        this.mBluetoothMusicManager.requestA2dp();
        boolean zIsAvrcpConnected = this.mBluetoothMusicManager.isAvrcpConnected();
        boolean zIsA2dpConnected = this.mBluetoothMusicManager.isA2dpConnected();
        if (zIsA2dpConnected) {
            this.mA2dpsinkstate = 2;
        } else {
            this.mA2dpsinkstate = 0;
            if (MyApplication.isHeadrest()) {
                Utils.showToast(getApplicationContext(), SkinUtils.getString(C0414R.string.str_connect_a2dp_avrcp));
            }
        }
        if (zIsAvrcpConnected) {
            this.mAvrcpctstate = 2;
        } else {
            this.mAvrcpctstate = 0;
        }
        updateA2dpConnectStatus(this.mA2dpsinkstate);
        updateAvrcpConnectStatus(this.mAvrcpctstate);
        Log.i(TAG, "isAvrcpConnected=" + zIsAvrcpConnected + " isA2dpConnected=" + zIsA2dpConnected);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "unRegisterBTMusicCallback: " + this.mBTMusicCallback.hashCode());
        this.mBluetoothMusicManager.unRegisterBTMusicCallback(this.mBTMusicCallback);
        this.mMainHandler.removeMessages(4);
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        ConnectionListener connectionListener = this.mConnectionListener;
        if (connectionListener != null) {
            this.mBluetoothMusicManager.removeConnectListener(connectionListener);
        }
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        BroadcastReceiver broadcastReceiver = this.mReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (SkinUtils.getViewId(view)) {
            case C0414R.id.btn_music_next /* 2131230851 */:
                this.mBluetoothMusicManager.send_Avrcp_Cmd(4);
                break;
            case C0414R.id.btn_music_pause /* 2131230852 */:
                this.mBluetoothMusicManager.send_Avrcp_Cmd(1);
                break;
            case C0414R.id.btn_music_play /* 2131230853 */:
                this.mBluetoothMusicManager.send_Avrcp_Cmd(0);
                break;
            case C0414R.id.btn_music_prev /* 2131230854 */:
                this.mBluetoothMusicManager.send_Avrcp_Cmd(5);
                break;
        }
    }

    private void onVolUp() {
        this.mAudioManager.adjustStreamVolume(3, 1, 1);
    }

    private void onVolDown() {
        this.mAudioManager.adjustStreamVolume(3, -1, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exit() {
        if (Utils.getSystemProperty("persist.sys.media_exit", "1").equals("1")) {
            try {
                this.mBluetoothMusicManager.unRegMusicClientBinder();
            } catch (RemoteException unused) {
            }
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        exit();
    }
}
