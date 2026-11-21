package android.carsource;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.ViewConfiguration;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class KeyButton {
    public static final int KEY_CANCELED = 6;
    public static final int KEY_LONGPRESSED2 = 3;
    public static final int KEY_LONGPRESSED = 2;
    public static final int KEY_PRESSED = 1;
    public static final int KEY_RELEASED = 5;
    public static final int KEY_REPEATING = 4;
    final int mAdc;
    final KeyButtonATTR mAttr;
    private final KeyCallback mCallback;
    private final StubHandler mHandler;
    final int mKeyCode;
    final boolean mLongpressable;
    private int mKeyState = 5;
    private AtomicBoolean mIsPressed = new AtomicBoolean(false);
    private AtomicBoolean mCanceled = new AtomicBoolean(false);

    interface KeyCallback {
        void onKeyEvent(KeyButton keyButton, int i, int i2, boolean z);
    }

    static final class KeyButtonATTR {
        int KeyCode;
        int LongPressKeyCode;
        int LongPressKeyCode2;
        int LongPressTimeout;
        int LongPressTimeout2;
        int Repeat;

        KeyButtonATTR() {
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public KeyButtonATTR m0clone() {
            KeyButtonATTR attr = new KeyButtonATTR();
            attr.Repeat = this.Repeat;
            attr.LongPressTimeout = this.LongPressTimeout;
            attr.LongPressTimeout2 = this.LongPressTimeout2;
            attr.KeyCode = this.KeyCode;
            attr.LongPressKeyCode = this.LongPressKeyCode;
            attr.LongPressKeyCode2 = this.LongPressKeyCode2;
            return attr;
        }
    }

    KeyButton(KeyButtonATTR attr, int adc, Looper looper, KeyCallback cb) {
        this.mAttr = attr.m0clone();
        if (this.mAttr.LongPressTimeout > 0 && this.mAttr.LongPressKeyCode <= 0) {
            this.mAttr.LongPressKeyCode = this.mAttr.KeyCode;
        }
        if (this.mAttr.LongPressKeyCode > 0 && this.mAttr.LongPressTimeout <= 0) {
            this.mAttr.LongPressTimeout = ViewConfiguration.getLongPressTimeout();
        }
        this.mLongpressable = this.mAttr.LongPressTimeout > 0;
        if (this.mAttr.LongPressTimeout <= 0) {
            this.mAttr.LongPressTimeout = ViewConfiguration.getLongPressTimeout();
            this.mAttr.LongPressKeyCode = this.mAttr.KeyCode;
        }
        this.mHandler = new StubHandler(looper);
        this.mCallback = cb;
        this.mAdc = adc;
        this.mKeyCode = this.mAttr.KeyCode;
    }

    class StubHandler extends Handler {
        private boolean hasLong;

        public StubHandler(Looper looper) {
            super(looper);
            this.hasLong = false;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1 || msg.what == 2) {
                KeyButton.this.mKeyState = ((Integer) msg.obj).intValue();
                removeMessages(0);
            }
            switch (KeyButton.this.mKeyState) {
                case 1:
                    this.hasLong = false;
                    KeyButton.this.mCallback.onKeyEvent(KeyButton.this, KeyButton.this.mKeyState, KeyButton.this.mAttr.KeyCode, this.hasLong);
                    if (KeyButton.this.mAttr.LongPressTimeout > 0) {
                        KeyButton.this.mKeyState = 2;
                        KeyButton.this.mHandler.sendMessageDelayed(KeyButton.this.mHandler.obtainMessage(0), KeyButton.this.mAttr.LongPressTimeout);
                        return;
                    }
                    return;
                case 2:
                    this.hasLong = true;
                    if (!KeyButton.this.mCanceled.get()) {
                        KeyButton.this.mCallback.onKeyEvent(KeyButton.this, KeyButton.this.mKeyState, KeyButton.this.mAttr.LongPressKeyCode, this.hasLong);
                    }
                    if (KeyButton.this.mAttr.Repeat > 0) {
                        KeyButton.this.mKeyState = 4;
                        KeyButton.this.mHandler.sendMessageDelayed(KeyButton.this.mHandler.obtainMessage(0), KeyButton.this.mAttr.Repeat);
                        return;
                    } else {
                        if (KeyButton.this.mAttr.LongPressTimeout2 > 0) {
                            KeyButton.this.mKeyState = 3;
                            KeyButton.this.mHandler.sendMessageDelayed(KeyButton.this.mHandler.obtainMessage(0), KeyButton.this.mAttr.LongPressTimeout2);
                            return;
                        }
                        return;
                    }
                case 3:
                    KeyButton.this.mCallback.onKeyEvent(KeyButton.this, KeyButton.this.mKeyState, KeyButton.this.mAttr.LongPressKeyCode2, this.hasLong);
                    return;
                case 4:
                    if (KeyButton.this.mIsPressed.get()) {
                        KeyButton.this.mCallback.onKeyEvent(KeyButton.this, KeyButton.this.mKeyState, KeyButton.this.mAttr.LongPressKeyCode, this.hasLong);
                        KeyButton.this.mHandler.sendMessageDelayed(KeyButton.this.mHandler.obtainMessage(0), KeyButton.this.mAttr.Repeat);
                        return;
                    }
                    return;
                case 5:
                    KeyButton.this.mIsPressed.set(false);
                    if (!KeyButton.this.mCanceled.get()) {
                        KeyButton.this.mCallback.onKeyEvent(KeyButton.this, KeyButton.this.mKeyState, KeyButton.this.mAttr.KeyCode, this.hasLong);
                    }
                    break;
                case 6:
                    break;
                default:
                    return;
            }
            KeyButton.this.mKeyState = 5;
        }
    }

    boolean isPressed() {
        return this.mIsPressed.get();
    }

    void setPressed() {
        if (!this.mIsPressed.getAndSet(true)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, 1));
            this.mCanceled.set(false);
        }
    }

    void setCancel() {
        this.mCanceled.set(true);
    }

    void setPressedTimeout(int timeout) {
        this.mHandler.removeMessages(2);
        if (!this.mIsPressed.getAndSet(true)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, 1));
            this.mCanceled.set(false);
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, 5), Math.max(2, timeout));
    }

    void Released() {
        if (this.mIsPressed.getAndSet(false)) {
            this.mHandler.removeMessages(2);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, 5), 2L);
        }
    }
}
