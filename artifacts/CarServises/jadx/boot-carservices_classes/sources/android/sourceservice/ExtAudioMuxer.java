package android.sourceservice;

import android.Configures.HConfig;
import android.carsource.McuService;
import android.media.AudioSystem;
import java.io.File;
import java.util.EnumSet;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class ExtAudioMuxer {
    static final int CMD_INPUT_GAIN_STATUS = 3;
    private static final int CMD_INPUT_MUTE_STATUS = 4;
    private static final int CMD_INPUT_STATUS = 1;
    private static final int CMD_MIXING_STATUS = 32;
    private static final int CMD_SYS_MUTE_STATUS = 17;
    private static final int CMD_SYS_VOLUME_MAX = 9;
    private static final int CMD_SYS_VOLUME_STATUS = 16;
    private static final int CMD_VOLUME_PERCENTAGE = 18;
    public static final boolean ExtBluetooth = false;
    private static final int MIX_PATH_BASE = 32;
    public static final int PATH_AUX2 = 4;
    public static final int PATH_AUX = 2;
    public static final int PATH_DAB = 5;
    public static final int PATH_EXTBT = 3;
    public static final int PATH_MUSIC = 1;
    public static final int PATH_RADIO = 0;
    private static final String TAG = "ExtAudioMuxer";
    public static final boolean ExtAudioAvailable = new File(HConfig.CUSTOM_SOUND_CHIP_PATH).exists();

    static native int SendCmdAudioMux(int i, int[] iArr);

    public static native int SendCmdVideoMux(String str);

    static native void nativeInit();

    private ExtAudioMuxer() {
    }

    static void DoAudioMux(EnumSet<SourceService.PATH> path) {
        int[] data = new int[1];
        if (path.contains(SourceService.PATH.HFP)) {
            data[0] = 1;
        } else if (path.contains(SourceService.PATH.RGEAR) || path.contains(SourceService.PATH.ONSTAR)) {
            data[0] = 2;
        } else if (path.contains(SourceService.PATH.RADIO) || path.contains(SourceService.PATH.RDS)) {
            data[0] = 0;
        } else if (path.contains(SourceService.PATH.AUX)) {
            data[0] = 2;
        } else if (path.contains(SourceService.PATH.A2DP) || path.contains(SourceService.PATH.MUSIC)) {
            data[0] = 1;
        } else if (path.contains(SourceService.PATH.AUX2)) {
            data[0] = 4;
        } else if (path.contains(SourceService.PATH.DAB)) {
            data[0] = 5;
        } else {
            data[0] = -1;
        }
        SendCmdAudioMux(1, data);
    }

    static void DoMix(boolean on, int backvol) {
        int[] data = new int[3];
        if (on) {
            data[0] = 1;
            data[1] = 32;
            data[2] = backvol;
        } else {
            data[0] = 0;
            data[1] = 32;
        }
        SendCmdAudioMux(32, data);
    }

    public static void setMasterVolumeIndex(int streamType, int index, int device, int mode) {
        boolean needadj = false;
        if (device == 8 || device == 2) {
            if (AudioSystem.isStreamActive(2, 0) && streamType == 2) {
                needadj = true;
            }
            boolean inCall = AudioSystem.isStreamActive(0, 0);
            if (inCall && streamType == 0) {
                needadj = true;
            }
            if (!inCall && streamType == 3) {
                needadj = true;
            }
        }
        if (needadj) {
            int[] data = {index};
            SendCmdAudioMux(16, data);
            McuService.sendCmdCarStatus(index == 0 ? "mute=40004" : "mute=40000");
        }
    }

    public static void setInputPathMute(int i, boolean z, int i2) {
        SendCmdAudioMux(4, new int[]{i, z ? 1 : 0, i2});
    }

    public static void setMasterMute(boolean z, int i) {
        SendCmdAudioMux(17, new int[]{z ? 1 : 0, i});
    }

    public static void setMasterVolumeMax(int max) {
        int[] data = {max};
        SendCmdAudioMux(9, data);
    }

    public static void setMastVolumePercentage(int percent) {
        int[] data = {percent};
        SendCmdAudioMux(18, data);
    }
}
