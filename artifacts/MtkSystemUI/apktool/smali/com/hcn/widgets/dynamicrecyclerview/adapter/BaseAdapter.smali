.class abstract Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;
.super Landroidx/recyclerview/widget/RecyclerView$Adapter;
.source "BaseAdapter.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        "K:",
        "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;",
        ">",
        "Landroidx/recyclerview/widget/RecyclerView$Adapter<",
        "TK;>;"
    }
.end annotation


# instance fields
.field public mData:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "TT;>;"
        }
    .end annotation
.end field


# direct methods
.method protected constructor <init>()V
    .locals 0

    .line 17
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter<TT;TK;>;"
    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;-><init>()V

    .line 19
    return-void
.end method


# virtual methods
.method public getItemCount()I
    .locals 1

    .line 36
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter<TT;TK;>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->mData:Ljava/util/List;

    if-nez v0, :cond_0

    const/4 v0, 0x0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->mData:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    :goto_0
    return v0
.end method

.method public bridge synthetic onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V
    .locals 0

    .line 13
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter<TT;TK;>;"
    check-cast p1, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->onBindViewHolder(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;I)V
    .locals 1
    .param p2, "position"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TK;I)V"
        }
    .end annotation

    .line 31
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter<TT;TK;>;"
    .local p1, "holder":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "TK;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->mData:Ljava/util/List;

    invoke-interface {v0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {p1, v0, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->onBaseBindView(Ljava/lang/Object;I)V

    .line 32
    return-void
.end method

.method public setData(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "TT;>;)V"
        }
    .end annotation

    .line 26
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter<TT;TK;>;"
    .local p1, "data":Ljava/util/List;, "Ljava/util/List<TT;>;"
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseAdapter;->mData:Ljava/util/List;

    .line 27
    return-void
.end method
