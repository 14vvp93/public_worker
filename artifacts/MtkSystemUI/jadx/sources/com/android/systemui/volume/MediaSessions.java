package com.android.systemui.volume;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.IRemoteVolumeController;
import android.media.MediaMetadata;
import android.media.session.ISessionController;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class MediaSessions {
    private static final String TAG = Util.logTag(MediaSessions.class);
    private final Callbacks mCallbacks;
    private final Context mContext;
    private final H mHandler;
    private boolean mInit;
    private final MediaSessionManager mMgr;
    private final Map<MediaSession.Token, MediaControllerRecord> mRecords = new HashMap();
    private final MediaSessionManager.OnActiveSessionsChangedListener mSessionsListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.systemui.volume.MediaSessions.1
        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(List<MediaController> controllers) {
            MediaSessions.this.onActiveSessionsUpdatedH(controllers);
        }
    };
    private final IRemoteVolumeController mRvc = new IRemoteVolumeController.Stub() { // from class: com.android.systemui.volume.MediaSessions.2
        public void remoteVolumeChanged(ISessionController session, int flags) throws RemoteException {
            MediaSessions.this.mHandler.obtainMessage(2, flags, 0, session).sendToTarget();
        }

        public void updateRemoteController(ISessionController session) throws RemoteException {
            MediaSessions.this.mHandler.obtainMessage(3, session).sendToTarget();
        }
    };

    public interface Callbacks {
        void onRemoteRemoved(MediaSession.Token token);

        void onRemoteUpdate(MediaSession.Token token, String str, MediaController.PlaybackInfo playbackInfo);

        void onRemoteVolumeChanged(MediaSession.Token token, int i);
    }

    public MediaSessions(Context context, Looper looper, Callbacks callbacks) {
        this.mContext = context;
        this.mHandler = new H(looper);
        this.mMgr = (MediaSessionManager) context.getSystemService("media_session");
        this.mCallbacks = callbacks;
    }

    public void dump(PrintWriter writer) {
        writer.println(getClass().getSimpleName() + " state:");
        writer.print("  mInit: ");
        writer.println(this.mInit);
        writer.print("  mRecords.size: ");
        writer.println(this.mRecords.size());
        int i = 0;
        for (MediaControllerRecord r : this.mRecords.values()) {
            i++;
            dump(i, writer, r.controller);
        }
    }

    public void init() {
        if (D.BUG) {
            Log.d(TAG, "init");
        }
        this.mMgr.addOnActiveSessionsChangedListener(this.mSessionsListener, null, this.mHandler);
        this.mInit = true;
        postUpdateSessions();
        this.mMgr.setRemoteVolumeController(this.mRvc);
    }

    protected void postUpdateSessions() {
        if (this.mInit) {
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public void setVolume(MediaSession.Token token, int level) {
        MediaControllerRecord r = this.mRecords.get(token);
        if (r == null) {
            Log.w(TAG, "setVolume: No record found for token " + token);
            return;
        }
        if (D.BUG) {
            Log.d(TAG, "Setting level to " + level);
        }
        r.controller.setVolumeTo(level, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemoteVolumeChangedH(ISessionController session, int flags) {
        MediaController controller = new MediaController(this.mContext, session);
        if (D.BUG) {
            Log.d(TAG, "remoteVolumeChangedH " + controller.getPackageName() + " " + Util.audioManagerFlagsToString(flags));
        }
        MediaSession.Token token = controller.getSessionToken();
        this.mCallbacks.onRemoteVolumeChanged(token, flags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateRemoteControllerH(ISessionController session) {
        MediaController controller = session != null ? new MediaController(this.mContext, session) : null;
        String pkg = controller != null ? controller.getPackageName() : null;
        if (D.BUG) {
            Log.d(TAG, "updateRemoteControllerH " + pkg);
        }
        postUpdateSessions();
    }

    protected void onActiveSessionsUpdatedH(List<MediaController> controllers) {
        if (D.BUG) {
            Log.d(TAG, "onActiveSessionsUpdatedH n=" + controllers.size());
        }
        Set<MediaSession.Token> toRemove = new HashSet<>(this.mRecords.keySet());
        for (MediaController controller : controllers) {
            MediaSession.Token token = controller.getSessionToken();
            MediaController.PlaybackInfo pi = controller.getPlaybackInfo();
            toRemove.remove(token);
            if (!this.mRecords.containsKey(token)) {
                MediaControllerRecord r = new MediaControllerRecord(controller);
                r.name = getControllerName(controller);
                this.mRecords.put(token, r);
                controller.registerCallback(r, this.mHandler);
            }
            MediaControllerRecord r2 = this.mRecords.get(token);
            boolean remote = isRemote(pi);
            if (remote) {
                updateRemoteH(token, r2.name, pi);
                r2.sentRemote = true;
            }
        }
        for (MediaSession.Token t : toRemove) {
            MediaControllerRecord r3 = this.mRecords.get(t);
            r3.controller.unregisterCallback(r3);
            this.mRecords.remove(t);
            if (D.BUG) {
                Log.d(TAG, "Removing " + r3.name + " sentRemote=" + r3.sentRemote);
            }
            if (r3.sentRemote) {
                this.mCallbacks.onRemoteRemoved(t);
                r3.sentRemote = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRemote(MediaController.PlaybackInfo pi) {
        return pi != null && pi.getPlaybackType() == 2;
    }

    protected String getControllerName(MediaController controller) {
        PackageManager pm = this.mContext.getPackageManager();
        String pkg = controller.getPackageName();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(pkg, 0);
            String appLabel = Objects.toString(ai.loadLabel(pm), "").trim();
            if (appLabel.length() > 0) {
                return appLabel;
            }
            return pkg;
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoteH(MediaSession.Token token, String name, MediaController.PlaybackInfo pi) {
        if (this.mCallbacks != null) {
            this.mCallbacks.onRemoteUpdate(token, name, pi);
        }
    }

    private static void dump(int n, PrintWriter writer, MediaController c) {
        writer.println("  Controller " + n + ": " + c.getPackageName());
        Bundle extras = c.getExtras();
        long flags = c.getFlags();
        MediaMetadata mm = c.getMetadata();
        MediaController.PlaybackInfo pi = c.getPlaybackInfo();
        PlaybackState playbackState = c.getPlaybackState();
        List<MediaSession.QueueItem> queue = c.getQueue();
        CharSequence queueTitle = c.getQueueTitle();
        int ratingType = c.getRatingType();
        PendingIntent sessionActivity = c.getSessionActivity();
        writer.println("    PlaybackState: " + Util.playbackStateToString(playbackState));
        writer.println("    PlaybackInfo: " + Util.playbackInfoToString(pi));
        if (mm != null) {
            writer.println("  MediaMetadata.desc=" + mm.getDescription());
        }
        writer.println("    RatingType: " + ratingType);
        writer.println("    Flags: " + flags);
        if (extras != null) {
            writer.println("    Extras:");
            for (String key : extras.keySet()) {
                writer.println("      " + key + "=" + extras.get(key));
            }
        }
        if (queueTitle != null) {
            writer.println("    QueueTitle: " + ((Object) queueTitle));
        }
        if (queue != null && !queue.isEmpty()) {
            writer.println("    Queue:");
            for (MediaSession.QueueItem qi : queue) {
                writer.println("      " + qi);
            }
        }
        if (pi != null) {
            writer.println("    sessionActivity: " + sessionActivity);
        }
    }

    private final class MediaControllerRecord extends MediaController.Callback {
        private final MediaController controller;
        private String name;
        private boolean sentRemote;

        private MediaControllerRecord(MediaController controller) {
            this.controller = controller;
        }

        private String cb(String method) {
            return method + " " + this.controller.getPackageName() + " ";
        }

        @Override // android.media.session.MediaController.Callback
        public void onAudioInfoChanged(MediaController.PlaybackInfo info) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onAudioInfoChanged") + Util.playbackInfoToString(info) + " sentRemote=" + this.sentRemote);
            }
            boolean remote = MediaSessions.isRemote(info);
            if (!remote && this.sentRemote) {
                MediaSessions.this.mCallbacks.onRemoteRemoved(this.controller.getSessionToken());
                this.sentRemote = false;
            } else if (remote) {
                MediaSessions.this.updateRemoteH(this.controller.getSessionToken(), this.name, info);
                this.sentRemote = true;
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onExtrasChanged(Bundle extras) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onExtrasChanged") + extras);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata metadata) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onMetadataChanged") + Util.mediaMetadataToString(metadata));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState state) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onPlaybackStateChanged") + Util.playbackStateToString(state));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onQueueChanged(List<MediaSession.QueueItem> queue) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onQueueChanged") + queue);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onQueueTitleChanged(CharSequence title) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onQueueTitleChanged") + ((Object) title));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onSessionDestroyed"));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionEvent(String event, Bundle extras) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onSessionEvent") + "event=" + event + " extras=" + extras);
            }
        }
    }

    private final class H extends Handler {
        private H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MediaSessions.this.onActiveSessionsUpdatedH(MediaSessions.this.mMgr.getActiveSessions(null));
                    break;
                case 2:
                    MediaSessions.this.onRemoteVolumeChangedH((ISessionController) msg.obj, msg.arg1);
                    break;
                case 3:
                    MediaSessions.this.onUpdateRemoteControllerH((ISessionController) msg.obj);
                    break;
            }
        }
    }
}
