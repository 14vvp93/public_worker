package com.hcn.widgets.dynamicrecyclerview.model;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class AppIconData {
    private Drawable mAppIcon;
    private String mAppName;
    private ComponentName mComponentName;

    public AppIconData(String appName, ComponentName componentName, Drawable appIcon) {
        this.mAppName = appName;
        this.mComponentName = componentName;
        this.mAppIcon = appIcon;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Drawable getAppIcon() {
        return this.mAppIcon;
    }
}
