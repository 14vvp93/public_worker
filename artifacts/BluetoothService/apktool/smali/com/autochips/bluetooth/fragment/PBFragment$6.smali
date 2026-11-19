.class Lcom/autochips/bluetooth/fragment/PBFragment$6;
.super Lcom/hcn/bluetooth/service/IPbapCallback$Stub;
.source "PBFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 841
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-direct {p0}, Lcom/hcn/bluetooth/service/IPbapCallback$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public onPbapConnectStateChanged(I)V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 863
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {v0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1500(Lcom/autochips/bluetooth/fragment/PBFragment;I)V

    return-void
.end method

.method public onPbapDownloadStateChanged(II)V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    const/4 v0, 0x2

    if-ne p2, v0, :cond_2

    const/4 p2, 0x1

    if-nez p1, :cond_0

    .line 846
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->removeMessages(I)V

    .line 847
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    :cond_0
    if-ne p1, p2, :cond_1

    const-string p1, "PBFragment"

    const-string p2, "Pbap Downloading..."

    .line 849
    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 850
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p1

    const/4 p2, 0x0

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->removeMessages(I)V

    .line 851
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 853
    :cond_1
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p2

    invoke-virtual {p2, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->removeMessages(I)V

    .line 854
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p2

    invoke-virtual {p2, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object p2

    .line 855
    iput p1, p2, Landroid/os/Message;->arg1:I

    .line 856
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$6;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    :cond_2
    :goto_0
    return-void
.end method
