package android.radio;

import android.Configures.HConfig;
import android.carsource.McuConstant;
import android.carsource.McuService;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sourceservice.ExtAudioMuxer;
import android.sourceservice.SourceService;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class RadioService extends IRadioPlayer.Stub {
    private static final int EVENT_CLEAR_PRESET = 5;
    private static final int EVENT_LOAD_PRESET = 2;
    private static final int EVENT_PRESET_PLAY = 1;
    private static final int EVENT_SET_MCUFREQ = 6;
    private static final int EVENT_SET_REGION = 3;
    private static final int EVENT_TA_MESSAGE = 4;
    private final Handler H;
    Utility.RadioParameters RADIOPARA;
    private boolean mAFenable;
    private final ArrayList<int[]> mAmPreset;
    private final Context mContext;
    private final ArrayList<int[]> mFmPreset;
    private int mRegion;
    private int mScanCount;
    private final SettingsObserver mSettingsObserver;
    private final HandlerThread mThread;
    private static RadioService sInstance;
    private static String TAG = "RadioService";
    private int DEBUG = 0;
    private boolean mTaMessageActive = false;
    private boolean mTAenable = false;
    private boolean mStereoEn = true;
    private boolean mIsMuted = false;
    private RadioPlayer.BAND mCurrentBand = RadioPlayer.BAND.FM1;
    private final RadioInfo mRadioInfo = new RadioInfo();
    private final AtomicInteger mTag = new AtomicInteger();
    private final HashMap<Integer, Integer> mScannedList = new HashMap<>();
    private final int mFMbandCounts = 4;
    private final int mAMbandCounts = 4;
    final String DESCRIPTOR = "android.sourceservice.ISourceInfo";
    private final int RADIO_CALL_TRANSACTION = 301;
    private final ArrayList<IRadioCallback> mCallBacks = new ArrayList<>();

    private native int nativeInitRadio(int i);

    private native int nativeScan(int i);

    private native int nativeSetAF(int i);

    private native int nativeSetEON(int i);

    private native int nativeSetFreq(int i);

    private native int nativeSetLocal(int i);

    private native int nativeSetPTY(int i);

    public static native int nativeSetPower(int i);

    private native int nativeSetREG(int i);

    private native int nativeSetStereo(int i);

    private native int nativeSetTA(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativesetRegion(int i);

    public static RadioService getInstance() {
        return sInstance;
    }

    public RadioService(Context ctx) {
        this.mContext = ctx;
        sInstance = this;
        this.mSettingsObserver = new SettingsObserver();
        ContentResolver cr = this.mContext.getContentResolver();
        this.mRegion = Settings.System.getInt(cr, HConfig.radio_region, HConfig.def_radio_region);
        SystemProperties.set("hw.sys.radio.region", Integer.toString(this.mRegion));
        this.RADIOPARA = new Utility.RadioParameters();
        this.mFmPreset = new ArrayList<>(4);
        this.mAmPreset = new ArrayList<>(4);
        ExtAudioMuxer.setInputPathMute(0, true, 16);
        this.mThread = new HandlerThread("fmradio");
        this.mThread.start();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.radio.RadioService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    RadioService.this.H.sendEmptyMessageDelayed(3, 100L);
                    RadioService.this.H.sendEmptyMessageDelayed(3, 200L);
                    RadioService.this.H.sendEmptyMessageDelayed(6, 300L);
                }
            }
        }, filter);
        this.H = new Handler(this.mThread.getLooper()) { // from class: android.radio.RadioService.2
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        RadioService.this.onPresetPlay(msg);
                        break;
                    case 2:
                        RadioService.this.loadAllPresets();
                        break;
                    case 3:
                        int ret = RadioService.this.setRadioRegion(RadioService.this.mRegion);
                        if (ret < 0) {
                            sendEmptyMessageDelayed(3, 1000L);
                        }
                        break;
                    case 4:
                        Intent i = new Intent(RadioPlayer.ACTION_TA_MESSAGE);
                        i.setClassName(SourceService.ACTIVE_RADIO, "com.hcn.autoradio.RadioMediaButtonReceiver");
                        i.setFlags(268435488);
                        i.putExtra(RadioPlayer.EXTRA_KEY_MESSAGE, msg.arg1);
                        SystemProperties.set("sys.radio.rds", Integer.toString(msg.arg1));
                        RadioService.this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
                        break;
                    case 5:
                        RadioService.this.clearPreset();
                        break;
                    case 6:
                        int freq = RadioService.this.mRadioInfo.mFreq;
                        int tag = RadioService.this.mTag.get();
                        McuService mcu = McuService.getMcuService();
                        byte[] data = {(byte) (freq & McuConstant.K_SWCSETTING), (byte) ((freq >> 8) & McuConstant.K_SWCSETTING), (byte) ((freq >> 16) & McuConstant.K_SWCSETTING), (byte) tag};
                        mcu.requestRadioMsg((byte) 3, data);
                        break;
                }
            }
        };
        this.H.sendEmptyMessageDelayed(2, 100L);
        this.H.sendEmptyMessageDelayed(3, 1000L);
        int extmod = nativeInitRadio(this.mRegion);
        Log.i(TAG, "external radio module status " + extmod);
    }

    @Override // android.radio.IRadioPlayer
    public void scan(int type, int uiband, int count) {
        this.mIsMuted = true;
        this.H.removeMessages(1);
        ExtAudioMuxer.setInputPathMute(0, true, 2);
        McuService.sendCmdCarStatus("mute=10001000");
        Log.i(TAG, "Start scan type:" + type);
        SystemClock.sleep(100L);
        int tag = nativeScan(type);
        McuService mcu = McuService.getMcuService();
        mcu.requestRadioMsg((byte) 4, new byte[]{(byte) type, (byte) tag});
        this.mRadioInfo.mScanType = type;
        this.mTag.set(tag);
        this.mScanCount = count;
        Log.i(TAG, "Begin to scan, max count:" + this.mScanCount);
        this.mScannedList.clear();
    }

    @Override // android.radio.IRadioPlayer
    public void presetScan(int seconds) {
        this.H.removeMessages(1);
        int index = this.mCurrentBand.index;
        int band = this.mCurrentBand.band;
        int[] preset = (band == RadioPlayer.BAND.FM1.band ? this.mFmPreset : this.mAmPreset).get(index);
        Log.i(TAG, "Start preset play:" + Arrays.toString(preset));
        this.mRadioInfo.mScanType = 5;
        Message msg = Message.obtain(this.H, 1, 0, seconds * 1000);
        this.H.sendMessageDelayed(msg, 0L);
    }

    public void stopScan() {
        Log.i(TAG, "Stop scan");
        this.H.removeMessages(1);
        int tag = nativeSetFreq(this.mRadioInfo.mFreq);
        this.mTag.set(tag);
        if (this.mRadioInfo.mScanType == 5) {
            callManager(8, "");
        }
        this.mRadioInfo.mScanType = 0;
        onStateChange();
    }

    @Override // android.radio.IRadioPlayer
    public int getUiband() {
        return this.mCurrentBand.getValue();
    }

    private boolean IsAMrange(int freq) {
        return freq >= this.RADIOPARA.AmMin && freq <= this.RADIOPARA.AmMax;
    }

    private boolean IsFMrange(int freq) {
        return freq >= this.RADIOPARA.FmMin && freq <= this.RADIOPARA.FmMax;
    }

    @Override // android.radio.IRadioPlayer
    public void setUibandIndexFreq(int uiband, int index, int freq) {
        if (uiband >= 0) {
            this.mCurrentBand = RadioPlayer.BAND.valueOf(uiband);
        }
        if (this.mCurrentBand.band == RadioPlayer.BAND.FM1.band && !IsFMrange(freq)) {
            throw new IllegalArgumentException("freq out of FM range:" + freq);
        }
        if (this.mCurrentBand.band == RadioPlayer.BAND.AM1.band && !IsAMrange(freq)) {
            throw new IllegalArgumentException("freq out of AM range:" + freq);
        }
        this.mRadioInfo.mIndexof = index;
        this.mRadioInfo.mScanType = 0;
        this.mRadioInfo.mUiBand = this.mCurrentBand.toString();
        Log.i(TAG, "setUiband :" + this.mRadioInfo.mUiBand + ", index " + index + ", freq " + freq);
        this.H.removeMessages(1);
        if (this.mRadioInfo.mFreq != freq) {
            sendFreq(freq, index);
        }
        onStateChange();
    }

    @Override // android.radio.IRadioPlayer
    public int stepUp() {
        int freq;
        this.mRadioInfo.mScanType = 0;
        this.H.removeMessages(1);
        int freq2 = this.mRadioInfo.mFreq;
        if (this.mCurrentBand.band == RadioPlayer.BAND.FM1.band) {
            freq = freq2 + this.RADIOPARA.FmStep;
            if (freq > this.RADIOPARA.FmMax) {
                freq = this.RADIOPARA.FmMin;
            }
        } else {
            freq = freq2 + this.RADIOPARA.AmStep;
            if (freq > this.RADIOPARA.AmMax) {
                freq = this.RADIOPARA.AmMin;
            }
        }
        sendFreq(freq, -1);
        onStateChange();
        return freq;
    }

    @Override // android.radio.IRadioPlayer
    public int stepDown() {
        int freq;
        this.mRadioInfo.mScanType = 0;
        this.H.removeMessages(1);
        int freq2 = this.mRadioInfo.mFreq;
        if (this.mCurrentBand.band == RadioPlayer.BAND.FM1.band) {
            freq = freq2 - this.RADIOPARA.FmStep;
            if (freq < this.RADIOPARA.FmMin) {
                freq = this.RADIOPARA.FmMax;
            }
        } else {
            freq = freq2 - this.RADIOPARA.AmStep;
            if (freq < this.RADIOPARA.AmMin) {
                freq = this.RADIOPARA.AmMax;
            }
        }
        sendFreq(freq, -1);
        onStateChange();
        return freq;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPresetPlay(Message msg) {
        int index = this.mCurrentBand.index;
        int band = this.mCurrentBand.band;
        int[] preset = (band == RadioPlayer.BAND.FM1.band ? this.mFmPreset : this.mAmPreset).get(index);
        int presetOf = msg.arg1;
        Log.i(TAG, "preset play index of " + presetOf);
        if (preset.length > presetOf) {
            int freq = preset[presetOf];
            sendFreq(freq, presetOf);
            int delay = msg.arg2;
            Message xmsg = Message.obtain(this.H, 1, presetOf + 1, delay);
            this.H.sendMessageDelayed(xmsg, delay);
        } else if (preset.length == presetOf) {
            int freq2 = band == RadioPlayer.BAND.FM1.band ? this.RADIOPARA.FmMin : this.RADIOPARA.AmMin;
            if (preset.length > 0) {
                freq2 = preset[0];
            }
            sendFreq(freq2, 0);
            Message xmsg2 = Message.obtain(this.H, 1, presetOf + 1, 600);
            this.H.sendMessageDelayed(xmsg2, 600L);
        } else {
            this.mRadioInfo.mScanType = 0;
            callManager(8, "");
        }
        onStateChange();
    }

    private int preset(RadioPlayer.BAND uiband, int direction) {
        int[] preset;
        int freq;
        int band = uiband.band;
        int index = uiband.index;
        if (band == RadioPlayer.BAND.FM1.band) {
            preset = this.mFmPreset.get(index);
            freq = this.RADIOPARA.FmMin;
        } else {
            preset = this.mAmPreset.get(index);
            freq = this.RADIOPARA.AmMin;
        }
        int N = preset.length;
        if (N > 0) {
            int idx = this.mRadioInfo.mIndexof + direction;
            if (idx < 0) {
                idx = N - 1;
            }
            int idx2 = idx % N;
            int freq2 = preset[idx2];
            sendFreq(freq2, idx2);
            return freq2;
        }
        return freq;
    }

    @Override // android.radio.IRadioPlayer
    public int next() {
        int freq = preset(this.mCurrentBand, 1);
        onStateChange();
        return freq;
    }

    @Override // android.radio.IRadioPlayer
    public int prev() {
        int freq = preset(this.mCurrentBand, -1);
        onStateChange();
        return freq;
    }

    private void sendFreq(int freq, int index) {
        ExtAudioMuxer.setInputPathMute(0, true, 1);
        SystemClock.sleep(120L);
        int delay = IsFMrange(freq) != IsFMrange(this.mRadioInfo.mFreq) ? 600 : 100;
        Log.d(TAG, String.format("set freq %d, delay %d ms to unmute.", Integer.valueOf(freq), Integer.valueOf(delay)));
        int tag = nativeSetFreq(freq);
        McuService mcu = McuService.getMcuService();
        byte[] data = {(byte) (freq & McuConstant.K_SWCSETTING), (byte) ((freq >> 8) & McuConstant.K_SWCSETTING), (byte) ((freq >> 16) & McuConstant.K_SWCSETTING), (byte) tag};
        mcu.requestRadioMsg((byte) 3, data);
        this.mRadioInfo.mIndexof = index;
        this.mRadioInfo.mFreq = freq;
        this.mTag.set(tag);
        SystemClock.sleep(delay);
        ExtAudioMuxer.setInputPathMute(0, false, 1);
    }

    @Override // android.radio.IRadioPlayer
    public int getFreq() {
        return this.mRadioInfo.mFreq;
    }

    @Override // android.radio.IRadioPlayer
    public int getRegion() {
        return this.mRegion;
    }

    @Override // android.radio.IRadioPlayer
    public RadioInfo getRadioInfo() {
        return this.mRadioInfo;
    }

    @Override // android.radio.IRadioPlayer
    public void stereo(boolean z) {
        int iNativeSetStereo = nativeSetStereo(z ? 1 : 0);
        McuService.getMcuService().requestRadioMsg((byte) 17, new byte[]{z ? (byte) 1 : (byte) 0, (byte) iNativeSetStereo});
        this.mTag.set(iNativeSetStereo);
    }

    @Override // android.radio.IRadioPlayer
    public void local(boolean z) {
        int iNativeSetLocal = nativeSetLocal(z ? 1 : 0);
        McuService.getMcuService().requestRadioMsg((byte) 10, new byte[]{z ? (byte) 1 : (byte) 0, (byte) iNativeSetLocal});
        this.mTag.set(iNativeSetLocal);
    }

    @Override // android.radio.IRadioPlayer
    public void setMute(boolean mute) {
        ExtAudioMuxer.setInputPathMute(0, mute, 4);
    }

    @Override // android.radio.IRadioPlayer
    public void setPTY(int type) {
        int tag = nativeSetPTY(type);
        McuService mcu = McuService.getMcuService();
        mcu.requestRadioMsg((byte) 11, new byte[]{(byte) type, (byte) tag});
        this.mRadioInfo.mScanType = 6;
        this.mTag.set(tag);
    }

    public void setTA() {
        setTA(!this.mTAenable);
    }

    @Override // android.radio.IRadioPlayer
    public void setTA(boolean z) {
        this.mTAenable = z;
        Log.i(TAG, "setTA enable " + z);
        int iNativeSetTA = nativeSetTA(z ? 1 : 0);
        McuService.getMcuService().requestRadioMsg((byte) 13, new byte[]{z ? (byte) 1 : (byte) 0, (byte) iNativeSetTA});
        this.mTag.set(iNativeSetTA);
        if (!z) {
            this.H.obtainMessage(4, 0, 0).sendToTarget();
            onTaBroadcastActive(false);
        } else if (this.mTaMessageActive) {
            this.H.obtainMessage(4, 1, 0).sendToTarget();
            onTaBroadcastActive(true);
        }
    }

    @Override // android.radio.IRadioPlayer
    public void setAF(boolean z) {
        this.mAFenable = z;
        int iNativeSetAF = nativeSetAF(z ? 1 : 0);
        McuService.getMcuService().requestRadioMsg((byte) 12, new byte[]{z ? (byte) 1 : (byte) 0, (byte) iNativeSetAF});
        this.mTag.set(iNativeSetAF);
    }

    @Override // android.radio.IRadioPlayer
    public void setREG(boolean z) {
        this.mTag.set(nativeSetREG(z ? 1 : 0));
    }

    @Override // android.radio.IRadioPlayer
    public void setEON(boolean z) {
        this.mTag.set(nativeSetEON(z ? 1 : 0));
    }

    private String arraytoString(int[] a) {
        StringBuilder b = new StringBuilder();
        int N = a.length - 1;
        if (N < 0) {
            return "";
        }
        int i = 0;
        while (true) {
            b.append(a[i]);
            if (i == N) {
                return b.toString();
            }
            b.append(",");
            i++;
        }
    }

    private int[] stringToArrays(String str) {
        if (str == null || str.length() == 0) {
            return new int[0];
        }
        String[] v = str.split(",");
        int N = v.length;
        int[] ret = new int[N];
        for (int i = 0; i < N; i++) {
            ret[i] = Integer.parseInt(v[i].trim());
        }
        return ret;
    }

    @Override // android.radio.IRadioPlayer
    public int[] getPreset(int uiband) {
        RadioPlayer.BAND band = RadioPlayer.BAND.valueOf(uiband);
        int index = band.index;
        if (band.band == RadioPlayer.BAND.FM1.band) {
            return this.mFmPreset.get(index);
        }
        return this.mAmPreset.get(index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadAllPresets() {
        ContentResolver cr = this.mContext.getContentResolver();
        for (int i = 0; i < 4; i++) {
            RadioPlayer.BAND band = RadioPlayer.BAND.valueOf(RadioPlayer.BAND.FM1.getValue() + i);
            String setTag = "fmradio_" + band.toString();
            String preset = Settings.System.getString(cr, setTag);
            Log.i(TAG, band.toString() + " getPreset:" + preset);
            this.mFmPreset.add(stringToArrays(preset));
        }
        for (int i2 = 0; i2 < 4; i2++) {
            RadioPlayer.BAND band2 = RadioPlayer.BAND.valueOf(RadioPlayer.BAND.AM1.getValue() + i2);
            String setTag2 = "fmradio_" + band2.toString();
            String preset2 = Settings.System.getString(cr, setTag2);
            Log.i(TAG, band2.toString() + " getPreset:" + preset2);
            this.mAmPreset.add(stringToArrays(preset2));
        }
    }

    @Override // android.radio.IRadioPlayer
    public void setPreset(int uiband, int[] preset) {
        long origId = Binder.clearCallingIdentity();
        RadioPlayer.BAND band = RadioPlayer.BAND.valueOf(uiband);
        String setTag = "fmradio_" + band.toString();
        String str = arraytoString(preset);
        ContentResolver cr = this.mContext.getContentResolver();
        Settings.System.putString(cr, setTag, str);
        if (band.band == RadioPlayer.BAND.FM1.band) {
            this.mFmPreset.set(band.index, preset);
        } else {
            this.mAmPreset.set(band.index, preset);
        }
        callManager(7, band.toString() + ":" + str);
        Binder.restoreCallingIdentity(origId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPreset() {
        int[] preset = new int[0];
        for (int i = 0; i < 4; i++) {
            setPreset(RadioPlayer.BAND.FM1.getValue() + i, preset);
        }
        for (int i2 = 0; i2 < 4; i2++) {
            setPreset(RadioPlayer.BAND.AM1.getValue() + i2, preset);
        }
    }

    public synchronized void onMessage(McuService.McuPacket pkt) {
        if (this.DEBUG > 0) {
            Log.v(TAG, pkt.toString());
        }
        int N = pkt.paramLen();
        byte bSubCmd = pkt.subCmd();
        int i = 0;
        int i2 = 1;
        if (bSubCmd == 5) {
            byte flags = pkt.param(1);
            int scantype = pkt.param(0) & 15;
            this.mStereoEn = (flags & 4) != 0;
            boolean local = (flags & McuConstant.GID_XM) != 0;
            boolean stereo = (flags & 64) != 0;
            boolean mute = (flags & 2) != 0;
            int freq = (pkt.param(2) & 255) + ((pkt.param(3) & 255) << 8) + ((pkt.param(4) & 255) << 16);
            boolean changed = false;
            if (this.mIsMuted != mute) {
                this.mIsMuted = mute;
                ExtAudioMuxer.setInputPathMute(0, mute, 2);
                McuService.sendCmdCarStatus(mute ? "mute=10001000" : "mute=10000000");
                Log.d(TAG, "fmradio set mute " + mute);
            }
            int signal = pkt.param(5) & 15;
            int tag = pkt.param(6) & 255;
            if (tag != this.mTag.get()) {
                Log.v(TAG, "Ignore message, reag tag " + tag + ", send tag " + this.mTag.get());
            } else {
                if (this.mRadioInfo.mFreq != freq) {
                    this.mRadioInfo.mFreq = freq;
                    changed = true;
                    Log.d(TAG, "fmradio freq set to " + freq + ", signal " + signal);
                }
                if (this.mRadioInfo.mIsStereo != stereo) {
                    this.mRadioInfo.mIsStereo = stereo;
                    changed = true;
                }
                if (this.mRadioInfo.mIsLocal != local) {
                    this.mRadioInfo.mIsLocal = local;
                    changed = true;
                }
                if (signal > 0 && scantype == 1) {
                    this.mScannedList.put(Integer.valueOf(this.mRadioInfo.mFreq), Integer.valueOf(signal));
                }
                if (scantype == 0 && this.mRadioInfo.mScanType == 1) {
                    int[] freqs = SortScannedResult(1, this.mScanCount);
                    Log.i(TAG, this.mCurrentBand.toString() + " Scan Freqs:" + Arrays.toString(freqs));
                    setPreset(this.mCurrentBand.getValue(), freqs);
                }
                if (scantype == 0 && (this.mRadioInfo.mScanType == 1 || this.mRadioInfo.mScanType == 3 || this.mRadioInfo.mScanType == 4)) {
                    callManager(2, Integer.toString(this.mRadioInfo.mScanType));
                }
                if (scantype == 0 && this.mRadioInfo.mScanType != 5) {
                    this.mRadioInfo.mScanType = 0;
                }
                if (scantype == 6) {
                    this.mRadioInfo.mScanType = scantype;
                }
                if (changed) {
                    onStateChange();
                }
            }
        } else {
            switch (bSubCmd) {
                case 33:
                    int state = (pkt.param(0) & McuConstant.K_SWCSETTING) | ((pkt.param(1) & 255) << 8) | ((pkt.param(2) & 255) << 16) | ((pkt.param(3) & 255) << 24);
                    boolean ta = (state & 2) > 0;
                    if (ta != this.mTaMessageActive) {
                        if (this.mTAenable) {
                            Handler handler = this.H;
                            if (!ta) {
                                i2 = 0;
                            }
                            handler.obtainMessage(4, i2, 0).sendToTarget();
                            onTaBroadcastActive(ta);
                        }
                        this.mTaMessageActive = ta;
                        Log.i(TAG, "Received TA active message, flag = 0x" + Integer.toHexString(state));
                    }
                    this.mRadioInfo.mRDSstate = Integer.toString(state);
                    callManager(4, Integer.toString(state));
                    break;
                case RadioPlayer.EVENT_PTY_TYPE /* 34 */:
                    int type = pkt.param(0) & 255;
                    this.mRadioInfo.mPTYtype = Integer.toString(type);
                    callManager(pkt.subCmd() & 255, Integer.toString(type));
                    break;
                default:
                    switch (bSubCmd) {
                        case RadioPlayer.EVENT_PS_MESSAGE /* 38 */:
                        case RadioPlayer.EVENT_RT_MESSAGE /* 41 */:
                            String str = "";
                            byte[] s = new byte[N - 1];
                            while (true) {
                                int i3 = i;
                                if (i3 < N - 1) {
                                    s[i3] = pkt.param(i3) == 0 ? McuConstant.GID_XM : pkt.param(i3);
                                    i = i3 + 1;
                                } else {
                                    try {
                                        str = new String(s, "UTF-8").trim();
                                    } catch (UnsupportedEncodingException e) {
                                        Log.e(TAG, e.getMessage());
                                    }
                                    this.mRadioInfo.mRTtype = str;
                                    callManager(pkt.subCmd() & 255, str);
                                }
                                break;
                            }
                            break;
                        case 39:
                            int pi = (pkt.param(0) & 255) | ((pkt.param(1) & 255) << 8);
                            this.mRadioInfo.mPSname = Integer.toString(pi);
                            callManager(38, Integer.toString(pi));
                            break;
                        case 40:
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    }

    private void onTaBroadcastActive(boolean active) {
        IBinder service = ServiceManager.getService("sourceinfo");
        if (service != null) {
            Parcel _data = Parcel.obtain();
            try {
                try {
                    _data.writeInterfaceToken("android.sourceservice.ISourceInfo");
                    _data.writeBoolean(active);
                    service.transact(301, _data, null, 1);
                } catch (Exception e) {
                    Log.w(TAG, "sourceinfo binder failed");
                }
            } finally {
                _data.recycle();
            }
        }
    }

    private void onStateChange() {
        callManager(1, this.mRadioInfo);
    }

    private int[] SortScannedResult(int gate, int max) {
        ArrayList<Map.Entry<Integer, Integer>> infoIds = new ArrayList<>(this.mScannedList.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Integer>>() { // from class: android.radio.RadioService.3
            @Override // java.util.Comparator
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().intValue() - o1.getValue().intValue();
            }
        });
        while (infoIds.size() > max) {
            infoIds.remove(infoIds.size() - 1);
        }
        for (int i = infoIds.size(); i > 0; i--) {
            if (infoIds.get(i - 1).getValue().intValue() < gate) {
                infoIds.remove(i - 1);
            }
        }
        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Integer>>() { // from class: android.radio.RadioService.4
            @Override // java.util.Comparator
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getKey().intValue() - o2.getKey().intValue();
            }
        });
        int N = infoIds.size();
        int[] freqs = new int[N];
        for (int i2 = 0; i2 < N; i2++) {
            freqs[i2] = infoIds.get(i2).getKey().intValue();
        }
        return freqs;
    }

    @Override // android.radio.IRadioPlayer
    public void setcallback(IRadioCallback callback) {
        synchronized (this.mCallBacks) {
            if (!this.mCallBacks.contains(callback)) {
                this.mCallBacks.add(callback);
            }
        }
    }

    @Override // android.radio.IRadioPlayer
    public void removecallback(IRadioCallback callback) {
        synchronized (this.mCallBacks) {
            this.mCallBacks.remove(callback);
        }
    }

    private void callManager(int cmd, RadioInfo info) {
        synchronized (this.mCallBacks) {
            Iterator<IRadioCallback> iter = this.mCallBacks.iterator();
            while (iter.hasNext()) {
                IRadioCallback c = iter.next();
                try {
                    c.onEvent(cmd, info);
                } catch (RemoteException e) {
                    Log.w(TAG, "callManager " + c + ", lost");
                    iter.remove();
                }
            }
        }
    }

    private void callManager(int cmd, String info) {
        synchronized (this.mCallBacks) {
            Iterator<IRadioCallback> iter = this.mCallBacks.iterator();
            while (iter.hasNext()) {
                IRadioCallback c = iter.next();
                try {
                    c.onStringEvent(cmd, info);
                } catch (RemoteException e) {
                    Log.w(TAG, "callManager " + c + ", lost");
                    iter.remove();
                }
            }
        }
    }

    private class SettingsObserver extends ContentObserver {
        SettingsObserver() {
            super(new Handler());
            ContentResolver cr = RadioService.this.mContext.getContentResolver();
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.radio_region), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);
            ContentResolver cr = RadioService.this.mContext.getContentResolver();
            RadioService.this.mRegion = Settings.System.getInt(cr, HConfig.radio_region, HConfig.def_radio_region);
            SystemProperties.set("hw.sys.radio.region", Integer.toString(RadioService.this.mRegion));
            RadioService.this.RADIOPARA = new Utility.RadioParameters();
            RadioService.this.setRadioRegion(RadioService.this.mRegion);
            RadioService.this.nativesetRegion(RadioService.this.mRegion);
            RadioService.this.H.sendEmptyMessageDelayed(5, 10L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRadioRegion(int region) {
        Log.i(TAG, "set Radio Region to " + region);
        McuService mcu = McuService.getMcuService();
        return mcu.requestRadioMsg((byte) 1, new byte[]{(byte) region, (byte) this.mTag.get()});
    }

    @Override // android.os.Binder
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        String opt;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            pw.println("Permission Denial: can't dump fmradio from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        int opti = 0;
        while (opti < args.length && (opt = args[opti]) != null && opt.length() > 0 && opt.charAt(0) == '-') {
            opti++;
            if ("-f".equals(opt)) {
                if (opti < args.length) {
                    int freq = Integer.parseInt(args[opti]);
                    this.mRadioInfo.mScanType = 0;
                    sendFreq(freq, -1);
                    opti++;
                } else {
                    pw.println("Error: -f option requires debug level");
                    return;
                }
            } else if ("-s".equals(opt)) {
                pw.println("Begin to scan:FM");
                scan(1, this.mCurrentBand.getValue(), 18);
                return;
            } else if ("-d".equals(opt)) {
                this.DEBUG = Integer.parseInt(args[opti]);
            }
        }
        pw.println("radio Manager (dumpsys fmradio)");
        pw.println("Scan:" + this.mRadioInfo.mScanType);
        pw.println("Local:" + this.mRadioInfo.mIsLocal);
        pw.println("Freq:" + this.mRadioInfo.mFreq);
        pw.println("mUiBand:" + this.mRadioInfo.mUiBand);
        pw.println("en_stereo:" + this.mStereoEn);
        pw.println("Stereo:" + this.mRadioInfo.mIsStereo);
        pw.println("Region:" + this.mRegion);
        pw.println("Traffic:" + this.mTaMessageActive);
        pw.println("TA:" + this.mTAenable);
        pw.println("AF:" + this.mAFenable);
        pw.println("scan result:");
        for (Map.Entry<Integer, Integer> entry : this.mScannedList.entrySet()) {
            pw.println("   " + entry.getKey() + ", signal " + entry.getValue());
            entry.getKey();
            entry.getValue();
        }
    }

    private void native_reportEvent(byte[] data, int length) {
        byte[] d = new byte[length + 2];
        d[0] = (byte) (length + 2);
        System.arraycopy(data, 0, d, 2, length);
        McuService.McuPacket pkt = new McuService.McuPacket(d);
        onMessage(pkt);
    }
}
