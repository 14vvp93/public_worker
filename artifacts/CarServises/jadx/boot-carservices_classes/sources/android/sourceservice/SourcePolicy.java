package android.sourceservice;

import android.Configures.HConfig;
import android.content.ContentResolver;
import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import java.util.EnumSet;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
class SourcePolicy {
    private static final String AUDIOFLINGER_DESC = "android.media.IAudioFlinger";
    private static final String AUDIOPOLICY_DESC = "android.media.IAudioPolicyService";
    private static final int SET_STREAMS_MUTE = 1004;
    private static final int STREAM_GAIN_SETTING = 1001;
    public static final int STREAM_GIS = 5;
    private static final int STREAM_MUSIC_FADE = 1003;
    private static final int STREAM_MUSIC_VOLUME = 1002;
    final Context mContext;
    volatile int mSessionId = 0;
    private int mMusicVolume = 100;

    SourcePolicy(Context context) {
        this.mContext = context;
    }

    void onNormalAudioPath(EnumSet<SourceService.PATH> path) {
        ExtAudioMuxer.DoAudioMux(path);
    }

    void onMixingStatus(EnumSet<SourceService.PATH> path) {
        if (path.contains(SourceService.PATH.HFP)) {
            if (!path.contains(SourceService.PATH.MIX)) {
                ExtAudioMuxer.DoMix(false, 0);
                return;
            }
            return;
        }
        if (path.contains(SourceService.PATH.ONSTAR)) {
            remoteNaviStreamVol(true);
            if (path.contains(SourceService.PATH.MIX) || path.contains(SourceService.PATH.MIXVOC)) {
                ExtAudioMuxer.DoMix(false, 0);
                return;
            }
            return;
        }
        if (path.contains(SourceService.PATH.MIXVOC)) {
            this.mMusicVolume = 0;
            remoteMusicVolume(this.mMusicVolume);
            remoteNaviStreamVol(true);
            ExtAudioMuxer.DoMix(true, 0);
            return;
        }
        if (path.contains(SourceService.PATH.MIX)) {
            ContentResolver cr = this.mContext.getContentResolver();
            int index = Settings.System.getInt(cr, HConfig.audio_background_volume, 25);
            this.mMusicVolume = Math.max(Math.min(index, 100), 0);
            remoteMusicVolume(this.mMusicVolume);
            remoteNaviStreamVol(false);
            ExtAudioMuxer.DoMix(true, index);
            return;
        }
        remoteNaviStreamVol(false);
        this.mMusicVolume = 100;
        remoteMusicVolume(this.mMusicVolume);
        ExtAudioMuxer.DoMix(false, 0);
    }

    void onBluetoothConnectStatus(boolean status, EnumSet<SourceService.PATH> path) {
        ExtAudioMuxer.DoAudioMux(path);
    }

    void onBluetoothVoiceStatus(boolean status, EnumSet<SourceService.PATH> path) {
        if (status) {
            this.mMusicVolume = 0;
            remoteMusicVolume(this.mMusicVolume);
            remoteNaviStreamVol(true);
            ExtAudioMuxer.DoAudioMux(path);
            return;
        }
        ExtAudioMuxer.DoAudioMux(path);
        if (path.contains(SourceService.PATH.MIXVOC)) {
            this.mMusicVolume = 0;
            remoteNaviStreamVol(true);
        } else if (path.contains(SourceService.PATH.MIX)) {
            ContentResolver cr = this.mContext.getContentResolver();
            int index = Settings.System.getInt(cr, HConfig.audio_background_volume, 25);
            this.mMusicVolume = Math.max(Math.min(index, 100), 0);
            remoteNaviStreamVol(false);
        } else {
            this.mMusicVolume = 100;
            remoteNaviStreamVol(false);
        }
        remoteMusicVolume(this.mMusicVolume);
    }

    void onRevGear(boolean on, EnumSet<SourceService.PATH> path) {
    }

    void onSessionChanged(int session) {
        this.mSessionId = session;
        remoteMusicVolume(this.mMusicVolume);
    }

    void onMixSessionChanged(int session) {
        if (this.mSessionId == session) {
            remoteMusicVolume(100);
        }
    }

    private void remoteMusicVolume(int volume) {
        Log.d("SourceService", "set Music mix volume " + volume + ", session " + this.mSessionId);
        IBinder service = ServiceManager.getService("media.audio_flinger");
        if (service != null && this.mSessionId > 0) {
            Parcel _data = Parcel.obtain();
            try {
                try {
                    _data.writeInterfaceToken(AUDIOFLINGER_DESC);
                    _data.writeInt(this.mSessionId);
                    _data.writeInt(volume);
                    service.transact(STREAM_MUSIC_VOLUME, _data, null, 1);
                } catch (Exception e) {
                    Log.w("SourceService", "media.audio_flinger binder failed");
                }
            } finally {
                _data.recycle();
            }
        }
    }

    void setStreamVolumeGain(int streamType, int gain) {
        IBinder service = ServiceManager.getService("media.audio_policy");
        if (service != null) {
            Parcel _data = Parcel.obtain();
            try {
                try {
                    _data.writeInterfaceToken(AUDIOPOLICY_DESC);
                    _data.writeInt(streamType);
                    _data.writeInt(gain);
                    service.transact(STREAM_GAIN_SETTING, _data, null, 1);
                } catch (Exception e) {
                    Log.w("SourceService", "media.audio_policy binder failed");
                }
            } finally {
                _data.recycle();
            }
        }
    }

    void setMusicFadeout() {
        Log.d("SourceService", "set Music Fadeout session " + this.mSessionId);
        IBinder service = ServiceManager.getService("media.audio_flinger");
        if (service != null && this.mSessionId > 0) {
            Parcel _data = Parcel.obtain();
            try {
                try {
                    _data.writeInterfaceToken(AUDIOFLINGER_DESC);
                    _data.writeInt(this.mSessionId);
                    _data.writeInt(1200);
                    service.transact(STREAM_MUSIC_FADE, _data, null, 1);
                } catch (Exception e) {
                    Log.w("SourceService", "media.audio_flinger binder failed");
                }
            } finally {
                _data.recycle();
            }
        }
    }

    private void remoteNaviStreamVol(boolean mute) {
        IBinder service = ServiceManager.getService("media.audio_flinger");
        if (service != null) {
            int mask = mute ? 32 : 0;
            Parcel _data = Parcel.obtain();
            try {
                try {
                    _data.writeInterfaceToken(AUDIOFLINGER_DESC);
                    _data.writeInt(mask);
                    service.transact(SET_STREAMS_MUTE, _data, null, 1);
                } catch (Exception e) {
                    Log.w("SourceService", "media.audio_flinger binder failed");
                }
            } finally {
                _data.recycle();
            }
        }
    }
}
