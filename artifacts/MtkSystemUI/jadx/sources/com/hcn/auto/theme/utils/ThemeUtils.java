package com.hcn.auto.theme.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/* JADX INFO: loaded from: classes.dex */
public class ThemeUtils {
    private static final String TAG = ThemeUtils.class.getCanonicalName();
    private static volatile ThemeUtils sInstance;
    private final String CONFIG_CAR_HOME_STYLE = "car_home_style";
    private String appPackageName;
    private Resources appResources;
    private Context context;
    private Resources mShareResources;
    private String skinPackageName;
    private String skinPackageNameFactory;
    private Resources skinResources;
    private Resources skinResourcesFactory;
    private String themePackageName;
    private String themePackageNameFactory;
    private Resources themeResources;
    private Resources themeResourcesFactory;

    private ThemeUtils(Application application) {
        loadThemeConfig(application);
        loadTheme(application);
    }

    public static ThemeUtils getInstance() {
        return sInstance;
    }

    public static ThemeUtils init(Application application) {
        if (sInstance == null) {
            synchronized (ThemeUtils.class) {
                if (sInstance == null) {
                    sInstance = new ThemeUtils(application);
                }
            }
        }
        return sInstance;
    }

    private void loadThemeConfig(Application application) {
        this.appPackageName = application.getPackageName();
        this.themePackageName = Utils.getSystemProperty("persist.sys.launcher.theme.pkg", "com.hcn.auto.theme.md03");
        this.skinPackageName = Utils.getSystemProperty("persist.sys.launcher.skin.pkg", "com.hcn.auto.theme.md03");
        this.themePackageNameFactory = getDefaultSystemProperty("persist.sys.launcher.theme.pkg");
        if (this.themePackageNameFactory == null || "".equals(this.themePackageNameFactory)) {
            this.themePackageNameFactory = this.themePackageName;
        }
        this.skinPackageNameFactory = getDefaultSystemProperty("persist.sys.launcher.skin.pkg");
        if (this.skinPackageNameFactory == null || "".equals(this.skinPackageNameFactory)) {
            this.skinPackageNameFactory = this.skinPackageName;
        }
        Log.d(TAG, "current theme config : " + this.themePackageName + ", skin config : " + this.skinPackageName);
        Log.d(TAG, "factory theme config : " + this.themePackageNameFactory + ", skin config : " + this.skinPackageNameFactory);
    }

    private void loadTheme(Application application) {
        this.context = application.getApplicationContext();
        this.appResources = this.context.getResources();
        PackageManager pm = this.context.getPackageManager();
        try {
            this.themeResourcesFactory = pm.getResourcesForApplication(this.themePackageNameFactory);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "can not find this factory theme package : " + this.themePackageNameFactory, e);
        }
        try {
            this.skinResourcesFactory = pm.getResourcesForApplication(this.skinPackageNameFactory);
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "can not find this factory skin package : " + this.skinPackageNameFactory, e2);
        }
        try {
            this.themeResources = pm.getResourcesForApplication(this.themePackageName);
        } catch (PackageManager.NameNotFoundException e3) {
            Log.e(TAG, "can not find this theme package : " + this.themePackageName, e3);
            this.themeResources = this.themeResourcesFactory;
            this.themePackageName = this.themePackageNameFactory;
        }
        try {
            this.skinResources = pm.getResourcesForApplication(this.skinPackageName);
        } catch (PackageManager.NameNotFoundException e4) {
            Log.e(TAG, "can not find this skin package : " + this.skinPackageName, e4);
            this.skinResources = this.skinResourcesFactory;
            this.skinPackageName = this.skinPackageNameFactory;
        }
        try {
            this.mShareResources = pm.getResourcesForApplication("com.hcn.auto.share");
        } catch (PackageManager.NameNotFoundException e5) {
            Log.w(TAG, "can not find this share package: com.hcn.auto.share");
        }
        Log.d(TAG, "loadTheme appResources : " + this.appResources + ", themeResourcesFactory : " + this.themeResourcesFactory + ", skinResourcesFactory : " + this.skinResourcesFactory);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("loadTheme themeResources : ");
        sb.append(this.themeResources);
        sb.append(", skinResources : ");
        sb.append(this.skinResources);
        Log.d(str, sb.toString());
    }

    private String getDefaultSystemProperty(String property) {
        String value = "";
        Reader reader = null;
        try {
            try {
                try {
                    File file = new File("/apd/xt_base.prop");
                    reader = file.exists() ? new FileReader(file) : new FileReader("/apd/custom.prop");
                    Properties pro = new Properties();
                    pro.load(reader);
                    value = pro.getProperty(property);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                    return value;
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return value;
    }

    public Bitmap getBitmap(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, "drawable", this.skinPackageName)) != 0) {
            return BitmapFactory.decodeResource(this.skinResources, identifier2);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, "drawable", this.skinPackageNameFactory)) != 0) {
            return BitmapFactory.decodeResource(this.skinResourcesFactory, identifier);
        }
        if (this.appResources != null) {
            int identifier3 = this.appResources.getIdentifier(name, "drawable", this.appPackageName);
            if (identifier3 != 0) {
                return BitmapFactory.decodeResource(this.appResources, identifier3);
            }
            return null;
        }
        Log.e(TAG, "Theme error : appResources = null, getBitmap return null");
        return null;
    }

    public XmlResourceParser getXml(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, "xml", this.skinPackageName)) != 0) {
            return this.skinResources.getXml(identifier2);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, "xml", this.skinPackageNameFactory)) != 0) {
            return this.skinResourcesFactory.getXml(identifier);
        }
        if (this.appResources != null) {
            int identifier3 = this.appResources.getIdentifier(name, "xml", this.appPackageName);
            if (identifier3 != 0) {
                return this.appResources.getXml(identifier3);
            }
            return null;
        }
        Log.e(TAG, "Theme error : appResources = null, getXml return null");
        return null;
    }

    public XmlResourceParser getXml(String name) {
        return getXml(name, true);
    }

    public Resources getSkinResources() {
        if (this.skinResources == null) {
            Log.e(TAG, "Theme error : skinResources = null");
        }
        return this.skinResources;
    }

    public Resources getSkinResourcesFactory() {
        if (this.skinResourcesFactory == null) {
            Log.e(TAG, "Theme error : skinResourcesFactory = null");
        }
        return this.skinResourcesFactory;
    }
}
