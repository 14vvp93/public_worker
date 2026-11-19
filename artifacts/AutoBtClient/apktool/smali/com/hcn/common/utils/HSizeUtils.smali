.class public Lcom/hcn/common/utils/HSizeUtils;
.super Ljava/lang/Object;
.source "HSizeUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;
    }
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 16
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 17
    new-instance v0, Ljava/lang/UnsupportedOperationException;

    const-string v1, "u can\'t instantiate me..."

    invoke-direct {v0, v1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static applyDimension(FI)F
    .locals 2

    .line 75
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    if-eqz p1, :cond_5

    const/4 v1, 0x1

    if-eq p1, v1, :cond_4

    const/4 v1, 0x2

    if-eq p1, v1, :cond_3

    const/4 v1, 0x3

    if-eq p1, v1, :cond_2

    const/4 v1, 0x4

    if-eq p1, v1, :cond_1

    const/4 v1, 0x5

    if-eq p1, v1, :cond_0

    const/4 p0, 0x0

    return p0

    .line 88
    :cond_0
    iget p1, v0, Landroid/util/DisplayMetrics;->xdpi:F

    mul-float/2addr p0, p1

    const p1, 0x3d214285

    :goto_0
    mul-float/2addr p0, p1

    return p0

    .line 86
    :cond_1
    iget p1, v0, Landroid/util/DisplayMetrics;->xdpi:F

    goto :goto_0

    .line 84
    :cond_2
    iget p1, v0, Landroid/util/DisplayMetrics;->xdpi:F

    mul-float/2addr p0, p1

    const p1, 0x3c638e39

    goto :goto_0

    .line 82
    :cond_3
    iget p1, v0, Landroid/util/DisplayMetrics;->scaledDensity:F

    goto :goto_0

    .line 80
    :cond_4
    iget p1, v0, Landroid/util/DisplayMetrics;->density:F

    goto :goto_0

    :cond_5
    return p0
.end method

.method public static dp2px(F)I
    .locals 1

    .line 27
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->density:F

    mul-float/2addr p0, v0

    const/high16 v0, 0x3f000000    # 0.5f

    add-float/2addr p0, v0

    float-to-int p0, p0

    return p0
.end method

.method public static forceGetViewSize(Landroid/view/View;Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;)V
    .locals 1

    .line 109
    new-instance v0, Lcom/hcn/common/utils/HSizeUtils$1;

    invoke-direct {v0, p1, p0}, Lcom/hcn/common/utils/HSizeUtils$1;-><init>(Lcom/hcn/common/utils/HSizeUtils$OnGetSizeListener;Landroid/view/View;)V

    invoke-virtual {p0, v0}, Landroid/view/View;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public static getMeasuredHeight(Landroid/view/View;)I
    .locals 1

    .line 136
    invoke-static {p0}, Lcom/hcn/common/utils/HSizeUtils;->measureView(Landroid/view/View;)[I

    move-result-object p0

    const/4 v0, 0x1

    aget p0, p0, v0

    return p0
.end method

.method public static getMeasuredWidth(Landroid/view/View;)I
    .locals 1

    .line 126
    invoke-static {p0}, Lcom/hcn/common/utils/HSizeUtils;->measureView(Landroid/view/View;)[I

    move-result-object p0

    const/4 v0, 0x0

    aget p0, p0, v0

    return p0
.end method

.method public static measureView(Landroid/view/View;)[I
    .locals 4

    .line 146
    invoke-virtual {p0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    if-nez v0, :cond_0

    .line 148
    new-instance v0, Landroid/view/ViewGroup$LayoutParams;

    const/4 v1, -0x1

    const/4 v2, -0x2

    invoke-direct {v0, v1, v2}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    .line 153
    :cond_0
    iget v1, v0, Landroid/view/ViewGroup$LayoutParams;->width:I

    const/4 v2, 0x0

    invoke-static {v2, v2, v1}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    move-result v1

    .line 154
    iget v0, v0, Landroid/view/ViewGroup$LayoutParams;->height:I

    if-lez v0, :cond_1

    const/high16 v3, 0x40000000    # 2.0f

    .line 157
    invoke-static {v0, v3}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    move-result v0

    goto :goto_0

    .line 159
    :cond_1
    invoke-static {v2, v2}, Landroid/view/View$MeasureSpec;->makeMeasureSpec(II)I

    move-result v0

    .line 161
    :goto_0
    invoke-virtual {p0, v1, v0}, Landroid/view/View;->measure(II)V

    const/4 v0, 0x2

    new-array v0, v0, [I

    .line 162
    invoke-virtual {p0}, Landroid/view/View;->getMeasuredWidth()I

    move-result v1

    aput v1, v0, v2

    const/4 v1, 0x1

    invoke-virtual {p0}, Landroid/view/View;->getMeasuredHeight()I

    move-result p0

    aput p0, v0, v1

    return-object v0
.end method

.method public static px2dp(F)I
    .locals 1

    .line 38
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->density:F

    div-float/2addr p0, v0

    const/high16 v0, 0x3f000000    # 0.5f

    add-float/2addr p0, v0

    float-to-int p0, p0

    return p0
.end method

.method public static px2sp(F)I
    .locals 1

    .line 60
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->scaledDensity:F

    div-float/2addr p0, v0

    const/high16 v0, 0x3f000000    # 0.5f

    add-float/2addr p0, v0

    float-to-int p0, p0

    return p0
.end method

.method public static sp2px(F)I
    .locals 1

    .line 49
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->scaledDensity:F

    mul-float/2addr p0, v0

    const/high16 v0, 0x3f000000    # 0.5f

    add-float/2addr p0, v0

    float-to-int p0, p0

    return p0
.end method
