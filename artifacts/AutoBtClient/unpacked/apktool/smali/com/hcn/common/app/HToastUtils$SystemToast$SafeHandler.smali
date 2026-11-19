.class Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;
.super Landroid/os/Handler;
.source "HToastUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HToastUtils$SystemToast;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "SafeHandler"
.end annotation


# instance fields
.field private impl:Landroid/os/Handler;


# direct methods
.method constructor <init>(Landroid/os/Handler;)V
    .locals 0

    .line 571
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    .line 572
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;->impl:Landroid/os/Handler;

    return-void
.end method


# virtual methods
.method public dispatchMessage(Landroid/os/Message;)V
    .locals 1

    .line 583
    :try_start_0
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;->impl:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->dispatchMessage(Landroid/os/Message;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 585
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :goto_0
    return-void
.end method

.method public handleMessage(Landroid/os/Message;)V
    .locals 1

    .line 577
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;->impl:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    return-void
.end method
