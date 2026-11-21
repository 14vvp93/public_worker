package android.audio;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.sourceservice.ISourceService;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class AudioEffect {
    private static final int CMD_BALANCE_STATUS = 21;
    private static final int CMD_EQ_ALTO_STATUS = 91;
    private static final int CMD_EQ_BAND_STATUS = 80;
    private static final int CMD_EQ_BASSBOOST_STATUS = 85;
    private static final int CMD_EQ_COMPRESSOR_STATUS = 88;
    private static final int CMD_EQ_DELAY_STATUS = 96;
    private static final int CMD_EQ_FREQEXPANSION_STATUS = 89;
    private static final int CMD_EQ_HFE_STATUS = 89;
    private static final int CMD_EQ_LOUDNESS_STATUS = 86;
    private static final int CMD_EQ_SURROUND_STATUS = 87;
    private static final int CMD_EQ_TREBLE_STATUS = 92;
    private static final int CMD_INPUT_GAIN_STATUS = 3;
    public static final int CMD_MASTER_VOLUME = 1000;
    private static final int CMD_SUBWOOFER_LPF_FREQ = 163;
    private static final int CMD_XOVER_HIGH_FREQ = 162;
    private static final int CMD_XOVER_LOW_FREQ = 161;
    public static final int DONOTCARE = Integer.MAX_VALUE;
    public static final int PATH_AUX = 2;
    public static final int PATH_BTVOICE = 106;
    public static final int PATH_MUSIC = 1;
    public static final int PATH_NAVI = 105;
    public static final int PATH_RADIO = 0;
    public static final int PATH_RING = 102;
    private static final String TAG = "AudioEffect";
    static AudioEffect sInstance;
    private final ISourceService mService = ISourceService.Stub.asInterface(ServiceManager.getService("sourceinfo"));

    public static AudioEffect getInstance() {
        if (sInstance == null) {
            sInstance = new AudioEffect();
        }
        return sInstance;
    }

    private AudioEffect() {
        if (this.mService == null) {
            throw new RuntimeException("can not find sourceinfo serivce");
        }
    }

    public void setMasterVolume(float left, float right) {
        int[] data = {Math.max(0, Math.min(100, (int) (left * 100.0f))), Math.max(0, Math.min(100, (int) (100.0f * right)))};
        doExtAudioEffect(1000, data);
    }

    public int setEQValue(int index, int cfreq, int gain, int qvalue) {
        int[] data = {index, cfreq, gain, qvalue};
        return doExtAudioEffect(CMD_EQ_BAND_STATUS, data);
    }

    public int setBalance(int fl, int fr, int rl, int rr) {
        int[] data = {fl, fr, rl, rr};
        return doExtAudioEffect(21, data);
    }

    public int setBassBoost(int value, int freq) {
        int[] data = new int[4];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = freq;
        data[3] = 10;
        return doExtAudioEffect(85, data);
    }

    public int setAlto(int value, int freq) {
        int[] data = new int[4];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = freq;
        data[3] = 10;
        return doExtAudioEffect(91, data);
    }

    public int setTreble(int value, int freq) {
        int[] data = new int[4];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = freq;
        data[3] = 10;
        return doExtAudioEffect(CMD_EQ_TREBLE_STATUS, data);
    }

    public int setLoud(int value) {
        int[] data = new int[3];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = Integer.MAX_VALUE;
        return doExtAudioEffect(86, data);
    }

    public int setSurround(int value) {
        int[] data = new int[2];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        return doExtAudioEffect(87, data);
    }

    public int setCompressor(int value) {
        int[] data = new int[3];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = Integer.MAX_VALUE;
        return doExtAudioEffect(88, data);
    }

    public int setFreqExpansion(int value) {
        int[] data = new int[3];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = Integer.MAX_VALUE;
        return doExtAudioEffect(89, data);
    }

    public int setDelay(int fl, int fr, int rl, int rr, int swl, int swr) {
        int[] data = {fl, fr, rl, rr, swl, swr};
        return doExtAudioEffect(CMD_EQ_DELAY_STATUS, data);
    }

    public int setSubWoofer(int value, int freq) {
        int[] data = new int[4];
        data[0] = value > 0 ? 1 : 0;
        data[1] = value;
        data[2] = freq;
        data[3] = 10;
        return doExtAudioEffect(CMD_SUBWOOFER_LPF_FREQ, data);
    }

    public int setXOverLowFreq(int freq, int gain, int qvalue) {
        int[] data = new int[4];
        data[0] = gain == 0 ? 0 : 1;
        data[1] = freq;
        data[2] = gain;
        data[3] = qvalue;
        return doExtAudioEffect(CMD_XOVER_LOW_FREQ, data);
    }

    public int setXOverHeighLowFreq(int freq, int gain, int qvalue) {
        int[] data = new int[4];
        data[0] = gain == 0 ? 0 : 1;
        data[1] = freq;
        data[2] = gain;
        data[3] = qvalue;
        return doExtAudioEffect(CMD_XOVER_HIGH_FREQ, data);
    }

    public int setInputGain(int input, int gain) {
        int[] data = {input, gain};
        return doExtAudioEffect(3, data);
    }

    public int doExtAudioEffect(int cmd, int[] data) {
        try {
            int ret = this.mService.doExtAudioEffect(cmd, data);
            return ret;
        } catch (RemoteException | NullPointerException e) {
            return -1;
        }
    }
}
