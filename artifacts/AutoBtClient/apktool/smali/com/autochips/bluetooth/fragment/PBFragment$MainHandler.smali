.class Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;
.super Landroid/os/Handler;
.source "PBFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "MainHandler"
.end annotation


# instance fields
.field private mWeakReferencePBFragment:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/autochips/bluetooth/fragment/PBFragment;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 1

    .line 121
    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p0, v0}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 122
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->mWeakReferencePBFragment:Ljava/lang/ref/WeakReference;

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 10

    .line 127
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->mWeakReferencePBFragment:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/autochips/bluetooth/fragment/PBFragment;

    if-eqz v0, :cond_5

    .line 129
    iget v1, p1, Landroid/os/Message;->what:I

    const/4 v2, 0x6

    packed-switch v1, :pswitch_data_0

    goto/16 :goto_0

    .line 175
    :pswitch_0
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$700(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    goto/16 :goto_0

    .line 152
    :pswitch_1
    iget-object v1, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    iget-object v2, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v2, Ljava/util/List;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 153
    iget-object v1, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    invoke-static {v0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$300(Lcom/autochips/bluetooth/fragment/PBFragment;I)V

    .line 154
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object v0

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->notifyDataSetChanged()V

    goto/16 :goto_0

    .line 157
    :pswitch_2
    iget-object v1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    if-eqz v1, :cond_0

    .line 158
    iget-object v1, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    iget-object v3, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v3, Ljava/util/List;

    invoke-virtual {v1, v3}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 160
    :cond_0
    iget-object v1, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    invoke-static {v0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$300(Lcom/autochips/bluetooth/fragment/PBFragment;I)V

    .line 162
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$500(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/TextView;

    move-result-object v1

    const/4 v3, 0x0

    const/4 v4, 0x1

    const v5, 0x7f0d0041

    if-eqz v1, :cond_1

    .line 163
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$500(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/TextView;

    move-result-object v1

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v6

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v7

    new-array v8, v4, [Ljava/lang/Object;

    iget-object v9, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v9}, Ljava/util/ArrayList;->size()I

    move-result v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v3

    invoke-static {v6, v7, v8}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v1, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 166
    :cond_1
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$600(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/EditText;

    move-result-object v1

    if-eqz v1, :cond_2

    .line 167
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$600(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/EditText;

    move-result-object v1

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v6

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v5

    new-array v4, v4, [Ljava/lang/Object;

    iget-object v7, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v4, v3

    invoke-static {v6, v5, v4}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 170
    :cond_2
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object v1

    invoke-virtual {v1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->notifyDataSetChanged()V

    const-wide/16 v3, 0x1f4

    .line 171
    invoke-virtual {p0, v2, v3, v4}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendEmptyMessageDelayed(IJ)Z

    .line 172
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "MSG_UI_LOAD_FINISH size="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v0, v0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PBFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 149
    :pswitch_3
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->clearContactList()V

    goto :goto_0

    .line 140
    :pswitch_4
    iget v1, p1, Landroid/os/Message;->arg1:I

    const/4 v3, 0x3

    if-eq v1, v3, :cond_3

    const/4 v3, 0x2

    if-ne v1, v3, :cond_4

    .line 143
    :cond_3
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f0d0087

    .line 144
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    .line 143
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    .line 146
    :cond_4
    invoke-virtual {p0, v2}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    :pswitch_5
    const-string v1, ""

    .line 137
    invoke-static {v0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$200(Lcom/autochips/bluetooth/fragment/PBFragment;Ljava/lang/String;)V

    goto :goto_0

    .line 132
    :pswitch_6
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$000(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    .line 133
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$100(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    .line 134
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->clearContactList()V

    .line 181
    :cond_5
    :goto_0
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
