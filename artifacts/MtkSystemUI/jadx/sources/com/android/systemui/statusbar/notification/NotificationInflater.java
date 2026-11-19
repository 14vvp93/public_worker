package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationContentView;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.util.Assert;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class NotificationInflater {
    private static final InflationExecutor EXECUTOR = new InflationExecutor();

    @VisibleForTesting
    static final int FLAG_REINFLATE_ALL = -1;

    @VisibleForTesting
    static final int FLAG_REINFLATE_EXPANDED_VIEW = 2;
    private InflationCallback mCallback;
    private boolean mIsChildInGroup;
    private boolean mIsLowPriority;
    private boolean mRedactAmbient;
    private RemoteViews.OnClickHandler mRemoteViewClickHandler;
    private final ExpandableNotificationRow mRow;
    private boolean mUsesIncreasedHeadsUpHeight;
    private boolean mUsesIncreasedHeight;

    public NotificationInflater(ExpandableNotificationRow row) {
        this.mRow = row;
    }

    public void setIsLowPriority(boolean isLowPriority) {
        this.mIsLowPriority = isLowPriority;
    }

    public void setIsChildInGroup(boolean childInGroup) {
        if (childInGroup != this.mIsChildInGroup) {
            this.mIsChildInGroup = childInGroup;
            if (this.mIsLowPriority) {
                inflateNotificationViews(3);
            }
        }
    }

    public void setUsesIncreasedHeight(boolean usesIncreasedHeight) {
        this.mUsesIncreasedHeight = usesIncreasedHeight;
    }

    public void setUsesIncreasedHeadsUpHeight(boolean usesIncreasedHeight) {
        this.mUsesIncreasedHeadsUpHeight = usesIncreasedHeight;
    }

    public void setRemoteViewClickHandler(RemoteViews.OnClickHandler remoteViewClickHandler) {
        this.mRemoteViewClickHandler = remoteViewClickHandler;
    }

    public void setRedactAmbient(boolean redactAmbient) {
        if (this.mRedactAmbient != redactAmbient) {
            this.mRedactAmbient = redactAmbient;
            if (this.mRow.getEntry() == null) {
                return;
            }
            inflateNotificationViews(16);
        }
    }

    public void inflateNotificationViews() {
        inflateNotificationViews(FLAG_REINFLATE_ALL);
    }

    @VisibleForTesting
    void inflateNotificationViews(int reInflateFlags) {
        if (this.mRow.isRemoved()) {
            return;
        }
        StatusBarNotification sbn = this.mRow.getEntry().notification;
        AsyncInflationTask task = new AsyncInflationTask(sbn, reInflateFlags, this.mRow, this.mIsLowPriority, this.mIsChildInGroup, this.mUsesIncreasedHeight, this.mUsesIncreasedHeadsUpHeight, this.mRedactAmbient, this.mCallback, this.mRemoteViewClickHandler);
        if (this.mCallback != null && this.mCallback.doInflateSynchronous()) {
            task.onPostExecute(task.doInBackground(new Void[0]));
        } else {
            task.execute(new Void[0]);
        }
    }

    @VisibleForTesting
    InflationProgress inflateNotificationViews(int reInflateFlags, Notification.Builder builder, Context packageContext) {
        InflationProgress result = createRemoteViews(reInflateFlags, builder, this.mIsLowPriority, this.mIsChildInGroup, this.mUsesIncreasedHeight, this.mUsesIncreasedHeadsUpHeight, this.mRedactAmbient, packageContext);
        apply(result, reInflateFlags, this.mRow, this.mRedactAmbient, this.mRemoteViewClickHandler, null);
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InflationProgress createRemoteViews(int reInflateFlags, Notification.Builder builder, boolean isLowPriority, boolean isChildInGroup, boolean usesIncreasedHeight, boolean usesIncreasedHeadsUpHeight, boolean redactAmbient, Context packageContext) {
        InflationProgress result = new InflationProgress();
        boolean isLowPriority2 = isLowPriority && !isChildInGroup;
        if ((reInflateFlags & 1) != 0) {
            result.newContentView = createContentView(builder, isLowPriority2, usesIncreasedHeight);
        }
        if ((reInflateFlags & 2) != 0) {
            result.newExpandedView = createExpandedView(builder, isLowPriority2);
        }
        if ((reInflateFlags & 4) != 0) {
            result.newHeadsUpView = builder.createHeadsUpContentView(usesIncreasedHeadsUpHeight);
        }
        if ((reInflateFlags & 8) != 0) {
            result.newPublicView = builder.makePublicContentView();
        }
        if ((reInflateFlags & 16) != 0) {
            result.newAmbientView = redactAmbient ? builder.makePublicAmbientNotification() : builder.makeAmbientNotification();
        }
        result.packageContext = packageContext;
        result.headsUpStatusBarText = builder.getHeadsUpStatusBarText(false);
        result.headsUpStatusBarTextPublic = builder.getHeadsUpStatusBarText(true);
        return result;
    }

    public static CancellationSignal apply(final InflationProgress result, int reInflateFlags, ExpandableNotificationRow row, boolean redactAmbient, RemoteViews.OnClickHandler remoteViewClickHandler, InflationCallback callback) {
        HashMap<Integer, CancellationSignal> runningInflations;
        NotificationContentView privateLayout;
        NotificationData.Entry entry;
        boolean z;
        NotificationContentView privateLayout2;
        NotificationData.Entry entry2;
        NotificationData.Entry entry3;
        boolean z2;
        NotificationData.Entry entry4;
        boolean isNewView;
        NotificationData.Entry entry5 = row.getEntry();
        NotificationContentView privateLayout3 = row.getPrivateLayout();
        NotificationContentView publicLayout = row.getPublicLayout();
        HashMap<Integer, CancellationSignal> runningInflations2 = new HashMap<>();
        if ((reInflateFlags & 1) == 0) {
            runningInflations = runningInflations2;
        } else {
            boolean isNewView2 = !canReapplyRemoteView(result.newContentView, entry5.cachedContentView);
            ApplyCallback applyCallback = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.1
                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public void setResultView(View v) {
                    result.inflatedContentView = v;
                }

                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return result.newContentView;
                }
            };
            runningInflations = runningInflations2;
            applyRemoteView(result, reInflateFlags, 1, row, redactAmbient, isNewView2, remoteViewClickHandler, callback, entry5, privateLayout3, privateLayout3.getContractedChild(), privateLayout3.getVisibleWrapper(0), runningInflations, applyCallback);
        }
        if ((reInflateFlags & 2) == 0 || result.newExpandedView == null) {
            privateLayout = privateLayout3;
            entry = entry5;
            z = true;
        } else {
            boolean isNewView3 = !canReapplyRemoteView(result.newExpandedView, entry5.cachedBigContentView);
            ApplyCallback applyCallback2 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.2
                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public void setResultView(View v) {
                    result.inflatedExpandedView = v;
                }

                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return result.newExpandedView;
                }
            };
            privateLayout = privateLayout3;
            z = true;
            entry = entry5;
            applyRemoteView(result, reInflateFlags, 2, row, redactAmbient, isNewView3, remoteViewClickHandler, callback, entry5, privateLayout3, privateLayout3.getExpandedChild(), privateLayout3.getVisibleWrapper(1), runningInflations, applyCallback2);
        }
        if ((reInflateFlags & 4) == 0 || result.newHeadsUpView == null) {
            privateLayout2 = privateLayout;
            entry2 = entry;
        } else {
            NotificationData.Entry entry6 = entry;
            boolean isNewView4 = !canReapplyRemoteView(result.newHeadsUpView, entry6.cachedHeadsUpContentView);
            ApplyCallback applyCallback3 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.3
                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public void setResultView(View v) {
                    result.inflatedHeadsUpView = v;
                }

                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return result.newHeadsUpView;
                }
            };
            NotificationContentView privateLayout4 = privateLayout;
            privateLayout2 = privateLayout4;
            entry2 = entry6;
            applyRemoteView(result, reInflateFlags, 4, row, redactAmbient, isNewView4, remoteViewClickHandler, callback, entry6, privateLayout4, privateLayout4.getHeadsUpChild(), privateLayout4.getVisibleWrapper(2), runningInflations, applyCallback3);
        }
        if ((reInflateFlags & 8) == 0) {
            entry3 = entry2;
            z2 = false;
        } else {
            NotificationData.Entry entry7 = entry2;
            boolean isNewView5 = !canReapplyRemoteView(result.newPublicView, entry7.cachedPublicContentView);
            ApplyCallback applyCallback4 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.4
                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public void setResultView(View v) {
                    result.inflatedPublicView = v;
                }

                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return result.newPublicView;
                }
            };
            z2 = false;
            entry3 = entry7;
            applyRemoteView(result, reInflateFlags, 8, row, redactAmbient, isNewView5, remoteViewClickHandler, callback, entry7, publicLayout, publicLayout.getContractedChild(), publicLayout.getVisibleWrapper(0), runningInflations, applyCallback4);
        }
        if ((reInflateFlags & 16) != 0) {
            NotificationContentView newParent = redactAmbient ? publicLayout : privateLayout2;
            if (canReapplyAmbient(row, redactAmbient)) {
                entry4 = entry3;
                if (canReapplyRemoteView(result.newAmbientView, entry4.cachedAmbientContentView)) {
                    isNewView = z2;
                }
                ApplyCallback applyCallback5 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.5
                    @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                    public void setResultView(View v) {
                        result.inflatedAmbientView = v;
                    }

                    @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                    public RemoteViews getRemoteView() {
                        return result.newAmbientView;
                    }
                };
                applyRemoteView(result, reInflateFlags, 16, row, redactAmbient, isNewView, remoteViewClickHandler, callback, entry4, newParent, newParent.getAmbientChild(), newParent.getVisibleWrapper(4), runningInflations, applyCallback5);
            } else {
                entry4 = entry3;
            }
            isNewView = z;
            ApplyCallback applyCallback6 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.5
                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public void setResultView(View v) {
                    result.inflatedAmbientView = v;
                }

                @Override // com.android.systemui.statusbar.notification.NotificationInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return result.newAmbientView;
                }
            };
            applyRemoteView(result, reInflateFlags, 16, row, redactAmbient, isNewView, remoteViewClickHandler, callback, entry4, newParent, newParent.getAmbientChild(), newParent.getVisibleWrapper(4), runningInflations, applyCallback6);
        }
        final HashMap<Integer, CancellationSignal> map = runningInflations;
        finishIfDone(result, reInflateFlags, map, callback, row, redactAmbient);
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationInflater$YqX46rBxwrjWs1TkaBTYm0eniC8
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                map.values().forEach($$Lambda$NotificationInflater$POlPJz26zF5Nt5Z2kVGSqFxN8Co.INSTANCE);
            }
        });
        return cancellationSignal;
    }

    @VisibleForTesting
    static void applyRemoteView(final InflationProgress result, final int reInflateFlags, final int inflationId, final ExpandableNotificationRow row, final boolean redactAmbient, final boolean isNewView, final RemoteViews.OnClickHandler remoteViewClickHandler, final InflationCallback callback, final NotificationData.Entry entry, final NotificationContentView parentLayout, final View existingView, final NotificationViewWrapper existingWrapper, final HashMap<Integer, CancellationSignal> runningInflations, final ApplyCallback applyCallback) {
        CancellationSignal cancellationSignal;
        final RemoteViews newContentView = applyCallback.getRemoteView();
        if (callback != null && callback.doInflateSynchronous()) {
            try {
                if (isNewView) {
                    try {
                        try {
                            View v = newContentView.apply(result.packageContext, parentLayout, remoteViewClickHandler);
                            v.setIsRootNamespace(true);
                            applyCallback.setResultView(v);
                        } catch (Exception e) {
                            e = e;
                            handleInflationError(runningInflations, e, entry.notification, callback);
                            runningInflations.put(Integer.valueOf(inflationId), new CancellationSignal());
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                } else {
                    try {
                        newContentView.reapply(result.packageContext, existingView, remoteViewClickHandler);
                        existingWrapper.onReinflated();
                    } catch (Exception e3) {
                        e = e3;
                        handleInflationError(runningInflations, e, entry.notification, callback);
                        runningInflations.put(Integer.valueOf(inflationId), new CancellationSignal());
                    }
                }
            } catch (Exception e4) {
                e = e4;
            }
        } else {
            RemoteViews.OnViewAppliedListener listener = new RemoteViews.OnViewAppliedListener() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.6
                public void onViewApplied(View v2) {
                    if (isNewView) {
                        v2.setIsRootNamespace(true);
                        applyCallback.setResultView(v2);
                    } else if (existingWrapper != null) {
                        existingWrapper.onReinflated();
                    }
                    runningInflations.remove(Integer.valueOf(inflationId));
                    NotificationInflater.finishIfDone(result, reInflateFlags, runningInflations, callback, row, redactAmbient);
                }

                public void onError(Exception e5) {
                    try {
                        View newView = existingView;
                        if (isNewView) {
                            newView = newContentView.apply(result.packageContext, parentLayout, remoteViewClickHandler);
                        } else {
                            newContentView.reapply(result.packageContext, existingView, remoteViewClickHandler);
                        }
                        Log.wtf("NotificationInflater", "Async Inflation failed but normal inflation finished normally.", e5);
                        onViewApplied(newView);
                    } catch (Exception e6) {
                        runningInflations.remove(Integer.valueOf(inflationId));
                        NotificationInflater.handleInflationError(runningInflations, e5, entry.notification, callback);
                    }
                }
            };
            if (isNewView) {
                cancellationSignal = newContentView.applyAsync(result.packageContext, parentLayout, EXECUTOR, listener, remoteViewClickHandler);
            } else {
                cancellationSignal = newContentView.reapplyAsync(result.packageContext, existingView, EXECUTOR, listener, remoteViewClickHandler);
            }
            runningInflations.put(Integer.valueOf(inflationId), cancellationSignal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleInflationError(HashMap<Integer, CancellationSignal> runningInflations, Exception e, StatusBarNotification notification, InflationCallback callback) {
        Assert.isMainThread();
        runningInflations.values().forEach($$Lambda$NotificationInflater$POlPJz26zF5Nt5Z2kVGSqFxN8Co.INSTANCE);
        if (callback != null) {
            callback.handleInflationException(notification, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean finishIfDone(InflationProgress result, int reInflateFlags, HashMap<Integer, CancellationSignal> runningInflations, InflationCallback endListener, ExpandableNotificationRow row, boolean redactAmbient) {
        Assert.isMainThread();
        NotificationData.Entry entry = row.getEntry();
        NotificationContentView privateLayout = row.getPrivateLayout();
        NotificationContentView publicLayout = row.getPublicLayout();
        if (!runningInflations.isEmpty()) {
            return false;
        }
        if ((reInflateFlags & 1) != 0) {
            if (result.inflatedContentView != null) {
                privateLayout.setContractedChild(result.inflatedContentView);
            }
            entry.cachedContentView = result.newContentView;
        }
        if ((reInflateFlags & 2) != 0) {
            if (result.inflatedExpandedView != null) {
                privateLayout.setExpandedChild(result.inflatedExpandedView);
            } else if (result.newExpandedView == null) {
                privateLayout.setExpandedChild(null);
            }
            entry.cachedBigContentView = result.newExpandedView;
            row.setExpandable(result.newExpandedView != null);
        }
        if ((reInflateFlags & 4) != 0) {
            if (result.inflatedHeadsUpView != null) {
                privateLayout.setHeadsUpChild(result.inflatedHeadsUpView);
            } else if (result.newHeadsUpView == null) {
                privateLayout.setHeadsUpChild(null);
            }
            entry.cachedHeadsUpContentView = result.newHeadsUpView;
        }
        if ((reInflateFlags & 8) != 0) {
            if (result.inflatedPublicView != null) {
                publicLayout.setContractedChild(result.inflatedPublicView);
            }
            entry.cachedPublicContentView = result.newPublicView;
        }
        if ((reInflateFlags & 16) != 0) {
            if (result.inflatedAmbientView != null) {
                NotificationContentView newParent = redactAmbient ? publicLayout : privateLayout;
                NotificationContentView otherParent = !redactAmbient ? publicLayout : privateLayout;
                newParent.setAmbientChild(result.inflatedAmbientView);
                otherParent.setAmbientChild(null);
            }
            entry.cachedAmbientContentView = result.newAmbientView;
        }
        entry.headsUpStatusBarText = result.headsUpStatusBarText;
        entry.headsUpStatusBarTextPublic = result.headsUpStatusBarTextPublic;
        if (endListener != null) {
            endListener.onAsyncInflationFinished(row.getEntry());
        }
        return true;
    }

    private static RemoteViews createExpandedView(Notification.Builder builder, boolean isLowPriority) {
        RemoteViews bigContentView = builder.createBigContentView();
        if (bigContentView != null) {
            return bigContentView;
        }
        if (isLowPriority) {
            RemoteViews contentView = builder.createContentView();
            Notification.Builder.makeHeaderExpanded(contentView);
            return contentView;
        }
        return null;
    }

    private static RemoteViews createContentView(Notification.Builder builder, boolean isLowPriority, boolean useLarge) {
        if (isLowPriority) {
            return builder.makeLowPriorityContentView(false);
        }
        return builder.createContentView(useLarge);
    }

    @VisibleForTesting
    static boolean canReapplyRemoteView(RemoteViews newView, RemoteViews oldView) {
        return (newView == null && oldView == null) || !(newView == null || oldView == null || oldView.getPackage() == null || newView.getPackage() == null || !newView.getPackage().equals(oldView.getPackage()) || newView.getLayoutId() != oldView.getLayoutId() || oldView.isReapplyDisallowed());
    }

    public void setInflationCallback(InflationCallback callback) {
        this.mCallback = callback;
    }

    public interface InflationCallback {
        void handleInflationException(StatusBarNotification statusBarNotification, Exception exc);

        void onAsyncInflationFinished(NotificationData.Entry entry);

        default boolean doInflateSynchronous() {
            return false;
        }
    }

    public void onDensityOrFontScaleChanged() {
        NotificationData.Entry entry = this.mRow.getEntry();
        entry.cachedAmbientContentView = null;
        entry.cachedBigContentView = null;
        entry.cachedContentView = null;
        entry.cachedHeadsUpContentView = null;
        entry.cachedPublicContentView = null;
        inflateNotificationViews();
    }

    private static boolean canReapplyAmbient(ExpandableNotificationRow row, boolean redactAmbient) {
        NotificationContentView ambientView = redactAmbient ? row.getPublicLayout() : row.getPrivateLayout();
        return ambientView.getAmbientChild() != null;
    }

    public static class AsyncInflationTask extends AsyncTask<Void, Void, InflationProgress> implements InflationTask, InflationCallback {
        private final InflationCallback mCallback;
        private CancellationSignal mCancellationSignal;
        private final Context mContext;
        private Exception mError;
        private final boolean mIsChildInGroup;
        private final boolean mIsLowPriority;
        private int mReInflateFlags;
        private final boolean mRedactAmbient;
        private RemoteViews.OnClickHandler mRemoteViewClickHandler;
        private ExpandableNotificationRow mRow;
        private final StatusBarNotification mSbn;
        private final boolean mUsesIncreasedHeadsUpHeight;
        private final boolean mUsesIncreasedHeight;

        private AsyncInflationTask(StatusBarNotification notification, int reInflateFlags, ExpandableNotificationRow row, boolean isLowPriority, boolean isChildInGroup, boolean usesIncreasedHeight, boolean usesIncreasedHeadsUpHeight, boolean redactAmbient, InflationCallback callback, RemoteViews.OnClickHandler remoteViewClickHandler) {
            this.mRow = row;
            this.mSbn = notification;
            this.mReInflateFlags = reInflateFlags;
            this.mContext = this.mRow.getContext();
            this.mIsLowPriority = isLowPriority;
            this.mIsChildInGroup = isChildInGroup;
            this.mUsesIncreasedHeight = usesIncreasedHeight;
            this.mUsesIncreasedHeadsUpHeight = usesIncreasedHeadsUpHeight;
            this.mRedactAmbient = redactAmbient;
            this.mRemoteViewClickHandler = remoteViewClickHandler;
            this.mCallback = callback;
            NotificationData.Entry entry = row.getEntry();
            entry.setInflationTask(this);
        }

        @VisibleForTesting
        public int getReInflateFlags() {
            return this.mReInflateFlags;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public InflationProgress doInBackground(Void... params) {
            try {
                Notification.Builder recoveredBuilder = Notification.Builder.recoverBuilder(this.mContext, this.mSbn.getNotification());
                Context packageContext = this.mSbn.getPackageContext(this.mContext);
                Notification notification = this.mSbn.getNotification();
                if (notification.isMediaNotification()) {
                    MediaNotificationProcessor processor = new MediaNotificationProcessor(this.mContext, packageContext);
                    processor.processNotification(notification, recoveredBuilder);
                }
                return NotificationInflater.createRemoteViews(this.mReInflateFlags, recoveredBuilder, this.mIsLowPriority, this.mIsChildInGroup, this.mUsesIncreasedHeight, this.mUsesIncreasedHeadsUpHeight, this.mRedactAmbient, packageContext);
            } catch (Exception e) {
                this.mError = e;
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(InflationProgress result) {
            if (this.mError == null) {
                this.mCancellationSignal = NotificationInflater.apply(result, this.mReInflateFlags, this.mRow, this.mRedactAmbient, this.mRemoteViewClickHandler, this);
            } else {
                handleError(this.mError);
            }
        }

        private void handleError(Exception e) {
            this.mRow.getEntry().onInflationTaskFinished();
            StatusBarNotification sbn = this.mRow.getStatusBarNotification();
            String ident = sbn.getPackageName() + "/0x" + Integer.toHexString(sbn.getId());
            Log.e("StatusBar", "couldn't inflate view for notification " + ident, e);
            this.mCallback.handleInflationException(sbn, new InflationException("Couldn't inflate contentViews" + e));
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public void abort() {
            cancel(true);
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public void supersedeTask(InflationTask task) {
            if (task instanceof AsyncInflationTask) {
                this.mReInflateFlags |= ((AsyncInflationTask) task).mReInflateFlags;
            }
        }

        @Override // com.android.systemui.statusbar.notification.NotificationInflater.InflationCallback
        public void handleInflationException(StatusBarNotification notification, Exception e) {
            handleError(e);
        }

        @Override // com.android.systemui.statusbar.notification.NotificationInflater.InflationCallback
        public void onAsyncInflationFinished(NotificationData.Entry entry) {
            this.mRow.getEntry().onInflationTaskFinished();
            this.mRow.onNotificationUpdated();
            this.mCallback.onAsyncInflationFinished(this.mRow.getEntry());
        }

        @Override // com.android.systemui.statusbar.notification.NotificationInflater.InflationCallback
        public boolean doInflateSynchronous() {
            return this.mCallback != null && this.mCallback.doInflateSynchronous();
        }
    }

    @VisibleForTesting
    static class InflationProgress {
        private CharSequence headsUpStatusBarText;
        private CharSequence headsUpStatusBarTextPublic;
        private View inflatedAmbientView;
        private View inflatedContentView;
        private View inflatedExpandedView;
        private View inflatedHeadsUpView;
        private View inflatedPublicView;
        private RemoteViews newAmbientView;
        private RemoteViews newContentView;
        private RemoteViews newExpandedView;
        private RemoteViews newHeadsUpView;
        private RemoteViews newPublicView;

        @VisibleForTesting
        Context packageContext;

        InflationProgress() {
        }
    }

    @VisibleForTesting
    static abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);

        ApplyCallback() {
        }
    }

    private static class InflationExecutor implements Executor {
        private final ThreadPoolExecutor mExecutor;
        private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT + NotificationInflater.FLAG_REINFLATE_ALL, 4));
        private static final int MAXIMUM_POOL_SIZE = (CPU_COUNT * 2) + 1;
        private static final ThreadFactory sThreadFactory = new ThreadFactory() { // from class: com.android.systemui.statusbar.notification.NotificationInflater.InflationExecutor.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable r) {
                return new Thread(r, "InflaterThread #" + this.mCount.getAndIncrement());
            }
        };

        private InflationExecutor() {
            this.mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), sThreadFactory);
            this.mExecutor.allowCoreThreadTimeOut(true);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.mExecutor.execute(runnable);
        }
    }
}
