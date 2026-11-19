package com.hcn.widgets.dynamicrecyclerview.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.hcn.widgets.dynamicrecyclerview.adapter.BaseViewHolder;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public abstract class BaseAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {
    public List<T> mData;

    protected BaseAdapter() {
    }

    public void setData(List<T> data) {
        this.mData = data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(K holder, int position) {
        holder.onBaseBindView(this.mData.get(position), position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.mData == null) {
            return 0;
        }
        return this.mData.size();
    }
}
