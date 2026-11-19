.class Lcom/android/systemui/settings/VolumeController$3;
.super Ljava/lang/Object;
.source "VolumeController.java"

# interfaces
.implements Ljava/lang/Runnable;


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

    .line 83
    iput-object p1, p0, Lcom/android/systemui/settings/VolumeController$3;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .line 86
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController$3;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v0}, Lcom/android/systemui/settings/VolumeController;->access$300(Lcom/android/systemui/settings/VolumeController;)Landroid/media/AudioManager;

    move-result-object v0

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v0

    .line 87
    .local v0, "val":I
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$3;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$200(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;

    move-result-object v1

    const/4 v2, 0x1

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v0, v3}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    .line 88
    return-void
.end method
