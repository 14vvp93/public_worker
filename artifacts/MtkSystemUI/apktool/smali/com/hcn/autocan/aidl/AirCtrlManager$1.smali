.class Lcom/hcn/autocan/aidl/AirCtrlManager$1;
.super Ljava/lang/Object;
.source "AirCtrlManager.java"

# interfaces
.implements Landroid/content/ServiceConnection;


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

    .line 66
    iput-object p1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onBindingDied(Landroid/content/ComponentName;)V
    .locals 2
    .param p1, "name"    # Landroid/content/ComponentName;

    .line 83
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-virtual {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->unRegisterCallBack()V

    .line 84
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$102(Lcom/hcn/autocan/aidl/AirCtrlManager;Lcom/hcn/autocan/aidl/IAirCtrlServices;)Lcom/hcn/autocan/aidl/IAirCtrlServices;

    .line 86
    return-void
.end method

.method public onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V
    .locals 2
    .param p1, "name"    # Landroid/content/ComponentName;
    .param p2, "service"    # Landroid/os/IBinder;

    .line 69
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;

    move-result-object v0

    const-string v1, " onServiceConnected "

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 70
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {p2}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$102(Lcom/hcn/autocan/aidl/AirCtrlManager;Lcom/hcn/autocan/aidl/IAirCtrlServices;)Lcom/hcn/autocan/aidl/IAirCtrlServices;

    .line 71
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-virtual {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->registerCallback()V

    .line 72
    return-void
.end method

.method public onServiceDisconnected(Landroid/content/ComponentName;)V
    .locals 2
    .param p1, "name"    # Landroid/content/ComponentName;

    .line 76
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-static {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "onServiceDisconnected"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 77
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-virtual {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->unRegisterCallBack()V

    .line 78
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;->this$0:Lcom/hcn/autocan/aidl/AirCtrlManager;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/hcn/autocan/aidl/AirCtrlManager;->access$102(Lcom/hcn/autocan/aidl/AirCtrlManager;Lcom/hcn/autocan/aidl/IAirCtrlServices;)Lcom/hcn/autocan/aidl/IAirCtrlServices;

    .line 79
    return-void
.end method
