.class Lcom/autochips/bluetooth/fragment/SetupFragment$1;
.super Ljava/lang/Object;
.source "SetupFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/SetupFragment;->initView()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V
    .locals 0

    .line 148
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 151
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 153
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_0

    .line 155
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    .line 157
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->resetBT()V

    :cond_1
    return-void
.end method
