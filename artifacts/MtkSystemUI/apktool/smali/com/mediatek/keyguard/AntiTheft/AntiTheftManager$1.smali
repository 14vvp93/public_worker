.class Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$1;
.super Landroid/content/BroadcastReceiver;
.source "AntiTheftManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;


# direct methods
.method constructor <init>(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;)V
    .locals 0
    .param p1, "this$0"    # Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    .line 309
    iput-object p1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$1;->this$0:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 312
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 314
    .local v0, "action":Ljava/lang/String;
    const-string v1, "AntiTheftManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "handleAntiTheftViewUpdate() - action = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 317
    const-string v1, "com.mediatek.ppl.NOTIFY_LOCK"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x2

    if-eqz v1, :cond_1

    .line 318
    const-string v1, "AntiTheftManager"

    const-string v3, "receive PPL_LOCK"

    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 319
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isSystemEncrypted()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 320
    const-string v1, "AntiTheftManager"

    const-string v2, "Currently system needs to be decrypted. Not show PPL."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 321
    return-void

    .line 323
    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$1;->this$0:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->access$000(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;II)V

    goto :goto_0

    .line 324
    :cond_1
    const-string v1, "com.mediatek.ppl.NOTIFY_UNLOCK"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 325
    const-string v1, "AntiTheftManager"

    const-string v3, "receive PPL_UNLOCK"

    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 326
    iget-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$1;->this$0:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    const/4 v3, 0x1

    invoke-static {v1, v2, v3}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->access$000(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;II)V

    .line 329
    :cond_2
    :goto_0
    return-void
.end method
