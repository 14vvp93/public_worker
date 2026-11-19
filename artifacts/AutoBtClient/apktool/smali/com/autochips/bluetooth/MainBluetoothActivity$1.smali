.class Lcom/autochips/bluetooth/MainBluetoothActivity$1;
.super Landroid/content/BroadcastReceiver;
.source "MainBluetoothActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/MainBluetoothActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/MainBluetoothActivity;)V
    .locals 0

    .line 87
    iput-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 2

    .line 90
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 91
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    const-string p1, "android.bluetooth.profile.extra.STATE"

    .line 92
    invoke-virtual {p2, p1, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const/4 p2, 0x2

    if-ne p1, p2, :cond_0

    .line 95
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    const/4 p2, 0x1

    invoke-static {p1, p2}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$002(Lcom/autochips/bluetooth/MainBluetoothActivity;Z)Z

    .line 96
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$100(Lcom/autochips/bluetooth/MainBluetoothActivity;)V

    goto :goto_0

    :cond_0
    if-nez p1, :cond_3

    .line 98
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {p1, v1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$002(Lcom/autochips/bluetooth/MainBluetoothActivity;Z)Z

    .line 99
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$100(Lcom/autochips/bluetooth/MainBluetoothActivity;)V

    goto :goto_0

    :cond_1
    const-string v0, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 101
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    const-string p1, "android.bluetooth.adapter.extra.STATE"

    const/16 v0, 0xa

    .line 102
    invoke-virtual {p2, p1, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    if-ne p1, v0, :cond_3

    .line 105
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {p1, v1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$002(Lcom/autochips/bluetooth/MainBluetoothActivity;Z)Z

    .line 106
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->access$100(Lcom/autochips/bluetooth/MainBluetoothActivity;)V

    goto :goto_0

    :cond_2
    const-string p2, "com.bt.extra.exit"

    .line 110
    invoke-virtual {p1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    .line 111
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;->this$0:Lcom/autochips/bluetooth/MainBluetoothActivity;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->finish()V

    :cond_3
    :goto_0
    return-void
.end method
