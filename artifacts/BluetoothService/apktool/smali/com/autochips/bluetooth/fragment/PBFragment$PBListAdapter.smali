.class Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;
.super Landroid/widget/SimpleAdapter;
.source "PBFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "PBListAdapter"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;
    }
.end annotation


# instance fields
.field private mList:Ljava/util/ArrayList;
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

.field private mSearchLetter:Ljava/lang/String;

.field private mSearchPBList:Ljava/util/ArrayList;
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

.field private mSelectIdx:I

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;Landroid/content/Context;Ljava/util/ArrayList;I[Ljava/lang/String;[I)V
    .locals 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;>;I[",
            "Ljava/lang/String;",
            "[I)V"
        }
    .end annotation

    .line 689
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    move-object v0, p0

    move-object v1, p2

    move-object v2, p3

    move v3, p4

    move-object v4, p5

    move-object v5, p6

    .line 690
    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleAdapter;-><init>(Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    const/4 p1, -0x1

    .line 682
    iput p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSelectIdx:I

    const-string p2, ""

    .line 685
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    .line 691
    iput p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSelectIdx:I

    .line 692
    iput-object p3, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mList:Ljava/util/ArrayList;

    .line 693
    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    return-void
.end method

.method static synthetic access$1100(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/lang/String;
    .locals 0

    .line 681
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$1300(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/util/ArrayList;
    .locals 0

    .line 681
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    return-object p0
.end method


# virtual methods
.method public getCount()I
    .locals 1

    .line 731
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 732
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0

    .line 735
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mList:Ljava/util/ArrayList;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public bridge synthetic getItem(I)Ljava/lang/Object;
    .locals 0

    .line 681
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->getItem(I)Ljava/util/HashMap;

    move-result-object p1

    return-object p1
.end method

.method public getItem(I)Ljava/util/HashMap;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 740
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_1

    .line 741
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-ge p1, v0, :cond_0

    .line 742
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    return-object p1

    :cond_0
    return-object v1

    .line 747
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mList:Ljava/util/ArrayList;

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-lt p1, v0, :cond_2

    goto :goto_0

    .line 750
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    return-object p1

    :cond_3
    :goto_0
    return-object v1
.end method

.method public getSelect()I
    .locals 1

    .line 754
    iget v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSelectIdx:I

    return v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 7

    if-nez p2, :cond_0

    .line 761
    new-instance p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;

    invoke-direct {p2, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)V

    const p3, 0x7f0b0038

    .line 762
    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object p3

    const v0, 0x7f0800db

    .line 763
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    const v0, 0x7f0800dc

    .line 764
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->phoneTextView:Landroid/widget/TextView;

    const v0, 0x7f080090

    .line 765
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->pathImageView:Landroid/widget/ImageView;

    .line 766
    invoke-virtual {p3, p2}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    goto :goto_0

    .line 768
    :cond_0
    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;

    move-object v6, p3

    move-object p3, p2

    move-object p2, v6

    .line 775
    :goto_0
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->getItem(I)Ljava/util/HashMap;

    move-result-object v0

    const-string v1, ""

    if-eqz v0, :cond_2

    const-string v1, "item_phonebook_name"

    .line 777
    invoke-virtual {v0, v1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 778
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    const v1, 0x7f0d006a

    .line 779
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    .line 781
    :cond_1
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v2

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    const/4 v5, 0x1

    add-int/2addr p1, v5

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    aput-object p1, v3, v4

    aput-object v1, v3, v5

    const-string p1, "%d. %s"

    invoke-static {v2, p1, v3}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    const-string p1, "item_phonebook_number"

    .line 782
    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    const-string v2, "item_phonebook_path"

    .line 783
    invoke-virtual {v0, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    goto :goto_1

    :cond_2
    move-object p1, v1

    move-object v0, p1

    .line 786
    :goto_1
    iget-object v2, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    if-eqz v2, :cond_3

    .line 787
    iget-object v2, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 789
    :cond_3
    iget-object v1, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->phoneTextView:Landroid/widget/TextView;

    if-eqz v1, :cond_4

    .line 790
    iget-object v1, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->phoneTextView:Landroid/widget/TextView;

    invoke-virtual {v1, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 792
    :cond_4
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->pathImageView:Landroid/widget/ImageView;

    if-eqz p1, :cond_6

    const-string p1, "1"

    .line 793
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_5

    .line 794
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->pathImageView:Landroid/widget/ImageView;

    const p2, 0x7f07007c

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 796
    :cond_5
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter$ViewHolder;->pathImageView:Landroid/widget/ImageView;

    const p2, 0x7f07007b

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_6
    :goto_2
    return-object p3
.end method

.method public setSearchLetter(Ljava/lang/String;)V
    .locals 4

    if-eqz p1, :cond_0

    .line 702
    invoke-virtual {p1}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object p1

    goto :goto_0

    :cond_0
    const-string p1, ""

    :goto_0
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    .line 703
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->clear()V

    .line 705
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_1

    .line 706
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->notifyDataSetChanged()V

    return-void

    .line 710
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    const-string v0, "#"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    const-string v0, "item_phonebook_label"

    if-eqz p1, :cond_4

    .line 711
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :cond_2
    :goto_1
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_6

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/HashMap;

    .line 712
    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 713
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_3

    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Ljava/lang/String;->charAt(I)C

    move-result v2

    invoke-static {v2}, Ljava/lang/Character;->isLetter(C)Z

    move-result v2

    if-nez v2, :cond_2

    .line 714
    :cond_3
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_1

    .line 718
    :cond_4
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :cond_5
    :goto_2
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_6

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/HashMap;

    .line 719
    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 720
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_5

    invoke-virtual {v2}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchLetter:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_5

    .line 721
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSearchPBList:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_2

    .line 726
    :cond_6
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->notifyDataSetChanged()V

    return-void
.end method

.method public setSelect(I)V
    .locals 0

    .line 697
    iput p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->mSelectIdx:I

    return-void
.end method
