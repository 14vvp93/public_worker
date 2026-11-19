package com.mediatek.systemui.ext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class DefaultSystemUIStatusBarExt implements ISystemUIStatusBarExt {
    public DefaultSystemUIStatusBarExt(Context context) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean disableHostFunction() {
        return false;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean checkIfSlotIdChanged(int subId, int slotId) {
        return false;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void isDataDisabled(int subId, boolean dataDisabled) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void getServiceStateForCustomizedView(int subId) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getCustomizeCsState(ServiceState serviceState, int state) {
        return state;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean isInCsCall() {
        return false;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getNetworkTypeIcon(int subId, int iconId, int networkType, ServiceState serviceState) {
        return iconId;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getDataTypeIcon(int subId, int iconId, int dataType, int dataState, ServiceState serviceState) {
        return iconId;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getCustomizeSignalStrengthIcon(int subId, int iconId, SignalStrength signalStrength, int networkType, ServiceState serviceState) {
        return iconId;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getCustomizeSignalStrengthLevel(int signalLevel, SignalStrength signalStrength, ServiceState serviceState) {
        return signalLevel;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void addCustomizedView(int subId, Context context, ViewGroup root) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void addSignalClusterCustomizedView(Context context, ViewGroup root, int index) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedNetworkTypeView(int subId, int networkTypeId, ImageView networkTypeView) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedDataTypeView(int subId, int dataTypeId, boolean dataIn, boolean dataOut) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedMobileTypeView(int subId, ImageView mobileTypeView) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedSignalStrengthView(int subId, int signalStrengthId, ImageView signalStrengthView) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getCommonSignalIconId(int mobileSignalIconId) {
        return mobileSignalIconId;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void SetHostViewInvisible(ImageView view) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setImsRegInfo(int subId, int imsRegState, boolean isOverwfc, boolean isImsOverVoice) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setDisVolteView(int subId, int iconId, ImageView volteView) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedView(int subId) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedNoSimView(boolean noSimsVisible) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedNoSimView(ImageView noSimView) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedVolteView(int iconId, ImageView volteView) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedAirplaneView(View noSimView, boolean airplaneMode) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedNoSimsVisible(boolean noSimsVisible) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean updateSignalStrengthWifiOnlyMode(ServiceState serviceState, boolean connected) {
        return connected;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void registerOpStateListener() {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setIconTint(int tint, float darkIntensity) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setIconTint(ColorStateList tint) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setNoSimIconTint(int tint, ImageView nosim) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setSimInserted(int slotId, boolean insert) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setImsSlotId(int slotId) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void setCustomizedPlmnTextTint(int tint) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean handleCallStateChanged(int subId, int state, String incomingNumber, ServiceState serviceState) {
        return false;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean needShowRoamingIcons(boolean isInRoaming) {
        return true;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public int getClockAmPmStyle(int defaultAmPmStyle) {
        return defaultAmPmStyle;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public boolean needShowWfcIcon() {
        return true;
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public void addCallback(ISystemUIStatusBarExt.StatusBarCallback cb) {
    }

    @Override // com.mediatek.systemui.ext.ISystemUIStatusBarExt
    public String[] addSlot(String[] slots) {
        return slots;
    }
}
