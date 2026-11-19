.class Lcom/autochips/bluetooth/fragment/PairedFragment$7;
.super Ljava/lang/Object;
.source "PairedFragment.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;->buildEditNameDialog()V
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

    .line 1048
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2

    .line 1051
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/EditText;

    move-result-object p2

    invoke-virtual {p2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p2

    .line 1052
    invoke-virtual {p2}, Ljava/lang/String;->length()I

    move-result v0

    const/16 v1, 0x14

    if-le v0, v1, :cond_0

    const/4 v0, 0x0

    const/16 v1, 0x13

    .line 1053
    invoke-virtual {p2, v0, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p2

    .line 1056
    :cond_0
    invoke-virtual {p2}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_3

    .line 1057
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 1058
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    invoke-virtual {v0, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBTName(Ljava/lang/String;)V

    .line 1060
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1500(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/view/View;

    move-result-object v0

    const v1, 0x7f08016b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    if-eqz v0, :cond_2

    .line 1062
    invoke-virtual {v0, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1064
    :cond_2
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    goto :goto_0

    .line 1066
    :cond_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$7;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const p2, 0x7f0d002e

    .line 1067
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p2

    .line 1066
    invoke-static {p1, p2}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :goto_0
    return-void
.end method
