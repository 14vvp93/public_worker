.class Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;
.super Ljava/lang/Object;
.source "BaseRecyclerViewAdapter.java"

# interfaces
.implements Landroid/view/View$OnLongClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onBindViewHolder(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

.field final synthetic val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    .line 61
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;"
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onLongClick(Landroid/view/View;)Z
    .locals 7
    .param p1, "view"    # Landroid/view/View;

    .line 64
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

    if-eqz v0, :cond_0

    .line 65
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

    const/4 v2, 0x0

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    iget-object v3, v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->itemView:Landroid/view/View;

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    .line 66
    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getAdapterPosition()I

    move-result v4

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$2;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getItemId()J

    move-result-wide v5

    .line 65
    invoke-interface/range {v1 .. v6}, Landroid/widget/AdapterView$OnItemLongClickListener;->onItemLongClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)Z

    move-result v0

    return v0

    .line 68
    :cond_0
    const/4 v0, 0x0

    return v0
.end method
