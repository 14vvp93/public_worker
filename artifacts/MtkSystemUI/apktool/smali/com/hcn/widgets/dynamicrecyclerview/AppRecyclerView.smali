.class public Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;
.super Landroid/widget/FrameLayout;
.source "AppRecyclerView.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconFilter;,
        Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;,
        Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;,
        Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;
    }
.end annotation


# instance fields
.field private mAppIconDataList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;"
        }
    .end annotation
.end field

.field private mClickListener:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;

.field private mColumnCount:I

.field private mContext:Landroid/content/Context;

.field private mDialog:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

.field private mHandler:Landroid/os/Handler;

.field private mLongClickListener:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;

.field private mOrientation:I

.field private mPresetDataList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;"
        }
    .end annotation
.end field

.field private mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

.field private mRecyclerViewItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field mRecyclerViewItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 63
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 64
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyleAttr"    # I

    .line 67
    invoke-direct {p0, p1, p2, p3}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 39
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mColumnCount:I

    .line 44
    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mOrientation:I

    .line 46
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mAppIconDataList:Ljava/util/ArrayList;

    .line 47
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mPresetDataList:Ljava/util/ArrayList;

    .line 56
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mHandler:Landroid/os/Handler;

    .line 201
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$3;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mRecyclerViewItemClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 225
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;

    invoke-direct {v0, p0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$4;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mRecyclerViewItemLongClickListener:Landroid/widget/AdapterView$OnItemLongClickListener;

    .line 68
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mContext:Landroid/content/Context;

    .line 69
    new-instance v0, Landroidx/recyclerview/widget/RecyclerView;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroidx/recyclerview/widget/RecyclerView;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    .line 70
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    .line 33
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mDialog:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    return-object v0
.end method

.method static synthetic access$200(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Ljava/util/ArrayList;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    .line 33
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mAppIconDataList:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$300(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    .line 33
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mClickListener:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemClickListener;

    return-object v0
.end method

.method static synthetic access$400(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    .line 33
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$500(Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;)Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;

    .line 33
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->mLongClickListener:Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$OnAppIconItemLongClickListener;

    return-object v0
.end method


# virtual methods
.method public dispatchTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 2
    .param p1, "ev"    # Landroid/view/MotionEvent;

    .line 335
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    const/4 v1, 0x1

    invoke-interface {v0, v1}, Landroid/view/ViewParent;->requestDisallowInterceptTouchEvent(Z)V

    .line 336
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    return v0
.end method
