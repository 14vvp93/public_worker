.class public Lcom/mediatek/systemui/statusbar/networktype/NetworkTypeUtils;
.super Ljava/lang/Object;
.source "NetworkTypeUtils.java"


# static fields
.field static final sNetworkTypeIcons:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 37
    new-instance v0, Lcom/mediatek/systemui/statusbar/networktype/NetworkTypeUtils$1;

    invoke-direct {v0}, Lcom/mediatek/systemui/statusbar/networktype/NetworkTypeUtils$1;-><init>()V

    sput-object v0, Lcom/mediatek/systemui/statusbar/networktype/NetworkTypeUtils;->sNetworkTypeIcons:Ljava/util/Map;

    return-void
.end method

.method private static getNetworkType(Landroid/telephony/ServiceState;)I
    .locals 2
    .param p0, "serviceState"    # Landroid/telephony/ServiceState;

    .line 87
    const/4 v0, 0x0

    .line 88
    .local v0, "type":I
    if-eqz p0, :cond_1

    .line 89
    invoke-virtual {p0}, Landroid/telephony/ServiceState;->getDataNetworkType()I

    move-result v1

    if-eqz v1, :cond_0

    .line 90
    invoke-virtual {p0}, Landroid/telephony/ServiceState;->getDataNetworkType()I

    move-result v1

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroid/telephony/ServiceState;->getVoiceNetworkType()I

    move-result v1

    :goto_0
    move v0, v1

    .line 92
    :cond_1
    return v0
.end method

.method public static getNetworkTypeIcon(Landroid/telephony/ServiceState;Lcom/android/systemui/statusbar/policy/NetworkControllerImpl$Config;Z)I
    .locals 4
    .param p0, "serviceState"    # Landroid/telephony/ServiceState;
    .param p1, "config"    # Lcom/android/systemui/statusbar/policy/NetworkControllerImpl$Config;
    .param p2, "hasService"    # Z

    .line 71
    const/4 v0, 0x0

    if-nez p2, :cond_0

    .line 73
    return v0

    .line 75
    :cond_0
    invoke-static {p0}, Lcom/mediatek/systemui/statusbar/networktype/NetworkTypeUtils;->getNetworkType(Landroid/telephony/ServiceState;)I

    move-result v1

    .line 77
    .local v1, "tempNetworkType":I
    sget-object v2, Lcom/mediatek/systemui/statusbar/networktype/NetworkTypeUtils;->sNetworkTypeIcons:Ljava/util/Map;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    .line 78
    .local v2, "iconId":Ljava/lang/Integer;
    if-nez v2, :cond_3

    .line 79
    if-nez v1, :cond_1

    goto :goto_0

    .line 80
    :cond_1
    iget-boolean v0, p1, Lcom/android/systemui/statusbar/policy/NetworkControllerImpl$Config;->showAtLeast3G:Z

    if-eqz v0, :cond_2

    const v0, 0x7f08062d

    goto :goto_0

    .line 81
    :cond_2
    const v0, 0x7f080630

    .line 79
    :goto_0
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    .line 83
    :cond_3
    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v0

    return v0
.end method
