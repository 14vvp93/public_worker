package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class CastTile extends QSTileImpl<QSTile.BooleanState> {
    private static final Intent CAST_SETTINGS = new Intent("android.settings.CAST_SETTINGS");
    private final ActivityStarter mActivityStarter;
    private final Callback mCallback;
    private final CastController mController;
    private final CastDetailAdapter mDetailAdapter;
    private Dialog mDialog;
    private final KeyguardMonitor mKeyguard;

    public CastTile(QSHost host) {
        super(host);
        this.mCallback = new Callback();
        this.mController = (CastController) Dependency.get(CastController.class);
        this.mDetailAdapter = new CastDetailAdapter();
        this.mKeyguard = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
        if (DEBUG) {
            Log.d(this.TAG, "handleSetListening " + listening);
        }
        if (listening) {
            this.mController.addCallback(this.mCallback);
            this.mKeyguard.addCallback(this.mCallback);
        } else {
            this.mController.setDiscovering(false);
            this.mController.removeCallback(this.mCallback);
            this.mKeyguard.removeCallback(this.mCallback);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleUserSwitch(int newUserId) {
        super.handleUserSwitch(newUserId);
        this.mController.setCurrentUserId(newUserId);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleSecondaryClick() {
        handleClick();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        if (this.mKeyguard.isSecure() && !this.mKeyguard.canSkipBouncer()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CastTile$0TU5SvbFGUs5F0udF1tvlhHVObs
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.showDetail(true);
                }
            });
        } else {
            showDetail(true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void showDetail(boolean show) {
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CastTile$WPXsuhhRJ1um-wt53q0kaFd3rzI
            @Override // java.lang.Runnable
            public final void run() {
                CastTile.lambda$showDetail$3(this.f$0);
            }
        });
    }

    public static /* synthetic */ void lambda$showDetail$3(final CastTile castTile) {
        castTile.mDialog = MediaRouteDialogPresenter.createDialog(castTile.mContext, 4, new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CastTile$4kXW6ECEqBpSUmuEtdBz8p9QY1w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CastTile.lambda$showDetail$1(this.f$0, view);
            }
        });
        castTile.mDialog.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(castTile.mDialog, true);
        SystemUIDialog.registerDismissListener(castTile.mDialog);
        SystemUIDialog.setWindowOnTop(castTile.mDialog);
        castTile.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.-$$Lambda$CastTile$MhJepZXXVH2Vaj80AOmfpppL58s
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mDialog.show();
            }
        });
        castTile.mHost.collapsePanels();
    }

    public static /* synthetic */ void lambda$showDetail$1(CastTile castTile, View v) {
        castTile.mDialog.dismiss();
        ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(castTile.getLongClickIntent(), 0);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_cast_title);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        state.label = this.mContext.getString(R.string.quick_settings_cast_title);
        state.contentDescription = state.label;
        state.value = false;
        Set<CastController.CastDevice> devices = this.mController.getCastDevices();
        boolean connecting = false;
        Iterator<CastController.CastDevice> it = devices.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CastController.CastDevice device = it.next();
            if (device.state == 2) {
                state.value = true;
                state.label = getDeviceName(device);
                state.contentDescription = ((Object) state.contentDescription) + "," + this.mContext.getString(R.string.accessibility_cast_name, state.label);
            } else if (device.state == 1) {
                connecting = true;
            }
        }
        if (!state.value && connecting) {
            state.label = this.mContext.getString(R.string.quick_settings_connecting);
        }
        state.state = state.value ? 2 : 1;
        state.icon = QSTileImpl.ResourceIcon.get(state.value ? R.drawable.ic_qs_cast_on : R.drawable.ic_qs_cast_off);
        this.mDetailAdapter.updateItems(devices);
        state.expandedAccessibilityClassName = Button.class.getName();
        state.contentDescription = ((Object) state.contentDescription) + "," + this.mContext.getString(R.string.accessibility_quick_settings_open_details);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return com.android.systemui.plugins.R.styleable.AppCompatTheme_windowFixedHeightMajor;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected String composeChangeAnnouncement() {
        if (!((QSTile.BooleanState) this.mState).value) {
            return this.mContext.getString(R.string.accessibility_casting_turned_off);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDeviceName(CastController.CastDevice device) {
        return device.name != null ? device.name : this.mContext.getString(R.string.quick_settings_cast_device_default_name);
    }

    private final class Callback implements CastController.Callback, KeyguardMonitor.Callback {
        private Callback() {
        }

        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public void onCastDevicesChanged() {
            CastTile.this.refreshState();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardMonitor.Callback
        public void onKeyguardShowingChanged() {
            CastTile.this.refreshState();
        }
    }

    private final class CastDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
        private QSDetailItems mItems;
        private final LinkedHashMap<String, CastController.CastDevice> mVisibleOrder;

        private CastDetailAdapter() {
            this.mVisibleOrder = new LinkedHashMap<>();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public CharSequence getTitle() {
            return CastTile.this.mContext.getString(R.string.quick_settings_cast_title);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Boolean getToggleState() {
            return null;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Intent getSettingsIntent() {
            return CastTile.CAST_SETTINGS;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public void setToggleState(boolean state) {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getMetricsCategory() {
            return 151;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public View createDetailView(Context context, View convertView, ViewGroup parent) {
            this.mItems = QSDetailItems.convertOrInflate(context, convertView, parent);
            this.mItems.setTagSuffix("Cast");
            if (convertView == null) {
                if (CastTile.DEBUG) {
                    Log.d(CastTile.this.TAG, "addOnAttachStateChangeListener");
                }
                this.mItems.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.qs.tiles.CastTile.CastDetailAdapter.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View v) {
                        if (CastTile.DEBUG) {
                            Log.d(CastTile.this.TAG, "onViewAttachedToWindow");
                        }
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View v) {
                        if (CastTile.DEBUG) {
                            Log.d(CastTile.this.TAG, "onViewDetachedFromWindow");
                        }
                        CastDetailAdapter.this.mVisibleOrder.clear();
                    }
                });
            }
            this.mItems.setEmptyState(R.drawable.ic_qs_cast_detail_empty, R.string.quick_settings_cast_detail_empty_text);
            this.mItems.setCallback(this);
            updateItems(CastTile.this.mController.getCastDevices());
            CastTile.this.mController.setDiscovering(true);
            return this.mItems;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateItems(Set<CastController.CastDevice> devices) {
            if (this.mItems == null) {
                return;
            }
            QSDetailItems.Item[] items = null;
            if (devices != null && !devices.isEmpty()) {
                for (CastController.CastDevice device : devices) {
                    if (device.state == 2) {
                        QSDetailItems.Item item = new QSDetailItems.Item();
                        item.iconResId = R.drawable.ic_qs_cast_on;
                        item.line1 = CastTile.this.getDeviceName(device);
                        item.line2 = CastTile.this.mContext.getString(R.string.quick_settings_connected);
                        item.tag = device;
                        item.canDisconnect = true;
                        items = new QSDetailItems.Item[]{item};
                        break;
                    }
                }
                if (items == null) {
                    for (CastController.CastDevice device2 : devices) {
                        this.mVisibleOrder.put(device2.id, device2);
                    }
                    items = new QSDetailItems.Item[devices.size()];
                    int i = 0;
                    for (String id : this.mVisibleOrder.keySet()) {
                        CastController.CastDevice device3 = this.mVisibleOrder.get(id);
                        if (devices.contains(device3)) {
                            QSDetailItems.Item item2 = new QSDetailItems.Item();
                            item2.iconResId = R.drawable.ic_qs_cast_off;
                            item2.line1 = CastTile.this.getDeviceName(device3);
                            if (device3.state == 1) {
                                item2.line2 = CastTile.this.mContext.getString(R.string.quick_settings_connecting);
                            }
                            item2.tag = device3;
                            items[i] = item2;
                            i++;
                        }
                    }
                }
            }
            this.mItems.setItems(items);
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemClick(QSDetailItems.Item item) {
            if (item != null && item.tag != null) {
                MetricsLogger.action(CastTile.this.mContext, 157);
                CastController.CastDevice device = (CastController.CastDevice) item.tag;
                CastTile.this.mController.startCasting(device);
            }
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemDisconnect(QSDetailItems.Item item) {
            if (item != null && item.tag != null) {
                MetricsLogger.action(CastTile.this.mContext, 158);
                CastController.CastDevice device = (CastController.CastDevice) item.tag;
                CastTile.this.mController.stopCasting(device);
            }
        }
    }
}
