package com.android.systemui.statusbar.stack;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.FooterView;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.NotificationUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StackScrollAlgorithm {
    private boolean mClipNotificationScrollToTop;
    private int mCollapsedSize;
    private float mHeadsUpInset;
    private int mIncreasedPaddingBetweenElements;
    private boolean mIsExpanded;
    private int mPaddingBetweenElements;
    private int mPinnedZTranslationExtra;
    private int mStatusBarHeight;
    private StackScrollAlgorithmState mTempAlgorithmState = new StackScrollAlgorithmState();

    public StackScrollAlgorithm(Context context) {
        initView(context);
    }

    public void initView(Context context) {
        initConstants(context);
    }

    private void initConstants(Context context) {
        Resources res = context.getResources();
        this.mPaddingBetweenElements = res.getDimensionPixelSize(R.dimen.notification_divider_height);
        this.mIncreasedPaddingBetweenElements = res.getDimensionPixelSize(R.dimen.notification_divider_height_increased);
        this.mCollapsedSize = res.getDimensionPixelSize(R.dimen.notification_min_height);
        this.mStatusBarHeight = res.getDimensionPixelSize(R.dimen.status_bar_height);
        this.mClipNotificationScrollToTop = res.getBoolean(R.bool.config_clipNotificationScrollToTop);
        this.mHeadsUpInset = this.mStatusBarHeight + res.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding);
        this.mPinnedZTranslationExtra = res.getDimensionPixelSize(R.dimen.heads_up_pinned_elevation);
    }

    public void getStackScrollState(AmbientState ambientState, StackScrollState resultState) {
        StackScrollAlgorithmState algorithmState = this.mTempAlgorithmState;
        resultState.resetViewStates();
        initAlgorithmState(resultState, algorithmState, ambientState);
        updatePositionsForState(resultState, algorithmState, ambientState);
        updateZValuesForState(resultState, algorithmState, ambientState);
        updateHeadsUpStates(resultState, algorithmState, ambientState);
        handleDraggedViews(ambientState, resultState, algorithmState);
        updateDimmedActivatedHideSensitive(ambientState, resultState, algorithmState);
        updateClipping(resultState, algorithmState, ambientState);
        updateSpeedBumpState(resultState, algorithmState, ambientState);
        updateShelfState(resultState, ambientState);
        getNotificationChildrenStates(resultState, algorithmState, ambientState);
    }

    private void getNotificationChildrenStates(StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        int childCount = algorithmState.visibleChildren.size();
        for (int i = 0; i < childCount; i++) {
            ExpandableView v = algorithmState.visibleChildren.get(i);
            if (v instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) v;
                row.getChildrenStates(resultState, ambientState);
            }
        }
    }

    private void updateSpeedBumpState(StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        int childCount = algorithmState.visibleChildren.size();
        int belowSpeedBump = ambientState.getSpeedBumpIndex();
        int i = 0;
        while (i < childCount) {
            View child = algorithmState.visibleChildren.get(i);
            ExpandableViewState childViewState = resultState.getViewStateForView(child);
            childViewState.belowSpeedBump = i >= belowSpeedBump;
            i++;
        }
    }

    private void updateShelfState(StackScrollState resultState, AmbientState ambientState) {
        NotificationShelf shelf = ambientState.getShelf();
        if (shelf != null) {
            shelf.updateState(resultState, ambientState);
        }
    }

    private void updateClipping(StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        float drawStart;
        if (!ambientState.isOnKeyguard()) {
            drawStart = ambientState.getTopPadding() + ambientState.getStackTranslation() + ambientState.getExpandAnimationTopChange();
        } else {
            drawStart = 0.0f;
        }
        int childCount = algorithmState.visibleChildren.size();
        float previousNotificationStart = 0.0f;
        float previousNotificationEnd = 0.0f;
        for (int i = 0; i < childCount; i++) {
            ExpandableView child = algorithmState.visibleChildren.get(i);
            ExpandableViewState state = resultState.getViewStateForView(child);
            if (!child.mustStayOnScreen() || state.headsUpIsVisible) {
                previousNotificationEnd = Math.max(drawStart, previousNotificationEnd);
                previousNotificationStart = Math.max(drawStart, previousNotificationStart);
            }
            float newYTranslation = state.yTranslation;
            float newHeight = state.height;
            float newNotificationEnd = newYTranslation + newHeight;
            boolean isHeadsUp = (child instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) child).isPinned();
            if (this.mClipNotificationScrollToTop && !state.inShelf && newYTranslation < previousNotificationEnd && (!isHeadsUp || ambientState.isShadeExpanded())) {
                float overlapAmount = previousNotificationEnd - newYTranslation;
                state.clipTopAmount = (int) overlapAmount;
            } else {
                state.clipTopAmount = 0;
            }
            if (!child.isTransparent()) {
                previousNotificationEnd = newNotificationEnd;
                previousNotificationStart = newYTranslation;
            }
        }
    }

    public static boolean canChildBeDismissed(View v) {
        if (!(v instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow row = (ExpandableNotificationRow) v;
        if (row.areGutsExposed()) {
            return false;
        }
        return row.canViewBeDismissed();
    }

    private void updateDimmedActivatedHideSensitive(AmbientState ambientState, StackScrollState resultState, StackScrollAlgorithmState algorithmState) {
        boolean dimmed = ambientState.isDimmed();
        boolean dark = ambientState.isFullyDark();
        boolean hideSensitive = ambientState.isHideSensitive();
        View activatedChild = ambientState.getActivatedChild();
        int childCount = algorithmState.visibleChildren.size();
        for (int i = 0; i < childCount; i++) {
            View child = algorithmState.visibleChildren.get(i);
            ExpandableViewState childViewState = resultState.getViewStateForView(child);
            childViewState.dimmed = dimmed;
            childViewState.dark = dark;
            childViewState.hideSensitive = hideSensitive;
            boolean isActivatedChild = activatedChild == child;
            if (dimmed && isActivatedChild) {
                childViewState.zTranslation += 2.0f * ambientState.getZDistanceBetweenElements();
            }
        }
    }

    private void handleDraggedViews(AmbientState ambientState, StackScrollState resultState, StackScrollAlgorithmState algorithmState) {
        ArrayList<View> draggedViews = ambientState.getDraggedViews();
        for (View draggedView : draggedViews) {
            int childIndex = algorithmState.visibleChildren.indexOf(draggedView);
            if (childIndex >= 0 && childIndex < algorithmState.visibleChildren.size() - 1) {
                View nextChild = algorithmState.visibleChildren.get(childIndex + 1);
                if (!draggedViews.contains(nextChild)) {
                    ExpandableViewState viewState = resultState.getViewStateForView(nextChild);
                    if (ambientState.isShadeExpanded()) {
                        viewState.shadowAlpha = 1.0f;
                        viewState.hidden = false;
                    }
                }
                resultState.getViewStateForView(draggedView).alpha = draggedView.getAlpha();
            }
        }
    }

    private void initAlgorithmState(StackScrollState resultState, StackScrollAlgorithmState state, AmbientState ambientState) {
        int firstHiddenIndex;
        int iIndexOf;
        float bottomOverScroll;
        int i = 0;
        float bottomOverScroll2 = ambientState.getOverScrollAmount(false);
        int scrollY = Math.max(0, ambientState.getScrollY());
        state.scrollY = (int) (scrollY + bottomOverScroll2);
        ViewGroup hostView = resultState.getHostView();
        int childCount = hostView.getChildCount();
        state.visibleChildren.clear();
        state.visibleChildren.ensureCapacity(childCount);
        state.paddingMap.clear();
        int notGoneIndex = 0;
        ExpandableView lastView = null;
        if (ambientState.isDark()) {
            firstHiddenIndex = ambientState.hasPulsingNotifications() ? 1 : 0;
        } else {
            firstHiddenIndex = childCount;
        }
        while (i < childCount) {
            ExpandableView v = (ExpandableView) hostView.getChildAt(i);
            if (v.getVisibility() == 8) {
                bottomOverScroll = bottomOverScroll2;
                scrollY = scrollY;
            } else if (v == ambientState.getShelf()) {
                bottomOverScroll = bottomOverScroll2;
                scrollY = scrollY;
            } else {
                if (i >= firstHiddenIndex) {
                    lastView = null;
                }
                notGoneIndex = updateNotGoneIndex(resultState, state, notGoneIndex, v);
                float increasedPadding = v.getIncreasedPaddingAmount();
                if (increasedPadding != 0.0f) {
                    state.paddingMap.put(v, Float.valueOf(increasedPadding));
                    if (lastView != null) {
                        Float prevValue = state.paddingMap.get(lastView);
                        float newValue = getPaddingForValue(Float.valueOf(increasedPadding));
                        if (prevValue != null) {
                            bottomOverScroll = bottomOverScroll2;
                            float bottomOverScroll3 = getPaddingForValue(prevValue);
                            if (increasedPadding > 0.0f) {
                                newValue = NotificationUtils.interpolate(bottomOverScroll3, newValue, increasedPadding);
                            } else if (prevValue.floatValue() > 0.0f) {
                                newValue = NotificationUtils.interpolate(newValue, bottomOverScroll3, prevValue.floatValue());
                            }
                        } else {
                            bottomOverScroll = bottomOverScroll2;
                        }
                        state.paddingMap.put(lastView, Float.valueOf(newValue));
                    } else {
                        bottomOverScroll = bottomOverScroll2;
                        scrollY = scrollY;
                    }
                } else {
                    bottomOverScroll = bottomOverScroll2;
                    scrollY = scrollY;
                    if (lastView != null) {
                        state.paddingMap.put(lastView, Float.valueOf(getPaddingForValue(state.paddingMap.get(lastView))));
                    }
                }
                if (v instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow row = (ExpandableNotificationRow) v;
                    List<ExpandableNotificationRow> children = row.getNotificationChildren();
                    if (row.isSummaryWithChildren() && children != null) {
                        for (ExpandableNotificationRow childRow : children) {
                            ExpandableNotificationRow row2 = row;
                            if (childRow.getVisibility() != 8) {
                                ExpandableViewState childState = resultState.getViewStateForView(childRow);
                                childState.notGoneIndex = notGoneIndex;
                                notGoneIndex++;
                            }
                            row = row2;
                        }
                    }
                }
                lastView = v;
            }
            i++;
            bottomOverScroll2 = bottomOverScroll;
            scrollY = scrollY;
        }
        ExpandableNotificationRow expandingNotification = ambientState.getExpandingNotification();
        if (expandingNotification != null) {
            if (expandingNotification.isChildInGroup()) {
                iIndexOf = state.visibleChildren.indexOf(expandingNotification.getNotificationParent());
            } else {
                iIndexOf = state.visibleChildren.indexOf(expandingNotification);
            }
        } else {
            iIndexOf = -1;
        }
        state.indexOfExpandingNotification = iIndexOf;
    }

    private float getPaddingForValue(Float increasedPadding) {
        if (increasedPadding == null) {
            return this.mPaddingBetweenElements;
        }
        if (increasedPadding.floatValue() >= 0.0f) {
            return NotificationUtils.interpolate(this.mPaddingBetweenElements, this.mIncreasedPaddingBetweenElements, increasedPadding.floatValue());
        }
        return NotificationUtils.interpolate(0.0f, this.mPaddingBetweenElements, 1.0f + increasedPadding.floatValue());
    }

    private int updateNotGoneIndex(StackScrollState resultState, StackScrollAlgorithmState state, int notGoneIndex, ExpandableView v) {
        ExpandableViewState viewState = resultState.getViewStateForView(v);
        viewState.notGoneIndex = notGoneIndex;
        state.visibleChildren.add(v);
        return notGoneIndex + 1;
    }

    private void updatePositionsForState(StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        float currentYPosition = -algorithmState.scrollY;
        int childCount = algorithmState.visibleChildren.size();
        for (int i = 0; i < childCount; i++) {
            currentYPosition = updateChild(i, resultState, algorithmState, ambientState, currentYPosition);
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0094  */
    /* JADX WARN: Code duplicated, block: B:24:0x009b  */
    /* JADX WARN: Instruction removed from duplicated block: B:24:0x009b, please report this as an issue */
    protected float updateChild(int i, StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState, float currentYPosition) {
        float currentYPosition2;
        ExpandableView child = algorithmState.visibleChildren.get(i);
        ExpandableViewState childViewState = resultState.getViewStateForView(child);
        childViewState.location = 0;
        int paddingAfterChild = getPaddingAfterChild(algorithmState, child);
        int childHeight = getMaxAllowedChildHeight(child);
        childViewState.yTranslation = currentYPosition;
        boolean isFooterView = child instanceof FooterView;
        boolean isEmptyShadeView = child instanceof EmptyShadeView;
        childViewState.location = 4;
        float inset = ambientState.getTopPadding() + ambientState.getStackTranslation();
        if (i <= algorithmState.getIndexOfExpandingNotification()) {
            inset += ambientState.getExpandAnimationTopChange();
        }
        if (child.mustStayOnScreen() && childViewState.yTranslation >= 0.0f) {
            float end = childViewState.yTranslation + childViewState.height + inset;
            childViewState.headsUpIsVisible = end < ambientState.getMaxHeadsUpTranslation();
        }
        if (isFooterView) {
            childViewState.yTranslation = Math.min(childViewState.yTranslation, ambientState.getInnerHeight() - childHeight);
        } else {
            if (isEmptyShadeView) {
                childViewState.yTranslation = (ambientState.getInnerHeight() - childHeight) + (ambientState.getStackTranslation() * 0.25f);
            } else {
                clampPositionToShelf(child, childViewState, ambientState);
            }
            currentYPosition2 = childViewState.yTranslation + childHeight + paddingAfterChild;
            if (currentYPosition2 <= 0.0f) {
                childViewState.location = 2;
            }
            if (childViewState.location == 0) {
                Log.wtf("StackScrollAlgorithm", "Failed to assign location for child " + i);
            }
            childViewState.yTranslation += inset;
            return currentYPosition2;
        }
        currentYPosition2 = childViewState.yTranslation + childHeight + paddingAfterChild;
        if (currentYPosition2 <= 0.0f) {
            childViewState.location = 2;
        }
        if (childViewState.location == 0) {
            Log.wtf("StackScrollAlgorithm", "Failed to assign location for child " + i);
        }
        childViewState.yTranslation += inset;
        return currentYPosition2;
    }

    protected int getPaddingAfterChild(StackScrollAlgorithmState algorithmState, ExpandableView child) {
        return algorithmState.getPaddingAfterChild(child);
    }

    private void updateHeadsUpStates(StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        int childCount = algorithmState.visibleChildren.size();
        ExpandableNotificationRow topHeadsUpEntry = null;
        for (int i = 0; i < childCount; i++) {
            View child = algorithmState.visibleChildren.get(i);
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                if (row.isHeadsUp()) {
                    ExpandableViewState childState = resultState.getViewStateForView(row);
                    boolean isTopEntry = true;
                    if (topHeadsUpEntry == null && row.mustStayOnScreen() && !childState.headsUpIsVisible) {
                        topHeadsUpEntry = row;
                        childState.location = 1;
                    }
                    if (topHeadsUpEntry != row) {
                        isTopEntry = false;
                    }
                    float unmodifiedEndLocation = childState.yTranslation + childState.height;
                    if (this.mIsExpanded && row.mustStayOnScreen() && !childState.headsUpIsVisible) {
                        clampHunToTop(ambientState, row, childState);
                        if (i == 0 && ambientState.isAboveShelf(row)) {
                            clampHunToMaxTranslation(ambientState, row, childState);
                            childState.hidden = false;
                        }
                    }
                    if (row.isPinned()) {
                        childState.yTranslation = Math.max(childState.yTranslation, this.mHeadsUpInset);
                        childState.height = Math.max(row.getIntrinsicHeight(), childState.height);
                        childState.hidden = false;
                        ExpandableViewState topState = resultState.getViewStateForView(topHeadsUpEntry);
                        if (topState != null && !isTopEntry && (!this.mIsExpanded || unmodifiedEndLocation < topState.yTranslation + topState.height)) {
                            childState.height = row.getIntrinsicHeight();
                            childState.yTranslation = (topState.yTranslation + topState.height) - childState.height;
                        }
                    }
                    if (row.isHeadsUpAnimatingAway()) {
                        childState.hidden = false;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void clampHunToTop(AmbientState ambientState, ExpandableNotificationRow row, ExpandableViewState childState) {
        float newTranslation = Math.max(ambientState.getTopPadding() + ambientState.getStackTranslation(), childState.yTranslation);
        childState.height = (int) Math.max(childState.height - (newTranslation - childState.yTranslation), row.getCollapsedHeight());
        childState.yTranslation = newTranslation;
    }

    private void clampHunToMaxTranslation(AmbientState ambientState, ExpandableNotificationRow row, ExpandableViewState childState) {
        float maxHeadsUpTranslation = ambientState.getMaxHeadsUpTranslation();
        float maxShelfPosition = ambientState.getInnerHeight() + ambientState.getTopPadding() + ambientState.getStackTranslation();
        float maxHeadsUpTranslation2 = Math.min(maxHeadsUpTranslation, maxShelfPosition);
        float bottomPosition = maxHeadsUpTranslation2 - row.getCollapsedHeight();
        float newTranslation = Math.min(childState.yTranslation, bottomPosition);
        childState.height = (int) Math.min(childState.height, maxHeadsUpTranslation2 - newTranslation);
        childState.yTranslation = newTranslation;
    }

    private void clampPositionToShelf(ExpandableView child, ExpandableViewState childViewState, AmbientState ambientState) {
        if (ambientState.getShelf() == null) {
            return;
        }
        int shelfStart = ambientState.getInnerHeight() - ambientState.getShelf().getIntrinsicHeight();
        if (ambientState.isAppearing() && !child.isAboveShelf()) {
            childViewState.yTranslation = Math.max(childViewState.yTranslation, shelfStart);
        }
        childViewState.yTranslation = Math.min(childViewState.yTranslation, shelfStart);
        if (childViewState.yTranslation >= shelfStart) {
            childViewState.hidden = (child.isExpandAnimationRunning() || child.hasExpandingChild()) ? false : true;
            childViewState.inShelf = true;
            childViewState.headsUpIsVisible = false;
        }
    }

    protected int getMaxAllowedChildHeight(View child) {
        if (!(child instanceof ExpandableView)) {
            return child == null ? this.mCollapsedSize : child.getHeight();
        }
        ExpandableView expandableView = (ExpandableView) child;
        return expandableView.getIntrinsicHeight();
    }

    private void updateZValuesForState(StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        int childCount = algorithmState.visibleChildren.size();
        float childrenOnTop = 0.0f;
        for (int i = childCount - 1; i >= 0; i--) {
            childrenOnTop = updateChildZValue(i, childrenOnTop, resultState, algorithmState, ambientState);
        }
    }

    protected float updateChildZValue(int i, float childrenOnTop, StackScrollState resultState, StackScrollAlgorithmState algorithmState, AmbientState ambientState) {
        ExpandableView child = algorithmState.visibleChildren.get(i);
        ExpandableViewState childViewState = resultState.getViewStateForView(child);
        int zDistanceBetweenElements = ambientState.getZDistanceBetweenElements();
        float baseZ = ambientState.getBaseZHeight();
        if (child.mustStayOnScreen() && !childViewState.headsUpIsVisible && !ambientState.isDozingAndNotPulsing(child) && childViewState.yTranslation < ambientState.getTopPadding() + ambientState.getStackTranslation()) {
            if (childrenOnTop != 0.0f) {
                childrenOnTop += 1.0f;
            } else {
                float overlap = (ambientState.getTopPadding() + ambientState.getStackTranslation()) - childViewState.yTranslation;
                childrenOnTop += Math.min(1.0f, overlap / childViewState.height);
            }
            float overlap2 = zDistanceBetweenElements;
            childViewState.zTranslation = (overlap2 * childrenOnTop) + baseZ;
        } else if (i == 0 && ambientState.isAboveShelf(child)) {
            int shelfHeight = ambientState.getShelf() == null ? 0 : ambientState.getShelf().getIntrinsicHeight();
            float shelfStart = (ambientState.getInnerHeight() - shelfHeight) + ambientState.getTopPadding() + ambientState.getStackTranslation();
            float notificationEnd = childViewState.yTranslation + child.getPinnedHeadsUpHeight() + this.mPaddingBetweenElements;
            if (shelfStart > notificationEnd) {
                childViewState.zTranslation = baseZ;
            } else {
                float factor = (notificationEnd - shelfStart) / shelfHeight;
                childViewState.zTranslation = (zDistanceBetweenElements * Math.min(factor, 1.0f)) + baseZ;
            }
        } else {
            childViewState.zTranslation = baseZ;
        }
        childViewState.zTranslation += (1.0f - child.getHeaderVisibleAmount()) * this.mPinnedZTranslationExtra;
        return childrenOnTop;
    }

    public void setIsExpanded(boolean isExpanded) {
        this.mIsExpanded = isExpanded;
    }

    public class StackScrollAlgorithmState {
        private int indexOfExpandingNotification;
        public int scrollY;
        public final ArrayList<ExpandableView> visibleChildren = new ArrayList<>();
        public final HashMap<ExpandableView, Float> paddingMap = new HashMap<>();

        public StackScrollAlgorithmState() {
        }

        public int getPaddingAfterChild(ExpandableView child) {
            Float padding = this.paddingMap.get(child);
            if (padding == null) {
                return StackScrollAlgorithm.this.mPaddingBetweenElements;
            }
            return (int) padding.floatValue();
        }

        public int getIndexOfExpandingNotification() {
            return this.indexOfExpandingNotification;
        }
    }
}
