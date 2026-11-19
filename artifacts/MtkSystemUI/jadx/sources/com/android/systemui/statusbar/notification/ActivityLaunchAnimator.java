package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.MathUtils;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import com.android.systemui.Interpolators;
import com.android.systemui.shared.system.SurfaceControlCompat;
import com.android.systemui.shared.system.SyncRtSurfaceTransactionApplier;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.NotificationListContainer;
import com.android.systemui.statusbar.phone.NotificationPanelView;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarWindowView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityLaunchAnimator {
    private boolean mAnimationPending;
    private final NotificationListContainer mNotificationContainer;
    private final NotificationPanelView mNotificationPanel;
    private StatusBar mStatusBar;
    private final StatusBarWindowView mStatusBarWindow;
    private final Runnable mTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$ActivityLaunchAnimator$l5Gj6YM2XO6z1WFQpGTriWePKVk
        @Override // java.lang.Runnable
        public final void run() {
            ActivityLaunchAnimator.lambda$new$0(this.f$0);
        }
    };

    public static /* synthetic */ void lambda$new$0(ActivityLaunchAnimator activityLaunchAnimator) {
        activityLaunchAnimator.setAnimationPending(false);
        activityLaunchAnimator.mStatusBar.collapsePanel(true);
    }

    public ActivityLaunchAnimator(StatusBarWindowView statusBarWindow, StatusBar statusBar, NotificationPanelView notificationPanel, NotificationListContainer container) {
        this.mNotificationPanel = notificationPanel;
        this.mNotificationContainer = container;
        this.mStatusBarWindow = statusBarWindow;
        this.mStatusBar = statusBar;
    }

    public RemoteAnimationAdapter getLaunchAnimation(ExpandableNotificationRow sourceNotification, boolean occluded) {
        if (this.mStatusBar.getBarState() != 0 || occluded) {
            return null;
        }
        AnimationRunner animationRunner = new AnimationRunner(sourceNotification);
        return new RemoteAnimationAdapter(animationRunner, 400L, 250L);
    }

    public boolean isAnimationPending() {
        return this.mAnimationPending;
    }

    public void setLaunchResult(int launchResult) {
        setAnimationPending((launchResult == 2 || launchResult == 0) && this.mStatusBar.getBarState() == 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAnimationPending(boolean pending) {
        this.mAnimationPending = pending;
        this.mStatusBarWindow.setExpandAnimationPending(pending);
        if (pending) {
            this.mStatusBarWindow.postDelayed(this.mTimeoutRunnable, 500L);
        } else {
            this.mStatusBarWindow.removeCallbacks(this.mTimeoutRunnable);
        }
    }

    class AnimationRunner extends IRemoteAnimationRunner.Stub {
        private final ExpandableNotificationRow mSourceNotification;
        private final SyncRtSurfaceTransactionApplier mSyncRtTransactionApplier;
        private final Rect mWindowCrop = new Rect();
        private boolean mInstantCollapsePanel = true;
        private final ExpandAnimationParameters mParams = new ExpandAnimationParameters();

        public AnimationRunner(ExpandableNotificationRow sourceNofitication) {
            this.mSourceNotification = sourceNofitication;
            this.mSyncRtTransactionApplier = new SyncRtSurfaceTransactionApplier(this.mSourceNotification);
        }

        public void onAnimationStart(final RemoteAnimationTarget[] remoteAnimationTargets, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            this.mSourceNotification.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$ActivityLaunchAnimator$AnimationRunner$sNLXzFzCbt6n0LlixbKU_lp1tVA
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.AnimationRunner.lambda$onAnimationStart$0(this.f$0, remoteAnimationTargets, iRemoteAnimationFinishedCallback);
                }
            });
        }

        public static /* synthetic */ void lambda$onAnimationStart$0(AnimationRunner animationRunner, RemoteAnimationTarget[] remoteAnimationTargets, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            final RemoteAnimationTarget primary = animationRunner.getPrimaryRemoteAnimationTarget(remoteAnimationTargets);
            if (primary == null) {
                ActivityLaunchAnimator.this.setAnimationPending(false);
                animationRunner.invokeCallback(iRemoteAnimationFinishedCallback);
                return;
            }
            boolean z = true;
            animationRunner.setExpandAnimationRunning(true);
            if (primary.position.y != 0 || primary.sourceContainerBounds.height() < ActivityLaunchAnimator.this.mNotificationPanel.getHeight()) {
                z = false;
            }
            animationRunner.mInstantCollapsePanel = z;
            if (!animationRunner.mInstantCollapsePanel) {
                ActivityLaunchAnimator.this.mNotificationPanel.collapseWithDuration(400);
            }
            ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
            animationRunner.mParams.startPosition = animationRunner.mSourceNotification.getLocationOnScreen();
            animationRunner.mParams.startTranslationZ = animationRunner.mSourceNotification.getTranslationZ();
            animationRunner.mParams.startClipTopAmount = animationRunner.mSourceNotification.getClipTopAmount();
            if (animationRunner.mSourceNotification.isChildInGroup()) {
                int parentClip = animationRunner.mSourceNotification.getNotificationParent().getClipTopAmount();
                animationRunner.mParams.parentStartClipTopAmount = parentClip;
                if (parentClip != 0) {
                    float childClip = parentClip - animationRunner.mSourceNotification.getTranslationY();
                    if (childClip > 0.0f) {
                        animationRunner.mParams.startClipTopAmount = (int) Math.ceil(childClip);
                    }
                }
            }
            final int targetWidth = primary.sourceContainerBounds.width();
            final int notificationHeight = animationRunner.mSourceNotification.getActualHeight() - animationRunner.mSourceNotification.getClipBottomAmount();
            final int notificationWidth = animationRunner.mSourceNotification.getWidth();
            anim.setDuration(400L);
            anim.setInterpolator(Interpolators.LINEAR);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.ActivityLaunchAnimator.AnimationRunner.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    AnimationRunner.this.mParams.linearProgress = animation.getAnimatedFraction();
                    float progress = Interpolators.FAST_OUT_SLOW_IN.getInterpolation(AnimationRunner.this.mParams.linearProgress);
                    int newWidth = (int) MathUtils.lerp(notificationWidth, targetWidth, progress);
                    AnimationRunner.this.mParams.left = (int) ((targetWidth - newWidth) / 2.0f);
                    AnimationRunner.this.mParams.right = AnimationRunner.this.mParams.left + newWidth;
                    AnimationRunner.this.mParams.top = (int) MathUtils.lerp(AnimationRunner.this.mParams.startPosition[1], primary.position.y, progress);
                    AnimationRunner.this.mParams.bottom = (int) MathUtils.lerp(AnimationRunner.this.mParams.startPosition[1] + notificationHeight, primary.position.y + primary.sourceContainerBounds.bottom, progress);
                    AnimationRunner.this.applyParamsToWindow(primary);
                    AnimationRunner.this.applyParamsToNotification(AnimationRunner.this.mParams);
                    AnimationRunner.this.applyParamsToNotificationList(AnimationRunner.this.mParams);
                }
            });
            anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.ActivityLaunchAnimator.AnimationRunner.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    AnimationRunner.this.setExpandAnimationRunning(false);
                    if (AnimationRunner.this.mInstantCollapsePanel) {
                        ActivityLaunchAnimator.this.mStatusBar.collapsePanel(false);
                    }
                    AnimationRunner.this.invokeCallback(iRemoteAnimationFinishedCallback);
                }
            });
            anim.start();
            ActivityLaunchAnimator.this.setAnimationPending(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invokeCallback(IRemoteAnimationFinishedCallback callback) {
            try {
                callback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        private RemoteAnimationTarget getPrimaryRemoteAnimationTarget(RemoteAnimationTarget[] remoteAnimationTargets) {
            for (RemoteAnimationTarget app : remoteAnimationTargets) {
                if (app.mode == 0) {
                    return app;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setExpandAnimationRunning(boolean running) {
            ActivityLaunchAnimator.this.mNotificationPanel.setLaunchingNotification(running);
            this.mSourceNotification.setExpandAnimationRunning(running);
            ActivityLaunchAnimator.this.mStatusBarWindow.setExpandAnimationRunning(running);
            ActivityLaunchAnimator.this.mNotificationContainer.setExpandingNotification(running ? this.mSourceNotification : null);
            if (!running) {
                applyParamsToNotification(null);
                applyParamsToNotificationList(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyParamsToNotificationList(ExpandAnimationParameters params) {
            ActivityLaunchAnimator.this.mNotificationContainer.applyExpandAnimationParams(params);
            ActivityLaunchAnimator.this.mNotificationPanel.applyExpandAnimationParams(params);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyParamsToNotification(ExpandAnimationParameters params) {
            this.mSourceNotification.applyExpandAnimationParams(params);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyParamsToWindow(RemoteAnimationTarget app) {
            Matrix m = new Matrix();
            m.postTranslate(0.0f, this.mParams.top - app.position.y);
            this.mWindowCrop.set(this.mParams.left, 0, this.mParams.right, this.mParams.getHeight());
            SyncRtSurfaceTransactionApplier.SurfaceParams params = new SyncRtSurfaceTransactionApplier.SurfaceParams(new SurfaceControlCompat(app.leash), 1.0f, m, this.mWindowCrop, app.prefixOrderIndex);
            this.mSyncRtTransactionApplier.scheduleApply(params);
        }

        public void onAnimationCancelled() throws RemoteException {
            this.mSourceNotification.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$ActivityLaunchAnimator$AnimationRunner$M-3NAwVAMqbtd1nWxQdGu3JgCNY
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.AnimationRunner.lambda$onAnimationCancelled$1(this.f$0);
                }
            });
        }

        public static /* synthetic */ void lambda$onAnimationCancelled$1(AnimationRunner animationRunner) {
            ActivityLaunchAnimator.this.setAnimationPending(false);
            ActivityLaunchAnimator.this.mStatusBar.onLaunchAnimationCancelled();
        }
    }

    public static class ExpandAnimationParameters {
        int bottom;
        int left;
        float linearProgress;
        int parentStartClipTopAmount;
        int right;
        int startClipTopAmount;
        int[] startPosition;
        float startTranslationZ;
        int top;

        public int getTop() {
            return this.top;
        }

        public int getWidth() {
            return this.right - this.left;
        }

        public int getHeight() {
            return this.bottom - this.top;
        }

        public int getTopChange() {
            int clipTopAmountCompensation = 0;
            if (this.startClipTopAmount != 0.0f) {
                clipTopAmountCompensation = (int) MathUtils.lerp(0.0f, this.startClipTopAmount, Interpolators.FAST_OUT_SLOW_IN.getInterpolation(this.linearProgress));
            }
            return Math.min((this.top - this.startPosition[1]) - clipTopAmountCompensation, 0);
        }

        public float getProgress() {
            return this.linearProgress;
        }

        public float getProgress(long delay, long duration) {
            return MathUtils.constrain(((this.linearProgress * 400.0f) - delay) / duration, 0.0f, 1.0f);
        }

        public int getStartClipTopAmount() {
            return this.startClipTopAmount;
        }

        public int getParentStartClipTopAmount() {
            return this.parentStartClipTopAmount;
        }

        public float getStartTranslationZ() {
            return this.startTranslationZ;
        }
    }
}
