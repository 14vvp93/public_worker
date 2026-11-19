.class Lcom/android/systemui/statusbar/phone/NavigationBarView$14;
.super Ljava/lang/Object;
.source "NavigationBarView.java"

# interfaces
.implements Landroid/content/DialogInterface$OnDismissListener;


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

    .line 1839
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$14;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss(Landroid/content/DialogInterface;)V
    .locals 2
    .param p1, "dialog"    # Landroid/content/DialogInterface;

    .line 1842
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$14;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1400(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1843
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$14;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1602(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;

    .line 1844
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$14;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1702(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/widget/SeekBar;)Landroid/widget/SeekBar;

    .line 1845
    return-void
.end method
