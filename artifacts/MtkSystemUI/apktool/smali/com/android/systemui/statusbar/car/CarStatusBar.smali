.class public Lcom/android/systemui/statusbar/car/CarStatusBar;
.super Lcom/android/systemui/statusbar/phone/StatusBar;
.source "CarStatusBar.java"

# interfaces
.implements Lcom/android/systemui/statusbar/car/CarBatteryController$BatteryViewHandler;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;
    }
.end annotation


# static fields
.field public static final ENABLE_HVAC_CONNECTION:Z


# instance fields
.field private mActivityManagerWrapper:Lcom/android/systemui/shared/system/ActivityManagerWrapper;

.field private mCarBatteryController:Lcom/android/systemui/statusbar/car/CarBatteryController;

.field private mCarFacetButtonController:Lcom/android/systemui/statusbar/car/CarFacetButtonController;

.field private mConnectedDeviceSignalController:Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;

.field private mDeviceIsProvisioned:Z

.field private mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

.field private mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

.field private mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

.field private mLeftNavigationBarWindow:Landroid/view/ViewGroup;

.field private mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

.field private mNavigationBarWindow:Landroid/view/ViewGroup;

.field private mNotificationPanelBackground:Landroid/graphics/drawable/Drawable;

.field private final mQueueLock:Ljava/lang/Object;

.field private mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

.field private mRightNavigationBarWindow:Landroid/view/ViewGroup;

.field private mShowBottom:Z

.field private mShowLeft:Z

.field private mShowRight:Z

.field private mTaskStackListener:Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 58
    const-string v0, "android.car.hvac.demo"

    .line 59
    const/4 v1, 0x1

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    xor-int/2addr v0, v1

    sput-boolean v0, Lcom/android/systemui/statusbar/car/CarStatusBar;->ENABLE_HVAC_CONNECTION:Z

    .line 58
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 55
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;-><init>()V

    .line 77
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mQueueLock:Ljava/lang/Object;

    .line 84
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    return-void
.end method

.method static synthetic access$102(Lcom/android/systemui/statusbar/car/CarStatusBar;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/car/CarStatusBar;
    .param p1, "x1"    # Z

    .line 55
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    return p1
.end method

.method static synthetic access$200(Lcom/android/systemui/statusbar/car/CarStatusBar;)Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/car/CarStatusBar;

    .line 55
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/systemui/statusbar/car/CarStatusBar;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/car/CarStatusBar;

    .line 55
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->restartNavBars()V

    return-void
.end method

.method static synthetic access$400(Lcom/android/systemui/statusbar/car/CarStatusBar;)Lcom/android/systemui/statusbar/car/CarFacetButtonController;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/car/CarStatusBar;

    .line 55
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarFacetButtonController:Lcom/android/systemui/statusbar/car/CarFacetButtonController;

    return-object v0
.end method

.method private attachNavBarWindows()V
    .locals 10

    .line 284
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowBottom:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 285
    new-instance v0, Landroid/view/WindowManager$LayoutParams;

    const/4 v3, -0x1

    const/4 v4, -0x1

    const/16 v5, 0x7e3

    const v6, 0x840028

    const/4 v7, -0x3

    move-object v2, v0

    invoke-direct/range {v2 .. v7}, Landroid/view/WindowManager$LayoutParams;-><init>(IIIII)V

    .line 293
    .local v0, "lp":Landroid/view/WindowManager$LayoutParams;
    const-string v2, "CarNavigationBar"

    invoke-virtual {v0, v2}, Landroid/view/WindowManager$LayoutParams;->setTitle(Ljava/lang/CharSequence;)V

    .line 294
    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    .line 295
    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mWindowManager:Landroid/view/WindowManager;

    iget-object v3, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-interface {v2, v3, v0}, Landroid/view/WindowManager;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 297
    .end local v0    # "lp":Landroid/view/WindowManager$LayoutParams;
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowLeft:Z

    const/high16 v2, 0x400000

    if-eqz v0, :cond_1

    .line 298
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f0700d8

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    .line 300
    .local v0, "width":I
    new-instance v3, Landroid/view/WindowManager$LayoutParams;

    const/4 v6, -0x1

    const/16 v7, 0x7e8

    const v8, 0x840028

    const/4 v9, -0x3

    move-object v4, v3

    move v5, v0

    invoke-direct/range {v4 .. v9}, Landroid/view/WindowManager$LayoutParams;-><init>(IIIII)V

    .line 308
    .local v3, "leftlp":Landroid/view/WindowManager$LayoutParams;
    const-string v4, "LeftCarNavigationBar"

    invoke-virtual {v3, v4}, Landroid/view/WindowManager$LayoutParams;->setTitle(Ljava/lang/CharSequence;)V

    .line 309
    iput v1, v3, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    .line 310
    iget v4, v3, Landroid/view/WindowManager$LayoutParams;->privateFlags:I

    or-int/2addr v4, v2

    iput v4, v3, Landroid/view/WindowManager$LayoutParams;->privateFlags:I

    .line 311
    const/4 v4, 0x3

    iput v4, v3, Landroid/view/WindowManager$LayoutParams;->gravity:I

    .line 312
    iget-object v4, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mWindowManager:Landroid/view/WindowManager;

    iget-object v5, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-interface {v4, v5, v3}, Landroid/view/WindowManager;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 314
    .end local v0    # "width":I
    .end local v3    # "leftlp":Landroid/view/WindowManager$LayoutParams;
    :cond_1
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowRight:Z

    if-eqz v0, :cond_2

    .line 315
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f0700fa

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    .line 317
    .restart local v0    # "width":I
    new-instance v3, Landroid/view/WindowManager$LayoutParams;

    const/4 v6, -0x1

    const/16 v7, 0x7e8

    const v8, 0x840028

    const/4 v9, -0x3

    move-object v4, v3

    move v5, v0

    invoke-direct/range {v4 .. v9}, Landroid/view/WindowManager$LayoutParams;-><init>(IIIII)V

    .line 325
    .local v3, "rightlp":Landroid/view/WindowManager$LayoutParams;
    const-string v4, "RightCarNavigationBar"

    invoke-virtual {v3, v4}, Landroid/view/WindowManager$LayoutParams;->setTitle(Ljava/lang/CharSequence;)V

    .line 326
    iput v1, v3, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    .line 327
    iget v1, v3, Landroid/view/WindowManager$LayoutParams;->privateFlags:I

    or-int/2addr v1, v2

    iput v1, v3, Landroid/view/WindowManager$LayoutParams;->privateFlags:I

    .line 328
    const/4 v1, 0x5

    iput v1, v3, Landroid/view/WindowManager$LayoutParams;->gravity:I

    .line 329
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mWindowManager:Landroid/view/WindowManager;

    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-interface {v1, v2, v3}, Landroid/view/WindowManager;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 332
    .end local v0    # "width":I
    .end local v3    # "rightlp":Landroid/view/WindowManager$LayoutParams;
    :cond_2
    return-void
.end method

.method private buildBottomBar(I)V
    .locals 2
    .param p1, "layout"    # I

    .line 338
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-static {v0, p1, v1}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    .line 339
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    .line 340
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v0, :cond_0

    .line 344
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v0, p0}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->setStatusBar(Lcom/android/systemui/statusbar/car/CarStatusBar;)V

    .line 345
    return-void

    .line 341
    :cond_0
    const-string v0, "CarStatusBar"

    const-string v1, "CarStatusBar failed inflate for R.layout.car_navigation_bar"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 342
    new-instance v0, Ljava/lang/RuntimeException;

    const-string v1, "Unable to build botom nav bar due to missing layout"

    invoke-direct {v0, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method private buildLeft(I)V
    .locals 2
    .param p1, "layout"    # I

    .line 348
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-static {v0, p1, v1}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    .line 349
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    .line 350
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v0, :cond_0

    .line 354
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v0, p0}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->setStatusBar(Lcom/android/systemui/statusbar/car/CarStatusBar;)V

    .line 355
    return-void

    .line 351
    :cond_0
    const-string v0, "CarStatusBar"

    const-string v1, "CarStatusBar failed inflate for R.layout.car_navigation_bar"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 352
    new-instance v0, Ljava/lang/RuntimeException;

    const-string v1, "Unable to build left nav bar due to missing layout"

    invoke-direct {v0, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method private buildNavBarContent()V
    .locals 1

    .line 249
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowBottom:Z

    if-eqz v0, :cond_1

    .line 250
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    if-eqz v0, :cond_0

    const v0, 0x7f0d0063

    goto :goto_0

    .line 251
    :cond_0
    const v0, 0x7f0d0064

    .line 250
    :goto_0
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->buildBottomBar(I)V

    .line 254
    :cond_1
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowLeft:Z

    if-eqz v0, :cond_3

    .line 255
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    if-eqz v0, :cond_2

    const v0, 0x7f0d005d

    goto :goto_1

    .line 256
    :cond_2
    const v0, 0x7f0d005e

    .line 255
    :goto_1
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->buildLeft(I)V

    .line 259
    :cond_3
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowRight:Z

    if-eqz v0, :cond_5

    .line 260
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    if-eqz v0, :cond_4

    const v0, 0x7f0d006a

    goto :goto_2

    .line 261
    :cond_4
    const v0, 0x7f0d006b

    .line 260
    :goto_2
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->buildRight(I)V

    .line 263
    :cond_5
    return-void
.end method

.method private buildNavBarWindows()V
    .locals 3

    .line 266
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowBottom:Z

    const/4 v1, 0x0

    const v2, 0x7f0d0130

    if-eqz v0, :cond_0

    .line 268
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0, v2, v1}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    .line 271
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowLeft:Z

    if-eqz v0, :cond_1

    .line 272
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0, v2, v1}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    .line 275
    :cond_1
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowRight:Z

    if-eqz v0, :cond_2

    .line 276
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0, v2, v1}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    .line 280
    :cond_2
    return-void
.end method

.method private buildRight(I)V
    .locals 2
    .param p1, "layout"    # I

    .line 359
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-static {v0, p1, v1}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    .line 360
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    .line 361
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v0, :cond_0

    .line 365
    return-void

    .line 362
    :cond_0
    const-string v0, "CarStatusBar"

    const-string v1, "CarStatusBar failed inflate for R.layout.car_navigation_bar"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 363
    new-instance v0, Ljava/lang/RuntimeException;

    const-string v1, "Unable to build right nav bar due to missing layout"

    invoke-direct {v0, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method private createBatteryController()Lcom/android/systemui/statusbar/policy/BatteryController;
    .locals 2

    .line 232
    new-instance v0, Lcom/android/systemui/statusbar/car/CarBatteryController;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/car/CarBatteryController;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarBatteryController:Lcom/android/systemui/statusbar/car/CarBatteryController;

    .line 233
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarBatteryController:Lcom/android/systemui/statusbar/car/CarBatteryController;

    invoke-virtual {v0, p0}, Lcom/android/systemui/statusbar/car/CarBatteryController;->addBatteryViewHandler(Lcom/android/systemui/statusbar/car/CarBatteryController$BatteryViewHandler;)V

    .line 234
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarBatteryController:Lcom/android/systemui/statusbar/car/CarBatteryController;

    return-object v0
.end method

.method private getDefaultWallpaper()Landroid/graphics/drawable/Drawable;
    .locals 2

    .line 525
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    const v1, 0x1080269

    invoke-virtual {v0, v1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method

.method static synthetic lambda$makeStatusBarView$0(Ljava/lang/String;Landroid/app/Fragment;)V
    .locals 0
    .param p0, "tag"    # Ljava/lang/String;
    .param p1, "fragment"    # Landroid/app/Fragment;

    .line 228
    return-void
.end method

.method private restartNavBars()V
    .locals 2

    .line 123
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarFacetButtonController:Lcom/android/systemui/statusbar/car/CarFacetButtonController;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/CarFacetButtonController;->removeAll()V

    .line 124
    sget-boolean v0, Lcom/android/systemui/statusbar/car/CarStatusBar;->ENABLE_HVAC_CONNECTION:Z

    if-eqz v0, :cond_0

    .line 125
    const-class v0, Lcom/android/systemui/statusbar/car/hvac/HvacController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/car/hvac/HvacController;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/hvac/HvacController;->removeAllComponents()V

    .line 127
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    .line 128
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->removeAllViews()V

    .line 129
    iput-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    .line 132
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    if-eqz v0, :cond_2

    .line 133
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->removeAllViews()V

    .line 134
    iput-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    .line 137
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    if-eqz v0, :cond_3

    .line 138
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->removeAllViews()V

    .line 139
    iput-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    .line 141
    :cond_3
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->buildNavBarContent()V

    .line 142
    return-void
.end method


# virtual methods
.method public animateExpandNotificationsPanel()V
    .locals 1

    .line 504
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mHeadsUpManager:Lcom/android/systemui/statusbar/phone/HeadsUpManagerPhone;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/HeadsUpManagerPhone;->releaseAllImmediately()V

    .line 505
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->animateExpandNotificationsPanel()V

    .line 506
    return-void
.end method

.method protected createNavigationBar()V
    .locals 2

    .line 239
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f050009

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowBottom:Z

    .line 240
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f05000c

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowLeft:Z

    .line 241
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f050013

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mShowRight:Z

    .line 243
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->buildNavBarWindows()V

    .line 244
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->buildNavBarContent()V

    .line 245
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->attachNavBarWindows()V

    .line 246
    return-void
.end method

.method protected createUserSwitcher()V
    .locals 4

    .line 463
    const-class v0, Lcom/android/systemui/statusbar/policy/UserSwitcherController;

    .line 464
    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/UserSwitcherController;

    .line 465
    .local v0, "userSwitcherController":Lcom/android/systemui/statusbar/policy/UserSwitcherController;
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/policy/UserSwitcherController;->useFullscreenUserSwitcher()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 466
    new-instance v1, Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStatusBarWindow:Lcom/android/systemui/statusbar/phone/StatusBarWindowView;

    const v3, 0x7f0a0179

    .line 467
    invoke-virtual {v2, v3}, Lcom/android/systemui/statusbar/phone/StatusBarWindowView;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/view/ViewStub;

    iget-object v3, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-direct {v1, p0, v2, v3}, Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;-><init>(Lcom/android/systemui/statusbar/phone/StatusBar;Landroid/view/ViewStub;Landroid/content/Context;)V

    iput-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    goto :goto_0

    .line 469
    :cond_0
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->createUserSwitcher()V

    .line 471
    :goto_0
    return-void
.end method

.method public dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .locals 3
    .param p1, "fd"    # Ljava/io/FileDescriptor;
    .param p2, "pw"    # Ljava/io/PrintWriter;
    .param p3, "args"    # [Ljava/lang/String;

    .line 371
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mQueueLock:Ljava/lang/Object;

    monitor-enter v0

    .line 372
    :try_start_0
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "  mStackScroller: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStackScroller:Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;

    invoke-static {v2}, Lcom/android/systemui/statusbar/car/CarStatusBar;->viewInfo(Landroid/view/View;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p2, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 373
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "  mStackScroller: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStackScroller:Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;

    invoke-static {v2}, Lcom/android/systemui/statusbar/car/CarStatusBar;->viewInfo(Landroid/view/View;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " scroll "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStackScroller:Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;

    .line 374
    invoke-virtual {v2}, Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;->getScrollX()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ","

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStackScroller:Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;

    .line 375
    invoke-virtual {v2}, Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;->getScrollY()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 373
    invoke-virtual {p2, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 376
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 378
    const-string v0, "  mTaskStackListener="

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mTaskStackListener:Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 379
    const-string v0, "  mCarFacetButtonController="

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 380
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarFacetButtonController:Lcom/android/systemui/statusbar/car/CarFacetButtonController;

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 381
    const-string v0, "  mFullscreenUserSwitcher="

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 382
    const-string v0, "  mCarBatteryController="

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 383
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarBatteryController:Lcom/android/systemui/statusbar/car/CarBatteryController;

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 386
    const-string v0, "  mConnectedDeviceSignalController="

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 387
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mConnectedDeviceSignalController:Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 388
    const-string v0, "  mNavigationBarView="

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 389
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 391
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 392
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    invoke-virtual {v0, p1, p2, p3}, Lcom/android/keyguard/KeyguardUpdateMonitor;->dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V

    .line 395
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/systemui/classifier/FalsingManager;->getInstance(Landroid/content/Context;)Lcom/android/systemui/classifier/FalsingManager;

    move-result-object v0

    invoke-virtual {v0, p2}, Lcom/android/systemui/classifier/FalsingManager;->dump(Ljava/io/PrintWriter;)V

    .line 396
    invoke-static {p2}, Lcom/android/systemui/classifier/FalsingLog;->dump(Ljava/io/PrintWriter;)V

    .line 398
    const-string v0, "SharedPreferences:"

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 399
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/systemui/Prefs;->getAll(Landroid/content/Context;)Ljava/util/Map;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map$Entry;

    .line 400
    .local v1, "entry":Ljava/util/Map$Entry;, "Ljava/util/Map$Entry<Ljava/lang/String;*>;"
    const-string v2, "  "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    invoke-interface {v1}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const-string v2, "="

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    invoke-interface {v1}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 401
    .end local v1    # "entry":Ljava/util/Map$Entry;, "Ljava/util/Map$Entry<Ljava/lang/String;*>;"
    goto :goto_0

    .line 402
    :cond_1
    return-void

    .line 376
    :catchall_0
    move-exception v1

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v1
.end method

.method protected getStatusBarWindowTouchListener()Landroid/view/View$OnTouchListener;
    .locals 1

    .line 415
    const/4 v0, 0x0

    return-object v0
.end method

.method public hideBatteryView()V
    .locals 2

    .line 431
    const-string v0, "CarStatusBar"

    const/4 v1, 0x3

    invoke-static {v0, v1}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 432
    const-string v0, "CarStatusBar"

    const-string v1, "hideBatteryView()"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 438
    :cond_0
    return-void
.end method

.method public hideKeyguard()Z
    .locals 2

    .line 163
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->hideKeyguard()Z

    move-result v0

    .line 164
    .local v0, "result":Z
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v1, :cond_0

    .line 165
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->hideKeyguardButtons()V

    .line 167
    :cond_0
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v1, :cond_1

    .line 168
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->hideKeyguardButtons()V

    .line 170
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v1, :cond_2

    .line 171
    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->hideKeyguardButtons()V

    .line 173
    :cond_2
    return v0
.end method

.method protected makeStatusBarView()V
    .locals 3

    .line 216
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->makeStatusBarView()V

    .line 218
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->getDefaultWallpaper()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNotificationPanelBackground:Landroid/graphics/drawable/Drawable;

    .line 219
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mScrimController:Lcom/android/systemui/statusbar/phone/ScrimController;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNotificationPanelBackground:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/ScrimController;->setScrimBehindDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 221
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStatusBarWindow:Lcom/android/systemui/statusbar/phone/StatusBarWindowView;

    invoke-static {v0}, Lcom/android/systemui/fragments/FragmentHostManager;->get(Landroid/view/View;)Lcom/android/systemui/fragments/FragmentHostManager;

    move-result-object v0

    .line 222
    .local v0, "manager":Lcom/android/systemui/fragments/FragmentHostManager;
    const-string v1, "CollapsedStatusBarFragment"

    sget-object v2, Lcom/android/systemui/statusbar/car/-$$Lambda$CarStatusBar$7jPzIv4lIi3N5zuDHkpus4wOtLo;->INSTANCE:Lcom/android/systemui/statusbar/car/-$$Lambda$CarStatusBar$7jPzIv4lIi3N5zuDHkpus4wOtLo;

    invoke-virtual {v0, v1, v2}, Lcom/android/systemui/fragments/FragmentHostManager;->addTagListener(Ljava/lang/String;Lcom/android/systemui/fragments/FragmentHostManager$FragmentListener;)Lcom/android/systemui/fragments/FragmentHostManager;

    .line 229
    return-void
.end method

.method public onDensityOrFontScaleChanged()V
    .locals 2

    .line 514
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->onDensityOrFontScaleChanged()V

    .line 517
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->getDefaultWallpaper()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNotificationPanelBackground:Landroid/graphics/drawable/Drawable;

    .line 518
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mScrimController:Lcom/android/systemui/statusbar/phone/ScrimController;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNotificationPanelBackground:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/ScrimController;->setScrimBehindDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 519
    return-void
.end method

.method public onUserSwitched(I)V
    .locals 1
    .param p1, "newUserId"    # I

    .line 475
    invoke-super {p0, p1}, Lcom/android/systemui/statusbar/phone/StatusBar;->onUserSwitched(I)V

    .line 476
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    if-eqz v0, :cond_0

    .line 477
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    invoke-virtual {v0, p1}, Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;->onUserSwitched(I)V

    .line 479
    :cond_0
    return-void
.end method

.method setNavBarVisibility(I)V
    .locals 1
    .param p1, "visibility"    # I

    .line 149
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    if-eqz v0, :cond_0

    .line 150
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 152
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    .line 153
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 155
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    if-eqz v0, :cond_2

    .line 156
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarWindow:Landroid/view/ViewGroup;

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 158
    :cond_2
    return-void
.end method

.method public showBatteryView()V
    .locals 2

    .line 420
    const-string v0, "CarStatusBar"

    const/4 v1, 0x3

    invoke-static {v0, v1}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 421
    const-string v0, "CarStatusBar"

    const-string v1, "showBatteryView()"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 427
    :cond_0
    return-void
.end method

.method public showKeyguard()V
    .locals 1

    .line 179
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->showKeyguard()V

    .line 180
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v0, :cond_0

    .line 181
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->showKeyguardButtons()V

    .line 183
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v0, :cond_1

    .line 184
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mLeftNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->showKeyguardButtons()V

    .line 186
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    if-eqz v0, :cond_2

    .line 187
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mRightNavigationBarView:Lcom/android/systemui/statusbar/car/CarNavigationBarView;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/CarNavigationBarView;->showKeyguardButtons()V

    .line 189
    :cond_2
    return-void
.end method

.method public start()V
    .locals 2

    .line 88
    invoke-super {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->start()V

    .line 89
    new-instance v0, Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;-><init>(Lcom/android/systemui/statusbar/car/CarStatusBar;Lcom/android/systemui/statusbar/car/CarStatusBar$1;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mTaskStackListener:Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;

    .line 90
    invoke-static {}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->getInstance()Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mActivityManagerWrapper:Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    .line 91
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mActivityManagerWrapper:Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    iget-object v1, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mTaskStackListener:Lcom/android/systemui/statusbar/car/CarStatusBar$TaskStackListenerImpl;

    invoke-virtual {v0, v1}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->registerTaskStackListener(Lcom/android/systemui/shared/system/TaskStackChangeListener;)V

    .line 93
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mStackScroller:Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/stack/NotificationStackScrollLayout;->setScrollingEnabled(Z)V

    .line 95
    invoke-direct {p0}, Lcom/android/systemui/statusbar/car/CarStatusBar;->createBatteryController()Lcom/android/systemui/statusbar/policy/BatteryController;

    .line 96
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarBatteryController:Lcom/android/systemui/statusbar/car/CarBatteryController;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/CarBatteryController;->startListening()V

    .line 98
    sget-boolean v0, Lcom/android/systemui/statusbar/car/CarStatusBar;->ENABLE_HVAC_CONNECTION:Z

    if-eqz v0, :cond_0

    .line 99
    const-string v0, "CarStatusBar"

    const-string v1, "Connecting to HVAC service"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 100
    const-class v0, Lcom/android/systemui/statusbar/car/hvac/HvacController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/car/hvac/HvacController;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/hvac/HvacController;->connectToCarService()V

    .line 102
    :cond_0
    const-class v0, Lcom/android/systemui/statusbar/car/CarFacetButtonController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/car/CarFacetButtonController;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mCarFacetButtonController:Lcom/android/systemui/statusbar/car/CarFacetButtonController;

    .line 103
    const-class v0, Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    iput-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    .line 104
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    invoke-interface {v0}, Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;->isDeviceProvisioned()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    .line 105
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceIsProvisioned:Z

    if-nez v0, :cond_1

    .line 106
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mDeviceProvisionedController:Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;

    new-instance v1, Lcom/android/systemui/statusbar/car/CarStatusBar$1;

    invoke-direct {v1, p0}, Lcom/android/systemui/statusbar/car/CarStatusBar$1;-><init>(Lcom/android/systemui/statusbar/car/CarStatusBar;)V

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/DeviceProvisionedController;->addCallback(Ljava/lang/Object;)V

    .line 116
    :cond_1
    return-void
.end method

.method public updateKeyguardState(ZZ)V
    .locals 2
    .param p1, "goingToFullShade"    # Z
    .param p2, "fromShadeLocked"    # Z

    .line 483
    invoke-super {p0, p1, p2}, Lcom/android/systemui/statusbar/phone/StatusBar;->updateKeyguardState(ZZ)V

    .line 484
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    if-eqz v0, :cond_1

    .line 485
    iget v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mState:I

    const/4 v1, 0x3

    if-ne v0, v1, :cond_0

    .line 486
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;->show()V

    goto :goto_0

    .line 488
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/CarStatusBar;->mFullscreenUserSwitcher:Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/car/FullscreenUserSwitcher;->hide()V

    .line 491
    :cond_1
    :goto_0
    return-void
.end method

.method public updateMediaMetaData(ZZ)V
    .locals 0
    .param p1, "metaDataChanged"    # Z
    .param p2, "allowEnterAnimation"    # Z

    .line 496
    return-void
.end method
