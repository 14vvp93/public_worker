.class Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;
.super Ljava/lang/Object;
.source "DynamicAppRecyclerView.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->showDialogWithIcon(Ljava/util/List;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

.field final synthetic val$appIconDataList:Ljava/util/List;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Ljava/util/List;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 921
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->val$appIconDataList:Ljava/util/List;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 2
    .param p2, "view"    # Landroid/view/View;
    .param p3, "position"    # I
    .param p4, "id"    # J
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView<",
            "*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .line 924
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->val$appIconDataList:Ljava/util/List;

    invoke-interface {v1, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->insertDataWithNotify(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 925
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$1000(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Landroidx/recyclerview/widget/RecyclerView;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$900(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->getItemCount()I

    move-result v1

    add-int/lit8 v1, v1, -0x1

    invoke-virtual {v0, v1}, Landroidx/recyclerview/widget/RecyclerView;->scrollToPosition(I)V

    .line 926
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$1200(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->hide()V

    .line 927
    return-void
.end method
