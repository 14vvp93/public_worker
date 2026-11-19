.class Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;
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

    .line 416
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

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

    .line 421
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getPairedStatus()Z

    move-result p1

    if-eqz p1, :cond_0

    return-void

    .line 424
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    const/4 p2, 0x1

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setPairedStatus(Z)V

    .line 425
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p1

    invoke-virtual {p1, p3}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getItem(I)Landroid/util/ArrayMap;

    move-result-object p1

    const-string p2, "remote_device_macaddr"

    invoke-virtual {p1, p2}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 427
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 428
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p2

    invoke-virtual {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->notifyDataSetChanged()V

    .line 429
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p2

    const-string p3, ""

    invoke-virtual {p2, p3}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->setSelect(Ljava/lang/String;)V

    .line 430
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    move-result-object p2

    invoke-virtual {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->notifyDataSetChanged()V

    .line 431
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "connectDevice = "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    const-string p3, "PairedFragmentEx"

    invoke-static {p3, p2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 432
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;->this$0:Lcom/autochips/bluetooth/fragment/PairedFragmentEx;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->access$300(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectDevice(Ljava/lang/String;)V

    return-void
.end method
