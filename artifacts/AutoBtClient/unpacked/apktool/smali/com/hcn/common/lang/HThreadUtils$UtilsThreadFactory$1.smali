.class Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory$1;
.super Ljava/lang/Thread;
.source "HThreadUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;->newThread(Ljava/lang/Runnable;)Ljava/lang/Thread;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;


# direct methods
.method constructor <init>(Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;Ljava/lang/Runnable;Ljava/lang/String;)V
    .locals 0

    .line 1140
    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory$1;->this$0:Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;

    invoke-direct {p0, p2, p3}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1144
    :try_start_0
    invoke-super {p0}, Ljava/lang/Thread;->run()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    const-string v1, "ThreadUtils"

    const-string v2, "Request threw uncaught throwable"

    .line 1146
    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method
