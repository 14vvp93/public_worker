.class public Lcom/hcn/skin/support/app/attr/BackgroundAttr;
.super Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.source "BackgroundAttr.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "BackgroundAttr"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 16
    invoke-direct {p0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;-><init>()V

    return-void
.end method


# virtual methods
.method protected applySkin(Landroid/view/View;)V
    .locals 2
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .line 23
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/BackgroundAttr;->isColor()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 25
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/BackgroundAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColor(I)I

    move-result v0

    if-lez v0, :cond_2

    .line 27
    invoke-virtual {p1, v0}, Landroid/view/View;->setBackgroundColor(I)V

    goto :goto_0

    .line 29
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/BackgroundAttr;->isDrawable()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 31
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/BackgroundAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 33
    invoke-virtual {p1, v0}, Landroid/view/View;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 35
    :cond_1
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/BackgroundAttr;->isAnim()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 37
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/attr/BackgroundAttr;->attrValueRefId:I

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 39
    invoke-virtual {p1, v0}, Landroid/view/View;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_2
    :goto_0
    return-void
.end method
