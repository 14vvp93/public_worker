.class Lcom/hcn/common/app/HToastUtils$2;
.super Ljava/lang/Object;
.source "HToastUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HToastUtils;->show(Landroid/view/View;Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$duration:I

.field final synthetic val$text:Ljava/lang/CharSequence;

.field final synthetic val$utils:Lcom/hcn/common/app/HToastUtils;

.field final synthetic val$view:Landroid/view/View;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HToastUtils;Landroid/view/View;Ljava/lang/CharSequence;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 488
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$2;->val$utils:Lcom/hcn/common/app/HToastUtils;

    iput-object p2, p0, Lcom/hcn/common/app/HToastUtils$2;->val$view:Landroid/view/View;

    iput-object p3, p0, Lcom/hcn/common/app/HToastUtils$2;->val$text:Ljava/lang/CharSequence;

    iput p4, p0, Lcom/hcn/common/app/HToastUtils$2;->val$duration:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 491
    invoke-static {}, Lcom/hcn/common/app/HToastUtils;->cancel()V

    .line 492
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$2;->val$utils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v0}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$smnewToast(Lcom/hcn/common/app/HToastUtils;)Lcom/hcn/common/app/HToastUtils$IToast;

    move-result-object v0

    .line 493
    new-instance v1, Ljava/lang/ref/WeakReference;

    invoke-direct {v1, v0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    invoke-static {v1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$sfputsWeakToast(Ljava/lang/ref/WeakReference;)V

    .line 494
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$2;->val$view:Landroid/view/View;

    if-eqz v1, :cond_0

    .line 495
    invoke-interface {v0, v1}, Lcom/hcn/common/app/HToastUtils$IToast;->setToastView(Landroid/view/View;)V

    goto :goto_0

    .line 497
    :cond_0
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$2;->val$text:Ljava/lang/CharSequence;

    invoke-interface {v0, v1}, Lcom/hcn/common/app/HToastUtils$IToast;->setToastView(Ljava/lang/CharSequence;)V

    .line 499
    :goto_0
    iget v1, p0, Lcom/hcn/common/app/HToastUtils$2;->val$duration:I

    invoke-interface {v0, v1}, Lcom/hcn/common/app/HToastUtils$IToast;->show(I)V

    return-void
.end method
