.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;
.super Landroid/os/Handler;
.source "PairedFragmentEx.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "PairedHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Landroid/os/Looper;)V
    .locals 0

    .line 584
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    .line 585
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 7

    .line 590
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 591
    iget v0, p1, Landroid/os/Message;->what:I

    const v1, 0x7f0d004f

    const v2, 0x7f0d0084

    const/4 v3, 0x0

    const/4 v4, 0x1

    const-string v5, "PairedFragmentEx"

    const-string v6, ""

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_4

    .line 667
    :pswitch_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 668
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_0

    .line 670
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1102(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Ljava/lang/String;)Ljava/lang/String;

    .line 671
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 672
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 673
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, v1, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;ILjava/lang/String;)V

    goto :goto_0

    .line 675
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1102(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Ljava/lang/String;)Ljava/lang/String;

    .line 676
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-static {p1, v0, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;ILjava/lang/String;)V

    .line 679
    :cond_1
    :goto_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 680
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto/16 :goto_4

    :pswitch_1
    const-string v0, "MSG_CONNECT_STATE_CHANGE"

    .line 635
    invoke-static {v5, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 636
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 637
    iget p1, p1, Landroid/os/Message;->arg1:I

    .line 638
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v5

    if-nez v5, :cond_2

    move-object v5, v6

    :cond_2
    if-ne p1, v4, :cond_3

    const p1, 0x7f0d0050

    .line 644
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    :goto_1
    move-object v6, v5

    goto :goto_2

    :cond_3
    const/4 v4, 0x2

    if-ne p1, v4, :cond_4

    .line 646
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    .line 647
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v1

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 648
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v1

    invoke-virtual {v1, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 649
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v1

    invoke-virtual {v1, v3}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setPairedStatus(Z)V

    .line 650
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1102(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Ljava/lang/String;)Ljava/lang/String;

    .line 651
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 652
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto :goto_1

    :cond_4
    const/4 v0, 0x3

    if-ne p1, v0, :cond_5

    const p1, 0x7f0d0053

    .line 654
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    goto :goto_1

    .line 657
    :cond_5
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1102(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Ljava/lang/String;)Ljava/lang/String;

    .line 658
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    .line 659
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 660
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 661
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 662
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 664
    :goto_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0, p1, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;ILjava/lang/String;)V

    goto/16 :goto_4

    .line 616
    :pswitch_2
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 617
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "MSG_BOND_STATE_CHANGE name="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " status="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v5, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 618
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v0

    const/16 v1, 0xc

    if-ne v0, v1, :cond_6

    .line 619
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 620
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 621
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1, v3}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setPairedStatus(Z)V

    goto :goto_3

    .line 622
    :cond_6
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v0

    const/16 v1, 0xa

    if-ne v0, v1, :cond_7

    .line 623
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 624
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 625
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1, v3}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setPairedStatus(Z)V

    goto :goto_3

    .line 626
    :cond_7
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v0

    const/16 v1, 0xb

    if-ne v0, v1, :cond_8

    .line 627
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 628
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1, v4}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setPairedStatus(Z)V

    .line 631
    :cond_8
    :goto_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 632
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto :goto_4

    .line 613
    :pswitch_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto :goto_4

    .line 610
    :pswitch_4
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto :goto_4

    .line 599
    :pswitch_5
    iget p1, p1, Landroid/os/Message;->arg1:I

    if-ne p1, v4, :cond_9

    const-string p1, "MSG_DISCOVERY_STATE_CHANGE start"

    .line 600
    invoke-static {v5, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 601
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 602
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto :goto_4

    :cond_9
    const-string p1, "MSG_DISCOVERY_STATE_CHANGE end"

    .line 604
    invoke-static {v5, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 605
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 606
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$900(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    goto :goto_4

    .line 593
    :pswitch_6
    iget p1, p1, Landroid/os/Message;->arg1:I

    .line 594
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "MSG_BLUETOOTH_STATE_CHANGE "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v5, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x6

    .line 595
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->removeMessages(I)V

    const-wide/16 v0, 0x64

    .line 596
    invoke-virtual {p0, p1, v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendEmptyMessageDelayed(IJ)Z

    :goto_4
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
