package com.hcn.skin.share;

import android.content.Context;
import android.content.res.Resources;

/* JADX INFO: loaded from: classes.dex */
public interface ISkinCompatResources {
    Context getContext();

    Context getSkinContext();

    String getSkinPkgName();

    int getSkinResId(int i);

    int getSkinResId(int i, String str);

    int getSkinResId(Context context, int i);

    int getSkinResId(String str, String str2);

    Resources getSkinResources();
}
