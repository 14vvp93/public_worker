.class Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;
.super Landroid/os/Handler;
.source "NavigationBarFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/NavigationBarFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;Landroid/os/Looper;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/phone/NavigationBarFragment;
    .param p2, "x0"    # Landroid/os/Looper;

    .line 2118
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 5
    .param p1, "msg"    # Landroid/os/Message;

    .line 2121
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 2122
    iget v0, p1, Landroid/os/Message;->what:I

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_1

    .line 2131
    :pswitch_0
    const-string v0, "NavigationBar"

    const-string v1, "MSG_CHECK_ACTIVE_STREAM..."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2132
    const/4 v0, 0x0

    .line 2133
    .local v0, "isStreamChange":Z
    const/4 v1, 0x0

    invoke-static {v1, v1}, Landroid/media/AudioSystem;->isStreamActive(II)Z

    move-result v2

    const/4 v3, 0x2

    if-eqz v2, :cond_0

    .line 2134
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v2

    if-eqz v2, :cond_2

    .line 2135
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v2, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$802(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;I)I

    .line 2136
    const/4 v0, 0x1

    goto :goto_0

    .line 2138
    :cond_0
    invoke-static {v3, v1}, Landroid/media/AudioSystem;->isStreamActive(II)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 2139
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v1

    if-eq v1, v3, :cond_2

    .line 2140
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v1, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$802(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;I)I

    .line 2141
    const/4 v0, 0x1

    goto :goto_0

    .line 2144
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v1

    const/4 v2, 0x3

    if-eq v1, v2, :cond_2

    .line 2145
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$802(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;I)I

    .line 2146
    const/4 v0, 0x1

    .line 2149
    :cond_2
    :goto_0
    if-eqz v0, :cond_3

    .line 2150
    const/4 v1, 0x1

    invoke-virtual {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->removeMessages(I)V

    .line 2151
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$900(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)Landroid/media/AudioManager;

    move-result-object v2

    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v4}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v2

    .line 2152
    .local v2, "volume_index":I
    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v4}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v4

    invoke-virtual {p0, v1, v4, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->obtainMessage(III)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->sendMessage(Landroid/os/Message;)Z

    .line 2154
    .end local v2    # "volume_index":I
    :cond_3
    const-wide/16 v1, 0x4b0

    invoke-virtual {p0, v3, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->sendEmptyMessageDelayed(IJ)Z

    .line 2155
    goto :goto_1

    .line 2124
    .end local v0    # "isStreamChange":Z
    :pswitch_1
    const-string v0, "NavigationBar"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "MSG_VOL_CHANGED activeStream="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p1, Landroid/os/Message;->arg1:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " vol="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p1, Landroid/os/Message;->arg2:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2125
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iget-object v0, v0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->mNavigationBarView:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    if-eqz v0, :cond_4

    .line 2126
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iget-object v0, v0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->mNavigationBarView:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    iget v1, p1, Landroid/os/Message;->arg1:I

    iget v2, p1, Landroid/os/Message;->arg2:I

    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    .line 2127
    invoke-static {v3}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$900(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)Landroid/media/AudioManager;

    move-result-object v3

    iget v4, p1, Landroid/os/Message;->arg1:I

    invoke-virtual {v3, v4}, Landroid/media/AudioManager;->isStreamMute(I)Z

    move-result v3

    .line 2126
    invoke-virtual {v0, v1, v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->onVolumeChanged(IIZ)V

    .line 2159
    :cond_4
    :goto_1
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
