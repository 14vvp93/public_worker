.class public Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;
.super Ljava/lang/Object;
.source "PointCloud.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;,
        Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;,
        Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;
    }
.end annotation


# instance fields
.field glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

.field private mCenterX:F

.field private mCenterY:F

.field private mDrawable:Landroid/graphics/drawable/Drawable;

.field private mOuterRadius:F

.field private mPaint:Landroid/graphics/Paint;

.field private mPointCloud:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;",
            ">;"
        }
    .end annotation
.end field

.field private mScale:F

.field waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;


# direct methods
.method public constructor <init>(Landroid/graphics/drawable/Drawable;)V
    .locals 3
    .param p1, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 117
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 32
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPointCloud:Ljava/util/ArrayList;

    .line 37
    const/high16 v0, 0x3f800000    # 1.0f

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mScale:F

    .line 41
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    .line 42
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    .line 118
    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    .line 119
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setFilterBitmap(Z)V

    .line 120
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    const/16 v2, 0xff

    invoke-static {v2, v2, v2}, Landroid/graphics/Color;->rgb(III)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setColor(I)V

    .line 121
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 122
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setDither(Z)V

    .line 124
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 125
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_0

    .line 126
    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v0

    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v1

    const/4 v2, 0x0

    invoke-virtual {p1, v2, v2, v0, v1}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 128
    :cond_0
    return-void
.end method

.method private static hypot(FF)F
    .locals 4
    .param p0, "x"    # F
    .param p1, "y"    # F

    .line 170
    float-to-double v0, p0

    float-to-double v2, p1

    invoke-static {v0, v1, v2, v3}, Ljava/lang/Math;->hypot(DD)D

    move-result-wide v0

    double-to-float v0, v0

    return v0
.end method

.method private interp(FFF)F
    .locals 1
    .param p1, "min"    # F
    .param p2, "max"    # F
    .param p3, "f"    # F

    .line 200
    sub-float v0, p2, p1

    mul-float/2addr v0, p3

    add-float/2addr v0, p1

    return v0
.end method

.method private static max(FF)F
    .locals 1
    .param p0, "a"    # F
    .param p1, "b"    # F

    .line 174
    cmpl-float v0, p0, p1

    if-lez v0, :cond_0

    move v0, p0

    goto :goto_0

    :cond_0
    move v0, p1

    :goto_0
    return v0
.end method


# virtual methods
.method public draw(Landroid/graphics/Canvas;)V
    .locals 13
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 204
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPointCloud:Ljava/util/ArrayList;

    .line 205
    .local v0, "points":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;>;"
    const/4 v1, 0x1

    invoke-virtual {p1, v1}, Landroid/graphics/Canvas;->save(I)I

    .line 206
    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mScale:F

    iget v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mScale:F

    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mCenterX:F

    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mCenterY:F

    invoke-virtual {p1, v2, v3, v4, v5}, Landroid/graphics/Canvas;->scale(FFFF)V

    .line 207
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_0
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-ge v2, v3, :cond_2

    .line 208
    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;

    .line 209
    .local v3, "point":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;
    const/high16 v4, 0x40000000    # 2.0f

    iget v5, v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->radius:F

    iget v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mOuterRadius:F

    div-float/2addr v5, v6

    const/high16 v6, 0x40800000    # 4.0f

    invoke-direct {p0, v6, v4, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->interp(FFF)F

    move-result v4

    .line 211
    .local v4, "pointSize":F
    iget v5, v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->x:F

    iget v7, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mCenterX:F

    add-float/2addr v5, v7

    .line 212
    .local v5, "px":F
    iget v7, v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->y:F

    iget v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mCenterY:F

    add-float/2addr v7, v8

    .line 213
    .local v7, "py":F
    invoke-virtual {p0, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->getAlphaForPoint(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;)I

    move-result v8

    .line 215
    .local v8, "alpha":I
    if-nez v8, :cond_0

    .end local v3    # "point":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;
    .end local v4    # "pointSize":F
    .end local v5    # "px":F
    .end local v7    # "py":F
    .end local v8    # "alpha":I
    goto :goto_1

    .line 217
    .restart local v3    # "point":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;
    .restart local v4    # "pointSize":F
    .restart local v5    # "px":F
    .restart local v7    # "py":F
    .restart local v8    # "alpha":I
    :cond_0
    iget-object v9, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v9, :cond_1

    .line 218
    invoke-virtual {p1, v1}, Landroid/graphics/Canvas;->save(I)I

    .line 219
    iget-object v9, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v9}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v9

    int-to-float v9, v9

    const/high16 v10, 0x3f000000    # 0.5f

    mul-float/2addr v9, v10

    .line 220
    .local v9, "cx":F
    iget-object v11, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v11}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v11

    int-to-float v11, v11

    mul-float/2addr v11, v10

    .line 221
    .local v11, "cy":F
    div-float v6, v4, v6

    .line 222
    .local v6, "s":F
    invoke-virtual {p1, v6, v6, v5, v7}, Landroid/graphics/Canvas;->scale(FFFF)V

    .line 223
    sub-float v10, v5, v9

    sub-float v12, v7, v11

    invoke-virtual {p1, v10, v12}, Landroid/graphics/Canvas;->translate(FF)V

    .line 224
    iget-object v10, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v10, v8}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 225
    iget-object v10, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v10, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 226
    invoke-virtual {p1}, Landroid/graphics/Canvas;->restore()V

    .line 227
    .end local v6    # "s":F
    .end local v9    # "cx":F
    .end local v11    # "cy":F
    goto :goto_1

    .line 228
    :cond_1
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {v6, v8}, Landroid/graphics/Paint;->setAlpha(I)V

    .line 229
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v5, v7, v4, v6}, Landroid/graphics/Canvas;->drawCircle(FFFLandroid/graphics/Paint;)V

    .line 207
    .end local v3    # "point":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;
    .end local v4    # "pointSize":F
    .end local v5    # "px":F
    .end local v7    # "py":F
    .end local v8    # "alpha":I
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 232
    .end local v2    # "i":I
    :cond_2
    invoke-virtual {p1}, Landroid/graphics/Canvas;->restore()V

    .line 233
    return-void
.end method

.method public getAlphaForPoint(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;)I
    .locals 11
    .param p1, "point"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;

    .line 179
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->access$000(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F

    move-result v0

    iget v1, p1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->x:F

    sub-float/2addr v0, v1

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    invoke-static {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->access$100(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F

    move-result v1

    iget v2, p1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->y:F

    sub-float/2addr v1, v2

    invoke-static {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->hypot(FF)F

    move-result v0

    .line 180
    .local v0, "glowDistance":F
    const/4 v1, 0x0

    .line 182
    .local v1, "glowAlpha":F
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    invoke-static {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->access$200(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F

    move-result v2

    cmpg-float v2, v0, v2

    const-wide v3, 0x3fe921fb54442d18L    # 0.7853981633974483

    const/4 v5, 0x0

    if-gez v2, :cond_0

    .line 183
    float-to-double v6, v0

    mul-double/2addr v6, v3

    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    invoke-static {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->access$200(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F

    move-result v2

    float-to-double v8, v2

    div-double/2addr v6, v8

    invoke-static {v6, v7}, Ljava/lang/Math;->cos(D)D

    move-result-wide v6

    .line 184
    .local v6, "cos":D
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    invoke-static {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->access$300(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F

    move-result v2

    const-wide/high16 v8, 0x4024000000000000L    # 10.0

    invoke-static {v6, v7, v8, v9}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v8

    double-to-float v8, v8

    invoke-static {v5, v8}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->max(FF)F

    move-result v8

    mul-float v1, v2, v8

    .line 188
    .end local v6    # "cos":D
    :cond_0
    iget v2, p1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->x:F

    iget v6, p1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;->y:F

    invoke-static {v2, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->hypot(FF)F

    move-result v2

    .line 189
    .local v2, "radius":F
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    invoke-static {v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->access$400(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F

    move-result v6

    sub-float v6, v2, v6

    .line 190
    .local v6, "distanceToWaveRing":F
    const/4 v7, 0x0

    .line 191
    .local v7, "waveAlpha":F
    iget-object v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    invoke-static {v8}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->access$500(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F

    move-result v8

    const/high16 v9, 0x3f000000    # 0.5f

    mul-float/2addr v8, v9

    cmpg-float v8, v6, v8

    if-gez v8, :cond_1

    cmpg-float v8, v6, v5

    if-gez v8, :cond_1

    .line 192
    float-to-double v8, v6

    mul-double/2addr v3, v8

    iget-object v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    invoke-static {v8}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->access$500(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F

    move-result v8

    float-to-double v8, v8

    div-double/2addr v3, v8

    invoke-static {v3, v4}, Ljava/lang/Math;->cos(D)D

    move-result-wide v3

    .line 193
    .local v3, "cos":D
    iget-object v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    invoke-static {v8}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->access$600(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F

    move-result v8

    const-wide/high16 v9, 0x4034000000000000L    # 20.0

    invoke-static {v3, v4, v9, v10}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v9

    double-to-float v9, v9

    invoke-static {v5, v9}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->max(FF)F

    move-result v5

    mul-float v7, v8, v5

    .line 196
    .end local v3    # "cos":D
    :cond_1
    invoke-static {v1, v7}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->max(FF)F

    move-result v3

    const/high16 v4, 0x437f0000    # 255.0f

    mul-float/2addr v3, v4

    float-to-int v3, v3

    return v3
.end method

.method public makePointCloud(FF)V
    .locals 19
    .param p1, "innerRadius"    # F
    .param p2, "outerRadius"    # F

    move-object/from16 v0, p0

    .line 136
    move/from16 v2, p2

    const/4 v3, 0x0

    cmpl-float v3, p1, v3

    if-nez v3, :cond_0

    .line 137
    const-string v3, "PointCloud"

    const-string v4, "Must specify an inner radius"

    invoke-static {v3, v4}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 138
    return-void

    .line 140
    :cond_0
    iput v2, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mOuterRadius:F

    .line 141
    iget-object v3, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPointCloud:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->clear()V

    .line 142
    sub-float v3, v2, p1

    .line 143
    .local v3, "pointAreaRadius":F
    const v4, 0x40c90fdb

    mul-float v5, v4, p1

    const/high16 v6, 0x41000000    # 8.0f

    div-float/2addr v5, v6

    .line 144
    .local v5, "ds":F
    div-float v6, v3, v5

    invoke-static {v6}, Ljava/lang/Math;->round(F)I

    move-result v6

    .line 145
    .local v6, "bands":I
    int-to-float v7, v6

    div-float v7, v3, v7

    .line 146
    .local v7, "dr":F
    move/from16 v8, p1

    .line 147
    .local v8, "r":F
    move v10, v8

    const/4 v8, 0x0

    .local v8, "b":I
    .local v10, "r":F
    :goto_0
    if-gt v8, v6, :cond_2

    .line 148
    mul-float v11, v4, v10

    .line 149
    .local v11, "circumference":F
    div-float v12, v11, v5

    float-to-int v12, v12

    .line 150
    .local v12, "pointsInBand":I
    const v13, 0x3fc90fdb

    .line 151
    .local v13, "eta":F
    int-to-float v14, v12

    div-float v14, v4, v14

    .line 152
    .local v14, "dEta":F
    move v15, v13

    const/4 v13, 0x0

    .local v13, "i":I
    .local v15, "eta":F
    :goto_1
    if-ge v13, v12, :cond_1

    .line 153
    move/from16 v16, v5

    float-to-double v4, v15

    .end local v5    # "ds":F
    .local v16, "ds":F
    invoke-static {v4, v5}, Ljava/lang/Math;->cos(D)D

    move-result-wide v4

    double-to-float v4, v4

    mul-float/2addr v4, v10

    .line 154
    .local v4, "x":F
    move/from16 v17, v10

    float-to-double v9, v15

    .end local v10    # "r":F
    .local v17, "r":F
    invoke-static {v9, v10}, Ljava/lang/Math;->sin(D)D

    move-result-wide v9

    double-to-float v5, v9

    mul-float v10, v17, v5

    .line 155
    .local v10, "y":F
    add-float/2addr v15, v14

    .line 156
    iget-object v5, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mPointCloud:Ljava/util/ArrayList;

    new-instance v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;

    move/from16 v1, v17

    invoke-direct {v9, v0, v4, v10, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$Point;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;FFF)V

    .end local v17    # "r":F
    .local v1, "r":F
    invoke-virtual {v5, v9}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 152
    .end local v4    # "x":F
    .end local v10    # "y":F
    add-int/lit8 v13, v13, 0x1

    move v10, v1

    move/from16 v5, v16

    const v4, 0x40c90fdb

    goto :goto_1

    .line 147
    .end local v1    # "r":F
    .end local v11    # "circumference":F
    .end local v12    # "pointsInBand":I
    .end local v13    # "i":I
    .end local v14    # "dEta":F
    .end local v15    # "eta":F
    .end local v16    # "ds":F
    .restart local v5    # "ds":F
    .local v10, "r":F
    :cond_1
    move/from16 v16, v5

    move v1, v10

    .end local v5    # "ds":F
    .end local v10    # "r":F
    .restart local v1    # "r":F
    .restart local v16    # "ds":F
    add-int/lit8 v8, v8, 0x1

    add-float v10, v1, v7

    .end local v1    # "r":F
    .restart local v10    # "r":F
    const v4, 0x40c90fdb

    goto :goto_0

    .line 159
    .end local v8    # "b":I
    .end local v16    # "ds":F
    .restart local v5    # "ds":F
    :cond_2
    move/from16 v16, v5

    move v1, v10

    .end local v5    # "ds":F
    .end local v10    # "r":F
    .restart local v1    # "r":F
    .restart local v16    # "ds":F
    return-void
.end method

.method public setCenter(FF)V
    .locals 0
    .param p1, "x"    # F
    .param p2, "y"    # F

    .line 131
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mCenterX:F

    .line 132
    iput p2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mCenterY:F

    .line 133
    return-void
.end method

.method public setScale(F)V
    .locals 0
    .param p1, "scale"    # F

    .line 162
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->mScale:F

    .line 163
    return-void
.end method
