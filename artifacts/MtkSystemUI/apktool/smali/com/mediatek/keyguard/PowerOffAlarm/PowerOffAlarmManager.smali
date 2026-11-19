.class public Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;
.super Ljava/lang/Object;
.source "PowerOffAlarmManager.java"


# static fields
.field private static sALRAM_TITLE:Ljava/lang/String;

.field public static sDEFAULT_TITLE:Ljava/lang/String;

.field private static sInstance:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;


# instance fields
.field private final mBroadcastReceiver:Landroid/content/BroadcastReceiver;

.field private mContext:Landroid/content/Context;

.field private mHandler:Landroid/os/Handler;

.field private mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

.field private mNeedToShowAlarmView:Z

.field private mSendRemoveIPOWinBroadcastRunnable:Ljava/lang/Runnable;

.field private mSystemReady:Z

.field private mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 30
    const-string v0, ""

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sDEFAULT_TITLE:Ljava/lang/String;

    .line 31
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sDEFAULT_TITLE:Ljava/lang/String;

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sALRAM_TITLE:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lcom/android/keyguard/ViewMediatorCallback;Lcom/android/internal/widget/LockPatternUtils;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "viewMediatorCallback"    # Lcom/android/keyguard/ViewMediatorCallback;
    .param p3, "lockPatternUtils"    # Lcom/android/internal/widget/LockPatternUtils;

    .line 39
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 27
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mSystemReady:Z

    .line 28
    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mNeedToShowAlarmView:Z

    .line 62
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager$1;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager$1;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    .line 89
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager$2;

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v1

    const/4 v2, 0x0

    const/4 v3, 0x1

    invoke-direct {v0, p0, v1, v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager$2;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;Landroid/os/Looper;Landroid/os/Handler$Callback;Z)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mHandler:Landroid/os/Handler;

    .line 144
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager$3;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager$3;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mSendRemoveIPOWinBroadcastRunnable:Ljava/lang/Runnable;

    .line 41
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mContext:Landroid/content/Context;

    .line 42
    iput-object p2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    .line 43
    iput-object p3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    .line 45
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 47
    .local v0, "filter":Landroid/content/IntentFilter;
    const-string v1, "android.intent.action.normal.shutdown"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 48
    const-string v1, "mediatek.intent.action.LAUNCH_POWEROFF_ALARM"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 49
    const-string v1, "android.intent.action.normal.boot"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 50
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 51
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    .line 20
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)Lcom/android/keyguard/ViewMediatorCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    .line 20
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    return-object v0
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    .line 20
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->handleAlarmBoot()V

    return-void
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)Lcom/android/internal/widget/LockPatternUtils;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    .line 20
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    return-object v0
.end method

.method static synthetic access$400(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    .line 20
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method public static getAlarmTitle()Ljava/lang/String;
    .locals 1

    .line 218
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sALRAM_TITLE:Ljava/lang/String;

    return-object v0
.end method

.method public static getInstance(Landroid/content/Context;Lcom/android/keyguard/ViewMediatorCallback;Lcom/android/internal/widget/LockPatternUtils;)Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;
    .locals 1
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "viewMediatorCallback"    # Lcom/android/keyguard/ViewMediatorCallback;
    .param p2, "lockPatternUtils"    # Lcom/android/internal/widget/LockPatternUtils;

    .line 56
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sInstance:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    if-nez v0, :cond_0

    .line 57
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    invoke-direct {v0, p0, p1, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;-><init>(Landroid/content/Context;Lcom/android/keyguard/ViewMediatorCallback;Lcom/android/internal/widget/LockPatternUtils;)V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sInstance:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    .line 59
    :cond_0
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sInstance:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;

    return-object v0
.end method

.method private handleAlarmBoot()V
    .locals 2

    .line 158
    const-string v0, "PowerOffAlarmManager"

    const-string v1, "handleAlarmBoot"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 159
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mNeedToShowAlarmView:Z

    .line 160
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->maybeShowAlarmView()V

    .line 161
    return-void
.end method

.method public static isAlarmBoot()Z
    .locals 1

    .line 180
    const/4 v0, 0x0

    return v0
.end method

.method private maybeShowAlarmView()V
    .locals 2

    .line 189
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mSystemReady:Z

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mNeedToShowAlarmView:Z

    if-eqz v0, :cond_1

    .line 190
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mNeedToShowAlarmView:Z

    .line 191
    const-string v0, "PowerOffAlarmManager"

    const-string v1, "maybeShowAlarmView start to showLocked"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 192
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 193
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->setSuppressPlaySoundFlag()V

    .line 194
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->hideLocked()V

    .line 196
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lcom/android/keyguard/ViewMediatorCallback;->showLocked(Landroid/os/Bundle;)V

    .line 198
    :cond_1
    return-void
.end method

.method public static setAlarmTitle(Ljava/lang/String;)V
    .locals 0
    .param p0, "title"    # Ljava/lang/String;

    .line 214
    sput-object p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sALRAM_TITLE:Ljava/lang/String;

    .line 215
    return-void
.end method

.method private startAlarmService()V
    .locals 3

    .line 169
    new-instance v0, Landroid/content/Intent;

    const-string v1, "com.android.deskclock.START_ALARM"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 170
    .local v0, "in":Landroid/content/Intent;
    const-string v1, "isAlarmBoot"

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    .line 171
    const-string v1, "com.android.deskclock"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 172
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 173
    return-void
.end method


# virtual methods
.method public onSystemReady()V
    .locals 1

    .line 184
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mSystemReady:Z

    .line 185
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->maybeShowAlarmView()V

    .line 186
    return-void
.end method

.method public startAlarm()V
    .locals 4

    .line 164
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->startAlarmService()V

    .line 166
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->mSendRemoveIPOWinBroadcastRunnable:Ljava/lang/Runnable;

    const-wide/16 v2, 0x5dc

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 167
    return-void
.end method
