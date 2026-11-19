.class Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;
.super Ljava/lang/Object;
.source "HPermissionUtils.java"

# interfaces
.implements Lcom/hcn/common/app/HPermissionUtils$OnExplainListener$ShouldRequest;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->onCreated(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

.field final synthetic val$activity:Lcom/hcn/common/app/HUtilsTransActivity;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;Lcom/hcn/common/app/HUtilsTransActivity;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 482
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;->this$0:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

    iput-object p2, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public start(Z)V
    .locals 1

    if-nez p1, :cond_0

    .line 486
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    goto :goto_0

    .line 488
    :cond_0
    iget-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;->this$0:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-static {p1, v0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->-$$Nest$mrequestPermissions(Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;Lcom/hcn/common/app/HUtilsTransActivity;)V

    :goto_0
    return-void
.end method
