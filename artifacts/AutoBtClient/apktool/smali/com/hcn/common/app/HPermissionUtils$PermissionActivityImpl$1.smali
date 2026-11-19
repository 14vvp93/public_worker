.class Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$1;
.super Ljava/lang/Object;
.source "HPermissionUtils.java"

# interfaces
.implements Lcom/hcn/common/utils/HUtilsEx$Consumer;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->start(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
        "Landroid/content/Intent;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic val$type:I


# direct methods
.method constructor <init>(I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 449
    iput p1, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$1;->val$type:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public accept(Landroid/content/Intent;)V
    .locals 2

    .line 452
    iget v0, p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$1;->val$type:I

    const-string v1, "TYPE"

    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    return-void
.end method

.method public bridge synthetic accept(Ljava/lang/Object;)V
    .locals 0

    .line 449
    check-cast p1, Landroid/content/Intent;

    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$1;->accept(Landroid/content/Intent;)V

    return-void
.end method
