.class Lcom/autochips/bluetooth/fragment/DialFragment$2;
.super Ljava/lang/Object;
.source "DialFragment.java"

# interfaces
.implements Landroid/text/TextWatcher;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/DialFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field mSearchKey:Ljava/lang/String;

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/DialFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V
    .locals 0

    .line 462
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string p1, ""

    .line 463
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->mSearchKey:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public afterTextChanged(Landroid/text/Editable;)V
    .locals 3

    .line 467
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    iget-object p1, p1, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {p1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "DialFragment"

    .line 468
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "afterTextChanged: search="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 469
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    const/4 v1, 0x2

    if-lt v0, v1, :cond_1

    .line 470
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->mSearchKey:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 471
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->mSearchKey:Ljava/lang/String;

    .line 472
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$100(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    .line 473
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$000(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    move-result-object p1

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->mSearchKey:Ljava/lang/String;

    const/4 v1, 0x4

    invoke-virtual {p1, v1, v0}, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    .line 474
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$000(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->removeMessages(I)V

    .line 475
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$000(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->sendMessage(Landroid/os/Message;)Z

    .line 476
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    monitor-enter p1

    .line 477
    :try_start_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$300(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/CancellationSignal;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 478
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$300(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/CancellationSignal;

    move-result-object v0

    invoke-virtual {v0}, Landroid/os/CancellationSignal;->cancel()V

    .line 480
    :cond_0
    monitor-exit p1

    goto :goto_0

    :catchall_0
    move-exception v0

    monitor-exit p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v0

    .line 483
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$100(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    :cond_2
    :goto_0
    return-void
.end method

.method public beforeTextChanged(Ljava/lang/CharSequence;III)V
    .locals 0

    return-void
.end method

.method public onTextChanged(Ljava/lang/CharSequence;III)V
    .locals 0

    return-void
.end method
