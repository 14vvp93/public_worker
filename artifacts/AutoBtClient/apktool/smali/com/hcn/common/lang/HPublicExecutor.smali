.class public Lcom/hcn/common/lang/HPublicExecutor;
.super Ljava/lang/Object;
.source "HPublicExecutor.java"


# static fields
.field private static sInstance:Lcom/hcn/common/lang/HPublicExecutor;


# instance fields
.field private final mAsyncTaskExecutor:Ljava/util/concurrent/ExecutorService;

.field private final mMainHandler:Landroid/os/Handler;

.field private mThreadId:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>()V
    .locals 10

    .line 53
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 39
    iput v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mThreadId:I

    .line 55
    invoke-direct {p0}, Lcom/hcn/common/lang/HPublicExecutor;->callerValidityCheck()V

    .line 58
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mMainHandler:Landroid/os/Handler;

    .line 61
    new-instance v0, Ljava/util/concurrent/ThreadPoolExecutor;

    sget-object v7, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    new-instance v8, Ljava/util/concurrent/LinkedBlockingQueue;

    invoke-direct {v8}, Ljava/util/concurrent/LinkedBlockingQueue;-><init>()V

    new-instance v9, Lcom/hcn/common/lang/HPublicExecutor$1;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "public-task#"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget v2, p0, Lcom/hcn/common/lang/HPublicExecutor;->mThreadId:I

    add-int/lit8 v3, v2, 0x1

    iput v3, p0, Lcom/hcn/common/lang/HPublicExecutor;->mThreadId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v9, p0, v1}, Lcom/hcn/common/lang/HPublicExecutor$1;-><init>(Lcom/hcn/common/lang/HPublicExecutor;Ljava/lang/String;)V

    const/4 v3, 0x0

    const/4 v4, 0x5

    const-wide/16 v5, 0x1e

    move-object v2, v0

    invoke-direct/range {v2 .. v9}, Ljava/util/concurrent/ThreadPoolExecutor;-><init>(IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V

    iput-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mAsyncTaskExecutor:Ljava/util/concurrent/ExecutorService;

    return-void
.end method

.method private callerValidityCheck()V
    .locals 2

    .line 84
    invoke-static {}, Lcom/hcn/common/lang/HThreadUtils;->isMainThread()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 85
    :cond_0
    new-instance v0, Ljava/lang/RuntimeException;

    const-string v1, "HPublicExecutor#submitTask/Prohibit calls from non main threads!"

    invoke-direct {v0, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static instance()Lcom/hcn/common/lang/HPublicExecutor;
    .locals 1

    .line 46
    sget-object v0, Lcom/hcn/common/lang/HPublicExecutor;->sInstance:Lcom/hcn/common/lang/HPublicExecutor;

    if-nez v0, :cond_0

    .line 47
    new-instance v0, Lcom/hcn/common/lang/HPublicExecutor;

    invoke-direct {v0}, Lcom/hcn/common/lang/HPublicExecutor;-><init>()V

    sput-object v0, Lcom/hcn/common/lang/HPublicExecutor;->sInstance:Lcom/hcn/common/lang/HPublicExecutor;

    .line 50
    :cond_0
    sget-object v0, Lcom/hcn/common/lang/HPublicExecutor;->sInstance:Lcom/hcn/common/lang/HPublicExecutor;

    return-object v0
.end method

.method static synthetic lambda$submitTask$0(Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;J)V
    .locals 1

    if-eqz p0, :cond_0

    const-string v0, "ok"

    .line 173
    invoke-interface {p0, p1, p2, v0}, Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;->onCompletion(JLjava/lang/Object;)V

    :cond_0
    return-void
.end method


# virtual methods
.method public synthetic lambda$submitTask$1$com-hcn-common-lang-HPublicExecutor(Lcom/hcn/common/lang/RunnableEx;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;J)V
    .locals 1

    .line 168
    invoke-virtual {p1}, Lcom/hcn/common/lang/RunnableEx;->run()V

    .line 171
    iget-object p1, p0, Lcom/hcn/common/lang/HPublicExecutor;->mMainHandler:Landroid/os/Handler;

    new-instance v0, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;

    invoke-direct {v0, p2, p3, p4}, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;-><init>(Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;J)V

    invoke-virtual {p1, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public submitAsyncResultTask(JLjava/util/concurrent/Callable;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)Ljava/util/concurrent/Future;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(J",
            "Ljava/util/concurrent/Callable<",
            "Ljava/lang/Object;",
            ">;",
            "Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;",
            ")",
            "Ljava/util/concurrent/Future<",
            "*>;"
        }
    .end annotation

    .line 130
    invoke-direct {p0}, Lcom/hcn/common/lang/HPublicExecutor;->callerValidityCheck()V

    .line 131
    iget-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mAsyncTaskExecutor:Ljava/util/concurrent/ExecutorService;

    new-instance v1, Lcom/hcn/common/lang/HTaskRunnable;

    invoke-direct {v1, p1, p2, p3, p4}, Lcom/hcn/common/lang/HTaskRunnable;-><init>(JLjava/util/concurrent/Callable;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)V

    invoke-interface {v0, v1}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    move-result-object p1

    return-object p1
.end method

.method public submitTask(JLcom/hcn/common/lang/RunnableEx;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)Ljava/util/concurrent/Future;
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(J",
            "Lcom/hcn/common/lang/RunnableEx;",
            "Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;",
            ")",
            "Ljava/util/concurrent/Future<",
            "*>;"
        }
    .end annotation

    .line 166
    invoke-direct {p0}, Lcom/hcn/common/lang/HPublicExecutor;->callerValidityCheck()V

    .line 167
    iget-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mAsyncTaskExecutor:Ljava/util/concurrent/ExecutorService;

    new-instance v7, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda0;

    move-object v1, v7

    move-object v2, p0

    move-object v3, p3

    move-object v4, p4

    move-wide v5, p1

    invoke-direct/range {v1 .. v6}, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda0;-><init>(Lcom/hcn/common/lang/HPublicExecutor;Lcom/hcn/common/lang/RunnableEx;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;J)V

    invoke-interface {v0, v7}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    move-result-object p1

    return-object p1
.end method

.method public submitTask(JLjava/util/concurrent/Callable;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)Ljava/util/concurrent/Future;
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(J",
            "Ljava/util/concurrent/Callable<",
            "Ljava/lang/Object;",
            ">;",
            "Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;",
            ")",
            "Ljava/util/concurrent/Future<",
            "*>;"
        }
    .end annotation

    .line 108
    invoke-direct {p0}, Lcom/hcn/common/lang/HPublicExecutor;->callerValidityCheck()V

    .line 109
    iget-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mAsyncTaskExecutor:Ljava/util/concurrent/ExecutorService;

    new-instance v7, Lcom/hcn/common/lang/HTaskRunnable;

    iget-object v5, p0, Lcom/hcn/common/lang/HPublicExecutor;->mMainHandler:Landroid/os/Handler;

    move-object v1, v7

    move-wide v2, p1

    move-object v4, p3

    move-object v6, p4

    invoke-direct/range {v1 .. v6}, Lcom/hcn/common/lang/HTaskRunnable;-><init>(JLjava/util/concurrent/Callable;Landroid/os/Handler;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)V

    invoke-interface {v0, v7}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    move-result-object p1

    return-object p1
.end method

.method public submitTask(Lcom/hcn/common/lang/RunnableEx;)Ljava/util/concurrent/Future;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/lang/RunnableEx;",
            ")",
            "Ljava/util/concurrent/Future<",
            "*>;"
        }
    .end annotation

    .line 145
    invoke-direct {p0}, Lcom/hcn/common/lang/HPublicExecutor;->callerValidityCheck()V

    .line 146
    iget-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor;->mAsyncTaskExecutor:Ljava/util/concurrent/ExecutorService;

    invoke-interface {v0, p1}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    move-result-object p1

    return-object p1
.end method
