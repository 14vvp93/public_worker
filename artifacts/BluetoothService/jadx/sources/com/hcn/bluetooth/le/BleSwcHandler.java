package com.hcn.bluetooth.le;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class BleSwcHandler extends Handler {
    private static final long KEY_DOWN_TIMEOUT = 6000;
    private static final long KEY_REPEAT_TIMEOUT = 400;
    private static final long LONG_PRESS_THRESHOLD = 800;
    private static final String TAG = "BleSwcHandler";
    private boolean isKeyDown;
    private boolean isLongPressed;
    private boolean isRepeatLongPress;
    private final Runnable keyDownTimeoutRunnable;
    private final Runnable longPressRunnable;
    private int mKeyCode;
    private long mKeyDownTime;
    private OnSWCKeyActionListener mListener;

    public interface OnSWCKeyActionListener {
        void onKeyLongPress(int i);

        void onKeyPress(int i);

        void onKeyRelease(int i, boolean z);
    }

    /* JADX INFO: renamed from: lambda$new$0$com-hcn-bluetooth-le-BleSwcHandler, reason: not valid java name */
    /* synthetic */ void m39lambda$new$0$comhcnbluetoothleBleSwcHandler() {
        Log.e(TAG, "key down timeout：keycode=" + this.mKeyCode);
        OnSWCKeyActionListener onSWCKeyActionListener = this.mListener;
        if (onSWCKeyActionListener != null) {
            onSWCKeyActionListener.onKeyRelease(this.mKeyCode, this.isLongPressed);
        }
        resetKeyState();
    }

    public BleSwcHandler(Looper looper, OnSWCKeyActionListener onSWCKeyActionListener) {
        super(looper);
        this.isKeyDown = false;
        this.isLongPressed = false;
        this.isRepeatLongPress = false;
        this.mKeyCode = -1;
        this.mKeyDownTime = 0L;
        this.longPressRunnable = new Runnable() { // from class: com.hcn.bluetooth.le.BleSwcHandler.1
            @Override // java.lang.Runnable
            public void run() {
                Log.e(BleSwcHandler.TAG, "onKeyLongPress");
                BleSwcHandler.this.isLongPressed = true;
                if (BleSwcHandler.this.mListener != null) {
                    BleSwcHandler.this.mListener.onKeyLongPress(BleSwcHandler.this.mKeyCode);
                }
                if (BleSwcHandler.this.isRepeatLongPress) {
                    BleSwcHandler.this.postDelayed(this, BleSwcHandler.KEY_REPEAT_TIMEOUT);
                }
            }
        };
        this.keyDownTimeoutRunnable = new Runnable() { // from class: com.hcn.bluetooth.le.BleSwcHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m39lambda$new$0$comhcnbluetoothleBleSwcHandler();
            }
        };
        this.mListener = onSWCKeyActionListener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
    }

    public void onDeviceInputEvent(int i, int i2, boolean z) {
        Log.d(TAG, "onDeviceInputEvent:keycode=" + i + ", state=" + (i2 == 0 ? "DOWN" : "UP"));
        if (i2 == 0) {
            if (this.isKeyDown && this.mKeyCode == i) {
                Log.e(TAG, "ignore keycode=" + i + ", state=DOWN");
                return;
            }
            resetKeyState();
            this.isKeyDown = true;
            this.isRepeatLongPress = z;
            this.mKeyCode = i;
            this.mKeyDownTime = SystemClock.uptimeMillis();
            this.mListener.onKeyPress(i);
            postDelayed(this.longPressRunnable, LONG_PRESS_THRESHOLD);
            postDelayed(this.keyDownTimeoutRunnable, KEY_DOWN_TIMEOUT);
            return;
        }
        if (i2 == 1) {
            if (!this.isKeyDown || this.mKeyCode != i) {
                Log.e(TAG, "ignore keycode=" + i + ", state=UP");
                return;
            }
            cancelAllDelayedTasks();
            SystemClock.uptimeMillis();
            OnSWCKeyActionListener onSWCKeyActionListener = this.mListener;
            if (onSWCKeyActionListener != null) {
                onSWCKeyActionListener.onKeyRelease(i, this.isLongPressed);
            }
            resetKeyState();
        }
    }

    private void cancelAllDelayedTasks() {
        removeCallbacks(this.longPressRunnable);
        removeCallbacks(this.keyDownTimeoutRunnable);
    }

    private void resetKeyState() {
        this.isKeyDown = false;
        this.isLongPressed = false;
        this.isRepeatLongPress = false;
        this.mKeyCode = -1;
        this.mKeyDownTime = 0L;
        cancelAllDelayedTasks();
    }
}
