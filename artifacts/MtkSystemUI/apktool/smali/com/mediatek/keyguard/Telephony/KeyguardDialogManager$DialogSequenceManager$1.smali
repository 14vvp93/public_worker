.class Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;
.super Landroid/database/ContentObserver;
.source "KeyguardDialogManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;


# direct methods
.method constructor <init>(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;Landroid/os/Handler;)V
    .locals 0
    .param p1, "this$1"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;
    .param p2, "x0"    # Landroid/os/Handler;

    .line 265
    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;->this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .locals 4
    .param p1, "selfChange"    # Z

    .line 268
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;->this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-static {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->access$200(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;)I

    move-result v0

    .line 270
    .local v0, "value":I
    const-string v1, "KeyguardDialogManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "DialogSequenceManager DialogSequenceObserver--onChange()--dialog_sequence_settings = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 273
    if-nez v0, :cond_0

    .line 274
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;->this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    const/4 v2, 0x0

    invoke-static {v1, v2}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->access$300(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;Z)V

    .line 275
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;->this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->handleShowDialog()V

    goto :goto_0

    .line 276
    :cond_0
    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    .line 277
    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;->this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-static {v2, v1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->access$300(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;Z)V

    .line 278
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;->this$1:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->handleShowDialog()V

    .line 280
    :cond_1
    :goto_0
    return-void
.end method
