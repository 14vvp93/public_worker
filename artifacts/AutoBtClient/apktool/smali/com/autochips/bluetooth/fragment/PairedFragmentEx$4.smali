.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$4;
.super Ljava/lang/Object;
.source "PairedFragmentEx.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->buildDeviceProfileConfigDialog()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 480
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    .line 483
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$4;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    .line 484
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    return-void
.end method
