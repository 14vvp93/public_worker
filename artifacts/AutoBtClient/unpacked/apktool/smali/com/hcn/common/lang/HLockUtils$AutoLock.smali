.class public Lcom/hcn/common/lang/HLockUtils$AutoLock;
.super Ljava/lang/Object;
.source "HLockUtils.java"

# interfaces
.implements Ljava/lang/AutoCloseable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/lang/HLockUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "AutoLock"
.end annotation


# instance fields
.field private mLockKey:Ljava/lang/String;

.field private final mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

.field private mRefNumber:I


# direct methods
.method static bridge synthetic -$$Nest$mlock(Lcom/hcn/common/lang/HLockUtils$AutoLock;)Lcom/hcn/common/lang/HLockUtils$AutoLock;
    .locals 0

    invoke-direct {p0}, Lcom/hcn/common/lang/HLockUtils$AutoLock;->lock()Lcom/hcn/common/lang/HLockUtils$AutoLock;

    move-result-object p0

    return-object p0
.end method

.method public constructor <init>(Ljava/lang/String;)V
    .locals 1

    .line 61
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, ""

    .line 52
    iput-object v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mLockKey:Ljava/lang/String;

    .line 55
    new-instance v0, Ljava/util/concurrent/locks/ReentrantLock;

    invoke-direct {v0}, Ljava/util/concurrent/locks/ReentrantLock;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    const/4 v0, 0x0

    .line 58
    iput v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mRefNumber:I

    .line 62
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 63
    iput-object p1, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mLockKey:Ljava/lang/String;

    :cond_0
    return-void
.end method

.method private lock()Lcom/hcn/common/lang/HLockUtils$AutoLock;
    .locals 1

    .line 73
    iget v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mRefNumber:I

    add-int/lit8 v0, v0, 0x1

    iput v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mRefNumber:I

    .line 76
    iget-object v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->lock()V

    return-object p0
.end method

.method private unLock()Lcom/hcn/common/lang/HLockUtils$AutoLock;
    .locals 2

    .line 86
    iget-object v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    if-eqz v0, :cond_1

    .line 87
    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->isLocked()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 88
    iget-object v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    .line 91
    iget-object v0, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mLockKey:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->intern()Ljava/lang/String;

    move-result-object v0

    monitor-enter v0

    .line 93
    :try_start_0
    iget v1, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mRefNumber:I

    add-int/lit8 v1, v1, -0x1

    iput v1, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mRefNumber:I

    if-nez v1, :cond_0

    .line 97
    iget-object v1, p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;->mLockKey:Ljava/lang/String;

    invoke-static {v1}, Lcom/hcn/common/lang/HLockUtils;->-$$Nest$smremoveAutoLock(Ljava/lang/String;)V

    .line 99
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    :cond_1
    :goto_0
    return-object p0
.end method


# virtual methods
.method public close()V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 107
    invoke-direct {p0}, Lcom/hcn/common/lang/HLockUtils$AutoLock;->unLock()Lcom/hcn/common/lang/HLockUtils$AutoLock;

    return-void
.end method
