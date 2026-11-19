.class Lcom/hcn/common/misc/HNetworkUtils$7;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils;->addOnWifiChangedConsumer(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 836
    iput-object p1, p0, Lcom/hcn/common/misc/HNetworkUtils$7;->val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 839
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 840
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$7;->val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 841
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$smstartScanWifi()V

    return-void

    .line 844
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$7;->val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetsPreWifiScanResults()Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    move-result-object v1

    invoke-interface {v0, v1}, Lcom/hcn/common/utils/HUtilsEx$Consumer;->accept(Ljava/lang/Object;)V

    .line 845
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$7;->val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    return-void
.end method
