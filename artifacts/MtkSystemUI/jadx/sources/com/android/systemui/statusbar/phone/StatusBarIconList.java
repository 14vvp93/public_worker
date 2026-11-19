package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.mediatek.systemui.ext.ISystemUIStatusBarExt;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import com.mediatek.systemui.statusbar.util.FeatureOptions;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarIconList {
    private ArrayList<Slot> mSlots = new ArrayList<>();
    private ISystemUIStatusBarExt mStatusBarExt;

    public StatusBarIconList(String[] slots, Context context) {
        this.mStatusBarExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeSystemUIStatusBar(context);
        for (String str : this.mStatusBarExt.addSlot(slots)) {
            this.mSlots.add(new Slot(str, null));
        }
        if (FeatureOptions.LOG_ENABLE) {
            Log.d("StatusBarIconList", "mSlots: " + this.mSlots);
        }
    }

    public int getSlotIndex(String slot) {
        int N = this.mSlots.size();
        for (int i = 0; i < N; i++) {
            Slot item = this.mSlots.get(i);
            if (item.getName().equals(slot)) {
                return i;
            }
        }
        this.mSlots.add(0, new Slot(slot, null));
        return 0;
    }

    protected ArrayList<Slot> getSlots() {
        return new ArrayList<>(this.mSlots);
    }

    protected Slot getSlot(String name) {
        return this.mSlots.get(getSlotIndex(name));
    }

    public void setIcon(int index, StatusBarIconHolder holder) {
        this.mSlots.get(index).addHolder(holder);
    }

    public void removeIcon(int index, int tag) {
        this.mSlots.get(index).removeForTag(tag);
    }

    public String getSlotName(int index) {
        return this.mSlots.get(index).getName();
    }

    public StatusBarIconHolder getIcon(int index, int tag) {
        return this.mSlots.get(index).getHolderForTag(tag);
    }

    public int getViewIndex(int slotIndex, int tag) {
        int count = 0;
        for (int i = 0; i < slotIndex; i++) {
            Slot item = this.mSlots.get(i);
            if (item.hasIconsInSlot()) {
                count += item.numberOfIcons();
            }
        }
        Slot viewItem = this.mSlots.get(slotIndex);
        return viewItem.viewIndexOffsetForTag(tag) + count;
    }

    public void dump(PrintWriter pw) {
        pw.println("StatusBarIconList state:");
        int N = this.mSlots.size();
        pw.println("  icon slots: " + N);
        for (int i = 0; i < N; i++) {
            pw.printf("    %2d:%s\n", Integer.valueOf(i), this.mSlots.get(i).toString());
        }
    }

    public static class Slot {
        Comparator c = new Comparator<StatusBarIconHolder>() { // from class: com.android.systemui.statusbar.phone.StatusBarIconList.Slot.1
            @Override // java.util.Comparator
            public int compare(StatusBarIconHolder h1, StatusBarIconHolder h2) {
                if (h1.getType() != 2 || h2.getType() != 2) {
                    return 0;
                }
                if (h1.getMobileState().phoneId < h2.getMobileState().phoneId) {
                    return 1;
                }
                return -1;
            }
        };
        private StatusBarIconHolder mHolder;
        private final String mName;
        private ArrayList<StatusBarIconHolder> mSubSlots;

        public Slot(String name, StatusBarIconHolder iconHolder) {
            this.mName = name;
            this.mHolder = iconHolder;
        }

        public String getName() {
            return this.mName;
        }

        public StatusBarIconHolder getHolderForTag(int tag) {
            if (tag == 0) {
                return this.mHolder;
            }
            if (this.mSubSlots != null) {
                for (StatusBarIconHolder holder : this.mSubSlots) {
                    if (holder.getTag() == tag) {
                        return holder;
                    }
                }
                return null;
            }
            return null;
        }

        public void addHolder(StatusBarIconHolder holder) {
            int tag = holder.getTag();
            if (tag == 0) {
                this.mHolder = holder;
            } else {
                setSubSlot(holder, tag);
            }
        }

        public void removeForTag(int tag) {
            if (tag == 0) {
                this.mHolder = null;
                return;
            }
            int index = getIndexForTag(tag);
            if (index != -1) {
                this.mSubSlots.remove(index);
            }
        }

        @VisibleForTesting
        public void clear() {
            this.mHolder = null;
            if (this.mSubSlots != null) {
                this.mSubSlots = null;
            }
        }

        private void setSubSlot(StatusBarIconHolder holder, int tag) {
            if (this.mSubSlots == null) {
                this.mSubSlots = new ArrayList<>();
                this.mSubSlots.add(holder);
            } else {
                if (getIndexForTag(tag) != -1) {
                    return;
                }
                this.mSubSlots.add(holder);
                Collections.sort(this.mSubSlots, this.c);
            }
        }

        private int getIndexForTag(int tag) {
            for (int i = 0; i < this.mSubSlots.size(); i++) {
                StatusBarIconHolder h = this.mSubSlots.get(i);
                if (h.getTag() == tag) {
                    return i;
                }
            }
            return -1;
        }

        public boolean hasIconsInSlot() {
            if (this.mHolder != null) {
                return true;
            }
            return this.mSubSlots != null && this.mSubSlots.size() > 0;
        }

        public int numberOfIcons() {
            int num = this.mHolder == null ? 0 : 1;
            return this.mSubSlots == null ? num : this.mSubSlots.size() + num;
        }

        public int viewIndexOffsetForTag(int tag) {
            if (this.mSubSlots == null) {
                return 0;
            }
            int subSlots = this.mSubSlots.size();
            if (tag == 0) {
                return subSlots;
            }
            return (subSlots - getIndexForTag(tag)) - 1;
        }

        public List<StatusBarIconHolder> getHolderListInViewOrder() {
            ArrayList<StatusBarIconHolder> holders = new ArrayList<>();
            if (this.mSubSlots != null) {
                for (int i = this.mSubSlots.size() - 1; i >= 0; i--) {
                    holders.add(this.mSubSlots.get(i));
                }
            }
            if (this.mHolder != null) {
                holders.add(this.mHolder);
            }
            return holders;
        }

        public String toString() {
            return String.format("(%s) %s", this.mName, subSlotsString());
        }

        private String subSlotsString() {
            if (this.mSubSlots == null) {
                return "";
            }
            return "" + this.mSubSlots.size() + " subSlots";
        }
    }
}
