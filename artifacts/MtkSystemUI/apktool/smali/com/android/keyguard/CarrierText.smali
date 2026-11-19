.class public Lcom/android/keyguard/CarrierText;
.super Landroid/widget/TextView;
.source "CarrierText.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/keyguard/CarrierText$CarrierTextTransformationMethod;,
        Lcom/android/keyguard/CarrierText$StatusMode;
    }
.end annotation


# static fields
.field private static final DEBUG:Z

.field private static mSeparator:Ljava/lang/CharSequence;


# instance fields
.field private mCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

.field private mCarrier:[Ljava/lang/String;

.field private mCarrierNeedToShow:[Z

.field private mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

.field private mContext:Landroid/content/Context;

.field private mFlags:I

.field private mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;

.field private final mIsEmergencyCallCapable:Z

.field private mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

.field private mNumOfPhone:I

.field private mSimErrorState:[Z

.field private mStatusMode:[Lcom/android/keyguard/CarrierText$StatusMode;

.field private mWifiManager:Landroid/net/wifi/WifiManager;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 56
    sget-boolean v0, Lcom/android/keyguard/KeyguardConstants;->DEBUG:Z

    sput-boolean v0, Lcom/android/keyguard/CarrierText;->DEBUG:Z

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 157
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/keyguard/CarrierText;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 158
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 161
    invoke-direct {p0, p1, p2}, Landroid/widget/TextView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 67
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getPhoneCount()I

    move-result v0

    new-array v0, v0, [Z

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mSimErrorState:[Z

    .line 84
    new-instance v0, Lcom/android/keyguard/CarrierText$1;

    invoke-direct {v0, p0}, Lcom/android/keyguard/CarrierText$1;-><init>(Lcom/android/keyguard/CarrierText;)V

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    .line 162
    iput-object p1, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    .line 163
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x11200cb

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/keyguard/CarrierText;->mIsEmergencyCallCapable:Z

    .line 165
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 167
    invoke-direct {p0}, Lcom/android/keyguard/CarrierText;->initMembers()V

    .line 170
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    move-result-object v0

    .line 171
    invoke-virtual {v0}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->makeOperatorSIMString()Lcom/mediatek/keyguard/ext/IOperatorSIMString;

    move-result-object v0

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;

    .line 172
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    move-result-object v0

    .line 173
    invoke-virtual {v0}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->makeCarrierText()Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    move-result-object v0

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    .line 176
    invoke-virtual {p1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    sget-object v1, Lcom/android/keyguard/R$styleable;->CarrierText:[I

    const/4 v2, 0x0

    invoke-virtual {v0, p2, v1, v2, v2}, Landroid/content/res/Resources$Theme;->obtainStyledAttributes(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;

    move-result-object v0

    .line 179
    .local v0, "a":Landroid/content/res/TypedArray;
    :try_start_0
    invoke-virtual {v0, v2, v2}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    move-result v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 181
    .local v1, "useAllCaps":Z
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->recycle()V

    .line 182
    nop

    .line 181
    nop

    .line 183
    new-instance v2, Lcom/android/keyguard/CarrierText$CarrierTextTransformationMethod;

    iget-object v3, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-direct {v2, p0, v3, v1}, Lcom/android/keyguard/CarrierText$CarrierTextTransformationMethod;-><init>(Lcom/android/keyguard/CarrierText;Landroid/content/Context;Z)V

    invoke-virtual {p0, v2}, Lcom/android/keyguard/CarrierText;->setTransformationMethod(Landroid/text/method/TransformationMethod;)V

    .line 185
    const-string/jumbo v2, "wifi"

    invoke-virtual {p1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/net/wifi/WifiManager;

    iput-object v2, p0, Lcom/android/keyguard/CarrierText;->mWifiManager:Landroid/net/wifi/WifiManager;

    .line 186
    return-void

    .line 181
    .end local v1    # "useAllCaps":Z
    :catchall_0
    move-exception v1

    invoke-virtual {v0}, Landroid/content/res/TypedArray;->recycle()V

    throw v1
.end method

.method private appendCsgInfo(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
    .locals 2
    .param p1, "srcText"    # Ljava/lang/CharSequence;
    .param p2, "hnbName"    # Ljava/lang/CharSequence;
    .param p3, "csgId"    # Ljava/lang/CharSequence;

    .line 647
    move-object v0, p1

    .line 648
    .local v0, "outText":Ljava/lang/CharSequence;
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 649
    invoke-static {p1, p2}, Lcom/android/keyguard/CarrierText;->concatenate(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    goto :goto_0

    .line 650
    :cond_0
    invoke-static {p3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 651
    invoke-static {p1, p3}, Lcom/android/keyguard/CarrierText;->concatenate(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 654
    :cond_1
    :goto_0
    return-object v0
.end method

.method private static concatenate(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
    .locals 4
    .param p0, "plmn"    # Ljava/lang/CharSequence;
    .param p1, "spn"    # Ljava/lang/CharSequence;

    .line 520
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    .line 521
    .local v0, "plmnValid":Z
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    .line 522
    .local v1, "spnValid":Z
    if-eqz v0, :cond_0

    if-eqz v1, :cond_0

    .line 523
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    sget-object v3, Lcom/android/keyguard/CarrierText;->mSeparator:Ljava/lang/CharSequence;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    return-object v2

    .line 524
    :cond_0
    if-eqz v0, :cond_1

    .line 525
    return-object p0

    .line 526
    :cond_1
    if-eqz v1, :cond_2

    .line 527
    return-object p1

    .line 529
    :cond_2
    const-string v2, ""

    return-object v2
.end method

.method private getCarrierTextForSimState(ILcom/android/internal/telephony/IccCardConstants$State;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
    .locals 5
    .param p1, "phoneId"    # I
    .param p2, "simState"    # Lcom/android/internal/telephony/IccCardConstants$State;
    .param p3, "text"    # Ljava/lang/CharSequence;
    .param p4, "hnbName"    # Ljava/lang/CharSequence;
    .param p5, "csgId"    # Ljava/lang/CharSequence;

    .line 377
    const/4 v0, 0x0

    .line 378
    .local v0, "carrierText":Ljava/lang/CharSequence;
    invoke-direct {p0, p2}, Lcom/android/keyguard/CarrierText;->getStatusForIccState(Lcom/android/internal/telephony/IccCardConstants$State;)Lcom/android/keyguard/CarrierText$StatusMode;

    move-result-object v1

    .line 379
    .local v1, "status":Lcom/android/keyguard/CarrierText$StatusMode;
    sget-object v2, Lcom/android/keyguard/CarrierText$2;->$SwitchMap$com$android$keyguard$CarrierText$StatusMode:[I

    invoke-virtual {v1}, Lcom/android/keyguard/CarrierText$StatusMode;->ordinal()I

    move-result v3

    aget v2, v2, v3

    packed-switch v2, :pswitch_data_0

    .line 440
    move-object v0, p3

    goto/16 :goto_0

    .line 435
    :pswitch_0
    nop

    .line 436
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v2

    const v3, 0x7f110308

    invoke-virtual {v2, v3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    .line 435
    invoke-direct {p0, v2, p3, p4, p5}, Lcom/android/keyguard/CarrierText;->makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 438
    goto :goto_0

    .line 430
    :pswitch_1
    nop

    .line 431
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v2

    const v3, 0x7f11030a

    invoke-virtual {v2, v3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    .line 430
    invoke-direct {p0, v2, p3, p4, p5}, Lcom/android/keyguard/CarrierText;->makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 433
    goto :goto_0

    .line 424
    :pswitch_2
    nop

    .line 425
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v2

    const v3, 0x7f110309

    invoke-virtual {v2, v3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    .line 424
    invoke-direct {p0, v2, p3, p4, p5}, Lcom/android/keyguard/CarrierText;->makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 427
    goto :goto_0

    .line 420
    :pswitch_3
    const/4 v0, 0x0

    .line 421
    goto :goto_0

    .line 413
    :pswitch_4
    nop

    .line 414
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v2

    const v3, 0x7f110303

    invoke-virtual {v2, v3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    .line 413
    invoke-direct {p0, v2, p3, p4, p5}, Lcom/android/keyguard/CarrierText;->makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 417
    goto :goto_0

    .line 401
    :pswitch_5
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v2

    const v3, 0x7f1102f2

    invoke-virtual {v2, v3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    .line 403
    .local v2, "simMessage":Ljava/lang/CharSequence;
    nop

    .line 404
    invoke-direct {p0, v2, p3, p4, p5}, Lcom/android/keyguard/CarrierText;->makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 406
    iget-object v3, p0, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    invoke-interface {v3, v0, v2, p1}, Lcom/mediatek/keyguard/ext/ICarrierTextExt;->customizeCarrierText(Ljava/lang/CharSequence;Ljava/lang/CharSequence;I)Ljava/lang/CharSequence;

    move-result-object v0

    .line 409
    iget-object v3, p0, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    invoke-interface {v3, v0}, Lcom/mediatek/keyguard/ext/ICarrierTextExt;->customizeCarrierTextWhenSimMissing(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 410
    goto :goto_0

    .line 391
    .end local v2    # "simMessage":Ljava/lang/CharSequence;
    :pswitch_6
    iget-object v2, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    const v3, 0x7f1102f4

    .line 392
    invoke-virtual {v2, v3}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    .line 391
    invoke-direct {p0, v2, p3, p4, p5}, Lcom/android/keyguard/CarrierText;->makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 394
    goto :goto_0

    .line 386
    :pswitch_7
    const-string v0, ""

    .line 387
    const/4 v0, 0x0

    .line 388
    goto :goto_0

    .line 381
    :pswitch_8
    move-object v0, p3

    .line 382
    nop

    .line 445
    :goto_0
    if-eqz v0, :cond_0

    .line 446
    iget-object v2, p0, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    iget-object v3, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    const/4 v4, 0x0

    invoke-interface {v2, v0, v3, p1, v4}, Lcom/mediatek/keyguard/ext/ICarrierTextExt;->customizeCarrierTextWhenCardTypeLocked(Ljava/lang/CharSequence;Landroid/content/Context;IZ)Ljava/lang/CharSequence;

    move-result-object v2

    .line 447
    invoke-interface {v2}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v0

    .line 450
    :cond_0
    sget-boolean v2, Lcom/android/keyguard/CarrierText;->DEBUG:Z

    if-eqz v2, :cond_1

    .line 451
    const-string v2, "CarrierText"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "getCarrierTextForSimState simState="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v4, " carrierName(from Sub)="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v4, " HNB="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v4, " CSG="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v4, " carrierText="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 455
    :cond_1
    return-object v0

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private getStatusForIccState(Lcom/android/internal/telephony/IccCardConstants$State;)Lcom/android/keyguard/CarrierText$StatusMode;
    .locals 3
    .param p1, "simState"    # Lcom/android/internal/telephony/IccCardConstants$State;

    .line 480
    if-nez p1, :cond_0

    .line 481
    sget-object v0, Lcom/android/keyguard/CarrierText$StatusMode;->SimUnknown:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v0

    .line 484
    :cond_0
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    .line 485
    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->isDeviceProvisioned()Z

    move-result v0

    if-nez v0, :cond_2

    sget-object v0, Lcom/android/internal/telephony/IccCardConstants$State;->ABSENT:Lcom/android/internal/telephony/IccCardConstants$State;

    if-eq p1, v0, :cond_1

    sget-object v0, Lcom/android/internal/telephony/IccCardConstants$State;->PERM_DISABLED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne p1, v0, :cond_2

    :cond_1
    const/4 v0, 0x1

    goto :goto_0

    :cond_2
    const/4 v0, 0x0

    .line 491
    .local v0, "missingAndNotProvisioned":Z
    :goto_0
    if-eqz v0, :cond_3

    .line 492
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimMissingLocked:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 495
    :cond_3
    sget-object v1, Lcom/android/keyguard/CarrierText$2;->$SwitchMap$com$android$internal$telephony$IccCardConstants$State:[I

    invoke-virtual {p1}, Lcom/android/internal/telephony/IccCardConstants$State;->ordinal()I

    move-result v2

    aget v1, v1, v2

    packed-switch v1, :pswitch_data_0

    .line 516
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimMissing:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 514
    :pswitch_0
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimIoError:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 512
    :pswitch_1
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimUnknown:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 510
    :pswitch_2
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimPermDisabled:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 508
    :pswitch_3
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->Normal:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 506
    :pswitch_4
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimPukLocked:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 504
    :pswitch_5
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimLocked:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 502
    :pswitch_6
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimNotReady:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 500
    :pswitch_7
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->NetworkLocked:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    .line 497
    :pswitch_8
    sget-object v1, Lcom/android/keyguard/CarrierText$StatusMode;->SimMissing:Lcom/android/keyguard/CarrierText$StatusMode;

    return-object v1

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private initMembers()V
    .locals 3

    .line 144
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->getNumOfPhone()I

    move-result v0

    iput v0, p0, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    .line 146
    iget v0, p0, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    new-array v0, v0, [Ljava/lang/String;

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mCarrier:[Ljava/lang/String;

    .line 147
    iget v0, p0, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    new-array v0, v0, [Z

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mCarrierNeedToShow:[Z

    .line 149
    iget v0, p0, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    new-array v0, v0, [Lcom/android/keyguard/CarrierText$StatusMode;

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mStatusMode:[Lcom/android/keyguard/CarrierText$StatusMode;

    .line 150
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget v1, p0, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    if-ge v0, v1, :cond_0

    .line 151
    iget-object v1, p0, Lcom/android/keyguard/CarrierText;->mStatusMode:[Lcom/android/keyguard/CarrierText$StatusMode;

    sget-object v2, Lcom/android/keyguard/CarrierText$StatusMode;->Normal:Lcom/android/keyguard/CarrierText$StatusMode;

    aput-object v2, v1, v0

    .line 150
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 153
    .end local v0    # "i":I
    :cond_0
    return-void
.end method

.method private isWifiOnlyDevice()Z
    .locals 2

    .line 592
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "connectivity"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    .line 594
    .local v0, "cm":Landroid/net/ConnectivityManager;
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/net/ConnectivityManager;->isNetworkSupported(I)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    return v1
.end method

.method private makeCarrierStringOnEmergencyCapable(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
    .locals 2
    .param p1, "simMessage"    # Ljava/lang/CharSequence;
    .param p2, "emergencyCallMessage"    # Ljava/lang/CharSequence;
    .param p3, "hnbName"    # Ljava/lang/CharSequence;
    .param p4, "csgId"    # Ljava/lang/CharSequence;

    .line 464
    move-object v0, p2

    .line 465
    .local v0, "emergencyCallMessageExtend":Ljava/lang/CharSequence;
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 466
    invoke-direct {p0, p2, p3, p4}, Lcom/android/keyguard/CarrierText;->appendCsgInfo(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 469
    :cond_0
    iget-boolean v1, p0, Lcom/android/keyguard/CarrierText;->mIsEmergencyCallCapable:Z

    if-eqz v1, :cond_1

    .line 470
    invoke-static {p1, v0}, Lcom/android/keyguard/CarrierText;->concatenate(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v1

    return-object v1

    .line 472
    :cond_1
    return-object p1
.end method

.method private showOrHideCarrier()Z
    .locals 12

    .line 606
    const/4 v0, 0x0

    .line 608
    .local v0, "mNumOfSIM":I
    const/4 v1, 0x0

    move v2, v0

    move v0, v1

    .local v0, "i":I
    .local v2, "mNumOfSIM":I
    :goto_0
    iget v3, p0, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    const/4 v4, 0x1

    if-ge v0, v3, :cond_3

    .line 609
    iget-object v3, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v3, v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v3

    .line 610
    .local v3, "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    invoke-direct {p0, v3}, Lcom/android/keyguard/CarrierText;->getStatusForIccState(Lcom/android/internal/telephony/IccCardConstants$State;)Lcom/android/keyguard/CarrierText$StatusMode;

    move-result-object v5

    .line 611
    .local v5, "statusMode":Lcom/android/keyguard/CarrierText$StatusMode;
    sget-object v6, Lcom/android/keyguard/CarrierText$StatusMode;->SimMissing:Lcom/android/keyguard/CarrierText$StatusMode;

    if-eq v5, v6, :cond_1

    sget-object v6, Lcom/android/keyguard/CarrierText$StatusMode;->SimMissingLocked:Lcom/android/keyguard/CarrierText$StatusMode;

    if-eq v5, v6, :cond_1

    sget-object v6, Lcom/android/keyguard/CarrierText$StatusMode;->SimUnknown:Lcom/android/keyguard/CarrierText$StatusMode;

    if-ne v5, v6, :cond_0

    goto :goto_1

    :cond_0
    move v6, v1

    goto :goto_2

    :cond_1
    :goto_1
    move v6, v4

    .line 614
    .local v6, "simMissing":Z
    :goto_2
    iget-object v7, p0, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    invoke-interface {v7, v6, v0}, Lcom/mediatek/keyguard/ext/ICarrierTextExt;->showCarrierTextWhenSimMissing(ZI)Z

    move-result v6

    .line 616
    if-nez v6, :cond_2

    .line 617
    iget-object v7, p0, Lcom/android/keyguard/CarrierText;->mCarrierNeedToShow:[Z

    aput-boolean v4, v7, v0

    .line 618
    add-int/lit8 v2, v2, 0x1

    goto :goto_3

    .line 620
    :cond_2
    iget-object v4, p0, Lcom/android/keyguard/CarrierText;->mCarrierNeedToShow:[Z

    aput-boolean v1, v4, v0

    .line 608
    .end local v3    # "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    .end local v5    # "statusMode":Lcom/android/keyguard/CarrierText$StatusMode;
    .end local v6    # "simMissing":Z
    :goto_3
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 624
    .end local v0    # "i":I
    :cond_3
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSubscriptionInfo(Z)Ljava/util/List;

    move-result-object v0

    .line 625
    .local v0, "subs":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    if-nez v2, :cond_6

    .line 626
    iget-object v3, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v3}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getDefaultPlmn()Ljava/lang/CharSequence;

    move-result-object v3

    invoke-interface {v3}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v3

    .line 627
    .local v3, "defaultPlmn":Ljava/lang/String;
    const/4 v5, 0x0

    .line 628
    .local v5, "index":I
    move v6, v1

    .local v6, "i":I
    :goto_4
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v7

    if-ge v6, v7, :cond_5

    .line 629
    invoke-interface {v0, v6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Landroid/telephony/SubscriptionInfo;

    .line 630
    .local v7, "info":Landroid/telephony/SubscriptionInfo;
    invoke-virtual {v7}, Landroid/telephony/SubscriptionInfo;->getSubscriptionId()I

    move-result v8

    .line 631
    .local v8, "subId":I
    invoke-virtual {v7}, Landroid/telephony/SubscriptionInfo;->getSimSlotIndex()I

    move-result v9

    .line 632
    .local v9, "phoneId":I
    invoke-virtual {v7}, Landroid/telephony/SubscriptionInfo;->getCarrierName()Ljava/lang/CharSequence;

    move-result-object v10

    .line 633
    .local v10, "carrierName":Ljava/lang/CharSequence;
    if-eqz v10, :cond_4

    invoke-virtual {v3, v10}, Ljava/lang/String;->contentEquals(Ljava/lang/CharSequence;)Z

    move-result v11

    if-nez v11, :cond_4

    .line 634
    move v5, v9

    .line 635
    goto :goto_5

    .line 628
    .end local v7    # "info":Landroid/telephony/SubscriptionInfo;
    .end local v8    # "subId":I
    .end local v9    # "phoneId":I
    .end local v10    # "carrierName":Ljava/lang/CharSequence;
    :cond_4
    add-int/lit8 v6, v6, 0x1

    goto :goto_4

    .line 638
    .end local v6    # "i":I
    :cond_5
    :goto_5
    iget-object v6, p0, Lcom/android/keyguard/CarrierText;->mCarrierNeedToShow:[Z

    aput-boolean v4, v6, v5

    .line 641
    .end local v3    # "defaultPlmn":Ljava/lang/String;
    .end local v5    # "index":I
    :cond_6
    if-nez v2, :cond_7

    move v1, v4

    nop

    :cond_7
    return v1
.end method


# virtual methods
.method protected onAttachedToWindow()V
    .locals 2

    .line 334
    invoke-super {p0}, Landroid/widget/TextView;->onAttachedToWindow()V

    .line 335
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v0}, Landroid/net/ConnectivityManager;->from(Landroid/content/Context;)Landroid/net/ConnectivityManager;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/net/ConnectivityManager;->isNetworkSupported(I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 337
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 338
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget-object v1, p0, Lcom/android/keyguard/CarrierText;->mCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->registerCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    goto :goto_0

    .line 341
    :cond_0
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 342
    const-string v0, ""

    invoke-virtual {p0, v0}, Lcom/android/keyguard/CarrierText;->setText(Ljava/lang/CharSequence;)V

    .line 344
    :goto_0
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 2

    .line 348
    invoke-super {p0}, Landroid/widget/TextView;->onDetachedFromWindow()V

    .line 349
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    if-eqz v0, :cond_0

    .line 350
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget-object v1, p0, Lcom/android/keyguard/CarrierText;->mCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->removeCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 352
    :cond_0
    return-void
.end method

.method protected onFinishInflate()V
    .locals 3

    .line 322
    invoke-super {p0}, Landroid/widget/TextView;->onFinishInflate()V

    .line 323
    invoke-virtual {p0}, Lcom/android/keyguard/CarrierText;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x10402ee

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/keyguard/CarrierText;->mSeparator:Ljava/lang/CharSequence;

    .line 325
    iget-object v0, p0, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->isDeviceInteractive()Z

    move-result v0

    .line 326
    .local v0, "shouldMarquee":Z
    invoke-virtual {p0, v0}, Lcom/android/keyguard/CarrierText;->setSelected(Z)V

    .line 329
    const/4 v1, 0x2

    const/4 v2, 0x0

    invoke-virtual {p0, v1, v2}, Lcom/android/keyguard/CarrierText;->setLayerType(ILandroid/graphics/Paint;)V

    .line 330
    return-void
.end method

.method protected onVisibilityChanged(Landroid/view/View;I)V
    .locals 1
    .param p1, "changedView"    # Landroid/view/View;
    .param p2, "visibility"    # I

    .line 356
    invoke-super {p0, p1, p2}, Landroid/widget/TextView;->onVisibilityChanged(Landroid/view/View;I)V

    .line 359
    if-nez p2, :cond_0

    .line 360
    sget-object v0, Landroid/text/TextUtils$TruncateAt;->MARQUEE:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {p0, v0}, Lcom/android/keyguard/CarrierText;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    goto :goto_0

    .line 362
    :cond_0
    sget-object v0, Landroid/text/TextUtils$TruncateAt;->END:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {p0, v0}, Lcom/android/keyguard/CarrierText;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 364
    :goto_0
    return-void
.end method

.method public setDisplayFlags(I)V
    .locals 0
    .param p1, "flags"    # I

    .line 121
    iput p1, p0, Lcom/android/keyguard/CarrierText;->mFlags:I

    .line 122
    return-void
.end method

.method protected updateCarrierText()V
    .locals 18

    .line 219
    move-object/from16 v6, p0

    const/4 v0, 0x0

    .line 220
    .local v0, "anySimReadyAndInService":Z
    const/4 v7, 0x0

    .line 222
    .local v7, "displayText":Ljava/lang/CharSequence;
    const/4 v8, 0x0

    .line 223
    .local v8, "hnbName":Ljava/lang/CharSequence;
    const/4 v9, 0x0

    .line 225
    .local v9, "csgId":Ljava/lang/CharSequence;
    invoke-direct/range {p0 .. p0}, Lcom/android/keyguard/CarrierText;->isWifiOnlyDevice()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 226
    const-string v1, "CarrierText"

    const-string v2, "updateCarrierText() - WifiOnly deivce, not show carrier text."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 227
    const-string v1, ""

    invoke-virtual {v6, v1}, Lcom/android/keyguard/CarrierText;->setText(Ljava/lang/CharSequence;)V

    .line 228
    return-void

    .line 231
    :cond_0
    invoke-direct/range {p0 .. p0}, Lcom/android/keyguard/CarrierText;->showOrHideCarrier()Z

    move-result v10

    .line 233
    .local v10, "allSimsMissing":Z
    const/4 v11, 0x0

    move v1, v0

    move v0, v11

    .local v0, "phoneId":I
    .local v1, "anySimReadyAndInService":Z
    :goto_0
    move v12, v0

    .end local v0    # "phoneId":I
    .local v12, "phoneId":I
    iget v0, v6, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    if-ge v12, v0, :cond_8

    .line 234
    invoke-static {v12}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v13

    .line 235
    .local v13, "subId":I
    iget-object v0, v6, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v0, v12}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v14

    .line 237
    .local v14, "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    iget-object v0, v6, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v0, v13}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSubscriptionInfoForSubId(I)Landroid/telephony/SubscriptionInfo;

    move-result-object v15

    .line 238
    .local v15, "subInfo":Landroid/telephony/SubscriptionInfo;
    const/16 v16, 0x0

    if-nez v15, :cond_1

    move-object/from16 v3, v16

    goto :goto_1

    :cond_1
    invoke-virtual {v15}, Landroid/telephony/SubscriptionInfo;->getCarrierName()Ljava/lang/CharSequence;

    move-result-object v0

    move-object v3, v0

    .line 240
    .local v3, "carrierName":Ljava/lang/CharSequence;
    :goto_1
    sget-object v0, Lcom/android/internal/telephony/IccCardConstants$State;->READY:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v14, v0, :cond_4

    .line 241
    iget-object v0, v6, Lcom/android/keyguard/CarrierText;->mKeyguardUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget-object v0, v0, Lcom/android/keyguard/KeyguardUpdateMonitor;->mServiceStates:Ljava/util/HashMap;

    invoke-static {v13}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/ServiceState;

    .line 242
    .local v0, "ss":Landroid/telephony/ServiceState;
    if-eqz v0, :cond_4

    invoke-virtual {v0}, Landroid/telephony/ServiceState;->getDataRegState()I

    move-result v2

    if-nez v2, :cond_4

    .line 245
    invoke-virtual {v0}, Landroid/telephony/ServiceState;->getRilDataRadioTechnology()I

    move-result v2

    const/16 v4, 0x12

    if-ne v2, v4, :cond_2

    iget-object v2, v6, Lcom/android/keyguard/CarrierText;->mWifiManager:Landroid/net/wifi/WifiManager;

    .line 246
    invoke-virtual {v2}, Landroid/net/wifi/WifiManager;->isWifiEnabled()Z

    move-result v2

    if-eqz v2, :cond_4

    iget-object v2, v6, Lcom/android/keyguard/CarrierText;->mWifiManager:Landroid/net/wifi/WifiManager;

    .line 247
    invoke-virtual {v2}, Landroid/net/wifi/WifiManager;->getConnectionInfo()Landroid/net/wifi/WifiInfo;

    move-result-object v2

    if-eqz v2, :cond_4

    iget-object v2, v6, Lcom/android/keyguard/CarrierText;->mWifiManager:Landroid/net/wifi/WifiManager;

    .line 248
    invoke-virtual {v2}, Landroid/net/wifi/WifiManager;->getConnectionInfo()Landroid/net/wifi/WifiInfo;

    move-result-object v2

    invoke-virtual {v2}, Landroid/net/wifi/WifiInfo;->getBSSID()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_4

    .line 249
    :cond_2
    sget-boolean v2, Lcom/android/keyguard/CarrierText;->DEBUG:Z

    if-eqz v2, :cond_3

    .line 250
    const-string v2, "CarrierText"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "SIM ready and in service: subId="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v13}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", ss="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 252
    :cond_3
    const/4 v0, 0x1

    .line 257
    .end local v1    # "anySimReadyAndInService":Z
    .local v0, "anySimReadyAndInService":Z
    move/from16 v17, v0

    goto :goto_2

    .end local v0    # "anySimReadyAndInService":Z
    .restart local v1    # "anySimReadyAndInService":Z
    :cond_4
    move/from16 v17, v1

    .line 258
    .end local v1    # "anySimReadyAndInService":Z
    .local v17, "anySimReadyAndInService":Z
    :goto_2
    move-object v0, v6

    move v1, v12

    move-object v2, v14

    move-object v4, v8

    move-object v5, v9

    invoke-direct/range {v0 .. v5}, Lcom/android/keyguard/CarrierText;->getCarrierTextForSimState(ILcom/android/internal/telephony/IccCardConstants$State;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    .line 260
    .local v0, "carrierTextForSimState":Ljava/lang/CharSequence;
    if-eqz v0, :cond_6

    .line 262
    iget-object v1, v6, Lcom/android/keyguard/CarrierText;->mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;

    .line 263
    invoke-interface {v0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v2

    sget-object v4, Lcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;->DELSIM:Lcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;

    iget-object v5, v6, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    .line 262
    invoke-interface {v1, v2, v12, v4, v5}, Lcom/mediatek/keyguard/ext/IOperatorSIMString;->getOperatorSIMString(Ljava/lang/String;ILcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;Landroid/content/Context;)Ljava/lang/String;

    move-result-object v0

    .line 266
    if-eqz v0, :cond_5

    .line 267
    iget-object v1, v6, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    .line 268
    invoke-interface {v0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v2

    .line 267
    invoke-interface {v1, v2}, Lcom/mediatek/keyguard/ext/ICarrierTextExt;->customizeCarrierTextCapital(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v1

    .line 268
    invoke-interface {v1}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_3

    .line 270
    :cond_5
    const/4 v0, 0x0

    .line 274
    :cond_6
    :goto_3
    if-eqz v0, :cond_7

    .line 275
    iget-object v1, v6, Lcom/android/keyguard/CarrierText;->mCarrier:[Ljava/lang/String;

    invoke-interface {v0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v12

    goto :goto_4

    .line 277
    :cond_7
    iget-object v1, v6, Lcom/android/keyguard/CarrierText;->mCarrier:[Ljava/lang/String;

    aput-object v16, v1, v12

    .line 233
    .end local v0    # "carrierTextForSimState":Ljava/lang/CharSequence;
    .end local v3    # "carrierName":Ljava/lang/CharSequence;
    .end local v13    # "subId":I
    .end local v14    # "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    .end local v15    # "subInfo":Landroid/telephony/SubscriptionInfo;
    :goto_4
    add-int/lit8 v0, v12, 0x1

    .end local v12    # "phoneId":I
    .local v0, "phoneId":I
    move/from16 v1, v17

    goto/16 :goto_0

    .line 282
    .end local v0    # "phoneId":I
    .end local v17    # "anySimReadyAndInService":Z
    .restart local v1    # "anySimReadyAndInService":Z
    :cond_8
    const/4 v0, 0x0

    .line 283
    .local v0, "carrierFinalContent":Ljava/lang/String;
    iget-object v2, v6, Lcom/android/keyguard/CarrierText;->mCarrierTextExt:Lcom/mediatek/keyguard/ext/ICarrierTextExt;

    sget-object v3, Lcom/android/keyguard/CarrierText;->mSeparator:Ljava/lang/CharSequence;

    invoke-interface {v3}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-interface {v2, v3}, Lcom/mediatek/keyguard/ext/ICarrierTextExt;->customizeCarrierTextDivider(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 284
    .local v2, "divider":Ljava/lang/String;
    nop

    .local v11, "i":I
    :goto_5
    move v3, v11

    .end local v11    # "i":I
    .local v3, "i":I
    iget v4, v6, Lcom/android/keyguard/CarrierText;->mNumOfPhone:I

    if-ge v3, v4, :cond_b

    .line 286
    iget-object v4, v6, Lcom/android/keyguard/CarrierText;->mCarrierNeedToShow:[Z

    aget-boolean v4, v4, v3

    if-eqz v4, :cond_a

    iget-object v4, v6, Lcom/android/keyguard/CarrierText;->mCarrier:[Ljava/lang/String;

    aget-object v4, v4, v3

    if-eqz v4, :cond_a

    .line 287
    if-nez v0, :cond_9

    .line 289
    iget-object v4, v6, Lcom/android/keyguard/CarrierText;->mCarrier:[Ljava/lang/String;

    aget-object v0, v4, v3

    goto :goto_6

    .line 291
    :cond_9
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 292
    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, v6, Lcom/android/keyguard/CarrierText;->mCarrier:[Ljava/lang/String;

    aget-object v5, v5, v3

    .line 293
    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 284
    :cond_a
    :goto_6
    add-int/lit8 v11, v3, 0x1

    .end local v3    # "i":I
    .restart local v11    # "i":I
    goto :goto_5

    .line 301
    .end local v11    # "i":I
    :cond_b
    if-nez v1, :cond_c

    iget-object v3, v6, Lcom/android/keyguard/CarrierText;->mContext:Landroid/content/Context;

    invoke-static {v3}, Lcom/android/settingslib/WirelessUtils;->isAirplaneModeOn(Landroid/content/Context;)Z

    move-result v3

    if-eqz v3, :cond_c

    .line 302
    invoke-virtual/range {p0 .. p0}, Lcom/android/keyguard/CarrierText;->getContext()Landroid/content/Context;

    move-result-object v3

    const v4, 0x7f110112

    invoke-virtual {v3, v4}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    .line 305
    :cond_c
    const-string v3, "CarrierText"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "updateCarrierText() - after combination, carrierFinalContent = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, ", allSimsMissing = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v10}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 307
    invoke-virtual {v6, v0}, Lcom/android/keyguard/CarrierText;->setText(Ljava/lang/CharSequence;)V

    .line 308
    return-void
.end method
