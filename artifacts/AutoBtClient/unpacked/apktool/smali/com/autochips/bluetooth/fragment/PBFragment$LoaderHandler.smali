.class Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;
.super Landroid/os/Handler;
.source "PBFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "LoaderHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;Landroid/os/Looper;)V
    .locals 0

    .line 186
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    .line 187
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 192
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 193
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 195
    :try_start_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/lang/String;

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->loadContact(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    const-string p1, "PBFragment"

    const-string v0, "handleMessage: MSG_PB_SEARCH Exception"

    .line 197
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 198
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    move-result-object p1

    const/4 v0, 0x6

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendEmptyMessage(I)Z

    :cond_0
    :goto_0
    return-void
.end method
