.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;
.super Ljava/lang/Object;
.source "PairedFragmentEx.java"

# interfaces
.implements Lcom/hcn/bluetooth/api/ConnectionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 568
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onServiceConnected"

    .line 571
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 572
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$500(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/service/IAdapterCallback;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    .line 573
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->removeMessages(I)V

    .line 574
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onServiceDisconnected()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onServiceDisconnected"

    .line 579
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method
