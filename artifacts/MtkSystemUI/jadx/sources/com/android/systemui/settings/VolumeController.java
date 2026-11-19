package com.android.systemui.settings;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.R;

/* JADX INFO: loaded from: classes.dex */
public class VolumeController implements ToggleSlider.Listener {
    private final AudioManager mAudioManager;
    private final Handler mBackgroundHandler;
    private final Context mContext;
    private final ToggleSlider mControl;
    private boolean mExternalChange;

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler;
    private final boolean mHasVolumeText;
    private final ImageView mIcon;
    private boolean mListening;
    private BroadcastReceiver mReceiver;
    private final Runnable mStartListeningRunnable;
    private final Runnable mStopListeningRunnable;
    private final Runnable mUpdateSliderRunnable;
    private final CurrentUserTracker mUserTracker;
    private final TextView mVolumeText;
    private boolean tracking;

    public VolumeController(Context context, ImageView icon, ToggleSlider control) {
        this(context, icon, control, null, false);
    }

    public VolumeController(Context context, ImageView icon, ToggleSlider control, TextView volumeText, boolean hasVolumeText) {
        this.mStartListeningRunnable = new Runnable() { // from class: com.android.systemui.settings.VolumeController.1
            @Override // java.lang.Runnable
            public void run() {
                VolumeController.this.mUserTracker.startTracking();
                VolumeController.this.mUpdateSliderRunnable.run();
                VolumeController.this.mHandler.sendEmptyMessage(3);
            }
        };
        this.mStopListeningRunnable = new Runnable() { // from class: com.android.systemui.settings.VolumeController.2
            @Override // java.lang.Runnable
            public void run() {
                VolumeController.this.mUserTracker.stopTracking();
                VolumeController.this.mHandler.sendEmptyMessage(4);
            }
        };
        this.mUpdateSliderRunnable = new Runnable() { // from class: com.android.systemui.settings.VolumeController.3
            @Override // java.lang.Runnable
            public void run() {
                int val = VolumeController.this.mAudioManager.getStreamVolume(3);
                VolumeController.this.mHandler.obtainMessage(1, val, 0).sendToTarget();
            }
        };
        this.mHandler = new Handler() { // from class: com.android.systemui.settings.VolumeController.4
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                boolean z = true;
                VolumeController.this.mExternalChange = true;
                try {
                    switch (msg.what) {
                        case 0:
                            VolumeController.this.updateIcon();
                            break;
                        case 1:
                            VolumeController.this.updateSlider(msg.arg1);
                            break;
                        case 2:
                            ToggleSlider toggleSlider = VolumeController.this.mControl;
                            if (msg.arg1 == 0) {
                                z = false;
                            }
                            toggleSlider.setChecked(z);
                            break;
                        case 3:
                            VolumeController.this.mControl.setOnChangedListener(VolumeController.this);
                            break;
                        case 4:
                            VolumeController.this.mControl.setOnChangedListener(null);
                            break;
                        default:
                            super.handleMessage(msg);
                            break;
                    }
                } finally {
                    VolumeController.this.mExternalChange = false;
                }
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.settings.VolumeController.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!"android.media.VOLUME_CHANGED_ACTION".equals(action) || VolumeController.this.tracking) {
                    if (!"android.media.STREAM_MUTE_CHANGED_ACTION".equals(action) || VolumeController.this.tracking) {
                        if (action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION") && !VolumeController.this.tracking) {
                            int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                            if (streamType == 3) {
                                VolumeController.this.mBackgroundHandler.post(VolumeController.this.mUpdateSliderRunnable);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    int streamType2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                    if (streamType2 == 3) {
                        VolumeController.this.mBackgroundHandler.post(VolumeController.this.mUpdateSliderRunnable);
                        return;
                    }
                    return;
                }
                int streamType3 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                if (streamType3 == 3) {
                    VolumeController.this.mBackgroundHandler.post(VolumeController.this.mUpdateSliderRunnable);
                }
            }
        };
        this.mContext = context;
        this.mIcon = icon;
        this.mControl = control;
        this.mVolumeText = volumeText;
        this.mHasVolumeText = hasVolumeText;
        this.mBackgroundHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
        this.mUserTracker = new CurrentUserTracker(this.mContext) { // from class: com.android.systemui.settings.VolumeController.5
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int newUserId) {
                VolumeController.this.mBackgroundHandler.post(VolumeController.this.mUpdateSliderRunnable);
            }
        };
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        if (this.mAudioManager != null) {
            this.mControl.setMax(this.mAudioManager.getStreamMaxVolume(3));
            this.mControl.setValue(this.mAudioManager.getStreamVolume(3));
            if (this.mHasVolumeText && this.mVolumeText != null) {
                updateVolumeText(this.mAudioManager.getStreamVolume(3));
            }
            this.mIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.-$$Lambda$VolumeController$Po80t8e7LXwUas_bbNfxcc5pFFQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VolumeController.lambda$new$0(this.f$0, view);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$new$0(VolumeController volumeController, View v) {
        if (volumeController.mAudioManager != null) {
            volumeController.mAudioManager.adjustStreamVolume(3, R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle, 1);
        }
    }

    @Override // com.android.systemui.settings.ToggleSlider.Listener
    public void onInit(ToggleSlider control) {
    }

    public void registerCallbacks() {
        if (this.mListening) {
            return;
        }
        this.mBackgroundHandler.post(this.mStartListeningRunnable);
        this.mListening = true;
        registerReceiver();
    }

    @Override // com.android.systemui.settings.ToggleSlider.Listener
    public void onChanged(ToggleSlider toggleSlider, boolean tracking, boolean automatic, int value, boolean stopTracking) {
        this.tracking = tracking;
        updateIcon();
        if (this.mExternalChange) {
            return;
        }
        this.mAudioManager.setStreamVolume(3, value, 0);
        this.mAudioManager.setStreamVolume(4, value, 0);
        if (this.mHasVolumeText && this.mVolumeText != null) {
            updateVolumeText(value);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIcon() {
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        boolean muted = audioManager.isStreamMute(3);
        if (this.mIcon != null) {
            this.mIcon.setImageResource(muted ? com.android.systemui.R.drawable.ic_qs_volume_off : com.android.systemui.R.drawable.ic_qs_volume_up);
        }
    }

    private void updateVolumeText(int volumeValue) {
        if (this.mVolumeText != null) {
            this.mVolumeText.setText(String.valueOf(volumeValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlider(int val) {
        if (val == this.mControl.getValue()) {
            return;
        }
        this.mControl.setValue(val);
        if (this.mHasVolumeText && this.mVolumeText != null) {
            updateVolumeText(val);
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }
}
