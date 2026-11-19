.class Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;
.super Ljava/lang/Object;
.source "MobileSignalController.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->onImsDisconnected(Landroid/telephony/ims/ImsReasonInfo;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;)V
    .locals 0
    .param p1, "this$1"    # Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    .line 212
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 215
    sget-boolean v0, Lcom/android/systemui/statusbar/policy/SignalController;->DEBUG:Z

    if-eqz v0, :cond_0

    .line 216
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController;->mTag:Ljava/lang/String;

    const-string v1, "onImsDisconnected STATE_OUT_OF_SERVICE"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 218
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController;->mCurrentState:Lcom/android/systemui/statusbar/policy/SignalController$State;

    check-cast v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$MobileState;

    const/4 v1, 0x1

    iput v1, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$MobileState;->imsRegState:I

    .line 219
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController;->mCurrentState:Lcom/android/systemui/statusbar/policy/SignalController$State;

    check-cast v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$MobileState;

    const/4 v1, -0x1

    iput v1, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$MobileState;->imsCap:I

    .line 220
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$2;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$000(Lcom/android/systemui/statusbar/policy/MobileSignalController;)V

    .line 221
    return-void
.end method
