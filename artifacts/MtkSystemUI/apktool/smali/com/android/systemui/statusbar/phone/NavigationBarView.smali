.class public Lcom/android/systemui/statusbar/phone/NavigationBarView;
.super Landroid/widget/FrameLayout;
.source "NavigationBarView.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;
.implements Lcom/android/systemui/plugins/PluginListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;,
        Lcom/android/systemui/statusbar/phone/NavigationBarView$H;,
        Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/widget/FrameLayout;",
        "Landroid/view/View$OnTouchListener;",
        "Lcom/android/systemui/plugins/PluginListener<",
        "Lcom/android/systemui/plugins/statusbar/phone/NavGesture;",
        ">;"
    }
.end annotation


# instance fields
.field private final AC_Type_acauto:I

.field private final AC_Type_econ:I

.field private final AC_Type_max:I

.field private final AC_Type_normal:I

.field private final AC_Type_null:I

.field private final AIR_TEMP_REVERSE_FLAG:Ljava/lang/String;

.field private final Auto_Type_big:I

.field private final Auto_Type_null:I

.field private final Auto_Type_small:I

.field private curtAirTempMirror:Z

.field private final cycle_Type_NULL:I

.field private final cycle_Type_auto:I

.field private final cycle_Type_in:I

.field private final cycle_Type_out:I

.field private jellyProgressView:Lcom/android/systemui/volume/JellyProgressView;

.field private layout_air_func:Landroid/widget/LinearLayout;

.field private mAccessibilityIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mAirLayout:Landroid/widget/LinearLayout;

.field private final mAirTempMirrorCfgObserver:Landroid/database/ContentObserver;

.field protected mAudioManager:Landroid/media/AudioManager;

.field private mAutoDismissHandler:Landroid/os/Handler;

.field private mAutoDismissRunnable:Ljava/lang/Runnable;

.field private mBTConnectStatus:Landroid/widget/Button;

.field private mBackAltCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mBackAltLandCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private final mBackButtonBounds:Landroid/graphics/Rect;

.field private mBackCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mBackIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mBackLandCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private final mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

.field private final mButtonDispatchers:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Lcom/android/systemui/statusbar/phone/ButtonDispatcher;",
            ">;"
        }
    .end annotation
.end field

.field private final mCallback:Lcom/android/systemui/statusbar/policy/BluetoothController$Callback;

.field private mChangebySelf:Z

.field private mConfiguration:Landroid/content/res/Configuration;

.field private final mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

.field private mCurrentRotation:I

.field mCurrentView:Landroid/view/View;

.field private final mDeadZone:Lcom/android/systemui/statusbar/policy/DeadZone;

.field private mDeadZoneConsuming:Z

.field mDisabledFlags:I

.field final mDisplay:Landroid/view/Display;

.field private mDivider:Lcom/android/systemui/stackdivider/Divider;

.field private mDockedIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private final mDockedListener:Ljava/util/function/Consumer;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/function/Consumer<",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation
.end field

.field private mDockedStackExists:Z

.field private mDownHitTarget:I

.field private mFront_RZC:I

.field private mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

.field private mHandler:Lcom/android/systemui/statusbar/phone/NavigationBarView$H;

.field private final mHideMediaBtnRunnable:Ljava/lang/Runnable;

.field private final mHomeButtonBounds:Landroid/graphics/Rect;

.field private mHomeCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mHomeDefaultIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mImeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private final mImeSwitcherClickListener:Landroid/view/View$OnClickListener;

.field private mInCarMode:Z

.field private mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

.field private mLayoutTransitionsEnabled:Z

.field mLongClickableAccessibilityButton:Z

.field private mMediaBtnHandler:Landroid/os/Handler;

.field private mMenuIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private mNavBarPlugin:Lcom/mediatek/systemui/ext/INavigationBarPlugin;

.field mNavigationIconHints:I

.field private mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

.field private mOnVerticalChangedListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;

.field private final mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

.field protected mPackageName:Ljava/lang/String;

.field private mPanelView:Lcom/android/systemui/statusbar/phone/NotificationPanelView;

.field private final mQuickStepAccessibilityDelegate:Landroid/view/View$AccessibilityDelegate;

.field private mRearLock:I

.field private mRecentIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

.field private final mRecentsButtonBounds:Landroid/graphics/Rect;

.field private mRecentsComponent:Lcom/android/systemui/RecentsComponent;

.field private mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

.field protected mResources:Landroid/content/res/Resources;

.field private mRotateBtnStyle:I

.field private mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

.field mRotatedViews:[Landroid/view/View;

.field private final mRotationButtonBounds:Landroid/graphics/Rect;

.field private mSeekBarVolume:Landroid/widget/SeekBar;

.field mShowAccessibilityButton:Z

.field mShowMenu:Z

.field mShowRotateButton:Z

.field private mSystemUICustomizationFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

.field private final mTmpPosition:[I

.field private final mTmpRect:Landroid/graphics/Rect;

.field private final mTransitionListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;

.field private mTvSoundVolume:Landroid/widget/TextView;

.field private mUseCarModeUi:Z

.field mVertical:Z

.field private mVolumeDialog:Landroid/app/AlertDialog;

.field private mVolumeReceiver:Landroid/content/BroadcastReceiver;

.field private mWakeAndUnlocking:Z

.field private final myReceiver:Landroid/content/BroadcastReceiver;

.field private naviBottomNext:Landroid/widget/ImageButton;

.field private naviBottomPre:Landroid/widget/ImageButton;

.field old_level_max:I

.field skinName:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 478
    invoke-direct {p0, p1, p2}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 169
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentView:Landroid/view/View;

    .line 170
    const/4 v1, 0x4

    new-array v2, v1, [Landroid/view/View;

    iput-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotatedViews:[Landroid/view/View;

    .line 173
    const/4 v2, -0x1

    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentRotation:I

    .line 179
    const/4 v2, 0x0

    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    .line 180
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    .line 182
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    .line 184
    new-instance v3, Landroid/graphics/Rect;

    invoke-direct {v3}, Landroid/graphics/Rect;-><init>()V

    iput-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHomeButtonBounds:Landroid/graphics/Rect;

    .line 185
    new-instance v3, Landroid/graphics/Rect;

    invoke-direct {v3}, Landroid/graphics/Rect;-><init>()V

    iput-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackButtonBounds:Landroid/graphics/Rect;

    .line 186
    new-instance v3, Landroid/graphics/Rect;

    invoke-direct {v3}, Landroid/graphics/Rect;-><init>()V

    iput-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsButtonBounds:Landroid/graphics/Rect;

    .line 187
    new-instance v3, Landroid/graphics/Rect;

    invoke-direct {v3}, Landroid/graphics/Rect;-><init>()V

    iput-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotationButtonBounds:Landroid/graphics/Rect;

    .line 188
    const/4 v3, 0x2

    new-array v4, v3, [I

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpPosition:[I

    .line 189
    new-instance v4, Landroid/graphics/Rect;

    invoke-direct {v4}, Landroid/graphics/Rect;-><init>()V

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpRect:Landroid/graphics/Rect;

    .line 204
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZoneConsuming:Z

    .line 213
    new-instance v4, Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;

    invoke-direct {v4, p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;Lcom/android/systemui/statusbar/phone/NavigationBarView$1;)V

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTransitionListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;

    .line 216
    const/4 v4, 0x1

    iput-boolean v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mLayoutTransitionsEnabled:Z

    .line 218
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    .line 219
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInCarMode:Z

    .line 222
    new-instance v5, Landroid/util/SparseArray;

    invoke-direct {v5}, Landroid/util/SparseArray;-><init>()V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    .line 231
    const v5, 0x7f120118

    iput v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateBtnStyle:I

    .line 235
    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSystemUICustomizationFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    .line 248
    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    .line 249
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mChangebySelf:Z

    .line 251
    new-instance v5, Landroid/os/Handler;

    invoke-direct {v5}, Landroid/os/Handler;-><init>()V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mMediaBtnHandler:Landroid/os/Handler;

    .line 252
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$1;

    invoke-direct {v5, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$1;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHideMediaBtnRunnable:Ljava/lang/Runnable;

    .line 260
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRearLock:I

    .line 261
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mFront_RZC:I

    .line 270
    const-string v5, "air_temp_reverse_flag"

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->AIR_TEMP_REVERSE_FLAG:Ljava/lang/String;

    .line 271
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    .line 275
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$2;

    invoke-direct {v5, p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$2;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/os/Handler;)V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirTempMirrorCfgObserver:Landroid/database/ContentObserver;

    .line 409
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$3;

    invoke-direct {v5, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$3;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mImeSwitcherClickListener:Landroid/view/View$OnClickListener;

    .line 449
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$4;

    invoke-direct {v5, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$4;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mQuickStepAccessibilityDelegate:Landroid/view/View$AccessibilityDelegate;

    .line 617
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$H;

    invoke-direct {v5, p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$H;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;Lcom/android/systemui/statusbar/phone/NavigationBarView$1;)V

    iput-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHandler:Lcom/android/systemui/statusbar/phone/NavigationBarView$H;

    .line 1571
    new-instance v0, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarView$rZTKiGVTHMScp1MOeQG9jGW8tDA;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarView$rZTKiGVTHMScp1MOeQG9jGW8tDA;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedListener:Ljava/util/function/Consumer;

    .line 1853
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAutoDismissHandler:Landroid/os/Handler;

    .line 1854
    new-instance v0, Lcom/android/systemui/statusbar/phone/NavigationBarView$15;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$15;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAutoDismissRunnable:Ljava/lang/Runnable;

    .line 1893
    new-instance v0, Lcom/android/systemui/statusbar/phone/NavigationBarView$16;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$16;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeReceiver:Landroid/content/BroadcastReceiver;

    .line 2268
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->Auto_Type_big:I

    .line 2269
    iput v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->Auto_Type_small:I

    .line 2270
    const/4 v0, 0x3

    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->Auto_Type_null:I

    .line 2294
    const/4 v5, 0x7

    iput v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->old_level_max:I

    .line 2520
    iput v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cycle_Type_in:I

    .line 2521
    iput v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cycle_Type_out:I

    .line 2522
    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cycle_Type_auto:I

    .line 2523
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cycle_Type_NULL:I

    .line 3059
    iput v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->AC_Type_normal:I

    .line 3060
    iput v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->AC_Type_max:I

    .line 3061
    iput v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->AC_Type_null:I

    .line 3062
    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->AC_Type_acauto:I

    .line 3063
    iput v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->AC_Type_econ:I

    .line 3361
    new-instance v0, Lcom/android/systemui/statusbar/phone/NavigationBarView$18;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$18;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCallback:Lcom/android/systemui/statusbar/policy/BluetoothController$Callback;

    .line 3568
    new-instance v0, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$19;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->myReceiver:Landroid/content/BroadcastReceiver;

    .line 480
    const-string/jumbo v0, "window"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager;

    .line 481
    invoke-interface {v0}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisplay:Landroid/view/Display;

    .line 483
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    .line 484
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    .line 486
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowAccessibilityButton:Z

    .line 487
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mLongClickableAccessibilityButton:Z

    .line 489
    const-class v0, Lcom/android/systemui/OverviewProxyService;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/OverviewProxyService;

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    .line 490
    new-instance v0, Lcom/android/systemui/recents/RecentsOnboarding;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-direct {v0, p1, v1}, Lcom/android/systemui/recents/RecentsOnboarding;-><init>(Landroid/content/Context;Lcom/android/systemui/OverviewProxyService;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

    .line 492
    new-instance v0, Landroid/content/res/Configuration;

    invoke-direct {v0}, Landroid/content/res/Configuration;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    .line 493
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/res/Configuration;->updateFrom(Landroid/content/res/Configuration;)I

    .line 497
    nop

    .line 498
    :try_start_0
    invoke-static {p1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSystemUICustomizationFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    .line 499
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSystemUICustomizationFactory:Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;

    invoke-virtual {v0, p1}, Lcom/mediatek/systemui/ext/OpSystemUICustomizationFactoryBase;->makeNavigationBar(Landroid/content/Context;)Lcom/mediatek/systemui/ext/INavigationBarPlugin;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavBarPlugin:Lcom/mediatek/systemui/ext/INavigationBarPlugin;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 503
    goto :goto_0

    .line 500
    :catch_0
    move-exception v0

    .line 502
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 505
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->reloadNavIcons()V

    .line 506
    new-instance v0, Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    .line 508
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a0082

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 509
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a01a5

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 510
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a032e

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 511
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a0266

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 512
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a01b6

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 513
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a000f

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 514
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a034d

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 515
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    new-instance v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    const v2, 0x7f0a0268

    invoke-direct {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;-><init>(I)V

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 516
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mResources:Landroid/content/res/Resources;

    .line 517
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinPkgName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mPackageName:Ljava/lang/String;

    .line 519
    new-instance v0, Lcom/android/systemui/statusbar/policy/DeadZone;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/policy/DeadZone;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZone:Lcom/android/systemui/statusbar/policy/DeadZone;

    .line 520
    const-class v0, Lcom/android/systemui/statusbar/policy/BluetoothController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/policy/BluetoothController;

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

    .line 521
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 522
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isNewLauncher()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 523
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->initPortSkinResource()V

    .line 526
    :cond_0
    return-void
.end method

.method private RunApp(Ljava/lang/String;Ljava/lang/String;)V
    .locals 9
    .param p1, "packageName"    # Ljava/lang/String;
    .param p2, "classname"    # Ljava/lang/String;

    .line 1934
    :try_start_0
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 1935
    const-string v0, "StatusBar/NavBarView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "RunApp with classname:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1936
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.intent.action.MAIN"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1937
    .local v0, "intent":Landroid/content/Intent;
    const-string v1, "android.intent.category.LAUNCHER"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addCategory(Ljava/lang/String;)Landroid/content/Intent;

    .line 1938
    const/high16 v1, 0x10020000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 1939
    new-instance v1, Landroid/content/ComponentName;

    invoke-direct {v1, p1, p2}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 1940
    .local v1, "cn":Landroid/content/ComponentName;
    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 1941
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    .line 1942
    .end local v0    # "intent":Landroid/content/Intent;
    .end local v1    # "cn":Landroid/content/ComponentName;
    goto :goto_1

    .line 1943
    :cond_0
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "RunApp without classname"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1945
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, p1, v1}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v0

    .line 1946
    .local v0, "pi":Landroid/content/pm/PackageInfo;
    if-eqz v0, :cond_2

    .line 1947
    new-instance v2, Landroid/content/Intent;

    const-string v3, "android.intent.action.MAIN"

    const/4 v4, 0x0

    invoke-direct {v2, v3, v4}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    .line 1949
    .local v2, "resolveIntent":Landroid/content/Intent;
    iget-object v3, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 1950
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v3

    .line 1951
    .local v3, "pManager":Landroid/content/pm/PackageManager;
    invoke-virtual {v3, v2, v1}, Landroid/content/pm/PackageManager;->queryIntentActivities(Landroid/content/Intent;I)Ljava/util/List;

    move-result-object v1

    .line 1954
    .local v1, "apps":Ljava/util/List;, "Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/pm/ResolveInfo;

    .line 1955
    .local v4, "ri":Landroid/content/pm/ResolveInfo;
    if-eqz v4, :cond_1

    .line 1956
    iget-object v5, v4, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v5, v5, Landroid/content/pm/ActivityInfo;->packageName:Ljava/lang/String;

    move-object p1, v5

    .line 1957
    iget-object v5, v4, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v5, v5, Landroid/content/pm/ActivityInfo;->name:Ljava/lang/String;

    .line 1959
    .local v5, "className":Ljava/lang/String;
    new-instance v6, Landroid/content/Intent;

    const-string v7, "android.intent.action.MAIN"

    invoke-direct {v6, v7}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1960
    .local v6, "intent":Landroid/content/Intent;
    const-string v7, "android.intent.category.LAUNCHER"

    invoke-virtual {v6, v7}, Landroid/content/Intent;->addCategory(Ljava/lang/String;)Landroid/content/Intent;

    .line 1962
    const/high16 v7, 0x10000000

    invoke-virtual {v6, v7}, Landroid/content/Intent;->setFlags(I)Landroid/content/Intent;

    .line 1964
    new-instance v7, Landroid/content/ComponentName;

    invoke-direct {v7, p1, v5}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 1967
    .local v7, "cn":Landroid/content/ComponentName;
    invoke-virtual {v6, v7}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 1968
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v8

    invoke-virtual {v8, v6}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    .line 1969
    .end local v5    # "className":Ljava/lang/String;
    .end local v6    # "intent":Landroid/content/Intent;
    .end local v7    # "cn":Landroid/content/ComponentName;
    goto :goto_0

    .line 1970
    :cond_1
    const-string v5, "StatusBar/NavBarView"

    const-string v6, "RunApp ResolveInfo == null!"

    invoke-static {v5, v6}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1972
    .end local v1    # "apps":Ljava/util/List;, "Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    .end local v2    # "resolveIntent":Landroid/content/Intent;
    .end local v3    # "pManager":Landroid/content/pm/PackageManager;
    .end local v4    # "ri":Landroid/content/pm/ResolveInfo;
    :goto_0
    goto :goto_1

    .line 1973
    :cond_2
    const-string v1, "StatusBar/NavBarView"

    const-string v2, "RunApp get app error!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 1978
    .end local v0    # "pi":Landroid/content/pm/PackageInfo;
    :goto_1
    goto :goto_2

    .line 1976
    :catch_0
    move-exception v0

    .line 1977
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 1979
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_2
    return-void
.end method

.method private SetViewVisible(II)V
    .locals 2
    .param p1, "id"    # I
    .param p2, "visibily"    # I

    .line 3297
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    .line 3298
    .local v0, "view":Landroid/view/View;
    if-eqz v0, :cond_1

    .line 3299
    const/4 v1, 0x1

    if-ne p2, v1, :cond_0

    .line 3300
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    .line 3302
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 3305
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateACandACMax(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 3071
    const/4 v0, 0x0

    .line 3072
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 3073
    packed-switch p1, :pswitch_data_0

    .line 3090
    goto :goto_0

    .line 3087
    :pswitch_0
    const v0, 0x7f080556

    .line 3088
    goto :goto_0

    .line 3084
    :pswitch_1
    const v0, 0x7f08054d

    .line 3085
    goto :goto_0

    .line 3078
    :pswitch_2
    const v0, 0x7f08054f

    .line 3079
    goto :goto_0

    .line 3075
    :pswitch_3
    const v0, 0x7f08054a

    .line 3076
    goto :goto_0

    .line 3081
    :pswitch_4
    const v0, 0x7f08054b

    .line 3082
    goto :goto_0

    .line 3093
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 3107
    :pswitch_5
    const v0, 0x7f080555

    .line 3108
    goto :goto_0

    .line 3104
    :pswitch_6
    const v0, 0x7f08054c

    .line 3105
    goto :goto_0

    .line 3098
    :pswitch_7
    const v0, 0x7f08054e

    .line 3099
    goto :goto_0

    .line 3095
    :pswitch_8
    const v0, 0x7f080549

    .line 3096
    goto :goto_0

    .line 3101
    :pswitch_9
    const v0, 0x7f080547

    .line 3102
    nop

    .line 3114
    :goto_0
    const/4 v1, 0x1

    const v2, 0x7f0a000e

    invoke-virtual {p0, v2, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(IZ)V

    .line 3115
    invoke-virtual {p0, v2, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 3116
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x0
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
    .end packed-switch
.end method

.method private UpdateAQ(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3135
    const v0, 0x7f0a0051

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3136
    return-void
.end method

.method private UpdateAirAutoSwing(I)V
    .locals 3
    .param p1, "autoSwing"    # I

    .line 3249
    const v0, 0x7f0a0044

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3250
    .local v0, "imageView":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3251
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    const v2, 0x7f0800f7

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3252
    const/4 v1, 0x1

    if-ne p1, v1, :cond_0

    .line 3253
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    .line 3255
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 3258
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateAirFilter(I)V
    .locals 3
    .param p1, "para"    # I

    .line 3261
    const v0, 0x7f0a0047

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3262
    .local v0, "imageView":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3263
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    const v2, 0x7f0801f2

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3264
    const/4 v1, 0x1

    if-ne p1, v1, :cond_0

    .line 3265
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    .line 3267
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 3270
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateAirHeat(I)V
    .locals 3
    .param p1, "para"    # I

    .line 3285
    const v0, 0x7f0a0049

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3286
    .local v0, "imageView":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3287
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    const v2, 0x7f080276

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3288
    const/4 v1, 0x1

    if-ne p1, v1, :cond_0

    .line 3289
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    .line 3291
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 3294
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateAirNanoe(I)V
    .locals 3
    .param p1, "nanoe"    # I

    .line 3237
    const v0, 0x7f0a004e

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3238
    .local v0, "imageView":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3239
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    const v2, 0x7f080129

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3240
    const/4 v1, 0x1

    if-ne p1, v1, :cond_0

    .line 3241
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    .line 3243
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 3246
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateAirOnProfile2(I)V
    .locals 3
    .param p1, "Profile"    # I

    .line 3119
    const v0, 0x7f0a007d

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3120
    .local v0, "imageView":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3121
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    const v2, 0x7f0800f3

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3122
    const/4 v1, 0x1

    if-ne p1, v1, :cond_0

    .line 3123
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    .line 3125
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 3128
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateAirPTC(I)V
    .locals 3
    .param p1, "para"    # I

    .line 3273
    const v0, 0x7f0a0050

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3274
    .local v0, "imageView":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3275
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    const v2, 0x7f08052a

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3276
    const/4 v1, 0x1

    if-ne p1, v1, :cond_0

    .line 3277
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    .line 3279
    :cond_0
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 3282
    :cond_1
    :goto_0
    return-void
.end method

.method private UpdateAuto(I)V
    .locals 3
    .param p1, "nWind"    # I

    .line 2273
    const/4 v0, 0x0

    .line 2274
    .local v0, "nResID":I
    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 2279
    :pswitch_0
    const v0, 0x7f080551

    .line 2280
    goto :goto_0

    .line 2276
    :pswitch_1
    const v0, 0x7f080550

    .line 2277
    nop

    .line 2285
    :goto_0
    const v1, 0x7f0a0473

    if-nez v0, :cond_0

    .line 2286
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2288
    :cond_0
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2291
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2292
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private UpdateAutoDisfog(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 2265
    const v0, 0x7f0a0043

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2266
    return-void
.end method

.method private UpdateCycle(I)V
    .locals 3
    .param p1, "nCycle"    # I

    .line 2526
    const-string v0, "StatusBar/NavBarView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "nCycle "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2527
    const/4 v0, 0x0

    .line 2528
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2529
    packed-switch p1, :pswitch_data_0

    .line 2540
    goto :goto_0

    .line 2537
    :pswitch_0
    const v0, 0x7f0801ff

    .line 2538
    goto :goto_0

    .line 2534
    :pswitch_1
    const v0, 0x7f080542

    .line 2535
    goto :goto_0

    .line 2531
    :pswitch_2
    const v0, 0x7f080540

    .line 2532
    goto :goto_0

    .line 2543
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2551
    :pswitch_3
    const v0, 0x7f0801fe

    .line 2552
    goto :goto_0

    .line 2548
    :pswitch_4
    const v0, 0x7f080541

    .line 2549
    goto :goto_0

    .line 2545
    :pswitch_5
    const v0, 0x7f08053e

    .line 2546
    nop

    .line 2558
    :goto_0
    const v1, 0x7f0a0045

    if-nez p1, :cond_1

    .line 2559
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2561
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2564
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2565
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch
.end method

.method private UpdateDirection(I)V
    .locals 3
    .param p1, "nShow"    # I

    .line 2339
    const-string v0, "StatusBar/NavBarView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "show wind mode "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2340
    const/4 v0, 0x0

    .line 2341
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2342
    packed-switch p1, :pswitch_data_0

    .line 2374
    goto :goto_0

    .line 2371
    :pswitch_0
    const v0, 0x7f0805a3

    .line 2372
    goto :goto_0

    .line 2368
    :pswitch_1
    const v0, 0x7f08059d

    .line 2369
    goto :goto_0

    .line 2365
    :pswitch_2
    const v0, 0x7f0805bc

    .line 2366
    goto :goto_0

    .line 2362
    :pswitch_3
    const v0, 0x7f0805c0

    .line 2363
    goto :goto_0

    .line 2359
    :pswitch_4
    const v0, 0x7f0805be

    .line 2360
    goto :goto_0

    .line 2356
    :pswitch_5
    const v0, 0x7f0805c1

    .line 2357
    goto :goto_0

    .line 2353
    :pswitch_6
    const v0, 0x7f0805a6

    .line 2354
    goto :goto_0

    .line 2350
    :pswitch_7
    const v0, 0x7f0805a7

    .line 2351
    goto :goto_0

    .line 2347
    :pswitch_8
    const v0, 0x7f0805a1

    .line 2348
    goto :goto_0

    .line 2344
    :pswitch_9
    const v0, 0x7f0805ab

    .line 2345
    goto :goto_0

    .line 2377
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2406
    :pswitch_a
    const v0, 0x7f0805a2

    .line 2407
    goto :goto_0

    .line 2403
    :pswitch_b
    const v0, 0x7f08059c

    .line 2404
    goto :goto_0

    .line 2400
    :pswitch_c
    const v0, 0x7f0805bb

    .line 2401
    goto :goto_0

    .line 2397
    :pswitch_d
    const v0, 0x7f0805bf

    .line 2398
    goto :goto_0

    .line 2394
    :pswitch_e
    const v0, 0x7f0805bd

    .line 2395
    goto :goto_0

    .line 2391
    :pswitch_f
    const v0, 0x7f0805b9

    .line 2392
    goto :goto_0

    .line 2388
    :pswitch_10
    const v0, 0x7f0805a5

    .line 2389
    goto :goto_0

    .line 2385
    :pswitch_11
    const v0, 0x7f0805a4

    .line 2386
    goto :goto_0

    .line 2382
    :pswitch_12
    const v0, 0x7f0805a0

    .line 2383
    goto :goto_0

    .line 2379
    :pswitch_13
    const v0, 0x7f0805aa

    .line 2380
    nop

    .line 2412
    :goto_0
    const/4 v1, 0x1

    const v2, 0x7f0a012b

    invoke-virtual {p0, v2, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(IZ)V

    .line 2413
    invoke-virtual {p0, v2, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2414
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x0
        :pswitch_13
        :pswitch_12
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
    .end packed-switch
.end method

.method private UpdateDual(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3012
    const v0, 0x7f0a013f

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3013
    return-void
.end method

.method private UpdateECO(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3131
    const v0, 0x7f0a0141

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3132
    return-void
.end method

.method private UpdateForntRZC(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3139
    const v0, 0x7f0a0177

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3140
    return-void
.end method

.method private UpdateLeftMassage(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2942
    const/4 v0, 0x0

    .line 2943
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2944
    packed-switch p1, :pswitch_data_0

    .line 2952
    goto :goto_0

    .line 2949
    :pswitch_0
    const v0, 0x7f080122

    .line 2950
    goto :goto_0

    .line 2946
    :pswitch_1
    const v0, 0x7f080120

    .line 2947
    goto :goto_0

    .line 2955
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2960
    :pswitch_2
    const v0, 0x7f080121

    .line 2961
    goto :goto_0

    .line 2957
    :pswitch_3
    const v0, 0x7f08011f

    .line 2958
    nop

    .line 2967
    :goto_0
    const v1, 0x7f0a004b

    if-nez v0, :cond_1

    .line 2968
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2970
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2973
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2974
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
    .end packed-switch
.end method

.method private UpdateLeftSeatHeat(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2576
    const/4 v0, 0x0

    .line 2577
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/16 v2, 0xf

    if-eqz v1, :cond_1

    .line 2578
    if-eq p1, v2, :cond_0

    packed-switch p1, :pswitch_data_0

    .line 2595
    goto :goto_0

    .line 2589
    :pswitch_0
    const v0, 0x7f080565

    .line 2590
    goto :goto_0

    .line 2586
    :pswitch_1
    const v0, 0x7f080563

    .line 2587
    goto :goto_0

    .line 2583
    :pswitch_2
    const v0, 0x7f080561

    .line 2584
    goto :goto_0

    .line 2580
    :pswitch_3
    const v0, 0x7f08055f

    .line 2581
    goto :goto_0

    .line 2592
    :cond_0
    const v0, 0x7f080567

    .line 2593
    goto :goto_0

    .line 2598
    :cond_1
    if-eq p1, v2, :cond_2

    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2609
    :pswitch_4
    const v0, 0x7f080564

    .line 2610
    goto :goto_0

    .line 2606
    :pswitch_5
    const v0, 0x7f080562

    .line 2607
    goto :goto_0

    .line 2603
    :pswitch_6
    const v0, 0x7f080560

    .line 2604
    goto :goto_0

    .line 2600
    :pswitch_7
    const v0, 0x7f08055e

    .line 2601
    goto :goto_0

    .line 2612
    :cond_2
    const v0, 0x7f080566

    .line 2613
    nop

    .line 2619
    :goto_0
    const v1, 0x7f0a023d

    if-nez v0, :cond_3

    .line 2620
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2622
    :cond_3
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2625
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2626
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
    .end packed-switch
.end method

.method private UpdateLeftSeatWind(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2729
    const/4 v0, 0x0

    .line 2730
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/16 v2, 0xf

    if-eqz v1, :cond_1

    .line 2731
    if-eq p1, v2, :cond_0

    packed-switch p1, :pswitch_data_0

    .line 2748
    goto :goto_0

    .line 2742
    :pswitch_0
    const v0, 0x7f0800df

    .line 2743
    goto :goto_0

    .line 2739
    :pswitch_1
    const v0, 0x7f0800dd

    .line 2740
    goto :goto_0

    .line 2736
    :pswitch_2
    const v0, 0x7f0800db

    .line 2737
    goto :goto_0

    .line 2733
    :pswitch_3
    const v0, 0x7f0800d9

    .line 2734
    goto :goto_0

    .line 2745
    :cond_0
    const v0, 0x7f0800e1

    .line 2746
    goto :goto_0

    .line 2751
    :cond_1
    if-eq p1, v2, :cond_2

    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2762
    :pswitch_4
    const v0, 0x7f0800de

    .line 2763
    goto :goto_0

    .line 2759
    :pswitch_5
    const v0, 0x7f0800dc

    .line 2760
    goto :goto_0

    .line 2756
    :pswitch_6
    const v0, 0x7f0800da

    .line 2757
    goto :goto_0

    .line 2753
    :pswitch_7
    const v0, 0x7f0800d8

    .line 2754
    goto :goto_0

    .line 2765
    :cond_2
    const v0, 0x7f0800e0

    .line 2766
    nop

    .line 2772
    :goto_0
    const v1, 0x7f0a0240

    if-nez v0, :cond_3

    .line 2773
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2775
    :cond_3
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2778
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2779
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
    .end packed-switch
.end method

.method private UpdateLeftTemp(Ljava/lang/String;)V
    .locals 2
    .param p1, "nStrTemp"    # Ljava/lang/String;

    .line 2494
    const v0, 0x7f0a023f

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(IZ)V

    .line 2495
    invoke-virtual {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewTextByID(ILjava/lang/String;)V

    .line 2496
    return-void
.end method

.method private UpdateLeftWaist(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2835
    const/4 v0, 0x0

    .line 2836
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2837
    packed-switch p1, :pswitch_data_0

    .line 2854
    goto :goto_0

    .line 2851
    :pswitch_0
    const v0, 0x7f080151

    .line 2852
    goto :goto_0

    .line 2848
    :pswitch_1
    const v0, 0x7f08014f

    .line 2849
    goto :goto_0

    .line 2845
    :pswitch_2
    const v0, 0x7f08014d

    .line 2846
    goto :goto_0

    .line 2842
    :pswitch_3
    const v0, 0x7f08014b

    .line 2843
    goto :goto_0

    .line 2839
    :pswitch_4
    const v0, 0x7f080149

    .line 2840
    goto :goto_0

    .line 2857
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2871
    :pswitch_5
    const v0, 0x7f080150

    .line 2872
    goto :goto_0

    .line 2868
    :pswitch_6
    const v0, 0x7f08014e

    .line 2869
    goto :goto_0

    .line 2865
    :pswitch_7
    const v0, 0x7f08014c

    .line 2866
    goto :goto_0

    .line 2862
    :pswitch_8
    const v0, 0x7f08014a

    .line 2863
    goto :goto_0

    .line 2859
    :pswitch_9
    const v0, 0x7f080148

    .line 2860
    nop

    .line 2878
    :goto_0
    const v1, 0x7f0a004c

    if-nez v0, :cond_1

    .line 2879
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2881
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2884
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2885
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
    .end packed-switch
.end method

.method private UpdateManual(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 2261
    const v0, 0x7f0a004d

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2262
    return-void
.end method

.method private UpdateMaxFront(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 2568
    const v0, 0x7f0a0176

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2569
    return-void
.end method

.method private UpdateRear(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 2572
    const v0, 0x7f0a032a

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2573
    return-void
.end method

.method private UpdateRearLock(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3056
    const v0, 0x7f0a032d

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3057
    return-void
.end method

.method private UpdateRearRZC(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3143
    const v0, 0x7f0a032b

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3144
    return-void
.end method

.method private UpdateRest(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3048
    const v0, 0x7f0a0339

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3049
    return-void
.end method

.method private UpdateRightMassage(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2977
    const/4 v0, 0x0

    .line 2978
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2979
    packed-switch p1, :pswitch_data_0

    .line 2987
    goto :goto_0

    .line 2984
    :pswitch_0
    const v0, 0x7f080128

    .line 2985
    goto :goto_0

    .line 2981
    :pswitch_1
    const v0, 0x7f080126

    .line 2982
    goto :goto_0

    .line 2990
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2995
    :pswitch_2
    const v0, 0x7f080127

    .line 2996
    goto :goto_0

    .line 2992
    :pswitch_3
    const v0, 0x7f080125

    .line 2993
    nop

    .line 3002
    :goto_0
    const v1, 0x7f0a0052

    if-nez v0, :cond_1

    .line 3003
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 3005
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3008
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 3009
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
    .end packed-switch
.end method

.method private UpdateRightSeatHeat(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2629
    const/4 v0, 0x0

    .line 2630
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/16 v2, 0xf

    if-eqz v1, :cond_1

    .line 2631
    if-eq p1, v2, :cond_0

    packed-switch p1, :pswitch_data_0

    .line 2648
    goto :goto_0

    .line 2642
    :pswitch_0
    const v0, 0x7f080571

    .line 2643
    goto :goto_0

    .line 2639
    :pswitch_1
    const v0, 0x7f08056f

    .line 2640
    goto :goto_0

    .line 2636
    :pswitch_2
    const v0, 0x7f08056d

    .line 2637
    goto :goto_0

    .line 2633
    :pswitch_3
    const v0, 0x7f08056b

    .line 2634
    goto :goto_0

    .line 2645
    :cond_0
    const v0, 0x7f080573

    .line 2646
    goto :goto_0

    .line 2651
    :cond_1
    if-eq p1, v2, :cond_2

    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2662
    :pswitch_4
    const v0, 0x7f080570

    .line 2663
    goto :goto_0

    .line 2659
    :pswitch_5
    const v0, 0x7f08056e

    .line 2660
    goto :goto_0

    .line 2656
    :pswitch_6
    const v0, 0x7f08056c

    .line 2657
    goto :goto_0

    .line 2653
    :pswitch_7
    const v0, 0x7f08056a

    .line 2654
    goto :goto_0

    .line 2665
    :cond_2
    const v0, 0x7f080572

    .line 2666
    nop

    .line 2672
    :goto_0
    const v1, 0x7f0a033d

    if-nez v0, :cond_3

    .line 2673
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2675
    :cond_3
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2678
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2679
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
    .end packed-switch
.end method

.method private UpdateRightSeatWind(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2782
    const/4 v0, 0x0

    .line 2783
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/16 v2, 0xf

    if-eqz v1, :cond_1

    .line 2784
    if-eq p1, v2, :cond_0

    packed-switch p1, :pswitch_data_0

    .line 2801
    goto :goto_0

    .line 2795
    :pswitch_0
    const v0, 0x7f0800ed

    .line 2796
    goto :goto_0

    .line 2792
    :pswitch_1
    const v0, 0x7f0800eb

    .line 2793
    goto :goto_0

    .line 2789
    :pswitch_2
    const v0, 0x7f0800e9

    .line 2790
    goto :goto_0

    .line 2786
    :pswitch_3
    const v0, 0x7f0800e7

    .line 2787
    goto :goto_0

    .line 2798
    :cond_0
    const v0, 0x7f0800ef

    .line 2799
    goto :goto_0

    .line 2804
    :cond_1
    if-eq p1, v2, :cond_2

    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2815
    :pswitch_4
    const v0, 0x7f0800ec

    .line 2816
    goto :goto_0

    .line 2812
    :pswitch_5
    const v0, 0x7f0800ea

    .line 2813
    goto :goto_0

    .line 2809
    :pswitch_6
    const v0, 0x7f0800e8

    .line 2810
    goto :goto_0

    .line 2806
    :pswitch_7
    const v0, 0x7f0800e6

    .line 2807
    goto :goto_0

    .line 2818
    :cond_2
    const v0, 0x7f0800ee

    .line 2819
    nop

    .line 2825
    :goto_0
    const v1, 0x7f0a0340

    if-nez v0, :cond_3

    .line 2826
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2828
    :cond_3
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2831
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2832
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
    .end packed-switch
.end method

.method private UpdateRightTemp(Ljava/lang/String;)V
    .locals 2
    .param p1, "nStrTemp"    # Ljava/lang/String;

    .line 2499
    const v0, 0x7f0a033f

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(IZ)V

    .line 2500
    invoke-virtual {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewTextByID(ILjava/lang/String;)V

    .line 2501
    return-void
.end method

.method private UpdateRightWaist(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2888
    const/4 v0, 0x0

    .line 2889
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2890
    packed-switch p1, :pswitch_data_0

    .line 2907
    goto :goto_0

    .line 2904
    :pswitch_0
    const v0, 0x7f08015d

    .line 2905
    goto :goto_0

    .line 2901
    :pswitch_1
    const v0, 0x7f08015b

    .line 2902
    goto :goto_0

    .line 2898
    :pswitch_2
    const v0, 0x7f080159

    .line 2899
    goto :goto_0

    .line 2895
    :pswitch_3
    const v0, 0x7f080157

    .line 2896
    goto :goto_0

    .line 2892
    :pswitch_4
    const v0, 0x7f080155

    .line 2893
    goto :goto_0

    .line 2910
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2924
    :pswitch_5
    const v0, 0x7f08015c

    .line 2925
    goto :goto_0

    .line 2921
    :pswitch_6
    const v0, 0x7f08015a

    .line 2922
    goto :goto_0

    .line 2918
    :pswitch_7
    const v0, 0x7f080158

    .line 2919
    goto :goto_0

    .line 2915
    :pswitch_8
    const v0, 0x7f080156

    .line 2916
    goto :goto_0

    .line 2912
    :pswitch_9
    const v0, 0x7f080154

    .line 2913
    nop

    .line 2932
    :goto_0
    const v1, 0x7f0a0053

    if-nez v0, :cond_1

    .line 2933
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2935
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2938
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2939
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
    .end packed-switch
.end method

.method private UpdateSteerChairHeat(I)V
    .locals 3
    .param p1, "nValue"    # I

    .line 2682
    const/4 v0, 0x0

    .line 2683
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2684
    packed-switch p1, :pswitch_data_0

    .line 2698
    goto :goto_0

    .line 2695
    :pswitch_0
    const v0, 0x7f080663

    .line 2696
    goto :goto_0

    .line 2692
    :pswitch_1
    const v0, 0x7f080669

    .line 2693
    goto :goto_0

    .line 2689
    :pswitch_2
    const v0, 0x7f080667

    .line 2690
    goto :goto_0

    .line 2686
    :pswitch_3
    const v0, 0x7f080665

    .line 2687
    goto :goto_0

    .line 2701
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2712
    :pswitch_4
    const v0, 0x7f080662

    .line 2713
    goto :goto_0

    .line 2709
    :pswitch_5
    const v0, 0x7f080668

    .line 2710
    goto :goto_0

    .line 2706
    :pswitch_6
    const v0, 0x7f080666

    .line 2707
    goto :goto_0

    .line 2703
    :pswitch_7
    const v0, 0x7f080664

    .line 2704
    nop

    .line 2719
    :goto_0
    const v1, 0x7f0a03d4

    if-nez v0, :cond_1

    .line 2720
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 2722
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 2725
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2726
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
    .end packed-switch
.end method

.method static synthetic access$100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomPre:Landroid/widget/ImageButton;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateVolumeProgress()V

    return-void
.end method

.method static synthetic access$102(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/widget/ImageButton;)Landroid/widget/ImageButton;
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # Landroid/widget/ImageButton;

    .line 159
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomPre:Landroid/widget/ImageButton;

    return-object p1
.end method

.method static synthetic access$1100(Lcom/android/systemui/statusbar/phone/NavigationBarView;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # Ljava/lang/String;
    .param p2, "x2"    # Ljava/lang/String;

    .line 159
    invoke-direct {p0, p1, p2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->RunApp(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$1200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->resetAutoDismissTimer()V

    return-void
.end method

.method static synthetic access$1300(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mChangebySelf:Z

    return v0
.end method

.method static synthetic access$1302(Lcom/android/systemui/statusbar/phone/NavigationBarView;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # Z

    .line 159
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mChangebySelf:Z

    return p1
.end method

.method static synthetic access$1400(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cancelAutoDismissTimer()V

    return-void
.end method

.method static synthetic access$1500(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->startAutoDismissTimer()V

    return-void
.end method

.method static synthetic access$1600(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/app/AlertDialog;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$1602(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # Landroid/app/AlertDialog;

    .line 159
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    return-object p1
.end method

.method static synthetic access$1700(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/SeekBar;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    return-object v0
.end method

.method static synthetic access$1702(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/widget/SeekBar;)Landroid/widget/SeekBar;
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # Landroid/widget/SeekBar;

    .line 159
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    return-object p1
.end method

.method static synthetic access$1800(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1900(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateBTStatus()V

    return-void
.end method

.method static synthetic access$200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/widget/ImageButton;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomNext:Landroid/widget/ImageButton;

    return-object v0
.end method

.method static synthetic access$2000(Lcom/android/systemui/statusbar/phone/NavigationBarView;I)Landroid/view/View;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # I

    .line 159
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$202(Lcom/android/systemui/statusbar/phone/NavigationBarView;Landroid/widget/ImageButton;)Landroid/widget/ImageButton;
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;
    .param p1, "x1"    # Landroid/widget/ImageButton;

    .line 159
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomNext:Landroid/widget/ImageButton;

    return-object p1
.end method

.method static synthetic access$2100(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Ljava/lang/Runnable;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHideMediaBtnRunnable:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$2200(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mMediaBtnHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Lcom/android/systemui/statusbar/phone/NavigationBarView$H;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHandler:Lcom/android/systemui/statusbar/phone/NavigationBarView$H;

    return-object v0
.end method

.method static synthetic access$400(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->checkOrSwapAirTemp()V

    return-void
.end method

.method static synthetic access$500(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$700(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isBackCar()Z

    move-result v0

    return v0
.end method

.method static synthetic access$800(Lcom/android/systemui/statusbar/phone/NavigationBarView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$900(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/NavigationBarView;

    .line 159
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->showVolumeDialog()V

    return-void
.end method

.method private cancelAutoDismissTimer()V
    .locals 2

    .line 1873
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAutoDismissHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAutoDismissRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 1874
    return-void
.end method

.method private checkOrSwapAirTemp()V
    .locals 7

    .line 2504
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "air_temp_reverse_flag"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    if-lez v0, :cond_0

    const/4 v2, 0x1

    nop

    :cond_0
    move v0, v2

    .line 2505
    .local v0, "reqAirTempMirrorCfg":Z
    const-string v1, "StatusBar/NavBarView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "reqAirTempMirrorCfg|curtAirTempMirror:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string/jumbo v3, "|"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 2506
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    if-ne v0, v1, :cond_1

    return-void

    .line 2507
    :cond_1
    const v1, 0x7f0a023f

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 2508
    .local v1, "leftView":Landroid/widget/TextView;
    const v2, 0x7f0a033f

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 2509
    .local v2, "rightView":Landroid/widget/TextView;
    if-eqz v1, :cond_3

    if-nez v2, :cond_2

    goto :goto_0

    .line 2512
    :cond_2
    invoke-virtual {v1}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v3

    .line 2513
    .local v3, "leftTempStr":Ljava/lang/CharSequence;
    invoke-virtual {v2}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v4

    .line 2514
    .local v4, "rightTempStr":Ljava/lang/CharSequence;
    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 2515
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 2516
    iput-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    .line 2517
    const-string v5, "StatusBar/NavBarView"

    const-string v6, "swap air temp suc!"

    invoke-static {v5, v6}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 2518
    return-void

    .line 2510
    .end local v3    # "leftTempStr":Ljava/lang/CharSequence;
    .end local v4    # "rightTempStr":Ljava/lang/CharSequence;
    :cond_3
    :goto_0
    return-void
.end method

.method private chooseNavigationIconDrawable(Landroid/content/Context;Landroid/content/Context;II)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 2
    .param p1, "lightContext"    # Landroid/content/Context;
    .param p2, "darkContext"    # Landroid/content/Context;
    .param p3, "icon"    # I
    .param p4, "quickStepIcon"    # I

    .line 774
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-virtual {v0}, Lcom/android/systemui/OverviewProxyService;->shouldShowSwipeUpUI()Z

    move-result v0

    .line 775
    .local v0, "quickStepEnabled":Z
    if-eqz v0, :cond_0

    .line 776
    invoke-direct {p0, p1, p2, p4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v1

    goto :goto_0

    .line 777
    :cond_0
    invoke-direct {p0, p1, p2, p3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v1

    .line 775
    :goto_0
    return-object v1
.end method

.method private cutTheTempUnit(Ljava/lang/String;)Ljava/lang/String;
    .locals 3
    .param p1, "str_temp"    # Ljava/lang/String;

    .line 2251
    const-string v0, ""

    .line 2252
    .local v0, "temp":Ljava/lang/String;
    if-eqz p1, :cond_0

    .line 2253
    const-string/jumbo v1, "\u2103"

    const-string v2, ""

    invoke-virtual {p1, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p1

    .line 2254
    const-string/jumbo v1, "\u2109"

    const-string v2, ""

    invoke-virtual {p1, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p1

    .line 2255
    move-object v0, p1

    .line 2257
    :cond_0
    return-object v0
.end method

.method private static dumpButton(Ljava/io/PrintWriter;Ljava/lang/String;Lcom/android/systemui/statusbar/phone/ButtonDispatcher;)V
    .locals 2
    .param p0, "pw"    # Ljava/io/PrintWriter;
    .param p1, "caption"    # Ljava/lang/String;
    .param p2, "button"    # Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    .line 1556
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "      "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, ": "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 1557
    if-nez p2, :cond_0

    .line 1558
    const-string v0, "null"

    invoke-virtual {p0, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    goto :goto_0

    .line 1560
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->getVisibility()I

    move-result v1

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->visibilityToString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, " alpha="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1561
    invoke-virtual {p2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->getAlpha()F

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 1560
    invoke-virtual {p0, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 1564
    :goto_0
    invoke-virtual {p0}, Ljava/io/PrintWriter;->println()V

    .line 1565
    return-void
.end method

.method private findViewByIdCompat(I)Landroid/view/View;
    .locals 1
    .param p1, "id"    # I

    .line 1582
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewById(I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method private getAirBar(I)Landroid/widget/ProgressBar;
    .locals 4
    .param p1, "max"    # I

    .line 2318
    const v0, 0x7f0a0308

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ProgressBar;

    .line 2319
    .local v1, "pro":Landroid/widget/ProgressBar;
    const/16 v2, 0x8

    invoke-virtual {v1, v2}, Landroid/widget/ProgressBar;->setVisibility(I)V

    .line 2321
    const/4 v3, 0x6

    if-eq p1, v3, :cond_1

    if-eq p1, v2, :cond_0

    .line 2329
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    .end local v1    # "pro":Landroid/widget/ProgressBar;
    .local v0, "pro":Landroid/widget/ProgressBar;
    goto :goto_0

    .line 2326
    .end local v0    # "pro":Landroid/widget/ProgressBar;
    .restart local v1    # "pro":Landroid/widget/ProgressBar;
    :cond_0
    const v0, 0x7f0a030c

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    .line 2327
    .end local v1    # "pro":Landroid/widget/ProgressBar;
    .restart local v0    # "pro":Landroid/widget/ProgressBar;
    goto :goto_0

    .line 2323
    .end local v0    # "pro":Landroid/widget/ProgressBar;
    .restart local v1    # "pro":Landroid/widget/ProgressBar;
    :cond_1
    const v0, 0x7f0a030b

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    .line 2324
    .end local v1    # "pro":Landroid/widget/ProgressBar;
    .restart local v0    # "pro":Landroid/widget/ProgressBar;
    nop

    .line 2333
    :goto_0
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setVisibility(I)V

    .line 2334
    invoke-virtual {v0, p1}, Landroid/widget/ProgressBar;->setMax(I)V

    .line 2335
    return-object v0
.end method

.method public static getApkPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .locals 7
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "pkg"    # Ljava/lang/String;

    .line 3553
    const/4 v0, 0x0

    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    .line 3554
    .local v1, "pm":Landroid/content/pm/PackageManager;
    const/4 v2, 0x0

    invoke-virtual {v1, p1, v2}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v2

    .line 3555
    .local v2, "info":Landroid/content/pm/ApplicationInfo;
    if-nez v2, :cond_0

    .line 3556
    return-object v0

    .line 3558
    :cond_0
    iget-object v3, v2, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 3559
    .local v3, "apkPath":Ljava/lang/String;
    const-string v4, "StatusBar/NavBarView"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "getApkPath apkPath:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " pkg:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 3560
    return-object v3

    .line 3562
    .end local v1    # "pm":Landroid/content/pm/PackageManager;
    .end local v2    # "info":Landroid/content/pm/ApplicationInfo;
    .end local v3    # "apkPath":Ljava/lang/String;
    :catch_0
    move-exception v1

    .line 3563
    .local v1, "e":Ljava/lang/Exception;
    const-string v2, "StatusBar/NavBarView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Didn\'t find package "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 3565
    .end local v1    # "e":Ljava/lang/Exception;
    return-object v0
.end method

.method private getBackIcon(ZZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 1
    .param p1, "carMode"    # Z
    .param p2, "landscape"    # Z

    .line 817
    if-eqz p2, :cond_1

    .line 818
    if-eqz p1, :cond_0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackLandCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_0

    .line 819
    :cond_1
    if-eqz p1, :cond_2

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 817
    :goto_0
    return-object v0
.end method

.method private getBackIconWithAlt(ZZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 1
    .param p1, "carMode"    # Z
    .param p2, "landscape"    # Z

    .line 811
    if-eqz p2, :cond_1

    .line 812
    if-eqz p1, :cond_0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackAltLandCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_0

    .line 813
    :cond_1
    if-eqz p1, :cond_2

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackAltCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 811
    :goto_0
    return-object v0
.end method

.method private getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 1
    .param p1, "lightContext"    # Landroid/content/Context;
    .param p2, "darkContext"    # Landroid/content/Context;
    .param p3, "icon"    # I

    .line 782
    const/4 v0, 0x1

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;IZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v0

    return-object v0
.end method

.method private getDrawable(Landroid/content/Context;Landroid/content/Context;IZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 2
    .param p1, "lightContext"    # Landroid/content/Context;
    .param p2, "darkContext"    # Landroid/content/Context;
    .param p3, "icon"    # I
    .param p4, "hasShadow"    # Z

    .line 787
    invoke-virtual {p1, p3}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 788
    invoke-virtual {p2, p3}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 787
    invoke-static {p1, v0, v1, p4}, Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;->create(Landroid/content/Context;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Z)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v0

    return-object v0
.end method

.method private getDrawable(Landroid/content/Context;III)Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;
    .locals 1
    .param p1, "ctx"    # Landroid/content/Context;
    .param p2, "icon"    # I
    .param p3, "lightColor"    # I
    .param p4, "darkColor"    # I

    .line 800
    invoke-virtual {p1, p2}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-static {v0, p3, p4}, Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;->create(Landroid/graphics/drawable/Drawable;II)Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    move-result-object v0

    return-object v0
.end method

.method private getResourceName(I)Ljava/lang/String;
    .locals 3
    .param p1, "resId"    # I

    .line 1384
    if-eqz p1, :cond_0

    .line 1385
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 1387
    .local v0, "res":Landroid/content/res/Resources;
    :try_start_0
    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getResourceName(I)Ljava/lang/String;

    move-result-object v1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v1

    .line 1388
    :catch_0
    move-exception v1

    .line 1389
    .local v1, "ex":Landroid/content/res/Resources$NotFoundException;
    const-string v2, "(unknown)"

    return-object v2

    .line 1392
    .end local v0    # "res":Landroid/content/res/Resources;
    .end local v1    # "ex":Landroid/content/res/Resources$NotFoundException;
    :cond_0
    const-string v0, "(null)"

    return-object v0
.end method

.method public static getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 6
    .param p0, "property"    # Ljava/lang/String;
    .param p1, "defaultValue"    # Ljava/lang/String;

    .line 3473
    :try_start_0
    const-string v0, "android.os.SystemProperties"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 3474
    .local v0, "clazz":Ljava/lang/Class;
    const-string v1, "get"

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Ljava/lang/String;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 3475
    .local v1, "getter":Ljava/lang/reflect/Method;
    const/4 v3, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p0, v2, v5

    invoke-virtual {v1, v3, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 3476
    .local v2, "value":Ljava/lang/String;
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    if-nez v3, :cond_0

    .line 3477
    return-object v2

    .line 3481
    .end local v0    # "clazz":Ljava/lang/Class;
    .end local v1    # "getter":Ljava/lang/reflect/Method;
    .end local v2    # "value":Ljava/lang/String;
    :cond_0
    goto :goto_0

    .line 3479
    :catch_0
    move-exception v0

    .line 3480
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "StatusBar/NavBarView"

    const-string v2, "Unable to read system properties"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 3482
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-object p1
.end method

.method private initAirTempMirrorCfg()V
    .locals 1

    .line 1458
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 1459
    return-void

    .line 1461
    :cond_0
    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->regOrUnregAirListen(Z)V

    .line 1462
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->checkOrSwapAirTemp()V

    .line 1463
    return-void
.end method

.method private initPortView()V
    .locals 18
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "ClickableViewAccessibility"
        }
    .end annotation

    .line 1587
    move-object/from16 v0, p0

    const-string v1, "persist.sys.skin"

    const-string v2, "com.hcn.auto.theme.p1"

    invoke-static {v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    .line 1588
    const v1, 0x7f0a0269

    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ImageButton;

    .line 1589
    .local v1, "btnHome":Landroid/widget/ImageButton;
    if-eqz v1, :cond_0

    .line 1590
    new-instance v2, Lcom/android/systemui/statusbar/phone/NavigationBarView$5;

    invoke-direct {v2, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$5;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v1, v2}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1608
    :cond_0
    const v2, 0x7f0a00b0

    invoke-direct {v0, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageButton;

    .line 1609
    .local v2, "btnApps":Landroid/widget/ImageButton;
    if-eqz v2, :cond_1

    .line 1610
    new-instance v3, Lcom/android/systemui/statusbar/phone/NavigationBarView$6;

    invoke-direct {v3, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$6;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v2, v3}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1619
    :cond_1
    const v3, 0x7f0a0267

    invoke-direct {v0, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/ImageButton;

    .line 1620
    .local v3, "btnBack":Landroid/widget/ImageButton;
    if-eqz v3, :cond_2

    .line 1621
    new-instance v4, Lcom/android/systemui/statusbar/phone/NavigationBarView$7;

    invoke-direct {v4, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$7;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v3, v4}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1629
    :cond_2
    const v4, 0x7f0a0041

    invoke-direct {v0, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Lcom/android/systemui/statusbar/phone/VolButton;

    .line 1630
    .local v4, "btnVolAdd":Lcom/android/systemui/statusbar/phone/VolButton;
    if-eqz v4, :cond_3

    .line 1631
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$8;

    invoke-direct {v5, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$8;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v4, v5}, Lcom/android/systemui/statusbar/phone/VolButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1651
    new-instance v5, Lcom/android/systemui/statusbar/phone/NavigationBarView$9;

    invoke-direct {v5, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$9;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v4, v5}, Lcom/android/systemui/statusbar/phone/VolButton;->setLongClickRepeatListener(Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;)V

    .line 1672
    :cond_3
    const v5, 0x7f0a0332

    invoke-direct {v0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Lcom/android/systemui/statusbar/phone/VolButton;

    .line 1673
    .local v5, "btnVolReduce":Lcom/android/systemui/statusbar/phone/VolButton;
    if-eqz v5, :cond_4

    .line 1674
    new-instance v6, Lcom/android/systemui/statusbar/phone/NavigationBarView$10;

    invoke-direct {v6, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$10;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v5, v6}, Lcom/android/systemui/statusbar/phone/VolButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1693
    new-instance v6, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;

    invoke-direct {v6, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$11;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v5, v6}, Lcom/android/systemui/statusbar/phone/VolButton;->setLongClickRepeatListener(Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;)V

    .line 1714
    :cond_4
    const/4 v6, 0x0

    .line 1715
    .local v6, "bluetoothConnected":Z
    const v7, 0x7f0a00ad

    invoke-direct {v0, v7}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/Button;

    iput-object v7, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    .line 1716
    iget-object v7, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    if-eqz v7, :cond_5

    .line 1717
    iget-object v7, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    new-instance v8, Lcom/android/systemui/statusbar/phone/NavigationBarView$12;

    invoke-direct {v8, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$12;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v7, v8}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1726
    invoke-direct/range {p0 .. p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateBTStatus()V

    .line 1729
    :cond_5
    const v7, 0x7f0a00ba

    invoke-direct {v0, v7}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/Button;

    .line 1730
    .local v7, "btn_left_temp_add":Landroid/widget/Button;
    if-eqz v7, :cond_6

    .line 1731
    invoke-virtual {v7, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1734
    :cond_6
    const v8, 0x7f0a00bb

    invoke-direct {v0, v8}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v8

    check-cast v8, Landroid/widget/Button;

    .line 1735
    .local v8, "btn_left_temp_dec":Landroid/widget/Button;
    if-eqz v8, :cond_7

    .line 1736
    invoke-virtual {v8, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1739
    :cond_7
    const v9, 0x7f0a00c4

    invoke-direct {v0, v9}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/Button;

    .line 1740
    .local v9, "btn_right_temp_add":Landroid/widget/Button;
    if-eqz v9, :cond_8

    .line 1741
    invoke-virtual {v9, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1744
    :cond_8
    const v10, 0x7f0a00c5

    invoke-direct {v0, v10}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v10

    check-cast v10, Landroid/widget/Button;

    .line 1745
    .local v10, "btn_right_temp_dec":Landroid/widget/Button;
    if-eqz v10, :cond_9

    .line 1746
    invoke-virtual {v10, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1749
    :cond_9
    const v11, 0x7f0a00b7

    invoke-direct {v0, v11}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v11

    check-cast v11, Landroid/widget/Button;

    .line 1750
    .local v11, "btn_fan_add":Landroid/widget/Button;
    if-eqz v11, :cond_a

    .line 1751
    invoke-virtual {v11, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1754
    :cond_a
    const v12, 0x7f0a00b8

    invoke-direct {v0, v12}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v12

    check-cast v12, Landroid/widget/Button;

    .line 1755
    .local v12, "btn_fan_dec":Landroid/widget/Button;
    if-eqz v12, :cond_b

    .line 1756
    invoke-virtual {v12, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1759
    :cond_b
    const v13, 0x7f0a00c1

    invoke-direct {v0, v13}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v13

    check-cast v13, Landroid/widget/Button;

    .line 1760
    .local v13, "btn_on_off":Landroid/widget/Button;
    if-eqz v13, :cond_c

    .line 1761
    invoke-virtual {v13, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1764
    :cond_c
    const v14, 0x7f0a00ae

    invoke-direct {v0, v14}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v14

    check-cast v14, Landroid/widget/Button;

    .line 1765
    .local v14, "btn_ac":Landroid/widget/Button;
    if-eqz v14, :cond_d

    .line 1766
    invoke-virtual {v14, v0}, Landroid/widget/Button;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 1769
    :cond_d
    const v15, 0x7f0a004a

    invoke-direct {v0, v15}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v15

    check-cast v15, Landroid/widget/LinearLayout;

    iput-object v15, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    .line 1770
    const v15, 0x7f0a0218

    invoke-direct {v0, v15}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v15

    check-cast v15, Landroid/widget/LinearLayout;

    iput-object v15, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->layout_air_func:Landroid/widget/LinearLayout;

    .line 1771
    invoke-virtual/range {p0 .. p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v15

    move-object/from16 v16, v1

    const-string v1, "audio"

    .end local v1    # "btnHome":Landroid/widget/ImageButton;
    .local v16, "btnHome":Landroid/widget/ImageButton;
    invoke-virtual {v15, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/AudioManager;

    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    .line 1772
    const v1, 0x7f0a02a1

    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Lcom/android/systemui/volume/JellyProgressView;

    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->jellyProgressView:Lcom/android/systemui/volume/JellyProgressView;

    .line 1775
    const v1, 0x7f0a029f

    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ImageButton;

    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomPre:Landroid/widget/ImageButton;

    .line 1776
    const v1, 0x7f0a029e

    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ImageButton;

    iput-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomNext:Landroid/widget/ImageButton;

    .line 1777
    iget-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomPre:Landroid/widget/ImageButton;

    const/16 v15, 0x8

    if-eqz v1, :cond_e

    iget-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomPre:Landroid/widget/ImageButton;

    invoke-virtual {v1, v15}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 1778
    :cond_e
    iget-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomNext:Landroid/widget/ImageButton;

    if-eqz v1, :cond_f

    iget-object v1, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->naviBottomNext:Landroid/widget/ImageButton;

    invoke-virtual {v1, v15}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 1780
    :cond_f
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    .line 1781
    .local v1, "filter":Landroid/content/IntentFilter;
    const-string v15, "com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS"

    invoke-virtual {v1, v15}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1782
    const-string v15, "com.mediatek.systemui.KEYCODE_MEDIA_NEXT"

    invoke-virtual {v1, v15}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1783
    invoke-virtual/range {p0 .. p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v15

    move-object/from16 v17, v2

    iget-object v2, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->myReceiver:Landroid/content/BroadcastReceiver;

    .end local v2    # "btnApps":Landroid/widget/ImageButton;
    .local v17, "btnApps":Landroid/widget/ImageButton;
    invoke-virtual {v15, v2, v1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 1785
    invoke-direct/range {p0 .. p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isNewLauncher()Z

    move-result v2

    if-eqz v2, :cond_11

    .line 1786
    iget-object v2, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v15, "p38"

    invoke-virtual {v2, v15}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_10

    iget-object v2, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v15, "p42"

    invoke-virtual {v2, v15}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_11

    .line 1787
    :cond_10
    const v2, 0x7f0a0431

    invoke-direct {v0, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    iput-object v2, v0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTvSoundVolume:Landroid/widget/TextView;

    .line 1790
    :cond_11
    return-void
.end method

.method private isBackCar()Z
    .locals 1

    .line 1925
    new-instance v0, Landroid/carstatus/CarStatus;

    invoke-direct {v0}, Landroid/carstatus/CarStatus;-><init>()V

    invoke-virtual {v0}, Landroid/carstatus/CarStatus;->getReversing()I

    move-result v0

    if-lez v0, :cond_0

    .line 1926
    const/4 v0, 0x1

    return v0

    .line 1928
    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method private isNewLauncher()Z
    .locals 3

    .line 3469
    const-string v0, "1"

    const-string v1, "ro.vendor.sys.skin_support"

    const-string v2, "0"

    invoke-static {v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static synthetic lambda$new$0(Lcom/android/systemui/statusbar/phone/NavigationBarView;Ljava/lang/Boolean;)V
    .locals 1
    .param p1, "exists"    # Ljava/lang/Boolean;

    .line 1572
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedStackExists:Z

    .line 1573
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateRecentsIcon()V

    .line 1574
    return-void
.end method

.method public static synthetic lambda$new$1(Lcom/android/systemui/statusbar/phone/NavigationBarView;Ljava/lang/Boolean;)V
    .locals 2
    .param p1, "exists"    # Ljava/lang/Boolean;

    .line 1571
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHandler:Lcom/android/systemui/statusbar/phone/NavigationBarView$H;

    new-instance v1, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarView$4XDnqGNkJ613pDIbZ7uGeE4B1z4;

    invoke-direct {v1, p0, p1}, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarView$4XDnqGNkJ613pDIbZ7uGeE4B1z4;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;Ljava/lang/Boolean;)V

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView$H;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method private notifyVerticalChangedListener(Z)V
    .locals 1
    .param p1, "newVertical"    # Z

    .line 1338
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOnVerticalChangedListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;

    if-eqz v0, :cond_0

    .line 1339
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOnVerticalChangedListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;

    invoke-interface {v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;->onVerticalChanged(Z)V

    .line 1341
    :cond_0
    return-void
.end method

.method private orientBackButton(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V
    .locals 3
    .param p1, "drawable"    # Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 762
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    const/4 v1, 0x1

    and-int/2addr v0, v1

    if-eqz v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 764
    .local v0, "useAltBack":Z
    :goto_0
    if-eqz v0, :cond_1

    .line 765
    const/high16 v1, -0x3d4c0000    # -90.0f

    goto :goto_1

    :cond_1
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getLayoutDirection()I

    move-result v2

    if-ne v2, v1, :cond_2

    const/high16 v1, 0x43340000    # 180.0f

    goto :goto_1

    :cond_2
    const/4 v1, 0x0

    .line 764
    :goto_1
    invoke-virtual {p1, v1}, Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;->setRotation(F)V

    .line 766
    return-void
.end method

.method private orientHomeButton(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V
    .locals 1
    .param p1, "drawable"    # Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 769
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    if-eqz v0, :cond_0

    const/high16 v0, 0x42b40000    # 90.0f

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    invoke-virtual {p1, v0}, Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;->setRotation(F)V

    .line 770
    return-void
.end method

.method private postCheckForInvalidLayout(Ljava/lang/String;)V
    .locals 3
    .param p1, "how"    # Ljava/lang/String;

    .line 1397
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHandler:Lcom/android/systemui/statusbar/phone/NavigationBarView$H;

    const/4 v1, 0x0

    const/16 v2, 0x21ee

    invoke-virtual {v0, v2, v1, v1, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView$H;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 1398
    return-void
.end method

.method private regOrUnregAirListen(Z)V
    .locals 5
    .param p1, "isReg"    # Z

    .line 1466
    if-eqz p1, :cond_0

    .line 1468
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "air_temp_reverse_flag"

    .line 1469
    invoke-static {v1}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    const/4 v2, 0x1

    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirTempMirrorCfgObserver:Landroid/database/ContentObserver;

    const/4 v4, -0x1

    .line 1468
    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;I)V

    goto :goto_0

    .line 1475
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirTempMirrorCfgObserver:Landroid/database/ContentObserver;

    invoke-virtual {v0, v1}, Landroid/content/ContentResolver;->unregisterContentObserver(Landroid/database/ContentObserver;)V

    .line 1477
    :goto_0
    return-void
.end method

.method private registerMediaButtonReceiver()V
    .locals 3

    .line 1427
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 1428
    .local v0, "filter":Landroid/content/IntentFilter;
    const-string v1, "com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1429
    const-string v1, "com.mediatek.systemui.KEYCODE_MEDIA_NEXT"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1430
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->myReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 1431
    const-string v1, "StatusBar/NavBarView"

    const-string v2, "Media button receiver registered"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1432
    return-void
.end method

.method private registerVolumeReceiver()V
    .locals 4

    .line 1909
    :try_start_0
    new-instance v0, Landroid/content/IntentFilter;

    const-string v1, "android.media.VOLUME_CHANGED_ACTION"

    invoke-direct {v0, v1}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 1910
    .local v0, "filter":Landroid/content/IntentFilter;
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 1913
    .end local v0    # "filter":Landroid/content/IntentFilter;
    goto :goto_0

    .line 1911
    :catch_0
    move-exception v0

    .line 1912
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "StatusBar/NavBarView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "registerVolumeReceiver error: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1914
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method

.method private reloadNavIcons()V
    .locals 3

    .line 694
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    sget-object v1, Landroid/content/res/Configuration;->EMPTY:Landroid/content/res/Configuration;

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    invoke-direct {p0, v0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateIcons(Landroid/content/Context;Landroid/content/res/Configuration;Landroid/content/res/Configuration;)V

    .line 695
    return-void
.end method

.method private resetAutoDismissTimer()V
    .locals 0

    .line 1869
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->startAutoDismissTimer()V

    .line 1870
    return-void
.end method

.method private setBTConnectStatusBackgroundAndText(Z)V
    .locals 6
    .param p1, "isConnected"    # Z

    .line 3397
    const-string v0, "persist.sys.skin"

    const-string v1, "com.hcn.auto.theme.p1"

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 3398
    .local v0, "skinName":Ljava/lang/String;
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v1

    iget v1, v1, Landroid/content/res/Configuration;->uiMode:I

    and-int/lit8 v1, v1, 0x30

    const/16 v2, 0x20

    if-ne v1, v2, :cond_0

    const-string v1, "night"

    goto :goto_0

    :cond_0
    const-string v1, ""

    .line 3401
    .local v1, "uiStyle":Ljava/lang/String;
    :goto_0
    const-string v2, "sa181"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 3402
    const-string v2, "night"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 3403
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    if-eqz p1, :cond_1

    const v3, 0x7f080197

    goto :goto_1

    :cond_1
    const v3, 0x7f080192

    :goto_1
    invoke-virtual {v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v2

    goto :goto_3

    .line 3404
    :cond_2
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    if-eqz p1, :cond_3

    const v3, 0x7f08018e

    goto :goto_2

    :cond_3
    const v3, 0x7f08018f

    :goto_2
    invoke-virtual {v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v2

    :goto_3
    goto :goto_5

    .line 3405
    :cond_4
    const-string v2, "p45"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_6

    .line 3406
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    if-eqz p1, :cond_5

    const v3, 0x7f080195

    goto :goto_4

    :cond_5
    const v3, 0x7f080190

    :goto_4
    invoke-virtual {v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v2

    .local v2, "backgroundResId":I
    :goto_5
    goto :goto_8

    .line 3407
    .end local v2    # "backgroundResId":I
    :cond_6
    const-string/jumbo v2, "xt819"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_8

    .line 3408
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    if-eqz p1, :cond_7

    const v3, 0x7f080199

    goto :goto_6

    :cond_7
    const v3, 0x7f080194

    :goto_6
    invoke-virtual {v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v2

    goto :goto_5

    .line 3410
    :cond_8
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    if-eqz p1, :cond_9

    const v3, 0x7f080196

    goto :goto_7

    :cond_9
    const v3, 0x7f080191

    :goto_7
    invoke-virtual {v2, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v2

    .line 3413
    .restart local v2    # "backgroundResId":I
    :goto_8
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    invoke-virtual {v3, v2}, Landroid/widget/Button;->setBackgroundResource(I)V

    .line 3414
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v4

    if-eqz p1, :cond_a

    const v5, 0x7f1101a4

    goto :goto_9

    :cond_a
    const v5, 0x7f1101a5

    :goto_9
    invoke-virtual {v4, v5}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 3415
    return-void
.end method

.method private setSlippery(Z)V
    .locals 5
    .param p1, "slippery"    # Z

    .line 1026
    const/4 v0, 0x0

    .line 1027
    .local v0, "changed":Z
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getParent()Landroid/view/ViewParent;

    move-result-object v1

    check-cast v1, Landroid/view/ViewGroup;

    .line 1028
    .local v1, "navbarView":Landroid/view/ViewGroup;
    nop

    .line 1029
    invoke-virtual {v1}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v2

    check-cast v2, Landroid/view/WindowManager$LayoutParams;

    .line 1030
    .local v2, "lp":Landroid/view/WindowManager$LayoutParams;
    if-nez v2, :cond_0

    .line 1031
    return-void

    .line 1033
    :cond_0
    const/high16 v3, 0x20000000

    if-eqz p1, :cond_1

    iget v4, v2, Landroid/view/WindowManager$LayoutParams;->flags:I

    and-int/2addr v4, v3

    if-nez v4, :cond_1

    .line 1034
    iget v4, v2, Landroid/view/WindowManager$LayoutParams;->flags:I

    or-int/2addr v3, v4

    iput v3, v2, Landroid/view/WindowManager$LayoutParams;->flags:I

    .line 1035
    const/4 v0, 0x1

    goto :goto_0

    .line 1036
    :cond_1
    if-nez p1, :cond_2

    iget v4, v2, Landroid/view/WindowManager$LayoutParams;->flags:I

    and-int/2addr v3, v4

    if-eqz v3, :cond_2

    .line 1037
    iget v3, v2, Landroid/view/WindowManager$LayoutParams;->flags:I

    const v4, -0x20000001

    and-int/2addr v3, v4

    iput v3, v2, Landroid/view/WindowManager$LayoutParams;->flags:I

    .line 1038
    const/4 v0, 0x1

    .line 1040
    :cond_2
    :goto_0
    if-eqz v0, :cond_3

    .line 1041
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v3

    const-string/jumbo v4, "window"

    invoke-virtual {v3, v4}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/view/WindowManager;

    .line 1042
    .local v3, "wm":Landroid/view/WindowManager;
    invoke-interface {v3, v1, v2}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1044
    .end local v3    # "wm":Landroid/view/WindowManager;
    :cond_3
    return-void
.end method

.method private setUpSwipeUpOnboarding(Z)V
    .locals 1
    .param p1, "connectedToOverviewProxy"    # Z

    .line 1480
    if-eqz p1, :cond_0

    .line 1481
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

    invoke-virtual {v0}, Lcom/android/systemui/recents/RecentsOnboarding;->onConnectedToLauncher()V

    goto :goto_0

    .line 1483
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

    invoke-virtual {v0}, Lcom/android/systemui/recents/RecentsOnboarding;->onDisconnectedFromLauncher()V

    .line 1485
    :goto_0
    return-void
.end method

.method private setUseFadingAnimations(Z)V
    .locals 4
    .param p1, "useFadingAnimations"    # Z

    .line 980
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    .line 981
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager$LayoutParams;

    .line 982
    .local v0, "lp":Landroid/view/WindowManager$LayoutParams;
    if-eqz v0, :cond_3

    .line 983
    iget v1, v0, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    const/4 v2, 0x0

    if-eqz v1, :cond_0

    const/4 v1, 0x1

    goto :goto_0

    :cond_0
    move v1, v2

    .line 984
    .local v1, "old":Z
    :goto_0
    if-nez v1, :cond_1

    if-eqz p1, :cond_1

    .line 985
    const v2, 0x7f120008

    iput v2, v0, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    goto :goto_1

    .line 986
    :cond_1
    if-eqz v1, :cond_2

    if-nez p1, :cond_2

    .line 987
    iput v2, v0, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    .line 991
    :goto_1
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v2

    const-string/jumbo v3, "window"

    invoke-virtual {v2, v3}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/view/WindowManager;

    .line 992
    .local v2, "wm":Landroid/view/WindowManager;
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getParent()Landroid/view/ViewParent;

    move-result-object v3

    check-cast v3, Landroid/view/View;

    invoke-interface {v2, v3, v0}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 992
    .end local v1    # "old":Z
    .end local v2    # "wm":Landroid/view/WindowManager;
    goto :goto_2

    .line 989
    .restart local v1    # "old":Z
    :cond_2
    return-void

    .line 994
    .end local v1    # "old":Z
    :cond_3
    :goto_2
    return-void
.end method

.method private shouldDeadZoneConsumeTouchEvents(Landroid/view/MotionEvent;)Z
    .locals 4
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 589
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZone:Lcom/android/systemui/statusbar/policy/DeadZone;

    invoke-virtual {v0, p1}, Lcom/android/systemui/statusbar/policy/DeadZone;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZoneConsuming:Z

    if-eqz v0, :cond_0

    goto :goto_0

    .line 605
    :cond_0
    return v1

    .line 590
    :cond_1
    :goto_0
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    const/4 v2, 0x3

    const/4 v3, 0x1

    if-eq v0, v2, :cond_2

    packed-switch v0, :pswitch_data_0

    goto :goto_1

    .line 593
    :pswitch_0
    invoke-direct {p0, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setSlippery(Z)V

    .line 594
    iput-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZoneConsuming:Z

    .line 595
    goto :goto_1

    .line 599
    :cond_2
    :pswitch_1
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSlippery()V

    .line 600
    iput-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZoneConsuming:Z

    .line 603
    :goto_1
    return v3

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method private showVolumeDialog()V
    .locals 6

    .line 1795
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1796
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateVolumeProgress()V

    .line 1797
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->resetAutoDismissTimer()V

    .line 1798
    return-void

    .line 1801
    :cond_0
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    const v2, 0x7f1200c8

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    .line 1802
    .local v0, "builder":Landroid/app/AlertDialog$Builder;
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    invoke-static {v1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v1

    .line 1803
    .local v1, "inflater":Landroid/view/LayoutInflater;
    const v2, 0x7f0d014a

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v2

    .line 1805
    .local v2, "customView":Landroid/view/View;
    const v3, 0x7f0a0395

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/SeekBar;

    iput-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    .line 1807
    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    .line 1808
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateVolumeProgress()V

    .line 1810
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v3

    iput-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    .line 1811
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    invoke-virtual {v3}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v3

    const/16 v4, 0x7f6

    invoke-virtual {v3, v4}, Landroid/view/Window;->setType(I)V

    .line 1814
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    const/4 v5, 0x3

    invoke-virtual {v4, v5}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/widget/SeekBar;->setMax(I)V

    .line 1815
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    invoke-virtual {v4, v5}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 1816
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    new-instance v4, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;

    invoke-direct {v4, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$13;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v3, v4}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    .line 1839
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    new-instance v4, Lcom/android/systemui/statusbar/phone/NavigationBarView$14;

    invoke-direct {v4, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$14;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v3, v4}, Landroid/app/AlertDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 1848
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    invoke-virtual {v3}, Landroid/app/AlertDialog;->show()V

    .line 1849
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->startAutoDismissTimer()V

    .line 1850
    return-void
.end method

.method private startAutoDismissTimer()V
    .locals 4

    .line 1864
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cancelAutoDismissTimer()V

    .line 1865
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAutoDismissHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAutoDismissRunnable:Ljava/lang/Runnable;

    const-wide/16 v2, 0xbb8

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 1866
    return-void
.end method

.method private unregisterVolumeReceiver()V
    .locals 4

    .line 1918
    :try_start_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 1921
    goto :goto_0

    .line 1919
    :catch_0
    move-exception v0

    .line 1920
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "StatusBar/NavBarView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "unregisterVolumeReceiver error: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1922
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method

.method private upDataIon(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3016
    const v0, 0x7f0a01c2

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3017
    return-void
.end method

.method private upDataZone(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3020
    const v0, 0x7f0a0494

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3021
    return-void
.end method

.method private updateAutoLevel(I)V
    .locals 3
    .param p1, "autoLevel"    # I

    .line 3151
    const/4 v0, 0x0

    .line 3152
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 3153
    packed-switch p1, :pswitch_data_0

    .line 3164
    goto :goto_0

    .line 3161
    :pswitch_0
    const v0, 0x7f08016f

    .line 3162
    goto :goto_0

    .line 3158
    :pswitch_1
    const v0, 0x7f08016b

    .line 3159
    goto :goto_0

    .line 3155
    :pswitch_2
    const v0, 0x7f080167

    .line 3156
    goto :goto_0

    .line 3167
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 3175
    :pswitch_3
    const v0, 0x7f08016c

    .line 3176
    goto :goto_0

    .line 3172
    :pswitch_4
    const v0, 0x7f080168

    .line 3173
    goto :goto_0

    .line 3169
    :pswitch_5
    const v0, 0x7f080164

    .line 3170
    nop

    .line 3182
    :goto_0
    const v1, 0x7f0a0080

    if-nez v0, :cond_1

    .line 3183
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 3185
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3188
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 3189
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch
.end method

.method private updateBTStatus()V
    .locals 1

    .line 3385
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    if-nez v0, :cond_0

    goto :goto_0

    .line 3390
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 3391
    return-void

    .line 3393
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

    invoke-interface {v0}, Lcom/android/systemui/statusbar/policy/BluetoothController;->isBluetoothConnected()Z

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setBTConnectStatusBackgroundAndText(Z)V

    .line 3394
    return-void

    .line 3386
    :cond_2
    :goto_0
    return-void
.end method

.method private updateButtonLocationOnScreen(Lcom/android/systemui/statusbar/phone/ButtonDispatcher;Landroid/graphics/Rect;)V
    .locals 8
    .param p1, "button"    # Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .param p2, "buttonBounds"    # Landroid/graphics/Rect;

    .line 1210
    invoke-virtual {p1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->getCurrentView()Landroid/view/View;

    move-result-object v0

    .line 1211
    .local v0, "view":Landroid/view/View;
    if-nez v0, :cond_0

    .line 1212
    invoke-virtual {p2}, Landroid/graphics/Rect;->setEmpty()V

    .line 1213
    return-void

    .line 1216
    :cond_0
    invoke-virtual {v0}, Landroid/view/View;->getTranslationX()F

    move-result v1

    .line 1217
    .local v1, "posX":F
    invoke-virtual {v0}, Landroid/view/View;->getTranslationY()F

    move-result v2

    .line 1218
    .local v2, "posY":F
    const/4 v3, 0x0

    invoke-virtual {v0, v3}, Landroid/view/View;->setTranslationX(F)V

    .line 1219
    invoke-virtual {v0, v3}, Landroid/view/View;->setTranslationY(F)V

    .line 1220
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpPosition:[I

    invoke-virtual {v0, v3}, Landroid/view/View;->getLocationInWindow([I)V

    .line 1221
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpPosition:[I

    const/4 v4, 0x0

    aget v3, v3, v4

    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpPosition:[I

    const/4 v6, 0x1

    aget v5, v5, v6

    iget-object v7, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpPosition:[I

    aget v4, v7, v4

    .line 1222
    invoke-virtual {v0}, Landroid/view/View;->getMeasuredWidth()I

    move-result v7

    add-int/2addr v4, v7

    iget-object v7, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpPosition:[I

    aget v6, v7, v6

    .line 1223
    invoke-virtual {v0}, Landroid/view/View;->getMeasuredHeight()I

    move-result v7

    add-int/2addr v6, v7

    .line 1221
    invoke-virtual {p2, v3, v5, v4, v6}, Landroid/graphics/Rect;->set(IIII)V

    .line 1224
    invoke-virtual {v0, v1}, Landroid/view/View;->setTranslationX(F)V

    .line 1225
    invoke-virtual {v0, v2}, Landroid/view/View;->setTranslationY(F)V

    .line 1226
    return-void
.end method

.method private updateCarMode(Landroid/content/res/Configuration;)Z
    .locals 5
    .param p1, "newConfig"    # Landroid/content/res/Configuration;

    .line 1364
    const/4 v0, 0x0

    .line 1365
    .local v0, "uiCarModeChanged":Z
    if-eqz p1, :cond_1

    .line 1366
    iget v1, p1, Landroid/content/res/Configuration;->uiMode:I

    and-int/lit8 v1, v1, 0xf

    .line 1367
    .local v1, "uiMode":I
    const/4 v2, 0x3

    const/4 v3, 0x0

    if-ne v1, v2, :cond_0

    const/4 v2, 0x1

    goto :goto_0

    :cond_0
    move v2, v3

    .line 1369
    .local v2, "isCarMode":Z
    :goto_0
    iget-boolean v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInCarMode:Z

    if-eq v2, v4, :cond_1

    .line 1370
    iput-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInCarMode:Z

    .line 1376
    iput-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    .line 1380
    .end local v1    # "uiMode":I
    .end local v2    # "isCarMode":Z
    :cond_1
    return v0
.end method

.method private updateCurrentView()V
    .locals 5

    .line 1243
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1244
    return-void

    .line 1247
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisplay:Landroid/view/Display;

    invoke-virtual {v0}, Landroid/view/Display;->getRotation()I

    move-result v0

    .line 1248
    .local v0, "rot":I
    const/4 v1, 0x0

    move v2, v1

    .local v2, "i":I
    :goto_0
    const/4 v3, 0x4

    if-ge v2, v3, :cond_1

    .line 1249
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotatedViews:[Landroid/view/View;

    aget-object v3, v3, v2

    const/16 v4, 0x8

    invoke-virtual {v3, v4}, Landroid/view/View;->setVisibility(I)V

    .line 1248
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 1251
    .end local v2    # "i":I
    :cond_1
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotatedViews:[Landroid/view/View;

    aget-object v2, v2, v0

    iput-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentView:Landroid/view/View;

    .line 1252
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentView:Landroid/view/View;

    invoke-virtual {v2, v1}, Landroid/view/View;->setVisibility(I)V

    .line 1253
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

    const/4 v3, 0x1

    if-ne v0, v3, :cond_2

    move v1, v3

    nop

    :cond_2
    invoke-virtual {v2, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;->setAlternativeOrder(Z)V

    .line 1254
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;->updateButtonDispatchersCurrentView()V

    .line 1255
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateLayoutTransitionsEnabled()V

    .line 1256
    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentRotation:I

    .line 1257
    return-void
.end method

.method private updateFast(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3040
    const v0, 0x7f0a0163

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3041
    return-void
.end method

.method private updateFollower(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3044
    const v0, 0x7f0a016c

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3045
    return-void
.end method

.method private updateIcons(Landroid/content/Context;Landroid/content/res/Configuration;Landroid/content/res/Configuration;)V
    .locals 6
    .param p1, "ctx"    # Landroid/content/Context;
    .param p2, "oldConfig"    # Landroid/content/res/Configuration;
    .param p3, "newConfig"    # Landroid/content/res/Configuration;

    .line 709
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 710
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "updateIcons, is portrait state, return!"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 711
    return-void

    .line 714
    :cond_0
    const v0, 0x7f040104

    invoke-static {p1, v0}, Lcom/android/settingslib/Utils;->getThemeAttr(Landroid/content/Context;I)I

    move-result v0

    .line 715
    .local v0, "dualToneDarkTheme":I
    const v1, 0x7f040260

    invoke-static {p1, v1}, Lcom/android/settingslib/Utils;->getThemeAttr(Landroid/content/Context;I)I

    move-result v1

    .line 716
    .local v1, "dualToneLightTheme":I
    new-instance v2, Landroid/view/ContextThemeWrapper;

    invoke-direct {v2, p1, v1}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 717
    .local v2, "lightContext":Landroid/content/Context;
    new-instance v3, Landroid/view/ContextThemeWrapper;

    invoke-direct {v3, p1, v0}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 719
    .local v3, "darkContext":Landroid/content/Context;
    iget v4, p2, Landroid/content/res/Configuration;->orientation:I

    iget v5, p3, Landroid/content/res/Configuration;->orientation:I

    if-ne v4, v5, :cond_1

    iget v4, p2, Landroid/content/res/Configuration;->densityDpi:I

    iget v5, p3, Landroid/content/res/Configuration;->densityDpi:I

    if-eq v4, v5, :cond_2

    .line 721
    :cond_1
    const v4, 0x7f0803f6

    invoke-direct {p0, v2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 722
    invoke-virtual {p0, v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeDrawable(Landroid/content/Context;Landroid/content/Context;)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHomeDefaultIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 724
    :cond_2
    iget v4, p2, Landroid/content/res/Configuration;->densityDpi:I

    iget v5, p3, Landroid/content/res/Configuration;->densityDpi:I

    if-ne v4, v5, :cond_3

    .line 725
    invoke-virtual {p2}, Landroid/content/res/Configuration;->getLayoutDirection()I

    move-result v4

    invoke-virtual {p3}, Landroid/content/res/Configuration;->getLayoutDirection()I

    move-result v5

    if-eq v4, v5, :cond_4

    .line 726
    :cond_3
    invoke-virtual {p0, v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackDrawable(Landroid/content/Context;Landroid/content/Context;)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 727
    const v4, 0x7f0803ff

    invoke-direct {p0, v2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 728
    const v4, 0x7f0803fd

    invoke-direct {p0, v2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mMenuIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 730
    const v4, 0x7f0803ef

    const/4 v5, 0x0

    invoke-direct {p0, v2, v3, v4, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;IZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAccessibilityIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 733
    const v4, 0x7f0802e0

    invoke-direct {p0, v2, v3, v4, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;IZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v4

    iput-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mImeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 736
    iget v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateBtnStyle:I

    invoke-virtual {p0, v4, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateRotateSuggestionButtonStyle(IZ)V

    .line 742
    :cond_4
    return-void
.end method

.method private updateLayoutTransitionsEnabled()V
    .locals 7

    .line 955
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v0

    if-nez v0, :cond_0

    .line 956
    return-void

    .line 959
    :cond_0
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mWakeAndUnlocking:Z

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mLayoutTransitionsEnabled:Z

    if-eqz v0, :cond_1

    move v0, v2

    goto :goto_0

    :cond_1
    move v0, v1

    .line 960
    .local v0, "enabled":Z
    :goto_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v3

    const v4, 0x7f0a029c

    invoke-virtual {v3, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/view/ViewGroup;

    .line 961
    .local v3, "navButtons":Landroid/view/ViewGroup;
    if-eqz v3, :cond_3

    .line 962
    invoke-virtual {v3}, Landroid/view/ViewGroup;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v4

    .line 963
    .local v4, "lt":Landroid/animation/LayoutTransition;
    if-eqz v4, :cond_3

    .line 964
    const/4 v5, 0x3

    const/4 v6, 0x2

    if-eqz v0, :cond_2

    .line 965
    invoke-virtual {v4, v6}, Landroid/animation/LayoutTransition;->enableTransitionType(I)V

    .line 966
    invoke-virtual {v4, v5}, Landroid/animation/LayoutTransition;->enableTransitionType(I)V

    .line 967
    invoke-virtual {v4, v1}, Landroid/animation/LayoutTransition;->enableTransitionType(I)V

    .line 968
    invoke-virtual {v4, v2}, Landroid/animation/LayoutTransition;->enableTransitionType(I)V

    goto :goto_1

    .line 970
    :cond_2
    invoke-virtual {v4, v6}, Landroid/animation/LayoutTransition;->disableTransitionType(I)V

    .line 971
    invoke-virtual {v4, v5}, Landroid/animation/LayoutTransition;->disableTransitionType(I)V

    .line 972
    invoke-virtual {v4, v1}, Landroid/animation/LayoutTransition;->disableTransitionType(I)V

    .line 973
    invoke-virtual {v4, v2}, Landroid/animation/LayoutTransition;->disableTransitionType(I)V

    .line 977
    .end local v4    # "lt":Landroid/animation/LayoutTransition;
    :cond_3
    :goto_1
    return-void
.end method

.method private updateMaxIconFront(I)V
    .locals 1
    .param p1, "maxFront"    # I

    .line 3147
    const v0, 0x7f0a025a

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3148
    return-void
.end method

.method private updateNomal(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3036
    const v0, 0x7f0a02b0

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3037
    return-void
.end method

.method private updateRearLock(I)V
    .locals 1
    .param p1, "rearLock"    # I

    .line 3233
    const v0, 0x7f0a032c

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3234
    return-void
.end method

.method private updateRecentsIcon()V
    .locals 2

    .line 1260
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    if-eqz v0, :cond_2

    .line 1261
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedStackExists:Z

    if-eqz v1, :cond_0

    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    if-eqz v1, :cond_0

    const/high16 v1, 0x42b40000    # 90.0f

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;->setRotation(F)V

    .line 1262
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedStackExists:Z

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDockedIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_1

    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    :goto_1
    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 1264
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;->reapplyDarkIntensity()V

    .line 1265
    return-void
.end method

.method private updateRightDirection(I)V
    .locals 3
    .param p1, "nShow"    # I

    .line 2417
    const-string v0, "StatusBar/NavBarView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "show wind mode "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2418
    const/4 v0, 0x0

    .line 2419
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 2420
    packed-switch p1, :pswitch_data_0

    .line 2451
    goto :goto_0

    .line 2448
    :pswitch_0
    const v0, 0x7f0805c8

    .line 2449
    goto :goto_0

    .line 2445
    :pswitch_1
    const v0, 0x7f0805ae

    .line 2446
    goto :goto_0

    .line 2442
    :pswitch_2
    const v0, 0x7f0805c3

    .line 2443
    goto :goto_0

    .line 2439
    :pswitch_3
    const v0, 0x7f0805ca

    .line 2440
    goto :goto_0

    .line 2436
    :pswitch_4
    const v0, 0x7f0805c6

    .line 2437
    goto :goto_0

    .line 2433
    :pswitch_5
    const v0, 0x7f0805b6

    .line 2434
    goto :goto_0

    .line 2430
    :pswitch_6
    const v0, 0x7f0805a9

    .line 2431
    goto :goto_0

    .line 2427
    :pswitch_7
    const v0, 0x7f0805b2

    .line 2428
    goto :goto_0

    .line 2424
    :pswitch_8
    const v0, 0x7f0805b0

    .line 2425
    goto :goto_0

    .line 2422
    :pswitch_9
    return-void

    .line 2454
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 2482
    :pswitch_a
    const v0, 0x7f0805c7

    .line 2483
    goto :goto_0

    .line 2479
    :pswitch_b
    const v0, 0x7f0805ad

    .line 2480
    goto :goto_0

    .line 2476
    :pswitch_c
    const v0, 0x7f0805c2

    .line 2477
    goto :goto_0

    .line 2473
    :pswitch_d
    const v0, 0x7f0805c9

    .line 2474
    goto :goto_0

    .line 2470
    :pswitch_e
    const v0, 0x7f0805c5

    .line 2471
    goto :goto_0

    .line 2467
    :pswitch_f
    const v0, 0x7f0805b5

    .line 2468
    goto :goto_0

    .line 2464
    :pswitch_10
    const v0, 0x7f0805a8

    .line 2465
    goto :goto_0

    .line 2461
    :pswitch_11
    const v0, 0x7f0805b1

    .line 2462
    goto :goto_0

    .line 2458
    :pswitch_12
    const v0, 0x7f0805af

    .line 2459
    goto :goto_0

    .line 2456
    :pswitch_13
    return-void

    .line 2489
    :goto_0
    const/4 v1, 0x1

    const v2, 0x7f0a012c

    invoke-virtual {p0, v2, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(IZ)V

    .line 2490
    invoke-virtual {p0, v2, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 2491
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x0
        :pswitch_13
        :pswitch_12
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
    .end packed-switch
.end method

.method private updateSlippery()V
    .locals 1

    .line 1022
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isQuickStepSwipeUpEnabled()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mPanelView:Lcom/android/systemui/statusbar/phone/NotificationPanelView;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/NotificationPanelView;->isFullyExpanded()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setSlippery(Z)V

    .line 1023
    return-void
.end method

.method private updateSoft(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3032
    const v0, 0x7f0a03b9

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3033
    return-void
.end method

.method private updateSteerHeat(I)V
    .locals 1
    .param p1, "steerHeat"    # I

    .line 3024
    const v0, 0x7f0a03d5

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3025
    return-void
.end method

.method private updateSync(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3028
    const v0, 0x7f0a0329

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3029
    return-void
.end method

.method private updateTaskSwitchHelper()V
    .locals 3

    .line 1313
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    if-nez v0, :cond_0

    return-void

    .line 1314
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getLayoutDirection()I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    goto :goto_0

    :cond_1
    const/4 v1, 0x0

    :goto_0
    move v0, v1

    .line 1315
    .local v0, "isRtl":Z
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    iget-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    invoke-interface {v1, v2, v0}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->setBarState(ZZ)V

    .line 1316
    return-void
.end method

.method private updateTemp(I)V
    .locals 1
    .param p1, "nValue"    # I

    .line 3052
    const v0, 0x7f0a03ec

    invoke-direct {p0, v0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3053
    return-void
.end method

.method private updateVolumeProgress()V
    .locals 2

    .line 1878
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    if-eqz v0, :cond_0

    .line 1879
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v0

    .line 1880
    .local v0, "currentVolume":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mSeekBarVolume:Landroid/widget/SeekBar;

    invoke-virtual {v1, v0}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 1882
    .end local v0    # "currentVolume":I
    :cond_0
    return-void
.end method

.method private updateWindIntensity(I)V
    .locals 3
    .param p1, "windIntensity"    # I

    .line 3192
    const/4 v0, 0x0

    .line 3193
    .local v0, "nResID":I
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v2, "p38"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 3194
    packed-switch p1, :pswitch_data_0

    .line 3205
    goto :goto_0

    .line 3202
    :pswitch_0
    const v0, 0x7f08016e

    .line 3203
    goto :goto_0

    .line 3199
    :pswitch_1
    const v0, 0x7f08016a

    .line 3200
    goto :goto_0

    .line 3196
    :pswitch_2
    const v0, 0x7f080166

    .line 3197
    goto :goto_0

    .line 3208
    :cond_0
    packed-switch p1, :pswitch_data_1

    goto :goto_0

    .line 3216
    :pswitch_3
    const v0, 0x7f08016d

    .line 3217
    goto :goto_0

    .line 3213
    :pswitch_4
    const v0, 0x7f080169

    .line 3214
    goto :goto_0

    .line 3210
    :pswitch_5
    const v0, 0x7f080165

    .line 3211
    nop

    .line 3223
    :goto_0
    const v1, 0x7f0a0475

    if-nez v0, :cond_1

    .line 3224
    const/4 v2, 0x0

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    goto :goto_1

    .line 3226
    :cond_1
    const/4 v2, 0x1

    invoke-direct {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewVisible(II)V

    .line 3229
    :goto_1
    invoke-virtual {p0, v1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewImageByID(II)V

    .line 3230
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x1
        :pswitch_5
        :pswitch_4
        :pswitch_3
    .end packed-switch
.end method

.method private static visibilityToString(I)Ljava/lang/String;
    .locals 1
    .param p0, "vis"    # I

    .line 1401
    const/4 v0, 0x4

    if-eq p0, v0, :cond_1

    const/16 v0, 0x8

    if-eq p0, v0, :cond_0

    .line 1407
    const-string v0, "VISIBLE"

    return-object v0

    .line 1405
    :cond_0
    const-string v0, "GONE"

    return-object v0

    .line 1403
    :cond_1
    const-string v0, "INVISIBLE"

    return-object v0
.end method


# virtual methods
.method protected SetViewGone(IZ)V
    .locals 2
    .param p1, "id"    # I
    .param p2, "gone"    # Z

    .line 3319
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    .line 3320
    .local v0, "view":Landroid/view/View;
    if-eqz v0, :cond_1

    .line 3321
    if-eqz p2, :cond_0

    .line 3322
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    .line 3324
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 3327
    :cond_1
    :goto_0
    return-void
.end method

.method protected SetViewImageByID(II)V
    .locals 2
    .param p1, "id"    # I
    .param p2, "nResID"    # I

    .line 3337
    if-nez p2, :cond_0

    .line 3338
    return-void

    .line 3340
    :cond_0
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 3341
    .local v0, "view":Landroid/widget/ImageView;
    if-eqz v0, :cond_1

    .line 3342
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 3344
    :cond_1
    return-void
.end method

.method protected SetViewTextByID(ILjava/lang/String;)V
    .locals 1
    .param p1, "id"    # I
    .param p2, "nStr"    # Ljava/lang/String;

    .line 3330
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 3331
    .local v0, "view":Landroid/widget/TextView;
    if-eqz v0, :cond_0

    .line 3332
    invoke-virtual {v0, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 3334
    :cond_0
    return-void
.end method

.method protected SetViewVisible(IZ)V
    .locals 2
    .param p1, "id"    # I
    .param p2, "bVisible"    # Z

    .line 3308
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    .line 3309
    .local v0, "view":Landroid/view/View;
    if-eqz v0, :cond_1

    .line 3310
    if-eqz p2, :cond_0

    .line 3311
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    .line 3313
    :cond_0
    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 3316
    :cond_1
    :goto_0
    return-void
.end method

.method public UpdateSpeed(II)V
    .locals 4
    .param p1, "nWind"    # I
    .param p2, "windMax"    # I

    .line 2297
    const/4 v0, 0x0

    .line 2299
    .local v0, "pro":Landroid/widget/ProgressBar;
    iget v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->old_level_max:I

    if-eq v1, p2, :cond_0

    .line 2300
    const-string v1, "StatusBar/NavBarView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "level max change hide "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->old_level_max:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2301
    iget v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->old_level_max:I

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getAirBar(I)Landroid/widget/ProgressBar;

    move-result-object v1

    const/16 v2, 0x8

    invoke-virtual {v1, v2}, Landroid/widget/ProgressBar;->setVisibility(I)V

    .line 2303
    :cond_0
    invoke-direct {p0, p2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getAirBar(I)Landroid/widget/ProgressBar;

    move-result-object v0

    .line 2304
    iput p2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->old_level_max:I

    .line 2305
    if-eqz v0, :cond_1

    .line 2309
    invoke-virtual {v0, p2}, Landroid/widget/ProgressBar;->setMax(I)V

    .line 2311
    :cond_1
    if-eqz v0, :cond_2

    .line 2312
    const-string v1, "StatusBar/NavBarView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "set progress bar "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2313
    invoke-virtual {v0, p1}, Landroid/widget/ProgressBar;->setProgress(I)V

    .line 2315
    :cond_2
    return-void
.end method

.method public abortCurrentGesture()V
    .locals 1

    .line 614
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->abortCurrentGesture()V

    .line 615
    return-void
.end method

.method public dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .locals 11
    .param p1, "fd"    # Ljava/io/FileDescriptor;
    .param p2, "pw"    # Ljava/io/PrintWriter;
    .param p3, "args"    # [Ljava/lang/String;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "DefaultLocale"
        }
    .end annotation

    .line 1506
    const-string v0, "NavigationBarView {"

    invoke-virtual {p2, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1508
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 1509
    .local v0, "r":Landroid/graphics/Rect;
    new-instance v1, Landroid/graphics/Point;

    invoke-direct {v1}, Landroid/graphics/Point;-><init>()V

    .line 1510
    .local v1, "size":Landroid/graphics/Point;
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisplay:Landroid/view/Display;

    invoke-virtual {v2, v1}, Landroid/view/Display;->getRealSize(Landroid/graphics/Point;)V

    .line 1512
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "      this: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p0}, Lcom/android/systemui/statusbar/phone/StatusBar;->viewInfo(Landroid/view/View;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1513
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getVisibility()I

    move-result v3

    invoke-static {v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->visibilityToString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    new-array v4, v3, [Ljava/lang/Object;

    .line 1512
    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1515
    invoke-virtual {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getWindowVisibleDisplayFrame(Landroid/graphics/Rect;)V

    .line 1516
    iget v2, v0, Landroid/graphics/Rect;->right:I

    iget v4, v1, Landroid/graphics/Point;->x:I

    const/4 v5, 0x1

    if-gt v2, v4, :cond_1

    iget v2, v0, Landroid/graphics/Rect;->bottom:I

    iget v4, v1, Landroid/graphics/Point;->y:I

    if-le v2, v4, :cond_0

    goto :goto_0

    :cond_0
    move v2, v3

    goto :goto_1

    :cond_1
    :goto_0
    move v2, v5

    .line 1517
    .local v2, "offscreen":Z
    :goto_1
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "      window: "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1518
    invoke-virtual {v0}, Landroid/graphics/Rect;->toShortString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1519
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getWindowVisibility()I

    move-result v6

    invoke-static {v6}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->visibilityToString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1520
    if-eqz v2, :cond_2

    const-string v6, " OFFSCREEN!"

    goto :goto_2

    :cond_2
    const-string v6, ""

    :goto_2
    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 1517
    invoke-virtual {p2, v4}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1522
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v4

    const/4 v6, 0x3

    const/4 v7, 0x2

    if-eqz v4, :cond_3

    .line 1523
    const-string v4, "      mCurrentView: id=%s (%dx%d) %s %f"

    const/4 v8, 0x5

    new-array v8, v8, [Ljava/lang/Object;

    .line 1524
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v9

    invoke-virtual {v9}, Landroid/view/View;->getId()I

    move-result v9

    invoke-direct {p0, v9}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getResourceName(I)Ljava/lang/String;

    move-result-object v9

    aput-object v9, v8, v3

    .line 1525
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v9

    invoke-virtual {v9}, Landroid/view/View;->getWidth()I

    move-result v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v5

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v9

    invoke-virtual {v9}, Landroid/view/View;->getHeight()I

    move-result v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v7

    .line 1526
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v9

    invoke-virtual {v9}, Landroid/view/View;->getVisibility()I

    move-result v9

    invoke-static {v9}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->visibilityToString(I)Ljava/lang/String;

    move-result-object v9

    aput-object v9, v8, v6

    const/4 v9, 0x4

    .line 1527
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v10

    invoke-virtual {v10}, Landroid/view/View;->getAlpha()F

    move-result v10

    invoke-static {v10}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v10

    aput-object v10, v8, v9

    .line 1523
    invoke-static {v4, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {p2, v4}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_3

    .line 1529
    :cond_3
    const-string v4, "      mCurrentView: null"

    invoke-virtual {p2, v4}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1532
    :goto_3
    const-string v4, "      disabled=0x%08x vertical=%s menu=%s"

    new-array v6, v6, [Ljava/lang/Object;

    iget v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    .line 1533
    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v6, v3

    .line 1534
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    if-eqz v3, :cond_4

    const-string v3, "true"

    goto :goto_4

    :cond_4
    const-string v3, "false"

    :goto_4
    aput-object v3, v6, v5

    .line 1535
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    if-eqz v3, :cond_5

    const-string v3, "true"

    goto :goto_5

    :cond_5
    const-string v3, "false"

    :goto_5
    aput-object v3, v6, v7

    .line 1532
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p2, v3}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1537
    const-string v3, "back"

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v4

    invoke-static {p2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->dumpButton(Ljava/io/PrintWriter;Ljava/lang/String;Lcom/android/systemui/statusbar/phone/ButtonDispatcher;)V

    .line 1538
    const-string v3, "home"

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v4

    invoke-static {p2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->dumpButton(Ljava/io/PrintWriter;Ljava/lang/String;Lcom/android/systemui/statusbar/phone/ButtonDispatcher;)V

    .line 1539
    const-string v3, "rcnt"

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v4

    invoke-static {p2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->dumpButton(Ljava/io/PrintWriter;Ljava/lang/String;Lcom/android/systemui/statusbar/phone/ButtonDispatcher;)V

    .line 1540
    const-string v3, "menu"

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getMenuButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v4

    invoke-static {p2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->dumpButton(Ljava/io/PrintWriter;Ljava/lang/String;Lcom/android/systemui/statusbar/phone/ButtonDispatcher;)V

    .line 1541
    const-string v3, "a11y"

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getAccessibilityButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v4

    invoke-static {p2, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->dumpButton(Ljava/io/PrintWriter;Ljava/lang/String;Lcom/android/systemui/statusbar/phone/ButtonDispatcher;)V

    .line 1543
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

    invoke-virtual {v3, p2}, Lcom/android/systemui/recents/RecentsOnboarding;->dump(Ljava/io/PrintWriter;)V

    .line 1545
    const-string v3, "    }"

    invoke-virtual {p2, v3}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1546
    return-void
.end method

.method public getAccessibilityButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 648
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a000f

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getBackButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 636
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a0082

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getBackDrawable(Landroid/content/Context;Landroid/content/Context;)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 2
    .param p1, "lightContext"    # Landroid/content/Context;
    .param p2, "darkContext"    # Landroid/content/Context;

    .line 745
    const v0, 0x7f0803f0

    const v1, 0x7f0803f5

    invoke-direct {p0, p1, p2, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->chooseNavigationIconDrawable(Landroid/content/Context;Landroid/content/Context;II)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v0

    .line 747
    .local v0, "drawable":Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->orientBackButton(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 748
    return-object v0
.end method

.method public getBarTransitions()Lcom/android/systemui/statusbar/phone/BarTransitions;
    .locals 1

    .line 529
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    return-object v0
.end method

.method public getBtButton()Landroid/view/View;
    .locals 1

    .line 3435
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBTConnectStatus:Landroid/widget/Button;

    return-object v0
.end method

.method getButtonAtPosition(II)Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 4
    .param p1, "x"    # I
    .param p2, "y"    # I

    .line 1143
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    invoke-virtual {v1}, Landroid/util/SparseArray;->size()I

    move-result v1

    if-ge v0, v1, :cond_1

    .line 1144
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    invoke-virtual {v1, v0}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    .line 1145
    .local v1, "button":Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->getCurrentView()Landroid/view/View;

    move-result-object v2

    .line 1146
    .local v2, "buttonView":Landroid/view/View;
    if-eqz v2, :cond_0

    .line 1147
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpRect:Landroid/graphics/Rect;

    invoke-virtual {v2, v3}, Landroid/view/View;->getHitRect(Landroid/graphics/Rect;)V

    .line 1148
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpRect:Landroid/graphics/Rect;

    invoke-virtual {p0, v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->offsetDescendantRectToMyCoords(Landroid/view/View;Landroid/graphics/Rect;)V

    .line 1149
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTmpRect:Landroid/graphics/Rect;

    invoke-virtual {v3, p1, p2}, Landroid/graphics/Rect;->contains(II)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 1150
    return-object v1

    .line 1143
    .end local v1    # "button":Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .end local v2    # "buttonView":Landroid/view/View;
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 1154
    .end local v0    # "i":I
    :cond_1
    const/4 v0, 0x0

    return-object v0
.end method

.method public getButtonDispatchers()Landroid/util/SparseArray;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Landroid/util/SparseArray<",
            "Lcom/android/systemui/statusbar/phone/ButtonDispatcher;",
            ">;"
        }
    .end annotation

    .line 660
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    return-object v0
.end method

.method public getCurrentView()Landroid/view/View;
    .locals 1

    .line 620
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentView:Landroid/view/View;

    return-object v0
.end method

.method public getDownHitTarget()I
    .locals 1

    .line 610
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    return v0
.end method

.method public getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 640
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a01a5

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getHomeDrawable(Landroid/content/Context;Landroid/content/Context;)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    .locals 3
    .param p1, "lightContext"    # Landroid/content/Context;
    .param p2, "darkContext"    # Landroid/content/Context;

    .line 752
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-virtual {v0}, Lcom/android/systemui/OverviewProxyService;->shouldShowSwipeUpUI()Z

    move-result v0

    .line 753
    .local v0, "quickStepEnabled":Z
    if-eqz v0, :cond_0

    .line 754
    const v1, 0x7f0803fa

    invoke-direct {p0, p1, p2, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;I)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v1

    goto :goto_0

    .line 755
    :cond_0
    const v1, 0x7f0803f7

    const/4 v2, 0x0

    invoke-direct {p0, p1, p2, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;Landroid/content/Context;IZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v1

    .line 757
    .local v1, "drawable":Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    :goto_0
    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->orientHomeButton(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 758
    return-object v1
.end method

.method public getImeSwitchButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 644
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a01b6

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getLightTransitionsController()Lcom/android/systemui/statusbar/phone/LightBarTransitionsController;
    .locals 1

    .line 533
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;->getLightTransitionsController()Lcom/android/systemui/statusbar/phone/LightBarTransitionsController;

    move-result-object v0

    return-object v0
.end method

.method public getMenuBackButton()Landroid/view/View;
    .locals 1

    .line 3431
    const v0, 0x7f0a0267

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method public getMenuButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 632
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a0266

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getMenuContainer()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 656
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a0268

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getMenuHomeButton()Landroid/view/View;
    .locals 1

    .line 3427
    const v0, 0x7f0a0269

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method public getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 628
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a032e

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;
    .locals 2

    .line 652
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mButtonDispatchers:Landroid/util/SparseArray;

    const v1, 0x7f0a034d

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    return-object v0
.end method

.method public getVolAddButton()Landroid/view/View;
    .locals 1

    .line 3419
    const v0, 0x7f0a0041

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method public getVolReduceButton()Landroid/view/View;
    .locals 1

    .line 3423
    const v0, 0x7f0a0332

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method public handleBluetoothCallback(Z)V
    .locals 2
    .param p1, "set"    # Z

    .line 3373
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

    if-nez v0, :cond_0

    .line 3374
    return-void

    .line 3377
    :cond_0
    if-eqz p1, :cond_1

    .line 3378
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCallback:Lcom/android/systemui/statusbar/policy/BluetoothController$Callback;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/BluetoothController;->addCallback(Ljava/lang/Object;)V

    goto :goto_0

    .line 3380
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mController:Lcom/android/systemui/statusbar/policy/BluetoothController;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCallback:Lcom/android/systemui/statusbar/policy/BluetoothController$Callback;

    invoke-interface {v0, v1}, Lcom/android/systemui/statusbar/policy/BluetoothController;->removeCallback(Ljava/lang/Object;)V

    .line 3382
    :goto_0
    return-void
.end method

.method public inScreenPinning()Z
    .locals 1

    .line 940
    invoke-static {}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->getInstance()Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->isScreenPinningActive()Z

    move-result v0

    return v0
.end method

.method public initBottomBar()V
    .locals 3

    .line 3443
    const v0, 0x7f0a029d

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    .line 3444
    .local v0, "layout":Landroid/widget/RelativeLayout;
    new-instance v1, Lcom/hcn/auto/systemui/InitViewImpl;

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    invoke-direct {v1, v0, v2}, Lcom/hcn/auto/systemui/InitViewImpl;-><init>(Landroid/view/View;Landroid/content/Context;)V

    iput-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    .line 3445
    return-void
.end method

.method public initHcnPort()V
    .locals 2

    .line 1577
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "initHcnPort"

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1578
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->initPortView()V

    .line 1579
    return-void
.end method

.method public initLeftSideBar()V
    .locals 1

    .line 3448
    const-class v0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->setUp()V

    .line 3449
    return-void
.end method

.method initPortSkinResource()V
    .locals 5

    .line 3532
    const-string v0, "persist.sys.skin"

    const-string v1, ""

    invoke-static {v0, v1}, Lcom/android/systemui/util/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 3533
    .local v0, "configSkin":Ljava/lang/String;
    const/4 v1, 0x0

    .line 3534
    .local v1, "apkPath":Ljava/lang/String;
    const-string v2, "StatusBar/NavBarView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onCreate "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 3535
    const-string v2, ""

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_1

    .line 3536
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "com.hcn.systemui.theme."

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getApkPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 3537
    const-string v2, "StatusBar/NavBarView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "skin pkg "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 3538
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 3539
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    invoke-static {v2}, Lcom/hcn/auto/systemui/SkinApkManager;->getInstance(Landroid/content/Context;)Lcom/hcn/auto/systemui/SkinApkManager;

    move-result-object v2

    invoke-virtual {v2, v0}, Lcom/hcn/auto/systemui/SkinApkManager;->getApkPathBySkinName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 3540
    const-string v2, "StatusBar/NavBarView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "skin pkg --is empty"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 3542
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    invoke-static {v2}, Lcom/hcn/auto/systemui/SkinApkManager;->getInstance(Landroid/content/Context;)Lcom/hcn/auto/systemui/SkinApkManager;

    move-result-object v2

    invoke-virtual {v2, v1}, Lcom/hcn/auto/systemui/SkinApkManager;->setSkinApkPath(Ljava/lang/String;)V

    .line 3543
    const-string v2, "StatusBar/NavBarView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "skin pkg --not empty"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 3546
    :cond_1
    :goto_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    invoke-static {v2}, Lcom/hcn/auto/systemui/SkinApkManager;->getInstance(Landroid/content/Context;)Lcom/hcn/auto/systemui/SkinApkManager;

    move-result-object v2

    invoke-virtual {v2, v1}, Lcom/hcn/auto/systemui/SkinApkManager;->loadSkinByAbsolutePath(Ljava/lang/String;)Z

    .line 3547
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    invoke-virtual {v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v2

    iput-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mResources:Landroid/content/res/Resources;

    .line 3548
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    invoke-virtual {v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinPkgName()Ljava/lang/String;

    move-result-object v2

    iput-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mPackageName:Ljava/lang/String;

    .line 3549
    return-void
.end method

.method public isOverviewEnabled()Z
    .locals 2

    .line 668
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    const/high16 v1, 0x1000000

    and-int/2addr v0, v1

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isQuickScrubEnabled()Z
    .locals 2

    .line 676
    const-string v0, "persist.quickstep.scrub.enabled"

    const/4 v1, 0x1

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    .line 677
    invoke-virtual {v0}, Lcom/android/systemui/OverviewProxyService;->isEnabled()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isOverviewEnabled()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    .line 678
    invoke-virtual {v0}, Lcom/android/systemui/OverviewProxyService;->getInteractionFlags()I

    move-result v0

    and-int/lit8 v0, v0, 0x2

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    .line 676
    :goto_0
    return v1
.end method

.method public isQuickStepSwipeUpEnabled()Z
    .locals 1

    .line 672
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-virtual {v0}, Lcom/android/systemui/OverviewProxyService;->shouldShowSwipeUpUI()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isOverviewEnabled()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isRecentsButtonVisible()Z
    .locals 1

    .line 664
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->getVisibility()I

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isRotateButtonVisible()Z
    .locals 1

    .line 1136
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowRotateButton:Z

    return v0
.end method

.method public needsReorient(I)Z
    .locals 1
    .param p1, "rotation"    # I

    .line 1238
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentRotation:I

    if-eq v0, p1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public onApplyWindowInsets(Landroid/view/WindowInsets;)Landroid/view/WindowInsets;
    .locals 4
    .param p1, "insets"    # Landroid/view/WindowInsets;

    .line 1550
    invoke-virtual {p1}, Landroid/view/WindowInsets;->getSystemWindowInsetLeft()I

    move-result v0

    invoke-virtual {p1}, Landroid/view/WindowInsets;->getSystemWindowInsetTop()I

    move-result v1

    .line 1551
    invoke-virtual {p1}, Landroid/view/WindowInsets;->getSystemWindowInsetRight()I

    move-result v2

    invoke-virtual {p1}, Landroid/view/WindowInsets;->getSystemWindowInsetBottom()I

    move-result v3

    .line 1550
    invoke-virtual {p0, v0, v1, v2, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setPadding(IIII)V

    .line 1552
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->onApplyWindowInsets(Landroid/view/WindowInsets;)Landroid/view/WindowInsets;

    move-result-object v0

    return-object v0
.end method

.method protected onAttachedToWindow()V
    .locals 3

    .line 1413
    invoke-super {p0}, Landroid/widget/FrameLayout;->onAttachedToWindow()V

    .line 1414
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->requestApplyInsets()V

    .line 1415
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->reorient()V

    .line 1416
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->onPluginDisconnected(Lcom/android/systemui/plugins/statusbar/phone/NavGesture;)V

    .line 1417
    const-class v0, Lcom/android/systemui/plugins/PluginManager;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/plugins/PluginManager;

    const-class v1, Lcom/android/systemui/plugins/statusbar/phone/NavGesture;

    const/4 v2, 0x0

    invoke-interface {v0, p0, v1, v2}, Lcom/android/systemui/plugins/PluginManager;->addPluginListener(Lcom/android/systemui/plugins/PluginListener;Ljava/lang/Class;Z)V

    .line 1419
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isQuickStepSwipeUpEnabled()Z

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setUpSwipeUpOnboarding(Z)V

    .line 1420
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->initAirTempMirrorCfg()V

    .line 1421
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->registerMediaButtonReceiver()V

    .line 1422
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->registerVolumeReceiver()V

    .line 1423
    return-void
.end method

.method protected onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 3
    .param p1, "newConfig"    # Landroid/content/res/Configuration;

    .line 1345
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 1347
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateCarMode(Landroid/content/res/Configuration;)Z

    move-result v0

    .line 1348
    .local v0, "uiCarModeChanged":Z
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateTaskSwitchHelper()V

    .line 1349
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    invoke-direct {p0, v1, v2, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateIcons(Landroid/content/Context;Landroid/content/res/Configuration;Landroid/content/res/Configuration;)V

    .line 1350
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateRecentsIcon()V

    .line 1351
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

    invoke-virtual {v1, p1}, Lcom/android/systemui/recents/RecentsOnboarding;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 1352
    if-nez v0, :cond_0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    iget v1, v1, Landroid/content/res/Configuration;->densityDpi:I

    iget v2, p1, Landroid/content/res/Configuration;->densityDpi:I

    if-ne v1, v2, :cond_0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    .line 1353
    invoke-virtual {v1}, Landroid/content/res/Configuration;->getLayoutDirection()I

    move-result v1

    invoke-virtual {p1}, Landroid/content/res/Configuration;->getLayoutDirection()I

    move-result v2

    if-eq v1, v2, :cond_1

    .line 1355
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNavButtonIcons()V

    .line 1357
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mConfiguration:Landroid/content/res/Configuration;

    invoke-virtual {v1, p1}, Landroid/content/res/Configuration;->updateFrom(Landroid/content/res/Configuration;)I

    .line 1358
    return-void
.end method

.method public onDarkIntensityChange(F)V
    .locals 1
    .param p1, "intensity"    # F

    .line 1186
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    if-eqz v0, :cond_0

    .line 1187
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v0, p1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->onDarkIntensityChange(F)V

    .line 1189
    :cond_0
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 2

    .line 1436
    invoke-super {p0}, Landroid/widget/FrameLayout;->onDetachedFromWindow()V

    .line 1437
    const-class v0, Lcom/android/systemui/plugins/PluginManager;

    invoke-static {v0}, Lcom/android/systemui/Dependency;->get(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/plugins/PluginManager;

    invoke-interface {v0, p0}, Lcom/android/systemui/plugins/PluginManager;->removePluginListener(Lcom/android/systemui/plugins/PluginListener;)V

    .line 1438
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    if-eqz v0, :cond_0

    .line 1439
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v0}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->destroy()V

    .line 1441
    :cond_0
    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setUpSwipeUpOnboarding(Z)V

    .line 1442
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mContext:Landroid/content/Context;

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    if-eqz v1, :cond_1

    .line 1443
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v1}, Lcom/hcn/auto/systemui/InitViewImpl;->unregisterTimeReceiver()V

    .line 1445
    :cond_1
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->regOrUnregAirListen(Z)V

    .line 1447
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->myReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 1448
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mMediaBtnHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHideMediaBtnRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 1449
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->unregisterVolumeReceiver()V

    .line 1450
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cancelAutoDismissTimer()V

    .line 1451
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 1452
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVolumeDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V

    .line 1454
    :cond_2
    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 1
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 1193
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v0, p1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->onDraw(Landroid/graphics/Canvas;)V

    .line 1194
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZone:Lcom/android/systemui/statusbar/policy/DeadZone;

    invoke-virtual {v0, p1}, Lcom/android/systemui/statusbar/policy/DeadZone;->onDraw(Landroid/graphics/Canvas;)V

    .line 1195
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->onDraw(Landroid/graphics/Canvas;)V

    .line 1196
    return-void
.end method

.method public onFinishInflate()V
    .locals 2

    .line 1159
    invoke-super {p0}, Landroid/widget/FrameLayout;->onFinishInflate()V

    .line 1160
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "onFinishInflate..."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1163
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1164
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "onFinishInflate, is portrait state, return!"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1165
    iput-object p0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentView:Landroid/view/View;

    .line 1166
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->initPortView()V

    .line 1167
    return-void

    .line 1173
    :cond_0
    return-void
.end method

.method public onInterceptTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 4
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 554
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->shouldDeadZoneConsumeTouchEvents(Landroid/view/MotionEvent;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    .line 555
    return v1

    .line 557
    :cond_0
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    .line 559
    :cond_1
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    float-to-int v0, v0

    .line 560
    .local v0, "x":I
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v2

    float-to-int v2, v2

    .line 561
    .local v2, "y":I
    const/4 v3, 0x0

    iput v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    .line 562
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->isVisible()Z

    move-result v3

    if-eqz v3, :cond_2

    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackButtonBounds:Landroid/graphics/Rect;

    invoke-virtual {v3, v0, v2}, Landroid/graphics/Rect;->contains(II)Z

    move-result v3

    if-eqz v3, :cond_2

    .line 563
    iput v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    goto :goto_0

    .line 564
    :cond_2
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->isVisible()Z

    move-result v1

    if-eqz v1, :cond_3

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHomeButtonBounds:Landroid/graphics/Rect;

    invoke-virtual {v1, v0, v2}, Landroid/graphics/Rect;->contains(II)Z

    move-result v1

    if-eqz v1, :cond_3

    .line 565
    const/4 v1, 0x2

    iput v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    goto :goto_0

    .line 566
    :cond_3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->isVisible()Z

    move-result v1

    if-eqz v1, :cond_4

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsButtonBounds:Landroid/graphics/Rect;

    invoke-virtual {v1, v0, v2}, Landroid/graphics/Rect;->contains(II)Z

    move-result v1

    if-eqz v1, :cond_4

    .line 567
    const/4 v1, 0x3

    iput v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    goto :goto_0

    .line 568
    :cond_4
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->isVisible()Z

    move-result v1

    if-eqz v1, :cond_5

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotationButtonBounds:Landroid/graphics/Rect;

    .line 569
    invoke-virtual {v1, v0, v2}, Landroid/graphics/Rect;->contains(II)Z

    move-result v1

    if-eqz v1, :cond_5

    .line 570
    const/4 v1, 0x4

    iput v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDownHitTarget:I

    .line 574
    .end local v0    # "x":I
    .end local v2    # "y":I
    :cond_5
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v0, p1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->onInterceptTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    return v0
.end method

.method protected onLayout(ZIIII)V
    .locals 8
    .param p1, "changed"    # Z
    .param p2, "left"    # I
    .param p3, "top"    # I
    .param p4, "right"    # I
    .param p5, "bottom"    # I

    .line 1200
    invoke-super/range {p0 .. p5}, Landroid/widget/FrameLayout;->onLayout(ZIIII)V

    .line 1201
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBackButtonBounds:Landroid/graphics/Rect;

    invoke-direct {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateButtonLocationOnScreen(Lcom/android/systemui/statusbar/phone/ButtonDispatcher;Landroid/graphics/Rect;)V

    .line 1202
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHomeButtonBounds:Landroid/graphics/Rect;

    invoke-direct {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateButtonLocationOnScreen(Lcom/android/systemui/statusbar/phone/ButtonDispatcher;Landroid/graphics/Rect;)V

    .line 1203
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsButtonBounds:Landroid/graphics/Rect;

    invoke-direct {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateButtonLocationOnScreen(Lcom/android/systemui/statusbar/phone/ButtonDispatcher;Landroid/graphics/Rect;)V

    .line 1204
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotationButtonBounds:Landroid/graphics/Rect;

    invoke-direct {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateButtonLocationOnScreen(Lcom/android/systemui/statusbar/phone/ButtonDispatcher;Landroid/graphics/Rect;)V

    .line 1205
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    move v3, p1

    move v4, p2

    move v5, p3

    move v6, p4

    move v7, p5

    invoke-interface/range {v2 .. v7}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->onLayout(ZIIII)V

    .line 1206
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsOnboarding:Lcom/android/systemui/recents/RecentsOnboarding;

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getMeasuredHeight()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/systemui/recents/RecentsOnboarding;->setNavBarHeight(I)V

    .line 1207
    return-void
.end method

.method public onNavigationButtonLongPress(Landroid/view/View;)V
    .locals 1
    .param p1, "v"    # Landroid/view/View;

    .line 997
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v0, p1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->onNavigationButtonLongPress(Landroid/view/View;)V

    .line 998
    return-void
.end method

.method public onPanelExpandedChange(Z)V
    .locals 0
    .param p1, "expanded"    # Z

    .line 1001
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSlippery()V

    .line 1002
    return-void
.end method

.method public bridge synthetic onPluginConnected(Lcom/android/systemui/plugins/Plugin;Landroid/content/Context;)V
    .locals 0

    .line 159
    check-cast p1, Lcom/android/systemui/plugins/statusbar/phone/NavGesture;

    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->onPluginConnected(Lcom/android/systemui/plugins/statusbar/phone/NavGesture;Landroid/content/Context;)V

    return-void
.end method

.method public onPluginConnected(Lcom/android/systemui/plugins/statusbar/phone/NavGesture;Landroid/content/Context;)V
    .locals 1
    .param p1, "plugin"    # Lcom/android/systemui/plugins/statusbar/phone/NavGesture;
    .param p2, "context"    # Landroid/content/Context;

    .line 1489
    invoke-interface {p1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture;->getGestureHelper()Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    .line 1490
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateTaskSwitchHelper()V

    .line 1491
    return-void
.end method

.method public bridge synthetic onPluginDisconnected(Lcom/android/systemui/plugins/Plugin;)V
    .locals 0

    .line 159
    check-cast p1, Lcom/android/systemui/plugins/statusbar/phone/NavGesture;

    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->onPluginDisconnected(Lcom/android/systemui/plugins/statusbar/phone/NavGesture;)V

    return-void
.end method

.method public onPluginDisconnected(Lcom/android/systemui/plugins/statusbar/phone/NavGesture;)V
    .locals 3
    .param p1, "plugin"    # Lcom/android/systemui/plugins/statusbar/phone/NavGesture;

    .line 1495
    new-instance v0, Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;-><init>(Landroid/content/Context;)V

    .line 1496
    .local v0, "defaultHelper":Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsComponent:Lcom/android/systemui/RecentsComponent;

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDivider:Lcom/android/systemui/stackdivider/Divider;

    invoke-virtual {v0, v1, v2, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;->setComponents(Lcom/android/systemui/RecentsComponent;Lcom/android/systemui/stackdivider/Divider;Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 1497
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    if-eqz v1, :cond_0

    .line 1498
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->destroy()V

    .line 1500
    :cond_0
    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    .line 1501
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateTaskSwitchHelper()V

    .line 1502
    return-void
.end method

.method protected onSizeChanged(IIII)V
    .locals 2
    .param p1, "w"    # I
    .param p2, "h"    # I
    .param p3, "oldw"    # I
    .param p4, "oldh"    # I

    .line 1325
    if-lez p1, :cond_0

    if-le p2, p1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 1326
    .local v0, "newVertical":Z
    :goto_0
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    if-eq v0, v1, :cond_1

    .line 1327
    iput-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    .line 1329
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->reorient()V

    .line 1330
    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->notifyVerticalChangedListener(Z)V

    .line 1333
    :cond_1
    const-string v1, "sizeChanged"

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->postCheckForInvalidLayout(Ljava/lang/String;)V

    .line 1334
    invoke-super {p0, p1, p2, p3, p4}, Landroid/widget/FrameLayout;->onSizeChanged(IIII)V

    .line 1335
    return-void
.end method

.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 8
    .param p1, "view"    # Landroid/view/View;
    .param p2, "motionEvent"    # Landroid/view/MotionEvent;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "NonConstantResourceId"
        }
    .end annotation

    .line 285
    const/4 v0, 0x0

    if-nez p2, :cond_0

    .line 286
    return v0

    .line 289
    :cond_0
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v1

    const/4 v2, 0x2

    if-ne v1, v2, :cond_1

    .line 290
    return v0

    .line 322
    :cond_1
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getViewId(Landroid/view/View;)I

    move-result v1

    .line 323
    .local v1, "id":I
    const v3, 0x7f0a00ba

    const/4 v4, 0x4

    const/4 v5, 0x6

    if-ne v1, v3, :cond_3

    .line 324
    iget-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    if-eqz v2, :cond_2

    .line 325
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v5, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto/16 :goto_0

    .line 327
    :cond_2
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v4, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto/16 :goto_0

    .line 329
    :cond_3
    const v3, 0x7f0a00bb

    const/4 v6, 0x5

    const/4 v7, 0x7

    if-ne v1, v3, :cond_5

    .line 330
    iget-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    if-eqz v2, :cond_4

    .line 331
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v7, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto/16 :goto_0

    .line 333
    :cond_4
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v6, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto/16 :goto_0

    .line 335
    :cond_5
    const v3, 0x7f0a00c4

    if-ne v1, v3, :cond_7

    .line 336
    iget-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    if-eqz v2, :cond_6

    .line 337
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v4, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto/16 :goto_0

    .line 339
    :cond_6
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v5, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto :goto_0

    .line 341
    :cond_7
    const v3, 0x7f0a00c5

    if-ne v1, v3, :cond_9

    .line 342
    iget-boolean v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    if-eqz v2, :cond_8

    .line 343
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v6, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto :goto_0

    .line 345
    :cond_8
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v7, v3}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto :goto_0

    .line 347
    :cond_9
    const v3, 0x7f0a00b7

    if-ne v1, v3, :cond_a

    .line 348
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v3

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v4

    invoke-virtual {v3, v2, v4}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto :goto_0

    .line 349
    :cond_a
    const v2, 0x7f0a00b8

    if-ne v1, v2, :cond_b

    .line 350
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    const/4 v3, 0x3

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v4

    invoke-virtual {v2, v3, v4}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto :goto_0

    .line 351
    :cond_b
    const v2, 0x7f0a00c1

    if-ne v1, v2, :cond_c

    .line 352
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    const/4 v3, 0x1

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v4

    invoke-virtual {v2, v3, v4}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V

    goto :goto_0

    .line 353
    :cond_c
    const v2, 0x7f0a00ae

    if-ne v1, v2, :cond_d

    .line 354
    invoke-static {}, Lcom/hcn/autocan/aidl/AirCtrlManager;->getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;

    move-result-object v2

    const/16 v3, 0x10

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v4

    invoke-virtual {v2, v3, v4}, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrl(II)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 358
    .end local v1    # "id":I
    :cond_d
    :goto_0
    goto :goto_1

    .line 356
    :catch_0
    move-exception v1

    .line 357
    .local v1, "e":Ljava/lang/Exception;
    const-string v2, "StatusBar/NavBarView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "err "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 359
    .end local v1    # "e":Ljava/lang/Exception;
    :goto_1
    return v0
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 2
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 579
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->shouldDeadZoneConsumeTouchEvents(Landroid/view/MotionEvent;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    .line 580
    return v1

    .line 582
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    invoke-interface {v0, p1}, Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 583
    return v1

    .line 585
    :cond_1
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    return v0
.end method

.method public onVolumeChanged(IIZ)V
    .locals 6
    .param p1, "stream"    # I
    .param p2, "volume"    # I
    .param p3, "mute"    # Z

    .line 3452
    move v0, p2

    .line 3453
    .local v0, "vol":I
    const/4 v1, 0x3

    if-ne p1, v1, :cond_0

    if-eqz p3, :cond_0

    .line 3454
    const/4 v0, 0x0

    .line 3456
    :cond_0
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v2, "%02d"

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v3, v4

    invoke-static {v1, v2, v3}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 3457
    .local v1, "value":Ljava/lang/String;
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTvSoundVolume:Landroid/widget/TextView;

    if-eqz v2, :cond_1

    .line 3458
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTvSoundVolume:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 3460
    :cond_1
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->jellyProgressView:Lcom/android/systemui/volume/JellyProgressView;

    if-eqz v2, :cond_2

    .line 3461
    int-to-float v2, v0

    const/high16 v3, 0x42c80000    # 100.0f

    mul-float/2addr v2, v3

    const/high16 v3, 0x42200000    # 40.0f

    div-float/2addr v2, v3

    .line 3462
    .local v2, "progress":F
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->jellyProgressView:Lcom/android/systemui/volume/JellyProgressView;

    invoke-virtual {v3, v2}, Lcom/android/systemui/volume/JellyProgressView;->setProgress(F)V

    .line 3464
    .end local v2    # "progress":F
    :cond_2
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    if-eqz v2, :cond_3

    .line 3465
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v2, p1, p2, p3}, Lcom/hcn/auto/systemui/InitViewImpl;->onVolumeChanged(IIZ)V

    .line 3467
    :cond_3
    return-void
.end method

.method public reorient()V
    .locals 2

    .line 1276
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "reorient..."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1279
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1280
    return-void

    .line 1283
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 1284
    const-string v0, "StatusBar/NavBarView"

    const-string v1, "reorient, is portrait state, return!"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1285
    return-void

    .line 1288
    :cond_1
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateCurrentView()V

    .line 1290
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRootView()Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/NavigationBarFrame;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZone:Lcom/android/systemui/statusbar/policy/DeadZone;

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFrame;->setDeadZone(Lcom/android/systemui/statusbar/policy/DeadZone;)V

    .line 1291
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDeadZone:Lcom/android/systemui/statusbar/policy/DeadZone;

    iget v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mCurrentRotation:I

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/policy/DeadZone;->onConfigurationChanged(I)V

    .line 1294
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;->init()V

    .line 1295
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setMenuVisibility(ZZ)V

    .line 1303
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isLayoutDirectionResolved()Z

    move-result v0

    if-nez v0, :cond_2

    .line 1304
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->resolveLayoutDirection()Z

    .line 1306
    :cond_2
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateTaskSwitchHelper()V

    .line 1307
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNavButtonIcons()V

    .line 1309
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVertical(Z)V

    .line 1310
    return-void
.end method

.method public setAccessibilityButtonState(ZZ)V
    .locals 3
    .param p1, "visible"    # Z
    .param p2, "longClickable"    # Z

    .line 1066
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowAccessibilityButton:Z

    .line 1067
    iput-boolean p2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mLongClickableAccessibilityButton:Z

    .line 1068
    const/4 v0, 0x4

    const/4 v1, 0x0

    if-eqz p1, :cond_0

    .line 1070
    const/4 v2, 0x1

    invoke-virtual {p0, v1, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setMenuVisibility(ZZ)V

    .line 1071
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getImeSwitchButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v2

    invoke-virtual {v2, v0}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 1072
    invoke-virtual {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setRotateButtonVisibility(Z)I

    .line 1075
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getAccessibilityButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v2

    if-eqz p1, :cond_1

    move v0, v1

    nop

    :cond_1
    invoke-virtual {v2, v0}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 1076
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getAccessibilityButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v0

    invoke-virtual {v0, p2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setLongClickable(Z)V

    .line 1077
    return-void
.end method

.method public setComponents(Lcom/android/systemui/RecentsComponent;Lcom/android/systemui/stackdivider/Divider;Lcom/android/systemui/statusbar/phone/NotificationPanelView;)V
    .locals 1
    .param p1, "recentsComponent"    # Lcom/android/systemui/RecentsComponent;
    .param p2, "divider"    # Lcom/android/systemui/stackdivider/Divider;
    .param p3, "panel"    # Lcom/android/systemui/statusbar/phone/NotificationPanelView;

    .line 538
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRecentsComponent:Lcom/android/systemui/RecentsComponent;

    .line 539
    iput-object p2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDivider:Lcom/android/systemui/stackdivider/Divider;

    .line 540
    iput-object p3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mPanelView:Lcom/android/systemui/statusbar/phone/NotificationPanelView;

    .line 541
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    instance-of v0, v0, Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;

    if-eqz v0, :cond_0

    .line 542
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mGestureHelper:Lcom/android/systemui/plugins/statusbar/phone/NavGesture$GestureHelper;

    check-cast v0, Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;

    invoke-virtual {v0, p1, p2, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarGestureHelper;->setComponents(Lcom/android/systemui/RecentsComponent;Lcom/android/systemui/stackdivider/Divider;Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    .line 545
    :cond_0
    return-void
.end method

.method public setDisabledFlags(I)V
    .locals 2
    .param p1, "disabledFlags"    # I

    .line 838
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    if-ne v0, p1, :cond_0

    return-void

    .line 840
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isOverviewEnabled()Z

    move-result v0

    .line 841
    .local v0, "overviewEnabledBefore":Z
    iput p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    .line 844
    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isOverviewEnabled()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 845
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->reloadNavIcons()V

    .line 848
    :cond_1
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNavButtonIcons()V

    .line 849
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSlippery()V

    .line 850
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isQuickStepSwipeUpEnabled()Z

    move-result v1

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setUpSwipeUpOnboarding(Z)V

    .line 851
    return-void
.end method

.method public setLayoutDirection(I)V
    .locals 0
    .param p1, "layoutDirection"    # I

    .line 805
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->reloadNavIcons()V

    .line 807
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->setLayoutDirection(I)V

    .line 808
    return-void
.end method

.method public setLayoutTransitionsEnabled(Z)V
    .locals 0
    .param p1, "enabled"    # Z

    .line 944
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mLayoutTransitionsEnabled:Z

    .line 945
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateLayoutTransitionsEnabled()V

    .line 946
    return-void
.end method

.method public setMenuVisibility(Z)V
    .locals 1
    .param p1, "show"    # Z

    .line 1047
    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setMenuVisibility(ZZ)V

    .line 1048
    return-void
.end method

.method public setMenuVisibility(ZZ)V
    .locals 5
    .param p1, "show"    # Z
    .param p2, "force"    # Z

    .line 1051
    if-nez p2, :cond_0

    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    if-ne v0, p1, :cond_0

    return-void

    .line 1053
    :cond_0
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    .line 1056
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowAccessibilityButton:Z

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowRotateButton:Z

    if-nez v0, :cond_1

    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    and-int/lit8 v0, v0, 0x2

    if-nez v0, :cond_1

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    move v0, v1

    .line 1061
    .local v0, "shouldShow":Z
    :goto_0
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getMenuButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v2

    const/4 v3, 0x4

    if-eqz v0, :cond_2

    move v4, v1

    goto :goto_1

    :cond_2
    move v4, v3

    :goto_1
    invoke-virtual {v2, v4}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 1062
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getMenuContainer()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v2

    if-eqz v0, :cond_3

    goto :goto_2

    :cond_3
    move v1, v3

    :goto_2
    invoke-virtual {v2, v1}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 1063
    return-void
.end method

.method public setNavigationIconHints(I)V
    .locals 3
    .param p1, "hints"    # I

    .line 823
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    if-ne p1, v0, :cond_0

    return-void

    .line 824
    :cond_0
    and-int/lit8 v0, p1, 0x1

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    move v0, v1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    .line 825
    .local v0, "backAlt":Z
    :goto_0
    iget v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    and-int/2addr v1, v2

    if-eqz v1, :cond_2

    if-nez v0, :cond_2

    .line 826
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTransitionListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;->onBackAltCleared()V

    .line 833
    :cond_2
    iput p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    .line 834
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNavButtonIcons()V

    .line 835
    return-void
.end method

.method public setOnVerticalChangedListener(Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;)V
    .locals 1
    .param p1, "onVerticalChangedListener"    # Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;

    .line 548
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOnVerticalChangedListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$OnVerticalChangedListener;

    .line 549
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->notifyVerticalChangedListener(Z)V

    .line 550
    return-void
.end method

.method public setRotateButtonVisibility(Z)I
    .locals 4
    .param p1, "visible"    # Z

    .line 1109
    const/4 v0, 0x0

    if-eqz p1, :cond_0

    iget-boolean v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowAccessibilityButton:Z

    if-nez v1, :cond_0

    const/4 v1, 0x1

    goto :goto_0

    :cond_0
    move v1, v0

    .line 1110
    .local v1, "adjVisible":Z
    :goto_0
    if-eqz v1, :cond_1

    move v2, v0

    goto :goto_1

    :cond_1
    const/4 v2, 0x4

    .line 1113
    .local v2, "vis":I
    :goto_1
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->getVisibility()I

    move-result v3

    if-ne v2, v3, :cond_2

    return v2

    .line 1115
    :cond_2
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v3

    invoke-virtual {v3, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 1116
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowRotateButton:Z

    .line 1119
    if-nez p1, :cond_3

    .line 1120
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    invoke-virtual {v3, v0}, Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 1121
    .local v0, "d":Landroid/graphics/drawable/Drawable;
    instance-of v3, v0, Landroid/graphics/drawable/AnimatedVectorDrawable;

    if-eqz v3, :cond_3

    .line 1122
    move-object v3, v0

    check-cast v3, Landroid/graphics/drawable/AnimatedVectorDrawable;

    .line 1123
    .local v3, "avd":Landroid/graphics/drawable/AnimatedVectorDrawable;
    invoke-virtual {v3}, Landroid/graphics/drawable/AnimatedVectorDrawable;->clearAnimationCallbacks()V

    .line 1124
    invoke-virtual {v3}, Landroid/graphics/drawable/AnimatedVectorDrawable;->reset()V

    .line 1129
    .end local v0    # "d":Landroid/graphics/drawable/Drawable;
    .end local v3    # "avd":Landroid/graphics/drawable/AnimatedVectorDrawable;
    :cond_3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNavButtonIcons()V

    .line 1132
    return v2
.end method

.method public setStreamVolumeValue(II)V
    .locals 2
    .param p1, "streamType"    # I
    .param p2, "nValue"    # I

    .line 1885
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x0

    invoke-virtual {v0, p1, p2, v1}, Landroid/media/AudioManager;->setStreamVolume(III)V

    .line 1886
    return-void
.end method

.method public setWakeAndUnlocking(Z)V
    .locals 0
    .param p1, "wakeAndUnlocking"    # Z

    .line 949
    invoke-direct {p0, p1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setUseFadingAnimations(Z)V

    .line 950
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mWakeAndUnlocking:Z

    .line 951
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateLayoutTransitionsEnabled()V

    .line 952
    return-void
.end method

.method public showAirData(Z)V
    .locals 4
    .param p1, "show"    # Z

    .line 1989
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 1990
    return-void

    .line 1993
    :cond_0
    const v0, 0x7f0a00ad

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 1994
    .local v0, "btImage":Landroid/widget/Button;
    if-eqz v0, :cond_2

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    if-eqz v1, :cond_2

    .line 1995
    const/16 v1, 0x8

    const/4 v2, 0x0

    if-eqz p1, :cond_1

    .line 1996
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v3, v2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 1997
    invoke-virtual {v0, v1}, Landroid/widget/Button;->setVisibility(I)V

    goto :goto_0

    .line 1999
    :cond_1
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v3, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 2000
    invoke-virtual {v0, v2}, Landroid/widget/Button;->setVisibility(I)V

    .line 2003
    :cond_2
    :goto_0
    return-void
.end method

.method public updateAir(Lcom/hcn/autocan/hiworld/gm/air/AIR;)V
    .locals 7
    .param p1, "air_obj"    # Lcom/hcn/autocan/hiworld/gm/air/AIR;

    .line 2117
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 2118
    return-void

    .line 2121
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 2122
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->layout_air_func:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_1

    .line 2123
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->layout_air_func:Landroid/widget/LinearLayout;

    new-instance v2, Lcom/android/systemui/statusbar/phone/NavigationBarView$17;

    invoke-direct {v2, p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView$17;-><init>(Lcom/android/systemui/statusbar/phone/NavigationBarView;)V

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 2132
    :cond_1
    const v0, 0x7f0a00ad

    invoke-direct {p0, v0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 2133
    .local v0, "btImage":Landroid/widget/Button;
    if-eqz v0, :cond_2

    .line 2134
    const/16 v2, 0x8

    invoke-virtual {v0, v2}, Landroid/widget/Button;->setVisibility(I)V

    .line 2137
    :cond_2
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getManual()I

    move-result v2

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateManual(I)V

    .line 2138
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getAutoDisFog()I

    move-result v2

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAutoDisfog(I)V

    .line 2140
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getBigAutoFlag()I

    move-result v2

    .line 2141
    .local v2, "bigAutoFlag":I
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getSmallAutoFlag()I

    move-result v3

    .line 2142
    .local v3, "smallAutoFlag":I
    const/4 v4, 0x1

    if-ne v2, v4, :cond_3

    .line 2143
    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAuto(I)V

    goto :goto_0

    .line 2144
    :cond_3
    if-ne v3, v4, :cond_4

    .line 2145
    invoke-direct {p0, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAuto(I)V

    goto :goto_0

    .line 2147
    :cond_4
    const/4 v1, 0x3

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAuto(I)V

    .line 2150
    :goto_0
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getWind_level()I

    move-result v1

    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getWindLevMax()I

    move-result v5

    invoke-virtual {p0, v1, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateSpeed(II)V

    .line 2151
    const v1, 0x7f0a00c1

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Button;

    .line 2152
    .local v1, "btn_on_off":Landroid/widget/Button;
    if-eqz v1, :cond_8

    .line 2153
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getPowerFlag()I

    move-result v5

    if-ne v5, v4, :cond_6

    .line 2154
    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v6, "p38"

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_5

    .line 2155
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f0801a4

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v1, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    goto :goto_1

    .line 2157
    :cond_5
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f0801a3

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v1, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    goto :goto_1

    .line 2160
    :cond_6
    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v6, "p38"

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_7

    .line 2161
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f0801a2

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v1, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    goto :goto_1

    .line 2163
    :cond_7
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f0801a1

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v1, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    .line 2168
    :cond_8
    :goto_1
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getWindMode()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateDirection(I)V

    .line 2169
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getWindModeRight()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateRightDirection(I)V

    .line 2171
    iget-boolean v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->curtAirTempMirror:Z

    if-eqz v5, :cond_9

    .line 2172
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRightTempNum()Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cutTheTempUnit(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateLeftTemp(Ljava/lang/String;)V

    .line 2173
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLeftTempNum()Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cutTheTempUnit(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRightTemp(Ljava/lang/String;)V

    goto :goto_2

    .line 2175
    :cond_9
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLeftTempNum()Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cutTheTempUnit(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateLeftTemp(Ljava/lang/String;)V

    .line 2176
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRightTempNum()Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->cutTheTempUnit(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRightTemp(Ljava/lang/String;)V

    .line 2179
    :goto_2
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getCycleMode()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateCycle(I)V

    .line 2181
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getFrontDisFog()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateMaxFront(I)V

    .line 2182
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRearDisFog()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRear(I)V

    .line 2183
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLeftChairHeat()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateLeftSeatHeat(I)V

    .line 2184
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRightChairHeat()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRightSeatHeat(I)V

    .line 2185
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getSteerChairHeat()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateSteerChairHeat(I)V

    .line 2187
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLeftChairWind()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateLeftSeatWind(I)V

    .line 2188
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRightChairWind()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRightSeatWind(I)V

    .line 2190
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLeftWaist()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateLeftWaist(I)V

    .line 2191
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRightWaist()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRightWaist(I)V

    .line 2192
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLeftMassage()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateLeftMassage(I)V

    .line 2193
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRightMassage()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRightMassage(I)V

    .line 2195
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getDual()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateDual(I)V

    .line 2196
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getIon()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->upDataIon(I)V

    .line 2197
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getZone()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->upDataZone(I)V

    .line 2198
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getHeatSteer()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSteerHeat(I)V

    .line 2199
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getSyncFlag()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSync(I)V

    .line 2200
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getSoft()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSoft(I)V

    .line 2201
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getNomal()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNomal(I)V

    .line 2202
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getFast()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateFast(I)V

    .line 2203
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getFollower()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateFollower(I)V

    .line 2204
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRest()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRest(I)V

    .line 2205
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getTemp()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateTemp(I)V

    .line 2207
    iget v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRearLock:I

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRearLock(I)V

    .line 2210
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getACMax()I

    move-result v5

    if-nez v5, :cond_a

    .line 2212
    const v5, 0x7f0a000e

    invoke-virtual {p0, v5, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->SetViewGone(IZ)V

    goto :goto_3

    .line 2214
    :cond_a
    const/4 v4, 0x2

    invoke-direct {p0, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateACandACMax(I)V

    .line 2217
    :goto_3
    const v4, 0x7f0a00ae

    invoke-direct {p0, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->findViewByIdCompat(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    .line 2218
    .local v4, "btn_ac":Landroid/widget/Button;
    if-eqz v4, :cond_e

    .line 2219
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getACFlag()I

    move-result v5

    if-nez v5, :cond_c

    .line 2220
    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v6, "p38"

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_b

    .line 2221
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f08019f

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    goto :goto_4

    .line 2223
    :cond_b
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f08019d

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    goto :goto_4

    .line 2226
    :cond_c
    iget-object v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string v6, "p38"

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_d

    .line 2227
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f08019e

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    goto :goto_4

    .line 2229
    :cond_d
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const v6, 0x7f08019c

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/widget/Button;->setBackgroundResource(I)V

    .line 2234
    :cond_e
    :goto_4
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getAutoMode()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAirOnProfile2(I)V

    .line 2235
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getEcon()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateECO(I)V

    .line 2236
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getAirQuality()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAQ(I)V

    .line 2237
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getMaxFront()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateForntRZC(I)V

    .line 2238
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getRear()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateRearRZC(I)V

    .line 2239
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getMaxFront()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateMaxIconFront(I)V

    .line 2240
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getAutoLevel()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateAutoLevel(I)V

    .line 2241
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getWindIntensity()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateWindIntensity(I)V

    .line 2242
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getLockRear()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateRearLock(I)V

    .line 2243
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getNanoe()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAirNanoe(I)V

    .line 2244
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getAutoSwing()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAirAutoSwing(I)V

    .line 2245
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getFilter()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAirFilter(I)V

    .line 2246
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getPTC()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAirPTC(I)V

    .line 2247
    invoke-virtual {p1}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getHeat()I

    move-result v5

    invoke-direct {p0, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->UpdateAirHeat(I)V

    .line 2248
    return-void
.end method

.method public updateAirBackground(I)V
    .locals 3
    .param p1, "theme"    # I

    .line 2007
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 2008
    return-void

    .line 2011
    :cond_0
    const/4 v0, 0x0

    .line 2012
    .local v0, "nResID":I
    packed-switch p1, :pswitch_data_0

    packed-switch p1, :pswitch_data_1

    packed-switch p1, :pswitch_data_2

    packed-switch p1, :pswitch_data_3

    packed-switch p1, :pswitch_data_4

    packed-switch p1, :pswitch_data_5

    sparse-switch p1, :sswitch_data_0

    goto/16 :goto_0

    .line 2105
    :sswitch_0
    const v0, 0x7f08010f

    .line 2106
    goto/16 :goto_0

    .line 2090
    :sswitch_1
    const v0, 0x7f0800fa

    .line 2091
    goto/16 :goto_0

    .line 2087
    :sswitch_2
    const v0, 0x7f080110

    .line 2088
    goto/16 :goto_0

    .line 2078
    :sswitch_3
    const v0, 0x7f08010d

    .line 2079
    goto/16 :goto_0

    .line 2075
    :sswitch_4
    const v0, 0x7f080108

    .line 2076
    goto/16 :goto_0

    .line 2057
    :sswitch_5
    const v0, 0x7f080100

    .line 2058
    goto/16 :goto_0

    .line 2102
    :pswitch_0
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    const v2, 0x7f080104

    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setBackgroundResource(I)V

    .line 2103
    goto/16 :goto_0

    .line 2099
    :pswitch_1
    const v0, 0x7f080104

    .line 2100
    goto/16 :goto_0

    .line 2096
    :pswitch_2
    const v0, 0x7f08010f

    .line 2097
    goto/16 :goto_0

    .line 2093
    :pswitch_3
    const v0, 0x7f08010f

    .line 2094
    goto :goto_0

    .line 2084
    :pswitch_4
    const v0, 0x7f08010f

    .line 2085
    goto :goto_0

    .line 2081
    :pswitch_5
    const v0, 0x7f08010e

    .line 2082
    goto :goto_0

    .line 2072
    :pswitch_6
    const v0, 0x7f0800fb

    .line 2073
    goto :goto_0

    .line 2069
    :pswitch_7
    const v0, 0x7f08010f

    .line 2070
    goto :goto_0

    .line 2066
    :pswitch_8
    const v0, 0x7f08010f

    .line 2067
    goto :goto_0

    .line 2063
    :pswitch_9
    const v0, 0x7f080103

    .line 2064
    goto :goto_0

    .line 2060
    :pswitch_a
    const v0, 0x7f080101

    .line 2061
    goto :goto_0

    .line 2054
    :pswitch_b
    const v0, 0x7f0800ff

    .line 2055
    goto :goto_0

    .line 2051
    :pswitch_c
    const v0, 0x7f0800fe

    .line 2052
    goto :goto_0

    .line 2048
    :pswitch_d
    const v0, 0x7f0800fd

    .line 2049
    goto :goto_0

    .line 2045
    :pswitch_e
    const v0, 0x7f0800fc

    .line 2046
    goto :goto_0

    .line 2042
    :pswitch_f
    const v0, 0x7f0800fb

    .line 2043
    goto :goto_0

    .line 2039
    :pswitch_10
    const v0, 0x7f0800fa

    .line 2040
    goto :goto_0

    .line 2036
    :pswitch_11
    const v0, 0x7f08010c

    .line 2037
    goto :goto_0

    .line 2033
    :pswitch_12
    const v0, 0x7f08010b

    .line 2034
    goto :goto_0

    .line 2030
    :pswitch_13
    const v0, 0x7f08010a

    .line 2031
    goto :goto_0

    .line 2027
    :pswitch_14
    const v0, 0x7f080109

    .line 2028
    goto :goto_0

    .line 2024
    :pswitch_15
    :sswitch_6
    const v0, 0x7f080106

    .line 2025
    goto :goto_0

    .line 2020
    :pswitch_16
    const v0, 0x7f080104

    .line 2021
    goto :goto_0

    .line 2017
    :pswitch_17
    const v0, 0x7f080102

    .line 2018
    goto :goto_0

    .line 2014
    :pswitch_18
    const v0, 0x7f0800f9

    .line 2015
    nop

    .line 2110
    :goto_0
    if-eqz v0, :cond_1

    .line 2111
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    invoke-virtual {v2, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 2113
    :cond_1
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_18
        :pswitch_17
        :pswitch_16
        :pswitch_15
        :pswitch_14
        :pswitch_13
        :pswitch_12
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x9
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0x13
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
    .end packed-switch

    :pswitch_data_3
    .packed-switch 0xa6
        :pswitch_5
        :pswitch_4
    .end packed-switch

    :pswitch_data_4
    .packed-switch 0x119
        :pswitch_3
        :pswitch_2
    .end packed-switch

    :pswitch_data_5
    .packed-switch 0x13f
        :pswitch_1
        :pswitch_0
    .end packed-switch

    :sswitch_data_0
    .sparse-switch
        0x11 -> :sswitch_5
        0x2d -> :sswitch_4
        0x33 -> :sswitch_3
        0x55 -> :sswitch_6
        0xc3 -> :sswitch_2
        0x117 -> :sswitch_1
        0x143 -> :sswitch_0
    .end sparse-switch
.end method

.method public updateAirBg(Landroid/graphics/drawable/Drawable;)V
    .locals 1
    .param p1, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 3439
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAirLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 3440
    return-void
.end method

.method public updateBottomBg(Ljava/lang/String;)V
    .locals 3
    .param p1, "uiStyle"    # Ljava/lang/String;

    .line 3486
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    if-eqz v0, :cond_7

    .line 3487
    const-string v0, "persist.sys.skin"

    const-string v1, "com.hcn.auto.theme.p1"

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    .line 3488
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/hcn/auto/systemui/InitViewImpl;->isNewBottomUI(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 3489
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0, p1}, Lcom/hcn/auto/systemui/InitViewImpl;->updateBottomBgUIStyle(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 3491
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    if-eqz v0, :cond_7

    .line 3492
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    .line 3493
    const-string v0, "night"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 3494
    const-string p1, "_night"

    goto :goto_0

    .line 3496
    :cond_1
    const-string p1, ""

    .line 3498
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    const-string/jumbo v1, "xt520"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_7

    .line 3499
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnAllApp()Landroid/widget/ImageButton;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 3500
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "_icon_all_apps_selector"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 3501
    .local v0, "resAllAppsName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 3502
    .local v1, "resAllAppsDraw":Landroid/graphics/drawable/Drawable;
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v2}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnAllApp()Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 3504
    .end local v0    # "resAllAppsName":Ljava/lang/String;
    .end local v1    # "resAllAppsDraw":Landroid/graphics/drawable/Drawable;
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnAllApp()Landroid/widget/ImageButton;

    move-result-object v0

    if-eqz v0, :cond_3

    .line 3505
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "_icon_navi_selector"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 3506
    .local v0, "resNaviName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 3507
    .local v1, "resNaviDraw":Landroid/graphics/drawable/Drawable;
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v2}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnNavi()Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 3509
    .end local v0    # "resNaviName":Ljava/lang/String;
    .end local v1    # "resNaviDraw":Landroid/graphics/drawable/Drawable;
    :cond_3
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnMusic()Landroid/widget/ImageButton;

    move-result-object v0

    if-eqz v0, :cond_4

    .line 3510
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "_icon_music_selector"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 3511
    .local v0, "resMusicName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 3512
    .local v1, "resMusicDraw":Landroid/graphics/drawable/Drawable;
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v2}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnMusic()Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 3514
    .end local v0    # "resMusicName":Ljava/lang/String;
    .end local v1    # "resMusicDraw":Landroid/graphics/drawable/Drawable;
    :cond_4
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnRadio()Landroid/widget/ImageButton;

    move-result-object v0

    if-eqz v0, :cond_5

    .line 3515
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->skinName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "_icon_radio_selector"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 3516
    .local v0, "resRadioName":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 3517
    .local v1, "resRadioDraw":Landroid/graphics/drawable/Drawable;
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v2}, Lcom/hcn/auto/systemui/InitViewImpl;->getBtnRadio()Landroid/widget/ImageButton;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/widget/ImageButton;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 3519
    .end local v0    # "resRadioName":Ljava/lang/String;
    .end local v1    # "resRadioDraw":Landroid/graphics/drawable/Drawable;
    :cond_5
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getTime()Landroid/widget/TextView;

    move-result-object v0

    if-eqz v0, :cond_7

    .line 3520
    const-string v0, "_night"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    .line 3521
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getTime()Landroid/widget/TextView;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f060198

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    goto :goto_1

    .line 3523
    :cond_6
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mInitView:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-virtual {v0}, Lcom/hcn/auto/systemui/InitViewImpl;->getTime()Landroid/widget/TextView;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f060197

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 3530
    :cond_7
    :goto_1
    return-void
.end method

.method public updateNavButtonIcons()V
    .locals 14

    .line 857
    iget v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    const/4 v1, 0x1

    and-int/2addr v0, v1

    const/4 v2, 0x0

    if-eqz v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    move v0, v2

    .line 859
    .local v0, "useAltBack":Z
    :goto_0
    if-eqz v0, :cond_1

    .line 860
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    iget-boolean v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    invoke-direct {p0, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackIconWithAlt(ZZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v3

    goto :goto_1

    .line 861
    :cond_1
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    iget-boolean v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mVertical:Z

    invoke-direct {p0, v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackIcon(ZZ)Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    move-result-object v3

    .line 862
    .local v3, "backIcon":Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    :goto_1
    iget-boolean v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    if-eqz v4, :cond_2

    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHomeCarModeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    goto :goto_2

    :cond_2
    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mHomeDefaultIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    .line 865
    .local v4, "homeIcon":Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;
    :goto_2
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v5

    invoke-static {v5}, Lcom/android/systemui/SystemUIApplication;->isPortraitWindowMode(Landroid/content/Context;)Z

    move-result v5

    if-nez v5, :cond_4

    .line 866
    iget-boolean v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    if-nez v5, :cond_3

    .line 867
    invoke-direct {p0, v3}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->orientBackButton(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 868
    invoke-direct {p0, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->orientHomeButton(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 871
    :cond_3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v5

    invoke-virtual {v5, v4}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 872
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v5

    invoke-virtual {v5, v3}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 874
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateRecentsIcon()V

    .line 878
    :cond_4
    iget-boolean v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowAccessibilityButton:Z

    if-nez v5, :cond_5

    iget-boolean v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowRotateButton:Z

    if-nez v5, :cond_5

    iget v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationIconHints:I

    and-int/lit8 v5, v5, 0x2

    if-eqz v5, :cond_5

    move v5, v1

    goto :goto_3

    :cond_5
    move v5, v2

    .line 882
    .local v5, "showImeButton":Z
    :goto_3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getImeSwitchButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v6

    const/4 v7, 0x4

    if-eqz v5, :cond_6

    move v8, v2

    goto :goto_4

    :cond_6
    move v8, v7

    :goto_4
    invoke-virtual {v6, v8}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 883
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getImeSwitchButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v6

    iget-object v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mImeIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    invoke-virtual {v6, v8}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 886
    iget-boolean v6, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowMenu:Z

    invoke-virtual {p0, v6, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setMenuVisibility(ZZ)V

    .line 887
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getMenuButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v6

    iget-object v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mMenuIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    invoke-virtual {v6, v8}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 890
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v6

    iget-object v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    invoke-virtual {v6, v8}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 893
    iget-boolean v6, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mShowAccessibilityButton:Z

    iget-boolean v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mLongClickableAccessibilityButton:Z

    invoke-virtual {p0, v6, v8}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setAccessibilityButtonState(ZZ)V

    .line 894
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getAccessibilityButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v6

    iget-object v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mAccessibilityIcon:Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;

    invoke-virtual {v6, v8}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 896
    iget-object v6, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mBarTransitions:Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;

    invoke-virtual {v6}, Lcom/android/systemui/statusbar/phone/NavigationBarTransitions;->reapplyDarkIntensity()V

    .line 898
    iget v6, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    const/high16 v8, 0x200000

    and-int/2addr v6, v8

    if-eqz v6, :cond_7

    move v6, v1

    goto :goto_5

    :cond_7
    move v6, v2

    .line 901
    .local v6, "disableHome":Z
    :goto_5
    iget-boolean v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mUseCarModeUi:Z

    if-nez v8, :cond_9

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isOverviewEnabled()Z

    move-result v8

    if-nez v8, :cond_8

    goto :goto_6

    :cond_8
    move v8, v2

    goto :goto_7

    :cond_9
    :goto_6
    move v8, v1

    .line 903
    .local v8, "disableRecent":Z
    :goto_7
    iget v9, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mDisabledFlags:I

    const/high16 v10, 0x400000

    and-int/2addr v9, v10

    if-eqz v9, :cond_a

    if-nez v0, :cond_a

    move v9, v1

    goto :goto_8

    :cond_a
    move v9, v2

    .line 908
    .local v9, "disableBack":Z
    :goto_8
    invoke-static {}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->getInstance()Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    move-result-object v10

    invoke-virtual {v10}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->isScreenPinningActive()Z

    move-result v10

    .line 909
    .local v10, "pinningActive":Z
    iget-object v11, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-virtual {v11}, Lcom/android/systemui/OverviewProxyService;->isEnabled()Z

    move-result v11

    if-eqz v11, :cond_d

    .line 912
    iget-object v11, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-virtual {v11}, Lcom/android/systemui/OverviewProxyService;->getInteractionFlags()I

    move-result v11

    .line 913
    .local v11, "flags":I
    and-int/lit8 v12, v11, 0x4

    if-nez v12, :cond_b

    goto :goto_9

    :cond_b
    move v1, v2

    :goto_9
    or-int/2addr v8, v1

    .line 914
    if-eqz v10, :cond_c

    .line 915
    move v1, v2

    .end local v6    # "disableHome":Z
    .local v1, "disableHome":Z
    move v6, v2

    .line 917
    .end local v9    # "disableBack":Z
    .end local v11    # "flags":I
    .local v6, "disableBack":Z
    move v9, v6

    move v6, v1

    .end local v1    # "disableHome":Z
    .local v6, "disableHome":Z
    .restart local v9    # "disableBack":Z
    :cond_c
    goto :goto_a

    :cond_d
    if-eqz v10, :cond_e

    .line 918
    move v8, v2

    move v9, v2

    .line 922
    :cond_e
    :goto_a
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v1

    if-eqz v1, :cond_f

    .line 923
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getCurrentView()Landroid/view/View;

    move-result-object v1

    const v11, 0x7f0a029c

    invoke-virtual {v1, v11}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/view/ViewGroup;

    .line 924
    .local v1, "navButtons":Landroid/view/ViewGroup;
    if-eqz v1, :cond_f

    .line 925
    invoke-virtual {v1}, Landroid/view/ViewGroup;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v11

    .line 926
    .local v11, "lt":Landroid/animation/LayoutTransition;
    if-eqz v11, :cond_f

    .line 927
    invoke-virtual {v11}, Landroid/animation/LayoutTransition;->getTransitionListeners()Ljava/util/List;

    move-result-object v12

    iget-object v13, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTransitionListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;

    invoke-interface {v12, v13}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v12

    if-nez v12, :cond_f

    .line 928
    iget-object v12, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mTransitionListener:Lcom/android/systemui/statusbar/phone/NavigationBarView$NavTransitionListener;

    invoke-virtual {v11, v12}, Landroid/animation/LayoutTransition;->addTransitionListener(Landroid/animation/LayoutTransition$TransitionListener;)V

    .line 934
    .end local v1    # "navButtons":Landroid/view/ViewGroup;
    .end local v11    # "lt":Landroid/animation/LayoutTransition;
    :cond_f
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getBackButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    if-eqz v9, :cond_10

    move v11, v7

    goto :goto_b

    :cond_10
    move v11, v2

    :goto_b
    invoke-virtual {v1, v11}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 935
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    if-eqz v6, :cond_11

    move v11, v7

    goto :goto_c

    :cond_11
    move v11, v2

    :goto_c
    invoke-virtual {v1, v11}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 936
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRecentsButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    if-eqz v8, :cond_12

    move v2, v7

    nop

    :cond_12
    invoke-virtual {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setVisibility(I)V

    .line 937
    return-void
.end method

.method public updateRotateSuggestionButtonStyle(IZ)V
    .locals 11
    .param p1, "style"    # I
    .param p2, "setIcon"    # Z

    .line 1080
    iput p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateBtnStyle:I

    .line 1081
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 1084
    .local v0, "ctx":Landroid/content/Context;
    const v1, 0x7f040104

    invoke-static {v0, v1}, Lcom/android/settingslib/Utils;->getThemeAttr(Landroid/content/Context;I)I

    move-result v1

    .line 1085
    .local v1, "dualToneDarkTheme":I
    const v2, 0x7f040260

    invoke-static {v0, v2}, Lcom/android/settingslib/Utils;->getThemeAttr(Landroid/content/Context;I)I

    move-result v2

    .line 1086
    .local v2, "dualToneLightTheme":I
    new-instance v3, Landroid/view/ContextThemeWrapper;

    invoke-direct {v3, v0, v1}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 1087
    .local v3, "darkContext":Landroid/content/Context;
    new-instance v4, Landroid/view/ContextThemeWrapper;

    invoke-direct {v4, v0, v2}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 1088
    .local v4, "lightContext":Landroid/content/Context;
    const v5, 0x7f04033d

    invoke-static {v4, v5}, Lcom/android/settingslib/Utils;->getColorAttr(Landroid/content/Context;I)I

    move-result v6

    .line 1089
    .local v6, "lightColor":I
    invoke-static {v3, v5}, Lcom/android/settingslib/Utils;->getColorAttr(Landroid/content/Context;I)I

    move-result v5

    .line 1092
    .local v5, "darkColor":I
    new-instance v7, Landroid/view/ContextThemeWrapper;

    invoke-direct {v7, v0, p1}, Landroid/view/ContextThemeWrapper;-><init>(Landroid/content/Context;I)V

    .line 1095
    .local v7, "rotateContext":Landroid/content/Context;
    iget-object v8, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    .line 1096
    .local v8, "priorIcon":Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;
    const v9, 0x7f080402

    invoke-direct {p0, v7, v9, v6, v5}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getDrawable(Landroid/content/Context;III)Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    move-result-object v9

    iput-object v9, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    .line 1100
    if-eqz v8, :cond_0

    invoke-virtual {v8}, Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;->isDarkIntensitySet()Z

    move-result v9

    if-eqz v9, :cond_0

    .line 1101
    iget-object v9, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    invoke-virtual {v8}, Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;->getDarkIntensity()F

    move-result v10

    invoke-virtual {v9, v10}, Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;->setDarkIntensity(F)V

    .line 1104
    :cond_0
    if-eqz p2, :cond_1

    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getRotateSuggestionButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v9

    iget-object v10, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mRotateSuggestionIcon:Lcom/android/systemui/statusbar/policy/TintedKeyButtonDrawable;

    invoke-virtual {v9, v10}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setImageDrawable(Lcom/android/systemui/statusbar/policy/KeyButtonDrawable;)V

    .line 1105
    :cond_1
    return-void
.end method

.method public updateStates()V
    .locals 3

    .line 1005
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mOverviewProxyService:Lcom/android/systemui/OverviewProxyService;

    invoke-virtual {v0}, Lcom/android/systemui/OverviewProxyService;->shouldShowSwipeUpUI()Z

    move-result v0

    .line 1007
    .local v0, "showSwipeUpUI":Z
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

    if-eqz v1, :cond_0

    .line 1009
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mNavigationInflaterView:Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarInflaterView;->onLikelyDefaultLayoutChange()V

    .line 1012
    :cond_0
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateSlippery()V

    .line 1013
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->reloadNavIcons()V

    .line 1014
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->updateNavButtonIcons()V

    .line 1015
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->isQuickStepSwipeUpEnabled()Z

    move-result v1

    invoke-direct {p0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->setUpSwipeUpOnboarding(Z)V

    .line 1016
    invoke-static {}, Lcom/android/systemui/shared/system/WindowManagerWrapper;->getInstance()Lcom/android/systemui/shared/system/WindowManagerWrapper;

    move-result-object v1

    xor-int/lit8 v2, v0, 0x1

    invoke-virtual {v1, v2}, Lcom/android/systemui/shared/system/WindowManagerWrapper;->setNavBarVirtualKeyHapticFeedbackEnabled(Z)V

    .line 1017
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/NavigationBarView;->getHomeButton()Lcom/android/systemui/statusbar/phone/ButtonDispatcher;

    move-result-object v1

    .line 1018
    if-eqz v0, :cond_1

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarView;->mQuickStepAccessibilityDelegate:Landroid/view/View$AccessibilityDelegate;

    goto :goto_0

    :cond_1
    const/4 v2, 0x0

    .line 1017
    :goto_0
    invoke-virtual {v1, v2}, Lcom/android/systemui/statusbar/phone/ButtonDispatcher;->setAccessibilityDelegate(Landroid/view/View$AccessibilityDelegate;)V

    .line 1019
    return-void
.end method
