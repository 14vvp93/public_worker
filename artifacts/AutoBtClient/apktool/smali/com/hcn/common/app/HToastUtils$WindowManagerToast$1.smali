.class Lcom/hcn/common/app/HToastUtils$WindowManagerToast$1;
.super Ljava/lang/Object;
.source "HToastUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HToastUtils$WindowManagerToast;->show(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/app/HToastUtils$WindowManagerToast;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HToastUtils$WindowManagerToast;)V
    .locals 0

    .line 646
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast$1;->this$0:Lcom/hcn/common/app/HToastUtils$WindowManagerToast;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .line 649
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast$1;->this$0:Lcom/hcn/common/app/HToastUtils$WindowManagerToast;

    invoke-virtual {v0}, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;->cancel()V

    return-void
.end method
