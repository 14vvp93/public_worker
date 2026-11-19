.class Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;
.super Landroid/widget/SimpleAdapter;
.source "RecordFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/RecordFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "HistoryListAdapter"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;
    }
.end annotation


# instance fields
.field private mList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field private mSelectIdx:I

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/RecordFragment;Landroid/content/Context;Ljava/util/ArrayList;I[Ljava/lang/String;[I)V
    .locals 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;I[",
            "Ljava/lang/String;",
            "[I)V"
        }
    .end annotation

    .line 571
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    move-object v0, p0

    move-object v1, p2

    move-object v2, p3

    move v3, p4

    move-object v4, p5

    move-object v5, p6

    .line 572
    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleAdapter;-><init>(Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    const/4 p1, -0x1

    .line 566
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mSelectIdx:I

    .line 574
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mSelectIdx:I

    .line 575
    iput-object p3, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    return-void
.end method


# virtual methods
.method public bridge synthetic getItem(I)Ljava/lang/Object;
    .locals 0

    .line 565
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->getItem(I)Ljava/util/HashMap;

    move-result-object p1

    return-object p1
.end method

.method public getItem(I)Ljava/util/HashMap;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation

    .line 580
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    if-eqz v0, :cond_0

    .line 581
    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    return-object p1

    :cond_0
    const/4 p1, 0x0

    return-object p1
.end method

.method public getSelect()I
    .locals 1

    .line 591
    iget v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mSelectIdx:I

    return v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 11

    if-nez p2, :cond_0

    .line 598
    new-instance p2, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;

    invoke-direct {p2, p0}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;)V

    const p3, 0x7f0b002b

    .line 599
    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object p3

    const v0, 0x7f0800d7

    .line 601
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    const v0, 0x7f0800d8

    .line 603
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->phoneTextView:Landroid/widget/TextView;

    const v0, 0x7f0800d9

    .line 605
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->timeTextView:Landroid/widget/TextView;

    const v0, 0x7f080061

    .line 607
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->imageView:Landroid/widget/ImageView;

    .line 608
    invoke-virtual {p3, p2}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    move-object v10, p3

    move-object p3, p2

    move-object p2, v10

    goto :goto_0

    .line 610
    :cond_0
    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;

    :goto_0
    const/4 v0, -0x1

    .line 617
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    const/4 v2, 0x2

    const/4 v3, 0x1

    const-string v4, ""

    if-ge p1, v1, :cond_2

    .line 619
    :try_start_0
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {v1, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/HashMap;

    const-string v5, "item_history_name"

    invoke-virtual {v1, v5}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    .line 620
    :try_start_1
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v5

    if-eqz v5, :cond_1

    const v5, 0x7f0d006a

    .line 621
    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    .line 623
    :cond_1
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v5

    const-string v6, "%d. %s"

    new-array v7, v2, [Ljava/lang/Object;

    const/4 v8, 0x0

    add-int/lit8 v9, p1, 0x1

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    aput-object v1, v7, v3

    invoke-static {v5, v6, v7}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 624
    iget-object v5, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {v5, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/util/HashMap;

    const-string v6, "item_history_number"

    invoke-virtual {v5, v6}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    .line 625
    :try_start_2
    iget-object v6, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {v6, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/util/HashMap;

    const-string v7, "item_history_type"

    invoke-virtual {v6, v7}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Integer;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v0

    .line 626
    iget-object v6, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {v6, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    const-string v6, "item_history_time"

    invoke-virtual {p1, v6}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Long;

    invoke-virtual {p1}, Ljava/lang/Long;->longValue()J

    move-result-wide v6

    .line 627
    invoke-static {v6, v7}, Lcom/hcn/bluetooth/api/Utils;->getDateToString(J)Ljava/lang/String;

    move-result-object v4
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_2

    goto :goto_2

    :catch_0
    move-object v5, v4

    goto :goto_1

    :catch_1
    move-object v1, v4

    move-object v5, v1

    :catch_2
    :goto_1
    const-string p1, "RecordFragment"

    const-string v6, "getView: Exception!!!!!"

    .line 629
    invoke-static {p1, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_2
    move-object p1, v4

    move-object v4, v1

    goto :goto_3

    :cond_2
    move-object p1, v4

    move-object v5, p1

    .line 633
    :goto_3
    iget-object v1, p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 634
    iget-object v1, p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->phoneTextView:Landroid/widget/TextView;

    invoke-virtual {v1, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 635
    iget-object v1, p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->timeTextView:Landroid/widget/TextView;

    invoke-virtual {v1, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    if-eq v0, v3, :cond_4

    if-eq v0, v2, :cond_3

    .line 644
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->imageView:Landroid/widget/ImageView;

    const p3, 0x7f0700ce

    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p3

    invoke-virtual {p1, p3}, Landroid/widget/ImageView;->setImageResource(I)V

    goto :goto_4

    .line 638
    :cond_3
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->imageView:Landroid/widget/ImageView;

    const p3, 0x7f0700cf

    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p3

    invoke-virtual {p1, p3}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_4

    .line 641
    :cond_4
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->imageView:Landroid/widget/ImageView;

    const p3, 0x7f0700cd

    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p3

    invoke-virtual {p1, p3}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    :goto_4
    return-object p2
.end method

.method public setSelect(I)V
    .locals 0

    .line 587
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->mSelectIdx:I

    return-void
.end method
