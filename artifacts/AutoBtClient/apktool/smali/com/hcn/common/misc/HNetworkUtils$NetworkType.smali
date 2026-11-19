.class public final enum Lcom/hcn/common/misc/HNetworkUtils$NetworkType;
.super Ljava/lang/Enum;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HNetworkUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "NetworkType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/hcn/common/misc/HNetworkUtils$NetworkType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_2G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_3G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_4G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_5G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_ETHERNET:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_NO:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_UNKNOWN:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

.field public static final enum NETWORK_WIFI:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;


# direct methods
.method private static synthetic $values()[Lcom/hcn/common/misc/HNetworkUtils$NetworkType;
    .locals 3

    const/16 v0, 0x8

    new-array v0, v0, [Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 62
    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_ETHERNET:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_WIFI:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x1

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_5G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x2

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_4G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x3

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_3G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x4

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_2G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x5

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_UNKNOWN:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x6

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_NO:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const/4 v2, 0x7

    aput-object v1, v0, v2

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 3

    .line 63
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_ETHERNET"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_ETHERNET:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 64
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_WIFI"

    const/4 v2, 0x1

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_WIFI:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 65
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_5G"

    const/4 v2, 0x2

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_5G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 66
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_4G"

    const/4 v2, 0x3

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_4G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 67
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_3G"

    const/4 v2, 0x4

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_3G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 68
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_2G"

    const/4 v2, 0x5

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_2G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 69
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_UNKNOWN"

    const/4 v2, 0x6

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_UNKNOWN:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 70
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    const-string v1, "NETWORK_NO"

    const/4 v2, 0x7

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_NO:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    .line 62
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->$values()[Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->$VALUES:[Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 62
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/hcn/common/misc/HNetworkUtils$NetworkType;
    .locals 1

    .line 62
    const-class v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object p0
.end method

.method public static values()[Lcom/hcn/common/misc/HNetworkUtils$NetworkType;
    .locals 1

    .line 62
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->$VALUES:[Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    invoke-virtual {v0}, [Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0
.end method
