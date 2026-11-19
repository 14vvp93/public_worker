.class public Lcom/hcn/auto/systemui/NoPaddingTextView;
.super Landroid/widget/TextView;
.source "NoPaddingTextView.java"


# annotations
.annotation build Landroid/annotation/SuppressLint;
    value = {
        "AppCompatCustomView"
    }
.end annotation


# instance fields
.field private mRect:Landroid/graphics/Rect;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 17
    invoke-direct {p0, p1, p2}, Landroid/widget/TextView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 18
    return-void
.end method


# virtual methods
.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 6
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 38
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->getText()Ljava/lang/CharSequence;

    move-result-object v0

    invoke-interface {v0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v0

    .line 39
    .local v0, "text":Ljava/lang/String;
    iget-object v1, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    iget v1, v1, Landroid/graphics/Rect;->left:I

    .line 40
    .local v1, "left":I
    iget-object v2, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    iget v2, v2, Landroid/graphics/Rect;->top:I

    .line 41
    .local v2, "top":I
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->getPaint()Landroid/text/TextPaint;

    move-result-object v3

    .line 42
    .local v3, "paint":Landroid/graphics/Paint;
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->getCurrentTextColor()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/graphics/Paint;->setColor(I)V

    .line 43
    neg-int v4, v1

    int-to-float v4, v4

    neg-int v5, v2

    int-to-float v5, v5

    invoke-virtual {p1, v0, v4, v5, v3}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 44
    return-void
.end method

.method protected onMeasure(II)V
    .locals 5
    .param p1, "widthMeasureSpec"    # I
    .param p2, "heightMeasureSpec"    # I

    .line 26
    invoke-super {p0, p1, p2}, Landroid/widget/TextView;->onMeasure(II)V

    .line 27
    iget-object v0, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    if-nez v0, :cond_0

    .line 28
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    .line 30
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->getPaint()Landroid/text/TextPaint;

    move-result-object v0

    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->getText()Ljava/lang/CharSequence;

    move-result-object v1

    invoke-interface {v1}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->getText()Ljava/lang/CharSequence;

    move-result-object v3

    invoke-interface {v3}, Ljava/lang/CharSequence;->length()I

    move-result v3

    iget-object v4, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/text/TextPaint;->getTextBounds(Ljava/lang/String;IILandroid/graphics/Rect;)V

    .line 31
    iget-object v0, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    invoke-virtual {v0}, Landroid/graphics/Rect;->width()I

    move-result v0

    .line 32
    .local v0, "width":I
    iget-object v1, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    invoke-virtual {v1}, Landroid/graphics/Rect;->height()I

    move-result v1

    .line 33
    .local v1, "height":I
    invoke-virtual {p0, v0, v1}, Lcom/hcn/auto/systemui/NoPaddingTextView;->setMeasuredDimension(II)V

    .line 34
    return-void
.end method

.method public setText(Ljava/lang/CharSequence;Landroid/widget/TextView$BufferType;)V
    .locals 1
    .param p1, "text"    # Ljava/lang/CharSequence;
    .param p2, "type"    # Landroid/widget/TextView$BufferType;

    .line 48
    invoke-super {p0, p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;Landroid/widget/TextView$BufferType;)V

    .line 49
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/auto/systemui/NoPaddingTextView;->mRect:Landroid/graphics/Rect;

    .line 50
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/NoPaddingTextView;->requestLayout()V

    .line 51
    return-void
.end method
