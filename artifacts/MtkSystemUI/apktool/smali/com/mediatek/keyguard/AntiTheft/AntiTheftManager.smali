.class public Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;
.super Ljava/lang/Object;
.source "AntiTheftManager.java"


# static fields
.field public static final ANTITHEFT_NONEED_PRINT_TEXT:Ljava/lang/String; = "AntiTheft Noneed Print Text"

.field private static final DEBUG:Z = false

.field private static final MSG_ANTITHEFT_KEYGUARD_UPDATE:I = 0x3e9

.field public static final PPL_LOCK:Ljava/lang/String; = "com.mediatek.ppl.NOTIFY_LOCK"

.field public static final PPL_UNLOCK:Ljava/lang/String; = "com.mediatek.ppl.NOTIFY_UNLOCK"

.field public static final RESET_FOR_ANTITHEFT_LOCK:Ljava/lang/String; = "antitheftlock_reset"

.field private static final TAG:Ljava/lang/String; = "AntiTheftManager"

.field private static mAntiTheftAutoTestNotShowUI:Z

.field private static mAntiTheftLockEnabled:I

.field private static mContext:Landroid/content/Context;

.field private static mDismissable:I

.field private static mIPplManager:Lcom/mediatek/common/ppl/IPplManager;

.field private static mKeypadNeeded:I

.field private static sInstance:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;


# instance fields
.field private final MSG_ARG_LOCK:I

.field private final MSG_ARG_UNLOCK:I

.field protected final mBroadcastReceiver:Landroid/content/BroadcastReceiver;
    .annotation build Lcom/android/internal/annotations/VisibleForTesting;
    .end annotation
.end field

.field private mHandler:Landroid/os/Handler;

.field protected mKeyguardSecurityCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

.field private mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

.field protected mPplServiceConnection:Landroid/content/ServiceConnection;
    .annotation build Lcom/android/internal/annotations/VisibleForTesting;
    .end annotation
.end field

.field private mSecurityModel:Lcom/android/keyguard/KeyguardSecurityModel;

.field private mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 58
    const/4 v0, 0x0

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    .line 61
    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeypadNeeded:I

    .line 64
    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    .line 67
    sput-boolean v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftAutoTestNotShowUI:Z

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lcom/android/keyguard/ViewMediatorCallback;Lcom/android/internal/widget/LockPatternUtils;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "viewMediatorCallback"    # Lcom/android/keyguard/ViewMediatorCallback;
    .param p3, "lockPatternUtils"    # Lcom/android/internal/widget/LockPatternUtils;

    .line 75
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 305
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->MSG_ARG_LOCK:I

    .line 306
    const/4 v0, 0x1

    iput v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->MSG_ARG_UNLOCK:I

    .line 308
    new-instance v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$1;

    invoke-direct {v1, p0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$1;-><init>(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;)V

    iput-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    .line 340
    new-instance v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$2;

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v2

    const/4 v3, 0x0

    invoke-direct {v1, p0, v2, v3, v0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$2;-><init>(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;Landroid/os/Looper;Landroid/os/Handler$Callback;Z)V

    iput-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mHandler:Landroid/os/Handler;

    .line 443
    new-instance v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$3;

    invoke-direct {v1, p0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$3;-><init>(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;)V

    iput-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mPplServiceConnection:Landroid/content/ServiceConnection;

    .line 76
    const-string v1, "AntiTheftManager"

    const-string v2, "AntiTheftManager() is called."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 78
    sput-object p1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mContext:Landroid/content/Context;

    .line 79
    iput-object p2, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    .line 80
    iput-object p3, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    .line 81
    new-instance v1, Lcom/android/keyguard/KeyguardSecurityModel;

    sget-object v2, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mContext:Landroid/content/Context;

    invoke-direct {v1, v2}, Lcom/android/keyguard/KeyguardSecurityModel;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mSecurityModel:Lcom/android/keyguard/KeyguardSecurityModel;

    .line 83
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    .line 86
    .local v1, "filter":Landroid/content/IntentFilter;
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isPrivacyProtectionLockSupport()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 87
    const-string v2, "AntiTheftManager"

    const-string v3, "MTK_PRIVACY_PROTECTION_LOCK is enabled."

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 88
    const/4 v2, 0x2

    invoke-static {v2, v0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->setKeypadNeeded(IZ)V

    .line 89
    invoke-static {v2, v0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->setDismissable(IZ)V

    .line 90
    const-string v0, "com.mediatek.ppl.NOTIFY_LOCK"

    invoke-virtual {v1, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 91
    const-string v0, "com.mediatek.ppl.NOTIFY_UNLOCK"

    invoke-virtual {v1, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 94
    :cond_0
    sget-object v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v2, v1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 95
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;II)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;
    .param p1, "x1"    # I
    .param p2, "x2"    # I

    .line 35
    invoke-direct {p0, p1, p2}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->sendAntiTheftUpdateMsg(II)V

    return-void
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;IZ)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;
    .param p1, "x1"    # I
    .param p2, "x2"    # Z

    .line 35
    invoke-direct {p0, p1, p2}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->handleAntiTheftViewUpdate(IZ)V

    return-void
.end method

.method static synthetic access$202(Lcom/mediatek/common/ppl/IPplManager;)Lcom/mediatek/common/ppl/IPplManager;
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/common/ppl/IPplManager;

    .line 35
    sput-object p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mIPplManager:Lcom/mediatek/common/ppl/IPplManager;

    return-object p0
.end method

.method private bindPplService()V
    .locals 4

    .line 463
    const-string v0, "AntiTheftManager"

    const-string v1, "binPplService() is called."

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 465
    sget-object v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mIPplManager:Lcom/mediatek/common/ppl/IPplManager;

    if-nez v0, :cond_0

    .line 467
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    const-string v1, "com.mediatek.ppl.service"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 468
    .local v0, "intent":Landroid/content/Intent;
    const-string v1, "com.mediatek.ppl"

    const-string v2, "com.mediatek.ppl.PplService"

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->setClassName(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 469
    sget-object v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mPplServiceConnection:Landroid/content/ServiceConnection;

    const/4 v3, 0x1

    invoke-virtual {v1, v0, v2, v3}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    :try_end_0
    .catch Ljava/lang/SecurityException; {:try_start_0 .. :try_end_0} :catch_0

    .end local v0    # "intent":Landroid/content/Intent;
    goto :goto_0

    .line 470
    :catch_0
    move-exception v0

    .line 471
    .local v0, "e":Ljava/lang/SecurityException;
    const-string v1, "AntiTheftManager"

    const-string v2, "bindPplService() - error in bind ppl service."

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 472
    .end local v0    # "e":Ljava/lang/SecurityException;
    :goto_0
    goto :goto_1

    .line 475
    :cond_0
    const-string v0, "AntiTheftManager"

    const-string v1, "bindPplService() -- the ppl service is already bound."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 477
    :goto_1
    return-void
.end method

.method public static checkPplStatus()V
    .locals 8

    .line 419
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isSystemEncrypted()Z

    move-result v0

    const/4 v1, 0x1

    xor-int/2addr v0, v1

    .line 422
    .local v0, "isUnEncrypted":Z
    :try_start_0
    invoke-static {}, Lvendor/mediatek/hardware/pplagent/V1_0/IPplAgent;->getService()Lvendor/mediatek/hardware/pplagent/V1_0/IPplAgent;

    move-result-object v2

    .line 423
    .local v2, "agent":Lvendor/mediatek/hardware/pplagent/V1_0/IPplAgent;
    if-eqz v2, :cond_3

    .line 424
    invoke-interface {v2}, Lvendor/mediatek/hardware/pplagent/V1_0/IPplAgent;->needLock()I

    move-result v3

    const/4 v4, 0x0

    if-ne v3, v1, :cond_0

    move v3, v1

    goto :goto_0

    :cond_0
    move v3, v4

    .line 425
    .local v3, "flag":Z
    :goto_0
    const-string v5, "AntiTheftManager"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "PplCheckLocked, the lock flag is:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    if-eqz v3, :cond_1

    if-eqz v0, :cond_1

    goto :goto_1

    :cond_1
    move v1, v4

    :goto_1
    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v5, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 426
    if-eqz v3, :cond_2

    if-eqz v0, :cond_2

    .line 427
    sget v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    or-int/lit8 v1, v1, 0x2

    sput v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    .line 429
    .end local v3    # "flag":Z
    :cond_2
    goto :goto_2

    .line 430
    :cond_3
    const-string v1, "AntiTheftManager"

    const-string v3, "PplCheckLocked, PPLAgent doesn\'t exit"

    invoke-static {v1, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 434
    .end local v2    # "agent":Lvendor/mediatek/hardware/pplagent/V1_0/IPplAgent;
    :goto_2
    goto :goto_3

    .line 432
    :catch_0
    move-exception v1

    .line 433
    .local v1, "e":Ljava/lang/Exception;
    const-string v2, "AntiTheftManager"

    const-string v3, "doPplLockCheck() - error in get PPLAgent service."

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 435
    .end local v1    # "e":Ljava/lang/Exception;
    :goto_3
    return-void
.end method

.method private doPplCheckPassword(Ljava/lang/String;)Z
    .locals 4
    .param p1, "pw"    # Ljava/lang/String;

    .line 480
    const/4 v0, 0x0

    .line 482
    .local v0, "unlockSuccess":Z
    sget-object v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mIPplManager:Lcom/mediatek/common/ppl/IPplManager;

    if-eqz v1, :cond_1

    .line 484
    :try_start_0
    sget-object v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mIPplManager:Lcom/mediatek/common/ppl/IPplManager;

    invoke-interface {v1, p1}, Lcom/mediatek/common/ppl/IPplManager;->unlock(Ljava/lang/String;)Z

    move-result v1

    move v0, v1

    .line 485
    const-string v1, "AntiTheftManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "doPplCheckPassword, unlockSuccess is "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 487
    if-eqz v0, :cond_0

    .line 489
    const/4 v1, 0x2

    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->setAntiTheftLocked(IZ)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 492
    :catch_0
    move-exception v1

    .line 494
    :cond_0
    :goto_0
    goto :goto_1

    .line 498
    :cond_1
    const-string v1, "AntiTheftManager"

    const-string v2, "doPplCheckPassword() mIPplManager == null !!??"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 501
    :goto_1
    return v0
.end method

.method private doPplLockCheck()V
    .locals 2

    .line 413
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    .line 414
    const/4 v0, 0x1

    invoke-direct {p0, v1, v0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->setAntiTheftLocked(IZ)V

    .line 416
    :cond_0
    return-void
.end method

.method public static getAntiTheftLayoutId()I
    .locals 1

    .line 257
    const v0, 0x7f0d0126

    return v0
.end method

.method public static getAntiTheftMessageAreaText(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    .locals 3
    .param p0, "text"    # Ljava/lang/CharSequence;
    .param p1, "seperator"    # Ljava/lang/CharSequence;

    .line 267
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 268
    .local v0, "b":Ljava/lang/StringBuilder;
    if-eqz p0, :cond_0

    invoke-interface {p0}, Ljava/lang/CharSequence;->length()I

    move-result v1

    if-lez v1, :cond_0

    .line 269
    invoke-interface {p0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "AntiTheft Noneed Print Text"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 270
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    .line 271
    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    .line 273
    :cond_0
    sget-object v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mContext:Landroid/content/Context;

    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getPrompt()I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    .line 274
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static getAntiTheftModeName(I)Ljava/lang/String;
    .locals 1
    .param p0, "mode"    # I

    .line 108
    if-eqz p0, :cond_1

    const/4 v0, 0x2

    if-eq p0, v0, :cond_0

    .line 115
    const-string v0, "AntiTheftMode.None"

    return-object v0

    .line 112
    :cond_0
    const-string v0, "AntiTheftMode.PplLock"

    return-object v0

    .line 110
    :cond_1
    const-string v0, "AntiTheftMode.None"

    return-object v0
.end method

.method public static getAntiTheftViewId()I
    .locals 1

    .line 253
    const v0, 0x7f0a01f7

    return v0
.end method

.method public static getCurrentAntiTheftMode()I
    .locals 4

    .line 121
    const/4 v0, 0x0

    .line 123
    .local v0, "shift":I
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->isAntiTheftLocked()Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_0

    .line 124
    return v2

    .line 126
    :cond_0
    :goto_0
    const/16 v1, 0x20

    if-ge v0, v1, :cond_2

    .line 127
    sget v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    const/4 v3, 0x1

    shl-int/2addr v3, v0

    and-int/2addr v1, v3

    .line 128
    .local v1, "mode":I
    if-eqz v1, :cond_1

    .line 129
    return v1

    .line 131
    :cond_1
    add-int/lit8 v0, v0, 0x1

    .line 132
    .end local v1    # "mode":I
    goto :goto_0

    .line 134
    :cond_2
    return v2
.end method

.method public static getInstance(Landroid/content/Context;Lcom/android/keyguard/ViewMediatorCallback;Lcom/android/internal/widget/LockPatternUtils;)Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;
    .locals 2
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "viewMediatorCallback"    # Lcom/android/keyguard/ViewMediatorCallback;
    .param p2, "lockPatternUtils"    # Lcom/android/internal/widget/LockPatternUtils;

    .line 100
    sget-object v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->sInstance:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    if-nez v0, :cond_0

    .line 101
    const-string v0, "AntiTheftManager"

    const-string v1, "getInstance(...) create one."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 102
    new-instance v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    invoke-direct {v0, p0, p1, p2}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;-><init>(Landroid/content/Context;Lcom/android/keyguard/ViewMediatorCallback;Lcom/android/internal/widget/LockPatternUtils;)V

    sput-object v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->sInstance:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    .line 104
    :cond_0
    sget-object v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->sInstance:Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;

    return-object v0
.end method

.method public static getPrompt()I
    .locals 1

    .line 261
    const v0, 0x7f110443

    return v0
.end method

.method private handleAntiTheftViewUpdate(IZ)V
    .locals 4
    .param p1, "antiTheftLockType"    # I
    .param p2, "lock"    # Z

    .line 352
    invoke-static {p1, p2}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->isNeedUpdate(IZ)Z

    move-result v0

    if-eqz v0, :cond_5

    .line 353
    invoke-direct {p0, p1, p2}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->setAntiTheftLocked(IZ)V

    .line 355
    const/4 v0, 0x1

    if-eqz p2, :cond_3

    .line 356
    const-string v1, "AntiTheftManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "handleAntiTheftViewUpdate() - locked, !isShowing = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    .line 357
    invoke-interface {v3}, Lcom/android/keyguard/ViewMediatorCallback;->isShowing()Z

    move-result v3

    xor-int/2addr v0, v3

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v0, " isKeyguardDoneOnGoing = "

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    .line 358
    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->isKeyguardDoneOnGoing()Z

    move-result v0

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 356
    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 362
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    .line 363
    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->isKeyguardDoneOnGoing()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_1

    .line 367
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mSecurityModel:Lcom/android/keyguard/KeyguardSecurityModel;

    .line 369
    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v1

    .line 368
    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardSecurityModel;->getSecurityMode(I)Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    move-result-object v0

    .line 367
    invoke-static {v0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->isAntiTheftPriorToSecMode(Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;)Z

    move-result v0

    .line 370
    .local v0, "needToRest":Z
    if-eqz v0, :cond_1

    .line 371
    const-string v1, "AntiTheftManager"

    const-string v2, "handleAntiTheftViewUpdate() - call resetStateLocked()."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 374
    iget-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    invoke-interface {v1}, Lcom/android/keyguard/ViewMediatorCallback;->resetStateLocked()V

    goto :goto_0

    .line 377
    :cond_1
    const-string v1, "AntiTheftManager"

    const-string v2, "No need to reset the security view to show AntiTheft,since current view should show above antitheft view."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 380
    .end local v0    # "needToRest":Z
    :goto_0
    goto :goto_2

    .line 364
    :cond_2
    :goto_1
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lcom/android/keyguard/ViewMediatorCallback;->showLocked(Landroid/os/Bundle;)V

    goto :goto_2

    .line 384
    :cond_3
    iget-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeyguardSecurityCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    if-eqz v1, :cond_4

    .line 385
    iget-object v1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeyguardSecurityCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 386
    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v2

    .line 385
    invoke-interface {v1, v0, v2}, Lcom/android/keyguard/KeyguardSecurityCallback;->dismiss(ZI)V

    goto :goto_2

    .line 388
    :cond_4
    const-string v0, "AntiTheftManager"

    const-string v1, "mKeyguardSecurityCallback is null !"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 392
    :goto_2
    invoke-virtual {p0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->adjustStatusBarLocked()V

    .line 394
    :cond_5
    return-void
.end method

.method public static isAntiTheftAutoTestNotShowUI()Z
    .locals 1

    .line 278
    sget-boolean v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftAutoTestNotShowUI:Z

    return v0
.end method

.method public static isAntiTheftLocked()Z
    .locals 1

    .line 157
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isAntiTheftPriorToSecMode(Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;)Z
    .locals 4
    .param p0, "mode"    # Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    .line 226
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getCurrentAntiTheftMode()I

    move-result v0

    .line 227
    .local v0, "currentAntiTheftType":I
    const/4 v1, 0x0

    .line 234
    .local v1, "isAntiTheftHasHigherPriority":Z
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->isAntiTheftLocked()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 235
    sget-object v2, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager$4;->$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode:[I

    invoke-virtual {p0}, Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;->ordinal()I

    move-result v3

    aget v2, v2, v3

    packed-switch v2, :pswitch_data_0

    .line 243
    const/4 v1, 0x1

    goto :goto_0

    .line 241
    :pswitch_0
    nop

    .line 248
    :cond_0
    :goto_0
    return v1

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public static isDismissable()Z
    .locals 3

    .line 190
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getCurrentAntiTheftMode()I

    move-result v0

    .line 191
    .local v0, "mode":I
    const/4 v1, 0x0

    .line 193
    .local v1, "dismissAble":Z
    if-nez v0, :cond_0

    .line 194
    const/4 v1, 0x1

    goto :goto_0

    .line 197
    :cond_0
    sget v2, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    and-int/2addr v2, v0

    if-eqz v2, :cond_1

    .line 198
    const/4 v1, 0x1

    .line 206
    :cond_1
    :goto_0
    return v1
.end method

.method public static isKeypadNeeded()Z
    .locals 5

    .line 138
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getCurrentAntiTheftMode()I

    move-result v0

    .line 139
    .local v0, "mode":I
    const-string v1, "AntiTheftManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getCurrentAntiTheftMode() = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getAntiTheftModeName(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 141
    sget v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeypadNeeded:I

    and-int/2addr v1, v0

    if-eqz v1, :cond_0

    const/4 v1, 0x1

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    .line 142
    .local v1, "needKeypad":Z
    :goto_0
    const-string v2, "AntiTheftManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isKeypadNeeded() = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 144
    return v1
.end method

.method private static isNeedUpdate(IZ)Z
    .locals 4
    .param p0, "lockMode"    # I
    .param p1, "enable"    # Z

    .line 164
    const/4 v0, 0x1

    .line 166
    .local v0, "needToUpdate":Z
    if-eqz p1, :cond_0

    sget v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    and-int/2addr v1, p0

    if-eqz v1, :cond_0

    .line 167
    const-string v1, "AntiTheftManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "isNeedUpdate() - lockMode( "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " ) is already enabled, no need update"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 169
    const/4 v0, 0x0

    goto :goto_0

    .line 171
    :cond_0
    if-nez p1, :cond_1

    sget v1, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    and-int/2addr v1, p0

    if-nez v1, :cond_1

    .line 172
    const-string v1, "AntiTheftManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "isNeedUpdate() - lockMode( "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " ) is already disabled, no need update"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 174
    const/4 v0, 0x0

    .line 177
    :cond_1
    :goto_0
    return v0
.end method

.method private sendAntiTheftUpdateMsg(II)V
    .locals 2
    .param p1, "antiTheftLockType"    # I
    .param p2, "lock"    # I

    .line 334
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x3e9

    invoke-virtual {v0, v1}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v0

    .line 335
    .local v0, "msg":Landroid/os/Message;
    iput p1, v0, Landroid/os/Message;->arg1:I

    .line 336
    iput p2, v0, Landroid/os/Message;->arg2:I

    .line 337
    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 338
    return-void
.end method

.method private setAntiTheftLocked(IZ)V
    .locals 2
    .param p1, "lockMode"    # I
    .param p2, "enable"    # Z

    .line 181
    if-eqz p2, :cond_0

    .line 182
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    or-int/2addr v0, p1

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    goto :goto_0

    .line 185
    :cond_0
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    not-int v1, p1

    and-int/2addr v0, v1

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mAntiTheftLockEnabled:I

    .line 187
    :goto_0
    return-void
.end method

.method public static setDismissable(IZ)V
    .locals 3
    .param p0, "lockMode"    # I
    .param p1, "canBeDismissed"    # Z

    .line 210
    const-string v0, "AntiTheftManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mDismissable is "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v2, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " before"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 211
    if-eqz p1, :cond_0

    .line 212
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    or-int/2addr v0, p0

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    goto :goto_0

    .line 215
    :cond_0
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    not-int v1, p0

    and-int/2addr v0, v1

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    .line 218
    :goto_0
    const-string v0, "AntiTheftManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mDismissable is "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v2, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mDismissable:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " after"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 219
    return-void
.end method

.method public static setKeypadNeeded(IZ)V
    .locals 2
    .param p0, "lockMode"    # I
    .param p1, "need"    # Z

    .line 148
    if-eqz p1, :cond_0

    .line 149
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeypadNeeded:I

    or-int/2addr v0, p0

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeypadNeeded:I

    goto :goto_0

    .line 152
    :cond_0
    sget v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeypadNeeded:I

    not-int v1, p0

    and-int/2addr v0, v1

    sput v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeypadNeeded:I

    .line 154
    :goto_0
    return-void
.end method


# virtual methods
.method public adjustStatusBarLocked()V
    .locals 1

    .line 505
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mViewMediatorCallback:Lcom/android/keyguard/ViewMediatorCallback;

    invoke-interface {v0}, Lcom/android/keyguard/ViewMediatorCallback;->adjustStatusBarLocked()V

    .line 506
    return-void
.end method

.method public checkPassword(Ljava/lang/String;)Z
    .locals 5
    .param p1, "pw"    # Ljava/lang/String;

    .line 287
    const/4 v0, 0x0

    .line 288
    .local v0, "unlockSuccess":Z
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getCurrentAntiTheftMode()I

    move-result v1

    .line 290
    .local v1, "mode":I
    const-string v2, "AntiTheftManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "checkPassword, mode is "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v1}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->getAntiTheftModeName(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 292
    const/4 v2, 0x2

    if-eq v1, v2, :cond_0

    goto :goto_0

    .line 294
    :cond_0
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->doPplCheckPassword(Ljava/lang/String;)Z

    move-result v0

    .line 295
    nop

    .line 300
    :goto_0
    const-string v2, "AntiTheftManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "checkPassword, unlockSuccess is "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 302
    return v0
.end method

.method public doAntiTheftLockCheck()V
    .locals 2

    .line 405
    const-string v0, "ro.crypto.state"

    const-string v1, "unsupported"

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 406
    .local v0, "status":Ljava/lang/String;
    const-string v1, "unencrypted"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 407
    invoke-direct {p0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->doPplLockCheck()V

    .line 409
    :cond_0
    return-void
.end method

.method public doBindAntiThftLockServices()V
    .locals 2

    .line 397
    const-string v0, "AntiTheftManager"

    const-string v1, "doBindAntiThftLockServices() is called."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 399
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isPrivacyProtectionLockSupport()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 400
    invoke-direct {p0}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->bindPplService()V

    .line 402
    :cond_0
    return-void
.end method

.method public getHandlerInstance()Landroid/os/Handler;
    .locals 1
    .annotation build Lcom/android/internal/annotations/VisibleForTesting;
    .end annotation

    .line 530
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method public getPPLBroadcastReceiverInstance()Landroid/content/BroadcastReceiver;
    .locals 1
    .annotation build Lcom/android/internal/annotations/VisibleForTesting;
    .end annotation

    .line 525
    iget-object v0, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    return-object v0
.end method

.method public getPPLManagerInstance()Lcom/mediatek/common/ppl/IPplManager;
    .locals 1
    .annotation build Lcom/android/internal/annotations/VisibleForTesting;
    .end annotation

    .line 520
    sget-object v0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mIPplManager:Lcom/mediatek/common/ppl/IPplManager;

    return-object v0
.end method

.method public setSecurityViewCallback(Lcom/android/keyguard/KeyguardSecurityCallback;)V
    .locals 3
    .param p1, "callback"    # Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 514
    const-string v0, "AntiTheftManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setSecurityViewCallback("

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, ")"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 515
    iput-object p1, p0, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->mKeyguardSecurityCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 516
    return-void
.end method
