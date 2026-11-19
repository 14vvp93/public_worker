.class Lcom/autochips/bluetooth/fragment/PairedFragment$6;
.super Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;
.source "PairedFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 904
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public onBluetoothStateChanged(I)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 907
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 908
    iput p1, v0, Landroid/os/Message;->arg1:I

    .line 909
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onConnectionStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 946
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    const/4 v1, 0x5

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 947
    iput p2, v0, Landroid/os/Message;->arg1:I

    .line 948
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 949
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDeviceBondStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 938
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p2

    const/4 v0, 0x4

    invoke-static {p2, v0}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object p2

    .line 939
    iput-object p1, p2, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 940
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDiscoveryDeviceFound(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 922
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    const/4 v1, 0x2

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 923
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 924
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDiscoveryDeviceNameChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 930
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    const/4 v1, 0x3

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 931
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 932
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDiscoveryStateChanged(I)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 914
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    const/4 v1, 0x1

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 915
    iput p1, v0, Landroid/os/Message;->arg1:I

    .line 916
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method
