.class public Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "ProgressDrawableAttr.java"


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
    instance-of v0, p1, Landroid/widget/SeekBar;

    if-eqz v0, :cond_1

    .line 24
    check-cast p1, Landroid/widget/SeekBar;

    .line 25
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;->isDrawable()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 26
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 29
    invoke-virtual {p1, v0}, Landroid/widget/SeekBar;->setProgressDrawableTiled(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 31
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;->isColor()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 32
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColor(I)I

    move-result v0

    if-lez v0, :cond_1

    .line 34
    new-instance v1, Landroid/graphics/drawable/ColorDrawable;

    invoke-direct {v1, v0}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    .line 36
    invoke-virtual {p1, v1}, Landroid/widget/SeekBar;->setProgressDrawableTiled(Landroid/graphics/drawable/Drawable;)V

    :cond_1
    :goto_0
    return-void
.end method
