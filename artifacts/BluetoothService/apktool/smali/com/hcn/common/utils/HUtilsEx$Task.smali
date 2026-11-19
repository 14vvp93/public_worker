.class public abstract Lcom/hcn/common/utils/HUtilsEx$Task;
.super Lcom/hcn/common/lang/HThreadUtils$SimpleTask;
.source "HUtilsEx.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HUtilsEx;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Task"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<Result:",
        "Ljava/lang/Object;",
        ">",
        "Lcom/hcn/common/lang/HThreadUtils$SimpleTask<",
        "TResult;>;"
    }
.end annotation


# instance fields
.field private mConsumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "TResult;>;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "TResult;>;)V"
        }
    .end annotation

    .line 536
    invoke-direct {p0}, Lcom/hcn/common/lang/HThreadUtils$SimpleTask;-><init>()V

    .line 537
    iput-object p1, p0, Lcom/hcn/common/utils/HUtilsEx$Task;->mConsumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    return-void
.end method


# virtual methods
.method public onSuccess(Ljava/lang/Object;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TResult;)V"
        }
    .end annotation

    .line 542
    iget-object v0, p0, Lcom/hcn/common/utils/HUtilsEx$Task;->mConsumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    if-eqz v0, :cond_0

    .line 543
    invoke-interface {v0, p1}, Lcom/hcn/common/utils/HUtilsEx$Consumer;->accept(Ljava/lang/Object;)V

    :cond_0
    return-void
.end method
