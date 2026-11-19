.class Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;
.super Ljava/lang/Object;
.source "LocalBluetoothHfpclientManager.java"

# interfaces
.implements Landroid/content/ServiceConnection;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;


# direct methods
.method constructor <init>(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;)V
    .locals 0

    .line 68
    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V
    .locals 1

    const-string p1, "LocalBluetoothHfpclientManager"

    const-string v0, "onServiceConnected"

    .line 79
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 80
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    invoke-static {p2}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    move-result-object p2

    invoke-static {p1, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->access$102(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;)Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    .line 81
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    const/4 p2, 0x1

    invoke-static {p1, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->access$002(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;Z)Z

    return-void
.end method

.method public onServiceDisconnected(Landroid/content/ComponentName;)V
    .locals 1

    const-string p1, "LocalBluetoothHfpclientManager"

    const-string v0, "onServiceDisconnected"

    .line 72
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 73
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->access$002(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;Z)Z

    .line 74
    iget-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;->this$0:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->access$102(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;)Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    return-void
.end method
