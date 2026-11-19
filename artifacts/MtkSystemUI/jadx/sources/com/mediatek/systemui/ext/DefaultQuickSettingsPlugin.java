package com.mediatek.systemui.ext;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.mediatek.systemui.statusbar.extcb.IconIdWrapper;

/* JADX INFO: loaded from: classes.dex */
public class DefaultQuickSettingsPlugin extends ContextWrapper implements IQuickSettingsPlugin {
    private static final String TAG = "DefaultQuickSettingsPlugin";
    protected Context mContext;

    public DefaultQuickSettingsPlugin(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public boolean customizeDisplayDataUsage(boolean isDisplay) {
        Log.i(TAG, "customizeDisplayDataUsage, return isDisplay = " + isDisplay);
        return isDisplay;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public String customizeQuickSettingsTileOrder(String defaultString) {
        return defaultString;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public Object customizeAddQSTile(Object qsTile) {
        return null;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public String customizeDataConnectionTile(int dataState, IconIdWrapper icon, String orgLabelStr) {
        Log.i(TAG, "customizeDataConnectionTile, icon = " + icon + ", orgLabelStr=" + orgLabelStr);
        return orgLabelStr;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public String customizeDualSimSettingsTile(boolean enable, IconIdWrapper icon, String labelStr) {
        Log.i(TAG, "customizeDualSimSettingsTile, enable = " + enable + " icon=" + icon + " labelStr=" + labelStr);
        return labelStr;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void customizeSimDataConnectionTile(int state, IconIdWrapper icon) {
        Log.i(TAG, "customizeSimDataConnectionTile, state = " + state + " icon=" + icon);
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void disableDataForOtherSubscriptions() {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public String customizeApnSettingsTile(boolean enable, IconIdWrapper icon, String orgLabelStr) {
        return orgLabelStr;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public String addOpTileSpecs(String defaultTileList) {
        return defaultTileList;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public boolean doOperatorSupportTile(String tileSpec) {
        return false;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public Object createTile(Object host, String tileSpec) {
        return null;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void addOpViews(ViewGroup vg) {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void registerCallbacks() {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void unregisterCallbacks() {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void setViewsVisibility(int visibility) {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void measureOpViews(int width) {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public View getPreviousView(View v) {
        return v;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public int getOpViewsHeight() {
        return 0;
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void setOpViewsLayout(int aboveViewHeight) {
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public String getTileLabel(String tile) {
        return "";
    }

    @Override // com.mediatek.systemui.ext.IQuickSettingsPlugin
    public void setHostAppInstance(Object o) {
    }
}
