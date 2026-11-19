.class Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;
.super Landroid/text/style/CharacterStyle;
.source "HSpanUtils.java"

# interfaces
.implements Landroid/text/style/UpdateAppearance;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HSpanUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "ShadowSpan"
.end annotation


# instance fields
.field private dx:F

.field private dy:F

.field private radius:F

.field private shadowColor:I


# direct methods
.method private constructor <init>(FFFI)V
    .locals 0

    .line 1415
    invoke-direct {p0}, Landroid/text/style/CharacterStyle;-><init>()V

    .line 1416
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->radius:F

    .line 1417
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->dx:F

    .line 1418
    iput p3, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->dy:F

    .line 1419
    iput p4, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->shadowColor:I

    return-void
.end method

.method synthetic constructor <init>(FFFILcom/hcn/common/utils/HSpanUtils$ShadowSpan-IA;)V
    .locals 0

    invoke-direct {p0, p1, p2, p3, p4}, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;-><init>(FFFI)V

    return-void
.end method


# virtual methods
.method public updateDrawState(Landroid/text/TextPaint;)V
    .locals 4

    .line 1424
    iget v0, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->radius:F

    iget v1, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->dx:F

    iget v2, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->dy:F

    iget v3, p0, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;->shadowColor:I

    invoke-virtual {p1, v0, v1, v2, v3}, Landroid/text/TextPaint;->setShadowLayer(FFFI)V

    return-void
.end method
