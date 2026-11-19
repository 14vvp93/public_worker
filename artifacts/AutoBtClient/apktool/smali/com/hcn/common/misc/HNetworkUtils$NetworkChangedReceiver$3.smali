.class Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->onReceive(Landroid/content/Context;Landroid/content/Intent;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)V
    .locals 0

    .line 996
    iput-object p1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1000
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getNetworkType()Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    move-result-object v0

    .line 1001
    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v1}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fgetmType(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    move-result-object v1

    if-ne v1, v0, :cond_0

    return-void

    .line 1005
    :cond_0
    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v1, v0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fputmType(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;Lcom/hcn/common/misc/HNetworkUtils$NetworkType;)V

    .line 1006
    sget-object v1, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_NO:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    if-ne v0, v1, :cond_1

    .line 1007
    iget-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fgetmListeners(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;

    .line 1008
    invoke-interface {v1}, Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;->onDisconnected()V

    goto :goto_0

    .line 1011
    :cond_1
    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v1}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fgetmListeners(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Ljava/util/Set;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;

    .line 1012
    invoke-interface {v2, v0}, Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;->onConnected(Lcom/hcn/common/misc/HNetworkUtils$NetworkType;)V

    goto :goto_1

    :cond_2
    return-void
.end method
