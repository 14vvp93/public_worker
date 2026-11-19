package com.android.systemui.charging;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;
import android.view.View;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
public class WirelessChargingAnimation {
    private static WirelessChargingView mPreviousWirelessChargingView;
    private final WirelessChargingView mCurrentWirelessChargingView;

    public interface Callback {
        void onAnimationEnded();

        void onAnimationStarting();
    }

    public WirelessChargingAnimation(Context context, Looper looper, int batteryLevel, Callback callback, boolean isDozing) {
        this.mCurrentWirelessChargingView = new WirelessChargingView(context, looper, batteryLevel, callback, isDozing);
    }

    public static WirelessChargingAnimation makeWirelessChargingAnimation(Context context, Looper looper, int batteryLevel, Callback callback, boolean isDozing) {
        return new WirelessChargingAnimation(context, looper, batteryLevel, callback, isDozing);
    }

    public void show() {
        if (this.mCurrentWirelessChargingView == null || this.mCurrentWirelessChargingView.mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }
        if (mPreviousWirelessChargingView != null) {
            mPreviousWirelessChargingView.hide(0L);
        }
        mPreviousWirelessChargingView = this.mCurrentWirelessChargingView;
        this.mCurrentWirelessChargingView.show();
        this.mCurrentWirelessChargingView.hide(1133L);
    }

    private static class WirelessChargingView {
        private Callback mCallback;
        private final Handler mHandler;
        private View mNextView;
        private View mView;
        private WindowManager mWM;
        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        private int mGravity = 17;

        public WirelessChargingView(Context context, Looper looper, int batteryLevel, Callback callback, boolean isDozing) {
            this.mCallback = callback;
            this.mNextView = new WirelessChargingLayout(context, batteryLevel, isDozing);
            WindowManager.LayoutParams params = this.mParams;
            params.height = -2;
            params.width = -1;
            params.format = -3;
            params.type = 2015;
            params.setTitle("Charging Animation");
            params.flags = 26;
            params.dimAmount = 0.3f;
            if (looper == null && (looper = Looper.myLooper()) == null) {
                throw new RuntimeException("Can't display wireless animation on a thread that has not called Looper.prepare()");
            }
            this.mHandler = new Handler(looper, null) { // from class: com.android.systemui.charging.WirelessChargingAnimation.WirelessChargingView.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            WirelessChargingView.this.handleShow();
                            break;
                        case 1:
                            WirelessChargingView.this.handleHide();
                            WirelessChargingView.this.mNextView = null;
                            break;
                    }
                }
            };
        }

        public void show() {
            this.mHandler.obtainMessage(0).sendToTarget();
        }

        public void hide(long duration) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 1), duration);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleShow() {
            if (this.mView != this.mNextView) {
                handleHide();
                this.mView = this.mNextView;
                Context context = this.mView.getContext().getApplicationContext();
                String packageName = this.mView.getContext().getOpPackageName();
                if (context == null) {
                    context = this.mView.getContext();
                }
                this.mWM = (WindowManager) context.getSystemService("window");
                this.mParams.packageName = packageName;
                this.mParams.hideTimeoutMilliseconds = 1133L;
                if (this.mView.getParent() != null) {
                    this.mWM.removeView(this.mView);
                }
                try {
                    if (this.mCallback != null) {
                        this.mCallback.onAnimationStarting();
                    }
                    this.mWM.addView(this.mView, this.mParams);
                } catch (WindowManager.BadTokenException e) {
                    Slog.d("WirelessChargingView", "Unable to add wireless charging view. " + e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleHide() {
            if (this.mView != null) {
                if (this.mView.getParent() != null) {
                    if (this.mCallback != null) {
                        this.mCallback.onAnimationEnded();
                    }
                    this.mWM.removeViewImmediate(this.mView);
                }
                this.mView = null;
            }
        }
    }
}
