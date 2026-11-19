package com.android.systemui.qs.customize;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.ArraySet;
import android.widget.Button;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TileQueryHelper {
    private final Context mContext;
    private boolean mFinished;
    private final TileStateListener mListener;
    private final ArrayList<TileInfo> mTiles = new ArrayList<>();
    private final ArraySet<String> mSpecs = new ArraySet<>();
    private final Handler mBgHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
    private final Handler mMainHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);

    public static class TileInfo {
        public boolean isSystem;
        public String spec;
        public QSTile.State state;
    }

    public interface TileStateListener {
        void onTilesChanged(List<TileInfo> list);
    }

    public TileQueryHelper(Context context, TileStateListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void queryTiles(QSTileHost host) {
        this.mTiles.clear();
        this.mSpecs.clear();
        this.mFinished = false;
        addStockTiles(host);
        addPackageTiles(host);
    }

    public boolean isFinished() {
        return this.mFinished;
    }

    private void addStockTiles(QSTileHost host) {
        String possible = this.mContext.getString(R.string.auto_quick_settings_tiles_stock);
        IQuickSettingsPlugin quickSettingsPlugin = OpSystemUICustomizationFactoryBase.getOpFactory(this.mContext).makeQuickSettings(this.mContext);
        String possible2 = quickSettingsPlugin.customizeQuickSettingsTileOrder(possible);
        ArrayList<String> possibleTiles = new ArrayList<>();
        possibleTiles.addAll(Arrays.asList(possible2.split(",")));
        if (Build.IS_DEBUGGABLE) {
            possibleTiles.add("dbg:mem");
        }
        final ArrayList<QSTile> tilesToAdd = new ArrayList<>();
        for (String spec : possibleTiles) {
            QSTile tile = host.createTile(spec);
            if (tile != null) {
                if (!tile.isAvailable()) {
                    tile.destroy();
                } else {
                    tile.setListening(this, true);
                    tile.clearState();
                    tile.refreshState();
                    tile.setListening(this, false);
                    tile.setTileSpec(spec);
                    tilesToAdd.add(tile);
                }
            }
        }
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.qs.customize.-$$Lambda$TileQueryHelper$pVNHAsbxeJK0zo0OnLB_L5xKe_E
            @Override // java.lang.Runnable
            public final void run() {
                TileQueryHelper.lambda$addStockTiles$0(this.f$0, tilesToAdd);
            }
        });
    }

    public static /* synthetic */ void lambda$addStockTiles$0(TileQueryHelper tileQueryHelper, ArrayList tilesToAdd) {
        Iterator it = tilesToAdd.iterator();
        while (it.hasNext()) {
            QSTile tile = (QSTile) it.next();
            QSTile.State state = tile.getState().copy();
            state.label = tile.getTileLabel();
            tile.destroy();
            tileQueryHelper.addTile(tile.getTileSpec(), (CharSequence) null, state, true);
        }
        tileQueryHelper.notifyTilesChanged(false);
    }

    private void addPackageTiles(final QSTileHost host) {
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.qs.customize.-$$Lambda$TileQueryHelper$-7aqDrq4N73id-i9gI_WE72bklw
            @Override // java.lang.Runnable
            public final void run() {
                TileQueryHelper.lambda$addPackageTiles$1(this.f$0, host);
            }
        });
    }

    public static /* synthetic */ void lambda$addPackageTiles$1(TileQueryHelper tileQueryHelper, QSTileHost host) {
        Collection<QSTile> params = host.getTiles();
        PackageManager pm = tileQueryHelper.mContext.getPackageManager();
        List<ResolveInfo> services = pm.queryIntentServicesAsUser(new Intent("android.service.quicksettings.action.QS_TILE"), 0, ActivityManager.getCurrentUser());
        String stockTiles = tileQueryHelper.mContext.getString(R.string.auto_quick_settings_tiles_stock);
        for (ResolveInfo info : services) {
            String packageName = info.serviceInfo.packageName;
            ComponentName componentName = new ComponentName(packageName, info.serviceInfo.name);
            if (!stockTiles.contains(componentName.flattenToString())) {
                CharSequence appLabel = info.serviceInfo.applicationInfo.loadLabel(pm);
                String spec = CustomTile.toSpec(componentName);
                QSTile.State state = tileQueryHelper.getState(params, spec);
                if (state != null) {
                    tileQueryHelper.addTile(spec, appLabel, state, false);
                } else if (info.serviceInfo.icon != 0 || info.serviceInfo.applicationInfo.icon != 0) {
                    Drawable icon = info.serviceInfo.loadIcon(pm);
                    if ("android.permission.BIND_QUICK_SETTINGS_TILE".equals(info.serviceInfo.permission) && icon != null) {
                        icon.mutate();
                        icon.setTint(tileQueryHelper.mContext.getColor(android.R.color.white));
                        CharSequence label = info.serviceInfo.loadLabel(pm);
                        tileQueryHelper.addTile(spec, icon, label != null ? label.toString() : "null", appLabel);
                    }
                }
            }
        }
        tileQueryHelper.notifyTilesChanged(true);
    }

    private void notifyTilesChanged(final boolean finished) {
        final ArrayList<TileInfo> tilesToReturn = new ArrayList<>(this.mTiles);
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.customize.-$$Lambda$TileQueryHelper$td1yVFso44MefBPUi6jpDHx3Yoc
            @Override // java.lang.Runnable
            public final void run() {
                TileQueryHelper.lambda$notifyTilesChanged$2(this.f$0, tilesToReturn, finished);
            }
        });
    }

    public static /* synthetic */ void lambda$notifyTilesChanged$2(TileQueryHelper tileQueryHelper, ArrayList tilesToReturn, boolean finished) {
        tileQueryHelper.mListener.onTilesChanged(tilesToReturn);
        tileQueryHelper.mFinished = finished;
    }

    private QSTile.State getState(Collection<QSTile> tiles, String spec) {
        for (QSTile tile : tiles) {
            if (spec.equals(tile.getTileSpec())) {
                return tile.getState().copy();
            }
        }
        return null;
    }

    private void addTile(String spec, CharSequence appLabel, QSTile.State state, boolean isSystem) {
        if (this.mSpecs.contains(spec)) {
            return;
        }
        TileInfo info = new TileInfo();
        info.state = state;
        info.state.dualTarget = false;
        info.state.expandedAccessibilityClassName = Button.class.getName();
        info.spec = spec;
        info.state.secondaryLabel = (isSystem || TextUtils.equals(state.label, appLabel)) ? null : appLabel;
        info.isSystem = isSystem;
        this.mTiles.add(info);
        this.mSpecs.add(spec);
    }

    private void addTile(String spec, Drawable drawable, CharSequence label, CharSequence appLabel) {
        QSTile.State state = new QSTile.State();
        state.label = label;
        state.contentDescription = label;
        state.icon = new QSTileImpl.DrawableIcon(drawable);
        addTile(spec, appLabel, state, false);
    }
}
