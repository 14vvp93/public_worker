.class Lcom/autochips/bluetooth/fragment/RecordFragment$3;
.super Ljava/lang/Object;
.source "RecordFragment.java"

# interfaces
.implements Lcom/hcn/bluetooth/api/ConnectionListener;


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

    .line 672
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected()V
    .locals 2

    .line 675
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$700(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    iget-object v1, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 676
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$700(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getPbapDownLoadState(I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 678
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/RecordFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->access$000(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    :cond_0
    return-void
.end method

.method public onServiceDisconnected()V
    .locals 0

    return-void
.end method
