.class final Lcom/hcn/common/utils/HMessageUtils$LazyHolder;
.super Ljava/lang/Object;
.source "HMessageUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HMessageUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "LazyHolder"
.end annotation


# static fields
.field private static final INSTANCE:Lcom/hcn/common/utils/HMessageUtils;


# direct methods
.method static bridge synthetic -$$Nest$sfgetINSTANCE()Lcom/hcn/common/utils/HMessageUtils;
    .locals 1

    sget-object v0, Lcom/hcn/common/utils/HMessageUtils$LazyHolder;->INSTANCE:Lcom/hcn/common/utils/HMessageUtils;

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 3

    .line 455
    new-instance v0, Lcom/hcn/common/utils/HMessageUtils;

    const-string v1, "LazyHolder"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/utils/HMessageUtils;-><init>(Ljava/lang/String;Lcom/hcn/common/utils/HMessageUtils-IA;)V

    sput-object v0, Lcom/hcn/common/utils/HMessageUtils$LazyHolder;->INSTANCE:Lcom/hcn/common/utils/HMessageUtils;

    .line 458
    invoke-static {}, Lcom/hcn/common/utils/HMessageUtils;->-$$Nest$sfgetsPool()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v1

    invoke-virtual {v0}, Lcom/hcn/common/utils/HMessageUtils;->name()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2, v0}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 454
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
