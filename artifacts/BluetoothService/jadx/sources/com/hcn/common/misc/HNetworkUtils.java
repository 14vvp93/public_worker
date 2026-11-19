package com.hcn.common.misc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.common.app.HShellUtils;
import com.hcn.common.lang.HThreadUtils;
import com.hcn.common.utils.HStringUtils;
import com.hcn.common.utils.HUtilsEx;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: classes.dex */
public class HNetworkUtils {
    private static final long SCAN_PERIOD_MILLIS = 3000;
    private static final Set<HUtilsEx.Consumer<WifiScanResults>> SCAN_RESULT_CONSUMERS = new CopyOnWriteArraySet();
    private static WifiScanResults sPreWifiScanResults;
    private static Timer sScanWifiTimer;

    public enum NetworkType {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    public interface OnNetworkStatusChangedListener {
        void onConnected(NetworkType networkType);

        void onDisconnected();
    }

    private HNetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void openWirelessSettings() {
        HUtilsEx.getApp().startActivity(new Intent("android.settings.WIRELESS_SETTINGS").setFlags(268435456));
    }

    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static HUtilsEx.Task<Boolean> isAvailableAsync(HUtilsEx.Consumer<Boolean> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<Boolean>(consumer) { // from class: com.hcn.common.misc.HNetworkUtils.1
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public Boolean doInBackground() {
                return Boolean.valueOf(HNetworkUtils.isAvailable());
            }
        });
    }

    public static boolean isAvailable() {
        return isAvailableByDns() || isAvailableByPing(null);
    }

    public static void isAvailableByPingAsync(HUtilsEx.Consumer<Boolean> consumer) {
        isAvailableByPingAsync(SkinID.SKIN_NONE, consumer);
    }

    public static HUtilsEx.Task<Boolean> isAvailableByPingAsync(final String str, HUtilsEx.Consumer<Boolean> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<Boolean>(consumer) { // from class: com.hcn.common.misc.HNetworkUtils.2
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public Boolean doInBackground() {
                return Boolean.valueOf(HNetworkUtils.isAvailableByPing(str));
            }
        });
    }

    public static boolean isAvailableByPing() {
        return isAvailableByPing(SkinID.SKIN_NONE);
    }

    public static boolean isAvailableByPing(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "223.5.5.5";
        }
        return HShellUtils.execCmd(String.format("ping -c 1 %s", str), false).result == 0;
    }

    public static void isAvailableByDnsAsync(HUtilsEx.Consumer<Boolean> consumer) {
        isAvailableByDnsAsync(SkinID.SKIN_NONE, consumer);
    }

    public static HUtilsEx.Task isAvailableByDnsAsync(final String str, HUtilsEx.Consumer<Boolean> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<Boolean>(consumer) { // from class: com.hcn.common.misc.HNetworkUtils.3
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public Boolean doInBackground() {
                return Boolean.valueOf(HNetworkUtils.isAvailableByDns(str));
            }
        });
    }

    public static boolean isAvailableByDns() {
        return isAvailableByDns(SkinID.SKIN_NONE);
    }

    public static boolean isAvailableByDns(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "www.baidu.com";
        }
        try {
            return InetAddress.getByName(str) != null;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getMobileDataEnabled() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) HUtilsEx.getApp().getSystemService("phone");
            if (telephonyManager == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 26) {
                return telephonyManager.isDataEnabled();
            }
            return ((Boolean) telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]).invoke(telephonyManager, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isBehindProxy() {
        return (System.getProperty("http.proxyHost") == null || System.getProperty("http.proxyPort") == null) ? false : true;
    }

    public static boolean isUsingVPN() {
        ConnectivityManager connectivityManager = (ConnectivityManager) HUtilsEx.getApp().getSystemService("connectivity");
        if (Build.VERSION.SDK_INT >= 28) {
            return connectivityManager.getNetworkInfo(17).isConnectedOrConnecting();
        }
        return connectivityManager.getNetworkInfo(4).isConnectedOrConnecting();
    }

    public static boolean isMobileData() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType() == 0;
    }

    public static boolean is4G() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getSubtype() == 13;
    }

    public static boolean is5G() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getSubtype() == 20;
    }

    public static boolean getWifiEnabled() {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        if (wifiManager == null) {
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    public static void setWifiEnabled(boolean z) {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        if (wifiManager == null || z == wifiManager.isWifiEnabled()) {
            return;
        }
        wifiManager.setWifiEnabled(z);
    }

    public static boolean isWifiConnected() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) HUtilsEx.getApp().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 1) ? false : true;
    }

    public static boolean isWifiAvailable() {
        return getWifiEnabled() && isAvailable();
    }

    public static HUtilsEx.Task<Boolean> isWifiAvailableAsync(HUtilsEx.Consumer<Boolean> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<Boolean>(consumer) { // from class: com.hcn.common.misc.HNetworkUtils.4
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public Boolean doInBackground() {
                return Boolean.valueOf(HNetworkUtils.isWifiAvailable());
            }
        });
    }

    public static String getNetworkOperatorName() {
        TelephonyManager telephonyManager = (TelephonyManager) HUtilsEx.getApp().getSystemService("phone");
        return telephonyManager == null ? SkinID.SKIN_NONE : telephonyManager.getNetworkOperatorName();
    }

    public static NetworkType getNetworkType() {
        if (isEthernet()) {
            return NetworkType.NETWORK_ETHERNET;
        }
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (activeNetworkInfo.getType() == 1) {
                return NetworkType.NETWORK_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    case 16:
                        return NetworkType.NETWORK_2G;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                        return NetworkType.NETWORK_3G;
                    case 13:
                    case 18:
                        return NetworkType.NETWORK_4G;
                    case 19:
                    default:
                        String subtypeName = activeNetworkInfo.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetworkType.NETWORK_3G;
                        }
                        return NetworkType.NETWORK_UNKNOWN;
                    case 20:
                        return NetworkType.NETWORK_5G;
                }
            }
            return NetworkType.NETWORK_UNKNOWN;
        }
        return NetworkType.NETWORK_NO;
    }

    private static boolean isEthernet() {
        NetworkInfo networkInfo;
        NetworkInfo.State state;
        ConnectivityManager connectivityManager = (ConnectivityManager) HUtilsEx.getApp().getSystemService("connectivity");
        if (connectivityManager == null || (networkInfo = connectivityManager.getNetworkInfo(9)) == null || (state = networkInfo.getState()) == null) {
            return false;
        }
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) HUtilsEx.getApp().getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    public static HUtilsEx.Task<String> getIPAddressAsync(final boolean z, HUtilsEx.Consumer<String> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<String>(consumer) { // from class: com.hcn.common.misc.HNetworkUtils.5
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public String doInBackground() {
                return HNetworkUtils.getIPAddress(z);
            }
        });
    }

    public static String getIPAddress(boolean z) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> linkedList = new LinkedList();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp() && !networkInterfaceNextElement.isLoopback()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        linkedList.addFirst(inetAddresses.nextElement());
                    }
                }
            }
            for (InetAddress inetAddress : linkedList) {
                if (!inetAddress.isLoopbackAddress()) {
                    String hostAddress = inetAddress.getHostAddress();
                    boolean z2 = hostAddress.indexOf(58) < 0;
                    if (z) {
                        if (z2) {
                            return hostAddress;
                        }
                    } else if (!z2) {
                        int iIndexOf = hostAddress.indexOf(37);
                        if (iIndexOf < 0) {
                            return hostAddress.toUpperCase();
                        }
                        return hostAddress.substring(0, iIndexOf).toUpperCase();
                    }
                }
            }
            return SkinID.SKIN_NONE;
        } catch (SocketException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            new LinkedList();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp() && !networkInterfaceNextElement.isLoopback()) {
                    List<InterfaceAddress> interfaceAddresses = networkInterfaceNextElement.getInterfaceAddresses();
                    int size = interfaceAddresses.size();
                    for (int i = 0; i < size; i++) {
                        InetAddress broadcast = interfaceAddresses.get(i).getBroadcast();
                        if (broadcast != null) {
                            return broadcast.getHostAddress();
                        }
                    }
                }
            }
            return SkinID.SKIN_NONE;
        } catch (SocketException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static HUtilsEx.Task<String> getDomainAddressAsync(final String str, HUtilsEx.Consumer<String> consumer) {
        return HUtilsEx.doAsync(new HUtilsEx.Task<String>(consumer) { // from class: com.hcn.common.misc.HNetworkUtils.6
            @Override // com.hcn.common.lang.HThreadUtils.Task
            public String doInBackground() {
                return HNetworkUtils.getDomainAddress(str);
            }
        });
    }

    public static String getDomainAddress(String str) {
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static String getIpAddressByWifi() {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        return wifiManager == null ? SkinID.SKIN_NONE : Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);
    }

    public static String getGatewayByWifi() {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        return wifiManager == null ? SkinID.SKIN_NONE : Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway);
    }

    public static String getNetMaskByWifi() {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        return wifiManager == null ? SkinID.SKIN_NONE : Formatter.formatIpAddress(wifiManager.getDhcpInfo().netmask);
    }

    public static String getServerAddressByWifi() {
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getSystemService("wifi");
        return wifiManager == null ? SkinID.SKIN_NONE : Formatter.formatIpAddress(wifiManager.getDhcpInfo().serverAddress);
    }

    public static String getSSID() {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) HUtilsEx.getApp().getApplicationContext().getSystemService("wifi");
        if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return SkinID.SKIN_NONE;
        }
        String ssid = connectionInfo.getSSID();
        if (TextUtils.isEmpty(ssid)) {
            return SkinID.SKIN_NONE;
        }
        return (ssid.length() > 2 && ssid.charAt(0) == '\"' && ssid.charAt(ssid.length() - 1) == '\"') ? ssid.substring(1, ssid.length() - 1) : ssid;
    }

    public static void registerNetworkStatusChangedListener(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
        NetworkChangedReceiver.getInstance().registerListener(onNetworkStatusChangedListener);
    }

    public static boolean isRegisteredNetworkStatusChangedListener(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
        return NetworkChangedReceiver.getInstance().isRegistered(onNetworkStatusChangedListener);
    }

    public static void unregisterNetworkStatusChangedListener(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
        NetworkChangedReceiver.getInstance().unregisterListener(onNetworkStatusChangedListener);
    }

    public static WifiScanResults getWifiScanResult() {
        List<ScanResult> scanResults;
        WifiScanResults wifiScanResults = new WifiScanResults();
        if (getWifiEnabled() && (scanResults = ((WifiManager) HUtilsEx.getApp().getSystemService("wifi")).getScanResults()) != null) {
            wifiScanResults.setAllResults(scanResults);
        }
        return wifiScanResults;
    }

    public static void addOnWifiChangedConsumer(final HUtilsEx.Consumer<WifiScanResults> consumer) {
        if (consumer == null) {
            return;
        }
        HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.misc.HNetworkUtils.7
            @Override // java.lang.Runnable
            public void run() {
                if (HNetworkUtils.SCAN_RESULT_CONSUMERS.isEmpty()) {
                    HNetworkUtils.SCAN_RESULT_CONSUMERS.add(consumer);
                    HNetworkUtils.startScanWifi();
                } else {
                    consumer.accept(HNetworkUtils.sPreWifiScanResults);
                    HNetworkUtils.SCAN_RESULT_CONSUMERS.add(consumer);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startScanWifi() {
        sPreWifiScanResults = new WifiScanResults();
        Timer timer = new Timer();
        sScanWifiTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.hcn.common.misc.HNetworkUtils.8
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                HNetworkUtils.startScanWifiIfEnabled();
                WifiScanResults wifiScanResult = HNetworkUtils.getWifiScanResult();
                if (HNetworkUtils.isSameScanResults(HNetworkUtils.sPreWifiScanResults.allResults, wifiScanResult.allResults)) {
                    return;
                }
                HNetworkUtils.sPreWifiScanResults = wifiScanResult;
                HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.misc.HNetworkUtils.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = HNetworkUtils.SCAN_RESULT_CONSUMERS.iterator();
                        while (it.hasNext()) {
                            ((HUtilsEx.Consumer) it.next()).accept(HNetworkUtils.sPreWifiScanResults);
                        }
                    }
                });
            }
        }, 0L, SCAN_PERIOD_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startScanWifiIfEnabled() {
        if (getWifiEnabled()) {
            ((WifiManager) HUtilsEx.getApp().getSystemService("wifi")).startScan();
        }
    }

    public static void removeOnWifiChangedConsumer(final HUtilsEx.Consumer<WifiScanResults> consumer) {
        if (consumer == null) {
            return;
        }
        HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.misc.HNetworkUtils.9
            @Override // java.lang.Runnable
            public void run() {
                HNetworkUtils.SCAN_RESULT_CONSUMERS.remove(consumer);
                if (HNetworkUtils.SCAN_RESULT_CONSUMERS.isEmpty()) {
                    HNetworkUtils.stopScanWifi();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void stopScanWifi() {
        Timer timer = sScanWifiTimer;
        if (timer != null) {
            timer.cancel();
            sScanWifiTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSameScanResults(List<ScanResult> list, List<ScanResult> list2) {
        if (list == null && list2 == null) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!isSameScanResultContent(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameScanResultContent(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult != null && scanResult2 != null && HStringUtils.equals(scanResult.BSSID, scanResult2.BSSID) && HStringUtils.equals(scanResult.SSID, scanResult2.SSID) && HStringUtils.equals(scanResult.capabilities, scanResult2.capabilities) && scanResult.level == scanResult2.level;
    }

    public static final class NetworkChangedReceiver extends BroadcastReceiver {
        private final Set<OnNetworkStatusChangedListener> mListeners = new HashSet();
        private NetworkType mType;

        /* JADX INFO: Access modifiers changed from: private */
        public static NetworkChangedReceiver getInstance() {
            return LazyHolder.INSTANCE;
        }

        void registerListener(final OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
            if (onNetworkStatusChangedListener == null) {
                return;
            }
            HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.misc.HNetworkUtils.NetworkChangedReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    int size = NetworkChangedReceiver.this.mListeners.size();
                    NetworkChangedReceiver.this.mListeners.add(onNetworkStatusChangedListener);
                    if (size == 0 && NetworkChangedReceiver.this.mListeners.size() == 1) {
                        NetworkChangedReceiver.this.mType = HNetworkUtils.getNetworkType();
                        HUtilsEx.getApp().registerReceiver(NetworkChangedReceiver.getInstance(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                    }
                }
            });
        }

        boolean isRegistered(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
            if (onNetworkStatusChangedListener == null) {
                return false;
            }
            return this.mListeners.contains(onNetworkStatusChangedListener);
        }

        void unregisterListener(final OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
            if (onNetworkStatusChangedListener == null) {
                return;
            }
            HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.common.misc.HNetworkUtils.NetworkChangedReceiver.2
                @Override // java.lang.Runnable
                public void run() {
                    int size = NetworkChangedReceiver.this.mListeners.size();
                    NetworkChangedReceiver.this.mListeners.remove(onNetworkStatusChangedListener);
                    if (size == 1 && NetworkChangedReceiver.this.mListeners.size() == 0) {
                        HUtilsEx.getApp().unregisterReceiver(NetworkChangedReceiver.getInstance());
                    }
                }
            });
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                HThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.hcn.common.misc.HNetworkUtils.NetworkChangedReceiver.3
                    @Override // java.lang.Runnable
                    public void run() {
                        NetworkType networkType = HNetworkUtils.getNetworkType();
                        if (NetworkChangedReceiver.this.mType == networkType) {
                            return;
                        }
                        NetworkChangedReceiver.this.mType = networkType;
                        if (networkType == NetworkType.NETWORK_NO) {
                            Iterator it = NetworkChangedReceiver.this.mListeners.iterator();
                            while (it.hasNext()) {
                                ((OnNetworkStatusChangedListener) it.next()).onDisconnected();
                            }
                        } else {
                            Iterator it2 = NetworkChangedReceiver.this.mListeners.iterator();
                            while (it2.hasNext()) {
                                ((OnNetworkStatusChangedListener) it2.next()).onConnected(networkType);
                            }
                        }
                    }
                }, 1000L);
            }
        }

        private static class LazyHolder {
            private static final NetworkChangedReceiver INSTANCE = new NetworkChangedReceiver();

            private LazyHolder() {
            }
        }
    }

    public static final class WifiScanResults {
        private List<ScanResult> allResults = new ArrayList();
        private List<ScanResult> filterResults = new ArrayList();

        public List<ScanResult> getAllResults() {
            return this.allResults;
        }

        public List<ScanResult> getFilterResults() {
            return this.filterResults;
        }

        public void setAllResults(List<ScanResult> list) {
            this.allResults = list;
            this.filterResults = filterScanResult(list);
        }

        private static List<ScanResult> filterScanResult(List<ScanResult> list) {
            ScanResult scanResult;
            if (list == null || list.isEmpty()) {
                return new ArrayList();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
            for (ScanResult scanResult2 : list) {
                if (!TextUtils.isEmpty(scanResult2.SSID) && ((scanResult = (ScanResult) linkedHashMap.get(scanResult2.SSID)) == null || scanResult.level < scanResult2.level)) {
                    linkedHashMap.put(scanResult2.SSID, scanResult2);
                }
            }
            return new ArrayList(linkedHashMap.values());
        }
    }
}
