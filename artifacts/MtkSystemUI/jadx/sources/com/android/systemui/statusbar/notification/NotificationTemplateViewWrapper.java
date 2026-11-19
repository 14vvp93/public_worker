package com.android.systemui.statusbar.notification;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.util.NotificationColorUtil;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.systemui.Dependency;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* JADX INFO: loaded from: classes.dex */
public class NotificationTemplateViewWrapper extends NotificationHeaderViewWrapper {
    private NotificationActionListLayout mActions;
    protected View mActionsContainer;
    private ArraySet<PendingIntent> mCancelledPendingIntents;
    private int mContentHeight;
    private int mMinHeightHint;
    protected ImageView mPicture;
    private ProgressBar mProgressBar;
    private View mRemoteInputHistory;
    private ImageView mReplyAction;
    private TextView mText;
    private TextView mTitle;
    private Rect mTmpRect;
    private UiOffloadThread mUiOffloadThread;

    protected NotificationTemplateViewWrapper(Context ctx, View view, ExpandableNotificationRow row) {
        super(ctx, view, row);
        this.mTmpRect = new Rect();
        this.mCancelledPendingIntents = new ArraySet<>();
        this.mTransformationHelper.setCustomTransformation(new ViewTransformationHelper.CustomTransformation() { // from class: com.android.systemui.statusbar.notification.NotificationTemplateViewWrapper.1
            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean transformTo(TransformState ownState, TransformableView notification, float transformationAmount) {
                if (!(notification instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState otherState = notification.getCurrentState(1);
                View text = ownState.getTransformedView();
                CrossFadeHelper.fadeOut(text, transformationAmount);
                if (otherState != null) {
                    ownState.transformViewVerticalTo(otherState, this, transformationAmount);
                    otherState.recycle();
                }
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean customTransformTarget(TransformState ownState, TransformState otherState) {
                float endY = getTransformationY(ownState, otherState);
                ownState.setTransformationEndY(endY);
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean transformFrom(TransformState ownState, TransformableView notification, float transformationAmount) {
                if (!(notification instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState otherState = notification.getCurrentState(1);
                View text = ownState.getTransformedView();
                CrossFadeHelper.fadeIn(text, transformationAmount);
                if (otherState != null) {
                    ownState.transformViewVerticalFrom(otherState, this, transformationAmount);
                    otherState.recycle();
                }
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean initTransformation(TransformState ownState, TransformState otherState) {
                float startY = getTransformationY(ownState, otherState);
                ownState.setTransformationStartY(startY);
                return true;
            }

            private float getTransformationY(TransformState ownState, TransformState otherState) {
                int[] otherStablePosition = otherState.getLaidOutLocationOnScreen();
                int[] ownStablePosition = ownState.getLaidOutLocationOnScreen();
                return ((otherStablePosition[1] + otherState.getTransformedView().getHeight()) - ownStablePosition[1]) * 0.33f;
            }
        }, 2);
    }

    private void resolveTemplateViews(StatusBarNotification notification) {
        this.mPicture = (ImageView) this.mView.findViewById(R.id.layoutDirection);
        if (this.mPicture != null) {
            this.mPicture.setTag(com.android.systemui.R.id.image_icon_tag, notification.getNotification().getLargeIcon());
        }
        this.mTitle = (TextView) this.mView.findViewById(R.id.title);
        this.mText = (TextView) this.mView.findViewById(R.id.numberPassword);
        View progress = this.mView.findViewById(R.id.progress);
        if (progress instanceof ProgressBar) {
            this.mProgressBar = (ProgressBar) progress;
        } else {
            this.mProgressBar = null;
        }
        this.mActionsContainer = this.mView.findViewById(R.id.KEYCODE_VIDEO_APP_1);
        this.mActions = this.mView.findViewById(R.id.KEYCODE_V);
        this.mReplyAction = (ImageView) this.mView.findViewById(R.id.label_error);
        this.mRemoteInputHistory = this.mView.findViewById(R.id.future);
        updatePendingIntentCancellations();
    }

    private void updatePendingIntentCancellations() {
        if (this.mActions != null) {
            int numActions = this.mActions.getChildCount();
            for (int i = 0; i < numActions; i++) {
                final Button action = (Button) this.mActions.getChildAt(i);
                performOnPendingIntentCancellation(action, new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$oMwnxymrbNZg0qMyiDXpJrgotxQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationTemplateViewWrapper.lambda$updatePendingIntentCancellations$0(this.f$0, action);
                    }
                });
            }
        }
        if (this.mReplyAction != null) {
            this.mReplyAction.setEnabled(true);
            performOnPendingIntentCancellation(this.mReplyAction, new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$FtzUFjG_Gvdx031nKmSLXw7UiBg
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationTemplateViewWrapper.lambda$updatePendingIntentCancellations$1(this.f$0);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$updatePendingIntentCancellations$0(NotificationTemplateViewWrapper notificationTemplateViewWrapper, Button action) {
        if (action.isEnabled()) {
            action.setEnabled(false);
            ColorStateList textColors = action.getTextColors();
            int[] colors = textColors.getColors();
            int[] newColors = new int[colors.length];
            float disabledAlpha = notificationTemplateViewWrapper.mView.getResources().getFloat(R.dimen.config_windowManagerCameraCompatAspectRatio);
            for (int j = 0; j < colors.length; j++) {
                int color = colors[j];
                newColors[j] = notificationTemplateViewWrapper.blendColorWithBackground(color, disabledAlpha);
            }
            ColorStateList newColorStateList = new ColorStateList(textColors.getStates(), newColors);
            action.setTextColor(newColorStateList);
        }
    }

    public static /* synthetic */ void lambda$updatePendingIntentCancellations$1(NotificationTemplateViewWrapper notificationTemplateViewWrapper) {
        if (notificationTemplateViewWrapper.mReplyAction != null && notificationTemplateViewWrapper.mReplyAction.isEnabled()) {
            notificationTemplateViewWrapper.mReplyAction.setEnabled(false);
            Drawable drawable = notificationTemplateViewWrapper.mReplyAction.getDrawable().mutate();
            PorterDuffColorFilter colorFilter = (PorterDuffColorFilter) drawable.getColorFilter();
            float disabledAlpha = notificationTemplateViewWrapper.mView.getResources().getFloat(R.dimen.config_windowManagerCameraCompatAspectRatio);
            if (colorFilter != null) {
                int color = colorFilter.getColor();
                drawable.mutate().setColorFilter(notificationTemplateViewWrapper.blendColorWithBackground(color, disabledAlpha), colorFilter.getMode());
                return;
            }
            notificationTemplateViewWrapper.mReplyAction.setAlpha(disabledAlpha);
        }
    }

    private int blendColorWithBackground(int color, float alpha) {
        return NotificationColorUtil.compositeColors(Color.argb((int) (255.0f * alpha), Color.red(color), Color.green(color), Color.blue(color)), resolveBackgroundColor());
    }

    private void performOnPendingIntentCancellation(View view, final Runnable cancellationRunnable) {
        final PendingIntent pendingIntent = (PendingIntent) view.getTag(R.id.image);
        if (pendingIntent == null) {
            return;
        }
        if (this.mCancelledPendingIntents.contains(pendingIntent)) {
            cancellationRunnable.run();
            return;
        }
        final PendingIntent.CancelListener listener = new PendingIntent.CancelListener() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$CdGwp65qr_-MX4610o6pqFvZxIU
            public final void onCancelled(PendingIntent pendingIntent2) {
                NotificationTemplateViewWrapper notificationTemplateViewWrapper = this.f$0;
                notificationTemplateViewWrapper.mView.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$xG2GQ4HDvuPqD0L-UzmtaSPeQy4
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationTemplateViewWrapper.lambda$performOnPendingIntentCancellation$2(notificationTemplateViewWrapper, pendingIntent, runnable);
                    }
                });
            }
        };
        if (this.mUiOffloadThread == null) {
            this.mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
        }
        if (view.isAttachedToWindow()) {
            this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$g3I4Kek7Jbtd7d61H8EguvhgrJw
                @Override // java.lang.Runnable
                public final void run() {
                    pendingIntent.registerCancelListener(listener);
                }
            });
        }
        view.addOnAttachStateChangeListener(new AnonymousClass2(pendingIntent, listener));
    }

    public static /* synthetic */ void lambda$performOnPendingIntentCancellation$2(NotificationTemplateViewWrapper notificationTemplateViewWrapper, PendingIntent pendingIntent, Runnable cancellationRunnable) {
        notificationTemplateViewWrapper.mCancelledPendingIntents.add(pendingIntent);
        cancellationRunnable.run();
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.NotificationTemplateViewWrapper$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnAttachStateChangeListener {
        final /* synthetic */ PendingIntent.CancelListener val$listener;
        final /* synthetic */ PendingIntent val$pendingIntent;

        AnonymousClass2(PendingIntent pendingIntent, PendingIntent.CancelListener cancelListener) {
            this.val$pendingIntent = pendingIntent;
            this.val$listener = cancelListener;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View v) {
            UiOffloadThread uiOffloadThread = NotificationTemplateViewWrapper.this.mUiOffloadThread;
            final PendingIntent pendingIntent = this.val$pendingIntent;
            final PendingIntent.CancelListener cancelListener = this.val$listener;
            uiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$2$hLjA2XXd-c0oPihadtdZw3GNclc
                @Override // java.lang.Runnable
                public final void run() {
                    pendingIntent.registerCancelListener(cancelListener);
                }
            });
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View v) {
            UiOffloadThread uiOffloadThread = NotificationTemplateViewWrapper.this.mUiOffloadThread;
            final PendingIntent pendingIntent = this.val$pendingIntent;
            final PendingIntent.CancelListener cancelListener = this.val$listener;
            uiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.notification.-$$Lambda$NotificationTemplateViewWrapper$2$wS--1KV8Whw4yTApvD_Oru1vhok
                @Override // java.lang.Runnable
                public final void run() {
                    pendingIntent.unregisterCancelListener(cancelListener);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public boolean disallowSingleClick(float x, float y) {
        if (this.mReplyAction != null && this.mReplyAction.getVisibility() == 0 && (isOnView(this.mReplyAction, x, y) || isOnView(this.mPicture, x, y))) {
            return true;
        }
        return super.disallowSingleClick(x, y);
    }

    private boolean isOnView(View view, float x, float y) {
        for (View searchView = (View) view.getParent(); searchView != null && !(searchView instanceof ExpandableNotificationRow); searchView = (View) searchView.getParent()) {
            searchView.getHitRect(this.mTmpRect);
            x -= this.mTmpRect.left;
            y -= this.mTmpRect.top;
        }
        view.getHitRect(this.mTmpRect);
        return this.mTmpRect.contains((int) x, (int) y);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow row) {
        resolveTemplateViews(row.getStatusBarNotification());
        super.onContentUpdated(row);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationHeaderViewWrapper
    protected void updateTransformedTypes() {
        super.updateTransformedTypes();
        if (this.mTitle != null) {
            this.mTransformationHelper.addTransformedView(1, this.mTitle);
        }
        if (this.mText != null) {
            this.mTransformationHelper.addTransformedView(2, this.mText);
        }
        if (this.mPicture != null) {
            this.mTransformationHelper.addTransformedView(3, this.mPicture);
        }
        if (this.mProgressBar != null) {
            this.mTransformationHelper.addTransformedView(4, this.mProgressBar);
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void setContentHeight(int contentHeight, int minHeightHint) {
        super.setContentHeight(contentHeight, minHeightHint);
        this.mContentHeight = contentHeight;
        this.mMinHeightHint = minHeightHint;
        updateActionOffset();
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public boolean shouldClipToRounding(boolean topRounded, boolean bottomRounded) {
        if (super.shouldClipToRounding(topRounded, bottomRounded)) {
            return true;
        }
        return (!bottomRounded || this.mActionsContainer == null || this.mActionsContainer.getVisibility() == 8) ? false : true;
    }

    private void updateActionOffset() {
        if (this.mActionsContainer != null) {
            int constrainedContentHeight = Math.max(this.mContentHeight, this.mMinHeightHint);
            this.mActionsContainer.setTranslationY((constrainedContentHeight - this.mView.getHeight()) - getHeaderTranslation());
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public int getExtraMeasureHeight() {
        int extra = 0;
        if (this.mActions != null) {
            extra = this.mActions.getExtraMeasureHeight();
        }
        if (this.mRemoteInputHistory != null && this.mRemoteInputHistory.getVisibility() != 8) {
            extra += this.mRow.getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.remote_input_history_extra_height);
        }
        return super.getExtraMeasureHeight() + extra;
    }
}
