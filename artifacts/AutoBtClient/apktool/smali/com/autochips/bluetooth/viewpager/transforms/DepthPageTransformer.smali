.class public Lcom/autochips/bluetooth/viewpager/transforms/DepthPageTransformer;
.super Lcom/autochips/bluetooth/viewpager/transforms/ABaseTransformer;
.source "DepthPageTransformer.java"


# static fields
.field private static final MIN_SCALE:F = 0.75f


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 21
    invoke-direct {p0}, Lcom/autochips/bluetooth/viewpager/transforms/ABaseTransformer;-><init>()V

    return-void
.end method


# virtual methods
.method protected isPagingEnabled()Z
    .locals 1

    const/4 v0, 0x1

    return v0
.end method

.method protected onTransform(Landroid/view/View;F)V
    .locals 5

    const/4 v0, 0x0

    cmpg-float v1, p2, v0

    const/4 v2, 0x0

    const/high16 v3, 0x3f800000    # 1.0f

    if-gtz v1, :cond_0

    .line 28
    invoke-virtual {p1, v2}, Landroid/view/View;->setVisibility(I)V

    .line 29
    invoke-virtual {p1, v0}, Landroid/view/View;->setTranslationX(F)V

    .line 30
    invoke-virtual {p1, v3}, Landroid/view/View;->setScaleX(F)V

    .line 31
    invoke-virtual {p1, v3}, Landroid/view/View;->setScaleY(F)V

    goto :goto_0

    :cond_0
    cmpg-float v0, p2, v3

    if-gez v0, :cond_1

    const/high16 v0, 0x3f400000    # 0.75f

    const/high16 v1, 0x3e800000    # 0.25f

    .line 33
    invoke-static {p2}, Ljava/lang/Math;->abs(F)F

    move-result v4

    sub-float v4, v3, v4

    mul-float/2addr v4, v1

    add-float/2addr v4, v0

    .line 34
    invoke-virtual {p1, v2}, Landroid/view/View;->setVisibility(I)V

    sub-float/2addr v3, p2

    .line 35
    invoke-virtual {p1, v3}, Landroid/view/View;->setAlpha(F)V

    const/high16 v0, 0x3f000000    # 0.5f

    .line 36
    invoke-virtual {p1}, Landroid/view/View;->getHeight()I

    move-result v1

    int-to-float v1, v1

    mul-float/2addr v1, v0

    invoke-virtual {p1, v1}, Landroid/view/View;->setPivotY(F)V

    .line 37
    invoke-virtual {p1}, Landroid/view/View;->getWidth()I

    move-result v0

    int-to-float v0, v0

    neg-float p2, p2

    mul-float/2addr v0, p2

    invoke-virtual {p1, v0}, Landroid/view/View;->setTranslationX(F)V

    .line 38
    invoke-virtual {p1, v4}, Landroid/view/View;->setScaleX(F)V

    .line 39
    invoke-virtual {p1, v4}, Landroid/view/View;->setScaleY(F)V

    goto :goto_0

    :cond_1
    cmpl-float p2, p2, v3

    if-ltz p2, :cond_2

    const/4 p2, 0x4

    .line 41
    invoke-virtual {p1, p2}, Landroid/view/View;->setVisibility(I)V

    :cond_2
    :goto_0
    return-void
.end method
