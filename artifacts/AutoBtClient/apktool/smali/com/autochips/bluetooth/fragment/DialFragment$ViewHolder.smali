.class Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;
.super Ljava/lang/Object;
.source "DialFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/DialFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ViewHolder"
.end annotation


# instance fields
.field public mContactName:Landroid/widget/TextView;

.field public mContactNum:Landroid/widget/TextView;

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/DialFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/widget/TextView;Landroid/widget/TextView;)V
    .locals 0

    .line 619
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 621
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;->mContactName:Landroid/widget/TextView;

    .line 622
    iput-object p3, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;->mContactNum:Landroid/widget/TextView;

    return-void
.end method
