.class Lcom/android/systemui/statusbar/phone/NavigationBarView$13;
.super Ljava/lang/Object;
.source "NavigationBarView.java"

# interfaces
.implements Landroid/widget/SeekBar$OnSeekBarChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/phone/NavigationBarView;->showVolumeDialog()V
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

    .line 1816
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onProgressChanged(Landroid/widget/SeekBar;IZ)V
    .locals 2
    .param p1, "seekBar"    # Landroid/widget/SeekBar;
    .param p2, "progress"    # I
    .param p3, "fromUser"    # Z

    .line 1819
    if-eqz p3, :cond_0

    .line 1820
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const/4 v1, 0x3

    invoke-virtual {v0, v1, p2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setStreamVolumeValue(II)V

    .line 1821
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1823
    :cond_0
    return-void
.end method

.method public onStartTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 2
    .param p1, "seekBar"    # Landroid/widget/SeekBar;

    .line 1827
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1302(Lcom/android/systemui/statusbar/phone/NavigationBarView;Z)Z

    .line 1828
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1400(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1829
    return-void
.end method

.method public onStopTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 2
    .param p1, "seekBar"    # Landroid/widget/SeekBar;

    .line 1833
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1302(Lcom/android/systemui/statusbar/phone/NavigationBarView;Z)Z

    .line 1834
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1500(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1835
    return-void
.end method
