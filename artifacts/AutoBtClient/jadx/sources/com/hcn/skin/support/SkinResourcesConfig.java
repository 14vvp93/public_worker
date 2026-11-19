package com.hcn.skin.support;

import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class SkinResourcesConfig {
    private static Class<? extends SkinCompatResources> sSkinResourcesClass;

    public static void setSkinResourcesClass(Class<? extends SkinCompatResources> cls) {
        sSkinResourcesClass = cls;
    }

    public static Class<? extends SkinCompatResources> getSkinResourcesClass() {
        return sSkinResourcesClass;
    }
}
