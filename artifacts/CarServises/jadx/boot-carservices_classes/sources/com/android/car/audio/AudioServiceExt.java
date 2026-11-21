package com.android.car.audio;

import android.media.AudioSystem;
import android.os.FileUtils;
import android.os.Handler;
import android.system.Os;
import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class AudioServiceExt {
    private static final String ALLOW_ADJUST_MUSIC_VOL_PACKAGE = "com.hcn.AutoMediaPlayer,com.txznet.smartadapter";
    private static final String TAG = "AudioServiceExt";
    private Reference<Handler> mRefHandler = null;
    private final UpdateVolumeRunnable mUpdateVolumeRunnable = new UpdateVolumeRunnable();
    private final File mAudioVolFile = new File("/tmp/system/audio_vol.kv");
    private final ConcurrentHashMap<String, Integer> mStreamVolumeMap = new ConcurrentHashMap<>();

    public void init(Handler handler) {
        this.mRefHandler = new WeakReference(handler);
    }

    public void release() {
    }

    public void updateVolume(int streamType, int index, int device) {
        boolean needUpdate = false;
        if (streamType == 0) {
            needUpdate = true;
            this.mStreamVolumeMap.put("volume_voice", Integer.valueOf(index));
        } else {
            switch (streamType) {
                case 2:
                    needUpdate = true;
                    this.mStreamVolumeMap.put("volume_ring", Integer.valueOf(index));
                    break;
                case 3:
                    needUpdate = true;
                    this.mStreamVolumeMap.put("volume_music", Integer.valueOf(index));
                    break;
            }
        }
        Handler handler = this.mRefHandler.get();
        if (handler == null || !needUpdate || handler.hasCallbacks(this.mUpdateVolumeRunnable)) {
            return;
        }
        handler.postDelayed(this.mUpdateVolumeRunnable, 1000L);
    }

    private final class UpdateVolumeRunnable implements Runnable {
        private UpdateVolumeRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            StringBuilder builder = new StringBuilder(128);
            for (String key : AudioServiceExt.this.mStreamVolumeMap.keySet()) {
                Integer value = (Integer) AudioServiceExt.this.mStreamVolumeMap.get(key);
                builder.append(key);
                builder.append("=");
                builder.append(value);
                builder.append("\n");
            }
            try {
                FileUtils.stringToFile(AudioServiceExt.this.mAudioVolFile, builder.toString());
                Os.chmod(AudioServiceExt.this.mAudioVolFile.getAbsolutePath(), 420);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean allowAdjustStreamVolume(int streamType, int index, int flags, String callingPackage, String caller, int uid) {
        if (uid < 10000) {
            return true;
        }
        if (streamType == 0) {
            return false;
        }
        if (streamType != 3) {
            return true;
        }
        return ALLOW_ADJUST_MUSIC_VOL_PACKAGE.contains(callingPackage);
    }

    public boolean isInCall() {
        return AudioSystem.isStreamActive(0, 0);
    }
}
