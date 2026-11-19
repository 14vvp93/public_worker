.class final Lcom/hcn/common/app/HToastUtils$SystemToast;
.super Lcom/hcn/common/app/HToastUtils$AbsToast;
.source "HToastUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HToastUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "SystemToast"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;
    }
.end annotation


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HToastUtils;)V
    .locals 3

    .line 543
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HToastUtils$AbsToast;-><init>(Lcom/hcn/common/app/HToastUtils;)V

    .line 544
    sget p1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v0, 0x19

    if-ne p1, v0, :cond_0

    .line 548
    :try_start_0
    const-class p1, Landroid/widget/Toast;

    const-string v0, "mTN"

    invoke-virtual {p1, v0}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object p1

    const/4 v0, 0x1

    .line 549
    invoke-virtual {p1, v0}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 550
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$SystemToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {p1, v1}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    .line 551
    invoke-virtual {p1}, Ljava/lang/reflect/Field;->getType()Ljava/lang/Class;

    move-result-object p1

    const-string v2, "mHandler"

    invoke-virtual {p1, v2}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object p1

    .line 552
    invoke-virtual {p1, v0}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 553
    invoke-virtual {p1, v1}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/Handler;

    .line 554
    new-instance v2, Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;

    invoke-direct {v2, v0}, Lcom/hcn/common/app/HToastUtils$SystemToast$SafeHandler;-><init>(Landroid/os/Handler;)V

    invoke-virtual {p1, v1, v2}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    :cond_0
    return-void
.end method


# virtual methods
.method public show(I)V
    .locals 1

    .line 561
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$SystemToast;->mToast:Landroid/widget/Toast;

    if-nez v0, :cond_0

    return-void

    .line 564
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$SystemToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v0, p1}, Landroid/widget/Toast;->setDuration(I)V

    .line 565
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$SystemToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    return-void
.end method
