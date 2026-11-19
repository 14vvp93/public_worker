.class Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;
.super Ljava/lang/Object;
.source "DynamicAppRecyclerView.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->showDialogOnlyText(Ljava/util/List;[Ljava/lang/CharSequence;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

.field final synthetic val$activityInfoList:Ljava/util/List;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Ljava/util/List;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 892
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->val$activityInfoList:Ljava/util/List;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 5
    .param p1, "dialog"    # Landroid/content/DialogInterface;
    .param p2, "which"    # I

    .line 894
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->val$activityInfoList:Ljava/util/List;

    invoke-interface {v0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/pm/LauncherActivityInfo;

    .line 895
    .local v0, "info":Landroid/content/pm/LauncherActivityInfo;
    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0}, Landroid/content/pm/LauncherActivityInfo;->getLabel()Ljava/lang/CharSequence;

    move-result-object v2

    invoke-interface {v2}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v3

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v4, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    invoke-direct {v1, v2, v3, v4}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;-><init>(Ljava/lang/String;Landroid/content/ComponentName;Landroid/graphics/drawable/Drawable;)V

    .line 896
    .local v1, "tmpInfo":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v2, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->insertDataWithNotify(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 897
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$1000(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Landroidx/recyclerview/widget/RecyclerView;

    move-result-object v2

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$900(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    move-result-object v3

    invoke-virtual {v3}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->getItemCount()I

    move-result v3

    add-int/lit8 v3, v3, -0x1

    invoke-virtual {v2, v3}, Landroidx/recyclerview/widget/RecyclerView;->scrollToPosition(I)V

    .line 898
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 899
    return-void
.end method
