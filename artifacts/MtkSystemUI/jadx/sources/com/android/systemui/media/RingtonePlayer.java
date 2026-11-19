package com.android.systemui.media;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.IAudioService;
import android.media.IRingtonePlayer;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.util.Log;
import com.android.systemui.SystemUI;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class RingtonePlayer extends SystemUI {
    private IAudioService mAudioService;
    private final NotificationPlayer mAsyncPlayer = new NotificationPlayer("RingtonePlayer");
    private final HashMap<IBinder, Client> mClients = new HashMap<>();
    private IRingtonePlayer mCallback = new IRingtonePlayer.Stub() { // from class: com.android.systemui.media.RingtonePlayer.1
        public void play(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping) throws RemoteException {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(token);
                if (client == null) {
                    UserHandle user = Binder.getCallingUserHandle();
                    client = RingtonePlayer.this.new Client(token, uri, user, aa);
                    token.linkToDeath(client, 0);
                    RingtonePlayer.this.mClients.put(token, client);
                }
            }
            Client client2 = client;
            client2.mRingtone.setLooping(looping);
            client2.mRingtone.setVolume(volume);
            client2.mRingtone.play();
        }

        public void stop(IBinder token) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.remove(token);
            }
            if (client != null) {
                client.mToken.unlinkToDeath(client, 0);
                client.mRingtone.stop();
            }
        }

        public boolean isPlaying(IBinder token) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(token);
            }
            if (client != null) {
                return client.mRingtone.isPlaying();
            }
            return false;
        }

        public void setPlaybackProperties(IBinder token, float volume, boolean looping) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(token);
            }
            if (client != null) {
                client.mRingtone.setVolume(volume);
                client.mRingtone.setLooping(looping);
            }
        }

        public void playAsync(Uri uri, UserHandle user, boolean looping, AudioAttributes aa) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Async playback only available from system UID.");
            }
            if (UserHandle.ALL.equals(user)) {
                user = UserHandle.SYSTEM;
            }
            RingtonePlayer.this.mAsyncPlayer.play(RingtonePlayer.this.getContextForUser(user), uri, looping, aa);
        }

        public void stopAsync() {
            if (Binder.getCallingUid() == 1000) {
                RingtonePlayer.this.mAsyncPlayer.stop();
                return;
            }
            throw new SecurityException("Async playback only available from system UID.");
        }

        public String getTitle(Uri uri) {
            UserHandle user = Binder.getCallingUserHandle();
            return Ringtone.getTitle(RingtonePlayer.this.getContextForUser(user), uri, false, false);
        }

        public ParcelFileDescriptor openRingtone(Uri uri) {
            UserHandle user = Binder.getCallingUserHandle();
            ContentResolver resolver = RingtonePlayer.this.getContextForUser(user).getContentResolver();
            if (uri.toString().startsWith(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString())) {
                Cursor c = resolver.query(uri, new String[]{"is_ringtone", "is_alarm", "is_notification"}, null, null, null);
                Throwable th = null;
                try {
                    if (c.moveToFirst() && (c.getInt(0) != 0 || c.getInt(1) != 0 || c.getInt(2) != 0)) {
                        try {
                            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = resolver.openFileDescriptor(uri, "r");
                            if (c != null) {
                                c.close();
                            }
                            return parcelFileDescriptorOpenFileDescriptor;
                        } catch (IOException e) {
                            throw new SecurityException(e);
                        }
                    }
                    if (c != null) {
                        c.close();
                    }
                } catch (Throwable th2) {
                    if (c != null) {
                        if (0 != 0) {
                            try {
                                c.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        } else {
                            c.close();
                        }
                    }
                    throw th2;
                }
            }
            throw new SecurityException("Uri is not ringtone, alarm, or notification: " + uri);
        }
    };

    @Override // com.android.systemui.SystemUI
    public void start() {
        this.mAsyncPlayer.setUsesWakeLock(this.mContext);
        this.mAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        try {
            this.mAudioService.setRingtonePlayer(this.mCallback);
        } catch (RemoteException e) {
            Log.e("RingtonePlayer", "Problem registering RingtonePlayer: " + e);
        }
    }

    private class Client implements IBinder.DeathRecipient {
        private final Ringtone mRingtone;
        private final IBinder mToken;

        public Client(IBinder token, Uri uri, UserHandle user, AudioAttributes aa) {
            this.mToken = token;
            this.mRingtone = new Ringtone(RingtonePlayer.this.getContextForUser(user), false);
            this.mRingtone.setAudioAttributes(aa);
            this.mRingtone.setUri(uri);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (RingtonePlayer.this.mClients) {
                RingtonePlayer.this.mClients.remove(this.mToken);
            }
            this.mRingtone.stop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Context getContextForUser(UserHandle user) {
        try {
            return this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, user);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("Clients:");
        synchronized (this.mClients) {
            for (Client client : this.mClients.values()) {
                pw.print("  mToken=");
                pw.print(client.mToken);
                pw.print(" mUri=");
                pw.println(client.mRingtone.getUri());
            }
        }
    }
}
