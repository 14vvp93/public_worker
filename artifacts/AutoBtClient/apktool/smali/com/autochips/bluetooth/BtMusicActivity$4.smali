.class Lcom/autochips/bluetooth/BtMusicActivity$4;
.super Ljava/lang/Object;
.source "BtMusicActivity.java"

# interfaces
.implements Lcom/hcn/bluetooth/api/ConnectionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/BtMusicActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/BtMusicActivity;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/BtMusicActivity;)V
    .locals 0

    .line 533
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected()V
    .locals 4

    const-string v0, "BTMusicActivity"

    const-string v1, "BluetoothMusic onServiceConnected"

    .line 536
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 537
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget-object v0, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object v1

    invoke-virtual {v1}, Lcom/autochips/bluetooth/MyApplication;->getBinder()Landroid/os/IBinder;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->regMusicClientBinder(Landroid/os/IBinder;)V

    .line 538
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget-object v0, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1100(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/service/IMusicCallback;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->registerBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V

    .line 539
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget-object v0, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->requestA2dp()V

    .line 541
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget-object v0, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isAvrcpConnected()Z

    move-result v0

    .line 542
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget-object v1, v1, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isA2dpConnected()Z

    move-result v1

    const/4 v2, 0x2

    const/4 v3, 0x0

    if-eqz v1, :cond_0

    .line 544
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iput v2, v1, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    goto :goto_0

    .line 546
    :cond_0
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iput v3, v1, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    :goto_0
    if-eqz v0, :cond_1

    .line 549
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iput v2, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    goto :goto_1

    .line 551
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iput v3, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    .line 553
    :goto_1
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget v1, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    invoke-static {v0, v1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$000(Lcom/autochips/bluetooth/BtMusicActivity;I)V

    .line 554
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$4;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget v1, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    invoke-static {v0, v1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$100(Lcom/autochips/bluetooth/BtMusicActivity;I)V

    return-void
.end method

.method public onServiceDisconnected()V
    .locals 2

    const-string v0, "BTMusicActivity"

    const-string v1, "BluetoothMusic onServiceDisconnected"

    .line 559
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method
