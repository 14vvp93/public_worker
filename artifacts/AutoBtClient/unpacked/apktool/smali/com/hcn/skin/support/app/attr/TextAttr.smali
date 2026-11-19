.class public Lcom/hcn/skin/support/app/attr/TextAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "TextAttr.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 16
    invoke-direct {p0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;-><init>()V

    return-void
.end method


# virtual methods
.method protected applySkin(Landroid/view/View;)V
    .locals 3

    .line 20
    instance-of v0, p1, Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 21
    check-cast p1, Landroid/widget/TextView;

    .line 22
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/TextAttr;->attrValueRefId:I

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getString(IZ)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 24
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_0
    return-void
.end method
