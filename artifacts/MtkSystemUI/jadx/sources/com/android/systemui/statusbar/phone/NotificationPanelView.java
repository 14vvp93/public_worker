package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardStatusView;
import com.android.systemui.DejankUtils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.FlingAnimationUtils;
import com.android.systemui.statusbar.GestureRecorder;
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.ActivityLaunchAnimator;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcher;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.stack.AnimationProperties;
import com.android.systemui.statusbar.stack.NotificationStackScrollLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public class NotificationPanelView extends PanelView implements View.OnClickListener, QS.HeightListener, ExpandableView.OnHeightChangedListener, KeyguardAffordanceHelper.Callback, OnHeadsUpChangedListener, NotificationStackScrollLayout.OnEmptySpaceClickListener, NotificationStackScrollLayout.OnOverscrollTopChangedListener {
    private final AnimatableProperty PANEL_ALPHA;
    private final AnimationProperties PANEL_ALPHA_IN_PROPERTIES;
    private final AnimationProperties PANEL_ALPHA_OUT_PROPERTIES;
    private final AccessibilityManager mAccessibilityManager;
    private KeyguardAffordanceHelper mAffordanceHelper;
    private final Paint mAlphaPaint;
    private int mAmbientIndicationBottomPadding;
    private final Runnable mAnimateKeyguardBottomAreaInvisibleEndRunnable;
    private final Runnable mAnimateKeyguardStatusBarInvisibleEndRunnable;
    private final Runnable mAnimateKeyguardStatusViewGoneEndRunnable;
    private final Runnable mAnimateKeyguardStatusViewInvisibleEndRunnable;
    private final Runnable mAnimateKeyguardStatusViewVisibleEndRunnable;
    private boolean mAnimateNextPositionUpdate;
    private AnimatorListenerAdapter mAnimatorListenerAdapter;
    private boolean mBlockTouches;
    private int mBouncerTop;
    private KeyguardClockPositionAlgorithm mClockPositionAlgorithm;
    private KeyguardClockPositionAlgorithm.Result mClockPositionResult;
    private boolean mClosingWithAlphaFadeOut;
    private boolean mCollapsedOnDown;
    private boolean mConflictingQsExpansionGesture;
    private int mCurrentPanelAlpha;
    private float mDarkAmount;
    private float mDarkAmountTarget;
    private ValueAnimator mDarkAnimator;
    private boolean mDozing;
    private boolean mDozingOnDown;
    private float mEmptyDragAmount;
    private float mExpandOffset;
    private boolean mExpandingFromHeadsUp;
    private FalsingManager mFalsingManager;
    private FlingAnimationUtils mFlingAnimationUtils;
    private final FragmentHostManager.FragmentListener mFragmentListener;
    private NotificationGroupManager mGroupManager;
    private boolean mHeadsUpAnimatingAway;
    private HeadsUpAppearanceController mHeadsUpAppearanceController;
    private Runnable mHeadsUpExistenceChangedRunnable;
    private HeadsUpTouchHelper mHeadsUpTouchHelper;
    private boolean mHideIconsDuringNotificationLaunch;
    private int mIndicationBottomPadding;
    private float mInitialHeightOnTouch;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private boolean mIntercepting;
    private boolean mIsExpanding;
    private boolean mIsExpansionFromHeadsUp;
    private boolean mIsFullWidth;
    private boolean mIsLaunchTransitionFinished;
    private boolean mIsLaunchTransitionRunning;
    private boolean mKeyguardShowing;
    private KeyguardStatusBarView mKeyguardStatusBar;
    private float mKeyguardStatusBarAnimateAlpha;
    private KeyguardStatusView mKeyguardStatusView;
    private boolean mKeyguardStatusViewAnimating;
    private KeyguardUserSwitcher mKeyguardUserSwitcher;
    private String mLastCameraLaunchSource;
    private int mLastOrientation;
    private float mLastOverscroll;
    private float mLastTouchX;
    private float mLastTouchY;
    private Runnable mLaunchAnimationEndRunnable;
    private boolean mLaunchingAffordance;
    private boolean mListenForHeadsUp;
    private LockscreenGestureLogger mLockscreenGestureLogger;
    private int mMaxFadeoutHeight;
    private int mNavigationBarBottomHeight;
    private boolean mNoVisibleNotifications;
    protected NotificationsQuickSettingsContainer mNotificationContainerParent;
    protected NotificationStackScrollLayout mNotificationStackScroller;
    private int mNotificationsHeaderCollideDistance;
    private int mOldLayoutDirection;
    private boolean mOnlyAffordanceInThisMotion;
    private int mPanelAlpha;
    private Runnable mPanelAlphaEndAction;
    private boolean mPanelExpanded;
    private int mPositionMinSideMargin;
    private final PowerManager mPowerManager;
    private boolean mPulsing;
    private QS mQs;
    private boolean mQsAnimatorExpand;
    private boolean mQsExpandImmediate;
    private boolean mQsExpanded;
    private boolean mQsExpandedWhenExpandingStarted;
    private ValueAnimator mQsExpansionAnimator;
    protected boolean mQsExpansionEnabled;
    private boolean mQsExpansionFromOverscroll;
    protected float mQsExpansionHeight;
    private int mQsFalsingThreshold;
    private FrameLayout mQsFrame;
    private boolean mQsFullyExpanded;
    protected int mQsMaxExpansionHeight;
    protected int mQsMinExpansionHeight;
    private View mQsNavbarScrim;
    private int mQsNotificationTopPadding;
    private int mQsPeekHeight;
    private boolean mQsScrimEnabled;
    private ValueAnimator mQsSizeChangeAnimator;
    private boolean mQsTouchAboveFalsingThreshold;
    private boolean mQsTracking;
    private VelocityTracker mQsVelocityTracker;
    private boolean mShowEmptyShadeView;
    private boolean mShowIconsWhenExpanded;
    private int mStackScrollerMeasuringPass;
    private boolean mStackScrollerOverscrolling;
    private final ValueAnimator.AnimatorUpdateListener mStatusBarAnimateAlphaListener;
    private int mStatusBarMinHeight;
    protected int mStatusBarState;
    private ArrayList<Consumer<ExpandableNotificationRow>> mTrackingHeadsUpListeners;
    private int mTrackingPointer;
    private boolean mTwoFingerQsExpandPossible;
    private boolean mUnlockIconActive;
    private int mUnlockMoveDistance;
    private boolean mUserSetupComplete;
    private ArrayList<Runnable> mVerticalTranslationListener;
    private static final Rect mDummyDirtyRect = new Rect(0, 0, 1, 1);
    private static final AnimationProperties CLOCK_ANIMATION_PROPERTIES = new AnimationProperties().setDuration(360);
    private static final FloatProperty<NotificationPanelView> SET_DARK_AMOUNT_PROPERTY = new FloatProperty<NotificationPanelView>("mDarkAmount") { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.1
        @Override // android.util.FloatProperty
        public void setValue(NotificationPanelView object, float value) {
            object.setDarkAmount(value);
        }

        @Override // android.util.Property
        public Float get(NotificationPanelView object) {
            return Float.valueOf(object.mDarkAmount);
        }
    };

    public NotificationPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mQsExpansionEnabled = true;
        this.mClockPositionAlgorithm = new KeyguardClockPositionAlgorithm();
        this.mClockPositionResult = new KeyguardClockPositionAlgorithm.Result();
        this.mQsScrimEnabled = true;
        this.mKeyguardStatusBarAnimateAlpha = 1.0f;
        this.mLastOrientation = -1;
        this.mLastCameraLaunchSource = "lockscreen_affordance";
        this.mHeadsUpExistenceChangedRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.2
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.setHeadsUpAnimatingAway(false);
                NotificationPanelView.this.notifyBarPanelExpansionChanged();
            }
        };
        this.mLockscreenGestureLogger = new LockscreenGestureLogger();
        this.mNoVisibleNotifications = true;
        this.mHideIconsDuringNotificationLaunch = true;
        this.mTrackingHeadsUpListeners = new ArrayList<>();
        this.mVerticalTranslationListener = new ArrayList<>();
        this.mAlphaPaint = new Paint();
        this.mAnimatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (NotificationPanelView.this.mPanelAlphaEndAction != null) {
                    NotificationPanelView.this.mPanelAlphaEndAction.run();
                }
            }
        };
        this.PANEL_ALPHA = AnimatableProperty.from("panelAlpha", new BiConsumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$aKsp0zdf_wKFZXD1TonJ2cFEsN4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((NotificationPanelView) obj).setPanelAlphaInternal(((Float) obj2).floatValue());
            }
        }, new Function() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$SmdYpsZqQm1fpR9OgK3SiEL3pJQ
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((NotificationPanelView) obj).getCurrentPanelAlpha());
            }
        }, R.id.panel_alpha_animator_tag, R.id.panel_alpha_animator_start_tag, R.id.panel_alpha_animator_end_tag);
        this.PANEL_ALPHA_OUT_PROPERTIES = new AnimationProperties().setDuration(150L).setCustomInterpolator(this.PANEL_ALPHA.getProperty(), Interpolators.ALPHA_OUT);
        this.PANEL_ALPHA_IN_PROPERTIES = new AnimationProperties().setDuration(200L).setAnimationFinishListener(this.mAnimatorListenerAdapter).setCustomInterpolator(this.PANEL_ALPHA.getProperty(), Interpolators.ALPHA_IN);
        this.mAnimateKeyguardStatusViewInvisibleEndRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.7
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mKeyguardStatusViewAnimating = false;
                NotificationPanelView.this.mKeyguardStatusView.setVisibility(4);
            }
        };
        this.mAnimateKeyguardStatusViewGoneEndRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.8
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mKeyguardStatusViewAnimating = false;
                NotificationPanelView.this.mKeyguardStatusView.setVisibility(8);
            }
        };
        this.mAnimateKeyguardStatusViewVisibleEndRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.9
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mKeyguardStatusViewAnimating = false;
            }
        };
        this.mAnimateKeyguardStatusBarInvisibleEndRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.10
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mKeyguardStatusBar.setVisibility(4);
                NotificationPanelView.this.mKeyguardStatusBar.setAlpha(1.0f);
                NotificationPanelView.this.mKeyguardStatusBarAnimateAlpha = 1.0f;
            }
        };
        this.mStatusBarAnimateAlphaListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.12
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                NotificationPanelView.this.mKeyguardStatusBarAnimateAlpha = ((Float) animation.getAnimatedValue()).floatValue();
                NotificationPanelView.this.updateHeaderKeyguardAlpha();
            }
        };
        this.mAnimateKeyguardBottomAreaInvisibleEndRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.13
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mKeyguardBottomArea.setVisibility(8);
            }
        };
        this.mFragmentListener = new AnonymousClass22();
        setWillNotDraw(true);
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        this.mAlphaPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        setPanelAlpha(255, false);
    }

    public void setStatusBar(StatusBar bar) {
        this.mStatusBar = bar;
        this.mKeyguardBottomArea.setStatusBar(this.mStatusBar);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mKeyguardStatusBar = (KeyguardStatusBarView) findViewById(R.id.keyguard_header);
        this.mKeyguardStatusView = (KeyguardStatusView) findViewById(R.id.keyguard_status_view);
        this.mNotificationContainerParent = (NotificationsQuickSettingsContainer) findViewById(R.id.notification_container_parent);
        this.mNotificationStackScroller = (NotificationStackScrollLayout) findViewById(R.id.notification_stack_scroller);
        this.mNotificationStackScroller.setOnHeightChangedListener(this);
        this.mNotificationStackScroller.setOverscrollTopChangedListener(this);
        this.mNotificationStackScroller.setOnEmptySpaceClickListener(this);
        final NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScroller;
        Objects.requireNonNull(notificationStackScrollLayout);
        addTrackingHeadsUpListener(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$xhbxghYggVvH-Fewrs8cwC_bnGY
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                notificationStackScrollLayout.setTrackingHeadsUp((ExpandableNotificationRow) obj);
            }
        });
        this.mKeyguardBottomArea = (KeyguardBottomAreaView) findViewById(R.id.keyguard_bottom_area);
        this.mQsNavbarScrim = findViewById(R.id.qs_navbar_scrim);
        this.mLastOrientation = getResources().getConfiguration().orientation;
        initBottomArea();
        this.mQsFrame = (FrameLayout) findViewById(R.id.qs_frame);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        FragmentHostManager.get(this).addTagListener(QS.TAG, this.mFragmentListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        FragmentHostManager.get(this).removeTagListener(QS.TAG, this.mFragmentListener);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void loadDimens() {
        super.loadDimens();
        this.mFlingAnimationUtils = new FlingAnimationUtils(getContext(), 0.4f);
        this.mStatusBarMinHeight = getResources().getDimensionPixelSize(android.R.dimen.handwriting_bounds_offset_right);
        this.mQsPeekHeight = getResources().getDimensionPixelSize(R.dimen.qs_peek_height);
        this.mNotificationsHeaderCollideDistance = getResources().getDimensionPixelSize(R.dimen.header_notifications_collide_distance);
        this.mUnlockMoveDistance = getResources().getDimensionPixelOffset(R.dimen.unlock_move_distance);
        this.mClockPositionAlgorithm.loadDimens(getResources());
        this.mQsFalsingThreshold = getResources().getDimensionPixelSize(R.dimen.qs_falsing_threshold);
        this.mPositionMinSideMargin = getResources().getDimensionPixelSize(R.dimen.notification_panel_min_side_margin);
        this.mMaxFadeoutHeight = getResources().getDimensionPixelSize(R.dimen.max_notification_fadeout_height);
        this.mIndicationBottomPadding = getResources().getDimensionPixelSize(R.dimen.keyguard_indication_bottom_padding);
        this.mQsNotificationTopPadding = getResources().getDimensionPixelSize(R.dimen.qs_notification_padding);
    }

    public void updateResources() {
        Resources res = getResources();
        int qsWidth = res.getDimensionPixelSize(R.dimen.qs_panel_width);
        int panelGravity = getResources().getInteger(R.integer.notification_panel_layout_gravity);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.mQsFrame.getLayoutParams();
        if (lp.width != qsWidth || lp.gravity != panelGravity) {
            lp.width = qsWidth;
            lp.gravity = panelGravity;
            this.mQsFrame.setLayoutParams(lp);
        }
        int panelWidth = res.getDimensionPixelSize(R.dimen.notification_panel_width);
        FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) this.mNotificationStackScroller.getLayoutParams();
        if (lp2.width != panelWidth || lp2.gravity != panelGravity) {
            lp2.width = panelWidth;
            lp2.gravity = panelGravity;
            this.mNotificationStackScroller.setLayoutParams(lp2);
        }
    }

    public void onThemeChanged() {
        int index = indexOfChild(this.mKeyguardStatusView);
        removeView(this.mKeyguardStatusView);
        this.mKeyguardStatusView = (KeyguardStatusView) LayoutInflater.from(this.mContext).inflate(R.layout.keyguard_status_view, (ViewGroup) this, false);
        addView(this.mKeyguardStatusView, index);
        int index2 = indexOfChild(this.mKeyguardBottomArea);
        removeView(this.mKeyguardBottomArea);
        this.mKeyguardBottomArea = (KeyguardBottomAreaView) LayoutInflater.from(this.mContext).inflate(R.layout.keyguard_bottom_area, (ViewGroup) this, false);
        addView(this.mKeyguardBottomArea, index2);
        initBottomArea();
        setDarkAmount(this.mDarkAmount);
        setKeyguardStatusViewVisibility(this.mStatusBarState, false, false);
        setKeyguardBottomAreaVisibility(this.mStatusBarState, false);
    }

    private void initBottomArea() {
        this.mAffordanceHelper = new KeyguardAffordanceHelper(this, getContext());
        this.mKeyguardBottomArea.setAffordanceHelper(this.mAffordanceHelper);
        this.mKeyguardBottomArea.setStatusBar(this.mStatusBar);
        this.mKeyguardBottomArea.setUserSetupComplete(this.mUserSetupComplete);
    }

    public void setKeyguardIndicationController(KeyguardIndicationController indicationController) {
        this.mKeyguardBottomArea.setKeyguardIndicationController(indicationController);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setIsFullWidth(this.mNotificationStackScroller.getWidth() == getWidth());
        this.mKeyguardStatusView.setPivotX(getWidth() / 2);
        this.mKeyguardStatusView.setPivotY(0.34521484f * this.mKeyguardStatusView.getClockTextSize());
        int oldMaxHeight = this.mQsMaxExpansionHeight;
        if (this.mQs != null) {
            this.mQsMinExpansionHeight = this.mKeyguardShowing ? 0 : this.mQs.getQsMinExpansionHeight();
            this.mQsMaxExpansionHeight = this.mQs.getDesiredHeight();
            this.mNotificationStackScroller.setMaxTopPadding(this.mQsMaxExpansionHeight + this.mQsNotificationTopPadding);
        }
        positionClockAndNotifications();
        if (this.mQsExpanded && this.mQsFullyExpanded) {
            this.mQsExpansionHeight = this.mQsMaxExpansionHeight;
            requestScrollerTopPaddingUpdate(false);
            requestPanelHeightUpdate();
            if (this.mQsMaxExpansionHeight != oldMaxHeight) {
                startQsSizeChangeAnimation(oldMaxHeight, this.mQsMaxExpansionHeight);
            }
        } else if (!this.mQsExpanded) {
            setQsExpansion(this.mQsMinExpansionHeight + this.mLastOverscroll);
        }
        updateExpandedHeight(getExpandedHeight());
        updateHeader();
        if (this.mQsSizeChangeAnimator == null && this.mQs != null) {
            this.mQs.setHeightOverride(this.mQs.getDesiredHeight());
        }
        updateMaxHeadsUpTranslation();
    }

    private void setIsFullWidth(boolean isFullWidth) {
        this.mIsFullWidth = isFullWidth;
        this.mNotificationStackScroller.setIsFullWidth(isFullWidth);
    }

    private void startQsSizeChangeAnimation(int oldHeight, int newHeight) {
        if (this.mQsSizeChangeAnimator != null) {
            oldHeight = ((Integer) this.mQsSizeChangeAnimator.getAnimatedValue()).intValue();
            this.mQsSizeChangeAnimator.cancel();
        }
        this.mQsSizeChangeAnimator = ValueAnimator.ofInt(oldHeight, newHeight);
        this.mQsSizeChangeAnimator.setDuration(300L);
        this.mQsSizeChangeAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mQsSizeChangeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                NotificationPanelView.this.requestScrollerTopPaddingUpdate(false);
                NotificationPanelView.this.requestPanelHeightUpdate();
                int height = ((Integer) NotificationPanelView.this.mQsSizeChangeAnimator.getAnimatedValue()).intValue();
                NotificationPanelView.this.mQs.setHeightOverride(height);
            }
        });
        this.mQsSizeChangeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationPanelView.this.mQsSizeChangeAnimator = null;
            }
        });
        this.mQsSizeChangeAnimator.start();
    }

    private void positionClockAndNotifications() {
        int totalHeight;
        boolean animate = this.mNotificationStackScroller.isAddOrRemoveAnimationPending();
        boolean animateClock = animate || this.mAnimateNextPositionUpdate;
        if (this.mStatusBarState != 1) {
            totalHeight = (this.mQs != null ? this.mQs.getHeader().getHeight() : 0) + this.mQsPeekHeight + this.mQsNotificationTopPadding;
        } else {
            int totalHeight2 = getHeight();
            int bottomPadding = Math.max(this.mIndicationBottomPadding, this.mAmbientIndicationBottomPadding);
            this.mClockPositionAlgorithm.setup(this.mStatusBarMinHeight, totalHeight2 - bottomPadding, this.mNotificationStackScroller.getIntrinsicContentHeight(), getExpandedFraction(), totalHeight2, this.mKeyguardStatusView.getHeight(), this.mDarkAmount, this.mStatusBar.isKeyguardCurrentlySecure(), this.mPulsing, this.mBouncerTop);
            this.mClockPositionAlgorithm.run(this.mClockPositionResult);
            PropertyAnimator.setProperty(this.mKeyguardStatusView, AnimatableProperty.X, this.mClockPositionResult.clockX, CLOCK_ANIMATION_PROPERTIES, animateClock);
            PropertyAnimator.setProperty(this.mKeyguardStatusView, AnimatableProperty.Y, this.mClockPositionResult.clockY, CLOCK_ANIMATION_PROPERTIES, animateClock);
            updateClock();
            totalHeight = this.mClockPositionResult.stackScrollerPadding;
        }
        this.mNotificationStackScroller.setIntrinsicPadding(totalHeight);
        this.mNotificationStackScroller.setAntiBurnInOffsetX(this.mClockPositionResult.clockX);
        this.mKeyguardBottomArea.setBurnInXOffset(this.mClockPositionResult.clockX);
        this.mStackScrollerMeasuringPass++;
        requestScrollerTopPaddingUpdate(animate);
        this.mStackScrollerMeasuringPass = 0;
        this.mAnimateNextPositionUpdate = false;
    }

    public int computeMaxKeyguardNotifications(int maximum) {
        float minPadding = this.mClockPositionAlgorithm.getMinStackScrollerPadding();
        int notificationPadding = Math.max(1, getResources().getDimensionPixelSize(R.dimen.notification_divider_height));
        NotificationShelf shelf = this.mNotificationStackScroller.getNotificationShelf();
        float shelfSize = shelf.getVisibility() == 8 ? 0.0f : shelf.getIntrinsicHeight() + notificationPadding;
        float availableSpace = (((this.mNotificationStackScroller.getHeight() - minPadding) - shelfSize) - Math.max(this.mIndicationBottomPadding, this.mAmbientIndicationBottomPadding)) - this.mKeyguardStatusView.getLogoutButtonHeight();
        int count = 0;
        for (int i = 0; i < this.mNotificationStackScroller.getChildCount(); i++) {
            ExpandableView child = (ExpandableView) this.mNotificationStackScroller.getChildAt(i);
            if (child instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) child;
                boolean suppressedSummary = this.mGroupManager.isSummaryOfSuppressedGroup(row.getStatusBarNotification());
                if (!suppressedSummary && this.mStatusBar.getNotificationLockscreenUserManager().shouldShowOnKeyguard(row.getStatusBarNotification()) && !row.isRemoved()) {
                    availableSpace -= child.getMinHeight(true) + notificationPadding;
                    if (availableSpace < 0.0f || count >= maximum) {
                        if (availableSpace > (-shelfSize)) {
                            for (int j = i + 1; j < this.mNotificationStackScroller.getChildCount(); j++) {
                                if (this.mNotificationStackScroller.getChildAt(j) instanceof ExpandableNotificationRow) {
                                    return count;
                                }
                            }
                            return count + 1;
                        }
                        return count;
                    }
                    count++;
                }
            }
        }
        return count;
    }

    public void setBouncerTop(int bouncerTop) {
        this.mBouncerTop = bouncerTop;
        positionClockAndNotifications();
    }

    private void updateClock() {
        if (!this.mKeyguardStatusViewAnimating) {
            this.mKeyguardStatusView.setAlpha(this.mClockPositionResult.clockAlpha);
        }
    }

    public void animateToFullShade(long delay) {
        this.mNotificationStackScroller.goToFullShade(delay);
        requestLayout();
        this.mAnimateNextPositionUpdate = true;
    }

    public void setQsExpansionEnabled(boolean qsExpansionEnabled) {
        this.mQsExpansionEnabled = qsExpansionEnabled;
        if (this.mQs == null) {
            return;
        }
        this.mQs.setHeaderClickable(qsExpansionEnabled);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    public void resetViews() {
        this.mIsLaunchTransitionFinished = false;
        this.mBlockTouches = false;
        this.mUnlockIconActive = false;
        if (!this.mLaunchingAffordance) {
            this.mAffordanceHelper.reset(false);
            this.mLastCameraLaunchSource = "lockscreen_affordance";
        }
        closeQs();
        this.mStatusBar.getGutsManager().closeAndSaveGuts(true, true, true, -1, -1, true);
        this.mNotificationStackScroller.setOverScrollAmount(0.0f, true, false, true);
        this.mNotificationStackScroller.resetScrollPosition();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    public void collapse(boolean delayed, float speedUpFactor) {
        if (!canPanelBeCollapsed()) {
            return;
        }
        if (this.mQsExpanded) {
            this.mQsExpandImmediate = true;
            this.mNotificationStackScroller.setShouldShowShelfOnly(true);
        }
        super.collapse(delayed, speedUpFactor);
    }

    public void closeQs() {
        cancelQsAnimation();
        setQsExpansion(this.mQsMinExpansionHeight);
    }

    public void animateCloseQs() {
        if (this.mQsExpansionAnimator != null) {
            if (!this.mQsAnimatorExpand) {
                return;
            }
            float height = this.mQsExpansionHeight;
            this.mQsExpansionAnimator.cancel();
            setQsExpansion(height);
        }
        flingSettings(0.0f, false);
    }

    public void expandWithQs() {
        if (this.mQsExpansionEnabled) {
            this.mQsExpandImmediate = true;
            this.mNotificationStackScroller.setShouldShowShelfOnly(true);
        }
        expand(true);
    }

    public void expandWithoutQs() {
        if (isQsExpanded()) {
            flingSettings(0.0f, false);
        } else {
            expand(true);
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    public void fling(float vel, boolean expand) {
        GestureRecorder gr = ((PhoneStatusBarView) this.mBar).mBar.getGestureRecorder();
        if (gr != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("fling ");
            sb.append(vel > 0.0f ? "open" : "closed");
            gr.tag(sb.toString(), "notifications,v=" + vel);
        }
        super.fling(vel, expand);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void flingToHeight(float vel, boolean expand, float target, float collapseSpeedUpFactor, boolean expandBecauseOfFalsing) {
        this.mHeadsUpTouchHelper.notifyFling(!expand);
        setClosingWithAlphaFadeout(!expand && getFadeoutAlpha() == 1.0f);
        super.flingToHeight(vel, expand, target, collapseSpeedUpFactor, expandBecauseOfFalsing);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mBlockTouches) {
            return false;
        }
        if (this.mQsFullyExpanded && this.mQs.onInterceptTouchEvent(event)) {
            return false;
        }
        initDownStates(event);
        if (this.mBar.panelEnabled() && this.mHeadsUpTouchHelper.onInterceptTouchEvent(event)) {
            this.mIsExpansionFromHeadsUp = true;
            MetricsLogger.count(this.mContext, "panel_open", 1);
            MetricsLogger.count(this.mContext, "panel_open_peek", 1);
            return true;
        }
        if (isFullyCollapsed() || !onQsIntercept(event)) {
            return super.onInterceptTouchEvent(event);
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean onQsIntercept(MotionEvent motionEvent) {
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
        if (iFindPointerIndex < 0) {
            iFindPointerIndex = 0;
            this.mTrackingPointer = motionEvent.getPointerId(0);
        }
        float x = motionEvent.getX(iFindPointerIndex);
        float y = motionEvent.getY(iFindPointerIndex);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    this.mIntercepting = true;
                    this.mInitialTouchY = y;
                    this.mInitialTouchX = x;
                    initVelocityTracker();
                    trackMovement(motionEvent);
                    if (shouldQuickSettingsIntercept(this.mInitialTouchX, this.mInitialTouchY, 0.0f)) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    if (this.mQsExpansionAnimator != null) {
                        onQsExpansionStarted();
                        this.mInitialHeightOnTouch = this.mQsExpansionHeight;
                        this.mQsTracking = true;
                        this.mIntercepting = false;
                        this.mNotificationStackScroller.cancelLongPress();
                    }
                    break;
                case 1:
                case 3:
                    trackMovement(motionEvent);
                    if (this.mQsTracking) {
                        flingQsWithCurrentVelocity(y, motionEvent.getActionMasked() == 3);
                        this.mQsTracking = false;
                    }
                    this.mIntercepting = false;
                    break;
                case 2:
                    float f = y - this.mInitialTouchY;
                    trackMovement(motionEvent);
                    if (this.mQsTracking) {
                        setQsExpansion(this.mInitialHeightOnTouch + f);
                        trackMovement(motionEvent);
                        this.mIntercepting = false;
                        return true;
                    }
                    if (Math.abs(f) > this.mTouchSlop && Math.abs(f) > Math.abs(x - this.mInitialTouchX) && shouldQuickSettingsIntercept(this.mInitialTouchX, this.mInitialTouchY, f)) {
                        this.mQsTracking = true;
                        onQsExpansionStarted();
                        notifyExpandingFinished();
                        this.mInitialHeightOnTouch = this.mQsExpansionHeight;
                        this.mInitialTouchY = y;
                        this.mInitialTouchX = x;
                        this.mIntercepting = false;
                        this.mNotificationStackScroller.cancelLongPress();
                        return true;
                    }
                    break;
            }
        } else {
            int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
            if (this.mTrackingPointer == pointerId) {
                boolean z = motionEvent.getPointerId(0) == pointerId;
                this.mTrackingPointer = motionEvent.getPointerId(z ? 1 : 0);
                this.mInitialTouchX = motionEvent.getX(z ? 1 : 0);
                this.mInitialTouchY = motionEvent.getY(z ? 1 : 0);
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean isInContentBounds(float x, float y) {
        float stackScrollerX = this.mNotificationStackScroller.getX();
        return !this.mNotificationStackScroller.isBelowLastNotification(x - stackScrollerX, y) && stackScrollerX < x && x < ((float) this.mNotificationStackScroller.getWidth()) + stackScrollerX;
    }

    private void initDownStates(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            boolean z = false;
            this.mOnlyAffordanceInThisMotion = false;
            this.mQsTouchAboveFalsingThreshold = this.mQsFullyExpanded;
            this.mDozingOnDown = isDozing();
            this.mCollapsedOnDown = isFullyCollapsed();
            if (this.mCollapsedOnDown && this.mHeadsUpManager.hasPinnedHeadsUp()) {
                z = true;
            }
            this.mListenForHeadsUp = z;
        }
    }

    private void flingQsWithCurrentVelocity(float y, boolean isCancelMotionEvent) {
        float vel = getCurrentQSVelocity();
        boolean expandsQs = flingExpandsQs(vel);
        if (expandsQs) {
            logQsSwipeDown(y);
        }
        flingSettings(vel, expandsQs && !isCancelMotionEvent);
    }

    private void logQsSwipeDown(float y) {
        int gesture;
        float vel = getCurrentQSVelocity();
        if (this.mStatusBarState == 1) {
            gesture = 193;
        } else {
            gesture = 194;
        }
        this.mLockscreenGestureLogger.write(gesture, (int) ((y - this.mInitialTouchY) / this.mStatusBar.getDisplayDensity()), (int) (vel / this.mStatusBar.getDisplayDensity()));
    }

    private boolean flingExpandsQs(float vel) {
        if (isFalseTouch()) {
            return false;
        }
        if (Math.abs(vel) < this.mFlingAnimationUtils.getMinVelocityPxPerSecond()) {
            return getQsExpansionFraction() > 0.5f;
        }
        return vel > 0.0f;
    }

    private boolean isFalseTouch() {
        if (!needsAntiFalsing()) {
            return false;
        }
        if (this.mFalsingManager.isClassiferEnabled()) {
            return this.mFalsingManager.isFalseTouch();
        }
        return !this.mQsTouchAboveFalsingThreshold;
    }

    private float getQsExpansionFraction() {
        return Math.min(1.0f, (this.mQsExpansionHeight - this.mQsMinExpansionHeight) / (this.mQsMaxExpansionHeight - this.mQsMinExpansionHeight));
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected float getOpeningHeight() {
        return this.mNotificationStackScroller.getOpeningHeight();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        ContentResolver cr = this.mContext.getContentResolver();
        String pin = Settings.System.getString(cr, "lockscreen_unlock_pin");
        if (pin != null && !pin.isEmpty() && this.mStatusBar.getBarState() == 1) {
            Log.d(TAG, "disable touch, is keyguard state");
            return false;
        }
        if (this.mBlockTouches || (this.mQs != null && this.mQs.isCustomizing())) {
            return false;
        }
        initDownStates(event);
        if (this.mListenForHeadsUp && !this.mHeadsUpTouchHelper.isTrackingHeadsUp() && this.mHeadsUpTouchHelper.onInterceptTouchEvent(event)) {
            this.mIsExpansionFromHeadsUp = true;
            MetricsLogger.count(this.mContext, "panel_open_peek", 1);
        }
        boolean handled = false;
        if ((!this.mIsExpanding || this.mHintAnimationRunning) && !this.mQsExpanded && this.mStatusBar.getBarState() != 0 && !this.mDozing) {
            handled = false | this.mAffordanceHelper.onTouchEvent(event);
        }
        if (this.mOnlyAffordanceInThisMotion) {
            return true;
        }
        boolean handled2 = handled | this.mHeadsUpTouchHelper.onTouchEvent(event);
        if (!this.mHeadsUpTouchHelper.isTrackingHeadsUp() && handleQsTouch(event)) {
            return true;
        }
        if (event.getActionMasked() == 0 && isFullyCollapsed()) {
            MetricsLogger.count(this.mContext, "panel_open", 1);
            updateVerticalPanelPosition(event.getX());
            handled2 = true;
        }
        boolean handled3 = handled2 | super.onTouchEvent(event);
        if (this.mDozing) {
            return handled3;
        }
        return true;
    }

    private boolean handleQsTouch(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == 0 && getExpandedFraction() == 1.0f && this.mStatusBar.getBarState() != 1 && !this.mQsExpanded && this.mQsExpansionEnabled) {
            this.mQsTracking = true;
            this.mConflictingQsExpansionGesture = true;
            onQsExpansionStarted();
            this.mInitialHeightOnTouch = this.mQsExpansionHeight;
            this.mInitialTouchY = event.getX();
            this.mInitialTouchX = event.getY();
        }
        if (!isFullyCollapsed()) {
            handleQsDown(event);
        }
        if (!this.mQsExpandImmediate && this.mQsTracking) {
            onQsTouch(event);
            if (!this.mConflictingQsExpansionGesture) {
                return true;
            }
        }
        if (action == 3 || action == 1) {
            this.mConflictingQsExpansionGesture = false;
        }
        if (action == 0 && isFullyCollapsed() && this.mQsExpansionEnabled) {
            this.mTwoFingerQsExpandPossible = true;
        }
        if (this.mTwoFingerQsExpandPossible && isOpenQsEvent(event) && event.getY(event.getActionIndex()) < this.mStatusBarMinHeight) {
            MetricsLogger.count(this.mContext, "panel_open_qs", 1);
            this.mQsExpandImmediate = true;
            this.mNotificationStackScroller.setShouldShowShelfOnly(true);
            requestPanelHeightUpdate();
            setListening(true);
        }
        return false;
    }

    private boolean isInQsArea(float x, float y) {
        return x >= this.mQsFrame.getX() && x <= this.mQsFrame.getX() + ((float) this.mQsFrame.getWidth()) && (y <= this.mNotificationStackScroller.getBottomMostNotificationBottom() || y <= this.mQs.getView().getY() + ((float) this.mQs.getView().getHeight()));
    }

    private boolean isOpenQsEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        int action = event.getActionMasked();
        boolean twoFingerDrag = action == 5 && pointerCount == 2;
        boolean stylusButtonClickDrag = action == 0 && (event.isButtonPressed(32) || event.isButtonPressed(64));
        boolean mouseButtonClickDrag = action == 0 && (event.isButtonPressed(2) || event.isButtonPressed(4));
        return twoFingerDrag || stylusButtonClickDrag || mouseButtonClickDrag;
    }

    private void handleQsDown(MotionEvent event) {
        if (event.getActionMasked() == 0 && shouldQuickSettingsIntercept(event.getX(), event.getY(), -1.0f)) {
            this.mFalsingManager.onQsDown();
            this.mQsTracking = true;
            onQsExpansionStarted();
            this.mInitialHeightOnTouch = this.mQsExpansionHeight;
            this.mInitialTouchY = event.getX();
            this.mInitialTouchX = event.getY();
            notifyExpandingFinished();
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean flingExpands(float vel, float vectorVel, float x, float y) {
        boolean expands = super.flingExpands(vel, vectorVel, x, y);
        if (this.mQsExpansionAnimator != null) {
            return true;
        }
        return expands;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean hasConflictingGestures() {
        return this.mStatusBar.getBarState() != 0;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean shouldGestureIgnoreXTouchSlop(float x, float y) {
        return !this.mAffordanceHelper.isOnAffordanceIcon(x, y);
    }

    private void onQsTouch(MotionEvent motionEvent) {
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
        if (iFindPointerIndex < 0) {
            iFindPointerIndex = 0;
            this.mTrackingPointer = motionEvent.getPointerId(0);
        }
        float y = motionEvent.getY(iFindPointerIndex);
        float x = motionEvent.getX(iFindPointerIndex);
        float f = y - this.mInitialTouchY;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    this.mQsTracking = true;
                    this.mInitialTouchY = y;
                    this.mInitialTouchX = x;
                    onQsExpansionStarted();
                    this.mInitialHeightOnTouch = this.mQsExpansionHeight;
                    initVelocityTracker();
                    trackMovement(motionEvent);
                    break;
                case 1:
                case 3:
                    this.mQsTracking = false;
                    this.mTrackingPointer = -1;
                    trackMovement(motionEvent);
                    if (getQsExpansionFraction() != 0.0f || y >= this.mInitialTouchY) {
                        flingQsWithCurrentVelocity(y, motionEvent.getActionMasked() == 3);
                    }
                    if (this.mQsVelocityTracker != null) {
                        this.mQsVelocityTracker.recycle();
                        this.mQsVelocityTracker = null;
                    }
                    break;
                case 2:
                    setQsExpansion(this.mInitialHeightOnTouch + f);
                    if (f >= getFalsingThreshold()) {
                        this.mQsTouchAboveFalsingThreshold = true;
                    }
                    trackMovement(motionEvent);
                    break;
            }
        }
        int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
        if (this.mTrackingPointer == pointerId) {
            boolean z = motionEvent.getPointerId(0) == pointerId;
            float y2 = motionEvent.getY(z ? 1 : 0);
            float x2 = motionEvent.getX(z ? 1 : 0);
            this.mTrackingPointer = motionEvent.getPointerId(z ? 1 : 0);
            this.mInitialHeightOnTouch = this.mQsExpansionHeight;
            this.mInitialTouchY = y2;
            this.mInitialTouchX = x2;
        }
    }

    private int getFalsingThreshold() {
        float factor = this.mStatusBar.isWakeUpComingFromTouch() ? 1.5f : 1.0f;
        return (int) (this.mQsFalsingThreshold * factor);
    }

    @Override // com.android.systemui.statusbar.stack.NotificationStackScrollLayout.OnOverscrollTopChangedListener
    public void onOverscrollTopChanged(float amount, boolean isRubberbanded) {
        cancelQsAnimation();
        if (!this.mQsExpansionEnabled) {
            amount = 0.0f;
        }
        float rounded = amount >= 1.0f ? amount : 0.0f;
        setOverScrolling(rounded != 0.0f && isRubberbanded);
        this.mQsExpansionFromOverscroll = rounded != 0.0f;
        this.mLastOverscroll = rounded;
        updateQsState();
        setQsExpansion(this.mQsMinExpansionHeight + rounded);
    }

    @Override // com.android.systemui.statusbar.stack.NotificationStackScrollLayout.OnOverscrollTopChangedListener
    public void flingTopOverscroll(float velocity, boolean open) {
        this.mLastOverscroll = 0.0f;
        this.mQsExpansionFromOverscroll = false;
        setQsExpansion(this.mQsExpansionHeight);
        flingSettings((this.mQsExpansionEnabled || !open) ? velocity : 0.0f, open && this.mQsExpansionEnabled, new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.6
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mStackScrollerOverscrolling = false;
                NotificationPanelView.this.setOverScrolling(false);
                NotificationPanelView.this.updateQsState();
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOverScrolling(boolean overscrolling) {
        this.mStackScrollerOverscrolling = overscrolling;
        if (this.mQs == null) {
            return;
        }
        this.mQs.setOverscrolling(overscrolling);
    }

    private void onQsExpansionStarted() {
        onQsExpansionStarted(0);
    }

    protected void onQsExpansionStarted(int overscrollAmount) {
        cancelQsAnimation();
        cancelHeightAnimator();
        float height = this.mQsExpansionHeight - overscrollAmount;
        setQsExpansion(height);
        requestPanelHeightUpdate();
        this.mNotificationStackScroller.checkSnoozeLeavebehind();
    }

    private void setQsExpanded(boolean expanded) {
        boolean changed = this.mQsExpanded != expanded;
        if (changed) {
            this.mQsExpanded = expanded;
            updateQsState();
            requestPanelHeightUpdate();
            this.mFalsingManager.setQsExpanded(expanded);
            this.mStatusBar.setQsExpanded(expanded);
            this.mNotificationContainerParent.setQsExpanded(expanded);
        }
    }

    public void setBarState(int statusBarState, boolean keyguardFadingAway, boolean goingToFullShade) {
        int oldState = this.mStatusBarState;
        boolean keyguardShowing = statusBarState == 1;
        setKeyguardStatusViewVisibility(statusBarState, keyguardFadingAway, goingToFullShade);
        setKeyguardBottomAreaVisibility(statusBarState, goingToFullShade);
        this.mStatusBarState = statusBarState;
        this.mKeyguardShowing = keyguardShowing;
        if (this.mQs != null) {
            this.mQs.setKeyguardShowing(this.mKeyguardShowing);
        }
        if (oldState != 1 || (!goingToFullShade && statusBarState != 2)) {
            if (oldState == 2 && statusBarState == 1) {
                animateKeyguardStatusBarIn(360L);
                this.mQs.animateHeaderSlidingOut();
            } else {
                this.mKeyguardStatusBar.setAlpha(1.0f);
                this.mKeyguardStatusBar.setVisibility(keyguardShowing ? 0 : 4);
                if (keyguardShowing && oldState != this.mStatusBarState) {
                    this.mKeyguardBottomArea.onKeyguardShowingChanged();
                    if (this.mQs != null) {
                        this.mQs.hideImmediately();
                    }
                }
            }
        } else {
            animateKeyguardStatusBarOut();
            long delay = this.mStatusBarState == 2 ? 0L : this.mStatusBar.calculateGoingToFullShadeDelay();
            this.mQs.animateHeaderSlidingIn(delay);
        }
        if (keyguardShowing) {
            updateDozingVisibilities(false);
        }
        resetVerticalPanelPosition();
        updateQsState();
    }

    private void animateKeyguardStatusBarOut() {
        long keyguardFadingAwayDelay;
        long keyguardFadingAwayDuration;
        ValueAnimator anim = ValueAnimator.ofFloat(this.mKeyguardStatusBar.getAlpha(), 0.0f);
        anim.addUpdateListener(this.mStatusBarAnimateAlphaListener);
        if (this.mStatusBar.isKeyguardFadingAway()) {
            keyguardFadingAwayDelay = this.mStatusBar.getKeyguardFadingAwayDelay();
        } else {
            keyguardFadingAwayDelay = 0;
        }
        anim.setStartDelay(keyguardFadingAwayDelay);
        if (this.mStatusBar.isKeyguardFadingAway()) {
            keyguardFadingAwayDuration = this.mStatusBar.getKeyguardFadingAwayDuration() / 2;
        } else {
            keyguardFadingAwayDuration = 360;
        }
        anim.setDuration(keyguardFadingAwayDuration);
        anim.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.11
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationPanelView.this.mAnimateKeyguardStatusBarInvisibleEndRunnable.run();
            }
        });
        anim.start();
    }

    private void animateKeyguardStatusBarIn(long duration) {
        this.mKeyguardStatusBar.setVisibility(0);
        this.mKeyguardStatusBar.setAlpha(0.0f);
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.addUpdateListener(this.mStatusBarAnimateAlphaListener);
        anim.setDuration(duration);
        anim.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        anim.start();
    }

    private void setKeyguardBottomAreaVisibility(int statusBarState, boolean goingToFullShade) {
        this.mKeyguardBottomArea.animate().cancel();
        if (goingToFullShade) {
            this.mKeyguardBottomArea.animate().alpha(0.0f).setStartDelay(this.mStatusBar.getKeyguardFadingAwayDelay()).setDuration(this.mStatusBar.getKeyguardFadingAwayDuration() / 2).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(this.mAnimateKeyguardBottomAreaInvisibleEndRunnable).start();
            return;
        }
        if (statusBarState == 1 || statusBarState == 2) {
            this.mKeyguardBottomArea.setVisibility(0);
            this.mKeyguardBottomArea.setAlpha(1.0f);
        } else {
            this.mKeyguardBottomArea.setVisibility(8);
            this.mKeyguardBottomArea.setAlpha(1.0f);
        }
    }

    private void setKeyguardStatusViewVisibility(int statusBarState, boolean keyguardFadingAway, boolean goingToFullShade) {
        this.mKeyguardStatusView.animate().cancel();
        this.mKeyguardStatusViewAnimating = false;
        if ((!keyguardFadingAway && this.mStatusBarState == 1 && statusBarState != 1) || goingToFullShade) {
            this.mKeyguardStatusViewAnimating = true;
            this.mKeyguardStatusView.animate().alpha(0.0f).setStartDelay(0L).setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(this.mAnimateKeyguardStatusViewGoneEndRunnable);
            if (keyguardFadingAway) {
                this.mKeyguardStatusView.animate().setStartDelay(this.mStatusBar.getKeyguardFadingAwayDelay()).setDuration(this.mStatusBar.getKeyguardFadingAwayDuration() / 2).start();
                return;
            }
            return;
        }
        if (this.mStatusBarState == 2 && statusBarState == 1) {
            this.mKeyguardStatusView.setVisibility(0);
            this.mKeyguardStatusViewAnimating = true;
            this.mKeyguardStatusView.setAlpha(0.0f);
            this.mKeyguardStatusView.animate().alpha(1.0f).setStartDelay(0L).setDuration(320L).setInterpolator(Interpolators.ALPHA_IN).withEndAction(this.mAnimateKeyguardStatusViewVisibleEndRunnable);
            return;
        }
        if (statusBarState == 1) {
            if (!keyguardFadingAway) {
                this.mKeyguardStatusView.setVisibility(0);
                this.mKeyguardStatusView.setAlpha(1.0f);
                return;
            } else {
                this.mKeyguardStatusViewAnimating = true;
                this.mKeyguardStatusView.animate().alpha(0.0f).translationYBy((-getHeight()) * 0.05f).setInterpolator(Interpolators.FAST_OUT_LINEAR_IN).setDuration(125L).setStartDelay(0L).withEndAction(this.mAnimateKeyguardStatusViewInvisibleEndRunnable).start();
                return;
            }
        }
        this.mKeyguardStatusView.setVisibility(8);
        this.mKeyguardStatusView.setAlpha(1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateQsState() {
        this.mNotificationStackScroller.setQsExpanded(this.mQsExpanded);
        this.mNotificationStackScroller.setScrollingEnabled(this.mStatusBarState != 1 && (!this.mQsExpanded || this.mQsExpansionFromOverscroll));
        updateEmptyShadeView();
        this.mQsNavbarScrim.setVisibility((this.mStatusBarState == 0 && this.mQsExpanded && !this.mStackScrollerOverscrolling && this.mQsScrimEnabled) ? 0 : 4);
        if (this.mKeyguardUserSwitcher != null && this.mQsExpanded && !this.mStackScrollerOverscrolling) {
            this.mKeyguardUserSwitcher.hideIfNotSimple(true);
        }
        if (this.mQs == null) {
            return;
        }
        this.mQs.setExpanded(this.mQsExpanded);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setQsExpansion(float height) {
        float height2 = Math.min(Math.max(height, this.mQsMinExpansionHeight), this.mQsMaxExpansionHeight);
        this.mQsFullyExpanded = height2 == ((float) this.mQsMaxExpansionHeight) && this.mQsMaxExpansionHeight != 0;
        if (height2 > this.mQsMinExpansionHeight && !this.mQsExpanded && !this.mStackScrollerOverscrolling) {
            setQsExpanded(true);
        } else if (height2 <= this.mQsMinExpansionHeight && this.mQsExpanded) {
            setQsExpanded(false);
        }
        this.mQsExpansionHeight = height2;
        updateQsExpansion();
        requestScrollerTopPaddingUpdate(false);
        if (this.mKeyguardShowing) {
            updateHeaderKeyguardAlpha();
        }
        if (this.mStatusBarState == 2 || this.mStatusBarState == 1) {
            updateKeyguardBottomAreaAlpha();
        }
        if (this.mStatusBarState == 0 && this.mQsExpanded && !this.mStackScrollerOverscrolling && this.mQsScrimEnabled) {
            this.mQsNavbarScrim.setAlpha(getQsExpansionFraction());
        }
        if (this.mAccessibilityManager.isEnabled()) {
            setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        }
        if (this.mQsFullyExpanded && this.mFalsingManager.shouldEnforceBouncer()) {
            this.mStatusBar.executeRunnableDismissingKeyguard(null, null, false, true, false);
        }
    }

    protected void updateQsExpansion() {
        if (this.mQs == null) {
            return;
        }
        float qsExpansionFraction = getQsExpansionFraction();
        this.mQs.setQsExpansion(qsExpansionFraction, getHeaderTranslation());
        this.mNotificationStackScroller.setQsExpansionFraction(qsExpansionFraction);
    }

    private String determineAccessibilityPaneTitle() {
        if (this.mQs != null && this.mQs.isCustomizing()) {
            return getContext().getString(R.string.accessibility_desc_quick_settings_edit);
        }
        if (this.mQsExpansionHeight != 0.0f && this.mQsFullyExpanded) {
            return getContext().getString(R.string.accessibility_desc_quick_settings);
        }
        if (this.mStatusBarState == 1) {
            return getContext().getString(R.string.accessibility_desc_lock_screen);
        }
        return getContext().getString(R.string.accessibility_desc_notification_shade);
    }

    private float calculateQsTopPadding() {
        int max;
        if (this.mKeyguardShowing && (this.mQsExpandImmediate || (this.mIsExpanding && this.mQsExpandedWhenExpandingStarted))) {
            int maxNotificationPadding = this.mClockPositionResult.stackScrollerPadding;
            int maxQsPadding = this.mQsMaxExpansionHeight + this.mQsNotificationTopPadding;
            if (this.mStatusBarState == 1) {
                max = Math.max(maxNotificationPadding, maxQsPadding);
            } else {
                max = maxQsPadding;
            }
            return (int) interpolate(getExpandedFraction(), this.mQsMinExpansionHeight, max);
        }
        if (this.mQsSizeChangeAnimator != null) {
            return ((Integer) this.mQsSizeChangeAnimator.getAnimatedValue()).intValue();
        }
        if (this.mKeyguardShowing) {
            return interpolate(getQsExpansionFraction(), this.mNotificationStackScroller.getIntrinsicPadding(), this.mQsMaxExpansionHeight + this.mQsNotificationTopPadding);
        }
        return this.mQsExpansionHeight + this.mQsNotificationTopPadding;
    }

    protected void requestScrollerTopPaddingUpdate(boolean animate) {
        this.mNotificationStackScroller.updateTopPadding(calculateQsTopPadding(), animate, this.mKeyguardShowing && (this.mQsExpandImmediate || (this.mIsExpanding && this.mQsExpandedWhenExpandingStarted)));
    }

    private void trackMovement(MotionEvent event) {
        if (this.mQsVelocityTracker != null) {
            this.mQsVelocityTracker.addMovement(event);
        }
        this.mLastTouchX = event.getX();
        this.mLastTouchY = event.getY();
    }

    private void initVelocityTracker() {
        if (this.mQsVelocityTracker != null) {
            this.mQsVelocityTracker.recycle();
        }
        this.mQsVelocityTracker = VelocityTracker.obtain();
    }

    private float getCurrentQSVelocity() {
        if (this.mQsVelocityTracker == null) {
            return 0.0f;
        }
        this.mQsVelocityTracker.computeCurrentVelocity(1000);
        return this.mQsVelocityTracker.getYVelocity();
    }

    private void cancelQsAnimation() {
        if (this.mQsExpansionAnimator != null) {
            this.mQsExpansionAnimator.cancel();
        }
    }

    public void flingSettings(float vel, boolean expand) {
        flingSettings(vel, expand, null, false);
    }

    protected void flingSettings(float vel, boolean expand, final Runnable onFinishRunnable, boolean isClick) {
        float target = expand ? this.mQsMaxExpansionHeight : this.mQsMinExpansionHeight;
        if (target == this.mQsExpansionHeight) {
            if (onFinishRunnable != null) {
                onFinishRunnable.run();
                return;
            }
            return;
        }
        boolean oppositeDirection = false;
        if ((vel > 0.0f && !expand) || (vel < 0.0f && expand)) {
            vel = 0.0f;
            oppositeDirection = true;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(this.mQsExpansionHeight, target);
        if (isClick) {
            animator.setInterpolator(Interpolators.TOUCH_RESPONSE);
            animator.setDuration(368L);
        } else {
            this.mFlingAnimationUtils.apply(animator, this.mQsExpansionHeight, target, vel);
        }
        if (oppositeDirection) {
            animator.setDuration(350L);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.14
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                NotificationPanelView.this.setQsExpansion(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.15
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                NotificationPanelView.this.mNotificationStackScroller.resetCheckSnoozeLeavebehind();
                NotificationPanelView.this.mQsExpansionAnimator = null;
                if (onFinishRunnable != null) {
                    onFinishRunnable.run();
                }
            }
        });
        animator.start();
        this.mQsExpansionAnimator = animator;
        this.mQsAnimatorExpand = expand;
    }

    private boolean shouldQuickSettingsIntercept(float x, float y, float yDiff) {
        if (!this.mQsExpansionEnabled || this.mCollapsedOnDown || this.mQs == null || this.mQsFrame == null) {
            return false;
        }
        View header = this.mKeyguardShowing ? this.mKeyguardStatusBar : this.mQs.getHeader();
        boolean onHeader = x >= this.mQsFrame.getX() && x <= this.mQsFrame.getX() + ((float) this.mQsFrame.getWidth()) && y >= ((float) header.getTop()) && y <= ((float) header.getBottom());
        if (this.mQsExpanded) {
            return onHeader || (yDiff < 0.0f && isInQsArea(x, y));
        }
        return onHeader;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean isScrolledToBottom() {
        return isInSettings() || this.mStatusBar.getBarState() == 1 || this.mNotificationStackScroller.isScrolledToBottom();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected int getMaxPanelHeight() {
        int maxHeight;
        int min = this.mStatusBarMinHeight;
        if (this.mStatusBar.getBarState() != 1 && this.mNotificationStackScroller.getNotGoneChildCount() == 0) {
            int minHeight = (int) (this.mQsMinExpansionHeight + getOverExpansionAmount());
            min = Math.max(min, minHeight);
        }
        if (this.mQsExpandImmediate || this.mQsExpanded || (this.mIsExpanding && this.mQsExpandedWhenExpandingStarted)) {
            maxHeight = calculatePanelHeightQsExpanded();
        } else {
            maxHeight = calculatePanelHeightShade();
        }
        return Math.max(maxHeight, min);
    }

    public boolean isInSettings() {
        return this.mQsExpanded;
    }

    public boolean isExpanding() {
        return this.mIsExpanding;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onHeightUpdated(float expandedHeight) {
        float panelHeightQsCollapsed;
        if ((!this.mQsExpanded || this.mQsExpandImmediate || (this.mIsExpanding && this.mQsExpandedWhenExpandingStarted)) && this.mStackScrollerMeasuringPass <= 2) {
            positionClockAndNotifications();
        }
        if (this.mQsExpandImmediate || (this.mQsExpanded && !this.mQsTracking && this.mQsExpansionAnimator == null && !this.mQsExpansionFromOverscroll)) {
            if (this.mKeyguardShowing) {
                panelHeightQsCollapsed = expandedHeight / getMaxPanelHeight();
            } else {
                float panelHeightQsCollapsed2 = this.mNotificationStackScroller.getIntrinsicPadding() + this.mNotificationStackScroller.getLayoutMinHeight();
                float panelHeightQsExpanded = calculatePanelHeightQsExpanded();
                panelHeightQsCollapsed = (expandedHeight - panelHeightQsCollapsed2) / (panelHeightQsExpanded - panelHeightQsCollapsed2);
            }
            setQsExpansion(this.mQsMinExpansionHeight + ((this.mQsMaxExpansionHeight - this.mQsMinExpansionHeight) * panelHeightQsCollapsed));
        }
        updateExpandedHeight(expandedHeight);
        updateHeader();
        updateUnlockIcon();
        updateNotificationTranslucency();
        updatePanelExpanded();
        this.mNotificationStackScroller.setShadeExpanded(!isFullyCollapsed());
    }

    private void updatePanelExpanded() {
        boolean isExpanded = !isFullyCollapsed();
        if (this.mPanelExpanded != isExpanded) {
            this.mHeadsUpManager.setIsPanelExpanded(isExpanded);
            this.mStatusBar.setPanelExpanded(isExpanded);
            this.mPanelExpanded = isExpanded;
        }
    }

    private int calculatePanelHeightShade() {
        int emptyBottomMargin = this.mNotificationStackScroller.getEmptyBottomMargin();
        int maxHeight = (int) ((this.mNotificationStackScroller.getHeight() - emptyBottomMargin) + this.mNotificationStackScroller.getTopPaddingOverflow());
        if (this.mStatusBarState == 1) {
            int minKeyguardPanelBottom = this.mClockPositionAlgorithm.getExpandedClockPosition() + this.mKeyguardStatusView.getHeight() + this.mNotificationStackScroller.getIntrinsicContentHeight();
            return Math.max(maxHeight, minKeyguardPanelBottom);
        }
        return maxHeight;
    }

    private int calculatePanelHeightQsExpanded() {
        float notificationHeight = (this.mNotificationStackScroller.getHeight() - this.mNotificationStackScroller.getEmptyBottomMargin()) - this.mNotificationStackScroller.getTopPadding();
        if (this.mNotificationStackScroller.getNotGoneChildCount() == 0 && this.mShowEmptyShadeView) {
            notificationHeight = this.mNotificationStackScroller.getEmptyShadeViewHeight();
        }
        int maxQsHeight = this.mQsMaxExpansionHeight;
        if (this.mKeyguardShowing) {
            maxQsHeight += this.mQsNotificationTopPadding;
        }
        if (this.mQsSizeChangeAnimator != null) {
            maxQsHeight = ((Integer) this.mQsSizeChangeAnimator.getAnimatedValue()).intValue();
        }
        float totalHeight = Math.max(maxQsHeight, this.mStatusBarState == 1 ? this.mClockPositionResult.stackScrollerPadding : 0) + notificationHeight + this.mNotificationStackScroller.getTopPaddingOverflow();
        if (totalHeight > this.mNotificationStackScroller.getHeight()) {
            float fullyCollapsedHeight = this.mNotificationStackScroller.getLayoutMinHeight() + maxQsHeight;
            totalHeight = Math.max(fullyCollapsedHeight, this.mNotificationStackScroller.getHeight());
        }
        return (int) totalHeight;
    }

    private void updateNotificationTranslucency() {
        float alpha = 1.0f;
        if (this.mClosingWithAlphaFadeOut && !this.mExpandingFromHeadsUp && !this.mHeadsUpManager.hasPinnedHeadsUp()) {
            alpha = getFadeoutAlpha();
        }
        this.mNotificationStackScroller.setAlpha(alpha);
    }

    private float getFadeoutAlpha() {
        float alpha = (getNotificationsTopY() + this.mNotificationStackScroller.getFirstItemMinHeight()) / this.mQsMinExpansionHeight;
        return (float) Math.pow(Math.max(0.0f, Math.min(alpha, 1.0f)), 0.75d);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected float getOverExpansionAmount() {
        return this.mNotificationStackScroller.getCurrentOverScrollAmount(true);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected float getOverExpansionPixels() {
        return this.mNotificationStackScroller.getCurrentOverScrolledPixels(true);
    }

    private void updateUnlockIcon() {
        if (this.mStatusBar.getBarState() == 1 || this.mStatusBar.getBarState() == 2) {
            boolean active = ((float) getMaxPanelHeight()) - getExpandedHeight() > ((float) this.mUnlockMoveDistance);
            KeyguardAffordanceView lockIcon = this.mKeyguardBottomArea.getLockIcon();
            if (active && !this.mUnlockIconActive && this.mTracking) {
                lockIcon.setImageAlpha(1.0f, true, 150L, Interpolators.FAST_OUT_LINEAR_IN, null);
                lockIcon.setImageScale(1.2f, true, 150L, Interpolators.FAST_OUT_LINEAR_IN);
            } else if (!active && this.mUnlockIconActive && this.mTracking) {
                lockIcon.setImageAlpha(lockIcon.getRestingAlpha(), true, 150L, Interpolators.FAST_OUT_LINEAR_IN, null);
                lockIcon.setImageScale(1.0f, true, 150L, Interpolators.FAST_OUT_LINEAR_IN);
            }
            this.mUnlockIconActive = active;
        }
    }

    private void updateHeader() {
        if (this.mStatusBar.getBarState() == 1) {
            updateHeaderKeyguardAlpha();
        }
        updateQsExpansion();
    }

    protected float getHeaderTranslation() {
        if (this.mStatusBar.getBarState() == 1) {
            return 0.0f;
        }
        float translation = MathUtils.lerp(-this.mQsMinExpansionHeight, 0.0f, Math.min(1.0f, this.mNotificationStackScroller.getAppearFraction(this.mExpandedHeight))) + this.mExpandOffset;
        return Math.min(0.0f, translation);
    }

    private float getKeyguardContentsAlpha() {
        float alpha;
        if (this.mStatusBar.getBarState() == 1) {
            alpha = getNotificationsTopY() / (this.mKeyguardStatusBar.getHeight() + this.mNotificationsHeaderCollideDistance);
        } else {
            float alpha2 = getNotificationsTopY();
            alpha = alpha2 / this.mKeyguardStatusBar.getHeight();
        }
        return (float) Math.pow(MathUtils.constrain(alpha, 0.0f, 1.0f), 0.75d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHeaderKeyguardAlpha() {
        float alphaQsExpansion = 1.0f - Math.min(1.0f, getQsExpansionFraction() * 2.0f);
        this.mKeyguardStatusBar.setAlpha(Math.min(getKeyguardContentsAlpha(), alphaQsExpansion) * this.mKeyguardStatusBarAnimateAlpha);
        this.mKeyguardStatusBar.setVisibility((this.mKeyguardStatusBar.getAlpha() == 0.0f || this.mDozing) ? 4 : 0);
    }

    private void updateKeyguardBottomAreaAlpha() {
        int i;
        float expansionAlpha = MathUtils.map(isUnlockHintRunning() ? 0.0f : 0.95f, 1.0f, 0.0f, 1.0f, getExpandedFraction());
        float alpha = Math.min(expansionAlpha, 1.0f - getQsExpansionFraction());
        this.mKeyguardBottomArea.setAlpha(alpha);
        KeyguardBottomAreaView keyguardBottomAreaView = this.mKeyguardBottomArea;
        if (alpha == 0.0f) {
            i = 4;
        } else {
            i = 0;
        }
        keyguardBottomAreaView.setImportantForAccessibility(i);
        View ambientIndicationContainer = this.mStatusBar.getAmbientIndicationContainer();
        if (ambientIndicationContainer != null) {
            ambientIndicationContainer.setAlpha(alpha);
        }
    }

    private float getNotificationsTopY() {
        if (this.mNotificationStackScroller.getNotGoneChildCount() == 0) {
            return getExpandedHeight();
        }
        return this.mNotificationStackScroller.getNotificationsTopY();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onExpandingStarted() {
        super.onExpandingStarted();
        this.mNotificationStackScroller.onExpansionStarted();
        this.mIsExpanding = true;
        this.mQsExpandedWhenExpandingStarted = this.mQsFullyExpanded;
        if (this.mQsExpanded) {
            onQsExpansionStarted();
        }
        if (this.mQs == null) {
            return;
        }
        this.mQs.setHeaderListening(true);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onExpandingFinished() {
        super.onExpandingFinished();
        this.mNotificationStackScroller.onExpansionStopped();
        this.mHeadsUpManager.onExpandingFinished();
        this.mIsExpanding = false;
        if (isFullyCollapsed()) {
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.16
                @Override // java.lang.Runnable
                public void run() {
                    NotificationPanelView.this.setListening(false);
                }
            });
            postOnAnimation(new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.17
                @Override // java.lang.Runnable
                public void run() {
                    NotificationPanelView.this.getParent().invalidateChild(NotificationPanelView.this, NotificationPanelView.mDummyDirtyRect);
                }
            });
        } else {
            setListening(true);
        }
        this.mQsExpandImmediate = false;
        this.mNotificationStackScroller.setShouldShowShelfOnly(false);
        this.mTwoFingerQsExpandPossible = false;
        this.mIsExpansionFromHeadsUp = false;
        notifyListenersTrackingHeadsUp(null);
        this.mExpandingFromHeadsUp = false;
        setPanelScrimMinFraction(0.0f);
    }

    private void notifyListenersTrackingHeadsUp(ExpandableNotificationRow pickedChild) {
        for (int i = 0; i < this.mTrackingHeadsUpListeners.size(); i++) {
            Consumer<ExpandableNotificationRow> listener = this.mTrackingHeadsUpListeners.get(i);
            listener.accept(pickedChild);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListening(boolean listening) {
        this.mKeyguardStatusBar.setListening(listening);
        if (this.mQs == null) {
            return;
        }
        this.mQs.setListening(listening);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    public void expand(boolean animate) {
        super.expand(animate);
        setListening(true);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void setOverExpansion(float overExpansion, boolean isPixels) {
        if (!this.mConflictingQsExpansionGesture && !this.mQsExpandImmediate && this.mStatusBar.getBarState() != 1) {
            this.mNotificationStackScroller.setOnHeightChangedListener(null);
            if (isPixels) {
                this.mNotificationStackScroller.setOverScrolledPixels(overExpansion, true, false);
            } else {
                this.mNotificationStackScroller.setOverScrollAmount(overExpansion, true, false);
            }
            this.mNotificationStackScroller.setOnHeightChangedListener(this);
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onTrackingStarted() {
        this.mFalsingManager.onTrackingStarted(this.mStatusBar.isKeyguardCurrentlySecure());
        super.onTrackingStarted();
        if (this.mQsFullyExpanded) {
            this.mQsExpandImmediate = true;
            this.mNotificationStackScroller.setShouldShowShelfOnly(true);
        }
        if (this.mStatusBar.getBarState() == 1 || this.mStatusBar.getBarState() == 2) {
            this.mAffordanceHelper.animateHideLeftRightIcon();
        }
        this.mNotificationStackScroller.onPanelTrackingStarted();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onTrackingStopped(boolean expand) {
        this.mFalsingManager.onTrackingStopped();
        super.onTrackingStopped(expand);
        if (expand) {
            this.mNotificationStackScroller.setOverScrolledPixels(0.0f, true, true);
        }
        this.mNotificationStackScroller.onPanelTrackingStopped();
        if (expand && ((this.mStatusBar.getBarState() == 1 || this.mStatusBar.getBarState() == 2) && !this.mHintAnimationRunning)) {
            this.mAffordanceHelper.reset(true);
        }
        if (!expand) {
            if (this.mStatusBar.getBarState() == 1 || this.mStatusBar.getBarState() == 2) {
                KeyguardAffordanceView lockIcon = this.mKeyguardBottomArea.getLockIcon();
                lockIcon.setImageAlpha(0.0f, true, 100L, Interpolators.FAST_OUT_LINEAR_IN, null);
                lockIcon.setImageScale(2.0f, true, 100L, Interpolators.FAST_OUT_LINEAR_IN);
            }
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView.OnHeightChangedListener
    public void onHeightChanged(ExpandableView view, boolean needsAnimation) {
        ExpandableNotificationRow firstRow;
        if (view == null && this.mQsExpanded) {
            return;
        }
        if (needsAnimation && this.mDarkAmount == 0.0f) {
            this.mAnimateNextPositionUpdate = true;
        }
        ExpandableView firstChildNotGone = this.mNotificationStackScroller.getFirstChildNotGone();
        if (firstChildNotGone instanceof ExpandableNotificationRow) {
            firstRow = (ExpandableNotificationRow) firstChildNotGone;
        } else {
            firstRow = null;
        }
        if (firstRow != null && (view == firstRow || firstRow.getNotificationParent() == firstRow)) {
            requestScrollerTopPaddingUpdate(false);
        }
        requestPanelHeightUpdate();
    }

    @Override // com.android.systemui.statusbar.ExpandableView.OnHeightChangedListener
    public void onReset(ExpandableView view) {
    }

    @Override // com.android.systemui.plugins.qs.QS.HeightListener
    public void onQsHeightChanged() {
        this.mQsMaxExpansionHeight = this.mQs != null ? this.mQs.getDesiredHeight() : 0;
        if (this.mQsExpanded && this.mQsFullyExpanded) {
            this.mQsExpansionHeight = this.mQsMaxExpansionHeight;
            requestScrollerTopPaddingUpdate(false);
            requestPanelHeightUpdate();
        }
        if (this.mAccessibilityManager.isEnabled()) {
            setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        }
        this.mNotificationStackScroller.setMaxTopPadding(this.mQsMaxExpansionHeight + this.mQsNotificationTopPadding);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView, android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mAffordanceHelper.onConfigurationChanged();
        if (newConfig.orientation != this.mLastOrientation) {
            resetVerticalPanelPosition();
        }
        this.mLastOrientation = newConfig.orientation;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        this.mNavigationBarBottomHeight = insets.getStableInsetBottom();
        updateMaxHeadsUpTranslation();
        return insets;
    }

    private void updateMaxHeadsUpTranslation() {
        this.mNotificationStackScroller.setHeadsUpBoundaries(getHeight(), this.mNavigationBarBottomHeight);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        if (layoutDirection != this.mOldLayoutDirection) {
            this.mAffordanceHelper.onRtlPropertiesChanged();
            this.mOldLayoutDirection = layoutDirection;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        onQsExpansionStarted();
        if (this.mQsExpanded) {
            flingSettings(0.0f, false, null, true);
        } else if (this.mQsExpansionEnabled) {
            this.mLockscreenGestureLogger.write(195, 0, 0);
            flingSettings(0.0f, true, null, true);
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public void onAnimationToSideStarted(boolean rightPage, float translation, float vel) {
        boolean start;
        if (getLayoutDirection() == 1) {
            start = rightPage;
        } else {
            start = !rightPage;
        }
        this.mIsLaunchTransitionRunning = true;
        this.mLaunchAnimationEndRunnable = null;
        float displayDensity = this.mStatusBar.getDisplayDensity();
        int lengthDp = Math.abs((int) (translation / displayDensity));
        int velocityDp = Math.abs((int) (vel / displayDensity));
        if (start) {
            this.mLockscreenGestureLogger.write(190, lengthDp, velocityDp);
            this.mFalsingManager.onLeftAffordanceOn();
            if (this.mFalsingManager.shouldEnforceBouncer()) {
                this.mStatusBar.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.18
                    @Override // java.lang.Runnable
                    public void run() {
                        NotificationPanelView.this.mKeyguardBottomArea.launchLeftAffordance();
                    }
                }, null, true, false, true);
            } else {
                this.mKeyguardBottomArea.launchLeftAffordance();
            }
        } else {
            if ("lockscreen_affordance".equals(this.mLastCameraLaunchSource)) {
                this.mLockscreenGestureLogger.write(189, lengthDp, velocityDp);
            }
            this.mFalsingManager.onCameraOn();
            if (this.mFalsingManager.shouldEnforceBouncer()) {
                this.mStatusBar.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.19
                    @Override // java.lang.Runnable
                    public void run() {
                        NotificationPanelView.this.mKeyguardBottomArea.launchCamera(NotificationPanelView.this.mLastCameraLaunchSource);
                    }
                }, null, true, false, true);
            } else {
                this.mKeyguardBottomArea.launchCamera(this.mLastCameraLaunchSource);
            }
        }
        this.mStatusBar.startLaunchTransitionTimeout();
        this.mBlockTouches = true;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public void onAnimationToSideEnded() {
        this.mIsLaunchTransitionRunning = false;
        this.mIsLaunchTransitionFinished = true;
        if (this.mLaunchAnimationEndRunnable != null) {
            this.mLaunchAnimationEndRunnable.run();
            this.mLaunchAnimationEndRunnable = null;
        }
        this.mStatusBar.readyForKeyguardDone();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void startUnlockHintAnimation() {
        if (this.mPowerManager.isPowerSaveMode()) {
            onUnlockHintStarted();
            onUnlockHintFinished();
        } else {
            super.startUnlockHintAnimation();
            startHighlightIconAnimation(getCenterIcon());
        }
    }

    private void startHighlightIconAnimation(final KeyguardAffordanceView icon) {
        icon.setImageAlpha(1.0f, true, 200L, Interpolators.FAST_OUT_SLOW_IN, new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.20
            @Override // java.lang.Runnable
            public void run() {
                icon.setImageAlpha(icon.getRestingAlpha(), true, 200L, Interpolators.FAST_OUT_SLOW_IN, null);
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public float getMaxTranslationDistance() {
        return (float) Math.hypot(getWidth(), getHeight());
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public void onSwipingStarted(boolean rightIcon) {
        boolean camera;
        this.mFalsingManager.onAffordanceSwipingStarted(rightIcon);
        if (getLayoutDirection() == 1) {
            camera = !rightIcon;
        } else {
            camera = rightIcon;
        }
        if (camera) {
            this.mKeyguardBottomArea.bindCameraPrewarmService();
        }
        requestDisallowInterceptTouchEvent(true);
        this.mOnlyAffordanceInThisMotion = true;
        this.mQsTracking = false;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public void onSwipingAborted() {
        this.mFalsingManager.onAffordanceSwipingAborted();
        this.mKeyguardBottomArea.unbindCameraPrewarmService(false);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public void onIconClicked(boolean rightIcon) {
        if (this.mHintAnimationRunning) {
            return;
        }
        boolean rightIcon2 = true;
        this.mHintAnimationRunning = true;
        this.mAffordanceHelper.startHintAnimation(rightIcon, new Runnable() { // from class: com.android.systemui.statusbar.phone.NotificationPanelView.21
            @Override // java.lang.Runnable
            public void run() {
                NotificationPanelView.this.mHintAnimationRunning = false;
                NotificationPanelView.this.mStatusBar.onHintFinished();
            }
        });
        if (getLayoutDirection() != 1) {
            rightIcon2 = rightIcon;
        } else if (rightIcon) {
            rightIcon2 = false;
        }
        if (rightIcon2) {
            this.mStatusBar.onCameraHintStarted();
        } else if (this.mKeyguardBottomArea.isLeftVoiceAssist()) {
            this.mStatusBar.onVoiceAssistHintStarted();
        } else {
            this.mStatusBar.onPhoneHintStarted();
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onUnlockHintFinished() {
        super.onUnlockHintFinished();
        this.mNotificationStackScroller.setUnlockHintRunning(false);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onUnlockHintStarted() {
        super.onUnlockHintStarted();
        this.mNotificationStackScroller.setUnlockHintRunning(true);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public KeyguardAffordanceView getLeftIcon() {
        if (getLayoutDirection() == 1) {
            return this.mKeyguardBottomArea.getRightView();
        }
        return this.mKeyguardBottomArea.getLeftView();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public KeyguardAffordanceView getCenterIcon() {
        return this.mKeyguardBottomArea.getLockIcon();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public KeyguardAffordanceView getRightIcon() {
        if (getLayoutDirection() == 1) {
            return this.mKeyguardBottomArea.getLeftView();
        }
        return this.mKeyguardBottomArea.getRightView();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public View getLeftPreview() {
        if (getLayoutDirection() == 1) {
            return this.mKeyguardBottomArea.getRightPreview();
        }
        return this.mKeyguardBottomArea.getLeftPreview();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public View getRightPreview() {
        if (getLayoutDirection() == 1) {
            return this.mKeyguardBottomArea.getLeftPreview();
        }
        return this.mKeyguardBottomArea.getRightPreview();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public float getAffordanceFalsingFactor() {
        return this.mStatusBar.isWakeUpComingFromTouch() ? 1.5f : 1.0f;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.Callback
    public boolean needsAntiFalsing() {
        return this.mStatusBarState == 1;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected float getPeekHeight() {
        if (this.mNotificationStackScroller.getNotGoneChildCount() > 0) {
            return this.mNotificationStackScroller.getPeekHeight();
        }
        return this.mQsMinExpansionHeight;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean shouldUseDismissingAnimation() {
        return (this.mStatusBarState == 0 || (this.mStatusBar.isKeyguardCurrentlySecure() && isTracking())) ? false : true;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean fullyExpandedClearAllVisible() {
        return this.mNotificationStackScroller.isFooterViewNotGone() && this.mNotificationStackScroller.isScrolledToBottom() && !this.mQsExpandImmediate;
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean isClearAllVisible() {
        return this.mNotificationStackScroller.isFooterViewContentVisible();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected int getClearAllHeight() {
        return this.mNotificationStackScroller.getFooterViewHeight();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean isTrackingBlocked() {
        return this.mConflictingQsExpansionGesture && this.mQsExpanded;
    }

    public boolean isQsExpanded() {
        return this.mQsExpanded;
    }

    public boolean isQsDetailShowing() {
        return this.mQs.isShowingDetail();
    }

    public QS getQS() {
        return this.mQs;
    }

    public void closeQsDetail() {
        this.mQs.closeDetail();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public boolean isLaunchTransitionFinished() {
        return this.mIsLaunchTransitionFinished;
    }

    public boolean isLaunchTransitionRunning() {
        return this.mIsLaunchTransitionRunning;
    }

    public void setLaunchTransitionEndRunnable(Runnable r) {
        this.mLaunchAnimationEndRunnable = r;
    }

    public void setEmptyDragAmount(float amount) {
        float factor = 0.8f;
        if (this.mNotificationStackScroller.getNotGoneChildCount() > 0 || !this.mStatusBar.hasActiveNotifications()) {
            factor = 0.4f;
        }
        this.mEmptyDragAmount = amount * factor;
        positionClockAndNotifications();
    }

    private static float interpolate(float t, float start, float end) {
        return ((1.0f - t) * start) + (t * end);
    }

    private void updateDozingVisibilities(boolean animate) {
        if (this.mDozing) {
            this.mKeyguardStatusBar.setVisibility(4);
            this.mKeyguardBottomArea.setDozing(this.mDozing, animate);
            return;
        }
        this.mKeyguardStatusBar.setVisibility(0);
        this.mKeyguardBottomArea.setDozing(this.mDozing, animate);
        if (animate) {
            animateKeyguardStatusBarIn(700L);
        }
    }

    public boolean isDozing() {
        return this.mDozing;
    }

    public void showEmptyShadeView(boolean emptyShadeViewVisible) {
        this.mShowEmptyShadeView = emptyShadeViewVisible;
        updateEmptyShadeView();
    }

    private void updateEmptyShadeView() {
        this.mNotificationStackScroller.updateEmptyShadeView(this.mShowEmptyShadeView && !this.mQsExpanded);
    }

    public void setQsScrimEnabled(boolean qsScrimEnabled) {
        boolean changed = this.mQsScrimEnabled != qsScrimEnabled;
        this.mQsScrimEnabled = qsScrimEnabled;
        if (changed) {
            updateQsState();
        }
    }

    public void setKeyguardUserSwitcher(KeyguardUserSwitcher keyguardUserSwitcher) {
        this.mKeyguardUserSwitcher = keyguardUserSwitcher;
    }

    public void onScreenTurningOn() {
        this.mKeyguardStatusView.dozeTimeTick();
    }

    @Override // com.android.systemui.statusbar.stack.NotificationStackScrollLayout.OnEmptySpaceClickListener
    public void onEmptySpaceClicked(float x, float y) {
        onEmptySpaceClick(x);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean onMiddleClicked() {
        switch (this.mStatusBar.getBarState()) {
            case 0:
                post(this.mPostCollapseRunnable);
                return false;
            case 1:
                if (!this.mDozingOnDown) {
                    this.mLockscreenGestureLogger.write(188, 0, 0);
                    startUnlockHintAnimation();
                }
                return true;
            case 2:
                if (!this.mQsExpanded) {
                    this.mStatusBar.goToKeyguard();
                }
                return true;
            default:
                return true;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mCurrentPanelAlpha != 255) {
            canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), this.mAlphaPaint);
        }
    }

    public float getCurrentPanelAlpha() {
        return this.mCurrentPanelAlpha;
    }

    public boolean setPanelAlpha(int alpha, boolean animate) {
        if (this.mPanelAlpha != alpha) {
            this.mPanelAlpha = alpha;
            PropertyAnimator.setProperty(this, this.PANEL_ALPHA, alpha, alpha == 255 ? this.PANEL_ALPHA_IN_PROPERTIES : this.PANEL_ALPHA_OUT_PROPERTIES, animate);
            return true;
        }
        return false;
    }

    public void setPanelAlphaInternal(float alpha) {
        this.mCurrentPanelAlpha = (int) alpha;
        this.mAlphaPaint.setARGB(this.mCurrentPanelAlpha, 255, 255, 255);
        invalidate();
    }

    public void setPanelAlphaEndAction(Runnable r) {
        this.mPanelAlphaEndAction = r;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinnedModeChanged(boolean inPinnedMode) {
        this.mNotificationStackScroller.setInHeadsUpPinnedMode(inPinnedMode);
        if (inPinnedMode) {
            this.mHeadsUpExistenceChangedRunnable.run();
            updateNotificationTranslucency();
        } else {
            setHeadsUpAnimatingAway(true);
            this.mNotificationStackScroller.runAfterAnimationFinished(this.mHeadsUpExistenceChangedRunnable);
        }
    }

    public void setHeadsUpAnimatingAway(boolean headsUpAnimatingAway) {
        this.mHeadsUpAnimatingAway = headsUpAnimatingAway;
        this.mNotificationStackScroller.setHeadsUpAnimatingAway(headsUpAnimatingAway);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpPinned(ExpandableNotificationRow headsUp) {
        this.mNotificationStackScroller.generateHeadsUpAnimation(headsUp, true);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpUnPinned(ExpandableNotificationRow headsUp) {
        if (isFullyCollapsed() && headsUp.isHeadsUp()) {
            this.mNotificationStackScroller.generateHeadsUpAnimation(headsUp, false);
            headsUp.setHeadsUpIsVisible();
        }
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpStateChanged(NotificationData.Entry entry, boolean isHeadsUp) {
        this.mNotificationStackScroller.generateHeadsUpAnimation(entry.row, isHeadsUp);
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    public void setHeadsUpManager(HeadsUpManagerPhone headsUpManager) {
        super.setHeadsUpManager(headsUpManager);
        this.mHeadsUpTouchHelper = new HeadsUpTouchHelper(headsUpManager, this.mNotificationStackScroller, this);
    }

    public void setTrackedHeadsUp(ExpandableNotificationRow pickedChild) {
        if (pickedChild != null) {
            notifyListenersTrackingHeadsUp(pickedChild);
            this.mExpandingFromHeadsUp = true;
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected void onClosingFinished() {
        super.onClosingFinished();
        resetVerticalPanelPosition();
        setClosingWithAlphaFadeout(false);
    }

    private void setClosingWithAlphaFadeout(boolean closing) {
        this.mClosingWithAlphaFadeOut = closing;
        this.mNotificationStackScroller.forceNoOverlappingRendering(closing);
    }

    protected void updateVerticalPanelPosition(float x) {
        if (this.mNotificationStackScroller.getWidth() * 1.75f > getWidth()) {
            resetVerticalPanelPosition();
            return;
        }
        float leftMost = this.mPositionMinSideMargin + (this.mNotificationStackScroller.getWidth() / 2);
        float rightMost = (getWidth() - this.mPositionMinSideMargin) - (this.mNotificationStackScroller.getWidth() / 2);
        if (Math.abs(x - (getWidth() / 2)) < this.mNotificationStackScroller.getWidth() / 4) {
            x = getWidth() / 2;
        }
        setVerticalPanelTranslation(Math.min(rightMost, Math.max(leftMost, x)) - (this.mNotificationStackScroller.getLeft() + (this.mNotificationStackScroller.getWidth() / 2)));
    }

    private void resetVerticalPanelPosition() {
        setVerticalPanelTranslation(0.0f);
    }

    protected void setVerticalPanelTranslation(float translation) {
        this.mNotificationStackScroller.setTranslationX(translation);
        this.mQsFrame.setTranslationX(translation);
        int size = this.mVerticalTranslationListener.size();
        for (int i = 0; i < size; i++) {
            this.mVerticalTranslationListener.get(i).run();
        }
    }

    protected void updateExpandedHeight(float expandedHeight) {
        if (this.mTracking) {
            this.mNotificationStackScroller.setExpandingVelocity(getCurrentExpandVelocity());
        }
        this.mNotificationStackScroller.setExpandedHeight(expandedHeight);
        updateKeyguardBottomAreaAlpha();
        updateStatusBarIcons();
    }

    public boolean isFullWidth() {
        return this.mIsFullWidth;
    }

    private void updateStatusBarIcons() {
        boolean showIconsWhenExpanded = isFullWidth() && getExpandedHeight() < getOpeningHeight();
        if (showIconsWhenExpanded && this.mNoVisibleNotifications && isOnKeyguard()) {
            showIconsWhenExpanded = false;
        }
        if (showIconsWhenExpanded != this.mShowIconsWhenExpanded) {
            this.mShowIconsWhenExpanded = showIconsWhenExpanded;
            this.mStatusBar.recomputeDisableFlags(false);
        }
    }

    private boolean isOnKeyguard() {
        return this.mStatusBar.getBarState() == 1;
    }

    public void setPanelScrimMinFraction(float minFraction) {
        this.mBar.panelScrimMinFractionChanged(minFraction);
    }

    public void clearNotificationEffects() {
        this.mStatusBar.clearNotificationEffects();
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    protected boolean isPanelVisibleBecauseOfHeadsUp() {
        return this.mHeadsUpManager.hasPinnedHeadsUp() || this.mHeadsUpAnimatingAway;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return !this.mDozing;
    }

    public void launchCamera(boolean animate, int source) {
        if (source == 1) {
            this.mLastCameraLaunchSource = "power_double_tap";
        } else if (source == 0) {
            this.mLastCameraLaunchSource = "wiggle_gesture";
        } else if (source == 2) {
            this.mLastCameraLaunchSource = "lift_to_launch_ml";
        } else {
            this.mLastCameraLaunchSource = "lockscreen_affordance";
        }
        if (!isFullyCollapsed()) {
            this.mLaunchingAffordance = true;
            setLaunchingAffordance(true);
        } else {
            animate = false;
        }
        this.mAffordanceHelper.launchAffordance(animate, getLayoutDirection() == 1);
    }

    public void onAffordanceLaunchEnded() {
        this.mLaunchingAffordance = false;
        setLaunchingAffordance(false);
    }

    @Override // android.view.View
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        updateFullyVisibleState(false);
    }

    public void notifyStartFading() {
        updateFullyVisibleState(true);
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        updateFullyVisibleState(false);
    }

    private void updateFullyVisibleState(boolean forceNotFullyVisible) {
        this.mNotificationStackScroller.setParentNotFullyVisible((!forceNotFullyVisible && getAlpha() == 1.0f && getVisibility() == 0) ? false : true);
    }

    private void setLaunchingAffordance(boolean launchingAffordance) {
        getLeftIcon().setLaunchingAffordance(launchingAffordance);
        getRightIcon().setLaunchingAffordance(launchingAffordance);
        getCenterIcon().setLaunchingAffordance(launchingAffordance);
    }

    public boolean canCameraGestureBeLaunched(boolean keyguardIsShowing) {
        if (!this.mStatusBar.isCameraAllowedByAdmin()) {
            return false;
        }
        ResolveInfo resolveInfo = this.mKeyguardBottomArea.resolveCameraIntent();
        String packageToLaunch = (resolveInfo == null || resolveInfo.activityInfo == null) ? null : resolveInfo.activityInfo.packageName;
        if (packageToLaunch != null) {
            return (keyguardIsShowing || !isForegroundApp(packageToLaunch)) && !this.mAffordanceHelper.isSwipingInProgress();
        }
        return false;
    }

    private boolean isForegroundApp(String pkgName) {
        ActivityManager am = (ActivityManager) getContext().getSystemService(ActivityManager.class);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        return !tasks.isEmpty() && pkgName.equals(tasks.get(0).topActivity.getPackageName());
    }

    public void setGroupManager(NotificationGroupManager groupManager) {
        this.mGroupManager = groupManager;
    }

    public boolean hideStatusBarIconsWhenExpanded() {
        if (this.mLaunchingNotification) {
            return this.mHideIconsDuringNotificationLaunch;
        }
        if (this.mHeadsUpAppearanceController == null || !this.mHeadsUpAppearanceController.shouldBeVisible()) {
            return (isFullWidth() && this.mShowIconsWhenExpanded) ? false : true;
        }
        return false;
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.NotificationPanelView$22, reason: invalid class name */
    class AnonymousClass22 implements FragmentHostManager.FragmentListener {
        AnonymousClass22() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public void onFragmentViewCreated(String tag, Fragment fragment) {
            NotificationPanelView.this.mQs = (QS) fragment;
            NotificationPanelView.this.mQs.setPanelView(NotificationPanelView.this);
            NotificationPanelView.this.mQs.setExpandClickListener(NotificationPanelView.this);
            NotificationPanelView.this.mQs.setHeaderClickable(NotificationPanelView.this.mQsExpansionEnabled);
            NotificationPanelView.this.mQs.setKeyguardShowing(NotificationPanelView.this.mKeyguardShowing);
            NotificationPanelView.this.mQs.setOverscrolling(NotificationPanelView.this.mStackScrollerOverscrolling);
            NotificationPanelView.this.mQs.getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NotificationPanelView$22$__XIt3ZYZTq_RPQPhfW5iii8b-o
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    NotificationPanelView.AnonymousClass22.lambda$onFragmentViewCreated$0(this.f$0, view, i, i2, i3, i4, i5, i6, i7, i8);
                }
            });
            NotificationPanelView.this.mNotificationStackScroller.setQsContainer((ViewGroup) NotificationPanelView.this.mQs.getView());
            NotificationPanelView.this.updateQsExpansion();
        }

        public static /* synthetic */ void lambda$onFragmentViewCreated$0(AnonymousClass22 anonymousClass22, View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int height = bottom - top;
            int oldHeight = oldBottom - oldTop;
            if (height != oldHeight) {
                NotificationPanelView.this.onQsHeightChanged();
            }
        }

        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public void onFragmentViewDestroyed(String tag, Fragment fragment) {
            if (fragment == NotificationPanelView.this.mQs) {
                NotificationPanelView.this.mQs = null;
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.PanelView
    public void setTouchDisabled(boolean disabled) {
        super.setTouchDisabled(disabled);
        if (disabled && this.mAffordanceHelper.isSwipingInProgress() && !this.mIsLaunchTransitionRunning) {
            this.mAffordanceHelper.reset(false);
        }
    }

    public void setDozing(boolean dozing, boolean animate) {
        if (dozing == this.mDozing) {
            return;
        }
        this.mDozing = dozing;
        if (this.mStatusBarState == 1 || this.mStatusBarState == 2) {
            updateDozingVisibilities(animate);
        }
        float darkAmount = dozing ? 1.0f : 0.0f;
        if (this.mDarkAnimator != null && this.mDarkAnimator.isRunning()) {
            if (animate && this.mDarkAmountTarget == darkAmount) {
                return;
            } else {
                this.mDarkAnimator.cancel();
            }
        }
        this.mDarkAmountTarget = darkAmount;
        if (animate) {
            this.mDarkAnimator = ObjectAnimator.ofFloat(this, SET_DARK_AMOUNT_PROPERTY, darkAmount);
            this.mDarkAnimator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            this.mDarkAnimator.setDuration(500L);
            this.mDarkAnimator.start();
            return;
        }
        setDarkAmount(darkAmount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDarkAmount(float amount) {
        this.mDarkAmount = amount;
        this.mKeyguardStatusView.setDarkAmount(this.mDarkAmount);
        this.mKeyguardBottomArea.setDarkAmount(this.mDarkAmount);
        positionClockAndNotifications();
    }

    public void setPulsing(boolean pulsing) {
        this.mPulsing = pulsing;
        boolean canAnimatePulse = !DozeParameters.getInstance(this.mContext).getDisplayNeedsBlanking();
        if (canAnimatePulse) {
            this.mAnimateNextPositionUpdate = true;
        }
        this.mNotificationStackScroller.setPulsing(pulsing, canAnimatePulse);
        this.mKeyguardStatusView.setPulsing(pulsing, canAnimatePulse);
    }

    public void dozeTimeTick() {
        this.mKeyguardStatusView.dozeTimeTick();
        this.mKeyguardBottomArea.dozeTimeTick();
        if (this.mDarkAmount > 0.0f) {
            positionClockAndNotifications();
        }
    }

    public void setStatusAccessibilityImportance(int mode) {
        this.mKeyguardStatusView.setImportantForAccessibility(mode);
    }

    public void setUserSetupComplete(boolean userSetupComplete) {
        this.mUserSetupComplete = userSetupComplete;
        this.mKeyguardBottomArea.setUserSetupComplete(userSetupComplete);
    }

    public LockIcon getLockIcon() {
        return this.mKeyguardBottomArea.getLockIcon();
    }

    public void applyExpandAnimationParams(ActivityLaunchAnimator.ExpandAnimationParameters params) {
        this.mExpandOffset = params != null ? params.getTopChange() : 0.0f;
        updateQsExpansion();
        if (params != null) {
            boolean hideIcons = params.getProgress(14L, 100L) == 0.0f;
            if (hideIcons != this.mHideIconsDuringNotificationLaunch) {
                this.mHideIconsDuringNotificationLaunch = hideIcons;
                if (!hideIcons) {
                    this.mStatusBar.recomputeDisableFlags(true);
                }
            }
        }
    }

    public void addTrackingHeadsUpListener(Consumer<ExpandableNotificationRow> listener) {
        this.mTrackingHeadsUpListeners.add(listener);
    }

    public void removeTrackingHeadsUpListener(Consumer<ExpandableNotificationRow> listener) {
        this.mTrackingHeadsUpListeners.remove(listener);
    }

    public void addVerticalTranslationListener(Runnable verticalTranslationListener) {
        this.mVerticalTranslationListener.add(verticalTranslationListener);
    }

    public void removeVerticalTranslationListener(Runnable verticalTranslationListener) {
        this.mVerticalTranslationListener.remove(verticalTranslationListener);
    }

    public void setHeadsUpAppearanceController(HeadsUpAppearanceController headsUpAppearanceController) {
        this.mHeadsUpAppearanceController = headsUpAppearanceController;
    }

    public void onBouncerPreHideAnimation() {
        setKeyguardStatusViewVisibility(this.mStatusBarState, true, false);
    }
}
