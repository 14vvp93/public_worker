.class Lcom/hcn/common/misc/HNetworkUtils$1;
.super Lcom/hcn/common/utils/HUtilsEx$Task;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils;->isAvailableAsync(Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/hcn/common/utils/HUtilsEx$Task<",
        "Ljava/lang/Boolean;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 0

    .line 104
    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HUtilsEx$Task;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    return-void
.end method


# virtual methods
.method public doInBackground()Ljava/lang/Boolean;
    .locals 1

    .line 108
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailable()Z

    move-result v0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    return-object v0
.end method

.method public bridge synthetic doInBackground()Ljava/lang/Object;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    .line 104
    invoke-virtual {p0}, Lcom/hcn/common/misc/HNetworkUtils$1;->doInBackground()Ljava/lang/Boolean;

    move-result-object v0

    return-object v0
.end method
