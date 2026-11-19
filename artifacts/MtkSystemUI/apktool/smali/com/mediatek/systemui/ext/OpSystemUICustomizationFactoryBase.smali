.class public Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;
.super Ljava/lang/Object;
.source "OpSystemUICustomizationFactoryBase.java"


# static fields
.field static sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

.field private static final sOpFactoryInfoList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 8

    .line 31
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    .line 34
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP03SystemUI.apk"

    const-string v3, "com.mediatek.op03.systemui.Op03SystemUICustomizationFactory"

    const-string v4, "com.mediatek.op03.systemui"

    const-string v5, "OP03"

    const-string v6, "SEGDEFAULT"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 42
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP07SystemUI.apk"

    const-string v3, "com.mediatek.op07.systemui.OP07SystemUICustomizationFactory"

    const-string v4, "com.mediatek.op07.systemui"

    const-string v5, "OP07"

    const-string v6, "SEGDEFAULT"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 50
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP18SystemUI.apk"

    const-string v3, "com.mediatek.op18.systemui.Op18SystemUICustomizationFactory"

    const-string v4, "com.mediatek.op18.systemui"

    const-string v5, "OP18"

    const-string v6, "SEGDEFAULT"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 58
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v1, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP01SystemUI.apk"

    const-string v3, "com.mediatek.systemui.op01.Op01SystemUICustomizationFactory"

    const-string v4, "com.mediatek.systemui.op01"

    const-string v5, "OP01"

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 65
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v1, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP02SystemUI.apk"

    const-string v3, "com.mediatek.systemui.op02.Op02SystemUICustomizationFactory"

    const-string v4, "com.mediatek.systemui.op02"

    const-string v5, "OP02"

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 72
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP09SystemUI.apk"

    const-string v3, "com.mediatek.systemui.op09.Op09SystemUICustomizationFactory"

    const-string v4, "com.mediatek.systemui.op09"

    const-string v5, "OP09"

    const-string v6, "SEGDEFAULT"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 80
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP09ClipSystemUI.apk"

    const-string v3, "com.mediatek.systemui.op09clip.Op09SystemUICustomizationFactory"

    const-string v4, "com.mediatek.systemui.op09clip"

    const-string v5, "OP09"

    const-string v6, "SEGC"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 88
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v1, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP08SystemUI.apk"

    const-string v3, "com.mediatek.op08.systemui.Op08SystemUICustomizationFactory"

    const-string v4, "com.mediatek.op08.systemui"

    const-string v5, "OP08"

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 95
    sget-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v1, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP20SystemUI.apk"

    const-string v3, "com.mediatek.op20.systemui.Op20SystemUICustomizationFactory"

    const-string v4, "com.mediatek.op20.systemui"

    const-string v5, "OP20"

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 103
    const/4 v0, 0x0

    sput-object v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static declared-synchronized getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;
    .locals 2
    .param p0, "context"    # Landroid/content/Context;

    const-class v0, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    monitor-enter v0

    .line 105
    :try_start_0
    sget-object v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    if-nez v1, :cond_0

    .line 106
    sget-object v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    .line 107
    invoke-static {p0, v1}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader;->loadFactory(Landroid/content/Context;Ljava/util/List;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    sput-object v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    .line 108
    sget-object v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    if-nez v1, :cond_0

    .line 109
    new-instance v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    invoke-direct {v1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;-><init>()V

    sput-object v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    .line 112
    :cond_0
    sget-object v1, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->sFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    .line 104
    .end local p0    # "context":Landroid/content/Context;
    :catchall_0
    move-exception p0

    monitor-exit v0

    throw p0
.end method


# virtual methods
.method public makeMobileIcon()Lcom/mediatek/systemui/ext/IMobileIconExt;
    .locals 1

    .line 12
    new-instance v0, Lcom/mediatek/systemui/ext/DefaultMobileIconExt;

    invoke-direct {v0}, Lcom/mediatek/systemui/ext/DefaultMobileIconExt;-><init>()V

    return-object v0
.end method

.method public makeNavigationBar(Landroid/content/Context;)Lcom/mediatek/systemui/ext/INavigationBarPlugin;
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 16
    new-instance v0, Lcom/mediatek/systemui/ext/DefaultNavigationBarPlugin;

    invoke-direct {v0, p1}, Lcom/mediatek/systemui/ext/DefaultNavigationBarPlugin;-><init>(Landroid/content/Context;)V

    return-object v0
.end method

.method public makeQuickSettings(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 20
    new-instance v0, Lcom/mediatek/systemui/ext/DefaultQuickSettingsPlugin;

    invoke-direct {v0, p1}, Lcom/mediatek/systemui/ext/DefaultQuickSettingsPlugin;-><init>(Landroid/content/Context;)V

    return-object v0
.end method

.method public makeStatusBarPlmn(Landroid/content/Context;)Lcom/mediatek/systemui/ext/IStatusBarPlmnPlugin;
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 24
    new-instance v0, Lcom/mediatek/systemui/ext/DefaultStatusBarPlmnPlugin;

    invoke-direct {v0, p1}, Lcom/mediatek/systemui/ext/DefaultStatusBarPlmnPlugin;-><init>(Landroid/content/Context;)V

    return-object v0
.end method

.method public makeSystemUIStatusBar(Landroid/content/Context;)Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt;
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 28
    new-instance v0, Lcom/mediatek/systemui/ext/DefaultSystemUIStatusBarExt;

    invoke-direct {v0, p1}, Lcom/mediatek/systemui/ext/DefaultSystemUIStatusBarExt;-><init>(Landroid/content/Context;)V

    return-object v0
.end method
