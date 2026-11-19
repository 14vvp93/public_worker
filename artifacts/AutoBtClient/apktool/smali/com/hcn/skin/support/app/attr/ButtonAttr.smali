.class public Lcom/hcn/skin/support/app/attr/ButtonAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "ButtonAttr.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "ButtonAttr"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 20
    invoke-direct {p0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;-><init>()V

    return-void
.end method


# virtual methods
.method protected applySkin(Landroid/view/View;)V
    .locals 2
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .line 28
    instance-of v0, p1, Landroid/widget/CheckBox;

    if-eqz v0, :cond_2

    .line 29
    check-cast p1, Landroid/widget/CheckBox;

    .line 30
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ButtonAttr;->isColor()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 32
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ButtonAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 34
    invoke-virtual {p1, v0}, Landroid/widget/CheckBox;->setButtonTintList(Landroid/content/res/ColorStateList;)V

    goto :goto_0

    .line 36
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ButtonAttr;->isDrawable()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 38
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ButtonAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 40
    invoke-virtual {p1, v0}, Landroid/widget/CheckBox;->setButtonDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 42
    :cond_1
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/ButtonAttr;->isAnim()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 44
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/ButtonAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 46
    invoke-virtual {p1, v0}, Landroid/widget/CheckBox;->setButtonDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_2
    :goto_0
    return-void
.end method
