.class public Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;
.super Ljava/lang/Object;
.source "OpKeyguardCustomizationFactoryBase.java"


# static fields
.field static sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

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

    .line 27
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    .line 30
    sget-object v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v1, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP01SystemUI.apk"

    const-string v3, "com.mediatek.keyguard.op01.Op01KeyguardCustomizationFactory"

    const-string v4, "com.mediatek.systemui.op01"

    const-string v5, "OP01"

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 37
    sget-object v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v1, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP02SystemUI.apk"

    const-string v3, "com.mediatek.keyguard.op02.Op02KeyguardCustomizationFactory"

    const-string v4, "com.mediatek.systemui.op02"

    const-string v5, "OP02"

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 44
    sget-object v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP09SystemUI.apk"

    const-string v3, "com.mediatek.keyguard.op09.Op09KeyguardCustomizationFactory"

    const-string v4, "com.mediatek.systemui.op09"

    const-string v5, "OP09"

    const-string v6, "SEGDEFAULT"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 52
    sget-object v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    new-instance v7, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;

    const-string v2, "OP09ClipSystemUI.apk"

    const-string v3, "com.mediatek.keyguard.op09clip.Op09KeyguardCustomizationFactory"

    const-string v4, "com.mediatek.systemui.op09clip"

    const-string v5, "OP09"

    const-string v6, "SEGC"

    move-object v1, v7

    invoke-direct/range {v1 .. v6}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader$OperatorFactoryInfo;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 62
    const/4 v0, 0x0

    sput-object v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static declared-synchronized getOpFactory(Landroid/content/Context;)Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;
    .locals 2
    .param p0, "context"    # Landroid/content/Context;

    const-class v0, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    monitor-enter v0

    .line 64
    :try_start_0
    sget-object v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    if-nez v1, :cond_0

    .line 65
    sget-object v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sOpFactoryInfoList:Ljava/util/List;

    .line 66
    invoke-static {p0, v1}, Lcom/mediatek/common/util/OperatorCustomizationFactoryLoader;->loadFactory(Landroid/content/Context;Ljava/util/List;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    sput-object v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    .line 67
    sget-object v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    if-nez v1, :cond_0

    .line 68
    new-instance v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    invoke-direct {v1}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;-><init>()V

    sput-object v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    .line 71
    :cond_0
    sget-object v1, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->sFactory:Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    .line 63
    .end local p0    # "context":Landroid/content/Context;
    :catchall_0
    move-exception p0

    monitor-exit v0

    throw p0
.end method


# virtual methods
.method public makeCarrierText()Lcom/mediatek/keyguard/ext/ICarrierTextExt;
    .locals 1

    .line 12
    new-instance v0, Lcom/mediatek/keyguard/ext/DefaultCarrierTextExt;

    invoke-direct {v0}, Lcom/mediatek/keyguard/ext/DefaultCarrierTextExt;-><init>()V

    return-object v0
.end method

.method public makeEmergencyButton()Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;
    .locals 1

    .line 16
    new-instance v0, Lcom/mediatek/keyguard/ext/DefaultEmergencyButtonExt;

    invoke-direct {v0}, Lcom/mediatek/keyguard/ext/DefaultEmergencyButtonExt;-><init>()V

    return-object v0
.end method

.method public makeKeyguardUtil()Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;
    .locals 1

    .line 20
    new-instance v0, Lcom/mediatek/keyguard/ext/DefaultKeyguardUtilExt;

    invoke-direct {v0}, Lcom/mediatek/keyguard/ext/DefaultKeyguardUtilExt;-><init>()V

    return-object v0
.end method

.method public makeOperatorSIMString()Lcom/mediatek/keyguard/ext/IOperatorSIMString;
    .locals 1

    .line 24
    new-instance v0, Lcom/mediatek/keyguard/ext/DefaultOperatorSIMString;

    invoke-direct {v0}, Lcom/mediatek/keyguard/ext/DefaultOperatorSIMString;-><init>()V

    return-object v0
.end method
