.class public Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;
.super Ljava/lang/Object;
.source "QSFactoryImpl.java"

# interfaces
.implements Lcom/android/systemui/plugins/qs/QSFactory;


# instance fields
.field private final mHost:Lcom/android/systemui/qs/QSTileHost;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/QSTileHost;)V
    .locals 0
    .param p1, "host"    # Lcom/android/systemui/qs/QSTileHost;

    .line 77
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 78
    iput-object p1, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    .line 79
    return-void
.end method

.method private createTileInternal(Ljava/lang/String;)Lcom/android/systemui/qs/tileimpl/QSTileImpl;
    .locals 5
    .param p1, "tileSpec"    # Ljava/lang/String;

    .line 91
    iget-object v0, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-virtual {v0}, Lcom/android/systemui/qs/QSTileHost;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 92
    .local v0, "context":Landroid/content/Context;
    nop

    .line 93
    invoke-static {v0}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;

    move-result-object v1

    .line 96
    .local v1, "quickSettingsPlugin":Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;
    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result v2

    const/4 v3, 0x1

    const/4 v4, 0x0

    sparse-switch v2, :sswitch_data_0

    goto/16 :goto_0

    :sswitch_0
    const-string v2, "location"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v2, 0x6

    goto/16 :goto_1

    :sswitch_1
    const-string v2, "settings"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0xf

    goto/16 :goto_1

    :sswitch_2
    const-string v2, "memorycleaner"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0xc

    goto/16 :goto_1

    :sswitch_3
    const-string v2, "screenoff"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0xb

    goto/16 :goto_1

    :sswitch_4
    const-string v2, "suspension"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0x11

    goto/16 :goto_1

    :sswitch_5
    const-string/jumbo v2, "wifi"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    move v2, v4

    goto/16 :goto_1

    :sswitch_6
    const-string v2, "mute"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0x8

    goto/16 :goto_1

    :sswitch_7
    const-string v2, "menu"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0x10

    goto/16 :goto_1

    :sswitch_8
    const-string v2, "cell"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v2, 0x2

    goto :goto_1

    :sswitch_9
    const-string v2, "cast"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v2, 0x7

    goto :goto_1

    :sswitch_a
    const-string v2, "dnd"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v2, 0x3

    goto :goto_1

    :sswitch_b
    const-string v2, "eq"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0xd

    goto :goto_1

    :sswitch_c
    const-string v2, "bt"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    move v2, v3

    goto :goto_1

    :sswitch_d
    const-string v2, "screensaver"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0xe

    goto :goto_1

    :sswitch_e
    const-string v2, "rotation"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v2, 0x5

    goto :goto_1

    :sswitch_f
    const-string v2, "screenshot"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0xa

    goto :goto_1

    :sswitch_10
    const-string v2, "airplane"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v2, 0x4

    goto :goto_1

    :sswitch_11
    const-string v2, "reboot"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/16 v2, 0x9

    goto :goto_1

    :cond_0
    :goto_0
    const/4 v2, -0x1

    :goto_1
    packed-switch v2, :pswitch_data_0

    goto/16 :goto_2

    .line 162
    :pswitch_0
    new-instance v2, Lcom/android/systemui/qs/tiles/SuspensionTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/SuspensionTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 160
    :pswitch_1
    new-instance v2, Lcom/android/systemui/qs/tiles/MenuTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/MenuTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 158
    :pswitch_2
    new-instance v2, Lcom/android/systemui/qs/tiles/SettingsTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/SettingsTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 156
    :pswitch_3
    new-instance v2, Lcom/android/systemui/qs/tiles/ScreenSaverTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/ScreenSaverTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 154
    :pswitch_4
    new-instance v2, Lcom/android/systemui/qs/tiles/EQTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/EQTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 152
    :pswitch_5
    new-instance v2, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 150
    :pswitch_6
    new-instance v2, Lcom/android/systemui/qs/tiles/ScreenOffTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/ScreenOffTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 148
    :pswitch_7
    new-instance v2, Lcom/android/systemui/qs/tiles/ScreenshotTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/ScreenshotTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 146
    :pswitch_8
    new-instance v2, Lcom/android/systemui/qs/tiles/RebootTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/RebootTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 144
    :pswitch_9
    new-instance v2, Lcom/android/systemui/qs/tiles/MuteTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/MuteTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 130
    :pswitch_a
    new-instance v2, Lcom/android/systemui/qs/tiles/CastTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/CastTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 128
    :pswitch_b
    new-instance v2, Lcom/android/systemui/qs/tiles/LocationTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/LocationTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 124
    :pswitch_c
    new-instance v2, Lcom/android/systemui/qs/tiles/RotationLockTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/RotationLockTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 120
    :pswitch_d
    new-instance v2, Lcom/android/systemui/qs/tiles/AirplaneModeTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/AirplaneModeTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 116
    :pswitch_e
    new-instance v2, Lcom/android/systemui/qs/tiles/DndTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/DndTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 114
    :pswitch_f
    new-instance v2, Lcom/android/systemui/qs/tiles/CellularTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/CellularTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 112
    :pswitch_10
    new-instance v2, Lcom/android/systemui/qs/tiles/BluetoothTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/BluetoothTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 98
    :pswitch_11
    const-string v2, "hide_network_entrance"

    invoke-static {v2, v4}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v2

    if-eq v2, v3, :cond_3

    .line 99
    sget v2, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_GOD_400:I

    sget v3, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_GOD:I

    if-ne v2, v3, :cond_2

    sget v2, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_16:I

    sget v3, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB:I

    if-eq v2, v3, :cond_1

    sget v2, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_22:I

    sget v3, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB:I

    if-eq v2, v3, :cond_1

    sget v2, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_37:I

    sget v3, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB:I

    if-eq v2, v3, :cond_1

    sget v2, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_65:I

    sget v3, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB:I

    if-ne v2, v3, :cond_2

    .line 104
    :cond_1
    new-instance v2, Lcom/android/systemui/qs/tiles/WifiNewTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/WifiNewTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 106
    :cond_2
    new-instance v2, Lcom/android/systemui/qs/tiles/WifiTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/android/systemui/qs/tiles/WifiTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 168
    :cond_3
    :goto_2
    const-string v2, "dataconnection"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isWifiOnlyDevice()Z

    move-result v2

    if-nez v2, :cond_4

    .line 169
    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    return-object v2

    .line 170
    :cond_4
    const-string v2, "simdataconnection"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_5

    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isWifiOnlyDevice()Z

    move-result v2

    if-nez v2, :cond_5

    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 171
    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_5

    .line 172
    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    return-object v2

    .line 174
    :cond_5
    const-string v2, "dulsimsettings"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_6

    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isWifiOnlyDevice()Z

    move-result v2

    if-nez v2, :cond_6

    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 175
    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_6

    .line 176
    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/systemui/qs/tiles/ext/DualSimSettingsTile;

    return-object v2

    .line 178
    :cond_6
    const-string v2, "apnsettings"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_7

    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isWifiOnlyDevice()Z

    move-result v2

    if-nez v2, :cond_7

    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 179
    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_7

    .line 180
    new-instance v2, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    iget-object v3, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-direct {v2, v3}, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;-><init>(Lcom/android/systemui/qs/QSHost;)V

    invoke-interface {v1, v2}, Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;->customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/systemui/qs/tiles/ext/ApnSettingsTile;

    return-object v2

    .line 185
    :cond_7
    const-string v2, "intent("

    invoke-virtual {p1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_8

    iget-object v2, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-static {v2, p1}, Lcom/android/systemui/qs/tiles/IntentTile;->create(Lcom/android/systemui/qs/QSHost;Ljava/lang/String;)Lcom/android/systemui/qs/tiles/IntentTile;

    move-result-object v2

    return-object v2

    .line 186
    :cond_8
    const-string v2, "custom("

    invoke-virtual {p1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_9

    iget-object v2, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-static {v2, p1}, Lcom/android/systemui/qs/external/CustomTile;->create(Lcom/android/systemui/qs/QSTileHost;Ljava/lang/String;)Lcom/android/systemui/qs/external/CustomTile;

    move-result-object v2

    return-object v2

    .line 196
    :cond_9
    const-string v2, "QSFactory"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Bad tile spec: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 197
    const/4 v2, 0x0

    return-object v2

    nop

    nop

    :sswitch_data_0
    .sparse-switch
        -0x37ba085b -> :sswitch_11
        -0x285a60ae -> :sswitch_10
        -0x18d27a9a -> :sswitch_f
        -0x266f082 -> :sswitch_e
        -0x17fee57 -> :sswitch_d
        0xc52 -> :sswitch_c
        0xcac -> :sswitch_b
        0x1851a -> :sswitch_a
        0x2e7b3f -> :sswitch_9
        0x2e8962 -> :sswitch_8
        0x33155f -> :sswitch_7
        0x335219 -> :sswitch_6
        0x37af15 -> :sswitch_5
        0x1c30fdd -> :sswitch_4
        0x7750583 -> :sswitch_3
        0x3b112495 -> :sswitch_2
        0x5582bc23 -> :sswitch_1
        0x714f9fb5 -> :sswitch_0
    .end sparse-switch

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
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


# virtual methods
.method public createTile(Ljava/lang/String;)Lcom/android/systemui/plugins/qs/QSTile;
    .locals 1
    .param p1, "tileSpec"    # Ljava/lang/String;

    .line 82
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->createTileInternal(Ljava/lang/String;)Lcom/android/systemui/qs/tileimpl/QSTileImpl;

    move-result-object v0

    .line 83
    .local v0, "tile":Lcom/android/systemui/qs/tileimpl/QSTileImpl;
    if-eqz v0, :cond_0

    .line 84
    invoke-virtual {v0}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;->handleStale()V

    .line 86
    :cond_0
    return-object v0
.end method

.method public createTileView(Lcom/android/systemui/plugins/qs/QSTile;Z)Lcom/android/systemui/plugins/qs/QSTileView;
    .locals 3
    .param p1, "tile"    # Lcom/android/systemui/plugins/qs/QSTile;
    .param p2, "collapsedView"    # Z

    .line 202
    new-instance v0, Landroid/view/ContextThemeWrapper;

    iget-object v1, p0, Lcom/android/systemui/qs/tileimpl/QSFactoryImpl;->mHost:Lcom/android/systemui/qs/QSTileHost;

    invoke-virtual {v1}, Lcom/android/systemui/qs/QSTileHost;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x7f120335

    invoke-direct {v0, v1, v2}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 203
    .local v0, "context":Landroid/content/Context;
    invoke-interface {p1, v0}, Lcom/android/systemui/plugins/qs/QSTile;->createTileView(Landroid/content/Context;)Lcom/android/systemui/plugins/qs/QSIconView;

    move-result-object v1

    .line 204
    .local v1, "icon":Lcom/android/systemui/plugins/qs/QSIconView;
    if-eqz p2, :cond_0

    .line 205
    new-instance v2, Lcom/android/systemui/qs/tileimpl/QSTileBaseView;

    invoke-direct {v2, v0, v1, p2}, Lcom/android/systemui/qs/tileimpl/QSTileBaseView;-><init>(Landroid/content/Context;Lcom/android/systemui/plugins/qs/QSIconView;Z)V

    return-object v2

    .line 207
    :cond_0
    new-instance v2, Lcom/android/systemui/qs/tileimpl/QSTileView;

    invoke-direct {v2, v0, v1}, Lcom/android/systemui/qs/tileimpl/QSTileView;-><init>(Landroid/content/Context;Lcom/android/systemui/plugins/qs/QSIconView;)V

    return-object v2
.end method
