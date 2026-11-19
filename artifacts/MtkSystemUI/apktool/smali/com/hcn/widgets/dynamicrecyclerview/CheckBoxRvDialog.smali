.class public Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;
.super Ljava/lang/Object;
.source "CheckBoxRvDialog.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation build Landroid/annotation/SuppressLint;
    value = {
        "NewApi"
    }
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String;


# instance fields
.field private defaultData:[Ljava/lang/String;

.field private mCheckIcon:Landroid/graphics/drawable/Drawable;

.field private mColumnCount:I

.field public mConfigMap:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mContext:Landroid/content/Context;

.field private mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconFilter;

.field private mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;

.field private mDialog:Landroid/app/AlertDialog;

.field private mDrawableSize:I

.field private mIconGravity:I

.field private mItemHeight:I

.field private mItemWidth:I

.field private mLLayout:Landroid/widget/LinearLayout;

.field private mOnConfirmClickListener:Landroid/view/View$OnClickListener;

.field private mOnItemClickListener:Landroid/view/View$OnClickListener;

.field private mSelectAdapter:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

.field private mSelectedApps:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;"
        }
    .end annotation
.end field

.field private mSelectedRv:Landroidx/recyclerview/widget/RecyclerView;

.field private mSelectedSaveApps:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mSettingKey:Ljava/lang/String;

.field private mSettingKey0:Ljava/lang/String;

.field private mSpFileName:Ljava/lang/String;

.field private mTextColor:I

.field private mTextMarginTop:I

.field private mTextOverflow:I

.field private mTextSize:I

.field private mUnselectAdapter:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

.field private mUnselectedApps:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;"
        }
    .end annotation
.end field

.field private mWindowHeight:I

.field private mWindowWidth:I

.field private spUtils:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 62
    const-class v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-virtual {v0}, Ljava/lang/Class;->getCanonicalName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    return-void
.end method

.method static synthetic access$000()Ljava/lang/String;
    .locals 1

    .line 61
    sget-object v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mUnselectedApps:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$1002(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;
    .locals 0
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;
    .param p1, "x1"    # Ljava/util/ArrayList;

    .line 61
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mUnselectedApps:Ljava/util/ArrayList;

    return-object p1
.end method

.method static synthetic access$1100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectAdapter:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    return-object v0
.end method

.method static synthetic access$1200(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mUnselectAdapter:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    return-object v0
.end method

.method static synthetic access$1300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mItemWidth:I

    return v0
.end method

.method static synthetic access$1400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mItemHeight:I

    return v0
.end method

.method static synthetic access$1500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDrawableSize:I

    return v0
.end method

.method static synthetic access$1600(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/graphics/drawable/Drawable;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mCheckIcon:Landroid/graphics/drawable/Drawable;

    return-object v0
.end method

.method static synthetic access$1700(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mIconGravity:I

    return v0
.end method

.method static synthetic access$1800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mTextSize:I

    return v0
.end method

.method static synthetic access$1900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mTextColor:I

    return v0
.end method

.method static synthetic access$2000(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mTextMarginTop:I

    return v0
.end method

.method static synthetic access$2100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mTextOverflow:I

    return v0
.end method

.method static synthetic access$2300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroidx/recyclerview/widget/RecyclerView;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedRv:Landroidx/recyclerview/widget/RecyclerView;

    return-object v0
.end method

.method static synthetic access$2400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/view/View$OnClickListener;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mOnItemClickListener:Landroid/view/View$OnClickListener;

    return-object v0
.end method

.method static synthetic access$400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedSaveApps:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$502(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;
    .locals 0
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;
    .param p1, "x1"    # Ljava/util/ArrayList;

    .line 61
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedSaveApps:Ljava/util/ArrayList;

    return-object p1
.end method

.method static synthetic access$600(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSpFileName:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$700(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->spUtils:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;

    return-object v0
.end method

.method static synthetic access$800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)[Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->defaultData:[Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 61
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedApps:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$902(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;
    .locals 0
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;
    .param p1, "x1"    # Ljava/util/ArrayList;

    .line 61
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedApps:Ljava/util/ArrayList;

    return-object p1
.end method

.method private isSameApp(Ljava/lang/String;Landroid/content/ComponentName;)Z
    .locals 5
    .param p1, "str"    # Ljava/lang/String;
    .param p2, "componentName"    # Landroid/content/ComponentName;

    .line 506
    const/4 v0, 0x0

    .line 507
    .local v0, "isSame":Z
    const-string v1, "/"

    invoke-virtual {p1, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v1

    .line 508
    .local v1, "array":[Ljava/lang/String;
    array-length v2, v1

    const/4 v3, 0x2

    if-ne v2, v3, :cond_1

    .line 509
    const/4 v2, 0x0

    aget-object v3, v1, v2

    if-eqz v3, :cond_1

    aget-object v3, v1, v2

    const-string v4, ""

    if-eq v3, v4, :cond_1

    if-eqz p2, :cond_1

    .line 511
    aget-object v2, v1, v2

    invoke-virtual {p2}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    const/4 v2, 0x1

    aget-object v3, v1, v2

    .line 512
    invoke-virtual {p2}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_0

    aget-object v3, v1, v2

    invoke-virtual {p2}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_0

    .line 513
    invoke-virtual {p2}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v3

    aget-object v2, v1, v2

    invoke-virtual {v3, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 514
    :cond_0
    const/4 v0, 0x1

    .line 518
    :cond_1
    return v0
.end method

.method public static synthetic lambda$saveShortNameList$0(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;Ljava/lang/StringBuilder;Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .param p1, "appIconData"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    .param p2, "shortNameList"    # Ljava/lang/StringBuilder;
    .param p3, "key"    # Ljava/lang/String;
    .param p4, "value"    # Ljava/lang/String;

    .line 486
    sget-object v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "key = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ", value = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 487
    invoke-virtual {p1}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    invoke-direct {p0, p4, v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->isSameApp(Ljava/lang/String;Landroid/content/ComponentName;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 488
    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 490
    :cond_0
    return-void
.end method

.method private processData()V
    .locals 2

    .line 523
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mLLayout:Landroid/widget/LinearLayout;

    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;

    invoke-direct {v1, p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)V

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->post(Ljava/lang/Runnable;)Z

    .line 566
    return-void
.end method

.method private saveShortNameList(Ljava/util/List;)V
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;)V"
        }
    .end annotation

    .line 483
    .local p1, "appIconDataList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 484
    .local v0, "shortNameList":Ljava/lang/StringBuilder;
    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 485
    .local v2, "appIconData":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mConfigMap:Ljava/util/Map;

    new-instance v4, Lcom/hcn/widgets/dynamicrecyclerview/-$$Lambda$CheckBoxRvDialog$7xHqjQPILKS9GfcCew_LVApHWWA;

    invoke-direct {v4, p0, v2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/-$$Lambda$CheckBoxRvDialog$7xHqjQPILKS9GfcCew_LVApHWWA;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;Ljava/lang/StringBuilder;)V

    invoke-interface {v3, v4}, Ljava/util/Map;->forEach(Ljava/util/function/BiConsumer;)V

    .line 491
    .end local v2    # "appIconData":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    goto :goto_0

    .line 492
    :cond_0
    sget-object v1, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSettingKey:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 493
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSettingKey:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Landroid/provider/Settings$System;->putString(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;)Z

    .line 494
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSettingKey0:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->setSystemProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 495
    return-void
.end method


# virtual methods
.method public accept(Landroid/content/pm/LauncherActivityInfo;)Z
    .locals 3
    .param p1, "info"    # Landroid/content/pm/LauncherActivityInfo;

    .line 603
    const/4 v0, 0x0

    if-nez p1, :cond_0

    .line 604
    return v0

    .line 606
    :cond_0
    const/4 v1, 0x1

    .line 607
    .local v1, "result":Z
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconFilter;

    if-eqz v2, :cond_2

    .line 608
    if-eqz v1, :cond_1

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconFilter;

    invoke-interface {v2, p1}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconFilter;->accept(Landroid/content/pm/LauncherActivityInfo;)Z

    move-result v2

    if-eqz v2, :cond_1

    const/4 v0, 0x1

    nop

    :cond_1
    move v1, v0

    .line 610
    :cond_2
    return v1
.end method

.method public getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;
    .locals 1
    .param p1, "info"    # Landroid/content/pm/LauncherActivityInfo;

    .line 585
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;

    if-nez v0, :cond_0

    .line 586
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$4;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$4;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;

    .line 593
    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;

    invoke-interface {v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;->loadIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method

.method public hideDialog()V
    .locals 1

    .line 575
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->hide()V

    .line 576
    return-void
.end method

.method public mapToComponentString(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p1, "key"    # Ljava/lang/String;

    .line 168
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mConfigMap:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method

.method public onClick(Landroid/view/View;)V
    .locals 2
    .param p1, "v"    # Landroid/view/View;

    .line 449
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    .line 450
    .local v0, "id":I
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->btn_confirm:I

    if-ne v0, v1, :cond_1

    .line 451
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedApps:Ljava/util/ArrayList;

    invoke-virtual {p0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->saveSelectedApps(Ljava/util/ArrayList;)V

    .line 452
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mOnConfirmClickListener:Landroid/view/View$OnClickListener;

    if-eqz v1, :cond_0

    .line 453
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mOnConfirmClickListener:Landroid/view/View$OnClickListener;

    invoke-interface {v1, p1}, Landroid/view/View$OnClickListener;->onClick(Landroid/view/View;)V

    .line 455
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->hideDialog()V

    goto :goto_0

    .line 456
    :cond_1
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->btn_cancel:I

    if-ne v0, v1, :cond_2

    .line 457
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->hideDialog()V

    .line 459
    :cond_2
    :goto_0
    return-void
.end method

.method public saveSelectedApps(Ljava/util/ArrayList;)V
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;)V"
        }
    .end annotation

    .line 462
    .local p1, "appList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedSaveApps:Ljava/util/ArrayList;

    .line 463
    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 464
    .local v1, "info":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    if-eqz v2, :cond_0

    .line 465
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedSaveApps:Ljava/util/ArrayList;

    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/ComponentName;->flattenToString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 467
    .end local v1    # "info":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    :cond_0
    goto :goto_0

    .line 468
    :cond_1
    new-instance v0, Lcom/google/gson/Gson;

    invoke-direct {v0}, Lcom/google/gson/Gson;-><init>()V

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedSaveApps:Ljava/util/ArrayList;

    invoke-virtual {v0, v1}, Lcom/google/gson/Gson;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 469
    .local v0, "apps":Ljava/lang/String;
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->spUtils:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSpFileName:Ljava/lang/String;

    invoke-virtual {v1, v2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;->put(Ljava/lang/String;Ljava/lang/String;)V

    .line 470
    invoke-direct {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->saveShortNameList(Ljava/util/List;)V

    .line 471
    sget-object v1, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    const-string v2, "saveSelectedApps success"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 472
    sget-object v1, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSpFileName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " size : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedSaveApps:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 473
    sget-object v1, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSpFileName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 474
    return-void
.end method

.method public showDialog()V
    .locals 3

    .line 569
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->processData()V

    .line 570
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 571
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mWindowWidth:I

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mWindowHeight:I

    invoke-virtual {v0, v1, v2}, Landroid/view/Window;->setLayout(II)V

    .line 572
    return-void
.end method

.method public updateSelectRVLayoutParams()V
    .locals 3

    .line 431
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedRv:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout$LayoutParams;

    .line 432
    .local v0, "lp":Landroid/widget/LinearLayout$LayoutParams;
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedApps:Ljava/util/ArrayList;

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedApps:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-gtz v1, :cond_0

    goto :goto_0

    .line 438
    :cond_0
    iget v1, v0, Landroid/widget/LinearLayout$LayoutParams;->height:I

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mItemHeight:I

    if-ne v1, v2, :cond_1

    .line 439
    return-void

    .line 441
    :cond_1
    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mItemHeight:I

    iput v1, v0, Landroid/widget/LinearLayout$LayoutParams;->height:I

    goto :goto_1

    .line 433
    :cond_2
    :goto_0
    iget v1, v0, Landroid/widget/LinearLayout$LayoutParams;->height:I

    if-nez v1, :cond_3

    .line 434
    return-void

    .line 436
    :cond_3
    const/4 v1, 0x0

    iput v1, v0, Landroid/widget/LinearLayout$LayoutParams;->height:I

    .line 443
    :goto_1
    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mItemWidth:I

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mColumnCount:I

    mul-int/2addr v1, v2

    iput v1, v0, Landroid/widget/LinearLayout$LayoutParams;->width:I

    .line 444
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mSelectedRv:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v1, v0}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 445
    return-void
.end method
