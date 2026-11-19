.class public Lcom/autochips/bluetooth/MainBluetoothActivity;
.super Lcom/hcn/skin/support/app/SkinCompatActivity;
.source "MainBluetoothActivity.java"

# interfaces
.implements Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;
.implements Landroid/view/View$OnClickListener;
.implements Lcom/autochips/bluetooth/IFragmentCallback;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/MainBluetoothActivity$MyHandler;
    }
.end annotation


# static fields
.field private static final MSG_UPDATE_CTRL:I = 0x0

.field public static final PAGE_FIVE:I = 0x4

.field public static final PAGE_FOUR:I = 0x3

.field public static final PAGE_ONE:I = 0x0

.field public static final PAGE_THREE:I = 0x2

.field public static final PAGE_TWO:I = 0x1

.field private static final TAG:Ljava/lang/String; = "MainBluetoothActivity"

.field public static final VOICE_EVENT_BT_EXIT:Ljava/lang/String; = "com.bt.extra.exit"


# instance fields
.field private dialFragment:Landroidx/fragment/app/Fragment;

.field private fragments:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroidx/fragment/app/Fragment;",
            ">;"
        }
    .end annotation
.end field

.field private isConnected:Z

.field private isUseSkinLayout:Z

.field private layoutDrawable:Landroid/graphics/drawable/Drawable;

.field private llBottomMenu:Landroid/view/ViewGroup;

.field private mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field private mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mBtnContract:Landroid/view/View;

.field private mBtnDial:Landroid/view/View;

.field private mBtnPaired:Landroid/view/View;

.field private mBtnRecord:Landroid/view/View;

.field private mBtnSetting:Landroid/view/View;

.field private mFragmentViewPaperAdapter:Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;

.field private mHandler:Landroid/os/Handler;

.field private mReceiver:Landroid/content/BroadcastReceiver;

.field private mSelectedView:Landroid/view/View;

.field private mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

.field private mainLayout:Landroid/view/ViewGroup;


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 50
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;-><init>()V

    .line 75
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    const/4 v0, 0x0

    .line 77
    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    .line 81
    iput-boolean v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    .line 82
    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mainLayout:Landroid/view/ViewGroup;

    .line 83
    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->layoutDrawable:Landroid/graphics/drawable/Drawable;

    .line 84
    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->llBottomMenu:Landroid/view/ViewGroup;

    .line 85
    iput-boolean v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isUseSkinLayout:Z

    .line 87
    new-instance v0, Lcom/autochips/bluetooth/MainBluetoothActivity$1;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/MainBluetoothActivity$1;-><init>(Lcom/autochips/bluetooth/MainBluetoothActivity;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 468
    new-instance v0, Lcom/autochips/bluetooth/MainBluetoothActivity$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/MainBluetoothActivity$2;-><init>(Lcom/autochips/bluetooth/MainBluetoothActivity;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    return-void
.end method

.method static synthetic access$002(Lcom/autochips/bluetooth/MainBluetoothActivity;Z)Z
    .locals 0

    .line 50
    iput-boolean p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    return p1
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/MainBluetoothActivity;)V
    .locals 0

    .line 50
    invoke-direct {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->updateView()V

    return-void
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/MainBluetoothActivity;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 50
    iget-object p0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/MainBluetoothActivity;)Landroid/os/Handler;
    .locals 0

    .line 50
    iget-object p0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mHandler:Landroid/os/Handler;

    return-object p0
.end method

.method private getPairedFragment()Landroidx/fragment/app/Fragment;
    .locals 2

    const v0, 0x7f090007

    .line 189
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getInteger(I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 190
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-direct {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;-><init>()V

    .line 191
    move-object v1, v0

    check-cast v1, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-virtual {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->setFragmentCallback(Lcom/autochips/bluetooth/IFragmentCallback;)V

    goto :goto_0

    .line 193
    :cond_0
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-direct {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment;-><init>()V

    :goto_0
    return-object v0
.end method

.method private getSetupFragment()Landroidx/fragment/app/Fragment;
    .locals 2

    const v0, 0x7f090009

    .line 205
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getInteger(I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 206
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-direct {v0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;-><init>()V

    .line 207
    move-object v1, v0

    check-cast v1, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;

    invoke-virtual {v0, p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->setFragmentCallback(Lcom/autochips/bluetooth/IFragmentCallback;)V

    goto :goto_0

    .line 209
    :cond_0
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragment;

    invoke-direct {v0}, Lcom/autochips/bluetooth/fragment/SetupFragment;-><init>()V

    :goto_0
    return-object v0
.end method

.method private initView()V
    .locals 3

    const v0, 0x7f0800e5

    .line 228
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mainLayout:Landroid/view/ViewGroup;

    .line 229
    invoke-static {}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getAppWallpaper()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 230
    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mainLayout:Landroid/view/ViewGroup;

    if-eqz v1, :cond_0

    if-eqz v0, :cond_0

    .line 231
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 233
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mainLayout:Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    .line 234
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->layoutDrawable:Landroid/graphics/drawable/Drawable;

    :cond_1
    const v0, 0x7f080044

    .line 237
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->llBottomMenu:Landroid/view/ViewGroup;

    const v0, 0x7f080097

    .line 238
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnDial:Landroid/view/View;

    const v0, 0x7f08008f

    .line 239
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnContract:Landroid/view/View;

    const v0, 0x7f080092

    .line 240
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnRecord:Landroid/view/View;

    const v0, 0x7f080094

    .line 241
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnSetting:Landroid/view/View;

    const v0, 0x7f08008e

    .line 242
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnPaired:Landroid/view/View;

    const v0, 0x7f09000a

    .line 244
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getInteger(I)I

    move-result v0

    if-eqz v0, :cond_2

    .line 245
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnSetting:Landroid/view/View;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    :cond_2
    const v0, 0x7f08017c

    .line 248
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    const/4 v1, 0x5

    .line 249
    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setOffscreenPageLimit(I)V

    .line 251
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnDial:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 252
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnContract:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 253
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnRecord:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 254
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnPaired:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 255
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnSetting:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 257
    new-instance v0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->getSupportFragmentManager()Landroidx/fragment/app/FragmentManager;

    move-result-object v1

    iget-object v2, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    invoke-direct {v0, v1, v2}, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;-><init>(Landroidx/fragment/app/FragmentManager;Ljava/util/List;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mFragmentViewPaperAdapter:Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;

    .line 259
    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    invoke-virtual {v1, v0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setAdapter(Landroidx/viewpager/widget/PagerAdapter;)V

    .line 260
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    const/4 v1, 0x1

    new-instance v2, Lcom/autochips/bluetooth/viewpager/transforms/DepthPageTransformer;

    invoke-direct {v2}, Lcom/autochips/bluetooth/viewpager/transforms/DepthPageTransformer;-><init>()V

    invoke-virtual {v0, v1, v2}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setPageTransformer(ZLandroidx/viewpager/widget/ViewPager$PageTransformer;)V

    .line 261
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    invoke-virtual {v0, p0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->addOnPageChangeListener(Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;)V

    return-void
.end method

.method private selectPage(I)V
    .locals 2

    .line 265
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    const/4 v1, 0x0

    invoke-virtual {v0, p1, v1}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setCurrentItem(IZ)V

    return-void
.end method

.method private updateView()V
    .locals 2

    .line 215
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    if-nez v0, :cond_0

    return-void

    .line 218
    :cond_0
    iget-boolean v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    if-nez v1, :cond_1

    .line 219
    invoke-virtual {v0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->getCurrentItem()I

    move-result v0

    if-ltz v0, :cond_1

    const/4 v1, 0x2

    if-gt v0, v1, :cond_1

    const/4 v0, 0x3

    .line 221
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    .line 224
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    iget-boolean v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setScanScroll(Z)Z

    return-void
.end method


# virtual methods
.method protected init()V
    .locals 6

    .line 164
    invoke-static {}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getInstance()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->init(Landroid/content/Context;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 165
    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 166
    new-instance v0, Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->dialFragment:Landroidx/fragment/app/Fragment;

    .line 167
    new-instance v0, Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-direct {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;-><init>()V

    .line 168
    new-instance v1, Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-direct {v1}, Lcom/autochips/bluetooth/fragment/PBFragment;-><init>()V

    .line 169
    invoke-direct {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->getSetupFragment()Landroidx/fragment/app/Fragment;

    move-result-object v2

    .line 170
    invoke-direct {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->getPairedFragment()Landroidx/fragment/app/Fragment;

    move-result-object v3

    .line 172
    iget-object v4, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    iget-object v5, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->dialFragment:Landroidx/fragment/app/Fragment;

    invoke-interface {v4, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 173
    iget-object v4, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 174
    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 175
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    invoke-interface {v0, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    const v0, 0x7f090009

    .line 177
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getInteger(I)I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_0

    .line 178
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->fragments:Ljava/util/List;

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 2

    .line 303
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    .line 304
    iget-boolean v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isUseSkinLayout:Z

    if-eqz v1, :cond_0

    .line 305
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result v0

    :cond_0
    const v1, 0x7f0d0082

    sparse-switch v0, :sswitch_data_0

    goto :goto_0

    .line 310
    :sswitch_0
    iget-boolean v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    .line 311
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    .line 312
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 314
    :cond_1
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_0

    :sswitch_1
    const/4 v0, 0x4

    .line 338
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    .line 339
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 326
    :sswitch_2
    iget-boolean v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    if-eqz v0, :cond_2

    const/4 v0, 0x2

    .line 327
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    .line 328
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 330
    :cond_2
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_0

    .line 318
    :sswitch_3
    iget-boolean v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    if-eqz v0, :cond_3

    const/4 v0, 0x1

    .line 319
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    .line 320
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 322
    :cond_3
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_0

    :sswitch_4
    const/4 v0, 0x3

    .line 334
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    .line 335
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    :goto_0
    return-void

    nop

    :sswitch_data_0
    .sparse-switch
        0x7f08008e -> :sswitch_4
        0x7f08008f -> :sswitch_3
        0x7f080092 -> :sswitch_2
        0x7f080094 -> :sswitch_1
        0x7f080097 -> :sswitch_0
    .end sparse-switch
.end method

.method public onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 0

    .line 369
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 4

    .line 118
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/4 v1, 0x0

    const/16 v2, 0x15

    if-lt v0, v2, :cond_0

    .line 119
    invoke-virtual {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->getWindow()Landroid/view/Window;

    move-result-object v0

    const/high16 v2, 0x4000000

    .line 120
    invoke-virtual {v0, v2}, Landroid/view/Window;->clearFlags(I)V

    .line 121
    invoke-virtual {v0}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v2

    const/16 v3, 0x500

    invoke-virtual {v2, v3}, Landroid/view/View;->setSystemUiVisibility(I)V

    const/high16 v2, -0x80000000

    .line 123
    invoke-virtual {v0, v2}, Landroid/view/Window;->addFlags(I)V

    .line 124
    invoke-virtual {v0, v1}, Landroid/view/Window;->setStatusBarColor(I)V

    :cond_0
    const-string v0, "MainBluetoothActivity"

    const-string v2, "onCreate()"

    .line 126
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 127
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onCreate(Landroid/os/Bundle;)V

    .line 128
    new-instance p1, Lcom/autochips/bluetooth/MainBluetoothActivity$MyHandler;

    invoke-direct {p1, p0}, Lcom/autochips/bluetooth/MainBluetoothActivity$MyHandler;-><init>(Lcom/autochips/bluetooth/MainBluetoothActivity;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mHandler:Landroid/os/Handler;

    const p1, 0x7f0b001d

    .line 129
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setContentView(I)V

    .line 130
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->useSkinPackage()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 131
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->isResourcesExist(I)I

    move-result p1

    if-lez p1, :cond_1

    const/4 v1, 0x1

    .line 132
    :cond_1
    iput-boolean v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isUseSkinLayout:Z

    .line 134
    :cond_2
    invoke-virtual {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->init()V

    .line 135
    invoke-direct {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->initView()V

    const/4 p1, 0x3

    .line 136
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->selectPage(I)V

    return-void
.end method

.method protected onDestroy()V
    .locals 2

    .line 415
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onDestroy()V

    .line 416
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    if-eqz v0, :cond_0

    .line 417
    invoke-virtual {v0, p0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->removeOnPageChangeListener(Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;)V

    .line 419
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    return-void
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 1

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 435
    :pswitch_0
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->getCurrentItem()I

    move-result v0

    if-nez v0, :cond_0

    .line 436
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->dialFragment:Landroidx/fragment/app/Fragment;

    check-cast v0, Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-virtual {v0, p1, p2}, Lcom/autochips/bluetooth/fragment/DialFragment;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1

    .line 442
    :cond_0
    :goto_0
    invoke-super {p0, p1, p2}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1

    :pswitch_data_0
    .packed-switch 0x7
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .locals 1

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 458
    :pswitch_0
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->getCurrentItem()I

    move-result v0

    if-nez v0, :cond_0

    .line 459
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->dialFragment:Landroidx/fragment/app/Fragment;

    check-cast v0, Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-virtual {v0, p1, p2}, Lcom/autochips/bluetooth/fragment/DialFragment;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1

    .line 465
    :cond_0
    :goto_0
    invoke-super {p0, p1, p2}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1

    :pswitch_data_0
    .packed-switch 0x7
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public onPageScrollStateChanged(I)V
    .locals 0

    return-void
.end method

.method public onPageScrolled(IFI)V
    .locals 0

    return-void
.end method

.method public onPageSelected(I)V
    .locals 1

    if-eqz p1, :cond_4

    const/4 v0, 0x1

    if-eq p1, v0, :cond_3

    const/4 v0, 0x2

    if-eq p1, v0, :cond_2

    const/4 v0, 0x3

    if-eq p1, v0, :cond_1

    const/4 v0, 0x4

    if-eq p1, v0, :cond_0

    goto :goto_0

    .line 290
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnSetting:Landroid/view/View;

    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 287
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnPaired:Landroid/view/View;

    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 284
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnRecord:Landroid/view/View;

    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 281
    :cond_3
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnContract:Landroid/view/View;

    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    goto :goto_0

    .line 278
    :cond_4
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mBtnDial:Landroid/view/View;

    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/MainBluetoothActivity;->setViewSelected(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method protected onPause()V
    .locals 2

    const-string v0, "MainBluetoothActivity"

    const-string v1, "onPause()"

    .line 401
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 402
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onPause()V

    return-void
.end method

.method protected onResume()V
    .locals 2

    const-string v0, "MainBluetoothActivity"

    const-string v1, "onResume()"

    .line 391
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 392
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onResume()V

    .line 393
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothConnected()Z

    move-result v0

    iput-boolean v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->isConnected:Z

    .line 394
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    .line 395
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method protected onSaveInstanceState(Landroid/os/Bundle;)V
    .locals 0

    return-void
.end method

.method protected onStart()V
    .locals 2

    .line 380
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onStart()V

    .line 381
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    const-string v1, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 382
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v1, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 383
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v1, "com.bt.extra.exit"

    .line 384
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 385
    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v1, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    return-void
.end method

.method protected onStop()V
    .locals 1

    .line 407
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onStop()V

    .line 408
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mReceiver:Landroid/content/BroadcastReceiver;

    if-eqz v0, :cond_0

    .line 409
    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    :cond_0
    return-void
.end method

.method protected onUserLeaveHint()V
    .locals 2

    .line 374
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onUserLeaveHint()V

    const-string v0, "MainBluetoothActivity"

    const-string v1, "onUserLeaveHint"

    .line 375
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public setViewSelected(Landroid/view/View;)V
    .locals 2

    .line 352
    iget-object v0, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mSelectedView:Landroid/view/View;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 353
    invoke-virtual {v0, v1}, Landroid/view/View;->setSelected(Z)V

    :cond_0
    const/4 v0, 0x1

    .line 355
    invoke-virtual {p1, v0}, Landroid/view/View;->setSelected(Z)V

    .line 356
    iput-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mSelectedView:Landroid/view/View;

    return-void
.end method

.method public updateBackground(Z)Z
    .locals 4

    .line 485
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "updateBackground: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainBluetoothActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    if-eqz p1, :cond_2

    .line 487
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mainLayout:Landroid/view/ViewGroup;

    if-eqz p1, :cond_0

    .line 488
    new-instance v1, Landroid/graphics/drawable/BitmapDrawable;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/MainBluetoothActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-static {p0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->getBlurBackgroundDrawer(Landroid/app/Activity;)Landroid/graphics/Bitmap;

    move-result-object v3

    invoke-direct {v1, v2, v3}, Landroid/graphics/drawable/BitmapDrawable;-><init>(Landroid/content/res/Resources;Landroid/graphics/Bitmap;)V

    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 490
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    const/4 v1, 0x4

    if-eqz p1, :cond_1

    .line 491
    invoke-virtual {p1, v1}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setVisibility(I)V

    .line 493
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->llBottomMenu:Landroid/view/ViewGroup;

    if-eqz p1, :cond_5

    .line 494
    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    goto :goto_0

    .line 497
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mainLayout:Landroid/view/ViewGroup;

    if-eqz p1, :cond_3

    .line 498
    iget-object v1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->layoutDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 500
    :cond_3
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->mViewPager:Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;

    if-eqz p1, :cond_4

    .line 501
    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->setVisibility(I)V

    .line 503
    :cond_4
    iget-object p1, p0, Lcom/autochips/bluetooth/MainBluetoothActivity;->llBottomMenu:Landroid/view/ViewGroup;

    if-eqz p1, :cond_5

    .line 504
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_5
    :goto_0
    return v0
.end method
