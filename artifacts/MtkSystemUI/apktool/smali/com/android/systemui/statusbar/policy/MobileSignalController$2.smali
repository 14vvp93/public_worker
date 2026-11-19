.class Lcom/android/systemui/statusbar/policy/MobileSignalController$2;
.super Lcom/mediatek/ims/MtkImsConnectionStateListener;
.source "MobileSignalController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/policy/MobileSignalController;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/policy/MobileSignalController;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/policy/MobileSignalController;

    .line 193
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-direct {p0}, Lcom/mediatek/ims/MtkImsConnectionStateListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onCapabilitiesStatusChanged(Landroid/telephony/ims/feature/ImsFeature$Capabilities;)V
    .locals 2
    .param p1, "config"    # Landroid/telephony/ims/feature/ImsFeature$Capabilities;

    .line 227
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$300(Lcom/android/systemui/statusbar/policy/MobileSignalController;)Landroid/os/Handler;

    move-result-object v0

    new-instance v1, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;

    invoke-direct {v1, p0, p1}, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;-><init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;Landroid/telephony/ims/feature/ImsFeature$Capabilities;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 237
    return-void
.end method

.method public onImsConnected(I)V
    .locals 2
    .param p1, "imsRadioTech"    # I

    .line 197
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$300(Lcom/android/systemui/statusbar/policy/MobileSignalController;)Landroid/os/Handler;

    move-result-object v0

    new-instance v1, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;

    invoke-direct {v1, p0, p1}, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;-><init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;I)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 208
    return-void
.end method

.method public onImsDisconnected(Landroid/telephony/ims/ImsReasonInfo;)V
    .locals 2
    .param p1, "info"    # Landroid/telephony/ims/ImsReasonInfo;

    .line 212
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$300(Lcom/android/systemui/statusbar/policy/MobileSignalController;)Landroid/os/Handler;

    move-result-object v0

    new-instance v1, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;

    invoke-direct {v1, p0}, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;-><init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 223
    return-void
.end method

.method public onWifiPdnOOSStateChanged(I)V
    .locals 2
    .param p1, "oosState"    # I

    .line 244
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$300(Lcom/android/systemui/statusbar/policy/MobileSignalController;)Landroid/os/Handler;

    move-result-object v0

    new-instance v1, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$4;

    invoke-direct {v1, p0, p1}, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$4;-><init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;I)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 259
    return-void
.end method
