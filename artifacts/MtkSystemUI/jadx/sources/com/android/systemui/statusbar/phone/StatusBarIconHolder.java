package com.android.systemui.statusbar.phone;

import com.android.internal.statusbar.StatusBarIcon;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarIconHolder {
    private StatusBarIcon mIcon;
    private StatusBarSignalPolicy.MobileIconState mMobileState;
    private StatusBarSignalPolicy.WifiIconState mWifiState;
    private int mType = 0;
    private int mTag = 0;
    private boolean mVisible = true;

    public static StatusBarIconHolder fromIcon(StatusBarIcon icon) {
        StatusBarIconHolder wrapper = new StatusBarIconHolder();
        wrapper.mIcon = icon;
        return wrapper;
    }

    public static StatusBarIconHolder fromWifiIconState(StatusBarSignalPolicy.WifiIconState state) {
        StatusBarIconHolder holder = new StatusBarIconHolder();
        holder.mWifiState = state;
        holder.mType = 1;
        return holder;
    }

    public static StatusBarIconHolder fromMobileIconState(StatusBarSignalPolicy.MobileIconState state) {
        StatusBarIconHolder holder = new StatusBarIconHolder();
        holder.mMobileState = state;
        holder.mType = 2;
        holder.mTag = state.subId;
        return holder;
    }

    public int getType() {
        return this.mType;
    }

    public StatusBarIcon getIcon() {
        return this.mIcon;
    }

    public StatusBarSignalPolicy.WifiIconState getWifiState() {
        return this.mWifiState;
    }

    public void setWifiState(StatusBarSignalPolicy.WifiIconState state) {
        this.mWifiState = state;
    }

    public StatusBarSignalPolicy.MobileIconState getMobileState() {
        return this.mMobileState;
    }

    public void setMobileState(StatusBarSignalPolicy.MobileIconState state) {
        this.mMobileState = state;
    }

    public boolean isVisible() {
        switch (this.mType) {
            case 0:
                return this.mIcon.visible;
            case 1:
                return this.mWifiState.visible;
            case 2:
                return this.mMobileState.visible;
            default:
                return true;
        }
    }

    public void setVisible(boolean visible) {
        if (isVisible() == visible) {
        }
        switch (this.mType) {
            case 0:
                this.mIcon.visible = visible;
                break;
            case 1:
                this.mWifiState.visible = visible;
                break;
            case 2:
                this.mMobileState.visible = visible;
                break;
        }
    }

    public int getTag() {
        return this.mTag;
    }
}
