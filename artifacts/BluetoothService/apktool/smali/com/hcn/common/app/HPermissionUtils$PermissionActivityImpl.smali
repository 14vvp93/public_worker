.class final Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;
.super Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;
.source "HPermissionUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HPermissionUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "PermissionActivityImpl"
.end annotation


# static fields
.field private static INSTANCE:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl; = null

.field private static final TYPE:Ljava/lang/String; = "TYPE"

.field private static final TYPE_DRAW_OVERLAYS:I = 0x3

.field private static final TYPE_RUNTIME:I = 0x1

.field private static final TYPE_WRITE_SETTINGS:I = 0x2

.field private static currentRequestCode:I = -0x1


# direct methods
.method static bridge synthetic -$$Nest$mrequestPermissions(Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;Lcom/hcn/common/app/HUtilsTransActivity;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->requestPermissions(Lcom/hcn/common/app/HUtilsTransActivity;)V

    return-void
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 446
    new-instance v0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

    invoke-direct {v0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;-><init>()V

    sput-object v0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->INSTANCE:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

    return-void
.end method

.method constructor <init>()V
    .locals 0

    .line 437
    invoke-direct {p0}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;-><init>()V

    return-void
.end method

.method private checkRequestCallback(I)V
    .locals 2

    const/4 v0, 0x0

    const/4 v1, 0x2

    if-ne p1, v1, :cond_2

    .line 553
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsSimpleCallback4WriteSettings()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    move-result-object p1

    if-nez p1, :cond_0

    return-void

    .line 557
    :cond_0
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->isGrantedWriteSettings()Z

    move-result p1

    if-eqz p1, :cond_1

    .line 558
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsSimpleCallback4WriteSettings()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    move-result-object p1

    invoke-interface {p1}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onGranted()V

    goto :goto_0

    .line 560
    :cond_1
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsSimpleCallback4WriteSettings()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    move-result-object p1

    invoke-interface {p1}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onDenied()V

    .line 562
    :goto_0
    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfputsSimpleCallback4WriteSettings(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)V

    goto :goto_2

    :cond_2
    const/4 v1, 0x3

    if-ne p1, v1, :cond_5

    .line 564
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsSimpleCallback4DrawOverlays()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    move-result-object p1

    if-nez p1, :cond_3

    return-void

    .line 568
    :cond_3
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->isGrantedDrawOverlays()Z

    move-result p1

    if-eqz p1, :cond_4

    .line 569
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsSimpleCallback4DrawOverlays()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    move-result-object p1

    invoke-interface {p1}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onGranted()V

    goto :goto_1

    .line 571
    :cond_4
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsSimpleCallback4DrawOverlays()Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;

    move-result-object p1

    invoke-interface {p1}, Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;->onDenied()V

    .line 573
    :goto_1
    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfputsSimpleCallback4DrawOverlays(Lcom/hcn/common/app/HPermissionUtils$SimpleCallback;)V

    :cond_5
    :goto_2
    return-void
.end method

.method private requestPermissions(Lcom/hcn/common/app/HUtilsTransActivity;)V
    .locals 2

    .line 509
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object v0

    new-instance v1, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$3;

    invoke-direct {v1, p0, p1}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$3;-><init>(Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;Lcom/hcn/common/app/HUtilsTransActivity;)V

    invoke-static {v0, p1, v1}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$mshouldRationale(Lcom/hcn/common/app/HPermissionUtils;Lcom/hcn/common/app/HUtilsTransActivity;Ljava/lang/Runnable;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 517
    :cond_0
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;

    move-result-object v0

    const/4 v1, 0x0

    new-array v1, v1, [Ljava/lang/String;

    invoke-interface {v0, v1}, Ljava/util/List;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Ljava/lang/String;

    const/4 v1, 0x1

    invoke-virtual {p1, v0, v1}, Lcom/hcn/common/app/HUtilsTransActivity;->requestPermissions([Ljava/lang/String;I)V

    return-void
.end method

.method public static start(I)V
    .locals 1

    .line 449
    new-instance v0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$1;

    invoke-direct {v0, p0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$1;-><init>(I)V

    sget-object p0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->INSTANCE:Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;

    invoke-static {v0, p0}, Lcom/hcn/common/app/HUtilsTransActivity;->start(Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;)V

    return-void
.end method


# virtual methods
.method public dispatchTouchEvent(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/view/MotionEvent;)Z
    .locals 0

    .line 533
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    const/4 p1, 0x1

    return p1
.end method

.method public onActivityResult(Lcom/hcn/common/app/HUtilsTransActivity;IILandroid/content/Intent;)V
    .locals 0

    .line 548
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    return-void
.end method

.method public onCreated(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/os/Bundle;)V
    .locals 2

    .line 459
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->getWindow()Landroid/view/Window;

    move-result-object p2

    const v0, 0x40010

    invoke-virtual {p2, v0}, Landroid/view/Window;->addFlags(I)V

    .line 461
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->getIntent()Landroid/content/Intent;

    move-result-object p2

    const-string v0, "TYPE"

    const/4 v1, -0x1

    invoke-virtual {p2, v0, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p2

    const-string v0, "PermissionUtils"

    const/4 v1, 0x1

    if-ne p2, v1, :cond_5

    .line 463
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    if-nez p2, :cond_0

    const-string p2, "sInstance is null."

    .line 464
    invoke-static {v0, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 465
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    return-void

    .line 468
    :cond_0
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;

    move-result-object p2

    if-nez p2, :cond_1

    const-string p2, "mPermissionsRequest is null."

    .line 469
    invoke-static {v0, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 470
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    return-void

    .line 473
    :cond_1
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;

    move-result-object p2

    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result p2

    if-gtz p2, :cond_2

    const-string p2, "mPermissionsRequest\'s size is no more than 0."

    .line 474
    invoke-static {v0, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 475
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    return-void

    .line 478
    :cond_2
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmThemeCallback(Lcom/hcn/common/app/HPermissionUtils;)Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;

    move-result-object p2

    if-eqz p2, :cond_3

    .line 479
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmThemeCallback(Lcom/hcn/common/app/HPermissionUtils;)Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;

    move-result-object p2

    invoke-interface {p2, p1}, Lcom/hcn/common/app/HPermissionUtils$ThemeCallback;->onActivityCreate(Landroid/app/Activity;)V

    .line 481
    :cond_3
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmOnExplainListener(Lcom/hcn/common/app/HPermissionUtils;)Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;

    move-result-object p2

    if-eqz p2, :cond_4

    .line 482
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmOnExplainListener(Lcom/hcn/common/app/HPermissionUtils;)Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;

    move-result-object p2

    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;

    move-result-object v0

    new-instance v1, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;

    invoke-direct {v1, p0, p1}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl$2;-><init>(Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;Lcom/hcn/common/app/HUtilsTransActivity;)V

    invoke-interface {p2, p1, v0, v1}, Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;->explain(Lcom/hcn/common/app/HUtilsTransActivity;Ljava/util/List;Lcom/hcn/common/app/HPermissionUtils$OnExplainListener$ShouldRequest;)V

    .line 492
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p1

    const/4 p2, 0x0

    invoke-static {p1, p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fputmOnExplainListener(Lcom/hcn/common/app/HPermissionUtils;Lcom/hcn/common/app/HPermissionUtils$OnExplainListener;)V

    return-void

    .line 495
    :cond_4
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->requestPermissions(Lcom/hcn/common/app/HUtilsTransActivity;)V

    goto :goto_0

    :cond_5
    const/4 v1, 0x2

    if-ne p2, v1, :cond_6

    .line 497
    sput v1, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->currentRequestCode:I

    .line 498
    invoke-static {p1, v1}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$smstartWriteSettingsActivity(Landroid/app/Activity;I)V

    goto :goto_0

    :cond_6
    const/4 v1, 0x3

    if-ne p2, v1, :cond_7

    .line 500
    sput v1, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->currentRequestCode:I

    .line 501
    invoke-static {p1, v1}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$smstartOverlayPermissionActivity(Landroid/app/Activity;I)V

    goto :goto_0

    .line 503
    :cond_7
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    const-string p1, "type is wrong."

    .line 504
    invoke-static {v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    return-void
.end method

.method public onDestroy(Lcom/hcn/common/app/HUtilsTransActivity;)V
    .locals 2

    .line 539
    sget v0, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->currentRequestCode:I

    const/4 v1, -0x1

    if-eq v0, v1, :cond_0

    .line 540
    invoke-direct {p0, v0}, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->checkRequestCallback(I)V

    .line 541
    sput v1, Lcom/hcn/common/app/HPermissionUtils$PermissionActivityImpl;->currentRequestCode:I

    .line 543
    :cond_0
    invoke-super {p0, p1}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onDestroy(Lcom/hcn/common/app/HUtilsTransActivity;)V

    return-void
.end method

.method public onRequestPermissionsResult(Lcom/hcn/common/app/HUtilsTransActivity;I[Ljava/lang/String;[I)V
    .locals 0

    .line 525
    invoke-virtual {p1}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    .line 526
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    if-eqz p2, :cond_0

    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$fgetmPermissionsRequest(Lcom/hcn/common/app/HPermissionUtils;)Ljava/util/List;

    move-result-object p2

    if-eqz p2, :cond_0

    .line 527
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$sfgetsInstance()Lcom/hcn/common/app/HPermissionUtils;

    move-result-object p2

    invoke-static {p2, p1}, Lcom/hcn/common/app/HPermissionUtils;->-$$Nest$monRequestPermissionsResult(Lcom/hcn/common/app/HPermissionUtils;Landroid/app/Activity;)V

    :cond_0
    return-void
.end method
