.class Lcom/android/systemui/statusbar/phone/NavigationBarView$16;
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

    .line 1893
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$16;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 1896
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 1897
    .local v0, "action":Ljava/lang/String;
    const-string v1, "android.media.VOLUME_CHANGED_ACTION"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$16;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1300(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 1898
    const-string v1, "android.media.EXTRA_VOLUME_STREAM_TYPE"

    const/4 v2, -0x1

    invoke-virtual {p2, v1, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 1899
    .local v1, "streamType":I
    const/4 v2, 0x3

    if-ne v1, v2, :cond_0

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$16;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1700(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/SeekBar;

    move-result-object v2

    if-eqz v2, :cond_0

    .line 1900
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$16;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1000(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1903
    .end local v1    # "streamType":I
    :cond_0
    return-void
.end method
