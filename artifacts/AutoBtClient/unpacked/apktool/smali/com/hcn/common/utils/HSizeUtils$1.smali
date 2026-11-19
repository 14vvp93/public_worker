.class Lcom/hcn/common/utils/HSizeUtils$1;
.super Ljava/lang/Object;
.source "HSizeUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/utils/HSizeUtils;->forceGetViewSize(Landroid/view/View;Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$listener:Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;

.field final synthetic val$view:Landroid/view/View;


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;Landroid/view/View;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 109
    iput-object p1, p0, Lcom/hcn/common/utils/HSizeUtils$1;->val$listener:Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;

    iput-object p2, p0, Lcom/hcn/common/utils/HSizeUtils$1;->val$view:Landroid/view/View;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 112
    iget-object v0, p0, Lcom/hcn/common/utils/HSizeUtils$1;->val$listener:Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;

    if-eqz v0, :cond_0

    .line 113
    iget-object v1, p0, Lcom/hcn/common/utils/HSizeUtils$1;->val$view:Landroid/view/View;

    invoke-interface {v0, v1}, Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;->onGetSize(Landroid/view/View;)V

    :cond_0
    return-void
.end method
