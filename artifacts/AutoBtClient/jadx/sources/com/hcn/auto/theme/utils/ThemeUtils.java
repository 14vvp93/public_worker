package com.hcn.auto.theme.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.auto.theme.constants.ThemeInfo;
import com.hcn.skinx_night.NightSkinX;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/* JADX INFO: loaded from: classes.dex */
public class ThemeUtils {
    private static final String TAG = "com.hcn.auto.theme.utils.ThemeUtils";
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
        this.themePackageName = Utils.getSystemProperty(ThemeInfo.CONFIG_KEY_THEME, ThemeInfo.MD03_THEME_PACKAGE_NAME);
        this.skinPackageName = Utils.getSystemProperty(ThemeInfo.CONFIG_KEY_SKIN, ThemeInfo.MD03_THEME_PACKAGE_NAME);
        String defaultSystemProperty = getDefaultSystemProperty(ThemeInfo.CONFIG_KEY_THEME);
        this.themePackageNameFactory = defaultSystemProperty;
        if (defaultSystemProperty == null || SkinID.SKIN_NONE.equals(defaultSystemProperty)) {
            this.themePackageNameFactory = this.themePackageName;
        }
        String defaultSystemProperty2 = getDefaultSystemProperty(ThemeInfo.CONFIG_KEY_SKIN);
        this.skinPackageNameFactory = defaultSystemProperty2;
        if (defaultSystemProperty2 == null || SkinID.SKIN_NONE.equals(defaultSystemProperty2)) {
            this.skinPackageNameFactory = this.skinPackageName;
        }
        String str = TAG;
        Log.d(str, "current theme config : " + this.themePackageName + ", skin config : " + this.skinPackageName);
        Log.d(str, "factory theme config : " + this.themePackageNameFactory + ", skin config : " + this.skinPackageNameFactory);
    }

    private void loadTheme(Application application) {
        Context applicationContext = application.getApplicationContext();
        this.context = applicationContext;
        this.appResources = applicationContext.getResources();
        PackageManager packageManager = this.context.getPackageManager();
        try {
            this.themeResourcesFactory = packageManager.getResourcesForApplication(this.themePackageNameFactory);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "can not find this factory theme package : " + this.themePackageNameFactory, e);
        }
        try {
            this.skinResourcesFactory = packageManager.getResourcesForApplication(this.skinPackageNameFactory);
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "can not find this factory skin package : " + this.skinPackageNameFactory, e2);
        }
        try {
            this.themeResources = packageManager.getResourcesForApplication(this.themePackageName);
        } catch (PackageManager.NameNotFoundException e3) {
            Log.e(TAG, "can not find this theme package : " + this.themePackageName, e3);
            this.themeResources = this.themeResourcesFactory;
            this.themePackageName = this.themePackageNameFactory;
        }
        try {
            this.skinResources = packageManager.getResourcesForApplication(this.skinPackageName);
        } catch (PackageManager.NameNotFoundException e4) {
            Log.e(TAG, "can not find this skin package : " + this.skinPackageName, e4);
            this.skinResources = this.skinResourcesFactory;
            this.skinPackageName = this.skinPackageNameFactory;
        }
        try {
            this.mShareResources = packageManager.getResourcesForApplication(ThemeInfo.SHARE_RES_PACKAGE_NAME);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "can not find this share package: com.hcn.auto.share");
        }
        String str = TAG;
        Log.d(str, "loadTheme appResources : " + this.appResources + ", themeResourcesFactory : " + this.themeResourcesFactory + ", skinResourcesFactory : " + this.skinResourcesFactory);
        Log.d(str, "loadTheme themeResources : " + this.themeResources + ", skinResources : " + this.skinResources);
    }

    private String getDefaultSystemProperty(String property) {
        FileReader fileReader = null;
        try {
            try {
                File file = new File("/apd/xt_base.prop");
                if (file.exists()) {
                    fileReader = new FileReader(file);
                } else {
                    fileReader = new FileReader("/apd/custom.prop");
                }
                Properties properties = new Properties();
                properties.load(fileReader);
                String property2 = properties.getProperty(property);
                try {
                    fileReader.close();
                    return property2;
                } catch (IOException e) {
                    e.printStackTrace();
                    return property2;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return SkinID.SKIN_NONE;
            }
        } catch (Throwable th) {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public Bitmap getBitmap(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, NightSkinX.ResTypeName.DRAWABLE, this.skinPackageName)) != 0) {
            return BitmapFactory.decodeResource(this.skinResources, identifier2);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, NightSkinX.ResTypeName.DRAWABLE, this.skinPackageNameFactory)) != 0) {
            return BitmapFactory.decodeResource(this.skinResourcesFactory, identifier);
        }
        Resources resources = this.appResources;
        if (resources != null) {
            int identifier3 = resources.getIdentifier(name, NightSkinX.ResTypeName.DRAWABLE, this.appPackageName);
            if (identifier3 != 0) {
                return BitmapFactory.decodeResource(this.appResources, identifier3);
            }
            return null;
        }
        Log.e(TAG, "Theme error : appResources = null, getBitmap return null");
        return null;
    }

    public Bitmap getBitmap(String name) {
        return getBitmap(name, true);
    }

    public Drawable getDrawable(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, NightSkinX.ResTypeName.DRAWABLE, this.skinPackageName)) != 0) {
            return this.skinResources.getDrawable(identifier2, null);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, NightSkinX.ResTypeName.DRAWABLE, this.skinPackageNameFactory)) != 0) {
            return this.skinResourcesFactory.getDrawable(identifier, null);
        }
        Resources resources = this.appResources;
        if (resources != null) {
            int identifier3 = resources.getIdentifier(name, NightSkinX.ResTypeName.DRAWABLE, this.appPackageName);
            if (identifier3 != 0) {
                return this.appResources.getDrawable(identifier3, null);
            }
        } else {
            Log.e(TAG, "Theme error : appResources = null, getDrawable return null");
        }
        return null;
    }

    public Drawable getDrawable(String name) {
        return getDrawable(name, true);
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
        Resources resources = this.appResources;
        if (resources != null) {
            int identifier3 = resources.getIdentifier(name, "xml", this.appPackageName);
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

    public int getColor(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, NightSkinX.ResTypeName.COLOR, this.skinPackageName)) != 0) {
            return this.skinResources.getColor(identifier2, null);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, NightSkinX.ResTypeName.COLOR, this.skinPackageNameFactory)) != 0) {
            return this.skinResourcesFactory.getColor(identifier, null);
        }
        Resources resources = this.appResources;
        if (resources != null) {
            int identifier3 = resources.getIdentifier(name, NightSkinX.ResTypeName.COLOR, this.appPackageName);
            if (identifier3 != 0) {
                return this.appResources.getColor(identifier3, null);
            }
            return 0;
        }
        Log.e(TAG, "Theme error : appResources = null, getColor return 0");
        return 0;
    }

    public int getColor(String name) {
        return getColor(name, true);
    }

    public String getString(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, "string", this.skinPackageName)) != 0) {
            return this.skinResources.getString(identifier2);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, "string", this.skinPackageNameFactory)) != 0) {
            return this.skinResourcesFactory.getString(identifier);
        }
        Resources resources = this.appResources;
        if (resources != null) {
            int identifier3 = resources.getIdentifier(name, "string", this.appPackageName);
            if (identifier3 != 0) {
                return this.appResources.getString(identifier3);
            }
            return null;
        }
        Log.e(TAG, "Theme error : appResources = null, getString return null");
        return null;
    }

    public String getString(String name) {
        return getString(name, true);
    }

    public int getInteger(String name, boolean needFromFactory) {
        int identifier;
        int identifier2;
        if (getSkinResources() != null && (identifier2 = this.skinResources.getIdentifier(name, "integer", this.skinPackageName)) != 0) {
            return this.skinResources.getInteger(identifier2);
        }
        if (needFromFactory && getSkinResourcesFactory() != null && (identifier = this.skinResourcesFactory.getIdentifier(name, "integer", this.skinPackageNameFactory)) != 0) {
            return this.skinResourcesFactory.getInteger(identifier);
        }
        Resources resources = this.appResources;
        if (resources != null) {
            int identifier3 = resources.getIdentifier(name, "integer", this.appPackageName);
            if (identifier3 != 0) {
                return this.appResources.getInteger(identifier3);
            }
            return 0;
        }
        Log.e(TAG, "Theme error : appResources = null, getInteger return 0");
        return 0;
    }

    public int getInteger(String name) {
        return getInteger(name, true);
    }

    public String getThemePackageName() {
        return this.themePackageName;
    }

    public String getSkinPackageName() {
        return this.skinPackageName;
    }

    public String getThemePackageNameFactory() {
        return this.themePackageNameFactory;
    }

    public String getSkinPackageNameFactory() {
        return this.skinPackageNameFactory;
    }

    public Resources getThemeResources() {
        return this.themeResources;
    }

    public Resources getShareResources() {
        return this.mShareResources;
    }

    public Resources getSkinResources() {
        if (this.skinResources == null) {
            Log.e(TAG, "Theme error : skinResources = null");
        }
        return this.skinResources;
    }

    public Resources getThemeResourcesFactory() {
        return this.themeResourcesFactory;
    }

    public Resources getSkinResourcesFactory() {
        if (this.skinResourcesFactory == null) {
            Log.e(TAG, "Theme error : skinResourcesFactory = null");
        }
        return this.skinResourcesFactory;
    }

    @Deprecated
    public Drawable getAppWallpaper() {
        if (1 == Settings.System.getInt(this.context.getContentResolver(), "car_home_style", 0)) {
            return getDrawable("headrest_wallpaper");
        }
        return getDrawable("app_wallpaper");
    }

    public Drawable getAppBackground() {
        int identifier;
        Drawable drawable;
        return (getShareResources() == null || (identifier = this.mShareResources.getIdentifier("background", NightSkinX.ResTypeName.DRAWABLE, ThemeInfo.SHARE_RES_PACKAGE_NAME)) == 0 || (drawable = this.mShareResources.getDrawable(identifier, null)) == null) ? getAppWallpaper() : drawable;
    }
}
