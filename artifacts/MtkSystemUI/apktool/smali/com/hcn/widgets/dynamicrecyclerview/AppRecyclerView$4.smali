.class Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;
.super Ljava/lang/Object;
.source "AppRecyclerView.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemLongClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    .line 226
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

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

    .line 230
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    const/4 v1, 0x1

    if-nez v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 231
    .local v0, "isAddButtonClicked":Z
    :goto_0
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    .line 232
    .local v2, "component":Landroid/content/ComponentName;
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$000(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    move-result-object v3

    invoke-virtual {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->showDialog()V

    .line 233
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;

    move-result-object v3

    if-eqz v3, :cond_1

    .line 234
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;

    move-result-object v3

    invoke-interface {v3, p3, v2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;->onItemLongClick(ILandroid/content/ComponentName;Z)Z

    .line 236
    :cond_1
    return v1
.end method
