package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.stack.AnimationProperties;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExpandableOutlineView extends ExpandableView {
    private boolean mAlwaysRoundBothCorners;
    private int mBackgroundTop;
    private float mBottomRoundness;
    private final Path mClipPath;
    private float mCurrentBottomRoundness;
    private float mCurrentTopRoundness;
    private boolean mCustomOutline;
    private float mDistanceToTopRoundness;
    private float mExtraWidthForClipping;
    private int mMinimumHeightForClipping;
    private float mOutlineAlpha;
    protected float mOutlineRadius;
    private final Rect mOutlineRect;
    private final ViewOutlineProvider mProvider;
    protected boolean mShouldTranslateContents;
    private Path mTmpPath;
    private Path mTmpPath2;
    private boolean mTopAmountRounded;
    private float mTopRoundness;
    private static final AnimatableProperty TOP_ROUNDNESS = AnimatableProperty.from("topRoundness", new BiConsumer() { // from class: com.android.systemui.statusbar.-$$Lambda$ExpandableOutlineView$-E-rMo_VNnRS1oxWSSK8iT4OIlo
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ExpandableOutlineView) obj).setTopRoundnessInternal(((Float) obj2).floatValue());
        }
    }, new Function() { // from class: com.android.systemui.statusbar.-$$Lambda$5MBgGbVXpg7XHgCv0UsDp2v7mps
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Float.valueOf(((ExpandableOutlineView) obj).getCurrentTopRoundness());
        }
    }, R.id.top_roundess_animator_tag, R.id.top_roundess_animator_end_tag, R.id.top_roundess_animator_start_tag);
    private static final AnimatableProperty BOTTOM_ROUNDNESS = AnimatableProperty.from("bottomRoundness", new BiConsumer() { // from class: com.android.systemui.statusbar.-$$Lambda$ExpandableOutlineView$CDvYEUsuxLuUwEGYNcJdVKtGE1E
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ExpandableOutlineView) obj).setBottomRoundnessInternal(((Float) obj2).floatValue());
        }
    }, new Function() { // from class: com.android.systemui.statusbar.-$$Lambda$CcOzLRI6prAuqo1UrMutjbjeKjw
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Float.valueOf(((ExpandableOutlineView) obj).getCurrentBottomRoundness());
        }
    }, R.id.bottom_roundess_animator_tag, R.id.bottom_roundess_animator_end_tag, R.id.bottom_roundess_animator_start_tag);
    private static final AnimationProperties ROUNDNESS_PROPERTIES = new AnimationProperties().setDuration(360);
    private static final Path EMPTY_PATH = new Path();

    /* JADX INFO: Access modifiers changed from: private */
    public Path getClipPath() {
        return getClipPath(false, false);
    }

    protected Path getClipPath(boolean ignoreTranslation, boolean clipRoundedToBottom) {
        int left;
        int top;
        int right;
        int bottom;
        Path intersectPath = null;
        if (!this.mCustomOutline) {
            int translation = (!this.mShouldTranslateContents || ignoreTranslation) ? 0 : (int) getTranslation();
            left = Math.max(translation, 0);
            top = this.mClipTopAmount + this.mBackgroundTop;
            right = Math.min(translation, 0) + getWidth();
            bottom = Math.max(getActualHeight(), top);
            int intersectBottom = Math.max(getActualHeight() - this.mClipBottomAmount, top);
            if (bottom != intersectBottom) {
                if (clipRoundedToBottom) {
                    bottom = intersectBottom;
                } else {
                    getRoundedRectPath(left, top, right, intersectBottom, 0.0f, 0.0f, this.mTmpPath2);
                    intersectPath = this.mTmpPath2;
                }
            }
        } else {
            left = this.mOutlineRect.left;
            top = this.mOutlineRect.top;
            right = this.mOutlineRect.right;
            bottom = this.mOutlineRect.bottom;
        }
        int right2 = right;
        int bottom2 = bottom;
        int bottom3 = bottom2 - top;
        if (bottom3 == 0) {
            return EMPTY_PATH;
        }
        float topRoundness = this.mAlwaysRoundBothCorners ? this.mOutlineRadius : getCurrentBackgroundRadiusTop();
        float bottomRoundness = this.mAlwaysRoundBothCorners ? this.mOutlineRadius : getCurrentBackgroundRadiusBottom();
        if (topRoundness + bottomRoundness > bottom3) {
            float overShoot = (topRoundness + bottomRoundness) - bottom3;
            topRoundness -= (this.mCurrentTopRoundness * overShoot) / (this.mCurrentTopRoundness + this.mCurrentBottomRoundness);
            bottomRoundness -= (this.mCurrentBottomRoundness * overShoot) / (this.mCurrentTopRoundness + this.mCurrentBottomRoundness);
        }
        getRoundedRectPath(left, top, right2, bottom2, topRoundness, bottomRoundness, this.mTmpPath);
        Path roundedRectPath = this.mTmpPath;
        if (intersectPath != null) {
            roundedRectPath.op(intersectPath, Path.Op.INTERSECT);
        }
        return roundedRectPath;
    }

    public static void getRoundedRectPath(int left, int top, int right, int bottom, float topRoundness, float bottomRoundness, Path outPath) {
        outPath.reset();
        int width = right - left;
        float topRoundnessX = Math.min(width / 2, topRoundness);
        float bottomRoundnessX = Math.min(width / 2, bottomRoundness);
        if (topRoundness > 0.0f) {
            outPath.moveTo(left, top + topRoundness);
            outPath.quadTo(left, top, left + topRoundnessX, top);
            outPath.lineTo(right - topRoundnessX, top);
            outPath.quadTo(right, top, right, top + topRoundness);
        } else {
            outPath.moveTo(left, top);
            outPath.lineTo(right, top);
        }
        if (bottomRoundness > 0.0f) {
            outPath.lineTo(right, bottom - bottomRoundness);
            outPath.quadTo(right, bottom, right - bottomRoundnessX, bottom);
            outPath.lineTo(left + bottomRoundnessX, bottom);
            outPath.quadTo(left, bottom, left, bottom - bottomRoundness);
        } else {
            outPath.lineTo(right, bottom);
            outPath.lineTo(left, bottom);
        }
        outPath.close();
    }

    public ExpandableOutlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOutlineRect = new Rect();
        this.mClipPath = new Path();
        this.mOutlineAlpha = -1.0f;
        this.mTmpPath = new Path();
        this.mTmpPath2 = new Path();
        this.mDistanceToTopRoundness = -1.0f;
        this.mMinimumHeightForClipping = 0;
        this.mProvider = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.ExpandableOutlineView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (ExpandableOutlineView.this.mCustomOutline || ExpandableOutlineView.this.mCurrentTopRoundness != 0.0f || ExpandableOutlineView.this.mCurrentBottomRoundness != 0.0f || ExpandableOutlineView.this.mAlwaysRoundBothCorners || ExpandableOutlineView.this.mTopAmountRounded) {
                    Path clipPath = ExpandableOutlineView.this.getClipPath();
                    if (clipPath != null && clipPath.isConvex()) {
                        outline.setConvexPath(clipPath);
                    }
                } else {
                    int translation = ExpandableOutlineView.this.mShouldTranslateContents ? (int) ExpandableOutlineView.this.getTranslation() : 0;
                    int left = Math.max(translation, 0);
                    int top = ExpandableOutlineView.this.mClipTopAmount + ExpandableOutlineView.this.mBackgroundTop;
                    int right = ExpandableOutlineView.this.getWidth() + Math.min(translation, 0);
                    int bottom = Math.max(ExpandableOutlineView.this.getActualHeight() - ExpandableOutlineView.this.mClipBottomAmount, top);
                    outline.setRect(left, top, right, bottom);
                }
                outline.setAlpha(ExpandableOutlineView.this.mOutlineAlpha);
            }
        };
        setOutlineProvider(this.mProvider);
        initDimens();
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        canvas.save();
        Path intersectPath = null;
        if (this.mTopAmountRounded && topAmountNeedsClipping()) {
            int left = (int) ((-this.mExtraWidthForClipping) / 2.0f);
            int top = (int) (this.mClipTopAmount - this.mDistanceToTopRoundness);
            int right = getWidth() + ((int) (this.mExtraWidthForClipping + left));
            int bottom = (int) Math.max(this.mMinimumHeightForClipping, Math.max(getActualHeight() - this.mClipBottomAmount, top + this.mOutlineRadius));
            getRoundedRectPath(left, top, right, bottom, this.mOutlineRadius, 0.0f, this.mClipPath);
            intersectPath = this.mClipPath;
        }
        int left2 = 0;
        if (childNeedsClipping(child)) {
            Path clipPath = getCustomClipPath(child);
            if (clipPath == null) {
                clipPath = getClipPath();
            }
            if (clipPath != null) {
                if (intersectPath != null) {
                    clipPath.op(intersectPath, Path.Op.INTERSECT);
                }
                canvas.clipPath(clipPath);
                left2 = 1;
            }
        }
        if (left2 == 0 && intersectPath != null) {
            canvas.clipPath(intersectPath);
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return result;
    }

    public void setExtraWidthForClipping(float extraWidthForClipping) {
        this.mExtraWidthForClipping = extraWidthForClipping;
    }

    public void setMinimumHeightForClipping(int minimumHeightForClipping) {
        this.mMinimumHeightForClipping = minimumHeightForClipping;
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setDistanceToTopRoundness(float distanceToTopRoundness) {
        super.setDistanceToTopRoundness(distanceToTopRoundness);
        if (distanceToTopRoundness != this.mDistanceToTopRoundness) {
            this.mTopAmountRounded = distanceToTopRoundness >= 0.0f;
            this.mDistanceToTopRoundness = distanceToTopRoundness;
            applyRoundness();
        }
    }

    protected boolean childNeedsClipping(View child) {
        return false;
    }

    public boolean topAmountNeedsClipping() {
        return true;
    }

    protected boolean isClippingNeeded() {
        return this.mAlwaysRoundBothCorners || this.mCustomOutline || getTranslation() != 0.0f;
    }

    private void initDimens() {
        Resources res = getResources();
        this.mShouldTranslateContents = res.getBoolean(R.bool.config_translateNotificationContentsOnSwipe);
        this.mOutlineRadius = res.getDimension(R.dimen.notification_shadow_radius);
        this.mAlwaysRoundBothCorners = res.getBoolean(R.bool.config_clipNotificationsToOutline);
        if (!this.mAlwaysRoundBothCorners) {
            this.mOutlineRadius = res.getDimensionPixelSize(Utils.getThemeAttr(this.mContext, android.R.attr.dialogCornerRadius));
        }
        setClipToOutline(this.mAlwaysRoundBothCorners);
    }

    public boolean setTopRoundness(float topRoundness, boolean animate) {
        if (this.mTopRoundness != topRoundness) {
            this.mTopRoundness = topRoundness;
            PropertyAnimator.setProperty(this, TOP_ROUNDNESS, topRoundness, ROUNDNESS_PROPERTIES, animate);
            return true;
        }
        return false;
    }

    protected void applyRoundness() {
        invalidateOutline();
        invalidate();
    }

    public float getCurrentBackgroundRadiusTop() {
        if (this.mTopAmountRounded) {
            return this.mOutlineRadius;
        }
        return this.mCurrentTopRoundness * this.mOutlineRadius;
    }

    public float getCurrentTopRoundness() {
        return this.mCurrentTopRoundness;
    }

    public float getCurrentBottomRoundness() {
        return this.mCurrentBottomRoundness;
    }

    protected float getCurrentBackgroundRadiusBottom() {
        return this.mCurrentBottomRoundness * this.mOutlineRadius;
    }

    public boolean setBottomRoundness(float bottomRoundness, boolean animate) {
        if (this.mBottomRoundness != bottomRoundness) {
            this.mBottomRoundness = bottomRoundness;
            PropertyAnimator.setProperty(this, BOTTOM_ROUNDNESS, bottomRoundness, ROUNDNESS_PROPERTIES, animate);
            return true;
        }
        return false;
    }

    protected void setBackgroundTop(int backgroundTop) {
        if (this.mBackgroundTop != backgroundTop) {
            this.mBackgroundTop = backgroundTop;
            invalidateOutline();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTopRoundnessInternal(float topRoundness) {
        this.mCurrentTopRoundness = topRoundness;
        applyRoundness();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBottomRoundnessInternal(float bottomRoundness) {
        this.mCurrentBottomRoundness = bottomRoundness;
        applyRoundness();
    }

    public void onDensityOrFontScaleChanged() {
        initDimens();
        applyRoundness();
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setActualHeight(int actualHeight, boolean notifyListeners) {
        int previousHeight = getActualHeight();
        super.setActualHeight(actualHeight, notifyListeners);
        if (previousHeight != actualHeight) {
            applyRoundness();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setClipTopAmount(int clipTopAmount) {
        int previousAmount = getClipTopAmount();
        super.setClipTopAmount(clipTopAmount);
        if (previousAmount != clipTopAmount) {
            applyRoundness();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public void setClipBottomAmount(int clipBottomAmount) {
        int previousAmount = getClipBottomAmount();
        super.setClipBottomAmount(clipBottomAmount);
        if (previousAmount != clipBottomAmount) {
            applyRoundness();
        }
    }

    protected void setOutlineAlpha(float alpha) {
        if (alpha != this.mOutlineAlpha) {
            this.mOutlineAlpha = alpha;
            applyRoundness();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public float getOutlineAlpha() {
        return this.mOutlineAlpha;
    }

    protected void setOutlineRect(RectF rect) {
        if (rect != null) {
            setOutlineRect(rect.left, rect.top, rect.right, rect.bottom);
        } else {
            this.mCustomOutline = false;
            applyRoundness();
        }
    }

    @Override // com.android.systemui.statusbar.ExpandableView
    public int getOutlineTranslation() {
        return this.mCustomOutline ? this.mOutlineRect.left : (int) getTranslation();
    }

    public void updateOutline() {
        if (this.mCustomOutline) {
            return;
        }
        boolean hasOutline = needsOutline();
        setOutlineProvider(hasOutline ? this.mProvider : null);
    }

    protected boolean needsOutline() {
        if (isChildInGroup()) {
            return isGroupExpanded() && !isGroupExpansionChanging();
        }
        if (isSummaryWithChildren()) {
            return !isGroupExpanded() || isGroupExpansionChanging();
        }
        return true;
    }

    protected void setOutlineRect(float left, float top, float right, float bottom) {
        this.mCustomOutline = true;
        this.mOutlineRect.set((int) left, (int) top, (int) right, (int) bottom);
        this.mOutlineRect.bottom = (int) Math.max(top, this.mOutlineRect.bottom);
        this.mOutlineRect.right = (int) Math.max(left, this.mOutlineRect.right);
        applyRoundness();
    }

    public Path getCustomClipPath(View child) {
        return null;
    }
}
