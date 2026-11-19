.class Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$LazyHolder;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "LazyHolder"
.end annotation


# static fields
.field private static final INSTANCE:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;


# direct methods
.method static bridge synthetic -$$Nest$sfgetINSTANCE()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;
    .locals 1

    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$LazyHolder;->INSTANCE:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 1021
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-direct {v0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;-><init>()V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$LazyHolder;->INSTANCE:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 1020
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
