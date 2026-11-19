.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;
.super Ljava/lang/Object;
.source "Ease.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Cubic"
.end annotation


# static fields
.field public static final easeIn:Landroid/animation/TimeInterpolator;

.field public static final easeInOut:Landroid/animation/TimeInterpolator;

.field public static final easeOut:Landroid/animation/TimeInterpolator;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 34
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic$1;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic$1;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeIn:Landroid/animation/TimeInterpolator;

    .line 39
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic$2;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic$2;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeOut:Landroid/animation/TimeInterpolator;

    .line 44
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic$3;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic$3;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeInOut:Landroid/animation/TimeInterpolator;

    return-void
.end method
