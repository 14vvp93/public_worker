package com.android.systemui.recents.views;

import android.app.ActivityManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.ConfigurationChangedEvent;
import com.android.systemui.recents.events.activity.HideRecentsEvent;
import com.android.systemui.recents.events.ui.DismissAllTaskViewsEvent;
import com.android.systemui.recents.events.ui.HideIncompatibleAppOverlayEvent;
import com.android.systemui.recents.events.ui.ShowIncompatibleAppOverlayEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragDropTargetChangedEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragEndEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragStartEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragStartInitializeDropTargetsEvent;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.shared.recents.model.Task;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class RecentsViewTouchHandler {
    private DividerSnapAlgorithm mDividerSnapAlgorithm;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mDragRequested;
    private float mDragSlop;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "drag_task")
    private Task mDragTask;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mIsDragging;
    private DropTarget mLastDropTarget;
    private RecentsView mRv;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "drag_task_view_")
    private TaskView mTaskView;

    @ViewDebug.ExportedProperty(category = "recents")
    private Point mTaskViewOffset = new Point();

    @ViewDebug.ExportedProperty(category = "recents")
    private Point mDownPos = new Point();
    private int mDeviceId = -1;
    private ArrayList<DropTarget> mDropTargets = new ArrayList<>();
    private ArrayList<DockState> mVisibleDockStates = new ArrayList<>();

    public RecentsViewTouchHandler(RecentsView rv) {
        this.mRv = rv;
        this.mDragSlop = ViewConfiguration.get(rv.getContext()).getScaledTouchSlop();
        updateSnapAlgorithm();
    }

    private void updateSnapAlgorithm() {
        Rect insets = new Rect();
        SystemServicesProxy.getInstance(this.mRv.getContext()).getStableInsets(insets);
        this.mDividerSnapAlgorithm = DividerSnapAlgorithm.create(this.mRv.getContext(), insets);
    }

    public void registerDropTargetForCurrentDrag(DropTarget target) {
        this.mDropTargets.add(target);
    }

    public ArrayList<DockState> getVisibleDockStates() {
        return this.mVisibleDockStates;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return handleTouchEvent(ev) || this.mDragRequested;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        handleTouchEvent(ev);
        if (ev.getAction() == 1 && this.mRv.getStack().getTaskCount() == 0) {
            EventBus.getDefault().send(new HideRecentsEvent(false, true));
        }
        return true;
    }

    public final void onBusEvent(DragStartEvent event) {
        InputDevice device;
        SystemServicesProxy ssp = Recents.getSystemServices();
        this.mRv.getParent().requestDisallowInterceptTouchEvent(true);
        this.mDragRequested = true;
        this.mIsDragging = false;
        this.mDragTask = event.task;
        this.mTaskView = event.taskView;
        this.mDropTargets.clear();
        int[] recentsViewLocation = new int[2];
        this.mRv.getLocationInWindow(recentsViewLocation);
        this.mTaskViewOffset.set((this.mTaskView.getLeft() - recentsViewLocation[0]) + event.tlOffset.x, (this.mTaskView.getTop() - recentsViewLocation[1]) + event.tlOffset.y);
        if (event.isUserTouchInitiated) {
            float x = this.mDownPos.x - this.mTaskViewOffset.x;
            float y = this.mDownPos.y - this.mTaskViewOffset.y;
            this.mTaskView.setTranslationX(x);
            this.mTaskView.setTranslationY(y);
        }
        this.mVisibleDockStates.clear();
        if (ActivityManager.supportsMultiWindow(this.mRv.getContext()) && !ssp.hasDockedTask() && this.mDividerSnapAlgorithm.isSplitScreenFeasible() && 1 == SystemProperties.getInt("persist.sys.splitscreen", 1)) {
            Recents.logDockAttempt(this.mRv.getContext(), event.task.getTopComponent(), event.task.resizeMode);
            if (!event.task.isDockable) {
                Log.d("XYG", "task not dockable");
                EventBus.getDefault().send(new ShowIncompatibleAppOverlayEvent());
            } else {
                Log.d("XYG", "task is dockable, startDragEvent");
                DockState[] dockStates = Recents.getConfiguration().getDockStatesForCurrentOrientation();
                for (DockState dockState : dockStates) {
                    registerDropTargetForCurrentDrag(dockState);
                    dockState.update(this.mRv.getContext());
                    this.mVisibleDockStates.add(dockState);
                }
            }
        }
        EventBus.getDefault().send(new DragStartInitializeDropTargetsEvent(event.task, event.taskView, this));
        if (this.mDeviceId != -1 && (device = InputDevice.getDevice(this.mDeviceId)) != null) {
            device.setPointerType(1021);
        }
    }

    public final void onBusEvent(DragEndEvent event) {
        if (!this.mDragTask.isDockable) {
            EventBus.getDefault().send(new HideIncompatibleAppOverlayEvent());
        }
        this.mDragRequested = false;
        this.mDragTask = null;
        this.mTaskView = null;
        this.mLastDropTarget = null;
    }

    public final void onBusEvent(ConfigurationChangedEvent event) {
        if (event.fromDisplayDensityChange || event.fromDeviceOrientationChange) {
            updateSnapAlgorithm();
        }
    }

    void cancelStackActionButtonClick() {
        this.mRv.getStackActionButton().setPressed(false);
    }

    private boolean isWithinStackActionButton(float x, float y) {
        Rect rect = this.mRv.getStackActionButtonBoundsFromStackLayout();
        return this.mRv.getStackActionButton().getVisibility() == 0 && this.mRv.getStackActionButton().pointInView(x - ((float) rect.left), y - ((float) rect.top), 0.0f);
    }

    private void changeStackActionButtonDrawableHotspot(float x, float y) {
        Rect rect = this.mRv.getStackActionButtonBoundsFromStackLayout();
        this.mRv.getStackActionButton().drawableHotspotChanged(x - rect.left, y - rect.top);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean handleTouchEvent(MotionEvent ev) {
        boolean cancelled;
        int action = ev.getActionMasked();
        boolean consumed = false;
        float evX = ev.getX();
        float evY = ev.getY();
        switch (action) {
            case 0:
                Log.d("XYG", "ACION DOWN");
                this.mDownPos.set((int) evX, (int) evY);
                this.mDeviceId = ev.getDeviceId();
                if (isWithinStackActionButton(evX, evY)) {
                    changeStackActionButtonDrawableHotspot(evX, evY);
                    this.mRv.getStackActionButton().setPressed(true);
                }
                return consumed;
            case 1:
            case 3:
                if (this.mRv.getStackActionButton().isPressed() && isWithinStackActionButton(evX, evY)) {
                    EventBus.getDefault().send(new DismissAllTaskViewsEvent());
                    consumed = true;
                }
                cancelStackActionButtonClick();
                if (this.mDragRequested) {
                    cancelled = action == 3;
                    if (cancelled) {
                        EventBus.getDefault().send(new DragDropTargetChangedEvent(this.mDragTask, null));
                    }
                    Log.d("XYG", "ACION UP");
                    EventBus.getDefault().send(new DragEndEvent(this.mDragTask, this.mTaskView, cancelled ? null : this.mLastDropTarget));
                } else {
                    this.mDeviceId = -1;
                }
                return consumed;
            case 2:
                float x = evX - this.mTaskViewOffset.x;
                float y = evY - this.mTaskViewOffset.y;
                if (this.mRv.getStackActionButton().isPressed() && isWithinStackActionButton(evX, evY)) {
                    changeStackActionButtonDrawableHotspot(evX, evY);
                }
                if (this.mDragRequested) {
                    if (!this.mIsDragging) {
                        cancelled = Math.hypot((double) (evX - ((float) this.mDownPos.x)), (double) (evY - ((float) this.mDownPos.y))) > ((double) this.mDragSlop);
                        this.mIsDragging = cancelled;
                    }
                    if (this.mIsDragging) {
                        int width = this.mRv.getMeasuredWidth();
                        int height = this.mRv.getMeasuredHeight();
                        DropTarget currentDropTarget = null;
                        if (this.mLastDropTarget != null && this.mLastDropTarget.acceptsDrop((int) evX, (int) evY, width, height, this.mRv.mSystemInsets, true)) {
                            currentDropTarget = this.mLastDropTarget;
                        }
                        if (currentDropTarget == null) {
                            Iterator<DropTarget> it = this.mDropTargets.iterator();
                            while (it.hasNext()) {
                                DropTarget target = it.next();
                                Iterator<DropTarget> it2 = it;
                                if (!target.acceptsDrop((int) evX, (int) evY, width, height, this.mRv.mSystemInsets, false)) {
                                    it = it2;
                                } else {
                                    currentDropTarget = target;
                                }
                            }
                        }
                        DropTarget currentDropTarget2 = currentDropTarget;
                        if (this.mLastDropTarget != currentDropTarget2) {
                            this.mLastDropTarget = currentDropTarget2;
                            EventBus.getDefault().send(new DragDropTargetChangedEvent(this.mDragTask, currentDropTarget2));
                        }
                    }
                    this.mTaskView.setTranslationX(x);
                    this.mTaskView.setTranslationY(y);
                }
                return consumed;
            default:
                return consumed;
        }
    }
}
