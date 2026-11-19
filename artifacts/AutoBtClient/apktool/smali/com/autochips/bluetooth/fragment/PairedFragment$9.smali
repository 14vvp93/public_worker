.class Lcom/autochips/bluetooth/fragment/PairedFragment$9;
.super Landroid/content/BroadcastReceiver;
.source "PairedFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 1123
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$9;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    .line 1126
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 1127
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    const-string p1, "android.bluetooth.adapter.extra.STATE"

    const/16 v0, 0xa

    .line 1128
    invoke-virtual {p2, p1, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const-string p2, "ParedFragment"

    if-ne p1, v0, :cond_0

    const-string p1, "onReceive: ACTION_STATE_CHANGED STATE_OFF"

    .line 1131
    invoke-static {p2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1132
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$9;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1800(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 1133
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$9;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1800(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    const p2, 0x7f07011d

    .line 1134
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    .line 1133
    invoke-virtual {p1, p2}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    :cond_0
    const/16 v0, 0xc

    if-ne p1, v0, :cond_1

    const-string p1, "onReceive: ACTION_STATE_CHANGED STATE_ON"

    .line 1137
    invoke-static {p2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1138
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$9;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1800(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 1139
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$9;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1800(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    const p2, 0x7f07011e

    .line 1140
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    .line 1139
    invoke-virtual {p1, p2}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_1
    :goto_0
    return-void
.end method
