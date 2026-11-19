.class public Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;
.super Ljava/lang/Object;
.source "WifiNewTile.java"

# interfaces
.implements Lcom/android/systemui/plugins/qs/DetailAdapter;
.implements Lcom/android/systemui/qs/QSDetailItems$Callback;
.implements Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController$AccessPointCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/WifiNewTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4
    name = "WifiDetailAdapter"
.end annotation


# instance fields
.field private mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

.field private mItems:Lcom/android/systemui/qs/QSDetailItems;

.field final synthetic this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;


# direct methods
.method protected constructor <init>(Lcom/android/systemui/qs/tiles/WifiNewTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/WifiNewTile;

    .line 305
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$400(Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;

    .line 305
    invoke-direct {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->updateItems()V

    return-void
.end method

.method private filterUnreachableAPs()V
    .locals 8

    .line 359
    const/4 v0, 0x0

    .line 360
    .local v0, "numReachable":I
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    array-length v2, v1

    const/4 v3, 0x0

    move v4, v0

    move v0, v3

    .end local v0    # "numReachable":I
    .local v4, "numReachable":I
    :goto_0
    if-ge v0, v2, :cond_1

    aget-object v5, v1, v0

    .line 361
    .local v5, "ap":Lcom/android/settingslib/wifi/AccessPoint;
    invoke-virtual {v5}, Lcom/android/settingslib/wifi/AccessPoint;->isReachable()Z

    move-result v6

    if-eqz v6, :cond_0

    add-int/lit8 v4, v4, 0x1

    .line 360
    .end local v5    # "ap":Lcom/android/settingslib/wifi/AccessPoint;
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 363
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    array-length v0, v0

    if-eq v4, v0, :cond_3

    .line 364
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    .line 365
    .local v0, "unfiltered":[Lcom/android/settingslib/wifi/AccessPoint;
    new-array v1, v4, [Lcom/android/settingslib/wifi/AccessPoint;

    iput-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    .line 366
    const/4 v1, 0x0

    .line 367
    .local v1, "i":I
    array-length v2, v0

    :goto_1
    if-ge v3, v2, :cond_3

    aget-object v5, v0, v3

    .line 368
    .restart local v5    # "ap":Lcom/android/settingslib/wifi/AccessPoint;
    invoke-virtual {v5}, Lcom/android/settingslib/wifi/AccessPoint;->isReachable()Z

    move-result v6

    if-eqz v6, :cond_2

    iget-object v6, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    add-int/lit8 v7, v1, 0x1

    .local v7, "i":I
    aput-object v5, v6, v1

    .line 367
    .end local v1    # "i":I
    .end local v5    # "ap":Lcom/android/settingslib/wifi/AccessPoint;
    move v1, v7

    .end local v7    # "i":I
    .restart local v1    # "i":I
    :cond_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 371
    .end local v0    # "unfiltered":[Lcom/android/settingslib/wifi/AccessPoint;
    .end local v1    # "i":I
    :cond_3
    return-void
.end method

.method private updateItems()V
    .locals 6

    .line 401
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    if-nez v0, :cond_0

    return-void

    .line 402
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    array-length v0, v0

    if-gtz v0, :cond_2

    :cond_1
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    iget-object v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    iget-object v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;->mInfo:Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;

    iget-boolean v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    if-nez v0, :cond_3

    .line 404
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-virtual {v0, v1}, Lcom/android/systemui/qs/tiles/WifiNewTile;->fireScanStateChanged(Z)V

    goto :goto_0

    .line 406
    :cond_3
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Lcom/android/systemui/qs/tiles/WifiNewTile;->fireScanStateChanged(Z)V

    .line 410
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    iget-object v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mSignalCallback:Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;

    iget-object v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiSignalCallback;->mInfo:Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;

    iget-boolean v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile$CallbackInfo;->enabled:Z

    const/4 v2, 0x0

    const v3, 0x7f0803c1

    if-nez v0, :cond_4

    .line 411
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    const v1, 0x7f11063d

    invoke-virtual {v0, v3, v1}, Lcom/android/systemui/qs/QSDetailItems;->setEmptyState(II)V

    .line 413
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    invoke-virtual {v0, v2}, Lcom/android/systemui/qs/QSDetailItems;->setItems([Lcom/android/systemui/qs/QSDetailItems$Item;)V

    .line 414
    return-void

    .line 418
    :cond_4
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    const v4, 0x7f1104d3

    invoke-virtual {v0, v3, v4}, Lcom/android/systemui/qs/QSDetailItems;->setEmptyState(II)V

    .line 422
    const/4 v0, 0x0

    .line 423
    .local v0, "items":[Lcom/android/systemui/qs/QSDetailItems$Item;
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    if-eqz v3, :cond_7

    .line 424
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    array-length v3, v3

    new-array v0, v3, [Lcom/android/systemui/qs/QSDetailItems$Item;

    .line 425
    nop

    .local v1, "i":I
    :goto_1
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    array-length v3, v3

    if-ge v1, v3, :cond_7

    .line 426
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    aget-object v3, v3, v1

    .line 427
    .local v3, "ap":Lcom/android/settingslib/wifi/AccessPoint;
    new-instance v4, Lcom/android/systemui/qs/QSDetailItems$Item;

    invoke-direct {v4}, Lcom/android/systemui/qs/QSDetailItems$Item;-><init>()V

    .line 428
    .local v4, "item":Lcom/android/systemui/qs/QSDetailItems$Item;
    iput-object v3, v4, Lcom/android/systemui/qs/QSDetailItems$Item;->tag:Ljava/lang/Object;

    .line 429
    iget-object v5, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v5}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1300(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    move-result-object v5

    invoke-interface {v5, v3}, Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;->getIcon(Lcom/android/settingslib/wifi/AccessPoint;)I

    move-result v5

    iput v5, v4, Lcom/android/systemui/qs/QSDetailItems$Item;->iconResId:I

    .line 430
    invoke-virtual {v3}, Lcom/android/settingslib/wifi/AccessPoint;->getSsid()Ljava/lang/CharSequence;

    move-result-object v5

    iput-object v5, v4, Lcom/android/systemui/qs/QSDetailItems$Item;->line1:Ljava/lang/CharSequence;

    .line 431
    invoke-virtual {v3}, Lcom/android/settingslib/wifi/AccessPoint;->isActive()Z

    move-result v5

    if-eqz v5, :cond_5

    invoke-virtual {v3}, Lcom/android/settingslib/wifi/AccessPoint;->getSummary()Ljava/lang/String;

    move-result-object v5

    goto :goto_2

    :cond_5
    move-object v5, v2

    :goto_2
    iput-object v5, v4, Lcom/android/systemui/qs/QSDetailItems$Item;->line2:Ljava/lang/CharSequence;

    .line 432
    invoke-virtual {v3}, Lcom/android/settingslib/wifi/AccessPoint;->getSecurity()I

    move-result v5

    if-eqz v5, :cond_6

    .line 433
    const v5, 0x7f080536

    goto :goto_3

    .line 434
    :cond_6
    const/4 v5, -0x1

    :goto_3
    iput v5, v4, Lcom/android/systemui/qs/QSDetailItems$Item;->icon2:I

    .line 435
    aput-object v4, v0, v1

    .line 425
    .end local v3    # "ap":Lcom/android/settingslib/wifi/AccessPoint;
    .end local v4    # "item":Lcom/android/systemui/qs/QSDetailItems$Item;
    add-int/lit8 v1, v1, 0x1

    goto :goto_1

    .line 438
    .end local v1    # "i":I
    :cond_7
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    invoke-virtual {v1, v0}, Lcom/android/systemui/qs/QSDetailItems;->setItems([Lcom/android/systemui/qs/QSDetailItems$Item;)V

    .line 439
    return-void
.end method


# virtual methods
.method public createDetailView(Landroid/content/Context;Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 3
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "convertView"    # Landroid/view/View;
    .param p3, "parent"    # Landroid/view/ViewGroup;

    .line 339
    invoke-static {}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1100()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1200(Lcom/android/systemui/qs/tiles/WifiNewTile;)Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "createDetailView convertView="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    if-eqz p2, :cond_0

    const/4 v2, 0x1

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 340
    :cond_1
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    .line 341
    invoke-static {p1, p2, p3}, Lcom/android/systemui/qs/QSDetailItems;->convertOrInflate(Landroid/content/Context;Landroid/view/View;Landroid/view/ViewGroup;)Lcom/android/systemui/qs/QSDetailItems;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    .line 342
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    const-string v1, "Wifi"

    invoke-virtual {v0, v1}, Lcom/android/systemui/qs/QSDetailItems;->setTagSuffix(Ljava/lang/String;)V

    .line 343
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    invoke-virtual {v0, p0}, Lcom/android/systemui/qs/QSDetailItems;->setCallback(Lcom/android/systemui/qs/QSDetailItems$Callback;)V

    .line 344
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1300(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;->scanForAccessPoints()V

    .line 345
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1400(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/plugins/qs/QSTile$State;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v0, v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    invoke-virtual {p0, v0}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->setItemsVisible(Z)V

    .line 346
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 334
    const/16 v0, 0x98

    return v0
.end method

.method public getSettingsIntent()Landroid/content/Intent;
    .locals 1

    .line 317
    invoke-static {}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$600()Landroid/content/Intent;

    move-result-object v0

    return-object v0
.end method

.method public getTitle()Ljava/lang/CharSequence;
    .locals 2

    .line 313
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$500(Lcom/android/systemui/qs/tiles/WifiNewTile;)Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f1104d4

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public getToggleState()Ljava/lang/Boolean;
    .locals 1

    .line 322
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$700(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/plugins/qs/QSTile$State;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;

    iget-boolean v0, v0, Lcom/android/systemui/plugins/qs/QSTile$SignalState;->value:Z

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    return-object v0
.end method

.method public onAccessPointsChanged(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/settingslib/wifi/AccessPoint;",
            ">;)V"
        }
    .end annotation

    .line 351
    .local p1, "accessPoints":Ljava/util/List;, "Ljava/util/List<Lcom/android/settingslib/wifi/AccessPoint;>;"
    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v0

    new-array v0, v0, [Lcom/android/settingslib/wifi/AccessPoint;

    invoke-interface {p1, v0}, Ljava/util/List;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/android/settingslib/wifi/AccessPoint;

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mAccessPoints:[Lcom/android/settingslib/wifi/AccessPoint;

    .line 352
    invoke-direct {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->filterUnreachableAPs()V

    .line 354
    invoke-direct {p0}, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->updateItems()V

    .line 355
    return-void
.end method

.method public onDetailItemClick(Lcom/android/systemui/qs/QSDetailItems$Item;)V
    .locals 3
    .param p1, "item"    # Lcom/android/systemui/qs/QSDetailItems$Item;

    .line 380
    if-eqz p1, :cond_2

    iget-object v0, p1, Lcom/android/systemui/qs/QSDetailItems$Item;->tag:Ljava/lang/Object;

    if-nez v0, :cond_0

    goto :goto_0

    .line 381
    :cond_0
    iget-object v0, p1, Lcom/android/systemui/qs/QSDetailItems$Item;->tag:Ljava/lang/Object;

    check-cast v0, Lcom/android/settingslib/wifi/AccessPoint;

    .line 382
    .local v0, "ap":Lcom/android/settingslib/wifi/AccessPoint;
    invoke-virtual {v0}, Lcom/android/settingslib/wifi/AccessPoint;->isActive()Z

    move-result v1

    if-nez v1, :cond_1

    .line 383
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v1}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1300(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;

    move-result-object v1

    invoke-interface {v1, v0}, Lcom/android/systemui/statusbar/policy/NetworkController$AccessPointController;->connect(Lcom/android/settingslib/wifi/AccessPoint;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 384
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v1}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1600(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/qs/QSHost;

    move-result-object v1

    invoke-interface {v1}, Lcom/android/systemui/qs/QSHost;->collapsePanels()V

    .line 387
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Lcom/android/systemui/qs/tiles/WifiNewTile;->showDetail(Z)V

    .line 388
    return-void

    .line 380
    .end local v0    # "ap":Lcom/android/settingslib/wifi/AccessPoint;
    :cond_2
    :goto_0
    return-void
.end method

.method public onDetailItemDisconnect(Lcom/android/systemui/qs/QSDetailItems$Item;)V
    .locals 0
    .param p1, "item"    # Lcom/android/systemui/qs/QSDetailItems$Item;

    .line 393
    return-void
.end method

.method public onSettingsActivityTriggered(Landroid/content/Intent;)V
    .locals 2
    .param p1, "settingsIntent"    # Landroid/content/Intent;

    .line 375
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1500(Lcom/android/systemui/qs/tiles/WifiNewTile;)Lcom/android/systemui/plugins/ActivityStarter;

    move-result-object v0

    const/4 v1, 0x0

    invoke-interface {v0, p1, v1}, Lcom/android/systemui/plugins/ActivityStarter;->postStartActivityDismissingKeyguard(Landroid/content/Intent;I)V

    .line 376
    return-void
.end method

.method public setItemsVisible(Z)V
    .locals 1
    .param p1, "visible"    # Z

    .line 396
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    if-nez v0, :cond_0

    return-void

    .line 397
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->mItems:Lcom/android/systemui/qs/QSDetailItems;

    invoke-virtual {v0, p1}, Lcom/android/systemui/qs/QSDetailItems;->setItemsVisible(Z)V

    .line 398
    return-void
.end method

.method public setToggleState(Z)V
    .locals 3
    .param p1, "state"    # Z

    .line 327
    invoke-static {}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$800()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$900(Lcom/android/systemui/qs/tiles/WifiNewTile;)Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setToggleState "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 328
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/WifiNewTile;->access$1000(Lcom/android/systemui/qs/tiles/WifiNewTile;)Landroid/content/Context;

    move-result-object v0

    const/16 v1, 0x99

    invoke-static {v0, v1, p1}, Lcom/android/internal/logging/MetricsLogger;->action(Landroid/content/Context;IZ)V

    .line 329
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/WifiNewTile$WifiDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/WifiNewTile;

    iget-object v0, v0, Lcom/android/systemui/qs/tiles/WifiNewTile;->mController:Lcom/android/systemui/statusbar/policy/NetworkController;

    invoke-interface {v0, p1}, Lcom/android/systemui/statusbar/policy/NetworkController;->setWifiEnabled(Z)V

    .line 330
    return-void
.end method
