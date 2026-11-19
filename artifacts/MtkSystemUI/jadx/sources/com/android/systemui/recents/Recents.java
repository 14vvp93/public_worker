package com.android.systemui.recents;

import android.app.ActivityManager;
import android.app.trust.TrustManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.EventLog;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.RecentsComponent;
import com.android.systemui.SystemUI;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.ConfigurationChangedEvent;
import com.android.systemui.recents.events.activity.DockedFirstAnimationFrameEvent;
import com.android.systemui.recents.events.activity.DockedTopTaskEvent;
import com.android.systemui.recents.events.activity.LaunchTaskFailedEvent;
import com.android.systemui.recents.events.activity.RecentsActivityStartingEvent;
import com.android.systemui.recents.events.component.RecentsVisibilityChangedEvent;
import com.android.systemui.recents.events.component.ScreenPinningRequestEvent;
import com.android.systemui.recents.events.component.SetWaitingForTransitionStartEvent;
import com.android.systemui.recents.events.component.ShowUserToastEvent;
import com.android.systemui.recents.events.ui.RecentsDrawnEvent;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.model.RecentsTaskLoader;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.stackdivider.Divider;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBar;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class Recents extends SystemUI implements RecentsComponent, CommandQueue.Callbacks {
    public static final Set<String> RECENTS_ACTIVITIES = new HashSet();
    private static RecentsConfiguration sConfiguration;
    private static RecentsDebugFlags sDebugFlags;
    private static SystemServicesProxy sSystemServicesProxy;
    private static RecentsTaskLoader sTaskLoader;
    private int mDraggingInRecentsCurrentUser;
    private Handler mHandler;
    private RecentsImpl mImpl;
    private OverviewProxyService mOverviewProxyService;
    private RecentsSystemUser mSystemToUserCallbacks;
    private TrustManager mTrustManager;
    private IRecentsSystemUserCallbacks mUserToSystemCallbacks;
    private final ArrayList<Runnable> mOnConnectRunnables = new ArrayList<>();
    private final IBinder.DeathRecipient mUserToSystemCallbacksDeathRcpt = new IBinder.DeathRecipient() { // from class: com.android.systemui.recents.Recents.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Recents.this.mUserToSystemCallbacks = null;
            EventLog.writeEvent(36060, 3, Integer.valueOf(Recents.sSystemServicesProxy.getProcessUser()));
            Recents.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.recents.Recents.1.1
                @Override // java.lang.Runnable
                public void run() {
                    Recents.this.registerWithSystemUser();
                }
            }, 5000L);
        }
    };
    private final ServiceConnection mUserToSystemServiceConnection = new ServiceConnection() { // from class: com.android.systemui.recents.Recents.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service != null) {
                Recents.this.mUserToSystemCallbacks = IRecentsSystemUserCallbacks.Stub.asInterface(service);
                EventLog.writeEvent(36060, 2, Integer.valueOf(Recents.sSystemServicesProxy.getProcessUser()));
                try {
                    service.linkToDeath(Recents.this.mUserToSystemCallbacksDeathRcpt, 0);
                } catch (RemoteException e) {
                    Log.e("Recents", "Lost connection to (System) SystemUI", e);
                }
                Recents.this.runAndFlushOnConnectRunnables();
            }
            Recents.this.mContext.unbindService(this);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    static {
        RECENTS_ACTIVITIES.add("com.android.systemui.recents.RecentsActivity");
    }

    public IBinder getSystemUserCallbacks() {
        return this.mSystemToUserCallbacks;
    }

    public static RecentsTaskLoader getTaskLoader() {
        return sTaskLoader;
    }

    public static SystemServicesProxy getSystemServices() {
        return sSystemServicesProxy;
    }

    public static RecentsConfiguration getConfiguration() {
        return sConfiguration;
    }

    public static RecentsDebugFlags getDebugFlags() {
        return sDebugFlags;
    }

    @Override // com.android.systemui.SystemUI
    public void start() {
        Resources res = this.mContext.getResources();
        int defaultTaskBarBackgroundColor = this.mContext.getColor(R.color.recents_task_bar_default_background_color);
        int defaultTaskViewBackgroundColor = this.mContext.getColor(R.color.recents_task_view_default_background_color);
        sDebugFlags = new RecentsDebugFlags();
        sSystemServicesProxy = SystemServicesProxy.getInstance(this.mContext);
        sConfiguration = new RecentsConfiguration(this.mContext);
        sTaskLoader = new RecentsTaskLoader(this.mContext, res.getInteger(R.integer.config_recents_max_thumbnail_count), res.getInteger(R.integer.config_recents_max_icon_count), res.getInteger(R.integer.recents_svelte_level));
        sTaskLoader.setDefaultColors(defaultTaskBarBackgroundColor, defaultTaskViewBackgroundColor);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$Recents$jxznMz7klPgaZ-4xCz9xQEhxpZg
            @Override // java.lang.Runnable
            public final void run() {
                Recents.lambda$start$0(this.f$0);
            }
        }, 4000L);
        putComponent(Recents.class, this);
        this.mTrustManager = (TrustManager) this.mContext.getSystemService("trust");
    }

    public static /* synthetic */ void lambda$start$0(Recents recents) {
        recents.mImpl = new RecentsImpl(recents.mContext);
        recents.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        EventBus.getDefault().register(recents, 1);
        EventBus.getDefault().register(sSystemServicesProxy, 1);
        EventBus.getDefault().register(sTaskLoader, 1);
        int processUser = sSystemServicesProxy.getProcessUser();
        if (sSystemServicesProxy.isSystemUser(processUser)) {
            ((CommandQueue) recents.getComponent(CommandQueue.class)).addCallbacks(recents);
            recents.mSystemToUserCallbacks = new RecentsSystemUser(recents.mContext, recents.mImpl);
        } else {
            recents.registerWithSystemUser();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showRecentApps(boolean triggeredFromAltTab) {
        if (!isUserSetup() || this.mImpl == null) {
            return;
        }
        IOverviewProxy overviewProxy = this.mOverviewProxyService.getProxy();
        if (overviewProxy != null) {
            try {
                overviewProxy.onOverviewShown(triggeredFromAltTab);
                return;
            } catch (RemoteException e) {
                Log.e("Recents", "Failed to send overview show event to launcher.", e);
            }
        }
        ActivityManagerWrapper.getInstance().closeSystemWindows("recentapps");
        if (((Divider) getComponent(Divider.class)).getView() == null) {
            return;
        }
        int recentsGrowTarget = ((Divider) getComponent(Divider.class)).getView().growsRecents();
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.showRecents(triggeredFromAltTab, false, true, recentsGrowTarget);
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.showRecents(triggeredFromAltTab, false, true, recentsGrowTarget);
                    return;
                } catch (RemoteException e2) {
                    Log.e("Recents", "Callback failed", e2);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void hideRecentApps(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) {
        if (!isUserSetup() || this.mImpl == null) {
            return;
        }
        IOverviewProxy overviewProxy = this.mOverviewProxyService.getProxy();
        if (overviewProxy != null) {
            try {
                overviewProxy.onOverviewHidden(triggeredFromAltTab, triggeredFromHomeKey);
                return;
            } catch (RemoteException e) {
                Log.e("Recents", "Failed to send overview hide event to launcher.", e);
            }
        }
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.hideRecents(triggeredFromAltTab, triggeredFromHomeKey);
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.hideRecents(triggeredFromAltTab, triggeredFromHomeKey);
                    return;
                } catch (RemoteException e2) {
                    Log.e("Recents", "Callback failed", e2);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void toggleRecentApps() {
        if (!isUserSetup()) {
            Log.e("Recents", "===> toggleRecentApps : not UserSetup");
            return;
        }
        if (this.mImpl == null) {
            Log.e("Recents", "===> toggleRecentApps : RecentsImpl is null");
            return;
        }
        IOverviewProxy overviewProxy = this.mOverviewProxyService.getProxy();
        if (overviewProxy != null) {
            final Runnable toggleRecents = new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$Recents$3yvKJWDcnR18f5NGyNqvNA0IqFk
                @Override // java.lang.Runnable
                public final void run() {
                    Recents.lambda$toggleRecentApps$1(this.f$0);
                }
            };
            StatusBar statusBar = (StatusBar) getComponent(StatusBar.class);
            if (statusBar != null && statusBar.isKeyguardShowing()) {
                statusBar.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$Recents$mGvrkxwCv_lDM9mh_TmlXxyV6p0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Recents.lambda$toggleRecentApps$2(this.f$0, toggleRecents);
                    }
                }, null, true, false, true);
                Log.d("Recents", "===> toggleRecentApps : isKeyguardShowing");
                return;
            } else {
                Log.d("Recents", "===> toggleRecentApps : toggleRecents.run()");
                toggleRecents.run();
                return;
            }
        }
        if (((Divider) getComponent(Divider.class)).getView() == null) {
            Log.e("Recents", "===> toggleRecentApps : getComponent(Divider.class).getView() == null");
            return;
        }
        int growTarget = ((Divider) getComponent(Divider.class)).getView().growsRecents();
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.toggleRecents(growTarget);
            Log.d("Recents", "===> toggleRecentApps : mImpl.toggleRecents(growTarget)");
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.toggleRecents(growTarget);
                    Log.d("Recents", "===> toggleRecentApps : callbacks.toggleRecents(growTarget)");
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    public static /* synthetic */ void lambda$toggleRecentApps$1(Recents recents) {
        try {
            if (recents.mOverviewProxyService.getProxy() != null) {
                recents.mOverviewProxyService.getProxy().onOverviewToggle();
            }
        } catch (RemoteException e) {
            Log.e("Recents", "Cannot send toggle recents through proxy service.", e);
        }
    }

    public static /* synthetic */ void lambda$toggleRecentApps$2(Recents recents, Runnable toggleRecents) {
        recents.mTrustManager.reportKeyguardShowingChanged();
        recents.mHandler.post(toggleRecents);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void preloadRecentApps() {
        if (!isUserSetup() || this.mImpl == null || this.mOverviewProxyService.getProxy() != null) {
            return;
        }
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.preloadRecents();
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.preloadRecents();
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void cancelPreloadRecentApps() {
        if (!isUserSetup() || this.mImpl == null || this.mOverviewProxyService.getProxy() != null) {
            return;
        }
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.cancelPreloadingRecents();
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.cancelPreloadingRecents();
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    @Override // com.android.systemui.RecentsComponent
    public boolean splitPrimaryTask(int dragMode, int stackCreateMode, Rect initialBounds, int metricsDockAction) {
        int activityType;
        if (!isUserSetup() || this.mImpl == null) {
            return false;
        }
        Point realSize = new Point();
        if (initialBounds == null) {
            ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(0).getRealSize(realSize);
            initialBounds = new Rect(0, 0, realSize.x, realSize.y);
        }
        int currentUser = sSystemServicesProxy.getCurrentUser();
        ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.getInstance().getRunningTask();
        if (runningTask != null) {
            activityType = runningTask.configuration.windowConfiguration.getActivityType();
        } else {
            activityType = 0;
        }
        boolean screenPinningActive = ActivityManagerWrapper.getInstance().isScreenPinningActive();
        boolean isRunningTaskInHomeOrRecentsStack = activityType == 2 || activityType == 3;
        if (runningTask == null || isRunningTaskInHomeOrRecentsStack || screenPinningActive) {
            return false;
        }
        logDockAttempt(this.mContext, runningTask.topActivity, runningTask.resizeMode);
        if (runningTask.supportsSplitScreenMultiWindow) {
            if (metricsDockAction != -1) {
                MetricsLogger.action(this.mContext, metricsDockAction, runningTask.topActivity.flattenToShortString());
            }
            if (sSystemServicesProxy.isSystemUser(currentUser)) {
                this.mImpl.splitPrimaryTask(runningTask.id, dragMode, stackCreateMode, initialBounds);
            } else if (this.mSystemToUserCallbacks != null) {
                IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
                if (callbacks != null) {
                    try {
                        callbacks.splitPrimaryTask(runningTask.id, dragMode, stackCreateMode, initialBounds);
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                } else {
                    Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
                }
            }
            this.mDraggingInRecentsCurrentUser = currentUser;
            if (this.mOverviewProxyService.getProxy() != null) {
                EventBus.getDefault().post(new RecentsDrawnEvent());
            }
            return true;
        }
        EventBus.getDefault().send(new ShowUserToastEvent(R.string.dock_non_resizeble_failed_to_dock_text, 0));
        return false;
    }

    public static void logDockAttempt(Context ctx, ComponentName activity, int resizeMode) {
        if (resizeMode == 0) {
            MetricsLogger.action(ctx, 391, activity.flattenToShortString());
        }
        MetricsLogger.count(ctx, getMetricsCounterForResizeMode(resizeMode), 1);
    }

    private static String getMetricsCounterForResizeMode(int resizeMode) {
        if (resizeMode == 4) {
            return "window_enter_unsupported";
        }
        switch (resizeMode) {
            case 1:
            case 2:
                return "window_enter_supported";
            default:
                return "window_enter_incompatible";
        }
    }

    @Override // com.android.systemui.RecentsComponent
    public void onDraggingInRecents(float distanceFromTop) {
        if (this.mImpl == null) {
            return;
        }
        if (sSystemServicesProxy.isSystemUser(this.mDraggingInRecentsCurrentUser)) {
            this.mImpl.onDraggingInRecents(distanceFromTop);
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(this.mDraggingInRecentsCurrentUser);
            if (callbacks != null) {
                try {
                    callbacks.onDraggingInRecents(distanceFromTop);
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + this.mDraggingInRecentsCurrentUser);
        }
    }

    @Override // com.android.systemui.RecentsComponent
    public void onDraggingInRecentsEnded(float velocity) {
        if (this.mImpl == null) {
            return;
        }
        if (sSystemServicesProxy.isSystemUser(this.mDraggingInRecentsCurrentUser)) {
            this.mImpl.onDraggingInRecentsEnded(velocity);
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(this.mDraggingInRecentsCurrentUser);
            if (callbacks != null) {
                try {
                    callbacks.onDraggingInRecentsEnded(velocity);
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + this.mDraggingInRecentsCurrentUser);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionFinished() {
        if (!getConfiguration().isLowRamDevice) {
            EventBus.getDefault().send(new SetWaitingForTransitionStartEvent(false));
        }
    }

    @Override // com.android.systemui.SystemUI
    public void onConfigurationChanged(Configuration newConfig) {
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (this.mImpl == null) {
            return;
        }
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.onConfigurationChanged();
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.onConfigurationChanged();
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    public final void onBusEvent(final RecentsVisibilityChangedEvent event) {
        SystemServicesProxy ssp = getSystemServices();
        int processUser = ssp.getProcessUser();
        if (this.mImpl == null) {
            return;
        }
        if (ssp.isSystemUser(processUser)) {
            this.mImpl.onVisibilityChanged(event.applicationContext, event.visible);
        } else {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.updateRecentsVisibility(event.visible);
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
        if (!event.visible) {
            this.mImpl.setWaitingForTransitionStart(false);
        }
    }

    public final void onBusEvent(DockedFirstAnimationFrameEvent event) {
        SystemServicesProxy ssp = getSystemServices();
        int processUser = ssp.getProcessUser();
        if (!ssp.isSystemUser(processUser)) {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.sendDockedFirstAnimationFrameEvent();
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
    }

    public final void onBusEvent(final ScreenPinningRequestEvent event) {
        int processUser = sSystemServicesProxy.getProcessUser();
        if (this.mImpl == null) {
            return;
        }
        if (sSystemServicesProxy.isSystemUser(processUser)) {
            this.mImpl.onStartScreenPinning(event.applicationContext, event.taskId);
        } else {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.startScreenPinning(event.taskId);
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
    }

    public final void onBusEvent(RecentsDrawnEvent event) {
        int processUser = sSystemServicesProxy.getProcessUser();
        if (!sSystemServicesProxy.isSystemUser(processUser)) {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.sendRecentsDrawnEvent();
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
    }

    public final void onBusEvent(final DockedTopTaskEvent event) {
        int processUser = sSystemServicesProxy.getProcessUser();
        if (!sSystemServicesProxy.isSystemUser(processUser)) {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.sendDockingTopTaskEvent(event.dragMode, event.initialRect);
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
    }

    public final void onBusEvent(RecentsActivityStartingEvent event) {
        int processUser = sSystemServicesProxy.getProcessUser();
        if (!sSystemServicesProxy.isSystemUser(processUser)) {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.sendLaunchRecentsEvent();
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
    }

    public final void onBusEvent(LaunchTaskFailedEvent event) {
        if (this.mImpl == null) {
            return;
        }
        this.mImpl.setWaitingForTransitionStart(false);
    }

    public final void onBusEvent(ConfigurationChangedEvent event) {
        if (this.mImpl == null) {
            return;
        }
        this.mImpl.onConfigurationChanged();
    }

    public final void onBusEvent(ShowUserToastEvent event) {
        int currentUser = sSystemServicesProxy.getCurrentUser();
        if (this.mImpl == null) {
            return;
        }
        if (sSystemServicesProxy.isSystemUser(currentUser)) {
            this.mImpl.onShowCurrentUserToast(event.msgResId, event.msgLength);
            return;
        }
        if (this.mSystemToUserCallbacks != null) {
            IRecentsNonSystemUserCallbacks callbacks = this.mSystemToUserCallbacks.getNonSystemUserRecentsForUser(currentUser);
            if (callbacks != null) {
                try {
                    callbacks.showCurrentUserToast(event.msgResId, event.msgLength);
                    return;
                } catch (RemoteException e) {
                    Log.e("Recents", "Callback failed", e);
                    return;
                }
            }
            Log.e("Recents", "No SystemUI callbacks found for user: " + currentUser);
        }
    }

    public final void onBusEvent(final SetWaitingForTransitionStartEvent event) {
        int processUser = sSystemServicesProxy.getProcessUser();
        if (this.mImpl == null) {
            return;
        }
        if (sSystemServicesProxy.isSystemUser(processUser)) {
            this.mImpl.setWaitingForTransitionStart(event.waitingForTransitionStart);
        } else {
            postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Recents.this.mUserToSystemCallbacks.setWaitingForTransitionStartEvent(event.waitingForTransitionStart);
                    } catch (RemoteException e) {
                        Log.e("Recents", "Callback failed", e);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerWithSystemUser() {
        final int processUser = sSystemServicesProxy.getProcessUser();
        if (this.mImpl == null) {
            return;
        }
        postToSystemUser(new Runnable() { // from class: com.android.systemui.recents.Recents.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Recents.this.mUserToSystemCallbacks.registerNonSystemUserCallbacks(new RecentsImplProxy(Recents.this.mImpl), processUser);
                } catch (RemoteException e) {
                    Log.e("Recents", "Failed to register", e);
                }
            }
        });
    }

    private void postToSystemUser(Runnable onConnectRunnable) {
        this.mOnConnectRunnables.add(onConnectRunnable);
        if (this.mUserToSystemCallbacks == null) {
            Intent systemUserServiceIntent = new Intent();
            systemUserServiceIntent.setClass(this.mContext, RecentsSystemUserService.class);
            boolean bound = this.mContext.bindServiceAsUser(systemUserServiceIntent, this.mUserToSystemServiceConnection, 1, UserHandle.SYSTEM);
            EventLog.writeEvent(36060, 1, Integer.valueOf(sSystemServicesProxy.getProcessUser()));
            if (!bound) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.recents.Recents.11
                    @Override // java.lang.Runnable
                    public void run() {
                        Recents.this.registerWithSystemUser();
                    }
                }, 5000L);
                return;
            }
            return;
        }
        runAndFlushOnConnectRunnables();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runAndFlushOnConnectRunnables() {
        for (Runnable r : this.mOnConnectRunnables) {
            r.run();
        }
        this.mOnConnectRunnables.clear();
    }

    private boolean isUserSetup() {
        ContentResolver cr = this.mContext.getContentResolver();
        return (Settings.Global.getInt(cr, "device_provisioned", 0) == 0 || Settings.Secure.getInt(cr, "user_setup_complete", 0) == 0) ? false : true;
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("Recents");
        pw.println("  currentUserId=" + SystemServicesProxy.getInstance(this.mContext).getCurrentUser());
    }
}
