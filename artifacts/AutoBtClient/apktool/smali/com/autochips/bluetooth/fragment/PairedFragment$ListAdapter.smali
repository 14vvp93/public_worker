.class Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;
.super Landroid/widget/SimpleAdapter;
.source "PairedFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ListAdapter"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;
    }
.end annotation


# instance fields
.field mDeviceList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/util/ArrayMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field private mSelectAddr:Ljava/lang/String;

.field private str_hfp:Ljava/lang/String;

.field private str_hfp_connected:Ljava/lang/String;

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragment;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V
    .locals 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            "Ljava/util/List<",
            "Landroid/util/ArrayMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;I[",
            "Ljava/lang/String;",
            "[I)V"
        }
    .end annotation

    .line 688
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    move-object v0, p0

    move-object v1, p2

    move-object v2, p3

    move v3, p4

    move-object v4, p5

    move-object v5, p6

    .line 689
    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleAdapter;-><init>(Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    const-string p1, ""

    .line 682
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mSelectAddr:Ljava/lang/String;

    const/4 p2, 0x0

    .line 684
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->str_hfp:Ljava/lang/String;

    .line 685
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->str_hfp_connected:Ljava/lang/String;

    .line 691
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mSelectAddr:Ljava/lang/String;

    .line 692
    iput-object p3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mDeviceList:Ljava/util/List;

    const p1, 0x7f0d008e

    .line 693
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->str_hfp:Ljava/lang/String;

    const p1, 0x7f0d004a

    .line 694
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->str_hfp_connected:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public getItem(I)Landroid/util/ArrayMap;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Landroid/util/ArrayMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation

    .line 707
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mDeviceList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/util/ArrayMap;

    return-object p1
.end method

.method public bridge synthetic getItem(I)Ljava/lang/Object;
    .locals 0

    .line 681
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->getItem(I)Landroid/util/ArrayMap;

    move-result-object p1

    return-object p1
.end method

.method public getSelect()Ljava/lang/String;
    .locals 1

    .line 702
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mSelectAddr:Ljava/lang/String;

    return-object v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 6

    if-nez p2, :cond_1

    .line 714
    new-instance p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;

    invoke-direct {p2, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;)V

    const p3, 0x7f0b0029

    .line 715
    invoke-static {p3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object p3

    const v0, 0x7f0800df

    .line 716
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    const v0, 0x7f0800dd

    .line 717
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    const v0, 0x7f0800de

    .line 718
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->macaddTextView:Landroid/widget/TextView;

    const v0, 0x7f080076

    .line 719
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    .line 720
    iget-object v0, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    if-eqz v0, :cond_0

    .line 721
    iget-object v0, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 724
    :cond_0
    invoke-virtual {p3, p2}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    goto :goto_0

    .line 726
    :cond_1
    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;

    move-object v5, p3

    move-object p3, p2

    move-object p2, v5

    .line 733
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mDeviceList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, -0x1

    const-string v2, ""

    if-ge p1, v0, :cond_5

    .line 734
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mDeviceList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/util/ArrayMap;

    const-string v0, "remote_device_name"

    .line 735
    invoke-virtual {p1, v0}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    move-object v2, v0

    check-cast v2, Ljava/lang/String;

    const-string v0, "remote_connect_status"

    .line 736
    invoke-virtual {p1, v0}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const-string v3, "remote_device_macaddr"

    .line 737
    invoke-virtual {p1, v3}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    .line 739
    iget-object v4, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    if-eqz v4, :cond_4

    .line 740
    iget-object v4, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    invoke-virtual {v4, p1}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    const/4 p1, 0x4

    if-ne v0, v1, :cond_2

    .line 742
    iget-object v1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    invoke-virtual {v1, p1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_1

    :cond_2
    const/16 v1, 0xc

    if-eq v0, v1, :cond_3

    .line 745
    iget-object v1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    invoke-virtual {v1, p1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_1

    .line 747
    :cond_3
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->profileConfigImageButton:Landroid/view/View;

    const/4 v1, 0x0

    invoke-virtual {p1, v1}, Landroid/view/View;->setVisibility(I)V

    :cond_4
    :goto_1
    move v1, v0

    goto :goto_2

    :cond_5
    move-object v3, v2

    .line 752
    :goto_2
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    if-eqz p1, :cond_6

    .line 753
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 755
    :cond_6
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    if-eqz p1, :cond_7

    .line 756
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 758
    :cond_7
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->macaddTextView:Landroid/widget/TextView;

    if-eqz p1, :cond_8

    .line 759
    iget-object p1, p2, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter$ViewHolder;->macaddTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_8
    const p1, 0x7f0800ac

    .line 762
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result p1

    invoke-virtual {p3, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    if-eqz p1, :cond_a

    .line 764
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mSelectAddr:Ljava/lang/String;

    invoke-virtual {p2}, Ljava/lang/String;->length()I

    move-result p2

    if-lez p2, :cond_9

    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mSelectAddr:Ljava/lang/String;

    invoke-virtual {p2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_9

    const p2, 0x7f070119

    .line 765
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    :cond_9
    const p2, 0x7f070118

    .line 767
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_a
    :goto_3
    return-object p3
.end method

.method public setSelect(Ljava/lang/String;)V
    .locals 0

    .line 698
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->mSelectAddr:Ljava/lang/String;

    return-void
.end method
