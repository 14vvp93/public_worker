.class Lcom/autochips/bluetooth/fragment/SetupFragmentEx$3;
.super Ljava/lang/Object;
.source "SetupFragmentEx.java"

# interfaces
.implements Landroid/widget/PopupWindow$OnDismissListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->showEditPinDialog()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V
    .locals 0

    .line 496
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss()V
    .locals 2

    .line 499
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/autochips/bluetooth/IFragmentCallback;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 500
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/autochips/bluetooth/IFragmentCallback;

    move-result-object v0

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    :cond_0
    return-void
.end method
