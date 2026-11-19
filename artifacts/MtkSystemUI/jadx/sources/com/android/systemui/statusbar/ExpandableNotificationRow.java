package com.android.systemui.statusbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.NotificationColorUtil;
import com.android.internal.widget.CachingIconView;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.AboveShelfChangedListener;
import com.android.systemui.statusbar.notification.ActivityLaunchAnimator;
import com.android.systemui.statusbar.notification.HybridNotificationView;
import com.android.systemui.statusbar.notification.NotificationInflater;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.VisualStabilityManager;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.stack.AmbientState;
import com.android.systemui.statusbar.stack.AnimationProperties;
import com.android.systemui.statusbar.stack.ExpandableViewState;
import com.android.systemui.statusbar.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.stack.StackScrollState;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableNotificationRow extends ActivatableNotificationView implements PluginListener<NotificationMenuRowPlugin> {
    private static final Property<ExpandableNotificationRow, Float> TRANSLATE_CONTENT = new FloatProperty<ExpandableNotificationRow>("translate") { // from class: com.android.systemui.statusbar.ExpandableNotificationRow.2
        @Override // android.util.FloatProperty
        public void setValue(ExpandableNotificationRow object, float value) {
            object.setTranslation(value);
        }

        @Override // android.util.Property
        public Float get(ExpandableNotificationRow object) {
            return Float.valueOf(object.getTranslation());
        }
    };
    private boolean mAboveShelf;
    private AboveShelfChangedListener mAboveShelfChangedListener;
    private String mAppName;
    private View mChildAfterViewWhenDismissed;
    private boolean mChildIsExpanding;
    private NotificationChildrenContainer mChildrenContainer;
    private ViewStub mChildrenContainerStub;
    private boolean mChildrenExpanded;
    private float mContentTransformationAmount;
    private boolean mDark;
    private boolean mDismissed;
    private boolean mEnableNonGroupedNotificationExpand;
    private NotificationData.Entry mEntry;
    private boolean mExpandAnimationRunning;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpandable;
    private boolean mExpandedWhenPinned;
    private FalsingManager mFalsingManager;
    private boolean mForceUnlocked;
    private boolean mGroupExpansionChanging;
    private NotificationGroupManager mGroupManager;
    private View mGroupParentWhenDismissed;
    private NotificationGuts mGuts;
    private ViewStub mGutsStub;
    private boolean mHasUserChangedExpansion;
    private float mHeaderVisibleAmount;
    private Consumer<Boolean> mHeadsUpAnimatingAwayListener;
    private HeadsUpManager mHeadsUpManager;
    private boolean mHeadsupDisappearRunning;
    private boolean mHideSensitiveForIntrinsicHeight;
    private boolean mIconAnimationRunning;
    private int mIconTransformContentShift;
    private int mIconTransformContentShiftNoIcon;
    private boolean mIconsVisible;
    private int mIncreasedPaddingBetweenElements;
    private boolean mIsBlockingHelperShowing;
    private boolean mIsColorized;
    private boolean mIsHeadsUp;
    private boolean mIsLastChild;
    private boolean mIsLowPriority;
    private boolean mIsPinned;
    private boolean mIsSummaryWithChildren;
    private boolean mIsSystemChildExpanded;
    private boolean mIsSystemExpanded;
    private boolean mJustClicked;
    private boolean mKeepInParent;
    private boolean mLastChronometerRunning;
    private LayoutListener mLayoutListener;
    private NotificationContentView[] mLayouts;
    private ExpansionLogger mLogger;
    private String mLoggingKey;
    private LongPressListener mLongPressListener;
    private boolean mLowPriorityStateUpdated;
    private int mMaxHeadsUpHeight;
    private int mMaxHeadsUpHeightBeforeP;
    private int mMaxHeadsUpHeightIncreased;
    private int mMaxHeadsUpHeightLegacy;
    private NotificationMenuRowPlugin mMenuRow;
    private boolean mMustStayOnScreen;
    private int mNotificationAmbientHeight;
    private int mNotificationColor;
    private int mNotificationColorAmbient;
    private final NotificationInflater mNotificationInflater;
    private int mNotificationLaunchHeight;
    private int mNotificationMaxHeight;
    private int mNotificationMinHeight;
    private int mNotificationMinHeightBeforeP;
    private int mNotificationMinHeightLarge;
    private int mNotificationMinHeightLegacy;
    private ExpandableNotificationRow mNotificationParent;
    private NotificationViewState mNotificationViewState;
    private View.OnClickListener mOnAppOpsClickListener;
    private View.OnClickListener mOnClickListener;
    private Runnable mOnDismissRunnable;
    private OnExpandClickListener mOnExpandClickListener;
    private boolean mOnKeyguard;
    private NotificationContentView mPrivateLayout;
    private NotificationContentView mPublicLayout;
    private boolean mRefocusOnDismiss;
    private boolean mRemoved;
    private BooleanSupplier mSecureStateProvider;
    private boolean mSensitive;
    private boolean mSensitiveHiddenInGeneral;
    private boolean mShowAmbient;
    private boolean mShowGroupBackgroundWhenExpanded;
    private boolean mShowNoBackground;
    private boolean mShowingPublic;
    private boolean mShowingPublicInitialized;
    private StatusBarNotification mStatusBarNotification;
    private SystemNotificationAsyncTask mSystemNotificationAsyncTask;
    private Animator mTranslateAnim;
    private ArrayList<View> mTranslateableViews;
    private float mTranslationWhenRemoved;
    private boolean mUseIncreasedCollapsedHeight;
    private boolean mUseIncreasedHeadsUpHeight;
    private boolean mUserExpanded;
    private boolean mUserLocked;
    private boolean mWasChildInGroupWhenRemoved;

    public interface ExpansionLogger {
        void logNotificationExpansion(String str, boolean z, boolean z2);
    }

    public interface LayoutListener {
        void onLayout();
    }

    public interface LongPressListener {
        boolean onLongPress(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem);
    }

    public interface OnAppOpsClickListener {
        boolean onClick(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem);
    }

    public interface OnExpandClickListener {
        void onExpandClicked(NotificationData.Entry entry, boolean z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean isSystemNotification(Context context, StatusBarNotification statusBarNotification) {
        PackageManager packageManager = StatusBar.getPackageManagerForUser(context, statusBarNotification.getUser().getIdentifier());
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(statusBarNotification.getPackageName(), 64);
            Boolean isSystemNotification = Boolean.valueOf(Utils.isSystemPackage(context.getResources(), packageManager, packageInfo));
            return isSystemNotification;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ExpandableNotifRow", "cacheIsSystemNotification: Could not find package info");
            return null;
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isGroupExpansionChanging() {
        if (isChildInGroup()) {
            return this.mNotificationParent.isGroupExpansionChanging();
        }
        return this.mGroupExpansionChanging;
    }

    public void setGroupExpansionChanging(boolean changing) {
        this.mGroupExpansionChanging = changing;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setActualHeightAnimating(boolean animating) {
        if (this.mPrivateLayout != null) {
            this.mPrivateLayout.setContentHeightAnimating(animating);
        }
    }

    public NotificationContentView getPrivateLayout() {
        return this.mPrivateLayout;
    }

    public NotificationContentView getPublicLayout() {
        return this.mPublicLayout;
    }

    public void setIconAnimationRunning(boolean running) {
        int i = 0;
        for (NotificationContentView l : this.mLayouts) {
            setIconAnimationRunning(running, l);
        }
        if (this.mIsSummaryWithChildren) {
            setIconAnimationRunningForChild(running, this.mChildrenContainer.getHeaderView());
            setIconAnimationRunningForChild(running, this.mChildrenContainer.getLowPriorityHeaderView());
            List<ExpandableNotificationRow> notificationChildren = this.mChildrenContainer.getNotificationChildren();
            while (true) {
                int i2 = i;
                int i3 = notificationChildren.size();
                if (i2 >= i3) {
                    break;
                }
                ExpandableNotificationRow child = notificationChildren.get(i2);
                child.setIconAnimationRunning(running);
                i = i2 + 1;
            }
        }
        this.mIconAnimationRunning = running;
    }

    private void setIconAnimationRunning(boolean running, NotificationContentView layout) {
        if (layout != null) {
            View contractedChild = layout.getContractedChild();
            View expandedChild = layout.getExpandedChild();
            View headsUpChild = layout.getHeadsUpChild();
            setIconAnimationRunningForChild(running, contractedChild);
            setIconAnimationRunningForChild(running, expandedChild);
            setIconAnimationRunningForChild(running, headsUpChild);
        }
    }

    private void setIconAnimationRunningForChild(boolean running, View child) {
        if (child != null) {
            ImageView icon = (ImageView) child.findViewById(R.id.icon);
            setIconRunning(icon, running);
            ImageView rightIcon = (ImageView) child.findViewById(R.id.layoutDirection);
            setIconRunning(rightIcon, running);
        }
    }

    private void setIconRunning(ImageView imageView, boolean running) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                if (running) {
                    animationDrawable.start();
                    return;
                } else {
                    animationDrawable.stop();
                    return;
                }
            }
            if (drawable instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animationDrawable2 = (AnimatedVectorDrawable) drawable;
                if (running) {
                    animationDrawable2.start();
                } else {
                    animationDrawable2.stop();
                }
            }
        }
    }

    public void updateNotification(NotificationData.Entry entry) {
        this.mEntry = entry;
        this.mStatusBarNotification = entry.notification;
        this.mNotificationInflater.inflateNotificationViews();
        cacheIsSystemNotification();
    }

    private void cacheIsSystemNotification() {
        if (this.mEntry != null && this.mEntry.mIsSystemNotification == null && this.mSystemNotificationAsyncTask.getStatus() == AsyncTask.Status.PENDING) {
            this.mSystemNotificationAsyncTask.execute(new Void[0]);
        }
    }

    public boolean getIsNonblockable() {
        boolean isNonblockable = ((NotificationBlockingHelperManager) Dependency.get(NotificationBlockingHelperManager.class)).isNonblockablePackage(this.mStatusBarNotification.getPackageName());
        if (this.mEntry != null && this.mEntry.mIsSystemNotification == null) {
            this.mSystemNotificationAsyncTask.cancel(true);
            this.mEntry.mIsSystemNotification = isSystemNotification(this.mContext, this.mStatusBarNotification);
        }
        if (!isNonblockable && this.mEntry != null && this.mEntry.mIsSystemNotification != null && this.mEntry.mIsSystemNotification.booleanValue() && this.mEntry.channel != null && !this.mEntry.channel.isBlockableSystem()) {
            return true;
        }
        return isNonblockable;
    }

    public void onNotificationUpdated() {
        for (NotificationContentView l : this.mLayouts) {
            l.onNotificationUpdated(this.mEntry);
        }
        this.mIsColorized = this.mStatusBarNotification.getNotification().isColorized();
        this.mShowingPublicInitialized = false;
        updateNotificationColor();
        if (this.mMenuRow != null) {
            this.mMenuRow.onNotificationUpdated(this.mStatusBarNotification);
            this.mMenuRow.setAppName(this.mAppName);
        }
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.recreateNotificationHeader(this.mExpandClickListener);
            this.mChildrenContainer.onNotificationUpdated();
        }
        if (this.mIconAnimationRunning) {
            setIconAnimationRunning(true);
        }
        if (this.mNotificationParent != null) {
            this.mNotificationParent.updateChildrenHeaderAppearance();
        }
        onChildrenCountChanged();
        this.mPublicLayout.updateExpandButtons(true);
        updateLimits();
        updateIconVisibilities();
        updateShelfIconColor();
        updateRippleAllowed();
    }

    @VisibleForTesting
    void updateShelfIconColor() {
        StatusBarIconView expandedIcon = this.mEntry.expandedIcon;
        boolean isPreL = Boolean.TRUE.equals(expandedIcon.getTag(com.android.systemui.R.id.icon_is_pre_L));
        boolean z = true;
        boolean colorize = !isPreL || NotificationUtils.isGrayscale(expandedIcon, NotificationColorUtil.getInstance(this.mContext));
        int color = 0;
        if (colorize) {
            NotificationHeaderView header = getVisibleNotificationHeader();
            if (header != null) {
                color = header.getOriginalIconColor();
            } else {
                NotificationData.Entry entry = this.mEntry;
                Context context = this.mContext;
                if (!this.mIsLowPriority || isExpanded()) {
                    z = false;
                }
                color = entry.getContrastedColor(context, z, getBackgroundColorWithoutTint());
            }
        }
        expandedIcon.setStaticDrawableColor(color);
    }

    public void setAboveShelfChangedListener(AboveShelfChangedListener aboveShelfChangedListener) {
        this.mAboveShelfChangedListener = aboveShelfChangedListener;
    }

    public void setSecureStateProvider(BooleanSupplier secureStateProvider) {
        this.mSecureStateProvider = secureStateProvider;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    public boolean isDimmable() {
        if (!getShowingLayout().isDimmable()) {
            return false;
        }
        return super.isDimmable();
    }

    private void updateLimits() {
        for (NotificationContentView l : this.mLayouts) {
            updateLimitsForView(l);
        }
    }

    private void updateLimitsForView(NotificationContentView layout) {
        int minHeight;
        int headsUpheight;
        boolean z = false;
        boolean customView = layout.getContractedChild().getId() != 16909350;
        boolean beforeN = this.mEntry.targetSdk < 24;
        boolean beforeP = this.mEntry.targetSdk < 28;
        if (customView && beforeP && !this.mIsSummaryWithChildren) {
            minHeight = beforeN ? this.mNotificationMinHeightLegacy : this.mNotificationMinHeightBeforeP;
        } else if (this.mUseIncreasedCollapsedHeight && layout == this.mPrivateLayout) {
            minHeight = this.mNotificationMinHeightLarge;
        } else {
            minHeight = this.mNotificationMinHeight;
        }
        if (layout.getHeadsUpChild() != null && layout.getHeadsUpChild().getId() != 16909350) {
            z = true;
        }
        boolean headsUpCustom = z;
        if (headsUpCustom && beforeP) {
            headsUpheight = beforeN ? this.mMaxHeadsUpHeightLegacy : this.mMaxHeadsUpHeightBeforeP;
        } else if (this.mUseIncreasedHeadsUpHeight && layout == this.mPrivateLayout) {
            headsUpheight = this.mMaxHeadsUpHeightIncreased;
        } else {
            headsUpheight = this.mMaxHeadsUpHeight;
        }
        NotificationViewWrapper headsUpWrapper = layout.getVisibleWrapper(2);
        if (headsUpWrapper != null) {
            headsUpheight = Math.max(headsUpheight, headsUpWrapper.getMinLayoutHeight());
        }
        layout.setHeights(minHeight, headsUpheight, this.mNotificationMaxHeight, this.mNotificationAmbientHeight);
    }

    public StatusBarNotification getStatusBarNotification() {
        return this.mStatusBarNotification;
    }

    public NotificationData.Entry getEntry() {
        return this.mEntry;
    }

    public boolean isHeadsUp() {
        return this.mIsHeadsUp;
    }

    public void setHeadsUp(boolean isHeadsUp) {
        boolean wasAboveShelf = isAboveShelf();
        int intrinsicBefore = getIntrinsicHeight();
        this.mIsHeadsUp = isHeadsUp;
        this.mPrivateLayout.setHeadsUp(isHeadsUp);
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateGroupOverflow();
        }
        if (intrinsicBefore != getIntrinsicHeight()) {
            notifyHeightChanged(false);
        }
        if (isHeadsUp) {
            this.mMustStayOnScreen = true;
            setAboveShelf(true);
        } else if (isAboveShelf() != wasAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!wasAboveShelf);
        }
    }

    public void setGroupManager(NotificationGroupManager groupManager) {
        this.mGroupManager = groupManager;
        this.mPrivateLayout.setGroupManager(groupManager);
    }

    public void setRemoteInputController(RemoteInputController r) {
        this.mPrivateLayout.setRemoteInputController(r);
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
        if (this.mMenuRow != null && this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.setAppName(this.mAppName);
        }
    }

    public void setHeaderVisibleAmount(float headerVisibleAmount) {
        if (this.mHeaderVisibleAmount != headerVisibleAmount) {
            this.mHeaderVisibleAmount = headerVisibleAmount;
            this.mPrivateLayout.setHeaderVisibleAmount(headerVisibleAmount);
            if (this.mChildrenContainer != null) {
                this.mChildrenContainer.setHeaderVisibleAmount(headerVisibleAmount);
            }
            notifyHeightChanged(false);
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public float getHeaderVisibleAmount() {
        return this.mHeaderVisibleAmount;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setHeadsUpIsVisible() {
        super.setHeadsUpIsVisible();
        this.mMustStayOnScreen = false;
    }

    public void addChildNotification(ExpandableNotificationRow row, int childIndex) {
        if (this.mChildrenContainer == null) {
            this.mChildrenContainerStub.inflate();
        }
        this.mChildrenContainer.addNotification(row, childIndex);
        onChildrenCountChanged();
        row.setIsChildInGroup(true, this);
    }

    public void removeChildNotification(ExpandableNotificationRow row) {
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.removeNotification(row);
        }
        onChildrenCountChanged();
        row.setIsChildInGroup(false, null);
        row.setBottomRoundness(0.0f, false);
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isChildInGroup() {
        return this.mNotificationParent != null;
    }

    public boolean isOnlyChildInGroup() {
        return this.mGroupManager.isOnlyChildInGroup(getStatusBarNotification());
    }

    public ExpandableNotificationRow getNotificationParent() {
        return this.mNotificationParent;
    }

    public void setIsChildInGroup(boolean isChildInGroup, ExpandableNotificationRow parent) {
        boolean childInGroup = StatusBar.ENABLE_CHILD_NOTIFICATIONS && isChildInGroup;
        if (this.mExpandAnimationRunning && !isChildInGroup && this.mNotificationParent != null) {
            this.mNotificationParent.setChildIsExpanding(false);
            this.mNotificationParent.setExtraWidthForClipping(0.0f);
            this.mNotificationParent.setMinimumHeightForClipping(0);
        }
        this.mNotificationParent = childInGroup ? parent : null;
        this.mPrivateLayout.setIsChildInGroup(childInGroup);
        this.mNotificationInflater.setIsChildInGroup(childInGroup);
        resetBackgroundAlpha();
        updateBackgroundForGroupState();
        updateClickAndFocus();
        if (this.mNotificationParent != null) {
            setOverrideTintColor(0, 0.0f);
            setDistanceToTopRoundness(-1.0f);
            this.mNotificationParent.updateBackgroundForGroupState();
        }
        updateIconVisibilities();
        updateBackgroundClipping();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() != 0 || !isChildInGroup() || isGroupExpanded()) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected boolean handleSlideBack() {
        if (this.mMenuRow != null && this.mMenuRow.isMenuVisible()) {
            animateTranslateNotification(0.0f);
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected boolean shouldHideBackground() {
        return super.shouldHideBackground() || this.mShowNoBackground;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isSummaryWithChildren() {
        return this.mIsSummaryWithChildren;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean areChildrenExpanded() {
        return this.mChildrenExpanded;
    }

    public List<ExpandableNotificationRow> getNotificationChildren() {
        if (this.mChildrenContainer == null) {
            return null;
        }
        return this.mChildrenContainer.getNotificationChildren();
    }

    public boolean applyChildOrder(List<ExpandableNotificationRow> childOrder, VisualStabilityManager visualStabilityManager, VisualStabilityManager.Callback callback) {
        return this.mChildrenContainer != null && this.mChildrenContainer.applyChildOrder(childOrder, visualStabilityManager, callback);
    }

    public void getChildrenStates(StackScrollState resultState, AmbientState ambientState) {
        if (this.mIsSummaryWithChildren) {
            ExpandableViewState parentState = resultState.getViewStateForView(this);
            this.mChildrenContainer.getState(resultState, parentState, ambientState);
        }
    }

    public void applyChildrenState(StackScrollState state) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.applyState(state);
        }
    }

    public void prepareExpansionChanged(StackScrollState state) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.prepareExpansionChanged(state);
        }
    }

    public void startChildAnimation(StackScrollState finalState, AnimationProperties properties) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.startAnimationToState(finalState, properties);
        }
    }

    public ExpandableNotificationRow getViewAtPosition(float y) {
        if (!this.mIsSummaryWithChildren || !this.mChildrenExpanded) {
            return this;
        }
        ExpandableNotificationRow view = this.mChildrenContainer.getViewAtPosition(y);
        return view == null ? this : view;
    }

    public NotificationGuts getGuts() {
        return this.mGuts;
    }

    public void setPinned(boolean pinned) {
        int intrinsicHeight = getIntrinsicHeight();
        boolean wasAboveShelf = isAboveShelf();
        this.mIsPinned = pinned;
        if (intrinsicHeight != getIntrinsicHeight()) {
            notifyHeightChanged(false);
        }
        if (pinned) {
            setIconAnimationRunning(true);
            this.mExpandedWhenPinned = false;
        } else if (this.mExpandedWhenPinned) {
            setUserExpanded(true);
        }
        setChronometerRunning(this.mLastChronometerRunning);
        if (isAboveShelf() != wasAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!wasAboveShelf);
        }
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    public boolean isPinned() {
        return this.mIsPinned;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getPinnedHeadsUpHeight() {
        return getPinnedHeadsUpHeight(true);
    }

    private int getPinnedHeadsUpHeight(boolean atLeastMinHeight) {
        if (this.mIsSummaryWithChildren) {
            return this.mChildrenContainer.getIntrinsicHeight();
        }
        if (this.mExpandedWhenPinned) {
            return Math.max(getMaxExpandHeight(), getHeadsUpHeight());
        }
        if (atLeastMinHeight) {
            return Math.max(getCollapsedHeight(), getHeadsUpHeight());
        }
        return getHeadsUpHeight();
    }

    public void setJustClicked(boolean justClicked) {
        this.mJustClicked = justClicked;
    }

    public boolean wasJustClicked() {
        return this.mJustClicked;
    }

    public void setChronometerRunning(boolean running) {
        this.mLastChronometerRunning = running;
        setChronometerRunning(running, this.mPrivateLayout);
        setChronometerRunning(running, this.mPublicLayout);
        if (this.mChildrenContainer != null) {
            List<ExpandableNotificationRow> notificationChildren = this.mChildrenContainer.getNotificationChildren();
            for (int i = 0; i < notificationChildren.size(); i++) {
                ExpandableNotificationRow child = notificationChildren.get(i);
                child.setChronometerRunning(running);
            }
        }
    }

    private void setChronometerRunning(boolean running, NotificationContentView layout) {
        if (layout != null) {
            boolean running2 = running || isPinned();
            View contractedChild = layout.getContractedChild();
            View expandedChild = layout.getExpandedChild();
            View headsUpChild = layout.getHeadsUpChild();
            setChronometerRunningForChild(running2, contractedChild);
            setChronometerRunningForChild(running2, expandedChild);
            setChronometerRunningForChild(running2, headsUpChild);
        }
    }

    private void setChronometerRunningForChild(boolean running, View child) {
        if (child != null) {
            View chronometer = child.findViewById(R.id.aerr_close);
            if (chronometer instanceof Chronometer) {
                ((Chronometer) chronometer).setStarted(running);
            }
        }
    }

    public NotificationHeaderView getNotificationHeader() {
        if (this.mIsSummaryWithChildren) {
            return this.mChildrenContainer.getHeaderView();
        }
        return this.mPrivateLayout.getNotificationHeader();
    }

    public NotificationHeaderView getVisibleNotificationHeader() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            return this.mChildrenContainer.getVisibleHeader();
        }
        return getShowingLayout().getVisibleNotificationHeader();
    }

    public NotificationHeaderView getContractedNotificationHeader() {
        if (this.mIsSummaryWithChildren) {
            return this.mChildrenContainer.getHeaderView();
        }
        return this.mPrivateLayout.getContractedNotificationHeader();
    }

    public void setOnExpandClickListener(OnExpandClickListener onExpandClickListener) {
        this.mOnExpandClickListener = onExpandClickListener;
    }

    public void setLongPressListener(LongPressListener longPressListener) {
        this.mLongPressListener = longPressListener;
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener l) {
        super.setOnClickListener(l);
        this.mOnClickListener = l;
        updateClickAndFocus();
    }

    private void updateClickAndFocus() {
        boolean normalChild = !isChildInGroup() || isGroupExpanded();
        boolean clickable = this.mOnClickListener != null && normalChild;
        if (isFocusable() != normalChild) {
            setFocusable(normalChild);
        }
        if (isClickable() != clickable) {
            setClickable(clickable);
        }
    }

    public void setHeadsUpManager(HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
    }

    public void setGutsView(NotificationMenuRowPlugin.MenuItem item) {
        if (this.mGuts != null && (item.getGutsView() instanceof NotificationGuts.GutsContent)) {
            ((NotificationGuts.GutsContent) item.getGutsView()).setGutsParent(this.mGuts);
            this.mGuts.setGutsContent((NotificationGuts.GutsContent) item.getGutsView());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener((PluginListener) this, NotificationMenuRowPlugin.class, false);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(NotificationMenuRowPlugin plugin, Context pluginContext) {
        boolean existed = this.mMenuRow.getMenuView() != null;
        if (existed) {
            removeView(this.mMenuRow.getMenuView());
        }
        this.mMenuRow = plugin;
        if (this.mMenuRow.useDefaultMenuItems()) {
            ArrayList<NotificationMenuRowPlugin.MenuItem> items = new ArrayList<>();
            items.add(NotificationMenuRow.createInfoItem(this.mContext));
            items.add(NotificationMenuRow.createSnoozeItem(this.mContext));
            items.add(NotificationMenuRow.createAppOpsItem(this.mContext));
            this.mMenuRow.setMenuItems(items);
        }
        if (existed) {
            createMenu();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(NotificationMenuRowPlugin plugin) {
        boolean existed = this.mMenuRow.getMenuView() != null;
        this.mMenuRow = new NotificationMenuRow(this.mContext);
        if (existed) {
            createMenu();
        }
    }

    public NotificationMenuRowPlugin createMenu() {
        if (this.mMenuRow.getMenuView() == null) {
            this.mMenuRow.createMenu(this, this.mStatusBarNotification);
            this.mMenuRow.setAppName(this.mAppName);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -1);
            addView(this.mMenuRow.getMenuView(), 0, lp);
        }
        return this.mMenuRow;
    }

    public NotificationMenuRowPlugin getProvider() {
        return this.mMenuRow;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableOutlineView
    public void onDensityOrFontScaleChanged() {
        super.onDensityOrFontScaleChanged();
        initDimens();
        initBackground();
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.reInflateViews(this.mExpandClickListener, this.mEntry.notification);
        }
        if (this.mGuts != null) {
            View oldGuts = this.mGuts;
            int index = indexOfChild(oldGuts);
            removeView(oldGuts);
            this.mGuts = (NotificationGuts) LayoutInflater.from(this.mContext).inflate(com.android.systemui.R.layout.notification_guts, (ViewGroup) this, false);
            this.mGuts.setVisibility(oldGuts.getVisibility());
            addView(this.mGuts, index);
        }
        View oldMenu = this.mMenuRow.getMenuView();
        if (oldMenu != null) {
            int menuIndex = indexOfChild(oldMenu);
            removeView(oldMenu);
            this.mMenuRow.createMenu(this, this.mStatusBarNotification);
            this.mMenuRow.setAppName(this.mAppName);
            addView(this.mMenuRow.getMenuView(), menuIndex);
        }
        for (NotificationContentView l : this.mLayouts) {
            l.initView();
            l.reInflateViews();
        }
        this.mNotificationInflater.onDensityOrFontScaleChanged();
        onNotificationUpdated();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        if (this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.onConfigurationChanged();
        }
    }

    public void setContentBackground(int customBackgroundColor, boolean animate, NotificationContentView notificationContentView) {
        if (getShowingLayout() == notificationContentView) {
            setTintColor(customBackgroundColor, animate);
        }
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected void setBackgroundTintColor(int color) {
        super.setBackgroundTintColor(color);
        NotificationContentView view = getShowingLayout();
        if (view != null) {
            view.setBackgroundTintColor(color);
        }
    }

    public void closeRemoteInput() {
        for (NotificationContentView l : this.mLayouts) {
            l.closeRemoteInput();
        }
    }

    public void setSingleLineWidthIndention(int indention) {
        this.mPrivateLayout.setSingleLineWidthIndention(indention);
    }

    public int getNotificationColor() {
        return this.mNotificationColor;
    }

    private void updateNotificationColor() {
        this.mNotificationColor = NotificationColorUtil.resolveContrastColor(this.mContext, getStatusBarNotification().getNotification().color, getBackgroundColorWithoutTint());
        this.mNotificationColorAmbient = NotificationColorUtil.resolveAmbientColor(this.mContext, getStatusBarNotification().getNotification().color);
    }

    public HybridNotificationView getSingleLineView() {
        return this.mPrivateLayout.getSingleLineView();
    }

    public HybridNotificationView getAmbientSingleLineView() {
        return getShowingLayout().getAmbientSingleLineChild();
    }

    public boolean isOnKeyguard() {
        return this.mOnKeyguard;
    }

    public void removeAllChildren() {
        List<ExpandableNotificationRow> notificationChildren = this.mChildrenContainer.getNotificationChildren();
        ArrayList<ExpandableNotificationRow> clonedList = new ArrayList<>(notificationChildren);
        for (int i = 0; i < clonedList.size(); i++) {
            ExpandableNotificationRow row = clonedList.get(i);
            if (!row.keepInParent()) {
                this.mChildrenContainer.removeNotification(row);
                row.setIsChildInGroup(false, null);
            }
        }
        onChildrenCountChanged();
    }

    public void setDismissed(boolean fromAccessibility) {
        List<ExpandableNotificationRow> notificationChildren;
        int i;
        setLongPressListener(null);
        this.mDismissed = true;
        this.mGroupParentWhenDismissed = this.mNotificationParent;
        this.mRefocusOnDismiss = fromAccessibility;
        this.mChildAfterViewWhenDismissed = null;
        this.mEntry.icon.setDismissed();
        if (isChildInGroup() && (i = (notificationChildren = this.mNotificationParent.getNotificationChildren()).indexOf(this)) != -1 && i < notificationChildren.size() - 1) {
            this.mChildAfterViewWhenDismissed = notificationChildren.get(i + 1);
        }
    }

    public boolean isDismissed() {
        return this.mDismissed;
    }

    public boolean keepInParent() {
        return this.mKeepInParent;
    }

    public void setKeepInParent(boolean keepInParent) {
        this.mKeepInParent = keepInParent;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isRemoved() {
        return this.mRemoved;
    }

    public void setRemoved() {
        this.mRemoved = true;
        this.mTranslationWhenRemoved = getTranslationY();
        this.mWasChildInGroupWhenRemoved = isChildInGroup();
        if (isChildInGroup()) {
            this.mTranslationWhenRemoved += getNotificationParent().getTranslationY();
        }
        this.mPrivateLayout.setRemoved();
    }

    public boolean wasChildInGroupWhenRemoved() {
        return this.mWasChildInGroupWhenRemoved;
    }

    public float getTranslationWhenRemoved() {
        return this.mTranslationWhenRemoved;
    }

    public NotificationChildrenContainer getChildrenContainer() {
        return this.mChildrenContainer;
    }

    public void setHeadsUpAnimatingAway(boolean headsUpAnimatingAway) {
        boolean wasAboveShelf = isAboveShelf();
        boolean changed = headsUpAnimatingAway != this.mHeadsupDisappearRunning;
        this.mHeadsupDisappearRunning = headsUpAnimatingAway;
        this.mPrivateLayout.setHeadsUpAnimatingAway(headsUpAnimatingAway);
        if (changed && this.mHeadsUpAnimatingAwayListener != null) {
            this.mHeadsUpAnimatingAwayListener.accept(Boolean.valueOf(headsUpAnimatingAway));
        }
        if (isAboveShelf() != wasAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!wasAboveShelf);
        }
    }

    public void setHeadsUpAnimatingAwayListener(Consumer<Boolean> listener) {
        this.mHeadsUpAnimatingAwayListener = listener;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    public boolean isHeadsUpAnimatingAway() {
        return this.mHeadsupDisappearRunning;
    }

    public View getChildAfterViewWhenDismissed() {
        return this.mChildAfterViewWhenDismissed;
    }

    public View getGroupParentWhenDismissed() {
        return this.mGroupParentWhenDismissed;
    }

    public boolean performDismissWithBlockingHelper(boolean fromAccessibility) {
        NotificationBlockingHelperManager manager = (NotificationBlockingHelperManager) Dependency.get(NotificationBlockingHelperManager.class);
        boolean isBlockingHelperShown = manager.perhapsShowBlockingHelper(this, this.mMenuRow);
        ((MetricsLogger) Dependency.get(MetricsLogger.class)).count("notification_dismissed", 1);
        performDismiss(fromAccessibility);
        return isBlockingHelperShown;
    }

    public void performDismiss(boolean fromAccessibility) {
        if (isOnlyChildInGroup()) {
            ExpandableNotificationRow groupSummary = this.mGroupManager.getLogicalGroupSummary(getStatusBarNotification());
            if (groupSummary.isClearable()) {
                groupSummary.performDismiss(fromAccessibility);
            }
        }
        setDismissed(fromAccessibility);
        if (isClearable() && this.mOnDismissRunnable != null) {
            this.mOnDismissRunnable.run();
        }
    }

    public void setBlockingHelperShowing(boolean isBlockingHelperShowing) {
        this.mIsBlockingHelperShowing = isBlockingHelperShowing;
    }

    public boolean isBlockingHelperShowing() {
        return this.mIsBlockingHelperShowing;
    }

    public void setOnDismissRunnable(Runnable onDismissRunnable) {
        this.mOnDismissRunnable = onDismissRunnable;
    }

    public View getNotificationIcon() {
        NotificationHeaderView notificationHeader = getVisibleNotificationHeader();
        if (notificationHeader != null) {
            return notificationHeader.getIcon();
        }
        return null;
    }

    public boolean isShowingIcon() {
        return (areGutsExposed() || getVisibleNotificationHeader() == null) ? false : true;
    }

    public void setContentTransformationAmount(float contentTransformationAmount, boolean isLastChild) {
        boolean changeTransformation = isLastChild != this.mIsLastChild;
        boolean changeTransformation2 = changeTransformation | (this.mContentTransformationAmount != contentTransformationAmount);
        this.mIsLastChild = isLastChild;
        this.mContentTransformationAmount = contentTransformationAmount;
        if (changeTransformation2) {
            updateContentTransformation();
        }
    }

    public void setIconsVisible(boolean iconsVisible) {
        if (iconsVisible != this.mIconsVisible) {
            this.mIconsVisible = iconsVisible;
            updateIconVisibilities();
        }
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected void onBelowSpeedBumpChanged() {
        updateIconVisibilities();
    }

    private void updateContentTransformation() {
        if (this.mExpandAnimationRunning) {
            return;
        }
        float translationY = (-this.mContentTransformationAmount) * this.mIconTransformContentShift;
        float contentAlpha = 1.0f;
        if (this.mIsLastChild) {
            float contentAlpha2 = 1.0f - this.mContentTransformationAmount;
            contentAlpha = Interpolators.ALPHA_OUT.getInterpolation(Math.min(contentAlpha2 / 0.5f, 1.0f));
            translationY *= 0.4f;
        }
        float contentAlpha3 = contentAlpha;
        for (NotificationContentView l : this.mLayouts) {
            l.setAlpha(contentAlpha3);
            l.setTranslationY(translationY);
        }
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.setAlpha(contentAlpha3);
            this.mChildrenContainer.setTranslationY(translationY);
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0011  */
    private void updateIconVisibilities() {
        boolean visible;
        if (isChildInGroup()) {
            visible = true;
        } else {
            isBelowSpeedBump();
            if (this.mIconsVisible) {
                visible = true;
            } else {
                visible = false;
            }
        }
        for (NotificationContentView l : this.mLayouts) {
            l.setIconsVisible(visible);
        }
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.setIconsVisible(visible);
        }
    }

    public int getRelativeTopPadding(View view) {
        int topPadding = 0;
        while (view.getParent() instanceof ViewGroup) {
            topPadding += view.getTop();
            view = (View) view.getParent();
            if (view instanceof ExpandableNotificationRow) {
                return topPadding;
            }
        }
        return topPadding;
    }

    public float getContentTranslation() {
        return this.mPrivateLayout.getTranslationY();
    }

    public void setIsLowPriority(boolean isLowPriority) {
        this.mIsLowPriority = isLowPriority;
        this.mPrivateLayout.setIsLowPriority(isLowPriority);
        this.mNotificationInflater.setIsLowPriority(this.mIsLowPriority);
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.setIsLowPriority(isLowPriority);
        }
    }

    public void setLowPriorityStateUpdated(boolean lowPriorityStateUpdated) {
        this.mLowPriorityStateUpdated = lowPriorityStateUpdated;
    }

    public boolean hasLowPriorityStateUpdated() {
        return this.mLowPriorityStateUpdated;
    }

    public boolean isLowPriority() {
        return this.mIsLowPriority;
    }

    public void setUseIncreasedCollapsedHeight(boolean use) {
        this.mUseIncreasedCollapsedHeight = use;
        this.mNotificationInflater.setUsesIncreasedHeight(use);
    }

    public void setUseIncreasedHeadsUpHeight(boolean use) {
        this.mUseIncreasedHeadsUpHeight = use;
        this.mNotificationInflater.setUsesIncreasedHeadsUpHeight(use);
    }

    public void setRemoteViewClickHandler(RemoteViews.OnClickHandler remoteViewClickHandler) {
        this.mNotificationInflater.setRemoteViewClickHandler(remoteViewClickHandler);
    }

    public void setInflationCallback(NotificationInflater.InflationCallback callback) {
        this.mNotificationInflater.setInflationCallback(callback);
    }

    public void setNeedsRedaction(boolean needsRedaction) {
        this.mNotificationInflater.setRedactAmbient(needsRedaction);
    }

    @VisibleForTesting
    public NotificationInflater getNotificationInflater() {
        return this.mNotificationInflater;
    }

    public int getNotificationColorAmbient() {
        return this.mNotificationColorAmbient;
    }

    public ExpandableNotificationRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHeaderVisibleAmount = 1.0f;
        this.mLastChronometerRunning = true;
        this.mExpandClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.ExpandableNotificationRow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean nowExpanded;
                if (ExpandableNotificationRow.this.shouldShowPublic() || ((ExpandableNotificationRow.this.mIsLowPriority && !ExpandableNotificationRow.this.isExpanded()) || !ExpandableNotificationRow.this.mGroupManager.isSummaryOfGroup(ExpandableNotificationRow.this.mStatusBarNotification))) {
                    if (ExpandableNotificationRow.this.mEnableNonGroupedNotificationExpand) {
                        if (v.isAccessibilityFocused()) {
                            ExpandableNotificationRow.this.mPrivateLayout.setFocusOnVisibilityChange();
                        }
                        if (ExpandableNotificationRow.this.isPinned()) {
                            nowExpanded = !ExpandableNotificationRow.this.mExpandedWhenPinned;
                            ExpandableNotificationRow.this.mExpandedWhenPinned = nowExpanded;
                        } else {
                            nowExpanded = !ExpandableNotificationRow.this.isExpanded();
                            ExpandableNotificationRow.this.setUserExpanded(nowExpanded);
                        }
                        ExpandableNotificationRow.this.notifyHeightChanged(true);
                        ExpandableNotificationRow.this.mOnExpandClickListener.onExpandClicked(ExpandableNotificationRow.this.mEntry, nowExpanded);
                        MetricsLogger.action(ExpandableNotificationRow.this.mContext, 407, nowExpanded);
                        return;
                    }
                    return;
                }
                ExpandableNotificationRow.this.mGroupExpansionChanging = true;
                boolean wasExpanded = ExpandableNotificationRow.this.mGroupManager.isGroupExpanded(ExpandableNotificationRow.this.mStatusBarNotification);
                boolean nowExpanded2 = ExpandableNotificationRow.this.mGroupManager.toggleGroupExpansion(ExpandableNotificationRow.this.mStatusBarNotification);
                ExpandableNotificationRow.this.mOnExpandClickListener.onExpandClicked(ExpandableNotificationRow.this.mEntry, nowExpanded2);
                MetricsLogger.action(ExpandableNotificationRow.this.mContext, 408, nowExpanded2);
                ExpandableNotificationRow.this.onExpansionChanged(true, wasExpanded);
            }
        };
        this.mIconsVisible = true;
        this.mSystemNotificationAsyncTask = new SystemNotificationAsyncTask();
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mNotificationInflater = new NotificationInflater(this);
        this.mMenuRow = new NotificationMenuRow(this.mContext);
        initDimens();
    }

    private void initDimens() {
        this.mNotificationMinHeightLegacy = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_min_height_legacy);
        this.mNotificationMinHeightBeforeP = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_min_height_before_p);
        this.mNotificationMinHeight = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_min_height);
        this.mNotificationMinHeightLarge = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_min_height_increased);
        this.mNotificationMaxHeight = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_max_height);
        this.mNotificationAmbientHeight = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_ambient_height);
        this.mMaxHeadsUpHeightLegacy = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_max_heads_up_height_legacy);
        this.mMaxHeadsUpHeightBeforeP = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_max_heads_up_height_before_p);
        this.mMaxHeadsUpHeight = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_max_heads_up_height);
        this.mMaxHeadsUpHeightIncreased = NotificationUtils.getFontScaledHeight(this.mContext, com.android.systemui.R.dimen.notification_max_heads_up_height_increased);
        Resources res = getResources();
        this.mIncreasedPaddingBetweenElements = res.getDimensionPixelSize(com.android.systemui.R.dimen.notification_divider_height_increased);
        this.mIconTransformContentShiftNoIcon = res.getDimensionPixelSize(com.android.systemui.R.dimen.notification_icon_transform_content_shift);
        this.mEnableNonGroupedNotificationExpand = res.getBoolean(com.android.systemui.R.bool.config_enableNonGroupedNotificationExpand);
        this.mShowGroupBackgroundWhenExpanded = res.getBoolean(com.android.systemui.R.bool.config_showGroupNotificationBgWhenExpanded);
    }

    public void reset() {
        this.mShowingPublicInitialized = false;
        onHeightReset();
        requestLayout();
    }

    public void showAppOpsIcons(ArraySet<Integer> activeOps) {
        if (this.mIsSummaryWithChildren && this.mChildrenContainer.getHeaderView() != null) {
            this.mChildrenContainer.getHeaderView().showAppOpsIcons(activeOps);
        }
        this.mPrivateLayout.showAppOpsIcons(activeOps);
        this.mPublicLayout.showAppOpsIcons(activeOps);
    }

    public View.OnClickListener getAppOpsOnClickListener() {
        return this.mOnAppOpsClickListener;
    }

    protected void setAppOpsOnClickListener(final OnAppOpsClickListener l) {
        this.mOnAppOpsClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.-$$Lambda$ExpandableNotificationRow$-ffBYE9PVKSnfJECJygWoqM2POs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExpandableNotificationRow.lambda$setAppOpsOnClickListener$0(this.f$0, l, view);
            }
        };
    }

    public static /* synthetic */ void lambda$setAppOpsOnClickListener$0(ExpandableNotificationRow expandableNotificationRow, OnAppOpsClickListener l, View v) {
        expandableNotificationRow.createMenu();
        NotificationMenuRowPlugin.MenuItem menuItem = expandableNotificationRow.getProvider().getAppOpsMenuItem(expandableNotificationRow.mContext);
        if (menuItem != null) {
            l.onClick(expandableNotificationRow, v.getWidth() / 2, v.getHeight() / 2, menuItem);
        }
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPublicLayout = (NotificationContentView) findViewById(com.android.systemui.R.id.expandedPublic);
        this.mPrivateLayout = (NotificationContentView) findViewById(com.android.systemui.R.id.expanded);
        int i = 0;
        this.mLayouts = new NotificationContentView[]{this.mPrivateLayout, this.mPublicLayout};
        for (NotificationContentView l : this.mLayouts) {
            l.setExpandClickListener(this.mExpandClickListener);
            l.setContainingNotification(this);
        }
        this.mGutsStub = (ViewStub) findViewById(com.android.systemui.R.id.notification_guts_stub);
        this.mGutsStub.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.android.systemui.statusbar.ExpandableNotificationRow.3
            @Override // android.view.ViewStub.OnInflateListener
            public void onInflate(ViewStub stub, View inflated) {
                ExpandableNotificationRow.this.mGuts = (NotificationGuts) inflated;
                ExpandableNotificationRow.this.mGuts.setClipTopAmount(ExpandableNotificationRow.this.getClipTopAmount());
                ExpandableNotificationRow.this.mGuts.setActualHeight(ExpandableNotificationRow.this.getActualHeight());
                ExpandableNotificationRow.this.mGutsStub = null;
            }
        });
        this.mChildrenContainerStub = (ViewStub) findViewById(com.android.systemui.R.id.child_container_stub);
        this.mChildrenContainerStub.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.android.systemui.statusbar.ExpandableNotificationRow.4
            @Override // android.view.ViewStub.OnInflateListener
            public void onInflate(ViewStub stub, View inflated) {
                ExpandableNotificationRow.this.mChildrenContainer = (NotificationChildrenContainer) inflated;
                ExpandableNotificationRow.this.mChildrenContainer.setIsLowPriority(ExpandableNotificationRow.this.mIsLowPriority);
                ExpandableNotificationRow.this.mChildrenContainer.setContainingNotification(ExpandableNotificationRow.this);
                ExpandableNotificationRow.this.mChildrenContainer.onNotificationUpdated();
                if (ExpandableNotificationRow.this.mShouldTranslateContents) {
                    ExpandableNotificationRow.this.mTranslateableViews.add(ExpandableNotificationRow.this.mChildrenContainer);
                }
            }
        });
        if (this.mShouldTranslateContents) {
            this.mTranslateableViews = new ArrayList<>();
            while (true) {
                int i2 = i;
                if (i2 < getChildCount()) {
                    this.mTranslateableViews.add(getChildAt(i2));
                    i = i2 + 1;
                } else {
                    this.mTranslateableViews.remove(this.mChildrenContainerStub);
                    this.mTranslateableViews.remove(this.mGutsStub);
                    return;
                }
            }
        }
    }

    private void doLongClickCallback() {
        doLongClickCallback(getWidth() / 2, getHeight() / 2);
    }

    public void doLongClickCallback(int x, int y) {
        createMenu();
        NotificationMenuRowPlugin.MenuItem menuItem = getProvider().getLongpressMenuItem(this.mContext);
        doLongClickCallback(x, y, menuItem);
    }

    private void doLongClickCallback(int x, int y, NotificationMenuRowPlugin.MenuItem menuItem) {
        if (this.mLongPressListener != null && menuItem != null) {
            this.mLongPressListener.onLongPress(this, x, y, menuItem);
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode)) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode)) {
            if (!event.isCanceled()) {
                performClick();
                return true;
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode)) {
            doLongClickCallback();
            return true;
        }
        return false;
    }

    public void resetTranslation() {
        if (this.mTranslateAnim != null) {
            this.mTranslateAnim.cancel();
        }
        if (!this.mShouldTranslateContents) {
            setTranslationX(0.0f);
        } else if (this.mTranslateableViews != null) {
            for (int i = 0; i < this.mTranslateableViews.size(); i++) {
                this.mTranslateableViews.get(i).setTranslationX(0.0f);
            }
            invalidateOutline();
            getEntry().expandedIcon.setScrollX(0);
        }
        this.mMenuRow.resetMenu();
    }

    void onGutsOpened() {
        resetTranslation();
        updateContentAccessibilityImportanceForGuts(false);
    }

    void onGutsClosed() {
        updateContentAccessibilityImportanceForGuts(true);
    }

    private void updateContentAccessibilityImportanceForGuts(boolean isEnabled) {
        if (this.mChildrenContainer != null) {
            updateChildAccessibilityImportance(this.mChildrenContainer, isEnabled);
        }
        if (this.mLayouts != null) {
            for (View view : this.mLayouts) {
                updateChildAccessibilityImportance(view, isEnabled);
            }
        }
        if (isEnabled) {
            requestAccessibilityFocus();
        }
    }

    private void updateChildAccessibilityImportance(View childView, boolean isEnabled) {
        int i;
        if (isEnabled) {
            i = 0;
        } else {
            i = 4;
        }
        childView.setImportantForAccessibility(i);
    }

    public CharSequence getActiveRemoteInputText() {
        return this.mPrivateLayout.getActiveRemoteInputText();
    }

    public void animateTranslateNotification(float leftTarget) {
        if (this.mTranslateAnim != null) {
            this.mTranslateAnim.cancel();
        }
        this.mTranslateAnim = getTranslateViewAnimator(leftTarget, null);
        if (this.mTranslateAnim != null) {
            this.mTranslateAnim.start();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setTranslation(float translationX) {
        if (areGutsExposed()) {
            return;
        }
        if (!this.mShouldTranslateContents) {
            setTranslationX(translationX);
        } else if (this.mTranslateableViews != null) {
            for (int i = 0; i < this.mTranslateableViews.size(); i++) {
                if (this.mTranslateableViews.get(i) != null) {
                    this.mTranslateableViews.get(i).setTranslationX(translationX);
                }
            }
            invalidateOutline();
            getEntry().expandedIcon.setScrollX((int) (-translationX));
        }
        if (this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.onTranslationUpdate(translationX);
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public float getTranslation() {
        if (!this.mShouldTranslateContents) {
            return getTranslationX();
        }
        if (this.mTranslateableViews != null && this.mTranslateableViews.size() > 0) {
            return this.mTranslateableViews.get(0).getTranslationX();
        }
        return 0.0f;
    }

    public Animator getTranslateViewAnimator(final float leftTarget, ValueAnimator.AnimatorUpdateListener listener) {
        if (this.mTranslateAnim != null) {
            this.mTranslateAnim.cancel();
        }
        if (areGutsExposed()) {
            return null;
        }
        ObjectAnimator translateAnim = ObjectAnimator.ofFloat(this, TRANSLATE_CONTENT, leftTarget);
        if (listener != null) {
            translateAnim.addUpdateListener(listener);
        }
        translateAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ExpandableNotificationRow.5
            boolean cancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator anim) {
                this.cancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator anim) {
                if (!this.cancelled && leftTarget == 0.0f) {
                    ExpandableNotificationRow.this.mMenuRow.resetMenu();
                    ExpandableNotificationRow.this.mTranslateAnim = null;
                }
            }
        });
        this.mTranslateAnim = translateAnim;
        return translateAnim;
    }

    public void inflateGuts() {
        if (this.mGuts == null) {
            this.mGutsStub.inflate();
        }
    }

    private void updateChildrenVisibility() {
        boolean hideContentWhileLaunching = this.mExpandAnimationRunning && this.mGuts != null && this.mGuts.isExposed();
        this.mPrivateLayout.setVisibility((shouldShowPublic() || this.mIsSummaryWithChildren || hideContentWhileLaunching) ? 4 : 0);
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.setVisibility((shouldShowPublic() || !this.mIsSummaryWithChildren || hideContentWhileLaunching) ? 4 : 0);
        }
        updateLimits();
    }

    public boolean onRequestSendAccessibilityEventInternal(View child, AccessibilityEvent event) {
        if (super.onRequestSendAccessibilityEventInternal(child, event)) {
            AccessibilityEvent record = AccessibilityEvent.obtain();
            onInitializeAccessibilityEvent(record);
            dispatchPopulateAccessibilityEvent(record);
            event.appendRecord(record);
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableView
    public void setDark(boolean dark, boolean fade, long delay) {
        super.setDark(dark, fade, delay);
        this.mDark = dark;
        if (!this.mIsHeadsUp) {
            fade = false;
        }
        NotificationContentView showing = getShowingLayout();
        if (showing != null) {
            showing.setDark(dark, fade, delay);
        }
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setDark(dark, fade, delay);
        }
        updateShelfIconColor();
    }

    public void applyExpandAnimationParams(ActivityLaunchAnimator.ExpandAnimationParameters params) {
        if (params == null) {
            return;
        }
        float zProgress = Interpolators.FAST_OUT_SLOW_IN.getInterpolation(params.getProgress(0L, 50L));
        float translationZ = MathUtils.lerp(params.getStartTranslationZ(), this.mNotificationLaunchHeight, zProgress);
        setTranslationZ(translationZ);
        float extraWidthForClipping = (params.getWidth() - getWidth()) + MathUtils.lerp(0.0f, this.mOutlineRadius * 2.0f, params.getProgress());
        setExtraWidthForClipping(extraWidthForClipping);
        int top = params.getTop();
        float interpolation = Interpolators.FAST_OUT_SLOW_IN.getInterpolation(params.getProgress());
        int startClipTopAmount = params.getStartClipTopAmount();
        if (this.mNotificationParent != null) {
            top = (int) (top - this.mNotificationParent.getTranslationY());
            this.mNotificationParent.setTranslationZ(translationZ);
            int parentStartClipTopAmount = params.getParentStartClipTopAmount();
            if (startClipTopAmount != 0) {
                int clipTopAmount = (int) MathUtils.lerp(parentStartClipTopAmount, parentStartClipTopAmount - startClipTopAmount, interpolation);
                this.mNotificationParent.setClipTopAmount(clipTopAmount);
            }
            this.mNotificationParent.setExtraWidthForClipping(extraWidthForClipping);
            this.mNotificationParent.setMinimumHeightForClipping(params.getHeight() + this.mNotificationParent.getActualHeight());
        } else if (startClipTopAmount != 0) {
            int clipTopAmount2 = (int) MathUtils.lerp(startClipTopAmount, 0.0f, interpolation);
            setClipTopAmount(clipTopAmount2);
        }
        setTranslationY(top);
        setActualHeight(params.getHeight());
        this.mBackgroundNormal.setExpandAnimationParams(params);
    }

    public void setExpandAnimationRunning(boolean expandAnimationRunning) {
        View contentView;
        if (this.mIsSummaryWithChildren) {
            contentView = this.mChildrenContainer;
        } else {
            contentView = getShowingLayout();
        }
        if (this.mGuts != null && this.mGuts.isExposed()) {
            contentView = this.mGuts;
        }
        if (expandAnimationRunning) {
            contentView.animate().alpha(0.0f).setDuration(67L).setInterpolator(Interpolators.ALPHA_OUT);
            setAboveShelf(true);
            this.mExpandAnimationRunning = true;
            this.mNotificationViewState.cancelAnimations(this);
            this.mNotificationLaunchHeight = AmbientState.getNotificationLaunchHeight(getContext());
        } else {
            this.mExpandAnimationRunning = false;
            setAboveShelf(isAboveShelf());
            if (this.mGuts != null) {
                this.mGuts.setAlpha(1.0f);
            }
            if (contentView != null) {
                contentView.setAlpha(1.0f);
            }
            setExtraWidthForClipping(0.0f);
            if (this.mNotificationParent != null) {
                this.mNotificationParent.setExtraWidthForClipping(0.0f);
                this.mNotificationParent.setMinimumHeightForClipping(0);
            }
        }
        if (this.mNotificationParent != null) {
            this.mNotificationParent.setChildIsExpanding(this.mExpandAnimationRunning);
        }
        updateChildrenVisibility();
        updateClipping();
        this.mBackgroundNormal.setExpandAnimationRunning(expandAnimationRunning);
    }

    private void setChildIsExpanding(boolean isExpanding) {
        this.mChildIsExpanding = isExpanding;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean hasExpandingChild() {
        return this.mChildIsExpanding;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    protected boolean shouldClipToActualHeight() {
        return (!super.shouldClipToActualHeight() || this.mExpandAnimationRunning || this.mChildIsExpanding) ? false : true;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isExpandAnimationRunning() {
        return this.mExpandAnimationRunning;
    }

    @Override // android.view.View
    public boolean isSoundEffectsEnabled() {
        boolean mute = (!this.mDark || this.mSecureStateProvider == null || this.mSecureStateProvider.getAsBoolean()) ? false : true;
        return !mute && super.isSoundEffectsEnabled();
    }

    public boolean isExpandable() {
        if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
            return this.mEnableNonGroupedNotificationExpand && this.mExpandable;
        }
        return !this.mChildrenExpanded;
    }

    public void setExpandable(boolean expandable) {
        this.mExpandable = expandable;
        this.mPrivateLayout.updateExpandButtons(isExpandable());
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setClipToActualHeight(boolean clipToActualHeight) {
        boolean z = true;
        super.setClipToActualHeight(clipToActualHeight || isUserLocked());
        NotificationContentView showingLayout = getShowingLayout();
        if (!clipToActualHeight && !isUserLocked()) {
            z = false;
        }
        showingLayout.setClipToActualHeight(z);
    }

    public boolean hasUserChangedExpansion() {
        return this.mHasUserChangedExpansion;
    }

    public boolean isUserExpanded() {
        return this.mUserExpanded;
    }

    public void setUserExpanded(boolean userExpanded) {
        setUserExpanded(userExpanded, false);
    }

    public void setUserExpanded(boolean userExpanded, boolean allowChildExpansion) {
        this.mFalsingManager.setNotificationExpanded();
        if (this.mIsSummaryWithChildren && !shouldShowPublic() && allowChildExpansion && !this.mChildrenContainer.showingAsLowPriority()) {
            boolean wasExpanded = this.mGroupManager.isGroupExpanded(this.mStatusBarNotification);
            this.mGroupManager.setGroupExpanded(this.mStatusBarNotification, userExpanded);
            onExpansionChanged(true, wasExpanded);
        } else if (!userExpanded || this.mExpandable) {
            boolean wasExpanded2 = isExpanded();
            this.mHasUserChangedExpansion = true;
            this.mUserExpanded = userExpanded;
            onExpansionChanged(true, wasExpanded2);
            if (!wasExpanded2 && isExpanded() && getActualHeight() != getIntrinsicHeight()) {
                notifyHeightChanged(true);
            }
        }
    }

    public void resetUserExpansion() {
        boolean changed = this.mUserExpanded;
        this.mHasUserChangedExpansion = false;
        this.mUserExpanded = false;
        if (changed && this.mIsSummaryWithChildren) {
            this.mChildrenContainer.onExpansionChanged();
        }
        updateShelfIconColor();
    }

    public boolean isUserLocked() {
        return this.mUserLocked && !this.mForceUnlocked;
    }

    public void setUserLocked(boolean userLocked) {
        this.mUserLocked = userLocked;
        this.mPrivateLayout.setUserExpanding(userLocked);
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.setUserLocked(userLocked);
            if (this.mIsSummaryWithChildren) {
                if (userLocked || !isGroupExpanded()) {
                    updateBackgroundForGroupState();
                }
            }
        }
    }

    public boolean isSystemExpanded() {
        return this.mIsSystemExpanded;
    }

    public void setSystemExpanded(boolean expand) {
        if (expand != this.mIsSystemExpanded) {
            boolean wasExpanded = isExpanded();
            this.mIsSystemExpanded = expand;
            notifyHeightChanged(false);
            onExpansionChanged(false, wasExpanded);
            if (this.mIsSummaryWithChildren) {
                this.mChildrenContainer.updateGroupOverflow();
            }
        }
    }

    public void setOnKeyguard(boolean onKeyguard) {
        if (onKeyguard != this.mOnKeyguard) {
            boolean wasAboveShelf = isAboveShelf();
            boolean wasExpanded = isExpanded();
            this.mOnKeyguard = onKeyguard;
            onExpansionChanged(false, wasExpanded);
            if (wasExpanded != isExpanded()) {
                if (this.mIsSummaryWithChildren) {
                    this.mChildrenContainer.updateGroupOverflow();
                }
                notifyHeightChanged(false);
            }
            if (isAboveShelf() != wasAboveShelf) {
                this.mAboveShelfChangedListener.onAboveShelfStateChanged(!wasAboveShelf);
            }
        }
        updateRippleAllowed();
    }

    private void updateRippleAllowed() {
        boolean allowed = isOnKeyguard() || this.mEntry.notification.getNotification().contentIntent == null;
        setRippleAllowed(allowed);
    }

    public boolean isClearable() {
        if (this.mStatusBarNotification == null || !this.mStatusBarNotification.isClearable()) {
            return false;
        }
        if (this.mIsSummaryWithChildren) {
            List<ExpandableNotificationRow> notificationChildren = this.mChildrenContainer.getNotificationChildren();
            for (int i = 0; i < notificationChildren.size(); i++) {
                ExpandableNotificationRow child = notificationChildren.get(i);
                if (!child.isClearable()) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getIntrinsicHeight() {
        if (isUserLocked()) {
            return getActualHeight();
        }
        if (this.mGuts != null && this.mGuts.isExposed()) {
            return this.mGuts.getIntrinsicHeight();
        }
        if (isChildInGroup() && !isGroupExpanded()) {
            return this.mPrivateLayout.getMinHeight();
        }
        if (this.mSensitive && this.mHideSensitiveForIntrinsicHeight) {
            return getMinHeight();
        }
        if (this.mIsSummaryWithChildren && (!this.mOnKeyguard || this.mShowAmbient)) {
            return this.mChildrenContainer.getIntrinsicHeight();
        }
        if (isHeadsUpAllowed() && (this.mIsHeadsUp || this.mHeadsupDisappearRunning)) {
            if (isPinned() || this.mHeadsupDisappearRunning) {
                return getPinnedHeadsUpHeight(true);
            }
            if (isExpanded()) {
                return Math.max(getMaxExpandHeight(), getHeadsUpHeight());
            }
            return Math.max(getCollapsedHeight(), getHeadsUpHeight());
        }
        if (isExpanded()) {
            return getMaxExpandHeight();
        }
        return getCollapsedHeight();
    }

    private boolean isHeadsUpAllowed() {
        return (this.mOnKeyguard || this.mShowAmbient) ? false : true;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isGroupExpanded() {
        return this.mGroupManager.isGroupExpanded(this.mStatusBarNotification);
    }

    private void onChildrenCountChanged() {
        this.mIsSummaryWithChildren = StatusBar.ENABLE_CHILD_NOTIFICATIONS && this.mChildrenContainer != null && this.mChildrenContainer.getNotificationChildCount() > 0;
        if (this.mIsSummaryWithChildren && this.mChildrenContainer.getHeaderView() == null) {
            this.mChildrenContainer.recreateNotificationHeader(this.mExpandClickListener);
        }
        getShowingLayout().updateBackgroundColor(false);
        this.mPrivateLayout.updateExpandButtons(isExpandable());
        updateChildrenHeaderAppearance();
        updateChildrenVisibility();
        applyChildrenRoundness();
    }

    public int getNumUniqueChannels() {
        ArraySet<NotificationChannel> channels = new ArraySet<>();
        channels.add(this.mEntry.channel);
        if (this.mIsSummaryWithChildren) {
            List<ExpandableNotificationRow> childrenRows = getNotificationChildren();
            int numChildren = childrenRows.size();
            for (int i = 0; i < numChildren; i++) {
                ExpandableNotificationRow childRow = childrenRows.get(i);
                NotificationChannel childChannel = childRow.getEntry().channel;
                StatusBarNotification childSbn = childRow.getStatusBarNotification();
                if (childSbn.getUser().equals(this.mStatusBarNotification.getUser()) && childSbn.getPackageName().equals(this.mStatusBarNotification.getPackageName())) {
                    channels.add(childChannel);
                }
            }
        }
        return channels.size();
    }

    public void updateChildrenHeaderAppearance() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateChildrenHeaderAppearance();
        }
    }

    public boolean isExpanded() {
        return isExpanded(false);
    }

    public boolean isExpanded(boolean allowOnKeyguard) {
        return (!this.mOnKeyguard || allowOnKeyguard) && ((!hasUserChangedExpansion() && (isSystemExpanded() || isSystemChildExpanded())) || isUserExpanded());
    }

    private boolean isSystemChildExpanded() {
        return this.mIsSystemChildExpanded;
    }

    public void setSystemChildExpanded(boolean expanded) {
        this.mIsSystemChildExpanded = expanded;
    }

    public void setLayoutListener(LayoutListener listener) {
        this.mLayoutListener = listener;
    }

    public void removeListener() {
        this.mLayoutListener = null;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int intrinsicBefore = getIntrinsicHeight();
        super.onLayout(changed, left, top, right, bottom);
        if (intrinsicBefore != getIntrinsicHeight()) {
            notifyHeightChanged(true);
        }
        if (this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.onHeightUpdate();
        }
        updateContentShiftHeight();
        if (this.mLayoutListener != null) {
            this.mLayoutListener.onLayout();
        }
    }

    private void updateContentShiftHeight() {
        NotificationHeaderView notificationHeader = getVisibleNotificationHeader();
        if (notificationHeader != null) {
            CachingIconView icon = notificationHeader.getIcon();
            this.mIconTransformContentShift = getRelativeTopPadding(icon) + icon.getHeight();
        } else {
            this.mIconTransformContentShift = this.mIconTransformContentShiftNoIcon;
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void notifyHeightChanged(boolean needsAnimation) {
        super.notifyHeightChanged(needsAnimation);
        getShowingLayout().requestSelectLayout(needsAnimation || isUserLocked());
    }

    public void setSensitive(boolean sensitive, boolean hideSensitive) {
        this.mSensitive = sensitive;
        this.mSensitiveHiddenInGeneral = hideSensitive;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setHideSensitiveForIntrinsicHeight(boolean hideSensitive) {
        this.mHideSensitiveForIntrinsicHeight = hideSensitive;
        if (this.mIsSummaryWithChildren) {
            List<ExpandableNotificationRow> notificationChildren = this.mChildrenContainer.getNotificationChildren();
            for (int i = 0; i < notificationChildren.size(); i++) {
                ExpandableNotificationRow child = notificationChildren.get(i);
                child.setHideSensitiveForIntrinsicHeight(hideSensitive);
            }
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setHideSensitive(boolean hideSensitive, boolean animated, long delay, long duration) {
        if (getVisibility() == 8) {
            return;
        }
        boolean oldShowingPublic = this.mShowingPublic;
        this.mShowingPublic = this.mSensitive && hideSensitive;
        if ((this.mShowingPublicInitialized && this.mShowingPublic == oldShowingPublic) || this.mPublicLayout.getChildCount() == 0) {
            return;
        }
        if (!animated) {
            this.mPublicLayout.animate().cancel();
            this.mPrivateLayout.animate().cancel();
            if (this.mChildrenContainer != null) {
                this.mChildrenContainer.animate().cancel();
                this.mChildrenContainer.setAlpha(1.0f);
            }
            this.mPublicLayout.setAlpha(1.0f);
            this.mPrivateLayout.setAlpha(1.0f);
            this.mPublicLayout.setVisibility(this.mShowingPublic ? 0 : 4);
            updateChildrenVisibility();
        } else {
            animateShowingPublic(delay, duration, this.mShowingPublic);
        }
        NotificationContentView showingLayout = getShowingLayout();
        showingLayout.updateBackgroundColor(animated);
        this.mPrivateLayout.updateExpandButtons(isExpandable());
        updateShelfIconColor();
        showingLayout.setDark(isDark(), false, 0L);
        this.mShowingPublicInitialized = true;
    }

    private void animateShowingPublic(long delay, long duration, boolean showingPublic) {
        View[] privateViews;
        int i = 0;
        if (this.mIsSummaryWithChildren) {
            privateViews = new View[]{this.mChildrenContainer};
        } else {
            privateViews = new View[]{this.mPrivateLayout};
        }
        View[] publicViews = {this.mPublicLayout};
        View[] hiddenChildren = showingPublic ? privateViews : publicViews;
        View[] shownChildren = showingPublic ? publicViews : privateViews;
        for (final View hiddenView : hiddenChildren) {
            hiddenView.setVisibility(0);
            hiddenView.animate().cancel();
            hiddenView.animate().alpha(0.0f).setStartDelay(delay).setDuration(duration).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.ExpandableNotificationRow.6
                @Override // java.lang.Runnable
                public void run() {
                    hiddenView.setVisibility(4);
                }
            });
        }
        int length = shownChildren.length;
        int i2 = 0;
        while (i2 < length) {
            View showView = shownChildren[i2];
            showView.setVisibility(i);
            showView.setAlpha(0.0f);
            showView.animate().cancel();
            showView.animate().alpha(1.0f).setStartDelay(delay).setDuration(duration);
            i2++;
            i = 0;
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean mustStayOnScreen() {
        return this.mIsHeadsUp && this.mMustStayOnScreen;
    }

    public boolean canViewBeDismissed() {
        return isClearable() && !(shouldShowPublic() && this.mSensitiveHiddenInGeneral);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldShowPublic() {
        return this.mSensitive && this.mHideSensitiveForIntrinsicHeight;
    }

    public void makeActionsVisibile() {
        setUserExpanded(true, true);
        if (isChildInGroup()) {
            this.mGroupManager.setGroupExpanded(this.mStatusBarNotification, true);
        }
        notifyHeightChanged(false);
    }

    public void setChildrenExpanded(boolean expanded, boolean animate) {
        this.mChildrenExpanded = expanded;
        if (this.mChildrenContainer != null) {
            this.mChildrenContainer.setChildrenExpanded(expanded);
        }
        updateBackgroundForGroupState();
        updateClickAndFocus();
    }

    public int getMaxExpandHeight() {
        return this.mPrivateLayout.getExpandHeight();
    }

    private int getHeadsUpHeight() {
        return this.mPrivateLayout.getHeadsUpHeight();
    }

    public boolean areGutsExposed() {
        return this.mGuts != null && this.mGuts.isExposed();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isContentExpandable() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            return true;
        }
        NotificationContentView showingLayout = getShowingLayout();
        return showingLayout.isContentExpandable();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected View getContentView() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            return this.mChildrenContainer;
        }
        return getShowingLayout();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected void onAppearAnimationFinished(boolean wasAppearing) {
        super.onAppearAnimationFinished(wasAppearing);
        if (wasAppearing) {
            if (this.mChildrenContainer != null) {
                this.mChildrenContainer.setAlpha(1.0f);
                this.mChildrenContainer.setLayerType(0, null);
            }
            for (NotificationContentView l : this.mLayouts) {
                l.setAlpha(1.0f);
                l.setLayerType(0, null);
            }
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getExtraBottomPadding() {
        if (this.mIsSummaryWithChildren && isGroupExpanded()) {
            return this.mIncreasedPaddingBetweenElements;
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setActualHeight(int height, boolean notifyListeners) {
        ViewGroup parent;
        boolean changed = height != getActualHeight();
        super.setActualHeight(height, notifyListeners);
        if (changed && isRemoved() && (parent = (ViewGroup) getParent()) != null) {
            parent.invalidate();
        }
        if (this.mGuts != null && this.mGuts.isExposed()) {
            this.mGuts.setActualHeight(height);
            return;
        }
        int contentHeight = Math.max(getMinHeight(), height);
        for (NotificationContentView l : this.mLayouts) {
            l.setContentHeight(contentHeight);
        }
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setActualHeight(height);
        }
        if (this.mGuts != null) {
            this.mGuts.setActualHeight(height);
        }
        if (this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.onHeightUpdate();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getMaxContentHeight() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            return this.mChildrenContainer.getMaxContentHeight();
        }
        NotificationContentView showingLayout = getShowingLayout();
        return showingLayout.getMaxHeight();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getMinHeight(boolean ignoreTemporaryStates) {
        if (!ignoreTemporaryStates && this.mGuts != null && this.mGuts.isExposed()) {
            return this.mGuts.getIntrinsicHeight();
        }
        if (!ignoreTemporaryStates && isHeadsUpAllowed() && this.mIsHeadsUp && this.mHeadsUpManager.isTrackingHeadsUp()) {
            return getPinnedHeadsUpHeight(false);
        }
        if (this.mIsSummaryWithChildren && !isGroupExpanded() && !shouldShowPublic()) {
            return this.mChildrenContainer.getMinHeight();
        }
        if (!ignoreTemporaryStates && isHeadsUpAllowed() && this.mIsHeadsUp) {
            return getHeadsUpHeight();
        }
        NotificationContentView showingLayout = getShowingLayout();
        return showingLayout.getMinHeight();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getCollapsedHeight() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            return this.mChildrenContainer.getCollapsedHeight();
        }
        return getMinHeight();
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setClipTopAmount(int clipTopAmount) {
        super.setClipTopAmount(clipTopAmount);
        for (NotificationContentView l : this.mLayouts) {
            l.setClipTopAmount(clipTopAmount);
        }
        if (this.mGuts != null) {
            this.mGuts.setClipTopAmount(clipTopAmount);
        }
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableOutlineView, com.android.systemui.statusbar.ExpandableView
    public void setClipBottomAmount(int clipBottomAmount) {
        if (this.mExpandAnimationRunning) {
            return;
        }
        if (clipBottomAmount != this.mClipBottomAmount) {
            super.setClipBottomAmount(clipBottomAmount);
            for (NotificationContentView l : this.mLayouts) {
                l.setClipBottomAmount(clipBottomAmount);
            }
            if (this.mGuts != null) {
                this.mGuts.setClipBottomAmount(clipBottomAmount);
            }
        }
        if (this.mChildrenContainer != null && !this.mChildIsExpanding) {
            this.mChildrenContainer.setClipBottomAmount(clipBottomAmount);
        }
    }

    public NotificationContentView getShowingLayout() {
        return shouldShowPublic() ? this.mPublicLayout : this.mPrivateLayout;
    }

    public void setLegacy(boolean legacy) {
        for (NotificationContentView l : this.mLayouts) {
            l.setLegacy(legacy);
        }
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected void updateBackgroundTint() {
        super.updateBackgroundTint();
        updateBackgroundForGroupState();
        if (this.mIsSummaryWithChildren) {
            List<ExpandableNotificationRow> notificationChildren = this.mChildrenContainer.getNotificationChildren();
            for (int i = 0; i < notificationChildren.size(); i++) {
                ExpandableNotificationRow child = notificationChildren.get(i);
                child.updateBackgroundForGroupState();
            }
        }
    }

    public void onFinishedExpansionChange() {
        this.mGroupExpansionChanging = false;
        updateBackgroundForGroupState();
    }

    public void updateBackgroundForGroupState() {
        int i = 0;
        if (this.mIsSummaryWithChildren) {
            this.mShowNoBackground = (this.mShowGroupBackgroundWhenExpanded || !isGroupExpanded() || isGroupExpansionChanging() || isUserLocked()) ? false : true;
            this.mChildrenContainer.updateHeaderForExpansion(this.mShowNoBackground);
            List<ExpandableNotificationRow> children = this.mChildrenContainer.getNotificationChildren();
            while (true) {
                int i2 = i;
                int i3 = children.size();
                if (i2 >= i3) {
                    break;
                }
                children.get(i2).updateBackgroundForGroupState();
                i = i2 + 1;
            }
        } else if (isChildInGroup()) {
            int childColor = getShowingLayout().getBackgroundColorForExpansionState();
            boolean showBackground = isGroupExpanded() || ((this.mNotificationParent.isGroupExpansionChanging() || this.mNotificationParent.isUserLocked()) && childColor != 0);
            this.mShowNoBackground = showBackground ? false : true;
        } else {
            this.mShowNoBackground = false;
        }
        updateOutline();
        updateBackground();
    }

    public int getPositionOfChild(ExpandableNotificationRow childRow) {
        if (this.mIsSummaryWithChildren) {
            return this.mChildrenContainer.getPositionInLinearLayout(childRow);
        }
        return 0;
    }

    public void setExpansionLogger(ExpansionLogger logger, String key) {
        this.mLogger = logger;
        this.mLoggingKey = key;
    }

    public void onExpandedByGesture(boolean userExpanded) {
        int event = 409;
        if (this.mGroupManager.isSummaryOfGroup(getStatusBarNotification())) {
            event = 410;
        }
        MetricsLogger.action(this.mContext, event, userExpanded);
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public float getIncreasedPaddingAmount() {
        if (this.mIsSummaryWithChildren) {
            if (isGroupExpanded()) {
                return 1.0f;
            }
            if (isUserLocked()) {
                return this.mChildrenContainer.getIncreasedPaddingAmount();
            }
            return 0.0f;
        }
        if (!isColorized()) {
            return 0.0f;
        }
        if (!this.mIsLowPriority || isExpanded()) {
            return -1.0f;
        }
        return 0.0f;
    }

    private boolean isColorized() {
        return this.mIsColorized && this.mBgTint != 0;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView
    protected boolean disallowSingleClick(MotionEvent event) {
        if (areGutsExposed()) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        NotificationHeaderView header = getVisibleNotificationHeader();
        if (header != null && header.isInTouchRect(x - getTranslation(), y)) {
            return true;
        }
        if ((!this.mIsSummaryWithChildren || shouldShowPublic()) && getShowingLayout().disallowSingleClick(x, y)) {
            return true;
        }
        return super.disallowSingleClick(event);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExpansionChanged(boolean userAction, boolean wasExpanded) {
        boolean nowExpanded = isExpanded();
        if (this.mIsSummaryWithChildren && (!this.mIsLowPriority || wasExpanded)) {
            nowExpanded = this.mGroupManager.isGroupExpanded(this.mStatusBarNotification);
        }
        if (nowExpanded != wasExpanded) {
            updateShelfIconColor();
            if (this.mLogger != null) {
                this.mLogger.logNotificationExpansion(this.mLoggingKey, userAction, nowExpanded);
            }
            if (this.mIsSummaryWithChildren) {
                this.mChildrenContainer.onExpansionChanged();
            }
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
        if (canViewBeDismissed()) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        }
        boolean expandable = shouldShowPublic();
        boolean isExpanded = false;
        if (!expandable) {
            if (this.mIsSummaryWithChildren) {
                expandable = true;
                if (!this.mIsLowPriority || isExpanded()) {
                    isExpanded = isGroupExpanded();
                }
            } else {
                expandable = this.mPrivateLayout.isContentExpandable();
                isExpanded = isExpanded();
            }
        }
        if (expandable) {
            if (isExpanded) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            } else {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            }
        }
        NotificationMenuRowPlugin provider = getProvider();
        if (provider != null) {
            NotificationMenuRowPlugin.MenuItem snoozeMenu = provider.getSnoozeMenuItem(getContext());
            if (snoozeMenu != null) {
                AccessibilityNodeInfo.AccessibilityAction action = new AccessibilityNodeInfo.AccessibilityAction(com.android.systemui.R.id.action_snooze, getContext().getResources().getString(com.android.systemui.R.string.notification_menu_snooze_action));
                info.addAction(action);
            }
        }
    }

    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (super.performAccessibilityActionInternal(action, arguments)) {
            return true;
        }
        if (action == 32) {
            doLongClickCallback();
            return true;
        }
        if (action == 262144 || action == 524288) {
            this.mExpandClickListener.onClick(this);
            return true;
        }
        if (action == 1048576) {
            performDismissWithBlockingHelper(true);
            return true;
        }
        if (action == com.android.systemui.R.id.action_snooze) {
            NotificationMenuRowPlugin provider = getProvider();
            if (provider == null) {
                provider = createMenu();
            }
            NotificationMenuRowPlugin.MenuItem snoozeMenu = provider.getSnoozeMenuItem(getContext());
            if (snoozeMenu != null) {
                doLongClickCallback(getWidth() / 2, getHeight() / 2, snoozeMenu);
            }
            return true;
        }
        return false;
    }

    public boolean shouldRefocusOnDismiss() {
        return this.mRefocusOnDismiss || isAccessibilityFocused();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public ExpandableViewState createNewViewState(StackScrollState stackScrollState) {
        this.mNotificationViewState = new NotificationViewState(stackScrollState);
        return this.mNotificationViewState;
    }

    public NotificationViewState getViewState() {
        return this.mNotificationViewState;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public boolean isAboveShelf() {
        return !isOnKeyguard() && (this.mIsPinned || this.mHeadsupDisappearRunning || ((this.mIsHeadsUp && this.mAboveShelf) || this.mExpandAnimationRunning || this.mChildIsExpanding));
    }

    public void setShowAmbient(boolean showAmbient) {
        if (showAmbient != this.mShowAmbient) {
            this.mShowAmbient = showAmbient;
            if (this.mChildrenContainer != null) {
                this.mChildrenContainer.notifyShowAmbientChanged();
            }
            notifyHeightChanged(false);
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    public boolean topAmountNeedsClipping() {
        if (isGroupExpanded() || isGroupExpansionChanging() || getShowingLayout().shouldClipToRounding(true, false)) {
            return true;
        }
        return (this.mGuts == null || this.mGuts.getAlpha() == 0.0f) ? false : true;
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableOutlineView
    protected boolean childNeedsClipping(View child) {
        if (child instanceof NotificationContentView) {
            NotificationContentView contentView = (NotificationContentView) child;
            if (isClippingNeeded()) {
                return true;
            }
            if (!hasNoRounding()) {
                if (contentView.shouldClipToRounding(getCurrentTopRoundness() != 0.0f, getCurrentBottomRoundness() != 0.0f)) {
                    return true;
                }
            }
        } else if (child == this.mChildrenContainer) {
            if (!this.mChildIsExpanding && (isClippingNeeded() || !hasNoRounding())) {
                return true;
            }
        } else if (child instanceof NotificationGuts) {
            return !hasNoRounding();
        }
        return super.childNeedsClipping(child);
    }

    @Override // com.android.systemui.statusbar.ActivatableNotificationView, com.android.systemui.statusbar.ExpandableOutlineView
    protected void applyRoundness() {
        super.applyRoundness();
        applyChildrenRoundness();
    }

    private void applyChildrenRoundness() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setCurrentBottomRoundness(getCurrentBottomRoundness());
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableOutlineView
    public Path getCustomClipPath(View child) {
        if (child instanceof NotificationGuts) {
            return getClipPath(true, false);
        }
        if (child instanceof NotificationChildrenContainer) {
            return getClipPath(false, true);
        }
        return super.getCustomClipPath(child);
    }

    private boolean hasNoRounding() {
        return getCurrentBottomRoundness() == 0.0f && getCurrentTopRoundness() == 0.0f;
    }

    public boolean isShowingAmbient() {
        return this.mShowAmbient;
    }

    public void setAboveShelf(boolean aboveShelf) {
        boolean wasAboveShelf = isAboveShelf();
        this.mAboveShelf = aboveShelf;
        if (isAboveShelf() != wasAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!wasAboveShelf);
        }
    }

    public static class NotificationViewState extends ExpandableViewState {
        private final StackScrollState mOverallState;

        private NotificationViewState(StackScrollState stackScrollState) {
            this.mOverallState = stackScrollState;
        }

        @Override // com.android.systemui.statusbar.stack.ExpandableViewState, com.android.systemui.statusbar.stack.ViewState
        public void applyToView(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) view;
                if (row.isExpandAnimationRunning()) {
                    return;
                }
                handleFixedTranslationZ(row);
                super.applyToView(view);
                row.applyChildrenState(this.mOverallState);
            }
        }

        private void handleFixedTranslationZ(ExpandableNotificationRow row) {
            if (row.hasExpandingChild()) {
                this.zTranslation = row.getTranslationZ();
                this.clipTopAmount = row.getClipTopAmount();
            }
        }

        @Override // com.android.systemui.statusbar.stack.ViewState
        protected void onYTranslationAnimationFinished(View view) {
            super.onYTranslationAnimationFinished(view);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) view;
                if (row.isHeadsUpAnimatingAway()) {
                    row.setHeadsUpAnimatingAway(false);
                }
            }
        }

        @Override // com.android.systemui.statusbar.stack.ExpandableViewState, com.android.systemui.statusbar.stack.ViewState
        public void animateTo(View child, AnimationProperties properties) {
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                if (row.isExpandAnimationRunning()) {
                    return;
                }
                handleFixedTranslationZ(row);
                super.animateTo(child, properties);
                row.startChildAnimation(this.mOverallState, properties);
            }
        }
    }

    @VisibleForTesting
    protected void setChildrenContainer(NotificationChildrenContainer childrenContainer) {
        this.mChildrenContainer = childrenContainer;
    }

    @VisibleForTesting
    protected void setPrivateLayout(NotificationContentView privateLayout) {
        this.mPrivateLayout = privateLayout;
    }

    @VisibleForTesting
    protected void setPublicLayout(NotificationContentView publicLayout) {
        this.mPublicLayout = publicLayout;
    }

    private class SystemNotificationAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private SystemNotificationAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voids) {
            return ExpandableNotificationRow.isSystemNotification(ExpandableNotificationRow.this.mContext, ExpandableNotificationRow.this.mStatusBarNotification);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean result) {
            if (ExpandableNotificationRow.this.mEntry != null) {
                ExpandableNotificationRow.this.mEntry.mIsSystemNotification = result;
            }
        }
    }
}
