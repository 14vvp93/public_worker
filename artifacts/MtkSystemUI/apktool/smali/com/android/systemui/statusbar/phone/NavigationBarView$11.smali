.class Lcom/android/systemui/statusbar/phone/NavigationBarView$11;
.super Ljava/lang/Object;
.source "NavigationBarView.java"

# interfaces
.implements Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/phone/NavigationBarView;->initPortView()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 1693
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public repeatAction()V
    .locals 5

    .line 1696
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$700(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 1697
    :cond_0
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "repeatAction reduce volume"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1698
    const-string v0, "p45"

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    iget-object v1, v1, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 1700
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$900(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1702
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    iget-object v0, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v0

    .line 1703
    .local v0, "currentVolume":I
    if-lez v0, :cond_1

    .line 1704
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    iget-object v2, v2, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    add-int/lit8 v3, v0, -0x1

    const/4 v4, 0x0

    invoke-virtual {v2, v1, v3, v4}, Landroid/media/AudioManager;->setStreamVolume(III)V

    .line 1705
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1000(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1707
    .end local v0    # "currentVolume":I
    :cond_1
    goto :goto_0

    .line 1708
    :cond_2
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x19

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 1710
    :goto_0
    return-void
.end method
