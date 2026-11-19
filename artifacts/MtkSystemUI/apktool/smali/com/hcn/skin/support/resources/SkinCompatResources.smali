.class public Lcom/hcn/skin/support/resources/SkinCompatResources;
.super Ljava/lang/Object;
.source "SkinCompatResources.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;
    }
.end annotation


# static fields
.field private static volatile sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;


# instance fields
.field private mSkinContext:Landroid/content/Context;

.field private mSkinPkgName:Ljava/lang/String;

.field private mSkinResources:Landroid/content/res/Resources;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 57
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 65
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    .line 69
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    .line 73
    const-string v0, ""

    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    return-void
.end method

.method static synthetic access$000(Lcom/hcn/skin/support/resources/SkinCompatResources;)Landroid/content/res/Resources;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/skin/support/resources/SkinCompatResources;

    .line 57
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method private buildIdRules(Landroid/view/View;Landroid/util/SparseIntArray;)V
    .locals 6
    .param p1, "v"    # Landroid/view/View;
    .param p2, "array"    # Landroid/util/SparseIntArray;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "ResourceType"
        }
    .end annotation

    .line 204
    :try_start_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->isViewIdGenerated(I)Z

    move-result v0

    if-nez v0, :cond_0

    .line 206
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v0

    .line 207
    .local v0, "idName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 208
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const-string v3, "id"

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v2, v0, v3, v4}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 209
    .local v2, "mappedId":I
    const-string v3, "SkinCompatResources"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "----View class:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, " name:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, " id:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v5

    invoke-static {v5}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, " mappedId:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v2}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 211
    if-lez v2, :cond_0

    .line 212
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    invoke-virtual {p2, v3, v2}, Landroid/util/SparseIntArray;->put(II)V

    .line 223
    .end local v0    # "idName":Ljava/lang/String;
    .end local v1    # "context":Landroid/content/Context;
    .end local v2    # "mappedId":I
    :cond_0
    instance-of v0, p1, Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    .line 224
    move-object v0, p1

    check-cast v0, Landroid/view/ViewGroup;

    .line 225
    .local v0, "vp":Landroid/view/ViewGroup;
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v1

    .line 226
    .local v1, "childCount":I
    const-string v2, "SkinCompatResources"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, "(#"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, ") "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/View;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, " contain "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " child"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 227
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_0
    if-ge v2, v1, :cond_1

    .line 228
    invoke-virtual {v0, v2}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v3

    invoke-direct {p0, v3, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildIdRules(Landroid/view/View;Landroid/util/SparseIntArray;)V
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 227
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 234
    .end local v0    # "vp":Landroid/view/ViewGroup;
    .end local v1    # "childCount":I
    .end local v2    # "i":I
    :cond_1
    goto :goto_1

    .line 232
    :catch_0
    move-exception v0

    .line 233
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 235
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    :goto_1
    return-void
.end method

.method private buildLayoutParams(Landroid/view/View;I)V
    .locals 6
    .param p1, "target"    # Landroid/view/View;
    .param p2, "layoutId"    # I

    .line 544
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-nez v0, :cond_0

    .line 545
    const-string v0, "SkinCompatResources"

    const-string v1, "buildLayoutParams return, mSkinResources is null "

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 546
    return-void

    .line 548
    :cond_0
    if-nez p2, :cond_1

    .line 550
    return-void

    .line 554
    :cond_1
    :try_start_0
    instance-of v0, p1, Landroid/support/constraint/ConstraintLayout;

    if-eqz v0, :cond_4

    .line 555
    const-string v0, "SkinCompatResources"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "buildLayoutParams with "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, " layoutId:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 556
    new-instance v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-direct {v0, v1}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;-><init>(Landroid/content/res/Resources;)V

    .line 558
    .local v0, "helper":Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    .line 559
    .local v1, "params":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/Integer;Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;>;"
    if-lez p2, :cond_2

    .line 561
    invoke-virtual {v0, p2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->parserLayout(I)Ljava/util/Map;

    move-result-object v2

    move-object v1, v2

    .line 568
    :cond_2
    move-object v2, p1

    check-cast v2, Landroid/view/ViewGroup;

    invoke-virtual {v2}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v2

    .line 570
    .local v2, "count":I
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v2, :cond_4

    .line 571
    move-object v4, p1

    check-cast v4, Landroid/view/ViewGroup;

    invoke-virtual {v4, v3}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v4

    .line 573
    .local v4, "child":Landroid/view/View;
    invoke-interface {v1}, Ljava/util/Map;->size()I

    move-result v5

    if-lez v5, :cond_3

    .line 574
    invoke-virtual {v4}, Landroid/view/View;->getId()I

    move-result v5

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-interface {v1, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_3

    .line 575
    invoke-virtual {v4}, Landroid/view/View;->getId()I

    move-result v5

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-interface {v1, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    iget v5, v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    invoke-direct {p0, v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildLayoutParams(Landroid/view/View;I)V

    .line 576
    invoke-virtual {v0, v4, v1}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->makeLayoutParams(Landroid/view/View;Ljava/util/Map;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 570
    .end local v4    # "child":Landroid/view/View;
    :cond_3
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 583
    .end local v0    # "helper":Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;
    .end local v1    # "params":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/Integer;Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;>;"
    .end local v2    # "count":I
    .end local v3    # "i":I
    :cond_4
    goto :goto_1

    .line 581
    :catch_0
    move-exception v0

    .line 582
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 584
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_1
    return-void
.end method

.method public static getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;
    .locals 2

    .line 81
    sget-object v0, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    if-nez v0, :cond_1

    .line 82
    const-class v0, Lcom/hcn/skin/support/resources/SkinCompatResources;

    monitor-enter v0

    .line 83
    :try_start_0
    sget-object v1, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    if-nez v1, :cond_0

    .line 84
    new-instance v1, Lcom/hcn/skin/support/resources/SkinCompatResources;

    invoke-direct {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;-><init>()V

    sput-object v1, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    .line 86
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    .line 88
    :cond_1
    :goto_0
    sget-object v0, Lcom/hcn/skin/support/resources/SkinCompatResources;->sInstance:Lcom/hcn/skin/support/resources/SkinCompatResources;

    return-object v0
.end method

.method private isViewIdGenerated(I)Z
    .locals 1
    .param p1, "id"    # I

    .line 192
    const/high16 v0, -0x1000000

    and-int/2addr v0, p1

    if-nez v0, :cond_0

    const v0, 0xffffff

    and-int/2addr v0, p1

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method


# virtual methods
.method public getAppResId(Ljava/lang/String;Ljava/lang/String;)I
    .locals 5
    .param p1, "name"    # Ljava/lang/String;
    .param p2, "type"    # Ljava/lang/String;

    .line 373
    const/4 v0, 0x0

    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 374
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 375
    .local v2, "resources":Landroid/content/res/Resources;
    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v3

    .line 376
    .local v3, "packageName":Ljava/lang/String;
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_0

    if-eqz v2, :cond_0

    .line 377
    invoke-virtual {v2, p1, p2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v4
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v0, v4

    .line 378
    .local v0, "targetId":I
    return v0

    .line 382
    .end local v0    # "targetId":I
    .end local v1    # "context":Landroid/content/Context;
    .end local v2    # "resources":Landroid/content/res/Resources;
    .end local v3    # "packageName":Ljava/lang/String;
    :cond_0
    nop

    .line 383
    return v0

    .line 380
    :catch_0
    move-exception v1

    .line 381
    .local v1, "e":Ljava/lang/Exception;
    return v0
.end method

.method public getBitmap(Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 3
    .param p1, "name"    # Ljava/lang/String;

    .line 850
    :try_start_0
    const-string v0, "drawable"

    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 851
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 852
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-static {v1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    return-object v1

    .line 854
    :cond_0
    const-string v1, "drawable"

    invoke-virtual {p0, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    move v0, v1

    .line 855
    if-lez v0, :cond_1

    .line 856
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 857
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-static {v2, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v2
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v2

    .line 861
    .end local v0    # "realId":I
    .end local v1    # "context":Landroid/content/Context;
    :cond_1
    goto :goto_0

    .line 859
    :catch_0
    move-exception v0

    .line 860
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 862
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public getColor(Ljava/lang/String;)I
    .locals 4
    .param p1, "name"    # Ljava/lang/String;

    .line 719
    :try_start_0
    const-string v0, "color"

    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 720
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 721
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v2

    invoke-virtual {v1, v0, v2}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v1

    return v1

    .line 723
    :cond_0
    const-string v1, "color"

    invoke-virtual {p0, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    move v0, v1

    .line 724
    if-lez v0, :cond_1

    .line 725
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 726
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v2, v0, v3}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v2
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return v2

    .line 730
    .end local v0    # "realId":I
    .end local v1    # "context":Landroid/content/Context;
    :cond_1
    goto :goto_0

    .line 728
    :catch_0
    move-exception v0

    .line 729
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 731
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    :goto_0
    const/4 v0, 0x0

    return v0
.end method

.method public getDrawable(I)Landroid/graphics/drawable/Drawable;
    .locals 4
    .param p1, "resId"    # I

    .line 787
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    .line 788
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 789
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v2

    invoke-virtual {v1, v0, v2}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    return-object v1

    .line 791
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 792
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v2, p1, v3}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v2

    .line 793
    .end local v0    # "realId":I
    .end local v1    # "context":Landroid/content/Context;
    :catch_0
    move-exception v0

    .line 794
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 796
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    const/4 v0, 0x0

    return-object v0
.end method

.method public getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
    .locals 4
    .param p1, "name"    # Ljava/lang/String;

    .line 807
    :try_start_0
    const-string v0, "drawable"

    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 808
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 809
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v2

    invoke-virtual {v1, v0, v2}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    return-object v1

    .line 811
    :cond_0
    const-string v1, "drawable"

    invoke-virtual {p0, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    move v0, v1

    .line 812
    if-lez v0, :cond_1

    .line 813
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 814
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v2, v0, v3}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v2

    .line 818
    .end local v0    # "realId":I
    .end local v1    # "context":Landroid/content/Context;
    :cond_1
    goto :goto_0

    .line 816
    :catch_0
    move-exception v0

    .line 817
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 819
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public getId(I)I
    .locals 5
    .param p1, "resId"    # I

    .line 394
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 395
    .local v0, "context":Landroid/content/Context;
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v1

    .line 396
    .local v1, "type":Ljava/lang/String;
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v2

    .line 398
    .local v2, "name":Ljava/lang/String;
    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v3, :cond_1

    .line 399
    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v4, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v3, v2, v1, v4}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 401
    .local v3, "targetId":I
    if-lez v3, :cond_0

    .line 402
    return v3

    .line 404
    :cond_0
    return p1

    .line 407
    .end local v3    # "targetId":I
    :cond_1
    return p1

    .line 409
    .end local v0    # "context":Landroid/content/Context;
    .end local v1    # "type":Ljava/lang/String;
    .end local v2    # "name":Ljava/lang/String;
    :catch_0
    move-exception v0

    .line 410
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 412
    .end local v0    # "e":Ljava/lang/Exception;
    const/4 v0, 0x0

    return v0
.end method

.method public getInteger(Ljava/lang/String;)I
    .locals 3
    .param p1, "name"    # Ljava/lang/String;

    .line 893
    :try_start_0
    const-string v0, "integer"

    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 894
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 895
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v1

    return v1

    .line 897
    :cond_0
    const-string v1, "integer"

    invoke-virtual {p0, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    move v0, v1

    .line 898
    if-lez v0, :cond_1

    .line 899
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 900
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v2
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return v2

    .line 904
    .end local v0    # "realId":I
    .end local v1    # "context":Landroid/content/Context;
    :cond_1
    goto :goto_0

    .line 902
    :catch_0
    move-exception v0

    .line 903
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 905
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    :goto_0
    const/4 v0, 0x0

    return v0
.end method

.method public getLayout(Ljava/lang/String;)Landroid/view/View;
    .locals 6
    .param p1, "name"    # Ljava/lang/String;

    .line 512
    const/4 v0, 0x0

    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 513
    .local v1, "context":Landroid/content/Context;
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v2, :cond_1

    .line 514
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    const-string v3, "layout"

    iget-object v4, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v2, p1, v3, v4}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 515
    .local v2, "targetId":I
    if-eqz v2, :cond_1

    .line 516
    iget-object v3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    invoke-static {v3}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v3

    invoke-virtual {v3, v2, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v3

    .line 517
    .local v3, "layoutView":Landroid/view/View;
    invoke-virtual {p0, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->handleView(Landroid/view/View;)V

    .line 518
    if-eqz v3, :cond_0

    invoke-virtual {v3}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v4

    const-string v5, "ConstraintLayout"

    invoke-virtual {v4, v5}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_0

    .line 519
    invoke-direct {p0, v3, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildLayoutParams(Landroid/view/View;I)V

    .line 521
    :cond_0
    return-object v3

    .line 524
    .end local v2    # "targetId":I
    .end local v3    # "layoutView":Landroid/view/View;
    :cond_1
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const-string v3, "layout"

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v2, p1, v3, v4}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 525
    .local v2, "appId":I
    if-eqz v2, :cond_2

    .line 526
    invoke-static {v1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v3

    invoke-virtual {v3, v2, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v3

    .line 527
    .restart local v3    # "layoutView":Landroid/view/View;
    invoke-virtual {p0, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->handleView(Landroid/view/View;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 528
    return-object v3

    .line 532
    .end local v1    # "context":Landroid/content/Context;
    .end local v2    # "appId":I
    .end local v3    # "layoutView":Landroid/view/View;
    :cond_2
    goto :goto_0

    .line 530
    :catch_0
    move-exception v1

    .line 531
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 533
    .end local v1    # "e":Ljava/lang/Exception;
    :goto_0
    return-object v0
.end method

.method public getSkinPkgName()Ljava/lang/String;
    .locals 1

    .line 165
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    return-object v0
.end method

.method public getSkinResId(I)I
    .locals 6
    .param p1, "resId"    # I

    .line 289
    const/4 v0, 0x0

    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    .line 290
    .local v1, "context":Landroid/content/Context;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v2

    .line 291
    .local v2, "type":Ljava/lang/String;
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v3

    .line 293
    .local v3, "name":Ljava/lang/String;
    invoke-static {v3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_0

    iget-object v4, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v4, :cond_0

    .line 294
    iget-object v4, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v5, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v4, v3, v2, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v4
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v0, v4

    .line 296
    .local v0, "targetId":I
    return v0

    .line 300
    .end local v0    # "targetId":I
    .end local v1    # "context":Landroid/content/Context;
    .end local v2    # "type":Ljava/lang/String;
    .end local v3    # "name":Ljava/lang/String;
    :cond_0
    nop

    .line 301
    return v0

    .line 298
    :catch_0
    move-exception v1

    .line 299
    .local v1, "e":Ljava/lang/Exception;
    return v0
.end method

.method public getSkinResId(Ljava/lang/String;Ljava/lang/String;)I
    .locals 3
    .param p1, "name"    # Ljava/lang/String;
    .param p2, "type"    # Ljava/lang/String;

    .line 314
    const/4 v0, 0x0

    :try_start_0
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_0

    .line 315
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, p1, p2, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v0, v1

    .line 317
    .local v0, "targetId":I
    return v0

    .line 321
    .end local v0    # "targetId":I
    :cond_0
    nop

    .line 322
    return v0

    .line 319
    :catch_0
    move-exception v1

    .line 320
    .local v1, "e":Ljava/lang/Exception;
    return v0
.end method

.method public getSkinResources()Landroid/content/res/Resources;
    .locals 1

    .line 156
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getString(I)Ljava/lang/String;
    .locals 2
    .param p1, "resId"    # I

    .line 656
    :try_start_0
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    .line 657
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 658
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1

    return-object v1

    .line 660
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v1

    .line 661
    .end local v0    # "realId":I
    :catch_0
    move-exception v0

    .line 662
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 664
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    const/4 v0, 0x0

    return-object v0
.end method

.method public getViewId(Landroid/view/View;)I
    .locals 6
    .param p1, "v"    # Landroid/view/View;

    .line 422
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    .line 423
    .local v0, "resId":I
    if-gtz v0, :cond_0

    .line 424
    return v0

    .line 427
    :cond_0
    :try_start_0
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_1

    .line 428
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v1

    .line 429
    .local v1, "name":Ljava/lang/String;
    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {v2, v0}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v2

    .line 430
    .local v2, "type":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v3

    invoke-virtual {v3}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v3

    .line 431
    .local v3, "context":Landroid/content/Context;
    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v3}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v1, v2, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v4
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v4

    .line 433
    .end local v1    # "name":Ljava/lang/String;
    .end local v2    # "type":Ljava/lang/String;
    .end local v3    # "context":Landroid/content/Context;
    :cond_1
    return v0

    .line 435
    :catch_0
    move-exception v1

    .line 436
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 438
    .end local v1    # "e":Ljava/lang/Exception;
    const/4 v1, 0x0

    return v1
.end method

.method public getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;
    .locals 3
    .param p1, "name"    # Ljava/lang/String;

    .line 934
    const-string v0, "SkinCompatResources"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getXml name:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " mSkinPkgName:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 936
    :try_start_0
    const-string/jumbo v0, "xml"

    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 937
    .local v0, "realId":I
    if-lez v0, :cond_0

    .line 938
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object v1

    return-object v1

    .line 940
    :cond_0
    const-string/jumbo v1, "xml"

    invoke-virtual {p0, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    move v0, v1

    .line 941
    if-lez v0, :cond_1

    .line 942
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object v1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v1

    .line 946
    .end local v0    # "realId":I
    :cond_1
    goto :goto_0

    .line 944
    :catch_0
    move-exception v0

    .line 945
    .local v0, "e":Landroid/content/res/Resources$NotFoundException;
    invoke-virtual {v0}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 947
    .end local v0    # "e":Landroid/content/res/Resources$NotFoundException;
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public handleView(Landroid/view/View;)V
    .locals 1
    .param p1, "target"    # Landroid/view/View;

    .line 175
    new-instance v0, Landroid/util/SparseIntArray;

    invoke-direct {v0}, Landroid/util/SparseIntArray;-><init>()V

    .line 176
    .local v0, "array":Landroid/util/SparseIntArray;
    invoke-direct {p0, p1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->buildIdRules(Landroid/view/View;Landroid/util/SparseIntArray;)V

    .line 188
    return-void
.end method

.method public resetSkin()V
    .locals 2

    .line 122
    const-string v0, "SkinCompatResources"

    const-string v1, "resetSkin"

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 123
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    .line 124
    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    .line 125
    const-string v0, ""

    iput-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    .line 126
    return-void
.end method

.method public setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V
    .locals 3
    .param p1, "skinContext"    # Landroid/content/Context;
    .param p2, "resources"    # Landroid/content/res/Resources;
    .param p3, "pkgName"    # Ljava/lang/String;

    .line 102
    if-eqz p2, :cond_2

    invoke-static {p3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 105
    :cond_0
    const-string v0, "SkinCompatResources"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setupSkin "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 107
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    .line 108
    iput-object p2, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinResources:Landroid/content/res/Resources;

    .line 109
    iput-object p3, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinPkgName:Ljava/lang/String;

    .line 110
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 112
    .local v0, "context":Landroid/content/Context;
    iget-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    if-nez v1, :cond_1

    .line 113
    const-string v1, "SkinCompatResources"

    const-string v2, "new SkinThemeContext"

    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 114
    new-instance v1, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;

    invoke-direct {v1, p0, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;-><init>(Lcom/hcn/skin/support/resources/SkinCompatResources;Landroid/content/Context;)V

    iput-object v1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources;->mSkinContext:Landroid/content/Context;

    .line 116
    :cond_1
    return-void

    .line 103
    .end local v0    # "context":Landroid/content/Context;
    :cond_2
    :goto_0
    return-void
.end method

.method public setupSkin(Landroid/content/res/Resources;Ljava/lang/String;)V
    .locals 1
    .param p1, "resources"    # Landroid/content/res/Resources;
    .param p2, "pkgName"    # Ljava/lang/String;

    .line 98
    const/4 v0, 0x0

    invoke-virtual {p0, v0, p1, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    .line 99
    return-void
.end method
