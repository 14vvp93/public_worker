package com.android.systemui.qs.tiles;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.graph.BluetoothDeviceLayerDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.util.ExternalParameterManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothTile extends QSTileImpl<QSTile.BooleanState> {
    private static final Intent BLUETOOTH_SETTINGS = new Intent("android.settings.BLUETOOTH_SETTINGS");
    private final ActivityStarter mActivityStarter;
    private final BluetoothController.Callback mCallback;
    private final BluetoothController mController;
    private final BluetoothDetailAdapter mDetailAdapter;

    public BluetoothTile(QSHost host) {
        super(host);
        this.mCallback = new BluetoothController.Callback() { // from class: com.android.systemui.qs.tiles.BluetoothTile.1
            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public void onBluetoothStateChange(boolean enabled) {
                BluetoothTile.this.refreshState();
                if (!BluetoothTile.this.isShowingDetail()) {
                    return;
                }
                BluetoothTile.this.mDetailAdapter.updateItems();
                BluetoothTile.this.fireToggleStateChanged(BluetoothTile.this.mDetailAdapter.getToggleState().booleanValue());
            }

            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public void onBluetoothDevicesChanged() {
                BluetoothTile.this.refreshState();
                if (!BluetoothTile.this.isShowingDetail()) {
                    return;
                }
                BluetoothTile.this.mDetailAdapter.updateItems();
            }
        };
        this.mController = (BluetoothController) Dependency.get(BluetoothController.class);
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        this.mDetailAdapter = (BluetoothDetailAdapter) createDetailAdapter();
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
        if (this.mController == null) {
            return;
        }
        if (listening) {
            this.mController.addCallback(this.mCallback);
        } else {
            this.mController.removeCallback(this.mCallback);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        boolean isEnabled = ((QSTile.BooleanState) this.mState).value;
        if (this.mController == null) {
            return;
        }
        refreshState(isEnabled ? null : ARG_SHOW_TRANSIENT_ENABLING);
        this.mController.setBluetoothEnabled(!isEnabled);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        if (ExternalParameterManager.ETHEME_GOD_400 == ExternalParameterManager.ETHEME_GOD && ExternalParameterManager.ETHEME_SUB_65 == ExternalParameterManager.ETHEME_SUB) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setPackage(this.mContext.getString(R.string.quick_settings_bluetooth_pkg));
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setFlags(270532608);
            return intent;
        }
        return new Intent("android.settings.BLUETOOTH_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleSecondaryClick() {
        if (this.mController == null) {
            return;
        }
        if (!this.mController.canConfigBluetooth()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
            return;
        }
        showDetail(true);
        if (!((QSTile.BooleanState) this.mState).value) {
            this.mController.setBluetoothEnabled(true);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_bluetooth_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        if (this.mController == null) {
            return;
        }
        boolean transientEnabling = arg == ARG_SHOW_TRANSIENT_ENABLING;
        boolean enabled = transientEnabling || this.mController.isBluetoothEnabled();
        boolean connected = this.mController.isBluetoothConnected();
        boolean connecting = this.mController.isBluetoothConnecting();
        state.isTransient = transientEnabling || connecting || this.mController.getBluetoothState() == 11;
        state.dualTarget = true;
        state.value = enabled;
        if (state.slash == null) {
            state.slash = new QSTile.SlashState();
        }
        state.slash.isSlashed = !enabled;
        state.label = this.mContext.getString(R.string.quick_settings_bluetooth_label);
        state.secondaryLabel = TextUtils.emptyIfNull(getSecondaryLabel(enabled, connecting, connected, state.isTransient));
        if (enabled) {
            if (connected) {
                state.icon = new BluetoothConnectedTileIcon();
                if (!TextUtils.isEmpty(this.mController.getConnectedDeviceName())) {
                    state.label = this.mController.getConnectedDeviceName();
                }
                state.contentDescription = this.mContext.getString(R.string.accessibility_bluetooth_name, state.label) + ", " + ((Object) state.secondaryLabel);
            } else if (state.isTransient) {
                state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_bluetooth_transient_animation);
                state.contentDescription = state.secondaryLabel;
            } else {
                state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_on);
                state.contentDescription = this.mContext.getString(R.string.accessibility_quick_settings_bluetooth) + "," + this.mContext.getString(R.string.accessibility_not_connected);
            }
            state.state = 2;
        } else {
            state.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_on);
            state.contentDescription = this.mContext.getString(R.string.accessibility_quick_settings_bluetooth);
            state.state = 1;
        }
        state.dualLabelContentDescription = this.mContext.getResources().getString(R.string.accessibility_quick_settings_open_settings, getTileLabel());
        state.expandedAccessibilityClassName = Switch.class.getName();
    }

    private String getSecondaryLabel(boolean enabled, boolean connecting, boolean connected, boolean isTransient) {
        if (connecting) {
            return this.mContext.getString(R.string.quick_settings_connecting);
        }
        if (isTransient) {
            return this.mContext.getString(R.string.quick_settings_bluetooth_secondary_label_transient);
        }
        List<CachedBluetoothDevice> connectedDevices = this.mController.getConnectedDevices();
        if (enabled && connected && !connectedDevices.isEmpty()) {
            if (connectedDevices.size() > 1) {
                return this.mContext.getResources().getQuantityString(R.plurals.quick_settings_hotspot_secondary_label_num_devices, connectedDevices.size(), Integer.valueOf(connectedDevices.size()));
            }
            CachedBluetoothDevice lastDevice = connectedDevices.get(0);
            int batteryLevel = lastDevice.getBatteryLevel();
            if (batteryLevel != -1) {
                return this.mContext.getString(R.string.quick_settings_bluetooth_secondary_label_battery_level, Utils.formatPercentage(batteryLevel));
            }
            BluetoothClass bluetoothClass = lastDevice.getBtClass();
            if (bluetoothClass != null) {
                if (bluetoothClass.doesClassMatch(1)) {
                    return this.mContext.getString(R.string.quick_settings_bluetooth_secondary_label_audio);
                }
                if (bluetoothClass.doesClassMatch(0)) {
                    return this.mContext.getString(R.string.quick_settings_bluetooth_secondary_label_headset);
                }
                if (bluetoothClass.doesClassMatch(3)) {
                    return this.mContext.getString(R.string.quick_settings_bluetooth_secondary_label_input);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionModeOverlay;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected String composeChangeAnnouncement() {
        if (((QSTile.BooleanState) this.mState).value) {
            return this.mContext.getString(R.string.accessibility_quick_settings_bluetooth_changed_on);
        }
        return this.mContext.getString(R.string.accessibility_quick_settings_bluetooth_changed_off);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mController.isBluetoothSupported();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected DetailAdapter createDetailAdapter() {
        return new BluetoothDetailAdapter();
    }

    private class BluetoothBatteryTileIcon extends QSTile.Icon {
        private int mBatteryLevel;
        private float mIconScale;

        BluetoothBatteryTileIcon(int batteryLevel, float iconScale) {
            this.mBatteryLevel = batteryLevel;
            this.mIconScale = iconScale;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return BluetoothDeviceLayerDrawable.createLayerDrawable(context, R.drawable.ic_qs_bluetooth_connected, this.mBatteryLevel, this.mIconScale);
        }
    }

    private class BluetoothConnectedTileIcon extends QSTile.Icon {
        BluetoothConnectedTileIcon() {
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return context.getDrawable(R.drawable.ic_qs_bluetooth_connected);
        }
    }

    protected class BluetoothDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
        private QSDetailItems mItems;

        protected BluetoothDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public CharSequence getTitle() {
            return BluetoothTile.this.mContext.getString(R.string.quick_settings_bluetooth_label);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Boolean getToggleState() {
            return Boolean.valueOf(((QSTile.BooleanState) BluetoothTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public boolean getToggleEnabled() {
            return BluetoothTile.this.mController.getBluetoothState() == 10 || BluetoothTile.this.mController.getBluetoothState() == 12;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Intent getSettingsIntent() {
            return BluetoothTile.BLUETOOTH_SETTINGS;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public void setToggleState(boolean state) {
            MetricsLogger.action(BluetoothTile.this.mContext, 154, state);
            BluetoothTile.this.mController.setBluetoothEnabled(state);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getMetricsCategory() {
            return 150;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public View createDetailView(Context context, View convertView, ViewGroup parent) {
            this.mItems = QSDetailItems.convertOrInflate(context, convertView, parent);
            this.mItems.setTagSuffix("Bluetooth");
            this.mItems.setCallback(this);
            updateItems();
            setItemsVisible(((QSTile.BooleanState) BluetoothTile.this.mState).value);
            return this.mItems;
        }

        public void setItemsVisible(boolean visible) {
            if (this.mItems == null) {
                return;
            }
            this.mItems.setItemsVisible(visible);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateItems() {
            if (this.mItems == null) {
                return;
            }
            if (BluetoothTile.this.mController.isBluetoothEnabled()) {
                this.mItems.setEmptyState(R.drawable.ic_qs_bluetooth_detail_empty, R.string.quick_settings_bluetooth_detail_empty_text);
            } else {
                this.mItems.setEmptyState(R.drawable.ic_qs_bluetooth_detail_empty, R.string.bt_is_off);
            }
            ArrayList<QSDetailItems.Item> items = new ArrayList<>();
            Collection<CachedBluetoothDevice> devices = BluetoothTile.this.mController.getDevices();
            if (devices != null) {
                int connectedDevices = 0;
                int count = 0;
                for (CachedBluetoothDevice device : devices) {
                    if (BluetoothTile.this.mController.getBondState(device) != 10) {
                        QSDetailItems.Item item = new QSDetailItems.Item();
                        item.iconResId = R.drawable.ic_qs_bluetooth_on;
                        item.line1 = device.getName();
                        item.tag = device;
                        int state = device.getMaxConnectionState();
                        if (state == 2) {
                            item.iconResId = R.drawable.ic_qs_bluetooth_connected;
                            int batteryLevel = device.getBatteryLevel();
                            if (batteryLevel == -1) {
                                item.line2 = BluetoothTile.this.mContext.getString(R.string.quick_settings_connected);
                            } else {
                                item.icon = BluetoothTile.this.new BluetoothBatteryTileIcon(batteryLevel, 1.0f);
                                item.line2 = BluetoothTile.this.mContext.getString(R.string.quick_settings_connected_battery_level, Utils.formatPercentage(batteryLevel));
                            }
                            item.canDisconnect = true;
                            items.add(connectedDevices, item);
                            connectedDevices++;
                        } else if (state == 1) {
                            item.iconResId = R.drawable.ic_qs_bluetooth_connecting;
                            item.line2 = BluetoothTile.this.mContext.getString(R.string.quick_settings_connecting);
                            items.add(connectedDevices, item);
                        } else {
                            items.add(item);
                        }
                        count++;
                        if (count == 20) {
                            break;
                        }
                    }
                }
            }
            this.mItems.setItems((QSDetailItems.Item[]) items.toArray(new QSDetailItems.Item[items.size()]));
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemClick(QSDetailItems.Item item) {
            CachedBluetoothDevice device;
            if (item != null && item.tag != null && (device = (CachedBluetoothDevice) item.tag) != null && device.getMaxConnectionState() == 0) {
                BluetoothTile.this.mController.connect(device);
            }
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemDisconnect(QSDetailItems.Item item) {
            CachedBluetoothDevice device;
            if (item != null && item.tag != null && (device = (CachedBluetoothDevice) item.tag) != null) {
                BluetoothTile.this.mController.disconnect(device);
            }
        }
    }
}
