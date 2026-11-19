.class Lcom/android/systemui/volume/JellyProgressView$1;
.super Landroid/content/BroadcastReceiver;
.source "JellyProgressView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/volume/JellyProgressView;->registerVolumeChangedReceiver()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/volume/JellyProgressView;


# direct methods
.method constructor <init>(Lcom/android/systemui/volume/JellyProgressView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/volume/JellyProgressView;

    .line 113
    iput-object p1, p0, Lcom/android/systemui/volume/JellyProgressView$1;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 116
    const-string v0, "android.media.EXTRA_VOLUME_STREAM_TYPE"

    const/4 v1, -0x1

    invoke-virtual {p2, v0, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v0

    .line 117
    .local v0, "streamType":I
    const-string v2, "android.media.EXTRA_VOLUME_STREAM_VALUE"

    invoke-virtual {p2, v2, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 118
    .local v1, "streamValue":I
    const-string v2, "JellyProgressView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onReceive streamType : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", streamValue : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 121
    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView$1;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-static {v2}, Lcom/android/systemui/volume/JellyProgressView;->access$000(Lcom/android/systemui/volume/JellyProgressView;)I

    move-result v2

    if-ne v0, v2, :cond_0

    .line 123
    int-to-float v2, v1

    const/high16 v3, 0x42c80000    # 100.0f

    mul-float/2addr v2, v3

    iget-object v3, p0, Lcom/android/systemui/volume/JellyProgressView$1;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-static {v3}, Lcom/android/systemui/volume/JellyProgressView;->access$100(Lcom/android/systemui/volume/JellyProgressView;)I

    move-result v3

    int-to-float v3, v3

    div-float/2addr v2, v3

    .line 124
    .local v2, "newProgress":F
    iget-object v3, p0, Lcom/android/systemui/volume/JellyProgressView$1;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-virtual {v3, v2}, Lcom/android/systemui/volume/JellyProgressView;->setProgress(F)V

    .line 126
    .end local v2    # "newProgress":F
    :cond_0
    return-void
.end method
