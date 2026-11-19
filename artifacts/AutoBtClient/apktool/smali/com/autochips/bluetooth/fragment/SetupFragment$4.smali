.class Lcom/autochips/bluetooth/fragment/SetupFragment$4;
.super Ljava/lang/Object;
.source "SetupFragment.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/SetupFragment;->showRebootDialog()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V
    .locals 0

    .line 573
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    .line 576
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const-string p2, "power"

    invoke-virtual {p1, p2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/os/PowerManager;

    if-eqz p1, :cond_0

    const-string p2, ""

    .line 578
    invoke-virtual {p1, p2}, Landroid/os/PowerManager;->reboot(Ljava/lang/String;)V

    :cond_0
    return-void
.end method
