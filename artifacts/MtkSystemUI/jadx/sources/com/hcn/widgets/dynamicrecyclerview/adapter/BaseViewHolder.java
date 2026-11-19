package com.hcn.widgets.dynamicrecyclerview.adapter;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;

    protected abstract void onBaseBindView(T t, int i);

    public BaseViewHolder(ViewGroup viewGroup, int layoutId) {
        this(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <V extends View> V getView(int i) {
        V v = (V) this.views.get(i);
        if (v == null) {
            V v2 = (V) this.itemView.findViewById(i);
            this.views.put(i, v2);
            return v2;
        }
        return v;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
}
