package com.android.keyguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.mediatek.keyguard.AntiTheft.AntiTheftManager;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardMessageArea extends TextView implements SecurityMessageDisplay {
    private static final Object ANNOUNCE_TOKEN = new Object();
    private final int mDefaultColor;
    private final Handler mHandler;
    private KeyguardUpdateMonitorCallback mInfoCallback;
    private CharSequence mMessage;
    private int mNextMessageColor;
    private KeyguardSecurityModel mSecurityModel;
    private CharSequence mSeparator;

    public KeyguardMessageArea(Context context) {
        this(context, null);
    }

    public KeyguardMessageArea(Context context, AttributeSet attrs) {
        this(context, attrs, KeyguardUpdateMonitor.getInstance(context));
    }

    public KeyguardMessageArea(Context context, AttributeSet attrs, KeyguardUpdateMonitor monitor) {
        super(context, attrs);
        this.mNextMessageColor = -1;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardMessageArea.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onFinishedGoingToSleep(int why) {
                KeyguardMessageArea.this.setSelected(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onStartedWakingUp() {
                KeyguardMessageArea.this.setSelected(true);
            }
        };
        setLayerType(2, null);
        this.mSecurityModel = new KeyguardSecurityModel(context);
        this.mSeparator = getResources().getString(android.R.string.config_wearMediaSessionsPackage);
        monitor.registerCallback(this.mInfoCallback);
        this.mHandler = new Handler(Looper.myLooper());
        this.mDefaultColor = getCurrentTextColor();
        update();
    }

    @Override // com.android.keyguard.SecurityMessageDisplay
    public void setNextMessageColor(int color) {
        this.mNextMessageColor = color;
    }

    @Override // com.android.keyguard.SecurityMessageDisplay
    public void setMessage(CharSequence msg) {
        if (!TextUtils.isEmpty(msg)) {
            securityMessageChanged(msg);
        } else {
            clearMessage();
        }
    }

    @Override // com.android.keyguard.SecurityMessageDisplay
    public void setMessage(int resId) {
        CharSequence message = null;
        if (resId != 0) {
            message = getContext().getResources().getText(resId);
        }
        setMessage(message);
    }

    public static SecurityMessageDisplay findSecurityMessageDisplay(View v) {
        KeyguardMessageArea messageArea = (KeyguardMessageArea) v.findViewById(com.android.systemui.R.id.keyguard_message_area);
        if (messageArea == null) {
            throw new RuntimeException("Can't find keyguard_message_area in " + v.getClass());
        }
        return messageArea;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        boolean shouldMarquee = KeyguardUpdateMonitor.getInstance(this.mContext).isDeviceInteractive();
        setSelected(shouldMarquee);
    }

    private void securityMessageChanged(CharSequence message) {
        this.mMessage = message;
        update();
        this.mHandler.removeCallbacksAndMessages(ANNOUNCE_TOKEN);
        this.mHandler.postAtTime(new AnnounceRunnable(this, getText()), ANNOUNCE_TOKEN, SystemClock.uptimeMillis() + 250);
    }

    private void clearMessage() {
        this.mMessage = null;
        update();
    }

    private void update() {
        CharSequence status = this.mMessage;
        setVisibility(TextUtils.isEmpty(status) ? 4 : 0);
        if (this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()) == KeyguardSecurityModel.SecurityMode.AntiTheft) {
            setText(AntiTheftManager.getAntiTheftMessageAreaText(status, this.mSeparator));
        } else {
            setText(status);
        }
        int color = this.mDefaultColor;
        if (this.mNextMessageColor != -1) {
            color = this.mNextMessageColor;
            this.mNextMessageColor = -1;
        }
        setTextColor(color);
    }

    private static class AnnounceRunnable implements Runnable {
        private final WeakReference<View> mHost;
        private final CharSequence mTextToAnnounce;

        AnnounceRunnable(View host, CharSequence textToAnnounce) {
            this.mHost = new WeakReference<>(host);
            this.mTextToAnnounce = textToAnnounce;
        }

        @Override // java.lang.Runnable
        public void run() {
            View host = this.mHost.get();
            if (host != null) {
                host.announceForAccessibility(this.mTextToAnnounce);
            }
        }
    }
}
