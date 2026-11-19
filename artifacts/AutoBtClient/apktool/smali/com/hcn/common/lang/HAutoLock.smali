.class public Lcom/hcn/common/lang/HAutoLock;
.super Ljava/lang/Object;
.source "HAutoLock.java"

# interfaces
.implements Ljava/lang/AutoCloseable;


# instance fields
.field private final mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 13
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 16
    new-instance v0, Ljava/util/concurrent/locks/ReentrantLock;

    invoke-direct {v0}, Ljava/util/concurrent/locks/ReentrantLock;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/lang/HAutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    return-void
.end method

.method public static getAutoLock()Lcom/hcn/common/lang/HAutoLock;
    .locals 1

    .line 28
    new-instance v0, Lcom/hcn/common/lang/HAutoLock;

    invoke-direct {v0}, Lcom/hcn/common/lang/HAutoLock;-><init>()V

    invoke-virtual {v0}, Lcom/hcn/common/lang/HAutoLock;->lock()Lcom/hcn/common/lang/HAutoLock;

    move-result-object v0

    return-object v0
.end method

.method private unLock()Lcom/hcn/common/lang/HAutoLock;
    .locals 1

    .line 38
    iget-object v0, p0, Lcom/hcn/common/lang/HAutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    if-eqz v0, :cond_0

    .line 39
    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->isLocked()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 40
    iget-object v0, p0, Lcom/hcn/common/lang/HAutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->unlock()V

    :cond_0
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

    .line 48
    invoke-direct {p0}, Lcom/hcn/common/lang/HAutoLock;->unLock()Lcom/hcn/common/lang/HAutoLock;

    return-void
.end method

.method public lock()Lcom/hcn/common/lang/HAutoLock;
    .locals 1

    .line 23
    iget-object v0, p0, Lcom/hcn/common/lang/HAutoLock;->mReentrantLock:Ljava/util/concurrent/locks/ReentrantLock;

    invoke-virtual {v0}, Ljava/util/concurrent/locks/ReentrantLock;->lock()V

    return-object p0
.end method
