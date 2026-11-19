package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.DemoMode;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.StatusBarMobileView;
import com.android.systemui.statusbar.StatusBarWifiView;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DemoStatusIcons extends StatusIconContainer implements DemoMode, DarkIconDispatcher.DarkReceiver {
    private int mColor;
    private boolean mDemoMode;
    private final int mIconSize;
    private final ArrayList<StatusBarMobileView> mMobileViews;
    private final LinearLayout mStatusIcons;
    private StatusBarWifiView mWifiView;

    public DemoStatusIcons(LinearLayout statusIcons, int iconSize) {
        super(statusIcons.getContext());
        this.mMobileViews = new ArrayList<>();
        this.mStatusIcons = statusIcons;
        this.mIconSize = iconSize;
        this.mColor = -1;
        if (statusIcons instanceof StatusIconContainer) {
            setShouldRestrictIcons(((StatusIconContainer) statusIcons).isRestrictingIcons());
        } else {
            setShouldRestrictIcons(false);
        }
        setLayoutParams(this.mStatusIcons.getLayoutParams());
        setPadding(this.mStatusIcons.getPaddingLeft(), this.mStatusIcons.getPaddingTop(), this.mStatusIcons.getPaddingRight(), this.mStatusIcons.getPaddingBottom());
        setOrientation(this.mStatusIcons.getOrientation());
        setGravity(16);
        ViewGroup p = (ViewGroup) this.mStatusIcons.getParent();
        p.addView(this, p.indexOfChild(this.mStatusIcons));
    }

    public void remove() {
        this.mMobileViews.clear();
        ((ViewGroup) getParent()).removeView(this);
    }

    public void setColor(int color) {
        this.mColor = color;
        updateColors();
    }

    private void updateColors() {
        for (int i = 0; i < getChildCount(); i++) {
            StatusIconDisplayable child = (StatusIconDisplayable) getChildAt(i);
            child.setStaticDrawableColor(this.mColor);
            child.setDecorColor(this.mColor);
        }
    }

    @Override // com.android.systemui.DemoMode
    public void dispatchDemoCommand(String command, Bundle args) {
        int iconId;
        int iconId2;
        if (!this.mDemoMode && command.equals("enter")) {
            this.mDemoMode = true;
            this.mStatusIcons.setVisibility(8);
            setVisibility(0);
            return;
        }
        if (this.mDemoMode && command.equals("exit")) {
            this.mDemoMode = false;
            this.mStatusIcons.setVisibility(0);
            setVisibility(8);
            return;
        }
        if (this.mDemoMode && command.equals("status")) {
            String volume = args.getString("volume");
            if (volume != null) {
                int iconId3 = volume.equals("vibrate") ? R.drawable.stat_sys_ringer_vibrate : 0;
                updateSlot("volume", null, iconId3);
            }
            String zen = args.getString("zen");
            if (zen != null) {
                if (zen.equals("important")) {
                    iconId2 = R.drawable.stat_sys_zen_important;
                } else {
                    iconId2 = zen.equals("none") ? R.drawable.stat_sys_zen_none : 0;
                }
                updateSlot("zen", null, iconId2);
            }
            String bt = args.getString("bluetooth");
            if (bt != null) {
                if (bt.equals("disconnected")) {
                    iconId = R.drawable.stat_sys_data_bluetooth;
                } else {
                    iconId = bt.equals("connected") ? R.drawable.stat_sys_data_bluetooth_connected : 0;
                }
                updateSlot("bluetooth", null, iconId);
            }
            String location = args.getString("location");
            if (location != null) {
                int iconId4 = location.equals("show") ? R.drawable.stat_sys_location : 0;
                updateSlot("location", null, iconId4);
            }
            String alarm = args.getString("alarm");
            if (alarm != null) {
                int iconId5 = alarm.equals("show") ? R.drawable.stat_sys_alarm : 0;
                updateSlot("alarm_clock", null, iconId5);
            }
            String tty = args.getString("tty");
            if (tty != null) {
                int iconId6 = tty.equals("show") ? R.drawable.stat_sys_tty_mode : 0;
                updateSlot("tty", null, iconId6);
            }
            String mute = args.getString("mute");
            if (mute != null) {
                int iconId7 = mute.equals("show") ? android.R.drawable.stat_notify_call_mute : 0;
                updateSlot("mute", null, iconId7);
            }
            String speakerphone = args.getString("speakerphone");
            if (speakerphone != null) {
                int iconId8 = speakerphone.equals("show") ? android.R.drawable.stat_sys_speakerphone : 0;
                updateSlot("speakerphone", null, iconId8);
            }
            String cast = args.getString("cast");
            if (cast != null) {
                int iconId9 = cast.equals("show") ? R.drawable.stat_sys_cast : 0;
                updateSlot("cast", null, iconId9);
            }
            String hotspot = args.getString("hotspot");
            if (hotspot != null) {
                int iconId10 = hotspot.equals("show") ? R.drawable.stat_sys_hotspot : 0;
                updateSlot("hotspot", null, iconId10);
            }
        }
    }

    private void updateSlot(String slot, String iconPkg, int iconId) {
        if (this.mDemoMode) {
            if (iconPkg == null) {
                iconPkg = this.mContext.getPackageName();
            }
            int removeIndex = -1;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof StatusBarIconView) {
                    StatusBarIconView v = (StatusBarIconView) child;
                    if (slot.equals(v.getTag())) {
                        if (iconId == 0) {
                            removeIndex = i;
                            break;
                        }
                        StatusBarIcon icon = v.getStatusBarIcon();
                        icon.visible = true;
                        icon.icon = Icon.createWithResource(icon.icon.getResPackage(), iconId);
                        v.set(icon);
                        v.updateDrawable();
                        return;
                    }
                }
            }
            int removeIndex2 = removeIndex;
            if (iconId == 0) {
                if (removeIndex2 != -1) {
                    removeViewAt(removeIndex2);
                    return;
                }
                return;
            }
            StatusBarIcon icon2 = new StatusBarIcon(iconPkg, UserHandle.SYSTEM, iconId, 0, 0, "Demo");
            icon2.visible = true;
            StatusBarIconView v2 = new StatusBarIconView(getContext(), slot, null, false);
            v2.setTag(slot);
            v2.set(icon2);
            v2.setStaticDrawableColor(this.mColor);
            v2.setDecorColor(this.mColor);
            addView(v2, 0, new LinearLayout.LayoutParams(-2, this.mIconSize));
        }
    }

    public void addDemoWifiView(StatusBarSignalPolicy.WifiIconState state) {
        Log.d("DemoStatusIcons", "addDemoWifiView: ");
        StatusBarWifiView view = StatusBarWifiView.fromContext(this.mContext, state.slot);
        int viewIndex = getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof StatusBarMobileView) {
                viewIndex = i;
                break;
            }
        }
        this.mWifiView = view;
        this.mWifiView.applyWifiState(state);
        this.mWifiView.setStaticDrawableColor(this.mColor);
        addView(view, viewIndex);
    }

    public void updateWifiState(StatusBarSignalPolicy.WifiIconState state) {
        Log.d("DemoStatusIcons", "updateWifiState: ");
        if (this.mWifiView == null) {
            addDemoWifiView(state);
        } else {
            this.mWifiView.applyWifiState(state);
        }
    }

    public void addMobileView(StatusBarSignalPolicy.MobileIconState state) {
        Log.d("DemoStatusIcons", "addMobileView: ");
        StatusBarMobileView view = StatusBarMobileView.fromContext(this.mContext, state.slot);
        view.applyMobileState(state);
        view.setStaticDrawableColor(this.mColor);
        this.mMobileViews.add(view);
        addView(view, getChildCount());
    }

    public void updateMobileState(StatusBarSignalPolicy.MobileIconState state) {
        Log.d("DemoStatusIcons", "updateMobileState: ");
        for (int i = 0; i < this.mMobileViews.size(); i++) {
            StatusBarMobileView view = this.mMobileViews.get(i);
            if (view.getState().subId == state.subId) {
                view.applyMobileState(state);
                return;
            }
        }
        addMobileView(state);
    }

    public void onRemoveIcon(StatusIconDisplayable view) {
        if (view.getSlot().equals("wifi")) {
            removeView(this.mWifiView);
            this.mWifiView = null;
            return;
        }
        StatusBarMobileView mobileView = matchingMobileView(view);
        if (mobileView != null) {
            removeView(mobileView);
            this.mMobileViews.remove(mobileView);
        }
    }

    private StatusBarMobileView matchingMobileView(StatusIconDisplayable otherView) {
        if (!(otherView instanceof StatusBarMobileView)) {
            return null;
        }
        StatusBarMobileView v = (StatusBarMobileView) otherView;
        for (StatusBarMobileView view : this.mMobileViews) {
            if (view.getState().subId == v.getState().subId) {
                return view;
            }
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        setColor(DarkIconDispatcher.getTint(area, this.mStatusIcons, tint));
        if (this.mWifiView != null) {
            this.mWifiView.onDarkChanged(area, darkIntensity, tint);
        }
        for (StatusBarMobileView view : this.mMobileViews) {
            view.onDarkChanged(area, darkIntensity, tint);
        }
    }
}
