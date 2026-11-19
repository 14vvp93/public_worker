.class public Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
.super Ljava/lang/Object;
.source "TargetDrawable.java"


# static fields
.field public static final STATE_ACTIVE:[I

.field public static final STATE_FOCUSED:[I

.field public static final STATE_INACTIVE:[I


# instance fields
.field private mAlpha:F

.field private mDrawable:Landroid/graphics/drawable/Drawable;

.field private mEnabled:Z

.field private mNumDrawables:I

.field private mPositionX:F

.field private mPositionY:F

.field private final mResourceId:I

.field private mScaleX:F

.field private mScaleY:F

.field private mTranslationX:F

.field private mTranslationY:F


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 30
    const/4 v0, 0x2

    new-array v1, v0, [I

    fill-array-data v1, :array_0

    sput-object v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_ACTIVE:[I

    .line 32
    new-array v0, v0, [I

    fill-array-data v0, :array_1

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    .line 34
    const/4 v0, 0x3

    new-array v0, v0, [I

    fill-array-data v0, :array_2

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_FOCUSED:[I

    return-void

    :array_0
    .array-data 4
        0x101009e
        0x10100a2
    .end array-data

    :array_1
    .array-data 4
        0x101009e
        -0x10100a2
    .end array-data

    :array_2
    .array-data 4
        0x101009e
        -0x10100a2
        0x101009c
    .end array-data
.end method

.method public constructor <init>(Landroid/content/res/Resources;II)V
    .locals 1
    .param p1, "res"    # Landroid/content/res/Resources;
    .param p2, "resId"    # I
    .param p3, "count"    # I

    .line 59
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 38
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationX:F

    .line 39
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationY:F

    .line 40
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionX:F

    .line 41
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionY:F

    .line 42
    const/high16 v0, 0x3f800000    # 1.0f

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mScaleX:F

    .line 43
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mScaleY:F

    .line 44
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mAlpha:F

    .line 46
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mEnabled:Z

    .line 48
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mNumDrawables:I

    .line 60
    iput p2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mResourceId:I

    .line 61
    invoke-virtual {p0, p1, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setDrawable(Landroid/content/res/Resources;I)V

    .line 62
    iput p3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mNumDrawables:I

    .line 63
    return-void
.end method

.method private resizeDrawables()V
    .locals 7

    .line 124
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    instance-of v0, v0, Landroid/graphics/drawable/StateListDrawable;

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    .line 125
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    check-cast v0, Landroid/graphics/drawable/StateListDrawable;

    .line 126
    .local v0, "d":Landroid/graphics/drawable/StateListDrawable;
    const/4 v2, 0x0

    .line 127
    .local v2, "maxWidth":I
    const/4 v3, 0x0

    .line 129
    .local v3, "maxHeight":I
    move v4, v3

    move v3, v2

    move v2, v1

    .local v2, "i":I
    .local v3, "maxWidth":I
    .local v4, "maxHeight":I
    :goto_0
    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mNumDrawables:I

    if-ge v2, v5, :cond_0

    .line 130
    invoke-virtual {v0, v2}, Landroid/graphics/drawable/StateListDrawable;->selectDrawable(I)Z

    .line 131
    invoke-virtual {v0}, Landroid/graphics/drawable/StateListDrawable;->getCurrent()Landroid/graphics/drawable/Drawable;

    move-result-object v5

    .line 132
    .local v5, "childDrawable":Landroid/graphics/drawable/Drawable;
    invoke-virtual {v5}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v6

    invoke-static {v3, v6}, Ljava/lang/Math;->max(II)I

    move-result v3

    .line 133
    invoke-virtual {v5}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v6

    invoke-static {v4, v6}, Ljava/lang/Math;->max(II)I

    move-result v4

    .line 129
    .end local v5    # "childDrawable":Landroid/graphics/drawable/Drawable;
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 138
    .end local v2    # "i":I
    :cond_0
    invoke-virtual {v0, v1, v1, v3, v4}, Landroid/graphics/drawable/StateListDrawable;->setBounds(IIII)V

    .line 140
    move v2, v1

    .restart local v2    # "i":I
    :goto_1
    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mNumDrawables:I

    if-ge v2, v5, :cond_1

    .line 141
    invoke-virtual {v0, v2}, Landroid/graphics/drawable/StateListDrawable;->selectDrawable(I)Z

    .line 142
    invoke-virtual {v0}, Landroid/graphics/drawable/StateListDrawable;->getCurrent()Landroid/graphics/drawable/Drawable;

    move-result-object v5

    .line 145
    .restart local v5    # "childDrawable":Landroid/graphics/drawable/Drawable;
    invoke-virtual {v5, v1, v1, v3, v4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 140
    .end local v5    # "childDrawable":Landroid/graphics/drawable/Drawable;
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 147
    .end local v0    # "d":Landroid/graphics/drawable/StateListDrawable;
    .end local v2    # "i":I
    .end local v3    # "maxWidth":I
    .end local v4    # "maxHeight":I
    :cond_1
    goto :goto_2

    :cond_2
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_3

    .line 148
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 149
    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v2

    iget-object v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v3}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v3

    .line 148
    invoke-virtual {v0, v1, v1, v2, v3}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 151
    :cond_3
    :goto_2
    return-void
.end method


# virtual methods
.method public draw(Landroid/graphics/Canvas;)V
    .locals 4
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 218
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mEnabled:Z

    if-nez v0, :cond_0

    goto :goto_0

    .line 221
    :cond_0
    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/graphics/Canvas;->save(I)I

    .line 222
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mScaleX:F

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mScaleY:F

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionX:F

    iget v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionY:F

    invoke-virtual {p1, v0, v1, v2, v3}, Landroid/graphics/Canvas;->scale(FFFF)V

    .line 223
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationX:F

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionX:F

    add-float/2addr v0, v1

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationY:F

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionY:F

    add-float/2addr v1, v2

    invoke-virtual {p1, v0, v1}, Landroid/graphics/Canvas;->translate(FF)V

    .line 224
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v0

    int-to-float v0, v0

    const/high16 v1, -0x41000000    # -0.5f

    mul-float/2addr v0, v1

    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v2

    int-to-float v2, v2

    mul-float/2addr v1, v2

    invoke-virtual {p1, v0, v1}, Landroid/graphics/Canvas;->translate(FF)V

    .line 225
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mAlpha:F

    const/high16 v2, 0x437f0000    # 255.0f

    mul-float/2addr v1, v2

    invoke-static {v1}, Ljava/lang/Math;->round(F)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 226
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 227
    invoke-virtual {p1}, Landroid/graphics/Canvas;->restore()V

    .line 228
    return-void

    .line 219
    :cond_1
    :goto_0
    return-void
.end method

.method public getHeight()I
    .locals 1

    .line 214
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public getResourceId()I
    .locals 1

    .line 235
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mResourceId:I

    return v0
.end method

.method public getWidth()I
    .locals 1

    .line 210
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public getX()F
    .locals 1

    .line 174
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationX:F

    return v0
.end method

.method public getY()F
    .locals 1

    .line 178
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationY:F

    return v0
.end method

.method public isEnabled()Z
    .locals 1

    .line 115
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mEnabled:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public setAlpha(F)V
    .locals 0
    .param p1, "alpha"    # F

    .line 170
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mAlpha:F

    .line 171
    return-void
.end method

.method public setDrawable(Landroid/content/res/Resources;I)V
    .locals 2
    .param p1, "res"    # Landroid/content/res/Resources;
    .param p2, "resId"    # I

    .line 68
    const/4 v0, 0x0

    if-nez p2, :cond_0

    move-object v1, v0

    goto :goto_0

    :cond_0
    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 70
    .local v1, "drawable":Landroid/graphics/drawable/Drawable;
    :goto_0
    if-eqz v1, :cond_1

    invoke-virtual {v1}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    nop

    :cond_1
    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 71
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->resizeDrawables()V

    .line 72
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 73
    return-void
.end method

.method public setPositionX(F)V
    .locals 0
    .param p1, "x"    # F

    .line 194
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionX:F

    .line 195
    return-void
.end method

.method public setPositionY(F)V
    .locals 0
    .param p1, "y"    # F

    .line 198
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mPositionY:F

    .line 199
    return-void
.end method

.method public setState([I)V
    .locals 1
    .param p1, "state"    # [I

    .line 84
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    instance-of v0, v0, Landroid/graphics/drawable/StateListDrawable;

    if-eqz v0, :cond_0

    .line 85
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mDrawable:Landroid/graphics/drawable/Drawable;

    check-cast v0, Landroid/graphics/drawable/StateListDrawable;

    .line 86
    .local v0, "d":Landroid/graphics/drawable/StateListDrawable;
    invoke-virtual {v0, p1}, Landroid/graphics/drawable/StateListDrawable;->setState([I)Z

    .line 88
    .end local v0    # "d":Landroid/graphics/drawable/StateListDrawable;
    :cond_0
    return-void
.end method

.method public setX(F)V
    .locals 0
    .param p1, "x"    # F

    .line 154
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationX:F

    .line 155
    return-void
.end method

.method public setY(F)V
    .locals 0
    .param p1, "y"    # F

    .line 158
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->mTranslationY:F

    .line 159
    return-void
.end method
