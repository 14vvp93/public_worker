.class public Lcom/mediatek/systemui/statusbar/util/SIMHelper;
.super Ljava/lang/Object;
.source "SIMHelper.java"


# static fields
.field private static final CT_NUMERIC:[Ljava/lang/String;

.field public static sContext:Landroid/content/Context;

.field private static sSimInfos:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/telephony/SubscriptionInfo;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 60
    const-string v0, "45502"

    const-string v1, "45507"

    const-string v2, "46003"

    const-string v3, "46011"

    const-string v4, "46012"

    const-string v5, "46013"

    filled-new-array/range {v0 .. v5}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->CT_NUMERIC:[Ljava/lang/String;

    return-void
.end method

.method public static getFirstSubInSlot(I)I
    .locals 4
    .param p0, "slotId"    # I

    .line 92
    invoke-static {p0}, Landroid/telephony/SubscriptionManager;->getSubId(I)[I

    move-result-object v0

    .line 93
    .local v0, "subIds":[I
    if-eqz v0, :cond_0

    array-length v1, v0

    if-lez v1, :cond_0

    .line 94
    const/4 v1, 0x0

    aget v1, v0, v1

    return v1

    .line 96
    :cond_0
    const-string v1, "SIMHelper"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Cannot get first sub in slot: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 97
    const/4 v1, -0x1

    return v1
.end method

.method public static getMainPhoneId()I
    .locals 5

    .line 189
    const/4 v0, -0x1

    .line 190
    .local v0, "mainPhoneId":I
    const-string v1, "persist.vendor.radio.simswitch"

    const-string v2, "1"

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 191
    .local v1, "curr3GSim":Ljava/lang/String;
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 192
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 193
    .local v2, "curr3GPhoneId":I
    add-int/lit8 v0, v2, -0x1

    .line 195
    .end local v2    # "curr3GPhoneId":I
    :cond_0
    const-string v2, "@M_SIMHelper"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "getMainPhoneId, mainPhoneId = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 196
    return v0
.end method

.method public static getSlotCount()I
    .locals 1

    .line 122
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getPhoneCount()I

    move-result v0

    return v0
.end method

.method public static isCTCardType(I)Z
    .locals 5
    .param p0, "slotId"    # I

    .line 244
    const/4 v0, 0x0

    .line 246
    .local v0, "ctCard":Z
    invoke-static {}, Lcom/mediatek/telephony/MtkTelephonyManagerEx;->getDefault()Lcom/mediatek/telephony/MtkTelephonyManagerEx;

    move-result-object v1

    invoke-virtual {v1, p0}, Lcom/mediatek/telephony/MtkTelephonyManagerEx;->getCdmaCardType(I)Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;

    move-result-object v1

    .line 247
    .local v1, "cardType":Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;
    sget-object v2, Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;->CT_4G_UICC_CARD:Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;

    invoke-virtual {v2, v1}, Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    sget-object v2, Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;->CT_3G_UIM_CARD:Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;

    .line 248
    invoke-virtual {v2, v1}, Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    sget-object v2, Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;->CT_UIM_SIM_CARD:Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;

    .line 249
    invoke-virtual {v2, v1}, Lcom/mediatek/internal/telephony/MtkIccCardConstants$CardType;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 250
    :cond_0
    const/4 v0, 0x1

    .line 252
    :cond_1
    sget-boolean v2, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->LOG_ENABLE:Z

    if-eqz v2, :cond_2

    .line 253
    const-string v2, "@M_SIMHelper"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isCTCardType, ctCard = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v4, ", soltId = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 256
    :cond_2
    return v0
.end method

.method public static isCtSim(I)Z
    .locals 7
    .param p0, "subId"    # I

    .line 229
    const/4 v0, 0x0

    .line 230
    .local v0, "ctSim":Z
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v1

    invoke-virtual {v1, p0}, Landroid/telephony/TelephonyManager;->getSimOperator(I)Ljava/lang/String;

    move-result-object v1

    .line 231
    .local v1, "numeric":Ljava/lang/String;
    sget-object v2, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->CT_NUMERIC:[Ljava/lang/String;

    array-length v3, v2

    const/4 v4, 0x0

    :goto_0
    if-ge v4, v3, :cond_1

    aget-object v5, v2, v4

    .line 232
    .local v5, "ct":Ljava/lang/String;
    invoke-virtual {v5, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_0

    .line 233
    const/4 v0, 0x1

    .line 234
    goto :goto_1

    .line 231
    .end local v5    # "ct":Ljava/lang/String;
    :cond_0
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 237
    :cond_1
    :goto_1
    sget-boolean v2, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->LOG_ENABLE:Z

    if-eqz v2, :cond_2

    .line 238
    const-string v2, "@M_SIMHelper"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isCtSim, ctSim = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v4, ", subId = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 240
    :cond_2
    return v0
.end method

.method public static isRadioOn(I)Z
    .locals 4
    .param p0, "subId"    # I

    .line 176
    const-string v0, "phone"

    .line 177
    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    .line 176
    invoke-static {v0}, Lcom/android/internal/telephony/ITelephony$Stub;->asInterface(Landroid/os/IBinder;)Lcom/android/internal/telephony/ITelephony;

    move-result-object v0

    .line 179
    .local v0, "telephony":Lcom/android/internal/telephony/ITelephony;
    if-eqz v0, :cond_0

    .line 180
    :try_start_0
    sget-object v1, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->sContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, p0, v1}, Lcom/android/internal/telephony/ITelephony;->isRadioOnForSubscriber(ILjava/lang/String;)Z

    move-result v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v1

    .line 182
    :catch_0
    move-exception v1

    .line 183
    .local v1, "e":Landroid/os/RemoteException;
    const-string v2, "SIMHelper"

    const-string v3, "mTelephony exception"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .end local v1    # "e":Landroid/os/RemoteException;
    goto :goto_0

    .line 184
    :cond_0
    nop

    .line 185
    :goto_0
    const/4 v1, 0x0

    return v1
.end method

.method public static isSecondaryCSIMForMixedVolte(I)Z
    .locals 7
    .param p0, "subId"    # I

    .line 208
    const/4 v0, 0x0

    .line 209
    .local v0, "ret":Z
    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->getMainPhoneId()I

    move-result v1

    .line 210
    .local v1, "mainPhoneId":I
    invoke-static {v1}, Lcom/mediatek/internal/telephony/MtkSubscriptionManager;->getSubIdUsingPhoneId(I)I

    move-result v2

    .line 211
    .local v2, "mainPhoneSubId":I
    invoke-static {p0}, Landroid/telephony/SubscriptionManager;->getSlotIndex(I)I

    move-result v3

    .line 213
    .local v3, "currentSoltId":I
    if-eq v1, v3, :cond_2

    .line 215
    invoke-static {v1}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isCTCardType(I)Z

    move-result v4

    if-nez v4, :cond_0

    invoke-static {v2}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isCtSim(I)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 217
    :cond_0
    invoke-static {v3}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isCTCardType(I)Z

    move-result v4

    if-nez v4, :cond_1

    invoke-static {p0}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isCtSim(I)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 218
    :cond_1
    const/4 v0, 0x1

    .line 221
    :cond_2
    sget-boolean v4, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->LOG_ENABLE:Z

    if-eqz v4, :cond_3

    .line 222
    const-string v4, "@M_SIMHelper"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "isSecondaryCSIMForMixedVolte, ret = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v6, ", subId = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 225
    :cond_3
    return v0
.end method

.method public static isWifiOnlyDevice()Z
    .locals 2

    .line 150
    sget-object v0, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->sContext:Landroid/content/Context;

    sget-object v1, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->sContext:Landroid/content/Context;

    const-string v1, "connectivity"

    .line 151
    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    .line 152
    .local v0, "cm":Landroid/net/ConnectivityManager;
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/net/ConnectivityManager;->isNetworkSupported(I)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    return v1
.end method

.method public static setContext(Landroid/content/Context;)V
    .locals 0
    .param p0, "context"    # Landroid/content/Context;

    .line 145
    sput-object p0, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->sContext:Landroid/content/Context;

    .line 146
    return-void
.end method

.method public static updateSIMInfos(Landroid/content/Context;)V
    .locals 1
    .param p0, "context"    # Landroid/content/Context;

    .line 88
    invoke-static {p0}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfoList()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->sSimInfos:Ljava/util/List;

    .line 89
    return-void
.end method
