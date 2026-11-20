package com.hcn.common.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.hcn.common.app.HShellUtils;
import com.hcn.skin.support.utils.ShellUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class HDeviceUtils {
    private static final String KEY_UDID = "KEY_UDID";
    private static volatile String udid;

    private HDeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isDeviceRooted() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/", "/system/sbin/", "/usr/bin/", "/vendor/bin/"};
        for (int i = 0; i < 11; i++) {
            if (new File(strArr[i] + ShellUtil.COMMAND_SU).exists()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAdbEnabled() {
        return Settings.Secure.getInt(HUtilsEx.getApp().getContentResolver(), "adb_enabled", 0) > 0;
    }

    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getAndroidID() {
        String string = Settings.Secure.getString(HUtilsEx.getApp().getContentResolver(), "android_id");
        return ("9774d56d682e549c".equals(string) || string == null) ? "" : string;
    }

    public static String getMacAddress() {
        String macAddress = getMacAddress(null);
        if (!TextUtils.isEmpty(macAddress) || getWifiEnabled()) {
            return macAddress;
        }
        setWifiEnabled(true);
        setWifiEnabled(false);
        return getMacAddress(null);
    }

    private static boolean getWifiEnabled() {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        if (wifiManager == null) {
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    private static void setWifiEnabled(boolean z) {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        if (wifiManager == null || z == wifiManager.isWifiEnabled()) {
            return;
        }
        wifiManager.setWifiEnabled(z);
    }

    public static String getMacAddress(String... strArr) {
        String macAddressByNetworkInterface = getMacAddressByNetworkInterface();
        if (isAddressNotInExcepts(macAddressByNetworkInterface, strArr)) {
            return macAddressByNetworkInterface;
        }
        String macAddressByInetAddress = getMacAddressByInetAddress();
        if (isAddressNotInExcepts(macAddressByInetAddress, strArr)) {
            return macAddressByInetAddress;
        }
        String macAddressByWifiInfo = getMacAddressByWifiInfo();
        if (isAddressNotInExcepts(macAddressByWifiInfo, strArr)) {
            return macAddressByWifiInfo;
        }
        String macAddressByFile = getMacAddressByFile();
        return isAddressNotInExcepts(macAddressByFile, strArr) ? macAddressByFile : "";
    }

    private static boolean isAddressNotInExcepts(String str, String... strArr) {
        if (TextUtils.isEmpty(str) || "02:00:00:00:00:00".equals(str)) {
            return false;
        }
        if (strArr == null) {
            return true;
        }
        for (String str2 : strArr) {
            if (str2 != null && str2.equals(str)) {
                return false;
            }
        }
        return true;
    }

    private static String getMacAddressByWifiInfo() {
        WifiInfo connectionInfo;
        try {
            WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getApplicationContext().getSystemService("wifi");
            if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
                return "02:00:00:00:00:00";
            }
            String macAddress = connectionInfo.getMacAddress();
            return !TextUtils.isEmpty(macAddress) ? macAddress : "02:00:00:00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String getMacAddressByNetworkInterface() {
        byte[] hardwareAddress;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement != null && "wlan0".equalsIgnoreCase(networkInterfaceNextElement.getName()) && (hardwareAddress = networkInterfaceNextElement.getHardwareAddress()) != null && hardwareAddress.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.format("%02x:", Byte.valueOf(b)));
                    }
                    return sb.substring(0, sb.length() - 1);
                }
            }
            return "02:00:00:00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String getMacAddressByInetAddress() {
        NetworkInterface byInetAddress;
        byte[] hardwareAddress;
        try {
            InetAddress inetAddress = getInetAddress();
            if (inetAddress == null || (byInetAddress = NetworkInterface.getByInetAddress(inetAddress)) == null || (hardwareAddress = byInetAddress.getHardwareAddress()) == null || hardwareAddress.length <= 0) {
                return "02:00:00:00:00:00";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : hardwareAddress) {
                sb.append(String.format("%02x:", Byte.valueOf(b)));
            }
            return sb.substring(0, sb.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static InetAddress getInetAddress() {
        String hostAddress;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && (hostAddress = inetAddressNextElement.getHostAddress()) != null && hostAddress.indexOf(58) < 0) {
                            return inetAddressNextElement;
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getMacAddressByFile() {
        String str;
        String str2;
        HShellUtils.CommandResult commandResultExecCmd = HShellUtils.execCmd("getprop wifi.interface", false);
        if (commandResultExecCmd.result != 0 || (str = commandResultExecCmd.successMsg) == null) {
            return "02:00:00:00:00:00";
        }
        HShellUtils.CommandResult commandResultExecCmd2 = HShellUtils.execCmd("cat /sys/class/net/" + str + "/address", false);
        return (commandResultExecCmd2.result != 0 || (str2 = commandResultExecCmd2.successMsg) == null || str2.length() <= 0) ? "02:00:00:00:00:00" : str2;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        String str = Build.MODEL;
        return str != null ? str.trim().replaceAll("\\s*", "") : "";
    }

    public static String[] getABIs() {
        if (Build.VERSION.SDK_INT >= 21) {
            return Build.SUPPORTED_ABIS;
        }
        return !TextUtils.isEmpty(Build.CPU_ABI2) ? new String[]{Build.CPU_ABI, Build.CPU_ABI2} : new String[]{Build.CPU_ABI};
    }

    public static boolean isTablet() {
        return (Resources.getSystem().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isEmulator() {
        String networkOperatorName;
        if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.toLowerCase().contains("vbox") || Build.FINGERPRINT.toLowerCase().contains("test-keys") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT)) {
            return true;
        }
        TelephonyManager telephonyManager = (TelephonyManager) HUtilsEx.getApp().getSystemService("phone");
        if (telephonyManager == null || (networkOperatorName = telephonyManager.getNetworkOperatorName()) == null) {
            networkOperatorName = "";
        }
        if ("android".equalsIgnoreCase(networkOperatorName)) {
            return true;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("tel:123456"));
        intent.setAction("android.intent.action.DIAL");
        if (intent.resolveActivity(HUtilsEx.getApp().getPackageManager()) == null) {
            return true;
        }
        return isEmulatorByCpu();
    }

    private static boolean isEmulatorByCpu() {
        String cpuInfo = readCpuInfo();
        return cpuInfo.contains("intel") || cpuInfo.contains("amd");
    }

    private static String readCpuInfo() {
        try {
            Process processStart = new ProcessBuilder("/system/bin/cat", "/proc/cpuinfo").start();
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream(), StandardCharsets.UTF_8));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    bufferedReader.close();
                    return sb.toString().toLowerCase();
                }
            }
        } catch (IOException unused) {
            return "";
        }
    }

    public static boolean isDevelopmentSettingsEnabled() {
        return Settings.Global.getInt(HUtilsEx.getApp().getContentResolver(), "development_settings_enabled", 0) > 0;
    }

    public static String getUniqueDeviceId() {
        return getUniqueDeviceId("", true);
    }

    public static String getUniqueDeviceId(String str) {
        return getUniqueDeviceId(str, true);
    }

    public static String getUniqueDeviceId(boolean z) {
        return getUniqueDeviceId("", z);
    }

    public static String getUniqueDeviceId(String str, boolean z) {
        if (!z) {
            return getUniqueDeviceIdReal(str);
        }
        if (udid == null) {
            synchronized (HDeviceUtils.class) {
                if (udid == null) {
                    String string = HUtilsEx.getSpUtils4Utils().getString(KEY_UDID, null);
                    if (string != null) {
                        udid = string;
                        return udid;
                    }
                    return getUniqueDeviceIdReal(str);
                }
            }
        }
        return udid;
    }

    private static String getUniqueDeviceIdReal(String str) {
        try {
            String androidID = getAndroidID();
            if (!TextUtils.isEmpty(androidID)) {
                return saveUdid(str + 2, androidID);
            }
        } catch (Exception unused) {
        }
        return saveUdid(str + 9, "");
    }

    public static boolean isSameDevice(String str) {
        if (TextUtils.isEmpty(str) && str.length() < 33) {
            return false;
        }
        if (str.equals(udid) || str.equals(HUtilsEx.getSpUtils4Utils().getString(KEY_UDID, null))) {
            return true;
        }
        int length = str.length() - 33;
        int i = length + 1;
        String strSubstring = str.substring(length, i);
        if (strSubstring.startsWith("1")) {
            String macAddress = getMacAddress();
            if ("".equals(macAddress)) {
                return false;
            }
            return str.substring(i).equals(getUdid("", macAddress));
        }
        if (!strSubstring.startsWith("2")) {
            return false;
        }
        String androidID = getAndroidID();
        if (TextUtils.isEmpty(androidID)) {
            return false;
        }
        return str.substring(i).equals(getUdid("", androidID));
    }

    private static String saveUdid(String str, String str2) {
        udid = getUdid(str, str2);
        HUtilsEx.getSpUtils4Utils().put(KEY_UDID, udid);
        return udid;
    }

    private static String getUdid(String str, String str2) {
        if ("".equals(str2)) {
            return str + UUID.randomUUID().toString().replace("-", "");
        }
        return str + UUID.nameUUIDFromBytes(str2.getBytes()).toString().replace("-", "");
    }
}
