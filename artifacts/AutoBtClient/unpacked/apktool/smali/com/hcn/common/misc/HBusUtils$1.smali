.class Lcom/hcn/common/misc/HBusUtils$1;
.super Ljava/lang/Object;
.source "HBusUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HBusUtils;->invokeMethod(Ljava/lang/Object;Ljava/lang/Object;Lcom/hcn/common/misc/HBusUtils$HBusInfo;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HBusUtils;

.field final synthetic val$arg:Ljava/lang/Object;

.field final synthetic val$bus:Ljava/lang/Object;

.field final synthetic val$busInfo:Lcom/hcn/common/misc/HBusUtils$HBusInfo;

.field final synthetic val$sticky:Z


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HBusUtils;Ljava/lang/Object;Ljava/lang/Object;Lcom/hcn/common/misc/HBusUtils$HBusInfo;Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 287
    iput-object p1, p0, Lcom/hcn/common/misc/HBusUtils$1;->this$0:Lcom/hcn/common/misc/HBusUtils;

    iput-object p2, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$bus:Ljava/lang/Object;

    iput-object p3, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$arg:Ljava/lang/Object;

    iput-object p4, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$busInfo:Lcom/hcn/common/misc/HBusUtils$HBusInfo;

    iput-boolean p5, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$sticky:Z

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .line 290
    iget-object v0, p0, Lcom/hcn/common/misc/HBusUtils$1;->this$0:Lcom/hcn/common/misc/HBusUtils;

    iget-object v1, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$bus:Ljava/lang/Object;

    iget-object v2, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$arg:Ljava/lang/Object;

    iget-object v3, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$busInfo:Lcom/hcn/common/misc/HBusUtils$HBusInfo;

    iget-boolean v4, p0, Lcom/hcn/common/misc/HBusUtils$1;->val$sticky:Z

    invoke-static {v0, v1, v2, v3, v4}, Lcom/hcn/common/misc/HBusUtils;->-$$Nest$mrealInvokeMethod(Lcom/hcn/common/misc/HBusUtils;Ljava/lang/Object;Ljava/lang/Object;Lcom/hcn/common/misc/HBusUtils$HBusInfo;Z)V

    return-void
.end method
