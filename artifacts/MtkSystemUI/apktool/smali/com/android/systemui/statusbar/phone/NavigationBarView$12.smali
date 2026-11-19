.class Lcom/android/systemui/statusbar/phone/NavigationBarView$12;
.super Ljava/lang/Object;
.source "NavigationBarView.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


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

    .line 1717
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$12;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .param p1, "view"    # Landroid/view/View;

    .line 1720
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$12;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$700(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 1721
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$12;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    const-string v1, "com.autochips.bluetooth"

    const-string v2, "com.autochips.bluetooth.MainBluetoothActivity"

    invoke-static {v0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$1100(Lcom/android/systemui/statusbar/phone/NavigationBarView;Ljava/lang/String;Ljava/lang/String;)V

    .line 1722
    return-void
.end method
