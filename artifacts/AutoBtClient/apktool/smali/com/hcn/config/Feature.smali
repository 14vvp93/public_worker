.class public Lcom/hcn/config/Feature;
.super Ljava/lang/Object;
.source "Feature.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/config/Feature$BIT;
    }
.end annotation


# static fields
.field private static S_INSTANCE:Lcom/hcn/config/Feature;


# instance fields
.field private mFeatureMark:J


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>()V
    .locals 2

    .line 31
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-wide/16 v0, 0x0

    .line 13
    iput-wide v0, p0, Lcom/hcn/config/Feature;->mFeatureMark:J

    .line 33
    sget-object v0, Lcom/hcn/config/Customer;->FOTA_FEATURE:Ljava/lang/String;

    const-string v1, "AJ1501-OB"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    .line 34
    invoke-virtual {p0, v1}, Lcom/hcn/config/Feature;->addFeature(I)V

    .line 38
    :cond_0
    sget v0, Lcom/hcn/config/Customer;->NAVIGATION_BAR_HEIGHT:I

    if-le v0, v1, :cond_1

    sget-object v0, Lcom/hcn/config/Customer;->FOTA_FEATURE:Ljava/lang/String;

    const-string v1, "ZA00"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_1

    const/4 v0, 0x4

    .line 39
    invoke-virtual {p0, v0}, Lcom/hcn/config/Feature;->addFeature(I)V

    .line 43
    :cond_1
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x21

    if-lt v0, v1, :cond_2

    const/16 v0, 0x20

    .line 44
    invoke-virtual {p0, v0}, Lcom/hcn/config/Feature;->addFeature(I)V

    :cond_2
    const/16 v0, 0x10

    .line 48
    invoke-virtual {p0, v0}, Lcom/hcn/config/Feature;->addFeature(I)V

    return-void
.end method

.method public static instance()Lcom/hcn/config/Feature;
    .locals 2

    .line 19
    sget-object v0, Lcom/hcn/config/Feature;->S_INSTANCE:Lcom/hcn/config/Feature;

    if-eqz v0, :cond_0

    return-object v0

    .line 22
    :cond_0
    const-class v0, Lcom/hcn/config/Feature;

    monitor-enter v0

    .line 23
    :try_start_0
    sget-object v1, Lcom/hcn/config/Feature;->S_INSTANCE:Lcom/hcn/config/Feature;

    if-nez v1, :cond_1

    .line 24
    new-instance v1, Lcom/hcn/config/Feature;

    invoke-direct {v1}, Lcom/hcn/config/Feature;-><init>()V

    sput-object v1, Lcom/hcn/config/Feature;->S_INSTANCE:Lcom/hcn/config/Feature;

    .line 26
    :cond_1
    sget-object v1, Lcom/hcn/config/Feature;->S_INSTANCE:Lcom/hcn/config/Feature;

    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    .line 27
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method


# virtual methods
.method public addFeature(I)V
    .locals 4

    .line 77
    iget-wide v0, p0, Lcom/hcn/config/Feature;->mFeatureMark:J

    int-to-long v2, p1

    or-long/2addr v0, v2

    iput-wide v0, p0, Lcom/hcn/config/Feature;->mFeatureMark:J

    return-void
.end method

.method public hasFeature(I)Z
    .locals 4

    .line 68
    iget-wide v0, p0, Lcom/hcn/config/Feature;->mFeatureMark:J

    int-to-long v2, p1

    and-long/2addr v0, v2

    cmp-long p1, v0, v2

    if-nez p1, :cond_0

    const/4 p1, 0x1

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    return p1
.end method

.method public removeFeature(I)V
    .locals 4

    .line 86
    iget-wide v0, p0, Lcom/hcn/config/Feature;->mFeatureMark:J

    not-int p1, p1

    int-to-long v2, p1

    and-long/2addr v0, v2

    iput-wide v0, p0, Lcom/hcn/config/Feature;->mFeatureMark:J

    return-void
.end method
