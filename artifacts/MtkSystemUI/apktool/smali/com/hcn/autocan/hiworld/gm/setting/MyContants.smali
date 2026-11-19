.class public Lcom/hcn/autocan/hiworld/gm/setting/MyContants;
.super Ljava/lang/Object;
.source "MyContants.java"


# static fields
.field public static DOOT_MAP:[[I

.field public static REF_MODE:Z

.field public static SYSTEM_TEMP_F:I

.field public static TAG:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    .line 4
    const-string v0, "canDebug->"

    sput-object v0, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->TAG:Ljava/lang/String;

    .line 5
    const/4 v0, 0x0

    sput-boolean v0, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->REF_MODE:Z

    .line 7
    sput v0, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->SYSTEM_TEMP_F:I

    .line 102
    const/4 v1, 0x6

    new-array v1, v1, [[I

    const/4 v2, 0x2

    new-array v3, v2, [I

    fill-array-data v3, :array_0

    aput-object v3, v1, v0

    new-array v0, v2, [I

    fill-array-data v0, :array_1

    const/4 v3, 0x1

    aput-object v0, v1, v3

    new-array v0, v2, [I

    fill-array-data v0, :array_2

    aput-object v0, v1, v2

    new-array v0, v2, [I

    fill-array-data v0, :array_3

    const/4 v3, 0x3

    aput-object v0, v1, v3

    new-array v0, v2, [I

    fill-array-data v0, :array_4

    const/4 v3, 0x4

    aput-object v0, v1, v3

    new-array v0, v2, [I

    fill-array-data v0, :array_5

    const/4 v2, 0x5

    aput-object v0, v1, v2

    sput-object v1, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->DOOT_MAP:[[I

    return-void

    :array_0
    .array-data 4
        0x0
        0x0
    .end array-data

    :array_1
    .array-data 4
        0x1
        0x1
    .end array-data

    :array_2
    .array-data 4
        0x2
        0x2
    .end array-data

    :array_3
    .array-data 4
        0x3
        0x3
    .end array-data

    :array_4
    .array-data 4
        0x4
        0x4
    .end array-data

    :array_5
    .array-data 4
        0x5
        0x5
    .end array-data
.end method
