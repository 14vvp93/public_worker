.class Lcom/autochips/bluetooth/BtMusicActivity$3;
.super Lcom/hcn/bluetooth/service/IMusicCallback$Stub;
.source "BtMusicActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/BtMusicActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/BtMusicActivity;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/BtMusicActivity;)V
    .locals 0

    .line 481
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-direct {p0}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public onA2dpConnectStateChanged(I)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 485
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onA2dpConnectStateChanged:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "BTMusicActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 486
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iput p1, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    .line 487
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    const/4 v0, 0x2

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onAvrcpConnectStateChanged(I)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 492
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onAvrcpConnectStateChanged:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "BTMusicActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 493
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iput p1, v0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    .line 494
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    const/4 v0, 0x3

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onMetadataChanged(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 500
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onMetadataChanged: title="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " artist="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " album="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "BTMusicActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 502
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$202(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;)Ljava/lang/String;

    .line 503
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1, p2}, Lcom/autochips/bluetooth/BtMusicActivity;->access$302(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;)Ljava/lang/String;

    .line 504
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1, p3}, Lcom/autochips/bluetooth/BtMusicActivity;->access$402(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;)Ljava/lang/String;

    .line 505
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    const/4 p2, 0x1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onPlayStatusChanged(Lcom/hcn/bluetooth/api/MusicPlayState;)V
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    const-string v0, "BTMusicActivity"

    if-nez p1, :cond_0

    const-string p1, "onPlayStatusChanged: null!!"

    .line 511
    invoke-static {v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 514
    :cond_0
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onPlayStatusChanged:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/MusicPlayState;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 515
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/MusicPlayState;->getState()I

    move-result v0

    .line 516
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$700(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/api/MusicPlayState;

    move-result-object v1

    const/4 v2, 0x4

    const/4 v3, 0x5

    if-eqz v1, :cond_1

    const/4 v1, 0x2

    if-ne v0, v1, :cond_1

    .line 517
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$702(Lcom/autochips/bluetooth/BtMusicActivity;Lcom/hcn/bluetooth/api/MusicPlayState;)Lcom/hcn/bluetooth/api/MusicPlayState;

    .line 518
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v2}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->removeMessages(I)V

    .line 520
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v3}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->removeMessages(I)V

    .line 521
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v3}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 523
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$702(Lcom/autochips/bluetooth/BtMusicActivity;Lcom/hcn/bluetooth/api/MusicPlayState;)Lcom/hcn/bluetooth/api/MusicPlayState;

    .line 524
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v3}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->removeMessages(I)V

    .line 525
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v3}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessage(I)Z

    .line 527
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v2}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->removeMessages(I)V

    .line 528
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$3;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    move-result-object p1

    invoke-virtual {p1, v2}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessage(I)Z

    :goto_0
    return-void
.end method
