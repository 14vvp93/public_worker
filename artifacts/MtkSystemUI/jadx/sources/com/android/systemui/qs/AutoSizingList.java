package com.android.systemui.qs;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class AutoSizingList extends LinearLayout {
    private ListAdapter mAdapter;
    private final Runnable mBindChildren;
    private int mCount;
    private final DataSetObserver mDataObserver;
    private boolean mEnableAutoSizing;
    private final Handler mHandler;
    private final int mItemSize;

    public AutoSizingList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBindChildren = new Runnable() { // from class: com.android.systemui.qs.AutoSizingList.1
            @Override // java.lang.Runnable
            public void run() {
                AutoSizingList.this.rebindChildren();
            }
        };
        this.mDataObserver = new DataSetObserver() { // from class: com.android.systemui.qs.AutoSizingList.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                if (AutoSizingList.this.mCount > AutoSizingList.this.getDesiredCount()) {
                    AutoSizingList.this.mCount = AutoSizingList.this.getDesiredCount();
                }
                AutoSizingList.this.postRebindChildren();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                AutoSizingList.this.postRebindChildren();
            }
        };
        this.mHandler = new Handler();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoSizingList);
        this.mItemSize = a.getDimensionPixelSize(1, 0);
        this.mEnableAutoSizing = a.getBoolean(0, true);
        a.recycle();
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerDataSetObserver(this.mDataObserver);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count;
        int requestedHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        if (requestedHeight != 0 && this.mCount != (count = getItemCount(requestedHeight))) {
            postRebindChildren();
            this.mCount = count;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int getItemCount(int requestedHeight) {
        int desiredCount = getDesiredCount();
        return this.mEnableAutoSizing ? Math.min(requestedHeight / this.mItemSize, desiredCount) : desiredCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDesiredCount() {
        if (this.mAdapter != null) {
            return this.mAdapter.getCount();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postRebindChildren() {
        this.mHandler.post(this.mBindChildren);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebindChildren() {
        if (this.mAdapter == null) {
            return;
        }
        int i = 0;
        while (i < this.mCount) {
            View v = i < getChildCount() ? getChildAt(i) : null;
            View newView = this.mAdapter.getView(i, v, this);
            if (newView != v) {
                if (v != null) {
                    removeView(v);
                }
                addView(newView, i);
            }
            i++;
        }
        while (i > this.mCount) {
            removeViewAt(getChildCount() - 1);
        }
    }
}
