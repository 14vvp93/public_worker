.class public Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "DualSimSettingsTile.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/systemui/qs/tileimpl/QSTileImpl<",
        "Lcom/android/systemui/plugins/qs/QSTile$BooleanState;",
        ">;"
    }
.end annotation


# static fields
.field private static final DUAL_SIM_SETTINGS:Landroid/content/Intent;


# instance fields
.field private final mDisableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private final mEnableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

.field private mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

.field private mTileLabel:Ljava/lang/CharSequence;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    .line 30
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "com.android.settings"

    const-string v3, "com.android.settings.Settings$SimSettingsActivity"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    move-result-object v0

    sput-object v0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->DUAL_SIM_SETTINGS:Landroid/content/Intent;

    return-void
.end method

.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 1
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 42
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 34
    new-instance v0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mEnableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 35
    new-instance v0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-direct {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>()V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mDisableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 116
    new-instance v0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile$1;

    invoke-direct {v0, p0}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile$1;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;)V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

    .line 43
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->registerSimStateReceiver()V

    .line 44
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 28
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->handleRefreshState(Ljava/lang/Object;)V

    return-void
.end method

.method static synthetic access$100(Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;Ljava/lang/Object;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;
    .param p1, "x1"    # Ljava/lang/Object;

    .line 28
    invoke-virtual {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->handleRefreshState(Ljava/lang/Object;)V

    return-void
.end method

.method private registerSimStateReceiver()V
    .locals 3

    .line 110
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 111
    .local v0, "filter":Landroid/content/IntentFilter;
    const-string v1, "android.intent.action.SIM_STATE_CHANGED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 112
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 113
    return-void
.end method


# virtual methods
.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 70
    const/4 v0, 0x0

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 57
    const/16 v0, 0x6f

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 62
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mContext:Landroid/content/Context;

    .line 63
    invoke-static {v0}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mContext:Landroid/content/Context;

    invoke-virtual {v0, v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v0

    const-string v1, "dulsimsettings"

    .line 64
    invoke-interface {v0, v1}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->getTileLabel(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mTileLabel:Ljava/lang/CharSequence;

    .line 65
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mTileLabel:Ljava/lang/CharSequence;

    return-object v0
.end method

.method protected handleClick()V
    .locals 5

    .line 80
    invoke-static {}, Landroid/telephony/SubscriptionManager;->getDefaultDataSubscriptionId()I

    move-result v0

    int-to-long v0, v0

    .line 81
    .local v0, "subId":J
    const-string v2, "DualSimSettingsTile"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "handleClick, "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v4, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->DUAL_SIM_SETTINGS:Landroid/content/Intent;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 82
    sget-object v2, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->DUAL_SIM_SETTINGS:Landroid/content/Intent;

    const-string v3, "subscription"

    invoke-virtual {v2, v3, v0, v1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;J)Landroid/content/Intent;

    .line 84
    const-class v2, Lcom/android/systemui/plugins/ActivityStarter;

    invoke-static {v2}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/systemui/plugins/ActivityStarter;

    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->DUAL_SIM_SETTINGS:Landroid/content/Intent;

    const/4 v4, 0x0

    invoke-interface {v2, v3, v4}, Lcom/android/systemui/plugins/ActivityStarter;->postStartActivityDismissingKeyguard(Landroid/content/Intent;I)V

    .line 86
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->refreshState()V

    .line 87
    return-void
.end method

.method protected handleLongClick()V
    .locals 0

    .line 75
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->handleClick()V

    .line 76
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 0
    .param p1, "listening"    # Z

    .line 53
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V
    .locals 5
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 91
    move-object v0, p2

    check-cast v0, Ljava/lang/Boolean;

    .line 92
    .local v0, "simInserted":Ljava/lang/Boolean;
    const-string v1, "DualSimSettingsTile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "handleUpdateState,  simInserted="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 93
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mContext:Landroid/content/Context;

    .line 94
    invoke-static {v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v2}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v1

    .line 96
    .local v1, "quickSettingsPlugin":Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;
    if-eqz v0, :cond_0

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 97
    const/4 v2, 0x0

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mDisableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    const-string v4, ""

    invoke-interface {v1, v2, v3, v4}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeDualSimSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    .line 99
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mDisableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 100
    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mDisableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 99
    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    goto :goto_0

    .line 102
    :cond_0
    const/4 v2, 0x1

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mEnableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    const-string v4, ""

    invoke-interface {v1, v2, v3, v4}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeDualSimSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    .line 104
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mEnableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-virtual {v2}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getIconId()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mEnableIconIdWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-static {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    move-result-object v2

    iput-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 106
    :goto_0
    iget-object v2, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    iput-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->mTileLabel:Ljava/lang/CharSequence;

    .line 107
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 28
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-virtual {p0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V

    return-void
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .locals 1

    .line 48
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 28
    invoke-virtual {p0}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    move-result-object v0

    return-object v0
.end method
