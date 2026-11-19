.class public Lcom/hcn/auto_compat/os/ProcessCompat;
.super Ljava/lang/Object;
.source "ProcessCompat.java"


# static fields
.field public static final THREAD_PRIORITY_TOP_APP_BOOST:I = -0xa

.field private static sAdjustedThreadPriority:Z

.field private static final sSupportAdjustThreadPriority:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const-string v0, "ro.media.support.adj_priority"

    const-string v1, "0"

    .line 24
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "1"

    .line 23
    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/auto_compat/os/ProcessCompat;->sSupportAdjustThreadPriority:Z

    const/4 v0, 0x0

    .line 27
    sput-boolean v0, Lcom/hcn/auto_compat/os/ProcessCompat;->sAdjustedThreadPriority:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static setThreadPriority(Landroid/content/Context;I)V
    .locals 1

    .line 40
    sget-boolean v0, Lcom/hcn/auto_compat/os/ProcessCompat;->sSupportAdjustThreadPriority:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    const/16 v0, -0x10

    if-gt p1, v0, :cond_2

    const/4 v0, 0x1

    if-eqz p0, :cond_1

    .line 50
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p0

    .line 51
    invoke-static {p0}, Lcom/hcn/common/app/HAppUtils;->isAppForeground(Ljava/lang/String;)Z

    move-result p0

    goto :goto_0

    :cond_1
    move p0, v0

    :goto_0
    if-eqz p0, :cond_3

    .line 56
    sput-boolean v0, Lcom/hcn/auto_compat/os/ProcessCompat;->sAdjustedThreadPriority:Z

    .line 57
    invoke-static {p1}, Landroid/os/Process;->setThreadPriority(I)V

    goto :goto_1

    .line 61
    :cond_2
    sget-boolean p0, Lcom/hcn/auto_compat/os/ProcessCompat;->sAdjustedThreadPriority:Z

    if-eqz p0, :cond_3

    const/4 p0, 0x0

    .line 62
    sput-boolean p0, Lcom/hcn/auto_compat/os/ProcessCompat;->sAdjustedThreadPriority:Z

    .line 63
    invoke-static {p1}, Landroid/os/Process;->setThreadPriority(I)V

    :cond_3
    :goto_1
    return-void
.end method
