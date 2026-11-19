.class Lcom/hcn/common/app/HToastUtils$ActivityToast$2;
.super Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;
.source "HToastUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HToastUtils$ActivityToast;->registerLifecycleCallback()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/app/HToastUtils$ActivityToast;

.field final synthetic val$index:I


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HToastUtils$ActivityToast;I)V
    .locals 0

    .line 781
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;->this$0:Lcom/hcn/common/app/HToastUtils$ActivityToast;

    iput p2, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;->val$index:I

    invoke-direct {p0}, Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;-><init>()V

    return-void
.end method


# virtual methods
.method public onActivityCreated(Landroid/app/Activity;)V
    .locals 3

    .line 784
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;->this$0:Lcom/hcn/common/app/HToastUtils$ActivityToast;

    invoke-static {v0}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->-$$Nest$misShowing(Lcom/hcn/common/app/HToastUtils$ActivityToast;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 785
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;->this$0:Lcom/hcn/common/app/HToastUtils$ActivityToast;

    iget v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;->val$index:I

    const/4 v2, 0x0

    invoke-static {v0, p1, v1, v2}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->-$$Nest$mshowWithActivityView(Lcom/hcn/common/app/HToastUtils$ActivityToast;Landroid/app/Activity;IZ)V

    :cond_0
    return-void
.end method
