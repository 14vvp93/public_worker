.class Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$2;
.super Landroid/telephony/PhoneStateListener;
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

    .line 251
    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$2;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    invoke-direct {p0}, Landroid/telephony/PhoneStateListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onCallStateChanged(ILjava/lang/String;)V
    .locals 3
    .param p1, "state"    # I
    .param p2, "incomingNumber"    # Ljava/lang/String;

    .line 255
    const-string v0, "ApnSettingsTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onCallStateChanged call state is "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 257
    if-eqz p1, :cond_0

    goto :goto_0

    .line 259
    :cond_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$2;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->access$000(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V

    .line 260
    nop

    .line 264
    :goto_0
    return-void
.end method
