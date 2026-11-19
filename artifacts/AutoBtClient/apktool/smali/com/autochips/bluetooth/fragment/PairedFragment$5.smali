.class Lcom/autochips/bluetooth/fragment/PairedFragment$5;
.super Ljava/lang/Object;
.source "PairedFragment.java"

# interfaces
.implements Lcom/hcn/bluetooth/api/ConnectionListener;


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

    .line 889
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onServiceConnected"

    .line 892
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 893
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1200(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/service/IAdapterCallback;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    .line 894
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->removeMessages(I)V

    .line 895
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$5;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onServiceDisconnected()V
    .locals 0

    return-void
.end method
