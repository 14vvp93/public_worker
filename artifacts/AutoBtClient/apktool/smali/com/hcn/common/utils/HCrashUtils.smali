.class public final Lcom/hcn/common/utils/HCrashUtils;
.super Ljava/lang/Object;
.source "HCrashUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;,
        Lcom/hcn/common/utils/HCrashUtils$CrashInfo;
    }
.end annotation


# static fields
.field static final synthetic $assertionsDisabled:Z

.field private static final DEFAULT_UNCAUGHT_EXCEPTION_HANDLER:Ljava/lang/Thread$UncaughtExceptionHandler;

.field private static final FILE_SEP:Ljava/lang/String;


# direct methods
.method static bridge synthetic -$$Nest$sfgetDEFAULT_UNCAUGHT_EXCEPTION_HANDLER()Ljava/lang/Thread$UncaughtExceptionHandler;
    .locals 1

    sget-object v0, Lcom/hcn/common/utils/HCrashUtils;->DEFAULT_UNCAUGHT_EXCEPTION_HANDLER:Ljava/lang/Thread$UncaughtExceptionHandler;

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 1

    const-string v0, "file.separator"

    .line 18
    invoke-static {v0}, Ljava/lang/System;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/utils/HCrashUtils;->FILE_SEP:Ljava/lang/String;

    .line 21
    invoke-static {}, Ljava/lang/Thread;->getDefaultUncaughtExceptionHandler()Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/utils/HCrashUtils;->DEFAULT_UNCAUGHT_EXCEPTION_HANDLER:Ljava/lang/Thread$UncaughtExceptionHandler;

    return-void
.end method

.method private constructor <init>()V
    .locals 2

    .line 23
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 24
    new-instance v0, Ljava/lang/UnsupportedOperationException;

    const-string v1, "u can\'t instantiate me..."

    invoke-direct {v0, v1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method private static getUncaughtExceptionHandler(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)Ljava/lang/Thread$UncaughtExceptionHandler;
    .locals 1

    .line 95
    new-instance v0, Lcom/hcn/common/utils/HCrashUtils$1;

    invoke-direct {v0, p1, p0}, Lcom/hcn/common/utils/HCrashUtils$1;-><init>(Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;Ljava/lang/String;)V

    return-object v0
.end method

.method public static init()V
    .locals 1

    const-string v0, ""

    .line 31
    invoke-static {v0}, Lcom/hcn/common/utils/HCrashUtils;->init(Ljava/lang/String;)V

    return-void
.end method

.method public static init(Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V
    .locals 1

    const-string v0, ""

    .line 58
    invoke-static {v0, p0}, Lcom/hcn/common/utils/HCrashUtils;->init(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V

    return-void
.end method

.method public static init(Ljava/io/File;)V
    .locals 1

    .line 40
    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p0

    const/4 v0, 0x0

    invoke-static {p0, v0}, Lcom/hcn/common/utils/HCrashUtils;->init(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V

    return-void
.end method

.method public static init(Ljava/io/File;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V
    .locals 0

    .line 68
    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, p1}, Lcom/hcn/common/utils/HCrashUtils;->init(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V

    return-void
.end method

.method public static init(Ljava/lang/String;)V
    .locals 1

    const/4 v0, 0x0

    .line 49
    invoke-static {p0, v0}, Lcom/hcn/common/utils/HCrashUtils;->init(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V

    return-void
.end method

.method public static init(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)V
    .locals 3

    .line 79
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 80
    invoke-static {}, Lcom/hcn/common/utils/HSDCardUtils;->isSDCardEnableByEnvironment()Z

    move-result p0

    const-string v0, "crash"

    if-eqz p0, :cond_0

    .line 81
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object p0

    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Landroid/app/Application;->getExternalFilesDir(Ljava/lang/String;)Ljava/io/File;

    move-result-object p0

    if-eqz p0, :cond_0

    .line 82
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/app/Application;->getExternalFilesDir(Ljava/lang/String;)Ljava/io/File;

    move-result-object v1

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    sget-object v1, Lcom/hcn/common/utils/HCrashUtils;->FILE_SEP:Ljava/lang/String;

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    goto :goto_0

    .line 84
    :cond_0
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/Application;->getFilesDir()Ljava/io/File;

    move-result-object v1

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    sget-object v1, Lcom/hcn/common/utils/HCrashUtils;->FILE_SEP:Ljava/lang/String;

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    goto :goto_0

    .line 88
    :cond_1
    sget-object v0, Lcom/hcn/common/utils/HCrashUtils;->FILE_SEP:Ljava/lang/String;

    invoke-virtual {p0, v0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_2

    goto :goto_0

    :cond_2
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 90
    :goto_0
    invoke-static {p0, p1}, Lcom/hcn/common/utils/HCrashUtils;->getUncaughtExceptionHandler(Ljava/lang/String;Lcom/hcn/common/utils/HCrashUtils$OnCrashListener;)Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Thread;->setDefaultUncaughtExceptionHandler(Ljava/lang/Thread$UncaughtExceptionHandler;)V

    return-void
.end method
