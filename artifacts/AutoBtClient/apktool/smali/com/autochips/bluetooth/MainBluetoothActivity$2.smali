.class Lcom/autochips/bluetooth/MainBluetoothActivity$2;
.super Ljava/lang/Object;
.source "MainBluetoothActivity.java"

# interfaces
.implements Lcom/hcn/bluetooth/api/ConnectionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/MainBluetoothActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/MainBluetoothActivity;)V
    .locals 0

    .line 468
    iput-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$2;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected()V
    .locals 2

    const-string v0, "MainBluetoothActivity"

    const-string v1, "onServiceConnected"

    .line 471
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 472
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$2;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$200(Lcom/autochips/bluetooth/MainBluetoothActivity;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothConnected()Z

    move-result v1

    invoke-static {v0, v1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$002(Lcom/autochips/bluetooth/MainBluetoothActivity;Z)Z

    .line 473
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$2;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$300(Lcom/autochips/bluetooth/MainBluetoothActivity;)Landroid/os/Handler;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    .line 474
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$2;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$300(Lcom/autochips/bluetooth/MainBluetoothActivity;)Landroid/os/Handler;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onServiceDisconnected()V
    .locals 0

    return-void
.end method
