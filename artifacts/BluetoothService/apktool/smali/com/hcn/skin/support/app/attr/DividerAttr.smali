.class public Lcom/hcn/skin/support/app/attr/DividerAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "DividerAttr.java"


# instance fields
.field public dividerHeight:I


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 17
    invoke-direct {p0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;-><init>()V

    const/4 v0, 0x1

    .line 19
    iput v0, p0, Lcom/hcn/skin/support/app/attr/DividerAttr;->dividerHeight:I

    return-void
.end method


# virtual methods
.method protected applySkin(Landroid/view/View;)V
    .locals 2

    .line 23
    instance-of v0, p1, Landroid/widget/ListView;

    if-eqz v0, :cond_1

    .line 24
    check-cast p1, Landroid/widget/ListView;

    .line 25
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/DividerAttr;->isDrawable()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 26
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/DividerAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 28
    invoke-virtual {p1, v0}, Landroid/widget/ListView;->setDivider(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 30
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/DividerAttr;->isColor()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 31
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/DividerAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColor(I)I

    move-result v0

    if-lez v0, :cond_1

    .line 33
    new-instance v1, Landroid/graphics/drawable/ColorDrawable;

    invoke-direct {v1, v0}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    .line 34
    invoke-virtual {p1, v1}, Landroid/widget/ListView;->setDivider(Landroid/graphics/drawable/Drawable;)V

    .line 35
    iget v0, p0, Lcom/hcn/skin/support/app/attr/DividerAttr;->dividerHeight:I

    invoke-virtual {p1, v0}, Landroid/widget/ListView;->setDividerHeight(I)V

    :cond_1
    :goto_0
    return-void
.end method
