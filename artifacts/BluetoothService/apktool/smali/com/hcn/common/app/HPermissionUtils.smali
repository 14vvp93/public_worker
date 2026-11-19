.class public Lcom/hcn/common/app/HPermissionUtils;
.super Ljava/lang/Object;
.source "HPermissionUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;,
        Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;,
        Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;,
        Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;,
        Lcom/hcn/common/app/HPermissionUtils$SingleCallback;,
        Lcom/hcn/common/app/HPermissionUtils$FullCallback;,
        Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;
    }
.end annotation


# static fields
.field private static sInstance:Lcom/hcn/common/app/HPermissionUtils;

.field private static sSimpleCallback4DrawOverlays:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

.field private static sSimpleCallback4WriteSettings:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;


# instance fields
.field private mFullCallback:Lcom/hcn/common/app/HPermissionUtils$FullCallback;

.field private mOnExplainListener:Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;

.field private mOnRationaleListener:Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;

.field private mPermissions:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mPermissionsDenied:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mPermissionsDeniedForever:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mPermissionsGranted:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mPermissionsParam:[Ljava/lang/String;

.field private mPermissionsRequest:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mSimpleCallback:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

.field private mSingleCallback:Lcom/hcn/common/app/HPermissionUtils$SingleCallback;

.field private mThemeCallback:Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;


# direct methods
.method static bridge synthetic -$$Nest$fgetmOnExplainListener(Lcom/hcn/common/app/HPermissionUtils;)Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnExplainListener:Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmThemeCallback(Lcom/hcn/common/app/HPermissionUtils;)Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/app/HPermissionUtils;->mThemeCallback:Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fputmOnExplainListener(Lcom/hcn/common/app/HPermissionUtils;Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;)V
    .locals 0

    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnExplainListener:Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;

    return-void
.end method

.method static bridge synthetic -$$Nest$fputmPermissionsDenied(Lcom/hcn/common/app/HPermissionUtils;Ljava/util/List;)V
    .locals 0

    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    return-void
.end method

.method static bridge synthetic -$$Nest$fputmPermissionsDeniedForever(Lcom/hcn/common/app/HPermissionUtils;Ljava/util/List;)V
    .locals 0

    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDeniedForever:Ljava/util/List;

    return-void
.end method

.method static bridge synthetic -$$Nest$monRequestPermissionsResult(Lcom/hcn/common/app/HPermissionUtils;Landroid/app/Activity;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/app/HPermissionUtils;->onRequestPermissionsResult(Landroid/app/Activity;)V

    return-void
.end method

.method static bridge synthetic -$$Nest$mrequestCallback(Lcom/hcn/common/app/HPermissionUtils;)V
    .locals 0

    invoke-direct {p0}, Lcom/hcn/common/app/HPermissionUtils;->requestCallback()V

    return-void
.end method

.method static bridge synthetic -$$Nest$mshouldRationale(Lcom/hcn/common/app/HPermissionUtils;Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)Z
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/hcn/common/app/HPermissionUtils;->shouldRationale(Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)Z

    move-result p0

    return p0
.end method

.method static bridge synthetic -$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;
    .locals 1

    sget-object v0, Lcom/hcn/common/app/HPermissionUtils;->sInstance:Lcom/hcn/common/app/HPermissionUtils;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$sfgetsSimpleCallback4DrawOverlays()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;
    .locals 1

    sget-object v0, Lcom/hcn/common/app/HPermissionUtils;->sSimpleCallback4DrawOverlays:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$sfgetsSimpleCallback4WriteSettings()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;
    .locals 1

    sget-object v0, Lcom/hcn/common/app/HPermissionUtils;->sSimpleCallback4WriteSettings:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$sfputsSimpleCallback4DrawOverlays(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)V
    .locals 0

    sput-object p0, Lcom/hcn/common/app/HPermissionUtils;->sSimpleCallback4DrawOverlays:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    return-void
.end method

.method static bridge synthetic -$$Nest$sfputsSimpleCallback4WriteSettings(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)V
    .locals 0

    sput-object p0, Lcom/hcn/common/app/HPermissionUtils;->sSimpleCallback4WriteSettings:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    return-void
.end method

.method static bridge synthetic -$$Nest$smstartOverlayPermissionActivity(Landroid/app/Activity;I)V
    .locals 0

    invoke-static {p0, p1}, Lcom/hcn/common/app/HPermissionUtils;->startOverlayPermissionActivity(Landroid/app/Activity;I)V

    return-void
.end method

.method static bridge synthetic -$$Nest$smstartWriteSettingsActivity(Landroid/app/Activity;I)V
    .locals 0

    invoke-static {p0, p1}, Lcom/hcn/common/app/HPermissionUtils;->startWriteSettingsActivity(Landroid/app/Activity;I)V

    return-void
.end method

.method private varargs constructor <init>([Ljava/lang/String;)V
    .locals 0

    .line 238
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 239
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsParam:[Ljava/lang/String;

    .line 240
    sput-object p0, Lcom/hcn/common/app/HPermissionUtils;->sInstance:Lcom/hcn/common/app/HPermissionUtils;

    return-void
.end method

.method public static getPermissions()Ljava/util/List;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 63
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->getPermissions(Ljava/lang/String;)Ljava/util/List;

    move-result-object v0

    return-object v0
.end method

.method public static getPermissions(Ljava/lang/String;)Ljava/util/List;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 73
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Application;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    const/16 v1, 0x1000

    .line 75
    :try_start_0
    invoke-virtual {v0, p0, v1}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object p0

    iget-object p0, p0, Landroid/content/pm/PackageInfo;->requestedPermissions:[Ljava/lang/String;

    if-nez p0, :cond_0

    .line 77
    invoke-static {}, Ljava/util/Collections;->emptyList()Ljava/util/List;

    move-result-object p0

    return-object p0

    .line 79
    :cond_0
    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    :catch_0
    move-exception p0

    .line 81
    invoke-virtual {p0}, Landroid/content/pm/PackageManager$NameNotFoundException;->printStackTrace()V

    .line 82
    invoke-static {}, Ljava/util/Collections;->emptyList()Ljava/util/List;

    move-result-object p0

    return-object p0
.end method

.method private getPermissionsStatus(Landroid/app/Activity;)V
    .locals 3

    .line 390
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 391
    invoke-static {v1}, Lcom/hcn/common/app/HPermissionUtils;->isGranted(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 392
    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 394
    :cond_1
    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 395
    invoke-virtual {p1, v1}, Landroid/app/Activity;->shouldShowRequestPermissionRationale(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 396
    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDeniedForever:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_2
    return-void
.end method

.method private static varargs getRequestAndDeniedPermissions([Ljava/lang/String;)Landroid/util/Pair;
    .locals 13
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "([",
            "Ljava/lang/String;",
            ")",
            "Landroid/util/Pair<",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation

    .line 108
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 109
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 110
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->getPermissions()Ljava/util/List;

    move-result-object v2

    .line 111
    array-length v3, p0

    const/4 v4, 0x0

    move v5, v4

    :goto_0
    if-ge v5, v3, :cond_3

    aget-object v6, p0, v5

    .line 113
    invoke-static {v6}, Lcom/hcn/common/constant/HPermissionConstants;->getPermissions(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    .line 114
    array-length v8, v7

    move v9, v4

    move v10, v9

    :goto_1
    if-ge v9, v8, :cond_1

    aget-object v11, v7, v9

    .line 115
    invoke-interface {v2, v11}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_0

    .line 116
    invoke-interface {v0, v11}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const/4 v10, 0x1

    :cond_0
    add-int/lit8 v9, v9, 0x1

    goto :goto_1

    :cond_1
    if-nez v10, :cond_2

    .line 121
    invoke-interface {v1, v6}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 122
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "U should add the permission of "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " in manifest."

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    const-string v7, "PermissionUtils"

    invoke-static {v7, v6}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_2
    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 125
    :cond_3
    invoke-static {v0, v1}, Landroid/util/Pair;->create(Ljava/lang/Object;Ljava/lang/Object;)Landroid/util/Pair;

    move-result-object p0

    return-object p0
.end method

.method private static isGranted(Ljava/lang/String;)Z
    .locals 2

    .line 130
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x17

    if-lt v0, v1, :cond_1

    .line 132
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0, p0}, Landroidx/core/content/ContextCompat;->checkSelfPermission(Landroid/content/Context;Ljava/lang/String;)I

    move-result p0

    if-nez p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public static varargs isGranted([Ljava/lang/String;)Z
    .locals 2

    .line 93
    invoke-static {p0}, Lcom/hcn/common/app/HPermissionUtils;->getRequestAndDeniedPermissions([Ljava/lang/String;)Landroid/util/Pair;

    move-result-object p0

    .line 94
    iget-object v0, p0, Landroid/util/Pair;->second:Ljava/lang/Object;

    check-cast v0, Ljava/util/List;

    .line 95
    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    .line 98
    :cond_0
    iget-object p0, p0, Landroid/util/Pair;->first:Ljava/lang/Object;

    check-cast p0, Ljava/util/List;

    .line 99
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_1
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    .line 100
    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->isGranted(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_1

    return v1

    :cond_2
    const/4 p0, 0x1

    return p0
.end method

.method public static isGrantedDrawOverlays()Z
    .locals 1

    .line 179
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0}, Landroid/provider/Settings;->canDrawOverlays(Landroid/content/Context;)Z

    move-result v0

    return v0
.end method

.method public static isGrantedWriteSettings()Z
    .locals 1

    .line 143
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0}, Landroid/provider/Settings$System;->canWrite(Landroid/content/Context;)Z

    move-result v0

    return v0
.end method

.method public static launchAppDetailsSettings()V
    .locals 2

    .line 211
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/hcn/common/app/HIntentUtils;->getLaunchAppDetailsSettingsIntent(Ljava/lang/String;Z)Landroid/content/Intent;

    move-result-object v0

    .line 212
    invoke-static {v0}, Lcom/hcn/common/app/HIntentUtils;->isIntentAvailable(Landroid/content/Intent;)Z

    move-result v1

    if-nez v1, :cond_0

    return-void

    .line 215
    :cond_0
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/app/Application;->startActivity(Landroid/content/Intent;)V

    return-void
.end method

.method private onRequestPermissionsResult(Landroid/app/Activity;)V
    .locals 0

    .line 431
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HPermissionUtils;->getPermissionsStatus(Landroid/app/Activity;)V

    .line 432
    invoke-direct {p0}, Lcom/hcn/common/app/HPermissionUtils;->requestCallback()V

    return-void
.end method

.method public static varargs permission([Ljava/lang/String;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 1

    .line 235
    new-instance v0, Lcom/hcn/common/app/HPermissionUtils;

    invoke-direct {v0, p0}, Lcom/hcn/common/app/HPermissionUtils;-><init>([Ljava/lang/String;)V

    return-object v0
.end method

.method public static varargs permissionGroup([Ljava/lang/String;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 225
    invoke-static {p0}, Lcom/hcn/common/app/HPermissionUtils;->permission([Ljava/lang/String;)Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p0

    return-object p0
.end method

.method private rationalInner(Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)V
    .locals 2

    .line 373
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HPermissionUtils;->getPermissionsStatus(Landroid/app/Activity;)V

    .line 374
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnRationaleListener:Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;

    new-instance v1, Lcom/hcn/common/app/HPermissionUtils$1;

    invoke-direct {v1, p0, p2, p1}, Lcom/hcn/common/app/HPermissionUtils$1;-><init>(Lcom/hcn/common/app/HPermissionUtils;Ljava/lang/Runnable;Lcom/hcn/common/app/HUtilsTransActivity;)V

    invoke-interface {v0, p1, v1}, Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;->rationale(Lcom/hcn/common/app/HUtilsTransActivity;Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener$ShouldRequest;)V

    return-void
.end method

.method private requestCallback()V
    .locals 6

    .line 403
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mSingleCallback:Lcom/hcn/common/app/HPermissionUtils$SingleCallback;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 404
    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->isEmpty()Z

    move-result v2

    iget-object v3, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    iget-object v4, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDeniedForever:Ljava/util/List;

    iget-object v5, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    invoke-interface {v0, v2, v3, v4, v5}, Lcom/hcn/common/app/HPermissionUtils$SingleCallback;->callback(ZLjava/util/List;Ljava/util/List;Ljava/util/List;)V

    .line 406
    iput-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mSingleCallback:Lcom/hcn/common/app/HPermissionUtils$SingleCallback;

    .line 408
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mSimpleCallback:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    if-eqz v0, :cond_2

    .line 409
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 410
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mSimpleCallback:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    invoke-interface {v0}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onGranted()V

    goto :goto_0

    .line 412
    :cond_1
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mSimpleCallback:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    invoke-interface {v0}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onDenied()V

    .line 414
    :goto_0
    iput-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mSimpleCallback:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    .line 416
    :cond_2
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mFullCallback:Lcom/hcn/common/app/HPermissionUtils$FullCallback;

    if-eqz v0, :cond_6

    .line 417
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    .line 418
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-lez v0, :cond_4

    .line 419
    :cond_3
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mFullCallback:Lcom/hcn/common/app/HPermissionUtils$FullCallback;

    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    invoke-interface {v0, v2}, Lcom/hcn/common/app/HPermissionUtils$FullCallback;->onGranted(Ljava/util/List;)V

    .line 421
    :cond_4
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_5

    .line 422
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mFullCallback:Lcom/hcn/common/app/HPermissionUtils$FullCallback;

    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDeniedForever:Ljava/util/List;

    iget-object v3, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    invoke-interface {v0, v2, v3}, Lcom/hcn/common/app/HPermissionUtils$FullCallback;->onDenied(Ljava/util/List;Ljava/util/List;)V

    .line 424
    :cond_5
    iput-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mFullCallback:Lcom/hcn/common/app/HPermissionUtils$FullCallback;

    .line 426
    :cond_6
    iput-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnRationaleListener:Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;

    .line 427
    iput-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mThemeCallback:Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;

    return-void
.end method

.method public static requestDrawOverlays(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)V
    .locals 1

    .line 185
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->isGrantedDrawOverlays()Z

    move-result v0

    if-eqz v0, :cond_1

    if-eqz p0, :cond_0

    .line 187
    invoke-interface {p0}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onGranted()V

    :cond_0
    return-void

    .line 191
    :cond_1
    sput-object p0, Lcom/hcn/common/app/HPermissionUtils;->sSimpleCallback4DrawOverlays:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    const/4 p0, 0x3

    .line 192
    invoke-static {p0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->start(I)V

    return-void
.end method

.method public static requestWriteSettings(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)V
    .locals 1

    .line 149
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->isGrantedWriteSettings()Z

    move-result v0

    if-eqz v0, :cond_1

    if-eqz p0, :cond_0

    .line 151
    invoke-interface {p0}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onGranted()V

    :cond_0
    return-void

    .line 155
    :cond_1
    sput-object p0, Lcom/hcn/common/app/HPermissionUtils;->sSimpleCallback4WriteSettings:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    const/4 p0, 0x2

    .line 156
    invoke-static {p0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->start(I)V

    return-void
.end method

.method private shouldRationale(Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)Z
    .locals 3

    .line 359
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnRationaleListener:Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    .line 360
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 361
    invoke-virtual {p1, v2}, Lcom/hcn/common/app/HUtilsTransActivity;->shouldShowRequestPermissionRationale(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 362
    invoke-direct {p0, p1, p2}, Lcom/hcn/common/app/HPermissionUtils;->rationalInner(Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)V

    const/4 p1, 0x1

    move v1, p1

    :cond_1
    const/4 p1, 0x0

    .line 367
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnRationaleListener:Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;

    :cond_2
    return v1
.end method

.method private static startOverlayPermissionActivity(Landroid/app/Activity;I)V
    .locals 3

    .line 198
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.settings.action.MANAGE_OVERLAY_PERMISSION"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 199
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "package:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;

    .line 200
    invoke-static {v0}, Lcom/hcn/common/app/HIntentUtils;->isIntentAvailable(Landroid/content/Intent;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 201
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->launchAppDetailsSettings()V

    return-void

    .line 204
    :cond_0
    invoke-virtual {p0, v0, p1}, Landroid/app/Activity;->startActivityForResult(Landroid/content/Intent;I)V

    return-void
.end method

.method private startPermissionActivity()V
    .locals 1

    const/4 v0, 0x1

    .line 351
    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->start(I)V

    return-void
.end method

.method private static startWriteSettingsActivity(Landroid/app/Activity;I)V
    .locals 3

    .line 162
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.settings.action.MANAGE_WRITE_SETTINGS"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 163
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "package:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;

    .line 164
    invoke-static {v0}, Lcom/hcn/common/app/HIntentUtils;->isIntentAvailable(Landroid/content/Intent;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 165
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->launchAppDetailsSettings()V

    return-void

    .line 168
    :cond_0
    invoke-virtual {p0, v0, p1}, Landroid/app/Activity;->startActivityForResult(Landroid/content/Intent;I)V

    return-void
.end method


# virtual methods
.method public callback(Lcom/hcn/common/app/HPermissionUtils$FullCallback;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 294
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mFullCallback:Lcom/hcn/common/app/HPermissionUtils$FullCallback;

    return-object p0
.end method

.method public callback(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 283
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mSimpleCallback:Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    return-object p0
.end method

.method public callback(Lcom/hcn/common/app/HPermissionUtils$SingleCallback;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 272
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mSingleCallback:Lcom/hcn/common/app/HPermissionUtils$SingleCallback;

    return-object p0
.end method

.method public explain(Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 250
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnExplainListener:Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;

    return-object p0
.end method

.method public rationale(Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 261
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mOnRationaleListener:Lcom/hcn/common/app/HPermissionUtils$OnRationaleListener;

    return-object p0
.end method

.method public request()V
    .locals 3

    .line 314
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsParam:[Ljava/lang/String;

    if-eqz v0, :cond_5

    array-length v0, v0

    if-gtz v0, :cond_0

    goto/16 :goto_2

    .line 319
    :cond_0
    new-instance v0, Ljava/util/LinkedHashSet;

    invoke-direct {v0}, Ljava/util/LinkedHashSet;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissions:Ljava/util/Set;

    .line 320
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    .line 321
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    .line 322
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    .line 323
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDeniedForever:Ljava/util/List;

    .line 325
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsParam:[Ljava/lang/String;

    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->getRequestAndDeniedPermissions([Ljava/lang/String;)Landroid/util/Pair;

    move-result-object v0

    .line 326
    iget-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissions:Ljava/util/Set;

    iget-object v2, v0, Landroid/util/Pair;->first:Ljava/lang/Object;

    check-cast v2, Ljava/util/Collection;

    invoke-interface {v1, v2}, Ljava/util/Set;->addAll(Ljava/util/Collection;)Z

    .line 327
    iget-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsDenied:Ljava/util/List;

    iget-object v0, v0, Landroid/util/Pair;->second:Ljava/lang/Object;

    check-cast v0, Ljava/util/Collection;

    invoke-interface {v1, v0}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    .line 329
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x17

    if-ge v0, v1, :cond_1

    .line 330
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    iget-object v1, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissions:Ljava/util/Set;

    invoke-interface {v0, v1}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    .line 331
    invoke-direct {p0}, Lcom/hcn/common/app/HPermissionUtils;->requestCallback()V

    goto :goto_1

    .line 333
    :cond_1
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissions:Ljava/util/Set;

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 334
    invoke-static {v1}, Lcom/hcn/common/app/HPermissionUtils;->isGranted(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 335
    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsGranted:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 337
    :cond_2
    iget-object v2, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 340
    :cond_3
    iget-object v0, p0, Lcom/hcn/common/app/HPermissionUtils;->mPermissionsRequest:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_4

    .line 341
    invoke-direct {p0}, Lcom/hcn/common/app/HPermissionUtils;->requestCallback()V

    goto :goto_1

    .line 343
    :cond_4
    invoke-direct {p0}, Lcom/hcn/common/app/HPermissionUtils;->startPermissionActivity()V

    :goto_1
    return-void

    :cond_5
    :goto_2
    const-string v0, "PermissionUtils"

    const-string v1, "No permissions to request."

    .line 315
    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public theme(Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;)Lcom/hcn/common/app/HPermissionUtils;
    .locals 0

    .line 305
    iput-object p1, p0, Lcom/hcn/common/app/HPermissionUtils;->mThemeCallback:Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;

    return-object p0
.end method
