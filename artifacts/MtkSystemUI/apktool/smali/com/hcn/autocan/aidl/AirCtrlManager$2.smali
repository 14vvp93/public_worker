.class Lcom/hcn/autocan/aidl/AirCtrlManager$2;
.super Lcom/hcn/autocan/aidl/IAirCallBack$Stub;
.source "AirCtrlManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/autocan/aidl/AirCtrlManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;


# direct methods
.method constructor <init>(Lcom/hcn/autocan/aidl/AirCtrlManager;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 133
    iput-object p1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$2;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-direct {p0}, Lcom/hcn/autocan/aidl/IAirCallBack$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public CallBackEvent(Lcom/hcn/autocan/hiworld/gm/air/AIR;)V
    .locals 6
    .param p1, "airBean"    # Lcom/hcn/autocan/hiworld/gm/air/AIR;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 136
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$2;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$200(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/autocan/aidl/IAirCallBack;

    .line 138
    .local v1, "callBack":Lcom/hcn/autocan/aidl/IAirCallBack;
    :try_start_0
    invoke-interface {v1, p1}, Lcom/hcn/autocan/aidl/IAirCallBack;->CallBackEvent(Lcom/hcn/autocan/hiworld/gm/air/AIR;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 141
    goto :goto_1

    .line 139
    :catch_0
    move-exception v2

    .line 140
    .local v2, "e":Ljava/lang/Exception;
    iget-object v3, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$2;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;

    move-result-object v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "err "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 142
    .end local v1    # "callBack":Lcom/hcn/autocan/aidl/IAirCallBack;
    .end local v2    # "e":Ljava/lang/Exception;
    :goto_1
    goto :goto_0

    .line 143
    :cond_0
    return-void
.end method
