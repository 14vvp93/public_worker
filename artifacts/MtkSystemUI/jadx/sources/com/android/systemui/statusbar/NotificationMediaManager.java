package com.android.systemui.statusbar;

import android.R;
import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import com.android.systemui.Dumpable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationMediaManager implements Dumpable {
    private final Context mContext;
    protected NotificationEntryManager mEntryManager;
    private MediaController mMediaController;
    private final MediaController.Callback mMediaListener = new MediaController.Callback() { // from class: com.android.systemui.statusbar.NotificationMediaManager.1
        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState state) {
            super.onPlaybackStateChanged(state);
            if (state != null && !NotificationMediaManager.this.isPlaybackActive(state.getState())) {
                NotificationMediaManager.this.clearCurrentMediaNotification();
                NotificationMediaManager.this.mPresenter.updateMediaMetaData(true, true);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata metadata) {
            super.onMetadataChanged(metadata);
            NotificationMediaManager.this.mMediaMetadata = metadata;
            NotificationMediaManager.this.mPresenter.updateMediaMetaData(true, true);
        }
    };
    private MediaMetadata mMediaMetadata;
    private String mMediaNotificationKey;
    private final MediaSessionManager mMediaSessionManager;
    protected NotificationPresenter mPresenter;

    public NotificationMediaManager(Context context) {
        this.mContext = context;
        this.mMediaSessionManager = (MediaSessionManager) this.mContext.getSystemService("media_session");
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
    }

    public void onNotificationRemoved(String key) {
        if (key.equals(this.mMediaNotificationKey)) {
            clearCurrentMediaNotification();
            this.mPresenter.updateMediaMetaData(true, true);
        }
    }

    public String getMediaNotificationKey() {
        return this.mMediaNotificationKey;
    }

    public MediaMetadata getMediaMetadata() {
        return this.mMediaMetadata;
    }

    public void findAndUpdateMediaNotifications() {
        MediaSession.Token token;
        boolean metaDataChanged = false;
        synchronized (this.mEntryManager.getNotificationData()) {
            ArrayList<NotificationData.Entry> activeNotifications = this.mEntryManager.getNotificationData().getActiveNotifications();
            int N = activeNotifications.size();
            NotificationData.Entry mediaNotification = null;
            MediaController controller = null;
            for (int i = 0; i < N; i++) {
                NotificationData.Entry entry = activeNotifications.get(i);
                if (isMediaNotification(entry) && (token = (MediaSession.Token) entry.notification.getNotification().extras.getParcelable("android.mediaSession")) != null) {
                    MediaController aController = new MediaController(this.mContext, token);
                    if (3 == getMediaControllerPlaybackState(aController)) {
                        mediaNotification = entry;
                        controller = aController;
                        break;
                    }
                }
            }
            if (mediaNotification == null && this.mMediaSessionManager != null) {
                List<MediaController> sessions = this.mMediaSessionManager.getActiveSessionsForUser(null, -1);
                for (MediaController aController2 : sessions) {
                    if (3 == getMediaControllerPlaybackState(aController2)) {
                        String pkg = aController2.getPackageName();
                        for (int i2 = 0; i2 < N; i2++) {
                            NotificationData.Entry entry2 = activeNotifications.get(i2);
                            if (entry2.notification.getPackageName().equals(pkg)) {
                                controller = aController2;
                                mediaNotification = entry2;
                                break;
                            }
                        }
                    }
                }
            }
            if (controller != null && !sameSessions(this.mMediaController, controller)) {
                clearCurrentMediaNotificationSession();
                this.mMediaController = controller;
                this.mMediaController.registerCallback(this.mMediaListener);
                this.mMediaMetadata = this.mMediaController.getMetadata();
                metaDataChanged = true;
            }
            if (mediaNotification != null && !mediaNotification.notification.getKey().equals(this.mMediaNotificationKey)) {
                this.mMediaNotificationKey = mediaNotification.notification.getKey();
            }
        }
        if (metaDataChanged) {
            this.mEntryManager.updateNotifications();
        }
        this.mPresenter.updateMediaMetaData(metaDataChanged, true);
    }

    public void clearCurrentMediaNotification() {
        this.mMediaNotificationKey = null;
        clearCurrentMediaNotificationSession();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("    mMediaSessionManager=");
        pw.println(this.mMediaSessionManager);
        pw.print("    mMediaNotificationKey=");
        pw.println(this.mMediaNotificationKey);
        pw.print("    mMediaController=");
        pw.print(this.mMediaController);
        if (this.mMediaController != null) {
            pw.print(" state=" + this.mMediaController.getPlaybackState());
        }
        pw.println();
        pw.print("    mMediaMetadata=");
        pw.print(this.mMediaMetadata);
        if (this.mMediaMetadata != null) {
            pw.print(" title=" + ((Object) this.mMediaMetadata.getText("android.media.metadata.TITLE")));
        }
        pw.println();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPlaybackActive(int state) {
        return (state == 1 || state == 7 || state == 0) ? false : true;
    }

    private boolean sameSessions(MediaController a, MediaController b) {
        if (a == b) {
            return true;
        }
        if (a == null) {
            return false;
        }
        return a.controlsSameSession(b);
    }

    private int getMediaControllerPlaybackState(MediaController controller) {
        PlaybackState playbackState;
        if (controller != null && (playbackState = controller.getPlaybackState()) != null) {
            return playbackState.getState();
        }
        return 0;
    }

    private boolean isMediaNotification(NotificationData.Entry entry) {
        return (entry.getExpandedContentView() == null || entry.getExpandedContentView().findViewById(R.id.fade_in) == null) ? false : true;
    }

    private void clearCurrentMediaNotificationSession() {
        this.mMediaMetadata = null;
        if (this.mMediaController != null) {
            this.mMediaController.unregisterCallback(this.mMediaListener);
        }
        this.mMediaController = null;
    }
}
