.class Lcom/autochips/bluetooth/fragment/PBFragment$2;
.super Landroid/content/BroadcastReceiver;
.source "PBFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 472
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    .line 475
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 476
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    const/4 p1, 0x0

    const-string v0, "android.bluetooth.profile.extra.STATE"

    .line 477
    invoke-virtual {p2, v0, p1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const/4 p2, 0x2

    if-ne p1, p2, :cond_0

    .line 480
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    const-string p2, ""

    invoke-static {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$200(Lcom/autochips/bluetooth/fragment/PBFragment;Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    if-nez p1, :cond_1

    .line 482
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->clearContactList()V

    :cond_1
    :goto_0
    return-void
.end method
