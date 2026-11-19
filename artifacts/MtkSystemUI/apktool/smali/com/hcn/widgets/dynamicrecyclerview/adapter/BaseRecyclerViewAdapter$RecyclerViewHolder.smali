.class Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;
.super Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;
.source "BaseRecyclerViewAdapter.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "RecyclerViewHolder"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<",
        "TT;>;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;Landroid/view/ViewGroup;I)V
    .locals 0
    .param p2, "viewGroup"    # Landroid/view/ViewGroup;
    .param p3, "layoutId"    # I

    .line 88
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>.RecyclerViewHolder;"
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    .line 89
    invoke-direct {p0, p2, p3}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;-><init>(Landroid/view/ViewGroup;I)V

    .line 90
    return-void
.end method


# virtual methods
.method protected onBaseBindView(Ljava/lang/Object;I)V
    .locals 1
    .param p2, "position"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;I)V"
        }
    .end annotation

    .line 98
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<TT;>.RecyclerViewHolder;"
    .local p1, "bean":Ljava/lang/Object;, "TT;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$RecyclerViewHolder;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    invoke-virtual {v0, p0, p1, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Ljava/lang/Object;I)V

    .line 99
    return-void
.end method
