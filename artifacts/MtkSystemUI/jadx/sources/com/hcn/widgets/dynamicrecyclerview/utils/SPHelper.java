package com.hcn.widgets.dynamicrecyclerview.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SPHelper {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public SPHelper(Context context, String FILE_NAME) {
        this.sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        this.editor = this.sharedPreferences.edit();
    }

    public SharedPreferences.Editor put(String key, Object object) {
        if (object instanceof String) {
            this.editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            this.editor.putInt(key, ((Integer) object).intValue());
        } else if (object instanceof Boolean) {
            this.editor.putBoolean(key, ((Boolean) object).booleanValue());
        } else if (object instanceof Float) {
            this.editor.putFloat(key, ((Float) object).floatValue());
        } else if (object instanceof Long) {
            this.editor.putLong(key, ((Long) object).longValue());
        } else {
            this.editor.putString(key, object.toString());
        }
        return this.editor;
    }

    public SharedPreferences.Editor remove(String key) {
        this.editor.remove(key);
        return this.editor;
    }

    public SharedPreferences.Editor clear() {
        this.editor.clear();
        return this.editor;
    }

    public void apply() {
        this.editor.apply();
    }

    public Map<String, ?> getAll() {
        return this.sharedPreferences.getAll();
    }
}
