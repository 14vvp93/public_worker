.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;
.super Landroid/widget/SimpleAdapter;
.source "PairedFragmentEx.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "PairedListAdapter"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;
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

.field private mSelectMacAddress:Ljava/lang/String;

.field private pairedStatus:Z

.field private str_hfp:Ljava/lang/String;

.field private str_hfp_connected:Ljava/lang/String;

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V
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

    .line 776
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    move-object v0, p0

    move-object v1, p2

    move-object v2, p3

    move v3, p4

    move-object v4, p5

    move-object v5, p6

    .line 777
    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleAdapter;-><init>(Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    const-string p1, ""

    .line 769
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mSelectMacAddress:Ljava/lang/String;

    const/4 p2, 0x0

    .line 771
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->str_hfp:Ljava/lang/String;

    .line 772
    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->str_hfp_connected:Ljava/lang/String;

    .line 779
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mSelectMacAddress:Ljava/lang/String;

    .line 780
    iput-object p3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mDeviceList:Ljava/util/List;

    const p1, 0x7f0d008e

    .line 781
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->str_hfp:Ljava/lang/String;

    const p1, 0x7f0d004a

    .line 782
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->str_hfp_connected:Ljava/lang/String;

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

    .line 803
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mDeviceList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/util/ArrayMap;

    return-object p1
.end method

.method public bridge synthetic getItem(I)Ljava/lang/Object;
    .locals 0

    .line 768
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getItem(I)Landroid/util/ArrayMap;

    move-result-object p1

    return-object p1
.end method

.method public getPairedStatus()Z
    .locals 1

    .line 798
    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->pairedStatus:Z

    return v0
.end method

.method public getSelect()Ljava/lang/String;
    .locals 1

    .line 790
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mSelectMacAddress:Ljava/lang/String;

    return-object v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 7

    if-nez p2, :cond_0

    const p2, 0x7f0b0029

    .line 810
    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object p2

    .line 811
    new-instance p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;

    invoke-direct {p3, p0, p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;Landroid/view/View;)V

    .line 812
    invoke-virtual {p2, p3}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    goto :goto_0

    .line 814
    :cond_0
    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;

    .line 821
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mDeviceList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x0

    const-string v2, ""

    const/4 v3, 0x4

    if-ge p1, v0, :cond_3

    .line 822
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mDeviceList:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/util/ArrayMap;

    const-string v0, "remote_device_name"

    .line 823
    invoke-virtual {p1, v0}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    const-string v4, "remote_connect_status"

    .line 824
    invoke-virtual {p1, v4}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Integer;

    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    move-result v4

    const-string v5, "remote_device_macaddr"

    .line 825
    invoke-virtual {p1, v5}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 827
    iget-object v6, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    if-eqz v6, :cond_4

    .line 828
    iget-object v6, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    invoke-virtual {v6, p1}, Landroid/widget/Button;->setTag(Ljava/lang/Object;)V

    const/4 p1, -0x1

    if-ne v4, p1, :cond_1

    .line 830
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    invoke-virtual {p1, v3}, Landroid/widget/Button;->setVisibility(I)V

    goto :goto_1

    :cond_1
    const/16 p1, 0xc

    if-eq v4, p1, :cond_2

    .line 833
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    invoke-virtual {p1, v3}, Landroid/widget/Button;->setVisibility(I)V

    goto :goto_1

    .line 835
    :cond_2
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    invoke-virtual {p1, v1}, Landroid/widget/Button;->setVisibility(I)V

    goto :goto_1

    :cond_3
    move-object v0, v2

    move-object v5, v0

    .line 841
    :cond_4
    :goto_1
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    if-eqz p1, :cond_5

    .line 842
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->profileConfigImageButton:Landroid/widget/Button;

    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-virtual {p1, v4}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 845
    :cond_5
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    if-eqz p1, :cond_6

    .line 846
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 849
    :cond_6
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    if-eqz p1, :cond_a

    if-eqz v5, :cond_8

    .line 850
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$1100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v5, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_8

    .line 851
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    const v0, 0x7f05003c

    if-eqz p1, :cond_7

    .line 852
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    const v2, 0x7f0d004a

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 853
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setTextColor(I)V

    .line 855
    :cond_7
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    if-eqz p1, :cond_a

    .line 856
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    goto :goto_2

    .line 859
    :cond_8
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    const v0, 0x7f05003b

    if-eqz p1, :cond_9

    .line 860
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 861
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->statusTextView:Landroid/widget/TextView;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setTextColor(I)V

    .line 863
    :cond_9
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    if-eqz p1, :cond_a

    .line 864
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->nameTextView:Landroid/widget/TextView;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    .line 869
    :cond_a
    :goto_2
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->progressBar:Landroid/widget/ProgressBar;

    if-eqz p1, :cond_d

    .line 870
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mSelectMacAddress:Ljava/lang/String;

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result p1

    if-lez p1, :cond_c

    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mSelectMacAddress:Ljava/lang/String;

    invoke-virtual {p1, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_c

    .line 871
    iget-boolean p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->pairedStatus:Z

    if-eqz p1, :cond_b

    .line 872
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->progressBar:Landroid/widget/ProgressBar;

    invoke-virtual {p1, v1}, Landroid/widget/ProgressBar;->setVisibility(I)V

    goto :goto_3

    .line 874
    :cond_b
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->progressBar:Landroid/widget/ProgressBar;

    invoke-virtual {p1, v3}, Landroid/widget/ProgressBar;->setVisibility(I)V

    goto :goto_3

    .line 877
    :cond_c
    iget-object p1, p3, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter$ViewHolder;->progressBar:Landroid/widget/ProgressBar;

    invoke-virtual {p1, v3}, Landroid/widget/ProgressBar;->setVisibility(I)V

    :cond_d
    :goto_3
    return-object p2
.end method

.method public setPairedStatus(Z)V
    .locals 0

    .line 794
    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->pairedStatus:Z

    return-void
.end method

.method public setSelect(Ljava/lang/String;)V
    .locals 0

    .line 786
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->mSelectMacAddress:Ljava/lang/String;

    return-void
.end method
