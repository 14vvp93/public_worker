package com.hcn.skin2.data;

import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skinx_config.HFeatureKeys;

/* JADX INFO: loaded from: classes.dex */
public class Skin2DataX {
    private static boolean mSupportChangedStatusBar = false;
    private static boolean mSupportNightXDecorProtocol = false;

    public static void init() {
        mSupportNightXDecorProtocol = SkinCompatResources.getInstance().getBoolean(HFeatureKeys.SUPPORT_DAY_NIGHT_FRAME_PROTOCOL);
        mSupportChangedStatusBar = SkinCompatResources.getInstance().getBoolean(HFeatureKeys.SUPPORT_DAY_NIGHT_FRAME_PROTOCOL);
    }

    public static boolean supportNightXDecorProtocol() {
        return mSupportNightXDecorProtocol;
    }

    public static boolean supportChangedStatusBar() {
        return mSupportChangedStatusBar;
    }
}
