.class Lcom/android/systemui/qs/tiles/RebootTile$1;
.super Ljava/lang/Object;
.source "RebootTile.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/qs/tiles/RebootTile;->handleClick()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/qs/tiles/RebootTile;


# direct methods
.method constructor <init>(Lcom/android/systemui/qs/tiles/RebootTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/RebootTile;

    .line 63
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/RebootTile$1;->this$0:Lcom/android/systemui/qs/tiles/RebootTile;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .param p1, "dialog"    # Landroid/content/DialogInterface;
    .param p2, "which"    # I

    .line 66
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 67
    invoke-static {}, Landroid/app/ActivityManager;->isUserAMonkey()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 68
    return-void

    .line 71
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/RebootTile$1;->this$0:Lcom/android/systemui/qs/tiles/RebootTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/RebootTile;->access$000(Lcom/android/systemui/qs/tiles/RebootTile;)Landroid/content/Context;

    move-result-object v0

    const-string v1, "audio"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    .line 72
    .local v0, "audioManager":Landroid/media/AudioManager;
    const/4 v1, 0x3

    const/16 v2, -0x64

    const/4 v3, 0x0

    invoke-virtual {v0, v1, v2, v3}, Landroid/media/AudioManager;->adjustStreamVolume(III)V

    .line 73
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/RebootTile$1;->this$0:Lcom/android/systemui/qs/tiles/RebootTile;

    invoke-static {v1}, Lcom/android/systemui/qs/tiles/RebootTile;->access$100(Lcom/android/systemui/qs/tiles/RebootTile;)Landroid/content/Context;

    move-result-object v1

    const-string v2, "power"

    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/PowerManager;

    .line 74
    .local v1, "pManager":Landroid/os/PowerManager;
    const-string v2, "Reboot"

    invoke-virtual {v1, v2}, Landroid/os/PowerManager;->reboot(Ljava/lang/String;)V

    .line 75
    return-void
.end method
