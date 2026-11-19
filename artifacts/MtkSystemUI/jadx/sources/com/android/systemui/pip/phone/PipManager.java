package com.android.systemui.pip.phone;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import android.view.IPinnedStackController;
import android.view.IPinnedStackListener;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.systemui.pip.BasePipManager;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.component.ExpandPipEvent;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.InputConsumerController;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class PipManager implements BasePipManager {
    private static PipManager sPipController;
    private IActivityManager mActivityManager;
    private PipAppOpsListener mAppOpsListener;
    private Context mContext;
    private InputConsumerController mInputConsumerController;
    private PipMediaController mMediaController;
    private PipMenuActivityController mMenuController;
    private PipTouchHandler mTouchHandler;
    private IWindowManager mWindowManager;
    private Handler mHandler = new Handler();
    private final PinnedStackListener mPinnedStackListener = new PinnedStackListener();
    SysUiTaskStackChangeListener mTaskStackListener = new SysUiTaskStackChangeListener() { // from class: com.android.systemui.pip.phone.PipManager.1
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityPinned(String packageName, int userId, int taskId, int stackId) {
            PipManager.this.mTouchHandler.onActivityPinned();
            PipManager.this.mMediaController.onActivityPinned();
            PipManager.this.mMenuController.onActivityPinned();
            PipManager.this.mAppOpsListener.onActivityPinned(packageName);
            SystemServicesProxy.getInstance(PipManager.this.mContext).setPipVisibility(true);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityUnpinned() {
            Pair<ComponentName, Integer> topPipActivityInfo = PipUtils.getTopPinnedActivity(PipManager.this.mContext, PipManager.this.mActivityManager);
            ComponentName topActivity = (ComponentName) topPipActivityInfo.first;
            if (topActivity != null) {
                ((Integer) topPipActivityInfo.second).intValue();
            }
            PipManager.this.mMenuController.onActivityUnpinned();
            PipManager.this.mTouchHandler.onActivityUnpinned(topActivity);
            PipManager.this.mAppOpsListener.onActivityUnpinned();
            SystemServicesProxy.getInstance(PipManager.this.mContext).setPipVisibility(topActivity != null);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onPinnedStackAnimationStarted() {
            PipManager.this.mTouchHandler.setTouchEnabled(false);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onPinnedStackAnimationEnded() {
            PipManager.this.mTouchHandler.setTouchEnabled(true);
            PipManager.this.mTouchHandler.onPinnedStackAnimationEnded();
            PipManager.this.mMenuController.onPinnedStackAnimationEnded();
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onPinnedActivityRestartAttempt(boolean clearedTask) {
            PipManager.this.mTouchHandler.getMotionHelper().expandPip(clearedTask);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    class PinnedStackListener extends IPinnedStackListener.Stub {
        private PinnedStackListener() {
        }

        public void onListenerRegistered(final IPinnedStackController controller) {
            PipManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipManager$PinnedStackListener$fsM0yPTeQnwLCmc8K2TS4ZFeBWc
                @Override // java.lang.Runnable
                public final void run() {
                    PipManager.this.mTouchHandler.setPinnedStackController(controller);
                }
            });
        }

        public void onImeVisibilityChanged(final boolean imeVisible, final int imeHeight) {
            PipManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipManager$PinnedStackListener$VBLjn70VeOT58ISp8JJdGGwiLRI
                @Override // java.lang.Runnable
                public final void run() {
                    PipManager.this.mTouchHandler.onImeVisibilityChanged(imeVisible, imeHeight);
                }
            });
        }

        public void onShelfVisibilityChanged(final boolean shelfVisible, final int shelfHeight) {
            PipManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipManager$PinnedStackListener$bf4e5rlYRO_U_i4UtAT1QucT53g
                @Override // java.lang.Runnable
                public final void run() {
                    PipManager.this.mTouchHandler.onShelfVisibilityChanged(shelfVisible, shelfHeight);
                }
            });
        }

        public void onMinimizedStateChanged(final boolean isMinimized) {
            PipManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipManager$PinnedStackListener$BUR7BmLfjK0NpOw2OLHQV6xTO5k
                @Override // java.lang.Runnable
                public final void run() {
                    PipManager.this.mTouchHandler.setMinimizedState(isMinimized, true);
                }
            });
        }

        public void onMovementBoundsChanged(final Rect insetBounds, final Rect normalBounds, final Rect animatingBounds, final boolean fromImeAdjustment, final boolean fromShelfAdjustment, final int displayRotation) {
            PipManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipManager$PinnedStackListener$qj7-lqmu1a4XOuu8emxk_Cwvcxo
                @Override // java.lang.Runnable
                public final void run() {
                    PipManager.this.mTouchHandler.onMovementBoundsChanged(insetBounds, normalBounds, animatingBounds, fromImeAdjustment, fromShelfAdjustment, displayRotation);
                }
            });
        }

        public void onActionsChanged(final ParceledListSlice actions) {
            PipManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.pip.phone.-$$Lambda$PipManager$PinnedStackListener$JU_-Gjrp-L4fTB-9HLmwOZwFKXw
                @Override // java.lang.Runnable
                public final void run() {
                    PipManager.this.mMenuController.setAppActions(actions);
                }
            });
        }
    }

    private PipManager() {
    }

    @Override // com.android.systemui.pip.BasePipManager
    public void initialize(Context context) {
        this.mContext = context;
        this.mActivityManager = ActivityManager.getService();
        this.mWindowManager = WindowManagerGlobal.getWindowManagerService();
        try {
            this.mWindowManager.registerPinnedStackListener(0, this.mPinnedStackListener);
        } catch (RemoteException e) {
            Log.e("PipManager", "Failed to register pinned stack listener", e);
        }
        ActivityManagerWrapper.getInstance().registerTaskStackListener(this.mTaskStackListener);
        this.mInputConsumerController = InputConsumerController.getPipInputConsumer();
        this.mInputConsumerController.registerInputConsumer();
        this.mMediaController = new PipMediaController(context, this.mActivityManager);
        this.mMenuController = new PipMenuActivityController(context, this.mActivityManager, this.mMediaController, this.mInputConsumerController);
        this.mTouchHandler = new PipTouchHandler(context, this.mActivityManager, this.mMenuController, this.mInputConsumerController);
        this.mAppOpsListener = new PipAppOpsListener(context, this.mActivityManager, this.mTouchHandler.getMotionHelper());
        EventBus.getDefault().register(this);
    }

    @Override // com.android.systemui.pip.BasePipManager
    public void onConfigurationChanged(Configuration newConfig) {
        this.mTouchHandler.onConfigurationChanged(newConfig);
    }

    public final void onBusEvent(ExpandPipEvent event) {
        this.mTouchHandler.getMotionHelper().expandPip(false);
    }

    @Override // com.android.systemui.pip.BasePipManager
    public void showPictureInPictureMenu() {
        this.mTouchHandler.showPictureInPictureMenu();
    }

    public static PipManager getInstance() {
        if (sPipController == null) {
            sPipController = new PipManager();
        }
        return sPipController;
    }

    @Override // com.android.systemui.pip.BasePipManager
    public void dump(PrintWriter pw) {
        pw.println("PipManager");
        this.mInputConsumerController.dump(pw, "  ");
        this.mMenuController.dump(pw, "  ");
        this.mTouchHandler.dump(pw, "  ");
    }
}
