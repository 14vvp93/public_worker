.class Lcom/android/systemui/statusbar/phone/NavigationBarView$19;
.super Landroid/content/BroadcastReceiver;
.source "NavigationBarView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/NavigationBarView;
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

    .line 3568
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 8
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 3572
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 3574
    .local v0, "action":Ljava/lang/String;
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const v3, 0x7f0a029f

    invoke-static {v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2000(Lcom/android/systemui/statusbar/phone/NavigationBarView;I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageButton;

    invoke-static {v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$102(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/widget/ImageButton;)Landroid/widget/ImageButton;

    .line 3575
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const v3, 0x7f0a029e

    invoke-static {v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2000(Lcom/android/systemui/statusbar/phone/NavigationBarView;I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageButton;

    invoke-static {v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$202(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/widget/ImageButton;)Landroid/widget/ImageButton;

    .line 3576
    const-string v1, "persist.sys.skin"

    const-string v2, "com.hcn.auto.theme.p1"

    invoke-static {v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 3577
    .local v1, "currentSkinName":Ljava/lang/String;
    const-string v2, "com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    const-wide/16 v3, 0xbb8

    const/16 v5, 0x8

    const/4 v6, 0x0

    if-eqz v2, :cond_2

    .line 3579
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    if-eqz v2, :cond_0

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v6}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 3580
    :cond_0
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v5}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 3581
    :cond_1
    const-string v2, "p38"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 3582
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/os/Handler;

    move-result-object v2

    iget-object v7, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v7}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Ljava/lang/Runnable;

    move-result-object v7

    invoke-virtual {v2, v7}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 3583
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/os/Handler;

    move-result-object v2

    iget-object v7, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v7}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Ljava/lang/Runnable;

    move-result-object v7

    invoke-virtual {v2, v7, v3, v4}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 3586
    :cond_2
    const-string v2, "com.mediatek.systemui.KEYCODE_MEDIA_NEXT"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_5

    .line 3588
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    if-eqz v2, :cond_3

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v6}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 3589
    :cond_3
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    if-eqz v2, :cond_4

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v5}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 3590
    :cond_4
    const-string v2, "p38"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_5

    .line 3591
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/os/Handler;

    move-result-object v2

    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Ljava/lang/Runnable;

    move-result-object v5

    invoke-virtual {v2, v5}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 3592
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/os/Handler;

    move-result-object v2

    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$2100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Ljava/lang/Runnable;

    move-result-object v5

    invoke-virtual {v2, v5, v3, v4}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 3596
    :cond_5
    return-void
.end method
