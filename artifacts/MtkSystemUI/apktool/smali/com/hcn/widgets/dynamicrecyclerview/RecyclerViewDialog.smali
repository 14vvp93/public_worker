.class public Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
.super Ljava/lang/Object;
.source "RecyclerViewDialog.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;
    }
.end annotation


# instance fields
.field private mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

.field private mColumnCount:I

.field private mContext:Landroid/content/Context;

.field private mDialog:Landroid/app/AlertDialog;

.field private mItemDivider:Landroid/graphics/drawable/Drawable;

.field private mItemHeight:I

.field private mItemIconSize:I

.field private mItemTextColor:I

.field private mItemTextDrawablePadding:I

.field private mItemTextSize:I

.field private mItemWidth:I

.field private mLLayout:Landroid/widget/LinearLayout;

.field private mLayoutType:I

.field private mOrientation:I

.field private mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

.field private mRecyclerViewLeftMargin:I

.field private mRecyclerViewTopMargin:I

.field private mRoot:Landroid/view/View;

.field private mRowCount:I

.field private mTvTitle:Landroid/widget/TextView;

.field private mWindowHeight:I

.field private mWindowWidth:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;

    .line 53
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 49
    const/4 v0, 0x1

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLayoutType:I

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mOrientation:I

    .line 54
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    .line 56
    invoke-static {p1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$layout;->layout_rv_dialog:I

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRoot:Landroid/view/View;

    .line 57
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRoot:Landroid/view/View;

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->dialog_rv:I

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroidx/recyclerview/widget/RecyclerView;

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    .line 58
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRoot:Landroid/view/View;

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->root:I

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLLayout:Landroid/widget/LinearLayout;

    .line 59
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRoot:Landroid/view/View;

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->tv_title:I

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mTvTitle:Landroid/widget/TextView;

    .line 61
    new-instance v0, Landroid/app/AlertDialog$Builder;

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$style;->dialogNoBg:I

    invoke-direct {v0, p1, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    .line 63
    .local v0, "builder":Landroid/app/AlertDialog$Builder;
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRoot:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    .line 64
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    .line 65
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1a

    if-le v1, v2, :cond_0

    .line 66
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v1}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0x7f6

    invoke-virtual {v1, v2}, Landroid/view/Window;->setType(I)V

    goto :goto_0

    .line 68
    :cond_0
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v1}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0x7d2

    invoke-virtual {v1, v2}, Landroid/view/Window;->setType(I)V

    .line 70
    :goto_0
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    sget v2, Lcom/hcn/widgets/dynamicrecyclerview/R$color;->white:I

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextColor:I

    .line 71
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLayoutType:I

    return v0
.end method

.method static synthetic access$100(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    return v0
.end method

.method static synthetic access$200(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    return v0
.end method

.method static synthetic access$300(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextDrawablePadding:I

    return v0
.end method

.method static synthetic access$400(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemIconSize:I

    return v0
.end method

.method static synthetic access$500(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextSize:I

    return v0
.end method

.method static synthetic access$600(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 37
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextColor:I

    return v0
.end method


# virtual methods
.method public create()V
    .locals 7

    .line 183
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowWidth:I

    if-nez v0, :cond_0

    .line 184
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->layout_rv_dialog_width:I

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    float-to-int v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowWidth:I

    .line 186
    :cond_0
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowHeight:I

    if-nez v0, :cond_1

    .line 187
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->layout_rv_dialog_height:I

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    float-to-int v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowHeight:I

    .line 189
    :cond_1
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-nez v0, :cond_2

    .line 190
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLLayout:Landroid/widget/LinearLayout;

    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$drawable;->dialog_bg:I

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setBackgroundResource(I)V

    .line 193
    :cond_2
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLayoutType:I

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez v0, :cond_6

    .line 194
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    if-gtz v0, :cond_3

    .line 195
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->item_dialog_linear_layout_width:I

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    float-to-int v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    .line 197
    :cond_3
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    if-gtz v0, :cond_4

    .line 198
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->item_dialog_linear_layout_height:I

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    float-to-int v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    .line 201
    :cond_4
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    new-instance v3, Landroidx/recyclerview/widget/LinearLayoutManager;

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-direct {v3, v4}, Landroidx/recyclerview/widget/LinearLayoutManager;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, v3}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutManager(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;)V

    .line 203
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-direct {v0, v3, v2, v1, v2}, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;-><init>(Landroid/content/Context;III)V

    .line 204
    .local v0, "decoration":Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemDivider:Landroid/graphics/drawable/Drawable;

    if-nez v1, :cond_5

    .line 205
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    sget v2, Lcom/hcn/widgets/dynamicrecyclerview/R$drawable;->icon_divider:I

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemDivider:Landroid/graphics/drawable/Drawable;

    .line 207
    :cond_5
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->setDrawable(Landroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;

    .line 208
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v1, v0}, Landroidx/recyclerview/widget/RecyclerView;->addItemDecoration(Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;)V

    .line 209
    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    sget v2, Lcom/hcn/widgets/dynamicrecyclerview/R$layout;->item_dialog_linear:I

    invoke-direct {v1, p0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;I)V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    .line 210
    .end local v0    # "decoration":Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;
    goto/16 :goto_1

    .line 211
    :cond_6
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    if-gtz v0, :cond_7

    .line 212
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->item_dialog_grid_layout_width:I

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    float-to-int v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    .line 214
    :cond_7
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    if-gtz v0, :cond_8

    .line 215
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$dimen;->item_dialog_grid_layout_height:I

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    float-to-int v0, v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    .line 218
    :cond_8
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRowCount:I

    if-nez v0, :cond_9

    const/4 v0, 0x2

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRowCount:I

    .line 219
    :cond_9
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mColumnCount:I

    if-nez v0, :cond_a

    const/4 v0, 0x5

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mColumnCount:I

    .line 221
    :cond_a
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout$LayoutParams;

    .line 222
    .local v0, "lp":Landroid/widget/LinearLayout$LayoutParams;
    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mColumnCount:I

    mul-int/2addr v3, v4

    iput v3, v0, Landroid/widget/LinearLayout$LayoutParams;->width:I

    .line 223
    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRowCount:I

    mul-int/2addr v3, v4

    iput v3, v0, Landroid/widget/LinearLayout$LayoutParams;->height:I

    .line 224
    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerViewTopMargin:I

    iput v3, v0, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 225
    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerViewLeftMargin:I

    iput v3, v0, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 226
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v3, v0}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 228
    iget v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mOrientation:I

    if-ne v3, v2, :cond_b

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mColumnCount:I

    goto :goto_0

    :cond_b
    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRowCount:I

    .line 229
    .local v2, "spanCount":I
    :goto_0
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    new-instance v4, Landroidx/recyclerview/widget/GridLayoutManager;

    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mContext:Landroid/content/Context;

    iget v6, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mOrientation:I

    invoke-direct {v4, v5, v2, v6, v1}, Landroidx/recyclerview/widget/GridLayoutManager;-><init>(Landroid/content/Context;IIZ)V

    invoke-virtual {v3, v4}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutManager(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;)V

    .line 230
    new-instance v1, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    sget v3, Lcom/hcn/widgets/dynamicrecyclerview/R$layout;->item_dialog_grid:I

    invoke-direct {v1, p0, v3}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;I)V

    iput-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    .line 232
    .end local v0    # "lp":Landroid/widget/LinearLayout$LayoutParams;
    .end local v2    # "spanCount":I
    :goto_1
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    invoke-virtual {v0, v1}, Landroidx/recyclerview/widget/RecyclerView;->setAdapter(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V

    .line 233
    return-void
.end method

.method public getDialog()Landroid/app/AlertDialog;
    .locals 1

    .line 77
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method public getRootView()Landroid/view/View;
    .locals 1

    .line 74
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRoot:Landroid/view/View;

    return-object v0
.end method

.method public hide()V
    .locals 1

    .line 259
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V

    .line 260
    return-void
.end method

.method public setColumnCount(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "columnCount"    # I

    .line 154
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mColumnCount:I

    .line 155
    return-object p0
.end method

.method public setData(Ljava/util/List;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;)",
            "Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;"
        }
    .end annotation

    .line 236
    .local p1, "appIconDataList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    invoke-virtual {v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->setData(Ljava/util/List;)V

    .line 237
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->notifyDataSetChanged()V

    .line 238
    return-object p0
.end method

.method public setDialogBackground(Landroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 1
    .param p1, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 81
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 82
    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowWidth:I

    .line 83
    invoke-virtual {p1}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowHeight:I

    .line 84
    return-object p0
.end method

.method public setDialogHeight(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "height"    # I

    .line 92
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowHeight:I

    .line 93
    return-object p0
.end method

.method public setDialogWidth(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "width"    # I

    .line 88
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowWidth:I

    .line 89
    return-object p0
.end method

.method public setItemHeight(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "height"    # I

    .line 119
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemHeight:I

    .line 120
    return-object p0
.end method

.method public setItemIconSize(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "size"    # I

    .line 139
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemIconSize:I

    .line 140
    return-object p0
.end method

.method public setItemTextColor(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "textColor"    # I

    .line 129
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextColor:I

    .line 130
    return-object p0
.end method

.method public setItemTextDrawablePadding(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "drawablePadding"    # I

    .line 134
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextDrawablePadding:I

    .line 135
    return-object p0
.end method

.method public setItemTextSize(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "textSize"    # I

    .line 124
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemTextSize:I

    .line 125
    return-object p0
.end method

.method public setItemWidth(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "width"    # I

    .line 114
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mItemWidth:I

    .line 115
    return-object p0
.end method

.method public setLayoutType(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "layoutType"    # I

    .line 144
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mLayoutType:I

    .line 145
    return-object p0
.end method

.method public setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 1
    .param p1, "listener"    # Landroid/content/DialogInterface$OnDismissListener;

    .line 247
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0, p1}, Landroid/app/AlertDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 248
    return-object p0
.end method

.method public setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 1
    .param p1, "listener"    # Landroid/widget/AdapterView$OnItemClickListener;

    .line 242
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mAdapter:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;

    invoke-virtual {v0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 243
    return-object p0
.end method

.method public setRecyclerViewTopMargin(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "topMargin"    # I

    .line 169
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRecyclerViewTopMargin:I

    .line 170
    return-object p0
.end method

.method public setRowCount(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "rowCount"    # I

    .line 149
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mRowCount:I

    .line 150
    return-object p0
.end method

.method public setTitleColor(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 1
    .param p1, "color"    # I

    .line 97
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mTvTitle:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 98
    return-object p0
.end method

.method public show()Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 3

    .line 252
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 254
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    iget v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowWidth:I

    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->mWindowHeight:I

    invoke-virtual {v0, v1, v2}, Landroid/view/Window;->setLayout(II)V

    .line 255
    return-object p0
.end method
