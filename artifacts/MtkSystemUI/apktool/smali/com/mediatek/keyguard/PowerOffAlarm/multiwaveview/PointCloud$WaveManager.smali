.class public Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;
.super Ljava/lang/Object;
.source "PointCloud.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "WaveManager"
.end annotation


# instance fields
.field private alpha:F

.field private radius:F

.field final synthetic this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

.field private width:F


# direct methods
.method public constructor <init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;)V
    .locals 1
    .param p1, "this$0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    .line 45
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 46
    const/high16 v0, 0x42480000    # 50.0f

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->radius:F

    .line 47
    const/high16 v0, 0x43480000    # 200.0f

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->width:F

    .line 48
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->alpha:F

    return-void
.end method

.method static synthetic access$400(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    .line 45
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->radius:F

    return v0
.end method

.method static synthetic access$500(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    .line 45
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->width:F

    return v0
.end method

.method static synthetic access$600(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    .line 45
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->alpha:F

    return v0
.end method


# virtual methods
.method public setAlpha(F)V
    .locals 0
    .param p1, "a"    # F

    .line 58
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->alpha:F

    .line 59
    return-void
.end method

.method public setRadius(F)V
    .locals 0
    .param p1, "r"    # F

    .line 50
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->radius:F

    .line 51
    return-void
.end method
