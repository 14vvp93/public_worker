package com.android.settingslib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.wrapper.LocationManagerWrapper;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes.dex */
public class Utils {

    @VisibleForTesting
    static final String STORAGE_MANAGER_SHOW_OPT_IN_PROPERTY = "ro.storage_manager.show_opt_in";
    static final int[] WIFI_PIE = {android.R.drawable.ic_media_route_connecting_light_01_mtrl, android.R.drawable.ic_media_route_connecting_light_02_mtrl, android.R.drawable.ic_media_route_connecting_light_03_mtrl, android.R.drawable.ic_media_route_connecting_light_04_mtrl, android.R.drawable.ic_media_route_connecting_light_05_mtrl};
    private static String sPermissionControllerPackageName;
    private static String sServicesSystemSharedLibPackageName;
    private static String sSharedSystemSharedLibPackageName;
    private static Signature[] sSystemSignature;

    public static void updateLocationEnabled(Context context, boolean enabled, int userId, int source) {
        Settings.Secure.putIntForUser(context.getContentResolver(), "location_changer", source, userId);
        Intent intent = new Intent("com.android.settings.location.MODE_CHANGING");
        int oldMode = Settings.Secure.getIntForUser(context.getContentResolver(), "location_mode", 0, userId);
        int newMode = enabled ? 3 : 0;
        intent.putExtra("CURRENT_MODE", oldMode);
        intent.putExtra("NEW_MODE", newMode);
        context.sendBroadcastAsUser(intent, UserHandle.of(userId), "android.permission.WRITE_SECURE_SETTINGS");
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        LocationManagerWrapper wrapper = new LocationManagerWrapper(locationManager);
        wrapper.setLocationEnabledForUser(enabled, UserHandle.of(userId));
    }

    public static String formatPercentage(long amount, long total) {
        return formatPercentage(amount / total);
    }

    public static String formatPercentage(int percentage) {
        return formatPercentage(((double) percentage) / 100.0d);
    }

    public static String formatPercentage(double percentage) {
        return NumberFormat.getPercentInstance().format(percentage);
    }

    public static int getColorAccent(Context context) {
        return getColorAttr(context, android.R.attr.colorAccent);
    }

    public static int getColorError(Context context) {
        return getColorAttr(context, android.R.attr.colorError);
    }

    public static int getDefaultColor(Context context, int resId) {
        ColorStateList list = context.getResources().getColorStateList(resId, context.getTheme());
        return list.getDefaultColor();
    }

    public static int getDisabled(Context context, int inputColor) {
        return applyAlphaAttr(context, android.R.attr.disabledAlpha, inputColor);
    }

    public static int applyAlphaAttr(Context context, int attr, int inputColor) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        float alpha = ta.getFloat(0, 0.0f);
        ta.recycle();
        return applyAlpha(alpha, inputColor);
    }

    public static int applyAlpha(float alpha, int inputColor) {
        return Color.argb((int) (alpha * Color.alpha(inputColor)), Color.red(inputColor), Color.green(inputColor), Color.blue(inputColor));
    }

    public static int getColorAttr(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        int colorAccent = ta.getColor(0, 0);
        ta.recycle();
        return colorAccent;
    }

    public static int getThemeAttr(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        int theme = ta.getResourceId(0, 0);
        ta.recycle();
        return theme;
    }

    public static Drawable getDrawable(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        Drawable drawable = ta.getDrawable(0);
        ta.recycle();
        return drawable;
    }

    public static boolean isSystemPackage(Resources resources, PackageManager pm, PackageInfo pkg) {
        if (sSystemSignature == null) {
            sSystemSignature = new Signature[]{getSystemSignature(pm)};
        }
        if (sPermissionControllerPackageName == null) {
            sPermissionControllerPackageName = pm.getPermissionControllerPackageName();
        }
        if (sServicesSystemSharedLibPackageName == null) {
            sServicesSystemSharedLibPackageName = pm.getServicesSystemSharedLibraryPackageName();
        }
        if (sSharedSystemSharedLibPackageName == null) {
            sSharedSystemSharedLibPackageName = pm.getSharedSystemSharedLibraryPackageName();
        }
        return (sSystemSignature[0] != null && sSystemSignature[0].equals(getFirstSignature(pkg))) || pkg.packageName.equals(sPermissionControllerPackageName) || pkg.packageName.equals(sServicesSystemSharedLibPackageName) || pkg.packageName.equals(sSharedSystemSharedLibPackageName) || pkg.packageName.equals("com.android.printspooler") || isDeviceProvisioningPackage(resources, pkg.packageName);
    }

    private static Signature getFirstSignature(PackageInfo pkg) {
        if (pkg != null && pkg.signatures != null && pkg.signatures.length > 0) {
            return pkg.signatures[0];
        }
        return null;
    }

    private static Signature getSystemSignature(PackageManager pm) {
        try {
            PackageInfo sys = pm.getPackageInfo("android", 64);
            return getFirstSignature(sys);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isDeviceProvisioningPackage(Resources resources, String packageName) {
        String deviceProvisioningPackage = resources.getString(android.R.string.activity_resolver_use_once);
        return deviceProvisioningPackage != null && deviceProvisioningPackage.equals(packageName);
    }
}
