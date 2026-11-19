.class public final enum Lcom/hcn/common/utils/HImageUtils$ImageType;
.super Ljava/lang/Enum;
.source "HImageUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HImageUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ImageType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/hcn/common/utils/HImageUtils$ImageType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_BMP:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_GIF:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_ICO:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_JPG:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_PNG:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_TIFF:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_UNKNOWN:Lcom/hcn/common/utils/HImageUtils$ImageType;

.field public static final enum TYPE_WEBP:Lcom/hcn/common/utils/HImageUtils$ImageType;


# instance fields
.field value:Ljava/lang/String;


# direct methods
.method private static synthetic $values()[Lcom/hcn/common/utils/HImageUtils$ImageType;
    .locals 3

    const/16 v0, 0x8

    new-array v0, v0, [Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1341
    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_JPG:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_PNG:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x1

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_GIF:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x2

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_TIFF:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x3

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_BMP:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x4

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_WEBP:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x5

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_ICO:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x6

    aput-object v1, v0, v2

    sget-object v1, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_UNKNOWN:Lcom/hcn/common/utils/HImageUtils$ImageType;

    const/4 v2, 0x7

    aput-object v1, v0, v2

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 4

    .line 1342
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_JPG"

    const/4 v2, 0x0

    const-string v3, "jpg"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_JPG:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1343
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_PNG"

    const/4 v2, 0x1

    const-string v3, "png"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_PNG:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1344
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_GIF"

    const/4 v2, 0x2

    const-string v3, "gif"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_GIF:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1345
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_TIFF"

    const/4 v2, 0x3

    const-string v3, "tiff"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_TIFF:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1346
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_BMP"

    const/4 v2, 0x4

    const-string v3, "bmp"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_BMP:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1347
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_WEBP"

    const/4 v2, 0x5

    const-string v3, "webp"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_WEBP:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1348
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_ICO"

    const/4 v2, 0x6

    const-string v3, "ico"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_ICO:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1349
    new-instance v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    const-string v1, "TYPE_UNKNOWN"

    const/4 v2, 0x7

    const-string v3, "unknown"

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/utils/HImageUtils$ImageType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->TYPE_UNKNOWN:Lcom/hcn/common/utils/HImageUtils$ImageType;

    .line 1341
    invoke-static {}, Lcom/hcn/common/utils/HImageUtils$ImageType;->$values()[Lcom/hcn/common/utils/HImageUtils$ImageType;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->$VALUES:[Lcom/hcn/common/utils/HImageUtils$ImageType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;ILjava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .line 1353
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 1354
    iput-object p3, p0, Lcom/hcn/common/utils/HImageUtils$ImageType;->value:Ljava/lang/String;

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/hcn/common/utils/HImageUtils$ImageType;
    .locals 1

    .line 1341
    const-class v0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/hcn/common/utils/HImageUtils$ImageType;

    return-object p0
.end method

.method public static values()[Lcom/hcn/common/utils/HImageUtils$ImageType;
    .locals 1

    .line 1341
    sget-object v0, Lcom/hcn/common/utils/HImageUtils$ImageType;->$VALUES:[Lcom/hcn/common/utils/HImageUtils$ImageType;

    invoke-virtual {v0}, [Lcom/hcn/common/utils/HImageUtils$ImageType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/hcn/common/utils/HImageUtils$ImageType;

    return-object v0
.end method


# virtual methods
.method public getValue()Ljava/lang/String;
    .locals 1

    .line 1358
    iget-object v0, p0, Lcom/hcn/common/utils/HImageUtils$ImageType;->value:Ljava/lang/String;

    return-object v0
.end method
