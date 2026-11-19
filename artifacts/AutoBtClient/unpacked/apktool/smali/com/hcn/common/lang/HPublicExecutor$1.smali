.class Lcom/hcn/common/lang/HPublicExecutor$1;
.super Lcom/hcn/common/concurrent/NamedThreadFactory;
.source "HPublicExecutor.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HPublicExecutor;-><init>()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/lang/HPublicExecutor;


# direct methods
.method constructor <init>(Lcom/hcn/common/lang/HPublicExecutor;Ljava/lang/String;)V
    .locals 0

    .line 64
    iput-object p1, p0, Lcom/hcn/common/lang/HPublicExecutor$1;->this$0:Lcom/hcn/common/lang/HPublicExecutor;

    invoke-direct {p0, p2}, Lcom/hcn/common/concurrent/NamedThreadFactory;-><init>(Ljava/lang/String;)V

    return-void
.end method


# virtual methods
.method public newThread(Ljava/lang/Runnable;)Ljava/lang/Thread;
    .locals 2

    .line 68
    invoke-super {p0, p1}, Lcom/hcn/common/concurrent/NamedThreadFactory;->newThread(Ljava/lang/Runnable;)Ljava/lang/Thread;

    move-result-object p1

    .line 69
    invoke-virtual {p1}, Ljava/lang/Thread;->getPriority()I

    move-result v0

    const/4 v1, 0x5

    if-eq v0, v1, :cond_0

    .line 71
    invoke-virtual {p1, v1}, Ljava/lang/Thread;->setPriority(I)V

    :cond_0
    return-object p1
.end method
