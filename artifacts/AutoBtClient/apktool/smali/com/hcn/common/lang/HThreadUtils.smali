.class public Lcom/hcn/common/lang/HThreadUtils;
.super Ljava/lang/Object;
.source "HThreadUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/lang/HThreadUtils$Task;,
        Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;,
        Lcom/hcn/common/lang/HThreadUtils$SyncValue;,
        Lcom/hcn/common/lang/HThreadUtils$SimpleTask;,
        Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;,
        Lcom/hcn/common/lang/HThreadUtils$LinkedBlockingQueueEx;
    }
.end annotation


# static fields
.field private static final CPU_COUNT:I

.field private static final HANDLER:Landroid/os/Handler;

.field private static final TASK_POOL_MAP:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Lcom/hcn/common/lang/HThreadUtils$Task;",
            "Ljava/util/concurrent/ExecutorService;",
            ">;"
        }
    .end annotation
.end field

.field private static final TIMER:Ljava/util/Timer;

.field private static final TYPE_CACHED:B = -0x2t

.field private static final TYPE_CPU:B = -0x8t

.field private static final TYPE_IO:B = -0x4t

.field private static final TYPE_PRIORITY_POOLS:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/util/concurrent/ExecutorService;",
            ">;>;"
        }
    .end annotation
.end field

.field private static final TYPE_SINGLE:B = -0x1t

.field private static sDeliver:Ljava/util/concurrent/Executor;


# direct methods
.method static bridge synthetic -$$Nest$sfgetCPU_COUNT()I
    .locals 1

    sget v0, Lcom/hcn/common/lang/HThreadUtils;->CPU_COUNT:I

    return v0
.end method

.method static bridge synthetic -$$Nest$sfgetTASK_POOL_MAP()Ljava/util/Map;
    .locals 1

    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->TASK_POOL_MAP:Ljava/util/Map;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$smgetGlobalDeliver()Ljava/util/concurrent/Executor;
    .locals 1

    invoke-static {}, Lcom/hcn/common/lang/HThreadUtils;->getGlobalDeliver()Ljava/util/concurrent/Executor;

    move-result-object v0

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 2

    .line 36
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    sput-object v0, Lcom/hcn/common/lang/HThreadUtils;->HANDLER:Landroid/os/Handler;

    .line 37
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lcom/hcn/common/lang/HThreadUtils;->TYPE_PRIORITY_POOLS:Ljava/util/Map;

    .line 38
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lcom/hcn/common/lang/HThreadUtils;->TASK_POOL_MAP:Ljava/util/Map;

    .line 40
    invoke-static {}, Ljava/lang/Runtime;->getRuntime()Ljava/lang/Runtime;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Runtime;->availableProcessors()I

    move-result v0

    sput v0, Lcom/hcn/common/lang/HThreadUtils;->CPU_COUNT:I

    .line 41
    new-instance v0, Ljava/util/Timer;

    invoke-direct {v0}, Ljava/util/Timer;-><init>()V

    sput-object v0, Lcom/hcn/common/lang/HThreadUtils;->TIMER:Ljava/util/Timer;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 35
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static cancel(Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 0

    if-nez p0, :cond_0

    return-void

    .line 865
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/common/lang/HThreadUtils$Task;->cancel()V

    return-void
.end method

.method public static cancel(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/hcn/common/lang/HThreadUtils$Task;",
            ">;)V"
        }
    .end annotation

    if-eqz p0, :cond_2

    .line 891
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_1

    .line 894
    :cond_0
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/lang/HThreadUtils$Task;

    if-nez v0, :cond_1

    goto :goto_0

    .line 898
    :cond_1
    invoke-virtual {v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->cancel()V

    goto :goto_0

    :cond_2
    :goto_1
    return-void
.end method

.method public static cancel(Ljava/util/concurrent/ExecutorService;)V
    .locals 3

    .line 908
    instance-of v0, p0, Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;

    if-eqz v0, :cond_1

    .line 909
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->TASK_POOL_MAP:Ljava/util/Map;

    invoke-interface {v0}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map$Entry;

    .line 910
    invoke-interface {v1}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v2

    if-ne v2, p0, :cond_0

    .line 911
    invoke-interface {v1}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/lang/HThreadUtils$Task;

    invoke-static {v1}, Lcom/hcn/common/lang/HThreadUtils;->cancel(Lcom/hcn/common/lang/HThreadUtils$Task;)V

    goto :goto_0

    :cond_1
    const-string p0, "ThreadUtils"

    const-string v0, "The executorService is not ThreadUtils\'s pool."

    .line 915
    invoke-static {p0, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_2
    return-void
.end method

.method public static varargs cancel([Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 3

    if-nez p0, :cond_0

    return-void

    .line 877
    :cond_0
    array-length v0, p0

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_2

    aget-object v2, p0, v1

    if-nez v2, :cond_1

    goto :goto_1

    .line 881
    :cond_1
    invoke-virtual {v2}, Lcom/hcn/common/lang/HThreadUtils$Task;->cancel()V

    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_2
    return-void
.end method

.method private static execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    const-wide/16 v2, 0x0

    const-wide/16 v4, 0x0

    const/4 v6, 0x0

    move-object v0, p0

    move-object v1, p1

    .line 929
    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method private static execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 949
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->TASK_POOL_MAP:Ljava/util/Map;

    monitor-enter v0

    .line 950
    :try_start_0
    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_0

    const-string p0, "ThreadUtils"

    const-string p1, "Task can only be executed once."

    .line 951
    invoke-static {p0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 952
    monitor-exit v0

    return-void

    .line 954
    :cond_0
    invoke-interface {v0, p1, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 955
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    const-wide/16 v0, 0x0

    cmp-long v2, p4, v0

    if-nez v2, :cond_2

    cmp-long p4, p2, v0

    if-nez p4, :cond_1

    .line 958
    invoke-interface {p0, p1}, Ljava/util/concurrent/ExecutorService;->execute(Ljava/lang/Runnable;)V

    goto :goto_0

    .line 960
    :cond_1
    new-instance p4, Lcom/hcn/common/lang/HThreadUtils$1;

    invoke-direct {p4, p0, p1}, Lcom/hcn/common/lang/HThreadUtils$1;-><init>(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    .line 966
    sget-object p0, Lcom/hcn/common/lang/HThreadUtils;->TIMER:Ljava/util/Timer;

    invoke-virtual {p6, p2, p3}, Ljava/util/concurrent/TimeUnit;->toMillis(J)J

    move-result-wide p1

    invoke-virtual {p0, p4, p1, p2}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;J)V

    goto :goto_0

    :cond_2
    const/4 v0, 0x1

    .line 969
    invoke-static {p1, v0}, Lcom/hcn/common/lang/HThreadUtils$Task;->-$$Nest$msetSchedule(Lcom/hcn/common/lang/HThreadUtils$Task;Z)V

    .line 970
    new-instance v0, Lcom/hcn/common/lang/HThreadUtils$2;

    invoke-direct {v0, p0, p1}, Lcom/hcn/common/lang/HThreadUtils$2;-><init>(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    .line 976
    sget-object p0, Lcom/hcn/common/lang/HThreadUtils;->TIMER:Ljava/util/Timer;

    invoke-virtual {p6, p2, p3}, Ljava/util/concurrent/TimeUnit;->toMillis(J)J

    move-result-wide p2

    invoke-virtual {p6, p4, p5}, Ljava/util/concurrent/TimeUnit;->toMillis(J)J

    move-result-wide p4

    move-object p1, v0

    invoke-virtual/range {p0 .. p5}, Ljava/util/Timer;->scheduleAtFixedRate(Ljava/util/TimerTask;JJ)V

    :goto_0
    return-void

    :catchall_0
    move-exception p0

    .line 955
    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0
.end method

.method private static executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 944
    invoke-static/range {p0 .. p6}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCached(Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 447
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByCached(Lcom/hcn/common/lang/HThreadUtils$Task;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;I)V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 459
    invoke-static {v0, p1}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p1

    invoke-static {p1, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByCachedAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 535
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCachedAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 554
    invoke-static {v0, p6}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    .line 553
    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCachedAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 503
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCachedAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 519
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCachedWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 473
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCachedWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x2

    .line 489
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p4

    invoke-static {p4, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCpu(Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 683
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByCpu(Lcom/hcn/common/lang/HThreadUtils$Task;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;I)V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 695
    invoke-static {v0, p1}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p1

    invoke-static {p1, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByCpuAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 771
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCpuAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 790
    invoke-static {v0, p6}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    .line 789
    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCpuAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 739
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCpuAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 755
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCpuWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 709
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCpuWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x8

    .line 725
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p4

    invoke-static {p4, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCustom(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    .line 802
    invoke-static {p0, p1}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByCustomAtFixRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 852
    invoke-static/range {p0 .. p6}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCustomAtFixRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const-wide/16 v2, 0x0

    move-object v0, p0

    move-object v1, p1

    move-wide v4, p2

    move-object v6, p4

    .line 834
    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByCustomWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 818
    invoke-static {p0, p1, p2, p3, p4}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByFixed(ILcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    .line 199
    invoke-static {p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    invoke-static {p0, p1}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByFixed(ILcom/hcn/common/lang/HThreadUtils$Task;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;I)V"
        }
    .end annotation

    .line 213
    invoke-static {p0, p2}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    invoke-static {p0, p1}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByFixedAtFixRate(ILcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 299
    invoke-static {p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    move-object v1, p1

    move-wide v2, p2

    move-wide v4, p4

    move-object v6, p6

    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByFixedAtFixRate(ILcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;I)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    .line 319
    invoke-static {p0, p7}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    move-object v1, p1

    move-wide v2, p2

    move-wide v4, p4

    move-object v6, p6

    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByFixedAtFixRate(ILcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 263
    invoke-static {p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    const-wide/16 v2, 0x0

    move-object v1, p1

    move-wide v4, p2

    move-object v6, p4

    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByFixedAtFixRate(ILcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    .line 281
    invoke-static {p0, p5}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    const-wide/16 v2, 0x0

    move-object v1, p1

    move-wide v4, p2

    move-object v6, p4

    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByFixedWithDelay(ILcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    .line 229
    invoke-static {p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    invoke-static {p0, p1, p2, p3, p4}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByFixedWithDelay(ILcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(I",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    .line 247
    invoke-static {p0, p5}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    invoke-static {p0, p1, p2, p3, p4}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByIo(Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 565
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByIo(Lcom/hcn/common/lang/HThreadUtils$Task;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;I)V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 577
    invoke-static {v0, p1}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p1

    invoke-static {p1, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeByIoAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 653
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByIoAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 672
    invoke-static {v0, p6}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    .line 671
    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByIoAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 621
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByIoAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 637
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByIoWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 591
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeByIoWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x4

    .line 607
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p4

    invoke-static {p4, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeBySingle(Lcom/hcn/common/lang/HThreadUtils$Task;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;)V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 329
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeBySingle(Lcom/hcn/common/lang/HThreadUtils$Task;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;I)V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 341
    invoke-static {v0, p1}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p1

    invoke-static {p1, p0}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;)V

    return-void
.end method

.method public static executeBySingleAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 417
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeBySingleAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;JJ",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 436
    invoke-static {v0, p6}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    move-object v2, p0

    move-wide v3, p1

    move-wide v5, p3

    move-object v7, p5

    .line 435
    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeBySingleAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 385
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeBySingleAtFixRate(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 401
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    const-wide/16 v3, 0x0

    move-object v2, p0

    move-wide v5, p1

    move-object v7, p3

    invoke-static/range {v1 .. v7}, Lcom/hcn/common/lang/HThreadUtils;->executeAtFixedRate(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeBySingleWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 355
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    invoke-static {v0, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static executeBySingleWithDelay(Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            "I)V"
        }
    .end annotation

    const/4 v0, -0x1

    .line 371
    invoke-static {v0, p4}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p4

    invoke-static {p4, p0, p1, p2, p3}, Lcom/hcn/common/lang/HThreadUtils;->executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method private static executeWithDelay(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JLjava/util/concurrent/TimeUnit;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/util/concurrent/ExecutorService;",
            "Lcom/hcn/common/lang/HThreadUtils$Task<",
            "TT;>;J",
            "Ljava/util/concurrent/TimeUnit;",
            ")V"
        }
    .end annotation

    const-wide/16 v4, 0x0

    move-object v0, p0

    move-object v1, p1

    move-wide v2, p2

    move-object v6, p4

    .line 936
    invoke-static/range {v0 .. v6}, Lcom/hcn/common/lang/HThreadUtils;->execute(Ljava/util/concurrent/ExecutorService;Lcom/hcn/common/lang/HThreadUtils$Task;JJLjava/util/concurrent/TimeUnit;)V

    return-void
.end method

.method public static getCachedPool()Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x2

    .line 132
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    return-object v0
.end method

.method public static getCachedPool(I)Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x2

    .line 144
    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method public static getCpuPool()Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x8

    .line 176
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    return-object v0
.end method

.method public static getCpuPool(I)Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x8

    .line 188
    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method public static getFixedPool(I)Ljava/util/concurrent/ExecutorService;
    .locals 0

    .line 84
    invoke-static {p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method public static getFixedPool(II)Ljava/util/concurrent/ExecutorService;
    .locals 0

    .line 98
    invoke-static {p0, p1}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method private static getGlobalDeliver()Ljava/util/concurrent/Executor;
    .locals 1

    .line 1412
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->sDeliver:Ljava/util/concurrent/Executor;

    if-nez v0, :cond_0

    .line 1413
    new-instance v0, Lcom/hcn/common/lang/HThreadUtils$3;

    invoke-direct {v0}, Lcom/hcn/common/lang/HThreadUtils$3;-><init>()V

    sput-object v0, Lcom/hcn/common/lang/HThreadUtils;->sDeliver:Ljava/util/concurrent/Executor;

    .line 1420
    :cond_0
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->sDeliver:Ljava/util/concurrent/Executor;

    return-object v0
.end method

.method public static getIoPool()Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x4

    .line 154
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    return-object v0
.end method

.method public static getIoPool(I)Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x4

    .line 165
    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method public static getMainHandler()Landroid/os/Handler;
    .locals 1

    .line 60
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->HANDLER:Landroid/os/Handler;

    return-object v0
.end method

.method private static getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, 0x5

    .line 981
    invoke-static {p0, v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method private static getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;
    .locals 3

    .line 985
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->TYPE_PRIORITY_POOLS:Ljava/util/Map;

    monitor-enter v0

    .line 987
    :try_start_0
    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    if-nez v1, :cond_0

    .line 989
    new-instance v1, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v1}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    .line 990
    invoke-static {p0, p1}, Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;->-$$Nest$smcreatePool(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v2

    .line 991
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-interface {v1, p1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 992
    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    invoke-interface {v0, p0, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 994
    :cond_0
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-interface {v1, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/concurrent/ExecutorService;

    if-nez v2, :cond_1

    .line 996
    invoke-static {p0, p1}, Lcom/hcn/common/lang/HThreadUtils$ThreadPoolExecutorEx;->-$$Nest$smcreatePool(II)Ljava/util/concurrent/ExecutorService;

    move-result-object v2

    .line 997
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    invoke-interface {v1, p0, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1000
    :cond_1
    :goto_0
    monitor-exit v0

    return-object v2

    :catchall_0
    move-exception p0

    .line 1001
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public static getSinglePool()Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x1

    .line 109
    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    return-object v0
.end method

.method public static getSinglePool(I)Ljava/util/concurrent/ExecutorService;
    .locals 1

    const/4 v0, -0x1

    .line 121
    invoke-static {v0, p0}, Lcom/hcn/common/lang/HThreadUtils;->getPoolByTypeAndPriority(II)Ljava/util/concurrent/ExecutorService;

    move-result-object p0

    return-object p0
.end method

.method public static isMainThread()Z
    .locals 2

    .line 56
    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static runOnUiThread(Ljava/lang/Runnable;)V
    .locals 2

    .line 64
    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    if-ne v0, v1, :cond_0

    .line 65
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V

    goto :goto_0

    .line 67
    :cond_0
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->HANDLER:Landroid/os/Handler;

    invoke-virtual {v0, p0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :goto_0
    return-void
.end method

.method public static runOnUiThreadDelayed(Ljava/lang/Runnable;J)V
    .locals 1

    .line 72
    sget-object v0, Lcom/hcn/common/lang/HThreadUtils;->HANDLER:Landroid/os/Handler;

    invoke-virtual {v0, p0, p1, p2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method

.method public static setDeliver(Ljava/util/concurrent/Executor;)V
    .locals 0

    .line 925
    sput-object p0, Lcom/hcn/common/lang/HThreadUtils;->sDeliver:Ljava/util/concurrent/Executor;

    return-void
.end method
