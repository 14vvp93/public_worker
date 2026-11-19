package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.os.SystemProperties;
import android.util.Log;
import android.view.ContextThemeWrapper;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import com.android.systemui.qs.tiles.BluetoothTile;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.qs.tiles.CellularTile;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.EQTile;
import com.android.systemui.qs.tiles.IntentTile;
import com.android.systemui.qs.tiles.LocationTile;
import com.android.systemui.qs.tiles.MemoryCleanerTile;
import com.android.systemui.qs.tiles.MenuTile;
import com.android.systemui.qs.tiles.MuteTile;
import com.android.systemui.qs.tiles.RebootTile;
import com.android.systemui.qs.tiles.RotationLockTile;
import com.android.systemui.qs.tiles.ScreenOffTile;
import com.android.systemui.qs.tiles.ScreenSaverTile;
import com.android.systemui.qs.tiles.ScreenshotTile;
import com.android.systemui.qs.tiles.SettingsTile;
import com.android.systemui.qs.tiles.SuspensionTile;
import com.android.systemui.qs.tiles.WifiNewTile;
import com.android.systemui.qs.tiles.WifiTile;
import com.android.systemui.util.ExternalParameterManager;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.qs.tiles.ext.ApnSettingsTile;
import com.mediatek.systemui.qs.tiles.ext.DualSimSettingsTile;
import com.mediatek.systemui.qs.tiles.ext.MobileDataTile;
import com.mediatek.systemui.qs.tiles.ext.SimDataConnectionTile;
import com.mediatek.systemui.statusbar.util.SIMHelper;

/* JADX INFO: loaded from: classes.dex */
public class QSFactoryImpl implements QSFactory {
    private final QSTileHost mHost;

    public QSFactoryImpl(QSTileHost host) {
        this.mHost = host;
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public QSTile createTile(String tileSpec) {
        QSTileImpl tile = createTileInternal(tileSpec);
        if (tile != null) {
            tile.handleStale();
        }
        return tile;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:59:0x00e1  */
    private QSTileImpl createTileInternal(String tileSpec) {
        Context context = this.mHost.getContext();
        IQuickSettingsPlugin quickSettingsPlugin = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeQuickSettings(context);
        switch (tileSpec) {
            case "wifi":
                if (SystemProperties.getInt("hide_network_entrance", 0) != 1) {
                    if (ExternalParameterManager.ETHEME_GOD_400 == ExternalParameterManager.ETHEME_GOD && (ExternalParameterManager.ETHEME_SUB_16 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_22 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_37 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_65 == ExternalParameterManager.ETHEME_SUB)) {
                        return new WifiNewTile(this.mHost);
                    }
                    return new WifiTile(this.mHost);
                }
                break;
            case "bt":
                return new BluetoothTile(this.mHost);
            case "cell":
                return new CellularTile(this.mHost);
            case "dnd":
                return new DndTile(this.mHost);
            case "airplane":
                return new AirplaneModeTile(this.mHost);
            case "rotation":
                return new RotationLockTile(this.mHost);
            case "location":
                return new LocationTile(this.mHost);
            case "cast":
                return new CastTile(this.mHost);
            case "mute":
                return new MuteTile(this.mHost);
            case "reboot":
                return new RebootTile(this.mHost);
            case "screenshot":
                return new ScreenshotTile(this.mHost);
            case "screenoff":
                return new ScreenOffTile(this.mHost);
            case "memorycleaner":
                return new MemoryCleanerTile(this.mHost);
            case "eq":
                return new EQTile(this.mHost);
            case "screensaver":
                return new ScreenSaverTile(this.mHost);
            case "settings":
                return new SettingsTile(this.mHost);
            case "menu":
                return new MenuTile(this.mHost);
            case "suspension":
                return new SuspensionTile(this.mHost);
        }
        if (tileSpec.equals("dataconnection") && !SIMHelper.isWifiOnlyDevice()) {
            return new MobileDataTile(this.mHost);
        }
        if (tileSpec.equals("simdataconnection") && !SIMHelper.isWifiOnlyDevice() && quickSettingsPlugin.customizeAddQSTile(new SimDataConnectionTile(this.mHost)) != null) {
            return (SimDataConnectionTile) quickSettingsPlugin.customizeAddQSTile(new SimDataConnectionTile(this.mHost));
        }
        if (tileSpec.equals("dulsimsettings") && !SIMHelper.isWifiOnlyDevice() && quickSettingsPlugin.customizeAddQSTile(new DualSimSettingsTile(this.mHost)) != null) {
            return (DualSimSettingsTile) quickSettingsPlugin.customizeAddQSTile(new DualSimSettingsTile(this.mHost));
        }
        if (tileSpec.equals("apnsettings") && !SIMHelper.isWifiOnlyDevice() && quickSettingsPlugin.customizeAddQSTile(new ApnSettingsTile(this.mHost)) != null) {
            return (ApnSettingsTile) quickSettingsPlugin.customizeAddQSTile(new ApnSettingsTile(this.mHost));
        }
        if (tileSpec.startsWith("intent(")) {
            return IntentTile.create(this.mHost, tileSpec);
        }
        if (tileSpec.startsWith("custom(")) {
            return CustomTile.create(this.mHost, tileSpec);
        }
        Log.w("QSFactory", "Bad tile spec: " + tileSpec);
        return null;
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public com.android.systemui.plugins.qs.QSTileView createTileView(QSTile tile, boolean collapsedView) {
        Context context = new ContextThemeWrapper(this.mHost.getContext(), R.style.qs_theme);
        QSIconView icon = tile.createTileView(context);
        if (collapsedView) {
            return new QSTileBaseView(context, icon, collapsedView);
        }
        return new QSTileView(context, icon);
    }
}
