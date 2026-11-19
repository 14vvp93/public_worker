package com.hcn.bluetooth.api;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.auto.launcher.constants.PackageInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static final String ACCOUNT_TYPE = "com.android.bluetooth.pbapsink";
    public static final String BT_ACTIVITY_NAME = "com.autochips.bluetooth.MainBluetoothActivity";
    public static final String BT_MUSIC_ACTIVITY_NAME = "com.autochips.bluetooth.BtMusicActivity";
    public static final String BT_MUSIC_PACKAGE_NAME = "com.autochips.bluetooth";
    public static final String BT_PACKAGE_NAME = "com.autochips.bluetooth";
    private static final String KEY_REVERSE_CONTACT_NAME = "persist.sys.bt.reverse_name";
    public static final int MAX_TOAST_SIZE = 3;
    private static final String TAG = "Utils";
    private static final String TIMESTAMP_FORMAT = "yyyyMMdd'T'HHmmss";
    private static int mToastIndex;
    private static String reverseName;
    private static Toast[] mToasts = new Toast[3];
    private static int ThemeType = Integer.parseInt(getSystemProperty("persist.sys.etheme_god", "0"));
    private static String SELECTION = "data1=? AND account_name=? AND account_type=?";

    static {
        updateReverseName();
    }

    public static boolean isPortrait(Context context) {
        return context != null && context.getResources().getConfiguration().orientation == 1;
    }

    public static String dateFormat(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 4)).append("-").append(str.substring(4, 6)).append("-").append(str.substring(6, 8));
        return sb.toString();
    }

    public static String timeFormat(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 2)).append(":").append(str.substring(2, 4)).append(":").append(str.substring(4, 6));
        return sb.toString();
    }

    public static String getDateToString(long j) {
        String[] strArrSplit;
        String str = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date(j));
        if (str == null || (strArrSplit = str.split("T")) == null || strArrSplit.length <= 0) {
            return SkinID.SKIN_NONE;
        }
        return dateFormat(strArrSplit[0]) + " " + timeFormat(strArrSplit[1]);
    }

    public static String getContactNameByNumber(Context context, String str, String str2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String string = SkinID.SKIN_NONE;
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return SkinID.SKIN_NONE;
        }
        Cursor cursorQuery = null;
        try {
            try {
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                isReverseName();
                cursorQuery = contentResolver.query(uri, new String[]{"display_name"}, SELECTION, new String[]{str, str2, ACCOUNT_TYPE}, null);
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        string = cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
                        if (!TextUtils.isEmpty(string)) {
                            break;
                        }
                    }
                }
            } catch (Exception unused) {
                Log.e(TAG, "getContactNameByNumber Exception!!!");
            }
            return string;
        } finally {
            Log.d(TAG, "getContactNameByNumber: time=" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public static void startApp(Context context, String str, String str2) {
        if (context == null) {
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName(str, str2));
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, int i) {
        if (context == null) {
            return;
        }
        Toast[] toastArr = mToasts;
        int i2 = mToastIndex;
        if (toastArr[i2] != null) {
            toastArr[i2].cancel();
        }
        mToasts[mToastIndex] = Toast.makeText(context, context.getString(i), 0);
        mToasts[mToastIndex].show();
        mToastIndex = (mToastIndex + 1) % 3;
    }

    public static void showToast(Context context, String str) {
        if (context == null) {
            return;
        }
        Toast[] toastArr = mToasts;
        int i = mToastIndex;
        if (toastArr[i] != null) {
            toastArr[i].cancel();
        }
        mToasts[mToastIndex] = Toast.makeText(context, str, 0);
        mToasts[mToastIndex].show();
        mToastIndex = (mToastIndex + 1) % 3;
    }

    public static boolean isNaviTopRunning(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ComponentName componentName = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0).topActivity;
            return componentName.getPackageName().toLowerCase().contains("navi") || componentName.getPackageName().toLowerCase().contains("map") || componentName.getPackageName().toLowerCase().contains("igo") || componentName.getPackageName().toLowerCase().contains("sygic") || componentName.getPackageName().toLowerCase().contains("papago") || componentName.getPackageName().toLowerCase().contains(PackageInfo.WAZE_PACKAGE_NAME);
        } catch (SecurityException unused) {
        }
    }

    public static boolean isReverseName() {
        return "enable".equals(reverseName);
    }

    public static void updateReverseName() {
        reverseName = getSystemProperty(KEY_REVERSE_CONTACT_NAME, "disable");
    }

    public static String getSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, new String(str), str2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void setSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, new String(str), str2);
        } catch (Exception unused) {
        }
    }

    public static int getThemeType() {
        return ThemeType;
    }
}
