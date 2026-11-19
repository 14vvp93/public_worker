.class Lcom/hcn/common/misc/HNetworkUtils$8$1;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils$8;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HNetworkUtils$8;


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HNetworkUtils$8;)V
    .locals 0

    .line 863
    iput-object p1, p0, Lcom/hcn/common/misc/HNetworkUtils$8$1;->this$0:Lcom/hcn/common/misc/HNetworkUtils$8;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 866
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/utils/HUtilsEx$Consumer;

    .line 867
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetsPreWifiScanResults()Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    move-result-object v2

    invoke-interface {v1, v2}, Lcom/hcn/common/utils/HUtilsEx$Consumer;->accept(Ljava/lang/Object;)V

    goto :goto_0

    :cond_0
    return-void
.end method
