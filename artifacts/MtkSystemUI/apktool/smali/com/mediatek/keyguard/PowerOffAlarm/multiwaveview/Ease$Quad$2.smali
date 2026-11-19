.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quad$2;
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

    .line 59
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public getInterpolation(F)F
    .locals 2
    .param p1, "input"    # F

    .line 61
    const/high16 v0, 0x3f800000    # 1.0f

    div-float v0, p1, v0

    move p1, v0

    const/high16 v1, -0x40800000    # -1.0f

    mul-float/2addr v1, v0

    const/high16 v0, 0x40000000    # 2.0f

    sub-float v0, p1, v0

    mul-float/2addr v1, v0

    const/4 v0, 0x0

    add-float/2addr v1, v0

    return v1
.end method
