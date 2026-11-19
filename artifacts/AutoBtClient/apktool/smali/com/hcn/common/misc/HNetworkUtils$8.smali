.class Lcom/hcn/common/misc/HNetworkUtils$8;
.super Ljava/util/TimerTask;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils;->startScanWifi()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 853
    invoke-direct {p0}, Ljava/util/TimerTask;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 857
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$smstartScanWifiIfEnabled()V

    .line 858
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getWifiScanResult()Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    move-result-object v0

    .line 859
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfgetsPreWifiScanResults()Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    move-result-object v1

    invoke-static {v1}, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;->-$$Nest$fgetallResults(Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;)Ljava/util/List;

    move-result-object v1

    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;->-$$Nest$fgetallResults(Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;)Ljava/util/List;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$smisSameScanResults(Ljava/util/List;Ljava/util/List;)Z

    move-result v1

    if-eqz v1, :cond_0

    return-void

    .line 862
    :cond_0
    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils;->-$$Nest$sfputsPreWifiScanResults(Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;)V

    .line 863
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$8$1;

    invoke-direct {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$8$1;-><init>(Lcom/hcn/common/misc/HNetworkUtils$8;)V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method
