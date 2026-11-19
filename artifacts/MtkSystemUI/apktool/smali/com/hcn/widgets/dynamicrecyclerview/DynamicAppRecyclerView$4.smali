.class Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;
.super Ljava/lang/Object;
.source "DynamicAppRecyclerView.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemLongClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 824
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemLongClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)Z
    .locals 4
    .param p2, "view"    # Landroid/view/View;
    .param p3, "position"    # I
    .param p4, "id"    # J
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView<",
            "*>;",
            "Landroid/view/View;",
            "IJ)Z"
        }
    .end annotation

    .line 828
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 829
    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    const/4 v1, 0x1

    if-nez v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 830
    .local v0, "isAddButtonClicked":Z
    :goto_0
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/util/List;

    move-result-object v2

    invoke-interface {v2, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    .line 831
    .local v2, "component":Landroid/content/ComponentName;
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;

    move-result-object v3

    if-eqz v3, :cond_1

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;

    move-result-object v3

    invoke-interface {v3, p3, v2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;->onItemLongClick(ILandroid/content/ComponentName;Z)Z

    move-result v3

    if-nez v3, :cond_2

    .line 833
    :cond_1
    if-nez v0, :cond_2

    .line 834
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v3, p3}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->removeDataWithNotify(I)V

    .line 837
    :cond_2
    return v1
.end method
