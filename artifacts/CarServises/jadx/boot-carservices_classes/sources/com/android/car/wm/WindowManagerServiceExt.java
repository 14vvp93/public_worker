package com.android.car.wm;

import android.Configures.HConfig;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class WindowManagerServiceExt {
    private static final boolean DEBUG = false;
    private static final String DEBUG_OVERSCANNING_PROP_NAME = "ro.debug.overscanning";
    public static final String LCM_1280_480 = "LC1025HD020";
    public static final String LCM_1280_480_LX = "LX-10A6086-40P-H2";
    public static final String LCM_1280_480_LX_01 = "LX_10C6086_40P_H12";
    public static final String LCM_1280_480_LX_02 = "LX-10C6086-40P-H12";
    public static final String LCM_960_360 = "FY636DV01.3";
    public static final String LCM_960_360_HTF = "HTF062032SRGB-V1";
    public static final String LCM_960_360_HTF_SMP = "HTF063640SMP-V0";
    public static final String LCM_960_360_JBS = "JBS636DV01.3";
    public static final String LCM_INFO_FILE = "/proc/lcminfo";
    public static final String LCM_NONE = "NONE";
    private static final String TAG = "WindowManagerServiceExt";
    private static final boolean SUPPORT_OVERSCAN_ADJUST = getString("ro.sys.overscan_support").equals("1");
    private static WindowManagerServiceExt sInstance = null;
    private Context mContext = null;
    private Reference<Handler> mHandlerRef = null;
    private IWindowManager mInterface = null;
    private static String mLcmInfoName = LCM_NONE;

    public static WindowManagerServiceExt instance() {
        if (sInstance == null) {
            sInstance = new WindowManagerServiceExt();
        }
        return sInstance;
    }

    private static String getString(String property) {
        return SystemProperties.get(property, "null");
    }

    private WindowManagerServiceExt() {
    }

    public void init(IWindowManager wms, Context content, Handler handler) {
        if (!Objects.isNull(this.mContext)) {
            Slog.w(TAG, "repeated calls to initialization functions!");
            return;
        }
        this.mInterface = wms;
        this.mContext = content;
        this.mHandlerRef = new WeakReference(handler);
    }

    public void release() {
    }

    public void displayReady() {
        if (Objects.isNull(this.mContext)) {
            Slog.e(TAG, "The current module is not initialized.");
            return;
        }
        String lcmName = readLcmInfoName();
        if (TextUtils.isEmpty(lcmName)) {
            return;
        }
        ContentResolver cr = this.mContext.getContentResolver();
        switch (lcmName) {
            case "HTF062032SRGB-V1":
            case "JBS636DV01.3":
            case "HTF063640SMP-V0":
            case "FY636DV01.3":
                if (SUPPORT_OVERSCAN_ADJUST) {
                    try {
                        this.mInterface.setOverscan(0, 0, 60, 0, 60);
                        SystemProperties.set(DEBUG_OVERSCANNING_PROP_NAME, "1");
                    } catch (RemoteException e) {
                    }
                    break;
                } else {
                    Settings.Global.putString(cr, "display_size_forced", "960,3932520");
                    SystemProperties.set(DEBUG_OVERSCANNING_PROP_NAME, "0");
                    break;
                }
                break;
            case "LX-10A6086-40P-H2":
            case "LX_10C6086_40P_H12":
            case "LX-10C6086-40P-H12":
            case "LC1025HD020":
                if (SUPPORT_OVERSCAN_ADJUST) {
                    try {
                        this.mInterface.setOverscan(0, 0, 60, 0, 60);
                        SystemProperties.set(DEBUG_OVERSCANNING_PROP_NAME, "1");
                    } catch (RemoteException e2) {
                    }
                    break;
                } else {
                    Settings.Global.putString(cr, "display_size_forced", "1280,3932640");
                    SystemProperties.set(DEBUG_OVERSCANNING_PROP_NAME, "0");
                    break;
                }
                break;
            case "NONE":
            default:
                setDisplaySizeFromApd();
                break;
        }
        try {
            int[] overscan = getDisplayOverscan();
            if (overscan != null) {
                this.mInterface.setOverscan(0, overscan[0], overscan[1], overscan[2], overscan[3]);
            }
        } catch (Exception e3) {
            Slog.e(TAG, "The current overscan is not initialized.");
        }
    }

    public void systemReady() {
        if (Objects.isNull(this.mContext)) {
            Slog.e(TAG, "The current module is not initialized.");
        }
    }

    public static String readLcmInfoName() {
        if (!LCM_NONE.equals(mLcmInfoName)) {
            return mLcmInfoName;
        }
        mLcmInfoName = "lcm/hcn";
        File file = new File(LCM_INFO_FILE);
        if (!file.exists()) {
            Log.i(TAG, "/proc/lcminfo not find!!");
            return null;
        }
        FileReader input = null;
        BufferedReader in = null;
        try {
            try {
                try {
                    input = new FileReader(LCM_INFO_FILE);
                    in = new BufferedReader(input);
                    while (true) {
                        String line = in.readLine();
                        if (line == null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            input.close();
                            return null;
                        }
                        if (line.startsWith("name:")) {
                            String[] kv = line.split(":");
                            if (kv.length == 2) {
                                String lcmName = kv[1];
                                mLcmInfoName = lcmName;
                                Log.i(TAG, "Device LCM Name: " + lcmName);
                                try {
                                    in.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                try {
                                    input.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                return lcmName;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (input == null) {
                        throw th;
                    }
                    try {
                        input.close();
                        throw th;
                    } catch (IOException e5) {
                        e5.printStackTrace();
                        throw th;
                    }
                }
            } catch (Exception e6) {
                Log.i(TAG, "/proc/lcminfo e:" + e6.toString());
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                if (input != null) {
                    input.close();
                }
                return null;
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
    }

    public static float displayOffsetY() {
        String lcmName = readLcmInfoName();
        if (TextUtils.isEmpty(lcmName)) {
            return 0.0f;
        }
        switch (lcmName) {
            case "FY636DV01.3":
            case "HTF062032SRGB-V1":
            case "JBS636DV01.3":
            case "HTF063640SMP-V0":
            case "LC1025HD020":
            case "LX-10A6086-40P-H2":
            case "LX_10C6086_40P_H12":
            case "LX-10C6086-40P-H12":
                return SUPPORT_OVERSCAN_ADJUST ? 60.0f : 0.0f;
            case "NONE":
            default:
                return 0.0f;
        }
    }

    public static boolean addWindowFilter(CharSequence pkg, CharSequence winTag, int type) {
        CharSequence tag = winTag;
        if (tag == null || tag.length() <= 0) {
            tag = pkg;
        }
        if ((type != 2003 && type != 2002) || !pkg.toString().contains("com.txznet.txz") || HConfig.isOverseaVersion() || (tag.toString().contains("com.txznet.txz") && type == 2003)) {
            return false;
        }
        return true;
    }

    private void setDisplaySizeFromApd() {
        ContentResolver cr = this.mContext.getContentResolver();
        int savedutc = Settings.System.getInt(cr, "build_utc", -1);
        String size = Settings.Global.getString(cr, "display_size_forced");
        if (size == null || savedutc == -1) {
            String mDisplaySize = HConfig.queryValueFromApd("/apd/config_gl.cfg", "display_size_forced");
            if (!TextUtils.isEmpty(mDisplaySize)) {
                Settings.Global.putString(cr, "display_size_forced", mDisplaySize);
                Log.v(TAG, "successfully set display size." + mDisplaySize);
            }
        }
    }

    private int[] getOverScanArray(String overscan) {
        int[] array = {0, 0, 0, 0};
        try {
            String[] mOverScan = overscan.trim().split(",");
            if (mOverScan != null && mOverScan.length == 4) {
                Log.v(TAG, "successfully set overscan:" + overscan);
                array[0] = Integer.valueOf(mOverScan[0]).intValue();
                array[1] = Integer.valueOf(mOverScan[1]).intValue();
                array[2] = Integer.valueOf(mOverScan[2]).intValue();
                array[3] = Integer.valueOf(mOverScan[3]).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    private int[] getDisplayOverscan() {
        ContentResolver cr = this.mContext.getContentResolver();
        String mOverScan = Settings.Global.getString(cr, "display_overscan_forced");
        if (!TextUtils.isEmpty(mOverScan)) {
            return getOverScanArray(mOverScan);
        }
        String mOverScan2 = HConfig.queryValueFromApd("/apd/config_gl.cfg", "display_overscan_forced");
        if (!TextUtils.isEmpty(mOverScan2)) {
            return getOverScanArray(mOverScan2);
        }
        return null;
    }
}
