.class public Lcom/hcn/common/app/HToastUtils;
.super Ljava/lang/Object;
.source "HToastUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/app/HToastUtils$MODE;,
        Lcom/hcn/common/app/HToastUtils$SystemToast;,
        Lcom/hcn/common/app/HToastUtils$WindowManagerToast;,
        Lcom/hcn/common/app/HToastUtils$ActivityToast;,
        Lcom/hcn/common/app/HToastUtils$UtilsMaxWidthRelativeLayout;,
        Lcom/hcn/common/app/HToastUtils$IToast;,
        Lcom/hcn/common/app/HToastUtils$AbsToast;
    }
.end annotation


# static fields
.field private static final COLOR_DEFAULT:I = -0x1000001

.field private static final DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

.field private static final NOTHING:Ljava/lang/String; = "toast nothing"

.field private static final NULL:Ljava/lang/String; = "toast null"

.field private static final TAG_TOAST:Ljava/lang/String; = "TAG_TOAST"

.field private static sWeakToast:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/hcn/common/app/HToastUtils$IToast;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private isLong:Z

.field private isNotUseSystemToast:Z

.field private mBgColor:I

.field private mBgResource:I

.field private mGravity:I

.field private mIcons:[Landroid/graphics/drawable/Drawable;

.field private mMode:Ljava/lang/String;

.field private mTextColor:I

.field private mTextSize:I

.field private mXOffset:I

.field private mYOffset:I


# direct methods
.method static bridge synthetic -$$Nest$fgetmBgColor(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mBgColor:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmBgResource(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mBgResource:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmGravity(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mGravity:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmTextColor(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mTextColor:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmTextSize(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mTextSize:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmXOffset(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mXOffset:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmYOffset(Lcom/hcn/common/app/HToastUtils;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/app/HToastUtils;->mYOffset:I

    return p0
.end method

.method static bridge synthetic -$$Nest$mtryApplyUtilsToastView(Lcom/hcn/common/app/HToastUtils;Ljava/lang/CharSequence;)Landroid/view/View;
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/app/HToastUtils;->tryApplyUtilsToastView(Ljava/lang/CharSequence;)Landroid/view/View;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$sfgetsWeakToast()Ljava/lang/ref/WeakReference;
    .locals 1

    sget-object v0, Lcom/hcn/common/app/HToastUtils;->sWeakToast:Ljava/lang/ref/WeakReference;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$sfputsWeakToast(Ljava/lang/ref/WeakReference;)V
    .locals 0

    sput-object p0, Lcom/hcn/common/app/HToastUtils;->sWeakToast:Ljava/lang/ref/WeakReference;

    return-void
.end method

.method static bridge synthetic -$$Nest$smnewToast(Lcom/hcn/common/app/HToastUtils;)Lcom/hcn/common/app/HToastUtils$IToast;
    .locals 0

    invoke-static {p0}, Lcom/hcn/common/app/HToastUtils;->newToast(Lcom/hcn/common/app/HToastUtils;)Lcom/hcn/common/app/HToastUtils$IToast;

    move-result-object p0

    return-object p0
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 71
    invoke-static {}, Lcom/hcn/common/app/HToastUtils;->make()Lcom/hcn/common/app/HToastUtils;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 58
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, -0x1

    .line 76
    iput v0, p0, Lcom/hcn/common/app/HToastUtils;->mGravity:I

    .line 77
    iput v0, p0, Lcom/hcn/common/app/HToastUtils;->mXOffset:I

    .line 78
    iput v0, p0, Lcom/hcn/common/app/HToastUtils;->mYOffset:I

    const v1, -0x1000001

    .line 79
    iput v1, p0, Lcom/hcn/common/app/HToastUtils;->mBgColor:I

    .line 80
    iput v0, p0, Lcom/hcn/common/app/HToastUtils;->mBgResource:I

    .line 81
    iput v1, p0, Lcom/hcn/common/app/HToastUtils;->mTextColor:I

    .line 82
    iput v0, p0, Lcom/hcn/common/app/HToastUtils;->mTextSize:I

    const/4 v0, 0x0

    .line 83
    iput-boolean v0, p0, Lcom/hcn/common/app/HToastUtils;->isLong:Z

    const/4 v1, 0x4

    new-array v1, v1, [Landroid/graphics/drawable/Drawable;

    .line 84
    iput-object v1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    .line 85
    iput-boolean v0, p0, Lcom/hcn/common/app/HToastUtils;->isNotUseSystemToast:Z

    return-void
.end method

.method public static cancel()V
    .locals 1

    .line 462
    new-instance v0, Lcom/hcn/common/app/HToastUtils$1;

    invoke-direct {v0}, Lcom/hcn/common/app/HToastUtils$1;-><init>()V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method public static getDefaultMaker()Lcom/hcn/common/app/HToastUtils;
    .locals 1

    .line 292
    sget-object v0, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    return-object v0
.end method

.method private getDuration()I
    .locals 1

    .line 341
    iget-boolean v0, p0, Lcom/hcn/common/app/HToastUtils;->isLong:Z

    return v0
.end method

.method private static getToastFriendlyText(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
    .locals 1

    if-nez p0, :cond_0

    const-string p0, "toast null"

    goto :goto_0

    .line 508
    :cond_0
    invoke-interface {p0}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-nez v0, :cond_1

    const-string p0, "toast nothing"

    :cond_1
    :goto_0
    return-object p0
.end method

.method public static make()Lcom/hcn/common/app/HToastUtils;
    .locals 1

    .line 94
    new-instance v0, Lcom/hcn/common/app/HToastUtils;

    invoke-direct {v0}, Lcom/hcn/common/app/HToastUtils;-><init>()V

    return-object v0
.end method

.method private static newToast(Lcom/hcn/common/app/HToastUtils;)Lcom/hcn/common/app/HToastUtils$IToast;
    .locals 2

    .line 516
    iget-boolean v0, p0, Lcom/hcn/common/app/HToastUtils;->isNotUseSystemToast:Z

    if-nez v0, :cond_1

    .line 517
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0}, Landroidx/core/app/NotificationManagerCompat;->from(Landroid/content/Context;)Landroidx/core/app/NotificationManagerCompat;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/core/app/NotificationManagerCompat;->areNotificationsEnabled()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 518
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x17

    if-ge v0, v1, :cond_0

    .line 519
    new-instance v0, Lcom/hcn/common/app/HToastUtils$SystemToast;

    invoke-direct {v0, p0}, Lcom/hcn/common/app/HToastUtils$SystemToast;-><init>(Lcom/hcn/common/app/HToastUtils;)V

    return-object v0

    .line 521
    :cond_0
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->isGrantedDrawOverlays()Z

    move-result v0

    if-nez v0, :cond_1

    .line 522
    new-instance v0, Lcom/hcn/common/app/HToastUtils$SystemToast;

    invoke-direct {v0, p0}, Lcom/hcn/common/app/HToastUtils$SystemToast;-><init>(Lcom/hcn/common/app/HToastUtils;)V

    return-object v0

    .line 528
    :cond_1
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x19

    if-ge v0, v1, :cond_2

    .line 529
    new-instance v0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;

    const/16 v1, 0x7d5

    invoke-direct {v0, p0, v1}, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;-><init>(Lcom/hcn/common/app/HToastUtils;I)V

    return-object v0

    .line 530
    :cond_2
    invoke-static {}, Lcom/hcn/common/app/HPermissionUtils;->isGrantedDrawOverlays()Z

    move-result v0

    if-eqz v0, :cond_4

    .line 531
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_3

    .line 532
    new-instance v0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;

    const/16 v1, 0x7f6

    invoke-direct {v0, p0, v1}, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;-><init>(Lcom/hcn/common/app/HToastUtils;I)V

    return-object v0

    .line 534
    :cond_3
    new-instance v0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;

    const/16 v1, 0x7d2

    invoke-direct {v0, p0, v1}, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;-><init>(Lcom/hcn/common/app/HToastUtils;I)V

    return-object v0

    .line 537
    :cond_4
    new-instance v0, Lcom/hcn/common/app/HToastUtils$ActivityToast;

    invoke-direct {v0, p0}, Lcom/hcn/common/app/HToastUtils$ActivityToast;-><init>(Lcom/hcn/common/app/HToastUtils;)V

    return-object v0
.end method

.method private static show(Landroid/view/View;ILcom/hcn/common/app/HToastUtils;)V
    .locals 1

    const/4 v0, 0x0

    .line 481
    invoke-static {p0, v0, p1, p2}, Lcom/hcn/common/app/HToastUtils;->show(Landroid/view/View;Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method private static show(Landroid/view/View;Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V
    .locals 1

    .line 488
    new-instance v0, Lcom/hcn/common/app/HToastUtils$2;

    invoke-direct {v0, p3, p0, p1, p2}, Lcom/hcn/common/app/HToastUtils$2;-><init>(Lcom/hcn/common/app/HToastUtils;Landroid/view/View;Ljava/lang/CharSequence;I)V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method private static show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V
    .locals 1

    .line 477
    invoke-static {p0}, Lcom/hcn/common/app/HToastUtils;->getToastFriendlyText(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object p0

    const/4 v0, 0x0

    invoke-static {v0, p0, p1, p2}, Lcom/hcn/common/app/HToastUtils;->show(Landroid/view/View;Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static showLong(I)V
    .locals 2

    .line 435
    invoke-static {p0}, Lcom/hcn/common/utils/HStringUtils;->getString(I)Ljava/lang/String;

    move-result-object p0

    sget-object v0, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v1, 0x1

    invoke-static {p0, v1, v0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static varargs showLong(I[Ljava/lang/Object;)V
    .locals 1

    .line 445
    invoke-static {p0, p1}, Lcom/hcn/common/utils/HStringUtils;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    sget-object p1, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v0, 0x1

    invoke-static {p0, v0, p1}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static showLong(Ljava/lang/CharSequence;)V
    .locals 2

    .line 426
    sget-object v0, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v1, 0x1

    invoke-static {p0, v1, v0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static varargs showLong(Ljava/lang/String;[Ljava/lang/Object;)V
    .locals 1

    .line 455
    invoke-static {p0, p1}, Lcom/hcn/common/utils/HStringUtils;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    sget-object p1, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v0, 0x1

    invoke-static {p0, v0, p1}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static showShort(I)V
    .locals 2

    .line 397
    invoke-static {p0}, Lcom/hcn/common/utils/HStringUtils;->getString(I)Ljava/lang/String;

    move-result-object p0

    sget-object v0, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v1, 0x0

    invoke-static {p0, v1, v0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static varargs showShort(I[Ljava/lang/Object;)V
    .locals 1

    .line 407
    invoke-static {p0, p1}, Lcom/hcn/common/utils/HStringUtils;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    sget-object p1, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v0, 0x0

    invoke-static {p0, v0, p1}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static showShort(Ljava/lang/CharSequence;)V
    .locals 2

    .line 388
    sget-object v0, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v1, 0x0

    invoke-static {p0, v1, v0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public static varargs showShort(Ljava/lang/String;[Ljava/lang/Object;)V
    .locals 1

    .line 417
    invoke-static {p0, p1}, Lcom/hcn/common/utils/HStringUtils;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    sget-object p1, Lcom/hcn/common/app/HToastUtils;->DEFAULT_MAKER:Lcom/hcn/common/app/HToastUtils;

    const/4 v0, 0x0

    invoke-static {p0, v0, p1}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method private tryApplyUtilsToastView(Ljava/lang/CharSequence;)Landroid/view/View;
    .locals 8

    .line 345
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mMode:Ljava/lang/String;

    const-string v1, "dark"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v2, 0x3

    const/4 v3, 0x2

    const/4 v4, 0x1

    const/4 v5, 0x0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mMode:Ljava/lang/String;

    const-string v6, "light"

    invoke-virtual {v6, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object v6, v0, v5

    if-nez v6, :cond_0

    aget-object v6, v0, v4

    if-nez v6, :cond_0

    aget-object v6, v0, v3

    if-nez v6, :cond_0

    aget-object v0, v0, v2

    if-nez v0, :cond_0

    const/4 p1, 0x0

    return-object p1

    .line 350
    :cond_0
    sget v0, Lcom/hcn/common/R$layout;->utils_toast_view:I

    invoke-static {v0}, Lcom/hcn/common/widget/HViewUtils;->layoutId2View(I)Landroid/view/View;

    move-result-object v0

    const v6, 0x102000b

    .line 351
    invoke-virtual {v0, v6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Landroid/widget/TextView;

    .line 352
    iget-object v7, p0, Lcom/hcn/common/app/HToastUtils;->mMode:Ljava/lang/String;

    invoke-virtual {v1, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 353
    invoke-virtual {v0}, Landroid/view/View;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v1}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    check-cast v1, Landroid/graphics/drawable/GradientDrawable;

    const-string v7, "#BB000000"

    .line 354
    invoke-static {v7}, Landroid/graphics/Color;->parseColor(Ljava/lang/String;)I

    move-result v7

    invoke-virtual {v1, v7}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    const/4 v1, -0x1

    .line 355
    invoke-virtual {v6, v1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 357
    :cond_1
    invoke-virtual {v6, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 358
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object p1, p1, v5

    if-eqz p1, :cond_2

    .line 359
    sget p1, Lcom/hcn/common/R$id;->utvLeftIconView:I

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    .line 360
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object v1, v1, v5

    invoke-static {p1, v1}, Landroidx/core/view/ViewCompat;->setBackground(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V

    .line 361
    invoke-virtual {p1, v5}, Landroid/view/View;->setVisibility(I)V

    .line 363
    :cond_2
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object p1, p1, v4

    if-eqz p1, :cond_3

    .line 364
    sget p1, Lcom/hcn/common/R$id;->utvTopIconView:I

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    .line 365
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object v1, v1, v4

    invoke-static {p1, v1}, Landroidx/core/view/ViewCompat;->setBackground(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V

    .line 366
    invoke-virtual {p1, v5}, Landroid/view/View;->setVisibility(I)V

    .line 368
    :cond_3
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object p1, p1, v3

    if-eqz p1, :cond_4

    .line 369
    sget p1, Lcom/hcn/common/R$id;->utvRightIconView:I

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    .line 370
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object v1, v1, v3

    invoke-static {p1, v1}, Landroidx/core/view/ViewCompat;->setBackground(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V

    .line 371
    invoke-virtual {p1, v5}, Landroid/view/View;->setVisibility(I)V

    .line 373
    :cond_4
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object p1, p1, v2

    if-eqz p1, :cond_5

    .line 374
    sget p1, Lcom/hcn/common/R$id;->utvBottomIconView:I

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    .line 375
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    aget-object v1, v1, v2

    invoke-static {p1, v1}, Landroidx/core/view/ViewCompat;->setBackground(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V

    .line 376
    invoke-virtual {p1, v5}, Landroid/view/View;->setVisibility(I)V

    :cond_5
    return-object v0
.end method


# virtual methods
.method public final setBgColor(I)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 131
    iput p1, p0, Lcom/hcn/common/app/HToastUtils;->mBgColor:I

    return-object p0
.end method

.method public final setBgResource(I)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 143
    iput p1, p0, Lcom/hcn/common/app/HToastUtils;->mBgResource:I

    return-object p0
.end method

.method public final setBottomIcon(I)Lcom/hcn/common/app/HToastUtils;
    .locals 1

    .line 259
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0, p1}, Landroidx/core/content/ContextCompat;->getDrawable(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HToastUtils;->setBottomIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;

    move-result-object p1

    return-object p1
.end method

.method public final setBottomIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;
    .locals 2

    .line 270
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    const/4 v1, 0x3

    aput-object p1, v0, v1

    return-object p0
.end method

.method public final setDurationIsLong(Z)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 178
    iput-boolean p1, p0, Lcom/hcn/common/app/HToastUtils;->isLong:Z

    return-object p0
.end method

.method public final setGravity(III)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 117
    iput p1, p0, Lcom/hcn/common/app/HToastUtils;->mGravity:I

    .line 118
    iput p2, p0, Lcom/hcn/common/app/HToastUtils;->mXOffset:I

    .line 119
    iput p3, p0, Lcom/hcn/common/app/HToastUtils;->mYOffset:I

    return-object p0
.end method

.method public final setLeftIcon(I)Lcom/hcn/common/app/HToastUtils;
    .locals 1

    .line 190
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0, p1}, Landroidx/core/content/ContextCompat;->getDrawable(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HToastUtils;->setLeftIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;

    move-result-object p1

    return-object p1
.end method

.method public final setLeftIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;
    .locals 2

    .line 201
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    const/4 v1, 0x0

    aput-object p1, v0, v1

    return-object p0
.end method

.method public final setMode(Ljava/lang/String;)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 103
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils;->mMode:Ljava/lang/String;

    return-object p0
.end method

.method public final setNotUseSystemToast()Lcom/hcn/common/app/HToastUtils;
    .locals 1

    const/4 v0, 0x1

    .line 281
    iput-boolean v0, p0, Lcom/hcn/common/app/HToastUtils;->isNotUseSystemToast:Z

    return-object p0
.end method

.method public final setRightIcon(I)Lcom/hcn/common/app/HToastUtils;
    .locals 1

    .line 236
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0, p1}, Landroidx/core/content/ContextCompat;->getDrawable(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HToastUtils;->setRightIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;

    move-result-object p1

    return-object p1
.end method

.method public final setRightIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;
    .locals 2

    .line 247
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    const/4 v1, 0x2

    aput-object p1, v0, v1

    return-object p0
.end method

.method public final setTextColor(I)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 155
    iput p1, p0, Lcom/hcn/common/app/HToastUtils;->mTextColor:I

    return-object p0
.end method

.method public final setTextSize(I)Lcom/hcn/common/app/HToastUtils;
    .locals 0

    .line 167
    iput p1, p0, Lcom/hcn/common/app/HToastUtils;->mTextSize:I

    return-object p0
.end method

.method public final setTopIcon(I)Lcom/hcn/common/app/HToastUtils;
    .locals 1

    .line 213
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0, p1}, Landroidx/core/content/ContextCompat;->getDrawable(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HToastUtils;->setTopIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;

    move-result-object p1

    return-object p1
.end method

.method public final setTopIcon(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/app/HToastUtils;
    .locals 2

    .line 224
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils;->mIcons:[Landroid/graphics/drawable/Drawable;

    const/4 v1, 0x1

    aput-object p1, v0, v1

    return-object p0
.end method

.method public final show(I)V
    .locals 1

    .line 310
    invoke-static {p1}, Lcom/hcn/common/utils/HStringUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils;->getDuration()I

    move-result v0

    invoke-static {p1, v0, p0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public final varargs show(I[Ljava/lang/Object;)V
    .locals 0

    .line 320
    invoke-static {p1, p2}, Lcom/hcn/common/utils/HStringUtils;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils;->getDuration()I

    move-result p2

    invoke-static {p1, p2, p0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public final show(Landroid/view/View;)V
    .locals 1

    .line 337
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils;->getDuration()I

    move-result v0

    invoke-static {p1, v0, p0}, Lcom/hcn/common/app/HToastUtils;->show(Landroid/view/View;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public final show(Ljava/lang/CharSequence;)V
    .locals 1

    .line 301
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils;->getDuration()I

    move-result v0

    invoke-static {p1, v0, p0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method public final varargs show(Ljava/lang/String;[Ljava/lang/Object;)V
    .locals 0

    .line 330
    invoke-static {p1, p2}, Lcom/hcn/common/utils/HStringUtils;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils;->getDuration()I

    move-result p2

    invoke-static {p1, p2, p0}, Lcom/hcn/common/app/HToastUtils;->show(Ljava/lang/CharSequence;ILcom/hcn/common/app/HToastUtils;)V

    return-void
.end method
