.class public Lcom/hcn/skin/support/observe/SkinObservable;
.super Ljava/lang/Object;
.source "SkinObservable.java"


# instance fields
.field private final observers:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/skin/support/observe/SkinObserver;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 13
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 14
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/skin/support/observe/SkinObservable;->observers:Ljava/util/ArrayList;

    .line 15
    return-void
.end method


# virtual methods
.method public notifySkinChanged()V
    .locals 1

    .line 31
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/hcn/skin/support/observe/SkinObservable;->notifySkinChanged(Ljava/lang/Object;)V

    .line 32
    return-void
.end method

.method public notifySkinChanged(Ljava/lang/Object;)V
    .locals 3
    .param p1, "arg"    # Ljava/lang/Object;

    .line 37
    monitor-enter p0

    .line 38
    :try_start_0
    iget-object v0, p0, Lcom/hcn/skin/support/observe/SkinObservable;->observers:Ljava/util/ArrayList;

    iget-object v1, p0, Lcom/hcn/skin/support/observe/SkinObservable;->observers:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    new-array v1, v1, [Lcom/hcn/skin/support/observe/SkinObserver;

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/hcn/skin/support/observe/SkinObserver;

    .line 39
    .local v0, "arrLocal":[Lcom/hcn/skin/support/observe/SkinObserver;
    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 41
    array-length v1, v0

    add-int/lit8 v1, v1, -0x1

    .local v1, "i":I
    :goto_0
    if-ltz v1, :cond_0

    .line 42
    aget-object v2, v0, v1

    invoke-interface {v2, p1}, Lcom/hcn/skin/support/observe/SkinObserver;->onSkinChanged(Ljava/lang/Object;)V

    .line 41
    add-int/lit8 v1, v1, -0x1

    goto :goto_0

    .line 44
    .end local v1    # "i":I
    :cond_0
    return-void

    .line 39
    .end local v0    # "arrLocal":[Lcom/hcn/skin/support/observe/SkinObserver;
    :catchall_0
    move-exception v0

    :try_start_1
    monitor-exit p0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v0
.end method
