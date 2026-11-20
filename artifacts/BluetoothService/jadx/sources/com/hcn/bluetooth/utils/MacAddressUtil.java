package com.hcn.bluetooth.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.hcn.skin.support.utils.SkinListUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MacAddressUtil {
    private static final String MAC_ADDRESS_KEY = "mac_addresses";
    private static final String PREF_NAME = "mac_address_prefs";
    private static final String TAG = "MacAddressUtil";
    private static final boolean isDebug = false;
    private static final String AUTO_CONNECT_COUNT = "persist.sys.auto_connect_count";
    public static final int MAX_SIZE = SystemProperties.getInt(AUTO_CONNECT_COUNT, 1);

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    public static void saveMacAddresses(Context context, List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editorEdit = getPreferences(context).edit();
        editorEdit.putString(MAC_ADDRESS_KEY, TextUtils.join(SkinListUtils.DEFAULT_JOIN_SEPARATOR, list));
        editorEdit.apply();
    }

    public static List<String> getMacAddresses(Context context) {
        String string = getPreferences(context).getString(MAC_ADDRESS_KEY, "");
        if (TextUtils.isEmpty(string)) {
            return new ArrayList();
        }
        return new ArrayList(Arrays.asList(string.split(SkinListUtils.DEFAULT_JOIN_SEPARATOR)));
    }

    public static void addMacAddressToHead(Context context, String str) {
        List<String> macAddresses = getMacAddresses(context);
        int i = MAX_SIZE;
        if (i == 1) {
            SystemProperties.set("persist.sys.bt_address", str);
        }
        macAddresses.remove(str);
        if (macAddresses.size() >= i) {
            macAddresses.remove(macAddresses.size() - 1);
        }
        macAddresses.add(0, str);
        saveMacAddresses(context, macAddresses);
    }

    public static void removeMacAddress(Context context, String str) {
        List<String> macAddresses = getMacAddresses(context);
        macAddresses.remove(str);
        saveMacAddresses(context, macAddresses);
    }
}
