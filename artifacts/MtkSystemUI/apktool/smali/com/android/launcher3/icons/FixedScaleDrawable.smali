.class public Lcom/android/launcher3/icons/FixedScaleDrawable;
.super Landroid/graphics/drawable/DrawableWrapper;
.source "FixedScaleDrawable.java"


# instance fields
.field private mScaleX:F

.field private mScaleY:F

.field private mTranslateX:F

.field private mTranslateY:F


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 28
    new-instance v0, Landroid/graphics/drawable/ColorDrawable;

    invoke-direct {v0}, Landroid/graphics/drawable/ColorDrawable;-><init>()V

    invoke-direct {p0, v0}, Landroid/graphics/drawable/DrawableWrapper;-><init>(Landroid/graphics/drawable/Drawable;)V

    .line 29
    const/high16 v0, 0x3f800000    # 1.0f

    iput v0, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleX:F

    .line 30
    iput v0, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    .line 33
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    const-string v1, "icon_translate_x"

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mTranslateX:F

    .line 34
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    const-string v1, "icon_translate_y"

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mTranslateY:F

    .line 36
    return-void
.end method


# virtual methods
.method public draw(Landroid/graphics/Canvas;)V
    .locals 5
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 40
    invoke-virtual {p1}, Landroid/graphics/Canvas;->save()I

    move-result v0

    .line 41
    .local v0, "saveCount":I
    iget v1, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mTranslateX:F

    iget v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mTranslateY:F

    invoke-virtual {p1, v1, v2}, Landroid/graphics/Canvas;->translate(FF)V

    .line 42
    iget v1, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleX:F

    iget v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    .line 43
    invoke-virtual {p0}, Lcom/android/launcher3/icons/FixedScaleDrawable;->getBounds()Landroid/graphics/Rect;

    move-result-object v3

    invoke-virtual {v3}, Landroid/graphics/Rect;->exactCenterX()F

    move-result v3

    invoke-virtual {p0}, Lcom/android/launcher3/icons/FixedScaleDrawable;->getBounds()Landroid/graphics/Rect;

    move-result-object v4

    invoke-virtual {v4}, Landroid/graphics/Rect;->exactCenterY()F

    move-result v4

    .line 42
    invoke-virtual {p1, v1, v2, v3, v4}, Landroid/graphics/Canvas;->scale(FFFF)V

    .line 44
    invoke-super {p0, p1}, Landroid/graphics/drawable/DrawableWrapper;->draw(Landroid/graphics/Canvas;)V

    .line 45
    invoke-virtual {p1, v0}, Landroid/graphics/Canvas;->restoreToCount(I)V

    .line 46
    return-void
.end method

.method public inflate(Landroid/content/res/Resources;Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;)V
    .locals 0
    .param p1, "r"    # Landroid/content/res/Resources;
    .param p2, "parser"    # Lorg/xmlpull/v1/XmlPullParser;
    .param p3, "attrs"    # Landroid/util/AttributeSet;

    .line 49
    return-void
.end method

.method public inflate(Landroid/content/res/Resources;Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/content/res/Resources$Theme;)V
    .locals 0
    .param p1, "r"    # Landroid/content/res/Resources;
    .param p2, "parser"    # Lorg/xmlpull/v1/XmlPullParser;
    .param p3, "attrs"    # Landroid/util/AttributeSet;
    .param p4, "theme"    # Landroid/content/res/Resources$Theme;

    .line 52
    return-void
.end method

.method public setScale(F)V
    .locals 6
    .param p1, "scale"    # F

    .line 55
    invoke-virtual {p0}, Lcom/android/launcher3/icons/FixedScaleDrawable;->getIntrinsicHeight()I

    move-result v0

    int-to-float v0, v0

    .line 56
    .local v0, "h":F
    invoke-virtual {p0}, Lcom/android/launcher3/icons/FixedScaleDrawable;->getIntrinsicWidth()I

    move-result v1

    int-to-float v1, v1

    .line 57
    .local v1, "w":F
    const/high16 v2, 0x3f800000    # 1.0f

    mul-float v3, p1, v2

    iput v3, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleX:F

    .line 59
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    const-string v4, "icon_size_scale_y"

    invoke-virtual {v3, v4}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v3

    int-to-float v3, v3

    iput v3, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    .line 60
    iget v3, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    const/4 v4, 0x0

    cmpl-float v3, v3, v4

    if-nez v3, :cond_0

    .line 61
    iget v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleX:F

    iput v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    goto :goto_0

    .line 63
    :cond_0
    iget v3, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    const v5, 0x3c23d70a    # 0.01f

    mul-float/2addr v3, v5

    const v5, 0x3f2aacda    # 0.6667f

    mul-float/2addr v3, v5

    mul-float/2addr v3, v2

    iput v3, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    .line 65
    :goto_0
    cmpl-float v2, v0, v1

    if-lez v2, :cond_1

    cmpl-float v2, v1, v4

    if-lez v2, :cond_1

    .line 66
    iget v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleX:F

    div-float v3, v1, v0

    mul-float/2addr v2, v3

    iput v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleX:F

    goto :goto_1

    .line 67
    :cond_1
    cmpl-float v2, v1, v0

    if-lez v2, :cond_2

    cmpl-float v2, v0, v4

    if-lez v2, :cond_2

    .line 68
    iget v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    div-float v3, v0, v1

    mul-float/2addr v2, v3

    iput v2, p0, Lcom/android/launcher3/icons/FixedScaleDrawable;->mScaleY:F

    .line 70
    :cond_2
    :goto_1
    return-void
.end method
