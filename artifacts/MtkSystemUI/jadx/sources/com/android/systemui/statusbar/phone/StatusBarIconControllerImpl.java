package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.ViewGroup;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.IconLogger;
import com.android.systemui.tuner.TunerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarIconControllerImpl extends StatusBarIconList implements Dumpable, CommandQueue.Callbacks, StatusBarIconController, ConfigurationController.ConfigurationListener, TunerService.Tunable {
    private Context mContext;
    private final ArraySet<String> mIconBlacklist;
    private final ArrayList<StatusBarIconController.IconManager> mIconGroups;
    private final IconLogger mIconLogger;
    private boolean mIsDark;

    public StatusBarIconControllerImpl(Context context) {
        super(context.getResources().getStringArray(R.array.config_deviceStatesAvailableForAppRequests), context);
        this.mIconGroups = new ArrayList<>();
        this.mIconBlacklist = new ArraySet<>();
        this.mIconLogger = (IconLogger) Dependency.get(IconLogger.class);
        this.mIsDark = false;
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        this.mContext = context;
        loadDimens();
        ((CommandQueue) SysUiServiceProvider.getComponent(context, CommandQueue.class)).addCallbacks(this);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist");
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void addIconGroup(StatusBarIconController.IconManager group) {
        this.mIconGroups.add(group);
        List<StatusBarIconList.Slot> allSlots = getSlots();
        for (int i = 0; i < allSlots.size(); i++) {
            StatusBarIconList.Slot slot = allSlots.get(i);
            List<StatusBarIconHolder> holders = slot.getHolderListInViewOrder();
            boolean blocked = this.mIconBlacklist.contains(slot.getName());
            for (StatusBarIconHolder holder : holders) {
                holder.getTag();
                int viewIndex = getViewIndex(getSlotIndex(slot.getName()), holder.getTag());
                group.onIconAdded(viewIndex, slot.getName(), blocked, holder);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void removeIconGroup(StatusBarIconController.IconManager group) {
        group.destroy();
        this.mIconGroups.remove(group);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if (!"icon_blacklist".equals(key)) {
            return;
        }
        this.mIconBlacklist.clear();
        this.mIconBlacklist.addAll((ArraySet<? extends String>) StatusBarIconController.getIconBlacklist(newValue));
        ArrayList<StatusBarIconList.Slot> currentSlots = getSlots();
        ArrayMap<StatusBarIconList.Slot, List<StatusBarIconHolder>> slotsToReAdd = new ArrayMap<>();
        for (int i = currentSlots.size() - 1; i >= 0; i--) {
            StatusBarIconList.Slot s = currentSlots.get(i);
            slotsToReAdd.put(s, s.getHolderListInViewOrder());
            removeAllIconsForSlot(s.getName());
        }
        for (int i2 = 0; i2 < currentSlots.size(); i2++) {
            StatusBarIconList.Slot item = currentSlots.get(i2);
            List<StatusBarIconHolder> iconsForSlot = slotsToReAdd.get(item);
            if (iconsForSlot != null) {
                for (StatusBarIconHolder holder : iconsForSlot) {
                    setIcon(getSlotIndex(item.getName()), holder);
                }
            }
        }
    }

    private void loadDimens() {
    }

    private void addSystemIcon(int index, final StatusBarIconHolder holder) {
        final String slot = getSlotName(index);
        final int viewIndex = getViewIndex(index, holder.getTag());
        final boolean blocked = this.mIconBlacklist.contains(slot);
        this.mIconLogger.onIconVisibility(getSlotName(index), holder.isVisible());
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarIconControllerImpl$fL8PZXISckai-5GwvhWVS3QVTsY
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((StatusBarIconController.IconManager) obj).onIconAdded(viewIndex, slot, blocked, holder);
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void setIcon(String slot, int resourceId, CharSequence contentDescription) {
        int index = getSlotIndex(slot);
        StatusBarIconHolder holder = getIcon(index, 0);
        if (holder == null) {
            StatusBarIcon icon = new StatusBarIcon(UserHandle.SYSTEM, this.mContext.getPackageName(), Icon.createWithResource(this.mContext, resourceId), 0, 0, contentDescription);
            setIcon(index, StatusBarIconHolder.fromIcon(icon));
        } else {
            holder.getIcon().icon = Icon.createWithResource(this.mContext, resourceId);
            holder.getIcon().contentDescription = contentDescription;
            handleSet(index, holder);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void setSignalIcon(String slot, StatusBarSignalPolicy.WifiIconState state) {
        int index = getSlotIndex(slot);
        if (state == null) {
            removeIcon(index, 0);
            return;
        }
        StatusBarIconHolder holder = getIcon(index, 0);
        if (holder == null) {
            setIcon(index, StatusBarIconHolder.fromWifiIconState(state));
        } else {
            holder.setWifiState(state);
            handleSet(index, holder);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void setMobileIcons(String slot, List<StatusBarSignalPolicy.MobileIconState> iconStates) {
        StatusBarIconList.Slot mobileSlot = getSlot(slot);
        int slotIndex = getSlotIndex(slot);
        for (StatusBarSignalPolicy.MobileIconState state : iconStates) {
            StatusBarIconHolder holder = mobileSlot.getHolderForTag(state.subId);
            if (holder == null) {
                setIcon(slotIndex, StatusBarIconHolder.fromMobileIconState(state));
            } else {
                holder.setMobileState(state);
                handleSet(slotIndex, holder);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void setExternalIcon(String slot) {
        final int viewIndex = getViewIndex(getSlotIndex(slot), 0);
        final int height = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_icon_drawing_size);
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarIconControllerImpl$rsmVGSlXlU7ffeIAEgpWeyyu04I
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((StatusBarIconController.IconManager) obj).onIconExternal(viewIndex, height);
            }
        });
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks, com.android.systemui.statusbar.phone.StatusBarIconController
    public void setIcon(String slot, StatusBarIcon icon) {
        setIcon(getSlotIndex(slot), icon);
    }

    private void setIcon(int index, StatusBarIcon icon) {
        if (icon == null) {
            removeAllIconsForSlot(getSlotName(index));
        } else {
            StatusBarIconHolder holder = StatusBarIconHolder.fromIcon(icon);
            setIcon(index, holder);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconList
    public void setIcon(int index, StatusBarIconHolder holder) {
        boolean isNew = getIcon(index, holder.getTag()) == null;
        super.setIcon(index, holder);
        if (isNew) {
            addSystemIcon(index, holder);
        } else {
            handleSet(index, holder);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void setIconVisibility(String slot, boolean visibility) {
        int index = getSlotIndex(slot);
        StatusBarIconHolder holder = getIcon(index, 0);
        if (holder == null || holder.isVisible() == visibility) {
            return;
        }
        holder.setVisible(visibility);
        handleSet(index, holder);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void removeIcon(String slot) {
        removeAllIconsForSlot(slot);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconController
    public void removeAllIconsForSlot(String slotName) {
        StatusBarIconList.Slot slot = getSlot(slotName);
        if (!slot.hasIconsInSlot()) {
            return;
        }
        this.mIconLogger.onIconHidden(slotName);
        int slotIndex = getSlotIndex(slotName);
        List<StatusBarIconHolder> iconsToRemove = slot.getHolderListInViewOrder();
        for (StatusBarIconHolder holder : iconsToRemove) {
            final int viewIndex = getViewIndex(slotIndex, holder.getTag());
            slot.removeForTag(holder.getTag());
            this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarIconControllerImpl$uTqaHUAWHbu0P16vDWL0oAyCetk
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((StatusBarIconController.IconManager) obj).onRemoveIcon(viewIndex);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarIconList
    public void removeIcon(int index, int tag) {
        if (getIcon(index, tag) == null) {
            return;
        }
        this.mIconLogger.onIconHidden(getSlotName(index));
        super.removeIcon(index, tag);
        final int viewIndex = getViewIndex(index, 0);
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarIconControllerImpl$XIHL8F8kJA04U9X_9IHtSYwXxLU
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((StatusBarIconController.IconManager) obj).onRemoveIcon(viewIndex);
            }
        });
    }

    private void handleSet(int index, final StatusBarIconHolder holder) {
        final int viewIndex = getViewIndex(index, holder.getTag());
        this.mIconLogger.onIconVisibility(getSlotName(index), holder.isVisible());
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarIconControllerImpl$ayp5xWywAkBOOSd-6MshVHM8Mms
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((StatusBarIconController.IconManager) obj).onSetIconHolder(viewIndex, holder);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("StatusBarIconController state:");
        for (StatusBarIconController.IconManager manager : this.mIconGroups) {
            if (manager.shouldLog()) {
                ViewGroup group = manager.mGroup;
                int N = group.getChildCount();
                pw.println("  icon views: " + N);
                for (int i = 0; i < N; i++) {
                    StatusIconDisplayable ic = (StatusIconDisplayable) group.getChildAt(i);
                    pw.println("    [" + i + "] icon=" + ic);
                }
            }
        }
        super.dump(pw);
    }

    public void dispatchDemoCommand(String command, Bundle args) {
        for (StatusBarIconController.IconManager manager : this.mIconGroups) {
            if (manager.isDemoable()) {
                manager.dispatchDemoCommand(command, args);
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        loadDimens();
    }
}
