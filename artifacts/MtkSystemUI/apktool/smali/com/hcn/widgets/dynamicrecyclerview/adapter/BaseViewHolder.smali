.class public abstract Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;
.super Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
.source "BaseViewHolder.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;"
    }
.end annotation


# instance fields
.field private final views:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Landroid/view/View;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Landroid/view/View;)V
    .locals 1
    .param p1, "itemView"    # Landroid/view/View;

    .line 49
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<TT;>;"
    invoke-direct {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;-><init>(Landroid/view/View;)V

    .line 50
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->views:Landroid/util/SparseArray;

    .line 51
    return-void
.end method

.method public constructor <init>(Landroid/view/ViewGroup;I)V
    .locals 2
    .param p1, "viewGroup"    # Landroid/view/ViewGroup;
    .param p2, "layoutId"    # I

    .line 41
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<TT;>;"
    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, p2, p1, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;-><init>(Landroid/view/View;)V

    .line 42
    return-void
.end method


# virtual methods
.method public getView(I)Landroid/view/View;
    .locals 2
    .param p1, "viewId"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<V:",
            "Landroid/view/View;",
            ">(I)TV;"
        }
    .end annotation

    .line 64
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<TT;>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->views:Landroid/util/SparseArray;

    invoke-virtual {v0, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/View;

    .line 65
    .local v0, "view":Landroid/view/View;
    if-nez v0, :cond_0

    .line 66
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v1, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 67
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->views:Landroid/util/SparseArray;

    invoke-virtual {v1, p1, v0}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 69
    :cond_0
    return-object v0
.end method

.method protected abstract onBaseBindView(Ljava/lang/Object;I)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;I)V"
        }
    .end annotation
.end method

.method public setImageDrawable(ILandroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;
    .locals 1
    .param p1, "viewId"    # I
    .param p2, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 152
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<TT;>;"
    invoke-virtual {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getView(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 153
    .local v0, "view":Landroid/widget/ImageView;
    invoke-virtual {v0, p2}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 154
    return-object p0
.end method
