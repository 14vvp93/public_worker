.class Lcom/autochips/bluetooth/fragment/PairedFragment$4;
.super Ljava/lang/Object;
.source "PairedFragment.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;->buildDeviceProfileConfigDialog()V
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

    .line 839
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    .line 842
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$1100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    .line 843
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    return-void
.end method
