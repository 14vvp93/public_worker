.class Lcom/hcn/skin/support/SkinCompatManager$1;
.super Landroid/content/BroadcastReceiver;
.source "SkinCompatManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/skin/support/SkinCompatManager;->registerSkinUpdateReceiver()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/skin/support/SkinCompatManager;


# direct methods
.method constructor <init>(Lcom/hcn/skin/support/SkinCompatManager;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/skin/support/SkinCompatManager;

    .line 329
    iput-object p1, p0, Lcom/hcn/skin/support/SkinCompatManager$1;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 332
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 333
    .local v0, "action":Ljava/lang/String;
    const-string v1, "com.hcn.skin.support.action.update"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 334
    const-string v1, "KEY_SKIN_NAME"

    invoke-virtual {p2, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 335
    .local v1, "name":Ljava/lang/String;
    const-string v2, "SkinCompatManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "receive broadcast update skin:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 336
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 337
    iget-object v2, p0, Lcom/hcn/skin/support/SkinCompatManager$1;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-virtual {v2, v1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkin(Ljava/lang/String;)Landroid/os/AsyncTask;

    .line 340
    .end local v1    # "name":Ljava/lang/String;
    :cond_0
    return-void
.end method
