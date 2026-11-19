.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart;
.super Ljava/lang/Object;
.source "Ease.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Quart"
.end annotation


# static fields
.field public static final easeIn:Landroid/animation/TimeInterpolator;

.field public static final easeInOut:Landroid/animation/TimeInterpolator;

.field public static final easeOut:Landroid/animation/TimeInterpolator;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 74
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart$1;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart$1;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart;->easeIn:Landroid/animation/TimeInterpolator;

    .line 79
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart$2;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart$2;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart;->easeOut:Landroid/animation/TimeInterpolator;

    .line 84
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart$3;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart$3;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart;->easeInOut:Landroid/animation/TimeInterpolator;

    return-void
.end method
