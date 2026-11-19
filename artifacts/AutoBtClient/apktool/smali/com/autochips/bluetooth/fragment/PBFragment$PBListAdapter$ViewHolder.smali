.class public final Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;
.super Ljava/lang/Object;
.source "PBFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x11
    name = "ViewHolder"
.end annotation


# instance fields
.field public nameTextView:Landroid/widget/TextView;

.field public pathImageView:Landroid/widget/ImageView;

.field public phoneTextView:Landroid/widget/TextView;

.field final synthetic this$1:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)V
    .locals 0

    .line 803
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->this$1:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
