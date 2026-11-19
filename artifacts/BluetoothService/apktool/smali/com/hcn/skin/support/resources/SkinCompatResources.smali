.class public Lcom/hcn/skin/support/resources/SkinCompatResources;
.super Ljava/lang/Object;
.source "SkinCompatResources.java"

# interfaces
.implements Lcom/hcn/skin/share/ISkinCompatResources;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "SkinCompatResources"

.field private static volatile sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;


# instance fields
.field private mSkinContext:Landroid/content/Context;

.field private mSkinInflaterFactorySet:Z

.field private mSkinPkgName:Ljava/lang/String;

.field private mSkinResources:Landroid/content/res/Resources;


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 67
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 75
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    const/4 v1, 0x0

    .line 78
    iput-boolean v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    .line 83
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    const-string v0, ""

    .line 87
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    return-void
.end method

.method static synthetic access$000(Lcom/hcn/skin/support/resources/SkinCompatResources;)Landroid/content/res/Resources;
    .locals 0

    .line 67
    iget-object p0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    return-object p0
.end method

.method static synthetic access$100(Lcom/hcn/skin/support/resources/SkinCompatResources;)Ljava/lang/String;
    .locals 0

    .line 67
    iget-object p0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    return-object p0
.end method

.method private buildIdRules(Landroid/view/View;Landroid/util/SparseIntArray;)V
    .locals 5

    .line 247
    :try_start_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    const/4 v1, -0x1

    const-string v2, "SkinCompatResources"

    if-eq v0, v1, :cond_0

    :try_start_1
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->isViewIdGenerated(I)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v0, :cond_0

    .line 249
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v0

    .line 250
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 251
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const-string v4, "id"

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v3, v0, v4, v1}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    .line 252
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "----View class:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " name:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, " id:"

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    invoke-static {v3}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, " mappedId:"

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {v1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    if-lez v1, :cond_0

    .line 255
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    invoke-virtual {p2, v0, v1}, Landroid/util/SparseIntArray;->put(II)V

    .line 266
    :cond_0
    instance-of v0, p1, Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    .line 267
    move-object v0, p1

    check-cast v0, Landroid/view/ViewGroup;

    .line 268
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v1

    .line 269
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "(#"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ") "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {p1}, Landroid/view/View;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v3, " contain "

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v3, " child"

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v2, p1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    const/4 p1, 0x0

    :goto_0
    if-ge p1, v1, :cond_1

    .line 271
    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v2

    invoke-direct {p0, v2, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildIdRules(Landroid/view/View;Landroid/util/SparseIntArray;)V
    :try_end_1
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_1 .. :try_end_1} :catch_0

    add-int/lit8 p1, p1, 0x1

    goto :goto_0

    :catch_0
    move-exception p1

    .line 276
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    return-void
.end method

.method private buildLayoutParams(Landroid/view/View;I)V
    .locals 5

    .line 625
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    const-string v1, "SkinCompatResources"

    if-nez v0, :cond_0

    const-string p1, "buildLayoutParams return, mSkinResources is null "

    .line 626
    invoke-static {v1, p1}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void

    :cond_0
    if-nez p2, :cond_1

    return-void

    .line 635
    :cond_1
    :try_start_0
    instance-of v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout;

    if-eqz v0, :cond_4

    .line 636
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "buildLayoutParams with "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " layoutId:"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 637
    new-instance v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-direct {v0, v1}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;-><init>(Landroid/content/res/Resources;)V

    .line 639
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    if-lez p2, :cond_2

    .line 642
    invoke-virtual {v0, p2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->parserLayout(I)Ljava/util/Map;

    move-result-object v1

    .line 649
    :cond_2
    move-object p2, p1

    check-cast p2, Landroid/view/ViewGroup;

    invoke-virtual {p2}, Landroid/view/ViewGroup;->getChildCount()I

    move-result p2

    const/4 v2, 0x0

    :goto_0
    if-ge v2, p2, :cond_4

    .line 652
    move-object v3, p1

    check-cast v3, Landroid/view/ViewGroup;

    invoke-virtual {v3, v2}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v3

    .line 654
    invoke-interface {v1}, Ljava/util/Map;->size()I

    move-result v4

    if-lez v4, :cond_3

    .line 655
    invoke-virtual {v3}, Landroid/view/View;->getId()I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-interface {v1, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-eqz v4, :cond_3

    .line 656
    invoke-virtual {v3}, Landroid/view/View;->getId()I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-interface {v1, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    iget v4, v4, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    invoke-direct {p0, v3, v4}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildLayoutParams(Landroid/view/View;I)V

    .line 657
    invoke-virtual {v0, v3, v1}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->makeLayoutParams(Landroid/view/View;Ljava/util/Map;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :catch_0
    move-exception p1

    .line 663
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_4
    return-void
.end method

.method public static getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;
    .locals 2

    .line 97
    sget-object v0, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    if-nez v0, :cond_2

    .line 98
    const-class v0, Lcom/hcn/skin/support/resources/SkinCompatResources;

    monitor-enter v0

    .line 99
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinResourcesConfig;->getSkinResourcesClass()Ljava/lang/Class;

    move-result-object v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_0

    .line 102
    :try_start_1
    invoke-virtual {v1}, Ljava/lang/Class;->newInstance()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/support/resources/SkinCompatResources;

    sput-object v1, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;
    :try_end_1
    .catch Ljava/lang/InstantiationException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/lang/IllegalAccessException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_1

    :catch_0
    move-exception v1

    goto :goto_0

    :catch_1
    move-exception v1

    .line 104
    :goto_0
    :try_start_2
    invoke-virtual {v1}, Ljava/lang/ReflectiveOperationException;->printStackTrace()V

    goto :goto_1

    .line 107
    :cond_0
    sget-object v1, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    if-nez v1, :cond_1

    .line 108
    new-instance v1, Lcom/hcn/skin/support/resources/SkinCompatResources;

    invoke-direct {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;-><init>()V

    sput-object v1, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    .line 111
    :cond_1
    :goto_1
    monitor-exit v0

    goto :goto_2

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v1

    .line 113
    :cond_2
    :goto_2
    sget-object v0, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    return-object v0
.end method

.method private isViewIdGenerated(I)Z
    .locals 1

    const/high16 v0, -0x1000000

    and-int/2addr v0, p1

    if-nez v0, :cond_0

    const v0, 0xffffff

    and-int/2addr p1, v0

    if-eqz p1, :cond_0

    const/4 p1, 0x1

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    return p1
.end method


# virtual methods
.method protected buildInflateRules(Landroid/view/View;Landroid/util/SparseIntArray;)V
    .locals 6

    .line 281
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    if-nez p1, :cond_0

    return-void

    .line 285
    :cond_0
    instance-of v0, p1, Landroid/widget/RelativeLayout$LayoutParams;

    if-eqz v0, :cond_3

    .line 286
    check-cast p1, Landroid/widget/RelativeLayout$LayoutParams;

    invoke-virtual {p1}, Landroid/widget/RelativeLayout$LayoutParams;->getRules()[I

    move-result-object p1

    if-nez p1, :cond_1

    return-void

    .line 290
    :cond_1
    array-length v0, p1

    .line 292
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Rules length "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "SkinCompatResources"

    invoke-static {v2, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_3

    .line 295
    aget v3, p1, v1

    if-lez v3, :cond_2

    aget v3, p1, v1

    invoke-virtual {p2, v3}, Landroid/util/SparseIntArray;->get(I)I

    move-result v3

    if-lez v3, :cond_2

    .line 296
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Rules["

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "]: Mapped from: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    aget v5, p1, v1

    invoke-static {v5}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "  to  "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-static {v3}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 297
    aput v3, p1, v1

    :cond_2
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_3
    return-void
.end method

.method public checkIfNeedBuildLayoutParams(Landroid/view/View;I)V
    .locals 4

    .line 668
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-nez v0, :cond_0

    return-void

    .line 671
    :cond_0
    instance-of v0, p1, Landroid/view/ViewGroup;

    if-eqz v0, :cond_2

    .line 672
    move-object v0, p1

    check-cast v0, Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v1

    .line 673
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "(#"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    invoke-static {v3}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ") "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p1}, Landroid/view/View;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, " contain "

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, " child"

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v2, "SkinCompatResources"

    invoke-static {v2, p1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    if-gtz v1, :cond_1

    return-void

    :cond_1
    const/4 p1, 0x0

    .line 677
    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object p1

    .line 678
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_2

    .line 679
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "View class:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " id:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " name:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    invoke-virtual {v1, v3}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 680
    instance-of v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout;

    if-eqz v0, :cond_2

    const-string v0, "view is ConstraintLayout"

    .line 681
    invoke-static {v2, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 682
    invoke-direct {p0, p1, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildLayoutParams(Landroid/view/View;I)V

    :cond_2
    return-void
.end method

.method public getAppResId(Ljava/lang/String;Ljava/lang/String;)I
    .locals 4

    const/4 v0, 0x0

    .line 443
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 444
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 445
    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    .line 446
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_0

    if-eqz v2, :cond_0

    .line 447
    invoke-virtual {v2, p1, p2, v1}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    :cond_0
    return v0
.end method

.method public getBitmap(I)Landroid/graphics/Bitmap;
    .locals 1

    .line 931
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 933
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-static {p1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    return-object p1

    .line 935
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 936
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-static {v0, p1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 938
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getBitmap(Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 2

    const-string v0, "drawable"

    .line 951
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 953
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-static {p1, v1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    return-object p1

    .line 955
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 957
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 958
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-static {v0, p1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 961
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getBoolean(I)Z
    .locals 1

    :try_start_0
    const-string v0, "bool"

    .line 1060
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(ILjava/lang/String;)I

    move-result v0

    if-lez v0, :cond_0

    .line 1062
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result p1

    return p1

    .line 1064
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 1066
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return p1
.end method

.method public getBoolean(Ljava/lang/String;)Z
    .locals 2

    const-string v0, "bool"

    .line 1080
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 1082
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result p1

    return p1

    .line 1084
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 1086
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 1087
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 1090
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method public getColor(I)I
    .locals 2

    :try_start_0
    const-string v0, "color"

    .line 799
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(ILjava/lang/String;)I

    move-result v0

    if-lez v0, :cond_0

    .line 801
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v1

    invoke-virtual {p1, v0, v1}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1

    return p1

    .line 803
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 804
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 806
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return p1
.end method

.method public getColor(Ljava/lang/String;)I
    .locals 2

    const-string v0, "color"

    .line 820
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 822
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {p1, v1, v0}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1

    return p1

    .line 824
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 826
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 827
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 830
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method public getColorStateList(I)Landroid/content/res/ColorStateList;
    .locals 2

    .line 844
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 846
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v1

    invoke-virtual {p1, v0, v1}, Landroid/content/res/Resources;->getColorStateList(ILandroid/content/res/Resources$Theme;)Landroid/content/res/ColorStateList;

    move-result-object p1

    return-object p1

    .line 848
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 849
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getColorStateList(ILandroid/content/res/Resources$Theme;)Landroid/content/res/ColorStateList;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 851
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getColorStateList(Ljava/lang/String;)Landroid/content/res/ColorStateList;
    .locals 2

    const-string v0, "color"

    .line 865
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 867
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {p1, v1, v0}, Landroid/content/res/Resources;->getColorStateList(ILandroid/content/res/Resources$Theme;)Landroid/content/res/ColorStateList;

    move-result-object p1

    return-object p1

    .line 869
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 871
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 872
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getColorStateList(ILandroid/content/res/Resources$Theme;)Landroid/content/res/ColorStateList;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 875
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getContext()Landroid/content/Context;
    .locals 1

    .line 195
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v0, :cond_0

    .line 196
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    return-object v0

    .line 198
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    return-object v0
.end method

.method public getDimension(I)F
    .locals 1

    :try_start_0
    const-string v0, "dimen"

    .line 1017
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(ILjava/lang/String;)I

    move-result v0

    if-lez v0, :cond_0

    .line 1019
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1

    return p1

    .line 1021
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 1023
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return p1
.end method

.method public getDimension(Ljava/lang/String;)F
    .locals 2

    const-string v0, "dimen"

    .line 1036
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 1038
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1

    return p1

    .line 1040
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 1042
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 1043
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 1046
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method public getDrawable(I)Landroid/graphics/drawable/Drawable;
    .locals 2

    :try_start_0
    const-string v0, "drawable"

    .line 888
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(ILjava/lang/String;)I

    move-result v0

    if-lez v0, :cond_0

    .line 890
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v1

    invoke-virtual {p1, v0, v1}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1

    .line 892
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 893
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 895
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
    .locals 2

    const-string v0, "drawable"

    .line 908
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 910
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {p1, v1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1

    .line 912
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 914
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 915
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 918
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getId(I)I
    .locals 4

    .line 464
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 465
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v1

    .line 466
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v0

    .line 468
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v2, :cond_0

    .line 469
    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v2, v0, v1, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    if-lez v0, :cond_0

    return v0

    :cond_0
    return p1

    :catch_0
    move-exception p1

    .line 480
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    const/4 p1, 0x0

    return p1
.end method

.method public getIdentifier(Ljava/lang/String;Ljava/lang/String;)I
    .locals 3

    .line 403
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    .line 407
    :cond_0
    :try_start_0
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v0, :cond_1

    .line 408
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v0, p1, p2, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    return p1

    .line 410
    :cond_1
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 411
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, p1, p2, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 414
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    return v1
.end method

.method public getIntArray(I)[I
    .locals 1

    .line 787
    new-instance p1, Lkotlin/NotImplementedError;

    const-string v0, "function \'getIntArray\' not implemented!"

    invoke-direct {p1, v0}, Lkotlin/NotImplementedError;-><init>(Ljava/lang/String;)V

    throw p1
.end method

.method public getInteger(I)I
    .locals 1

    .line 974
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 976
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1

    return p1

    .line 978
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 980
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return p1
.end method

.method public getInteger(Ljava/lang/String;)I
    .locals 2

    const-string v0, "integer"

    .line 994
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 996
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1

    return p1

    .line 998
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 1000
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 1001
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 1004
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method public getLayout(I)Landroid/view/View;
    .locals 1

    const/4 v0, 0x0

    .line 553
    invoke-virtual {p0, p1, v0, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getLayout(ILandroid/view/ViewGroup;Landroid/view/LayoutInflater$Factory2;)Landroid/view/View;

    move-result-object p1

    return-object p1
.end method

.method public getLayout(ILandroid/view/ViewGroup;Landroid/view/LayoutInflater$Factory2;)Landroid/view/View;
    .locals 5

    .line 560
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 561
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_1

    .line 562
    invoke-virtual {p0, v0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Landroid/content/Context;I)I

    move-result v1

    if-lez v1, :cond_1

    const-string v2, "SkinCompatResources"

    .line 564
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "getLayout skin "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-static {v1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 565
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v2

    .line 566
    iget-boolean v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    if-nez v3, :cond_0

    if-eqz p3, :cond_0

    const/4 v3, 0x1

    .line 568
    iput-boolean v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    .line 569
    invoke-virtual {v2, p3}, Landroid/view/LayoutInflater;->setFactory2(Landroid/view/LayoutInflater$Factory2;)V

    .line 571
    :cond_0
    invoke-virtual {v2, v1, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    :cond_1
    if-eqz p1, :cond_2

    .line 575
    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p3

    invoke-virtual {p3, p1, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 578
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_2
    const/4 p1, 0x0

    return-object p1
.end method

.method public getLayout(Landroid/content/Context;ILandroid/view/LayoutInflater$Factory2;)Landroid/view/View;
    .locals 3

    .line 528
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    const-string v1, "SkinCompatResources"

    const/4 v2, 0x0

    if-eqz v0, :cond_2

    .line 530
    :try_start_0
    invoke-virtual {p0, p1, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Landroid/content/Context;I)I

    move-result p1

    if-lez p1, :cond_1

    .line 532
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "getLayout skin "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-static {p1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v0, " "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v1, p2}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 533
    iget-object p2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    .line 534
    iget-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    if-nez v0, :cond_0

    if-eqz p3, :cond_0

    const/4 v0, 0x1

    .line 536
    iput-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    .line 537
    invoke-virtual {p2, p3}, Landroid/view/LayoutInflater;->setFactory2(Landroid/view/LayoutInflater$Factory2;)V

    .line 540
    :cond_0
    invoke-virtual {p2, p1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :cond_1
    return-object v2

    :catch_0
    move-exception p1

    .line 545
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_2
    const-string p1, "getLayout mSkinResources == null "

    .line 548
    invoke-static {v1, p1}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-object v2
.end method

.method public getLayout(Ljava/lang/String;)Landroid/view/View;
    .locals 1

    const/4 v0, 0x0

    .line 584
    invoke-virtual {p0, p1, v0, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getLayout(Ljava/lang/String;Landroid/view/ViewGroup;Landroid/view/LayoutInflater$Factory2;)Landroid/view/View;

    move-result-object p1

    return-object p1
.end method

.method public getLayout(Ljava/lang/String;Landroid/view/ViewGroup;Landroid/view/LayoutInflater$Factory2;)Landroid/view/View;
    .locals 4

    .line 592
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 593
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const-string v2, "layout"

    if-eqz v1, :cond_1

    .line 594
    :try_start_1
    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-eqz v1, :cond_1

    .line 596
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-static {p1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p1

    .line 597
    iget-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    if-nez v0, :cond_0

    if-eqz p3, :cond_0

    const/4 v0, 0x1

    .line 599
    iput-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    .line 600
    invoke-virtual {p1, p3}, Landroid/view/LayoutInflater;->setFactory2(Landroid/view/LayoutInflater$Factory2;)V

    .line 602
    :cond_0
    invoke-virtual {p1, v1, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1

    return-object p1

    .line 606
    :cond_1
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p3

    .line 607
    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p3, p1, v2, v1}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_2

    .line 609
    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p3

    invoke-virtual {p3, p1, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 612
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_2
    const/4 p1, 0x0

    return-object p1
.end method

.method public getSkinContext()Landroid/content/Context;
    .locals 1

    .line 160
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    return-object v0
.end method

.method public getSkinPkgName()Ljava/lang/String;
    .locals 1

    .line 219
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    return-object v0
.end method

.method public getSkinResId(I)I
    .locals 4

    const/4 v0, 0x0

    .line 334
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 335
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v2

    .line 336
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object p1

    .line 338
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_0

    .line 339
    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    :cond_0
    return v0
.end method

.method public getSkinResId(ILjava/lang/String;)I
    .locals 3

    const/4 v0, 0x0

    .line 359
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 360
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object p1

    .line 362
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_0

    .line 363
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, p1, p2, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    :cond_0
    return v0
.end method

.method public getSkinResId(Landroid/content/Context;I)I
    .locals 3

    const/4 v0, 0x0

    .line 313
    :try_start_0
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p2}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v1

    .line 314
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object p1

    .line 316
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-nez p2, :cond_0

    iget-object p2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_0

    .line 317
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    :cond_0
    return v0
.end method

.method public getSkinResId(Ljava/lang/String;Ljava/lang/String;)I
    .locals 3

    const/4 v0, 0x0

    .line 384
    :try_start_0
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_0

    .line 385
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, p1, p2, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    :cond_0
    return v0
.end method

.method public getSkinResources()Landroid/content/res/Resources;
    .locals 1

    .line 209
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getString(I)Ljava/lang/String;
    .locals 1

    .line 749
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 751
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    .line 753
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 755
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getString(IZ)Ljava/lang/String;
    .locals 0

    .line 738
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public getString(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    const-string v0, "string"

    .line 768
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 770
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    .line 772
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 774
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 777
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getStringArray(I)[Ljava/lang/String;
    .locals 1

    .line 783
    new-instance p1, Lkotlin/NotImplementedError;

    const-string v0, "function \'getStringArray\' not implemented!"

    invoke-direct {p1, v0}, Lkotlin/NotImplementedError;-><init>(Ljava/lang/String;)V

    throw p1
.end method

.method public getTargetResIdStrictly(I)I
    .locals 3

    const/4 v0, 0x0

    .line 421
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 422
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceName(I)Ljava/lang/String;

    move-result-object p1

    .line 423
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_0

    const/4 v2, 0x0

    .line 424
    invoke-virtual {v1, p1, v2, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    :cond_0
    return v0
.end method

.method public getText(I)Ljava/lang/CharSequence;
    .locals 1

    .line 696
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 698
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    return-object p1

    .line 700
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 702
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getText(Ljava/lang/String;)Ljava/lang/CharSequence;
    .locals 2

    const-string v0, "string"

    .line 715
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 717
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    return-object p1

    .line 719
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 721
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 724
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getViewId(Landroid/view/View;)I
    .locals 4

    .line 492
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    if-gtz p1, :cond_0

    return p1

    .line 497
    :cond_0
    :try_start_0
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v0, :cond_1

    .line 498
    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "id"

    .line 502
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v2, v0, v1, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    if-lez v2, :cond_1

    .line 504
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v2

    invoke-virtual {v2}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v2

    .line 505
    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v2}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v0, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :cond_1
    return p1

    :catch_0
    move-exception v0

    .line 513
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    return p1
.end method

.method public getXml(I)Landroid/content/res/XmlResourceParser;
    .locals 1

    .line 1103
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getTargetResIdStrictly(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 1105
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    return-object p1

    .line 1107
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 1109
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;
    .locals 3

    const-string v0, "xml"

    .line 1121
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getXml name:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " mSkinPkgName:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "SkinCompatResources"

    invoke-static {v2, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 1123
    :try_start_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lez v1, :cond_0

    .line 1125
    iget-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    return-object p1

    .line 1127
    :cond_0
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 1129
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 1132
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public handleView(Landroid/view/View;)V
    .locals 1

    .line 229
    new-instance v0, Landroid/util/SparseIntArray;

    invoke-direct {v0}, Landroid/util/SparseIntArray;-><init>()V

    .line 230
    invoke-direct {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildIdRules(Landroid/view/View;Landroid/util/SparseIntArray;)V

    return-void
.end method

.method public resetSkin()V
    .locals 2

    const-string v0, "SkinCompatResources"

    const-string v1, "resetSkin"

    .line 147
    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    const/4 v0, 0x0

    .line 148
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    .line 149
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    const-string v0, ""

    .line 150
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    return-void
.end method

.method public setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V
    .locals 2

    if-eqz p2, :cond_1

    .line 127
    invoke-static {p3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 130
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setupSkin "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatResources"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 132
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    .line 133
    iput-object p2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    .line 134
    iput-object p3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    .line 135
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object p1

    .line 137
    iget-object p2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    if-nez p2, :cond_1

    const-string p2, "new SkinThemeContext"

    .line 138
    invoke-static {v1, p2}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 139
    new-instance p2, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;

    invoke-direct {p2, p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;-><init>(Lcom/hcn/skin/support/resources/SkinCompatResources;Landroid/content/Context;)V

    iput-object p2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    :cond_1
    :goto_0
    return-void
.end method

.method public setupSkin(Landroid/content/res/Resources;Ljava/lang/String;)V
    .locals 1

    const/4 v0, 0x0

    .line 123
    invoke-virtual {p0, v0, p1, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    return-void
.end method

.method public skinInflaterFactorySet(Landroid/view/LayoutInflater$Factory2;)V
    .locals 1

    .line 179
    iget-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    if-eqz v0, :cond_0

    const-string p1, "SkinCompatResources"

    const-string v0, "A factory has already been set on skin LayoutInflater!"

    .line 180
    invoke-static {p1, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    const/4 v0, 0x1

    .line 184
    iput-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    .line 185
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/view/LayoutInflater;->setFactory2(Landroid/view/LayoutInflater$Factory2;)V

    return-void
.end method

.method public skinInflaterFactorySet()Z
    .locals 1

    .line 169
    iget-boolean v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinInflaterFactorySet:Z

    return v0
.end method
