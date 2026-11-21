package com.android.car.settings;

import android.Configures.HConfig;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.link.CarletterInterface;
import android.net.Uri;
import android.os.Build;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.system.Os;
import android.util.Log;
import android.util.Slog;
import com.android.car.CarService;
import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class CarSettingsService extends ICarSettings.Stub {
    private static final String DESCRIPTOR = "com.android.car.settings.ICarSettings";
    private static final int MSG_CREATE_FAN_CONFIG_FILE = 1;
    private static final int MSG_NONE = 0;
    private static final int MSG_UPDATE_FAN_CONFIG_FILE = 2;
    public static final String SETTINGS_SERVICE = "car_settings";
    private static final String TAG = "CarSettingsService";
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private SettingsHandler mSettingsHandler;
    private SettingsThread mSettingsThread;
    protected static final boolean LOGD = !"user".equals(Build.TYPE);
    private SettingsObserver mSettingsObserver = null;
    private final File mFanConfigFile = new File("/tmp/system/fan_parameter.kv");
    private final ConcurrentHashMap<String, Integer> mFanParameterMap = new ConcurrentHashMap<>();
    private final CarSettingsImpl mCarSettingsImpl = new CarSettingsImpl();
    private final int SET_CALL_TRANSACTION = HConfig.MBoardCode.FEIYIN_DSP_BOARD;
    public static final Uri FAN_CONSTANT_OPENING_URI = Settings.Global.getUriFor(HConfig.fan_constant_opening);
    public static final Uri FAN_CONSTANT_CLOSING_URI = Settings.Global.getUriFor(HConfig.fan_constant_closing);
    public static final Uri FAN_TEMPER_THRESHOLD_URI = Settings.Global.getUriFor(HConfig.fan_temper_threshold);
    public static final Uri FAN_VOLUME_THRESHOLD_URI = Settings.Global.getUriFor(HConfig.fan_volume_threshold);

    public static final class Lifecycle extends CarService {
        private CarSettingsService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new CarSettingsService(context);
        }

        @Override // com.android.car.CarService
        public void onStart() {
            publishBinderService(CarSettingsService.SETTINGS_SERVICE, this.mService);
        }

        @Override // com.android.car.CarService
        public void onBootPhase(int phase) {
            if (phase == 550) {
                this.mService.systemReady();
            }
        }
    }

    public CarSettingsService(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        createSettingsThread();
    }

    public void systemReady() {
        Slog.i(TAG, "systemReady.");
        registerSettingsObserver();
        this.mSettingsHandler.sendEmptyMessageDelayed(1, 0L);
    }

    public void release() {
    }

    @Override // com.android.car.settings.ICarSettings.Stub, android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 101) {
            data.enforceInterface(DESCRIPTOR);
            String key = data.readString();
            String value = data.readString();
            Slog.i(TAG, "SET_CALL_TRANSACTION: " + key + "=" + value);
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    private class SettingsThread extends Thread {
        SettingsThread() {
            super(CarSettingsService.TAG);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Looper.prepare();
            synchronized (CarSettingsService.this) {
                CarSettingsService.this.mSettingsHandler = new SettingsHandler();
                CarSettingsService.this.notify();
            }
            Looper.loop();
        }
    }

    private class SettingsHandler extends Handler {
        private SettingsHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CarSettingsService.this.onMsgCreateFanConfigFile();
                    break;
                case 2:
                    CarSettingsService.this.onMsgUpdateFanConfigFile((String) msg.obj);
                    break;
            }
        }
    }

    private void createSettingsThread() {
        this.mSettingsThread = new SettingsThread();
        this.mSettingsThread.start();
        waitForSettingsHandlerCreation();
    }

    private void waitForSettingsHandlerCreation() {
        synchronized (this) {
            while (this.mSettingsHandler == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "Interrupted while waiting on settings handler.");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMsgCreateFanConfigFile() {
        StringBuilder builder = new StringBuilder(CarletterInterface.KEYCODE_KNOB_LEFT);
        int constantOpening = Settings.Global.getInt(this.mContentResolver, HConfig.fan_constant_opening, 1);
        this.mFanParameterMap.put("constant_opening", Integer.valueOf(constantOpening));
        builder.append("constant_opening");
        builder.append("=");
        builder.append(constantOpening);
        builder.append("\n");
        int constantClosing = Settings.Global.getInt(this.mContentResolver, HConfig.fan_constant_closing, 0);
        this.mFanParameterMap.put("constant_closing", Integer.valueOf(constantClosing));
        builder.append("constant_closing");
        builder.append("=");
        builder.append(constantClosing);
        builder.append("\n");
        int temperThreshold = Settings.Global.getInt(this.mContentResolver, HConfig.fan_temper_threshold, 0);
        this.mFanParameterMap.put("temper_threshold", Integer.valueOf(temperThreshold));
        builder.append("temper_threshold");
        builder.append("=");
        builder.append(temperThreshold);
        builder.append("\n");
        int volumeThreshold = Settings.Global.getInt(this.mContentResolver, HConfig.fan_volume_threshold, 0);
        this.mFanParameterMap.put("volume_threshold", Integer.valueOf(volumeThreshold));
        builder.append("volume_threshold");
        builder.append("=");
        builder.append(volumeThreshold);
        builder.append("\n");
        try {
            FileUtils.stringToFile(this.mFanConfigFile, builder.toString());
            Os.chmod(this.mFanConfigFile.getAbsolutePath(), 420);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:23:0x0042  */
    public void onMsgUpdateFanConfigFile(String configItem) {
        switch (configItem) {
            case "fan_constant_opening":
                int constantOpening = Settings.Global.getInt(this.mContentResolver, configItem, 0);
                this.mFanParameterMap.put("constant_opening", Integer.valueOf(constantOpening));
                break;
            case "fan_constant_closing":
                int constantClosing = Settings.Global.getInt(this.mContentResolver, configItem, 0);
                this.mFanParameterMap.put("constant_closing", Integer.valueOf(constantClosing));
                break;
            case "fan_temper_threshold":
                int temperThreshold = Settings.Global.getInt(this.mContentResolver, configItem, 0);
                this.mFanParameterMap.put("temper_threshold", Integer.valueOf(temperThreshold));
                break;
            case "fan_volume_threshold":
                int volumeThreshold = Settings.Global.getInt(this.mContentResolver, configItem, 0);
                this.mFanParameterMap.put("volume_threshold", Integer.valueOf(volumeThreshold));
                break;
            default:
                return;
        }
        StringBuilder builder = new StringBuilder(128);
        for (String key : this.mFanParameterMap.keySet()) {
            Integer value = this.mFanParameterMap.get(key);
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append("\n");
        }
        try {
            FileUtils.stringToFile(this.mFanConfigFile, builder.toString());
            Os.chmod(this.mFanConfigFile.getAbsolutePath(), 420);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.car.settings.ICarSettings
    public void test(int i, String s) {
    }

    @Override // com.android.car.settings.ICarSettings
    public String getHardwareInfo() {
        if (this.mCarSettingsImpl != null) {
            return this.mCarSettingsImpl.getHardwareInfo(this.mContext);
        }
        return "NULL";
    }

    @Override // com.android.car.settings.ICarSettings
    public String getSystemVersion() {
        if (this.mCarSettingsImpl != null) {
            return this.mCarSettingsImpl.getSystemVersion(this.mContext);
        }
        return "NULL";
    }

    @Override // com.android.car.settings.ICarSettings
    public String getMcuVersion() {
        if (this.mCarSettingsImpl != null) {
            return this.mCarSettingsImpl.getMcuVersion(this.mContext);
        }
        return "NULL";
    }

    private void registerSettingsObserver() {
        if (this.mSettingsObserver != null) {
            return;
        }
        this.mSettingsObserver = new SettingsObserver(this);
        this.mContentResolver.registerContentObserver(FAN_CONSTANT_OPENING_URI, false, this.mSettingsObserver);
        this.mContentResolver.registerContentObserver(FAN_CONSTANT_CLOSING_URI, false, this.mSettingsObserver);
        this.mContentResolver.registerContentObserver(FAN_TEMPER_THRESHOLD_URI, false, this.mSettingsObserver);
        this.mContentResolver.registerContentObserver(FAN_VOLUME_THRESHOLD_URI, false, this.mSettingsObserver);
    }

    private void unregisterSettingsObserver() {
        if (this.mSettingsObserver == null) {
            return;
        }
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
        this.mSettingsObserver = null;
    }

    private static final class SettingsObserver extends ContentObserver {
        private Reference<CarSettingsService> mRefOwner;

        public SettingsObserver(CarSettingsService service) {
            super(null);
            this.mRefOwner = null;
            this.mRefOwner = new WeakReference(service);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            CarSettingsService s = this.mRefOwner.get();
            if (s == null || uri == null) {
                return;
            }
            String key = uri.toString().replace("content://settings/global/", "");
            switch (key) {
                case "fan_constant_opening":
                case "fan_constant_closing":
                case "fan_temper_threshold":
                case "fan_volume_threshold":
                    if (!s.mSettingsHandler.hasMessages(2, key)) {
                        Message msg = s.mSettingsHandler.obtainMessage(2, key);
                        s.mSettingsHandler.sendMessageDelayed(msg, 200L);
                        break;
                    }
                    break;
            }
        }
    }
}
