.class Lcom/autochips/bluetooth/fragment/RecordFragment$1;
.super Landroid/content/BroadcastReceiver;
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

    .line 355
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    .line 359
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 360
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_2

    const/4 p1, 0x0

    const-string v0, "android.bluetooth.profile.extra.STATE"

    .line 362
    invoke-virtual {p2, v0, p1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const/4 p2, 0x2

    if-ne p1, p2, :cond_1

    .line 365
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    const p2, 0x7f080081

    invoke-static {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$502(Lcom/autochips/bluetooth/fragment/RecordFragment;I)I

    .line 366
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$600(Lcom/autochips/bluetooth/fragment/RecordFragment;)Landroid/widget/RadioButton;

    move-result-object p1

    if-eqz p1, :cond_0

    .line 367
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$600(Lcom/autochips/bluetooth/fragment/RecordFragment;)Landroid/widget/RadioButton;

    move-result-object p1

    const/4 p2, 0x1

    invoke-virtual {p1, p2}, Landroid/widget/RadioButton;->setChecked(Z)V

    .line 369
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    const/4 p2, 0x3

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/RecordFragment;->startLoadHistoryCallLog(I)V

    goto :goto_0

    :cond_1
    if-nez p1, :cond_2

    .line 371
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->stopLoadHistoryCallLog()V

    .line 372
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    :cond_2
    :goto_0
    return-void
.end method
