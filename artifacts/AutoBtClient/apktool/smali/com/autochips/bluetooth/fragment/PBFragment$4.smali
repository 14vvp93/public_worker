.class Lcom/autochips/bluetooth/fragment/PBFragment$4;
.super Ljava/lang/Object;
.source "PBFragment.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 810
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView<",
            "*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .line 813
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    invoke-virtual {p1, p3}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->setSelect(I)V

    .line 814
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->notifyDataSetChanged()V

    .line 816
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->access$1100(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    const-string p2, "item_phonebook_number"

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->access$1300(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/util/ArrayList;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/ArrayList;->size()I

    move-result p1

    if-lez p1, :cond_0

    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->access$1300(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/util/ArrayList;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/ArrayList;->size()I

    move-result p1

    if-ge p3, p1, :cond_0

    .line 817
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->access$1300(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/util/ArrayList;

    move-result-object p1

    invoke-virtual {p1, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    invoke-virtual {p1, p2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    goto :goto_0

    .line 819
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$4;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    iget-object p1, p1, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {p1, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    invoke-virtual {p1, p2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 821
    :goto_0
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p2

    invoke-virtual {p2}, Lcom/autochips/bluetooth/MyApplication;->getHfpclientManager()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->dial(Ljava/lang/String;)V

    return-void
.end method
