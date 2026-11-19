package com.android.systemui.statusbar.notification;

import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.statusbar.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public class AboveShelfObserver implements AboveShelfChangedListener {
    private boolean mHasViewsAboveShelf = false;
    private final ViewGroup mHostLayout;
    private HasViewAboveShelfChangedListener mListener;

    public interface HasViewAboveShelfChangedListener {
        void onHasViewsAboveShelfChanged(boolean z);
    }

    public AboveShelfObserver(ViewGroup hostLayout) {
        this.mHostLayout = hostLayout;
    }

    public void setListener(HasViewAboveShelfChangedListener listener) {
        this.mListener = listener;
    }

    @Override // com.android.systemui.statusbar.notification.AboveShelfChangedListener
    public void onAboveShelfStateChanged(boolean aboveShelf) {
        boolean hasViewsAboveShelf = aboveShelf;
        if (!hasViewsAboveShelf && this.mHostLayout != null) {
            int n = this.mHostLayout.getChildCount();
            for (int i = 0; i < n; i++) {
                View child = this.mHostLayout.getChildAt(i);
                if ((child instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) child).isAboveShelf()) {
                    hasViewsAboveShelf = true;
                    break;
                }
            }
        }
        if (this.mHasViewsAboveShelf != hasViewsAboveShelf) {
            this.mHasViewsAboveShelf = hasViewsAboveShelf;
            if (this.mListener != null) {
                this.mListener.onHasViewsAboveShelfChanged(hasViewsAboveShelf);
            }
        }
    }

    @VisibleForTesting
    boolean hasViewsAboveShelf() {
        return this.mHasViewsAboveShelf;
    }
}
