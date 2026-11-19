package android.support.v4.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.os.ResultReceiver;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
@TargetApi(16)
class MediaController2ImplLegacy implements MediaController2.SupportLibraryImpl {
    private static final boolean DEBUG = Log.isLoggable("MC2ImplLegacy", 3);
    static final Bundle sDefaultRootExtras = new Bundle();
    private SessionCommandGroup2 mAllowedCommands;
    private MediaBrowserCompat mBrowserCompat;
    private int mBufferingState;
    private final MediaController2.ControllerCallback mCallback;
    private final Executor mCallbackExecutor;
    private volatile boolean mConnected;
    private final Context mContext;
    private MediaControllerCompat mControllerCompat;
    private ControllerCompatCallback mControllerCompatCallback;
    private MediaItem2 mCurrentMediaItem;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private MediaController2 mInstance;
    private boolean mIsReleased;
    final Object mLock;
    private MediaMetadataCompat mMediaMetadataCompat;
    private MediaController2.PlaybackInfo mPlaybackInfo;
    private PlaybackStateCompat mPlaybackStateCompat;
    private int mPlayerState;
    private List<MediaItem2> mPlaylist;
    private MediaMetadata2 mPlaylistMetadata;
    private int mRepeatMode;
    private int mShuffleMode;
    private final SessionToken2 mToken;

    static {
        sDefaultRootExtras.putBoolean("android.support.v4.media.root_default_root", true);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (DEBUG) {
            Log.d("MC2ImplLegacy", "release from " + this.mToken);
        }
        synchronized (this.mLock) {
            if (this.mIsReleased) {
                return;
            }
            this.mHandler.removeCallbacksAndMessages(null);
            if (Build.VERSION.SDK_INT >= 18) {
                this.mHandlerThread.quitSafely();
            } else {
                this.mHandlerThread.quit();
            }
            this.mIsReleased = true;
            sendCommand("android.support.v4.media.controller.command.DISCONNECT");
            if (this.mControllerCompat != null) {
                this.mControllerCompat.unregisterCallback(this.mControllerCompatCallback);
            }
            if (this.mBrowserCompat != null) {
                this.mBrowserCompat.disconnect();
                this.mBrowserCompat = null;
            }
            if (this.mControllerCompat != null) {
                this.mControllerCompat.unregisterCallback(this.mControllerCompatCallback);
                this.mControllerCompat = null;
            }
            this.mConnected = false;
            this.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.1
                @Override // java.lang.Runnable
                public void run() {
                    MediaController2ImplLegacy.this.mCallback.onDisconnected(MediaController2ImplLegacy.this.mInstance);
                }
            });
        }
    }

    void onConnectedNotLocked(Bundle data) {
        data.setClassLoader(MediaSession2.class.getClassLoader());
        final SessionCommandGroup2 allowedCommands = SessionCommandGroup2.fromBundle(data.getBundle("android.support.v4.media.argument.ALLOWED_COMMANDS"));
        int playerState = data.getInt("android.support.v4.media.argument.PLAYER_STATE");
        MediaItem2 currentMediaItem = MediaItem2.fromBundle(data.getBundle("android.support.v4.media.argument.MEDIA_ITEM"));
        int bufferingState = data.getInt("android.support.v4.media.argument.BUFFERING_STATE");
        PlaybackStateCompat playbackStateCompat = (PlaybackStateCompat) data.getParcelable("android.support.v4.media.argument.PLAYBACK_STATE_COMPAT");
        int repeatMode = data.getInt("android.support.v4.media.argument.REPEAT_MODE");
        int shuffleMode = data.getInt("android.support.v4.media.argument.SHUFFLE_MODE");
        List<MediaItem2> playlist = MediaUtils2.convertToMediaItem2List(data.getParcelableArray("android.support.v4.media.argument.PLAYLIST"));
        MediaController2.PlaybackInfo playbackInfo = MediaController2.PlaybackInfo.fromBundle(data.getBundle("android.support.v4.media.argument.PLAYBACK_INFO"));
        MediaMetadata2 metadata = MediaMetadata2.fromBundle(data.getBundle("android.support.v4.media.argument.PLAYLIST_METADATA"));
        if (DEBUG) {
            Log.d("MC2ImplLegacy", "onConnectedNotLocked token=" + this.mToken + ", allowedCommands=" + allowedCommands);
        }
        try {
            synchronized (this.mLock) {
                if (this.mIsReleased) {
                    if (0 != 0) {
                        close();
                        return;
                    }
                    return;
                }
                if (this.mConnected) {
                    Log.e("MC2ImplLegacy", "Cannot be notified about the connection result many times. Probably a bug or malicious app.");
                    if (1 != 0) {
                        close();
                        return;
                    }
                    return;
                }
                this.mAllowedCommands = allowedCommands;
                this.mPlayerState = playerState;
                this.mCurrentMediaItem = currentMediaItem;
                this.mBufferingState = bufferingState;
                this.mPlaybackStateCompat = playbackStateCompat;
                this.mRepeatMode = repeatMode;
                this.mShuffleMode = shuffleMode;
                this.mPlaylist = playlist;
                this.mPlaylistMetadata = metadata;
                this.mConnected = true;
                this.mPlaybackInfo = playbackInfo;
                this.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MediaController2ImplLegacy.this.mCallback.onConnected(MediaController2ImplLegacy.this.mInstance, allowedCommands);
                    }
                });
                if (0 != 0) {
                    close();
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                close();
            }
            throw th;
        }
    }

    /* JADX INFO: renamed from: android.support.v4.media.MediaController2ImplLegacy$3, reason: invalid class name */
    class AnonymousClass3 extends ResultReceiver {
        final /* synthetic */ MediaController2ImplLegacy this$0;

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (this.this$0.mHandlerThread.isAlive()) {
                switch (resultCode) {
                    case -1:
                        this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass3.this.this$0.mCallback.onDisconnected(AnonymousClass3.this.this$0.mInstance);
                            }
                        });
                        this.this$0.close();
                        break;
                    case 0:
                        this.this$0.onConnectedNotLocked(resultData);
                        break;
                }
            }
        }
    }

    private void sendCommand(String command) throws Throwable {
        sendCommand(command, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCommand(String command, ResultReceiver receiver) throws Throwable {
        sendCommand(command, null, receiver);
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0049 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendCommand(String command, Bundle args, ResultReceiver receiver) throws Throwable {
        if (args == null) {
            args = new Bundle();
        }
        synchronized (this.mLock) {
            ControllerCompatCallback callback = null;
            try {
                MediaControllerCompat controller = this.mControllerCompat;
                try {
                    callback = this.mControllerCompatCallback;
                    BundleCompat.putBinder(args, "android.support.v4.media.argument.ICONTROLLER_CALLBACK", callback.getIControllerCallback().asBinder());
                    args.putString("android.support.v4.media.argument.PACKAGE_NAME", this.mContext.getPackageName());
                    args.putInt("android.support.v4.media.argument.UID", Process.myUid());
                    args.putInt("android.support.v4.media.argument.PID", Process.myPid());
                    controller.sendCommand(command, args, receiver);
                } catch (Throwable th) {
                    th = th;
                    while (true) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    private final class ControllerCompatCallback extends MediaControllerCompat.Callback {
        final /* synthetic */ MediaController2ImplLegacy this$0;

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onSessionReady() throws Throwable {
            this.this$0.sendCommand("android.support.v4.media.controller.command.CONNECT", new ResultReceiver(this.this$0.mHandler) { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.1
                @Override // android.os.ResultReceiver
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    if (ControllerCompatCallback.this.this$0.mHandlerThread.isAlive()) {
                        switch (resultCode) {
                            case -1:
                                ControllerCompatCallback.this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        ControllerCompatCallback.this.this$0.mCallback.onDisconnected(ControllerCompatCallback.this.this$0.mInstance);
                                    }
                                });
                                ControllerCompatCallback.this.this$0.close();
                                break;
                            case 0:
                                ControllerCompatCallback.this.this$0.onConnectedNotLocked(resultData);
                                break;
                        }
                    }
                }
            });
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onSessionDestroyed() {
            this.this$0.close();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            synchronized (this.this$0.mLock) {
                this.this$0.mPlaybackStateCompat = state;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            synchronized (this.this$0.mLock) {
                this.this$0.mMediaMetadataCompat = metadata;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onSessionEvent(String event, Bundle extras) {
            if (extras != null) {
                extras.setClassLoader(MediaSession2.class.getClassLoader());
            }
            byte b = -1;
            switch (event.hashCode()) {
                case -2076894204:
                    if (event.equals("android.support.v4.media.session.event.ON_BUFFERING_STATE_CHANGED")) {
                        b = 13;
                    }
                    break;
                case -2060536131:
                    if (event.equals("android.support.v4.media.session.event.ON_PLAYBACK_SPEED_CHANGED")) {
                        b = 12;
                    }
                    break;
                case -1588811870:
                    if (event.equals("android.support.v4.media.session.event.ON_PLAYBACK_INFO_CHANGED")) {
                        b = 11;
                    }
                    break;
                case -1471144819:
                    if (event.equals("android.support.v4.media.session.event.ON_PLAYER_STATE_CHANGED")) {
                        b = 1;
                    }
                    break;
                case -1021916189:
                    if (event.equals("android.support.v4.media.session.event.ON_ERROR")) {
                        b = 3;
                    }
                    break;
                case -617184370:
                    if (event.equals("android.support.v4.media.session.event.ON_CURRENT_MEDIA_ITEM_CHANGED")) {
                        b = 2;
                    }
                    break;
                case -92092013:
                    if (event.equals("android.support.v4.media.session.event.ON_ROUTES_INFO_CHANGED")) {
                        b = 4;
                    }
                    break;
                case -53555497:
                    if (event.equals("android.support.v4.media.session.event.ON_REPEAT_MODE_CHANGED")) {
                        b = 7;
                    }
                    break;
                case 229988025:
                    if (event.equals("android.support.v4.media.session.event.SEND_CUSTOM_COMMAND")) {
                        b = 9;
                    }
                    break;
                case 306321100:
                    if (event.equals("android.support.v4.media.session.event.ON_PLAYLIST_METADATA_CHANGED")) {
                        b = 6;
                    }
                    break;
                case 408969344:
                    if (event.equals("android.support.v4.media.session.event.SET_CUSTOM_LAYOUT")) {
                        b = 10;
                    }
                    break;
                case 806201420:
                    if (event.equals("android.support.v4.media.session.event.ON_PLAYLIST_CHANGED")) {
                        b = 5;
                    }
                    break;
                case 896576579:
                    if (event.equals("android.support.v4.media.session.event.ON_SHUFFLE_MODE_CHANGED")) {
                        b = 8;
                    }
                    break;
                case 1696119769:
                    if (event.equals("android.support.v4.media.session.event.ON_ALLOWED_COMMANDS_CHANGED")) {
                        b = 0;
                    }
                    break;
                case 1871849865:
                    if (event.equals("android.support.v4.media.session.event.ON_SEEK_COMPLETED")) {
                        b = 14;
                    }
                    break;
            }
            switch (b) {
                case 0:
                    final SessionCommandGroup2 allowedCommands = SessionCommandGroup2.fromBundle(extras.getBundle("android.support.v4.media.argument.ALLOWED_COMMANDS"));
                    synchronized (this.this$0.mLock) {
                        this.this$0.mAllowedCommands = allowedCommands;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.2
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onAllowedCommandsChanged(ControllerCompatCallback.this.this$0.mInstance, allowedCommands);
                        }
                    });
                    return;
                case 1:
                    final int playerState = extras.getInt("android.support.v4.media.argument.PLAYER_STATE");
                    PlaybackStateCompat state = (PlaybackStateCompat) extras.getParcelable("android.support.v4.media.argument.PLAYBACK_STATE_COMPAT");
                    if (state == null) {
                        return;
                    }
                    synchronized (this.this$0.mLock) {
                        this.this$0.mPlayerState = playerState;
                        this.this$0.mPlaybackStateCompat = state;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.3
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onPlayerStateChanged(ControllerCompatCallback.this.this$0.mInstance, playerState);
                        }
                    });
                    return;
                case 2:
                    final MediaItem2 item = MediaItem2.fromBundle(extras.getBundle("android.support.v4.media.argument.MEDIA_ITEM"));
                    synchronized (this.this$0.mLock) {
                        this.this$0.mCurrentMediaItem = item;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.4
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onCurrentMediaItemChanged(ControllerCompatCallback.this.this$0.mInstance, item);
                        }
                    });
                    return;
                case 3:
                    final int errorCode = extras.getInt("android.support.v4.media.argument.ERROR_CODE");
                    final Bundle errorExtras = extras.getBundle("android.support.v4.media.argument.EXTRAS");
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.5
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onError(ControllerCompatCallback.this.this$0.mInstance, errorCode, errorExtras);
                        }
                    });
                    return;
                case 4:
                    final List<Bundle> routes = MediaUtils2.convertToBundleList(extras.getParcelableArray("android.support.v4.media.argument.ROUTE_BUNDLE"));
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.6
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onRoutesInfoChanged(ControllerCompatCallback.this.this$0.mInstance, routes);
                        }
                    });
                    return;
                case 5:
                    final MediaMetadata2 playlistMetadata = MediaMetadata2.fromBundle(extras.getBundle("android.support.v4.media.argument.PLAYLIST_METADATA"));
                    final List<MediaItem2> playlist = MediaUtils2.convertToMediaItem2List(extras.getParcelableArray("android.support.v4.media.argument.PLAYLIST"));
                    synchronized (this.this$0.mLock) {
                        this.this$0.mPlaylist = playlist;
                        this.this$0.mPlaylistMetadata = playlistMetadata;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.7
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onPlaylistChanged(ControllerCompatCallback.this.this$0.mInstance, playlist, playlistMetadata);
                        }
                    });
                    return;
                case 6:
                    final MediaMetadata2 playlistMetadata2 = MediaMetadata2.fromBundle(extras.getBundle("android.support.v4.media.argument.PLAYLIST_METADATA"));
                    synchronized (this.this$0.mLock) {
                        this.this$0.mPlaylistMetadata = playlistMetadata2;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.8
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onPlaylistMetadataChanged(ControllerCompatCallback.this.this$0.mInstance, playlistMetadata2);
                        }
                    });
                    return;
                case 7:
                    final int repeatMode = extras.getInt("android.support.v4.media.argument.REPEAT_MODE");
                    synchronized (this.this$0.mLock) {
                        this.this$0.mRepeatMode = repeatMode;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.9
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onRepeatModeChanged(ControllerCompatCallback.this.this$0.mInstance, repeatMode);
                        }
                    });
                    return;
                case 8:
                    final int shuffleMode = extras.getInt("android.support.v4.media.argument.SHUFFLE_MODE");
                    synchronized (this.this$0.mLock) {
                        this.this$0.mShuffleMode = shuffleMode;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.10
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onShuffleModeChanged(ControllerCompatCallback.this.this$0.mInstance, shuffleMode);
                        }
                    });
                    return;
                case 9:
                    Bundle commandBundle = extras.getBundle("android.support.v4.media.argument.CUSTOM_COMMAND");
                    if (commandBundle == null) {
                        return;
                    }
                    final SessionCommand2 command = SessionCommand2.fromBundle(commandBundle);
                    final Bundle args = extras.getBundle("android.support.v4.media.argument.ARGUMENTS");
                    final ResultReceiver receiver = (ResultReceiver) extras.getParcelable("android.support.v4.media.argument.RESULT_RECEIVER");
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.11
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onCustomCommand(ControllerCompatCallback.this.this$0.mInstance, command, args, receiver);
                        }
                    });
                    return;
                case 10:
                    final List<MediaSession2.CommandButton> layout = MediaUtils2.convertToCommandButtonList(extras.getParcelableArray("android.support.v4.media.argument.COMMAND_BUTTONS"));
                    if (layout != null) {
                        this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.12
                            @Override // java.lang.Runnable
                            public void run() {
                                ControllerCompatCallback.this.this$0.mCallback.onCustomLayoutChanged(ControllerCompatCallback.this.this$0.mInstance, layout);
                            }
                        });
                        return;
                    }
                    return;
                case 11:
                    final MediaController2.PlaybackInfo info = MediaController2.PlaybackInfo.fromBundle(extras.getBundle("android.support.v4.media.argument.PLAYBACK_INFO"));
                    if (info == null) {
                        return;
                    }
                    synchronized (this.this$0.mLock) {
                        this.this$0.mPlaybackInfo = info;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.13
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onPlaybackInfoChanged(ControllerCompatCallback.this.this$0.mInstance, info);
                        }
                    });
                    return;
                case 12:
                    final PlaybackStateCompat state2 = (PlaybackStateCompat) extras.getParcelable("android.support.v4.media.argument.PLAYBACK_STATE_COMPAT");
                    if (state2 == null) {
                        return;
                    }
                    synchronized (this.this$0.mLock) {
                        this.this$0.mPlaybackStateCompat = state2;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.14
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onPlaybackSpeedChanged(ControllerCompatCallback.this.this$0.mInstance, state2.getPlaybackSpeed());
                        }
                    });
                    return;
                case 13:
                    final MediaItem2 item2 = MediaItem2.fromBundle(extras.getBundle("android.support.v4.media.argument.MEDIA_ITEM"));
                    final int bufferingState = extras.getInt("android.support.v4.media.argument.BUFFERING_STATE");
                    PlaybackStateCompat state3 = (PlaybackStateCompat) extras.getParcelable("android.support.v4.media.argument.PLAYBACK_STATE_COMPAT");
                    if (item2 == null || state3 == null) {
                        return;
                    }
                    synchronized (this.this$0.mLock) {
                        this.this$0.mBufferingState = bufferingState;
                        this.this$0.mPlaybackStateCompat = state3;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.15
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onBufferingStateChanged(ControllerCompatCallback.this.this$0.mInstance, item2, bufferingState);
                        }
                    });
                    return;
                case 14:
                    final long position = extras.getLong("android.support.v4.media.argument.SEEK_POSITION");
                    PlaybackStateCompat state4 = (PlaybackStateCompat) extras.getParcelable("android.support.v4.media.argument.PLAYBACK_STATE_COMPAT");
                    if (state4 == null) {
                        return;
                    }
                    synchronized (this.this$0.mLock) {
                        this.this$0.mPlaybackStateCompat = state4;
                        break;
                    }
                    this.this$0.mCallbackExecutor.execute(new Runnable() { // from class: android.support.v4.media.MediaController2ImplLegacy.ControllerCompatCallback.16
                        @Override // java.lang.Runnable
                        public void run() {
                            ControllerCompatCallback.this.this$0.mCallback.onSeekCompleted(ControllerCompatCallback.this.this$0.mInstance, position);
                        }
                    });
                    return;
                default:
                    return;
            }
        }
    }
}
