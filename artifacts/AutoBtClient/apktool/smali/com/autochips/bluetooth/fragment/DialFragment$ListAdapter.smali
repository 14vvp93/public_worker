.class Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;
.super Landroid/widget/BaseAdapter;
.source "DialFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/DialFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ListAdapter"
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private mPBDatas:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation
.end field

.field private mSearchText:Ljava/lang/String;

.field private mlayoutInflater:Landroid/view/LayoutInflater;

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/DialFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/content/Context;Ljava/util/ArrayList;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;>;)V"
        }
    .end annotation

    .line 634
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {p0}, Landroid/widget/BaseAdapter;-><init>()V

    .line 636
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mContext:Landroid/content/Context;

    .line 637
    iput-object p3, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mPBDatas:Ljava/util/ArrayList;

    .line 638
    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mlayoutInflater:Landroid/view/LayoutInflater;

    return-void
.end method


# virtual methods
.method public getCount()I
    .locals 1

    .line 647
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mPBDatas:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0
.end method

.method public getItem(I)Ljava/lang/Object;
    .locals 1

    .line 652
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mPBDatas:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    return-object p1
.end method

.method public getItemId(I)J
    .locals 2

    const-wide/16 v0, 0x0

    return-wide v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 5

    if-nez p2, :cond_0

    .line 665
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mlayoutInflater:Landroid/view/LayoutInflater;

    const v0, 0x7f0b002a

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x0

    invoke-virtual {p2, v0, p3, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p2

    const p3, 0x7f0800ad

    .line 666
    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p3

    invoke-virtual {p2, p3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p3

    check-cast p3, Landroid/widget/TextView;

    const v0, 0x7f0800ae

    .line 667
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 668
    new-instance v1, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {v1, v2, p3, v0}, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/widget/TextView;Landroid/widget/TextView;)V

    .line 669
    invoke-virtual {p2, v1}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    goto :goto_0

    .line 671
    :cond_0
    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p3

    move-object v1, p3

    check-cast v1, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;

    .line 674
    :goto_0
    iget-object p3, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mPBDatas:Ljava/util/ArrayList;

    invoke-virtual {p3, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Ljava/util/HashMap;

    const-string v0, "item_phonebook_number"

    invoke-virtual {p3, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Ljava/lang/String;

    .line 676
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mSearchText:Ljava/lang/String;

    invoke-virtual {p3, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 677
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mSearchText:Ljava/lang/String;

    invoke-virtual {p3, v0}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v0

    .line 678
    new-instance v2, Landroid/text/SpannableStringBuilder;

    invoke-direct {v2, p3}, Landroid/text/SpannableStringBuilder;-><init>(Ljava/lang/CharSequence;)V

    .line 679
    new-instance p3, Landroid/text/style/ForegroundColorSpan;

    const v3, 0x7f050043

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v3

    invoke-direct {p3, v3}, Landroid/text/style/ForegroundColorSpan;-><init>(I)V

    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mSearchText:Ljava/lang/String;

    .line 680
    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v3

    add-int/2addr v3, v0

    const/16 v4, 0x22

    .line 679
    invoke-virtual {v2, p3, v0, v3, v4}, Landroid/text/SpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 681
    iget-object p3, v1, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;->mContactNum:Landroid/widget/TextView;

    invoke-virtual {p3, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_1

    .line 683
    :cond_1
    iget-object p3, v1, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;->mContactNum:Landroid/widget/TextView;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mPBDatas:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/HashMap;

    invoke-virtual {v2, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/CharSequence;

    invoke-virtual {p3, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 686
    :goto_1
    iget-object p3, v1, Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;->mContactName:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mPBDatas:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    const-string v0, "item_phonebook_name"

    invoke-virtual {p1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/CharSequence;

    invoke-virtual {p3, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    return-object p2
.end method

.method public setSearchText(Ljava/lang/String;)V
    .locals 0

    .line 642
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->mSearchText:Ljava/lang/String;

    return-void
.end method
