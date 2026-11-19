.class Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$3;
.super Ljava/lang/Object;
.source "HPermissionUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->requestPermissions(Lcom/hcn/common/app/HUtilsTransActivity;)V
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

    .line 509
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$3;->this$0:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

    iput-object p2, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$3;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 512
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$3;->val$activity:Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object v1

    invoke-static {v1}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;

    move-result-object v1

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/String;

    invoke-interface {v1, v2}, Ljava/util/List;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v1

    check-cast v1, [Ljava/lang/String;

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Lcom/hcn/common/app/HUtilsTransActivity;->requestPermissions([Ljava/lang/String;I)V

    return-void
.end method
