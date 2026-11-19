.class Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;
.super Ljava/lang/Object;
.source "DynamicAppRecyclerView.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


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

    .line 801
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 5
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

    .line 805
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    :try_start_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 806
    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 807
    .local v0, "isAddButtonClicked":Z
    :goto_0
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/util/List;

    move-result-object v1

    invoke-interface {v1, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v1

    .line 808
    .local v1, "component":Landroid/content/ComponentName;
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;

    move-result-object v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;

    move-result-object v2

    invoke-interface {v2, p3, v1, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;->onItemClick(ILandroid/content/ComponentName;Z)Z

    move-result v2

    if-nez v2, :cond_3

    .line 810
    :cond_1
    if-eqz v0, :cond_2

    .line 811
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$600(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    goto :goto_1

    .line 813
    :cond_2
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$700(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v1}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v3

    .line 814
    invoke-virtual {v1}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    .line 813
    invoke-static {v2, v3, v4}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->startApp(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 819
    .end local v0    # "isAddButtonClicked":Z
    .end local v1    # "component":Landroid/content/ComponentName;
    :cond_3
    :goto_1
    goto :goto_2

    .line 817
    :catch_0
    move-exception v0

    .line 818
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 820
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_2
    return-void
.end method
