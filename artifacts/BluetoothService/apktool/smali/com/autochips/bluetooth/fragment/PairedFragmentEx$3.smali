.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;
.super Ljava/lang/Object;
.source "PairedFragmentEx.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PairedFragmentEx;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 440
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

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

    .line 445
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    const-string p2, ""

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 446
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->notifyDataSetChanged()V

    .line 447
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1, p3}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getItem(I)Landroid/util/ArrayMap;

    move-result-object p1

    const-string p2, "remote_device_macaddr"

    invoke-virtual {p1, p2}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 449
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 450
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->notifyDataSetChanged()V

    .line 451
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->showPairedOperationDialog()V

    return-void
.end method
