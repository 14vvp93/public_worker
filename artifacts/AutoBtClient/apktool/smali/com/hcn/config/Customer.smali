.class public Lcom/hcn/config/Customer;
.super Ljava/lang/Object;
.source "Customer.java"


# static fields
.field public static final FOTA_FEATURE:Ljava/lang/String;

.field public static final NAVIGATION_BAR_HEIGHT:I

.field public static final ZE_XUN_ELECTRONICS:Ljava/lang/String; = "ZXDZ"

.field private static sCustomerName:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const-string v0, "ro.fota.device"

    const-string v1, "None"

    .line 40
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/config/Customer;->FOTA_FEATURE:Ljava/lang/String;

    const-string v0, "ro.sys.customer.naviheight"

    const/4 v1, 0x0

    .line 44
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    sput v0, Lcom/hcn/config/Customer;->NAVIGATION_BAR_HEIGHT:I

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 10
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static name()Ljava/lang/String;
    .locals 2

    .line 28
    sget-object v0, Lcom/hcn/config/Customer;->sCustomerName:Ljava/lang/String;

    if-nez v0, :cond_0

    const-string v0, "persist.sys.customer"

    const-string v1, ""

    .line 29
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/config/Customer;->sCustomerName:Ljava/lang/String;

    .line 31
    :cond_0
    sget-object v0, Lcom/hcn/config/Customer;->sCustomerName:Ljava/lang/String;

    return-object v0
.end method
