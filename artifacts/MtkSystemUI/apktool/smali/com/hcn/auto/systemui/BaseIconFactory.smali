.class public Lcom/hcn/auto/systemui/BaseIconFactory;
.super Ljava/lang/Object;
.source "BaseIconFactory.java"

# interfaces
.implements Ljava/lang/AutoCloseable;


# static fields
.field static final ATLEAST_OREO:Z

.field static final ATLEAST_P:Z


# instance fields
.field private final mCanvas:Landroid/graphics/Canvas;

.field private final mColorExtractor:Lcom/android/launcher3/icons/ColorExtractor;

.field protected final mContext:Landroid/content/Context;

.field private mDisableColorExtractor:Z

.field protected final mFillResIconDpi:I

.field protected final mIconBitmapSize:I

.field private mNormalizer:Lcom/hcn/auto/systemui/IconNormalizer;

.field private final mOldBounds:Landroid/graphics/Rect;

.field private final mPm:Landroid/content/pm/PackageManager;

.field private final mShapeDetection:Z

.field private mWrapperBackgroundColor:I

.field private mWrapperIcon:Landroid/graphics/drawable/Drawable;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    .line 504
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/4 v1, 0x0

    const/4 v2, 0x1

    const/16 v3, 0x1a

    if-lt v0, v3, :cond_0

    move v0, v2

    goto :goto_0

    :cond_0
    move v0, v1

    :goto_0
    sput-boolean v0, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_OREO:Z

    .line 505
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x1c

    if-lt v0, v3, :cond_1

    move v1, v2

    nop

    :cond_1
    sput-boolean v1, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_P:Z

    .line 506
    return-void
.end method

.method protected constructor <init>(Landroid/content/Context;II)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "fillResIconDpi"    # I
    .param p3, "iconBitmapSize"    # I

    .line 69
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/hcn/auto/systemui/BaseIconFactory;-><init>(Landroid/content/Context;IIZ)V

    .line 70
    return-void
.end method

.method protected constructor <init>(Landroid/content/Context;IIZ)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "fillResIconDpi"    # I
    .param p3, "iconBitmapSize"    # I
    .param p4, "shapeDetection"    # Z

    .line 54
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 55
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mOldBounds:Landroid/graphics/Rect;

    .line 56
    const/4 v0, -0x1

    iput v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperBackgroundColor:I

    .line 57
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    .line 58
    iput-boolean p4, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mShapeDetection:Z

    .line 59
    iput p2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mFillResIconDpi:I

    .line 60
    iput p3, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mIconBitmapSize:I

    .line 61
    iget-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mPm:Landroid/content/pm/PackageManager;

    .line 62
    new-instance v0, Lcom/android/launcher3/icons/ColorExtractor;

    invoke-direct {v0}, Lcom/android/launcher3/icons/ColorExtractor;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mColorExtractor:Lcom/android/launcher3/icons/ColorExtractor;

    .line 63
    new-instance v0, Landroid/graphics/Canvas;

    invoke-direct {v0}, Landroid/graphics/Canvas;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    .line 64
    iget-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    new-instance v1, Landroid/graphics/PaintFlagsDrawFilter;

    const/4 v2, 0x4

    const/4 v3, 0x2

    invoke-direct {v1, v2, v3}, Landroid/graphics/PaintFlagsDrawFilter;-><init>(II)V

    invoke-virtual {v0, v1}, Landroid/graphics/Canvas;->setDrawFilter(Landroid/graphics/DrawFilter;)V

    .line 65
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/BaseIconFactory;->clear()V

    .line 66
    return-void
.end method

.method private createIconBitmap(Landroid/graphics/drawable/Drawable;F)Landroid/graphics/Bitmap;
    .locals 1
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "scale"    # F

    .line 439
    iget v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mIconBitmapSize:I

    invoke-virtual {p0, p1, p2, v0}, Lcom/hcn/auto/systemui/BaseIconFactory;->createIconBitmap(Landroid/graphics/drawable/Drawable;FI)Landroid/graphics/Bitmap;

    move-result-object v0

    return-object v0
.end method

.method private normalizeAndWrapToAdaptiveIcon(Landroid/graphics/drawable/Drawable;ZLandroid/graphics/RectF;[F)Landroid/graphics/drawable/Drawable;
    .locals 17
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "shrinkNonAdaptiveIcons"    # Z
    .param p3, "outIconBounds"    # Landroid/graphics/RectF;
    .param p4, "outScale"    # [F

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    .line 203
    const v2, 0x3f47ae14    # 0.78f

    .line 204
    .local v2, "scale":F
    iget v3, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mIconBitmapSize:I

    .line 205
    .local v3, "iconSize":I
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    const-string v5, "icon_size"

    invoke-virtual {v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v4

    .line 206
    .local v4, "iconSizeTheme":I
    if-eqz v4, :cond_0

    .line 207
    move v3, v4

    .line 210
    :cond_0
    const/16 v5, 0x78

    if-lt v3, v5, :cond_1

    .line 211
    const/high16 v2, 0x3f800000    # 1.0f

    .line 214
    :cond_1
    const/4 v6, 0x0

    if-eqz p2, :cond_11

    sget-boolean v8, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_OREO:Z

    if-eqz v8, :cond_11

    .line 215
    iget-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    if-nez v8, :cond_2

    .line 216
    iget-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    const v9, 0x7f0800d5

    invoke-virtual {v8, v9}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v8}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object v8

    iput-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    .line 219
    :cond_2
    iget-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    check-cast v8, Landroid/graphics/drawable/AdaptiveIconDrawable;

    .line 220
    .local v8, "dr":Landroid/graphics/drawable/AdaptiveIconDrawable;
    new-instance v9, Lcom/android/launcher3/icons/FixedScaleDrawable;

    invoke-direct {v9}, Lcom/android/launcher3/icons/FixedScaleDrawable;-><init>()V

    .line 221
    .local v9, "fsd":Lcom/android/launcher3/icons/FixedScaleDrawable;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v10

    const-string v11, "ignore_icon_background"

    invoke-virtual {v10, v11}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v10

    .line 222
    .local v10, "ignoreIconBackground":I
    if-nez v10, :cond_3

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v11

    const-string v12, "icon_background"

    invoke-virtual {v11, v12}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v11

    goto :goto_0

    :cond_3
    const/4 v11, 0x0

    .line 223
    .local v11, "iconBackground":Landroid/graphics/drawable/Drawable;
    :goto_0
    const/4 v12, 0x1

    if-eqz v11, :cond_4

    .line 224
    new-instance v13, Landroid/graphics/drawable/AdaptiveIconDrawable;

    invoke-direct {v13, v11, v9}, Landroid/graphics/drawable/AdaptiveIconDrawable;-><init>(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V

    move-object v8, v13

    goto :goto_1

    .line 225
    :cond_4
    if-ne v10, v12, :cond_5

    .line 226
    invoke-virtual {v8}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v13

    invoke-virtual {v13, v6}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 229
    :cond_5
    :goto_1
    invoke-virtual {v8}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getForeground()Landroid/graphics/drawable/Drawable;

    move-result-object v13

    move-object v9, v13

    check-cast v9, Lcom/android/launcher3/icons/FixedScaleDrawable;

    .line 230
    invoke-virtual {v9, v1}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 231
    const v13, 0x3f333333    # 0.7f

    const v14, 0x3f2aacda    # 0.6667f

    if-ne v3, v5, :cond_6

    .line 232
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 233
    :cond_6
    const/16 v5, 0x7e

    if-ne v3, v5, :cond_7

    .line 234
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 235
    :cond_7
    const/16 v5, 0x80

    if-ne v3, v5, :cond_8

    .line 236
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 237
    :cond_8
    const/16 v5, 0x9b

    if-ne v3, v5, :cond_9

    .line 238
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 239
    :cond_9
    const/16 v5, 0xa0

    const/high16 v13, 0x3f000000    # 0.5f

    if-ne v3, v5, :cond_a

    .line 240
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 241
    :cond_a
    const/16 v5, 0xa5

    if-ne v3, v5, :cond_b

    .line 242
    const v5, 0x3f19999a    # 0.6f

    mul-float/2addr v5, v2

    mul-float/2addr v5, v14

    invoke-virtual {v9, v5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 243
    :cond_b
    const/16 v5, 0xb2

    if-ne v3, v5, :cond_c

    .line 244
    const v5, 0x3ecccccd    # 0.4f

    mul-float/2addr v5, v2

    mul-float/2addr v5, v14

    invoke-virtual {v9, v5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 245
    :cond_c
    const/16 v5, 0xbc

    if-ne v3, v5, :cond_d

    .line 246
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 248
    :cond_d
    const v5, 0x3f6b851f    # 0.92f

    mul-float/2addr v5, v2

    invoke-virtual {v9, v5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 251
    :goto_2
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const-string v13, "icon_size_scale"

    invoke-virtual {v5, v13}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v5

    .line 252
    .local v5, "iconScale":I
    const/16 v5, 0x64

    .line 253
    const v13, 0x3c23d70a    # 0.01f

    if-eqz v5, :cond_e

    .line 254
    int-to-float v7, v5

    mul-float/2addr v7, v2

    mul-float/2addr v7, v13

    mul-float/2addr v7, v14

    invoke-virtual {v9, v7}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 257
    :cond_e
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v7

    const-string/jumbo v14, "za37_icon_background_n"

    invoke-static {v14}, Lcom/hcn/auto/systemui/IconThemeUtils;->getIconBackgroundName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v7, v14}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 258
    .local v7, "icon_bg":Landroid/graphics/drawable/Drawable;
    if-eqz v7, :cond_10

    .line 259
    const/high16 v2, 0x3f800000    # 1.0f

    .line 260
    const/4 v14, 0x2

    new-array v14, v14, [Landroid/graphics/drawable/Drawable;

    aput-object v7, v14, v6

    const/4 v15, 0x0

    aput-object v15, v14, v12

    .line 261
    .local v14, "layers":[Landroid/graphics/drawable/Drawable;
    if-eqz v5, :cond_f

    .line 262
    int-to-float v6, v5

    mul-float/2addr v6, v2

    mul-float/2addr v6, v13

    invoke-virtual {v9, v6}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 265
    :cond_f
    aput-object v9, v14, v12

    .line 266
    new-instance v6, Landroid/graphics/drawable/LayerDrawable;

    invoke-direct {v6, v14}, Landroid/graphics/drawable/LayerDrawable;-><init>([Landroid/graphics/drawable/Drawable;)V

    move-object v1, v6

    .line 267
    .end local v14    # "layers":[Landroid/graphics/drawable/Drawable;
    .end local p1    # "icon":Landroid/graphics/drawable/Drawable;
    .local v1, "icon":Landroid/graphics/drawable/Drawable;
    goto :goto_3

    .line 268
    .end local v1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local p1    # "icon":Landroid/graphics/drawable/Drawable;
    :cond_10
    move-object v1, v8

    .line 270
    .end local v5    # "iconScale":I
    .end local v7    # "icon_bg":Landroid/graphics/drawable/Drawable;
    .end local v8    # "dr":Landroid/graphics/drawable/AdaptiveIconDrawable;
    .end local v9    # "fsd":Lcom/android/launcher3/icons/FixedScaleDrawable;
    .end local v10    # "ignoreIconBackground":I
    .end local v11    # "iconBackground":Landroid/graphics/drawable/Drawable;
    .end local p1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local v1    # "icon":Landroid/graphics/drawable/Drawable;
    :goto_3
    nop

    .line 274
    move-object/from16 v8, p3

    goto :goto_4

    .line 271
    .end local v1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local p1    # "icon":Landroid/graphics/drawable/Drawable;
    :cond_11
    invoke-virtual/range {p0 .. p0}, Lcom/hcn/auto/systemui/BaseIconFactory;->getNormalizer()Lcom/hcn/auto/systemui/IconNormalizer;

    move-result-object v5

    const/4 v6, 0x0

    move-object v7, v6

    check-cast v7, Landroid/graphics/Path;

    check-cast v6, [Z

    move-object/from16 v8, p3

    invoke-virtual {v5, v1, v8, v7, v6}, Lcom/hcn/auto/systemui/IconNormalizer;->getScale(Landroid/graphics/drawable/Drawable;Landroid/graphics/RectF;Landroid/graphics/Path;[Z)F

    move-result v2

    .line 274
    .end local p1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local v1    # "icon":Landroid/graphics/drawable/Drawable;
    :goto_4
    const/4 v6, 0x0

    aput v2, p4, v6

    .line 275
    return-object v1
.end method

.method private normalizeAndWrapToAdaptiveIcon(Landroid/graphics/drawable/Drawable;ZLandroid/graphics/RectF;[FFLandroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;
    .locals 8
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "shrinkNonAdaptiveIcons"    # Z
    .param p3, "outIconBounds"    # Landroid/graphics/RectF;
    .param p4, "outScale"    # [F
    .param p5, "scale"    # F
    .param p6, "background"    # Landroid/graphics/drawable/Drawable;

    .line 356
    const/4 v0, 0x0

    const/4 v1, 0x0

    if-eqz p2, :cond_5

    sget-boolean v2, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_OREO:Z

    if-eqz v2, :cond_5

    .line 357
    iget-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    if-nez v2, :cond_0

    .line 358
    iget-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    const v3, 0x7f0800d5

    invoke-virtual {v2, v3}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object v2

    iput-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    .line 361
    :cond_0
    iget-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    check-cast v2, Landroid/graphics/drawable/AdaptiveIconDrawable;

    .line 362
    .local v2, "dr":Landroid/graphics/drawable/AdaptiveIconDrawable;
    new-instance v3, Lcom/android/launcher3/icons/FixedScaleDrawable;

    invoke-direct {v3}, Lcom/android/launcher3/icons/FixedScaleDrawable;-><init>()V

    .line 363
    .local v3, "fsd":Lcom/android/launcher3/icons/FixedScaleDrawable;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    const-string v5, "icon_translate_x"

    invoke-virtual {v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    .line 366
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    const-string v5, "ignore_icon_background"

    invoke-virtual {v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v4

    .line 367
    .local v4, "ignoreIconBackground":I
    if-nez v4, :cond_1

    move-object v5, p6

    goto :goto_0

    :cond_1
    move-object v5, v1

    .line 368
    .local v5, "iconBackground":Landroid/graphics/drawable/Drawable;
    :goto_0
    const/4 v6, 0x1

    if-eqz v5, :cond_2

    .line 369
    new-instance v7, Landroid/graphics/drawable/AdaptiveIconDrawable;

    invoke-direct {v7, v5, v3}, Landroid/graphics/drawable/AdaptiveIconDrawable;-><init>(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V

    move-object v2, v7

    goto :goto_1

    .line 370
    :cond_2
    if-ne v4, v6, :cond_3

    .line 371
    invoke-virtual {v2}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v7

    invoke-virtual {v7, v0}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 374
    :cond_3
    :goto_1
    invoke-virtual {v2}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getForeground()Landroid/graphics/drawable/Drawable;

    move-result-object v7

    move-object v3, v7

    check-cast v3, Lcom/android/launcher3/icons/FixedScaleDrawable;

    .line 375
    invoke-virtual {v3, p1}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 376
    if-eqz v5, :cond_4

    .line 377
    const/4 v7, 0x2

    new-array v7, v7, [Landroid/graphics/drawable/Drawable;

    aput-object v5, v7, v0

    aput-object v1, v7, v6

    move-object v1, v7

    .line 378
    .local v1, "layers":[Landroid/graphics/drawable/Drawable;
    invoke-virtual {v3, p5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 379
    aput-object v3, v1, v6

    .line 380
    new-instance v6, Landroid/graphics/drawable/LayerDrawable;

    invoke-direct {v6, v1}, Landroid/graphics/drawable/LayerDrawable;-><init>([Landroid/graphics/drawable/Drawable;)V

    move-object p1, v6

    .line 381
    .end local v1    # "layers":[Landroid/graphics/drawable/Drawable;
    goto :goto_2

    .line 382
    :cond_4
    move-object p1, v2

    .line 384
    .end local v2    # "dr":Landroid/graphics/drawable/AdaptiveIconDrawable;
    .end local v3    # "fsd":Lcom/android/launcher3/icons/FixedScaleDrawable;
    .end local v4    # "ignoreIconBackground":I
    .end local v5    # "iconBackground":Landroid/graphics/drawable/Drawable;
    :goto_2
    goto :goto_3

    .line 385
    :cond_5
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/BaseIconFactory;->getNormalizer()Lcom/hcn/auto/systemui/IconNormalizer;

    move-result-object v2

    move-object v3, v1

    check-cast v3, Landroid/graphics/Path;

    check-cast v1, [Z

    invoke-virtual {v2, p1, p3, v3, v1}, Lcom/hcn/auto/systemui/IconNormalizer;->getScale(Landroid/graphics/drawable/Drawable;Landroid/graphics/RectF;Landroid/graphics/Path;[Z)F

    move-result p5

    .line 388
    :goto_3
    aput p5, p4, v0

    .line 389
    return-object p1
.end method

.method private normalizeAndWrapToAdaptiveIcon(Landroid/graphics/drawable/Drawable;ZLandroid/graphics/RectF;[FLjava/lang/String;)Landroid/graphics/drawable/Drawable;
    .locals 17
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "shrinkNonAdaptiveIcons"    # Z
    .param p3, "outIconBounds"    # Landroid/graphics/RectF;
    .param p4, "outScale"    # [F
    .param p5, "iconName"    # Ljava/lang/String;

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    .line 279
    const v2, 0x3f47ae14    # 0.78f

    .line 280
    .local v2, "scale":F
    iget v3, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mIconBitmapSize:I

    .line 281
    .local v3, "iconSize":I
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    const-string v5, "icon_size"

    invoke-virtual {v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v4

    .line 282
    .local v4, "iconSizeTheme":I
    if-eqz v4, :cond_0

    .line 283
    move v3, v4

    .line 286
    :cond_0
    const/16 v5, 0x78

    if-lt v3, v5, :cond_1

    .line 287
    const/high16 v2, 0x3f800000    # 1.0f

    .line 290
    :cond_1
    const/4 v6, 0x0

    if-eqz p2, :cond_11

    sget-boolean v8, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_OREO:Z

    if-eqz v8, :cond_11

    .line 291
    iget-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    if-nez v8, :cond_2

    .line 292
    iget-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    const v9, 0x7f0800d5

    invoke-virtual {v8, v9}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v8}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object v8

    iput-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    .line 295
    :cond_2
    iget-object v8, v0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperIcon:Landroid/graphics/drawable/Drawable;

    check-cast v8, Landroid/graphics/drawable/AdaptiveIconDrawable;

    .line 296
    .local v8, "dr":Landroid/graphics/drawable/AdaptiveIconDrawable;
    new-instance v9, Lcom/android/launcher3/icons/FixedScaleDrawable;

    invoke-direct {v9}, Lcom/android/launcher3/icons/FixedScaleDrawable;-><init>()V

    .line 297
    .local v9, "fsd":Lcom/android/launcher3/icons/FixedScaleDrawable;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v10

    const-string v11, "ignore_icon_background"

    invoke-virtual {v10, v11}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v10

    .line 298
    .local v10, "ignoreIconBackground":I
    if-nez v10, :cond_3

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v11

    const-string v12, "icon_background"

    invoke-virtual {v11, v12}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v11

    goto :goto_0

    :cond_3
    const/4 v11, 0x0

    .line 299
    .local v11, "iconBackground":Landroid/graphics/drawable/Drawable;
    :goto_0
    const/4 v12, 0x1

    if-eqz v11, :cond_4

    .line 300
    new-instance v13, Landroid/graphics/drawable/AdaptiveIconDrawable;

    invoke-direct {v13, v11, v9}, Landroid/graphics/drawable/AdaptiveIconDrawable;-><init>(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V

    move-object v8, v13

    goto :goto_1

    .line 301
    :cond_4
    if-ne v10, v12, :cond_5

    .line 302
    invoke-virtual {v8}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v13

    invoke-virtual {v13, v6}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 305
    :cond_5
    :goto_1
    invoke-virtual {v8}, Landroid/graphics/drawable/AdaptiveIconDrawable;->getForeground()Landroid/graphics/drawable/Drawable;

    move-result-object v13

    move-object v9, v13

    check-cast v9, Lcom/android/launcher3/icons/FixedScaleDrawable;

    .line 306
    invoke-virtual {v9, v1}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 307
    const v13, 0x3f333333    # 0.7f

    const v14, 0x3f2aacda    # 0.6667f

    if-ne v3, v5, :cond_6

    .line 308
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 309
    :cond_6
    const/16 v5, 0x7e

    if-ne v3, v5, :cond_7

    .line 310
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 311
    :cond_7
    const/16 v5, 0x80

    if-ne v3, v5, :cond_8

    .line 312
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 313
    :cond_8
    const/16 v5, 0x9b

    if-ne v3, v5, :cond_9

    .line 314
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 315
    :cond_9
    const/16 v5, 0xa0

    const/high16 v13, 0x3f000000    # 0.5f

    if-ne v3, v5, :cond_a

    .line 316
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 317
    :cond_a
    const/16 v5, 0xa5

    if-ne v3, v5, :cond_b

    .line 318
    const v5, 0x3f19999a    # 0.6f

    mul-float/2addr v5, v2

    mul-float/2addr v5, v14

    invoke-virtual {v9, v5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 319
    :cond_b
    const/16 v5, 0xb2

    if-ne v3, v5, :cond_c

    .line 320
    const v5, 0x3ecccccd    # 0.4f

    mul-float/2addr v5, v2

    mul-float/2addr v5, v14

    invoke-virtual {v9, v5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 321
    :cond_c
    const/16 v5, 0xbc

    if-ne v3, v5, :cond_d

    .line 322
    mul-float/2addr v13, v2

    mul-float/2addr v13, v14

    invoke-virtual {v9, v13}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    goto :goto_2

    .line 324
    :cond_d
    const v5, 0x3f6b851f    # 0.92f

    mul-float/2addr v5, v2

    invoke-virtual {v9, v5}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 327
    :goto_2
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const-string v13, "icon_size_scale"

    invoke-virtual {v5, v13}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v5

    .line 328
    .local v5, "iconScale":I
    const/16 v5, 0x3c

    .line 329
    const v13, 0x3c23d70a    # 0.01f

    if-eqz v5, :cond_e

    .line 330
    int-to-float v7, v5

    mul-float/2addr v7, v2

    mul-float/2addr v7, v13

    mul-float/2addr v7, v14

    invoke-virtual {v9, v7}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 333
    :cond_e
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v7

    invoke-static/range {p5 .. p5}, Lcom/hcn/auto/systemui/IconThemeUtils;->getIconBackgroundName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v7, v14}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 335
    .local v7, "icon_bg":Landroid/graphics/drawable/Drawable;
    if-eqz v7, :cond_10

    .line 336
    const/high16 v2, 0x3f800000    # 1.0f

    .line 337
    const/4 v14, 0x2

    new-array v14, v14, [Landroid/graphics/drawable/Drawable;

    aput-object v7, v14, v6

    const/4 v15, 0x0

    aput-object v15, v14, v12

    .line 338
    .local v14, "layers":[Landroid/graphics/drawable/Drawable;
    if-eqz v5, :cond_f

    .line 339
    int-to-float v6, v5

    mul-float/2addr v6, v2

    mul-float/2addr v6, v13

    invoke-virtual {v9, v6}, Lcom/android/launcher3/icons/FixedScaleDrawable;->setScale(F)V

    .line 342
    :cond_f
    aput-object v9, v14, v12

    .line 343
    new-instance v6, Landroid/graphics/drawable/LayerDrawable;

    invoke-direct {v6, v14}, Landroid/graphics/drawable/LayerDrawable;-><init>([Landroid/graphics/drawable/Drawable;)V

    move-object v1, v6

    .line 344
    .end local v14    # "layers":[Landroid/graphics/drawable/Drawable;
    .end local p1    # "icon":Landroid/graphics/drawable/Drawable;
    .local v1, "icon":Landroid/graphics/drawable/Drawable;
    goto :goto_3

    .line 345
    .end local v1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local p1    # "icon":Landroid/graphics/drawable/Drawable;
    :cond_10
    move-object v1, v8

    .line 347
    .end local v5    # "iconScale":I
    .end local v7    # "icon_bg":Landroid/graphics/drawable/Drawable;
    .end local v8    # "dr":Landroid/graphics/drawable/AdaptiveIconDrawable;
    .end local v9    # "fsd":Lcom/android/launcher3/icons/FixedScaleDrawable;
    .end local v10    # "ignoreIconBackground":I
    .end local v11    # "iconBackground":Landroid/graphics/drawable/Drawable;
    .end local p1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local v1    # "icon":Landroid/graphics/drawable/Drawable;
    :goto_3
    nop

    .line 351
    move-object/from16 v8, p3

    goto :goto_4

    .line 348
    .end local v1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local p1    # "icon":Landroid/graphics/drawable/Drawable;
    :cond_11
    invoke-virtual/range {p0 .. p0}, Lcom/hcn/auto/systemui/BaseIconFactory;->getNormalizer()Lcom/hcn/auto/systemui/IconNormalizer;

    move-result-object v5

    const/4 v6, 0x0

    move-object v7, v6

    check-cast v7, Landroid/graphics/Path;

    check-cast v6, [Z

    move-object/from16 v8, p3

    invoke-virtual {v5, v1, v8, v7, v6}, Lcom/hcn/auto/systemui/IconNormalizer;->getScale(Landroid/graphics/drawable/Drawable;Landroid/graphics/RectF;Landroid/graphics/Path;[Z)F

    move-result v2

    .line 351
    .end local p1    # "icon":Landroid/graphics/drawable/Drawable;
    .restart local v1    # "icon":Landroid/graphics/drawable/Drawable;
    :goto_4
    const/4 v6, 0x0

    aput v2, p4, v6

    .line 352
    return-object v1
.end method


# virtual methods
.method protected clear()V
    .locals 1

    .line 73
    const/4 v0, -0x1

    iput v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mWrapperBackgroundColor:I

    .line 74
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mDisableColorExtractor:Z

    .line 75
    return-void
.end method

.method public close()V
    .locals 0

    .line 492
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/BaseIconFactory;->clear()V

    .line 493
    return-void
.end method

.method public createIconBitmap(Landroid/graphics/drawable/Drawable;FI)Landroid/graphics/Bitmap;
    .locals 10
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "scale"    # F
    .param p3, "size"    # I

    .line 443
    sget-object v0, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {p3, p3, v0}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 444
    .local v0, "bitmap":Landroid/graphics/Bitmap;
    if-nez p1, :cond_0

    .line 445
    return-object v0

    .line 447
    :cond_0
    iget-object v1, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    invoke-virtual {v1, v0}, Landroid/graphics/Canvas;->setBitmap(Landroid/graphics/Bitmap;)V

    .line 448
    iget-object v1, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mOldBounds:Landroid/graphics/Rect;

    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getBounds()Landroid/graphics/Rect;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/graphics/Rect;->set(Landroid/graphics/Rect;)V

    .line 450
    sget-boolean v1, Lcom/hcn/auto/systemui/BaseIconFactory;->ATLEAST_OREO:Z

    if-eqz v1, :cond_1

    instance-of v1, p1, Landroid/graphics/drawable/AdaptiveIconDrawable;

    if-eqz v1, :cond_1

    .line 451
    const v1, 0x3c2aaaab

    int-to-float v2, p3

    mul-float/2addr v1, v2

    float-to-double v1, v1

    invoke-static {v1, v2}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v1

    double-to-int v1, v1

    int-to-float v2, p3

    const/high16 v3, 0x3f800000    # 1.0f

    sub-float/2addr v3, p2

    mul-float/2addr v2, v3

    const/high16 v3, 0x40000000    # 2.0f

    div-float/2addr v2, v3

    invoke-static {v2}, Ljava/lang/Math;->round(F)I

    move-result v2

    invoke-static {v1, v2}, Ljava/lang/Math;->max(II)I

    move-result v1

    .line 452
    .local v1, "width":I
    sub-int v2, p3, v1

    sub-int v3, p3, v1

    invoke-virtual {p1, v1, v1, v2, v3}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 453
    iget-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    invoke-virtual {p1, v2}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    goto :goto_1

    .line 455
    .end local v1    # "width":I
    :cond_1
    instance-of v1, p1, Landroid/graphics/drawable/BitmapDrawable;

    if-eqz v1, :cond_2

    .line 456
    move-object v1, p1

    check-cast v1, Landroid/graphics/drawable/BitmapDrawable;

    .line 457
    .local v1, "bitmapDrawable":Landroid/graphics/drawable/BitmapDrawable;
    invoke-virtual {v1}, Landroid/graphics/drawable/BitmapDrawable;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object v2

    .line 458
    .local v2, "b":Landroid/graphics/Bitmap;
    if-eqz v0, :cond_2

    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getDensity()I

    move-result v3

    if-nez v3, :cond_2

    .line 459
    iget-object v3, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v3

    invoke-virtual {v1, v3}, Landroid/graphics/drawable/BitmapDrawable;->setTargetDensity(Landroid/util/DisplayMetrics;)V

    .line 463
    .end local v1    # "bitmapDrawable":Landroid/graphics/drawable/BitmapDrawable;
    .end local v2    # "b":Landroid/graphics/Bitmap;
    :cond_2
    move v1, p3

    .line 464
    .local v1, "width":I
    move v2, p3

    .line 465
    .local v2, "height":I
    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v3

    .line 466
    .local v3, "intrinsicWidth":I
    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v4

    .line 467
    .local v4, "intrinsicHeight":I
    if-lez v3, :cond_4

    if-lez v4, :cond_4

    .line 468
    int-to-float v5, v3

    int-to-float v6, v4

    div-float/2addr v5, v6

    .line 469
    .local v5, "ratio":F
    if-le v3, v4, :cond_3

    .line 470
    int-to-float v6, p3

    div-float/2addr v6, v5

    float-to-int v2, v6

    goto :goto_0

    .line 471
    :cond_3
    if-le v4, v3, :cond_4

    .line 472
    int-to-float v6, p3

    mul-float/2addr v6, v5

    float-to-int v1, v6

    .line 476
    .end local v5    # "ratio":F
    :cond_4
    :goto_0
    sub-int v5, p3, v1

    div-int/lit8 v5, v5, 0x2

    .line 477
    .local v5, "left":I
    sub-int v6, p3, v2

    div-int/lit8 v6, v6, 0x2

    .line 478
    .local v6, "top":I
    add-int v7, v5, v1

    add-int v8, v6, v2

    invoke-virtual {p1, v5, v6, v7, v8}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 479
    iget-object v7, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    invoke-virtual {v7}, Landroid/graphics/Canvas;->save()I

    .line 480
    iget-object v7, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    div-int/lit8 v8, p3, 0x2

    int-to-float v8, v8

    div-int/lit8 v9, p3, 0x2

    int-to-float v9, v9

    invoke-virtual {v7, p2, p2, v8, v9}, Landroid/graphics/Canvas;->scale(FFFF)V

    .line 481
    iget-object v7, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    invoke-virtual {p1, v7}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 482
    iget-object v7, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    invoke-virtual {v7}, Landroid/graphics/Canvas;->restore()V

    .line 485
    .end local v2    # "height":I
    .end local v3    # "intrinsicWidth":I
    .end local v4    # "intrinsicHeight":I
    .end local v5    # "left":I
    .end local v6    # "top":I
    :goto_1
    iget-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mOldBounds:Landroid/graphics/Rect;

    invoke-virtual {p1, v2}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    .line 486
    iget-object v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mCanvas:Landroid/graphics/Canvas;

    const/4 v3, 0x0

    check-cast v3, Landroid/graphics/Bitmap;

    invoke-virtual {v2, v3}, Landroid/graphics/Canvas;->setBitmap(Landroid/graphics/Bitmap;)V

    .line 487
    return-object v0
.end method

.method public createScaledBitmapWithoutShadow(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;ZF)Landroid/graphics/Bitmap;
    .locals 8
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "iconBackground"    # Landroid/graphics/drawable/Drawable;
    .param p3, "shrinkNonAdaptiveIcons"    # Z
    .param p4, "scale"    # F

    .line 181
    new-instance v3, Landroid/graphics/RectF;

    invoke-direct {v3}, Landroid/graphics/RectF;-><init>()V

    .line 182
    .local v3, "iconBounds":Landroid/graphics/RectF;
    const/4 v0, 0x1

    new-array v7, v0, [F

    .line 183
    .local v7, "outScale":[F
    move-object v0, p0

    move-object v1, p1

    move v2, p3

    move-object v4, v7

    move v5, p4

    move-object v6, p2

    invoke-direct/range {v0 .. v6}, Lcom/hcn/auto/systemui/BaseIconFactory;->normalizeAndWrapToAdaptiveIcon(Landroid/graphics/drawable/Drawable;ZLandroid/graphics/RectF;[FFLandroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    .line 184
    const/high16 v0, 0x3f800000    # 1.0f

    invoke-direct {p0, p1, v0}, Lcom/hcn/auto/systemui/BaseIconFactory;->createIconBitmap(Landroid/graphics/drawable/Drawable;F)Landroid/graphics/Bitmap;

    move-result-object v0

    return-object v0
.end method

.method public createScaledBitmapWithoutShadow(Landroid/graphics/drawable/Drawable;Z)Landroid/graphics/Bitmap;
    .locals 3
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "shrinkNonAdaptiveIcons"    # Z

    .line 167
    new-instance v0, Landroid/graphics/RectF;

    invoke-direct {v0}, Landroid/graphics/RectF;-><init>()V

    .line 168
    .local v0, "iconBounds":Landroid/graphics/RectF;
    const/4 v1, 0x1

    new-array v1, v1, [F

    .line 169
    .local v1, "scale":[F
    invoke-direct {p0, p1, p2, v0, v1}, Lcom/hcn/auto/systemui/BaseIconFactory;->normalizeAndWrapToAdaptiveIcon(Landroid/graphics/drawable/Drawable;ZLandroid/graphics/RectF;[F)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    .line 170
    const/high16 v2, 0x3f800000    # 1.0f

    invoke-direct {p0, p1, v2}, Lcom/hcn/auto/systemui/BaseIconFactory;->createIconBitmap(Landroid/graphics/drawable/Drawable;F)Landroid/graphics/Bitmap;

    move-result-object v2

    return-object v2
.end method

.method public createScaledBitmapWithoutShadow(Landroid/graphics/drawable/Drawable;ZLjava/lang/String;)Landroid/graphics/Bitmap;
    .locals 7
    .param p1, "icon"    # Landroid/graphics/drawable/Drawable;
    .param p2, "shrinkNonAdaptiveIcons"    # Z
    .param p3, "iconName"    # Ljava/lang/String;

    .line 174
    new-instance v3, Landroid/graphics/RectF;

    invoke-direct {v3}, Landroid/graphics/RectF;-><init>()V

    .line 175
    .local v3, "iconBounds":Landroid/graphics/RectF;
    const/4 v0, 0x1

    new-array v6, v0, [F

    .line 176
    .local v6, "scale":[F
    move-object v0, p0

    move-object v1, p1

    move v2, p2

    move-object v4, v6

    move-object v5, p3

    invoke-direct/range {v0 .. v5}, Lcom/hcn/auto/systemui/BaseIconFactory;->normalizeAndWrapToAdaptiveIcon(Landroid/graphics/drawable/Drawable;ZLandroid/graphics/RectF;[FLjava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    .line 177
    const/high16 v0, 0x3f800000    # 1.0f

    invoke-direct {p0, p1, v0}, Lcom/hcn/auto/systemui/BaseIconFactory;->createIconBitmap(Landroid/graphics/drawable/Drawable;F)Landroid/graphics/Bitmap;

    move-result-object v0

    return-object v0
.end method

.method public getNormalizer()Lcom/hcn/auto/systemui/IconNormalizer;
    .locals 4

    .line 86
    iget-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mNormalizer:Lcom/hcn/auto/systemui/IconNormalizer;

    if-nez v0, :cond_0

    .line 87
    new-instance v0, Lcom/hcn/auto/systemui/IconNormalizer;

    iget-object v1, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mContext:Landroid/content/Context;

    iget v2, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mIconBitmapSize:I

    iget-boolean v3, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mShapeDetection:Z

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/auto/systemui/IconNormalizer;-><init>(Landroid/content/Context;IZ)V

    iput-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mNormalizer:Lcom/hcn/auto/systemui/IconNormalizer;

    .line 90
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto/systemui/BaseIconFactory;->mNormalizer:Lcom/hcn/auto/systemui/IconNormalizer;

    return-object v0
.end method
