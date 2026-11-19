.class Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;
.super Ljava/lang/Object;
.source "BaseRecyclerViewAdapter.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


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

    .line 52
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;"
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 7
    .param p1, "view"    # Landroid/view/View;

    .line 55
    .local p0, "this":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    if-eqz v0, :cond_0

    .line 56
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;

    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;->onItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    const/4 v2, 0x0

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    iget-object v3, v0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->itemView:Landroid/view/View;

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    .line 57
    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getAdapterPosition()I

    move-result v4

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter$1;->val$holder:Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getItemId()J

    move-result-wide v5

    .line 56
    invoke-interface/range {v1 .. v6}, Landroid/widget/AdapterView$OnItemClickListener;->onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V

    .line 59
    :cond_0
    return-void
.end method
