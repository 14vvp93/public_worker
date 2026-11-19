.class Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;
.super Ljava/lang/Object;
.source "AppRecyclerView.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


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

    .line 202
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

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

    .line 206
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    :try_start_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    move v0, v1

    .line 207
    .local v0, "isAddButtonClicked":Z
    :goto_0
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    .line 209
    .local v2, "component":Landroid/content/ComponentName;
    if-eqz v0, :cond_1

    .line 210
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$000(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->showDialog()V

    goto :goto_1

    .line 212
    :cond_1
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;

    move-result-object v3

    if-eqz v3, :cond_2

    .line 213
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;

    move-result-object v3

    invoke-interface {v3, p3, v2, v1}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;->onItemClick(ILandroid/content/ComponentName;Z)Z

    goto :goto_1

    .line 215
    :cond_2
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v2}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v3

    .line 216
    invoke-virtual {v2}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    .line 215
    invoke-static {v1, v3, v4}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->startApp(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 221
    .end local v0    # "isAddButtonClicked":Z
    .end local v2    # "component":Landroid/content/ComponentName;
    :goto_1
    goto :goto_2

    .line 219
    :catch_0
    move-exception v0

    .line 220
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 222
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_2
    return-void
.end method
