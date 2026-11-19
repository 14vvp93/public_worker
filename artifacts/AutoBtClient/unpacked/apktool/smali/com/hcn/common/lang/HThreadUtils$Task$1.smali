.class Lcom/hcn/common/lang/HThreadUtils$Task$1;
.super Ljava/util/TimerTask;
.source "HThreadUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HThreadUtils$Task;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/lang/HThreadUtils$Task;


# direct methods
.method constructor <init>(Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 0

    .line 1236
    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$Task$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-direct {p0}, Ljava/util/TimerTask;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .line 1239
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->isDone()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->-$$Nest$fgetmTimeoutListener(Lcom/hcn/common/lang/HThreadUtils$Task;)Lcom/hcn/common/lang/HThreadUtils$Task$OnTimeoutListener;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 1240
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->-$$Nest$mtimeout(Lcom/hcn/common/lang/HThreadUtils$Task;)V

    .line 1241
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->-$$Nest$fgetmTimeoutListener(Lcom/hcn/common/lang/HThreadUtils$Task;)Lcom/hcn/common/lang/HThreadUtils$Task$OnTimeoutListener;

    move-result-object v0

    invoke-interface {v0}, Lcom/hcn/common/lang/HThreadUtils$Task$OnTimeoutListener;->onTimeout()V

    .line 1242
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->onDone()V

    :cond_0
    return-void
.end method
