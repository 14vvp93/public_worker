.class Lcom/hcn/common/utils/HCrashUtils$1;
.super Ljava/lang/Object;
.source "HCrashUtils.java"

# interfaces
.implements Ljava/lang/Thread$UncaughtExceptionHandler;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/utils/HCrashUtils;->getUncaughtExceptionHandler(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)Ljava/lang/Thread$UncaughtExceptionHandler;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$dirPath:Ljava/lang/String;

.field final synthetic val$onCrashListener:Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 95
    iput-object p1, p0, Lcom/hcn/common/utils/HCrashUtils$1;->val$onCrashListener:Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;

    iput-object p2, p0, Lcom/hcn/common/utils/HCrashUtils$1;->val$dirPath:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public uncaughtException(Ljava/lang/Thread;Ljava/lang/Throwable;)V
    .locals 4

    .line 99
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "yyyy_MM_dd-HH_mm_ss"

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    new-instance v1, Ljava/util/Date;

    invoke-direct {v1}, Ljava/util/Date;-><init>()V

    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v0

    .line 100
    new-instance v1, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;

    const/4 v2, 0x0

    invoke-direct {v1, v0, p2, v2}, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;-><init>(Ljava/lang/String;Ljava/lang/Throwable;Lcom/hcn/common/utils/HCrashUtils$CrashInfo-IA;)V

    .line 101
    iget-object v2, p0, Lcom/hcn/common/utils/HCrashUtils$1;->val$onCrashListener:Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;

    if-eqz v2, :cond_0

    .line 102
    invoke-interface {v2, v1}, Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;->onCrash(Lcom/hcn/common/utils/HCrashUtils$CrashInfo;)V

    .line 104
    :cond_0
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/common/utils/HCrashUtils$1;->val$dirPath:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ".txt"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 105
    invoke-virtual {v1}, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->toString()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x1

    invoke-static {v0, v1, v2}, Lcom/hcn/common/utils/HFileIOUtils;->writeFileFromString(Ljava/lang/String;Ljava/lang/String;Z)Z

    .line 107
    invoke-static {}, Lcom/hcn/common/utils/HCrashUtils;->-$$Nest$sfgetDEFAULT_UNCAUGHT_EXCEPTION_HANDLER()Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 108
    invoke-static {}, Lcom/hcn/common/utils/HCrashUtils;->-$$Nest$sfgetDEFAULT_UNCAUGHT_EXCEPTION_HANDLER()Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object v0

    invoke-interface {v0, p1, p2}, Ljava/lang/Thread$UncaughtExceptionHandler;->uncaughtException(Ljava/lang/Thread;Ljava/lang/Throwable;)V

    :cond_1
    return-void
.end method
