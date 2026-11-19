.class Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$4;
.super Ljava/lang/Object;
.source "HUtilsActivityLifecycleImpl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->processHideSoftInputOnActivityDestroy(Landroid/app/Activity;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$tag:Ljava/lang/Object;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;Landroid/app/Activity;Ljava/lang/Object;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 345
    iput-object p1, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$4;->this$0:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

    iput-object p2, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$4;->val$activity:Landroid/app/Activity;

    iput-object p3, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$4;->val$tag:Ljava/lang/Object;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 349
    :try_start_0
    iget-object v0, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$4;->val$activity:Landroid/app/Activity;

    invoke-virtual {v0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 351
    iget-object v1, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$4;->val$tag:Ljava/lang/Object;

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/Window;->setSoftInputMode(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    :cond_0
    return-void
.end method
