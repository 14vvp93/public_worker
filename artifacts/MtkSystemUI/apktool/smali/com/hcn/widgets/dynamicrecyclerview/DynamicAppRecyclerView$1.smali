.class Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;
.super Landroid/os/Handler;
.source "DynamicAppRecyclerView.java"


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

    .line 190
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .param p1, "msg"    # Landroid/os/Message;

    .line 193
    iget v0, p1, Landroid/os/Message;->what:I

    const/16 v1, 0x3e8

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 195
    :cond_0
    const-string v0, "DynamicAppRecyclerView"

    const-string v1, "initHandler1111:"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 196
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$000(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Z

    .line 197
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$100(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Ljava/lang/String;)V

    .line 199
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->RestoreData()V

    .line 200
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/16 v1, 0x14

    if-ge v0, v1, :cond_1

    .line 201
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    .line 203
    :cond_1
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->notifyDataSetChanged()V

    .line 204
    const-string v0, "DynamicAppRecyclerView"

    const-string v1, "initHandler2222:"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 207
    :goto_0
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 208
    return-void
.end method
