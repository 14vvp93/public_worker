.class public Lcom/hcn/auto/systemui/InitViewImpl;
.super Ljava/lang/Object;
.source "InitViewImpl.java"


# instance fields
.field bottomBg:Landroid/widget/RelativeLayout;

.field private dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

.field private mBtnAllApp:Landroid/widget/ImageButton;

.field private mBtnBt:Landroid/widget/ImageButton;

.field private mBtnBtMusic:Landroid/widget/ImageButton;

.field private mBtnEq:Landroid/widget/ImageButton;

.field private mBtnHome:Landroid/widget/ImageButton;

.field private mBtnMusic:Landroid/widget/ImageButton;

.field private mBtnMute:Landroid/widget/ImageButton;

.field private mBtnNavi:Landroid/widget/ImageButton;

.field private mBtnOffScreen:Landroid/widget/ImageButton;

.field private mBtnRadio:Landroid/widget/ImageButton;

.field private mBtnSetting:Landroid/widget/ImageButton;

.field private mContext:Landroid/content/Context;

.field private mCurrentSkin:Ljava/lang/String;

.field private mDate:Landroid/widget/TextView;

.field private mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

.field private mIsReversing:I

.field private mMuteImg:Landroid/widget/ImageView;

.field protected mPackageName:Ljava/lang/String;

.field private mPlayImg:Landroid/widget/ImageView;

.field protected mResources:Landroid/content/res/Resources;

.field private mTime:Landroid/widget/TextView;

.field private mTvSoundVolume:Landroid/widget/TextView;

.field private mUiMode:I

.field private mView:Landroid/view/View;

.field private mWeek:Landroid/widget/TextView;

.field private timeReceiver:Landroid/content/BroadcastReceiver;

.field utils:Lcom/hcn/auto/systemui/Utils;


# direct methods
.method public constructor <init>(Landroid/view/View;Landroid/content/Context;)V
    .locals 1
    .param p1, "view"    # Landroid/view/View;
    .param p2, "context"    # Landroid/content/Context;

    .line 101
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 89
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    .line 102
    iput-object p1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    .line 103
    iput-object p2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 104
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mResources:Landroid/content/res/Resources;

    .line 105
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinPkgName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mPackageName:Ljava/lang/String;

    .line 106
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->initView()V

    .line 107
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/auto/systemui/InitViewImpl;)V
    .locals 0
    .param p0, "x0"    # Lcom/hcn/auto/systemui/InitViewImpl;

    .line 56
    invoke-direct {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->onTimeChanged()V

    return-void
.end method

.method private changeBottomBgThemeDialog()Z
    .locals 3

    .line 603
    const-string v0, "1"

    const-string v1, "change_bottomBg_theme"

    const-string v2, "0"

    invoke-static {v1, v2}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method private hideDialog()V
    .locals 1

    .line 382
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

    if-eqz v0, :cond_0

    .line 383
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/RecyclerViewDialog;->hide()V

    .line 385
    :cond_0
    return-void
.end method

.method private isBackCar()Z
    .locals 1

    .line 651
    new-instance v0, Landroid/carstatus/CarStatus;

    invoke-direct {v0}, Landroid/carstatus/CarStatus;-><init>()V

    invoke-virtual {v0}, Landroid/carstatus/CarStatus;->getReversing()I

    move-result v0

    if-lez v0, :cond_0

    .line 652
    const/4 v0, 0x1

    return v0

    .line 654
    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method static synthetic lambda$initView$0(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 123
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x14f

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 130
    return-void
.end method

.method static synthetic lambda$initView$1(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 135
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/4 v1, 0x3

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 136
    return-void
.end method

.method public static synthetic lambda$initView$10(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)V
    .locals 3
    .param p1, "v"    # Landroid/view/View;

    .line 188
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    const-string v1, "com.hcn.autoradio"

    const-string v2, "com.hcn.autoradio.RadioMain"

    invoke-static {v0, v1, v2}, Lcom/android/systemui/util/Utils;->startActivity(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    .line 189
    return-void
.end method

.method public static synthetic lambda$initView$11(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)V
    .locals 1
    .param p1, "view"    # Landroid/view/View;

    .line 193
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->startTimeSetting(Landroid/content/Context;)V

    return-void
.end method

.method public static synthetic lambda$initView$12(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)V
    .locals 1
    .param p1, "v"    # Landroid/view/View;

    .line 196
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->startCalendar(Landroid/content/Context;)V

    return-void
.end method

.method public static synthetic lambda$initView$13(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)V
    .locals 1
    .param p1, "v"    # Landroid/view/View;

    .line 200
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->startCalendar(Landroid/content/Context;)V

    return-void
.end method

.method public static synthetic lambda$initView$14(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)Z
    .locals 3
    .param p1, "v"    # Landroid/view/View;

    .line 206
    invoke-direct {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->changeBottomBgThemeDialog()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 208
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 209
    .local v0, "intent":Landroid/content/Intent;
    const v1, 0x10008000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 210
    const-string v1, "com.hcn.themepicker"

    const-string v2, "com.hcn.themepicker.BottomBgThemeActivity"

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->setClassName(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 211
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 214
    .end local v0    # "intent":Landroid/content/Intent;
    goto :goto_0

    .line 212
    :catch_0
    move-exception v0

    .line 213
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "InitViewImpl"

    const-string v2, ""

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 216
    .end local v0    # "e":Ljava/lang/Exception;
    :cond_0
    :goto_0
    const/4 v0, 0x1

    return v0
.end method

.method public static synthetic lambda$initView$15(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)V
    .locals 0
    .param p1, "view"    # Landroid/view/View;

    .line 293
    invoke-direct {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->hideDialog()V

    return-void
.end method

.method static synthetic lambda$initView$16(Landroid/content/pm/LauncherActivityInfo;)Z
    .locals 2
    .param p0, "launcherActivityInfo"    # Landroid/content/pm/LauncherActivityInfo;

    .line 298
    invoke-virtual {p0}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isHidden(Ljava/lang/String;Ljava/lang/String;)Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    .line 300
    .local v0, "bRet":Z
    return v0
.end method

.method public static synthetic lambda$initView$17(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;
    .locals 7
    .param p1, "launcherActivityInfo"    # Landroid/content/pm/LauncherActivityInfo;

    .line 303
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->utils:Lcom/hcn/auto/systemui/Utils;

    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    const-string v3, "car_home_"

    .line 304
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    const-string v4, "icon_size"

    invoke-virtual {v0, v4}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v4

    const-string v6, "car_home_icon_background"

    .line 303
    const v5, 0x3f266666    # 0.65f

    invoke-static/range {v1 .. v6}, Lcom/hcn/auto/systemui/Utils;->getCarHomeIconFromTheme(Landroid/content/Context;Landroid/content/ComponentName;Ljava/lang/String;IFLjava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 305
    .local v0, "drawable":Landroid/graphics/drawable/Drawable;
    if-nez v0, :cond_2

    .line 306
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->utils:Lcom/hcn/auto/systemui/Utils;

    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/hcn/auto/systemui/Utils;->getActivityIconFromTheme(Landroid/content/Context;Landroid/content/ComponentName;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 307
    const-string/jumbo v1, "xt617"

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    const-string/jumbo v1, "xt618"

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 308
    :cond_0
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->utils:Lcom/hcn/auto/systemui/Utils;

    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    const-string/jumbo v3, "xt617_icon_themes"

    const-string/jumbo v4, "xt617_bottom_icon_background"

    invoke-static {v1, v2, v3, v4}, Lcom/hcn/auto/systemui/Utils;->getActivityIconFromTheme(Landroid/content/Context;Landroid/content/ComponentName;Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 311
    :cond_1
    const-string v1, "sa266"

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 312
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->utils:Lcom/hcn/auto/systemui/Utils;

    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    const-string v3, "sa266_icon_themes"

    const-string/jumbo v4, "xt617_bottom_icon_background"

    invoke-static {v1, v2, v3, v4}, Lcom/hcn/auto/systemui/Utils;->getActivityIconFromTheme(Landroid/content/Context;Landroid/content/ComponentName;Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 317
    :cond_2
    return-object v0
.end method

.method static synthetic lambda$initView$2(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 141
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x134

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 142
    return-void
.end method

.method static synthetic lambda$initView$3(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 147
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x136

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 148
    return-void
.end method

.method static synthetic lambda$initView$4(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 153
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x14b

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 154
    return-void
.end method

.method static synthetic lambda$initView$5(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 159
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x12a

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 160
    return-void
.end method

.method static synthetic lambda$initView$6(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 164
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0xd1

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 165
    return-void
.end method

.method static synthetic lambda$initView$7(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 170
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0xa4

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 171
    return-void
.end method

.method public static synthetic lambda$initView$8(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/view/View;)V
    .locals 3
    .param p1, "v"    # Landroid/view/View;

    .line 176
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    const-string v1, "com.android.settings"

    const-string v2, "com.android.settings.Settings"

    invoke-static {v0, v1, v2}, Lcom/android/systemui/util/Utils;->startActivity(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    .line 177
    return-void
.end method

.method static synthetic lambda$initView$9(Landroid/view/View;)V
    .locals 3
    .param p0, "v"    # Landroid/view/View;

    .line 182
    invoke-static {}, Landroid/carsource/McuManager;->getsInstance()Landroid/carsource/McuManager;

    move-result-object v0

    const/16 v1, 0x12e

    const/16 v2, 0x32

    invoke-virtual {v0, v1, v2}, Landroid/carsource/McuManager;->injectKeyEventTimeout(II)V

    .line 183
    return-void
.end method

.method private onTimeChanged()V
    .locals 4

    .line 442
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    const/16 v1, 0x1c

    if-eqz v0, :cond_5

    .line 443
    const/4 v0, 0x0

    .line 444
    .local v0, "formatTime":Ljava/lang/String;
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    if-lt v2, v1, :cond_0

    .line 445
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    invoke-virtual {v2}, Landroid/widget/TextView;->getAccessibilityPaneTitle()Ljava/lang/CharSequence;

    move-result-object v2

    move-object v0, v2

    check-cast v0, Ljava/lang/String;

    .line 447
    :cond_0
    if-nez v0, :cond_1

    .line 448
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    invoke-virtual {v2}, Landroid/widget/TextView;->getContentDescription()Ljava/lang/CharSequence;

    move-result-object v2

    move-object v0, v2

    check-cast v0, Ljava/lang/String;

    .line 450
    :cond_1
    if-eqz v0, :cond_2

    .line 451
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/text/format/DateFormat;->is24HourFormat(Landroid/content/Context;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 452
    const-string v0, "HH:mm"

    .line 455
    :cond_2
    if-eqz v0, :cond_3

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/text/format/DateFormat;->format(Ljava/lang/CharSequence;Ljava/util/Date;)Ljava/lang/CharSequence;

    move-result-object v2

    goto :goto_0

    :cond_3
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/text/format/DateFormat;->getTimeFormat(Landroid/content/Context;)Ljava/text/DateFormat;

    move-result-object v2

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v3

    invoke-virtual {v3}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/text/DateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v2

    .line 457
    .local v2, "value":Ljava/lang/CharSequence;
    :goto_0
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    instance-of v3, v3, Landroid/widget/TextView;

    if-eqz v3, :cond_4

    .line 458
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_1

    .line 459
    :cond_4
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    instance-of v3, v3, Landroid/widget/Button;

    if-eqz v3, :cond_5

    .line 460
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    check-cast v3, Landroid/widget/Button;

    invoke-virtual {v3, v2}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 464
    .end local v0    # "formatTime":Ljava/lang/String;
    .end local v2    # "value":Ljava/lang/CharSequence;
    :cond_5
    :goto_1
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    if-eqz v0, :cond_a

    .line 465
    const/4 v0, 0x0

    .line 466
    .local v0, "formatDate":Ljava/lang/String;
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    if-lt v2, v1, :cond_6

    .line 467
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    invoke-virtual {v2}, Landroid/widget/TextView;->getAccessibilityPaneTitle()Ljava/lang/CharSequence;

    move-result-object v2

    move-object v0, v2

    check-cast v0, Ljava/lang/String;

    .line 469
    :cond_6
    if-nez v0, :cond_7

    .line 470
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    invoke-virtual {v2}, Landroid/widget/TextView;->getContentDescription()Ljava/lang/CharSequence;

    move-result-object v2

    move-object v0, v2

    check-cast v0, Ljava/lang/String;

    .line 472
    :cond_7
    if-eqz v0, :cond_8

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/text/format/DateFormat;->format(Ljava/lang/CharSequence;Ljava/util/Date;)Ljava/lang/CharSequence;

    move-result-object v2

    goto :goto_2

    :cond_8
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/text/format/DateFormat;->getMediumDateFormat(Landroid/content/Context;)Ljava/text/DateFormat;

    move-result-object v2

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v3

    invoke-virtual {v3}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/text/DateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v2

    .line 474
    .restart local v2    # "value":Ljava/lang/CharSequence;
    :goto_2
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    instance-of v3, v3, Landroid/widget/TextView;

    if-eqz v3, :cond_9

    .line 475
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_3

    .line 476
    :cond_9
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    instance-of v3, v3, Landroid/widget/Button;

    if-eqz v3, :cond_a

    .line 477
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    check-cast v3, Landroid/widget/Button;

    invoke-virtual {v3, v2}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 481
    .end local v0    # "formatDate":Ljava/lang/String;
    .end local v2    # "value":Ljava/lang/CharSequence;
    :cond_a
    :goto_3
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    if-eqz v0, :cond_f

    .line 482
    const/4 v0, 0x0

    .line 483
    .local v0, "formatWeek":Ljava/lang/String;
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    if-lt v2, v1, :cond_b

    .line 484
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    invoke-virtual {v1}, Landroid/widget/TextView;->getAccessibilityPaneTitle()Ljava/lang/CharSequence;

    move-result-object v1

    move-object v0, v1

    check-cast v0, Ljava/lang/String;

    .line 486
    :cond_b
    if-nez v0, :cond_c

    .line 487
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    invoke-virtual {v1}, Landroid/widget/TextView;->getContentDescription()Ljava/lang/CharSequence;

    move-result-object v1

    move-object v0, v1

    check-cast v0, Ljava/lang/String;

    .line 489
    :cond_c
    if-eqz v0, :cond_d

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/text/format/DateFormat;->format(Ljava/lang/CharSequence;Ljava/util/Date;)Ljava/lang/CharSequence;

    move-result-object v1

    goto :goto_4

    :cond_d
    const-string v1, "E"

    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/text/format/DateFormat;->format(Ljava/lang/CharSequence;Ljava/util/Date;)Ljava/lang/CharSequence;

    move-result-object v1

    .line 491
    .local v1, "value":Ljava/lang/CharSequence;
    :goto_4
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    instance-of v2, v2, Landroid/widget/TextView;

    if-eqz v2, :cond_e

    .line 492
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_5

    .line 493
    :cond_e
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    instance-of v2, v2, Landroid/widget/Button;

    if-eqz v2, :cond_f

    .line 494
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    check-cast v2, Landroid/widget/Button;

    invoke-virtual {v2, v1}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 497
    .end local v0    # "formatWeek":Ljava/lang/String;
    .end local v1    # "value":Ljava/lang/CharSequence;
    :cond_f
    :goto_5
    return-void
.end method

.method private registerTimeReceiver()V
    .locals 3

    .line 425
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->timeReceiver:Landroid/content/BroadcastReceiver;

    if-nez v0, :cond_0

    .line 426
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 427
    .local v0, "intentFilter":Landroid/content/IntentFilter;
    const-string v1, "android.intent.action.TIMEZONE_CHANGED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 428
    const-string v1, "android.intent.action.DATE_CHANGED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 429
    const-string v1, "android.intent.action.TIME_SET"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 430
    const-string v1, "android.intent.action.TIME_TICK"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 431
    new-instance v1, Lcom/hcn/auto/systemui/InitViewImpl$1;

    invoke-direct {v1, p0}, Lcom/hcn/auto/systemui/InitViewImpl$1;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    iput-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->timeReceiver:Landroid/content/BroadcastReceiver;

    .line 437
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->timeReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 439
    .end local v0    # "intentFilter":Landroid/content/IntentFilter;
    :cond_0
    return-void
.end method

.method private resizeBitmapDrawable(Landroid/graphics/Bitmap;II)Landroid/graphics/drawable/Drawable;
    .locals 2
    .param p1, "originalBitmap"    # Landroid/graphics/Bitmap;
    .param p2, "newWidth"    # I
    .param p3, "newHeight"    # I

    .line 387
    const/4 v0, 0x1

    invoke-static {p1, p2, p3, v0}, Landroid/graphics/Bitmap;->createScaledBitmap(Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 388
    .local v0, "resizedBitmap":Landroid/graphics/Bitmap;
    new-instance v1, Landroid/graphics/drawable/BitmapDrawable;

    invoke-direct {v1, v0}, Landroid/graphics/drawable/BitmapDrawable;-><init>(Landroid/graphics/Bitmap;)V

    return-object v1
.end method

.method public static startCalendar(Landroid/content/Context;)V
    .locals 2
    .param p0, "context"    # Landroid/content/Context;

    .line 411
    if-nez p0, :cond_0

    .line 412
    return-void

    .line 415
    :cond_0
    const-string v0, "com.google.android.calendar"

    invoke-static {v0, p0}, Lcom/hcn/auto/systemui/Tools;->isAppInstalled(Ljava/lang/String;Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 416
    const-string v0, "com.google.android.calendar"

    const-string v1, "com.google.android.calendar.AllInOneCalendarActivity"

    invoke-static {p0, v0, v1}, Lcom/hcn/auto/systemui/Tools;->startActivity(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 417
    :cond_1
    const-string v0, "com.calendar.hcncalendar"

    invoke-static {v0, p0}, Lcom/hcn/auto/systemui/Tools;->isAppInstalled(Ljava/lang/String;Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 418
    const-string v0, "com.calendar.hcncalendar"

    const-string v1, "com.calendar.hcncalendar.CalendarActivity"

    invoke-static {p0, v0, v1}, Lcom/hcn/auto/systemui/Tools;->startActivity(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 420
    :cond_2
    invoke-static {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->startTimeSetting(Landroid/content/Context;)V

    .line 422
    :goto_0
    return-void
.end method

.method public static startTimeSetting(Landroid/content/Context;)V
    .locals 3
    .param p0, "context"    # Landroid/content/Context;

    .line 500
    if-nez p0, :cond_0

    .line 501
    return-void

    .line 505
    :cond_0
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 506
    .local v0, "it":Landroid/content/Intent;
    const-string v1, "android.settings.DATE_SETTINGS"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 507
    const/high16 v1, 0x10020000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setFlags(I)Landroid/content/Intent;

    .line 508
    invoke-virtual {p0, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 511
    .end local v0    # "it":Landroid/content/Intent;
    goto :goto_0

    .line 509
    :catch_0
    move-exception v0

    .line 510
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "InitViewImpl"

    const-string v2, ""

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 512
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method


# virtual methods
.method public findViewByName(Ljava/lang/String;)Landroid/view/View;
    .locals 4
    .param p1, "name"    # Ljava/lang/String;

    .line 589
    const/4 v0, 0x0

    .line 591
    .local v0, "id":I
    :try_start_0
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mResources:Landroid/content/res/Resources;

    const-string v2, "id"

    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mPackageName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v0, v1

    .line 594
    goto :goto_0

    .line 592
    :catch_0
    move-exception v1

    .line 593
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 596
    .end local v1    # "e":Ljava/lang/Exception;
    :goto_0
    if-eqz v0, :cond_0

    .line 597
    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    invoke-virtual {v1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    return-object v1

    .line 599
    :cond_0
    const/4 v1, 0x0

    return-object v1
.end method

.method public getBtnAllApp()Landroid/widget/ImageButton;
    .locals 1

    .line 392
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    return-object v0
.end method

.method public getBtnMusic()Landroid/widget/ImageButton;
    .locals 1

    .line 399
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    return-object v0
.end method

.method public getBtnNavi()Landroid/widget/ImageButton;
    .locals 1

    .line 396
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    return-object v0
.end method

.method public getBtnRadio()Landroid/widget/ImageButton;
    .locals 1

    .line 403
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    return-object v0
.end method

.method public getTime()Landroid/widget/TextView;
    .locals 1

    .line 407
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    return-object v0
.end method

.method public initBtnView(Ljava/lang/String;)V
    .locals 3
    .param p1, "skinName"    # Ljava/lang/String;

    .line 341
    invoke-virtual {p0, p1}, Lcom/hcn/auto/systemui/InitViewImpl;->isNewBottomUI(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 342
    const-string v0, "InitViewImpl"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "initBtnView----isNewBottomUI---"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 343
    const-string v0, "btn_all_app"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    .line 344
    const-string v0, "btn_eq"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnEq:Landroid/widget/ImageButton;

    .line 345
    const-string v0, "btn_off_screen"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnOffScreen:Landroid/widget/ImageButton;

    .line 346
    const-string v0, "btn_bt_music"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBtMusic:Landroid/widget/ImageButton;

    .line 347
    const-string v0, "btn_navi"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    .line 348
    const-string v0, "btn_music"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    .line 349
    const-string v0, "btn_radio"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    .line 350
    const-string v0, "tv_time"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    .line 351
    const-string v0, "tv_date"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    .line 352
    const-string v0, "tv_week"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    .line 353
    const-string v0, "tv_sound_volume"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    .line 354
    const-string v0, "btn_mute"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    .line 355
    const-string v0, "btn_settings"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnSetting:Landroid/widget/ImageButton;

    .line 356
    const-string v0, "btn_bt"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBt:Landroid/widget/ImageButton;

    .line 357
    const-string v0, "navi_bottom"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->bottomBg:Landroid/widget/RelativeLayout;

    .line 358
    const-string v0, "btn_home"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnHome:Landroid/widget/ImageButton;

    .line 359
    const-string v0, "play_img"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mPlayImg:Landroid/widget/ImageView;

    .line 360
    const-string v0, "mute_img"

    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mMuteImg:Landroid/widget/ImageView;

    goto/16 :goto_0

    .line 362
    :cond_0
    const-string v0, "InitViewImpl"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "initBtnView----notNewBottomUI---"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 363
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00af

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    .line 364
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00b6

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnEq:Landroid/widget/ImageButton;

    .line 365
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00c0

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnOffScreen:Landroid/widget/ImageButton;

    .line 366
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00b3

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBtMusic:Landroid/widget/ImageButton;

    .line 367
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00be

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    .line 368
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00bc

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    .line 369
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00c3

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    .line 370
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a0433

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    .line 371
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a042f

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    .line 372
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a0435

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    .line 373
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a0431

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    .line 374
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00bd

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    .line 375
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00c6

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnSetting:Landroid/widget/ImageButton;

    .line 376
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00b2

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBt:Landroid/widget/ImageButton;

    .line 377
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a029d

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->bottomBg:Landroid/widget/RelativeLayout;

    .line 378
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v1, 0x7f0a00b9

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnHome:Landroid/widget/ImageButton;

    .line 380
    :goto_0
    return-void
.end method

.method public initView()V
    .locals 17

    .line 110
    move-object/from16 v1, p0

    const-string v0, "1"

    const-string v2, "independent_systemui_style"

    const-string v3, "0"

    invoke-static {v2, v3}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 111
    const-string v0, "persist.sys.systemui.skin"

    const-string v2, ""

    invoke-static {v0, v2}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    goto :goto_0

    .line 113
    :cond_0
    const-string v0, "persist.sys.skin"

    const-string v2, ""

    invoke-static {v0, v2}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    .line 115
    :goto_0
    new-instance v0, Lcom/hcn/auto/systemui/Utils;

    iget-object v2, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-direct {v0, v2}, Lcom/hcn/auto/systemui/Utils;-><init>(Landroid/content/Context;)V

    iput-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->utils:Lcom/hcn/auto/systemui/Utils;

    .line 116
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->utils:Lcom/hcn/auto/systemui/Utils;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/Utils;->init()V

    .line 117
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    iget v0, v0, Landroid/content/res/Configuration;->uiMode:I

    iput v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mUiMode:I

    .line 118
    iget v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mUiMode:I

    and-int/lit8 v0, v0, 0x30

    const/16 v2, 0x20

    if-ne v0, v2, :cond_1

    const/4 v0, 0x1

    goto :goto_1

    :cond_1
    const/4 v0, 0x0

    :goto_1
    move v5, v0

    .line 119
    .local v5, "isNight":Z
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->initBtnView(Ljava/lang/String;)V

    .line 120
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    iget v0, v0, Landroid/content/res/Configuration;->uiMode:I

    iput v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mUiMode:I

    .line 121
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    if-eqz v0, :cond_2

    .line 122
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$dME3wslnbpTaukINnWlzPldQ-9E;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$dME3wslnbpTaukINnWlzPldQ-9E;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 133
    :cond_2
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnHome:Landroid/widget/ImageButton;

    if-eqz v0, :cond_3

    .line 134
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnHome:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$6avzLCZeoTBLphbT3RW3AJpYgNk;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$6avzLCZeoTBLphbT3RW3AJpYgNk;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 139
    :cond_3
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnEq:Landroid/widget/ImageButton;

    if-eqz v0, :cond_4

    .line 140
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnEq:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$WHoCQdtv7g87_vl58uLJLbING0w;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$WHoCQdtv7g87_vl58uLJLbING0w;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 145
    :cond_4
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnOffScreen:Landroid/widget/ImageButton;

    if-eqz v0, :cond_5

    .line 146
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnOffScreen:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$BDU1y7MspRk9gTmILJ8whvcxyL4;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$BDU1y7MspRk9gTmILJ8whvcxyL4;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 151
    :cond_5
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBtMusic:Landroid/widget/ImageButton;

    if-eqz v0, :cond_6

    .line 152
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBtMusic:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$ahOO5KN-4drqFtbD-pRLHMBk7mk;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$ahOO5KN-4drqFtbD-pRLHMBk7mk;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 157
    :cond_6
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    if-eqz v0, :cond_7

    .line 158
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$AiB7JWELCdhbhPPsQh3bzdDIc7o;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$AiB7JWELCdhbhPPsQh3bzdDIc7o;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 162
    :cond_7
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    if-eqz v0, :cond_8

    .line 163
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$gLH1U_fMoZ9_Vw1jFdz8jSYl4hw;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$gLH1U_fMoZ9_Vw1jFdz8jSYl4hw;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 168
    :cond_8
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    if-eqz v0, :cond_9

    .line 169
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$n3Lwea6kVCp8AdcQkcleJxA5lY0;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$n3Lwea6kVCp8AdcQkcleJxA5lY0;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 174
    :cond_9
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnSetting:Landroid/widget/ImageButton;

    if-eqz v0, :cond_a

    .line 175
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnSetting:Landroid/widget/ImageButton;

    new-instance v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$NJPmxLoXrudd1jW5JoJwKoDtRYo;

    invoke-direct {v6, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$NJPmxLoXrudd1jW5JoJwKoDtRYo;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 180
    :cond_a
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBt:Landroid/widget/ImageButton;

    if-eqz v0, :cond_b

    .line 181
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnBt:Landroid/widget/ImageButton;

    sget-object v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$AYAfwqp1j2KuTyjQjbZmvUAco3I;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$AYAfwqp1j2KuTyjQjbZmvUAco3I;

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 186
    :cond_b
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    if-eqz v0, :cond_c

    .line 187
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    new-instance v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$rOVlinZeUekh1NmiLBid3CfkEw4;

    invoke-direct {v6, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$rOVlinZeUekh1NmiLBid3CfkEw4;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v0, v6}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 192
    :cond_c
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    if-eqz v0, :cond_d

    .line 193
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    new-instance v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$kybrSNNyIjQa0N4bWP9hGVA-QlU;

    invoke-direct {v6, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$kybrSNNyIjQa0N4bWP9hGVA-QlU;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v0, v6}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 195
    :cond_d
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    if-eqz v0, :cond_e

    .line 196
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDate:Landroid/widget/TextView;

    new-instance v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$CNKIsYhRRbQNvSFmgflvzokNnzw;

    invoke-direct {v6, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$CNKIsYhRRbQNvSFmgflvzokNnzw;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v0, v6}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 199
    :cond_e
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    if-eqz v0, :cond_f

    .line 200
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mWeek:Landroid/widget/TextView;

    new-instance v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$0l96YvVH8LDpqSxmoNrW7AksGiY;

    invoke-direct {v6, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$0l96YvVH8LDpqSxmoNrW7AksGiY;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v0, v6}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 203
    :cond_f
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->bottomBg:Landroid/widget/RelativeLayout;

    if-eqz v0, :cond_10

    .line 204
    invoke-static {}, Lcom/android/systemui/SystemUIApplication;->hasBottomBar()Z

    move-result v0

    if-eqz v0, :cond_10

    .line 205
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->bottomBg:Landroid/widget/RelativeLayout;

    new-instance v6, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$IWnMhJ3tTET3ib_HnDLmkg7PQoQ;

    invoke-direct {v6, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$IWnMhJ3tTET3ib_HnDLmkg7PQoQ;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v0, v6}, Landroid/widget/RelativeLayout;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V

    .line 220
    :cond_10
    invoke-direct/range {p0 .. p0}, Lcom/hcn/auto/systemui/InitViewImpl;->onTimeChanged()V

    .line 221
    invoke-direct/range {p0 .. p0}, Lcom/hcn/auto/systemui/InitViewImpl;->registerTimeReceiver()V

    .line 222
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    if-eqz v0, :cond_14

    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mPlayImg:Landroid/widget/ImageView;

    if-eqz v0, :cond_14

    .line 223
    const-string v0, "InitViewImpl"

    const-string v6, "mTvSoundVolume-not null"

    invoke-static {v0, v6}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 224
    new-instance v0, Landroid/widget/RelativeLayout$LayoutParams;

    const/4 v6, -0x2

    invoke-direct {v0, v6, v6}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    .line 225
    .local v0, "tvSoundVolumeParams":Landroid/view/ViewGroup$MarginLayoutParams;
    new-instance v7, Landroid/widget/RelativeLayout$LayoutParams;

    invoke-direct {v7, v6, v6}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    move-object v6, v7

    .line 226
    .local v6, "tvPlayImgParams":Landroid/view/ViewGroup$MarginLayoutParams;
    const-string v7, "tvSoundVolume_layout_marginStart"

    const-string v8, "0"

    invoke-static {v7, v8}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v7

    .line 227
    .local v7, "tvSoundVolumeMarginStart":I
    const-string v8, "tvSoundVolume_layout_marginBottom"

    const-string v9, "0"

    invoke-static {v8, v9}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v8

    .line 228
    .local v8, "tvSoundVolumeMarginBottom":I
    const-string v9, "playImg_layout_height"

    const-string v10, "0"

    invoke-static {v9, v10}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v9

    .line 229
    .local v9, "playImgHeight":I
    const-string v10, "playImg_layout_width"

    const-string v11, "0"

    invoke-static {v10, v11}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    invoke-static {v10}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v10

    .line 230
    .local v10, "playImgWidth":I
    const-string v11, "playImg_layout_marginEnd"

    const-string v12, "0"

    invoke-static {v11, v12}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    invoke-static {v11}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v11

    .line 231
    .local v11, "playImgMarginEnd":I
    const-string v12, "playImg_layout_marginBottom"

    const-string v13, "0"

    invoke-static {v12, v13}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v12

    invoke-static {v12}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v12

    .line 232
    .local v12, "playImgMarginBottom":I
    const-string v13, "ro.sys.customer.naviheight"

    const-string v14, ""

    invoke-static {v13, v14}, Lcom/android/systemui/util/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v13

    invoke-static {v13}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v13

    .line 233
    .local v13, "bottomBgHeight":I
    iget-object v14, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    const-string/jumbo v15, "window"

    invoke-virtual {v14, v15}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v14

    check-cast v14, Landroid/view/WindowManager;

    .line 234
    .local v14, "wm":Landroid/view/WindowManager;
    invoke-interface {v14}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v15

    invoke-virtual {v15}, Landroid/view/Display;->getWidth()I

    move-result v15

    .line 235
    .local v15, "bottomBgWidth":I
    iget-object v2, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    invoke-virtual {v2}, Landroid/widget/TextView;->getTextSize()F

    move-result v2

    float-to-int v2, v2

    .line 236
    .local v2, "textSize":I
    const-string v4, "InitViewImpl"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v16, v14

    const-string v14, "mTvSoundVolume-not null--bottombgWidth-"

    .line 236
    .end local v14    # "wm":Landroid/view/WindowManager;
    .local v16, "wm":Landroid/view/WindowManager;
    invoke-virtual {v3, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v15}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v14, "--bottombgHtight--"

    invoke-virtual {v3, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v13}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v4, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 237
    const-string v3, "InitViewImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "mTvSoundVolume-not null--textSize-"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v14, "--screenWidth--"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v15}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 238
    if-eqz v7, :cond_12

    if-eqz v8, :cond_12

    .line 239
    iget-object v3, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/res/Configuration;->getLayoutDirection()I

    move-result v3

    const/4 v4, 0x1

    if-ne v3, v4, :cond_11

    .line 240
    sub-int v3, v13, v2

    sub-int/2addr v3, v8

    sub-int v4, v15, v7

    sub-int/2addr v4, v2

    const/4 v14, 0x0

    invoke-virtual {v0, v14, v3, v4, v14}, Landroid/view/ViewGroup$MarginLayoutParams;->setMargins(IIII)V

    .line 241
    const-string v3, "InitViewImpl"

    const-string v4, "mTvSoundVolume-not null right"

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_2

    .line 243
    :cond_11
    const/4 v14, 0x0

    sub-int v3, v13, v2

    sub-int/2addr v3, v8

    invoke-virtual {v0, v7, v3, v14, v14}, Landroid/view/ViewGroup$MarginLayoutParams;->setMargins(IIII)V

    .line 244
    const-string v3, "InitViewImpl"

    const-string v4, "mTvSoundVolume-not null left"

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 246
    :goto_2
    iget-object v3, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    invoke-virtual {v3, v0}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 248
    :cond_12
    if-eqz v11, :cond_13

    if-eqz v12, :cond_13

    .line 249
    sub-int v3, v15, v11

    sub-int/2addr v3, v10

    sub-int v4, v13, v9

    sub-int/2addr v4, v12

    const/4 v14, 0x0

    invoke-virtual {v6, v3, v4, v14, v14}, Landroid/view/ViewGroup$MarginLayoutParams;->setMargins(IIII)V

    .line 250
    iget-object v3, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mPlayImg:Landroid/widget/ImageView;

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 252
    :cond_13
    const-string v3, "InitViewImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "mTvSoundVolume-not null---"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v14, "----"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 253
    const-string v3, "InitViewImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "mTvSoundVolume-not null---"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v14, "----"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v12}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 254
    const-string v3, "InitViewImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "mTvSoundVolume-not null--playImgHeight--"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v14, "--playImgWidth--"

    invoke-virtual {v4, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 256
    .end local v0    # "tvSoundVolumeParams":Landroid/view/ViewGroup$MarginLayoutParams;
    .end local v2    # "textSize":I
    .end local v6    # "tvPlayImgParams":Landroid/view/ViewGroup$MarginLayoutParams;
    .end local v7    # "tvSoundVolumeMarginStart":I
    .end local v8    # "tvSoundVolumeMarginBottom":I
    .end local v9    # "playImgHeight":I
    .end local v10    # "playImgWidth":I
    .end local v11    # "playImgMarginEnd":I
    .end local v12    # "playImgMarginBottom":I
    .end local v13    # "bottomBgHeight":I
    .end local v15    # "bottomBgWidth":I
    .end local v16    # "wm":Landroid/view/WindowManager;
    :cond_14
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->isNewBottomUI(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_16

    .line 257
    const-string v0, "InitViewImpl"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "initView---"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mPackageName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "----"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mUiMode:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, "---"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 258
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    const-string v2, "config_change_theme"

    invoke-virtual {v0, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v0

    .line 259
    .local v0, "configChangeTheme":I
    const/4 v2, 0x1

    if-ne v0, v2, :cond_16

    .line 260
    iget v2, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mUiMode:I

    and-int/lit8 v2, v2, 0x30

    const/16 v3, 0x20

    if-ne v2, v3, :cond_15

    const-string v2, "night"

    goto :goto_3

    :cond_15
    const-string v2, ""

    :goto_3
    invoke-virtual {v1, v2}, Lcom/hcn/auto/systemui/InitViewImpl;->updateBottomBgUIStyle(Ljava/lang/String;)V

    .line 264
    .end local v0    # "configChangeTheme":I
    :cond_16
    :try_start_0
    const-string v0, "darv_apps"

    invoke-virtual {v1, v0}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iput-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 265
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    if-nez v0, :cond_17

    .line 266
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mView:Landroid/view/View;

    const v2, 0x7f0a0109

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iput-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 268
    :cond_17
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    if-nez v0, :cond_18

    .line 269
    return-void

    .line 272
    :cond_18
    iget-object v0, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f08075e

    invoke-static {v0, v2}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 273
    .local v0, "bitmap":Landroid/graphics/Bitmap;
    iget-object v2, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f070516

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v2

    float-to-int v2, v2

    .line 274
    .local v2, "newWidth":I
    iget-object v3, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const v4, 0x7f070515

    invoke-virtual {v3, v4}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v3

    float-to-int v3, v3

    .line 275
    .local v3, "newHeight":I
    invoke-direct {v1, v0, v2, v3}, Lcom/hcn/auto/systemui/InitViewImpl;->resizeBitmapDrawable(Landroid/graphics/Bitmap;II)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    .line 276
    .local v4, "dialogBackground":Landroid/graphics/drawable/Drawable;
    new-instance v6, Lcom/hcn/auto/systemui/RecyclerViewDialog;

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-direct {v6, v7}, Lcom/hcn/auto/systemui/RecyclerViewDialog;-><init>(Landroid/content/Context;)V

    iput-object v6, v1, Lcom/hcn/auto/systemui/InitViewImpl;->dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

    .line 277
    iget-object v6, v1, Lcom/hcn/auto/systemui/InitViewImpl;->dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

    const/4 v7, 0x1

    invoke-virtual {v6, v7}, Lcom/hcn/auto/systemui/RecyclerViewDialog;->setLayoutType(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    const/4 v7, 0x3

    .line 278
    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setRowCount(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    const/4 v7, 0x5

    .line 279
    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setColumnCount(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 280
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f06019d

    iget-object v9, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v9}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v9

    invoke-virtual {v7, v8, v9}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setTitleColor(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 281
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f070519

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setItemIconSize(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 282
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f07051b

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setItemTextSize(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 283
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f07051e

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setDialogWidth(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 284
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f070517

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setDialogHeight(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 285
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f070518

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setItemHeight(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 286
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f07051c

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setItemWidth(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 287
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f07051a

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setItemTextDrawablePadding(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 288
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f06019c

    iget-object v9, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v9}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v9

    invoke-virtual {v7, v8, v9}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setItemTextColor(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    .line 289
    invoke-virtual {v6, v4}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setDialogBackground(Landroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 290
    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f07051d

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v7

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setRecyclerViewTopMargin(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v6

    .line 291
    invoke-virtual {v6}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->create()V

    .line 293
    iget-object v6, v1, Lcom/hcn/auto/systemui/InitViewImpl;->dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

    invoke-virtual {v6}, Lcom/hcn/auto/systemui/RecyclerViewDialog;->getRootView()Landroid/view/View;

    move-result-object v6

    new-instance v7, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$1Pzt69nQyruZilZ-eLll1aq0kwQ;

    invoke-direct {v7, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$1Pzt69nQyruZilZ-eLll1aq0kwQ;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v6, v7}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 295
    iget-object v6, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    iget-object v7, v1, Lcom/hcn/auto/systemui/InitViewImpl;->dialog:Lcom/hcn/auto/systemui/RecyclerViewDialog;

    .line 296
    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->setDialog(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    move-result-object v6

    sget-object v7, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$mxqmUY0EJCYHlMf2SsOquMgRUhM;->INSTANCE:Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$mxqmUY0EJCYHlMf2SsOquMgRUhM;

    .line 297
    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->setAppIconFilter(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;)V

    .line 302
    iget-object v6, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    new-instance v7, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$G-vVoBvoDx8Zp9Ab_7B-ayZ2pvA;

    invoke-direct {v7, v1}, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$G-vVoBvoDx8Zp9Ab_7B-ayZ2pvA;-><init>(Lcom/hcn/auto/systemui/InitViewImpl;)V

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->setAppIconLoader(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;)V

    .line 320
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v6

    const-string v7, "app_icon"

    const-string v8, "layout"

    invoke-virtual {v6, v7, v8}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v6

    .line 321
    .local v6, "layoutId":I
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v7

    const-string v8, "icon_add_selector"

    invoke-virtual {v7, v8}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 322
    .local v7, "addDrawable":Landroid/graphics/drawable/Drawable;
    iget-object v8, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v8}, Lcom/hcn/auto/systemui/InitViewImpl;->isNewBottomUI(Ljava/lang/String;)Z

    move-result v8

    if-eqz v8, :cond_19

    if-eqz v6, :cond_19

    if-eqz v7, :cond_19

    .line 323
    iget-object v8, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    new-instance v9, Lcom/hcn/auto/systemui/IconAdapter;

    invoke-direct {v9, v6}, Lcom/hcn/auto/systemui/IconAdapter;-><init>(I)V

    iget-object v10, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    const-string v10, "launcher_dynamic_app"

    const/4 v11, 0x0

    invoke-virtual {v8, v9, v7, v11, v10}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initAppIconRecycleView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;Landroid/graphics/drawable/Drawable;ILjava/lang/String;)V

    goto :goto_4

    .line 326
    :cond_19
    const-string v8, "persist.sys.skin"

    const-string v9, ""

    invoke-static {v8, v9}, Lcom/hcn/auto/systemui/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 327
    .local v8, "configSkin":Ljava/lang/String;
    const-string/jumbo v9, "xt617"

    invoke-virtual {v9, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-nez v9, :cond_1a

    const-string/jumbo v9, "xt618"

    invoke-virtual {v9, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_1b

    .line 328
    :cond_1a
    iget-object v9, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    new-instance v10, Lcom/hcn/auto/systemui/IconAdapter;

    const v11, 0x7f0d01cf

    invoke-direct {v10, v11}, Lcom/hcn/auto/systemui/IconAdapter;-><init>(I)V

    iget-object v11, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    .line 329
    invoke-virtual {v11}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v11

    const v12, 0x7f08075f

    iget-object v13, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v13}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v13

    invoke-virtual {v11, v12, v13}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v11

    iget-object v12, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    const-string v12, "launcher_dynamic_app"

    .line 328
    const/4 v13, 0x0

    invoke-virtual {v9, v10, v11, v13, v12}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initAppIconRecycleView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;Landroid/graphics/drawable/Drawable;ILjava/lang/String;)V

    .line 333
    .end local v8    # "configSkin":Ljava/lang/String;
    :cond_1b
    :goto_4
    iget-object v8, v1, Lcom/hcn/auto/systemui/InitViewImpl;->mDynamicAppRecyclerView:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    invoke-virtual {v8}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->create()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 337
    .end local v0    # "bitmap":Landroid/graphics/Bitmap;
    .end local v2    # "newWidth":I
    .end local v3    # "newHeight":I
    .end local v4    # "dialogBackground":Landroid/graphics/drawable/Drawable;
    .end local v6    # "layoutId":I
    .end local v7    # "addDrawable":Landroid/graphics/drawable/Drawable;
    goto :goto_5

    .line 335
    :catch_0
    move-exception v0

    .line 336
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 338
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_5
    return-void
.end method

.method public isNewBottomUI(Ljava/lang/String;)Z
    .locals 4
    .param p1, "skinName"    # Ljava/lang/String;

    .line 575
    invoke-static {}, Lcom/android/systemui/SystemUIApplication;->hasBottomBar()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    .line 576
    const-string v0, "InitViewImpl"

    const-string v2, "notNewBottomUI---false"

    invoke-static {v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 577
    return v1

    .line 579
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "com.hcn.systemui.theme."

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/hcn/auto/systemui/Utils;->getApkPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 580
    .local v0, "apkPath":Ljava/lang/String;
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_1

    .line 581
    const-string v1, "InitViewImpl"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "isNewBottomUI---true"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 582
    const/4 v1, 0x1

    return v1

    .line 584
    :cond_1
    const-string v2, "InitViewImpl"

    const-string v3, "notNewBottomUI---false"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 585
    return v1
.end method

.method public onVolumeChanged(IIZ)V
    .locals 7
    .param p1, "stream"    # I
    .param p2, "volume"    # I
    .param p3, "mute"    # Z

    .line 526
    move v0, p2

    .line 527
    .local v0, "vol":I
    const/4 v1, 0x3

    if-ne p1, v1, :cond_0

    if-eqz p3, :cond_0

    .line 528
    const/4 v0, 0x0

    .line 530
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mResources:Landroid/content/res/Resources;

    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mPackageName:Ljava/lang/String;

    invoke-virtual {v1, v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->setupSkin(Landroid/content/res/Resources;Ljava/lang/String;)V

    .line 531
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v2, "%02d"

    const/4 v3, 0x1

    new-array v4, v3, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/4 v6, 0x0

    aput-object v5, v4, v6

    invoke-static {v1, v2, v4}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 532
    .local v1, "value":Ljava/lang/String;
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    if-eqz v2, :cond_4

    .line 534
    if-eqz p2, :cond_2

    .line 535
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {p0, v2}, Lcom/hcn/auto/systemui/InitViewImpl;->isNewBottomUI(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_1

    const-string v2, "btn_mute"

    invoke-virtual {p0, v2}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v2

    if-eqz v2, :cond_1

    .line 536
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v4, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, "_icon_volume_selector"

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 537
    .local v2, "resVolumeName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    invoke-virtual {v4, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    .line 538
    .local v4, "resVolumeDraw":Landroid/graphics/drawable/Drawable;
    iget-object v5, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    invoke-virtual {v5, v4}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 539
    .end local v2    # "resVolumeName":Ljava/lang/String;
    .end local v4    # "resVolumeDraw":Landroid/graphics/drawable/Drawable;
    goto :goto_0

    .line 540
    :cond_1
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    const v4, 0x7f0807b5

    invoke-virtual {v2, v4}, Landroid/widget/ImageButton;->setBackgroundResource(I)V

    goto :goto_0

    .line 543
    :cond_2
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {p0, v2}, Lcom/hcn/auto/systemui/InitViewImpl;->isNewBottomUI(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_3

    const-string v2, "btn_mute"

    invoke-virtual {p0, v2}, Lcom/hcn/auto/systemui/InitViewImpl;->findViewByName(Ljava/lang/String;)Landroid/view/View;

    move-result-object v2

    if-eqz v2, :cond_3

    .line 544
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v4, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, "_icon_mute_selector"

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 545
    .local v2, "resMuteName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    invoke-virtual {v4, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    .line 546
    .local v4, "resMuteDraw":Landroid/graphics/drawable/Drawable;
    iget-object v5, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    invoke-virtual {v5, v4}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 547
    .end local v2    # "resMuteName":Ljava/lang/String;
    .end local v4    # "resMuteDraw":Landroid/graphics/drawable/Drawable;
    goto :goto_0

    .line 548
    :cond_3
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMute:Landroid/widget/ImageButton;

    const v4, 0x7f0807b1

    invoke-virtual {v2, v4}, Landroid/widget/ImageButton;->setBackgroundResource(I)V

    .line 552
    :cond_4
    :goto_0
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    if-eqz v2, :cond_7

    .line 553
    invoke-direct {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->isBackCar()Z

    move-result v2

    if-eqz v2, :cond_5

    .line 554
    iget v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    const/4 v4, 0x2

    if-ge v2, v4, :cond_7

    .line 555
    iget v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    add-int/2addr v2, v3

    iput v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    goto :goto_1

    .line 557
    :cond_5
    invoke-direct {p0}, Lcom/hcn/auto/systemui/InitViewImpl;->isBackCar()Z

    move-result v2

    if-nez v2, :cond_6

    iget v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    if-lez v2, :cond_6

    .line 558
    iget v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    sub-int/2addr v2, v3

    iput v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mIsReversing:I

    goto :goto_1

    .line 560
    :cond_6
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 563
    :cond_7
    :goto_1
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mMuteImg:Landroid/widget/ImageView;

    if-eqz v2, :cond_9

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    if-eqz v2, :cond_9

    .line 564
    const/16 v2, 0x8

    if-eqz p2, :cond_8

    .line 565
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mMuteImg:Landroid/widget/ImageView;

    invoke-virtual {v3, v2}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 566
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    invoke-virtual {v2, v6}, Landroid/widget/TextView;->setVisibility(I)V

    goto :goto_2

    .line 568
    :cond_8
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mMuteImg:Landroid/widget/ImageView;

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 569
    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTvSoundVolume:Landroid/widget/TextView;

    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setVisibility(I)V

    .line 572
    :cond_9
    :goto_2
    return-void
.end method

.method public unregisterTimeReceiver()V
    .locals 2

    .line 515
    const-string v0, "InitViewImpl"

    const-string v1, "unregisterTimeReceiver"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 516
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->timeReceiver:Landroid/content/BroadcastReceiver;

    if-eqz v0, :cond_0

    .line 518
    :try_start_0
    iget-object v0, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/hcn/auto/systemui/InitViewImpl;->timeReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 521
    goto :goto_0

    .line 519
    :catch_0
    move-exception v0

    .line 520
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 523
    .end local v0    # "e":Ljava/lang/Exception;
    :cond_0
    :goto_0
    return-void
.end method

.method public updateBottomBgUIStyle(Ljava/lang/String;)V
    .locals 6
    .param p1, "uiStyle"    # Ljava/lang/String;

    .line 607
    const-string v0, ""

    .line 608
    .local v0, "resName":Ljava/lang/String;
    const-string v1, "night"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 609
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "_bottom_bg_"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    .line 611
    :cond_0
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "_bottom_bg"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 613
    :goto_0
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 614
    .local v1, "drawable":Landroid/graphics/drawable/Drawable;
    if-eqz v1, :cond_7

    .line 615
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->bottomBg:Landroid/widget/RelativeLayout;

    invoke-virtual {v2, v1}, Landroid/widget/RelativeLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 616
    const-string v2, "night"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 617
    const-string p1, "_night"

    goto :goto_1

    .line 619
    :cond_1
    const-string p1, ""

    .line 621
    :goto_1
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    if-eqz v2, :cond_2

    .line 622
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "_icon_all_apps_selector"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 623
    .local v2, "resAllAppsName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    .line 624
    .local v3, "resAllAppsDraw":Landroid/graphics/drawable/Drawable;
    iget-object v4, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnAllApp:Landroid/widget/ImageButton;

    invoke-virtual {v4, v3}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 626
    .end local v2    # "resAllAppsName":Ljava/lang/String;
    .end local v3    # "resAllAppsDraw":Landroid/graphics/drawable/Drawable;
    :cond_2
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    if-eqz v2, :cond_3

    .line 627
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "_icon_navi_selector"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 628
    .local v2, "resNaviName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    .line 629
    .local v3, "resNaviDraw":Landroid/graphics/drawable/Drawable;
    iget-object v4, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnNavi:Landroid/widget/ImageButton;

    invoke-virtual {v4, v3}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 631
    .end local v2    # "resNaviName":Ljava/lang/String;
    .end local v3    # "resNaviDraw":Landroid/graphics/drawable/Drawable;
    :cond_3
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    if-eqz v2, :cond_4

    .line 632
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "_icon_music_selector"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 633
    .local v2, "resMusicName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    .line 634
    .local v3, "resMusicDraw":Landroid/graphics/drawable/Drawable;
    iget-object v4, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnMusic:Landroid/widget/ImageButton;

    invoke-virtual {v4, v3}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 636
    .end local v2    # "resMusicName":Ljava/lang/String;
    .end local v3    # "resMusicDraw":Landroid/graphics/drawable/Drawable;
    :cond_4
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    if-eqz v2, :cond_5

    .line 637
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "_icon_radio_selector"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 638
    .local v2, "resRadioName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    .line 639
    .local v3, "resRadioDraw":Landroid/graphics/drawable/Drawable;
    iget-object v4, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mBtnRadio:Landroid/widget/ImageButton;

    invoke-virtual {v4, v3}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 641
    .end local v2    # "resRadioName":Ljava/lang/String;
    .end local v3    # "resRadioDraw":Landroid/graphics/drawable/Drawable;
    :cond_5
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    if-eqz v2, :cond_7

    .line 642
    const-string v2, "_night"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_6

    .line 643
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v5, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, "_volume_text_color"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColor(Ljava/lang/String;)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setTextColor(I)V

    goto :goto_2

    .line 645
    :cond_6
    iget-object v2, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mTime:Landroid/widget/TextView;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v5, p0, Lcom/hcn/auto/systemui/InitViewImpl;->mCurrentSkin:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, "_volume_text_color"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getColor(Ljava/lang/String;)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setTextColor(I)V

    .line 649
    :cond_7
    :goto_2
    return-void
.end method
