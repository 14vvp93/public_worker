.class public Lcom/android/keyguard/EmergencyButton;
.super Landroid/widget/Button;
.source "EmergencyButton.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;
    }
.end annotation


# static fields
.field private static final DEBUG:Z

.field private static final INTENT_EMERGENCY_DIAL:Landroid/content/Intent;


# instance fields
.field private mDownX:I

.field private mDownY:I

.field private mEccPhoneIdForNoneSecurityMode:I

.field private final mEmergencyAffordanceManager:Lcom/android/internal/util/EmergencyAffordanceManager;

.field private mEmergencyButtonCallback:Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;

.field private mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

.field private final mEnableEmergencyCallWhileSimLocked:Z

.field mInfoCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

.field private mIsSecure:Z

.field private final mIsVoiceCapable:Z

.field private mLocateAtNonSecureView:Z

.field private mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

.field private mLongPressWasDragged:Z

.field private mPowerManager:Landroid/os/PowerManager;

.field private mTelephonyManager:Landroid/telephony/TelephonyManager;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 58
    sget-boolean v0, Lcom/android/keyguard/KeyguardConstants;->DEBUG:Z

    sput-boolean v0, Lcom/android/keyguard/EmergencyButton;->DEBUG:Z

    .line 59
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "com.android.phone.EmergencyDialer.DIAL"

    .line 60
    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v0

    const-string v1, "com.android.phone"

    .line 61
    invoke-virtual {v0, v1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v0

    .line 62
    const/high16 v1, 0x14800000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setFlags(I)Landroid/content/Intent;

    move-result-object v0

    sput-object v0, Lcom/android/keyguard/EmergencyButton;->INTENT_EMERGENCY_DIAL:Landroid/content/Intent;

    .line 59
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 128
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/keyguard/EmergencyButton;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 129
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 132
    invoke-direct {p0, p1, p2}, Landroid/widget/Button;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 71
    new-instance v0, Lcom/android/keyguard/EmergencyButton$1;

    invoke-direct {v0, p0}, Lcom/android/keyguard/EmergencyButton$1;-><init>(Lcom/android/keyguard/EmergencyButton;)V

    iput-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mInfoCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    .line 115
    const/4 v0, -0x1

    iput v0, p0, Lcom/android/keyguard/EmergencyButton;->mEccPhoneIdForNoneSecurityMode:I

    .line 119
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/keyguard/EmergencyButton;->mLocateAtNonSecureView:Z

    .line 133
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x11200cb

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v1

    iput-boolean v1, p0, Lcom/android/keyguard/EmergencyButton;->mIsVoiceCapable:Z

    .line 135
    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x112006a

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v1

    iput-boolean v1, p0, Lcom/android/keyguard/EmergencyButton;->mEnableEmergencyCallWhileSimLocked:Z

    .line 137
    new-instance v1, Lcom/android/internal/util/EmergencyAffordanceManager;

    invoke-direct {v1, p1}, Lcom/android/internal/util/EmergencyAffordanceManager;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyAffordanceManager:Lcom/android/internal/util/EmergencyAffordanceManager;

    .line 141
    :try_start_0
    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-static {v1}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    move-result-object v1

    .line 142
    invoke-virtual {v1}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->makeEmergencyButton()Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

    move-result-object v1

    iput-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 146
    goto :goto_0

    .line 143
    :catch_0
    move-exception v1

    .line 144
    .local v1, "e":Ljava/lang/Exception;
    const-string v2, "EmergencyButton"

    const-string v3, "EmergencyButton() - error in calling getEmergencyButtonExt()."

    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 145
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 149
    .end local v1    # "e":Ljava/lang/Exception;
    :goto_0
    sget-object v1, Lcom/android/keyguard/R$styleable;->ECCButtonAttr:[I

    .line 150
    invoke-virtual {p1, p2, v1}, Landroid/content/Context;->obtainStyledAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object v1

    .line 151
    .local v1, "localAtNonSecureValue":Landroid/content/res/TypedArray;
    iget-boolean v2, p0, Lcom/android/keyguard/EmergencyButton;->mLocateAtNonSecureView:Z

    invoke-virtual {v1, v0, v2}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/keyguard/EmergencyButton;->mLocateAtNonSecureView:Z

    .line 153
    const/4 v0, 0x0

    .line 155
    .end local v1    # "localAtNonSecureValue":Landroid/content/res/TypedArray;
    .local v0, "localAtNonSecureValue":Landroid/content/res/TypedArray;
    const-string v1, "phone"

    invoke-virtual {p1, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/telephony/TelephonyManager;

    iput-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    .line 156
    return-void
.end method

.method static synthetic access$000(Lcom/android/keyguard/EmergencyButton;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/keyguard/EmergencyButton;

    .line 56
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->updateEmergencyCallButton()V

    return-void
.end method

.method static synthetic access$100()Z
    .locals 1

    .line 56
    sget-boolean v0, Lcom/android/keyguard/EmergencyButton;->DEBUG:Z

    return v0
.end method

.method static synthetic access$200(Lcom/android/keyguard/EmergencyButton;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/keyguard/EmergencyButton;

    .line 56
    iget-boolean v0, p0, Lcom/android/keyguard/EmergencyButton;->mLongPressWasDragged:Z

    return v0
.end method

.method static synthetic access$300(Lcom/android/keyguard/EmergencyButton;)Lcom/android/internal/util/EmergencyAffordanceManager;
    .locals 1
    .param p0, "x0"    # Lcom/android/keyguard/EmergencyButton;

    .line 56
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyAffordanceManager:Lcom/android/internal/util/EmergencyAffordanceManager;

    return-object v0
.end method

.method private eccButtonShouldShow()Z
    .locals 11

    .line 348
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->getNumOfPhone()I

    move-result v0

    .line 349
    .local v0, "phoneCount":I
    new-array v1, v0, [Z

    .line 350
    .local v1, "isServiceSupportEcc":[Z
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v2

    .line 352
    .local v2, "tm":Landroid/telephony/TelephonyManager;
    const/4 v3, -0x1

    iput v3, p0, Lcom/android/keyguard/EmergencyButton;->mEccPhoneIdForNoneSecurityMode:I

    .line 354
    if-eqz v2, :cond_4

    .line 355
    const/4 v4, 0x0

    move v5, v4

    .local v5, "i":I
    :goto_0
    if-ge v5, v0, :cond_4

    .line 356
    invoke-static {v5}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v6

    .line 358
    .local v6, "subId":I
    iget-object v7, p0, Lcom/android/keyguard/EmergencyButton;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    invoke-virtual {v7, v6}, Landroid/telephony/TelephonyManager;->getServiceStateForSubscriber(I)Landroid/telephony/ServiceState;

    move-result-object v7

    .line 359
    .local v7, "ss":Landroid/telephony/ServiceState;
    if-eqz v7, :cond_2

    .line 360
    const-string v8, "EmergencyButton"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "ss.getState()="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Landroid/telephony/ServiceState;->getState()I

    move-result v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v10, " ss.isEmergencyOnly()="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 361
    invoke-virtual {v7}, Landroid/telephony/ServiceState;->isEmergencyOnly()Z

    move-result v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v10, " for simId="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    .line 360
    invoke-static {v8, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 362
    invoke-virtual {v7}, Landroid/telephony/ServiceState;->getState()I

    move-result v8

    if-eqz v8, :cond_1

    .line 363
    invoke-virtual {v7}, Landroid/telephony/ServiceState;->isEmergencyOnly()Z

    move-result v8

    if-eqz v8, :cond_0

    goto :goto_1

    .line 370
    :cond_0
    aput-boolean v4, v1, v5

    goto :goto_2

    .line 364
    :cond_1
    :goto_1
    const/4 v8, 0x1

    aput-boolean v8, v1, v5

    .line 366
    iget v8, p0, Lcom/android/keyguard/EmergencyButton;->mEccPhoneIdForNoneSecurityMode:I

    if-ne v8, v3, :cond_3

    .line 367
    iput v5, p0, Lcom/android/keyguard/EmergencyButton;->mEccPhoneIdForNoneSecurityMode:I

    goto :goto_2

    .line 373
    :cond_2
    const-string v8, "EmergencyButton"

    const-string v9, "Service state is null"

    invoke-static {v8, v9}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 355
    .end local v6    # "subId":I
    .end local v7    # "ss":Landroid/telephony/ServiceState;
    :cond_3
    :goto_2
    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 378
    .end local v5    # "i":I
    :cond_4
    iget-object v3, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->getCurPhoneId()I

    move-result v4

    invoke-interface {v3, v1, v4}, Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;->showEccByServiceState([ZI)Z

    move-result v3

    return v3
.end method

.method private getCurPhoneId()I
    .locals 2

    .line 390
    new-instance v0, Lcom/android/keyguard/KeyguardSecurityModel;

    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Lcom/android/keyguard/KeyguardSecurityModel;-><init>(Landroid/content/Context;)V

    .line 391
    .local v0, "securityModel":Lcom/android/keyguard/KeyguardSecurityModel;
    nop

    .line 392
    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardSecurityModel;->getSecurityMode(I)Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;

    move-result-object v1

    .line 391
    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardSecurityModel;->getPhoneIdUsingSecurityMode(Lcom/android/keyguard/KeyguardSecurityModel$SecurityMode;)I

    move-result v1

    return v1
.end method

.method private getTelecommManager()Landroid/telecom/TelecomManager;
    .locals 2

    .line 343
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    const-string v1, "telecom"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telecom/TelecomManager;

    return-object v0
.end method

.method private isInCall()Z
    .locals 1

    .line 339
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->getTelecommManager()Landroid/telecom/TelecomManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telecom/TelecomManager;->isInCall()Z

    move-result v0

    return v0
.end method

.method private resumeCall()V
    .locals 2

    .line 332
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->getTelecommManager()Landroid/telecom/TelecomManager;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/telecom/TelecomManager;->showInCallScreen(Z)V

    .line 333
    return-void
.end method

.method private updateEmergencyCallButton()V
    .locals 8

    .line 269
    const/4 v0, 0x0

    .line 273
    .local v0, "visible":Z
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->isInCall()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 274
    const/4 v0, 0x1

    goto :goto_0

    .line 275
    :cond_0
    iget-boolean v1, p0, Lcom/android/keyguard/EmergencyButton;->mIsVoiceCapable:Z

    if-eqz v1, :cond_2

    .line 276
    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-static {v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v1

    .line 277
    invoke-virtual {v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->isSimPinVoiceSecure()Z

    move-result v1

    .line 278
    .local v1, "simLocked":Z
    if-eqz v1, :cond_1

    .line 280
    iget-boolean v0, p0, Lcom/android/keyguard/EmergencyButton;->mEnableEmergencyCallWhileSimLocked:Z

    goto :goto_0

    .line 283
    :cond_1
    iget-object v2, p0, Lcom/android/keyguard/EmergencyButton;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v3

    invoke-virtual {v2, v3}, Lcom/android/internal/widget/LockPatternUtils;->isSecure(I)Z

    move-result v0

    .line 288
    .end local v1    # "simLocked":Z
    :cond_2
    :goto_0
    invoke-static {}, Lcom/mediatek/keyguard/AntiTheft/AntiTheftManager;->isAntiTheftLocked()Z

    move-result v1

    .line 289
    .local v1, "antiTheftLocked":Z
    const/4 v2, 0x0

    .line 292
    .local v2, "show":Z
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->eccButtonShouldShow()Z

    move-result v3

    .line 294
    .local v3, "eccShouldShow":Z
    sget-boolean v4, Lcom/android/keyguard/EmergencyButton;->DEBUG:Z

    if-eqz v4, :cond_3

    .line 295
    const-string v4, "EmergencyButton"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mLocateAtNonSecureView = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v6, p0, Lcom/android/keyguard/EmergencyButton;->mLocateAtNonSecureView:Z

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 298
    :cond_3
    iget-boolean v4, p0, Lcom/android/keyguard/EmergencyButton;->mLocateAtNonSecureView:Z

    const/4 v5, 0x0

    if-eqz v4, :cond_5

    iget-object v4, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

    invoke-interface {v4}, Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;->showEccInNonSecureUnlock()Z

    move-result v4

    if-nez v4, :cond_5

    .line 299
    sget-boolean v4, Lcom/android/keyguard/EmergencyButton;->DEBUG:Z

    if-eqz v4, :cond_4

    .line 300
    const-string v4, "EmergencyButton"

    const-string v6, "ECC Button is located on Notification Keygaurd and OP do not ask to show. So this is a normal case ,we never show it."

    invoke-static {v4, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 303
    :cond_4
    const/4 v2, 0x0

    goto :goto_2

    .line 305
    :cond_5
    if-nez v0, :cond_6

    if-nez v1, :cond_6

    iget-object v4, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

    .line 306
    invoke-interface {v4}, Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;->showEccInNonSecureUnlock()Z

    move-result v4

    if-eqz v4, :cond_7

    :cond_6
    if-eqz v3, :cond_7

    const/4 v4, 0x1

    goto :goto_1

    :cond_7
    move v4, v5

    :goto_1
    move v2, v4

    .line 308
    const-string v4, "EmergencyButton"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "show = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 309
    sget-boolean v4, Lcom/android/keyguard/EmergencyButton;->DEBUG:Z

    if-eqz v4, :cond_8

    .line 310
    const-string v4, "EmergencyButton"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v7, "visible= "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v7, ", antiTheftLocked="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v7, ", mEmergencyButtonExt.showEccInNonSecureUnlock() ="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v7, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

    .line 312
    invoke-interface {v7}, Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;->showEccInNonSecureUnlock()Z

    move-result v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v7, ", eccShouldShow="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 310
    invoke-static {v4, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 317
    :cond_8
    :goto_2
    iget-boolean v4, p0, Lcom/android/keyguard/EmergencyButton;->mLocateAtNonSecureView:Z

    if-eqz v4, :cond_9

    if-nez v2, :cond_9

    .line 318
    const/16 v4, 0x8

    invoke-virtual {p0, v4}, Lcom/android/keyguard/EmergencyButton;->setVisibility(I)V

    goto :goto_3

    .line 320
    :cond_9
    invoke-virtual {p0, v2, v5}, Lcom/android/keyguard/EmergencyButton;->updateEmergencyCallButtonState(ZZ)V

    .line 322
    :goto_3
    return-void
.end method


# virtual methods
.method protected onAttachedToWindow()V
    .locals 2

    .line 160
    invoke-super {p0}, Landroid/widget/Button;->onAttachedToWindow()V

    .line 161
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mInfoCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->registerCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 162
    return-void
.end method

.method protected onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 1
    .param p1, "newConfig"    # Landroid/content/res/Configuration;

    .line 226
    invoke-super {p0, p1}, Landroid/widget/Button;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 228
    const v0, 0x7f110314

    invoke-virtual {p0, v0}, Lcom/android/keyguard/EmergencyButton;->setText(I)V

    .line 229
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->updateEmergencyCallButton()V

    .line 230
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 2

    .line 166
    invoke-super {p0}, Landroid/widget/Button;->onDetachedFromWindow()V

    .line 167
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mInfoCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->removeCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 168
    return-void
.end method

.method protected onFinishInflate()V
    .locals 2

    .line 172
    invoke-super {p0}, Landroid/widget/Button;->onFinishInflate()V

    .line 173
    new-instance v0, Lcom/android/internal/widget/LockPatternUtils;

    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Lcom/android/internal/widget/LockPatternUtils;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    .line 174
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    const-string v1, "power"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/PowerManager;

    iput-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mPowerManager:Landroid/os/PowerManager;

    .line 175
    new-instance v0, Lcom/android/keyguard/EmergencyButton$2;

    invoke-direct {v0, p0}, Lcom/android/keyguard/EmergencyButton$2;-><init>(Lcom/android/keyguard/EmergencyButton;)V

    invoke-virtual {p0, v0}, Lcom/android/keyguard/EmergencyButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 183
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/internal/widget/LockPatternUtils;->isSecure(I)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/keyguard/EmergencyButton;->mIsSecure:Z

    .line 186
    new-instance v0, Lcom/android/keyguard/EmergencyButton$3;

    invoke-direct {v0, p0}, Lcom/android/keyguard/EmergencyButton$3;-><init>(Lcom/android/keyguard/EmergencyButton;)V

    invoke-virtual {p0, v0}, Lcom/android/keyguard/EmergencyButton;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V

    .line 197
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->updateEmergencyCallButton()V

    .line 198
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 6
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 202
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    float-to-int v0, v0

    .line 203
    .local v0, "x":I
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v1

    float-to-int v1, v1

    .line 204
    .local v1, "y":I
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v2

    if-nez v2, :cond_0

    .line 205
    iput v0, p0, Lcom/android/keyguard/EmergencyButton;->mDownX:I

    .line 206
    iput v1, p0, Lcom/android/keyguard/EmergencyButton;->mDownY:I

    .line 207
    const/4 v2, 0x0

    iput-boolean v2, p0, Lcom/android/keyguard/EmergencyButton;->mLongPressWasDragged:Z

    goto :goto_0

    .line 209
    :cond_0
    iget v2, p0, Lcom/android/keyguard/EmergencyButton;->mDownX:I

    sub-int v2, v0, v2

    invoke-static {v2}, Ljava/lang/Math;->abs(I)I

    move-result v2

    .line 210
    .local v2, "xDiff":I
    iget v3, p0, Lcom/android/keyguard/EmergencyButton;->mDownY:I

    sub-int v3, v1, v3

    invoke-static {v3}, Ljava/lang/Math;->abs(I)I

    move-result v3

    .line 211
    .local v3, "yDiff":I
    iget-object v4, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-static {v4}, Landroid/view/ViewConfiguration;->get(Landroid/content/Context;)Landroid/view/ViewConfiguration;

    move-result-object v4

    invoke-virtual {v4}, Landroid/view/ViewConfiguration;->getScaledTouchSlop()I

    move-result v4

    .line 212
    .local v4, "touchSlop":I
    invoke-static {v3}, Ljava/lang/Math;->abs(I)I

    move-result v5

    if-gt v5, v4, :cond_1

    invoke-static {v2}, Ljava/lang/Math;->abs(I)I

    move-result v5

    if-le v5, v4, :cond_2

    .line 213
    :cond_1
    const/4 v5, 0x1

    iput-boolean v5, p0, Lcom/android/keyguard/EmergencyButton;->mLongPressWasDragged:Z

    .line 216
    .end local v2    # "xDiff":I
    .end local v3    # "yDiff":I
    .end local v4    # "touchSlop":I
    :cond_2
    :goto_0
    invoke-super {p0, p1}, Landroid/widget/Button;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v2

    return v2
.end method

.method public performLongClick()Z
    .locals 1

    .line 221
    invoke-super {p0}, Landroid/widget/Button;->performLongClick()Z

    move-result v0

    return v0
.end method

.method public setCallback(Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;)V
    .locals 0
    .param p1, "callback"    # Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;

    .line 325
    iput-object p1, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonCallback:Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;

    .line 326
    return-void
.end method

.method public takeEmergencyCallAction()V
    .locals 6

    .line 236
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    const/16 v1, 0xc8

    invoke-static {v0, v1}, Lcom/android/internal/logging/MetricsLogger;->action(Landroid/content/Context;I)V

    .line 239
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mPowerManager:Landroid/os/PowerManager;

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v1

    const/4 v3, 0x1

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/PowerManager;->userActivity(JZ)V

    .line 241
    :try_start_0
    invoke-static {}, Landroid/app/ActivityManager;->getService()Landroid/app/IActivityManager;

    move-result-object v0

    invoke-interface {v0}, Landroid/app/IActivityManager;->stopSystemLockTaskMode()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 244
    goto :goto_0

    .line 242
    :catch_0
    move-exception v0

    .line 243
    .local v0, "e":Landroid/os/RemoteException;
    const-string v1, "EmergencyButton"

    const-string v2, "Failed to stop app pinning"

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 245
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_0
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->isInCall()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 246
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->resumeCall()V

    .line 247
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonCallback:Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;

    if-eqz v0, :cond_2

    .line 248
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonCallback:Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;

    invoke-interface {v0}, Lcom/android/keyguard/EmergencyButton$EmergencyButtonCallback;->onEmergencyButtonClickedWhenInCall()V

    goto :goto_1

    .line 251
    :cond_0
    iget-object v0, p0, Lcom/android/keyguard/EmergencyButton;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    invoke-virtual {v0, v3}, Lcom/android/keyguard/KeyguardUpdateMonitor;->reportEmergencyCallAction(Z)V

    .line 256
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->getCurPhoneId()I

    move-result v0

    .line 257
    .local v0, "phoneId":I
    const/4 v1, -0x1

    if-ne v0, v1, :cond_1

    .line 258
    iget v0, p0, Lcom/android/keyguard/EmergencyButton;->mEccPhoneIdForNoneSecurityMode:I

    .line 260
    :cond_1
    iget-object v1, p0, Lcom/android/keyguard/EmergencyButton;->mEmergencyButtonExt:Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;

    sget-object v2, Lcom/android/keyguard/EmergencyButton;->INTENT_EMERGENCY_DIAL:Landroid/content/Intent;

    invoke-interface {v1, v2, v0}, Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;->customizeEmergencyIntent(Landroid/content/Intent;I)V

    .line 262
    invoke-virtual {p0}, Lcom/android/keyguard/EmergencyButton;->getContext()Landroid/content/Context;

    move-result-object v1

    sget-object v2, Lcom/android/keyguard/EmergencyButton;->INTENT_EMERGENCY_DIAL:Landroid/content/Intent;

    .line 263
    invoke-virtual {p0}, Lcom/android/keyguard/EmergencyButton;->getContext()Landroid/content/Context;

    move-result-object v3

    const/4 v4, 0x0

    invoke-static {v3, v4, v4}, Landroid/app/ActivityOptions;->makeCustomAnimation(Landroid/content/Context;II)Landroid/app/ActivityOptions;

    move-result-object v3

    invoke-virtual {v3}, Landroid/app/ActivityOptions;->toBundle()Landroid/os/Bundle;

    move-result-object v3

    new-instance v4, Landroid/os/UserHandle;

    .line 264
    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v5

    invoke-direct {v4, v5}, Landroid/os/UserHandle;-><init>(I)V

    .line 262
    invoke-virtual {v1, v2, v3, v4}, Landroid/content/Context;->startActivityAsUser(Landroid/content/Intent;Landroid/os/Bundle;Landroid/os/UserHandle;)V

    .line 266
    .end local v0    # "phoneId":I
    :cond_2
    :goto_1
    return-void
.end method

.method public updateEmergencyCallButtonState(ZZ)V
    .locals 3
    .param p1, "shown"    # Z
    .param p2, "showIcon"    # Z

    .line 396
    iget-boolean v0, p0, Lcom/android/keyguard/EmergencyButton;->mIsVoiceCapable:Z

    if-eqz v0, :cond_3

    if-eqz p1, :cond_3

    .line 397
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/keyguard/EmergencyButton;->setVisibility(I)V

    .line 404
    invoke-direct {p0}, Lcom/android/keyguard/EmergencyButton;->isInCall()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 406
    const v1, 0x1040328

    .line 407
    .local v1, "textId":I
    if-eqz p2, :cond_0

    const v2, 0x1080084

    goto :goto_0

    :cond_0
    move v2, v0

    .line 408
    .local v2, "phoneCallIcon":I
    :goto_0
    invoke-virtual {p0, v2, v0, v0, v0}, Lcom/android/keyguard/EmergencyButton;->setCompoundDrawablesWithIntrinsicBounds(IIII)V

    .line 409
    .end local v2    # "phoneCallIcon":I
    goto :goto_2

    .line 410
    .end local v1    # "textId":I
    :cond_1
    const v1, 0x104030d

    .line 411
    .restart local v1    # "textId":I
    if-eqz p2, :cond_2

    const v2, 0x1080363

    goto :goto_1

    :cond_2
    move v2, v0

    .line 412
    .local v2, "emergencyIcon":I
    :goto_1
    invoke-virtual {p0, v2, v0, v0, v0}, Lcom/android/keyguard/EmergencyButton;->setCompoundDrawablesWithIntrinsicBounds(IIII)V

    .line 414
    .end local v2    # "emergencyIcon":I
    :goto_2
    invoke-virtual {p0, v1}, Lcom/android/keyguard/EmergencyButton;->setText(I)V

    .line 415
    return-void

    .line 399
    .end local v1    # "textId":I
    :cond_3
    const/4 v0, 0x4

    invoke-virtual {p0, v0}, Lcom/android/keyguard/EmergencyButton;->setVisibility(I)V

    .line 400
    return-void
.end method
