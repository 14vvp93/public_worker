package android.radio;

import android.carsource.McuConstant;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.Arrays;
import java.util.Locale;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class RadioPlayer {
    public static final String ACTION_TA_MESSAGE = "radio.RadioPlayer.TA_MESSAGE";
    public static final int EVENT_LIST_CHANGED = 7;
    public static final int EVENT_PS_DONE = 8;
    public static final int EVENT_PS_MESSAGE = 38;
    public static final int EVENT_PTY_TYPE = 34;
    public static final int EVENT_RDS_STATE = 4;
    public static final int EVENT_RT_MESSAGE = 41;
    public static final int EVENT_SCAN_DONE = 2;
    public static final int EVENT_STATE = 1;
    public static final String EXTRA_KEY_MESSAGE = "radio.RadioPlayer.extra.KEY_EVENT";
    static final int PRESET_PLAY = 5;
    static final int RDS_SCAN = 6;
    static final int SEEK_ALL = 1;
    static final int SEEK_DOWN = 4;
    static final int SEEK_PLAY = 2;
    static final int SEEK_UP = 3;
    private static final String TAG = "RadioPlayer";
    private OnEventListener mOnEventListener;
    private static RadioPlayer sRadioPlayer;
    private IRadioPlayer mService = IRadioPlayer.Stub.asInterface(ServiceManager.getService("fmradio"));
    private final IRadioCallback mToken = new IRadioCallback.Stub() { // from class: android.radio.RadioPlayer.1
        @Override // android.radio.IRadioCallback
        public void onEvent(int cmd, RadioInfo info) {
            OnEventListener l = RadioPlayer.this.mOnEventListener;
            if (info != null && l != null) {
                l.onEvent(cmd, info);
            }
        }

        @Override // android.radio.IRadioCallback
        public void onStringEvent(int cmd, String info) {
            OnEventListener l = RadioPlayer.this.mOnEventListener;
            if (l != null) {
                l.onEvent(cmd, info);
            }
        }
    };

    public interface OnEventListener {
        void onEvent(int i, RadioInfo radioInfo);

        void onEvent(int i, String str);
    }

    public enum BAND {
        FM1(0, 0),
        FM2(0, 1),
        FM3(0, 2),
        FM4(0, 3),
        AM1(1, 0),
        AM2(1, 1),
        AM3(1, 2),
        AM4(1, 3);

        final int band;
        final int index;

        @Override // java.lang.Enum
        public String toString() {
            Locale locale = Locale.US;
            Object[] objArr = new Object[2];
            objArr[0] = this.band == 0 ? "FM" : "AM";
            objArr[1] = Integer.valueOf(this.index + 1);
            return String.format(locale, "%s-%d", objArr);
        }

        static BAND valueOf(int value) {
            int b = value >> 8;
            int i = value & McuConstant.K_SWCSETTING;
            Locale locale = Locale.US;
            Object[] objArr = new Object[2];
            objArr[0] = b == 0 ? "FM" : "AM";
            objArr[1] = Integer.valueOf(i + 1);
            return valueOf(String.format(locale, "%s%d", objArr));
        }

        int getValue() {
            return (this.band << 8) + this.index;
        }

        BAND(int band, int index) {
            this.band = band;
            this.index = index;
        }
    }

    private RadioPlayer() {
    }

    public static RadioPlayer getRadioPlayer() {
        if (sRadioPlayer == null) {
            sRadioPlayer = new RadioPlayer();
        }
        return sRadioPlayer;
    }

    public int[] getPreset(BAND uiband) {
        int[] preset = new int[0];
        try {
            preset = this.mService.getPreset(uiband.getValue());
            Log.i(TAG, uiband.toString() + " getPreset:" + Arrays.toString(preset));
            return preset;
        } catch (RemoteException | NullPointerException e) {
            return preset;
        }
    }

    public void setOnEventListener(OnEventListener listener) {
        this.mOnEventListener = listener;
        try {
            if (listener != null) {
                this.mService.setcallback(this.mToken);
            } else {
                this.mService.removecallback(this.mToken);
            }
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public static boolean hasRadiomodule() {
        return false;
    }

    private void scan(int type, BAND band, int count) {
        try {
            this.mService.scan(type, band.getValue(), count);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public int getFreq() {
        try {
            int freq = this.mService.getFreq();
            return freq;
        } catch (RemoteException | NullPointerException e) {
            return -1;
        }
    }

    public int getRegion() {
        try {
            return this.mService.getRegion();
        } catch (RemoteException | NullPointerException e) {
            return 0;
        }
    }

    public RadioInfo getRadioInfo() {
        try {
            return this.mService.getRadioInfo();
        } catch (RemoteException | NullPointerException e) {
            return null;
        }
    }

    public void setUibandIndexFreq(BAND uiband, int index, int freq) {
        try {
            this.mService.setUibandIndexFreq(uiband.getValue(), index, freq);
        } catch (RemoteException e) {
            throw new RuntimeException("set frequency " + freq + " out of range.");
        } catch (NullPointerException e2) {
        }
    }

    public BAND getUiband() {
        BAND band = BAND.FM1;
        try {
            BAND band2 = BAND.valueOf(this.mService.getUiband());
            return band2;
        } catch (RemoteException | NullPointerException e) {
            return band;
        }
    }

    public int next() {
        try {
            int freq = this.mService.next();
            return freq;
        } catch (RemoteException | NullPointerException e) {
            return 0;
        }
    }

    public int prev() {
        try {
            int freq = this.mService.prev();
            return freq;
        } catch (RemoteException | NullPointerException e) {
            return 0;
        }
    }

    public int stepUp() {
        try {
            int freq = this.mService.stepUp();
            return freq;
        } catch (RemoteException | NullPointerException e) {
            return -1;
        }
    }

    public int stepDown() {
        try {
            int freq = this.mService.stepDown();
            return freq;
        } catch (RemoteException | NullPointerException e) {
            return -1;
        }
    }

    public void seekDown(BAND uiband) {
        scan(4, uiband, 0);
    }

    public void seekUp(BAND uiband) {
        scan(3, uiband, 0);
    }

    public void presetScan(int seconds) {
        try {
            this.mService.presetScan(seconds);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void autoScan(BAND uiband, int count) {
        scan(1, uiband, count);
    }

    public void seekPlay(BAND uiband) {
        scan(2, uiband, 0);
    }

    public void stereo(boolean enable) {
        try {
            this.mService.stereo(enable);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setMute(boolean mute) {
        try {
            this.mService.setMute(mute);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void local(boolean enable) {
        try {
            this.mService.local(enable);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setPreset(BAND band, int[] preset) {
        if (preset == null || preset.length == 0) {
            return;
        }
        try {
            this.mService.setPreset(band.getValue(), preset);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setPTY(int type) {
        try {
            this.mService.setPTY(type);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setTA(boolean enable) {
        try {
            this.mService.setTA(enable);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setAF(boolean enable) {
        try {
            this.mService.setAF(enable);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setREG(boolean enable) {
        try {
            this.mService.setREG(enable);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    public void setEON(boolean enable) {
        try {
            this.mService.setEON(enable);
        } catch (RemoteException | NullPointerException e) {
        }
    }

    protected void finalize() {
        try {
            this.mService.removecallback(this.mToken);
        } catch (RemoteException | NullPointerException e) {
        }
        try {
            super.finalize();
        } catch (Throwable th) {
        }
    }
}
