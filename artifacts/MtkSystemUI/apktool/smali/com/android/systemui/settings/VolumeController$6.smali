.class Lcom/android/systemui/settings/VolumeController$6;
.super Landroid/content/BroadcastReceiver;
.source "VolumeController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/settings/VolumeController;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/settings/VolumeController;


# direct methods
.method constructor <init>(Lcom/android/systemui/settings/VolumeController;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/settings/VolumeController;

    .line 244
    iput-object p1, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 248
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 250
    .local v0, "action":Ljava/lang/String;
    const-string v1, "android.media.VOLUME_CHANGED_ACTION"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x3

    const/4 v3, -0x1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$900(Lcom/android/systemui/settings/VolumeController;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 251
    const-string v1, "android.media.EXTRA_VOLUME_STREAM_TYPE"

    invoke-virtual {p2, v1, v3}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 252
    .local v1, "streamType":I
    if-ne v1, v2, :cond_0

    .line 253
    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v2}, Lcom/android/systemui/settings/VolumeController;->access$800(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;

    move-result-object v2

    iget-object v3, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v3}, Lcom/android/systemui/settings/VolumeController;->access$100(Lcom/android/systemui/settings/VolumeController;)Ljava/lang/Runnable;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 255
    .end local v1    # "streamType":I
    :cond_0
    goto :goto_0

    :cond_1
    const-string v1, "android.media.STREAM_MUTE_CHANGED_ACTION"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$900(Lcom/android/systemui/settings/VolumeController;)Z

    move-result v1

    if-nez v1, :cond_3

    .line 256
    const-string v1, "android.media.EXTRA_VOLUME_STREAM_TYPE"

    invoke-virtual {p2, v1, v3}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 257
    .restart local v1    # "streamType":I
    if-ne v1, v2, :cond_2

    .line 258
    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v2}, Lcom/android/systemui/settings/VolumeController;->access$800(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;

    move-result-object v2

    iget-object v3, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v3}, Lcom/android/systemui/settings/VolumeController;->access$100(Lcom/android/systemui/settings/VolumeController;)Ljava/lang/Runnable;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 260
    .end local v1    # "streamType":I
    :cond_2
    goto :goto_0

    :cond_3
    const-string v1, "android.media.STREAM_DEVICES_CHANGED_ACTION"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$900(Lcom/android/systemui/settings/VolumeController;)Z

    move-result v1

    if-nez v1, :cond_4

    .line 261
    const-string v1, "android.media.EXTRA_VOLUME_STREAM_TYPE"

    invoke-virtual {p2, v1, v3}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 262
    .restart local v1    # "streamType":I
    if-ne v1, v2, :cond_4

    .line 263
    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v2}, Lcom/android/systemui/settings/VolumeController;->access$800(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;

    move-result-object v2

    iget-object v3, p0, Lcom/android/systemui/settings/VolumeController$6;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v3}, Lcom/android/systemui/settings/VolumeController;->access$100(Lcom/android/systemui/settings/VolumeController;)Ljava/lang/Runnable;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 266
    .end local v1    # "streamType":I
    :cond_4
    :goto_0
    return-void
.end method
