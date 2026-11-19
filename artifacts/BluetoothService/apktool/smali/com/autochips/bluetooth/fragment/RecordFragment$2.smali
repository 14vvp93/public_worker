.class Lcom/autochips/bluetooth/fragment/RecordFragment$2;
.super Ljava/lang/Object;
.source "RecordFragment.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/RecordFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/RecordFragment;)V
    .locals 0

    .line 659
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

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

    .line 662
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$300(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    move-result-object p1

    invoke-virtual {p1, p3}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->setSelect(I)V

    .line 663
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$300(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->notifyDataSetChanged()V

    .line 664
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$2;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$300(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    move-result-object p1

    invoke-virtual {p1, p3}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->getItem(I)Ljava/util/HashMap;

    move-result-object p1

    if-eqz p1, :cond_0

    const-string p2, "item_history_number"

    .line 666
    invoke-virtual {p1, p2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 667
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p2

    invoke-virtual {p2}, Lcom/autochips/bluetooth/MyApplication;->getHfpclientManager()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->dial(Ljava/lang/String;)V

    :cond_0
    return-void
.end method
