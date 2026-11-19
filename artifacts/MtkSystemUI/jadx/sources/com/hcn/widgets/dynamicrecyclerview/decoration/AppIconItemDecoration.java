package com.hcn.widgets.dynamicrecyclerview.decoration;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class AppIconItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {R.attr.listDivider};
    private Rect mBounds = new Rect();
    private Context mContext;
    private Drawable mDivider;
    private int mDividerSpacing;
    private int mFooterNoShowSize;
    private int mHeaderNoShowSize;
    private int mLeftTopPadding;
    private int mOrientation;
    private Paint mPaint;
    private RecyclerView mRecyclerView;
    private int mRightBottomPadding;

    public AppIconItemDecoration(Context context, int orientation, int headerNoShowSize, int footerNoShowSize) {
        this.mHeaderNoShowSize = 0;
        this.mFooterNoShowSize = 1;
        this.mContext = context;
        this.mHeaderNoShowSize = headerNoShowSize;
        this.mFooterNoShowSize = footerNoShowSize;
        setOrientation(orientation);
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        a.recycle();
    }

    public AppIconItemDecoration setOrientation(int orientation) {
        if (orientation != 0 && orientation != 1) {
            throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = orientation;
        return this;
    }

    public AppIconItemDecoration setDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("drawable-mdpi cannot be null.");
        }
        this.mDivider = drawable;
        return this;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() != null) {
            if (this.mDivider == null && this.mPaint == null) {
                return;
            }
            if (this.mOrientation == 1) {
                drawVertical(canvas, parent, state);
            } else {
                drawHorizontal(canvas, parent, state);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x00c2  */
    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        canvas.save();
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }
        int left2 = left;
        int right2 = right;
        int childCount = parent.getChildCount();
        int lastPosition = state.getItemCount() - 1;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < childCount) {
                View child = parent.getChildAt(i2);
                int childRealPosition = parent.getChildAdapterPosition(child);
                if (childRealPosition >= this.mHeaderNoShowSize) {
                    if (childRealPosition <= lastPosition - this.mFooterNoShowSize) {
                        if (this.mDivider != null) {
                            parent.getDecoratedBoundsWithMargins(child, this.mBounds);
                            int bottom = this.mBounds.bottom + Math.round(child.getTranslationY());
                            int top = bottom - this.mDivider.getIntrinsicHeight();
                            this.mDivider.setBounds(left2, top, right2, bottom);
                            this.mDivider.draw(canvas);
                        }
                        if (this.mPaint != null) {
                            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                            int left1 = left2 + this.mLeftTopPadding;
                            int right1 = right2 - this.mRightBottomPadding;
                            int top1 = child.getBottom() + params.bottomMargin;
                            int bottom1 = top1 + this.mDividerSpacing;
                            canvas.drawRect(left1, top1, right1, bottom1, this.mPaint);
                        }
                    }
                }
                i = i2 + 1;
                left2 = left2;
            } else {
                canvas.restore();
                return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x00c2  */
    private void drawHorizontal(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int top;
        int bottom;
        canvas.save();
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }
        int top2 = top;
        int bottom2 = bottom;
        int childCount = parent.getChildCount();
        int lastPosition = state.getItemCount() - 1;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < childCount) {
                View child = parent.getChildAt(i2);
                int childRealPosition = parent.getChildAdapterPosition(child);
                if (childRealPosition >= this.mHeaderNoShowSize) {
                    if (childRealPosition <= lastPosition - this.mFooterNoShowSize) {
                        if (this.mDivider != null) {
                            parent.getDecoratedBoundsWithMargins(child, this.mBounds);
                            int right = this.mBounds.right + Math.round(child.getTranslationX());
                            int left = right - this.mDivider.getIntrinsicWidth();
                            this.mDivider.setBounds(left, top2, right, bottom2);
                            this.mDivider.draw(canvas);
                        }
                        if (this.mPaint != null) {
                            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                            int left1 = child.getRight() + params.rightMargin;
                            int right1 = left1 + this.mDividerSpacing;
                            int top1 = top2 + this.mLeftTopPadding;
                            int bottom1 = bottom2 - this.mRightBottomPadding;
                            canvas.drawRect(left1, top1, right1, bottom1, this.mPaint);
                        }
                    }
                }
                i = i2 + 1;
                top2 = top2;
            } else {
                canvas.restore();
                return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (this.mDivider == null && this.mPaint == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        int lastPosition = state.getItemCount() - 1;
        int position = parent.getChildAdapterPosition(view);
        if (this.mRecyclerView == null) {
            this.mRecyclerView = parent;
        }
        boolean isShowDivider = this.mHeaderNoShowSize <= position && position <= lastPosition - this.mFooterNoShowSize;
        if (this.mOrientation == 1) {
            if (isShowDivider) {
                outRect.set(0, 0, 0, this.mDivider != null ? this.mDivider.getIntrinsicHeight() : this.mDividerSpacing);
                return;
            } else {
                outRect.set(0, 0, 0, 0);
                return;
            }
        }
        if (isShowDivider) {
            outRect.set(0, 0, this.mDivider != null ? this.mDivider.getIntrinsicWidth() : this.mDividerSpacing, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
