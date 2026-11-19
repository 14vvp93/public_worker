package com.hcn.widgets.dynamicrecyclerview.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ApplySharedPref", "NewApi"})
public final class SPUtils {
    private static final Map<String, SPUtils> SP_UTILS_MAP = new HashMap();
    private SharedPreferences sp;

    public void put(String key, String value) {
        put(key, value, false);
    }

    public void put(String key, String value, boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putString(key, value).commit();
        } else {
            this.sp.edit().putString(key, value).apply();
        }
    }

    public String getString(String key, String defaultValue) {
        return this.sp.getString(key, defaultValue);
    }
}
