.class Lcom/hcn/common/lang/HThreadUtils$Task$4;
.super Ljava/lang/Object;
.source "HThreadUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


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

.field final synthetic val$throwable:Ljava/lang/Throwable;


# direct methods
.method constructor <init>(Lcom/hcn/common/lang/HThreadUtils$Task;Ljava/lang/Throwable;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1278
    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$Task$4;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    iput-object p2, p0, Lcom/hcn/common/lang/HThreadUtils$Task$4;->val$throwable:Ljava/lang/Throwable;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 1281
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$4;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    iget-object v1, p0, Lcom/hcn/common/lang/HThreadUtils$Task$4;->val$throwable:Ljava/lang/Throwable;

    invoke-virtual {v0, v1}, Lcom/hcn/common/lang/HThreadUtils$Task;->onFail(Ljava/lang/Throwable;)V

    .line 1282
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$4;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->onDone()V

    return-void
.end method
