package com.android.car.settings;

import android.Configures.HConfig;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class CarSettingsImpl {
    private static final String TAG = "CarHardwareSettingsImpl";
    private static final HashMap<String, String> CACH_SOUND_EFFECT_CHIP_MAP = new HashMap<>();
    private static final HashMap<String, String> CACH_REVERSE_PARKING_CHIP_MAP = new HashMap<>();
    private String mHarewareInfo = null;
    private String mVersionInfo = null;
    private String mMcuVersionInfo = null;

    static {
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.DSP_CHIP_7601, "1");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.DSP_CHIP_7602, "2");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.ASP_CHIP_2348, "3");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.ASP_CHIP_3313, "4");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.ASP_CHIP_CSC37534, "5");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.DSP_CHIP_7604, "6");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.DSP_CHIP_7604c, "6");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.DSP_CHIP_32107, "7");
        CACH_SOUND_EFFECT_CHIP_MAP.put(HConfig.DSP_CHIP_FY7604, "8");
        CACH_REVERSE_PARKING_CHIP_MAP.put(HConfig.TP2804, "1");
        CACH_REVERSE_PARKING_CHIP_MAP.put(HConfig.TP9951, "2");
        CACH_REVERSE_PARKING_CHIP_MAP.put(HConfig.TP9950, "3");
    }

    public String getSystemVersion(Context context) {
        if (this.mVersionInfo == null) {
            String mVersion = SystemProperties.get(HConfig.PROP_SYSTEM_VERSION, "UNKNOWN");
            String mCustomer = SystemProperties.get(HConfig.PROP_CUSTOM_SYSTEM_VERSION_PREFFIX, "");
            String cpuABI = SystemProperties.get(HConfig.PROP_CPU_ARCH, "");
            if (!TextUtils.isEmpty(mCustomer)) {
                if (!HConfig.isOverseaVersion()) {
                    if (!mCustomer.toUpperCase().equals("MAINLAND") && !mCustomer.toUpperCase().equals("OV_BOARD")) {
                        mVersion = mVersion.replaceAll("AJ", mCustomer.toUpperCase());
                    }
                } else if ("Makhija".equalsIgnoreCase(mCustomer)) {
                    mVersion = mVersion.replaceAll("AJ", "LH");
                } else if ("Nakamichi".equalsIgnoreCase(mCustomer)) {
                    mVersion = mVersion.replaceAll("AJ", "Na");
                } else if (!"ov_board".equalsIgnoreCase(mCustomer)) {
                    mVersion = mVersion.replaceAll("AJ", mCustomer.toUpperCase());
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(mVersion);
            sb.append(cpuABI.startsWith("arm64") ? "_6" : "_3");
            this.mVersionInfo = sb.toString() + "_";
        }
        return this.mVersionInfo + getHardwareInfo(context);
    }

    public String getMcuVersion(Context context) {
        if (this.mMcuVersionInfo == null) {
            ContentResolver cr = context.getContentResolver();
            String mcuVersion = SystemProperties.get(HConfig.PROP_MCU_VERSION, "UNKNOWN");
            String mFakeMcuName = Settings.System.getString(cr, HConfig.SETTING_PROP_FAKE_MCU_VERSION);
            String mCustomer = SystemProperties.get(HConfig.PROP_CUSTOM_SYSTEM_VERSION_PREFFIX, "");
            int mMainBoardCode = Settings.System.getInt(cr, HConfig.mainboard_code, 0);
            int index = mcuVersion.indexOf(95);
            boolean mFakeMcuNameExist = !TextUtils.isEmpty(mFakeMcuName);
            if (index != -1) {
                if (mFakeMcuNameExist) {
                    mcuVersion = mFakeMcuName + mcuVersion.substring(index);
                } else {
                    if ("Makhija".equalsIgnoreCase(mCustomer)) {
                        mcuVersion = "LH8088-V" + mcuVersion.substring(index);
                    }
                    if (mMainBoardCode == 101) {
                        if (mcuVersion.equals("B11016-AJ" + mcuVersion.substring(index))) {
                            mcuVersion = "DWE1016-XK" + mcuVersion.substring(index);
                        } else {
                            mcuVersion = "DWE0709-XK" + mcuVersion.substring(index);
                        }
                    }
                }
            }
            this.mMcuVersionInfo = mcuVersion;
        }
        return this.mMcuVersionInfo;
    }

    public String getHardwareInfo(Context context) {
        if (this.mHarewareInfo == null) {
            StringBuffer temp = new StringBuffer();
            temp.append(getMemoryCode(context));
            temp.append(getEmmcCode());
            temp.append(getReverseParkingChipCode());
            temp.append(getSoundEffectChipCode());
            this.mHarewareInfo = temp.toString();
        }
        return this.mHarewareInfo + getOtherInfo();
    }

    private String getReverseParkingChipCode() {
        String ret = "0";
        File file = new File(HConfig.CUSTOM_VIDEO_CHIP_PATH);
        if (file.exists()) {
            try {
                String v = FileUtils.readTextFile(new File(HConfig.CUSTOM_VIDEO_CHIP_PATH), 0, null);
                if (v.contains(HConfig.TP2804)) {
                    ret = CACH_REVERSE_PARKING_CHIP_MAP.get(HConfig.TP2804);
                } else if (v.contains(HConfig.TP9951)) {
                    ret = CACH_REVERSE_PARKING_CHIP_MAP.get(HConfig.TP9951);
                } else if (v.contains(HConfig.TP9950)) {
                    ret = CACH_REVERSE_PARKING_CHIP_MAP.get(HConfig.TP9950);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private String getMemoryCode(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);
        long mMemTotal = memoryInfo.totalMem / 1048576;
        if (mMemTotal < 700) {
            return "0";
        }
        if (mMemTotal > 900 && mMemTotal < 1300) {
            return "1";
        }
        if (mMemTotal > 1300 && mMemTotal < 1800) {
            return "9";
        }
        String code = "" + ((int) Math.ceil(mMemTotal / 1024.0d));
        return code;
    }

    private String getEmmcCode() {
        int primaryVolumeSize = HConfig.getPrimaryVolumeSize();
        int mEmmcTotal = (int) Math.ceil((primaryVolumeSize * 1.0f) / 16.0f);
        return String.valueOf(mEmmcTotal);
    }

    private String getSoundEffectChipCode() {
        boolean mExt = new File(HConfig.CUSTOM_SOUND_CHIP_PATH).exists();
        if (!mExt) {
            return "0";
        }
        String chipName = SystemProperties.get(HConfig.PROP_DSP_CHIP, HConfig.DSP_CHIP_7601);
        String tmpcode = CACH_SOUND_EFFECT_CHIP_MAP.get(chipName);
        if (tmpcode == null) {
            return "0";
        }
        return tmpcode;
    }

    private String getOtherInfo() {
        String mReString = "";
        if ("ms1836".equals(SystemProperties.get(HConfig.PROP_AVOUT_CHIP, ""))) {
            mReString = "V";
        }
        if ("TRUE".equals(SystemProperties.get(HConfig.PROP_GOOD_COM_AUTH, ""))) {
            return mReString + "B";
        }
        return mReString;
    }
}
