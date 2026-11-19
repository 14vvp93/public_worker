package com.android.systemui.recents.views;

import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.drawable.GradientDrawable;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsActivityLaunchState;
import com.android.systemui.recents.RecentsConfiguration;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.CancelEnterRecentsWindowAnimationEvent;
import com.android.systemui.recents.events.activity.DismissRecentsToHomeAnimationStarted;
import com.android.systemui.recents.events.activity.DockedFirstAnimationFrameEvent;
import com.android.systemui.recents.events.activity.EnterRecentsWindowAnimationCompletedEvent;
import com.android.systemui.recents.events.activity.ExitRecentsWindowFirstAnimationFrameEvent;
import com.android.systemui.recents.events.activity.HideStackActionButtonEvent;
import com.android.systemui.recents.events.activity.LaunchTaskEvent;
import com.android.systemui.recents.events.activity.LaunchTaskFailedEvent;
import com.android.systemui.recents.events.activity.LaunchTaskStartedEvent;
import com.android.systemui.recents.events.activity.LaunchTaskSucceededEvent;
import com.android.systemui.recents.events.activity.MultiWindowStateChangedEvent;
import com.android.systemui.recents.events.activity.ShowEmptyViewEvent;
import com.android.systemui.recents.events.activity.ShowStackActionButtonEvent;
import com.android.systemui.recents.events.component.ExpandPipEvent;
import com.android.systemui.recents.events.component.ScreenPinningRequestEvent;
import com.android.systemui.recents.events.component.SetWaitingForTransitionStartEvent;
import com.android.systemui.recents.events.ui.AllTaskViewsDismissedEvent;
import com.android.systemui.recents.events.ui.DismissAllTaskViewsEvent;
import com.android.systemui.recents.events.ui.DraggingInRecentsEndedEvent;
import com.android.systemui.recents.events.ui.DraggingInRecentsEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragDropTargetChangedEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragEndCancelledEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragEndEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragStartEvent;
import com.android.systemui.recents.misc.ReferenceCountedTrigger;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.TaskStack;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.recents.view.AppTransitionAnimationSpecCompat;
import com.android.systemui.shared.recents.view.AppTransitionAnimationSpecsFuture;
import com.android.systemui.shared.recents.view.RecentsTransition;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.ActivityOptionsCompat;
import com.android.systemui.shared.system.WindowManagerWrapper;
import com.android.systemui.stackdivider.WindowManagerProxy;
import com.android.systemui.statusbar.FlingAnimationUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class RecentsView extends FrameLayout {
    private boolean mAwaitingFirstLayout;
    private GradientDrawable mBackgroundScrim;
    private ValueAnimator mBackgroundScrimAnimator;
    private float mBusynessFactor;
    private int mDividerSize;
    private TextView mEmptyView;
    private final FlingAnimationUtils mFlingAnimationUtils;
    private Handler mHandler;
    private ColorDrawable mMultiWindowBackgroundScrim;
    private TextView mStackActionButton;
    private final int mStackButtonShadowColor;
    private final PointF mStackButtonShadowDistance;
    private final float mStackButtonShadowRadius;

    @ViewDebug.ExportedProperty(category = "recents")
    Rect mSystemInsets;
    private TaskStackView mTaskStackView;
    private Point mTmpDisplaySize;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "touch_")
    private RecentsViewTouchHandler mTouchHandler;
    private RecentsTransitionComposer mTransitionHelper;
    private final ValueAnimator.AnimatorUpdateListener mUpdateBackgroundScrimAlpha;

    public static /* synthetic */ void lambda$new$0(RecentsView recentsView, ValueAnimator animation) {
        int alpha = ((Integer) animation.getAnimatedValue()).intValue();
        recentsView.mBackgroundScrim.setAlpha(alpha);
        recentsView.mMultiWindowBackgroundScrim.setAlpha(alpha);
    }

    public RecentsView(Context context) {
        this(context, null);
    }

    public RecentsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RecentsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int i;
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mAwaitingFirstLayout = true;
        this.mSystemInsets = new Rect();
        this.mTmpDisplaySize = new Point();
        this.mUpdateBackgroundScrimAlpha = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.recents.views.-$$Lambda$RecentsView$6rfoH9yP_J2fW6JDlOW4RINdzy4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RecentsView.lambda$new$0(this.f$0, valueAnimator);
            }
        };
        setWillNotDraw(false);
        SystemServicesProxy ssp = Recents.getSystemServices();
        this.mHandler = new Handler();
        this.mTransitionHelper = new RecentsTransitionComposer(getContext());
        this.mDividerSize = ssp.getDockedDividerSize(context);
        this.mTouchHandler = new RecentsViewTouchHandler(this);
        this.mFlingAnimationUtils = new FlingAnimationUtils(context, 0.3f);
        this.mBackgroundScrim = new GradientDrawable(context);
        this.mMultiWindowBackgroundScrim = new ColorDrawable();
        LayoutInflater inflater = LayoutInflater.from(context);
        this.mEmptyView = (TextView) inflater.inflate(R.layout.recents_empty, (ViewGroup) this, false);
        addView(this.mEmptyView);
        if (this.mStackActionButton != null) {
            removeView(this.mStackActionButton);
        }
        Log.e("RecentsView", "isLowRamDevice:" + Recents.getConfiguration().isLowRamDevice);
        if (Recents.getConfiguration().isLowRamDevice) {
            i = R.layout.recents_low_ram_stack_action_button;
        } else {
            i = R.layout.recents_stack_action_button;
        }
        this.mStackActionButton = (TextView) inflater.inflate(i, (ViewGroup) this, false);
        this.mStackButtonShadowRadius = this.mStackActionButton.getShadowRadius();
        this.mStackButtonShadowDistance = new PointF(this.mStackActionButton.getShadowDx(), this.mStackActionButton.getShadowDy());
        this.mStackButtonShadowColor = this.mStackActionButton.getShadowColor();
        addView(this.mStackActionButton);
        reevaluateStyles();
    }

    public void reevaluateStyles() {
        int textColor = Utils.getColorAttr(this.mContext, R.attr.wallpaperTextColor);
        boolean usingDarkText = Color.luminance(textColor) < 0.5f;
        this.mEmptyView.setTextColor(textColor);
        this.mEmptyView.setCompoundDrawableTintList(new ColorStateList(new int[][]{new int[]{android.R.attr.state_enabled}}, new int[]{textColor}));
        if (this.mStackActionButton != null) {
            this.mStackActionButton.setTextColor(textColor);
            if (usingDarkText) {
                this.mStackActionButton.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            } else {
                this.mStackActionButton.setShadowLayer(this.mStackButtonShadowRadius, this.mStackButtonShadowDistance.x, this.mStackButtonShadowDistance.y, this.mStackButtonShadowColor);
            }
        }
        int systemBarsStyle = usingDarkText ? 8208 : 0;
        setSystemUiVisibility(1792 | systemBarsStyle);
    }

    public void onReload(TaskStack stack, boolean isResumingFromVisible) {
        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        boolean isTaskStackEmpty = stack.getTaskCount() == 0;
        if (this.mTaskStackView == null) {
            isResumingFromVisible = false;
            this.mTaskStackView = new TaskStackView(getContext());
            this.mTaskStackView.setSystemInsets(this.mSystemInsets);
            addView(this.mTaskStackView);
        }
        this.mAwaitingFirstLayout = !isResumingFromVisible;
        this.mTaskStackView.onReload(isResumingFromVisible);
        updateStack(stack, true);
        updateBusyness();
        if (isResumingFromVisible) {
            animateBackgroundScrim(getOpaqueScrimAlpha(), 200);
            return;
        }
        if (launchState.launchedViaDockGesture || launchState.launchedFromApp || isTaskStackEmpty) {
            this.mBackgroundScrim.setAlpha((int) (getOpaqueScrimAlpha() * 255.0f));
        } else {
            this.mBackgroundScrim.setAlpha(0);
        }
        this.mMultiWindowBackgroundScrim.setAlpha(this.mBackgroundScrim.getAlpha());
    }

    public void updateStack(TaskStack stack, boolean setStackViewTasks) {
        if (setStackViewTasks) {
            this.mTaskStackView.setTasks(stack, true);
        }
        if (stack.getTaskCount() > 0) {
            hideEmptyView();
        } else {
            showEmptyView(R.string.recents_empty_message);
        }
    }

    public void updateScrimOpacity() {
        if (updateBusyness()) {
            animateBackgroundScrim(getOpaqueScrimAlpha(), 200);
        }
    }

    private boolean updateBusyness() {
        int taskCount = this.mTaskStackView.getStack().getTaskCount();
        float busyness = Math.min(taskCount, 3) / 3.0f;
        if (this.mBusynessFactor == busyness) {
            return false;
        }
        this.mBusynessFactor = busyness;
        return true;
    }

    public TaskStack getStack() {
        return this.mTaskStackView.getStack();
    }

    public void updateBackgroundScrim(Window window, boolean isInMultiWindow) {
        if (isInMultiWindow) {
            this.mBackgroundScrim.setCallback((Drawable.Callback) null);
            window.setBackgroundDrawable(this.mMultiWindowBackgroundScrim);
        } else {
            this.mMultiWindowBackgroundScrim.setCallback(null);
            window.setBackgroundDrawable(this.mBackgroundScrim);
        }
    }

    public boolean launchFocusedTask(int logEvent) {
        Task task;
        if (this.mTaskStackView == null || (task = this.mTaskStackView.getFocusedTask()) == null) {
            return false;
        }
        TaskView taskView = this.mTaskStackView.getChildViewForTask(task);
        EventBus.getDefault().send(new LaunchTaskEvent(taskView, task, null, false));
        if (logEvent != 0) {
            MetricsLogger.action(getContext(), logEvent, task.key.getComponent().toString());
            return true;
        }
        return true;
    }

    public boolean launchPreviousTask() {
        Task task;
        if (Recents.getConfiguration().getLaunchState().launchedFromPipApp) {
            EventBus.getDefault().send(new ExpandPipEvent());
            return true;
        }
        if (this.mTaskStackView == null || (task = getStack().getLaunchTarget()) == null) {
            return false;
        }
        TaskView taskView = this.mTaskStackView.getChildViewForTask(task);
        EventBus.getDefault().send(new LaunchTaskEvent(taskView, task, null, false));
        return true;
    }

    public void showEmptyView(int msgResId) {
        this.mTaskStackView.setVisibility(4);
        this.mEmptyView.setText(msgResId);
        this.mEmptyView.setVisibility(0);
        this.mEmptyView.bringToFront();
        this.mStackActionButton.bringToFront();
    }

    public void hideEmptyView() {
        this.mEmptyView.setVisibility(4);
        this.mTaskStackView.setVisibility(0);
        this.mTaskStackView.bringToFront();
        this.mStackActionButton.bringToFront();
    }

    public void setScrimColors(ColorExtractor.GradientColors scrimColors, boolean animated) {
        this.mBackgroundScrim.setColors(scrimColors, animated);
        int alpha = this.mMultiWindowBackgroundScrim.getAlpha();
        this.mMultiWindowBackgroundScrim.setColor(scrimColors.getMainColor());
        this.mMultiWindowBackgroundScrim.setAlpha(alpha);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        EventBus.getDefault().register(this, 3);
        EventBus.getDefault().register(this.mTouchHandler, 4);
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(this.mTouchHandler);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        if (!hasNaviBarHeight()) {
            if (width == 1280 && height == 600) {
                height = 600;
            } else if (width == 1280 && height != 720) {
                height = 720;
            }
            if (width == 720 && height != 1280) {
                height = 1280;
            }
            if (getContext().getDisplay().getHeight() == 480) {
                height = 480;
            }
            if (getContext().getDisplay().getHeight() == 452) {
                height = 452;
            }
        }
        Log.e("RecentsView", "onMeasure---width:" + width + "---height:" + height);
        if (this.mTaskStackView.getVisibility() != 8) {
            this.mTaskStackView.measure(View.MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
        }
        if (this.mEmptyView.getVisibility() != 8) {
            measureChild(this.mEmptyView, View.MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
        }
        Rect buttonBounds = this.mTaskStackView.mLayoutAlgorithm.getStackActionButtonRect();
        measureChild(this.mStackActionButton, View.MeasureSpec.makeMeasureSpec(buttonBounds.width(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(buttonBounds.height(), Integer.MIN_VALUE));
        Log.e("RecentsView", "onMeasure-mStackActionButton---width:" + buttonBounds.width() + "---height:" + buttonBounds.height());
        setMeasuredDimension(width, height);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mTaskStackView.getVisibility() != 8) {
            this.mTaskStackView.layout(left, top, getMeasuredWidth() + left, getMeasuredHeight() + top);
        }
        if (this.mEmptyView.getVisibility() != 8) {
            int leftRightInsets = this.mSystemInsets.left + this.mSystemInsets.right;
            int topBottomInsets = this.mSystemInsets.top + this.mSystemInsets.bottom;
            int childWidth = this.mEmptyView.getMeasuredWidth();
            int childHeight = this.mEmptyView.getMeasuredHeight();
            int childLeft = this.mSystemInsets.left + left + (Math.max(0, ((right - left) - leftRightInsets) - childWidth) / 2);
            int childTop = this.mSystemInsets.top + top + (Math.max(0, ((bottom - top) - topBottomInsets) - childHeight) / 2);
            this.mEmptyView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
        this.mContext.getDisplay().getRealSize(this.mTmpDisplaySize);
        this.mBackgroundScrim.setScreenSize(this.mTmpDisplaySize.x, this.mTmpDisplaySize.y);
        int overscanning = SystemProperties.getInt("ro.debug.overscanning", 0);
        Log.v("RecentsView", "onLayout overscanning:" + overscanning);
        if (overscanning == 1) {
            this.mBackgroundScrim.setBounds(0, 0, this.mTmpDisplaySize.x, this.mTmpDisplaySize.y);
        } else {
            this.mBackgroundScrim.setBounds(left, top, right, bottom);
        }
        this.mMultiWindowBackgroundScrim.setBounds(0, 0, this.mTmpDisplaySize.x, this.mTmpDisplaySize.y);
        Rect buttonBounds = getStackActionButtonBoundsFromStackLayout();
        this.mStackActionButton.layout(buttonBounds.left, buttonBounds.top, buttonBounds.right, buttonBounds.bottom);
        Log.e("RecentsView", "onLayout---buttonBounds.left:" + buttonBounds.left + "---top:" + buttonBounds.top + "---right:" + buttonBounds.right + "---bottom:" + buttonBounds.bottom);
        if (this.mAwaitingFirstLayout) {
            this.mAwaitingFirstLayout = false;
            RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
            if (launchState.launchedViaDragGesture) {
                setTranslationY(getMeasuredHeight());
            } else {
                setTranslationY(0.0f);
            }
            if (Recents.getConfiguration().isLowRamDevice && this.mEmptyView.getVisibility() == 0) {
                animateEmptyView(true, null);
            }
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        this.mSystemInsets.set(insets.getSystemWindowInsets());
        this.mTaskStackView.setSystemInsets(this.mSystemInsets);
        requestLayout();
        return insets;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.mTouchHandler.onInterceptTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return this.mTouchHandler.onTouchEvent(ev);
    }

    @Override // android.view.View
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        ArrayList<DockState> visDockStates = this.mTouchHandler.getVisibleDockStates();
        for (int i = visDockStates.size() - 1; i >= 0; i--) {
            visDockStates.get(i).viewState.draw(canvas);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable who) {
        ArrayList<DockState> visDockStates = this.mTouchHandler.getVisibleDockStates();
        for (int i = visDockStates.size() - 1; i >= 0; i--) {
            Drawable d = visDockStates.get(i).viewState.dockAreaOverlay;
            if (d == who) {
                return true;
            }
        }
        return super.verifyDrawable(who);
    }

    private boolean hasNaviBarHeight() {
        boolean hasNaviBar = false;
        int naviBar = SystemProperties.getInt("sys.disp.forceshownavbar", 0);
        int height = SystemProperties.getInt("ro.sys.customer.naviheight", 0);
        if (naviBar > 0 && height > 0) {
            hasNaviBar = true;
        }
        Log.e("RecentsView", "system ui has naviBar:" + hasNaviBar);
        return hasNaviBar;
    }

    public final void onBusEvent(LaunchTaskEvent event) {
        launchTaskFromRecents(getStack(), event.task, this.mTaskStackView, event.taskView, event.screenPinningRequested, event.targetWindowingMode, event.targetActivityType);
        if (Recents.getConfiguration().isLowRamDevice) {
            EventBus.getDefault().send(new HideStackActionButtonEvent(false));
        }
    }

    public final void onBusEvent(DismissRecentsToHomeAnimationStarted event) {
        EventBus.getDefault().send(new HideStackActionButtonEvent());
        animateBackgroundScrim(0.0f, 200);
        if (Recents.getConfiguration().isLowRamDevice) {
            animateEmptyView(false, event.getAnimationTrigger());
        }
    }

    public final void onBusEvent(DragStartEvent event) {
        updateVisibleDockRegions(Recents.getConfiguration().getDockStatesForCurrentOrientation(), true, DockState.NONE.viewState.dockAreaAlpha, DockState.NONE.viewState.hintTextAlpha, true, false);
        if (this.mStackActionButton != null) {
            this.mStackActionButton.animate().alpha(0.0f).setDuration(100L).setInterpolator(Interpolators.ALPHA_OUT).start();
        }
    }

    public final void onBusEvent(DragDropTargetChangedEvent event) {
        if (event.dropTarget == null || !(event.dropTarget instanceof DockState)) {
            updateVisibleDockRegions(Recents.getConfiguration().getDockStatesForCurrentOrientation(), true, DockState.NONE.viewState.dockAreaAlpha, DockState.NONE.viewState.hintTextAlpha, true, true);
        } else {
            DockState dockState = (DockState) event.dropTarget;
            updateVisibleDockRegions(new DockState[]{dockState}, false, -1, -1, true, true);
        }
        if (this.mStackActionButton != null) {
            event.addPostAnimationCallback(new Runnable() { // from class: com.android.systemui.recents.views.RecentsView.1
                @Override // java.lang.Runnable
                public void run() {
                    Rect buttonBounds = RecentsView.this.getStackActionButtonBoundsFromStackLayout();
                    RecentsView.this.mStackActionButton.setLeftTopRightBottom(buttonBounds.left, buttonBounds.top, buttonBounds.right, buttonBounds.bottom);
                }
            });
        }
    }

    public final void onBusEvent(final DragEndEvent event) {
        if (event.dropTarget instanceof DockState) {
            DockState dockState = (DockState) event.dropTarget;
            updateVisibleDockRegions(null, false, -1, -1, false, false);
            Utilities.setViewFrameFromTranslation(event.taskView);
            ActivityOptions options = ActivityOptionsCompat.makeSplitScreenOptions(dockState.createMode == 0);
            if (ActivityManagerWrapper.getInstance().startActivityFromRecents(event.task.key.id, options)) {
                ActivityManagerWrapper.currentDockActivity = event.task.key.getComponent().getClassName();
                Log.d("XYG", "DragEndEvent startActivity " + event.task.key.id + "/" + event.task.key.getComponent().getClassName());
                Runnable animStartedListener = new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$RecentsView$RRL6yVNHxRLA7npjCgaGmNF62Mc
                    @Override // java.lang.Runnable
                    public final void run() {
                        RecentsView.lambda$onBusEvent$1(this.f$0, event);
                    }
                };
                final Rect taskRect = getTaskRect(event.taskView);
                AppTransitionAnimationSpecsFuture future = new AppTransitionAnimationSpecsFuture(getHandler()) { // from class: com.android.systemui.recents.views.RecentsView.2
                    @Override // com.android.systemui.shared.recents.view.AppTransitionAnimationSpecsFuture
                    public List<AppTransitionAnimationSpecCompat> composeSpecs() {
                        return RecentsView.this.mTransitionHelper.composeDockAnimationSpec(event.taskView, taskRect);
                    }
                };
                WindowManagerWrapper.getInstance().overridePendingAppTransitionMultiThumbFuture(future, animStartedListener, getHandler(), true);
                MetricsLogger.action(this.mContext, 270, event.task.getTopComponent().flattenToShortString());
            } else {
                EventBus.getDefault().send(new DragEndCancelledEvent(getStack(), event.task, event.taskView));
            }
        } else {
            updateVisibleDockRegions(null, true, -1, -1, true, false);
        }
        if (this.mStackActionButton != null) {
            this.mStackActionButton.animate().alpha(1.0f).setDuration(134L).setInterpolator(Interpolators.ALPHA_IN).start();
        }
    }

    public static /* synthetic */ void lambda$onBusEvent$1(RecentsView recentsView, DragEndEvent event) {
        EventBus.getDefault().send(new DockedFirstAnimationFrameEvent());
        recentsView.getStack().removeTask(event.task, null, true);
        Log.d("XYG", "DragEndEvent removeTask" + event.task.key.id + "/" + event.task.key.getComponent().getPackageName());
    }

    public final void onBusEvent(DragEndCancelledEvent event) {
        updateVisibleDockRegions(null, true, -1, -1, true, false);
    }

    private Rect getTaskRect(TaskView taskView) {
        int[] location = taskView.getLocationOnScreen();
        int viewX = location[0];
        int viewY = location[1];
        return new Rect(viewX, viewY, (int) (viewX + (taskView.getWidth() * taskView.getScaleX())), (int) (viewY + (taskView.getHeight() * taskView.getScaleY())));
    }

    public final void onBusEvent(DraggingInRecentsEvent event) {
        if (this.mTaskStackView.getTaskViews().size() > 0) {
            setTranslationY(event.distanceFromTop - this.mTaskStackView.getTaskViews().get(0).getY());
        }
    }

    public final void onBusEvent(DraggingInRecentsEndedEvent event) {
        ViewPropertyAnimator animator = animate();
        if (event.velocity > this.mFlingAnimationUtils.getMinVelocityPxPerSecond()) {
            animator.translationY(getHeight());
            animator.withEndAction(new Runnable() { // from class: com.android.systemui.recents.views.RecentsView.3
                @Override // java.lang.Runnable
                public void run() {
                    WindowManagerProxy.getInstance().maximizeDockedStack();
                }
            });
            this.mFlingAnimationUtils.apply(animator, getTranslationY(), getHeight(), event.velocity);
        } else {
            animator.translationY(0.0f);
            animator.setListener(null);
            this.mFlingAnimationUtils.apply(animator, getTranslationY(), 0.0f, event.velocity);
        }
        animator.start();
    }

    public final void onBusEvent(EnterRecentsWindowAnimationCompletedEvent event) {
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        if (!launchState.launchedViaDockGesture && !launchState.launchedFromApp && getStack().getTaskCount() > 0) {
            animateBackgroundScrim(getOpaqueScrimAlpha(), 300);
        }
    }

    public final void onBusEvent(AllTaskViewsDismissedEvent event) {
        EventBus.getDefault().send(new HideStackActionButtonEvent());
    }

    public final void onBusEvent(DismissAllTaskViewsEvent event) {
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (!ssp.hasDockedTask()) {
            animateBackgroundScrim(0.0f, 200);
        }
    }

    public final void onBusEvent(ShowStackActionButtonEvent event) {
        showStackActionButton(134, event.translate);
    }

    public final void onBusEvent(HideStackActionButtonEvent event) {
        hideStackActionButton(100, true);
    }

    public final void onBusEvent(MultiWindowStateChangedEvent event) {
        updateStack(event.stack, false);
    }

    public final void onBusEvent(ShowEmptyViewEvent event) {
        showEmptyView(R.string.recents_empty_message);
    }

    private void showStackActionButton(final int duration, final boolean translate) {
        ReferenceCountedTrigger postAnimationTrigger = new ReferenceCountedTrigger();
        if (this.mStackActionButton.getVisibility() == 4) {
            this.mStackActionButton.setVisibility(0);
            this.mStackActionButton.setAlpha(0.0f);
            if (translate) {
                this.mStackActionButton.setTranslationY(this.mStackActionButton.getMeasuredHeight() * (Recents.getConfiguration().isLowRamDevice ? 1.0f : -0.25f));
            } else {
                this.mStackActionButton.setTranslationY(0.0f);
            }
            postAnimationTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.RecentsView.4
                @Override // java.lang.Runnable
                public void run() {
                    if (translate) {
                        RecentsView.this.mStackActionButton.animate().translationY(0.0f);
                    }
                    RecentsView.this.mStackActionButton.animate().alpha(1.0f).setDuration(duration).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).start();
                }
            });
        }
        postAnimationTrigger.flushLastDecrementRunnables();
    }

    private void hideStackActionButton(int duration, boolean translate) {
        ReferenceCountedTrigger postAnimationTrigger = new ReferenceCountedTrigger();
        hideStackActionButton(duration, translate, postAnimationTrigger);
        postAnimationTrigger.flushLastDecrementRunnables();
    }

    private void hideStackActionButton(int duration, boolean translate, final ReferenceCountedTrigger postAnimationTrigger) {
        if (this.mStackActionButton.getVisibility() == 0) {
            if (translate) {
                this.mStackActionButton.animate().translationY(this.mStackActionButton.getMeasuredHeight() * (Recents.getConfiguration().isLowRamDevice ? 1.0f : -0.25f));
            }
            this.mStackActionButton.animate().alpha(0.0f).setDuration(duration).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).withEndAction(new Runnable() { // from class: com.android.systemui.recents.views.RecentsView.5
                @Override // java.lang.Runnable
                public void run() {
                    RecentsView.this.mStackActionButton.setVisibility(4);
                    postAnimationTrigger.decrement();
                }
            }).start();
            postAnimationTrigger.increment();
        }
    }

    private void animateEmptyView(boolean show, ReferenceCountedTrigger postAnimationTrigger) {
        float start = this.mTaskStackView.getStackAlgorithm().getTaskRect().height() / 4;
        this.mEmptyView.setTranslationY(show ? start : 0.0f);
        this.mEmptyView.setAlpha(show ? 0.0f : 1.0f);
        ViewPropertyAnimator animator = this.mEmptyView.animate().setDuration(150L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).translationY(show ? 0.0f : start).alpha(show ? 1.0f : 0.0f);
        if (postAnimationTrigger != null) {
            animator.setListener(postAnimationTrigger.decrementOnAnimationEnd());
            postAnimationTrigger.increment();
        }
        animator.start();
    }

    private void updateVisibleDockRegions(DockState[] newDockStates, boolean isDefaultDockState, int overrideAreaAlpha, int overrideHintAlpha, boolean animateAlpha, boolean animateBounds) {
        Rect bounds;
        ArraySet<DockState> newDockStatesSet = Utilities.arrayToSet(newDockStates, new ArraySet());
        ArrayList<DockState> visDockStates = this.mTouchHandler.getVisibleDockStates();
        for (int i = visDockStates.size() - 1; i >= 0; i--) {
            DockState dockState = visDockStates.get(i);
            DockState.ViewState viewState = dockState.viewState;
            if (newDockStates == null || !newDockStatesSet.contains(dockState)) {
                viewState.startAnimation(null, 0, 0, 250, Interpolators.FAST_OUT_SLOW_IN, animateAlpha, animateBounds);
            } else {
                int areaAlpha = overrideAreaAlpha != -1 ? overrideAreaAlpha : viewState.dockAreaAlpha;
                int hintAlpha = overrideHintAlpha != -1 ? overrideHintAlpha : viewState.hintTextAlpha;
                if (isDefaultDockState) {
                    bounds = dockState.getPreDockedBounds(getMeasuredWidth(), getMeasuredHeight(), this.mSystemInsets);
                } else {
                    bounds = dockState.getDockedBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDividerSize, this.mSystemInsets, getResources());
                }
                if (viewState.dockAreaOverlay.getCallback() != this) {
                    viewState.dockAreaOverlay.setCallback(this);
                    viewState.dockAreaOverlay.setBounds(bounds);
                }
                viewState.startAnimation(bounds, areaAlpha, hintAlpha, 250, Interpolators.FAST_OUT_SLOW_IN, animateAlpha, animateBounds);
            }
        }
    }

    private float getOpaqueScrimAlpha() {
        return MathUtils.map(0.0f, 1.0f, 0.45f, 0.7f, this.mBusynessFactor);
    }

    private void animateBackgroundScrim(float alpha, int duration) {
        Interpolator interpolator;
        Utilities.cancelAnimationWithoutCallbacks(this.mBackgroundScrimAnimator);
        int fromAlpha = this.mBackgroundScrim.getAlpha();
        int toAlpha = (int) (255.0f * alpha);
        this.mBackgroundScrimAnimator = ValueAnimator.ofInt(fromAlpha, toAlpha);
        this.mBackgroundScrimAnimator.setDuration(duration);
        ValueAnimator valueAnimator = this.mBackgroundScrimAnimator;
        if (toAlpha > fromAlpha) {
            interpolator = Interpolators.ALPHA_IN;
        } else {
            interpolator = Interpolators.ALPHA_OUT;
        }
        valueAnimator.setInterpolator(interpolator);
        this.mBackgroundScrimAnimator.addUpdateListener(this.mUpdateBackgroundScrimAlpha);
        this.mBackgroundScrimAnimator.start();
    }

    Rect getStackActionButtonBoundsFromStackLayout() {
        int left;
        int top;
        int paddingLeft;
        int top2;
        Rect actionButtonRect = new Rect(this.mTaskStackView.mLayoutAlgorithm.getStackActionButtonRect());
        if (Recents.getConfiguration().isLowRamDevice) {
            Rect windowRect = Recents.getSystemServices().getWindowRect();
            int spaceLeft = (windowRect.width() - this.mSystemInsets.left) - this.mSystemInsets.right;
            if (SystemUIApplication.hasBottomBar()) {
                left = ((actionButtonRect.left - this.mStackActionButton.getPaddingLeft()) - this.mStackActionButton.getMeasuredWidth()) - 50;
                top2 = getMeasuredHeight() - (actionButtonRect.height() * 3);
            } else {
                left = ((spaceLeft - this.mStackActionButton.getMeasuredWidth()) / 2) + this.mSystemInsets.left;
                top2 = windowRect.height() - ((this.mStackActionButton.getMeasuredHeight() + this.mSystemInsets.bottom) + (this.mStackActionButton.getPaddingBottom() / 2));
            }
            top = top2;
        } else if (SystemUIApplication.hasBottomBar()) {
            left = ((actionButtonRect.left - this.mStackActionButton.getPaddingLeft()) - this.mStackActionButton.getMeasuredWidth()) - 50;
            top = getMeasuredHeight() - (actionButtonRect.height() * 3);
        } else if (SystemUIApplication.isPortraitDevice() || SystemUIApplication.isPortraitWindowMode(this.mContext)) {
            left = (getMeasuredWidth() - this.mStackActionButton.getMeasuredWidth()) / 2;
            top = getMeasuredHeight() - ((actionButtonRect.height() * 12) / 5);
        } else {
            if (isLayoutRtl()) {
                paddingLeft = actionButtonRect.right + this.mStackActionButton.getPaddingRight() + 50;
            } else {
                paddingLeft = ((actionButtonRect.left - this.mStackActionButton.getPaddingLeft()) - this.mStackActionButton.getMeasuredWidth()) - 50;
            }
            left = paddingLeft;
            top = getMeasuredHeight() - (actionButtonRect.height() * 2);
        }
        actionButtonRect.set(left, top, this.mStackActionButton.getMeasuredWidth() + left, this.mStackActionButton.getMeasuredHeight() + top);
        Log.e("RecentsView", "getStackActionButtonBoundsFromStackLayout---left:" + left + "---top:" + top + "---right:" + left + this.mStackActionButton.getMeasuredWidth() + "---bottom:" + top + this.mStackActionButton.getMeasuredHeight());
        return actionButtonRect;
    }

    View getStackActionButton() {
        return this.mStackActionButton;
    }

    public void launchTaskFromRecents(TaskStack stack, final Task task, final TaskStackView stackView, TaskView taskView, boolean screenPinningRequested, final int windowingMode, final int activityType) {
        AppTransitionAnimationSpecsFuture transitionFuture;
        Runnable animStartedListener;
        AppTransitionAnimationSpecsFuture appTransitionAnimationSpecsFuture;
        if (taskView != null) {
            final Rect windowRect = Recents.getSystemServices().getWindowRect();
            transitionFuture = new AppTransitionAnimationSpecsFuture(stackView.getHandler()) { // from class: com.android.systemui.recents.views.RecentsView.6
                @Override // com.android.systemui.shared.recents.view.AppTransitionAnimationSpecsFuture
                public List<AppTransitionAnimationSpecCompat> composeSpecs() {
                    return RecentsView.this.mTransitionHelper.composeAnimationSpecs(task, stackView, windowingMode, activityType, windowRect);
                }
            };
            animStartedListener = new AnonymousClass7(task, stackView, screenPinningRequested);
        } else {
            transitionFuture = null;
            animStartedListener = new Runnable() { // from class: com.android.systemui.recents.views.RecentsView.8
                private boolean mHandled;

                @Override // java.lang.Runnable
                public void run() {
                    if (this.mHandled) {
                        return;
                    }
                    this.mHandled = true;
                    EventBus.getDefault().send(new CancelEnterRecentsWindowAnimationEvent(task));
                    EventBus.getDefault().send(new ExitRecentsWindowFirstAnimationFrameEvent());
                    stackView.cancelAllTaskViewAnimations();
                    if (!Recents.getConfiguration().isLowRamDevice) {
                        EventBus.getDefault().send(new SetWaitingForTransitionStartEvent(false));
                    }
                }
            };
        }
        AppTransitionAnimationSpecsFuture transitionFuture2 = transitionFuture;
        Runnable animStartedListener2 = animStartedListener;
        EventBus.getDefault().send(new SetWaitingForTransitionStartEvent(true));
        Context context = this.mContext;
        Handler handler = this.mHandler;
        if (transitionFuture2 != null) {
            appTransitionAnimationSpecsFuture = transitionFuture2;
        } else {
            appTransitionAnimationSpecsFuture = null;
        }
        ActivityOptions opts = RecentsTransition.createAspectScaleAnimation(context, handler, true, appTransitionAnimationSpecsFuture, animStartedListener2);
        if (taskView == null) {
            startTaskActivity(stack, task, taskView, opts, transitionFuture2, windowingMode, activityType);
        } else {
            LaunchTaskStartedEvent launchStartedEvent = new LaunchTaskStartedEvent(taskView, screenPinningRequested);
            EventBus.getDefault().send(launchStartedEvent);
            startTaskActivity(stack, task, taskView, opts, transitionFuture2, windowingMode, activityType);
        }
        ActivityManagerWrapper.getInstance().closeSystemWindows("recentapps");
    }

    /* JADX INFO: renamed from: com.android.systemui.recents.views.RecentsView$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {
        private boolean mHandled;
        final /* synthetic */ boolean val$screenPinningRequested;
        final /* synthetic */ TaskStackView val$stackView;
        final /* synthetic */ Task val$task;

        AnonymousClass7(Task task, TaskStackView taskStackView, boolean z) {
            this.val$task = task;
            this.val$stackView = taskStackView;
            this.val$screenPinningRequested = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mHandled) {
                return;
            }
            this.mHandled = true;
            EventBus.getDefault().send(new CancelEnterRecentsWindowAnimationEvent(this.val$task));
            EventBus.getDefault().send(new ExitRecentsWindowFirstAnimationFrameEvent());
            this.val$stackView.cancelAllTaskViewAnimations();
            if (this.val$screenPinningRequested) {
                Handler handler = RecentsView.this.mHandler;
                final Task task = this.val$task;
                handler.postDelayed(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$RecentsView$7$6nm4p5eX5UvPb6uaSaO8OVjwZdU
                    @Override // java.lang.Runnable
                    public final void run() {
                        EventBus.getDefault().send(new ScreenPinningRequestEvent(RecentsView.this.mContext, task.key.id));
                    }
                }, 350L);
            }
            if (!Recents.getConfiguration().isLowRamDevice) {
                EventBus.getDefault().send(new SetWaitingForTransitionStartEvent(false));
            }
        }
    }

    private void startTaskActivity(final TaskStack stack, final Task task, final TaskView taskView, ActivityOptions opts, final AppTransitionAnimationSpecsFuture transitionFuture, int windowingMode, int activityType) {
        ActivityManagerWrapper.getInstance().startActivityFromRecentsAsync(task.key, opts, windowingMode, activityType, new Consumer() { // from class: com.android.systemui.recents.views.-$$Lambda$RecentsView$Izp2qzQsBKZyynkPkjmVQGpzmLA
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RecentsView.lambda$startTaskActivity$2(this.f$0, stack, task, taskView, (Boolean) obj);
            }
        }, getHandler());
        if (transitionFuture != null) {
            Handler handler = this.mHandler;
            Objects.requireNonNull(transitionFuture);
            handler.post(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$2_yYbS189Yb53TwKAnkQBhUWOR4
                @Override // java.lang.Runnable
                public final void run() {
                    transitionFuture.composeSpecsSynchronous();
                }
            });
        }
    }

    public static /* synthetic */ void lambda$startTaskActivity$2(RecentsView recentsView, TaskStack stack, Task task, TaskView taskView, Boolean succeeded) {
        if (succeeded.booleanValue()) {
            int taskIndexFromFront = 0;
            int taskIndex = stack.indexOfTask(task);
            if (taskIndex > -1) {
                taskIndexFromFront = (stack.getTaskCount() - taskIndex) - 1;
            }
            EventBus.getDefault().send(new LaunchTaskSucceededEvent(taskIndexFromFront));
            return;
        }
        Log.e("RecentsView", recentsView.mContext.getString(R.string.recents_launch_error_message, task.title));
        if (taskView != null) {
            taskView.dismissTask();
        }
        EventBus.getDefault().send(new LaunchTaskFailedEvent());
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        this.mTouchHandler.cancelStackActionButtonClick();
    }

    public void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        String id = Integer.toHexString(System.identityHashCode(this));
        writer.print(prefix);
        writer.print("RecentsView");
        writer.print(" awaitingFirstLayout=");
        writer.print(this.mAwaitingFirstLayout ? "Y" : "N");
        writer.print(" insets=");
        writer.print(Utilities.dumpRect(this.mSystemInsets));
        writer.print(" [0x");
        writer.print(id);
        writer.print("]");
        writer.println();
        if (getStack() != null) {
            getStack().dump(innerPrefix, writer);
        }
        if (this.mTaskStackView != null) {
            this.mTaskStackView.dump(innerPrefix, writer);
        }
    }
}
