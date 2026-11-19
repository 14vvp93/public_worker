.class Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory$2;
.super Ljava/lang/Object;
.source "HThreadUtils.java"

# interfaces
.implements Ljava/lang/Thread$UncaughtExceptionHandler;


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
.method constructor <init>(Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;)V
    .locals 0

    .line 1151
    iput-object p1, p0, Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory$2;->this$0:Lcom/hcn/common/lang/HThreadUtils$UtilsThreadFactory;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public uncaughtException(Ljava/lang/Thread;Ljava/lang/Throwable;)V
    .locals 0

    .line 1154
    sget-object p1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {p1, p2}, Ljava/io/PrintStream;->println(Ljava/lang/Object;)V

    return-void
.end method
