.class public Lcom/hcn/common/lang/HLockUtils;
.super Ljava/lang/Object;
.source "HLockUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/lang/HLockUtils$AutoLock;
    }
.end annotation


# static fields
.field private static sLockMap:Ljava/util/concurrent/ConcurrentHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/ConcurrentHashMap<",
            "Ljava/lang/String;",
            "Lcom/hcn/common/lang/HLockUtils$AutoLock;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static bridge synthetic -$$Nest$smremoveAutoLock(Ljava/lang/String;)V
    .locals 0

    invoke-static {p0}, Lcom/hcn/common/lang/HLockUtils;->removeAutoLock(Ljava/lang/String;)V

    return-void
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 22
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lcom/hcn/common/lang/HLockUtils;->sLockMap:Ljava/util/concurrent/ConcurrentHashMap;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 19
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getAutoLock(Ljava/lang/String;)Lcom/hcn/common/lang/HLockUtils$AutoLock;
    .locals 3

    .line 30
    invoke-virtual {p0}, Ljava/lang/String;->intern()Ljava/lang/String;

    move-result-object v0

    monitor-enter v0

    .line 32
    :try_start_0
    sget-object v1, Lcom/hcn/common/lang/HLockUtils;->sLockMap:Ljava/util/concurrent/ConcurrentHashMap;

    new-instance v2, Lcom/hcn/common/lang/HLockUtils$$ExternalSyntheticLambda0;

    invoke-direct {v2, p0}, Lcom/hcn/common/lang/HLockUtils$$ExternalSyntheticLambda0;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p0, v2}, Ljava/util/concurrent/ConcurrentHashMap;->computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/hcn/common/lang/HLockUtils$AutoLock;

    .line 35
    invoke-static {p0}, Lcom/hcn/common/lang/HLockUtils$AutoLock;->-$$Nest$mlock(Lcom/hcn/common/lang/HLockUtils$AutoLock;)Lcom/hcn/common/lang/HLockUtils$AutoLock;

    move-result-object p0

    monitor-exit v0

    return-object p0

    :catchall_0
    move-exception p0

    .line 36
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method static synthetic lambda$getAutoLock$0(Ljava/lang/String;Ljava/lang/String;)Lcom/hcn/common/lang/HLockUtils$AutoLock;
    .locals 0

    .line 34
    new-instance p1, Lcom/hcn/common/lang/HLockUtils$AutoLock;

    invoke-direct {p1, p0}, Lcom/hcn/common/lang/HLockUtils$AutoLock;-><init>(Ljava/lang/String;)V

    return-object p1
.end method

.method private static removeAutoLock(Ljava/lang/String;)V
    .locals 1

    .line 44
    sget-object v0, Lcom/hcn/common/lang/HLockUtils;->sLockMap:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0, p0}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method
