.class Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$1;
.super Landroid/content/BroadcastReceiver;
.source "ApnSettingsTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;


# direct methods
.method constructor <init>(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    .line 227
    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$1;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 230
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 232
    .local v0, "action":Ljava/lang/String;
    const-string v1, "ApnSettingsTile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "onReceive(), action: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 235
    const-string v1, "android.intent.action.AIRPLANE_MODE"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 236
    const-string v1, "state"

    const/4 v2, 0x0

    invoke-virtual {p2, v1, v2}, Landroid/content/Intent;->getBooleanExtra(Ljava/lang/String;Z)Z

    move-result v1

    .line 237
    .local v1, "enabled":Z
    const-string v2, "ApnSettingsTile"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onReceive(), airline mode changed: state is "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 238
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$1;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    invoke-static {v2}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->access$000(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V

    .line 239
    .end local v1    # "enabled":Z
    goto :goto_0

    :cond_0
    const-string v1, "com.mediatek.phone.ACTION_EF_CSP_CONTENT_NOTIFY"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "com.mediatek.intent.action.MSIM_MODE"

    .line 240
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "mediatek.intent.action.ACTION_MD_TYPE_CHANGE"

    .line 241
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "mediatek.intent.action.LOCATED_PLMN_CHANGED"

    .line 242
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE"

    .line 243
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE"

    .line 244
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "android.intent.action.ACTION_SUBINFO_RECORD_UPDATED"

    .line 245
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 246
    :cond_1
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$1;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    invoke-static {v1}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->access$000(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V

    .line 248
    :cond_2
    :goto_0
    return-void
.end method
