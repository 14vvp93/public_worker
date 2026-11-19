.class Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;
.super Landroid/content/BroadcastReceiver;
.source "SetupFragmentEx.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/SetupFragmentEx;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V
    .locals 0

    .line 564
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    .line 567
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 568
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    const-string p1, "android.bluetooth.adapter.extra.STATE"

    const/16 v0, 0xa

    .line 569
    invoke-virtual {p2, p1, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const-string p2, "SetupFragmentEx"

    if-ne p1, v0, :cond_0

    const-string p1, "onReceive: ACTION_STATE_CHANGED STATE_OFF"

    .line 572
    invoke-static {p2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 573
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Landroid/widget/Button;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 574
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Landroid/widget/Button;

    move-result-object p1

    const p2, 0x7f07011d

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    :cond_0
    const/16 v0, 0xc

    if-ne p1, v0, :cond_1

    const-string p1, "onReceive: ACTION_STATE_CHANGED STATE_ON"

    .line 577
    invoke-static {p2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 578
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Landroid/widget/Button;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 579
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Landroid/widget/Button;

    move-result-object p1

    const p2, 0x7f07011e

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_1
    :goto_0
    return-void
.end method
