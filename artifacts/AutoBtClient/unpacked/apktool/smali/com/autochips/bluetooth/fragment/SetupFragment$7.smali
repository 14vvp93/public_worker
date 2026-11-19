.class Lcom/autochips/bluetooth/fragment/SetupFragment$7;
.super Ljava/lang/Object;
.source "SetupFragment.java"

# interfaces
.implements Lcom/hcn/bluetooth/api/ConnectionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/SetupFragment;
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

    .line 602
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected()V
    .locals 3

    .line 605
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$200(Lcom/autochips/bluetooth/fragment/SetupFragment;)Landroid/view/View;

    move-result-object v0

    const v1, 0x7f08016b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 606
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 607
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v2

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v1, Lcom/autochips/bluetooth/fragment/SetupFragment;->mDevName:Ljava/lang/String;

    :cond_0
    if-eqz v0, :cond_1

    .line 610
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    iget-object v1, v1, Lcom/autochips/bluetooth/fragment/SetupFragment;->mDevName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 613
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->access$600(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    return-void
.end method

.method public onServiceDisconnected()V
    .locals 0

    return-void
.end method
