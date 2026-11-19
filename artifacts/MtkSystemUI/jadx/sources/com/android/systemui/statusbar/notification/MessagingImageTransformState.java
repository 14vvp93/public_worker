package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.internal.widget.MessagingImageMessage;
import com.android.systemui.R;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* JADX INFO: loaded from: classes.dex */
public class MessagingImageTransformState extends ImageTransformState {
    private static Pools.SimplePool<MessagingImageTransformState> sInstancePool = new Pools.SimplePool<>(40);
    private MessagingImageMessage mImageMessage;

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        this.mImageMessage = (MessagingImageMessage) view;
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    protected boolean sameAs(TransformState otherState) {
        if (super.sameAs(otherState)) {
            return true;
        }
        if (otherState instanceof MessagingImageTransformState) {
            MessagingImageTransformState otherMessage = (MessagingImageTransformState) otherState;
            return this.mImageMessage.sameAs(otherMessage.mImageMessage);
        }
        return false;
    }

    public static MessagingImageTransformState obtain() {
        MessagingImageTransformState instance = (MessagingImageTransformState) sInstancePool.acquire();
        if (instance != null) {
            return instance;
        }
        return new MessagingImageTransformState();
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    protected boolean transformScale(TransformState otherState) {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void transformViewFrom(TransformState otherState, int transformationFlags, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        super.transformViewFrom(otherState, transformationFlags, customTransformation, transformationAmount);
        float interpolatedValue = this.mDefaultInterpolator.getInterpolation(transformationAmount);
        if ((otherState instanceof MessagingImageTransformState) && sameAs(otherState)) {
            MessagingImageMessage otherMessage = ((MessagingImageTransformState) otherState).mImageMessage;
            if (transformationAmount == 0.0f) {
                setStartActualWidth(otherMessage.getActualWidth());
                setStartActualHeight(otherMessage.getActualHeight());
            }
            float startActualWidth = getStartActualWidth();
            this.mImageMessage.setActualWidth((int) NotificationUtils.interpolate(startActualWidth, this.mImageMessage.getStaticWidth(), interpolatedValue));
            float startActualHeight = getStartActualHeight();
            this.mImageMessage.setActualHeight((int) NotificationUtils.interpolate(startActualHeight, this.mImageMessage.getHeight(), interpolatedValue));
        }
    }

    public int getStartActualWidth() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_actual_width);
        if (tag == null) {
            return -1;
        }
        return ((Integer) tag).intValue();
    }

    public void setStartActualWidth(int actualWidth) {
        this.mTransformedView.setTag(R.id.transformation_start_actual_width, Integer.valueOf(actualWidth));
    }

    public int getStartActualHeight() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_actual_height);
        if (tag == null) {
            return -1;
        }
        return ((Integer) tag).intValue();
    }

    public void setStartActualHeight(int actualWidth) {
        this.mTransformedView.setTag(R.id.transformation_start_actual_height, Integer.valueOf(actualWidth));
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public void recycle() {
        super.recycle();
        if (getClass() == MessagingImageTransformState.class) {
            sInstancePool.release(this);
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void resetTransformedView() {
        super.resetTransformedView();
        this.mImageMessage.setActualWidth(this.mImageMessage.getStaticWidth());
        this.mImageMessage.setActualHeight(this.mImageMessage.getHeight());
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    protected void reset() {
        super.reset();
        this.mImageMessage = null;
    }
}
