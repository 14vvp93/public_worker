.class public Lcom/hcn/skin/support/SkinCompatManager;
.super Lcom/hcn/skin/support/observe/SkinObservable;
.source "SkinCompatManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;,
        Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;
    }
.end annotation


# static fields
.field private static volatile sInstance:Lcom/hcn/skin/support/SkinCompatManager;


# instance fields
.field private final mAppContext:Landroid/content/Context;

.field private mLoading:Z

.field private final mLock:Ljava/lang/Object;

.field private mSkinApkPath:Ljava/lang/String;


# direct methods
.method private constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 71
    invoke-direct {p0}, Lcom/hcn/skin/support/observe/SkinObservable;-><init>()V

    .line 44
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLock:Ljava/lang/Object;

    .line 45
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLoading:Z

    .line 48
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 72
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    .line 73
    invoke-static {}, Lcom/hcn/skin/support/utils/SkinLog;->init()V

    .line 74
    invoke-static {p1}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->init(Landroid/content/Context;)V

    .line 75
    invoke-direct {p0}, Lcom/hcn/skin/support/SkinCompatManager;->registerSkinUpdateReceiver()V

    .line 76
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/skin/support/SkinCompatManager;)Ljava/lang/Object;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/skin/support/SkinCompatManager;

    .line 34
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLock:Ljava/lang/Object;

    return-object v0
.end method

.method static synthetic access$100(Lcom/hcn/skin/support/SkinCompatManager;)Z
    .locals 1
    .param p0, "x0"    # Lcom/hcn/skin/support/SkinCompatManager;

    .line 34
    iget-boolean v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLoading:Z

    return v0
.end method

.method static synthetic access$102(Lcom/hcn/skin/support/SkinCompatManager;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/hcn/skin/support/SkinCompatManager;
    .param p1, "x1"    # Z

    .line 34
    iput-boolean p1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mLoading:Z

    return p1
.end method

.method public static create(Landroid/content/Context;)Lcom/hcn/skin/support/SkinCompatManager;
    .locals 2
    .param p0, "context"    # Landroid/content/Context;

    .line 57
    sget-object v0, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    if-nez v0, :cond_1

    .line 58
    const-class v0, Lcom/hcn/skin/support/SkinCompatManager;

    monitor-enter v0

    .line 59
    :try_start_0
    sget-object v1, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    if-nez v1, :cond_0

    .line 60
    new-instance v1, Lcom/hcn/skin/support/SkinCompatManager;

    invoke-direct {v1, p0}, Lcom/hcn/skin/support/SkinCompatManager;-><init>(Landroid/content/Context;)V

    sput-object v1, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    .line 62
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    .line 64
    :cond_1
    :goto_0
    sget-object v0, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    return-object v0
.end method

.method public static getInstance()Lcom/hcn/skin/support/SkinCompatManager;
    .locals 1

    .line 68
    sget-object v0, Lcom/hcn/skin/support/SkinCompatManager;->sInstance:Lcom/hcn/skin/support/SkinCompatManager;

    return-object v0
.end method

.method private getSkinResources(Ljava/lang/String;Ljava/lang/StringBuilder;)Landroid/content/res/Resources;
    .locals 6
    .param p1, "skinPkgPath"    # Ljava/lang/String;
    .param p2, "sb"    # Ljava/lang/StringBuilder;

    .line 395
    const-string v0, "SkinCompatManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getSkinResources "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 397
    const/4 v0, 0x0

    :try_start_0
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, p1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 398
    .local v1, "apkFile":Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    if-nez v2, :cond_0

    .line 399
    const-string v2, "SkinCompatManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "getSkinResources return! "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, " not exists"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 400
    return-object v0

    .line 402
    :cond_0
    iget-object v2, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v2

    const/4 v3, 0x0

    invoke-virtual {v2, p1, v3}, Landroid/content/pm/PackageManager;->getPackageArchiveInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v2

    .line 403
    .local v2, "packageInfo":Landroid/content/pm/PackageInfo;
    const-string v3, "SkinCompatManager"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "packageInfo:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 404
    iget-object v3, v2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iput-object p1, v3, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 405
    iget-object v3, v2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iput-object p1, v3, Landroid/content/pm/ApplicationInfo;->publicSourceDir:Ljava/lang/String;

    .line 406
    iget-object v3, v2, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {p2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 407
    iget-object v3, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v3

    iget-object v4, v2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    invoke-virtual {v3, v4}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Landroid/content/pm/ApplicationInfo;)Landroid/content/res/Resources;

    move-result-object v3

    .line 408
    .local v3, "res":Landroid/content/res/Resources;
    const-string v4, "SkinCompatManager"

    const-string v5, "getSkinResources end"

    invoke-static {v4, v5}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 409
    return-object v3

    .line 410
    .end local v1    # "apkFile":Ljava/io/File;
    .end local v2    # "packageInfo":Landroid/content/pm/PackageInfo;
    .end local v3    # "res":Landroid/content/res/Resources;
    :catch_0
    move-exception v1

    .line 411
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 413
    .end local v1    # "e":Ljava/lang/Exception;
    return-object v0
.end method

.method private registerSkinUpdateReceiver()V
    .locals 3

    .line 327
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 328
    .local v0, "intentFilter":Landroid/content/IntentFilter;
    const-string v1, "com.hcn.skin.support.action.update"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 329
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    new-instance v2, Lcom/hcn/skin/support/SkinCompatManager$1;

    invoke-direct {v2, p0}, Lcom/hcn/skin/support/SkinCompatManager$1;-><init>(Lcom/hcn/skin/support/SkinCompatManager;)V

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 342
    return-void
.end method

.method private setupSkinFile()V
    .locals 10

    .line 297
    :try_start_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 298
    .local v0, "context":Landroid/content/Context;
    invoke-virtual {v0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "skin"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->list(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v1

    .line 299
    .local v1, "skinFiles":[Ljava/lang/String;
    array-length v2, v1

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v2, :cond_1

    aget-object v4, v1, v3

    .line 300
    .local v4, "fileName":Ljava/lang/String;
    invoke-static {v0}, Lcom/hcn/skin/support/utils/SkinFileUtils;->getSkinDir(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v5

    .line 301
    .local v5, "targetDir":Ljava/lang/String;
    new-instance v6, Ljava/io/File;

    invoke-direct {v6, v5, v4}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 302
    .local v6, "file":Ljava/io/File;
    invoke-virtual {v6}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_0

    .line 303
    const-string v7, "SkinCompatManager"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "copySkinAssetsToDir "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 304
    invoke-static {v0, v4, v5}, Lcom/hcn/skin/support/utils/SkinFileUtils;->copySkinAssetsToDir(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 299
    .end local v4    # "fileName":Ljava/lang/String;
    .end local v5    # "targetDir":Ljava/lang/String;
    .end local v6    # "file":Ljava/io/File;
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 309
    .end local v0    # "context":Landroid/content/Context;
    .end local v1    # "skinFiles":[Ljava/lang/String;
    :cond_1
    goto :goto_1

    .line 307
    :catch_0
    move-exception v0

    .line 308
    .local v0, "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 310
    .end local v0    # "e":Ljava/io/IOException;
    :goto_1
    return-void
.end method


# virtual methods
.method public getContext()Landroid/content/Context;
    .locals 1

    .line 79
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager;->mAppContext:Landroid/content/Context;

    return-object v0
.end method

.method public loadSkin(Ljava/lang/String;)Landroid/os/AsyncTask;
    .locals 1
    .param p1, "skinName"    # Ljava/lang/String;

    .line 133
    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkin(Ljava/lang/String;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)Landroid/os/AsyncTask;

    move-result-object v0

    return-object v0
.end method

.method public loadSkin(Ljava/lang/String;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)Landroid/os/AsyncTask;
    .locals 4
    .param p1, "skinName"    # Ljava/lang/String;
    .param p2, "listener"    # Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    .line 144
    const-string v0, "SkinCompatManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "loadSkin "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 145
    new-instance v0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;

    invoke-direct {v0, p0, p2}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;-><init>(Lcom/hcn/skin/support/SkinCompatManager;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)V

    sget-object v1, Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR:Ljava/util/concurrent/Executor;

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/String;

    const/4 v3, 0x0

    aput-object p1, v2, v3

    invoke-virtual {v0, v1, v2}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->executeOnExecutor(Ljava/util/concurrent/Executor;[Ljava/lang/Object;)Landroid/os/AsyncTask;

    move-result-object v0

    return-object v0
.end method

.method public loadSkinByPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .param p1, "skinContext"    # Landroid/content/Context;
    .param p2, "skinApkPath"    # Ljava/lang/String;

    .line 268
    const-string v0, "SkinCompatManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "loadSkinByPath "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 269
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 270
    return-object v1

    .line 272
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 273
    .local v0, "sb":Ljava/lang/StringBuilder;
    iput-object p2, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 274
    invoke-direct {p0, p2, v0}, Lcom/hcn/skin/support/SkinCompatManager;->getSkinResources(Ljava/lang/String;Ljava/lang/StringBuilder;)Landroid/content/res/Resources;

    move-result-object v2

    .line 275
    .local v2, "resources":Landroid/content/res/Resources;
    if-eqz v2, :cond_1

    .line 276
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, p1, v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    .line 277
    return-object p2

    .line 279
    :cond_1
    iput-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 280
    return-object v1
.end method

.method public loadSkinByPath(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p1, "skinApkPath"    # Ljava/lang/String;

    .line 264
    const/4 v0, 0x0

    invoke-virtual {p0, v0, p1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinByPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public loadSkinSync(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .locals 9
    .param p1, "skinContext"    # Landroid/content/Context;
    .param p2, "skinName"    # Ljava/lang/String;

    .line 226
    invoke-direct {p0}, Lcom/hcn/skin/support/SkinCompatManager;->setupSkinFile()V

    .line 227
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_3

    const-string v0, ""

    invoke-virtual {v0, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_3

    .line 228
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 230
    .local v0, "sb":Ljava/lang/StringBuilder;
    const/4 v2, 0x0

    .line 231
    .local v2, "skinPkgPath":Ljava/lang/String;
    new-instance v3, Ljava/io/File;

    invoke-virtual {p0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-static {v4}, Lcom/hcn/skin/support/utils/SkinFileUtils;->getSkinDir(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4, p2}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 232
    .local v3, "file":Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v4

    if-eqz v4, :cond_0

    .line 233
    invoke-virtual {v3}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    goto/16 :goto_0

    .line 235
    :cond_0
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "/apd/app/"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 236
    .local v4, "path":Ljava/lang/String;
    const-string v5, "SkinCompatManager"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "loadSkinSync "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 237
    new-instance v5, Ljava/io/File;

    invoke-direct {v5, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 238
    .local v5, "apkInApd":Ljava/io/File;
    invoke-virtual {v5}, Ljava/io/File;->exists()Z

    move-result v6

    if-eqz v6, :cond_1

    .line 239
    invoke-virtual {v5}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    .line 240
    const-string v6, "SkinCompatManager"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "loadSkinSync from apd "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 242
    :cond_1
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v7

    invoke-virtual {v7}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v7, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v7, "skin"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v7, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 243
    const-string v6, "SkinCompatManager"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "loadSkinSync from sdcard "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 246
    .end local v4    # "path":Ljava/lang/String;
    .end local v5    # "apkInApd":Ljava/io/File;
    :goto_0
    iput-object v2, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 247
    invoke-direct {p0, v2, v0}, Lcom/hcn/skin/support/SkinCompatManager;->getSkinResources(Ljava/lang/String;Ljava/lang/StringBuilder;)Landroid/content/res/Resources;

    move-result-object v4

    .line 248
    .local v4, "resources":Landroid/content/res/Resources;
    if-eqz v4, :cond_2

    .line 249
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v1, p1, v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/Context;Landroid/content/res/Resources;Ljava/lang/String;)V

    .line 250
    return-object p2

    .line 252
    :cond_2
    iput-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager;->mSkinApkPath:Ljava/lang/String;

    .line 254
    .end local v0    # "sb":Ljava/lang/StringBuilder;
    .end local v2    # "skinPkgPath":Ljava/lang/String;
    .end local v3    # "file":Ljava/io/File;
    .end local v4    # "resources":Landroid/content/res/Resources;
    :cond_3
    return-object v1
.end method

.method public loadSkinSync(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p1, "skinName"    # Ljava/lang/String;

    .line 222
    const/4 v0, 0x0

    invoke-virtual {p0, v0, p1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinSync(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
