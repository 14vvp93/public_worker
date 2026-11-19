.class Lcom/hcn/common/lang/HThreadUtils$Task$5;
.super Ljava/lang/Object;
.source "HThreadUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HThreadUtils$Task;->cancel(Z)V
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

    .line 1305
    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$Task$5;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .line 1308
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$5;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->onCancel()V

    .line 1309
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$Task$5;->this$0:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->onDone()V

    return-void
.end method
