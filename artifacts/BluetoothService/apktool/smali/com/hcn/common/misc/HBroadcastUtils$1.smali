.class Lcom/hcn/common/misc/HBroadcastUtils$1;
.super Landroid/os/Handler;
.source "HBroadcastUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/HBroadcastUtils;-><init>(Landroid/content/Context;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HBroadcastUtils;


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HBroadcastUtils;Landroid/os/Looper;)V
    .locals 0

    .line 91
    iput-object p1, p0, Lcom/hcn/common/misc/HBroadcastUtils$1;->this$0:Lcom/hcn/common/misc/HBroadcastUtils;

    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 95
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    .line 100
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    goto :goto_0

    .line 97
    :cond_0
    iget-object p1, p0, Lcom/hcn/common/misc/HBroadcastUtils$1;->this$0:Lcom/hcn/common/misc/HBroadcastUtils;

    invoke-virtual {p1}, Lcom/hcn/common/misc/HBroadcastUtils;->executePendingBroadcasts()V

    :goto_0
    return-void
.end method
