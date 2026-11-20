package com.hcn.skin.support.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class SkinSharedPreferences {
    private static final String FILE_NAME = "meta-data";
    private static final String KEY_SKIN_NAME = "skin-name";
    private static SkinSharedPreferences sInstance;
    private final Context mContext;
    private final SharedPreferences.Editor mEditor;
    private final SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (SkinSharedPreferences.class) {
                if (sInstance == null) {
                    sInstance = new SkinSharedPreferences(context.getApplicationContext());
                }
            }
        }
    }

    public static SkinSharedPreferences getInstance() {
        return sInstance;
    }

    private SkinSharedPreferences(Context context) {
        this.mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        this.mSharedPreferences = sharedPreferences;
        this.mEditor = sharedPreferences.edit();
    }

    public SkinSharedPreferences setSkinName(String str) {
        this.mEditor.putString(KEY_SKIN_NAME, str);
        return this;
    }

    public String getSkinName() {
        return this.mSharedPreferences.getString(KEY_SKIN_NAME, "");
    }

    public void commitEditor() {
        this.mEditor.apply();
    }
}
