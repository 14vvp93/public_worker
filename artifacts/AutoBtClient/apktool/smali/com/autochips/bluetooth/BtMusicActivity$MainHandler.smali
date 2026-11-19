.class Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;
.super Landroid/os/Handler;
.source "BtMusicActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/BtMusicActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "MainHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/BtMusicActivity;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/BtMusicActivity;Landroid/os/Looper;)V
    .locals 0

    .line 99
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    .line 100
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 4

    .line 105
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 106
    iget p1, p1, Landroid/os/Message;->what:I

    const/4 v0, 0x1

    if-eq p1, v0, :cond_4

    const/4 v0, 0x2

    if-eq p1, v0, :cond_3

    const/4 v0, 0x3

    if-eq p1, v0, :cond_2

    const/4 v1, 0x4

    if-eq p1, v1, :cond_1

    const/4 v0, 0x5

    if-eq p1, v0, :cond_0

    goto :goto_0

    .line 124
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$700(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/api/MusicPlayState;

    move-result-object p1

    if-eqz p1, :cond_5

    .line 125
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$700(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/api/MusicPlayState;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/MusicPlayState;->getState()I

    move-result v0

    invoke-static {p1, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->access$800(Lcom/autochips/bluetooth/BtMusicActivity;I)V

    goto :goto_0

    .line 117
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$600(Lcom/autochips/bluetooth/BtMusicActivity;)V

    .line 118
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$700(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/api/MusicPlayState;

    move-result-object p1

    if-eqz p1, :cond_5

    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    .line 119
    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$700(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/api/MusicPlayState;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/MusicPlayState;->getState()I

    move-result p1

    if-ne p1, v0, :cond_5

    const-wide/16 v2, 0x3e8

    .line 120
    invoke-virtual {p0, v1, v2, v3}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->sendEmptyMessageDelayed(IJ)Z

    goto :goto_0

    .line 111
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget v0, p1, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    invoke-static {p1, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->access$100(Lcom/autochips/bluetooth/BtMusicActivity;I)V

    goto :goto_0

    .line 108
    :cond_3
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    iget v0, p1, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    invoke-static {p1, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->access$000(Lcom/autochips/bluetooth/BtMusicActivity;I)V

    goto :goto_0

    .line 114
    :cond_4
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {p1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$200(Lcom/autochips/bluetooth/BtMusicActivity;)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v1}, Lcom/autochips/bluetooth/BtMusicActivity;->access$300(Lcom/autochips/bluetooth/BtMusicActivity;)Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->this$0:Lcom/autochips/bluetooth/BtMusicActivity;

    invoke-static {v2}, Lcom/autochips/bluetooth/BtMusicActivity;->access$400(Lcom/autochips/bluetooth/BtMusicActivity;)Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v0, v1, v2}, Lcom/autochips/bluetooth/BtMusicActivity;->access$500(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    :cond_5
    :goto_0
    return-void
.end method
