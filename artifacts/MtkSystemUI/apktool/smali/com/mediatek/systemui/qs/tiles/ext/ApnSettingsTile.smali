.class public Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "ApnSettingsTile.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/systemui/qs/tileimpl/QSTileImpl<",
        "Lcom/android/systemui/plugins/qs/QSTile$BooleanState;",
        ">;"
    }
.end annotation


# static fields
.field private static final APN_SETTINGS:Landroid/content/Intent;


# instance fields
.field private mApnSettingsEnabled:Z

.field private mApnStateLabel:Ljava/lang/String;

.field private final mDisableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private final mEnableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private mIsAirplaneMode:Z

.field private mIsWifiOnly:Z

.field private mListening:Z

.field private final mPhoneStateListener:Landroid/telephony/PhoneStateListener;

.field private mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

.field private final mReceiver:Landroid/content/BroadcastReceiver;

.field private final mSubscriptionManager:Landroid/telephony/SubscriptionManager;

.field private mTileLabel:Ljava/lang/CharSequence;

.field private final mUm:Landroid/os/UserManager;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    .line 40
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "com.android.settings"

    const-string v3, "com.android.settings.Settings$ApnSettingsActivity"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    move-result-object v0

    sput-object v0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->APN_SETTINGS:Landroid/content/Intent;

    return-void
.end method

.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 3
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 63
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 44
    new-instance v0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mEnableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 45
    new-instance v0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mDisableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 47
    const-string v0, ""

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    .line 49
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    .line 56
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    .line 227
    new-instance v1, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$1;

    invoke-direct {v1, p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$1;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 251
    new-instance v1, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$2;

    invoke-direct {v1, p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile$2;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mPhoneStateListener:Landroid/telephony/PhoneStateListener;

    .line 64
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    .line 65
    invoke-static {v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v2}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    .line 67
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    invoke-static {v1}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mSubscriptionManager:Landroid/telephony/SubscriptionManager;

    .line 68
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    const-string/jumbo v2, "user"

    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/UserManager;

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mUm:Landroid/os/UserManager;

    .line 70
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    const-string v2, "connectivity"

    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/net/ConnectivityManager;

    .line 72
    .local v1, "cm":Landroid/net/ConnectivityManager;
    invoke-virtual {v1, v0}, Landroid/net/ConnectivityManager;->isNetworkSupported(I)Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mIsWifiOnly:Z

    .line 74
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->updateState()V

    .line 77
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    .line 36
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->updateState()V

    return-void
.end method

.method private isAllRadioOff()Z
    .locals 6

    .line 214
    const/4 v0, 0x1

    .line 215
    .local v0, "result":Z
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mSubscriptionManager:Landroid/telephony/SubscriptionManager;

    invoke-virtual {v1}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionIdList()[I

    move-result-object v1

    .line 216
    .local v1, "subIds":[I
    if-eqz v1, :cond_1

    array-length v2, v1

    if-lez v2, :cond_1

    .line 217
    array-length v2, v1

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v2, :cond_1

    aget v4, v1, v3

    .line 218
    .local v4, "subId":I
    invoke-static {v4}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isRadioOn(I)Z

    move-result v5

    if-eqz v5, :cond_0

    .line 219
    const/4 v0, 0x0

    .line 220
    goto :goto_1

    .line 217
    .end local v4    # "subId":I
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 224
    :cond_1
    :goto_1
    return v0
.end method

.method private final updateState()V
    .locals 9

    .line 161
    const/4 v0, 0x0

    .line 162
    .local v0, "enabled":Z
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "airplane_mode_on"

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Landroid/provider/Settings$Global;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v1

    const/4 v2, 0x1

    if-eqz v1, :cond_0

    move v1, v2

    goto :goto_0

    :cond_0
    move v1, v3

    :goto_0
    iput-boolean v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mIsAirplaneMode:Z

    .line 166
    invoke-static {}, Landroid/os/UserHandle;->myUserId()I

    move-result v1

    if-nez v1, :cond_2

    .line 167
    invoke-static {}, Landroid/app/ActivityManager;->getCurrentUser()I

    move-result v1

    if-eqz v1, :cond_1

    goto :goto_1

    :cond_1
    move v1, v3

    goto :goto_2

    :cond_2
    :goto_1
    move v1, v2

    .line 168
    .local v1, "isSecondaryUser":Z
    :goto_2
    iget-object v4, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mUm:Landroid/os/UserManager;

    const-string v5, "no_config_mobile_networks"

    .line 169
    invoke-virtual {v4, v5}, Landroid/os/UserManager;->hasUserRestriction(Ljava/lang/String;)Z

    move-result v4

    .line 171
    .local v4, "isRestricted":Z
    iget-boolean v5, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mIsWifiOnly:Z

    if-nez v5, :cond_6

    if-nez v1, :cond_6

    if-eqz v4, :cond_3

    goto :goto_4

    .line 178
    :cond_3
    iget-object v5, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mSubscriptionManager:Landroid/telephony/SubscriptionManager;

    invoke-virtual {v5}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfoCount()I

    move-result v5

    .line 179
    .local v5, "simNum":I
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v6

    invoke-virtual {v6}, Landroid/telephony/TelephonyManager;->getCallState()I

    move-result v6

    .line 180
    .local v6, "callState":I
    if-nez v6, :cond_4

    goto :goto_3

    :cond_4
    move v2, v3

    .line 181
    .local v2, "isIdle":Z
    :goto_3
    iget-boolean v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mIsAirplaneMode:Z

    if-nez v3, :cond_5

    if-lez v5, :cond_5

    if-eqz v2, :cond_5

    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->isAllRadioOff()Z

    move-result v3

    if-nez v3, :cond_5

    .line 182
    const/4 v0, 0x1

    .line 186
    :cond_5
    const-string v3, "ApnSettingsTile"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "updateState(), mIsAirplaneMode = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v8, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mIsAirplaneMode:Z

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v8, ", simNum = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, ", callstate = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, ", isIdle = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v3, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .end local v2    # "isIdle":Z
    .end local v5    # "simNum":I
    .end local v6    # "callState":I
    goto :goto_5

    .line 172
    :cond_6
    :goto_4
    const/4 v0, 0x0

    .line 174
    const-string v2, "ApnSettingsTile"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "updateState(), isSecondaryUser = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v5, ", mIsWifiOnly = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v5, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mIsWifiOnly:Z

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v5, ", isRestricted = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 192
    :goto_5
    iput-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    .line 195
    const-string v2, "ApnSettingsTile"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "updateState(), mApnSettingsEnabled = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v5, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 198
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->updateStateResources()V

    .line 200
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->refreshState()V

    .line 201
    return-void
.end method

.method private final updateStateResources()V
    .locals 4

    .line 204
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    if-eqz v0, :cond_0

    .line 205
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    iget-boolean v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mEnableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    invoke-interface {v0, v1, v2, v3}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeApnSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    goto :goto_0

    .line 208
    :cond_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    iget-boolean v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mDisableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    invoke-interface {v0, v1, v2, v3}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeApnSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    .line 211
    :goto_0
    return-void
.end method


# virtual methods
.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 92
    const/4 v0, 0x0

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 125
    const/16 v0, 0x6f

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 86
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    const-string v1, "apnsettings"

    invoke-interface {v0, v1}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->getTileLabel(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mTileLabel:Ljava/lang/CharSequence;

    .line 87
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mTileLabel:Ljava/lang/CharSequence;

    return-object v0
.end method

.method protected handleClick()V
    .locals 4

    .line 135
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->updateState()V

    .line 136
    const-string v0, "ApnSettingsTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "handleClick(), mApnSettingsEnabled = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 137
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    if-eqz v0, :cond_0

    .line 138
    invoke-static {}, Landroid/telephony/SubscriptionManager;->getDefaultDataSubscriptionId()I

    move-result v0

    .line 139
    .local v0, "subId":I
    sget-object v1, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->APN_SETTINGS:Landroid/content/Intent;

    const-string v2, "sub_id"

    invoke-virtual {v1, v2, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 140
    const-string v1, "ApnSettingsTile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "handleClick(), "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->APN_SETTINGS:Landroid/content/Intent;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 142
    const-class v1, Lcom/android/systemui/plugins/ActivityStarter;

    invoke-static {v1}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/systemui/plugins/ActivityStarter;

    sget-object v2, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->APN_SETTINGS:Landroid/content/Intent;

    const/4 v3, 0x0

    invoke-interface {v1, v2, v3}, Lcom/android/systemui/plugins/ActivityStarter;->postStartActivityDismissingKeyguard(Landroid/content/Intent;I)V

    .line 145
    .end local v0    # "subId":I
    :cond_0
    return-void
.end method

.method protected handleLongClick()V
    .locals 0

    .line 130
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->handleClick()V

    .line 131
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 4
    .param p1, "listening"    # Z

    .line 97
    const-string v0, "ApnSettingsTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setListening(), listening = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 98
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mListening:Z

    if-ne v0, p1, :cond_0

    .line 99
    return-void

    .line 102
    :cond_0
    iput-boolean p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mListening:Z

    .line 103
    if-eqz p1, :cond_1

    .line 104
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 105
    .local v0, "mIntentFilter":Landroid/content/IntentFilter;
    const-string v1, "android.intent.action.AIRPLANE_MODE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 106
    const-string v1, "com.mediatek.phone.ACTION_EF_CSP_CONTENT_NOTIFY"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 107
    const-string v1, "com.mediatek.intent.action.MSIM_MODE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 108
    const-string v1, "mediatek.intent.action.ACTION_MD_TYPE_CHANGE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 109
    const-string v1, "mediatek.intent.action.LOCATED_PLMN_CHANGED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 110
    const-string v1, "android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 111
    const-string v1, "android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 112
    const-string v1, "android.intent.action.ACTION_SUBINFO_RECORD_UPDATED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 113
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 114
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mPhoneStateListener:Landroid/telephony/PhoneStateListener;

    const/16 v3, 0x20

    invoke-virtual {v1, v2, v3}, Landroid/telephony/TelephonyManager;->listen(Landroid/telephony/PhoneStateListener;I)V

    .line 116
    .end local v0    # "mIntentFilter":Landroid/content/IntentFilter;
    goto :goto_0

    .line 117
    :cond_1
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 118
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mPhoneStateListener:Landroid/telephony/PhoneStateListener;

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/telephony/TelephonyManager;->listen(Landroid/telephony/PhoneStateListener;I)V

    .line 121
    :goto_0
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V
    .locals 2
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 149
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnSettingsEnabled:Z

    if-eqz v0, :cond_0

    .line 150
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mEnableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 151
    invoke-virtual {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mEnableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 150
    invoke-static {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v0

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    goto :goto_0

    .line 153
    :cond_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mDisableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 154
    invoke-virtual {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mDisableApnStateIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 153
    invoke-static {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v0

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 156
    :goto_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    .line 157
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->mApnStateLabel:Ljava/lang/String;

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->contentDescription:Ljava/lang/CharSequence;

    .line 158
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 36
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-virtual {p0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V

    return-void
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .locals 1

    .line 81
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 36
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    move-result-object v0

    return-object v0
.end method
