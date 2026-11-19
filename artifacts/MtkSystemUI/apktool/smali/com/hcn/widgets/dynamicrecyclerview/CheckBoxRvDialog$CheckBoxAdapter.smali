.class Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;
.super Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;
.source "CheckBoxRvDialog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "CheckBoxAdapter"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<",
        "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
        ">;"
    }
.end annotation


# instance fields
.field private checked:Z

.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;


# direct methods
.method static synthetic access$2200(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;)Z
    .locals 1
    .param p0, "x0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    .line 613
    iget-boolean v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->checked:Z

    return v0
.end method


# virtual methods
.method public addData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    .locals 1
    .param p1, "data"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 627
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->mData:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 628
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->notifyDataSetChanged()V

    .line 629
    return-void
.end method

.method public bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V
    .locals 5
    .param p2, "appInfo"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    .param p3, "position"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            "I)V"
        }
    .end annotation

    .line 638
    .local p1, "holder":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    sget v0, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->checkbox:I

    invoke-virtual {p1, v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getView(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;

    .line 639
    .local v0, "checkBox":Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;
    invoke-virtual {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->getParent()Landroid/view/ViewParent;

    move-result-object v1

    check-cast v1, Landroid/widget/FrameLayout;

    .line 640
    .local v1, "fl":Landroid/widget/FrameLayout;
    iget-boolean v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->checked:Z

    const/4 v3, -0x2

    if-eqz v2, :cond_2

    .line 641
    invoke-virtual {v1}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v2

    check-cast v2, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 642
    .local v2, "lp":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v4

    if-lez v4, :cond_0

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v4

    goto :goto_0

    :cond_0
    move v4, v3

    :goto_0
    iput v4, v2, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->width:I

    .line 643
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v4

    if-lez v4, :cond_1

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v3

    nop

    :cond_1
    iput v3, v2, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->height:I

    .line 644
    invoke-virtual {v1, v2}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 645
    .end local v2    # "lp":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    goto :goto_2

    .line 646
    :cond_2
    invoke-virtual {v1}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v2

    check-cast v2, Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;

    .line 647
    .local v2, "lp":Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v4

    if-lez v4, :cond_3

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1300(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v4

    goto :goto_1

    :cond_3
    move v4, v3

    :goto_1
    iput v4, v2, Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;->width:I

    .line 648
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v4

    if-lez v4, :cond_4

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v3

    nop

    :cond_4
    iput v3, v2, Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;->height:I

    .line 649
    invoke-virtual {v1, v2}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 652
    .end local v2    # "lp":Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;
    :goto_2
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    if-lez v2, :cond_5

    .line 653
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setDrawableSize(I)V

    .line 655
    :cond_5
    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getAppIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 656
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1600(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    if-eqz v2, :cond_6

    .line 657
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1600(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setCheckIcon(Landroid/graphics/drawable/Drawable;)V

    .line 659
    :cond_6
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1700(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setIconGravity(I)V

    .line 660
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    if-lez v2, :cond_7

    .line 661
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setTextSize(I)V

    .line 663
    :cond_7
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setTextColor(I)V

    .line 664
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$2000(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    int-to-float v2, v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setTextMarginTop(F)V

    .line 665
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$2100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    if-lez v2, :cond_8

    .line 666
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$2100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)I

    move-result v2

    int-to-float v2, v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setPadding(F)V

    .line 668
    :cond_8
    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getAppName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setText(Ljava/lang/String;)V

    .line 669
    iget-boolean v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->checked:Z

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setChecked(Z)V

    .line 671
    new-instance v2, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;

    invoke-direct {v2, p0, p2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter$1;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V

    invoke-virtual {v0, v2}, Lcom/hcn/widgets/dynamicrecyclerview/CustomCheckBox;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 690
    return-void
.end method

.method public bridge synthetic bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Ljava/lang/Object;I)V
    .locals 0

    .line 613
    check-cast p2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {p0, p1, p2, p3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V

    return-void
.end method

.method public removeData(Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;)V
    .locals 1
    .param p1, "data"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    .line 632
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->mData:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    .line 633
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->notifyDataSetChanged()V

    .line 634
    return-void
.end method

.method public setList(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;)V"
        }
    .end annotation

    .line 622
    .local p1, "data":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    invoke-virtual {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->setData(Ljava/util/List;)V

    .line 623
    invoke-virtual {p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->notifyDataSetChanged()V

    .line 624
    return-void
.end method
