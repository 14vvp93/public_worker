.class public Lcom/hcn/common/utils/HHandler;
.super Landroid/os/Handler;
.source "HHandler.java"


# direct methods
.method public constructor <init>(Landroid/os/Looper;)V
    .locals 0

    .line 22
    invoke-direct {p0, p1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public final postUniqueDelayed(Ljava/lang/Runnable;J)Z
    .locals 0

    .line 66
    invoke-virtual {p0, p1}, Lcom/hcn/common/utils/HHandler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 67
    invoke-virtual {p0, p1, p2, p3}, Lcom/hcn/common/utils/HHandler;->postDelayed(Ljava/lang/Runnable;J)Z

    move-result p1

    return p1
.end method

.method public final sendEmptyUniqueMessage(I)Z
    .locals 2

    const-wide/16 v0, 0x0

    .line 32
    invoke-virtual {p0, p1, v0, v1}, Lcom/hcn/common/utils/HHandler;->sendEmptyUniqueMessageDelayed(IJ)Z

    move-result p1

    return p1
.end method

.method public final sendEmptyUniqueMessageDelayed(IJ)Z
    .locals 5

    .line 45
    invoke-virtual {p0, p1}, Lcom/hcn/common/utils/HHandler;->hasMessages(I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 46
    invoke-virtual {p0, p1}, Lcom/hcn/common/utils/HHandler;->removeMessages(I)V

    .line 50
    :cond_0
    invoke-static {}, Landroid/os/Message;->obtain()Landroid/os/Message;

    move-result-object v0

    .line 51
    iput p1, v0, Landroid/os/Message;->what:I

    .line 53
    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v1

    const-wide/16 v3, 0x0

    cmp-long p1, p2, v3

    if-gez p1, :cond_1

    move-wide p2, v3

    :cond_1
    add-long/2addr v1, p2

    .line 52
    invoke-virtual {p0, v0, v1, v2}, Lcom/hcn/common/utils/HHandler;->sendMessageAtTime(Landroid/os/Message;J)Z

    move-result p1

    return p1
.end method
