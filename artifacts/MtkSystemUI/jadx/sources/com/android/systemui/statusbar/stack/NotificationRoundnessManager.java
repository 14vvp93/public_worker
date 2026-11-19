package com.android.systemui.statusbar.stack;

import android.view.View;
import com.android.systemui.statusbar.ActivatableNotificationView;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
class NotificationRoundnessManager implements OnHeadsUpChangedListener {
    private HashSet<View> mAnimatedChildren;
    private float mAppearFraction;
    private boolean mExpanded;
    private ActivatableNotificationView mFirst;
    private ActivatableNotificationView mLast;
    private Runnable mRoundingChangedCallback;
    private ExpandableNotificationRow mTrackedHeadsUp;

    NotificationRoundnessManager() {
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinned(ExpandableNotificationRow headsUp) {
        updateRounding(headsUp, false);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpUnPinned(ExpandableNotificationRow headsUp) {
        updateRounding(headsUp, true);
    }

    public void onHeadsupAnimatingAwayChanged(ExpandableNotificationRow row, boolean isAnimatingAway) {
        updateRounding(row, false);
    }

    private void updateRounding(ActivatableNotificationView view, boolean animate) {
        float topRoundness = getRoundness(view, true);
        float bottomRoundness = getRoundness(view, false);
        boolean firstChanged = view.setTopRoundness(topRoundness, animate);
        boolean secondChanged = view.setBottomRoundness(bottomRoundness, animate);
        if (view == this.mFirst || view == this.mLast) {
            if (firstChanged || secondChanged) {
                this.mRoundingChangedCallback.run();
            }
        }
    }

    private float getRoundness(ActivatableNotificationView view, boolean top) {
        if ((view.isPinned() || view.isHeadsUpAnimatingAway()) && !this.mExpanded) {
            return 1.0f;
        }
        if (view == this.mFirst && top) {
            return 1.0f;
        }
        if (view != this.mLast || top) {
            return (view != this.mTrackedHeadsUp || this.mAppearFraction > 0.0f) ? 0.0f : 1.0f;
        }
        return 1.0f;
    }

    public void setExpanded(float expandedHeight, float appearFraction) {
        this.mExpanded = expandedHeight != 0.0f;
        this.mAppearFraction = appearFraction;
        if (this.mTrackedHeadsUp != null) {
            updateRounding(this.mTrackedHeadsUp, true);
        }
    }

    public void setFirstAndLastBackgroundChild(ActivatableNotificationView first, ActivatableNotificationView last) {
        boolean z = false;
        boolean firstChanged = this.mFirst != first;
        boolean lastChanged = this.mLast != last;
        if (!firstChanged && !lastChanged) {
            return;
        }
        ActivatableNotificationView oldFirst = this.mFirst;
        ActivatableNotificationView oldLast = this.mLast;
        this.mFirst = first;
        this.mLast = last;
        if (firstChanged && oldFirst != null && !oldFirst.isRemoved()) {
            updateRounding(oldFirst, oldFirst.isShown());
        }
        if (lastChanged && oldLast != null && !oldLast.isRemoved()) {
            updateRounding(oldLast, oldLast.isShown());
        }
        if (this.mFirst != null) {
            updateRounding(this.mFirst, this.mFirst.isShown() && !this.mAnimatedChildren.contains(this.mFirst));
        }
        if (this.mLast != null) {
            ActivatableNotificationView activatableNotificationView = this.mLast;
            if (this.mLast.isShown() && !this.mAnimatedChildren.contains(this.mLast)) {
                z = true;
            }
            updateRounding(activatableNotificationView, z);
        }
        this.mRoundingChangedCallback.run();
    }

    public void setAnimatedChildren(HashSet<View> animatedChildren) {
        this.mAnimatedChildren = animatedChildren;
    }

    public void setOnRoundingChangedCallback(Runnable roundingChangedCallback) {
        this.mRoundingChangedCallback = roundingChangedCallback;
    }

    public void setTrackingHeadsUp(ExpandableNotificationRow row) {
        this.mTrackedHeadsUp = row;
    }
}
