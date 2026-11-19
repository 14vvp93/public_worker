package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.qs.tileimpl.QSFactoryImpl;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class QSTileHost implements PluginListener<QSFactory>, QSHost, TunerService.Tunable {
    private static final boolean DEBUG = Log.isLoggable("QSTileHost", 3);
    private final AutoTileManager mAutoTiles;
    private final Context mContext;
    private int mCurrentUser;
    private final StatusBarIconController mIconController;
    private IQuickSettingsPlugin mQuickSettingsExt;
    private final StatusBar mStatusBar;
    private final LinkedHashMap<String, QSTile> mTiles = new LinkedHashMap<>();
    protected final ArrayList<String> mTileSpecs = new ArrayList<>();
    private final List<QSHost.Callback> mCallbacks = new ArrayList();
    private final ArrayList<QSFactory> mQsFactories = new ArrayList<>();
    private final TileServices mServices = new TileServices(this, (Looper) Dependency.get(Dependency.BG_LOOPER));

    public QSTileHost(Context context, StatusBar statusBar, StatusBarIconController iconController) {
        this.mQuickSettingsExt = null;
        this.mIconController = iconController;
        this.mContext = context;
        this.mStatusBar = statusBar;
        this.mQuickSettingsExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeQuickSettings(context);
        this.mQsFactories.add(new QSFactoryImpl(this));
        ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener((PluginListener) this, QSFactory.class, true);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "sysui_qs_tiles");
        this.mAutoTiles = new AutoTileManager(context, this);
    }

    public StatusBarIconController getIconController() {
        return this.mIconController;
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(QSFactory plugin, Context pluginContext) {
        this.mQsFactories.add(0, plugin);
        String value = ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(QSFactory plugin) {
        this.mQsFactories.remove(plugin);
        String value = ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    public void addCallback(QSHost.Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void removeCallback(QSHost.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public Collection<QSTile> getTiles() {
        return this.mTiles.values();
    }

    @Override // com.android.systemui.qs.QSHost
    public void warn(String message, Throwable t) {
    }

    @Override // com.android.systemui.qs.QSHost
    public void collapsePanels() {
        this.mStatusBar.postAnimateCollapsePanels();
    }

    public void forceCollapsePanels() {
        this.mStatusBar.postAnimateForceCollapsePanels();
    }

    @Override // com.android.systemui.qs.QSHost
    public void openPanels() {
        this.mStatusBar.postAnimateOpenPanels();
    }

    @Override // com.android.systemui.qs.QSHost
    public Context getContext() {
        return this.mContext;
    }

    @Override // com.android.systemui.qs.QSHost
    public TileServices getTileServices() {
        return this.mServices;
    }

    @Override // com.android.systemui.qs.QSHost
    public int indexOf(String spec) {
        return this.mTileSpecs.indexOf(spec);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if (!"sysui_qs_tiles".equals(key)) {
            return;
        }
        if (DEBUG) {
            Log.d("QSTileHost", "Recreating tiles");
        }
        if (newValue == null && UserManager.isDeviceInDemoMode(this.mContext)) {
            newValue = this.mContext.getResources().getString(R.string.quick_settings_tiles_retail_mode);
        }
        final List<String> tileSpecs = loadTileSpecs(this.mContext, newValue);
        int currentUser = ActivityManager.getCurrentUser();
        if (tileSpecs.equals(this.mTileSpecs) && currentUser == this.mCurrentUser) {
            return;
        }
        this.mTiles.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.qs.-$$Lambda$QSTileHost$yU-sQWBVZ_STOuSxrKNWIDPkScE
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return QSTileHost.lambda$onTuningChanged$1(tileSpecs, (Map.Entry) obj);
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.qs.-$$Lambda$QSTileHost$3Sm37Dy1JWpLe_hn88f-6UG0Q-M
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSTileHost.lambda$onTuningChanged$2((Map.Entry) obj);
            }
        });
        LinkedHashMap<String, QSTile> newTiles = new LinkedHashMap<>();
        for (String tileSpec : tileSpecs) {
            QSTile tile = this.mTiles.get(tileSpec);
            if (tile != null && (!(tile instanceof CustomTile) || ((CustomTile) tile).getUser() == currentUser)) {
                if (tile.isAvailable()) {
                    if (DEBUG) {
                        Log.d("QSTileHost", "Adding " + tile);
                    }
                    tile.removeCallbacks();
                    if (!(tile instanceof CustomTile) && this.mCurrentUser != currentUser) {
                        tile.userSwitch(currentUser);
                    }
                    newTiles.put(tileSpec, tile);
                } else {
                    tile.destroy();
                }
            } else {
                if (DEBUG) {
                    Log.d("QSTileHost", "Creating tile: " + tileSpec);
                }
                try {
                    QSTile tile2 = createTile(tileSpec);
                    if (tile2 != null) {
                        if (tile2.isAvailable()) {
                            tile2.setTileSpec(tileSpec);
                            newTiles.put(tileSpec, tile2);
                        } else {
                            tile2.destroy();
                        }
                    }
                } catch (Throwable t) {
                    Log.w("QSTileHost", "Error creating tile for spec: " + tileSpec, t);
                }
            }
        }
        this.mCurrentUser = currentUser;
        this.mTileSpecs.clear();
        this.mTileSpecs.addAll(tileSpecs);
        this.mTiles.clear();
        this.mTiles.putAll(newTiles);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onTilesChanged();
        }
    }

    static /* synthetic */ boolean lambda$onTuningChanged$1(List tileSpecs, Map.Entry tile) {
        return !tileSpecs.contains(tile.getKey());
    }

    static /* synthetic */ void lambda$onTuningChanged$2(Map.Entry tile) {
        if (DEBUG) {
            Log.d("QSTileHost", "Destroying tile: " + ((String) tile.getKey()));
        }
        ((QSTile) tile.getValue()).destroy();
    }

    public void addTile(String spec) {
        String setting = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_qs_tiles", ActivityManager.getCurrentUser());
        List<String> tileSpecs = loadTileSpecs(this.mContext, setting);
        if (tileSpecs.contains(spec)) {
            return;
        }
        tileSpecs.add(spec);
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_qs_tiles", TextUtils.join(",", tileSpecs), ActivityManager.getCurrentUser());
    }

    public void addTile(ComponentName tile) {
        List<String> newSpecs = new ArrayList<>(this.mTileSpecs);
        newSpecs.add(0, CustomTile.toSpec(tile));
        changeTiles(this.mTileSpecs, newSpecs);
    }

    public void removeTile(ComponentName tile) {
        List<String> newSpecs = new ArrayList<>(this.mTileSpecs);
        newSpecs.remove(CustomTile.toSpec(tile));
        changeTiles(this.mTileSpecs, newSpecs);
    }

    public void changeTiles(List<String> previousTiles, List<String> newTiles) {
        int NP = previousTiles.size();
        newTiles.size();
        for (int i = 0; i < NP; i++) {
            String tileSpec = previousTiles.get(i);
            if (tileSpec.startsWith("custom(") && !newTiles.contains(tileSpec)) {
                ComponentName component = CustomTile.getComponentFromSpec(tileSpec);
                Intent intent = new Intent().setComponent(component);
                TileLifecycleManager lifecycleManager = new TileLifecycleManager(new Handler(), this.mContext, this.mServices, new Tile(), intent, new UserHandle(ActivityManager.getCurrentUser()));
                lifecycleManager.onStopListening();
                lifecycleManager.onTileRemoved();
                TileLifecycleManager.setTileAdded(this.mContext, component, false);
                lifecycleManager.flushMessagesAndUnbind();
            }
        }
        if (DEBUG) {
            Log.d("QSTileHost", "saveCurrentTiles " + newTiles);
        }
        Settings.Secure.putStringForUser(getContext().getContentResolver(), "sysui_qs_tiles", TextUtils.join(",", newTiles), ActivityManager.getCurrentUser());
    }

    public QSTile createTile(String tileSpec) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTile t = this.mQsFactories.get(i).createTile(tileSpec);
            if (t != null) {
                return t;
            }
        }
        if (this.mQuickSettingsExt != null && this.mQuickSettingsExt.doOperatorSupportTile(tileSpec)) {
            return (QSTile) this.mQuickSettingsExt.createTile(this, tileSpec);
        }
        return null;
    }

    public QSTileView createTileView(QSTile tile, boolean collapsedView) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTileView view = this.mQsFactories.get(i).createTileView(tile, collapsedView);
            if (view != null) {
                return view;
            }
        }
        throw new RuntimeException("Default factory didn't create view for " + tile.getTileSpec());
    }

    protected List<String> loadTileSpecs(Context context, String tileList) {
        Resources res = context.getResources();
        String defaultTileList = res.getString(R.string.auto_quick_settings_tiles_default);
        if (this.mQuickSettingsExt != null) {
            defaultTileList = this.mQuickSettingsExt.customizeQuickSettingsTileOrder(this.mQuickSettingsExt.addOpTileSpecs(defaultTileList));
        }
        Log.d("QSTileHost", "loadTileSpecs() default tile list: " + defaultTileList);
        if (tileList == null) {
            tileList = res.getString(R.string.quick_settings_tiles);
            if (DEBUG) {
                Log.d("QSTileHost", "Loaded tile specs from config: " + tileList);
            }
        } else if (DEBUG) {
            Log.d("QSTileHost", "Loaded tile specs from setting: " + tileList);
        }
        ArrayList<String> tiles = new ArrayList<>();
        boolean addedDefault = false;
        for (String str : tileList.split(",")) {
            String tile = str.trim();
            if (!tile.isEmpty()) {
                if (tile.equals("default")) {
                    if (!addedDefault) {
                        tiles.addAll(Arrays.asList(defaultTileList.split(",")));
                        addedDefault = true;
                    }
                } else {
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }
}
