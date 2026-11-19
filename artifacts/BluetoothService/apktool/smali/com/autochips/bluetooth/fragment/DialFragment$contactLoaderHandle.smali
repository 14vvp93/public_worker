.class Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;
.super Landroid/os/Handler;
.source "DialFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/DialFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "contactLoaderHandle"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/DialFragment;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/Looper;)V
    .locals 0

    .line 523
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    .line 524
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 529
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 530
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x4

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 533
    :cond_0
    :try_start_0
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/lang/String;

    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->searchContact(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 535
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :goto_0
    return-void
.end method

.method public searchContact(Ljava/lang/String;)V
    .locals 14
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 545
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$600(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v0

    const/4 v1, 0x1

    if-nez v0, :cond_0

    const-string p1, "DialFragment"

    const-string v0, "searchContact device is not connected!"

    .line 547
    invoke-static {p1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 548
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$700(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/Handler;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void

    :cond_0
    const-string v2, "DialFragment"

    .line 552
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "searchContact key : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 554
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    .line 555
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-virtual {v3}, Lcom/autochips/bluetooth/fragment/DialFragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v3, "display_name"

    .line 558
    invoke-static {}, Lcom/hcn/bluetooth/api/Utils;->isReverseName()Z

    move-result v5

    if-eqz v5, :cond_1

    const-string v3, "display_name_alt"

    :cond_1
    const/4 v11, 0x0

    .line 562
    :try_start_0
    iget-object v5, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    monitor-enter v5
    :try_end_0
    .catch Landroid/os/OperationCanceledException; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_2

    .line 563
    :try_start_1
    iget-object v6, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    new-instance v7, Landroid/os/CancellationSignal;

    invoke-direct {v7}, Landroid/os/CancellationSignal;-><init>()V

    invoke-static {v6, v7}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$302(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/CancellationSignal;)Landroid/os/CancellationSignal;

    .line 564
    monitor-exit v5
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 565
    :try_start_2
    sget-object v5, Landroid/provider/ContactsContract$CommonDataKinds$Phone;->CONTENT_URI:Landroid/net/Uri;

    const/4 v12, 0x2

    new-array v6, v12, [Ljava/lang/String;

    const/4 v7, 0x0

    aput-object v3, v6, v7

    const-string v8, "data1"

    aput-object v8, v6, v1

    .line 566
    invoke-static {}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$800()Ljava/lang/String;

    move-result-object v8

    const/4 v9, 0x3

    new-array v9, v9, [Ljava/lang/String;

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "%"

    invoke-virtual {v10, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v13, "%"

    invoke-virtual {v10, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    aput-object v10, v9, v7

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v9, v1

    const-string v0, "com.android.bluetooth.pbapsink"

    aput-object v0, v9, v12

    const/4 v0, 0x0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    .line 567
    invoke-static {v1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$300(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/CancellationSignal;

    move-result-object v10

    move-object v7, v8

    move-object v8, v9

    move-object v9, v0

    .line 565
    invoke-virtual/range {v4 .. v10}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;

    move-result-object v0
    :try_end_2
    .catch Landroid/os/OperationCanceledException; {:try_start_2 .. :try_end_2} :catch_1
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .line 569
    :cond_2
    :goto_0
    :try_start_3
    invoke-interface {v0}, Landroid/database/Cursor;->moveToNext()Z

    move-result v1

    if-eqz v1, :cond_3

    const-string v1, "data1"

    .line 570
    invoke-interface {v0, v1}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v1

    invoke-interface {v0, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v1

    .line 571
    invoke-interface {v0, v3}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v4

    invoke-interface {v0, v4}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v4

    .line 573
    new-instance v5, Ljava/util/HashMap;

    invoke-direct {v5}, Ljava/util/HashMap;-><init>()V

    const-string v6, "item_phonebook_name"

    .line 574
    invoke-virtual {v5, v6, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v4, "item_phonebook_number"

    .line 575
    invoke-virtual {v5, v4, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v1, "item_phonebook_path"

    const-string v4, ""

    .line 576
    invoke-virtual {v5, v1, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 577
    invoke-virtual {v2, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 579
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v1

    const/16 v4, 0xa

    if-lt v1, v4, :cond_2

    .line 581
    new-instance v1, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;

    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {v1, v4, v11}, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;Lcom/autochips/bluetooth/fragment/DialFragment$1;)V

    .line 582
    iput-object p1, v1, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->searchKey:Ljava/lang/String;

    .line 583
    iput-object v2, v1, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->pbList:Ljava/util/ArrayList;

    .line 584
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v2}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$700(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/Handler;

    move-result-object v2

    invoke-static {v2, v12, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    .line 585
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v2}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$700(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/Handler;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    .line 586
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    move-object v2, v1

    goto :goto_0

    .line 589
    :cond_3
    invoke-interface {v0}, Landroid/database/Cursor;->close()V
    :try_end_3
    .catch Landroid/os/OperationCanceledException; {:try_start_3 .. :try_end_3} :catch_0
    .catchall {:try_start_3 .. :try_end_3} :catchall_2

    .line 600
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    monitor-enter v0

    .line 601
    :try_start_4
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v1, v11}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$302(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/CancellationSignal;)Landroid/os/CancellationSignal;

    .line 602
    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 604
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-eqz v0, :cond_4

    .line 605
    new-instance v0, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {v0, v1, v11}, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;Lcom/autochips/bluetooth/fragment/DialFragment$1;)V

    .line 606
    iput-object p1, v0, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->searchKey:Ljava/lang/String;

    .line 607
    iput-object v2, v0, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->pbList:Ljava/util/ArrayList;

    .line 608
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$700(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/Handler;

    move-result-object p1

    invoke-static {p1, v12, v0}, Landroid/os/Message;->obtain(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    .line 609
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$700(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/Handler;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    :cond_4
    return-void

    :catchall_0
    move-exception p1

    .line 602
    :try_start_5
    monitor-exit v0
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_0

    throw p1

    :catch_0
    move-exception p1

    goto :goto_1

    :catchall_1
    move-exception p1

    .line 564
    :try_start_6
    monitor-exit v5
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_1

    :try_start_7
    throw p1
    :try_end_7
    .catch Landroid/os/OperationCanceledException; {:try_start_7 .. :try_end_7} :catch_1
    .catchall {:try_start_7 .. :try_end_7} :catchall_2

    :catchall_2
    move-exception p1

    goto :goto_2

    :catch_1
    move-exception p1

    move-object v0, v11

    .line 591
    :goto_1
    :try_start_8
    invoke-virtual {p1}, Landroid/os/OperationCanceledException;->printStackTrace()V

    if-eqz v0, :cond_5

    .line 593
    invoke-interface {v0}, Landroid/database/Cursor;->close()V

    .line 595
    :cond_5
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    monitor-enter p1
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_2

    .line 596
    :try_start_9
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v0, v11}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$302(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/CancellationSignal;)Landroid/os/CancellationSignal;

    .line 597
    monitor-exit p1
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_4

    .line 600
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    monitor-enter v0

    .line 601
    :try_start_a
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {p1, v11}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$302(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/CancellationSignal;)Landroid/os/CancellationSignal;

    .line 602
    monitor-exit v0

    return-void

    :catchall_3
    move-exception p1

    monitor-exit v0
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_3

    throw p1

    :catchall_4
    move-exception v0

    .line 597
    :try_start_b
    monitor-exit p1
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_4

    :try_start_c
    throw v0
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_2

    .line 600
    :goto_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    monitor-enter v0

    .line 601
    :try_start_d
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-static {v1, v11}, Lcom/autochips/bluetooth/fragment/DialFragment;->access$302(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/CancellationSignal;)Landroid/os/CancellationSignal;

    .line 602
    monitor-exit v0
    :try_end_d
    .catchall {:try_start_d .. :try_end_d} :catchall_5

    .line 603
    throw p1

    :catchall_5
    move-exception p1

    .line 602
    :try_start_e
    monitor-exit v0
    :try_end_e
    .catchall {:try_start_e .. :try_end_e} :catchall_5

    throw p1
.end method
