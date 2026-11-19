.class public Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "SimDataConnectionTile.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;,
        Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/systemui/qs/tileimpl/QSTileImpl<",
        "Lcom/android/systemui/plugins/qs/QSTile$BooleanState;",
        ">;"
    }
.end annotation


# instance fields
.field private mListening:Z

.field private mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

.field private mTileLabel:Ljava/lang/CharSequence;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 1
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 52
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 40
    nop

    .line 41
    invoke-static {}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->values()[Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    move-result-object v0

    array-length v0, v0

    new-array v0, v0, [Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 53
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->init()V

    .line 54
    return-void
.end method

.method static synthetic access$1000(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1800(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$200(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$300(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$400(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$500(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$600(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$700(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;Ljava/util/List;I)Z
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;
    .param p1, "x1"    # Ljava/util/List;
    .param p2, "x2"    # I

    .line 36
    invoke-direct {p0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->isSimInsertedBySlot(Ljava/util/List;I)Z

    move-result v0

    return v0
.end method

.method static synthetic access$800(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Z
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->isWifiOnlyDevice()Z

    move-result v0

    return v0
.end method

.method static synthetic access$900(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 36
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method private init()V
    .locals 3

    .line 70
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    array-length v1, v1

    if-ge v0, v1, :cond_0

    .line 71
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    new-instance v2, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    aput-object v2, v1, v0

    .line 70
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 73
    .end local v0    # "i":I
    :cond_0
    new-instance v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    invoke-direct {v0, p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 74
    return-void
.end method

.method private isSimInsertedBySlot(Ljava/util/List;I)Z
    .locals 4
    .param p2, "slotId"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/telephony/SubscriptionInfo;",
            ">;I)Z"
        }
    .end annotation

    .line 593
    .local p1, "infos":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->getSlotCount()I

    move-result v0

    const/4 v1, 0x0

    if-lt p2, v0, :cond_0

    .line 594
    return v1

    .line 597
    :cond_0
    if-eqz p1, :cond_3

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v0

    if-lez v0, :cond_3

    .line 598
    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/telephony/SubscriptionInfo;

    .line 599
    .local v2, "info":Landroid/telephony/SubscriptionInfo;
    invoke-virtual {v2}, Landroid/telephony/SubscriptionInfo;->getSimSlotIndex()I

    move-result v3

    if-ne v3, p2, :cond_1

    .line 600
    const/4 v0, 0x1

    return v0

    .line 602
    .end local v2    # "info":Landroid/telephony/SubscriptionInfo;
    :cond_1
    goto :goto_0

    .line 603
    :cond_2
    return v1

    .line 605
    :cond_3
    const-string v0, "SimDataConnectionTile"

    const-string v2, "isSimInsertedBySlot, SubscriptionInfo is null"

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 606
    return v1
.end method

.method private isWifiOnlyDevice()Z
    .locals 2

    .line 586
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    const-string v1, "connectivity"

    .line 587
    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    .line 588
    .local v0, "cm":Landroid/net/ConnectivityManager;
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/net/ConnectivityManager;->isNetworkSupported(I)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    return v1
.end method


# virtual methods
.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 66
    const/4 v0, 0x0

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 107
    const/16 v0, 0x6f

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 58
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    .line 59
    invoke-static {v0}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    invoke-virtual {v0, v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v0

    const-string v1, "simdataconnection"

    .line 60
    invoke-interface {v0, v1}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->getTileLabel(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mTileLabel:Ljava/lang/CharSequence;

    .line 61
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mTileLabel:Ljava/lang/CharSequence;

    return-object v0
.end method

.method protected handleClick()V
    .locals 2

    .line 88
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    invoke-virtual {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isClickable()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 89
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    invoke-virtual {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->toggleState(Landroid/content/Context;)V

    .line 91
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->refreshState()V

    .line 92
    return-void
.end method

.method protected handleLongClick()V
    .locals 0

    .line 83
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->handleClick()V

    .line 84
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 1
    .param p1, "listening"    # Z

    .line 96
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mListening:Z

    if-ne v0, p1, :cond_0

    return-void

    .line 97
    :cond_0
    iput-boolean p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mListening:Z

    .line 98
    if-eqz p1, :cond_1

    .line 99
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->access$000(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V

    goto :goto_0

    .line 101
    :cond_1
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->access$100(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V

    .line 103
    :goto_0
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V
    .locals 4
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 112
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimDataSwitchStateMachine:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    invoke-virtual {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->getCurrentSimConnState()Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->ordinal()I

    move-result v0

    .line 113
    .local v0, "simConnState":I
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    .line 114
    invoke-static {v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v2}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v1

    .line 115
    .local v1, "quickSettingsPlugin":Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    aget-object v2, v2, v0

    invoke-interface {v1, v0, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeSimDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)V

    .line 117
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    aget-object v2, v2, v0

    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->mSimConnectionIconWrapperArray:[Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    aget-object v3, v3, v0

    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 119
    const-string v2, "simdataconnection"

    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->getTileLabel(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    .line 120
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 36
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-virtual {p0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V

    return-void
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .locals 1

    .line 78
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 36
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    move-result-object v0

    return-object v0
.end method
