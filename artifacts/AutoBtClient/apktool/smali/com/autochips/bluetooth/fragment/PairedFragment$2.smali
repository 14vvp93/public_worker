.class Lcom/autochips/bluetooth/fragment/PairedFragment$2;
.super Ljava/lang/Object;
.source "PairedFragment.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 535
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

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

    .line 540
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object p1

    const-string p2, ""

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 541
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->notifyDataSetChanged()V

    .line 542
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object p1

    invoke-virtual {p1, p3}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->getItem(I)Landroid/util/ArrayMap;

    move-result-object p1

    const-string p2, "remote_device_macaddr"

    invoke-virtual {p1, p2}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 544
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->setSelect(Ljava/lang/String;)V

    .line 545
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->notifyDataSetChanged()V

    return-void
.end method
