.class Lcom/autochips/bluetooth/fragment/PairedFragment$3;
.super Ljava/lang/Object;
.source "PairedFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;->updateConnectState(ILjava/lang/String;)V
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

    .line 582
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 585
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$900(Lcom/autochips/bluetooth/fragment/PairedFragment;)Z

    move-result p1

    if-nez p1, :cond_0

    .line 587
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    sget-object v0, Landroid/text/TextUtils$TruncateAt;->MARQUEE:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 588
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    const/4 v0, -0x1

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setMarqueeRepeatLimit(I)V

    .line 589
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setSelected(Z)V

    .line 590
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$902(Lcom/autochips/bluetooth/fragment/PairedFragment;Z)Z

    goto :goto_0

    .line 593
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 594
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setMarqueeRepeatLimit(I)V

    .line 595
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setSelected(Z)V

    .line 596
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$902(Lcom/autochips/bluetooth/fragment/PairedFragment;Z)Z

    :goto_0
    return-void
.end method
