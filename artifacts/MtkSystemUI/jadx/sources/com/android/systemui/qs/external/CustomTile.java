package com.android.systemui.qs.external;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.quicksettings.IQSTileService;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import java.util.Objects;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class CustomTile extends QSTileImpl<QSTile.State> implements TileLifecycleManager.TileChangeListener {
    private final ComponentName mComponent;
    private Icon mDefaultIcon;
    private boolean mIsShowingDialog;
    private boolean mIsTokenGranted;
    private boolean mListening;
    private final IQSTileService mService;
    private final TileServiceManager mServiceManager;
    private final Tile mTile;
    private final IBinder mToken;
    private final int mUser;
    private final IWindowManager mWindowManager;

    private CustomTile(QSTileHost host, String action) {
        super(host);
        this.mToken = new Binder();
        this.mWindowManager = WindowManagerGlobal.getWindowManagerService();
        this.mComponent = ComponentName.unflattenFromString(action);
        this.mTile = new Tile();
        setTileIcon();
        this.mServiceManager = host.getTileServices().getTileWrapper(this);
        this.mService = this.mServiceManager.getTileService();
        this.mServiceManager.setTileChangeListener(this);
        this.mUser = ActivityManager.getCurrentUser();
        handleStale();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected long getStaleTimeout() {
        return 3600000 + (60000 * ((long) this.mHost.indexOf(getTileSpec())));
    }

    private void setTileIcon() {
        Icon iconCreateWithResource;
        try {
            PackageManager pm = this.mContext.getPackageManager();
            int flags = 786432;
            if (isSystemApp(pm)) {
                flags = 786432 | 512;
            }
            ServiceInfo info = pm.getServiceInfo(this.mComponent, flags);
            int icon = info.icon != 0 ? info.icon : info.applicationInfo.icon;
            boolean updateIcon = this.mTile.getIcon() == null || iconEquals(this.mTile.getIcon(), this.mDefaultIcon);
            if (icon == 0) {
                iconCreateWithResource = null;
            } else {
                iconCreateWithResource = Icon.createWithResource(this.mComponent.getPackageName(), icon);
            }
            this.mDefaultIcon = iconCreateWithResource;
            if (updateIcon) {
                this.mTile.setIcon(this.mDefaultIcon);
            }
            if (this.mTile.getLabel() == null) {
                this.mTile.setLabel(info.loadLabel(pm));
            }
        } catch (Exception e) {
            this.mDefaultIcon = null;
        }
    }

    private boolean isSystemApp(PackageManager pm) throws PackageManager.NameNotFoundException {
        return pm.getApplicationInfo(this.mComponent.getPackageName(), 0).isSystemApp();
    }

    private boolean iconEquals(Icon icon1, Icon icon2) {
        if (icon1 == icon2) {
            return true;
        }
        if (icon1 != null && icon2 != null && icon1.getType() == 2 && icon2.getType() == 2 && icon1.getResId() == icon2.getResId() && Objects.equals(icon1.getResPackage(), icon2.getResPackage())) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.qs.external.TileLifecycleManager.TileChangeListener
    public void onTileChanged(ComponentName tile) {
        setTileIcon();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mDefaultIcon != null;
    }

    public int getUser() {
        return this.mUser;
    }

    public ComponentName getComponent() {
        return this.mComponent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public LogMaker populate(LogMaker logMaker) {
        return super.populate(logMaker).setComponentName(this.mComponent);
    }

    public Tile getQsTile() {
        return this.mTile;
    }

    public void updateState(Tile tile) {
        this.mTile.setIcon(tile.getIcon());
        this.mTile.setLabel(tile.getLabel());
        this.mTile.setContentDescription(tile.getContentDescription());
        this.mTile.setState(tile.getState());
    }

    public void onDialogShown() {
        this.mIsShowingDialog = true;
    }

    public void onDialogHidden() {
        this.mIsShowingDialog = false;
        try {
            this.mWindowManager.removeWindowToken(this.mToken, 0);
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
        if (this.mListening == listening) {
            return;
        }
        this.mListening = listening;
        try {
            if (listening) {
                setTileIcon();
                refreshState();
                if (!this.mServiceManager.isActiveTile()) {
                    this.mServiceManager.setBindRequested(true);
                    this.mService.onStartListening();
                    return;
                }
                return;
            }
            this.mService.onStopListening();
            if (this.mIsTokenGranted && !this.mIsShowingDialog) {
                try {
                    this.mWindowManager.removeWindowToken(this.mToken, 0);
                } catch (RemoteException e) {
                }
                this.mIsTokenGranted = false;
            }
            this.mIsShowingDialog = false;
            this.mServiceManager.setBindRequested(false);
        } catch (RemoteException e2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleDestroy() {
        super.handleDestroy();
        if (this.mIsTokenGranted) {
            try {
                this.mWindowManager.removeWindowToken(this.mToken, 0);
            } catch (RemoteException e) {
            }
        }
        this.mHost.getTileServices().freeService(this, this.mServiceManager);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        return state;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        Intent i = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
        i.setPackage(this.mComponent.getPackageName());
        Intent i2 = resolveIntent(i);
        if (i2 != null) {
            i2.putExtra("android.intent.extra.COMPONENT_NAME", this.mComponent);
            i2.putExtra("state", this.mTile.getState());
            return i2;
        }
        return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", this.mComponent.getPackageName(), null));
    }

    private Intent resolveIntent(Intent i) {
        ResolveInfo result = this.mContext.getPackageManager().resolveActivityAsUser(i, 0, ActivityManager.getCurrentUser());
        if (result != null) {
            return new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES").setClassName(result.activityInfo.packageName, result.activityInfo.name);
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        if (this.mTile.getState() == 0) {
            return;
        }
        try {
            this.mWindowManager.addWindowToken(this.mToken, 2035, 0);
            this.mIsTokenGranted = true;
        } catch (RemoteException e) {
        }
        try {
            if (this.mServiceManager.isActiveTile()) {
                this.mServiceManager.setBindRequested(true);
                this.mService.onStartListening();
            }
            this.mService.onClick(this.mToken);
        } catch (RemoteException e2) {
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return getState().label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleUpdateState(QSTile.State state, Object arg) {
        Drawable drawable;
        int tileState = this.mTile.getState();
        if (this.mServiceManager.hasPendingBind()) {
            tileState = 0;
        }
        state.state = tileState;
        try {
            drawable = this.mTile.getIcon().loadDrawable(this.mContext);
        } catch (Exception e) {
            Log.w(this.TAG, "Invalid icon, forcing into unavailable state");
            state.state = 0;
            drawable = this.mDefaultIcon.loadDrawable(this.mContext);
        }
        final Drawable drawableF = drawable;
        state.iconSupplier = new Supplier() { // from class: com.android.systemui.qs.external.-$$Lambda$CustomTile$Oh-NzDEMM2yCWnVYbU2_DKTzaqo
            @Override // java.util.function.Supplier
            public final Object get() {
                return CustomTile.lambda$handleUpdateState$0(drawableF);
            }
        };
        state.label = this.mTile.getLabel();
        if (this.mTile.getContentDescription() != null) {
            state.contentDescription = this.mTile.getContentDescription();
        } else {
            state.contentDescription = state.label;
        }
    }

    static /* synthetic */ QSTile.Icon lambda$handleUpdateState$0(Drawable drawableF) {
        Drawable.ConstantState cs = drawableF.getConstantState();
        if (cs != null) {
            return new QSTileImpl.DrawableIcon(cs.newDrawable());
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 268;
    }

    public void startUnlockAndRun() {
        ((ActivityStarter) Dependency.get(ActivityStarter.class)).postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.external.-$$Lambda$CustomTile$q1MKWZaaapZOjYFe9CyeyabLR0Q
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mService.onUnlockComplete();
            }
        });
    }

    public static String toSpec(ComponentName name) {
        return "custom(" + name.flattenToShortString() + ")";
    }

    public static ComponentName getComponentFromSpec(String spec) {
        String action = spec.substring("custom(".length(), spec.length() - 1);
        if (action.isEmpty()) {
            throw new IllegalArgumentException("Empty custom tile spec action");
        }
        return ComponentName.unflattenFromString(action);
    }

    public static CustomTile create(QSTileHost host, String spec) {
        if (spec == null || !spec.startsWith("custom(") || !spec.endsWith(")")) {
            throw new IllegalArgumentException("Bad custom tile spec: " + spec);
        }
        String action = spec.substring("custom(".length(), spec.length() - 1);
        if (action.isEmpty()) {
            throw new IllegalArgumentException("Empty custom tile spec action");
        }
        return new CustomTile(host, action);
    }
}
