.class Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;
.super Landroid/os/Handler;
.source "RecordFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/RecordFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "MainHandler"
.end annotation


# instance fields
.field private mWeakRefFragment:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/autochips/bluetooth/fragment/RecordFragment;",
            ">;"
        }
    .end annotation
.end field

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/RecordFragment;Lcom/autochips/bluetooth/fragment/RecordFragment;)V
    .locals 0

    .line 105
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    .line 106
    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object p1

    invoke-direct {p0, p1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 107
    new-instance p1, Ljava/lang/ref/WeakReference;

    invoke-direct {p1, p2}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->mWeakRefFragment:Ljava/lang/ref/WeakReference;

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 4

    .line 112
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->mWeakRefFragment:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/autochips/bluetooth/fragment/RecordFragment;

    if-eqz v0, :cond_9

    .line 114
    iget v1, p1, Landroid/os/Message;->what:I

    if-eqz v1, :cond_8

    const/4 v2, 0x1

    if-eq v1, v2, :cond_7

    const/4 v2, 0x3

    const/4 v3, 0x2

    if-eq v1, v3, :cond_4

    if-eq v1, v2, :cond_3

    const/4 v2, 0x4

    const-string v3, "RecordFragment"

    if-eq v1, v2, :cond_1

    const/4 v2, 0x5

    if-eq v1, v2, :cond_0

    goto/16 :goto_0

    .line 137
    :cond_0
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$200(Lcom/autochips/bluetooth/fragment/RecordFragment;)Ljava/util/ArrayList;

    move-result-object v1

    iget-object v2, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v2, Ljava/util/List;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 138
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$300(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    move-result-object v1

    invoke-virtual {v1}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->notifyDataSetChanged()V

    .line 139
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$200(Lcom/autochips/bluetooth/fragment/RecordFragment;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    invoke-static {v0, v1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$400(Lcom/autochips/bluetooth/fragment/RecordFragment;I)V

    const-string v0, "MSG_UI_HISTORY_ADD"

    .line 140
    invoke-static {v3, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 143
    :cond_1
    iget-object v1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    if-eqz v1, :cond_2

    .line 144
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$200(Lcom/autochips/bluetooth/fragment/RecordFragment;)Ljava/util/ArrayList;

    move-result-object v1

    iget-object v2, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v2, Ljava/util/List;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 146
    :cond_2
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$300(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    move-result-object v1

    invoke-virtual {v1}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->notifyDataSetChanged()V

    .line 147
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$100(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    .line 148
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "MSG_UI_LOAD_FINISH size="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$200(Lcom/autochips/bluetooth/fragment/RecordFragment;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 134
    :cond_3
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    goto :goto_0

    .line 125
    :cond_4
    iget v1, p1, Landroid/os/Message;->arg1:I

    if-eq v1, v2, :cond_5

    if-ne v1, v3, :cond_6

    .line 128
    :cond_5
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x7f0d0087

    .line 129
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v2

    .line 128
    invoke-static {v1, v2}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    .line 131
    :cond_6
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$100(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    goto :goto_0

    :cond_7
    const/4 v1, 0x0

    .line 122
    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->startLoadHistoryCallLog(I)V

    goto :goto_0

    .line 117
    :cond_8
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$000(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    .line 118
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->stopLoadHistoryCallLog()V

    .line 119
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    .line 153
    :cond_9
    :goto_0
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    return-void
.end method
