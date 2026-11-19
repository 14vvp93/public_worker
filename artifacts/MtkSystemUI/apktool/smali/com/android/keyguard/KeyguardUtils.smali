.class public Lcom/android/keyguard/KeyguardUtils;
.super Ljava/lang/Object;
.source "KeyguardUtils.java"


# static fields
.field private static final DEBUG:Z

.field private static mIsDismissEnabled:Z

.field private static mIsDismissSimMeLockSupport:Z

.field private static mIsMediatekSimMeLockSupport:Z

.field private static final mIsPrivacyProtectionLockSupport:Z

.field private static mIsSimmePolicyEnabled:Z

.field private static mValidPhoneIds:I

.field private static sInstance:Lcom/android/keyguard/KeyguardUtils;

.field private static sPhoneCount:I


# instance fields
.field private mSubscriptionManager:Landroid/telephony/SubscriptionManager;

.field private mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 44
    sget-boolean v0, Lcom/android/keyguard/KeyguardConstants;->DEBUG:Z

    sput-boolean v0, Lcom/android/keyguard/KeyguardUtils;->DEBUG:Z

    .line 50
    const-string v0, "ro.vendor.mtk_privacy_protection_lock"

    .line 51
    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsPrivacyProtectionLockSupport:Z

    .line 56
    new-instance v0, Lcom/android/keyguard/KeyguardUtils;

    invoke-direct {v0}, Lcom/android/keyguard/KeyguardUtils;-><init>()V

    sput-object v0, Lcom/android/keyguard/KeyguardUtils;->sInstance:Lcom/android/keyguard/KeyguardUtils;

    .line 121
    const-string v0, "ro.vendor.sim_me_lock_mode"

    const-string v1, "0"

    .line 122
    invoke-static {v0, v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "0"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsMediatekSimMeLockSupport:Z

    .line 124
    const/4 v0, 0x0

    sput v0, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    .line 130
    sput-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsDismissEnabled:Z

    .line 131
    sput-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsSimmePolicyEnabled:Z

    .line 132
    const/4 v1, -0x1

    sput v1, Lcom/android/keyguard/KeyguardUtils;->mValidPhoneIds:I

    .line 133
    sput-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsDismissSimMeLockSupport:Z

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 67
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 68
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 62
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 63
    invoke-static {p1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iput-object v0, p0, Lcom/android/keyguard/KeyguardUtils;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 64
    invoke-static {p1}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v0

    iput-object v0, p0, Lcom/android/keyguard/KeyguardUtils;->mSubscriptionManager:Landroid/telephony/SubscriptionManager;

    .line 65
    return-void
.end method

.method public static getDefault()Lcom/android/keyguard/KeyguardUtils;
    .locals 1

    .line 71
    sget-object v0, Lcom/android/keyguard/KeyguardUtils;->sInstance:Lcom/android/keyguard/KeyguardUtils;

    return-object v0
.end method

.method public static getNumOfPhone()I
    .locals 2

    .line 171
    sget v0, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    if-nez v0, :cond_1

    .line 172
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getPhoneCount()I

    move-result v0

    sput v0, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    .line 174
    sget v0, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    const/4 v1, 0x4

    if-le v0, v1, :cond_0

    goto :goto_0

    :cond_0
    sget v1, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    :goto_0
    sput v1, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    .line 176
    :cond_1
    sget v0, Lcom/android/keyguard/KeyguardUtils;->sPhoneCount:I

    return v0
.end method

.method public static getPhoneIdUsingSubId(I)I
    .locals 4
    .param p0, "subId"    # I

    .line 194
    const-string v0, "KeyguardUtils"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getPhoneIdUsingSubId: subId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 195
    invoke-static {p0}, Landroid/telephony/SubscriptionManager;->getPhoneId(I)I

    move-result v0

    .line 196
    .local v0, "phoneId":I
    if-ltz v0, :cond_1

    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->getNumOfPhone()I

    move-result v1

    if-lt v0, v1, :cond_0

    goto :goto_0

    .line 199
    :cond_0
    const-string v1, "KeyguardUtils"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getPhoneIdUsingSubId: get phone ID = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 197
    :cond_1
    :goto_0
    const-string v1, "KeyguardUtils"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getPhoneIdUsingSubId: invalid phonId = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 202
    :goto_1
    return v0
.end method

.method public static getSubIdUsingPhoneId(I)I
    .locals 1
    .param p0, "phoneId"    # I

    .line 211
    invoke-static {p0}, Lcom/mediatek/internal/telephony/MtkSubscriptionManager;->getSubIdUsingPhoneId(I)I

    move-result v0

    .line 212
    .local v0, "subId":I
    return v0
.end method

.method public static final isDismissSimMeLockSupport()Z
    .locals 1

    .line 140
    sget-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsDismissSimMeLockSupport:Z

    return v0
.end method

.method public static isFlightModePowerOffMd()Z
    .locals 4

    .line 161
    const-string v0, "ro.vendor.mtk_flight_mode_power_off_md"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    .line 162
    .local v0, "powerOffMd":Z
    const-string v1, "KeyguardUtils"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "powerOffMd = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 163
    return v0
.end method

.method public static final isMediatekSimMeLockSupport()Z
    .locals 1

    .line 136
    sget-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsMediatekSimMeLockSupport:Z

    return v0
.end method

.method public static final isPrivacyProtectionLockSupport()Z
    .locals 1

    .line 266
    sget-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsPrivacyProtectionLockSupport:Z

    return v0
.end method

.method public static final isSimMeLockValid(I)Z
    .locals 5
    .param p0, "phoneId"    # I

    .line 281
    sget v0, Lcom/android/keyguard/KeyguardUtils;->mValidPhoneIds:I

    const/4 v1, 0x1

    const/4 v2, -0x1

    if-ne v0, v2, :cond_0

    .line 282
    return v1

    .line 284
    :cond_0
    sget v0, Lcom/android/keyguard/KeyguardUtils;->mValidPhoneIds:I

    shl-int v2, v1, p0

    and-int/2addr v0, v2

    .line 285
    .local v0, "validMask":I
    const-string v2, "KeyguardUtils"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isSimMeLockValid phoneId="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", result="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 286
    shl-int v2, v1, p0

    if-ne v2, v0, :cond_1

    return v1

    .line 287
    :cond_1
    const/4 v1, 0x0

    return v1
.end method

.method public static isSystemEncrypted()Z
    .locals 7

    .line 233
    const/4 v0, 0x1

    .line 234
    .local v0, "bRet":Z
    const-string v1, "ro.crypto.type"

    invoke-static {v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 235
    .local v1, "cryptoType":Ljava/lang/String;
    const-string v2, "ro.crypto.state"

    invoke-static {v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 236
    .local v2, "state":Ljava/lang/String;
    const-string/jumbo v3, "vold.decrypt"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 237
    .local v3, "decrypt":Ljava/lang/String;
    const-string v4, "unsupported"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_0

    .line 238
    const/4 v4, 0x0

    return v4

    .line 241
    :cond_0
    const-string v4, "unencrypted"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 242
    const-string v4, ""

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 243
    const/4 v0, 0x0

    goto :goto_0

    .line 245
    :cond_1
    const-string v4, ""

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    goto :goto_0

    .line 247
    :cond_2
    const-string v4, "encrypted"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 248
    const-string v4, "file"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3

    .line 249
    const/4 v0, 0x0

    goto :goto_0

    .line 250
    :cond_3
    const-string v4, "block"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 251
    const-string v4, "trigger_restart_framework"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 252
    const/4 v0, 0x0

    .line 258
    :cond_4
    :goto_0
    sget-boolean v4, Lcom/android/keyguard/KeyguardUtils;->DEBUG:Z

    if-eqz v4, :cond_5

    .line 259
    const-string v4, "KeyguardUtils"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "cryptoType="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " ro.crypto.state="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " vold.decrypt="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " sysEncrypted="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 262
    :cond_5
    return v0
.end method

.method public static isValidPhoneId(I)Z
    .locals 1
    .param p0, "phoneId"    # I

    .line 185
    const v0, 0x7fffffff

    if-eq p0, v0, :cond_0

    if-ltz p0, :cond_0

    .line 186
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->getNumOfPhone()I

    move-result v0

    if-ge p0, v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 185
    :goto_0
    return v0
.end method

.method private final parseValueFromConfig(Landroid/content/Context;Ljava/lang/String;I)I
    .locals 7
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "configName"    # Ljava/lang/String;
    .param p3, "lockPolicy"    # I

    .line 335
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "string"

    .line 336
    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v3

    .line 335
    invoke-virtual {v0, v1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 337
    .local v0, "resourceId":I
    const/4 v1, -0x1

    if-nez v0, :cond_0

    .line 338
    const-string v2, "KeyguardUtils"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Cannot get valid source id for "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 339
    return v1

    .line 341
    :cond_0
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    .line 342
    .local v2, "configValueString":Ljava/lang/String;
    const-string v3, "KeyguardUtils"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Get config for "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", value="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 343
    if-eqz v2, :cond_1

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v3

    if-nez v3, :cond_1

    .line 346
    :try_start_0
    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v1, v3

    .line 350
    .local v1, "configInt":I
    goto :goto_0

    .line 347
    .end local v1    # "configInt":I
    :catch_0
    move-exception v3

    .line 348
    .local v3, "e":Ljava/lang/Exception;
    const-string v4, "KeyguardUtils"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Exception happened: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 349
    nop

    .line 351
    .end local v3    # "e":Ljava/lang/Exception;
    .restart local v1    # "configInt":I
    :goto_0
    return v1

    .line 353
    .end local v1    # "configInt":I
    :cond_1
    return v1
.end method

.method public static requestImeStatusRefresh(Landroid/content/Context;)V
    .locals 3
    .param p0, "context"    # Landroid/content/Context;

    .line 220
    const-string v0, "input_method"

    .line 221
    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/inputmethod/InputMethodManager;

    .line 222
    .local v0, "imm":Landroid/view/inputmethod/InputMethodManager;
    if-eqz v0, :cond_0

    .line 223
    sget-boolean v1, Lcom/android/keyguard/KeyguardUtils;->DEBUG:Z

    if-eqz v1, :cond_0

    .line 224
    const-string v1, "KeyguardUtils"

    const-string v2, "call imm.requestImeStatusRefresh()"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 229
    :cond_0
    return-void
.end method


# virtual methods
.method public getOptrBitmapUsingPhoneId(ILandroid/content/Context;)Landroid/graphics/Bitmap;
    .locals 5
    .param p1, "phoneId"    # I
    .param p2, "context"    # Landroid/content/Context;

    .line 105
    invoke-static {p1}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v0

    .line 106
    .local v0, "subId":I
    const/4 v1, 0x0

    .line 107
    .local v1, "bgBitmap":Landroid/graphics/Bitmap;
    iget-object v2, p0, Lcom/android/keyguard/KeyguardUtils;->mSubscriptionManager:Landroid/telephony/SubscriptionManager;

    invoke-virtual {v2, v0}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfo(I)Landroid/telephony/SubscriptionInfo;

    move-result-object v2

    .line 108
    .local v2, "info":Landroid/telephony/SubscriptionInfo;
    if-nez v2, :cond_0

    .line 109
    sget-boolean v3, Lcom/android/keyguard/KeyguardUtils;->DEBUG:Z

    if-eqz v3, :cond_1

    .line 110
    const-string v3, "KeyguardUtils"

    const-string v4, "getOptrBitmapUsingPhoneId, return null"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 113
    :cond_0
    invoke-virtual {v2, p2}, Landroid/telephony/SubscriptionInfo;->createIconBitmap(Landroid/content/Context;)Landroid/graphics/Bitmap;

    move-result-object v1

    .line 115
    :cond_1
    :goto_0
    return-object v1
.end method

.method public getOptrNameUsingPhoneId(ILandroid/content/Context;)Ljava/lang/String;
    .locals 5
    .param p1, "phoneId"    # I
    .param p2, "context"    # Landroid/content/Context;

    .line 81
    invoke-static {p1}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v0

    .line 82
    .local v0, "subId":I
    iget-object v1, p0, Lcom/android/keyguard/KeyguardUtils;->mSubscriptionManager:Landroid/telephony/SubscriptionManager;

    invoke-virtual {v1, v0}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfo(I)Landroid/telephony/SubscriptionInfo;

    move-result-object v1

    .line 83
    .local v1, "info":Landroid/telephony/SubscriptionInfo;
    if-nez v1, :cond_0

    .line 84
    sget-boolean v2, Lcom/android/keyguard/KeyguardUtils;->DEBUG:Z

    if-eqz v2, :cond_2

    .line 85
    const-string v2, "KeyguardUtils"

    const-string v3, "getOptrNameUsingPhoneId, return null"

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 88
    :cond_0
    sget-boolean v2, Lcom/android/keyguard/KeyguardUtils;->DEBUG:Z

    if-eqz v2, :cond_1

    .line 89
    const-string v2, "KeyguardUtils"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "getOptrNameUsingPhoneId mDisplayName="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Landroid/telephony/SubscriptionInfo;->getDisplayName()Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 91
    :cond_1
    invoke-virtual {v1}, Landroid/telephony/SubscriptionInfo;->getDisplayName()Ljava/lang/CharSequence;

    move-result-object v2

    if-eqz v2, :cond_2

    .line 92
    invoke-virtual {v1}, Landroid/telephony/SubscriptionInfo;->getDisplayName()Ljava/lang/CharSequence;

    move-result-object v2

    invoke-interface {v2}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v2

    return-object v2

    .line 95
    :cond_2
    :goto_0
    const/4 v2, 0x0

    return-object v2
.end method

.method public final initSimmePolicy(Landroid/content/Context;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;

    .line 291
    sget-boolean v0, Lcom/android/keyguard/KeyguardUtils;->mIsMediatekSimMeLockSupport:Z

    if-eqz v0, :cond_0

    .line 292
    return-void

    .line 295
    :cond_0
    const-string v0, "1"

    .line 296
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f110548

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    .line 295
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    .line 297
    const-string v0, "KeyguardUtils"

    const-string v2, "config enabled for simme dependency"

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 298
    sput-boolean v1, Lcom/android/keyguard/KeyguardUtils;->mIsSimmePolicyEnabled:Z

    goto :goto_0

    .line 300
    :cond_1
    new-instance v0, Ljava/io/File;

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v2

    const-string v3, "SimmeLock"

    invoke-direct {v0, v2, v3}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 302
    .local v0, "simmeOption":Ljava/io/File;
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v2

    sput-boolean v2, Lcom/android/keyguard/KeyguardUtils;->mIsSimmePolicyEnabled:Z

    .line 306
    .end local v0    # "simmeOption":Ljava/io/File;
    :goto_0
    const-string v0, "1"

    .line 307
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f110227

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    .line 306
    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 308
    const-string v0, "KeyguardUtils"

    const-string v2, "config enabled for simme dismiss"

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 309
    sput-boolean v1, Lcom/android/keyguard/KeyguardUtils;->mIsDismissEnabled:Z

    goto :goto_1

    .line 311
    :cond_2
    new-instance v0, Ljava/io/File;

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v2

    const-string v3, "SimmeDismiss"

    invoke-direct {v0, v2, v3}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 313
    .local v0, "dismissOption":Ljava/io/File;
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v2

    sput-boolean v2, Lcom/android/keyguard/KeyguardUtils;->mIsDismissEnabled:Z

    .line 317
    .end local v0    # "dismissOption":Ljava/io/File;
    :goto_1
    invoke-static {}, Lcom/mediatek/telephony/MtkTelephonyManagerEx;->getDefault()Lcom/mediatek/telephony/MtkTelephonyManagerEx;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mediatek/telephony/MtkTelephonyManagerEx;->getSimLockPolicy()I

    move-result v0

    .line 318
    .local v0, "lockPolicy":I
    const/4 v2, 0x0

    if-lez v0, :cond_3

    move v3, v1

    goto :goto_2

    :cond_3
    move v3, v2

    :goto_2
    sput-boolean v3, Lcom/android/keyguard/KeyguardUtils;->mIsMediatekSimMeLockSupport:Z

    .line 319
    if-gtz v0, :cond_4

    return-void

    .line 320
    :cond_4
    sget-boolean v3, Lcom/android/keyguard/KeyguardUtils;->mIsSimmePolicyEnabled:Z

    if-eqz v3, :cond_5

    .line 321
    const-string v3, "simme_lock_with_request_"

    invoke-direct {p0, p1, v3, v0}, Lcom/android/keyguard/KeyguardUtils;->parseValueFromConfig(Landroid/content/Context;Ljava/lang/String;I)I

    move-result v3

    sput v3, Lcom/android/keyguard/KeyguardUtils;->mValidPhoneIds:I

    .line 322
    sget v3, Lcom/android/keyguard/KeyguardUtils;->mValidPhoneIds:I

    const/16 v4, 0xf

    if-le v3, v4, :cond_5

    .line 323
    const/4 v3, -0x1

    sput v3, Lcom/android/keyguard/KeyguardUtils;->mValidPhoneIds:I

    .line 327
    :cond_5
    sget-boolean v3, Lcom/android/keyguard/KeyguardUtils;->mIsDismissEnabled:Z

    if-eqz v3, :cond_6

    .line 328
    sput-boolean v1, Lcom/android/keyguard/KeyguardUtils;->mIsDismissSimMeLockSupport:Z

    .line 329
    const-string v1, "dismiss_button_with_request_"

    invoke-direct {p0, p1, v1, v0}, Lcom/android/keyguard/KeyguardUtils;->parseValueFromConfig(Landroid/content/Context;Ljava/lang/String;I)I

    move-result v1

    if-nez v1, :cond_6

    .line 330
    sput-boolean v2, Lcom/android/keyguard/KeyguardUtils;->mIsDismissSimMeLockSupport:Z

    .line 333
    :cond_6
    return-void
.end method
