.class Lcom/hcn/common/lang/HThreadUtils$3;
.super Ljava/lang/Object;
.source "HThreadUtils.java"

# interfaces
.implements Ljava/util/concurrent/Executor;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HThreadUtils;->getGlobalDeliver()Ljava/util/concurrent/Executor;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 1413
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public execute(Ljava/lang/Runnable;)V
    .locals 0

    .line 1416
    invoke-static {p1}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method
