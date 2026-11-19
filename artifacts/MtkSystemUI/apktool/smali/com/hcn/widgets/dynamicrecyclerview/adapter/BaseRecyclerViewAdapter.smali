.class public abstract Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;
.super Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;
.source "BaseRecyclerViewAdapter.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter<",
        "TT;",
        "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<",
        "TT;>;>;"
    }
.end annotation


# instance fields
.field private mLayoutId:I

.field protected onItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field protected onItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;


# direct methods
.method protected constructor <init>(I)V
    .locals 0
    .param p1, "layoutId"    # I

    .line 23
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;-><init>()V

    .line 24
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->mLayoutId:I

    .line 25
    return-void
.end method


# virtual methods
.method public abstract bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Ljava/lang/Object;I)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<",
            "TT;>;TT;I)V"
        }
    .end annotation
.end method

.method public bridge synthetic getItemCount()I
    .locals 1

    .line 16
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    invoke-super {p0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->getItemCount()I

    move-result v0

    return v0
.end method

.method public bridge synthetic onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V
    .locals 0

    .line 16
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    check-cast p1, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onBindViewHolder(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;I)V
    .locals 2
    .param p1, "holder"    # Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;
    .param p2, "position"    # I

    .line 51
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    invoke-super {p0, p1, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->onBindViewHolder(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;I)V

    .line 52
    iget-object v0, p1, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->itemView:Landroid/view/View;

    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;

    invoke-direct {v1, p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;)V

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 61
    iget-object v0, p1, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->itemView:Landroid/view/View;

    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;

    invoke-direct {v1, p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;)V

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V

    .line 72
    return-void
.end method

.method public bridge synthetic onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
    .locals 0

    .line 16
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    invoke-virtual {p0, p1, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    move-result-object p1

    return-object p1
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;
    .locals 2
    .param p1, "parent"    # Landroid/view/ViewGroup;
    .param p2, "viewType"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/view/ViewGroup;",
            "I)",
            "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<",
            "TT;>;"
        }
    .end annotation

    .line 46
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->mLayoutId:I

    invoke-direct {v0, p0, p1, v1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;Landroid/view/ViewGroup;I)V

    return-object v0
.end method

.method public bridge synthetic setData(Ljava/util/List;)V
    .locals 0

    .line 16
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    invoke-super {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->setData(Ljava/util/List;)V

    return-void
.end method

.method public setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V
    .locals 0
    .param p1, "onItemClickListener"    # Landroid/widget/AdapterView$OnItemClickListener;

    .line 78
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 79
    return-void
.end method

.method public setOnItemLongClickListener(Landroid/widget/AdapterView$OnItemLongClickListener;)V
    .locals 0
    .param p1, "onItemLongClickListener"    # Landroid/widget/AdapterView$OnItemLongClickListener;

    .line 83
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>;"
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

    .line 84
    return-void
.end method
