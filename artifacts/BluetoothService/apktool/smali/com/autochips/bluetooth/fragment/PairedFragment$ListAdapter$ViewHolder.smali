.class public final Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;
.super Ljava/lang/Object;
.source "PairedFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x11
    name = "ViewHolder"
.end annotation


# instance fields
.field public macaddTextView:Landroid/widget/TextView;

.field public nameTextView:Landroid/widget/TextView;

.field public profileConfigImageButton:Landroid/view/View;

.field public statusTextView:Landroid/widget/TextView;

.field final synthetic this$1:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;)V
    .locals 0

    .line 773
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->this$1:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
