.class public Lcom/android/systemui/util/leak/GarbageMonitor$Service;
.super Lcom/android/systemui/SystemUI;
.source "GarbageMonitor.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/util/leak/GarbageMonitor;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Service"
.end annotation


# instance fields
.field private mGarbageMonitor:Lcom/android/systemui/util/leak/GarbageMonitor;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 438
    invoke-direct {p0}, Lcom/android/systemui/SystemUI;-><init>()V

    return-void
.end method


# virtual methods
.method public start()V
    .locals 3

    .line 443
    iget-object v0, p0, Lcom/android/systemui/util/leak/GarbageMonitor$Service;->mContext:Landroid/content/Context;

    .line 445
    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "sysui_force_enable_leak_reporting"

    .line 444
    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    if-eqz v0, :cond_0

    const/4 v2, 0x1

    nop

    :cond_0
    move v0, v2

    .line 447
    .local v0, "forceEnable":Z
    const-class v1, Lcom/android/systemui/util/leak/GarbageMonitor;

    invoke-static {v1}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/systemui/util/leak/GarbageMonitor;

    iput-object v1, p0, Lcom/android/systemui/util/leak/GarbageMonitor$Service;->mGarbageMonitor:Lcom/android/systemui/util/leak/GarbageMonitor;

    .line 448
    invoke-static {}, Lcom/android/systemui/util/leak/GarbageMonitor;->access$500()Z

    move-result v1

    if-nez v1, :cond_1

    if-eqz v0, :cond_2

    .line 451
    :cond_1
    const-string v1, "disableleakmonitor"

    invoke-static {v1}, Lcom/android/systemui/util/leak/GarbageMonitor;->access$600(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_2

    .line 452
    iget-object v1, p0, Lcom/android/systemui/util/leak/GarbageMonitor$Service;->mGarbageMonitor:Lcom/android/systemui/util/leak/GarbageMonitor;

    invoke-virtual {v1}, Lcom/android/systemui/util/leak/GarbageMonitor;->startLeakMonitor()V

    .line 456
    :cond_2
    if-eqz v0, :cond_3

    .line 459
    const-string v1, "disableheaptracking"

    invoke-static {v1}, Lcom/android/systemui/util/leak/GarbageMonitor;->access$600(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_3

    .line 460
    iget-object v1, p0, Lcom/android/systemui/util/leak/GarbageMonitor$Service;->mGarbageMonitor:Lcom/android/systemui/util/leak/GarbageMonitor;

    invoke-virtual {v1}, Lcom/android/systemui/util/leak/GarbageMonitor;->startHeapTracking()V

    .line 464
    :cond_3
    return-void
.end method
