.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$1;
.super Ljava/lang/Object;
.source "PairedFragmentEx.java"

# interfaces
.implements Landroid/widget/PopupWindow$OnDismissListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->showPairedOperationDialog()V
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

    .line 291
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss()V
    .locals 2

    .line 294
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/IFragmentCallback;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 295
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$1;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/IFragmentCallback;

    move-result-object v0

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    :cond_0
    return-void
.end method
