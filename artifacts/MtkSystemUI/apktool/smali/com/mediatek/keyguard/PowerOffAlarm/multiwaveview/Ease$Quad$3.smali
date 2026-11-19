.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quad$3;
.super Ljava/lang/Object;
.source "Ease.java"

# interfaces
.implements Landroid/animation/TimeInterpolator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quad;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 64
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public getInterpolation(F)F
    .locals 5
    .param p1, "input"    # F

    .line 66
    const/high16 v0, 0x3f000000    # 0.5f

    div-float v1, p1, v0

    move p1, v1

    const/high16 v2, 0x3f800000    # 1.0f

    cmpg-float v1, v1, v2

    const/4 v3, 0x0

    if-gez v1, :cond_0

    .line 67
    mul-float/2addr v0, p1

    mul-float/2addr v0, p1

    add-float/2addr v0, v3

    goto :goto_0

    .line 68
    :cond_0
    const/high16 v0, -0x41000000    # -0.5f

    sub-float v1, p1, v2

    move p1, v1

    const/high16 v4, 0x40000000    # 2.0f

    sub-float v4, p1, v4

    mul-float/2addr v1, v4

    sub-float/2addr v1, v2

    mul-float/2addr v0, v1

    add-float/2addr v0, v3

    .line 66
    :goto_0
    return v0
.end method
