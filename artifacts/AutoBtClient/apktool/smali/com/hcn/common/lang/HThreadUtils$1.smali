.class Lcom/hcn/common/lang/HThreadUtils$1;
.super Ljava/util/TimerTask;
.source "HThreadUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$pool:Ljava/util/concurrent/ExecutorService;

.field final synthetic val$task:Lcom/hcn/common/lang/HThreadUtils$Task;


# direct methods
.method constructor <init>(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 0

    .line 960
    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$1;->val$pool:Ljava/util/concurrent/ExecutorService;

    iput-object p2, p0, Lcom/hcn/common/lang/HThreadUtils$1;->val$task:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-direct {p0}, Ljava/util/TimerTask;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 963
    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$1;->val$pool:Ljava/util/concurrent/ExecutorService;

    iget-object v1, p0, Lcom/hcn/common/lang/HThreadUtils$1;->val$task:Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-interface {v0, v1}, Ljava/util/concurrent/ExecutorService;->execute(Ljava/lang/Runnable;)V

    return-void
.end method
