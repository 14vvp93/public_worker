.class Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;
.super Ljava/lang/Object;
.source "MobileSignalController.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->onCapabilitiesStatusChanged(Landroid/telephony/ims/feature/ImsFeature$Capabilities;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

.field final synthetic val$config:Landroid/telephony/ims/feature/ImsFeature$Capabilities;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;Landroid/telephony/ims/feature/ImsFeature$Capabilities;)V
    .locals 0
    .param p1, "this$1"    # Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    .line 227
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iput-object p2, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->val$config:Landroid/telephony/ims/feature/ImsFeature$Capabilities;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 230
    sget-boolean v0, Lcom/android/systemui/statusbar/policy/SignalController;->DEBUG:Z

    if-eqz v0, :cond_0

    .line 231
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController;->mTag:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onCapabilitiesStatusChanged:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->val$config:Landroid/telephony/ims/feature/ImsFeature$Capabilities;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 233
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v1, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->val$config:Landroid/telephony/ims/feature/ImsFeature$Capabilities;

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$402(Lcom/android/systemui/statusbar/policy/MobileSignalController;Landroid/telephony/ims/feature/ImsFeature$Capabilities;)Landroid/telephony/ims/feature/ImsFeature$Capabilities;

    .line 234
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$3;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$200(Lcom/android/systemui/statusbar/policy/MobileSignalController;)V

    .line 235
    return-void
.end method
