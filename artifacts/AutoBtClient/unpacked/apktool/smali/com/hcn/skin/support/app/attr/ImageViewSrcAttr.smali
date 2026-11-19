.class public Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "ImageViewSrcAttr.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ImageViewSrcAttr"


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

    .line 24
    instance-of v0, p1, Landroid/widget/ImageView;

    if-eqz v0, :cond_2

    .line 25
    check-cast p1, Landroid/widget/ImageView;

    .line 26
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;->isDrawable()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 28
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 30
    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 32
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;->isColor()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 34
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColor(I)I

    move-result v0

    if-lez v0, :cond_2

    .line 36
    new-instance v1, Landroid/graphics/drawable/ColorDrawable;

    invoke-direct {v1, v0}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    .line 37
    invoke-virtual {p1, v1}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 39
    :cond_1
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;->isAnim()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 41
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 43
    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_2
    :goto_0
    return-void
.end method
