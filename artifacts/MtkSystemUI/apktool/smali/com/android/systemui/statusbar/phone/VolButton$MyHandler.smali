.class Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;
.super Landroid/os/Handler;
.source "VolButton.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/VolButton;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "MyHandler"
.end annotation


# instance fields
.field private ref:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/systemui/statusbar/phone/VolButton;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/phone/VolButton;)V
    .locals 1
    .param p1, "button"    # Lcom/android/systemui/statusbar/phone/VolButton;

    .line 61
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    .line 62
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;->ref:Ljava/lang/ref/WeakReference;

    .line 63
    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .param p1, "msg"    # Landroid/os/Message;

    .line 66
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 67
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;->ref:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/VolButton;

    .line 68
    .local v0, "button":Lcom/android/systemui/statusbar/phone/VolButton;
    if-eqz v0, :cond_0

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/VolButton;->access$300(Lcom/android/systemui/statusbar/phone/VolButton;)Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 69
    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/VolButton;->access$300(Lcom/android/systemui/statusbar/phone/VolButton;)Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;

    move-result-object v1

    invoke-interface {v1}, Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;->repeatAction()V

    .line 71
    :cond_0
    return-void
.end method
