.class Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;
.super Ljava/lang/Object;
.source "LocalBluetoothAdapterManager.java"

# interfaces
.implements Landroid/content/ServiceConnection;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;


# direct methods
.method constructor <init>(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;)V
    .locals 0

    .line 108
    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V
    .locals 1

    const-string p1, "LocalBluetoothAdapterManager"

    const-string v0, "onServiceConnected"

    .line 120
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 121
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 122
    invoke-static {p2}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    move-result-object p2

    .line 121
    invoke-static {p1, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->access$102(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;)Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    .line 123
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 p2, 0x1

    invoke-static {p1, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->access$002(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;Z)Z

    .line 124
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->callConnectListener(Z)V

    return-void
.end method

.method public onServiceDisconnected(Landroid/content/ComponentName;)V
    .locals 2

    const-string p1, "LocalBluetoothAdapterManager"

    const-string v0, "onServiceDisconnected"

    .line 112
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 113
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->access$002(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;Z)Z

    .line 114
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 v1, 0x0

    invoke-static {p1, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->access$102(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;)Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    .line 115
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->callConnectListener(Z)V

    return-void
.end method
