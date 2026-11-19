package com.hcn.auto.theme.utils;

import android.Configures.HConfig;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.util.Log;
import com.autochips.bluetooth.skin.SkinID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class IconThemeUtils {
    private static final String TAG = "com.hcn.auto.theme.utils.IconThemeUtils";
    private static List<AppInfo> appInfoList;

    private static List<AppInfo> parseIconThemeFromXml() {
        ArrayList arrayList = new ArrayList();
        XmlResourceParser xml = ThemeUtils.getInstance().getXml("icon_theme");
        try {
            AppInfo appInfo = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType != 2) {
                    if (eventType != 3) {
                        if (eventType == 4) {
                            String text = xml.getText();
                            appInfo.setIconName(text == null ? SkinID.SKIN_NONE : text.trim());
                        } else {
                            continue;
                        }
                    } else if (xml.getName().equals("app") && !isTrimEmpty(appInfo.getPackageName())) {
                        arrayList.add(appInfo);
                    }
                } else if (xml.getName().equals("app")) {
                    String attributeValue = xml.getAttributeValue(null, "packageName");
                    String attributeValue2 = xml.getAttributeValue(null, "className");
                    String attributeValue3 = xml.getAttributeValue(null, "hidden");
                    AppInfo appInfo2 = new AppInfo();
                    appInfo2.setPackageName(attributeValue == null ? SkinID.SKIN_NONE : attributeValue.trim());
                    appInfo2.setClassName(attributeValue2 == null ? SkinID.SKIN_NONE : attributeValue2.trim());
                    appInfo2.setHidden("true".equals(attributeValue3));
                    appInfo = appInfo2;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, SkinID.SKIN_NONE, e);
        } catch (XmlPullParserException e2) {
            Log.e(TAG, SkinID.SKIN_NONE, e2);
        } catch (Exception e3) {
            Log.e(TAG, SkinID.SKIN_NONE, e3);
        }
        return arrayList;
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

    public static String getCustomAppIconName(String packageName, String className) {
        if (isTrimEmpty(packageName)) {
            return null;
        }
        if (appInfoList == null) {
            appInfoList = parseIconThemeFromXml();
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

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getIconName() {
            return this.iconName;
        }

        public void setIconName(String iconName) {
            this.iconName = iconName;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public String toString() {
            return "AppInfo{packageName='" + this.packageName + "', className='" + this.className + "', iconName='" + this.iconName + "', hidden='" + this.hidden + "'}";
        }
    }
}
