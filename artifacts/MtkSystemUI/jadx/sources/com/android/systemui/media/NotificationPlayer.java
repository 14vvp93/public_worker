package com.android.systemui.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class NotificationPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    @GuardedBy("mQueueAudioFocusLock")
    private AudioManager mAudioManagerWithAudioFocus;

    @GuardedBy("mCompletionHandlingLock")
    private CreationAndCompletionThread mCompletionThread;

    @GuardedBy("mCompletionHandlingLock")
    private Looper mLooper;
    private MediaPlayer mPlayer;
    private String mTag;

    @GuardedBy("mCmdQueue")
    private CmdThread mThread;

    @GuardedBy("mCmdQueue")
    private PowerManager.WakeLock mWakeLock;
    private final LinkedList<Command> mCmdQueue = new LinkedList<>();
    private final Object mCompletionHandlingLock = new Object();
    private final Object mQueueAudioFocusLock = new Object();
    private int mNotificationRampTimeMs = 0;
    private int mState = 2;

    private static final class Command {
        AudioAttributes attributes;
        int code;
        Context context;
        boolean looping;
        long requestTime;
        Uri uri;

        private Command() {
        }

        public String toString() {
            return "{ code=" + this.code + " looping=" + this.looping + " attributes=" + this.attributes + " uri=" + this.uri + " }";
        }
    }

    private final class CreationAndCompletionThread extends Thread {
        public Command mCmd;

        public CreationAndCompletionThread(Command cmd) {
            this.mCmd = cmd;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Looper.prepare();
            NotificationPlayer.this.mLooper = Looper.myLooper();
            synchronized (this) {
                AudioManager audioManager = (AudioManager) this.mCmd.context.getSystemService("audio");
                try {
                    MediaPlayer player = new MediaPlayer();
                    if (this.mCmd.attributes == null) {
                        this.mCmd.attributes = new AudioAttributes.Builder().setUsage(5).setContentType(4).build();
                    }
                    player.setAudioAttributes(this.mCmd.attributes);
                    player.setDataSource(this.mCmd.context, this.mCmd.uri);
                    player.setLooping(this.mCmd.looping);
                    player.setOnCompletionListener(NotificationPlayer.this);
                    player.setOnErrorListener(NotificationPlayer.this);
                    player.prepare();
                    if (this.mCmd.uri != null && this.mCmd.uri.getEncodedPath() != null && this.mCmd.uri.getEncodedPath().length() > 0 && !audioManager.isMusicActiveRemotely()) {
                        synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                            if (NotificationPlayer.this.mAudioManagerWithAudioFocus == null) {
                                int focusGain = 3;
                                if (this.mCmd.looping) {
                                    focusGain = 1;
                                }
                                NotificationPlayer.this.mNotificationRampTimeMs = audioManager.getFocusRampTimeMs(focusGain, this.mCmd.attributes);
                                audioManager.requestAudioFocus(null, this.mCmd.attributes, focusGain, 0);
                                NotificationPlayer.this.mAudioManagerWithAudioFocus = audioManager;
                            }
                        }
                    }
                    try {
                        Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                    } catch (InterruptedException e) {
                        Log.e(NotificationPlayer.this.mTag, "Exception while sleeping to sync notification playback with ducking", e);
                    }
                    try {
                        player.start();
                    } catch (Exception e2) {
                        player.release();
                        player = null;
                        NotificationPlayer.this.abandonAudioFocusAfterError();
                    }
                    if (NotificationPlayer.this.mPlayer != null) {
                        NotificationPlayer.this.mPlayer.release();
                    }
                    NotificationPlayer.this.mPlayer = player;
                } catch (Exception e3) {
                    Log.w(NotificationPlayer.this.mTag, "error loading sound for " + this.mCmd.uri, e3);
                    NotificationPlayer.this.abandonAudioFocusAfterError();
                }
                notify();
            }
            Looper.loop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abandonAudioFocusAfterError() {
        synchronized (this.mQueueAudioFocusLock) {
            if (this.mAudioManagerWithAudioFocus != null) {
                this.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                this.mAudioManagerWithAudioFocus = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSound(Command cmd) {
        try {
            synchronized (this.mCompletionHandlingLock) {
                if (this.mLooper != null && this.mLooper.getThread().getState() != Thread.State.TERMINATED) {
                    this.mLooper.quit();
                }
                this.mCompletionThread = new CreationAndCompletionThread(cmd);
                synchronized (this.mCompletionThread) {
                    this.mCompletionThread.start();
                    this.mCompletionThread.wait();
                }
            }
            long delay = SystemClock.uptimeMillis() - cmd.requestTime;
            if (delay > 1000) {
                Log.w(this.mTag, "Notification sound delayed by " + delay + "msecs");
            }
        } catch (Exception e) {
            Log.w(this.mTag, "error loading sound for " + cmd.uri, e);
        }
    }

    private final class CmdThread extends Thread {
        CmdThread() {
            super("NotificationPlayer-" + NotificationPlayer.this.mTag);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Command cmd;
            while (true) {
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    cmd = (Command) NotificationPlayer.this.mCmdQueue.removeFirst();
                }
                switch (cmd.code) {
                    case 1:
                        NotificationPlayer.this.startSound(cmd);
                        break;
                    case 2:
                        if (NotificationPlayer.this.mPlayer == null) {
                            Log.w(NotificationPlayer.this.mTag, "STOP command without a player");
                        } else {
                            long delay = SystemClock.uptimeMillis() - cmd.requestTime;
                            if (delay > 1000) {
                                Log.w(NotificationPlayer.this.mTag, "Notification stop delayed by " + delay + "msecs");
                            }
                            NotificationPlayer.this.mPlayer.stop();
                            NotificationPlayer.this.mPlayer.release();
                            NotificationPlayer.this.mPlayer = null;
                            synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                                if (NotificationPlayer.this.mAudioManagerWithAudioFocus != null) {
                                    NotificationPlayer.this.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                                    NotificationPlayer.this.mAudioManagerWithAudioFocus = null;
                                }
                            }
                            synchronized (NotificationPlayer.this.mCompletionHandlingLock) {
                                if (NotificationPlayer.this.mLooper != null && NotificationPlayer.this.mLooper.getThread().getState() != Thread.State.TERMINATED) {
                                    NotificationPlayer.this.mLooper.quit();
                                }
                            }
                        }
                        break;
                }
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    if (NotificationPlayer.this.mCmdQueue.size() == 0) {
                        NotificationPlayer.this.mThread = null;
                        NotificationPlayer.this.releaseWakeLock();
                        return;
                    }
                }
            }
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        synchronized (this.mQueueAudioFocusLock) {
            if (this.mAudioManagerWithAudioFocus != null) {
                this.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                this.mAudioManagerWithAudioFocus = null;
            }
        }
        synchronized (this.mCmdQueue) {
            synchronized (this.mCompletionHandlingLock) {
                if (this.mCmdQueue.size() == 0) {
                    if (this.mLooper != null) {
                        this.mLooper.quit();
                    }
                    this.mCompletionThread = null;
                }
            }
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(this.mTag, "error " + what + " (extra=" + extra + ") playing notification");
        onCompletion(mp);
        return true;
    }

    public NotificationPlayer(String tag) {
        if (tag != null) {
            this.mTag = tag;
        } else {
            this.mTag = "NotificationPlayer";
        }
    }

    public void play(Context context, Uri uri, boolean looping, AudioAttributes attributes) {
        Command cmd = new Command();
        cmd.requestTime = SystemClock.uptimeMillis();
        cmd.code = 1;
        cmd.context = context;
        cmd.uri = uri;
        cmd.looping = looping;
        cmd.attributes = attributes;
        synchronized (this.mCmdQueue) {
            enqueueLocked(cmd);
            this.mState = 1;
        }
    }

    public void stop() {
        synchronized (this.mCmdQueue) {
            if (this.mState != 2) {
                Command cmd = new Command();
                cmd.requestTime = SystemClock.uptimeMillis();
                cmd.code = 2;
                enqueueLocked(cmd);
                this.mState = 2;
            }
        }
    }

    @GuardedBy("mCmdQueue")
    private void enqueueLocked(Command cmd) {
        this.mCmdQueue.add(cmd);
        if (this.mThread == null) {
            acquireWakeLock();
            this.mThread = new CmdThread();
            this.mThread.start();
        }
    }

    public void setUsesWakeLock(Context context) {
        synchronized (this.mCmdQueue) {
            if (this.mWakeLock != null || this.mThread != null) {
                throw new RuntimeException("assertion failed mWakeLock=" + this.mWakeLock + " mThread=" + this.mThread);
            }
            PowerManager pm = (PowerManager) context.getSystemService("power");
            this.mWakeLock = pm.newWakeLock(1, this.mTag);
        }
    }

    @GuardedBy("mCmdQueue")
    private void acquireWakeLock() {
        if (this.mWakeLock != null) {
            this.mWakeLock.acquire();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mCmdQueue")
    public void releaseWakeLock() {
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
        }
    }
}
