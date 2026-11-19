.class public Lcom/hcn/auto/systemui/netspeed/NetSpeedView;
.super Landroid/widget/TextView;
.source "NetSpeedView.java"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/DarkIconDispatcher$DarkReceiver;


# static fields
.field private static final TAG:Ljava/lang/String;


# instance fields
.field private isVisibility:Z

.field private mAttached:Z

.field private mContext:Landroid/content/Context;

.field private final mNetSpeedSettingsObserver:Landroid/database/ContentObserver;

.field private mThread:Ljava/lang/Thread;

.field private mUid:I

.field private mainHandler:Landroid/os/Handler;

.field private threadRunnable:Ljava/lang/Runnable;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 20
    const-class v0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 32
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 33
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 36
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 37
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyle"    # I

    .line 40
    invoke-direct {p0, p1, p2, p3}, Landroid/widget/TextView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 83
    new-instance v0, Landroid/os/Handler;

    new-instance v1, Lcom/hcn/auto/systemui/netspeed/-$$Lambda$NetSpeedView$dX8WopmoYcPlockFNrMYuvEo4Is;

    invoke-direct {v1, p0}, Lcom/hcn/auto/systemui/netspeed/-$$Lambda$NetSpeedView$dX8WopmoYcPlockFNrMYuvEo4Is;-><init>(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)V

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Handler$Callback;)V

    iput-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mainHandler:Landroid/os/Handler;

    .line 89
    new-instance v0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView$1;

    iget-object v1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mainHandler:Landroid/os/Handler;

    invoke-direct {v0, p0, v1}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView$1;-><init>(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;Landroid/os/Handler;)V

    iput-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mNetSpeedSettingsObserver:Landroid/database/ContentObserver;

    .line 102
    new-instance v0, Lcom/hcn/auto/systemui/netspeed/-$$Lambda$NetSpeedView$c5Syh5kx3_OmDUY-zHy8mEbR99c;

    invoke-direct {v0, p0}, Lcom/hcn/auto/systemui/netspeed/-$$Lambda$NetSpeedView$c5Syh5kx3_OmDUY-zHy8mEbR99c;-><init>(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)V

    iput-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->threadRunnable:Ljava/lang/Runnable;

    .line 41
    iput-object p1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mContext:Landroid/content/Context;

    .line 42
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationInfo()Landroid/content/pm/ApplicationInfo;

    move-result-object v0

    iget v0, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    iput v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mUid:I

    .line 43
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)V
    .locals 0
    .param p0, "x0"    # Lcom/hcn/auto/systemui/netspeed/NetSpeedView;

    .line 19
    invoke-direct {p0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->setViewVisibility()V

    return-void
.end method

.method static synthetic access$100()Ljava/lang/String;
    .locals 1

    .line 19
    sget-object v0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)Z
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/systemui/netspeed/NetSpeedView;

    .line 19
    iget-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->isVisibility:Z

    return v0
.end method

.method public static synthetic lambda$new$0(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;Landroid/os/Message;)Z
    .locals 1
    .param p1, "msg"    # Landroid/os/Message;

    .line 84
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Ljava/lang/String;

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->setText(Ljava/lang/CharSequence;)V

    .line 85
    const/4 v0, 0x1

    return v0
.end method

.method public static synthetic lambda$new$1(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)V
    .locals 4

    .line 103
    :goto_0
    iget-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mAttached:Z

    if-eqz v0, :cond_1

    .line 105
    iget-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->isVisibility:Z

    if-eqz v0, :cond_0

    .line 106
    invoke-static {}, Landroid/os/Message;->obtain()Landroid/os/Message;

    move-result-object v0

    .line 107
    .local v0, "message":Landroid/os/Message;
    iget v1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mUid:I

    invoke-static {v1}, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->getNetSpeed(I)Ljava/lang/String;

    move-result-object v1

    iput-object v1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 108
    iget-object v1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mainHandler:Landroid/os/Handler;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 109
    iget-object v1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mainHandler:Landroid/os/Handler;

    const-wide/16 v2, 0x76c

    invoke-virtual {v1, v0, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 112
    .end local v0    # "message":Landroid/os/Message;
    :cond_0
    const-wide/16 v0, 0x7d0

    :try_start_0
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 115
    :goto_1
    goto :goto_0

    .line 113
    :catch_0
    move-exception v0

    .line 114
    .local v0, "e":Ljava/lang/InterruptedException;
    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    .end local v0    # "e":Ljava/lang/InterruptedException;
    goto :goto_1

    .line 117
    :cond_1
    return-void
.end method

.method private setViewVisibility()V
    .locals 1

    .line 79
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->showNetSpeed(Landroid/content/Context;)Z

    move-result v0

    iput-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->isVisibility:Z

    .line 80
    iget-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->isVisibility:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    goto :goto_0

    :cond_0
    const/16 v0, 0x8

    :goto_0
    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->setVisibility(I)V

    .line 81
    return-void
.end method

.method private startThread()V
    .locals 3

    .line 120
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mThread:Ljava/lang/Thread;

    if-nez v0, :cond_0

    .line 121
    new-instance v0, Ljava/lang/Thread;

    iget-object v1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->threadRunnable:Ljava/lang/Runnable;

    const-string v2, "netSpeedThread"

    invoke-direct {v0, v1, v2}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    iput-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mThread:Ljava/lang/Thread;

    .line 122
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mThread:Ljava/lang/Thread;

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 124
    :cond_0
    return-void
.end method

.method private stopThread()V
    .locals 1

    .line 127
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mThread:Ljava/lang/Thread;

    if-eqz v0, :cond_0

    .line 128
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mThread:Ljava/lang/Thread;

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V

    .line 129
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mThread:Ljava/lang/Thread;

    .line 131
    :cond_0
    return-void
.end method


# virtual methods
.method protected onAttachedToWindow()V
    .locals 1

    .line 57
    invoke-super {p0}, Landroid/widget/TextView;->onAttachedToWindow()V

    .line 58
    iget-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mAttached:Z

    if-nez v0, :cond_0

    .line 59
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mAttached:Z

    .line 60
    const-class v0, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;

    invoke-interface {v0, p0}, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;->addDarkReceiver(Lcom/android/systemui/statusbar/policy/DarkIconDispatcher$DarkReceiver;)V

    .line 61
    invoke-static {}, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->reset()V

    .line 62
    invoke-direct {p0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->startThread()V

    .line 64
    :cond_0
    return-void
.end method

.method public onDarkChanged(Landroid/graphics/Rect;FI)V
    .locals 1
    .param p1, "area"    # Landroid/graphics/Rect;
    .param p2, "darkIntensity"    # F
    .param p3, "tint"    # I

    .line 99
    invoke-static {p1, p0, p3}, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;->getTint(Landroid/graphics/Rect;Landroid/view/View;I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->setTextColor(I)V

    .line 100
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 2

    .line 68
    invoke-super {p0}, Landroid/widget/TextView;->onDetachedFromWindow()V

    .line 69
    iget-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mAttached:Z

    if-eqz v0, :cond_0

    .line 70
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mAttached:Z

    .line 72
    :cond_0
    const-class v0, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;

    invoke-interface {v0, p0}, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;->removeDarkReceiver(Lcom/android/systemui/statusbar/policy/DarkIconDispatcher$DarkReceiver;)V

    .line 73
    invoke-static {}, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->reset()V

    .line 74
    invoke-direct {p0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->stopThread()V

    .line 75
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mainHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 76
    return-void
.end method

.method protected onFinishInflate()V
    .locals 5

    .line 47
    invoke-super {p0}, Landroid/widget/TextView;->onFinishInflate()V

    .line 48
    invoke-direct {p0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->setViewVisibility()V

    .line 49
    iget v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mUid:I

    invoke-static {v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->getNetSpeed(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->setText(Ljava/lang/CharSequence;)V

    .line 51
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "real_time_network"

    invoke-static {v1}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->mNetSpeedSettingsObserver:Landroid/database/ContentObserver;

    const/4 v3, 0x1

    const/4 v4, -0x1

    invoke-virtual {v0, v1, v3, v2, v4}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;I)V

    .line 53
    return-void
.end method
