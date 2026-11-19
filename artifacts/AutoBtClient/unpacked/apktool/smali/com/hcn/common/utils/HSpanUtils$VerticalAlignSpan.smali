.class Lcom/hcn/common/utils/HSpanUtils$VerticalAlignSpan;
.super Landroid/text/style/ReplacementSpan;
.source "HSpanUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HSpanUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "VerticalAlignSpan"
.end annotation


# static fields
.field static final ALIGN_CENTER:I = 0x2

.field static final ALIGN_TOP:I = 0x3


# instance fields
.field final mVerticalAlignment:I


# direct methods
.method constructor <init>(I)V
    .locals 0

    .line 997
    invoke-direct {p0}, Landroid/text/style/ReplacementSpan;-><init>()V

    .line 998
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils$VerticalAlignSpan;->mVerticalAlignment:I

    return-void
.end method


# virtual methods
.method public draw(Landroid/graphics/Canvas;Ljava/lang/CharSequence;IIFIIILandroid/graphics/Paint;)V
    .locals 1

    .line 1009
    invoke-interface {p2, p3, p4}, Ljava/lang/CharSequence;->subSequence(II)Ljava/lang/CharSequence;

    move-result-object p2

    .line 1010
    invoke-virtual {p9}, Landroid/graphics/Paint;->getFontMetricsInt()Landroid/graphics/Paint$FontMetricsInt;

    move-result-object p3

    .line 1011
    invoke-interface {p2}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object p2

    int-to-float p4, p7

    iget v0, p3, Landroid/graphics/Paint$FontMetricsInt;->descent:I

    add-int/2addr v0, p7

    add-int/2addr v0, p7

    iget p3, p3, Landroid/graphics/Paint$FontMetricsInt;->ascent:I

    add-int/2addr v0, p3

    int-to-float p3, v0

    const/high16 p7, 0x40000000    # 2.0f

    div-float/2addr p3, p7

    add-int/2addr p8, p6

    int-to-float p6, p8

    div-float/2addr p6, p7

    sub-float/2addr p3, p6

    sub-float/2addr p4, p3

    invoke-virtual {p1, p2, p5, p4, p9}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    return-void
.end method

.method public getSize(Landroid/graphics/Paint;Ljava/lang/CharSequence;IILandroid/graphics/Paint$FontMetricsInt;)I
    .locals 0

    .line 1003
    invoke-interface {p2, p3, p4}, Ljava/lang/CharSequence;->subSequence(II)Ljava/lang/CharSequence;

    move-result-object p2

    .line 1004
    invoke-interface {p2}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result p1

    float-to-int p1, p1

    return p1
.end method
