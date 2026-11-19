.class public Lcom/hcn/skin2/Skin2;
.super Ljava/lang/Object;
.source "Skin2.java"


# static fields
.field private static final SKIN2_LOG_LEVEL_PROP:Ljava/lang/String; = "persist.sys.skin2.log"

.field private static final TAG:Ljava/lang/String; = "Skin2"

.field private static sInstance:Lcom/hcn/skin2/Skin2;


# instance fields
.field private mContextRef:Ljava/lang/ref/Reference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/Reference<",
            "Landroid/content/Context;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>()V
    .locals 3

    .line 81
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 83
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/hcn/skin2/Skin2;->mContextRef:Ljava/lang/ref/Reference;

    .line 86
    invoke-static {}, Lcom/hcn/skin2/Skin2;->skin2LogLevel()I

    move-result v0

    .line 87
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->getConfig()Lcom/hcn/common/misc/LogUtils$Config;

    move-result-object v1

    .line 88
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->isAppDebug()Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 v0, 0x2

    .line 87
    :cond_0
    invoke-virtual {v1, v0}, Lcom/hcn/common/misc/LogUtils$Config;->setConsoleFilter(I)Lcom/hcn/common/misc/LogUtils$Config;

    .line 89
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->getConfig()Lcom/hcn/common/misc/LogUtils$Config;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/hcn/common/misc/LogUtils$Config;->setLogHeadSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;

    .line 90
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->getConfig()Lcom/hcn/common/misc/LogUtils$Config;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/hcn/common/misc/LogUtils$Config;->setBorderSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;

    .line 93
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/skin2/runtime/RRO;->initOverlayManager(Landroid/content/Context;)V

    .line 94
    const-class v0, Lcom/hcn/skin2/enhance/SkinExCompatResources;

    invoke-static {v0}, Lcom/hcn/skin/support/SkinResourcesConfig;->setSkinResourcesClass(Ljava/lang/Class;)V

    .line 95
    invoke-static {}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->asyncLoadSupportEnable()V

    .line 96
    invoke-static {}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->instance()Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/skin/support/SkinCompatManager;->setSkinLayoutFactory2(Landroid/view/LayoutInflater$Factory2;)V

    .line 97
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/skin/support/SkinCompatManager;->create(Landroid/content/Context;)Lcom/hcn/skin/support/SkinCompatManager;

    return-void
.end method

.method public static getIntArray(I)[I
    .locals 1

    .line 342
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 343
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getIntArray(I)[I

    move-result-object p0

    return-object p0

    .line 345
    :cond_0
    invoke-static {}, Lcom/hcn/skin2/Skin2;->instance()Lcom/hcn/skin2/Skin2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/Skin2;->context()Landroid/content/Context;

    move-result-object v0

    .line 346
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/content/res/Resources;->getIntArray(I)[I

    move-result-object p0

    return-object p0
.end method

.method public static getStringArray(I)[Ljava/lang/String;
    .locals 1

    .line 292
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 293
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 295
    :cond_0
    invoke-static {}, Lcom/hcn/skin2/Skin2;->instance()Lcom/hcn/skin2/Skin2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/Skin2;->context()Landroid/content/Context;

    move-result-object v0

    .line 296
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public static inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;
    .locals 3

    .line 366
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 367
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_0

    .line 369
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    .line 370
    invoke-static {}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->instance()Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->skinInflaterFactorySet(Landroid/view/LayoutInflater$Factory2;)V

    .line 371
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinContext()Landroid/content/Context;

    move-result-object v1

    .line 372
    invoke-static {v1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v1

    invoke-virtual {v1, v0, p1, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    .line 373
    sget-object p2, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    invoke-virtual {p1, p0, p2}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    return-object p1

    .line 378
    :cond_0
    invoke-static {}, Lcom/hcn/skin2/Skin2;->instance()Lcom/hcn/skin2/Skin2;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/Skin2;->context()Landroid/content/Context;

    move-result-object v0

    .line 379
    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    invoke-virtual {v0, p0, p1, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    .line 380
    sget-object p2, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;

    invoke-virtual {p1, p0, p2}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    return-object p1
.end method

.method public static init(Landroid/app/Application;)Lcom/hcn/skin2/Skin2;
    .locals 0

    .line 120
    invoke-static {p0}, Lcom/hcn/common/utils/HUtilsEx;->init(Landroid/app/Application;)V

    .line 121
    invoke-static {}, Lcom/hcn/skin2/Skin2;->instance()Lcom/hcn/skin2/Skin2;

    move-result-object p0

    return-object p0
.end method

.method public static instance()Lcom/hcn/skin2/Skin2;
    .locals 1

    .line 107
    sget-object v0, Lcom/hcn/skin2/Skin2;->sInstance:Lcom/hcn/skin2/Skin2;

    if-nez v0, :cond_0

    .line 108
    new-instance v0, Lcom/hcn/skin2/Skin2;

    invoke-direct {v0}, Lcom/hcn/skin2/Skin2;-><init>()V

    sput-object v0, Lcom/hcn/skin2/Skin2;->sInstance:Lcom/hcn/skin2/Skin2;

    .line 110
    :cond_0
    sget-object v0, Lcom/hcn/skin2/Skin2;->sInstance:Lcom/hcn/skin2/Skin2;

    return-object v0
.end method

.method private static skin2LogLevel()I
    .locals 9

    const-string v0, "persist.sys.skin2.log"

    const-string v1, "D"

    .line 50
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 52
    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    move-result v2

    const/16 v3, 0x44

    const/4 v4, 0x3

    const/4 v5, 0x6

    const/4 v6, 0x5

    const/4 v7, 0x4

    const/4 v8, 0x2

    if-eq v2, v3, :cond_9

    const/16 v1, 0x45

    if-eq v2, v1, :cond_8

    const/16 v1, 0x49

    if-eq v2, v1, :cond_7

    const/16 v1, 0x69

    if-eq v2, v1, :cond_6

    const/16 v1, 0x56

    if-eq v2, v1, :cond_5

    const/16 v1, 0x57

    if-eq v2, v1, :cond_4

    const/16 v1, 0x64

    if-eq v2, v1, :cond_3

    const/16 v1, 0x65

    if-eq v2, v1, :cond_2

    const/16 v1, 0x76

    if-eq v2, v1, :cond_1

    const/16 v1, 0x77

    if-eq v2, v1, :cond_0

    goto/16 :goto_0

    :cond_0
    const-string v1, "w"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    move v0, v7

    goto/16 :goto_1

    :cond_1
    const-string v1, "v"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    const/4 v0, 0x0

    goto :goto_1

    :cond_2
    const-string v1, "e"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    move v0, v5

    goto :goto_1

    :cond_3
    const-string v1, "d"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    const/16 v0, 0x8

    goto :goto_1

    :cond_4
    const-string v1, "W"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    move v0, v6

    goto :goto_1

    :cond_5
    const-string v1, "V"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    const/4 v0, 0x1

    goto :goto_1

    :cond_6
    const-string v1, "i"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    move v0, v8

    goto :goto_1

    :cond_7
    const-string v1, "I"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    move v0, v4

    goto :goto_1

    :cond_8
    const-string v1, "E"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    const/4 v0, 0x7

    goto :goto_1

    :cond_9
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    const/16 v0, 0x9

    goto :goto_1

    :cond_a
    :goto_0
    const/4 v0, -0x1

    :goto_1
    packed-switch v0, :pswitch_data_0

    return v4

    :pswitch_0
    return v5

    :pswitch_1
    return v6

    :pswitch_2
    return v7

    :pswitch_3
    return v8

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_3
        :pswitch_3
        :pswitch_2
        :pswitch_2
        :pswitch_1
        :pswitch_1
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public static skinContext()Landroid/content/Context;
    .locals 2

    .line 165
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 166
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinContext()Landroid/content/Context;

    move-result-object v0

    return-object v0

    .line 169
    :cond_0
    new-instance v0, Ljava/lang/RuntimeException;

    const-string v1, "Illegal call, extension skin pack not currently loaded!"

    invoke-direct {v0, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static useSkinPackage()Z
    .locals 1

    .line 141
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static xAppId(I)I
    .locals 6

    .line 193
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 194
    sget-object v0, Lcom/hcn/skin2/Skin2;->sInstance:Lcom/hcn/skin2/Skin2;

    invoke-virtual {v0}, Lcom/hcn/skin2/Skin2;->context()Landroid/content/Context;

    move-result-object v0

    .line 197
    :try_start_0
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p0}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    .line 200
    :catch_0
    sget-object v1, Lcom/hcn/skin2/Skin2;->TAG:Ljava/lang/String;

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Object;

    const-string v4, "It may be a skin package resource ID."

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-static {v1, v3}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 204
    :try_start_1
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    .line 205
    invoke-virtual {v1, p0}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v3

    .line 206
    invoke-virtual {v1, p0}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v1

    .line 208
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    .line 209
    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v4, v1, v3, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p0
    :try_end_1
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_1 .. :try_end_1} :catch_1

    return p0

    .line 212
    :catch_1
    sget-object v0, Lcom/hcn/skin2/Skin2;->TAG:Ljava/lang/String;

    new-array v1, v2, [Ljava/lang/Object;

    const-string v2, "This is an illegal resource ID."

    aput-object v2, v1, v5

    invoke-static {v0, v1}, Lcom/hcn/common/misc/LogUtils;->eTag(Ljava/lang/String;[Ljava/lang/Object;)V

    :cond_0
    return p0
.end method

.method public static xAppId4Skin(I)I
    .locals 4

    .line 233
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 234
    sget-object v0, Lcom/hcn/skin2/Skin2;->sInstance:Lcom/hcn/skin2/Skin2;

    invoke-virtual {v0}, Lcom/hcn/skin2/Skin2;->context()Landroid/content/Context;

    move-result-object v0

    .line 235
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    .line 238
    :try_start_0
    invoke-virtual {v1, p0}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v2

    .line 239
    invoke-virtual {v1, p0}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v1

    .line 240
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    .line 241
    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v3, v1, v2, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p0
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    .line 243
    :catch_0
    sget-object v0, Lcom/hcn/skin2/Skin2;->TAG:Ljava/lang/String;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "This is an illegal resource ID."

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lcom/hcn/common/misc/LogUtils;->eTag(Ljava/lang/String;[Ljava/lang/Object;)V

    :cond_0
    return p0
.end method

.method public static xId(I)I
    .locals 1

    .line 179
    invoke-static {}, Lcom/hcn/skin2/Skin2;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 180
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result p0

    :cond_0
    return p0
.end method

.method public static xIntArray(I)[I
    .locals 0

    .line 312
    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->xAppId(I)I

    move-result p0

    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->getIntArray(I)[I

    move-result-object p0

    return-object p0
.end method

.method public static xIntArray(IZ)[I
    .locals 0

    if-eqz p1, :cond_0

    .line 325
    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->xAppId4Skin(I)I

    move-result p0

    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->getIntArray(I)[I

    move-result-object p0

    return-object p0

    .line 327
    :cond_0
    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->getIntArray(I)[I

    move-result-object p0

    return-object p0
.end method

.method public static xStringArray(I)[Ljava/lang/String;
    .locals 0

    .line 262
    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->xAppId(I)I

    move-result p0

    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->getStringArray(I)[Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public static xStringArray(IZ)[Ljava/lang/String;
    .locals 0

    if-eqz p1, :cond_0

    .line 275
    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->xAppId4Skin(I)I

    move-result p0

    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->getStringArray(I)[Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 277
    :cond_0
    invoke-static {p0}, Lcom/hcn/skin2/Skin2;->getStringArray(I)[Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method


# virtual methods
.method public context()Landroid/content/Context;
    .locals 2

    .line 151
    iget-object v0, p0, Lcom/hcn/skin2/Skin2;->mContextRef:Ljava/lang/ref/Reference;

    invoke-virtual {v0}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    const-string v1, "The owner context of Skin2 is null!"

    .line 153
    invoke-static {v0, v1}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    return-object v0
.end method

.method public rroCallback(Lcom/hcn/skin2/enhance/IOverlayCallback;)Lcom/hcn/skin2/Skin2;
    .locals 0

    .line 132
    invoke-static {p1}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->setOverlayCallback(Lcom/hcn/skin2/enhance/IOverlayCallback;)V

    return-object p0
.end method
