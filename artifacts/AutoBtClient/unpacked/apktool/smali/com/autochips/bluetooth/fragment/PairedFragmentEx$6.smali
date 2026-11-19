.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;
.super Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;
.source "PairedFragmentEx.java"


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

    .line 906
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-direct {p0}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public onBluetoothStateChanged(I)V
    .locals 2

    .line 910
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 911
    iput p1, v0, Landroid/os/Message;->arg1:I

    .line 912
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onConnectionStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V
    .locals 2

    .line 945
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    const/4 v1, 0x5

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 946
    iput p2, v0, Landroid/os/Message;->arg1:I

    .line 947
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 948
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDeviceBondStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V
    .locals 1

    .line 938
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p2

    const/4 v0, 0x4

    invoke-static {p2, v0}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object p2

    .line 939
    iput-object p1, p2, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 940
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDiscoveryDeviceFound(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V
    .locals 2

    .line 924
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    const/4 v1, 0x2

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 925
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 926
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDiscoveryDeviceNameChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V
    .locals 2

    .line 931
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    const/4 v1, 0x3

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 932
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 933
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public onDiscoveryStateChanged(I)V
    .locals 2

    .line 917
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object v0

    const/4 v1, 0x1

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 918
    iput p1, v0, Landroid/os/Message;->arg1:I

    .line 919
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method
