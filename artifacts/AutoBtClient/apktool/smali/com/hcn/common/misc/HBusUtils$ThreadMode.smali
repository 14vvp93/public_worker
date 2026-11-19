.class public final enum Lcom/hcn/common/misc/HBusUtils$ThreadMode;
.super Ljava/lang/Enum;
.source "HBusUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HBusUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ThreadMode"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/hcn/common/misc/HBusUtils$ThreadMode;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/hcn/common/misc/HBusUtils$ThreadMode;

.field public static final enum CACHED:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

.field public static final enum CPU:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

.field public static final enum IO:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

.field public static final enum MAIN:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

.field public static final enum POSTING:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

.field public static final enum SINGLE:Lcom/hcn/common/misc/HBusUtils$ThreadMode;


# direct methods
.method private static synthetic $values()[Lcom/hcn/common/misc/HBusUtils$ThreadMode;
    .locals 3

    const/4 v0, 0x6

    new-array v0, v0, [Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    .line 448
    sget-object v1, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->MAIN:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->IO:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const/4 v2, 0x1

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->CPU:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const/4 v2, 0x2

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->CACHED:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const/4 v2, 0x3

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->SINGLE:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const/4 v2, 0x4

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->POSTING:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const/4 v2, 0x5

    aput-object v1, v0, v2

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 3

    .line 449
    new-instance v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const-string v1, "MAIN"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->MAIN:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    new-instance v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const-string v1, "IO"

    const/4 v2, 0x1

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->IO:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    new-instance v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const-string v1, "CPU"

    const/4 v2, 0x2

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->CPU:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    new-instance v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const-string v1, "CACHED"

    const/4 v2, 0x3

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->CACHED:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    new-instance v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const-string v1, "SINGLE"

    const/4 v2, 0x4

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->SINGLE:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    new-instance v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    const-string v1, "POSTING"

    const/4 v2, 0x5

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->POSTING:Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    .line 448
    invoke-static {}, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->$values()[Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->$VALUES:[Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 448
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/hcn/common/misc/HBusUtils$ThreadMode;
    .locals 1

    .line 448
    const-class v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    return-object p0
.end method

.method public static values()[Lcom/hcn/common/misc/HBusUtils$ThreadMode;
    .locals 1

    .line 448
    sget-object v0, Lcom/hcn/common/misc/HBusUtils$ThreadMode;->$VALUES:[Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    invoke-virtual {v0}, [Lcom/hcn/common/misc/HBusUtils$ThreadMode;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/hcn/common/misc/HBusUtils$ThreadMode;

    return-object v0
.end method
