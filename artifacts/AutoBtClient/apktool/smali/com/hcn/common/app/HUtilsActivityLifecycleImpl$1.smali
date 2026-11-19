.class Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;
.super Ljava/lang/Object;
.source "HUtilsActivityLifecycleImpl.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->addActivityLifecycleCallbacks(Landroid/app/Activity;Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$listener:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;Landroid/app/Activity;Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 96
    iput-object p1, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;->this$0:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

    iput-object p2, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;->val$activity:Landroid/app/Activity;

    iput-object p3, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;->val$listener:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 99
    iget-object v0, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;->this$0:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

    iget-object v1, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;->val$activity:Landroid/app/Activity;

    iget-object v2, p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl$1;->val$listener:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;

    invoke-static {v0, v1, v2}, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->-$$Nest$maddActivityLifecycleCallbacksInner(Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;Landroid/app/Activity;Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;)V

    return-void
.end method
