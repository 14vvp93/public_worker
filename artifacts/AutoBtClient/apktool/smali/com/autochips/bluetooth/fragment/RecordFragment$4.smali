.class Lcom/autochips/bluetooth/fragment/RecordFragment$4;
.super Lcom/hcn/bluetooth/service/IPbapCallback$Stub;
.source "RecordFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/RecordFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/RecordFragment;)V
    .locals 0

    .line 688
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-direct {p0}, Lcom/hcn/bluetooth/service/IPbapCallback$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public onPbapConnectStateChanged(I)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    return-void
.end method

.method public onPbapDownloadStateChanged(II)V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    const/4 v0, 0x6

    if-ne p2, v0, :cond_2

    const/4 p2, 0x1

    if-nez p1, :cond_0

    .line 693
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    if-eqz p1, :cond_2

    .line 694
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->removeMessages(I)V

    .line 695
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    :cond_0
    if-ne p1, p2, :cond_1

    .line 698
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    if-eqz p1, :cond_2

    .line 699
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    const/4 p2, 0x0

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->removeMessages(I)V

    .line 700
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 703
    :cond_1
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p2

    const/4 v0, 0x2

    invoke-virtual {p2, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->removeMessages(I)V

    .line 704
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p2

    invoke-virtual {p2, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object p2

    .line 705
    iput p1, p2, Landroid/os/Message;->arg1:I

    .line 706
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    :cond_2
    :goto_0
    return-void
.end method
