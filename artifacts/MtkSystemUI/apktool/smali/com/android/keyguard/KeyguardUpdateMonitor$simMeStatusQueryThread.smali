.class Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;
.super Ljava/lang/Thread;
.source "KeyguardUpdateMonitor.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/keyguard/KeyguardUpdateMonitor;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "simMeStatusQueryThread"
.end annotation


# instance fields
.field simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

.field final synthetic this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;


# direct methods
.method constructor <init>(Lcom/android/keyguard/KeyguardUpdateMonitor;Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;)V
    .locals 0
    .param p2, "simArgs"    # Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    .line 2482
    iput-object p1, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    .line 2483
    iput-object p2, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    .line 2484
    return-void
.end method


# virtual methods
.method public run()V
    .locals 7

    .line 2489
    :try_start_0
    iget-object v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->access$4800(Lcom/android/keyguard/KeyguardUpdateMonitor;)Ljava/util/HashMap;

    move-result-object v0

    iget-object v1, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v1, v1, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    iget-object v2, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v2, v2, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 2490
    const-string v0, "KeyguardUpdateMonitor"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "queryNetworkLock, phoneId ="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v2, v2, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", simMECategory ="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v2, v2, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2493
    iget-object v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v0, v0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    if-ltz v0, :cond_2

    iget-object v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v0, v0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    const/4 v1, 0x5

    if-le v0, v1, :cond_0

    goto :goto_1

    .line 2497
    :cond_0
    iget-object v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v0, v0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v0

    .line 2498
    .local v0, "subId":I
    const-string v2, "phoneEx"

    .line 2499
    invoke-static {v2}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v2

    .line 2498
    invoke-static {v2}, Lcom/mediatek/internal/telephony/IMtkTelephonyEx$Stub;->asInterface(Landroid/os/IBinder;)Lcom/mediatek/internal/telephony/IMtkTelephonyEx;

    move-result-object v2

    iget-object v3, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v3, v3, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    .line 2500
    invoke-interface {v2, v0, v3}, Lcom/mediatek/internal/telephony/IMtkTelephonyEx;->queryNetworkLock(II)Landroid/os/Bundle;

    move-result-object v2

    .line 2501
    .local v2, "bundle":Landroid/os/Bundle;
    const-string v3, "com.mediatek.phone.QUERY_SIMME_LOCK_RESULT"

    const/4 v4, 0x0

    invoke-virtual {v2, v3, v4}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    .line 2503
    .local v3, "query_result":Z
    const-string v4, "KeyguardUpdateMonitor"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "queryNetworkLock, query_result ="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2505
    if-eqz v3, :cond_1

    .line 2506
    iget-object v4, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-static {v4}, Lcom/android/keyguard/KeyguardUpdateMonitor;->access$4900(Lcom/android/keyguard/KeyguardUpdateMonitor;)Ljava/util/HashMap;

    move-result-object v4

    iget-object v5, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    iget v5, v5, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const-string v6, "com.mediatek.phone.SIMME_LOCK_LEFT_COUNT"

    .line 2507
    invoke-virtual {v2, v6, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    .line 2506
    invoke-virtual {v4, v5, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 2509
    :cond_1
    const-string v1, "KeyguardUpdateMonitor"

    const-string v4, "queryIccNetworkLock result fail"

    invoke-static {v1, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2511
    :goto_0
    iget-object v1, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-static {v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->access$2500(Lcom/android/keyguard/KeyguardUpdateMonitor;)Landroid/os/Handler;

    move-result-object v1

    iget-object v4, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->this$0:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-static {v4}, Lcom/android/keyguard/KeyguardUpdateMonitor;->access$2500(Lcom/android/keyguard/KeyguardUpdateMonitor;)Landroid/os/Handler;

    move-result-object v4

    const/16 v5, 0x130

    iget-object v6, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$simMeStatusQueryThread;->simArgs:Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    invoke-virtual {v4, v5, v6}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v4

    invoke-virtual {v1, v4}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 2514
    .end local v0    # "subId":I
    .end local v2    # "bundle":Landroid/os/Bundle;
    .end local v3    # "query_result":Z
    goto :goto_2

    .line 2494
    :cond_2
    :goto_1
    return-void

    .line 2512
    :catch_0
    move-exception v0

    .line 2513
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "KeyguardUpdateMonitor"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "queryIccNetworkLock got exception: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2515
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_2
    return-void
.end method
