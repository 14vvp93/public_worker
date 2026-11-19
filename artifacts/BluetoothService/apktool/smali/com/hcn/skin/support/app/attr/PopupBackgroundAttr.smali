.class public Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "PopupBackgroundAttr.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "PopupBackgroundAttr"


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

    .line 25
    instance-of v0, p1, Landroid/widget/Spinner;

    if-eqz v0, :cond_1

    .line 26
    check-cast p1, Landroid/widget/Spinner;

    .line 27
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;->isDrawable()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 28
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 30
    invoke-virtual {p1, v0}, Landroid/widget/Spinner;->setPopupBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 32
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;->isAnim()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 33
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 35
    invoke-virtual {p1, v0}, Landroid/widget/Spinner;->setPopupBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_1
    :goto_0
    return-void
.end method
