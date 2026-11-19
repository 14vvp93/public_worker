.class public Lcom/android/systemui/SystemUIApplication;
.super Landroid/app/Application;
.source "SystemUIApplication.java"

# interfaces
.implements Lcom/android/systemui/SysUiServiceProvider;


# static fields
.field public static final CUSTOM_NAME:Ljava/lang/String;

.field private static gInst:Lcom/android/systemui/SystemUIApplication;

.field private static mIsPortraitDevice:Z

.field private static mbNaviWidth:I

.field private static mbSupportBotNavis:Z

.field private static mbSupportRotation:Z

.field private static mbSupportSingleNavis:I


# instance fields
.field private mBootCompleted:Z

.field private final mComponents:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation
.end field

.field private mHomeReady:Z

.field private mServices:[Lcom/android/systemui/SystemUI;

.field private mServicesStarted:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 64
    const/4 v0, 0x0

    sput-object v0, Lcom/android/systemui/SystemUIApplication;->gInst:Lcom/android/systemui/SystemUIApplication;

    .line 76
    const/4 v0, 0x0

    sput-boolean v0, Lcom/android/systemui/SystemUIApplication;->mIsPortraitDevice:Z

    .line 79
    sput-boolean v0, Lcom/android/systemui/SystemUIApplication;->mbSupportRotation:Z

    .line 81
    sput-boolean v0, Lcom/android/systemui/SystemUIApplication;->mbSupportBotNavis:Z

    .line 84
    sput v0, Lcom/android/systemui/SystemUIApplication;->mbSupportSingleNavis:I

    .line 87
    sput v0, Lcom/android/systemui/SystemUIApplication;->mbNaviWidth:I

    .line 91
    const-string v0, "persist.sys.customer"

    const-string v1, ""

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/systemui/SystemUIApplication;->CUSTOM_NAME:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 59
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    .line 73
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/SystemUIApplication;->mComponents:Ljava/util/Map;

    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/SystemUIApplication;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;

    .line 59
    iget-boolean v0, p0, Lcom/android/systemui/SystemUIApplication;->mBootCompleted:Z

    return v0
.end method

.method static synthetic access$002(Lcom/android/systemui/SystemUIApplication;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;
    .param p1, "x1"    # Z

    .line 59
    iput-boolean p1, p0, Lcom/android/systemui/SystemUIApplication;->mBootCompleted:Z

    return p1
.end method

.method static synthetic access$100(Lcom/android/systemui/SystemUIApplication;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;

    .line 59
    iget-boolean v0, p0, Lcom/android/systemui/SystemUIApplication;->mServicesStarted:Z

    return v0
.end method

.method static synthetic access$200(Lcom/android/systemui/SystemUIApplication;)[Lcom/android/systemui/SystemUI;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;

    .line 59
    iget-object v0, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/systemui/SystemUIApplication;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;

    .line 59
    iget-boolean v0, p0, Lcom/android/systemui/SystemUIApplication;->mHomeReady:Z

    return v0
.end method

.method static synthetic access$302(Lcom/android/systemui/SystemUIApplication;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;
    .param p1, "x1"    # Z

    .line 59
    iput-boolean p1, p0, Lcom/android/systemui/SystemUIApplication;->mHomeReady:Z

    return p1
.end method

.method static synthetic access$400(Lcom/android/systemui/SystemUIApplication;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/SystemUIApplication;

    .line 59
    invoke-direct {p0}, Lcom/android/systemui/SystemUIApplication;->startOtherServicesAtc()V

    return-void
.end method

.method public static getNaviBarPosition()I
    .locals 1

    .line 296
    sget v0, Lcom/android/systemui/SystemUIApplication;->mbSupportSingleNavis:I

    return v0
.end method

.method public static hasBottomBar()Z
    .locals 1

    .line 293
    sget-boolean v0, Lcom/android/systemui/SystemUIApplication;->mbSupportBotNavis:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lcom/android/systemui/SystemUIApplication;->mIsPortraitDevice:Z

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isPortraitDevice()Z
    .locals 1

    .line 103
    sget-boolean v0, Lcom/android/systemui/SystemUIApplication;->mIsPortraitDevice:Z

    return v0
.end method

.method public static isPortraitWindowMode(Landroid/content/Context;)Z
    .locals 7
    .param p0, "context"    # Landroid/content/Context;

    .line 284
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    .line 285
    .local v0, "configuration":Landroid/content/res/Configuration;
    invoke-static {p0}, Landroid/Configures/HConfig;->isRotationTypeConfigured(Landroid/content/Context;)Z

    move-result v1

    .line 286
    .local v1, "isRotationTypeDevice":Z
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    const-string/jumbo v3, "user_rotation"

    const/4 v4, 0x0

    invoke-static {v2, v3, v4}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v2

    .line 287
    .local v2, "v":I
    sget-boolean v3, Lcom/android/systemui/SystemUIApplication;->mIsPortraitDevice:Z

    const/4 v5, 0x1

    if-nez v3, :cond_0

    if-eqz v1, :cond_0

    if-ne v2, v5, :cond_0

    iget v3, v0, Landroid/content/res/Configuration;->orientation:I

    if-ne v3, v5, :cond_0

    move v3, v5

    goto :goto_0

    :cond_0
    move v3, v4

    .line 289
    .local v3, "isRotationPortraitState":Z
    :goto_0
    sget-boolean v6, Lcom/android/systemui/SystemUIApplication;->mIsPortraitDevice:Z

    if-eqz v6, :cond_1

    if-eqz v2, :cond_2

    :cond_1
    if-eqz v3, :cond_3

    :cond_2
    move v4, v5

    nop

    :cond_3
    return v4
.end method

.method private startOtherServicesAtc()V
    .locals 2

    .line 318
    invoke-virtual {p0}, Lcom/android/systemui/SystemUIApplication;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f03002b

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v0

    .line 319
    .local v0, "names":[Ljava/lang/String;
    const/4 v1, 0x1

    invoke-direct {p0, v0, v1}, Lcom/android/systemui/SystemUIApplication;->startServicesIfNeeded([Ljava/lang/String;Z)V

    .line 320
    return-void
.end method

.method private startServicesIfNeeded([Ljava/lang/String;)V
    .locals 1
    .param p1, "services"    # [Ljava/lang/String;

    .line 334
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/systemui/SystemUIApplication;->startServicesIfNeeded([Ljava/lang/String;Z)V

    .line 335
    return-void
.end method

.method private startServicesIfNeeded([Ljava/lang/String;Z)V
    .locals 11
    .param p1, "services"    # [Ljava/lang/String;
    .param p2, "force"    # Z

    .line 338
    iget-boolean v0, p0, Lcom/android/systemui/SystemUIApplication;->mServicesStarted:Z

    if-eqz v0, :cond_0

    if-nez p2, :cond_0

    .line 339
    return-void

    .line 341
    :cond_0
    array-length v0, p1

    new-array v0, v0, [Lcom/android/systemui/SystemUI;

    iput-object v0, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    .line 343
    iget-boolean v0, p0, Lcom/android/systemui/SystemUIApplication;->mBootCompleted:Z

    const/4 v1, 0x1

    if-nez v0, :cond_1

    .line 346
    const-string v0, "1"

    const-string v2, "sys.boot_completed"

    invoke-static {v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 347
    iput-boolean v1, p0, Lcom/android/systemui/SystemUIApplication;->mBootCompleted:Z

    .line 352
    :cond_1
    const-string v0, "SystemUIService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Starting SystemUI services for user "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 353
    invoke-static {}, Landroid/os/Process;->myUserHandle()Landroid/os/UserHandle;

    move-result-object v3

    invoke-virtual {v3}, Landroid/os/UserHandle;->getIdentifier()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, "."

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 352
    invoke-static {v0, v2}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 354
    new-instance v0, Landroid/util/TimingsTraceLog;

    const-string v2, "SystemUIBootTiming"

    const-wide/16 v3, 0x1000

    invoke-direct {v0, v2, v3, v4}, Landroid/util/TimingsTraceLog;-><init>(Ljava/lang/String;J)V

    .line 356
    .local v0, "log":Landroid/util/TimingsTraceLog;
    const-string v2, "StartServices"

    invoke-virtual {v0, v2}, Landroid/util/TimingsTraceLog;->traceBegin(Ljava/lang/String;)V

    .line 357
    array-length v2, p1

    .line 358
    .local v2, "N":I
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v2, :cond_4

    .line 359
    aget-object v4, p1, v3

    .line 361
    .local v4, "clsName":Ljava/lang/String;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "StartServices"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v0, v5}, Landroid/util/TimingsTraceLog;->traceBegin(Ljava/lang/String;)V

    .line 362
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v5

    .line 365
    .local v5, "ti":J
    :try_start_0
    invoke-static {v4}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v7

    .line 366
    .local v7, "cls":Ljava/lang/Class;
    iget-object v8, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    invoke-virtual {v7}, Ljava/lang/Class;->newInstance()Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Lcom/android/systemui/SystemUI;

    aput-object v9, v8, v3
    :try_end_0
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/lang/IllegalAccessException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/InstantiationException; {:try_start_0 .. :try_end_0} :catch_0

    .line 373
    nop

    .line 372
    nop

    .line 375
    iget-object v8, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    aget-object v8, v8, v3

    iput-object p0, v8, Lcom/android/systemui/SystemUI;->mContext:Landroid/content/Context;

    .line 376
    iget-object v8, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    aget-object v8, v8, v3

    iget-object v9, p0, Lcom/android/systemui/SystemUIApplication;->mComponents:Ljava/util/Map;

    iput-object v9, v8, Lcom/android/systemui/SystemUI;->mComponents:Ljava/util/Map;

    .line 378
    iget-object v8, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    aget-object v8, v8, v3

    invoke-virtual {v8}, Lcom/android/systemui/SystemUI;->start()V

    .line 379
    invoke-virtual {v0}, Landroid/util/TimingsTraceLog;->traceEnd()V

    .line 382
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v8

    sub-long/2addr v8, v5

    .line 383
    .end local v5    # "ti":J
    .local v8, "ti":J
    const-wide/16 v5, 0x3e8

    cmp-long v5, v8, v5

    if-lez v5, :cond_2

    .line 384
    const-string v5, "SystemUIService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Initialization of "

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v10, " took "

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v8, v9}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v10, " ms"

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 386
    :cond_2
    iget-boolean v5, p0, Lcom/android/systemui/SystemUIApplication;->mBootCompleted:Z

    if-eqz v5, :cond_3

    .line 387
    iget-object v5, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    aget-object v5, v5, v3

    invoke-virtual {v5}, Lcom/android/systemui/SystemUI;->onBootCompleted()V

    .line 358
    .end local v4    # "clsName":Ljava/lang/String;
    .end local v7    # "cls":Ljava/lang/Class;
    .end local v8    # "ti":J
    :cond_3
    add-int/lit8 v3, v3, 0x1

    goto/16 :goto_0

    .line 371
    .restart local v4    # "clsName":Ljava/lang/String;
    .restart local v5    # "ti":J
    :catch_0
    move-exception v1

    .line 372
    .local v1, "ex":Ljava/lang/InstantiationException;
    new-instance v7, Ljava/lang/RuntimeException;

    invoke-direct {v7, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw v7

    .line 369
    .end local v1    # "ex":Ljava/lang/InstantiationException;
    :catch_1
    move-exception v1

    .line 370
    .local v1, "ex":Ljava/lang/IllegalAccessException;
    new-instance v7, Ljava/lang/RuntimeException;

    invoke-direct {v7, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw v7

    .line 367
    .end local v1    # "ex":Ljava/lang/IllegalAccessException;
    :catch_2
    move-exception v1

    .line 368
    .local v1, "ex":Ljava/lang/ClassNotFoundException;
    new-instance v7, Ljava/lang/RuntimeException;

    invoke-direct {v7, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw v7

    .line 390
    .end local v1    # "ex":Ljava/lang/ClassNotFoundException;
    .end local v3    # "i":I
    .end local v4    # "clsName":Ljava/lang/String;
    .end local v5    # "ti":J
    :cond_4
    invoke-virtual {v0}, Landroid/util/TimingsTraceLog;->traceEnd()V

    .line 391
    const-class v3, Lcom/android/systemui/plugins/PluginManager;

    invoke-static {v3}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/systemui/plugins/PluginManager;

    new-instance v4, Lcom/android/systemui/SystemUIApplication$4;

    invoke-direct {v4, p0}, Lcom/android/systemui/SystemUIApplication$4;-><init>(Lcom/android/systemui/SystemUIApplication;)V

    const-class v5, Lcom/android/systemui/plugins/OverlayPlugin;

    invoke-interface {v3, v4, v5, v1}, Lcom/android/systemui/plugins/PluginManager;->addPluginListener(Lcom/android/systemui/plugins/PluginListener;Ljava/lang/Class;Z)V

    .line 422
    iput-boolean v1, p0, Lcom/android/systemui/SystemUIApplication;->mServicesStarted:Z

    .line 423
    return-void
.end method


# virtual methods
.method public getComponent(Ljava/lang/Class;)Ljava/lang/Object;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/lang/Class<",
            "TT;>;)TT;"
        }
    .end annotation

    .line 439
    .local p1, "interfaceType":Ljava/lang/Class;, "Ljava/lang/Class<TT;>;"
    iget-object v0, p0, Lcom/android/systemui/SystemUIApplication;->mComponents:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public getServices()[Lcom/android/systemui/SystemUI;
    .locals 1

    .line 443
    iget-object v0, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    return-object v0
.end method

.method public onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 3
    .param p1, "newConfig"    # Landroid/content/res/Configuration;

    .line 427
    iget-boolean v0, p0, Lcom/android/systemui/SystemUIApplication;->mServicesStarted:Z

    if-eqz v0, :cond_1

    .line 428
    iget-object v0, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    array-length v0, v0

    .line 429
    .local v0, "len":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_1

    .line 430
    iget-object v2, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    aget-object v2, v2, v1

    if-eqz v2, :cond_0

    .line 431
    iget-object v2, p0, Lcom/android/systemui/SystemUIApplication;->mServices:[Lcom/android/systemui/SystemUI;

    aget-object v2, v2, v1

    invoke-virtual {v2, p1}, Lcom/android/systemui/SystemUI;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 429
    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 435
    .end local v0    # "len":I
    .end local v1    # "i":I
    :cond_1
    return-void
.end method

.method public onCreate()V
    .locals 6

    .line 136
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 141
    sput-object p0, Lcom/android/systemui/SystemUIApplication;->gInst:Lcom/android/systemui/SystemUIApplication;

    .line 143
    invoke-static {p0}, Lcom/hcn/auto/systemui/SkinManager;->getInstance(Landroid/content/Context;)Lcom/hcn/auto/systemui/SkinManager;

    move-result-object v0

    .line 144
    .local v0, "skinManager":Lcom/hcn/auto/systemui/SkinManager;
    invoke-virtual {v0}, Lcom/hcn/auto/systemui/SkinManager;->loadSkin()V

    .line 147
    const-string v1, "sys.disp.force_direct"

    const/4 v2, 0x1

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v1

    .line 148
    .local v1, "hwRotation":I
    const/4 v3, 0x2

    if-ne v3, v1, :cond_0

    .line 149
    sput-boolean v2, Lcom/android/systemui/SystemUIApplication;->mIsPortraitDevice:Z

    .line 153
    :cond_0
    invoke-static {p0}, Landroid/Configures/HConfig;->isRotationTypeConfigured(Landroid/content/Context;)Z

    move-result v3

    if-eqz v3, :cond_1

    .line 154
    sput-boolean v2, Lcom/android/systemui/SystemUIApplication;->mbSupportRotation:Z

    .line 157
    :cond_1
    const-string v3, "sys.disp.navigationbarposition"

    const/4 v4, 0x0

    invoke-static {v3, v4}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v3

    sput v3, Lcom/android/systemui/SystemUIApplication;->mbSupportSingleNavis:I

    .line 159
    const-string v3, "ro.sys.customer.naviwidth"

    invoke-static {v3, v4}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v3

    sput v3, Lcom/android/systemui/SystemUIApplication;->mbNaviWidth:I

    .line 163
    const-string v3, "ro.sys.customer.naviheight"

    invoke-static {v3, v4}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v3

    if-le v3, v2, :cond_2

    .line 164
    sput-boolean v2, Lcom/android/systemui/SystemUIApplication;->mbSupportBotNavis:Z

    .line 167
    :cond_2
    const v2, 0x7f120234

    invoke-virtual {p0, v2}, Lcom/android/systemui/SystemUIApplication;->setTheme(I)V

    .line 168
    invoke-static {p0}, Lcom/android/systemui/SystemUIFactory;->createFromConfig(Landroid/content/Context;)V

    .line 169
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->init(Landroid/app/Application;)Lcom/hcn/auto/theme/utils/ThemeUtils;

    .line 171
    invoke-static {}, Landroid/os/Process;->myUserHandle()Landroid/os/UserHandle;

    move-result-object v2

    sget-object v3, Landroid/os/UserHandle;->SYSTEM:Landroid/os/UserHandle;

    invoke-virtual {v2, v3}, Landroid/os/UserHandle;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 172
    new-instance v2, Landroid/content/IntentFilter;

    const-string v3, "android.intent.action.BOOT_COMPLETED"

    invoke-direct {v2, v3}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 173
    .local v2, "bootCompletedFilter":Landroid/content/IntentFilter;
    const/16 v3, 0x3e8

    invoke-virtual {v2, v3}, Landroid/content/IntentFilter;->setPriority(I)V

    .line 174
    new-instance v5, Lcom/android/systemui/SystemUIApplication$1;

    invoke-direct {v5, p0}, Lcom/android/systemui/SystemUIApplication$1;-><init>(Lcom/android/systemui/SystemUIApplication;)V

    invoke-virtual {p0, v5, v2}, Lcom/android/systemui/SystemUIApplication;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 191
    const-string v5, "ro.atc.boot_time"

    invoke-static {v5, v4}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v4

    if-eqz v4, :cond_3

    .line 193
    new-instance v4, Landroid/content/IntentFilter;

    const-string v5, "android.intent.action.HOME_READY"

    invoke-direct {v4, v5}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 194
    .local v4, "homeReadyFilter":Landroid/content/IntentFilter;
    invoke-virtual {v4, v3}, Landroid/content/IntentFilter;->setPriority(I)V

    .line 195
    new-instance v3, Lcom/android/systemui/SystemUIApplication$2;

    invoke-direct {v3, p0}, Lcom/android/systemui/SystemUIApplication$2;-><init>(Lcom/android/systemui/SystemUIApplication;)V

    invoke-virtual {p0, v3, v4}, Lcom/android/systemui/SystemUIApplication;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 207
    .end local v4    # "homeReadyFilter":Landroid/content/IntentFilter;
    :cond_3
    new-instance v3, Landroid/content/IntentFilter;

    const-string v4, "android.intent.action.LOCALE_CHANGED"

    invoke-direct {v3, v4}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 208
    .local v3, "localeChangedFilter":Landroid/content/IntentFilter;
    new-instance v4, Lcom/android/systemui/SystemUIApplication$3;

    invoke-direct {v4, p0}, Lcom/android/systemui/SystemUIApplication$3;-><init>(Lcom/android/systemui/SystemUIApplication;)V

    invoke-virtual {p0, v4, v3}, Lcom/android/systemui/SystemUIApplication;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 218
    .end local v2    # "bootCompletedFilter":Landroid/content/IntentFilter;
    .end local v3    # "localeChangedFilter":Landroid/content/IntentFilter;
    goto :goto_0

    .line 221
    :cond_4
    invoke-static {}, Landroid/app/ActivityThread;->currentProcessName()Ljava/lang/String;

    move-result-object v2

    .line 222
    .local v2, "processName":Ljava/lang/String;
    invoke-virtual {p0}, Lcom/android/systemui/SystemUIApplication;->getApplicationInfo()Landroid/content/pm/ApplicationInfo;

    move-result-object v3

    .line 223
    .local v3, "info":Landroid/content/pm/ApplicationInfo;
    if-eqz v2, :cond_5

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v5, v3, Landroid/content/pm/ApplicationInfo;->processName:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, ":"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_5

    .line 224
    return-void

    .line 230
    :cond_5
    invoke-virtual {p0}, Lcom/android/systemui/SystemUIApplication;->startSecondaryUserServicesIfNeeded()V

    .line 232
    .end local v2    # "processName":Ljava/lang/String;
    .end local v3    # "info":Landroid/content/pm/ApplicationInfo;
    :goto_0
    return-void
.end method

.method startSecondaryUserServicesIfNeeded()V
    .locals 2

    .line 328
    nop

    .line 329
    invoke-virtual {p0}, Lcom/android/systemui/SystemUIApplication;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f03002a

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v0

    .line 330
    .local v0, "names":[Ljava/lang/String;
    invoke-direct {p0, v0}, Lcom/android/systemui/SystemUIApplication;->startServicesIfNeeded([Ljava/lang/String;)V

    .line 331
    return-void
.end method

.method public startServicesIfNeeded()V
    .locals 2

    .line 309
    const-string v0, "ro.atc.boot_time"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 310
    invoke-virtual {p0}, Lcom/android/systemui/SystemUIApplication;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f03002c

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v0

    .local v0, "names":[Ljava/lang/String;
    goto :goto_0

    .line 312
    .end local v0    # "names":[Ljava/lang/String;
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/SystemUIApplication;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f030029

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v0

    .line 314
    .restart local v0    # "names":[Ljava/lang/String;
    :goto_0
    invoke-direct {p0, v0}, Lcom/android/systemui/SystemUIApplication;->startServicesIfNeeded([Ljava/lang/String;)V

    .line 315
    return-void
.end method
