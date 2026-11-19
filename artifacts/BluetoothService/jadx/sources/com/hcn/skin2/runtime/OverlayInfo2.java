package com.hcn.skin2.runtime;

import android.os.Build;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public class OverlayInfo2 {
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 3;
    public static final int STATE_ENABLED_STATIC = 6;
    public static final int STATE_MISSING_TARGET = 0;
    public static final int STATE_NO_IDMAP = 1;
    public static final int STATE_OVERLAY_IS_BEING_REPLACED = 5;
    public static final int STATE_OVERLAY_UPGRADING = 5;

    @Deprecated
    public static final int STATE_TARGET_IS_BEING_REPLACED = 4;
    public static final int STATE_TARGET_UPGRADING = 4;
    public static final int STATE_UNKNOWN = -1;
    public final String baseCodePath;
    public final String category;
    public final boolean isStatic;
    public final String overlayName;
    public final String packageName;
    public final int priority;
    public final int state;
    public final String targetOverlayableName;
    public final String targetPackageName;
    public final int userId;

    public static String stateToString(int i) {
        switch (i) {
            case -1:
                return "STATE_UNKNOWN";
            case 0:
                return "STATE_MISSING_TARGET";
            case 1:
                return "STATE_NO_IDMAP";
            case 2:
                return "STATE_DISABLED";
            case 3:
                return "STATE_ENABLED";
            case 4:
                return "STATE_TARGET_UPGRADING | STATE_TARGET_IS_BEING_REPLACED";
            case 5:
                return "STATE_OVERLAY_UPGRADING | STATE_OVERLAY_IS_BEING_REPLACED";
            case 6:
                return "STATE_ENABLED_STATIC";
            default:
                return "<unknown state>";
        }
    }

    public OverlayInfo2(Parcel parcel) {
        this.packageName = parcel.readString();
        if (Build.VERSION.SDK_INT >= 33) {
            this.overlayName = parcel.readString();
        } else {
            this.overlayName = "unsupport";
        }
        this.targetPackageName = parcel.readString();
        if (Build.VERSION.SDK_INT >= 29) {
            this.targetOverlayableName = parcel.readString();
        } else {
            this.targetOverlayableName = "unsupport";
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.category = parcel.readString();
        } else {
            this.category = "unsupport";
        }
        this.baseCodePath = parcel.readString();
        this.state = parcel.readInt();
        this.userId = parcel.readInt();
        if (Build.VERSION.SDK_INT >= 28) {
            this.priority = parcel.readInt();
            this.isStatic = parcel.readInt() != 0;
        } else {
            this.priority = 0;
            this.isStatic = false;
        }
        ensureValidState();
    }

    public boolean isEnabled() {
        int i = this.state;
        return i == 3 || i == 6;
    }

    private void ensureValidState() {
        if (this.packageName == null) {
            throw new IllegalArgumentException("packageName must not be null");
        }
        if (this.targetPackageName == null) {
            throw new IllegalArgumentException("targetPackageName must not be null");
        }
        if (this.baseCodePath == null) {
            throw new IllegalArgumentException("baseCodePath must not be null");
        }
        switch (this.state) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return;
            default:
                throw new IllegalArgumentException("State " + this.state + " is not a valid state");
        }
    }

    public String toString() {
        return "OverlayInfo { overlay=" + this.packageName + ", target=" + this.targetPackageName + ", state=" + this.state + " (" + stateToString(this.state) + "), userId=" + this.userId + " }";
    }
}
