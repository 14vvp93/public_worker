.class public Lcom/hcn/skin/support/SkinCompatManager;
.super Lcom/hcn/skin/support/observe/SkinObservable;
.source "SkinCompatManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;,
        Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "SkinCompatManager"

.field private static mSkinLayoutFactory2:Landroid/view/LayoutInflater$Factory2;

.field private static volatile sInstance:Lcom/hcn/skin/support/SkinCompatManager;


# instance fields
.field private final mAppContext:Landroid/content/Context;

.field private mLoading:Z

.field private final mLock:Ljava/lang/Object;

.field private mSkinApkPath:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .locals 1

    .line 88
    invoke-direct {p0}, Lcom/hcn/skin/support/observe/SkinObservable;-><init>()V

    .line 61
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLock:Ljava/lang/Object;

    const/4 v0, 0x0

    .line 62
    iput-boolean v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLoading:Z

    const/4 v0, 0x0

    .line 65
    iput-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 89
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    .line 90
    invoke-static {}, Lcom/hcn/skin/support/utils/SkinLog;->init()V

    .line 91
    invoke-static {p1}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->init(Landroid/content/Context;)V

    .line 92
    invoke-direct {p0}, Lcom/hcn/skin/support/SkinCompatManager;->registerSkinUpdateReceiver()V

    return-void
.end method

.method static synthetic access$000(Lcom/hcn/skin/support/SkinCompatManager;)Ljava/lang/Object;
    .locals 0

    .line 39
    iget-object p0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLock:Ljava/lang/Object;

    return-object p0
.end method

.method static synthetic access$100(Lcom/hcn/skin/support/SkinCompatManager;)Z
    .locals 0

    .line 39
    iget-boolean p0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLoading:Z

    return p0
.end method

.method static synthetic access$102(Lcom/hcn/skin/support/SkinCompatManager;Z)Z
    .locals 0

    .line 39
    iput-boolean p1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLoading:Z

    return p1
.end method

.method public static create(Landroid/content/Context;)Lcom/hcn/skin/support/SkinCompatManager;
    .locals 2

    .line 74
    sget-object v0, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    if-nez v0, :cond_1

    .line 75
    const-class v0, Lcom/hcn/skin/support/SkinCompatManager;

    monitor-enter v0

    .line 76
    :try_start_0
    sget-object v1, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    if-nez v1, :cond_0

    .line 77
    new-instance v1, Lcom/hcn/skin/support/SkinCompatManager;

    invoke-direct {v1, p0}, Lcom/hcn/skin/support/SkinCompatManager;-><init>(Landroid/content/Context;)V

    sput-object v1, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    .line 79
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0

    .line 81
    :cond_1
    :goto_0
    sget-object p0, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    return-object p0
.end method

.method public static getInstance()Lcom/hcn/skin/support/SkinCompatManager;
    .locals 1

    .line 85
    sget-object v0, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    return-object v0
.end method

.method private getSkinResources(Ljava/lang/String;Ljava/lang/StringBuilder;)Landroid/content/res/Resources;
    .locals 5

    .line 412
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "getSkinResources "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatManager"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    const/4 v0, 0x0

    .line 414
    :try_start_0
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, p1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 415
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v2

    if-nez v2, :cond_0

    .line 416
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getSkinResources return! "

    invoke-virtual {p2, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, " not exists"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-object v0

    .line 419
    :cond_0
    iget-object v2, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v2

    const/4 v3, 0x0

    invoke-virtual {v2, p1, v3}, Landroid/content/pm/PackageManager;->getPackageArchiveInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v2

    .line 420
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "packageInfo:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 421
    iget-object v3, v2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iput-object p1, v3, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 422
    iget-object v3, v2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iput-object p1, v3, Landroid/content/pm/ApplicationInfo;->publicSourceDir:Ljava/lang/String;

    .line 423
    iget-object p1, v2, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 424
    iget-object p1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object p1

    iget-object p2, v2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    invoke-virtual {p1, p2}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Landroid/content/pm/ApplicationInfo;)Landroid/content/res/Resources;

    move-result-object p1

    const-string p2, "getSkinResources end"

    .line 425
    invoke-static {v1, p2}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 428
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    return-object v0
.end method

.method private getSkinResourcesByPackage(Ljava/lang/String;)Landroid/content/res/Resources;
    .locals 4

    .line 440
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "getSkinResourcesByPackage "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatManager"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 442
    :try_start_0
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    const/4 v2, 0x0

    invoke-virtual {v0, p1, v2}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 444
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getApplicationInfo "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v0, v0, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 446
    :cond_0
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 450
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method private registerSkinUpdateReceiver()V
    .locals 3

    .line 344
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    const-string v1, "com.hcn.skin.support.action.update"

    .line 345
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 346
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    new-instance v2, Lcom/hcn/skin/support/SkinCompatManager$1;

    invoke-direct {v2, p0}, Lcom/hcn/skin/support/SkinCompatManager$1;-><init>(Lcom/hcn/skin/support/SkinCompatManager;)V

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    return-void
.end method

.method public static setSkinLayoutFactory2(Landroid/view/LayoutInflater$Factory2;)V
    .locals 0

    .line 46
    sput-object p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinLayoutFactory2:Landroid/view/LayoutInflater$Factory2;

    return-void
.end method

.method private setupSkinFile()V
    .locals 10

    .line 314
    :try_start_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 315
    invoke-virtual {v0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "skin"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->list(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v1

    .line 316
    array-length v2, v1

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v2, :cond_1

    aget-object v4, v1, v3

    .line 317
    invoke-static {v0}, Lcom/hcn/skin/support/utils/SkinFileUtils;->getSkinDir(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v5

    .line 318
    new-instance v6, Ljava/io/File;

    invoke-direct {v6, v5, v4}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 319
    invoke-virtual {v6}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_0

    const-string v7, "SkinCompatManager"

    .line 320
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "copySkinAssetsToDir "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v7, v6}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 321
    invoke-static {v0, v4, v5}, Lcom/hcn/skin/support/utils/SkinFileUtils;->copySkinAssetsToDir(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :catch_0
    move-exception v0

    .line 325
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    :cond_1
    return-void
.end method

.method public static skinLayoutFactory2()Landroid/view/LayoutInflater$Factory2;
    .locals 1

    .line 50
    sget-object v0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinLayoutFactory2:Landroid/view/LayoutInflater$Factory2;

    return-object v0
.end method


# virtual methods
.method public addSupportAttr(Ljava/lang/String;Lcom/hcn/skin/support/app/attr/base/SkinAttr;)V
    .locals 0

    .line 504
    invoke-static {p1, p2}, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->addSupportAttr(Ljava/lang/String;Lcom/hcn/skin/support/app/attr/base/SkinAttr;)V

    return-void
.end method

.method public getContext()Landroid/content/Context;
    .locals 1

    .line 96
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    return-object v0
.end method

.method public getSkinPackageName(Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    .line 368
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "getSkinPackageName "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatManager"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 370
    :try_start_0
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    const/4 v2, 0x1

    .line 371
    invoke-virtual {v0, p1, v2}, Landroid/content/pm/PackageManager;->getPackageArchiveInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object p1

    const-string v0, "getSkinPackageName end"

    .line 372
    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 373
    iget-object p1, p1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 375
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public getSkinPath()Ljava/lang/String;
    .locals 1

    .line 306
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    return-object v0
.end method

.method public getSkinResources(Ljava/lang/String;)Landroid/content/res/Resources;
    .locals 4
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .line 388
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "getSkinResources "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatManager"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 390
    :try_start_0
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    const/4 v2, 0x0

    invoke-virtual {v0, p1, v2}, Landroid/content/pm/PackageManager;->getPackageArchiveInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v0

    .line 391
    iget-object v2, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iput-object p1, v2, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 392
    iget-object v2, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iput-object p1, v2, Landroid/content/pm/ApplicationInfo;->publicSourceDir:Ljava/lang/String;

    .line 393
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getSkinResources:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, " package:"

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object v2, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget-object v2, v2, Landroid/content/pm/ApplicationInfo;->packageName:Ljava/lang/String;

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 394
    iget-object p1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object p1

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    invoke-virtual {p1, v0}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Landroid/content/pm/ApplicationInfo;)Landroid/content/res/Resources;

    move-result-object p1

    .line 395
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v2, "getSkinResources end"

    .line 396
    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 397
    new-instance v1, Landroid/content/res/Resources;

    invoke-virtual {p1}, Landroid/content/res/Resources;->getAssets()Landroid/content/res/AssetManager;

    move-result-object p1

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v2

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    invoke-direct {v1, p1, v2, v0}, Landroid/content/res/Resources;-><init>(Landroid/content/res/AssetManager;Landroid/util/DisplayMetrics;Landroid/content/res/Configuration;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object v1

    :catch_0
    move-exception p1

    .line 399
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    const/4 p1, 0x0

    return-object p1
.end method

.method public loadSkin(Ljava/lang/String;)Landroid/os/AsyncTask;
    .locals 1

    const/4 v0, 0x0

    .line 150
    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkin(Ljava/lang/String;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)Landroid/os/AsyncTask;

    move-result-object p1

    return-object p1
.end method

.method public loadSkin(Ljava/lang/String;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)Landroid/os/AsyncTask;
    .locals 3

    .line 161
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "loadSkin "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatManager"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 162
    new-instance v0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;

    invoke-direct {v0, p0, p2}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;-><init>(Lcom/hcn/skin/support/SkinCompatManager;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)V

    sget-object p2, Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR:Ljava/util/concurrent/Executor;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    aput-object p1, v1, v2

    invoke-virtual {v0, p2, v1}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->executeOnExecutor(Ljava/util/concurrent/Executor;[Ljava/lang/Object;)Landroid/os/AsyncTask;

    move-result-object p1

    return-object p1
.end method

.method public loadSkin()Ljava/lang/String;
    .locals 3

    .line 135
    invoke-static {}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->getInstance()Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->getSkinName()Ljava/lang/String;

    move-result-object v0

    .line 136
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "loadSkin SharedPreferences "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "SkinCompatManager"

    invoke-static {v2, v1}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 137
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 140
    :cond_0
    invoke-virtual {p0, v0}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinSync(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public loadSkinByPackage(Ljava/lang/String;)V
    .locals 2

    .line 335
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    const-string v0, ""

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 336
    invoke-direct {p0, p1}, Lcom/hcn/skin/support/SkinCompatManager;->getSkinResourcesByPackage(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 338
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, v0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/res/Resources;Ljava/lang/String;)V

    :cond_0
    return-void
.end method

.method public loadSkinByPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    .line 285
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "loadSkinByPath "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatManager"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 286
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 289
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 290
    iput-object p2, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 291
    invoke-direct {p0, p2, v0}, Lcom/hcn/skin/support/SkinCompatManager;->getSkinResources(Ljava/lang/String;Ljava/lang/StringBuilder;)Landroid/content/res/Resources;

    move-result-object v2

    if-eqz v2, :cond_1

    .line 293
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, p1, v2, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    return-object p2

    .line 296
    :cond_1
    iput-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    return-object v1
.end method

.method public loadSkinByPath(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    const/4 v0, 0x0

    .line 281
    invoke-virtual {p0, v0, p1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinByPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public loadSkinClass(Ljava/lang/String;)Ljava/lang/Class;
    .locals 8

    .line 475
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinPkgName()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    if-eqz v0, :cond_0

    const-string v2, ""

    .line 476
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    goto :goto_0

    :cond_0
    move v2, v1

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v2, 0x1

    :goto_1
    const/4 v3, 0x0

    if-eqz v2, :cond_2

    return-object v3

    .line 482
    :cond_2
    :try_start_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v2

    .line 483
    invoke-virtual {v2, v0, v1}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v1

    if-nez v1, :cond_3

    return-object v3

    .line 488
    :cond_3
    iget-object v2, v1, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 489
    invoke-virtual {p0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-virtual {v4}, Landroid/content/Context;->getCodeCacheDir()Ljava/io/File;

    move-result-object v4

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    .line 490
    iget-object v1, v1, Landroid/content/pm/ApplicationInfo;->nativeLibraryDir:Ljava/lang/String;

    const-string v5, "SkinCompatManager"

    .line 491
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "loadSkinClass apkPath:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " optPath:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " libPath:"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 492
    new-instance v5, Ldalvik/system/DexClassLoader;

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v6

    invoke-direct {v5, v2, v4, v1, v6}, Ldalvik/system/DexClassLoader;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V

    .line 493
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "."

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v5, p1}, Ldalvik/system/DexClassLoader;->loadClass(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 498
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    return-object v3
.end method

.method public loadSkinSync(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .locals 6

    .line 243
    invoke-direct {p0}, Lcom/hcn/skin/support/SkinCompatManager;->setupSkinFile()V

    .line 244
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_3

    const-string v0, ""

    invoke-virtual {v0, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_3

    .line 245
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 248
    new-instance v2, Ljava/io/File;

    invoke-virtual {p0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/skin/support/utils/SkinFileUtils;->getSkinDir(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3, p2}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 249
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v3

    if-eqz v3, :cond_0

    .line 250
    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    goto/16 :goto_0

    .line 252
    :cond_0
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "/apd/app/"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 253
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "loadSkinSync "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "SkinCompatManager"

    invoke-static {v4, v3}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 254
    new-instance v3, Ljava/io/File;

    invoke-direct {v3, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 255
    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v2

    if-eqz v2, :cond_1

    .line 256
    invoke-virtual {v3}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    .line 257
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "loadSkinSync from apd "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v4, v3}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 259
    :cond_1
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v3

    invoke-virtual {v3}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "skin"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 260
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "loadSkinSync from sdcard "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v4, v3}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 263
    :goto_0
    iput-object v2, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 264
    invoke-direct {p0, v2, v0}, Lcom/hcn/skin/support/SkinCompatManager;->getSkinResources(Ljava/lang/String;Ljava/lang/StringBuilder;)Landroid/content/res/Resources;

    move-result-object v2

    if-eqz v2, :cond_2

    .line 266
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, p1, v2, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    return-object p2

    .line 269
    :cond_2
    iput-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    :cond_3
    return-object v1
.end method

.method public loadSkinSync(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    const/4 v0, 0x0

    .line 239
    invoke-virtual {p0, v0, p1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinSync(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public restoreDefaultSkin()V
    .locals 2

    const-string v0, "SkinCompatManager"

    const-string v1, "restoreDefaultSkin"

    .line 125
    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    const-string v0, ""

    .line 126
    invoke-virtual {p0, v0}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkin(Ljava/lang/String;)Landroid/os/AsyncTask;

    return-void
.end method

.method public updateConfig(Landroid/content/res/Configuration;)V
    .locals 3

    .line 456
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 457
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinPkgName()Ljava/lang/String;

    move-result-object v1

    if-eqz v0, :cond_0

    .line 459
    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v2

    invoke-virtual {v0, p1, v2}, Landroid/content/res/Resources;->updateConfiguration(Landroid/content/res/Configuration;Landroid/util/DisplayMetrics;)V

    .line 460
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updateConfig "

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v2

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v2, "SkinCompatManager"

    invoke-static {v2, p1}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 462
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinContext()Landroid/content/Context;

    move-result-object p1

    .line 463
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    invoke-virtual {v2, p1, v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    :cond_0
    return-void
.end method
