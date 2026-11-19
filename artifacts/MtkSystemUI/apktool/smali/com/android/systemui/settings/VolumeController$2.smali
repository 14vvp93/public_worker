.class Lcom/android/systemui/settings/VolumeController$2;
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

    .line 70
    iput-object p1, p0, Lcom/android/systemui/settings/VolumeController$2;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 73
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController$2;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v0}, Lcom/android/systemui/settings/VolumeController;->access$000(Lcom/android/systemui/settings/VolumeController;)Lcom/android/systemui/settings/CurrentUserTracker;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/systemui/settings/CurrentUserTracker;->stopTracking()V

    .line 75
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController$2;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v0}, Lcom/android/systemui/settings/VolumeController;->access$200(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;

    move-result-object v0

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 76
    return-void
.end method
