package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.util.ArraySet;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.NotificationExpandButton;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class NotificationHeaderViewWrapper extends NotificationViewWrapper {
    private static final Interpolator LOW_PRIORITY_HEADER_CLOSE = new PathInterpolator(0.4f, 0.0f, 0.7f, 1.0f);
    protected int mColor;
    private NotificationExpandButton mExpandButton;
    private TextView mHeaderText;
    protected float mHeaderTranslation;
    private ImageView mIcon;
    private boolean mIsLowPriority;
    private NotificationHeaderView mNotificationHeader;
    private boolean mShowExpandButtonAtEnd;
    private boolean mTransformLowPriorityTitle;
    protected final ViewTransformationHelper mTransformationHelper;
    private final int mTranslationForHeader;
    private ImageView mWorkProfileImage;

    protected NotificationHeaderViewWrapper(Context ctx, View view, ExpandableNotificationRow row) {
        super(ctx, view, row);
        this.mShowExpandButtonAtEnd = ctx.getResources().getBoolean(R.bool.config_showNotificationExpandButtonAtEnd);
        this.mTransformationHelper = new ViewTransformationHelper();
        this.mTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(1) { // from class: com.android.systemui.statusbar.notification.NotificationHeaderViewWrapper.1
            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public Interpolator getCustomInterpolator(int interpolationType, boolean isFrom) {
                boolean isLowPriority = NotificationHeaderViewWrapper.this.mView instanceof NotificationHeaderView;
                if (interpolationType == 16) {
                    if ((!isLowPriority || isFrom) && (isLowPriority || !isFrom)) {
                        return NotificationHeaderViewWrapper.LOW_PRIORITY_HEADER_CLOSE;
                    }
                    return Interpolators.LINEAR_OUT_SLOW_IN;
                }
                return null;
            }

            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation
            protected boolean hasCustomTransformation() {
                return NotificationHeaderViewWrapper.this.mIsLowPriority && NotificationHeaderViewWrapper.this.mTransformLowPriorityTitle;
            }
        }, 1);
        resolveHeaderViews();
        addAppOpsOnClickListener(row);
        this.mTranslationForHeader = ctx.getResources().getDimensionPixelSize(android.R.dimen.conversation_badge_protrusion_group_expanded_face_pile) - ctx.getResources().getDimensionPixelSize(android.R.dimen.conversation_content_start);
    }

    protected void resolveHeaderViews() {
        this.mIcon = (ImageView) this.mView.findViewById(android.R.id.icon);
        this.mHeaderText = (TextView) this.mView.findViewById(android.R.id.collapsing);
        this.mExpandButton = this.mView.findViewById(android.R.id.autofill_sheet_scroll_view_space);
        this.mWorkProfileImage = (ImageView) this.mView.findViewById(android.R.id.insideInset);
        this.mNotificationHeader = this.mView.findViewById(android.R.id.fullUser);
        this.mNotificationHeader.setShowExpandButtonAtEnd(this.mShowExpandButtonAtEnd);
        this.mColor = this.mNotificationHeader.getOriginalIconColor();
    }

    private void addAppOpsOnClickListener(ExpandableNotificationRow row) {
        this.mNotificationHeader.setAppOpsOnClickListener(row.getAppOpsOnClickListener());
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow row) {
        super.onContentUpdated(row);
        this.mIsLowPriority = row.isLowPriority();
        this.mTransformLowPriorityTitle = (row.isChildInGroup() || row.isSummaryWithChildren()) ? false : true;
        ArraySet<View> previousViews = this.mTransformationHelper.getAllTransformingViews();
        resolveHeaderViews();
        updateTransformedTypes();
        addRemainingTransformTypes();
        updateCropToPaddingForImageViews();
        Notification notification = row.getStatusBarNotification().getNotification();
        this.mIcon.setTag(R.id.image_icon_tag, notification.getSmallIcon());
        this.mWorkProfileImage.setTag(R.id.image_icon_tag, notification.getSmallIcon());
        ArraySet<View> currentViews = this.mTransformationHelper.getAllTransformingViews();
        for (int i = 0; i < previousViews.size(); i++) {
            View view = previousViews.valueAt(i);
            if (!currentViews.contains(view)) {
                this.mTransformationHelper.resetTransformedView(view);
            }
        }
    }

    private void addRemainingTransformTypes() {
        this.mTransformationHelper.addRemainingTransformTypes(this.mView);
    }

    private void updateCropToPaddingForImageViews() {
        Stack<View> stack = new Stack<>();
        stack.push(this.mView);
        while (!stack.isEmpty()) {
            View child = stack.pop();
            if (child instanceof ImageView) {
                ((ImageView) child).setCropToPadding(true);
            } else if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                for (int i = 0; i < group.getChildCount(); i++) {
                    stack.push(group.getChildAt(i));
                }
            }
        }
    }

    protected void updateTransformedTypes() {
        this.mTransformationHelper.reset();
        this.mTransformationHelper.addTransformedView(0, this.mIcon);
        if (this.mIsLowPriority) {
            this.mTransformationHelper.addTransformedView(1, this.mHeaderText);
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void updateExpandability(boolean expandable, View.OnClickListener onClickListener) {
        this.mExpandButton.setVisibility(expandable ? 0 : 8);
        this.mNotificationHeader.setOnClickListener(expandable ? onClickListener : null);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void setHeaderVisibleAmount(float headerVisibleAmount) {
        super.setHeaderVisibleAmount(headerVisibleAmount);
        this.mNotificationHeader.setAlpha(headerVisibleAmount);
        this.mHeaderTranslation = (1.0f - headerVisibleAmount) * this.mTranslationForHeader;
        this.mView.setTranslationY(this.mHeaderTranslation);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public int getHeaderTranslation() {
        return (int) this.mHeaderTranslation;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public NotificationHeaderView getNotificationHeader() {
        return this.mNotificationHeader;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public TransformState getCurrentState(int fadingView) {
        return this.mTransformationHelper.getCurrentState(fadingView);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView notification, Runnable endRunnable) {
        this.mTransformationHelper.transformTo(notification, endRunnable);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView notification, float transformationAmount) {
        this.mTransformationHelper.transformTo(notification, transformationAmount);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView notification) {
        this.mTransformationHelper.transformFrom(notification);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView notification, float transformationAmount) {
        this.mTransformationHelper.transformFrom(notification, transformationAmount);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void setIsChildInGroup(boolean isChildInGroup) {
        super.setIsChildInGroup(isChildInGroup);
        this.mTransformLowPriorityTitle = !isChildInGroup;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.mTransformationHelper.setVisible(visible);
    }
}
