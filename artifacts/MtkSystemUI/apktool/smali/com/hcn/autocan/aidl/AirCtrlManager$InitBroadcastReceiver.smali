.class public Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;
.super Landroid/content/BroadcastReceiver;
.source "AirCtrlManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/autocan/aidl/AirCtrlManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "InitBroadcastReceiver"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;


# direct methods
.method public constructor <init>(Lcom/hcn/autocan/aidl/AirCtrlManager;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 159
    iput-object p1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 162
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 163
    .local v0, "action":Ljava/lang/String;
    sget-object v1, Lcom/hcn/util/HCNConstants;->ACTION_INIT_CAN:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 164
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "init can"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 165
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-virtual {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->unRegisterCallBack()V

    .line 166
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$300(Lcom/hcn/autocan/aidl/AirCtrlManager;)V

    goto :goto_0

    .line 167
    :cond_0
    sget-object v1, Lcom/hcn/util/HCNConstants;->ACTION_UNBIND_CAN:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 168
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$400(Lcom/hcn/autocan/aidl/AirCtrlManager;)Landroid/content/Context;

    move-result-object v1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$500(Lcom/hcn/autocan/aidl/AirCtrlManager;)Landroid/content/ServiceConnection;

    move-result-object v1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$100(Lcom/hcn/autocan/aidl/AirCtrlManager;)Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v1

    if-eqz v1, :cond_1

    .line 169
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "unbindService "

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 171
    :try_start_0
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$400(Lcom/hcn/autocan/aidl/AirCtrlManager;)Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v2}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$500(Lcom/hcn/autocan/aidl/AirCtrlManager;)Landroid/content/ServiceConnection;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 174
    goto :goto_0

    .line 172
    :catch_0
    move-exception v1

    .line 173
    .local v1, "e":Ljava/lang/Exception;
    iget-object v2, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v2}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "e "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 177
    .end local v1    # "e":Ljava/lang/Exception;
    :cond_1
    :goto_0
    return-void
.end method
