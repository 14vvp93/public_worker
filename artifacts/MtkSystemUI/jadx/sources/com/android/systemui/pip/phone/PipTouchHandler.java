package com.android.systemui.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.IPinnedStackController;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.os.logging.MetricsLoggerWrapper;
import com.android.internal.policy.PipSnapAlgorithm;
import com.android.systemui.plugins.R;
import com.android.systemui.shared.system.InputConsumerController;
import com.android.systemui.statusbar.FlingAnimationUtils;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class PipTouchHandler {
    private final AccessibilityManager mAccessibilityManager;
    private final IActivityManager mActivityManager;
    private final Context mContext;
    private final PipDismissViewController mDismissViewController;
    private int mDisplayRotation;
    private int mExpandedShortestEdgeSize;
    private final FlingAnimationUtils mFlingAnimationUtils;
    private final PipTouchGesture[] mGestures;
    private int mImeHeight;
    private int mImeOffset;
    private boolean mIsImeShowing;
    private boolean mIsMinimized;
    private boolean mIsShelfShowing;
    private final PipMenuActivityController mMenuController;
    private final PipMotionHelper mMotionHelper;
    private boolean mMovementWithinDismiss;
    private boolean mMovementWithinMinimize;
    private int mOrientation;
    private IPinnedStackController mPinnedStackController;
    private Rect mResizeStep0Bounds;
    private Rect mResizeStep1Bounds;
    private Rect mResizeStep2Bounds;
    private Rect mResizeStep3Bounds;
    private Rect mResizeStep4Bounds;
    private Rect mResizeStepBoundsOrigin;
    private boolean mSendingHoverAccessibilityEvents;
    private int mShelfHeight;
    private final PipSnapAlgorithm mSnapAlgorithm;
    private final PipTouchState mTouchState;
    private final ViewConfiguration mViewConfig;
    private final PipMenuListener mMenuListener = new PipMenuListener();
    private boolean mShowPipMenuOnAnimationEnd = false;
    private Rect mMovementBounds = new Rect();
    private Rect mInsetBounds = new Rect();
    private Rect mNormalBounds = new Rect();
    private Rect mNormalMovementBounds = new Rect();
    private Rect mExpandedBounds = new Rect();
    private Rect mExpandedMovementBounds = new Rect();
    private int mDeferResizeToNormalBoundsUntilRotation = -1;
    private Handler mHandler = new Handler();
    private Runnable mShowDismissAffordance = new Runnable() { // from class: com.android.systemui.pip.phone.PipTouchHandler.1
        @Override // java.lang.Runnable
        public void run() {
            PipTouchHandler.this.mDismissViewController.showDismissTarget();
        }
    };
    private ValueAnimator.AnimatorUpdateListener mUpdateScrimListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.pip.phone.PipTouchHandler.2
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            PipTouchHandler.this.updateDismissFraction();
        }
    };
    private int mMenuState = 0;
    private float mSavedSnapFraction = -1.0f;
    private final Rect mTmpBounds = new Rect();
    private boolean isOriginBoundsInitialed = false;
    private PipTouchGesture mDefaultMovementGesture = new PipTouchGesture() { // from class: com.android.systemui.pip.phone.PipTouchHandler.3
        private boolean mStartedOnLeft;
        private final Point mStartPosition = new Point();
        private final PointF mDelta = new PointF();

        @Override // com.android.systemui.pip.phone.PipTouchGesture
        public void onDown(PipTouchState touchState) {
            if (touchState.isUserInteracting()) {
                Rect bounds = PipTouchHandler.this.mMotionHelper.getBounds();
                this.mDelta.set(0.0f, 0.0f);
                this.mStartPosition.set(bounds.left, bounds.top);
                this.mStartedOnLeft = bounds.left < PipTouchHandler.this.mMovementBounds.centerX();
                PipTouchHandler.this.mMovementWithinMinimize = true;
                PipTouchHandler.this.mMovementWithinDismiss = touchState.getDownTouchPosition().y >= ((float) PipTouchHandler.this.mMovementBounds.bottom);
                if (PipTouchHandler.this.mMenuState != 0 && !PipTouchHandler.this.mIsMinimized) {
                    PipTouchHandler.this.mMenuController.pokeMenu();
                }
                PipTouchHandler.this.mDismissViewController.createDismissTarget();
                PipTouchHandler.this.mHandler.postDelayed(PipTouchHandler.this.mShowDismissAffordance, 225L);
            }
        }

        @Override // com.android.systemui.pip.phone.PipTouchGesture
        boolean onMove(PipTouchState touchState) {
            if (!touchState.isUserInteracting()) {
                return false;
            }
            if (touchState.startedDragging()) {
                PipTouchHandler.this.mSavedSnapFraction = -1.0f;
                PipTouchHandler.this.mHandler.removeCallbacks(PipTouchHandler.this.mShowDismissAffordance);
                PipTouchHandler.this.mDismissViewController.showDismissTarget();
                PipTouchHandler.this.updateResizeMovementBounds();
            }
            if (!touchState.isDragging()) {
                return false;
            }
            PointF lastDelta = touchState.getLastTouchDelta();
            float lastX = this.mStartPosition.x + this.mDelta.x;
            float lastY = this.mStartPosition.y + this.mDelta.y;
            float left = lastDelta.x + lastX;
            float top = lastDelta.y + lastY;
            touchState.allowDraggingOffscreen();
            float left2 = Math.max(PipTouchHandler.this.mMovementBounds.left, Math.min(PipTouchHandler.this.mMovementBounds.right, left));
            float top2 = Math.max(PipTouchHandler.this.mMovementBounds.top, top);
            this.mDelta.x += left2 - lastX;
            this.mDelta.y += top2 - lastY;
            PipTouchHandler.this.mTmpBounds.set(PipTouchHandler.this.mMotionHelper.getBounds());
            PipTouchHandler.this.mTmpBounds.offsetTo((int) left2, (int) top2);
            PipTouchHandler.this.mMotionHelper.movePip(PipTouchHandler.this.mTmpBounds);
            PipTouchHandler.this.updateDismissFraction();
            PointF curPos = touchState.getLastTouchPosition();
            if (PipTouchHandler.this.mMovementWithinMinimize) {
                PipTouchHandler.this.mMovementWithinMinimize = !this.mStartedOnLeft ? curPos.x < ((float) PipTouchHandler.this.mMovementBounds.right) : curPos.x > ((float) (PipTouchHandler.this.mMovementBounds.left + PipTouchHandler.this.mTmpBounds.width()));
            }
            if (PipTouchHandler.this.mMovementWithinDismiss) {
                PipTouchHandler.this.mMovementWithinDismiss = curPos.y >= ((float) PipTouchHandler.this.mMovementBounds.bottom);
            }
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:40:0x008e, code lost:
        
            r7 = true;
         */
        @Override // com.android.systemui.pip.phone.PipTouchGesture
        @SuppressLint({"NewApi"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean onUp(PipTouchState touchState) {
            PipTouchHandler.this.cleanUpDismissTarget();
            if (!touchState.isUserInteracting()) {
                return false;
            }
            PointF vel = touchState.getVelocity();
            boolean isHorizontal = Math.abs(vel.x) > Math.abs(vel.y);
            float velocity = PointF.length(vel.x, vel.y);
            boolean isFling = velocity > PipTouchHandler.this.mFlingAnimationUtils.getMinVelocityPxPerSecond();
            boolean isFlingToBot = isFling && vel.y > 0.0f && !isHorizontal && (PipTouchHandler.this.mMovementWithinDismiss || 0 != 0);
            if (PipTouchHandler.this.mMotionHelper.shouldDismissPip() || isFlingToBot) {
                Pair<ComponentName, Integer> topPipActivityInfo = PipUtils.getTopPinnedActivity(PipTouchHandler.this.mContext, PipTouchHandler.this.mActivityManager);
                if (topPipActivityInfo.first != null) {
                    MetricsLoggerWrapper.logPictureInPictureDismissByDrag(PipTouchHandler.this.mContext, topPipActivityInfo);
                }
                PipTouchHandler.this.mMotionHelper.animateDismiss(PipTouchHandler.this.mMotionHelper.getBounds(), vel.x, vel.y, PipTouchHandler.this.mUpdateScrimListener);
                return true;
            }
            if (!touchState.isDragging()) {
                if (PipTouchHandler.this.mIsMinimized) {
                    PipTouchHandler.this.mMotionHelper.animateToClosestSnapTarget(PipTouchHandler.this.mMovementBounds, null, null);
                    PipTouchHandler.this.setMinimizedStateInternal(false);
                } else if (PipTouchHandler.this.mMenuState != 2) {
                    if (PipTouchHandler.this.mTouchState.isDoubleTap()) {
                        PipTouchHandler.this.mMotionHelper.expandPip();
                    } else if (PipTouchHandler.this.mTouchState.isWaitingForDoubleTap()) {
                        PipTouchHandler.this.mTouchState.scheduleDoubleTapTimeoutCallback();
                    } else {
                        PipTouchHandler.this.mMenuController.showMenu(2, PipTouchHandler.this.mMotionHelper.getBounds(), PipTouchHandler.this.mMovementBounds, true, PipTouchHandler.this.willResizeMenu());
                    }
                } else {
                    PipTouchHandler.this.mMenuController.hideMenu();
                    PipTouchHandler.this.mMotionHelper.expandPip();
                }
            } else {
                if (isFling && isHorizontal && PipTouchHandler.this.mMovementWithinMinimize) {
                    boolean z = this.mStartedOnLeft ? false : false;
                }
                if (PipTouchHandler.this.mIsMinimized) {
                    PipTouchHandler.this.setMinimizedStateInternal(false);
                }
                AnimatorListenerAdapter postAnimationCallback = null;
                if (PipTouchHandler.this.mMenuState != 0) {
                    PipTouchHandler.this.mMenuController.showMenu(PipTouchHandler.this.mMenuState, PipTouchHandler.this.mMotionHelper.getBounds(), PipTouchHandler.this.mMovementBounds, true, PipTouchHandler.this.willResizeMenu());
                } else {
                    postAnimationCallback = new AnimatorListenerAdapter() { // from class: com.android.systemui.pip.phone.PipTouchHandler.3.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            PipTouchHandler.this.mMenuController.hideMenu();
                        }
                    };
                }
                if (!isFling) {
                    PipTouchHandler.this.mMotionHelper.animateToClosestSnapTarget(PipTouchHandler.this.mMovementBounds, PipTouchHandler.this.mUpdateScrimListener, postAnimationCallback);
                } else {
                    PipTouchHandler.this.mMotionHelper.flingToSnapTarget(velocity, vel.x, vel.y, PipTouchHandler.this.mMovementBounds, PipTouchHandler.this.mUpdateScrimListener, postAnimationCallback, this.mStartPosition);
                }
            }
            return true;
        }
    };
    private BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.android.systemui.pip.phone.PipTouchHandler.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.action.PIP_CONTROL".equals(action)) {
                String key = intent.getStringExtra("command");
                Log.d("PipTouchHandler", "onReceive command : " + key);
                if ("expand".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipExpand();
                    return;
                }
                if ("dismiss".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipDismiss();
                    return;
                }
                if ("bigger".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipBigger();
                    return;
                }
                if ("smaller".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipSmaller();
                    return;
                }
                if ("minimize".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipMinimize();
                    return;
                }
                if ("hidden".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipHidden();
                    return;
                }
                if ("show".equals(key)) {
                    PipTouchHandler.this.mMenuListener.onPipShow();
                } else if ("touchEnabled".equals(key)) {
                    PipTouchHandler.this.setTouchEnabled(true);
                } else if ("touchDisabled".equals(key)) {
                    PipTouchHandler.this.setTouchEnabled(false);
                }
            }
        }
    };

    private class PipMenuListener implements PipMenuActivityController.Listener {
        private PipMenuListener() {
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        public void onPipMenuStateChanged(int menuState, boolean resize) {
            PipTouchHandler.this.setMenuState(menuState, resize);
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        public void onPipExpand() {
            if (!PipTouchHandler.this.mIsMinimized) {
                PipTouchHandler.this.mMotionHelper.mCurrentResize = 1;
                PipTouchHandler.this.mMotionHelper.expandPip();
            }
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        public void onPipMinimize() {
            PipTouchHandler.this.setMinimizedStateInternal(true);
            PipTouchHandler.this.mMotionHelper.animateToClosestMinimizedState(PipTouchHandler.this.mMovementBounds, null);
        }

        public void onPipHidden() {
            PipTouchHandler.this.resizePIP(PipTouchHandler.this.mResizeStep0Bounds, 0);
        }

        public void onPipShow() {
            PipTouchHandler.this.resizePIP(PipTouchHandler.this.getCurrentBounds(), 0);
            PipTouchHandler.this.cleanUp();
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        @SuppressLint({"NewApi"})
        public void onPipDismiss() {
            PipTouchHandler.this.mMotionHelper.mCurrentResize = 1;
            Pair<ComponentName, Integer> topPipActivityInfo = PipUtils.getTopPinnedActivity(PipTouchHandler.this.mContext, PipTouchHandler.this.mActivityManager);
            if (topPipActivityInfo.first != null) {
                MetricsLoggerWrapper.logPictureInPictureDismissByTap(PipTouchHandler.this.mContext, topPipActivityInfo);
            }
            PipTouchHandler.this.mMotionHelper.dismissPip();
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        public void onPipShowMenu() {
            PipTouchHandler.this.mMenuController.showMenu(2, PipTouchHandler.this.mMotionHelper.getBounds(), PipTouchHandler.this.mMovementBounds, true, PipTouchHandler.this.willResizeMenu());
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        public void onPipBigger() {
            int nSize = PipTouchHandler.this.mMotionHelper.mCurrentResize;
            if (PipTouchHandler.this.mOrientation != 1 || nSize != 2) {
                PipTouchHandler.this.mMotionHelper.mCurrentResize++;
                if (PipTouchHandler.this.mMotionHelper.mCurrentResize >= 4) {
                    PipTouchHandler.this.mMotionHelper.mCurrentResize = 4;
                }
                if (nSize != PipTouchHandler.this.mMotionHelper.mCurrentResize) {
                    PipTouchHandler.this.resizePIP(PipTouchHandler.this.getCurrentBounds());
                }
            }
        }

        @Override // com.android.systemui.pip.phone.PipMenuActivityController.Listener
        public void onPipSmaller() {
            int nSize = PipTouchHandler.this.mMotionHelper.mCurrentResize;
            PipTouchHandler.this.mMotionHelper.mCurrentResize--;
            if (PipTouchHandler.this.mMotionHelper.mCurrentResize < 1) {
                PipTouchHandler.this.mMotionHelper.mCurrentResize = 1;
            }
            if (nSize != PipTouchHandler.this.mMotionHelper.mCurrentResize) {
                PipTouchHandler.this.resizePIP(PipTouchHandler.this.getCurrentBounds(), 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect getCurrentBounds() {
        if (this.mResizeStepBoundsOrigin == null || this.mResizeStepBoundsOrigin.width() == 0 || this.mMotionHelper.mCurrentResize > 4 || this.mMotionHelper.mCurrentResize < 0) {
            return this.mResizeStep1Bounds;
        }
        float scale = ((this.mMotionHelper.mCurrentResize - 1) * 0.5f) + 1.0f;
        return new Rect(0, 0, (int) (this.mResizeStepBoundsOrigin.width() * scale), (int) (this.mResizeStepBoundsOrigin.height() * scale));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resizePIP(Rect toBounds) {
        resizePIP(toBounds, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resizePIP(Rect toBounds, int menuState) {
        this.mMenuState = menuState;
        updateResizeMovementBounds();
        this.mSavedSnapFraction = this.mMotionHelper.animateToExpandedState(toBounds, this.mMovementBounds, this.mExpandedMovementBounds);
    }

    private void getMovementBounds(Rect stackBounds, Rect insetBounds, Rect movementBoundsOut, int bottomOffset) {
        movementBoundsOut.set(insetBounds);
        movementBoundsOut.right = Math.max(insetBounds.left, (insetBounds.right - stackBounds.width()) + R.styleable.AppCompatTheme_toolbarNavigationButtonStyle);
        movementBoundsOut.bottom = Math.max(insetBounds.top, insetBounds.bottom - stackBounds.height());
        movementBoundsOut.bottom -= bottomOffset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResizeMovementBounds() {
        Rect bounds = new Rect();
        if (this.mMotionHelper.getBounds().width() < 264) {
            getMovementBounds(getCurrentBounds(), this.mInsetBounds, bounds, this.mIsImeShowing ? this.mImeHeight : 0);
        } else {
            this.mSnapAlgorithm.getMovementBounds(getCurrentBounds(), this.mInsetBounds, bounds, this.mIsImeShowing ? this.mImeHeight : 0);
        }
        this.mExpandedMovementBounds = bounds;
        this.mNormalMovementBounds = bounds;
        this.mMovementBounds = bounds;
    }

    public PipTouchHandler(Context context, IActivityManager activityManager, PipMenuActivityController menuController, InputConsumerController inputConsumerController) {
        this.mOrientation = -1;
        this.mContext = context;
        this.mActivityManager = activityManager;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mViewConfig = ViewConfiguration.get(context);
        this.mMenuController = menuController;
        this.mMenuController.addListener(this.mMenuListener);
        this.mDismissViewController = new PipDismissViewController(context);
        this.mSnapAlgorithm = new PipSnapAlgorithm(this.mContext);
        this.mFlingAnimationUtils = new FlingAnimationUtils(context, 2.5f);
        this.mGestures = new PipTouchGesture[]{this.mDefaultMovementGesture};
        this.mMotionHelper = new PipMotionHelper(this.mContext, this.mActivityManager, this.mMenuController, this.mSnapAlgorithm, this.mFlingAnimationUtils);
        this.mTouchState = new PipTouchState(this.mViewConfig, this.mHandler, new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipTouchHandler$Uq5M9Md512Sfgd22VAeFpot25E0
            @Override // java.lang.Runnable
            public final void run() {
                PipTouchHandler pipTouchHandler = this.f$0;
                pipTouchHandler.mMenuController.showMenu(2, pipTouchHandler.mMotionHelper.getBounds(), pipTouchHandler.mMovementBounds, true, pipTouchHandler.willResizeMenu());
            }
        });
        Resources res = context.getResources();
        this.mOrientation = res.getConfiguration().orientation;
        this.mExpandedShortestEdgeSize = res.getDimensionPixelSize(com.android.systemui.R.dimen.pip_expanded_shortest_edge_size);
        this.mImeOffset = res.getDimensionPixelSize(com.android.systemui.R.dimen.pip_ime_offset);
        this.mResizeStep0Bounds = new Rect(0, 0, 1, 1);
        this.mResizeStep1Bounds = new Rect(0, 0, res.getInteger(com.android.systemui.R.integer.pip_size1_right), res.getInteger(com.android.systemui.R.integer.pip_size1_bottom));
        this.mResizeStep2Bounds = new Rect(0, 0, res.getInteger(com.android.systemui.R.integer.pip_size2_right), res.getInteger(com.android.systemui.R.integer.pip_size2_bottom));
        this.mResizeStep3Bounds = new Rect(0, 0, res.getInteger(com.android.systemui.R.integer.pip_size3_right), res.getInteger(com.android.systemui.R.integer.pip_size3_bottom));
        this.mResizeStep4Bounds = new Rect(0, 0, res.getInteger(com.android.systemui.R.integer.pip_size4_right), res.getInteger(com.android.systemui.R.integer.pip_size4_bottom));
        inputConsumerController.setTouchListener(new InputConsumerController.TouchListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipTouchHandler$6VeR24MB4-xnOTUaDMT0CXNQVjE
            @Override // com.android.systemui.shared.system.InputConsumerController.TouchListener
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                return this.f$0.handleTouchEvent(motionEvent);
            }
        });
        inputConsumerController.setRegistrationListener(new InputConsumerController.RegistrationListener() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipTouchHandler$NVpciZTELe-GnxXPZeY5rYMmqJQ
            @Override // com.android.systemui.shared.system.InputConsumerController.RegistrationListener
            public final void onRegistrationChanged(boolean z) {
                this.f$0.onRegistrationChanged(z);
            }
        });
        onRegistrationChanged(inputConsumerController.isRegistered());
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.systemui.action.PIP_CONTROL");
        this.mContext.registerReceiver(this.receiver, filter);
    }

    public void setTouchEnabled(boolean enabled) {
        this.mTouchState.setAllowTouches(enabled);
    }

    public void showPictureInPictureMenu() {
        if (!this.mTouchState.isUserInteracting()) {
            this.mMenuController.showMenu(2, this.mMotionHelper.getBounds(), this.mMovementBounds, false, willResizeMenu());
        }
    }

    public void onActivityPinned() {
        this.isOriginBoundsInitialed = false;
        cleanUp();
        this.mShowPipMenuOnAnimationEnd = true;
    }

    public void onActivityUnpinned(ComponentName topPipActivity) {
        this.isOriginBoundsInitialed = false;
        if (topPipActivity == null) {
            cleanUp();
        }
    }

    public void onPinnedStackAnimationEnded() {
        this.mMotionHelper.synchronizePinnedStackBounds();
        if (!this.isOriginBoundsInitialed) {
            this.mResizeStepBoundsOrigin = new Rect(0, 0, this.mMotionHelper.getBounds().width(), this.mMotionHelper.getBounds().height());
            this.isOriginBoundsInitialed = true;
        }
        if (this.mShowPipMenuOnAnimationEnd) {
            this.mMenuState = 0;
            this.mShowPipMenuOnAnimationEnd = false;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        this.mMotionHelper.onConfigurationChanged();
        this.mMotionHelper.synchronizePinnedStackBounds();
        this.mOrientation = newConfig.orientation;
        if (this.mOrientation == 1 && this.mMotionHelper.mCurrentResize > 2) {
            this.mMotionHelper.mCurrentResize = 2;
            resizePIP(getCurrentBounds(), 0);
            cleanUp();
        }
    }

    public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) {
        this.mIsImeShowing = imeVisible;
        this.mImeHeight = imeHeight;
    }

    public void onShelfVisibilityChanged(boolean shelfVisible, int shelfHeight) {
        this.mIsShelfShowing = shelfVisible;
        this.mShelfHeight = shelfHeight;
    }

    public void onMovementBoundsChanged(Rect insetBounds, Rect normalBounds, Rect animatingBounds, boolean fromImeAdjustment, boolean fromShelfAdjustment, int displayRotation) {
        int bottomOffset = this.mIsImeShowing ? this.mImeHeight : 0;
        this.mNormalBounds = normalBounds;
        Rect normalMovementBounds = new Rect();
        this.mSnapAlgorithm.getMovementBounds(this.mNormalBounds, insetBounds, normalMovementBounds, bottomOffset);
        float aspectRatio = normalBounds.width() / normalBounds.height();
        Point displaySize = new Point();
        this.mContext.getDisplay().getRealSize(displaySize);
        Size expandedSize = this.mSnapAlgorithm.getSizeForAspectRatio(aspectRatio, this.mExpandedShortestEdgeSize, displaySize.x, displaySize.y);
        this.mExpandedBounds.set(0, 0, expandedSize.getWidth(), expandedSize.getHeight());
        Rect expandedMovementBounds = new Rect();
        this.mSnapAlgorithm.getMovementBounds(this.mExpandedBounds, insetBounds, expandedMovementBounds, bottomOffset);
        if ((fromImeAdjustment || fromShelfAdjustment) && !this.mTouchState.isUserInteracting()) {
            int adjustedOffset = Math.max(this.mIsImeShowing ? this.mImeHeight + this.mImeOffset : 0, this.mIsShelfShowing ? this.mShelfHeight : 0);
            Rect normalAdjustedBounds = new Rect();
            this.mSnapAlgorithm.getMovementBounds(this.mNormalBounds, insetBounds, normalAdjustedBounds, adjustedOffset);
            Rect expandedAdjustedBounds = new Rect();
            this.mSnapAlgorithm.getMovementBounds(this.mExpandedBounds, insetBounds, expandedAdjustedBounds, adjustedOffset);
            Rect toAdjustedBounds = this.mMenuState == 2 ? expandedAdjustedBounds : normalAdjustedBounds;
            Rect toMovementBounds = this.mMenuState == 2 ? expandedMovementBounds : normalMovementBounds;
            if (toAdjustedBounds.bottom < this.mMovementBounds.bottom && animatingBounds.top < toAdjustedBounds.bottom) {
                return;
            }
            int movementBoundsAdjustment = toMovementBounds.bottom - this.mMovementBounds.bottom;
            int offsetAdjustment = fromImeAdjustment ? this.mImeOffset : this.mShelfHeight;
            int i = toAdjustedBounds.bottom;
            Rect toMovementBounds2 = this.mMovementBounds;
            if (i >= toMovementBounds2.bottom && animatingBounds.top < (toAdjustedBounds.bottom - movementBoundsAdjustment) - offsetAdjustment) {
                return;
            } else {
                animateToOffset(animatingBounds, toAdjustedBounds);
            }
        }
        this.mNormalMovementBounds = normalMovementBounds;
        this.mExpandedMovementBounds = expandedMovementBounds;
        this.mDisplayRotation = displayRotation;
        this.mInsetBounds.set(insetBounds);
        updateResizeMovementBounds();
        updateMovementBounds(this.mMenuState);
        if (this.mDeferResizeToNormalBoundsUntilRotation == displayRotation) {
            this.mMotionHelper.animateToUnexpandedState(normalBounds, this.mSavedSnapFraction, this.mNormalMovementBounds, this.mMovementBounds, this.mIsMinimized, true);
            this.mSavedSnapFraction = -1.0f;
            this.mDeferResizeToNormalBoundsUntilRotation = -1;
        }
    }

    private void animateToOffset(Rect animatingBounds, Rect toAdjustedBounds) {
        Rect bounds = new Rect(animatingBounds);
        bounds.offset(0, toAdjustedBounds.bottom - bounds.top);
        bounds.offset(0, Math.max(0, this.mMovementBounds.top - bounds.top));
        this.mMotionHelper.animateToOffset(bounds);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRegistrationChanged(boolean isRegistered) {
        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection;
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (isRegistered) {
            pipAccessibilityInteractionConnection = new PipAccessibilityInteractionConnection(this.mMotionHelper, new PipAccessibilityInteractionConnection.AccessibilityCallbacks() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipTouchHandler$1nY3kLe318Fm3UtIAbDmSK80h7w
                @Override // com.android.systemui.pip.phone.PipAccessibilityInteractionConnection.AccessibilityCallbacks
                public final void onAccessibilityShowMenu() {
                    this.f$0.onAccessibilityShowMenu();
                }
            }, this.mHandler);
        } else {
            pipAccessibilityInteractionConnection = null;
        }
        accessibilityManager.setPictureInPictureActionReplacingConnection(pipAccessibilityInteractionConnection);
        if (!isRegistered && this.mTouchState.isUserInteracting()) {
            cleanUpDismissTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityShowMenu() {
        this.mMenuController.showMenu(2, this.mMotionHelper.getBounds(), this.mMovementBounds, false, willResizeMenu());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean handleTouchEvent(MotionEvent ev) {
        if (this.mPinnedStackController == null) {
            return true;
        }
        this.mTouchState.onTouchEvent(ev);
        switch (ev.getAction()) {
            case 0:
                this.mMotionHelper.synchronizePinnedStackBounds();
                for (PipTouchGesture gesture : this.mGestures) {
                    gesture.onDown(this.mTouchState);
                }
                break;
            case 1:
                updateMovementBounds(this.mMenuState);
                for (PipTouchGesture gesture2 : this.mGestures) {
                    if (gesture2.onUp(this.mTouchState)) {
                        this.mTouchState.reset();
                    }
                    break;
                }
                this.mTouchState.reset();
                break;
            case 2:
                for (PipTouchGesture gesture3 : this.mGestures) {
                    if (gesture3.onMove(this.mTouchState)) {
                    }
                    break;
                }
                break;
            case 3:
                this.mTouchState.reset();
                break;
            case 7:
            case 9:
                if (this.mAccessibilityManager.isEnabled() && !this.mSendingHoverAccessibilityEvents) {
                    AccessibilityEvent event = AccessibilityEvent.obtain(128);
                    event.setImportantForAccessibility(true);
                    event.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID);
                    event.setWindowId(-3);
                    this.mAccessibilityManager.sendAccessibilityEvent(event);
                    this.mSendingHoverAccessibilityEvents = true;
                }
                break;
            case 10:
                if (this.mAccessibilityManager.isEnabled() && this.mSendingHoverAccessibilityEvents) {
                    AccessibilityEvent event2 = AccessibilityEvent.obtain(256);
                    event2.setImportantForAccessibility(true);
                    event2.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID);
                    event2.setWindowId(-3);
                    this.mAccessibilityManager.sendAccessibilityEvent(event2);
                    this.mSendingHoverAccessibilityEvents = false;
                }
                break;
        }
        return this.mMenuState == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDismissFraction() {
        if (this.mMenuController != null && !this.mIsImeShowing) {
            Rect bounds = this.mMotionHelper.getBounds();
            float target = this.mInsetBounds.bottom;
            float fraction = 0.0f;
            if (bounds.bottom > target) {
                float distance = bounds.bottom - target;
                fraction = Math.min(distance / bounds.height(), 1.0f);
            }
            if (Float.compare(fraction, 0.0f) != 0 || this.mMenuController.isMenuActivityVisible()) {
                this.mMenuController.setDismissFraction(fraction);
            }
        }
    }

    void setPinnedStackController(IPinnedStackController controller) {
        this.mPinnedStackController = controller;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMinimizedStateInternal(boolean isMinimized) {
        setMinimizedState(isMinimized, false);
    }

    void setMinimizedState(boolean isMinimized, boolean fromController) {
        if (this.mIsMinimized != isMinimized) {
            Pair<ComponentName, Integer> topPipActivityInfo = PipUtils.getTopPinnedActivity(this.mContext, this.mActivityManager);
            if (topPipActivityInfo.first != null) {
                MetricsLoggerWrapper.logPictureInPictureMinimize(this.mContext, isMinimized, topPipActivityInfo);
            }
        }
        this.mIsMinimized = isMinimized;
        this.mSnapAlgorithm.setMinimized(isMinimized);
        if (fromController) {
            if (isMinimized) {
                this.mMotionHelper.movePip(this.mMotionHelper.getClosestMinimizedBounds(this.mNormalBounds, this.mMovementBounds));
            }
        } else if (this.mPinnedStackController != null) {
            try {
                this.mPinnedStackController.setIsMinimized(isMinimized);
            } catch (RemoteException e) {
                Log.e("PipTouchHandler", "Could not set minimized state", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMenuState(int menuState, boolean resize) {
        updateResizeMovementBounds();
        boolean z = false;
        if (menuState == 2) {
            Rect expandedBounds = new Rect(getCurrentBounds());
            if (resize) {
                this.mSavedSnapFraction = this.mMotionHelper.animateToExpandedState(expandedBounds, this.mMovementBounds, this.mExpandedMovementBounds);
            }
        } else if (menuState == 0) {
            if (resize) {
                if (this.mDeferResizeToNormalBoundsUntilRotation == -1) {
                    try {
                        int displayRotation = this.mPinnedStackController.getDisplayRotation();
                        if (this.mDisplayRotation != displayRotation) {
                            this.mDeferResizeToNormalBoundsUntilRotation = displayRotation;
                        }
                    } catch (RemoteException e) {
                        Log.e("PipTouchHandler", "Could not get display rotation from controller");
                    }
                }
                if (this.mDeferResizeToNormalBoundsUntilRotation == -1) {
                    Rect normalBounds = new Rect(getCurrentBounds());
                    this.mMotionHelper.animateToUnexpandedState(normalBounds, this.mSavedSnapFraction, this.mNormalMovementBounds, this.mMovementBounds, this.mIsMinimized, false);
                    this.mSavedSnapFraction = -1.0f;
                }
            } else {
                setTouchEnabled(false);
                this.mSavedSnapFraction = -1.0f;
            }
        }
        this.mMenuState = menuState;
        updateMovementBounds(menuState);
        if (menuState != 1) {
            Context context = this.mContext;
            if (menuState == 2) {
                z = true;
            }
            MetricsLoggerWrapper.logPictureInPictureMenuVisible(context, z);
        }
    }

    public PipMotionHelper getMotionHelper() {
        return this.mMotionHelper;
    }

    private void updateMovementBounds(int menuState) {
        Rect rect;
        boolean isMenuExpanded = menuState == 2;
        if (isMenuExpanded) {
            rect = this.mExpandedMovementBounds;
        } else {
            rect = this.mNormalMovementBounds;
        }
        this.mMovementBounds = rect;
        try {
            this.mPinnedStackController.setMinEdgeSize(isMenuExpanded ? this.mExpandedShortestEdgeSize : 0);
        } catch (RemoteException e) {
            Log.e("PipTouchHandler", "Could not set minimized state", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanUpDismissTarget() {
        this.mHandler.removeCallbacks(this.mShowDismissAffordance);
        this.mDismissViewController.destroyDismissTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanUp() {
        if (this.mIsMinimized) {
            setMinimizedStateInternal(false);
        }
        cleanUpDismissTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean willResizeMenu() {
        return (this.mExpandedBounds.width() == this.mNormalBounds.width() && this.mExpandedBounds.height() == this.mNormalBounds.height()) ? false : true;
    }

    public void dump(PrintWriter pw, String prefix) {
        String innerPrefix = prefix + "  ";
        pw.println(prefix + "PipTouchHandler");
        pw.println(innerPrefix + "mMovementBounds=" + this.mMovementBounds);
        pw.println(innerPrefix + "mNormalBounds=" + this.mNormalBounds);
        pw.println(innerPrefix + "mNormalMovementBounds=" + this.mNormalMovementBounds);
        pw.println(innerPrefix + "mExpandedBounds=" + this.mExpandedBounds);
        pw.println(innerPrefix + "mExpandedMovementBounds=" + this.mExpandedMovementBounds);
        pw.println(innerPrefix + "mMenuState=" + this.mMenuState);
        pw.println(innerPrefix + "mIsMinimized=" + this.mIsMinimized);
        pw.println(innerPrefix + "mIsImeShowing=" + this.mIsImeShowing);
        pw.println(innerPrefix + "mImeHeight=" + this.mImeHeight);
        pw.println(innerPrefix + "mIsShelfShowing=" + this.mIsShelfShowing);
        pw.println(innerPrefix + "mShelfHeight=" + this.mShelfHeight);
        pw.println(innerPrefix + "mSavedSnapFraction=" + this.mSavedSnapFraction);
        pw.println(innerPrefix + "mEnableDragToEdgeDismiss=true");
        pw.println(innerPrefix + "mEnableMinimize=false");
        this.mSnapAlgorithm.dump(pw, innerPrefix);
        this.mTouchState.dump(pw, innerPrefix);
        this.mMotionHelper.dump(pw, innerPrefix);
    }
}
