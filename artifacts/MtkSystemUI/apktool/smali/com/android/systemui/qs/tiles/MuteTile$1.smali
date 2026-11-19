.class Lcom/android/systemui/qs/tiles/MuteTile$1;
.super Landroid/content/BroadcastReceiver;
.source "MuteTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/MuteTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/qs/tiles/MuteTile;


# direct methods
.method constructor <init>(Lcom/android/systemui/qs/tiles/MuteTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/MuteTile;

    .line 93
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/MuteTile$1;->this$0:Lcom/android/systemui/qs/tiles/MuteTile;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 96
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 97
    .local v0, "action":Ljava/lang/String;
    const-string v1, "android.media.STREAM_MUTE_CHANGED_ACTION"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 98
    const-string v1, "android.media.EXTRA_VOLUME_STREAM_TYPE"

    const/4 v2, -0x1

    invoke-virtual {p2, v1, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 99
    .local v1, "streamType":I
    const-string v2, "android.media.EXTRA_STREAM_VOLUME_MUTED"

    const/4 v3, 0x0

    invoke-virtual {p2, v2, v3}, Landroid/content/Intent;->getBooleanExtra(Ljava/lang/String;Z)Z

    move-result v2

    .line 100
    .local v2, "volumeMuted":Z
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MuteTile$1;->this$0:Lcom/android/systemui/qs/tiles/MuteTile;

    invoke-static {v3}, Lcom/android/systemui/qs/tiles/MuteTile;->access$000(Lcom/android/systemui/qs/tiles/MuteTile;)Ljava/lang/String;

    move-result-object v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "onReceive streamType : "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", volumeMuted : "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 101
    const/4 v3, 0x3

    if-ne v1, v3, :cond_0

    .line 102
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MuteTile$1;->this$0:Lcom/android/systemui/qs/tiles/MuteTile;

    invoke-static {v2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/systemui/qs/tiles/MuteTile;->access$100(Lcom/android/systemui/qs/tiles/MuteTile;Ljava/lang/Object;)V

    .line 105
    .end local v1    # "streamType":I
    .end local v2    # "volumeMuted":Z
    :cond_0
    return-void
.end method
