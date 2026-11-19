.class Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;
.super Ljava/lang/Object;
.source "VolButton.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/VolButton;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "LongClickThread"
.end annotation


# instance fields
.field private num:I

.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/VolButton;


# direct methods
.method private constructor <init>(Lcom/android/systemui/statusbar/phone/VolButton;)V
    .locals 0

    .line 43
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->this$0:Lcom/android/systemui/statusbar/phone/VolButton;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/android/systemui/statusbar/phone/VolButton;Lcom/android/systemui/statusbar/phone/VolButton$1;)V
    .locals 0
    .param p1, "x0"    # Lcom/android/systemui/statusbar/phone/VolButton;
    .param p2, "x1"    # Lcom/android/systemui/statusbar/phone/VolButton$1;

    .line 43
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;-><init>(Lcom/android/systemui/statusbar/phone/VolButton;)V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .line 47
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->this$0:Lcom/android/systemui/statusbar/phone/VolButton;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/VolButton;->isPressed()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 48
    iget v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->num:I

    const/4 v1, 0x1

    add-int/2addr v0, v1

    iput v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->num:I

    .line 49
    iget v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->num:I

    rem-int/lit8 v0, v0, 0x5

    if-nez v0, :cond_0

    .line 50
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->this$0:Lcom/android/systemui/statusbar/phone/VolButton;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/VolButton;->access$100(Lcom/android/systemui/statusbar/phone/VolButton;)Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;->sendEmptyMessage(I)Z

    .line 52
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;->this$0:Lcom/android/systemui/statusbar/phone/VolButton;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/VolButton;->access$200(Lcom/android/systemui/statusbar/phone/VolButton;)J

    move-result-wide v0

    const-wide/16 v2, 0x5

    div-long/2addr v0, v2

    invoke-static {v0, v1}, Landroid/os/SystemClock;->sleep(J)V

    goto :goto_0

    .line 54
    :cond_1
    return-void
.end method
