.class public Lcom/hcn/auto/systemui/IconFactory;
.super Lcom/hcn/auto/systemui/BaseIconFactory;
.source "IconFactory.java"


# static fields
.field private static sPool:Lcom/hcn/auto/systemui/IconFactory;

.field private static sPoolId:I

.field private static final sPoolSync:Ljava/lang/Object;


# instance fields
.field private final mPoolId:I

.field private next:Lcom/hcn/auto/systemui/IconFactory;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 5
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lcom/hcn/auto/systemui/IconFactory;->sPoolSync:Ljava/lang/Object;

    .line 7
    const/4 v0, 0x0

    sput v0, Lcom/hcn/auto/systemui/IconFactory;->sPoolId:I

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;III)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "fillResIconDpi"    # I
    .param p3, "iconBitmapSize"    # I
    .param p4, "poolId"    # I

    .line 35
    invoke-direct {p0, p1, p2, p3}, Lcom/hcn/auto/systemui/BaseIconFactory;-><init>(Landroid/content/Context;II)V

    .line 36
    iput p4, p0, Lcom/hcn/auto/systemui/IconFactory;->mPoolId:I

    .line 37
    return-void
.end method

.method public static obtain(Landroid/content/Context;I)Lcom/hcn/auto/systemui/IconFactory;
    .locals 3
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "iconBitmapSize"    # I

    .line 13
    sget-object v0, Lcom/hcn/auto/systemui/IconFactory;->sPoolSync:Ljava/lang/Object;

    monitor-enter v0

    .line 14
    :try_start_0
    sget-object v1, Lcom/hcn/auto/systemui/IconFactory;->sPool:Lcom/hcn/auto/systemui/IconFactory;

    if-eqz v1, :cond_0

    .line 15
    sget-object v1, Lcom/hcn/auto/systemui/IconFactory;->sPool:Lcom/hcn/auto/systemui/IconFactory;

    .line 16
    .local v1, "m":Lcom/hcn/auto/systemui/IconFactory;
    iget-object v2, v1, Lcom/hcn/auto/systemui/IconFactory;->next:Lcom/hcn/auto/systemui/IconFactory;

    sput-object v2, Lcom/hcn/auto/systemui/IconFactory;->sPool:Lcom/hcn/auto/systemui/IconFactory;

    .line 17
    const/4 v2, 0x0

    iput-object v2, v1, Lcom/hcn/auto/systemui/IconFactory;->next:Lcom/hcn/auto/systemui/IconFactory;

    .line 18
    monitor-exit v0

    return-object v1

    .line 21
    .end local v1    # "m":Lcom/hcn/auto/systemui/IconFactory;
    :cond_0
    sget v1, Lcom/hcn/auto/systemui/IconFactory;->sPoolId:I

    .line 22
    .local v1, "poolId":I
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 24
    new-instance v0, Lcom/hcn/auto/systemui/IconFactory;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v2

    iget v2, v2, Landroid/content/res/Configuration;->densityDpi:I

    invoke-direct {v0, p0, v2, p1, v1}, Lcom/hcn/auto/systemui/IconFactory;-><init>(Landroid/content/Context;III)V

    return-object v0

    .line 22
    .end local v1    # "poolId":I
    :catchall_0
    move-exception v1

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v1
.end method


# virtual methods
.method public close()V
    .locals 0

    .line 50
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/IconFactory;->recycle()V

    .line 51
    return-void
.end method

.method public recycle()V
    .locals 3

    .line 40
    sget-object v0, Lcom/hcn/auto/systemui/IconFactory;->sPoolSync:Ljava/lang/Object;

    monitor-enter v0

    .line 41
    :try_start_0
    sget v1, Lcom/hcn/auto/systemui/IconFactory;->sPoolId:I

    iget v2, p0, Lcom/hcn/auto/systemui/IconFactory;->mPoolId:I

    if-ne v1, v2, :cond_0

    .line 42
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/IconFactory;->clear()V

    .line 43
    sget-object v1, Lcom/hcn/auto/systemui/IconFactory;->sPool:Lcom/hcn/auto/systemui/IconFactory;

    iput-object v1, p0, Lcom/hcn/auto/systemui/IconFactory;->next:Lcom/hcn/auto/systemui/IconFactory;

    .line 44
    sput-object p0, Lcom/hcn/auto/systemui/IconFactory;->sPool:Lcom/hcn/auto/systemui/IconFactory;

    .line 46
    :cond_0
    monitor-exit v0

    .line 47
    return-void

    .line 46
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method
