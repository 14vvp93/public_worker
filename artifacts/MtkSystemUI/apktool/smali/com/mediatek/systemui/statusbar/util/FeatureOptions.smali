.class public Lcom/mediatek/systemui/statusbar/util/FeatureOptions;
.super Ljava/lang/Object;
.source "FeatureOptions.java"


# static fields
.field public static final LOG_ENABLE:Z

.field public static final LOW_RAM_SUPPORT:Z

.field public static final MTK_CTA_SET:Z

.field public static final MTK_CT_MIXED_VOLTE_SUPPORT:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 26
    const-string v0, "ro.config.low_ram"

    invoke-static {v0}, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->isPropertyEnabledBoolean(Ljava/lang/String;)Z

    move-result v0

    sput-boolean v0, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->LOW_RAM_SUPPORT:Z

    .line 28
    const-string v0, "ro.vendor.mtk_cta_set"

    invoke-static {v0}, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->isPropertyEnabledInt(Ljava/lang/String;)Z

    move-result v0

    sput-boolean v0, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->MTK_CTA_SET:Z

    .line 31
    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->isMtkCtMixedVolteSupport()Z

    move-result v0

    sput-boolean v0, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->MTK_CT_MIXED_VOLTE_SUPPORT:Z

    .line 38
    const-string v0, "ro.build.type"

    .line 39
    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "eng"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "ro.build.type"

    .line 40
    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string/jumbo v1, "userdebug"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    sput-boolean v0, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->LOG_ENABLE:Z

    .line 38
    return-void
.end method

.method private static isMtkCtMixedVolteSupport()Z
    .locals 4

    .line 73
    const/4 v0, 0x0

    .line 74
    .local v0, "ret":Z
    sget-boolean v1, Lcom/mediatek/systemui/statusbar/util/FeatureOptions;->LOG_ENABLE:Z

    if-eqz v1, :cond_0

    .line 75
    const-string v1, "FeatureOptions"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "isMtkCtMixedVolteSupport: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 77
    :cond_0
    return v0
.end method

.method private static isPropertyEnabledBoolean(Ljava/lang/String;)Z
    .locals 2
    .param p0, "propertyString"    # Ljava/lang/String;

    .line 48
    const-string v0, "true"

    const-string v1, "false"

    invoke-static {p0, v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method private static isPropertyEnabledInt(Ljava/lang/String;)Z
    .locals 2
    .param p0, "propertyString"    # Ljava/lang/String;

    .line 57
    const-string v0, "1"

    invoke-static {p0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method
