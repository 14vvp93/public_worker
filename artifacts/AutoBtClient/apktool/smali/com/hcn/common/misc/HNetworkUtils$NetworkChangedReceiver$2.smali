.class Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->unregisterListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

.field final synthetic val$listener:Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 979
    iput-object p1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    iput-object p2, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;->val$listener:Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 982
    iget-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fgetmListeners(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->size()I

    move-result v0

    .line 983
    iget-object v1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v1}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fgetmListeners(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Ljava/util/Set;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;->val$listener:Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;

    invoke-interface {v1, v2}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 984
    iget-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;->this$0:Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$fgetmListeners(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->size()I

    move-result v0

    if-nez v0, :cond_0

    .line 985
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$smgetInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/Application;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    :cond_0
    return-void
.end method
