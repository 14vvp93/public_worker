.class Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;
.super Ljava/lang/Object;
.source "CheckBoxRvDialog.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

.field final synthetic val$appInfo:Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    .locals 0
    .param p1, "this$1"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    .line 671
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->val$appInfo:Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2
    .param p1, "v"    # Landroid/view/View;

    .line 674
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->access$2200(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 675
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->val$appInfo:Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->removeData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 676
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1200(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->val$appInfo:Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->addData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    goto :goto_0

    .line 678
    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->val$appInfo:Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->addData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 679
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$2300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroidx/recyclerview/widget/RecyclerView;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v1, v1, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->getItemCount()I

    move-result v1

    invoke-virtual {v0, v1}, Landroidx/recyclerview/widget/RecyclerView;->smoothScrollToPosition(I)V

    .line 681
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1200(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->val$appInfo:Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->removeData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 683
    :goto_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->updateSelectRVLayoutParams()V

    .line 685
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$2400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/view/View$OnClickListener;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 686
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;->this$1:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    iget-object v0, v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$2400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/view/View$OnClickListener;

    move-result-object v0

    invoke-interface {v0, p1}, Landroid/view/View$OnClickListener;->onClick(Landroid/view/View;)V

    .line 688
    :cond_1
    return-void
.end method
