package com.android.systemui.recents.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.IntProperty;
import android.util.Property;
import android.view.animation.Interpolator;
import com.android.internal.policy.DockedDividerUtils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.recents.Recents;
import com.android.systemui.shared.recents.utilities.Utilities;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DockState implements DropTarget {
    public final int createMode;
    private final RectF dockArea;
    public final int dockSide;
    private final RectF expandedTouchDockArea;
    private final RectF touchArea;
    public final ViewState viewState;
    public static final DockState NONE = new DockState(-1, -1, 80, 255, 0, null, null, null);
    public static final DockState LEFT = new DockState(1, 0, 80, 0, 1, new RectF(0.0f, 0.0f, 0.125f, 1.0f), new RectF(0.0f, 0.0f, 0.125f, 1.0f), new RectF(0.0f, 0.0f, 0.5f, 1.0f));
    public static final DockState TOP = new DockState(2, 0, 80, 0, 0, new RectF(0.0f, 0.0f, 1.0f, 0.125f), new RectF(0.0f, 0.0f, 1.0f, 0.125f), new RectF(0.0f, 0.0f, 1.0f, 0.5f));
    public static final DockState RIGHT = new DockState(3, 1, 80, 0, 1, new RectF(0.875f, 0.0f, 1.0f, 1.0f), new RectF(0.875f, 0.0f, 1.0f, 1.0f), new RectF(0.5f, 0.0f, 1.0f, 1.0f));
    public static final DockState BOTTOM = new DockState(4, 1, 80, 0, 0, new RectF(0.0f, 0.875f, 1.0f, 1.0f), new RectF(0.0f, 0.875f, 1.0f, 1.0f), new RectF(0.0f, 0.5f, 1.0f, 1.0f));
    private static final Rect mTmpRect = new Rect();

    @Override // com.android.systemui.recents.views.DropTarget
    public boolean acceptsDrop(int x, int y, int width, int height, Rect insets, boolean isCurrentTarget) {
        if (isCurrentTarget) {
            getMappedRect(this.expandedTouchDockArea, width, height, mTmpRect);
            return mTmpRect.contains(x, y);
        }
        getMappedRect(this.touchArea, width, height, mTmpRect);
        updateBoundsWithSystemInsets(mTmpRect, insets);
        return mTmpRect.contains(x, y);
    }

    public static class ViewState {
        private static final IntProperty<ViewState> HINT_ALPHA = new IntProperty<ViewState>("drawableAlpha") { // from class: com.android.systemui.recents.views.DockState.ViewState.1
            @Override // android.util.IntProperty
            public void setValue(ViewState object, int alpha) {
                object.mHintTextAlpha = alpha;
                object.dockAreaOverlay.invalidateSelf();
            }

            @Override // android.util.Property
            public Integer get(ViewState object) {
                return Integer.valueOf(object.mHintTextAlpha);
            }
        };
        public final int dockAreaAlpha;
        public final ColorDrawable dockAreaOverlay;
        public final int hintTextAlpha;
        public final int hintTextOrientation;
        private AnimatorSet mDockAreaOverlayAnimator;
        private String mHintText;
        private int mHintTextAlpha;
        private Point mHintTextBounds;
        private Paint mHintTextPaint;
        private final int mHintTextResId;
        private Rect mTmpRect;

        private ViewState(int areaAlpha, int hintAlpha, int hintOrientation, int hintTextResId) {
            this.mHintTextBounds = new Point();
            this.mHintTextAlpha = 255;
            this.mTmpRect = new Rect();
            this.dockAreaAlpha = areaAlpha;
            this.dockAreaOverlay = new ColorDrawable(Recents.getConfiguration().isGridEnabled ? -16777216 : -1);
            this.dockAreaOverlay.setAlpha(0);
            this.hintTextAlpha = hintAlpha;
            this.hintTextOrientation = hintOrientation;
            this.mHintTextResId = hintTextResId;
            this.mHintTextPaint = new Paint(1);
            this.mHintTextPaint.setColor(-1);
        }

        public void update(Context context) {
            Resources res = context.getResources();
            this.mHintText = context.getString(this.mHintTextResId);
            this.mHintTextPaint.setTextSize(res.getDimensionPixelSize(R.dimen.recents_drag_hint_text_size));
            this.mHintTextPaint.getTextBounds(this.mHintText, 0, this.mHintText.length(), this.mTmpRect);
            this.mHintTextBounds.set((int) this.mHintTextPaint.measureText(this.mHintText), this.mTmpRect.height());
        }

        public void draw(Canvas canvas) {
            if (this.dockAreaOverlay.getAlpha() > 0) {
                this.dockAreaOverlay.draw(canvas);
            }
            if (this.mHintTextAlpha > 0) {
                Rect bounds = this.dockAreaOverlay.getBounds();
                int x = bounds.left + ((bounds.width() - this.mHintTextBounds.x) / 2);
                int y = bounds.top + ((bounds.height() + this.mHintTextBounds.y) / 2);
                this.mHintTextPaint.setAlpha(this.mHintTextAlpha);
                if (this.hintTextOrientation == 1) {
                    canvas.save();
                    canvas.rotate(-90.0f, bounds.centerX(), bounds.centerY());
                }
                canvas.drawText(this.mHintText, x, y, this.mHintTextPaint);
                if (this.hintTextOrientation == 1) {
                    canvas.restore();
                }
            }
        }

        public void startAnimation(Rect bounds, int areaAlpha, int hintAlpha, int duration, Interpolator interpolator, boolean animateAlpha, boolean animateBounds) {
            Interpolator interpolator2;
            if (this.mDockAreaOverlayAnimator != null) {
                this.mDockAreaOverlayAnimator.cancel();
            }
            ArrayList<Animator> animators = new ArrayList<>();
            if (this.dockAreaOverlay.getAlpha() != areaAlpha) {
                if (animateAlpha) {
                    ObjectAnimator anim = ObjectAnimator.ofInt(this.dockAreaOverlay, (Property<ColorDrawable, Integer>) Utilities.DRAWABLE_ALPHA, this.dockAreaOverlay.getAlpha(), areaAlpha);
                    anim.setDuration(duration);
                    anim.setInterpolator(interpolator);
                    animators.add(anim);
                } else {
                    this.dockAreaOverlay.setAlpha(areaAlpha);
                }
            }
            if (this.mHintTextAlpha != hintAlpha) {
                if (animateAlpha) {
                    ObjectAnimator anim2 = ObjectAnimator.ofInt(this, HINT_ALPHA, this.mHintTextAlpha, hintAlpha);
                    anim2.setDuration(150L);
                    if (hintAlpha > this.mHintTextAlpha) {
                        interpolator2 = Interpolators.ALPHA_IN;
                    } else {
                        interpolator2 = Interpolators.ALPHA_OUT;
                    }
                    anim2.setInterpolator(interpolator2);
                    animators.add(anim2);
                } else {
                    this.mHintTextAlpha = hintAlpha;
                    this.dockAreaOverlay.invalidateSelf();
                }
            }
            if (bounds != null && !this.dockAreaOverlay.getBounds().equals(bounds)) {
                if (0 != 0) {
                    PropertyValuesHolder prop = PropertyValuesHolder.ofObject(Utilities.DRAWABLE_RECT, Utilities.RECT_EVALUATOR, new Rect(this.dockAreaOverlay.getBounds()), bounds);
                    ObjectAnimator anim3 = ObjectAnimator.ofPropertyValuesHolder(this.dockAreaOverlay, prop);
                    anim3.setDuration(duration);
                    anim3.setInterpolator(interpolator);
                    animators.add(anim3);
                } else {
                    this.dockAreaOverlay.setBounds(bounds);
                }
            }
            if (!animators.isEmpty()) {
                this.mDockAreaOverlayAnimator = new AnimatorSet();
                this.mDockAreaOverlayAnimator.playTogether(animators);
                this.mDockAreaOverlayAnimator.start();
            }
        }
    }

    DockState(int dockSide, int createMode, int dockAreaAlpha, int hintTextAlpha, int hintTextOrientation, RectF touchArea, RectF dockArea, RectF expandedTouchDockArea) {
        this.dockSide = dockSide;
        this.createMode = createMode;
        this.viewState = new ViewState(dockAreaAlpha, hintTextAlpha, hintTextOrientation, R.string.recents_drag_hint_message);
        this.dockArea = dockArea;
        this.touchArea = touchArea;
        this.expandedTouchDockArea = expandedTouchDockArea;
    }

    public void update(Context context) {
        this.viewState.update(context);
    }

    public Rect getPreDockedBounds(int width, int height, Rect insets) {
        getMappedRect(this.dockArea, width, height, mTmpRect);
        return updateBoundsWithSystemInsets(mTmpRect, insets);
    }

    public Rect getDockedBounds(int width, int height, int dividerSize, Rect insets, Resources res) {
        boolean isHorizontalDivision = res.getConfiguration().orientation == 1;
        int position = DockedDividerUtils.calculateMiddlePosition(isHorizontalDivision, insets, width, height, dividerSize);
        Rect newWindowBounds = new Rect();
        DockedDividerUtils.calculateBoundsForPosition(position, this.dockSide, newWindowBounds, width, height, dividerSize);
        return newWindowBounds;
    }

    public Rect getDockedTaskStackBounds(Rect displayRect, int width, int height, int dividerSize, Rect insets, TaskStackLayoutAlgorithm layoutAlgorithm, Resources res, Rect windowRectOut) {
        boolean isHorizontalDivision = res.getConfiguration().orientation == 1;
        int position = DockedDividerUtils.calculateMiddlePosition(isHorizontalDivision, insets, width, height, dividerSize);
        DockedDividerUtils.calculateBoundsForPosition(position, DockedDividerUtils.invertDockSide(this.dockSide), windowRectOut, width, height, dividerSize);
        Rect taskStackBounds = new Rect();
        int top = this.dockArea.bottom >= 1.0f ? insets.top : 0;
        layoutAlgorithm.getTaskStackBounds(displayRect, windowRectOut, top, 0, insets.right, taskStackBounds);
        return taskStackBounds;
    }

    private Rect updateBoundsWithSystemInsets(Rect bounds, Rect insets) {
        if (this.dockSide == 1) {
            bounds.right += insets.left;
        } else if (this.dockSide == 3) {
            bounds.left -= insets.right;
        }
        return bounds;
    }

    private void getMappedRect(RectF bounds, int width, int height, Rect out) {
        out.set((int) (bounds.left * width), (int) (bounds.top * height), (int) (bounds.right * width), (int) (bounds.bottom * height));
    }
}
