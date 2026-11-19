.class public Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;
.super Landroid/view/View;
.source "CustomCheckBox.java"


# annotations
.annotation build Landroid/annotation/SuppressLint;
    value = {
        "NewApi"
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String;


# instance fields
.field private mBitmapPaint:Landroid/graphics/Paint;

.field private mCheckIcon:Landroid/graphics/drawable/Drawable;

.field private mCheckableScope:Landroid/graphics/RectF;

.field private mChecked:Z

.field private mContext:Landroid/content/Context;

.field private mDrawable:Landroid/graphics/drawable/Drawable;

.field private mDrawableSize:F

.field private mDrawableX:F

.field private mDrawableY:F

.field private mIconGravity:I

.field private mIconSize:F

.field private mIconX:F

.field private mIconY:F

.field private mOnClickListener:Landroid/view/View$OnClickListener;

.field private mPadding:F

.field private mText:Ljava/lang/String;

.field private mTextColor:I

.field private mTextMarginTop:F

.field private mTextPaint:Landroid/text/TextPaint;

.field private mTextSize:F

.field private mTextWidth:F

.field private mTextX:F

.field private mTextY:F

.field private mUncheckIcon:Landroid/graphics/drawable/Drawable;

.field private mViewWidth:F


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 29
    const-class v0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;

    invoke-virtual {v0}, Ljava/lang/Class;->getCanonicalName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 57
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 58
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyleAttr"    # I

    .line 61
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    .line 62
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyleAttr"    # I
    .param p4, "defStyleRes"    # I

    .line 65
    invoke-direct {p0, p1, p2, p3, p4}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    .line 40
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mChecked:Z

    .line 66
    invoke-direct {p0, p1, p2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->init(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 67
    return-void
.end method

.method private calculateIconXY()V
    .locals 4

    .line 151
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconGravity:I

    const/4 v1, 0x0

    const/high16 v2, 0x40000000    # 2.0f

    const/4 v3, 0x2

    if-eq v0, v3, :cond_1

    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconGravity:I

    const/4 v3, 0x1

    if-ne v0, v3, :cond_0

    goto :goto_0

    .line 154
    :cond_0
    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    goto :goto_1

    .line 152
    :cond_1
    :goto_0
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v0, v2

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    .line 156
    :goto_1
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconGravity:I

    packed-switch v0, :pswitch_data_0

    .line 159
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    sub-float/2addr v0, v1

    div-float/2addr v0, v2

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    .line 160
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    sub-float/2addr v0, v1

    div-float/2addr v0, v2

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    add-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    .line 161
    goto :goto_2

    .line 175
    :pswitch_0
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    sub-float/2addr v0, v1

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v1, v2

    sub-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    .line 176
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    add-float/2addr v0, v1

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v1, v2

    sub-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    goto :goto_2

    .line 171
    :pswitch_1
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v1, v2

    sub-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    .line 172
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    add-float/2addr v0, v1

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v1, v2

    sub-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    .line 173
    goto :goto_2

    .line 167
    :pswitch_2
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    sub-float/2addr v0, v3

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v3, v2

    sub-float/2addr v0, v3

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    .line 168
    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    .line 169
    goto :goto_2

    .line 163
    :pswitch_3
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    div-float/2addr v3, v2

    sub-float/2addr v0, v3

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    .line 164
    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    .line 165
    nop

    .line 179
    :goto_2
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private init(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 70
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mContext:Landroid/content/Context;

    .line 72
    sget-object v0, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox:[I

    invoke-virtual {p1, p2, v0}, Landroid/content/Context;->obtainStyledAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object v0

    .line 73
    .local v0, "a":Landroid/content/res/TypedArray;
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_padding:I

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->CustomCheckBox_padding:I

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    .line 75
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_drawableSize:I

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->CustomCheckBox_drawableSize:I

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    .line 76
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_drawable:I

    invoke-virtual {v0, v1}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 77
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    if-nez v1, :cond_0

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    sget v2, Lcom/hcn/widgets/dynamicrecyclerview/R$drawable;->customcheckbox_drawable_default:I

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    :goto_0
    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 80
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_checkIcon:I

    invoke-virtual {v0, v1}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    .line 81
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    if-nez v1, :cond_1

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    sget v2, Lcom/hcn/widgets/dynamicrecyclerview/R$drawable;->icon_checked:I

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    goto :goto_1

    :cond_1
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    :goto_1
    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    .line 82
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_uncheckIcon:I

    invoke-virtual {v0, v1}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mUncheckIcon:Landroid/graphics/drawable/Drawable;

    .line 83
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_icon_Gravity:I

    const/4 v2, 0x2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getInteger(II)I

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconGravity:I

    .line 84
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_iconSize:I

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v2

    int-to-float v2, v2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    .line 86
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_text:I

    invoke-virtual {v0, v1}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mText:Ljava/lang/String;

    .line 87
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_textSize:I

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->CustomCheckBox_textSize:I

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextSize:F

    .line 88
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_textColor:I

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$color;->black:I

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getColor(I)I

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getColor(II)I

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextColor:I

    .line 89
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$styleable;->CustomCheckBox_textMarginTop:I

    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->CustomCheckBox_textMarginTop:I

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextMarginTop:F

    .line 91
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->recycle()V

    .line 94
    new-instance v1, Landroid/graphics/Paint;

    invoke-direct {v1}, Landroid/graphics/Paint;-><init>()V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mBitmapPaint:Landroid/graphics/Paint;

    .line 95
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mBitmapPaint:Landroid/graphics/Paint;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 96
    new-instance v1, Landroid/text/TextPaint;

    invoke-direct {v1}, Landroid/text/TextPaint;-><init>()V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    .line 97
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    invoke-virtual {v1, v2}, Landroid/text/TextPaint;->setAntiAlias(Z)V

    .line 98
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextColor:I

    invoke-virtual {v1, v2}, Landroid/text/TextPaint;->setColor(I)V

    .line 99
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextSize:F

    invoke-virtual {v1, v2}, Landroid/text/TextPaint;->setTextSize(F)V

    .line 100
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    sget-object v2, Landroid/graphics/Paint$Align;->CENTER:Landroid/graphics/Paint$Align;

    invoke-virtual {v1, v2}, Landroid/text/TextPaint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    .line 102
    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v2, v3

    add-float/2addr v1, v2

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    .line 103
    return-void
.end method


# virtual methods
.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 6
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 126
    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    .line 129
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableX:F

    float-to-int v1, v1

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    float-to-int v2, v2

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableX:F

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    add-float/2addr v3, v4

    float-to-int v3, v3

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    iget v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    add-float/2addr v4, v5

    float-to-int v4, v4

    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 130
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 133
    iget-boolean v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mChecked:Z

    if-eqz v0, :cond_0

    .line 134
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    goto :goto_0

    .line 136
    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mUncheckIcon:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_1

    .line 137
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mUncheckIcon:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 142
    :cond_1
    :goto_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mText:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/text/TextPaint;->measureText(Ljava/lang/String;)F

    move-result v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextWidth:F

    .line 143
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextWidth:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    cmpl-float v0, v0, v1

    if-ltz v0, :cond_2

    .line 144
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mText:Ljava/lang/String;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    sget-object v3, Landroid/text/TextUtils$TruncateAt;->END:Landroid/text/TextUtils$TruncateAt;

    invoke-static {v0, v1, v2, v3}, Landroid/text/TextUtils;->ellipsize(Ljava/lang/CharSequence;Landroid/text/TextPaint;FLandroid/text/TextUtils$TruncateAt;)Ljava/lang/CharSequence;

    move-result-object v0

    invoke-interface {v0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mText:Ljava/lang/String;

    .line 147
    :cond_2
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mText:Ljava/lang/String;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextX:F

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextY:F

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    invoke-virtual {p1, v0, v1, v2, v3}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 148
    return-void
.end method

.method protected onLayout(ZIIII)V
    .locals 6
    .param p1, "changed"    # Z
    .param p2, "left"    # I
    .param p3, "top"    # I
    .param p4, "right"    # I
    .param p5, "bottom"    # I

    .line 107
    invoke-super/range {p0 .. p5}, Landroid/view/View;->onLayout(ZIIII)V

    .line 109
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableX:F

    .line 111
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->calculateIconXY()V

    .line 113
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    const/high16 v1, 0x40000000    # 2.0f

    div-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextX:F

    .line 114
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableY:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    add-float/2addr v0, v1

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextMarginTop:F

    add-float/2addr v0, v1

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextSize:F

    add-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextY:F

    .line 116
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    float-to-int v1, v1

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    float-to-int v2, v2

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    add-float/2addr v3, v4

    float-to-int v3, v3

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    iget v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    add-float/2addr v4, v5

    float-to-int v4, v4

    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 117
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mUncheckIcon:Landroid/graphics/drawable/Drawable;

    if-eqz v0, :cond_0

    .line 118
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mUncheckIcon:Landroid/graphics/drawable/Drawable;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    float-to-int v1, v1

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    float-to-int v2, v2

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    add-float/2addr v3, v4

    float-to-int v3, v3

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    iget v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    add-float/2addr v4, v5

    float-to-int v4, v4

    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 121
    :cond_0
    new-instance v0, Landroid/graphics/RectF;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    const/4 v2, 0x0

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    add-float/2addr v3, v4

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    invoke-direct {v0, v1, v2, v3, v4}, Landroid/graphics/RectF;-><init>(FFFF)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckableScope:Landroid/graphics/RectF;

    .line 122
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 3
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 272
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 274
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckableScope:Landroid/graphics/RectF;

    iget v2, v2, Landroid/graphics/RectF;->left:F

    cmpl-float v0, v0, v2

    if-ltz v0, :cond_0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckableScope:Landroid/graphics/RectF;

    iget v2, v2, Landroid/graphics/RectF;->right:F

    cmpg-float v0, v0, v2

    if-gtz v0, :cond_0

    .line 275
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v0

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckableScope:Landroid/graphics/RectF;

    iget v2, v2, Landroid/graphics/RectF;->top:F

    cmpl-float v0, v0, v2

    if-ltz v0, :cond_0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v0

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckableScope:Landroid/graphics/RectF;

    iget v2, v2, Landroid/graphics/RectF;->bottom:F

    cmpg-float v0, v0, v2

    if-gtz v0, :cond_0

    .line 276
    iget-boolean v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mChecked:Z

    xor-int/2addr v0, v1

    invoke-virtual {p0, v0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setChecked(Z)V

    .line 277
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->redrawCheckIcon()V

    .line 278
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mOnClickListener:Landroid/view/View$OnClickListener;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-interface {v0, p0}, Landroid/view/View$OnClickListener;->onClick(Landroid/view/View;)V

    .line 281
    :cond_0
    return v1
.end method

.method public redrawCheckIcon()V
    .locals 5

    .line 183
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    float-to-double v0, v0

    invoke-static {v0, v1}, Ljava/lang/Math;->floor(D)D

    move-result-wide v0

    double-to-int v0, v0

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    float-to-double v1, v1

    invoke-static {v1, v2}, Ljava/lang/Math;->floor(D)D

    move-result-wide v1

    double-to-int v1, v1

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconX:F

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    add-float/2addr v2, v3

    float-to-double v2, v2

    invoke-static {v2, v3}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v2

    double-to-int v2, v2

    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconY:F

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    add-float/2addr v3, v4

    float-to-double v3, v3

    invoke-static {v3, v4}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v3

    double-to-int v3, v3

    invoke-virtual {p0, v0, v1, v2, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->postInvalidate(IIII)V

    .line 184
    return-void
.end method

.method public setCheckIcon(Landroid/graphics/drawable/Drawable;)V
    .locals 1
    .param p1, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 212
    if-nez p1, :cond_0

    return-void

    .line 214
    :cond_0
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    .line 215
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconSize:F

    .line 216
    return-void
.end method

.method public setChecked(Z)V
    .locals 0
    .param p1, "checked"    # Z

    .line 191
    iput-boolean p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mChecked:Z

    .line 192
    return-void
.end method

.method public setDrawable(Landroid/graphics/drawable/Drawable;)V
    .locals 0
    .param p1, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 206
    if-nez p1, :cond_0

    return-void

    .line 208
    :cond_0
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawable:Landroid/graphics/drawable/Drawable;

    .line 209
    return-void
.end method

.method public setDrawableSize(I)V
    .locals 3
    .param p1, "size"    # I

    .line 199
    if-gtz p1, :cond_0

    return-void

    .line 201
    :cond_0
    int-to-float v0, p1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    .line 202
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mDrawableSize:F

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    const/high16 v2, 0x40000000    # 2.0f

    mul-float/2addr v1, v2

    add-float/2addr v0, v1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mViewWidth:F

    .line 203
    return-void
.end method

.method public setIconGravity(I)V
    .locals 1
    .param p1, "gravity"    # I

    .line 231
    if-ltz p1, :cond_0

    const/4 v0, 0x4

    if-gt p1, v0, :cond_0

    .line 232
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconGravity:I

    goto :goto_0

    .line 234
    :cond_0
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mIconGravity:I

    .line 236
    :goto_0
    return-void
.end method

.method public setOnClickListener(Landroid/view/View$OnClickListener;)V
    .locals 0
    .param p1, "listener"    # Landroid/view/View$OnClickListener;

    .line 267
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mOnClickListener:Landroid/view/View$OnClickListener;

    .line 268
    return-void
.end method

.method public setPadding(F)V
    .locals 0
    .param p1, "padding"    # F

    .line 263
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mPadding:F

    .line 264
    return-void
.end method

.method public setText(Ljava/lang/String;)V
    .locals 0
    .param p1, "text"    # Ljava/lang/String;

    .line 239
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mText:Ljava/lang/String;

    .line 240
    return-void
.end method

.method public setTextColor(I)V
    .locals 2
    .param p1, "color"    # I

    .line 250
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextColor:I

    .line 251
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextColor:I

    invoke-virtual {v0, v1}, Landroid/text/TextPaint;->setColor(I)V

    .line 252
    return-void
.end method

.method public setTextMarginTop(F)V
    .locals 0
    .param p1, "textMarginTop"    # F

    .line 259
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextMarginTop:F

    .line 260
    return-void
.end method

.method public setTextSize(I)V
    .locals 2
    .param p1, "textSize"    # I

    .line 243
    if-gtz p1, :cond_0

    return-void

    .line 245
    :cond_0
    int-to-float v0, p1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextSize:F

    .line 246
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextPaint:Landroid/text/TextPaint;

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->mTextSize:F

    invoke-virtual {v0, v1}, Landroid/text/TextPaint;->setTextSize(F)V

    .line 247
    return-void
.end method
