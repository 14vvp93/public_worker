package com.android.systemui.qs;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.systemui.R;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class PseudoGridView extends ViewGroup {
    private int mHorizontalSpacing;
    private int mNumColumns;
    private int mVerticalSpacing;

    public PseudoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNumColumns = 3;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PseudoGridView);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case 0:
                    this.mHorizontalSpacing = a.getDimensionPixelSize(attr, 0);
                    break;
                case 1:
                    this.mNumColumns = a.getInt(attr, 3);
                    break;
                case 2:
                    this.mVerticalSpacing = a.getDimensionPixelSize(attr, 0);
                    break;
            }
        }
        a.recycle();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) == 0) {
            throw new UnsupportedOperationException("Needs a maximum width");
        }
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int childWidth = (width - ((this.mNumColumns - 1) * this.mHorizontalSpacing)) / this.mNumColumns;
        int i = 1073741824;
        int childWidthSpec = View.MeasureSpec.makeMeasureSpec(childWidth, 1073741824);
        int children = getChildCount();
        int rows = ((this.mNumColumns + children) - 1) / this.mNumColumns;
        int totalHeight = 0;
        int totalHeight2 = 0;
        while (totalHeight2 < rows) {
            int startOfRow = this.mNumColumns * totalHeight2;
            int endOfRow = Math.min(this.mNumColumns + startOfRow, children);
            int maxHeight = 0;
            for (int maxHeight2 = startOfRow; maxHeight2 < endOfRow; maxHeight2++) {
                View child = getChildAt(maxHeight2);
                child.measure(childWidthSpec, 0);
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
            int maxHeightSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, i);
            for (int i2 = startOfRow; i2 < endOfRow; i2++) {
                View child2 = getChildAt(i2);
                if (child2.getMeasuredHeight() != maxHeight) {
                    child2.measure(childWidthSpec, maxHeightSpec);
                }
            }
            totalHeight += maxHeight;
            if (totalHeight2 > 0) {
                totalHeight += this.mVerticalSpacing;
            }
            totalHeight2++;
            i = 1073741824;
        }
        setMeasuredDimension(width, resolveSizeAndState(totalHeight, heightMeasureSpec, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        boolean isRtl = isLayoutRtl();
        int children = getChildCount();
        int rows = ((this.mNumColumns + children) - 1) / this.mNumColumns;
        int y = 0;
        for (int y2 = 0; y2 < rows; y2++) {
            int x = isRtl ? getWidth() : 0;
            int maxHeight = 0;
            int startOfRow = this.mNumColumns * y2;
            int endOfRow = Math.min(this.mNumColumns + startOfRow, children);
            int x2 = x;
            for (int x3 = startOfRow; x3 < endOfRow; x3++) {
                View child = getChildAt(x3);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                if (isRtl) {
                    x2 -= width;
                }
                child.layout(x2, y, x2 + width, y + height);
                maxHeight = Math.max(maxHeight, height);
                if (isRtl) {
                    x2 -= this.mHorizontalSpacing;
                } else {
                    x2 += this.mHorizontalSpacing + width;
                }
            }
            y += maxHeight;
            if (y2 > 0) {
                y += this.mVerticalSpacing;
            }
        }
    }

    public static class ViewGroupAdapterBridge extends DataSetObserver {
        private final BaseAdapter mAdapter;
        private boolean mReleased = false;
        private final WeakReference<ViewGroup> mViewGroup;

        public static void link(ViewGroup viewGroup, BaseAdapter adapter) {
            new ViewGroupAdapterBridge(viewGroup, adapter);
        }

        private ViewGroupAdapterBridge(ViewGroup viewGroup, BaseAdapter adapter) {
            this.mViewGroup = new WeakReference<>(viewGroup);
            this.mAdapter = adapter;
            this.mAdapter.registerDataSetObserver(this);
            refresh();
        }

        private void refresh() {
            if (this.mReleased) {
                return;
            }
            ViewGroup viewGroup = this.mViewGroup.get();
            if (viewGroup == null) {
                release();
                return;
            }
            int childCount = viewGroup.getChildCount();
            int adapterCount = this.mAdapter.getCount();
            int N = Math.max(childCount, adapterCount);
            for (int i = 0; i < N; i++) {
                if (i < adapterCount) {
                    View oldView = null;
                    if (i < childCount) {
                        oldView = viewGroup.getChildAt(i);
                    }
                    View newView = this.mAdapter.getView(i, oldView, viewGroup);
                    if (oldView == null) {
                        viewGroup.addView(newView);
                    } else if (oldView != newView) {
                        viewGroup.removeViewAt(i);
                        viewGroup.addView(newView, i);
                    }
                } else {
                    int lastIndex = viewGroup.getChildCount() - 1;
                    viewGroup.removeViewAt(lastIndex);
                }
            }
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            refresh();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            release();
        }

        private void release() {
            if (!this.mReleased) {
                this.mReleased = true;
                this.mAdapter.unregisterDataSetObserver(this);
            }
        }
    }
}
