.class Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;
.super Ljava/lang/Object;
.source "SetupFragmentEx.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->initView()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V
    .locals 0

    .line 167
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 170
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 172
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_0

    .line 174
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    .line 176
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->resetBT()V

    :cond_1
    return-void
.end method
