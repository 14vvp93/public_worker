.class final Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;
.super Ljava/util/concurrent/LinkedBlockingQueue;
.source "HThreadUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/lang/HThreadUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "LinkedBlockingQueueEx"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/util/concurrent/LinkedBlockingQueue<",
        "Ljava/lang/Runnable;",
        ">;"
    }
.end annotation


# instance fields
.field private mCapacity:I

.field private volatile mPool:Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;


# direct methods
.method static bridge synthetic -$$Nest$fputmPool(Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;)V
    .locals 0

    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mPool:Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;

    return-void
.end method

.method constructor <init>()V
    .locals 1

    .line 1092
    invoke-direct {p0}, Ljava/util/concurrent/LinkedBlockingQueue;-><init>()V

    const v0, 0x7fffffff

    .line 1089
    iput v0, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mCapacity:I

    return-void
.end method

.method constructor <init>(I)V
    .locals 1

    .line 1103
    invoke-direct {p0}, Ljava/util/concurrent/LinkedBlockingQueue;-><init>()V

    const v0, 0x7fffffff

    .line 1089
    iput v0, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mCapacity:I

    .line 1104
    iput p1, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mCapacity:I

    return-void
.end method

.method constructor <init>(Z)V
    .locals 1

    .line 1096
    invoke-direct {p0}, Ljava/util/concurrent/LinkedBlockingQueue;-><init>()V

    const v0, 0x7fffffff

    .line 1089
    iput v0, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mCapacity:I

    if-eqz p1, :cond_0

    const/4 p1, 0x0

    .line 1098
    iput p1, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mCapacity:I

    :cond_0
    return-void
.end method


# virtual methods
.method public bridge synthetic offer(Ljava/lang/Object;)Z
    .locals 0

    .line 1085
    check-cast p1, Ljava/lang/Runnable;

    invoke-virtual {p0, p1}, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->offer(Ljava/lang/Runnable;)Z

    move-result p1

    return p1
.end method

.method public offer(Ljava/lang/Runnable;)Z
    .locals 2

    .line 1109
    iget v0, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mCapacity:I

    invoke-virtual {p0}, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->size()I

    move-result v1

    if-gt v0, v1, :cond_0

    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mPool:Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mPool:Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;

    .line 1110
    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;->getPoolSize()I

    move-result v0

    iget-object v1, p0, Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;->mPool:Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;

    invoke-virtual {v1}, Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;->getMaximumPoolSize()I

    move-result v1

    if-ge v0, v1, :cond_0

    const/4 p1, 0x0

    return p1

    .line 1114
    :cond_0
    invoke-super {p0, p1}, Ljava/util/concurrent/LinkedBlockingQueue;->offer(Ljava/lang/Object;)Z

    move-result p1

    return p1
.end method
