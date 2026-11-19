.class Lcom/autochips/bluetooth/fragment/DialFragment$1;
.super Landroid/content/BroadcastReceiver;
.source "DialFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/DialFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/DialFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V
    .locals 0

    .line 106
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 2

    .line 109
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    .line 110
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "action = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "DialFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-string v0, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 111
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p1, 0x0

    const-string v0, "android.bluetooth.profile.extra.STATE"

    .line 112
    invoke-virtual {p2, v0, p1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const/4 p2, 0x2

    if-ne p1, p2, :cond_0

    .line 115
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$000(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    move-result-object p1

    const/4 p2, 0x3

    const-wide/16 v0, 0xc8

    invoke-virtual {p1, p2, v0, v1}, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->sendEmptyMessageDelayed(IJ)Z

    goto :goto_0

    :cond_0
    if-nez p1, :cond_2

    .line 118
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$100(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    .line 119
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$200(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    goto :goto_0

    :cond_1
    const-string v0, "android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED"

    .line 121
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_2

    const-string p1, "android.bluetooth.headsetclient.extra.CALL"

    .line 122
    invoke-virtual {p2, p1}, Landroid/content/Intent;->getParcelableExtra(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object p1

    check-cast p1, Landroid/bluetooth/BluetoothHeadsetClientCall;

    .line 123
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothHeadsetClientCall;->getState()I

    move-result p1

    .line 124
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "state = "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v1, p2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p2, 0x7

    if-ne p1, p2, :cond_2

    .line 126
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    iget-object p1, p1, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-eqz p1, :cond_2

    .line 127
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    iget-object p1, p1, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    const-string p2, ""

    invoke-virtual {p1, p2}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    :cond_2
    :goto_0
    return-void
.end method
