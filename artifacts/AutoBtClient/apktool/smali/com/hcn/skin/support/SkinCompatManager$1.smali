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

    .line 346
    iput-object p1, p0, Lcom/hcn/skin/support/SkinCompatManager$1;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    .line 349
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "com.hcn.skin.support.action.update"

    .line 350
    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    const-string p1, "KEY_SKIN_NAME"

    .line 351
    invoke-virtual {p2, p1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    .line 352
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "receive broadcast update skin:"

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    const-string v0, "SkinCompatManager"

    invoke-static {v0, p2}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 353
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-nez p2, :cond_0

    .line 354
    iget-object p2, p0, Lcom/hcn/skin/support/SkinCompatManager$1;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-virtual {p2, p1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkin(Ljava/lang/String;)Landroid/os/AsyncTask;

    :cond_0
    return-void
.end method
