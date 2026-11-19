package com.android.systemui.statusbar.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.SwipeHelper;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.ActivatableNotificationView;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.FooterView;
import com.android.systemui.statusbar.NotificationBlockingHelperManager;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationGuts;
import com.android.systemui.statusbar.NotificationListContainer;
import com.android.systemui.statusbar.NotificationLogger;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationSnooze;
import com.android.systemui.statusbar.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.ActivityLaunchAnimator;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.HeadsUpUtil;
import com.android.systemui.statusbar.policy.ScrollAdapter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationStackScrollLayout extends ViewGroup implements ExpandHelper.Callback, SwipeHelper.Callback, NotificationMenuRowPlugin.OnMenuEventListener, ExpandableView.OnHeightChangedListener, NotificationListContainer, VisibilityLocationProvider, NotificationGroupManager.OnGroupChangeListener, ScrollAdapter {
    private static final Property<NotificationStackScrollLayout, Float> DARK_AMOUNT = new FloatProperty<NotificationStackScrollLayout>("darkAmount") { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.7
        @Override // android.util.FloatProperty
        public void setValue(NotificationStackScrollLayout object, float value) {
            object.setDarkAmount(value);
        }

        @Override // android.util.Property
        public Float get(NotificationStackScrollLayout object) {
            return Float.valueOf(object.getDarkAmount());
        }
    };
    private boolean mActivateNeedsAnimation;
    private int mActivePointerId;
    private ArrayList<View> mAddedHeadsUpChildren;
    private final AmbientState mAmbientState;
    private boolean mAnimateNextBackgroundBottom;
    private boolean mAnimateNextBackgroundTop;
    private Runnable mAnimateScroll;
    private ArrayList<AnimationEvent> mAnimationEvents;
    private HashSet<Runnable> mAnimationFinishedRunnables;
    private boolean mAnimationRunning;
    private boolean mAnimationsEnabled;
    private int mAntiBurnInOffsetX;
    private final Rect mBackgroundAnimationRect;
    private Rect mBackgroundBounds;
    private final Paint mBackgroundPaint;
    private final Path mBackgroundPath;
    private boolean mBackwardScrollable;
    private int mBgColor;
    private ObjectAnimator mBottomAnimator;
    private int mBottomInset;
    private int mBottomMargin;
    private int mCachedBackgroundColor;
    private boolean mChangePositionInProgress;
    boolean mCheckForLeavebehind;
    private boolean mChildTransferInProgress;
    private ArrayList<View> mChildrenChangingPositions;
    private HashSet<View> mChildrenToAddAnimated;
    private ArrayList<View> mChildrenToRemoveAnimated;
    private boolean mChildrenUpdateRequested;
    private ViewTreeObserver.OnPreDrawListener mChildrenUpdater;
    private HashSet<ExpandableView> mClearTransientViewsWhenFinished;
    private final Rect mClipRect;
    private int mCollapsedSize;
    private int mContentHeight;
    private boolean mContinuousShadowUpdate;
    private int mCornerRadius;
    private NotificationMenuRowPlugin mCurrMenuRow;
    private Rect mCurrentBounds;
    private int mCurrentStackHeight;
    private StackScrollState mCurrentStackScrollState;
    private float mDarkAmount;
    private ObjectAnimator mDarkAmountAnimator;
    private int mDarkAnimationOriginIndex;
    private boolean mDarkNeedsAnimation;
    private int mDarkSeparatorPadding;
    private int mDarkTopPadding;
    private float mDimAmount;
    private ValueAnimator mDimAnimator;
    private Animator.AnimatorListener mDimEndListener;
    private ValueAnimator.AnimatorUpdateListener mDimUpdateListener;
    private boolean mDimmedNeedsAnimation;
    private boolean mDisallowDismissInThisMotion;
    private boolean mDisallowScrollingInThisMotion;
    private boolean mDismissAllInProgress;
    private boolean mDontClampNextScroll;
    private boolean mDontReportNextOverScroll;
    private int mDownX;
    private ArrayList<View> mDragAnimPendingChildren;
    private boolean mDrawBackgroundAsSrc;
    protected EmptyShadeView mEmptyShadeView;
    private Rect mEndAnimationRect;
    private boolean mEverythingNeedsAnimation;
    private ExpandHelper mExpandHelper;
    private View mExpandedGroupView;
    private float mExpandedHeight;
    private ArrayList<BiConsumer<Float, Float>> mExpandedHeightListeners;
    private boolean mExpandedInThisMotion;
    private boolean mExpandingNotification;
    private boolean mFadeNotificationsOnDismiss;
    private boolean mFadingOut;
    private FalsingManager mFalsingManager;
    private Runnable mFinishScrollingCallback;
    private ActivatableNotificationView mFirstVisibleBackgroundChild;
    protected FooterView mFooterView;
    private boolean mForceNoOverlappingRendering;
    private View mForcedScroll;
    private boolean mForwardScrollable;
    private HashSet<View> mFromMoreCardAdditions;
    private boolean mGenerateChildOrderChangedEvent;
    private long mGoToFullShadeDelay;
    private boolean mGoToFullShadeNeedsAnimation;
    private boolean mGroupExpandedForMeasure;
    private NotificationGroupManager mGroupManager;
    private boolean mHeadsUpAnimatingAway;
    private HeadsUpAppearanceController mHeadsUpAppearanceController;
    private HashSet<Pair<ExpandableNotificationRow, Boolean>> mHeadsUpChangeAnimations;
    private boolean mHeadsUpGoingAwayAnimationsAllowed;
    private int mHeadsUpInset;
    private HeadsUpManagerPhone mHeadsUpManager;
    private boolean mHideSensitiveNeedsAnimation;
    private boolean mInHeadsUpPinnedMode;
    private int mIncreasedPaddingBetweenElements;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private int mIntrinsicContentHeight;
    private int mIntrinsicPadding;
    private boolean mIsBeingDragged;
    private boolean mIsClipped;
    private boolean mIsExpanded;
    private boolean mIsExpansionChanging;
    private int mLastMotionY;
    private ActivatableNotificationView mLastVisibleBackgroundChild;
    private NotificationLogger.OnChildLocationsChangedListener mListener;
    private ExpandableNotificationRow.LongPressListener mLongPressListener;
    private int mMaxDisplayedNotifications;
    private int mMaxLayoutHeight;
    private float mMaxOverScroll;
    private int mMaxScrollAfterExpand;
    private int mMaxTopPadding;
    private int mMaximumVelocity;
    private View mMenuExposedView;
    private int mMinInteractionHeight;
    private float mMinTopOverScrollToEscape;
    private int mMinimumVelocity;
    private boolean mNeedViewResizeAnimation;
    private View mNeedingPulseAnimation;
    private boolean mNeedsAnimation;
    private boolean mNoAmbient;
    private OnEmptySpaceClickListener mOnEmptySpaceClickListener;
    private ExpandableView.OnHeightChangedListener mOnHeightChangedListener;
    private boolean mOnlyScrollingInThisMotion;
    private float mOverScrolledBottomPixels;
    private float mOverScrolledTopPixels;
    private int mOverflingDistance;
    private OnOverscrollTopChangedListener mOverscrollTopChangedListener;
    private int mOwnScrollY;
    private int mPaddingBetweenElements;
    private boolean mPanelTracking;
    private boolean mParentNotFullyVisible;
    private boolean mPulsing;
    protected ViewGroup mQsContainer;
    private boolean mQsExpanded;
    private float mQsExpansionFraction;
    private Runnable mReclamp;
    private int mRegularTopPadding;
    private Rect mRequestedClipBounds;
    private NotificationRoundnessManager mRoundnessManager;
    private ViewTreeObserver.OnPreDrawListener mRunningAnimationUpdater;
    private ScrimController mScrimController;
    private boolean mScrollable;
    private boolean mScrolledToTopOnFirstDown;
    private OverScroller mScroller;
    protected boolean mScrollingEnabled;
    private final int mSeparatorThickness;
    private final int mSeparatorWidth;
    private ViewTreeObserver.OnPreDrawListener mShadowUpdater;
    private NotificationShelf mShelf;
    private final boolean mShouldDrawNotificationBackground;
    private boolean mShouldShowShelfOnly;
    private int mSidePaddings;
    private ArrayList<View> mSnappedBackChildren;
    private PorterDuffXfermode mSrcMode;
    protected final StackScrollAlgorithm mStackScrollAlgorithm;
    private float mStackTranslation;
    private Rect mStartAnimationRect;
    private final StackStateAnimator mStateAnimator;
    private StatusBar mStatusBar;
    private int mStatusBarHeight;
    private int mStatusBarState;
    private NotificationSwipeHelper mSwipeHelper;
    private ArrayList<View> mSwipedOutViews;
    private boolean mSwipingInProgress;
    private int[] mTempInt2;
    private final ArrayList<Pair<ExpandableNotificationRow, Boolean>> mTmpList;
    private ArrayList<ExpandableView> mTmpSortedChildren;
    private ObjectAnimator mTopAnimator;
    private int mTopPadding;
    private boolean mTopPaddingNeedsAnimation;
    private float mTopPaddingOverflow;
    private boolean mTouchIsClick;
    private int mTouchSlop;
    private boolean mTrackingHeadsUp;
    private View mTranslatingParentView;
    private boolean mUsingLightTheme;
    private VelocityTracker mVelocityTracker;
    private Comparator<ExpandableView> mViewPositionComparator;

    public interface OnEmptySpaceClickListener {
        void onEmptySpaceClicked(float f, float f2);
    }

    public interface OnOverscrollTopChangedListener {
        void flingTopOverscroll(float f, boolean z);

        void onOverscrollTopChanged(float f, boolean z);
    }

    public NotificationStackScrollLayout(Context context) {
        this(context, null);
    }

    public NotificationStackScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationStackScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NotificationStackScrollLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCurrentStackHeight = Integer.MAX_VALUE;
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPath = new Path();
        this.mActivePointerId = -1;
        this.mBottomInset = 0;
        this.mCurrentStackScrollState = new StackScrollState(this);
        this.mChildrenToAddAnimated = new HashSet<>();
        this.mAddedHeadsUpChildren = new ArrayList<>();
        this.mChildrenToRemoveAnimated = new ArrayList<>();
        this.mSnappedBackChildren = new ArrayList<>();
        this.mDragAnimPendingChildren = new ArrayList<>();
        this.mChildrenChangingPositions = new ArrayList<>();
        this.mFromMoreCardAdditions = new HashSet<>();
        this.mAnimationEvents = new ArrayList<>();
        this.mSwipedOutViews = new ArrayList<>();
        this.mStateAnimator = new StackStateAnimator(this);
        this.mIsExpanded = true;
        this.mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                NotificationStackScrollLayout.this.updateForcedScroll();
                NotificationStackScrollLayout.this.updateChildren();
                NotificationStackScrollLayout.this.mChildrenUpdateRequested = false;
                NotificationStackScrollLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        this.mTempInt2 = new int[2];
        this.mAnimationFinishedRunnables = new HashSet<>();
        this.mClearTransientViewsWhenFinished = new HashSet<>();
        this.mHeadsUpChangeAnimations = new HashSet<>();
        this.mRoundnessManager = new NotificationRoundnessManager();
        this.mTmpList = new ArrayList<>();
        this.mRunningAnimationUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                NotificationStackScrollLayout.this.onPreDrawDuringAnimation();
                return true;
            }
        };
        this.mBackgroundBounds = new Rect();
        this.mStartAnimationRect = new Rect();
        this.mEndAnimationRect = new Rect();
        this.mCurrentBounds = new Rect(-1, -1, -1, -1);
        this.mBottomAnimator = null;
        this.mTopAnimator = null;
        this.mFirstVisibleBackgroundChild = null;
        this.mLastVisibleBackgroundChild = null;
        this.mTmpSortedChildren = new ArrayList<>();
        this.mDimEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationStackScrollLayout.this.mDimAnimator = null;
            }
        };
        this.mDimUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                NotificationStackScrollLayout.this.setDimAmount(((Float) animation.getAnimatedValue()).floatValue());
            }
        };
        this.mShadowUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.5
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                NotificationStackScrollLayout.this.updateViewShadows();
                return true;
            }
        };
        this.mViewPositionComparator = new Comparator<ExpandableView>() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.6
            @Override // java.util.Comparator
            public int compare(ExpandableView view, ExpandableView otherView) {
                float endY = view.getTranslationY() + view.getActualHeight();
                float otherEndY = otherView.getTranslationY() + otherView.getActualHeight();
                if (endY < otherEndY) {
                    return -1;
                }
                if (endY > otherEndY) {
                    return 1;
                }
                return 0;
            }
        };
        this.mSrcMode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        this.mDarkAmount = 0.0f;
        this.mMaxDisplayedNotifications = -1;
        this.mClipRect = new Rect();
        this.mHeadsUpGoingAwayAnimationsAllowed = true;
        this.mAnimateScroll = new Runnable() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$NotificationStackScrollLayout$jMWJf69jcb2Er7Sg4rletxrm5pw
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.animateScroll();
            }
        };
        this.mBackgroundAnimationRect = new Rect();
        this.mExpandedHeightListeners = new ArrayList<>();
        this.mReclamp = new Runnable() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.8
            @Override // java.lang.Runnable
            public void run() {
                int range = NotificationStackScrollLayout.this.getScrollRange();
                NotificationStackScrollLayout.this.mScroller.startScroll(NotificationStackScrollLayout.this.mScrollX, NotificationStackScrollLayout.this.mOwnScrollY, 0, range - NotificationStackScrollLayout.this.mOwnScrollY);
                NotificationStackScrollLayout.this.mDontReportNextOverScroll = true;
                NotificationStackScrollLayout.this.mDontClampNextScroll = true;
                NotificationStackScrollLayout.this.animateScroll();
            }
        };
        Resources res = getResources();
        this.mAmbientState = new AmbientState(context);
        this.mBgColor = context.getColor(R.color.notification_shade_background_color);
        int minHeight = res.getDimensionPixelSize(R.dimen.notification_min_height);
        int maxHeight = res.getDimensionPixelSize(R.dimen.notification_max_height);
        this.mExpandHelper = new ExpandHelper(getContext(), this, minHeight, maxHeight);
        this.mExpandHelper.setEventSource(this);
        this.mExpandHelper.setScrollAdapter(this);
        this.mSwipeHelper = new NotificationSwipeHelper(0, this, getContext());
        this.mStackScrollAlgorithm = createStackScrollAlgorithm(context);
        initView(context);
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mShouldDrawNotificationBackground = res.getBoolean(R.bool.config_drawNotificationBackground);
        this.mFadeNotificationsOnDismiss = res.getBoolean(R.bool.config_fadeNotificationsOnDismiss);
        this.mSeparatorWidth = res.getDimensionPixelSize(R.dimen.widget_separator_width);
        this.mSeparatorThickness = res.getDimensionPixelSize(R.dimen.widget_separator_thickness);
        this.mDarkSeparatorPadding = res.getDimensionPixelSize(R.dimen.widget_bottom_separator_padding);
        this.mRoundnessManager.setAnimatedChildren(this.mChildrenToAddAnimated);
        this.mRoundnessManager.setOnRoundingChangedCallback(new Runnable() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$NotificationStackScrollLayout$5BC2kaMAloRxl8auhuScFksFNjA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.invalidate();
            }
        });
        final NotificationRoundnessManager notificationRoundnessManager = this.mRoundnessManager;
        Objects.requireNonNull(notificationRoundnessManager);
        addOnExpandedHeightListener(new BiConsumer() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$bam6n-VlBWgcqhwrPMDo0VFka_4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                notificationRoundnessManager.setExpanded(((Float) obj).floatValue(), ((Float) obj2).floatValue());
            }
        });
        final NotificationBlockingHelperManager blockingHelperManager = (NotificationBlockingHelperManager) Dependency.get(NotificationBlockingHelperManager.class);
        addOnExpandedHeightListener(new BiConsumer() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$NotificationStackScrollLayout$ORqWctSAOFbrSZfQ2fKGWKeQ46w
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                blockingHelperManager.setNotificationShadeExpanded(((Float) obj).floatValue());
            }
        });
        updateWillNotDraw();
        this.mBackgroundPaint.setAntiAlias(true);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public NotificationSwipeActionHelper getSwipeActionHelper() {
        return this.mSwipeHelper;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
    public void onMenuClicked(View view, int x, int y, NotificationMenuRowPlugin.MenuItem item) {
        if (this.mLongPressListener == null) {
            return;
        }
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) view;
            MetricsLogger.action(this.mContext, 333, row.getStatusBarNotification().getPackageName());
        }
        this.mLongPressListener.onLongPress(view, x, y, item);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
    public void onMenuReset(View row) {
        if (this.mTranslatingParentView != null && row == this.mTranslatingParentView) {
            this.mMenuExposedView = null;
            this.mTranslatingParentView = null;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
    public void onMenuShown(View row) {
        this.mMenuExposedView = this.mTranslatingParentView;
        if (row instanceof ExpandableNotificationRow) {
            MetricsLogger.action(this.mContext, 332, ((ExpandableNotificationRow) row).getStatusBarNotification().getPackageName());
        }
        this.mSwipeHelper.onMenuShown(row);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mShouldDrawNotificationBackground) {
            if (this.mCurrentBounds.top < this.mCurrentBounds.bottom || this.mAmbientState.isDark()) {
                drawBackground(canvas);
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        int lockScreenLeft = this.mSidePaddings;
        int lockScreenRight = getWidth() - this.mSidePaddings;
        int lockScreenTop = this.mCurrentBounds.top;
        int lockScreenBottom = this.mCurrentBounds.bottom;
        int darkLeft = (getWidth() / 2) - (this.mSeparatorWidth / 2);
        int darkRight = this.mSeparatorWidth + darkLeft;
        int darkTop = (int) (this.mRegularTopPadding + (this.mSeparatorThickness / 2.0f));
        int darkBottom = this.mSeparatorThickness + darkTop;
        if (!this.mAmbientState.hasPulsingNotifications()) {
            if (this.mAmbientState.isFullyDark()) {
                if (this.mFirstVisibleBackgroundChild != null) {
                    canvas.drawRect(darkLeft, darkTop, darkRight, darkBottom, this.mBackgroundPaint);
                }
            } else {
                float inverseDark = 1.0f - this.mDarkAmount;
                float yProgress = Interpolators.FAST_OUT_SLOW_IN.getInterpolation(inverseDark);
                float xProgress = Interpolators.FAST_OUT_SLOW_IN.getInterpolation(2.0f * inverseDark);
                this.mBackgroundAnimationRect.set((int) MathUtils.lerp(darkLeft, lockScreenLeft, xProgress), (int) MathUtils.lerp(darkTop, lockScreenTop, yProgress), (int) MathUtils.lerp(darkRight, lockScreenRight, xProgress), (int) MathUtils.lerp(darkBottom, lockScreenBottom, yProgress));
                if (!this.mAmbientState.isDark() || this.mFirstVisibleBackgroundChild != null) {
                    canvas.drawRoundRect(this.mBackgroundAnimationRect.left, this.mBackgroundAnimationRect.top, this.mBackgroundAnimationRect.right, this.mBackgroundAnimationRect.bottom, this.mCornerRadius, this.mCornerRadius, this.mBackgroundPaint);
                }
            }
        }
        updateClipping();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackgroundDimming() {
        if (!this.mShouldDrawNotificationBackground) {
            return;
        }
        float alpha = 0.7f + (0.3f * (1.0f - this.mDimAmount));
        float alpha2 = alpha * (1.0f - this.mDarkAmount);
        int scrimColor = this.mScrimController.getBackgroundColor();
        int awakeColor = ColorUtils.blendARGB(scrimColor, this.mBgColor, alpha2);
        float colorInterpolation = Interpolators.DECELERATE_QUINT.getInterpolation(this.mDarkAmount);
        int color = ColorUtils.blendARGB(awakeColor, -1, colorInterpolation);
        if (this.mCachedBackgroundColor != color) {
            this.mCachedBackgroundColor = color;
            this.mBackgroundPaint.setColor(color);
            invalidate();
        }
    }

    private void initView(Context context) {
        this.mScroller = new OverScroller(getContext());
        setDescendantFocusability(262144);
        setClipChildren(false);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mOverflingDistance = configuration.getScaledOverflingDistance();
        Resources res = context.getResources();
        this.mCollapsedSize = res.getDimensionPixelSize(R.dimen.notification_min_height);
        this.mStackScrollAlgorithm.initView(context);
        this.mAmbientState.reload(context);
        this.mPaddingBetweenElements = Math.max(1, res.getDimensionPixelSize(R.dimen.notification_divider_height));
        this.mIncreasedPaddingBetweenElements = res.getDimensionPixelSize(R.dimen.notification_divider_height_increased);
        this.mMinTopOverScrollToEscape = res.getDimensionPixelSize(R.dimen.min_top_overscroll_to_qs);
        this.mStatusBarHeight = res.getDimensionPixelSize(R.dimen.status_bar_height);
        this.mBottomMargin = res.getDimensionPixelSize(R.dimen.notification_panel_margin_bottom);
        this.mSidePaddings = res.getDimensionPixelSize(R.dimen.notification_side_paddings);
        this.mMinInteractionHeight = res.getDimensionPixelSize(R.dimen.notification_min_interaction_height);
        this.mCornerRadius = res.getDimensionPixelSize(Utils.getThemeAttr(this.mContext, android.R.attr.dialogCornerRadius));
        this.mHeadsUpInset = this.mStatusBarHeight + res.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding);
    }

    public void setDrawBackgroundAsSrc(boolean asSrc) {
        this.mDrawBackgroundAsSrc = asSrc;
        updateSrcDrawing();
    }

    private void updateSrcDrawing() {
        if (!this.mShouldDrawNotificationBackground) {
            return;
        }
        this.mBackgroundPaint.setXfermode((!this.mDrawBackgroundAsSrc || this.mFadingOut || this.mParentNotFullyVisible) ? null : this.mSrcMode);
        invalidate();
    }

    private void notifyHeightChangeListener(ExpandableView view) {
        notifyHeightChangeListener(view, false);
    }

    private void notifyHeightChangeListener(ExpandableView view, boolean needsAnimation) {
        if (this.mOnHeightChangedListener != null) {
            this.mOnHeightChangedListener.onHeightChanged(view, needsAnimation);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int childWidthSpec = View.MeasureSpec.makeMeasureSpec(width - (this.mSidePaddings * 2), View.MeasureSpec.getMode(widthMeasureSpec));
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            measureChild(getChildAt(i), childWidthSpec, heightMeasureSpec);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float centerX = getWidth() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float width = child.getMeasuredWidth();
            float height = child.getMeasuredHeight();
            child.layout((int) (centerX - (width / 2.0f)), 0, (int) ((width / 2.0f) + centerX), (int) height);
        }
        setMaxLayoutHeight(getHeight());
        updateContentHeight();
        clampScrollPosition();
        requestChildrenUpdate();
        updateFirstAndLastBackgroundViews();
        updateAlgorithmLayoutMinHeight();
    }

    private void requestAnimationOnViewResize(ExpandableNotificationRow row) {
        if (this.mAnimationsEnabled) {
            if (this.mIsExpanded || (row != null && row.isPinned())) {
                this.mNeedViewResizeAnimation = true;
                this.mNeedsAnimation = true;
            }
        }
    }

    public void updateSpeedBumpIndex(int newIndex, boolean noAmbient) {
        this.mAmbientState.setSpeedBumpIndex(newIndex);
        this.mNoAmbient = noAmbient;
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void setChildLocationsChangedListener(NotificationLogger.OnChildLocationsChangedListener listener) {
        this.mListener = listener;
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer, com.android.systemui.statusbar.notification.VisibilityLocationProvider
    public boolean isInVisibleLocation(ExpandableNotificationRow row) {
        ExpandableViewState childViewState = this.mCurrentStackScrollState.getViewStateForView(row);
        if (childViewState == null || (childViewState.location & 5) == 0 || row.getVisibility() != 0) {
            return false;
        }
        return true;
    }

    private void setMaxLayoutHeight(int maxLayoutHeight) {
        this.mMaxLayoutHeight = maxLayoutHeight;
        this.mShelf.setMaxLayoutHeight(maxLayoutHeight);
        updateAlgorithmHeightAndPadding();
    }

    private void updateAlgorithmHeightAndPadding() {
        this.mTopPadding = (int) MathUtils.lerp(this.mRegularTopPadding, this.mDarkTopPadding, this.mDarkAmount);
        this.mAmbientState.setLayoutHeight(getLayoutHeight());
        updateAlgorithmLayoutMinHeight();
        this.mAmbientState.setTopPadding(this.mTopPadding);
    }

    private void updateAlgorithmLayoutMinHeight() {
        this.mAmbientState.setLayoutMinHeight((this.mQsExpanded || isHeadsUpTransition()) ? getLayoutMinHeight() : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChildren() {
        float currVelocity;
        updateScrollStateForAddedChildren();
        AmbientState ambientState = this.mAmbientState;
        if (this.mScroller.isFinished()) {
            currVelocity = 0.0f;
        } else {
            currVelocity = this.mScroller.getCurrVelocity();
        }
        ambientState.setCurrentScrollVelocity(currVelocity);
        this.mAmbientState.setScrollY(this.mOwnScrollY);
        this.mStackScrollAlgorithm.getStackScrollState(this.mAmbientState, this.mCurrentStackScrollState);
        if (!isCurrentlyAnimating() && !this.mNeedsAnimation) {
            applyCurrentState();
        } else {
            startAnimationToState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPreDrawDuringAnimation() {
        this.mShelf.updateAppearance();
        updateClippingToTopRoundedCorner();
        if (!this.mNeedsAnimation && !this.mChildrenUpdateRequested) {
            updateBackground();
        }
    }

    private void updateClippingToTopRoundedCorner() {
        Float clipStart = Float.valueOf(this.mTopPadding + this.mStackTranslation + this.mAmbientState.getExpandAnimationTopChange());
        Float clipEnd = Float.valueOf(clipStart.floatValue() + this.mCornerRadius);
        boolean first = true;
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child.getVisibility() != 8) {
                float start = child.getTranslationY();
                float end = child.getActualHeight() + start;
                boolean z = true;
                boolean clip = (clipStart.floatValue() > start && clipStart.floatValue() < end) || (clipEnd.floatValue() >= start && clipEnd.floatValue() <= end);
                if (first && this.mOwnScrollY == 0) {
                    z = false;
                }
                child.setDistanceToTopRoundness(clip & z ? Math.max(start - clipStart.floatValue(), 0.0f) : -1.0f);
                first = false;
            }
        }
    }

    private void updateScrollStateForAddedChildren() {
        int padding;
        if (this.mChildrenToAddAnimated.isEmpty()) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (this.mChildrenToAddAnimated.contains(child)) {
                int startingPosition = getPositionInLinearLayout(child);
                float increasedPaddingAmount = child.getIncreasedPaddingAmount();
                if (increasedPaddingAmount == 1.0f) {
                    padding = this.mIncreasedPaddingBetweenElements;
                } else {
                    padding = increasedPaddingAmount == -1.0f ? 0 : this.mPaddingBetweenElements;
                }
                int childHeight = getIntrinsicHeight(child) + padding;
                if (startingPosition < this.mOwnScrollY) {
                    setOwnScrollY(this.mOwnScrollY + childHeight);
                }
            }
        }
        clampScrollPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForcedScroll() {
        if (this.mForcedScroll != null && (!this.mForcedScroll.hasFocus() || !this.mForcedScroll.isAttachedToWindow())) {
            this.mForcedScroll = null;
        }
        if (this.mForcedScroll != null) {
            ExpandableView expandableView = (ExpandableView) this.mForcedScroll;
            int positionInLinearLayout = getPositionInLinearLayout(expandableView);
            int targetScroll = targetScrollForView(expandableView, positionInLinearLayout);
            int outOfViewScroll = expandableView.getIntrinsicHeight() + positionInLinearLayout;
            int targetScroll2 = Math.max(0, Math.min(targetScroll, getScrollRange()));
            if (this.mOwnScrollY < targetScroll2 || outOfViewScroll < this.mOwnScrollY) {
                setOwnScrollY(targetScroll2);
            }
        }
    }

    private void requestChildrenUpdate() {
        if (!this.mChildrenUpdateRequested) {
            getViewTreeObserver().addOnPreDrawListener(this.mChildrenUpdater);
            this.mChildrenUpdateRequested = true;
            invalidate();
        }
    }

    private boolean isCurrentlyAnimating() {
        return this.mStateAnimator.isRunning();
    }

    private void clampScrollPosition() {
        int scrollRange = getScrollRange();
        if (scrollRange < this.mOwnScrollY) {
            setOwnScrollY(scrollRange);
        }
    }

    public int getTopPadding() {
        return this.mTopPadding;
    }

    private void setTopPadding(int topPadding, boolean animate) {
        if (this.mRegularTopPadding != topPadding) {
            this.mRegularTopPadding = topPadding;
            this.mDarkTopPadding = this.mDarkSeparatorPadding + topPadding;
            this.mAmbientState.setDarkTopPadding(this.mDarkTopPadding);
            updateAlgorithmHeightAndPadding();
            updateContentHeight();
            if (animate && this.mAnimationsEnabled && this.mIsExpanded) {
                this.mTopPaddingNeedsAnimation = true;
                this.mNeedsAnimation = true;
            }
            requestChildrenUpdate();
            notifyHeightChangeListener(null, animate);
        }
    }

    public void setExpandedHeight(float height) {
        float translationY;
        int stackStartPosition;
        float translationY2;
        int stackHeight;
        this.mExpandedHeight = height;
        setIsExpanded(height > 0.0f);
        int minExpansionHeight = getMinExpansionHeight();
        if (height < minExpansionHeight) {
            this.mClipRect.left = 0;
            this.mClipRect.right = getWidth();
            this.mClipRect.top = 0;
            this.mClipRect.bottom = (int) height;
            height = minExpansionHeight;
            setRequestedClipBounds(this.mClipRect);
        } else {
            setRequestedClipBounds(null);
        }
        float appearEndPosition = getAppearEndPosition();
        float appearStartPosition = getAppearStartPosition();
        float appearFraction = 1.0f;
        boolean appearing = height < appearEndPosition;
        this.mAmbientState.setAppearing(appearing);
        if (!appearing) {
            translationY2 = 0.0f;
            if (this.mShouldShowShelfOnly) {
                stackStartPosition = this.mTopPadding + this.mShelf.getIntrinsicHeight();
            } else if (this.mQsExpanded) {
                int stackStartPosition2 = (this.mContentHeight - this.mTopPadding) + this.mIntrinsicPadding;
                int stackEndPosition = this.mMaxTopPadding + this.mShelf.getIntrinsicHeight();
                if (stackStartPosition2 <= stackEndPosition) {
                    stackHeight = stackEndPosition;
                } else {
                    stackHeight = (int) NotificationUtils.interpolate(stackStartPosition2, stackEndPosition, this.mQsExpansionFraction);
                }
                stackStartPosition = stackHeight;
            } else {
                stackStartPosition = (int) height;
            }
        } else {
            appearFraction = getAppearFraction(height);
            if (appearFraction >= 0.0f) {
                translationY = NotificationUtils.interpolate(getExpandTranslationStart(), 0.0f, appearFraction);
            } else {
                float translationY3 = height - appearStartPosition;
                translationY = translationY3 + getExpandTranslationStart();
            }
            if (isHeadsUpTransition()) {
                int stackHeight2 = this.mFirstVisibleBackgroundChild.getPinnedHeadsUpHeight();
                translationY2 = MathUtils.lerp(this.mHeadsUpInset - this.mTopPadding, 0.0f, appearFraction);
                stackStartPosition = stackHeight2;
            } else {
                float f = translationY;
                stackStartPosition = (int) (height - translationY);
                translationY2 = f;
            }
        }
        if (stackStartPosition != this.mCurrentStackHeight) {
            this.mCurrentStackHeight = stackStartPosition;
            updateAlgorithmHeightAndPadding();
            requestChildrenUpdate();
        }
        setStackTranslation(translationY2);
        for (int i = 0; i < this.mExpandedHeightListeners.size(); i++) {
            BiConsumer<Float, Float> listener = this.mExpandedHeightListeners.get(i);
            listener.accept(Float.valueOf(this.mExpandedHeight), Float.valueOf(appearFraction));
        }
    }

    private void setRequestedClipBounds(Rect clipRect) {
        this.mRequestedClipBounds = clipRect;
        updateClipping();
    }

    public int getIntrinsicContentHeight() {
        return this.mIntrinsicContentHeight;
    }

    public void updateClipping() {
        boolean clipped = false;
        boolean animatingClipping = this.mDarkAmount > 0.0f && this.mDarkAmount < 1.0f;
        if (this.mRequestedClipBounds != null && !this.mInHeadsUpPinnedMode && !this.mHeadsUpAnimatingAway) {
            clipped = true;
        }
        if (this.mIsClipped != clipped) {
            this.mIsClipped = clipped;
            updateFadingState();
        }
        if (animatingClipping) {
            setClipBounds(this.mBackgroundAnimationRect);
        } else if (clipped) {
            setClipBounds(this.mRequestedClipBounds);
        } else {
            setClipBounds(null);
        }
    }

    private float getExpandTranslationStart() {
        return -this.mTopPadding;
    }

    private float getAppearStartPosition() {
        if (isHeadsUpTransition()) {
            return this.mHeadsUpInset + this.mFirstVisibleBackgroundChild.getPinnedHeadsUpHeight();
        }
        return getMinExpansionHeight();
    }

    private int getTopHeadsUpPinnedHeight() {
        ExpandableNotificationRow groupSummary;
        NotificationData.Entry topEntry = this.mHeadsUpManager.getTopEntry();
        if (topEntry == null) {
            return 0;
        }
        ExpandableNotificationRow row = topEntry.row;
        if (row.isChildInGroup() && (groupSummary = this.mGroupManager.getGroupSummary(row.getStatusBarNotification())) != null) {
            row = groupSummary;
        }
        return row.getPinnedHeadsUpHeight();
    }

    private float getAppearEndPosition() {
        int appearPosition;
        int notGoneChildCount = getNotGoneChildCount();
        if (this.mEmptyShadeView.getVisibility() == 8 && notGoneChildCount != 0) {
            if (isHeadsUpTransition() || (this.mHeadsUpManager.hasPinnedHeadsUp() && !this.mAmbientState.isDark())) {
                appearPosition = getTopHeadsUpPinnedHeight();
            } else {
                appearPosition = 0;
                if (notGoneChildCount >= 1 && this.mShelf.getVisibility() != 8) {
                    appearPosition = 0 + this.mShelf.getIntrinsicHeight();
                }
            }
        } else {
            appearPosition = this.mEmptyShadeView.getHeight();
        }
        return (onKeyguard() ? this.mTopPadding : this.mIntrinsicPadding) + appearPosition;
    }

    private boolean isHeadsUpTransition() {
        return this.mTrackingHeadsUp && this.mFirstVisibleBackgroundChild != null && this.mAmbientState.isAboveShelf(this.mFirstVisibleBackgroundChild);
    }

    public float getAppearFraction(float height) {
        float appearEndPosition = getAppearEndPosition();
        float appearStartPosition = getAppearStartPosition();
        return (height - appearStartPosition) / (appearEndPosition - appearStartPosition);
    }

    public float getStackTranslation() {
        return this.mStackTranslation;
    }

    private void setStackTranslation(float stackTranslation) {
        if (stackTranslation != this.mStackTranslation) {
            this.mStackTranslation = stackTranslation;
            this.mAmbientState.setStackTranslation(stackTranslation);
            requestChildrenUpdate();
        }
    }

    private int getLayoutHeight() {
        return Math.min(this.mMaxLayoutHeight, this.mCurrentStackHeight);
    }

    public int getFirstItemMinHeight() {
        ExpandableView firstChild = getFirstChildNotGone();
        return firstChild != null ? firstChild.getMinHeight() : this.mCollapsedSize;
    }

    public void setLongPressListener(ExpandableNotificationRow.LongPressListener listener) {
        this.mLongPressListener = listener;
    }

    public void setQsContainer(ViewGroup qsContainer) {
        this.mQsContainer = qsContainer;
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public void onChildDismissed(View view) {
        ExpandableNotificationRow row = (ExpandableNotificationRow) view;
        if (!row.isDismissed()) {
            handleChildViewDismissed(view);
        }
        ViewGroup transientContainer = row.getTransientContainer();
        if (transientContainer != null) {
            transientContainer.removeTransientView(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChildViewDismissed(View view) {
        if (this.mDismissAllInProgress) {
            return;
        }
        boolean isBlockingHelperShown = false;
        setSwipingInProgress(false);
        if (this.mDragAnimPendingChildren.contains(view)) {
            this.mDragAnimPendingChildren.remove(view);
        }
        this.mAmbientState.onDragFinished(view);
        updateContinuousShadowDrawing();
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) view;
            if (row.isHeadsUp()) {
                this.mHeadsUpManager.addSwipedOutNotification(row.getStatusBarNotification().getKey());
            }
            isBlockingHelperShown = row.performDismissWithBlockingHelper(false);
        }
        if (!isBlockingHelperShown) {
            this.mSwipedOutViews.add(view);
        }
        this.mFalsingManager.onNotificationDismissed();
        if (this.mFalsingManager.shouldEnforceBouncer()) {
            this.mStatusBar.executeRunnableDismissingKeyguard(null, null, false, true, false);
        }
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public void onChildSnappedBack(View animView, float targetLeft) {
        this.mAmbientState.onDragFinished(animView);
        updateContinuousShadowDrawing();
        if (!this.mDragAnimPendingChildren.contains(animView)) {
            if (this.mAnimationsEnabled) {
                this.mSnappedBackChildren.add(animView);
                this.mNeedsAnimation = true;
            }
            requestChildrenUpdate();
        } else {
            this.mDragAnimPendingChildren.remove(animView);
        }
        if (this.mCurrMenuRow != null && targetLeft == 0.0f) {
            this.mCurrMenuRow.resetMenu();
            this.mCurrMenuRow = null;
        }
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public boolean updateSwipeProgress(View animView, boolean dismissable, float swipeProgress) {
        return !this.mFadeNotificationsOnDismiss;
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public void onBeginDrag(View v) {
        this.mFalsingManager.onNotificatonStartDismissing();
        setSwipingInProgress(true);
        this.mAmbientState.onBeginDrag(v);
        updateContinuousShadowDrawing();
        if (this.mAnimationsEnabled && (this.mIsExpanded || !isPinnedHeadsUp(v))) {
            this.mDragAnimPendingChildren.add(v);
            this.mNeedsAnimation = true;
        }
        requestChildrenUpdate();
    }

    public static boolean isPinnedHeadsUp(View v) {
        if (!(v instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow row = (ExpandableNotificationRow) v;
        return row.isHeadsUp() && row.isPinned();
    }

    private boolean isHeadsUp(View v) {
        if (v instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) v;
            return row.isHeadsUp();
        }
        return false;
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public void onDragCancelled(View v) {
        this.mFalsingManager.onNotificatonStopDismissing();
        setSwipingInProgress(false);
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public float getFalsingThresholdFactor() {
        return this.mStatusBar.isWakeUpComingFromTouch() ? 1.5f : 1.0f;
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public View getChildAtPosition(MotionEvent ev) {
        View child = getChildAtPosition(ev.getX(), ev.getY());
        if (child instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) child;
            ExpandableNotificationRow parent = row.getNotificationParent();
            if (parent != null && parent.areChildrenExpanded()) {
                if (parent.areGutsExposed() || this.mMenuExposedView == parent || (parent.getNotificationChildren().size() == 1 && parent.isClearable())) {
                    return parent;
                }
                return child;
            }
            return child;
        }
        return child;
    }

    public ExpandableView getClosestChildAtRawPosition(float touchX, float touchY) {
        getLocationOnScreen(this.mTempInt2);
        float localTouchY = touchY - this.mTempInt2[1];
        ExpandableView closestChild = null;
        float minDist = Float.MAX_VALUE;
        int count = getChildCount();
        for (int childIdx = 0; childIdx < count; childIdx++) {
            ExpandableView slidingChild = (ExpandableView) getChildAt(childIdx);
            if (slidingChild.getVisibility() != 8 && !(slidingChild instanceof StackScrollerDecorView)) {
                float childTop = slidingChild.getTranslationY();
                float top = slidingChild.getClipTopAmount() + childTop;
                float bottom = (slidingChild.getActualHeight() + childTop) - slidingChild.getClipBottomAmount();
                float dist = Math.min(Math.abs(top - localTouchY), Math.abs(bottom - localTouchY));
                if (dist < minDist) {
                    closestChild = slidingChild;
                    minDist = dist;
                }
            }
        }
        return closestChild;
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public ExpandableView getChildAtRawPosition(float touchX, float touchY) {
        getLocationOnScreen(this.mTempInt2);
        return getChildAtPosition(touchX - this.mTempInt2[0], touchY - this.mTempInt2[1]);
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public ExpandableView getChildAtPosition(float touchX, float touchY) {
        return getChildAtPosition(touchX, touchY, true);
    }

    private ExpandableView getChildAtPosition(float touchX, float touchY, boolean requireMinHeight) {
        int count = getChildCount();
        for (int childIdx = 0; childIdx < count; childIdx++) {
            ExpandableView slidingChild = (ExpandableView) getChildAt(childIdx);
            if (slidingChild.getVisibility() == 0 && !(slidingChild instanceof StackScrollerDecorView)) {
                float childTop = slidingChild.getTranslationY();
                float top = slidingChild.getClipTopAmount() + childTop;
                float bottom = (slidingChild.getActualHeight() + childTop) - slidingChild.getClipBottomAmount();
                int right = getWidth();
                if ((bottom - top >= this.mMinInteractionHeight || !requireMinHeight) && touchY >= top && touchY <= bottom && touchX >= 0 && touchX <= right) {
                    if (slidingChild instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow row = (ExpandableNotificationRow) slidingChild;
                        if (this.mIsExpanded || !row.isHeadsUp() || !row.isPinned() || this.mHeadsUpManager.getTopEntry().row == row || this.mGroupManager.getGroupSummary(this.mHeadsUpManager.getTopEntry().row.getStatusBarNotification()) == row) {
                            return row.getViewAtPosition(touchY - childTop);
                        }
                    } else {
                        return slidingChild;
                    }
                }
            }
        }
        return null;
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public boolean canChildBeExpanded(View v) {
        return (v instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) v).isExpandable() && !((ExpandableNotificationRow) v).areGutsExposed() && (this.mIsExpanded || !((ExpandableNotificationRow) v).isPinned());
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public void setUserExpandedChild(View v, boolean userExpanded) {
        if (v instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) v;
            if (userExpanded && onKeyguard()) {
                row.setUserLocked(false);
                updateContentHeight();
                notifyHeightChangeListener(row);
            } else {
                row.setUserExpanded(userExpanded, true);
                row.onExpandedByGesture(userExpanded);
            }
        }
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public void setExpansionCancelled(View v) {
        if (v instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) v).setGroupExpansionChanging(false);
        }
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public void setUserLockedChild(View v, boolean userLocked) {
        if (v instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) v).setUserLocked(userLocked);
        }
        cancelLongPress();
        requestDisallowInterceptTouchEvent(true);
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public void expansionStateChanged(boolean isExpanding) {
        this.mExpandingNotification = isExpanding;
        if (!this.mExpandedInThisMotion) {
            this.mMaxScrollAfterExpand = this.mOwnScrollY;
            this.mExpandedInThisMotion = true;
        }
    }

    @Override // com.android.systemui.ExpandHelper.Callback
    public int getMaxExpandHeight(ExpandableView view) {
        return view.getMaxContentHeight();
    }

    public void setScrollingEnabled(boolean enable) {
        this.mScrollingEnabled = enable;
    }

    public void lockScrollTo(View v) {
        if (this.mForcedScroll == v) {
            return;
        }
        this.mForcedScroll = v;
        scrollTo(v);
    }

    public boolean scrollTo(View v) {
        ExpandableView expandableView = (ExpandableView) v;
        int positionInLinearLayout = getPositionInLinearLayout(v);
        int targetScroll = targetScrollForView(expandableView, positionInLinearLayout);
        int outOfViewScroll = expandableView.getIntrinsicHeight() + positionInLinearLayout;
        if (this.mOwnScrollY >= targetScroll && outOfViewScroll >= this.mOwnScrollY) {
            return false;
        }
        this.mScroller.startScroll(this.mScrollX, this.mOwnScrollY, 0, targetScroll - this.mOwnScrollY);
        this.mDontReportNextOverScroll = true;
        animateScroll();
        return true;
    }

    private int targetScrollForView(ExpandableView v, int positionInLinearLayout) {
        return (((v.getIntrinsicHeight() + positionInLinearLayout) + getImeInset()) - getHeight()) + getTopPadding();
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        this.mBottomInset = insets.getSystemWindowInsetBottom();
        int range = getScrollRange();
        if (this.mOwnScrollY > range) {
            removeCallbacks(this.mReclamp);
            postDelayed(this.mReclamp, 50L);
        } else if (this.mForcedScroll != null) {
            scrollTo(this.mForcedScroll);
        }
        return insets;
    }

    public void setExpandingEnabled(boolean enable) {
        this.mExpandHelper.setEnabled(enable);
    }

    private boolean isScrollingEnabled() {
        return this.mScrollingEnabled;
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public boolean canChildBeDismissed(View v) {
        return StackScrollAlgorithm.canChildBeDismissed(v);
    }

    @Override // com.android.systemui.SwipeHelper.Callback
    public boolean isAntiFalsingNeeded() {
        return onKeyguard();
    }

    private boolean onKeyguard() {
        return this.mStatusBarState == 1;
    }

    private void setSwipingInProgress(boolean isSwiped) {
        this.mSwipingInProgress = isSwiped;
        if (isSwiped) {
            requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mStatusBarHeight = getResources().getDimensionPixelOffset(R.dimen.status_bar_height);
        float densityScale = getResources().getDisplayMetrics().density;
        this.mSwipeHelper.setDensityScale(densityScale);
        float pagingTouchSlop = ViewConfiguration.get(getContext()).getScaledPagingTouchSlop();
        this.mSwipeHelper.setPagingTouchSlop(pagingTouchSlop);
        initView(getContext());
    }

    public void dismissViewAnimated(View child, Runnable endRunnable, int delay, long duration) {
        this.mSwipeHelper.dismissChild(child, 0.0f, endRunnable, delay, true, duration, true);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void snapViewIfNeeded(ExpandableNotificationRow child) {
        boolean animate = this.mIsExpanded || isPinnedHeadsUp(child);
        float targetLeft = child.getProvider().isMenuVisible() ? child.getTranslation() : 0.0f;
        this.mSwipeHelper.snapChildIfNeeded(child, animate, targetLeft);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public ViewGroup getViewParentForNotification(NotificationData.Entry entry) {
        return this;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isCancelOrUp = ev.getActionMasked() == 3 || ev.getActionMasked() == 1;
        handleEmptySpaceClick(ev);
        boolean expandWantsIt = false;
        if (this.mIsExpanded && !this.mSwipingInProgress && !this.mOnlyScrollingInThisMotion) {
            if (isCancelOrUp) {
                this.mExpandHelper.onlyObserveMovements(false);
            }
            boolean wasExpandingBefore = this.mExpandingNotification;
            expandWantsIt = this.mExpandHelper.onTouchEvent(ev);
            if (this.mExpandedInThisMotion && !this.mExpandingNotification && wasExpandingBefore && !this.mDisallowScrollingInThisMotion) {
                dispatchDownEventToScroller(ev);
            }
        }
        boolean scrollerWantsIt = false;
        if (this.mIsExpanded && !this.mSwipingInProgress && !this.mExpandingNotification && !this.mDisallowScrollingInThisMotion) {
            scrollerWantsIt = onScrollTouch(ev);
        }
        boolean horizontalSwipeWantsIt = false;
        if (!this.mIsBeingDragged && !this.mExpandingNotification && !this.mExpandedInThisMotion && !this.mOnlyScrollingInThisMotion && !this.mDisallowDismissInThisMotion) {
            horizontalSwipeWantsIt = this.mSwipeHelper.onTouchEvent(ev);
        }
        NotificationGuts guts = this.mStatusBar.getGutsManager().getExposedGuts();
        if (guts != null && !isTouchInView(ev, guts) && (guts.getGutsContent() instanceof NotificationSnooze)) {
            NotificationSnooze ns = (NotificationSnooze) guts.getGutsContent();
            if ((ns.isExpanded() && isCancelOrUp) || (!horizontalSwipeWantsIt && scrollerWantsIt)) {
                checkSnoozeLeavebehind();
            }
        }
        if (ev.getActionMasked() == 1) {
            this.mCheckForLeavebehind = true;
        }
        return horizontalSwipeWantsIt || scrollerWantsIt || expandWantsIt || super.onTouchEvent(ev);
    }

    private void dispatchDownEventToScroller(MotionEvent ev) {
        MotionEvent downEvent = MotionEvent.obtain(ev);
        downEvent.setAction(0);
        onScrollTouch(downEvent);
        downEvent.recycle();
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (!isScrollingEnabled() || !this.mIsExpanded || this.mSwipingInProgress || this.mExpandingNotification || this.mDisallowScrollingInThisMotion) {
            return false;
        }
        if ((event.getSource() & 2) != 0 && event.getAction() == 8 && !this.mIsBeingDragged) {
            float vscroll = event.getAxisValue(9);
            if (vscroll != 0.0f) {
                int delta = (int) (getVerticalScrollFactor() * vscroll);
                int range = getScrollRange();
                int oldScrollY = this.mOwnScrollY;
                int newScrollY = oldScrollY - delta;
                if (newScrollY < 0) {
                    newScrollY = 0;
                } else if (newScrollY > range) {
                    newScrollY = range;
                }
                if (newScrollY != oldScrollY) {
                    setOwnScrollY(newScrollY);
                    return true;
                }
            }
        }
        return super.onGenericMotionEvent(event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean onScrollTouch(MotionEvent ev) {
        float scrollAmount;
        if (!isScrollingEnabled()) {
            return false;
        }
        if (isInsideQsContainer(ev) && !this.mIsBeingDragged) {
            return false;
        }
        this.mForcedScroll = null;
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        switch (action & 255) {
            case 0:
                if (getChildCount() == 0 || !isInContentBounds(ev)) {
                    return false;
                }
                boolean isBeingDragged = !this.mScroller.isFinished();
                setIsBeingDragged(isBeingDragged);
                if (!this.mScroller.isFinished()) {
                    this.mScroller.forceFinished(true);
                }
                this.mLastMotionY = (int) ev.getY();
                this.mDownX = (int) ev.getX();
                this.mActivePointerId = ev.getPointerId(0);
                return true;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int initialVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                    if (shouldOverScrollFling(initialVelocity)) {
                        onOverScrollFling(true, initialVelocity);
                    } else if (getChildCount() > 0) {
                        if (Math.abs(initialVelocity) > this.mMinimumVelocity) {
                            float currentOverScrollTop = getCurrentOverScrollAmount(true);
                            if (currentOverScrollTop == 0.0f || initialVelocity > 0) {
                                fling(-initialVelocity);
                            } else {
                                onOverScrollFling(false, initialVelocity);
                            }
                        } else if (this.mScroller.springBack(this.mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                            animateScroll();
                        }
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                }
                return true;
            case 2:
                int activePointerIndex = ev.findPointerIndex(this.mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                } else {
                    int y = (int) ev.getY(activePointerIndex);
                    int x = (int) ev.getX(activePointerIndex);
                    int deltaY = this.mLastMotionY - y;
                    int xDiff = Math.abs(x - this.mDownX);
                    int yDiff = Math.abs(deltaY);
                    if (!this.mIsBeingDragged && yDiff > this.mTouchSlop && yDiff > xDiff) {
                        setIsBeingDragged(true);
                        deltaY = deltaY > 0 ? deltaY - this.mTouchSlop : deltaY + this.mTouchSlop;
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y;
                        int range = getScrollRange();
                        if (this.mExpandedInThisMotion) {
                            range = Math.min(range, this.mMaxScrollAfterExpand);
                        }
                        if (deltaY < 0) {
                            scrollAmount = overScrollDown(deltaY);
                        } else {
                            scrollAmount = overScrollUp(deltaY, range);
                        }
                        if (scrollAmount != 0.0f) {
                            customOverScrollBy((int) scrollAmount, this.mOwnScrollY, range, getHeight() / 2);
                            checkSnoozeLeavebehind();
                        }
                    }
                }
                return true;
            case 3:
                if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(this.mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                        animateScroll();
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                }
                return true;
            case 4:
            default:
                return true;
            case 5:
                int index = ev.getActionIndex();
                this.mLastMotionY = (int) ev.getY(index);
                this.mDownX = (int) ev.getX(index);
                this.mActivePointerId = ev.getPointerId(index);
                return true;
            case 6:
                onSecondaryPointerUp(ev);
                this.mLastMotionY = (int) ev.getY(ev.findPointerIndex(this.mActivePointerId));
                this.mDownX = (int) ev.getX(ev.findPointerIndex(this.mActivePointerId));
                return true;
        }
    }

    protected boolean isInsideQsContainer(MotionEvent ev) {
        return this.mQsContainer != null && ev.getY() < ((float) this.mQsContainer.getBottom());
    }

    private void onOverScrollFling(boolean open, int initialVelocity) {
        if (this.mOverscrollTopChangedListener != null) {
            this.mOverscrollTopChangedListener.flingTopOverscroll(initialVelocity, open);
        }
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false);
    }

    private float overScrollUp(int deltaY, int range) {
        int deltaY2 = Math.max(deltaY, 0);
        float currentTopAmount = getCurrentOverScrollAmount(true);
        float newTopAmount = currentTopAmount - deltaY2;
        if (currentTopAmount > 0.0f) {
            setOverScrollAmount(newTopAmount, true, false);
        }
        float scrollAmount = newTopAmount < 0.0f ? -newTopAmount : 0.0f;
        float newScrollY = this.mOwnScrollY + scrollAmount;
        if (newScrollY > range) {
            if (!this.mExpandedInThisMotion) {
                float currentBottomPixels = getCurrentOverScrolledPixels(false);
                setOverScrolledPixels((currentBottomPixels + newScrollY) - range, false, false);
            }
            setOwnScrollY(range);
            return 0.0f;
        }
        return scrollAmount;
    }

    private float overScrollDown(int deltaY) {
        int deltaY2 = Math.min(deltaY, 0);
        float currentBottomAmount = getCurrentOverScrollAmount(false);
        float newBottomAmount = deltaY2 + currentBottomAmount;
        if (currentBottomAmount > 0.0f) {
            setOverScrollAmount(newBottomAmount, false, false);
        }
        float scrollAmount = newBottomAmount < 0.0f ? newBottomAmount : 0.0f;
        float newScrollY = this.mOwnScrollY + scrollAmount;
        if (newScrollY < 0.0f) {
            float currentTopPixels = getCurrentOverScrolledPixels(true);
            setOverScrolledPixels(currentTopPixels - newScrollY, true, false);
            setOwnScrollY(0);
            return 0.0f;
        }
        return scrollAmount;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = (ev.getAction() & 65280) >> 8;
        int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mLastMotionY = (int) ev.getY(newPointerIndex);
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    public void setFinishScrollingCallback(Runnable runnable) {
        this.mFinishScrollingCallback = runnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int oldY = this.mOwnScrollY;
            int y = this.mScroller.getCurrY();
            if (oldY != y) {
                int range = getScrollRange();
                if ((y < 0 && oldY >= 0) || (y > range && oldY <= range)) {
                    float currVelocity = this.mScroller.getCurrVelocity();
                    if (currVelocity >= this.mMinimumVelocity) {
                        this.mMaxOverScroll = (Math.abs(currVelocity) / 1000.0f) * this.mOverflingDistance;
                    }
                }
                if (this.mDontClampNextScroll) {
                    range = Math.max(range, oldY);
                }
                customOverScrollBy(y - oldY, oldY, range, (int) this.mMaxOverScroll);
            }
            postOnAnimation(this.mAnimateScroll);
            return;
        }
        this.mDontClampNextScroll = false;
        if (this.mFinishScrollingCallback != null) {
            this.mFinishScrollingCallback.run();
        }
    }

    private boolean customOverScrollBy(int deltaY, int scrollY, int scrollRangeY, int maxOverScrollY) {
        int newScrollY = scrollY + deltaY;
        int top = -maxOverScrollY;
        int bottom = maxOverScrollY + scrollRangeY;
        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }
        onCustomOverScrolled(newScrollY, clampedY);
        return clampedY;
    }

    public void setOverScrolledPixels(float numPixels, boolean onTop, boolean animate) {
        setOverScrollAmount(getRubberBandFactor(onTop) * numPixels, onTop, animate, true);
    }

    public void setOverScrollAmount(float amount, boolean onTop, boolean animate) {
        setOverScrollAmount(amount, onTop, animate, true);
    }

    public void setOverScrollAmount(float amount, boolean onTop, boolean animate, boolean cancelAnimators) {
        setOverScrollAmount(amount, onTop, animate, cancelAnimators, isRubberbanded(onTop));
    }

    public void setOverScrollAmount(float amount, boolean onTop, boolean animate, boolean cancelAnimators, boolean isRubberbanded) {
        if (cancelAnimators) {
            this.mStateAnimator.cancelOverScrollAnimators(onTop);
        }
        setOverScrollAmountInternal(amount, onTop, animate, isRubberbanded);
    }

    private void setOverScrollAmountInternal(float amount, boolean onTop, boolean animate, boolean isRubberbanded) {
        float amount2 = Math.max(0.0f, amount);
        if (animate) {
            this.mStateAnimator.animateOverScrollToAmount(amount2, onTop, isRubberbanded);
            return;
        }
        setOverScrolledPixels(amount2 / getRubberBandFactor(onTop), onTop);
        this.mAmbientState.setOverScrollAmount(amount2, onTop);
        if (onTop) {
            notifyOverscrollTopListener(amount2, isRubberbanded);
        }
        requestChildrenUpdate();
    }

    private void notifyOverscrollTopListener(float amount, boolean isRubberbanded) {
        this.mExpandHelper.onlyObserveMovements(amount > 1.0f);
        if (this.mDontReportNextOverScroll) {
            this.mDontReportNextOverScroll = false;
        } else if (this.mOverscrollTopChangedListener != null) {
            this.mOverscrollTopChangedListener.onOverscrollTopChanged(amount, isRubberbanded);
        }
    }

    public void setOverscrollTopChangedListener(OnOverscrollTopChangedListener overscrollTopChangedListener) {
        this.mOverscrollTopChangedListener = overscrollTopChangedListener;
    }

    public float getCurrentOverScrollAmount(boolean top) {
        return this.mAmbientState.getOverScrollAmount(top);
    }

    public float getCurrentOverScrolledPixels(boolean top) {
        return top ? this.mOverScrolledTopPixels : this.mOverScrolledBottomPixels;
    }

    private void setOverScrolledPixels(float amount, boolean onTop) {
        if (onTop) {
            this.mOverScrolledTopPixels = amount;
        } else {
            this.mOverScrolledBottomPixels = amount;
        }
    }

    private void onCustomOverScrolled(int scrollY, boolean clampedY) {
        if (!this.mScroller.isFinished()) {
            setOwnScrollY(scrollY);
            if (clampedY) {
                springBack();
                return;
            }
            float overScrollTop = getCurrentOverScrollAmount(true);
            if (this.mOwnScrollY < 0) {
                notifyOverscrollTopListener(-this.mOwnScrollY, isRubberbanded(true));
                return;
            } else {
                notifyOverscrollTopListener(overScrollTop, isRubberbanded(true));
                return;
            }
        }
        setOwnScrollY(scrollY);
    }

    private void springBack() {
        boolean onTop;
        float newAmount;
        int scrollRange = getScrollRange();
        boolean overScrolledTop = this.mOwnScrollY <= 0;
        boolean overScrolledBottom = this.mOwnScrollY >= scrollRange;
        if (overScrolledTop || overScrolledBottom) {
            if (overScrolledTop) {
                onTop = true;
                newAmount = -this.mOwnScrollY;
                setOwnScrollY(0);
                this.mDontReportNextOverScroll = true;
            } else {
                onTop = false;
                newAmount = this.mOwnScrollY - scrollRange;
                setOwnScrollY(scrollRange);
            }
            setOverScrollAmount(newAmount, onTop, false);
            setOverScrollAmount(0.0f, onTop, true);
            this.mScroller.forceFinished(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScrollRange() {
        int contentHeight = getContentHeight();
        int scrollRange = Math.max(0, contentHeight - this.mMaxLayoutHeight);
        int imeInset = getImeInset();
        return scrollRange + Math.min(imeInset, Math.max(0, getContentHeight() - (getHeight() - imeInset)));
    }

    private int getImeInset() {
        return Math.max(0, this.mBottomInset - (getRootView().getHeight() - getHeight()));
    }

    public ExpandableView getFirstChildNotGone() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8 && child != this.mShelf) {
                return (ExpandableView) child;
            }
        }
        return null;
    }

    private View getFirstChildBelowTranlsationY(float translationY, boolean ignoreChildren) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                float rowTranslation = child.getTranslationY();
                if (rowTranslation >= translationY) {
                    return child;
                }
                if (!ignoreChildren && (child instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                    if (row.isSummaryWithChildren() && row.areChildrenExpanded()) {
                        List<ExpandableNotificationRow> notificationChildren = row.getNotificationChildren();
                        for (int childIndex = 0; childIndex < notificationChildren.size(); childIndex++) {
                            ExpandableNotificationRow rowChild = notificationChildren.get(childIndex);
                            if (rowChild.getTranslationY() + rowTranslation >= translationY) {
                                return rowChild;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public View getLastChildNotGone() {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8 && child != this.mShelf) {
                return child;
            }
        }
        return null;
    }

    public int getNotGoneChildCount() {
        int childCount = getChildCount();
        int count = 0;
        for (int i = 0; i < childCount; i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child.getVisibility() != 8 && !child.willBeGone() && child != this.mShelf) {
                count++;
            }
        }
        return count;
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    private void updateContentHeight() {
        int maxDisplayedNotifications;
        float padding;
        float previousPaddingRequest = this.mPaddingBetweenElements;
        float previousPaddingAmount = 0.0f;
        int numShownItems = 0;
        boolean finish = false;
        if (!this.mAmbientState.isFullyDark()) {
            maxDisplayedNotifications = this.mMaxDisplayedNotifications;
        } else {
            maxDisplayedNotifications = hasPulsingNotifications() ? 1 : 0;
        }
        int height = 0;
        for (int height2 = 0; height2 < getChildCount(); height2++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(height2);
            boolean footerViewOnLockScreen = expandableView == this.mFooterView && onKeyguard();
            if (expandableView.getVisibility() != 8 && !expandableView.hasNoContentHeight() && !footerViewOnLockScreen) {
                boolean limitReached = maxDisplayedNotifications != -1 && numShownItems >= maxDisplayedNotifications;
                boolean notificationOnAmbientThatIsNotPulsing = this.mAmbientState.isFullyDark() && hasPulsingNotifications() && (expandableView instanceof ExpandableNotificationRow) && !isPulsing(((ExpandableNotificationRow) expandableView).getEntry());
                if (limitReached || notificationOnAmbientThatIsNotPulsing) {
                    expandableView = this.mShelf;
                    finish = true;
                }
                float increasedPaddingAmount = expandableView.getIncreasedPaddingAmount();
                if (increasedPaddingAmount < 0.0f) {
                    int ownPadding = (int) NotificationUtils.interpolate(0.0f, this.mPaddingBetweenElements, 1.0f + increasedPaddingAmount);
                    float padding2 = previousPaddingAmount > 0.0f ? (int) NotificationUtils.interpolate(ownPadding, this.mIncreasedPaddingBetweenElements, previousPaddingAmount) : ownPadding;
                    padding = padding2;
                    previousPaddingRequest = ownPadding;
                } else {
                    padding = (int) NotificationUtils.interpolate(previousPaddingRequest, this.mIncreasedPaddingBetweenElements, increasedPaddingAmount);
                    previousPaddingRequest = (int) NotificationUtils.interpolate(this.mPaddingBetweenElements, this.mIncreasedPaddingBetweenElements, increasedPaddingAmount);
                }
                if (height != 0) {
                    height = (int) (height + padding);
                }
                previousPaddingAmount = increasedPaddingAmount;
                height += expandableView.getIntrinsicHeight();
                numShownItems++;
                if (finish) {
                    break;
                }
            }
        }
        this.mIntrinsicContentHeight = height;
        this.mContentHeight = this.mTopPadding + height + this.mBottomMargin;
        updateScrollability();
        clampScrollPosition();
        this.mAmbientState.setLayoutMaxHeight(this.mContentHeight);
    }

    private boolean isPulsing(NotificationData.Entry entry) {
        return this.mAmbientState.isPulsing(entry);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public boolean hasPulsingNotifications() {
        return this.mPulsing;
    }

    private void updateScrollability() {
        boolean scrollable = !this.mQsExpanded && getScrollRange() > 0;
        if (scrollable != this.mScrollable) {
            this.mScrollable = scrollable;
            setFocusable(scrollable);
            updateForwardAndBackwardScrollability();
        }
    }

    private void updateForwardAndBackwardScrollability() {
        boolean forwardScrollable = this.mScrollable && this.mOwnScrollY < getScrollRange();
        boolean backwardsScrollable = this.mScrollable && this.mOwnScrollY > 0;
        boolean changed = (forwardScrollable == this.mForwardScrollable && backwardsScrollable == this.mBackwardScrollable) ? false : true;
        this.mForwardScrollable = forwardScrollable;
        this.mBackwardScrollable = backwardsScrollable;
        if (changed) {
            sendAccessibilityEvent(2048);
        }
    }

    private void updateBackground() {
        if (!this.mShouldDrawNotificationBackground || this.mAmbientState.isFullyDark()) {
            return;
        }
        updateBackgroundBounds();
        if (!this.mCurrentBounds.equals(this.mBackgroundBounds)) {
            boolean animate = this.mAnimateNextBackgroundTop || this.mAnimateNextBackgroundBottom || areBoundsAnimating();
            if (!isExpanded()) {
                abortBackgroundAnimators();
                animate = false;
            }
            if (animate) {
                startBackgroundAnimation();
            } else {
                this.mCurrentBounds.set(this.mBackgroundBounds);
                applyCurrentBackgroundBounds();
            }
        } else {
            abortBackgroundAnimators();
        }
        this.mAnimateNextBackgroundBottom = false;
        this.mAnimateNextBackgroundTop = false;
    }

    private void abortBackgroundAnimators() {
        if (this.mBottomAnimator != null) {
            this.mBottomAnimator.cancel();
        }
        if (this.mTopAnimator != null) {
            this.mTopAnimator.cancel();
        }
    }

    private boolean areBoundsAnimating() {
        return (this.mBottomAnimator == null && this.mTopAnimator == null) ? false : true;
    }

    private void startBackgroundAnimation() {
        this.mCurrentBounds.left = this.mBackgroundBounds.left;
        this.mCurrentBounds.right = this.mBackgroundBounds.right;
        startBottomAnimation();
        startTopAnimation();
    }

    private void startTopAnimation() {
        int previousEndValue = this.mEndAnimationRect.top;
        int newEndValue = this.mBackgroundBounds.top;
        ObjectAnimator previousAnimator = this.mTopAnimator;
        if (previousAnimator != null && previousEndValue == newEndValue) {
            return;
        }
        if (!this.mAnimateNextBackgroundTop) {
            if (previousAnimator != null) {
                int previousStartValue = this.mStartAnimationRect.top;
                PropertyValuesHolder[] values = previousAnimator.getValues();
                values[0].setIntValues(previousStartValue, newEndValue);
                this.mStartAnimationRect.top = previousStartValue;
                this.mEndAnimationRect.top = newEndValue;
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            setBackgroundTop(newEndValue);
            return;
        }
        if (previousAnimator != null) {
            previousAnimator.cancel();
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "backgroundTop", this.mCurrentBounds.top, newEndValue);
        Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
        animator.setInterpolator(interpolator);
        animator.setDuration(360L);
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationStackScrollLayout.this.mStartAnimationRect.top = -1;
                NotificationStackScrollLayout.this.mEndAnimationRect.top = -1;
                NotificationStackScrollLayout.this.mTopAnimator = null;
            }
        });
        animator.start();
        this.mStartAnimationRect.top = this.mCurrentBounds.top;
        this.mEndAnimationRect.top = newEndValue;
        this.mTopAnimator = animator;
    }

    private void startBottomAnimation() {
        int previousStartValue = this.mStartAnimationRect.bottom;
        int previousEndValue = this.mEndAnimationRect.bottom;
        int newEndValue = this.mBackgroundBounds.bottom;
        ObjectAnimator previousAnimator = this.mBottomAnimator;
        if (previousAnimator != null && previousEndValue == newEndValue) {
            return;
        }
        if (!this.mAnimateNextBackgroundBottom) {
            if (previousAnimator != null) {
                PropertyValuesHolder[] values = previousAnimator.getValues();
                values[0].setIntValues(previousStartValue, newEndValue);
                this.mStartAnimationRect.bottom = previousStartValue;
                this.mEndAnimationRect.bottom = newEndValue;
                previousAnimator.setCurrentPlayTime(previousAnimator.getCurrentPlayTime());
                return;
            }
            setBackgroundBottom(newEndValue);
            return;
        }
        if (previousAnimator != null) {
            previousAnimator.cancel();
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "backgroundBottom", this.mCurrentBounds.bottom, newEndValue);
        Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
        animator.setInterpolator(interpolator);
        animator.setDuration(360L);
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationStackScrollLayout.this.mStartAnimationRect.bottom = -1;
                NotificationStackScrollLayout.this.mEndAnimationRect.bottom = -1;
                NotificationStackScrollLayout.this.mBottomAnimator = null;
            }
        });
        animator.start();
        this.mStartAnimationRect.bottom = this.mCurrentBounds.bottom;
        this.mEndAnimationRect.bottom = newEndValue;
        this.mBottomAnimator = animator;
    }

    private void setBackgroundTop(int top) {
        this.mCurrentBounds.top = top;
        applyCurrentBackgroundBounds();
    }

    public void setBackgroundBottom(int bottom) {
        this.mCurrentBounds.bottom = bottom;
        applyCurrentBackgroundBounds();
    }

    private void applyCurrentBackgroundBounds() {
        Rect rect;
        if (!this.mShouldDrawNotificationBackground) {
            return;
        }
        boolean awake = this.mDarkAmount != 0.0f || this.mAmbientState.isDark();
        ScrimController scrimController = this.mScrimController;
        if (this.mFadingOut || this.mParentNotFullyVisible || awake || this.mIsClipped) {
            rect = null;
        } else {
            rect = this.mCurrentBounds;
        }
        scrimController.setExcludedBackgroundArea(rect);
        invalidate();
    }

    private void updateBackgroundBounds() {
        ActivatableNotificationView lastView;
        int top;
        int bottom;
        int top2;
        int finalTranslationY;
        getLocationInWindow(this.mTempInt2);
        this.mBackgroundBounds.left = this.mTempInt2[0] + this.mSidePaddings;
        this.mBackgroundBounds.right = (this.mTempInt2[0] + getWidth()) - this.mSidePaddings;
        if (!this.mIsExpanded) {
            this.mBackgroundBounds.top = 0;
            this.mBackgroundBounds.bottom = 0;
            return;
        }
        ActivatableNotificationView firstView = this.mFirstVisibleBackgroundChild;
        int top3 = 0;
        if (firstView != null) {
            int finalTranslationY2 = (int) Math.ceil(ViewState.getFinalTranslationY(firstView));
            if (this.mAnimateNextBackgroundTop || ((this.mTopAnimator == null && this.mCurrentBounds.top == finalTranslationY2) || (this.mTopAnimator != null && this.mEndAnimationRect.top == finalTranslationY2))) {
                top3 = finalTranslationY2;
            } else {
                top3 = (int) Math.ceil(firstView.getTranslationY());
            }
        }
        if (this.mShelf.hasItemsInStableShelf() && this.mShelf.getVisibility() != 8) {
            lastView = this.mShelf;
        } else {
            lastView = this.mLastVisibleBackgroundChild;
        }
        if (lastView != null) {
            if (lastView == this.mShelf) {
                finalTranslationY = (int) this.mShelf.getTranslationY();
            } else {
                finalTranslationY = (int) ViewState.getFinalTranslationY(lastView);
            }
            int finalHeight = ExpandableViewState.getFinalActualHeight(lastView);
            int finalBottom = (finalTranslationY + finalHeight) - lastView.getClipBottomAmount();
            int finalTranslationY3 = (this.mAnimateNextBackgroundBottom || (this.mBottomAnimator == null && this.mCurrentBounds.bottom == finalBottom) || (this.mBottomAnimator != null && this.mEndAnimationRect.bottom == finalBottom)) ? finalBottom : (int) ((lastView.getTranslationY() + lastView.getActualHeight()) - lastView.getClipBottomAmount());
            int i = finalTranslationY3;
            bottom = top3;
            top = i;
        } else {
            top = this.mTopPadding;
            bottom = top;
        }
        if (this.mStatusBarState != 1) {
            top2 = (int) Math.max(this.mTopPadding + this.mStackTranslation, bottom);
        } else {
            top2 = Math.max(0, bottom);
        }
        this.mBackgroundBounds.top = top2;
        this.mBackgroundBounds.bottom = Math.max(top, top2);
    }

    private ActivatableNotificationView getLastChildWithBackground() {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8 && (child instanceof ActivatableNotificationView) && child != this.mShelf) {
                return (ActivatableNotificationView) child;
            }
        }
        return null;
    }

    private ActivatableNotificationView getFirstChildWithBackground() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8 && (child instanceof ActivatableNotificationView) && child != this.mShelf) {
                return (ActivatableNotificationView) child;
            }
        }
        return null;
    }

    protected void fling(int velocityY) {
        if (getChildCount() > 0) {
            int scrollRange = getScrollRange();
            float topAmount = getCurrentOverScrollAmount(true);
            float bottomAmount = getCurrentOverScrollAmount(false);
            if (velocityY < 0 && topAmount > 0.0f) {
                setOwnScrollY(this.mOwnScrollY - ((int) topAmount));
                this.mDontReportNextOverScroll = true;
                setOverScrollAmount(0.0f, true, false);
                this.mMaxOverScroll = ((Math.abs(velocityY) / 1000.0f) * getRubberBandFactor(true) * this.mOverflingDistance) + topAmount;
            } else if (velocityY > 0 && bottomAmount > 0.0f) {
                setOwnScrollY((int) (this.mOwnScrollY + bottomAmount));
                setOverScrollAmount(0.0f, false, false);
                this.mMaxOverScroll = ((Math.abs(velocityY) / 1000.0f) * getRubberBandFactor(false) * this.mOverflingDistance) + bottomAmount;
            } else {
                this.mMaxOverScroll = 0.0f;
            }
            int minScrollY = Math.max(0, scrollRange);
            if (this.mExpandedInThisMotion) {
                minScrollY = Math.min(minScrollY, this.mMaxScrollAfterExpand);
            }
            this.mScroller.fling(this.mScrollX, this.mOwnScrollY, 1, velocityY, 0, 0, 0, minScrollY, 0, (!this.mExpandedInThisMotion || this.mOwnScrollY < 0) ? 1073741823 : 0);
            animateScroll();
        }
    }

    private boolean shouldOverScrollFling(int initialVelocity) {
        float topOverScroll = getCurrentOverScrollAmount(true);
        return this.mScrolledToTopOnFirstDown && !this.mExpandedInThisMotion && topOverScroll > this.mMinTopOverScrollToEscape && initialVelocity > 0;
    }

    public void updateTopPadding(float qsHeight, boolean animate, boolean ignoreIntrinsicPadding) {
        int topPadding = (int) qsHeight;
        int minStackHeight = getLayoutMinHeight();
        if (topPadding + minStackHeight > getHeight()) {
            this.mTopPaddingOverflow = (topPadding + minStackHeight) - getHeight();
        } else {
            this.mTopPaddingOverflow = 0.0f;
        }
        setTopPadding(ignoreIntrinsicPadding ? topPadding : clampPadding(topPadding), animate);
        setExpandedHeight(this.mExpandedHeight);
    }

    public void setMaxTopPadding(int maxTopPadding) {
        this.mMaxTopPadding = maxTopPadding;
    }

    public int getLayoutMinHeight() {
        if (isHeadsUpTransition()) {
            return getTopHeadsUpPinnedHeight();
        }
        if (this.mShelf.getVisibility() == 8) {
            return 0;
        }
        return this.mShelf.getIntrinsicHeight();
    }

    public float getTopPaddingOverflow() {
        return this.mTopPaddingOverflow;
    }

    public int getPeekHeight() {
        ExpandableView firstChild = getFirstChildNotGone();
        int firstChildMinHeight = firstChild != null ? firstChild.getCollapsedHeight() : this.mCollapsedSize;
        int shelfHeight = 0;
        if (this.mLastVisibleBackgroundChild != null && this.mShelf.getVisibility() != 8) {
            shelfHeight = this.mShelf.getIntrinsicHeight();
        }
        return this.mIntrinsicPadding + firstChildMinHeight + shelfHeight;
    }

    private int clampPadding(int desiredPadding) {
        return Math.max(desiredPadding, this.mIntrinsicPadding);
    }

    private float getRubberBandFactor(boolean onTop) {
        if (!onTop) {
            return 0.35f;
        }
        if (this.mExpandedInThisMotion) {
            return 0.15f;
        }
        if (this.mIsExpansionChanging || this.mPanelTracking) {
            return 0.21f;
        }
        if (!this.mScrolledToTopOnFirstDown) {
            return 0.35f;
        }
        return 1.0f;
    }

    private boolean isRubberbanded(boolean onTop) {
        return !onTop || this.mExpandedInThisMotion || this.mIsExpansionChanging || this.mPanelTracking || !this.mScrolledToTopOnFirstDown;
    }

    private void endDrag() {
        setIsBeingDragged(false);
        recycleVelocityTracker();
        if (getCurrentOverScrollAmount(true) > 0.0f) {
            setOverScrollAmount(0.0f, true, true);
        }
        if (getCurrentOverScrollAmount(false) > 0.0f) {
            setOverScrollAmount(0.0f, false, true);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        initDownStates(ev);
        handleEmptySpaceClick(ev);
        boolean expandWantsIt = false;
        if (!this.mSwipingInProgress && !this.mOnlyScrollingInThisMotion) {
            expandWantsIt = this.mExpandHelper.onInterceptTouchEvent(ev);
        }
        boolean scrollWantsIt = false;
        if (!this.mSwipingInProgress && !this.mExpandingNotification) {
            scrollWantsIt = onInterceptTouchEventScroll(ev);
        }
        boolean swipeWantsIt = false;
        if (!this.mIsBeingDragged && !this.mExpandingNotification && !this.mExpandedInThisMotion && !this.mOnlyScrollingInThisMotion && !this.mDisallowDismissInThisMotion) {
            swipeWantsIt = this.mSwipeHelper.onInterceptTouchEvent(ev);
        }
        boolean isUp = ev.getActionMasked() == 1;
        NotificationGuts guts = this.mStatusBar.getGutsManager().getExposedGuts();
        if (!isTouchInView(ev, guts) && isUp && !swipeWantsIt && !expandWantsIt && !scrollWantsIt) {
            this.mCheckForLeavebehind = false;
            this.mStatusBar.getGutsManager().closeAndSaveGuts(true, false, false, -1, -1, false);
        }
        if (ev.getActionMasked() == 1) {
            this.mCheckForLeavebehind = true;
        }
        return swipeWantsIt || scrollWantsIt || expandWantsIt || super.onInterceptTouchEvent(ev);
    }

    private void handleEmptySpaceClick(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case 1:
                if (this.mStatusBarState != 1 && this.mTouchIsClick && isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY)) {
                    this.mOnEmptySpaceClickListener.onEmptySpaceClicked(this.mInitialTouchX, this.mInitialTouchY);
                    break;
                }
                break;
            case 2:
                if (this.mTouchIsClick) {
                    if (Math.abs(ev.getY() - this.mInitialTouchY) > this.mTouchSlop || Math.abs(ev.getX() - this.mInitialTouchX) > this.mTouchSlop) {
                        this.mTouchIsClick = false;
                    }
                }
                break;
        }
    }

    private void initDownStates(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.mExpandedInThisMotion = false;
            this.mOnlyScrollingInThisMotion = !this.mScroller.isFinished();
            this.mDisallowScrollingInThisMotion = false;
            this.mDisallowDismissInThisMotion = false;
            this.mTouchIsClick = true;
            this.mInitialTouchX = ev.getX();
            this.mInitialTouchY = ev.getY();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void setChildTransferInProgress(boolean childTransferInProgress) {
        this.mChildTransferInProgress = childTransferInProgress;
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (!this.mChildTransferInProgress) {
            onViewRemovedInternal(child, this);
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void cleanUpViewState(View child) {
        if (child == this.mTranslatingParentView) {
            this.mTranslatingParentView = null;
        }
        this.mCurrentStackScrollState.removeViewStateForView(child);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        if (disallowIntercept) {
            cancelLongPress();
        }
    }

    private void onViewRemovedInternal(View child, ViewGroup container) {
        if (this.mChangePositionInProgress) {
            return;
        }
        ExpandableView expandableView = (ExpandableView) child;
        expandableView.setOnHeightChangedListener(null);
        this.mCurrentStackScrollState.removeViewStateForView(child);
        updateScrollStateForRemovedChild(expandableView);
        boolean animationGenerated = generateRemoveAnimation(child);
        if (animationGenerated) {
            if (!this.mSwipedOutViews.contains(child) || Math.abs(expandableView.getTranslation()) != expandableView.getWidth()) {
                container.addTransientView(child, 0);
                expandableView.setTransientContainer(container);
            }
        } else {
            this.mSwipedOutViews.remove(child);
        }
        updateAnimationState(false, child);
        focusNextViewIfFocused(child);
    }

    private void focusNextViewIfFocused(View view) {
        float translationY;
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) view;
            if (row.shouldRefocusOnDismiss()) {
                View nextView = row.getChildAfterViewWhenDismissed();
                if (nextView == null) {
                    View groupParentWhenDismissed = row.getGroupParentWhenDismissed();
                    if (groupParentWhenDismissed != null) {
                        translationY = groupParentWhenDismissed.getTranslationY();
                    } else {
                        translationY = view.getTranslationY();
                    }
                    nextView = getFirstChildBelowTranlsationY(translationY, true);
                }
                if (nextView != null) {
                    nextView.requestAccessibilityFocus();
                }
            }
        }
    }

    private boolean isChildInGroup(View child) {
        return (child instanceof ExpandableNotificationRow) && this.mGroupManager.isChildInGroupWithSummary(((ExpandableNotificationRow) child).getStatusBarNotification());
    }

    private boolean generateRemoveAnimation(View child) {
        if (removeRemovedChildFromHeadsUpChangeAnimations(child)) {
            this.mAddedHeadsUpChildren.remove(child);
            return false;
        }
        if (isClickedHeadsUp(child)) {
            this.mClearTransientViewsWhenFinished.add((ExpandableView) child);
            return true;
        }
        if (!this.mIsExpanded || !this.mAnimationsEnabled || isChildInInvisibleGroup(child)) {
            return false;
        }
        if (!this.mChildrenToAddAnimated.contains(child)) {
            this.mChildrenToRemoveAnimated.add(child);
            this.mNeedsAnimation = true;
            return true;
        }
        this.mChildrenToAddAnimated.remove(child);
        this.mFromMoreCardAdditions.remove(child);
        return false;
    }

    private boolean isClickedHeadsUp(View child) {
        return HeadsUpUtil.isClickedHeadsUpNotification(child);
    }

    private boolean removeRemovedChildFromHeadsUpChangeAnimations(View child) {
        boolean hasAddEvent = false;
        for (Pair<ExpandableNotificationRow, Boolean> eventPair : this.mHeadsUpChangeAnimations) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) eventPair.first;
            boolean isHeadsUp = ((Boolean) eventPair.second).booleanValue();
            if (child == row) {
                this.mTmpList.add(eventPair);
                hasAddEvent |= isHeadsUp;
            }
        }
        if (hasAddEvent) {
            this.mHeadsUpChangeAnimations.removeAll(this.mTmpList);
            ((ExpandableNotificationRow) child).setHeadsUpAnimatingAway(false);
        }
        this.mTmpList.clear();
        return hasAddEvent;
    }

    private boolean isChildInInvisibleGroup(View child) {
        if (child instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) child;
            ExpandableNotificationRow groupSummary = this.mGroupManager.getGroupSummary(row.getStatusBarNotification());
            return (groupSummary == null || groupSummary == row || row.getVisibility() != 4) ? false : true;
        }
        return false;
    }

    private void updateScrollStateForRemovedChild(ExpandableView removedChild) {
        int padding;
        int startingPosition = getPositionInLinearLayout(removedChild);
        float increasedPaddingAmount = removedChild.getIncreasedPaddingAmount();
        if (increasedPaddingAmount < 0.0f) {
            padding = (int) NotificationUtils.interpolate(0.0f, this.mPaddingBetweenElements, 1.0f + increasedPaddingAmount);
        } else {
            padding = (int) NotificationUtils.interpolate(this.mPaddingBetweenElements, this.mIncreasedPaddingBetweenElements, increasedPaddingAmount);
        }
        int childHeight = getIntrinsicHeight(removedChild) + padding;
        int endPosition = startingPosition + childHeight;
        if (endPosition <= this.mOwnScrollY) {
            setOwnScrollY(this.mOwnScrollY - childHeight);
        } else if (startingPosition < this.mOwnScrollY) {
            setOwnScrollY(startingPosition);
        }
    }

    private int getIntrinsicHeight(View view) {
        if (view instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) view;
            return expandableView.getIntrinsicHeight();
        }
        return view.getHeight();
    }

    public int getPositionInLinearLayout(View requestedView) {
        float padding;
        ExpandableNotificationRow childInGroup = null;
        ExpandableNotificationRow requestedRow = null;
        if (isChildInGroup(requestedView)) {
            childInGroup = (ExpandableNotificationRow) requestedView;
            ExpandableNotificationRow notificationParent = childInGroup.getNotificationParent();
            requestedRow = notificationParent;
            requestedView = notificationParent;
        }
        float previousPaddingRequest = this.mPaddingBetweenElements;
        float previousPaddingAmount = 0.0f;
        float previousPaddingRequest2 = previousPaddingRequest;
        int position = 0;
        for (int position2 = 0; position2 < getChildCount(); position2++) {
            ExpandableView child = (ExpandableView) getChildAt(position2);
            boolean notGone = child.getVisibility() != 8;
            if (notGone && !child.hasNoContentHeight()) {
                float increasedPaddingAmount = child.getIncreasedPaddingAmount();
                if (increasedPaddingAmount < 0.0f) {
                    int ownPadding = (int) NotificationUtils.interpolate(0.0f, this.mPaddingBetweenElements, 1.0f + increasedPaddingAmount);
                    padding = previousPaddingAmount > 0.0f ? (int) NotificationUtils.interpolate(ownPadding, this.mIncreasedPaddingBetweenElements, previousPaddingAmount) : ownPadding;
                    previousPaddingRequest2 = ownPadding;
                } else {
                    padding = (int) NotificationUtils.interpolate(previousPaddingRequest2, this.mIncreasedPaddingBetweenElements, increasedPaddingAmount);
                    previousPaddingRequest2 = (int) NotificationUtils.interpolate(this.mPaddingBetweenElements, this.mIncreasedPaddingBetweenElements, increasedPaddingAmount);
                }
                if (position != 0) {
                    position = (int) (position + padding);
                }
                previousPaddingAmount = increasedPaddingAmount;
            }
            if (child == requestedView) {
                if (requestedRow != null) {
                    return position + requestedRow.getPositionOfChild(childInGroup);
                }
                return position;
            }
            if (notGone) {
                position += getIntrinsicHeight(child);
            }
        }
        return 0;
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        onViewAddedInternal(child);
    }

    private void updateFirstAndLastBackgroundViews() {
        ActivatableNotificationView firstChild = getFirstChildWithBackground();
        ActivatableNotificationView lastChild = getLastChildWithBackground();
        if (this.mAnimationsEnabled && this.mIsExpanded) {
            this.mAnimateNextBackgroundTop = firstChild != this.mFirstVisibleBackgroundChild;
            this.mAnimateNextBackgroundBottom = lastChild != this.mLastVisibleBackgroundChild;
        } else {
            this.mAnimateNextBackgroundTop = false;
            this.mAnimateNextBackgroundBottom = false;
        }
        this.mFirstVisibleBackgroundChild = firstChild;
        this.mLastVisibleBackgroundChild = lastChild;
        this.mAmbientState.setLastVisibleBackgroundChild(lastChild);
        this.mRoundnessManager.setFirstAndLastBackgroundChild(this.mFirstVisibleBackgroundChild, this.mLastVisibleBackgroundChild);
        invalidate();
    }

    private void onViewAddedInternal(View child) {
        updateHideSensitiveForChild(child);
        ((ExpandableView) child).setOnHeightChangedListener(this);
        generateAddAnimation(child, false);
        updateAnimationState(child);
        updateChronometerForChild(child);
    }

    private void updateHideSensitiveForChild(View child) {
        if (child instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) child;
            expandableView.setHideSensitiveForIntrinsicHeight(this.mAmbientState.isHideSensitive());
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void notifyGroupChildRemoved(View row, ViewGroup childrenContainer) {
        onViewRemovedInternal(row, childrenContainer);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void notifyGroupChildAdded(View row) {
        onViewAddedInternal(row);
    }

    public void setAnimationsEnabled(boolean animationsEnabled) {
        this.mAnimationsEnabled = animationsEnabled;
        updateNotificationAnimationStates();
        if (!animationsEnabled) {
            this.mSwipedOutViews.clear();
            this.mChildrenToRemoveAnimated.clear();
            clearTemporaryViewsInGroup(this);
        }
    }

    private void updateNotificationAnimationStates() {
        boolean running = this.mAnimationsEnabled || hasPulsingNotifications();
        this.mShelf.setAnimationsEnabled(running);
        int childCount = getChildCount();
        boolean running2 = running;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            running2 &= this.mIsExpanded || isPinnedHeadsUp(child);
            updateAnimationState(running2, child);
        }
    }

    private void updateAnimationState(View child) {
        updateAnimationState((this.mAnimationsEnabled || hasPulsingNotifications()) && (this.mIsExpanded || isPinnedHeadsUp(child)), child);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void setExpandingNotification(ExpandableNotificationRow row) {
        this.mAmbientState.setExpandingNotification(row);
        requestChildrenUpdate();
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void bindRow(final ExpandableNotificationRow row) {
        row.setHeadsUpAnimatingAwayListener(new Consumer() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$NotificationStackScrollLayout$kf3mstlQ__SBMLTTAvwCr700C4g
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationStackScrollLayout.lambda$bindRow$1(this.f$0, row, (Boolean) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$bindRow$1(NotificationStackScrollLayout notificationStackScrollLayout, ExpandableNotificationRow row, Boolean animatingAway) {
        notificationStackScrollLayout.mRoundnessManager.onHeadsupAnimatingAwayChanged(row, animatingAway.booleanValue());
        if (notificationStackScrollLayout.mHeadsUpAppearanceController != null) {
            notificationStackScrollLayout.mHeadsUpAppearanceController.updateHeader(row.getEntry());
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void applyExpandAnimationParams(ActivityLaunchAnimator.ExpandAnimationParameters params) {
        this.mAmbientState.setExpandAnimationTopChange(params == null ? 0 : params.getTopChange());
        requestChildrenUpdate();
    }

    private void updateAnimationState(boolean running, View child) {
        if (child instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) child;
            row.setIconAnimationRunning(running);
        }
    }

    public boolean isAddOrRemoveAnimationPending() {
        return this.mNeedsAnimation && !(this.mChildrenToAddAnimated.isEmpty() && this.mChildrenToRemoveAnimated.isEmpty());
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void generateAddAnimation(View child, boolean fromMoreCard) {
        if (this.mIsExpanded && this.mAnimationsEnabled && !this.mChangePositionInProgress) {
            this.mChildrenToAddAnimated.add(child);
            if (fromMoreCard) {
                this.mFromMoreCardAdditions.add(child);
            }
            this.mNeedsAnimation = true;
        }
        if (isHeadsUp(child) && this.mAnimationsEnabled && !this.mChangePositionInProgress) {
            this.mAddedHeadsUpChildren.add(child);
            this.mChildrenToAddAnimated.remove(child);
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void changeViewPosition(View child, int newIndex) {
        int currentIndex = indexOfChild(child);
        if (currentIndex == -1) {
            boolean isTransient = false;
            if ((child instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) child).getTransientContainer() != null) {
                isTransient = true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Attempting to re-position ");
            sb.append(isTransient ? "transient" : "");
            sb.append(" view {");
            sb.append(child);
            sb.append("}");
            Log.e("StackScroller", sb.toString());
            return;
        }
        if (child != null && child.getParent() == this && currentIndex != newIndex) {
            this.mChangePositionInProgress = true;
            ((ExpandableView) child).setChangingPosition(true);
            removeView(child);
            addView(child, newIndex);
            ((ExpandableView) child).setChangingPosition(false);
            this.mChangePositionInProgress = false;
            if (this.mIsExpanded && this.mAnimationsEnabled && child.getVisibility() != 8) {
                this.mChildrenChangingPositions.add(child);
                this.mNeedsAnimation = true;
            }
        }
    }

    private void startAnimationToState() {
        if (this.mNeedsAnimation) {
            generateChildHierarchyEvents();
            this.mNeedsAnimation = false;
        }
        if (!this.mAnimationEvents.isEmpty() || isCurrentlyAnimating()) {
            setAnimationRunning(true);
            this.mStateAnimator.startAnimationForEvents(this.mAnimationEvents, this.mCurrentStackScrollState, this.mGoToFullShadeDelay);
            this.mAnimationEvents.clear();
            updateBackground();
            updateViewShadows();
            updateClippingToTopRoundedCorner();
        } else {
            applyCurrentState();
        }
        this.mGoToFullShadeDelay = 0L;
    }

    private void generateChildHierarchyEvents() {
        generateHeadsUpAnimationEvents();
        generateChildRemovalEvents();
        generateChildAdditionEvents();
        generatePositionChangeEvents();
        generateSnapBackEvents();
        generateDragEvents();
        generateTopPaddingEvent();
        generateActivateEvent();
        generateDimmedEvent();
        generateHideSensitiveEvent();
        generateDarkEvent();
        generateGoToFullShadeEvent();
        generateViewResizeEvent();
        generateGroupExpansionEvent();
        generateAnimateEverythingEvent();
        generatePulsingAnimationEvent();
        this.mNeedsAnimation = false;
    }

    private void generateHeadsUpAnimationEvents() {
        int i;
        for (Pair<ExpandableNotificationRow, Boolean> eventPair : this.mHeadsUpChangeAnimations) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) eventPair.first;
            boolean isHeadsUp = ((Boolean) eventPair.second).booleanValue();
            int type = 17;
            boolean onBottom = false;
            boolean pinnedAndClosed = row.isPinned() && !this.mIsExpanded;
            if (!this.mIsExpanded && !isHeadsUp) {
                if (row.wasJustClicked()) {
                    i = 16;
                } else {
                    i = 15;
                }
                type = i;
                if (row.isChildInGroup()) {
                    row.setHeadsUpAnimatingAway(false);
                } else {
                    AnimationEvent event = new AnimationEvent(row, type);
                    event.headsUpFromBottom = onBottom;
                    this.mAnimationEvents.add(event);
                }
            } else {
                ExpandableViewState viewState = this.mCurrentStackScrollState.getViewStateForView(row);
                if (viewState != null) {
                    if (isHeadsUp && (this.mAddedHeadsUpChildren.contains(row) || pinnedAndClosed)) {
                        if (pinnedAndClosed || shouldHunAppearFromBottom(viewState)) {
                            type = 14;
                        } else {
                            type = 0;
                        }
                        onBottom = pinnedAndClosed ? false : true;
                    }
                    AnimationEvent event2 = new AnimationEvent(row, type);
                    event2.headsUpFromBottom = onBottom;
                    this.mAnimationEvents.add(event2);
                }
            }
        }
        this.mHeadsUpChangeAnimations.clear();
        this.mAddedHeadsUpChildren.clear();
    }

    private boolean shouldHunAppearFromBottom(ExpandableViewState viewState) {
        if (viewState.yTranslation + viewState.height < this.mAmbientState.getMaxHeadsUpTranslation()) {
            return false;
        }
        return true;
    }

    private void generateGroupExpansionEvent() {
        if (this.mExpandedGroupView != null) {
            this.mAnimationEvents.add(new AnimationEvent(this.mExpandedGroupView, 13));
            this.mExpandedGroupView = null;
        }
    }

    private void generateViewResizeEvent() {
        if (this.mNeedViewResizeAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 12));
        }
        this.mNeedViewResizeAnimation = false;
    }

    private void generateSnapBackEvents() {
        for (View child : this.mSnappedBackChildren) {
            this.mAnimationEvents.add(new AnimationEvent(child, 5));
        }
        this.mSnappedBackChildren.clear();
    }

    private void generateDragEvents() {
        for (View child : this.mDragAnimPendingChildren) {
            this.mAnimationEvents.add(new AnimationEvent(child, 4));
        }
        this.mDragAnimPendingChildren.clear();
    }

    private void generateChildRemovalEvents() {
        ViewGroup transientContainer;
        for (View child : this.mChildrenToRemoveAnimated) {
            boolean childWasSwipedOut = this.mSwipedOutViews.contains(child);
            float removedTranslation = child.getTranslationY();
            boolean ignoreChildren = true;
            boolean z = false;
            int i = 1;
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                if (row.isRemoved() && row.wasChildInGroupWhenRemoved()) {
                    removedTranslation = row.getTranslationWhenRemoved();
                    ignoreChildren = false;
                }
                childWasSwipedOut |= Math.abs(row.getTranslation()) == ((float) row.getWidth());
            }
            if (!childWasSwipedOut) {
                Rect clipBounds = child.getClipBounds();
                if (clipBounds != null && clipBounds.height() == 0) {
                    z = true;
                }
                childWasSwipedOut = z;
                if (childWasSwipedOut && (child instanceof ExpandableView) && (transientContainer = ((ExpandableView) child).getTransientContainer()) != null) {
                    transientContainer.removeTransientView(child);
                }
            }
            if (childWasSwipedOut) {
                i = 2;
            }
            int animationType = i;
            AnimationEvent event = new AnimationEvent(child, animationType);
            event.viewAfterChangingView = getFirstChildBelowTranlsationY(removedTranslation, ignoreChildren);
            this.mAnimationEvents.add(event);
            this.mSwipedOutViews.remove(child);
        }
        this.mChildrenToRemoveAnimated.clear();
    }

    private void generatePositionChangeEvents() {
        for (View child : this.mChildrenChangingPositions) {
            this.mAnimationEvents.add(new AnimationEvent(child, 8));
        }
        this.mChildrenChangingPositions.clear();
        if (this.mGenerateChildOrderChangedEvent) {
            this.mAnimationEvents.add(new AnimationEvent(null, 8));
            this.mGenerateChildOrderChangedEvent = false;
        }
    }

    private void generateChildAdditionEvents() {
        for (View child : this.mChildrenToAddAnimated) {
            if (this.mFromMoreCardAdditions.contains(child)) {
                this.mAnimationEvents.add(new AnimationEvent(child, 0, 360L));
            } else {
                this.mAnimationEvents.add(new AnimationEvent(child, 0));
            }
        }
        this.mChildrenToAddAnimated.clear();
        this.mFromMoreCardAdditions.clear();
    }

    private void generateTopPaddingEvent() {
        AnimationEvent event;
        if (this.mTopPaddingNeedsAnimation) {
            if (this.mAmbientState.isDark()) {
                event = new AnimationEvent((View) null, 3, 550L);
            } else {
                event = new AnimationEvent(null, 3);
            }
            this.mAnimationEvents.add(event);
        }
        this.mTopPaddingNeedsAnimation = false;
    }

    private void generateActivateEvent() {
        if (this.mActivateNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 6));
        }
        this.mActivateNeedsAnimation = false;
    }

    private void generateAnimateEverythingEvent() {
        if (this.mEverythingNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 18));
        }
        this.mEverythingNeedsAnimation = false;
    }

    private void generateDimmedEvent() {
        if (this.mDimmedNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 7));
        }
        this.mDimmedNeedsAnimation = false;
    }

    private void generateHideSensitiveEvent() {
        if (this.mHideSensitiveNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 11));
        }
        this.mHideSensitiveNeedsAnimation = false;
    }

    private void generateDarkEvent() {
        if (this.mDarkNeedsAnimation) {
            AnimationEvent ev = new AnimationEvent((View) null, 9, new AnimationFilter().animateDark().animateY(this.mShelf));
            ev.darkAnimationOriginIndex = this.mDarkAnimationOriginIndex;
            this.mAnimationEvents.add(ev);
            startDarkAmountAnimation();
        }
        this.mDarkNeedsAnimation = false;
    }

    private void generateGoToFullShadeEvent() {
        if (this.mGoToFullShadeNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 10));
        }
        this.mGoToFullShadeNeedsAnimation = false;
    }

    private boolean onInterceptTouchEventScroll(MotionEvent ev) {
        if (!isScrollingEnabled()) {
            return false;
        }
        int action = ev.getAction();
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i = action & 255;
        if (i != 6) {
            switch (i) {
                case 0:
                    int y = (int) ev.getY();
                    this.mScrolledToTopOnFirstDown = isScrolledToTop();
                    if (getChildAtPosition(ev.getX(), y, false) == null) {
                        setIsBeingDragged(false);
                        recycleVelocityTracker();
                    } else {
                        this.mLastMotionY = y;
                        this.mDownX = (int) ev.getX();
                        this.mActivePointerId = ev.getPointerId(0);
                        initOrResetVelocityTracker();
                        this.mVelocityTracker.addMovement(ev);
                        boolean isBeingDragged = !this.mScroller.isFinished();
                        setIsBeingDragged(isBeingDragged);
                    }
                    break;
                case 1:
                case 3:
                    setIsBeingDragged(false);
                    this.mActivePointerId = -1;
                    recycleVelocityTracker();
                    if (this.mScroller.springBack(this.mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                        animateScroll();
                    }
                    break;
                case 2:
                    int activePointerId = this.mActivePointerId;
                    if (activePointerId != -1) {
                        int pointerIndex = ev.findPointerIndex(activePointerId);
                        if (pointerIndex == -1) {
                            Log.e("StackScroller", "Invalid pointerId=" + activePointerId + " in onInterceptTouchEvent");
                        } else {
                            int y2 = (int) ev.getY(pointerIndex);
                            int x = (int) ev.getX(pointerIndex);
                            int yDiff = Math.abs(y2 - this.mLastMotionY);
                            int xDiff = Math.abs(x - this.mDownX);
                            if (yDiff > this.mTouchSlop && yDiff > xDiff) {
                                setIsBeingDragged(true);
                                this.mLastMotionY = y2;
                                this.mDownX = x;
                                initVelocityTrackerIfNotExists();
                                this.mVelocityTracker.addMovement(ev);
                            }
                        }
                    }
                    break;
            }
        } else {
            onSecondaryPointerUp(ev);
        }
        return this.mIsBeingDragged;
    }

    protected StackScrollAlgorithm createStackScrollAlgorithm(Context context) {
        return new StackScrollAlgorithm(context);
    }

    private boolean isInContentBounds(MotionEvent event) {
        return isInContentBounds(event.getY());
    }

    public boolean isInContentBounds(float y) {
        return y < ((float) (getHeight() - getEmptyBottomMargin()));
    }

    private void setIsBeingDragged(boolean isDragged) {
        this.mIsBeingDragged = isDragged;
        if (isDragged) {
            requestDisallowInterceptTouchEvent(true);
            cancelLongPress();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            cancelLongPress();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void clearChildFocus(View child) {
        super.clearChildFocus(child);
        if (this.mForcedScroll == child) {
            this.mForcedScroll = null;
        }
    }

    public void requestDisallowLongPress() {
        cancelLongPress();
    }

    public void requestDisallowDismiss() {
        this.mDisallowDismissInThisMotion = true;
    }

    @Override // android.view.View
    public void cancelLongPress() {
        this.mSwipeHelper.cancelLongPress();
    }

    @Override // com.android.systemui.statusbar.policy.ScrollAdapter
    public boolean isScrolledToTop() {
        return this.mOwnScrollY == 0;
    }

    public boolean isScrolledToBottom() {
        return this.mOwnScrollY >= getScrollRange();
    }

    @Override // com.android.systemui.statusbar.policy.ScrollAdapter
    public View getHostView() {
        return this;
    }

    public int getEmptyBottomMargin() {
        return Math.max(this.mMaxLayoutHeight - this.mContentHeight, 0);
    }

    public void checkSnoozeLeavebehind() {
        if (this.mCheckForLeavebehind) {
            this.mStatusBar.getGutsManager().closeAndSaveGuts(true, false, false, -1, -1, false);
            this.mCheckForLeavebehind = false;
        }
    }

    public void resetCheckSnoozeLeavebehind() {
        this.mCheckForLeavebehind = true;
    }

    public void onExpansionStarted() {
        this.mIsExpansionChanging = true;
        this.mAmbientState.setExpansionChanging(true);
        checkSnoozeLeavebehind();
    }

    public void onExpansionStopped() {
        this.mIsExpansionChanging = false;
        resetCheckSnoozeLeavebehind();
        this.mAmbientState.setExpansionChanging(false);
        if (!this.mIsExpanded) {
            setOwnScrollY(0);
            this.mStatusBar.resetUserExpandedStates();
            clearTemporaryViews();
            clearUserLockedViews();
        }
    }

    private void clearUserLockedViews() {
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                row.setUserLocked(false);
            }
        }
    }

    private void clearTemporaryViews() {
        clearTemporaryViewsInGroup(this);
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                clearTemporaryViewsInGroup(row.getChildrenContainer());
            }
        }
    }

    private void clearTemporaryViewsInGroup(ViewGroup viewGroup) {
        while (viewGroup != null && viewGroup.getTransientViewCount() != 0) {
            viewGroup.removeTransientView(viewGroup.getTransientView(0));
        }
    }

    public void onPanelTrackingStarted() {
        this.mPanelTracking = true;
        this.mAmbientState.setPanelTracking(true);
    }

    public void onPanelTrackingStopped() {
        this.mPanelTracking = false;
        this.mAmbientState.setPanelTracking(false);
    }

    public void resetScrollPosition() {
        this.mScroller.abortAnimation();
        setOwnScrollY(0);
    }

    private void setIsExpanded(boolean isExpanded) {
        boolean changed = isExpanded != this.mIsExpanded;
        this.mIsExpanded = isExpanded;
        this.mStackScrollAlgorithm.setIsExpanded(isExpanded);
        if (changed) {
            if (!this.mIsExpanded) {
                this.mGroupManager.collapseAllGroups();
                this.mExpandHelper.cancelImmediately();
            }
            updateNotificationAnimationStates();
            updateChronometers();
            requestChildrenUpdate();
        }
    }

    private void updateChronometers() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            updateChronometerForChild(getChildAt(i));
        }
    }

    private void updateChronometerForChild(View child) {
        if (child instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) child;
            row.setChronometerRunning(this.mIsExpanded);
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView.OnHeightChangedListener
    public void onHeightChanged(ExpandableView view, boolean needsAnimation) {
        ExpandableNotificationRow row;
        updateContentHeight();
        updateScrollPositionOnExpandInBottom(view);
        clampScrollPosition();
        notifyHeightChangeListener(view, needsAnimation);
        if (view instanceof ExpandableNotificationRow) {
            row = (ExpandableNotificationRow) view;
        } else {
            row = null;
        }
        if (row != null && (row == this.mFirstVisibleBackgroundChild || row.getNotificationParent() == this.mFirstVisibleBackgroundChild)) {
            updateAlgorithmLayoutMinHeight();
        }
        if (needsAnimation) {
            requestAnimationOnViewResize(row);
        }
        requestChildrenUpdate();
    }

    @Override // com.android.systemui.statusbar.ExpandableView.OnHeightChangedListener
    public void onReset(ExpandableView view) {
        updateAnimationState(view);
        updateChronometerForChild(view);
    }

    private void updateScrollPositionOnExpandInBottom(ExpandableView view) {
        if ((view instanceof ExpandableNotificationRow) && !onKeyguard()) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) view;
            if (!row.isUserLocked() || row == getFirstChildNotGone() || row.isSummaryWithChildren()) {
                return;
            }
            float endPosition = row.getTranslationY() + row.getActualHeight();
            if (row.isChildInGroup()) {
                endPosition += row.getNotificationParent().getTranslationY();
            }
            int layoutEnd = this.mMaxLayoutHeight + ((int) this.mStackTranslation);
            if (row != this.mLastVisibleBackgroundChild && this.mShelf.getVisibility() != 8) {
                layoutEnd -= this.mShelf.getIntrinsicHeight() + this.mPaddingBetweenElements;
            }
            if (endPosition > layoutEnd) {
                setOwnScrollY((int) ((this.mOwnScrollY + endPosition) - layoutEnd));
                this.mDisallowScrollingInThisMotion = true;
            }
        }
    }

    public void setOnHeightChangedListener(ExpandableView.OnHeightChangedListener mOnHeightChangedListener) {
        this.mOnHeightChangedListener = mOnHeightChangedListener;
    }

    public void setOnEmptySpaceClickListener(OnEmptySpaceClickListener listener) {
        this.mOnEmptySpaceClickListener = listener;
    }

    public void onChildAnimationFinished() {
        setAnimationRunning(false);
        requestChildrenUpdate();
        runAnimationFinishedRunnables();
        clearTransient();
        clearHeadsUpDisappearRunning();
    }

    private void clearHeadsUpDisappearRunning() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) view;
                row.setHeadsUpAnimatingAway(false);
                if (row.isSummaryWithChildren()) {
                    for (ExpandableNotificationRow child : row.getNotificationChildren()) {
                        child.setHeadsUpAnimatingAway(false);
                    }
                }
            }
        }
    }

    private void clearTransient() {
        for (ExpandableView view : this.mClearTransientViewsWhenFinished) {
            StackStateAnimator.removeTransientView(view);
        }
        this.mClearTransientViewsWhenFinished.clear();
    }

    private void runAnimationFinishedRunnables() {
        for (Runnable runnable : this.mAnimationFinishedRunnables) {
            runnable.run();
        }
        this.mAnimationFinishedRunnables.clear();
    }

    public void setDimmed(boolean dimmed, boolean animate) {
        boolean dimmed2 = dimmed & onKeyguard();
        this.mAmbientState.setDimmed(dimmed2);
        if (animate && this.mAnimationsEnabled) {
            this.mDimmedNeedsAnimation = true;
            this.mNeedsAnimation = true;
            animateDimmed(dimmed2);
        } else {
            setDimAmount(dimmed2 ? 1.0f : 0.0f);
        }
        requestChildrenUpdate();
    }

    boolean isDimmed() {
        return this.mAmbientState.isDimmed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDimAmount(float dimAmount) {
        this.mDimAmount = dimAmount;
        updateBackgroundDimming();
    }

    private void animateDimmed(boolean dimmed) {
        if (this.mDimAnimator != null) {
            this.mDimAnimator.cancel();
        }
        float target = dimmed ? 1.0f : 0.0f;
        if (target == this.mDimAmount) {
            return;
        }
        this.mDimAnimator = TimeAnimator.ofFloat(this.mDimAmount, target);
        this.mDimAnimator.setDuration(220L);
        this.mDimAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mDimAnimator.addListener(this.mDimEndListener);
        this.mDimAnimator.addUpdateListener(this.mDimUpdateListener);
        this.mDimAnimator.start();
    }

    public void setHideSensitive(boolean hideSensitive, boolean animate) {
        if (hideSensitive != this.mAmbientState.isHideSensitive()) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ExpandableView v = (ExpandableView) getChildAt(i);
                v.setHideSensitiveForIntrinsicHeight(hideSensitive);
            }
            this.mAmbientState.setHideSensitive(hideSensitive);
            if (animate && this.mAnimationsEnabled) {
                this.mHideSensitiveNeedsAnimation = true;
                this.mNeedsAnimation = true;
            }
            updateContentHeight();
            requestChildrenUpdate();
        }
    }

    public void setActivatedChild(ActivatableNotificationView activatedChild) {
        this.mAmbientState.setActivatedChild(activatedChild);
        if (this.mAnimationsEnabled) {
            this.mActivateNeedsAnimation = true;
            this.mNeedsAnimation = true;
        }
        requestChildrenUpdate();
    }

    public ActivatableNotificationView getActivatedChild() {
        return this.mAmbientState.getActivatedChild();
    }

    private void applyCurrentState() {
        this.mCurrentStackScrollState.apply();
        if (this.mListener != null) {
            this.mListener.onChildLocationsChanged();
        }
        runAnimationFinishedRunnables();
        setAnimationRunning(false);
        updateBackground();
        updateViewShadows();
        updateClippingToTopRoundedCorner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewShadows() {
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child.getVisibility() != 8) {
                this.mTmpSortedChildren.add(child);
            }
        }
        Collections.sort(this.mTmpSortedChildren, this.mViewPositionComparator);
        ExpandableView previous = null;
        for (int i2 = 0; i2 < this.mTmpSortedChildren.size(); i2++) {
            ExpandableView expandableView = this.mTmpSortedChildren.get(i2);
            float translationZ = expandableView.getTranslationZ();
            float otherZ = previous == null ? translationZ : previous.getTranslationZ();
            float diff = otherZ - translationZ;
            if (diff <= 0.0f || diff >= 0.1f) {
                expandableView.setFakeShadowIntensity(0.0f, 0.0f, 0, 0);
            } else {
                float yLocation = ((previous.getTranslationY() + previous.getActualHeight()) - expandableView.getTranslationY()) - previous.getExtraBottomPadding();
                expandableView.setFakeShadowIntensity(diff / 0.1f, previous.getOutlineAlpha(), (int) yLocation, previous.getOutlineTranslation());
            }
            previous = expandableView;
        }
        this.mTmpSortedChildren.clear();
    }

    public void updateDecorViews(boolean lightTheme) {
        if (lightTheme == this.mUsingLightTheme) {
            return;
        }
        this.mUsingLightTheme = lightTheme;
        Context context = new ContextThemeWrapper(this.mContext, lightTheme ? 2131886648 : 2131886644);
        int textColor = Utils.getColorAttr(context, R.attr.wallpaperTextColor);
        this.mFooterView.setTextColor(textColor);
        this.mEmptyShadeView.setTextColor(textColor);
    }

    public void goToFullShade(long delay) {
        this.mGoToFullShadeNeedsAnimation = true;
        this.mGoToFullShadeDelay = delay;
        this.mNeedsAnimation = true;
        requestChildrenUpdate();
    }

    public void cancelExpandHelper() {
        this.mExpandHelper.cancel();
    }

    public void setIntrinsicPadding(int intrinsicPadding) {
        this.mIntrinsicPadding = intrinsicPadding;
        this.mAmbientState.setIntrinsicPadding(intrinsicPadding);
    }

    public int getIntrinsicPadding() {
        return this.mIntrinsicPadding;
    }

    public float getNotificationsTopY() {
        return this.mTopPadding + getStackTranslation();
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public void setDark(boolean dark, boolean animate, PointF touchWakeUpScreenLocation) {
        if (this.mAmbientState.isDark() == dark) {
            return;
        }
        this.mAmbientState.setDark(dark);
        if (animate && this.mAnimationsEnabled) {
            this.mDarkNeedsAnimation = true;
            this.mDarkAnimationOriginIndex = findDarkAnimationOriginIndex(touchWakeUpScreenLocation);
            this.mNeedsAnimation = true;
        } else {
            if (this.mDarkAmountAnimator != null) {
                this.mDarkAmountAnimator.cancel();
            }
            setDarkAmount(dark ? 1.0f : 0.0f);
            updateBackground();
        }
        requestChildrenUpdate();
        applyCurrentBackgroundBounds();
        updateWillNotDraw();
        notifyHeightChangeListener(this.mShelf);
    }

    private void updateAntiBurnInTranslation() {
        setTranslationX(this.mAntiBurnInOffsetX * this.mDarkAmount);
    }

    private void updateWillNotDraw() {
        boolean willDraw = this.mShouldDrawNotificationBackground;
        setWillNotDraw(willDraw ? false : true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDarkAmount(float darkAmount) {
        this.mDarkAmount = darkAmount;
        boolean wasFullyDark = this.mAmbientState.isFullyDark();
        this.mAmbientState.setDarkAmount(darkAmount);
        if (this.mAmbientState.isFullyDark() != wasFullyDark) {
            updateContentHeight();
            DozeParameters dozeParameters = DozeParameters.getInstance(this.mContext);
            if (this.mAmbientState.isFullyDark() && dozeParameters.shouldControlScreenOff()) {
                this.mShelf.fadeInTranslating();
            }
        }
        updateAlgorithmHeightAndPadding();
        updateBackgroundDimming();
        updateAntiBurnInTranslation();
        requestChildrenUpdate();
    }

    public float getDarkAmount() {
        return this.mDarkAmount;
    }

    private void startDarkAmountAnimation() {
        Property<NotificationStackScrollLayout, Float> property = DARK_AMOUNT;
        float[] fArr = new float[2];
        fArr[0] = this.mDarkAmount;
        fArr[1] = this.mAmbientState.isDark() ? 1.0f : 0.0f;
        ObjectAnimator darkAnimator = ObjectAnimator.ofFloat(this, property, fArr);
        darkAnimator.setDuration(500L);
        darkAnimator.setInterpolator(Interpolators.ALPHA_IN);
        darkAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.11
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationStackScrollLayout.this.mDarkAmountAnimator = null;
            }
        });
        if (this.mDarkAmountAnimator != null) {
            this.mDarkAmountAnimator.cancel();
        }
        this.mDarkAmountAnimator = darkAnimator;
        this.mDarkAmountAnimator.start();
    }

    private int findDarkAnimationOriginIndex(PointF screenLocation) {
        if (screenLocation == null || screenLocation.y < this.mTopPadding) {
            return -1;
        }
        if (screenLocation.y > getBottomMostNotificationBottom()) {
            return -2;
        }
        View child = getClosestChildAtRawPosition(screenLocation.x, screenLocation.y);
        if (child == null) {
            return -1;
        }
        return getNotGoneIndex(child);
    }

    private int getNotGoneIndex(View child) {
        int count = getChildCount();
        int notGoneIndex = 0;
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (child == v) {
                return notGoneIndex;
            }
            if (v.getVisibility() != 8) {
                notGoneIndex++;
            }
        }
        return -1;
    }

    public void setFooterView(FooterView footerView) {
        int index = -1;
        if (this.mFooterView != null) {
            index = indexOfChild(this.mFooterView);
            removeView(this.mFooterView);
        }
        this.mFooterView = footerView;
        addView(this.mFooterView, index);
    }

    public void setEmptyShadeView(EmptyShadeView emptyShadeView) {
        int index = -1;
        if (this.mEmptyShadeView != null) {
            index = indexOfChild(this.mEmptyShadeView);
            removeView(this.mEmptyShadeView);
        }
        this.mEmptyShadeView = emptyShadeView;
        addView(this.mEmptyShadeView, index);
    }

    public void updateEmptyShadeView(boolean visible) {
        this.mEmptyShadeView.setVisible(visible, this.mIsExpanded && this.mAnimationsEnabled);
        int oldTextRes = this.mEmptyShadeView.getTextResource();
        int newTextRes = this.mStatusBar.areNotificationsHidden() ? R.string.dnd_suppressing_shade_text : R.string.empty_shade_text;
        if (oldTextRes != newTextRes) {
            this.mEmptyShadeView.setText(newTextRes);
        }
    }

    public void updateFooterView(boolean visible, boolean showDismissView) {
        if (this.mFooterView == null) {
            return;
        }
        boolean animate = this.mIsExpanded && this.mAnimationsEnabled;
        this.mFooterView.setVisible(visible, animate);
        this.mFooterView.setSecondaryVisible(showDismissView, animate);
    }

    public void setDismissAllInProgress(boolean dismissAllInProgress) {
        this.mDismissAllInProgress = dismissAllInProgress;
        this.mAmbientState.setDismissAllInProgress(dismissAllInProgress);
        handleDismissAllClipping();
    }

    private void handleDismissAllClipping() {
        int count = getChildCount();
        boolean previousChildWillBeDismissed = false;
        for (int i = 0; i < count; i++) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child.getVisibility() != 8) {
                if (this.mDismissAllInProgress && previousChildWillBeDismissed) {
                    child.setMinClipTopAmount(child.getClipTopAmount());
                } else {
                    child.setMinClipTopAmount(0);
                }
                previousChildWillBeDismissed = canChildBeDismissed(child);
            }
        }
    }

    public boolean isFooterViewNotGone() {
        return (this.mFooterView == null || this.mFooterView.getVisibility() == 8 || this.mFooterView.willBeGone()) ? false : true;
    }

    public boolean isFooterViewContentVisible() {
        return this.mFooterView != null && this.mFooterView.isContentVisible();
    }

    public int getFooterViewHeight() {
        if (this.mFooterView == null) {
            return 0;
        }
        return this.mFooterView.getHeight() + this.mPaddingBetweenElements;
    }

    public int getEmptyShadeViewHeight() {
        return this.mEmptyShadeView.getHeight();
    }

    public float getBottomMostNotificationBottom() {
        int count = getChildCount();
        float max = 0.0f;
        for (int childIdx = 0; childIdx < count; childIdx++) {
            ExpandableView child = (ExpandableView) getChildAt(childIdx);
            if (child.getVisibility() != 8) {
                float bottom = (child.getTranslationY() + child.getActualHeight()) - child.getClipBottomAmount();
                if (bottom > max) {
                    max = bottom;
                }
            }
        }
        return getStackTranslation() + max;
    }

    public void setStatusBar(StatusBar statusBar) {
        this.mStatusBar = statusBar;
    }

    public void setGroupManager(NotificationGroupManager groupManager) {
        this.mGroupManager = groupManager;
    }

    public void onGoToKeyguard() {
        requestAnimateEverything();
    }

    private void requestAnimateEverything() {
        if (this.mIsExpanded && this.mAnimationsEnabled) {
            this.mEverythingNeedsAnimation = true;
            this.mNeedsAnimation = true;
            requestChildrenUpdate();
        }
    }

    public boolean isBelowLastNotification(float touchX, float touchY) {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            ExpandableView child = (ExpandableView) getChildAt(i);
            if (child.getVisibility() != 8) {
                float childTop = child.getY();
                if (childTop > touchY) {
                    return false;
                }
                boolean belowChild = touchY > (((float) child.getActualHeight()) + childTop) - ((float) child.getClipBottomAmount());
                if (child == this.mFooterView) {
                    if (!belowChild && !this.mFooterView.isOnEmptySpace(touchX - this.mFooterView.getX(), touchY - childTop)) {
                        return false;
                    }
                } else {
                    if (child == this.mEmptyShadeView) {
                        return true;
                    }
                    if (!belowChild) {
                        return false;
                    }
                }
            }
        }
        int i2 = this.mTopPadding;
        return touchY > ((float) i2) + this.mStackTranslation;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationGroupManager.OnGroupChangeListener
    public void onGroupExpansionChanged(final ExpandableNotificationRow changedRow, boolean expanded) {
        boolean animated = !this.mGroupExpandedForMeasure && this.mAnimationsEnabled && (this.mIsExpanded || changedRow.isPinned());
        if (animated) {
            this.mExpandedGroupView = changedRow;
            this.mNeedsAnimation = true;
        }
        changedRow.setChildrenExpanded(expanded, animated);
        if (!this.mGroupExpandedForMeasure) {
            onHeightChanged(changedRow, false);
        }
        runAfterAnimationFinished(new Runnable() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.12
            @Override // java.lang.Runnable
            public void run() {
                changedRow.onFinishedExpansionChange();
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.NotificationGroupManager.OnGroupChangeListener
    public void onGroupCreatedFromChildren(NotificationGroupManager.NotificationGroup group) {
        this.mStatusBar.requestNotificationUpdate();
    }

    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        super.onInitializeAccessibilityEventInternal(event);
        event.setScrollable(this.mScrollable);
        event.setScrollX(this.mScrollX);
        event.setScrollY(this.mOwnScrollY);
        event.setMaxScrollX(this.mScrollX);
        event.setMaxScrollY(getScrollRange());
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        if (this.mScrollable) {
            info.setScrollable(true);
            if (this.mBackwardScrollable) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
            }
            if (this.mForwardScrollable) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
            }
        }
        info.setClassName(ScrollView.class.getName());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004e  */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0021, code lost:
    
        if (r10 != 16908346) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        int targetScrollY;
        if (super.performAccessibilityActionInternal(action, arguments)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        int direction = -1;
        if (action == 4096) {
            direction = 1;
            int viewportHeight = (((getHeight() - this.mPaddingBottom) - this.mTopPadding) - this.mPaddingTop) - this.mShelf.getIntrinsicHeight();
            targetScrollY = Math.max(0, Math.min(this.mOwnScrollY + (direction * viewportHeight), getScrollRange()));
            if (targetScrollY != this.mOwnScrollY) {
                this.mScroller.startScroll(this.mScrollX, this.mOwnScrollY, 0, targetScrollY - this.mOwnScrollY);
                animateScroll();
                return true;
            }
        } else if (action == 8192 || action == 16908344) {
            int viewportHeight2 = (((getHeight() - this.mPaddingBottom) - this.mTopPadding) - this.mPaddingTop) - this.mShelf.getIntrinsicHeight();
            targetScrollY = Math.max(0, Math.min(this.mOwnScrollY + (direction * viewportHeight2), getScrollRange()));
            if (targetScrollY != this.mOwnScrollY) {
                this.mScroller.startScroll(this.mScrollX, this.mOwnScrollY, 0, targetScrollY - this.mOwnScrollY);
                animateScroll();
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationGroupManager.OnGroupChangeListener
    public void onGroupsChanged() {
        this.mStatusBar.requestNotificationUpdate();
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void generateChildOrderChangedEvent() {
        if (this.mIsExpanded && this.mAnimationsEnabled) {
            this.mGenerateChildOrderChangedEvent = true;
            this.mNeedsAnimation = true;
            requestChildrenUpdate();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public int getContainerChildCount() {
        return getChildCount();
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public View getContainerChildAt(int i) {
        return getChildAt(i);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void removeContainerView(View v) {
        removeView(v);
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void addContainerView(View v) {
        addView(v);
    }

    public void runAfterAnimationFinished(Runnable runnable) {
        this.mAnimationFinishedRunnables.add(runnable);
    }

    public void setHeadsUpManager(HeadsUpManagerPhone headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
        this.mAmbientState.setHeadsUpManager(headsUpManager);
        this.mHeadsUpManager.addListener(this.mRoundnessManager);
    }

    public void generateHeadsUpAnimation(ExpandableNotificationRow row, boolean isHeadsUp) {
        if (this.mAnimationsEnabled) {
            if (isHeadsUp || this.mHeadsUpGoingAwayAnimationsAllowed) {
                this.mHeadsUpChangeAnimations.add(new Pair<>(row, Boolean.valueOf(isHeadsUp)));
                this.mNeedsAnimation = true;
                if (!this.mIsExpanded && !isHeadsUp) {
                    row.setHeadsUpAnimatingAway(true);
                }
                requestChildrenUpdate();
            }
        }
    }

    public void setShadeExpanded(boolean shadeExpanded) {
        this.mAmbientState.setShadeExpanded(shadeExpanded);
        this.mStateAnimator.setShadeExpanded(shadeExpanded);
    }

    public void setHeadsUpBoundaries(int height, int bottomBarHeight) {
        this.mAmbientState.setMaxHeadsUpTranslation(height - bottomBarHeight);
        this.mStateAnimator.setHeadsUpAppearHeightBottom(height);
        requestChildrenUpdate();
    }

    public void setTrackingHeadsUp(ExpandableNotificationRow row) {
        this.mTrackingHeadsUp = row != null;
        this.mRoundnessManager.setTrackingHeadsUp(row);
    }

    public void setScrimController(ScrimController scrimController) {
        this.mScrimController = scrimController;
        this.mScrimController.setScrimBehindChangeRunnable(new Runnable() { // from class: com.android.systemui.statusbar.stack.-$$Lambda$NotificationStackScrollLayout$3VfNfgMGO3-TgMmei2bv0geU0dY
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateBackgroundDimming();
            }
        });
    }

    public void forceNoOverlappingRendering(boolean force) {
        this.mForceNoOverlappingRendering = force;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return !this.mForceNoOverlappingRendering && super.hasOverlappingRendering();
    }

    public void setAnimationRunning(boolean animationRunning) {
        if (animationRunning != this.mAnimationRunning) {
            if (animationRunning) {
                getViewTreeObserver().addOnPreDrawListener(this.mRunningAnimationUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mRunningAnimationUpdater);
            }
            this.mAnimationRunning = animationRunning;
            updateContinuousShadowDrawing();
        }
    }

    public boolean isExpanded() {
        return this.mIsExpanded;
    }

    public void setPulsing(boolean pulsing, boolean animated) {
        if (!this.mPulsing && !pulsing) {
            return;
        }
        this.mPulsing = pulsing;
        this.mNeedingPulseAnimation = animated ? getFirstChildNotGone() : null;
        this.mAmbientState.setPulsing(pulsing);
        updateNotificationAnimationStates();
        updateAlgorithmHeightAndPadding();
        updateContentHeight();
        requestChildrenUpdate();
        notifyHeightChangeListener(null, animated);
        this.mNeedsAnimation |= animated;
    }

    private void generatePulsingAnimationEvent() {
        if (this.mNeedingPulseAnimation != null) {
            int type = this.mPulsing ? 19 : 20;
            this.mAnimationEvents.add(new AnimationEvent(this.mNeedingPulseAnimation, type));
            this.mNeedingPulseAnimation = null;
        }
    }

    public void setFadingOut(boolean fadingOut) {
        if (fadingOut != this.mFadingOut) {
            this.mFadingOut = fadingOut;
            updateFadingState();
        }
    }

    public void setParentNotFullyVisible(boolean parentNotFullyVisible) {
        if (this.mScrimController != null && parentNotFullyVisible != this.mParentNotFullyVisible) {
            this.mParentNotFullyVisible = parentNotFullyVisible;
            updateFadingState();
        }
    }

    private void updateFadingState() {
        applyCurrentBackgroundBounds();
        updateSrcDrawing();
    }

    @Override // android.view.View
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        setFadingOut(alpha != 1.0f);
    }

    public void setQsExpanded(boolean qsExpanded) {
        this.mQsExpanded = qsExpanded;
        updateAlgorithmLayoutMinHeight();
        updateScrollability();
    }

    public void setQsExpansionFraction(float qsExpansionFraction) {
        this.mQsExpansionFraction = qsExpansionFraction;
    }

    public void setOwnScrollY(int ownScrollY) {
        if (ownScrollY != this.mOwnScrollY) {
            onScrollChanged(this.mScrollX, ownScrollY, this.mScrollX, this.mOwnScrollY);
            this.mOwnScrollY = ownScrollY;
            updateForwardAndBackwardScrollability();
            requestChildrenUpdate();
        }
    }

    public void setShelf(NotificationShelf shelf) {
        int index = -1;
        if (this.mShelf != null) {
            index = indexOfChild(this.mShelf);
            removeView(this.mShelf);
        }
        this.mShelf = shelf;
        addView(this.mShelf, index);
        this.mAmbientState.setShelf(shelf);
        this.mStateAnimator.setShelf(shelf);
        shelf.bind(this.mAmbientState, this);
    }

    public NotificationShelf getNotificationShelf() {
        return this.mShelf;
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void setMaxDisplayedNotifications(int maxDisplayedNotifications) {
        if (this.mMaxDisplayedNotifications != maxDisplayedNotifications) {
            this.mMaxDisplayedNotifications = maxDisplayedNotifications;
            updateContentHeight();
            notifyHeightChangeListener(this.mShelf);
        }
    }

    public void setShouldShowShelfOnly(boolean shouldShowShelfOnly) {
        this.mShouldShowShelfOnly = shouldShowShelfOnly;
        updateAlgorithmLayoutMinHeight();
    }

    public int getMinExpansionHeight() {
        return this.mShelf.getIntrinsicHeight() - ((this.mShelf.getIntrinsicHeight() - this.mStatusBarHeight) / 2);
    }

    public void setInHeadsUpPinnedMode(boolean inHeadsUpPinnedMode) {
        this.mInHeadsUpPinnedMode = inHeadsUpPinnedMode;
        updateClipping();
    }

    public void setHeadsUpAnimatingAway(boolean headsUpAnimatingAway) {
        this.mHeadsUpAnimatingAway = headsUpAnimatingAway;
        updateClipping();
    }

    public void setStatusBarState(int statusBarState) {
        this.mStatusBarState = statusBarState;
        this.mAmbientState.setStatusBarState(statusBarState);
    }

    public void setExpandingVelocity(float expandingVelocity) {
        this.mAmbientState.setExpandingVelocity(expandingVelocity);
    }

    public float getOpeningHeight() {
        if (this.mEmptyShadeView.getVisibility() == 8) {
            return getMinExpansionHeight();
        }
        return getAppearEndPosition();
    }

    public void setIsFullWidth(boolean isFullWidth) {
        this.mAmbientState.setPanelFullWidth(isFullWidth);
    }

    public void setUnlockHintRunning(boolean running) {
        this.mAmbientState.setUnlockHintRunning(running);
    }

    public void setQsCustomizerShowing(boolean isShowing) {
        this.mAmbientState.setQsCustomizerShowing(isShowing);
        requestChildrenUpdate();
    }

    public void setHeadsUpGoingAwayAnimationsAllowed(boolean headsUpGoingAwayAnimationsAllowed) {
        this.mHeadsUpGoingAwayAnimationsAllowed = headsUpGoingAwayAnimationsAllowed;
    }

    public void setAntiBurnInOffsetX(int antiBurnInOffsetX) {
        this.mAntiBurnInOffsetX = antiBurnInOffsetX;
        updateAntiBurnInTranslation();
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        String str;
        Object[] objArr = new Object[9];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = this.mPulsing ? "T" : "f";
        objArr[2] = this.mAmbientState.isQsCustomizerShowing() ? "T" : "f";
        if (getVisibility() == 0) {
            str = "visible";
        } else {
            str = getVisibility() == 8 ? "gone" : "invisible";
        }
        objArr[3] = str;
        objArr[4] = Float.valueOf(getAlpha());
        objArr[5] = Integer.valueOf(this.mAmbientState.getScrollY());
        objArr[6] = Integer.valueOf(this.mMaxTopPadding);
        objArr[7] = this.mShouldShowShelfOnly ? "T" : "f";
        objArr[8] = Float.valueOf(this.mQsExpansionFraction);
        pw.println(String.format("[%s: pulsing=%s qsCustomizerShowing=%s visibility=%s alpha:%f scrollY:%d maxTopPadding:%d showShelfOnly=%s qsExpandFraction=%f]", objArr));
    }

    public boolean isFullyDark() {
        return this.mAmbientState.isFullyDark();
    }

    public void addOnExpandedHeightListener(BiConsumer<Float, Float> listener) {
        this.mExpandedHeightListeners.add(listener);
    }

    public void removeOnExpandedHeightListener(BiConsumer<Float, Float> listener) {
        this.mExpandedHeightListeners.remove(listener);
    }

    public void setHeadsUpAppearanceController(HeadsUpAppearanceController headsUpAppearanceController) {
        this.mHeadsUpAppearanceController = headsUpAppearanceController;
    }

    private class NotificationSwipeHelper extends SwipeHelper implements NotificationSwipeActionHelper {
        private Runnable mFalsingCheck;
        private Handler mHandler;

        public NotificationSwipeHelper(int swipeDirection, SwipeHelper.Callback callback, Context context) {
            super(swipeDirection, callback, context);
            this.mHandler = new Handler();
            this.mFalsingCheck = new Runnable() { // from class: com.android.systemui.statusbar.stack.NotificationStackScrollLayout.NotificationSwipeHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    NotificationSwipeHelper.this.resetExposedMenuView(true, true);
                }
            };
        }

        @Override // com.android.systemui.SwipeHelper
        public void onDownUpdate(View currView, MotionEvent ev) {
            NotificationStackScrollLayout.this.mTranslatingParentView = currView;
            if (NotificationStackScrollLayout.this.mCurrMenuRow != null) {
                NotificationStackScrollLayout.this.mCurrMenuRow.onTouchEvent(currView, ev, 0.0f);
            }
            NotificationStackScrollLayout.this.mCurrMenuRow = null;
            this.mHandler.removeCallbacks(this.mFalsingCheck);
            resetExposedMenuView(true, false);
            if (currView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) currView;
                NotificationStackScrollLayout.this.mCurrMenuRow = row.createMenu();
                NotificationStackScrollLayout.this.mCurrMenuRow.setSwipeActionHelper(this);
                NotificationStackScrollLayout.this.mCurrMenuRow.setMenuClickListener(NotificationStackScrollLayout.this);
                NotificationStackScrollLayout.this.mCurrMenuRow.onTouchEvent(currView, ev, 0.0f);
            }
        }

        @Override // com.android.systemui.SwipeHelper
        public void onMoveUpdate(View view, MotionEvent ev, float translation, float delta) {
            this.mHandler.removeCallbacks(this.mFalsingCheck);
            if (NotificationStackScrollLayout.this.mCurrMenuRow != null) {
                NotificationStackScrollLayout.this.mCurrMenuRow.onTouchEvent(view, ev, 0.0f);
            }
        }

        @Override // com.android.systemui.SwipeHelper
        public boolean handleUpEvent(MotionEvent ev, View animView, float velocity, float translation) {
            if (NotificationStackScrollLayout.this.mCurrMenuRow != null) {
                return NotificationStackScrollLayout.this.mCurrMenuRow.onTouchEvent(animView, ev, velocity);
            }
            return false;
        }

        @Override // com.android.systemui.SwipeHelper
        public void dismissChild(View view, float velocity, boolean useAccelerateInterpolator) {
            super.dismissChild(view, velocity, useAccelerateInterpolator);
            if (NotificationStackScrollLayout.this.mIsExpanded) {
                NotificationStackScrollLayout.this.handleChildViewDismissed(view);
            }
            NotificationStackScrollLayout.this.mStatusBar.getGutsManager().closeAndSaveGuts(true, false, false, -1, -1, false);
            handleMenuCoveredOrDismissed();
        }

        @Override // com.android.systemui.SwipeHelper
        public void snapChild(View animView, float targetLeft, float velocity) {
            super.snapChild(animView, targetLeft, velocity);
            NotificationStackScrollLayout.this.onDragCancelled(animView);
            if (targetLeft == 0.0f) {
                handleMenuCoveredOrDismissed();
            }
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public void snooze(StatusBarNotification sbn, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
            NotificationStackScrollLayout.this.mStatusBar.setNotificationSnoozed(sbn, snoozeOption);
        }

        @Override // com.android.systemui.SwipeHelper, com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public boolean isFalseGesture(MotionEvent ev) {
            return super.isFalseGesture(ev);
        }

        private void handleMenuCoveredOrDismissed() {
            if (NotificationStackScrollLayout.this.mMenuExposedView != null && NotificationStackScrollLayout.this.mMenuExposedView == NotificationStackScrollLayout.this.mTranslatingParentView) {
                NotificationStackScrollLayout.this.mMenuExposedView = null;
            }
        }

        @Override // com.android.systemui.SwipeHelper
        public Animator getViewTranslationAnimator(View v, float target, ValueAnimator.AnimatorUpdateListener listener) {
            if (v instanceof ExpandableNotificationRow) {
                return ((ExpandableNotificationRow) v).getTranslateViewAnimator(target, listener);
            }
            return super.getViewTranslationAnimator(v, target, listener);
        }

        @Override // com.android.systemui.SwipeHelper
        public void setTranslation(View v, float translate) {
            ((ExpandableView) v).setTranslation(translate);
        }

        @Override // com.android.systemui.SwipeHelper
        public float getTranslation(View v) {
            return ((ExpandableView) v).getTranslation();
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public void dismiss(View animView, float velocity) {
            dismissChild(animView, velocity, !swipedFastEnough(0.0f, 0.0f));
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public void snap(View animView, float targetLeft, float velocity) {
            snapChild(animView, targetLeft, velocity);
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public boolean swipedFarEnough(float translation, float viewSize) {
            return swipedFarEnough();
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public boolean swipedFastEnough(float translation, float velocity) {
            return swipedFastEnough();
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
        public float getMinDismissVelocity() {
            return getEscapeVelocity();
        }

        public void onMenuShown(View animView) {
            NotificationStackScrollLayout.this.onDragCancelled(animView);
            if (NotificationStackScrollLayout.this.isAntiFalsingNeeded()) {
                this.mHandler.removeCallbacks(this.mFalsingCheck);
                this.mHandler.postDelayed(this.mFalsingCheck, 4000L);
            }
        }

        public void closeControlsIfOutsideTouch(MotionEvent ev) {
            NotificationGuts guts = NotificationStackScrollLayout.this.mStatusBar.getGutsManager().getExposedGuts();
            View view = null;
            if (guts == null || guts.getGutsContent().isLeavebehind()) {
                if (NotificationStackScrollLayout.this.mCurrMenuRow != null && NotificationStackScrollLayout.this.mCurrMenuRow.isMenuVisible() && NotificationStackScrollLayout.this.mTranslatingParentView != null) {
                    view = NotificationStackScrollLayout.this.mTranslatingParentView;
                }
            } else {
                view = guts;
            }
            if (view != null && !NotificationStackScrollLayout.this.isTouchInView(ev, view)) {
                NotificationStackScrollLayout.this.mStatusBar.getGutsManager().closeAndSaveGuts(false, false, true, -1, -1, false);
                resetExposedMenuView(true, true);
            }
        }

        public void resetExposedMenuView(boolean animate, boolean force) {
            if (NotificationStackScrollLayout.this.mMenuExposedView != null) {
                if (force || NotificationStackScrollLayout.this.mMenuExposedView != NotificationStackScrollLayout.this.mTranslatingParentView) {
                    View prevMenuExposedView = NotificationStackScrollLayout.this.mMenuExposedView;
                    if (!animate) {
                        if (NotificationStackScrollLayout.this.mMenuExposedView instanceof ExpandableNotificationRow) {
                            ExpandableNotificationRow row = (ExpandableNotificationRow) NotificationStackScrollLayout.this.mMenuExposedView;
                            if (!row.isRemoved()) {
                                row.resetTranslation();
                            }
                        }
                    } else {
                        Animator anim = getViewTranslationAnimator(prevMenuExposedView, 0.0f, null);
                        if (anim != null) {
                            anim.start();
                        }
                    }
                    NotificationStackScrollLayout.this.mMenuExposedView = null;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTouchInView(MotionEvent ev, View view) {
        int height;
        if (view == null) {
            return false;
        }
        if (view instanceof ExpandableView) {
            height = ((ExpandableView) view).getActualHeight();
        } else {
            height = view.getHeight();
        }
        int rx = (int) ev.getRawX();
        int ry = (int) ev.getRawY();
        view.getLocationOnScreen(this.mTempInt2);
        int x = this.mTempInt2[0];
        int y = this.mTempInt2[1];
        Rect rect = new Rect(x, y, view.getWidth() + x, y + height);
        boolean ret = rect.contains(rx, ry);
        return ret;
    }

    private void updateContinuousShadowDrawing() {
        boolean continuousShadowUpdate = this.mAnimationRunning || !this.mAmbientState.getDraggedViews().isEmpty();
        if (continuousShadowUpdate != this.mContinuousShadowUpdate) {
            if (continuousShadowUpdate) {
                getViewTreeObserver().addOnPreDrawListener(this.mShadowUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mShadowUpdater);
            }
            this.mContinuousShadowUpdate = continuousShadowUpdate;
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListContainer
    public void resetExposedMenuView(boolean animate, boolean force) {
        this.mSwipeHelper.resetExposedMenuView(animate, force);
    }

    public void closeControlsIfOutsideTouch(MotionEvent ev) {
        this.mSwipeHelper.closeControlsIfOutsideTouch(ev);
    }

    static class AnimationEvent {
        static AnimationFilter[] FILTERS = {new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateDimmed().animateZ(), new AnimationFilter().animateShadowAlpha(), new AnimationFilter().animateShadowAlpha().animateHeight(), new AnimationFilter().animateZ(), new AnimationFilter().animateDimmed(), new AnimationFilter().animateAlpha().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ(), null, new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateDimmed().animateZ().hasDelays(), new AnimationFilter().animateHideSensitive(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateAlpha().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateShadowAlpha().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateAlpha().animateShadowAlpha().animateDark().animateDimmed().animateHideSensitive().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateAlpha().animateY(), new AnimationFilter().animateAlpha().animateY()};
        static int[] LENGTHS = {464, 464, 360, 360, 360, 360, 220, 220, 360, 500, 448, 360, 360, 360, 550, 300, 300, 360, 360, 550, 275};
        final int animationType;
        final View changingView;
        int darkAnimationOriginIndex;
        final long eventStartTime;
        final AnimationFilter filter;
        boolean headsUpFromBottom;
        final long length;
        View viewAfterChangingView;

        AnimationEvent(View view, int type) {
            this(view, type, LENGTHS[type]);
        }

        AnimationEvent(View view, int type, AnimationFilter filter) {
            this(view, type, LENGTHS[type], filter);
        }

        AnimationEvent(View view, int type, long length) {
            this(view, type, length, FILTERS[type]);
        }

        AnimationEvent(View view, int type, long length, AnimationFilter filter) {
            this.eventStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.changingView = view;
            this.animationType = type;
            this.length = length;
            this.filter = filter;
        }

        static long combineLength(ArrayList<AnimationEvent> events) {
            long length = 0;
            int size = events.size();
            for (int i = 0; i < size; i++) {
                AnimationEvent event = events.get(i);
                length = Math.max(length, event.length);
                if (event.animationType == 10) {
                    return event.length;
                }
            }
            return length;
        }
    }
}
