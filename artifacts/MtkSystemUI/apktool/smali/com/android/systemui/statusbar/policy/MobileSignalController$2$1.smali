.class Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;
.super Ljava/lang/Object;
.source "MobileSignalController.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->onImsConnected(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

.field final synthetic val$imsRadioTech:I


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/policy/MobileSignalController$2;I)V
    .locals 0
    .param p1, "this$1"    # Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    .line 197
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iput p2, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->val$imsRadioTech:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 200
    sget-boolean v0, Lcom/android/systemui/statusbar/policy/SignalController;->DEBUG:Z

    if-eqz v0, :cond_0

    .line 201
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController;->mTag:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onImsConnected STATE_IN_SERVICE, imsRadioTech = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->val$imsRadioTech:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 203
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget v1, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->val$imsRadioTech:I

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$102(Lcom/android/systemui/statusbar/policy/MobileSignalController;I)I

    .line 204
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController;->mCurrentState:Lcom/android/systemui/statusbar/policy/SignalController$State;

    check-cast v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$MobileState;

    const/4 v1, 0x0

    iput v1, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$MobileState;->imsRegState:I

    .line 205
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2$1;->this$1:Lcom/android/systemui/statusbar/policy/MobileSignalController$2;

    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/MobileSignalController$2;->this$0:Lcom/android/systemui/statusbar/policy/MobileSignalController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/policy/MobileSignalController;->access$200(Lcom/android/systemui/statusbar/policy/MobileSignalController;)V

    .line 206
    return-void
.end method
