.class Lcom/autochips/bluetooth/fragment/DialFragment$3;
.super Landroid/os/Handler;
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

    .line 497
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 502
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 503
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-eq v0, v1, :cond_1

    const/4 v1, 0x2

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 509
    :cond_0
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;

    .line 510
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$400(Lcom/autochips/bluetooth/fragment/DialFragment;)Ljava/util/ArrayList;

    move-result-object v0

    iget-object v1, p1, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->pbList:Ljava/util/ArrayList;

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 511
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$500(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

    move-result-object v0

    iget-object p1, p1, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->searchKey:Ljava/lang/String;

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->setSearchText(Ljava/lang/String;)V

    .line 512
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$500(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->notifyDataSetChanged()V

    goto :goto_0

    .line 505
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$100(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    :goto_0
    return-void
.end method
