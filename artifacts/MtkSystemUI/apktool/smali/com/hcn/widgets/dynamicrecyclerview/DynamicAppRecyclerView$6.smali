.class Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;
.super Ljava/lang/Object;
.source "DynamicAppRecyclerView.java"

# interfaces
.implements Landroid/content/DialogInterface$OnDismissListener;


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


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 901
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss(Landroid/content/DialogInterface;)V
    .locals 1
    .param p1, "dialog"    # Landroid/content/DialogInterface;

    .line 903
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$1100(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->isAttachedToWindow()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 904
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$1100(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    move-result-object v0

    invoke-interface {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;->onDialogHide()V

    .line 907
    :cond_0
    return-void
.end method
