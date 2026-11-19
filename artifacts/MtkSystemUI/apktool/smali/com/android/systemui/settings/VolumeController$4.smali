.class Lcom/android/systemui/settings/VolumeController$4;
.super Landroid/os/Handler;
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

    .line 92
    iput-object p1, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 4
    .param p1, "msg"    # Landroid/os/Message;

    .line 95
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/systemui/settings/VolumeController;->access$402(Lcom/android/systemui/settings/VolumeController;Z)Z

    .line 97
    const/4 v0, 0x0

    :try_start_0
    iget v2, p1, Landroid/os/Message;->what:I

    packed-switch v2, :pswitch_data_0

    .line 114
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    goto :goto_1

    .line 111
    :pswitch_0
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$700(Lcom/android/systemui/settings/VolumeController;)Lcom/android/systemui/settings/ToggleSlider;

    move-result-object v1

    const/4 v2, 0x0

    invoke-interface {v1, v2}, Lcom/android/systemui/settings/ToggleSlider;->setOnChangedListener(Lcom/android/systemui/settings/ToggleSlider$Listener;)V

    .line 112
    goto :goto_1

    .line 108
    :pswitch_1
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$700(Lcom/android/systemui/settings/VolumeController;)Lcom/android/systemui/settings/ToggleSlider;

    move-result-object v1

    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-interface {v1, v2}, Lcom/android/systemui/settings/ToggleSlider;->setOnChangedListener(Lcom/android/systemui/settings/ToggleSlider$Listener;)V

    .line 109
    goto :goto_1

    .line 105
    :pswitch_2
    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v2}, Lcom/android/systemui/settings/VolumeController;->access$700(Lcom/android/systemui/settings/VolumeController;)Lcom/android/systemui/settings/ToggleSlider;

    move-result-object v2

    iget v3, p1, Landroid/os/Message;->arg1:I

    if-eqz v3, :cond_0

    goto :goto_0

    :cond_0
    move v1, v0

    :goto_0
    invoke-interface {v2, v1}, Lcom/android/systemui/settings/ToggleSlider;->setChecked(Z)V

    .line 106
    goto :goto_1

    .line 102
    :pswitch_3
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    iget v2, p1, Landroid/os/Message;->arg1:I

    invoke-static {v1, v2}, Lcom/android/systemui/settings/VolumeController;->access$600(Lcom/android/systemui/settings/VolumeController;I)V

    .line 103
    goto :goto_1

    .line 99
    :pswitch_4
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1}, Lcom/android/systemui/settings/VolumeController;->access$500(Lcom/android/systemui/settings/VolumeController;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 100
    nop

    .line 117
    :goto_1
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v1, v0}, Lcom/android/systemui/settings/VolumeController;->access$402(Lcom/android/systemui/settings/VolumeController;Z)Z

    .line 118
    nop

    .line 119
    return-void

    .line 117
    :catchall_0
    move-exception v1

    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController$4;->this$0:Lcom/android/systemui/settings/VolumeController;

    invoke-static {v2, v0}, Lcom/android/systemui/settings/VolumeController;->access$402(Lcom/android/systemui/settings/VolumeController;Z)Z

    throw v1

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
