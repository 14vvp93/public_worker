.class public Lcom/hcn/common/lang/HTaskRunnable;
.super Ljava/lang/Object;
.source "HTaskRunnable.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;",
        "Ljava/lang/Runnable;"
    }
.end annotation


# instance fields
.field private final mHandlerRef:Ljava/lang/ref/Reference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/Reference<",
            "Landroid/os/Handler;",
            ">;"
        }
    .end annotation
.end field

.field private final mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

.field private final mTask:Ljava/util/concurrent/Callable;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/Callable<",
            "TT;>;"
        }
    .end annotation
.end field

.field private final mTaskUniqueId:J


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 43
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 44
    new-instance v0, Ljava/lang/UnsupportedOperationException;

    const-string v1, "u can\'t instantiate me..."

    invoke-direct {v0, v1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public constructor <init>(JLjava/util/concurrent/Callable;Landroid/os/Handler;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(J",
            "Ljava/util/concurrent/Callable<",
            "TT;>;",
            "Landroid/os/Handler;",
            "Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;",
            ")V"
        }
    .end annotation

    .line 84
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 85
    iput-wide p1, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTaskUniqueId:J

    .line 86
    iput-object p3, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTask:Ljava/util/concurrent/Callable;

    .line 88
    new-instance p1, Ljava/lang/ref/WeakReference;

    invoke-direct {p1, p4}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object p1, p0, Lcom/hcn/common/lang/HTaskRunnable;->mHandlerRef:Ljava/lang/ref/Reference;

    .line 89
    iput-object p5, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    return-void
.end method

.method public constructor <init>(JLjava/util/concurrent/Callable;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(J",
            "Ljava/util/concurrent/Callable<",
            "TT;>;",
            "Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;",
            ")V"
        }
    .end annotation

    .line 65
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 66
    iput-wide p1, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTaskUniqueId:J

    .line 67
    iput-object p3, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTask:Ljava/util/concurrent/Callable;

    const/4 p1, 0x0

    .line 69
    iput-object p1, p0, Lcom/hcn/common/lang/HTaskRunnable;->mHandlerRef:Ljava/lang/ref/Reference;

    .line 70
    iput-object p4, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    return-void
.end method

.method public constructor <init>(Ljava/util/concurrent/Callable;)V
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/concurrent/Callable<",
            "TT;>;)V"
        }
    .end annotation

    const-wide/16 v0, -0x1

    const/4 v2, 0x0

    .line 53
    invoke-direct {p0, v0, v1, p1, v2}, Lcom/hcn/common/lang/HTaskRunnable;-><init>(JLjava/util/concurrent/Callable;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)V

    return-void
.end method


# virtual methods
.method public synthetic lambda$run$0$com-hcn-common-lang-HTaskRunnable(Ljava/lang/Object;)V
    .locals 3

    .line 120
    iget-object v0, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    iget-wide v1, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTaskUniqueId:J

    invoke-interface {v0, v1, v2, p1}, Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;->onCompletion(JLjava/lang/Object;)V

    return-void
.end method

.method public synthetic lambda$run$1$com-hcn-common-lang-HTaskRunnable(Ljava/lang/String;)V
    .locals 3

    .line 142
    iget-object v0, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    iget-wide v1, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTaskUniqueId:J

    invoke-interface {v0, v1, v2, p1}, Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;->onCompletion(JLjava/lang/Object;)V

    return-void
.end method

.method public run()V
    .locals 7

    const/4 v0, 0x0

    const/4 v1, 0x0

    .line 112
    :try_start_0
    iget-object v2, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTask:Ljava/util/concurrent/Callable;

    invoke-interface {v2}, Ljava/util/concurrent/Callable;->call()Ljava/lang/Object;

    move-result-object v1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    const/4 v2, 0x1

    .line 116
    :try_start_1
    iget-object v3, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    if-eqz v3, :cond_1

    .line 117
    iget-object v4, p0, Lcom/hcn/common/lang/HTaskRunnable;->mHandlerRef:Ljava/lang/ref/Reference;

    if-eqz v4, :cond_0

    .line 118
    invoke-virtual {v4}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/os/Handler;

    if-eqz v3, :cond_1

    .line 120
    new-instance v4, Lcom/hcn/common/lang/HTaskRunnable$$ExternalSyntheticLambda0;

    invoke-direct {v4, p0, v1}, Lcom/hcn/common/lang/HTaskRunnable$$ExternalSyntheticLambda0;-><init>(Lcom/hcn/common/lang/HTaskRunnable;Ljava/lang/Object;)V

    invoke-virtual {v3, v4}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_0

    .line 123
    :cond_0
    iget-wide v4, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTaskUniqueId:J

    invoke-interface {v3, v4, v5, v1}, Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;->onCompletion(JLjava/lang/Object;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    :cond_1
    :goto_0
    return-void

    :catchall_0
    move-exception v1

    goto :goto_2

    :catch_0
    move-exception v1

    goto :goto_1

    :catchall_1
    move-exception v2

    move-object v6, v2

    move v2, v1

    move-object v1, v6

    goto :goto_2

    :catch_1
    move-exception v2

    move-object v6, v2

    move v2, v1

    move-object v1, v6

    :goto_1
    if-nez v2, :cond_2

    .line 129
    :try_start_2
    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    .line 132
    :cond_2
    new-instance v3, Ljava/lang/RuntimeException;

    invoke-direct {v3, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw v3
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 135
    :goto_2
    iget-object v3, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    if-eqz v3, :cond_4

    if-nez v2, :cond_4

    .line 137
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_4

    .line 138
    iget-object v2, p0, Lcom/hcn/common/lang/HTaskRunnable;->mHandlerRef:Ljava/lang/ref/Reference;

    if-eqz v2, :cond_3

    .line 139
    invoke-virtual {v2}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/os/Handler;

    if-eqz v2, :cond_4

    .line 142
    new-instance v3, Lcom/hcn/common/lang/HTaskRunnable$$ExternalSyntheticLambda1;

    invoke-direct {v3, p0, v0}, Lcom/hcn/common/lang/HTaskRunnable$$ExternalSyntheticLambda1;-><init>(Lcom/hcn/common/lang/HTaskRunnable;Ljava/lang/String;)V

    invoke-virtual {v2, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_3

    .line 145
    :cond_3
    iget-object v2, p0, Lcom/hcn/common/lang/HTaskRunnable;->mListener:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    iget-wide v3, p0, Lcom/hcn/common/lang/HTaskRunnable;->mTaskUniqueId:J

    invoke-interface {v2, v3, v4, v0}, Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;->onCompletion(JLjava/lang/Object;)V

    .line 148
    :cond_4
    :goto_3
    throw v1
.end method
