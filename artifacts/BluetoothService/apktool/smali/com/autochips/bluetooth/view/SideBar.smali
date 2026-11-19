.class public Lcom/autochips/bluetooth/view/SideBar;
.super Landroid/view/View;
.source "SideBar.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;
    }
.end annotation


# static fields
.field public static b:[Ljava/lang/String;


# instance fields
.field private choose:I

.field private direction:I

.field private mBackgroundColor:I

.field private mTextDialog:Landroid/widget/TextView;

.field private onTouchingLetterChangedListener:Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;

.field private paint:Landroid/graphics/Paint;

.field private txt_color:I

.field private txt_color_choose:I

.field private txt_size:I


# direct methods
.method static constructor <clinit>()V
    .locals 27

    const-string v0, "A"

    const-string v1, "B"

    const-string v2, "C"

    const-string v3, "D"

    const-string v4, "E"

    const-string v5, "F"

    const-string v6, "G"

    const-string v7, "H"

    const-string v8, "I"

    const-string v9, "J"

    const-string v10, "K"

    const-string v11, "L"

    const-string v12, "M"

    const-string v13, "N"

    const-string v14, "O"

    const-string v15, "P"

    const-string v16, "Q"

    const-string v17, "R"

    const-string v18, "S"

    const-string v19, "T"

    const-string v20, "U"

    const-string v21, "V"

    const-string v22, "W"

    const-string v23, "X"

    const-string v24, "Y"

    const-string v25, "Z"

    const-string v26, "#"

    .line 20
    filled-new-array/range {v0 .. v26}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    .line 71
    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    const/4 p1, -0x1

    .line 24
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    .line 25
    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    const v0, 0xffffff

    .line 27
    iput v0, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color:I

    const/high16 v0, 0xc60000

    .line 28
    iput v0, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color_choose:I

    const/16 v0, 0x16

    .line 29
    iput v0, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_size:I

    const/4 v0, 0x0

    .line 30
    iput v0, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    .line 31
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->mBackgroundColor:I

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    .line 63
    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const/4 p1, -0x1

    .line 24
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    .line 25
    new-instance p2, Landroid/graphics/Paint;

    invoke-direct {p2}, Landroid/graphics/Paint;-><init>()V

    iput-object p2, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    const p2, 0xffffff

    .line 27
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color:I

    const/high16 p2, 0xc60000

    .line 28
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color_choose:I

    const/16 p2, 0x16

    .line 29
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_size:I

    const/4 p2, 0x0

    .line 30
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    .line 31
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->mBackgroundColor:I

    const p1, 0x7f050075

    .line 64
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result p1

    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color:I

    const p1, 0x7f05007a

    .line 65
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result p1

    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color_choose:I

    const p1, 0x7f09000c

    .line 66
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getInteger(I)I

    move-result p1

    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_size:I

    const p1, 0x7f09000b

    .line 67
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getInteger(I)I

    move-result p1

    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    .line 59
    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const/4 p1, -0x1

    .line 24
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    .line 25
    new-instance p2, Landroid/graphics/Paint;

    invoke-direct {p2}, Landroid/graphics/Paint;-><init>()V

    iput-object p2, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    const p2, 0xffffff

    .line 27
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color:I

    const/high16 p2, 0xc60000

    .line 28
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color_choose:I

    const/16 p2, 0x16

    .line 29
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_size:I

    const/4 p2, 0x0

    .line 30
    iput p2, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    .line 31
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->mBackgroundColor:I

    return-void
.end method


# virtual methods
.method public dispatchTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 6

    .line 123
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    const/4 v1, 0x1

    invoke-interface {v0, v1}, Landroid/view/ViewParent;->requestDisallowInterceptTouchEvent(Z)V

    .line 124
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    .line 125
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v2

    .line 126
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    .line 127
    iget v3, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    .line 128
    iget-object v4, p0, Lcom/autochips/bluetooth/view/SideBar;->onTouchingLetterChangedListener:Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;

    .line 130
    iget v5, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    if-nez v5, :cond_0

    .line 131
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->getWidth()I

    move-result p1

    int-to-float p1, p1

    div-float/2addr v2, p1

    sget-object p1, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length p1, p1

    int-to-float p1, p1

    mul-float/2addr v2, p1

    float-to-int p1, v2

    goto :goto_0

    .line 133
    :cond_0
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->getHeight()I

    move-result v2

    int-to-float v2, v2

    div-float/2addr p1, v2

    sget-object v2, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length v2, v2

    int-to-float v2, v2

    mul-float/2addr p1, v2

    float-to-int p1, p1

    :goto_0
    if-eq v0, v1, :cond_3

    if-eq v3, p1, :cond_4

    if-ltz p1, :cond_4

    .line 149
    sget-object v0, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length v2, v0

    if-ge p1, v2, :cond_4

    if-eqz v4, :cond_1

    .line 151
    aget-object v0, v0, p1

    invoke-interface {v4, v0}, Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;->onTouchingLetterChanged(Ljava/lang/String;)V

    .line 153
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/view/SideBar;->mTextDialog:Landroid/widget/TextView;

    if-eqz v0, :cond_2

    .line 154
    sget-object v2, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    aget-object v2, v2, p1

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 155
    iget-object v0, p0, Lcom/autochips/bluetooth/view/SideBar;->mTextDialog:Landroid/widget/TextView;

    const/4 v2, 0x0

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setVisibility(I)V

    .line 158
    :cond_2
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    .line 159
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->invalidate()V

    goto :goto_1

    .line 140
    :cond_3
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->invalidate()V

    .line 141
    iget-object p1, p0, Lcom/autochips/bluetooth/view/SideBar;->mTextDialog:Landroid/widget/TextView;

    if-eqz p1, :cond_4

    const/4 v0, 0x4

    .line 142
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_4
    :goto_1
    return v1
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 11

    .line 76
    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    .line 77
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->getHeight()I

    move-result v0

    .line 78
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->getWidth()I

    move-result v1

    .line 82
    iget v2, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    const/high16 v3, 0x3f800000    # 1.0f

    const/4 v4, 0x0

    const/high16 v5, 0x40000000    # 2.0f

    if-nez v2, :cond_0

    int-to-float v2, v1

    mul-float/2addr v2, v3

    .line 83
    sget-object v3, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length v6, v3

    int-to-float v6, v6

    div-float v6, v2, v6

    div-float/2addr v6, v5

    sub-float/2addr v2, v6

    .line 84
    array-length v3, v3

    int-to-float v3, v3

    div-float/2addr v2, v3

    goto :goto_0

    :cond_0
    int-to-float v2, v0

    mul-float/2addr v2, v3

    .line 86
    sget-object v3, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length v6, v3

    int-to-float v6, v6

    div-float v6, v2, v6

    div-float/2addr v6, v5

    sub-float/2addr v2, v6

    .line 87
    array-length v3, v3

    int-to-float v3, v3

    div-float/2addr v2, v3

    move v10, v4

    move v4, v2

    move v2, v10

    .line 89
    :goto_0
    iget v3, p0, Lcom/autochips/bluetooth/view/SideBar;->mBackgroundColor:I

    const/4 v6, -0x1

    if-eq v3, v6, :cond_1

    .line 90
    invoke-virtual {p1, v3}, Landroid/graphics/Canvas;->drawColor(I)V

    :cond_1
    const/4 v3, 0x0

    .line 92
    :goto_1
    sget-object v6, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length v6, v6

    if-ge v3, v6, :cond_4

    .line 95
    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    iget v7, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color:I

    invoke-virtual {v6, v7}, Landroid/graphics/Paint;->setColor(I)V

    .line 99
    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    sget-object v7, Landroid/graphics/Typeface;->DEFAULT_BOLD:Landroid/graphics/Typeface;

    invoke-virtual {v6, v7}, Landroid/graphics/Paint;->setTypeface(Landroid/graphics/Typeface;)Landroid/graphics/Typeface;

    .line 100
    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    const/4 v7, 0x1

    invoke-virtual {v6, v7}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 101
    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    iget v7, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_size:I

    int-to-float v7, v7

    invoke-virtual {v6, v7}, Landroid/graphics/Paint;->setTextSize(F)V

    .line 103
    iget v6, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    if-ne v3, v6, :cond_2

    .line 104
    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    iget v7, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_color_choose:I

    invoke-virtual {v6, v7}, Landroid/graphics/Paint;->setColor(I)V

    .line 107
    :cond_2
    iget v6, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    if-nez v6, :cond_3

    .line 108
    div-int/lit8 v6, v0, 0x2

    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    sget-object v7, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    aget-object v7, v7, v3

    invoke-virtual {v6, v7}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    int-to-float v6, v3

    mul-float/2addr v6, v2

    add-float/2addr v6, v2

    .line 110
    sget-object v7, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    aget-object v7, v7, v3

    const/high16 v8, 0x41000000    # 8.0f

    sub-float/2addr v6, v8

    const/high16 v8, 0x41c00000    # 24.0f

    iget-object v9, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    invoke-virtual {p1, v7, v6, v8, v9}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto :goto_2

    .line 112
    :cond_3
    div-int/lit8 v6, v1, 0x2

    int-to-float v6, v6

    iget-object v7, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    sget-object v8, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    aget-object v8, v8, v3

    invoke-virtual {v7, v8}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v7

    div-float/2addr v7, v5

    sub-float/2addr v6, v7

    int-to-float v7, v3

    mul-float/2addr v7, v4

    add-float/2addr v7, v4

    .line 114
    sget-object v8, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    aget-object v8, v8, v3

    iget-object v9, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    invoke-virtual {p1, v8, v6, v7, v9}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 116
    :goto_2
    iget-object v6, p0, Lcom/autochips/bluetooth/view/SideBar;->paint:Landroid/graphics/Paint;

    invoke-virtual {v6}, Landroid/graphics/Paint;->reset()V

    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    :cond_4
    return-void
.end method

.method public setChoose(I)V
    .locals 1

    if-ltz p1, :cond_0

    .line 34
    sget-object v0, Lcom/autochips/bluetooth/view/SideBar;->b:[Ljava/lang/String;

    array-length v0, v0

    if-ge p1, v0, :cond_0

    .line 35
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    goto :goto_0

    :cond_0
    const/4 p1, -0x1

    .line 37
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->choose:I

    .line 39
    :goto_0
    invoke-virtual {p0}, Lcom/autochips/bluetooth/view/SideBar;->invalidate()V

    return-void
.end method

.method public setDirection(I)V
    .locals 0

    .line 47
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->direction:I

    return-void
.end method

.method public setOnTouchingLetterChangedListener(Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;)V
    .locals 0

    .line 173
    iput-object p1, p0, Lcom/autochips/bluetooth/view/SideBar;->onTouchingLetterChangedListener:Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;

    return-void
.end method

.method public setShowBackground(I)V
    .locals 0

    .line 51
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->mBackgroundColor:I

    return-void
.end method

.method public setTextSize(I)V
    .locals 0

    .line 55
    iput p1, p0, Lcom/autochips/bluetooth/view/SideBar;->txt_size:I

    return-void
.end method

.method public setTextView(Landroid/widget/TextView;)V
    .locals 0

    .line 43
    iput-object p1, p0, Lcom/autochips/bluetooth/view/SideBar;->mTextDialog:Landroid/widget/TextView;

    return-void
.end method
