.class public Lcom/android/systemui/qs/tiles/WifiNewTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "WifiNewTile.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;,
        Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;,
        Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/systemui/qs/tileimpl/QSTileImpl<",
        "Lcom/android/systemui/plugins/qs/QSTile$SignalState;",
        ">;"
    }
.end annotation


# static fields
.field private static final WIFI_SETTINGS:Landroid/content/Intent;


# instance fields
.field private final mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

.field protected final mController:Lcom/android/systemui/statusbar/policy/NetworkController;

.field private final mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

.field private mExpectDisabled:Z

.field protected final mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

.field private final mStateBeforeClick:Lcom/android/systemui/plugins/qs/QSTile$SignalState;

.field private final mWifiController:Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 52
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.settings.WIFI_SETTINGS"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    sput-object v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->WIFI_SETTINGS:Landroid/content/Intent;

    return-void
.end method

.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 1
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 64
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 57
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mStateBeforeClick:Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    .line 59
    new-instance v0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    invoke-direct {v0, p0}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;-><init>(Lcom/android/systemui/qs/tiles/WifiNewTile;)V

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    .line 65
    const-class v0, Lcom/android/systemui/statusbar/policy/NetworkController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/NetworkController;

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    .line 66
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    invoke-interface {v0}, Lcom/android/systemui/statusbar/policy/NetworkController;->getAccessPointController()Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mWifiController:Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    .line 67
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->createDetailAdapter()Lcom/android/systemui/plugins/qs/DetailAdapter;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    .line 68
    const-class v0, Lcom/android/systemui/plugins/ActivityStarter;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/plugins/ActivityStarter;

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    .line 69
    return-void
.end method

.method static synthetic access$000()Z
    .locals 1

    .line 51
    sget-boolean v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->DEBUG:Z

    return v0
.end method

.method static synthetic access$100(Lcom/android/systemui/qs/tiles/WifiNewTile;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/android/systemui/qs/tiles/WifiNewTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1100()Z
    .locals 1

    .line 51
    sget-boolean v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->DEBUG:Z

    return v0
.end method

.method static synthetic access$1200(Lcom/android/systemui/qs/tiles/WifiNewTile;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1300(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mWifiController:Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    return-object v0
.end method

.method static synthetic access$1400(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    return-object v0
.end method

.method static synthetic access$1500(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/plugins/ActivityStarter;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    return-object v0
.end method

.method static synthetic access$1600(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/qs/QSHost;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mHost:Lcom/android/systemui/qs/QSHost;

    return-object v0
.end method

.method static synthetic access$200(Lcom/android/systemui/qs/tiles/WifiNewTile;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->isShowingDetail()Z

    move-result v0

    return v0
.end method

.method static synthetic access$300(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    return-object v0
.end method

.method static synthetic access$500(Lcom/android/systemui/qs/tiles/WifiNewTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$600()Landroid/content/Intent;
    .locals 1

    .line 51
    sget-object v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->WIFI_SETTINGS:Landroid/content/Intent;

    return-object v0
.end method

.method static synthetic access$700(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    return-object v0
.end method

.method static synthetic access$800()Z
    .locals 1

    .line 51
    sget-boolean v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->DEBUG:Z

    return v0
.end method

.method static synthetic access$900(Lcom/android/systemui/qs/tiles/WifiNewTile;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 51
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method private getSecondaryLabel(ZLjava/lang/String;)Ljava/lang/CharSequence;
    .locals 2
    .param p1, "isTransient"    # Z
    .param p2, "statusLabel"    # Ljava/lang/String;

    .line 217
    if-eqz p1, :cond_0

    .line 218
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1104d9

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    .line 219
    :cond_0
    nop

    .line 217
    move-object v0, p2

    :goto_0
    return-object v0
.end method

.method public static synthetic lambda$handleClick$0(Lcom/android/systemui/qs/tiles/WifiNewTile;)V
    .locals 1

    .line 125
    iget-boolean v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mExpectDisabled:Z

    if-eqz v0, :cond_0

    .line 126
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mExpectDisabled:Z

    .line 127
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->refreshState()V

    .line 129
    :cond_0
    return-void
.end method

.method private static removeDoubleQuotes(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .param p0, "string"    # Ljava/lang/String;

    .line 247
    if-nez p0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 248
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    .line 249
    .local v0, "length":I
    const/4 v1, 0x1

    if-le v0, v1, :cond_1

    const/4 v2, 0x0

    invoke-virtual {p0, v2}, Ljava/lang/String;->charAt(I)C

    move-result v2

    const/16 v3, 0x22

    if-ne v2, v3, :cond_1

    add-int/lit8 v2, v0, -0x1

    invoke-virtual {p0, v2}, Ljava/lang/String;->charAt(I)C

    move-result v2

    if-ne v2, v3, :cond_1

    .line 250
    add-int/lit8 v2, v0, -0x1

    invoke-virtual {p0, v1, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    return-object v1

    .line 252
    :cond_1
    return-object p0
.end method


# virtual methods
.method protected composeChangeAnnouncement()Ljava/lang/String;
    .locals 2

    .line 234
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    check-cast v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v0, v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    if-eqz v0, :cond_0

    .line 235
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1100d3

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0

    .line 237
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1100d2

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method protected createDetailAdapter()Lcom/android/systemui/plugins/qs/DetailAdapter;
    .locals 1

    .line 101
    new-instance v0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    invoke-direct {v0, p0}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;-><init>(Lcom/android/systemui/qs/tiles/WifiNewTile;)V

    return-object v0
.end method

.method public getDetailAdapter()Lcom/android/systemui/plugins/qs/DetailAdapter;
    .locals 1

    .line 96
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    return-object v0
.end method

.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 111
    sget-object v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->WIFI_SETTINGS:Landroid/content/Intent;

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 224
    const/16 v0, 0x7e

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 148
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1104d4

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method protected handleClick()V
    .locals 5

    .line 117
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    check-cast v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mStateBeforeClick:Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    invoke-virtual {v0, v1}, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->copyTo(Lcom/android/systemui/plugins/qs/QSTile$State;)Z

    .line 118
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    check-cast v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v0, v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    .line 120
    .local v0, "wifiEnabled":Z
    if-eqz v0, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    sget-object v1, Lcom/android/systemui/qs/tiles/WifiNewTile;->ARG_SHOW_TRANSIENT_ENABLING:Ljava/lang/Object;

    :goto_0
    invoke-virtual {p0, v1}, Lcom/android/systemui/qs/tiles/WifiNewTile;->refreshState(Ljava/lang/Object;)V

    .line 121
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    xor-int/lit8 v2, v0, 0x1

    invoke-interface {v1, v2}, Lcom/android/systemui/statusbar/policy/NetworkController;->setWifiEnabled(Z)V

    .line 122
    iput-boolean v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mExpectDisabled:Z

    .line 123
    iget-boolean v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mExpectDisabled:Z

    if-eqz v1, :cond_1

    .line 124
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mHandler:Lcom/android/systemui/qs/tileimpl/QSTileImpl$H;

    new-instance v2, Lcom/android/systemui/qs/tiles/-$$Lambda$WifiNewTile$oJv6m0QYqqLN6NYGXmZ1Y71fuNs;

    invoke-direct {v2, p0}, Lcom/android/systemui/qs/tiles/-$$Lambda$WifiNewTile$oJv6m0QYqqLN6NYGXmZ1Y71fuNs;-><init>(Lcom/android/systemui/qs/tiles/WifiNewTile;)V

    const-wide/16 v3, 0x15e

    invoke-virtual {v1, v2, v3, v4}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$H;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 131
    :cond_1
    return-void
.end method

.method protected handleSecondaryClick()V
    .locals 3

    .line 135
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mWifiController:Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    invoke-interface {v0}, Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;->canConfigWifi()Z

    move-result v0

    if-nez v0, :cond_0

    .line 136
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mActivityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    new-instance v1, Landroid/content/Intent;

    const-string v2, "android.settings.WIFI_SETTINGS"

    invoke-direct {v1, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const/4 v2, 0x0

    invoke-interface {v0, v1, v2}, Lcom/android/systemui/plugins/ActivityStarter;->postStartActivityDismissingKeyguard(Landroid/content/Intent;I)V

    .line 138
    return-void

    .line 140
    :cond_0
    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->showDetail(Z)V

    .line 141
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    check-cast v1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v1, v1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    if-nez v1, :cond_1

    .line 142
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    invoke-interface {v1, v0}, Lcom/android/systemui/statusbar/policy/NetworkController;->setWifiEnabled(Z)V

    .line 144
    :cond_1
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 2
    .param p1, "listening"    # Z

    .line 78
    if-eqz p1, :cond_0

    .line 79
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/NetworkController;->addCallback(Lcom/android/systemui/statusbar/policy/NetworkController$SignalCallback;)V

    goto :goto_0

    .line 81
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/NetworkController;->removeCallback(Lcom/android/systemui/statusbar/policy/NetworkController$SignalCallback;)V

    .line 83
    :goto_0
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$SignalState;Ljava/lang/Object;)V
    .locals 13
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$SignalState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 153
    sget-boolean v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->DEBUG:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "handleUpdateState arg="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 154
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    iget-object v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;->mInfo:Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;

    .line 155
    .local v0, "cb":Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;
    iget-boolean v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mExpectDisabled:Z

    const/4 v2, 0x0

    if-eqz v1, :cond_2

    .line 156
    iget-boolean v1, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-eqz v1, :cond_1

    .line 157
    return-void

    .line 159
    :cond_1
    iput-boolean v2, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mExpectDisabled:Z

    .line 162
    :cond_2
    sget-object v1, Lcom/android/systemui/qs/tiles/WifiNewTile;->ARG_SHOW_TRANSIENT_ENABLING:Ljava/lang/Object;

    const/4 v3, 0x1

    if-ne p2, v1, :cond_3

    move v1, v3

    goto :goto_0

    :cond_3
    move v1, v2

    .line 163
    .local v1, "transientEnabling":Z
    :goto_0
    iget-boolean v4, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-eqz v4, :cond_4

    iget v4, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->wifiSignalIconId:I

    if-lez v4, :cond_4

    iget-object v4, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->ssid:Ljava/lang/String;

    if-eqz v4, :cond_4

    move v4, v3

    goto :goto_1

    :cond_4
    move v4, v2

    .line 164
    .local v4, "wifiConnected":Z
    :goto_1
    iget v5, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->wifiSignalIconId:I

    if-lez v5, :cond_5

    iget-object v5, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->ssid:Ljava/lang/String;

    if-nez v5, :cond_5

    move v5, v3

    goto :goto_2

    :cond_5
    move v5, v2

    .line 165
    .local v5, "wifiNotConnected":Z
    :goto_2
    iget-boolean v6, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    iget-boolean v7, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-eq v6, v7, :cond_6

    move v6, v3

    goto :goto_3

    :cond_6
    move v6, v2

    .line 166
    .local v6, "enabledChanging":Z
    :goto_3
    if-eqz v6, :cond_7

    .line 167
    iget-object v7, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    iget-boolean v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    invoke-virtual {v7, v8}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->setItemsVisible(Z)V

    .line 168
    iget-boolean v7, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    invoke-virtual {p0, v7}, Lcom/android/systemui/qs/tiles/WifiNewTile;->fireToggleStateChanged(Z)V

    .line 170
    :cond_7
    iget-object v7, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->slash:Lcom/android/systemui/plugins/qs/QSTile$SlashState;

    if-nez v7, :cond_8

    .line 171
    new-instance v7, Lcom/android/systemui/plugins/qs/QSTile$SlashState;

    invoke-direct {v7}, Lcom/android/systemui/plugins/qs/QSTile$SlashState;-><init>()V

    iput-object v7, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->slash:Lcom/android/systemui/plugins/qs/QSTile$SlashState;

    .line 172
    iget-object v7, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->slash:Lcom/android/systemui/plugins/qs/QSTile$SlashState;

    const/high16 v8, 0x40c00000    # 6.0f

    iput v8, v7, Lcom/android/systemui/plugins/qs/QSTile$SlashState;->rotation:F

    .line 174
    :cond_8
    iget-object v7, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->slash:Lcom/android/systemui/plugins/qs/QSTile$SlashState;

    iput-boolean v2, v7, Lcom/android/systemui/plugins/qs/QSTile$SlashState;->isSlashed:Z

    .line 175
    if-nez v1, :cond_a

    iget-boolean v7, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->isTransient:Z

    if-eqz v7, :cond_9

    goto :goto_4

    :cond_9
    move v7, v2

    goto :goto_5

    :cond_a
    :goto_4
    move v7, v3

    .line 176
    .local v7, "isTransient":Z
    :goto_5
    iget-object v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->statusLabel:Ljava/lang/String;

    invoke-direct {p0, v7, v8}, Lcom/android/systemui/qs/tiles/WifiNewTile;->getSecondaryLabel(ZLjava/lang/String;)Ljava/lang/CharSequence;

    move-result-object v8

    iput-object v8, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->secondaryLabel:Ljava/lang/CharSequence;

    .line 177
    const/4 v8, 0x2

    iput v8, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->state:I

    .line 178
    iput-boolean v3, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->dualTarget:Z

    .line 179
    if-nez v1, :cond_c

    iget-boolean v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-eqz v8, :cond_b

    goto :goto_6

    :cond_b
    move v8, v2

    goto :goto_7

    :cond_c
    :goto_6
    move v8, v3

    :goto_7
    iput-boolean v8, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    .line 180
    iget-boolean v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-eqz v8, :cond_d

    iget-boolean v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->activityIn:Z

    if-eqz v8, :cond_d

    move v8, v3

    goto :goto_8

    :cond_d
    move v8, v2

    :goto_8
    iput-boolean v8, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->activityIn:Z

    .line 181
    iget-boolean v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-eqz v8, :cond_e

    iget-boolean v8, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->activityOut:Z

    if-eqz v8, :cond_e

    move v8, v3

    goto :goto_9

    :cond_e
    move v8, v2

    :goto_9
    iput-boolean v8, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->activityOut:Z

    .line 182
    new-instance v8, Ljava/lang/StringBuffer;

    invoke-direct {v8}, Ljava/lang/StringBuffer;-><init>()V

    .line 183
    .local v8, "minimalContentDescription":Ljava/lang/StringBuffer;
    iget-object v9, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    invoke-virtual {v9}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v9

    .line 184
    .local v9, "r":Landroid/content/res/Resources;
    const v10, 0x7f0803e1

    const v11, 0x7f1104d4

    if-eqz v7, :cond_f

    .line 185
    invoke-static {v10}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 186
    invoke-virtual {v9, v11}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    goto :goto_a

    .line 187
    :cond_f
    iget-boolean v12, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    if-nez v12, :cond_10

    .line 188
    iget-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->slash:Lcom/android/systemui/plugins/qs/QSTile$SlashState;

    iput-boolean v3, v10, Lcom/android/systemui/plugins/qs/QSTile$SlashState;->isSlashed:Z

    .line 189
    iput v3, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->state:I

    .line 190
    const v10, 0x7f0803c2

    invoke-static {v10}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 191
    invoke-virtual {v9, v11}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    goto :goto_a

    .line 192
    :cond_10
    if-eqz v4, :cond_11

    .line 193
    invoke-static {v10}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 194
    iget-object v10, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->ssid:Ljava/lang/String;

    invoke-static {v10}, Lcom/android/systemui/qs/tiles/WifiNewTile;->removeDoubleQuotes(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    goto :goto_a

    .line 195
    :cond_11
    if-eqz v5, :cond_12

    .line 196
    const v10, 0x7f0803c3

    invoke-static {v10}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 197
    invoke-virtual {v9, v11}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    goto :goto_a

    .line 199
    :cond_12
    const v10, 0x7f0803c9

    invoke-static {v10}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 200
    invoke-virtual {v9, v11}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->label:Ljava/lang/CharSequence;

    .line 202
    :goto_a
    iget-object v10, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    .line 203
    invoke-virtual {v10, v11}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v10

    .line 202
    invoke-virtual {v8, v10}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v10, ","

    .line 203
    invoke-virtual {v8, v10}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 204
    iget-boolean v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    if-eqz v10, :cond_13

    .line 205
    if-eqz v4, :cond_13

    .line 206
    iget-object v10, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->wifiSignalContentDescription:Ljava/lang/String;

    invoke-virtual {v8, v10}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v10, ","

    invoke-virtual {v8, v10}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 207
    iget-object v10, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->ssid:Ljava/lang/String;

    invoke-static {v10}, Lcom/android/systemui/qs/tiles/WifiNewTile;->removeDoubleQuotes(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v8, v10}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 210
    :cond_13
    invoke-virtual {v8}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v10

    iput-object v10, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->contentDescription:Ljava/lang/CharSequence;

    .line 211
    const v10, 0x7f1100cb

    new-array v3, v3, [Ljava/lang/Object;

    .line 212
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->getTileLabel()Ljava/lang/CharSequence;

    move-result-object v11

    aput-object v11, v3, v2

    .line 211
    invoke-virtual {v9, v10, v3}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->dualLabelContentDescription:Ljava/lang/CharSequence;

    .line 213
    const-class v2, Landroid/widget/Switch;

    invoke-virtual {v2}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->expandedAccessibilityClassName:Ljava/lang/String;

    .line 214
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 51
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/qs/tiles/WifiNewTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$SignalState;Ljava/lang/Object;)V

    return-void
.end method

.method public isAvailable()Z
    .locals 2

    .line 243
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    const-string v1, "android.hardware.wifi"

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->hasSystemFeature(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$SignalState;
    .locals 1

    .line 73
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$SignalState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 51
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    move-result-object v0

    return-object v0
.end method

.method public setDetailListening(Z)V
    .locals 2
    .param p1, "listening"    # Z

    .line 87
    if-eqz p1, :cond_0

    .line 88
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mWifiController:Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;->addAccessPointCallback(Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController$AccessPointCallback;)V

    goto :goto_0

    .line 90
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mWifiController:Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mDetailAdapter:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;->removeAccessPointCallback(Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController$AccessPointCallback;)V

    .line 92
    :goto_0
    return-void
.end method

.method protected shouldAnnouncementBeDelayed()Z
    .locals 2

    .line 229
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mStateBeforeClick:Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v0, v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mState:Lcom/android/systemui/plugins/qs/QSTile$State;

    check-cast v1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v1, v1, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method
