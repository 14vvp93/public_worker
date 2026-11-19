.class Lcom/hcn/common/app/HPermissionUtils$1;
.super Ljava/lang/Object;
.source "HPermissionUtils.java"

# interfaces
.implements Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener$ShouldRequest;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HPermissionUtils;->rationalInner(Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/app/HPermissionUtils;

.field final synthetic val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

.field final synthetic val$againRunnable:Ljava/lang/Runnable;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HPermissionUtils;Ljava/lang/Runnable;Lcom/hcn/common/app/HUtilsTransActivity;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 374
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$1;->this$0:Lcom/hcn/common/app/HPermissionUtils;

    iput-object p2, p0, Lcom/hcn/common/app/HPermissionUtils$1;->val$againRunnable:Ljava/lang/Runnable;

    iput-object p3, p0, Lcom/hcn/common/app/HPermissionUtils$1;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public again(Z)V
    .locals 1

    if-eqz p1, :cond_0

    .line 378
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$1;->this$0:Lcom/hcn/common/app/HPermissionUtils;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-static {p1, v0}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fputmPermissionsDenied(Lcom/hcn/common/app/HPermissionUtils;Ljava/util/List;)V

    .line 379
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$1;->this$0:Lcom/hcn/common/app/HPermissionUtils;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-static {p1, v0}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fputmPermissionsDeniedForever(Lcom/hcn/common/app/HPermissionUtils;Ljava/util/List;)V

    .line 380
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$1;->val$againRunnable:Ljava/lang/Runnable;

    invoke-interface {p1}, Ljava/lang/Runnable;->run()V

    goto :goto_0

    .line 382
    :cond_0
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$1;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    .line 383
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$1;->this$0:Lcom/hcn/common/app/HPermissionUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$mrequestCallback(Lcom/hcn/common/app/HPermissionUtils;)V

    :goto_0
    return-void
.end method
