package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* JADX INFO: loaded from: classes.dex */
public class TransformState {
    private boolean mSameAsAny;
    protected TransformInfo mTransformInfo;
    protected View mTransformedView;
    private static Pools.SimplePool<TransformState> sInstancePool = new Pools.SimplePool<>(40);
    private static ViewClippingUtil.ClippingParameters CLIPPING_PARAMETERS = new ViewClippingUtil.ClippingParameters() { // from class: com.android.systemui.statusbar.notification.TransformState.1
        public boolean shouldFinish(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) view;
                return !row.isChildInGroup();
            }
            return false;
        }

        public void onClippingStateChanged(View view, boolean isClipping) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow row = (ExpandableNotificationRow) view;
                if (isClipping) {
                    row.setClipToActualHeight(true);
                } else if (row.isChildInGroup()) {
                    row.setClipToActualHeight(false);
                }
            }
        }
    };
    private int[] mOwnPosition = new int[2];
    private float mTransformationEndY = -1.0f;
    private float mTransformationEndX = -1.0f;
    protected Interpolator mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;

    public interface TransformInfo {
        boolean isAnimating();
    }

    public void initFrom(View view, TransformInfo transformInfo) {
        this.mTransformedView = view;
        this.mTransformInfo = transformInfo;
    }

    public void transformViewFrom(TransformState otherState, float transformationAmount) {
        this.mTransformedView.animate().cancel();
        if (sameAs(otherState)) {
            ensureVisible();
        } else {
            CrossFadeHelper.fadeIn(this.mTransformedView, transformationAmount);
        }
        transformViewFullyFrom(otherState, transformationAmount);
    }

    protected void ensureVisible() {
        if (this.mTransformedView.getVisibility() == 4 || this.mTransformedView.getAlpha() != 1.0f) {
            this.mTransformedView.setAlpha(1.0f);
            this.mTransformedView.setVisibility(0);
        }
    }

    public void transformViewFullyFrom(TransformState otherState, float transformationAmount) {
        transformViewFrom(otherState, 17, null, transformationAmount);
    }

    public void transformViewFullyFrom(TransformState otherState, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        transformViewFrom(otherState, 17, customTransformation, transformationAmount);
    }

    public void transformViewVerticalFrom(TransformState otherState, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        transformViewFrom(otherState, 16, customTransformation, transformationAmount);
    }

    public void transformViewVerticalFrom(TransformState otherState, float transformationAmount) {
        transformViewFrom(otherState, 16, null, transformationAmount);
    }

    protected void transformViewFrom(TransformState otherState, int transformationFlags, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        int[] otherPosition;
        float f;
        Interpolator customInterpolator;
        Interpolator customInterpolator2;
        View transformedView = this.mTransformedView;
        boolean transformX = (transformationFlags & 1) != 0;
        boolean transformY = (transformationFlags & 16) != 0;
        int viewHeight = getViewHeight();
        int otherHeight = otherState.getViewHeight();
        boolean differentHeight = (otherHeight == viewHeight || otherHeight == 0 || viewHeight == 0) ? false : true;
        int viewWidth = getViewWidth();
        int otherWidth = otherState.getViewWidth();
        boolean differentWidth = (otherWidth == viewWidth || otherWidth == 0 || viewWidth == 0) ? false : true;
        boolean transformScale = transformScale(otherState) && (differentHeight || differentWidth);
        if (transformationAmount == 0.0f || ((transformX && getTransformationStartX() == -1.0f) || ((transformY && getTransformationStartY() == -1.0f) || ((transformScale && getTransformationStartScaleX() == -1.0f && differentWidth) || (transformScale && getTransformationStartScaleY() == -1.0f && differentHeight))))) {
            if (transformationAmount != 0.0f) {
                otherPosition = otherState.getLaidOutLocationOnScreen();
            } else {
                otherPosition = otherState.getLocationOnScreen();
            }
            int[] ownStablePosition = getLaidOutLocationOnScreen();
            if (customTransformation == null || !customTransformation.initTransformation(this, otherState)) {
                if (transformX) {
                    setTransformationStartX(otherPosition[0] - ownStablePosition[0]);
                }
                if (transformY) {
                    setTransformationStartY(otherPosition[1] - ownStablePosition[1]);
                }
                View otherView = otherState.getTransformedView();
                if (transformScale && differentWidth) {
                    setTransformationStartScaleX((otherWidth * otherView.getScaleX()) / viewWidth);
                    transformedView.setPivotX(0.0f);
                } else {
                    setTransformationStartScaleX(-1.0f);
                }
                if (transformScale && differentHeight) {
                    setTransformationStartScaleY((otherHeight * otherView.getScaleY()) / viewHeight);
                    transformedView.setPivotY(0.0f);
                    f = -1.0f;
                } else {
                    f = -1.0f;
                    setTransformationStartScaleY(-1.0f);
                }
            } else {
                f = -1.0f;
            }
            if (!transformX) {
                setTransformationStartX(f);
            }
            if (!transformY) {
                setTransformationStartY(f);
            }
            if (!transformScale) {
                setTransformationStartScaleX(f);
                setTransformationStartScaleY(f);
            }
            setClippingDeactivated(transformedView, true);
        }
        float interpolatedValue = this.mDefaultInterpolator.getInterpolation(transformationAmount);
        if (transformX) {
            float interpolation = interpolatedValue;
            if (customTransformation != null && (customInterpolator2 = customTransformation.getCustomInterpolator(1, true)) != null) {
                interpolation = customInterpolator2.getInterpolation(transformationAmount);
            }
            transformedView.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), 0.0f, interpolation));
        }
        if (transformY) {
            float interpolation2 = interpolatedValue;
            if (customTransformation != null && (customInterpolator = customTransformation.getCustomInterpolator(16, true)) != null) {
                interpolation2 = customInterpolator.getInterpolation(transformationAmount);
            }
            transformedView.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), 0.0f, interpolation2));
        }
        if (transformScale) {
            float transformationStartScaleX = getTransformationStartScaleX();
            if (transformationStartScaleX != -1.0f) {
                transformedView.setScaleX(NotificationUtils.interpolate(transformationStartScaleX, 1.0f, interpolatedValue));
            }
            float transformationStartScaleY = getTransformationStartScaleY();
            if (transformationStartScaleY != -1.0f) {
                transformedView.setScaleY(NotificationUtils.interpolate(transformationStartScaleY, 1.0f, interpolatedValue));
            }
        }
    }

    protected int getViewWidth() {
        return this.mTransformedView.getWidth();
    }

    protected int getViewHeight() {
        return this.mTransformedView.getHeight();
    }

    protected boolean transformScale(TransformState otherState) {
        return false;
    }

    public boolean transformViewTo(TransformState otherState, float transformationAmount) {
        this.mTransformedView.animate().cancel();
        if (sameAs(otherState)) {
            if (this.mTransformedView.getVisibility() == 0) {
                this.mTransformedView.setAlpha(0.0f);
                this.mTransformedView.setVisibility(4);
                return false;
            }
            return false;
        }
        CrossFadeHelper.fadeOut(this.mTransformedView, transformationAmount);
        transformViewFullyTo(otherState, transformationAmount);
        return true;
    }

    public void transformViewFullyTo(TransformState otherState, float transformationAmount) {
        transformViewTo(otherState, 17, null, transformationAmount);
    }

    public void transformViewFullyTo(TransformState otherState, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        transformViewTo(otherState, 17, customTransformation, transformationAmount);
    }

    public void transformViewVerticalTo(TransformState otherState, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        transformViewTo(otherState, 16, customTransformation, transformationAmount);
    }

    public void transformViewVerticalTo(TransformState otherState, float transformationAmount) {
        transformViewTo(otherState, 16, null, transformationAmount);
    }

    private void transformViewTo(TransformState otherState, int transformationFlags, ViewTransformationHelper.CustomTransformation customTransformation, float transformationAmount) {
        View transformedView = this.mTransformedView;
        boolean transformX = (transformationFlags & 1) != 0;
        boolean transformY = (transformationFlags & 16) != 0;
        boolean transformScale = transformScale(otherState);
        if (transformationAmount == 0.0f) {
            if (transformX) {
                float transformationStartX = getTransformationStartX();
                float start = transformationStartX != -1.0f ? transformationStartX : transformedView.getTranslationX();
                setTransformationStartX(start);
            }
            if (transformY) {
                float transformationStartY = getTransformationStartY();
                float start2 = transformationStartY != -1.0f ? transformationStartY : transformedView.getTranslationY();
                setTransformationStartY(start2);
            }
            otherState.getTransformedView();
            if (transformScale && otherState.getViewWidth() != getViewWidth()) {
                setTransformationStartScaleX(transformedView.getScaleX());
                transformedView.setPivotX(0.0f);
            } else {
                setTransformationStartScaleX(-1.0f);
            }
            if (transformScale && otherState.getViewHeight() != getViewHeight()) {
                setTransformationStartScaleY(transformedView.getScaleY());
                transformedView.setPivotY(0.0f);
            } else {
                setTransformationStartScaleY(-1.0f);
            }
            setClippingDeactivated(transformedView, true);
        }
        float interpolatedValue = this.mDefaultInterpolator.getInterpolation(transformationAmount);
        int[] otherStablePosition = otherState.getLaidOutLocationOnScreen();
        int[] ownPosition = getLaidOutLocationOnScreen();
        if (transformX) {
            float endX = otherStablePosition[0] - ownPosition[0];
            float interpolation = interpolatedValue;
            if (customTransformation != null) {
                if (customTransformation.customTransformTarget(this, otherState)) {
                    endX = this.mTransformationEndX;
                }
                Interpolator customInterpolator = customTransformation.getCustomInterpolator(1, false);
                if (customInterpolator != null) {
                    interpolation = customInterpolator.getInterpolation(transformationAmount);
                }
            }
            transformedView.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), endX, interpolation));
        }
        if (transformY) {
            float endY = otherStablePosition[1] - ownPosition[1];
            float interpolation2 = interpolatedValue;
            if (customTransformation != null) {
                if (customTransformation.customTransformTarget(this, otherState)) {
                    endY = this.mTransformationEndY;
                }
                Interpolator customInterpolator2 = customTransformation.getCustomInterpolator(16, false);
                if (customInterpolator2 != null) {
                    interpolation2 = customInterpolator2.getInterpolation(transformationAmount);
                }
            }
            transformedView.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), endY, interpolation2));
        }
        if (transformScale) {
            otherState.getTransformedView();
            float transformationStartScaleX = getTransformationStartScaleX();
            if (transformationStartScaleX != -1.0f) {
                transformedView.setScaleX(NotificationUtils.interpolate(transformationStartScaleX, otherState.getViewWidth() / getViewWidth(), interpolatedValue));
            }
            float transformationStartScaleY = getTransformationStartScaleY();
            if (transformationStartScaleY != -1.0f) {
                transformedView.setScaleY(NotificationUtils.interpolate(transformationStartScaleY, otherState.getViewHeight() / getViewHeight(), interpolatedValue));
            }
        }
    }

    protected void setClippingDeactivated(View transformedView, boolean deactivated) {
        ViewClippingUtil.setClippingDeactivated(transformedView, deactivated, CLIPPING_PARAMETERS);
    }

    public int[] getLaidOutLocationOnScreen() {
        int[] location = getLocationOnScreen();
        location[0] = (int) (location[0] - this.mTransformedView.getTranslationX());
        location[1] = (int) (location[1] - this.mTransformedView.getTranslationY());
        return location;
    }

    public int[] getLocationOnScreen() {
        this.mTransformedView.getLocationOnScreen(this.mOwnPosition);
        int[] iArr = this.mOwnPosition;
        iArr[0] = (int) (iArr[0] - ((1.0f - this.mTransformedView.getScaleX()) * this.mTransformedView.getPivotX()));
        int[] iArr2 = this.mOwnPosition;
        iArr2[1] = (int) (iArr2[1] - ((1.0f - this.mTransformedView.getScaleY()) * this.mTransformedView.getPivotY()));
        int[] iArr3 = this.mOwnPosition;
        iArr3[1] = iArr3[1] - (MessagingPropertyAnimator.getTop(this.mTransformedView) - MessagingPropertyAnimator.getLayoutTop(this.mTransformedView));
        return this.mOwnPosition;
    }

    protected boolean sameAs(TransformState otherState) {
        return this.mSameAsAny;
    }

    public void appear(float transformationAmount, TransformableView otherView) {
        if (transformationAmount == 0.0f) {
            prepareFadeIn();
        }
        CrossFadeHelper.fadeIn(this.mTransformedView, transformationAmount);
    }

    public void disappear(float transformationAmount, TransformableView otherView) {
        CrossFadeHelper.fadeOut(this.mTransformedView, transformationAmount);
    }

    public static TransformState createFrom(View view, TransformInfo transformInfo) {
        if (view instanceof TextView) {
            TextViewTransformState result = TextViewTransformState.obtain();
            result.initFrom(view, transformInfo);
            return result;
        }
        if (view.getId() == 16908685) {
            ActionListTransformState result2 = ActionListTransformState.obtain();
            result2.initFrom(view, transformInfo);
            return result2;
        }
        if (view.getId() == 16909125) {
            MessagingLayoutTransformState result3 = MessagingLayoutTransformState.obtain();
            result3.initFrom(view, transformInfo);
            return result3;
        }
        if (view instanceof MessagingImageMessage) {
            MessagingImageTransformState result4 = MessagingImageTransformState.obtain();
            result4.initFrom(view, transformInfo);
            return result4;
        }
        if (view instanceof ImageView) {
            ImageTransformState result5 = ImageTransformState.obtain();
            result5.initFrom(view, transformInfo);
            if (view.getId() == 16909229) {
                result5.setIsSameAsAnyView(true);
            }
            return result5;
        }
        if (view instanceof ProgressBar) {
            ProgressTransformState result6 = ProgressTransformState.obtain();
            result6.initFrom(view, transformInfo);
            return result6;
        }
        TransformState result7 = obtain();
        result7.initFrom(view, transformInfo);
        return result7;
    }

    public void setIsSameAsAnyView(boolean sameAsAny) {
        this.mSameAsAny = sameAsAny;
    }

    public void recycle() {
        reset();
        if (getClass() == TransformState.class) {
            sInstancePool.release(this);
        }
    }

    public void setTransformationEndY(float transformationEndY) {
        this.mTransformationEndY = transformationEndY;
    }

    public float getTransformationStartX() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_x_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public float getTransformationStartY() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_y_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public float getTransformationStartScaleX() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_scale_x_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public float getTransformationStartScaleY() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_scale_y_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public void setTransformationStartX(float transformationStartX) {
        this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf(transformationStartX));
    }

    public void setTransformationStartY(float transformationStartY) {
        this.mTransformedView.setTag(R.id.transformation_start_y_tag, Float.valueOf(transformationStartY));
    }

    private void setTransformationStartScaleX(float startScaleX) {
        this.mTransformedView.setTag(R.id.transformation_start_scale_x_tag, Float.valueOf(startScaleX));
    }

    private void setTransformationStartScaleY(float startScaleY) {
        this.mTransformedView.setTag(R.id.transformation_start_scale_y_tag, Float.valueOf(startScaleY));
    }

    protected void reset() {
        this.mTransformedView = null;
        this.mTransformInfo = null;
        this.mSameAsAny = false;
        this.mTransformationEndX = -1.0f;
        this.mTransformationEndY = -1.0f;
        this.mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;
    }

    public void setVisible(boolean visible, boolean force) {
        if (force || this.mTransformedView.getVisibility() != 8) {
            if (this.mTransformedView.getVisibility() != 8) {
                this.mTransformedView.setVisibility(visible ? 0 : 4);
            }
            this.mTransformedView.animate().cancel();
            this.mTransformedView.setAlpha(visible ? 1.0f : 0.0f);
            resetTransformedView();
        }
    }

    public void prepareFadeIn() {
        resetTransformedView();
    }

    protected void resetTransformedView() {
        this.mTransformedView.setTranslationX(0.0f);
        this.mTransformedView.setTranslationY(0.0f);
        this.mTransformedView.setScaleX(1.0f);
        this.mTransformedView.setScaleY(1.0f);
        setClippingDeactivated(this.mTransformedView, false);
        abortTransformation();
    }

    public void abortTransformation() {
        this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf(-1.0f));
        this.mTransformedView.setTag(R.id.transformation_start_y_tag, Float.valueOf(-1.0f));
        this.mTransformedView.setTag(R.id.transformation_start_scale_x_tag, Float.valueOf(-1.0f));
        this.mTransformedView.setTag(R.id.transformation_start_scale_y_tag, Float.valueOf(-1.0f));
    }

    public static TransformState obtain() {
        TransformState instance = (TransformState) sInstancePool.acquire();
        if (instance != null) {
            return instance;
        }
        return new TransformState();
    }

    public View getTransformedView() {
        return this.mTransformedView;
    }

    public void setDefaultInterpolator(Interpolator interpolator) {
        this.mDefaultInterpolator = interpolator;
    }
}
