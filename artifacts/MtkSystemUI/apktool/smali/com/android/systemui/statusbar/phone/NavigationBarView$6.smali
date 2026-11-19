.class Lcom/android/systemui/statusbar/phone/NavigationBarView$6;
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

    .line 1610
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .param p1, "view"    # Landroid/view/View;

    .line 1613
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView$6;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarView;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->access$700(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 1614
    :cond_0
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x14f

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 1615
    return-void
.end method
