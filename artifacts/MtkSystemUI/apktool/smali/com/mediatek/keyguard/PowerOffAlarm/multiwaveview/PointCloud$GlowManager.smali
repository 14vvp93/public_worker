.class public Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;
.super Ljava/lang/Object;
.source "PointCloud.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "GlowManager"
.end annotation


# instance fields
.field private alpha:F

.field private radius:F

.field final synthetic this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

.field private x:F

.field private y:F


# direct methods
.method public constructor <init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;)V
    .locals 1
    .param p1, "this$0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    .line 66
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 69
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->radius:F

    .line 70
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->alpha:F

    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    .line 66
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->x:F

    return v0
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    .line 66
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->y:F

    return v0
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    .line 66
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->radius:F

    return v0
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    .line 66
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->alpha:F

    return v0
.end method


# virtual methods
.method public setRadius(F)V
    .locals 0
    .param p1, "r"    # F

    .line 97
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->radius:F

    .line 98
    return-void
.end method

.method public setX(F)V
    .locals 0
    .param p1, "x1"    # F

    .line 73
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->x:F

    .line 74
    return-void
.end method

.method public setY(F)V
    .locals 0
    .param p1, "y1"    # F

    .line 81
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->y:F

    .line 82
    return-void
.end method
