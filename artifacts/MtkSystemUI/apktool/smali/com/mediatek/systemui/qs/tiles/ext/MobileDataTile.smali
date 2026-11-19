.class public Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "MobileDataTile.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;,
        Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/systemui/qs/tileimpl/QSTileImpl<",
        "Lcom/android/systemui/plugins/qs/QSTile$SignalState;",
        ">;"
    }
.end annotation


# instance fields
.field private final mCallback:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;

.field private mConnected:Z

.field private final mController:Lcom/android/systemui/statusbar/policy/NetworkController;

.field private mDataConnectionState:I

.field private final mDataController:Lcom/android/settingslib/net/DataUsageController;

.field private mDataStateIconId:I

.field private final mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private final mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private mEnabled:Z

.field private mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

.field private mTileLabel:Ljava/lang/CharSequence;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 2
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 58
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 45
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    .line 46
    const/4 v1, 0x0

    iput v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    .line 47
    const v1, 0x7f0803a9

    iput v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    .line 48
    new-instance v1, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v1}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 49
    new-instance v1, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v1}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 51
    new-instance v1, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;

    invoke-direct {v1, p0, v0}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$1;)V

    iput-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mCallback:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;

    .line 59
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    .line 60
    invoke-static {v0}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    invoke-virtual {v0, v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    .line 62
    const-class v0, Lcom/android/systemui/statusbar/policy/NetworkController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/NetworkController;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    .line 63
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    invoke-interface {v0}, Lcom/android/systemui/statusbar/policy/NetworkController;->getMobileDataController()Lcom/android/settingslib/net/DataUsageController;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataController:Lcom/android/settingslib/net/DataUsageController;

    .line 65
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    const-string v1, "create MobileDataTile"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 67
    return-void
.end method

.method static synthetic access$200(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 27
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->refreshState(Ljava/lang/Object;)V

    return-void
.end method

.method static synthetic access$300(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    .line 27
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$400(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 27
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->refreshState(Ljava/lang/Object;)V

    return-void
.end method

.method static synthetic access$500(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    .line 27
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$600(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 27
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->refreshState(Ljava/lang/Object;)V

    return-void
.end method

.method static synthetic access$700(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 27
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->refreshState(Ljava/lang/Object;)V

    return-void
.end method

.method static synthetic access$800(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 27
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->refreshState(Ljava/lang/Object;)V

    return-void
.end method

.method private final isDefaultDataSimRadioOn()Z
    .locals 5

    .line 197
    invoke-static {}, Landroid/telephony/SubscriptionManager;->getDefaultDataSubscriptionId()I

    move-result v0

    .line 198
    .local v0, "subId":I
    if-ltz v0, :cond_0

    invoke-static {v0}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isRadioOn(I)Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v1, 0x1

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    .line 200
    .local v1, "isRadioOn":Z
    :goto_0
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isDefaultDataSimRadioOn subId="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", isRadioOn="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 202
    return v1
.end method


# virtual methods
.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 88
    const/4 v0, 0x0

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 98
    const/16 v0, 0x6f

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 83
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    const v1, 0x7f110396

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method protected handleClick()V
    .locals 2

    .line 108
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataController:Lcom/android/settingslib/net/DataUsageController;

    invoke-virtual {v0}, Lcom/android/settingslib/net/DataUsageController;->isMobileDataSupported()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnabled:Z

    if-eqz v0, :cond_2

    .line 110
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mConnected:Z

    if-nez v0, :cond_1

    .line 111
    invoke-static {}, Landroid/telephony/SubscriptionManager;->getDefaultDataSubscriptionId()I

    move-result v0

    .line 113
    .local v0, "subId":I
    if-gez v0, :cond_0

    .line 114
    return-void

    .line 116
    :cond_0
    invoke-static {v0}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isRadioOn(I)Z

    move-result v1

    if-nez v1, :cond_1

    .line 117
    return-void

    .line 120
    .end local v0    # "subId":I
    :cond_1
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataController:Lcom/android/settingslib/net/DataUsageController;

    iget-boolean v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mConnected:Z

    xor-int/lit8 v1, v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/settingslib/net/DataUsageController;->setMobileDataEnabled(Z)V

    .line 122
    :cond_2
    return-void
.end method

.method protected handleLongClick()V
    .locals 0

    .line 103
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->handleClick()V

    .line 104
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 3
    .param p1, "listening"    # Z

    .line 72
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setListening = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 74
    if-eqz p1, :cond_0

    .line 75
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mCallback:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/NetworkController;->addCallback(Lcom/android/systemui/statusbar/policy/NetworkController$SignalCallback;)V

    goto :goto_0

    .line 77
    :cond_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mCallback:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/NetworkController;->removeCallback(Lcom/android/systemui/statusbar/policy/NetworkController$SignalCallback;)V

    .line 79
    :goto_0
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$SignalState;Ljava/lang/Object;)V
    .locals 9
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$SignalState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 127
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "handleUpdateState arg="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 130
    move-object v0, p2

    check-cast v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    .line 132
    .local v0, "cb":Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;
    if-nez v0, :cond_0

    .line 133
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mCallback:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;

    iget-object v0, v1, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    .line 136
    :cond_0
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataController:Lcom/android/settingslib/net/DataUsageController;

    invoke-virtual {v1}, Lcom/android/settingslib/net/DataUsageController;->isMobileDataSupported()Z

    move-result v1

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-eqz v1, :cond_1

    iget-boolean v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->noSim:Z

    if-nez v1, :cond_1

    iget-boolean v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->airplaneModeEnabled:Z

    if-nez v1, :cond_1

    .line 137
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->isDefaultDataSimRadioOn()Z

    move-result v1

    if-eqz v1, :cond_1

    move v1, v2

    goto :goto_0

    :cond_1
    move v1, v3

    .line 138
    .local v1, "enabled":Z
    :goto_0
    if-eqz v1, :cond_2

    iget-object v4, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataController:Lcom/android/settingslib/net/DataUsageController;

    invoke-virtual {v4}, Lcom/android/settingslib/net/DataUsageController;->isMobileDataEnabled()Z

    move-result v4

    if-eqz v4, :cond_2

    iget v4, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->mobileSignalIconId:I

    if-lez v4, :cond_2

    move v4, v2

    goto :goto_1

    :cond_2
    move v4, v3

    .line 140
    .local v4, "dataConnected":Z
    :goto_1
    iget v5, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->mobileSignalIconId:I

    if-lez v5, :cond_3

    iget-object v5, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabledDesc:Ljava/lang/String;

    if-nez v5, :cond_3

    move v5, v2

    goto :goto_2

    :cond_3
    move v5, v3

    .line 142
    .local v5, "dataNotConnected":Z
    :goto_2
    iput-boolean v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnabled:Z

    .line 143
    iput-boolean v4, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mConnected:Z

    .line 144
    iget-boolean v6, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabled:Z

    if-eqz v6, :cond_4

    iget-boolean v6, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->activityIn:Z

    if-eqz v6, :cond_4

    move v6, v2

    goto :goto_3

    :cond_4
    move v6, v3

    :goto_3
    iput-boolean v6, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->activityIn:Z

    .line 145
    iget-boolean v6, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabled:Z

    if-eqz v6, :cond_5

    iget-boolean v6, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->activityOut:Z

    if-eqz v6, :cond_5

    move v6, v2

    goto :goto_4

    :cond_5
    move v6, v3

    :goto_4
    iput-boolean v6, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->activityOut:Z

    .line 147
    const v6, 0x7f0803a9

    const v7, 0x7f110396

    if-nez v1, :cond_6

    .line 148
    const/4 v2, 0x3

    iput v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    .line 149
    iput v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    .line 150
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setResources(Landroid/content/res/Resources;)V

    .line 151
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setIconId(I)V

    .line 152
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v8, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    .line 154
    invoke-virtual {v8, v7}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 153
    invoke-interface {v2, v3, v6, v7}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    .line 155
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 156
    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 155
    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    goto/16 :goto_5

    .line 157
    :cond_6
    if-eqz v4, :cond_7

    .line 158
    iput v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    .line 159
    const v2, 0x7f0803aa

    iput v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    .line 160
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setResources(Landroid/content/res/Resources;)V

    .line 161
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setIconId(I)V

    .line 162
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v8, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    .line 164
    invoke-virtual {v8, v7}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 163
    invoke-interface {v2, v3, v6, v7}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    .line 165
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 166
    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mEnableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 165
    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    goto :goto_5

    .line 167
    :cond_7
    if-eqz v5, :cond_8

    .line 168
    iput v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    .line 169
    iput v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    .line 170
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setResources(Landroid/content/res/Resources;)V

    .line 171
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setIconId(I)V

    .line 172
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v8, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    .line 174
    invoke-virtual {v8, v7}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 173
    invoke-interface {v2, v3, v6, v7}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    .line 175
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 176
    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 175
    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    goto :goto_5

    .line 178
    :cond_8
    iput v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    .line 179
    iput v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    .line 180
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setResources(Landroid/content/res/Resources;)V

    .line 181
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataStateIconId:I

    invoke-virtual {v2, v3}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->setIconId(I)V

    .line 182
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mQuickSettingsExt:Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    iget v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDataConnectionState:I

    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iget-object v8, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mContext:Landroid/content/Context;

    .line 184
    invoke-virtual {v8, v7}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 183
    invoke-interface {v2, v3, v6, v7}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    .line 185
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 186
    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mDisableStateIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 185
    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 189
    :goto_5
    iget-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    iput-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->mTileLabel:Ljava/lang/CharSequence;

    .line 192
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "handleUpdateState state="

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 194
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 27
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    invoke-virtual {p0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$SignalState;Ljava/lang/Object;)V

    return-void
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$SignalState;
    .locals 1

    .line 93
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$SignalState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 27
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    move-result-object v0

    return-object v0
.end method
