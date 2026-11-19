.class public Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;
.super Ljava/lang/Object;
.source "VoiceWakeupManagerProxy.java"


# static fields
.field private static mVoiceWakeupClsObj:Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation
.end field

.field private static mVoiceWakeupInstance:Ljava/lang/Object;

.field private static sInstance:Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 20
    const/4 v0, 0x0

    sput-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->sInstance:Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;

    .line 21
    sput-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    .line 22
    sput-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 24
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 25
    const-string v0, "VoiceWakeupManagerProxy"

    const-string v1, "constructor is called."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    return-void
.end method

.method private static createVoiceWakeupManagerInstance()V
    .locals 4

    .line 39
    :try_start_0
    const-string v0, "com.mediatek.keyguard.VoiceWakeup.VoiceWakeupManager"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    sput-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    .line 40
    sget-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    if-eqz v0, :cond_0

    .line 41
    sget-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    const-string v1, "getInstance"

    const/4 v2, 0x0

    new-array v3, v2, [Ljava/lang/Class;

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 42
    .local v0, "vowInstance":Ljava/lang/reflect/Method;
    sget-object v1, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    new-array v2, v2, [Ljava/lang/Object;

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    sput-object v1, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 46
    .end local v0    # "vowInstance":Ljava/lang/reflect/Method;
    :cond_0
    goto :goto_0

    .line 44
    :catch_0
    move-exception v0

    .line 45
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "VoiceWakeupManagerProxy"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "createVoiceWakeupManagerInstance error: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 47
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method

.method public static getInstance()Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;
    .locals 2

    .line 29
    sget-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->sInstance:Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;

    if-nez v0, :cond_0

    .line 30
    const-string v0, "VoiceWakeupManagerProxy"

    const-string v1, "getInstance(...) create one."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 31
    new-instance v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;

    invoke-direct {v0}, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->sInstance:Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;

    .line 32
    invoke-static {}, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->createVoiceWakeupManagerInstance()V

    .line 34
    :cond_0
    sget-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->sInstance:Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;

    return-object v0
.end method


# virtual methods
.method public isDismissAndLaunchApp()Z
    .locals 5

    .line 50
    const/4 v0, 0x0

    move v1, v0

    .line 52
    .local v1, "result":Z
    :try_start_0
    sget-object v2, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    if-eqz v2, :cond_0

    .line 53
    sget-object v2, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    const-string v3, "isDismissAndLaunchApp"

    new-array v4, v0, [Ljava/lang/Class;

    invoke-virtual {v2, v3, v4}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v2

    .line 55
    .local v2, "method":Ljava/lang/reflect/Method;
    sget-object v3, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    new-array v0, v0, [Ljava/lang/Object;

    invoke-virtual {v2, v3, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 60
    .end local v1    # "result":Z
    .end local v2    # "method":Ljava/lang/reflect/Method;
    .local v0, "result":Z
    move v1, v0

    .end local v0    # "result":Z
    .restart local v1    # "result":Z
    :cond_0
    goto :goto_0

    .line 57
    :catch_0
    move-exception v0

    .line 58
    .local v0, "e":Ljava/lang/Exception;
    const-string v2, "VoiceWakeupManagerProxy"

    const-string v3, "reflect isDismissAndLaunchApp error"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 59
    const-string v2, "VoiceWakeupManagerProxy"

    invoke-static {v0}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 61
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return v1
.end method

.method public notifySecurityModeChange(Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;)V
    .locals 7
    .param p1, "currentMode"    # Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;
    .param p2, "nextMode"    # Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    .line 81
    :try_start_0
    sget-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    if-eqz v0, :cond_0

    .line 82
    sget-object v0, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    const-string v1, "notifySecurityModeChange"

    const/4 v2, 0x2

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    const-class v4, Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    const/4 v6, 0x1

    aput-object v4, v3, v6

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 85
    .local v0, "method":Ljava/lang/reflect/Method;
    sget-object v1, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p1, v2, v5

    aput-object p2, v2, v6

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 90
    .end local v0    # "method":Ljava/lang/reflect/Method;
    :cond_0
    goto :goto_0

    .line 87
    :catch_0
    move-exception v0

    .line 88
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "VoiceWakeupManagerProxy"

    const-string v2, "reflect notifySecurityModeChange error"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 89
    const-string v1, "VoiceWakeupManagerProxy"

    invoke-static {v0}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 91
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method

.method public onDismiss()Z
    .locals 5

    .line 65
    const/4 v0, 0x0

    move v1, v0

    .line 67
    .local v1, "result":Z
    :try_start_0
    sget-object v2, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    if-eqz v2, :cond_0

    .line 68
    sget-object v2, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupClsObj:Ljava/lang/Class;

    const-string v3, "onDismiss"

    new-array v4, v0, [Ljava/lang/Class;

    invoke-virtual {v2, v3, v4}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v2

    .line 69
    .local v2, "method":Ljava/lang/reflect/Method;
    sget-object v3, Lcom/mediatek/keyguard/VoiceWakeup/VoiceWakeupManagerProxy;->mVoiceWakeupInstance:Ljava/lang/Object;

    new-array v0, v0, [Ljava/lang/Object;

    invoke-virtual {v2, v3, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 74
    .end local v1    # "result":Z
    .end local v2    # "method":Ljava/lang/reflect/Method;
    .local v0, "result":Z
    move v1, v0

    .end local v0    # "result":Z
    .restart local v1    # "result":Z
    :cond_0
    goto :goto_0

    .line 71
    :catch_0
    move-exception v0

    .line 72
    .local v0, "e":Ljava/lang/Exception;
    const-string v2, "VoiceWakeupManagerProxy"

    const-string v3, "reflect onDismiss error"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 73
    const-string v2, "VoiceWakeupManagerProxy"

    invoke-static {v0}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 75
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return v1
.end method
