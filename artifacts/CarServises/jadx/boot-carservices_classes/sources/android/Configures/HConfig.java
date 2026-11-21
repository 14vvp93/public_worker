package android.Configures;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.utilities.Util;
import com.android.car.settings.ICarSettings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.io.IoUtils;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class HConfig {
    public static final String AHD1080P25 = "AHD1080P25";
    public static final String AHD1080P30 = "AHD1080P30";
    public static final String AHD720P25 = "AHD720P25";
    public static final String AHD720P30 = "AHD720P30";
    public static final String ASP_CHIP_2348 = "pt2348";
    public static final String ASP_CHIP_3313 = "cd3313";
    public static final String ASP_CHIP_CSC37534 = "csc37534";
    private static final String CARSETTINGS_SERVICE_NAME = "car_settings";
    public static final String CUSTOM_SOUND_CHIP_PATH = "/dev/audiomux";
    public static final String CUSTOM_VIDEO_CHIP_PATH = "/dev/videomux";
    public static final String DSP_CHIP_32107 = "bu32107";
    public static final String DSP_CHIP_7601 = "ak7601";
    public static final String DSP_CHIP_7602 = "ak7602";
    public static final String DSP_CHIP_7604 = "ak7604";
    public static final String DSP_CHIP_7604c = "ak7604c";
    public static final String DSP_CHIP_FY7604 = "fy7604";
    public static final int HIGH_SPEED_PROHIBIT_STATE = 1;
    private static final HashSet<String> HW_SETTINGS;
    private static final String KEY_FAKE_EMMC_SIZE = "fake_emmc_size";
    private static final String KEY_FAKE_MEM_SIZE = "fake_memory_size";
    private static final String KEY_FAKE_PLATFORM = "fake_platform";
    private static final String KEY_FAKE_VERSION = "fake_version";
    public static final String LOCKSCREEN_DISABLED_FILE_NAME = "/apd/lockscreen_disabled";
    public static final int LOW_SPEED_WATCH_STATE = 0;
    private static final int MSG_SAVE = 1;
    public static final String NTSC = "NTSC";
    public static final int NVROM_HW_MAINBOARD_CODE = 8;
    public static final int NVROM_LCM_AVDD_IDX = 2;
    public static final int NVROM_LCM_BACKLIGHT_RANGE = 7;
    public static final int NVROM_LCM_FORCE_ID = 5;
    public static final int NVROM_LCM_INDEX0 = 0;
    public static final int NVROM_LCM_POWER_SEQ = 6;
    public static final int NVROM_LCM_VCOM_IDX = 3;
    public static final int NVROM_MOTOR_IDX = 4;
    public static final int NVROM_USB_MODE_IDX = 1;
    public static final String PAL = "PAL";
    public static final String PARKINGVIEW_THIRDAPP_ENABLE = "parkingview_thirdApp_enable";
    public static final String PROP_AVOUT_CHIP = "ro.hw.avout.chip";
    public static final String PROP_CPU_ARCH = "ro.product.cpu.abi";
    public static final String PROP_CUSTOM_SYSTEM_VERSION_PREFFIX = "persist.sys.customer";
    public static final String PROP_DSP_CHIP = "ro.hw.dsp.chip";
    public static final String PROP_GOOD_COM_AUTH = "ro.hw.goodocom.auth";
    public static final String PROP_MCU_VERSION = "hw.sys.device.version";
    public static final String PROP_SYSTEM_VERSION = "ro.mediatek.version.release";
    public static final String R1080P25 = "R1080P25";
    public static final String R1080P30 = "R1080P30";
    public static final String R720P25 = "R720P25";
    public static final String R720P30 = "R720P30";
    public static final String R720P50 = "R720P50";
    public static final String R720P60 = "R720P60";
    public static final String SETTING_PROP_FAKE_MCU_VERSION = "fake_mcu_version";
    private static final String TAG = "HConfig";
    public static final String TIME_12_24 = "time_12_24";
    public static final String TP2804 = "TP2804";
    public static final String TP9950 = "TP9950";
    public static final String TP9951 = "TP9951";
    public static final String VEHICLE_SPEED_VIDEO_STATE = "vehicle_speed_video_state";
    public static final String acc_jump_fcamera = "acc_jump_fcamera";
    public static final String acc_show_ad_logo_enable = "acc_show_ad_logo_enable";
    public static final String accoff_delay = "accoff_delay";
    public static final String audio_amp_percentage = "hcnhw/audio_amp_percentage";
    public static final String audio_background_volume = "audio_background_volume";
    public static final String audio_lr_balance = "audio_lr_balance";
    public static final String audio_rev_volume = "audio_rev_volume";
    public static final String aux_audio_status = "aux_audio_status";
    public static final String aux_icon_enable = "aux_icon_enable";
    public static final String avin_videoflip = "avin_videoflip";
    public static final String back_camera_select = "back_camera_select_setting";
    public static final String camera_crop = "hcnhw/camera_crop";
    public static final String camera_mirror_10 = "hcnhw/camera_mirror_10";
    public static final String camera_mirror_11 = "hcnhw/camera_mirror_11";
    public static final String camera_mirror_8 = "hcnhw/camera_mirror_8";
    public static final String camera_mirror_9 = "hcnhw/camera_mirror_9";
    public static final String camera_open_delayed_time = "hcnhw/camera_open_delayed_time";
    public static final String camera_record_auto_save = "hcnhw/camera_record_auto_save";
    public static final String camera_record_save_path = "camera_record_save_path";
    public static final String camera_record_save_time = "hcnhw/camera_record_save_time";
    public static final String camera_return_standby_time = "hcnhw/camera_return_standby_time";
    public static final String camera_standby_time = "hcnhw/camera_standby_time";
    public static final String camera_turing_set = "hcnhw/camera_turing_set";
    public static final String camera_ui_type = "hcnhw/camera_ui_type";
    public static final String can_control_extpwr = "can_control_extpwr";
    public static final String can_ignore_doorinfo = "can_ignore_doorinfo";
    public static final String can_ignore_pwminfo = "can_ignore_pwminfo";
    public static final String can_type_settings = "hcnhw/can_type_settings";
    public static final String car_model_settings = "hcnhw/car_model_settings";
    public static final String car_revs_support = "hcnhw/car_revs_support";
    public static final String coldstart_auto_launch_app = "coldstart_auto_launch_app";
    public static final String dash_camera_icon_enable = "dash_camera_icon_enable";
    public static final int def_radio_region;
    public static final String default_audio_volume = "default_audio_volume";
    public static final String deinterlance_enable = "hcnhw/deinterlance_enable";
    public static final String driving_disable_video = "driving_disable_video";
    public static final String enable_android_home = "enable_android_home";
    public static final String enable_install_packages = "hcnhw/enable_install_packages";
    public static final String exclusive_audio_focus = "sys.exclusive.audio_focus_package";
    public static final String external_microphone = "hcnhw/external_microphone";
    public static final String fan_constant_closing = "fan_constant_closing";
    public static final String fan_constant_opening = "fan_constant_opening";
    public static final String fan_temper_threshold = "fan_temper_threshold";
    public static final String fan_volume_threshold = "fan_volume_threshold";
    public static final String fcamera_switch_key = "fcamera_switch_key";
    public static final String front_videoflip = "front_videoflip";
    public static final String gsensor_sensitive = "gsensor_sensitive";
    public static final String headlight_setting = "headlight_setting";
    public static final String install_packages_code = "install_packages_code";
    public static final String knob_actuator_step = "hcnhw/knob_actuator_step";
    public static final String lcd_avdd = "hcnhw/lcd_avdd";
    public static final String lcd_backlight_range = "hcnhw/lcd_bl_range";
    public static final String lcd_vcom = "hcnhw/lcd_vcom";
    public static final String lockscreen_disabled_pin = "lockscreen_disabled_pin";
    public static final String lockscreen_unlock_pin = "lockscreen_unlock_pin";
    private static ICarSettings mCarSettingsService = null;
    private final Context mContext;
    private static String mMcuVersionInfo = null;
    public static final String mainboard_code = "hcnhw/mainboard_code";
    public static final String mcu_aux_mode = "hcnhw/mcu_aux_mode";
    public static final String media_navi_gain = "media_navi_gain";
    public static final String media_show_id3_title = "show_id3_title";
    public static final String panel_tolerance = "panel_tolerance";
    public static final String pannel_led_adjust_entry_enable = "pannel_led_adjust_enable";
    public static final String pannel_led_color = "pannel_led_color";
    public static final String pannel_led_color_mask = "pannel_led_color_mask";
    public static final String pannel_led_preset_colors = "pannel_led_preset_colors";
    public static final String power_down_afteraccoff = "power_down_afteraccoff";
    public static final String power_low_threshold = "power_low_threshold";
    public static final String poweroff_timeout = "poweroff_timeout";
    public static final String preferred_connect = "preferred_connect";
    public static final String preferred_navigator = "preferred_navigator";
    public static final String preferred_speed_unit = "preferred_speed_unit";
    public static final String radio_ant_extpwr = "radio_ant_extpwr";
    public static final String radio_module = "hcnhw/radio_module";
    public static final String radio_region = "radio_region";
    public static final String reardisplay_status = "reardisplay_status";
    public static final String reboot_insteadof_suspend = "reboot_insteadof_suspend";
    public static final String reboot_low_threshold = "reboot_low_threshold";
    public static final String rev_backlight = "rev_backlight";
    public static final String rev_camera_format = "hcnhw/rev_camera_format";
    public static final String rev_debounce = "rev_debounce";
    public static final String rev_dynamic_line = "rev_dynamic_line";
    public static final String rev_jump_fcamera = "rev_jump_fcamera";
    public static final String rev_static_line = "rev_static_line";
    public static final String rev_videoflip = "rev_videoflip";
    public static final String right_SideCamera = "hcnhw/right_SideCamera";
    private static HConfig sInstance = null;
    private final int sMainBoardCode;
    public static final String screen_motor = "hcnhw/screen_motor";
    public static final String screen_off_afteraccoff = "screen_off_afteraccoff";
    public static final String screenoff_timeout = "screenoff_timeout";
    public static final String screensaver_timeout = "screensaver_timeout";
    public static final String screensaver_type = "screensaver_type";
    public static final String setting_day_night_mode_choose = "setting_day_night_mode_choose";
    public static final String setting_fakepower_defaultvolume_enable = "setting_fakepower_defaultvolume_enable";
    public static final String setting_skin_mode_enable = "autosetting_skin_mode_enable";
    public static final String shortcut_setting = "shortcut_setting";
    public static final String startup_navigator = "startup_navigator";
    public static final String swc_long_press_time = "swc_long_press_time";
    public static final String swc_pullup = "swc_pullup";
    public static final String swc_repeat_time = "swc_repeat_time";
    public static final String swc_tolerance = "swc_tolerance";
    public static final String swc_type_predefined = "hcnhw/swc_type_predefined";
    public static final String touch_calibraion = "hcnhw/touch_cali";
    public static final String touch_irq_type = "hcnhw/touch_irq_type";
    public static final String usb_work_path = "usb_switch_key";
    public static final String user_key1_action = "user_key1_action";
    public static final String user_key2_action = "user_key2_action";
    public static final String user_warn_afteraccoff = "user_warn_afteraccoff";
    private static final File CFG_FileHW = new File("/data/system/config_hw.cfg");
    private static final File CFG_File3HW = new File("/apd/config_hw.cfg");
    private static final File CFG_File3HWSaved = new File("/apd/config_hw.saved");
    private static final File CFG_File4HCN = new File("/apd/hcnconfig.cfg");
    private static final File CFG_FileETC = new File("/system/etc/hcnconfig.cfg");
    private static final File CFG_FileSW = new File("/apd/config_sw.cfg");
    private static final File CFG_FileGL = new File("/apd/config_gl.cfg");
    private final SettingsObserver mObserver = new SettingsObserver();
    private final Handler H = new Handler() { // from class: android.Configures.HConfig.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                HConfig.this.save();
            }
        }
    };

    public interface MBoardCode {
        public static final int FEIYIN_DSP_BOARD = 101;
        public static final int NAKAMICHI_MP5_BOARD = 201;
        public static final int PUBLIC_BOARD = 0;
        public static final int YUFENG_USB_BOARD = 301;
    }

    static {
        def_radio_region = isOverseaVersion() ? 1 : 4;
        mMcuVersionInfo = null;
        HW_SETTINGS = new HashSet<>();
        HW_SETTINGS.add(external_microphone);
        HW_SETTINGS.add(can_type_settings);
        HW_SETTINGS.add(car_model_settings);
        HW_SETTINGS.add(radio_module);
        HW_SETTINGS.add(right_SideCamera);
        HW_SETTINGS.add(lcd_vcom);
        HW_SETTINGS.add(lcd_avdd);
        HW_SETTINGS.add(lcd_backlight_range);
        HW_SETTINGS.add(touch_calibraion);
        HW_SETTINGS.add(touch_irq_type);
        HW_SETTINGS.add(enable_install_packages);
        HW_SETTINGS.add(swc_type_predefined);
        HW_SETTINGS.add(audio_amp_percentage);
        HW_SETTINGS.add(mcu_aux_mode);
        HW_SETTINGS.add(camera_standby_time);
        HW_SETTINGS.add(camera_return_standby_time);
        HW_SETTINGS.add(camera_turing_set);
        HW_SETTINGS.add(camera_ui_type);
        HW_SETTINGS.add(camera_crop);
        HW_SETTINGS.add(camera_mirror_8);
        HW_SETTINGS.add(camera_mirror_9);
        HW_SETTINGS.add(camera_mirror_10);
        HW_SETTINGS.add(camera_mirror_11);
        HW_SETTINGS.add(camera_record_auto_save);
        HW_SETTINGS.add(camera_record_save_time);
        HW_SETTINGS.add(camera_record_save_path);
        HW_SETTINGS.add(camera_open_delayed_time);
        HW_SETTINGS.add(car_revs_support);
        HW_SETTINGS.add(deinterlance_enable);
        HW_SETTINGS.add(screen_motor);
        HW_SETTINGS.add(rev_camera_format);
        HW_SETTINGS.add(knob_actuator_step);
        HW_SETTINGS.add(mainboard_code);
    }

    public int mainBoardCode() {
        return this.sMainBoardCode;
    }

    public static boolean isOverseaVersion() {
        return SystemProperties.get("ro.com.google.gmsversion", "").length() > 0;
    }

    public static HConfig instance() {
        if (sInstance == null) {
            throw new RuntimeException("Please initialize [HConfig] Object!");
        }
        return sInstance;
    }

    public static HConfig instance(Context context) {
        if (sInstance == null) {
            synchronized (HConfig.class) {
                if (sInstance == null) {
                    sInstance = new HConfig(context);
                }
            }
        }
        return sInstance;
    }

    private HConfig(Context context) {
        this.mContext = context;
        load();
        ContentResolver cr = this.mContext.getContentResolver();
        int nMainBoardCode = getNvrom(8, 0);
        int tMainBoardCode = Settings.System.getInt(cr, mainboard_code, 0);
        if (nMainBoardCode <= 0 && tMainBoardCode != nMainBoardCode) {
            tMainBoardCode = 0;
            Settings.System.putInt(cr, mainboard_code, 0);
        }
        this.sMainBoardCode = tMainBoardCode;
    }

    private void load() {
        ContentResolver cr = this.mContext.getContentResolver();
        int buildutc = SystemProperties.getInt("ro.build.date.utc", 0);
        int savedutc = Settings.System.getInt(cr, "build_utc", -1);
        if (savedutc == -1) {
            loadFileSettings(cr, CFG_FileSW, true);
            loadFileSettings(cr, CFG_File3HW, true);
            loadFileSettings(cr, CFG_File3HWSaved, false);
            loadGlobalSettings(cr, CFG_FileGL, true);
            Settings.System.putInt(cr, "build_utc", buildutc);
            if (Settings.System.getInt(cr, audio_rev_volume, -1000) == -1000) {
                Settings.System.putInt(cr, audio_rev_volume, 0);
            }
            if (Settings.System.getInt(cr, audio_background_volume, -1000) == -1000) {
                Settings.System.putInt(cr, audio_background_volume, 0);
            }
            if (Settings.System.getInt(cr, power_down_afteraccoff, -1000) == -1000) {
                Settings.System.putInt(cr, power_down_afteraccoff, 8000);
            }
            if (Settings.System.getInt(cr, rev_dynamic_line, -1000) == -1000) {
                Settings.System.putInt(cr, rev_dynamic_line, 1);
            }
            if (Settings.System.getInt(cr, driving_disable_video, -1000) == -1000) {
                Settings.System.putInt(cr, driving_disable_video, 1);
            }
            if (Settings.System.getInt(cr, media_show_id3_title, -1000) == -1000) {
                Settings.System.putInt(cr, media_show_id3_title, 0);
            }
        }
        loadFileSettings(cr, CFG_FileETC, true);
        loadFileSettings(cr, CFG_File4HCN, true);
    }

    public void systemReady() {
        setMicrophone();
        setDelanceEnable();
        setRevCameraFormat();
        ContentResolver cr = this.mContext.getContentResolver();
        String platform = Settings.System.getString(cr, KEY_FAKE_PLATFORM);
        if (!TextUtils.isEmpty(platform)) {
            SystemProperties.set("sys.fakeplatform", platform);
        }
        String version = Settings.System.getString(cr, KEY_FAKE_VERSION);
        if (!TextUtils.isEmpty(version)) {
            SystemProperties.set("sys.fake_version", version);
        }
        int memSize = Settings.System.getInt(cr, KEY_FAKE_MEM_SIZE, -1000);
        if (memSize > 0) {
            SystemProperties.set("sys.memory.fakesize", Integer.toString(memSize));
        }
        int emmcSize = Settings.System.getInt(cr, KEY_FAKE_EMMC_SIZE, -1000);
        if (emmcSize > 0) {
            SystemProperties.set("sys.emmc.fakesize", Integer.toString(emmcSize));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMicrophone() {
        ContentResolver cr = this.mContext.getContentResolver();
        int extmicro = Settings.System.getInt(cr, external_microphone, 0) > 0 ? 1 : 0;
        AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
        am.setWiredDeviceConnectionState(-2147483632, extmicro, "", TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDelanceEnable() {
        ContentResolver cr = this.mContext.getContentResolver();
        String enable = Settings.System.getString(cr, deinterlance_enable);
        String v = (TextUtils.isEmpty(enable) || "0".equals(enable)) ? "bob" : enable;
        SystemProperties.set("sys.mtkcam.deinterlance", v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMotorToNvrom() {
        ContentResolver cr = this.mContext.getContentResolver();
        String str = Settings.System.getString(cr, screen_motor);
        if (str == null) {
            str = "";
        }
        int val = 0;
        if (str.contains("switch")) {
            val = 0 | 1;
        }
        if (str.contains("motor")) {
            val |= 2;
        }
        setNvrom(4, val);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRevCameraFormat() {
        ContentResolver cr = this.mContext.getContentResolver();
        String format = Settings.System.getString(cr, rev_camera_format);
        if (TextUtils.isEmpty(format)) {
            format = "N/A";
        }
        try {
            FileUtils.stringToFile(new File(CUSTOM_VIDEO_CHIP_PATH), "rev_format=" + format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFileSettings(ContentResolver cr, File file, boolean override) {
        if (!file.exists()) {
            return;
        }
        try {
            FileReader input = new FileReader(file.getAbsolutePath());
            BufferedReader in = new BufferedReader(input);
            while (true) {
                String line = in.readLine();
                if (line != null) {
                    String line2 = line.trim();
                    if (!line2.startsWith("#")) {
                        String[] kv = line2.split("=");
                        if (kv.length == 1) {
                            String key = kv[0].trim();
                            if (override || Settings.System.getString(cr, key) == null) {
                                Settings.System.putString(cr, key, "");
                            }
                        }
                        if (kv.length == 2) {
                            String key2 = kv[0].trim();
                            if (override || Settings.System.getString(cr, key2) == null) {
                                Settings.System.putString(cr, key2, kv[1].trim());
                            }
                        }
                    }
                } else {
                    in.close();
                    input.close();
                    return;
                }
            }
        } catch (IOException e) {
        }
    }

    private void loadGlobalSettings(ContentResolver cr, File file, boolean override) {
        if (!file.exists() || !file.isFile()) {
            return;
        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            Throwable th = null;
            while (true) {
                try {
                    try {
                        String line = in.readLine();
                        if (line != null) {
                            String line2 = line.trim();
                            if (!line2.startsWith("#")) {
                                String[] kv = line2.split("=", 2);
                                if (kv.length == 1) {
                                    String key = kv[0].trim();
                                    if (override || Settings.Global.getString(cr, key) == null) {
                                        Settings.Global.putString(cr, key, "");
                                    }
                                }
                                if (kv.length == 2) {
                                    String key2 = kv[0].trim();
                                    String value = kv[1].trim();
                                    if (override || Settings.Global.getString(cr, key2) == null) {
                                        Settings.Global.putString(cr, key2, value);
                                    }
                                }
                            }
                        } else {
                            in.close();
                            return;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                } catch (Throwable th3) {
                    if (th != null) {
                        try {
                            in.close();
                        } catch (Throwable th4) {
                            th.addSuppressed(th4);
                        }
                    } else {
                        in.close();
                    }
                    throw th3;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "load config_gl config file Exception:" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        ContentResolver cr = this.mContext.getContentResolver();
        Bundle map = new Bundle();
        for (String key : HW_SETTINGS) {
            String v = Settings.System.getString(cr, key);
            if (v != null) {
                map.putString(key, v);
            }
        }
        Object[] infoIds = map.keySet().toArray();
        Arrays.sort(infoIds);
        StringBuilder sb = new StringBuilder(2048);
        for (Object key2 : infoIds) {
            sb.append(key2);
            sb.append("=");
            sb.append(map.getString((String) key2));
            sb.append("\n");
        }
        AtomicFile destination = new AtomicFile(CFG_FileHW);
        FileOutputStream fos = null;
        try {
            try {
                fos = destination.startWrite();
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(sb.toString());
                osw.close();
                destination.finishWrite(fos);
            } catch (Throwable th) {
                destination.failWrite(fos);
            }
            IoUtils.closeQuietly(fos);
            try {
                File dst = new File(CFG_File3HWSaved.getAbsoluteFile() + ".tmp");
                FileUtils.copy(CFG_FileHW, dst);
                dst.renameTo(CFG_File3HWSaved);
                Util.nativeSync();
            } catch (Exception e) {
                Log.e(TAG, "Copy hw config file Exception:" + e);
            }
        } catch (Throwable th2) {
            IoUtils.closeQuietly(fos);
            throw th2;
        }
    }

    private class SettingsObserver extends ContentObserver {
        SettingsObserver() {
            super(new Handler());
            ContentResolver cr = HConfig.this.mContext.getContentResolver();
            cr.registerContentObserver(Settings.System.getUriFor("hcnhw"), true, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);
            String key = uri.toString().replace("content://settings/system/", "");
            Log.i(HConfig.TAG, "Setting changed " + uri + ", " + key);
            if (key.startsWith("hcnhw")) {
                HConfig.this.H.removeMessages(1);
                HConfig.this.H.sendEmptyMessageDelayed(1, 1000L);
            }
            if (key.equals(HConfig.external_microphone)) {
                HConfig.this.setMicrophone();
            }
            if (key.equals(HConfig.deinterlance_enable)) {
                HConfig.this.setDelanceEnable();
            }
            if (key.equals(HConfig.screen_motor)) {
                HConfig.this.setMotorToNvrom();
            }
            if (key.equals(HConfig.rev_camera_format)) {
                HConfig.this.setRevCameraFormat();
            }
        }
    }

    public static void setNvrom(int addr, int value) {
        IBinder service = ServiceManager.getService("nvrom");
        Parcel _data = Parcel.obtain();
        try {
            try {
                _data.writeInterfaceToken("android.carstatus.CarStatusService");
                _data.writeInt(addr);
                _data.writeInt(value);
                service.transact(1, _data, null, 0);
            } catch (Exception e) {
                Log.w(TAG, "setNvrom error " + e);
            }
        } finally {
            _data.recycle();
        }
    }

    public static int getNvrom(int addr) {
        return getNvrom(addr, 0);
    }

    public static int getNvrom(int addr, int def) {
        IBinder service = ServiceManager.getService("nvrom");
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        int value = def;
        try {
            _data.writeInterfaceToken("android.carstatus.CarStatusService");
            _data.writeInt(addr);
            _data.writeInt(def);
            service.transact(2, _data, _reply, 0);
            _reply.readException();
            value = _reply.readInt();
        } catch (Exception e) {
            Log.w(TAG, "getNvrom error " + e);
        } finally {
            _data.recycle();
            _reply.recycle();
        }
        return value;
    }

    public static String getSystemVersion(Context context) {
        if (mCarSettingsService == null) {
            mCarSettingsService = ICarSettings.Stub.asInterface(ServiceManager.getService("car_settings"));
        }
        try {
            if (mCarSettingsService == null) {
                return "NULL";
            }
            String result = mCarSettingsService.getSystemVersion();
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            return "NULL";
        }
    }

    public static String getMcuVersion(Context context) throws RemoteException {
        if (mMcuVersionInfo == null) {
            if (mCarSettingsService == null) {
                mCarSettingsService = ICarSettings.Stub.asInterface(ServiceManager.getService("car_settings"));
            }
            String result = "NULL";
            try {
                if (mCarSettingsService != null) {
                    result = mCarSettingsService.getMcuVersion();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mMcuVersionInfo = result;
        }
        return mMcuVersionInfo;
    }

    public static String getHardWareInfo(Context context) throws RemoteException {
        if (mCarSettingsService == null) {
            mCarSettingsService = ICarSettings.Stub.asInterface(ServiceManager.getService("car_settings"));
        }
        String result = "NULL";
        try {
            if (mCarSettingsService != null) {
                result = mCarSettingsService.getHardwareInfo();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "_" + result;
    }

    public static int getPrimaryVolumeSize() {
        int primaryVolumeSize = 0;
        String size = SystemProperties.get("ro.internal.storage.size", "").replaceAll("G", "");
        if (!TextUtils.isEmpty(size)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(size);
            if (isNum.matches()) {
                primaryVolumeSize = Integer.parseInt(size);
            }
        }
        if (primaryVolumeSize <= 0) {
            Log.e(TAG, "get the total size of emmc fail");
            return 0;
        }
        Log.i(TAG, "get the total size of emmc:" + primaryVolumeSize);
        return primaryVolumeSize;
    }

    public static boolean isRotationTypeConfigured(Context context) {
        ContentResolver cr = context.getContentResolver();
        String rotationType = Settings.System.getStringForUser(cr, screen_motor, -2);
        if (!TextUtils.isEmpty(rotationType)) {
            switch (rotationType) {
                case "switch":
                case "motor":
                case "manual":
                    return true;
            }
        }
        return false;
    }

    public static boolean needRotationToggleButton(Context context) {
        ContentResolver cr = context.getContentResolver();
        String rotationType = Settings.System.getString(cr, screen_motor);
        if (!TextUtils.isEmpty(rotationType)) {
            switch (rotationType) {
                case "motor":
                case "manual":
                    return true;
            }
        }
        return false;
    }

    public static String queryValueFromApd(String path, String name) {
        BufferedReader in = null;
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(name)) {
            return null;
        }
        File file = new File(path);
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            Log.v(TAG, "file not exist.");
            return null;
        }
        String result = null;
        FileReader input = null;
        try {
            try {
                try {
                    input = new FileReader(file);
                    in = new BufferedReader(input);
                    while (true) {
                        String line = in.readLine();
                        if (line == null) {
                            break;
                        }
                        String line2 = line.trim();
                        if (!line2.startsWith("#")) {
                            String[] kv = line2.split("=", 2);
                            if (kv.length == 2) {
                                String key = kv[0].trim();
                                String value = kv[1].trim();
                                if (name.equals(key) && !TextUtils.isEmpty(value)) {
                                    result = value;
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.close();
                } catch (Throwable th) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (input == null) {
                        throw th;
                    }
                    try {
                        input.close();
                        throw th;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        throw th;
                    }
                }
            } catch (Exception e4) {
                Log.e(TAG, "set display size exception:" + e4.toString());
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (input != null) {
                    input.close();
                }
                return result;
            }
        } catch (IOException e6) {
            e6.printStackTrace();
        }
        return result;
    }
}
