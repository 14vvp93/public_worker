package com.hcn.auto.systemui.netspeed;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;

/* JADX INFO: loaded from: classes.dex */
public class NetSpeedView extends TextView implements DarkIconDispatcher.DarkReceiver {
    private static final String TAG = NetSpeedView.class.getSimpleName();
    private boolean isVisibility;
    private boolean mAttached;
    private Context mContext;
    private final ContentObserver mNetSpeedSettingsObserver;
    private Thread mThread;
    private int mUid;
    private Handler mainHandler;
    private Runnable threadRunnable;

    public NetSpeedView(Context context) {
        this(context, null);
    }

    public NetSpeedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetSpeedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mainHandler = new Handler(new Handler.Callback() { // from class: com.hcn.auto.systemui.netspeed.-$$Lambda$NetSpeedView$dX8WopmoYcPlockFNrMYuvEo4Is
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return NetSpeedView.lambda$new$0(this.f$0, message);
            }
        });
        this.mNetSpeedSettingsObserver = new ContentObserver(this.mainHandler) { // from class: com.hcn.auto.systemui.netspeed.NetSpeedView.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                NetSpeedView.this.setViewVisibility();
                Log.e(NetSpeedView.TAG, "net speed changed to:" + NetSpeedView.this.isVisibility);
            }
        };
        this.threadRunnable = new Runnable() { // from class: com.hcn.auto.systemui.netspeed.-$$Lambda$NetSpeedView$c5Syh5kx3_OmDUY-zHy8mEbR99c
            @Override // java.lang.Runnable
            public final void run() {
                NetSpeedView.lambda$new$1(this.f$0);
            }
        };
        this.mContext = context;
        this.mUid = context.getApplicationInfo().uid;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setViewVisibility();
        setText(NetSpeed.getNetSpeed(this.mUid));
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("real_time_network"), true, this.mNetSpeedSettingsObserver, -1);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mAttached) {
            this.mAttached = true;
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this);
            NetSpeed.reset();
            startThread();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            this.mAttached = false;
        }
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this);
        NetSpeed.reset();
        stopThread();
        this.mainHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewVisibility() {
        this.isVisibility = NetSpeed.showNetSpeed(this.mContext);
        setVisibility(this.isVisibility ? 0 : 8);
    }

    public static /* synthetic */ boolean lambda$new$0(NetSpeedView netSpeedView, Message msg) {
        netSpeedView.setText((String) msg.obj);
        return true;
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        setTextColor(DarkIconDispatcher.getTint(area, this, tint));
    }

    public static /* synthetic */ void lambda$new$1(NetSpeedView netSpeedView) {
        while (netSpeedView.mAttached) {
            if (netSpeedView.isVisibility) {
                Message message = Message.obtain();
                message.obj = NetSpeed.getNetSpeed(netSpeedView.mUid);
                netSpeedView.mainHandler.removeCallbacksAndMessages(null);
                netSpeedView.mainHandler.sendMessageDelayed(message, 1900L);
            }
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startThread() {
        if (this.mThread == null) {
            this.mThread = new Thread(this.threadRunnable, "netSpeedThread");
            this.mThread.start();
        }
    }

    private void stopThread() {
        if (this.mThread != null) {
            this.mThread.interrupt();
            this.mThread = null;
        }
    }
}
