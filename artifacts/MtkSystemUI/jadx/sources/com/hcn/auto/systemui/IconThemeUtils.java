package com.hcn.auto.systemui;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.resources.SkinCompatResources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class IconThemeUtils {
    private static final String TAG = IconThemeUtils.class.getCanonicalName();
    private static List<AppInfo> appInfoList;

    private static List<AppInfo> parseIconThemeFromXml() {
        XmlResourceParser xmlParser;
        boolean getCurrentSkin = SkinCompatResources.getInstance().getSkinResources() == null;
        Log.d(TAG, "parseIconThemeFromXml: getCurrentSkin = " + getCurrentSkin);
        if (getCurrentSkin) {
            return null;
        }
        List<AppInfo> appInfoList2 = new ArrayList<>();
        AppInfo appInfo = null;
        if (!"xt617".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "")) && !"xt618".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", "")) && "sa266".equals(com.android.systemui.util.Utils.getSystemProperty("persist.sys.skin", ""))) {
            xmlParser = SkinCompatResources.getInstance().getXml("sa266_icon_themes");
        } else {
            xmlParser = SkinCompatResources.getInstance().getXml("xt617_icon_themes");
        }
        try {
            for (int event = xmlParser.getEventType(); event != 1; event = xmlParser.next()) {
                if (event != 0) {
                    switch (event) {
                        case 2:
                            if (xmlParser.getName().equals("app")) {
                                String packageName = xmlParser.getAttributeValue(null, "packageName");
                                String className = xmlParser.getAttributeValue(null, "className");
                                String hidden = xmlParser.getAttributeValue(null, "hidden");
                                appInfo = new AppInfo();
                                appInfo.setPackageName(packageName == null ? "" : packageName.trim());
                                appInfo.setClassName(className == null ? "" : className.trim());
                                String iconName = xmlParser.getText();
                                Log.d(TAG, "parseIconThemeFromXml: iconName = " + iconName);
                                appInfo.setIconName(iconName == null ? "" : iconName.trim());
                                appInfo.setHidden("true".equals(hidden));
                            }
                            break;
                        case 3:
                            if (xmlParser.getName().equals("app") && !isTrimEmpty(appInfo.getPackageName())) {
                                appInfoList2.add(appInfo);
                            }
                            break;
                        case 4:
                            String iconName2 = xmlParser.getText();
                            Log.d(TAG, "parseIconThemeFromXml: iconName = " + iconName2);
                            appInfo.setIconName(iconName2 == null ? "" : iconName2.trim());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
        } catch (XmlPullParserException e2) {
            Log.e(TAG, "", e2);
        } catch (Exception e3) {
            Log.e(TAG, "", e3);
        }
        return appInfoList2;
    }

    private static List<AppInfo> parseIconThemeFromXml(String xmlName) {
        boolean getCurrentSkin = SkinCompatResources.getInstance().getSkinResources() == null;
        Log.d(TAG, "parseIconThemeFromXml: getCurrentSkin = " + getCurrentSkin);
        if (getCurrentSkin) {
            return null;
        }
        List<AppInfo> appInfoList2 = new ArrayList<>();
        AppInfo appInfo = null;
        XmlResourceParser xmlParser = SkinCompatResources.getInstance().getXml(xmlName);
        try {
            for (int event = xmlParser.getEventType(); event != 1; event = xmlParser.next()) {
                if (event != 0) {
                    switch (event) {
                        case 2:
                            if (xmlParser.getName().equals("app")) {
                                String packageName = xmlParser.getAttributeValue(null, "packageName");
                                String className = xmlParser.getAttributeValue(null, "className");
                                String hidden = xmlParser.getAttributeValue(null, "hidden");
                                appInfo = new AppInfo();
                                appInfo.setPackageName(packageName == null ? "" : packageName.trim());
                                appInfo.setClassName(className == null ? "" : className.trim());
                                String iconName = xmlParser.getText();
                                Log.d(TAG, "parseIconThemeFromXml: first iconName = " + iconName);
                                appInfo.setIconName(iconName == null ? "" : iconName.trim());
                                appInfo.setHidden("true".equals(hidden));
                            }
                            break;
                        case 3:
                            if (xmlParser.getName().equals("app") && !isTrimEmpty(appInfo.getPackageName())) {
                                appInfoList2.add(appInfo);
                            }
                            break;
                        case 4:
                            String iconName2 = xmlParser.getText();
                            Log.d(TAG, "parseIconThemeFromXml: second iconName = " + iconName2);
                            appInfo.setIconName(iconName2 == null ? "" : iconName2.trim());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
        } catch (XmlPullParserException e2) {
            Log.e(TAG, "", e2);
        } catch (Exception e3) {
            Log.e(TAG, "", e3);
        }
        return appInfoList2;
    }

    public static Bitmap getCustomAppIconBitmap(String packageName, String className) {
        Log.d(TAG, "getCustomAppIconBitmap: packageName = " + isTrimEmpty(packageName) + " className = " + isTrimEmpty(className));
        if (isTrimEmpty(packageName)) {
            return null;
        }
        if (appInfoList == null) {
            appInfoList = parseIconThemeFromXml();
        }
        if (appInfoList == null) {
            return null;
        }
        for (int i = 0; i < appInfoList.size(); i++) {
            AppInfo appInfo = appInfoList.get(i);
            if (packageName.equals(appInfo.packageName) && ((isTrimEmpty(appInfo.className) || appInfo.className.equals(className)) && !isTrimEmpty(appInfo.iconName))) {
                return SkinCompatResources.getInstance().getBitmap(getIconBackgroundName(appInfo.iconName));
            }
        }
        return null;
    }

    public static Bitmap getCustomAppIconBitmap(String packageName, String className, String xmlName) {
        Log.d(TAG, "getCustomAppIconBitmap: packageName = " + isTrimEmpty(packageName) + " className = " + isTrimEmpty(className));
        if (isTrimEmpty(packageName)) {
            return null;
        }
        if (appInfoList == null) {
            appInfoList = parseIconThemeFromXml(xmlName);
        }
        if (appInfoList == null) {
            return null;
        }
        for (int i = 0; i < appInfoList.size(); i++) {
            AppInfo appInfo = appInfoList.get(i);
            if (packageName.equals(appInfo.packageName) && ((isTrimEmpty(appInfo.className) || appInfo.className.equals(className)) && !isTrimEmpty(appInfo.iconName))) {
                return SkinCompatResources.getInstance().getBitmap(getIconBackgroundName(appInfo.iconName));
            }
        }
        return null;
    }

    public static String getIconBackgroundName(String iconName) {
        String resName;
        Log.d(TAG, "getIconBackgroundName: iconName = " + iconName);
        if (SkinCompatResources.getInstance().getSkinResources() == null) {
            return iconName;
        }
        int support_color_theme = SkinCompatResources.getInstance().getInteger("support_color_theme");
        if (support_color_theme == 0) {
            return iconName;
        }
        String uiStyle = Settings.System.getString(SkinCompatManager.getInstance().getContext().getContentResolver(), "ui_style");
        if (TextUtils.isEmpty(uiStyle)) {
            uiStyle = "";
        }
        if ("".equals(uiStyle)) {
            resName = iconName;
        } else {
            resName = iconName + "_" + uiStyle;
        }
        int resId = SkinCompatResources.getInstance().getSkinResId(resName, "drawable");
        if (resId > 0) {
            return resName;
        }
        return iconName;
    }

    public static String getCustomAppIconName(String packageName, String className) {
        if (isTrimEmpty(packageName)) {
            return null;
        }
        if (appInfoList == null) {
            appInfoList = parseIconThemeFromXml();
        }
        if (appInfoList == null) {
            return null;
        }
        for (int i = 0; i < appInfoList.size(); i++) {
            AppInfo appInfo = appInfoList.get(i);
            if (packageName.equals(appInfo.packageName) && ((isTrimEmpty(appInfo.className) || appInfo.className.equals(className)) && !isTrimEmpty(appInfo.iconName))) {
                return appInfo.iconName;
            }
        }
        return null;
    }

    static boolean isTrimEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    static class AppInfo {
        private String className;
        private boolean hidden;
        private String iconName;
        private String packageName;

        AppInfo() {
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setIconName(String iconName) {
            this.iconName = iconName;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public String toString() {
            return "AppInfo{packageName='" + this.packageName + "', className='" + this.className + "', iconName='" + this.iconName + "', hidden='" + this.hidden + "'}";
        }
    }
}
