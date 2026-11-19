package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.carsource.McuManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider;
import com.android.systemui.shared.system.NavigationBarCompat;
import com.hcn.auto.systemui.Utils;

/* JADX INFO: loaded from: classes.dex */
public class KeyButtonView extends ImageView implements NavBarButtonProvider.ButtonInterface {
    private static final String TAG = KeyButtonView.class.getSimpleName();
    private LocalHandler localHandler;
    private AudioManager mAudioManager;
    private final Runnable mCheckLongPress;
    private int mCode;
    private int mContentDescriptionRes;
    private long mDownTime;
    private boolean mGestureAborted;
    private boolean mIsVertical;
    private boolean mLongClicked;
    private final MetricsLogger mMetricsLogger;
    private View.OnClickListener mOnClickListener;
    private final OverviewProxyService mOverviewProxyService;
    private final boolean mPlaySounds;
    private final KeyButtonRipple mRipple;
    private boolean mSupportsLongpress;
    private int mTouchDownX;
    private int mTouchDownY;

    public KeyButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mSupportsLongpress = true;
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mCheckLongPress = new Runnable() { // from class: com.android.systemui.statusbar.policy.KeyButtonView.1
            @Override // java.lang.Runnable
            public void run() {
                if (KeyButtonView.this.isPressed()) {
                    if (!KeyButtonView.this.isLongClickable()) {
                        if (KeyButtonView.this.mSupportsLongpress) {
                            KeyButtonView.this.sendEvent(0, 128);
                            KeyButtonView.this.sendAccessibilityEvent(2);
                            KeyButtonView.this.mLongClicked = true;
                            return;
                        }
                        return;
                    }
                    KeyButtonView.this.performLongClick();
                    KeyButtonView.this.mLongClicked = true;
                }
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyButtonView, defStyle, 0);
        this.mCode = a.getInteger(1, 0);
        this.mSupportsLongpress = a.getBoolean(2, true);
        this.mPlaySounds = a.getBoolean(3, true);
        TypedValue value = new TypedValue();
        if (a.getValue(0, value)) {
            this.mContentDescriptionRes = value.resourceId;
        }
        a.recycle();
        setClickable(true);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mRipple = new KeyButtonRipple(context, this);
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        setBackground(this.mRipple);
        forceHasOverlappingRendering(false);
        this.localHandler = new LocalHandler();
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.mCode != 0 || super.isClickable();
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.policy.KeyButtonView$2] */
    public void loadAsync(Icon icon) {
        new AsyncTask<Icon, Void, Drawable>() { // from class: com.android.systemui.statusbar.policy.KeyButtonView.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Drawable doInBackground(Icon... params) {
                return params[0].loadDrawable(KeyButtonView.this.mContext);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Drawable drawable) {
                KeyButtonView.this.setImageDrawable(drawable);
            }
        }.execute(icon);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mContentDescriptionRes != 0) {
            setContentDescription(this.mContext.getString(this.mContentDescriptionRes));
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (this.mCode != 0) {
            info.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, null));
            if (this.mSupportsLongpress || isLongClickable()) {
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, null));
            }
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (action == 16 && this.mCode != 0) {
            sendEvent(0, 0, SystemClock.uptimeMillis());
            sendEvent(1, 0);
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        }
        if (action == 32 && this.mCode != 0) {
            sendEvent(0, 128);
            sendEvent(1, 0);
            sendAccessibilityEvent(2);
            return true;
        }
        return super.performAccessibilityActionInternal(action, arguments);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        int quickStepTouchSlopPx;
        int quickScrubTouchSlopPx;
        boolean showSwipeUI = this.mOverviewProxyService.shouldShowSwipeUpUI();
        int action = ev.getAction();
        if (action == 0) {
            this.mGestureAborted = false;
        }
        if (this.mGestureAborted) {
            setPressed(false);
            return false;
        }
        switch (action) {
            case 0:
                Utils.setSystemProperty("sys.systemui.interacting", "1");
                this.mDownTime = SystemClock.uptimeMillis();
                this.mLongClicked = false;
                setPressed(true);
                this.mTouchDownX = (int) ev.getRawX();
                this.mTouchDownY = (int) ev.getRawY();
                if (this.mCode != 0) {
                    sendEvent(0, 0, this.mDownTime);
                } else {
                    performHapticFeedback(1);
                }
                if (!showSwipeUI) {
                    playSoundEffect(0);
                }
                removeCallbacks(this.mCheckLongPress);
                postDelayed(this.mCheckLongPress, ViewConfiguration.getLongPressTimeout());
                return true;
            case 1:
                Utils.setSystemProperty("sys.systemui.interacting", "0");
                boolean doIt = isPressed() && !this.mLongClicked;
                setPressed(false);
                boolean doHapticFeedback = SystemClock.uptimeMillis() - this.mDownTime > 150;
                if (showSwipeUI) {
                    if (doIt) {
                        performHapticFeedback(1);
                        playSoundEffect(0);
                    }
                } else if (doHapticFeedback && !this.mLongClicked) {
                    performHapticFeedback(8);
                }
                if (this.mCode != 0) {
                    if (doIt) {
                        if (this.mCode == 309) {
                            McuManager.getsInstance().injectKeyEventTimeout(309, 50);
                        } else {
                            sendEvent(1, 0);
                        }
                        sendAccessibilityEvent(1);
                    } else {
                        sendEvent(1, 32);
                    }
                } else if (doIt && this.mOnClickListener != null) {
                    this.mOnClickListener.onClick(this);
                    sendAccessibilityEvent(1);
                }
                removeCallbacks(this.mCheckLongPress);
                return true;
            case 2:
                int x = (int) ev.getRawX();
                int y = (int) ev.getRawY();
                int iAbs = Math.abs(x - this.mTouchDownX);
                if (this.mIsVertical) {
                    quickStepTouchSlopPx = NavigationBarCompat.getQuickScrubTouchSlopPx();
                } else {
                    quickStepTouchSlopPx = NavigationBarCompat.getQuickStepTouchSlopPx();
                }
                boolean exceededTouchSlopX = iAbs > quickStepTouchSlopPx;
                int iAbs2 = Math.abs(y - this.mTouchDownY);
                if (this.mIsVertical) {
                    quickScrubTouchSlopPx = NavigationBarCompat.getQuickStepTouchSlopPx();
                } else {
                    quickScrubTouchSlopPx = NavigationBarCompat.getQuickScrubTouchSlopPx();
                }
                boolean exceededTouchSlopY = iAbs2 > quickScrubTouchSlopPx;
                if (exceededTouchSlopX || exceededTouchSlopY) {
                    setPressed(false);
                    removeCallbacks(this.mCheckLongPress);
                }
                return true;
            case 3:
                Utils.setSystemProperty("sys.systemui.interacting", "0");
                setPressed(false);
                if (this.mCode != 0) {
                    sendEvent(1, 32);
                }
                removeCallbacks(this.mCheckLongPress);
                return true;
            default:
                return true;
        }
    }

    @Override // android.view.View
    public void playSoundEffect(int soundConstant) {
        if (this.mPlaySounds) {
            this.mAudioManager.playSoundEffect(soundConstant, ActivityManager.getCurrentUser());
        }
    }

    public void sendEvent(int action, int flags) {
        sendEvent(action, flags, SystemClock.uptimeMillis());
    }

    void sendEvent(int action, int flags, long when) {
        if (this.mCode == 309) {
            return;
        }
        this.mMetricsLogger.write(new LogMaker(931).setType(4).setSubtype(this.mCode).addTaggedData(933, Integer.valueOf(action)).addTaggedData(932, Integer.valueOf(flags)));
        int repeatCount = (flags & 128) != 0 ? 1 : 0;
        KeyEvent ev = new KeyEvent(this.mDownTime, when, action, this.mCode, repeatCount, 0, -1, 0, flags | 8 | 64, 257);
        InputManager.getInstance().injectInputEvent(ev, 0);
        if (this.localHandler == null || !isPressed()) {
            return;
        }
        if (this.mCode == 24 || this.mCode == 25) {
            this.localHandler.removeMessages(0);
            this.localHandler.sendEmptyMessageDelayed(0, 150L);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider.ButtonInterface
    public void abortCurrentGesture() {
        setPressed(false);
        this.mRipple.abortDelayedRipple();
        this.mGestureAborted = true;
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider.ButtonInterface
    public void setDarkIntensity(float darkIntensity) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) getDrawable()).setDarkIntensity(darkIntensity);
            invalidate();
        }
        this.mRipple.setDarkIntensity(darkIntensity);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider.ButtonInterface
    public void setDelayTouchFeedback(boolean shouldDelay) {
        this.mRipple.setDelayTouchFeedback(shouldDelay);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider.ButtonInterface
    public void setVertical(boolean vertical) {
        this.mIsVertical = vertical;
    }

    class LocalHandler extends Handler {
        LocalHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0 && KeyButtonView.this.isPressed()) {
                long downTime = SystemClock.uptimeMillis();
                KeyButtonView.this.sendEvent(0, 0, downTime);
                Log.d("SYS_KB", "LongPress mCode=" + KeyButtonView.this.mCode);
            }
        }
    }
}
