package com.hcn.auto.theme.utils;

import android.Configures.HConfig;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class IconThemeUtils {
    private static final String TAG = IconThemeUtils.class.getCanonicalName();
    private static List<AppInfo> appInfoList;

    private static List<AppInfo> parseIconThemeFromXml() {
        List<AppInfo> appInfoList2 = new ArrayList<>();
        AppInfo appInfo = null;
        XmlResourceParser xmlParser = ThemeUtils.getInstance().getXml("icon_theme");
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
                                appInfo.setHidden("true".equals(hidden));
                            }
                            break;
                        case 3:
                            if (xmlParser.getName().equals("app") && !isTrimEmpty(appInfo.getPackageName())) {
                                appInfoList2.add(appInfo);
                            }
                            break;
                        case 4:
                            String iconName = xmlParser.getText();
                            appInfo.setIconName(iconName == null ? "" : iconName.trim());
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

    public static boolean isHidden(String packageName, String className) {
        if (isTrimEmpty(packageName)) {
            return true;
        }
        if (appInfoList == null) {
            appInfoList = parseIconThemeFromXml();
        }
        if (isOverseas() && "com.hcn.autoupdate".equals(packageName)) {
            return true;
        }
        for (int i = 0; i < appInfoList.size(); i++) {
            AppInfo appInfo = appInfoList.get(i);
            if (packageName.equals(appInfo.packageName) && (isTrimEmpty(appInfo.className) || appInfo.className.equals(className))) {
                return appInfo.hidden;
            }
        }
        return false;
    }

    public static Bitmap getCustomAppIcon(String packageName, String className) {
        if (isTrimEmpty(packageName)) {
            return null;
        }
        if (appInfoList == null) {
            appInfoList = parseIconThemeFromXml();
        }
        for (int i = 0; i < appInfoList.size(); i++) {
            AppInfo appInfo = appInfoList.get(i);
            if (packageName.equals(appInfo.packageName) && ((isTrimEmpty(appInfo.className) || appInfo.className.equals(className)) && !isTrimEmpty(appInfo.iconName))) {
                return ThemeUtils.getInstance().getBitmap(appInfo.iconName, false);
            }
        }
        return null;
    }

    static boolean isTrimEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    static boolean isOverseas() {
        return HConfig.isOverseaVersion();
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
