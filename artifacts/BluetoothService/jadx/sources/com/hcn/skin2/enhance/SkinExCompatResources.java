package com.hcn.skin2.enhance;

import android.content.res.Resources;
import android.text.TextUtils;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin2.runtime.IllegalUseException;
import com.hcn.skin2.runtime.RRO;
import com.hcn.skinx_night.NightSkinX;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SkinExCompatResources extends SkinCompatResources {
    private static final boolean DEBUG = HUtilsEx.isAppDebug();
    private static final String TAG = "SkinExCompatResources";

    public SkinExCompatResources() {
        LogUtils.dTag(TAG, "Constructor.");
    }

    public static void asyncLoadSupportEnable() {
        LogUtils.dTag(TAG, "asyncLoadSkinEnable.");
        NightSkinX.setSkinCompatResources(SkinCompatResources.getInstance());
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public String getString(int i) {
        CharSequence text = getText(i);
        if (Objects.isNull(text)) {
            return null;
        }
        return text.toString();
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public String getString(int i, boolean z) {
        if (!z) {
            return getString(i);
        }
        if (Objects.isNull(getSkinResources())) {
            return null;
        }
        if (RRO.instance().isEnabled()) {
            try {
                String resourceEntryName = getSkinResources().getResourceEntryName(i);
                if (!TextUtils.isEmpty(resourceEntryName)) {
                    int appResId = getAppResId(resourceEntryName, "string");
                    if (appResId > 0) {
                        return SkinCompatManager.getInstance().getContext().getResources().getString(appResId);
                    }
                    LogUtils.wTag(TAG, "[S2]This resource[" + resourceEntryName + "] is not defined in the main program and cannot be replaced through RRO.");
                } else {
                    throw new IllegalUseException("method 'getString(int, boolean)' prohibits external calls.");
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        return getSkinResources().getString(i);
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public String getString(String str) {
        CharSequence text = getText(str);
        if (Objects.isNull(text)) {
            return null;
        }
        return text.toString();
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public CharSequence getText(int i) {
        if (RRO.instance().isEnabled()) {
            try {
                return SkinCompatManager.getInstance().getContext().getResources().getText(i);
            } catch (Resources.NotFoundException unused) {
                LogUtils.wTag(TAG, "[S1]This resource is not defined in the main program and cannot be replaced through RRO.");
                try {
                    Resources skinResources = getSkinResources();
                    if (Objects.isNull(skinResources)) {
                        return null;
                    }
                    return skinResources.getText(i);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    LogUtils.m46e(TAG, "The parameters of method 'getString(int)' are not supported.");
                    return null;
                }
            }
        }
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return getSkinResources().getText(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getText(i);
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public CharSequence getText(String str) {
        if (RRO.instance().isEnabled()) {
            int appResId = getAppResId(str, "string");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getText(appResId);
            }
            LogUtils.wTag(TAG, "[S3]This resource is not defined in the main program and cannot be replaced through RRO.");
            int skinResId = getSkinResId(str, "string");
            if (skinResId > 0) {
                return getSkinResources().getText(skinResId);
            }
            LogUtils.wTag(TAG, "[S3]This resource '" + str + "' definition was not found.");
            return null;
        }
        try {
            int skinResId2 = getSkinResId(str, "string");
            if (skinResId2 > 0) {
                return getSkinResources().getText(skinResId2);
            }
            int appResId2 = getAppResId(str, "string");
            if (appResId2 > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getText(appResId2);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public String[] getStringArray(int i) {
        if (RRO.instance().isEnabled()) {
            try {
                return SkinCompatManager.getInstance().getContext().getResources().getStringArray(i);
            } catch (Resources.NotFoundException unused) {
                LogUtils.wTag(TAG, "[SA1]This resource is not defined in the main program and cannot be replaced through RRO.");
                try {
                    Resources skinResources = getSkinResources();
                    if (Objects.isNull(skinResources)) {
                        return null;
                    }
                    return skinResources.getStringArray(i);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    LogUtils.m46e(TAG, "The parameters of method 'getString(int)' are not supported.");
                    return null;
                }
            }
        }
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return getSkinResources().getStringArray(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getStringArray(i);
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.hcn.skin.support.resources.SkinCompatResources
    public int[] getIntArray(int i) {
        if (RRO.instance().isEnabled()) {
            try {
                return SkinCompatManager.getInstance().getContext().getResources().getIntArray(i);
            } catch (Resources.NotFoundException unused) {
                LogUtils.wTag(TAG, "[SA1]This resource is not defined in the main program and cannot be replaced through RRO.");
                try {
                    Resources skinResources = getSkinResources();
                    if (Objects.isNull(skinResources)) {
                        return null;
                    }
                    return skinResources.getIntArray(i);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    LogUtils.m46e(TAG, "The parameters of method 'getString(int)' are not supported.");
                    return null;
                }
            }
        }
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return getSkinResources().getIntArray(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getIntArray(i);
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
