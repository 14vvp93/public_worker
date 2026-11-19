.class Lcom/android/systemui/settings/VolumeController$5;
.super Lcom/android/systemui/settings/CurrentUserTracker;
.source "VolumeController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/settings/VolumeController;-><init>(Landroid/content/Context;Landroid/widget/ImageView;Lcom/android/systemui/settings/ToggleSlider;Landroid/widget/TextView;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/settings/VolumeController;


# direct methods
.method constructor <init>(Lcom/android/systemui/settings/VolumeController;Landroid/content/Context;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/settings/VolumeController;
    .param p2, "context"    # Landroid/content/Context;

    .line 135
    iput-object p1, p0, Lcom/android/systemui/settings/VolumeController$5;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-direct {p0, p2}, Lcom/android/systemui/settings/CurrentUserTracker;-><init>(Landroid/content/Context;)V

    return-void
.end method


# virtual methods
.method public onUserSwitched(I)V
    .locals 2
    .param p1, "newUserId"    # I

    .line 138
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController$5;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v0}, Lcom/android/systemui/settings/VolumeController;->access$800(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$5;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$100(Lcom/android/systemui/settings/VolumeController;)Ljava/lang/Runnable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 139
    return-void
.end method
