.class Lcom/hcn/common/misc/HNetworkUtils$9;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils;->removeOnWifiChangedConsumer(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
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

    .line 892
    iput-object p1, p0, Lcom/hcn/common/misc/HNetworkUtils$9;->val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 895
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$9;->val$consumer:Lcom/hcn/common/utils/HUtilsEx$Consumer;

    invoke-interface {v0, v1}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    .line 896
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 897
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$smstopScanWifi()V

    :cond_0
    return-void
.end method
