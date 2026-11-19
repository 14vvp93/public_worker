.class Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;
.super Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;
.source "RecyclerViewDialog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "AppIconAdapter"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter<",
        "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;


# direct methods
.method protected constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;I)V
    .locals 0
    .param p2, "layoutId"    # I

    .line 268
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    .line 269
    invoke-direct {p0, p2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseRecyclerViewAdapter;-><init>(I)V

    .line 270
    return-void
.end method


# virtual methods
.method public bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V
    .locals 7
    .param p2, "data"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
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

    .line 273
    .local p1, "holder":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    sget v0, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->app_icon:I

    invoke-virtual {p1, v0}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getView(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 274
    .local v0, "appIcon":Landroid/widget/ImageView;
    sget v1, Lcom/hcn/widgets/dynamicrecyclerview/R$id;->app_name:I

    invoke-virtual {p1, v1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->getView(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 276
    .local v1, "appName":Landroid/widget/TextView;
    invoke-virtual {v0}, Landroid/widget/ImageView;->getParent()Landroid/view/ViewParent;

    move-result-object v2

    check-cast v2, Landroid/widget/LinearLayout;

    .line 277
    .local v2, "ll":Landroid/widget/LinearLayout;
    invoke-virtual {v1}, Landroid/widget/TextView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v3

    check-cast v3, Landroid/widget/LinearLayout$LayoutParams;

    .line 278
    .local v3, "appNameLp":Landroid/widget/LinearLayout$LayoutParams;
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$000(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v4

    if-nez v4, :cond_1

    .line 279
    invoke-virtual {v2}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v4

    check-cast v4, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 280
    .local v4, "lp":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$100(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v4, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->width:I

    .line 281
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v4, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->height:I

    .line 282
    invoke-virtual {v2, v4}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 284
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    if-lez v5, :cond_0

    .line 285
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v3, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 287
    :cond_0
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$100(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iget-object v6, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v6}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v6

    sub-int/2addr v5, v6

    iget-object v6, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v6}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v6

    sub-int/2addr v5, v6

    add-int/lit8 v5, v5, -0x14

    invoke-virtual {v1, v5}, Landroid/widget/TextView;->setMaxWidth(I)V

    .line 288
    .end local v4    # "lp":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    goto :goto_0

    .line 289
    :cond_1
    invoke-virtual {v2}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v4

    check-cast v4, Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;

    .line 290
    .local v4, "lp":Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$100(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v4, Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;->width:I

    .line 291
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$200(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v4, Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;->height:I

    .line 292
    invoke-virtual {v2, v4}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 294
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    if-lez v5, :cond_2

    .line 295
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$300(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v3, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 297
    :cond_2
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$100(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    add-int/lit8 v5, v5, -0xa

    invoke-virtual {v1, v5}, Landroid/widget/TextView;->setMaxWidth(I)V

    .line 299
    .end local v4    # "lp":Landroidx/recyclerview/widget/GridLayoutManager$LayoutParams;
    :goto_0
    invoke-virtual {v1, v3}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 301
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v4

    if-lez v4, :cond_3

    .line 302
    invoke-virtual {v0}, Landroid/widget/ImageView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v4

    check-cast v4, Landroid/widget/LinearLayout$LayoutParams;

    .line 303
    .local v4, "appIconLp":Landroid/widget/LinearLayout$LayoutParams;
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v4, Landroid/widget/LinearLayout$LayoutParams;->width:I

    .line 304
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v5

    iput v5, v4, Landroid/widget/LinearLayout$LayoutParams;->height:I

    .line 305
    invoke-virtual {v0, v4}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 308
    .end local v4    # "appIconLp":Landroid/widget/LinearLayout$LayoutParams;
    :cond_3
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v4

    if-lez v4, :cond_4

    .line 309
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v4

    int-to-float v4, v4

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setTextSize(F)V

    .line 311
    :cond_4
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;->access$600(Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;)I

    move-result v4

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setTextColor(I)V

    .line 313
    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getAppIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v4

    invoke-virtual {v0, v4}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 314
    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getAppName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 315
    return-void
.end method

.method public bridge synthetic bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Ljava/lang/Object;I)V
    .locals 0

    .line 266
    check-cast p2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {p0, p1, p2, p3}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog$AppIconAdapter;->bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V

    return-void
.end method
