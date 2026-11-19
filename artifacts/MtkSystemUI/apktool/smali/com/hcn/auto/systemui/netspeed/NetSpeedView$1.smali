.class Lcom/hcn/auto/systemui/netspeed/NetSpeedView$1;
.super Landroid/database/ContentObserver;
.source "NetSpeedView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/auto/systemui/netspeed/NetSpeedView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/auto/systemui/netspeed/NetSpeedView;


# direct methods
.method constructor <init>(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;Landroid/os/Handler;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/auto/systemui/netspeed/NetSpeedView;
    .param p2, "x0"    # Landroid/os/Handler;

    .line 89
    iput-object p1, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView$1;->this$0:Lcom/hcn/auto/systemui/netspeed/NetSpeedView;

    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .locals 3
    .param p1, "selfChange"    # Z

    .line 92
    iget-object v0, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView$1;->this$0:Lcom/hcn/auto/systemui/netspeed/NetSpeedView;

    invoke-static {v0}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->access$000(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)V

    .line 93
    invoke-static {}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->access$100()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "net speed changed to:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/systemui/netspeed/NetSpeedView$1;->this$0:Lcom/hcn/auto/systemui/netspeed/NetSpeedView;

    invoke-static {v2}, Lcom/hcn/auto/systemui/netspeed/NetSpeedView;->access$200(Lcom/hcn/auto/systemui/netspeed/NetSpeedView;)Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 94
    return-void
.end method
