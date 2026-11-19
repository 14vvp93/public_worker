.class public final Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;
.super Ljava/lang/Object;
.source "PairedFragmentEx.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x11
    name = "ViewHolder"
.end annotation


# instance fields
.field public convertView:Landroid/view/View;

.field public nameTextView:Landroid/widget/TextView;

.field public profileConfigImageButton:Landroid/widget/Button;

.field public progressBar:Landroid/widget/ProgressBar;

.field public statusTextView:Landroid/widget/TextView;

.field final synthetic this$1:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;Landroid/view/View;)V
    .locals 0

    .line 891
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->this$1:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 892
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->convertView:Landroid/view/View;

    if-eqz p2, :cond_0

    const p1, 0x7f0800df

    .line 894
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    .line 895
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->convertView:Landroid/view/View;

    const p2, 0x7f0800dd

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p2

    invoke-virtual {p1, p2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    .line 896
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->convertView:Landroid/view/View;

    const p2, 0x7f080076

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p2

    invoke-virtual {p1, p2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    .line 897
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->convertView:Landroid/view/View;

    const p2, 0x7f080053

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p2

    invoke-virtual {p1, p2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ProgressBar;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->progressBar:Landroid/widget/ProgressBar;

    :cond_0
    return-void
.end method
