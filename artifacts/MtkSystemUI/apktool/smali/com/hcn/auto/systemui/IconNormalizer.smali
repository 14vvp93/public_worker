.class public Lcom/hcn/auto/systemui/IconNormalizer;
.super Ljava/lang/Object;
.source "IconNormalizer.java"


# instance fields
.field private final mAdaptiveIconBounds:Landroid/graphics/RectF;

.field private mAdaptiveIconScale:F

.field private final mBitmap:Landroid/graphics/Bitmap;

.field private final mBounds:Landroid/graphics/Rect;

.field private final mCanvas:Landroid/graphics/Canvas;

.field private mEnableShapeDetection:Z

.field private final mLeftBorder:[F

.field private final mMatrix:Landroid/graphics/Matrix;

.field private final mMaxSize:I

.field private final mPaintMaskShape:Landroid/graphics/Paint;

.field private final mPaintMaskShapeOutline:Landroid/graphics/Paint;

.field private final mPixels:[B

.field private final mRightBorder:[F

.field private final mShapePath:Landroid/graphics/Path;


# direct methods
.method constructor <init>(Landroid/content/Context;IZ)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "iconBitmapSize"    # I
    .param p3, "shapeDetection"    # Z

    .line 53
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 54
    mul-int/lit8 v0, p2, 0x2

    iput v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    .line 55
    iget v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    iget v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    sget-object v2, Landroid/graphics/Bitmap$Config;->ALPHA_8:Landroid/graphics/Bitmap$Config;

    invoke-static {v0, v1, v2}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBitmap:Landroid/graphics/Bitmap;

    .line 56
    new-instance v0, Landroid/graphics/Canvas;

    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBitmap:Landroid/graphics/Bitmap;

    invoke-direct {v0, v1}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mCanvas:Landroid/graphics/Canvas;

    .line 57
    iget v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    iget v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    mul-int/2addr v0, v1

    new-array v0, v0, [B

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPixels:[B

    .line 58
    iget v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    new-array v0, v0, [F

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mLeftBorder:[F

    .line 59
    iget v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    new-array v0, v0, [F

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mRightBorder:[F

    .line 60
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    .line 61
    new-instance v0, Landroid/graphics/RectF;

    invoke-direct {v0}, Landroid/graphics/RectF;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconBounds:Landroid/graphics/RectF;

    .line 62
    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShape:Landroid/graphics/Paint;

    .line 63
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShape:Landroid/graphics/Paint;

    const/high16 v1, -0x10000

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setColor(I)V

    .line 64
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShape:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 65
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShape:Landroid/graphics/Paint;

    new-instance v1, Landroid/graphics/PorterDuffXfermode;

    sget-object v2, Landroid/graphics/PorterDuff$Mode;->XOR:Landroid/graphics/PorterDuff$Mode;

    invoke-direct {v1, v2}, Landroid/graphics/PorterDuffXfermode;-><init>(Landroid/graphics/PorterDuff$Mode;)V

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setXfermode(Landroid/graphics/Xfermode;)Landroid/graphics/Xfermode;

    .line 66
    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShapeOutline:Landroid/graphics/Paint;

    .line 67
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShapeOutline:Landroid/graphics/Paint;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v1

    iget v1, v1, Landroid/util/DisplayMetrics;->density:F

    const/high16 v2, 0x40000000    # 2.0f

    mul-float/2addr v2, v1

    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 68
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShapeOutline:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 69
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShapeOutline:Landroid/graphics/Paint;

    const/high16 v1, -0x1000000

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setColor(I)V

    .line 70
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShapeOutline:Landroid/graphics/Paint;

    new-instance v1, Landroid/graphics/PorterDuffXfermode;

    sget-object v2, Landroid/graphics/PorterDuff$Mode;->CLEAR:Landroid/graphics/PorterDuff$Mode;

    invoke-direct {v1, v2}, Landroid/graphics/PorterDuffXfermode;-><init>(Landroid/graphics/PorterDuff$Mode;)V

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setXfermode(Landroid/graphics/Xfermode;)Landroid/graphics/Xfermode;

    .line 71
    new-instance v0, Landroid/graphics/Path;

    invoke-direct {v0}, Landroid/graphics/Path;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mShapePath:Landroid/graphics/Path;

    .line 72
    new-instance v0, Landroid/graphics/Matrix;

    invoke-direct {v0}, Landroid/graphics/Matrix;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMatrix:Landroid/graphics/Matrix;

    .line 73
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconScale:F

    .line 74
    iput-boolean p3, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mEnableShapeDetection:Z

    .line 75
    return-void
.end method

.method private static convertToConvexArray([FIII)V
    .locals 12
    .param p0, "xCoordinates"    # [F
    .param p1, "direction"    # I
    .param p2, "topY"    # I
    .param p3, "bottomY"    # I

    .line 251
    array-length v0, p0

    .line 252
    .local v0, "total":I
    add-int/lit8 v1, v0, -0x1

    new-array v1, v1, [F

    .line 253
    .local v1, "angles":[F
    move v2, p2

    .line 254
    .local v2, "first":I
    const/4 v3, -0x1

    .line 255
    .local v3, "last":I
    const v4, 0x7f7fffff    # Float.MAX_VALUE

    .line 257
    .local v4, "lastAngle":F
    add-int/lit8 v5, p2, 0x1

    .local v5, "i":I
    :goto_0
    if-gt v5, p3, :cond_6

    .line 258
    aget v6, p0, v5

    const/high16 v7, -0x40800000    # -1.0f

    cmpg-float v6, v6, v7

    if-lez v6, :cond_5

    .line 260
    const v6, 0x7f7fffff    # Float.MAX_VALUE

    cmpl-float v6, v4, v6

    if-nez v6, :cond_0

    .line 261
    move v6, v2

    .local v6, "start":I
    goto :goto_1

    .line 263
    .end local v6    # "start":I
    :cond_0
    aget v6, p0, v5

    aget v7, p0, v3

    sub-float/2addr v6, v7

    sub-int v7, v5, v3

    int-to-float v7, v7

    div-float/2addr v6, v7

    .line 264
    .local v6, "currentAngle":F
    move v7, v3

    .line 265
    .local v7, "start":I
    sub-float v8, v6, v4

    int-to-float v9, p1

    mul-float/2addr v8, v9

    const/4 v9, 0x0

    cmpg-float v8, v8, v9

    if-gez v8, :cond_2

    .line 266
    move v11, v7

    move v7, v6

    move v6, v11

    .local v6, "start":I
    .local v7, "currentAngle":F
    :cond_1
    if-le v6, v2, :cond_3

    .line 267
    add-int/lit8 v6, v6, -0x1

    .line 268
    aget v8, p0, v5

    aget v10, p0, v6

    sub-float/2addr v8, v10

    sub-int v10, v5, v6

    int-to-float v10, v10

    div-float v7, v8, v10

    .line 269
    aget v8, v1, v6

    sub-float v8, v7, v8

    int-to-float v10, p1

    mul-float/2addr v8, v10

    cmpl-float v8, v8, v9

    if-ltz v8, :cond_1

    .line 270
    goto :goto_1

    .line 276
    .end local v6    # "start":I
    .local v7, "start":I
    :cond_2
    move v6, v7

    .end local v7    # "start":I
    .restart local v6    # "start":I
    :cond_3
    :goto_1
    aget v7, p0, v5

    aget v8, p0, v6

    sub-float/2addr v7, v8

    sub-int v8, v5, v6

    int-to-float v8, v8

    div-float/2addr v7, v8

    .line 278
    .end local v4    # "lastAngle":F
    .local v7, "lastAngle":F
    move v4, v6

    .local v4, "j":I
    :goto_2
    if-ge v4, v5, :cond_4

    .line 279
    aput v7, v1, v4

    .line 280
    aget v8, p0, v6

    sub-int v9, v4, v6

    int-to-float v9, v9

    mul-float/2addr v9, v7

    add-float/2addr v8, v9

    aput v8, p0, v4

    .line 278
    add-int/lit8 v4, v4, 0x1

    goto :goto_2

    .line 283
    .end local v4    # "j":I
    :cond_4
    move v3, v5

    .line 257
    .end local v6    # "start":I
    move v4, v7

    .end local v7    # "lastAngle":F
    .local v4, "lastAngle":F
    :cond_5
    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 287
    .end local v5    # "i":I
    :cond_6
    return-void
.end method

.method private static getScale(FFF)F
    .locals 6
    .param p0, "hullArea"    # F
    .param p1, "boundingArea"    # F
    .param p2, "fullArea"    # F

    .line 78
    div-float v0, p0, p1

    .line 80
    .local v0, "hullByRect":F
    const v1, 0x3f490fdb

    cmpg-float v1, v0, v1

    const/high16 v2, 0x3f800000    # 1.0f

    if-gez v1, :cond_0

    .line 81
    const v1, 0x3f28e38e

    .local v1, "scaleRequired":F
    goto :goto_0

    .line 83
    .end local v1    # "scaleRequired":F
    :cond_0
    const v1, 0x3f26aaab

    const v3, 0x3d25ae4f

    sub-float v4, v2, v0

    mul-float/2addr v3, v4

    add-float/2addr v1, v3

    .line 86
    .restart local v1    # "scaleRequired":F
    :goto_0
    div-float v3, p0, p2

    .line 87
    .local v3, "areaScale":F
    cmpl-float v4, v3, v1

    if-lez v4, :cond_1

    div-float v2, v1, v3

    float-to-double v4, v2

    invoke-static {v4, v5}, Ljava/lang/Math;->sqrt(D)D

    move-result-wide v4

    double-to-float v2, v4

    nop

    :cond_1
    return v2
.end method

.method private isShape(Landroid/graphics/Path;)Z
    .locals 4
    .param p1, "maskPath"    # Landroid/graphics/Path;

    .line 109
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v0}, Landroid/graphics/Rect;->width()I

    move-result v0

    int-to-float v0, v0

    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v1}, Landroid/graphics/Rect;->height()I

    move-result v1

    int-to-float v1, v1

    div-float/2addr v0, v1

    .line 110
    .local v0, "iconRatio":F
    const/high16 v1, 0x3f800000    # 1.0f

    sub-float v1, v0, v1

    invoke-static {v1}, Ljava/lang/Math;->abs(F)F

    move-result v1

    const v2, 0x3d4ccccd    # 0.05f

    cmpl-float v1, v1, v2

    if-lez v1, :cond_0

    .line 111
    const/4 v1, 0x0

    return v1

    .line 113
    :cond_0
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMatrix:Landroid/graphics/Matrix;

    invoke-virtual {v1}, Landroid/graphics/Matrix;->reset()V

    .line 114
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMatrix:Landroid/graphics/Matrix;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v2}, Landroid/graphics/Rect;->width()I

    move-result v2

    int-to-float v2, v2

    iget-object v3, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v3}, Landroid/graphics/Rect;->height()I

    move-result v3

    int-to-float v3, v3

    invoke-virtual {v1, v2, v3}, Landroid/graphics/Matrix;->setScale(FF)V

    .line 115
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMatrix:Landroid/graphics/Matrix;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v2, v2, Landroid/graphics/Rect;->left:I

    int-to-float v2, v2

    iget-object v3, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v3, v3, Landroid/graphics/Rect;->top:I

    int-to-float v3, v3

    invoke-virtual {v1, v2, v3}, Landroid/graphics/Matrix;->postTranslate(FF)Z

    .line 116
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMatrix:Landroid/graphics/Matrix;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mShapePath:Landroid/graphics/Path;

    invoke-virtual {p1, v1, v2}, Landroid/graphics/Path;->transform(Landroid/graphics/Matrix;Landroid/graphics/Path;)V

    .line 117
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mCanvas:Landroid/graphics/Canvas;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mShapePath:Landroid/graphics/Path;

    iget-object v3, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShape:Landroid/graphics/Paint;

    invoke-virtual {v1, v2, v3}, Landroid/graphics/Canvas;->drawPath(Landroid/graphics/Path;Landroid/graphics/Paint;)V

    .line 118
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mCanvas:Landroid/graphics/Canvas;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mShapePath:Landroid/graphics/Path;

    iget-object v3, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPaintMaskShapeOutline:Landroid/graphics/Paint;

    invoke-virtual {v1, v2, v3}, Landroid/graphics/Canvas;->drawPath(Landroid/graphics/Path;Landroid/graphics/Paint;)V

    .line 119
    invoke-direct {p0}, Lcom/hcn/auto/systemui/IconNormalizer;->isTransparentBitmap()Z

    move-result v1

    return v1
.end method

.method private isTransparentBitmap()Z
    .locals 9

    .line 124
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPixels:[B

    invoke-static {v0}, Ljava/nio/ByteBuffer;->wrap([B)Ljava/nio/ByteBuffer;

    move-result-object v0

    .line 125
    .local v0, "buffer":Ljava/nio/ByteBuffer;
    invoke-virtual {v0}, Ljava/nio/ByteBuffer;->rewind()Ljava/nio/Buffer;

    .line 126
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v1, v0}, Landroid/graphics/Bitmap;->copyPixelsToBuffer(Ljava/nio/Buffer;)V

    .line 127
    iget-object v1, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v1, v1, Landroid/graphics/Rect;->top:I

    .line 128
    .local v1, "y":I
    iget v2, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    mul-int/2addr v2, v1

    .line 129
    .local v2, "index":I
    iget v3, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    iget-object v4, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v4, v4, Landroid/graphics/Rect;->right:I

    sub-int/2addr v3, v4

    .line 132
    .local v3, "rowSizeDiff":I
    const/4 v4, 0x0

    move v5, v2

    move v2, v1

    move v1, v4

    .local v1, "sum":I
    .local v2, "y":I
    .local v5, "index":I
    :goto_0
    iget-object v6, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v6, v6, Landroid/graphics/Rect;->bottom:I

    if-ge v2, v6, :cond_2

    .line 133
    iget-object v6, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v6, v6, Landroid/graphics/Rect;->left:I

    add-int/2addr v5, v6

    .line 135
    iget-object v6, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v6, v6, Landroid/graphics/Rect;->left:I

    .local v6, "x":I
    :goto_1
    iget-object v7, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v7, v7, Landroid/graphics/Rect;->right:I

    if-ge v6, v7, :cond_1

    .line 136
    iget-object v7, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mPixels:[B

    aget-byte v7, v7, v5

    and-int/lit16 v7, v7, 0xff

    const/16 v8, 0x28

    if-le v7, v8, :cond_0

    .line 137
    add-int/lit8 v1, v1, 0x1

    .line 140
    :cond_0
    add-int/lit8 v5, v5, 0x1

    .line 135
    add-int/lit8 v6, v6, 0x1

    goto :goto_1

    .line 143
    .end local v6    # "x":I
    :cond_1
    add-int/2addr v5, v3

    .line 132
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 146
    :cond_2
    int-to-float v6, v1

    iget-object v7, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v7}, Landroid/graphics/Rect;->width()I

    move-result v7

    iget-object v8, p0, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v8}, Landroid/graphics/Rect;->height()I

    move-result v8

    mul-int/2addr v7, v8

    int-to-float v7, v7

    div-float/2addr v6, v7

    .line 147
    .local v6, "percentageDiffPixels":F
    const v7, 0x3ba3d70a    # 0.005f

    cmpg-float v7, v6, v7

    if-gez v7, :cond_3

    const/4 v4, 0x1

    nop

    :cond_3
    return v4
.end method

.method public static normalizeAdaptiveIcon(Landroid/graphics/drawable/Drawable;ILandroid/graphics/RectF;)F
    .locals 11
    .param p0, "d"    # Landroid/graphics/drawable/Drawable;
    .param p1, "size"    # I
    .param p2, "outBounds"    # Landroid/graphics/RectF;
    .annotation build Landroid/annotation/TargetApi;
        value = 0x1a
    .end annotation

    .line 92
    new-instance v0, Landroid/graphics/Rect;

    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getBounds()Landroid/graphics/Rect;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/graphics/Rect;-><init>(Landroid/graphics/Rect;)V

    .line 93
    .local v0, "tmpBounds":Landroid/graphics/Rect;
    const/4 v1, 0x0

    invoke-virtual {p0, v1, v1, p1, p1}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 94
    move-object v2, p0

    check-cast v2, Landroid/graphics/drawable/AdaptiveIconDrawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getIconMask()Landroid/graphics/Path;

    move-result-object v2

    .line 95
    .local v2, "path":Landroid/graphics/Path;
    new-instance v3, Landroid/graphics/Region;

    invoke-direct {v3}, Landroid/graphics/Region;-><init>()V

    .line 96
    .local v3, "region":Landroid/graphics/Region;
    new-instance v4, Landroid/graphics/Region;

    invoke-direct {v4, v1, v1, p1, p1}, Landroid/graphics/Region;-><init>(IIII)V

    invoke-virtual {v3, v2, v4}, Landroid/graphics/Region;->setPath(Landroid/graphics/Path;Landroid/graphics/Region;)Z

    .line 97
    invoke-virtual {v3}, Landroid/graphics/Region;->getBounds()Landroid/graphics/Rect;

    move-result-object v1

    .line 98
    .local v1, "hullBounds":Landroid/graphics/Rect;
    invoke-static {v3}, Lcom/android/launcher3/icons/GraphicsUtils;->getArea(Landroid/graphics/Region;)I

    move-result v4

    .line 99
    .local v4, "hullArea":I
    if-eqz p2, :cond_0

    .line 100
    int-to-float v5, p1

    .line 101
    .local v5, "sizeF":F
    iget v6, v1, Landroid/graphics/Rect;->left:I

    int-to-float v6, v6

    div-float/2addr v6, v5

    iget v7, v1, Landroid/graphics/Rect;->top:I

    int-to-float v7, v7

    div-float/2addr v7, v5

    iget v8, v1, Landroid/graphics/Rect;->right:I

    int-to-float v8, v8

    div-float/2addr v8, v5

    const/high16 v9, 0x3f800000    # 1.0f

    sub-float v8, v9, v8

    iget v10, v1, Landroid/graphics/Rect;->bottom:I

    int-to-float v10, v10

    div-float/2addr v10, v5

    sub-float/2addr v9, v10

    invoke-virtual {p2, v6, v7, v8, v9}, Landroid/graphics/RectF;->set(FFFF)V

    .line 104
    .end local v5    # "sizeF":F
    :cond_0
    invoke-virtual {p0, v0}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    .line 105
    int-to-float v5, v4

    int-to-float v6, v4

    mul-int v7, p1, p1

    int-to-float v7, v7

    invoke-static {v5, v6, v7}, Lcom/hcn/auto/systemui/IconNormalizer;->getScale(FFF)F

    move-result v5

    return v5
.end method


# virtual methods
.method public declared-synchronized getScale(Landroid/graphics/drawable/Drawable;Landroid/graphics/RectF;Landroid/graphics/Path;[Z)F
    .locals 22
    .param p1, "d"    # Landroid/graphics/drawable/Drawable;
    .param p2, "outBounds"    # Landroid/graphics/RectF;
    .param p3, "path"    # Landroid/graphics/Path;
    .param p4, "outMaskShape"    # [Z

    move-object/from16 v1, p0

    move-object/from16 v0, p1

    move-object/from16 v2, p2

    move-object/from16 v3, p4

    monitor-enter p0

    .line 151
    :try_start_0
    sget-boolean v4, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_OREO:Z

    if-eqz v4, :cond_2

    instance-of v4, v0, Landroid/graphics/drawable/AdaptiveIconDrawable;

    if-eqz v4, :cond_2

    .line 152
    iget v4, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconScale:F

    const/4 v5, 0x0

    cmpl-float v4, v4, v5

    if-nez v4, :cond_0

    .line 153
    iget v4, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    iget-object v5, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconBounds:Landroid/graphics/RectF;

    invoke-static {v0, v4, v5}, Lcom/hcn/auto/systemui/IconNormalizer;->normalizeAdaptiveIcon(Landroid/graphics/drawable/Drawable;ILandroid/graphics/RectF;)F

    move-result v4

    iput v4, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconScale:F

    .line 156
    :cond_0
    if-eqz v2, :cond_1

    .line 157
    iget-object v4, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconBounds:Landroid/graphics/RectF;

    invoke-virtual {v2, v4}, Landroid/graphics/RectF;->set(Landroid/graphics/RectF;)V

    .line 160
    :cond_1
    iget v4, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mAdaptiveIconScale:F
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return v4

    .line 162
    :cond_2
    :try_start_1
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v4

    .line 163
    .local v4, "width":I
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v5

    .line 164
    .local v5, "height":I
    if-lez v4, :cond_4

    if-lez v5, :cond_4

    .line 165
    iget v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    if-gt v4, v6, :cond_3

    iget v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    if-le v5, v6, :cond_7

    .line 166
    :cond_3
    invoke-static {v4, v5}, Ljava/lang/Math;->max(II)I

    move-result v6

    .line 167
    .local v6, "max":I
    iget v7, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    mul-int/2addr v7, v4

    div-int/2addr v7, v6

    move v4, v7

    .line 168
    iget v7, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    mul-int/2addr v7, v5

    div-int/2addr v7, v6

    move v5, v7

    .line 169
    .end local v6    # "max":I
    goto :goto_2

    .line 171
    :cond_4
    if-lez v4, :cond_5

    iget v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    if-gt v4, v6, :cond_5

    move v6, v4

    goto :goto_0

    :cond_5
    iget v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    :goto_0
    move v4, v6

    .line 172
    if-lez v5, :cond_6

    iget v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    if-gt v5, v6, :cond_6

    move v6, v5

    goto :goto_1

    :cond_6
    iget v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    :goto_1
    move v5, v6

    .line 175
    :cond_7
    :goto_2
    iget-object v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBitmap:Landroid/graphics/Bitmap;

    const/4 v7, 0x0

    invoke-virtual {v6, v7}, Landroid/graphics/Bitmap;->eraseColor(I)V

    .line 176
    invoke-virtual {v0, v7, v7, v4, v5}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 177
    iget-object v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mCanvas:Landroid/graphics/Canvas;

    invoke-virtual {v0, v6}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 178
    iget-object v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mPixels:[B

    invoke-static {v6}, Ljava/nio/ByteBuffer;->wrap([B)Ljava/nio/ByteBuffer;

    move-result-object v6

    .line 179
    .local v6, "buffer":Ljava/nio/ByteBuffer;
    invoke-virtual {v6}, Ljava/nio/ByteBuffer;->rewind()Ljava/nio/Buffer;

    .line 180
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v8, v6}, Landroid/graphics/Bitmap;->copyPixelsToBuffer(Ljava/nio/Buffer;)V

    .line 181
    const/4 v8, -0x1

    .line 182
    .local v8, "topY":I
    const/4 v9, -0x1

    .line 183
    .local v9, "bottomY":I
    iget v10, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    const/4 v11, 0x1

    add-int/2addr v10, v11

    .line 184
    .local v10, "leftX":I
    const/4 v12, -0x1

    .line 185
    .local v12, "rightX":I
    const/4 v13, 0x0

    .line 186
    .local v13, "index":I
    iget v14, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mMaxSize:I

    sub-int/2addr v14, v4

    .line 189
    .local v14, "rowSizeDiff":I
    move v15, v13

    move v13, v10

    move v10, v9

    move v9, v8

    move v8, v7

    .local v8, "z":I
    .local v9, "topY":I
    .local v10, "bottomY":I
    .local v13, "leftX":I
    .local v15, "index":I
    :goto_3
    if-ge v8, v5, :cond_d

    .line 190
    const/16 v16, -0x1

    .line 191
    .local v16, "lastX":I
    const/16 v17, -0x1

    .line 193
    .local v17, "firstX":I
    const/4 v8, 0x0

    move/from16 v18, v16

    move/from16 v11, v17

    .end local v16    # "lastX":I
    .end local v17    # "firstX":I
    .local v11, "firstX":I
    .local v18, "lastX":I
    :goto_4
    if-ge v8, v4, :cond_a

    .line 194
    iget-object v7, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mPixels:[B

    aget-byte v7, v7, v15

    and-int/lit16 v7, v7, 0xff

    const/16 v0, 0x28

    if-le v7, v0, :cond_9

    .line 195
    const/4 v0, -0x1

    if-ne v11, v0, :cond_8

    .line 196
    move v11, v8

    .line 199
    :cond_8
    move v0, v8

    .line 202
    .end local v18    # "lastX":I
    .local v0, "lastX":I
    move/from16 v18, v0

    .end local v0    # "lastX":I
    .restart local v18    # "lastX":I
    :cond_9
    add-int/lit8 v15, v15, 0x1

    .line 193
    add-int/lit8 v8, v8, 0x1

    move-object/from16 v0, p1

    goto :goto_4

    .line 205
    :cond_a
    add-int/2addr v15, v14

    .line 206
    iget-object v0, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mLeftBorder:[F

    int-to-float v7, v11

    aput v7, v0, v8

    .line 207
    iget-object v0, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mRightBorder:[F

    move-object/from16 v19, v6

    move/from16 v7, v18

    int-to-float v6, v7

    .end local v6    # "buffer":Ljava/nio/ByteBuffer;
    .end local v18    # "lastX":I
    .local v7, "lastX":I
    .local v19, "buffer":Ljava/nio/ByteBuffer;
    aput v6, v0, v8

    .line 208
    const/4 v0, -0x1

    if-eq v11, v0, :cond_c

    .line 209
    move v6, v8

    .line 210
    .end local v10    # "bottomY":I
    .local v6, "bottomY":I
    if-ne v9, v0, :cond_b

    .line 211
    move v9, v8

    .line 214
    :cond_b
    invoke-static {v13, v11}, Ljava/lang/Math;->min(II)I

    move-result v0

    .line 215
    .end local v13    # "leftX":I
    .local v0, "leftX":I
    invoke-static {v12, v7}, Ljava/lang/Math;->max(II)I

    move-result v10

    move v7, v10

    .line 189
    .end local v11    # "firstX":I
    .end local v12    # "rightX":I
    .local v7, "rightX":I
    move v13, v0

    move v10, v6

    move v12, v7

    .end local v0    # "leftX":I
    .end local v6    # "bottomY":I
    .end local v7    # "rightX":I
    .restart local v10    # "bottomY":I
    .restart local v12    # "rightX":I
    .restart local v13    # "leftX":I
    :cond_c
    add-int/lit8 v8, v8, 0x1

    move-object/from16 v6, v19

    move-object/from16 v0, p1

    const/4 v7, 0x0

    const/4 v11, 0x1

    goto :goto_3

    .line 219
    .end local v8    # "z":I
    .end local v19    # "buffer":Ljava/nio/ByteBuffer;
    .local v6, "buffer":Ljava/nio/ByteBuffer;
    :cond_d
    move-object/from16 v19, v6

    .end local v6    # "buffer":Ljava/nio/ByteBuffer;
    .restart local v19    # "buffer":Ljava/nio/ByteBuffer;
    const/high16 v0, 0x3f800000    # 1.0f

    const/4 v6, -0x1

    if-eq v9, v6, :cond_12

    if-eq v12, v6, :cond_12

    .line 220
    iget-object v7, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mLeftBorder:[F

    const/4 v8, 0x1

    invoke-static {v7, v8, v9, v10}, Lcom/hcn/auto/systemui/IconNormalizer;->convertToConvexArray([FIII)V

    .line 221
    iget-object v7, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mRightBorder:[F

    invoke-static {v7, v6, v9, v10}, Lcom/hcn/auto/systemui/IconNormalizer;->convertToConvexArray([FIII)V

    .line 222
    const/4 v6, 0x0

    .line 224
    .local v6, "area":F
    move v7, v6

    const/4 v6, 0x0

    .local v6, "y":I
    .local v7, "area":F
    :goto_5
    if-ge v6, v5, :cond_f

    .line 225
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mLeftBorder:[F

    aget v8, v8, v6

    const/high16 v11, -0x40800000    # -1.0f

    cmpg-float v8, v8, v11

    if-lez v8, :cond_e

    .line 226
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mRightBorder:[F

    aget v8, v8, v6

    iget-object v11, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mLeftBorder:[F

    aget v11, v11, v6

    sub-float/2addr v8, v11

    add-float/2addr v8, v0

    add-float/2addr v7, v8

    .line 224
    :cond_e
    add-int/lit8 v6, v6, 0x1

    goto :goto_5

    .line 230
    :cond_f
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iput v13, v8, Landroid/graphics/Rect;->left:I

    .line 231
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iput v12, v8, Landroid/graphics/Rect;->right:I

    .line 232
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iput v9, v8, Landroid/graphics/Rect;->top:I

    .line 233
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iput v10, v8, Landroid/graphics/Rect;->bottom:I

    .line 234
    if-eqz v2, :cond_10

    .line 235
    iget-object v8, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v8, v8, Landroid/graphics/Rect;->left:I

    int-to-float v8, v8

    int-to-float v11, v4

    div-float/2addr v8, v11

    iget-object v11, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v11, v11, Landroid/graphics/Rect;->top:I

    int-to-float v11, v11

    int-to-float v0, v5

    div-float/2addr v11, v0

    iget-object v0, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v0, v0, Landroid/graphics/Rect;->right:I

    int-to-float v0, v0

    move/from16 v20, v6

    int-to-float v6, v4

    .end local v6    # "y":I
    .local v20, "y":I
    div-float/2addr v0, v6

    const/high16 v6, 0x3f800000    # 1.0f

    sub-float v0, v6, v0

    iget-object v6, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mBounds:Landroid/graphics/Rect;

    iget v6, v6, Landroid/graphics/Rect;->bottom:I

    int-to-float v6, v6

    move/from16 v21, v14

    int-to-float v14, v5

    .end local v14    # "rowSizeDiff":I
    .local v21, "rowSizeDiff":I
    div-float/2addr v6, v14

    const/high16 v14, 0x3f800000    # 1.0f

    sub-float v6, v14, v6

    invoke-virtual {v2, v8, v11, v0, v6}, Landroid/graphics/RectF;->set(FFFF)V

    goto :goto_6

    .line 238
    .end local v20    # "y":I
    .end local v21    # "rowSizeDiff":I
    .restart local v6    # "y":I
    .restart local v14    # "rowSizeDiff":I
    :cond_10
    move/from16 v20, v6

    move/from16 v21, v14

    .end local v6    # "y":I
    .end local v14    # "rowSizeDiff":I
    .restart local v20    # "y":I
    .restart local v21    # "rowSizeDiff":I
    :goto_6
    if-eqz v3, :cond_11

    iget-boolean v0, v1, Lcom/hcn/auto/systemui/IconNormalizer;->mEnableShapeDetection:Z

    if-eqz v0, :cond_11

    array-length v0, v3

    if-lez v0, :cond_11

    .line 239
    move-object/from16 v0, p3

    invoke-direct {v1, v0}, Lcom/hcn/auto/systemui/IconNormalizer;->isShape(Landroid/graphics/Path;)Z

    move-result v6

    const/4 v8, 0x0

    aput-boolean v6, v3, v8

    goto :goto_7

    .line 242
    :cond_11
    move-object/from16 v0, p3

    :goto_7
    add-int/lit8 v6, v10, 0x1

    sub-int/2addr v6, v9

    add-int/lit8 v8, v12, 0x1

    sub-int/2addr v8, v13

    mul-int/2addr v6, v8

    int-to-float v6, v6

    .line 243
    .local v6, "rectArea":F
    mul-int v8, v4, v5

    int-to-float v8, v8

    invoke-static {v7, v6, v8}, Lcom/hcn/auto/systemui/IconNormalizer;->getScale(FFF)F

    move-result v8
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return v8

    .line 245
    .end local v6    # "rectArea":F
    .end local v7    # "area":F
    .end local v20    # "y":I
    .end local v21    # "rowSizeDiff":I
    .restart local v14    # "rowSizeDiff":I
    :cond_12
    move-object/from16 v0, p3

    move/from16 v21, v14

    .end local v14    # "rowSizeDiff":I
    .restart local v21    # "rowSizeDiff":I
    monitor-exit p0

    const/high16 v6, 0x3f800000    # 1.0f

    return v6

    .line 150
    .end local v4    # "width":I
    .end local v5    # "height":I
    .end local v9    # "topY":I
    .end local v10    # "bottomY":I
    .end local v12    # "rightX":I
    .end local v13    # "leftX":I
    .end local v15    # "index":I
    .end local v19    # "buffer":Ljava/nio/ByteBuffer;
    .end local v21    # "rowSizeDiff":I
    .end local p1    # "d":Landroid/graphics/drawable/Drawable;
    .end local p2    # "outBounds":Landroid/graphics/RectF;
    .end local p3    # "path":Landroid/graphics/Path;
    .end local p4    # "outMaskShape":[Z
    :catchall_0
    move-exception v0

    monitor-exit p0

    .end local p0    # "this":Lcom/hcn/auto/systemui/IconNormalizer;
    throw v0
.end method
