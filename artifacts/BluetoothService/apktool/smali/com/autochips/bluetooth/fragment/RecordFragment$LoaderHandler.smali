.class Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;
.super Landroid/os/Handler;
.source "RecordFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/RecordFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "LoaderHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/RecordFragment;Landroid/os/Looper;)V
    .locals 0

    .line 158
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    .line 159
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 164
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 165
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 167
    :try_start_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    iget p1, p1, Landroid/os/Message;->arg1:I

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->loadHistoryCallLog(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    const-string p1, "RecordFragment"

    const-string v0, "handleMessage: MSG_PB_SEARCH Exception"

    .line 169
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 170
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$100(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    :cond_0
    :goto_0
    return-void
.end method
