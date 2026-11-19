package com.hcn.skin.support.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class SkinSharedPreferences {
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

    private SkinSharedPreferences(Context applicationContext) {
        this.mContext = applicationContext;
        this.mSharedPreferences = this.mContext.getSharedPreferences("meta-data", 0);
        this.mEditor = this.mSharedPreferences.edit();
    }

    public SkinSharedPreferences setSkinName(String skinName) {
        this.mEditor.putString("skin-name", skinName);
        return this;
    }

    public void commitEditor() {
        this.mEditor.apply();
    }
}
