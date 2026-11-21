package android.carsource;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class Screenshot {
    private static final long SCREENSHOT_CHORD_DEBOUNCE_DELAY_MILLIS = 150;
    private Context mContext;
    private final Handler mHandler;
    long mPowerKeyTime;
    boolean mPowerKeyTriggered;
    private boolean mScreenshotChordEnabled;
    private final Runnable mScreenshotChordLongPress;
    ServiceConnection mScreenshotConnection;
    final Object mScreenshotLock;
    final Runnable mScreenshotTimeout;
    boolean mVolumeDownKeyConsumedByScreenshotChord;
    long mVolumeDownKeyTime;
    boolean mVolumeDownKeyTriggered;
    boolean mVolumeUpKeyTriggered;

    Screenshot(Context context) {
        this.mScreenshotChordEnabled = SystemProperties.getInt("sys.screenshot.enable", 1) == 1;
        this.mVolumeUpKeyTriggered = false;
        this.mVolumeDownKeyConsumedByScreenshotChord = false;
        this.mScreenshotLock = new Object();
        this.mScreenshotConnection = null;
        this.mScreenshotTimeout = new Runnable() { // from class: android.carsource.Screenshot.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (Screenshot.this.mScreenshotLock) {
                    if (Screenshot.this.mScreenshotConnection != null) {
                        Screenshot.this.mContext.unbindService(Screenshot.this.mScreenshotConnection);
                        Screenshot.this.mScreenshotConnection = null;
                    }
                }
            }
        };
        this.mScreenshotChordLongPress = new Runnable() { // from class: android.carsource.Screenshot.3
            @Override // java.lang.Runnable
            public void run() {
                Screenshot.this.takeScreenshot();
            }
        };
        this.mContext = context;
        this.mHandler = new Handler();
    }

    void interceptScreenshotChord() {
        if (this.mScreenshotChordEnabled && this.mVolumeDownKeyTriggered && this.mPowerKeyTriggered && !this.mVolumeUpKeyTriggered && !this.mVolumeDownKeyConsumedByScreenshotChord) {
            long now = SystemClock.uptimeMillis();
            if (now <= this.mVolumeDownKeyTime + SCREENSHOT_CHORD_DEBOUNCE_DELAY_MILLIS) {
                this.mVolumeDownKeyConsumedByScreenshotChord = true;
                this.mHandler.postDelayed(this.mScreenshotChordLongPress, ViewConfiguration.getGlobalActionKeyTimeout());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeScreenshot() {
        synchronized (this.mScreenshotLock) {
            if (this.mScreenshotConnection != null) {
                return;
            }
            ComponentName cn = new ComponentName("com.android.systemui", "com.android.systemui.screenshot.TakeScreenshotService");
            Intent intent = new Intent();
            intent.setComponent(cn);
            ServiceConnection conn = new ServiceConnection() { // from class: android.carsource.Screenshot.2
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName name, IBinder service) {
                    synchronized (Screenshot.this.mScreenshotLock) {
                        if (Screenshot.this.mScreenshotConnection != this) {
                            return;
                        }
                        Messenger messenger = new Messenger(service);
                        Message msg = Message.obtain((Handler) null, 1);
                        Handler h = new Handler(Screenshot.this.mHandler.getLooper()) { // from class: android.carsource.Screenshot.2.1
                            @Override // android.os.Handler
                            public void handleMessage(Message msg2) {
                                synchronized (Screenshot.this.mScreenshotLock) {
                                    if (Screenshot.this.mScreenshotConnection == this) {
                                        Screenshot.this.mContext.unbindService(Screenshot.this.mScreenshotConnection);
                                        Screenshot.this.mScreenshotConnection = null;
                                        Screenshot.this.mHandler.removeCallbacks(Screenshot.this.mScreenshotTimeout);
                                    }
                                }
                            }
                        };
                        msg.replyTo = new Messenger(h);
                        msg.arg2 = 0;
                        msg.arg1 = 0;
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName name) {
                }
            };
            if (this.mContext.bindService(intent, conn, 1)) {
                this.mScreenshotConnection = conn;
                this.mHandler.postDelayed(this.mScreenshotTimeout, 10000L);
            }
        }
    }
}
