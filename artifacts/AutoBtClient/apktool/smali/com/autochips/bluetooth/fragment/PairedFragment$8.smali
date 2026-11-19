.class Lcom/autochips/bluetooth/fragment/PairedFragment$8;
.super Ljava/lang/Object;
.source "PairedFragment.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;->buildEditPinDialog()V
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

    .line 1098
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1

    .line 1101
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1600(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/EditText;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    .line 1103
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result p2

    const/4 v0, 0x4

    if-lt p2, v0, :cond_1

    .line 1104
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result p2

    const/16 v0, 0x10

    if-gt p2, v0, :cond_1

    .line 1105
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1500(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/view/View;

    move-result-object p2

    const v0, 0x7f08016c

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p2

    check-cast p2, Landroid/widget/TextView;

    if-eqz p2, :cond_0

    .line 1107
    invoke-virtual {p2, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1109
    :cond_0
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    iput-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPinCode:Ljava/lang/String;

    .line 1110
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/app/Dialog;

    move-result-object p1

    invoke-virtual {p1}, Landroid/app/Dialog;->dismiss()V

    .line 1111
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    if-eqz p1, :cond_2

    .line 1112
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    iget-object p2, p2, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPinCode:Ljava/lang/String;

    invoke-virtual {p1, p2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBTPincode(Ljava/lang/String;)V

    goto :goto_0

    .line 1115
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$8;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const p2, 0x7f0d002e

    .line 1116
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p2

    .line 1115
    invoke-static {p1, p2}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :cond_2
    :goto_0
    return-void
.end method
