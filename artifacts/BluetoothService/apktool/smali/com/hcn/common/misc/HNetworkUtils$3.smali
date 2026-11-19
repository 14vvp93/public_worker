.class Lcom/hcn/common/misc/HNetworkUtils$3;
.super Lcom/hcn/common/utils/HUtilsEx$Task;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByDnsAsync(Ljava/lang/String;Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
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


# instance fields
.field final synthetic val$domain:Ljava/lang/String;


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;Ljava/lang/String;)V
    .locals 0

    .line 205
    iput-object p2, p0, Lcom/hcn/common/misc/HNetworkUtils$3;->val$domain:Ljava/lang/String;

    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HUtilsEx$Task;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    return-void
.end method


# virtual methods
.method public doInBackground()Ljava/lang/Boolean;
    .locals 1

    .line 209
    iget-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$3;->val$domain:Ljava/lang/String;

    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByDns(Ljava/lang/String;)Z

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

    .line 205
    invoke-virtual {p0}, Lcom/hcn/common/misc/HNetworkUtils$3;->doInBackground()Ljava/lang/Boolean;

    move-result-object v0

    return-object v0
.end method
