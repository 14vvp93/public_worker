.class public Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;
.super Landroid/widget/FrameLayout;
.source "DynamicAppRecyclerView.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;,
        Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;,
        Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;,
        Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;,
        Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;
    }
.end annotation


# instance fields
.field public configFile:Ljava/io/File;

.field initHandler:Landroid/os/Handler;

.field private mAddIcon:Landroid/graphics/drawable/Drawable;

.field private mAlertDialog:Landroid/app/AlertDialog;

.field private mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

.field private mAppIconDataList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;"
        }
    .end annotation
.end field

.field private mClickListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;

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

.field private mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;

.field private mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;

.field private mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

.field private mFilterSet:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Landroid/content/ComponentName;",
            ">;"
        }
    .end annotation
.end field

.field private mLongClickListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;

.field private mOnDialogStatusListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

.field private mOrientation:I

.field private mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

.field private mRecyclerViewItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field mRecyclerViewItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

.field private mSettingKey0:Ljava/lang/String;

.field public mSpFile:Ljava/io/File;

.field private mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

.field private save_file_name:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;

    .line 169
    const/4 v0, 0x0

    const/4 v1, 0x0

    invoke-direct {p0, p1, v1, v0, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    .line 170
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 173
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    .line 174
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyleAttr"    # I

    .line 177
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    .line 178
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyleAttr"    # I
    .param p4, "defStyleRes"    # I

    .line 182
    invoke-direct {p0, p1, p2, p3, p4}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;II)V

    .line 69
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mColumnCount:I

    .line 77
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    .line 78
    new-instance v0, Landroid/util/ArraySet;

    invoke-direct {v0}, Landroid/util/ArraySet;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mFilterSet:Ljava/util/Set;

    .line 190
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$1;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initHandler:Landroid/os/Handler;

    .line 800
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$3;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerViewItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 823
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$4;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerViewItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

    .line 183
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    .line 184
    new-instance v0, Landroid/util/ArraySet;

    invoke-direct {v0}, Landroid/util/ArraySet;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mFilterSet:Ljava/util/Set;

    .line 186
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    .line 187
    new-instance v0, Landroidx/recyclerview/widget/RecyclerView;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroidx/recyclerview/widget/RecyclerView;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    .line 188
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Z
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initConfigMap()Z

    move-result v0

    return v0
.end method

.method static synthetic access$100(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->save_file_name:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Landroidx/recyclerview/widget/RecyclerView;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    return-object v0
.end method

.method static synthetic access$1100(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOnDialogStatusListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    return-object v0
.end method

.method static synthetic access$1200(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    return-object v0
.end method

.method static synthetic access$200(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Ljava/lang/String;)V
    .locals 0
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;
    .param p1, "x1"    # Ljava/lang/String;

    .line 63
    invoke-direct {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initData(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$300(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Ljava/util/List;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    return-object v0
.end method

.method static synthetic access$400(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V
    .locals 0
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->putAddItemListTail()V

    return-void
.end method

.method static synthetic access$500(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mClickListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemClickListener;

    return-object v0
.end method

.method static synthetic access$600(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V
    .locals 0
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->handleAddItemClick()V

    return-void
.end method

.method static synthetic access$700(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$800(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mLongClickListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnAppIconItemLongClickListener;

    return-object v0
.end method

.method static synthetic access$900(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    return-object v0
.end method

.method private addAppIconData(ILcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    .locals 2
    .param p1, "index"    # I
    .param p2, "data"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 788
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0, p1, p2}, Ljava/util/List;->add(ILjava/lang/Object;)V

    .line 789
    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 790
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mFilterSet:Ljava/util/Set;

    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 792
    :cond_0
    return-void
.end method

.method private addAppIconData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    .locals 2
    .param p1, "data"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 784
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x0

    invoke-static {v0, v1}, Ljava/lang/Math;->max(II)I

    move-result v0

    invoke-direct {p0, v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->addAppIconData(ILcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 785
    return-void
.end method

.method private handleAddItemClick()V
    .locals 9

    .line 845
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->getInstalledApps(Landroid/content/Context;Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v0

    .line 846
    .local v0, "appInfoList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/content/pm/LauncherActivityInfo;>;"
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    const/4 v2, 0x0

    if-nez v1, :cond_3

    .line 847
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 848
    .local v1, "dialogDataList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/content/pm/LauncherActivityInfo;>;"
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    .line 849
    .local v3, "dialogLabelList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/CharSequence;>;"
    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v4

    .line 851
    .local v4, "var4":Ljava/util/Iterator;
    :goto_0
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_1

    .line 852
    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Landroid/content/pm/LauncherActivityInfo;

    .line 853
    .local v5, "info":Landroid/content/pm/LauncherActivityInfo;
    invoke-virtual {p0, v5}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->accept(Landroid/content/pm/LauncherActivityInfo;)Z

    move-result v6

    if-eqz v6, :cond_0

    .line 854
    invoke-virtual {v1, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 855
    invoke-virtual {v5}, Landroid/content/pm/LauncherActivityInfo;->getLabel()Ljava/lang/CharSequence;

    move-result-object v6

    invoke-virtual {v3, v6}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 857
    .end local v5    # "info":Landroid/content/pm/LauncherActivityInfo;
    :cond_0
    goto :goto_0

    .line 859
    :cond_1
    invoke-virtual {v1}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v5

    if-eqz v5, :cond_2

    .line 860
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    sget v6, Lcom/hcn/widgets/dynamicrecyclerview/R$string;->no_more_app:I

    invoke-static {v5, v6, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_1

    .line 862
    :cond_2
    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v2

    new-array v2, v2, [Ljava/lang/CharSequence;

    .line 863
    .local v2, "items":[Ljava/lang/CharSequence;
    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/CharSequence;

    invoke-direct {p0, v1, v5}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->showDialogOnlyText(Ljava/util/List;[Ljava/lang/CharSequence;)V

    .line 865
    .end local v1    # "dialogDataList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/content/pm/LauncherActivityInfo;>;"
    .end local v2    # "items":[Ljava/lang/CharSequence;
    .end local v3    # "dialogLabelList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/CharSequence;>;"
    .end local v4    # "var4":Ljava/util/Iterator;
    :goto_1
    goto :goto_3

    .line 866
    :cond_3
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 868
    .local v1, "dialogList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :goto_2
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_5

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/pm/LauncherActivityInfo;

    .line 869
    .local v4, "info":Landroid/content/pm/LauncherActivityInfo;
    invoke-virtual {p0, v4}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->accept(Landroid/content/pm/LauncherActivityInfo;)Z

    move-result v5

    if-eqz v5, :cond_4

    .line 870
    new-instance v5, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v4}, Landroid/content/pm/LauncherActivityInfo;->getLabel()Ljava/lang/CharSequence;

    move-result-object v6

    invoke-interface {v6}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v6

    .line 871
    invoke-virtual {v4}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v7

    invoke-virtual {p0, v4}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-direct {v5, v6, v7, v8}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;-><init>(Ljava/lang/String;Landroid/content/ComponentName;Landroid/graphics/drawable/Drawable;)V

    .line 872
    .local v5, "tmpInfo":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    invoke-virtual {v1, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 874
    .end local v4    # "info":Landroid/content/pm/LauncherActivityInfo;
    .end local v5    # "tmpInfo":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    :cond_4
    goto :goto_2

    .line 875
    :cond_5
    invoke-virtual {v1}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_6

    .line 876
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    sget v4, Lcom/hcn/widgets/dynamicrecyclerview/R$string;->no_more_app:I

    invoke-static {v3, v4, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_3

    .line 878
    :cond_6
    invoke-virtual {p0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->showDialogWithIcon(Ljava/util/List;)V

    .line 881
    .end local v1    # "dialogList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    :goto_3
    return-void
.end method

.method private initConfigMap()Z
    .locals 9

    .line 369
    const/4 v0, 0x1

    .line 370
    .local v0, "initSuccess":Z
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->isDynamicAppListXmlExit()Z

    move-result v1

    if-eqz v1, :cond_3

    .line 371
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    if-nez v1, :cond_4

    .line 372
    new-instance v1, Landroid/util/ArrayMap;

    invoke-direct {v1}, Landroid/util/ArrayMap;-><init>()V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    .line 373
    const/4 v1, 0x0

    .line 374
    .local v1, "is":Ljava/io/InputStream;
    const/4 v2, 0x0

    .line 377
    .local v2, "xmldoc":Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;
    :try_start_0
    const-string v3, "DynamicAppRecyclerView"

    const-string v4, "Use extra config file:apd/dynamic_app_list.xml"

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 378
    const-string v3, "DynamicAppRecyclerView"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "mConfigMap==null:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    if-nez v5, :cond_0

    const/4 v5, 0x1

    goto :goto_0

    :cond_0
    const/4 v5, 0x0

    :goto_0
    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 379
    new-instance v3, Ljava/io/FileInputStream;

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->configFile:Ljava/io/File;

    invoke-direct {v3, v4}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    move-object v1, v3

    .line 382
    :try_start_1
    new-instance v3, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;

    invoke-direct {v3, v1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;-><init>(Ljava/io/InputStream;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_1

    move-object v2, v3

    .line 387
    goto :goto_1

    .line 383
    :catch_0
    move-exception v3

    .line 384
    .local v3, "e":Ljava/lang/Exception;
    :try_start_2
    const-string v4, "DynamicAppRecyclerView"

    const-string v5, "parse the xmlfile is fail"

    invoke-static {v4, v5}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 385
    const/4 v0, 0x0

    .line 386
    invoke-virtual {v3}, Ljava/lang/Exception;->printStackTrace()V
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_1

    .line 392
    .end local v3    # "e":Ljava/lang/Exception;
    :goto_1
    goto :goto_2

    .line 388
    :catch_1
    move-exception v3

    .line 389
    .local v3, "e":Ljava/io/IOException;
    invoke-virtual {v3}, Ljava/io/IOException;->printStackTrace()V

    .line 390
    const-string v4, "DynamicAppRecyclerView"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "read the xmlfile is fail"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 391
    const/4 v0, 0x0

    .line 394
    .end local v3    # "e":Ljava/io/IOException;
    :goto_2
    if-eqz v2, :cond_1

    .line 395
    iget-object v3, v2, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->appItemList:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :goto_3
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;

    .line 397
    .local v4, "appItem":Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    iget-object v6, v4, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;->shortName:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v8, v4, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;->packageName:Ljava/lang/String;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v8, "/"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v8, v4, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;->className:Ljava/lang/String;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-interface {v5, v6, v7}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 398
    .end local v4    # "appItem":Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;
    goto :goto_3

    .line 400
    :cond_1
    if-eqz v1, :cond_2

    .line 402
    :try_start_3
    invoke-virtual {v1}, Ljava/io/InputStream;->close()V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_2

    .line 405
    goto :goto_4

    .line 403
    :catch_2
    move-exception v3

    .line 404
    .restart local v3    # "e":Ljava/io/IOException;
    invoke-virtual {v3}, Ljava/io/IOException;->printStackTrace()V

    .line 407
    .end local v1    # "is":Ljava/io/InputStream;
    .end local v2    # "xmldoc":Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;
    .end local v3    # "e":Ljava/io/IOException;
    :cond_2
    :goto_4
    goto/16 :goto_5

    .line 410
    :cond_3
    const-string v1, "DynamicAppRecyclerView"

    const-string v2, "configFile.exists()"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 411
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    if-nez v1, :cond_4

    .line 412
    new-instance v1, Landroid/util/ArrayMap;

    invoke-direct {v1}, Landroid/util/ArrayMap;-><init>()V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    .line 413
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "music"

    const-string v3, "com.hcn.AutoMediaPlayer/com.hcn.MediaActivity.MusicPlayerUiActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 415
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string/jumbo v2, "video"

    const-string v3, "com.hcn.AutoMediaPlayer/com.hcn.MediaActivity.VideoPlayerUiActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 417
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "radio"

    const-string v3, "com.hcn.autoradio/.RadioMain"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 418
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "bluetooth"

    const-string v3, "com.autochips.bluetooth/.MainBluetoothActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 419
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "btmusic"

    const-string v3, "com.autochips.bluetooth/.BtMusicActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 420
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "chrome"

    const-string v3, "com.android.chrome/com.google.android.apps.chrome.Main"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 421
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "googlemap"

    const-string v3, "com.google.android.apps.maps/com.google.android.maps.MapsActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 423
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "setting"

    const-string v3, "com.android.settings/.Settings"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 424
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "playstore"

    const-string v3, "com.android.vending/.AssetBrowserActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 425
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string/jumbo v2, "youtube"

    const-string v3, "com.google.android.youtube/.app.honeycomb.Shell$HomeActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 427
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "filemanager"

    const-string v3, "com.mediatek.filemanager/.FileManagerOperationActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 428
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "autosetting"

    const-string v3, "com.hcn.autosetting/.ui.SettingActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 429
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "avin"

    const-string v3, "com.auto.hcamera/.VideoInActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 430
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "eq"

    const-string v3, "com.hcn.autoeq/.EqUIMain"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 431
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "heq"

    const-string v3, "com.hcn.autoeq/.MainActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 432
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "image"

    const-string v3, "com.hcn.gallery/.ui.ImageScanActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 433
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "carinfo"

    const-string v3, "com.hcn.autocan/.activity.CanActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 434
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "blind"

    const-string v3, "com.auto.hcamera/.HCameraActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 435
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "guide"

    const-string v3, "com.hcn.autouserguide/.UserGuideActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 436
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "spotify"

    const-string v3, "com.spotify.music/.libs.loginflow.LoginActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 437
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "browser"

    const-string v3, "com.android.browser/.BrowserActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 438
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "ov_easyconn"

    const-string v3, "net.easyconn/.ui.MainActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 439
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "easyconn"

    const-string v3, "net.easyconn/.ui.StandMainActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 440
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string/jumbo v2, "zlink_carplay"

    const-string v3, "com.zjinnova.zlink/.main.view.SplashActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 441
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "kwmusic"

    const-string v3, "cn.kuwo.kwmusiccar/.WelcomeActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 442
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "kwtingshu"

    const-string v3, "cn.kuwo.tingshucar/.WelcomeActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 443
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string v2, "kaolafm"

    const-string v3, "com.edog.car/com.kaolafm.auto.home.HubActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 444
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string/jumbo v2, "waze"

    const-string v3, "com.waze/.FreeMapAppActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 445
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string/jumbo v2, "yandexgo"

    const-string v3, "ru.yandex.taxi/ru.yandex.taxi.activity.MainActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 446
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    const-string/jumbo v2, "yandex"

    const-string v3, "ru.yandex.yandexnavi/ru.yandex.yandexnavi.core.NavigatorActivity"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 450
    :cond_4
    :goto_5
    return v0
.end method

.method private initData(Ljava/lang/String;)V
    .locals 7
    .param p1, "save_file_name"    # Ljava/lang/String;

    .line 330
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getDataDir()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "/shared_prefs/"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 331
    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ".xml"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpFile:Ljava/io/File;

    .line 332
    const/4 v0, 0x0

    .line 333
    .local v0, "defaultData":[Ljava/lang/String;
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpFile:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_1

    .line 335
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    invoke-static {v1, p1}, Landroid/provider/Settings$System;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 336
    .local v1, "str":Ljava/lang/String;
    const-string v2, "DynamicAppRecyclerView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "str:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 337
    if-eqz v1, :cond_0

    .line 338
    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    .line 339
    const-string v2, "DynamicAppRecyclerView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "str:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, " -- defaultData.length: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    array-length v4, v0

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 341
    :cond_0
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSettingKey0:Ljava/lang/String;

    const/4 v3, 0x0

    invoke-static {v2, v3}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 342
    if-eqz v1, :cond_1

    .line 343
    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    .line 344
    const-string v2, "DynamicAppRecyclerView"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "str:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v4, " -- defaultData.length: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    array-length v4, v0

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 348
    .end local v1    # "str":Ljava/lang/String;
    :cond_1
    :goto_0
    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    invoke-direct {v1, v2, p1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    .line 350
    if-eqz v0, :cond_4

    array-length v1, v0

    if-lez v1, :cond_4

    .line 351
    const/4 v1, 0x0

    .line 353
    .local v1, "bApply":Z
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_1
    array-length v3, v0

    if-ge v2, v3, :cond_3

    .line 354
    aget-object v3, v0, v2

    invoke-virtual {p0, v3}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mapToComponentString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 355
    .local v3, "value":Ljava/lang/String;
    const-string v4, "DynamicAppRecyclerView"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "defaultData[i]:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget-object v6, v0, v2

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, "--value:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 356
    invoke-static {v3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_2

    .line 357
    const-string v4, "DynamicAppRecyclerView"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "i:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v6, "---value:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 358
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5, v3}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->put(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/SharedPreferences$Editor;

    .line 359
    const/4 v1, 0x1

    .line 353
    .end local v3    # "value":Ljava/lang/String;
    :cond_2
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 362
    .end local v2    # "i":I
    :cond_3
    if-eqz v1, :cond_4

    .line 363
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-virtual {v2}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->apply()V

    .line 366
    .end local v1    # "bApply":Z
    :cond_4
    return-void
.end method

.method private isSameApp(Ljava/lang/String;Landroid/content/ComponentName;)Z
    .locals 5
    .param p1, "str"    # Ljava/lang/String;
    .param p2, "componentName"    # Landroid/content/ComponentName;

    .line 583
    const/4 v0, 0x0

    .line 584
    .local v0, "isSame":Z
    const-string v1, "/"

    invoke-virtual {p1, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v1

    .line 585
    .local v1, "array":[Ljava/lang/String;
    array-length v2, v1

    const/4 v3, 0x2

    if-ne v2, v3, :cond_1

    .line 586
    const/4 v2, 0x0

    aget-object v3, v1, v2

    if-eqz v3, :cond_1

    aget-object v3, v1, v2

    const-string v4, ""

    if-eq v3, v4, :cond_1

    if-eqz p2, :cond_1

    .line 588
    aget-object v2, v1, v2

    invoke-virtual {p2}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    const/4 v2, 0x1

    aget-object v3, v1, v2

    .line 589
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

    .line 590
    invoke-virtual {p2}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v3

    aget-object v2, v1, v2

    invoke-virtual {v3, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 591
    :cond_0
    const/4 v0, 0x1

    .line 595
    :cond_1
    return v0
.end method

.method public static synthetic lambda$saveShortNameList$0(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;Ljava/lang/StringBuilder;Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .param p1, "appIconData"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    .param p2, "shortNameList"    # Ljava/lang/StringBuilder;
    .param p3, "key"    # Ljava/lang/String;
    .param p4, "value"    # Ljava/lang/String;

    .line 562
    const-string v0, "DynamicAppRecyclerView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "key:value:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "---"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 563
    invoke-virtual {p1}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    invoke-direct {p0, p4, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->isSameApp(Ljava/lang/String;Landroid/content/ComponentName;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 564
    const-string v0, "DynamicAppRecyclerView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getComponentName::"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 565
    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 567
    :cond_0
    return-void
.end method

.method private putAddItemListTail()V
    .locals 4

    .line 771
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->containsAddItem()Z

    move-result v0

    if-nez v0, :cond_0

    .line 772
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    const-string v1, ""

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAddIcon:Landroid/graphics/drawable/Drawable;

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;-><init>(Ljava/lang/String;Landroid/content/ComponentName;Landroid/graphics/drawable/Drawable;)V

    .line 774
    .local v0, "addIcon":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    invoke-direct {p0, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->addAppIconData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 776
    .end local v0    # "addIcon":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    :cond_0
    return-void
.end method

.method private removeAppIconData(I)Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    .locals 3
    .param p1, "index"    # I

    .line 795
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 796
    .local v0, "delData":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mFilterSet:Ljava/util/Set;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    invoke-interface {v1, v2}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    .line 797
    return-object v0
.end method

.method private saveDataToSP(II)V
    .locals 2
    .param p1, "startPos"    # I
    .param p2, "count"    # I

    .line 646
    if-nez p1, :cond_0

    .line 647
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->clear()Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 650
    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-static {p2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 652
    :goto_0
    move v0, p1

    .local v0, "i":I
    :goto_1
    if-ge v0, p2, :cond_1

    .line 653
    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->saveDataToSP(IZ)V

    .line 652
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    .line 655
    .end local v0    # "i":I
    :cond_1
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->apply()V

    .line 656
    return-void
.end method

.method private saveShortNameList(Ljava/util/List;)V
    .locals 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;)V"
        }
    .end annotation

    .line 555
    .local p1, "appIconDataList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 556
    .local v0, "shortNameList":Ljava/lang/StringBuilder;
    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 558
    .local v2, "appIconData":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    const-string v3, "DynamicAppRecyclerView"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "appIconData.getComponentName::"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v5, "----mConfigMap="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    if-nez v5, :cond_0

    const/4 v5, 0x1

    goto :goto_1

    :cond_0
    const/4 v5, 0x0

    :goto_1
    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 561
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    new-instance v4, Lcom/hcn/widgets/dynamicrecyclerview/-$$Lambda$DynamicAppRecyclerView$irbLlZRxcBzstsXRFP8Q8Vqu9nA;

    invoke-direct {v4, p0, v2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/-$$Lambda$DynamicAppRecyclerView$irbLlZRxcBzstsXRFP8Q8Vqu9nA;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;Ljava/lang/StringBuilder;)V

    invoke-interface {v3, v4}, Ljava/util/Map;->forEach(Ljava/util/function/BiConsumer;)V

    .line 568
    .end local v2    # "appIconData":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    goto :goto_0

    .line 569
    :cond_1
    const-string v1, "DynamicAppRecyclerView"

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 570
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->save_file_name:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Landroid/provider/Settings$System;->putString(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;)Z

    .line 571
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSettingKey0:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->setSystemProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 572
    return-void
.end method

.method private showDialogOnlyText(Ljava/util/List;[Ljava/lang/CharSequence;)V
    .locals 3
    .param p2, "items"    # [Ljava/lang/CharSequence;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/content/pm/LauncherActivityInfo;",
            ">;[",
            "Ljava/lang/CharSequence;",
            ")V"
        }
    .end annotation

    .line 885
    .local p1, "activityInfoList":Ljava/util/List;, "Ljava/util/List<Landroid/content/pm/LauncherActivityInfo;>;"
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    const v2, 0x103012e

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    .line 887
    .local v0, "builder":Landroid/app/AlertDialog$Builder;
    :try_start_0
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$string;->dialog_title:I

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 890
    goto :goto_0

    .line 888
    :catch_0
    move-exception v1

    .line 889
    .local v1, "var5":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 892
    .end local v1    # "var5":Ljava/lang/Exception;
    :goto_0
    const/4 v1, -0x1

    new-instance v2, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;

    invoke-direct {v2, p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$5;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Ljava/util/List;)V

    invoke-virtual {v0, p2, v1, v2}, Landroid/app/AlertDialog$Builder;->setSingleChoiceItems([Ljava/lang/CharSequence;ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 901
    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;

    invoke-direct {v1, p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$6;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)Landroid/app/AlertDialog$Builder;

    .line 909
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAlertDialog:Landroid/app/AlertDialog;

    .line 910
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAlertDialog:Landroid/app/AlertDialog;

    invoke-virtual {v1}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0x7f6

    invoke-virtual {v1, v2}, Landroid/view/Window;->setType(I)V

    .line 911
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAlertDialog:Landroid/app/AlertDialog;

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 912
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOnDialogStatusListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    if-eqz v1, :cond_0

    .line 913
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOnDialogStatusListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    invoke-interface {v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;->onDialogShow()V

    .line 916
    :cond_0
    return-void
.end method


# virtual methods
.method protected RestoreData()V
    .locals 12

    .line 682
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->getAll()Ljava/util/Map;

    move-result-object v0

    .line 683
    .local v0, "map":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/String;*>;"
    const/4 v1, 0x0

    .line 684
    .local v1, "errorOccurred":Z
    const/4 v2, 0x0

    move v3, v1

    move v1, v2

    .local v1, "i":I
    .local v3, "errorOccurred":Z
    :goto_0
    invoke-interface {v0}, Ljava/util/Map;->size()I

    move-result v4

    if-ge v1, v4, :cond_4

    .line 686
    :try_start_0
    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-interface {v0, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 687
    .local v4, "s":Ljava/lang/String;
    const-string v5, "/"

    invoke-virtual {v4, v5}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v5

    .line 688
    .local v5, "array":[Ljava/lang/String;
    const-string v6, "DynamicAppRecyclerView"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "array:"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    array-length v8, v5

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 690
    aget-object v6, v5, v2

    if-nez v6, :cond_0

    .line 691
    const/4 v3, 0x1

    .line 692
    goto :goto_3

    .line 694
    :cond_0
    iget-object v6, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    aget-object v7, v5, v2

    invoke-static {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->getInstalledApps(Landroid/content/Context;Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v6

    .line 696
    .local v6, "infoList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/content/pm/LauncherActivityInfo;>;"
    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v7

    if-lez v7, :cond_2

    .line 697
    invoke-virtual {v6}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v7

    :goto_1
    invoke-interface {v7}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_3

    invoke-interface {v7}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Landroid/content/pm/LauncherActivityInfo;

    .line 698
    .local v8, "info":Landroid/content/pm/LauncherActivityInfo;
    invoke-virtual {v8}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v9

    invoke-virtual {v9}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v9

    const/4 v10, 0x1

    aget-object v10, v5, v10

    invoke-virtual {v9, v10}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_1

    .line 699
    new-instance v7, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v8}, Landroid/content/pm/LauncherActivityInfo;->getLabel()Ljava/lang/CharSequence;

    move-result-object v9

    invoke-interface {v9}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v9

    .line 700
    invoke-virtual {v8}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v10

    invoke-virtual {p0, v8}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object v11

    invoke-direct {v7, v9, v10, v11}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;-><init>(Ljava/lang/String;Landroid/content/ComponentName;Landroid/graphics/drawable/Drawable;)V

    .line 701
    .local v7, "data":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    invoke-direct {p0, v7}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->addAppIconData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 702
    goto :goto_2

    .line 704
    .end local v7    # "data":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    .end local v8    # "info":Landroid/content/pm/LauncherActivityInfo;
    :cond_1
    goto :goto_1

    .line 706
    :cond_2
    const/4 v3, 0x1

    .line 712
    .end local v4    # "s":Ljava/lang/String;
    .end local v5    # "array":[Ljava/lang/String;
    .end local v6    # "infoList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/content/pm/LauncherActivityInfo;>;"
    :cond_3
    :goto_2
    goto :goto_3

    .line 708
    :catch_0
    move-exception v4

    .line 709
    .local v4, "e":Ljava/lang/Exception;
    invoke-virtual {v4}, Ljava/lang/Exception;->printStackTrace()V

    .line 710
    const/4 v3, 0x1

    .line 711
    nop

    .line 684
    .end local v4    # "e":Ljava/lang/Exception;
    :goto_3
    add-int/lit8 v1, v1, 0x1

    goto/16 :goto_0

    .line 714
    .end local v1    # "i":I
    :cond_4
    if-eqz v3, :cond_5

    .line 715
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    invoke-direct {p0, v2, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->saveDataToSP(II)V

    .line 717
    :cond_5
    return-void
.end method

.method public accept(Landroid/content/pm/LauncherActivityInfo;)Z
    .locals 4
    .param p1, "info"    # Landroid/content/pm/LauncherActivityInfo;

    .line 744
    const/4 v0, 0x0

    if-nez p1, :cond_0

    .line 745
    return v0

    .line 747
    :cond_0
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mFilterSet:Ljava/util/Set;

    invoke-virtual {p1}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    invoke-interface {v1, v2}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x1

    xor-int/2addr v1, v2

    .line 748
    .local v1, "result":Z
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;

    if-eqz v3, :cond_2

    .line 749
    if-eqz v1, :cond_1

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;

    invoke-interface {v3, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;->accept(Landroid/content/pm/LauncherActivityInfo;)Z

    move-result v3

    if-eqz v3, :cond_1

    move v0, v2

    nop

    :cond_1
    move v1, v0

    .line 751
    :cond_2
    return v1
.end method

.method public containsAddItem()Z
    .locals 4

    .line 760
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x0

    if-lez v0, :cond_1

    .line 761
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    const/4 v3, 0x1

    sub-int/2addr v2, v3

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    if-nez v0, :cond_0

    move v1, v3

    nop

    :cond_0
    return v1

    .line 763
    :cond_1
    return v1
.end method

.method public create()V
    .locals 2

    .line 323
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    if-nez v0, :cond_0

    .line 324
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 325
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->create()V

    .line 327
    :cond_0
    return-void
.end method

.method public dispatchTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 2
    .param p1, "ev"    # Landroid/view/MotionEvent;

    .line 455
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    const/4 v1, 0x1

    invoke-interface {v0, v1}, Landroid/view/ViewParent;->requestDisallowInterceptTouchEvent(Z)V

    .line 456
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    return v0
.end method

.method public getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;
    .locals 1
    .param p1, "info"    # Landroid/content/pm/LauncherActivityInfo;

    .line 726
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;

    if-nez v0, :cond_0

    .line 727
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$2;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$2;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;

    .line 734
    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;

    invoke-interface {v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;->loadIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method

.method public initAppIconRecycleView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;Landroid/graphics/drawable/Drawable;ILjava/lang/String;)V
    .locals 7
    .param p1, "adapter"    # Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;
    .param p2, "addIcon"    # Landroid/graphics/drawable/Drawable;
    .param p3, "orientation"    # I
    .param p4, "save_file_name"    # Ljava/lang/String;

    .line 219
    iput p3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOrientation:I

    .line 220
    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAddIcon:Landroid/graphics/drawable/Drawable;

    .line 221
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mColumnCount:I

    const/4 v1, 0x0

    if-lez v0, :cond_0

    .line 222
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    new-instance v2, Landroidx/recyclerview/widget/GridLayoutManager;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mColumnCount:I

    invoke-direct {v2, v3, v4}, Landroidx/recyclerview/widget/GridLayoutManager;-><init>(Landroid/content/Context;I)V

    invoke-virtual {v0, v2}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutManager(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;)V

    goto :goto_0

    .line 224
    :cond_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    new-instance v2, Landroidx/recyclerview/widget/LinearLayoutManager;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mContext:Landroid/content/Context;

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOrientation:I

    invoke-direct {v2, v3, v4, v1}, Landroidx/recyclerview/widget/LinearLayoutManager;-><init>(Landroid/content/Context;IZ)V

    invoke-virtual {v0, v2}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutManager(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;)V

    .line 228
    :goto_0
    const/4 v0, 0x1

    const/4 v2, -0x1

    if-ne p3, v0, :cond_1

    .line 229
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    new-instance v4, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v4, v2, v2, v0}, Landroid/widget/FrameLayout$LayoutParams;-><init>(III)V

    invoke-virtual {p0, v3, v4}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    goto :goto_1

    .line 233
    :cond_1
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    new-instance v4, Landroid/widget/FrameLayout$LayoutParams;

    const/16 v5, 0x10

    invoke-direct {v4, v2, v2, v5}, Landroid/widget/FrameLayout$LayoutParams;-><init>(III)V

    invoke-virtual {p0, v3, v4}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 238
    :goto_1
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    .line 239
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-virtual {v2, v3}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->setData(Ljava/util/List;)V

    .line 241
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerViewItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {v2, v3}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 242
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerViewItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

    invoke-virtual {v2, v3}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->setOnItemLongClickListener(Landroid/widget/AdapterView$OnItemLongClickListener;)V

    .line 243
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    invoke-virtual {v2, v3}, Landroidx/recyclerview/widget/RecyclerView;->setAdapter(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V

    .line 244
    iput-object p4, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->save_file_name:Ljava/lang/String;

    .line 245
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "persist.sys."

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    iput-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSettingKey0:Ljava/lang/String;

    .line 246
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initConfigMap()Z

    move-result v2

    .line 249
    .local v2, "initSuccess":Z
    invoke-direct {p0, p4}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initData(Ljava/lang/String;)V

    .line 251
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->RestoreData()V

    .line 254
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    const/16 v4, 0x14

    if-ge v3, v4, :cond_2

    .line 255
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->putAddItemListTail()V

    .line 259
    :cond_2
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initHandler:Landroid/os/Handler;

    const/16 v4, 0x3e8

    invoke-virtual {v3, v4}, Landroid/os/Handler;->removeMessages(I)V

    .line 260
    if-nez v2, :cond_4

    .line 261
    const/4 v3, 0x0

    iput-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    .line 262
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->clear()V

    .line 263
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpFile:Ljava/io/File;

    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v3

    if-eqz v3, :cond_3

    .line 264
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpFile:Ljava/io/File;

    invoke-virtual {v3}, Ljava/io/File;->delete()Z

    .line 266
    :cond_3
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->initHandler:Landroid/os/Handler;

    const-wide/16 v5, 0xbb8

    invoke-virtual {v3, v4, v5, v6}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    .line 268
    :cond_4
    const-string v3, "DynamicAppRecyclerView"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "configMap==null:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    if-nez v5, :cond_5

    goto :goto_2

    :cond_5
    move v0, v1

    :goto_2
    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 269
    return-void
.end method

.method public insertDataWithNotify(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    .locals 4
    .param p1, "data"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 534
    :try_start_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x1

    sub-int/2addr v0, v1

    .line 535
    .local v0, "pos":I
    invoke-direct {p0, v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->addAppIconData(ILcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    .line 536
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    invoke-virtual {v2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->notifyItemInserted(I)V

    .line 537
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    invoke-virtual {v2, v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->notifyItemRangeChanged(II)V

    .line 538
    invoke-virtual {p0, v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->saveDataToSP(IZ)V

    .line 539
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-direct {p0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->saveShortNameList(Ljava/util/List;)V

    .line 540
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    const/16 v3, 0x14

    if-le v2, v3, :cond_0

    .line 541
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    sub-int/2addr v2, v1

    invoke-virtual {p0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->removeDataWithNotify(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 545
    .end local v0    # "pos":I
    :cond_0
    goto :goto_0

    .line 543
    :catch_0
    move-exception v0

    .line 546
    :goto_0
    return-void
.end method

.method public isDynamicAppListXmlExit()Z
    .locals 3

    .line 605
    new-instance v0, Ljava/io/File;

    const-string v1, "apd/dynamic_app_list.xml"

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->configFile:Ljava/io/File;

    .line 606
    const-string v0, "DynamicAppRecyclerView"

    const-string v1, "configFile.exists()"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 607
    const-string v0, "DynamicAppRecyclerView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "configMap==null:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    if-nez v2, :cond_0

    const/4 v2, 0x1

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 608
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->configFile:Ljava/io/File;

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    return v0
.end method

.method public mapToComponentString(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p1, "key"    # Ljava/lang/String;

    .line 612
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mConfigMap:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method

.method public notifyDataSetChanged()V
    .locals 1

    .line 489
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    if-eqz v0, :cond_0

    .line 490
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->notifyDataSetChanged()V

    .line 492
    :cond_0
    return-void
.end method

.method public removeDataWithNotify(I)V
    .locals 3
    .param p1, "pos"    # I

    .line 621
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/16 v1, 0x14

    if-ne v0, v1, :cond_0

    .line 622
    invoke-direct {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->putAddItemListTail()V

    .line 624
    :cond_0
    invoke-direct {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->removeAppIconData(I)Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 625
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    invoke-virtual {v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->notifyItemRemoved(I)V

    .line 627
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-eq p1, v0, :cond_1

    .line 628
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconAdapter:Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    sub-int/2addr v2, p1

    invoke-virtual {v0, p1, v2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;->notifyItemRangeChanged(II)V

    .line 630
    :cond_1
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    .line 631
    .local v0, "count":I
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ne v2, v1, :cond_2

    .line 632
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->containsAddItem()Z

    move-result v1

    if-nez v1, :cond_2

    .line 633
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v0

    .line 635
    :cond_2
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-direct {p0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->saveShortNameList(Ljava/util/List;)V

    .line 636
    invoke-direct {p0, p1, v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->saveDataToSP(II)V

    .line 637
    return-void
.end method

.method public saveDataToSP(IZ)V
    .locals 8
    .param p1, "pos"    # I
    .param p2, "withApply"    # Z

    .line 666
    :try_start_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mAppIconDataList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getComponentName()Landroid/content/ComponentName;

    move-result-object v0

    .line 667
    .local v0, "componentName":Landroid/content/ComponentName;
    if-eqz v0, :cond_0

    .line 668
    invoke-virtual {v0}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v1

    .line 669
    .local v1, "pkg":Ljava/lang/String;
    invoke-virtual {v0}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v2

    const-string v3, ""

    invoke-virtual {v2, v1, v3}, Ljava/lang/String;->replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 670
    .local v2, "cls":Ljava/lang/String;
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    const-string v5, "%s/%s"

    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object v1, v6, v7

    const/4 v7, 0x1

    aput-object v2, v6, v7

    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v3, v4, v5}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->put(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/SharedPreferences$Editor;

    .line 672
    if-eqz p2, :cond_0

    .line 673
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mSpHelper:Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;

    invoke-virtual {v3}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->apply()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 678
    .end local v0    # "componentName":Landroid/content/ComponentName;
    .end local v1    # "pkg":Ljava/lang/String;
    .end local v2    # "cls":Ljava/lang/String;
    :cond_0
    goto :goto_0

    .line 676
    :catch_0
    move-exception v0

    .line 677
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 679
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method

.method public setAppIconFilter(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;)V
    .locals 0
    .param p1, "iconFilter"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;

    .line 143
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconFilter:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconFilter;

    .line 144
    return-void
.end method

.method public setAppIconLoader(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;)V
    .locals 0
    .param p1, "iconLoader"    # Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;

    .line 129
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDefaultIconLoader:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;

    .line 130
    return-void
.end method

.method public setDialog(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;
    .locals 0
    .param p1, "dialog"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 282
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 283
    return-object p0
.end method

.method public showDialogWithIcon(Ljava/util/List;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;)V"
        }
    .end annotation

    .line 920
    .local p1, "appIconDataList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-virtual {v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setData(Ljava/util/List;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v0

    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;

    invoke-direct {v1, p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$8;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;Ljava/util/List;)V

    .line 921
    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v0

    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$7;

    invoke-direct {v1, p0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$7;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;)V

    .line 929
    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    move-result-object v0

    .line 937
    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->show()Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 939
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOnDialogStatusListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    if-eqz v0, :cond_0

    .line 940
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView;->mOnDialogStatusListener:Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;

    invoke-interface {v0}, Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$OnDialogStatusListener;->onDialogShow()V

    .line 942
    :cond_0
    return-void
.end method
