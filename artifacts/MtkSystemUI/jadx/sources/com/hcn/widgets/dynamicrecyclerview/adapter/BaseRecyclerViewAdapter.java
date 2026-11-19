package com.hcn.widgets.dynamicrecyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseRecyclerViewAdapter<T> extends BaseAdapter<T, BaseViewHolder<T>> {
    private int mLayoutId;
    protected AdapterView.OnItemClickListener onItemClickListener;
    protected AdapterView.OnItemLongClickListener onItemLongClickListener;

    public abstract void bindView(BaseViewHolder<T> baseViewHolder, T t, int i);

    @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ int getItemCount() {
        return super.getItemCount();
    }

    @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseAdapter
    public /* bridge */ /* synthetic */ void setData(List list) {
        super.setData(list);
    }

    protected BaseRecyclerViewAdapter(int layoutId) {
        this.mLayoutId = layoutId;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(parent, this.mLayoutId);
    }

    @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BaseRecyclerViewAdapter.this.onItemClickListener != null) {
                    BaseRecyclerViewAdapter.this.onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (BaseRecyclerViewAdapter.this.onItemLongClickListener != null) {
                    return BaseRecyclerViewAdapter.this.onItemLongClickListener.onItemLongClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
                }
                return false;
            }
        });
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private class RecyclerViewHolder extends BaseViewHolder<T> {
        RecyclerViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseViewHolder
        protected void onBaseBindView(T bean, int position) {
            BaseRecyclerViewAdapter.this.bindView(this, bean, position);
        }
    }
}
