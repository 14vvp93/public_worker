.class public Lcom/hcn/skin/support/app/attr/TextColorAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "TextColorAttr.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 18
    invoke-direct {p0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;-><init>()V

    return-void
.end method


# virtual methods
.method protected applySkin(Landroid/view/View;)V
    .locals 2

    .line 23
    instance-of v0, p1, Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 24
    check-cast p1, Landroid/widget/TextView;

    .line 25
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/TextColorAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 27
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(Landroid/content/res/ColorStateList;)V

    :cond_0
    return-void
.end method
