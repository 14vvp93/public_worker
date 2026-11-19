.class Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;
.super Landroid/os/Handler;
.source "PairedFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "MainHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragment;Landroid/os/Looper;)V
    .locals 0

    .line 123
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    .line 124
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 6

    .line 129
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 130
    iget v0, p1, Landroid/os/Message;->what:I

    const v1, 0x7f0d004f

    const v2, 0x7f0d0084

    const/4 v3, 0x1

    const-string v4, "ParedFragment"

    const-string v5, ""

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_4

    .line 194
    :pswitch_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 195
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_0

    .line 197
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 198
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v0

    invoke-virtual {v0, v5}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 199
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, v1, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragment;ILjava/lang/String;)V

    goto :goto_0

    .line 201
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-static {p1, v0, v5}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragment;ILjava/lang/String;)V

    .line 204
    :cond_1
    :goto_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 205
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$500(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 206
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$800(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto/16 :goto_4

    :pswitch_1
    const-string v0, "MSG_CONNECT_STATE_CHANGE"

    .line 169
    invoke-static {v4, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 170
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 171
    iget p1, p1, Landroid/os/Message;->arg1:I

    .line 172
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_2

    move-object v4, v5

    :cond_2
    if-ne p1, v3, :cond_3

    const p1, 0x7f0d0050

    .line 178
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    :goto_1
    move-object v5, v4

    goto :goto_2

    :cond_3
    const/4 v3, 0x2

    if-ne p1, v3, :cond_4

    .line 180
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    .line 181
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v1

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 182
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v0

    invoke-virtual {v0, v5}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 183
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$500(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 184
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto :goto_1

    :cond_4
    const/4 v0, 0x3

    if-ne p1, v0, :cond_5

    const p1, 0x7f0d0053

    .line 186
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    goto :goto_1

    .line 189
    :cond_5
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    .line 191
    :goto_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0, p1, v5}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$600(Lcom/autochips/bluetooth/fragment/PairedFragment;ILjava/lang/String;)V

    goto/16 :goto_4

    .line 155
    :pswitch_2
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 156
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

    invoke-static {v4, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 157
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v0

    const/16 v1, 0xc

    if-ne v0, v1, :cond_6

    .line 158
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 159
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object p1

    invoke-virtual {p1, v5}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    goto :goto_3

    .line 160
    :cond_6
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v0

    const/16 v1, 0xa

    if-ne v0, v1, :cond_7

    .line 161
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v0

    invoke-virtual {v0, v5}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 162
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object v0

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 165
    :cond_7
    :goto_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$500(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 166
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto :goto_4

    .line 152
    :pswitch_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto :goto_4

    .line 149
    :pswitch_4
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto :goto_4

    .line 138
    :pswitch_5
    iget p1, p1, Landroid/os/Message;->arg1:I

    if-ne p1, v3, :cond_8

    const-string p1, "MSG_DISCOVERY_STATE_CHANGE start"

    .line 139
    invoke-static {v4, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 140
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$000(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 141
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto :goto_4

    :cond_8
    const-string p1, "MSG_DISCOVERY_STATE_CHANGE end"

    .line 143
    invoke-static {v4, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 144
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 145
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    goto :goto_4

    .line 132
    :pswitch_6
    iget p1, p1, Landroid/os/Message;->arg1:I

    .line 133
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "MSG_BLUETOOTH_STATE_CHANGE "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v4, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x6

    .line 134
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->removeMessages(I)V

    const-wide/16 v0, 0x64

    .line 135
    invoke-virtual {p0, p1, v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendEmptyMessageDelayed(IJ)Z

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
